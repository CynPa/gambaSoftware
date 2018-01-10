/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.OrdenDespachoCliente;
/*   5:    */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioOrdenDespachoCliente;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  17:    */ import net.sf.jasperreports.engine.JRException;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @ViewScoped
/*  22:    */ public class ReporteOrdenDespachoClienteBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -192264607856793455L;
/*  26:    */   @EJB
/*  27:    */   private ServicioOrdenDespachoCliente servicioOrdenDespachoCliente;
/*  28:    */   private OrdenDespachoCliente ordenDespachoClienteSimple;
/*  29:    */   private OrdenDespachoCliente ordenDespachoClienteAcumulado;
/*  30: 48 */   private final String COMPILE_FILE_NAME = "reporteOrdenDespachoCliente";
/*  31:    */   private boolean indicadorAcumulado;
/*  32:    */   
/*  33:    */   protected JRDataSource getJRDataSource()
/*  34:    */   {
/*  35: 60 */     List listaDatosReporte = new ArrayList();
/*  36: 61 */     JRDataSource ds = null;
/*  37: 62 */     listaDatosReporte = this.servicioOrdenDespachoCliente.getReporteOrdenDespachoGavetas(getOrdenDespachoCliente(), isIndicadorAcumulado());
/*  38:    */     
/*  39: 64 */     String[] fields = { "f_codigoProducto", "f_nombreProducto", "f_unidad_pedido", "f_cantidadPedido", "f_cantidadDespacho", "f_nombreGaveta", "f_numeroGavetas", "f_adicional", "f_cantidadPedidoTotal", "f_numeroDecimales" };
/*  40:    */     
/*  41:    */ 
/*  42: 67 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  43:    */     
/*  44: 69 */     return ds;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected String getCompileFileName()
/*  48:    */   {
/*  49: 79 */     return "reporteOrdenDespachoCliente";
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected Map<String, Object> getReportParameters()
/*  53:    */   {
/*  54: 90 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  55: 91 */     reportParameters.put("ReportTitle", "Orden Despacho Cliente");
/*  56: 92 */     if (!isIndicadorAcumulado()) {
/*  57: 93 */       reportParameters.put("p_numeroOrden", getOrdenDespachoCliente().getNumero());
/*  58:    */     } else {
/*  59: 95 */       reportParameters.put("p_numeroOrden", "Acumulado");
/*  60:    */     }
/*  61: 97 */     reportParameters.put("p_fechaOrden", getOrdenDespachoCliente().getFecha());
/*  62: 98 */     reportParameters.put("p_tipoOrden", getOrdenDespachoCliente().getTipoOrdenDespacho().getNombre());
/*  63:    */     
/*  64:100 */     return reportParameters;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String execute()
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:109 */       super.prepareReport();
/*  72:110 */       this.ordenDespachoClienteAcumulado = null;
/*  73:111 */       this.ordenDespachoClienteSimple = null;
/*  74:    */     }
/*  75:    */     catch (JRException e)
/*  76:    */     {
/*  77:113 */       LOG.info("Error JRException");
/*  78:114 */       e.printStackTrace();
/*  79:115 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81:    */     catch (IOException e)
/*  82:    */     {
/*  83:117 */       LOG.info("Error IOException");
/*  84:118 */       e.printStackTrace();
/*  85:119 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  86:    */     }
/*  87:121 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public OrdenDespachoCliente getOrdenDespachoCliente()
/*  91:    */   {
/*  92:125 */     if (this.ordenDespachoClienteAcumulado != null) {
/*  93:126 */       return this.ordenDespachoClienteAcumulado;
/*  94:    */     }
/*  95:128 */     if (this.ordenDespachoClienteSimple != null) {
/*  96:129 */       return this.ordenDespachoClienteSimple;
/*  97:    */     }
/*  98:131 */     return new OrdenDespachoCliente();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isIndicadorAcumulado()
/* 102:    */   {
/* 103:135 */     if (this.ordenDespachoClienteAcumulado != null) {
/* 104:136 */       this.indicadorAcumulado = true;
/* 105:    */     }
/* 106:138 */     return this.indicadorAcumulado;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIndicadorAcumulado(boolean indicadorAcumulado)
/* 110:    */   {
/* 111:142 */     this.indicadorAcumulado = indicadorAcumulado;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public OrdenDespachoCliente getOrdenDespachoClienteAcumulado()
/* 115:    */   {
/* 116:146 */     return this.ordenDespachoClienteAcumulado;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setOrdenDespachoClienteAcumulado(OrdenDespachoCliente ordenDespachoClienteAcumulado)
/* 120:    */   {
/* 121:150 */     this.ordenDespachoClienteSimple = null;
/* 122:151 */     this.ordenDespachoClienteAcumulado = ordenDespachoClienteAcumulado;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public OrdenDespachoCliente getOrdenDespachoClienteSimple()
/* 126:    */   {
/* 127:155 */     return this.ordenDespachoClienteSimple;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setOrdenDespachoClienteSimple(OrdenDespachoCliente ordenDespachoClienteSimple)
/* 131:    */   {
/* 132:159 */     this.ordenDespachoClienteAcumulado = null;
/* 133:160 */     this.ordenDespachoClienteSimple = ordenDespachoClienteSimple;
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteOrdenDespachoClienteBean
 * JD-Core Version:    0.7.0.1
 */