/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   8:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.finaciero.cobros.reportes.ReporteNotaCreditoFinancieraBean;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  23:    */ import net.sf.jasperreports.engine.JRException;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ReporteDevolucionClienteBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -6079259004992504951L;
/*  32:    */   @EJB
/*  33:    */   private ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  34:    */   private FacturaCliente devolucionCliente;
/*  35: 40 */   private String COMPILE_FILE_NAME = "reporteNotaCreditoCliente";
/*  36: 42 */   private boolean indicadorDetallado = false;
/*  37: 43 */   private boolean indicadorImpreso = true;
/*  38: 44 */   private int numeroImpresiones = 1;
/*  39:    */   
/*  40:    */   protected JRDataSource getJRDataSource()
/*  41:    */   {
/*  42: 50 */     List listaDatosReporte = new ArrayList();
/*  43: 51 */     JRDataSource ds = null;
/*  44:    */     
/*  45: 53 */     procesarDetalle();
/*  46:    */     try
/*  47:    */     {
/*  48: 57 */       if (getDevolucionCliente().getDocumento().isIndicadorDocumentoElectronico()) {
/*  49: 58 */         this.indicadorDetallado = true;
/*  50:    */       }
/*  51: 61 */       listaDatosReporte = this.servicioNotaCreditoCliente.getReporteNotaCreditoCliente(getDevolucionCliente().getIdFacturaCliente(), 
/*  52: 62 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion(), DocumentoBase.DEVOLUCION_CLIENTE, this.indicadorDetallado, this.numeroImpresiones, this.indicadorImpreso);
/*  53:    */       
/*  54: 64 */       ds = new QueryResultDataSource(listaDatosReporte, ReporteNotaCreditoFinancieraBean.fields);
/*  55:    */     }
/*  56:    */     catch (Exception e)
/*  57:    */     {
/*  58: 67 */       LOG.info("Error ", e);
/*  59: 68 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  60:    */     }
/*  61: 70 */     return ds;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void procesarDetalle()
/*  65:    */   {
/*  66: 74 */     setIndicadorDetallado(false);
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected String getCompileFileName()
/*  70:    */   {
/*  71: 79 */     if (!this.devolucionCliente.getDocumento().getReporte().isEmpty()) {
/*  72: 80 */       this.COMPILE_FILE_NAME = this.devolucionCliente.getDocumento().getReporte();
/*  73:    */     }
/*  74: 82 */     return this.COMPILE_FILE_NAME;
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected Map<String, Object> getReportParameters()
/*  78:    */   {
/*  79: 88 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  80: 89 */     reportParameters.put("ReportTitle", "Devolucion Cliente");
/*  81: 90 */     reportParameters.put("p_indicadorNotaCredito", Boolean.valueOf(false));
/*  82: 91 */     reportParameters.put("p_indicadorAnulado", Boolean.valueOf(this.devolucionCliente.getEstado().equals(Estado.ANULADO)));
/*  83: 92 */     return reportParameters;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String execute()
/*  87:    */   {
/*  88: 98 */     setDocumentoElectronico(getDevolucionCliente().getDocumento().isIndicadorDocumentoElectronico());
/*  89:    */     try
/*  90:    */     {
/*  91:101 */       super.prepareReport();
/*  92:    */     }
/*  93:    */     catch (JRException e)
/*  94:    */     {
/*  95:103 */       LOG.info("Error JRException");
/*  96:104 */       e.printStackTrace();
/*  97:105 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  98:    */     }
/*  99:    */     catch (IOException e)
/* 100:    */     {
/* 101:107 */       LOG.info("Error IOException");
/* 102:108 */       e.printStackTrace();
/* 103:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 104:    */     }
/* 105:111 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public FacturaCliente getDevolucionCliente()
/* 109:    */   {
/* 110:118 */     if (this.devolucionCliente == null) {
/* 111:119 */       this.devolucionCliente = new FacturaCliente();
/* 112:    */     }
/* 113:121 */     return this.devolucionCliente;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setDevolucionCliente(FacturaCliente devolucionCliente)
/* 117:    */   {
/* 118:129 */     this.devolucionCliente = devolucionCliente;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isIndicadorDetallado()
/* 122:    */   {
/* 123:133 */     return this.indicadorDetallado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIndicadorDetallado(boolean indicadorDetallado)
/* 127:    */   {
/* 128:137 */     this.indicadorDetallado = indicadorDetallado;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isIndicadorImpreso()
/* 132:    */   {
/* 133:141 */     return this.indicadorImpreso;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setIndicadorImpreso(boolean indicadorImpreso)
/* 137:    */   {
/* 138:145 */     this.indicadorImpreso = indicadorImpreso;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getNumeroImpresiones()
/* 142:    */   {
/* 143:149 */     return this.numeroImpresiones;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setNumeroImpresiones(int numeroImpresiones)
/* 147:    */   {
/* 148:153 */     this.numeroImpresiones = numeroImpresiones;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteDevolucionClienteBean
 * JD-Core Version:    0.7.0.1
 */