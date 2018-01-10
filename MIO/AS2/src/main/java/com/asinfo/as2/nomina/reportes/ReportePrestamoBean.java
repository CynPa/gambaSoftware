/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Prestamo;
/*   4:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPrestamo;
/*   5:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   6:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.faces.bean.ManagedBean;
/*  12:    */ import javax.faces.bean.ViewScoped;
/*  13:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  14:    */ import org.apache.log4j.Logger;
/*  15:    */ 
/*  16:    */ @ManagedBean
/*  17:    */ @ViewScoped
/*  18:    */ public class ReportePrestamoBean
/*  19:    */   extends AbstractBaseReportBean
/*  20:    */ {
/*  21:    */   @EJB
/*  22:    */   private ServicioPrestamo servicioPrestamo;
/*  23:    */   private static final long serialVersionUID = 4455310887544791406L;
/*  24:    */   private Prestamo prestamo;
/*  25:    */   
/*  26:    */   protected JRDataSource getJRDataSource()
/*  27:    */   {
/*  28: 56 */     List listaDatosReporte = new ArrayList();
/*  29: 57 */     JRDataSource ds = null;
/*  30:    */     try
/*  31:    */     {
/*  32: 60 */       listaDatosReporte = this.servicioPrestamo.reporteTablaAmortizacion(getPrestamo());
/*  33: 61 */       String[] fields = { "codigo", "identificacion", "nombreEmpleado", "fechaSolicitud", "fechaAprobacion", "fechaInicioDescuento", "monto", "interes", "plazo", "numeroCuota", "cuota", "capitalCuota", "interesCuota", "fechaCuota" };
/*  34:    */       
/*  35: 63 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  36:    */     }
/*  37:    */     catch (Exception e)
/*  38:    */     {
/*  39: 65 */       LOG.info("Error " + e);
/*  40: 66 */       e.printStackTrace();
/*  41:    */     }
/*  42: 68 */     return ds;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected Map<String, Object> getReportParameters()
/*  46:    */   {
/*  47: 81 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  48: 82 */     reportParameters.put("ReportTitle", "Tabla Amortizacion");
/*  49:    */     
/*  50: 84 */     return reportParameters;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String execute()
/*  54:    */   {
/*  55:    */     try
/*  56:    */     {
/*  57: 94 */       super.prepareReport();
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61: 96 */       e.printStackTrace();
/*  62:    */     }
/*  63: 99 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getCompileFileName()
/*  67:    */   {
/*  68:110 */     return "reporteTablaAmortizacion";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Prestamo getPrestamo()
/*  72:    */   {
/*  73:119 */     if (this.prestamo == null) {
/*  74:120 */       this.prestamo = new Prestamo();
/*  75:    */     }
/*  76:122 */     return this.prestamo;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setPrestamo(Prestamo prestamo)
/*  80:    */   {
/*  81:132 */     this.prestamo = prestamo;
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReportePrestamoBean
 * JD-Core Version:    0.7.0.1
 */