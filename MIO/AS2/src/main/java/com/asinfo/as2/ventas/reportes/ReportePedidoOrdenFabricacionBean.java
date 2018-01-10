/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.controller.EmpresaBean;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ManagedProperty;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  23:    */ import net.sf.jasperreports.engine.JRException;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ReportePedidoOrdenFabricacionBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -6197604472302717804L;
/*  32:    */   private Empresa empresa;
/*  33:    */   private Date fechaDesde;
/*  34:    */   private Date fechaHasta;
/*  35:    */   private List<Empresa> listaClientes;
/*  36: 40 */   private final String COMPILE_FILE_NAME = "pedidoSaldoPorDespachar";
/*  37: 42 */   private boolean indicadorSaldosPendientes = true;
/*  38:    */   @EJB
/*  39:    */   private ServicioEmpresa servicioEmpresa;
/*  40:    */   @EJB
/*  41:    */   private ServicioReporteVenta servicioReporteVenta;
/*  42:    */   @ManagedProperty("#{empresaBean}")
/*  43:    */   private EmpresaBean empresaBean;
/*  44:    */   
/*  45:    */   protected JRDataSource getJRDataSource()
/*  46:    */   {
/*  47: 61 */     List listaDatosReporte = new ArrayList();
/*  48: 62 */     JRDataSource ds = null;
/*  49:    */     try
/*  50:    */     {
/*  51: 64 */       int idEmpresa = 0;
/*  52: 65 */       if (this.empresa == null)
/*  53:    */       {
/*  54: 66 */         this.empresa = new Empresa();
/*  55: 67 */         idEmpresa = 0;
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59: 69 */         idEmpresa = this.empresa.getIdEmpresa();
/*  60:    */       }
/*  61: 71 */       listaDatosReporte = this.servicioReporteVenta.getReportePedidoOrdenFabricacion(this.fechaDesde, this.fechaHasta, idEmpresa, this.indicadorSaldosPendientes, 
/*  62: 72 */         AppUtil.getOrganizacion().getId());
/*  63:    */       
/*  64: 74 */       String[] fields = { "numeroPedido", "nombreCliente", "nombreProducto", "volumenProducto", "pesoProducto", "totalPedido", "totalFacturado", "totalDespachado" };
/*  65:    */       
/*  66: 76 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  67:    */     }
/*  68:    */     catch (Exception e)
/*  69:    */     {
/*  70: 78 */       LOG.info("Error " + e);
/*  71: 79 */       e.printStackTrace();
/*  72:    */     }
/*  73: 81 */     return ds;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected String getCompileFileName()
/*  77:    */   {
/*  78: 91 */     return "pedidoSaldoPorDespachar";
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Map<String, Object> getReportParameters()
/*  82:    */   {
/*  83:101 */     if (this.empresa.getIdEmpresa() == -1) {
/*  84:102 */       getEmpresa().setNombreFiscal("");
/*  85:    */     }
/*  86:105 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  87:106 */     reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_estado_cuenta_titulo"));
/*  88:107 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  89:108 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  90:    */     
/*  91:110 */     return reportParameters;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String execute()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:120 */       super.prepareReport();
/*  99:    */     }
/* 100:    */     catch (JRException e)
/* 101:    */     {
/* 102:122 */       LOG.info("Error JRException");
/* 103:123 */       e.printStackTrace();
/* 104:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 105:    */     }
/* 106:    */     catch (IOException e)
/* 107:    */     {
/* 108:126 */       LOG.info("Error IOException");
/* 109:127 */       e.printStackTrace();
/* 110:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 111:    */     }
/* 112:130 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Empresa getEmpresa()
/* 116:    */   {
/* 117:134 */     if (this.empresa == null) {
/* 118:135 */       this.empresa = new Empresa();
/* 119:    */     }
/* 120:137 */     return this.empresa;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setEmpresa(Empresa empresa)
/* 124:    */   {
/* 125:141 */     this.empresa = empresa;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Date getFechaDesde()
/* 129:    */   {
/* 130:145 */     return this.fechaDesde;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setFechaDesde(Date fechaDesde)
/* 134:    */   {
/* 135:149 */     this.fechaDesde = fechaDesde;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Date getFechaHasta()
/* 139:    */   {
/* 140:153 */     return this.fechaHasta;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setFechaHasta(Date fechaHasta)
/* 144:    */   {
/* 145:157 */     this.fechaHasta = fechaHasta;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<Empresa> getListaClientes()
/* 149:    */   {
/* 150:161 */     if (this.listaClientes == null) {
/* 151:162 */       this.listaClientes = this.servicioEmpresa.obtenerClientes();
/* 152:    */     }
/* 153:164 */     return this.listaClientes;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setListaClientes(List<Empresa> listaClientes)
/* 157:    */   {
/* 158:168 */     this.listaClientes = listaClientes;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public EmpresaBean getEmpresaBean()
/* 162:    */   {
/* 163:172 */     return this.empresaBean;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setEmpresaBean(EmpresaBean empresaBean)
/* 167:    */   {
/* 168:176 */     this.empresaBean = empresaBean;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isIndicadorSaldosPendientes()
/* 172:    */   {
/* 173:185 */     return this.indicadorSaldosPendientes;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setIndicadorSaldosPendientes(boolean indicadorSaldosPendientes)
/* 177:    */   {
/* 178:195 */     this.indicadorSaldosPendientes = indicadorSaldosPendientes;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePedidoOrdenFabricacionBean
 * JD-Core Version:    0.7.0.1
 */