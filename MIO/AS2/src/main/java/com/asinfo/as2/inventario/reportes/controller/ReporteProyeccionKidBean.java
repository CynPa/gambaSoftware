/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.clases.ProyeccionKid;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   9:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteProyeccionKids;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.RequestScoped;
/*  20:    */ import javax.persistence.Temporal;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @RequestScoped
/*  30:    */ public class ReporteProyeccionKidBean
/*  31:    */   extends AbstractBaseReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 770222109067650882L;
/*  34:    */   @EJB
/*  35:    */   private transient ServicioReporteProyeccionKids servicioReporteProyeccionKids;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioBodega servicioBodega;
/*  38: 53 */   private final String COMPILE_FILE_NAME = "reporteProyeccionKid";
/*  39:    */   private Producto producto;
/*  40:    */   private Bodega bodega;
/*  41:    */   private List<Bodega> listaBodega;
/*  42:    */   @Temporal(TemporalType.DATE)
/*  43:    */   @NotNull
/*  44: 57 */   private Date fechaDesde = new Date();
/*  45:    */   @Temporal(TemporalType.DATE)
/*  46:    */   @NotNull
/*  47: 61 */   private Date fechaHasta = new Date();
/*  48:    */   
/*  49:    */   protected JRDataSource getJRDataSource()
/*  50:    */   {
/*  51: 67 */     List<ProyeccionKid> listaDatosReporte = new ArrayList();
/*  52: 68 */     JRDataSource ds = null;
/*  53:    */     try
/*  54:    */     {
/*  55: 70 */       listaDatosReporte = this.servicioReporteProyeccionKids.getListaProyeccionKids(AppUtil.getOrganizacion().getId(), this.producto, this.bodega, this.fechaDesde, this.fechaHasta);
/*  56:    */       
/*  57: 72 */       ds = new JRBeanCollectionDataSource(listaDatosReporte);
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61: 74 */       e.printStackTrace();
/*  62:    */     }
/*  63: 76 */     return ds;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getCompileFileName()
/*  67:    */   {
/*  68: 81 */     return "reporteProyeccionKid";
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected Map<String, Object> getReportParameters()
/*  72:    */   {
/*  73: 86 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  74: 87 */     reportParameters.put("ReportTitle", "Proyeccion Kids");
/*  75: 88 */     reportParameters.put("p_producto", this.producto != null ? this.producto.getNombre() : "Todos");
/*  76: 89 */     reportParameters.put("p_bodega", this.bodega != null ? this.bodega.getNombre() : "Todos");
/*  77: 90 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/*  78: 91 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/*  79: 92 */     return reportParameters;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String execute()
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:100 */       super.prepareReport();
/*  87:    */     }
/*  88:    */     catch (JRException e)
/*  89:    */     {
/*  90:102 */       LOG.info("Error JRException");
/*  91:103 */       e.printStackTrace();
/*  92:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  93:    */     }
/*  94:    */     catch (IOException e)
/*  95:    */     {
/*  96:106 */       LOG.info("Error IOException");
/*  97:107 */       e.printStackTrace();
/*  98:108 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  99:    */     }
/* 100:111 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void cargarProducto(Producto producto)
/* 104:    */   {
/* 105:115 */     this.producto = producto;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void limpiarProducto()
/* 109:    */   {
/* 110:119 */     this.producto = new Producto();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Date getFechaDesde()
/* 114:    */   {
/* 115:128 */     return this.fechaDesde;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setFechaDesde(Date fechaDesde)
/* 119:    */   {
/* 120:138 */     this.fechaDesde = fechaDesde;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Date getFechaHasta()
/* 124:    */   {
/* 125:147 */     return this.fechaHasta;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setFechaHasta(Date fechaHasta)
/* 129:    */   {
/* 130:157 */     this.fechaHasta = fechaHasta;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Bodega getBodega()
/* 134:    */   {
/* 135:166 */     return this.bodega;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setBodega(Bodega bodega)
/* 139:    */   {
/* 140:176 */     this.bodega = bodega;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<Bodega> getListaBodega()
/* 144:    */   {
/* 145:185 */     if (this.listaBodega == null) {
/* 146:186 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, null);
/* 147:    */     }
/* 148:188 */     return this.listaBodega;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Producto getProducto()
/* 152:    */   {
/* 153:197 */     return this.producto;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setProducto(Producto producto)
/* 157:    */   {
/* 158:207 */     this.producto = producto;
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteProyeccionKidBean
 * JD-Core Version:    0.7.0.1
 */