/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteMovimientoBancarioDao;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.Banco;
/*   7:    */ import com.asinfo.as2.entities.ConceptoContable;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.FormaPago;
/*  13:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioConceptoContable;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario;
/*  20:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteMovimientoBancario;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  23:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  24:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  25:    */ import java.io.IOException;
/*  26:    */ import java.math.BigDecimal;
/*  27:    */ import java.util.ArrayList;
/*  28:    */ import java.util.Calendar;
/*  29:    */ import java.util.Date;
/*  30:    */ import java.util.HashMap;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import javax.annotation.PostConstruct;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ViewScoped;
/*  37:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  38:    */ import javax.faces.model.SelectItem;
/*  39:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  40:    */ import net.sf.jasperreports.engine.JRException;
/*  41:    */ import org.apache.log4j.Logger;
/*  42:    */ import org.primefaces.component.datatable.DataTable;
/*  43:    */ import org.primefaces.component.selectonemenu.SelectOneMenu;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class ReporteMovimientoBancarioBean
/*  48:    */   extends AbstractBaseReportBean
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = -8575219747524044522L;
/*  51:    */   @EJB
/*  52:    */   private ServicioReporteMovimientoBancario servicioReporteMovimientoBancario;
/*  53:    */   @EJB
/*  54:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  55:    */   @EJB
/*  56:    */   private ServicioConceptoContable servicioConceptoContable;
/*  57:    */   @EJB
/*  58:    */   ServicioMovimientoBancario servicioMovimientoBancario;
/*  59:    */   @EJB
/*  60:    */   private ReporteMovimientoBancarioDao reporteMovimientoBancarioDao;
/*  61:    */   private Date fechaDesde;
/*  62:    */   private Date fechaHasta;
/*  63:    */   private ConceptoContable conceptoContable;
/*  64:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  65:    */   private int idCuentaBancariaOrganizacion;
/*  66:    */   private int idFormaPago;
/*  67:    */   private boolean indicadorEstado;
/*  68:    */   private boolean giradosNocobrados;
/*  69:    */   private TipoReporte tipoReporte;
/*  70:    */   
/*  71:    */   static enum TipoReporte
/*  72:    */   {
/*  73: 45 */     VALORES_COBRADOS("Valores Cobrados"),  VALORES_PAGADOS("Valores Pagados");
/*  74:    */     
/*  75:    */     public String nombre;
/*  76:    */     
/*  77:    */     private TipoReporte(String nombre)
/*  78:    */     {
/*  79: 50 */       this.nombre = nombre;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83: 87 */   private BigDecimal debe = BigDecimal.ZERO;
/*  84: 88 */   private BigDecimal haber = BigDecimal.ZERO;
/*  85:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo;
/*  86:    */   private List<SelectItem> listaEstadoCombo;
/*  87:    */   private List<MovimientoBancario> listaMovimientoBancario;
/*  88:    */   private DataTable dtMovimientoBancario;
/*  89:    */   
/*  90:    */   protected JRDataSource getJRDataSource()
/*  91:    */   {
/*  92:100 */     List<MovimientoBancario> listaDatosReporte = new ArrayList();
/*  93:101 */     List<Object[]> listaReporte = new ArrayList();
/*  94:102 */     JRDataSource ds = null;
/*  95:    */     try
/*  96:    */     {
/*  97:105 */       listaDatosReporte = this.servicioReporteMovimientoBancario.getReporteMovimientoBancario(this.fechaDesde, this.fechaHasta, this.idCuentaBancariaOrganizacion, this.idFormaPago, 
/*  98:106 */         getConceptoContable().getId(), isIndicadorEstado(), AppUtil.getOrganizacion().getId(), isGiradosNocobrados(), null, this.tipoReporte != null ? this.tipoReporte
/*  99:107 */         .name() : null);
/* 100:108 */       String a = "n/a";
/* 101:    */       
/* 102:110 */       Map<Integer, BigDecimal> mapCBO = new HashMap();
/* 103:111 */       for (MovimientoBancario movimientoBancario : listaDatosReporte)
/* 104:    */       {
/* 105:113 */         CuentaBancariaOrganizacion cbo = movimientoBancario.getCuentaBancariaOrganizacion();
/* 106:114 */         if (!mapCBO.containsKey(Integer.valueOf(cbo.getIdCuentaBancariaOrganizacion())))
/* 107:    */         {
/* 108:115 */           BigDecimal saldoInicial = this.reporteMovimientoBancarioDao.getSaldoFecha(cbo, this.fechaDesde);
/* 109:116 */           mapCBO.put(Integer.valueOf(cbo.getIdCuentaBancariaOrganizacion()), saldoInicial);
/* 110:    */         }
/* 111:119 */         Object[] objeto = new Object[16];
/* 112:120 */         objeto[0] = movimientoBancario.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre();
/* 113:121 */         objeto[1] = movimientoBancario.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero();
/* 114:122 */         objeto[2] = (movimientoBancario.getConceptoContable() != null ? movimientoBancario.getConceptoContable().getNombre() : a);
/* 115:123 */         objeto[3] = movimientoBancario.getDocumentoReferencia();
/* 116:124 */         objeto[4] = movimientoBancario.getFecha();
/* 117:125 */         objeto[5] = movimientoBancario.getDocumento().getNombre();
/* 118:126 */         objeto[6] = movimientoBancario.getBeneficiario();
/* 119:127 */         objeto[7] = movimientoBancario.getDescripcion();
/* 120:128 */         objeto[8] = movimientoBancario.getEstado();
/* 121:129 */         objeto[9] = movimientoBancario.getFormaPago().getNombre();
/* 122:130 */         BigDecimal debe = movimientoBancario.getDetalleAsiento().getDebe();
/* 123:131 */         objeto[10] = (debe != null ? debe : BigDecimal.ZERO);
/* 124:132 */         BigDecimal haber = movimientoBancario.getDetalleAsiento().getHaber();
/* 125:133 */         objeto[11] = (haber != null ? haber : BigDecimal.ZERO);
/* 126:134 */         objeto[12] = movimientoBancario.getDetalleAsiento().getAsiento().getNumero();
/* 127:135 */         objeto[13] = movimientoBancario.getDetalleAsiento().getAsiento().getTipoAsiento().getNombre();
/* 128:136 */         objeto[14] = mapCBO.get(Integer.valueOf(cbo.getIdCuentaBancariaOrganizacion()));
/* 129:137 */         objeto[15] = movimientoBancario.getDetalleAsiento().getDescripcion();
/* 130:138 */         listaReporte.add(objeto);
/* 131:    */       }
/* 132:141 */       String[] fields = { "f_banco", "f_numeroCuenta", "f_conceptoContable", "f_documentoReferencia", "f_fecha", "f_documento", "f_beneficiario", "f_descripcion", "f_estado", "f_formaPago", "f_debe", "f_haber", "f_numeroAsiento", "f_tipoAsiento", "f_saldoInicial", "f_descripcion_asiento" };
/* 133:    */       
/* 134:    */ 
/* 135:    */ 
/* 136:145 */       ds = new QueryResultDataSource(listaReporte, fields);
/* 137:    */     }
/* 138:    */     catch (Exception e)
/* 139:    */     {
/* 140:147 */       LOG.info("Error " + e);
/* 141:148 */       e.printStackTrace();
/* 142:    */     }
/* 143:150 */     return ds;
/* 144:    */   }
/* 145:    */   
/* 146:    */   @PostConstruct
/* 147:    */   public void init()
/* 148:    */   {
/* 149:155 */     Calendar calfechaDesde = Calendar.getInstance();
/* 150:156 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 151:157 */     this.fechaDesde = calfechaDesde.getTime();
/* 152:158 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected String getCompileFileName()
/* 156:    */   {
/* 157:168 */     return "reporteMovimientoBancario";
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected Map<String, Object> getReportParameters()
/* 161:    */   {
/* 162:179 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 163:180 */     reportParameters.put("ReportTitle", "Movimientos Bancarios");
/* 164:181 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 165:182 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 166:183 */     return reportParameters;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String execute()
/* 170:    */   {
/* 171:    */     try
/* 172:    */     {
/* 173:189 */       validarDatos();
/* 174:    */       
/* 175:191 */       super.prepareReport();
/* 176:    */     }
/* 177:    */     catch (JRException e)
/* 178:    */     {
/* 179:194 */       LOG.info("Error JRException");
/* 180:195 */       e.printStackTrace();
/* 181:196 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 182:    */     }
/* 183:    */     catch (IOException e)
/* 184:    */     {
/* 185:198 */       LOG.info("Error IOException");
/* 186:199 */       e.printStackTrace();
/* 187:200 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 188:    */     }
/* 189:203 */     return "";
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void validarDatos()
/* 193:    */   {
/* 194:207 */     if (this.fechaDesde == null) {
/* 195:208 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 196:    */     }
/* 197:210 */     if (this.fechaHasta == null) {
/* 198:211 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void actualizarFormaPago(AjaxBehaviorEvent event)
/* 203:    */   {
/* 204:216 */     int idCuentaBancariaOrganizacion = ((Integer)((SelectOneMenu)event.getSource()).getValue()).intValue();
/* 205:217 */     if (idCuentaBancariaOrganizacion != 0) {
/* 206:218 */       this.cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(idCuentaBancariaOrganizacion);
/* 207:    */     } else {
/* 208:220 */       this.cuentaBancariaOrganizacion = new CuentaBancariaOrganizacion();
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String procesar()
/* 213:    */   {
/* 214:227 */     validarDatos();
/* 215:    */     
/* 216:229 */     this.listaMovimientoBancario = this.servicioReporteMovimientoBancario.getReporteMovimientoBancario(this.fechaDesde, this.fechaHasta, this.idCuentaBancariaOrganizacion, this.idFormaPago, 
/* 217:230 */       getConceptoContable().getId(), isIndicadorEstado(), AppUtil.getOrganizacion().getId(), 
/* 218:231 */       isGiradosNocobrados(), null, this.tipoReporte != null ? this.tipoReporte.name() : null);
/* 219:    */     
/* 220:233 */     calcularTotal(this.listaMovimientoBancario);
/* 221:    */     
/* 222:235 */     return "";
/* 223:    */   }
/* 224:    */   
/* 225:    */   private void calcularTotal(List<MovimientoBancario> listaMovimientoBancario)
/* 226:    */   {
/* 227:239 */     this.debe = BigDecimal.ZERO;
/* 228:240 */     this.haber = BigDecimal.ZERO;
/* 229:241 */     for (MovimientoBancario mb : listaMovimientoBancario)
/* 230:    */     {
/* 231:242 */       this.haber = this.haber.add(mb.getDetalleAsiento().getHaber());
/* 232:243 */       this.debe = this.debe.add(mb.getDetalleAsiento().getDebe());
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Date getFechaDesde()
/* 237:    */   {
/* 238:253 */     return this.fechaDesde;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setFechaDesde(Date fechaDesde)
/* 242:    */   {
/* 243:263 */     this.fechaDesde = fechaDesde;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Date getFechaHasta()
/* 247:    */   {
/* 248:272 */     return this.fechaHasta;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setFechaHasta(Date fechaHasta)
/* 252:    */   {
/* 253:282 */     this.fechaHasta = fechaHasta;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public ConceptoContable getConceptoContable()
/* 257:    */   {
/* 258:291 */     if (this.conceptoContable == null) {
/* 259:292 */       this.conceptoContable = new ConceptoContable();
/* 260:    */     }
/* 261:294 */     return this.conceptoContable;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setConceptoContable(ConceptoContable conceptoContable)
/* 265:    */   {
/* 266:304 */     this.conceptoContable = conceptoContable;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 270:    */   {
/* 271:313 */     if (this.cuentaBancariaOrganizacion == null) {
/* 272:314 */       this.cuentaBancariaOrganizacion = new CuentaBancariaOrganizacion();
/* 273:    */     }
/* 274:316 */     return this.cuentaBancariaOrganizacion;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 278:    */   {
/* 279:326 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public int getIdCuentaBancariaOrganizacion()
/* 283:    */   {
/* 284:335 */     return this.idCuentaBancariaOrganizacion;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setIdCuentaBancariaOrganizacion(int idCuentaBancariaOrganizacion)
/* 288:    */   {
/* 289:345 */     this.idCuentaBancariaOrganizacion = idCuentaBancariaOrganizacion;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public int getIdFormaPago()
/* 293:    */   {
/* 294:354 */     return this.idFormaPago;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setIdFormaPago(int idFormaPago)
/* 298:    */   {
/* 299:364 */     this.idFormaPago = idFormaPago;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public BigDecimal getDebe()
/* 303:    */   {
/* 304:373 */     return this.debe;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setDebe(BigDecimal debe)
/* 308:    */   {
/* 309:383 */     this.debe = debe;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public BigDecimal getHaber()
/* 313:    */   {
/* 314:392 */     return this.haber;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setHaber(BigDecimal haber)
/* 318:    */   {
/* 319:402 */     this.haber = haber;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacionCombo()
/* 323:    */   {
/* 324:411 */     if (this.listaCuentaBancariaOrganizacionCombo == null) {
/* 325:412 */       this.listaCuentaBancariaOrganizacionCombo = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, true, null);
/* 326:    */     }
/* 327:414 */     return this.listaCuentaBancariaOrganizacionCombo;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setListaCuentaBancariaOrganizacionCombo(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo)
/* 331:    */   {
/* 332:424 */     this.listaCuentaBancariaOrganizacionCombo = listaCuentaBancariaOrganizacionCombo;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public List<SelectItem> getListaEstadoCombo()
/* 336:    */   {
/* 337:433 */     if (this.listaEstadoCombo == null)
/* 338:    */     {
/* 339:434 */       this.listaEstadoCombo = new ArrayList();
/* 340:435 */       for (Estado estado : Estado.values()) {
/* 341:436 */         if ((estado.equals(Estado.CONTABILIZADO)) || (estado.equals(Estado.ANULADO)))
/* 342:    */         {
/* 343:437 */           SelectItem selectItem = new SelectItem(estado, estado.name());
/* 344:438 */           this.listaEstadoCombo.add(selectItem);
/* 345:    */         }
/* 346:    */       }
/* 347:    */     }
/* 348:442 */     return this.listaEstadoCombo;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setListaEstadoCombo(List<SelectItem> listaEstadoCombo)
/* 352:    */   {
/* 353:452 */     this.listaEstadoCombo = listaEstadoCombo;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<MovimientoBancario> getListaMovimientoBancario()
/* 357:    */   {
/* 358:461 */     return this.listaMovimientoBancario;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setListaMovimientoBancario(List<MovimientoBancario> listaMovimientoBancario)
/* 362:    */   {
/* 363:471 */     this.listaMovimientoBancario = listaMovimientoBancario;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public List<ConceptoContable> autocompletarConceptoContable(String consulta)
/* 367:    */   {
/* 368:476 */     List<ConceptoContable> lista = new ArrayList();
/* 369:477 */     String sortField = "codigo";
/* 370:478 */     HashMap<String, String> filters = new HashMap();
/* 371:479 */     filters.put("nombre", consulta.trim() + "%");
/* 372:480 */     lista = this.servicioConceptoContable.obtenerListaCombo(sortField, true, filters);
/* 373:481 */     return lista;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public boolean isIndicadorEstado()
/* 377:    */   {
/* 378:490 */     return this.indicadorEstado;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setIndicadorEstado(boolean indicadorEstado)
/* 382:    */   {
/* 383:500 */     this.indicadorEstado = indicadorEstado;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public DataTable getDtMovimientoBancario()
/* 387:    */   {
/* 388:509 */     return this.dtMovimientoBancario;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void setDtMovimientoBancario(DataTable dtMovimientoBancario)
/* 392:    */   {
/* 393:519 */     this.dtMovimientoBancario = dtMovimientoBancario;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public boolean isGiradosNocobrados()
/* 397:    */   {
/* 398:523 */     return this.giradosNocobrados;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void setGiradosNocobrados(boolean giradosNocobrados)
/* 402:    */   {
/* 403:527 */     this.giradosNocobrados = giradosNocobrados;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public List<SelectItem> getListaTipoReporte()
/* 407:    */   {
/* 408:531 */     List<SelectItem> listaSelectItems = new ArrayList();
/* 409:532 */     for (TipoReporte tipoReporte : TipoReporte.values())
/* 410:    */     {
/* 411:533 */       SelectItem item = new SelectItem(tipoReporte, tipoReporte.nombre);
/* 412:534 */       listaSelectItems.add(item);
/* 413:    */     }
/* 414:536 */     return listaSelectItems;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public TipoReporte getTipoReporte()
/* 418:    */   {
/* 419:540 */     return this.tipoReporte;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 423:    */   {
/* 424:544 */     this.tipoReporte = tipoReporte;
/* 425:    */   }
/* 426:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.ReporteMovimientoBancarioBean
 * JD-Core Version:    0.7.0.1
 */