/*   1:    */ package com.asinfo.as2.compras.importaciones.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   6:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   7:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.math.BigDecimal;
/*  11:    */ import java.math.RoundingMode;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Collection;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  20:    */ import net.sf.jasperreports.engine.JRException;
/*  21:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ReportePresupuestoFacturaProveedorImportacionBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioFacturaProveedorImportacion servicioFacturaProveedorImportacion;
/*  32:    */   private FacturaProveedorImportacion facturaProveedorImportacion;
/*  33: 59 */   private BigDecimal dolarImportacion = BigDecimal.ZERO;
/*  34:    */   
/*  35:    */   protected JRDataSource getJRDataSource()
/*  36:    */   {
/*  37: 67 */     JRDataSource ds = null;
/*  38:    */     try
/*  39:    */     {
/*  40: 70 */       if (getFacturaProveedorImportacion().getIdFacturaProveedorImportacion() > 0)
/*  41:    */       {
/*  42: 71 */         this.facturaProveedorImportacion = this.servicioFacturaProveedorImportacion.cargarDetalle(getFacturaProveedorImportacion().getId());
/*  43: 72 */         BigDecimal total = BigDecimal.ZERO;
/*  44: 73 */         BigDecimal totalGastoReal = BigDecimal.ZERO;
/*  45: 74 */         for (DetalleFacturaProveedor dfp : this.facturaProveedorImportacion.getFacturaProveedor().getListaDetalleFacturaProveedor())
/*  46:    */         {
/*  47: 75 */           total = total.add(dfp.getPrecioLinea());
/*  48: 76 */           totalGastoReal = totalGastoReal.add(dfp.getGastoReal());
/*  49:    */         }
/*  50: 78 */         if (total.compareTo(BigDecimal.ZERO) != 0) {
/*  51: 79 */           this.dolarImportacion = totalGastoReal.divide(total, 6, RoundingMode.HALF_UP);
/*  52:    */         }
/*  53: 81 */         Object lista = new ArrayList();
/*  54: 82 */         ((List)lista).add(this.facturaProveedorImportacion);
/*  55: 83 */         ds = new JRBeanCollectionDataSource((Collection)lista);
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59: 85 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  60:    */       }
/*  61:    */     }
/*  62:    */     catch (Exception e)
/*  63:    */     {
/*  64: 89 */       LOG.info("Error " + e);
/*  65: 90 */       e.printStackTrace();
/*  66:    */     }
/*  67: 92 */     return ds;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected String getCompileFileName()
/*  71:    */   {
/*  72: 97 */     return "reporteFacturaImportacion";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String execute()
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79:103 */       super.prepareReport();
/*  80:    */     }
/*  81:    */     catch (JRException e)
/*  82:    */     {
/*  83:107 */       e.printStackTrace();
/*  84:    */     }
/*  85:    */     catch (IOException e)
/*  86:    */     {
/*  87:110 */       e.printStackTrace();
/*  88:    */     }
/*  89:112 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected Map<String, Object> getReportParameters()
/*  93:    */   {
/*  94:117 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  95:118 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  96:    */     
/*  97:120 */     reportParameters.put("SUBREPORT_DIRhg", getPathReportes());
/*  98:121 */     reportParameters.put("dolarImportacion", this.dolarImportacion);
/*  99:    */     
/* 100:123 */     return reportParameters;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public FacturaProveedorImportacion getFacturaProveedorImportacion()
/* 104:    */   {
/* 105:131 */     return this.facturaProveedorImportacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 109:    */   {
/* 110:135 */     this.facturaProveedorImportacion = facturaProveedorImportacion;
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.reportes.ReportePresupuestoFacturaProveedorImportacionBean
 * JD-Core Version:    0.7.0.1
 */