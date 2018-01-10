/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.CentroCosto;
/*   6:    */ import com.asinfo.as2.entities.DimensionContable;
/*   7:    */ import com.asinfo.as2.entities.NivelCuenta;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
/*  12:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCuenta;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioEstadoFinanciero;
/*  17:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ManagedProperty;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.event.SelectEvent;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class ReporteEstadoFinancieroMensualBean
/*  39:    */   extends AbstractBaseReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = -8024333787771529921L;
/*  42:    */   @EJB
/*  43:    */   private ServicioEstadoFinanciero servicioEstadoFinanciero;
/*  44:    */   @EJB
/*  45:    */   private ServicioCuentaContable servicioCuentaContable;
/*  46:    */   @EJB
/*  47:    */   private ServicioCentroCosto servicioCentroCosto;
/*  48:    */   @EJB
/*  49:    */   private ServicioNivelCuenta servicioNivelCuenta;
/*  50:    */   @EJB
/*  51:    */   private ServicioSucursal servicioSucursal;
/*  52:    */   private CentroCosto centroCosto;
/*  53: 77 */   private int mesDesde = FuncionesUtiles.obtenerMesActual();
/*  54: 78 */   private int anioDesde = FuncionesUtiles.obtenerAnioActual();
/*  55: 79 */   private int mesHasta = FuncionesUtiles.obtenerMesActual();
/*  56: 80 */   private int anioHasta = FuncionesUtiles.obtenerAnioActual();
/*  57:    */   private Sucursal sucursal;
/*  58:    */   private TipoEstadoFinanciero tipoEstadoFinanciero;
/*  59:    */   private int nivel;
/*  60:    */   private boolean indicadorNIIF;
/*  61:    */   private List<SelectItem> listaMes;
/*  62:    */   private List<NivelCuenta> listaNivelCuenta;
/*  63:    */   private List<SelectItem> listaTipoEstadoFinanciero;
/*  64:    */   private List<Sucursal> listaSucursal;
/*  65:    */   private List<NivelCuenta> listaNivelCuentaCombo;
/*  66:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  67:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  68: 96 */   private String valorDimension = "";
/*  69: 97 */   private String nombreDimension = "";
/*  70:    */   private boolean flujoCajaEconomico;
/*  71:    */   
/*  72:    */   public String execute()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:105 */       super.prepareReport();
/*  77:    */     }
/*  78:    */     catch (JRException e)
/*  79:    */     {
/*  80:108 */       e.printStackTrace();
/*  81:    */     }
/*  82:    */     catch (IOException e)
/*  83:    */     {
/*  84:111 */       e.printStackTrace();
/*  85:    */     }
/*  86:114 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected Map<String, Object> getReportParameters()
/*  90:    */   {
/*  91:127 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  92:128 */     reportParameters.put("ReportTitle", this.tipoEstadoFinanciero.getNombre());
/*  93:129 */     reportParameters.put("p_anioDesde", Integer.valueOf(this.anioDesde));
/*  94:130 */     reportParameters.put("p_mesDesde", Integer.valueOf(this.mesDesde));
/*  95:131 */     reportParameters.put("p_anioHasta", Integer.valueOf(this.anioHasta));
/*  96:132 */     reportParameters.put("p_mesHasta", Integer.valueOf(this.mesHasta));
/*  97:133 */     reportParameters.put("p_sucursal", this.sucursal == null ? "" : this.sucursal.getNombre());
/*  98:134 */     reportParameters.put("p_centroCosto", this.centroCosto == null ? "" : this.centroCosto.getNombre());
/*  99:135 */     return reportParameters;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected JRDataSource getJRDataSource()
/* 103:    */   {
/* 104:147 */     JRDataSource ds = null;
/* 105:148 */     String[] fields = { "f_codigoCuenta", "f_nombreCuentaContable", "f_saldo", "f_fechaHasta" };
/* 106:    */     try
/* 107:    */     {
/* 108:151 */       List lista = this.servicioEstadoFinanciero.obtenerReporteEstadoFinancieroComparativoMensual(this.anioDesde, this.mesDesde, this.anioHasta, this.mesHasta, this.tipoEstadoFinanciero, 
/* 109:152 */         getListaDimensionContableBean().getNumeroDimension(), getValorDimension(), this.indicadorNIIF, this.sucursal, this.nivel, this.flujoCajaEconomico, AppUtil.getOrganizacion().getIdOrganizacion());
/* 110:    */       
/* 111:154 */       ds = new QueryResultDataSource(lista, fields);
/* 112:    */     }
/* 113:    */     catch (ExcepcionAS2Financiero e)
/* 114:    */     {
/* 115:156 */       LOG.info("Error " + e);
/* 116:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_porcentaje_impuesto_renta"));
/* 117:158 */       e.printStackTrace();
/* 118:    */     }
/* 119:160 */     return ds;
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected String getCompileFileName()
/* 123:    */   {
/* 124:172 */     return "reporteEstadoFinancieroMensual";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getNivelPorDefecto()
/* 128:    */   {
/* 129:181 */     int nivelPorDefecto = 0;
/* 130:182 */     if (getListaNivelCuentaCombo().isEmpty()) {
/* 131:183 */       addErrorMessage(getLanguageController().getMensaje("msg_error_nivel_cuenta_contable_no_existe"));
/* 132:    */     } else {
/* 133:185 */       for (NivelCuenta nivelCuenta : getListaNivelCuentaCombo()) {
/* 134:186 */         if (nivelCuenta.getCodigo() > nivelPorDefecto) {
/* 135:187 */           nivelPorDefecto = nivelCuenta.getCodigo();
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:191 */     return nivelPorDefecto;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List<NivelCuenta> getListaNivelCuentaCombo()
/* 143:    */   {
/* 144:200 */     if (this.listaNivelCuentaCombo == null)
/* 145:    */     {
/* 146:201 */       this.listaNivelCuentaCombo = new ArrayList();
/* 147:202 */       this.listaNivelCuentaCombo = this.servicioNivelCuenta.obtenerTodosOrdenadoDescendente();
/* 148:    */     }
/* 149:204 */     return this.listaNivelCuentaCombo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public ServicioEstadoFinanciero getServicioEstadoFinanciero()
/* 153:    */   {
/* 154:208 */     return this.servicioEstadoFinanciero;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setServicioEstadoFinanciero(ServicioEstadoFinanciero servicioEstadoFinanciero)
/* 158:    */   {
/* 159:212 */     this.servicioEstadoFinanciero = servicioEstadoFinanciero;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public ServicioCuentaContable getServicioCuentaContable()
/* 163:    */   {
/* 164:216 */     return this.servicioCuentaContable;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setServicioCuentaContable(ServicioCuentaContable servicioCuentaContable)
/* 168:    */   {
/* 169:220 */     this.servicioCuentaContable = servicioCuentaContable;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public ServicioCentroCosto getServicioCentroCosto()
/* 173:    */   {
/* 174:224 */     return this.servicioCentroCosto;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setServicioCentroCosto(ServicioCentroCosto servicioCentroCosto)
/* 178:    */   {
/* 179:228 */     this.servicioCentroCosto = servicioCentroCosto;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public ServicioNivelCuenta getServicioNivelCuenta()
/* 183:    */   {
/* 184:232 */     return this.servicioNivelCuenta;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setServicioNivelCuenta(ServicioNivelCuenta servicioNivelCuenta)
/* 188:    */   {
/* 189:236 */     this.servicioNivelCuenta = servicioNivelCuenta;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public ServicioSucursal getServicioSucursal()
/* 193:    */   {
/* 194:240 */     return this.servicioSucursal;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setServicioSucursal(ServicioSucursal servicioSucursal)
/* 198:    */   {
/* 199:244 */     this.servicioSucursal = servicioSucursal;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public CentroCosto getCentroCosto()
/* 203:    */   {
/* 204:248 */     return this.centroCosto;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setCentroCosto(CentroCosto centroCosto)
/* 208:    */   {
/* 209:252 */     this.centroCosto = centroCosto;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int getMesDesde()
/* 213:    */   {
/* 214:256 */     return this.mesDesde;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setMesDesde(int mesDesde)
/* 218:    */   {
/* 219:260 */     this.mesDesde = mesDesde;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int getAnioDesde()
/* 223:    */   {
/* 224:264 */     return this.anioDesde;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setAnioDesde(int anioDesde)
/* 228:    */   {
/* 229:268 */     this.anioDesde = anioDesde;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public int getMesHasta()
/* 233:    */   {
/* 234:272 */     return this.mesHasta;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setMesHasta(int mesHasta)
/* 238:    */   {
/* 239:276 */     this.mesHasta = mesHasta;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public int getAnioHasta()
/* 243:    */   {
/* 244:280 */     return this.anioHasta;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setAnioHasta(int anioHasta)
/* 248:    */   {
/* 249:284 */     this.anioHasta = anioHasta;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public Sucursal getSucursal()
/* 253:    */   {
/* 254:288 */     return this.sucursal;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setSucursal(Sucursal sucursal)
/* 258:    */   {
/* 259:292 */     this.sucursal = sucursal;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public int getNivel()
/* 263:    */   {
/* 264:296 */     return this.nivel;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setNivel(int nivel)
/* 268:    */   {
/* 269:300 */     this.nivel = nivel;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public List<SelectItem> getListaMes()
/* 273:    */   {
/* 274:304 */     if (this.listaMes == null)
/* 275:    */     {
/* 276:305 */       this.listaMes = new ArrayList();
/* 277:306 */       for (Mes t : Mes.values())
/* 278:    */       {
/* 279:307 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 280:308 */         this.listaMes.add(item);
/* 281:    */       }
/* 282:    */     }
/* 283:311 */     return this.listaMes;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setListaMes(List<SelectItem> listaMes)
/* 287:    */   {
/* 288:315 */     this.listaMes = listaMes;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<NivelCuenta> getListaNivelCuenta()
/* 292:    */   {
/* 293:319 */     return this.listaNivelCuenta;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setListaNivelCuenta(List<NivelCuenta> listaNivelCuenta)
/* 297:    */   {
/* 298:323 */     this.listaNivelCuenta = listaNivelCuenta;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<SelectItem> getListaTipoEstadoFinanciero()
/* 302:    */   {
/* 303:327 */     if (this.listaTipoEstadoFinanciero == null)
/* 304:    */     {
/* 305:328 */       this.listaTipoEstadoFinanciero = new ArrayList();
/* 306:329 */       SelectItem se = new SelectItem();
/* 307:330 */       se.setValue(TipoEstadoFinanciero.BALANCE_GENERAL);
/* 308:331 */       se.setLabel(TipoEstadoFinanciero.BALANCE_GENERAL.getNombre());
/* 309:332 */       this.listaTipoEstadoFinanciero.add(se);
/* 310:    */       
/* 311:334 */       se = new SelectItem();
/* 312:335 */       se.setValue(TipoEstadoFinanciero.BALANCE_RESULTADOS);
/* 313:336 */       se.setLabel(TipoEstadoFinanciero.BALANCE_RESULTADOS.getNombre());
/* 314:337 */       this.listaTipoEstadoFinanciero.add(se);
/* 315:    */     }
/* 316:339 */     return this.listaTipoEstadoFinanciero;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public List<Sucursal> getListaSucursal()
/* 320:    */   {
/* 321:343 */     if (this.listaSucursal == null) {
/* 322:344 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 323:    */     }
/* 324:346 */     return this.listaSucursal;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public TipoEstadoFinanciero getTipoEstadoFinanciero()
/* 328:    */   {
/* 329:350 */     return this.tipoEstadoFinanciero;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setTipoEstadoFinanciero(TipoEstadoFinanciero tipoEstadoFinanciero)
/* 333:    */   {
/* 334:354 */     this.tipoEstadoFinanciero = tipoEstadoFinanciero;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public boolean isIndicadorNIIF()
/* 338:    */   {
/* 339:358 */     return this.indicadorNIIF;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setIndicadorNIIF(boolean indicadorNIIF)
/* 343:    */   {
/* 344:362 */     this.indicadorNIIF = indicadorNIIF;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public boolean isFlujoCajaEconomico()
/* 348:    */   {
/* 349:366 */     return this.flujoCajaEconomico;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setFlujoCajaEconomico(boolean flujoCajaEconomico)
/* 353:    */   {
/* 354:370 */     this.flujoCajaEconomico = flujoCajaEconomico;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 358:    */   {
/* 359:373 */     return this.listaDimensionContableBean;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 363:    */   {
/* 364:377 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public String getValorDimension()
/* 368:    */   {
/* 369:381 */     return this.valorDimension;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setValorDimension(String valorDimension)
/* 373:    */   {
/* 374:385 */     this.valorDimension = valorDimension;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public String getNombreDimension()
/* 378:    */   {
/* 379:394 */     return this.nombreDimension;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setNombreDimension(String nombreDimension)
/* 383:    */   {
/* 384:404 */     this.nombreDimension = nombreDimension;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void cargarDimensionContableListener(SelectEvent event)
/* 388:    */   {
/* 389:408 */     DimensionContable dimension = (DimensionContable)event.getObject();
/* 390:    */     try
/* 391:    */     {
/* 392:410 */       this.valorDimension = dimension.getCodigo();
/* 393:411 */       this.nombreDimension = dimension.getNombre();
/* 394:    */     }
/* 395:    */     catch (Exception e)
/* 396:    */     {
/* 397:413 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 398:414 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/* 399:    */     }
/* 400:    */   }
/* 401:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReporteEstadoFinancieroMensualBean
 * JD-Core Version:    0.7.0.1
 */