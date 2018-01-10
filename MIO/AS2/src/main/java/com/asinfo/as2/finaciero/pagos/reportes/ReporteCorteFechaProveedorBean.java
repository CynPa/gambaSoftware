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
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Calendar;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.RequestScoped;
/*  29:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  30:    */ import net.sf.jasperreports.engine.JRException;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @RequestScoped
/*  35:    */ public class ReporteCorteFechaProveedorBean
/*  36:    */   extends AbstractBaseReportBean
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 104190321605520263L;
/*  39:    */   @EJB
/*  40:    */   private ServicioReporteCompra servicioReporteCompra;
/*  41:    */   @EJB
/*  42:    */   private ServicioEmpresa servicioEmpresa;
/*  43:    */   @EJB
/*  44:    */   private ServicioTipoOperacion servicioTipoOperacion;
/*  45:    */   @EJB
/*  46:    */   private transient ServicioSucursal servicioSucursal;
/*  47:    */   @EJB
/*  48:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  49: 73 */   private Date fechaHasta = Calendar.getInstance().getTime();
/*  50:    */   private boolean indicadorResumen;
/*  51:    */   private Empresa empresa;
/*  52:    */   private TipoOperacion tipoOperacion;
/*  53:    */   private Sucursal sucursal;
/*  54:    */   private CategoriaEmpresa categoriaEmpresa;
/*  55:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  56:    */   private List<TipoOperacion> listaTipoOperacion;
/*  57:    */   private List<Sucursal> listaSucursal;
/*  58:    */   
/*  59:    */   protected String getCompileFileName()
/*  60:    */   {
/*  61: 94 */     if (this.indicadorResumen) {
/*  62: 95 */       return "reporteCorteResumido";
/*  63:    */     }
/*  64: 97 */     return "reporteCorte";
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected Map<String, Object> getReportParameters()
/*  68:    */   {
/*  69:108 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  70:109 */     if (!this.indicadorResumen) {
/*  71:110 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_corte_fecha_titulo"));
/*  72:    */     } else {
/*  73:112 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_corte_fecha_resumido_titulo"));
/*  74:    */     }
/*  75:114 */     reportParameters.put("FechaHasta", this.fechaHasta);
/*  76:115 */     reportParameters.put("total", "Total");
/*  77:116 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/*  78:117 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? getCategoriaEmpresa().getNombre() : "Todos");
/*  79:118 */     reportParameters.put("reporteProveedor", Boolean.valueOf(true));
/*  80:119 */     reportParameters.put("agrupadoCategoriaEmpresa", 
/*  81:120 */       Boolean.valueOf((this.categoriaEmpresa != null) && (this.categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()))));
/*  82:    */     
/*  83:122 */     return reportParameters;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected JRDataSource getJRDataSource()
/*  87:    */   {
/*  88:133 */     List listaDatosReporte = new ArrayList();
/*  89:134 */     JRDataSource ds = null;
/*  90:    */     try
/*  91:    */     {
/*  92:137 */       if (this.fechaHasta == null) {
/*  93:138 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/*  94:    */       }
/*  95:141 */       listaDatosReporte = this.servicioReporteCompra.getListaReporteCorteFecha(this.fechaHasta, getEmpresa().getIdEmpresa(), AppUtil.getOrganizacion()
/*  96:142 */         .getId(), getTipoOperacion().getId(), getSucursal(), getCategoriaEmpresa());
/*  97:    */       
/*  98:144 */       String[] fields = { "codigo", "identificacion", "nombre", "fechaFactura", "factura", "debito", "credito", "saldo", "idSucursal", "nombreSucursal", "idCategoriaEmpresa", "nombreCategoriaEmpresa" };
/*  99:    */       
/* 100:    */ 
/* 101:147 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:149 */       LOG.info("Error " + e);
/* 106:150 */       e.printStackTrace();
/* 107:    */     }
/* 108:152 */     return ds;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String execute()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:162 */       if (this.fechaHasta == null) {
/* 116:163 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 117:    */       }
/* 118:165 */       super.prepareReport();
/* 119:    */     }
/* 120:    */     catch (JRException e)
/* 121:    */     {
/* 122:167 */       LOG.info("Error JRException");
/* 123:168 */       e.printStackTrace();
/* 124:169 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 125:    */     }
/* 126:    */     catch (IOException e)
/* 127:    */     {
/* 128:171 */       LOG.info("Error IOException");
/* 129:172 */       e.printStackTrace();
/* 130:173 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 131:    */     }
/* 132:176 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 136:    */   {
/* 137:180 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Date getFechaHasta()
/* 141:    */   {
/* 142:189 */     return this.fechaHasta;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setFechaHasta(Date fechaHasta)
/* 146:    */   {
/* 147:199 */     this.fechaHasta = fechaHasta;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isIndicadorResumen()
/* 151:    */   {
/* 152:203 */     return this.indicadorResumen;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 156:    */   {
/* 157:207 */     this.indicadorResumen = indicadorResumen;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Empresa getEmpresa()
/* 161:    */   {
/* 162:211 */     if (this.empresa == null) {
/* 163:212 */       this.empresa = new Empresa();
/* 164:    */     }
/* 165:214 */     return this.empresa;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setEmpresa(Empresa empresa)
/* 169:    */   {
/* 170:218 */     this.empresa = empresa;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public TipoOperacion getTipoOperacion()
/* 174:    */   {
/* 175:227 */     if (this.tipoOperacion == null) {
/* 176:228 */       this.tipoOperacion = new TipoOperacion();
/* 177:    */     }
/* 178:230 */     return this.tipoOperacion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setTipoOperacion(TipoOperacion tipoOperacion)
/* 182:    */   {
/* 183:240 */     this.tipoOperacion = tipoOperacion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<TipoOperacion> getListaTipoOperacion()
/* 187:    */   {
/* 188:249 */     if (this.listaTipoOperacion == null) {
/* 189:250 */       this.listaTipoOperacion = this.servicioTipoOperacion.obtenerListaCombo("nombre", true, null);
/* 190:    */     }
/* 191:252 */     return this.listaTipoOperacion;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaTipoOperacion(List<TipoOperacion> listaTipoOperacion)
/* 195:    */   {
/* 196:262 */     this.listaTipoOperacion = listaTipoOperacion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Sucursal getSucursal()
/* 200:    */   {
/* 201:269 */     return this.sucursal;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setSucursal(Sucursal sucursal)
/* 205:    */   {
/* 206:277 */     this.sucursal = sucursal;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public List<Sucursal> getListaSucursal()
/* 210:    */   {
/* 211:284 */     if (this.listaSucursal == null)
/* 212:    */     {
/* 213:285 */       Map<String, String> filters = new HashMap();
/* 214:286 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 215:287 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 216:    */     }
/* 217:290 */     return this.listaSucursal;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 221:    */   {
/* 222:298 */     this.listaSucursal = listaSucursal;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 226:    */   {
/* 227:302 */     return this.categoriaEmpresa;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 231:    */   {
/* 232:306 */     this.categoriaEmpresa = categoriaEmpresa;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 236:    */   {
/* 237:315 */     HashMap<String, String> filtros = new HashMap();
/* 238:316 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 239:317 */     if (this.listaCategoriaEmpresa == null)
/* 240:    */     {
/* 241:318 */       this.listaCategoriaEmpresa = new ArrayList();
/* 242:319 */       this.listaCategoriaEmpresa.add(new CategoriaEmpresa(-99001, CategoriaEmpresaEnum.TODOS_AGRUPADO.name(), "Todos Agrupado..."));
/* 243:320 */       this.listaCategoriaEmpresa.addAll(this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros));
/* 244:    */     }
/* 245:322 */     return this.listaCategoriaEmpresa;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 249:    */   {
/* 250:332 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 251:    */   }
/* 252:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteCorteFechaProveedorBean
 * JD-Core Version:    0.7.0.1
 */