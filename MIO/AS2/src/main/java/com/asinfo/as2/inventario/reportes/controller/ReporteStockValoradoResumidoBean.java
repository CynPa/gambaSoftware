/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteSaldoProducto;
/*   4:    */ import com.asinfo.as2.clases.ReporteStockValoradoResumido;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.entities.Atributo;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.Unidad;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*  21:    */ import com.asinfo.as2.inventario.reportes.controller.dataSource.ReporteStockProductoDataSource;
/*  22:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValorado;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  25:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  26:    */ import com.asinfo.as2.ventas.reportes.AbstractInventarioReportBean;
/*  27:    */ import java.io.IOException;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.math.RoundingMode;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Calendar;
/*  32:    */ import java.util.Date;
/*  33:    */ import java.util.HashMap;
/*  34:    */ import java.util.Iterator;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import java.util.TreeMap;
/*  38:    */ import javax.annotation.PostConstruct;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.faces.bean.ManagedBean;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.model.SelectItem;
/*  43:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  44:    */ import net.sf.jasperreports.engine.JRException;
/*  45:    */ import org.apache.log4j.Logger;
/*  46:    */ 
/*  47:    */ @ManagedBean
/*  48:    */ @ViewScoped
/*  49:    */ public class ReporteStockValoradoResumidoBean
/*  50:    */   extends AbstractInventarioReportBean
/*  51:    */ {
/*  52:    */   private static final long serialVersionUID = -595660703984412823L;
/*  53:    */   @EJB
/*  54:    */   private ServicioReporteStockValorado servicioReporteStockValorado;
/*  55:    */   @EJB
/*  56:    */   private ServicioSucursal servicioSucursal;
/*  57:    */   @EJB
/*  58:    */   private ServicioBodega servicioBodega;
/*  59:    */   @EJB
/*  60:    */   private ServicioProducto servicioProducto;
/*  61:    */   @EJB
/*  62:    */   private ServicioAtributo servicioAtributo;
/*  63:    */   @EJB
/*  64:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  65:    */   @EJB
/*  66:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  67:    */   @EJB
/*  68:    */   private ServicioValorAtributo servicioValorAtributo;
/*  69: 89 */   private ReporteStockProductoDataSource reporteStockProductoDataSource = new ReporteStockProductoDataSource();
/*  70:    */   private Date fechaDesde;
/*  71:    */   private Date fechaHasta;
/*  72:    */   private Bodega bodega;
/*  73:    */   private Sucursal sucursal;
/*  74:    */   private List<Bodega> listaBodega;
/*  75:    */   private List<Sucursal> listaSucursal;
/*  76:    */   private boolean indicadorSaldoCero;
/*  77:    */   
/*  78:    */   private static enum enumUnidad
/*  79:    */   {
/*  80:107 */     STOCK,  VENTA,  ALMACENAMIENTO;
/*  81:    */     
/*  82:    */     private enumUnidad() {}
/*  83:    */   }
/*  84:    */   
/*  85:110 */   private enumUnidad unidad = enumUnidad.STOCK;
/*  86:    */   private List<SelectItem> listaUnidad;
/*  87:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  88:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  89:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  90:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  91:    */   
/*  92:    */   protected JRDataSource getJRDataSource()
/*  93:    */   {
/*  94:130 */     return this.reporteStockProductoDataSource;
/*  95:    */   }
/*  96:    */   
/*  97:    */   @PostConstruct
/*  98:    */   public void init()
/*  99:    */   {
/* 100:135 */     Calendar calfechaDesde = Calendar.getInstance();
/* 101:136 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 102:137 */     this.fechaDesde = calfechaDesde.getTime();
/* 103:138 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected Map<String, Object> getReportParameters()
/* 107:    */   {
/* 108:149 */     asignarValorAtributo(getValorAtributoSeleccionado());
/* 109:    */     
/* 110:151 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 111:152 */     reportParameters.put("ReportTitle", "Reporte Stock Valorado Rsumido");
/* 112:153 */     reportParameters.put("p_sucursal", this.sucursal == null ? "" : this.sucursal.getNombre());
/* 113:154 */     reportParameters.put("p_bodega", this.bodega == null ? "" : this.bodega.getNombre());
/* 114:155 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 115:156 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 116:157 */     reportParameters.put("p_categoriaProducto", this.categoriaProductoSeleccionado != null ? this.categoriaProductoSeleccionado.getNombre() : "Todos");
/* 117:158 */     reportParameters.put("p_subCategoriaProducto", this.subcategoriaProductoSeleccionado != null ? this.subcategoriaProductoSeleccionado.getNombre() : "Todos");
/* 118:159 */     reportParameters.put("p_atributo", getAtributo() != null ? getAtributo().getNombre() : "Todos");
/* 119:160 */     reportParameters.put("p_valorAtributo", (getValorAtributo() != null) && (!getValorAtributo().isEmpty()) ? getValorAtributo() : "Todos");
/* 120:161 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 121:162 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 122:163 */     return reportParameters;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String execute()
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:170 */       asignarValorAtributo(getValorAtributoSeleccionado());
/* 130:    */       
/* 131:172 */       Map<String, ReporteStockValoradoResumido> hmReporte = new TreeMap();
/* 132:173 */       List<ReporteStockValoradoResumido> listaReporteStockValoradoResumido = this.servicioReporteStockValorado.getReporteStockValoradoResumido(null, this.bodega, this.fechaDesde, this.fechaHasta, 
/* 133:174 */         getAtributo(), getValorAtributo(), AppUtil.getOrganizacion().getId(), this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado);
/* 134:177 */       for (ReporteStockValoradoResumido reporteStock : listaReporteStockValoradoResumido)
/* 135:    */       {
/* 136:178 */         clave = reporteStock.getIdBodega() + ":" + reporteStock.getNombreProducto() + ":" + reporteStock.getIdProducto();
/* 137:179 */         hmReporte.put(clave, reporteStock);
/* 138:    */       }
/* 139:184 */       Date fechaSaldoInicial = FuncionesUtiles.sumarFechaDiasMeses(this.fechaDesde, -1);
/* 140:185 */       List<ReporteSaldoProducto> lista = this.servicioReporteStockValorado.getReporteSaldoProducto(null, this.bodega, fechaSaldoInicial, getAtributo(), 
/* 141:186 */         getValorAtributo(), AppUtil.getOrganizacion().getId(), this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado);
/* 142:188 */       for (String clave = lista.iterator(); clave.hasNext();)
/* 143:    */       {
/* 144:188 */         reporteStock = (ReporteSaldoProducto)clave.next();
/* 145:    */         
/* 146:190 */         String clave = reporteStock.getIdBodega() + ":" + reporteStock.getNombreProducto() + ":" + reporteStock.getIdProducto();
/* 147:192 */         if (!hmReporte.containsKey(clave))
/* 148:    */         {
/* 149:194 */           BigDecimal saldoInicial = this.servicioProducto.getSaldoInicial(reporteStock.getIdProducto().intValue(), new Bodega(reporteStock
/* 150:195 */             .getIdBodega().intValue()), this.fechaDesde);
/* 151:196 */           BigDecimal costoTotal = this.servicioProducto.getCosto(reporteStock.getIdProducto().intValue(), this.fechaDesde, new Bodega(reporteStock.getIdBodega().intValue()));
/* 152:197 */           if (saldoInicial.compareTo(BigDecimal.ZERO) != 0)
/* 153:    */           {
/* 154:198 */             ReporteStockValoradoResumido dr = new ReporteStockValoradoResumido(reporteStock);
/* 155:199 */             dr.setSaldoInicial(saldoInicial);
/* 156:200 */             dr.setSaldoFinal(saldoInicial);
/* 157:201 */             dr.setCostoInicial(costoTotal);
/* 158:202 */             dr.setCodigoUnidad(reporteStock.getCodigoUnidad());
/* 159:203 */             dr.setCodigoAlterno(reporteStock.getCodigoAlternoProducto());
/* 160:204 */             hmReporte.put(clave, dr);
/* 161:    */           }
/* 162:206 */           else if ((this.indicadorSaldoCero) && (saldoInicial.compareTo(BigDecimal.ZERO) == 0))
/* 163:    */           {
/* 164:207 */             ReporteStockValoradoResumido dr = new ReporteStockValoradoResumido(reporteStock);
/* 165:208 */             dr.setCodigoAlterno(reporteStock.getCodigoAlternoProducto());
/* 166:209 */             hmReporte.put(clave, dr);
/* 167:    */           }
/* 168:    */         }
/* 169:    */       }
/* 170:    */       ReporteSaldoProducto reporteStock;
/* 171:217 */       Date fechaCostoInicial = FuncionesUtiles.sumarFechaDiasMeses(this.fechaDesde, -1);
/* 172:218 */       for (ReporteStockValoradoResumido reporteStock : listaReporteStockValoradoResumido)
/* 173:    */       {
/* 174:219 */         BigDecimal saldoInicial = this.servicioProducto.getSaldoInicial(reporteStock.getIdProducto(), new Bodega(reporteStock.getIdBodega()), this.fechaDesde);
/* 175:    */         
/* 176:221 */         BigDecimal saldoTotal = BigDecimal.ZERO;
/* 177:222 */         if ((ParametrosSistema.isCosteoPorBodega(AppUtil.getOrganizacion().getId()).booleanValue()) && (this.bodega != null)) {
/* 178:223 */           saldoTotal = this.servicioProducto.getSaldo(reporteStock.getIdProducto(), this.bodega.getIdBodega(), fechaCostoInicial);
/* 179:    */         } else {
/* 180:225 */           saldoTotal = this.servicioProducto.getSaldo(reporteStock.getIdProducto(), 0, fechaCostoInicial);
/* 181:    */         }
/* 182:227 */         reporteStock.setSaldoInicial(saldoInicial);
/* 183:228 */         BigDecimal costoInicialProducto = BigDecimal.ZERO;
/* 184:229 */         if (saldoTotal.compareTo(BigDecimal.ZERO) > 0)
/* 185:    */         {
/* 186:231 */           if (ParametrosSistema.isCosteoPorBodega(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 187:233 */             costoInicialProducto = this.servicioProducto.getCosto(reporteStock.getIdProducto(), fechaCostoInicial, new Bodega(reporteStock
/* 188:234 */               .getIdBodega()));
/* 189:    */           } else {
/* 190:236 */             costoInicialProducto = this.servicioProducto.getCosto(reporteStock.getIdProducto(), fechaCostoInicial, null);
/* 191:    */           }
/* 192:238 */           costoInicialProducto = costoInicialProducto.divide(saldoTotal, 10, RoundingMode.HALF_UP);
/* 193:    */         }
/* 194:240 */         reporteStock.setCostoUnitario(costoInicialProducto);
/* 195:241 */         reporteStock.setCostoInicial(costoInicialProducto.multiply(saldoInicial));
/* 196:242 */         reporteStock.setCodigoUnidad(reporteStock.getCodigoUnidad());
/* 197:    */       }
/* 198:246 */       if (this.unidad != enumUnidad.STOCK) {
/* 199:247 */         convertirSaldos(hmReporte);
/* 200:    */       }
/* 201:250 */       this.reporteStockProductoDataSource.setListaReporteStockValoradoResumido(new ArrayList(hmReporte.values()));
/* 202:    */       
/* 203:252 */       super.prepareReport();
/* 204:    */     }
/* 205:    */     catch (JRException e)
/* 206:    */     {
/* 207:254 */       LOG.info("Error JRException");
/* 208:255 */       e.printStackTrace();
/* 209:256 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 210:    */     }
/* 211:    */     catch (IOException e)
/* 212:    */     {
/* 213:258 */       LOG.info("Error IOException");
/* 214:259 */       e.printStackTrace();
/* 215:260 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 216:    */     }
/* 217:263 */     return null;
/* 218:    */   }
/* 219:    */   
/* 220:    */   private void convertirSaldos(Map<String, ReporteStockValoradoResumido> hmReporte)
/* 221:    */   {
/* 222:269 */     for (ReporteStockValoradoResumido r : hmReporte.values()) {
/* 223:    */       try
/* 224:    */       {
/* 225:273 */         Unidad unidadStock = new Unidad(r.getIdUnidad(), r.getCodigoUnidad(), r.getNombreUnidad());
/* 226:274 */         Unidad unidadConversion = null;
/* 227:276 */         if (this.unidad == enumUnidad.VENTA) {
/* 228:278 */           unidadConversion = r.getIdUnidadVenta() == null ? unidadStock : new Unidad(r.getIdUnidadVenta().intValue(), r.getCodigoUnidadVenta(), r.getNombreUnidadVenta());
/* 229:280 */         } else if (this.unidad == enumUnidad.ALMACENAMIENTO) {
/* 230:282 */           unidadConversion = r.getIdUnidadAlmacenamiento() == null ? unidadStock : new Unidad(r.getIdUnidadAlmacenamiento().intValue(), r.getCodigoUnidadAlmacenamiento(), r.getNombreUnidadAlmacenamiento());
/* 231:    */         }
/* 232:285 */         BigDecimal saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getSaldoInicial());
/* 233:286 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 234:287 */         r.setSaldoInicial(saldoUnidad);
/* 235:    */         
/* 236:289 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getRecepcion());
/* 237:290 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 238:291 */         r.setRecepcion(saldoUnidad);
/* 239:    */         
/* 240:293 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getAjusteIngreso());
/* 241:294 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 242:295 */         r.setAjusteIngreso(saldoUnidad);
/* 243:    */         
/* 244:297 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getTransferenciaIngreso());
/* 245:298 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 246:299 */         r.setTransferenciaIngreso(saldoUnidad);
/* 247:    */         
/* 248:301 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getDevolucionCliente());
/* 249:302 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 250:303 */         r.setDevolucionCliente(saldoUnidad);
/* 251:    */         
/* 252:305 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getAjusteEgreso());
/* 253:306 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 254:307 */         r.setAjusteEgreso(saldoUnidad);
/* 255:    */         
/* 256:309 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getTransferenciaEgreso());
/* 257:310 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 258:311 */         r.setTransferenciaEgreso(saldoUnidad);
/* 259:    */         
/* 260:313 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getConsumo());
/* 261:314 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 262:315 */         r.setConsumo(saldoUnidad);
/* 263:    */         
/* 264:317 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getDespacho());
/* 265:318 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 266:319 */         r.setDespacho(saldoUnidad);
/* 267:    */         
/* 268:321 */         saldoUnidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, r.getIdProducto(), r.getDevolucionProvedor());
/* 269:322 */         saldoUnidad = FuncionesUtiles.redondearBigDecimal(saldoUnidad, 4);
/* 270:323 */         r.setDevolucionProvedor(saldoUnidad);
/* 271:    */         
/* 272:325 */         r.setCodigoUnidad(unidadConversion.getCodigo());
/* 273:    */       }
/* 274:    */       catch (ExcepcionAS2 e)
/* 275:    */       {
/* 276:328 */         e.printStackTrace();
/* 277:    */       }
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   protected String getCompileFileName()
/* 282:    */   {
/* 283:341 */     return "reporteStockValoradoResumido";
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void cargarListaSubcategoriaProducto()
/* 287:    */   {
/* 288:346 */     HashMap<String, String> filters = new HashMap();
/* 289:347 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 290:348 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public List<Sucursal> getListaSucursal()
/* 294:    */   {
/* 295:357 */     if (this.listaSucursal == null) {
/* 296:358 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 297:    */     }
/* 298:360 */     return this.listaSucursal;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public Sucursal getSucursal()
/* 302:    */   {
/* 303:369 */     if (this.sucursal == null) {
/* 304:370 */       this.sucursal = new Sucursal();
/* 305:    */     }
/* 306:372 */     return this.sucursal;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setSucursal(Sucursal sucursal)
/* 310:    */   {
/* 311:382 */     this.sucursal = sucursal;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public Date getFechaDesde()
/* 315:    */   {
/* 316:391 */     return this.fechaDesde;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setFechaDesde(Date fechaDesde)
/* 320:    */   {
/* 321:401 */     this.fechaDesde = fechaDesde;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public Date getFechaHasta()
/* 325:    */   {
/* 326:410 */     return this.fechaHasta;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setFechaHasta(Date fechaHasta)
/* 330:    */   {
/* 331:420 */     this.fechaHasta = fechaHasta;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public Bodega getBodega()
/* 335:    */   {
/* 336:429 */     return this.bodega;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setBodega(Bodega bodega)
/* 340:    */   {
/* 341:439 */     this.bodega = bodega;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public List<Bodega> getListaBodega()
/* 345:    */   {
/* 346:448 */     if (this.listaBodega == null) {
/* 347:449 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, null);
/* 348:    */     }
/* 349:451 */     return this.listaBodega;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public ReporteStockProductoDataSource getReporteStockProductoDataSource()
/* 353:    */   {
/* 354:460 */     return this.reporteStockProductoDataSource;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public enumUnidad getUnidad()
/* 358:    */   {
/* 359:469 */     return this.unidad;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setUnidad(enumUnidad unidad)
/* 363:    */   {
/* 364:479 */     this.unidad = unidad;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public List<SelectItem> getListaUnidad()
/* 368:    */   {
/* 369:488 */     if (this.listaUnidad == null)
/* 370:    */     {
/* 371:489 */       this.listaUnidad = new ArrayList();
/* 372:490 */       for (enumUnidad unidad : enumUnidad.values())
/* 373:    */       {
/* 374:491 */         SelectItem item = new SelectItem(unidad, unidad.name());
/* 375:492 */         this.listaUnidad.add(item);
/* 376:    */       }
/* 377:    */     }
/* 378:495 */     return this.listaUnidad;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setListaUnidad(List<SelectItem> listaUnidad)
/* 382:    */   {
/* 383:505 */     this.listaUnidad = listaUnidad;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 387:    */   {
/* 388:514 */     return this.categoriaProductoSeleccionado;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 392:    */   {
/* 393:522 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 397:    */   {
/* 398:530 */     HashMap<String, String> filters = new HashMap();
/* 399:531 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 400:532 */     if (this.listaCategoriaProductos == null) {
/* 401:533 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 402:    */     }
/* 403:535 */     return this.listaCategoriaProductos;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 407:    */   {
/* 408:543 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 412:    */   {
/* 413:550 */     return this.subcategoriaProductoSeleccionado;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 417:    */   {
/* 418:558 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 422:    */   {
/* 423:565 */     return this.listaSubcategoriaProductos;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 427:    */   {
/* 428:573 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public boolean isIndicadorSaldoCero()
/* 432:    */   {
/* 433:577 */     return this.indicadorSaldoCero;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setIndicadorSaldoCero(boolean indicadorSaldoCero)
/* 437:    */   {
/* 438:581 */     this.indicadorSaldoCero = indicadorSaldoCero;
/* 439:    */   }
/* 440:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteStockValoradoResumidoBean
 * JD-Core Version:    0.7.0.1
 */