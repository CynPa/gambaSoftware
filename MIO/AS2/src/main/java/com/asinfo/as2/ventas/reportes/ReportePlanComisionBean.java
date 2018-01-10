/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.PlanComision;
/*   5:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   6:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioPlanComision;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.faces.bean.ManagedBean;
/*  11:    */ import javax.faces.bean.RequestScoped;
/*  12:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  13:    */ import net.sf.jasperreports.engine.JRException;
/*  14:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  15:    */ import org.apache.log4j.Logger;
/*  16:    */ 
/*  17:    */ @ManagedBean
/*  18:    */ @RequestScoped
/*  19:    */ public class ReportePlanComisionBean
/*  20:    */   extends AbstractBaseReportBean
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = -192264607856793455L;
/*  23:    */   @EJB
/*  24:    */   private ServicioPlanComision servicioPlanComision;
/*  25:    */   private PlanComision planComision;
/*  26: 45 */   private final String COMPILE_FILE_NAME = "reportePlanComision";
/*  27:    */   
/*  28:    */   protected JRDataSource getJRDataSource()
/*  29:    */   {
/*  30: 49 */     JRDataSource ds = null;
/*  31:    */     try
/*  32:    */     {
/*  33: 51 */       ds = new JRBeanCollectionDataSource(this.servicioPlanComision.getReportePlanComision(getPlanComision()));
/*  34:    */     }
/*  35:    */     catch (Exception e)
/*  36:    */     {
/*  37: 53 */       LOG.info("Error " + e);
/*  38: 54 */       e.printStackTrace();
/*  39:    */     }
/*  40: 56 */     return ds;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected String getCompileFileName()
/*  44:    */   {
/*  45: 66 */     return "reportePlanComision";
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected Map<String, Object> getReportParameters()
/*  49:    */   {
/*  50: 77 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  51: 78 */     reportParameters.put("ReportTitle", "Plan Comisiones");
/*  52: 79 */     if (getPlanComision() != null)
/*  53:    */     {
/*  54: 80 */       reportParameters.put("nombreUsuario", getPlanComision().getUsuarioCreacion().trim());
/*  55: 81 */       reportParameters.put("p_codigoPlanComision", getPlanComision().getCodigo());
/*  56: 82 */       reportParameters.put("p_nombrePlanComision", getPlanComision().getNombre());
/*  57: 83 */       reportParameters.put("p_descripcionPlanComision", getPlanComision().getDescripcion());
/*  58:    */     }
/*  59: 85 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  60: 86 */     return reportParameters;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String execute()
/*  64:    */   {
/*  65:    */     try
/*  66:    */     {
/*  67: 95 */       super.prepareReport();
/*  68:    */     }
/*  69:    */     catch (JRException e)
/*  70:    */     {
/*  71: 97 */       LOG.info("Error JRException");
/*  72: 98 */       e.printStackTrace();
/*  73: 99 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  74:    */     }
/*  75:    */     catch (IOException e)
/*  76:    */     {
/*  77:101 */       LOG.info("Error IOException");
/*  78:102 */       e.printStackTrace();
/*  79:103 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81:105 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public PlanComision getPlanComision()
/*  85:    */   {
/*  86:109 */     if (this.planComision == null) {
/*  87:110 */       this.planComision = new PlanComision();
/*  88:    */     }
/*  89:112 */     return this.planComision;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setPlanComision(PlanComision planComision)
/*  93:    */   {
/*  94:116 */     this.planComision = planComision;
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePlanComisionBean
 * JD-Core Version:    0.7.0.1
 */