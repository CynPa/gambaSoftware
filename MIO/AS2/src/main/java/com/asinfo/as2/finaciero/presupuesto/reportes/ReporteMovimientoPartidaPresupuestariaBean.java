/*  1:   */ package com.asinfo.as2.finaciero.presupuesto.reportes;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Documento;
/*  4:   */ import com.asinfo.as2.entities.Organizacion;
/*  5:   */ import com.asinfo.as2.entities.presupuesto.MovimientoPartidaPresupuestaria;
/*  6:   */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioMovimientoPartidaPresupuestaria;
/*  7:   */ import com.asinfo.as2.util.AppUtil;
/*  8:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  9:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/* 10:   */ import java.util.ArrayList;
/* 11:   */ import java.util.List;
/* 12:   */ import java.util.Map;
/* 13:   */ import javax.ejb.EJB;
/* 14:   */ import javax.faces.bean.ManagedBean;
/* 15:   */ import javax.faces.bean.ViewScoped;
/* 16:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 17:   */ 
/* 18:   */ @ManagedBean
/* 19:   */ @ViewScoped
/* 20:   */ public class ReporteMovimientoPartidaPresupuestariaBean
/* 21:   */   extends AbstractBaseReportBean
/* 22:   */ {
/* 23:   */   private static final long serialVersionUID = -7165643702454800203L;
/* 24:   */   @EJB
/* 25:   */   private transient ServicioMovimientoPartidaPresupuestaria servicioMovimientoPartidaPresupuestaria;
/* 26:   */   private MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria;
/* 27:   */   
/* 28:   */   protected JRDataSource getJRDataSource()
/* 29:   */   {
/* 30:43 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 31:44 */     JRDataSource ds = null;
/* 32:   */     
/* 33:46 */     String[] fields = { "f_numero", "f_fecha", "f_documento", "f_mesOrigen", "f_mesDestino", "f_ejercicio", "f_nota", "f_codigoCuentaContable", "f_nombreCuentaContable", "f_codigoDimensionContable", "f_nombreDimensionContable", "f_saldo", "f_codigoCuentaContableDestino", "f_nombreCuentaContableDestino", "f_codigoDimensionContableDestino", "f_nombreDimensionContableDestino", "f_saldoDestino", "f_valor" };
/* 34:   */     
/* 35:   */ 
/* 36:49 */     setMovimientoPartidaPresupuestaria(this.servicioMovimientoPartidaPresupuestaria.cargarDetalle(this.movimientoPartidaPresupuestaria.getId()));
/* 37:50 */     listaDatosReporte = this.servicioMovimientoPartidaPresupuestaria.getReporteMovimientoPartidaPresupuestaria(getMovimientoPartidaPresupuestaria()
/* 38:51 */       .getId(), AppUtil.getOrganizacion().getId());
/* 39:52 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 40:53 */     return ds;
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected String getCompileFileName()
/* 44:   */   {
/* 45:58 */     if (getMovimientoPartidaPresupuestaria().getDocumento().getOperacion() == 0) {
/* 46:59 */       return "reporteMovimientoPartidaPresupuestariaTransferencia";
/* 47:   */     }
/* 48:61 */     return "reporteMovimientoPartidaPresupuestaria";
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String execute()
/* 52:   */   {
/* 53:   */     try
/* 54:   */     {
/* 55:67 */       super.prepareReport();
/* 56:   */     }
/* 57:   */     catch (Exception e)
/* 58:   */     {
/* 59:69 */       e.printStackTrace();
/* 60:   */     }
/* 61:71 */     return null;
/* 62:   */   }
/* 63:   */   
/* 64:   */   protected Map<String, Object> getReportParameters()
/* 65:   */   {
/* 66:76 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 67:77 */     reportParameters.put("ReportTitle", "Movimiento Partida Presupuestaria");
/* 68:78 */     return reportParameters;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public MovimientoPartidaPresupuestaria getMovimientoPartidaPresupuestaria()
/* 72:   */   {
/* 73:82 */     return this.movimientoPartidaPresupuestaria;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public void setMovimientoPartidaPresupuestaria(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria)
/* 77:   */   {
/* 78:86 */     this.movimientoPartidaPresupuestaria = movimientoPartidaPresupuestaria;
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.reportes.ReporteMovimientoPartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */