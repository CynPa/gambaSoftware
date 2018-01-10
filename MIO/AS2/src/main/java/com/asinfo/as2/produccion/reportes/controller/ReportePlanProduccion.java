/*  1:   */ package com.asinfo.as2.produccion.reportes.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.DetallePlanProduccion;
/*  4:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.faces.bean.ManagedBean;
/*  9:   */ import javax.faces.bean.ViewScoped;
/* 10:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 11:   */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/* 12:   */ 
/* 13:   */ @ManagedBean
/* 14:   */ @ViewScoped
/* 15:   */ public class ReportePlanProduccion
/* 16:   */   extends AbstractBaseReportBean
/* 17:   */ {
/* 18:   */   private static final long serialVersionUID = 1103140461194449977L;
/* 19:   */   private List<DetallePlanProduccion> listaDetallePlanProduccion;
/* 20:   */   
/* 21:   */   protected JRDataSource getJRDataSource()
/* 22:   */   {
/* 23:38 */     ArrayList<DetallePlanProduccion> listaAEliminar = new ArrayList();
/* 24:40 */     for (DetallePlanProduccion dpp : this.listaDetallePlanProduccion) {
/* 25:41 */       if ((dpp.getBatchLunes() == 0) && (dpp.getBatchMartes() == 0) && 
/* 26:42 */         (dpp.getBatchMiercoles() == 0) && (dpp.getBatchJueves() == 0) && 
/* 27:43 */         (dpp.getBatchViernes() == 0) && (dpp.getBatchSabado() == 0) && (dpp.getBatchDomingo() == 0)) {
/* 28:46 */         listaAEliminar.add(dpp);
/* 29:   */       }
/* 30:   */     }
/* 31:50 */     this.listaDetallePlanProduccion.removeAll(listaAEliminar);
/* 32:   */     
/* 33:   */ 
/* 34:53 */     Object ds = new JRBeanCollectionDataSource(this.listaDetallePlanProduccion);
/* 35:54 */     return ds;
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected String getCompileFileName()
/* 39:   */   {
/* 40:59 */     return "reportePlanProduccion";
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String execute()
/* 44:   */   {
/* 45:   */     try
/* 46:   */     {
/* 47:65 */       super.prepareReport();
/* 48:   */     }
/* 49:   */     catch (Exception e)
/* 50:   */     {
/* 51:67 */       e.printStackTrace();
/* 52:   */     }
/* 53:69 */     return null;
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected Map<String, Object> getReportParameters()
/* 57:   */   {
/* 58:74 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 59:75 */     reportParameters.put("ReportTitle", "Plan Producción");
/* 60:   */     
/* 61:77 */     return reportParameters;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public List<DetallePlanProduccion> getListaDetallePlanProduccion()
/* 65:   */   {
/* 66:81 */     return this.listaDetallePlanProduccion;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void setListaDetallePlanProduccion(List<DetallePlanProduccion> listaDetallePlanProduccion)
/* 70:   */   {
/* 71:85 */     this.listaDetallePlanProduccion = listaDetallePlanProduccion;
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReportePlanProduccion
 * JD-Core Version:    0.7.0.1
 */