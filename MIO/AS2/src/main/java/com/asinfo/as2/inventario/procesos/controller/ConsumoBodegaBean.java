/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   9:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  12:    */ import com.asinfo.as2.entities.Lote;
/*  13:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  14:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*  15:    */ import com.asinfo.as2.entities.Organizacion;
/*  16:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  17:    */ import com.asinfo.as2.entities.Producto;
/*  18:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  19:    */ import com.asinfo.as2.entities.Sucursal;
/*  20:    */ import com.asinfo.as2.entities.Unidad;
/*  21:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  25:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  29:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  30:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  31:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  32:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  33:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  34:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  35:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*  36:    */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*  37:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  38:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  39:    */ import com.asinfo.as2.util.AppUtil;
/*  40:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  41:    */ import java.io.BufferedInputStream;
/*  42:    */ import java.io.InputStream;
/*  43:    */ import java.math.BigDecimal;
/*  44:    */ import java.math.RoundingMode;
/*  45:    */ import java.util.ArrayList;
/*  46:    */ import java.util.Collection;
/*  47:    */ import java.util.Date;
/*  48:    */ import java.util.HashMap;
/*  49:    */ import java.util.Iterator;
/*  50:    */ import java.util.List;
/*  51:    */ import java.util.Map;
/*  52:    */ import javax.annotation.PostConstruct;
/*  53:    */ import javax.ejb.EJB;
/*  54:    */ import javax.faces.bean.ManagedBean;
/*  55:    */ import javax.faces.bean.ViewScoped;
/*  56:    */ import javax.faces.component.html.HtmlInputText;
/*  57:    */ import javax.faces.context.FacesContext;
/*  58:    */ import javax.faces.context.PartialViewContext;
/*  59:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  60:    */ import org.apache.log4j.Logger;
/*  61:    */ import org.primefaces.component.datatable.DataTable;
/*  62:    */ import org.primefaces.context.RequestContext;
/*  63:    */ import org.primefaces.event.FileUploadEvent;
/*  64:    */ import org.primefaces.event.SelectEvent;
/*  65:    */ import org.primefaces.model.LazyDataModel;
/*  66:    */ import org.primefaces.model.SortOrder;
/*  67:    */ import org.primefaces.model.UploadedFile;
/*  68:    */ 
/*  69:    */ @ManagedBean
/*  70:    */ @ViewScoped
/*  71:    */ public class ConsumoBodegaBean
/*  72:    */   extends MovimientoInventarioBaseBean
/*  73:    */ {
/*  74:    */   private static final long serialVersionUID = 5617445651812644617L;
/*  75:    */   @EJB
/*  76:    */   private ServicioDocumento servicioDocumento;
/*  77:    */   @EJB
/*  78:    */   private ServicioProducto servicioProducto;
/*  79:    */   @EJB
/*  80:    */   private ServicioDestinoCosto servicioDestinoCosto;
/*  81:    */   @EJB
/*  82:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  83:    */   @EJB
/*  84:    */   private ServicioUnidad servicioUnidad;
/*  85:    */   @EJB
/*  86:    */   private ServicioLote servicioLote;
/*  87:    */   @EJB
/*  88:    */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  89:    */   @EJB
/*  90:    */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  91:    */   @EJB
/*  92:    */   private ServicioSucursal servicioSucursal;
/*  93:    */   private MovimientoInventario movimientoInventario;
/*  94:    */   private LazyDataModel<MovimientoInventario> listaMovimientoInventario;
/*  95:    */   private DetalleMovimientoInventario detalleMovimientoInventario;
/*  96:    */   private InventarioProducto inventarioProducto;
/*  97:    */   private List<Documento> listaDocumentoCombo;
/*  98:    */   private List<Bodega> listaBodegaCombo;
/*  99:    */   private List<Sucursal> listaSucursal;
/* 100:    */   private DataTable dtMovimientoInventario;
/* 101:    */   private DataTable dtDetalleMovimientoInventario;
/* 102:    */   private DataTable dtInventarioProductoLote;
/* 103:109 */   private String codigoCabecera = "";
/* 104:    */   private Lote loteCrear;
/* 105:    */   private DetalleMovimientoInventario detalleMovimientoInventarioSeleccionado;
/* 106:    */   private OrdenSalidaMaterial ordenSalidaMaterial;
/* 107:    */   private boolean indicadorMostrarOrdenSalidaMaterial;
/* 108:115 */   private AS2Exception exContabilizacion = new AS2Exception("");
/* 109:    */   
/* 110:    */   @PostConstruct
/* 111:    */   public void init()
/* 112:    */   {
/* 113:119 */     getListaProductoBean().setActivo(true);
/* 114:120 */     getListaProductoBean().setIndicadorConsumo(true);
/* 115:121 */     getListaProductoBean().setIndicadorVenta(false);
/* 116:122 */     getListaProductoBean().setIndicadorCompra(false);
/* 117:123 */     getListaProductoBean().setIndicadorProduccion(false);
/* 118:124 */     getListaProductoBean().setTipoProducto(null);
/* 119:125 */     getListaProductoBean().setIndicadorPesoBalanza(false);
/* 120:126 */     getListaProductoBean().setIndicadorMantenimiento(false);
/* 121:127 */     this.listaMovimientoInventario = new LazyDataModel()
/* 122:    */     {
/* 123:    */       private static final long serialVersionUID = 2418385872426391969L;
/* 124:    */       
/* 125:    */       public List<MovimientoInventario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 126:    */       {
/* 127:139 */         List<MovimientoInventario> lista = new ArrayList();
/* 128:140 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 129:142 */         if (filters == null) {
/* 130:143 */           filters = new HashMap();
/* 131:    */         }
/* 132:146 */         filters.put("documento.documentoBase", ConsumoBodegaBean.this.getDocumentoBase().toString());
/* 133:147 */         filters = ConsumoBodegaBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/* 134:148 */         lista.addAll(ConsumoBodegaBean.this.servicioMovimientoInventario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters));
/* 135:149 */         ConsumoBodegaBean.this.listaMovimientoInventario.setRowCount(ConsumoBodegaBean.this.servicioMovimientoInventario.contarPorCirterio(filters));
/* 136:    */         
/* 137:151 */         return lista;
/* 138:    */       }
/* 139:    */     };
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String editar()
/* 143:    */   {
/* 144:176 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 145:177 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void actualizarIndicadorMostrarOrdenSalidaMaterial()
/* 149:    */   {
/* 150:181 */     setIndicadorMostrarOrdenSalidaMaterial(false);
/* 151:182 */     if (this.movimientoInventario != null) {
/* 152:183 */       for (DetalleMovimientoInventario dm : this.movimientoInventario.getDetalleMovimientosInventario()) {
/* 153:184 */         if ((!dm.isEliminado()) && (dm.getDetalleOrdenSalidaMaterial() != null))
/* 154:    */         {
/* 155:185 */           setIndicadorMostrarOrdenSalidaMaterial(true);
/* 156:186 */           break;
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String guardar()
/* 163:    */   {
/* 164:    */     try
/* 165:    */     {
/* 166:200 */       this.servicioMovimientoInventario.guardar(this.movimientoInventario);
/* 167:    */       
/* 168:202 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 169:203 */       limpiar();
/* 170:204 */       setEditado(false);
/* 171:    */     }
/* 172:    */     catch (ExcepcionAS2Financiero e)
/* 173:    */     {
/* 174:207 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 175:208 */       LOG.info("ERROR AL GUARDAR CONSUMO DE BODEGA:", e);
/* 176:    */     }
/* 177:    */     catch (ExcepcionAS2Inventario e)
/* 178:    */     {
/* 179:211 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 180:212 */       LOG.info("ERROR AL GUARDAR CONSUMO DE BODEGA:", e);
/* 181:    */     }
/* 182:    */     catch (AS2Exception e)
/* 183:    */     {
/* 184:215 */       this.exContabilizacion = e;
/* 185:216 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 186:217 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 187:218 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/* 188:    */     }
/* 189:    */     catch (Exception e)
/* 190:    */     {
/* 191:221 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 192:222 */       LOG.error("ERROR AL GUARDAR CONSUMO DE BODEGA", e);
/* 193:    */     }
/* 194:224 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String eliminar()
/* 198:    */   {
/* 199:235 */     if (getMovimientoInventario().getId() != 0) {
/* 200:    */       try
/* 201:    */       {
/* 202:237 */         this.servicioMovimientoInventario.anular(this.movimientoInventario);
/* 203:238 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 204:    */       }
/* 205:    */       catch (ExcepcionAS2Financiero e)
/* 206:    */       {
/* 207:240 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 208:241 */         LOG.info("ERROR AL ELIMINAR CONSUMO CLIENTE:", e);
/* 209:    */       }
/* 210:    */       catch (ExcepcionAS2Inventario e)
/* 211:    */       {
/* 212:243 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 213:244 */         LOG.info("ERROR AL ELIMINAR CONSUMO CLIENTE:", e);
/* 214:    */       }
/* 215:    */       catch (Exception e)
/* 216:    */       {
/* 217:246 */         e.printStackTrace();
/* 218:247 */         LOG.error("ERROR AL ELIMINAR CONSUMO CLIENTE:", e);
/* 219:    */       }
/* 220:    */     } else {
/* 221:250 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 222:    */     }
/* 223:252 */     return "";
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String limpiar()
/* 227:    */   {
/* 228:262 */     creaConsumoBodega();
/* 229:263 */     return "";
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String cargarDatos()
/* 233:    */   {
/* 234:273 */     return "";
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void creaConsumoBodega()
/* 238:    */   {
/* 239:280 */     this.ordenSalidaMaterial = null;
/* 240:281 */     setIndicadorMostrarOrdenSalidaMaterial(false);
/* 241:    */     
/* 242:283 */     this.movimientoInventario = new MovimientoInventario();
/* 243:284 */     this.movimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 244:285 */     this.movimientoInventario.setSucursal(AppUtil.getSucursal());
/* 245:286 */     this.movimientoInventario.setEstado(Estado.ELABORADO);
/* 246:288 */     if (!getListaDocumentoCombo().isEmpty())
/* 247:    */     {
/* 248:289 */       Documento documento = (Documento)getListaDocumentoCombo().get(0);
/* 249:290 */       this.movimientoInventario.setDocumento(documento);
/* 250:291 */       actualizarDocumento();
/* 251:    */     }
/* 252:294 */     this.movimientoInventario.setFecha(new Date());
/* 253:295 */     this.movimientoInventario.setNumero("");
/* 254:    */   }
/* 255:    */   
/* 256:    */   public DetalleMovimientoInventario creaDetalleConsumoBodega()
/* 257:    */   {
/* 258:300 */     this.detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 259:301 */     this.detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 260:302 */     this.detalleMovimientoInventario.setIdSucursal(this.movimientoInventario.getSucursal().getId());
/* 261:303 */     this.detalleMovimientoInventario.setBodegaOrigen(AppUtil.getBodega());
/* 262:304 */     this.detalleMovimientoInventario.setProducto(new Producto());
/* 263:305 */     this.detalleMovimientoInventario.setMovimientoInventario(this.movimientoInventario);
/* 264:    */     
/* 265:307 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* 266:308 */     inventarioProducto.setOperacion(this.movimientoInventario.getDocumento().getOperacion());
/* 267:309 */     inventarioProducto.setProducto(this.detalleMovimientoInventario.getProducto());
/* 268:    */     
/* 269:311 */     this.detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 270:313 */     if (!getMovimientoInventario().getDetalleMovimientosInventario().isEmpty()) {
/* 271:314 */       this.detalleMovimientoInventario.setDestinoCosto(((DetalleMovimientoInventario)getMovimientoInventario().getDetalleMovimientosInventario().get(0)).getDestinoCosto());
/* 272:    */     }
/* 273:317 */     return this.detalleMovimientoInventario;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String creacionLote()
/* 277:    */   {
/* 278:321 */     this.detalleMovimientoInventarioSeleccionado = ((DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData());
/* 279:322 */     this.loteCrear = new Lote();
/* 280:323 */     this.loteCrear.setActivo(true);
/* 281:324 */     this.loteCrear.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 282:325 */     this.loteCrear.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 283:326 */     this.loteCrear.setProducto(this.detalleMovimientoInventarioSeleccionado.getProducto());
/* 284:327 */     return "";
/* 285:    */   }
/* 286:    */   
/* 287:    */   public String guardarLote()
/* 288:    */   {
/* 289:    */     try
/* 290:    */     {
/* 291:332 */       this.loteCrear.setCodigo(this.loteCrear.getProducto().getPrefijoLote() + this.loteCrear.getCodigo());
/* 292:333 */       this.servicioLote.guardar(this.loteCrear);
/* 293:334 */       this.loteCrear.getProducto().setPrefijoLote(null);
/* 294:335 */       this.detalleMovimientoInventarioSeleccionado.getInventarioProducto().setLote(this.loteCrear);
/* 295:336 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 296:    */     }
/* 297:    */     catch (Exception e)
/* 298:    */     {
/* 299:338 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 300:339 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 301:    */     }
/* 302:341 */     return "";
/* 303:    */   }
/* 304:    */   
/* 305:    */   public List<DetalleMovimientoInventario> getListaDetalleMovimientoInventario()
/* 306:    */   {
/* 307:350 */     List<DetalleMovimientoInventario> listaDetalleMovimientoInventario = new ArrayList();
/* 308:351 */     for (DetalleMovimientoInventario detalleMovimientoInventario : getMovimientoInventario().getDetalleMovimientosInventario()) {
/* 309:352 */       if (!detalleMovimientoInventario.isEliminado()) {
/* 310:353 */         listaDetalleMovimientoInventario.add(detalleMovimientoInventario);
/* 311:    */       }
/* 312:    */     }
/* 313:356 */     return listaDetalleMovimientoInventario;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public String agregarDetalle()
/* 317:    */   {
/* 318:365 */     creaDetalleConsumoBodega();
/* 319:366 */     getMovimientoInventario().getDetalleMovimientosInventario().add(this.detalleMovimientoInventario);
/* 320:367 */     return "";
/* 321:    */   }
/* 322:    */   
/* 323:    */   public String generaDistribucion()
/* 324:    */   {
/* 325:376 */     this.detalleMovimientoInventario = ((DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData());
/* 326:377 */     return "";
/* 327:    */   }
/* 328:    */   
/* 329:    */   public String eliminarDetalle()
/* 330:    */   {
/* 331:382 */     DetalleMovimientoInventario dmi = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/* 332:383 */     if (dmi.getInventarioProducto() != null) {
/* 333:384 */       dmi.getInventarioProducto().setEliminado(true);
/* 334:    */     }
/* 335:386 */     dmi.setEliminado(true);
/* 336:387 */     actualizarIndicadorMostrarOrdenSalidaMaterial();
/* 337:388 */     return "";
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 341:    */   {
/* 342:397 */     DetalleMovimientoInventario dmi = null;
/* 343:398 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 344:    */     try
/* 345:    */     {
/* 346:400 */       dmi = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/* 347:401 */       cargarProductoDesdeCodigoEnDetalle(codigo, this.detalleMovimientoInventario);
/* 348:    */     }
/* 349:    */     catch (ExcepcionAS2 e)
/* 350:    */     {
/* 351:403 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 352:404 */       dmi.getProducto().setCodigo("");
/* 353:405 */       dmi.getProducto().setNombre("");
/* 354:    */     }
/* 355:    */     catch (Exception ex)
/* 356:    */     {
/* 357:407 */       LOG.error("Error al cargar el producto por codigo: " + ex);
/* 358:408 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 359:409 */       dmi.getProducto().setCodigo("");
/* 360:410 */       dmi.getProducto().setNombre("");
/* 361:    */     }
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void actualizarSaldoLote(SelectEvent event)
/* 365:    */   {
/* 366:415 */     DetalleMovimientoInventario detalleMovimientoInventario = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/* 367:416 */     Lote lote = (Lote)event.getObject();
/* 368:417 */     actualizarSaldo(detalleMovimientoInventario, lote);
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void actualizarSaldo()
/* 372:    */   {
/* 373:421 */     DetalleMovimientoInventario detalleMovimientoInventario = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/* 374:422 */     actualizarSaldo(detalleMovimientoInventario, null);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void actualizarSaldo(DetalleMovimientoInventario dmi, Lote lote)
/* 378:    */   {
/* 379:426 */     Bodega bodega = dmi.getBodegaOrigen();
/* 380:427 */     if (lote != null)
/* 381:    */     {
/* 382:428 */       dmi.getInventarioProducto().setLote(lote);
/* 383:    */       
/* 384:430 */       BigDecimal saldo = this.servicioProducto.getSaldoLote(dmi.getProducto().getIdProducto(), dmi.getBodegaOrigen().getIdBodega(), dmi
/* 385:431 */         .getInventarioProducto().getLote().getIdLote(), this.movimientoInventario.getFecha());
/* 386:432 */       dmi.setSaldo(saldo);
/* 387:    */     }
/* 388:    */     else
/* 389:    */     {
/* 390:434 */       BigDecimal saldoBodega = this.servicioProducto.getSaldo(dmi.getProducto().getId(), bodega == null ? 0 : bodega.getId(), this.movimientoInventario
/* 391:435 */         .getFecha());
/* 392:436 */       dmi.setSaldo(saldoBodega);
/* 393:    */     }
/* 394:    */   }
/* 395:    */   
/* 396:    */   private void cargarProductoDesdeCodigoEnDetalle(String codigo, DetalleMovimientoInventario detalleMovimientoInventario)
/* 397:    */     throws ExcepcionAS2
/* 398:    */   {
/* 399:442 */     Producto producto = null;
/* 400:443 */     producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.CONSUMO_BODEGA);
/* 401:444 */     this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 402:445 */     detalleMovimientoInventario.setProducto(producto);
/* 403:    */     
/* 404:    */ 
/* 405:448 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* 406:449 */     inventarioProducto.setOperacion(this.movimientoInventario.getDocumento().getOperacion());
/* 407:450 */     inventarioProducto.setProducto(detalleMovimientoInventario.getProducto());
/* 408:451 */     detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 409:453 */     if (producto.getLote() != null)
/* 410:    */     {
/* 411:454 */       detalleMovimientoInventario.getInventarioProducto().setLote(producto.getLote());
/* 412:455 */       BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), detalleMovimientoInventario.getBodegaOrigen().getIdBodega(), producto
/* 413:456 */         .getLote().getIdLote(), this.movimientoInventario.getFecha());
/* 414:457 */       detalleMovimientoInventario.setSaldo(saldo);
/* 415:459 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/* 416:460 */         detalleMovimientoInventario.setCantidadOrigen(saldo);
/* 417:    */       }
/* 418:    */     }
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void agregarDetalleDesdeCodigoCabecera()
/* 422:    */   {
/* 423:467 */     agregarDetalle();
/* 424:    */     try
/* 425:    */     {
/* 426:469 */       cargarProductoDesdeCodigoEnDetalle(this.codigoCabecera, this.detalleMovimientoInventario);
/* 427:470 */       this.codigoCabecera = "";
/* 428:    */     }
/* 429:    */     catch (ExcepcionAS2 e)
/* 430:    */     {
/* 431:472 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 432:473 */       this.detalleMovimientoInventario.getProducto().setCodigo("");
/* 433:474 */       this.detalleMovimientoInventario.getProducto().setNombre("");
/* 434:475 */       this.detalleMovimientoInventario.setEliminado(true);
/* 435:476 */       this.codigoCabecera = "";
/* 436:    */     }
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void actualizarCantidadDetalleMovimientoInventario(AjaxBehaviorEvent event)
/* 440:    */   {
/* 441:486 */     DetalleMovimientoInventario dmi = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/* 442:487 */     actualizarCantidadDetalleMovimientoInventario(dmi);
/* 443:    */   }
/* 444:    */   
/* 445:    */   protected void actualizarCantidadDetalleMovimientoInventario(DetalleMovimientoInventario dmi)
/* 446:    */   {
/* 447:    */     try
/* 448:    */     {
/* 449:497 */       calculaConversion(dmi);
/* 450:    */     }
/* 451:    */     catch (Exception e)
/* 452:    */     {
/* 453:499 */       e.printStackTrace();
/* 454:500 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 455:    */     }
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void calculaConversion(DetalleMovimientoInventario dmi)
/* 459:    */   {
/* 460:512 */     BigDecimal cantidadOrigen = dmi.getCantidadOrigen();
/* 461:513 */     dmi.setCantidad(cantidadOrigen);
/* 462:    */     try
/* 463:    */     {
/* 464:517 */       BigDecimal valorConversion = this.servicioProducto.convierteUnidad(dmi.getUnidadConversion(), dmi.getProducto().getUnidad(), dmi.getProducto()
/* 465:518 */         .getIdProducto(), dmi.getCantidadOrigen());
/* 466:519 */       dmi.setTraCantidadCoversion(valorConversion);
/* 467:    */     }
/* 468:    */     catch (ExcepcionAS2Inventario e)
/* 469:    */     {
/* 470:522 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 471:    */     }
/* 472:    */     catch (Exception e)
/* 473:    */     {
/* 474:524 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/* 475:525 */       e.printStackTrace();
/* 476:    */     }
/* 477:    */   }
/* 478:    */   
/* 479:    */   public String actualizarDocumento()
/* 480:    */   {
/* 481:536 */     Integer idDocumento = Integer.valueOf(getMovimientoInventario().getDocumento().getIdDocumento());
/* 482:537 */     this.movimientoInventario.setDocumento(this.servicioDocumento.buscarPorId(idDocumento));
/* 483:    */     
/* 484:539 */     setSecuenciaEditable(!this.movimientoInventario.getDocumento().isIndicadorBloqueoSecuencia());
/* 485:    */     
/* 486:541 */     return "";
/* 487:    */   }
/* 488:    */   
/* 489:    */   public List<DestinoCosto> autocompletarDestinoCosto(String consulta)
/* 490:    */   {
/* 491:552 */     consulta = consulta.toUpperCase();
/* 492:553 */     List<DestinoCosto> lista = this.servicioDestinoCosto.autocompletarDestinoCosto(consulta);
/* 493:554 */     return lista;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(String numero)
/* 497:    */   {
/* 498:566 */     if (ParametrosSistema.getAprobarOrdenSalidaMaterial(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 499:567 */       return this.servicioOrdenSalidaMaterial.autocompletarOrdenSalidaMaterial(AppUtil.getOrganizacion().getId(), numero, Boolean.valueOf(false), Boolean.valueOf(true));
/* 500:    */     }
/* 501:569 */     return this.servicioOrdenSalidaMaterial.autocompletarOrdenSalidaMaterial(AppUtil.getOrganizacion().getId(), numero, Boolean.valueOf(false));
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void cargaListaConversionUnidad(MovimientoInventario movimientoInventario)
/* 505:    */   {
/* 506:578 */     for (Iterator localIterator1 = movimientoInventario.getDetalleMovimientosInventario().iterator(); localIterator1.hasNext();)
/* 507:    */     {
/* 508:578 */       dmi = (DetalleMovimientoInventario)localIterator1.next();
/* 509:579 */       List<Unidad> listaUnidadConversion = this.servicioUnidad.obtenerListaUnidadPorUnidadOrigen(dmi.getProducto().getUnidad().getIdUnidad());
/* 510:580 */       dmi.getTraListaUnidadConversion().add(dmi.getProducto().getUnidad());
/* 511:581 */       for (Unidad unidad : listaUnidadConversion) {
/* 512:582 */         dmi.getTraListaUnidadConversion().add(unidad);
/* 513:    */       }
/* 514:    */     }
/* 515:    */     DetalleMovimientoInventario dmi;
/* 516:    */   }
/* 517:    */   
/* 518:    */   public void cargarProducto()
/* 519:    */   {
/* 520:    */     try
/* 521:    */     {
/* 522:592 */       Producto producto = getListaProductoBean().getProducto();
/* 523:593 */       if (producto != null)
/* 524:    */       {
/* 525:595 */         DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 526:596 */         detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 527:597 */         detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 528:598 */         detalleMovimientoInventario.setProducto(producto);
/* 529:599 */         detalleMovimientoInventario.setBodegaOrigen(AppUtil.getBodega());
/* 530:600 */         BigDecimal saldoBodega = this.servicioProducto.getSaldo(producto.getId(), AppUtil.getBodega() == null ? 0 : AppUtil.getBodega().getId(), this.movimientoInventario
/* 531:601 */           .getFecha());
/* 532:602 */         detalleMovimientoInventario.setSaldo(saldoBodega);
/* 533:603 */         detalleMovimientoInventario.setMovimientoInventario(this.movimientoInventario);
/* 534:604 */         detalleMovimientoInventario.setCantidad(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/* 535:605 */         detalleMovimientoInventario.setCantidadOrigen(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/* 536:    */         
/* 537:607 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 538:608 */         inventarioProducto.setOperacion(this.movimientoInventario.getDocumento().getOperacion());
/* 539:609 */         inventarioProducto.setProducto(detalleMovimientoInventario.getProducto());
/* 540:610 */         inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 541:611 */         detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 542:612 */         this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 543:613 */         detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 544:615 */         if (getListaProductoBean().getSaldoProductoLote() != null)
/* 545:    */         {
/* 546:616 */           detalleMovimientoInventario.setBodegaOrigen(getListaProductoBean().getSaldoProductoLote().getBodega());
/* 547:617 */           detalleMovimientoInventario.setCantidad(getListaProductoBean().getSaldoProductoLote().getSaldo()
/* 548:618 */             .setScale(2, RoundingMode.HALF_UP));
/* 549:619 */           detalleMovimientoInventario.setSaldo(detalleMovimientoInventario.getCantidad());
/* 550:620 */           inventarioProducto.setBodega(detalleMovimientoInventario.getBodegaOrigen());
/* 551:621 */           inventarioProducto.setLote(getListaProductoBean().getSaldoProductoLote().getLote());
/* 552:622 */           inventarioProducto.setCantidad(detalleMovimientoInventario.getCantidad());
/* 553:    */         }
/* 554:624 */         replicarDestinoCosto(detalleMovimientoInventario);
/* 555:625 */         this.movimientoInventario.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 556:    */       }
/* 557:628 */       getListaProductoBean().setProducto(null);
/* 558:    */     }
/* 559:    */     catch (Exception e)
/* 560:    */     {
/* 561:631 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 562:632 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 563:    */     }
/* 564:    */     finally
/* 565:    */     {
/* 566:634 */       getListaProductoBean().setProducto(null);
/* 567:635 */       getListaProductoBean().setSaldoProductoLote(null);
/* 568:    */     }
/* 569:    */   }
/* 570:    */   
/* 571:    */   private void replicarDestinoCosto(DetalleMovimientoInventario detalleMovimientoInventario)
/* 572:    */   {
/* 573:640 */     if (!getMovimientoInventario().getDetalleMovimientosInventario().isEmpty()) {
/* 574:641 */       detalleMovimientoInventario.setDestinoCosto(((DetalleMovimientoInventario)getMovimientoInventario().getDetalleMovimientosInventario().get(0)).getDestinoCosto());
/* 575:    */     }
/* 576:    */   }
/* 577:    */   
/* 578:    */   public String cargarDetalleConsumoBodega(FileUploadEvent event)
/* 579:    */   {
/* 580:    */     try
/* 581:    */     {
/* 582:647 */       String fileName = "movimiento_inventario" + event.getFile().getFileName();
/* 583:648 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 584:649 */       this.servicioMovimientoInventario.cargarDetalleConsumoBodega(this.movimientoInventario, fileName, input, 5);
/* 585:650 */       for (DetalleMovimientoInventario dmi : this.movimientoInventario.getDetalleMovimientosInventario())
/* 586:    */       {
/* 587:651 */         actualizarCantidadDetalleMovimientoInventario(dmi);
/* 588:652 */         actualizarSaldo(dmi, null);
/* 589:    */       }
/* 590:654 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 591:    */     }
/* 592:    */     catch (ExcepcionAS2Financiero e)
/* 593:    */     {
/* 594:657 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 595:658 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 596:    */     }
/* 597:    */     catch (ExcepcionAS2Compras e)
/* 598:    */     {
/* 599:661 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 600:662 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 601:    */     }
/* 602:    */     catch (ExcepcionAS2 e)
/* 603:    */     {
/* 604:665 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 605:666 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 606:    */     }
/* 607:    */     catch (Exception e)
/* 608:    */     {
/* 609:669 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 610:    */     }
/* 611:671 */     return null;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public void cargarDetalleDesdeOrdenSalidaMaterial(SelectEvent event)
/* 615:    */   {
/* 616:676 */     OrdenSalidaMaterial ordenSalidaMaterial = (OrdenSalidaMaterial)event.getObject();
/* 617:678 */     if ((getMovimientoInventario() != null) && (ordenSalidaMaterial != null)) {
/* 618:    */       try
/* 619:    */       {
/* 620:682 */         this.servicioOrdenSalidaMaterial.generarConsumoBodega(ordenSalidaMaterial, this.movimientoInventario, false, DocumentoBase.CONSUMO_BODEGA);
/* 621:    */       }
/* 622:    */       catch (ExcepcionAS2 e)
/* 623:    */       {
/* 624:685 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 625:686 */         LOG.info("ERROR AL CARGAR ORDEN-SALIDA-MERCADERIA CONSUMO DE BODEGA:", e);
/* 626:    */       }
/* 627:    */     }
/* 628:690 */     actualizarIndicadorMostrarOrdenSalidaMaterial();
/* 629:    */   }
/* 630:    */   
/* 631:    */   public String copiar()
/* 632:    */   {
/* 633:    */     try
/* 634:    */     {
/* 635:695 */       MovimientoInventario auxMovimientoInventarioCopias = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(this.movimientoInventario.getId()));
/* 636:696 */       this.movimientoInventario = this.servicioMovimientoInventario.copiarMovimientoInventario(auxMovimientoInventarioCopias);
/* 637:697 */       for (DetalleMovimientoInventario de : this.movimientoInventario.getDetalleMovimientosInventario()) {
/* 638:    */         try
/* 639:    */         {
/* 640:699 */           cargarProductoDesdeCodigoEnDetalle(de.getProducto().getCodigo(), de);
/* 641:    */         }
/* 642:    */         catch (ExcepcionAS2 e)
/* 643:    */         {
/* 644:701 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 645:702 */           e.printStackTrace();
/* 646:    */         }
/* 647:    */       }
/* 648:706 */       setEditado(true);
/* 649:    */     }
/* 650:    */     catch (ExcepcionAS2Financiero e)
/* 651:    */     {
/* 652:709 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 653:    */     }
/* 654:712 */     return "";
/* 655:    */   }
/* 656:    */   
/* 657:    */   public List<Lote> autocompletarLotes(String consulta)
/* 658:    */   {
/* 659:716 */     DetalleMovimientoInventario detalleMovimientoInventario = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/* 660:717 */     return this.servicioLote.autocompletarLote(detalleMovimientoInventario.getProducto(), consulta);
/* 661:    */   }
/* 662:    */   
/* 663:    */   public DocumentoBase getDocumentoBase()
/* 664:    */   {
/* 665:721 */     return DocumentoBase.CONSUMO_BODEGA;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public MovimientoInventario getMovimientoInventario()
/* 669:    */   {
/* 670:725 */     if (this.movimientoInventario == null) {
/* 671:726 */       creaConsumoBodega();
/* 672:    */     }
/* 673:728 */     return this.movimientoInventario;
/* 674:    */   }
/* 675:    */   
/* 676:    */   public void setMovimientoInventario(MovimientoInventario movimientoInventario)
/* 677:    */   {
/* 678:732 */     this.movimientoInventario = movimientoInventario;
/* 679:    */   }
/* 680:    */   
/* 681:    */   public DetalleMovimientoInventario getDetalleMovimientoInventario()
/* 682:    */   {
/* 683:736 */     if (this.detalleMovimientoInventario == null) {
/* 684:737 */       creaDetalleConsumoBodega();
/* 685:    */     }
/* 686:739 */     return this.detalleMovimientoInventario;
/* 687:    */   }
/* 688:    */   
/* 689:    */   public void setDetalleMovimientoInventario(DetalleMovimientoInventario detalleMovimientoInventario)
/* 690:    */   {
/* 691:743 */     this.detalleMovimientoInventario = detalleMovimientoInventario;
/* 692:    */   }
/* 693:    */   
/* 694:    */   public List<Documento> getListaDocumentoCombo()
/* 695:    */   {
/* 696:    */     try
/* 697:    */     {
/* 698:754 */       if (this.listaDocumentoCombo == null) {
/* 699:755 */         this.listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(getDocumentoBase(), AppUtil.getOrganizacion().getId());
/* 700:    */       }
/* 701:    */     }
/* 702:    */     catch (ExcepcionAS2 e)
/* 703:    */     {
/* 704:758 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 705:    */     }
/* 706:760 */     return this.listaDocumentoCombo;
/* 707:    */   }
/* 708:    */   
/* 709:    */   public void setListaDocumentoCombo(List<Documento> listaDocumentoCombo)
/* 710:    */   {
/* 711:764 */     this.listaDocumentoCombo = listaDocumentoCombo;
/* 712:    */   }
/* 713:    */   
/* 714:    */   public List<Bodega> getListaBodegaCombo()
/* 715:    */   {
/* 716:773 */     if (this.listaBodegaCombo == null) {
/* 717:774 */       this.listaBodegaCombo = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 718:    */     }
/* 719:776 */     return this.listaBodegaCombo;
/* 720:    */   }
/* 721:    */   
/* 722:    */   public void setListaBodegaCombo(List<Bodega> listaBodegaCombo)
/* 723:    */   {
/* 724:780 */     this.listaBodegaCombo = listaBodegaCombo;
/* 725:    */   }
/* 726:    */   
/* 727:    */   public LazyDataModel<MovimientoInventario> getListaMovimientoInventario()
/* 728:    */   {
/* 729:784 */     return this.listaMovimientoInventario;
/* 730:    */   }
/* 731:    */   
/* 732:    */   public void setListaMovimientoInventario(LazyDataModel<MovimientoInventario> listaMovimientoInventario)
/* 733:    */   {
/* 734:788 */     this.listaMovimientoInventario = listaMovimientoInventario;
/* 735:    */   }
/* 736:    */   
/* 737:    */   public DataTable getDtMovimientoInventario()
/* 738:    */   {
/* 739:792 */     return this.dtMovimientoInventario;
/* 740:    */   }
/* 741:    */   
/* 742:    */   public void setDtMovimientoInventario(DataTable dtMovimientoInventario)
/* 743:    */   {
/* 744:796 */     this.dtMovimientoInventario = dtMovimientoInventario;
/* 745:    */   }
/* 746:    */   
/* 747:    */   public DataTable getDtDetalleMovimientoInventario()
/* 748:    */   {
/* 749:800 */     return this.dtDetalleMovimientoInventario;
/* 750:    */   }
/* 751:    */   
/* 752:    */   public void setDtDetalleMovimientoInventario(DataTable dtDetalleMovimientoInventario)
/* 753:    */   {
/* 754:804 */     this.dtDetalleMovimientoInventario = dtDetalleMovimientoInventario;
/* 755:    */   }
/* 756:    */   
/* 757:    */   public DataTable getDtInventarioProductoLote()
/* 758:    */   {
/* 759:813 */     return this.dtInventarioProductoLote;
/* 760:    */   }
/* 761:    */   
/* 762:    */   public void setDtInventarioProductoLote(DataTable dtInventarioProductoLote)
/* 763:    */   {
/* 764:823 */     this.dtInventarioProductoLote = dtInventarioProductoLote;
/* 765:    */   }
/* 766:    */   
/* 767:    */   public InventarioProducto getInventarioProducto()
/* 768:    */   {
/* 769:832 */     if (this.inventarioProducto == null)
/* 770:    */     {
/* 771:833 */       this.inventarioProducto = new InventarioProducto();
/* 772:834 */       this.inventarioProducto.setOperacion(this.movimientoInventario.getDocumento().getOperacion());
/* 773:    */     }
/* 774:836 */     return this.inventarioProducto;
/* 775:    */   }
/* 776:    */   
/* 777:    */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 778:    */   {
/* 779:846 */     this.inventarioProducto = inventarioProducto;
/* 780:    */   }
/* 781:    */   
/* 782:    */   public String getCodigoCabecera()
/* 783:    */   {
/* 784:855 */     return this.codigoCabecera;
/* 785:    */   }
/* 786:    */   
/* 787:    */   public void setCodigoCabecera(String codigoCabecera)
/* 788:    */   {
/* 789:865 */     this.codigoCabecera = codigoCabecera;
/* 790:    */   }
/* 791:    */   
/* 792:    */   public Lote getLoteCrear()
/* 793:    */   {
/* 794:874 */     if (this.loteCrear == null) {
/* 795:875 */       this.loteCrear = new Lote();
/* 796:    */     }
/* 797:877 */     return this.loteCrear;
/* 798:    */   }
/* 799:    */   
/* 800:    */   public void setLoteCrear(Lote loteCrear)
/* 801:    */   {
/* 802:887 */     this.loteCrear = loteCrear;
/* 803:    */   }
/* 804:    */   
/* 805:    */   public DetalleMovimientoInventario getDetalleMovimientoInventarioSeleccionado()
/* 806:    */   {
/* 807:896 */     return this.detalleMovimientoInventarioSeleccionado;
/* 808:    */   }
/* 809:    */   
/* 810:    */   public void setDetalleMovimientoInventarioSeleccionado(DetalleMovimientoInventario detalleMovimientoInventarioSeleccionado)
/* 811:    */   {
/* 812:906 */     this.detalleMovimientoInventarioSeleccionado = detalleMovimientoInventarioSeleccionado;
/* 813:    */   }
/* 814:    */   
/* 815:    */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/* 816:    */   {
/* 817:910 */     return this.ordenSalidaMaterial;
/* 818:    */   }
/* 819:    */   
/* 820:    */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 821:    */   {
/* 822:914 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/* 823:    */   }
/* 824:    */   
/* 825:    */   public boolean isIndicadorMostrarOrdenSalidaMaterial()
/* 826:    */   {
/* 827:918 */     return this.indicadorMostrarOrdenSalidaMaterial;
/* 828:    */   }
/* 829:    */   
/* 830:    */   public void setIndicadorMostrarOrdenSalidaMaterial(boolean indicadorMostrarOrdenSalidaMaterial)
/* 831:    */   {
/* 832:922 */     this.indicadorMostrarOrdenSalidaMaterial = indicadorMostrarOrdenSalidaMaterial;
/* 833:    */   }
/* 834:    */   
/* 835:    */   public List<OrdenTrabajoMantenimiento> autocompletarOrdenTrabajo(String consulta)
/* 836:    */   {
/* 837:926 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 838:927 */     filtros.put("estado", Estado.PROCESADO.toString());
/* 839:928 */     filtros.put("numero", "%" + consulta + "%");
/* 840:929 */     return this.servicioOrdenTrabajoMantenimiento.obtenerListaCombo("fechaMantenimiento", true, filtros);
/* 841:    */   }
/* 842:    */   
/* 843:    */   public void actualizarOrdenTrabajoMantenimiento()
/* 844:    */   {
/* 845:933 */     if (getMovimientoInventario().getOrdenTrabajoMantenimiento() != null) {
/* 846:    */       try
/* 847:    */       {
/* 848:935 */         this.servicioOrdenTrabajoMantenimiento.cargarOrdenTrabajoEnConsumoBodega(this.movimientoInventario, this.movimientoInventario
/* 849:936 */           .getOrdenTrabajoMantenimiento(), AppUtil.getBodega());
/* 850:    */       }
/* 851:    */       catch (ExcepcionAS2 e)
/* 852:    */       {
/* 853:938 */         e.printStackTrace();
/* 854:939 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 855:    */       }
/* 856:    */     }
/* 857:    */   }
/* 858:    */   
/* 859:    */   public List<Sucursal> getListaSucursal()
/* 860:    */   {
/* 861:951 */     if (this.listaSucursal == null)
/* 862:    */     {
/* 863:952 */       Map<String, String> filters = new HashMap();
/* 864:953 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 865:954 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("codigo", true, filters);
/* 866:    */     }
/* 867:956 */     return this.listaSucursal;
/* 868:    */   }
/* 869:    */   
/* 870:    */   public AS2Exception getExContabilizacion()
/* 871:    */   {
/* 872:963 */     return this.exContabilizacion;
/* 873:    */   }
/* 874:    */   
/* 875:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 876:    */   {
/* 877:971 */     this.exContabilizacion = exContabilizacion;
/* 878:    */   }
/* 879:    */   
/* 880:    */   public String getRutaPlantilla()
/* 881:    */   {
/* 882:979 */     return "/resources/plantillas/inventario/AS2 Consumo Bodega.xls";
/* 883:    */   }
/* 884:    */   
/* 885:    */   public String getNombrePlantilla()
/* 886:    */   {
/* 887:987 */     return "AS2 Consumo Bodega.xls";
/* 888:    */   }
/* 889:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.ConsumoBodegaBean
 * JD-Core Version:    0.7.0.1
 */