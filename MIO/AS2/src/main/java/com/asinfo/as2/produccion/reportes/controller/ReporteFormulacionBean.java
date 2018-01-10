/*  1:   */ package com.asinfo.as2.produccion.reportes.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  4:   */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  5:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  6:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.faces.bean.ManagedBean;
/* 11:   */ import javax.faces.bean.ViewScoped;
/* 12:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 13:   */ 
/* 14:   */ @ManagedBean
/* 15:   */ @ViewScoped
/* 16:   */ public class ReporteFormulacionBean
/* 17:   */   extends AbstractBaseReportBean
/* 18:   */ {
/* 19:   */   @EJB
/* 20:   */   private transient ServicioOrdenFabricacion servicioOrdenFabricacion;
/* 21:   */   private static final long serialVersionUID = 1103140461194449977L;
/* 22:   */   private OrdenFabricacion ordenFabricacion;
/* 23:   */   
/* 24:   */   protected JRDataSource getJRDataSource()
/* 25:   */   {
/* 26:42 */     JRDataSource ds = null;
/* 27:43 */     List<Object[]> listaDatosReporte = this.servicioOrdenFabricacion.getReporteFormulacion(getOrdenFabricacion());
/* 28:   */     
/* 29:45 */     String[] fields = { "f_numeroOrdenFabricacion", "f_codigoProducto", "f_nombreProducto", "f_codigoProductoPrincipal", "f_nombreProductoPrincipal", "f_cantidadFormulacion", "f_cantidadPlanificadaPrincipal", "f_pesoProductoPrincipal", "f_cantidadBom", "f_descripcionFormula", "f_codigoMaterial", "f_nombreMaterial", "f_cantidadMaterial", "f_unidadMaterial", "f_loteMaterial", "f_tipoMaterial", "f_nota", "f_cantidadPorCadaBatch", "f_fechaFormulacion", "f_indicadorConsumoDirecto" };
/* 30:   */     
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:50 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 35:51 */     return ds;
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected String getCompileFileName()
/* 39:   */   {
/* 40:56 */     return "reporteFormulacion";
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String execute()
/* 44:   */   {
/* 45:   */     try
/* 46:   */     {
/* 47:62 */       super.prepareReport();
/* 48:   */     }
/* 49:   */     catch (Exception e)
/* 50:   */     {
/* 51:64 */       e.printStackTrace();
/* 52:   */     }
/* 53:66 */     return null;
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected Map<String, Object> getReportParameters()
/* 57:   */   {
/* 58:72 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 59:73 */     reportParameters.put("ReportTitle", "Reporte Formulación");
/* 60:   */     
/* 61:75 */     return reportParameters;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public OrdenFabricacion getOrdenFabricacion()
/* 65:   */   {
/* 66:79 */     if (this.ordenFabricacion == null) {
/* 67:80 */       this.ordenFabricacion = new OrdenFabricacion();
/* 68:   */     }
/* 69:82 */     return this.ordenFabricacion;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 73:   */   {
/* 74:86 */     this.ordenFabricacion = ordenFabricacion;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteFormulacionBean
 * JD-Core Version:    0.7.0.1
 */