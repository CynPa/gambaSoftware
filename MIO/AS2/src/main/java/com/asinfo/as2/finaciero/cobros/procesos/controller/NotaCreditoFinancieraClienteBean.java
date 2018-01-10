/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   7:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   8:    */ import com.asinfo.as2.controller.LanguageController;
/*   9:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*  10:    */ import com.asinfo.as2.dao.CuentaPorCobrarDao;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  12:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  13:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  14:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  15:    */ import com.asinfo.as2.entities.Documento;
/*  16:    */ import com.asinfo.as2.entities.Empresa;
/*  17:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  18:    */ import com.asinfo.as2.entities.Impuesto;
/*  19:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*  20:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  21:    */ import com.asinfo.as2.entities.NotaFacturaCliente;
/*  22:    */ import com.asinfo.as2.entities.Organizacion;
/*  23:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  24:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  25:    */ import com.asinfo.as2.entities.Subempresa;
/*  26:    */ import com.asinfo.as2.entities.Sucursal;
/*  27:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  28:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  29:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  30:    */ import com.asinfo.as2.enumeraciones.ExportOption;
/*  31:    */ import com.asinfo.as2.enumeraciones.TipoImpuestoEnum;
/*  32:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  33:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  34:    */ import com.asinfo.as2.finaciero.cobros.reportes.ReporteNotaCreditoFinancieraBean;
/*  35:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*  36:    */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*  37:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  38:    */ import com.asinfo.as2.util.AppUtil;
/*  39:    */ import com.asinfo.as2.util.RutaArchivo;
/*  40:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  41:    */ import com.asinfo.as2.utils.JsfUtil;
/*  42:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  43:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  44:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  45:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  46:    */ import java.io.File;
/*  47:    */ import java.io.FileNotFoundException;
/*  48:    */ import java.math.BigDecimal;
/*  49:    */ import java.math.RoundingMode;
/*  50:    */ import java.util.ArrayList;
/*  51:    */ import java.util.Date;
/*  52:    */ import java.util.HashMap;
/*  53:    */ import java.util.Iterator;
/*  54:    */ import java.util.List;
/*  55:    */ import java.util.Map;
/*  56:    */ import javax.annotation.PostConstruct;
/*  57:    */ import javax.ejb.EJB;
/*  58:    */ import javax.faces.bean.ManagedBean;
/*  59:    */ import javax.faces.bean.ManagedProperty;
/*  60:    */ import javax.faces.bean.ViewScoped;
/*  61:    */ import javax.faces.component.html.HtmlSelectOneMenu;
/*  62:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  63:    */ import org.apache.log4j.Logger;
/*  64:    */ import org.primefaces.component.datatable.DataTable;
/*  65:    */ import org.primefaces.event.FileUploadEvent;
/*  66:    */ import org.primefaces.event.SelectEvent;
/*  67:    */ import org.primefaces.model.LazyDataModel;
/*  68:    */ import org.primefaces.model.SortOrder;
/*  69:    */ import org.primefaces.model.StreamedContent;
/*  70:    */ 
/*  71:    */ @ManagedBean
/*  72:    */ @ViewScoped
/*  73:    */ public class NotaCreditoFinancieraClienteBean
/*  74:    */   extends PageControllerAS2
/*  75:    */ {
/*  76:    */   private static final long serialVersionUID = 7447438187503589426L;
/*  77:    */   @EJB
/*  78:    */   protected ServicioEmpresa servicioEmpresa;
/*  79:    */   @EJB
/*  80:    */   protected ServicioDocumento servicioDocumento;
/*  81:    */   @EJB
/*  82:    */   protected ServicioFacturaCliente servicioFacturaCliente;
/*  83:    */   @EJB
/*  84:    */   protected ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  85:    */   @EJB
/*  86:    */   protected ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  87:    */   @EJB
/*  88:    */   protected ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  89:    */   @EJB
/*  90:    */   protected CuentaPorCobrarDao cuentaPorCobrarDao;
/*  91:    */   @EJB
/*  92:    */   protected transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  93:    */   @EJB
/*  94:    */   protected transient ServicioSucursal servicioSucursal;
/*  95:    */   protected FacturaCliente notaCreditoCliente;
/*  96:    */   protected FacturaClienteSRI facturaClienteSRI;
/*  97:    */   protected LazyDataModel<FacturaCliente> listaNotaCreditoCliente;
/*  98:    */   protected List<Documento> listaDocumento;
/*  99:    */   protected List<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente;
/* 100:119 */   protected List<DireccionEmpresa> listaDireccionEmpresa = new ArrayList();
/* 101:120 */   protected BigDecimal valorNotaCredito = BigDecimal.ZERO;
/* 102:    */   protected NotaFacturaCliente notaFacturaCliente;
/* 103:    */   protected Integer idNotaCreditoCliente;
/* 104:    */   protected String mails;
/* 105:    */   private DataTable dtNotaCreditoCliente;
/* 106:    */   private DataTable dtDetalleNotaCreditoCliente;
/* 107:    */   private DataTable dtImpuestoDetalleNC;
/* 108:    */   private DataTable dtDetalleNotaFacturaCliente;
/* 109:    */   private BigDecimal valorBloqueado;
/* 110:    */   private List<Subempresa> listaSubempresa;
/* 111:    */   @ManagedProperty("#{reporteNotaCreditoFinancieraBean}")
/* 112:    */   private ReporteNotaCreditoFinancieraBean reporteNotaCreditoFinancieraBean;
/* 113:    */   
/* 114:    */   @PostConstruct
/* 115:    */   public void init()
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:146 */       limpiar();
/* 120:147 */       this.listaNotaCreditoCliente = new LazyDataModel()
/* 121:    */       {
/* 122:    */         private static final long serialVersionUID = 4780083578367601484L;
/* 123:    */         
/* 124:    */         public List<FacturaCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 125:    */         {
/* 126:154 */           if (NotaCreditoFinancieraClienteBean.this.idNotaCreditoCliente != null)
/* 127:    */           {
/* 128:156 */             filters.put("idFacturaCliente", "" + NotaCreditoFinancieraClienteBean.this.idNotaCreditoCliente);
/* 129:157 */             NotaCreditoFinancieraClienteBean.this.idNotaCreditoCliente = null;
/* 130:    */           }
/* 131:159 */           List<FacturaCliente> lista = new ArrayList();
/* 132:    */           
/* 133:161 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 134:162 */           filters.put("documento.documentoBase", DocumentoBase.NOTA_CREDITO_CLIENTE.toString());
/* 135:163 */           lista = NotaCreditoFinancieraClienteBean.this.servicioNotaCreditoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 136:    */           
/* 137:165 */           NotaCreditoFinancieraClienteBean.this.listaNotaCreditoCliente.setRowCount(NotaCreditoFinancieraClienteBean.this.servicioNotaCreditoCliente.contarPorCriterio(filters));
/* 138:166 */           return lista;
/* 139:    */         }
/* 140:    */       };
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:172 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 145:173 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String editar()
/* 150:    */   {
/* 151:184 */     if (this.notaCreditoCliente != null) {
/* 152:    */       try
/* 153:    */       {
/* 154:186 */         this.notaCreditoCliente = this.servicioFacturaCliente.cargarDetalle(this.notaCreditoCliente.getId());
/* 155:187 */         this.notaCreditoCliente.setListaNotaFacturaCliente(this.servicioFacturaCliente.cargarListaNotaFacturaCliente(this.notaCreditoCliente));
/* 156:188 */         if ((!this.notaCreditoCliente.getEstado().equals(Estado.RECHAZADO_SRI)) && (!this.notaCreditoCliente.getEstado().equals(Estado.ELABORADO)) && 
/* 157:189 */           (!this.notaCreditoCliente.getEstado().equals(Estado.APROBADO_PARCIAL)))
/* 158:    */         {
/* 159:190 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 160:    */         }
/* 161:    */         else
/* 162:    */         {
/* 163:192 */           actualizarDocumento();
/* 164:193 */           cargarDirecciones();
/* 165:    */           
/* 166:195 */           setEditado(true);
/* 167:    */         }
/* 168:    */       }
/* 169:    */       catch (Exception e)
/* 170:    */       {
/* 171:198 */         addErrorMessage(e.getMessage());
/* 172:    */       }
/* 173:    */     } else {
/* 174:201 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 175:    */     }
/* 176:203 */     return "";
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String guardar()
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:216 */       this.servicioNotaCreditoCliente.guardar(this.notaCreditoCliente);
/* 184:    */       
/* 185:218 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 186:219 */       if (this.notaCreditoCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() != null)
/* 187:    */       {
/* 188:220 */         this.reporteNotaCreditoFinancieraBean.setIndicadorImpreso(false);
/* 189:221 */         this.reporteNotaCreditoFinancieraBean.setFacturaCliente(this.notaCreditoCliente);
/* 190:222 */         this.reporteNotaCreditoFinancieraBean.setExportOption(ExportOption.PRINTER);
/* 191:223 */         this.reporteNotaCreditoFinancieraBean.setNumeroImpresiones(AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/* 192:224 */           .getNumeroCopiasDocumentoTributario());
/* 193:225 */         this.reporteNotaCreditoFinancieraBean.execute();
/* 194:    */       }
/* 195:227 */       limpiar();
/* 196:    */     }
/* 197:    */     catch (ExcepcionAS2Financiero e)
/* 198:    */     {
/* 199:230 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion());
/* 200:231 */       if (e.getMessage() != null) {
/* 201:232 */         strMensaje = strMensaje + " " + e.getMessage();
/* 202:    */       }
/* 203:234 */       addInfoMessage(strMensaje);
/* 204:235 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 205:    */     }
/* 206:    */     catch (ExcepcionAS2 e)
/* 207:    */     {
/* 208:238 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion());
/* 209:239 */       if (e.getMessage() != null) {
/* 210:240 */         strMensaje = strMensaje + " " + e.getMessage();
/* 211:    */       }
/* 212:242 */       addInfoMessage(strMensaje);
/* 213:243 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 214:    */     }
/* 215:    */     catch (AS2Exception e)
/* 216:    */     {
/* 217:246 */       JsfUtil.addErrorMessage(e, "");
/* 218:247 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 219:    */     }
/* 220:    */     catch (Exception e)
/* 221:    */     {
/* 222:249 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 223:250 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 224:    */     }
/* 225:254 */     return "";
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isGuardarAjax()
/* 229:    */   {
/* 230:259 */     return false;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void prorratearDetalle()
/* 234:    */   {
/* 235:264 */     BigDecimal valorFacturaTotal = BigDecimal.ZERO;
/* 236:265 */     BigDecimal valorTotal = BigDecimal.ZERO;
/* 237:266 */     BigDecimal valorPorLinea = BigDecimal.ZERO;
/* 238:268 */     for (Iterator localIterator = getDetalleFacturaCliente().iterator(); localIterator.hasNext();)
/* 239:    */     {
/* 240:268 */       detalleNotaCredito = (DetalleFacturaCliente)localIterator.next();
/* 241:269 */       valorFacturaTotal = valorFacturaTotal.add(detalleNotaCredito.getDetalleFacturaClientePadre().getPrecioLinea());
/* 242:    */     }
/* 243:    */     DetalleFacturaCliente detalleNotaCredito;
/* 244:272 */     valorPorLinea = valorFacturaTotal == BigDecimal.ZERO ? valorFacturaTotal : this.valorNotaCredito.divide(valorFacturaTotal, 24, RoundingMode.HALF_UP);
/* 245:    */     
/* 246:274 */     DetalleFacturaCliente dnc = null;
/* 247:275 */     for (DetalleFacturaCliente detalleNotaCredito : getDetalleFacturaCliente())
/* 248:    */     {
/* 249:276 */       detalleNotaCredito.setPrecio(valorPorLinea.multiply(detalleNotaCredito.getDetalleFacturaClientePadre().getPrecioLinea()).setScale(2, RoundingMode.HALF_UP));
/* 250:    */       
/* 251:278 */       valorTotal = valorTotal.add(detalleNotaCredito.getPrecio());
/* 252:279 */       dnc = detalleNotaCredito;
/* 253:    */     }
/* 254:282 */     BigDecimal diferencia = valorTotal.subtract(this.valorNotaCredito);
/* 255:284 */     if (!diferencia.equals(BigDecimal.ZERO)) {
/* 256:285 */       if (valorTotal.compareTo(this.valorNotaCredito) == 1) {
/* 257:286 */         dnc.setPrecio(dnc.getPrecio().subtract(diferencia));
/* 258:    */       } else {
/* 259:288 */         dnc.setPrecio(dnc.getPrecio().add(diferencia.abs()));
/* 260:    */       }
/* 261:    */     }
/* 262:291 */     totalizar();
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String eliminar()
/* 266:    */   {
/* 267:302 */     if (this.notaCreditoCliente.getId() > 0) {
/* 268:    */       try
/* 269:    */       {
/* 270:304 */         this.servicioNotaCreditoCliente.anular(this.notaCreditoCliente);
/* 271:305 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 272:    */       }
/* 273:    */       catch (ExcepcionAS2Ventas e)
/* 274:    */       {
/* 275:307 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 276:308 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE ExcepcionAS2Ventas", e);
/* 277:309 */         e.printStackTrace();
/* 278:    */       }
/* 279:    */       catch (ExcepcionAS2Financiero e)
/* 280:    */       {
/* 281:311 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 282:312 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE ExcepcionAS2Financiero", e);
/* 283:313 */         e.printStackTrace();
/* 284:    */       }
/* 285:    */       catch (Exception e)
/* 286:    */       {
/* 287:315 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 288:316 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE Exception", e);
/* 289:317 */         e.printStackTrace();
/* 290:    */       }
/* 291:    */     } else {
/* 292:320 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 293:    */     }
/* 294:323 */     return "";
/* 295:    */   }
/* 296:    */   
/* 297:    */   public String limpiar()
/* 298:    */   {
/* 299:334 */     setEditado(false);
/* 300:    */     
/* 301:336 */     crearNotaCredito();
/* 302:    */     
/* 303:338 */     return "";
/* 304:    */   }
/* 305:    */   
/* 306:    */   public String crear()
/* 307:    */   {
/* 308:349 */     if (AppUtil.getPuntoDeVenta().isIndicadorPos())
/* 309:    */     {
/* 310:350 */       addInfoMessage(getLanguageController().getMensaje("msg_error_facturar_pos"));
/* 311:351 */       setEditado(false);
/* 312:352 */       return "";
/* 313:    */     }
/* 314:354 */     limpiar();
/* 315:355 */     setEditado(true);
/* 316:356 */     return "";
/* 317:    */   }
/* 318:    */   
/* 319:    */   private void crearNotaCredito()
/* 320:    */   {
/* 321:363 */     this.notaCreditoCliente = new FacturaCliente();
/* 322:364 */     this.notaCreditoCliente.setNumero("");
/* 323:365 */     this.notaCreditoCliente.setFecha(new Date());
/* 324:366 */     this.notaCreditoCliente.setEstado(Estado.ELABORADO);
/* 325:367 */     this.notaCreditoCliente.setNumeroCuotas(1);
/* 326:    */     
/* 327:369 */     this.notaCreditoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 328:370 */     this.notaCreditoCliente.setSucursal(AppUtil.getSucursal());
/* 329:    */     
/* 330:372 */     Documento documento = null;
/* 331:373 */     if (!getListaDocumento().isEmpty())
/* 332:    */     {
/* 333:374 */       documento = (Documento)getListaDocumento().get(0);
/* 334:375 */       this.notaCreditoCliente.setDocumento(documento);
/* 335:376 */       actualizarDocumento();
/* 336:    */     }
/* 337:    */     else
/* 338:    */     {
/* 339:378 */       this.notaCreditoCliente.setDocumento(new Documento());
/* 340:    */     }
/* 341:381 */     this.facturaClienteSRI = new FacturaClienteSRI();
/* 342:382 */     this.facturaClienteSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 343:383 */     this.facturaClienteSRI.setEstado(Estado.ELABORADO);
/* 344:384 */     this.facturaClienteSRI.setNumero("0");
/* 345:385 */     this.facturaClienteSRI.setFacturaCliente(this.notaCreditoCliente);
/* 346:    */     
/* 347:387 */     this.notaCreditoCliente.setFacturaClienteSRI(this.facturaClienteSRI);
/* 348:    */     
/* 349:389 */     this.valorNotaCredito = BigDecimal.ZERO;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public String cargarDatos()
/* 353:    */   {
/* 354:399 */     return "";
/* 355:    */   }
/* 356:    */   
/* 357:    */   public String actualizarDocumento()
/* 358:    */   {
/* 359:    */     try
/* 360:    */     {
/* 361:411 */       if (isIndicadorAutoimpresor()) {
/* 362:412 */         this.notaCreditoCliente.setAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(this.servicioAutorizacionAutoimpresorSRI
/* 363:413 */           .obtenerAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(this.notaCreditoCliente.getDocumento().getDocumentoBase(), 
/* 364:414 */           AppUtil.getPuntoDeVenta()));
/* 365:    */       }
/* 366:416 */       if (this.notaCreditoCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() == null) {
/* 367:417 */         if (this.notaCreditoCliente.getDocumento().isIndicadorDocumentoTributario())
/* 368:    */         {
/* 369:419 */           PuntoDeVenta puntoDeVenta = this.servicioFacturaCliente.cargarPuntoVenta(this.notaCreditoCliente);
/* 370:420 */           this.servicioNotaCreditoCliente.cargarSecuencia(this.notaCreditoCliente, puntoDeVenta);
/* 371:    */         }
/* 372:    */         else
/* 373:    */         {
/* 374:422 */           this.servicioNotaCreditoCliente.cargarSecuencia(this.notaCreditoCliente, null);
/* 375:    */         }
/* 376:    */       }
/* 377:    */     }
/* 378:    */     catch (ExcepcionAS2 e)
/* 379:    */     {
/* 380:426 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 381:    */     }
/* 382:429 */     setSecuenciaEditable(!this.notaCreditoCliente.getDocumento().isIndicadorBloqueoSecuencia());
/* 383:    */     
/* 384:431 */     return "";
/* 385:    */   }
/* 386:    */   
/* 387:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 388:    */   {
/* 389:435 */     return this.servicioEmpresa.autocompletarClientes(consulta, true);
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void seleccionarUltimaFactura()
/* 393:    */   {
/* 394:440 */     FacturaCliente facturaCliente = this.servicioFacturaCliente.obtenerUltimaFacturaAutorizadaPorCliente(AppUtil.getOrganizacion().getId(), this.notaCreditoCliente
/* 395:441 */       .getEmpresa(), false, null);
/* 396:442 */     if (facturaCliente != null) {
/* 397:443 */       cargarDetalles(facturaCliente);
/* 398:    */     }
/* 399:    */   }
/* 400:    */   
/* 401:    */   public List<FacturaCliente> autocompletarFacturas(String consulta)
/* 402:    */   {
/* 403:448 */     Map<String, String> filters = new HashMap();
/* 404:449 */     List<FacturaCliente> lista = new ArrayList();
/* 405:451 */     if (getNotaCreditoCliente().getEmpresa() != null)
/* 406:    */     {
/* 407:452 */       filters.put("empresa.idEmpresa", "" + getNotaCreditoCliente().getEmpresa().getId());
/* 408:453 */       if ((consulta != null) && (!consulta.isEmpty()))
/* 409:    */       {
/* 410:454 */         filters.put("OR~01~referencia2", "%" + consulta + "%");
/* 411:455 */         filters.put("OR~01~numero", "%" + consulta + "%");
/* 412:    */       }
/* 413:458 */       filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/* 414:459 */       if (!ParametrosSistema.getRealizaNotasCreditoAFacturaNoAutorizada(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 415:460 */         filters.put("facturaClienteSRI.autorizacion", "!=0000000000");
/* 416:    */       }
/* 417:461 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 418:    */       
/* 419:463 */       lista = this.servicioFacturaCliente.obtenerListaCombo("fecha", false, filters);
/* 420:    */     }
/* 421:    */     else
/* 422:    */     {
/* 423:465 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 424:    */     }
/* 425:467 */     return lista;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void totalizar()
/* 429:    */   {
/* 430:    */     try
/* 431:    */     {
/* 432:472 */       this.servicioNotaCreditoCliente.totalizar(this.notaCreditoCliente);
/* 433:    */     }
/* 434:    */     catch (ExcepcionAS2Ventas e)
/* 435:    */     {
/* 436:474 */       LOG.error(e.getErrorMessage(e));
/* 437:    */     }
/* 438:    */     catch (Exception e)
/* 439:    */     {
/* 440:476 */       LOG.error(e);
/* 441:477 */       e.printStackTrace();
/* 442:    */     }
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void actualizarCliente(SelectEvent event)
/* 446:    */   {
/* 447:487 */     Empresa empresa = (Empresa)event.getObject();
/* 448:488 */     this.notaCreditoCliente.setEmpresa(empresa);
/* 449:489 */     this.notaCreditoCliente.setFacturaClientePadre(null);
/* 450:    */     
/* 451:491 */     this.notaCreditoCliente.setEmail(this.servicioEmpresa.cargarMails(empresa, DocumentoBase.NOTA_CREDITO_CLIENTE));
/* 452:    */     
/* 453:493 */     cargarDirecciones();
/* 454:494 */     cargarSubempresas();
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void cargarDirecciones()
/* 458:    */   {
/* 459:507 */     if (this.notaCreditoCliente.getSubempresa() != null) {
/* 460:508 */       setListaDireccionEmpresa(this.servicioEmpresa.obtenerListaComboDirecciones(this.notaCreditoCliente.getSubempresa().getEmpresa().getId()));
/* 461:509 */     } else if (this.notaCreditoCliente.getFacturaClientePadre() != null) {
/* 462:510 */       setListaDireccionEmpresa(this.servicioEmpresa.obtenerListaComboDirecciones(this.notaCreditoCliente.getFacturaClientePadre().getEmpresa().getId()));
/* 463:    */     } else {
/* 464:512 */       setListaDireccionEmpresa(this.servicioEmpresa.obtenerListaComboDirecciones(this.notaCreditoCliente.getEmpresa().getId()));
/* 465:    */     }
/* 466:514 */     for (DireccionEmpresa direccion : getListaDireccionEmpresa()) {
/* 467:515 */       if (direccion.isIndicadorDireccionPrincipal())
/* 468:    */       {
/* 469:516 */         this.notaCreditoCliente.setDireccionEmpresa(direccion);
/* 470:517 */         break;
/* 471:    */       }
/* 472:    */     }
/* 473:520 */     if ((this.notaCreditoCliente.getDireccionEmpresa() != null) && (this.listaDireccionEmpresa.size() > 0)) {
/* 474:521 */       this.notaCreditoCliente.setDireccionEmpresa((DireccionEmpresa)this.listaDireccionEmpresa.get(0));
/* 475:    */     }
/* 476:    */   }
/* 477:    */   
/* 478:    */   public void cargarSubempresas()
/* 479:    */   {
/* 480:526 */     if (this.notaCreditoCliente.getEmpresa() != null) {
/* 481:527 */       this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.notaCreditoCliente.getEmpresa().getId());
/* 482:    */     }
/* 483:    */   }
/* 484:    */   
/* 485:    */   public List<DetalleFacturaCliente> getDetalleFacturaCliente()
/* 486:    */   {
/* 487:537 */     List<DetalleFacturaCliente> detalle = new ArrayList();
/* 488:538 */     for (DetalleFacturaCliente dfc : this.notaCreditoCliente.getListaDetalleFacturaCliente()) {
/* 489:539 */       if (!dfc.isEliminado()) {
/* 490:540 */         detalle.add(dfc);
/* 491:    */       }
/* 492:    */     }
/* 493:543 */     return detalle;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public List<ImpuestoProductoFacturaCliente> getListaImpuestoProductoNC()
/* 497:    */   {
/* 498:553 */     List<ImpuestoProductoFacturaCliente> listaImpuestoProductoFacturaClientes = new ArrayList();
/* 499:555 */     for (DetalleFacturaCliente dfc : this.notaCreditoCliente.getListaDetalleFacturaCliente()) {
/* 500:557 */       for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente()) {
/* 501:558 */         if (!ipfc.isEliminado()) {
/* 502:559 */           listaImpuestoProductoFacturaClientes.add(ipfc);
/* 503:    */         }
/* 504:    */       }
/* 505:    */     }
/* 506:565 */     return listaImpuestoProductoFacturaClientes;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public void cargarDetalles(FacturaCliente facturaCliente)
/* 510:    */   {
/* 511:    */     try
/* 512:    */     {
/* 513:570 */       if (getNotaCreditoCliente().getId() == 0)
/* 514:    */       {
/* 515:571 */         this.notaCreditoCliente.setListaDetalleFacturaCliente(new ArrayList());
/* 516:572 */         this.notaCreditoCliente.setFacturaClientePadre(facturaCliente);
/* 517:573 */         this.notaCreditoCliente = this.servicioNotaCreditoCliente.cargarDetalleFactura(facturaCliente, this.notaCreditoCliente);
/* 518:    */         
/* 519:    */ 
/* 520:576 */         this.valorBloqueado = this.cuentaPorCobrarDao.valorBloqueado(facturaCliente.getIdFacturaCliente());
/* 521:578 */         for (DetalleFacturaCliente detalleFacturaCliente : this.notaCreditoCliente.getListaDetalleFacturaCliente())
/* 522:    */         {
/* 523:579 */           detalleFacturaCliente.setCantidad(BigDecimal.valueOf(1L));
/* 524:580 */           if (!detalleFacturaCliente.isIndicadorPorcentajeIce())
/* 525:    */           {
/* 526:581 */             detalleFacturaCliente.setCodigoIce("");
/* 527:582 */             detalleFacturaCliente.setIce(BigDecimal.ZERO);
/* 528:    */           }
/* 529:584 */           detalleFacturaCliente.setPrecio(BigDecimal.ZERO);
/* 530:585 */           for (int i = 0; i < detalleFacturaCliente.getListaImpuestoProductoFacturaCliente().size(); i++) {
/* 531:587 */             if (((ImpuestoProductoFacturaCliente)detalleFacturaCliente.getListaImpuestoProductoFacturaCliente().get(i)).getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_CANTIDAD_UNIDADES))
/* 532:    */             {
/* 533:588 */               detalleFacturaCliente.getListaImpuestoProductoFacturaCliente().remove(i);
/* 534:589 */               i--;
/* 535:    */             }
/* 536:    */           }
/* 537:    */         }
/* 538:593 */         this.notaCreditoCliente.setFacturaClienteSRI(this.facturaClienteSRI);
/* 539:594 */         this.facturaClienteSRI.setFacturaCliente(this.notaCreditoCliente);
/* 540:595 */         this.notaCreditoCliente.setReferencia8(facturaCliente.getReferencia8());
/* 541:596 */         this.notaCreditoCliente.setReferencia9(facturaCliente.getReferencia9());
/* 542:597 */         this.notaCreditoCliente.setDescripcion(facturaCliente.getDescripcion());
/* 543:598 */         totalizar();
/* 544:    */       }
/* 545:    */     }
/* 546:    */     catch (ExcepcionAS2Compras e)
/* 547:    */     {
/* 548:603 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 549:    */     }
/* 550:    */     catch (ExcepcionAS2 e)
/* 551:    */     {
/* 552:606 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 553:    */     }
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void cargarDetalleNotaCreditoCliente(SelectEvent event)
/* 557:    */   {
/* 558:616 */     FacturaCliente facturaCliente = (FacturaCliente)event.getObject();
/* 559:617 */     cargarDetalles(facturaCliente);
/* 560:    */   }
/* 561:    */   
/* 562:    */   public String getDirectorioDescarga()
/* 563:    */   {
/* 564:622 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "nota_credito_financiera_cliente");
/* 565:    */   }
/* 566:    */   
/* 567:    */   public String getNombreArchivo()
/* 568:    */   {
/* 569:627 */     return this.notaCreditoCliente.getArchivo();
/* 570:    */   }
/* 571:    */   
/* 572:    */   public void processUpload(FileUploadEvent event)
/* 573:    */   {
/* 574:    */     try
/* 575:    */     {
/* 576:640 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "nota_credito_financiera_cliente");
/* 577:    */       
/* 578:642 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getNotaCreditoCliente().getNumero(), uploadDir);
/* 579:    */       
/* 580:    */ 
/* 581:645 */       HashMap<String, Object> campos = new HashMap();
/* 582:646 */       campos.put("archivo", fileName);
/* 583:647 */       this.servicioNotaCreditoCliente.actualizarAtributoEntidad(this.notaCreditoCliente, campos);
/* 584:    */       
/* 585:649 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 586:    */     }
/* 587:    */     catch (Exception e)
/* 588:    */     {
/* 589:652 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 590:653 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 591:    */     }
/* 592:    */   }
/* 593:    */   
/* 594:    */   public String eliminarArchivo()
/* 595:    */   {
/* 596:659 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), this.notaCreditoCliente.getArchivo());
/* 597:660 */     this.notaCreditoCliente.setArchivo(null);
/* 598:661 */     HashMap<String, Object> campos = new HashMap();
/* 599:662 */     campos.put("archivo", null);
/* 600:663 */     this.servicioNotaCreditoCliente.actualizarAtributoEntidad(this.notaCreditoCliente, campos);
/* 601:664 */     return null;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public void actualizarSubclienteListener(AjaxBehaviorEvent event)
/* 605:    */   {
/* 606:671 */     Subempresa subempresa = (Subempresa)((HtmlSelectOneMenu)event.getSource()).getValue();
/* 607:673 */     if (subempresa != null)
/* 608:    */     {
/* 609:674 */       this.notaCreditoCliente.setSubempresa(subempresa);
/* 610:675 */       this.notaCreditoCliente.setDireccionEmpresa(null);
/* 611:676 */       this.notaCreditoCliente.setEmail(this.servicioEmpresa.cargarMails(subempresa.getEmpresa(), DocumentoBase.NOTA_CREDITO_CLIENTE));
/* 612:677 */       cargarDirecciones();
/* 613:    */     }
/* 614:    */   }
/* 615:    */   
/* 616:    */   public FacturaCliente getNotaCreditoCliente()
/* 617:    */   {
/* 618:690 */     return this.notaCreditoCliente;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public void setNotaCreditoCliente(FacturaCliente notaCreditoCliente)
/* 622:    */   {
/* 623:700 */     this.notaCreditoCliente = notaCreditoCliente;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public LazyDataModel<FacturaCliente> getListaNotaCreditoCliente()
/* 627:    */   {
/* 628:709 */     return this.listaNotaCreditoCliente;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public void setListaNotaCreditoCliente(LazyDataModel<FacturaCliente> listaNotaCreditoCliente)
/* 632:    */   {
/* 633:719 */     this.listaNotaCreditoCliente = listaNotaCreditoCliente;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public List<Documento> getListaDocumento()
/* 637:    */   {
/* 638:728 */     if (this.listaDocumento == null) {
/* 639:    */       try
/* 640:    */       {
/* 641:730 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 642:    */       }
/* 643:    */       catch (ExcepcionAS2 e)
/* 644:    */       {
/* 645:732 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 646:    */       }
/* 647:    */     }
/* 648:736 */     return this.listaDocumento;
/* 649:    */   }
/* 650:    */   
/* 651:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 652:    */   {
/* 653:746 */     this.listaDocumento = listaDocumento;
/* 654:    */   }
/* 655:    */   
/* 656:    */   public DataTable getDtNotaCreditoCliente()
/* 657:    */   {
/* 658:755 */     return this.dtNotaCreditoCliente;
/* 659:    */   }
/* 660:    */   
/* 661:    */   public void setDtNotaCreditoCliente(DataTable dtNotaCreditoCliente)
/* 662:    */   {
/* 663:765 */     this.dtNotaCreditoCliente = dtNotaCreditoCliente;
/* 664:    */   }
/* 665:    */   
/* 666:    */   public DataTable getDtDetalleNotaCreditoCliente()
/* 667:    */   {
/* 668:774 */     return this.dtDetalleNotaCreditoCliente;
/* 669:    */   }
/* 670:    */   
/* 671:    */   public void setDtDetalleNotaCreditoCliente(DataTable dtDetalleNotaCreditoCliente)
/* 672:    */   {
/* 673:784 */     this.dtDetalleNotaCreditoCliente = dtDetalleNotaCreditoCliente;
/* 674:    */   }
/* 675:    */   
/* 676:    */   public DataTable getDtImpuestoDetalleNC()
/* 677:    */   {
/* 678:793 */     return this.dtImpuestoDetalleNC;
/* 679:    */   }
/* 680:    */   
/* 681:    */   public void setDtImpuestoDetalleNC(DataTable dtImpuestoDetalleNC)
/* 682:    */   {
/* 683:803 */     this.dtImpuestoDetalleNC = dtImpuestoDetalleNC;
/* 684:    */   }
/* 685:    */   
/* 686:    */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 687:    */   {
/* 688:812 */     return this.listaDireccionEmpresa;
/* 689:    */   }
/* 690:    */   
/* 691:    */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 692:    */   {
/* 693:822 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 694:    */   }
/* 695:    */   
/* 696:    */   public Integer getIdNotaCreditoCliente()
/* 697:    */   {
/* 698:826 */     return this.idNotaCreditoCliente;
/* 699:    */   }
/* 700:    */   
/* 701:    */   public void setIdNotaCreditoCliente(Integer idNotaCreditoCliente)
/* 702:    */   {
/* 703:830 */     this.idNotaCreditoCliente = idNotaCreditoCliente;
/* 704:    */   }
/* 705:    */   
/* 706:    */   public List<MotivoNotaCreditoCliente> getListaMotivoNotaCreditoCliente()
/* 707:    */   {
/* 708:839 */     if (this.listaMotivoNotaCreditoCliente == null) {
/* 709:840 */       this.listaMotivoNotaCreditoCliente = this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("nombre", true, null);
/* 710:    */     }
/* 711:842 */     return this.listaMotivoNotaCreditoCliente;
/* 712:    */   }
/* 713:    */   
/* 714:    */   public ReporteNotaCreditoFinancieraBean getReporteNotaCreditoFinancieraBean()
/* 715:    */   {
/* 716:851 */     return this.reporteNotaCreditoFinancieraBean;
/* 717:    */   }
/* 718:    */   
/* 719:    */   public void setReporteNotaCreditoFinancieraBean(ReporteNotaCreditoFinancieraBean reporteNotaCreditoFinancieraBean)
/* 720:    */   {
/* 721:861 */     this.reporteNotaCreditoFinancieraBean = reporteNotaCreditoFinancieraBean;
/* 722:    */   }
/* 723:    */   
/* 724:    */   public BigDecimal getValorNotaCredito()
/* 725:    */   {
/* 726:865 */     return this.valorNotaCredito;
/* 727:    */   }
/* 728:    */   
/* 729:    */   public void setValorNotaCredito(BigDecimal valorNotaCredito)
/* 730:    */   {
/* 731:869 */     this.valorNotaCredito = valorNotaCredito;
/* 732:    */   }
/* 733:    */   
/* 734:    */   public BigDecimal getValorBloqueado()
/* 735:    */   {
/* 736:873 */     return this.valorBloqueado;
/* 737:    */   }
/* 738:    */   
/* 739:    */   public void setValorBloqueado(BigDecimal valorBloqueado)
/* 740:    */   {
/* 741:877 */     this.valorBloqueado = valorBloqueado;
/* 742:    */   }
/* 743:    */   
/* 744:    */   public String getMails()
/* 745:    */   {
/* 746:881 */     return this.mails;
/* 747:    */   }
/* 748:    */   
/* 749:    */   public void setMails(String mails)
/* 750:    */   {
/* 751:885 */     this.mails = mails;
/* 752:    */   }
/* 753:    */   
/* 754:    */   public void enviarMail()
/* 755:    */   {
/* 756:    */     try
/* 757:    */     {
/* 758:890 */       if (((this.notaCreditoCliente.getDocumento() != null) && (!this.notaCreditoCliente.getDocumento().isIndicadorDocumentoElectronico())) || 
/* 759:891 */         (this.notaCreditoCliente.getEstado().equals(Estado.ANULADO)) || (this.notaCreditoCliente.getEstado().equals(Estado.EN_ESPERA)) || 
/* 760:892 */         (this.notaCreditoCliente.getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/* 761:893 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 762:    */       } else {
/* 763:895 */         this.servicioFacturaCliente.enviarMail(this.notaCreditoCliente, this.mails);
/* 764:    */       }
/* 765:    */     }
/* 766:    */     catch (ExcepcionAS2 e)
/* 767:    */     {
/* 768:898 */       addErrorMessage(this.notaCreditoCliente.getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/* 769:    */     }
/* 770:    */   }
/* 771:    */   
/* 772:    */   public StreamedContent getXMLSRI()
/* 773:    */   {
/* 774:903 */     if ((this.notaCreditoCliente != null) && (this.notaCreditoCliente.getId() != 0) && (this.notaCreditoCliente.getFacturaClienteSRI() != null))
/* 775:    */     {
/* 776:904 */       String pathSRI = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri";
/* 777:    */       
/* 778:    */ 
/* 779:907 */       String pathDocumento = pathSRI + File.separator + "documentos_electronicos" + File.separator + TipoDocumentoElectronicoEnum.NOTA_CREDITO.toString();
/* 780:908 */       String pathArchivoAutorizado = pathDocumento + File.separator + "autorizado";
/* 781:909 */       String nombreArchivo = this.notaCreditoCliente.getNumero() + "-" + this.notaCreditoCliente.getFacturaClienteSRI().getClaveAcceso() + ".xml";
/* 782:910 */       pathArchivoAutorizado = pathArchivoAutorizado + File.separator + nombreArchivo;
/* 783:    */       try
/* 784:    */       {
/* 785:912 */         return FuncionesUtiles.descargarArchivo(pathArchivoAutorizado, "application/xls", nombreArchivo);
/* 786:    */       }
/* 787:    */       catch (FileNotFoundException e)
/* 788:    */       {
/* 789:914 */         e.printStackTrace();
/* 790:915 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 791:916 */         return null;
/* 792:    */       }
/* 793:    */     }
/* 794:919 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 795:920 */     return null;
/* 796:    */   }
/* 797:    */   
/* 798:    */   public void cargarNotas(FacturaCliente notaCredito)
/* 799:    */   {
/* 800:925 */     this.notaCreditoCliente = notaCredito;
/* 801:926 */     List<NotaFacturaCliente> listaNota = this.servicioFacturaCliente.cargarListaNotaFacturaCliente(this.notaCreditoCliente);
/* 802:927 */     this.notaCreditoCliente.setListaNotaFacturaCliente(listaNota);
/* 803:    */     
/* 804:929 */     crearNotaFacturaCliente();
/* 805:    */   }
/* 806:    */   
/* 807:    */   private void crearNotaFacturaCliente()
/* 808:    */   {
/* 809:933 */     this.notaFacturaCliente = new NotaFacturaCliente();
/* 810:934 */     this.notaFacturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 811:935 */     this.notaFacturaCliente.setIdSucursal(AppUtil.getSucursal().getId());
/* 812:936 */     this.notaFacturaCliente.setFacturaCliente(this.notaCreditoCliente);
/* 813:    */   }
/* 814:    */   
/* 815:    */   public void agregarNotaFacturaCliente()
/* 816:    */   {
/* 817:    */     try
/* 818:    */     {
/* 819:941 */       this.servicioFacturaCliente.guardarNotaFacturaCliente(this.notaFacturaCliente);
/* 820:    */     }
/* 821:    */     catch (Exception e)
/* 822:    */     {
/* 823:943 */       addErrorMessage(e.getMessage());
/* 824:    */     }
/* 825:945 */     cargarNotas(this.notaCreditoCliente);
/* 826:    */   }
/* 827:    */   
/* 828:    */   public NotaFacturaCliente getNotaFacturaCliente()
/* 829:    */   {
/* 830:949 */     return this.notaFacturaCliente;
/* 831:    */   }
/* 832:    */   
/* 833:    */   public void setNotaFacturaCliente(NotaFacturaCliente notaFacturaCliente)
/* 834:    */   {
/* 835:953 */     this.notaFacturaCliente = notaFacturaCliente;
/* 836:    */   }
/* 837:    */   
/* 838:    */   public DataTable getDtDetalleNotaFacturaCliente()
/* 839:    */   {
/* 840:957 */     return this.dtDetalleNotaFacturaCliente;
/* 841:    */   }
/* 842:    */   
/* 843:    */   public void setDtDetalleNotaFacturaCliente(DataTable dtDetalleNotaFacturaCliente)
/* 844:    */   {
/* 845:961 */     this.dtDetalleNotaFacturaCliente = dtDetalleNotaFacturaCliente;
/* 846:    */   }
/* 847:    */   
/* 848:    */   public List<Subempresa> getListaSubempresa()
/* 849:    */   {
/* 850:965 */     if (this.listaSubempresa == null) {
/* 851:966 */       this.listaSubempresa = new ArrayList();
/* 852:    */     }
/* 853:968 */     return this.listaSubempresa;
/* 854:    */   }
/* 855:    */   
/* 856:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 857:    */   {
/* 858:972 */     this.listaSubempresa = listaSubempresa;
/* 859:    */   }
/* 860:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.NotaCreditoFinancieraClienteBean
 * JD-Core Version:    0.7.0.1
 */