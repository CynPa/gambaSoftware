/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   9:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*  10:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.Empleado;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.entities.FormaPago;
/*  15:    */ import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  18:    */ import com.asinfo.as2.entities.Proveedor;
/*  19:    */ import com.asinfo.as2.entities.Secuencia;
/*  20:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  21:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  22:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  25:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  26:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*  27:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioLiquidacionAnticipoProveedor;
/*  28:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  29:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  30:    */ import com.asinfo.as2.util.AppUtil;
/*  31:    */ import com.asinfo.as2.util.RutaArchivo;
/*  32:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  33:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.util.ArrayList;
/*  36:    */ import java.util.Calendar;
/*  37:    */ import java.util.Date;
/*  38:    */ import java.util.HashMap;
/*  39:    */ import java.util.List;
/*  40:    */ import java.util.Map;
/*  41:    */ import javax.annotation.PostConstruct;
/*  42:    */ import javax.ejb.EJB;
/*  43:    */ import javax.faces.bean.ManagedBean;
/*  44:    */ import javax.faces.bean.ViewScoped;
/*  45:    */ import javax.faces.event.ActionEvent;
/*  46:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  47:    */ import org.apache.log4j.Logger;
/*  48:    */ import org.primefaces.component.datatable.DataTable;
/*  49:    */ import org.primefaces.component.selectonemenu.SelectOneMenu;
/*  50:    */ import org.primefaces.event.FileUploadEvent;
/*  51:    */ import org.primefaces.event.SelectEvent;
/*  52:    */ import org.primefaces.model.LazyDataModel;
/*  53:    */ import org.primefaces.model.SortOrder;
/*  54:    */ 
/*  55:    */ @ManagedBean
/*  56:    */ @ViewScoped
/*  57:    */ public class AnticipoProveedorBean
/*  58:    */   extends PageControllerAS2
/*  59:    */ {
/*  60:    */   private static final long serialVersionUID = -2940140443655021551L;
/*  61:    */   @EJB
/*  62:    */   private ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  63:    */   @EJB
/*  64:    */   private ServicioEmpresa servicioEmpresa;
/*  65:    */   @EJB
/*  66:    */   private ServicioDocumento servicioDocumento;
/*  67:    */   @EJB
/*  68:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  69:    */   @EJB
/*  70:    */   private ServicioLiquidacionAnticipoProveedor servicioLiquidacionAnticipoProveedor;
/*  71:    */   @EJB
/*  72:    */   private ServicioSecuencia servicioSecuencia;
/*  73:    */   @EJB
/*  74:    */   private ServicioUsuario servicioUsuario;
/*  75:    */   @EJB
/*  76:    */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  77:    */   private AnticipoProveedor anticipoProveedor;
/*  78:    */   private LiquidacionAnticipoProveedor liquidacionAnticipoProveedor;
/*  79:    */   private LazyDataModel<AnticipoProveedor> listaAnticipoProveedor;
/*  80:    */   private LazyDataModel<LiquidacionAnticipoProveedor> listaLiquidacionAnticipoProveedor;
/*  81:    */   private Empresa empresa;
/*  82:    */   private boolean mostrarSaldoInicial;
/*  83:    */   private boolean mostrarDialogContabilizar;
/*  84:    */   private boolean mostrarDialogEntregarCheque;
/*  85:    */   private List<Empresa> listaEmpresa;
/*  86:    */   private List<Documento> listaDocumento;
/*  87:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  88:    */   private List<PersonaResponsable> listaPersonaResponsable;
/*  89:    */   private DataTable dtAnticipoProveedor;
/*  90:    */   private DataTable dtLiquidacionAnticipoProveedor;
/*  91:    */   private Boolean usaOrdenPago;
/*  92:    */   private String numeroAnticipo;
/*  93:    */   
/*  94:    */   @PostConstruct
/*  95:    */   public void init()
/*  96:    */   {
/*  97:120 */     this.listaAnticipoProveedor = new LazyDataModel()
/*  98:    */     {
/*  99:    */       private static final long serialVersionUID = 1L;
/* 100:    */       
/* 101:    */       public List<AnticipoProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 102:    */       {
/* 103:127 */         if (AnticipoProveedorBean.this.numeroAnticipo != null) {
/* 104:128 */           filters.put("numero", AnticipoProveedorBean.this.numeroAnticipo);
/* 105:    */         }
/* 106:131 */         List<AnticipoProveedor> lista = new ArrayList();
/* 107:    */         
/* 108:133 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 109:134 */         filters.put("documento.documentoBase", DocumentoBase.ANTICIPO_PROVEEDOR.toString());
/* 110:135 */         lista = AnticipoProveedorBean.this.servicioAnticipoProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 111:    */         
/* 112:137 */         AnticipoProveedorBean.this.listaAnticipoProveedor.setRowCount(AnticipoProveedorBean.this.servicioAnticipoProveedor.contarPorCriterio(filters));
/* 113:    */         
/* 114:139 */         return lista;
/* 115:    */       }
/* 116:142 */     };
/* 117:143 */     this.listaLiquidacionAnticipoProveedor = new LazyDataModel()
/* 118:    */     {
/* 119:    */       private static final long serialVersionUID = 1L;
/* 120:    */       
/* 121:    */       public List<LiquidacionAnticipoProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 122:    */       {
/* 123:151 */         List<LiquidacionAnticipoProveedor> lista = new ArrayList();
/* 124:152 */         AnticipoProveedorBean.this.listaLiquidacionAnticipoProveedor.setRowCount(0);
/* 125:    */         
/* 126:154 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 127:156 */         if ((AnticipoProveedorBean.this.anticipoProveedor != null) && (AnticipoProveedorBean.this.anticipoProveedor.getId() > 0))
/* 128:    */         {
/* 129:157 */           filters.put("anticipoProveedor.idAnticipoProveedor", String.valueOf(AnticipoProveedorBean.this.anticipoProveedor.getId()));
/* 130:    */           
/* 131:159 */           lista = AnticipoProveedorBean.this.servicioLiquidacionAnticipoProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 132:    */           
/* 133:161 */           AnticipoProveedorBean.this.listaLiquidacionAnticipoProveedor.setRowCount(AnticipoProveedorBean.this.servicioLiquidacionAnticipoProveedor.contarPorCriterio(filters));
/* 134:    */         }
/* 135:164 */         return lista;
/* 136:    */       }
/* 137:    */     };
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String editar()
/* 141:    */   {
/* 142:176 */     if (getAnticipoProveedor().getId() > 0) {
/* 143:    */       try
/* 144:    */       {
/* 145:180 */         this.servicioAnticipoProveedor.esEditable(this.anticipoProveedor);
/* 146:181 */         this.anticipoProveedor = this.servicioAnticipoProveedor.cargarDetalle(this.anticipoProveedor.getId());
/* 147:182 */         setEditado(true);
/* 148:    */       }
/* 149:    */       catch (ExcepcionAS2Financiero e)
/* 150:    */       {
/* 151:185 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 152:186 */         LOG.info("ERROR AL EDITAR ANTICIPO PROVEEDOR");
/* 153:    */       }
/* 154:    */     } else {
/* 155:190 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 156:    */     }
/* 157:193 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String guardar()
/* 161:    */   {
/* 162:    */     try
/* 163:    */     {
/* 164:205 */       this.servicioAnticipoProveedor.guardar(this.anticipoProveedor);
/* 165:206 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 166:    */       
/* 167:208 */       cargarDatos();
/* 168:    */     }
/* 169:    */     catch (ExcepcionAS2Financiero e)
/* 170:    */     {
/* 171:211 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 172:    */     }
/* 173:    */     catch (ExcepcionAS2 e)
/* 174:    */     {
/* 175:214 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 176:    */     }
/* 177:    */     catch (Exception e)
/* 178:    */     {
/* 179:217 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 180:218 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 181:    */     }
/* 182:221 */     return "";
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void contabilizarAnticipoProveedor(ActionEvent aev)
/* 186:    */   {
/* 187:    */     try
/* 188:    */     {
/* 189:231 */       Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.anticipoProveedor.getDocumento().getId()));
/* 190:232 */       if (documento.isIndicadorContabilizar())
/* 191:    */       {
/* 192:233 */         this.anticipoProveedor.getDocumento().setTipoAsiento(documento.getTipoAsiento());
/* 193:234 */         this.anticipoProveedor.setEstado(Estado.CONTABILIZADO);
/* 194:235 */         this.servicioAnticipoProveedor.guardar(this.anticipoProveedor);
/* 195:236 */         setMostrarDialogContabilizar(false);
/* 196:237 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 197:    */       }
/* 198:    */     }
/* 199:    */     catch (ExcepcionAS2Financiero e)
/* 200:    */     {
/* 201:240 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 202:241 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 203:    */     }
/* 204:    */     catch (ExcepcionAS2 e)
/* 205:    */     {
/* 206:243 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 207:244 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 208:    */     }
/* 209:    */     catch (Exception e)
/* 210:    */     {
/* 211:246 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 212:247 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String seleccionarAnticipoAContabilizar()
/* 217:    */   {
/* 218:257 */     setMostrarDialogContabilizar(true);
/* 219:258 */     AnticipoProveedor anticipoProveedorSeleccionado = (AnticipoProveedor)this.dtAnticipoProveedor.getRowData();
/* 220:    */     
/* 221:260 */     AnticipoProveedor anticipoProveedorConDetalle = this.servicioAnticipoProveedor.cargarDetalle(anticipoProveedorSeleccionado.getIdAnticipoProveedor());
/* 222:261 */     setAnticipoProveedor(anticipoProveedorConDetalle);
/* 223:262 */     actualizarFormaPago();
/* 224:263 */     return "";
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String eliminar()
/* 228:    */   {
/* 229:273 */     anularAnticipoProveedor();
/* 230:274 */     return "";
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String limpiar()
/* 234:    */   {
/* 235:284 */     crearAnticipoProveeedor();
/* 236:285 */     return "";
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String cargarDatos()
/* 240:    */   {
/* 241:295 */     setEditado(false);
/* 242:    */     try
/* 243:    */     {
/* 244:298 */       limpiar();
/* 245:    */     }
/* 246:    */     catch (Exception e)
/* 247:    */     {
/* 248:301 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 249:302 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 250:    */     }
/* 251:304 */     return "";
/* 252:    */   }
/* 253:    */   
/* 254:    */   public String crearAnticipoProveeedor()
/* 255:    */   {
/* 256:308 */     this.anticipoProveedor = new AnticipoProveedor();
/* 257:309 */     this.anticipoProveedor.setValor(BigDecimal.ZERO);
/* 258:310 */     this.anticipoProveedor.setFecha(new Date());
/* 259:311 */     this.anticipoProveedor.setEstado(Estado.ELABORADO);
/* 260:312 */     this.anticipoProveedor.setSucursal(AppUtil.getSucursal());
/* 261:313 */     this.anticipoProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 262:    */     
/* 263:315 */     Documento documento = null;
/* 264:316 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 265:    */     {
/* 266:317 */       documento = (Documento)getListaDocumento().get(0);
/* 267:318 */       this.anticipoProveedor.setDocumento(documento);
/* 268:319 */       actualizarDocumento();
/* 269:    */     }
/* 270:    */     else
/* 271:    */     {
/* 272:321 */       documento = new Documento();
/* 273:322 */       documento.setSecuencia(new Secuencia());
/* 274:323 */       this.anticipoProveedor.setDocumento(documento);
/* 275:    */     }
/* 276:326 */     this.anticipoProveedor.setNumero("");
/* 277:    */     
/* 278:328 */     this.anticipoProveedor.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/* 279:329 */     this.anticipoProveedor.setFormaPago(new FormaPago());
/* 280:330 */     cargarPersonaResponsable();
/* 281:    */     
/* 282:332 */     return "";
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void anularAnticipoProveedor()
/* 286:    */   {
/* 287:339 */     if (getAnticipoProveedor().getId() > 0) {
/* 288:    */       try
/* 289:    */       {
/* 290:343 */         this.servicioAnticipoProveedor.anularAnticipoProveedor(this.anticipoProveedor);
/* 291:    */       }
/* 292:    */       catch (ExcepcionAS2Financiero e)
/* 293:    */       {
/* 294:346 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 295:347 */         LOG.info("ERROR AL ANULAR ANTICIPO CLIENTE");
/* 296:    */       }
/* 297:    */       catch (ExcepcionAS2 e)
/* 298:    */       {
/* 299:350 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 300:351 */         LOG.info("ERROR AL ANULAR ANTICIPO CLIENTE");
/* 301:    */       }
/* 302:    */     } else {
/* 303:355 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   public String liquidarAnticipoProveedor()
/* 308:    */   {
/* 309:367 */     if (this.anticipoProveedor.getEstado() == Estado.ANULADO)
/* 310:    */     {
/* 311:368 */       addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 312:369 */       return "";
/* 313:    */     }
/* 314:371 */     AppUtil.removeAtributo("anticipo_proveedor");
/* 315:372 */     AppUtil.setAtributo("anticipo_proveedor", this.anticipoProveedor);
/* 316:373 */     return "liquidacionAnticipoProveedor?faces-redirect=true";
/* 317:    */   }
/* 318:    */   
/* 319:    */   public String devolverAnticipoProveedor()
/* 320:    */   {
/* 321:379 */     if (this.anticipoProveedor.getEstado() == Estado.ANULADO)
/* 322:    */     {
/* 323:380 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 324:381 */       return "";
/* 325:    */     }
/* 326:383 */     if (this.anticipoProveedor.getCuentaBancariaOrganizacionDevolucion() != null)
/* 327:    */     {
/* 328:384 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 329:385 */       return "";
/* 330:    */     }
/* 331:387 */     AppUtil.removeAtributo("anticipo_proveedor");
/* 332:388 */     AppUtil.setAtributo("anticipo_proveedor", this.anticipoProveedor);
/* 333:389 */     return "devolucionAnticipoProveedor?faces-redirect=true";
/* 334:    */   }
/* 335:    */   
/* 336:    */   public String actualizarDocumento()
/* 337:    */   {
/* 338:400 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getAnticipoProveedor().getDocumento().getIdDocumento()));
/* 339:401 */     getAnticipoProveedor().setDocumento(documento);
/* 340:    */     
/* 341:403 */     setSecuenciaEditable(!this.anticipoProveedor.getDocumento().isIndicadorBloqueoSecuencia());
/* 342:    */     
/* 343:405 */     return "";
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void actualizarProveedor(SelectEvent event)
/* 347:    */   {
/* 348:409 */     Empresa empresa = (Empresa)event.getObject();
/* 349:    */     
/* 350:411 */     String beneficiario = empresa.getProveedor().getBeneficiario() != null ? empresa.getProveedor().getBeneficiario() : "";
/* 351:    */     
/* 352:413 */     getAnticipoProveedor().setBeneficiario(beneficiario.equals("") ? empresa.getNombreFiscal() : beneficiario);
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/* 356:    */   {
/* 357:424 */     SelectOneMenu selectOneMenu = (SelectOneMenu)event.getComponent();
/* 358:425 */     Integer idCuentaBancaria = Integer.valueOf(Integer.parseInt(selectOneMenu.getValue().toString()));
/* 359:    */     
/* 360:427 */     this.anticipoProveedor.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(idCuentaBancaria.intValue()));
/* 361:    */   }
/* 362:    */   
/* 363:    */   public String seleccionarLiquidacionProveedor()
/* 364:    */   {
/* 365:431 */     this.liquidacionAnticipoProveedor = ((LiquidacionAnticipoProveedor)this.dtLiquidacionAnticipoProveedor.getRowData());
/* 366:432 */     return "";
/* 367:    */   }
/* 368:    */   
/* 369:    */   public String anularLiquidacionProveedor()
/* 370:    */   {
/* 371:    */     try
/* 372:    */     {
/* 373:437 */       this.servicioLiquidacionAnticipoProveedor.anular(this.liquidacionAnticipoProveedor);
/* 374:    */     }
/* 375:    */     catch (ExcepcionAS2Financiero e)
/* 376:    */     {
/* 377:439 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 378:    */     }
/* 379:    */     catch (ExcepcionAS2 e)
/* 380:    */     {
/* 381:441 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 382:    */     }
/* 383:443 */     return "";
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void actualizarFormaPago()
/* 387:    */   {
/* 388:450 */     String numero = "";
/* 389:451 */     int idFormaPago = getAnticipoProveedor().getFormaPago().getId();
/* 390:452 */     int idCuentaBancariaOrganizacion = getAnticipoProveedor().getCuentaBancariaOrganizacion().getId();
/* 391:453 */     getAnticipoProveedor()
/* 392:454 */       .setSecuencia(this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(idCuentaBancariaOrganizacion, idFormaPago));
/* 393:456 */     if (getAnticipoProveedor().getSecuencia() != null) {
/* 394:    */       try
/* 395:    */       {
/* 396:459 */         numero = this.servicioSecuencia.obtenerSecuencia(getAnticipoProveedor().getSecuencia(), getAnticipoProveedor().getFecha());
/* 397:460 */         getAnticipoProveedor().setDocumentoReferencia(numero);
/* 398:    */       }
/* 399:    */       catch (ExcepcionAS2 e)
/* 400:    */       {
/* 401:462 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 402:463 */         e.printStackTrace();
/* 403:    */       }
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void processDownload()
/* 408:    */   {
/* 409:    */     try
/* 410:    */     {
/* 411:476 */       AnticipoProveedor fp = (AnticipoProveedor)this.dtAnticipoProveedor.getRowData();
/* 412:477 */       String fileName = fp.getPdf();
/* 413:478 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "anticipo_proveedor");
/* 414:480 */       if (fileName == null) {
/* 415:481 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 416:    */       } else {
/* 417:483 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 418:    */       }
/* 419:    */     }
/* 420:    */     catch (Exception e)
/* 421:    */     {
/* 422:487 */       e.printStackTrace();
/* 423:488 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 424:    */     }
/* 425:    */   }
/* 426:    */   
/* 427:    */   public String eliminarArchivo()
/* 428:    */   {
/* 429:493 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getAnticipoProveedor().getPdf());
/* 430:494 */     getAnticipoProveedor().setPdf(null);
/* 431:495 */     HashMap<String, Object> campos = new HashMap();
/* 432:496 */     campos.put("pdf", null);
/* 433:497 */     this.servicioAnticipoProveedor.actualizarAtributoEntidad(getAnticipoProveedor(), campos);
/* 434:498 */     return null;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public void processUpload(FileUploadEvent event)
/* 438:    */   {
/* 439:    */     try
/* 440:    */     {
/* 441:511 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "anticipo_proveedor");
/* 442:    */       
/* 443:513 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getAnticipoProveedor().getNumero(), uploadDir);
/* 444:    */       
/* 445:    */ 
/* 446:516 */       HashMap<String, Object> campos = new HashMap();
/* 447:517 */       campos.put("pdf", fileName);
/* 448:518 */       this.servicioAnticipoProveedor.actualizarAtributoEntidad(getAnticipoProveedor(), campos);
/* 449:    */       
/* 450:520 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 451:    */     }
/* 452:    */     catch (Exception e)
/* 453:    */     {
/* 454:523 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 455:524 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 456:    */     }
/* 457:    */   }
/* 458:    */   
/* 459:    */   public String getDirectorioDescarga()
/* 460:    */   {
/* 461:531 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "anticipo_proveedor");
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void onRowSelect(SelectEvent event)
/* 465:    */   {
/* 466:544 */     this.anticipoProveedor = ((AnticipoProveedor)event.getObject());
/* 467:    */   }
/* 468:    */   
/* 469:    */   public AnticipoProveedor getAnticipoProveedor()
/* 470:    */   {
/* 471:553 */     if (this.anticipoProveedor == null) {
/* 472:554 */       crearAnticipoProveeedor();
/* 473:    */     }
/* 474:556 */     return this.anticipoProveedor;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public void setAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 478:    */   {
/* 479:566 */     this.anticipoProveedor = anticipoProveedor;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public Empresa getEmpresa()
/* 483:    */   {
/* 484:575 */     return this.empresa;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void setEmpresa(Empresa empresa)
/* 488:    */   {
/* 489:585 */     this.empresa = empresa;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public List<Empresa> getListaEmpresa()
/* 493:    */   {
/* 494:594 */     if (this.listaEmpresa == null) {
/* 495:595 */       this.listaEmpresa = this.servicioEmpresa.obtenerClientes();
/* 496:    */     }
/* 497:597 */     return this.listaEmpresa;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void setListaEmpresa(List<Empresa> listaEmpresa)
/* 501:    */   {
/* 502:607 */     this.listaEmpresa = listaEmpresa;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public List<Documento> getListaDocumento()
/* 506:    */   {
/* 507:617 */     if (this.listaDocumento == null) {
/* 508:    */       try
/* 509:    */       {
/* 510:619 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.ANTICIPO_PROVEEDOR);
/* 511:    */       }
/* 512:    */       catch (ExcepcionAS2 e)
/* 513:    */       {
/* 514:621 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 515:    */       }
/* 516:    */     }
/* 517:624 */     return this.listaDocumento;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 521:    */   {
/* 522:634 */     this.listaDocumento = listaDocumento;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public DataTable getDtAnticipoProveedor()
/* 526:    */   {
/* 527:638 */     return this.dtAnticipoProveedor;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setDtAnticipoProveedor(DataTable dtAnticipoProveedor)
/* 531:    */   {
/* 532:642 */     this.dtAnticipoProveedor = dtAnticipoProveedor;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public LazyDataModel<AnticipoProveedor> getListaAnticipoProveedor()
/* 536:    */   {
/* 537:646 */     return this.listaAnticipoProveedor;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setListaAnticipoProveedor(LazyDataModel<AnticipoProveedor> listaAnticipoProveedor)
/* 541:    */   {
/* 542:650 */     this.listaAnticipoProveedor = listaAnticipoProveedor;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 546:    */   {
/* 547:654 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 548:655 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 549:    */     }
/* 550:658 */     return this.listaCuentaBancariaOrganizacion;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 554:    */   {
/* 555:662 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public boolean isMostrarSaldoInicial()
/* 559:    */   {
/* 560:667 */     Calendar calendario = Calendar.getInstance();
/* 561:668 */     calendario.setTime(ParametrosSistema.getFechaMaximaSaldosIniciales(AppUtil.getOrganizacion().getId()));
/* 562:669 */     int day = calendario.get(5);
/* 563:670 */     int month = calendario.get(2) + 1;
/* 564:671 */     int year = calendario.get(1);
/* 565:    */     
/* 566:673 */     Date fecha = FuncionesUtiles.getFecha(day, month, year);
/* 567:    */     
/* 568:675 */     this.mostrarSaldoInicial = FuncionesUtiles.compararFechaAnteriorOIgual(new Date(), fecha);
/* 569:    */     
/* 570:677 */     return this.mostrarSaldoInicial;
/* 571:    */   }
/* 572:    */   
/* 573:    */   public void setMostrarSaldoInicial(boolean mostrarSaldoInicial)
/* 574:    */   {
/* 575:681 */     this.mostrarSaldoInicial = mostrarSaldoInicial;
/* 576:    */   }
/* 577:    */   
/* 578:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 579:    */   {
/* 580:685 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/* 581:    */   }
/* 582:    */   
/* 583:    */   public DataTable getDtLiquidacionAnticipoProveedor()
/* 584:    */   {
/* 585:694 */     return this.dtLiquidacionAnticipoProveedor;
/* 586:    */   }
/* 587:    */   
/* 588:    */   public void setDtLiquidacionAnticipoProveedor(DataTable dtLiquidacionAnticipoProveedor)
/* 589:    */   {
/* 590:704 */     this.dtLiquidacionAnticipoProveedor = dtLiquidacionAnticipoProveedor;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public LiquidacionAnticipoProveedor getLiquidacionAnticipoProveedor()
/* 594:    */   {
/* 595:708 */     if (this.liquidacionAnticipoProveedor == null) {
/* 596:709 */       this.liquidacionAnticipoProveedor = new LiquidacionAnticipoProveedor();
/* 597:    */     }
/* 598:711 */     return this.liquidacionAnticipoProveedor;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public void setLiquidacionAnticipoProveedor(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/* 602:    */   {
/* 603:715 */     this.liquidacionAnticipoProveedor = liquidacionAnticipoProveedor;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public String getNumeroAnticipo()
/* 607:    */   {
/* 608:724 */     return this.numeroAnticipo;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public void setNumeroAnticipo(String numeroAnticipo)
/* 612:    */   {
/* 613:734 */     this.numeroAnticipo = numeroAnticipo;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public LazyDataModel<LiquidacionAnticipoProveedor> getListaLiquidacionAnticipoProveedor()
/* 617:    */   {
/* 618:743 */     return this.listaLiquidacionAnticipoProveedor;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public void setListaLiquidacionAnticipoProveedor(LazyDataModel<LiquidacionAnticipoProveedor> listaLiquidacionAnticipoProveedor)
/* 622:    */   {
/* 623:753 */     this.listaLiquidacionAnticipoProveedor = listaLiquidacionAnticipoProveedor;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public boolean isMostrarDialogContabilizar()
/* 627:    */   {
/* 628:762 */     return this.mostrarDialogContabilizar;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public void setMostrarDialogContabilizar(boolean mostrarDialogContabilizar)
/* 632:    */   {
/* 633:772 */     this.mostrarDialogContabilizar = mostrarDialogContabilizar;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public boolean isMostrarDialogEntregarCheque()
/* 637:    */   {
/* 638:781 */     return this.mostrarDialogEntregarCheque;
/* 639:    */   }
/* 640:    */   
/* 641:    */   public void setMostrarDialogEntregarCheque(boolean mostrarDialogEntregarCheque)
/* 642:    */   {
/* 643:791 */     this.mostrarDialogEntregarCheque = mostrarDialogEntregarCheque;
/* 644:    */   }
/* 645:    */   
/* 646:    */   public Boolean getUsaOrdenPago()
/* 647:    */   {
/* 648:795 */     if (this.usaOrdenPago == null) {
/* 649:796 */       this.usaOrdenPago = ParametrosSistema.getIndicadorOrdenesPago(AppUtil.getOrganizacion().getId());
/* 650:    */     }
/* 651:798 */     return this.usaOrdenPago;
/* 652:    */   }
/* 653:    */   
/* 654:    */   public String anularDevolverAnticipoProveedor()
/* 655:    */   {
/* 656:803 */     if (this.anticipoProveedor.getEstado() == Estado.ANULADO) {
/* 657:804 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 658:    */     }
/* 659:    */     try
/* 660:    */     {
/* 661:809 */       this.servicioAnticipoProveedor.anularDevolverAnticipoProveedor(this.anticipoProveedor);
/* 662:810 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 663:811 */       return "anticipoProveedor?faces-redirect=true";
/* 664:    */     }
/* 665:    */     catch (ExcepcionAS2Financiero e)
/* 666:    */     {
/* 667:814 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 668:    */     }
/* 669:    */     catch (ExcepcionAS2 e)
/* 670:    */     {
/* 671:816 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 672:    */     }
/* 673:    */     catch (Exception e)
/* 674:    */     {
/* 675:818 */       e.printStackTrace();
/* 676:    */     }
/* 677:820 */     return "";
/* 678:    */   }
/* 679:    */   
/* 680:    */   public List<PersonaResponsable> getListaPersonaResponsable()
/* 681:    */   {
/* 682:824 */     if (this.listaPersonaResponsable == null)
/* 683:    */     {
/* 684:825 */       HashMap<String, String> filters = new HashMap();
/* 685:826 */       filters.put("indicadorCompra", "true");
/* 686:827 */       this.listaPersonaResponsable = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filters);
/* 687:    */     }
/* 688:829 */     return this.listaPersonaResponsable;
/* 689:    */   }
/* 690:    */   
/* 691:    */   public void setListaPersonaResponsable(List<PersonaResponsable> listaPersonaResponsable)
/* 692:    */   {
/* 693:833 */     this.listaPersonaResponsable = listaPersonaResponsable;
/* 694:    */   }
/* 695:    */   
/* 696:    */   public boolean isIndicadorOrdenCompraConPersonaResponsable()
/* 697:    */   {
/* 698:837 */     return ParametrosSistema.isOrdenCompraConPersonaResponsable(AppUtil.getOrganizacion().getId()).booleanValue();
/* 699:    */   }
/* 700:    */   
/* 701:    */   private void cargarPersonaResponsable()
/* 702:    */   {
/* 703:    */     List<EntidadUsuario> listaUsuario;
/* 704:842 */     if (isIndicadorOrdenCompraConPersonaResponsable())
/* 705:    */     {
/* 706:844 */       HashMap<String, String> filters = new HashMap();
/* 707:845 */       filters.put("idUsuario", "" + AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 708:    */       
/* 709:847 */       listaUsuario = this.servicioUsuario.obtenerListaCombo("", true, filters);
/* 710:850 */       if ((listaUsuario != null) && (!listaUsuario.isEmpty()) && (((EntidadUsuario)listaUsuario.get(0)).getEmpleado() != null)) {
/* 711:851 */         for (PersonaResponsable personaResponsable : getListaPersonaResponsable()) {
/* 712:852 */           if ((personaResponsable.getEmpleado() != null) && 
/* 713:853 */             (personaResponsable.getEmpleado().getIdEmpleado() == ((EntidadUsuario)listaUsuario.get(0)).getEmpleado().getIdEmpleado()))
/* 714:    */           {
/* 715:854 */             this.anticipoProveedor.setPersonaResponsable(personaResponsable);
/* 716:855 */             break;
/* 717:    */           }
/* 718:    */         }
/* 719:    */       }
/* 720:    */     }
/* 721:    */   }
/* 722:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.AnticipoProveedorBean
 * JD-Core Version:    0.7.0.1
 */