/*   1:    */ package com.asinfo.as2.seguridad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.seguridad.LogAuditoria;
/*   6:    */ import com.asinfo.as2.enumeraciones.ProcesoAuditoriaEnum;
/*   7:    */ import com.asinfo.as2.seguridad.ServicioLogAuditoria;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Calendar;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  23:    */ import net.sf.jasperreports.engine.JRException;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class LogAuditoriaBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 8619583228726473010L;
/*  32:    */   @EJB
/*  33:    */   private ServicioLogAuditoria servicioLogAuditoria;
/*  34:    */   private List<LogAuditoria> listaLogAuditoria;
/*  35:    */   private Map<String, String> filtros;
/*  36:    */   private String entidad;
/*  37:    */   private String nombreUsuario;
/*  38:    */   private Date fechaDesde;
/*  39:    */   private Date fechaHasta;
/*  40:    */   
/*  41:    */   private void cargarFiltros()
/*  42:    */   {
/*  43: 65 */     this.filtros = new HashMap();
/*  44: 66 */     if (!this.entidad.isEmpty()) {
/*  45: 67 */       this.filtros.put("claseEntidad", this.entidad);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void procesar()
/*  50:    */   {
/*  51: 72 */     crearLogAuditoria(ProcesoAuditoriaEnum.CONSULTAR_AUDITORIA, "Consultar auditoria");
/*  52: 73 */     this.listaLogAuditoria = this.servicioLogAuditoria.obtenerAuditoriaFiltrado(this.nombreUsuario, this.entidad, this.fechaDesde, this.fechaHasta, AppUtil.getOrganizacion()
/*  53: 74 */       .getIdOrganizacion());
/*  54:    */   }
/*  55:    */   
/*  56:    */   @PostConstruct
/*  57:    */   public void init() {}
/*  58:    */   
/*  59:    */   protected JRDataSource getJRDataSource()
/*  60:    */   {
/*  61: 85 */     Calendar cal = Calendar.getInstance();
/*  62: 86 */     cal.setTime(this.fechaHasta);
/*  63: 87 */     cal.set(11, 23);
/*  64: 88 */     cal.set(12, 59);
/*  65: 89 */     cal.set(13, 59);
/*  66: 90 */     cal.set(14, 999);
/*  67:    */     
/*  68: 92 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  69: 93 */     JRDataSource ds = null;
/*  70:    */     
/*  71:    */ 
/*  72: 96 */     listaDatosReporte = this.servicioLogAuditoria.listaReporteLogAuditoria(this.fechaDesde, cal.getTime(), this.nombreUsuario, this.entidad, AppUtil.getOrganizacion()
/*  73: 97 */       .getIdOrganizacion());
/*  74:    */     
/*  75: 99 */     String[] fields = { "f_claseEntidad", "f_fechaCreacion", "f_mensaje", "f_nombreUsuario", "f_hostRemoto" };
/*  76:    */     
/*  77:101 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  78:    */     
/*  79:103 */     return ds;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected String getCompileFileName()
/*  83:    */   {
/*  84:108 */     return "reporteLogAuditoria";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String execute()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:114 */       super.prepareReport();
/*  92:    */     }
/*  93:    */     catch (JRException e)
/*  94:    */     {
/*  95:116 */       LOG.info("Error JRException");
/*  96:117 */       e.printStackTrace();
/*  97:118 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  98:    */     }
/*  99:    */     catch (IOException e)
/* 100:    */     {
/* 101:120 */       LOG.info("Error IOException");
/* 102:121 */       e.printStackTrace();
/* 103:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 104:    */     }
/* 105:125 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected Map<String, Object> getReportParameters()
/* 109:    */   {
/* 110:131 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 111:132 */     reportParameters.put("ReportTitle", "Auditoria " + this.entidad);
/* 112:133 */     reportParameters.put("fechaDesde", this.fechaDesde);
/* 113:134 */     reportParameters.put("fechaHasta", this.fechaHasta);
/* 114:135 */     reportParameters.put("usuario", this.nombreUsuario);
/* 115:136 */     reportParameters.put("proceso", this.entidad);
/* 116:137 */     return reportParameters;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<LogAuditoria> getListaLogAuditoria()
/* 120:    */   {
/* 121:146 */     return this.listaLogAuditoria;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setListaLogAuditoria(List<LogAuditoria> listaLogAuditoria)
/* 125:    */   {
/* 126:156 */     this.listaLogAuditoria = listaLogAuditoria;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getEntidad()
/* 130:    */   {
/* 131:160 */     return this.entidad;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setEntidad(String entidad)
/* 135:    */   {
/* 136:164 */     this.entidad = entidad;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getNombreUsuario()
/* 140:    */   {
/* 141:168 */     return this.nombreUsuario;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setNombreUsuario(String nombreUsuario)
/* 145:    */   {
/* 146:172 */     this.nombreUsuario = nombreUsuario;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Date getFechaDesde()
/* 150:    */   {
/* 151:176 */     return this.fechaDesde;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setFechaDesde(Date fechaDesde)
/* 155:    */   {
/* 156:180 */     this.fechaDesde = fechaDesde;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Date getFechaHasta()
/* 160:    */   {
/* 161:184 */     return this.fechaHasta;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setFechaHasta(Date fechaHasta)
/* 165:    */   {
/* 166:188 */     this.fechaHasta = fechaHasta;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.reportes.controller.LogAuditoriaBean
 * JD-Core Version:    0.7.0.1
 */