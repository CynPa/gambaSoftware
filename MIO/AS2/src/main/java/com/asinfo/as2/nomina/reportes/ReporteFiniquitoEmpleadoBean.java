/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CargoEmpleado;
/*   5:    */ import com.asinfo.as2.entities.CausaSalidaEmpleado;
/*   6:    */ import com.asinfo.as2.entities.Departamento;
/*   7:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.PagoRol;
/*  13:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.entities.TipoContrato;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.enumeraciones.Genero;
/*  18:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  19:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  31:    */ import net.sf.jasperreports.engine.JRException;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ReporteFiniquitoEmpleadoBean
/*  37:    */   extends AbstractBaseReportBean
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -2187850619853994825L;
/*  40:    */   @EJB
/*  41:    */   private ServicioReporteNomina servicioReporteNomina;
/*  42:    */   @EJB
/*  43:    */   private ServicioPagoRol servicioPagoRol;
/*  44:    */   @EJB
/*  45:    */   private ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  46:    */   private HistoricoEmpleado historicoEmpleado;
/*  47: 56 */   private String COMPILE_FILE_NAME = "reporteFiniquitoEmpleado";
/*  48:    */   
/*  49:    */   protected JRDataSource getJRDataSource()
/*  50:    */   {
/*  51: 66 */     List listaDatosReporte = new ArrayList();
/*  52: 67 */     JRDataSource ds = null;
/*  53: 68 */     listaDatosReporte = this.servicioReporteNomina.getPagoRolEmpleadoRubro(this.historicoEmpleado, getEmpleado(), AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal());
/*  54:    */     
/*  55: 70 */     String[] fields = { "nombreRubro", "valorRubro", "operacion", "provision" };
/*  56: 71 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  57: 72 */     return ds;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected Map<String, Object> getReportParameters()
/*  61:    */   {
/*  62: 83 */     HistoricoEmpleado he = this.servicioHistoricoEmpleado.cargarDetalle(this.historicoEmpleado.getIdHistoricoEmpleado(), true);
/*  63:    */     
/*  64: 85 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  65: 86 */     reportParameters.put("ReportTitle", "Resumen Finiquito");
/*  66: 87 */     reportParameters.put("p_fechaSalida", he.getFechaSalida());
/*  67: 88 */     reportParameters.put("p_fechaIngreso", he.getFechaIngreso());
/*  68: 89 */     reportParameters.put("p_cargoEmpleado", he.getEmpleado().getCargoEmpleado().getNombre());
/*  69: 90 */     reportParameters.put("p_identificacion", he.getEmpleado().getEmpresa().getIdentificacion());
/*  70: 91 */     reportParameters.put("p_causaSalida", he.getCausaSalidaEmpleado().getNombre());
/*  71: 92 */     reportParameters.put("departamento", he.getEmpleado().getDepartamento().getNombre());
/*  72: 93 */     reportParameters.put("fecha_nacimiento", he.getEmpleado().getFechaNacimiento());
/*  73: 94 */     reportParameters.put("genero", he.getEmpleado().getGenero().getNombre());
/*  74: 95 */     reportParameters.put("direccion", ((DireccionEmpresa)he.getEmpleado().getEmpresa().getDirecciones().get(0)).getDireccionCompleta());
/*  75: 96 */     reportParameters.put("tipo_contrato", he.getEmpleado().getTipoContrato().getNombre());
/*  76: 97 */     reportParameters.put("correo", he.getEmpleado().getEmpresa().getEmail1());
/*  77: 98 */     reportParameters.put("nombreEmpleado", he.getEmpleado().getNombres());
/*  78: 99 */     reportParameters.put("apellidosEmpleado", he.getEmpleado().getApellidos());
/*  79:    */     
/*  80:101 */     return reportParameters;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String execute()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:110 */       super.prepareReport();
/*  88:    */     }
/*  89:    */     catch (JRException e)
/*  90:    */     {
/*  91:113 */       LOG.info("Error JRException");
/*  92:114 */       e.printStackTrace();
/*  93:115 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  94:    */     }
/*  95:    */     catch (IOException e)
/*  96:    */     {
/*  97:117 */       LOG.info("Error IOException");
/*  98:118 */       e.printStackTrace();
/*  99:119 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 100:    */     }
/* 101:122 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected String getCompileFileName()
/* 105:    */   {
/* 106:133 */     if (this.historicoEmpleado.getEstadoFiniquito().getNombre() == "Elaborado") {
/* 107:134 */       return this.COMPILE_FILE_NAME;
/* 108:    */     }
/* 109:136 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Empleado getEmpleado()
/* 113:    */   {
/* 114:147 */     Empleado empleado = new Empleado();
/* 115:148 */     if (this.historicoEmpleado != null) {
/* 116:149 */       empleado = this.historicoEmpleado.getEmpleado();
/* 117:    */     }
/* 118:151 */     return empleado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public PagoRol getPagoRol()
/* 122:    */   {
/* 123:160 */     PagoRol pagoRol = new PagoRol();
/* 124:161 */     if (this.historicoEmpleado != null) {
/* 125:162 */       pagoRol = this.historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol();
/* 126:    */     }
/* 127:164 */     return pagoRol;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 131:    */   {
/* 132:171 */     return this.historicoEmpleado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 136:    */   {
/* 137:178 */     this.historicoEmpleado = historicoEmpleado;
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteFiniquitoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */