/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.configuracion.servicio.impl.ServicioTipoOperacion;
/*   4:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.TipoOperacion;
/*  12:    */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.RequestScoped;
/*  27:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  28:    */ import net.sf.jasperreports.engine.JRException;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @RequestScoped
/*  33:    */ public class ReporteVencimientoFacturaProveedorBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = -1911047301927807623L;
/*  37:    */   @EJB
/*  38:    */   private ServicioReporteCompra servicioReporteCompra;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpresa servicioEmpresa;
/*  41:    */   @EJB
/*  42:    */   private ServicioTipoOperacion servicioTipoOperacion;
/*  43:    */   @EJB
/*  44:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  45: 69 */   private Date fechaHasta = Calendar.getInstance().getTime();
/*  46:    */   private boolean indicadorResumen;
/*  47:    */   private Empresa empresa;
/*  48:    */   private TipoOperacion tipoOperacion;
/*  49:    */   private CategoriaEmpresa categoriaEmpresa;
/*  50:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  51:    */   private boolean indicadorSoloVencido;
/*  52:    */   private List<TipoOperacion> listaTipoOperacion;
/*  53:    */   
/*  54:    */   protected String getCompileFileName()
/*  55:    */   {
/*  56: 88 */     if (this.indicadorResumen) {
/*  57: 89 */       return "reporteVencimientoFacturaResumen";
/*  58:    */     }
/*  59: 91 */     return "reporteVencimientoFactura";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<String, Object> getReportParameters()
/*  63:    */   {
/*  64:103 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  65:104 */     if (this.indicadorResumen) {
/*  66:105 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_titulo_reporte_vencimientos_resumido"));
/*  67:    */     } else {
/*  68:107 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_titulo_reporte_vencimientos"));
/*  69:    */     }
/*  70:109 */     if (this.fechaHasta == null) {
/*  71:110 */       this.fechaHasta = new Date();
/*  72:    */     }
/*  73:112 */     reportParameters.put("FechaHasta", this.fechaHasta);
/*  74:113 */     reportParameters.put("total", "Total");
/*  75:114 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? getCategoriaEmpresa().getNombre() : "Todos");
/*  76:115 */     reportParameters.put("reporteProveedor", Boolean.valueOf(true));
/*  77:116 */     reportParameters.put("agrupadoCategoriaEmpresa", 
/*  78:117 */       Boolean.valueOf((this.categoriaEmpresa != null) && (this.categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()))));
/*  79:    */     
/*  80:119 */     return reportParameters;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected JRDataSource getJRDataSource()
/*  84:    */   {
/*  85:131 */     List listaDatosReporte = new ArrayList();
/*  86:132 */     JRDataSource ds = null;
/*  87:    */     try
/*  88:    */     {
/*  89:135 */       if (this.fechaHasta == null) {
/*  90:136 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/*  91:    */       }
/*  92:139 */       listaDatosReporte = this.servicioReporteCompra.getVencimientos(getEmpresa(), this.fechaHasta, AppUtil.getOrganizacion().getId(), 
/*  93:140 */         getTipoOperacion(), getCategoriaEmpresa(), isIndicadorSoloVencido());
/*  94:    */       
/*  95:142 */       String[] fields = { "f_codigo", "f_nombreFiscal", "f_identificacion", "f_numeroFactura", "f_fechaFactura", "f_fechaVencimiento", "f_saldoVencido", "f_saldoPorVencer", "f_saldo", "idCategoriaEmpresa", "nombreCategoriaEmpresa" };
/*  96:    */       
/*  97:    */ 
/*  98:145 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:147 */       LOG.info("Error " + e);
/* 103:148 */       e.printStackTrace();
/* 104:    */     }
/* 105:150 */     return ds;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 109:    */   {
/* 110:154 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String execute()
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:164 */       if (this.fechaHasta == null) {
/* 118:165 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 119:    */       }
/* 120:167 */       super.prepareReport();
/* 121:    */     }
/* 122:    */     catch (JRException e)
/* 123:    */     {
/* 124:169 */       LOG.info("Error JRException");
/* 125:170 */       e.printStackTrace();
/* 126:171 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 127:    */     }
/* 128:    */     catch (IOException e)
/* 129:    */     {
/* 130:173 */       LOG.info("Error IOException");
/* 131:174 */       e.printStackTrace();
/* 132:175 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 133:    */     }
/* 134:178 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Date getFechaHasta()
/* 138:    */   {
/* 139:187 */     return this.fechaHasta;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setFechaHasta(Date fechaHasta)
/* 143:    */   {
/* 144:197 */     this.fechaHasta = fechaHasta;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isIndicadorResumen()
/* 148:    */   {
/* 149:201 */     return this.indicadorResumen;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 153:    */   {
/* 154:205 */     this.indicadorResumen = indicadorResumen;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Empresa getEmpresa()
/* 158:    */   {
/* 159:209 */     return this.empresa;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setEmpresa(Empresa empresa)
/* 163:    */   {
/* 164:213 */     this.empresa = empresa;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public TipoOperacion getTipoOperacion()
/* 168:    */   {
/* 169:222 */     return this.tipoOperacion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setTipoOperacion(TipoOperacion tipoOperacion)
/* 173:    */   {
/* 174:232 */     this.tipoOperacion = tipoOperacion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public List<TipoOperacion> getListaTipoOperacion()
/* 178:    */   {
/* 179:241 */     if (this.listaTipoOperacion == null) {
/* 180:242 */       this.listaTipoOperacion = this.servicioTipoOperacion.obtenerListaCombo("nombre", true, null);
/* 181:    */     }
/* 182:244 */     return this.listaTipoOperacion;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setListaTipoOperacion(List<TipoOperacion> listaTipoOperacion)
/* 186:    */   {
/* 187:254 */     this.listaTipoOperacion = listaTipoOperacion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 191:    */   {
/* 192:258 */     return this.categoriaEmpresa;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 196:    */   {
/* 197:262 */     this.categoriaEmpresa = categoriaEmpresa;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 201:    */   {
/* 202:271 */     HashMap<String, String> filtros = new HashMap();
/* 203:272 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 204:273 */     if (this.listaCategoriaEmpresa == null)
/* 205:    */     {
/* 206:274 */       this.listaCategoriaEmpresa = new ArrayList();
/* 207:275 */       this.listaCategoriaEmpresa.add(new CategoriaEmpresa(-99001, CategoriaEmpresaEnum.TODOS_AGRUPADO.name(), "Todos Agrupado..."));
/* 208:276 */       this.listaCategoriaEmpresa.addAll(this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros));
/* 209:    */     }
/* 210:279 */     return this.listaCategoriaEmpresa;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 214:    */   {
/* 215:289 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isIndicadorSoloVencido()
/* 219:    */   {
/* 220:293 */     return this.indicadorSoloVencido;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setIndicadorSoloVencido(boolean indicadorSoloVencido)
/* 224:    */   {
/* 225:297 */     this.indicadorSoloVencido = indicadorSoloVencido;
/* 226:    */   }
/* 227:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteVencimientoFacturaProveedorBean
 * JD-Core Version:    0.7.0.1
 */