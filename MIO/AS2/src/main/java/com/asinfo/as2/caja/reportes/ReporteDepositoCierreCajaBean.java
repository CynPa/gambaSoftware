/*   1:    */ package com.asinfo.as2.caja.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCaja;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import net.sf.jasperreports.engine.JRException;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ReporteDepositoCierreCajaBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 2581044010235310949L;
/*  30:    */   @EJB
/*  31:    */   private ServicioCaja servicioCaja;
/*  32:    */   private InterfazContableProceso interfazContableProceso;
/*  33:    */   private Date fechaDesde;
/*  34:    */   private Date fechaHasta;
/*  35:    */   
/*  36:    */   protected JRDataSource getJRDataSource()
/*  37:    */   {
/*  38: 54 */     List listaDatosReporte = new ArrayList();
/*  39: 55 */     JRDataSource ds = null;
/*  40: 56 */     String[] fields = null;
/*  41:    */     try
/*  42:    */     {
/*  43: 59 */       if (this.interfazContableProceso != null) {
/*  44: 60 */         if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA))
/*  45:    */         {
/*  46: 61 */           fields = new String[] { "f_fechaContabilizacionICP", "f_fechaDesdeICP", "f_fechaHastaICP", "f_numeroICP", "f_documentoReferenciaICP", "f_nombreB", "f_nombreFP", "f_fechaC", "f_nombreFiscalE", "f_documentoReferenciaDFC", "f_numeroFC", "f_valorDC" };
/*  47:    */           
/*  48:    */ 
/*  49: 64 */           listaDatosReporte = this.servicioCaja.getListaReporteDepositosCierreCaja(null, this.interfazContableProceso.getFechaHasta(), 
/*  50: 65 */             AppUtil.getOrganizacion().getId());
/*  51:    */         }
/*  52:    */         else
/*  53:    */         {
/*  54: 67 */           fields = new String[] { "numero", "valor", "formaPago", "caja", "usuario", "documentoReferencia", "banco", "empresa", "documentoReferenciaInterfaz", "f_asiento" };
/*  55:    */           
/*  56: 69 */           listaDatosReporte = this.servicioCaja.getReporteDepositoCierreCajaBean(this.interfazContableProceso);
/*  57:    */         }
/*  58:    */       }
/*  59: 72 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  60:    */     }
/*  61:    */     catch (Exception e)
/*  62:    */     {
/*  63: 74 */       LOG.info("Error " + e);
/*  64: 75 */       e.printStackTrace();
/*  65:    */     }
/*  66: 77 */     return ds;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected String getCompileFileName()
/*  70:    */   {
/*  71: 87 */     if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  72: 88 */       return "reporteDepositosCierreCajaPorFecha";
/*  73:    */     }
/*  74: 90 */     return "reporteDepositoCierreCaja";
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected Map<String, Object> getReportParameters()
/*  78:    */   {
/*  79:101 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  80:102 */     reportParameters.put("ReportTitle", "Deposito Cierre Caja");
/*  81:103 */     reportParameters.put("p_fechaDepositoCierreCaja", this.interfazContableProceso.getFechaContabilizacion() == null ? "" : this.interfazContableProceso.getFechaContabilizacion());
/*  82:104 */     return reportParameters;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String execute()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:109 */       super.prepareReport();
/*  90:    */     }
/*  91:    */     catch (JRException e)
/*  92:    */     {
/*  93:111 */       LOG.info("Error JRException");
/*  94:112 */       e.printStackTrace();
/*  95:113 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  96:    */     }
/*  97:    */     catch (IOException e)
/*  98:    */     {
/*  99:115 */       LOG.info("Error IOException");
/* 100:116 */       e.printStackTrace();
/* 101:117 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 102:    */     }
/* 103:120 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public InterfazContableProceso getInterfazContableProceso()
/* 107:    */   {
/* 108:124 */     return this.interfazContableProceso;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 112:    */   {
/* 113:128 */     this.interfazContableProceso = interfazContableProceso;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Date getFechaDesde()
/* 117:    */   {
/* 118:137 */     return this.fechaDesde;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setFechaDesde(Date fechaDesde)
/* 122:    */   {
/* 123:147 */     this.fechaDesde = fechaDesde;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Date getFechaHasta()
/* 127:    */   {
/* 128:156 */     return this.fechaHasta;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setFechaHasta(Date fechaHasta)
/* 132:    */   {
/* 133:166 */     this.fechaHasta = fechaHasta;
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.reportes.ReporteDepositoCierreCajaBean
 * JD-Core Version:    0.7.0.1
 */