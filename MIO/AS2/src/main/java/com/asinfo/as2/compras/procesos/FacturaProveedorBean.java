/*    1:     */ package com.asinfo.as2.compras.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.TempCorrrecionFacturas;
/*    4:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    5:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    6:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*    7:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    9:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   10:     */ import com.asinfo.as2.controller.LanguageController;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   12:     */ import com.asinfo.as2.entities.CentroCosto;
/*   13:     */ import com.asinfo.as2.entities.Ciudad;
/*   14:     */ import com.asinfo.as2.entities.CuentaContable;
/*   15:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   16:     */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacion;
/*   17:     */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionProducto;
/*   18:     */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   19:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   20:     */ import com.asinfo.as2.entities.Documento;
/*   21:     */ import com.asinfo.as2.entities.Empresa;
/*   22:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   23:     */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   24:     */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*   25:     */ import com.asinfo.as2.entities.Organizacion;
/*   26:     */ import com.asinfo.as2.entities.Proveedor;
/*   27:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   28:     */ import com.asinfo.as2.entities.Sucursal;
/*   29:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   30:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   31:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   32:     */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*   33:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   34:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   35:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   36:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   37:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/*   38:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*   39:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   40:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*   41:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   42:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*   43:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   44:     */ import com.asinfo.as2.util.AppUtil;
/*   45:     */ import com.asinfo.as2.util.RutaArchivo;
/*   46:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   47:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   48:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*   49:     */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*   50:     */ import java.io.BufferedInputStream;
/*   51:     */ import java.io.File;
/*   52:     */ import java.io.FileNotFoundException;
/*   53:     */ import java.io.IOException;
/*   54:     */ import java.io.InputStream;
/*   55:     */ import java.io.PrintStream;
/*   56:     */ import java.io.StringReader;
/*   57:     */ import java.math.BigDecimal;
/*   58:     */ import java.math.RoundingMode;
/*   59:     */ import java.text.SimpleDateFormat;
/*   60:     */ import java.util.ArrayList;
/*   61:     */ import java.util.Calendar;
/*   62:     */ import java.util.Date;
/*   63:     */ import java.util.HashMap;
/*   64:     */ import java.util.List;
/*   65:     */ import java.util.Map;
/*   66:     */ import javax.annotation.PostConstruct;
/*   67:     */ import javax.ejb.EJB;
/*   68:     */ import javax.faces.bean.ManagedBean;
/*   69:     */ import javax.faces.bean.ViewScoped;
/*   70:     */ import javax.validation.constraints.NotNull;
/*   71:     */ import javax.validation.constraints.Size;
/*   72:     */ import org.apache.log4j.Logger;
/*   73:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   74:     */ import org.jdom2.Document;
/*   75:     */ import org.jdom2.Element;
/*   76:     */ import org.jdom2.input.SAXBuilder;
/*   77:     */ import org.primefaces.component.datatable.DataTable;
/*   78:     */ import org.primefaces.event.FileUploadEvent;
/*   79:     */ import org.primefaces.event.SelectEvent;
/*   80:     */ import org.primefaces.model.StreamedContent;
/*   81:     */ import org.primefaces.model.UploadedFile;
/*   82:     */ import org.xml.sax.InputSource;
/*   83:     */ 
/*   84:     */ @ManagedBean
/*   85:     */ @ViewScoped
/*   86:     */ public class FacturaProveedorBean
/*   87:     */   extends FacturaProveedorBaseBean
/*   88:     */ {
/*   89:     */   private static final long serialVersionUID = 1L;
/*   90:  90 */   private BigDecimal saldoAnticiposProveedor = BigDecimal.ZERO;
/*   91:     */   private DataTable dtCuentasPorPagar;
/*   92:     */   private DataTable dtCuentaContable;
/*   93:     */   private DataTable dtDetalleFacturaProveedorImportacion;
/*   94:     */   private String pedidoSeleccionado;
/*   95:  98 */   private DetalleFacturaProveedorImportacion detalleFacturaProveedorImportacionSeleccionado = new DetalleFacturaProveedorImportacion();
/*   96:  99 */   private BigDecimal gastoImportacion = BigDecimal.ZERO;
/*   97:     */   private String numero;
/*   98:     */   private boolean mostrarSaldoInicial;
/*   99:     */   private boolean renderProveedorNuevo;
/*  100:     */   private boolean renderAutorizacionNuevo;
/*  101:     */   private String identificacionProveedor;
/*  102:     */   @NotNull
/*  103:     */   private TipoIdentificacion tipoIdentificacionProveedor;
/*  104:     */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  105:     */   @Size(min=10, max=100)
/*  106:     */   private String nombreProveedor;
/*  107:     */   @Size(min=2, max=50)
/*  108:     */   private String direccionProveedor;
/*  109:     */   @Size(max=13)
/*  110:     */   private String telefonoProveedor;
/*  111:     */   @NotNull
/*  112:     */   private Ciudad ciudad;
/*  113:     */   private String email;
/*  114:     */   private List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI;
/*  115:     */   private List<TempCorrrecionFacturas> listaTempCorrrecionFacturas;
/*  116:     */   @EJB
/*  117:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  118:     */   @EJB
/*  119:     */   private ServicioImpuesto servicioImpuesto;
/*  120:     */   private List<ReembolsoGastos> listaReembolsoGastosPrincipal;
/*  121:     */   private DataTable dtReembolsoGastos;
/*  122:     */   private List<TipoIdentificacion> listaTipoIdentificacionCombo;
/*  123: 138 */   private BigDecimal totalBaseImponibleDiferenteCero = BigDecimal.ZERO;
/*  124: 139 */   private BigDecimal totalBaseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  125: 140 */   private BigDecimal totalBaseImponibleTarifaCero = BigDecimal.ZERO;
/*  126: 141 */   private BigDecimal totalBaseExentaIva = BigDecimal.ZERO;
/*  127: 142 */   private BigDecimal totalMontoIva = BigDecimal.ZERO;
/*  128: 143 */   private BigDecimal totalMontoIce = BigDecimal.ZERO;
/*  129: 144 */   private BigDecimal totalDescuentoImpuesto = BigDecimal.ZERO;
/*  130: 146 */   private Boolean indicadorParametroEditarPrecioPedido = null;
/*  131:     */   
/*  132:     */   @PostConstruct
/*  133:     */   public void init()
/*  134:     */   {
/*  135: 151 */     super.init();
/*  136:     */   }
/*  137:     */   
/*  138:     */   protected void obtenerFiltros(Map<String, String> filters)
/*  139:     */   {
/*  140: 161 */     if (this.idFacturaProveedor != null)
/*  141:     */     {
/*  142: 162 */       filters.put("idFacturaProveedor", this.idFacturaProveedor.toString());
/*  143: 163 */       this.idFacturaProveedor = null;
/*  144:     */     }
/*  145: 166 */     if (this.numero != null)
/*  146:     */     {
/*  147: 167 */       filters.put("numero", this.numero);
/*  148: 168 */       this.numero = null;
/*  149:     */     }
/*  150: 171 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
/*  151: 172 */     filters.put("documento.indicadorDocumentoExterior", "false");
/*  152:     */   }
/*  153:     */   
/*  154:     */   public void actualizarProveedorListener(SelectEvent event)
/*  155:     */   {
/*  156: 185 */     Empresa empresa = this.servicioEmpresa.obtenerDatosProveedor(((Empresa)event.getObject()).getIdEmpresa());
/*  157: 186 */     actualizarProveedor(empresa);
/*  158: 187 */     cargarListaTipoComprobanteSRI();
/*  159: 188 */     this.listaFormaPagoSRI = null;
/*  160:     */   }
/*  161:     */   
/*  162:     */   public void actualizarProveedor(Empresa empresa)
/*  163:     */   {
/*  164: 198 */     getFacturaProveedor().setEmpresa(empresa);
/*  165: 199 */     if (getFacturaProveedor().getDocumento().isIndicadorDocumentoTributario())
/*  166:     */     {
/*  167: 200 */       getFacturaProveedor().getFacturaProveedorSRI().setTipoIdentificacion(empresa.getTipoIdentificacion());
/*  168: 201 */       getFacturaProveedor().getFacturaProveedorSRI().setPuntoEmision(null);
/*  169: 202 */       getFacturaProveedor().getFacturaProveedorSRI().setEstablecimiento(null);
/*  170: 203 */       getFacturaProveedor().getFacturaProveedorSRI().setAutorizacion(null);
/*  171:     */       
/*  172:     */ 
/*  173:     */ 
/*  174: 207 */       getFacturaProveedor().getFacturaProveedorSRI().setAutorizacionProveedorSRI(null);
/*  175:     */     }
/*  176: 212 */     List<TipoComprobanteSRI> listaTipoComprobanteSRI = this.servicioSRI.buscarPorTipoIdentificacion(empresa.getTipoIdentificacion());
/*  177: 213 */     setListaTipoComprobanteSRI(listaTipoComprobanteSRI);
/*  178:     */     
/*  179:     */ 
/*  180:     */ 
/*  181:     */ 
/*  182: 218 */     this.saldoAnticiposProveedor = this.servicioAnticipoProveedor.obtenerSaldoProveedor(getFacturaProveedor().getEmpresa().getId());
/*  183: 221 */     if (getFacturaProveedor().getCondicionPago() == null) {
/*  184: 222 */       getFacturaProveedor().setCondicionPago(empresa.getProveedor().getCondicionPago());
/*  185:     */     }
/*  186: 226 */     if (getFacturaProveedor().getNumeroCuotas() == 0) {
/*  187: 227 */       getFacturaProveedor().setNumeroCuotas(empresa.getProveedor().getNumeroCuotas());
/*  188:     */     }
/*  189: 230 */     cargarDirecciones();
/*  190: 231 */     actualizarListaPedidoClienteADespachar();
/*  191: 232 */     actualizarListaRegistroPesoPorLiquidar();
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void seleccionarImportacion(SelectEvent event)
/*  195:     */   {
/*  196: 237 */     FacturaProveedorImportacion facturaImportacion = (FacturaProveedorImportacion)event.getObject();
/*  197: 238 */     if (facturaImportacion != null) {
/*  198: 239 */       this.detalleFacturaProveedorImportacionSeleccionado.setFacturaProveedor(facturaImportacion.getFacturaProveedor());
/*  199:     */     }
/*  200:     */   }
/*  201:     */   
/*  202:     */   public String copiar()
/*  203:     */   {
/*  204:     */     try
/*  205:     */     {
/*  206: 245 */       FacturaProveedor fp = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(this.facturaProveedor.getId()));
/*  207: 246 */       this.facturaProveedor = this.servicioFacturaProveedor.copiarFacturaProveedor(fp);
/*  208: 247 */       cargarDirecciones();
/*  209: 248 */       cargarListaTipoComprobanteSRI();
/*  210: 249 */       totalizar();
/*  211: 250 */       this.listaFormaPagoSRI = null;
/*  212:     */       
/*  213: 252 */       setEditado(actualizarAutorizacionProveedorSRIInputText());
/*  214:     */     }
/*  215:     */     catch (ExcepcionAS2Compras e)
/*  216:     */     {
/*  217: 255 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  218:     */     }
/*  219:     */     catch (ExcepcionAS2 e)
/*  220:     */     {
/*  221: 258 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  222:     */     }
/*  223: 261 */     return "";
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void cargarDistribucionManual(DetalleFacturaProveedorImportacion detalleFacturaProveedorImportacion)
/*  227:     */   {
/*  228:     */     try
/*  229:     */     {
/*  230: 267 */       this.detalleFacturaProveedorImportacionSeleccionado = detalleFacturaProveedorImportacion;
/*  231:     */       
/*  232:     */ 
/*  233: 270 */       FacturaProveedor fpip = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(this.detalleFacturaProveedorImportacionSeleccionado.getFacturaProveedor().getId()));
/*  234: 271 */       if (this.detalleFacturaProveedorImportacionSeleccionado.getListaDetalleFacturaProveedorImportacionProducto().size() == 0) {
/*  235: 273 */         for (DetalleFacturaProveedor dfp : fpip.getListaDetalleFacturaProveedor())
/*  236:     */         {
/*  237: 274 */           DetalleFacturaProveedorImportacionProducto dfpip = new DetalleFacturaProveedorImportacionProducto();
/*  238: 275 */           dfpip.setDetalleFacturaProveedorImportacion(this.detalleFacturaProveedorImportacionSeleccionado);
/*  239: 276 */           dfpip.setDetalleFacturaProveedor(dfp);
/*  240: 277 */           this.detalleFacturaProveedorImportacionSeleccionado.getListaDetalleFacturaProveedorImportacionProducto().add(dfpip);
/*  241:     */         }
/*  242:     */       }
/*  243:     */     }
/*  244:     */     catch (ExcepcionAS2Compras e)
/*  245:     */     {
/*  246: 281 */       e.printStackTrace();
/*  247:     */     }
/*  248:     */   }
/*  249:     */   
/*  250:     */   public void buscarCuentaContable()
/*  251:     */   {
/*  252: 290 */     GastoProductoFacturaProveedor gastoProductoFacturaProveedor = (GastoProductoFacturaProveedor)getDtGastoProductoFacturaProveedor().getRowData();
/*  253: 291 */     String codigoCuentaContable = gastoProductoFacturaProveedor.getCuentaContableGasto().getCodigo();
/*  254:     */     try
/*  255:     */     {
/*  256: 293 */       if (codigoCuentaContable != "")
/*  257:     */       {
/*  258: 294 */         CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, 
/*  259: 295 */           AppUtil.getOrganizacion().getIdOrganizacion());
/*  260: 296 */         setGastoProductoFacturaProveedorSeleccionado(gastoProductoFacturaProveedor);
/*  261: 297 */         setearCuentaContable(cuentaContable);
/*  262:     */       }
/*  263:     */     }
/*  264:     */     catch (ExcepcionAS2Financiero e)
/*  265:     */     {
/*  266: 300 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/*  267: 301 */       addInfoMessage(strMensaje);
/*  268:     */     }
/*  269:     */   }
/*  270:     */   
/*  271:     */   public void agregarDetalleFacturaProveedorImportacion()
/*  272:     */   {
/*  273: 306 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)getDtGastoImportacion().getRowData();
/*  274: 307 */     DetalleFacturaProveedorImportacion dfpi = new DetalleFacturaProveedorImportacion();
/*  275: 308 */     dfpi.setIdOrganizacion(dfp.getIdOrganizacion());
/*  276: 309 */     dfpi.setIdSucursal(dfp.getIdSucursal());
/*  277: 310 */     dfpi.setDetalleFacturaProveedor(dfp);
/*  278: 311 */     dfp.getListaDetalleFacturaProveedorImportacion().add(dfpi);
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void eliminarDetalleFacturaProveedorImportacion()
/*  282:     */   {
/*  283: 315 */     this.detalleFacturaProveedorImportacionSeleccionado = ((DetalleFacturaProveedorImportacion)this.dtDetalleFacturaProveedorImportacion.getRowData());
/*  284: 316 */     this.detalleFacturaProveedorImportacionSeleccionado.setEliminado(true);
/*  285:     */   }
/*  286:     */   
/*  287:     */   public String agregarDetalleTemp(FileUploadEvent event)
/*  288:     */   {
/*  289: 327 */     if (this.listaTempCorrrecionFacturas == null) {
/*  290: 328 */       this.listaTempCorrrecionFacturas = new ArrayList();
/*  291:     */     }
/*  292:     */     try
/*  293:     */     {
/*  294: 332 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  295: 333 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getIdOrganizacion(), "temp", input, 1, 0);
/*  296: 334 */       for (HSSFCell[] fila : datos) {
/*  297:     */         try
/*  298:     */         {
/*  299: 336 */           String compra = fila[5].getStringCellValue().trim();
/*  300: 337 */           System.out.println(">>>>>>>>" + compra);
/*  301: 338 */           String centroCosto = fila[7].getStringCellValue().trim();
/*  302: 339 */           System.out.println(">>>>>>>>" + centroCosto);
/*  303: 340 */           String CCGasto = fila[8].getStringCellValue().trim();
/*  304: 341 */           System.out.println(">>>>>>>>" + CCGasto);
/*  305: 342 */           String CCGastoIva = fila[9].getStringCellValue().trim();
/*  306: 343 */           System.out.println(">>>>>>>>" + CCGastoIva);
/*  307:     */           
/*  308: 345 */           TempCorrrecionFacturas tmp = new TempCorrrecionFacturas();
/*  309: 346 */           tmp.setNumero(compra);
/*  310: 347 */           tmp.setCentroCosto(centroCosto);
/*  311: 348 */           tmp.setCuentaGasto(CCGasto);
/*  312: 349 */           tmp.setCuentaGastoIva(CCGastoIva);
/*  313: 350 */           this.listaTempCorrrecionFacturas.add(tmp);
/*  314:     */         }
/*  315:     */         catch (IllegalArgumentException e)
/*  316:     */         {
/*  317: 353 */           e.printStackTrace();
/*  318: 354 */           break;
/*  319:     */         }
/*  320:     */         catch (Exception e)
/*  321:     */         {
/*  322: 356 */           e.printStackTrace();
/*  323: 357 */           break;
/*  324:     */         }
/*  325:     */       }
/*  326: 360 */       System.out.println("Tamanio a procesar" + this.listaTempCorrrecionFacturas.size());
/*  327: 361 */       procesarTemporal();
/*  328:     */     }
/*  329:     */     catch (IOException e1)
/*  330:     */     {
/*  331: 363 */       e1.printStackTrace();
/*  332:     */     }
/*  333: 366 */     return "";
/*  334:     */   }
/*  335:     */   
/*  336:     */   public void procesarTemporal()
/*  337:     */   {
/*  338: 370 */     for (TempCorrrecionFacturas tmp : this.listaTempCorrrecionFacturas) {
/*  339:     */       try
/*  340:     */       {
/*  341: 372 */         CentroCosto centroCosto = this.servicioCentroCosto.buscarPorCodigo(tmp.getCentroCosto());
/*  342: 373 */         CuentaContable CCGasto = this.servicioCuentaContable.buscarPorCodigo(tmp.getCuentaGasto(), AppUtil.getOrganizacion().getId());
/*  343: 374 */         CuentaContable CCGastoIva = this.servicioCuentaContable.buscarPorCodigo(tmp.getCuentaGastoIva(), AppUtil.getOrganizacion().getId());
/*  344: 375 */         setCentroCosto(centroCosto);
/*  345: 376 */         Map<String, String> filters = new HashMap();
/*  346: 377 */         filters.put("numero", tmp.getNumero());
/*  347: 378 */         if (!this.servicioFacturaProveedor.obtenerListaCombo("", false, filters).isEmpty())
/*  348:     */         {
/*  349: 379 */           FacturaProveedor facturaProveedor = (FacturaProveedor)this.servicioFacturaProveedor.obtenerListaCombo("", false, filters).get(0);
/*  350: 380 */           facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(facturaProveedor.getId()));
/*  351: 381 */           for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  352: 382 */             this.servicioFacturaProveedor.agregarGastoProductoFacturaProveedor(dfp, null, null, null, null, null, CCGasto, CCGastoIva);
/*  353:     */           }
/*  354: 384 */           this.servicioFacturaProveedor.guardarAux(facturaProveedor);
/*  355: 385 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  356:     */         }
/*  357:     */       }
/*  358:     */       catch (ExcepcionAS2Compras e)
/*  359:     */       {
/*  360: 389 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  361: 390 */         e.printStackTrace();
/*  362:     */       }
/*  363:     */       catch (ExcepcionAS2 e)
/*  364:     */       {
/*  365: 392 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  366: 393 */         e.printStackTrace();
/*  367:     */       }
/*  368:     */       catch (Exception e)
/*  369:     */       {
/*  370: 395 */         e.printStackTrace();
/*  371: 396 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  372:     */       }
/*  373:     */     }
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void agregarDetalleReembolsoGastos()
/*  377:     */   {
/*  378: 410 */     ReembolsoGastos rg = new ReembolsoGastos();
/*  379: 411 */     rg.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  380: 412 */     rg.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  381: 413 */     rg.setFacturaProveedorSRI(getFacturaProveedor().getFacturaProveedorSRI());
/*  382: 414 */     getFacturaProveedor().getFacturaProveedorSRI().getListaReembolsoGastos().add(rg);
/*  383:     */   }
/*  384:     */   
/*  385:     */   public List<ReembolsoGastos> getListaReembolsoGastos()
/*  386:     */   {
/*  387: 420 */     List<ReembolsoGastos> listaFiltrado = new ArrayList();
/*  388: 421 */     for (ReembolsoGastos rg : getFacturaProveedor().getFacturaProveedorSRI().getListaReembolsoGastos()) {
/*  389: 422 */       if (!rg.isEliminado()) {
/*  390: 423 */         listaFiltrado.add(rg);
/*  391:     */       }
/*  392:     */     }
/*  393: 427 */     return listaFiltrado;
/*  394:     */   }
/*  395:     */   
/*  396:     */   public String editar()
/*  397:     */   {
/*  398:     */     try
/*  399:     */     {
/*  400: 432 */       super.editarPadre();
/*  401: 433 */       if ((this.facturaProveedor.getFacturaProveedorSRI() != null) && (this.facturaProveedor.getFacturaProveedorSRI().isIndicadorReembolso()))
/*  402:     */       {
/*  403: 434 */         totalizarReembolsoGastos();
/*  404: 436 */         for (ReembolsoGastos rg : this.facturaProveedor.getFacturaProveedorSRI().getListaReembolsoGastos())
/*  405:     */         {
/*  406: 437 */           List<TipoComprobanteSRI> listaTipoComprobanteSRI = this.servicioSRI.buscarPorTipoIdentificacion(rg.getTipoIdentificacion());
/*  407: 438 */           rg.setListaTipoComprobanteSRI(listaTipoComprobanteSRI);
/*  408:     */         }
/*  409:     */       }
/*  410: 442 */       for (DetalleFacturaProveedor dfp : this.facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  411: 443 */         if (dfp.getDetallePedidoProveedor() != null)
/*  412:     */         {
/*  413: 444 */           setPedidoProveedor(dfp.getDetallePedidoProveedor().getPedidoProveedor());
/*  414: 445 */           break;
/*  415:     */         }
/*  416:     */       }
/*  417:     */     }
/*  418:     */     catch (ExcepcionAS2Financiero e)
/*  419:     */     {
/*  420: 449 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  421: 450 */       LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  422:     */     }
/*  423:     */     catch (ExcepcionAS2Compras e)
/*  424:     */     {
/*  425: 453 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  426: 454 */       LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  427:     */     }
/*  428:     */     catch (Exception e)
/*  429:     */     {
/*  430: 457 */       addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/*  431: 458 */       LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  432:     */     }
/*  433: 461 */     return "";
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void totalizarReembolsoGastos()
/*  437:     */   {
/*  438: 466 */     setTotalBaseImponibleDiferenteCero(BigDecimal.ZERO);
/*  439: 467 */     setTotalBaseImponibleNoObjetoIva(BigDecimal.ZERO);
/*  440: 468 */     setTotalBaseImponibleTarifaCero(BigDecimal.ZERO);
/*  441: 469 */     setTotalBaseExentaIva(BigDecimal.ZERO);
/*  442: 470 */     setTotalMontoIva(BigDecimal.ZERO);
/*  443: 471 */     setTotalMontoIce(BigDecimal.ZERO);
/*  444: 472 */     setTotalDescuentoImpuesto(BigDecimal.ZERO);
/*  445: 474 */     for (ReembolsoGastos rg : getListaReembolsoGastos()) {
/*  446: 475 */       if (!rg.isEliminado())
/*  447:     */       {
/*  448: 477 */         BigDecimal porcentajeImpuestoParaReembolso = this.servicioImpuesto.getPorcentajeIVA(getFacturaProveedor().getIdOrganizacion(), rg
/*  449: 478 */           .getFechaEmision() != null ? rg.getFechaEmision() : getFacturaProveedor().getFecha());
/*  450: 479 */         rg.setMontoIva(rg.getBaseImponibleDiferenteCero()
/*  451: 480 */           .multiply(porcentajeImpuestoParaReembolso.divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP)));
/*  452: 481 */         rg.setMontoIva(FuncionesUtiles.redondearBigDecimal(rg.getMontoIva(), 2));
/*  453:     */         
/*  454: 483 */         this.totalBaseImponibleTarifaCero = this.totalBaseImponibleTarifaCero.add(rg.getBaseImponibleTarifaCero());
/*  455: 484 */         this.totalBaseImponibleDiferenteCero = this.totalBaseImponibleDiferenteCero.add(rg.getBaseImponibleDiferenteCero());
/*  456: 485 */         this.totalBaseImponibleNoObjetoIva = this.totalBaseImponibleNoObjetoIva.add(rg.getBaseImponibleNoObjetoIva());
/*  457: 486 */         this.totalBaseExentaIva = this.totalBaseExentaIva.add(rg.getBaseExentaIva());
/*  458: 487 */         this.totalMontoIva = this.totalMontoIva.add(rg.getMontoIva());
/*  459: 488 */         this.totalMontoIce = this.totalMontoIce.add(rg.getMontoIce());
/*  460: 489 */         this.totalDescuentoImpuesto = this.totalDescuentoImpuesto.add(rg.getDescuentoImpuesto());
/*  461:     */       }
/*  462:     */     }
/*  463:     */   }
/*  464:     */   
/*  465:     */   public String eliminarDetalleReembolsoGastos()
/*  466:     */   {
/*  467: 497 */     ReembolsoGastos rg = (ReembolsoGastos)this.dtReembolsoGastos.getRowData();
/*  468: 498 */     rg.setEliminado(true);
/*  469: 499 */     totalizarReembolsoGastos();
/*  470: 500 */     return "";
/*  471:     */   }
/*  472:     */   
/*  473:     */   public void buscarEmpresaEvent()
/*  474:     */   {
/*  475:     */     try
/*  476:     */     {
/*  477: 506 */       ReembolsoGastos rg = (ReembolsoGastos)this.dtReembolsoGastos.getRowData();
/*  478: 507 */       if (rg.getTipoIdentificacion().isIndicadorValidarIdentificacion()) {
/*  479: 508 */         ValidarIdentificacion.validarIdentificacion(true, rg.getIdentificacionProveedor());
/*  480:     */       }
/*  481:     */     }
/*  482:     */     catch (ExcepcionAS2Identification e)
/*  483:     */     {
/*  484: 512 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  485: 513 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  486: 514 */       e.printStackTrace();
/*  487:     */     }
/*  488:     */   }
/*  489:     */   
/*  490:     */   public String getDirectorioDescarga()
/*  491:     */   {
/*  492: 520 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "factura_proveedor");
/*  493:     */   }
/*  494:     */   
/*  495:     */   public String getNombreArchivo()
/*  496:     */   {
/*  497: 525 */     return this.facturaProveedor.getPdf();
/*  498:     */   }
/*  499:     */   
/*  500:     */   public void processUpload(FileUploadEvent event)
/*  501:     */   {
/*  502:     */     try
/*  503:     */     {
/*  504: 538 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "factura_proveedor");
/*  505:     */       
/*  506: 540 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getFacturaProveedor().getNumero(), uploadDir);
/*  507:     */       
/*  508:     */ 
/*  509: 543 */       HashMap<String, Object> campos = new HashMap();
/*  510: 544 */       campos.put("pdf", fileName);
/*  511: 545 */       this.servicioFacturaProveedor.actualizarAtributoEntidad(getFacturaProveedor(), campos);
/*  512:     */       
/*  513: 547 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  514:     */     }
/*  515:     */     catch (Exception e)
/*  516:     */     {
/*  517: 550 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  518: 551 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  519:     */     }
/*  520:     */   }
/*  521:     */   
/*  522:     */   public String eliminarArchivo()
/*  523:     */   {
/*  524: 557 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getFacturaProveedor().getPdf());
/*  525: 558 */     getFacturaProveedor().setPdf(null);
/*  526: 559 */     HashMap<String, Object> campos = new HashMap();
/*  527: 560 */     campos.put("pdf", null);
/*  528: 561 */     this.servicioFacturaProveedor.actualizarAtributoEntidad(getFacturaProveedor(), campos);
/*  529: 562 */     return null;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public String crearProveedor()
/*  533:     */   {
/*  534:     */     try
/*  535:     */     {
/*  536: 573 */       Empresa empresa = this.servicioEmpresa.crearClienteProveedorConDatosBasicos(this.identificacionProveedor, this.tipoIdentificacionProveedor, this.nombreProveedor, this.direccionProveedor, this.telefonoProveedor, 
/*  537: 574 */         AppUtil.getOrganizacion().getIdOrganizacion(), 
/*  538: 575 */         AppUtil.getSucursal().getIdSucursal(), this.ciudad, null, this.email, false);
/*  539:     */       
/*  540: 577 */       this.identificacionProveedor = null;
/*  541: 578 */       this.tipoIdentificacionProveedor = null;
/*  542: 579 */       this.nombreProveedor = null;
/*  543: 580 */       this.direccionProveedor = null;
/*  544: 581 */       this.telefonoProveedor = null;
/*  545: 582 */       this.email = null;
/*  546: 583 */       actualizarProveedor(empresa);
/*  547: 584 */       setRenderProveedorNuevo(false);
/*  548: 585 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  549:     */     }
/*  550:     */     catch (ExcepcionAS2Identification e)
/*  551:     */     {
/*  552: 587 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  553: 588 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  554:     */     }
/*  555:     */     catch (ExcepcionAS2 e)
/*  556:     */     {
/*  557: 590 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  558: 591 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  559:     */     }
/*  560:     */     catch (Exception ex)
/*  561:     */     {
/*  562: 593 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  563: 594 */       LOG.error("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", ex);
/*  564:     */     }
/*  565: 597 */     return "";
/*  566:     */   }
/*  567:     */   
/*  568:     */   public void actualizarListaAutorizacion()
/*  569:     */   {
/*  570: 601 */     this.renderAutorizacionNuevo = true;
/*  571: 602 */     this.listaAutorizacionProveedorSRI = new ArrayList();
/*  572: 603 */     if (this.facturaProveedor.getEmpresa() != null)
/*  573:     */     {
/*  574: 604 */       AutorizacionProveedorSRI autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/*  575: 605 */       autorizacionProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  576: 606 */       autorizacionProveedorSRI.setEmpresa(this.facturaProveedor.getEmpresa());
/*  577: 607 */       autorizacionProveedorSRI.setActivo(true);
/*  578: 608 */       this.listaAutorizacionProveedorSRI.add(autorizacionProveedorSRI);
/*  579:     */     }
/*  580:     */     else
/*  581:     */     {
/*  582: 610 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  583:     */     }
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void crearAutorizacion()
/*  587:     */   {
/*  588:     */     try
/*  589:     */     {
/*  590: 617 */       AutorizacionProveedorSRI autorizacionProveedorSRI = (AutorizacionProveedorSRI)getListaAutorizacionProveedorSRI().get(0);
/*  591: 619 */       if (autorizacionProveedorSRI.isIndicadorFacturaElectronica())
/*  592:     */       {
/*  593: 621 */         HashMap<String, String> filters = new HashMap();
/*  594: 622 */         filters.put("puntoEmision", "=" + autorizacionProveedorSRI.getPuntoEmision());
/*  595: 623 */         filters.put("establecimiento", "=" + autorizacionProveedorSRI.getEstablecimiento());
/*  596: 624 */         filters.put("indicadorFacturaElectronica", "=true");
/*  597: 625 */         filters.put("empresa.idEmpresa", "=" + this.facturaProveedor.getEmpresa().getIdEmpresa());
/*  598: 626 */         filters.put("idOrganizacion", "!=-1");
/*  599:     */         
/*  600: 628 */         List<AutorizacionProveedorSRI> lista = this.servicioAutorizacionProveedorSRI.obtenerListaCombo(AutorizacionProveedorSRI.class, "establecimiento", true, filters);
/*  601: 631 */         if (lista.isEmpty())
/*  602:     */         {
/*  603: 632 */           autorizacionProveedorSRI.setAutorizacion("0000000000000000000000000000000000000");
/*  604: 633 */           autorizacionProveedorSRI.setFechaDesde(FuncionesUtiles.getFecha(1, 1, 1999));
/*  605: 634 */           autorizacionProveedorSRI.setFechaHasta(FuncionesUtiles.getFecha(31, 12, 2999));
/*  606: 635 */           autorizacionProveedorSRI.setActivo(true);
/*  607: 636 */           this.servicioAutorizacionProveedorSRI.guardar(autorizacionProveedorSRI);
/*  608:     */         }
/*  609:     */         else
/*  610:     */         {
/*  611: 638 */           autorizacionProveedorSRI = (AutorizacionProveedorSRI)lista.get(0);
/*  612:     */         }
/*  613:     */       }
/*  614:     */       else
/*  615:     */       {
/*  616: 642 */         this.servicioAutorizacionProveedorSRI.guardar(autorizacionProveedorSRI);
/*  617:     */       }
/*  618: 645 */       if ((autorizacionProveedorSRI.isActivo()) && (autorizacionProveedorSRI.getFechaHasta().compareTo(this.facturaProveedor.getFecha()) != -1))
/*  619:     */       {
/*  620: 646 */         this.facturaProveedor.getFacturaProveedorSRI().setAutorizacionProveedorSRI(autorizacionProveedorSRI);
/*  621: 647 */         this.facturaProveedor.getFacturaProveedorSRI().setEstablecimiento(autorizacionProveedorSRI.getEstablecimiento());
/*  622: 648 */         this.facturaProveedor.getFacturaProveedorSRI().setPuntoEmision(autorizacionProveedorSRI.getPuntoEmision());
/*  623: 649 */         if (!autorizacionProveedorSRI.isIndicadorFacturaElectronica()) {
/*  624: 650 */           this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion(autorizacionProveedorSRI.getAutorizacion());
/*  625:     */         } else {
/*  626: 652 */           this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion("");
/*  627:     */         }
/*  628: 654 */         this.facturaProveedor.getFacturaProveedorSRI().setIndicadorFacturaElectronica(autorizacionProveedorSRI.isIndicadorFacturaElectronica());
/*  629: 655 */         this.facturaProveedor.getFacturaProveedorSRI().setPatronAutorizacion(autorizacionProveedorSRI.getPatronAutorizacion());
/*  630:     */       }
/*  631: 657 */       setListaAutorizacionProveedorSRI(null);
/*  632: 658 */       setRenderAutorizacionNuevo(false);
/*  633:     */     }
/*  634:     */     catch (AS2Exception e)
/*  635:     */     {
/*  636: 660 */       addInfoMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  637: 661 */       e.printStackTrace();
/*  638:     */     }
/*  639:     */   }
/*  640:     */   
/*  641:     */   public List<AutorizacionProveedorSRI> getListaAutorizacionProveedorSRI()
/*  642:     */   {
/*  643: 668 */     return this.listaAutorizacionProveedorSRI;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setListaAutorizacionProveedorSRI(List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI)
/*  647:     */   {
/*  648: 672 */     this.listaAutorizacionProveedorSRI = listaAutorizacionProveedorSRI;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public String getPedidoSeleccionado()
/*  652:     */   {
/*  653: 681 */     return this.pedidoSeleccionado;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void setPedidoSeleccionado(String pedidoSeleccionado)
/*  657:     */   {
/*  658: 691 */     this.pedidoSeleccionado = pedidoSeleccionado;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public DataTable getDtCuentasPorPagar()
/*  662:     */   {
/*  663: 700 */     return this.dtCuentasPorPagar;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void setDtCuentasPorPagar(DataTable dtCuentasPorPagar)
/*  667:     */   {
/*  668: 710 */     this.dtCuentasPorPagar = dtCuentasPorPagar;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public boolean isMostrarSaldoInicial()
/*  672:     */   {
/*  673: 719 */     Calendar calendario = Calendar.getInstance();
/*  674: 720 */     calendario.setTime(ParametrosSistema.getFechaMaximaSaldosIniciales(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  675: 721 */     int day = calendario.get(5);
/*  676: 722 */     int month = calendario.get(2) + 1;
/*  677: 723 */     int year = calendario.get(1);
/*  678:     */     
/*  679: 725 */     Date fecha = FuncionesUtiles.getFecha(day, month, year);
/*  680:     */     
/*  681: 727 */     this.mostrarSaldoInicial = FuncionesUtiles.compararFechaAnteriorOIgual(new Date(), fecha);
/*  682:     */     
/*  683: 729 */     return this.mostrarSaldoInicial;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void setMostrarSaldoInicial(boolean mostrarSaldoInicial)
/*  687:     */   {
/*  688: 739 */     this.mostrarSaldoInicial = mostrarSaldoInicial;
/*  689:     */   }
/*  690:     */   
/*  691:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  692:     */   {
/*  693: 743 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/*  694:     */   }
/*  695:     */   
/*  696:     */   public BigDecimal getSaldoAnticiposProveedor()
/*  697:     */   {
/*  698: 754 */     return this.saldoAnticiposProveedor;
/*  699:     */   }
/*  700:     */   
/*  701:     */   public void setSaldoAnticiposProveedor(BigDecimal saldoAnticiposProveedor)
/*  702:     */   {
/*  703: 764 */     this.saldoAnticiposProveedor = saldoAnticiposProveedor;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public DataTable getDtCuentaContable()
/*  707:     */   {
/*  708: 773 */     return this.dtCuentaContable;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public void setDtCuentaContable(DataTable dtCuentaContable)
/*  712:     */   {
/*  713: 783 */     this.dtCuentaContable = dtCuentaContable;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public String getNumero()
/*  717:     */   {
/*  718: 792 */     return this.numero;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public void setNumero(String numero)
/*  722:     */   {
/*  723: 802 */     this.numero = numero;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public List<TempCorrrecionFacturas> getListaTempCorrrecionFacturas()
/*  727:     */   {
/*  728: 811 */     return this.listaTempCorrrecionFacturas;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void setListaTempCorrrecionFacturas(List<TempCorrrecionFacturas> listaTempCorrrecionFacturas)
/*  732:     */   {
/*  733: 821 */     this.listaTempCorrrecionFacturas = listaTempCorrrecionFacturas;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public DataTable getDtDetalleFacturaProveedorImportacion()
/*  737:     */   {
/*  738: 830 */     return this.dtDetalleFacturaProveedorImportacion;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public void setDtDetalleFacturaProveedorImportacion(DataTable dtDetalleFacturaProveedorImportacion)
/*  742:     */   {
/*  743: 840 */     this.dtDetalleFacturaProveedorImportacion = dtDetalleFacturaProveedorImportacion;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public DetalleFacturaProveedorImportacion getDetalleFacturaProveedorImportacionSeleccionado()
/*  747:     */   {
/*  748: 849 */     return this.detalleFacturaProveedorImportacionSeleccionado;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public void setDetalleFacturaProveedorImportacionSeleccionado(DetalleFacturaProveedorImportacion detalleFacturaProveedorImportacionSeleccionado)
/*  752:     */   {
/*  753: 859 */     this.detalleFacturaProveedorImportacionSeleccionado = detalleFacturaProveedorImportacionSeleccionado;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public BigDecimal getGastoImportacion()
/*  757:     */   {
/*  758: 868 */     this.gastoImportacion = BigDecimal.ZERO;
/*  759: 869 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)getDtDetalleFacturaProveedor().getRowData();
/*  760: 870 */     for (DetalleFacturaProveedorImportacion dfpi : dfp.getListaDetalleFacturaProveedorImportacion()) {
/*  761: 871 */       if (!dfpi.isEliminado()) {
/*  762: 872 */         this.gastoImportacion = this.gastoImportacion.add(dfpi.getValor());
/*  763:     */       }
/*  764:     */     }
/*  765: 875 */     return this.gastoImportacion;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public Integer getIdFacturaProveedor()
/*  769:     */   {
/*  770: 884 */     return this.idFacturaProveedor;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setIdFacturaProveedor(Integer idFacturaProveedor)
/*  774:     */   {
/*  775: 894 */     this.idFacturaProveedor = idFacturaProveedor;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public String cargarRecepcionesPendientes()
/*  779:     */   {
/*  780: 898 */     if (this.facturaProveedor.getEmpresa() == null)
/*  781:     */     {
/*  782: 899 */       this.listaRecepcionProveedorPendientes = new ArrayList();
/*  783: 900 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar_proveedor"));
/*  784:     */     }
/*  785:     */     else
/*  786:     */     {
/*  787: 903 */       this.listaRecepcionProveedorPendientes = this.servicioRecepcionProveedor.buscarRecepcionesNoFacturadasPorProveedor(Integer.valueOf(this.facturaProveedor.getEmpresa().getId()));
/*  788: 904 */       for (RecepcionProveedor recepcion : this.listaRecepcionProveedorAsignados) {
/*  789: 905 */         this.listaRecepcionProveedorPendientes.remove(recepcion);
/*  790:     */       }
/*  791:     */     }
/*  792: 908 */     return "";
/*  793:     */   }
/*  794:     */   
/*  795:     */   public String adicionarRecepciones()
/*  796:     */   {
/*  797: 912 */     for (RecepcionProveedor recepcion : this.listaRecepcionProveedorSeleccionados)
/*  798:     */     {
/*  799: 913 */       RecepcionProveedor recepcionProveedor = this.servicioRecepcionProveedor.cargarDetalleAFacturar(recepcion.getIdRecepcionProveedor());
/*  800: 914 */       agregarDetalleDespachoAFactura(recepcionProveedor);
/*  801: 915 */       this.listaRecepcionProveedorAsignados.add(recepcionProveedor);
/*  802:     */     }
/*  803: 917 */     return "";
/*  804:     */   }
/*  805:     */   
/*  806:     */   public String eliminarDetalle()
/*  807:     */   {
/*  808: 922 */     super.eliminarDetalle();
/*  809: 923 */     this.listaRecepcionProveedorAsignados = new ArrayList();
/*  810: 924 */     this.facturaProveedor.setRecepcionProveedor(null);
/*  811: 925 */     Map<Integer, RecepcionProveedor> mapaRecepcionProveedor = new HashMap();
/*  812: 927 */     for (DetalleFacturaProveedor detalle : this.facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  813: 928 */       if ((!detalle.isEliminado()) && (!detalle.getListaDetalleRecepcionProveedor().isEmpty())) {
/*  814: 929 */         for (DetalleRecepcionProveedor detalleRecepcion : detalle.getListaDetalleRecepcionProveedor())
/*  815:     */         {
/*  816: 930 */           mapaRecepcionProveedor.put(Integer.valueOf(detalleRecepcion.getRecepcionProveedor().getId()), detalleRecepcion.getRecepcionProveedor());
/*  817: 931 */           this.facturaProveedor.setRecepcionProveedor(detalleRecepcion.getRecepcionProveedor());
/*  818:     */         }
/*  819:     */       }
/*  820:     */     }
/*  821: 936 */     this.listaRecepcionProveedorAsignados.addAll(mapaRecepcionProveedor.values());
/*  822:     */     
/*  823: 938 */     return "";
/*  824:     */   }
/*  825:     */   
/*  826:     */   public String cargarCabeceraFacturaProveedorXML(FileUploadEvent event)
/*  827:     */   {
/*  828:     */     try
/*  829:     */     {
/*  830: 943 */       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
/*  831: 944 */       Date fecha = null;
/*  832: 945 */       SAXBuilder builder = new SAXBuilder();
/*  833: 946 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  834:     */       
/*  835: 948 */       Document document = builder.build(input);
/*  836: 949 */       Element rootNode = document.getRootElement();
/*  837: 950 */       List<Element> list = rootNode.getChildren("comprobante");
/*  838: 951 */       List<Element> listNumeroAutorizacion = rootNode.getChildren("numeroAutorizacion");
/*  839: 954 */       if ((list == null) || (list.isEmpty()))
/*  840:     */       {
/*  841: 955 */         addErrorMessage(getLanguageController().getMensaje("msg_error_carga_xml_proveedor"));
/*  842: 956 */         return "";
/*  843:     */       }
/*  844: 959 */       for (int i = 0; i < list.size(); i++)
/*  845:     */       {
/*  846: 961 */         Element node = (Element)list.get(i);
/*  847: 962 */         String comprobante = node.getText();
/*  848: 963 */         Document documentString = builder.build(new InputSource(new StringReader(comprobante)));
/*  849: 964 */         Element rootNodes = documentString.getRootElement();
/*  850: 965 */         List<Element> infoFactura = rootNodes.getChildren("infoFactura");
/*  851: 966 */         List<Element> infoTributaria = rootNodes.getChildren("infoTributaria");
/*  852: 968 */         for (int j = 0; j < infoFactura.size(); j++)
/*  853:     */         {
/*  854: 969 */           Element nodeInfoFactura = (Element)infoFactura.get(j);
/*  855: 970 */           String fechaEmision = nodeInfoFactura.getChildText("fechaEmision");
/*  856: 971 */           fecha = dateFormat.parse(fechaEmision);
/*  857: 972 */           this.facturaProveedor.getFacturaProveedorSRI().setFechaEmision(fecha);
/*  858:     */         }
/*  859: 975 */         for (int k = 0; k < infoTributaria.size(); k++)
/*  860:     */         {
/*  861: 977 */           Element nodeInfoTributaria = (Element)infoTributaria.get(k);
/*  862: 978 */           String identificacion = nodeInfoTributaria.getChildText("ruc");
/*  863: 979 */           Empresa empresaPorId = this.servicioEmpresa.buscarEmpresaPorIdentificacion(AppUtil.getOrganizacion().getId(), identificacion);
/*  864: 980 */           Empresa obtenerDatosProveedor = this.servicioEmpresa.obtenerDatosProveedor(empresaPorId.getId());
/*  865: 981 */           actualizarProveedor(obtenerDatosProveedor);
/*  866: 982 */           String secuencial = nodeInfoTributaria.getChildText("secuencial");
/*  867: 983 */           this.facturaProveedor.getFacturaProveedorSRI().setNumero(secuencial);
/*  868: 984 */           String estab = nodeInfoTributaria.getChildText("estab");
/*  869: 985 */           String ptoEmi = nodeInfoTributaria.getChildText("ptoEmi");
/*  870: 988 */           for (int m = 0; m < listNumeroAutorizacion.size(); m++)
/*  871:     */           {
/*  872: 989 */             Element nodeListNumeroAutorizacion = (Element)listNumeroAutorizacion.get(i);
/*  873: 990 */             String autorizacion = nodeListNumeroAutorizacion.getText();
/*  874: 991 */             this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion(autorizacion);
/*  875:     */           }
/*  876: 995 */           HashMap<String, String> filters = new HashMap();
/*  877: 996 */           filters.put("puntoEmision", "=" + ptoEmi);
/*  878: 997 */           filters.put("establecimiento", "=" + estab);
/*  879: 998 */           filters.put("indicadorFacturaElectronica", "=true");
/*  880: 999 */           filters.put("empresa.idEmpresa", "=" + this.facturaProveedor.getEmpresa().getIdEmpresa());
/*  881:1000 */           filters.put("idOrganizacion", "=" + AppUtil.getOrganizacion().getId());
/*  882:1001 */           List<AutorizacionProveedorSRI> lista = this.servicioAutorizacionProveedorSRI.obtenerListaCombo(AutorizacionProveedorSRI.class, "establecimiento", true, filters);
/*  883:1002 */           AutorizacionProveedorSRI autorizacionProveedorSRI = null;
/*  884:1003 */           if (lista.isEmpty())
/*  885:     */           {
/*  886:1005 */             autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/*  887:1006 */             autorizacionProveedorSRI.setEstablecimiento(estab);
/*  888:1007 */             autorizacionProveedorSRI.setPuntoEmision(ptoEmi);
/*  889:1008 */             autorizacionProveedorSRI.setIndicadorFacturaElectronica(true);
/*  890:1009 */             autorizacionProveedorSRI.setActivo(true);
/*  891:1010 */             autorizacionProveedorSRI.setEmpresa(obtenerDatosProveedor);
/*  892:1011 */             autorizacionProveedorSRI.setAutorizacion("0000000000000000000000000000000000000");
/*  893:1012 */             autorizacionProveedorSRI.setFechaDesde(FuncionesUtiles.getFecha(1, 1, 1999));
/*  894:1013 */             autorizacionProveedorSRI.setFechaHasta(FuncionesUtiles.getFecha(31, 12, 2999));
/*  895:1014 */             autorizacionProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  896:1015 */             this.servicioAutorizacionProveedorSRI.guardar(autorizacionProveedorSRI);
/*  897:     */           }
/*  898:     */           else
/*  899:     */           {
/*  900:1018 */             autorizacionProveedorSRI = (AutorizacionProveedorSRI)lista.get(0);
/*  901:     */           }
/*  902:1021 */           this.facturaProveedor.getFacturaProveedorSRI().setEstablecimiento(estab);
/*  903:1022 */           this.facturaProveedor.getFacturaProveedorSRI().setPuntoEmision(ptoEmi);
/*  904:1023 */           this.facturaProveedor.getFacturaProveedorSRI().setIndicadorFacturaElectronica(true);
/*  905:1024 */           this.facturaProveedor.getFacturaProveedorSRI().setAutorizacionProveedorSRI(autorizacionProveedorSRI);
/*  906:     */         }
/*  907:     */       }
/*  908:     */     }
/*  909:     */     catch (ExcepcionAS2 e)
/*  910:     */     {
/*  911:1029 */       e.printStackTrace();
/*  912:1030 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  913:     */     }
/*  914:     */     catch (Exception e)
/*  915:     */     {
/*  916:1033 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  917:1034 */       e.printStackTrace();
/*  918:     */     }
/*  919:1036 */     return null;
/*  920:     */   }
/*  921:     */   
/*  922:     */   public DataTable getDtReembolsoGastos()
/*  923:     */   {
/*  924:1041 */     return this.dtReembolsoGastos;
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void setDtReembolsoGastos(DataTable dtReembolsoGastos)
/*  928:     */   {
/*  929:1045 */     this.dtReembolsoGastos = dtReembolsoGastos;
/*  930:     */   }
/*  931:     */   
/*  932:     */   public List<ReembolsoGastos> getListaReembolsoGastosPrincipal()
/*  933:     */   {
/*  934:1049 */     if (this.listaReembolsoGastosPrincipal == null) {
/*  935:1050 */       this.listaReembolsoGastosPrincipal = new ArrayList();
/*  936:     */     }
/*  937:1052 */     return this.listaReembolsoGastosPrincipal;
/*  938:     */   }
/*  939:     */   
/*  940:     */   public void setListaReembolsoGastosPrincipal(List<ReembolsoGastos> listaReembolsoGastosPrincipal)
/*  941:     */   {
/*  942:1056 */     this.listaReembolsoGastosPrincipal = listaReembolsoGastosPrincipal;
/*  943:     */   }
/*  944:     */   
/*  945:     */   public BigDecimal getTotalBaseImponibleDiferenteCero()
/*  946:     */   {
/*  947:1060 */     return this.totalBaseImponibleDiferenteCero;
/*  948:     */   }
/*  949:     */   
/*  950:     */   public void setTotalBaseImponibleDiferenteCero(BigDecimal totalBaseImponibleDiferenteCero)
/*  951:     */   {
/*  952:1064 */     this.totalBaseImponibleDiferenteCero = totalBaseImponibleDiferenteCero;
/*  953:     */   }
/*  954:     */   
/*  955:     */   public BigDecimal getTotalBaseImponibleNoObjetoIva()
/*  956:     */   {
/*  957:1068 */     return this.totalBaseImponibleNoObjetoIva;
/*  958:     */   }
/*  959:     */   
/*  960:     */   public void setTotalBaseImponibleNoObjetoIva(BigDecimal totalBaseImponibleNoObjetoIva)
/*  961:     */   {
/*  962:1072 */     this.totalBaseImponibleNoObjetoIva = totalBaseImponibleNoObjetoIva;
/*  963:     */   }
/*  964:     */   
/*  965:     */   public BigDecimal getTotalBaseImponibleTarifaCero()
/*  966:     */   {
/*  967:1076 */     return this.totalBaseImponibleTarifaCero;
/*  968:     */   }
/*  969:     */   
/*  970:     */   public void setTotalBaseImponibleTarifaCero(BigDecimal totalBaseImponibleTarifaCero)
/*  971:     */   {
/*  972:1080 */     this.totalBaseImponibleTarifaCero = totalBaseImponibleTarifaCero;
/*  973:     */   }
/*  974:     */   
/*  975:     */   public List<TipoIdentificacion> getListaTipoIdentificacionCombo()
/*  976:     */   {
/*  977:1084 */     HashMap<String, String> filters = new HashMap();
/*  978:1085 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  979:1086 */     if (this.listaTipoIdentificacionCombo == null) {
/*  980:1087 */       this.listaTipoIdentificacionCombo = this.servicioTipoIdentificacion.obtenerListaCombo("codigo", true, filters);
/*  981:     */     }
/*  982:1089 */     return this.listaTipoIdentificacionCombo;
/*  983:     */   }
/*  984:     */   
/*  985:     */   public void setListaTipoIdentificacionCombo(List<TipoIdentificacion> listaTipoIdentificacionCombo)
/*  986:     */   {
/*  987:1093 */     this.listaTipoIdentificacionCombo = listaTipoIdentificacionCombo;
/*  988:     */   }
/*  989:     */   
/*  990:     */   public BigDecimal getTotalBaseExentaIva()
/*  991:     */   {
/*  992:1097 */     return this.totalBaseExentaIva;
/*  993:     */   }
/*  994:     */   
/*  995:     */   public void setTotalBaseExentaIva(BigDecimal totalBaseExentaIva)
/*  996:     */   {
/*  997:1101 */     this.totalBaseExentaIva = totalBaseExentaIva;
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public BigDecimal getTotalMontoIva()
/* 1001:     */   {
/* 1002:1105 */     return this.totalMontoIva;
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public void setTotalMontoIva(BigDecimal totalMontoIva)
/* 1006:     */   {
/* 1007:1109 */     this.totalMontoIva = totalMontoIva;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public BigDecimal getTotalMontoIce()
/* 1011:     */   {
/* 1012:1113 */     return this.totalMontoIce;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public void setTotalMontoIce(BigDecimal totalMontoIce)
/* 1016:     */   {
/* 1017:1117 */     this.totalMontoIce = totalMontoIce;
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public void cargarListaTipoComprobanteSRIReembolso()
/* 1021:     */   {
/* 1022:1121 */     ReembolsoGastos rg = (ReembolsoGastos)this.dtReembolsoGastos.getRowData();
/* 1023:1122 */     if ((rg != null) && (rg.getTipoIdentificacion() != null))
/* 1024:     */     {
/* 1025:1123 */       List<TipoComprobanteSRI> listaTipoComprobanteSRI = this.servicioSRI.buscarPorTipoIdentificacion(rg.getTipoIdentificacion());
/* 1026:1124 */       rg.setListaTipoComprobanteSRI(listaTipoComprobanteSRI);
/* 1027:     */     }
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public StreamedContent getXMLSRI()
/* 1031:     */   {
/* 1032:1129 */     if ((this.facturaProveedor != null) && (this.facturaProveedor.getId() != 0) && (this.facturaProveedor.getFacturaProveedorSRI() != null))
/* 1033:     */     {
/* 1034:1130 */       String pathSRI = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri";
/* 1035:     */       
/* 1036:     */ 
/* 1037:1133 */       String pathDocumento = pathSRI + File.separator + "documentos_electronicos" + File.separator + TipoDocumentoElectronicoEnum.RETENCION.toString();
/* 1038:1134 */       String pathArchivoAutorizado = pathDocumento + File.separator + "autorizado";
/* 1039:     */       
/* 1040:     */ 
/* 1041:     */ 
/* 1042:1138 */       String nombreArchivo = this.facturaProveedor.getFacturaProveedorSRI().getEstablecimientoRetencion() + "-" + this.facturaProveedor.getFacturaProveedorSRI().getPuntoEmisionRetencion() + "-" + this.facturaProveedor.getFacturaProveedorSRI().getNumeroRetencion() + "-" + this.facturaProveedor.getFacturaProveedorSRI().getClaveAcceso() + ".xml";
/* 1043:1139 */       pathArchivoAutorizado = pathArchivoAutorizado + File.separator + nombreArchivo;
/* 1044:     */       try
/* 1045:     */       {
/* 1046:1141 */         return FuncionesUtiles.descargarArchivo(pathArchivoAutorizado, "application/xls", nombreArchivo);
/* 1047:     */       }
/* 1048:     */       catch (FileNotFoundException e)
/* 1049:     */       {
/* 1050:1143 */         e.printStackTrace();
/* 1051:1144 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1052:1145 */         return null;
/* 1053:     */       }
/* 1054:     */     }
/* 1055:1148 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1056:1149 */     return null;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public Boolean getIndicadorEditarPrecio()
/* 1060:     */   {
/* 1061:1154 */     boolean indicadorEditarPrecio = true;
/* 1062:1155 */     this.indicadorParametroEditarPrecioPedido = getIndicadorParametroEditarPrecioPedido();
/* 1063:1156 */     if (this.indicadorParametroEditarPrecioPedido.booleanValue()) {
/* 1064:1157 */       return Boolean.valueOf(true);
/* 1065:     */     }
/* 1066:1160 */     if (((this.facturaProveedor != null) && (this.facturaProveedor.getRecepcionProveedor() != null) && 
/* 1067:1161 */       (this.facturaProveedor.getRecepcionProveedor().getPedidoProveedor() != null)) || (getPedidoProveedor() != null)) {
/* 1068:1162 */       indicadorEditarPrecio = false;
/* 1069:     */     }
/* 1070:1164 */     return Boolean.valueOf(indicadorEditarPrecio);
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public Boolean getIndicadorParametroEditarPrecioPedido()
/* 1074:     */   {
/* 1075:1168 */     if (this.indicadorParametroEditarPrecioPedido == null) {
/* 1076:1169 */       this.indicadorParametroEditarPrecioPedido = ParametrosSistema.getEditarPrecioFacturaPedidoProveedor(AppUtil.getOrganizacion().getId());
/* 1077:     */     }
/* 1078:1171 */     return this.indicadorParametroEditarPrecioPedido;
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public BigDecimal getTotalDescuentoImpuesto()
/* 1082:     */   {
/* 1083:1175 */     return this.totalDescuentoImpuesto;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public void setTotalDescuentoImpuesto(BigDecimal totalDescuentoImpuesto)
/* 1087:     */   {
/* 1088:1179 */     this.totalDescuentoImpuesto = totalDescuentoImpuesto;
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public boolean isRenderProveedorNuevo()
/* 1092:     */   {
/* 1093:1183 */     return this.renderProveedorNuevo;
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   public void setRenderProveedorNuevo(boolean renderProveedorNuevo)
/* 1097:     */   {
/* 1098:1187 */     this.renderProveedorNuevo = renderProveedorNuevo;
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   public String getIdentificacionProveedor()
/* 1102:     */   {
/* 1103:1191 */     return this.identificacionProveedor;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public void setIdentificacionProveedor(String identificacionProveedor)
/* 1107:     */   {
/* 1108:1195 */     this.identificacionProveedor = identificacionProveedor;
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public TipoIdentificacion getTipoIdentificacionProveedor()
/* 1112:     */   {
/* 1113:1199 */     return this.tipoIdentificacionProveedor;
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public void setTipoIdentificacionProveedor(TipoIdentificacion tipoIdentificacionProveedor)
/* 1117:     */   {
/* 1118:1203 */     this.tipoIdentificacionProveedor = tipoIdentificacionProveedor;
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/* 1122:     */   {
/* 1123:1207 */     if (this.listaTipoIdentificacion == null) {
/* 1124:1208 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 1125:     */     }
/* 1126:1210 */     return this.listaTipoIdentificacion;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/* 1130:     */   {
/* 1131:1214 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public String getNombreProveedor()
/* 1135:     */   {
/* 1136:1218 */     return this.nombreProveedor;
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public void setNombreProveedor(String nombreProveedor)
/* 1140:     */   {
/* 1141:1222 */     this.nombreProveedor = nombreProveedor;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public String getDireccionProveedor()
/* 1145:     */   {
/* 1146:1226 */     return this.direccionProveedor;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public void setDireccionProveedor(String direccionProveedor)
/* 1150:     */   {
/* 1151:1230 */     this.direccionProveedor = direccionProveedor;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public String getTelefonoProveedor()
/* 1155:     */   {
/* 1156:1234 */     return this.telefonoProveedor;
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public void setTelefonoProveedor(String telefonoProveedor)
/* 1160:     */   {
/* 1161:1238 */     this.telefonoProveedor = telefonoProveedor;
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public Ciudad getCiudad()
/* 1165:     */   {
/* 1166:1242 */     return this.ciudad;
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public void setCiudad(Ciudad ciudad)
/* 1170:     */   {
/* 1171:1246 */     this.ciudad = ciudad;
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public String getEmail()
/* 1175:     */   {
/* 1176:1250 */     return this.email;
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   public void setEmail(String email)
/* 1180:     */   {
/* 1181:1254 */     this.email = email;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   public boolean isRenderAutorizacionNuevo()
/* 1185:     */   {
/* 1186:1258 */     return this.renderAutorizacionNuevo;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public void setRenderAutorizacionNuevo(boolean renderAutorizacionNuevo)
/* 1190:     */   {
/* 1191:1262 */     this.renderAutorizacionNuevo = renderAutorizacionNuevo;
/* 1192:     */   }
/* 1193:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.FacturaProveedorBean
 * JD-Core Version:    0.7.0.1
 */