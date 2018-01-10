/*   1:    */ package com.asinfo.as2.mantenimiento.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*   5:    */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.RequestScoped;
/*  14:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  15:    */ import net.sf.jasperreports.engine.JRException;
/*  16:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @RequestScoped
/*  21:    */ public class ReporteOrdenTrabajoMantenimientoBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @EJB
/*  26:    */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  27:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/*  28: 50 */   private final String COMPILE_FILE_NAME = "reporteOrdenTrabajoMantenimiento";
/*  29:    */   
/*  30:    */   protected JRDataSource getJRDataSource()
/*  31:    */   {
/*  32: 55 */     JRDataSource ds = null;
/*  33:    */     try
/*  34:    */     {
/*  35: 57 */       List<OrdenTrabajoMantenimiento> list = new ArrayList();
/*  36: 58 */       list.add(this.servicioOrdenTrabajoMantenimiento.cargarDetalle(getOrdenTrabajoMantenimiento()));
/*  37: 59 */       ds = new JRBeanCollectionDataSource(list);
/*  38:    */     }
/*  39:    */     catch (Exception e)
/*  40:    */     {
/*  41: 61 */       e.printStackTrace();
/*  42:    */     }
/*  43: 63 */     return ds;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/*  47:    */   {
/*  48: 67 */     if (this.ordenTrabajoMantenimiento == null) {
/*  49: 68 */       this.ordenTrabajoMantenimiento = new OrdenTrabajoMantenimiento();
/*  50:    */     }
/*  51: 70 */     return this.ordenTrabajoMantenimiento;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTabajoMantenimiento)
/*  55:    */   {
/*  56: 74 */     this.ordenTrabajoMantenimiento = ordenTabajoMantenimiento;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected String getCompileFileName()
/*  60:    */   {
/*  61: 79 */     return "reporteOrdenTrabajoMantenimiento";
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected Map<String, Object> getReportParameters()
/*  65:    */   {
/*  66: 90 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  67: 91 */     reportParameters.put("ReportTitle", "Orden de Trabajo");
/*  68: 92 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  69: 93 */     return reportParameters;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String execute()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:100 */       super.prepareReport();
/*  77:    */     }
/*  78:    */     catch (JRException e)
/*  79:    */     {
/*  80:103 */       LOG.info("Error JRException");
/*  81:104 */       e.printStackTrace();
/*  82:105 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  83:    */     }
/*  84:    */     catch (IOException e)
/*  85:    */     {
/*  86:107 */       LOG.info("Error IOException");
/*  87:108 */       e.printStackTrace();
/*  88:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  89:    */     }
/*  90:112 */     return "";
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.reportes.ReporteOrdenTrabajoMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */