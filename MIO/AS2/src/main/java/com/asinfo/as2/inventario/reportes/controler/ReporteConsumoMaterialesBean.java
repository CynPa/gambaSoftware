/*   1:    */ package com.asinfo.as2.inventario.reportes.controler;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Lote;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   7:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   8:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
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
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReporteConsumoMaterialesBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 770222109067650882L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  37:    */   @EJB
/*  38:    */   private ServicioLote servicioLote;
/*  39:    */   @EJB
/*  40:    */   private ServicioProducto servicioProducto;
/*  41: 56 */   private final String COMPILE_FILE_NAME = "reporteConsumoMateriales";
/*  42:    */   @Temporal(TemporalType.DATE)
/*  43:    */   @NotNull
/*  44: 58 */   private Date fechaDesde = new Date();
/*  45:    */   @Temporal(TemporalType.DATE)
/*  46:    */   @NotNull
/*  47: 62 */   private Date fechaHasta = new Date();
/*  48: 66 */   boolean indicadorSoloCerradas = true;
/*  49:    */   private Producto producto;
/*  50:    */   private Lote lote;
/*  51:    */   
/*  52:    */   @PostConstruct
/*  53:    */   public void init()
/*  54:    */   {
/*  55: 73 */     Calendar calfechaDesde = Calendar.getInstance();
/*  56: 74 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  57: 75 */     this.fechaDesde = calfechaDesde.getTime();
/*  58: 76 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected JRDataSource getJRDataSource()
/*  62:    */   {
/*  63: 82 */     List listaDatosReporte = new ArrayList();
/*  64: 83 */     JRDataSource ds = null;
/*  65:    */     try
/*  66:    */     {
/*  67: 85 */       listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteOrdenSalidaMaterial(0, false, this.fechaDesde, this.fechaHasta, this.producto, this.lote, this.indicadorSoloCerradas, false);
/*  68:    */       
/*  69:    */ 
/*  70: 88 */       String[] fields = { "f_idDetalleOrdenSalidaMaterial", "f_numero", "f_fecha", "f_descripcionOrdenSalidaMaterial", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidadProducto", "f_cantidadDespachada", "f_cantidadADevolver", "f_cantidadDevuelta", "f_cantidadUtilizada", "f_descripcion", "f_OrdenFabricacion", "f_lote" };
/*  71:    */       
/*  72:    */ 
/*  73:    */ 
/*  74: 92 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  75:    */     }
/*  76:    */     catch (Exception e)
/*  77:    */     {
/*  78: 94 */       e.printStackTrace();
/*  79:    */     }
/*  80: 96 */     return ds;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected String getCompileFileName()
/*  84:    */   {
/*  85:101 */     return "reporteConsumoMateriales";
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected Map<String, Object> getReportParameters()
/*  89:    */   {
/*  90:107 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  91:108 */     reportParameters.put("ReportTitle", "Reporte Consumo Materiales");
/*  92:109 */     reportParameters.put("fechaDesde", getFechaDesde());
/*  93:110 */     reportParameters.put("fechaHasta", getFechaHasta());
/*  94:111 */     reportParameters.put("indicadorSoloCerradas", Boolean.valueOf(this.indicadorSoloCerradas));
/*  95:112 */     if (this.producto != null) {
/*  96:113 */       reportParameters.put("producto", this.producto.getNombre());
/*  97:    */     }
/*  98:115 */     if (this.lote != null) {
/*  99:116 */       reportParameters.put("lote", this.lote.getCodigo());
/* 100:    */     }
/* 101:118 */     return reportParameters;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<Lote> autocompletarLotes(String consulta)
/* 105:    */   {
/* 106:122 */     return this.servicioLote.autocompletarLote(getProducto(), consulta);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String execute()
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:130 */       super.prepareReport();
/* 114:    */     }
/* 115:    */     catch (JRException e)
/* 116:    */     {
/* 117:132 */       LOG.info("Error JRException");
/* 118:133 */       e.printStackTrace();
/* 119:134 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:    */     catch (IOException e)
/* 122:    */     {
/* 123:136 */       LOG.info("Error IOException");
/* 124:137 */       e.printStackTrace();
/* 125:138 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 126:    */     }
/* 127:141 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void cargarProducto(Producto producto)
/* 131:    */   {
/* 132:145 */     this.producto = producto;
/* 133:146 */     this.lote = null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Date getFechaDesde()
/* 137:    */   {
/* 138:150 */     return this.fechaDesde;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setFechaDesde(Date fechaDesde)
/* 142:    */   {
/* 143:154 */     this.fechaDesde = fechaDesde;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Date getFechaHasta()
/* 147:    */   {
/* 148:158 */     return this.fechaHasta;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setFechaHasta(Date fechaHasta)
/* 152:    */   {
/* 153:162 */     this.fechaHasta = fechaHasta;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Producto getProducto()
/* 157:    */   {
/* 158:166 */     return this.producto;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setProducto(Producto producto)
/* 162:    */   {
/* 163:170 */     this.producto = producto;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Lote getLote()
/* 167:    */   {
/* 168:174 */     return this.lote;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setLote(Lote lote)
/* 172:    */   {
/* 173:178 */     this.lote = lote;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isIndicadorSoloCerradas()
/* 177:    */   {
/* 178:182 */     return this.indicadorSoloCerradas;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setIndicadorSoloCerradas(boolean indicadorSoloCerradas)
/* 182:    */   {
/* 183:186 */     this.indicadorSoloCerradas = indicadorSoloCerradas;
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controler.ReporteConsumoMaterialesBean
 * JD-Core Version:    0.7.0.1
 */