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
/*  23:    */ public class ReporteHojaRutaTransportistaBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -192264607856793455L;
/*  27:    */   @EJB
/*  28:    */   private ServicioHojaRuta servicioHojaRuta;
/*  29:    */   private HojaRuta hojaRuta;
/*  30:    */   protected boolean detallado;
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 58 */     List listaDatosReporte = new ArrayList();
/*  35: 59 */     JRDataSource ds = null;
/*  36:    */     
/*  37: 61 */     listaDatosReporte = this.servicioHojaRuta.reporteHojaRutaTransportista(AppUtil.getOrganizacion().getIdOrganizacion(), this.hojaRuta.getIdHojaRuta(), this.detallado);
/*  38:    */     
/*  39:    */ 
/*  40: 64 */     String[] fields = { "f_transportista", "f_hojaRuta", "f_fechaHojaRuta", "f_descripcionHojaRuta", "f_despacho", "f_empresa", "f_subEmpresa", "f_producto", "f_unidadVenta", "f_cantidad", "f_direccion" };
/*  41:    */     
/*  42:    */ 
/*  43: 67 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  44:    */     
/*  45: 69 */     return ds;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected String getCompileFileName()
/*  49:    */   {
/*  50: 79 */     if (this.detallado) {
/*  51: 80 */       return "hojaRutaTransportista";
/*  52:    */     }
/*  53: 82 */     return "hojaRutaTransportistaResumido";
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected Map<String, Object> getReportParameters()
/*  57:    */   {
/*  58: 95 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  59: 96 */     reportParameters.put("ReportTitle", "Hoja Ruta Transportista");
/*  60: 97 */     return reportParameters;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String execute()
/*  64:    */   {
/*  65:    */     try
/*  66:    */     {
/*  67:106 */       super.prepareReport();
/*  68:    */     }
/*  69:    */     catch (JRException e)
/*  70:    */     {
/*  71:108 */       LOG.info("Error JRException");
/*  72:109 */       e.printStackTrace();
/*  73:110 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  74:    */     }
/*  75:    */     catch (IOException e)
/*  76:    */     {
/*  77:112 */       LOG.info("Error IOException");
/*  78:113 */       e.printStackTrace();
/*  79:114 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81:116 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public HojaRuta getHojaRuta()
/*  85:    */   {
/*  86:120 */     if (this.hojaRuta == null) {
/*  87:121 */       this.hojaRuta = new HojaRuta();
/*  88:    */     }
/*  89:123 */     return this.hojaRuta;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setHojaRuta(HojaRuta hojaRuta)
/*  93:    */   {
/*  94:127 */     this.hojaRuta = hojaRuta;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isDetallado()
/*  98:    */   {
/*  99:131 */     return this.detallado;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDetallado(boolean detallado)
/* 103:    */   {
/* 104:135 */     this.detallado = detallado;
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteHojaRutaTransportistaBean
 * JD-Core Version:    0.7.0.1
 */