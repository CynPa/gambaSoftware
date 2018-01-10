/*   1:    */ package com.asinfo.as2.ventas.procesos.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.dao.AsientoDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   8:    */ import com.asinfo.as2.entities.Asiento;
/*   9:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.GuiaAerea;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.aerolineas.Cass;
/*  15:    */ import com.asinfo.as2.entities.aerolineas.ConfiguracionCass;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  19:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  20:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  23:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  24:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  25:    */ import com.asinfo.as2.util.AppUtil;
/*  26:    */ import com.asinfo.as2.utils.JsfUtil;
/*  27:    */ import java.io.IOException;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import javax.annotation.PostConstruct;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ViewScoped;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ import org.primefaces.component.datatable.DataTable;
/*  39:    */ import org.primefaces.event.FileUploadEvent;
/*  40:    */ import org.primefaces.model.LazyDataModel;
/*  41:    */ import org.primefaces.model.SortOrder;
/*  42:    */ 
/*  43:    */ @ManagedBean
/*  44:    */ @ViewScoped
/*  45:    */ public class CargaCassBean
/*  46:    */   extends PageControllerAS2
/*  47:    */ {
/*  48:    */   static final long serialVersionUID = 1L;
/*  49:    */   @EJB
/*  50:    */   private ServicioGenerico<Cass> servicioCass;
/*  51:    */   @EJB
/*  52:    */   private ServicioDocumento servicioDocumento;
/*  53:    */   @EJB
/*  54:    */   private ServicioGenerico<ConfiguracionCass> servicioConfiguracionCass;
/*  55:    */   @EJB
/*  56:    */   private ServicioCuentaContable servicioCuentaContable;
/*  57:    */   @EJB
/*  58:    */   private ServicioAsiento servicioAsiento;
/*  59:    */   @EJB
/*  60:    */   private AsientoDao asientoDao;
/*  61:    */   @EJB
/*  62:    */   private ServicioSecuencia servicioSecuencia;
/*  63:    */   @EJB
/*  64:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  65:    */   private LazyDataModel<Cass> listaCass;
/*  66:    */   private DataTable dtCass;
/*  67:    */   private Cass cass;
/*  68:    */   private List<GuiaAerea> listaCassAWM;
/*  69:    */   private List<GuiaAerea> listaCcrDcr;
/*  70:    */   private List<GuiaAerea> listaGuiaAereaAgrupada;
/*  71:    */   private BigDecimal totalDebito;
/*  72:    */   private BigDecimal totalCredito;
/*  73:    */   private List<Documento> listaDocumentoAerolinea;
/*  74:    */   private BigDecimal totalPesoCargaAWM;
/*  75:    */   private BigDecimal totalChargesDueCarrierPpAWM;
/*  76:    */   private BigDecimal totalWeightChargeCcAWM;
/*  77:    */   private BigDecimal totalOtherChargesDueCarrierCcAWM;
/*  78:    */   private BigDecimal totalOtherChargesDueAgentCcAWM;
/*  79:    */   private BigDecimal totalRateOfExchangeAWM;
/*  80:    */   private BigDecimal totalDiscountAWM;
/*  81:    */   private BigDecimal totalRateOfExchangeCcrDcrCcoDco;
/*  82:    */   private BigDecimal totalWeightChargeCcrDcrCcoDco;
/*  83:    */   private BigDecimal totalChargesDueAgentCcrDcrCcoDco;
/*  84:    */   private BigDecimal totalChargesDueCarrierCcrDcrCcoDco;
/*  85:    */   private BigDecimal totalDiscountCcrDcrCcoDco;
/*  86:    */   private BigDecimal totalCommissionCcrDcrCcoDco;
/*  87:    */   private BigDecimal totalWGTChargeAgrupado;
/*  88:    */   private BigDecimal dueCarrierAgrupado;
/*  89:    */   private BigDecimal wGTChargeCollectAgrupado;
/*  90:    */   private BigDecimal otherAgentAgrupado;
/*  91:    */   private BigDecimal disctAgrupado;
/*  92:    */   private BigDecimal commAgrupado;
/*  93:    */   private BigDecimal taxComAgrupado;
/*  94:    */   private BigDecimal ivaAgt3080Agrupado;
/*  95:    */   private BigDecimal ivaRet7020Agrupado;
/*  96:    */   private BigDecimal isrlAgrupado;
/*  97:    */   private BigDecimal amountAgrupado;
/*  98:    */   private Asiento asiento;
/*  99:    */   
/* 100:    */   @PostConstruct
/* 101:    */   public void init()
/* 102:    */   {
/* 103:111 */     this.listaCass = new LazyDataModel()
/* 104:    */     {
/* 105:    */       private static final long serialVersionUID = 1L;
/* 106:    */       
/* 107:    */       public List<Cass> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 108:    */       {
/* 109:118 */         List<Cass> lista = new ArrayList();
/* 110:119 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 111:120 */         List<String> listaCampos = new ArrayList();
/* 112:121 */         listaCampos.add("asiento.tipoAsiento");
/* 113:    */         
/* 114:123 */         lista = CargaCassBean.this.servicioCass.obtenerListaPorPagina(Cass.class, startIndex, pageSize, sortField, ordenar, filters, listaCampos);
/* 115:124 */         CargaCassBean.this.listaCass.setRowCount(CargaCassBean.this.servicioCass.contarPorCriterio(Cass.class, filters));
/* 116:    */         
/* 117:126 */         return lista;
/* 118:    */       }
/* 119:    */     };
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String migrarCass(FileUploadEvent event)
/* 123:    */     throws IOException
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:136 */       this.listaCassAWM = new ArrayList();
/* 128:137 */       this.listaCcrDcr = new ArrayList();
/* 129:138 */       this.totalCredito = BigDecimal.ZERO;
/* 130:139 */       this.totalDebito = BigDecimal.ZERO;
/* 131:140 */       this.asiento = new Asiento();
/* 132:141 */       this.listaGuiaAereaAgrupada = new ArrayList();
/* 133:    */       
/* 134:143 */       this.servicioInterfazContableProceso.migrarCass(event, getCass(), getAsiento(), getTotalCredito(), getTotalDebito(), getListaCassAWM(), 
/* 135:144 */         getListaCcrDcr(), getListaGuiaAereaAgrupada(), AppUtil.getOrganizacion().getIdOrganizacion());
/* 136:145 */       totales(getCass(), getCass().getAsiento());
/* 137:    */     }
/* 138:    */     catch (AS2Exception e)
/* 139:    */     {
/* 140:148 */       JsfUtil.addErrorMessage(e, "");
/* 141:149 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 142:150 */       e.printStackTrace();
/* 143:    */     }
/* 144:    */     catch (ExcepcionAS2 e)
/* 145:    */     {
/* 146:153 */       e.printStackTrace();
/* 147:    */     }
/* 148:156 */     return "";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void totales(Cass cass, Asiento asiento)
/* 152:    */   {
/* 153:161 */     for (GuiaAerea ga : cass.getListaGuiasAereas())
/* 154:    */     {
/* 155:162 */       if (ga.getRecordId().equals("AWM"))
/* 156:    */       {
/* 157:164 */         setTotalPesoCargaAWM(getTotalPesoCargaAWM().add(ga.getWeightChargePp()));
/* 158:165 */         setTotalChargesDueCarrierPpAWM(getTotalChargesDueCarrierPpAWM().add(ga.getChargesDueCarrierPp()));
/* 159:166 */         setTotalWeightChargeCcAWM(getTotalWeightChargeCcAWM().add(ga.getWeightChargeCc()));
/* 160:167 */         setTotalOtherChargesDueCarrierCcAWM(getTotalOtherChargesDueCarrierCcAWM().add(ga.getOtherChargesDueCarrierCc()));
/* 161:168 */         setTotalOtherChargesDueAgentCcAWM(getTotalOtherChargesDueAgentCcAWM().add(ga.getOtherChargesDueAgentCc()));
/* 162:169 */         setTotalRateOfExchangeAWM(getTotalRateOfExchangeAWM().add(ga.getRateOfExchange()));
/* 163:170 */         setTotalDiscountAWM(getTotalDiscountAWM().add(ga.getDiscount()));
/* 164:    */       }
/* 165:172 */       if ((ga.getRecordId().substring(0, 3).equals("DCR")) || (ga.getRecordId().substring(0, 3).equals("DCO")))
/* 166:    */       {
/* 167:173 */         setTotalRateOfExchangeCcrDcrCcoDco(getTotalRateOfExchangeCcrDcrCcoDco().add(ga.getRateOfExchange()));
/* 168:174 */         setTotalWeightChargeCcrDcrCcoDco(getTotalWeightChargeCcrDcrCcoDco().add(ga.getWeightCharge()));
/* 169:175 */         setTotalChargesDueAgentCcrDcrCcoDco(getTotalChargesDueAgentCcrDcrCcoDco().add(ga.getChargesDueAgent()));
/* 170:176 */         setTotalChargesDueCarrierCcrDcrCcoDco(getTotalChargesDueCarrierCcrDcrCcoDco().add(ga.getChargesDueCarrier()));
/* 171:177 */         setTotalDiscountCcrDcrCcoDco(getTotalDiscountCcrDcrCcoDco().add(ga.getDiscount()));
/* 172:178 */         setTotalCommissionCcrDcrCcoDco(getTotalCommissionCcrDcrCcoDco().add(ga.getCommission()));
/* 173:    */       }
/* 174:    */     }
/* 175:183 */     for (GuiaAerea ga : getListaGuiaAereaAgrupada())
/* 176:    */     {
/* 177:185 */       setTotalWGTChargeAgrupado(getTotalWGTChargeAgrupado().add(ga.getTotalWgtCharge()));
/* 178:186 */       setDueCarrierAgrupado(getDueCarrierAgrupado().add(ga.getTotalDueCarrier()));
/* 179:187 */       setwGTChargeCollectAgrupado(getwGTChargeCollectAgrupado().add(ga.getTotalWgtChargeCollect()));
/* 180:188 */       setOtherAgentAgrupado(getOtherAgentAgrupado().add(ga.getTotalOtherChargesDueAgentCc()));
/* 181:189 */       setDisctAgrupado(getDisctAgrupado().add(ga.getTotalDiscount()));
/* 182:190 */       setCommAgrupado(getCommAgrupado().add(ga.getTotalCommission()));
/* 183:191 */       setTaxComAgrupado(getTaxComAgrupado().add(ga.getTotalTaxCom()));
/* 184:192 */       setIvaAgt3080Agrupado(getIvaAgt3080Agrupado().add(ga.getTotalIvaAgt3080()));
/* 185:193 */       setIvaRet7020Agrupado(getIvaRet7020Agrupado().add(ga.getTotalIvaRet7020()));
/* 186:194 */       setIsrlAgrupado(getIsrlAgrupado().add(ga.getTotalIsrl()));
/* 187:195 */       setAmountAgrupado(getAmountAgrupado().add(ga.getTotalAmount()));
/* 188:    */     }
/* 189:199 */     for (DetalleAsiento da : asiento.getListaDetalleAsiento())
/* 190:    */     {
/* 191:200 */       setTotalCredito(getTotalCredito().add(da.getHaber()));
/* 192:201 */       setTotalDebito(getTotalDebito().add(da.getDebe()));
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<GuiaAerea> getListaCassAWM()
/* 197:    */   {
/* 198:207 */     if (this.listaCassAWM == null) {
/* 199:208 */       this.listaCassAWM = new ArrayList();
/* 200:    */     }
/* 201:210 */     return this.listaCassAWM;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setListaCassAWM(List<GuiaAerea> listaCassAWM)
/* 205:    */   {
/* 206:214 */     this.listaCassAWM = listaCassAWM;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Asiento getAsiento()
/* 210:    */   {
/* 211:218 */     return this.asiento == null ? (this.asiento = new Asiento()) : this.asiento;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setAsiento(Asiento asiento)
/* 215:    */   {
/* 216:222 */     this.asiento = asiento;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public BigDecimal getTotalDebito()
/* 220:    */   {
/* 221:226 */     return this.totalDebito == null ? BigDecimal.ZERO : this.totalDebito;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setTotalDebito(BigDecimal totalDebito)
/* 225:    */   {
/* 226:230 */     this.totalDebito = totalDebito;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public BigDecimal getTotalCredito()
/* 230:    */   {
/* 231:234 */     return this.totalCredito == null ? BigDecimal.ZERO : this.totalCredito;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setTotalCredito(BigDecimal totalCredito)
/* 235:    */   {
/* 236:238 */     this.totalCredito = totalCredito;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<GuiaAerea> getListaCcrDcr()
/* 240:    */   {
/* 241:242 */     return this.listaCcrDcr == null ? (this.listaCcrDcr = new ArrayList()) : this.listaCcrDcr;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setListaCcrDcr(List<GuiaAerea> listaCcrDcr)
/* 245:    */   {
/* 246:246 */     this.listaCcrDcr = listaCcrDcr;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Cass getCass()
/* 250:    */   {
/* 251:250 */     return this.cass == null ? (this.cass = new Cass()) : this.cass;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setCass(Cass cass)
/* 255:    */   {
/* 256:254 */     this.cass = cass;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String editar()
/* 260:    */   {
/* 261:260 */     return null;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String guardar()
/* 265:    */   {
/* 266:    */     try
/* 267:    */     {
/* 268:267 */       this.servicioInterfazContableProceso.validarArchivoCass(this.cass.getIdOrganizacion(), this.cass.getRecordId(), this.cass.getCassAreaCode(), this.cass
/* 269:268 */         .getAirlinePrefix(), this.cass.getDatePeriodStart(), this.cass.getDatePeriodEnd(), this.cass.getDateOfBilling(), this.cass.getFileNumber(), this.cass
/* 270:269 */         .getCurrencyCode());
/* 271:270 */       this.servicioAsiento.guardar(this.asiento);
/* 272:271 */       this.cass.setNumero(this.servicioSecuencia.obtenerSecuenciaDocumento(this.cass.getDocumento().getIdDocumento(), new Date()));
/* 273:272 */       this.servicioCass.guardarValidar(getCass(), getCass().getListaGuiasAereas());
/* 274:273 */       this.servicioSecuencia.actualizarSecuencia(this.cass.getDocumento().getSecuencia(), this.cass.getNumero());
/* 275:274 */       setEditado(false);
/* 276:275 */       limpiar();
/* 277:    */       
/* 278:277 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 279:    */     }
/* 280:    */     catch (AS2Exception e)
/* 281:    */     {
/* 282:279 */       JsfUtil.addErrorMessage(e, "");
/* 283:    */     }
/* 284:    */     catch (Exception e)
/* 285:    */     {
/* 286:281 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 287:282 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 288:    */     }
/* 289:284 */     return "";
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String eliminar()
/* 293:    */   {
/* 294:    */     try
/* 295:    */     {
/* 296:290 */       Asiento asi = null;
/* 297:291 */       this.cass.setEstado(Estado.ANULADO);
/* 298:292 */       if (this.cass.getAsiento() != null)
/* 299:    */       {
/* 300:293 */         this.servicioAsiento.actualizarIndicadorAsientoAerolineas(Integer.valueOf(this.cass.getAsiento().getId()));
/* 301:294 */         asi = this.servicioAsiento.cargarDetalle(this.cass.getAsiento().getId());
/* 302:    */       }
/* 303:297 */       this.cass.getAsiento().setIndicadorAutomatico(false);
/* 304:298 */       this.asientoDao.flush();
/* 305:299 */       this.servicioAsiento.anular(asi);
/* 306:300 */       this.servicioCass.guardar(this.cass);
/* 307:301 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 308:302 */       limpiar();
/* 309:    */     }
/* 310:    */     catch (ExcepcionAS2Financiero e)
/* 311:    */     {
/* 312:304 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 313:305 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 314:    */     }
/* 315:    */     catch (Exception e)
/* 316:    */     {
/* 317:307 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 318:308 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 319:    */     }
/* 320:310 */     return "";
/* 321:    */   }
/* 322:    */   
/* 323:    */   public String limpiar()
/* 324:    */   {
/* 325:315 */     this.cass = new Cass();
/* 326:316 */     this.cass.setEditado(true);
/* 327:317 */     this.cass.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 328:318 */     this.cass.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 329:319 */     this.cass.setEstado(Estado.ELABORADO);
/* 330:320 */     this.listaCassAWM = null;
/* 331:321 */     this.listaCcrDcr = null;
/* 332:322 */     this.totalCredito = null;
/* 333:323 */     this.totalDebito = null;
/* 334:324 */     this.asiento = null;
/* 335:325 */     this.listaGuiaAereaAgrupada = null;
/* 336:326 */     return null;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public String cargarDatos()
/* 340:    */   {
/* 341:332 */     return null;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public String cancelar()
/* 345:    */   {
/* 346:336 */     limpiar();
/* 347:337 */     setEditado(false);
/* 348:338 */     return "";
/* 349:    */   }
/* 350:    */   
/* 351:    */   public LazyDataModel<Cass> getListaCass()
/* 352:    */   {
/* 353:342 */     return this.listaCass;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setListaCass(LazyDataModel<Cass> listaCass)
/* 357:    */   {
/* 358:346 */     this.listaCass = listaCass;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public DataTable getDtCass()
/* 362:    */   {
/* 363:350 */     return this.dtCass;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setDtCass(DataTable dtCass)
/* 367:    */   {
/* 368:354 */     this.dtCass = dtCass;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public BigDecimal getTotalPesoCargaAWM()
/* 372:    */   {
/* 373:358 */     return this.totalPesoCargaAWM == null ? (this.totalPesoCargaAWM = BigDecimal.ZERO) : this.totalPesoCargaAWM;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setTotalPesoCargaAWM(BigDecimal totalPesoCargaAWM)
/* 377:    */   {
/* 378:362 */     this.totalPesoCargaAWM = totalPesoCargaAWM;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public BigDecimal getTotalChargesDueCarrierPpAWM()
/* 382:    */   {
/* 383:366 */     return this.totalChargesDueCarrierPpAWM == null ? (this.totalChargesDueCarrierPpAWM = BigDecimal.ZERO) : this.totalChargesDueCarrierPpAWM;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setTotalChargesDueCarrierPpAWM(BigDecimal totalChargesDueCarrierPpAWM)
/* 387:    */   {
/* 388:370 */     this.totalChargesDueCarrierPpAWM = totalChargesDueCarrierPpAWM;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public BigDecimal getTotalWeightChargeCcAWM()
/* 392:    */   {
/* 393:374 */     return this.totalWeightChargeCcAWM == null ? (this.totalWeightChargeCcAWM = BigDecimal.ZERO) : this.totalWeightChargeCcAWM;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setTotalWeightChargeCcAWM(BigDecimal totalWeightChargeCcAWM)
/* 397:    */   {
/* 398:378 */     this.totalWeightChargeCcAWM = totalWeightChargeCcAWM;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public BigDecimal getTotalOtherChargesDueCarrierCcAWM()
/* 402:    */   {
/* 403:382 */     return this.totalOtherChargesDueCarrierCcAWM == null ? (this.totalOtherChargesDueCarrierCcAWM = BigDecimal.ZERO) : this.totalOtherChargesDueCarrierCcAWM;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setTotalOtherChargesDueCarrierCcAWM(BigDecimal totalOtherChargesDueCarrierCcAWM)
/* 407:    */   {
/* 408:386 */     this.totalOtherChargesDueCarrierCcAWM = totalOtherChargesDueCarrierCcAWM;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public BigDecimal getTotalOtherChargesDueAgentCcAWM()
/* 412:    */   {
/* 413:390 */     return this.totalOtherChargesDueAgentCcAWM == null ? (this.totalOtherChargesDueAgentCcAWM = BigDecimal.ZERO) : this.totalOtherChargesDueAgentCcAWM;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setTotalOtherChargesDueAgentCcAWM(BigDecimal totalOtherChargesDueAgentCcAWM)
/* 417:    */   {
/* 418:394 */     this.totalOtherChargesDueAgentCcAWM = totalOtherChargesDueAgentCcAWM;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public BigDecimal getTotalRateOfExchangeAWM()
/* 422:    */   {
/* 423:398 */     return this.totalRateOfExchangeAWM == null ? (this.totalRateOfExchangeAWM = BigDecimal.ZERO) : this.totalRateOfExchangeAWM;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setTotalRateOfExchangeAWM(BigDecimal totalRateOfExchangeAWM)
/* 427:    */   {
/* 428:402 */     this.totalRateOfExchangeAWM = totalRateOfExchangeAWM;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public BigDecimal getTotalDiscountAWM()
/* 432:    */   {
/* 433:406 */     return this.totalDiscountAWM == null ? (this.totalDiscountAWM = BigDecimal.ZERO) : this.totalDiscountAWM;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setTotalDiscountAWM(BigDecimal totalDiscountAWM)
/* 437:    */   {
/* 438:410 */     this.totalDiscountAWM = totalDiscountAWM;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public BigDecimal getTotalRateOfExchangeCcrDcrCcoDco()
/* 442:    */   {
/* 443:414 */     return this.totalRateOfExchangeCcrDcrCcoDco == null ? (this.totalRateOfExchangeCcrDcrCcoDco = BigDecimal.ZERO) : this.totalRateOfExchangeCcrDcrCcoDco;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setTotalRateOfExchangeCcrDcrCcoDco(BigDecimal totalRateOfExchangeCcrDcrCcoDco)
/* 447:    */   {
/* 448:418 */     this.totalRateOfExchangeCcrDcrCcoDco = totalRateOfExchangeCcrDcrCcoDco;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public BigDecimal getTotalWeightChargeCcrDcrCcoDco()
/* 452:    */   {
/* 453:422 */     return this.totalWeightChargeCcrDcrCcoDco == null ? (this.totalWeightChargeCcrDcrCcoDco = BigDecimal.ZERO) : this.totalWeightChargeCcrDcrCcoDco;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setTotalWeightChargeCcrDcrCcoDco(BigDecimal totalWeightChargeCcrDcrCcoDco)
/* 457:    */   {
/* 458:426 */     this.totalWeightChargeCcrDcrCcoDco = totalWeightChargeCcrDcrCcoDco;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public BigDecimal getTotalChargesDueAgentCcrDcrCcoDco()
/* 462:    */   {
/* 463:430 */     return this.totalChargesDueAgentCcrDcrCcoDco == null ? (this.totalChargesDueAgentCcrDcrCcoDco = BigDecimal.ZERO) : this.totalChargesDueAgentCcrDcrCcoDco;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setTotalChargesDueAgentCcrDcrCcoDco(BigDecimal totalChargesDueAgentCcrDcrCcoDco)
/* 467:    */   {
/* 468:434 */     this.totalChargesDueAgentCcrDcrCcoDco = totalChargesDueAgentCcrDcrCcoDco;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public BigDecimal getTotalChargesDueCarrierCcrDcrCcoDco()
/* 472:    */   {
/* 473:438 */     return this.totalChargesDueCarrierCcrDcrCcoDco == null ? (this.totalChargesDueCarrierCcrDcrCcoDco = BigDecimal.ZERO) : this.totalChargesDueCarrierCcrDcrCcoDco;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setTotalChargesDueCarrierCcrDcrCcoDco(BigDecimal totalChargesDueCarrierCcrDcrCcoDco)
/* 477:    */   {
/* 478:442 */     this.totalChargesDueCarrierCcrDcrCcoDco = totalChargesDueCarrierCcrDcrCcoDco;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public BigDecimal getTotalDiscountCcrDcrCcoDco()
/* 482:    */   {
/* 483:446 */     return this.totalDiscountCcrDcrCcoDco == null ? (this.totalDiscountCcrDcrCcoDco = BigDecimal.ZERO) : this.totalDiscountCcrDcrCcoDco;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setTotalDiscountCcrDcrCcoDco(BigDecimal totalDiscountCcrDcrCcoDco)
/* 487:    */   {
/* 488:450 */     this.totalDiscountCcrDcrCcoDco = totalDiscountCcrDcrCcoDco;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public List<Documento> getListaDocumentoAerolinea()
/* 492:    */   {
/* 493:    */     try
/* 494:    */     {
/* 495:455 */       if (this.listaDocumentoAerolinea == null) {
/* 496:456 */         this.listaDocumentoAerolinea = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.CARGA_ARCHIVO);
/* 497:    */       }
/* 498:    */     }
/* 499:    */     catch (ExcepcionAS2 e)
/* 500:    */     {
/* 501:459 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 502:    */     }
/* 503:462 */     return this.listaDocumentoAerolinea;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public void setListaDocumentoAerolinea(List<Documento> listaDocumentoDespacho)
/* 507:    */   {
/* 508:466 */     this.listaDocumentoAerolinea = listaDocumentoDespacho;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public List<GuiaAerea> getListaGuiaAereaAgrupada()
/* 512:    */   {
/* 513:470 */     return this.listaGuiaAereaAgrupada == null ? (this.listaGuiaAereaAgrupada = new ArrayList()) : this.listaGuiaAereaAgrupada;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public void setListaGuiaAereaAgrupada(List<GuiaAerea> listaGuiaAereaAgrupada)
/* 517:    */   {
/* 518:474 */     this.listaGuiaAereaAgrupada = listaGuiaAereaAgrupada;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public BigDecimal getTotalCommissionCcrDcrCcoDco()
/* 522:    */   {
/* 523:478 */     return this.totalCommissionCcrDcrCcoDco == null ? BigDecimal.ZERO : this.totalCommissionCcrDcrCcoDco;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public void setTotalCommissionCcrDcrCcoDco(BigDecimal totalCommissionCcrDcrCcoDco)
/* 527:    */   {
/* 528:482 */     this.totalCommissionCcrDcrCcoDco = totalCommissionCcrDcrCcoDco;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public BigDecimal getTotalWGTChargeAgrupado()
/* 532:    */   {
/* 533:486 */     return this.totalWGTChargeAgrupado == null ? BigDecimal.ZERO : this.totalWGTChargeAgrupado;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public void setTotalWGTChargeAgrupado(BigDecimal totalWGTChargeAgrupado)
/* 537:    */   {
/* 538:490 */     this.totalWGTChargeAgrupado = totalWGTChargeAgrupado;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public BigDecimal getDueCarrierAgrupado()
/* 542:    */   {
/* 543:494 */     return this.dueCarrierAgrupado == null ? BigDecimal.ZERO : this.dueCarrierAgrupado;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public void setDueCarrierAgrupado(BigDecimal dueCarrierAgrupado)
/* 547:    */   {
/* 548:498 */     this.dueCarrierAgrupado = dueCarrierAgrupado;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public BigDecimal getwGTChargeCollectAgrupado()
/* 552:    */   {
/* 553:502 */     return this.wGTChargeCollectAgrupado == null ? BigDecimal.ZERO : this.wGTChargeCollectAgrupado;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void setwGTChargeCollectAgrupado(BigDecimal wGTChargeCollectAgrupado)
/* 557:    */   {
/* 558:506 */     this.wGTChargeCollectAgrupado = wGTChargeCollectAgrupado;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public BigDecimal getOtherAgentAgrupado()
/* 562:    */   {
/* 563:510 */     return this.otherAgentAgrupado == null ? BigDecimal.ZERO : this.otherAgentAgrupado;
/* 564:    */   }
/* 565:    */   
/* 566:    */   public void setOtherAgentAgrupado(BigDecimal otherAgentAgrupado)
/* 567:    */   {
/* 568:514 */     this.otherAgentAgrupado = otherAgentAgrupado;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public BigDecimal getDisctAgrupado()
/* 572:    */   {
/* 573:518 */     return this.disctAgrupado == null ? BigDecimal.ZERO : this.disctAgrupado;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public void setDisctAgrupado(BigDecimal disctAgrupado)
/* 577:    */   {
/* 578:522 */     this.disctAgrupado = disctAgrupado;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public BigDecimal getCommAgrupado()
/* 582:    */   {
/* 583:526 */     return this.commAgrupado == null ? BigDecimal.ZERO : this.commAgrupado;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public void setCommAgrupado(BigDecimal commAgrupado)
/* 587:    */   {
/* 588:530 */     this.commAgrupado = commAgrupado;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public BigDecimal getTaxComAgrupado()
/* 592:    */   {
/* 593:534 */     return this.taxComAgrupado == null ? BigDecimal.ZERO : this.taxComAgrupado;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public void setTaxComAgrupado(BigDecimal taxComAgrupado)
/* 597:    */   {
/* 598:538 */     this.taxComAgrupado = taxComAgrupado;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public BigDecimal getIvaAgt3080Agrupado()
/* 602:    */   {
/* 603:542 */     return this.ivaAgt3080Agrupado == null ? BigDecimal.ZERO : this.ivaAgt3080Agrupado;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void setIvaAgt3080Agrupado(BigDecimal ivaAgt3080Agrupado)
/* 607:    */   {
/* 608:546 */     this.ivaAgt3080Agrupado = ivaAgt3080Agrupado;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public BigDecimal getIvaRet7020Agrupado()
/* 612:    */   {
/* 613:550 */     return this.ivaRet7020Agrupado == null ? BigDecimal.ZERO : this.ivaRet7020Agrupado;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public void setIvaRet7020Agrupado(BigDecimal ivaRet7020Agrupado)
/* 617:    */   {
/* 618:554 */     this.ivaRet7020Agrupado = ivaRet7020Agrupado;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public BigDecimal getIsrlAgrupado()
/* 622:    */   {
/* 623:558 */     return this.isrlAgrupado == null ? BigDecimal.ZERO : this.isrlAgrupado;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public void setIsrlAgrupado(BigDecimal isrlAgrupado)
/* 627:    */   {
/* 628:562 */     this.isrlAgrupado = isrlAgrupado;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public BigDecimal getAmountAgrupado()
/* 632:    */   {
/* 633:566 */     return this.amountAgrupado == null ? BigDecimal.ZERO : this.amountAgrupado;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public void setAmountAgrupado(BigDecimal amountAgrupado)
/* 637:    */   {
/* 638:570 */     this.amountAgrupado = amountAgrupado;
/* 639:    */   }
/* 640:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.CargaCassBean
 * JD-Core Version:    0.7.0.1
 */