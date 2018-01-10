/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Atributo;
/*   8:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   9:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.entities.Subempresa;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.HashMap;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.faces.bean.ManagedBean;
/*  31:    */ import javax.faces.bean.ViewScoped;
/*  32:    */ import javax.faces.model.SelectItem;
/*  33:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  34:    */ import net.sf.jasperreports.engine.JRException;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ @ViewScoped
/*  39:    */ public class reporteResultadoNetoProductosVendidosBean
/*  40:    */   extends AbstractBaseReportBean
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  43:    */   @EJB
/*  44:    */   private ServicioReporteVenta servicioReporteVenta;
/*  45:    */   @EJB
/*  46:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  47:    */   @EJB
/*  48:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  49:    */   @EJB
/*  50:    */   private ServicioAtributo servicioAtributo;
/*  51:    */   @EJB
/*  52:    */   private ServicioSucursal servicioSucursal;
/*  53:    */   @EJB
/*  54:    */   protected ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  55:    */   @EJB
/*  56:    */   private ServicioEmpresa servicioEmpresa;
/*  57: 77 */   private Date fechaDesde = FuncionesUtiles.getFechaInicioMes(new Date());
/*  58: 78 */   private Date fechaHasta = FuncionesUtiles.getFechaFinMes(new Date());
/*  59:    */   private CategoriaProducto categoriaProducto;
/*  60:    */   private SubcategoriaProducto subcategoriaProducto;
/*  61:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  62:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  63:    */   private Atributo atributo;
/*  64: 84 */   private String valorAtributo = "";
/*  65:    */   private List<Atributo> listaAtributo;
/*  66:    */   private boolean cliente;
/*  67:    */   private Sucursal sucursal;
/*  68: 88 */   private boolean saldoInicial = false;
/*  69:    */   private CategoriaEmpresa categoriaEmpresa;
/*  70:    */   private Empresa empresa;
/*  71:    */   private Subempresa subempresa;
/*  72:    */   private List<Subempresa> listaSubempresa;
/*  73:    */   
/*  74:    */   private static enum TipoReporteEnum
/*  75:    */   {
/*  76: 96 */     PRODUCTO("producto"),  CLIENTE("cliente"),  FACTURA("factura"),  FACTURA_PRODUCTO("facturaProducto");
/*  77:    */     
/*  78:    */     private String nombre;
/*  79:    */     
/*  80:    */     private TipoReporteEnum(String nombre)
/*  81:    */     {
/*  82:100 */       this.nombre = nombre;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public String getNombre()
/*  86:    */     {
/*  87:109 */       return this.nombre;
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:114 */   private TipoReporteEnum tipoReporte = TipoReporteEnum.PRODUCTO;
/*  92:    */   private List<SelectItem> listaTipoReporte;
/*  93:    */   private List<Sucursal> listaSucursalCombo;
/*  94:    */   
/*  95:    */   protected JRDataSource getJRDataSource()
/*  96:    */   {
/*  97:127 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  98:128 */     JRDataSource ds = null;
/*  99:    */     
/* 100:    */ 
/* 101:131 */     listaDatosReporte = this.servicioReporteVenta.getReporteResultadoNetoProductosVendidos(this.fechaDesde, this.fechaHasta, AppUtil.getOrganizacion()
/* 102:132 */       .getIdOrganizacion(), this.categoriaProducto, this.subcategoriaProducto, this.atributo, this.valorAtributo, this.tipoReporte.getNombre(), this.sucursal, this.saldoInicial, this.categoriaEmpresa, this.empresa);
/* 103:    */     
/* 104:    */ 
/* 105:135 */     String[] fields = { "f_identificacionEmpresa", "f_nombreFiscalEmpresa", "f_codigoCategoriaEmpresa", "f_nombreCategoriaEmpresa", "f_codigoSucursal", "f_nombreSucursal", "f_codigoProducto", "f_nombreProducto", "f_codigoBodega", "f_nombreBodega", "f_numeroFactura", "f_fechaFactura", "f_numeroDespachoCliente", "f_fechaDespachoCliente", "f_codigoAlternoProducto", "f_volumenProducto", "f_nombreSubcategoriaProducto", "f_codigoSubcategoriaProducto", "f_nombreCategoriaProducto", "f_codigoCategoriaProducto", "unidadNombre", "unidadCodigo", "f_agenteComercial", "f_canal", "f_cantidadVentas", "f_valorUnitarioVentas", "f_valorTotalVentas", "f_cantidadDespachos", "f_valorUnitarioDespachos", "f_valorTotalDespachos", "f_cantidadNotaCredito", "f_valorUnitarioNotaCredito", "f_valorTotalNotaCredito", "f_cantidadDevolucion", "f_valorUnitarioDevolucion", "f_valorTotalDevolucion", "f_cantidadDevolucionCostos", "f_valorUnitarioDevolucionCostos", "f_valorTotalDevolucionCostos", "f_cantidadDebito", "f_valorUnitarioDebito", "f_valorTotalDebito" };
/* 106:    */     
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:144 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 115:    */     
/* 116:146 */     return ds;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 120:    */   {
/* 121:150 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected String getCompileFileName()
/* 125:    */   {
/* 126:163 */     if (this.tipoReporte.equals(TipoReporteEnum.FACTURA)) {
/* 127:164 */       return "reporteResultadoNetoProductosVendidosPorFactura";
/* 128:    */     }
/* 129:166 */     if (this.tipoReporte.equals(TipoReporteEnum.FACTURA_PRODUCTO)) {
/* 130:167 */       return "reporteResultadoNetoProductosVendidosPorFacturaProducto";
/* 131:    */     }
/* 132:169 */     if (this.tipoReporte.equals(TipoReporteEnum.CLIENTE)) {
/* 133:170 */       return "reporteResultadoNetoProductosVendidosPorCliente";
/* 134:    */     }
/* 135:172 */     return "reporteResultadoNetoProductosVendidos";
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected Map<String, Object> getReportParameters()
/* 139:    */   {
/* 140:182 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 141:183 */     reportParameters.put("p_resumido", Boolean.valueOf(this.cliente));
/* 142:184 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 143:185 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 144:186 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? this.categoriaEmpresa.getNombre() : "Todas");
/* 145:187 */     reportParameters.put("p_empresa", this.empresa != null ? this.empresa.getNombreComercial() : "Todas");
/* 146:    */     
/* 147:189 */     return reportParameters;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String execute()
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:199 */       super.prepareReport();
/* 155:    */     }
/* 156:    */     catch (JRException e)
/* 157:    */     {
/* 158:201 */       LOG.info("Error JRException");
/* 159:202 */       e.printStackTrace();
/* 160:203 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 161:    */     }
/* 162:    */     catch (IOException e)
/* 163:    */     {
/* 164:205 */       LOG.info("Error IOException");
/* 165:206 */       e.printStackTrace();
/* 166:207 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 167:    */     }
/* 168:210 */     return null;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void cargarListaSubcategoriaProducto()
/* 172:    */   {
/* 173:214 */     HashMap<String, String> filters = new HashMap();
/* 174:215 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 175:216 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Date getFechaDesde()
/* 179:    */   {
/* 180:225 */     return this.fechaDesde;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setFechaDesde(Date fechaDesde)
/* 184:    */   {
/* 185:235 */     this.fechaDesde = fechaDesde;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Date getFechaHasta()
/* 189:    */   {
/* 190:244 */     return this.fechaHasta;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setFechaHasta(Date fechaHasta)
/* 194:    */   {
/* 195:254 */     this.fechaHasta = fechaHasta;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public CategoriaProducto getCategoriaProducto()
/* 199:    */   {
/* 200:258 */     return this.categoriaProducto;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 204:    */   {
/* 205:262 */     this.categoriaProducto = categoriaProducto;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 209:    */   {
/* 210:266 */     return this.subcategoriaProducto;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 214:    */   {
/* 215:270 */     this.subcategoriaProducto = subcategoriaProducto;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 219:    */   {
/* 220:274 */     HashMap<String, String> filters = new HashMap();
/* 221:275 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 222:276 */     if (this.listaCategoriaProductos == null) {
/* 223:277 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 224:    */     }
/* 225:279 */     return this.listaCategoriaProductos;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 229:    */   {
/* 230:283 */     return this.listaSubcategoriaProductos;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 234:    */   {
/* 235:287 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 239:    */   {
/* 240:291 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public List<Atributo> getListaAtributo()
/* 244:    */   {
/* 245:295 */     HashMap<String, String> filters = new HashMap();
/* 246:296 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 247:297 */     filters.put("indicadorProducto", "true");
/* 248:299 */     if (this.listaAtributo == null) {
/* 249:300 */       this.listaAtributo = this.servicioAtributo.obtenerListaCombo("nombre", true, filters);
/* 250:    */     }
/* 251:302 */     return this.listaAtributo;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Atributo getAtributo()
/* 255:    */   {
/* 256:306 */     return this.atributo;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setAtributo(Atributo atributo)
/* 260:    */   {
/* 261:310 */     this.atributo = atributo;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String getValorAtributo()
/* 265:    */   {
/* 266:314 */     return this.valorAtributo;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setValorAtributo(String valorAtributo)
/* 270:    */   {
/* 271:318 */     this.valorAtributo = valorAtributo;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setListaAtributo(List<Atributo> listaAtributo)
/* 275:    */   {
/* 276:322 */     this.listaAtributo = listaAtributo;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public boolean isCliente()
/* 280:    */   {
/* 281:326 */     return this.cliente;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setCliente(boolean cliente)
/* 285:    */   {
/* 286:330 */     this.cliente = cliente;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Sucursal getSucursal()
/* 290:    */   {
/* 291:334 */     return this.sucursal;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setSucursal(Sucursal sucursal)
/* 295:    */   {
/* 296:338 */     this.sucursal = sucursal;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public TipoReporteEnum getTipoReporte()
/* 300:    */   {
/* 301:342 */     return this.tipoReporte;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setTipoReporte(TipoReporteEnum tipoReporte)
/* 305:    */   {
/* 306:346 */     this.tipoReporte = tipoReporte;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public List<SelectItem> getListaTipoReporte()
/* 310:    */   {
/* 311:350 */     if (this.listaTipoReporte == null)
/* 312:    */     {
/* 313:351 */       this.listaTipoReporte = new ArrayList();
/* 314:352 */       for (TipoReporteEnum tr : TipoReporteEnum.values()) {
/* 315:353 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 316:    */       }
/* 317:    */     }
/* 318:357 */     return this.listaTipoReporte;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public List<Sucursal> getListaSucursalCombo()
/* 322:    */   {
/* 323:366 */     HashMap<String, String> filters = new HashMap();
/* 324:367 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 325:368 */     if (this.listaSucursalCombo == null) {
/* 326:369 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 327:    */     }
/* 328:371 */     return this.listaSucursalCombo;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 332:    */   {
/* 333:381 */     this.listaSucursalCombo = listaSucursalCombo;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public boolean isSaldoInicial()
/* 337:    */   {
/* 338:385 */     return this.saldoInicial;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setSaldoInicial(boolean saldoInicial)
/* 342:    */   {
/* 343:389 */     this.saldoInicial = saldoInicial;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 347:    */   {
/* 348:396 */     return this.categoriaEmpresa;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 352:    */   {
/* 353:403 */     this.categoriaEmpresa = categoriaEmpresa;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 357:    */   {
/* 358:407 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 359:408 */     List<CategoriaEmpresa> lista = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros);
/* 360:409 */     return lista;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void actualizarCategoriaEmpresa()
/* 364:    */   {
/* 365:413 */     this.empresa = null;
/* 366:414 */     this.subempresa = null;
/* 367:415 */     this.listaSubempresa = new ArrayList();
/* 368:    */   }
/* 369:    */   
/* 370:    */   public Empresa getEmpresa()
/* 371:    */   {
/* 372:422 */     return this.empresa;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setEmpresa(Empresa empresa)
/* 376:    */   {
/* 377:429 */     this.empresa = empresa;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public Subempresa getSubempresa()
/* 381:    */   {
/* 382:436 */     return this.subempresa;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setSubempresa(Subempresa subempresa)
/* 386:    */   {
/* 387:443 */     this.subempresa = subempresa;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public boolean isIndicadorEmpresa()
/* 391:    */   {
/* 392:450 */     boolean indicadorEmpresa = false;
/* 393:451 */     if (getEmpresa() != null) {
/* 394:452 */       indicadorEmpresa = true;
/* 395:    */     }
/* 396:454 */     return indicadorEmpresa;
/* 397:    */   }
/* 398:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.reporteResultadoNetoProductosVendidosBean
 * JD-Core Version:    0.7.0.1
 */