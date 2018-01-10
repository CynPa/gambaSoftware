/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   5:    */ import com.asinfo.as2.entities.Departamento;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.TipoPermisoEmpleado;
/*  10:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioTipoPermisoEmpleado;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Calendar;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.RequestScoped;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @RequestScoped
/*  29:    */ public class ReportePermisosBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private Date fechaDesde;
/*  33:    */   private Date fechaHasta;
/*  34:    */   private Empleado empleado;
/*  35:    */   private Sucursal sucursal;
/*  36:    */   private Departamento departamento;
/*  37:    */   private TipoPermisoEmpleado tipoPermisoEmpleado;
/*  38:    */   private boolean indicadorResumen;
/*  39:    */   @EJB
/*  40:    */   private ServicioReporteNomina servicioReporteNomina;
/*  41:    */   @EJB
/*  42:    */   private ServicioSucursal servicioSucursal;
/*  43:    */   @EJB
/*  44:    */   private ServicioDepartamento servicioDepartamento;
/*  45:    */   @EJB
/*  46:    */   private ServicioTipoPermisoEmpleado servicioTipoPermisoEmpleado;
/*  47:    */   private List<Sucursal> listaSucursal;
/*  48:    */   private List<Departamento> listaDepartamento;
/*  49:    */   private List<TipoPermisoEmpleado> listaTipoPermisoEmpleado;
/*  50:    */   private static final long serialVersionUID = 1L;
/*  51:    */   
/*  52:    */   protected JRDataSource getJRDataSource()
/*  53:    */   {
/*  54: 70 */     int i = 0;
/*  55: 71 */     if (this.sucursal != null) {
/*  56: 72 */       i = getSucursal().getIdSucursal();
/*  57:    */     }
/*  58: 74 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  59: 75 */     JRDataSource ds = null;
/*  60:    */     try
/*  61:    */     {
/*  62: 78 */       listaDatosReporte = this.servicioReporteNomina.getReportePermisos(this.empleado, i, this.departamento, this.fechaDesde, this.fechaHasta, 
/*  63: 79 */         AppUtil.getOrganizacion().getIdOrganizacion(), this.tipoPermisoEmpleado);
/*  64: 80 */       String[] fields = { "f_sucursal", "f_fechaPermiso", "f_horas", "f_horaDesde", "f_horaHasta", "f_numeroPermiso", "f_nombres", "f_apellidos", "f_departamento", "f_tipoPermiso", "f_diaHasta", "f_nota", "f_estado", "f_numero", "f_codigoEmpleado", "f_identificacion" };
/*  65:    */       
/*  66:    */ 
/*  67: 83 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71: 86 */       LOG.info("Error " + e);
/*  72: 87 */       e.printStackTrace();
/*  73:    */     }
/*  74: 89 */     return ds;
/*  75:    */   }
/*  76:    */   
/*  77:    */   @PostConstruct
/*  78:    */   public void init()
/*  79:    */   {
/*  80: 95 */     Calendar calfechaDesde = Calendar.getInstance();
/*  81: 96 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  82: 97 */     this.fechaDesde = calfechaDesde.getTime();
/*  83: 98 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected String getCompileFileName()
/*  87:    */   {
/*  88:104 */     if (isIndicadorResumen()) {
/*  89:105 */       return "reportePermisoResumen";
/*  90:    */     }
/*  91:108 */     return "reportePermiso";
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected Map<String, Object> getReportParameters()
/*  95:    */   {
/*  96:114 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  97:115 */     reportParameters.put("ReportTitle", isIndicadorResumen() ? "Reporte Resumido Permisos" : "Reporte Detallado Permisos");
/*  98:116 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/*  99:117 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 100:    */     
/* 101:119 */     return reportParameters;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String execute()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:125 */       super.prepareReport();
/* 109:    */     }
/* 110:    */     catch (Exception e)
/* 111:    */     {
/* 112:127 */       e.printStackTrace();
/* 113:    */     }
/* 114:130 */     return null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String cargarEmpleado()
/* 118:    */   {
/* 119:134 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Empleado getEmpleado()
/* 123:    */   {
/* 124:139 */     return this.empleado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setEmpleado(Empleado empleado)
/* 128:    */   {
/* 129:143 */     this.empleado = empleado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Sucursal getSucursal()
/* 133:    */   {
/* 134:147 */     return this.sucursal;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setSucursal(Sucursal sucursal)
/* 138:    */   {
/* 139:151 */     this.sucursal = sucursal;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Departamento getDepartamento()
/* 143:    */   {
/* 144:155 */     return this.departamento;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDepartamento(Departamento departamento)
/* 148:    */   {
/* 149:159 */     this.departamento = departamento;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<Sucursal> getListaSucursal()
/* 153:    */   {
/* 154:163 */     if (this.listaSucursal == null) {
/* 155:164 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 156:    */     }
/* 157:166 */     return this.listaSucursal;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 161:    */   {
/* 162:170 */     this.listaSucursal = listaSucursal;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<Departamento> getListaDepartamento()
/* 166:    */   {
/* 167:174 */     if (this.listaDepartamento == null) {
/* 168:175 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 169:    */     }
/* 170:177 */     return this.listaDepartamento;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 174:    */   {
/* 175:181 */     this.listaDepartamento = listaDepartamento;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Date getFechaDesde()
/* 179:    */   {
/* 180:185 */     return this.fechaDesde;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setFechaDesde(Date fechaDesde)
/* 184:    */   {
/* 185:189 */     this.fechaDesde = fechaDesde;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Date getFechaHasta()
/* 189:    */   {
/* 190:193 */     return this.fechaHasta;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setFechaHasta(Date fechaHasta)
/* 194:    */   {
/* 195:197 */     this.fechaHasta = fechaHasta;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<TipoPermisoEmpleado> getListaTipoPermisoEmpleado()
/* 199:    */   {
/* 200:201 */     if (this.listaTipoPermisoEmpleado == null) {
/* 201:202 */       this.listaTipoPermisoEmpleado = this.servicioTipoPermisoEmpleado.obtenerListaCombo("nombre", true, null);
/* 202:    */     }
/* 203:204 */     return this.listaTipoPermisoEmpleado;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setListaTipoPermisoEmpleado(List<TipoPermisoEmpleado> listaTipoPermisoEmpleado)
/* 207:    */   {
/* 208:208 */     this.listaTipoPermisoEmpleado = listaTipoPermisoEmpleado;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public TipoPermisoEmpleado getTipoPermisoEmpleado()
/* 212:    */   {
/* 213:212 */     return this.tipoPermisoEmpleado;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setTipoPermisoEmpleado(TipoPermisoEmpleado tipoPermisoEmpleado)
/* 217:    */   {
/* 218:216 */     this.tipoPermisoEmpleado = tipoPermisoEmpleado;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean isIndicadorResumen()
/* 222:    */   {
/* 223:220 */     return this.indicadorResumen;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 227:    */   {
/* 228:224 */     this.indicadorResumen = indicadorResumen;
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReportePermisosBean
 * JD-Core Version:    0.7.0.1
 */