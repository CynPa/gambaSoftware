/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EstadoFinanciero;
/*   4:    */ import com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource.EstadoFinancieroDataSource;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.faces.bean.ManagedBean;
/*   9:    */ import javax.faces.bean.ManagedProperty;
/*  10:    */ import javax.faces.bean.RequestScoped;
/*  11:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  12:    */ 
/*  13:    */ @ManagedBean
/*  14:    */ @RequestScoped
/*  15:    */ public class ReporteBalanceComprobacionBean
/*  16:    */   extends AbstractBaseReportBean
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 6727423095104479734L;
/*  19:    */   private EstadoFinanciero estadoFinanciero;
/*  20: 43 */   private EstadoFinancieroDataSource estadoFinancieroDataSource = new EstadoFinancieroDataSource();
/*  21: 44 */   private final String COMPILE_FILE_NAME = "balanceComprobacion";
/*  22:    */   @ManagedProperty("#{balanceComprobacionBean}")
/*  23:    */   private BalanceComprobacionBean balanceComprobacionBean;
/*  24:    */   
/*  25:    */   public EstadoFinanciero getEstadoFinanciero()
/*  26:    */   {
/*  27: 50 */     return this.estadoFinanciero;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setEstadoFinanciero(EstadoFinanciero estadoFinanciero)
/*  31:    */   {
/*  32: 54 */     this.estadoFinanciero = estadoFinanciero;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected JRDataSource getJRDataSource()
/*  36:    */   {
/*  37: 65 */     return this.estadoFinancieroDataSource;
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected String getCompileFileName()
/*  41:    */   {
/*  42: 76 */     return "balanceComprobacion";
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected Map<String, Object> getReportParameters()
/*  46:    */   {
/*  47: 81 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  48:    */     
/*  49: 83 */     reportParameters.put("ReportTitle", "Balance de Comprobación");
/*  50: 84 */     reportParameters.put("p_fechaDesde", FuncionesUtiles.dateToString(this.balanceComprobacionBean.getEstadoFinanciero().getFechaDesde()));
/*  51: 85 */     reportParameters.put("p_fechaHasta", FuncionesUtiles.dateToString(this.balanceComprobacionBean.getEstadoFinanciero().getFechaHasta()));
/*  52:    */     
/*  53: 87 */     reportParameters.put("p_totalDebe", this.balanceComprobacionBean.getTotalDebe());
/*  54: 88 */     reportParameters.put("p_totalHaber", this.balanceComprobacionBean.getTotalHaber());
/*  55: 89 */     reportParameters.put("p_totalAcreedor", this.balanceComprobacionBean.getTotalAcreedor());
/*  56: 90 */     reportParameters.put("p_totalDeudor", this.balanceComprobacionBean.getTotalDeudor());
/*  57: 91 */     reportParameters.put("p_saldoInicial", this.balanceComprobacionBean.getSaldoInicial());
/*  58:    */     
/*  59: 93 */     reportParameters.put("p_sucursal", this.balanceComprobacionBean.getSucursal());
/*  60: 94 */     reportParameters.put("p_centroCosto", this.balanceComprobacionBean.getCentroCosto());
/*  61:    */     
/*  62: 96 */     return reportParameters;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String execute()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:102 */       this.estadoFinancieroDataSource.setListaDetalleEstadoFinanciero(this.balanceComprobacionBean.getEstadoFinanciero()
/*  70:103 */         .getListaDetalleEstadoFinanciero());
/*  71:    */       
/*  72:105 */       super.prepareReport();
/*  73:    */     }
/*  74:    */     catch (Exception e)
/*  75:    */     {
/*  76:108 */       e.printStackTrace();
/*  77:    */     }
/*  78:111 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public BalanceComprobacionBean getBalanceComprobacionBean()
/*  82:    */   {
/*  83:120 */     return this.balanceComprobacionBean;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setBalanceComprobacionBean(BalanceComprobacionBean balanceComprobacionBean)
/*  87:    */   {
/*  88:130 */     this.balanceComprobacionBean = balanceComprobacionBean;
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReporteBalanceComprobacionBean
 * JD-Core Version:    0.7.0.1
 */