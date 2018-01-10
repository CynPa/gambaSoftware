/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteSaldoProducto;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Lote;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  14:    */ import com.asinfo.as2.entities.Unidad;
/*  15:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  23:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValorado;
/*  24:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  25:    */ import com.asinfo.as2.util.AppUtil;
/*  26:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  27:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  28:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  29:    */ import java.io.IOException;
/*  30:    */ import java.math.BigDecimal;
/*  31:    */ import java.util.ArrayList;
/*  32:    */ import java.util.Date;
/*  33:    */ import java.util.HashMap;
/*  34:    */ import java.util.List;
/*  35:    */ import java.util.Map;
/*  36:    */ import java.util.TreeMap;
/*  37:    */ import javax.annotation.PostConstruct;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.faces.bean.ManagedBean;
/*  40:    */ import javax.faces.bean.ViewScoped;
/*  41:    */ import javax.faces.model.SelectItem;
/*  42:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  43:    */ import net.sf.jasperreports.engine.JRException;
/*  44:    */ import org.apache.log4j.Logger;
/*  45:    */ 
/*  46:    */ @ManagedBean
/*  47:    */ @ViewScoped
/*  48:    */ public class ReporteStockValoradoPorLoteBean
/*  49:    */   extends AbstractBaseReportBean
/*  50:    */ {
/*  51:    */   private static final long serialVersionUID = 4142507465102427364L;
/*  52:    */   @EJB
/*  53:    */   private ServicioReporteStockValorado servicioReporteStockValorado;
/*  54:    */   @EJB
/*  55:    */   private ServicioBodega servicioBodega;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioProducto servicioProducto;
/*  58:    */   @EJB
/*  59:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  60:    */   @EJB
/*  61:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioLote servicioLote;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioAtributo servicioAtributo;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  68:    */   @EJB
/*  69:    */   private ServicioGenerico<OrganizacionConfiguracion> servicioOrganizacionConfiguracion;
/*  70:    */   
/*  71:    */   private static enum enumUnidad
/*  72:    */   {
/*  73: 89 */     STOCK,  VENTA,  ALMACENAMIENTO;
/*  74:    */     
/*  75:    */     private enumUnidad() {}
/*  76:    */   }
/*  77:    */   
/*  78: 92 */   private enumUnidad unidad = enumUnidad.STOCK;
/*  79:    */   private List<SelectItem> listaUnidad;
/*  80: 96 */   private Date fechaHasta = new Date();
/*  81:    */   private Bodega bodega;
/*  82:    */   private List<Bodega> listaBodega;
/*  83:    */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  84:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  85:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  86:    */   private Producto productoSeleccionado;
/*  87:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  88:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  89:    */   private Lote lote;
/*  90:    */   private Producto producto;
/*  91:    */   private Atributo atributoOrdenFabricacion;
/*  92:    */   private Atributo atributo1;
/*  93:    */   private Atributo atributo2;
/*  94:    */   private Atributo atributo3;
/*  95:    */   private Atributo atributo4;
/*  96:    */   private Atributo atributo5;
/*  97:    */   private Atributo atributo6;
/*  98:    */   private Atributo atributo7;
/*  99:    */   private Atributo atributo8;
/* 100:    */   private Atributo atributo9;
/* 101:    */   private ValorAtributo valorAtributoOF;
/* 102:    */   private ValorAtributo valorAtributo1;
/* 103:    */   private ValorAtributo valorAtributo2;
/* 104:    */   private ValorAtributo valorAtributo3;
/* 105:    */   private ValorAtributo valorAtributo4;
/* 106:    */   private ValorAtributo valorAtributo5;
/* 107:    */   private ValorAtributo valorAtributo6;
/* 108:    */   private ValorAtributo valorAtributo7;
/* 109:    */   private ValorAtributo valorAtributo8;
/* 110:    */   private ValorAtributo valorAtributo9;
/* 111:133 */   private boolean indicadorProductoLote = true;
/* 112:    */   
/* 113:    */   @PostConstruct
/* 114:    */   public void init()
/* 115:    */   {
/* 116:138 */     if (getOrganizacionConfiguracion().getAtributo1() != null) {
/* 117:139 */       this.atributoOrdenFabricacion = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo1().getId());
/* 118:    */     }
/* 119:141 */     if (getOrganizacionConfiguracion().getAtributo2() != null) {
/* 120:142 */       this.atributo1 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo2().getId());
/* 121:    */     }
/* 122:144 */     if (getOrganizacionConfiguracion().getAtributo3() != null) {
/* 123:145 */       this.atributo2 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo3().getId());
/* 124:    */     }
/* 125:147 */     if (getOrganizacionConfiguracion().getAtributo4() != null) {
/* 126:148 */       this.atributo3 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo4().getId());
/* 127:    */     }
/* 128:150 */     if (getOrganizacionConfiguracion().getAtributo5() != null) {
/* 129:151 */       this.atributo4 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo5().getId());
/* 130:    */     }
/* 131:153 */     if (getOrganizacionConfiguracion().getAtributo6() != null) {
/* 132:154 */       this.atributo5 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo6().getId());
/* 133:    */     }
/* 134:156 */     if (getOrganizacionConfiguracion().getAtributo7() != null) {
/* 135:157 */       this.atributo6 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo7().getId());
/* 136:    */     }
/* 137:159 */     if (getOrganizacionConfiguracion().getAtributo8() != null) {
/* 138:160 */       this.atributo7 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo8().getId());
/* 139:    */     }
/* 140:162 */     if (getOrganizacionConfiguracion().getAtributo9() != null) {
/* 141:163 */       this.atributo8 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo9().getId());
/* 142:    */     }
/* 143:165 */     if (getOrganizacionConfiguracion().getAtributo10() != null) {
/* 144:166 */       this.atributo9 = this.servicioAtributo.cargarDetalle(getOrganizacionConfiguracion().getAtributo10().getId());
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected JRDataSource getJRDataSource()
/* 149:    */   {
/* 150:176 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 151:177 */     JRDataSource ds = null;
/* 152:    */     try
/* 153:    */     {
/* 154:180 */       Map<String, ReporteSaldoProducto> hmReporte = new TreeMap();
/* 155:181 */       int numeroAtributos = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/* 156:    */       
/* 157:183 */       List<ReporteSaldoProducto> listaReporteSaldoProducto = this.servicioReporteStockValorado.getReporteSaldoProductoPorLote(null, this.bodega, this.fechaHasta, null, "", 
/* 158:184 */         AppUtil.getOrganizacion().getId(), this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, this.indicadorProductoLote, null, this.producto, 
/* 159:185 */         getListaValoresAtributo(), numeroAtributos);
/* 160:187 */       for (ReporteSaldoProducto rSaldoProducto : listaReporteSaldoProducto)
/* 161:    */       {
/* 162:189 */         String clave = rSaldoProducto.getIdBodega() + ":" + rSaldoProducto.getIdSubcategoriaProducto() + ":" + rSaldoProducto.getIdProducto() + ":" + rSaldoProducto.getIdLote();
/* 163:201 */         if (rSaldoProducto.getSaldo().compareTo(BigDecimal.ZERO) != 0)
/* 164:    */         {
/* 165:    */           try
/* 166:    */           {
/* 167:207 */             Unidad unidadStock = new Unidad(rSaldoProducto.getIdUnidad().intValue(), rSaldoProducto.getCodigoUnidad(), rSaldoProducto.getNombreUnidad());
/* 168:208 */             Unidad unidadConversion = unidadStock;
/* 169:210 */             if (this.unidad.equals(enumUnidad.ALMACENAMIENTO)) {
/* 170:214 */               unidadConversion = rSaldoProducto.getIdUnidadAlmacenamiento() == null ? unidadStock : new Unidad(rSaldoProducto.getIdUnidadAlmacenamiento().intValue(), rSaldoProducto.getCodigoUnidadAlmacenamiento(), rSaldoProducto.getNombreUnidadAlmacenamiento());
/* 171:216 */             } else if (this.unidad.equals(enumUnidad.VENTA)) {
/* 172:219 */               unidadConversion = rSaldoProducto.getIdUnidadVenta() == null ? unidadStock : new Unidad(rSaldoProducto.getIdUnidadVenta().intValue(), rSaldoProducto.getCodigoUnidadVenta(), rSaldoProducto.getNombreUnidadVenta());
/* 173:    */             }
/* 174:222 */             BigDecimal cantidad = this.servicioProducto.convierteUnidad(unidadStock, unidadConversion, rSaldoProducto.getIdProducto().intValue(), rSaldoProducto
/* 175:223 */               .getSaldo());
/* 176:    */             
/* 177:    */ 
/* 178:226 */             rSaldoProducto.setSaldo(cantidad);
/* 179:227 */             rSaldoProducto.setCodigoUnidad(unidadConversion.getCodigo());
/* 180:    */           }
/* 181:    */           catch (ExcepcionAS2 e)
/* 182:    */           {
/* 183:230 */             e.printStackTrace();
/* 184:    */           }
/* 185:233 */           hmReporte.put(clave, rSaldoProducto);
/* 186:    */         }
/* 187:    */       }
/* 188:238 */       for (ReporteSaldoProducto p : hmReporte.values())
/* 189:    */       {
/* 190:239 */         Object[] datos = new Object[20];
/* 191:240 */         datos[0] = p.getCodigoProducto();
/* 192:241 */         datos[1] = p.getNombreProducto();
/* 193:242 */         datos[2] = p.getCodigoBodega();
/* 194:243 */         datos[3] = p.getNombreBodega();
/* 195:244 */         datos[4] = p.getSaldo();
/* 196:245 */         datos[5] = BigDecimal.ZERO;
/* 197:246 */         datos[6] = p.getCodigoUnidad();
/* 198:247 */         datos[7] = p.getCodigoLote();
/* 199:248 */         datos[8] = p.getCodigoSubcategoriaProducto();
/* 200:249 */         datos[9] = p.getNombreSubcategoriaProducto();
/* 201:250 */         datos[10] = p.getValorAtributo1();
/* 202:251 */         datos[11] = p.getValorAtributo2();
/* 203:252 */         datos[12] = p.getValorAtributo3();
/* 204:253 */         datos[13] = p.getValorAtributo4();
/* 205:254 */         datos[14] = p.getValorAtributo5();
/* 206:255 */         datos[15] = p.getValorAtributo6();
/* 207:256 */         datos[16] = p.getValorAtributo7();
/* 208:257 */         datos[17] = p.getValorAtributo8();
/* 209:258 */         datos[18] = p.getValorAtributo9();
/* 210:259 */         datos[19] = p.getValorAtributo10();
/* 211:    */         
/* 212:261 */         listaDatosReporte.add(datos);
/* 213:    */       }
/* 214:264 */       String[] fields = { "f_codigoProducto", "f_nombreProducto", "f_codigoBodega", "f_nombreBodega", "f_saldoProducto", "f_costoProducto", "f_codigoUnidad", "f_codigoLote", "f_codigoSubcategoriaProducto", "f_nombreSubcategoriaProducto", "f_valorAtributo1", "f_valorAtributo2", "f_valorAtributo3", "f_valorAtributo4", "f_valorAtributo5", "f_valorAtributo6", "f_valorAtributo7", "f_valorAtributo8", "f_valorAtributo9", "f_valorAtributo10" };
/* 215:    */       
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:269 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 220:    */     }
/* 221:    */     catch (Exception e)
/* 222:    */     {
/* 223:271 */       e.printStackTrace();
/* 224:    */     }
/* 225:273 */     return ds;
/* 226:    */   }
/* 227:    */   
/* 228:    */   protected String getCompileFileName()
/* 229:    */   {
/* 230:283 */     return "reporteStockValoradoPorLote";
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String execute()
/* 234:    */   {
/* 235:    */     try
/* 236:    */     {
/* 237:295 */       super.prepareReport();
/* 238:    */     }
/* 239:    */     catch (JRException e)
/* 240:    */     {
/* 241:298 */       LOG.info("Error JRException");
/* 242:299 */       e.printStackTrace();
/* 243:300 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 244:    */     }
/* 245:    */     catch (IOException e)
/* 246:    */     {
/* 247:302 */       LOG.info("Error IOException");
/* 248:303 */       e.printStackTrace();
/* 249:304 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 250:    */     }
/* 251:306 */     return "";
/* 252:    */   }
/* 253:    */   
/* 254:    */   protected Map<String, Object> getReportParameters()
/* 255:    */   {
/* 256:317 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 257:318 */     reportParameters.put("ReportTitle", "Stock Valorado Por Lote");
/* 258:319 */     reportParameters.put("usuario", "Usuario:");
/* 259:320 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/* 260:321 */     reportParameters.put("pagina", "Pagina:");
/* 261:322 */     reportParameters.put("desde", "Desde:");
/* 262:323 */     reportParameters.put("hasta", "Hasta:");
/* 263:324 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 264:325 */     reportParameters.put("bodega", this.bodega != null ? this.bodega.getNombre() : "");
/* 265:326 */     reportParameters.put("p_categoriaProducto", this.categoriaProductoSeleccionado != null ? this.categoriaProductoSeleccionado.getNombre() : "Todos");
/* 266:327 */     reportParameters
/* 267:328 */       .put("p_subcategoriaProducto", this.subcategoriaProductoSeleccionado != null ? this.subcategoriaProductoSeleccionado.getNombre() : "Todos");
/* 268:329 */     reportParameters.put("p_producto", this.productoSeleccionado != null ? this.productoSeleccionado.getNombre() : "Todos");
/* 269:330 */     reportParameters.put("atributo1", getOrganizacionConfiguracion().getAtributo1() != null ? getOrganizacionConfiguracion().getAtributo1().getNombre() : "");
/* 270:331 */     reportParameters.put("atributo2", getOrganizacionConfiguracion().getAtributo2() != null ? getOrganizacionConfiguracion().getAtributo2().getNombre() : "");
/* 271:332 */     reportParameters.put("atributo3", getOrganizacionConfiguracion().getAtributo3() != null ? getOrganizacionConfiguracion().getAtributo3().getNombre() : "");
/* 272:333 */     reportParameters.put("atributo4", getOrganizacionConfiguracion().getAtributo4() != null ? getOrganizacionConfiguracion().getAtributo4().getNombre() : "");
/* 273:334 */     reportParameters.put("atributo5", getOrganizacionConfiguracion().getAtributo5() != null ? getOrganizacionConfiguracion().getAtributo5().getNombre() : "");
/* 274:335 */     reportParameters.put("atributo6", getOrganizacionConfiguracion().getAtributo6() != null ? getOrganizacionConfiguracion().getAtributo6().getNombre() : "");
/* 275:336 */     reportParameters.put("atributo7", getOrganizacionConfiguracion().getAtributo7() != null ? getOrganizacionConfiguracion().getAtributo7().getNombre() : "");
/* 276:337 */     reportParameters.put("atributo8", getOrganizacionConfiguracion().getAtributo8() != null ? getOrganizacionConfiguracion().getAtributo8().getNombre() : "");
/* 277:338 */     reportParameters.put("atributo9", getOrganizacionConfiguracion().getAtributo9() != null ? getOrganizacionConfiguracion().getAtributo9().getNombre() : "");
/* 278:339 */     reportParameters.put("atributo10", getOrganizacionConfiguracion().getAtributo10() != null ? getOrganizacionConfiguracion().getAtributo10().getNombre() : "");
/* 279:340 */     return reportParameters;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void cargarListaSubcategoriaProducto()
/* 283:    */   {
/* 284:344 */     HashMap<String, String> filters = new HashMap();
/* 285:345 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 286:346 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public List<Lote> autocompletarLotes(String consulta)
/* 290:    */   {
/* 291:356 */     return this.servicioLote.autocompletarLote(consulta);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public List<Producto> autocompletarProductos(String consulta)
/* 295:    */   {
/* 296:366 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public Date getFechaHasta()
/* 300:    */   {
/* 301:375 */     return this.fechaHasta;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setFechaHasta(Date fechaHasta)
/* 305:    */   {
/* 306:385 */     this.fechaHasta = fechaHasta;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public Bodega getBodega()
/* 310:    */   {
/* 311:394 */     return this.bodega;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setBodega(Bodega bodega)
/* 315:    */   {
/* 316:404 */     this.bodega = bodega;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public List<Bodega> getListaBodega()
/* 320:    */   {
/* 321:413 */     if (this.listaBodega == null) {
/* 322:414 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, null);
/* 323:    */     }
/* 324:416 */     return this.listaBodega;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 328:    */   {
/* 329:426 */     this.listaBodega = listaBodega;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public enumUnidad getUnidad()
/* 333:    */   {
/* 334:435 */     return this.unidad;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setUnidad(enumUnidad unidad)
/* 338:    */   {
/* 339:445 */     this.unidad = unidad;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public List<SelectItem> getListaUnidad()
/* 343:    */   {
/* 344:454 */     if (this.listaUnidad == null)
/* 345:    */     {
/* 346:455 */       this.listaUnidad = new ArrayList();
/* 347:456 */       for (enumUnidad unidad : enumUnidad.values())
/* 348:    */       {
/* 349:457 */         SelectItem item = new SelectItem(unidad, unidad.name());
/* 350:458 */         this.listaUnidad.add(item);
/* 351:    */       }
/* 352:    */     }
/* 353:461 */     return this.listaUnidad;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setListaUnidad(List<SelectItem> listaUnidad)
/* 357:    */   {
/* 358:471 */     this.listaUnidad = listaUnidad;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public Producto getProductoSeleccionado()
/* 362:    */   {
/* 363:478 */     return this.productoSeleccionado;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setProductoSeleccionado(Producto productoSeleccionado)
/* 367:    */   {
/* 368:486 */     this.productoSeleccionado = productoSeleccionado;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 372:    */   {
/* 373:493 */     if (this.categoriaProductoSeleccionado == null) {
/* 374:494 */       this.categoriaProductoSeleccionado = new CategoriaProducto();
/* 375:    */     }
/* 376:496 */     return this.categoriaProductoSeleccionado;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 380:    */   {
/* 381:504 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 385:    */   {
/* 386:511 */     return this.subcategoriaProductoSeleccionado;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 390:    */   {
/* 391:519 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 395:    */   {
/* 396:527 */     return this.listaSubcategoriaProductos;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 400:    */   {
/* 401:535 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 405:    */   {
/* 406:542 */     HashMap<String, String> filters = new HashMap();
/* 407:543 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 408:544 */     if (this.listaCategoriaProductos == null) {
/* 409:545 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 410:    */     }
/* 411:547 */     return this.listaCategoriaProductos;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 415:    */   {
/* 416:555 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public Lote getLote()
/* 420:    */   {
/* 421:564 */     return this.lote;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setLote(Lote lote)
/* 425:    */   {
/* 426:574 */     this.lote = lote;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public boolean isIndicadorProductoLote()
/* 430:    */   {
/* 431:583 */     return this.indicadorProductoLote;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setIndicadorProductoLote(boolean indicadorProductoLote)
/* 435:    */   {
/* 436:593 */     this.indicadorProductoLote = indicadorProductoLote;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public Producto getProducto()
/* 440:    */   {
/* 441:602 */     return this.producto;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void setProducto(Producto producto)
/* 445:    */   {
/* 446:612 */     this.producto = producto;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public Atributo getAtributoOrdenFabricacion()
/* 450:    */   {
/* 451:615 */     return this.atributoOrdenFabricacion;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setAtributoOrdenFabricacion(Atributo atributoOrdenFabricacion)
/* 455:    */   {
/* 456:618 */     this.atributoOrdenFabricacion = atributoOrdenFabricacion;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public Atributo getAtributo1()
/* 460:    */   {
/* 461:621 */     return this.atributo1;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setAtributo1(Atributo atributo1)
/* 465:    */   {
/* 466:624 */     this.atributo1 = atributo1;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public Atributo getAtributo2()
/* 470:    */   {
/* 471:627 */     return this.atributo2;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setAtributo2(Atributo atributo2)
/* 475:    */   {
/* 476:630 */     this.atributo2 = atributo2;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public Atributo getAtributo3()
/* 480:    */   {
/* 481:633 */     return this.atributo3;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setAtributo3(Atributo atributo3)
/* 485:    */   {
/* 486:636 */     this.atributo3 = atributo3;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public Atributo getAtributo4()
/* 490:    */   {
/* 491:639 */     return this.atributo4;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setAtributo4(Atributo atributo4)
/* 495:    */   {
/* 496:642 */     this.atributo4 = atributo4;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public Atributo getAtributo5()
/* 500:    */   {
/* 501:645 */     return this.atributo5;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void setAtributo5(Atributo atributo5)
/* 505:    */   {
/* 506:648 */     this.atributo5 = atributo5;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public Atributo getAtributo6()
/* 510:    */   {
/* 511:651 */     return this.atributo6;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void setAtributo6(Atributo atributo6)
/* 515:    */   {
/* 516:654 */     this.atributo6 = atributo6;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public Atributo getAtributo7()
/* 520:    */   {
/* 521:657 */     return this.atributo7;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public void setAtributo7(Atributo atributo7)
/* 525:    */   {
/* 526:660 */     this.atributo7 = atributo7;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public Atributo getAtributo8()
/* 530:    */   {
/* 531:663 */     return this.atributo8;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public void setAtributo8(Atributo atributo8)
/* 535:    */   {
/* 536:666 */     this.atributo8 = atributo8;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public Atributo getAtributo9()
/* 540:    */   {
/* 541:669 */     return this.atributo9;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setAtributo9(Atributo atributo9)
/* 545:    */   {
/* 546:672 */     this.atributo9 = atributo9;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public ValorAtributo getValorAtributoOF()
/* 550:    */   {
/* 551:675 */     return this.valorAtributoOF;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void setValorAtributoOF(ValorAtributo valorAtributoOF)
/* 555:    */   {
/* 556:678 */     this.valorAtributoOF = valorAtributoOF;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public ValorAtributo getValorAtributo1()
/* 560:    */   {
/* 561:681 */     return this.valorAtributo1;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public void setValorAtributo1(ValorAtributo valorAtributo1)
/* 565:    */   {
/* 566:684 */     this.valorAtributo1 = valorAtributo1;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public ValorAtributo getValorAtributo2()
/* 570:    */   {
/* 571:687 */     return this.valorAtributo2;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public void setValorAtributo2(ValorAtributo valorAtributo2)
/* 575:    */   {
/* 576:690 */     this.valorAtributo2 = valorAtributo2;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public ValorAtributo getValorAtributo3()
/* 580:    */   {
/* 581:693 */     return this.valorAtributo3;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public void setValorAtributo3(ValorAtributo valorAtributo3)
/* 585:    */   {
/* 586:696 */     this.valorAtributo3 = valorAtributo3;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public ValorAtributo getValorAtributo4()
/* 590:    */   {
/* 591:699 */     return this.valorAtributo4;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public void setValorAtributo4(ValorAtributo valorAtributo4)
/* 595:    */   {
/* 596:702 */     this.valorAtributo4 = valorAtributo4;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public ValorAtributo getValorAtributo5()
/* 600:    */   {
/* 601:705 */     return this.valorAtributo5;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public void setValorAtributo5(ValorAtributo valorAtributo5)
/* 605:    */   {
/* 606:708 */     this.valorAtributo5 = valorAtributo5;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public ValorAtributo getValorAtributo6()
/* 610:    */   {
/* 611:711 */     return this.valorAtributo6;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public void setValorAtributo6(ValorAtributo valorAtributo6)
/* 615:    */   {
/* 616:714 */     this.valorAtributo6 = valorAtributo6;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public ValorAtributo getValorAtributo7()
/* 620:    */   {
/* 621:717 */     return this.valorAtributo7;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public void setValorAtributo7(ValorAtributo valorAtributo7)
/* 625:    */   {
/* 626:720 */     this.valorAtributo7 = valorAtributo7;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public ValorAtributo getValorAtributo8()
/* 630:    */   {
/* 631:723 */     return this.valorAtributo8;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public void setValorAtributo8(ValorAtributo valorAtributo8)
/* 635:    */   {
/* 636:726 */     this.valorAtributo8 = valorAtributo8;
/* 637:    */   }
/* 638:    */   
/* 639:    */   public ValorAtributo getValorAtributo9()
/* 640:    */   {
/* 641:729 */     return this.valorAtributo9;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public void setValorAtributo9(ValorAtributo valorAtributo9)
/* 645:    */   {
/* 646:732 */     this.valorAtributo9 = valorAtributo9;
/* 647:    */   }
/* 648:    */   
/* 649:    */   private List<ValorAtributo> getListaValoresAtributo()
/* 650:    */   {
/* 651:736 */     List<ValorAtributo> list = new ArrayList();
/* 652:737 */     if (this.valorAtributoOF != null) {
/* 653:738 */       list.add(this.valorAtributoOF);
/* 654:    */     }
/* 655:740 */     if (this.valorAtributo1 != null) {
/* 656:741 */       list.add(this.valorAtributo1);
/* 657:    */     }
/* 658:743 */     if (this.valorAtributo2 != null) {
/* 659:744 */       list.add(this.valorAtributo2);
/* 660:    */     }
/* 661:746 */     if (this.valorAtributo3 != null) {
/* 662:747 */       list.add(this.valorAtributo3);
/* 663:    */     }
/* 664:749 */     if (this.valorAtributo4 != null) {
/* 665:750 */       list.add(this.valorAtributo4);
/* 666:    */     }
/* 667:752 */     if (this.valorAtributo5 != null) {
/* 668:753 */       list.add(this.valorAtributo5);
/* 669:    */     }
/* 670:755 */     if (this.valorAtributo6 != null) {
/* 671:756 */       list.add(this.valorAtributo6);
/* 672:    */     }
/* 673:758 */     if (this.valorAtributo7 != null) {
/* 674:759 */       list.add(this.valorAtributo7);
/* 675:    */     }
/* 676:761 */     if (this.valorAtributo8 != null) {
/* 677:762 */       list.add(this.valorAtributo8);
/* 678:    */     }
/* 679:764 */     if (this.valorAtributo9 != null) {
/* 680:765 */       list.add(this.valorAtributo9);
/* 681:    */     }
/* 682:767 */     return list;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 686:    */   {
/* 687:771 */     if (this.organizacionConfiguracion == null)
/* 688:    */     {
/* 689:772 */       int numeroAtributos = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/* 690:773 */       List<String> listaCampos = new ArrayList();
/* 691:774 */       for (int i = 1; i <= numeroAtributos; i++) {
/* 692:775 */         listaCampos.add("atributo" + i);
/* 693:    */       }
/* 694:777 */       this.organizacionConfiguracion = ((OrganizacionConfiguracion)this.servicioOrganizacionConfiguracion.cargarDetalle(OrganizacionConfiguracion.class, 
/* 695:778 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getIdOrganizacionConfiguracion(), listaCampos));
/* 696:    */     }
/* 697:780 */     return this.organizacionConfiguracion;
/* 698:    */   }
/* 699:    */   
/* 700:    */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 701:    */   {
/* 702:784 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 703:    */   }
/* 704:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteStockValoradoPorLoteBean
 * JD-Core Version:    0.7.0.1
 */