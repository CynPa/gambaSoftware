/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Departamento;
/*   9:    */ import com.asinfo.as2.entities.DimensionContable;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PagoRol;
/*  12:    */ import com.asinfo.as2.entities.Quincena;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  17:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class ReporteIngresosEgresosBean
/*  38:    */   extends AbstractBaseReportBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = -916075102491017726L;
/*  41:    */   @EJB
/*  42:    */   private ServicioReporteNomina servicioReporteNomina;
/*  43:    */   @EJB
/*  44:    */   private ServicioPagoRol servicioPagoRol;
/*  45:    */   @EJB
/*  46:    */   private ServicioDepartamento servicioDepartamento;
/*  47:    */   @EJB
/*  48:    */   private ServicioSucursal servicioSucursal;
/*  49:    */   @EJB
/*  50:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  51:    */   @EJB
/*  52:    */   private ServicioCentroCosto servicioCentroCosto;
/*  53:    */   @EJB
/*  54:    */   private ServicioDimensionContable servicioProyecto;
/*  55:    */   private DimensionContable dimensionContable;
/*  56:    */   private PagoRol pagoRol;
/*  57:    */   private Departamento departamento;
/*  58:    */   private Sucursal sucursal;
/*  59:    */   private int idCategoriaEmpresa;
/*  60:    */   private FormaPagoEmpleado formaPagoEmpleado;
/*  61:    */   private List<SelectItem> listaPagoRol;
/*  62:    */   private List<SelectItem> listaFormaPagoEmpleado;
/*  63:    */   private List<Departamento> listaDepartamento;
/*  64:    */   private List<Sucursal> listaSucursal;
/*  65:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  66:    */   private int indicadorAgrupado;
/*  67:    */   private boolean indicadorMostrarProvision;
/*  68:    */   private boolean indicadorCodigos;
/*  69:    */   
/*  70:    */   private static enum enumAgrupado
/*  71:    */   {
/*  72: 93 */     Detallado,  Departamento,  CC;
/*  73:    */     
/*  74:    */     private enumAgrupado() {}
/*  75:    */   }
/*  76:    */   
/*  77: 96 */   private enumAgrupado agrupado = enumAgrupado.Detallado;
/*  78:    */   private List<SelectItem> listaAgrupado;
/*  79:    */   
/*  80:    */   protected JRDataSource getJRDataSource()
/*  81:    */   {
/*  82:107 */     List listaDatosReporte = new ArrayList();
/*  83:108 */     JRDataSource ds = null;
/*  84:109 */     listaDatosReporte = this.servicioReporteNomina.getListaIngresosEgresos(this.pagoRol, this.formaPagoEmpleado, this.departamento, this.sucursal, 
/*  85:110 */       getIdCategoriaEmpresa(), AppUtil.getOrganizacion().getId(), this.indicadorMostrarProvision, this.indicadorAgrupado, this.indicadorCodigos, this.dimensionContable);
/*  86:    */     
/*  87:    */ 
/*  88:113 */     String[] fields = { "identificacion", "apellido", "nombre", "nombreRubro", "valorRubro", "operacion", "fechaPagoRol", "indicadorProvision", "tipo", "codigoNombre", "f_ordenImpresion", "f_codigoCentroCosto", "f_nombreCentroCosto", "f_cargo", "f_diasFalta", "f_salarioAsignado", "f_baseImponibleImponibleImpuestoRenta", "f_diasTrabajados", "f_tiempo", "f_indicadorTiempo", "f_propina", "f_departamento", "f_sucursal", "f_cc", "f_genero", "f_indicadorImpresionSobre", "f_formula", "f_fechaIngresoEmpleado", "f_indicadorPagoCash", "f_fechaSalidaEmpleado", "f_baseImponibleIEES" };
/*  89:    */     
/*  90:    */ 
/*  91:    */ 
/*  92:117 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  93:118 */     return ds;
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected Map<String, Object> getReportParameters()
/*  97:    */   {
/*  98:129 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  99:130 */     reportParameters.put("ReportTitle", "Reporte Ingresos Egresos");
/* 100:131 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/* 101:132 */     reportParameters.put("p_fechaRol", FuncionesUtiles.nombreMes(this.pagoRol.getMes() - 1) + "-" + Integer.toString(this.pagoRol.getAnio()));
/* 102:133 */     reportParameters.put("FechaRol", this.pagoRol.getFecha());
/* 103:134 */     if (this.indicadorAgrupado == 1) {
/* 104:135 */       reportParameters.put("p_agrupado", "Departamento");
/* 105:136 */     } else if (this.indicadorAgrupado == 2) {
/* 106:137 */       reportParameters.put("p_agrupado", "Centro de Costo");
/* 107:    */     }
/* 108:140 */     return reportParameters;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String execute()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:150 */       super.prepareReport();
/* 116:    */     }
/* 117:    */     catch (JRException e)
/* 118:    */     {
/* 119:152 */       LOG.info("Error JRException");
/* 120:153 */       e.printStackTrace();
/* 121:154 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos") + " " + e.getMessage());
/* 122:    */     }
/* 123:    */     catch (IOException e)
/* 124:    */     {
/* 125:156 */       LOG.info("Error IOException");
/* 126:157 */       e.printStackTrace();
/* 127:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos") + " " + e.getMessage());
/* 128:    */     }
/* 129:    */     catch (Exception e)
/* 130:    */     {
/* 131:160 */       LOG.error("Error al generar el reporte de Ingresos-Egresos");
/* 132:161 */       e.printStackTrace();
/* 133:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 134:    */     }
/* 135:165 */     return null;
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected String getCompileFileName()
/* 139:    */   {
/* 140:176 */     if ((this.indicadorAgrupado == 1) || (this.indicadorAgrupado == 2)) {
/* 141:177 */       return "reporteIngresosEgresosEmpleadoCentroCosto";
/* 142:    */     }
/* 143:179 */     return "reporteIngresosEgresosEmpleado";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<SelectItem> getListaPagoRol()
/* 147:    */   {
/* 148:190 */     List<PagoRol> lista = new ArrayList();
/* 149:191 */     Map<String, String> filters = new HashMap();
/* 150:192 */     filters.put("indicadorFiniquito", "false");
/* 151:    */     
/* 152:194 */     lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 153:195 */     if (this.listaPagoRol == null)
/* 154:    */     {
/* 155:196 */       this.listaPagoRol = new ArrayList();
/* 156:197 */       for (PagoRol pagoRol : lista)
/* 157:    */       {
/* 158:203 */         String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 159:204 */         SelectItem item = new SelectItem(Integer.valueOf(pagoRol.getIdPagoRol()), label);
/* 160:205 */         this.listaPagoRol.add(item);
/* 161:    */       }
/* 162:    */     }
/* 163:208 */     return this.listaPagoRol;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void actualizarAgrupamiento()
/* 167:    */   {
/* 168:213 */     if (this.agrupado.equals(enumAgrupado.Detallado)) {
/* 169:214 */       this.indicadorAgrupado = 0;
/* 170:216 */     } else if (this.agrupado.equals(enumAgrupado.Departamento)) {
/* 171:217 */       this.indicadorAgrupado = 1;
/* 172:219 */     } else if (this.agrupado.equals(enumAgrupado.CC)) {
/* 173:220 */       this.indicadorAgrupado = 2;
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public PagoRol getPagoRol()
/* 178:    */   {
/* 179:230 */     if (this.pagoRol == null) {
/* 180:231 */       this.pagoRol = new PagoRol();
/* 181:    */     }
/* 182:234 */     return this.pagoRol;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setPagoRol(PagoRol pagoRol)
/* 186:    */   {
/* 187:244 */     this.pagoRol = pagoRol;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Departamento getDepartamento()
/* 191:    */   {
/* 192:253 */     return this.departamento;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setDepartamento(Departamento departamento)
/* 196:    */   {
/* 197:263 */     this.departamento = departamento;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public List<SelectItem> getListaFormaPagoEmpleado()
/* 201:    */   {
/* 202:272 */     if (this.listaFormaPagoEmpleado == null)
/* 203:    */     {
/* 204:273 */       this.listaFormaPagoEmpleado = new ArrayList();
/* 205:274 */       for (FormaPagoEmpleado formaPagoEmpleado : FormaPagoEmpleado.values())
/* 206:    */       {
/* 207:275 */         SelectItem item = new SelectItem(formaPagoEmpleado, formaPagoEmpleado.getNombre());
/* 208:276 */         this.listaFormaPagoEmpleado.add(item);
/* 209:    */       }
/* 210:    */     }
/* 211:279 */     return this.listaFormaPagoEmpleado;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public FormaPagoEmpleado getFormaPagoEmpleado()
/* 215:    */   {
/* 216:288 */     return this.formaPagoEmpleado;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setFormaPagoEmpleado(FormaPagoEmpleado formaPagoEmpleado)
/* 220:    */   {
/* 221:298 */     this.formaPagoEmpleado = formaPagoEmpleado;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<Departamento> getListaDepartamento()
/* 225:    */   {
/* 226:307 */     if (this.listaDepartamento == null) {
/* 227:308 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 228:    */     }
/* 229:310 */     return this.listaDepartamento;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 233:    */   {
/* 234:320 */     this.listaDepartamento = listaDepartamento;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List<Sucursal> getListaSucursal()
/* 238:    */   {
/* 239:329 */     if (this.listaSucursal == null) {
/* 240:330 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 241:    */     }
/* 242:332 */     return this.listaSucursal;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Sucursal getSucursal()
/* 246:    */   {
/* 247:341 */     return this.sucursal;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setSucursal(Sucursal sucursal)
/* 251:    */   {
/* 252:351 */     this.sucursal = sucursal;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public int getIdCategoriaEmpresa()
/* 256:    */   {
/* 257:360 */     return this.idCategoriaEmpresa;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setIdCategoriaEmpresa(int idCategoriaEmpresa)
/* 261:    */   {
/* 262:370 */     this.idCategoriaEmpresa = idCategoriaEmpresa;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 266:    */   {
/* 267:379 */     if (this.listaCategoriaEmpresa == null) {
/* 268:380 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/* 269:    */     }
/* 270:382 */     return this.listaCategoriaEmpresa;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 274:    */   {
/* 275:392 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public boolean isIndicadorMostrarProvision()
/* 279:    */   {
/* 280:401 */     return this.indicadorMostrarProvision;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setIndicadorMostrarProvision(boolean indicadorMostrarProvision)
/* 284:    */   {
/* 285:411 */     this.indicadorMostrarProvision = indicadorMostrarProvision;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public enumAgrupado getAgrupado()
/* 289:    */   {
/* 290:420 */     return this.agrupado;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setAgrupado(enumAgrupado agrupado)
/* 294:    */   {
/* 295:430 */     this.agrupado = agrupado;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public List<SelectItem> getListaAgrupado()
/* 299:    */   {
/* 300:437 */     if (this.listaAgrupado == null)
/* 301:    */     {
/* 302:438 */       this.listaAgrupado = new ArrayList();
/* 303:439 */       for (enumAgrupado agrupado : enumAgrupado.values())
/* 304:    */       {
/* 305:440 */         SelectItem item = new SelectItem(agrupado, agrupado.name());
/* 306:441 */         this.listaAgrupado.add(item);
/* 307:    */       }
/* 308:    */     }
/* 309:444 */     return this.listaAgrupado;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setListaAgrupado(List<SelectItem> listaAgrupado)
/* 313:    */   {
/* 314:452 */     this.listaAgrupado = listaAgrupado;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public int getIndicadorAgrupado()
/* 318:    */   {
/* 319:459 */     return this.indicadorAgrupado;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setIndicadorAgrupado(int indicadorAgrupado)
/* 323:    */   {
/* 324:467 */     this.indicadorAgrupado = indicadorAgrupado;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean isIndicadorCodigos()
/* 328:    */   {
/* 329:471 */     return this.indicadorCodigos;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setIndicadorCodigos(boolean indicadorCodigos)
/* 333:    */   {
/* 334:475 */     this.indicadorCodigos = indicadorCodigos;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public List<DimensionContable> autocompletarCentroCostos(String consulta)
/* 338:    */   {
/* 339:479 */     Map<String, String> filters = new HashMap();
/* 340:480 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 341:481 */     filters.put("numero", "1");
/* 342:482 */     filters.put("nombre", consulta);
/* 343:483 */     return this.servicioProyecto.obtenerListaCombo("codigo", true, filters);
/* 344:    */   }
/* 345:    */   
/* 346:    */   public DimensionContable getDimensionContable()
/* 347:    */   {
/* 348:487 */     return this.dimensionContable;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setDimensionContable(DimensionContable dimensionContable)
/* 352:    */   {
/* 353:491 */     this.dimensionContable = dimensionContable;
/* 354:    */   }
/* 355:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteIngresosEgresosBean
 * JD-Core Version:    0.7.0.1
 */