/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.controller.EmpresaBean;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.LinkedHashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ManagedProperty;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class ReportePedidoSaldoPorRecibirBean
/*  38:    */   extends AbstractBaseReportBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = -5391378159126915725L;
/*  41:    */   private Empresa empresa;
/*  42:    */   private Date fechaDesde;
/*  43:    */   private Date fechaHasta;
/*  44: 62 */   private boolean soloPendientes = true;
/*  45:    */   private List<Empresa> listaClientes;
/*  46: 64 */   private final String COMPILE_FILE_NAME1 = "pedidoSaldoPorRecepcionar";
/*  47: 65 */   private final String COMPILE_FILE_NAME2 = "reporteFacturaVsRecepcion";
/*  48:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  49:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  50:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  51:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  52: 72 */   int tipoReporte = 0;
/*  53:    */   @EJB
/*  54:    */   private ServicioEmpresa servicioEmpresa;
/*  55:    */   @EJB
/*  56:    */   private ServicioReporteCompra servicioReporteCompra;
/*  57:    */   @EJB
/*  58:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  59:    */   @EJB
/*  60:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  61:    */   @ManagedProperty("#{empresaBean}")
/*  62:    */   private EmpresaBean empresaBean;
/*  63:    */   
/*  64:    */   protected String getCompileFileName()
/*  65:    */   {
/*  66: 95 */     if (this.tipoReporte == 1) {
/*  67: 96 */       return "pedidoSaldoPorRecepcionar";
/*  68:    */     }
/*  69: 98 */     if (this.tipoReporte == 2) {
/*  70: 99 */       return "reporteFacturaVsRecepcion";
/*  71:    */     }
/*  72:101 */     return "";
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected JRDataSource getJRDataSource()
/*  76:    */   {
/*  77:114 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  78:115 */     JRDataSource ds = null;
/*  79:116 */     LinkedHashMap<String, Object[]> hmListaDatosReporte = new LinkedHashMap();
/*  80:    */     try
/*  81:    */     {
/*  82:118 */       int idOrganizacion = AppUtil.getOrganizacion().getId();
/*  83:119 */       int idEmpresa = 0;
/*  84:120 */       if (this.empresa == null)
/*  85:    */       {
/*  86:121 */         this.empresa = new Empresa();
/*  87:122 */         idEmpresa = 0;
/*  88:    */       }
/*  89:    */       else
/*  90:    */       {
/*  91:124 */         idEmpresa = this.empresa.getIdEmpresa();
/*  92:    */       }
/*  93:126 */       String[] fields = new String[0];
/*  94:127 */       if (this.tipoReporte == 1)
/*  95:    */       {
/*  96:128 */         listaDatosReporte = this.servicioReporteCompra.getReportePedidoSaldoPorRecibir(this.fechaDesde, this.fechaHasta, idEmpresa, this.soloPendientes, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, idOrganizacion);
/*  97:    */         
/*  98:130 */         fields = new String[] { "numeroPedido", "nombreCliente", "nombreProducto", "volumenProducto", "pesoProducto", "totalPedido", "totalFacturado", "totalDespachado", "fechaPedido" };
/*  99:    */       }
/* 100:133 */       if (this.tipoReporte == 2)
/* 101:    */       {
/* 102:134 */         listaDatosReporte = this.servicioReporteCompra.getReporteFacturaRecepcion(this.fechaDesde, this.fechaHasta, idEmpresa, this.soloPendientes, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, idOrganizacion);
/* 103:135 */         listaDatosReporte.addAll(this.servicioReporteCompra.getReporteRecepcionFactura(this.fechaDesde, this.fechaHasta, idEmpresa, this.soloPendientes, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, idOrganizacion));
/* 104:137 */         if (!this.soloPendientes)
/* 105:    */         {
/* 106:138 */           for (Object[] objs : listaDatosReporte)
/* 107:    */           {
/* 108:139 */             String clave = (String)objs[0] + "~" + (String)objs[2] + "~" + (String)objs[6];
/* 109:    */             
/* 110:141 */             hmListaDatosReporte.put(clave, objs);
/* 111:    */           }
/* 112:144 */           listaDatosReporte.clear();
/* 113:145 */           listaDatosReporte.addAll(hmListaDatosReporte.values());
/* 114:    */         }
/* 115:148 */         fields = new String[] { "numeroFactura", "fechaFactura", "numeroRecepcion", "fechaRecepcion", "cantidadFacturada", "cantidadRecibida", "codigoProducto", "nombreProducto", "nombreFiscal" };
/* 116:    */       }
/* 117:152 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 118:    */     }
/* 119:    */     catch (Exception e)
/* 120:    */     {
/* 121:154 */       LOG.info("Error " + e);
/* 122:155 */       e.printStackTrace();
/* 123:    */     }
/* 124:157 */     return ds;
/* 125:    */   }
/* 126:    */   
/* 127:    */   @PostConstruct
/* 128:    */   public void init()
/* 129:    */   {
/* 130:162 */     Calendar calfechaDesde = Calendar.getInstance();
/* 131:163 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 132:164 */     this.fechaDesde = calfechaDesde.getTime();
/* 133:165 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected Map<String, Object> getReportParameters()
/* 137:    */   {
/* 138:175 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 139:176 */     if ((this.empresa != null) && (this.empresa.getIdEmpresa() == -1)) {
/* 140:177 */       getEmpresa().setNombreFiscal("");
/* 141:    */     }
/* 142:180 */     if (this.tipoReporte == 1) {
/* 143:181 */       reportParameters.put("ReportTitle", "Pedido vs Recepcion vs Factura");
/* 144:    */     }
/* 145:183 */     if (this.tipoReporte == 2) {
/* 146:184 */       reportParameters.put("ReportTitle", "Recepcion vs Factura");
/* 147:    */     }
/* 148:187 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 149:188 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 150:189 */     reportParameters.put("filtroProveedor", this.empresa.getNombreFiscal() == null ? "TODOS" : this.empresa == null ? "TODOS" : this.empresa.getNombreFiscal());
/* 151:190 */     reportParameters.put("filtroSoloPendientes", Boolean.valueOf(this.soloPendientes));
/* 152:191 */     reportParameters.put("filtroCategoriaProducto", this.categoriaProductoSeleccionado == null ? "TODOS" : this.categoriaProductoSeleccionado.getNombre());
/* 153:192 */     reportParameters.put("filtroSubcategoriaProducto", this.subcategoriaProductoSeleccionado == null ? "TODOS" : this.subcategoriaProductoSeleccionado.getNombre());
/* 154:    */     
/* 155:194 */     return reportParameters;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String execute()
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:204 */       super.prepareReport();
/* 163:    */     }
/* 164:    */     catch (JRException e)
/* 165:    */     {
/* 166:206 */       LOG.info("Error JRException");
/* 167:207 */       e.printStackTrace();
/* 168:208 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 169:    */     }
/* 170:    */     catch (IOException e)
/* 171:    */     {
/* 172:210 */       LOG.info("Error IOException");
/* 173:211 */       e.printStackTrace();
/* 174:212 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 175:    */     }
/* 176:214 */     return "";
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Empresa getEmpresa()
/* 180:    */   {
/* 181:223 */     if (this.empresa == null) {
/* 182:224 */       this.empresa = new Empresa();
/* 183:    */     }
/* 184:226 */     return this.empresa;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setEmpresa(Empresa empresa)
/* 188:    */   {
/* 189:236 */     this.empresa = empresa;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Date getFechaDesde()
/* 193:    */   {
/* 194:245 */     return this.fechaDesde;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setFechaDesde(Date fechaDesde)
/* 198:    */   {
/* 199:255 */     this.fechaDesde = fechaDesde;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Date getFechaHasta()
/* 203:    */   {
/* 204:264 */     return this.fechaHasta;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setFechaHasta(Date fechaHasta)
/* 208:    */   {
/* 209:274 */     this.fechaHasta = fechaHasta;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Empresa> getListaClientes()
/* 213:    */   {
/* 214:283 */     if (this.listaClientes == null) {
/* 215:284 */       this.listaClientes = this.servicioEmpresa.obtenerClientes();
/* 216:    */     }
/* 217:286 */     return this.listaClientes;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 221:    */   {
/* 222:290 */     HashMap<String, String> filters = new HashMap();
/* 223:291 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 224:292 */     if (this.listaCategoriaProductos == null) {
/* 225:293 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 226:    */     }
/* 227:295 */     return this.listaCategoriaProductos;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void cargarListaSubcategoriaProducto()
/* 231:    */   {
/* 232:299 */     HashMap<String, String> filters = new HashMap();
/* 233:300 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 234:301 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 238:    */   {
/* 239:305 */     return this.listaSubcategoriaProductos;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 243:    */   {
/* 244:310 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 248:    */   {
/* 249:315 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setListaClientes(List<Empresa> listaClientes)
/* 253:    */   {
/* 254:325 */     this.listaClientes = listaClientes;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public EmpresaBean getEmpresaBean()
/* 258:    */   {
/* 259:334 */     return this.empresaBean;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setEmpresaBean(EmpresaBean empresaBean)
/* 263:    */   {
/* 264:344 */     this.empresaBean = empresaBean;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isSoloPendientes()
/* 268:    */   {
/* 269:348 */     return this.soloPendientes;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setSoloPendientes(boolean soloPendientes)
/* 273:    */   {
/* 274:352 */     this.soloPendientes = soloPendientes;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 278:    */   {
/* 279:356 */     return this.categoriaProductoSeleccionado;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 283:    */   {
/* 284:361 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 288:    */   {
/* 289:365 */     return this.subcategoriaProductoSeleccionado;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 293:    */   {
/* 294:370 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public int getTipoReporte()
/* 298:    */   {
/* 299:374 */     return this.tipoReporte;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setTipoReporte(int tipoReporte)
/* 303:    */   {
/* 304:378 */     this.tipoReporte = tipoReporte;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public List<SelectItem> getListaTipoReporte()
/* 308:    */   {
/* 309:382 */     List<SelectItem> lista = new ArrayList();
/* 310:383 */     lista.add(new SelectItem(Integer.valueOf(1), "Reporte Pedido vs Recepcion vs Factura"));
/* 311:384 */     lista.add(new SelectItem(Integer.valueOf(2), "Reporte Recepcion vs Factura"));
/* 312:385 */     return lista;
/* 313:    */   }
/* 314:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReportePedidoSaldoPorRecibirBean
 * JD-Core Version:    0.7.0.1
 */