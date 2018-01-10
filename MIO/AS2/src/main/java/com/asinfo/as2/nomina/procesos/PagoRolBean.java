/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.FormaPago;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PagoRol;
/*  12:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  13:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  14:    */ import com.asinfo.as2.entities.Quincena;
/*  15:    */ import com.asinfo.as2.entities.Rubro;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.enumeraciones.DiaMes;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  21:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*  22:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  25:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  26:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargaEmpleado;
/*  27:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*  28:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  29:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  30:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioComprobanteRol;
/*  31:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  32:    */ import com.asinfo.as2.util.AppUtil;
/*  33:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  34:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  35:    */ import java.util.ArrayList;
/*  36:    */ import java.util.Calendar;
/*  37:    */ import java.util.Collection;
/*  38:    */ import java.util.Date;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Map;
/*  42:    */ import javax.annotation.PostConstruct;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.faces.bean.ManagedBean;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import javax.faces.context.FacesContext;
/*  47:    */ import javax.faces.context.PartialViewContext;
/*  48:    */ import javax.faces.model.SelectItem;
/*  49:    */ import org.apache.log4j.Logger;
/*  50:    */ import org.primefaces.component.datatable.DataTable;
/*  51:    */ import org.primefaces.context.RequestContext;
/*  52:    */ import org.primefaces.model.LazyDataModel;
/*  53:    */ import org.primefaces.model.SortOrder;
/*  54:    */ 
/*  55:    */ @ManagedBean
/*  56:    */ @ViewScoped
/*  57:    */ public class PagoRolBean
/*  58:    */   extends PageControllerAS2
/*  59:    */ {
/*  60:    */   private static final long serialVersionUID = 1L;
/*  61:    */   @EJB
/*  62:    */   private ServicioPagoRol servicioPagoRol;
/*  63:    */   @EJB
/*  64:    */   private ServicioDocumento servicioDocumento;
/*  65:    */   @EJB
/*  66:    */   private ServicioQuincena servicioQuincena;
/*  67:    */   @EJB
/*  68:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  69:    */   @EJB
/*  70:    */   private ServicioFormaPago servicioFormaPago;
/*  71:    */   @EJB
/*  72:    */   private ServicioRubro servicioRubro;
/*  73:    */   @EJB
/*  74:    */   private ServicioComprobanteRol servicioComprobanteRol;
/*  75:    */   @EJB
/*  76:    */   private ServicioCargaEmpleado servicioCargaEmpleado;
/*  77:    */   private PagoRol pagoRol;
/*  78:    */   private Quincena quincena;
/*  79:    */   private List<Documento> listaDocumentoPagoRol;
/*  80:    */   private List<SelectItem> listaMes;
/*  81:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  82:    */   private List<FormaPago> listaFormaPago;
/*  83:    */   private int idCuentaBancariaOrganizacion;
/*  84:    */   private int idFormaPago;
/*  85:    */   private HashMap<Integer, HashMap<Integer, PagoRolEmpleadoRubro>> mapaPagoRolEmpleadoRubro;
/*  86:    */   private boolean mostrarSaldoInicial;
/*  87:    */   private LazyDataModel<PagoRol> listaPagoRol;
/*  88:    */   List<PagoRolEmpleado> listaPagoRolEmpleadoNegativo;
/*  89:    */   private List<Quincena> listaQuincena;
/*  90:    */   private DataTable dtPagoRol;
/*  91:127 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  92:    */   
/*  93:    */   @PostConstruct
/*  94:    */   public void init()
/*  95:    */   {
/*  96:134 */     this.listaPagoRol = new LazyDataModel()
/*  97:    */     {
/*  98:    */       private static final long serialVersionUID = 1L;
/*  99:    */       
/* 100:    */       public List<PagoRol> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 101:    */       {
/* 102:141 */         List<PagoRol> lista = new ArrayList();
/* 103:142 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 104:    */         
/* 105:144 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 106:145 */         filters.put("documento.documentoBase", DocumentoBase.PAGO_ROL.toString());
/* 107:146 */         lista = PagoRolBean.this.servicioPagoRol.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 108:    */         
/* 109:148 */         PagoRolBean.this.listaPagoRol.setRowCount(PagoRolBean.this.servicioPagoRol.contarPorCriterio(filters));
/* 110:    */         
/* 111:150 */         return lista;
/* 112:    */       }
/* 113:    */     };
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void crearPagoRol()
/* 117:    */   {
/* 118:160 */     this.pagoRol = new PagoRol();
/* 119:161 */     this.pagoRol.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 120:162 */     this.pagoRol.setIdSucursal(AppUtil.getSucursal().getId());
/* 121:    */     
/* 122:164 */     Documento documento = null;
/* 123:165 */     if ((getListaDocumentoPagoRol() != null) && (!getListaDocumentoPagoRol().isEmpty()))
/* 124:    */     {
/* 125:166 */       documento = (Documento)getListaDocumentoPagoRol().get(0);
/* 126:167 */       this.pagoRol.setDocumento(documento);
/* 127:    */     }
/* 128:    */     else
/* 129:    */     {
/* 130:169 */       documento = new Documento();
/* 131:170 */       this.pagoRol.setDocumento(documento);
/* 132:    */     }
/* 133:173 */     this.pagoRol.setEstado(Estado.ELABORADO);
/* 134:174 */     this.pagoRol.setQuincena(new Quincena());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String editar()
/* 138:    */   {
/* 139:183 */     if (getPagoRol().getIdPagoRol() > 0)
/* 140:    */     {
/* 141:184 */       this.pagoRol = this.servicioPagoRol.cargarDetalle(this.pagoRol.getIdPagoRol());
/* 142:185 */       if (!this.pagoRol.getEstado().equals(Estado.CONTABILIZADO))
/* 143:    */       {
/* 144:186 */         this.quincena = this.pagoRol.getQuincena();
/* 145:187 */         setEditado(true);
/* 146:    */       }
/* 147:    */       else
/* 148:    */       {
/* 149:189 */         LOG.info("No entro en if");
/* 150:190 */         addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 151:    */       }
/* 152:    */     }
/* 153:    */     else
/* 154:    */     {
/* 155:193 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 156:    */     }
/* 157:195 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String guardar()
/* 161:    */   {
/* 162:    */     try
/* 163:    */     {
/* 164:205 */       this.servicioPagoRol.guardar(this.pagoRol);
/* 165:206 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 166:207 */       setEditado(false);
/* 167:208 */       limpiar();
/* 168:    */     }
/* 169:    */     catch (ExcepcionAS2Financiero e)
/* 170:    */     {
/* 171:210 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 172:211 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 173:    */     }
/* 174:    */     catch (Exception e)
/* 175:    */     {
/* 176:213 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 177:214 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 178:    */     }
/* 179:216 */     return "";
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String eliminar()
/* 183:    */   {
/* 184:225 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 185:226 */     return "";
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String cargarDatos()
/* 189:    */   {
/* 190:235 */     return "";
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String limpiar()
/* 194:    */   {
/* 195:244 */     crearPagoRol();
/* 196:245 */     return "";
/* 197:    */   }
/* 198:    */   
/* 199:    */   private void validarRubros()
/* 200:    */   {
/* 201:255 */     String nombre = "";
/* 202:256 */     int idRubro = ParametrosSistema.getRubroSalarioUnificado(AppUtil.getOrganizacion().getIdOrganizacion()).intValue();
/* 203:257 */     Rubro rubro = this.servicioRubro.buscarPorId(idRubro);
/* 204:258 */     if (rubro == null)
/* 205:    */     {
/* 206:259 */       nombre = Parametro.RUBRO_SALARIO_UNIFICADO.getNombre();
/* 207:260 */       addErrorMessage(getLanguageController().getMensaje("msg_error_parametrizacion_rubros") + nombre);
/* 208:    */     }
/* 209:262 */     idRubro = ParametrosSistema.getRubroAportePatronalIESS(AppUtil.getOrganizacion().getIdOrganizacion()).intValue();
/* 210:263 */     rubro = this.servicioRubro.buscarPorId(idRubro);
/* 211:264 */     if (rubro == null)
/* 212:    */     {
/* 213:265 */       nombre = Parametro.RUBRO_APORTE_PERSONAL_IESS.getNombre();
/* 214:266 */       addErrorMessage(getLanguageController().getMensaje("msg_error_parametrizacion_rubros") + nombre);
/* 215:    */     }
/* 216:268 */     idRubro = ParametrosSistema.getRubroAportePersonalIESS(AppUtil.getOrganizacion().getIdOrganizacion()).intValue();
/* 217:269 */     rubro = this.servicioRubro.buscarPorId(idRubro);
/* 218:270 */     if (rubro == null)
/* 219:    */     {
/* 220:271 */       nombre = Parametro.RUBRO_APORTE_PATRONAL_IESS.getNombre();
/* 221:272 */       addErrorMessage(getLanguageController().getMensaje("msg_error_parametrizacion_rubros") + nombre);
/* 222:    */     }
/* 223:275 */     idRubro = ParametrosSistema.getRubroImpuestoALARenta(AppUtil.getOrganizacion().getIdOrganizacion()).intValue();
/* 224:276 */     rubro = this.servicioRubro.buscarPorId(idRubro);
/* 225:277 */     if (rubro == null)
/* 226:    */     {
/* 227:278 */       nombre = Parametro.RUBRO_IMPUESTO_A_LA_RENTA.getNombre();
/* 228:279 */       addErrorMessage(getLanguageController().getMensaje("msg_error_parametrizacion_rubros") + nombre);
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String procesarPagoRol()
/* 233:    */   {
/* 234:    */     try
/* 235:    */     {
/* 236:286 */       validarRubros();
/* 237:287 */       this.pagoRol = ((PagoRol)this.dtPagoRol.getRowData());
/* 238:    */       
/* 239:289 */       this.servicioCargaEmpleado.actualizaCargasActivas(this.pagoRol.getIdOrganizacion(), this.pagoRol.getFecha());
/* 240:    */       
/* 241:291 */       AppUtil.setAtributo("mapaPagoRolEmpleadoRubro", null);
/* 242:292 */       PagoRol pr = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/* 243:293 */       if (pr.getEstado() == Estado.CONTABILIZADO)
/* 244:    */       {
/* 245:294 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 246:295 */         return "";
/* 247:    */       }
/* 248:298 */       if (!this.pagoRol.getIndicadorGeneradoPorUtilidad().booleanValue()) {
/* 249:301 */         this.servicioPagoRol.actualizarPagoRolDias(this.pagoRol, AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), null);
/* 250:    */       }
/* 251:307 */       this.servicioPagoRol.actualizarPagoRolEmpleadoRubro(this.pagoRol, null);
/* 252:    */       
/* 253:309 */       this.pagoRol = this.servicioPagoRol.procesarPagoRol(this.pagoRol, AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), null, false);
/* 254:310 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 255:311 */       setEditado(false);
/* 256:312 */       this.listaPagoRolEmpleadoNegativo = this.pagoRol.getListaPagoRolEmpleadoNegativo();
/* 257:313 */       if (this.listaPagoRolEmpleadoNegativo.size() > 0)
/* 258:    */       {
/* 259:314 */         FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelPagoRolEmpleadoNegativo");
/* 260:315 */         RequestContext.getCurrentInstance().execute("dialogoPagoRolEmpleadoNegativo.show()");
/* 261:    */       }
/* 262:317 */       limpiar();
/* 263:    */     }
/* 264:    */     catch (ExcepcionAS2Nomina e)
/* 265:    */     {
/* 266:320 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 267:321 */       LOG.error("ERROR AL PROCESAR PAGO ROL", e);
/* 268:    */     }
/* 269:    */     catch (ExcepcionAS2Financiero e)
/* 270:    */     {
/* 271:323 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 272:324 */       LOG.error("ERROR AL PROCESAR PAGO ROL", e);
/* 273:    */     }
/* 274:    */     catch (ExcepcionAS2 e)
/* 275:    */     {
/* 276:326 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 277:327 */       LOG.error("ERROR AL PROCESAR PAGO ROL", e);
/* 278:    */     }
/* 279:330 */     return "";
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String pasarRubrosVariables()
/* 283:    */   {
/* 284:    */     try
/* 285:    */     {
/* 286:335 */       pasarPagoRol();
/* 287:336 */       return "rubroVariableEmpleado?faces-redirect=true";
/* 288:    */     }
/* 289:    */     catch (ExcepcionAS2Financiero e)
/* 290:    */     {
/* 291:338 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 292:    */     }
/* 293:339 */     return "";
/* 294:    */   }
/* 295:    */   
/* 296:    */   public String pasarDiasFalta()
/* 297:    */   {
/* 298:    */     try
/* 299:    */     {
/* 300:346 */       pasarPagoRol();
/* 301:347 */       return "diasFalta?faces-redirect=true";
/* 302:    */     }
/* 303:    */     catch (ExcepcionAS2Financiero e)
/* 304:    */     {
/* 305:349 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 306:    */     }
/* 307:350 */     return "";
/* 308:    */   }
/* 309:    */   
/* 310:    */   private void pasarPagoRol()
/* 311:    */     throws ExcepcionAS2Financiero
/* 312:    */   {
/* 313:356 */     this.servicioPagoRol.esEditable(this.pagoRol);
/* 314:357 */     AppUtil.removeAtributo("pago_rol");
/* 315:358 */     AppUtil.setAtributo("pago_rol", this.pagoRol);
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void anularAsientoPagoRol()
/* 319:    */   {
/* 320:    */     try
/* 321:    */     {
/* 322:363 */       this.pagoRol = ((PagoRol)this.dtPagoRol.getRowData());
/* 323:364 */       this.servicioPagoRol.anular(this.pagoRol);
/* 324:365 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 325:    */     }
/* 326:    */     catch (ExcepcionAS2Financiero e)
/* 327:    */     {
/* 328:367 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 329:368 */       LOG.info("ERROR AL Anular Asiento Pago Rol", e);
/* 330:    */     }
/* 331:    */     catch (Exception e)
/* 332:    */     {
/* 333:370 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 334:371 */       LOG.error("ERROR AL ANULAR DATOS", e);
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void comprobantePagoRol()
/* 339:    */   {
/* 340:    */     try
/* 341:    */     {
/* 342:378 */       this.pagoRol = ((PagoRol)this.dtPagoRol.getRowData());
/* 343:379 */       this.pagoRol = this.servicioPagoRol.cargarDetalle(this.pagoRol.getIdPagoRol());
/* 344:380 */       this.servicioComprobanteRol.contabilizar(this.pagoRol);
/* 345:381 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 346:    */     }
/* 347:    */     catch (ExcepcionAS2Financiero e)
/* 348:    */     {
/* 349:383 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 350:    */     }
/* 351:    */     catch (ExcepcionAS2Nomina e)
/* 352:    */     {
/* 353:385 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 354:    */     }
/* 355:    */     catch (ExcepcionAS2 e)
/* 356:    */     {
/* 357:387 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 358:    */     }
/* 359:    */     catch (AS2Exception e)
/* 360:    */     {
/* 361:389 */       this.exContabilizacion = e;
/* 362:390 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 363:391 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 364:    */     }
/* 365:    */   }
/* 366:    */   
/* 367:    */   public String setearPagoRolSeleccionado()
/* 368:    */   {
/* 369:396 */     PagoRol pagoRolSeleccionado = (PagoRol)this.dtPagoRol.getRowData();
/* 370:397 */     setPagoRol(pagoRolSeleccionado);
/* 371:398 */     return "";
/* 372:    */   }
/* 373:    */   
/* 374:    */   public String selectQuincena()
/* 375:    */   {
/* 376:405 */     this.quincena = this.servicioQuincena.buscarPorId(this.pagoRol.getQuincena().getIdQuincena());
/* 377:406 */     int dia = this.quincena.getDiaPago().ordinal();
/* 378:407 */     int mes = 0;
/* 379:408 */     if (this.quincena.getMesPago() == 0) {
/* 380:409 */       mes = this.pagoRol.getMes();
/* 381:    */     } else {
/* 382:411 */       mes = this.quincena.getMesPago();
/* 383:    */     }
/* 384:413 */     int anio = this.pagoRol.getAnio();
/* 385:414 */     Date fecha = null;
/* 386:415 */     if (this.quincena.getDiaPago() != DiaMes.CERO) {
/* 387:416 */       fecha = FuncionesUtiles.getFecha(dia, mes, anio);
/* 388:    */     } else {
/* 389:418 */       fecha = FuncionesUtiles.getFechaFinMes(anio, mes);
/* 390:    */     }
/* 391:420 */     this.pagoRol.setMes(mes);
/* 392:421 */     this.pagoRol.setFecha(fecha);
/* 393:422 */     return "";
/* 394:    */   }
/* 395:    */   
/* 396:    */   public String selectMes()
/* 397:    */   {
/* 398:427 */     int anio = this.pagoRol.getAnio();
/* 399:428 */     int mes = this.pagoRol.getMes();
/* 400:429 */     int dia = this.quincena.getDiaPago().ordinal();
/* 401:    */     
/* 402:431 */     Date fecha = null;
/* 403:432 */     if (this.quincena.getDiaPago() != DiaMes.CERO) {
/* 404:433 */       fecha = FuncionesUtiles.getFecha(dia, mes, anio);
/* 405:    */     } else {
/* 406:435 */       fecha = FuncionesUtiles.getFechaFinMes(anio, mes);
/* 407:    */     }
/* 408:437 */     this.pagoRol.setFecha(fecha);
/* 409:438 */     return "";
/* 410:    */   }
/* 411:    */   
/* 412:    */   public String changeAnio()
/* 413:    */   {
/* 414:443 */     int anio = this.pagoRol.getAnio();
/* 415:444 */     int mes = this.pagoRol.getMes();
/* 416:445 */     int dia = FuncionesUtiles.getDiaFecha(this.pagoRol.getFecha());
/* 417:446 */     Date fecha = null;
/* 418:447 */     fecha = FuncionesUtiles.getFecha(dia, mes, anio);
/* 419:448 */     this.pagoRol.setFecha(fecha);
/* 420:449 */     return "";
/* 421:    */   }
/* 422:    */   
/* 423:    */   public PagoRol getPagoRol()
/* 424:    */   {
/* 425:461 */     if (this.pagoRol == null) {
/* 426:462 */       crearPagoRol();
/* 427:    */     }
/* 428:464 */     return this.pagoRol;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setPagoRol(PagoRol pagoRol)
/* 432:    */   {
/* 433:474 */     this.pagoRol = pagoRol;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public LazyDataModel<PagoRol> getListaPagoRol()
/* 437:    */   {
/* 438:483 */     return this.listaPagoRol;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setListaPagoRol(LazyDataModel<PagoRol> listaPagoRol)
/* 442:    */   {
/* 443:493 */     this.listaPagoRol = listaPagoRol;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public DataTable getDtPagoRol()
/* 447:    */   {
/* 448:502 */     return this.dtPagoRol;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void setDtPagoRol(DataTable dtPagoRol)
/* 452:    */   {
/* 453:512 */     this.dtPagoRol = dtPagoRol;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public List<Documento> getListaDocumentoPagoRol()
/* 457:    */   {
/* 458:    */     try
/* 459:    */     {
/* 460:522 */       if (this.listaDocumentoPagoRol == null) {
/* 461:523 */         this.listaDocumentoPagoRol = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PAGO_ROL);
/* 462:    */       }
/* 463:    */     }
/* 464:    */     catch (ExcepcionAS2 e)
/* 465:    */     {
/* 466:526 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 467:    */     }
/* 468:529 */     return this.listaDocumentoPagoRol;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void setListaDocumentoPagoRol(List<Documento> listaDocumentoPagoRol)
/* 472:    */   {
/* 473:539 */     this.listaDocumentoPagoRol = listaDocumentoPagoRol;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public List<SelectItem> getListaMes()
/* 477:    */   {
/* 478:543 */     if (this.listaMes == null)
/* 479:    */     {
/* 480:544 */       this.listaMes = new ArrayList();
/* 481:545 */       for (Mes t : Mes.values())
/* 482:    */       {
/* 483:546 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 484:547 */         this.listaMes.add(item);
/* 485:    */       }
/* 486:    */     }
/* 487:550 */     return this.listaMes;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public List<PagoRolEmpleado> getListaPagoRolEmpleadoNegativo()
/* 491:    */   {
/* 492:554 */     return this.listaPagoRolEmpleadoNegativo;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public List<Quincena> getListaQuincena()
/* 496:    */   {
/* 497:563 */     if (this.listaQuincena == null)
/* 498:    */     {
/* 499:564 */       Map<String, String> filtros = new HashMap();
/* 500:565 */       filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 501:566 */       filtros.put("indicadorFiniquito", "false");
/* 502:567 */       this.listaQuincena = this.servicioQuincena.obtenerListaCombo("nombre", true, filtros);
/* 503:    */     }
/* 504:569 */     return this.listaQuincena;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 508:    */   {
/* 509:578 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 510:579 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 511:    */     }
/* 512:581 */     return this.listaCuentaBancariaOrganizacion;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 516:    */   {
/* 517:591 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public List<FormaPago> getListaFormaPago()
/* 521:    */   {
/* 522:600 */     if (this.listaFormaPago == null) {
/* 523:601 */       this.listaFormaPago = this.servicioFormaPago.obtenerListaCombo(null, false, null);
/* 524:    */     }
/* 525:603 */     return this.listaFormaPago;
/* 526:    */   }
/* 527:    */   
/* 528:    */   public void setListaFormaPago(List<FormaPago> listaFormaPago)
/* 529:    */   {
/* 530:613 */     this.listaFormaPago = listaFormaPago;
/* 531:    */   }
/* 532:    */   
/* 533:    */   public int getIdCuentaBancariaOrganizacion()
/* 534:    */   {
/* 535:622 */     return this.idCuentaBancariaOrganizacion;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public void setIdCuentaBancariaOrganizacion(int idCuentaBancariaOrganizacion)
/* 539:    */   {
/* 540:632 */     this.idCuentaBancariaOrganizacion = idCuentaBancariaOrganizacion;
/* 541:    */   }
/* 542:    */   
/* 543:    */   public int getIdFormaPago()
/* 544:    */   {
/* 545:641 */     return this.idFormaPago;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public void setIdFormaPago(int idFormaPago)
/* 549:    */   {
/* 550:651 */     this.idFormaPago = idFormaPago;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public HashMap<Integer, HashMap<Integer, PagoRolEmpleadoRubro>> getMapaPagoRolEmpleadoRubro()
/* 554:    */   {
/* 555:660 */     return this.mapaPagoRolEmpleadoRubro;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public void setMapaPagoRolEmpleadoRubro(HashMap<Integer, HashMap<Integer, PagoRolEmpleadoRubro>> mapaPagoRolEmpleadoRubro)
/* 559:    */   {
/* 560:670 */     this.mapaPagoRolEmpleadoRubro = mapaPagoRolEmpleadoRubro;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public Quincena getQuincena()
/* 564:    */   {
/* 565:679 */     return this.quincena;
/* 566:    */   }
/* 567:    */   
/* 568:    */   public void setQuincena(Quincena quincena)
/* 569:    */   {
/* 570:689 */     this.quincena = quincena;
/* 571:    */   }
/* 572:    */   
/* 573:    */   public boolean isMostrarSaldoInicial()
/* 574:    */   {
/* 575:698 */     Calendar calendario = Calendar.getInstance();
/* 576:699 */     calendario.setTime(ParametrosSistema.getFechaMaximaSaldosIniciales(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 577:700 */     int day = calendario.get(5);
/* 578:701 */     int month = calendario.get(2) + 1;
/* 579:702 */     int year = calendario.get(1);
/* 580:    */     
/* 581:704 */     Date fecha = FuncionesUtiles.getFecha(day, month, year);
/* 582:    */     
/* 583:706 */     this.mostrarSaldoInicial = FuncionesUtiles.compararFechaAnteriorOIgual(new Date(), fecha);
/* 584:    */     
/* 585:708 */     return this.mostrarSaldoInicial;
/* 586:    */   }
/* 587:    */   
/* 588:    */   public AS2Exception getExContabilizacion()
/* 589:    */   {
/* 590:717 */     return this.exContabilizacion;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 594:    */   {
/* 595:727 */     this.exContabilizacion = exContabilizacion;
/* 596:    */   }
/* 597:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.PagoRolBean
 * JD-Core Version:    0.7.0.1
 */