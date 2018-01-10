/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  18:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Calendar;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  34:    */ import net.sf.jasperreports.engine.JRException;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ public class ReporteTransferenciaInventarioBean
/*  39:    */   extends AbstractBaseReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1L;
/*  42:    */   @EJB
/*  43:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  44:    */   @EJB
/*  45:    */   private ServicioDocumento servicioDocumento;
/*  46:    */   @EJB
/*  47:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  48:    */   @EJB
/*  49:    */   private ServicioBodega servicioBodega;
/*  50:    */   @EJB
/*  51:    */   private ServicioDimensionContable servicioProyecto;
/*  52:    */   @EJB
/*  53:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  54:    */   private Date fechaDesde;
/*  55:    */   private Date fechaHasta;
/*  56:    */   private Bodega bodegaOrigen;
/*  57:    */   private Bodega bodegaDestino;
/*  58:    */   private Documento documento;
/*  59:    */   private SubcategoriaProducto subcategoriaProducto;
/*  60:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  61:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  62:    */   private Estado estado;
/*  63:    */   private DimensionContable proyecto;
/*  64:    */   private List<Documento> listaDocumento;
/*  65:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  66:    */   private List<Bodega> listaBodega;
/*  67:    */   private List<Estado> listaEstado;
/*  68:    */   private List<DimensionContable> listaProyecto;
/*  69:    */   
/*  70:    */   protected JRDataSource getJRDataSource()
/*  71:    */   {
/*  72: 94 */     List listaDatosReporte = new ArrayList();
/*  73: 95 */     JRDataSource ds = null;
/*  74:    */     try
/*  75:    */     {
/*  76: 98 */       listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteTransferenciaInventario(AppUtil.getOrganizacion().getId(), this.documento, this.fechaDesde, this.fechaHasta, this.bodegaOrigen, this.bodegaDestino, this.subcategoriaProducto, this.estado, this.proyecto, this.categoriaProductoSeleccionado);
/*  77:    */       
/*  78:100 */       String[] fields = { "f_numero", "f_fecha", "f_descripcion", "f_estado", "f_codigoProducto", "f_nombreProducto", "f_nombreComercialProducto", "f_unidad", "f_cantidad", "f_costo", "f_documento", "f_lote", "f_bodegaOrigen", "f_bodegaDestino", "f_estado", "f_nombreProyecto", "f_spcodigo", "f_spnombre", "f_cpcodigo", "f_cpnombre" };
/*  79:    */       
/*  80:    */ 
/*  81:    */ 
/*  82:104 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:106 */       e.printStackTrace();
/*  87:    */     }
/*  88:108 */     return ds;
/*  89:    */   }
/*  90:    */   
/*  91:    */   @PostConstruct
/*  92:    */   public void init()
/*  93:    */   {
/*  94:113 */     Calendar calfechaDesde = Calendar.getInstance();
/*  95:114 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  96:115 */     this.fechaDesde = calfechaDesde.getTime();
/*  97:116 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected String getCompileFileName()
/* 101:    */   {
/* 102:121 */     return "reporteTransferenciaBodegaDetallado";
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected Map<String, Object> getReportParameters()
/* 106:    */   {
/* 107:127 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 108:128 */     reportParameters.put("ReportTitle", "Transferencias Entre Bodegas");
/* 109:129 */     reportParameters.put("fechaDesde", this.fechaDesde);
/* 110:130 */     reportParameters.put("fechaHasta", this.fechaHasta);
/* 111:131 */     reportParameters.put("bodegaOrigen", this.bodegaOrigen == null ? "" : this.bodegaOrigen.getNombre());
/* 112:132 */     reportParameters.put("bodegaDestino", this.bodegaDestino == null ? "" : this.bodegaDestino.getNombre());
/* 113:133 */     reportParameters.put("documento", this.documento == null ? "" : this.documento.getNombre());
/* 114:134 */     reportParameters.put("subcategoriaProducto", this.subcategoriaProducto == null ? "" : this.subcategoriaProducto.getNombre());
/* 115:135 */     reportParameters.put("p_estado", this.estado != null ? this.estado.getNombre() : "Todos");
/* 116:136 */     return reportParameters;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String execute()
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:145 */       validaDatos();
/* 124:146 */       super.prepareReport();
/* 125:    */     }
/* 126:    */     catch (JRException e)
/* 127:    */     {
/* 128:149 */       LOG.info("Error JRException");
/* 129:150 */       e.printStackTrace();
/* 130:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 131:    */     }
/* 132:    */     catch (IOException e)
/* 133:    */     {
/* 134:153 */       LOG.info("Error IOException");
/* 135:154 */       e.printStackTrace();
/* 136:155 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 137:    */     }
/* 138:158 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void validaDatos()
/* 142:    */   {
/* 143:162 */     if (this.fechaDesde == null) {
/* 144:163 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 145:    */     }
/* 146:165 */     if (this.fechaHasta == null) {
/* 147:166 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Date getFechaDesde()
/* 152:    */   {
/* 153:176 */     return this.fechaDesde;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setFechaDesde(Date fechaDesde)
/* 157:    */   {
/* 158:186 */     this.fechaDesde = fechaDesde;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Date getFechaHasta()
/* 162:    */   {
/* 163:195 */     return this.fechaHasta;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setFechaHasta(Date fechaHasta)
/* 167:    */   {
/* 168:205 */     this.fechaHasta = fechaHasta;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Documento getDocumento()
/* 172:    */   {
/* 173:214 */     return this.documento;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setDocumento(Documento documento)
/* 177:    */   {
/* 178:224 */     this.documento = documento;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 182:    */   {
/* 183:233 */     if (this.subcategoriaProducto == null) {
/* 184:234 */       this.subcategoriaProducto = new SubcategoriaProducto();
/* 185:    */     }
/* 186:236 */     return this.subcategoriaProducto;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 190:    */   {
/* 191:246 */     this.subcategoriaProducto = subcategoriaProducto;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<Documento> getListaDocumento()
/* 195:    */   {
/* 196:255 */     if (this.listaDocumento == null) {
/* 197:    */       try
/* 198:    */       {
/* 199:257 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.TRANSFERENCIA_BODEGA);
/* 200:    */       }
/* 201:    */       catch (ExcepcionAS2 e)
/* 202:    */       {
/* 203:259 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 204:    */       }
/* 205:    */     }
/* 206:262 */     return this.listaDocumento;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 210:    */   {
/* 211:272 */     this.listaDocumento = listaDocumento;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 215:    */   {
/* 216:281 */     if (this.listaSubcategoriaProducto == null) {
/* 217:282 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 218:    */     }
/* 219:284 */     return this.listaSubcategoriaProducto;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 223:    */   {
/* 224:294 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public List<Bodega> getListaBodega()
/* 228:    */   {
/* 229:298 */     if (this.listaBodega == null) {
/* 230:299 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, null);
/* 231:    */     }
/* 232:301 */     return this.listaBodega;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 236:    */   {
/* 237:305 */     this.listaBodega = listaBodega;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public ServicioBodega getServicioBodega()
/* 241:    */   {
/* 242:309 */     return this.servicioBodega;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setServicioBodega(ServicioBodega servicioBodega)
/* 246:    */   {
/* 247:313 */     this.servicioBodega = servicioBodega;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Bodega getBodegaOrigen()
/* 251:    */   {
/* 252:317 */     return this.bodegaOrigen;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setBodegaOrigen(Bodega bodegaOrigen)
/* 256:    */   {
/* 257:321 */     this.bodegaOrigen = bodegaOrigen;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Bodega getBodegaDestino()
/* 261:    */   {
/* 262:325 */     return this.bodegaDestino;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setBodegaDestino(Bodega bodegaDestino)
/* 266:    */   {
/* 267:329 */     this.bodegaDestino = bodegaDestino;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Estado getEstado()
/* 271:    */   {
/* 272:333 */     return this.estado;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setEstado(Estado estado)
/* 276:    */   {
/* 277:337 */     this.estado = estado;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public List<Estado> getListaEstado()
/* 281:    */   {
/* 282:341 */     if (this.listaEstado == null)
/* 283:    */     {
/* 284:342 */       this.listaEstado = new ArrayList();
/* 285:    */       
/* 286:344 */       this.listaEstado.add(Estado.ELABORADO);
/* 287:345 */       this.listaEstado.add(Estado.CONTABILIZADO);
/* 288:346 */       this.listaEstado.add(Estado.PROCESADO);
/* 289:    */     }
/* 290:349 */     return this.listaEstado;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setListaEstado(List<Estado> listaEstado)
/* 294:    */   {
/* 295:353 */     this.listaEstado = listaEstado;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public DimensionContable getProyecto()
/* 299:    */   {
/* 300:357 */     return this.proyecto;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setProyecto(DimensionContable proyecto)
/* 304:    */   {
/* 305:361 */     this.proyecto = proyecto;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public List<DimensionContable> getListaProyecto()
/* 309:    */   {
/* 310:365 */     if (this.listaProyecto == null) {
/* 311:366 */       this.listaProyecto = this.servicioProyecto.obtenerListaCombo("codigo", true, null);
/* 312:    */     }
/* 313:368 */     return this.listaProyecto;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setListaProyecto(List<DimensionContable> listaProyecto)
/* 317:    */   {
/* 318:372 */     this.listaProyecto = listaProyecto;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public ServicioDimensionContable getServicioProyecto()
/* 322:    */   {
/* 323:376 */     return this.servicioProyecto;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setServicioProyecto(ServicioDimensionContable servicioProyecto)
/* 327:    */   {
/* 328:380 */     this.servicioProyecto = servicioProyecto;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 332:    */   {
/* 333:387 */     if (this.categoriaProductoSeleccionado == null) {
/* 334:388 */       this.categoriaProductoSeleccionado = new CategoriaProducto();
/* 335:    */     }
/* 336:390 */     return this.categoriaProductoSeleccionado;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 340:    */   {
/* 341:398 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 345:    */   {
/* 346:405 */     HashMap<String, String> filters = new HashMap();
/* 347:406 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 348:407 */     if (this.listaCategoriaProductos == null) {
/* 349:408 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 350:    */     }
/* 351:410 */     return this.listaCategoriaProductos;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 355:    */   {
/* 356:418 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 357:    */   }
/* 358:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteTransferenciaInventarioBean
 * JD-Core Version:    0.7.0.1
 */