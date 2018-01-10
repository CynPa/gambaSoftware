/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Canal;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.DimensionContable;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  15:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.Zona;
/*  18:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  19:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoVentaEnum;
/*  21:    */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*  26:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  27:    */ import com.asinfo.as2.util.AppUtil;
/*  28:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  29:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  30:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  31:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  32:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  33:    */ import java.io.IOException;
/*  34:    */ import java.util.ArrayList;
/*  35:    */ import java.util.Calendar;
/*  36:    */ import java.util.Date;
/*  37:    */ import java.util.HashMap;
/*  38:    */ import java.util.List;
/*  39:    */ import java.util.Map;
/*  40:    */ import javax.annotation.PostConstruct;
/*  41:    */ import javax.ejb.EJB;
/*  42:    */ import javax.faces.bean.ManagedBean;
/*  43:    */ import javax.faces.bean.ViewScoped;
/*  44:    */ import javax.faces.model.SelectItem;
/*  45:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  46:    */ import net.sf.jasperreports.engine.JRException;
/*  47:    */ import org.apache.log4j.Logger;
/*  48:    */ 
/*  49:    */ @ManagedBean
/*  50:    */ @ViewScoped
/*  51:    */ public class ReporteFacturacionResumidoClienteBean
/*  52:    */   extends AbstractInventarioReportBean
/*  53:    */ {
/*  54:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  55:    */   @EJB
/*  56:    */   private ServicioReporteVenta servicioReporteVenta;
/*  57:    */   @EJB
/*  58:    */   private ServicioEmpresa servicioEmpresa;
/*  59:    */   @EJB
/*  60:    */   private ServicioUsuario servicioUsuario;
/*  61:    */   @EJB
/*  62:    */   private ServicioCanal servicioCanal;
/*  63:    */   @EJB
/*  64:    */   private ServicioZona servicioZona;
/*  65:    */   @EJB
/*  66:    */   private ServicioSucursal servicioSucursal;
/*  67:    */   @EJB
/*  68:    */   private ServicioPuntoDeVenta ServicioPuntoDeVenta;
/*  69:    */   @EJB
/*  70:    */   private ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  71:    */   @EJB
/*  72:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  73:    */   @EJB
/*  74:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  75:    */   @EJB
/*  76:    */   private ServicioAtributo servicioAtributo;
/*  77:    */   @EJB
/*  78:    */   private ServicioValorAtributo servicioValorAtributo;
/*  79:    */   private Empresa empresa;
/*  80:    */   private int idVendedor;
/*  81:    */   private Zona zona;
/*  82:    */   private Canal canal;
/*  83:    */   private Sucursal sucursal;
/*  84:    */   private PuntoDeVenta puntoVenta;
/*  85:    */   private boolean anuladas;
/*  86:    */   private CategoriaProducto categoriaProducto;
/*  87:    */   private Producto producto;
/*  88:    */   private SubcategoriaProducto subcategoriaProducto;
/*  89:    */   private Date fechaDesde;
/*  90:    */   private Date fechaHasta;
/*  91:    */   private String numeroFacturaDesde;
/*  92:    */   private String numeroFacturaHasta;
/*  93:120 */   private DocumentoBase documentoBase = DocumentoBase.FACTURA_CLIENTE;
/*  94:121 */   private MotivoNotaCreditoCliente motivoNotaCreditoCliente = new MotivoNotaCreditoCliente();
/*  95:    */   private List<Empresa> listaClienteCombo;
/*  96:    */   private List<Zona> listaZonaCombo;
/*  97:    */   private List<Canal> listaCanalCombo;
/*  98:    */   private List<EntidadUsuario> listaVendedorCombo;
/*  99:    */   private List<Sucursal> listaSucursalCombo;
/* 100:128 */   private List<PuntoDeVenta> listaPuntoVenta = new ArrayList();
/* 101:    */   private List<SelectItem> listaTipoVenta;
/* 102:    */   private List<DocumentoBase> listaDocumentoBase;
/* 103:    */   private List<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente;
/* 104:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/* 105:    */   private List<CategoriaProducto> listaCategoriaProductos;
/* 106:135 */   private boolean indicadorResumen = true;
/* 107:    */   private boolean indicadorAgrupado;
/* 108:    */   private boolean saldoInicial;
/* 109:139 */   private TipoVentaEnum tipoVenta = TipoVentaEnum.LOCAL;
/* 110:    */   private DimensionContable proyecto;
/* 111:    */   private boolean indicadorTipoReporte;
/* 112:    */   
/* 113:    */   private static enum enumTipoReporte
/* 114:    */   {
/* 115:143 */     PRODUCTO,  FACTURA;
/* 116:    */     
/* 117:    */     private enumTipoReporte() {}
/* 118:    */   }
/* 119:    */   
/* 120:149 */   private enumTipoReporte tipoReporte = enumTipoReporte.FACTURA;
/* 121:    */   private List<SelectItem> listaTipoReporte;
/* 122:    */   
/* 123:    */   protected JRDataSource getJRDataSource()
/* 124:    */   {
/* 125:161 */     List listaDatosReporte = new ArrayList();
/* 126:162 */     JRDataSource ds = null;
/* 127:163 */     asignarValorAtributo(getValorAtributoSeleccionado());
/* 128:165 */     if ((this.tipoReporte.equals(enumTipoReporte.PRODUCTO)) && (!this.indicadorResumen))
/* 129:    */     {
/* 130:167 */       listaDatosReporte = this.servicioReporteVenta.getListaReporteVentaProductoDetallado(this.fechaDesde, this.fechaHasta, this.numeroFacturaDesde, this.numeroFacturaHasta, this.empresa
/* 131:168 */         .getId(), this.idVendedor, this.anuladas, getCanal().getId(), getZona().getId(), this.sucursal, this.puntoVenta, this.tipoVenta, this.saldoInicial, 
/* 132:169 */         AppUtil.getOrganizacion().getId(), this.documentoBase, getMotivoNotaCreditoCliente().getId(), this.categoriaProducto, this.subcategoriaProducto, this.producto, 
/* 133:170 */         getAtributo(), getValorAtributo(), this.proyecto);
/* 134:    */       
/* 135:172 */       String[] fields = { "f_codigoSucursal", "f_nombreSucursal", "f_codigoCanal", "f_nombreCanal", "f_codigoZona", "f_nombreZona", "f_codigoVendedor", "f_nombreVendedor", "f_numeroFactura", "f_fechaFactura", "f_nombreCliente", "f_identificacionCliente", "f_totalFactura", "f_descuentoFactura", "f_impuestoFactura", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad", "f_precio", "f_descripcion", "f_descuento", "f_anio", "f_mes", "f_numeroFacturaPadre", "f_nombreDocumento", "f_operacion", "f_precioLinea", "f_descuentoLinea", "f_cantidad", "f_peso", "f_unidad", "f_motivoNotaCredito", "f_proyecto", "f_descripcion_factura", "f_autorizacion" };
/* 136:    */       
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:179 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 143:    */     }
/* 144:182 */     else if (this.indicadorResumen)
/* 145:    */     {
/* 146:184 */       if (this.tipoReporte.equals(enumTipoReporte.PRODUCTO))
/* 147:    */       {
/* 148:185 */         if (this.indicadorAgrupado)
/* 149:    */         {
/* 150:186 */           listaDatosReporte = this.servicioReporteVenta.getListaReporteFacturacionProductoResumido(this.fechaDesde, this.fechaHasta, this.numeroFacturaDesde, this.numeroFacturaHasta, this.empresa
/* 151:187 */             .getIdEmpresa(), this.idVendedor, this.anuladas, getCanal().getId(), getZona()
/* 152:188 */             .getId(), this.sucursal, this.puntoVenta, this.tipoVenta, this.saldoInicial, AppUtil.getOrganizacion().getIdOrganizacion(), this.documentoBase, this.categoriaProducto, this.subcategoriaProducto, this.producto, 
/* 153:189 */             getAtributo(), getValorAtributo(), this.proyecto);
/* 154:    */           
/* 155:191 */           String[] fields = { "f_codigoProducto", "f_nombreProducto", "f_codigoUnidad", "f_nombreUnidad", "f_cantidad", "f_peso", "f_proyecto", "f_descripcion_factura", "f_autorizacion" };
/* 156:    */           
/* 157:193 */           ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 158:    */         }
/* 159:    */         else
/* 160:    */         {
/* 161:195 */           listaDatosReporte = this.servicioReporteVenta.getListaReporteFacturacionDetallado(this.fechaDesde, this.fechaHasta, this.numeroFacturaDesde, this.numeroFacturaHasta, this.empresa
/* 162:196 */             .getId(), this.idVendedor, this.anuladas, getCanal().getId(), getZona().getId(), this.sucursal, this.puntoVenta, this.tipoVenta, this.saldoInicial, 
/* 163:197 */             isIndicadorTipoReporte(), AppUtil.getOrganizacion().getId(), this.documentoBase, 
/* 164:198 */             getMotivoNotaCreditoCliente().getId(), this.categoriaProducto, this.subcategoriaProducto, this.producto, getAtributo(), 
/* 165:199 */             getValorAtributo(), this.proyecto);
/* 166:    */           
/* 167:201 */           String[] fields = { "f_codigoSucursal", "f_nombreSucursal", "f_codigoCanal", "f_nombreCanal", "f_codigoZona", "f_nombreZona", "f_codigoVendedor", "f_nombreVendedor", "f_numeroFactura", "f_fechaFactura", "f_nombreCliente", "f_identificacionCliente", "f_totalFactura", "f_descuentoFactura", "f_impuestoFactura", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad", "f_precio", "f_descripcion", "f_descuento", "f_numeroFacturaPadre", "f_codigoDocumento", "f_nombreDocumento", "f_operacion", "f_impuesto", "f_peso", "f_precioLinea", "f_descuentoLinea", "f_unidad", "f_motivoNotaCredito", "f_proyecto", "f_descripcion_factura", "f_autorizacion" };
/* 168:    */           
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:209 */           ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 176:    */         }
/* 177:    */       }
/* 178:    */       else
/* 179:    */       {
/* 180:214 */         listaDatosReporte = this.servicioReporteVenta.getListaReporteFacturacionResumido(this.fechaDesde, this.fechaHasta, this.numeroFacturaDesde, this.numeroFacturaHasta, this.empresa
/* 181:215 */           .getId(), this.idVendedor, this.anuladas, getCanal().getId(), getZona().getId(), this.sucursal, this.puntoVenta, this.tipoVenta, this.saldoInicial, 
/* 182:216 */           isIndicadorTipoReporte(), AppUtil.getOrganizacion().getId(), this.documentoBase, 
/* 183:217 */           getMotivoNotaCreditoCliente().getId(), this.proyecto);
/* 184:    */         
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:228 */         String[] fields = { "f_codigoSucursal", "f_nombreSucursal", "f_codigoCanal", "f_nombreCanal", "f_codigoZona", "f_nombreZona", "f_codigoVendedor", "f_nombreVendedor", "f_numeroFactura", "f_fechaFactura", "f_nombreCliente", "f_identificacionCliente", "f_tipoEstructuraPrecio", "f_valorEstructuraPrecio", "f_totalFactura", "f_descuentoFactura", "f_impuestoFactura", "f_idFactura", "f_nombreComercial", "f_numeroFacturaPadre", "f_autorizacion", "f_fechaCreacion", "f_estado", "f_usuario_modificacion", "f_fecha_modificacion", "f_motivo_anulacion", "f_identificacion", "f_usuarioCreacion", "f_codigoDocumento", "f_motivoNotaCredito", "f_nombreDocumento", "f_operacion", "f_cantidad", "f_peso", "f_unidad", "f_motivoNotaCredito", "f_proyecto", "f_descripcion_factura", "f_autorizacion", "f_montoIva", "f_subEmpresaNombreFiscal", "f_subEmpresaNombreComercial", "f_clave_acceso" };
/* 195:    */         
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:236 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 203:    */       }
/* 204:    */     }
/* 205:    */     else
/* 206:    */     {
/* 207:242 */       listaDatosReporte = this.servicioReporteVenta.getListaReporteFacturacionDetallado(this.fechaDesde, this.fechaHasta, this.numeroFacturaDesde, this.numeroFacturaHasta, this.empresa
/* 208:243 */         .getId(), this.idVendedor, this.anuladas, getCanal().getId(), getZona().getId(), this.sucursal, this.puntoVenta, this.tipoVenta, this.saldoInicial, 
/* 209:244 */         isIndicadorTipoReporte(), AppUtil.getOrganizacion().getId(), this.documentoBase, 
/* 210:245 */         getMotivoNotaCreditoCliente().getId(), this.categoriaProducto, this.subcategoriaProducto, this.producto, getAtributo(), getValorAtributo(), this.proyecto);
/* 211:    */       
/* 212:    */ 
/* 213:248 */       String[] fields = { "f_codigoSucursal", "f_nombreSucursal", "f_codigoCanal", "f_nombreCanal", "f_codigoZona", "f_nombreZona", "f_codigoVendedor", "f_nombreVendedor", "f_numeroFactura", "f_fechaFactura", "f_nombreCliente", "f_identificacionCliente", "f_totalFactura", "f_descuentoFactura", "f_impuestoFactura", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad", "f_precio", "f_descripcion", "f_descuento", "f_numeroFacturaPadre", "f_codigoDocumento", "f_nombreDocumento", "f_operacion", "f_impuesto", "f_peso", "f_precioLinea", "f_descuentoLinea", "f_unidad", "f_motivoNotaCredito", "f_proyecto", "f_descripcion_factura", "f_autorizacion", "f_subEmpresaNombreFiscal", "f_subEmpresaNombreComercial", "f_guiaRemision", "f_numeroPedido", "f_clave_acceso" };
/* 214:    */       
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:255 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 221:    */     }
/* 222:260 */     return ds;
/* 223:    */   }
/* 224:    */   
/* 225:    */   @PostConstruct
/* 226:    */   public void init()
/* 227:    */   {
/* 228:265 */     Calendar calfechaDesde = Calendar.getInstance();
/* 229:266 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 230:267 */     this.fechaDesde = calfechaDesde.getTime();
/* 231:268 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 232:    */   }
/* 233:    */   
/* 234:    */   protected String getCompileFileName()
/* 235:    */   {
/* 236:278 */     if (this.tipoReporte.equals(enumTipoReporte.FACTURA))
/* 237:    */     {
/* 238:279 */       if (this.indicadorResumen) {
/* 239:280 */         return "reporteFacturacionResumido";
/* 240:    */       }
/* 241:282 */       return "reporteFacturacionDetallado";
/* 242:    */     }
/* 243:285 */     if (this.indicadorResumen)
/* 244:    */     {
/* 245:286 */       if (this.indicadorAgrupado) {
/* 246:287 */         return "reporteFacturacionProductoResumido";
/* 247:    */       }
/* 248:289 */       return "reporteVentaProductoResumido";
/* 249:    */     }
/* 250:292 */     return "reporteVentaProductoDetallado";
/* 251:    */   }
/* 252:    */   
/* 253:    */   protected Map<String, Object> getReportParameters()
/* 254:    */   {
/* 255:304 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 256:305 */     String tituloReporte = "";
/* 257:306 */     if (this.indicadorResumen) {
/* 258:307 */       tituloReporte = tituloReporte + " Resumen";
/* 259:    */     } else {
/* 260:309 */       tituloReporte = tituloReporte + " Detalle";
/* 261:    */     }
/* 262:311 */     tituloReporte = tituloReporte + (this.documentoBase != null ? this.documentoBase.getNombre() : "Todos                  ");
/* 263:    */     
/* 264:313 */     reportParameters.put("ReportTitle", tituloReporte);
/* 265:314 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 266:315 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 267:316 */     reportParameters.put("NumeroDesde", this.numeroFacturaDesde);
/* 268:317 */     reportParameters.put("NumeroHasta", this.numeroFacturaHasta);
/* 269:318 */     reportParameters.put("Total", "Total");
/* 270:319 */     reportParameters.put("p_motivoNotaCredito", getMotivoNotaCreditoCliente().getNombre());
/* 271:320 */     reportParameters.put("p_sucursal", this.sucursal != null ? getSucursal().getNombre() : "Todos");
/* 272:321 */     reportParameters.put("p_canal", getCanal().getId() > 0 ? getCanal().getNombre() : "Todos");
/* 273:322 */     reportParameters.put("p_zona", getZona().getId() > 0 ? getZona().getNombre() : "Todos");
/* 274:323 */     return reportParameters;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void cargarListaSubcategoriaProducto()
/* 278:    */   {
/* 279:327 */     HashMap<String, String> filters = new HashMap();
/* 280:328 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 281:329 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public String execute()
/* 285:    */   {
/* 286:    */     try
/* 287:    */     {
/* 288:339 */       validaDatos();
/* 289:340 */       super.prepareReport();
/* 290:    */     }
/* 291:    */     catch (JRException e)
/* 292:    */     {
/* 293:342 */       LOG.info("Error JRException");
/* 294:343 */       e.printStackTrace();
/* 295:344 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 296:    */     }
/* 297:    */     catch (IOException e)
/* 298:    */     {
/* 299:346 */       LOG.info("Error IOException");
/* 300:347 */       e.printStackTrace();
/* 301:348 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 302:    */     }
/* 303:351 */     return null;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void cargarProducto() {}
/* 307:    */   
/* 308:    */   public void validaDatos()
/* 309:    */   {
/* 310:360 */     if (this.empresa == null)
/* 311:    */     {
/* 312:361 */       this.empresa = new Empresa();
/* 313:362 */       this.empresa.setId(-1);
/* 314:    */     }
/* 315:364 */     if (this.fechaDesde == null) {
/* 316:365 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 317:    */     }
/* 318:367 */     if (this.fechaHasta == null) {
/* 319:368 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void cargarPuntoVenta()
/* 324:    */   {
/* 325:373 */     if (this.sucursal != null)
/* 326:    */     {
/* 327:374 */       Map<String, String> filters = new HashMap();
/* 328:375 */       filters.put("sucursal.idSucursal", String.valueOf(this.sucursal.getId()));
/* 329:376 */       this.listaPuntoVenta = this.ServicioPuntoDeVenta.obtenerListaCombo("nombre", true, filters);
/* 330:    */     }
/* 331:    */     else
/* 332:    */     {
/* 333:378 */       this.listaPuntoVenta = new ArrayList();
/* 334:    */     }
/* 335:    */   }
/* 336:    */   
/* 337:    */   public Date getFechaDesde()
/* 338:    */   {
/* 339:388 */     return this.fechaDesde;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setFechaDesde(Date fechaDesde)
/* 343:    */   {
/* 344:398 */     this.fechaDesde = fechaDesde;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public Date getFechaHasta()
/* 348:    */   {
/* 349:407 */     return this.fechaHasta;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setFechaHasta(Date fechaHasta)
/* 353:    */   {
/* 354:417 */     this.fechaHasta = fechaHasta;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public String getNumeroFacturaDesde()
/* 358:    */   {
/* 359:426 */     return this.numeroFacturaDesde;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setNumeroFacturaDesde(String numeroFacturaDesde)
/* 363:    */   {
/* 364:436 */     this.numeroFacturaDesde = numeroFacturaDesde;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public String getNumeroFacturaHasta()
/* 368:    */   {
/* 369:445 */     return this.numeroFacturaHasta;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setNumeroFacturaHasta(String numeroFacturaHasta)
/* 373:    */   {
/* 374:455 */     this.numeroFacturaHasta = numeroFacturaHasta;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public Empresa getEmpresa()
/* 378:    */   {
/* 379:464 */     if (this.empresa == null)
/* 380:    */     {
/* 381:465 */       this.empresa = new Empresa();
/* 382:466 */       this.empresa.setId(0);
/* 383:    */     }
/* 384:468 */     return this.empresa;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setEmpresa(Empresa empresa)
/* 388:    */   {
/* 389:478 */     this.empresa = empresa;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public int getIdVendedor()
/* 393:    */   {
/* 394:487 */     return this.idVendedor;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setIdVendedor(int idVendedor)
/* 398:    */   {
/* 399:497 */     this.idVendedor = idVendedor;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public Zona getZona()
/* 403:    */   {
/* 404:504 */     if (this.zona == null) {
/* 405:505 */       this.zona = new Zona();
/* 406:    */     }
/* 407:508 */     return this.zona;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setZona(Zona zona)
/* 411:    */   {
/* 412:516 */     this.zona = zona;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public Canal getCanal()
/* 416:    */   {
/* 417:523 */     if (this.canal == null) {
/* 418:524 */       this.canal = new Canal();
/* 419:    */     }
/* 420:527 */     return this.canal;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setCanal(Canal canal)
/* 424:    */   {
/* 425:535 */     this.canal = canal;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public Sucursal getSucursal()
/* 429:    */   {
/* 430:544 */     return this.sucursal;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setSucursal(Sucursal sucursal)
/* 434:    */   {
/* 435:554 */     this.sucursal = sucursal;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public boolean isIndicadorResumen()
/* 439:    */   {
/* 440:563 */     return this.indicadorResumen;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 444:    */   {
/* 445:573 */     this.indicadorResumen = indicadorResumen;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 449:    */   {
/* 450:577 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 451:    */   }
/* 452:    */   
/* 453:    */   public List<Empresa> getListaClienteCombo()
/* 454:    */   {
/* 455:586 */     if (this.listaClienteCombo == null) {
/* 456:587 */       this.listaClienteCombo = this.servicioEmpresa.obtenerClientes();
/* 457:    */     }
/* 458:589 */     return this.listaClienteCombo;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public void setListaClienteCombo(List<Empresa> listaClienteCombo)
/* 462:    */   {
/* 463:599 */     this.listaClienteCombo = listaClienteCombo;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public List<Zona> getListaZonaCombo()
/* 467:    */   {
/* 468:608 */     if (this.listaZonaCombo == null) {
/* 469:609 */       this.listaZonaCombo = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 470:    */     }
/* 471:611 */     return this.listaZonaCombo;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setListaZonaCombo(List<Zona> listaZonaCombo)
/* 475:    */   {
/* 476:621 */     this.listaZonaCombo = listaZonaCombo;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public List<Canal> getListaCanalCombo()
/* 480:    */   {
/* 481:630 */     if (this.listaCanalCombo == null) {
/* 482:631 */       this.listaCanalCombo = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 483:    */     }
/* 484:633 */     return this.listaCanalCombo;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void setListaCanalCombo(List<Canal> listaCanalCombo)
/* 488:    */   {
/* 489:643 */     this.listaCanalCombo = listaCanalCombo;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public List<EntidadUsuario> getListaVendedorCombo()
/* 493:    */   {
/* 494:652 */     if (this.listaVendedorCombo == null) {
/* 495:653 */       this.listaVendedorCombo = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId());
/* 496:    */     }
/* 497:655 */     return this.listaVendedorCombo;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void setListaVendedorCombo(List<EntidadUsuario> listaVendedorCombo)
/* 501:    */   {
/* 502:665 */     this.listaVendedorCombo = listaVendedorCombo;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public List<Sucursal> getListaSucursalCombo()
/* 506:    */   {
/* 507:674 */     if (this.listaSucursalCombo == null) {
/* 508:675 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 509:    */     }
/* 510:677 */     return this.listaSucursalCombo;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 514:    */   {
/* 515:687 */     this.listaSucursalCombo = listaSucursalCombo;
/* 516:    */   }
/* 517:    */   
/* 518:    */   public boolean isAnuladas()
/* 519:    */   {
/* 520:691 */     return this.anuladas;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public void setAnuladas(boolean anuladas)
/* 524:    */   {
/* 525:695 */     this.anuladas = anuladas;
/* 526:    */   }
/* 527:    */   
/* 528:    */   public List<SelectItem> getListaTipoVenta()
/* 529:    */   {
/* 530:704 */     if (this.listaTipoVenta == null)
/* 531:    */     {
/* 532:705 */       this.listaTipoVenta = new ArrayList();
/* 533:706 */       for (TipoVentaEnum tipoVenta : TipoVentaEnum.values())
/* 534:    */       {
/* 535:707 */         SelectItem item = new SelectItem(tipoVenta, tipoVenta.name());
/* 536:708 */         this.listaTipoVenta.add(item);
/* 537:    */       }
/* 538:    */     }
/* 539:711 */     return this.listaTipoVenta;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public void setListaTipoVenta(List<SelectItem> listaTipoVenta)
/* 543:    */   {
/* 544:721 */     this.listaTipoVenta = listaTipoVenta;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public TipoVentaEnum getTipoVenta()
/* 548:    */   {
/* 549:730 */     return this.tipoVenta;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setTipoVenta(TipoVentaEnum tipoVenta)
/* 553:    */   {
/* 554:740 */     this.tipoVenta = tipoVenta;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public boolean isSaldoInicial()
/* 558:    */   {
/* 559:749 */     return this.saldoInicial;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setSaldoInicial(boolean saldoInicial)
/* 563:    */   {
/* 564:759 */     this.saldoInicial = saldoInicial;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public enumTipoReporte getTipoReporte()
/* 568:    */   {
/* 569:768 */     return this.tipoReporte;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public boolean isIndicadorTipoReporte()
/* 573:    */   {
/* 574:777 */     this.indicadorTipoReporte = false;
/* 575:778 */     if (this.tipoReporte.equals(enumTipoReporte.PRODUCTO)) {
/* 576:779 */       this.indicadorTipoReporte = true;
/* 577:    */     }
/* 578:781 */     return this.indicadorTipoReporte;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public void setIndicadorTipoReporte(boolean indicadorTipoReporte)
/* 582:    */   {
/* 583:791 */     this.indicadorTipoReporte = indicadorTipoReporte;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 587:    */   {
/* 588:801 */     this.tipoReporte = tipoReporte;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public List<SelectItem> getListaTipoReporte()
/* 592:    */   {
/* 593:810 */     if (this.listaTipoReporte == null)
/* 594:    */     {
/* 595:811 */       this.listaTipoReporte = new ArrayList();
/* 596:812 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 597:813 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 598:    */       }
/* 599:    */     }
/* 600:816 */     return this.listaTipoReporte;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 604:    */   {
/* 605:826 */     this.listaTipoReporte = listaTipoReporte;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public DocumentoBase getDocumentoBase()
/* 609:    */   {
/* 610:835 */     return this.documentoBase;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 614:    */   {
/* 615:845 */     this.documentoBase = documentoBase;
/* 616:    */   }
/* 617:    */   
/* 618:    */   public MotivoNotaCreditoCliente getMotivoNotaCreditoCliente()
/* 619:    */   {
/* 620:854 */     if (this.motivoNotaCreditoCliente == null) {
/* 621:855 */       this.motivoNotaCreditoCliente = new MotivoNotaCreditoCliente();
/* 622:    */     }
/* 623:857 */     return this.motivoNotaCreditoCliente;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public void setMotivoNotaCreditoCliente(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/* 627:    */   {
/* 628:867 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public List<DocumentoBase> getListaDocumentoBase()
/* 632:    */   {
/* 633:876 */     if (this.listaDocumentoBase == null)
/* 634:    */     {
/* 635:877 */       this.listaDocumentoBase = new ArrayList();
/* 636:878 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 637:879 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 638:880 */       this.listaDocumentoBase.add(DocumentoBase.FACTURA_CLIENTE);
/* 639:881 */       this.listaDocumentoBase.add(DocumentoBase.DEVOLUCION_CLIENTE);
/* 640:    */     }
/* 641:883 */     return this.listaDocumentoBase;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public void setListaDocumentoBase(List<DocumentoBase> listaDocumentoBase)
/* 645:    */   {
/* 646:893 */     this.listaDocumentoBase = listaDocumentoBase;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public List<MotivoNotaCreditoCliente> getListaMotivoNotaCreditoCliente()
/* 650:    */   {
/* 651:902 */     if (this.listaMotivoNotaCreditoCliente == null) {
/* 652:903 */       this.listaMotivoNotaCreditoCliente = this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("nombre", true, null);
/* 653:    */     }
/* 654:905 */     return this.listaMotivoNotaCreditoCliente;
/* 655:    */   }
/* 656:    */   
/* 657:    */   public void setListaMotivoNotaCreditoCliente(List<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente)
/* 658:    */   {
/* 659:915 */     this.listaMotivoNotaCreditoCliente = listaMotivoNotaCreditoCliente;
/* 660:    */   }
/* 661:    */   
/* 662:    */   public boolean isIndicadorAgrupado()
/* 663:    */   {
/* 664:924 */     return this.indicadorAgrupado;
/* 665:    */   }
/* 666:    */   
/* 667:    */   public void setIndicadorAgrupado(boolean indicadorAgrupado)
/* 668:    */   {
/* 669:934 */     this.indicadorAgrupado = indicadorAgrupado;
/* 670:    */   }
/* 671:    */   
/* 672:    */   public boolean isProductoResumen()
/* 673:    */   {
/* 674:938 */     return (this.tipoReporte.equals(enumTipoReporte.PRODUCTO)) && (this.indicadorResumen);
/* 675:    */   }
/* 676:    */   
/* 677:    */   public CategoriaProducto getCategoriaProducto()
/* 678:    */   {
/* 679:942 */     return this.categoriaProducto;
/* 680:    */   }
/* 681:    */   
/* 682:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 683:    */   {
/* 684:946 */     this.categoriaProducto = categoriaProducto;
/* 685:    */   }
/* 686:    */   
/* 687:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 688:    */   {
/* 689:950 */     return this.subcategoriaProducto;
/* 690:    */   }
/* 691:    */   
/* 692:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 693:    */   {
/* 694:954 */     this.subcategoriaProducto = subcategoriaProducto;
/* 695:    */   }
/* 696:    */   
/* 697:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 698:    */   {
/* 699:958 */     HashMap<String, String> filters = new HashMap();
/* 700:959 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 701:960 */     if (this.listaCategoriaProductos == null) {
/* 702:961 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 703:    */     }
/* 704:963 */     return this.listaCategoriaProductos;
/* 705:    */   }
/* 706:    */   
/* 707:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 708:    */   {
/* 709:967 */     return this.listaSubcategoriaProductos;
/* 710:    */   }
/* 711:    */   
/* 712:    */   public Producto getProducto()
/* 713:    */   {
/* 714:971 */     return this.producto;
/* 715:    */   }
/* 716:    */   
/* 717:    */   public void setProducto(Producto producto)
/* 718:    */   {
/* 719:975 */     this.producto = producto;
/* 720:    */   }
/* 721:    */   
/* 722:    */   public List<PuntoDeVenta> getListaPuntoVenta()
/* 723:    */   {
/* 724:979 */     return this.listaPuntoVenta;
/* 725:    */   }
/* 726:    */   
/* 727:    */   public void setListaPuntoVenta(List<PuntoDeVenta> listaPuntoVenta)
/* 728:    */   {
/* 729:983 */     this.listaPuntoVenta = listaPuntoVenta;
/* 730:    */   }
/* 731:    */   
/* 732:    */   public PuntoDeVenta getPuntoVenta()
/* 733:    */   {
/* 734:987 */     return this.puntoVenta;
/* 735:    */   }
/* 736:    */   
/* 737:    */   public void setPuntoVenta(PuntoDeVenta puntoVenta)
/* 738:    */   {
/* 739:991 */     this.puntoVenta = puntoVenta;
/* 740:    */   }
/* 741:    */   
/* 742:    */   public DimensionContable getProyecto()
/* 743:    */   {
/* 744:995 */     return this.proyecto;
/* 745:    */   }
/* 746:    */   
/* 747:    */   public void setProyecto(DimensionContable proyecto)
/* 748:    */   {
/* 749:999 */     this.proyecto = proyecto;
/* 750:    */   }
/* 751:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteFacturacionResumidoClienteBean
 * JD-Core Version:    0.7.0.1
 */