/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.controller.EmpresaBean;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ManagedProperty;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.faces.model.SelectItem;
/*  30:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  31:    */ import net.sf.jasperreports.engine.JRException;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ReportePedidoSaldoPorDespacharBean
/*  37:    */   extends AbstractBaseReportBean
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -5502052499882678164L;
/*  40:    */   private Empresa empresa;
/*  41:    */   private Date fechaDesde;
/*  42:    */   private Date fechaHasta;
/*  43:    */   private List<Empresa> listaClientes;
/*  44: 65 */   private final String COMPILE_FILE_NAME1 = "pedidoSaldoPorDespachar";
/*  45: 66 */   private final String COMPILE_FILE_NAME2 = "reporteFacturaVsDespacho";
/*  46:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  47:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  48:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  49:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  50: 73 */   int tipoReporte = 0;
/*  51: 75 */   private boolean indicadorSaldosPendientes = true;
/*  52:    */   @EJB
/*  53:    */   private ServicioEmpresa servicioEmpresa;
/*  54:    */   @EJB
/*  55:    */   private ServicioReporteVenta servicioReporteVenta;
/*  56:    */   @EJB
/*  57:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  58:    */   @EJB
/*  59:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  60:    */   @ManagedProperty("#{empresaBean}")
/*  61:    */   private EmpresaBean empresaBean;
/*  62:    */   
/*  63:    */   protected JRDataSource getJRDataSource()
/*  64:    */   {
/*  65: 98 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  66: 99 */     JRDataSource ds = null;
/*  67:100 */     Map<String, Object[]> hmListaDatosReporte = new HashMap();
/*  68:    */     try
/*  69:    */     {
/*  70:103 */       int idOrganizacion = AppUtil.getOrganizacion().getId();
/*  71:104 */       int idEmpresa = 0;
/*  72:105 */       if (this.empresa == null)
/*  73:    */       {
/*  74:106 */         this.empresa = new Empresa();
/*  75:107 */         idEmpresa = 0;
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:109 */         idEmpresa = this.empresa.getIdEmpresa();
/*  80:    */       }
/*  81:111 */       String[] fields = new String[0];
/*  82:112 */       if (this.tipoReporte == 1)
/*  83:    */       {
/*  84:113 */         listaDatosReporte = this.servicioReporteVenta.getReportePedidoSaldoPorDespachar(this.fechaDesde, this.fechaHasta, idEmpresa, this.indicadorSaldosPendientes, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, idOrganizacion);
/*  85:    */         
/*  86:115 */         fields = new String[] { "numeroPedido", "nombreCliente", "codigoProducto", "nombreProducto", "volumenProducto", "pesoProducto", "totalPedido", "totalFacturado", "totalDespachado", "fechaPedido" };
/*  87:    */       }
/*  88:119 */       if (this.tipoReporte == 2)
/*  89:    */       {
/*  90:120 */         listaDatosReporte = this.servicioReporteVenta.getReporteFacturaDespacho(this.fechaDesde, this.fechaHasta, idEmpresa, this.indicadorSaldosPendientes, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, idOrganizacion);
/*  91:121 */         listaDatosReporte.addAll(this.servicioReporteVenta.getReporteDespachoFactura(this.fechaDesde, this.fechaHasta, idEmpresa, this.indicadorSaldosPendientes, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, idOrganizacion));
/*  92:123 */         if (!this.indicadorSaldosPendientes)
/*  93:    */         {
/*  94:124 */           for (Object[] objs : listaDatosReporte)
/*  95:    */           {
/*  96:125 */             String clave = (String)objs[0] + "~" + (String)objs[2] + "~" + (String)objs[6];
/*  97:    */             
/*  98:127 */             hmListaDatosReporte.put(clave, objs);
/*  99:    */           }
/* 100:130 */           listaDatosReporte.clear();
/* 101:131 */           listaDatosReporte.addAll(hmListaDatosReporte.values());
/* 102:    */         }
/* 103:134 */         fields = new String[] { "numeroFactura", "fechaFactura", "numeroDespacho", "fechaDespacho", "cantidadFacturada", "cantidadDespachada", "codigoProducto", "nombreProducto", "nombreFiscal", "guiaRemision" };
/* 104:    */       }
/* 105:141 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 106:    */     }
/* 107:    */     catch (Exception e)
/* 108:    */     {
/* 109:143 */       LOG.info("Error " + e);
/* 110:144 */       e.printStackTrace();
/* 111:    */     }
/* 112:146 */     return ds;
/* 113:    */   }
/* 114:    */   
/* 115:    */   @PostConstruct
/* 116:    */   public void init()
/* 117:    */   {
/* 118:152 */     Calendar calfechaDesde = Calendar.getInstance();
/* 119:153 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 120:154 */     this.fechaDesde = calfechaDesde.getTime();
/* 121:155 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected String getCompileFileName()
/* 125:    */   {
/* 126:166 */     if (this.tipoReporte == 1) {
/* 127:167 */       return "pedidoSaldoPorDespachar";
/* 128:    */     }
/* 129:169 */     if (this.tipoReporte == 2) {
/* 130:170 */       return "reporteFacturaVsDespacho";
/* 131:    */     }
/* 132:172 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected Map<String, Object> getReportParameters()
/* 136:    */   {
/* 137:184 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 138:185 */     if ((this.empresa != null) && (this.empresa.getIdEmpresa() == -1)) {
/* 139:186 */       getEmpresa().setNombreFiscal("");
/* 140:    */     }
/* 141:189 */     if (this.tipoReporte == 1) {
/* 142:190 */       reportParameters.put("ReportTitle", "Pedido vs Despacho vs Factura");
/* 143:    */     }
/* 144:192 */     if (this.tipoReporte == 2) {
/* 145:193 */       reportParameters.put("ReportTitle", "Despacho vs Factura");
/* 146:    */     }
/* 147:196 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 148:197 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 149:198 */     reportParameters.put("filtroEmpresa", this.empresa.getNombreFiscal() == null ? "TODOS" : this.empresa == null ? "TODOS" : this.empresa.getNombreFiscal());
/* 150:199 */     reportParameters.put("filtroSoloPendientes", Boolean.valueOf(this.indicadorSaldosPendientes));
/* 151:200 */     reportParameters.put("filtroCategoriaProducto", this.categoriaProductoSeleccionado == null ? "TODOS" : this.categoriaProductoSeleccionado.getNombre());
/* 152:201 */     reportParameters.put("filtroSubcategoriaProducto", this.subcategoriaProductoSeleccionado == null ? "TODOS" : this.subcategoriaProductoSeleccionado.getNombre());
/* 153:    */     
/* 154:203 */     return reportParameters;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String execute()
/* 158:    */   {
/* 159:    */     try
/* 160:    */     {
/* 161:213 */       super.prepareReport();
/* 162:    */     }
/* 163:    */     catch (JRException e)
/* 164:    */     {
/* 165:215 */       LOG.info("Error JRException");
/* 166:216 */       e.printStackTrace();
/* 167:217 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 168:    */     }
/* 169:    */     catch (IOException e)
/* 170:    */     {
/* 171:219 */       LOG.info("Error IOException");
/* 172:220 */       e.printStackTrace();
/* 173:221 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 174:    */     }
/* 175:223 */     return "";
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Empresa getEmpresa()
/* 179:    */   {
/* 180:227 */     if (this.empresa == null) {
/* 181:228 */       this.empresa = new Empresa();
/* 182:    */     }
/* 183:230 */     return this.empresa;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setEmpresa(Empresa empresa)
/* 187:    */   {
/* 188:234 */     this.empresa = empresa;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Date getFechaDesde()
/* 192:    */   {
/* 193:238 */     return this.fechaDesde;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setFechaDesde(Date fechaDesde)
/* 197:    */   {
/* 198:242 */     this.fechaDesde = fechaDesde;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Date getFechaHasta()
/* 202:    */   {
/* 203:246 */     return this.fechaHasta;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setFechaHasta(Date fechaHasta)
/* 207:    */   {
/* 208:250 */     this.fechaHasta = fechaHasta;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<Empresa> getListaClientes()
/* 212:    */   {
/* 213:254 */     if (this.listaClientes == null) {
/* 214:255 */       this.listaClientes = this.servicioEmpresa.obtenerClientes();
/* 215:    */     }
/* 216:257 */     return this.listaClientes;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setListaClientes(List<Empresa> listaClientes)
/* 220:    */   {
/* 221:261 */     this.listaClientes = listaClientes;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public EmpresaBean getEmpresaBean()
/* 225:    */   {
/* 226:265 */     return this.empresaBean;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setEmpresaBean(EmpresaBean empresaBean)
/* 230:    */   {
/* 231:269 */     this.empresaBean = empresaBean;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean isIndicadorSaldosPendientes()
/* 235:    */   {
/* 236:278 */     return this.indicadorSaldosPendientes;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setIndicadorSaldosPendientes(boolean indicadorSaldosPendientes)
/* 240:    */   {
/* 241:288 */     this.indicadorSaldosPendientes = indicadorSaldosPendientes;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 245:    */   {
/* 246:292 */     return this.categoriaProductoSeleccionado;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 250:    */   {
/* 251:297 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 255:    */   {
/* 256:301 */     return this.subcategoriaProductoSeleccionado;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 260:    */   {
/* 261:306 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public int getTipoReporte()
/* 265:    */   {
/* 266:310 */     return this.tipoReporte;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setTipoReporte(int tipoReporte)
/* 270:    */   {
/* 271:314 */     this.tipoReporte = tipoReporte;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<SelectItem> getListaTipoReporte()
/* 275:    */   {
/* 276:318 */     List<SelectItem> lista = new ArrayList();
/* 277:319 */     lista.add(new SelectItem(Integer.valueOf(1), "Reporte Pedido vs Despacho vs Factura"));
/* 278:320 */     lista.add(new SelectItem(Integer.valueOf(2), "Reporte Despacho vs Factura"));
/* 279:321 */     return lista;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 283:    */   {
/* 284:325 */     HashMap<String, String> filters = new HashMap();
/* 285:326 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 286:327 */     if (this.listaCategoriaProductos == null) {
/* 287:328 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 288:    */     }
/* 289:330 */     return this.listaCategoriaProductos;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void cargarListaSubcategoriaProducto()
/* 293:    */   {
/* 294:334 */     HashMap<String, String> filters = new HashMap();
/* 295:335 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 296:336 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 300:    */   {
/* 301:340 */     return this.listaSubcategoriaProductos;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 305:    */   {
/* 306:345 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 310:    */   {
/* 311:350 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 312:    */   }
/* 313:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePedidoSaldoPorDespacharBean
 * JD-Core Version:    0.7.0.1
 */