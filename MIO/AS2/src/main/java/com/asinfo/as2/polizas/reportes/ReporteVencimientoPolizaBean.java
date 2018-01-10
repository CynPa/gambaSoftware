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
/*  38:    */ public class ReporteVencimientoPolizaBean
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
/*  64:    */   private int idContrato;
/*  65:    */   private EstadoPoliza estado;
/*  66:    */   private Date fechaHasta;
/*  67:    */   private List<Sucursal> listaSucursal;
/*  68:    */   private List<Asegurado> listaAsegurado;
/*  69:    */   private List<Afianzado> listaAfianzado;
/*  70:    */   private List<Aseguradora> listaAseguradora;
/*  71:    */   private List<Broker> listaBroker;
/*  72:    */   private List<TipoPoliza> listaTipoPoliza;
/*  73:    */   private List<SelectItem> listaEstado;
/*  74:    */   private List<ContratoPoliza> listaContratoPoliza;
/*  75:    */   
/*  76:    */   protected JRDataSource getJRDataSource()
/*  77:    */   {
/*  78:119 */     List listaDatosReporte = new ArrayList();
/*  79:120 */     JRDataSource ds = null;
/*  80:121 */     listaDatosReporte = this.servicioReportePoliza.getReporteVencimientoPoliza(this.idTipoPoliza, this.idAsegurado, this.idAseguradora, this.idAfianzado, this.idBroker, this.idContrato, this.fechaHasta, this.estado);
/*  81:    */     
/*  82:    */ 
/*  83:124 */     String[] fields = { "f_numero", "f_tipoPoliza", "f_renegociacion", "f_numeroAnexo", "f_fecha", "f_fechaSolicitud", "f_fechaVencimiento", "f_plazo", "f_aseguradora", "f_asegurado", "f_nombresBroker", "f_apellidosBroker", "f_nombresAfianzado", "f_apellidosAfianzado", "f_estado", "f_valorAsegurado", "f_valorPrima", "f_valorOtro", "f_valorPoliza", "f_establecimientoFactura", "f_puntoFactura", "f_numeroFactura", "f_fechaFactura", "f_valorFactura" };
/*  84:    */     
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:129 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  89:    */     
/*  90:131 */     return ds;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected String getCompileFileName()
/*  94:    */   {
/*  95:142 */     return "reporteVencimientoPoliza";
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected Map<String, Object> getReportParameters()
/*  99:    */   {
/* 100:154 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 101:    */     
/* 102:156 */     reportParameters.put("ReportTitle", "Reporte Vencimiento de Polizas");
/* 103:157 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 104:158 */     reportParameters.put("Total", "Total");
/* 105:159 */     return reportParameters;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String execute()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:169 */       validaDatos();
/* 113:170 */       super.prepareReport();
/* 114:    */     }
/* 115:    */     catch (JRException e)
/* 116:    */     {
/* 117:172 */       LOG.info("Error JRException");
/* 118:173 */       e.printStackTrace();
/* 119:174 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:    */     catch (IOException e)
/* 122:    */     {
/* 123:176 */       LOG.info("Error IOException");
/* 124:177 */       e.printStackTrace();
/* 125:178 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 126:    */     }
/* 127:181 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void validaDatos()
/* 131:    */   {
/* 132:185 */     if (this.fechaHasta == null) {
/* 133:186 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int getIdSucursal()
/* 138:    */   {
/* 139:206 */     return this.idSucursal;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setIdSucursal(int idSucursal)
/* 143:    */   {
/* 144:216 */     this.idSucursal = idSucursal;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int getIdBroker()
/* 148:    */   {
/* 149:225 */     return this.idBroker;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIdBroker(int idBroker)
/* 153:    */   {
/* 154:235 */     this.idBroker = idBroker;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int getIdTipoPoliza()
/* 158:    */   {
/* 159:244 */     return this.idTipoPoliza;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIdTipoPoliza(int idTipoPoliza)
/* 163:    */   {
/* 164:254 */     this.idTipoPoliza = idTipoPoliza;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getIdAsegurado()
/* 168:    */   {
/* 169:263 */     return this.idAsegurado;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setIdAsegurado(int idAsegurado)
/* 173:    */   {
/* 174:273 */     this.idAsegurado = idAsegurado;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getIdAseguradora()
/* 178:    */   {
/* 179:282 */     return this.idAseguradora;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIdAseguradora(int idAseguradora)
/* 183:    */   {
/* 184:292 */     this.idAseguradora = idAseguradora;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getIdAfianzado()
/* 188:    */   {
/* 189:301 */     return this.idAfianzado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setIdAfianzado(int idAfianzado)
/* 193:    */   {
/* 194:311 */     this.idAfianzado = idAfianzado;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public EstadoPoliza getEstado()
/* 198:    */   {
/* 199:320 */     return this.estado;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setEstado(EstadoPoliza estado)
/* 203:    */   {
/* 204:330 */     this.estado = estado;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int getIdContrato()
/* 208:    */   {
/* 209:339 */     return this.idContrato;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setIdContrato(int idContrato)
/* 213:    */   {
/* 214:349 */     this.idContrato = idContrato;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Date getFechaHasta()
/* 218:    */   {
/* 219:358 */     return this.fechaHasta;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setFechaHasta(Date fechaHasta)
/* 223:    */   {
/* 224:368 */     this.fechaHasta = fechaHasta;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public List<Sucursal> getListaSucursal()
/* 228:    */   {
/* 229:377 */     if (this.listaSucursal == null) {
/* 230:378 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 231:    */     }
/* 232:380 */     return this.listaSucursal;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 236:    */   {
/* 237:390 */     this.listaSucursal = listaSucursal;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<Asegurado> getListaAsegurado()
/* 241:    */   {
/* 242:399 */     if (this.listaAsegurado == null) {
/* 243:400 */       this.listaAsegurado = this.servicioAsegurado.obtenerListaCombo("nombre", true, null);
/* 244:    */     }
/* 245:402 */     return this.listaAsegurado;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setListaAsegurado(List<Asegurado> listaAsegurado)
/* 249:    */   {
/* 250:412 */     this.listaAsegurado = listaAsegurado;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<Afianzado> getListaAfianzado()
/* 254:    */   {
/* 255:421 */     if (this.listaAfianzado == null) {
/* 256:422 */       this.listaAfianzado = this.servicioAfianzado.obtenerListaCombo("nombres", true, null);
/* 257:    */     }
/* 258:425 */     return this.listaAfianzado;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setListaAfianzado(List<Afianzado> listaAfianzado)
/* 262:    */   {
/* 263:435 */     this.listaAfianzado = listaAfianzado;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<Aseguradora> getListaAseguradora()
/* 267:    */   {
/* 268:444 */     if (this.listaAseguradora == null) {
/* 269:445 */       this.listaAseguradora = this.servicioAseguradora.obtenerListaCombo("nombre", true, null);
/* 270:    */     }
/* 271:447 */     return this.listaAseguradora;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setListaAseguradora(List<Aseguradora> listaAseguradora)
/* 275:    */   {
/* 276:457 */     this.listaAseguradora = listaAseguradora;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<Broker> getListaBroker()
/* 280:    */   {
/* 281:466 */     if (this.listaBroker == null) {
/* 282:467 */       this.listaBroker = this.servicioBroker.obtenerListaCombo("nombres", true, null);
/* 283:    */     }
/* 284:469 */     return this.listaBroker;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setListaBroker(List<Broker> listaBroker)
/* 288:    */   {
/* 289:479 */     this.listaBroker = listaBroker;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public List<TipoPoliza> getListaTipoPoliza()
/* 293:    */   {
/* 294:488 */     if (this.listaTipoPoliza == null) {
/* 295:489 */       this.listaTipoPoliza = this.servicioTipoPoliza.obtenerListaCombo("nombre", true, null);
/* 296:    */     }
/* 297:491 */     return this.listaTipoPoliza;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setListaTipoPoliza(List<TipoPoliza> listaTipoPoliza)
/* 301:    */   {
/* 302:501 */     this.listaTipoPoliza = listaTipoPoliza;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public List<SelectItem> getListaEstado()
/* 306:    */   {
/* 307:510 */     if (this.listaEstado == null)
/* 308:    */     {
/* 309:511 */       this.listaEstado = new ArrayList();
/* 310:512 */       for (EstadoPoliza estadoPoliza : EstadoPoliza.values()) {
/* 311:513 */         this.listaEstado.add(new SelectItem(estadoPoliza, estadoPoliza.getNombre()));
/* 312:    */       }
/* 313:    */     }
/* 314:516 */     return this.listaEstado;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setListaEstado(List<SelectItem> listaEstado)
/* 318:    */   {
/* 319:526 */     this.listaEstado = listaEstado;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public List<ContratoPoliza> getListaContratoPoliza()
/* 323:    */   {
/* 324:535 */     if (this.listaContratoPoliza == null) {
/* 325:536 */       this.listaContratoPoliza = this.servicioContratoPoliza.obtenerListaCombo("nombre", true, null);
/* 326:    */     }
/* 327:538 */     return this.listaContratoPoliza;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setListaContratoPoliza(List<ContratoPoliza> listaContratoPoliza)
/* 331:    */   {
/* 332:548 */     this.listaContratoPoliza = listaContratoPoliza;
/* 333:    */   }
/* 334:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.reportes.ReporteVencimientoPolizaBean
 * JD-Core Version:    0.7.0.1
 */