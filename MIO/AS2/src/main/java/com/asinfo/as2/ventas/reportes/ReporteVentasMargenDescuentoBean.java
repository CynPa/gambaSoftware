/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.controller.EmpresaBean;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Calendar;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ManagedProperty;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReporteVentasMargenDescuentoBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReporteVenta servicioReporteVenta;
/*  37:    */   private Empresa empresa;
/*  38:    */   private Date fechaDesde;
/*  39:    */   private Date fechaHasta;
/*  40:    */   private BigDecimal porcentaje;
/*  41: 48 */   private final String COMPILE_FILE_NAME = "reporteVentasMargenDescuento";
/*  42:    */   @ManagedProperty("#{empresaBean}")
/*  43:    */   private EmpresaBean empresaBean;
/*  44:    */   
/*  45:    */   protected JRDataSource getJRDataSource()
/*  46:    */   {
/*  47: 61 */     List listaDatosReporte = new ArrayList();
/*  48: 62 */     JRDataSource ds = null;
/*  49:    */     try
/*  50:    */     {
/*  51: 64 */       validaDatos();
/*  52: 65 */       listaDatosReporte = this.servicioReporteVenta.getListaReporteVentasMargenDescuento(this.fechaDesde, this.fechaHasta, this.empresa.getIdEmpresa(), this.porcentaje, 
/*  53: 66 */         AppUtil.getOrganizacion().getId());
/*  54:    */       
/*  55: 68 */       String[] fields = { "f_factura", "f_fecha", "f_cliente", "f_descuento", "f_cantidad", "f_codigo", "f_producto", "f_volumen", "f_peso" };
/*  56:    */       
/*  57:    */ 
/*  58: 71 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  59:    */     }
/*  60:    */     catch (ExcepcionAS2 e)
/*  61:    */     {
/*  62: 73 */       LOG.info("Error " + e);
/*  63: 74 */       e.printStackTrace();
/*  64:    */     }
/*  65: 76 */     return ds;
/*  66:    */   }
/*  67:    */   
/*  68:    */   @PostConstruct
/*  69:    */   public void init()
/*  70:    */   {
/*  71: 81 */     Calendar calfechaDesde = Calendar.getInstance();
/*  72: 82 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  73: 83 */     this.fechaDesde = calfechaDesde.getTime();
/*  74: 84 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected String getCompileFileName()
/*  78:    */   {
/*  79: 94 */     return "reporteVentasMargenDescuento";
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected Map<String, Object> getReportParameters()
/*  83:    */   {
/*  84:104 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  85:105 */     reportParameters.put("ReportTitle", "Ventas por Margen de Descuento");
/*  86:106 */     reportParameters.put("usuario", "Usuario:");
/*  87:107 */     reportParameters.put("fechaHora", "Fecha y Hora:");
/*  88:108 */     reportParameters.put("pagina", "Pagina:");
/*  89:109 */     reportParameters.put("desde", "Desde:");
/*  90:110 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(getFechaDesde()));
/*  91:111 */     reportParameters.put("hasta", "Hasta");
/*  92:112 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(getFechaHasta()));
/*  93:113 */     reportParameters.put("p_cliente", "Cliente");
/*  94:114 */     reportParameters.put("p_factura", "Factura ");
/*  95:115 */     reportParameters.put("p_fecha", "Fecha ");
/*  96:116 */     reportParameters.put("p_producto", "Producto");
/*  97:117 */     reportParameters.put("p_codigo", "Codigo");
/*  98:118 */     reportParameters.put("p_descuento", "Descuento");
/*  99:119 */     reportParameters.put("p_cantidad", "Cantidad");
/* 100:120 */     reportParameters.put("p_peso", "Peso ");
/* 101:121 */     reportParameters.put("p_volumen", "Volumen");
/* 102:122 */     reportParameters.put("p_pesoTotal", "Peso Total");
/* 103:123 */     reportParameters.put("p_volumenTotal", "Volumen Total");
/* 104:124 */     return reportParameters;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String execute()
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:134 */       super.prepareReport();
/* 112:    */     }
/* 113:    */     catch (JRException e)
/* 114:    */     {
/* 115:136 */       LOG.info("Error JRException");
/* 116:137 */       e.printStackTrace();
/* 117:138 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 118:    */     }
/* 119:    */     catch (IOException e)
/* 120:    */     {
/* 121:140 */       LOG.info("Error IOException");
/* 122:141 */       e.printStackTrace();
/* 123:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 124:    */     }
/* 125:144 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void validaDatos()
/* 129:    */   {
/* 130:148 */     if (this.empresa == null)
/* 131:    */     {
/* 132:149 */       this.empresa = new Empresa();
/* 133:150 */       this.empresa.setId(0);
/* 134:    */     }
/* 135:152 */     if (this.fechaDesde == null) {
/* 136:153 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 137:    */     }
/* 138:155 */     if (this.fechaHasta == null) {
/* 139:156 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 140:    */     }
/* 141:158 */     if (this.porcentaje == null) {
/* 142:159 */       this.porcentaje = BigDecimal.ZERO;
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Date getFechaDesde()
/* 147:    */   {
/* 148:164 */     return this.fechaDesde;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setFechaDesde(Date fechaDesde)
/* 152:    */   {
/* 153:168 */     this.fechaDesde = fechaDesde;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Date getFechaHasta()
/* 157:    */   {
/* 158:172 */     return this.fechaHasta;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setFechaHasta(Date fechaHasta)
/* 162:    */   {
/* 163:176 */     this.fechaHasta = fechaHasta;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Empresa getEmpresa()
/* 167:    */   {
/* 168:180 */     return this.empresa;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setEmpresa(Empresa empresa)
/* 172:    */   {
/* 173:184 */     this.empresa = empresa;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public EmpresaBean getEmpresaBean()
/* 177:    */   {
/* 178:188 */     return this.empresaBean;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setEmpresaBean(EmpresaBean empresaBean)
/* 182:    */   {
/* 183:192 */     this.empresaBean = empresaBean;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public BigDecimal getPorcentaje()
/* 187:    */   {
/* 188:196 */     return this.porcentaje;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 192:    */   {
/* 193:200 */     this.porcentaje = porcentaje;
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteVentasMargenDescuentoBean
 * JD-Core Version:    0.7.0.1
 */