/*   1:    */ package com.asinfo.as2.mantenimiento.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.TipoActividad;
/*  12:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  13:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioCalendarioMantenimiento;
/*  14:    */ import com.asinfo.as2.mantenimiento.procesos.controller.CalendarioMantenimientoBean;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Calendar;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.RequestScoped;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @RequestScoped
/*  34:    */ public class ReporteCalendarioMantenimientoBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -192264607856793455L;
/*  38:    */   @EJB
/*  39:    */   private ServicioCalendarioMantenimiento servicioCalendarioMantenimiento;
/*  40:    */   private Calendar fechaInicio;
/*  41: 52 */   private final String COMPILE_FILE_NAME = "reporteCalendarioMantenimiento";
/*  42: 53 */   private final String COMPILE_FILE_NAME_PARO = "reporteCalendarioParosMantenimiento";
/*  43:    */   private CalendarioMantenimientoBean calendarioMantenimientoBean;
/*  44: 57 */   private boolean indicadorReporteParo = false;
/*  45:    */   
/*  46:    */   protected JRDataSource getJRDataSource()
/*  47:    */   {
/*  48: 67 */     List listaDatosReporte = new ArrayList();
/*  49: 68 */     JRDataSource ds = null;
/*  50:    */     try
/*  51:    */     {
/*  52: 70 */       this.fechaInicio.set(5, 1);
/*  53: 71 */       Date fechaDesde = this.fechaInicio.getTime();
/*  54: 72 */       Date fechaHasta = FuncionesUtiles.getFechaFinMes(fechaDesde);
/*  55: 73 */       listaDatosReporte = this.servicioCalendarioMantenimiento.getReporteCalendarioMantenimiento(AppUtil.getOrganizacion().getId(), fechaDesde, fechaHasta, this.calendarioMantenimientoBean
/*  56: 74 */         .getCategoriaEquipo(), this.calendarioMantenimientoBean.getSubcategoriaEquipo(), this.calendarioMantenimientoBean
/*  57: 75 */         .getEquipo(), this.calendarioMantenimientoBean.getComponenteEquipo(), this.calendarioMantenimientoBean
/*  58: 76 */         .getTipoActividad(), this.calendarioMantenimientoBean.getActividad(), this.calendarioMantenimientoBean
/*  59: 77 */         .getUbicacion(), this.calendarioMantenimientoBean.isIndicadorSoloConParo(), this.indicadorReporteParo);
/*  60:    */       
/*  61: 79 */       String[] fields = { "f_equipo", "f_componente", "f_actividad", "f_dia", "f_valor" };
/*  62:    */       
/*  63: 81 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  64:    */     }
/*  65:    */     catch (Exception e)
/*  66:    */     {
/*  67: 83 */       LOG.info("Error " + e);
/*  68: 84 */       e.printStackTrace();
/*  69:    */     }
/*  70: 86 */     return ds;
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected String getCompileFileName()
/*  74:    */   {
/*  75: 96 */     if (this.indicadorReporteParo) {
/*  76: 97 */       return "reporteCalendarioParosMantenimiento";
/*  77:    */     }
/*  78: 99 */     return "reporteCalendarioMantenimiento";
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Map<String, Object> getReportParameters()
/*  82:    */   {
/*  83:110 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  84:111 */     if (this.indicadorReporteParo) {
/*  85:112 */       reportParameters.put("ReportTitle", "Calendario Paros por Mantenimiento");
/*  86:    */     } else {
/*  87:114 */       reportParameters.put("ReportTitle", "Calendario Mantenimiento");
/*  88:    */     }
/*  89:116 */     String mes = Mes.values()[this.fechaInicio.get(2)].getNombre();
/*  90:117 */     reportParameters.put("p_mes", mes);
/*  91:118 */     reportParameters.put("p_anio", Integer.valueOf(this.fechaInicio.get(1)));
/*  92:119 */     reportParameters.put("p_categoriaEquipo", this.calendarioMantenimientoBean.getCategoriaEquipo() == null ? "TODOS" : this.calendarioMantenimientoBean
/*  93:120 */       .getCategoriaEquipo().getNombre());
/*  94:121 */     reportParameters.put("p_subCategoriaEquipo", this.calendarioMantenimientoBean.getSubcategoriaEquipo() == null ? "TODOS" : this.calendarioMantenimientoBean
/*  95:122 */       .getSubcategoriaEquipo().getNombre());
/*  96:123 */     reportParameters.put("p_equipo", this.calendarioMantenimientoBean.getEquipo() == null ? "TODOS" : this.calendarioMantenimientoBean.getEquipo()
/*  97:124 */       .getNombre());
/*  98:125 */     reportParameters.put("p_componenteEquipo", this.calendarioMantenimientoBean.getComponenteEquipo() == null ? "TODOS" : this.calendarioMantenimientoBean
/*  99:126 */       .getComponenteEquipo().getNombre());
/* 100:127 */     reportParameters.put("p_tipoActividad", this.calendarioMantenimientoBean.getTipoActividad() == null ? "TODOS" : this.calendarioMantenimientoBean
/* 101:128 */       .getTipoActividad().getNombre());
/* 102:129 */     reportParameters.put("p_actividad", this.calendarioMantenimientoBean.getActividad() == null ? "TODOS" : this.calendarioMantenimientoBean.getActividad()
/* 103:130 */       .getNombre());
/* 104:131 */     reportParameters.put("p_ubicacion", this.calendarioMantenimientoBean.getUbicacion() == null ? "TODOS" : this.calendarioMantenimientoBean.getUbicacion()
/* 105:132 */       .getNombre());
/* 106:133 */     if (!this.indicadorReporteParo) {
/* 107:134 */       reportParameters.put("p_soloConParo", Boolean.valueOf(this.calendarioMantenimientoBean.isIndicadorSoloConParo()));
/* 108:    */     }
/* 109:136 */     return reportParameters;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String execute()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:145 */       super.prepareReport();
/* 117:    */     }
/* 118:    */     catch (JRException e)
/* 119:    */     {
/* 120:147 */       LOG.info("Error JRException");
/* 121:148 */       e.printStackTrace();
/* 122:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 123:    */     }
/* 124:    */     catch (IOException e)
/* 125:    */     {
/* 126:151 */       LOG.info("Error IOException");
/* 127:152 */       e.printStackTrace();
/* 128:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 129:    */     }
/* 130:155 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Calendar getFechaInicio()
/* 134:    */   {
/* 135:159 */     return this.fechaInicio;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setFechaInicio(Calendar fechaInicio)
/* 139:    */   {
/* 140:163 */     this.fechaInicio = fechaInicio;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public CalendarioMantenimientoBean getCalendarioMantenimientoBean()
/* 144:    */   {
/* 145:167 */     return this.calendarioMantenimientoBean;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setCalendarioMantenimientoBean(CalendarioMantenimientoBean calendarioMantenimientoBean)
/* 149:    */   {
/* 150:171 */     this.calendarioMantenimientoBean = calendarioMantenimientoBean;
/* 151:172 */     this.fechaInicio = calendarioMantenimientoBean.getFechaInicial();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isIndicadorReporteParo()
/* 155:    */   {
/* 156:176 */     return this.indicadorReporteParo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setIndicadorReporteParo(boolean indicadorReporteParo)
/* 160:    */   {
/* 161:180 */     this.indicadorReporteParo = indicadorReporteParo;
/* 162:    */   }
/* 163:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.reportes.ReporteCalendarioMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */