/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteFacturaProveedor;
/*   4:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.RequestScoped;
/*  15:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  16:    */ import net.sf.jasperreports.engine.JRException;
/*  17:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @RequestScoped
/*  22:    */ public class ReporteFacturaProveedorAsientosBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -6450022697432262754L;
/*  26:    */   @EJB
/*  27:    */   private ServicioReporteCompra servicioReporteCompra;
/*  28:    */   private FacturaProveedor facturaProveedor;
/*  29: 48 */   private String COMPILE_FILE_NAME = "reporteFacturaProveedorAsientos";
/*  30:    */   
/*  31:    */   protected JRDataSource getJRDataSource()
/*  32:    */   {
/*  33: 57 */     JRDataSource ds = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 59 */       ReporteFacturaProveedor reporteFacturaProveedor = this.servicioReporteCompra.getReporteFacturaProveedorAsientos(this.facturaProveedor.getIdFacturaProveedor());
/*  37: 60 */       List<ReporteFacturaProveedor> lista = new ArrayList();
/*  38: 61 */       lista.add(reporteFacturaProveedor);
/*  39: 62 */       ds = new JRBeanCollectionDataSource(lista);
/*  40:    */     }
/*  41:    */     catch (Exception e)
/*  42:    */     {
/*  43: 64 */       e.printStackTrace();
/*  44:    */     }
/*  45: 66 */     return ds;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected String getCompileFileName()
/*  49:    */   {
/*  50: 76 */     return this.COMPILE_FILE_NAME;
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected Map<String, Object> getReportParameters()
/*  54:    */   {
/*  55: 87 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  56: 88 */     reportParameters.put("ReportTitle", "Titulo del reporte");
/*  57: 89 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  58: 90 */     return reportParameters;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String execute()
/*  62:    */   {
/*  63: 97 */     if (getFacturaProveedor().getId() == 0) {
/*  64: 98 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  65:    */     } else {
/*  66:    */       try
/*  67:    */       {
/*  68:102 */         super.prepareReport();
/*  69:    */       }
/*  70:    */       catch (JRException e)
/*  71:    */       {
/*  72:104 */         LOG.info("Error JRException");
/*  73:105 */         e.printStackTrace();
/*  74:106 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  75:    */       }
/*  76:    */       catch (IOException e)
/*  77:    */       {
/*  78:108 */         LOG.info("Error IOException");
/*  79:109 */         e.printStackTrace();
/*  80:110 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  81:    */       }
/*  82:    */     }
/*  83:114 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public FacturaProveedor getFacturaProveedor()
/*  87:    */   {
/*  88:118 */     if (this.facturaProveedor == null) {
/*  89:119 */       this.facturaProveedor = new FacturaProveedor();
/*  90:    */     }
/*  91:121 */     return this.facturaProveedor;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/*  95:    */   {
/*  96:125 */     this.facturaProveedor = facturaProveedor;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteFacturaProveedorAsientosBean
 * JD-Core Version:    0.7.0.1
 */