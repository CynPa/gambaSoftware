/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*  12:    */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReporteAnticipoProveedor;
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
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class ReporteAnticipoProveedorCorteFechaBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 2887621168786720186L;
/*  38:    */   @EJB
/*  39:    */   private transient ServicioReporteAnticipoProveedor servicioReporteAnticipoProveedor;
/*  40:    */   @EJB
/*  41:    */   private transient ServicioEmpresa servicioEmpresa;
/*  42:    */   @EJB
/*  43:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  44:    */   @EJB
/*  45:    */   private ServicioSucursal servicioSucursal;
/*  46:    */   private Date fechaDesde;
/*  47:    */   private Date fechaHasta;
/*  48:    */   private Empresa empresa;
/*  49:    */   private boolean indicadorResumen;
/*  50:    */   private boolean indicadorSaldoDiferenciaCero;
/*  51:    */   private int idSucursal;
/*  52:    */   private CategoriaEmpresa categoriaEmpresa;
/*  53:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  54:    */   private List<Sucursal> listaSucursalCombo;
/*  55:    */   
/*  56:    */   protected JRDataSource getJRDataSource()
/*  57:    */   {
/*  58: 83 */     List listaDatosReporte = new ArrayList();
/*  59: 84 */     JRDataSource ds = null;
/*  60:    */     try
/*  61:    */     {
/*  62: 89 */       listaDatosReporte = this.servicioReporteAnticipoProveedor.getReporteCorteFechaAnticipoProveedores(this.fechaDesde, this.fechaHasta, this.empresa
/*  63: 90 */         .getIdEmpresa(), this.indicadorSaldoDiferenciaCero, AppUtil.getOrganizacion().getId(), getCategoriaEmpresa(), getIdSucursal());
/*  64: 91 */       if (listaDatosReporte.size() == 0)
/*  65:    */       {
/*  66: 92 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  67:    */       }
/*  68:    */       else
/*  69:    */       {
/*  70: 95 */         String[] fields = { "numeroAnticipo", "valorAnticipo", "valorLiquidacion", "nombreComercial", "nombreFiscal", "fechaLiquidacion", "factura", "saldoAnticipo", "identificacion", "numeroNotaCredito", "fechaAnticipoCliente", "valorDevolucion", "nombreSucursal", "codigoSucursal", "idCategoriaEmpresa", "nombreCategoriaEmpresa", "descripcionAnticipo" };
/*  71:    */         
/*  72:    */ 
/*  73:    */ 
/*  74: 99 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79:103 */       e.printStackTrace();
/*  80:104 */       addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/*  81:    */     }
/*  82:107 */     return ds;
/*  83:    */   }
/*  84:    */   
/*  85:    */   @PostConstruct
/*  86:    */   public void init()
/*  87:    */   {
/*  88:112 */     Calendar calfechaDesde = Calendar.getInstance();
/*  89:113 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  90:114 */     this.fechaDesde = calfechaDesde.getTime();
/*  91:115 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected String getCompileFileName()
/*  95:    */   {
/*  96:125 */     if (this.indicadorResumen) {
/*  97:126 */       return "reporteCorteFechaAnticipoResumido";
/*  98:    */     }
/*  99:128 */     return "reporteCorteFechaAnticipo";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String execute()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:140 */       validaDatos();
/* 107:141 */       super.prepareReport();
/* 108:    */     }
/* 109:    */     catch (JRException e)
/* 110:    */     {
/* 111:143 */       LOG.info("Error JRException");
/* 112:144 */       e.printStackTrace();
/* 113:145 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 114:    */     }
/* 115:    */     catch (IOException e)
/* 116:    */     {
/* 117:147 */       LOG.info("Error IOException");
/* 118:148 */       e.printStackTrace();
/* 119:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:152 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected Map<String, Object> getReportParameters()
/* 125:    */   {
/* 126:162 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 127:163 */     if (!this.indicadorResumen) {
/* 128:164 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_corte_fecha_anticipo_proveedor"));
/* 129:    */     } else {
/* 130:166 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_corte_fecha_anticipo_proveedor_resumido"));
/* 131:    */     }
/* 132:168 */     reportParameters.put("fechaDesde", this.fechaDesde);
/* 133:169 */     reportParameters.put("fechaHasta", this.fechaHasta);
/* 134:170 */     reportParameters.put("total", "Total");
/* 135:171 */     reportParameters.put("p_categoriaEmpresa", getCategoriaEmpresa() != null ? getCategoriaEmpresa().getNombre() : "Todos");
/* 136:172 */     reportParameters.put("reporteProveedor", Boolean.valueOf(true));
/* 137:173 */     reportParameters.put("agrupadoCategoriaEmpresa", 
/* 138:174 */       Boolean.valueOf((this.categoriaEmpresa != null) && (this.categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()))));
/* 139:    */     
/* 140:176 */     return reportParameters;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 144:    */   {
/* 145:180 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void validaDatos()
/* 149:    */   {
/* 150:185 */     if (this.empresa == null) {
/* 151:186 */       this.empresa = new Empresa();
/* 152:    */     }
/* 153:188 */     if (this.fechaDesde == null) {
/* 154:189 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 155:    */     }
/* 156:191 */     if (this.fechaHasta == null) {
/* 157:192 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Date getFechaHasta()
/* 162:    */   {
/* 163:202 */     return this.fechaHasta;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setFechaHasta(Date fechaHasta)
/* 167:    */   {
/* 168:212 */     this.fechaHasta = fechaHasta;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Empresa getEmpresa()
/* 172:    */   {
/* 173:221 */     if (this.empresa == null) {
/* 174:222 */       this.empresa = new Empresa();
/* 175:    */     }
/* 176:224 */     return this.empresa;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setEmpresa(Empresa empresa)
/* 180:    */   {
/* 181:234 */     this.empresa = empresa;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Date getFechaDesde()
/* 185:    */   {
/* 186:243 */     return this.fechaDesde;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setFechaDesde(Date fechaDesde)
/* 190:    */   {
/* 191:253 */     this.fechaDesde = fechaDesde;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean isIndicadorResumen()
/* 195:    */   {
/* 196:262 */     return this.indicadorResumen;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 200:    */   {
/* 201:272 */     this.indicadorResumen = indicadorResumen;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isIndicadorSaldoDiferenciaCero()
/* 205:    */   {
/* 206:281 */     return this.indicadorSaldoDiferenciaCero;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setIndicadorSaldoDiferenciaCero(boolean indicadorSaldoDiferenciaCero)
/* 210:    */   {
/* 211:291 */     this.indicadorSaldoDiferenciaCero = indicadorSaldoDiferenciaCero;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 215:    */   {
/* 216:295 */     return this.categoriaEmpresa;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 220:    */   {
/* 221:299 */     this.categoriaEmpresa = categoriaEmpresa;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 225:    */   {
/* 226:308 */     HashMap<String, String> filtros = new HashMap();
/* 227:309 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 228:310 */     if (this.listaCategoriaEmpresa == null)
/* 229:    */     {
/* 230:311 */       this.listaCategoriaEmpresa = new ArrayList();
/* 231:312 */       this.listaCategoriaEmpresa.add(new CategoriaEmpresa(-99001, CategoriaEmpresaEnum.TODOS_AGRUPADO.name(), "Todos Agrupado..."));
/* 232:313 */       this.listaCategoriaEmpresa.addAll(this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros));
/* 233:    */     }
/* 234:315 */     return this.listaCategoriaEmpresa;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 238:    */   {
/* 239:325 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public int getIdSucursal()
/* 243:    */   {
/* 244:329 */     return this.idSucursal;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setIdSucursal(int idSucursal)
/* 248:    */   {
/* 249:333 */     this.idSucursal = idSucursal;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public List<Sucursal> getListaSucursalCombo()
/* 253:    */   {
/* 254:342 */     if (this.listaSucursalCombo == null) {
/* 255:343 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 256:    */     }
/* 257:345 */     return this.listaSucursalCombo;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 261:    */   {
/* 262:355 */     if (listaSucursalCombo == null) {
/* 263:356 */       listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 264:    */     }
/* 265:358 */     this.listaSucursalCombo = listaSucursalCombo;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteAnticipoProveedorCorteFechaBean
 * JD-Core Version:    0.7.0.1
 */