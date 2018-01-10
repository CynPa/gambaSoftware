/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   6:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*   7:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReporteFabricacion;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.Serializable;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import net.sf.jasperreports.engine.JRException;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ReporteCostoFabricacionBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -6109947204471031028L;
/*  31:    */   private Date fechaDesde;
/*  32:    */   private Date fechaHasta;
/*  33:    */   private List<OrdenFabricacion> ordenesFabricacion;
/*  34:    */   private int idOrdenFabricacionSeleccionada;
/*  35:    */   private OrdenFabricacion ordenFabricacionSeleccionada;
/*  36:    */   @EJB
/*  37:    */   private ServicioReporteFabricacion servicioReporte;
/*  38:    */   @EJB
/*  39:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init() {}
/*  43:    */   
/*  44:    */   public void fechasCambiadasListener()
/*  45:    */   {
/*  46: 74 */     if ((null != this.fechaDesde) && (null != this.fechaHasta)) {
/*  47: 75 */       this.ordenesFabricacion = this.servicioOrdenFabricacion.getListaOrdenesLanzadas(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void ordenFabricacionSeleccionadaListener()
/*  52:    */   {
/*  53: 83 */     if (this.idOrdenFabricacionSeleccionada > 0) {
/*  54: 84 */       for (OrdenFabricacion ordenFabricacion : this.ordenesFabricacion) {
/*  55: 85 */         if (ordenFabricacion.getIdOrdenFabricacion() == this.idOrdenFabricacionSeleccionada)
/*  56:    */         {
/*  57: 86 */           this.ordenFabricacionSeleccionada = ordenFabricacion;
/*  58: 87 */           break;
/*  59:    */         }
/*  60:    */       }
/*  61:    */     } else {
/*  62: 91 */       this.ordenFabricacionSeleccionada = null;
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected JRDataSource getJRDataSource()
/*  67:    */   {
/*  68: 98 */     List listaDatosReporte = new ArrayList();
/*  69: 99 */     JRDataSource ds = null;
/*  70:    */     try
/*  71:    */     {
/*  72:102 */       listaDatosReporte = this.servicioReporte.getReporteCostoFabricacion(this.fechaDesde, this.fechaHasta, this.ordenFabricacionSeleccionada);
/*  73:    */       
/*  74:104 */       String[] fields = { "f_numeroOrdenFabricacion", "f_codigoProducto", "f_nombreProducto", "f_cantidadInventario", "f_costoMateriales", "f_costoDepreciaciones", "f_costoManoDeObra", "f_costoIndirecto", "f_costo" };
/*  75:    */       
/*  76:    */ 
/*  77:107 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81:110 */       e.printStackTrace();
/*  82:    */     }
/*  83:112 */     return ds;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected String getCompileFileName()
/*  87:    */   {
/*  88:117 */     return "reporteCostoFabricacion";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String execute()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:123 */       super.prepareReport();
/*  96:    */     }
/*  97:    */     catch (JRException e)
/*  98:    */     {
/*  99:125 */       LOG.info("Error JRException");
/* 100:126 */       e.printStackTrace();
/* 101:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 102:    */     }
/* 103:    */     catch (IOException e)
/* 104:    */     {
/* 105:129 */       LOG.info("Error IOException");
/* 106:130 */       e.printStackTrace();
/* 107:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 108:    */     }
/* 109:133 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Date getFechaDesde()
/* 113:    */   {
/* 114:137 */     return this.fechaDesde;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setFechaDesde(Date fechaDesde)
/* 118:    */   {
/* 119:141 */     this.fechaDesde = fechaDesde;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Date getFechaHasta()
/* 123:    */   {
/* 124:145 */     return this.fechaHasta;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setFechaHasta(Date fechaHasta)
/* 128:    */   {
/* 129:149 */     this.fechaHasta = fechaHasta;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<OrdenFabricacion> getOrdenesFabricacion()
/* 133:    */   {
/* 134:153 */     return this.ordenesFabricacion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setOrdenesFabricacion(List<OrdenFabricacion> ordenesFabricacion)
/* 138:    */   {
/* 139:157 */     this.ordenesFabricacion = ordenesFabricacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getIdOrdenFabricacionSeleccionada()
/* 143:    */   {
/* 144:161 */     return this.idOrdenFabricacionSeleccionada;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setIdOrdenFabricacionSeleccionada(int idOrdenFabricacionSeleccionada)
/* 148:    */   {
/* 149:165 */     this.idOrdenFabricacionSeleccionada = idOrdenFabricacionSeleccionada;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public OrdenFabricacion getOrdenFabricacionSeleccionada()
/* 153:    */   {
/* 154:169 */     return this.ordenFabricacionSeleccionada;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setOrdenFabricacionSeleccionada(OrdenFabricacion ordenFabricacionSeleccionada)
/* 158:    */   {
/* 159:173 */     this.ordenFabricacionSeleccionada = ordenFabricacionSeleccionada;
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteCostoFabricacionBean
 * JD-Core Version:    0.7.0.1
 */