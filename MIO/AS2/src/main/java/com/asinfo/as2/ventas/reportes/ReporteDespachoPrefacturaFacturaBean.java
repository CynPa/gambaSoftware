/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteDespachoCliente;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Date;
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
/*  25:    */ public class ReporteDespachoPrefacturaFacturaBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private ServicioEmpresa servicioEmpresa;
/*  31:    */   @EJB
/*  32:    */   private ServicioPersonaResponsable servicioResponsableSalidaMercaderia;
/*  33:    */   @EJB
/*  34:    */   private ServicioReporteDespachoCliente servicioReporteDespachoCliente;
/*  35:    */   private Empresa empresa;
/*  36:    */   private Date fechaDesde;
/*  37:    */   private Date fechaHasta;
/*  38: 58 */   private boolean indicadorDetallado = false;
/*  39:    */   
/*  40:    */   protected JRDataSource getJRDataSource()
/*  41:    */   {
/*  42: 70 */     List listaDatosReporte = new ArrayList();
/*  43: 71 */     JRDataSource ds = null;
/*  44:    */     
/*  45: 73 */     listaDatosReporte = this.servicioReporteDespachoCliente.getReporteDespachoPrefacturaFactura(this.fechaDesde, this.fechaHasta, this.empresa, this.indicadorDetallado);
/*  46: 74 */     String[] fields = { "f_cliente", "f_numeroDespacho", "f_fechaDespacho", "f_descripcionDespacho", "f_numeroPrefactura", "f_fechaPrefactura", "f_valorPrefactura", "f_numeroFactura", "f_fechaFactura", "f_tipoProducto", "f_subcategoriaProducto", "f_descripcion2", "f_valorBono", "f_guia", "f_mso", "f_descripcionFactura", "f_costoDespacho", "f_codigoProducto", "f_producto" };
/*  47:    */     
/*  48:    */ 
/*  49: 77 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  50:    */     
/*  51: 79 */     return ds;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected String getCompileFileName()
/*  55:    */   {
/*  56: 90 */     if (this.indicadorDetallado) {
/*  57: 91 */       return "reporteDespachoPrefacturaFacturaDetallado";
/*  58:    */     }
/*  59: 93 */     return "reporteDespachoPrefacturaFactura";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<String, Object> getReportParameters()
/*  63:    */   {
/*  64:105 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  65:106 */     reportParameters.put("ReportTitle", "Reporte Despacho Detallado");
/*  66:107 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  67:108 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  68:109 */     return reportParameters;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String execute()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:119 */       validaDatos();
/*  76:120 */       super.prepareReport();
/*  77:    */     }
/*  78:    */     catch (JRException e)
/*  79:    */     {
/*  80:122 */       LOG.info("Error JRException");
/*  81:123 */       e.printStackTrace();
/*  82:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  83:    */     }
/*  84:    */     catch (IOException e)
/*  85:    */     {
/*  86:126 */       LOG.info("Error IOException");
/*  87:127 */       e.printStackTrace();
/*  88:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  89:    */     }
/*  90:131 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void validaDatos()
/*  94:    */   {
/*  95:135 */     if (this.fechaDesde == null) {
/*  96:136 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  97:    */     }
/*  98:138 */     if (this.fechaHasta == null) {
/*  99:139 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 104:    */   {
/* 105:150 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Date getFechaDesde()
/* 109:    */   {
/* 110:163 */     return this.fechaDesde;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setFechaDesde(Date fechaDesde)
/* 114:    */   {
/* 115:173 */     this.fechaDesde = fechaDesde;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Date getFechaHasta()
/* 119:    */   {
/* 120:182 */     return this.fechaHasta;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setFechaHasta(Date fechaHasta)
/* 124:    */   {
/* 125:192 */     this.fechaHasta = fechaHasta;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Empresa getEmpresa()
/* 129:    */   {
/* 130:201 */     return this.empresa;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setEmpresa(Empresa empresa)
/* 134:    */   {
/* 135:211 */     this.empresa = empresa;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isIndicadorDetallado()
/* 139:    */   {
/* 140:218 */     return this.indicadorDetallado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIndicadorDetallado(boolean indicadorDetallado)
/* 144:    */   {
/* 145:225 */     this.indicadorDetallado = indicadorDetallado;
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteDespachoPrefacturaFacturaBean
 * JD-Core Version:    0.7.0.1
 */