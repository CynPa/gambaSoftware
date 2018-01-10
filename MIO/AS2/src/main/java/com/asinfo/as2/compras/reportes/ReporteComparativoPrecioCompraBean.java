/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   6:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   7:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.RequestScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import net.sf.jasperreports.engine.JRException;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @RequestScoped
/*  23:    */ public class ReporteComparativoPrecioCompraBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @EJB
/*  28:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  29:    */   @EJB
/*  30:    */   private ServicioReporteCompra servicioReporteCompra;
/*  31:    */   private TipoProducto tipoProducto;
/*  32:    */   private SubcategoriaProducto subcategoriaProducto;
/*  33:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  34:    */   private List<TipoProducto> listaTipoProducto;
/*  35:    */   
/*  36:    */   protected JRDataSource getJRDataSource()
/*  37:    */   {
/*  38: 73 */     List listaDatosReporte = new ArrayList();
/*  39: 74 */     JRDataSource ds = null;
/*  40:    */     try
/*  41:    */     {
/*  42: 76 */       listaDatosReporte = this.servicioReporteCompra.getReporteComparativoPrecioCompra(getSubcategoriaProducto().getId(), this.tipoProducto);
/*  43:    */       
/*  44: 78 */       String[] fields = { "f_codigoProveedor", "f_identificacion", "f_nombreComercial", "f_nombreFiscal", "f_codigoCategoriaProducto", "f_nombreCategoriaProducto", "f_codigoSubcategoriaProducto", "f_nombreSubcategoriaProducto", "f_codigoProducto", "f_nombreProducto", "f_precio", "f_fecha" };
/*  45:    */       
/*  46:    */ 
/*  47:    */ 
/*  48: 82 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  49:    */     }
/*  50:    */     catch (Exception e)
/*  51:    */     {
/*  52: 84 */       LOG.info("Error " + e);
/*  53:    */     }
/*  54: 86 */     return ds;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String getCompileFileName()
/*  58:    */   {
/*  59: 96 */     return "reporteComparativoPrecioCompra";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<String, Object> getReportParameters()
/*  63:    */   {
/*  64:107 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  65:108 */     reportParameters.put("ReportTitle", "Reporte Comparativo Precio Compras");
/*  66:109 */     return reportParameters;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String execute()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:119 */       super.prepareReport();
/*  74:    */     }
/*  75:    */     catch (JRException e)
/*  76:    */     {
/*  77:121 */       LOG.info("Error JRException");
/*  78:122 */       e.printStackTrace();
/*  79:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81:    */     catch (IOException e)
/*  82:    */     {
/*  83:125 */       LOG.info("Error IOException");
/*  84:126 */       e.printStackTrace();
/*  85:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  86:    */     }
/*  87:130 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public TipoProducto getTipoProducto()
/*  91:    */   {
/*  92:143 */     return this.tipoProducto;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setTipoProducto(TipoProducto tipoProducto)
/*  96:    */   {
/*  97:153 */     this.tipoProducto = tipoProducto;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 101:    */   {
/* 102:162 */     if (this.subcategoriaProducto == null) {
/* 103:163 */       this.subcategoriaProducto = new SubcategoriaProducto();
/* 104:    */     }
/* 105:165 */     return this.subcategoriaProducto;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 109:    */   {
/* 110:175 */     this.subcategoriaProducto = subcategoriaProducto;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 114:    */   {
/* 115:184 */     if (this.listaSubcategoriaProducto == null) {
/* 116:185 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 117:    */     }
/* 118:187 */     return this.listaSubcategoriaProducto;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 122:    */   {
/* 123:197 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<TipoProducto> getListaTipoProducto()
/* 127:    */   {
/* 128:206 */     if (this.listaTipoProducto == null)
/* 129:    */     {
/* 130:207 */       this.listaTipoProducto = new ArrayList();
/* 131:208 */       for (TipoProducto tipoProducto : TipoProducto.values()) {
/* 132:209 */         this.listaTipoProducto.add(tipoProducto);
/* 133:    */       }
/* 134:    */     }
/* 135:212 */     return this.listaTipoProducto;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setListaTipoProducto(List<TipoProducto> listaTipoProducto)
/* 139:    */   {
/* 140:222 */     this.listaTipoProducto = listaTipoProducto;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteComparativoPrecioCompraBean
 * JD-Core Version:    0.7.0.1
 */