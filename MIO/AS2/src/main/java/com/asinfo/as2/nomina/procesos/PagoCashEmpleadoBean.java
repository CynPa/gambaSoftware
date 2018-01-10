/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Banco;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.DetallePagoCash;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.FormaPago;
/*  12:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.PagoCash;
/*  15:    */ import com.asinfo.as2.entities.PagoRol;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  18:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  19:    */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*  20:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  22:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  23:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPagoCash;
/*  24:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  25:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  26:    */ import com.asinfo.as2.nomina.reportes.ServicioReporteNomina;
/*  27:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.util.RutaArchivo;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.text.SimpleDateFormat;
/*  34:    */ import java.util.ArrayList;
/*  35:    */ import java.util.Date;
/*  36:    */ import java.util.HashMap;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.annotation.PostConstruct;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.faces.bean.ManagedBean;
/*  42:    */ import javax.faces.bean.ViewScoped;
/*  43:    */ import javax.faces.event.ActionEvent;
/*  44:    */ import javax.faces.model.SelectItem;
/*  45:    */ import org.apache.log4j.Logger;
/*  46:    */ import org.primefaces.component.datatable.DataTable;
/*  47:    */ import org.primefaces.event.FileUploadEvent;
/*  48:    */ import org.primefaces.model.LazyDataModel;
/*  49:    */ import org.primefaces.model.SortOrder;
/*  50:    */ import org.primefaces.model.StreamedContent;
/*  51:    */ 
/*  52:    */ @ManagedBean
/*  53:    */ @ViewScoped
/*  54:    */ public class PagoCashEmpleadoBean
/*  55:    */   extends PageControllerAS2
/*  56:    */ {
/*  57:    */   private static final long serialVersionUID = -3763601852164181158L;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioPagoCash servicioPagoCash;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioDocumento servicioDocumento;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioPagoRol servicioPagoRol;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioReporteNomina servicioReporteNomina;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioGenerico<Banco> servicioBanco;
/*  70:    */   private PagoCash pagoCash;
/*  71:    */   private List<Documento> listaDocumento;
/*  72:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  73:    */   private List<SelectItem> listaPagoRol;
/*  74:    */   private StreamedContent file;
/*  75:    */   private static final String TIPO_CONTENIDO = "application/txt";
/*  76:    */   private String documentoReferencia;
/*  77:    */   private boolean mostrarDialogContabilizar;
/*  78:    */   private BigDecimal total;
/*  79:    */   private TipoServicioCuentaBancariaEnum tipoServicioCuentaBancaria;
/*  80:102 */   private boolean activadorTipoServicio = true;
/*  81:    */   private LazyDataModel<PagoCash> listaPagoCash;
/*  82:    */   private DataTable dtPagoCashEmpleado;
/*  83:    */   private DataTable dtDetallePagoCash;
/*  84:    */   private List<FormaPago> listaFormaPagoOrganizacion;
/*  85:    */   
/*  86:    */   @PostConstruct
/*  87:    */   public void init()
/*  88:    */   {
/*  89:122 */     this.listaPagoCash = new LazyDataModel()
/*  90:    */     {
/*  91:    */       private static final long serialVersionUID = 7586860018116664670L;
/*  92:    */       
/*  93:    */       public List<PagoCash> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  94:    */       {
/*  95:129 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  96:    */         
/*  97:131 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  98:132 */         filters.put("documento.documentoBase", DocumentoBase.PAGO_CASH_EMPLEADO.toString());
/*  99:133 */         List<PagoCash> lista = PagoCashEmpleadoBean.this.servicioPagoCash.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 100:    */         
/* 101:135 */         PagoCashEmpleadoBean.this.listaPagoCash.setRowCount(PagoCashEmpleadoBean.this.servicioPagoCash.contarPorCriterio(filters));
/* 102:136 */         return lista;
/* 103:    */       }
/* 104:    */     };
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void crearPagoCash()
/* 108:    */   {
/* 109:150 */     this.pagoCash = new PagoCash();
/* 110:151 */     this.pagoCash.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 111:152 */     this.pagoCash.setIdSucursal(AppUtil.getSucursal().getId());
/* 112:153 */     this.pagoCash.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/* 113:154 */     this.pagoCash.setFormaPago(new FormaPago());
/* 114:155 */     this.pagoCash.setFechaPago(new Date());
/* 115:156 */     this.pagoCash.setFechaVencimiento(new Date());
/* 116:    */     
/* 117:158 */     Documento documento = null;
/* 118:159 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 119:    */     {
/* 120:160 */       documento = (Documento)getListaDocumento().get(0);
/* 121:161 */       this.pagoCash.setDocumento(documento);
/* 122:162 */       actualizarDocumento();
/* 123:    */     }
/* 124:165 */     this.pagoCash.setNumero("");
/* 125:166 */     this.pagoCash.setValorPago(BigDecimal.ZERO);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String editar()
/* 129:    */   {
/* 130:175 */     if ((this.pagoCash != null) && (this.pagoCash.getIdPagoCash() != 0)) {
/* 131:    */       try
/* 132:    */       {
/* 133:177 */         this.servicioPagoCash.esEditable(this.pagoCash);
/* 134:178 */         this.pagoCash = this.servicioPagoCash.cargarDetalleEmpleado(getPagoCash().getIdPagoCash());
/* 135:179 */         actualizarCuentaBancaria();
/* 136:180 */         setEditado(true);
/* 137:    */       }
/* 138:    */       catch (ExcepcionAS2Financiero e)
/* 139:    */       {
/* 140:183 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 141:    */       }
/* 142:    */     } else {
/* 143:187 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 144:    */     }
/* 145:189 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String guardar()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:199 */       this.pagoCash.setEstado(Estado.ELABORADO);
/* 153:200 */       this.servicioPagoCash.guardar(this.pagoCash);
/* 154:    */       
/* 155:202 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 156:203 */       setEditado(false);
/* 157:204 */       limpiar();
/* 158:    */     }
/* 159:    */     catch (Exception e)
/* 160:    */     {
/* 161:206 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 162:207 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 163:    */     }
/* 164:209 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void actualizarPagoCashListener()
/* 168:    */     throws ExcepcionAS2Nomina
/* 169:    */   {
/* 170:213 */     boolean requiereAprobacion = ParametrosSistema.getIndicadorAprobarNomina(AppUtil.getOrganizacion().getId()).booleanValue();
/* 171:214 */     Banco banco = null;
/* 172:215 */     if (!this.pagoCash.getCuentaBancariaOrganizacion().isIndicadorTransferenciaInterbancariaPagoRol()) {
/* 173:216 */       banco = this.pagoCash.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco();
/* 174:    */     }
/* 175:219 */     this.servicioReporteNomina.getCashManagementEmpleado(this.pagoCash, null, null, null, requiereAprobacion, this.pagoCash.getTipoServicioCuentaBancaria(), banco);
/* 176:222 */     if (this.dtDetallePagoCash != null) {
/* 177:223 */       this.dtDetallePagoCash.reset();
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String eliminar()
/* 182:    */   {
/* 183:234 */     if ((getPagoCash() != null) && (getPagoCash().getIdPagoCash() != 0)) {
/* 184:    */       try
/* 185:    */       {
/* 186:237 */         this.servicioPagoCash.anularPagoCashEmpleado(this.pagoCash);
/* 187:    */       }
/* 188:    */       catch (ExcepcionAS2 e)
/* 189:    */       {
/* 190:239 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 191:240 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 192:    */       }
/* 193:    */     } else {
/* 194:243 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 195:    */     }
/* 196:246 */     return "";
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String cargarDatos()
/* 200:    */   {
/* 201:255 */     return "";
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String limpiar()
/* 205:    */   {
/* 206:264 */     crearPagoCash();
/* 207:265 */     this.tipoServicioCuentaBancaria = null;
/* 208:266 */     this.dtDetallePagoCash.reset();
/* 209:267 */     this.activadorTipoServicio = true;
/* 210:268 */     return "";
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String actualizarDocumento()
/* 214:    */   {
/* 215:281 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getPagoCash().getDocumento().getIdDocumento()));
/* 216:282 */     getPagoCash().setDocumento(documento);
/* 217:283 */     return "";
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void activarTipoServicio()
/* 221:    */   {
/* 222:291 */     if ((getPagoCash().getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("30")) || 
/* 223:292 */       (getPagoCash().getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("17"))) {
/* 224:293 */       this.activadorTipoServicio = false;
/* 225:    */     } else {
/* 226:295 */       this.activadorTipoServicio = true;
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void actualizarCuentaBancaria()
/* 231:    */   {
/* 232:309 */     this.listaFormaPagoOrganizacion = new ArrayList();
/* 233:311 */     if (this.pagoCash.getCuentaBancariaOrganizacion() != null)
/* 234:    */     {
/* 235:313 */       CuentaBancariaOrganizacion cbo = this.servicioCuentaBancariaOrganizacion.cargarDetalle(this.pagoCash.getCuentaBancariaOrganizacion().getIdCuentaBancariaOrganizacion());
/* 236:314 */       for (FormaPagoCuentaBancariaOrganizacion fp : cbo.getListaFormaPago()) {
/* 237:315 */         this.listaFormaPagoOrganizacion.add(fp.getFormaPago());
/* 238:    */       }
/* 239:318 */       activarTipoServicio();
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public StreamedContent generarArchivo(PagoCash pagoCashSeleccionado)
/* 244:    */   {
/* 245:328 */     StreamedContent file = null;
/* 246:329 */     int numeroColumnas = 0;
/* 247:    */     try
/* 248:    */     {
/* 249:331 */       PagoCash pagoCashConDetalle = this.servicioPagoCash.cargarDetalleEmpleado(pagoCashSeleccionado.getIdPagoCash());
/* 250:    */       
/* 251:333 */       String rutaArchivoTxt = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pagoCashEmpleado");
/* 252:    */       
/* 253:335 */       String nombreArchivo = FuncionesUtiles.nombreMes(pagoCashConDetalle.getPagoRol().getMes() - 1) + " " + pagoCashConDetalle.getPagoRol().getAnio() + ".txt";
/* 254:    */       
/* 255:337 */       List<Object[]> listaDatos = this.servicioPagoCash.getDatosArchivoPagoCashEmpleado(pagoCashConDetalle, 
/* 256:338 */         AppUtil.getOrganizacion().getIdOrganizacion());
/* 257:340 */       if (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("205"))
/* 258:    */       {
/* 259:341 */         numeroColumnas = 2;
/* 260:    */       }
/* 261:342 */       else if (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("30"))
/* 262:    */       {
/* 263:343 */         numeroColumnas = 1;
/* 264:    */       }
/* 265:344 */       else if (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("17"))
/* 266:    */       {
/* 267:345 */         numeroColumnas = 1;
/* 268:    */       }
/* 269:    */       else
/* 270:    */       {
/* 271:347 */         numeroColumnas = 11;
/* 272:348 */         if (!listaDatos.isEmpty()) {
/* 273:349 */           numeroColumnas = ((Object[])listaDatos.get(0)).length;
/* 274:    */         }
/* 275:    */       }
/* 276:353 */       if (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("36"))
/* 277:    */       {
/* 278:356 */         nombreArchivo = "CRDB R" + pagoCashConDetalle.getPagoRol().getMes() + Integer.toString(pagoCashConDetalle.getPagoRol().getAnio()).substring(2, 4) + ".txt";
/* 279:357 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaDatos, numeroColumnas, "");
/* 280:    */       }
/* 281:358 */       else if (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("32"))
/* 282:    */       {
/* 283:363 */         nombreArchivo = "PAGO ROL" + pagoCashConDetalle.getPagoRol().getMes() + Integer.toString(pagoCashConDetalle.getPagoRol().getAnio()).substring(2, 4) + ".txt";
/* 284:364 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaDatos, 1, "");
/* 285:    */       }
/* 286:366 */       else if (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("30"))
/* 287:    */       {
/* 288:370 */         nombreArchivo = "PACIFIC-" + FuncionesUtiles.nombreMes(pagoCashConDetalle.getPagoRol().getMes() - 1) + " " + pagoCashConDetalle.getPagoRol().getAnio() + " (" + (pagoCashSeleccionado.getTipoServicioCuentaBancaria() != null ? pagoCashSeleccionado.getTipoServicioCuentaBancaria() : "") + ")" + ".txt";
/* 289:    */         
/* 290:372 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaDatos, numeroColumnas, "\t");
/* 291:    */       }
/* 292:373 */       else if (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("17"))
/* 293:    */       {
/* 294:375 */         SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
/* 295:376 */         nombreArchivo = "NCR" + formato.format(pagoCashSeleccionado.getFechaPago()) + "XXX_" + pagoCashSeleccionado.getIdPagoCash() + ".txt";
/* 296:377 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaDatos, numeroColumnas, "");
/* 297:    */       }
/* 298:378 */       else if ((pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("34")) || 
/* 299:379 */         (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("37")))
/* 300:    */       {
/* 301:381 */         String mes = "" + FuncionesUtiles.getMes(pagoCashSeleccionado.getFechaPago());
/* 302:382 */         mes = mes.length() == 1 ? "0" + mes : mes;
/* 303:383 */         String dia = "" + FuncionesUtiles.getDiaFecha(pagoCashSeleccionado.getFechaPago());
/* 304:384 */         dia = dia.length() == 1 ? "0" + dia : dia;
/* 305:    */         
/* 306:    */ 
/* 307:387 */         nombreArchivo = AppUtil.getOrganizacion().getRazonSocial() + FuncionesUtiles.getAnio(pagoCashSeleccionado.getFechaPago()) + mes + dia + pagoCashSeleccionado.getIdPagoCash() + ".BIZ";
/* 308:388 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaDatos, numeroColumnas, "", "Cp1252");
/* 309:    */       }
/* 310:    */       else
/* 311:    */       {
/* 312:391 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaDatos, numeroColumnas, "\t");
/* 313:    */       }
/* 314:394 */       if ((pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("34")) || 
/* 315:395 */         (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("37"))) {
/* 316:396 */         file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "text/plain", nombreArchivo);
/* 317:    */       } else {
/* 318:398 */         file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "application/txt", nombreArchivo);
/* 319:    */       }
/* 320:401 */       LOG.info("Archivo pago cash proveedor generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivo);
/* 321:402 */       addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_generado") + ": " + rutaArchivoTxt + nombreArchivo);
/* 322:    */     }
/* 323:    */     catch (Exception e)
/* 324:    */     {
/* 325:404 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 326:405 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 327:    */     }
/* 328:408 */     return file;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void contabilizarPagoCash(ActionEvent aev)
/* 332:    */   {
/* 333:    */     try
/* 334:    */     {
/* 335:419 */       this.servicioPagoCash.contabilizarPagoCashEmpleado(this.pagoCash, getDocumentoReferencia());
/* 336:    */       
/* 337:421 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 338:    */     }
/* 339:    */     catch (ExcepcionAS2Financiero e)
/* 340:    */     {
/* 341:423 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 342:    */     }
/* 343:    */     catch (ExcepcionAS2 e)
/* 344:    */     {
/* 345:425 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 346:    */     }
/* 347:    */     catch (Exception e)
/* 348:    */     {
/* 349:427 */       addErrorMessage(getLanguageController().getMensaje("msg_error_contabilizar_pago_cash"));
/* 350:428 */       LOG.error("Error al contabilizar el pago cash " + e + ". Causa: " + e.getCause());
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void seleccionarPagoCashAContabilizar(ActionEvent event)
/* 355:    */   {
/* 356:    */     try
/* 357:    */     {
/* 358:439 */       setMostrarDialogContabilizar(true);
/* 359:440 */       PagoCash pagoCashSeleccionado = (PagoCash)this.dtPagoCashEmpleado.getRowData();
/* 360:441 */       PagoCash pagoCashConDetalle = this.servicioPagoCash.cargarDetalleEmpleado(pagoCashSeleccionado.getIdPagoCash());
/* 361:442 */       setPagoCash(pagoCashConDetalle);
/* 362:    */     }
/* 363:    */     catch (ExcepcionAS2Financiero e)
/* 364:    */     {
/* 365:444 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void processDownload()
/* 370:    */   {
/* 371:    */     try
/* 372:    */     {
/* 373:455 */       PagoCash fp = (PagoCash)this.dtPagoCashEmpleado.getRowData();
/* 374:456 */       String fileName = fp.getPdf();
/* 375:457 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_cash_empleado");
/* 376:459 */       if (fileName == null) {
/* 377:460 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 378:    */       } else {
/* 379:462 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 380:    */       }
/* 381:    */     }
/* 382:    */     catch (Exception e)
/* 383:    */     {
/* 384:466 */       e.printStackTrace();
/* 385:467 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   public String eliminarArchivo()
/* 390:    */   {
/* 391:472 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getPagoCash().getPdf());
/* 392:473 */     getPagoCash().setPdf(null);
/* 393:474 */     HashMap<String, Object> campos = new HashMap();
/* 394:475 */     campos.put("pdf", null);
/* 395:476 */     this.servicioPagoCash.actualizarAtributoEntidad(getPagoCash(), campos);
/* 396:477 */     return null;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void processUpload(FileUploadEvent event)
/* 400:    */   {
/* 401:    */     try
/* 402:    */     {
/* 403:490 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_cash_empleado");
/* 404:    */       
/* 405:492 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getPagoCash().getNumero(), uploadDir);
/* 406:    */       
/* 407:494 */       HashMap<String, Object> campos = new HashMap();
/* 408:495 */       campos.put("pdf", fileName);
/* 409:496 */       this.servicioPagoCash.actualizarAtributoEntidad(getPagoCash(), campos);
/* 410:    */       
/* 411:498 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 412:    */     }
/* 413:    */     catch (Exception e)
/* 414:    */     {
/* 415:501 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 416:502 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 417:    */     }
/* 418:    */   }
/* 419:    */   
/* 420:    */   public String getDirectorioDescarga()
/* 421:    */   {
/* 422:509 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_cash_empleado");
/* 423:    */   }
/* 424:    */   
/* 425:    */   public PagoCash getPagoCash()
/* 426:    */   {
/* 427:518 */     return this.pagoCash;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setPagoCash(PagoCash pagoCash)
/* 431:    */   {
/* 432:528 */     this.pagoCash = pagoCash;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public LazyDataModel<PagoCash> getListaPagoCash()
/* 436:    */   {
/* 437:537 */     return this.listaPagoCash;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setListaPagoCash(LazyDataModel<PagoCash> listaPagoCash)
/* 441:    */   {
/* 442:547 */     this.listaPagoCash = listaPagoCash;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public DataTable getDtPagoCashEmpleado()
/* 446:    */   {
/* 447:556 */     return this.dtPagoCashEmpleado;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setDtPagoCashEmpleado(DataTable dtPagoCashEmpleado)
/* 451:    */   {
/* 452:566 */     this.dtPagoCashEmpleado = dtPagoCashEmpleado;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public List<Documento> getListaDocumento()
/* 456:    */   {
/* 457:575 */     if (this.listaDocumento == null) {
/* 458:    */       try
/* 459:    */       {
/* 460:577 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PAGO_CASH_EMPLEADO, 
/* 461:578 */           AppUtil.getOrganizacion().getId());
/* 462:    */       }
/* 463:    */       catch (ExcepcionAS2 e)
/* 464:    */       {
/* 465:580 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 466:    */       }
/* 467:    */     }
/* 468:583 */     return this.listaDocumento;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 472:    */   {
/* 473:593 */     this.listaDocumento = listaDocumento;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 477:    */   {
/* 478:602 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 479:603 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 480:    */     }
/* 481:605 */     return this.listaCuentaBancariaOrganizacion;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 485:    */   {
/* 486:615 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public List<SelectItem> getListaPagoRol()
/* 490:    */   {
/* 491:625 */     if (this.listaPagoRol == null)
/* 492:    */     {
/* 493:626 */       List<PagoRol> lista = new ArrayList();
/* 494:627 */       Map<String, String> filters = new HashMap();
/* 495:628 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 496:629 */       filters.put("indicadorFiniquito", "false");
/* 497:    */       
/* 498:631 */       lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 499:632 */       if (this.listaPagoRol == null)
/* 500:    */       {
/* 501:633 */         this.listaPagoRol = new ArrayList();
/* 502:634 */         for (PagoRol pagoRol : lista)
/* 503:    */         {
/* 504:639 */           SelectItem item = new SelectItem(pagoRol, FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString()));
/* 505:640 */           this.listaPagoRol.add(item);
/* 506:    */         }
/* 507:    */       }
/* 508:    */     }
/* 509:645 */     return this.listaPagoRol;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setListaPagoRol(List<SelectItem> listaPagoRol)
/* 513:    */   {
/* 514:655 */     this.listaPagoRol = listaPagoRol;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public StreamedContent getFile()
/* 518:    */   {
/* 519:664 */     PagoCash pagoCashSeleccionado = (PagoCash)this.dtPagoCashEmpleado.getRowData();
/* 520:665 */     this.file = generarArchivo(pagoCashSeleccionado);
/* 521:666 */     return this.file;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public void setFile(StreamedContent file)
/* 525:    */   {
/* 526:676 */     this.file = file;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public String getDocumentoReferencia()
/* 530:    */   {
/* 531:685 */     return this.documentoReferencia;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 535:    */   {
/* 536:695 */     this.documentoReferencia = documentoReferencia;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public boolean isMostrarDialogContabilizar()
/* 540:    */   {
/* 541:704 */     return this.mostrarDialogContabilizar;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setMostrarDialogContabilizar(boolean mostrarDialogContabilizar)
/* 545:    */   {
/* 546:714 */     this.mostrarDialogContabilizar = mostrarDialogContabilizar;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public List<DetallePagoCash> getListaDetallePagoCash()
/* 550:    */   {
/* 551:723 */     this.total = BigDecimal.ZERO;
/* 552:724 */     List<DetallePagoCash> lista = new ArrayList();
/* 553:725 */     for (DetallePagoCash detallePagoCash : this.pagoCash.getListaDetallePagoCash()) {
/* 554:726 */       if (!detallePagoCash.isEliminado())
/* 555:    */       {
/* 556:727 */         lista.add(detallePagoCash);
/* 557:728 */         this.total = this.total.add(detallePagoCash.getValor());
/* 558:    */       }
/* 559:    */     }
/* 560:731 */     return lista;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public BigDecimal getTotal()
/* 564:    */   {
/* 565:735 */     return this.total;
/* 566:    */   }
/* 567:    */   
/* 568:    */   public void setTotal(BigDecimal total)
/* 569:    */   {
/* 570:739 */     this.total = total;
/* 571:    */   }
/* 572:    */   
/* 573:    */   public DataTable getDtDetallePagoCash()
/* 574:    */   {
/* 575:748 */     return this.dtDetallePagoCash;
/* 576:    */   }
/* 577:    */   
/* 578:    */   public void setDtDetallePagoCash(DataTable dtDetallePagoCash)
/* 579:    */   {
/* 580:758 */     this.dtDetallePagoCash = dtDetallePagoCash;
/* 581:    */   }
/* 582:    */   
/* 583:    */   public TipoServicioCuentaBancariaEnum getTipoServicioCuentaBancaria()
/* 584:    */   {
/* 585:765 */     return this.tipoServicioCuentaBancaria;
/* 586:    */   }
/* 587:    */   
/* 588:    */   public void setTipoServicioCuentaBancaria(TipoServicioCuentaBancariaEnum tipoServicioCuentaBancaria)
/* 589:    */   {
/* 590:773 */     this.tipoServicioCuentaBancaria = tipoServicioCuentaBancaria;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public List<FormaPago> getListaFormaPagoOrganizacion()
/* 594:    */   {
/* 595:780 */     return this.listaFormaPagoOrganizacion;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public void setListaFormaPagoOrganizacion(List<FormaPago> listaFormaPagoOrganizacion)
/* 599:    */   {
/* 600:788 */     this.listaFormaPagoOrganizacion = listaFormaPagoOrganizacion;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public boolean isActivadorTipoServicio()
/* 604:    */   {
/* 605:792 */     return this.activadorTipoServicio;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public void setActivadorTipoServicio(boolean activadorTipoServicio)
/* 609:    */   {
/* 610:796 */     this.activadorTipoServicio = activadorTipoServicio;
/* 611:    */   }
/* 612:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.PagoCashEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */