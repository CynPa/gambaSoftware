/*   1:    */ package com.asinfo.as2.compras.importaciones.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.reportes.servicio.ServicioReporteImportacion;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Pais;
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
/*  25:    */ public class ReporteFacturaProveedorImportacionGastoDetalladoBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -6294684041781121043L;
/*  29:    */   @EJB
/*  30:    */   private ServicioReporteImportacion servicioReporteImportacion;
/*  31:    */   @EJB
/*  32:    */   private ServicioEmpresa servicioEmpresa;
/*  33:    */   @EJB
/*  34:    */   private ServicioPais servicioPais;
/*  35:    */   private Date fechaDesde;
/*  36:    */   private Date fechaHasta;
/*  37:    */   private Empresa proveedor;
/*  38:    */   private Pais paisOrigen;
/*  39: 61 */   private String COMPILE_FILE_NAME = "reporteFacturaProveedorImportacionGastoDetallado";
/*  40:    */   private List<Pais> listaPais;
/*  41:    */   
/*  42:    */   protected JRDataSource getJRDataSource()
/*  43:    */   {
/*  44: 74 */     List listaReporte = new ArrayList();
/*  45: 75 */     listaReporte = this.servicioReporteImportacion.getReporteFacturaProveedorImportacionGastoDetallado(this.fechaDesde, this.fechaHasta, this.proveedor);
/*  46: 76 */     JRDataSource ds = null;
/*  47:    */     try
/*  48:    */     {
/*  49: 79 */       String[] fields = { "numeroFactura", "fechaFactura", "nombreFiscal", "puertoEmbarque", "codigoProducto", "nombreProducto", "valorPresupuestoImportacion", "valorRealImportacion" };
/*  50: 80 */       ds = new QueryResultDataSource(listaReporte, fields);
/*  51:    */     }
/*  52:    */     catch (Exception e)
/*  53:    */     {
/*  54: 82 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  55:    */     }
/*  56: 85 */     return ds;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Map<String, Object> getReportParameters()
/*  60:    */   {
/*  61: 96 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  62: 97 */     reportParameters.put("ReportTitle", "Gastos Detallados por Importación");
/*  63: 98 */     return reportParameters;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getCompileFileName()
/*  67:    */   {
/*  68:108 */     return this.COMPILE_FILE_NAME;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String execute()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:118 */       validaFechas();
/*  76:119 */       super.prepareReport();
/*  77:    */     }
/*  78:    */     catch (JRException e)
/*  79:    */     {
/*  80:122 */       e.printStackTrace();
/*  81:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos" + e.getMessage() + "1"));
/*  82:    */     }
/*  83:    */     catch (IOException e)
/*  84:    */     {
/*  85:125 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos" + e.getMessage() + "2"));
/*  86:    */     }
/*  87:127 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   private void validaFechas()
/*  91:    */   {
/*  92:131 */     if (this.fechaDesde == null) {
/*  93:132 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  94:    */     }
/*  95:134 */     if (this.fechaHasta == null) {
/*  96:135 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 101:    */   {
/* 102:140 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Date getFechaDesde()
/* 106:    */   {
/* 107:161 */     return this.fechaDesde;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setFechaDesde(Date fechaDesde)
/* 111:    */   {
/* 112:171 */     this.fechaDesde = fechaDesde;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Date getFechaHasta()
/* 116:    */   {
/* 117:180 */     return this.fechaHasta;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFechaHasta(Date fechaHasta)
/* 121:    */   {
/* 122:190 */     this.fechaHasta = fechaHasta;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Empresa getProveedor()
/* 126:    */   {
/* 127:199 */     return this.proveedor;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setProveedor(Empresa proveedor)
/* 131:    */   {
/* 132:209 */     this.proveedor = proveedor;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Pais getPaisOrigen()
/* 136:    */   {
/* 137:218 */     return this.paisOrigen;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setPaisOrigen(Pais paisOrigen)
/* 141:    */   {
/* 142:228 */     this.paisOrigen = paisOrigen;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<Pais> getListaPais()
/* 146:    */   {
/* 147:232 */     if (this.listaPais == null) {
/* 148:233 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", true, null);
/* 149:    */     }
/* 150:235 */     return this.listaPais;
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.reportes.ReporteFacturaProveedorImportacionGastoDetalladoBean
 * JD-Core Version:    0.7.0.1
 */