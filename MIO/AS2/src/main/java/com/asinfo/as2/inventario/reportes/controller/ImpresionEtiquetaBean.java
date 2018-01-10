/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  10:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  15:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  22:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  23:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  24:    */ import com.asinfo.as2.util.AppUtil;
/*  25:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  26:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  27:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  28:    */ import java.io.IOException;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.HashMap;
/*  31:    */ import java.util.Iterator;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ManagedProperty;
/*  37:    */ import javax.faces.bean.ViewScoped;
/*  38:    */ import javax.faces.model.SelectItem;
/*  39:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  40:    */ import net.sf.jasperreports.engine.JRException;
/*  41:    */ import org.apache.log4j.Logger;
/*  42:    */ 
/*  43:    */ @ManagedBean
/*  44:    */ @ViewScoped
/*  45:    */ public class ImpresionEtiquetaBean
/*  46:    */   extends AbstractBaseReportBean
/*  47:    */ {
/*  48:    */   private static final long serialVersionUID = 1L;
/*  49:    */   @EJB
/*  50:    */   private ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  51:    */   @EJB
/*  52:    */   private ServicioDocumento servicioDocumento;
/*  53:    */   @EJB
/*  54:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*  55:    */   @EJB
/*  56:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  57:    */   @EJB
/*  58:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  59:    */   @EJB
/*  60:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  61:    */   @EJB
/*  62:    */   private ServicioBodega servicioBodega;
/*  63:    */   private List<Documento> listaDocumento;
/*  64:    */   private List<Bodega> listaBodega;
/*  65:    */   private List<SelectItem> listaTipoReporte;
/*  66:    */   private String numeroDocumento;
/*  67:    */   private String numeroLoteDesde;
/*  68:    */   private String numeroLoteHasta;
/*  69:    */   private Documento documento;
/*  70: 84 */   private boolean indicadorPorMovimiento = true;
/*  71:    */   private boolean indicadorImprimirPorUnidad;
/*  72:    */   private boolean saldoMayorCero;
/*  73:    */   @ManagedProperty("#{listaProductoBean}")
/*  74:    */   private ListaProductoBean listaProductoBean;
/*  75:    */   private Producto producto;
/*  76:    */   private CategoriaProducto categoriaProducto;
/*  77:    */   private SubcategoriaProducto subcategoriaProducto;
/*  78:    */   private Bodega bodega;
/*  79:    */   
/*  80:    */   protected JRDataSource getJRDataSource()
/*  81:    */   {
/*  82: 99 */     JRDataSource ds = null;
/*  83:    */     try
/*  84:    */     {
/*  85:102 */       String[] fields = { "f_numero", "f_fecha", "f_codigoLote", "f_nombreComercialProducto", "f_cantidad", "f_nombreUnidad", "f_descripcionProducto", "f_proveedor", "f_nombreProducto", "f_codigoUnidad", "f_codigoProducto", "f_numeroDecimales", "f_precio", "f_fechaCreacion" };
/*  86:    */       
/*  87:    */ 
/*  88:    */ 
/*  89:106 */       List<Object[]> listaDatosReporte = new ArrayList();
/*  90:108 */       if (isIndicadorPorMovimiento())
/*  91:    */       {
/*  92:110 */         int numeroAtributos = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/*  93:    */         
/*  94:112 */         listaDatosReporte = this.servicioRecepcionProveedor.getDatosImpresionEtiquetaLote(AppUtil.getOrganizacion().getId(), this.documento, this.numeroDocumento, this.numeroLoteDesde, this.numeroLoteHasta, 
/*  95:113 */           isIndicadorImprimirPorUnidad(), numeroAtributos);
/*  96:115 */         if (this.documento.getDocumentoBase() == DocumentoBase.INGRESO_PRODUCCION)
/*  97:    */         {
/*  98:117 */           List<String> fieldList = new ArrayList();
/*  99:118 */           fieldList.add("f_numero");
/* 100:119 */           fieldList.add("f_fecha");
/* 101:120 */           fieldList.add("f_codigoLote");
/* 102:121 */           fieldList.add("f_nombreComercialProducto");
/* 103:122 */           fieldList.add("f_cantidad");
/* 104:123 */           fieldList.add("f_nombreUnidad");
/* 105:124 */           fieldList.add("f_descripcionProducto");
/* 106:125 */           fieldList.add("f_proveedor");
/* 107:126 */           fieldList.add("f_nombreProducto");
/* 108:127 */           fieldList.add("f_codigoUnidad");
/* 109:128 */           fieldList.add("f_codigoProducto");
/* 110:129 */           fieldList.add("f_numeroDecimales");
/* 111:130 */           fieldList.add("f_precio");
/* 112:131 */           fieldList.add("f_fechaCreacion");
/* 113:133 */           if (numeroAtributos > 0)
/* 114:    */           {
/* 115:135 */             fieldList.add("f_codigoAtributoOFA");
/* 116:136 */             fieldList.add("f_nombreAtributoOFA");
/* 117:137 */             fieldList.add("f_codigoValorAtributoOFA");
/* 118:138 */             fieldList.add("f_nombreValorAtributoOFA");
/* 119:140 */             for (int i = 1; i <= numeroAtributos - 1; i++)
/* 120:    */             {
/* 121:141 */               fieldList.add("f_codigoAtributo" + i);
/* 122:142 */               fieldList.add("f_nombreAtributo" + i);
/* 123:143 */               fieldList.add("f_codigoValorAtributo" + i);
/* 124:144 */               fieldList.add("f_nombreValorAtributo" + i);
/* 125:    */             }
/* 126:    */           }
/* 127:148 */           fields = (String[])fieldList.toArray(new String[fieldList.size()]);
/* 128:    */         }
/* 129:    */       }
/* 130:    */       else
/* 131:    */       {
/* 132:152 */         listaDatosReporte = this.servicioRecepcionProveedor.getDatosImpresionEtiqueta(AppUtil.getOrganizacion().getId(), this.producto, this.categoriaProducto, this.subcategoriaProducto, this.bodega, 
/* 133:153 */           isSaldoMayorCero());
/* 134:    */       }
/* 135:155 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 136:    */     }
/* 137:    */     catch (Exception e)
/* 138:    */     {
/* 139:157 */       e.printStackTrace();
/* 140:    */     }
/* 141:159 */     return ds;
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected String getCompileFileName()
/* 145:    */   {
/* 146:164 */     if (isIndicadorImprimirPorUnidad()) {
/* 147:165 */       return "reporteEtiquetaPorUnidad";
/* 148:    */     }
/* 149:167 */     return "reporteLoteEtiqueta";
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected Map<String, Object> getReportParameters()
/* 153:    */   {
/* 154:173 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 155:174 */     reportParameters.put("ReportTitle", "Lote Etiqueta");
/* 156:175 */     return reportParameters;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String execute()
/* 160:    */   {
/* 161:    */     try
/* 162:    */     {
/* 163:183 */       super.prepareReport();
/* 164:    */     }
/* 165:    */     catch (JRException e)
/* 166:    */     {
/* 167:185 */       LOG.info("Error JRException");
/* 168:186 */       e.printStackTrace();
/* 169:187 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 170:    */     }
/* 171:    */     catch (IOException e)
/* 172:    */     {
/* 173:189 */       LOG.info("Error IOException");
/* 174:190 */       e.printStackTrace();
/* 175:191 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 176:    */     }
/* 177:194 */     return "";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<String> autocompletarNumeroMovimiento(String cadena)
/* 181:    */   {
/* 182:199 */     List<String> lista = new ArrayList();
/* 183:    */     
/* 184:201 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 185:202 */     HashMap<String, String> filters = new HashMap();
/* 186:204 */     if (getDocumento() != null)
/* 187:    */     {
/* 188:    */       Iterator localIterator;
/* 189:    */       RecepcionProveedor recepcionProveedor;
/* 190:    */       MovimientoInventario movimientoInventario;
/* 191:    */       MovimientoInventario movimientoInventario;
/* 192:    */       FacturaCliente facturaCliente;
/* 193:205 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[getDocumento().getDocumentoBase().ordinal()])
/* 194:    */       {
/* 195:    */       case 1: 
/* 196:207 */         filters = new HashMap();
/* 197:208 */         filters.put("idOrganizacion", "=" + idOrganizacion);
/* 198:209 */         filters.put("numero", "" + cadena);
/* 199:210 */         filters.put("documento.idDocumento", "" + getDocumento().getId());
/* 200:211 */         List<RecepcionProveedor> listaRecepcionProveedor = this.servicioRecepcionProveedor.obtenerListaCombo("numero", true, filters);
/* 201:212 */         for (localIterator = listaRecepcionProveedor.iterator(); localIterator.hasNext();)
/* 202:    */         {
/* 203:212 */           recepcionProveedor = (RecepcionProveedor)localIterator.next();
/* 204:213 */           lista.add(recepcionProveedor.getNumero());
/* 205:    */         }
/* 206:215 */         break;
/* 207:    */       case 2: 
/* 208:217 */         filters = new HashMap();
/* 209:218 */         filters.put("idOrganizacion", "=" + idOrganizacion);
/* 210:219 */         filters.put("numero", "" + cadena);
/* 211:220 */         filters.put("documento.idDocumento", "" + getDocumento().getId());
/* 212:221 */         Object listaMovimientoInventario = this.servicioMovimientoInventario.obtenerListaCombo("numero", true, filters);
/* 213:222 */         for (recepcionProveedor = ((List)listaMovimientoInventario).iterator(); recepcionProveedor.hasNext();)
/* 214:    */         {
/* 215:222 */           movimientoInventario = (MovimientoInventario)recepcionProveedor.next();
/* 216:223 */           lista.add(movimientoInventario.getNumero());
/* 217:    */         }
/* 218:225 */         break;
/* 219:    */       case 3: 
/* 220:227 */         filters = new HashMap();
/* 221:228 */         filters.put("idOrganizacion", "=" + idOrganizacion);
/* 222:229 */         filters.put("numero", "" + cadena);
/* 223:230 */         filters.put("documento.idDocumento", "" + getDocumento().getId());
/* 224:231 */         List<MovimientoInventario> listaMovimientoInventarioTransferencia = this.servicioMovimientoInventario.obtenerListaCombo("numero", true, filters);
/* 225:233 */         for (movimientoInventario = listaMovimientoInventarioTransferencia.iterator(); movimientoInventario.hasNext();)
/* 226:    */         {
/* 227:233 */           movimientoInventario = (MovimientoInventario)movimientoInventario.next();
/* 228:234 */           lista.add(movimientoInventario.getNumero());
/* 229:    */         }
/* 230:236 */         break;
/* 231:    */       case 4: 
/* 232:238 */         filters = new HashMap();
/* 233:239 */         filters.put("idOrganizacion", "=" + idOrganizacion);
/* 234:240 */         filters.put("numero", "" + cadena);
/* 235:241 */         filters.put("documento.idDocumento", "" + getDocumento().getId());
/* 236:242 */         List<FacturaCliente> listaFacturaCliente = this.servicioFacturaCliente.obtenerListaCombo("numero", true, filters);
/* 237:243 */         for (movimientoInventario = listaFacturaCliente.iterator(); movimientoInventario.hasNext();)
/* 238:    */         {
/* 239:243 */           facturaCliente = (FacturaCliente)movimientoInventario.next();
/* 240:244 */           lista.add(facturaCliente.getNumero());
/* 241:    */         }
/* 242:247 */         break;
/* 243:    */       case 5: 
/* 244:249 */         filters = new HashMap();
/* 245:250 */         filters.put("idOrganizacion", "=" + idOrganizacion);
/* 246:251 */         filters.put("numero", "" + cadena);
/* 247:252 */         filters.put("documento.idDocumento", "" + getDocumento().getId());
/* 248:253 */         List<MovimientoInventario> listaMovimientoInventarioIngresoFabricacion = this.servicioMovimientoInventario.obtenerListaCombo("numero", true, filters);
/* 249:255 */         for (MovimientoInventario movimientoInventario : listaMovimientoInventarioIngresoFabricacion) {
/* 250:256 */           lista.add(movimientoInventario.getNumero());
/* 251:    */         }
/* 252:258 */         break;
/* 253:    */       }
/* 254:    */     }
/* 255:263 */     return lista;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void reiniciarNumero()
/* 259:    */   {
/* 260:267 */     setNumeroDocumento("");
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void cargarProducto(Producto producto)
/* 264:    */   {
/* 265:274 */     setProducto(producto);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void actualizarFiltros()
/* 269:    */   {
/* 270:281 */     if (!isIndicadorPorMovimiento())
/* 271:    */     {
/* 272:282 */       setDocumento(null);
/* 273:283 */       setNumeroLoteDesde(null);
/* 274:284 */       setNumeroLoteHasta(null);
/* 275:285 */       setNumeroDocumento(null);
/* 276:286 */       setIndicadorImprimirPorUnidad(true);
/* 277:    */     }
/* 278:    */     else
/* 279:    */     {
/* 280:288 */       setProducto(null);
/* 281:289 */       setCategoriaProducto(null);
/* 282:290 */       setSubcategoriaProducto(null);
/* 283:291 */       setIndicadorImprimirPorUnidad(false);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 288:    */   {
/* 289:296 */     List<CategoriaProducto> lista = new ArrayList();
/* 290:297 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 291:298 */     filters.put("nombre", "%" + consulta.trim());
/* 292:299 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 293:300 */     return lista;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 297:    */   {
/* 298:304 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 299:305 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 300:306 */     if (this.categoriaProducto != null) {
/* 301:307 */       filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 302:    */     }
/* 303:309 */     filters.put("nombre", "%" + consulta.trim());
/* 304:310 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 305:311 */     return lista;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public List<Documento> getListaDocumento()
/* 309:    */   {
/* 310:315 */     if (this.listaDocumento == null) {
/* 311:    */       try
/* 312:    */       {
/* 313:317 */         this.listaDocumento = new ArrayList();
/* 314:318 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.RECEPCION_BODEGA, 
/* 315:319 */           AppUtil.getOrganizacion().getIdOrganizacion()));
/* 316:320 */         for (Documento documento : this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.AJUSTE_INVENTARIO, 
/* 317:321 */           AppUtil.getOrganizacion().getIdOrganizacion())) {
/* 318:322 */           if (documento.getOperacion() == 1) {
/* 319:324 */             this.listaDocumento.add(documento);
/* 320:    */           }
/* 321:    */         }
/* 322:327 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DEVOLUCION_CLIENTE, 
/* 323:328 */           AppUtil.getOrganizacion().getIdOrganizacion()));
/* 324:329 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.TRANSFERENCIA_BODEGA, 
/* 325:330 */           AppUtil.getOrganizacion().getIdOrganizacion()));
/* 326:331 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.INGRESO_PRODUCCION, 
/* 327:332 */           AppUtil.getOrganizacion().getIdOrganizacion()));
/* 328:    */       }
/* 329:    */       catch (ExcepcionAS2 e)
/* 330:    */       {
/* 331:334 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 332:    */       }
/* 333:    */     }
/* 334:337 */     return this.listaDocumento;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 338:    */   {
/* 339:341 */     this.listaDocumento = listaDocumento;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public List<SelectItem> getListaTipoReporte()
/* 343:    */   {
/* 344:345 */     if (this.listaTipoReporte == null)
/* 345:    */     {
/* 346:346 */       this.listaTipoReporte = new ArrayList();
/* 347:347 */       this.listaTipoReporte.add(new SelectItem(Boolean.valueOf(true), "Por Movimiento"));
/* 348:348 */       this.listaTipoReporte.add(new SelectItem(Boolean.valueOf(false), "Por Producto"));
/* 349:    */     }
/* 350:350 */     return this.listaTipoReporte;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 354:    */   {
/* 355:354 */     this.listaTipoReporte = listaTipoReporte;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public Documento getDocumento()
/* 359:    */   {
/* 360:358 */     return this.documento;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setDocumento(Documento documento)
/* 364:    */   {
/* 365:362 */     this.documento = documento;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public String getNumeroDocumento()
/* 369:    */   {
/* 370:366 */     return this.numeroDocumento;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setNumeroDocumento(String numeroDocumento)
/* 374:    */   {
/* 375:370 */     this.numeroDocumento = numeroDocumento;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public String getNumeroLoteDesde()
/* 379:    */   {
/* 380:374 */     return this.numeroLoteDesde;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setNumeroLoteDesde(String numeroLoteDesde)
/* 384:    */   {
/* 385:378 */     this.numeroLoteDesde = numeroLoteDesde;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public String getNumeroLoteHasta()
/* 389:    */   {
/* 390:382 */     return this.numeroLoteHasta;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setNumeroLoteHasta(String numeroLoteHasta)
/* 394:    */   {
/* 395:386 */     this.numeroLoteHasta = numeroLoteHasta;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public Producto getProducto()
/* 399:    */   {
/* 400:390 */     return this.producto;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setProducto(Producto producto)
/* 404:    */   {
/* 405:394 */     this.producto = producto;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public boolean isIndicadorPorMovimiento()
/* 409:    */   {
/* 410:398 */     return this.indicadorPorMovimiento;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setIndicadorPorMovimiento(boolean indicadorPorMovimiento)
/* 414:    */   {
/* 415:402 */     this.indicadorPorMovimiento = indicadorPorMovimiento;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public boolean isIndicadorImprimirPorUnidad()
/* 419:    */   {
/* 420:406 */     return this.indicadorImprimirPorUnidad;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setIndicadorImprimirPorUnidad(boolean indicadorImprimirPorUnidad)
/* 424:    */   {
/* 425:410 */     this.indicadorImprimirPorUnidad = indicadorImprimirPorUnidad;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public CategoriaProducto getCategoriaProducto()
/* 429:    */   {
/* 430:414 */     return this.categoriaProducto;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 434:    */   {
/* 435:418 */     this.categoriaProducto = categoriaProducto;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 439:    */   {
/* 440:422 */     return this.subcategoriaProducto;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 444:    */   {
/* 445:426 */     this.subcategoriaProducto = subcategoriaProducto;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public List<Bodega> getListaBodega()
/* 449:    */   {
/* 450:430 */     if (this.listaBodega == null) {
/* 451:431 */       this.listaBodega = this.servicioBodega.obtenerListaComboPorUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario(), AppUtil.getOrganizacion().getId());
/* 452:    */     }
/* 453:433 */     return this.listaBodega;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 457:    */   {
/* 458:437 */     this.listaBodega = listaBodega;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public Bodega getBodega()
/* 462:    */   {
/* 463:441 */     return this.bodega;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setBodega(Bodega bodega)
/* 467:    */   {
/* 468:445 */     this.bodega = bodega;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public ListaProductoBean getListaProductoBean()
/* 472:    */   {
/* 473:449 */     return this.listaProductoBean;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 477:    */   {
/* 478:453 */     this.listaProductoBean = listaProductoBean;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public boolean isSaldoMayorCero()
/* 482:    */   {
/* 483:457 */     return this.saldoMayorCero;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setSaldoMayorCero(boolean saldoMayorCero)
/* 487:    */   {
/* 488:461 */     this.saldoMayorCero = saldoMayorCero;
/* 489:    */   }
/* 490:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ImpresionEtiquetaBean
 * JD-Core Version:    0.7.0.1
 */