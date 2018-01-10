/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.controller.EmpresaBean;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReportePagoProveedor;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Calendar;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.annotation.PostConstruct;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ManagedProperty;
/*  27:    */ import javax.faces.bean.RequestScoped;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @RequestScoped
/*  34:    */ public class ReportePagoProveedorBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 7521544347379293416L;
/*  38:    */   @EJB
/*  39:    */   private ServicioReportePagoProveedor servicioReportePagoProveedor;
/*  40:    */   @EJB
/*  41:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  42:    */   private Empresa empresa;
/*  43:    */   private Date fechaDesde;
/*  44:    */   private Date fechaHasta;
/*  45:    */   private boolean indicadorResumen;
/*  46:    */   private CategoriaEmpresa categoriaEmpresa;
/*  47:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  48:    */   @ManagedProperty("#{empresaBean}")
/*  49:    */   private EmpresaBean empresaBean;
/*  50:    */   
/*  51:    */   protected JRDataSource getJRDataSource()
/*  52:    */   {
/*  53: 85 */     List listaDatosReporte = new ArrayList();
/*  54: 86 */     JRDataSource ds = null;
/*  55:    */     try
/*  56:    */     {
/*  57: 88 */       validaDatos();
/*  58: 89 */       listaDatosReporte = this.servicioReportePagoProveedor.getReportePagoProveedor(this.fechaDesde, this.fechaHasta, this.empresa.getIdEmpresa(), 
/*  59: 90 */         AppUtil.getOrganizacion().getId(), getCategoriaEmpresa());
/*  60:    */       
/*  61: 92 */       String[] fields = { "f_identificacion", "f_nombreFiscal", "f_numero", "f_fecha", "f_factura", "f_estado", "f_descripcion", "f_asiento", "f_tipoAsiento", "f_valor", "idCategoriaEmpresa", "nombreCategoriaEmpresa" };
/*  62:    */       
/*  63:    */ 
/*  64: 95 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  65:    */     }
/*  66:    */     catch (ExcepcionAS2 e)
/*  67:    */     {
/*  68: 97 */       LOG.info("Error " + e);
/*  69: 98 */       e.printStackTrace();
/*  70:    */     }
/*  71:100 */     return ds;
/*  72:    */   }
/*  73:    */   
/*  74:    */   @PostConstruct
/*  75:    */   public void init()
/*  76:    */   {
/*  77:105 */     Calendar calfechaDesde = Calendar.getInstance();
/*  78:106 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  79:107 */     this.fechaDesde = calfechaDesde.getTime();
/*  80:108 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected String getCompileFileName()
/*  84:    */   {
/*  85:118 */     if (this.indicadorResumen) {
/*  86:119 */       return "reporteCobroPagoResumido";
/*  87:    */     }
/*  88:121 */     return "reporteCobroPago";
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected Map<String, Object> getReportParameters()
/*  92:    */   {
/*  93:132 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  94:133 */     reportParameters.put("ReportTitle", "Pagos");
/*  95:134 */     reportParameters.put("usuario", "Usuario:");
/*  96:135 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/*  97:136 */     reportParameters.put("pagina", "Pagina:");
/*  98:137 */     reportParameters.put("desde", "Desde:");
/*  99:138 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 100:139 */     reportParameters.put("hasta", "Hasta");
/* 101:140 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 102:141 */     reportParameters.put("p_total", "Total");
/* 103:142 */     reportParameters.put("p_identificacion", "Identificacion");
/* 104:143 */     reportParameters.put("p_nombreFiscal", "Nombre Fiscal");
/* 105:144 */     reportParameters.put("p_numero", "Numero ");
/* 106:145 */     reportParameters.put("p_fecha", "Fecha");
/* 107:146 */     reportParameters.put("p_descripcion", "Descripcion");
/* 108:147 */     reportParameters.put("p_factura", "Factura");
/* 109:148 */     reportParameters.put("p_estado", "Estado");
/* 110:149 */     reportParameters.put("p_asiento", "Asiento ");
/* 111:150 */     reportParameters.put("p_tipoAsiento", "Tipo Asiento");
/* 112:151 */     reportParameters.put("p_valor", "Valor");
/* 113:152 */     reportParameters.put("p_total", "Total");
/* 114:153 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? getCategoriaEmpresa().getNombre() : "Todos");
/* 115:154 */     reportParameters.put("reporteProveedor", Boolean.valueOf(true));
/* 116:155 */     reportParameters.put("agrupadoCategoriaEmpresa", 
/* 117:156 */       Boolean.valueOf((this.categoriaEmpresa != null) && (this.categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()))));
/* 118:    */     
/* 119:158 */     return reportParameters;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String execute()
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:169 */       if (this.fechaDesde == null) {
/* 127:170 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 128:    */       }
/* 129:172 */       if (this.fechaHasta == null) {
/* 130:173 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 131:    */       }
/* 132:176 */       super.prepareReport();
/* 133:    */     }
/* 134:    */     catch (JRException e)
/* 135:    */     {
/* 136:178 */       LOG.info("Error JRException");
/* 137:179 */       e.printStackTrace();
/* 138:180 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 139:    */     }
/* 140:    */     catch (IOException e)
/* 141:    */     {
/* 142:182 */       LOG.info("Error IOException");
/* 143:183 */       e.printStackTrace();
/* 144:184 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 145:    */     }
/* 146:186 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void validaDatos()
/* 150:    */   {
/* 151:190 */     if (this.empresa == null)
/* 152:    */     {
/* 153:191 */       this.empresa = new Empresa();
/* 154:192 */       this.empresa.setId(0);
/* 155:    */     }
/* 156:194 */     if (this.fechaDesde == null) {
/* 157:195 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 158:    */     }
/* 159:197 */     if (this.fechaHasta == null) {
/* 160:198 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Date getFechaDesde()
/* 165:    */   {
/* 166:203 */     return this.fechaDesde;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setFechaDesde(Date fechaDesde)
/* 170:    */   {
/* 171:207 */     this.fechaDesde = fechaDesde;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Date getFechaHasta()
/* 175:    */   {
/* 176:211 */     return this.fechaHasta;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setFechaHasta(Date fechaHasta)
/* 180:    */   {
/* 181:215 */     this.fechaHasta = fechaHasta;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Empresa getEmpresa()
/* 185:    */   {
/* 186:219 */     return this.empresa;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setEmpresa(Empresa empresa)
/* 190:    */   {
/* 191:223 */     this.empresa = empresa;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public EmpresaBean getEmpresaBean()
/* 195:    */   {
/* 196:227 */     return this.empresaBean;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setEmpresaBean(EmpresaBean empresaBean)
/* 200:    */   {
/* 201:231 */     this.empresaBean = empresaBean;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isIndicadorResumen()
/* 205:    */   {
/* 206:240 */     return this.indicadorResumen;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 210:    */   {
/* 211:250 */     this.indicadorResumen = indicadorResumen;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 215:    */   {
/* 216:254 */     return this.categoriaEmpresa;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 220:    */   {
/* 221:258 */     this.categoriaEmpresa = categoriaEmpresa;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 225:    */   {
/* 226:267 */     HashMap<String, String> filtros = new HashMap();
/* 227:268 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 228:269 */     if (this.listaCategoriaEmpresa == null)
/* 229:    */     {
/* 230:270 */       this.listaCategoriaEmpresa = new ArrayList();
/* 231:271 */       this.listaCategoriaEmpresa.add(new CategoriaEmpresa(-99001, CategoriaEmpresaEnum.TODOS_AGRUPADO.name(), "Todos Agrupado..."));
/* 232:272 */       this.listaCategoriaEmpresa.addAll(this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros));
/* 233:    */     }
/* 234:274 */     return this.listaCategoriaEmpresa;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 238:    */   {
/* 239:284 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 240:    */   }
/* 241:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReportePagoProveedorBean
 * JD-Core Version:    0.7.0.1
 */