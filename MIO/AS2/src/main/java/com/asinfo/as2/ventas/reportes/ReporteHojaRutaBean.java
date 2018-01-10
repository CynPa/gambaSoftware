/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.HojaRuta;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.util.AppUtil;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.RequestScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import net.sf.jasperreports.engine.JRException;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @RequestScoped
/*  23:    */ public class ReporteHojaRutaBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -192264607856793455L;
/*  27:    */   @EJB
/*  28:    */   private ServicioHojaRuta servicioHojaRuta;
/*  29:    */   private HojaRuta hojaRuta;
/*  30: 48 */   private final String COMPILE_FILE_NAME = "hojaRuta";
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 58 */     List listaDatosReporte = new ArrayList();
/*  35: 59 */     JRDataSource ds = null;
/*  36:    */     
/*  37: 61 */     listaDatosReporte = this.servicioHojaRuta.reporteHojaRuta(AppUtil.getOrganizacion().getIdOrganizacion(), this.hojaRuta.getIdHojaRuta());
/*  38:    */     
/*  39: 63 */     String[] fields = { "f_nombreDocumento", "f_fechaDocumento", "f_decripcionDocumento", "f_factura", "f_nombreFiscal", "f_fechaDespacho", "f_cantidad", "f_piezas", "f_bultos", "f_descripcion", "f_numero", "f_ciudad", "f_responsable" };
/*  40:    */     
/*  41:    */ 
/*  42: 66 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  43:    */     
/*  44: 68 */     return ds;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected String getCompileFileName()
/*  48:    */   {
/*  49: 78 */     return "hojaRuta";
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected Map<String, Object> getReportParameters()
/*  53:    */   {
/*  54: 89 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  55: 90 */     reportParameters.put("ReportTitle", "Hoja Ruta");
/*  56: 91 */     return reportParameters;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String execute()
/*  60:    */   {
/*  61:    */     try
/*  62:    */     {
/*  63:100 */       super.prepareReport();
/*  64:    */     }
/*  65:    */     catch (JRException e)
/*  66:    */     {
/*  67:102 */       LOG.info("Error JRException");
/*  68:103 */       e.printStackTrace();
/*  69:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  70:    */     }
/*  71:    */     catch (IOException e)
/*  72:    */     {
/*  73:106 */       LOG.info("Error IOException");
/*  74:107 */       e.printStackTrace();
/*  75:108 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  76:    */     }
/*  77:110 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public HojaRuta getHojaRuta()
/*  81:    */   {
/*  82:114 */     if (this.hojaRuta == null) {
/*  83:115 */       this.hojaRuta = new HojaRuta();
/*  84:    */     }
/*  85:117 */     return this.hojaRuta;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setHojaRuta(HojaRuta hojaRuta)
/*  89:    */   {
/*  90:121 */     this.hojaRuta = hojaRuta;
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteHojaRutaBean
 * JD-Core Version:    0.7.0.1
 */