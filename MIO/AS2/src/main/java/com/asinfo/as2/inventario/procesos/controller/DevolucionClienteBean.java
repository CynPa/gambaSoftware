/*    1:     */ package com.asinfo.as2.inventario.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    8:     */ import com.asinfo.as2.controller.LanguageController;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   12:     */ import com.asinfo.as2.entities.Bodega;
/*   13:     */ import com.asinfo.as2.entities.Cliente;
/*   14:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   15:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   16:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   17:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   18:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   19:     */ import com.asinfo.as2.entities.Documento;
/*   20:     */ import com.asinfo.as2.entities.Empresa;
/*   21:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   22:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   23:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   24:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   25:     */ import com.asinfo.as2.entities.Lote;
/*   26:     */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*   27:     */ import com.asinfo.as2.entities.Organizacion;
/*   28:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   29:     */ import com.asinfo.as2.entities.Producto;
/*   30:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   31:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   32:     */ import com.asinfo.as2.entities.Secuencia;
/*   33:     */ import com.asinfo.as2.entities.Subempresa;
/*   34:     */ import com.asinfo.as2.entities.Sucursal;
/*   35:     */ import com.asinfo.as2.entities.Ubicacion;
/*   36:     */ import com.asinfo.as2.entities.Zona;
/*   37:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   38:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   39:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   40:     */ import com.asinfo.as2.enumeraciones.ExportOption;
/*   41:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   42:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   43:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   44:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*   45:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   46:     */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*   47:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   48:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   49:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   50:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   51:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   52:     */ import com.asinfo.as2.inventario.reportes.controller.ReporteDevolucionClienteBean;
/*   53:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   54:     */ import com.asinfo.as2.util.AppUtil;
/*   55:     */ import com.asinfo.as2.util.RutaArchivo;
/*   56:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   57:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   58:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   59:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*   60:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   61:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*   62:     */ import java.io.File;
/*   63:     */ import java.io.FileNotFoundException;
/*   64:     */ import java.math.BigDecimal;
/*   65:     */ import java.util.ArrayList;
/*   66:     */ import java.util.Collection;
/*   67:     */ import java.util.Date;
/*   68:     */ import java.util.HashMap;
/*   69:     */ import java.util.List;
/*   70:     */ import java.util.Map;
/*   71:     */ import javax.annotation.PostConstruct;
/*   72:     */ import javax.ejb.EJB;
/*   73:     */ import javax.faces.bean.ManagedBean;
/*   74:     */ import javax.faces.bean.ManagedProperty;
/*   75:     */ import javax.faces.bean.ViewScoped;
/*   76:     */ import javax.faces.component.html.HtmlSelectOneMenu;
/*   77:     */ import javax.faces.context.FacesContext;
/*   78:     */ import javax.faces.context.PartialViewContext;
/*   79:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   80:     */ import org.apache.log4j.Logger;
/*   81:     */ import org.primefaces.component.datatable.DataTable;
/*   82:     */ import org.primefaces.context.RequestContext;
/*   83:     */ import org.primefaces.event.FileUploadEvent;
/*   84:     */ import org.primefaces.event.SelectEvent;
/*   85:     */ import org.primefaces.model.LazyDataModel;
/*   86:     */ import org.primefaces.model.SortOrder;
/*   87:     */ import org.primefaces.model.StreamedContent;
/*   88:     */ 
/*   89:     */ @ManagedBean
/*   90:     */ @ViewScoped
/*   91:     */ public class DevolucionClienteBean
/*   92:     */   extends MovimientoInventarioBaseBean
/*   93:     */ {
/*   94:     */   private static final long serialVersionUID = 8239938626246344319L;
/*   95:     */   @EJB
/*   96:     */   private transient ServicioEmpresa servicioEmpresa;
/*   97:     */   @EJB
/*   98:     */   private transient ServicioDocumento servicioDocumento;
/*   99:     */   @EJB
/*  100:     */   private transient ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  101:     */   @EJB
/*  102:     */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  103:     */   @EJB
/*  104:     */   private transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  105:     */   @EJB
/*  106:     */   private transient ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  107:     */   @EJB
/*  108:     */   private transient ServicioLote servicioLote;
/*  109:     */   @EJB
/*  110:     */   private transient ServicioOrganizacion servicioOrganizacion;
/*  111:     */   @EJB
/*  112:     */   private transient ServicioProducto servicioProducto;
/*  113:     */   @EJB
/*  114:     */   private ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  115:     */   @EJB
/*  116:     */   protected transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  117:     */   @EJB
/*  118:     */   protected transient ServicioSucursal servicioSucursal;
/*  119:     */   @EJB
/*  120:     */   protected transient ServicioListaPrecios servicioListaPrecios;
/*  121:     */   @EJB
/*  122:     */   protected transient ServicioDespachoCliente servicioDespachoCliente;
/*  123:     */   private FacturaCliente devolucionCliente;
/*  124:     */   private LazyDataModel<FacturaCliente> listaDevolucionCliente;
/*  125:     */   private List<Documento> listaDocumento;
/*  126: 136 */   private List<DireccionEmpresa> listaDireccionEmpresa = new ArrayList();
/*  127:     */   private List<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente;
/*  128:     */   private List<Bodega> listaBodega;
/*  129:     */   private List<Subempresa> listaSubempresa;
/*  130:     */   private DetalleFacturaCliente detalleFacturaClienteSeleccionado;
/*  131:     */   private DetalleFacturaCliente detalleFacturaClienteOriginal;
/*  132:     */   private String mails;
/*  133:     */   private String numeroDevolucion;
/*  134:     */   private Lote loteCrear;
/*  135:     */   private boolean indicadorSeleccionarTodos;
/*  136:     */   private DataTable dtDevolucionCliente;
/*  137:     */   private DataTable dtDetalleDevolucionCliente;
/*  138:     */   private DataTable dtImpuestoDevolucion;
/*  139: 155 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  140:     */   @ManagedProperty("#{reporteDevolucionClienteBean}")
/*  141:     */   private ReporteDevolucionClienteBean reporteDevolucionClienteBean;
/*  142:     */   private Boolean agregarDetalle;
/*  143:     */   private List<DetalleFacturaCliente> listaDetalleFC;
/*  144:     */   private List<DetalleFacturaCliente> listaDetalleFCFilter;
/*  145:     */   
/*  146:     */   @PostConstruct
/*  147:     */   public void init()
/*  148:     */   {
/*  149: 169 */     getListaProductoBean().setIndicadorVenta(true);
/*  150: 170 */     getListaProductoBean().setActivo(true);
/*  151:     */     try
/*  152:     */     {
/*  153: 173 */       this.listaDevolucionCliente = new LazyDataModel()
/*  154:     */       {
/*  155:     */         private static final long serialVersionUID = 4780083578367601484L;
/*  156:     */         
/*  157:     */         public List<FacturaCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  158:     */         {
/*  159: 180 */           if (DevolucionClienteBean.this.numeroDevolucion != null)
/*  160:     */           {
/*  161: 182 */             if (DevolucionClienteBean.this.numeroDevolucion.length() > 17) {
/*  162: 183 */               DevolucionClienteBean.this.numeroDevolucion = DevolucionClienteBean.this.numeroDevolucion.substring(0, 17);
/*  163:     */             }
/*  164: 185 */             filters.put("numero", DevolucionClienteBean.this.numeroDevolucion);
/*  165:     */           }
/*  166: 188 */           List<FacturaCliente> lista = new ArrayList();
/*  167:     */           
/*  168: 190 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  169:     */           
/*  170: 192 */           filters.put("documento.documentoBase", DocumentoBase.DEVOLUCION_CLIENTE.toString());
/*  171: 193 */           lista = DevolucionClienteBean.this.servicioNotaCreditoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  172:     */           
/*  173: 195 */           DevolucionClienteBean.this.listaDevolucionCliente.setRowCount(DevolucionClienteBean.this.servicioNotaCreditoCliente.contarPorCriterio(filters));
/*  174: 196 */           return lista;
/*  175:     */         }
/*  176:     */       };
/*  177:     */     }
/*  178:     */     catch (Exception e)
/*  179:     */     {
/*  180: 202 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  181: 203 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  182:     */     }
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void cargarProducto()
/*  186:     */   {
/*  187: 208 */     Producto producto = getListaProductoBean().getProducto();
/*  188: 209 */     if (producto != null) {
/*  189: 210 */       if (this.devolucionCliente.getEmpresa() != null)
/*  190:     */       {
/*  191: 211 */         DetalleFacturaCliente detalleFacturaCliente = new DetalleFacturaCliente();
/*  192: 212 */         this.devolucionCliente.getListaDetalleFacturaCliente().add(detalleFacturaCliente);
/*  193: 213 */         if (getListaProductoBean().getSaldoProductoLote() != null) {
/*  194: 214 */           producto.setLote(getListaProductoBean().getSaldoProductoLote().getLote());
/*  195:     */         }
/*  196: 216 */         actualizarProducto(detalleFacturaCliente, producto);
/*  197: 217 */         detalleFacturaCliente.setCantidad(producto.getTraCantidad());
/*  198:     */         
/*  199: 219 */         Integer idZona = null;
/*  200: 220 */         if ((isIndicadorListaPrecioPorZona()) && 
/*  201: 221 */           (this.devolucionCliente.getEmpresa().getCliente().getZona() != null)) {
/*  202: 222 */           idZona = Integer.valueOf(this.devolucionCliente.getEmpresa().getCliente().getZona().getId());
/*  203:     */         }
/*  204: 226 */         if (this.devolucionCliente.getEmpresa().getCliente().getListaPrecios() != null) {
/*  205:     */           try
/*  206:     */           {
/*  207: 229 */             DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(this.servicioEmpresa.cargarDetalleTodo(this.devolucionCliente.getEmpresa())
/*  208: 230 */               .getCliente().getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), this.devolucionCliente.getFecha(), idZona, "");
/*  209:     */             
/*  210:     */ 
/*  211: 233 */             detalleFacturaCliente.setPrecio(dvlp.getPrecioUnitario());
/*  212:     */           }
/*  213:     */           catch (ExcepcionAS2 e)
/*  214:     */           {
/*  215: 235 */             addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  216:     */           }
/*  217:     */         } else {
/*  218: 238 */           addInfoMessage(getLanguageController().getMensaje("msg_error_empresa_lista_precios"));
/*  219:     */         }
/*  220:     */       }
/*  221:     */       else
/*  222:     */       {
/*  223: 241 */         addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  224:     */       }
/*  225:     */     }
/*  226: 246 */     totalizar();
/*  227: 247 */     getListaProductoBean().setProducto(null);
/*  228:     */   }
/*  229:     */   
/*  230:     */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/*  231:     */   {
/*  232: 256 */     DetalleFacturaCliente dfc = (DetalleFacturaCliente)this.dtDetalleDevolucionCliente.getRowData();
/*  233: 258 */     if (dfc.getProducto() != null)
/*  234:     */     {
/*  235: 259 */       String codigo = dfc.getProducto().getCodigo();
/*  236: 260 */       Producto producto = null;
/*  237:     */       try
/*  238:     */       {
/*  239: 263 */         producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.DEVOLUCION_CLIENTE);
/*  240: 265 */         if (this.devolucionCliente.getEmpresaFinal() != null)
/*  241:     */         {
/*  242: 266 */           actualizarProducto(dfc, producto);
/*  243:     */           
/*  244: 268 */           Integer idZona = null;
/*  245: 269 */           if ((isIndicadorListaPrecioPorZona()) && 
/*  246: 270 */             (this.devolucionCliente.getEmpresaFinal().getCliente().getZona() != null)) {
/*  247: 271 */             idZona = Integer.valueOf(this.devolucionCliente.getEmpresaFinal().getCliente().getZona().getId());
/*  248:     */           }
/*  249: 275 */           if (this.devolucionCliente.getEmpresaFinal().getCliente().getListaPrecios() != null)
/*  250:     */           {
/*  251: 276 */             DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(this.devolucionCliente.getEmpresaFinal()
/*  252: 277 */               .getCliente().getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), this.devolucionCliente.getFecha(), idZona, "");
/*  253:     */             
/*  254: 279 */             dfc.setPrecio(dvlp.getPrecioUnitario());
/*  255:     */           }
/*  256:     */           else
/*  257:     */           {
/*  258: 281 */             addInfoMessage(getLanguageController().getMensaje("msg_error_empresa_lista_precios"));
/*  259:     */           }
/*  260:     */         }
/*  261:     */         else
/*  262:     */         {
/*  263: 284 */           addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  264:     */         }
/*  265:     */       }
/*  266:     */       catch (ExcepcionAS2 e)
/*  267:     */       {
/*  268: 288 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  269: 289 */         dfc.getProducto().setCodigo("");
/*  270: 290 */         dfc.getProducto().setNombre("");
/*  271:     */       }
/*  272: 293 */       totalizar();
/*  273:     */     }
/*  274:     */   }
/*  275:     */   
/*  276:     */   private void actualizarProducto(DetalleFacturaCliente detalleFacturaCliente, Producto producto)
/*  277:     */   {
/*  278: 299 */     for (ImpuestoProductoFacturaCliente ipfc : detalleFacturaCliente.getListaImpuestoProductoFacturaCliente()) {
/*  279: 300 */       ipfc.setEliminado(true);
/*  280:     */     }
/*  281: 302 */     detalleFacturaCliente.setProducto(producto);
/*  282: 303 */     detalleFacturaCliente.setUnidadVenta(producto.getUnidadVenta());
/*  283: 304 */     detalleFacturaCliente.setFacturaCliente(this.devolucionCliente);
/*  284: 305 */     detalleFacturaCliente.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/*  285: 306 */     detalleFacturaCliente.setIce(producto.getIce());
/*  286: 307 */     detalleFacturaCliente.setCodigoIce(producto.getCodigoIce());
/*  287: 309 */     if (this.devolucionCliente.getEmpresa().getCliente().isExcentoImpuestos()) {
/*  288: 310 */       detalleFacturaCliente.setIndicadorImpuesto(false);
/*  289:     */     } else {
/*  290: 312 */       detalleFacturaCliente.setIndicadorImpuesto(producto.isIndicadorImpuestos());
/*  291:     */     }
/*  292: 315 */     if (detalleFacturaCliente.isIndicadorImpuesto()) {
/*  293:     */       try
/*  294:     */       {
/*  295: 317 */         this.servicioFacturaCliente.obtenerImpuestosProductos(detalleFacturaCliente.getProducto(), detalleFacturaCliente);
/*  296:     */       }
/*  297:     */       catch (ExcepcionAS2Inventario e)
/*  298:     */       {
/*  299: 320 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  300:     */       }
/*  301:     */     }
/*  302: 324 */     if (isFacturaClienteSaldoInicial()) {
/*  303: 325 */       detalleFacturaCliente.setDetalleFacturaClientePadre(this.detalleFacturaClienteOriginal.getDetalleFacturaClientePadre());
/*  304:     */     }
/*  305: 328 */     InventarioProducto inventarioProducto = new InventarioProducto();
/*  306: 329 */     detalleFacturaCliente.setInventarioProducto(inventarioProducto);
/*  307: 330 */     inventarioProducto.setProducto(producto);
/*  308: 331 */     inventarioProducto.setLote(producto.getLote());
/*  309: 332 */     inventarioProducto.setDetalleDevolucionCliente(detalleFacturaCliente);
/*  310: 333 */     cargarBodega(detalleFacturaCliente);
/*  311: 334 */     totalizar();
/*  312:     */   }
/*  313:     */   
/*  314:     */   public String editar()
/*  315:     */   {
/*  316: 345 */     if (!this.devolucionCliente.getEstado().equals(Estado.RECHAZADO_SRI)) {
/*  317: 346 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  318:     */     } else {
/*  319:     */       try
/*  320:     */       {
/*  321: 349 */         this.devolucionCliente = this.servicioFacturaCliente.cargarDetalle(this.devolucionCliente.getId());
/*  322: 350 */         actualizarDocumento();
/*  323: 351 */         cargarDirecciones();
/*  324:     */         
/*  325: 353 */         setEditado(true);
/*  326:     */       }
/*  327:     */       catch (Exception e)
/*  328:     */       {
/*  329: 355 */         e.printStackTrace();
/*  330: 356 */         addErrorMessage(e.getMessage());
/*  331:     */       }
/*  332:     */     }
/*  333: 359 */     return "";
/*  334:     */   }
/*  335:     */   
/*  336:     */   public String guardar()
/*  337:     */   {
/*  338: 369 */     boolean guardo = false;
/*  339:     */     try
/*  340:     */     {
/*  341: 377 */       this.servicioNotaCreditoCliente.guardar(this.devolucionCliente);
/*  342:     */       
/*  343: 379 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  344: 381 */       if (this.devolucionCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() != null)
/*  345:     */       {
/*  346: 382 */         this.reporteDevolucionClienteBean.setIndicadorImpreso(false);
/*  347: 383 */         this.reporteDevolucionClienteBean.setDevolucionCliente(this.devolucionCliente);
/*  348: 384 */         this.reporteDevolucionClienteBean.setExportOption(ExportOption.PRINTER);
/*  349: 385 */         this.reporteDevolucionClienteBean.setNumeroImpresiones(AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/*  350: 386 */           .getNumeroCopiasDocumentoTributario());
/*  351: 387 */         this.reporteDevolucionClienteBean.execute();
/*  352:     */       }
/*  353: 389 */       guardo = true;
/*  354:     */     }
/*  355:     */     catch (ExcepcionAS2Financiero e)
/*  356:     */     {
/*  357: 391 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  358: 392 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  359:     */     }
/*  360:     */     catch (ExcepcionAS2Ventas e)
/*  361:     */     {
/*  362: 394 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  363: 395 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  364:     */     }
/*  365:     */     catch (AS2Exception e)
/*  366:     */     {
/*  367: 397 */       this.exContabilizacion = e;
/*  368: 398 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  369: 399 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  370:     */     }
/*  371:     */     catch (ExcepcionAS2 e)
/*  372:     */     {
/*  373: 401 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  374: 402 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  375:     */     }
/*  376:     */     catch (Exception e)
/*  377:     */     {
/*  378: 404 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  379: 405 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  380:     */     }
/*  381: 407 */     if (guardo) {
/*  382:     */       try
/*  383:     */       {
/*  384: 411 */         this.servicioNotaCreditoCliente.costearTransformacionDevolucionCliente(this.devolucionCliente);
/*  385:     */         
/*  386: 413 */         this.servicioNotaCreditoCliente.contabilizarTransformacionDevolucionCliente(this.devolucionCliente);
/*  387:     */       }
/*  388:     */       catch (ExcepcionAS2Financiero e)
/*  389:     */       {
/*  390: 415 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  391: 416 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/*  392:     */       }
/*  393:     */       catch (ExcepcionAS2Ventas e)
/*  394:     */       {
/*  395: 418 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  396: 419 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/*  397:     */       }
/*  398:     */       catch (AS2Exception e)
/*  399:     */       {
/*  400: 421 */         this.exContabilizacion = e;
/*  401: 422 */         FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  402: 423 */         RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  403:     */       }
/*  404:     */       catch (ExcepcionAS2 e)
/*  405:     */       {
/*  406: 425 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  407: 426 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/*  408:     */       }
/*  409:     */       catch (Exception e)
/*  410:     */       {
/*  411: 428 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  412: 429 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/*  413:     */       }
/*  414:     */       finally
/*  415:     */       {
/*  416: 432 */         limpiar();
/*  417:     */       }
/*  418:     */     }
/*  419: 436 */     return "";
/*  420:     */   }
/*  421:     */   
/*  422:     */   public String eliminar()
/*  423:     */   {
/*  424: 446 */     if (this.devolucionCliente.getId() > 0) {
/*  425:     */       try
/*  426:     */       {
/*  427: 449 */         this.servicioNotaCreditoCliente.anular(this.devolucionCliente);
/*  428: 450 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  429:     */       }
/*  430:     */       catch (ExcepcionAS2Inventario e)
/*  431:     */       {
/*  432: 453 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  433: 454 */         LOG.info("ERROR AL ELIMINAR RECEPCION PROVEEDOR:", e);
/*  434:     */       }
/*  435:     */       catch (ExcepcionAS2Ventas e)
/*  436:     */       {
/*  437: 457 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  438: 458 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE(DEVOLUCION) ExcepcionAS2Ventas");
/*  439: 459 */         e.printStackTrace();
/*  440:     */       }
/*  441:     */       catch (ExcepcionAS2Financiero e)
/*  442:     */       {
/*  443: 461 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  444: 462 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE(DEVOLUCION)ExcepcionAS2Financiero");
/*  445: 463 */         e.printStackTrace();
/*  446:     */       }
/*  447:     */       catch (ExcepcionAS2 e)
/*  448:     */       {
/*  449: 465 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  450: 466 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE(DEVOLUCION) Exception");
/*  451:     */       }
/*  452:     */       catch (Exception e)
/*  453:     */       {
/*  454: 468 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  455:     */         
/*  456: 470 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE(DEVOLUCION) Exception");
/*  457:     */       }
/*  458:     */     } else {
/*  459: 474 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  460:     */     }
/*  461: 477 */     return "";
/*  462:     */   }
/*  463:     */   
/*  464:     */   public String limpiar()
/*  465:     */   {
/*  466: 487 */     setEditado(false);
/*  467: 488 */     crearDevolucion();
/*  468: 489 */     this.detalleFacturaClienteOriginal = null;
/*  469: 490 */     resetListas();
/*  470: 491 */     return "";
/*  471:     */   }
/*  472:     */   
/*  473:     */   public String crear()
/*  474:     */   {
/*  475: 502 */     if (AppUtil.getPuntoDeVenta().isIndicadorPos())
/*  476:     */     {
/*  477: 503 */       addInfoMessage(getLanguageController().getMensaje("msg_error_facturar_pos"));
/*  478: 504 */       setEditado(false);
/*  479: 505 */       return "";
/*  480:     */     }
/*  481: 507 */     limpiar();
/*  482: 508 */     setEditado(true);
/*  483: 509 */     return "";
/*  484:     */   }
/*  485:     */   
/*  486:     */   private void crearDevolucion()
/*  487:     */   {
/*  488: 513 */     this.devolucionCliente = new FacturaCliente();
/*  489: 514 */     this.devolucionCliente.setNumero("");
/*  490: 515 */     this.devolucionCliente.setFecha(new Date());
/*  491: 516 */     this.devolucionCliente.setEstado(Estado.ELABORADO);
/*  492: 517 */     this.devolucionCliente.setNumeroCuotas(1);
/*  493: 518 */     this.devolucionCliente.setMotivoNotaCreditoCliente(new MotivoNotaCreditoCliente());
/*  494:     */     
/*  495: 520 */     Documento documento = null;
/*  496: 521 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/*  497:     */     {
/*  498: 522 */       documento = (Documento)getListaDocumento().get(0);
/*  499: 523 */       this.devolucionCliente.setDocumento(documento);
/*  500: 524 */       actualizarDocumento();
/*  501:     */     }
/*  502:     */     else
/*  503:     */     {
/*  504: 526 */       documento = new Documento();
/*  505: 527 */       documento.setSecuencia(new Secuencia());
/*  506: 528 */       this.devolucionCliente.setDocumento(documento);
/*  507:     */     }
/*  508: 531 */     this.devolucionCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  509: 532 */     this.devolucionCliente.setSucursal(AppUtil.getSucursal());
/*  510: 533 */     setIndicadorSeleccionarTodos(false);
/*  511: 535 */     if (this.devolucionCliente.getDocumento().isIndicadorDocumentoElectronico())
/*  512:     */     {
/*  513: 536 */       if (this.devolucionCliente.getFacturaClienteSRI() == null) {
/*  514: 537 */         this.devolucionCliente.setFacturaClienteSRI(new FacturaClienteSRI());
/*  515:     */       }
/*  516: 540 */       int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(AppUtil.getOrganizacion().getId()).booleanValue() ? 2 : 1;
/*  517: 541 */       this.devolucionCliente.getFacturaClienteSRI().setAmbiente(ambiente);
/*  518: 542 */       this.devolucionCliente.getFacturaClienteSRI().setDireccionMatriz(AppUtil.getDireccionMatriz());
/*  519: 543 */       this.devolucionCliente.getFacturaClienteSRI().setDireccionSucursal(AppUtil.getSucursal().getUbicacion().getDireccionCompleta());
/*  520: 544 */       this.devolucionCliente.getFacturaClienteSRI().setFacturaCliente(this.devolucionCliente);
/*  521: 545 */       this.devolucionCliente.getFacturaClienteSRI().setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  522: 546 */       this.devolucionCliente.getFacturaClienteSRI().setIdSucursal(AppUtil.getSucursal().getId());
/*  523:     */     }
/*  524:     */   }
/*  525:     */   
/*  526:     */   public String cargarDatos()
/*  527:     */   {
/*  528: 558 */     return "";
/*  529:     */   }
/*  530:     */   
/*  531:     */   public String agregarDetalle()
/*  532:     */   {
/*  533: 562 */     if (this.devolucionCliente.getEmpresaFinal() != null)
/*  534:     */     {
/*  535: 563 */       DetalleFacturaCliente d = new DetalleFacturaCliente();
/*  536: 564 */       d.setFacturaCliente(getDevolucionCliente());
/*  537: 565 */       d.setProducto(new Producto());
/*  538: 566 */       d.setCantidad(BigDecimal.ZERO);
/*  539: 567 */       d.setPrecio(BigDecimal.ZERO);
/*  540: 568 */       d.setDescuento(BigDecimal.ZERO);
/*  541: 569 */       getDevolucionCliente().getListaDetalleFacturaCliente().add(d);
/*  542:     */     }
/*  543:     */     else
/*  544:     */     {
/*  545: 571 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  546:     */     }
/*  547: 574 */     return "";
/*  548:     */   }
/*  549:     */   
/*  550:     */   public String actualizarDocumento()
/*  551:     */   {
/*  552: 585 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.devolucionCliente.getDocumento().getId()));
/*  553: 586 */     this.devolucionCliente.setDocumento(documento);
/*  554:     */     try
/*  555:     */     {
/*  556: 590 */       if (isIndicadorAutoimpresor()) {
/*  557: 591 */         this.devolucionCliente.setAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(this.servicioAutorizacionAutoimpresorSRI
/*  558: 592 */           .obtenerAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(DocumentoBase.NOTA_CREDITO_CLIENTE, AppUtil.getPuntoDeVenta()));
/*  559:     */       }
/*  560: 595 */       if (this.devolucionCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() == null) {
/*  561: 596 */         if (this.devolucionCliente.getDocumento().isIndicadorDocumentoTributario())
/*  562:     */         {
/*  563: 597 */           PuntoDeVenta puntoDeVenta = this.servicioFacturaCliente.cargarPuntoVenta(this.devolucionCliente);
/*  564: 598 */           this.servicioNotaCreditoCliente.cargarSecuencia(this.devolucionCliente, puntoDeVenta);
/*  565:     */         }
/*  566:     */         else
/*  567:     */         {
/*  568: 600 */           this.servicioNotaCreditoCliente.cargarSecuencia(this.devolucionCliente, null);
/*  569:     */         }
/*  570:     */       }
/*  571:     */     }
/*  572:     */     catch (ExcepcionAS2 e)
/*  573:     */     {
/*  574: 605 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  575:     */     }
/*  576: 608 */     return "";
/*  577:     */   }
/*  578:     */   
/*  579:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  580:     */   {
/*  581: 612 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/*  582:     */   }
/*  583:     */   
/*  584:     */   public void seleccionarUltimaFactura()
/*  585:     */   {
/*  586: 617 */     resetListas();
/*  587: 618 */     FacturaCliente facturaCliente = this.servicioFacturaCliente.obtenerUltimaFacturaAutorizadaPorCliente(AppUtil.getOrganizacion().getId(), this.devolucionCliente
/*  588: 619 */       .getEmpresa(), true, null);
/*  589: 620 */     if (facturaCliente != null) {
/*  590: 621 */       actualizarDetalles(facturaCliente);
/*  591:     */     }
/*  592:     */   }
/*  593:     */   
/*  594:     */   public List<FacturaCliente> autocompletarFacturas(String consulta)
/*  595:     */   {
/*  596: 626 */     Map<String, String> filters = new HashMap();
/*  597: 627 */     List<FacturaCliente> lista = new ArrayList();
/*  598: 629 */     if (getDevolucionCliente().getEmpresa() != null)
/*  599:     */     {
/*  600: 630 */       filters.put("empresa.idEmpresa", "" + getDevolucionCliente().getEmpresa().getId());
/*  601: 632 */       if (getDevolucionCliente().getSubempresa() != null) {
/*  602: 633 */         filters.put("subempresa.idSubempresa", "" + getDevolucionCliente().getSubempresa().getId());
/*  603:     */       }
/*  604: 635 */       if ((consulta != null) && (!consulta.isEmpty())) {
/*  605: 636 */         filters.put("numero", "%" + consulta);
/*  606:     */       }
/*  607: 638 */       filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/*  608: 639 */       filters.put("despachoCliente", "!=" + null);
/*  609: 640 */       filters.put("despachoCliente.idDespachoCliente", ">0");
/*  610: 642 */       if (!ParametrosSistema.getRealizaNotasCreditoAFacturaNoAutorizada(AppUtil.getOrganizacion().getId()).booleanValue()) {
/*  611: 643 */         filters.put("facturaClienteSRI.autorizacion", "!=0000000000");
/*  612:     */       }
/*  613: 645 */       lista = this.servicioFacturaCliente.obtenerListaCombo("fecha", true, filters);
/*  614:     */     }
/*  615:     */     else
/*  616:     */     {
/*  617: 647 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  618:     */     }
/*  619: 650 */     return lista;
/*  620:     */   }
/*  621:     */   
/*  622:     */   public List<DespachoCliente> autocompletarDespachos(String consulta)
/*  623:     */   {
/*  624: 655 */     List<DespachoCliente> lista = new ArrayList();
/*  625: 656 */     if (getDevolucionCliente().getEmpresa() != null) {
/*  626: 657 */       lista = this.servicioDespachoCliente.autocompletarDespachosNoFacturadosPorCliente(Integer.valueOf(getDevolucionCliente().getEmpresa().getId()), consulta, 
/*  627: 658 */         getDevolucionCliente().getSubempresa() != null ? getDevolucionCliente().getSubempresa().getId() : 0);
/*  628:     */     } else {
/*  629: 660 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  630:     */     }
/*  631: 662 */     return lista;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void totalizar()
/*  635:     */   {
/*  636:     */     try
/*  637:     */     {
/*  638: 666 */       this.servicioNotaCreditoCliente.totalizar(this.devolucionCliente);
/*  639:     */     }
/*  640:     */     catch (ExcepcionAS2Ventas e)
/*  641:     */     {
/*  642: 668 */       LOG.error(e.getErrorMessage(e));
/*  643:     */     }
/*  644:     */     catch (Exception e)
/*  645:     */     {
/*  646: 670 */       LOG.error(e);
/*  647: 671 */       e.printStackTrace();
/*  648:     */     }
/*  649:     */   }
/*  650:     */   
/*  651:     */   public String eliminarDetalle()
/*  652:     */   {
/*  653: 682 */     resetListas();
/*  654: 683 */     DetalleFacturaCliente d = (DetalleFacturaCliente)this.dtDetalleDevolucionCliente.getRowData();
/*  655: 685 */     for (ImpuestoProductoFacturaCliente ipfc : d.getListaImpuestoProductoFacturaCliente()) {
/*  656: 686 */       ipfc.setEliminado(true);
/*  657:     */     }
/*  658: 689 */     d.setEliminado(true);
/*  659: 690 */     totalizar();
/*  660:     */     
/*  661: 692 */     return "";
/*  662:     */   }
/*  663:     */   
/*  664:     */   public String creacionLote()
/*  665:     */     throws ExcepcionAS2, AS2Exception
/*  666:     */   {
/*  667: 696 */     this.detalleFacturaClienteSeleccionado = ((DetalleFacturaCliente)this.dtDetalleDevolucionCliente.getRowData());
/*  668:     */     
/*  669:     */ 
/*  670:     */ 
/*  671:     */ 
/*  672:     */ 
/*  673:     */ 
/*  674:     */ 
/*  675: 704 */     this.loteCrear = this.servicioLote.crearLote(AppUtil.getOrganizacion().getIdOrganizacion(), this.detalleFacturaClienteSeleccionado.getProducto(), "", false, null, null, null, false);
/*  676:     */     
/*  677: 706 */     this.loteCrear.setCodigo(null);
/*  678: 707 */     this.loteCrear.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  679: 708 */     this.loteCrear.setFechaFabricacion(null);
/*  680: 711 */     if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA))
/*  681:     */     {
/*  682: 712 */       Integer numeroSerie = this.servicioOrganizacion.actualizarSecuenciaInicioSerie(AppUtil.getOrganizacion());
/*  683:     */       
/*  684: 714 */       this.loteCrear.setCodigo(String.valueOf(FuncionesUtiles.completarALaIzquierda('0', 10, numeroSerie.toString())));
/*  685: 715 */       this.loteCrear.setIndicadorMovimientoInterno(true);
/*  686: 716 */       this.loteCrear.setFechaCaducidad(new Date());
/*  687: 717 */       this.loteCrear.setFechaFabricacion(new Date());
/*  688:     */     }
/*  689: 719 */     return "";
/*  690:     */   }
/*  691:     */   
/*  692:     */   public String guardarLote()
/*  693:     */   {
/*  694:     */     try
/*  695:     */     {
/*  696: 724 */       this.loteCrear.setCodigo(this.loteCrear.getProducto().getPrefijoLote() + this.loteCrear.getCodigo());
/*  697: 725 */       this.servicioLote.guardar(this.loteCrear);
/*  698: 726 */       this.loteCrear.getProducto().setPrefijoLote(null);
/*  699: 727 */       this.detalleFacturaClienteSeleccionado.getInventarioProducto().setLote(this.loteCrear);
/*  700: 728 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  701:     */     }
/*  702:     */     catch (Exception e)
/*  703:     */     {
/*  704: 730 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  705: 731 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  706:     */     }
/*  707:     */     finally
/*  708:     */     {
/*  709: 733 */       this.loteCrear = null;
/*  710:     */     }
/*  711: 735 */     return "";
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void actualizarCliente(SelectEvent event)
/*  715:     */   {
/*  716: 744 */     Empresa empresa = (Empresa)event.getObject();
/*  717: 745 */     this.devolucionCliente.setEmpresa(empresa);
/*  718: 746 */     this.devolucionCliente.setFacturaClientePadre(null);
/*  719: 747 */     cargarDirecciones();
/*  720: 748 */     cargarSubempresas();
/*  721:     */     
/*  722: 750 */     this.devolucionCliente.setEmail(this.servicioEmpresa.cargarMails(empresa, DocumentoBase.DEVOLUCION_CLIENTE));
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void cargarSubempresas()
/*  726:     */   {
/*  727: 755 */     if (this.devolucionCliente.getEmpresa() != null) {
/*  728: 756 */       this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.devolucionCliente.getEmpresa().getId());
/*  729:     */     }
/*  730:     */   }
/*  731:     */   
/*  732:     */   public void actualizarFactura(SelectEvent event)
/*  733:     */   {
/*  734: 767 */     this.detalleFacturaClienteOriginal = null;
/*  735: 768 */     FacturaCliente facturaClientePadre = (FacturaCliente)event.getObject();
/*  736: 769 */     actualizarDetalles(facturaClientePadre);
/*  737: 770 */     cargarDirecciones();
/*  738: 771 */     this.devolucionCliente.setEmail(this.servicioEmpresa.cargarMails(facturaClientePadre.getEmpresa(), DocumentoBase.DEVOLUCION_CLIENTE));
/*  739: 772 */     this.devolucionCliente.setSubempresa(facturaClientePadre.getSubempresa());
/*  740:     */   }
/*  741:     */   
/*  742:     */   public void resetListas()
/*  743:     */   {
/*  744: 776 */     this.listaDetalleFC = null;
/*  745: 777 */     this.listaDetalleFCFilter = null;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public void actualizarDetalles(FacturaCliente facturaClientePadre)
/*  749:     */   {
/*  750: 782 */     resetListas();
/*  751: 784 */     if ((facturaClientePadre.getDespachoCliente() != null) || (facturaClientePadre.isIndicadorSaldoInicial()))
/*  752:     */     {
/*  753: 785 */       this.devolucionCliente.setFacturaClientePadre(facturaClientePadre);
/*  754: 786 */       this.servicioNotaCreditoCliente.actualizarDetalleDevolucion(this.devolucionCliente.getFacturaClientePadre().getId(), this.devolucionCliente);
/*  755: 787 */       this.devolucionCliente.setSubempresa(facturaClientePadre.getSubempresa());
/*  756: 788 */       if ((isFacturaClienteSaldoInicial()) && (this.devolucionCliente.getListaDetalleFacturaCliente().size() > 0))
/*  757:     */       {
/*  758: 789 */         this.detalleFacturaClienteOriginal = ((DetalleFacturaCliente)this.devolucionCliente.getListaDetalleFacturaCliente().get(0));
/*  759: 790 */         cargarBodega(this.detalleFacturaClienteOriginal);
/*  760:     */       }
/*  761:     */     }
/*  762:     */     else
/*  763:     */     {
/*  764: 794 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  765:     */     }
/*  766:     */   }
/*  767:     */   
/*  768:     */   public void actualizarFacturaDesdeDespacho(SelectEvent event)
/*  769:     */   {
/*  770: 799 */     this.detalleFacturaClienteOriginal = null;
/*  771: 800 */     DespachoCliente despachoCliente = (DespachoCliente)event.getObject();
/*  772: 801 */     actualizarDetallesDesdeDespacho(despachoCliente);
/*  773:     */   }
/*  774:     */   
/*  775:     */   public void actualizarDetallesDesdeDespacho(DespachoCliente despachoCliente)
/*  776:     */   {
/*  777: 806 */     resetListas();
/*  778: 807 */     this.devolucionCliente.setDespachoCliente(despachoCliente);
/*  779: 808 */     this.servicioNotaCreditoCliente.actualizarDetalleDevolucionDesdeDespacho(this.devolucionCliente.getDespachoCliente().getId(), this.devolucionCliente);
/*  780:     */   }
/*  781:     */   
/*  782:     */   private void cargarBodega(DetalleFacturaCliente detalleFacturaCliente)
/*  783:     */   {
/*  784: 813 */     if (AppUtil.getBodega() != null) {
/*  785: 814 */       detalleFacturaCliente.setBodega(AppUtil.getBodega());
/*  786:     */     } else {
/*  787: 816 */       detalleFacturaCliente.setBodega(detalleFacturaCliente.getProducto().getBodegaVenta() == null ? new Bodega() : detalleFacturaCliente
/*  788: 817 */         .getProducto().getBodegaVenta());
/*  789:     */     }
/*  790:     */   }
/*  791:     */   
/*  792:     */   public void cargarDirecciones()
/*  793:     */   {
/*  794: 827 */     if (this.devolucionCliente.getSubempresa() != null) {
/*  795: 828 */       setListaDireccionEmpresa(this.servicioEmpresa.obtenerListaComboDirecciones(this.devolucionCliente.getSubempresa().getEmpresa().getId()));
/*  796: 829 */     } else if (this.devolucionCliente.getFacturaClientePadre() != null) {
/*  797: 830 */       setListaDireccionEmpresa(this.servicioEmpresa.obtenerListaComboDirecciones(this.devolucionCliente.getFacturaClientePadre().getEmpresa().getId()));
/*  798:     */     } else {
/*  799: 832 */       setListaDireccionEmpresa(this.servicioEmpresa.obtenerListaComboDirecciones(this.devolucionCliente.getEmpresa().getId()));
/*  800:     */     }
/*  801: 834 */     for (DireccionEmpresa direccion : getListaDireccionEmpresa()) {
/*  802: 835 */       if (direccion.isIndicadorDireccionPrincipal()) {
/*  803: 836 */         this.devolucionCliente.setDireccionEmpresa(direccion);
/*  804:     */       } else {
/*  805: 838 */         this.devolucionCliente.setDireccionEmpresa((DireccionEmpresa)this.listaDireccionEmpresa.get(0));
/*  806:     */       }
/*  807:     */     }
/*  808:     */   }
/*  809:     */   
/*  810:     */   public List<DetalleFacturaCliente> getDetalleFacturaCliente()
/*  811:     */   {
/*  812: 850 */     List<DetalleFacturaCliente> detalle = new ArrayList();
/*  813: 851 */     for (DetalleFacturaCliente dfc : this.devolucionCliente.getListaDetalleFacturaCliente()) {
/*  814: 852 */       if (!dfc.isEliminado()) {
/*  815: 853 */         detalle.add(dfc);
/*  816:     */       }
/*  817:     */     }
/*  818: 856 */     return detalle;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public void actualizarSubclienteListener(AjaxBehaviorEvent event)
/*  822:     */   {
/*  823: 861 */     Subempresa subempresa = (Subempresa)((HtmlSelectOneMenu)event.getSource()).getValue();
/*  824: 863 */     if (subempresa != null)
/*  825:     */     {
/*  826: 864 */       this.devolucionCliente.setSubempresa(subempresa);
/*  827: 865 */       this.devolucionCliente.setDireccionEmpresa(null);
/*  828: 866 */       this.devolucionCliente.setEmail(this.servicioEmpresa.cargarMails(subempresa.getEmpresa(), DocumentoBase.DEVOLUCION_CLIENTE));
/*  829: 867 */       cargarDirecciones();
/*  830:     */     }
/*  831:     */   }
/*  832:     */   
/*  833:     */   public List<Lote> autocompletarLotes(String consulta)
/*  834:     */   {
/*  835: 880 */     DetalleFacturaCliente detalleDevolucion = (DetalleFacturaCliente)this.dtDetalleDevolucionCliente.getRowData();
/*  836: 881 */     List<Lote> lista = this.servicioLote.autocompletarLote(detalleDevolucion.getProducto(), consulta);
/*  837: 882 */     return lista;
/*  838:     */   }
/*  839:     */   
/*  840:     */   public List<ImpuestoProductoFacturaCliente> getListaImpuestoProductoNC()
/*  841:     */   {
/*  842: 892 */     List<ImpuestoProductoFacturaCliente> listaImpuestoProductoFacturaClientes = new ArrayList();
/*  843: 894 */     for (DetalleFacturaCliente dfc : this.devolucionCliente.getListaDetalleFacturaCliente()) {
/*  844: 896 */       for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente()) {
/*  845: 897 */         if (!ipfc.isEliminado()) {
/*  846: 898 */           listaImpuestoProductoFacturaClientes.add(ipfc);
/*  847:     */         }
/*  848:     */       }
/*  849:     */     }
/*  850: 904 */     return listaImpuestoProductoFacturaClientes;
/*  851:     */   }
/*  852:     */   
/*  853:     */   public String cargarCantidadADevolver(DetalleFacturaCliente d)
/*  854:     */   {
/*  855: 908 */     if (d.getDetalleFacturaClientePadre() != null)
/*  856:     */     {
/*  857: 909 */       d.setCantidad(d.getDetalleFacturaClientePadre().getDetalleDespachoCliente().getCantidadPorDevolver());
/*  858: 910 */       d.setPeso(d.getDetalleFacturaClientePadre().getDetalleDespachoCliente().getPeso());
/*  859:     */     }
/*  860:     */     else
/*  861:     */     {
/*  862: 912 */       d.setCantidad(d.getDetalleDespachoClienteNoFacturado().getCantidadPorDevolver());
/*  863: 913 */       d.setPeso(d.getDetalleDespachoClienteNoFacturado().getPeso());
/*  864:     */     }
/*  865: 915 */     totalizar();
/*  866: 916 */     return "";
/*  867:     */   }
/*  868:     */   
/*  869:     */   public String limpiarCantidadADevolver(DetalleFacturaCliente d)
/*  870:     */   {
/*  871: 920 */     d.setCantidad(BigDecimal.ZERO);
/*  872: 921 */     d.setPeso(BigDecimal.ZERO);
/*  873: 922 */     totalizar();
/*  874: 923 */     return "";
/*  875:     */   }
/*  876:     */   
/*  877:     */   public String seleccionarTodos()
/*  878:     */   {
/*  879: 927 */     for (DetalleFacturaCliente detalleFacturaCliente : getDevolucionCliente().getListaDetalleFacturaCliente()) {
/*  880: 928 */       cargarCantidadADevolver(detalleFacturaCliente);
/*  881:     */     }
/*  882: 930 */     return "";
/*  883:     */   }
/*  884:     */   
/*  885:     */   public String limpiarTodos()
/*  886:     */   {
/*  887: 934 */     for (DetalleFacturaCliente detalleFacturaCliente : getDevolucionCliente().getListaDetalleFacturaCliente()) {
/*  888: 935 */       limpiarCantidadADevolver(detalleFacturaCliente);
/*  889:     */     }
/*  890: 937 */     return "";
/*  891:     */   }
/*  892:     */   
/*  893:     */   public boolean isGuardarAjax()
/*  894:     */   {
/*  895: 942 */     return !isIndicadorAutoimpresor();
/*  896:     */   }
/*  897:     */   
/*  898:     */   public String getDirectorioDescarga()
/*  899:     */   {
/*  900: 947 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "devolucion_cliente");
/*  901:     */   }
/*  902:     */   
/*  903:     */   public String getNombreArchivo()
/*  904:     */   {
/*  905: 952 */     return this.devolucionCliente.getArchivo();
/*  906:     */   }
/*  907:     */   
/*  908:     */   public void processUpload(FileUploadEvent event)
/*  909:     */   {
/*  910:     */     try
/*  911:     */     {
/*  912: 965 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "devolucion_cliente");
/*  913:     */       
/*  914: 967 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), this.devolucionCliente.getNumero(), uploadDir);
/*  915:     */       
/*  916: 969 */       HashMap<String, Object> campos = new HashMap();
/*  917: 970 */       campos.put("archivo", fileName);
/*  918: 971 */       this.servicioNotaCreditoCliente.actualizarAtributoEntidad(this.devolucionCliente, campos);
/*  919:     */       
/*  920: 973 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  921:     */     }
/*  922:     */     catch (Exception e)
/*  923:     */     {
/*  924: 976 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  925: 977 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  926:     */     }
/*  927:     */   }
/*  928:     */   
/*  929:     */   public String eliminarArchivo()
/*  930:     */   {
/*  931: 983 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), this.devolucionCliente.getArchivo());
/*  932: 984 */     this.devolucionCliente.setArchivo(null);
/*  933: 985 */     HashMap<String, Object> campos = new HashMap();
/*  934: 986 */     campos.put("archivo", null);
/*  935: 987 */     this.servicioNotaCreditoCliente.actualizarAtributoEntidad(this.devolucionCliente, campos);
/*  936: 988 */     return null;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public FacturaCliente getDevolucionCliente()
/*  940:     */   {
/*  941: 997 */     return this.devolucionCliente;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void setDevolucionCliente(FacturaCliente devolucionCliente)
/*  945:     */   {
/*  946:1007 */     this.devolucionCliente = devolucionCliente;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public LazyDataModel<FacturaCliente> getListaDevolucionCliente()
/*  950:     */   {
/*  951:1016 */     return this.listaDevolucionCliente;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public void setListaDevolucionCliente(LazyDataModel<FacturaCliente> listaDevolucionCliente)
/*  955:     */   {
/*  956:1026 */     this.listaDevolucionCliente = listaDevolucionCliente;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public List<Documento> getListaDocumento()
/*  960:     */   {
/*  961:1035 */     if (this.listaDocumento == null) {
/*  962:     */       try
/*  963:     */       {
/*  964:1037 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.DEVOLUCION_CLIENTE);
/*  965:     */       }
/*  966:     */       catch (ExcepcionAS2 e)
/*  967:     */       {
/*  968:1039 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  969:     */       }
/*  970:     */     }
/*  971:1043 */     return this.listaDocumento;
/*  972:     */   }
/*  973:     */   
/*  974:     */   public void setListaDocumento(List<Documento> listaDocumento)
/*  975:     */   {
/*  976:1053 */     this.listaDocumento = listaDocumento;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public DataTable getdtDevolucionCliente()
/*  980:     */   {
/*  981:1062 */     return this.dtDevolucionCliente;
/*  982:     */   }
/*  983:     */   
/*  984:     */   public void setdtDevolucionCliente(DataTable dtDevolucionCliente)
/*  985:     */   {
/*  986:1072 */     this.dtDevolucionCliente = dtDevolucionCliente;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public DataTable getDtDetalleDevolucionCliente()
/*  990:     */   {
/*  991:1081 */     return this.dtDetalleDevolucionCliente;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public void setDtDetalleDevolucionCliente(DataTable dtDetalleDevolucionCliente)
/*  995:     */   {
/*  996:1091 */     this.dtDetalleDevolucionCliente = dtDetalleDevolucionCliente;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public DataTable getdtImpuestoDevolucion()
/* 1000:     */   {
/* 1001:1100 */     return this.dtImpuestoDevolucion;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public void setdtImpuestoDevolucion(DataTable dtImpuestoDevolucion)
/* 1005:     */   {
/* 1006:1110 */     this.dtImpuestoDevolucion = dtImpuestoDevolucion;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 1010:     */   {
/* 1011:1119 */     return this.listaDireccionEmpresa;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 1015:     */   {
/* 1016:1129 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public String getNumeroDevolucion()
/* 1020:     */   {
/* 1021:1138 */     return this.numeroDevolucion;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public void setNumeroDevolucion(String numeroDevolucion)
/* 1025:     */   {
/* 1026:1148 */     this.numeroDevolucion = numeroDevolucion;
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public List<MotivoNotaCreditoCliente> getListaMotivoNotaCreditoCliente()
/* 1030:     */   {
/* 1031:1157 */     if (this.listaMotivoNotaCreditoCliente == null) {
/* 1032:1158 */       this.listaMotivoNotaCreditoCliente = this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("nombre", true, null);
/* 1033:     */     }
/* 1034:1160 */     return this.listaMotivoNotaCreditoCliente;
/* 1035:     */   }
/* 1036:     */   
/* 1037:     */   public void setListaMotivoNotaCreditoCliente(List<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente)
/* 1038:     */   {
/* 1039:1170 */     this.listaMotivoNotaCreditoCliente = listaMotivoNotaCreditoCliente;
/* 1040:     */   }
/* 1041:     */   
/* 1042:     */   public List<Bodega> getListaBodega()
/* 1043:     */   {
/* 1044:1179 */     if (this.listaBodega == null) {
/* 1045:1180 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 1046:     */     }
/* 1047:1182 */     return this.listaBodega;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public void setListaBodega(List<Bodega> listaBodega)
/* 1051:     */   {
/* 1052:1192 */     this.listaBodega = listaBodega;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public List<Subempresa> getListaSubempresa()
/* 1056:     */   {
/* 1057:1201 */     if (this.listaSubempresa == null) {
/* 1058:1202 */       this.listaSubempresa = new ArrayList();
/* 1059:     */     }
/* 1060:1204 */     return this.listaSubempresa;
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 1064:     */   {
/* 1065:1214 */     this.listaSubempresa = listaSubempresa;
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public Lote getLoteCrear()
/* 1069:     */   {
/* 1070:1223 */     return this.loteCrear;
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public void setLoteCrear(Lote loteCrear)
/* 1074:     */   {
/* 1075:1233 */     this.loteCrear = loteCrear;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public boolean isFacturaClienteSaldoInicial()
/* 1079:     */   {
/* 1080:1237 */     return (this.devolucionCliente.getFacturaClientePadre() != null) && (this.devolucionCliente.getFacturaClientePadre().isIndicadorSaldoInicial());
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   public boolean isIndicadorSeleccionarTodos()
/* 1084:     */   {
/* 1085:1246 */     return this.indicadorSeleccionarTodos;
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   public void setIndicadorSeleccionarTodos(boolean indicadorSeleccionarTodos)
/* 1089:     */   {
/* 1090:1256 */     this.indicadorSeleccionarTodos = indicadorSeleccionarTodos;
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public AS2Exception getExContabilizacion()
/* 1094:     */   {
/* 1095:1265 */     return this.exContabilizacion;
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 1099:     */   {
/* 1100:1275 */     this.exContabilizacion = exContabilizacion;
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public ReporteDevolucionClienteBean getReporteDevolucionClienteBean()
/* 1104:     */   {
/* 1105:1279 */     return this.reporteDevolucionClienteBean;
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public void setReporteDevolucionClienteBean(ReporteDevolucionClienteBean reporteDevolucionClienteBean)
/* 1109:     */   {
/* 1110:1283 */     this.reporteDevolucionClienteBean = reporteDevolucionClienteBean;
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public String getMails()
/* 1114:     */   {
/* 1115:1287 */     return this.mails;
/* 1116:     */   }
/* 1117:     */   
/* 1118:     */   public void setMails(String mails)
/* 1119:     */   {
/* 1120:1291 */     this.mails = mails;
/* 1121:     */   }
/* 1122:     */   
/* 1123:     */   public void enviarMail()
/* 1124:     */   {
/* 1125:     */     try
/* 1126:     */     {
/* 1127:1296 */       if (((this.devolucionCliente.getDocumento() != null) && (!this.devolucionCliente.getDocumento().isIndicadorDocumentoElectronico())) || 
/* 1128:1297 */         (this.devolucionCliente.getEstado().equals(Estado.ANULADO)) || (this.devolucionCliente.getEstado().equals(Estado.EN_ESPERA)) || 
/* 1129:1298 */         (this.devolucionCliente.getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/* 1130:1299 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1131:     */       } else {
/* 1132:1301 */         this.servicioFacturaCliente.enviarMail(this.devolucionCliente, this.mails);
/* 1133:     */       }
/* 1134:     */     }
/* 1135:     */     catch (ExcepcionAS2 e)
/* 1136:     */     {
/* 1137:1304 */       addErrorMessage(this.devolucionCliente.getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/* 1138:     */     }
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public boolean isAgregarDetalle()
/* 1142:     */   {
/* 1143:1309 */     if (this.agregarDetalle == null) {
/* 1144:1310 */       this.agregarDetalle = ParametrosSistema.getAgregarDetalleDevolucionCliente(AppUtil.getOrganizacion().getId());
/* 1145:     */     }
/* 1146:1312 */     return this.agregarDetalle.booleanValue();
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public void setAgregarDetalle(boolean agregarDetalle)
/* 1150:     */   {
/* 1151:1316 */     this.agregarDetalle = Boolean.valueOf(agregarDetalle);
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public List<Producto> autocompletarProductos(String consulta)
/* 1155:     */   {
/* 1156:1320 */     Map<String, String> filtros = new HashMap();
/* 1157:1321 */     filtros.put("indicadorVenta", String.valueOf(true));
/* 1158:1322 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta, filtros);
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   public StreamedContent getXMLSRI()
/* 1162:     */   {
/* 1163:1326 */     if ((this.devolucionCliente != null) && (this.devolucionCliente.getId() != 0) && (this.devolucionCliente.getFacturaClienteSRI() != null))
/* 1164:     */     {
/* 1165:1327 */       String pathSRI = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri";
/* 1166:     */       
/* 1167:     */ 
/* 1168:1330 */       String pathDocumento = pathSRI + File.separator + "documentos_electronicos" + File.separator + TipoDocumentoElectronicoEnum.NOTA_CREDITO.toString();
/* 1169:1331 */       String pathArchivoAutorizado = pathDocumento + File.separator + "autorizado";
/* 1170:     */       
/* 1171:1333 */       String nombreArchivo = this.devolucionCliente.getNumero() + "-" + this.devolucionCliente.getFacturaClienteSRI().getClaveAcceso() + ".xml";
/* 1172:1334 */       pathArchivoAutorizado = pathArchivoAutorizado + File.separator + nombreArchivo;
/* 1173:     */       try
/* 1174:     */       {
/* 1175:1336 */         return FuncionesUtiles.descargarArchivo(pathArchivoAutorizado, "application/xls", nombreArchivo);
/* 1176:     */       }
/* 1177:     */       catch (FileNotFoundException e)
/* 1178:     */       {
/* 1179:1338 */         e.printStackTrace();
/* 1180:1339 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1181:1340 */         return null;
/* 1182:     */       }
/* 1183:     */     }
/* 1184:1343 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1185:1344 */     return null;
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   public List<DetalleFacturaCliente> getListaDetalleFC()
/* 1189:     */   {
/* 1190:1352 */     if (this.listaDetalleFC == null)
/* 1191:     */     {
/* 1192:1353 */       this.listaDetalleFC = new ArrayList();
/* 1193:1354 */       for (DetalleFacturaCliente detallePago : getDevolucionCliente().getListaDetalleFacturaCliente()) {
/* 1194:1355 */         if (!detallePago.isEliminado()) {
/* 1195:1356 */           this.listaDetalleFC.add(detallePago);
/* 1196:     */         }
/* 1197:     */       }
/* 1198:     */     }
/* 1199:1360 */     return this.listaDetalleFC;
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public void setListaDetalleFC(List<DetalleFacturaCliente> listaDetalleFC)
/* 1203:     */   {
/* 1204:1367 */     this.listaDetalleFC = listaDetalleFC;
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public List<DetalleFacturaCliente> getListaDetalleFCFilter()
/* 1208:     */   {
/* 1209:1374 */     return this.listaDetalleFCFilter;
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public void setListaDetalleFCFilter(List<DetalleFacturaCliente> listaDetalleFCFilter)
/* 1213:     */   {
/* 1214:1381 */     this.listaDetalleFCFilter = listaDetalleFCFilter;
/* 1215:     */   }
/* 1216:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.DevolucionClienteBean
 * JD-Core Version:    0.7.0.1
 */