/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.MotivoLlamadoAtencion;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioLlamadoAtencion;
/*   9:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioMotivoLlamadoAtencion;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Calendar;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ReporteLlamadosAtencionBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private ServicioReporteNomina servicioReporteNomina;
/*  35:    */   @EJB
/*  36:    */   private ServicioDepartamento servicioDepartamento;
/*  37:    */   @EJB
/*  38:    */   private ServicioMotivoLlamadoAtencion servicioMotivoLlamadoAtencion;
/*  39:    */   @EJB
/*  40:    */   private ServicioLlamadoAtencion servicioLlamadoAtencion;
/*  41:    */   private Empleado empleado;
/*  42:    */   private Departamento departamento;
/*  43:    */   private MotivoLlamadoAtencion motivoLlamadoAtencion;
/*  44:    */   private Date fechaDesde;
/*  45:    */   private Date fechaHasta;
/*  46: 61 */   private final String COMPILE_FILE_NAME = "reporteLlamadosAtencion";
/*  47:    */   private List<MotivoLlamadoAtencion> listaMotivoLlamadoAtencion;
/*  48:    */   private List<Departamento> listaDepartamento;
/*  49:    */   
/*  50:    */   protected JRDataSource getJRDataSource()
/*  51:    */   {
/*  52: 74 */     List listaDatosReporte = new ArrayList();
/*  53: 75 */     JRDataSource ds = null;
/*  54: 76 */     String[] fields = null;
/*  55:    */     try
/*  56:    */     {
/*  57: 78 */       int idEmpleado = this.empleado == null ? 0 : this.empleado.getId();
/*  58: 79 */       int idDepartamento = this.departamento == null ? 0 : this.departamento.getId();
/*  59: 80 */       int idMotivoLlamadoAtencion = this.motivoLlamadoAtencion == null ? 0 : this.motivoLlamadoAtencion.getId();
/*  60: 81 */       listaDatosReporte = this.servicioLlamadoAtencion.reportellamadosAtencion(AppUtil.getOrganizacion().getId(), idEmpleado, idDepartamento, idMotivoLlamadoAtencion, this.fechaDesde, this.fechaHasta);
/*  61:    */       
/*  62: 83 */       fields = new String[] { "f_empleado", "f_motivo", "f_cantidad" };
/*  63:    */       
/*  64: 85 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  65:    */     }
/*  66:    */     catch (Exception e)
/*  67:    */     {
/*  68: 88 */       LOG.info("Error " + e);
/*  69: 89 */       e.printStackTrace();
/*  70:    */     }
/*  71: 91 */     return ds;
/*  72:    */   }
/*  73:    */   
/*  74:    */   @PostConstruct
/*  75:    */   public void init()
/*  76:    */   {
/*  77: 97 */     Calendar calfechaDesde = Calendar.getInstance();
/*  78: 98 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  79: 99 */     this.fechaDesde = calfechaDesde.getTime();
/*  80:100 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected String getCompileFileName()
/*  84:    */   {
/*  85:111 */     return "reporteLlamadosAtencion";
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected Map<String, Object> getReportParameters()
/*  89:    */   {
/*  90:117 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  91:118 */     reportParameters.put("ReportTitle", "Reporte de Llamados de Atención");
/*  92:    */     
/*  93:120 */     return reportParameters;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String execute()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:130 */       super.prepareReport();
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:132 */       e.printStackTrace();
/* 105:    */     }
/* 106:135 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String cargarEmpleado()
/* 110:    */   {
/* 111:139 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Empleado getEmpleado()
/* 115:    */   {
/* 116:148 */     if (this.empleado == null) {
/* 117:149 */       this.empleado = new Empleado();
/* 118:    */     }
/* 119:151 */     return this.empleado;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setEmpleado(Empleado empleado)
/* 123:    */   {
/* 124:161 */     this.empleado = empleado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getCOMPILE_FILE_NAME()
/* 128:    */   {
/* 129:170 */     return "reporteLlamadosAtencion";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Departamento getDepartamento()
/* 133:    */   {
/* 134:179 */     if (this.departamento == null) {
/* 135:180 */       this.departamento = new Departamento();
/* 136:    */     }
/* 137:182 */     return this.departamento;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setDepartamento(Departamento departamento)
/* 141:    */   {
/* 142:192 */     this.departamento = departamento;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<Departamento> getListaDepartamento()
/* 146:    */   {
/* 147:201 */     if (this.listaDepartamento == null) {
/* 148:202 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 149:    */     }
/* 150:204 */     return this.listaDepartamento;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public MotivoLlamadoAtencion getMotivoLlamadoAtencion()
/* 154:    */   {
/* 155:208 */     return this.motivoLlamadoAtencion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setMotivoLlamadoAtencion(MotivoLlamadoAtencion motivoLlamadoAtencion)
/* 159:    */   {
/* 160:212 */     this.motivoLlamadoAtencion = motivoLlamadoAtencion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<MotivoLlamadoAtencion> getListaMotivoLlamadoAtencion()
/* 164:    */   {
/* 165:216 */     HashMap<String, String> filters = new HashMap();
/* 166:217 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 167:218 */     filters.put("activo", "true");
/* 168:219 */     this.listaMotivoLlamadoAtencion = this.servicioMotivoLlamadoAtencion.obtenerListaCombo("nombre", true, filters);
/* 169:220 */     return this.listaMotivoLlamadoAtencion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setListaMotivoLlamadoAtencion(List<MotivoLlamadoAtencion> listaMotivoLlamadoAtencion)
/* 173:    */   {
/* 174:225 */     this.listaMotivoLlamadoAtencion = listaMotivoLlamadoAtencion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Date getFechaDesde()
/* 178:    */   {
/* 179:229 */     return this.fechaDesde;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setFechaDesde(Date fechaDesde)
/* 183:    */   {
/* 184:233 */     this.fechaDesde = fechaDesde;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Date getFechaHasta()
/* 188:    */   {
/* 189:237 */     return this.fechaHasta;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setFechaHasta(Date fechaHasta)
/* 193:    */   {
/* 194:241 */     this.fechaHasta = fechaHasta;
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteLlamadosAtencionBean
 * JD-Core Version:    0.7.0.1
 */