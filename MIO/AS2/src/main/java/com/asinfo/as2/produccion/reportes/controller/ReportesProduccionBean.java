/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.produccion.PlanProduccion;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  13:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanProduccion;
/*  14:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReportesProduccion;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  17:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.io.Serializable;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import javax.faces.model.SelectItem;
/*  29:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  30:    */ import net.sf.jasperreports.engine.JRException;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class ReportesProduccionBean
/*  36:    */   extends AbstractBaseReportBean
/*  37:    */   implements Serializable
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -6109947204471031028L;
/*  40:    */   @EJB
/*  41:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  42:    */   @EJB
/*  43:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  44:    */   @EJB
/*  45:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  46:    */   @EJB
/*  47:    */   private ServicioPlanProduccion servicioPlanProduccion;
/*  48:    */   @EJB
/*  49:    */   private ServicioReportesProduccion servicioReportesProduccion;
/*  50:    */   @EJB
/*  51:    */   private ServicioProducto servicioProducto;
/*  52:    */   
/*  53:    */   private static enum enumTipoReporte
/*  54:    */   {
/*  55: 57 */     ORDEN_FABRICACION_DIA,  MATERIAS_PRIMAS,  RENDIMIENTO_MATERIALES;
/*  56:    */     
/*  57:    */     private enumTipoReporte() {}
/*  58:    */   }
/*  59:    */   
/*  60: 60 */   private enumTipoReporte tipoReporte = enumTipoReporte.ORDEN_FABRICACION_DIA;
/*  61:    */   private List<SelectItem> listaTipoReporte;
/*  62:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  63:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  64: 65 */   private Date fecha = new Date();
/*  65: 66 */   private Date fechaHasta = new Date();
/*  66:    */   private Producto producto;
/*  67:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  68:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  69:    */   private PlanProduccion planProduccion;
/*  70:    */   private List<PlanProduccion> listaPlanProduccion;
/*  71: 74 */   private boolean indicadorProducido = true;
/*  72:    */   
/*  73:    */   protected JRDataSource getJRDataSource()
/*  74:    */   {
/*  75: 78 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  76: 79 */     JRDataSource ds = null;
/*  77: 80 */     String[] fields = null;
/*  78: 81 */     if (this.producto != null)
/*  79:    */     {
/*  80: 82 */       this.categoriaProductoSeleccionado = null;
/*  81: 83 */       this.subcategoriaProductoSeleccionado = null;
/*  82:    */     }
/*  83:    */     try
/*  84:    */     {
/*  85: 86 */       if (this.tipoReporte.equals(enumTipoReporte.ORDEN_FABRICACION_DIA))
/*  86:    */       {
/*  87: 87 */         listaDatosReporte = this.servicioReportesProduccion.reporteOrdenFabricacion(this.fecha, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado);
/*  88:    */         
/*  89: 89 */         fields = new String[] { "f_numeroOrdenFabricacion", "f_codigoProducto", "f_nombreProducto", "f_cantidadBach", "f_cantidadProducida", "f_cantidadPlanificada", "f_unidad", "f_cantidadFabricada", "f_descripcion", "f_categoriaProducto", "f_subcategoriaProducto" };
/*  90:    */       }
/*  91: 92 */       if (this.tipoReporte.equals(enumTipoReporte.MATERIAS_PRIMAS))
/*  92:    */       {
/*  93: 93 */         listaDatosReporte = this.servicioReportesProduccion.listaMateriaPrima(this.planProduccion, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado);
/*  94:    */         
/*  95: 95 */         fields = new String[] { "f_cantidadProducir", "f_codigoProducto", "f_nombreProducto", "f_dia", "f_unidad" };
/*  96:    */       }
/*  97: 97 */       if (this.tipoReporte.equals(enumTipoReporte.RENDIMIENTO_MATERIALES))
/*  98:    */       {
/*  99: 98 */         listaDatosReporte = this.servicioReportesProduccion.listaRendimientoMateriales(this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, this.producto, this.fecha, this.fechaHasta, 
/* 100: 99 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 101:100 */         fields = new String[] { "f_codigoProductoTerminado", "f_productoTerminado", "f_fechaOrdenFabricacion", "f_codigoMateriaPrima", "f_materiaPrima", "f_cantidadUtilizadaReal", "f_valorProducido", "f_cantidadBom", "f_cantidadRequerida" };
/* 102:    */       }
/* 103:103 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:106 */       e.printStackTrace();
/* 108:    */     }
/* 109:108 */     return ds;
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected String getCompileFileName()
/* 113:    */   {
/* 114:113 */     if (this.tipoReporte.equals(enumTipoReporte.ORDEN_FABRICACION_DIA)) {
/* 115:114 */       return "reporteOrdenFabricacionDia";
/* 116:    */     }
/* 117:116 */     if (this.tipoReporte.equals(enumTipoReporte.MATERIAS_PRIMAS)) {
/* 118:117 */       return "reporteMateriasPrimas";
/* 119:    */     }
/* 120:119 */     if (this.tipoReporte.equals(enumTipoReporte.RENDIMIENTO_MATERIALES)) {
/* 121:120 */       return "reporteRendimientoMateriales";
/* 122:    */     }
/* 123:122 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected Map<String, Object> getReportParameters()
/* 127:    */   {
/* 128:127 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 129:128 */     reportParameters.put("p_fecha", this.fecha);
/* 130:129 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 131:130 */     reportParameters.put("p_subcategoriaProductoSeleccionado", this.subcategoriaProductoSeleccionado != null ? this.subcategoriaProductoSeleccionado.getNombre() : "Todos");
/* 132:131 */     reportParameters.put("p_categoriaProductoSeleccionado", this.categoriaProductoSeleccionado != null ? this.categoriaProductoSeleccionado.getNombre() : "Todos");
/* 133:132 */     reportParameters.put("p_planificacion", this.planProduccion != null ? "Semana: " + this.planProduccion.getSemanaAnio() + " Fecha Inicio: " + this.planProduccion
/* 134:133 */       .getFechaInicio() + " Fecha Fin:" + this.planProduccion.getFechaFin() : "");
/* 135:134 */     reportParameters.put("p_indicadorProducido", Boolean.valueOf(this.indicadorProducido));
/* 136:135 */     return reportParameters;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String execute()
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:141 */       super.prepareReport();
/* 144:    */     }
/* 145:    */     catch (JRException e)
/* 146:    */     {
/* 147:143 */       LOG.info("Error JRException");
/* 148:144 */       e.printStackTrace();
/* 149:145 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 150:    */     }
/* 151:    */     catch (IOException e)
/* 152:    */     {
/* 153:147 */       LOG.info("Error IOException");
/* 154:148 */       e.printStackTrace();
/* 155:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 156:    */     }
/* 157:151 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void cargarListaSubcategoriaProducto()
/* 161:    */   {
/* 162:155 */     HashMap<String, String> filters = new HashMap();
/* 163:156 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 164:157 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 168:    */   {
/* 169:161 */     HashMap<String, String> filters = new HashMap();
/* 170:162 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 171:163 */     if (this.listaCategoriaProductos == null) {
/* 172:164 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 173:    */     }
/* 174:166 */     return this.listaCategoriaProductos;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void cargarProducto(Producto producto)
/* 178:    */   {
/* 179:170 */     this.producto = producto;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 183:    */   {
/* 184:174 */     return this.categoriaProductoSeleccionado;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 188:    */   {
/* 189:178 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 193:    */   {
/* 194:182 */     return this.subcategoriaProductoSeleccionado;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 198:    */   {
/* 199:186 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 203:    */   {
/* 204:190 */     return this.listaSubcategoriaProductos;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 208:    */   {
/* 209:194 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 213:    */   {
/* 214:198 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Date getFecha()
/* 218:    */   {
/* 219:202 */     return this.fecha;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setFecha(Date fecha)
/* 223:    */   {
/* 224:206 */     this.fecha = fecha;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public enumTipoReporte getTipoReporte()
/* 228:    */   {
/* 229:210 */     return this.tipoReporte;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 233:    */   {
/* 234:214 */     this.tipoReporte = tipoReporte;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List<SelectItem> getListaTipoReporte()
/* 238:    */   {
/* 239:218 */     if (this.listaTipoReporte == null)
/* 240:    */     {
/* 241:219 */       this.listaTipoReporte = new ArrayList();
/* 242:220 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 243:221 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 244:    */       }
/* 245:    */     }
/* 246:224 */     return this.listaTipoReporte;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 250:    */   {
/* 251:228 */     this.listaTipoReporte = listaTipoReporte;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public PlanProduccion getPlanProduccion()
/* 255:    */   {
/* 256:232 */     return this.planProduccion;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setPlanProduccion(PlanProduccion planProduccion)
/* 260:    */   {
/* 261:236 */     this.planProduccion = planProduccion;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<PlanProduccion> getListaPlanProduccion()
/* 265:    */   {
/* 266:240 */     HashMap<String, String> filters = new HashMap();
/* 267:241 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 268:242 */     if (this.listaPlanProduccion == null) {
/* 269:243 */       this.listaPlanProduccion = this.servicioPlanProduccion.obtenerListaCombo("idPlanProduccion", true, filters);
/* 270:    */     }
/* 271:245 */     return this.listaPlanProduccion;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setListaPlanProduccion(List<PlanProduccion> listaPlanProduccion)
/* 275:    */   {
/* 276:249 */     this.listaPlanProduccion = listaPlanProduccion;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public Date getFechaHasta()
/* 280:    */   {
/* 281:253 */     return this.fechaHasta;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setFechaHasta(Date fechaHasta)
/* 285:    */   {
/* 286:257 */     this.fechaHasta = fechaHasta;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Producto getProducto()
/* 290:    */   {
/* 291:261 */     return this.producto;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setProducto(Producto producto)
/* 295:    */   {
/* 296:265 */     this.producto = producto;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public boolean isIndicadorProducido()
/* 300:    */   {
/* 301:269 */     return this.indicadorProducido;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setIndicadorProducido(boolean indicadorProducido)
/* 305:    */   {
/* 306:273 */     this.indicadorProducido = indicadorProducido;
/* 307:    */   }
/* 308:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReportesProduccionBean
 * JD-Core Version:    0.7.0.1
 */