/*   1:    */ package com.asinfo.as2.compras.importaciones.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioDocumentoGastoImportacion;
/*   5:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGastoImportacion;
/*   6:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioDetalleFacturaProveedorImportacionGasto;
/*   7:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion;
/*   8:    */ import com.asinfo.as2.controller.LanguageController;
/*   9:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioMoneda;
/*  11:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  12:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto;
/*  13:    */ import com.asinfo.as2.entities.DocumentoGastoImportacion;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  16:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*  17:    */ import com.asinfo.as2.entities.GastoImportacion;
/*  18:    */ import com.asinfo.as2.entities.Moneda;
/*  19:    */ import com.asinfo.as2.entities.Organizacion;
/*  20:    */ import com.asinfo.as2.entities.Sucursal;
/*  21:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  22:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  23:    */ import com.asinfo.as2.enumeraciones.TipoProrrateoEnum;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  27:    */ import com.asinfo.as2.util.AppUtil;
/*  28:    */ import com.asinfo.as2.utils.JsfUtil;
/*  29:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  30:    */ import java.math.BigDecimal;
/*  31:    */ import java.util.ArrayList;
/*  32:    */ import java.util.Date;
/*  33:    */ import java.util.HashMap;
/*  34:    */ import java.util.List;
/*  35:    */ import java.util.Map;
/*  36:    */ import javax.annotation.PostConstruct;
/*  37:    */ import javax.ejb.EJB;
/*  38:    */ import javax.faces.bean.ManagedBean;
/*  39:    */ import javax.faces.bean.ViewScoped;
/*  40:    */ import javax.faces.model.SelectItem;
/*  41:    */ import org.apache.log4j.Logger;
/*  42:    */ import org.primefaces.component.datatable.DataTable;
/*  43:    */ import org.primefaces.context.RequestContext;
/*  44:    */ import org.primefaces.event.ToggleEvent;
/*  45:    */ import org.primefaces.model.LazyDataModel;
/*  46:    */ import org.primefaces.model.SortOrder;
/*  47:    */ 
/*  48:    */ @ManagedBean
/*  49:    */ @ViewScoped
/*  50:    */ public class PresupuestoFacturaProveedorImportacionBean
/*  51:    */   extends PageControllerAS2
/*  52:    */ {
/*  53:    */   private static final long serialVersionUID = 1L;
/*  54:    */   @EJB
/*  55:    */   private ServicioFacturaProveedorImportacion servicioFacturaProveedorImportacion;
/*  56:    */   @EJB
/*  57:    */   private ServicioGastoImportacion servicioGastoImportacion;
/*  58:    */   @EJB
/*  59:    */   private ServicioMoneda servicioMoneda;
/*  60:    */   @EJB
/*  61:    */   private ServicioDocumentoGastoImportacion servicioDocumentoGastoImportacion;
/*  62:    */   @EJB
/*  63:    */   private ServicioDetalleFacturaProveedorImportacionGasto servicioDetalleFacturaProveedorImportacionGasto;
/*  64:    */   private FacturaProveedorImportacion facturaProveedorImportacion;
/*  65:    */   private DetalleFacturaProveedorImportacionGasto detalleFacturaProveedorImportacionGasto;
/*  66:    */   private DocumentoGastoImportacion[] listaDocumentoGastoImportacionSeleccionado;
/*  67:    */   private LazyDataModel<FacturaProveedorImportacion> listaFacturaProveedorImportacion;
/*  68:    */   private List<GastoImportacion> listaGastoImportacion;
/*  69:    */   private List<Moneda> listaMoneda;
/*  70:    */   private List<SelectItem> listaTipoProrrateo;
/*  71:    */   private List<DocumentoGastoImportacion> listaDocumentoGastoImportacionOpcional;
/*  72:    */   private Date fechaContabilizacion;
/*  73:    */   private boolean mostradoDialogoLiquidacion;
/*  74:    */   private DataTable dtFacturaProveedorImportacion;
/*  75:    */   private DataTable dtDetalleFacturaProveedorImportacionGasto;
/*  76:    */   private DataTable dtDetalleFacturaProveedor;
/*  77:    */   private List<FacturaProveedor> listaFacturasProveedor;
/*  78:    */   private DataTable dtListaReporte;
/*  79:    */   
/*  80:    */   @PostConstruct
/*  81:    */   public void init()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:120 */       this.listaFacturaProveedorImportacion = new LazyDataModel()
/*  86:    */       {
/*  87:    */         private static final long serialVersionUID = 1L;
/*  88:    */         
/*  89:    */         public List<FacturaProveedorImportacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  90:    */         {
/*  91:128 */           List<FacturaProveedorImportacion> lista = new ArrayList();
/*  92:130 */           if (filters == null) {
/*  93:131 */             filters = new HashMap();
/*  94:    */           }
/*  95:133 */           filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/*  96:134 */           filters.put("facturaProveedor.estado", Estado.ELABORADO.toString());
/*  97:    */           
/*  98:136 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  99:    */           
/* 100:138 */           lista = PresupuestoFacturaProveedorImportacionBean.this.servicioFacturaProveedorImportacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 101:    */           
/* 102:140 */           PresupuestoFacturaProveedorImportacionBean.this.listaFacturaProveedorImportacion.setRowCount(PresupuestoFacturaProveedorImportacionBean.this.servicioFacturaProveedorImportacion.contarPorCriterio(filters));
/* 103:141 */           return lista;
/* 104:    */         }
/* 105:    */       };
/* 106:    */     }
/* 107:    */     catch (Exception e)
/* 108:    */     {
/* 109:147 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 110:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void crearFacturaProveedorGastoImportacion()
/* 115:    */   {
/* 116:161 */     this.facturaProveedorImportacion = new FacturaProveedorImportacion();
/* 117:162 */     this.facturaProveedorImportacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 118:163 */     this.facturaProveedorImportacion.setSucursal(AppUtil.getSucursal());
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String editar()
/* 122:    */   {
/* 123:173 */     if (getFacturaProveedorImportacion().getIdFacturaProveedorImportacion() > 0)
/* 124:    */     {
/* 125:175 */       if (!getFacturaProveedorImportacion().getFacturaProveedor().getEstado().equals(Estado.CERRADO))
/* 126:    */       {
/* 127:176 */         this.facturaProveedorImportacion = this.servicioFacturaProveedorImportacion.cargarDetalle(getFacturaProveedorImportacion().getId());
/* 128:177 */         setEditado(true);
/* 129:    */       }
/* 130:    */       else
/* 131:    */       {
/* 132:179 */         addInfoMessage(getLanguageController().getMensaje("msg_error_factura_proveedor_importacion_cerrado"));
/* 133:    */       }
/* 134:    */     }
/* 135:    */     else {
/* 136:183 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 137:    */     }
/* 138:185 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String guardar()
/* 142:    */   {
/* 143:    */     try
/* 144:    */     {
/* 145:195 */       this.servicioFacturaProveedorImportacion.guardar(this.facturaProveedorImportacion);
/* 146:196 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 147:197 */       setEditado(false);
/* 148:198 */       limpiar();
/* 149:    */     }
/* 150:    */     catch (Exception e)
/* 151:    */     {
/* 152:200 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 153:201 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 154:    */     }
/* 155:203 */     return "";
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String eliminar()
/* 159:    */   {
/* 160:212 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 161:213 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String cargarDatos()
/* 165:    */   {
/* 166:222 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String limpiar()
/* 170:    */   {
/* 171:232 */     crearFacturaProveedorGastoImportacion();
/* 172:233 */     return "";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String crear()
/* 176:    */   {
/* 177:243 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 178:244 */     return "";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<DetalleFacturaProveedorImportacionGasto> getListaDetalleFacturaProveedorImportacionGasto()
/* 182:    */   {
/* 183:248 */     List<DetalleFacturaProveedorImportacionGasto> lista = new ArrayList();
/* 184:249 */     for (DetalleFacturaProveedorImportacionGasto dfpig : getFacturaProveedorImportacion().getListaDetalleFacturaProveedorImportacionGasto()) {
/* 185:250 */       if (!dfpig.isEliminado()) {
/* 186:251 */         lista.add(dfpig);
/* 187:    */       }
/* 188:    */     }
/* 189:254 */     return lista;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String eliminarDetalle()
/* 193:    */   {
/* 194:260 */     DetalleFacturaProveedorImportacionGasto dfpig = (DetalleFacturaProveedorImportacionGasto)this.dtDetalleFacturaProveedorImportacionGasto.getRowData();
/* 195:261 */     dfpig.setEliminado(true);
/* 196:262 */     return "";
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String agregarDetalle()
/* 200:    */   {
/* 201:266 */     BigDecimal valorPresupuesto = BigDecimal.ZERO;
/* 202:267 */     if (this.listaDocumentoGastoImportacionSeleccionado != null) {
/* 203:268 */       for (DocumentoGastoImportacion documentoGastoImportacion : this.listaDocumentoGastoImportacionSeleccionado)
/* 204:    */       {
/* 205:270 */         if (documentoGastoImportacion.getGastoImportacion().isIndicadorCalculoAutomatico())
/* 206:    */         {
/* 207:272 */           BigDecimal valorGasto = this.facturaProveedorImportacion.getFacturaProveedor().getTotalFactura().multiply(documentoGastoImportacion.getGastoImportacion().getPorcentaje().divide(BigDecimal.valueOf(100.0D)));
/* 208:273 */           valorPresupuesto = valorGasto.add(documentoGastoImportacion.getGastoImportacion().getValorManual());
/* 209:    */         }
/* 210:    */         else
/* 211:    */         {
/* 212:276 */           valorPresupuesto = documentoGastoImportacion.getGastoImportacion().getValorManual();
/* 213:    */         }
/* 214:279 */         documentoGastoImportacion = this.servicioDocumentoGastoImportacion.cargarDetalle(documentoGastoImportacion.getId());
/* 215:280 */         this.detalleFacturaProveedorImportacionGasto = new DetalleFacturaProveedorImportacionGasto();
/* 216:281 */         this.detalleFacturaProveedorImportacionGasto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 217:282 */         this.detalleFacturaProveedorImportacionGasto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 218:283 */         this.detalleFacturaProveedorImportacionGasto.setTipoProrrateoEnum(documentoGastoImportacion.getGastoImportacion().getTipoProrrateo());
/* 219:284 */         this.detalleFacturaProveedorImportacionGasto.setValorPresupuesto(valorPresupuesto);
/* 220:285 */         this.detalleFacturaProveedorImportacionGasto.setValorReal(BigDecimal.ZERO);
/* 221:286 */         this.detalleFacturaProveedorImportacionGasto.setFechaGasto(this.facturaProveedorImportacion.getFacturaProveedor().getFecha());
/* 222:287 */         this.detalleFacturaProveedorImportacionGasto.setFacturaProveedorImportacion(this.facturaProveedorImportacion);
/* 223:288 */         this.detalleFacturaProveedorImportacionGasto.setGastoImportacion(documentoGastoImportacion.getGastoImportacion());
/* 224:289 */         this.detalleFacturaProveedorImportacionGasto.setMoneda(this.facturaProveedorImportacion.getMoneda());
/* 225:    */         
/* 226:291 */         this.facturaProveedorImportacion.getListaDetalleFacturaProveedorImportacionGasto().add(this.detalleFacturaProveedorImportacionGasto);
/* 227:    */       }
/* 228:    */     }
/* 229:295 */     return "";
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String obtenerListaGastoimportacionOpcional()
/* 233:    */     throws ExcepcionAS2Compras
/* 234:    */   {
/* 235:305 */     List<GastoImportacion> listaGastoImportacion = new ArrayList();
/* 236:    */     
/* 237:    */ 
/* 238:308 */     List<DetalleFacturaProveedorImportacionGasto> listaPresupuestoImportacion = this.servicioDetalleFacturaProveedorImportacionGasto.obtenerListaPresupuestoImportacion(this.facturaProveedorImportacion.getFacturaProveedor());
/* 239:310 */     if (!listaPresupuestoImportacion.isEmpty())
/* 240:    */     {
/* 241:312 */       for (DetalleFacturaProveedorImportacionGasto dfpig : listaPresupuestoImportacion) {
/* 242:313 */         listaGastoImportacion.add(dfpig.getGastoImportacion());
/* 243:    */       }
/* 244:316 */       this.listaDocumentoGastoImportacionOpcional = this.servicioDocumentoGastoImportacion.obtenerListaDocumentoGastoImportacion(this.facturaProveedorImportacion.getFacturaProveedor(), false, listaGastoImportacion);
/* 245:    */     }
/* 246:    */     else
/* 247:    */     {
/* 248:319 */       this.listaDocumentoGastoImportacionOpcional = this.servicioDocumentoGastoImportacion.obtenerListaDocumentoGastoImportacion(this.facturaProveedorImportacion.getFacturaProveedor().getDocumento(), false);
/* 249:    */     }
/* 250:322 */     return "";
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String reLiquidar()
/* 254:    */   {
/* 255:    */     try
/* 256:    */     {
/* 257:327 */       this.facturaProveedorImportacion = this.servicioFacturaProveedorImportacion.prorratear(this.facturaProveedorImportacion.getId());
/* 258:328 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 259:    */     }
/* 260:    */     catch (ExcepcionAS2 e)
/* 261:    */     {
/* 262:330 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 263:331 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 264:    */     }
/* 265:333 */     return "";
/* 266:    */   }
/* 267:    */   
/* 268:    */   public String liquidarImportacion()
/* 269:    */   {
/* 270:338 */     if (ParametrosSistema.isContabilizaImportacionBasadaPresupuesto(AppUtil.getOrganizacion().getId()).booleanValue())
/* 271:    */     {
/* 272:339 */       setMostradoDialogoLiquidacion(true);
/* 273:340 */       this.fechaContabilizacion = new Date();
/* 274:341 */       RequestContext.getCurrentInstance().update("panelDialogoLiquidacion");
/* 275:342 */       RequestContext.getCurrentInstance().execute("dialogLiquidar.show()");
/* 276:    */     }
/* 277:    */     else
/* 278:    */     {
/* 279:344 */       liquidarImportacionSegunPresupuesto();
/* 280:345 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 281:    */     }
/* 282:348 */     return "";
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String liquidarImportacionSegunPresupuesto()
/* 286:    */   {
/* 287:    */     try
/* 288:    */     {
/* 289:354 */       this.servicioFacturaProveedorImportacion.liquidarImportacion(this.facturaProveedorImportacion.getFacturaProveedor(), this.fechaContabilizacion);
/* 290:355 */       cerrarDialogoLiquidacion();
/* 291:356 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 292:    */     }
/* 293:    */     catch (ExcepcionAS2Financiero e)
/* 294:    */     {
/* 295:358 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 296:359 */       LOG.error("ERROR AL LIQUIDAR IMPORTACION", e);
/* 297:    */     }
/* 298:    */     catch (ExcepcionAS2 e)
/* 299:    */     {
/* 300:361 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 301:362 */       LOG.error("ERROR AL LIQUIDAR IMPORTACION", e);
/* 302:    */     }
/* 303:    */     catch (AS2Exception e)
/* 304:    */     {
/* 305:364 */       JsfUtil.addErrorMessage(e, "");
/* 306:365 */       e.printStackTrace();
/* 307:    */     }
/* 308:368 */     return "";
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void cerrarDialogoLiquidacion()
/* 312:    */   {
/* 313:373 */     this.mostradoDialogoLiquidacion = false;
/* 314:374 */     this.fechaContabilizacion = null;
/* 315:375 */     RequestContext.getCurrentInstance().update("panelDialogoLiquidacion");
/* 316:376 */     RequestContext.getCurrentInstance().execute("dialogLiquidar.hide()");
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void cargarDatosFacturaImportacion(ToggleEvent event)
/* 320:    */   {
/* 321:381 */     FacturaProveedorImportacion facturaProveedorImportacion = (FacturaProveedorImportacion)event.getData();
/* 322:382 */     FacturaProveedor facturaProveedorAux = facturaProveedorImportacion.getFacturaProveedor();
/* 323:383 */     Empresa empresa = facturaProveedorAux.getEmpresa();
/* 324:    */     
/* 325:385 */     HashMap<String, FacturaProveedor> hmFacturaProveedo = new HashMap();
/* 326:    */     try
/* 327:    */     {
/* 328:389 */       List<Object[]> listaFacturasProveedor = this.servicioFacturaProveedorImportacion.getFacturasProveedorImportacion(empresa.getId(), facturaProveedorAux
/* 329:390 */         .getId());
/* 330:392 */       for (Object[] objects : listaFacturasProveedor)
/* 331:    */       {
/* 332:393 */         StringBuilder clave = new StringBuilder();
/* 333:394 */         clave.append((String)objects[14]);
/* 334:395 */         clave.append((String)objects[31]);
/* 335:396 */         clave.append((String)objects[32]);
/* 336:397 */         clave.append((String)objects[33]);
/* 337:    */         
/* 338:399 */         facturaProveedorAux = (FacturaProveedor)hmFacturaProveedo.get(clave.toString());
/* 339:400 */         if (facturaProveedorAux == null)
/* 340:    */         {
/* 341:401 */           FacturaProveedor facturaProveedor = new FacturaProveedor();
/* 342:402 */           facturaProveedor.setIdFacturaProveedor(objects[39] == null ? 0 : ((Integer)objects[39]).intValue());
/* 343:403 */           facturaProveedor.setNumero((String)objects[14]);
/* 344:404 */           facturaProveedor.setFecha((Date)objects[15]);
/* 345:405 */           facturaProveedor.setEmpresa(new Empresa());
/* 346:406 */           facturaProveedor.getEmpresa().setNombreFiscal((String)objects[17]);
/* 347:407 */           facturaProveedor.setFacturaProveedorSRI(new FacturaProveedorSRI());
/* 348:408 */           facturaProveedor.getFacturaProveedorSRI().setEstablecimiento((String)objects[31]);
/* 349:409 */           facturaProveedor.getFacturaProveedorSRI().setPuntoEmision((String)objects[32]);
/* 350:410 */           facturaProveedor.getFacturaProveedorSRI().setNumero((String)objects[33]);
/* 351:411 */           facturaProveedor.setListaDetalleFacturaProveedorImportacion(new ArrayList());
/* 352:    */           
/* 353:413 */           getListaFacturasProveedor().add(facturaProveedor);
/* 354:414 */           hmFacturaProveedo.put(clave.toString(), facturaProveedor);
/* 355:    */         }
/* 356:    */       }
/* 357:    */     }
/* 358:    */     catch (ExcepcionAS2 e)
/* 359:    */     {
/* 360:419 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 361:420 */       e.printStackTrace();
/* 362:    */     }
/* 363:    */   }
/* 364:    */   
/* 365:    */   public List<DetalleFacturaProveedor> getListaDetalleFacturaProveedor()
/* 366:    */   {
/* 367:426 */     List<DetalleFacturaProveedor> lista = new ArrayList();
/* 368:427 */     for (DetalleFacturaProveedor dfp : this.facturaProveedorImportacion.getFacturaProveedor().getListaDetalleFacturaProveedor()) {
/* 369:428 */       if (!dfp.isEliminado()) {
/* 370:429 */         lista.add(dfp);
/* 371:    */       }
/* 372:    */     }
/* 373:432 */     return lista;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public FacturaProveedorImportacion getFacturaProveedorImportacion()
/* 377:    */   {
/* 378:445 */     if (this.facturaProveedorImportacion == null) {
/* 379:446 */       crearFacturaProveedorGastoImportacion();
/* 380:    */     }
/* 381:448 */     return this.facturaProveedorImportacion;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 385:    */   {
/* 386:458 */     this.facturaProveedorImportacion = facturaProveedorImportacion;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public LazyDataModel<FacturaProveedorImportacion> getListaFacturaProveedorImportacion()
/* 390:    */   {
/* 391:467 */     return this.listaFacturaProveedorImportacion;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setListaFacturaProveedorImportacion(LazyDataModel<FacturaProveedorImportacion> listaFacturaProveedorImportacion)
/* 395:    */   {
/* 396:477 */     this.listaFacturaProveedorImportacion = listaFacturaProveedorImportacion;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public DataTable getDtFacturaProveedorImportacion()
/* 400:    */   {
/* 401:486 */     return this.dtFacturaProveedorImportacion;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setDtFacturaProveedorImportacion(DataTable dtFacturaProveedorImportacion)
/* 405:    */   {
/* 406:496 */     this.dtFacturaProveedorImportacion = dtFacturaProveedorImportacion;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public DataTable getDtDetalleFacturaProveedorImportacionGasto()
/* 410:    */   {
/* 411:505 */     return this.dtDetalleFacturaProveedorImportacionGasto;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setDtDetalleFacturaProveedorImportacionGasto(DataTable dtDetalleFacturaProveedorImportacionGasto)
/* 415:    */   {
/* 416:515 */     this.dtDetalleFacturaProveedorImportacionGasto = dtDetalleFacturaProveedorImportacionGasto;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public DetalleFacturaProveedorImportacionGasto getDetalleFacturaProveedorImportacionGasto()
/* 420:    */   {
/* 421:524 */     return this.detalleFacturaProveedorImportacionGasto;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setDetalleFacturaProveedorImportacionGasto(DetalleFacturaProveedorImportacionGasto detalleFacturaProveedorImportacionGasto)
/* 425:    */   {
/* 426:534 */     this.detalleFacturaProveedorImportacionGasto = detalleFacturaProveedorImportacionGasto;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public List<GastoImportacion> getListaGastoImportacion()
/* 430:    */   {
/* 431:543 */     if (this.listaGastoImportacion == null) {
/* 432:544 */       this.listaGastoImportacion = this.servicioGastoImportacion.obtenerListaCombo(null, false, null);
/* 433:    */     }
/* 434:546 */     return this.listaGastoImportacion;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public List<SelectItem> getListaTipoProrrateo()
/* 438:    */   {
/* 439:555 */     if (this.listaTipoProrrateo == null)
/* 440:    */     {
/* 441:556 */       this.listaTipoProrrateo = new ArrayList();
/* 442:557 */       for (TipoProrrateoEnum tipoProrrateo : TipoProrrateoEnum.values())
/* 443:    */       {
/* 444:558 */         SelectItem item = new SelectItem(tipoProrrateo, tipoProrrateo.getNombre());
/* 445:559 */         this.listaTipoProrrateo.add(item);
/* 446:    */       }
/* 447:    */     }
/* 448:562 */     return this.listaTipoProrrateo;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public List<Moneda> getListaMoneda()
/* 452:    */   {
/* 453:571 */     if (this.listaMoneda == null) {
/* 454:572 */       this.listaMoneda = this.servicioMoneda.obtenerListaCombo(null, false, null);
/* 455:    */     }
/* 456:574 */     return this.listaMoneda;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public List<DocumentoGastoImportacion> getListaDocumentoGastoImportacionOpcional()
/* 460:    */   {
/* 461:583 */     return this.listaDocumentoGastoImportacionOpcional;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setListaDocumentoGastoImportacionOpcional(List<DocumentoGastoImportacion> listaDocumentoGastoImportacionOpcional)
/* 465:    */   {
/* 466:593 */     this.listaDocumentoGastoImportacionOpcional = listaDocumentoGastoImportacionOpcional;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public DocumentoGastoImportacion[] getListaDocumentoGastoImportacionSeleccionado()
/* 470:    */   {
/* 471:602 */     return this.listaDocumentoGastoImportacionSeleccionado;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setListaDocumentoGastoImportacionSeleccionado(DocumentoGastoImportacion[] listaDocumentoGastoImportacionSeleccionado)
/* 475:    */   {
/* 476:612 */     this.listaDocumentoGastoImportacionSeleccionado = listaDocumentoGastoImportacionSeleccionado;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public DataTable getDtDetalleFacturaProveedor()
/* 480:    */   {
/* 481:621 */     return this.dtDetalleFacturaProveedor;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setDtDetalleFacturaProveedor(DataTable dtDetalleFacturaProveedor)
/* 485:    */   {
/* 486:631 */     this.dtDetalleFacturaProveedor = dtDetalleFacturaProveedor;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public List<FacturaProveedor> getListaFacturasProveedor()
/* 490:    */   {
/* 491:635 */     if (this.listaFacturasProveedor == null) {
/* 492:636 */       this.listaFacturasProveedor = new ArrayList();
/* 493:    */     }
/* 494:638 */     return this.listaFacturasProveedor;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public DataTable getDtListaReporte()
/* 498:    */   {
/* 499:642 */     return this.dtListaReporte;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setListaFacturasProveedor(List<FacturaProveedor> listaFacturasProveedor)
/* 503:    */   {
/* 504:646 */     this.listaFacturasProveedor = listaFacturasProveedor;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void setDtListaReporte(DataTable dtListaReporte)
/* 508:    */   {
/* 509:650 */     this.dtListaReporte = dtListaReporte;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public Date getFechaContabilizacion()
/* 513:    */   {
/* 514:654 */     return this.fechaContabilizacion;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 518:    */   {
/* 519:658 */     this.fechaContabilizacion = fechaContabilizacion;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public boolean isMostradoDialogoLiquidacion()
/* 523:    */   {
/* 524:662 */     return this.mostradoDialogoLiquidacion;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void setMostradoDialogoLiquidacion(boolean mostradoDialogoLiquidacion)
/* 528:    */   {
/* 529:666 */     this.mostradoDialogoLiquidacion = mostradoDialogoLiquidacion;
/* 530:    */   }
/* 531:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.procesos.PresupuestoFacturaProveedorImportacionBean
 * JD-Core Version:    0.7.0.1
 */