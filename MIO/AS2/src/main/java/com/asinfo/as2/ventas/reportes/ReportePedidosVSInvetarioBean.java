/*  1:   */ package com.asinfo.as2.ventas.reportes;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  4:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  5:   */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Date;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 11:   */ 
/* 12:   */ public class ReportePedidosVSInvetarioBean
/* 13:   */   extends AbstractBaseReportBean
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private ServicioPedidoCliente servicioPedidoCliente;
/* 17:   */   private Date fechaHasta;
/* 18:   */   
/* 19:   */   protected JRDataSource getJRDataSource()
/* 20:   */   {
/* 21:42 */     List listaDatosReporte = new ArrayList();
/* 22:43 */     JRDataSource ds = null;
/* 23:44 */     listaDatosReporte = this.servicioPedidoCliente.getPedidosVSInventario(this.fechaHasta);
/* 24:   */     
/* 25:46 */     String[] fields = { "f_factura", "f_fecha", "f_cliente", "f_descuento", "f_cantidad", "f_codigo", "f_producto", "f_volumen", "f_peso" };
/* 26:   */     
/* 27:   */ 
/* 28:   */ 
/* 29:50 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 30:51 */     return ds;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected String getCompileFileName()
/* 34:   */   {
/* 35:60 */     return null;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Date getFechaHasta()
/* 39:   */   {
/* 40:64 */     return this.fechaHasta;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setFechaHasta(Date fechaHasta)
/* 44:   */   {
/* 45:68 */     this.fechaHasta = fechaHasta;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String execute()
/* 49:   */   {
/* 50:77 */     return null;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePedidosVSInvetarioBean
 * JD-Core Version:    0.7.0.1
 */