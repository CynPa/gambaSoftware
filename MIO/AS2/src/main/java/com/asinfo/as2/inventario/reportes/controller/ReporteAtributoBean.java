/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Atributo;
/*   5:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.RequestScoped;
/*  15:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  16:    */ import net.sf.jasperreports.engine.JRException;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @RequestScoped
/*  21:    */ public class ReporteAtributoBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -4283259981721466901L;
/*  25:    */   @EJB
/*  26:    */   private ServicioAtributo servicioAtributo;
/*  27:    */   private Atributo atributo;
/*  28: 53 */   private final String COMPILE_FILE_NAME = "reporteAtributo";
/*  29:    */   
/*  30:    */   protected JRDataSource getJRDataSource()
/*  31:    */   {
/*  32: 63 */     List listaDatosReporte = new ArrayList();
/*  33: 64 */     JRDataSource ds = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 66 */       listaDatosReporte = this.servicioAtributo.getReporteAtributo(getAtributo().getId());
/*  37:    */       
/*  38: 68 */       String[] fields = { "f_codigoAtributo", "f_nombreAtributo", "f_tipoAtributo", "f_valorCodigo", "f_valorNombre" };
/*  39:    */       
/*  40: 70 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  41:    */     }
/*  42:    */     catch (Exception e)
/*  43:    */     {
/*  44: 72 */       e.printStackTrace();
/*  45:    */     }
/*  46: 74 */     return ds;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected String getCompileFileName()
/*  50:    */   {
/*  51: 84 */     return "reporteAtributo";
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected Map<String, Object> getReportParameters()
/*  55:    */   {
/*  56: 95 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  57: 96 */     reportParameters.put("ReportTitle", "Atributo");
/*  58: 97 */     return reportParameters;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String execute()
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65:106 */       super.prepareReport();
/*  66:    */     }
/*  67:    */     catch (JRException e)
/*  68:    */     {
/*  69:109 */       LOG.info("Error JRException");
/*  70:110 */       e.printStackTrace();
/*  71:111 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  72:    */     }
/*  73:    */     catch (IOException e)
/*  74:    */     {
/*  75:113 */       LOG.info("Error IOException");
/*  76:114 */       e.printStackTrace();
/*  77:115 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  78:    */     }
/*  79:118 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Atributo getAtributo()
/*  83:    */   {
/*  84:122 */     if (this.atributo == null) {
/*  85:123 */       this.atributo = new Atributo();
/*  86:    */     }
/*  87:125 */     return this.atributo;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setAtributo(Atributo atributo)
/*  91:    */   {
/*  92:129 */     this.atributo = atributo;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteAtributoBean
 * JD-Core Version:    0.7.0.1
 */