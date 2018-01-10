/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPagoComision;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Calendar;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ManagedProperty;
/*  33:    */ import javax.faces.bean.ViewScoped;
/*  34:    */ import javax.faces.model.SelectItem;
/*  35:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  36:    */ import net.sf.jasperreports.engine.JRException;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ 
/*  39:    */ @ManagedBean
/*  40:    */ @ViewScoped
/*  41:    */ public class ReporteComisionesBean
/*  42:    */   extends AbstractBaseReportBean
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = -192264607856793455L;
/*  45:    */   @EJB
/*  46:    */   private ServicioPagoComision servicioPagoComision;
/*  47:    */   @EJB
/*  48:    */   private ServicioEmpresa servicioEmpresa;
/*  49:    */   @EJB
/*  50:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  51:    */   @EJB
/*  52:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  53:    */   @EJB
/*  54:    */   private ServicioUsuario servicioUsuario;
/*  55:    */   @EJB
/*  56:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  57:    */   private EntidadUsuario agenteComercial;
/*  58:    */   private CategoriaProducto categoriaProducto;
/*  59:    */   private SubcategoriaProducto subcategoriaProducto;
/*  60:    */   private Producto producto;
/*  61:    */   private CategoriaEmpresa categoriaEmpresa;
/*  62:    */   private Empresa empresa;
/*  63:    */   private TipoReporte tipoReporte;
/*  64:    */   private Date fechaDesde;
/*  65:    */   private Date fechaHasta;
/*  66:    */   private List<EntidadUsuario> listaAgenteComercial;
/*  67:    */   private List<CategoriaProducto> listaCategoriaProducto;
/*  68:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  69:    */   private List<SelectItem> listaTipoReporte;
/*  70:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  71:    */   @ManagedProperty("#{listaProductoBean}")
/*  72:    */   private ListaProductoBean listaProductoBean;
/*  73:    */   
/*  74:    */   static enum TipoReporte
/*  75:    */   {
/*  76: 96 */     DETALLADO("Reporte Detallado", "reporteComisionesDetallado", "Reporte Comisiones Detallado"),  RESUMIDO("Reporte Resumido", "reporteComisionesResumido", "Reporte Comisiones Resumido");
/*  77:    */     
/*  78:    */     private String nombre;
/*  79:    */     private String COMPILE_FILE_NAME;
/*  80:    */     private String titulo;
/*  81:    */     
/*  82:    */     private TipoReporte(String nombre, String COMPILE_FILE_NAME, String titulo)
/*  83:    */     {
/*  84:104 */       this.nombre = nombre;
/*  85:105 */       this.COMPILE_FILE_NAME = COMPILE_FILE_NAME;
/*  86:106 */       this.titulo = titulo;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public String getNombre()
/*  90:    */     {
/*  91:110 */       return this.nombre;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public String getCOMPILE_FILE_NAME()
/*  95:    */     {
/*  96:114 */       return this.COMPILE_FILE_NAME;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public String getTitulo()
/* 100:    */     {
/* 101:118 */       return this.titulo;
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   @PostConstruct
/* 106:    */   public void init()
/* 107:    */   {
/* 108:124 */     getListaProductoBean().setIndicadorVenta(true);
/* 109:    */     
/* 110:126 */     Calendar calfechaDesde = Calendar.getInstance();
/* 111:127 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 112:128 */     this.fechaDesde = calfechaDesde.getTime();
/* 113:129 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 114:130 */     this.tipoReporte = TipoReporte.DETALLADO;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected JRDataSource getJRDataSource()
/* 118:    */   {
/* 119:135 */     JRDataSource ds = null;
/* 120:136 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 121:    */     try
/* 122:    */     {
/* 123:138 */       listaDatosReporte = this.servicioPagoComision.getReportePagoComision(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, this.agenteComercial, this.categoriaProducto, this.subcategoriaProducto, this.producto, this.categoriaEmpresa, this.empresa);
/* 124:    */       
/* 125:    */ 
/* 126:141 */       String[] fields = { "f_documento", "f_numeroPagoComision", "f_fechaPagoComision", "f_mesInicialPagoComision", "f_mesFinalPagoComision", "f_anioInicialPagoComision", "f_anioFinalPagoComision", "f_estadoPagoComision", "f_descripcionPagoComision", "f_agenteComercial", "f_numeroFacturaCliente", "f_nombreCategoriaProducto", "f_nombreSubcategoriaProducto", "f_codigoProducto", "f_nombreProducto", "f_rangoDiasComision", "f_numeroCobro", "f_baseComision", "f_formaPagoComisionEnum", "f_porcentajeComision", "f_valorComision", "f_nombreEmpresa" };
/* 127:    */       
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:147 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 133:    */     }
/* 134:    */     catch (Exception e)
/* 135:    */     {
/* 136:149 */       LOG.info("Error " + e);
/* 137:150 */       e.printStackTrace();
/* 138:    */     }
/* 139:152 */     return ds;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected String getCompileFileName()
/* 143:    */   {
/* 144:157 */     return this.tipoReporte.getCOMPILE_FILE_NAME();
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected Map<String, Object> getReportParameters()
/* 148:    */   {
/* 149:162 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 150:163 */     reportParameters.put("ReportTitle", this.tipoReporte.getTitulo());
/* 151:164 */     reportParameters.put("p_agenteComercial", this.agenteComercial
/* 152:165 */       .getNombre1() + " " + this.agenteComercial.getNombre2());
/* 153:166 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa == null ? "TODOS" : this.categoriaEmpresa.getNombre());
/* 154:167 */     reportParameters.put("p_empresa", this.empresa == null ? "TODOS" : this.empresa.getNombreFiscal());
/* 155:168 */     reportParameters.put("p_categoriaProducto", this.categoriaProducto == null ? "TODOS" : this.categoriaProducto.getNombre());
/* 156:169 */     reportParameters.put("p_subcategoriaProducto", this.subcategoriaProducto == null ? "TODOS" : this.subcategoriaProducto.getNombre());
/* 157:170 */     reportParameters.put("p_producto", this.producto == null ? "TODOS" : this.producto.getNombre());
/* 158:171 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 159:172 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 160:173 */     return reportParameters;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String execute()
/* 164:    */   {
/* 165:    */     try
/* 166:    */     {
/* 167:182 */       super.prepareReport();
/* 168:    */     }
/* 169:    */     catch (JRException e)
/* 170:    */     {
/* 171:184 */       LOG.info("Error JRException");
/* 172:185 */       e.printStackTrace();
/* 173:186 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 174:    */     }
/* 175:    */     catch (IOException e)
/* 176:    */     {
/* 177:188 */       LOG.info("Error IOException");
/* 178:189 */       e.printStackTrace();
/* 179:190 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 180:    */     }
/* 181:192 */     return "";
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<CategoriaProducto> getListaCategoriaProducto()
/* 185:    */   {
/* 186:196 */     if (this.listaCategoriaProducto == null)
/* 187:    */     {
/* 188:197 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 189:198 */       filtros.put("activo", "true");
/* 190:199 */       this.listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filtros);
/* 191:    */     }
/* 192:201 */     return this.listaCategoriaProducto;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void actualizarCategoriaProducto()
/* 196:    */   {
/* 197:205 */     this.subcategoriaProducto = null;
/* 198:206 */     this.listaSubcategoriaProducto = null;
/* 199:207 */     actualizarSubcategoriaProducto();
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void actualizarCategoriaEmpresa()
/* 203:    */   {
/* 204:211 */     this.empresa = null;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void actualizarSubcategoriaProducto()
/* 208:    */   {
/* 209:215 */     this.producto = null;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 213:    */   {
/* 214:219 */     if (this.listaSubcategoriaProducto == null)
/* 215:    */     {
/* 216:220 */       this.listaSubcategoriaProducto = new ArrayList();
/* 217:221 */       if (this.categoriaProducto != null)
/* 218:    */       {
/* 219:222 */         Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 220:223 */         filtros.put("activo", "true");
/* 221:224 */         filtros.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 222:225 */         this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filtros);
/* 223:    */       }
/* 224:    */     }
/* 225:228 */     return this.listaSubcategoriaProducto;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<EntidadUsuario> getListaAgenteComercial()
/* 229:    */   {
/* 230:232 */     if (this.listaAgenteComercial == null) {
/* 231:233 */       this.listaAgenteComercial = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId(), Boolean.valueOf(true));
/* 232:    */     }
/* 233:235 */     return this.listaAgenteComercial;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<SelectItem> getListaTipoReporte()
/* 237:    */   {
/* 238:239 */     if (this.listaTipoReporte == null)
/* 239:    */     {
/* 240:240 */       this.listaTipoReporte = new ArrayList();
/* 241:241 */       for (TipoReporte tipoReporte : TipoReporte.values())
/* 242:    */       {
/* 243:242 */         SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 244:243 */         this.listaTipoReporte.add(item);
/* 245:    */       }
/* 246:    */     }
/* 247:247 */     return this.listaTipoReporte;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 251:    */   {
/* 252:251 */     if (this.listaCategoriaEmpresa == null)
/* 253:    */     {
/* 254:252 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 255:253 */       filtros.put("activo", "true");
/* 256:254 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros);
/* 257:    */     }
/* 258:256 */     return this.listaCategoriaEmpresa;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void cargarProducto(Producto producto)
/* 262:    */   {
/* 263:260 */     this.producto = producto;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Integer getClasificacionPagoComisiones()
/* 267:    */   {
/* 268:264 */     return ParametrosSistema.getClasificadorPagoComisiones(AppUtil.getOrganizacion().getId());
/* 269:    */   }
/* 270:    */   
/* 271:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 272:    */   {
/* 273:268 */     return this.servicioEmpresa.autocompletarClientes(consulta, false, this.categoriaEmpresa);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public EntidadUsuario getAgenteComercial()
/* 277:    */   {
/* 278:272 */     return this.agenteComercial;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 282:    */   {
/* 283:276 */     this.agenteComercial = agenteComercial;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public CategoriaProducto getCategoriaProducto()
/* 287:    */   {
/* 288:280 */     return this.categoriaProducto;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 292:    */   {
/* 293:284 */     this.categoriaProducto = categoriaProducto;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 297:    */   {
/* 298:288 */     return this.subcategoriaProducto;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 302:    */   {
/* 303:292 */     this.subcategoriaProducto = subcategoriaProducto;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Producto getProducto()
/* 307:    */   {
/* 308:296 */     return this.producto;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setProducto(Producto producto)
/* 312:    */   {
/* 313:300 */     this.producto = producto;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Empresa getEmpresa()
/* 317:    */   {
/* 318:304 */     return this.empresa;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setEmpresa(Empresa empresa)
/* 322:    */   {
/* 323:308 */     this.empresa = empresa;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public Date getFechaDesde()
/* 327:    */   {
/* 328:312 */     return this.fechaDesde;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setFechaDesde(Date fechaDesde)
/* 332:    */   {
/* 333:316 */     this.fechaDesde = fechaDesde;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public Date getFechaHasta()
/* 337:    */   {
/* 338:320 */     return this.fechaHasta;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setFechaHasta(Date fechaHasta)
/* 342:    */   {
/* 343:324 */     this.fechaHasta = fechaHasta;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 347:    */   {
/* 348:328 */     return this.categoriaEmpresa;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 352:    */   {
/* 353:332 */     this.categoriaEmpresa = categoriaEmpresa;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public TipoReporte getTipoReporte()
/* 357:    */   {
/* 358:336 */     return this.tipoReporte;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 362:    */   {
/* 363:340 */     this.tipoReporte = tipoReporte;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public ListaProductoBean getListaProductoBean()
/* 367:    */   {
/* 368:344 */     return this.listaProductoBean;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 372:    */   {
/* 373:348 */     this.listaProductoBean = listaProductoBean;
/* 374:    */   }
/* 375:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteComisionesBean
 * JD-Core Version:    0.7.0.1
 */