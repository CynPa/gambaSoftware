/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   6:    */ import com.asinfo.as2.entities.Departamento;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*   9:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoDiscapacidad;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.RequestScoped;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import net.sf.jasperreports.engine.JRException;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @RequestScoped
/*  26:    */ public class ReporteDiscapacidadEmpleadoBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 3384335625906695746L;
/*  30:    */   @EJB
/*  31:    */   private transient ServicioSucursal servicioSucursal;
/*  32:    */   @EJB
/*  33:    */   private transient ServicioTipoDiscapacidad servicioTipoDiscapacidad;
/*  34:    */   @EJB
/*  35:    */   private transient ServicioDepartamento servicioDepartamento;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioReporteNomina servicioReporteNomina;
/*  38:    */   private Sucursal sucursal;
/*  39:    */   private TipoDiscapacidad tipoDiscapacidad;
/*  40:    */   private Departamento departamento;
/*  41:    */   private List<Sucursal> listaSucursal;
/*  42:    */   private List<TipoDiscapacidad> listaTipoDiscapacidad;
/*  43:    */   private List<Departamento> listaDepartamento;
/*  44:    */   private String COMPILE_FILE_NAME;
/*  45:    */   private boolean indicadorConCargas;
/*  46:    */   
/*  47:    */   protected JRDataSource getJRDataSource()
/*  48:    */   {
/*  49: 77 */     List listaDatosReporte = new ArrayList();
/*  50:    */     
/*  51: 79 */     JRDataSource ds = null;
/*  52: 80 */     String[] fields = null;
/*  53: 81 */     if (this.indicadorConCargas)
/*  54:    */     {
/*  55: 82 */       listaDatosReporte = this.servicioReporteNomina.getReporteDiscapacidadCargaEmpleado(this.sucursal, this.tipoDiscapacidad, this.departamento, AppUtil.getOrganizacion());
/*  56: 83 */       fields = new String[] { "f_codigo", "f_identificacion", "f_apellidoEmpleado", "f_nombreEmpleado", "f_cargo", "f_departamente", "f_discapacidad", "f_porcentajeDiscapacidad", "f_nombreCarga", "f_discapacidadCarga", "f_porcentajeDiscapacidadCarga", "f_identificacionCarga" };
/*  57:    */     }
/*  58:    */     else
/*  59:    */     {
/*  60: 87 */       listaDatosReporte = this.servicioReporteNomina.getReporteDiscapacidad(this.sucursal, this.tipoDiscapacidad, this.departamento, AppUtil.getOrganizacion());
/*  61: 88 */       fields = new String[] { "f_codigo", "f_identificacion", "f_apellidoEmpleado", "f_nombreEmpleado", "f_cargo", "f_departamente", "f_discapacidad", "f_porcentajeDiscapacidad" };
/*  62:    */     }
/*  63: 92 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  64: 93 */     return ds;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected String getCompileFileName()
/*  68:    */   {
/*  69:103 */     if (this.indicadorConCargas) {
/*  70:104 */       this.COMPILE_FILE_NAME = "reporteDiscapacidadEmpleadoCarga";
/*  71:    */     } else {
/*  72:106 */       this.COMPILE_FILE_NAME = "reporteDiscapacidadEmpleado";
/*  73:    */     }
/*  74:109 */     return this.COMPILE_FILE_NAME;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String execute()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:120 */       super.prepareReport();
/*  82:    */     }
/*  83:    */     catch (JRException e)
/*  84:    */     {
/*  85:122 */       LOG.info("Error JRException");
/*  86:123 */       e.printStackTrace();
/*  87:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  88:    */     }
/*  89:    */     catch (IOException e)
/*  90:    */     {
/*  91:126 */       LOG.info("Error IOException");
/*  92:127 */       e.printStackTrace();
/*  93:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  94:    */     }
/*  95:131 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected Map<String, Object> getReportParameters()
/*  99:    */   {
/* 100:141 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 101:142 */     reportParameters.put("ReportTitle", "Reporte Discapacidad");
/* 102:143 */     if (this.sucursal == null) {
/* 103:144 */       reportParameters.put("sucursal", "Todos");
/* 104:    */     } else {
/* 105:146 */       reportParameters.put("sucursal", this.sucursal.getNombre());
/* 106:    */     }
/* 107:148 */     if (this.tipoDiscapacidad == null) {
/* 108:149 */       reportParameters.put("tipoDiscapacidad", "Todos");
/* 109:    */     } else {
/* 110:151 */       reportParameters.put("tipoDiscapacidad", this.tipoDiscapacidad.getNombre());
/* 111:    */     }
/* 112:153 */     return reportParameters;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Sucursal getSucursal()
/* 116:    */   {
/* 117:162 */     if (this.sucursal == null) {
/* 118:163 */       this.sucursal = new Sucursal();
/* 119:    */     }
/* 120:165 */     return this.sucursal;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setSucursal(Sucursal sucursal)
/* 124:    */   {
/* 125:175 */     this.sucursal = sucursal;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public TipoDiscapacidad getTipoDiscapacidad()
/* 129:    */   {
/* 130:184 */     if (this.tipoDiscapacidad == null) {
/* 131:185 */       this.tipoDiscapacidad = new TipoDiscapacidad();
/* 132:    */     }
/* 133:187 */     return this.tipoDiscapacidad;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setTipoDiscapacidad(TipoDiscapacidad tipoDiscapacidad)
/* 137:    */   {
/* 138:197 */     this.tipoDiscapacidad = tipoDiscapacidad;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<Sucursal> getListaSucursal()
/* 142:    */   {
/* 143:206 */     if (this.listaSucursal == null) {
/* 144:207 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 145:    */     }
/* 146:209 */     return this.listaSucursal;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 150:    */   {
/* 151:219 */     this.listaSucursal = listaSucursal;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<TipoDiscapacidad> getListaTipoDiscapacidad()
/* 155:    */   {
/* 156:228 */     if (this.listaTipoDiscapacidad == null) {
/* 157:229 */       this.listaTipoDiscapacidad = this.servicioTipoDiscapacidad.obtenerListaCombo("nombre", true, null);
/* 158:    */     }
/* 159:231 */     return this.listaTipoDiscapacidad;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaTipoDiscapacidad(List<TipoDiscapacidad> listaTipoDiscapacidad)
/* 163:    */   {
/* 164:241 */     this.listaTipoDiscapacidad = listaTipoDiscapacidad;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean isIndicadorConCargas()
/* 168:    */   {
/* 169:250 */     return this.indicadorConCargas;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setIndicadorConCargas(boolean indicadorConCargas)
/* 173:    */   {
/* 174:260 */     this.indicadorConCargas = indicadorConCargas;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Departamento getDepartamento()
/* 178:    */   {
/* 179:269 */     if (this.departamento == null) {
/* 180:270 */       this.departamento = new Departamento();
/* 181:    */     }
/* 182:272 */     return this.departamento;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setDepartamento(Departamento departamento)
/* 186:    */   {
/* 187:282 */     this.departamento = departamento;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List<Departamento> getListaDepartamento()
/* 191:    */   {
/* 192:291 */     if (this.listaDepartamento == null) {
/* 193:292 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 194:    */     }
/* 195:294 */     return this.listaDepartamento;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 199:    */   {
/* 200:304 */     this.listaDepartamento = listaDepartamento;
/* 201:    */   }
/* 202:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteDiscapacidadEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */