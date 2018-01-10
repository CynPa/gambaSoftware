/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteRecepcionProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  19:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  20:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  21:    */ import java.io.IOException;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Calendar;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.annotation.PostConstruct;
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
/*  39:    */ public class ReporteRecepcionesProveedorBean
/*  40:    */   extends AbstractBaseReportBean
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 1L;
/*  43:    */   @EJB
/*  44:    */   private ServicioEmpresa servicioEmpresa;
/*  45:    */   @EJB
/*  46:    */   private ServicioReporteRecepcionProveedor servicioReporteRecepcionProveedor;
/*  47:    */   @EJB
/*  48:    */   private ServicioBodega servicioBodega;
/*  49:    */   @EJB
/*  50:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  51:    */   @EJB
/*  52:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  53:    */   private Empresa empresa;
/*  54:    */   private PersonaResponsable responsableSalidaMercaderia;
/*  55:    */   private Date fechaDesde;
/*  56:    */   private Date fechaHasta;
/*  57:    */   private Bodega bodega;
/*  58:    */   private SubcategoriaProducto subcategoriaProducto;
/*  59:    */   private CategoriaProducto categoriaProducto;
/*  60:    */   private Producto producto;
/*  61:    */   private List<Bodega> listaBodega;
/*  62:    */   private List<SelectItem> listaTipoReporte;
/*  63:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  64:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  65:    */   private TipoReporte tipoReporte;
/*  66:    */   
/*  67:    */   static enum TipoReporte
/*  68:    */   {
/*  69: 89 */     POR_PROVEEDOR("Reporte por Proveedor"),  POR_PRODUCTO("Reporte por Productos"),  POR_LOTES("Reporte por Lote");
/*  70:    */     
/*  71:    */     private String nombre;
/*  72:    */     
/*  73:    */     private TipoReporte(String nombre)
/*  74:    */     {
/*  75: 94 */       this.nombre = nombre;
/*  76:    */     }
/*  77:    */     
/*  78:    */     public String getNombre()
/*  79:    */     {
/*  80: 98 */       return this.nombre;
/*  81:    */     }
/*  82:    */     
/*  83:    */     public void setNombre(String nombre)
/*  84:    */     {
/*  85:102 */       this.nombre = nombre;
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected JRDataSource getJRDataSource()
/*  90:    */   {
/*  91:116 */     List listaDatosReporte = new ArrayList();
/*  92:117 */     JRDataSource ds = null;
/*  93:    */     
/*  94:119 */     listaDatosReporte = this.servicioReporteRecepcionProveedor.getReporteRecepcionProveedor(AppUtil.getOrganizacion().getIdOrganizacion(), this.tipoReporte
/*  95:120 */       .ordinal(), getEmpresa().getId(), this.bodega, this.categoriaProducto, this.subcategoriaProducto, this.producto, this.fechaDesde, this.fechaHasta);
/*  96:    */     
/*  97:122 */     String[] fields = null;
/*  98:124 */     if (this.tipoReporte.equals(TipoReporte.POR_PROVEEDOR)) {
/*  99:125 */       fields = new String[] { "f_numero", "f_fecha", "f_nombreComercial", "f_nombreFiscal", "f_identificacion", "f_usuario", "f_descripcion", "f_recepcion", "f_factura", "f_compra", "f_codigoProducto", "f_codigoComercialProducto", "f_nombreProducto", "f_nombreComercialProducto", "f_unidad", "f_codigoBodega", "f_nombreBodega", "f_cantidad", "f_costo" };
/* 100:128 */     } else if (this.tipoReporte.equals(TipoReporte.POR_PRODUCTO)) {
/* 101:129 */       fields = new String[] { "f_codigoProducto", "f_codigoComercialProducto", "f_nombreProducto", "f_nombreComercialProducto", "f_unidad", "f_codigoBodega", "f_nombreBodega", "f_cantidad", "f_costo" };
/* 102:131 */     } else if (this.tipoReporte.equals(TipoReporte.POR_LOTES)) {
/* 103:132 */       fields = new String[] { "f_numero", "f_fecha", "f_nombreComercial", "f_nombreFiscal", "f_identificacion", "f_usuario", "f_descripcion", "f_recepcion", "f_factura", "f_compra", "f_codigoProducto", "f_codigoComercialProducto", "f_nombreProducto", "f_nombreComercialProducto", "f_unidad", "f_codigoBodega", "f_nombreBodega", "f_cantidad", "f_costo", "f_lote" };
/* 104:    */     }
/* 105:136 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 106:    */     
/* 107:138 */     return ds;
/* 108:    */   }
/* 109:    */   
/* 110:    */   @PostConstruct
/* 111:    */   public void init()
/* 112:    */   {
/* 113:143 */     Calendar calfechaDesde = Calendar.getInstance();
/* 114:144 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 115:145 */     this.fechaDesde = calfechaDesde.getTime();
/* 116:146 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 117:147 */     this.tipoReporte = TipoReporte.POR_PROVEEDOR;
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected String getCompileFileName()
/* 121:    */   {
/* 122:157 */     if (TipoReporte.POR_PRODUCTO.equals(this.tipoReporte)) {
/* 123:158 */       return "reporteRecepcionesProducto";
/* 124:    */     }
/* 125:159 */     if (TipoReporte.POR_PROVEEDOR.equals(this.tipoReporte)) {
/* 126:160 */       return "reporteRecepcionesProveedor";
/* 127:    */     }
/* 128:162 */     return "reporteRecepcionesLote";
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected Map<String, Object> getReportParameters()
/* 132:    */   {
/* 133:173 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 134:174 */     if (TipoReporte.POR_PROVEEDOR.equals(this.tipoReporte)) {
/* 135:175 */       reportParameters.put("ReportTitle", "Reporte Recepción por Proveedor");
/* 136:176 */     } else if (TipoReporte.POR_PRODUCTO.equals(this.tipoReporte)) {
/* 137:177 */       reportParameters.put("ReportTitle", "Reporte Recepción por Producto");
/* 138:    */     } else {
/* 139:179 */       reportParameters.put("ReportTitle", "Reporte Recepción por Lote");
/* 140:    */     }
/* 141:182 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 142:183 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 143:184 */     reportParameters.put("FormatoFecha", ParametrosSistema.getFormatoFecha(AppUtil.getOrganizacion().getId()));
/* 144:185 */     reportParameters.put("p_bodega", this.bodega != null ? this.bodega.getNombre() : "Todos");
/* 145:186 */     reportParameters.put("p_categoriaProducto", this.categoriaProducto != null ? this.categoriaProducto.getNombre() : "Todos");
/* 146:187 */     reportParameters.put("p_subcategoriaProducto", this.subcategoriaProducto != null ? this.subcategoriaProducto.getNombre() : "Todos");
/* 147:188 */     reportParameters.put("p_producto", this.producto != null ? this.producto.getNombre() : "Todos");
/* 148:189 */     reportParameters.put("p_empresa", this.empresa != null ? this.empresa.getNombreFiscal() : "Todos");
/* 149:    */     
/* 150:191 */     return reportParameters;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String execute()
/* 154:    */   {
/* 155:    */     try
/* 156:    */     {
/* 157:201 */       validaDatos();
/* 158:202 */       super.prepareReport();
/* 159:    */     }
/* 160:    */     catch (JRException e)
/* 161:    */     {
/* 162:204 */       LOG.info("Error JRException");
/* 163:205 */       e.printStackTrace();
/* 164:206 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 165:    */     }
/* 166:    */     catch (IOException e)
/* 167:    */     {
/* 168:208 */       LOG.info("Error IOException");
/* 169:209 */       e.printStackTrace();
/* 170:210 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 171:    */     }
/* 172:213 */     return null;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void validaDatos()
/* 176:    */   {
/* 177:217 */     if (this.fechaDesde == null) {
/* 178:218 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 179:    */     }
/* 180:220 */     if (this.fechaHasta == null) {
/* 181:221 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 186:    */   {
/* 187:232 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Date getFechaDesde()
/* 191:    */   {
/* 192:241 */     return this.fechaDesde;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setFechaDesde(Date fechaDesde)
/* 196:    */   {
/* 197:251 */     this.fechaDesde = fechaDesde;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Date getFechaHasta()
/* 201:    */   {
/* 202:260 */     return this.fechaHasta;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setFechaHasta(Date fechaHasta)
/* 206:    */   {
/* 207:270 */     this.fechaHasta = fechaHasta;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Empresa getEmpresa()
/* 211:    */   {
/* 212:279 */     if (this.empresa == null) {
/* 213:280 */       this.empresa = new Empresa();
/* 214:    */     }
/* 215:282 */     return this.empresa;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setEmpresa(Empresa empresa)
/* 219:    */   {
/* 220:292 */     this.empresa = empresa;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public PersonaResponsable getResponsableSalidaMercaderia()
/* 224:    */   {
/* 225:301 */     if (this.responsableSalidaMercaderia == null) {
/* 226:302 */       this.responsableSalidaMercaderia = new PersonaResponsable();
/* 227:    */     }
/* 228:304 */     return this.responsableSalidaMercaderia;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setResponsableSalidaMercaderia(PersonaResponsable responsableSalidaMercaderia)
/* 232:    */   {
/* 233:314 */     this.responsableSalidaMercaderia = responsableSalidaMercaderia;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Bodega getBodega()
/* 237:    */   {
/* 238:318 */     return this.bodega;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setBodega(Bodega bodega)
/* 242:    */   {
/* 243:322 */     this.bodega = bodega;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<Bodega> getListaBodega()
/* 247:    */   {
/* 248:326 */     if (this.listaBodega == null) {
/* 249:327 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, null);
/* 250:    */     }
/* 251:329 */     return this.listaBodega;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 255:    */   {
/* 256:333 */     this.listaBodega = listaBodega;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<SelectItem> getListaTipoReporte()
/* 260:    */   {
/* 261:337 */     if (this.listaTipoReporte == null)
/* 262:    */     {
/* 263:338 */       this.listaTipoReporte = new ArrayList();
/* 264:339 */       for (TipoReporte tipoReporte : TipoReporte.values())
/* 265:    */       {
/* 266:340 */         SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 267:341 */         this.listaTipoReporte.add(item);
/* 268:    */       }
/* 269:    */     }
/* 270:345 */     return this.listaTipoReporte;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public TipoReporte getTipoReporte()
/* 274:    */   {
/* 275:349 */     return this.tipoReporte;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 279:    */   {
/* 280:353 */     this.tipoReporte = tipoReporte;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 284:    */   {
/* 285:357 */     HashMap<String, String> filters = new HashMap();
/* 286:358 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 287:359 */     if (this.listaCategoriaProductos == null) {
/* 288:360 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 289:    */     }
/* 290:362 */     return this.listaCategoriaProductos;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void cargarListaSubcategoriaProducto()
/* 294:    */   {
/* 295:366 */     this.listaSubcategoriaProducto = new ArrayList();
/* 296:367 */     if (this.categoriaProducto != null)
/* 297:    */     {
/* 298:368 */       HashMap<String, String> filters = new HashMap();
/* 299:369 */       filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 300:370 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 301:    */     }
/* 302:373 */     this.subcategoriaProducto = null;
/* 303:374 */     actualizarSubcategoriaProducto();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void actualizarSubcategoriaProducto()
/* 307:    */   {
/* 308:378 */     this.producto = null;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void cargarProducto(Producto producto)
/* 312:    */   {
/* 313:382 */     this.producto = producto;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 317:    */   {
/* 318:386 */     return this.subcategoriaProducto;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 322:    */   {
/* 323:390 */     this.subcategoriaProducto = subcategoriaProducto;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public CategoriaProducto getCategoriaProducto()
/* 327:    */   {
/* 328:394 */     return this.categoriaProducto;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 332:    */   {
/* 333:398 */     this.categoriaProducto = categoriaProducto;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public Producto getProducto()
/* 337:    */   {
/* 338:402 */     return this.producto;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setProducto(Producto producto)
/* 342:    */   {
/* 343:406 */     this.producto = producto;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 347:    */   {
/* 348:410 */     return this.listaSubcategoriaProducto;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 352:    */   {
/* 353:414 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 354:    */   }
/* 355:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteRecepcionesProveedorBean
 * JD-Core Version:    0.7.0.1
 */