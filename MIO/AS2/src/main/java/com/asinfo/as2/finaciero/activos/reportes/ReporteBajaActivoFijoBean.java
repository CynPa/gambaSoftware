/*   1:    */ package com.asinfo.as2.finaciero.activos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*   5:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioMotivoBajaActivo;
/*   6:    */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteBajaActivoFijo;
/*   7:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Calendar;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import net.sf.jasperreports.engine.JRException;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ReporteBajaActivoFijoBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1208505782133526114L;
/*  30:    */   @EJB
/*  31:    */   private ServicioReporteBajaActivoFijo servicioReporteBajaActivoFijo;
/*  32:    */   @EJB
/*  33:    */   private ServicioMotivoBajaActivo servicioMotivoBajaActivo;
/*  34:    */   private Date fechaDesde;
/*  35:    */   private Date fechaHasta;
/*  36:    */   private int idMotivoBajaActivo;
/*  37:    */   private boolean indicadorDepreciacionFiscal;
/*  38:    */   private List<MotivoBajaActivo> listaMotivoBajaActivo;
/*  39:    */   
/*  40:    */   protected JRDataSource getJRDataSource()
/*  41:    */   {
/*  42: 71 */     List listaDatosReporte = new ArrayList();
/*  43: 72 */     JRDataSource ds = null;
/*  44:    */     try
/*  45:    */     {
/*  46: 74 */       listaDatosReporte = this.servicioReporteBajaActivoFijo.getReporteBajaActivoFijo(this.idMotivoBajaActivo, this.indicadorDepreciacionFiscal, this.fechaDesde, this.fechaHasta);
/*  47:    */       
/*  48:    */ 
/*  49: 77 */       String[] fields = { "f_codigoActivoFijo", "f_nombreActivoFijo", "f_valorActivo", "f_valorDepreciado", "f_valorCompraRelacionada", "f_valorAdicional", "f_valorDepreciacionAcumulada", "f_fechaBaja", "f_codigoMotivoBaja", "f_nombreMotivoBaja", "f_codigoCategoriaActivoFijo", "f_nombreCategoriaActivoFijo" };
/*  50:    */       
/*  51:    */ 
/*  52:    */ 
/*  53: 81 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57: 83 */       e.printStackTrace();
/*  58:    */     }
/*  59: 85 */     return ds;
/*  60:    */   }
/*  61:    */   
/*  62:    */   @PostConstruct
/*  63:    */   public void init()
/*  64:    */   {
/*  65: 91 */     Calendar calfechaDesde = Calendar.getInstance();
/*  66: 92 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  67: 93 */     this.fechaDesde = calfechaDesde.getTime();
/*  68: 94 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected String getCompileFileName()
/*  72:    */   {
/*  73:104 */     return "reporteBajaActivoFijo";
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected Map<String, Object> getReportParameters()
/*  77:    */   {
/*  78:115 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  79:116 */     reportParameters.put("ReportTitle", "Bajas Activos Fijos");
/*  80:117 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  81:118 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  82:119 */     return reportParameters;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String execute()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:128 */       validaDatos();
/*  90:129 */       super.prepareReport();
/*  91:    */     }
/*  92:    */     catch (JRException e)
/*  93:    */     {
/*  94:132 */       LOG.info("Error JRException");
/*  95:133 */       e.printStackTrace();
/*  96:134 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  97:    */     }
/*  98:    */     catch (IOException e)
/*  99:    */     {
/* 100:136 */       LOG.info("Error IOException");
/* 101:137 */       e.printStackTrace();
/* 102:138 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 103:    */     }
/* 104:141 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void validaDatos()
/* 108:    */   {
/* 109:146 */     if (this.fechaDesde == null) {
/* 110:147 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 111:    */     }
/* 112:149 */     if (this.fechaHasta == null) {
/* 113:150 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getIdMotivoBajaActivo()
/* 118:    */   {
/* 119:158 */     return this.idMotivoBajaActivo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdMotivoBajaActivo(int idMotivoBajaActivo)
/* 123:    */   {
/* 124:166 */     this.idMotivoBajaActivo = idMotivoBajaActivo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Date getFechaDesde()
/* 128:    */   {
/* 129:173 */     return this.fechaDesde;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setFechaDesde(Date fechaDesde)
/* 133:    */   {
/* 134:181 */     this.fechaDesde = fechaDesde;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Date getFechaHasta()
/* 138:    */   {
/* 139:188 */     return this.fechaHasta;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setFechaHasta(Date fechaHasta)
/* 143:    */   {
/* 144:196 */     this.fechaHasta = fechaHasta;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isIndicadorDepreciacionFiscal()
/* 148:    */   {
/* 149:203 */     return this.indicadorDepreciacionFiscal;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIndicadorDepreciacionFiscal(boolean indicadorDepreciacionFiscal)
/* 153:    */   {
/* 154:211 */     this.indicadorDepreciacionFiscal = indicadorDepreciacionFiscal;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<MotivoBajaActivo> getListaMotivoBajaActivo()
/* 158:    */   {
/* 159:218 */     if (this.listaMotivoBajaActivo == null) {
/* 160:219 */       this.listaMotivoBajaActivo = this.servicioMotivoBajaActivo.obtenerListaCombo("nombre", true, null);
/* 161:    */     }
/* 162:221 */     return this.listaMotivoBajaActivo;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setListaMotivoBajaActivo(List<MotivoBajaActivo> listaMotivoBajaActivo)
/* 166:    */   {
/* 167:229 */     this.listaMotivoBajaActivo = listaMotivoBajaActivo;
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.reportes.ReporteBajaActivoFijoBean
 * JD-Core Version:    0.7.0.1
 */