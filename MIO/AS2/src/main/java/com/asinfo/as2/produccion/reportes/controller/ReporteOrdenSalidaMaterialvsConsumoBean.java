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
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  20:    */ import net.sf.jasperreports.engine.JRException;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class ReporteOrdenSalidaMaterialvsConsumoBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -3267343209386176432L;
/*  30:    */   private Date fechaDesde;
/*  31:    */   private Date fechaHasta;
/*  32:    */   private List<OrdenFabricacion> ordenesFabricacion;
/*  33:    */   private int idOrdenFabricacionSeleccionada;
/*  34:    */   private OrdenFabricacion ordenFabricacionSeleccionada;
/*  35:    */   @EJB
/*  36:    */   private ServicioReporteFabricacion servicioReporte;
/*  37:    */   @EJB
/*  38:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  39:    */   
/*  40:    */   public void fechasCambiadasListener()
/*  41:    */   {
/*  42: 69 */     if ((null != this.fechaDesde) && (null != this.fechaHasta)) {
/*  43: 70 */       this.ordenesFabricacion = this.servicioOrdenFabricacion.getListaOrdenesLanzadas(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void ordenFabricacionSeleccionadaListener()
/*  48:    */   {
/*  49: 78 */     if (this.idOrdenFabricacionSeleccionada > 0) {
/*  50: 79 */       for (OrdenFabricacion ordenFabricacion : this.ordenesFabricacion) {
/*  51: 80 */         if (ordenFabricacion.getIdOrdenFabricacion() == this.idOrdenFabricacionSeleccionada)
/*  52:    */         {
/*  53: 81 */           this.ordenFabricacionSeleccionada = ordenFabricacion;
/*  54: 82 */           break;
/*  55:    */         }
/*  56:    */       }
/*  57:    */     } else {
/*  58: 86 */       this.ordenFabricacionSeleccionada = null;
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected JRDataSource getJRDataSource()
/*  63:    */   {
/*  64: 93 */     List listaDatosReporte = new ArrayList();
/*  65: 94 */     JRDataSource ds = null;
/*  66:    */     try
/*  67:    */     {
/*  68: 97 */       listaDatosReporte = this.servicioReporte.getReporteOrdenSalidaMaterialvsConsumo(this.fechaDesde, this.fechaHasta, this.ordenFabricacionSeleccionada);
/*  69:    */       
/*  70: 99 */       String[] fields = { "f_numeroOrdenFabricacion", "f_codigoProducto", "f_nombreProducto", "f_numeroOrdenSalida", "f_cantidadOrdenSalida", "f_cantidadMovimiento" };
/*  71:    */       
/*  72:    */ 
/*  73:102 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77:105 */       e.printStackTrace();
/*  78:    */     }
/*  79:107 */     return ds;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected String getCompileFileName()
/*  83:    */   {
/*  84:112 */     return "reporteOrdenSalidaMaterialvsConsumo";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String execute()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:118 */       super.prepareReport();
/*  92:    */     }
/*  93:    */     catch (JRException e)
/*  94:    */     {
/*  95:120 */       LOG.info("Error JRException");
/*  96:121 */       e.printStackTrace();
/*  97:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  98:    */     }
/*  99:    */     catch (IOException e)
/* 100:    */     {
/* 101:124 */       LOG.info("Error IOException");
/* 102:125 */       e.printStackTrace();
/* 103:126 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 104:    */     }
/* 105:128 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Date getFechaDesde()
/* 109:    */   {
/* 110:132 */     return this.fechaDesde;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setFechaDesde(Date fechaDesde)
/* 114:    */   {
/* 115:136 */     this.fechaDesde = fechaDesde;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Date getFechaHasta()
/* 119:    */   {
/* 120:140 */     return this.fechaHasta;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setFechaHasta(Date fechaHasta)
/* 124:    */   {
/* 125:144 */     this.fechaHasta = fechaHasta;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public List<OrdenFabricacion> getOrdenesFabricacion()
/* 129:    */   {
/* 130:148 */     return this.ordenesFabricacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setOrdenesFabricacion(List<OrdenFabricacion> ordenesFabricacion)
/* 134:    */   {
/* 135:152 */     this.ordenesFabricacion = ordenesFabricacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getIdOrdenFabricacionSeleccionada()
/* 139:    */   {
/* 140:156 */     return this.idOrdenFabricacionSeleccionada;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIdOrdenFabricacionSeleccionada(int idOrdenFabricacionSeleccionada)
/* 144:    */   {
/* 145:160 */     this.idOrdenFabricacionSeleccionada = idOrdenFabricacionSeleccionada;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public OrdenFabricacion getOrdenFabricacionSeleccionada()
/* 149:    */   {
/* 150:164 */     return this.ordenFabricacionSeleccionada;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setOrdenFabricacionSeleccionada(OrdenFabricacion ordenFabricacionSeleccionada)
/* 154:    */   {
/* 155:168 */     this.ordenFabricacionSeleccionada = ordenFabricacionSeleccionada;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteOrdenSalidaMaterialvsConsumoBean
 * JD-Core Version:    0.7.0.1
 */