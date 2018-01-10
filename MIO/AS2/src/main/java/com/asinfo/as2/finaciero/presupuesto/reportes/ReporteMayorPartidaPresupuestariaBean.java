/*   1:    */ package com.asinfo.as2.finaciero.presupuesto.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.CentroCosto;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  11:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  12:    */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioPartidaPresupuestaria;
/*  13:    */ import com.asinfo.as2.financiero.presupuesto.reportes.servicio.ServicioReportePresupuesto;
/*  14:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Calendar;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  31:    */ import net.sf.jasperreports.engine.JRException;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ReporteMayorPartidaPresupuestariaBean
/*  37:    */   extends AbstractBaseReportBean
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 1L;
/*  40:    */   @EJB
/*  41:    */   private ServicioReportePresupuesto servicioReportePresupuesto;
/*  42:    */   @EJB
/*  43:    */   private ServicioSucursal servicioSucursal;
/*  44:    */   @EJB
/*  45:    */   private ServicioPartidaPresupuestaria servicioPartidaPresupuestaria;
/*  46:    */   private int idPartidaPresupuestaria;
/*  47:    */   private int idSucursal;
/*  48:    */   private boolean indicadorNIIF;
/*  49:    */   private Date fechaDesde;
/*  50:    */   private Date fechaHasta;
/*  51:    */   private PartidaPresupuestaria partidaPresupuestaria;
/*  52:    */   private CentroCosto centroCosto;
/*  53:    */   private List<Sucursal> listaSucursal;
/*  54:    */   private List<DetalleAsiento> listaDetalleAsiento;
/*  55:    */   private BigDecimal debe;
/*  56:    */   private BigDecimal haber;
/*  57: 82 */   private BigDecimal saldoCentroCostoDebe = BigDecimal.ZERO;
/*  58: 83 */   private BigDecimal saldoCentroCostoHaber = BigDecimal.ZERO;
/*  59: 84 */   private BigDecimal saldoInicial = BigDecimal.ZERO;
/*  60: 85 */   private BigDecimal saldoFinal = BigDecimal.ZERO;
/*  61:    */   
/*  62:    */   protected JRDataSource getJRDataSource()
/*  63:    */   {
/*  64: 96 */     List listaDatosReporte = new ArrayList();
/*  65: 97 */     JRDataSource ds = null;
/*  66:    */     
/*  67: 99 */     listaDatosReporte = this.servicioReportePresupuesto.getReporteMayorPartidaPresupuestaria(getPartidaPresupuestaria(), this.fechaDesde, this.fechaHasta, this.idSucursal, this.indicadorNIIF, 
/*  68:100 */       getCentroCosto());
/*  69:    */     
/*  70:102 */     String[] fields = { "f_fecha", "f_codigoPartidaPresupuestaria", "f_nombrePartidaPresupuestaria", "f_numeroAsiento", "f_tipoAsiento", "f_nombreCentroCosto", "f_codigoCentroCosto", "f_debe", "f_haber" };
/*  71:    */     
/*  72:    */ 
/*  73:105 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  74:    */     
/*  75:107 */     return ds;
/*  76:    */   }
/*  77:    */   
/*  78:    */   @PostConstruct
/*  79:    */   public void init()
/*  80:    */   {
/*  81:112 */     Calendar calfechaDesde = Calendar.getInstance();
/*  82:113 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  83:114 */     this.fechaDesde = calfechaDesde.getTime();
/*  84:115 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected String getCompileFileName()
/*  88:    */   {
/*  89:125 */     return "reporteMayorPartidaPresupuestaria";
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected Map<String, Object> getReportParameters()
/*  93:    */   {
/*  94:135 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  95:    */     
/*  96:137 */     reportParameters.put("ReportTitle", "Mayor por Partida Presupuestaria");
/*  97:138 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  98:139 */     BigDecimal saldoInicial = this.servicioReportePresupuesto.obtenerSaldo(this.fechaDesde, this.fechaHasta, ValoresCalculo.DEBE_HABER, TipoCalculo.SALDO_INICIAL, this.indicadorNIIF, this.idSucursal, 
/*  99:140 */       getPartidaPresupuestaria(), getCentroCosto());
/* 100:141 */     reportParameters.put("saldoInicial", saldoInicial);
/* 101:142 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 102:143 */     reportParameters.put("Total", "Total");
/* 103:144 */     return reportParameters;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String execute()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:154 */       validaDatos();
/* 111:155 */       super.prepareReport();
/* 112:    */     }
/* 113:    */     catch (JRException e)
/* 114:    */     {
/* 115:157 */       LOG.info("Error JRException");
/* 116:158 */       e.printStackTrace();
/* 117:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 118:    */     }
/* 119:    */     catch (IOException e)
/* 120:    */     {
/* 121:161 */       LOG.info("Error IOException");
/* 122:162 */       e.printStackTrace();
/* 123:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 124:    */     }
/* 125:166 */     return null;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void validaDatos()
/* 129:    */   {
/* 130:170 */     if (this.fechaDesde == null) {
/* 131:171 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 132:    */     }
/* 133:173 */     if (this.fechaHasta == null) {
/* 134:174 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void cargarCentroCosto() {}
/* 139:    */   
/* 140:    */   public void cargarPartidaPresupuestaria() {}
/* 141:    */   
/* 142:    */   public void procesar()
/* 143:    */   {
/* 144:194 */     this.listaDetalleAsiento = new ArrayList();
/* 145:196 */     if (this.partidaPresupuestaria != null)
/* 146:    */     {
/* 147:198 */       validaDatos();
/* 148:    */       
/* 149:200 */       this.saldoInicial = this.servicioReportePresupuesto.obtenerSaldo(this.fechaDesde, this.fechaHasta, ValoresCalculo.DEBE_HABER, TipoCalculo.SALDO_INICIAL, this.indicadorNIIF, this.idSucursal, this.partidaPresupuestaria, this.centroCosto);
/* 150:    */       
/* 151:    */ 
/* 152:203 */       this.listaDetalleAsiento = this.servicioReportePresupuesto.getMayorPartidaPresupuestaria(getPartidaPresupuestaria(), this.fechaDesde, this.fechaHasta, this.idSucursal, this.indicadorNIIF, 
/* 153:204 */         getCentroCosto());
/* 154:    */       
/* 155:206 */       String etiquetaSaldoInicial = getLanguageController().getMensaje("msg_saldo_inicial");
/* 156:    */       
/* 157:208 */       DetalleAsiento detalleAsiento = new DetalleAsiento(getSaldoCentroCostoDebe(), getSaldoCentroCostoHaber(), etiquetaSaldoInicial, etiquetaSaldoInicial, "", this.fechaDesde, "", "", "", "", "");
/* 158:    */       
/* 159:    */ 
/* 160:211 */       this.listaDetalleAsiento.add(0, detalleAsiento);
/* 161:    */       
/* 162:213 */       calcular();
/* 163:    */     }
/* 164:    */     else
/* 165:    */     {
/* 166:216 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   private void calcular()
/* 171:    */   {
/* 172:221 */     this.debe = BigDecimal.ZERO;
/* 173:222 */     this.haber = BigDecimal.ZERO;
/* 174:223 */     this.saldoFinal = BigDecimal.ZERO;
/* 175:224 */     for (DetalleAsiento da : this.listaDetalleAsiento)
/* 176:    */     {
/* 177:225 */       this.haber = this.haber.add(da.getHaber());
/* 178:226 */       this.debe = this.debe.add(da.getDebe());
/* 179:    */     }
/* 180:228 */     this.saldoFinal = this.saldoFinal.add(this.debe);
/* 181:229 */     this.saldoFinal = this.saldoFinal.subtract(this.haber);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<PartidaPresupuestaria> getListaPartidaPresupuestaria()
/* 185:    */   {
/* 186:240 */     List<PartidaPresupuestaria> lista = this.servicioPartidaPresupuestaria.obtenerPartidaPresupuestariaPorUsuario(AppUtil.getOrganizacion().getId(), 
/* 187:241 */       AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 188:242 */     return lista;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public PartidaPresupuestaria getPartidaPresupuestaria()
/* 192:    */   {
/* 193:255 */     return this.partidaPresupuestaria;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria)
/* 197:    */   {
/* 198:265 */     this.partidaPresupuestaria = partidaPresupuestaria;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public CentroCosto getCentroCosto()
/* 202:    */   {
/* 203:274 */     return this.centroCosto;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setCentroCosto(CentroCosto centroCosto)
/* 207:    */   {
/* 208:284 */     this.centroCosto = centroCosto;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public BigDecimal getSaldoCentroCostoDebe()
/* 212:    */   {
/* 213:293 */     if (this.saldoInicial.compareTo(BigDecimal.ZERO) >= 0) {
/* 214:294 */       this.saldoCentroCostoDebe = this.saldoInicial;
/* 215:    */     }
/* 216:296 */     return this.saldoCentroCostoDebe;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setSaldoCentroCostoDebe(BigDecimal saldoCentroCostoDebe)
/* 220:    */   {
/* 221:306 */     this.saldoCentroCostoDebe = saldoCentroCostoDebe;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public BigDecimal getSaldoCentroCostoHaber()
/* 225:    */   {
/* 226:315 */     if (this.saldoInicial.compareTo(BigDecimal.ZERO) <= 0) {
/* 227:316 */       this.saldoCentroCostoHaber = this.saldoInicial.negate();
/* 228:    */     }
/* 229:318 */     return this.saldoCentroCostoHaber;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setSaldoCentroCostoHaber(BigDecimal saldoCentroCostoHaber)
/* 233:    */   {
/* 234:328 */     this.saldoCentroCostoHaber = saldoCentroCostoHaber;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public BigDecimal getSaldoInicial()
/* 238:    */   {
/* 239:337 */     return this.saldoInicial;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setSaldoInicial(BigDecimal saldoInicial)
/* 243:    */   {
/* 244:347 */     this.saldoInicial = saldoInicial;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public int getIdPartidaPresupuestaria()
/* 248:    */   {
/* 249:356 */     return this.idPartidaPresupuestaria;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setIdPartidaPresupuestaria(int idPartidaPresupuestaria)
/* 253:    */   {
/* 254:366 */     this.idPartidaPresupuestaria = idPartidaPresupuestaria;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public int getIdSucursal()
/* 258:    */   {
/* 259:375 */     return this.idSucursal;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setIdSucursal(int idSucursal)
/* 263:    */   {
/* 264:385 */     this.idSucursal = idSucursal;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isIndicadorNIIF()
/* 268:    */   {
/* 269:394 */     return this.indicadorNIIF;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setIndicadorNIIF(boolean indicadorNIIF)
/* 273:    */   {
/* 274:404 */     this.indicadorNIIF = indicadorNIIF;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Date getFechaDesde()
/* 278:    */   {
/* 279:413 */     return this.fechaDesde;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setFechaDesde(Date fechaDesde)
/* 283:    */   {
/* 284:423 */     this.fechaDesde = fechaDesde;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Date getFechaHasta()
/* 288:    */   {
/* 289:432 */     return this.fechaHasta;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setFechaHasta(Date fechaHasta)
/* 293:    */   {
/* 294:442 */     this.fechaHasta = fechaHasta;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<Sucursal> getListaSucursal()
/* 298:    */   {
/* 299:451 */     if (this.listaSucursal == null) {
/* 300:452 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 301:    */     }
/* 302:454 */     return this.listaSucursal;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 306:    */   {
/* 307:464 */     this.listaSucursal = listaSucursal;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public BigDecimal getDebe()
/* 311:    */   {
/* 312:473 */     return this.debe;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setDebe(BigDecimal debe)
/* 316:    */   {
/* 317:483 */     this.debe = debe;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public BigDecimal getHaber()
/* 321:    */   {
/* 322:492 */     return this.haber;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setHaber(BigDecimal haber)
/* 326:    */   {
/* 327:502 */     this.haber = haber;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public BigDecimal getSaldoFinal()
/* 331:    */   {
/* 332:511 */     return this.saldoFinal;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setSaldoFinal(BigDecimal saldoFinal)
/* 336:    */   {
/* 337:521 */     this.saldoFinal = saldoFinal;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 341:    */   {
/* 342:530 */     if (this.listaDetalleAsiento == null) {
/* 343:531 */       this.listaDetalleAsiento = new ArrayList();
/* 344:    */     }
/* 345:533 */     return this.listaDetalleAsiento;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setListaDetalleAsiento(List<DetalleAsiento> listaDetalleAsiento)
/* 349:    */   {
/* 350:543 */     this.listaDetalleAsiento = listaDetalleAsiento;
/* 351:    */   }
/* 352:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.reportes.ReporteMayorPartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */