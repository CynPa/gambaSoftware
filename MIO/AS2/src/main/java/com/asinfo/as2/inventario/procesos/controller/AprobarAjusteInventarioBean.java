/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  10:    */ import com.asinfo.as2.entities.Lote;
/*  11:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
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
/*  26:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  29:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  30:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  31:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  32:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  33:    */ import com.asinfo.as2.util.AppUtil;
/*  34:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  35:    */ import com.asinfo.as2.utils.JsfUtil;
/*  36:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  37:    */ import java.math.BigDecimal;
/*  38:    */ import java.math.RoundingMode;
/*  39:    */ import java.util.ArrayList;
/*  40:    */ import java.util.Calendar;
/*  41:    */ import java.util.Date;
/*  42:    */ import java.util.HashMap;
/*  43:    */ import java.util.List;
/*  44:    */ import java.util.Map;
/*  45:    */ import javax.annotation.PostConstruct;
/*  46:    */ import javax.ejb.EJB;
/*  47:    */ import javax.faces.bean.ManagedBean;
/*  48:    */ import javax.faces.bean.ViewScoped;
/*  49:    */ import org.apache.log4j.Logger;
/*  50:    */ import org.primefaces.component.datatable.DataTable;
/*  51:    */ import org.primefaces.model.LazyDataModel;
/*  52:    */ import org.primefaces.model.SortOrder;
/*  53:    */ 
/*  54:    */ @ManagedBean
/*  55:    */ @ViewScoped
/*  56:    */ public class AprobarAjusteInventarioBean
/*  57:    */   extends MovimientoInventarioBaseBean
/*  58:    */ {
/*  59:    */   private static final long serialVersionUID = -5463907651730263568L;
/*  60:    */   @EJB
/*  61:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*  62:    */   @EJB
/*  63:    */   private ServicioProducto servicioProducto;
/*  64:    */   @EJB
/*  65:    */   private ServicioDocumento servicioDocumento;
/*  66:    */   @EJB
/*  67:    */   private ServicioMotivoAjusteInventario servicioMotivoAjusteInventario;
/*  68:    */   @EJB
/*  69:    */   private ServicioLote servicioLote;
/*  70:    */   @EJB
/*  71:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  72:    */   @EJB
/*  73:    */   private ServicioOrganizacion servicioOrganizacion;
/*  74:    */   private MovimientoInventario ajusteInventario;
/*  75:    */   private LazyDataModel<MovimientoInventario> listaAjusteInventario;
/*  76:    */   private List<Documento> listaDocumentosAjuste;
/*  77:    */   private List<MotivoAjusteInventario> listaMotivoAjusteInventario;
/*  78:    */   private DataTable dtListaAjuste;
/*  79:    */   private DataTable dtDetalleAjuste;
/*  80:    */   private DataTable dtInventarioProductoLote;
/*  81:    */   private List<Bodega> listaBodega;
/*  82:    */   private boolean mostrarSaldoInicial;
/*  83:    */   private Lote loteCrear;
/*  84:    */   private String codigoCabecera;
/*  85:    */   private DetalleMovimientoInventario detalleMovimientoInventario;
/*  86:    */   
/*  87:    */   @PostConstruct
/*  88:    */   public void init()
/*  89:    */   {
/*  90:110 */     getListaProductoBean().setActivo(true);
/*  91:111 */     this.listaAjusteInventario = new LazyDataModel()
/*  92:    */     {
/*  93:    */       private static final long serialVersionUID = 1L;
/*  94:    */       
/*  95:    */       public List<MovimientoInventario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  96:    */       {
/*  97:118 */         List<MovimientoInventario> lista = new ArrayList();
/*  98:    */         
/*  99:120 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 100:122 */         if (filters == null) {
/* 101:123 */           filters = new HashMap();
/* 102:    */         }
/* 103:126 */         filters.put("documento.documentoBase", AprobarAjusteInventarioBean.this.getDocumentoBase().toString());
/* 104:127 */         filters.put("estado", Estado.valueOf("ELABORADO").toString());
/* 105:128 */         filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 106:129 */         lista.addAll(AprobarAjusteInventarioBean.this.servicioMovimientoInventario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters));
/* 107:    */         
/* 108:131 */         AprobarAjusteInventarioBean.this.listaAjusteInventario.setRowCount(AprobarAjusteInventarioBean.this.servicioMovimientoInventario.contarPorCirterio(filters));
/* 109:    */         
/* 110:133 */         return lista;
/* 111:    */       }
/* 112:    */     };
/* 113:    */   }
/* 114:    */   
/* 115:    */   public DocumentoBase getDocumentoBase()
/* 116:    */   {
/* 117:140 */     return DocumentoBase.AJUSTE_INVENTARIO;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String editar()
/* 121:    */   {
/* 122:153 */     if ((getAjusteInventario() != null) && (getAjusteInventario().getIdMovimientoInventario() > 0)) {
/* 123:    */       try
/* 124:    */       {
/* 125:161 */         this.ajusteInventario = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(getAjusteInventario().getId()));
/* 126:162 */         for (DetalleMovimientoInventario detalle : this.ajusteInventario.getDetalleMovimientosInventario()) {
/* 127:    */           try
/* 128:    */           {
/* 129:164 */             this.servicioUnidadConversion.cargarListaUnidadConversion(detalle.getProducto());
/* 130:    */           }
/* 131:    */           catch (ExcepcionAS2 e)
/* 132:    */           {
/* 133:166 */             addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 134:    */           }
/* 135:    */         }
/* 136:170 */         setEditado(true);
/* 137:    */       }
/* 138:    */       catch (Exception e)
/* 139:    */       {
/* 140:177 */         addErrorMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 141:    */       }
/* 142:    */     } else {
/* 143:181 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 144:    */     }
/* 145:184 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void actualizarProducto(DetalleMovimientoInventario detalleMovimientoInventario)
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:189 */       String codigo = detalleMovimientoInventario.getProducto().getCodigo();
/* 153:190 */       cargarProductoDesdeCodigoEnDetalle(codigo, detalleMovimientoInventario);
/* 154:    */     }
/* 155:    */     catch (ExcepcionAS2 e)
/* 156:    */     {
/* 157:192 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 158:193 */       detalleMovimientoInventario.getProducto().setCodigo("");
/* 159:194 */       detalleMovimientoInventario.getProducto().setNombre("");
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void cargarProductoDesdeCodigoEnDetalle(String codigo, DetalleMovimientoInventario detalleMovimientoInventario)
/* 164:    */     throws ExcepcionAS2
/* 165:    */   {
/* 166:200 */     Producto producto = null;
/* 167:201 */     producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 168:202 */     this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 169:203 */     detalleMovimientoInventario.setProducto(producto);
/* 170:204 */     cargarBodega(detalleMovimientoInventario);
/* 171:205 */     detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 172:206 */     boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/* 173:209 */     if (!indicadorAprobarMovimientoInventario)
/* 174:    */     {
/* 175:210 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 176:211 */       detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 177:212 */       inventarioProducto.setProducto(producto);
/* 178:    */     }
/* 179:215 */     if (producto.getLote() != null)
/* 180:    */     {
/* 181:216 */       detalleMovimientoInventario.getInventarioProducto().setLote(producto.getLote());
/* 182:217 */       BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), detalleMovimientoInventario.getBodegaOrigen().getIdBodega(), producto
/* 183:218 */         .getLote().getIdLote(), this.ajusteInventario.getFecha());
/* 184:219 */       detalleMovimientoInventario.setSaldo(saldo);
/* 185:221 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/* 186:222 */         detalleMovimientoInventario.setCantidad(saldo.setScale(4, RoundingMode.HALF_UP));
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String eliminarDetalle(DetalleMovimientoInventario detalle)
/* 192:    */   {
/* 193:228 */     detalle.setEliminado(true);
/* 194:229 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String agregarDetalle()
/* 198:    */   {
/* 199:239 */     DetalleMovimientoInventario d = new DetalleMovimientoInventario();
/* 200:240 */     d.setMovimientoInventario(getAjusteInventario());
/* 201:241 */     d.setProducto(new Producto());
/* 202:242 */     cargarBodega(d);
/* 203:243 */     boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/* 204:246 */     if (!indicadorAprobarMovimientoInventario)
/* 205:    */     {
/* 206:247 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 207:248 */       inventarioProducto.setDetalleMovimientoInventario(d);
/* 208:249 */       d.setInventarioProducto(inventarioProducto);
/* 209:    */     }
/* 210:252 */     getAjusteInventario().getDetalleMovimientosInventario().add(d);
/* 211:253 */     setDetalleMovimientoInventario(d);
/* 212:254 */     return "";
/* 213:    */   }
/* 214:    */   
/* 215:    */   private void cargarBodega(DetalleMovimientoInventario detalleMovimientoInventario)
/* 216:    */   {
/* 217:258 */     if (AppUtil.getBodega() != null)
/* 218:    */     {
/* 219:259 */       detalleMovimientoInventario.setBodegaOrigen(AppUtil.getBodega());
/* 220:    */     }
/* 221:    */     else
/* 222:    */     {
/* 223:261 */       Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(detalleMovimientoInventario.getProducto(), 
/* 224:262 */         Integer.valueOf(detalleMovimientoInventario.getIdSucursal()));
/* 225:263 */       detalleMovimientoInventario.setBodegaOrigen(bodegaTrabajo);
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String limpiar()
/* 230:    */   {
/* 231:274 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 232:275 */     return "";
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String guardar()
/* 236:    */   {
/* 237:    */     try
/* 238:    */     {
/* 239:286 */       this.servicioMovimientoInventario.guardar(getAjusteInventario(), false, true);
/* 240:    */       
/* 241:288 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 242:    */       
/* 243:290 */       setEditado(false);
/* 244:    */     }
/* 245:    */     catch (ExcepcionAS2Inventario e)
/* 246:    */     {
/* 247:293 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 248:294 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 249:    */     }
/* 250:    */     catch (ExcepcionAS2Financiero e)
/* 251:    */     {
/* 252:296 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 253:297 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 254:    */     }
/* 255:    */     catch (ExcepcionAS2 e)
/* 256:    */     {
/* 257:299 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 258:300 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 259:    */     }
/* 260:    */     catch (AS2Exception e)
/* 261:    */     {
/* 262:302 */       JsfUtil.addErrorMessage(e, "");
/* 263:303 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 264:    */     }
/* 265:    */     catch (Exception e)
/* 266:    */     {
/* 267:305 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 268:306 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 269:    */     }
/* 270:308 */     return "";
/* 271:    */   }
/* 272:    */   
/* 273:    */   public String eliminar()
/* 274:    */   {
/* 275:318 */     if ((getAjusteInventario() != null) && (getAjusteInventario().getId() > 0)) {
/* 276:    */       try
/* 277:    */       {
/* 278:320 */         this.servicioMovimientoInventario.anular(getAjusteInventario());
/* 279:321 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 280:    */       }
/* 281:    */       catch (ExcepcionAS2Financiero e)
/* 282:    */       {
/* 283:324 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 284:325 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 285:    */       }
/* 286:    */       catch (ExcepcionAS2Inventario e)
/* 287:    */       {
/* 288:327 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 289:328 */         e.printStackTrace();
/* 290:329 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 291:    */       }
/* 292:    */       catch (Exception e)
/* 293:    */       {
/* 294:331 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 295:332 */         LOG.error("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 296:    */       }
/* 297:    */     } else {
/* 298:335 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 299:    */     }
/* 300:337 */     return "";
/* 301:    */   }
/* 302:    */   
/* 303:    */   public String cargarDatos()
/* 304:    */   {
/* 305:348 */     return "";
/* 306:    */   }
/* 307:    */   
/* 308:    */   public List<Documento> getListaDocumentosAjuste()
/* 309:    */   {
/* 310:356 */     if (this.listaDocumentosAjuste == null) {
/* 311:    */       try
/* 312:    */       {
/* 313:358 */         this.listaDocumentosAjuste = this.servicioDocumento.buscarPorDocumentoBase(getDocumentoBase());
/* 314:    */       }
/* 315:    */       catch (ExcepcionAS2 e)
/* 316:    */       {
/* 317:360 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 318:    */       }
/* 319:    */     }
/* 320:365 */     return this.listaDocumentosAjuste;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void cargarProducto()
/* 324:    */   {
/* 325:    */     try
/* 326:    */     {
/* 327:372 */       Producto producto = getListaProductoBean().getProducto();
/* 328:373 */       if (producto != null)
/* 329:    */       {
/* 330:    */         try
/* 331:    */         {
/* 332:376 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 333:    */         }
/* 334:    */         catch (ExcepcionAS2 e)
/* 335:    */         {
/* 336:378 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 337:    */         }
/* 338:381 */         this.detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 339:382 */         this.detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 340:383 */         this.detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 341:384 */         this.detalleMovimientoInventario.setProducto(producto);
/* 342:385 */         this.detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 343:386 */         this.detalleMovimientoInventario.setCantidad(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/* 344:387 */         cargarBodega(this.detalleMovimientoInventario);
/* 345:388 */         boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(AppUtil.getOrganizacion().getId(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/* 346:    */         
/* 347:    */ 
/* 348:391 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 349:392 */         if (!indicadorAprobarMovimientoInventario)
/* 350:    */         {
/* 351:394 */           inventarioProducto.setDetalleMovimientoInventario(this.detalleMovimientoInventario);
/* 352:395 */           inventarioProducto.setProducto(producto);
/* 353:396 */           this.detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 354:    */         }
/* 355:399 */         this.detalleMovimientoInventario.setMovimientoInventario(this.ajusteInventario);
/* 356:401 */         if (getListaProductoBean().getSaldoProductoLote() != null)
/* 357:    */         {
/* 358:402 */           this.detalleMovimientoInventario.setBodegaOrigen(getListaProductoBean().getSaldoProductoLote().getBodega());
/* 359:403 */           this.detalleMovimientoInventario.setCantidad(getListaProductoBean().getSaldoProductoLote().getSaldo()
/* 360:404 */             .setScale(2, RoundingMode.HALF_UP));
/* 361:405 */           this.detalleMovimientoInventario.setSaldo(this.detalleMovimientoInventario.getCantidad());
/* 362:406 */           if (!indicadorAprobarMovimientoInventario)
/* 363:    */           {
/* 364:407 */             inventarioProducto.setBodega(this.detalleMovimientoInventario.getBodegaOrigen());
/* 365:408 */             inventarioProducto.setLote(getListaProductoBean().getSaldoProductoLote().getLote());
/* 366:409 */             inventarioProducto.setCantidad(this.detalleMovimientoInventario.getCantidad());
/* 367:    */           }
/* 368:    */         }
/* 369:413 */         this.ajusteInventario.getDetalleMovimientosInventario().add(this.detalleMovimientoInventario);
/* 370:    */       }
/* 371:    */     }
/* 372:    */     catch (Exception e)
/* 373:    */     {
/* 374:417 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 375:418 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 376:    */     }
/* 377:    */     finally
/* 378:    */     {
/* 379:420 */       getListaProductoBean().setProducto(null);
/* 380:421 */       getListaProductoBean().setSaldoProductoLote(null);
/* 381:    */     }
/* 382:    */   }
/* 383:    */   
/* 384:    */   public String crear()
/* 385:    */   {
/* 386:427 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 387:428 */     return "";
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setListaDocumentosAjuste(List<Documento> listaDocumentosTransferencia)
/* 391:    */   {
/* 392:436 */     this.listaDocumentosAjuste = listaDocumentosTransferencia;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setDetalleMovimientoInventario(List<DetalleMovimientoInventario> detalleMovimientoInventario)
/* 396:    */   {
/* 397:446 */     getAjusteInventario().setDetalleMovimientosInventario(detalleMovimientoInventario);
/* 398:    */   }
/* 399:    */   
/* 400:    */   public DataTable getDtDetalleAjuste()
/* 401:    */   {
/* 402:455 */     return this.dtDetalleAjuste;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void setDtDetalleAjuste(DataTable dtDetalleAjuste)
/* 406:    */   {
/* 407:465 */     this.dtDetalleAjuste = dtDetalleAjuste;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public MovimientoInventario getAjusteInventario()
/* 411:    */   {
/* 412:474 */     return this.ajusteInventario;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void setAjusteInventario(MovimientoInventario ajusteInventario)
/* 416:    */   {
/* 417:484 */     this.ajusteInventario = ajusteInventario;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public LazyDataModel<MovimientoInventario> getListaAjusteInventario()
/* 421:    */   {
/* 422:493 */     return this.listaAjusteInventario;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void setListaAjusteInventario(LazyDataModel<MovimientoInventario> listaAjusteInventario)
/* 426:    */   {
/* 427:503 */     this.listaAjusteInventario = listaAjusteInventario;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public DataTable getDtListaAjuste()
/* 431:    */   {
/* 432:512 */     return this.dtListaAjuste;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setDtListaAjuste(DataTable dtListaAjuste)
/* 436:    */   {
/* 437:522 */     this.dtListaAjuste = dtListaAjuste;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public List<DetalleMovimientoInventario> getDetalleAjusteMovimiento()
/* 441:    */   {
/* 442:532 */     List<DetalleMovimientoInventario> detalle = new ArrayList();
/* 443:533 */     for (DetalleMovimientoInventario dm : getAjusteInventario().getDetalleMovimientosInventario()) {
/* 444:535 */       if (!dm.isEliminado()) {
/* 445:536 */         detalle.add(dm);
/* 446:    */       }
/* 447:    */     }
/* 448:540 */     return detalle;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public boolean isMostrarSaldoInicial()
/* 452:    */   {
/* 453:549 */     Calendar calendario = Calendar.getInstance();
/* 454:550 */     calendario.setTime(ParametrosSistema.getFechaMaximaSaldosIniciales(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 455:551 */     int day = calendario.get(5);
/* 456:552 */     int month = calendario.get(2) + 1;
/* 457:553 */     int year = calendario.get(1);
/* 458:    */     
/* 459:555 */     Date fecha = FuncionesUtiles.getFecha(day, month, year);
/* 460:    */     
/* 461:557 */     this.mostrarSaldoInicial = FuncionesUtiles.compararFechaAnteriorOIgual(new Date(), fecha);
/* 462:    */     
/* 463:559 */     return this.mostrarSaldoInicial;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setMostrarSaldoInicial(boolean mostrarSaldoInicial)
/* 467:    */   {
/* 468:569 */     this.mostrarSaldoInicial = mostrarSaldoInicial;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public List<Bodega> getListaBodega()
/* 472:    */   {
/* 473:578 */     if (this.listaBodega == null) {
/* 474:579 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 475:    */     }
/* 476:581 */     return this.listaBodega;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 480:    */   {
/* 481:591 */     this.listaBodega = listaBodega;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public List<MotivoAjusteInventario> getListaMotivoAjusteInventario()
/* 485:    */   {
/* 486:600 */     if (this.listaMotivoAjusteInventario == null) {
/* 487:601 */       this.listaMotivoAjusteInventario = this.servicioMotivoAjusteInventario.obtenerListaCombo("nombre", true, null);
/* 488:    */     }
/* 489:603 */     return this.listaMotivoAjusteInventario;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setListaMotivoAjusteInventario(List<MotivoAjusteInventario> listaMotivoAjusteInventario)
/* 493:    */   {
/* 494:613 */     this.listaMotivoAjusteInventario = listaMotivoAjusteInventario;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public DataTable getDtInventarioProductoLote()
/* 498:    */   {
/* 499:622 */     return this.dtInventarioProductoLote;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setDtInventarioProductoLote(DataTable dtInventarioProductoLote)
/* 503:    */   {
/* 504:632 */     this.dtInventarioProductoLote = dtInventarioProductoLote;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public Lote getLoteCrear()
/* 508:    */   {
/* 509:641 */     if (this.loteCrear == null) {
/* 510:642 */       this.loteCrear = new Lote();
/* 511:    */     }
/* 512:644 */     return this.loteCrear;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void setLoteCrear(Lote loteCrear)
/* 516:    */   {
/* 517:654 */     this.loteCrear = loteCrear;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public String getCodigoCabecera()
/* 521:    */   {
/* 522:663 */     return this.codigoCabecera;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void setCodigoCabecera(String codigoCabecera)
/* 526:    */   {
/* 527:673 */     this.codigoCabecera = codigoCabecera;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void ajusteInventarioSeleccionado()
/* 531:    */   {
/* 532:677 */     this.ajusteInventario = ((MovimientoInventario)this.dtListaAjuste.getRowData());
/* 533:    */   }
/* 534:    */   
/* 535:    */   public void aprobar()
/* 536:    */   {
/* 537:    */     try
/* 538:    */     {
/* 539:682 */       this.servicioMovimientoInventario.guardar(this.ajusteInventario, true, true);
/* 540:    */       
/* 541:684 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 542:    */       
/* 543:686 */       setEditado(false);
/* 544:    */     }
/* 545:    */     catch (ExcepcionAS2Inventario e)
/* 546:    */     {
/* 547:689 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 548:690 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 549:    */     }
/* 550:    */     catch (ExcepcionAS2Financiero e)
/* 551:    */     {
/* 552:692 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 553:693 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 554:    */     }
/* 555:    */     catch (ExcepcionAS2 e)
/* 556:    */     {
/* 557:695 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 558:696 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 559:    */     }
/* 560:    */     catch (AS2Exception e)
/* 561:    */     {
/* 562:698 */       JsfUtil.addErrorMessage(e, "");
/* 563:699 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 564:    */     }
/* 565:    */     catch (Exception e)
/* 566:    */     {
/* 567:701 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 568:702 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 569:    */     }
/* 570:    */   }
/* 571:    */   
/* 572:    */   public DetalleMovimientoInventario getDetalleMovimientoInventario()
/* 573:    */   {
/* 574:708 */     return this.detalleMovimientoInventario;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setDetalleMovimientoInventario(DetalleMovimientoInventario detalleMovimientoInventario)
/* 578:    */   {
/* 579:712 */     this.detalleMovimientoInventario = detalleMovimientoInventario;
/* 580:    */   }
/* 581:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.AprobarAjusteInventarioBean
 * JD-Core Version:    0.7.0.1
 */