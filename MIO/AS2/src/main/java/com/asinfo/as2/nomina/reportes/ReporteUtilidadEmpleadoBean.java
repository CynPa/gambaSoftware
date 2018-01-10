/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   6:    */ import com.asinfo.as2.entities.Departamento;
/*   7:    */ import com.asinfo.as2.entities.Empleado;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.Utilidad;
/*  11:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioUtilidad;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Calendar;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ReporteUtilidadEmpleadoBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private ServicioReporteNomina servicioReporteNomina;
/*  35:    */   @EJB
/*  36:    */   private ServicioDepartamento servicioDepartamento;
/*  37:    */   @EJB
/*  38:    */   private ServicioSucursal servicioSucursal;
/*  39:    */   @EJB
/*  40:    */   private ServicioUtilidad servicioUtilidad;
/*  41:    */   private Sucursal sucursal;
/*  42:    */   private Departamento departamento;
/*  43:    */   private Empleado empleado;
/*  44:    */   private Utilidad utilidad;
/*  45: 71 */   private Calendar c1 = Calendar.getInstance();
/*  46: 72 */   private int anio = this.c1.get(1) - 1;
/*  47:    */   private List<Sucursal> listaSucursal;
/*  48:    */   private List<Departamento> listaDepartamento;
/*  49:    */   
/*  50:    */   protected JRDataSource getJRDataSource()
/*  51:    */   {
/*  52: 93 */     List listaDatosReporte = new ArrayList();
/*  53: 94 */     JRDataSource ds = null;
/*  54: 96 */     if (getUtilidad().getId() != 0)
/*  55:    */     {
/*  56: 98 */       listaDatosReporte = this.servicioReporteNomina.getReporteUtilidadEmpleado(getSucursal().getId(), getEmpleado().getId(), getUtilidad().getId(), 
/*  57: 99 */         getDepartamento().getId());
/*  58:    */       
/*  59:101 */       String[] fields = { "f_empleado", "f_identificacion", "f_carga", "f_diasTrabajados", "f_valor10", "f_valor5", "f_valorUtilidad", "f_diasRealesTrabajados", "f_retencionJudicial", "f_idEmpleado" };
/*  60:    */       
/*  61:103 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  62:    */     }
/*  63:106 */     return ds;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected Map<String, Object> getReportParameters()
/*  67:    */   {
/*  68:117 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  69:118 */     reportParameters.put("ReportTitle", "Reporte Utilidad");
/*  70:119 */     reportParameters.put("Anio", Integer.valueOf(getUtilidad() == null ? getAnio() : getUtilidad().getAnio()));
/*  71:120 */     reportParameters.put("Sucursal", getSucursal().getNombre());
/*  72:121 */     return reportParameters;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String execute()
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79:131 */       super.prepareReport();
/*  80:    */     }
/*  81:    */     catch (JRException e)
/*  82:    */     {
/*  83:133 */       LOG.info("Error JRException");
/*  84:134 */       e.printStackTrace();
/*  85:135 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  86:    */     }
/*  87:    */     catch (IOException e)
/*  88:    */     {
/*  89:137 */       LOG.info("Error IOException");
/*  90:138 */       e.printStackTrace();
/*  91:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  92:    */     }
/*  93:142 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected String getCompileFileName()
/*  97:    */   {
/*  98:152 */     return "reporteUtilidadEmpleado";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String cargarEmpleado()
/* 102:    */   {
/* 103:159 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void verificarUtilidad()
/* 107:    */   {
/* 108:163 */     Utilidad utilidad = this.servicioUtilidad.obtenerUtilidadPorAnio(AppUtil.getOrganizacion().getId(), getAnio());
/* 109:164 */     if (utilidad == null) {
/* 110:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_utilidad_anio"));
/* 111:    */     } else {
/* 112:167 */       this.utilidad = utilidad;
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Sucursal getSucursal()
/* 117:    */   {
/* 118:181 */     if (this.sucursal == null) {
/* 119:182 */       this.sucursal = new Sucursal();
/* 120:    */     }
/* 121:184 */     return this.sucursal;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setSucursal(Sucursal sucursal)
/* 125:    */   {
/* 126:194 */     this.sucursal = sucursal;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Departamento getDepartamento()
/* 130:    */   {
/* 131:203 */     if (this.departamento == null) {
/* 132:204 */       this.departamento = new Departamento();
/* 133:    */     }
/* 134:206 */     return this.departamento;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDepartamento(Departamento departamento)
/* 138:    */   {
/* 139:216 */     this.departamento = departamento;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Empleado getEmpleado()
/* 143:    */   {
/* 144:225 */     if (this.empleado == null) {
/* 145:226 */       this.empleado = new Empleado();
/* 146:    */     }
/* 147:228 */     return this.empleado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setEmpleado(Empleado empleado)
/* 151:    */   {
/* 152:238 */     this.empleado = empleado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Utilidad getUtilidad()
/* 156:    */   {
/* 157:247 */     if (this.utilidad == null) {
/* 158:248 */       this.utilidad = new Utilidad();
/* 159:    */     }
/* 160:250 */     return this.utilidad;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setUtilidad(Utilidad utilidad)
/* 164:    */   {
/* 165:260 */     this.utilidad = utilidad;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getAnio()
/* 169:    */   {
/* 170:269 */     return this.anio;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setAnio(int anio)
/* 174:    */   {
/* 175:279 */     this.anio = anio;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<Sucursal> getListaSucursal()
/* 179:    */   {
/* 180:288 */     if (this.listaSucursal == null) {
/* 181:289 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 182:    */     }
/* 183:291 */     return this.listaSucursal;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<Departamento> getListaDepartamento()
/* 187:    */   {
/* 188:300 */     if (this.listaDepartamento == null) {
/* 189:301 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 190:    */     }
/* 191:303 */     return this.listaDepartamento;
/* 192:    */   }
/* 193:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteUtilidadEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */