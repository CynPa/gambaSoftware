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
/*  25:    */ public class ReporteFacturaProveedorImportacionGastoBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -2302233523055404539L;
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
/*  39: 63 */   private String COMPILE_FILE_NAME = "reporteFacturaProveedorImportacionGasto";
/*  40:    */   private List<Pais> listaPais;
/*  41:    */   
/*  42:    */   protected JRDataSource getJRDataSource()
/*  43:    */   {
/*  44: 76 */     List listaReporte = new ArrayList();
/*  45: 77 */     listaReporte = this.servicioReporteImportacion.getReporteFacturaProveedorImportacionGasto(this.fechaDesde, this.fechaHasta, this.proveedor, this.paisOrigen);
/*  46: 78 */     JRDataSource ds = null;
/*  47:    */     try
/*  48:    */     {
/*  49: 81 */       String[] fields = { "numeroFactura", "fechaFactura", "nombreFiscal", "fechaEmbarque", "puertoEmbarque", "fechaLlegada", "puertoLlegada", "medioTransporte", "paisOrigen", "estado", "gastoImportacion", "tipoProrrateo", "valorPresupuesto", "valorReal" };
/*  50:    */       
/*  51: 83 */       ds = new QueryResultDataSource(listaReporte, fields);
/*  52:    */     }
/*  53:    */     catch (Exception e)
/*  54:    */     {
/*  55: 85 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos") + ">>>>>>");
/*  56:    */     }
/*  57: 88 */     return ds;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected Map<String, Object> getReportParameters()
/*  61:    */   {
/*  62: 99 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  63:100 */     reportParameters.put("ReportTitle", "Gastos por Importación");
/*  64:101 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  65:102 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  66:103 */     return reportParameters;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected String getCompileFileName()
/*  70:    */   {
/*  71:113 */     return this.COMPILE_FILE_NAME;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String execute()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:123 */       validaFechas();
/*  79:124 */       super.prepareReport();
/*  80:    */     }
/*  81:    */     catch (JRException e)
/*  82:    */     {
/*  83:127 */       e.printStackTrace();
/*  84:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos" + e.getMessage() + "1"));
/*  85:    */     }
/*  86:    */     catch (IOException e)
/*  87:    */     {
/*  88:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos" + e.getMessage() + "2"));
/*  89:    */     }
/*  90:132 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   private void validaFechas()
/*  94:    */   {
/*  95:136 */     if (this.fechaDesde == null) {
/*  96:137 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  97:    */     }
/*  98:139 */     if (this.fechaHasta == null) {
/*  99:140 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 104:    */   {
/* 105:145 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Date getFechaDesde()
/* 109:    */   {
/* 110:157 */     return this.fechaDesde;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setFechaDesde(Date fechaDesde)
/* 114:    */   {
/* 115:167 */     this.fechaDesde = fechaDesde;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Date getFechaHasta()
/* 119:    */   {
/* 120:176 */     return this.fechaHasta;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setFechaHasta(Date fechaHasta)
/* 124:    */   {
/* 125:186 */     this.fechaHasta = fechaHasta;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Empresa getProveedor()
/* 129:    */   {
/* 130:195 */     return this.proveedor;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setProveedor(Empresa proveedor)
/* 134:    */   {
/* 135:205 */     this.proveedor = proveedor;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Pais getPaisOrigen()
/* 139:    */   {
/* 140:214 */     return this.paisOrigen;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPaisOrigen(Pais paisOrigen)
/* 144:    */   {
/* 145:224 */     this.paisOrigen = paisOrigen;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<Pais> getListaPais()
/* 149:    */   {
/* 150:229 */     if (this.listaPais == null) {
/* 151:230 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", true, null);
/* 152:    */     }
/* 153:232 */     return this.listaPais;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.reportes.ReporteFacturaProveedorImportacionGastoBean
 * JD-Core Version:    0.7.0.1
 */