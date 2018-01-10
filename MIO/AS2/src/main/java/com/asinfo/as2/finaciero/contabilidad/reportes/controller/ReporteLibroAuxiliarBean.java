/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaContable;
/*   4:    */ import com.asinfo.as2.entities.DimensionContable;
/*   5:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*   6:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.faces.bean.ManagedBean;
/*  12:    */ import javax.faces.bean.ManagedProperty;
/*  13:    */ import javax.faces.bean.RequestScoped;
/*  14:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  15:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @RequestScoped
/*  20:    */ public class ReporteLibroAuxiliarBean
/*  21:    */   extends AbstractBaseReportBean
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 962709651515387240L;
/*  24:    */   @ManagedProperty("#{libroAuxiliarBean}")
/*  25:    */   private LibroAuxiliarBean libroAuxiliarBean;
/*  26: 47 */   private List<CuentaContable> listaCuentaContable = new ArrayList();
/*  27: 49 */   private final String COMPILE_FILE_NAME = "libroAuxiliar";
/*  28:    */   
/*  29:    */   protected JRDataSource getJRDataSource()
/*  30:    */   {
/*  31: 58 */     JRDataSource ds = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 61 */       ds = new JRBeanCollectionDataSource(this.listaCuentaContable);
/*  35:    */     }
/*  36:    */     catch (Exception e)
/*  37:    */     {
/*  38: 64 */       LOG.info("Error " + e);
/*  39: 65 */       e.printStackTrace();
/*  40:    */     }
/*  41: 67 */     return ds;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected String getCompileFileName()
/*  45:    */   {
/*  46: 77 */     return "libroAuxiliar";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String execute()
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 83 */       super.prepareReport();
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57: 86 */       e.printStackTrace();
/*  58:    */     }
/*  59: 89 */     return "";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<String, Object> getReportParameters()
/*  63:    */   {
/*  64: 94 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  65:    */     
/*  66: 96 */     reportParameters.put("ReportTitle", "Libro Auxiliar");
/*  67: 97 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  68: 98 */     reportParameters.put("p_fechaDesde", FuncionesUtiles.dateToString(this.libroAuxiliarBean.getFechaDesde()));
/*  69: 99 */     reportParameters.put("p_fechaHasta", FuncionesUtiles.dateToString(this.libroAuxiliarBean.getFechaHasta()));
/*  70:100 */     reportParameters.put("p_valor_dimension", this.libroAuxiliarBean.getDimension() != null ? this.libroAuxiliarBean.getDimension().getCodigo() : "");
/*  71:101 */     reportParameters.put("p_dimension", this.libroAuxiliarBean.getListaDimensionContableBean().getNumeroDimension());
/*  72:102 */     return reportParameters;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public LibroAuxiliarBean getLibroAuxiliarBean()
/*  76:    */   {
/*  77:111 */     return this.libroAuxiliarBean;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setLibroAuxiliarBean(LibroAuxiliarBean libroAuxiliarBean)
/*  81:    */   {
/*  82:121 */     this.libroAuxiliarBean = libroAuxiliarBean;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<CuentaContable> getListaCuentaContable()
/*  86:    */   {
/*  87:130 */     return this.listaCuentaContable;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setListaCuentaContable(List<CuentaContable> listaCuentaContable)
/*  91:    */   {
/*  92:140 */     this.listaCuentaContable = listaCuentaContable;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReporteLibroAuxiliarBean
 * JD-Core Version:    0.7.0.1
 */