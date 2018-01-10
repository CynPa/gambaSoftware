/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.configuracion.servicio.impl.ServicioTipoOperacion;
/*   4:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.TipoOperacion;
/*  14:    */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*  15:    */ import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Calendar;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.RequestScoped;
/*  34:    */ import javax.faces.model.SelectItem;
/*  35:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  36:    */ import net.sf.jasperreports.engine.JRException;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ 
/*  39:    */ @ManagedBean
/*  40:    */ @RequestScoped
/*  41:    */ public class ReporteEstadoCuentaProveedorBean
/*  42:    */   extends AbstractBaseReportBean
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = -1451975169845268868L;
/*  45:    */   @EJB
/*  46:    */   private ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  47:    */   @EJB
/*  48:    */   private ServicioEmpresa servicioEmpresa;
/*  49:    */   @EJB
/*  50:    */   private ServicioReporteCompra servicioReporteCompra;
/*  51:    */   @EJB
/*  52:    */   private ServicioTipoOperacion servicioTipoOperacion;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioSucursal servicioSucursal;
/*  55:    */   @EJB
/*  56:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  57:    */   private Empresa empresa;
/*  58:    */   private Date fechaDesde;
/*  59:    */   private Date fechaHasta;
/*  60:    */   private TipoOperacion tipoOperacion;
/*  61:    */   private List<SelectItem> listaOrdenamiento;
/*  62: 87 */   private OrdenamientoEnum orden = OrdenamientoEnum.FECHA;
/*  63: 88 */   private boolean saldoDiferenteDeCero = false;
/*  64:    */   private Sucursal sucursal;
/*  65:    */   private CategoriaEmpresa categoriaEmpresa;
/*  66:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  67:    */   private List<Empresa> listaProveedores;
/*  68:    */   private List<TipoOperacion> listaTipoOperacion;
/*  69:    */   private List<Sucursal> listaSucursal;
/*  70:102 */   private static String COMPILE_FILE_NAME = "reporteEstadoCuenta";
/*  71:    */   
/*  72:    */   @PostConstruct
/*  73:    */   public void init()
/*  74:    */   {
/*  75:106 */     Calendar calfechaDesde = Calendar.getInstance();
/*  76:107 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  77:108 */     this.fechaDesde = calfechaDesde.getTime();
/*  78:109 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected String getCompileFileName()
/*  82:    */   {
/*  83:119 */     if (this.orden.equals(OrdenamientoEnum.FACTURA)) {
/*  84:120 */       COMPILE_FILE_NAME = "reporteEstadoCuentaFactura";
/*  85:121 */     } else if (this.orden.equals(OrdenamientoEnum.DOCUMENTO)) {
/*  86:122 */       COMPILE_FILE_NAME = "reporteEstadoCuentaCobro";
/*  87:123 */     } else if (this.orden.equals(OrdenamientoEnum.SALDO_FACTURA)) {
/*  88:124 */       COMPILE_FILE_NAME = "reporteEstadoCuentaSaldoFactura";
/*  89:    */     } else {
/*  90:126 */       COMPILE_FILE_NAME = "reporteEstadoCuenta";
/*  91:    */     }
/*  92:129 */     return COMPILE_FILE_NAME;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected Map<String, Object> getReportParameters()
/*  96:    */   {
/*  97:139 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  98:140 */     reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_estado_cuenta_titulo"));
/*  99:141 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 100:142 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 101:143 */     BigDecimal saldoAnticipo = this.servicioAnticipoProveedor.obtenerSaldoAnticipo(getEmpresa().getIdEmpresa(), this.fechaHasta);
/* 102:144 */     reportParameters.put("saldoAnticipo", saldoAnticipo);
/* 103:145 */     BigDecimal saldoInicial = this.servicioReporteCompra.obtenerSaldoEstadoCuenta(getEmpresa().getIdEmpresa(), this.fechaDesde, getTipoOperacion().getId());
/* 104:146 */     reportParameters.put("saldoInicial", saldoInicial);
/* 105:147 */     reportParameters.put("mostrarChequePosfechado", Boolean.valueOf(false));
/* 106:148 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/* 107:149 */     reportParameters.put("p_tipo_operacion", getTipoOperacion().getId() > 0 ? this.tipoOperacion.getNombre() : "Todos");
/* 108:150 */     reportParameters.put("reporteProveedor", Boolean.TRUE);
/* 109:151 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? getCategoriaEmpresa().getNombre() : "Todos");
/* 110:152 */     reportParameters.put("agrupadoCategoriaEmpresa", 
/* 111:153 */       Boolean.valueOf((this.categoriaEmpresa != null) && (this.categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()))));
/* 112:    */     
/* 113:155 */     return reportParameters;
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected JRDataSource getJRDataSource()
/* 117:    */   {
/* 118:166 */     List listaDatosReporte = new ArrayList();
/* 119:167 */     JRDataSource ds = null;
/* 120:    */     try
/* 121:    */     {
/* 122:169 */       listaDatosReporte = this.servicioReporteCompra.getListaReporteEstadoCuenta(this.fechaDesde, this.fechaHasta, getEmpresa().getIdEmpresa(), 
/* 123:170 */         getTipoOperacion().getId(), this.orden, this.saldoDiferenteDeCero, AppUtil.getOrganizacion().getIdOrganizacion(), getSucursal(), 
/* 124:171 */         getCategoriaEmpresa());
/* 125:    */       
/* 126:173 */       String[] fields = { "nombreCliente", "codigoCliente", "fechaFactura", "numeroFactura", "fechaVence", "detalleDocumento", "valorCompras", "valorPagos", "referenciaDocumento", "numeroDocumento", "tipoOperacion", "codigoDocumento", "codigoDocumentoProceso", "f_asiento", "f_asientoDocumento", "f_referencia1", "f_referencia2", "f_valorReferencia1", "f_valorReferencia2", "f_valorReferencia3", "identificacion", "idCategoriaEmpresa", "nombreCategoriaEmpresa", "descripcionFactura", "idPago" };
/* 127:    */       
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:178 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 132:    */     }
/* 133:    */     catch (ExcepcionAS2 e)
/* 134:    */     {
/* 135:180 */       LOG.info("Error " + e);
/* 136:181 */       e.printStackTrace();
/* 137:    */     }
/* 138:183 */     return ds;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String execute()
/* 142:    */   {
/* 143:    */     try
/* 144:    */     {
/* 145:194 */       if (this.empresa.getIdEmpresa() == 0)
/* 146:    */       {
/* 147:195 */         addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar_proveedor"));
/* 148:    */       }
/* 149:    */       else
/* 150:    */       {
/* 151:197 */         if (this.fechaDesde == null) {
/* 152:198 */           this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 153:    */         }
/* 154:200 */         if (this.fechaHasta == null) {
/* 155:201 */           this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 156:    */         }
/* 157:203 */         super.prepareReport();
/* 158:    */       }
/* 159:    */     }
/* 160:    */     catch (JRException e)
/* 161:    */     {
/* 162:207 */       LOG.info("Error JRException");
/* 163:208 */       e.printStackTrace();
/* 164:209 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 165:    */     }
/* 166:    */     catch (IOException e)
/* 167:    */     {
/* 168:211 */       LOG.info("Error IOException");
/* 169:212 */       e.printStackTrace();
/* 170:213 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 171:    */     }
/* 172:216 */     return "";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Empresa getEmpresa()
/* 176:    */   {
/* 177:227 */     if (this.empresa == null) {
/* 178:228 */       this.empresa = new Empresa();
/* 179:    */     }
/* 180:230 */     return this.empresa;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setEmpresa(Empresa empresa)
/* 184:    */   {
/* 185:240 */     this.empresa = empresa;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Date getFechaDesde()
/* 189:    */   {
/* 190:249 */     return this.fechaDesde;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setFechaDesde(Date fechaDesde)
/* 194:    */   {
/* 195:259 */     this.fechaDesde = fechaDesde;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Date getFechaHasta()
/* 199:    */   {
/* 200:268 */     return this.fechaHasta;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public TipoOperacion getTipoOperacion()
/* 204:    */   {
/* 205:277 */     if (this.tipoOperacion == null) {
/* 206:278 */       this.tipoOperacion = new TipoOperacion();
/* 207:    */     }
/* 208:280 */     return this.tipoOperacion;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setTipoOperacion(TipoOperacion tipoOperacion)
/* 212:    */   {
/* 213:290 */     this.tipoOperacion = tipoOperacion;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setFechaHasta(Date fechaHasta)
/* 217:    */   {
/* 218:300 */     this.fechaHasta = fechaHasta;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<Empresa> getListaProveedores()
/* 222:    */   {
/* 223:304 */     if (this.listaProveedores == null) {
/* 224:305 */       this.listaProveedores = this.servicioEmpresa.obtenerClientes();
/* 225:    */     }
/* 226:307 */     return this.listaProveedores;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setListaProveedores(List<Empresa> listaProveedores)
/* 230:    */   {
/* 231:311 */     this.listaProveedores = listaProveedores;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public List<TipoOperacion> getListaTipoOperacion()
/* 235:    */   {
/* 236:320 */     if (this.listaTipoOperacion == null) {
/* 237:321 */       this.listaTipoOperacion = this.servicioTipoOperacion.obtenerListaCombo("nombre", true, null);
/* 238:    */     }
/* 239:323 */     return this.listaTipoOperacion;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setListaTipoOperacion(List<TipoOperacion> listaTipoOperacion)
/* 243:    */   {
/* 244:333 */     this.listaTipoOperacion = listaTipoOperacion;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 248:    */   {
/* 249:337 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public List<SelectItem> getListaOrdenamiento()
/* 253:    */   {
/* 254:341 */     if (this.listaOrdenamiento == null)
/* 255:    */     {
/* 256:342 */       this.listaOrdenamiento = new ArrayList();
/* 257:343 */       SelectItem item = new SelectItem(OrdenamientoEnum.FECHA, OrdenamientoEnum.FECHA.getNombre());
/* 258:344 */       this.listaOrdenamiento.add(item);
/* 259:345 */       item = new SelectItem(OrdenamientoEnum.FACTURA, OrdenamientoEnum.FACTURA.getNombre());
/* 260:346 */       this.listaOrdenamiento.add(item);
/* 261:347 */       item = new SelectItem(OrdenamientoEnum.DOCUMENTO, OrdenamientoEnum.DOCUMENTO.getNombre());
/* 262:348 */       this.listaOrdenamiento.add(item);
/* 263:349 */       item = new SelectItem(OrdenamientoEnum.SALDO_FACTURA, OrdenamientoEnum.SALDO_FACTURA.getNombre());
/* 264:350 */       this.listaOrdenamiento.add(item);
/* 265:    */     }
/* 266:352 */     return this.listaOrdenamiento;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public OrdenamientoEnum getOrden()
/* 270:    */   {
/* 271:357 */     return this.orden;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setOrden(OrdenamientoEnum orden)
/* 275:    */   {
/* 276:361 */     this.orden = orden;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public boolean isSaldoDiferenteDeCero()
/* 280:    */   {
/* 281:365 */     return this.saldoDiferenteDeCero;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setSaldoDiferenteDeCero(boolean saldoDiferenteDeCero)
/* 285:    */   {
/* 286:369 */     this.saldoDiferenteDeCero = saldoDiferenteDeCero;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Sucursal getSucursal()
/* 290:    */   {
/* 291:376 */     return this.sucursal;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setSucursal(Sucursal sucursal)
/* 295:    */   {
/* 296:384 */     this.sucursal = sucursal;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<Sucursal> getListaSucursal()
/* 300:    */   {
/* 301:391 */     if (this.listaSucursal == null)
/* 302:    */     {
/* 303:392 */       Map<String, String> filters = new HashMap();
/* 304:393 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 305:394 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 306:    */     }
/* 307:397 */     return this.listaSucursal;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 311:    */   {
/* 312:405 */     this.listaSucursal = listaSucursal;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 316:    */   {
/* 317:409 */     return this.categoriaEmpresa;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 321:    */   {
/* 322:413 */     this.categoriaEmpresa = categoriaEmpresa;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 326:    */   {
/* 327:422 */     HashMap<String, String> filtros = new HashMap();
/* 328:423 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 329:424 */     if (this.listaCategoriaEmpresa == null)
/* 330:    */     {
/* 331:425 */       this.listaCategoriaEmpresa = new ArrayList();
/* 332:426 */       this.listaCategoriaEmpresa.add(new CategoriaEmpresa(-99001, CategoriaEmpresaEnum.TODOS_AGRUPADO.name(), "Todos Agrupado..."));
/* 333:427 */       this.listaCategoriaEmpresa.addAll(this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros));
/* 334:    */     }
/* 335:430 */     return this.listaCategoriaEmpresa;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 339:    */   {
/* 340:440 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 341:    */   }
/* 342:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteEstadoCuentaProveedorBean
 * JD-Core Version:    0.7.0.1
 */