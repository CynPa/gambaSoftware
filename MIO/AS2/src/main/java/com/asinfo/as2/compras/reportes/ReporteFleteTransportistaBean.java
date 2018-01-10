/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.Transportista;
/*   6:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*   7:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Calendar;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  23:    */ import net.sf.jasperreports.engine.JRException;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ReporteFleteTransportistaBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 3615647209557356681L;
/*  32:    */   @EJB
/*  33:    */   private transient ServicioRegistroPeso servicioRegistroPeso;
/*  34:    */   @EJB
/*  35:    */   private transient ServicioTransportista servicioTransportista;
/*  36:    */   private Date fechaDesde;
/*  37:    */   private Date fechaHasta;
/*  38: 59 */   private boolean indicadorLiquidados = true;
/*  39: 60 */   private boolean indicadorPorLiquidar = true;
/*  40:    */   private List<Transportista> listaTransportista;
/*  41:    */   private Transportista transportista;
/*  42:    */   
/*  43:    */   protected JRDataSource getJRDataSource()
/*  44:    */   {
/*  45: 72 */     List listaDatosReporte = new ArrayList();
/*  46: 73 */     JRDataSource ds = null;
/*  47:    */     
/*  48: 75 */     listaDatosReporte = this.servicioRegistroPeso.getReporteFleteTransportistas(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, this.transportista, this.indicadorLiquidados, this.indicadorPorLiquidar);
/*  49:    */     
/*  50:    */ 
/*  51: 78 */     String[] fields = { "f_fechaPeso", "f_identificacionTransportista", "f_nombreTransportista", "f_vehiculo", "f_numeroPeso", "f_numeroCompra", "f_numeroFactura", "f_indicadorLiquidado", "f_pesoNeto" };
/*  52:    */     
/*  53:    */ 
/*  54: 81 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  55: 82 */     return ds;
/*  56:    */   }
/*  57:    */   
/*  58:    */   @PostConstruct
/*  59:    */   public void init()
/*  60:    */   {
/*  61: 87 */     Calendar calfechaDesde = Calendar.getInstance();
/*  62: 88 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  63: 89 */     this.fechaDesde = calfechaDesde.getTime();
/*  64: 90 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected String getCompileFileName()
/*  68:    */   {
/*  69:100 */     return "reporteFleteTransportista";
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected Map<String, Object> getReportParameters()
/*  73:    */   {
/*  74:111 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  75:112 */     reportParameters.put("ReportTitle", "Reporte Flete Transportistas");
/*  76:113 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/*  77:114 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/*  78:115 */     reportParameters.put("p_indicadorLiquidados", Boolean.valueOf(this.indicadorLiquidados));
/*  79:116 */     reportParameters.put("p_indicadorPorLiquidar", Boolean.valueOf(this.indicadorPorLiquidar));
/*  80:117 */     if (this.transportista != null) {
/*  81:118 */       reportParameters.put("p_transportista", this.transportista.getNombre());
/*  82:    */     } else {
/*  83:120 */       reportParameters.put("p_transportista", "Todos");
/*  84:    */     }
/*  85:123 */     return reportParameters;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String execute()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:134 */       if (this.fechaDesde == null) {
/*  93:135 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  94:    */       }
/*  95:137 */       if (this.fechaHasta == null) {
/*  96:138 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/*  97:    */       }
/*  98:141 */       super.prepareReport();
/*  99:    */     }
/* 100:    */     catch (JRException e)
/* 101:    */     {
/* 102:143 */       LOG.info("Error JRException");
/* 103:144 */       e.printStackTrace();
/* 104:145 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 105:    */     }
/* 106:    */     catch (IOException e)
/* 107:    */     {
/* 108:147 */       LOG.info("Error IOException");
/* 109:148 */       e.printStackTrace();
/* 110:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 111:    */     }
/* 112:152 */     return null;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Date getFechaDesde()
/* 116:    */   {
/* 117:156 */     return this.fechaDesde;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFechaDesde(Date fechaDesde)
/* 121:    */   {
/* 122:160 */     this.fechaDesde = fechaDesde;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Date getFechaHasta()
/* 126:    */   {
/* 127:164 */     return this.fechaHasta;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setFechaHasta(Date fechaHasta)
/* 131:    */   {
/* 132:168 */     this.fechaHasta = fechaHasta;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isIndicadorLiquidados()
/* 136:    */   {
/* 137:172 */     return this.indicadorLiquidados;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setIndicadorLiquidados(boolean indicadorLiquidados)
/* 141:    */   {
/* 142:176 */     this.indicadorLiquidados = indicadorLiquidados;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isIndicadorPorLiquidar()
/* 146:    */   {
/* 147:180 */     return this.indicadorPorLiquidar;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIndicadorPorLiquidar(boolean indicadorPorLiquidar)
/* 151:    */   {
/* 152:184 */     this.indicadorPorLiquidar = indicadorPorLiquidar;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<Transportista> getListaTransportista()
/* 156:    */   {
/* 157:188 */     if (this.listaTransportista == null)
/* 158:    */     {
/* 159:189 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 160:190 */       filters.put("activo", "=true");
/* 161:    */       
/* 162:192 */       filters.put("empresa.idEmpresa", "!= 0");
/* 163:193 */       this.listaTransportista = this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 164:    */     }
/* 165:195 */     return this.listaTransportista;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Transportista getTransportista()
/* 169:    */   {
/* 170:199 */     return this.transportista;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setTransportista(Transportista transportista)
/* 174:    */   {
/* 175:203 */     this.transportista = transportista;
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteFleteTransportistaBean
 * JD-Core Version:    0.7.0.1
 */