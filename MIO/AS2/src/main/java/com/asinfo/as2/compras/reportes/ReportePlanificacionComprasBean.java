/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Atributo;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*  18:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  22:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  23:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  24:    */ import java.io.IOException;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  34:    */ import net.sf.jasperreports.engine.JRException;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.event.SelectEvent;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class ReportePlanificacionComprasBean
/*  41:    */   extends AbstractBaseReportBean
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = 3615647209557356681L;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioReporteCompra servicioReporteCompra;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioCategoriaProducto servicioCategoriaProducto;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  50:    */   @EJB
/*  51:    */   private transient ServicioProducto servicioProducto;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioAtributo servicioAtributo;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioValorAtributo servicioValorAtributo;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioBodega servicioBodega;
/*  58:    */   private CategoriaProducto categoriaProducto;
/*  59:    */   private SubcategoriaProducto subcategoriaProducto;
/*  60: 85 */   private Date fechaCorte = FuncionesUtiles.setAtributoFecha(new Date());
/*  61:    */   private Producto producto;
/*  62:    */   private Bodega bodega;
/*  63:    */   private Atributo atributo;
/*  64:    */   private ValorAtributo valorAtributoSeleccionado;
/*  65:    */   private String textoValorAtributo;
/*  66: 91 */   private int meses = ParametrosSistema.getMesesSinConsumo(AppUtil.getOrganizacion().getId()).intValue();
/*  67:    */   private List<Bodega> listaBodega;
/*  68:    */   private List<Atributo> listaAtributo;
/*  69:    */   
/*  70:    */   public void actualizarCategoriaProducto(SelectEvent event)
/*  71:    */   {
/*  72:105 */     setCategoriaProducto((CategoriaProducto)event.getObject());
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected JRDataSource getJRDataSource()
/*  76:    */   {
/*  77:115 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  78:116 */     JRDataSource ds = null;
/*  79:    */     try
/*  80:    */     {
/*  81:119 */       listaDatosReporte = this.servicioReporteCompra.getReportePlanificacionCompras(AppUtil.getOrganizacion().getId(), this.fechaCorte, this.categoriaProducto, this.subcategoriaProducto, this.producto, this.bodega, this.atributo, this.valorAtributoSeleccionado, this.textoValorAtributo, 
/*  82:120 */         Integer.valueOf(this.meses));
/*  83:    */       
/*  84:122 */       String[] fields = { "f_idProducto", "f_codigoProducto", "f_nombreProducto", "f_codigoSubCategoriaProducto", "f_nombreSubCategoriaProducto", "f_promedioStockMinimoBodegas", "f_promedioConsumoMensual", "f_stockKardex", "f_stockEnTransitoLocal", "f_stockEnTransitoImportacion", "f_cantidadAComprar", "f_costoUnitario", "f_valorTotalSinIVA", "f_ingresos", "f_egresos", "f_meses", "f_periodoEstadistico" };
/*  85:    */       
/*  86:    */ 
/*  87:    */ 
/*  88:126 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:128 */       LOG.info("Error " + e);
/*  93:129 */       e.printStackTrace();
/*  94:    */     }
/*  95:131 */     return ds;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected String getCompileFileName()
/*  99:    */   {
/* 100:141 */     return "reportePlanificacionCompras";
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected Map<String, Object> getReportParameters()
/* 104:    */   {
/* 105:152 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 106:153 */     reportParameters.put("ReportTitle", "Reporte Planificacion de compras");
/* 107:154 */     reportParameters.put("FechaCorte", this.fechaCorte);
/* 108:155 */     reportParameters.put("meses", Integer.valueOf(this.meses));
/* 109:156 */     reportParameters.put("categoriaProducto", this.categoriaProducto == null ? "TODAS" : this.categoriaProducto.getNombre());
/* 110:157 */     reportParameters.put("subcategoriaProducto", this.subcategoriaProducto == null ? "TODAS" : this.subcategoriaProducto.getNombre());
/* 111:158 */     reportParameters.put("producto", this.producto == null ? "TODOS" : this.producto.getNombre());
/* 112:159 */     reportParameters.put("bodega", this.bodega == null ? "TODAS" : this.bodega.getNombre());
/* 113:160 */     reportParameters.put("atributo", this.atributo == null ? "TODOS" : this.atributo.getNombre());
/* 114:161 */     reportParameters.put("valorAtributo", 
/* 115:    */     
/* 116:    */ 
/* 117:164 */       !this.textoValorAtributo.isEmpty() ? this.textoValorAtributo : this.valorAtributoSeleccionado != null ? this.valorAtributoSeleccionado.getNombre() : (this.valorAtributoSeleccionado == null) && (this.textoValorAtributo == null) ? "TODOS" : "TODOS");
/* 118:    */     
/* 119:166 */     return reportParameters;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String execute()
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:176 */       super.prepareReport();
/* 127:    */     }
/* 128:    */     catch (JRException e)
/* 129:    */     {
/* 130:178 */       LOG.info("Error JRException");
/* 131:179 */       e.printStackTrace();
/* 132:180 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 133:    */     }
/* 134:    */     catch (IOException e)
/* 135:    */     {
/* 136:182 */       LOG.info("Error IOException");
/* 137:183 */       e.printStackTrace();
/* 138:184 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 139:    */     }
/* 140:187 */     return null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<ValorAtributo> autocompletarValorAtributo(String consulta)
/* 144:    */   {
/* 145:191 */     if (getAtributo() != null) {
/* 146:192 */       return this.servicioValorAtributo.autocompletarValorAtributo(consulta, getAtributo());
/* 147:    */     }
/* 148:194 */     List<ValorAtributo> listaVacia = new ArrayList();
/* 149:195 */     return listaVacia;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<Producto> autocompletarProducto(String consulta)
/* 153:    */   {
/* 154:200 */     List<Producto> lp = new ArrayList();
/* 155:201 */     Map<String, String> filtros = new HashMap();
/* 156:202 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 157:203 */     filtros.put("nombre", "%" + consulta.trim());
/* 158:204 */     if (this.categoriaProducto != null) {
/* 159:205 */       filtros.put("subcategoriaProducto.categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 160:    */     }
/* 161:207 */     if (this.subcategoriaProducto != null) {
/* 162:208 */       filtros.put("subcategoriaProducto.idSubcategoriaProducto", "" + this.subcategoriaProducto.getId());
/* 163:    */     }
/* 164:    */     try
/* 165:    */     {
/* 166:211 */       lp = this.servicioProducto.obtenerProductos("nombre", false, filtros);
/* 167:    */     }
/* 168:    */     catch (ExcepcionAS2Inventario e)
/* 169:    */     {
/* 170:213 */       e.printStackTrace();
/* 171:    */     }
/* 172:215 */     return lp;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 176:    */   {
/* 177:220 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 178:    */     
/* 179:222 */     HashMap<String, String> filters = new HashMap();
/* 180:224 */     if (this.categoriaProducto != null) {
/* 181:225 */       filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 182:    */     }
/* 183:228 */     filters.put("nombre", "%" + consulta.trim());
/* 184:    */     
/* 185:230 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 186:    */     
/* 187:232 */     return lista;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 191:    */   {
/* 192:236 */     List<CategoriaProducto> lista = new ArrayList();
/* 193:    */     
/* 194:238 */     HashMap<String, String> filters = new HashMap();
/* 195:239 */     filters.put("nombre", "%" + consulta.trim());
/* 196:    */     
/* 197:241 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 198:    */     
/* 199:243 */     return lista;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public CategoriaProducto getCategoriaProducto()
/* 203:    */   {
/* 204:247 */     return this.categoriaProducto;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 208:    */   {
/* 209:251 */     this.categoriaProducto = categoriaProducto;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 213:    */   {
/* 214:255 */     return this.subcategoriaProducto;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 218:    */   {
/* 219:259 */     this.subcategoriaProducto = subcategoriaProducto;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public List<Atributo> getListaAtributo()
/* 223:    */   {
/* 224:263 */     if (this.listaAtributo == null)
/* 225:    */     {
/* 226:264 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 227:265 */       filters.put("indicadorProducto", "true");
/* 228:266 */       this.listaAtributo = this.servicioAtributo.obtenerListaCombo("nombre", true, filters);
/* 229:    */     }
/* 230:268 */     return this.listaAtributo;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Atributo getAtributo()
/* 234:    */   {
/* 235:272 */     return this.atributo;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setAtributo(Atributo atributo)
/* 239:    */   {
/* 240:276 */     this.atributo = atributo;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Date getFechaCorte()
/* 244:    */   {
/* 245:280 */     return this.fechaCorte;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setFechaCorte(Date fechaCorte)
/* 249:    */   {
/* 250:284 */     this.fechaCorte = fechaCorte;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Producto getProducto()
/* 254:    */   {
/* 255:288 */     return this.producto;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setProducto(Producto producto)
/* 259:    */   {
/* 260:292 */     this.producto = producto;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public ValorAtributo getValorAtributoSeleccionado()
/* 264:    */   {
/* 265:296 */     return this.valorAtributoSeleccionado;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setValorAtributoSeleccionado(ValorAtributo valorAtributoSeleccionado)
/* 269:    */   {
/* 270:300 */     this.valorAtributoSeleccionado = valorAtributoSeleccionado;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public Bodega getBodega()
/* 274:    */   {
/* 275:304 */     return this.bodega;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setBodega(Bodega bodega)
/* 279:    */   {
/* 280:308 */     this.bodega = bodega;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public List<Bodega> getListaBodega()
/* 284:    */   {
/* 285:312 */     if (this.listaBodega == null) {
/* 286:313 */       this.listaBodega = this.servicioBodega.obtenerBodegaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 287:    */     }
/* 288:315 */     return this.listaBodega;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public String getTextoValorAtributo()
/* 292:    */   {
/* 293:319 */     return this.textoValorAtributo;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setTextoValorAtributo(String textoValorAtributo)
/* 297:    */   {
/* 298:323 */     this.textoValorAtributo = textoValorAtributo;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public int getMeses()
/* 302:    */   {
/* 303:327 */     return this.meses;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setMeses(int meses)
/* 307:    */   {
/* 308:331 */     this.meses = meses;
/* 309:    */   }
/* 310:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReportePlanificacionComprasBean
 * JD-Core Version:    0.7.0.1
 */