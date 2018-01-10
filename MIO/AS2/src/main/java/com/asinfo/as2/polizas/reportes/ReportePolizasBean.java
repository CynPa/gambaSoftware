/*   1:    */ package com.asinfo.as2.polizas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Sucursal;
/*   6:    */ import com.asinfo.as2.entities.polizas.Afianzado;
/*   7:    */ import com.asinfo.as2.entities.polizas.Asegurado;
/*   8:    */ import com.asinfo.as2.entities.polizas.Aseguradora;
/*   9:    */ import com.asinfo.as2.entities.polizas.Broker;
/*  10:    */ import com.asinfo.as2.entities.polizas.ContratoPoliza;
/*  11:    */ import com.asinfo.as2.entities.polizas.TipoPoliza;
/*  12:    */ import com.asinfo.as2.enumeraciones.polizas.EstadoPoliza;
/*  13:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAfianzado;
/*  14:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAsegurado;
/*  15:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAseguradora;
/*  16:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioBroker;
/*  17:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioContratoPoliza;
/*  18:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioTipoPoliza;
/*  19:    */ import com.asinfo.as2.polizas.reportes.servicio.ServicioReportePoliza;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.RequestScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  33:    */ import net.sf.jasperreports.engine.JRException;
/*  34:    */ import org.apache.log4j.Logger;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @RequestScoped
/*  38:    */ public class ReportePolizasBean
/*  39:    */   extends AbstractBaseReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1L;
/*  42:    */   @EJB
/*  43:    */   private ServicioReportePoliza servicioReportePoliza;
/*  44:    */   @EJB
/*  45:    */   private ServicioSucursal servicioSucursal;
/*  46:    */   @EJB
/*  47:    */   private ServicioAsegurado servicioAsegurado;
/*  48:    */   @EJB
/*  49:    */   private ServicioAfianzado servicioAfianzado;
/*  50:    */   @EJB
/*  51:    */   private ServicioAseguradora servicioAseguradora;
/*  52:    */   @EJB
/*  53:    */   private ServicioBroker servicioBroker;
/*  54:    */   @EJB
/*  55:    */   private ServicioTipoPoliza servicioTipoPoliza;
/*  56:    */   @EJB
/*  57:    */   private ServicioContratoPoliza servicioContratoPoliza;
/*  58:    */   private int idSucursal;
/*  59:    */   private int idBroker;
/*  60:    */   private int idTipoPoliza;
/*  61:    */   private int idAsegurado;
/*  62:    */   private int idAseguradora;
/*  63:    */   private int idAfianzado;
/*  64:    */   private EstadoPoliza estado;
/*  65:    */   private boolean indicadorVigente;
/*  66:    */   private int idContrato;
/*  67:    */   private Date fechaDesde;
/*  68:    */   private Date fechaHasta;
/*  69:    */   private List<Sucursal> listaSucursal;
/*  70:    */   private List<Asegurado> listaAsegurado;
/*  71:    */   private List<Afianzado> listaAfianzado;
/*  72:    */   private List<Aseguradora> listaAseguradora;
/*  73:    */   private List<Broker> listaBroker;
/*  74:    */   private List<TipoPoliza> listaTipoPoliza;
/*  75:    */   private List<SelectItem> listaEstado;
/*  76:    */   private List<ContratoPoliza> listaContratoPoliza;
/*  77:    */   
/*  78:    */   protected JRDataSource getJRDataSource()
/*  79:    */   {
/*  80:121 */     List listaDatosReporte = new ArrayList();
/*  81:122 */     JRDataSource ds = null;
/*  82:123 */     listaDatosReporte = this.servicioReportePoliza.getReportePolizas(this.idTipoPoliza, this.idAsegurado, this.idAseguradora, this.idAfianzado, this.idBroker, this.idContrato, this.fechaDesde, this.fechaHasta, this.estado, this.indicadorVigente);
/*  83:    */     
/*  84:    */ 
/*  85:126 */     String[] fields = { "f_numero", "f_tipoPoliza", "f_renegociacion", "f_numeroAnexo", "f_fecha", "f_fechaSolicitud", "f_fechaVencimiento", "f_plazo", "f_aseguradora", "f_asegurado", "f_nombresBroker", "f_apellidosBroker", "f_nombresAfianzado", "f_apellidosAfianzado", "f_estado", "f_valorAsegurado", "f_valorPrima", "f_valorOtro", "f_valorPoliza", "f_establecimientoFactura", "f_puntoFactura", "f_numeroFactura", "f_fechaFactura", "f_valorFactura" };
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:131 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  91:    */     
/*  92:133 */     return ds;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected String getCompileFileName()
/*  96:    */   {
/*  97:145 */     return "reportePolizas";
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected Map<String, Object> getReportParameters()
/* 101:    */   {
/* 102:158 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 103:    */     
/* 104:160 */     reportParameters.put("ReportTitle", "Reporte Polizas");
/* 105:161 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 106:162 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 107:163 */     reportParameters.put("Total", "Total");
/* 108:164 */     return reportParameters;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String execute()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:174 */       validaDatos();
/* 116:175 */       super.prepareReport();
/* 117:    */     }
/* 118:    */     catch (JRException e)
/* 119:    */     {
/* 120:177 */       LOG.info("Error JRException");
/* 121:178 */       e.printStackTrace();
/* 122:179 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 123:    */     }
/* 124:    */     catch (IOException e)
/* 125:    */     {
/* 126:181 */       LOG.info("Error IOException");
/* 127:182 */       e.printStackTrace();
/* 128:183 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 129:    */     }
/* 130:186 */     return null;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void validaDatos()
/* 134:    */   {
/* 135:190 */     if (this.fechaDesde == null) {
/* 136:191 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 137:    */     }
/* 138:193 */     if (this.fechaHasta == null) {
/* 139:194 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getIdSucursal()
/* 144:    */   {
/* 145:214 */     return this.idSucursal;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setIdSucursal(int idSucursal)
/* 149:    */   {
/* 150:224 */     this.idSucursal = idSucursal;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public int getIdBroker()
/* 154:    */   {
/* 155:233 */     return this.idBroker;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setIdBroker(int idBroker)
/* 159:    */   {
/* 160:243 */     this.idBroker = idBroker;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int getIdTipoPoliza()
/* 164:    */   {
/* 165:252 */     return this.idTipoPoliza;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setIdTipoPoliza(int idTipoPoliza)
/* 169:    */   {
/* 170:262 */     this.idTipoPoliza = idTipoPoliza;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getIdAsegurado()
/* 174:    */   {
/* 175:271 */     return this.idAsegurado;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setIdAsegurado(int idAsegurado)
/* 179:    */   {
/* 180:281 */     this.idAsegurado = idAsegurado;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public int getIdAseguradora()
/* 184:    */   {
/* 185:290 */     return this.idAseguradora;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setIdAseguradora(int idAseguradora)
/* 189:    */   {
/* 190:300 */     this.idAseguradora = idAseguradora;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public int getIdAfianzado()
/* 194:    */   {
/* 195:309 */     return this.idAfianzado;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setIdAfianzado(int idAfianzado)
/* 199:    */   {
/* 200:319 */     this.idAfianzado = idAfianzado;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public EstadoPoliza getEstado()
/* 204:    */   {
/* 205:328 */     return this.estado;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setEstado(EstadoPoliza estado)
/* 209:    */   {
/* 210:338 */     this.estado = estado;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public int getIdContrato()
/* 214:    */   {
/* 215:347 */     return this.idContrato;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setIdContrato(int idContrato)
/* 219:    */   {
/* 220:357 */     this.idContrato = idContrato;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Date getFechaDesde()
/* 224:    */   {
/* 225:361 */     return this.fechaDesde;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setFechaDesde(Date fechaDesde)
/* 229:    */   {
/* 230:371 */     this.fechaDesde = fechaDesde;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Date getFechaHasta()
/* 234:    */   {
/* 235:380 */     return this.fechaHasta;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setFechaHasta(Date fechaHasta)
/* 239:    */   {
/* 240:390 */     this.fechaHasta = fechaHasta;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean isIndicadorVigente()
/* 244:    */   {
/* 245:399 */     return this.indicadorVigente;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setIndicadorVigente(boolean indicadorVigente)
/* 249:    */   {
/* 250:409 */     this.indicadorVigente = indicadorVigente;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<Sucursal> getListaSucursal()
/* 254:    */   {
/* 255:418 */     if (this.listaSucursal == null) {
/* 256:419 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 257:    */     }
/* 258:421 */     return this.listaSucursal;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 262:    */   {
/* 263:431 */     this.listaSucursal = listaSucursal;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<Asegurado> getListaAsegurado()
/* 267:    */   {
/* 268:440 */     if (this.listaAsegurado == null) {
/* 269:441 */       this.listaAsegurado = this.servicioAsegurado.obtenerListaCombo("nombre", true, null);
/* 270:    */     }
/* 271:443 */     return this.listaAsegurado;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setListaAsegurado(List<Asegurado> listaAsegurado)
/* 275:    */   {
/* 276:453 */     this.listaAsegurado = listaAsegurado;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<Afianzado> getListaAfianzado()
/* 280:    */   {
/* 281:462 */     if (this.listaAfianzado == null) {
/* 282:463 */       this.listaAfianzado = this.servicioAfianzado.obtenerListaCombo("nombres", true, null);
/* 283:    */     }
/* 284:466 */     return this.listaAfianzado;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setListaAfianzado(List<Afianzado> listaAfianzado)
/* 288:    */   {
/* 289:476 */     this.listaAfianzado = listaAfianzado;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public List<Aseguradora> getListaAseguradora()
/* 293:    */   {
/* 294:485 */     if (this.listaAseguradora == null) {
/* 295:486 */       this.listaAseguradora = this.servicioAseguradora.obtenerListaCombo("nombre", true, null);
/* 296:    */     }
/* 297:488 */     return this.listaAseguradora;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setListaAseguradora(List<Aseguradora> listaAseguradora)
/* 301:    */   {
/* 302:498 */     this.listaAseguradora = listaAseguradora;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public List<Broker> getListaBroker()
/* 306:    */   {
/* 307:507 */     if (this.listaBroker == null) {
/* 308:508 */       this.listaBroker = this.servicioBroker.obtenerListaCombo("nombres", true, null);
/* 309:    */     }
/* 310:510 */     return this.listaBroker;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setListaBroker(List<Broker> listaBroker)
/* 314:    */   {
/* 315:520 */     this.listaBroker = listaBroker;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public List<TipoPoliza> getListaTipoPoliza()
/* 319:    */   {
/* 320:529 */     if (this.listaTipoPoliza == null) {
/* 321:530 */       this.listaTipoPoliza = this.servicioTipoPoliza.obtenerListaCombo("nombre", true, null);
/* 322:    */     }
/* 323:532 */     return this.listaTipoPoliza;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setListaTipoPoliza(List<TipoPoliza> listaTipoPoliza)
/* 327:    */   {
/* 328:542 */     this.listaTipoPoliza = listaTipoPoliza;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public List<SelectItem> getListaEstado()
/* 332:    */   {
/* 333:551 */     if (this.listaEstado == null)
/* 334:    */     {
/* 335:552 */       this.listaEstado = new ArrayList();
/* 336:553 */       for (EstadoPoliza estadoPoliza : EstadoPoliza.values()) {
/* 337:554 */         this.listaEstado.add(new SelectItem(estadoPoliza, estadoPoliza.getNombre()));
/* 338:    */       }
/* 339:    */     }
/* 340:557 */     return this.listaEstado;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setListaEstado(List<SelectItem> listaEstado)
/* 344:    */   {
/* 345:567 */     this.listaEstado = listaEstado;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public List<ContratoPoliza> getListaContratoPoliza()
/* 349:    */   {
/* 350:576 */     if (this.listaContratoPoliza == null) {
/* 351:577 */       this.listaContratoPoliza = this.servicioContratoPoliza.obtenerListaCombo("nombre", true, null);
/* 352:    */     }
/* 353:579 */     return this.listaContratoPoliza;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setListaContratoPoliza(List<ContratoPoliza> listaContratoPoliza)
/* 357:    */   {
/* 358:589 */     this.listaContratoPoliza = listaContratoPoliza;
/* 359:    */   }
/* 360:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.reportes.ReportePolizasBean
 * JD-Core Version:    0.7.0.1
 */