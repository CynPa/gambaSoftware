/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
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
/*  23:    */ public class ReporteOrdenPagoProveedorBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -4283259981721466901L;
/*  27:    */   @EJB
/*  28:    */   private ServicioOrdenPagoProveedor servicioOrdenPagoProveedor;
/*  29:    */   private OrdenPagoProveedor ordenPagoProveedor;
/*  30:    */   public static final String COMPILE_FILE_NAME = "reporteOrdenPagoProveedor";
/*  31: 53 */   public static final String[] fields = { "f_categoriaEmpresa", "f_nombreProveedor", "f_numeroCompra", "f_numerofactura", "f_descripcionFactura", "f_fechaEmision", "f_fechaVencimiento", "f_valorSolicitado", "f_valorAprobado", "f_diasVencidos", "f_responsable", "f_responsable_factura", "f_dias_plazo" };
/*  32:    */   
/*  33:    */   protected JRDataSource getJRDataSource()
/*  34:    */   {
/*  35: 64 */     List listaDatosReporte = new ArrayList();
/*  36: 65 */     JRDataSource ds = null;
/*  37:    */     try
/*  38:    */     {
/*  39: 67 */       listaDatosReporte = this.servicioOrdenPagoProveedor.getReporteOrdenPagoProveedor(this.ordenPagoProveedor);
/*  40:    */       
/*  41: 69 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  42:    */     }
/*  43:    */     catch (Exception e)
/*  44:    */     {
/*  45: 71 */       e.printStackTrace();
/*  46:    */     }
/*  47: 73 */     return ds;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected String getCompileFileName()
/*  51:    */   {
/*  52: 83 */     if (getOrdenPagoProveedor().getDocumento().getReporte() != null) {
/*  53: 84 */       return getOrdenPagoProveedor().getDocumento().getReporte();
/*  54:    */     }
/*  55: 86 */     return "reporteOrdenPagoProveedor";
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected Map<String, Object> getReportParameters()
/*  59:    */   {
/*  60: 98 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  61: 99 */     reportParameters.put("ReportTitle", "Orden Pago Proveedor");
/*  62:100 */     if (getOrdenPagoProveedor().getId() != 0)
/*  63:    */     {
/*  64:101 */       reportParameters.put("p_numero", getOrdenPagoProveedor().getNumero());
/*  65:102 */       reportParameters.put("p_fechaCorte", getOrdenPagoProveedor().getFechaCorte());
/*  66:103 */       reportParameters.put("p_estado", getOrdenPagoProveedor().getEstado().toString());
/*  67:104 */       reportParameters.put("p_descripcion", getOrdenPagoProveedor().getDescripcion());
/*  68:    */     }
/*  69:106 */     return reportParameters;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String execute()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:114 */       super.prepareReport();
/*  77:    */     }
/*  78:    */     catch (JRException e)
/*  79:    */     {
/*  80:116 */       LOG.info("Error JRException");
/*  81:117 */       e.printStackTrace();
/*  82:118 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  83:    */     }
/*  84:    */     catch (IOException e)
/*  85:    */     {
/*  86:120 */       LOG.info("Error IOException");
/*  87:121 */       e.printStackTrace();
/*  88:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  89:    */     }
/*  90:125 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public OrdenPagoProveedor getOrdenPagoProveedor()
/*  94:    */   {
/*  95:129 */     if (this.ordenPagoProveedor == null) {
/*  96:130 */       this.ordenPagoProveedor = new OrdenPagoProveedor();
/*  97:    */     }
/*  98:132 */     return this.ordenPagoProveedor;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/* 102:    */   {
/* 103:136 */     this.ordenPagoProveedor = ordenPagoProveedor;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteOrdenPagoProveedorBean
 * JD-Core Version:    0.7.0.1
 */