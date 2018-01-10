/*   1:    */ package com.asinfo.as2.compras.importaciones.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.reportes.servicio.ServicioReporteImportacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
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
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class ReporteFacturasImportacionPorLiquidarBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -8808352804580614926L;
/*  29:    */   @EJB
/*  30:    */   private transient ServicioReporteImportacion servicioReporteImportacion;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioEmpresa servicioEmpresa;
/*  33: 53 */   private String COMPILE_FILE_NAME = "reporteFacturasProveedorImportacionPorLiquidar";
/*  34:    */   private FacturaProveedorImportacion facturaProveedorImportacion;
/*  35:    */   private Date fechaDesde;
/*  36:    */   private Date fechaHasta;
/*  37:    */   private Empresa proveedor;
/*  38:    */   
/*  39:    */   protected JRDataSource getJRDataSource()
/*  40:    */   {
/*  41: 70 */     List listaDatosReporte = new ArrayList();
/*  42: 71 */     JRDataSource ds = null;
/*  43:    */     try
/*  44:    */     {
/*  45: 75 */       listaDatosReporte = this.servicioReporteImportacion.getReporteFacturasProveedorImportacionPorLiquidar(this.fechaDesde, this.fechaHasta, this.proveedor);
/*  46:    */       
/*  47: 77 */       String[] fields = { "f_numeroPedido", "f_fechaPedido", "f_descripcionPedido", "numeroFactura", "fechaFactura", "nombreFiscal", "f_valorFactura", "f_fechaLiquidacion", "f_valorLiquidacion", "puertoEmbarque", "puertoLlegada", "paisOrigen", "f_informacionTransporte", "f_estado" };
/*  48:    */       
/*  49:    */ 
/*  50: 80 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  51:    */     }
/*  52:    */     catch (ExcepcionAS2 e)
/*  53:    */     {
/*  54: 83 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  55:    */     }
/*  56: 85 */     return ds;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Map<String, Object> getReportParameters()
/*  60:    */   {
/*  61: 98 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  62: 99 */     reportParameters.put("ReportTitle", "Resumen de Importaciones");
/*  63:100 */     return reportParameters;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getCompileFileName()
/*  67:    */   {
/*  68:111 */     return this.COMPILE_FILE_NAME;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String execute()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:116 */       validaFechas();
/*  76:117 */       super.prepareReport();
/*  77:    */     }
/*  78:    */     catch (JRException e)
/*  79:    */     {
/*  80:120 */       e.printStackTrace();
/*  81:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  82:    */     }
/*  83:    */     catch (IOException e)
/*  84:    */     {
/*  85:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  86:    */     }
/*  87:125 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<Empresa> autocompletarProveedores(String consulta)
/*  91:    */   {
/*  92:129 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void validaFechas()
/*  96:    */   {
/*  97:133 */     if (this.fechaDesde == null) {
/*  98:134 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  99:    */     }
/* 100:136 */     if (this.fechaHasta == null) {
/* 101:137 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public FacturaProveedorImportacion getFacturaProveedorImportacion()
/* 106:    */   {
/* 107:149 */     if (this.facturaProveedorImportacion == null) {
/* 108:150 */       this.facturaProveedorImportacion = new FacturaProveedorImportacion();
/* 109:    */     }
/* 110:152 */     return this.facturaProveedorImportacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 114:    */   {
/* 115:162 */     this.facturaProveedorImportacion = facturaProveedorImportacion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Date getFechaDesde()
/* 119:    */   {
/* 120:171 */     return this.fechaDesde;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setFechaDesde(Date fechaDesde)
/* 124:    */   {
/* 125:181 */     this.fechaDesde = fechaDesde;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Date getFechaHasta()
/* 129:    */   {
/* 130:190 */     return this.fechaHasta;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setFechaHasta(Date fechaHasta)
/* 134:    */   {
/* 135:200 */     this.fechaHasta = fechaHasta;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Empresa getProveedor()
/* 139:    */   {
/* 140:209 */     return this.proveedor;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setProveedor(Empresa proveedor)
/* 144:    */   {
/* 145:219 */     this.proveedor = proveedor;
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.reportes.ReporteFacturasImportacionPorLiquidarBean
 * JD-Core Version:    0.7.0.1
 */