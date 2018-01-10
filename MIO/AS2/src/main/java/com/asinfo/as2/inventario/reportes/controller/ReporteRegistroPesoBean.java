/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.RegistroPeso;
/*   5:    */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*   6:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  16:    */ import net.sf.jasperreports.engine.JRException;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ public class ReporteRegistroPesoBean
/*  21:    */   extends AbstractBaseReportBean
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 3748793990194304607L;
/*  24:    */   @EJB
/*  25:    */   private ServicioRegistroPeso servicioRegistroPeso;
/*  26:    */   private RegistroPeso registroPeso;
/*  27: 43 */   private final String COMPILE_FILE_NAME_MATERIA_PRIMA = "reporteRegistroPesoMateriaPrima";
/*  28: 44 */   private final String COMPILE_FILE_NAME_TRANSFERENCIA_BODEGA = "reporteRegistroPesoTransferenciaBodega";
/*  29: 45 */   private final String COMPILE_FILE_NAME_DESPACHO_CLIENTE = "reporteRegistroPesoDespachoCliente";
/*  30:    */   
/*  31:    */   protected JRDataSource getJRDataSource()
/*  32:    */   {
/*  33: 50 */     List listaDatosReporte = new ArrayList();
/*  34: 51 */     JRDataSource ds = null;
/*  35:    */     try
/*  36:    */     {
/*  37: 53 */       listaDatosReporte = this.servicioRegistroPeso.getReporte(this.registroPeso == null ? 0 : this.registroPeso.getId());
/*  38:    */       
/*  39: 55 */       String[] fields = { "f_fecha", "f_numero", "f_nombreEmpresa", "f_identificacionEmpresa", "f_bodega", "f_fechaCreacion", "f_fechaEntrada", "f_fechaSalida", "f_nombreChofer", "f_identificacionChofer", "f_placaVehiculo", "f_nombreTransportista", "f_codigoProducto", "f_nombreProducto", "f_lote", "f_pesoEntrada", "f_pesoSalida", "f_pesoDestareTotal", "f_pesoNeto", "f_cantidad", "f_numeroPedidoProveedor", "f_usuarioAprobacionPedidoProveedor", "f_fechaAprobacionPedidoProveedor", "f_cantidadPedidoProveedor", "f_numeroTransferencia", "f_fechaTransferencia", "f_usuarioTransferencia", "f_bodegaOrigenTransferencia", "f_cantidadTransferencia", "f_codigoProductoDespachoCliente", "f_nombreProductoDespachoCliente", "f_loteDespachoCliente" };
/*  40:    */       
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 63 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 65 */       e.printStackTrace();
/*  52:    */     }
/*  53: 67 */     return ds;
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected String getCompileFileName()
/*  57:    */   {
/*  58: 72 */     if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) || 
/*  59: 73 */       (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.OTROS))) {
/*  60: 74 */       return "reporteRegistroPesoMateriaPrima";
/*  61:    */     }
/*  62: 75 */     if (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) {
/*  63: 76 */       return "reporteRegistroPesoTransferenciaBodega";
/*  64:    */     }
/*  65: 78 */     return "reporteRegistroPesoDespachoCliente";
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected Map<String, Object> getReportParameters()
/*  69:    */   {
/*  70: 85 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  71: 86 */     if (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) {
/*  72: 87 */       reportParameters.put("ReportTitle", "Registro Peso Compra Materia Prima");
/*  73: 88 */     } else if (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.OTROS)) {
/*  74: 89 */       reportParameters.put("ReportTitle", "Registro Peso");
/*  75: 90 */     } else if (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) {
/*  76: 91 */       reportParameters.put("ReportTitle", "Registro Peso Recepcion Transferencia Bodega");
/*  77:    */     } else {
/*  78: 93 */       reportParameters.put("ReportTitle", "Registro Peso Despacho Cliente");
/*  79:    */     }
/*  80: 95 */     return reportParameters;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String execute()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:104 */       super.prepareReport();
/*  88:    */     }
/*  89:    */     catch (JRException e)
/*  90:    */     {
/*  91:107 */       LOG.info("Error JRException");
/*  92:108 */       e.printStackTrace();
/*  93:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  94:    */     }
/*  95:    */     catch (IOException e)
/*  96:    */     {
/*  97:111 */       LOG.info("Error IOException");
/*  98:112 */       e.printStackTrace();
/*  99:113 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 100:    */     }
/* 101:116 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public RegistroPeso getRegistroPeso()
/* 105:    */   {
/* 106:120 */     return this.registroPeso;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setRegistroPeso(RegistroPeso registroPeso)
/* 110:    */   {
/* 111:124 */     this.registroPeso = registroPeso;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteRegistroPesoBean
 * JD-Core Version:    0.7.0.1
 */