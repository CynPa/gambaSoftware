/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EstadoFinanciero;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
/*   5:    */ import com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource.EstadoFinancieroDataSource;
/*   6:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.faces.bean.ManagedBean;
/*  10:    */ import javax.faces.bean.ManagedProperty;
/*  11:    */ import javax.faces.bean.RequestScoped;
/*  12:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  13:    */ 
/*  14:    */ @ManagedBean
/*  15:    */ @RequestScoped
/*  16:    */ public class ReporteEstadoFinancieroBean
/*  17:    */   extends AbstractBaseReportBean
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 8436289147804190712L;
/*  20: 41 */   private EstadoFinancieroDataSource estadoFinancieroDataSource = new EstadoFinancieroDataSource();
/*  21: 42 */   private String COMPILE_FILE_NAME = "";
/*  22:    */   @ManagedProperty("#{estadoFinancieroBean}")
/*  23:    */   private EstadoFinancieroBean estadoFinancieroBean;
/*  24:    */   
/*  25:    */   protected JRDataSource getJRDataSource()
/*  26:    */   {
/*  27: 54 */     return this.estadoFinancieroDataSource;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected String getCompileFileName()
/*  31:    */   {
/*  32: 64 */     if (this.estadoFinancieroBean.getEstadoFinanciero().getTipoEstadoFinanciero() == TipoEstadoFinanciero.BALANCE_RESULTADOS) {
/*  33: 65 */       this.COMPILE_FILE_NAME = "estadoResultados";
/*  34:    */     } else {
/*  35: 67 */       this.COMPILE_FILE_NAME = "balanceGeneral";
/*  36:    */     }
/*  37: 70 */     return this.COMPILE_FILE_NAME;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String execute()
/*  41:    */   {
/*  42:    */     try
/*  43:    */     {
/*  44: 75 */       this.estadoFinancieroDataSource.setListaDetalleEstadoFinanciero(this.estadoFinancieroBean.getEstadoFinanciero().getListaDetalleEstadoFinanciero());
/*  45: 76 */       super.prepareReport();
/*  46:    */     }
/*  47:    */     catch (Exception e)
/*  48:    */     {
/*  49: 79 */       e.printStackTrace();
/*  50:    */     }
/*  51: 82 */     return "";
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected Map<String, Object> getReportParameters()
/*  55:    */   {
/*  56: 87 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  57: 88 */     if (this.estadoFinancieroBean.getEstadoFinanciero().getTipoEstadoFinanciero() == TipoEstadoFinanciero.BALANCE_RESULTADOS) {
/*  58: 89 */       reportParameters.put("ReportTitle", "Estado de resultados");
/*  59:    */     } else {
/*  60: 91 */       reportParameters.put("ReportTitle", "Estado de Situación Financiera");
/*  61:    */     }
/*  62: 94 */     reportParameters.put("p_fechaDesde", FuncionesUtiles.dateToString(this.estadoFinancieroBean.getEstadoFinanciero().getFechaDesde()));
/*  63: 95 */     reportParameters.put("p_fechaHasta", FuncionesUtiles.dateToString(this.estadoFinancieroBean.getEstadoFinanciero().getFechaHasta()));
/*  64: 96 */     reportParameters.put("resultadoEjercicio", this.estadoFinancieroBean.getEstadoFinanciero().getResultadoEjercicio());
/*  65: 97 */     reportParameters.put("saldoActivo", this.estadoFinancieroBean.getEstadoFinanciero().getSaldoActivo());
/*  66: 98 */     reportParameters.put("saldoPasivo", this.estadoFinancieroBean.getEstadoFinanciero().getSaldoPasivo());
/*  67: 99 */     reportParameters.put("saldoPatrimonio", this.estadoFinancieroBean.getEstadoFinanciero().getSaldoPatrimonio());
/*  68:100 */     reportParameters.put("centro_costo", this.estadoFinancieroBean.getValorDimension() + " " + this.estadoFinancieroBean.getNombreDimension());
/*  69:101 */     return reportParameters;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public EstadoFinancieroBean getEstadoFinancieroBean()
/*  73:    */   {
/*  74:105 */     return this.estadoFinancieroBean;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setEstadoFinancieroBean(EstadoFinancieroBean estadoFinancieroBean)
/*  78:    */   {
/*  79:109 */     this.estadoFinancieroBean = estadoFinancieroBean;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReporteEstadoFinancieroBean
 * JD-Core Version:    0.7.0.1
 */