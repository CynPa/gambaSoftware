/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.RequestScoped;
/*  19:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  20:    */ import net.sf.jasperreports.engine.JRException;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @RequestScoped
/*  25:    */ public class ReporteDespachoResumidoBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -192264607856793455L;
/*  29:    */   @EJB
/*  30:    */   private ServicioDespachoCliente servicioDespachoCliente;
/*  31:    */   private DespachoCliente despachoCliente;
/*  32: 49 */   private final String COMPILE_FILE_NAME = "despachoClienteResumido";
/*  33:    */   
/*  34:    */   protected JRDataSource getJRDataSource()
/*  35:    */   {
/*  36: 59 */     List listaDatosReporte = new ArrayList();
/*  37: 60 */     JRDataSource ds = null;
/*  38:    */     try
/*  39:    */     {
/*  40: 62 */       listaDatosReporte = this.servicioDespachoCliente.getReporteDespachoCliente(getDespachoCliente(), 
/*  41: 63 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/*  42:    */       
/*  43: 65 */       String[] fields = { "numeroDespacho", "nombreComercial", "fechaDespacho", "nombreProducto", "cantidadProducto", "nombreBodega", "pesoProducto", "volumenProducto", "codigoUnidad", "codigoProducto", "codigoLote", "nombreCiudad", "nombreFiscal", "descripcionDespacho", "numeroFactura", "numeroPedido", "nombreProyecto", "estado", "descripcion", "sub_cliente", "sub_cliente_codigo", "f_codigoAlternoProducto" };
/*  44: 70 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos() > 0)
/*  45:    */       {
/*  46: 72 */         fields = new String[62];
/*  47:    */         
/*  48: 74 */         fields[0] = "numeroDespacho";
/*  49: 75 */         fields[1] = "nombreComercial";
/*  50: 76 */         fields[2] = "fechaDespacho";
/*  51: 77 */         fields[3] = "nombreProducto";
/*  52: 78 */         fields[4] = "cantidadProducto";
/*  53: 79 */         fields[5] = "nombreBodega";
/*  54: 80 */         fields[6] = "pesoProducto";
/*  55: 81 */         fields[7] = "volumenProducto";
/*  56: 82 */         fields[8] = "codigoUnidad";
/*  57: 83 */         fields[9] = "codigoProducto";
/*  58: 84 */         fields[10] = "codigoLote";
/*  59: 85 */         fields[11] = "nombreCiudad";
/*  60: 86 */         fields[12] = "nombreFiscal";
/*  61: 87 */         fields[13] = "descripcionDespacho";
/*  62: 88 */         fields[14] = "numeroFactura";
/*  63: 89 */         fields[15] = "numeroPedido";
/*  64: 90 */         fields[16] = "nombreProyecto";
/*  65: 91 */         fields[17] = "estado";
/*  66: 92 */         fields[18] = "descripcion";
/*  67: 93 */         fields[19] = "sub_cliente";
/*  68: 94 */         fields[20] = "sub_cliente_codigo";
/*  69: 95 */         fields[21] = "f_codigoAlternoProducto";
/*  70:    */         
/*  71: 97 */         fields[22] = "codigoAtributoOFA";
/*  72: 98 */         fields[23] = "nombreAtributoOFA";
/*  73: 99 */         fields[24] = "codigoValorAtributoOFA";
/*  74:100 */         fields[25] = "nombreValorAtributoOFA";
/*  75:    */         
/*  76:102 */         int index = 26;
/*  77:103 */         for (int i = 1; i <= AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos() - 1; i++)
/*  78:    */         {
/*  79:104 */           fields[(index++)] = ("codigoAtributo" + i);
/*  80:105 */           fields[(index++)] = ("nombreAtributo" + i);
/*  81:106 */           fields[(index++)] = ("codigoValorAtributo" + i);
/*  82:107 */           fields[(index++)] = ("nombreValorAtributo" + i);
/*  83:    */         }
/*  84:    */       }
/*  85:111 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  86:    */     }
/*  87:    */     catch (ExcepcionAS2 e)
/*  88:    */     {
/*  89:114 */       LOG.info("Error " + e);
/*  90:115 */       e.printStackTrace();
/*  91:    */     }
/*  92:117 */     return ds;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected String getCompileFileName()
/*  96:    */   {
/*  97:127 */     return "despachoClienteResumido";
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected Map<String, Object> getReportParameters()
/* 101:    */   {
/* 102:138 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 103:139 */     reportParameters.put("ReportTitle", "Titulo del reporte");
/* 104:140 */     return reportParameters;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String execute()
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:149 */       super.prepareReport();
/* 112:    */     }
/* 113:    */     catch (JRException e)
/* 114:    */     {
/* 115:151 */       LOG.info("Error JRException");
/* 116:152 */       e.printStackTrace();
/* 117:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 118:    */     }
/* 119:    */     catch (IOException e)
/* 120:    */     {
/* 121:155 */       LOG.info("Error IOException");
/* 122:156 */       e.printStackTrace();
/* 123:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 124:    */     }
/* 125:159 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public DespachoCliente getDespachoCliente()
/* 129:    */   {
/* 130:163 */     if (this.despachoCliente == null) {
/* 131:164 */       this.despachoCliente = new DespachoCliente();
/* 132:    */     }
/* 133:166 */     return this.despachoCliente;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setDespachoCliente(DespachoCliente despachoCliente)
/* 137:    */   {
/* 138:170 */     this.despachoCliente = despachoCliente;
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteDespachoResumidoBean
 * JD-Core Version:    0.7.0.1
 */