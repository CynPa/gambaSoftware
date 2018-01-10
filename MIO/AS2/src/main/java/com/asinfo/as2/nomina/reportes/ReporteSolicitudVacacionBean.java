/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.DetalleVacacion;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Vacacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioDetalleVacacion;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.RequestScoped;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @RequestScoped
/*  25:    */ public class ReporteSolicitudVacacionBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private ServicioDetalleVacacion servicioDetalleVacacion;
/*  31:    */   private DetalleVacacion detalleVacacion;
/*  32: 54 */   private final String COMPILE_FILE_NAME = "solicitudVacacion";
/*  33:    */   
/*  34:    */   protected JRDataSource getJRDataSource()
/*  35:    */   {
/*  36: 64 */     List listaDatosReporte = new ArrayList();
/*  37: 65 */     JRDataSource ds = null;
/*  38:    */     try
/*  39:    */     {
/*  40: 67 */       listaDatosReporte = this.servicioDetalleVacacion.getReporteSolicitudVacacion(this.detalleVacacion.getIdDetalleVacacion(), AppUtil.getOrganizacion()
/*  41: 68 */         .getId());
/*  42: 69 */       String[] fields = { "codigo", "identificacion", "nombreFiscal", "estadoCivil", "departamento", "cargoEmpleado", "titulo", "imagen", "fechaIngreso", "diasTomados", "fechaInicio", "fechFin", "periodoInicio", "periodoFin", "vdias", "vdiasAdicionales", "vdiasTomados", "descripcion", "f_numero", "f_usuarioCreacion", "f_usuarioModificacion", "f_apellidos", "f_nombres", "f_fechaCreacion", "f_anticipoVacacion", "f_aprobadoPor", "f_telefono1" };
/*  43:    */       
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 74 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 77 */       LOG.info("Error " + e);
/*  52: 78 */       e.printStackTrace();
/*  53:    */     }
/*  54: 80 */     return ds;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String getCompileFileName()
/*  58:    */   {
/*  59: 90 */     return "solicitudVacacion";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<String, Object> getReportParameters()
/*  63:    */   {
/*  64: 95 */     int diasDisponibles = 0;
/*  65: 96 */     int diasPorPeriodo = 0;
/*  66: 97 */     Date diaReintegrarse = null;
/*  67:    */     
/*  68: 99 */     this.detalleVacacion = this.servicioDetalleVacacion.cargarDetalle(this.detalleVacacion.getIdDetalleVacacion());
/*  69:100 */     List<Vacacion> listaVacaciones = this.servicioDetalleVacacion.obtenerListaVacacionesPorDetalle(this.detalleVacacion.getIdDetalleVacacion());
/*  70:101 */     String detalleDiasDisponibles = "";
/*  71:102 */     for (Vacacion vacacion : listaVacaciones)
/*  72:    */     {
/*  73:104 */       diasPorPeriodo = vacacion.getDias() + vacacion.getDiasAdicionales() - vacacion.getDiasTomados();
/*  74:106 */       if ((this.detalleVacacion.getVacacion().equals(vacacion)) && 
/*  75:107 */         (this.detalleVacacion.getEstado() != Estado.CERRADO) && (this.detalleVacacion.getEstado() != Estado.APROBADO)) {
/*  76:108 */         diasPorPeriodo -= this.detalleVacacion.getDiasTomados();
/*  77:    */       }
/*  78:110 */       diasDisponibles += diasPorPeriodo;
/*  79:111 */       if (diasPorPeriodo > 0) {
/*  80:113 */         detalleDiasDisponibles = detalleDiasDisponibles + "\n" + FuncionesUtiles.dateToString(vacacion.getFechaInicioPeriodo()) + "-" + FuncionesUtiles.dateToString(vacacion.getFechaFinPeriodo()) + "\t" + diasPorPeriodo;
/*  81:    */       }
/*  82:115 */       diaReintegrarse = FuncionesUtiles.sumarFechaDiasMeses(this.detalleVacacion.getFechaFin(), 1);
/*  83:    */     }
/*  84:118 */     detalleDiasDisponibles = detalleDiasDisponibles.length() > 0 ? detalleDiasDisponibles.substring(1) : "";
/*  85:119 */     Object reportParameters = super.getReportParameters();
/*  86:120 */     ((Map)reportParameters).put("ReportTitle", getLanguageController().getMensaje("msg_reporte_retencion_sri_titulo"));
/*  87:121 */     ((Map)reportParameters).put("p_diasDisponibles", Integer.valueOf(diasDisponibles));
/*  88:122 */     ((Map)reportParameters).put("p_detalleDiasDisponibles", detalleDiasDisponibles);
/*  89:123 */     ((Map)reportParameters).put("p_diaReintegrarse", diaReintegrarse);
/*  90:    */     
/*  91:125 */     return reportParameters;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String execute()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:135 */       super.prepareReport();
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:137 */       e.printStackTrace();
/* 103:    */     }
/* 104:140 */     return null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String cargarEmpleado()
/* 108:    */   {
/* 109:144 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getCOMPILE_FILE_NAME()
/* 113:    */   {
/* 114:153 */     return "solicitudVacacion";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public DetalleVacacion getDetalleVacacion()
/* 118:    */   {
/* 119:165 */     if (this.detalleVacacion == null) {
/* 120:166 */       this.detalleVacacion = new DetalleVacacion();
/* 121:    */     }
/* 122:168 */     return this.detalleVacacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDetalleVacacion(DetalleVacacion detalleVacacion)
/* 126:    */   {
/* 127:178 */     this.detalleVacacion = detalleVacacion;
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteSolicitudVacacionBean
 * JD-Core Version:    0.7.0.1
 */