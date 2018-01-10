/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   9:    */ import com.asinfo.as2.entities.Dispositivo;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  12:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*  13:    */ import com.asinfo.as2.entities.Lote;
/*  14:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  15:    */ import com.asinfo.as2.entities.OrdenFabricacionMaquina;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  18:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  19:    */ import com.asinfo.as2.entities.Producto;
/*  20:    */ import com.asinfo.as2.entities.Sucursal;
/*  21:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  22:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*  23:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  24:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  25:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  26:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  27:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  28:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  29:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  30:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  31:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  32:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  33:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  34:    */ import com.asinfo.as2.inventario.procesos.controller.AjusteInventarioBean;
/*  35:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  36:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMaquina;
/*  37:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*  38:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  39:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  40:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  41:    */ import com.asinfo.as2.util.AppUtil;
/*  42:    */ import com.asinfo.as2.utils.JsfUtil;
/*  43:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  44:    */ import java.math.BigDecimal;
/*  45:    */ import java.math.RoundingMode;
/*  46:    */ import java.util.ArrayList;
/*  47:    */ import java.util.Date;
/*  48:    */ import java.util.HashMap;
/*  49:    */ import java.util.List;
/*  50:    */ import java.util.Map;
/*  51:    */ import javax.ejb.EJB;
/*  52:    */ import javax.faces.bean.ManagedBean;
/*  53:    */ import javax.faces.bean.ViewScoped;
/*  54:    */ import javax.faces.context.ExternalContext;
/*  55:    */ import javax.faces.context.FacesContext;
/*  56:    */ import javax.servlet.ServletContext;
/*  57:    */ import javax.servlet.http.HttpSession;
/*  58:    */ import org.apache.log4j.Logger;
/*  59:    */ import org.primefaces.component.datatable.DataTable;
/*  60:    */ 
/*  61:    */ @ManagedBean
/*  62:    */ @ViewScoped
/*  63:    */ public class IngresoFabricacionBean
/*  64:    */   extends AjusteInventarioBean
/*  65:    */ {
/*  66:    */   @EJB
/*  67:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  68:    */   @EJB
/*  69:    */   private ServicioLote servicioLote;
/*  70:    */   @EJB
/*  71:    */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*  72:    */   @EJB
/*  73:    */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*  74:    */   @EJB
/*  75:    */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  76:    */   @EJB
/*  77:    */   private ServicioGenerico<OrdenFabricacionMaquina> servicioOrdenFabricacionMaquina;
/*  78:    */   @EJB
/*  79:    */   private ServicioMaquina servicioMaquina;
/*  80:    */   @EJB
/*  81:    */   private ServicioOrganizacion servicioOrganizacion;
/*  82:    */   @EJB
/*  83:    */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  84:    */   private static final long serialVersionUID = -5463907651730263568L;
/*  85:    */   protected DetalleMovimientoInventario detalleSeleccionado;
/*  86:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  87:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  88:    */   private List<Dispositivo> listaDispositivo;
/*  89:    */   private List<Maquina> listaMaquina;
/*  90:    */   private List<PersonaResponsable> listaPersonaResponsable;
/*  91:    */   private Lote loteCrear;
/*  92:    */   private LecturaBalanza lecturaBalanza;
/*  93:    */   private List<UnidadManejo> listaUnidadManejo;
/*  94:    */   private List<UnidadManejo> listaPallet;
/*  95:    */   private Boolean mostrarBalanza;
/*  96:    */   private boolean indicadorTodosMaterial;
/*  97:    */   private boolean indicadorMaquinaObligatoria;
/*  98:    */   private String busquedaPorOrdenFabricacion;
/*  99:    */   protected DataTable dtLecturaBalanza;
/* 100:    */   protected List<LecturaBalanza> listaLecturaBalanza;
/* 101:    */   private OrganizacionConfiguracion organizacionConfiguracion;
/* 102:    */   
/* 103:    */   public DocumentoBase getDocumentoBase()
/* 104:    */   {
/* 105:119 */     return DocumentoBase.INGRESO_PRODUCCION;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void crearAjuste()
/* 109:    */   {
/* 110:124 */     if ((this.ajusteInventario != null) && (this.ajusteInventario.getOrdenFabricacion() != null))
/* 111:    */     {
/* 112:125 */       this.ajusteInventario = this.servicioMovimientoInventario.obtenerIngresoFabricacionPorFecha(AppUtil.getOrganizacion().getId(), new Date(), 
/* 113:126 */         getAjusteInventario().getOrdenFabricacion());
/* 114:127 */       if ((this.ajusteInventario != null) && (this.ajusteInventario.getOrdenFabricacion() != null) && 
/* 115:128 */         (this.ajusteInventario.getOrdenFabricacion().getAtributoOrdenFabricacion() != null)) {
/* 116:129 */         this.ajusteInventario = null;
/* 117:    */       }
/* 118:    */     }
/* 119:132 */     if (this.ajusteInventario == null)
/* 120:    */     {
/* 121:133 */       super.crearAjuste();
/* 122:134 */       getAjusteInventario().setEstado(Estado.PROCESADO);
/* 123:135 */       getAjusteInventario().setNumero("");
/* 124:    */     }
/* 125:137 */     crearDetalle();
/* 126:138 */     this.mostrarBalanza = null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void crearDetalle()
/* 130:    */   {
/* 131:142 */     this.detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 132:143 */     this.detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 133:144 */     this.detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 134:145 */     this.detalleMovimientoInventario.setCantidadOrigen(BigDecimal.ZERO);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String editar()
/* 138:    */   {
/* 139:150 */     if ((this.ajusteInventario != null) && (this.ajusteInventario.getId() != 0))
/* 140:    */     {
/* 141:151 */       this.ajusteInventario = this.servicioMovimientoInventario.cargarDetallesDiariosIngresoFabricacion(this.ajusteInventario, this.categoriaProductoSeleccionado, 
/* 142:152 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 143:153 */       this.listaLecturaBalanza = null;
/* 144:154 */       if (!this.ajusteInventario.getEstado().equals(Estado.PROCESADO))
/* 145:    */       {
/* 146:155 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " " + this.ajusteInventario.getEstado());
/* 147:156 */         return "";
/* 148:    */       }
/* 149:158 */       crearDetalle();
/* 150:159 */       ordenFabricacionValueChanged();
/* 151:    */       
/* 152:161 */       setEditado(true);
/* 153:    */     }
/* 154:    */     else
/* 155:    */     {
/* 156:163 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 157:    */     }
/* 158:165 */     return "";
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String guardar()
/* 162:    */   {
/* 163:175 */     boolean noError = false;
/* 164:    */     try
/* 165:    */     {
/* 166:178 */       validar();
/* 167:179 */       this.detalleMovimientoInventario.setMaquina(this.ajusteInventario.getMaquina());
/* 168:180 */       this.ajusteInventario = this.servicioMovimientoInventario.guardarIngresoFabricacion(this.ajusteInventario, this.detalleMovimientoInventario, null, 
/* 169:181 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion());
/* 170:182 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 171:    */     }
/* 172:    */     catch (ExcepcionAS2Inventario e)
/* 173:    */     {
/* 174:184 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 175:185 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 176:    */     }
/* 177:    */     catch (ExcepcionAS2Financiero e)
/* 178:    */     {
/* 179:187 */       noError = true;
/* 180:188 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 181:189 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 182:    */     }
/* 183:    */     catch (ExcepcionAS2 e)
/* 184:    */     {
/* 185:191 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 186:192 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 187:    */     }
/* 188:    */     catch (AS2Exception e)
/* 189:    */     {
/* 190:194 */       noError = true;
/* 191:195 */       JsfUtil.addErrorMessage(e, "");
/* 192:196 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 193:    */     }
/* 194:    */     catch (Exception e)
/* 195:    */     {
/* 196:198 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 197:199 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 198:    */     }
/* 199:    */     finally
/* 200:    */     {
/* 201:201 */       if (!noError)
/* 202:    */       {
/* 203:202 */         this.ajusteInventario = this.servicioMovimientoInventario.cargarDetallesDiariosIngresoFabricacion(getAjusteInventario(), this.categoriaProductoSeleccionado, 
/* 204:203 */           AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 205:204 */         crearDetalle();
/* 206:205 */         ordenFabricacionValueChanged();
/* 207:206 */         this.listaLecturaBalanza = null;
/* 208:    */       }
/* 209:    */     }
/* 210:209 */     return "";
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void ordenFabricacionValueChanged()
/* 214:    */   {
/* 215:218 */     if ((this.detalleMovimientoInventario != null) && (getAjusteInventario().getOrdenFabricacion() != null))
/* 216:    */     {
/* 217:    */       try
/* 218:    */       {
/* 219:220 */         Producto producto = getAjusteInventario().getOrdenFabricacion().getProducto();
/* 220:221 */         this.detalleMovimientoInventario.setProducto(producto);
/* 221:222 */         this.detalleMovimientoInventario.setCantidadOrigen(BigDecimal.ZERO);
/* 222:    */         
/* 223:224 */         cargarBodega(this.detalleMovimientoInventario);
/* 224:226 */         if (this.detalleMovimientoInventario.getInventarioProducto() != null) {
/* 225:227 */           this.detalleMovimientoInventario.getInventarioProducto().setProducto(producto);
/* 226:    */         }
/* 227:230 */         this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 228:231 */         this.detalleMovimientoInventario.setLote(null);
/* 229:232 */         getLecturaBalanza().setProducto(producto);
/* 230:233 */         cargarUnidadManejoPredeterminada();
/* 231:234 */         this.detalleMovimientoInventario.setDescripcion(getAjusteInventario().getOrdenFabricacion().getDescripcion());
/* 232:237 */         if (this.servicioUnidadConversion.obtenerUnidadConversionConProducto(producto).isEmpty()) {
/* 233:238 */           this.detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 234:    */         } else {
/* 235:240 */           this.detalleMovimientoInventario.setUnidadConversion(null);
/* 236:    */         }
/* 237:243 */         this.servicioUnidadConversion.obtenerUnidadConversionConProducto(producto);
/* 238:    */       }
/* 239:    */       catch (ExcepcionAS2 e)
/* 240:    */       {
/* 241:248 */         e.printStackTrace();
/* 242:249 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 243:    */       }
/* 244:    */     }
/* 245:251 */     else if (getAjusteInventario().getOrdenFabricacion() == null)
/* 246:    */     {
/* 247:252 */       this.detalleMovimientoInventario.setProducto(null);
/* 248:253 */       this.detalleMovimientoInventario.setCantidadOrigen(BigDecimal.ZERO);
/* 249:254 */       this.detalleMovimientoInventario.setLote(null);
/* 250:    */     }
/* 251:256 */     this.mostrarBalanza = null;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void actualizarCantidadDetalle()
/* 255:    */   {
/* 256:260 */     if ((this.detalleMovimientoInventario != null) && (this.detalleMovimientoInventario.getCantidadOrigen() != null) && 
/* 257:261 */       (this.detalleMovimientoInventario.getCantidadOrigen().compareTo(BigDecimal.ZERO) > 0) && 
/* 258:262 */       (this.detalleMovimientoInventario.getUnidadConversion() != null))
/* 259:    */     {
/* 260:263 */       BigDecimal cantidadOrigen = this.detalleMovimientoInventario.getCantidadOrigen();
/* 261:264 */       this.detalleMovimientoInventario.setCantidad(cantidadOrigen);
/* 262:    */       try
/* 263:    */       {
/* 264:270 */         BigDecimal valorConversion = this.servicioProducto.convierteUnidad(this.detalleMovimientoInventario.getUnidadConversion(), this.detalleMovimientoInventario.getProducto().getUnidad(), this.detalleMovimientoInventario.getProducto().getIdProducto(), this.detalleMovimientoInventario.getCantidadOrigen()).setScale(2, RoundingMode.HALF_UP);
/* 265:271 */         this.detalleMovimientoInventario.setTraCantidadCoversion(valorConversion);
/* 266:272 */         this.detalleMovimientoInventario.setCantidad(valorConversion);
/* 267:    */       }
/* 268:    */       catch (ExcepcionAS2Inventario e)
/* 269:    */       {
/* 270:274 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 271:275 */         e.printStackTrace();
/* 272:    */       }
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String eliminarDetalle()
/* 277:    */   {
/* 278:    */     try
/* 279:    */     {
/* 280:283 */       this.servicioMovimientoInventario.eliminarDetalleIngresoFabricacion(getAjusteInventario(), this.detalleSeleccionado, 
/* 281:284 */         isPermiteEliminacionIngresoProduccion());
/* 282:285 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 283:    */     }
/* 284:    */     catch (ExcepcionAS2Inventario e)
/* 285:    */     {
/* 286:288 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 287:289 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 288:    */     }
/* 289:    */     catch (ExcepcionAS2 e)
/* 290:    */     {
/* 291:291 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 292:292 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 293:    */     }
/* 294:    */     catch (AS2Exception e)
/* 295:    */     {
/* 296:294 */       JsfUtil.addErrorMessage(e, "");
/* 297:295 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 298:    */     }
/* 299:    */     catch (Exception e)
/* 300:    */     {
/* 301:297 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 302:298 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 303:    */     }
/* 304:    */     finally
/* 305:    */     {
/* 306:300 */       this.ajusteInventario = this.servicioMovimientoInventario.cargarDetallesDiariosIngresoFabricacion(this.ajusteInventario, this.categoriaProductoSeleccionado, 
/* 307:301 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 308:302 */       this.listaLecturaBalanza = null;
/* 309:    */     }
/* 310:304 */     return "";
/* 311:    */   }
/* 312:    */   
/* 313:    */   public DetalleMovimientoInventario getDetalleSeleccionado()
/* 314:    */   {
/* 315:308 */     return this.detalleSeleccionado;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setDetalleSeleccionado(DetalleMovimientoInventario detalleSeleccionado)
/* 319:    */   {
/* 320:312 */     this.detalleSeleccionado = detalleSeleccionado;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 324:    */   {
/* 325:316 */     HashMap<String, String> filters = new HashMap();
/* 326:317 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 327:318 */     filters.put("activo", "=true");
/* 328:319 */     if (this.listaCategoriaProductos == null) {
/* 329:320 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 330:    */     }
/* 331:322 */     return this.listaCategoriaProductos;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 335:    */   {
/* 336:326 */     return this.categoriaProductoSeleccionado;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 340:    */   {
/* 341:330 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void actualizarCategoriaProducto()
/* 345:    */   {
/* 346:334 */     this.ajusteInventario = this.servicioMovimientoInventario.cargarDetallesDiariosIngresoFabricacion(getAjusteInventario(), this.categoriaProductoSeleccionado, 
/* 347:335 */       AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 348:336 */     this.ajusteInventario.setOrdenFabricacion(null);
/* 349:337 */     ordenFabricacionValueChanged();
/* 350:    */   }
/* 351:    */   
/* 352:    */   public List<OrdenFabricacion> autocompletarOrdenFabricacion(String cadena)
/* 353:    */   {
/* 354:341 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 355:    */     
/* 356:343 */     Boolean indicadorBusquedaPorOrdenFabricacion = null;
/* 357:344 */     if (getBusquedaPorOrdenFabricacion().equals("ORDEN")) {
/* 358:345 */       indicadorBusquedaPorOrdenFabricacion = Boolean.valueOf(true);
/* 359:346 */     } else if (getBusquedaPorOrdenFabricacion().equals("SUBORDEN")) {
/* 360:347 */       indicadorBusquedaPorOrdenFabricacion = Boolean.valueOf(false);
/* 361:    */     }
/* 362:350 */     List<Integer> listIdsSucursalesAsignadasUsuarioEnSesion = getListaIdsSucursalesAsignadasUsuarioEnSesion(AppUtil.getUsuarioEnSesion());
/* 363:351 */     return this.servicioOrdenFabricacion.autocompletarOrdenesAbiertas(idOrganizacion, cadena, this.categoriaProductoSeleccionado, listIdsSucursalesAsignadasUsuarioEnSesion, indicadorBusquedaPorOrdenFabricacion);
/* 364:    */   }
/* 365:    */   
/* 366:    */   protected void cargarBodega(DetalleMovimientoInventario detalleMovimientoInventario)
/* 367:    */   {
/* 368:356 */     Bodega bodegaTrabajoOrden = getAjusteInventario().getOrdenFabricacion().getBodega();
/* 369:    */     
/* 370:358 */     Bodega bodegaTrabajo = bodegaTrabajoOrden != null ? bodegaTrabajoOrden : this.servicioProducto.obtenerBodegaTrabajoProducto(detalleMovimientoInventario.getProducto(), 
/* 371:359 */       Integer.valueOf(detalleMovimientoInventario.getIdSucursal()));
/* 372:361 */     if (bodegaTrabajo != null)
/* 373:    */     {
/* 374:362 */       detalleMovimientoInventario.setBodegaOrigen(bodegaTrabajo);
/* 375:363 */       detalleMovimientoInventario.setBodegaDestino(bodegaTrabajo);
/* 376:    */     }
/* 377:    */     else
/* 378:    */     {
/* 379:365 */       addErrorMessage(getLanguageController().getMensaje("msg_info_parametrizar_bodega_trabajo") + " ( " + (detalleMovimientoInventario
/* 380:366 */         .getProducto() != null ? detalleMovimientoInventario.getProducto().getNombre() : "") + " - " + 
/* 381:367 */         AppUtil.getSucursal().getNombre() + " ).");
/* 382:    */     }
/* 383:369 */     if ((detalleMovimientoInventario.getBodegaOrigen() == null) && (AppUtil.getBodega() != null))
/* 384:    */     {
/* 385:370 */       detalleMovimientoInventario.setBodegaOrigen(AppUtil.getBodega());
/* 386:371 */       detalleMovimientoInventario.setBodegaDestino(AppUtil.getBodega());
/* 387:    */     }
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void crearLoteListener()
/* 391:    */     throws ExcepcionAS2, AS2Exception
/* 392:    */   {
/* 393:376 */     if (this.ajusteInventario.getOrdenFabricacion() != null)
/* 394:    */     {
/* 395:377 */       this.loteCrear = this.detalleMovimientoInventario.getLote();
/* 396:378 */       if ((this.loteCrear != null) && (this.loteCrear.getCodigo() != null) && (!this.loteCrear.getCodigo().isEmpty()))
/* 397:    */       {
/* 398:379 */         Map<String, String> filters = new HashMap();
/* 399:380 */         filters.put("codigo", "=" + this.loteCrear.getCodigo());
/* 400:381 */         filters.put("producto.idProducto", "=" + this.ajusteInventario.getOrdenFabricacion().getProducto().getIdProducto());
/* 401:382 */         filters.put("indicadorMovimientoInterno", "true");
/* 402:383 */         List<Lote> loteList = this.servicioLote.obtenerListaCombo("codigo", true, filters);
/* 403:384 */         this.loteCrear = (!loteList.isEmpty() ? (Lote)loteList.get(0) : this.loteCrear);
/* 404:    */       }
/* 405:    */       else
/* 406:    */       {
/* 407:386 */         this.loteCrear = this.servicioLote.crearLoteInterno(this.ajusteInventario.getIdOrganizacion(), this.ajusteInventario.getOrdenFabricacion().getProducto(), null, false);
/* 408:    */       }
/* 409:389 */       if (this.ajusteInventario.getOrdenFabricacion().getValorAtributoOrdenFabricacion() != null) {
/* 410:390 */         this.loteCrear.setValorAtributo1(this.ajusteInventario.getOrdenFabricacion().getValorAtributoOrdenFabricacion());
/* 411:    */       }
/* 412:393 */       this.loteCrear.setDisableMovimientoInterno(true);
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   public String guardarLote()
/* 417:    */   {
/* 418:    */     try
/* 419:    */     {
/* 420:399 */       this.loteCrear.setCodigo(this.loteCrear.getProducto().getPrefijoLote() + this.loteCrear.getCodigo());
/* 421:400 */       if (this.loteCrear.getIdLote() != 0) {
/* 422:401 */         this.servicioLote.guardar(this.loteCrear);
/* 423:    */       }
/* 424:403 */       this.loteCrear.getProducto().setPrefijoLote(null);
/* 425:404 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 426:    */     }
/* 427:    */     catch (AS2Exception e)
/* 428:    */     {
/* 429:406 */       JsfUtil.addErrorMessage(e, "");
/* 430:    */     }
/* 431:    */     catch (Exception e)
/* 432:    */     {
/* 433:408 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 434:409 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 435:    */     }
/* 436:411 */     this.detalleMovimientoInventario.setLote(this.loteCrear);
/* 437:412 */     return "";
/* 438:    */   }
/* 439:    */   
/* 440:    */   public List<Lote> autocompletarLotesProductoTerminado(String consulta)
/* 441:    */   {
/* 442:416 */     List<Lote> listaLote = new ArrayList();
/* 443:418 */     if (this.ajusteInventario.getOrdenFabricacion() != null) {
/* 444:419 */       listaLote = this.servicioLote.autocompletarLote(this.ajusteInventario.getOrdenFabricacion().getProducto(), consulta + "~LIKE" + "~MAX_RESULT");
/* 445:    */     }
/* 446:422 */     return listaLote;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void seleccionarDetalleSeleccionado()
/* 450:    */   {
/* 451:426 */     this.detalleSeleccionado = ((DetalleMovimientoInventario)getDtDetalleAjuste().getRowData());
/* 452:    */   }
/* 453:    */   
/* 454:    */   public String getUrlReporte()
/* 455:    */   {
/* 456:430 */     FacesContext context = FacesContext.getCurrentInstance();
/* 457:431 */     ExternalContext externalContext = context.getExternalContext();
/* 458:432 */     Object session = externalContext.getSession(false);
/* 459:433 */     HttpSession httpSession = (HttpSession)session;
/* 460:434 */     String url = "";
/* 461:435 */     if ((getDetalleSeleccionado() != null) && (getDetalleSeleccionado().getLote() != null)) {
/* 462:439 */       url = httpSession.getServletContext().getContextPath() + "/servlet/reporteEtiquetaIngresoFabricacion?idOrganizacion=" + AppUtil.getOrganizacion().getId() + "&idDocumento=" + getDetalleSeleccionado().getMovimientoInventario().getDocumento().getId() + "&numero=" + getDetalleSeleccionado().getMovimientoInventario().getNumero() + "&idLote=" + getDetalleSeleccionado().getLote().getId();
/* 463:    */     }
/* 464:441 */     return url;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public Lote getLoteCrear()
/* 468:    */   {
/* 469:445 */     return this.loteCrear;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setLoteCrear(Lote loteCrear)
/* 473:    */   {
/* 474:449 */     this.loteCrear = loteCrear;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public LecturaBalanza getLecturaBalanza()
/* 478:    */   {
/* 479:453 */     if (this.lecturaBalanza == null)
/* 480:    */     {
/* 481:454 */       this.lecturaBalanza = new LecturaBalanza();
/* 482:455 */       this.lecturaBalanza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 483:456 */       this.lecturaBalanza.setIdSucursal(AppUtil.getSucursal().getId());
/* 484:457 */       this.lecturaBalanza.setDispositivo(AppUtil.getUsuarioEnSesion().getDispositivo());
/* 485:458 */       cargarUnidadManejoPredeterminada();
/* 486:    */     }
/* 487:460 */     return this.lecturaBalanza;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setLecturaBalanza(LecturaBalanza lecturaBalanza)
/* 491:    */   {
/* 492:464 */     this.lecturaBalanza = lecturaBalanza;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public List<UnidadManejo> getListaUnidadManejo()
/* 496:    */   {
/* 497:471 */     if ((this.listaUnidadManejo == null) || ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)))
/* 498:    */     {
/* 499:472 */       this.listaUnidadManejo = new ArrayList();
/* 500:473 */       if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 501:474 */         this.listaUnidadManejo = this.servicioProducto.obtenerListaUnidadManejoPorProducto(this.lecturaBalanza.getProducto());
/* 502:    */       }
/* 503:    */     }
/* 504:477 */     return this.listaUnidadManejo;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/* 508:    */   {
/* 509:485 */     this.listaUnidadManejo = listaUnidadManejo;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public List<UnidadManejo> getListaPallet()
/* 513:    */   {
/* 514:489 */     if (this.listaPallet == null)
/* 515:    */     {
/* 516:490 */       Map<String, String> filters = new HashMap();
/* 517:491 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 518:492 */       filters.put("activo", "true");
/* 519:493 */       filters.put("categoriaUnidadManejo.indicadorPallet", "true");
/* 520:494 */       this.listaPallet = this.servicioUnidadManejo.obtenerListaCombo(UnidadManejo.class, "nombre", true, filters);
/* 521:    */     }
/* 522:496 */     return this.listaPallet;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void setListaPallet(List<UnidadManejo> listaPallet)
/* 526:    */   {
/* 527:500 */     this.listaPallet = listaPallet;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public boolean isMostrarBalanza()
/* 531:    */   {
/* 532:504 */     if (this.mostrarBalanza == null) {
/* 533:505 */       this.mostrarBalanza = Boolean.valueOf((ParametrosSistema.getProduccionUsaBalanza(AppUtil.getOrganizacion().getId()).booleanValue()) && 
/* 534:506 */         (this.ajusteInventario.getOrdenFabricacion() != null) && 
/* 535:507 */         (this.ajusteInventario.getOrdenFabricacion().getProducto().isIndicadorPesoBalanza()));
/* 536:    */     }
/* 537:509 */     return this.mostrarBalanza.booleanValue();
/* 538:    */   }
/* 539:    */   
/* 540:    */   public List<Dispositivo> getListaDispositivo()
/* 541:    */   {
/* 542:513 */     if (this.listaDispositivo == null)
/* 543:    */     {
/* 544:514 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 545:515 */       filtros.put("activo", "true");
/* 546:516 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 547:    */     }
/* 548:518 */     return this.listaDispositivo;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 552:    */   {
/* 553:522 */     if (this.listaLecturaBalanza == null)
/* 554:    */     {
/* 555:523 */       this.listaLecturaBalanza = new ArrayList();
/* 556:524 */       for (DetalleMovimientoInventario detalle : this.ajusteInventario.getDetalleMovimientosInventario()) {
/* 557:525 */         if (!detalle.isEliminado()) {
/* 558:526 */           for (LecturaBalanza lectura : detalle.getListaLecturaBalanza()) {
/* 559:527 */             if (!lectura.isEliminado()) {
/* 560:528 */               this.listaLecturaBalanza.add(lectura);
/* 561:    */             }
/* 562:    */           }
/* 563:    */         }
/* 564:    */       }
/* 565:    */     }
/* 566:534 */     return this.listaLecturaBalanza;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public DataTable getDtLecturaBalanza()
/* 570:    */   {
/* 571:538 */     return this.dtLecturaBalanza;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public void setDtLecturaBalanza(DataTable dtLecturaBalanza)
/* 575:    */   {
/* 576:542 */     this.dtLecturaBalanza = dtLecturaBalanza;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void eliminarLecturaBalanza(LecturaBalanza lectura)
/* 580:    */   {
/* 581:    */     try
/* 582:    */     {
/* 583:547 */       LecturaBalanza lecturaEliminar = (LecturaBalanza)this.dtLecturaBalanza.getRowData();
/* 584:548 */       this.servicioMovimientoInventario.eliminarDetalleIngresoFabricacion(getAjusteInventario(), lecturaEliminar.getDetalleMovimientoInventario(), 
/* 585:549 */         isPermiteEliminacionIngresoProduccion());
/* 586:    */       
/* 587:551 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 588:    */     }
/* 589:    */     catch (ExcepcionAS2Inventario e)
/* 590:    */     {
/* 591:554 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 592:555 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 593:    */     }
/* 594:    */     catch (ExcepcionAS2 e)
/* 595:    */     {
/* 596:557 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 597:558 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 598:    */     }
/* 599:    */     catch (AS2Exception e)
/* 600:    */     {
/* 601:560 */       JsfUtil.addErrorMessage(e, "");
/* 602:561 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 603:    */     }
/* 604:    */     catch (Exception e)
/* 605:    */     {
/* 606:563 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 607:564 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 608:    */     }
/* 609:    */     finally
/* 610:    */     {
/* 611:566 */       this.ajusteInventario = this.servicioMovimientoInventario.cargarDetallesDiariosIngresoFabricacion(this.ajusteInventario, this.categoriaProductoSeleccionado, 
/* 612:567 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 613:568 */       this.listaLecturaBalanza = null;
/* 614:    */     }
/* 615:    */   }
/* 616:    */   
/* 617:    */   public void capturarPesoListener()
/* 618:    */   {
/* 619:573 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 620:    */       try
/* 621:    */       {
/* 622:575 */         this.servicioMarcacionDispositivo.calcularPesoNeto(AppUtil.getOrganizacion().getId(), this.lecturaBalanza, true);
/* 623:    */       }
/* 624:    */       catch (AS2Exception ex)
/* 625:    */       {
/* 626:577 */         JsfUtil.addErrorMessage(ex, "");
/* 627:    */       }
/* 628:    */     }
/* 629:    */   }
/* 630:    */   
/* 631:    */   public void agregarPesoListener()
/* 632:    */   {
/* 633:583 */     if ((this.lecturaBalanza.getProducto() != null) && (this.lecturaBalanza.getPesoNeto().compareTo(BigDecimal.ZERO) > 0))
/* 634:    */     {
/* 635:584 */       boolean guardo = false;
/* 636:585 */       int idIngreso = getAjusteInventario().getId();
/* 637:    */       try
/* 638:    */       {
/* 639:588 */         validar();
/* 640:589 */         this.detalleMovimientoInventario.setMaquina(this.ajusteInventario.getMaquina());
/* 641:590 */         this.ajusteInventario = this.servicioMovimientoInventario.guardarIngresoFabricacion(this.ajusteInventario, this.detalleMovimientoInventario, this.lecturaBalanza, 
/* 642:591 */           AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion());
/* 643:592 */         guardo = true;
/* 644:593 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 645:    */       }
/* 646:    */       catch (ExcepcionAS2Inventario e)
/* 647:    */       {
/* 648:595 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 649:596 */         LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 650:    */       }
/* 651:    */       catch (ExcepcionAS2Financiero e)
/* 652:    */       {
/* 653:598 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 654:599 */         LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 655:    */       }
/* 656:    */       catch (ExcepcionAS2 e)
/* 657:    */       {
/* 658:601 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 659:602 */         LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 660:    */       }
/* 661:    */       catch (AS2Exception e)
/* 662:    */       {
/* 663:604 */         JsfUtil.addErrorMessage(e, "");
/* 664:605 */         LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 665:    */       }
/* 666:    */       catch (Exception e)
/* 667:    */       {
/* 668:607 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 669:608 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 670:    */       }
/* 671:611 */       if (guardo) {
/* 672:612 */         this.ajusteInventario = this.servicioMovimientoInventario.cargarDetallesDiariosIngresoFabricacion(getAjusteInventario(), this.categoriaProductoSeleccionado, 
/* 673:613 */           AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 674:614 */       } else if (idIngreso == 0) {
/* 675:616 */         getAjusteInventario().setId(0);
/* 676:    */       }
/* 677:619 */       Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 678:620 */       this.lecturaBalanza = null;
/* 679:621 */       getLecturaBalanza().setDispositivo(dispositivo);
/* 680:    */       
/* 681:623 */       crearDetalle();
/* 682:624 */       ordenFabricacionValueChanged();
/* 683:625 */       this.listaLecturaBalanza = null;
/* 684:    */     }
/* 685:    */   }
/* 686:    */   
/* 687:    */   public void calcularCantidad()
/* 688:    */   {
/* 689:630 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getUnidadManejo() != null)) {
/* 690:631 */       this.servicioMarcacionDispositivo.calcularCantidad(this.lecturaBalanza);
/* 691:    */     }
/* 692:    */   }
/* 693:    */   
/* 694:    */   public List<Maquina> getListaMaquina()
/* 695:    */   {
/* 696:636 */     this.listaMaquina = new ArrayList();
/* 697:637 */     if (this.indicadorTodosMaterial)
/* 698:    */     {
/* 699:638 */       this.listaMaquina = this.servicioMaquina.obtenerListaCombo("nombre", true, null);
/* 700:    */     }
/* 701:639 */     else if (getAjusteInventario().getOrdenFabricacion() != null)
/* 702:    */     {
/* 703:641 */       HashMap<String, String> filters = new HashMap();
/* 704:642 */       if (getAjusteInventario().getOrdenFabricacion().getOrdenFabricacionPrincipal() != null) {
/* 705:643 */         filters.put("ordenFabricacion.idOrdenFabricacion", "" + 
/* 706:644 */           getAjusteInventario().getOrdenFabricacion().getOrdenFabricacionPrincipal().getIdOrdenFabricacion());
/* 707:    */       } else {
/* 708:646 */         filters.put("ordenFabricacion.idOrdenFabricacion", "" + getAjusteInventario().getOrdenFabricacion().getIdOrdenFabricacion());
/* 709:    */       }
/* 710:649 */       List<String> listaCampos = new ArrayList();
/* 711:650 */       listaCampos.add("maquina");
/* 712:651 */       List<OrdenFabricacionMaquina> lista = this.servicioOrdenFabricacionMaquina.obtenerListaPorPagina(OrdenFabricacionMaquina.class, 0, 1000, "idOrdenFabricacionMaquina", true, filters, listaCampos);
/* 713:653 */       for (OrdenFabricacionMaquina ordenFabricacionMaquina : lista) {
/* 714:654 */         this.listaMaquina.add(ordenFabricacionMaquina.getMaquina());
/* 715:    */       }
/* 716:657 */       if (!this.listaMaquina.isEmpty()) {
/* 717:658 */         this.indicadorMaquinaObligatoria = true;
/* 718:    */       }
/* 719:    */     }
/* 720:662 */     return this.listaMaquina;
/* 721:    */   }
/* 722:    */   
/* 723:    */   public void validar()
/* 724:    */     throws AS2Exception
/* 725:    */   {
/* 726:666 */     if ((this.indicadorMaquinaObligatoria) && (this.ajusteInventario.getMaquina() == null)) {
/* 727:667 */       throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_MAQUINA_OBLIGATORIA", new String[] { "" });
/* 728:    */     }
/* 729:    */   }
/* 730:    */   
/* 731:    */   public List<PersonaResponsable> getListaPersonaResponsable()
/* 732:    */   {
/* 733:672 */     if (this.listaPersonaResponsable == null)
/* 734:    */     {
/* 735:673 */       HashMap<String, String> filters = new HashMap();
/* 736:674 */       filters.put("indicadorOrdenFabricacion", "true");
/* 737:675 */       this.listaPersonaResponsable = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filters);
/* 738:    */     }
/* 739:677 */     return this.listaPersonaResponsable;
/* 740:    */   }
/* 741:    */   
/* 742:    */   public void cargarUnidadManejoPredeterminada()
/* 743:    */   {
/* 744:681 */     for (UnidadManejo unidadManejo : getListaUnidadManejo()) {
/* 745:682 */       if (unidadManejo.isPredeterminado()) {
/* 746:683 */         getLecturaBalanza().setUnidadManejo(unidadManejo);
/* 747:    */       }
/* 748:    */     }
/* 749:    */   }
/* 750:    */   
/* 751:    */   public void setListaMaquina(List<Maquina> listaMaquina)
/* 752:    */   {
/* 753:689 */     this.listaMaquina = listaMaquina;
/* 754:    */   }
/* 755:    */   
/* 756:    */   public Boolean getIndicadorTodosMaterial()
/* 757:    */   {
/* 758:693 */     return Boolean.valueOf(this.indicadorTodosMaterial);
/* 759:    */   }
/* 760:    */   
/* 761:    */   public void setIndicadorTodosMaterial(Boolean indicadorTodosMaterial)
/* 762:    */   {
/* 763:697 */     this.indicadorTodosMaterial = indicadorTodosMaterial.booleanValue();
/* 764:    */   }
/* 765:    */   
/* 766:    */   public DetalleMovimientoInventario getDetalleMovimientoInventarioSeleccionado()
/* 767:    */   {
/* 768:701 */     return this.detalleMovimientoInventarioSeleccionado;
/* 769:    */   }
/* 770:    */   
/* 771:    */   public void setDetalleMovimientoInventarioSeleccionado(DetalleMovimientoInventario detalleMovimientoInventarioSeleccionado)
/* 772:    */   {
/* 773:705 */     this.detalleMovimientoInventarioSeleccionado = detalleMovimientoInventarioSeleccionado;
/* 774:    */   }
/* 775:    */   
/* 776:    */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 777:    */   {
/* 778:709 */     if (this.organizacionConfiguracion == null) {
/* 779:710 */       this.organizacionConfiguracion = this.servicioOrganizacion.cargarDetalle(AppUtil.getOrganizacion().getId()).getOrganizacionConfiguracion();
/* 780:    */     }
/* 781:712 */     return this.organizacionConfiguracion;
/* 782:    */   }
/* 783:    */   
/* 784:    */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 785:    */   {
/* 786:716 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 787:    */   }
/* 788:    */   
/* 789:    */   public boolean isIndicadorMaquinaObligatoria()
/* 790:    */   {
/* 791:720 */     return this.indicadorMaquinaObligatoria;
/* 792:    */   }
/* 793:    */   
/* 794:    */   public String getBusquedaPorOrdenFabricacion()
/* 795:    */   {
/* 796:724 */     if (this.busquedaPorOrdenFabricacion == null) {
/* 797:725 */       this.busquedaPorOrdenFabricacion = "ORDEN";
/* 798:    */     }
/* 799:727 */     return this.busquedaPorOrdenFabricacion;
/* 800:    */   }
/* 801:    */   
/* 802:    */   public void setBusquedaPorOrdenFabricacion(String busquedaPorOrdenFabricacion)
/* 803:    */   {
/* 804:731 */     this.busquedaPorOrdenFabricacion = busquedaPorOrdenFabricacion;
/* 805:    */   }
/* 806:    */   
/* 807:    */   public boolean isPermiteEliminacionIngresoProduccion()
/* 808:    */   {
/* 809:735 */     return ParametrosSistema.isPermiteEliminacionIngresoProduccion(AppUtil.getOrganizacion().getId()).booleanValue();
/* 810:    */   }
/* 811:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.IngresoFabricacionBean
 * JD-Core Version:    0.7.0.1
 */