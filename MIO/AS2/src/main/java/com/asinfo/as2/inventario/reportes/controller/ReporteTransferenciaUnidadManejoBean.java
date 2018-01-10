/*  1:   */ package com.asinfo.as2.inventario.reportes.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.MovimientoUnidadManejo;
/*  4:   */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoUnidadManejo;
/*  5:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  6:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  7:   */ import java.io.IOException;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.faces.bean.ManagedBean;
/* 12:   */ import javax.faces.bean.RequestScoped;
/* 13:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 14:   */ import net.sf.jasperreports.engine.JRException;
/* 15:   */ 
/* 16:   */ @ManagedBean
/* 17:   */ @RequestScoped
/* 18:   */ public class ReporteTransferenciaUnidadManejoBean
/* 19:   */   extends AbstractBaseReportBean
/* 20:   */ {
/* 21:   */   private static final long serialVersionUID = 1L;
/* 22:   */   @EJB
/* 23:   */   private ServicioReporteMovimientoUnidadManejo servicioReporteMovimientoUnidadManejo;
/* 24:   */   private MovimientoUnidadManejo movimientoUnidadManejo;
/* 25:   */   
/* 26:   */   public MovimientoUnidadManejo getMovimientoUnidadManejo()
/* 27:   */   {
/* 28:42 */     return this.movimientoUnidadManejo;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setMovimientoUnidadManejo(MovimientoUnidadManejo movimientoUnidadManejo)
/* 32:   */   {
/* 33:46 */     this.movimientoUnidadManejo = movimientoUnidadManejo;
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected JRDataSource getJRDataSource()
/* 37:   */   {
/* 38:52 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 39:53 */     JRDataSource ds = null;
/* 40:54 */     String[] fields = { "f_documentoNombre", "f_movimientoCodigo", "f_sucursal", "f_transportista", "f_cliente", "f_subcliente", "f_sucursalD", "f_transportistaD", "f_clienteD", "f_subclienteD", "f_movimientoFecha", "f_movimientoDescripcion", "f_unidadMenjoCodigo", "f_unidadManejoNombre", "f_cantidad", "f_operacion" };
/* 41:   */     
/* 42:   */ 
/* 43:57 */     listaDatosReporte = this.servicioReporteMovimientoUnidadManejo.getReporteTransferencia(getMovimientoUnidadManejo());
/* 44:58 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 45:59 */     return ds;
/* 46:   */   }
/* 47:   */   
/* 48:   */   protected String getCompileFileName()
/* 49:   */   {
/* 50:64 */     return "reporteTransferenciaUnidadManejo";
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String execute()
/* 54:   */   {
/* 55:   */     try
/* 56:   */     {
/* 57:71 */       super.prepareReport();
/* 58:   */     }
/* 59:   */     catch (JRException e)
/* 60:   */     {
/* 61:74 */       e.printStackTrace();
/* 62:   */     }
/* 63:   */     catch (IOException e)
/* 64:   */     {
/* 65:76 */       e.printStackTrace();
/* 66:   */     }
/* 67:78 */     return null;
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteTransferenciaUnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */