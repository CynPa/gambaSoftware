/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   6:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
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
/*  21:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  22:    */ import net.sf.jasperreports.engine.JRException;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ public class ReporteCierreCirculoBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  32:    */   @EJB
/*  33:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  34:    */   private Date fechaDesde;
/*  35:    */   private Date fechaHasta;
/*  36:    */   private SubcategoriaProducto subcategoriaProducto;
/*  37:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  38:    */   
/*  39:    */   protected JRDataSource getJRDataSource()
/*  40:    */   {
/*  41: 63 */     List listaDatosReporte = new ArrayList();
/*  42: 64 */     JRDataSource ds = null;
/*  43:    */     try
/*  44:    */     {
/*  45: 67 */       listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteCierreCirculo(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, this.subcategoriaProducto);
/*  46:    */       
/*  47:    */ 
/*  48: 70 */       String[] fields = { "f_codigoMateriaPrima", "f_nombreMateriaPrima", "f_loteMateriaPrima", "f_proveedorMateriaPrima", "f_cantidadMateriaPrimaRecibida", "f_cantidadMateriaPrimaUtilizada", "f_cantidadMateriaPrimaDesecho", "f_codigoProducto", "f_nombreProducto", "f_loteProducto", "f_cantidadProductoProducido", "f_cantidadProductoDespachado", "f_cantidadProductoDevuelto" };
/*  49:    */       
/*  50:    */ 
/*  51:    */ 
/*  52: 74 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  53:    */     }
/*  54:    */     catch (Exception e)
/*  55:    */     {
/*  56: 76 */       e.printStackTrace();
/*  57:    */     }
/*  58: 78 */     return ds;
/*  59:    */   }
/*  60:    */   
/*  61:    */   @PostConstruct
/*  62:    */   public void init()
/*  63:    */   {
/*  64: 83 */     Calendar calfechaDesde = Calendar.getInstance();
/*  65: 84 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  66: 85 */     this.fechaDesde = calfechaDesde.getTime();
/*  67: 86 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected String getCompileFileName()
/*  71:    */   {
/*  72: 91 */     return "reporteCierreCirculo";
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected Map<String, Object> getReportParameters()
/*  76:    */   {
/*  77: 97 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  78: 98 */     reportParameters.put("ReportTitle", "Reporte Cierre Circulo");
/*  79: 99 */     reportParameters.put("fechaDesde", this.fechaDesde);
/*  80:100 */     reportParameters.put("fechaHasta", this.fechaHasta);
/*  81:101 */     reportParameters.put("subcategoriaProducto", this.subcategoriaProducto == null ? "" : this.subcategoriaProducto.getNombre());
/*  82:102 */     return reportParameters;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String execute()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:111 */       validaDatos();
/*  90:112 */       super.prepareReport();
/*  91:    */     }
/*  92:    */     catch (JRException e)
/*  93:    */     {
/*  94:115 */       LOG.info("Error JRException");
/*  95:116 */       e.printStackTrace();
/*  96:117 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  97:    */     }
/*  98:    */     catch (IOException e)
/*  99:    */     {
/* 100:119 */       LOG.info("Error IOException");
/* 101:120 */       e.printStackTrace();
/* 102:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 103:    */     }
/* 104:124 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void validaDatos()
/* 108:    */   {
/* 109:128 */     if (this.fechaDesde == null) {
/* 110:129 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 111:    */     }
/* 112:131 */     if (this.fechaHasta == null) {
/* 113:132 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Date getFechaDesde()
/* 118:    */   {
/* 119:142 */     return this.fechaDesde;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setFechaDesde(Date fechaDesde)
/* 123:    */   {
/* 124:152 */     this.fechaDesde = fechaDesde;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Date getFechaHasta()
/* 128:    */   {
/* 129:161 */     return this.fechaHasta;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setFechaHasta(Date fechaHasta)
/* 133:    */   {
/* 134:171 */     this.fechaHasta = fechaHasta;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 138:    */   {
/* 139:180 */     if (this.subcategoriaProducto == null) {
/* 140:181 */       this.subcategoriaProducto = new SubcategoriaProducto();
/* 141:    */     }
/* 142:183 */     return this.subcategoriaProducto;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 146:    */   {
/* 147:193 */     this.subcategoriaProducto = subcategoriaProducto;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 151:    */   {
/* 152:202 */     if (this.listaSubcategoriaProducto == null) {
/* 153:203 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 154:    */     }
/* 155:205 */     return this.listaSubcategoriaProducto;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 159:    */   {
/* 160:215 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 161:    */   }
/* 162:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteCierreCirculoBean
 * JD-Core Version:    0.7.0.1
 */