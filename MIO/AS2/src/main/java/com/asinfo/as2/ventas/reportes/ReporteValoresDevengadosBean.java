/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import javax.faces.model.SelectItem;
/*  21:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  22:    */ import net.sf.jasperreports.engine.JRException;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ReporteValoresDevengadosBean
/*  28:    */   extends AbstractBaseReportBean
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioContratoVenta servicioContratoVenta;
/*  33:    */   @EJB
/*  34:    */   private ServicioEmpresa servicioEmpresa;
/*  35: 46 */   private final String COMPILE_FILE_NAME = "reporteValoresDevengados";
/*  36:    */   private Date fecha;
/*  37:    */   private Date fechaHasta;
/*  38:    */   private Empresa empresa;
/*  39:    */   
/*  40:    */   private static enum enumTipoFecha
/*  41:    */   {
/*  42: 53 */     FACTURA,  DEVENGADO;
/*  43:    */     
/*  44:    */     private enumTipoFecha() {}
/*  45:    */   }
/*  46:    */   
/*  47: 55 */   private enumTipoFecha tipoFecha = enumTipoFecha.DEVENGADO;
/*  48:    */   private List<SelectItem> listaTipoFecha;
/*  49:    */   
/*  50:    */   protected JRDataSource getJRDataSource()
/*  51:    */   {
/*  52: 64 */     List listaDatosReporte = new ArrayList();
/*  53: 65 */     JRDataSource ds = null;
/*  54:    */     try
/*  55:    */     {
/*  56: 67 */       listaDatosReporte = this.servicioContratoVenta.getValoresDevengados(getFecha(), getFechaHasta(), getEmpresa(), AppUtil.getOrganizacion(), AppUtil.getSucursal(), this.tipoFecha != enumTipoFecha.DEVENGADO);
/*  57:    */       
/*  58: 69 */       String[] fields = { "numeroContrato", "numeroFactura", "numeroNotaCredito", "fecha", "valor_x_devengar", "nombreCliente", "devengado", "fechaComprobante" };
/*  59: 70 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  60:    */     }
/*  61:    */     catch (ExcepcionAS2 e)
/*  62:    */     {
/*  63: 72 */       LOG.info("Error " + e);
/*  64: 73 */       e.printStackTrace();
/*  65:    */     }
/*  66: 75 */     return ds;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected Map<String, Object> getReportParameters()
/*  70:    */   {
/*  71: 83 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  72: 84 */     reportParameters.put("ReportTitle", "Reporte Valores Devengados");
/*  73: 85 */     reportParameters.put("fecha", FuncionesUtiles.getFechaFinMes(getFecha()));
/*  74:    */     
/*  75: 87 */     return reportParameters;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected String getCompileFileName()
/*  79:    */   {
/*  80: 95 */     return "reporteValoresDevengados";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String execute()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:104 */       super.prepareReport();
/*  88:    */     }
/*  89:    */     catch (JRException e)
/*  90:    */     {
/*  91:106 */       LOG.info("Error JRException");
/*  92:107 */       e.printStackTrace();
/*  93:108 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  94:    */     }
/*  95:    */     catch (IOException e)
/*  96:    */     {
/*  97:110 */       LOG.info("Error IOException");
/*  98:111 */       e.printStackTrace();
/*  99:112 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 100:    */     }
/* 101:115 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Date getFecha()
/* 105:    */   {
/* 106:124 */     if (this.fecha == null) {
/* 107:125 */       this.fecha = FuncionesUtiles.getFechaInicioMes(new Date());
/* 108:    */     }
/* 109:128 */     return this.fecha;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Date getFechaHasta()
/* 113:    */   {
/* 114:132 */     if (this.fechaHasta == null) {
/* 115:133 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(new Date());
/* 116:    */     }
/* 117:137 */     return this.fechaHasta;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFecha(Date fecha)
/* 121:    */   {
/* 122:145 */     this.fecha = fecha;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Empresa getEmpresa()
/* 126:    */   {
/* 127:154 */     if (this.empresa == null)
/* 128:    */     {
/* 129:155 */       this.empresa = new Empresa();
/* 130:156 */       this.empresa.setId(0);
/* 131:    */     }
/* 132:158 */     return this.empresa;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setEmpresa(Empresa empresa)
/* 136:    */   {
/* 137:165 */     this.empresa = empresa;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 141:    */   {
/* 142:174 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setFechaHasta(Date fechaHasta)
/* 146:    */   {
/* 147:178 */     this.fechaHasta = fechaHasta;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<SelectItem> getListaTipoFecha()
/* 151:    */   {
/* 152:182 */     if (this.listaTipoFecha == null)
/* 153:    */     {
/* 154:183 */       this.listaTipoFecha = new ArrayList();
/* 155:184 */       for (enumTipoFecha tr : enumTipoFecha.values()) {
/* 156:185 */         this.listaTipoFecha.add(new SelectItem(tr, tr.name()));
/* 157:    */       }
/* 158:    */     }
/* 159:188 */     return this.listaTipoFecha;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaOpcionesFecha(List<SelectItem> listaTipoFecha)
/* 163:    */   {
/* 164:192 */     this.listaTipoFecha = listaTipoFecha;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public enumTipoFecha getTipoFecha()
/* 168:    */   {
/* 169:196 */     return this.tipoFecha;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setTipoFecha(enumTipoFecha tipoFecha)
/* 173:    */   {
/* 174:200 */     this.tipoFecha = tipoFecha;
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteValoresDevengadosBean
 * JD-Core Version:    0.7.0.1
 */