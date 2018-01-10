/*   1:    */ package com.asinfo.as2.nomina.asistencia.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Calendar;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.RequestScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @RequestScoped
/*  22:    */ public class ReporteHorarioPersonalizadoBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private Date fechaDesde;
/*  26:    */   private Date fechaHasta;
/*  27:    */   private Departamento departamento;
/*  28:    */   @EJB
/*  29:    */   private ServicioReporteAsistencia servicioReporteAsistencia;
/*  30:    */   @EJB
/*  31:    */   private ServicioDepartamento servicioDepartamento;
/*  32:    */   private List<Departamento> listaDepartamento;
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   
/*  35:    */   protected JRDataSource getJRDataSource()
/*  36:    */   {
/*  37: 61 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  38: 62 */     JRDataSource ds = null;
/*  39:    */     try
/*  40:    */     {
/*  41: 65 */       listaDatosReporte = this.servicioReporteAsistencia.getReporteHorarioPersonalizado(this.departamento, this.fechaDesde, this.fechaHasta);
/*  42: 66 */       String[] fields = { "f_nombreCompletoEmpleado", "f_fecha", "f_turno" };
/*  43:    */       
/*  44: 68 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  45:    */     }
/*  46:    */     catch (Exception e)
/*  47:    */     {
/*  48: 71 */       LOG.info("Error " + e);
/*  49: 72 */       e.printStackTrace();
/*  50:    */     }
/*  51: 74 */     return ds;
/*  52:    */   }
/*  53:    */   
/*  54:    */   @PostConstruct
/*  55:    */   public void init()
/*  56:    */   {
/*  57: 79 */     Calendar calfechaDesde = Calendar.getInstance();
/*  58: 80 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  59: 81 */     this.fechaDesde = calfechaDesde.getTime();
/*  60: 82 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected String getCompileFileName()
/*  64:    */   {
/*  65: 87 */     return "reporteHorarioPersonalizado";
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected Map<String, Object> getReportParameters()
/*  69:    */   {
/*  70: 93 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  71: 94 */     reportParameters.put("ReportTitle", "Reporte Horario Personalizado");
/*  72: 95 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/*  73: 96 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/*  74: 97 */     reportParameters.put("p_departamento", getDepartamento() != null ? getDepartamento().getNombre() : "Todos");
/*  75: 98 */     return reportParameters;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String execute()
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:104 */       super.prepareReport();
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:106 */       e.printStackTrace();
/*  87:    */     }
/*  88:109 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String cargarEmpleado()
/*  92:    */   {
/*  93:113 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Departamento getDepartamento()
/*  97:    */   {
/*  98:118 */     return this.departamento;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDepartamento(Departamento departamento)
/* 102:    */   {
/* 103:122 */     this.departamento = departamento;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<Departamento> getListaDepartamento()
/* 107:    */   {
/* 108:126 */     if (this.listaDepartamento == null) {
/* 109:127 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 110:    */     }
/* 111:129 */     return this.listaDepartamento;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 115:    */   {
/* 116:133 */     this.listaDepartamento = listaDepartamento;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Date getFechaDesde()
/* 120:    */   {
/* 121:137 */     return this.fechaDesde;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setFechaDesde(Date fechaDesde)
/* 125:    */   {
/* 126:141 */     this.fechaDesde = fechaDesde;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Date getFechaHasta()
/* 130:    */   {
/* 131:145 */     return this.fechaHasta;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setFechaHasta(Date fechaHasta)
/* 135:    */   {
/* 136:149 */     this.fechaHasta = fechaHasta;
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.reportes.ReporteHorarioPersonalizadoBean
 * JD-Core Version:    0.7.0.1
 */