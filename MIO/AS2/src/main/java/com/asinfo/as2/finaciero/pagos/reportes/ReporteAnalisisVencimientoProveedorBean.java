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
/*  12:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.TipoOperacion;
/*  15:    */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.RequestScoped;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @RequestScoped
/*  37:    */ public class ReporteAnalisisVencimientoProveedorBean
/*  38:    */   extends AbstractBaseReportBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 605465445550205775L;
/*  41:    */   @EJB
/*  42:    */   private ServicioEmpresa servicioEmpresa;
/*  43:    */   @EJB
/*  44:    */   private ServicioReporteCompra servicioReporteCompra;
/*  45:    */   @EJB
/*  46:    */   private ServicioTipoOperacion servicioTipoOperacion;
/*  47:    */   @EJB
/*  48:    */   private transient ServicioSucursal servicioSucursal;
/*  49:    */   @EJB
/*  50:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  51:    */   private boolean indicadorResumen;
/*  52:    */   private boolean indicadorSoloVencido;
/*  53:    */   private Empresa empresa;
/*  54:    */   private TipoOperacion tipoOperacion;
/*  55: 79 */   private Date fechaHasta = new Date();
/*  56: 80 */   private Date fechaDesde = new Date();
/*  57:    */   private Sucursal sucursal;
/*  58:    */   private List<Empresa> listaProveedores;
/*  59:    */   private List<TipoOperacion> listaTipoOperacion;
/*  60: 88 */   private TipoOrganizacion tipoOrganizacion = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion();
/*  61:    */   private List<Sucursal> listaSucursal;
/*  62:    */   private CategoriaEmpresa categoriaEmpresa;
/*  63:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  64:    */   
/*  65:    */   protected JRDataSource getJRDataSource()
/*  66:    */   {
/*  67:102 */     List listaDatosReporte = new ArrayList();
/*  68:103 */     JRDataSource ds = null;
/*  69:    */     try
/*  70:    */     {
/*  71:105 */       if (this.tipoOrganizacion != TipoOrganizacion.TIPO_ORGANIZACION_ADRIALPETRO) {
/*  72:106 */         setFechaDesde(null);
/*  73:    */       }
/*  74:108 */       listaDatosReporte = this.servicioReporteCompra.getAnalisisVencimientoProveedor(getFechaDesde(), getFechaHasta(), getEmpresa().getIdEmpresa(), 
/*  75:109 */         AppUtil.getOrganizacion().getId(), getTipoOperacion().getId(), this.tipoOrganizacion, getSucursal(), getCategoriaEmpresa(), isIndicadorSoloVencido());
/*  76:    */       
/*  77:111 */       String[] fields = { "codigoEmpresa", "nombreFiscal", "identificacion", "numeroFactura", "fechaFactura", "fechaRecepcionFactura", "fechaVencimiento", "f_vencido120+", "f_vencido120", "f_vencido90", "f_vencido60", "f_vencido30", "f_por_vencer30", "f_por_vencer60", "f_por_vencer90", "f_por_vencer120", "f_por_vencer120+", "f_total", "f_debito", "f_credito", "f_descripcionFactura", "f_asientoCompra", "f_valorRetenido", "idCategoriaEmpresa", "nombreCategoriaEmpresa", "f_numeroPedido", "f_centroCosto", "f_fechaEmision" };
/*  78:    */       
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:116 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  83:    */     }
/*  84:    */     catch (ExcepcionAS2 e)
/*  85:    */     {
/*  86:119 */       LOG.info("Error " + e);
/*  87:120 */       e.printStackTrace();
/*  88:    */     }
/*  89:122 */     return ds;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected String getCompileFileName()
/*  93:    */   {
/*  94:132 */     if (this.indicadorResumen) {
/*  95:133 */       return "reporteAnalisisVencimientoResumidoProveedor";
/*  96:    */     }
/*  97:135 */     if (this.tipoOrganizacion == TipoOrganizacion.TIPO_ORGANIZACION_ADRIALPETRO) {
/*  98:136 */       return "reporteAnalisisVencimientoDetallado2";
/*  99:    */     }
/* 100:138 */     return "reporteAnalisisVencimientoDetalladoProveedor";
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected Map<String, Object> getReportParameters()
/* 104:    */   {
/* 105:149 */     if (getEmpresa().getIdEmpresa() == 0) {
/* 106:150 */       getEmpresa().setNombreFiscal("");
/* 107:    */     }
/* 108:152 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 109:153 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 110:154 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 111:155 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 112:156 */     reportParameters.put("p_empresa", this.empresa.getId() != 0 ? this.empresa.getNombreFiscal() : "Todos");
/* 113:157 */     reportParameters.put("reporteProveedor", Boolean.TRUE);
/* 114:158 */     reportParameters.put("p_tipoOperacion", this.tipoOperacion != null ? this.tipoOperacion.getNombre() : "Todos");
/* 115:159 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/* 116:160 */     if (!this.indicadorResumen) {
/* 117:161 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_analisis_vencimientos_titulo"));
/* 118:    */     } else {
/* 119:163 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_analisis_vencimientos_resumido_titulo"));
/* 120:    */     }
/* 121:165 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? getCategoriaEmpresa().getNombre() : "Todos");
/* 122:166 */     reportParameters.put("agrupadoCategoriaEmpresa", 
/* 123:167 */       Boolean.valueOf((this.categoriaEmpresa != null) && (this.categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()))));
/* 124:    */     
/* 125:169 */     return reportParameters;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String execute()
/* 129:    */   {
/* 130:    */     try
/* 131:    */     {
/* 132:179 */       super.prepareReport();
/* 133:    */     }
/* 134:    */     catch (JRException e)
/* 135:    */     {
/* 136:181 */       LOG.info("Error JRException");
/* 137:182 */       e.printStackTrace();
/* 138:183 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 139:    */     }
/* 140:    */     catch (IOException e)
/* 141:    */     {
/* 142:185 */       LOG.info("Error IOException");
/* 143:186 */       e.printStackTrace();
/* 144:187 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 145:    */     }
/* 146:190 */     return null;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<Empresa> getListaProveedores()
/* 150:    */   {
/* 151:199 */     if (this.listaProveedores == null) {
/* 152:200 */       this.listaProveedores = this.servicioEmpresa.obtenerClientes();
/* 153:    */     }
/* 154:202 */     return this.listaProveedores;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setListaProveedores(List<Empresa> listaProveedores)
/* 158:    */   {
/* 159:212 */     this.listaProveedores = listaProveedores;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Empresa getEmpresa()
/* 163:    */   {
/* 164:216 */     if (this.empresa == null) {
/* 165:217 */       this.empresa = new Empresa();
/* 166:    */     }
/* 167:219 */     return this.empresa;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setEmpresa(Empresa empresa)
/* 171:    */   {
/* 172:223 */     this.empresa = empresa;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 176:    */   {
/* 177:227 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isIndicadorResumen()
/* 181:    */   {
/* 182:231 */     return this.indicadorResumen;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 186:    */   {
/* 187:235 */     this.indicadorResumen = indicadorResumen;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Date getFechaHasta()
/* 191:    */   {
/* 192:244 */     if (this.fechaHasta == null) {
/* 193:245 */       this.fechaHasta = new Date();
/* 194:    */     }
/* 195:247 */     return this.fechaHasta;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setFechaHasta(Date fechaHasta)
/* 199:    */   {
/* 200:257 */     this.fechaHasta = fechaHasta;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public TipoOperacion getTipoOperacion()
/* 204:    */   {
/* 205:266 */     if (this.tipoOperacion == null) {
/* 206:267 */       this.tipoOperacion = new TipoOperacion();
/* 207:    */     }
/* 208:269 */     return this.tipoOperacion;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setTipoOperacion(TipoOperacion tipoOperacion)
/* 212:    */   {
/* 213:279 */     this.tipoOperacion = tipoOperacion;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<TipoOperacion> getListaTipoOperacion()
/* 217:    */   {
/* 218:288 */     if (this.listaTipoOperacion == null) {
/* 219:289 */       this.listaTipoOperacion = this.servicioTipoOperacion.obtenerListaCombo("nombre", true, null);
/* 220:    */     }
/* 221:291 */     return this.listaTipoOperacion;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setListaTipoOperacion(List<TipoOperacion> listaTipoOperacion)
/* 225:    */   {
/* 226:301 */     this.listaTipoOperacion = listaTipoOperacion;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Date getFechaDesde()
/* 230:    */   {
/* 231:310 */     return this.fechaDesde;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setFechaDesde(Date fechaDesde)
/* 235:    */   {
/* 236:320 */     this.fechaDesde = fechaDesde;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public Sucursal getSucursal()
/* 240:    */   {
/* 241:327 */     return this.sucursal;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setSucursal(Sucursal sucursal)
/* 245:    */   {
/* 246:334 */     this.sucursal = sucursal;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public List<Sucursal> getListaSucursal()
/* 250:    */   {
/* 251:341 */     if (this.listaSucursal == null)
/* 252:    */     {
/* 253:342 */       Map<String, String> filters = new HashMap();
/* 254:343 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 255:344 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 256:    */     }
/* 257:347 */     return this.listaSucursal;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 261:    */   {
/* 262:354 */     this.listaSucursal = listaSucursal;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 266:    */   {
/* 267:358 */     return this.categoriaEmpresa;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 271:    */   {
/* 272:362 */     this.categoriaEmpresa = categoriaEmpresa;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 276:    */   {
/* 277:371 */     HashMap<String, String> filtros = new HashMap();
/* 278:372 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 279:373 */     if (this.listaCategoriaEmpresa == null)
/* 280:    */     {
/* 281:374 */       this.listaCategoriaEmpresa = new ArrayList();
/* 282:375 */       this.listaCategoriaEmpresa.add(new CategoriaEmpresa(-99001, CategoriaEmpresaEnum.TODOS_AGRUPADO.name(), "Todos Agrupado..."));
/* 283:376 */       this.listaCategoriaEmpresa.addAll(this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros));
/* 284:    */     }
/* 285:379 */     return this.listaCategoriaEmpresa;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 289:    */   {
/* 290:389 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public boolean isIndicadorSoloVencido()
/* 294:    */   {
/* 295:393 */     return this.indicadorSoloVencido;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setIndicadorSoloVencido(boolean indicadorSoloVencido)
/* 299:    */   {
/* 300:397 */     this.indicadorSoloVencido = indicadorSoloVencido;
/* 301:    */   }
/* 302:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteAnalisisVencimientoProveedorBean
 * JD-Core Version:    0.7.0.1
 */