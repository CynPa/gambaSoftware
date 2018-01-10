/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   6:    */ import com.asinfo.as2.entities.Documento;
/*   7:    */ import com.asinfo.as2.entities.InventarioProducto;
/*   8:    */ import com.asinfo.as2.entities.Lote;
/*   9:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  20:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  23:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  24:    */ import com.asinfo.as2.inventario.procesos.controller.TransferenciaBodegaBean;
/*  25:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  26:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  27:    */ import com.asinfo.as2.util.AppUtil;
/*  28:    */ import com.asinfo.as2.utils.JsfUtil;
/*  29:    */ import com.asinfo.as2.utils.NodoArbol;
/*  30:    */ import java.math.BigDecimal;
/*  31:    */ import java.math.RoundingMode;
/*  32:    */ import java.util.ArrayList;
/*  33:    */ import java.util.HashMap;
/*  34:    */ import java.util.Iterator;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import javax.ejb.EJB;
/*  38:    */ import javax.faces.bean.ManagedBean;
/*  39:    */ import javax.faces.bean.ViewScoped;
/*  40:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  41:    */ import org.apache.log4j.Logger;
/*  42:    */ import org.primefaces.component.datatable.DataTable;
/*  43:    */ 
/*  44:    */ @ManagedBean
/*  45:    */ @ViewScoped
/*  46:    */ public class ProduccionBomBean
/*  47:    */   extends TransferenciaBodegaBean
/*  48:    */ {
/*  49:    */   private static final long serialVersionUID = 7366190029638795237L;
/*  50:    */   @EJB
/*  51:    */   private ServicioProducto servicioProducto;
/*  52:    */   @EJB
/*  53:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  54:    */   @EJB
/*  55:    */   private ServicioLote servicioLote;
/*  56:    */   @EJB
/*  57:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  58:    */   private List<Documento> listaDocumentoOrigen;
/*  59:    */   private List<Documento> listaDocumentoDestino;
/*  60:    */   private String numeroTransformacion;
/*  61:    */   private Lote loteCrear;
/*  62:    */   
/*  63:    */   protected void obtenerFiltros(Map<String, String> filters)
/*  64:    */   {
/*  65: 81 */     filters.put("documento.documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/*  66: 82 */     filters.put("movimientoInventarioPadre", OperacionEnum.IS_NOT_NULL.toString());
/*  67: 83 */     if (this.numeroTransformacion != null) {
/*  68: 84 */       filters.put("numero", this.numeroTransformacion);
/*  69:    */     }
/*  70: 86 */     this.numeroTransformacion = null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String crear()
/*  74:    */   {
/*  75: 92 */     super.crear();
/*  76: 94 */     if (getTransferencia() != null)
/*  77:    */     {
/*  78: 96 */       getTransferencia().setBodegaDestino(AppUtil.getBodega());
/*  79: 97 */       getTransferencia().setBodegaOrigen(AppUtil.getBodega());
/*  80: 99 */       if (!getListaDocumentoOrigen().isEmpty()) {
/*  81:100 */         getTransferencia().setDocumento((Documento)getListaDocumentoOrigen().get(0));
/*  82:    */       }
/*  83:103 */       if (!getListaDocumentoDestino().isEmpty()) {
/*  84:104 */         getTransferencia().setDocumentoDestino((Documento)getListaDocumentoDestino().get(0));
/*  85:    */       }
/*  86:    */     }
/*  87:108 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:114 */     if ((getTransferencia() != null) && (getTransferencia().getId() != 0)) {
/*  93:    */       try
/*  94:    */       {
/*  95:116 */         this.servicioMovimientoInventario.anularTransformacionProducto(getTransferencia());
/*  96:117 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  97:    */       }
/*  98:    */       catch (ExcepcionAS2Financiero e)
/*  99:    */       {
/* 100:120 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 101:121 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 102:    */       }
/* 103:    */       catch (ExcepcionAS2Inventario e)
/* 104:    */       {
/* 105:123 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 106:124 */         e.printStackTrace();
/* 107:125 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 108:    */       }
/* 109:    */       catch (Exception e)
/* 110:    */       {
/* 111:127 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 112:128 */         LOG.error("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/* 113:    */       }
/* 114:    */     } else {
/* 115:131 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 116:    */     }
/* 117:133 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String guardar()
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:139 */       getTransferencia().setDetalleMovimientosInventario(getDetalleMovimientoInventario());
/* 125:140 */       MovimientoInventario transformacionMateriales = this.servicioMovimientoInventario.guardaTransformacionProducto(getTransferencia(), false, null, null, null);
/* 126:    */       
/* 127:142 */       this.servicioMovimientoInventario.contabilizar(transformacionMateriales);
/* 128:    */       
/* 129:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 130:    */       
/* 131:146 */       limpiar();
/* 132:    */     }
/* 133:    */     catch (ExcepcionAS2Inventario e)
/* 134:    */     {
/* 135:149 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 136:150 */       LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/* 137:    */     }
/* 138:    */     catch (ExcepcionAS2 e)
/* 139:    */     {
/* 140:152 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 141:153 */       LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/* 142:    */     }
/* 143:    */     catch (AS2Exception e)
/* 144:    */     {
/* 145:155 */       JsfUtil.addErrorMessage(e, "");
/* 146:156 */       LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/* 147:    */     }
/* 148:    */     catch (Exception e)
/* 149:    */     {
/* 150:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 151:159 */       LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/* 152:    */     }
/* 153:161 */     return "";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List<Documento> getListaDocumentoOrigen()
/* 157:    */   {
/* 158:169 */     if (this.listaDocumentoOrigen == null)
/* 159:    */     {
/* 160:171 */       Map<String, String> filtros = new HashMap();
/* 161:172 */       filtros.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 162:173 */       filtros.put("operacion", "=-1");
/* 163:174 */       filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 164:    */       
/* 165:176 */       this.listaDocumentoOrigen = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/* 166:    */     }
/* 167:179 */     return this.listaDocumentoOrigen;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaDocumentoOrigen(List<Documento> listaDocumentoOrigen)
/* 171:    */   {
/* 172:187 */     this.listaDocumentoOrigen = listaDocumentoOrigen;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<Documento> getListaDocumentoDestino()
/* 176:    */   {
/* 177:195 */     if (this.listaDocumentoDestino == null)
/* 178:    */     {
/* 179:197 */       Map<String, String> filtros = new HashMap();
/* 180:198 */       filtros.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 181:199 */       filtros.put("operacion", "=1");
/* 182:200 */       filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 183:    */       
/* 184:202 */       this.listaDocumentoDestino = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/* 185:    */     }
/* 186:205 */     return this.listaDocumentoDestino;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setListaDocumentoDestino(List<Documento> listaDocumentoDestino)
/* 190:    */   {
/* 191:213 */     this.listaDocumentoDestino = listaDocumentoDestino;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String getNumeroTransformacion()
/* 195:    */   {
/* 196:217 */     return this.numeroTransformacion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setNumeroTransformacion(String numeroTransformacion)
/* 200:    */   {
/* 201:221 */     this.numeroTransformacion = numeroTransformacion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void cargarProducto()
/* 205:    */   {
/* 206:225 */     Producto producto = getListaProductoBean().getProducto();
/* 207:226 */     getTransferencia().setCantidadTransformacion(producto.getTraCantidad());
/* 208:227 */     producto = this.servicioProducto.cargarDetalleListaMaterial(producto);
/* 209:228 */     getTransferencia().setProductoTerminadoTransformacion(producto);
/* 210:229 */     actualizarProductoTerminado(producto, null);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void actualizarOrdenFabricacion()
/* 214:    */   {
/* 215:233 */     if (getTransferencia().getOrdenFabricacion() != null)
/* 216:    */     {
/* 217:234 */       getTransferencia().setCantidadTransformacion(getTransferencia().getOrdenFabricacion().getCantidad());
/* 218:235 */       getTransferencia().setProductoTerminadoTransformacion(getTransferencia().getOrdenFabricacion().getProducto());
/* 219:236 */       actualizarProductoTerminado(getTransferencia().getOrdenFabricacion().getProducto(), getTransferencia().getOrdenFabricacion());
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void crearLoteListener()
/* 224:    */     throws ExcepcionAS2, AS2Exception
/* 225:    */   {
/* 226:241 */     if (getTransferencia().getProductoTerminadoTransformacion() != null) {
/* 227:242 */       this.loteCrear = this.servicioLote.crearLoteInterno(getTransferencia().getIdOrganizacion(), getTransferencia().getProductoTerminadoTransformacion(), null, false);
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String guardarLote()
/* 232:    */   {
/* 233:    */     try
/* 234:    */     {
/* 235:249 */       this.servicioLote.guardar(this.loteCrear);
/* 236:250 */       getTransferencia().setLoteTransformacion(this.loteCrear);
/* 237:    */     }
/* 238:    */     catch (AS2Exception e)
/* 239:    */     {
/* 240:252 */       JsfUtil.addErrorMessage(e, "");
/* 241:    */     }
/* 242:    */     catch (Exception e)
/* 243:    */     {
/* 244:254 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 245:255 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 246:    */     }
/* 247:258 */     return "";
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void actualizarCantidad()
/* 251:    */   {
/* 252:262 */     Producto producto = getTransferencia().getProductoTerminadoTransformacion();
/* 253:263 */     actualizarProductoTerminado(producto, getTransferencia().getOrdenFabricacion());
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void actualizarProductoTerminado(Producto producto, OrdenFabricacion ordenFabricacion)
/* 257:    */   {
/* 258:    */     Object listaSubordenes;
/* 259:267 */     if (producto != null)
/* 260:    */     {
/* 261:268 */       for (Iterator localIterator = getTransferencia().getDetalleMovimientosInventario().iterator(); localIterator.hasNext();)
/* 262:    */       {
/* 263:268 */         detalle = (DetalleMovimientoInventario)localIterator.next();
/* 264:269 */         detalle.setEliminado(true);
/* 265:    */       }
/* 266:    */       DetalleMovimientoInventario detalle;
/* 267:272 */       producto.setCantidadProducir(getTransferencia().getCantidadTransformacion());
/* 268:273 */       producto.setArbolComponentes(this.servicioProducto.obtenerArbolComponentes(producto));
/* 269:274 */       if (ordenFabricacion != null)
/* 270:    */       {
/* 271:275 */         listaSubordenes = this.servicioOrdenFabricacion.obtenerListaSubordenFabricacion(ordenFabricacion.getId(), true);
/* 272:276 */         for (OrdenFabricacion suborden : (List)listaSubordenes)
/* 273:    */         {
/* 274:277 */           Producto material = suborden.getProducto();
/* 275:278 */           BigDecimal cantidad = suborden.getProporcionOrdenPadre().multiply(getTransferencia().getCantidadTransformacion());
/* 276:279 */           crearDetalle(material, cantidad);
/* 277:    */         }
/* 278:    */       }
/* 279:283 */       for (listaSubordenes = producto.getArbolComponentes().getHojas().iterator(); ((Iterator)listaSubordenes).hasNext();)
/* 280:    */       {
/* 281:283 */         NodoArbol<Producto> nodo = (NodoArbol)((Iterator)listaSubordenes).next();
/* 282:284 */         Producto material = (Producto)nodo.getValor();
/* 283:285 */         BigDecimal cantidad = material.getCantidadProducir();
/* 284:286 */         crearDetalle(material, cantidad);
/* 285:    */       }
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/* 290:    */   {
/* 291:292 */     DetalleMovimientoInventario dmi = (DetalleMovimientoInventario)getDtDetalleMovimientoInventario().getRowData();
/* 292:293 */     actualizarInventarioProducto(dmi);
/* 293:    */   }
/* 294:    */   
/* 295:    */   private void crearDetalle(Producto material, BigDecimal cantidad)
/* 296:    */   {
/* 297:297 */     material = this.servicioProducto.cargaDetalle(material.getId());
/* 298:298 */     material.setCantidadProducir(cantidad);
/* 299:299 */     if (material != null)
/* 300:    */     {
/* 301:    */       try
/* 302:    */       {
/* 303:301 */         this.servicioUnidadConversion.cargarListaUnidadConversion(material);
/* 304:    */       }
/* 305:    */       catch (ExcepcionAS2 e)
/* 306:    */       {
/* 307:303 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 308:    */       }
/* 309:305 */       DetalleMovimientoInventario detalleMovimientoInventario = null;
/* 310:307 */       for (DetalleMovimientoInventario detalle : getTransferencia().getDetalleMovimientosInventario()) {
/* 311:308 */         if (detalle.getProducto().getIdProducto() == material.getIdProducto())
/* 312:    */         {
/* 313:309 */           detalleMovimientoInventario = detalle;
/* 314:310 */           detalleMovimientoInventario.setEliminado(false);
/* 315:311 */           break;
/* 316:    */         }
/* 317:    */       }
/* 318:315 */       if (detalleMovimientoInventario == null)
/* 319:    */       {
/* 320:316 */         detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 321:317 */         detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 322:318 */         detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 323:319 */         detalleMovimientoInventario.setProducto(material);
/* 324:    */         
/* 325:321 */         detalleMovimientoInventario.setMovimientoInventario(getTransferencia());
/* 326:    */         
/* 327:323 */         detalleMovimientoInventario.setUnidadConversion(material.getUnidad());
/* 328:324 */         actualizarInventarioProducto(detalleMovimientoInventario);
/* 329:    */         
/* 330:326 */         getTransferencia().getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 331:    */       }
/* 332:328 */       detalleMovimientoInventario.setCantidad(cantidad.setScale(4, RoundingMode.HALF_UP));
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   private void actualizarInventarioProducto(DetalleMovimientoInventario detMovInventario)
/* 337:    */   {
/* 338:333 */     Producto producto = detMovInventario.getProducto();
/* 339:334 */     detMovInventario.setBodegaOrigen(this.servicioProducto.obtenerBodegaTrabajoProducto(producto, Integer.valueOf(AppUtil.getSucursal().getIdSucursal())));
/* 340:335 */     detMovInventario.setBodegaDestino(getTransferencia().getBodegaDestino());
/* 341:336 */     detMovInventario.setUnidadConversion(producto.getUnidad());
/* 342:337 */     detMovInventario.setListaBodegasTrabajoProducto(this.servicioProducto.obtenerListaBodegasTrabajoProducto(producto, null));
/* 343:    */     
/* 344:339 */     InventarioProducto inventarioProducto = detMovInventario.getInventarioProducto();
/* 345:340 */     if (inventarioProducto == null) {
/* 346:341 */       inventarioProducto = new InventarioProducto();
/* 347:    */     }
/* 348:343 */     inventarioProducto.setOperacion(getTransferencia().getDocumento().getOperacion());
/* 349:344 */     inventarioProducto.setDetalleMovimientoInventario(detMovInventario);
/* 350:345 */     inventarioProducto.setProducto(producto);
/* 351:346 */     detMovInventario.setInventarioProducto(inventarioProducto);
/* 352:    */   }
/* 353:    */   
/* 354:    */   public List<Lote> autocompletarLotesProductoTerminado(String consulta)
/* 355:    */   {
/* 356:350 */     List<Lote> listaLote = new ArrayList();
/* 357:352 */     if (getTransferencia().getProductoTerminadoTransformacion() != null) {
/* 358:353 */       listaLote = this.servicioLote.autocompletarLote(getTransferencia().getProductoTerminadoTransformacion(), consulta + "~LIKE" + "~MAX_RESULT");
/* 359:    */     }
/* 360:356 */     return listaLote;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public List<Lote> autocompletarLotes(String consulta)
/* 364:    */   {
/* 365:360 */     DetalleMovimientoInventario detalleDespachoCliente = (DetalleMovimientoInventario)getDtDetalleMovimientoInventario().getRowData();
/* 366:361 */     List<Lote> listaLote = this.servicioLote.autocompletarLote(detalleDespachoCliente.getProducto(), consulta + "~LIKE" + "~MAX_RESULT");
/* 367:362 */     return listaLote;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public Lote getLoteCrear()
/* 371:    */   {
/* 372:366 */     return this.loteCrear;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setLoteCrear(Lote loteCrear)
/* 376:    */   {
/* 377:370 */     this.loteCrear = loteCrear;
/* 378:    */   }
/* 379:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.ProduccionBomBean
 * JD-Core Version:    0.7.0.1
 */