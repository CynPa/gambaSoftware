/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.PagoCash;
/*   7:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   8:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPagoCash;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  20:    */ import net.sf.jasperreports.engine.JRException;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class ReportePagoCashBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private ServicioPagoCash servicioPagoCash;
/*  31:    */   private PagoCash pagoCash;
/*  32: 56 */   public static String COMPILE_FILE_NAME = "";
/*  33: 57 */   public static final String[] fields = { "f_nombreComercial", "f_numero", "f_fechaPago", "f_valorPagado", "f_facturaProveedor", "f_facturaProveedorSRI", "f_fechaFactura", "f_fechaVencimiento", "f_aprobados", "f_procesados", "f_establecimiento", "f_puntoEmision", "f_categoriaEmpresa", "f_nombreBanco", "f_numeroCuenta", "f_documentoReferencia", "f_numeroCuentaD", "f_tipoCuentaD", "f_nombreCuentaD", "f_codigoBanco", "f_descripcion", "f_responsable", "f_responsable_factura" };
/*  34:    */   
/*  35:    */   protected JRDataSource getJRDataSource()
/*  36:    */   {
/*  37: 70 */     List listaDatosReporte = new ArrayList();
/*  38: 71 */     JRDataSource ds = null;
/*  39:    */     try
/*  40:    */     {
/*  41: 76 */       if (this.pagoCash.getDocumento().getDocumentoBase() == DocumentoBase.PAGO_CASH_EMPLEADO)
/*  42:    */       {
/*  43: 77 */         String[] fields_pago_empleado = { "f_fecha", "f_numero", "f_nombreBanco", "f_numeroCuenta", "f_nombreComercial", "f_valorPagado", "f_tipoCuentaD", "f_numeroCuentaD", "f_nombreCuentaD", "f_nombres", "f_apellidos", "f_quincena", "f_identificacion", "f_codigo" };
/*  44:    */         
/*  45: 79 */         listaDatosReporte = this.servicioPagoCash.datosPagoCashEmpleado(this.pagoCash);
/*  46: 80 */         ds = new QueryResultDataSource(listaDatosReporte, fields_pago_empleado);
/*  47:    */       }
/*  48:    */       else
/*  49:    */       {
/*  50: 82 */         listaDatosReporte = this.servicioPagoCash.datosPagoCash(AppUtil.getOrganizacion().getIdOrganizacion(), this.pagoCash);
/*  51: 83 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  52:    */       }
/*  53:    */     }
/*  54:    */     catch (Exception e)
/*  55:    */     {
/*  56: 87 */       e.printStackTrace();
/*  57:    */     }
/*  58: 89 */     return ds;
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected String getCompileFileName()
/*  62:    */   {
/*  63: 99 */     COMPILE_FILE_NAME = this.pagoCash.getDocumento().getReporte();
/*  64:100 */     return COMPILE_FILE_NAME;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected Map<String, Object> getReportParameters()
/*  68:    */   {
/*  69:111 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  70:112 */     reportParameters.put("ReportTitle", "Pago");
/*  71:113 */     return reportParameters;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String execute()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:122 */       super.prepareReport();
/*  79:    */     }
/*  80:    */     catch (JRException e)
/*  81:    */     {
/*  82:125 */       LOG.info("Error JRException");
/*  83:126 */       e.printStackTrace();
/*  84:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  85:    */     }
/*  86:    */     catch (IOException e)
/*  87:    */     {
/*  88:129 */       LOG.info("Error IOException");
/*  89:130 */       e.printStackTrace();
/*  90:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  91:    */     }
/*  92:134 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public PagoCash getPagoCash()
/*  96:    */   {
/*  97:138 */     return this.pagoCash;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPagoCash(PagoCash pagoCash)
/* 101:    */   {
/* 102:142 */     this.pagoCash = pagoCash;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReportePagoCashBean
 * JD-Core Version:    0.7.0.1
 */