/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.FactorConversion;
/*   4:    */ import com.asinfo.as2.clases.ReporteInventarioProducto;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.Lote;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.Unidad;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  17:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  18:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteInventarioProducto;
/*  19:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValorado;
/*  20:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  21:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  24:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  25:    */ import java.io.IOException;
/*  26:    */ import java.math.BigDecimal;
/*  27:    */ import java.math.RoundingMode;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Date;
/*  30:    */ import java.util.HashMap;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import java.util.TreeMap;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ViewScoped;
/*  37:    */ import javax.faces.model.SelectItem;
/*  38:    */ import javax.persistence.Temporal;
/*  39:    */ import javax.persistence.TemporalType;
/*  40:    */ import javax.validation.constraints.NotNull;
/*  41:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  42:    */ import net.sf.jasperreports.engine.JRException;
/*  43:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  44:    */ import org.primefaces.component.datatable.DataTable;
/*  45:    */ 
/*  46:    */ @ManagedBean
/*  47:    */ @ViewScoped
/*  48:    */ public class ReporteInventarioProductoBean
/*  49:    */   extends AbstractBaseReportBean
/*  50:    */ {
/*  51:    */   private static final long serialVersionUID = -657564978049733933L;
/*  52:    */   @EJB
/*  53:    */   private ServicioReporteInventarioProducto servicioReporteInventarioProducto;
/*  54:    */   @EJB
/*  55:    */   private ServicioBodega servicioBodega;
/*  56:    */   @EJB
/*  57:    */   private ServicioProducto servicioProducto;
/*  58:    */   @EJB
/*  59:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  60:    */   @EJB
/*  61:    */   private ServicioLote servicioLote;
/*  62:    */   @EJB
/*  63:    */   private ServicioReporteStockValorado servicioReporteStockValorado;
/*  64:    */   @EJB
/*  65:    */   private ServicioGenerico<OrganizacionConfiguracion> servicioOrganizacionConfiguracion;
/*  66:    */   private Producto producto;
/*  67:    */   private Bodega bodega;
/*  68:    */   private List<Bodega> listaBodega;
/*  69:    */   private Lote lote;
/*  70:    */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  71:    */   private List<ReporteInventarioProducto> listaReporteInventarioProducto;
/*  72:    */   private List<ReporteInventarioProducto> listaReporteInventarioProductoFiltrado;
/*  73: 99 */   private BigDecimal totalIngreso = BigDecimal.ZERO;
/*  74:100 */   private BigDecimal totalEgreso = BigDecimal.ZERO;
/*  75:    */   private String nombreUnidad;
/*  76:    */   @Temporal(TemporalType.DATE)
/*  77:    */   @NotNull
/*  78:    */   private Date fechaDesde;
/*  79:    */   @Temporal(TemporalType.DATE)
/*  80:    */   @NotNull
/*  81:107 */   private Date fechaHasta = new Date();
/*  82:    */   private ReporteInventarioProducto reporteInventarioProducto;
/*  83:    */   
/*  84:    */   private static enum enumUnidad
/*  85:    */   {
/*  86:115 */     STOCK,  VENTA,  ALMACENAMIENTO;
/*  87:    */     
/*  88:    */     private enumUnidad() {}
/*  89:    */   }
/*  90:    */   
/*  91:118 */   private enumUnidad unidad = enumUnidad.STOCK;
/*  92:    */   private List<SelectItem> listaUnidad;
/*  93:    */   private DataTable dtIventarioProducto;
/*  94:    */   
/*  95:    */   public String procesar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:132 */       Map<String, Integer> hmReporte = new TreeMap();
/* 100:134 */       if ((this.producto != null) || (this.lote != null))
/* 101:    */       {
/* 102:135 */         if (this.lote != null) {
/* 103:136 */           this.producto = this.lote.getProducto();
/* 104:    */         }
/* 105:139 */         List<Producto> listaProducto = new ArrayList();
/* 106:140 */         listaProducto.add(this.producto);
/* 107:    */         
/* 108:142 */         this.producto = this.servicioProducto.cargaDetalle(getProducto().getId());
/* 109:    */         
/* 110:144 */         String clave = "" + this.producto.getIdProducto();
/* 111:145 */         hmReporte.put(clave, Integer.valueOf(this.producto.getIdProducto()));
/* 112:    */         
/* 113:147 */         this.listaReporteInventarioProducto = this.servicioReporteInventarioProducto.obtenerMovimientosInventarioProducto(AppUtil.getOrganizacion()
/* 114:148 */           .getId(), listaProducto, this.bodega, this.fechaDesde, this.fechaHasta, getUnidad().ordinal(), this.lote, getListaBodega(), AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 115:    */         
/* 116:    */ 
/* 117:151 */         saldoInicial = BigDecimal.ZERO;
/* 118:152 */         costoInicial = BigDecimal.ZERO;
/* 119:    */         
/* 120:154 */         factorConversion = null;
/* 121:155 */         strMensaje = "";
/* 122:156 */         if (this.unidad.equals(enumUnidad.VENTA))
/* 123:    */         {
/* 124:158 */           factorConversion = this.servicioUnidadConversion.obtenerFactorConversion(getProducto().getId(), getProducto()
/* 125:159 */             .getSubcategoriaProducto().getIdSubcategoriaProducto(), getProducto().getUnidad().getId(), getProducto().getUnidadVenta()
/* 126:160 */             .getId());
/* 127:    */           
/* 128:    */ 
/* 129:163 */           strMensaje = this.producto.getCodigo() + "-" + this.producto.getNombre() + " (" + this.producto.getUnidad().getNombre() + "-" + getProducto().getUnidadVenta().getNombre() + ")";
/* 130:    */         }
/* 131:165 */         else if (this.unidad.equals(enumUnidad.ALMACENAMIENTO))
/* 132:    */         {
/* 133:167 */           factorConversion = this.servicioUnidadConversion.obtenerFactorConversion(getProducto().getId(), getProducto()
/* 134:168 */             .getSubcategoriaProducto().getIdSubcategoriaProducto(), getProducto().getUnidad().getId(), getProducto()
/* 135:169 */             .getUnidadAlmacenamiento().getId());
/* 136:    */           
/* 137:171 */           strMensaje = this.producto.getCodigo() + "-" + this.producto.getNombre() + " (" + this.producto.getUnidad().getNombre() + "-" + getProducto().getUnidadAlmacenamiento().getNombre() + ")";
/* 138:    */         }
/* 139:175 */         for (ReporteInventarioProducto reporteInventarioProducto : this.listaReporteInventarioProducto)
/* 140:    */         {
/* 141:177 */           reporteInventarioProducto.setProducto(this.producto);
/* 142:179 */           if (!this.unidad.equals(enumUnidad.STOCK)) {
/* 143:180 */             if (factorConversion != null)
/* 144:    */             {
/* 145:181 */               if (factorConversion.isIndicadorInverso()) {
/* 146:182 */                 reporteInventarioProducto.setCantidad(reporteInventarioProducto.getCantidad().divide(factorConversion.getFactor(), 4, RoundingMode.HALF_UP));
/* 147:    */               } else {
/* 148:186 */                 reporteInventarioProducto.setCantidad(reporteInventarioProducto.getCantidad().multiply(factorConversion.getFactor()).setScale(4, RoundingMode.HALF_UP));
/* 149:    */               }
/* 150:    */             }
/* 151:    */             else {
/* 152:191 */               throw new ExcepcionAS2Inventario("msg_error_unidad_conversion", strMensaje);
/* 153:    */             }
/* 154:    */           }
/* 155:195 */           saldoInicial = saldoInicial.add(reporteInventarioProducto.getCantidad().multiply(new BigDecimal(reporteInventarioProducto
/* 156:196 */             .getOperacion().intValue())));
/* 157:197 */           costoInicial = costoInicial.add(reporteInventarioProducto.getCosto().multiply(new BigDecimal(reporteInventarioProducto
/* 158:198 */             .getOperacion().intValue())));
/* 159:    */           
/* 160:200 */           reporteInventarioProducto.setCantidadTotal(saldoInicial.setScale(4, RoundingMode.HALF_UP));
/* 161:201 */           reporteInventarioProducto.setCostoTotal(costoInicial.setScale(4, RoundingMode.HALF_UP));
/* 162:    */         }
/* 163:    */       }
/* 164:    */     }
/* 165:    */     catch (ExcepcionAS2 e)
/* 166:    */     {
/* 167:    */       BigDecimal saldoInicial;
/* 168:    */       BigDecimal costoInicial;
/* 169:    */       FactorConversion factorConversion;
/* 170:    */       String strMensaje;
/* 171:207 */       e.printStackTrace();
/* 172:    */     }
/* 173:210 */     return "";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void actualizarUnidad()
/* 177:    */   {
/* 178:217 */     if (getProducto() != null) {
/* 179:219 */       if (this.unidad.equals(enumUnidad.ALMACENAMIENTO))
/* 180:    */       {
/* 181:220 */         if (getProducto().getUnidadAlmacenamiento() != null) {
/* 182:221 */           this.nombreUnidad = getProducto().getUnidadAlmacenamiento().getNombre();
/* 183:    */         }
/* 184:    */       }
/* 185:224 */       else if (this.unidad.equals(enumUnidad.VENTA))
/* 186:    */       {
/* 187:225 */         if (getProducto().getUnidadVenta() != null) {
/* 188:226 */           this.nombreUnidad = getProducto().getUnidadVenta().getNombre();
/* 189:    */         }
/* 190:    */       }
/* 191:229 */       else if (getProducto().getUnidad() != null) {
/* 192:230 */         this.nombreUnidad = getProducto().getUnidad().getNombre();
/* 193:    */       }
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public List<Lote> autocompletarLotes(String consulta)
/* 198:    */   {
/* 199:237 */     List<Lote> lista = new ArrayList();
/* 200:238 */     String sortField = "codigo";
/* 201:239 */     Map<String, String> filters = new HashMap();
/* 202:240 */     filters.put("codigo", consulta.trim());
/* 203:241 */     lista = this.servicioLote.obtenerListaCombo(sortField, true, filters);
/* 204:242 */     return lista;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void cargarProducto(Producto producto)
/* 208:    */   {
/* 209:252 */     this.producto = producto;
/* 210:253 */     actualizarUnidad();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Date getFechaDesde()
/* 214:    */   {
/* 215:262 */     if (this.fechaDesde == null) {
/* 216:263 */       this.fechaDesde = FuncionesUtiles.getFechaInicioMes(new Date());
/* 217:    */     }
/* 218:265 */     return this.fechaDesde;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setFechaDesde(Date fechaDesde)
/* 222:    */   {
/* 223:275 */     this.fechaDesde = fechaDesde;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Date getFechaHasta()
/* 227:    */   {
/* 228:284 */     return this.fechaHasta;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setFechaHasta(Date fechaHasta)
/* 232:    */   {
/* 233:294 */     this.fechaHasta = fechaHasta;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Producto getProducto()
/* 237:    */   {
/* 238:303 */     return this.producto;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setProducto(Producto producto)
/* 242:    */   {
/* 243:313 */     this.producto = producto;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public DataTable getDtIventarioProducto()
/* 247:    */   {
/* 248:322 */     return this.dtIventarioProducto;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setDtIventarioProducto(DataTable dtIventarioProducto)
/* 252:    */   {
/* 253:332 */     this.dtIventarioProducto = dtIventarioProducto;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public ReporteInventarioProducto getReporteInventarioProducto()
/* 257:    */   {
/* 258:336 */     return this.reporteInventarioProducto;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setReporteInventarioProducto(ReporteInventarioProducto reporteInventarioProducto)
/* 262:    */   {
/* 263:340 */     this.reporteInventarioProducto = reporteInventarioProducto;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Bodega getBodega()
/* 267:    */   {
/* 268:349 */     return this.bodega;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setBodega(Bodega bodega)
/* 272:    */   {
/* 273:359 */     this.bodega = bodega;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<Bodega> getListaBodega()
/* 277:    */   {
/* 278:368 */     if (this.listaBodega == null) {
/* 279:369 */       this.listaBodega = this.servicioBodega.obtenerListaComboPorUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario(), AppUtil.getOrganizacion().getId());
/* 280:    */     }
/* 281:371 */     return this.listaBodega;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 285:    */   {
/* 286:381 */     this.listaBodega = listaBodega;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public enumUnidad getUnidad()
/* 290:    */   {
/* 291:390 */     return this.unidad;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setUnidad(enumUnidad unidad)
/* 295:    */   {
/* 296:400 */     this.unidad = unidad;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<SelectItem> getListaUnidad()
/* 300:    */   {
/* 301:409 */     if (this.listaUnidad == null)
/* 302:    */     {
/* 303:410 */       this.listaUnidad = new ArrayList();
/* 304:411 */       for (enumUnidad unidad : enumUnidad.values())
/* 305:    */       {
/* 306:412 */         SelectItem item = new SelectItem(unidad, unidad.name());
/* 307:413 */         this.listaUnidad.add(item);
/* 308:    */       }
/* 309:    */     }
/* 310:416 */     return this.listaUnidad;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setListaUnidad(List<SelectItem> listaUnidad)
/* 314:    */   {
/* 315:426 */     this.listaUnidad = listaUnidad;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public List<ReporteInventarioProducto> getListaReporteInventarioProducto()
/* 319:    */   {
/* 320:435 */     return this.listaReporteInventarioProducto;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setListaReporteInventarioProducto(List<ReporteInventarioProducto> listaReporteInventarioProducto)
/* 324:    */   {
/* 325:445 */     this.listaReporteInventarioProducto = listaReporteInventarioProducto;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public BigDecimal getTotalIngreso()
/* 329:    */   {
/* 330:454 */     this.totalIngreso = BigDecimal.ZERO;
/* 331:455 */     int numeroDecimales = 5;
/* 332:456 */     List<ReporteInventarioProducto> lista = this.listaReporteInventarioProductoFiltrado == null ? this.listaReporteInventarioProducto : this.listaReporteInventarioProductoFiltrado;
/* 333:458 */     if (lista != null) {
/* 334:459 */       for (ReporteInventarioProducto rip : lista) {
/* 335:460 */         if (rip.getOperacion().intValue() == 1)
/* 336:    */         {
/* 337:461 */           this.totalIngreso = this.totalIngreso.add(rip.getCantidad());
/* 338:462 */           numeroDecimales = rip.getNumeroDecimales().intValue();
/* 339:    */         }
/* 340:    */       }
/* 341:    */     }
/* 342:466 */     return this.totalIngreso.setScale(numeroDecimales, RoundingMode.DOWN);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setTotalIngreso(BigDecimal totalIngreso)
/* 346:    */   {
/* 347:476 */     this.totalIngreso = totalIngreso;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public BigDecimal getTotalEgreso()
/* 351:    */   {
/* 352:485 */     this.totalEgreso = BigDecimal.ZERO;
/* 353:486 */     int numeroDecimales = 5;
/* 354:487 */     List<ReporteInventarioProducto> lista = this.listaReporteInventarioProductoFiltrado == null ? this.listaReporteInventarioProducto : this.listaReporteInventarioProductoFiltrado;
/* 355:489 */     if (lista != null) {
/* 356:490 */       for (ReporteInventarioProducto rip : lista) {
/* 357:491 */         if (rip.getOperacion().intValue() == -1)
/* 358:    */         {
/* 359:492 */           this.totalEgreso = this.totalEgreso.add(rip.getCantidad());
/* 360:493 */           numeroDecimales = rip.getNumeroDecimales().intValue();
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:498 */     return this.totalEgreso.setScale(numeroDecimales, RoundingMode.DOWN);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setTotalEgreso(BigDecimal totalEgreso)
/* 368:    */   {
/* 369:508 */     this.totalEgreso = totalEgreso;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public String getNombreUnidad()
/* 373:    */   {
/* 374:517 */     return this.nombreUnidad;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void setNombreUnidad(String nombreUnidad)
/* 378:    */   {
/* 379:527 */     this.nombreUnidad = nombreUnidad;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public Lote getLote()
/* 383:    */   {
/* 384:531 */     return this.lote;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setLote(Lote lote)
/* 388:    */   {
/* 389:535 */     this.lote = lote;
/* 390:    */   }
/* 391:    */   
/* 392:    */   protected JRDataSource getJRDataSource()
/* 393:    */   {
/* 394:541 */     return new JRBeanCollectionDataSource(this.listaReporteInventarioProducto);
/* 395:    */   }
/* 396:    */   
/* 397:    */   protected String getCompileFileName()
/* 398:    */   {
/* 399:546 */     return "reporteInventario";
/* 400:    */   }
/* 401:    */   
/* 402:    */   public String execute()
/* 403:    */   {
/* 404:    */     try
/* 405:    */     {
/* 406:552 */       super.prepareReport();
/* 407:    */     }
/* 408:    */     catch (JRException e)
/* 409:    */     {
/* 410:555 */       e.printStackTrace();
/* 411:    */     }
/* 412:    */     catch (IOException e)
/* 413:    */     {
/* 414:558 */       e.printStackTrace();
/* 415:    */     }
/* 416:560 */     return "";
/* 417:    */   }
/* 418:    */   
/* 419:    */   public Map<String, Object> getReportParameters()
/* 420:    */   {
/* 421:565 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 422:566 */     reportParameters.put("p_nombreProducto", this.producto.getNombre());
/* 423:567 */     reportParameters.put("p_codigoProducto", this.producto.getNombre());
/* 424:568 */     reportParameters.put("fechaDesde", this.fechaDesde);
/* 425:569 */     reportParameters.put("fechaHasta", this.fechaHasta);
/* 426:570 */     reportParameters.put("ReportTitle", "Inventario Producto");
/* 427:571 */     return reportParameters;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public List<ReporteInventarioProducto> getListaReporteInventarioProductoFiltrado()
/* 431:    */   {
/* 432:575 */     return this.listaReporteInventarioProductoFiltrado;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setListaReporteInventarioProductoFiltrado(List<ReporteInventarioProducto> listaReporteInventarioProductoFiltrado)
/* 436:    */   {
/* 437:579 */     this.listaReporteInventarioProductoFiltrado = listaReporteInventarioProductoFiltrado;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public boolean isVisible()
/* 441:    */   {
/* 442:583 */     if (isCosteoPorBodega())
/* 443:    */     {
/* 444:584 */       if (getBodega() == null) {
/* 445:585 */         return false;
/* 446:    */       }
/* 447:587 */       return true;
/* 448:    */     }
/* 449:590 */     if (getBodega() == null) {
/* 450:591 */       return true;
/* 451:    */     }
/* 452:593 */     return false;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 456:    */   {
/* 457:599 */     if (this.organizacionConfiguracion == null)
/* 458:    */     {
/* 459:600 */       int numeroAtributos = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/* 460:601 */       List<String> listaCampos = new ArrayList();
/* 461:602 */       for (int i = 1; i <= numeroAtributos; i++) {
/* 462:603 */         listaCampos.add("atributo" + i);
/* 463:    */       }
/* 464:605 */       this.organizacionConfiguracion = ((OrganizacionConfiguracion)this.servicioOrganizacionConfiguracion.cargarDetalle(OrganizacionConfiguracion.class, 
/* 465:606 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getIdOrganizacionConfiguracion(), listaCampos));
/* 466:    */     }
/* 467:608 */     return this.organizacionConfiguracion;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 471:    */   {
/* 472:612 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 473:    */   }
/* 474:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteInventarioProductoBean
 * JD-Core Version:    0.7.0.1
 */