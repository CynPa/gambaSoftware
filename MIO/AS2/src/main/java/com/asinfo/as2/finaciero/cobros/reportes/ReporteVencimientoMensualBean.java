/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Subempresa;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.event.SelectEvent;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class ReporteVencimientoMensualBean
/*  31:    */   extends AbstractClientReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private ServicioReporteVenta servicioReporteVenta;
/*  36:    */   
/*  37:    */   static enum TipoReporte
/*  38:    */   {
/*  39: 58 */     FECHA_VENCIMIENTO_FACTURA("Fecha Vencimiento Factura"),  FECHA_EMISION_FACTURA("Fecha Emisión Factura");
/*  40:    */     
/*  41:    */     private String nombre;
/*  42:    */     
/*  43:    */     private TipoReporte(String nombre)
/*  44:    */     {
/*  45: 61 */       this.nombre = nombre;
/*  46:    */     }
/*  47:    */     
/*  48:    */     public String getNombre()
/*  49:    */     {
/*  50: 64 */       return this.nombre;
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54: 68 */   private TipoReporte tipoReporte = TipoReporte.FECHA_VENCIMIENTO_FACTURA;
/*  55:    */   private boolean indicadorResumen;
/*  56:    */   
/*  57:    */   protected JRDataSource getJRDataSource()
/*  58:    */   {
/*  59: 78 */     boolean emisionFactura = false;
/*  60: 79 */     if (this.tipoReporte.equals(TipoReporte.FECHA_EMISION_FACTURA)) {
/*  61: 80 */       emisionFactura = true;
/*  62:    */     }
/*  63: 82 */     List<Object[]> listaDatosReporte = this.servicioReporteVenta.getReporteVencimientoMensual(AppUtil.getOrganizacion().getIdOrganizacion(), this.empresa, this.subempresa, this.fechaHasta, emisionFactura);
/*  64: 83 */     String[] fields = { "f_codigoEmpresa", "f_identificacionEmpresa", "f_nombreFiscalEmpresa", "f_categoriaEmpresa", "f_identificacionSubempresa", "f_nombreFiscalSubempresa", "f_numeroFactura", "f_año", "f_mes", "f_saldo", "f_referencia2" };
/*  65:    */     
/*  66: 85 */     JRDataSource ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  67: 86 */     return ds;
/*  68:    */   }
/*  69:    */   
/*  70:    */   @PostConstruct
/*  71:    */   public void init()
/*  72:    */   {
/*  73: 91 */     this.fechaHasta = new Date();
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected String getCompileFileName()
/*  77:    */   {
/*  78:101 */     if (this.indicadorResumen) {
/*  79:102 */       return "reporteVencimientoMensualResumen";
/*  80:    */     }
/*  81:104 */     return "reporteVencimientoMensualDetallado";
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected Map<String, Object> getReportParameters()
/*  85:    */   {
/*  86:114 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  87:115 */     if (this.indicadorResumen) {
/*  88:116 */       reportParameters.put("ReportTitle", "Vencimiento Mensual Resumen (" + this.tipoReporte.getNombre() + ")");
/*  89:    */     } else {
/*  90:118 */       reportParameters.put("ReportTitle", "Vencimiento Mensual Detallado (" + this.tipoReporte.getNombre() + ")");
/*  91:    */     }
/*  92:120 */     reportParameters.put("p_empresa", this.empresa != null ? this.empresa.getNombreFiscal() : "Todos");
/*  93:121 */     reportParameters.put("p_subempresa", this.subempresa != null ? this.subempresa.getEmpresa().getNombreFiscal() : "Todos");
/*  94:122 */     reportParameters.put("p_fechaCorte", this.fechaHasta != null ? FuncionesUtiles.dateToString(this.fechaHasta) : "");
/*  95:    */     
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:127 */     return reportParameters;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String execute()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:138 */       super.prepareReport();
/* 107:    */     }
/* 108:    */     catch (JRException e)
/* 109:    */     {
/* 110:140 */       LOG.info("Error JRException");
/* 111:141 */       e.printStackTrace();
/* 112:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 113:    */     }
/* 114:    */     catch (IOException e)
/* 115:    */     {
/* 116:144 */       LOG.info("Error IOException");
/* 117:145 */       e.printStackTrace();
/* 118:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 119:    */     }
/* 120:148 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 124:    */   {
/* 125:155 */     return this.servicioEmpresa.autocompletarClientes(consulta, false, this.categoriaEmpresa);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void actualizarClienteListener(SelectEvent event)
/* 129:    */   {
/* 130:160 */     Empresa empresa = (Empresa)event.getObject();
/* 131:161 */     setEmpresa(empresa);
/* 132:162 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(empresa.getId(), false);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public TipoReporte getTipoReporte()
/* 136:    */   {
/* 137:174 */     return this.tipoReporte;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 141:    */   {
/* 142:178 */     this.tipoReporte = tipoReporte;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<SelectItem> getListaTipoReporte()
/* 146:    */   {
/* 147:184 */     List<SelectItem> lista = new ArrayList();
/* 148:185 */     for (TipoReporte tipoReporte : TipoReporte.values()) {
/* 149:186 */       lista.add(new SelectItem(tipoReporte, tipoReporte.getNombre()));
/* 150:    */     }
/* 151:188 */     return lista;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isIndicadorResumen()
/* 155:    */   {
/* 156:192 */     return this.indicadorResumen;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 160:    */   {
/* 161:196 */     this.indicadorResumen = indicadorResumen;
/* 162:    */   }
/* 163:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteVencimientoMensualBean
 * JD-Core Version:    0.7.0.1
 */