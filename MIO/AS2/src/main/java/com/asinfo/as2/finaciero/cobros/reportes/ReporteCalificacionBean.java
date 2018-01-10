/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteCarteraCobrada;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Calificacion;
/*   8:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Recaudador;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*  14:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  15:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  19:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  20:    */ import java.io.IOException;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.RequestScoped;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @RequestScoped
/*  35:    */ public class ReporteCalificacionBean
/*  36:    */   extends AbstractClientReportBean
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = -3178818486208673560L;
/*  39:    */   @EJB
/*  40:    */   private ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  41:    */   @EJB
/*  42:    */   private ServicioUsuario servicioUsuario;
/*  43:    */   @EJB
/*  44:    */   private ServicioEmpresa servicioEmpresa;
/*  45:    */   @EJB
/*  46:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  47:    */   @EJB
/*  48:    */   private ServicioGenerico<Calificacion> servicioCalificacion;
/*  49:    */   private List<Calificacion> listaCalificacion;
/*  50:    */   private Calificacion calificacion;
/*  51:    */   
/*  52:    */   protected JRDataSource getJRDataSource()
/*  53:    */   {
/*  54: 55 */     JRDataSource ds = null;
/*  55:    */     try
/*  56:    */     {
/*  57: 58 */       Date fechaDesde = (getMesesAnalisisCalificacionCliente() == null) || (getMesesAnalisisCalificacionCliente().intValue() == 0) ? FuncionesUtiles.obtenerFechaInicial() : FuncionesUtiles.sumarFechaMeses(new Date(), getMesesAnalisisCalificacionCliente().intValue());
/*  58: 59 */       setFechaDesde(fechaDesde);
/*  59: 60 */       setFechaHasta(new Date());
/*  60: 61 */       List<ReporteCarteraCobrada> lista = this.servicioReporteCobroCliente.getReporteCalificacionCliente(getCategoriaEmpresa(), getAgenteComercial(), 
/*  61: 62 */         getRecaudador(), getEmpresa(), this.calificacion, getFechaDesde(), getFechaHasta(), AppUtil.getOrganizacion().getId());
/*  62: 63 */       ds = new JRBeanCollectionDataSource(lista);
/*  63:    */     }
/*  64:    */     catch (Exception e)
/*  65:    */     {
/*  66: 65 */       e.printStackTrace();
/*  67:    */     }
/*  68: 67 */     return ds;
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected String getCompileFileName()
/*  72:    */   {
/*  73: 71 */     return "reporteCalificacionCliente";
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected Map<String, Object> getReportParameters()
/*  77:    */   {
/*  78: 75 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  79: 76 */     reportParameters.put("ReportTitle", "Reporte Cartera Cobrada");
/*  80: 77 */     reportParameters.put("fechaHasta", getFechaHasta());
/*  81: 78 */     reportParameters.put("fechaDesde", getFechaDesde());
/*  82: 79 */     reportParameters.put("categoriaEmpresa", getCategoriaEmpresa() != null ? getCategoriaEmpresa().getNombre() : "Todos");
/*  83: 80 */     reportParameters.put("empresa", getEmpresa() != null ? getEmpresa().getNombreFiscal() : "Todos");
/*  84: 81 */     reportParameters.put("recaudador", getRecaudador() != null ? getRecaudador().getNombre() : "Todos");
/*  85: 82 */     reportParameters.put("agenteComercial", getAgenteComercial() != null ? getAgenteComercial().getNombre2() + " " + getAgenteComercial().getNombre1() : "Todos");
/*  86: 83 */     return reportParameters;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String execute()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93: 88 */       super.prepareReport();
/*  94:    */     }
/*  95:    */     catch (JRException e)
/*  96:    */     {
/*  97: 90 */       LOG.info("Error JRException");
/*  98: 91 */       e.printStackTrace();
/*  99: 92 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 100:    */     }
/* 101:    */     catch (IOException e)
/* 102:    */     {
/* 103: 94 */       LOG.info("Error IOException");
/* 104: 95 */       e.printStackTrace();
/* 105: 96 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 106:    */     }
/* 107: 98 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Calificacion getCalificacion()
/* 111:    */   {
/* 112:102 */     return this.calificacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setCalificacion(Calificacion calificacion)
/* 116:    */   {
/* 117:106 */     this.calificacion = calificacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public List<Calificacion> getListaCalificacion()
/* 121:    */   {
/* 122:110 */     if (this.listaCalificacion == null)
/* 123:    */     {
/* 124:111 */       HashMap<String, String> filters = new HashMap();
/* 125:112 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 126:113 */       this.listaCalificacion = this.servicioCalificacion.obtenerListaCombo(Calificacion.class, "codigo", true, filters);
/* 127:    */     }
/* 128:115 */     return this.listaCalificacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaCalificacion(List<Calificacion> listaCalificacion)
/* 132:    */   {
/* 133:119 */     this.listaCalificacion = listaCalificacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Integer getMesesAnalisisCalificacionCliente()
/* 137:    */   {
/* 138:123 */     return ParametrosSistema.getMeseAnalisisCalificacionCliente(AppUtil.getOrganizacion().getId());
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteCalificacionBean
 * JD-Core Version:    0.7.0.1
 */