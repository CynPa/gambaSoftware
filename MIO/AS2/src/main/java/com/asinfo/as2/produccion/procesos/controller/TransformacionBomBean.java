/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  10:    */ import com.asinfo.as2.entities.Lote;
/*  11:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  14:    */ import com.asinfo.as2.entities.Producto;
/*  15:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  18:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  19:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  20:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  21:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  26:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  27:    */ import com.asinfo.as2.inventario.procesos.controller.TransferenciaBodegaBean;
/*  28:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  29:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  30:    */ import com.asinfo.as2.util.AppUtil;
/*  31:    */ import com.asinfo.as2.utils.JsfUtil;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.math.RoundingMode;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.Iterator;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.faces.bean.ManagedBean;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import org.apache.log4j.Logger;
/*  43:    */ import org.primefaces.component.datatable.DataTable;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class TransformacionBomBean
/*  48:    */   extends TransferenciaBodegaBean
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = 7366190029638795237L;
/*  51:    */   @EJB
/*  52:    */   private ServicioProducto servicioProducto;
/*  53:    */   @EJB
/*  54:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  55:    */   @EJB
/*  56:    */   private ServicioLote servicioLote;
/*  57:    */   @EJB
/*  58:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  59:    */   @EJB
/*  60:    */   private ServicioOrganizacion servicioOrganizacion;
/*  61:    */   private List<Documento> listaDocumentoOrigen;
/*  62:    */   private List<Documento> listaDocumentoDestino;
/*  63:    */   private Lote loteCrear;
/*  64:    */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  65:    */   private String codigoCabecera;
/*  66:    */   
/*  67:    */   public String crear()
/*  68:    */   {
/*  69: 90 */     super.crear();
/*  70: 91 */     if (getTransferencia() != null)
/*  71:    */     {
/*  72: 92 */       getTransferencia().setBodegaDestino(AppUtil.getBodega());
/*  73: 93 */       getTransferencia().setBodegaOrigen(AppUtil.getBodega());
/*  74: 94 */       if (!getListaDocumentoOrigen().isEmpty()) {
/*  75: 95 */         getTransferencia().setDocumento((Documento)getListaDocumentoOrigen().get(0));
/*  76:    */       }
/*  77: 97 */       if (!getListaDocumentoDestino().isEmpty()) {
/*  78: 98 */         getTransferencia().setDocumentoDestino((Documento)getListaDocumentoDestino().get(0));
/*  79:    */       }
/*  80:    */     }
/*  81:101 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String guardar()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:112 */       setTransformacionBom();
/*  89:113 */       getTransferencia().setDetalleMovimientosInventario(getDetalleMovimientoInventario());
/*  90:114 */       MovimientoInventario transformacionMateriales = this.servicioMovimientoInventario.guardaTransformacionProducto(getTransferencia(), false, null, null, null);
/*  91:    */       
/*  92:116 */       this.servicioMovimientoInventario.contabilizar(transformacionMateriales);
/*  93:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  94:118 */       limpiar();
/*  95:    */     }
/*  96:    */     catch (ExcepcionAS2Inventario e)
/*  97:    */     {
/*  98:120 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  99:121 */       LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/* 100:    */     }
/* 101:    */     catch (ExcepcionAS2 e)
/* 102:    */     {
/* 103:123 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 104:124 */       LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/* 105:    */     }
/* 106:    */     catch (AS2Exception e)
/* 107:    */     {
/* 108:126 */       JsfUtil.addErrorMessage(e, "");
/* 109:127 */       LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/* 110:    */     }
/* 111:    */     catch (Exception e)
/* 112:    */     {
/* 113:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 114:130 */       LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/* 115:    */     }
/* 116:132 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String eliminar()
/* 120:    */   {
/* 121:143 */     if ((getTransferencia() != null) && (getTransferencia().getId() != 0)) {
/* 122:    */       try
/* 123:    */       {
/* 124:145 */         this.servicioMovimientoInventario.anularTransformacionProducto(getTransferencia());
/* 125:146 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 126:    */       }
/* 127:    */       catch (ExcepcionAS2Financiero e)
/* 128:    */       {
/* 129:148 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 130:149 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 131:    */       }
/* 132:    */       catch (ExcepcionAS2Inventario e)
/* 133:    */       {
/* 134:151 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 135:152 */         e.printStackTrace();
/* 136:153 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 137:    */       }
/* 138:    */       catch (Exception e)
/* 139:    */       {
/* 140:155 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 141:156 */         LOG.error("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 142:    */       }
/* 143:    */     } else {
/* 144:159 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 145:    */     }
/* 146:161 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected void obtenerFiltros(Map<String, String> filters)
/* 150:    */   {
/* 151:171 */     filters.put("documento.documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 152:172 */     filters.put("movimientoInventarioPadre", OperacionEnum.IS_NOT_NULL.toString());
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void cargarProducto()
/* 156:    */   {
/* 157:178 */     Producto producto = getListaProductoBean().getProducto();
/* 158:179 */     producto = this.servicioProducto.cargarDetalleListaMaterial(producto);
/* 159:180 */     getTransferencia().setProductoTerminadoTransformacion(producto);
/* 160:183 */     for (Iterator localIterator = getTransferencia().getDetalleMovimientosInventario().iterator(); localIterator.hasNext();)
/* 161:    */     {
/* 162:183 */       detalle = (DetalleMovimientoInventario)localIterator.next();
/* 163:184 */       detalle.setEliminado(true);
/* 164:185 */       if (detalle.getInventarioProducto() != null) {
/* 165:186 */         detalle.getInventarioProducto().setEliminado(true);
/* 166:    */       }
/* 167:    */     }
/* 168:    */     DetalleMovimientoInventario detalle;
/* 169:191 */     if ((getTransferencia().getProductoTerminadoTransformacion() != null) && 
/* 170:192 */       (getTransferencia().getProductoTerminadoTransformacion().isIndicadorLote()))
/* 171:    */     {
/* 172:194 */       Object listaSaldoProductoLote = this.servicioProducto.obtenerProductosConSaldoBodegaLote(null, new Date(), 
/* 173:195 */         getTransferencia().getProductoTerminadoTransformacion(), false);
/* 174:197 */       for (SaldoProductoLote saldoProductoLote : (List)listaSaldoProductoLote)
/* 175:    */       {
/* 176:200 */         DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 177:201 */         detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 178:202 */         detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 179:203 */         detalleMovimientoInventario.setProducto(saldoProductoLote.getProducto());
/* 180:204 */         detalleMovimientoInventario.setMovimientoInventario(getTransferencia());
/* 181:205 */         detalleMovimientoInventario.setUnidadConversion(saldoProductoLote.getProducto().getUnidad());
/* 182:206 */         detalleMovimientoInventario.setBodegaOrigen(saldoProductoLote.getBodega());
/* 183:207 */         Lote lote = this.servicioLote.getAtributosLote(saldoProductoLote.getLote().getId(), 
/* 184:208 */           AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 185:209 */         detalleMovimientoInventario.setLote(lote);
/* 186:210 */         detalleMovimientoInventario.setCantidad(saldoProductoLote.getSaldo().setScale(2, RoundingMode.HALF_UP));
/* 187:211 */         detalleMovimientoInventario.setCantidadTransformacion(saldoProductoLote.getSaldo().setScale(2, RoundingMode.HALF_UP));
/* 188:212 */         detalleMovimientoInventario.setBodegaTransformacion(saldoProductoLote.getBodega());
/* 189:    */         
/* 190:    */ 
/* 191:215 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 192:216 */         inventarioProducto.setOperacion(getTransferencia().getDocumento().getOperacion());
/* 193:217 */         inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 194:218 */         inventarioProducto.setProducto(producto);
/* 195:219 */         inventarioProducto.setBodega(saldoProductoLote.getBodega());
/* 196:220 */         inventarioProducto.setLote(lote);
/* 197:221 */         detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 198:    */         
/* 199:223 */         getTransferencia().getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 200:    */       }
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void seleccionarListener()
/* 205:    */   {
/* 206:233 */     boolean seleccionado = false;
/* 207:234 */     for (DetalleMovimientoInventario dmi : getTransferencia().getDetalleMovimientosInventario()) {
/* 208:235 */       if (dmi.isSeleccionado())
/* 209:    */       {
/* 210:236 */         seleccionado = true;
/* 211:237 */         break;
/* 212:    */       }
/* 213:    */     }
/* 214:241 */     if (!seleccionado) {
/* 215:242 */       for (DetalleMovimientoInventario dmi : getTransferencia().getDetalleMovimientosInventario()) {
/* 216:243 */         dmi.setEliminado(false);
/* 217:    */       }
/* 218:    */     } else {
/* 219:246 */       for (DetalleMovimientoInventario dmi : getTransferencia().getDetalleMovimientosInventario()) {
/* 220:247 */         dmi.setEliminado(!dmi.isSeleccionado());
/* 221:    */       }
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void crearLoteListener()
/* 226:    */     throws ExcepcionAS2, AS2Exception
/* 227:    */   {
/* 228:253 */     setDetalleMovimientoInventarioSeleccionado((DetalleMovimientoInventario)getDtDetalleMovimientoInventario().getRowData());
/* 229:254 */     this.loteCrear = getDetalleMovimientoInventarioSeleccionado().getLoteTransformacion();
/* 230:255 */     if ((this.loteCrear != null) && (this.loteCrear.getCodigo() != null) && (!this.loteCrear.getCodigo().isEmpty()))
/* 231:    */     {
/* 232:256 */       Map<String, String> filters = new HashMap();
/* 233:257 */       filters.put("codigo", "=" + this.loteCrear.getCodigo());
/* 234:258 */       filters.put("producto.idProducto", "=" + getTransferencia().getProductoTerminadoTransformacion().getIdProducto());
/* 235:259 */       filters.put("indicadorMovimientoInterno", "true");
/* 236:260 */       List<Lote> loteList = this.servicioLote.obtenerListaCombo("codigo", true, filters);
/* 237:261 */       this.loteCrear = (!loteList.isEmpty() ? (Lote)loteList.get(0) : this.loteCrear);
/* 238:    */     }
/* 239:    */     else
/* 240:    */     {
/* 241:263 */       this.loteCrear = this.servicioLote.crearLoteInterno(getTransferencia().getIdOrganizacion(), getTransferencia().getProductoTerminadoTransformacion(), null, false);
/* 242:    */     }
/* 243:266 */     cargarValorAtributo(getDetalleMovimientoInventarioSeleccionado());
/* 244:    */   }
/* 245:    */   
/* 246:    */   public String guardarLote()
/* 247:    */   {
/* 248:    */     try
/* 249:    */     {
/* 250:271 */       this.loteCrear.setCodigo(this.loteCrear.getProducto().getPrefijoLote() + this.loteCrear.getCodigo());
/* 251:272 */       if (this.loteCrear.getIdLote() != 0) {
/* 252:273 */         this.servicioLote.guardar(this.loteCrear);
/* 253:    */       }
/* 254:275 */       this.loteCrear.getProducto().setPrefijoLote(null);
/* 255:276 */       getTransferencia().setLoteTransformacion(this.loteCrear);
/* 256:277 */       getDetalleMovimientoInventarioSeleccionado().setLoteTransformacion(this.loteCrear);
/* 257:    */     }
/* 258:    */     catch (AS2Exception e)
/* 259:    */     {
/* 260:279 */       JsfUtil.addErrorMessage(e, "");
/* 261:    */     }
/* 262:    */     catch (Exception e)
/* 263:    */     {
/* 264:281 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 265:282 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 266:    */     }
/* 267:285 */     return "";
/* 268:    */   }
/* 269:    */   
/* 270:    */   private void cargarValorAtributo(DetalleMovimientoInventario detalleMovimientoInventario)
/* 271:    */   {
/* 272:289 */     if ((detalleMovimientoInventario != null) && (detalleMovimientoInventario.getLote() != null))
/* 273:    */     {
/* 274:290 */       if (detalleMovimientoInventario.getLote().getValorAtributo1() != null) {
/* 275:291 */         this.loteCrear.setValorAtributo1(detalleMovimientoInventario.getLote().getValorAtributo1());
/* 276:    */       }
/* 277:293 */       if (detalleMovimientoInventario.getLote().getValorAtributo2() != null) {
/* 278:294 */         this.loteCrear.setValorAtributo2(detalleMovimientoInventario.getLote().getValorAtributo2());
/* 279:    */       }
/* 280:296 */       if (detalleMovimientoInventario.getLote().getValorAtributo3() != null) {
/* 281:297 */         this.loteCrear.setValorAtributo3(detalleMovimientoInventario.getLote().getValorAtributo3());
/* 282:    */       }
/* 283:299 */       if (detalleMovimientoInventario.getLote().getValorAtributo4() != null) {
/* 284:300 */         this.loteCrear.setValorAtributo4(detalleMovimientoInventario.getLote().getValorAtributo4());
/* 285:    */       }
/* 286:302 */       if (detalleMovimientoInventario.getLote().getValorAtributo5() != null) {
/* 287:303 */         this.loteCrear.setValorAtributo5(detalleMovimientoInventario.getLote().getValorAtributo5());
/* 288:    */       }
/* 289:305 */       if (detalleMovimientoInventario.getLote().getValorAtributo6() != null) {
/* 290:306 */         this.loteCrear.setValorAtributo6(detalleMovimientoInventario.getLote().getValorAtributo6());
/* 291:    */       }
/* 292:308 */       if (detalleMovimientoInventario.getLote().getValorAtributo7() != null) {
/* 293:309 */         this.loteCrear.setValorAtributo7(detalleMovimientoInventario.getLote().getValorAtributo7());
/* 294:    */       }
/* 295:311 */       if (detalleMovimientoInventario.getLote().getValorAtributo8() != null) {
/* 296:312 */         this.loteCrear.setValorAtributo8(detalleMovimientoInventario.getLote().getValorAtributo8());
/* 297:    */       }
/* 298:314 */       if (detalleMovimientoInventario.getLote().getValorAtributo9() != null) {
/* 299:315 */         this.loteCrear.setValorAtributo9(detalleMovimientoInventario.getLote().getValorAtributo9());
/* 300:    */       }
/* 301:317 */       if (detalleMovimientoInventario.getLote().getValorAtributo10() != null) {
/* 302:318 */         this.loteCrear.setValorAtributo10(detalleMovimientoInventario.getLote().getValorAtributo10());
/* 303:    */       }
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   private void setTransformacionBom()
/* 308:    */   {
/* 309:327 */     DetalleMovimientoInventario detalleMovimientoInventario = null;
/* 310:328 */     for (DetalleMovimientoInventario dmi : getTransferencia().getDetalleMovimientosInventario()) {
/* 311:329 */       if (dmi.isSeleccionado())
/* 312:    */       {
/* 313:330 */         detalleMovimientoInventario = dmi;
/* 314:331 */         break;
/* 315:    */       }
/* 316:    */     }
/* 317:334 */     if (detalleMovimientoInventario != null)
/* 318:    */     {
/* 319:335 */       getTransferencia().setIndicadorTransferenciaBoom(true);
/* 320:336 */       getTransferencia().setCantidadTransformacion(detalleMovimientoInventario.getCantidadTransformacion());
/* 321:337 */       getTransferencia().setLoteTransformacion(detalleMovimientoInventario.getLoteTransformacion());
/* 322:338 */       getTransferencia().setBodegaDestino(detalleMovimientoInventario.getBodegaTransformacion());
/* 323:339 */       detalleMovimientoInventario.setDescripcion(getTransferencia().getDescripcion());
/* 324:    */     }
/* 325:    */     else
/* 326:    */     {
/* 327:341 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void agregarDetalleDesdeCodigoCabecera()
/* 332:    */   {
/* 333:    */     try
/* 334:    */     {
/* 335:349 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(this.codigoCabecera, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 336:350 */       getTransferencia().setProductoTerminadoTransformacion(producto);
/* 337:    */       
/* 338:    */ 
/* 339:353 */       Bodega bodegaTrabajoMaterial = this.servicioProducto.obtenerBodegaTrabajoProducto(producto, Integer.valueOf(AppUtil.getSucursal().getId()));
/* 340:354 */       if (bodegaTrabajoMaterial == null) {
/* 341:355 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_EXISTE_BODEGA_TRABAJO_SUCURSAL", new String[] { producto.getCodigo() + "-" + producto.getNombre() });
/* 342:    */       }
/* 343:359 */       for (DetalleMovimientoInventario detalle : getTransferencia().getDetalleMovimientosInventario())
/* 344:    */       {
/* 345:360 */         detalle.setEliminado(true);
/* 346:361 */         if (detalle.getInventarioProducto() != null) {
/* 347:362 */           detalle.getInventarioProducto().setEliminado(true);
/* 348:    */         }
/* 349:    */       }
/* 350:367 */       if ((getTransferencia().getProductoTerminadoTransformacion() != null) && 
/* 351:368 */         (getTransferencia().getProductoTerminadoTransformacion().isIndicadorLote()))
/* 352:    */       {
/* 353:371 */         BigDecimal saldoLote = this.servicioProducto.getSaldoLote(producto.getId(), bodegaTrabajoMaterial.getId(), producto.getLote().getId(), 
/* 354:372 */           getTransferencia().getFecha());
/* 355:    */         
/* 356:    */ 
/* 357:375 */         DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 358:376 */         detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 359:377 */         detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 360:378 */         detalleMovimientoInventario.setProducto(producto);
/* 361:379 */         detalleMovimientoInventario.setMovimientoInventario(getTransferencia());
/* 362:380 */         detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 363:381 */         detalleMovimientoInventario.setBodegaOrigen(bodegaTrabajoMaterial);
/* 364:382 */         Lote lote = this.servicioLote.getAtributosLote(producto.getLote().getId(), 
/* 365:383 */           AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 366:384 */         detalleMovimientoInventario.setLote(lote);
/* 367:385 */         detalleMovimientoInventario.setCantidad(saldoLote.setScale(2, RoundingMode.HALF_UP));
/* 368:386 */         detalleMovimientoInventario.setCantidadTransformacion(saldoLote.setScale(2, RoundingMode.HALF_UP));
/* 369:387 */         detalleMovimientoInventario.setBodegaTransformacion(bodegaTrabajoMaterial);
/* 370:    */         
/* 371:    */ 
/* 372:390 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 373:391 */         inventarioProducto.setOperacion(getTransferencia().getDocumento().getOperacion());
/* 374:392 */         inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 375:393 */         inventarioProducto.setProducto(producto);
/* 376:394 */         inventarioProducto.setBodega(bodegaTrabajoMaterial);
/* 377:395 */         inventarioProducto.setLote(lote);
/* 378:396 */         detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 379:397 */         detalleMovimientoInventario.setSeleccionado(true);
/* 380:    */         
/* 381:399 */         getTransferencia().getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 382:    */       }
/* 383:402 */       this.codigoCabecera = "";
/* 384:    */     }
/* 385:    */     catch (ExcepcionAS2 e)
/* 386:    */     {
/* 387:405 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 388:406 */       e.printStackTrace();
/* 389:    */     }
/* 390:    */     catch (AS2Exception e)
/* 391:    */     {
/* 392:408 */       JsfUtil.addErrorMessage(e, "");
/* 393:409 */       e.printStackTrace();
/* 394:    */     }
/* 395:    */   }
/* 396:    */   
/* 397:    */   public List<Documento> getListaDocumentoOrigen()
/* 398:    */   {
/* 399:417 */     if (this.listaDocumentoOrigen == null)
/* 400:    */     {
/* 401:418 */       Map<String, String> filtros = new HashMap();
/* 402:419 */       filtros.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 403:420 */       filtros.put("operacion", "=-1");
/* 404:421 */       filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 405:422 */       this.listaDocumentoOrigen = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/* 406:    */     }
/* 407:424 */     return this.listaDocumentoOrigen;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setListaDocumentoOrigen(List<Documento> listaDocumentoOrigen)
/* 411:    */   {
/* 412:432 */     this.listaDocumentoOrigen = listaDocumentoOrigen;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public List<Documento> getListaDocumentoDestino()
/* 416:    */   {
/* 417:439 */     if (this.listaDocumentoDestino == null)
/* 418:    */     {
/* 419:440 */       Map<String, String> filtros = new HashMap();
/* 420:441 */       filtros.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 421:442 */       filtros.put("operacion", "=1");
/* 422:443 */       filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 423:444 */       this.listaDocumentoDestino = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/* 424:    */     }
/* 425:446 */     return this.listaDocumentoDestino;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public Lote getLoteCrear()
/* 429:    */   {
/* 430:450 */     return this.loteCrear;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setLoteCrear(Lote loteCrear)
/* 434:    */   {
/* 435:454 */     this.loteCrear = loteCrear;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 439:    */   {
/* 440:458 */     if (this.organizacionConfiguracion == null) {
/* 441:459 */       this.organizacionConfiguracion = this.servicioOrganizacion.cargarDetalle(AppUtil.getOrganizacion().getId()).getOrganizacionConfiguracion();
/* 442:    */     }
/* 443:461 */     return this.organizacionConfiguracion;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 447:    */   {
/* 448:465 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public String getCodigoCabecera()
/* 452:    */   {
/* 453:469 */     return this.codigoCabecera;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setCodigoCabecera(String codigoCabecera)
/* 457:    */   {
/* 458:473 */     this.codigoCabecera = codigoCabecera;
/* 459:    */   }
/* 460:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.TransformacionBomBean
 * JD-Core Version:    0.7.0.1
 */