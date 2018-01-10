/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  11:    */ import com.asinfo.as2.entities.Lote;
/*  12:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  15:    */ import com.asinfo.as2.entities.Producto;
/*  16:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*  21:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  22:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  26:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  29:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  30:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  31:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  32:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  33:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  34:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  35:    */ import com.asinfo.as2.util.AppUtil;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  38:    */ import java.io.BufferedInputStream;
/*  39:    */ import java.io.InputStream;
/*  40:    */ import java.math.BigDecimal;
/*  41:    */ import java.math.RoundingMode;
/*  42:    */ import java.util.ArrayList;
/*  43:    */ import java.util.Calendar;
/*  44:    */ import java.util.Collection;
/*  45:    */ import java.util.Date;
/*  46:    */ import java.util.HashMap;
/*  47:    */ import java.util.List;
/*  48:    */ import java.util.Map;
/*  49:    */ import javax.annotation.PostConstruct;
/*  50:    */ import javax.ejb.EJB;
/*  51:    */ import javax.faces.bean.ManagedBean;
/*  52:    */ import javax.faces.bean.ViewScoped;
/*  53:    */ import javax.faces.context.FacesContext;
/*  54:    */ import javax.faces.context.PartialViewContext;
/*  55:    */ import org.apache.log4j.Logger;
/*  56:    */ import org.primefaces.component.datatable.DataTable;
/*  57:    */ import org.primefaces.context.RequestContext;
/*  58:    */ import org.primefaces.event.FileUploadEvent;
/*  59:    */ import org.primefaces.model.LazyDataModel;
/*  60:    */ import org.primefaces.model.SortOrder;
/*  61:    */ import org.primefaces.model.UploadedFile;
/*  62:    */ 
/*  63:    */ @ManagedBean
/*  64:    */ @ViewScoped
/*  65:    */ public class AjusteInventarioBean
/*  66:    */   extends MovimientoInventarioBaseBean
/*  67:    */ {
/*  68:    */   private static final long serialVersionUID = -5463907651730263568L;
/*  69:    */   @EJB
/*  70:    */   protected ServicioProducto servicioProducto;
/*  71:    */   @EJB
/*  72:    */   private ServicioDocumento servicioDocumento;
/*  73:    */   @EJB
/*  74:    */   private ServicioLote servicioLote;
/*  75:    */   @EJB
/*  76:    */   protected ServicioUnidadConversion servicioUnidadConversion;
/*  77:    */   @EJB
/*  78:    */   private ServicioOrganizacion servicioOrganizacion;
/*  79:    */   @EJB
/*  80:    */   protected ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  81:    */   @EJB
/*  82:    */   protected ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  83:    */   @EJB
/*  84:    */   private ServicioAtributo servicioAtributo;
/*  85:    */   protected MovimientoInventario ajusteInventario;
/*  86:    */   private LazyDataModel<MovimientoInventario> listaAjusteInventario;
/*  87:    */   private List<Documento> listaDocumentosAjuste;
/*  88:    */   protected DetalleMovimientoInventario detalleMovimientoInventario;
/*  89:    */   protected DetalleMovimientoInventario detalleMovimientoInventarioSeleccionado;
/*  90:102 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  91:    */   private DataTable dtListaAjuste;
/*  92:    */   private DataTable dtDetalleAjuste;
/*  93:    */   private DataTable dtInventarioProductoLote;
/*  94:    */   private DataTable dtEdicionAjusteInventario;
/*  95:    */   private List<Bodega> listaBodega;
/*  96:    */   private boolean mostrarSaldoInicial;
/*  97:    */   private Lote loteCrear;
/*  98:    */   private String codigoCabecera;
/*  99:    */   private Integer idAjusteInventario;
/* 100:    */   private List<DetalleMovimientoInventario> listaEdicionDetalleMovimiento;
/* 101:    */   
/* 102:    */   @PostConstruct
/* 103:    */   public void init()
/* 104:    */   {
/* 105:126 */     crearAjuste();
/* 106:127 */     getListaProductoBean().setActivo(true);
/* 107:128 */     this.listaAjusteInventario = new LazyDataModel()
/* 108:    */     {
/* 109:    */       private static final long serialVersionUID = 1L;
/* 110:    */       
/* 111:    */       public List<MovimientoInventario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 112:    */       {
/* 113:135 */         List<MovimientoInventario> lista = new ArrayList();
/* 114:    */         
/* 115:137 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 116:139 */         if (filters == null) {
/* 117:140 */           filters = new HashMap();
/* 118:    */         }
/* 119:143 */         if (AjusteInventarioBean.this.idAjusteInventario != null)
/* 120:    */         {
/* 121:144 */           filters.put("idMovimientoInventario", AjusteInventarioBean.this.idAjusteInventario.toString());
/* 122:145 */           AjusteInventarioBean.this.idAjusteInventario = null;
/* 123:    */         }
/* 124:148 */         filters.put("documento.documentoBase", AjusteInventarioBean.this.getDocumentoBase().toString());
/* 125:149 */         filters = AjusteInventarioBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/* 126:150 */         lista.addAll(AjusteInventarioBean.this.servicioMovimientoInventario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters));
/* 127:    */         
/* 128:152 */         AjusteInventarioBean.this.listaAjusteInventario.setRowCount(AjusteInventarioBean.this.servicioMovimientoInventario.contarPorCirterio(filters));
/* 129:    */         
/* 130:154 */         return lista;
/* 131:    */       }
/* 132:    */     };
/* 133:    */   }
/* 134:    */   
/* 135:    */   public DocumentoBase getDocumentoBase()
/* 136:    */   {
/* 137:161 */     return DocumentoBase.AJUSTE_INVENTARIO;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void actualizarProducto()
/* 141:    */   {
/* 142:165 */     String codigo = "";
/* 143:    */     try
/* 144:    */     {
/* 145:168 */       this.detalleMovimientoInventario = ((DetalleMovimientoInventario)this.dtDetalleAjuste.getRowData());
/* 146:169 */       codigo = this.detalleMovimientoInventario.getProducto().getCodigo();
/* 147:170 */       cargarProductoDesdeCodigoEnDetalle(codigo, this.detalleMovimientoInventario);
/* 148:    */     }
/* 149:    */     catch (ExcepcionAS2 e)
/* 150:    */     {
/* 151:172 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage() + " " + codigo);
/* 152:173 */       this.detalleMovimientoInventario.getProducto().setCodigo("");
/* 153:174 */       this.detalleMovimientoInventario.getProducto().setNombre("");
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   private void cargarProductoDesdeCodigoEnDetalle(String codigo, DetalleMovimientoInventario detalleMovimientoInventario)
/* 158:    */     throws ExcepcionAS2
/* 159:    */   {
/* 160:180 */     Producto producto = null;
/* 161:181 */     producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 162:182 */     this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 163:183 */     detalleMovimientoInventario.setProducto(producto);
/* 164:184 */     cargarBodega(detalleMovimientoInventario);
/* 165:185 */     detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 166:186 */     boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/* 167:189 */     if (!indicadorAprobarMovimientoInventario)
/* 168:    */     {
/* 169:190 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 170:191 */       detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 171:192 */       inventarioProducto.setProducto(producto);
/* 172:193 */       inventarioProducto.setOperacion(this.ajusteInventario.getDocumento().getOperacion());
/* 173:    */     }
/* 174:196 */     if (producto.getLote() != null)
/* 175:    */     {
/* 176:197 */       detalleMovimientoInventario.getInventarioProducto().setLote(producto.getLote());
/* 177:198 */       detalleMovimientoInventario.setLote(producto.getLote());
/* 178:199 */       BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), detalleMovimientoInventario.getBodegaOrigen().getIdBodega(), producto
/* 179:200 */         .getLote().getIdLote(), this.ajusteInventario.getFecha());
/* 180:201 */       detalleMovimientoInventario.setSaldo(saldo);
/* 181:203 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/* 182:204 */         detalleMovimientoInventario.setCantidad(saldo.setScale(4, RoundingMode.HALF_UP));
/* 183:    */       }
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected void cargarBodega(DetalleMovimientoInventario detalleMovimientoInventario)
/* 188:    */   {
/* 189:210 */     if (AppUtil.getBodega() != null)
/* 190:    */     {
/* 191:211 */       detalleMovimientoInventario.setBodegaOrigen(AppUtil.getBodega());
/* 192:    */     }
/* 193:    */     else
/* 194:    */     {
/* 195:213 */       Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(detalleMovimientoInventario.getProducto(), 
/* 196:214 */         Integer.valueOf(detalleMovimientoInventario.getIdSucursal()));
/* 197:215 */       detalleMovimientoInventario.setBodegaOrigen(bodegaTrabajo);
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String editar()
/* 202:    */   {
/* 203:227 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 204:    */     
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:249 */     return "";
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String limpiar()
/* 229:    */   {
/* 230:259 */     crearAjuste();
/* 231:260 */     return "";
/* 232:    */   }
/* 233:    */   
/* 234:    */   protected void crearAjuste()
/* 235:    */   {
/* 236:264 */     this.ajusteInventario = new MovimientoInventario();
/* 237:265 */     this.ajusteInventario.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 238:266 */     this.ajusteInventario.setSucursal(AppUtil.getSucursal());
/* 239:267 */     this.ajusteInventario.setNumero("");
/* 240:268 */     Documento documento = null;
/* 241:269 */     if ((getListaDocumentosAjuste() != null) && (!getListaDocumentosAjuste().isEmpty()))
/* 242:    */     {
/* 243:270 */       documento = (Documento)getListaDocumentosAjuste().get(0);
/* 244:271 */       this.ajusteInventario.setDocumento(documento);
/* 245:272 */       actualizarDocumento();
/* 246:    */     }
/* 247:    */     else
/* 248:    */     {
/* 249:274 */       documento = new Documento();
/* 250:275 */       this.ajusteInventario.setDocumento(documento);
/* 251:    */     }
/* 252:278 */     this.ajusteInventario.setEstado(Estado.ELABORADO);
/* 253:279 */     this.ajusteInventario.setFecha(new Date());
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String guardar()
/* 257:    */   {
/* 258:    */     try
/* 259:    */     {
/* 260:291 */       this.servicioMovimientoInventario.guardar(getAjusteInventario(), false, true);
/* 261:    */       
/* 262:293 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 263:    */       
/* 264:295 */       setEditado(false);
/* 265:    */     }
/* 266:    */     catch (ExcepcionAS2Inventario e)
/* 267:    */     {
/* 268:298 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 269:299 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 270:    */     }
/* 271:    */     catch (ExcepcionAS2Financiero e)
/* 272:    */     {
/* 273:301 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 274:302 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 275:    */     }
/* 276:    */     catch (ExcepcionAS2 e)
/* 277:    */     {
/* 278:304 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 279:305 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 280:    */     }
/* 281:    */     catch (AS2Exception e)
/* 282:    */     {
/* 283:307 */       this.exContabilizacion = e;
/* 284:308 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 285:309 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 286:310 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 287:    */     }
/* 288:    */     catch (Exception e)
/* 289:    */     {
/* 290:312 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 291:313 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 292:    */     }
/* 293:315 */     return "";
/* 294:    */   }
/* 295:    */   
/* 296:    */   public String agregarDetalle()
/* 297:    */   {
/* 298:325 */     DetalleMovimientoInventario d = new DetalleMovimientoInventario();
/* 299:326 */     d.setMovimientoInventario(getAjusteInventario());
/* 300:327 */     d.setProducto(new Producto());
/* 301:328 */     cargarBodega(d);
/* 302:    */     
/* 303:330 */     boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/* 304:333 */     if (!indicadorAprobarMovimientoInventario)
/* 305:    */     {
/* 306:334 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 307:335 */       inventarioProducto.setDetalleMovimientoInventario(d);
/* 308:336 */       d.setInventarioProducto(inventarioProducto);
/* 309:337 */       inventarioProducto.setOperacion(this.ajusteInventario.getDocumento().getOperacion());
/* 310:    */     }
/* 311:340 */     getAjusteInventario().getDetalleMovimientosInventario().add(d);
/* 312:341 */     this.detalleMovimientoInventario = d;
/* 313:342 */     return "";
/* 314:    */   }
/* 315:    */   
/* 316:    */   public String creacionLote()
/* 317:    */     throws ExcepcionAS2, AS2Exception
/* 318:    */   {
/* 319:346 */     this.detalleMovimientoInventarioSeleccionado = ((DetalleMovimientoInventario)this.dtDetalleAjuste.getRowData());
/* 320:    */     
/* 321:348 */     this.loteCrear = this.detalleMovimientoInventarioSeleccionado.getLote();
/* 322:349 */     if ((this.loteCrear != null) && (this.loteCrear.getCodigo() != null) && (!this.loteCrear.getCodigo().isEmpty()))
/* 323:    */     {
/* 324:350 */       Map<String, String> filters = new HashMap();
/* 325:351 */       filters.put("codigo", "=" + this.loteCrear.getCodigo());
/* 326:352 */       filters.put("producto.idProducto", "=" + this.detalleMovimientoInventarioSeleccionado.getProducto().getIdProducto());
/* 327:353 */       filters.put("indicadorMovimientoInterno", "true");
/* 328:354 */       List<Lote> loteList = this.servicioLote.obtenerListaCombo("codigo", true, filters);
/* 329:355 */       this.loteCrear = (!loteList.isEmpty() ? (Lote)loteList.get(0) : this.loteCrear);
/* 330:    */     }
/* 331:    */     else
/* 332:    */     {
/* 333:357 */       this.loteCrear = this.servicioLote.crearLoteInterno(AppUtil.getOrganizacion().getIdOrganizacion(), this.detalleMovimientoInventarioSeleccionado.getProducto(), null, false);
/* 334:    */     }
/* 335:385 */     return "";
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String guardarLote()
/* 339:    */     throws AS2Exception
/* 340:    */   {
/* 341:    */     try
/* 342:    */     {
/* 343:390 */       boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/* 344:    */       
/* 345:    */ 
/* 346:393 */       this.loteCrear.setCodigo(this.loteCrear.getProducto().getPrefijoLote() + this.loteCrear.getCodigo());
/* 347:394 */       if (this.loteCrear.getIdLote() != 0) {
/* 348:395 */         this.servicioLote.guardar(this.loteCrear);
/* 349:    */       }
/* 350:397 */       this.loteCrear.getProducto().setPrefijoLote(null);
/* 351:    */       
/* 352:399 */       this.loteCrear.setCodigo(this.loteCrear.getProducto().getPrefijoLote() + this.loteCrear.getCodigo());
/* 353:400 */       this.servicioLote.guardar(this.loteCrear);
/* 354:401 */       this.loteCrear.getProducto().setPrefijoLote(null);
/* 355:403 */       if (!indicadorAprobarMovimientoInventario)
/* 356:    */       {
/* 357:404 */         this.detalleMovimientoInventarioSeleccionado.getInventarioProducto().setLote(this.loteCrear);
/* 358:405 */         this.detalleMovimientoInventarioSeleccionado.setLote(this.loteCrear);
/* 359:    */       }
/* 360:    */       else
/* 361:    */       {
/* 362:407 */         this.detalleMovimientoInventarioSeleccionado.setLote(this.loteCrear);
/* 363:    */       }
/* 364:411 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 365:    */     }
/* 366:    */     catch (Exception e)
/* 367:    */     {
/* 368:413 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 369:414 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 370:    */     }
/* 371:416 */     return "";
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void agregarDetalleDesdeCodigoCabecera()
/* 375:    */   {
/* 376:420 */     DetalleMovimientoInventario detalle = new DetalleMovimientoInventario();
/* 377:    */     try
/* 378:    */     {
/* 379:422 */       detalle.setMovimientoInventario(getAjusteInventario());
/* 380:423 */       detalle.setProducto(new Producto());
/* 381:424 */       cargarBodega(detalle);
/* 382:    */       
/* 383:426 */       boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/* 384:429 */       if (!indicadorAprobarMovimientoInventario)
/* 385:    */       {
/* 386:430 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 387:431 */         inventarioProducto.setDetalleMovimientoInventario(detalle);
/* 388:432 */         detalle.setInventarioProducto(inventarioProducto);
/* 389:433 */         inventarioProducto.setOperacion(this.ajusteInventario.getDocumento().getOperacion());
/* 390:    */       }
/* 391:436 */       getAjusteInventario().getDetalleMovimientosInventario().add(detalle);
/* 392:437 */       this.detalleMovimientoInventario = detalle;
/* 393:    */       
/* 394:439 */       cargarProductoDesdeCodigoEnDetalle(this.codigoCabecera, detalle);
/* 395:440 */       this.codigoCabecera = "";
/* 396:    */     }
/* 397:    */     catch (ExcepcionAS2 e)
/* 398:    */     {
/* 399:442 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 400:443 */       detalle.getProducto().setCodigo("");
/* 401:444 */       detalle.getProducto().setNombre("");
/* 402:445 */       detalle.setEliminado(true);
/* 403:446 */       this.codigoCabecera = "";
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   public String eliminarDetalle()
/* 408:    */   {
/* 409:451 */     DetalleMovimientoInventario d = (DetalleMovimientoInventario)this.dtDetalleAjuste.getRowData();
/* 410:452 */     d.setEliminado(true);
/* 411:453 */     return "";
/* 412:    */   }
/* 413:    */   
/* 414:    */   public List<Lote> autocompletarLotes(String consulta)
/* 415:    */   {
/* 416:457 */     DetalleMovimientoInventario detalleMovimientoInventario = (DetalleMovimientoInventario)this.dtDetalleAjuste.getRowData();
/* 417:458 */     return this.servicioLote.autocompletarLote(detalleMovimientoInventario.getProducto(), consulta);
/* 418:    */   }
/* 419:    */   
/* 420:    */   public String actualizarDocumento()
/* 421:    */   {
/* 422:468 */     getAjusteInventario().setDocumento(this.servicioDocumento.buscarPorId(Integer.valueOf(getAjusteInventario().getDocumento().getId())));
/* 423:    */     
/* 424:470 */     setSecuenciaEditable(!this.ajusteInventario.getDocumento().isIndicadorBloqueoSecuencia());
/* 425:    */     
/* 426:472 */     return "";
/* 427:    */   }
/* 428:    */   
/* 429:    */   public String eliminar()
/* 430:    */   {
/* 431:482 */     if ((getAjusteInventario() != null) && (getAjusteInventario().getId() != 0)) {
/* 432:    */       try
/* 433:    */       {
/* 434:484 */         this.servicioMovimientoInventario.anular(getAjusteInventario());
/* 435:485 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 436:    */       }
/* 437:    */       catch (ExcepcionAS2Financiero e)
/* 438:    */       {
/* 439:488 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 440:489 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 441:    */       }
/* 442:    */       catch (ExcepcionAS2Inventario e)
/* 443:    */       {
/* 444:491 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 445:492 */         e.printStackTrace();
/* 446:493 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 447:    */       }
/* 448:    */       catch (Exception e)
/* 449:    */       {
/* 450:495 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 451:496 */         LOG.error("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 452:    */       }
/* 453:    */     } else {
/* 454:499 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 455:    */     }
/* 456:501 */     return "";
/* 457:    */   }
/* 458:    */   
/* 459:    */   public String cargarDatos()
/* 460:    */   {
/* 461:511 */     return "";
/* 462:    */   }
/* 463:    */   
/* 464:    */   public List<Documento> getListaDocumentosAjuste()
/* 465:    */   {
/* 466:519 */     if (this.listaDocumentosAjuste == null) {
/* 467:    */       try
/* 468:    */       {
/* 469:521 */         this.listaDocumentosAjuste = this.servicioDocumento.buscarPorDocumentoBase(getDocumentoBase());
/* 470:    */       }
/* 471:    */       catch (ExcepcionAS2 e)
/* 472:    */       {
/* 473:523 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 474:    */       }
/* 475:    */     }
/* 476:528 */     return this.listaDocumentosAjuste;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void cargarProducto(Producto producto)
/* 480:    */   {
/* 481:532 */     getListaProductoBean().setProducto(producto);
/* 482:533 */     cargarProducto();
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void cargarProducto()
/* 486:    */   {
/* 487:    */     try
/* 488:    */     {
/* 489:540 */       Producto producto = getListaProductoBean().getProducto();
/* 490:542 */       if (producto != null)
/* 491:    */       {
/* 492:    */         try
/* 493:    */         {
/* 494:545 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 495:    */         }
/* 496:    */         catch (ExcepcionAS2 e)
/* 497:    */         {
/* 498:547 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 499:    */         }
/* 500:550 */         this.detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 501:551 */         this.detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 502:552 */         this.detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 503:553 */         this.detalleMovimientoInventario.setProducto(producto);
/* 504:554 */         this.detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 505:555 */         this.detalleMovimientoInventario.setCantidad(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/* 506:556 */         this.detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 507:557 */         this.detalleMovimientoInventario.setCantidad(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/* 508:558 */         cargarBodega(this.detalleMovimientoInventario);
/* 509:    */         
/* 510:560 */         boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/* 511:    */         
/* 512:    */ 
/* 513:563 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 514:565 */         if (!indicadorAprobarMovimientoInventario)
/* 515:    */         {
/* 516:567 */           inventarioProducto.setDetalleMovimientoInventario(this.detalleMovimientoInventario);
/* 517:568 */           inventarioProducto.setProducto(producto);
/* 518:569 */           inventarioProducto.setOperacion(this.ajusteInventario.getDocumento().getOperacion());
/* 519:570 */           this.detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 520:    */         }
/* 521:573 */         this.detalleMovimientoInventario.setMovimientoInventario(this.ajusteInventario);
/* 522:575 */         if (getListaProductoBean().getSaldoProductoLote() != null)
/* 523:    */         {
/* 524:576 */           this.detalleMovimientoInventario.setBodegaOrigen(getListaProductoBean().getSaldoProductoLote().getBodega());
/* 525:577 */           this.detalleMovimientoInventario
/* 526:578 */             .setCantidad(getListaProductoBean().getSaldoProductoLote().getSaldo().setScale(2, RoundingMode.HALF_UP));
/* 527:579 */           this.detalleMovimientoInventario.setSaldo(this.detalleMovimientoInventario.getCantidad());
/* 528:580 */           if (!indicadorAprobarMovimientoInventario)
/* 529:    */           {
/* 530:581 */             inventarioProducto.setBodega(this.detalleMovimientoInventario.getBodegaOrigen());
/* 531:582 */             inventarioProducto.setLote(getListaProductoBean().getSaldoProductoLote().getLote());
/* 532:583 */             inventarioProducto.setCantidad(this.detalleMovimientoInventario.getCantidad());
/* 533:    */           }
/* 534:585 */           this.detalleMovimientoInventario.setLote(getListaProductoBean().getSaldoProductoLote().getLote());
/* 535:    */         }
/* 536:588 */         this.ajusteInventario.getDetalleMovimientosInventario().add(this.detalleMovimientoInventario);
/* 537:    */       }
/* 538:    */     }
/* 539:    */     catch (Exception e)
/* 540:    */     {
/* 541:592 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 542:593 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 543:    */     }
/* 544:    */     finally
/* 545:    */     {
/* 546:595 */       getListaProductoBean().setProducto(null);
/* 547:596 */       getListaProductoBean().setSaldoProductoLote(null);
/* 548:    */     }
/* 549:    */   }
/* 550:    */   
/* 551:    */   public String cargarDetalleAjusteInventario(FileUploadEvent event)
/* 552:    */   {
/* 553:    */     try
/* 554:    */     {
/* 555:603 */       String fileName = "ajuste_inventario" + event.getFile().getFileName();
/* 556:604 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 557:605 */       this.servicioMovimientoInventario.cargarDetalleAjusteInventario(this.ajusteInventario, fileName, input, 5);
/* 558:606 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 559:    */     }
/* 560:    */     catch (ExcepcionAS2Financiero e)
/* 561:    */     {
/* 562:609 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 563:610 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 564:    */     }
/* 565:    */     catch (ExcepcionAS2Compras e)
/* 566:    */     {
/* 567:613 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 568:614 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 569:    */     }
/* 570:    */     catch (ExcepcionAS2 e)
/* 571:    */     {
/* 572:617 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 573:618 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 574:    */     }
/* 575:    */     catch (Exception e)
/* 576:    */     {
/* 577:621 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 578:    */     }
/* 579:623 */     return null;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public void cargarDatosAjusteInventario()
/* 583:    */   {
/* 584:627 */     this.listaEdicionDetalleMovimiento.clear();
/* 585:628 */     this.ajusteInventario = ((MovimientoInventario)this.dtListaAjuste.getRowData());
/* 586:629 */     this.ajusteInventario = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(this.ajusteInventario.getIdMovimientoInventario()));
/* 587:631 */     for (DetalleMovimientoInventario dmi : this.ajusteInventario.getDetalleMovimientosInventario()) {
/* 588:632 */       if (!dmi.isEliminado()) {
/* 589:633 */         this.listaEdicionDetalleMovimiento.add(dmi);
/* 590:    */       }
/* 591:    */     }
/* 592:    */   }
/* 593:    */   
/* 594:    */   public String guardarDetalleMovimiento()
/* 595:    */   {
/* 596:    */     try
/* 597:    */     {
/* 598:641 */       this.servicioMovimientoInventario.guardarDetalleMovimiento(this.ajusteInventario);
/* 599:642 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 600:    */     }
/* 601:    */     catch (ExcepcionAS2Financiero e)
/* 602:    */     {
/* 603:644 */       e.printStackTrace();
/* 604:645 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 605:    */     }
/* 606:    */     catch (ExcepcionAS2 e)
/* 607:    */     {
/* 608:647 */       e.printStackTrace();
/* 609:648 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 610:    */     }
/* 611:    */     catch (AS2Exception e)
/* 612:    */     {
/* 613:650 */       e.printStackTrace();
/* 614:651 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 615:    */     }
/* 616:653 */     return "";
/* 617:    */   }
/* 618:    */   
/* 619:    */   public void setListaDocumentosAjuste(List<Documento> listaDocumentosTransferencia)
/* 620:    */   {
/* 621:661 */     this.listaDocumentosAjuste = listaDocumentosTransferencia;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public void setDetalleMovimientoInventario(List<DetalleMovimientoInventario> detalleMovimientoInventario)
/* 625:    */   {
/* 626:671 */     getAjusteInventario().setDetalleMovimientosInventario(detalleMovimientoInventario);
/* 627:    */   }
/* 628:    */   
/* 629:    */   public DetalleMovimientoInventario getDetalleMovimientoInventario()
/* 630:    */   {
/* 631:675 */     return this.detalleMovimientoInventario;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public DataTable getDtDetalleAjuste()
/* 635:    */   {
/* 636:684 */     return this.dtDetalleAjuste;
/* 637:    */   }
/* 638:    */   
/* 639:    */   public void setDtDetalleAjuste(DataTable dtDetalleAjuste)
/* 640:    */   {
/* 641:694 */     this.dtDetalleAjuste = dtDetalleAjuste;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public MovimientoInventario getAjusteInventario()
/* 645:    */   {
/* 646:703 */     return this.ajusteInventario;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public void setAjusteInventario(MovimientoInventario ajusteInventario)
/* 650:    */   {
/* 651:713 */     this.ajusteInventario = ajusteInventario;
/* 652:    */   }
/* 653:    */   
/* 654:    */   public LazyDataModel<MovimientoInventario> getListaAjusteInventario()
/* 655:    */   {
/* 656:722 */     return this.listaAjusteInventario;
/* 657:    */   }
/* 658:    */   
/* 659:    */   public void setListaAjusteInventario(LazyDataModel<MovimientoInventario> listaAjusteInventario)
/* 660:    */   {
/* 661:732 */     this.listaAjusteInventario = listaAjusteInventario;
/* 662:    */   }
/* 663:    */   
/* 664:    */   public DataTable getDtListaAjuste()
/* 665:    */   {
/* 666:741 */     return this.dtListaAjuste;
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void setDtListaAjuste(DataTable dtListaAjuste)
/* 670:    */   {
/* 671:751 */     this.dtListaAjuste = dtListaAjuste;
/* 672:    */   }
/* 673:    */   
/* 674:    */   public List<DetalleMovimientoInventario> getDetalleAjusteMovimiento()
/* 675:    */   {
/* 676:761 */     List<DetalleMovimientoInventario> detalle = new ArrayList();
/* 677:762 */     for (DetalleMovimientoInventario dm : getAjusteInventario().getDetalleMovimientosInventario()) {
/* 678:764 */       if (!dm.isEliminado()) {
/* 679:765 */         detalle.add(dm);
/* 680:    */       }
/* 681:    */     }
/* 682:769 */     return detalle;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public boolean isMostrarSaldoInicial()
/* 686:    */   {
/* 687:778 */     Calendar calendario = Calendar.getInstance();
/* 688:779 */     calendario.setTime(ParametrosSistema.getFechaMaximaSaldosIniciales(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 689:780 */     int day = calendario.get(5);
/* 690:781 */     int month = calendario.get(2) + 1;
/* 691:782 */     int year = calendario.get(1);
/* 692:    */     
/* 693:784 */     Date fecha = FuncionesUtiles.getFecha(day, month, year);
/* 694:    */     
/* 695:786 */     this.mostrarSaldoInicial = FuncionesUtiles.compararFechaAnteriorOIgual(new Date(), fecha);
/* 696:    */     
/* 697:788 */     return this.mostrarSaldoInicial;
/* 698:    */   }
/* 699:    */   
/* 700:    */   public void setMostrarSaldoInicial(boolean mostrarSaldoInicial)
/* 701:    */   {
/* 702:798 */     this.mostrarSaldoInicial = mostrarSaldoInicial;
/* 703:    */   }
/* 704:    */   
/* 705:    */   public List<Bodega> getListaBodega()
/* 706:    */   {
/* 707:807 */     if (this.listaBodega == null) {
/* 708:808 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 709:    */     }
/* 710:810 */     return this.listaBodega;
/* 711:    */   }
/* 712:    */   
/* 713:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 714:    */   {
/* 715:820 */     this.listaBodega = listaBodega;
/* 716:    */   }
/* 717:    */   
/* 718:    */   public DataTable getDtInventarioProductoLote()
/* 719:    */   {
/* 720:829 */     return this.dtInventarioProductoLote;
/* 721:    */   }
/* 722:    */   
/* 723:    */   public void setDtInventarioProductoLote(DataTable dtInventarioProductoLote)
/* 724:    */   {
/* 725:839 */     this.dtInventarioProductoLote = dtInventarioProductoLote;
/* 726:    */   }
/* 727:    */   
/* 728:    */   public Lote getLoteCrear()
/* 729:    */   {
/* 730:848 */     return this.loteCrear;
/* 731:    */   }
/* 732:    */   
/* 733:    */   public void setLoteCrear(Lote loteCrear)
/* 734:    */   {
/* 735:858 */     this.loteCrear = loteCrear;
/* 736:    */   }
/* 737:    */   
/* 738:    */   public String getCodigoCabecera()
/* 739:    */   {
/* 740:867 */     return this.codigoCabecera;
/* 741:    */   }
/* 742:    */   
/* 743:    */   public void setCodigoCabecera(String codigoCabecera)
/* 744:    */   {
/* 745:877 */     this.codigoCabecera = codigoCabecera;
/* 746:    */   }
/* 747:    */   
/* 748:    */   public AS2Exception getExContabilizacion()
/* 749:    */   {
/* 750:886 */     return this.exContabilizacion;
/* 751:    */   }
/* 752:    */   
/* 753:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 754:    */   {
/* 755:896 */     this.exContabilizacion = exContabilizacion;
/* 756:    */   }
/* 757:    */   
/* 758:    */   public List<DetalleMovimientoInventario> getListaEdicionDetalleMovimiento()
/* 759:    */   {
/* 760:900 */     if (this.listaEdicionDetalleMovimiento == null) {
/* 761:901 */       this.listaEdicionDetalleMovimiento = new ArrayList();
/* 762:    */     }
/* 763:903 */     return this.listaEdicionDetalleMovimiento;
/* 764:    */   }
/* 765:    */   
/* 766:    */   public void setListaEdicionDetalleMovimiento(List<DetalleMovimientoInventario> listaEdicionDetalleMovimiento)
/* 767:    */   {
/* 768:907 */     this.listaEdicionDetalleMovimiento = listaEdicionDetalleMovimiento;
/* 769:    */   }
/* 770:    */   
/* 771:    */   public DataTable getDtEdicionAjusteInventario()
/* 772:    */   {
/* 773:911 */     return this.dtEdicionAjusteInventario;
/* 774:    */   }
/* 775:    */   
/* 776:    */   public void setDtEdicionAjusteInventario(DataTable dtEdicionAjusteInventario)
/* 777:    */   {
/* 778:915 */     this.dtEdicionAjusteInventario = dtEdicionAjusteInventario;
/* 779:    */   }
/* 780:    */   
/* 781:    */   public Integer getIdAjusteInventario()
/* 782:    */   {
/* 783:919 */     return this.idAjusteInventario;
/* 784:    */   }
/* 785:    */   
/* 786:    */   public void setIdAjusteInventario(Integer idAjusteInventario)
/* 787:    */   {
/* 788:923 */     this.idAjusteInventario = idAjusteInventario;
/* 789:    */   }
/* 790:    */   
/* 791:    */   public String getRutaPlantilla()
/* 792:    */   {
/* 793:928 */     return "/resources/plantillas/inventario/AS2 Ajuste Inventario.xls";
/* 794:    */   }
/* 795:    */   
/* 796:    */   public String getNombrePlantilla()
/* 797:    */   {
/* 798:933 */     return "AS2 Ajuste Inventario.xls";
/* 799:    */   }
/* 800:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.AjusteInventarioBean
 * JD-Core Version:    0.7.0.1
 */