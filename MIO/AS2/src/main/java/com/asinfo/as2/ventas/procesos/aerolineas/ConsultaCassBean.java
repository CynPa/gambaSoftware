/*   1:    */ package com.asinfo.as2.ventas.procesos.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.GuiaAerea;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Subempresa;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.ventas.procesos.aerolineas.servicio.ServicioGuiaAerea;
/*  10:    */ import java.math.BigDecimal;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import javax.faces.model.SelectItem;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.StreamedContent;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class ConsultaCassBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @EJB
/*  28:    */   private ServicioEmpresa servicioEmpresa;
/*  29:    */   @EJB
/*  30:    */   private ServicioGuiaAerea servicioGuiaAerea;
/*  31:    */   private Subempresa subempresa;
/*  32:    */   private Date fechasDesde;
/*  33:    */   private Date fechasHasta;
/*  34:    */   private String numeroGuia;
/*  35:    */   private StreamedContent archivo;
/*  36:    */   private List<GuiaAerea> listaCassAWM;
/*  37:    */   private List<GuiaAerea> listaCcrDcr;
/*  38: 48 */   private BigDecimal totalPesoCargaAWM = BigDecimal.ZERO;
/*  39: 49 */   private BigDecimal totalChargesDueCarrierPpAWM = BigDecimal.ZERO;
/*  40: 50 */   private BigDecimal totalWeightChargeCcAWM = BigDecimal.ZERO;
/*  41: 51 */   private BigDecimal totalOtherChargesDueCarrierCcAWM = BigDecimal.ZERO;
/*  42: 52 */   private BigDecimal totalOtherChargesDueAgentCcAWM = BigDecimal.ZERO;
/*  43: 53 */   private BigDecimal totalRateOfExchangeAWM = BigDecimal.ZERO;
/*  44: 54 */   private BigDecimal totalDiscountAWM = BigDecimal.ZERO;
/*  45: 56 */   private BigDecimal totalRateOfExchangeCcrDcrCcoDco = BigDecimal.ZERO;
/*  46: 57 */   private BigDecimal totalWeightChargeCcrDcrCcoDco = BigDecimal.ZERO;
/*  47: 58 */   private BigDecimal totalChargesDueAgentCcrDcrCcoDco = BigDecimal.ZERO;
/*  48: 59 */   private BigDecimal totalChargesDueCarrierCcrDcrCcoDco = BigDecimal.ZERO;
/*  49: 60 */   private BigDecimal totalCommissionCcrDcrCcoDco = BigDecimal.ZERO;
/*  50: 61 */   private BigDecimal totalDiscountCcrDcrCcoDco = BigDecimal.ZERO;
/*  51:    */   private DataTable dtGuiaAreas;
/*  52:    */   private List<SelectItem> listaTipoReporte;
/*  53:    */   
/*  54:    */   private static enum enumTipoReporte
/*  55:    */   {
/*  56: 66 */     Periodo,  Agencia,  Guia;
/*  57:    */     
/*  58:    */     private enumTipoReporte() {}
/*  59:    */   }
/*  60:    */   
/*  61: 76 */   private enumTipoReporte tipoReporte = enumTipoReporte.Periodo;
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65: 81 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String guardar()
/*  69:    */   {
/*  70: 87 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String eliminar()
/*  74:    */   {
/*  75: 93 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String limpiar()
/*  79:    */   {
/*  80: 99 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String cargarDatos()
/*  84:    */   {
/*  85:105 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void restablecerFiltros()
/*  89:    */   {
/*  90:109 */     this.fechasDesde = null;
/*  91:110 */     this.fechasHasta = null;
/*  92:111 */     this.numeroGuia = null;
/*  93:112 */     this.subempresa = null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void cargarDatosAgentCode()
/*  97:    */   {
/*  98:116 */     this.listaCassAWM = new ArrayList();
/*  99:117 */     this.listaCcrDcr = new ArrayList();
/* 100:118 */     this.totalPesoCargaAWM = BigDecimal.ZERO;
/* 101:119 */     this.totalChargesDueCarrierPpAWM = BigDecimal.ZERO;
/* 102:120 */     this.totalWeightChargeCcAWM = BigDecimal.ZERO;
/* 103:121 */     this.totalOtherChargesDueCarrierCcAWM = BigDecimal.ZERO;
/* 104:122 */     this.totalOtherChargesDueAgentCcAWM = BigDecimal.ZERO;
/* 105:123 */     this.totalRateOfExchangeAWM = BigDecimal.ZERO;
/* 106:124 */     this.totalDiscountAWM = BigDecimal.ZERO;
/* 107:126 */     for (GuiaAerea ga : this.servicioGuiaAerea.obtenerGuiasAereasPorAgentCode(this.subempresa != null ? this.subempresa.getCodigo() : null, 
/* 108:127 */       AppUtil.getOrganizacion().getIdOrganizacion(), this.fechasDesde, this.fechasHasta, this.numeroGuia)) {
/* 109:128 */       if (ga.getRecordId().equals("AWM"))
/* 110:    */       {
/* 111:129 */         this.listaCassAWM.add(ga);
/* 112:130 */         setTotalPesoCargaAWM(getTotalPesoCargaAWM().add(ga.getWeightChargePp()));
/* 113:131 */         setTotalChargesDueCarrierPpAWM(getTotalChargesDueCarrierPpAWM().add(ga.getChargesDueCarrierPp()));
/* 114:132 */         setTotalWeightChargeCcAWM(getTotalWeightChargeCcAWM().add(ga.getWeightChargeCc()));
/* 115:133 */         setTotalOtherChargesDueCarrierCcAWM(getTotalOtherChargesDueCarrierCcAWM().add(ga.getOtherChargesDueCarrierCc()));
/* 116:134 */         setTotalOtherChargesDueAgentCcAWM(getTotalOtherChargesDueAgentCcAWM().add(ga.getOtherChargesDueAgentCc()));
/* 117:135 */         setTotalRateOfExchangeAWM(getTotalRateOfExchangeAWM().add(ga.getRateOfExchange()));
/* 118:136 */         setTotalDiscountAWM(getTotalDiscountAWM().add(ga.getDiscount()));
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:138 */         this.listaCcrDcr.add(ga);
/* 123:139 */         setTotalRateOfExchangeCcrDcrCcoDco(getTotalRateOfExchangeCcrDcrCcoDco().add(ga.getRateOfExchange()));
/* 124:140 */         setTotalWeightChargeCcrDcrCcoDco(getTotalWeightChargeCcrDcrCcoDco().add(ga.getWeightCharge()));
/* 125:141 */         setTotalChargesDueAgentCcrDcrCcoDco(getTotalChargesDueAgentCcrDcrCcoDco().add(ga.getChargesDueAgent()));
/* 126:142 */         setTotalChargesDueCarrierCcrDcrCcoDco(getTotalChargesDueCarrierCcrDcrCcoDco().add(ga.getChargesDueCarrier()));
/* 127:143 */         setTotalDiscountCcrDcrCcoDco(getTotalDiscountCcrDcrCcoDco().add(ga.getDiscount()));
/* 128:144 */         setTotalCommissionCcrDcrCcoDco(getTotalCommissionCcrDcrCcoDco().add(ga.getCommission()));
/* 129:    */       }
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public List<Subempresa> autocompletarSubEmpresa(String consulta)
/* 134:    */   {
/* 135:151 */     return this.servicioEmpresa.autocompletarSubEmpresa(consulta, null);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List<GuiaAerea> getListaCassAWM()
/* 139:    */   {
/* 140:155 */     return this.listaCassAWM == null ? (this.listaCassAWM = new ArrayList()) : this.listaCassAWM;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setListaCassAWM(List<GuiaAerea> listaCassAWM)
/* 144:    */   {
/* 145:159 */     this.listaCassAWM = listaCassAWM;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public BigDecimal getTotalPesoCargaAWM()
/* 149:    */   {
/* 150:163 */     return this.totalPesoCargaAWM;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setTotalPesoCargaAWM(BigDecimal totalPesoCargaAWM)
/* 154:    */   {
/* 155:167 */     this.totalPesoCargaAWM = totalPesoCargaAWM;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public BigDecimal getTotalChargesDueCarrierPpAWM()
/* 159:    */   {
/* 160:171 */     return this.totalChargesDueCarrierPpAWM;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setTotalChargesDueCarrierPpAWM(BigDecimal totalChargesDueCarrierPpAWM)
/* 164:    */   {
/* 165:175 */     this.totalChargesDueCarrierPpAWM = totalChargesDueCarrierPpAWM;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public BigDecimal getTotalWeightChargeCcAWM()
/* 169:    */   {
/* 170:179 */     return this.totalWeightChargeCcAWM;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setTotalWeightChargeCcAWM(BigDecimal totalWeightChargeCcAWM)
/* 174:    */   {
/* 175:183 */     this.totalWeightChargeCcAWM = totalWeightChargeCcAWM;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public BigDecimal getTotalOtherChargesDueCarrierCcAWM()
/* 179:    */   {
/* 180:187 */     return this.totalOtherChargesDueCarrierCcAWM;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setTotalOtherChargesDueCarrierCcAWM(BigDecimal totalOtherChargesDueCarrierCcAWM)
/* 184:    */   {
/* 185:191 */     this.totalOtherChargesDueCarrierCcAWM = totalOtherChargesDueCarrierCcAWM;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public BigDecimal getTotalOtherChargesDueAgentCcAWM()
/* 189:    */   {
/* 190:195 */     return this.totalOtherChargesDueAgentCcAWM;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setTotalOtherChargesDueAgentCcAWM(BigDecimal totalOtherChargesDueAgentCcAWM)
/* 194:    */   {
/* 195:199 */     this.totalOtherChargesDueAgentCcAWM = totalOtherChargesDueAgentCcAWM;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public BigDecimal getTotalRateOfExchangeAWM()
/* 199:    */   {
/* 200:203 */     return this.totalRateOfExchangeAWM;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setTotalRateOfExchangeAWM(BigDecimal totalRateOfExchangeAWM)
/* 204:    */   {
/* 205:207 */     this.totalRateOfExchangeAWM = totalRateOfExchangeAWM;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public BigDecimal getTotalDiscountAWM()
/* 209:    */   {
/* 210:211 */     return this.totalDiscountAWM;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setTotalDiscountAWM(BigDecimal totalDiscountAWM)
/* 214:    */   {
/* 215:215 */     this.totalDiscountAWM = totalDiscountAWM;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<GuiaAerea> getListaCcrDcr()
/* 219:    */   {
/* 220:219 */     return this.listaCcrDcr == null ? (this.listaCcrDcr = new ArrayList()) : this.listaCcrDcr;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaCcrDcr(List<GuiaAerea> listaCcrDcr)
/* 224:    */   {
/* 225:223 */     this.listaCcrDcr = listaCcrDcr;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public BigDecimal getTotalRateOfExchangeCcrDcrCcoDco()
/* 229:    */   {
/* 230:227 */     return this.totalRateOfExchangeCcrDcrCcoDco;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setTotalRateOfExchangeCcrDcrCcoDco(BigDecimal totalRateOfExchangeCcrDcrCcoDco)
/* 234:    */   {
/* 235:231 */     this.totalRateOfExchangeCcrDcrCcoDco = totalRateOfExchangeCcrDcrCcoDco;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public BigDecimal getTotalWeightChargeCcrDcrCcoDco()
/* 239:    */   {
/* 240:235 */     return this.totalWeightChargeCcrDcrCcoDco;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setTotalWeightChargeCcrDcrCcoDco(BigDecimal totalWeightChargeCcrDcrCcoDco)
/* 244:    */   {
/* 245:239 */     this.totalWeightChargeCcrDcrCcoDco = totalWeightChargeCcrDcrCcoDco;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public BigDecimal getTotalChargesDueAgentCcrDcrCcoDco()
/* 249:    */   {
/* 250:243 */     return this.totalChargesDueAgentCcrDcrCcoDco;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setTotalChargesDueAgentCcrDcrCcoDco(BigDecimal totalChargesDueAgentCcrDcrCcoDco)
/* 254:    */   {
/* 255:247 */     this.totalChargesDueAgentCcrDcrCcoDco = totalChargesDueAgentCcrDcrCcoDco;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public BigDecimal getTotalChargesDueCarrierCcrDcrCcoDco()
/* 259:    */   {
/* 260:251 */     return this.totalChargesDueCarrierCcrDcrCcoDco;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setTotalChargesDueCarrierCcrDcrCcoDco(BigDecimal totalChargesDueCarrierCcrDcrCcoDco)
/* 264:    */   {
/* 265:255 */     this.totalChargesDueCarrierCcrDcrCcoDco = totalChargesDueCarrierCcrDcrCcoDco;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public BigDecimal getTotalCommissionCcrDcrCcoDco()
/* 269:    */   {
/* 270:259 */     return this.totalCommissionCcrDcrCcoDco;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setTotalCommissionCcrDcrCcoDco(BigDecimal totalCommissionCcrDcrCcoDco)
/* 274:    */   {
/* 275:263 */     this.totalCommissionCcrDcrCcoDco = totalCommissionCcrDcrCcoDco;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public BigDecimal getTotalDiscountCcrDcrCcoDco()
/* 279:    */   {
/* 280:267 */     return this.totalDiscountCcrDcrCcoDco;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setTotalDiscountCcrDcrCcoDco(BigDecimal totalDiscountCcrDcrCcoDco)
/* 284:    */   {
/* 285:271 */     this.totalDiscountCcrDcrCcoDco = totalDiscountCcrDcrCcoDco;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Subempresa getSubempresa()
/* 289:    */   {
/* 290:275 */     return this.subempresa;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setSubempresa(Subempresa subempresa)
/* 294:    */   {
/* 295:279 */     this.subempresa = subempresa;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public StreamedContent getArchivo()
/* 299:    */   {
/* 300:283 */     return this.archivo;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setArchivo(StreamedContent archivo)
/* 304:    */   {
/* 305:287 */     this.archivo = archivo;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public Date getFechasDesde()
/* 309:    */   {
/* 310:291 */     return this.fechasDesde;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setFechasDesde(Date fechasDesde)
/* 314:    */   {
/* 315:295 */     this.fechasDesde = fechasDesde;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Date getFechasHasta()
/* 319:    */   {
/* 320:299 */     return this.fechasHasta;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setFechasHasta(Date fechasHasta)
/* 324:    */   {
/* 325:303 */     this.fechasHasta = fechasHasta;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String getNumeroGuia()
/* 329:    */   {
/* 330:307 */     return this.numeroGuia;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setNumeroGuia(String numeroGuia)
/* 334:    */   {
/* 335:311 */     this.numeroGuia = numeroGuia;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public List<SelectItem> getListaTipoReporte()
/* 339:    */   {
/* 340:315 */     if (this.listaTipoReporte == null)
/* 341:    */     {
/* 342:316 */       this.listaTipoReporte = new ArrayList();
/* 343:317 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 344:318 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 345:    */       }
/* 346:    */     }
/* 347:321 */     return this.listaTipoReporte;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 351:    */   {
/* 352:325 */     this.listaTipoReporte = listaTipoReporte;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public enumTipoReporte getTipoReporte()
/* 356:    */   {
/* 357:329 */     return this.tipoReporte;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 361:    */   {
/* 362:333 */     this.tipoReporte = tipoReporte;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public DataTable getDtGuiaAreas()
/* 366:    */   {
/* 367:337 */     return this.dtGuiaAreas;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setDtGuiaAreas(DataTable dtGuiaAreas)
/* 371:    */   {
/* 372:341 */     this.dtGuiaAreas = dtGuiaAreas;
/* 373:    */   }
/* 374:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.ConsultaCassBean
 * JD-Core Version:    0.7.0.1
 */