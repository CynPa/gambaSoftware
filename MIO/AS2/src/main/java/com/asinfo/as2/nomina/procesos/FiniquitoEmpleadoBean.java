/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.DetalleFiniquitoEmpleado;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  12:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  13:    */ import com.asinfo.as2.entities.Rubro;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  19:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  20:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  21:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioComprobanteRol;
/*  22:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioFiniquitoEmpleado;
/*  23:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  24:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  25:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  26:    */ import com.asinfo.as2.util.AppUtil;
/*  27:    */ import java.math.BigDecimal;
/*  28:    */ import java.text.ParseException;
/*  29:    */ import java.text.SimpleDateFormat;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Date;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.List;
/*  34:    */ import javax.annotation.PostConstruct;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.faces.bean.ManagedBean;
/*  37:    */ import javax.faces.bean.ViewScoped;
/*  38:    */ import javax.faces.context.ExternalContext;
/*  39:    */ import javax.faces.context.FacesContext;
/*  40:    */ import javax.servlet.http.HttpSession;
/*  41:    */ import org.apache.log4j.Logger;
/*  42:    */ import org.primefaces.component.datatable.DataTable;
/*  43:    */ 
/*  44:    */ @ManagedBean
/*  45:    */ @ViewScoped
/*  46:    */ public class FiniquitoEmpleadoBean
/*  47:    */   extends PageControllerAS2
/*  48:    */ {
/*  49:    */   private static final long serialVersionUID = 3819353449102087178L;
/*  50:    */   @EJB
/*  51:    */   private transient ServicioFiniquitoEmpleado servicioFiniquitoEmpleado;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioDocumento servicioDocumento;
/*  54:    */   private transient ServicioComprobanteRol servicioComprobanteRol;
/*  55:    */   @EJB
/*  56:    */   private transient ServicioRubro servicioRubro;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioPagoRol servicioPagoRol;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  63:    */   private HistoricoEmpleado historicoEmpleado;
/*  64:    */   private List<Documento> listaDocumento;
/*  65:    */   private PagoRolEmpleadoRubro pagoRolEmpleadoRubro;
/*  66:    */   private boolean editar;
/*  67: 87 */   private boolean simular = false;
/*  68:    */   private Date fechaSalida;
/*  69:    */   private BigDecimal totalIngresos;
/*  70:    */   private BigDecimal totalEgresos;
/*  71:    */   private Integer idHistoricoEmpleado;
/*  72:    */   private DataTable dtPagoRolEmpleadoRubro;
/*  73:    */   private boolean deshabilitarEmpleado;
/*  74:    */   
/*  75:    */   @PostConstruct
/*  76:    */   public void init()
/*  77:    */   {
/*  78:110 */     ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/*  79:111 */     HttpSession session = (HttpSession)context.getSession(true);
/*  80:112 */     if (session.getAttribute("idHistoricoEmpleado") != null) {
/*  81:113 */       this.idHistoricoEmpleado = ((Integer)session.getAttribute("idHistoricoEmpleado"));
/*  82:    */     }
/*  83:115 */     if (session.getAttribute("simular") != null) {
/*  84:116 */       this.simular = ((Boolean)session.getAttribute("simular")).booleanValue();
/*  85:    */     }
/*  86:118 */     if (session.getAttribute("fechaSalida") != null)
/*  87:    */     {
/*  88:119 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*  89:    */       try
/*  90:    */       {
/*  91:121 */         this.fechaSalida = sdf.parse((String)session.getAttribute("fechaSalida"));
/*  92:    */       }
/*  93:    */       catch (ParseException e)
/*  94:    */       {
/*  95:123 */         e.printStackTrace();
/*  96:    */       }
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void generarFiniquito()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:134 */       this.historicoEmpleado = this.servicioFiniquitoEmpleado.generarFiniquito(this.historicoEmpleado);
/* 105:    */     }
/* 106:    */     catch (ExcepcionAS2Nomina e)
/* 107:    */     {
/* 108:136 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 109:137 */       LOG.error("ERROR AL GENERAR FINIQUITO ", e);
/* 110:    */     }
/* 111:    */     catch (ExcepcionAS2 e)
/* 112:    */     {
/* 113:139 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 114:140 */       LOG.error("ERROR AL GENERAR FINIQUITO ", e);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void procesarFiniquito()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:147 */       AppUtil.setAtributo("mapaPagoRolEmpleadoRubro", null);
/* 123:148 */       this.historicoEmpleado.setPagoRolEmpleadoFiniquito(this.servicioFiniquitoEmpleado.procesarFiniquito(this.historicoEmpleado
/* 124:149 */         .getPagoRolEmpleadoFiniquito(), this.simular));
/* 125:    */       
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:155 */       this.servicioPagoRol.procesarPagoRol(this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol(), AppUtil.getOrganizacion()
/* 131:156 */         .getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), Integer.valueOf(this.historicoEmpleado.getEmpleado().getIdEmpleado()), true);
/* 132:    */       
/* 133:158 */       this.servicioFiniquitoEmpleado.refrescarDatosFiniquito(this.historicoEmpleado.getPagoRolEmpleadoFiniquito());
/* 134:    */       
/* 135:160 */       this.historicoEmpleado = this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getHistoricoEmpleadoFiniquito();
/* 136:161 */       totalPagoRolEmpleadoRubro();
/* 137:162 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 138:    */     }
/* 139:    */     catch (ExcepcionAS2Nomina e)
/* 140:    */     {
/* 141:164 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 142:165 */       e.printStackTrace();
/* 143:    */     }
/* 144:    */     catch (ExcepcionAS2Financiero e)
/* 145:    */     {
/* 146:167 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 147:168 */       e.printStackTrace();
/* 148:    */     }
/* 149:    */     catch (ExcepcionAS2 e)
/* 150:    */     {
/* 151:170 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 152:171 */       e.printStackTrace();
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void editarFiniquito()
/* 157:    */   {
/* 158:177 */     if ((this.historicoEmpleado.getEstadoFiniquito() != null) && (this.historicoEmpleado.getEstadoFiniquito().equals(Estado.SIN_ESTADO))) {
/* 159:178 */       this.historicoEmpleado.setEstadoFiniquito(Estado.ELABORADO);
/* 160:    */     }
/* 161:181 */     PagoRolEmpleado pagoRolEmpleado = this.historicoEmpleado.getPagoRolEmpleadoFiniquito();
/* 162:    */     
/* 163:183 */     this.historicoEmpleado.setPagoRolEmpleadoFiniquito(pagoRolEmpleado);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String editar()
/* 167:    */   {
/* 168:191 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String guardar()
/* 172:    */   {
/* 173:    */     try
/* 174:    */     {
/* 175:201 */       this.servicioFiniquitoEmpleado.guardarFiniquito(this.historicoEmpleado.getPagoRolEmpleadoFiniquito());
/* 176:202 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 177:203 */       setEditado(false);
/* 178:204 */       limpiar();
/* 179:205 */       return "/paginas/nomina/procesos/salidaEmpleado?faces-redirect=true";
/* 180:    */     }
/* 181:    */     catch (ExcepcionAS2Nomina e)
/* 182:    */     {
/* 183:207 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 184:208 */       e.printStackTrace();
/* 185:209 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 186:    */     }
/* 187:    */     catch (Exception e)
/* 188:    */     {
/* 189:211 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 190:212 */       e.printStackTrace();
/* 191:213 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 192:    */     }
/* 193:215 */     return "";
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String eliminar()
/* 197:    */   {
/* 198:    */     try
/* 199:    */     {
/* 200:225 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 201:    */     }
/* 202:    */     catch (Exception e)
/* 203:    */     {
/* 204:227 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 205:228 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 206:    */     }
/* 207:230 */     return "";
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String cargarDatos()
/* 211:    */   {
/* 212:237 */     return "";
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String cancelar()
/* 216:    */   {
/* 217:242 */     return "/paginas/nomina/procesos/salidaEmpleado?faces-redirect=true";
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String regresarIngresoEmpleado()
/* 221:    */   {
/* 222:246 */     return "/paginas/nomina/procesos/historicoEmpleado?faces-redirect=true";
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<Rubro> autocompletarRubro(String consulta)
/* 226:    */   {
/* 227:255 */     List<Rubro> listaRubro = new ArrayList();
/* 228:256 */     HashMap<String, String> filters = new HashMap();
/* 229:257 */     filters.put("nombre", consulta.trim());
/* 230:258 */     listaRubro = this.servicioRubro.obtenerListaCombo("", false, filters);
/* 231:260 */     for (PagoRolEmpleadoRubro prer : getListaPagoRolEmpleadoRubro()) {
/* 232:261 */       listaRubro.remove(prer.getRubro());
/* 233:    */     }
/* 234:264 */     return listaRubro;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public HistoricoEmpleado cargarHistoricoEmpleado()
/* 238:    */   {
/* 239:273 */     if (this.idHistoricoEmpleado != null) {
/* 240:274 */       this.historicoEmpleado = this.servicioHistoricoEmpleado.cargarDetalle(this.idHistoricoEmpleado.intValue(), true);
/* 241:    */     }
/* 242:276 */     if (this.simular)
/* 243:    */     {
/* 244:277 */       this.historicoEmpleado.setFechaSalida(this.fechaSalida);
/* 245:278 */       this.historicoEmpleado.setFechaFiniquito(this.fechaSalida);
/* 246:    */     }
/* 247:280 */     if (this.editar)
/* 248:    */     {
/* 249:281 */       editarFiniquito();
/* 250:282 */       totalPagoRolEmpleadoRubro();
/* 251:    */     }
/* 252:    */     else
/* 253:    */     {
/* 254:284 */       generarFiniquito();
/* 255:285 */       procesarFiniquito();
/* 256:    */     }
/* 257:288 */     return this.historicoEmpleado;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void comprobantePagoRol()
/* 261:    */   {
/* 262:    */     try
/* 263:    */     {
/* 264:299 */       this.servicioComprobanteRol.contabilizar(this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol());
/* 265:300 */       this.historicoEmpleado.setEstadoFiniquito(Estado.CONTABILIZADO);
/* 266:    */       
/* 267:302 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 268:    */     }
/* 269:    */     catch (ExcepcionAS2Financiero e)
/* 270:    */     {
/* 271:304 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 272:    */     }
/* 273:    */     catch (ExcepcionAS2Nomina e)
/* 274:    */     {
/* 275:306 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 276:    */     }
/* 277:    */     catch (ExcepcionAS2 e)
/* 278:    */     {
/* 279:308 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 280:    */     }
/* 281:    */     catch (Exception e)
/* 282:    */     {
/* 283:310 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 284:311 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<PagoRolEmpleadoRubro> getListaPagoRolEmpleadoRubro()
/* 289:    */   {
/* 290:320 */     List<PagoRolEmpleadoRubro> lista = new ArrayList();
/* 291:321 */     if (this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getListaPagoRolEmpleadoRubro() != null) {
/* 292:322 */       for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getListaPagoRolEmpleadoRubro()) {
/* 293:323 */         if (!pagoRolEmpleadoRubro.isEliminado()) {
/* 294:324 */           lista.add(pagoRolEmpleadoRubro);
/* 295:    */         }
/* 296:    */       }
/* 297:    */     }
/* 298:328 */     return lista;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 302:    */   {
/* 303:337 */     return this.historicoEmpleado;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 307:    */   {
/* 308:347 */     this.historicoEmpleado = historicoEmpleado;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public DataTable getDtPagoRolEmpleadoRubro()
/* 312:    */   {
/* 313:351 */     return this.dtPagoRolEmpleadoRubro;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setDtPagoRolEmpleadoRubro(DataTable dtPagoRolEmpleadoRubro)
/* 317:    */   {
/* 318:355 */     this.dtPagoRolEmpleadoRubro = dtPagoRolEmpleadoRubro;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public PagoRolEmpleadoRubro getPagoRolEmpleadoRubro()
/* 322:    */   {
/* 323:359 */     return this.pagoRolEmpleadoRubro;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setPagoRolEmpleadoRubro(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 327:    */   {
/* 328:363 */     this.pagoRolEmpleadoRubro = pagoRolEmpleadoRubro;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public List<Documento> getListaDocumento()
/* 332:    */   {
/* 333:    */     try
/* 334:    */     {
/* 335:373 */       if (this.listaDocumento == null) {
/* 336:374 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FINIQUITO, AppUtil.getOrganizacion().getId());
/* 337:    */       }
/* 338:    */     }
/* 339:    */     catch (ExcepcionAS2 e)
/* 340:    */     {
/* 341:377 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 342:    */     }
/* 343:380 */     return this.listaDocumento;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public boolean isDeshabilitarEmpleado()
/* 347:    */   {
/* 348:389 */     return this.deshabilitarEmpleado;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setDeshabilitarEmpleado(boolean deshabilitarEmpleado)
/* 352:    */   {
/* 353:399 */     this.deshabilitarEmpleado = deshabilitarEmpleado;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public String agregarDetalle()
/* 357:    */   {
/* 358:408 */     this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getListaPagoRolEmpleadoRubro().add(creaDetallePagoRolEmpleadoRubro());
/* 359:409 */     return "";
/* 360:    */   }
/* 361:    */   
/* 362:    */   public PagoRolEmpleadoRubro creaDetallePagoRolEmpleadoRubro()
/* 363:    */   {
/* 364:418 */     PagoRolEmpleadoRubro pagoRolEmpleadoRubroNew = new PagoRolEmpleadoRubro();
/* 365:419 */     pagoRolEmpleadoRubroNew.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 366:420 */     pagoRolEmpleadoRubroNew.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 367:    */     
/* 368:422 */     pagoRolEmpleadoRubroNew.setRubro(null);
/* 369:423 */     pagoRolEmpleadoRubroNew.setValor(BigDecimal.ZERO);
/* 370:424 */     pagoRolEmpleadoRubroNew.setTiempo(BigDecimal.ZERO);
/* 371:425 */     pagoRolEmpleadoRubroNew.setIndicadorManual(true);
/* 372:426 */     return pagoRolEmpleadoRubroNew;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void totalPagoRolEmpleadoRubro()
/* 376:    */   {
/* 377:435 */     this.totalIngresos = BigDecimal.ZERO;
/* 378:436 */     this.totalEgresos = BigDecimal.ZERO;
/* 379:437 */     if (this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getListaPagoRolEmpleadoRubro() != null) {
/* 380:438 */       for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getListaPagoRolEmpleadoRubro()) {
/* 381:439 */         if ((!pagoRolEmpleadoRubro.isIndicadorProvision()) && (pagoRolEmpleadoRubro.getRubro().getOperacion() == 1) && 
/* 382:440 */           (pagoRolEmpleadoRubro.isEliminado() != true)) {
/* 383:441 */           this.totalIngresos = this.totalIngresos.add(pagoRolEmpleadoRubro.getValor());
/* 384:442 */         } else if ((pagoRolEmpleadoRubro.getRubro().getOperacion() < 0) && (pagoRolEmpleadoRubro.isEliminado() != true)) {
/* 385:443 */           this.totalEgresos = this.totalEgresos.add(pagoRolEmpleadoRubro.getValor());
/* 386:    */         }
/* 387:    */       }
/* 388:    */     }
/* 389:    */   }
/* 390:    */   
/* 391:    */   public String eliminarDetalle()
/* 392:    */   {
/* 393:454 */     PagoRolEmpleadoRubro prer = (PagoRolEmpleadoRubro)this.dtPagoRolEmpleadoRubro.getRowData();
/* 394:455 */     if (prer.getRubro() == null)
/* 395:    */     {
/* 396:456 */       this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getListaPagoRolEmpleadoRubro().remove(prer);
/* 397:457 */       return "";
/* 398:    */     }
/* 399:459 */     prer.setEliminado(true);
/* 400:460 */     totalPagoRolEmpleadoRubro();
/* 401:461 */     return "";
/* 402:    */   }
/* 403:    */   
/* 404:    */   public Integer getIdHistoricoEmpleado()
/* 405:    */   {
/* 406:469 */     return this.idHistoricoEmpleado;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void setIdHistoricoEmpleado(Integer idHistoricoEmpleado)
/* 410:    */   {
/* 411:477 */     this.idHistoricoEmpleado = idHistoricoEmpleado;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public BigDecimal getTotalIngresos()
/* 415:    */   {
/* 416:481 */     return this.totalIngresos;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void setTotalIngresos(BigDecimal totalIngresos)
/* 420:    */   {
/* 421:485 */     this.totalIngresos = totalIngresos;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public BigDecimal getTotalEgresos()
/* 425:    */   {
/* 426:489 */     return this.totalEgresos;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public void setTotalEgresos(BigDecimal totalEgresos)
/* 430:    */   {
/* 431:493 */     this.totalEgresos = totalEgresos;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public boolean isEditar()
/* 435:    */   {
/* 436:497 */     return this.editar;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setEditar(boolean editar)
/* 440:    */   {
/* 441:501 */     this.editar = editar;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public boolean isSimular()
/* 445:    */   {
/* 446:508 */     return this.simular;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void setSimular(boolean simular)
/* 450:    */   {
/* 451:516 */     this.simular = simular;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public Date getFechaInicioDecimoTerceroMensual()
/* 455:    */   {
/* 456:520 */     Date fechaInicioDecimoTerceroMensual = null;
/* 457:521 */     for (DetalleFiniquitoEmpleado detalleFiniquitoEmpleado : this.historicoEmpleado.getListaDetalleFiniquitoEmpleado()) {
/* 458:522 */       if (detalleFiniquitoEmpleado.isIndicadorImpresionSobre())
/* 459:    */       {
/* 460:523 */         fechaInicioDecimoTerceroMensual = detalleFiniquitoEmpleado.getFecha();
/* 461:524 */         break;
/* 462:    */       }
/* 463:    */     }
/* 464:527 */     return fechaInicioDecimoTerceroMensual;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public String limpiar()
/* 468:    */   {
/* 469:533 */     return null;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public Date getFechaSalida()
/* 473:    */   {
/* 474:540 */     return this.fechaSalida;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public void setFechaSalida(Date fechaSalida)
/* 478:    */   {
/* 479:548 */     this.fechaSalida = fechaSalida;
/* 480:    */   }
/* 481:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.FiniquitoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */