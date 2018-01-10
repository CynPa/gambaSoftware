/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteCarteraCobrada;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Recaudador;
/*  11:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  12:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*  13:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.RequestScoped;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @RequestScoped
/*  30:    */ public class ReporteCarteraCobradaBean
/*  31:    */   extends AbstractClientReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = -3178818486208673560L;
/*  34:    */   @EJB
/*  35:    */   private ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  36:    */   @EJB
/*  37:    */   private ServicioUsuario servicioUsuario;
/*  38:    */   @EJB
/*  39:    */   private ServicioEmpresa servicioEmpresa;
/*  40:    */   @EJB
/*  41:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  42:    */   private boolean indicadorVendedor;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 47 */     this.indicadorResumen = true;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected JRDataSource getJRDataSource()
/*  51:    */   {
/*  52: 51 */     JRDataSource ds = null;
/*  53:    */     try
/*  54:    */     {
/*  55: 53 */       List<ReporteCarteraCobrada> lista = this.servicioReporteCobroCliente.getReporteCarteraCobrada(getCategoriaEmpresa(), getAgenteComercial(), 
/*  56: 54 */         getRecaudador(), getEmpresa(), getFechaDesde(), getFechaHasta(), isIndicadorResumen(), isIndicadorVendedor(), AppUtil.getOrganizacion().getId());
/*  57: 55 */       ds = new JRBeanCollectionDataSource(lista);
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61: 57 */       e.printStackTrace();
/*  62:    */     }
/*  63: 59 */     return ds;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getCompileFileName()
/*  67:    */   {
/*  68: 63 */     if (isIndicadorVendedor())
/*  69:    */     {
/*  70: 64 */       if (isIndicadorResumen()) {
/*  71: 65 */         return "reporteCarteraCobradaResumidoVendedor";
/*  72:    */       }
/*  73: 67 */       return "reporteCarteraCobradaDetalladoVendedor";
/*  74:    */     }
/*  75: 69 */     if (isIndicadorResumen()) {
/*  76: 70 */       return "reporteCarteraCobradaResumido";
/*  77:    */     }
/*  78: 72 */     return "reporteCarteraCobradaDetallado";
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Map<String, Object> getReportParameters()
/*  82:    */   {
/*  83: 76 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  84: 77 */     reportParameters.put("ReportTitle", "Reporte Cartera Cobrada");
/*  85: 78 */     reportParameters.put("fechaHasta", getFechaHasta());
/*  86: 79 */     reportParameters.put("fechaDesde", getFechaDesde());
/*  87: 80 */     reportParameters.put("categoriaEmpresa", getCategoriaEmpresa() != null ? getCategoriaEmpresa().getNombre() : "Todos");
/*  88: 81 */     reportParameters.put("empresa", getEmpresa() != null ? getEmpresa().getNombreFiscal() : "Todos");
/*  89: 82 */     reportParameters.put("recaudador", getRecaudador() != null ? getRecaudador().getNombre() : "Todos");
/*  90: 83 */     reportParameters.put("agenteComercial", 
/*  91: 84 */       getAgenteComercial() != null ? getAgenteComercial().getNombre2() + " " + getAgenteComercial().getNombre1() : "Todos");
/*  92: 85 */     return reportParameters;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String execute()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99: 90 */       super.prepareReport();
/* 100:    */     }
/* 101:    */     catch (JRException e)
/* 102:    */     {
/* 103: 92 */       LOG.info("Error JRException");
/* 104: 93 */       e.printStackTrace();
/* 105: 94 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 106:    */     }
/* 107:    */     catch (IOException e)
/* 108:    */     {
/* 109: 96 */       LOG.info("Error IOException");
/* 110: 97 */       e.printStackTrace();
/* 111: 98 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 112:    */     }
/* 113:100 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isIndicadorVendedor()
/* 117:    */   {
/* 118:104 */     return this.indicadorVendedor;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setIndicadorVendedor(boolean indicadorVendedor)
/* 122:    */   {
/* 123:108 */     this.indicadorVendedor = indicadorVendedor;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteCarteraCobradaBean
 * JD-Core Version:    0.7.0.1
 */