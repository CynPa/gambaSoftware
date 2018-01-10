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
/*  25:    */ public class ReporteDespachoClienteBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -192264607856793455L;
/*  29:    */   @EJB
/*  30:    */   private ServicioDespachoCliente servicioDespachoCliente;
/*  31:    */   private DespachoCliente despachoCliente;
/*  32: 49 */   private final String COMPILE_FILE_NAME = "despachoCliente";
/*  33:    */   
/*  34:    */   protected JRDataSource getJRDataSource()
/*  35:    */   {
/*  36: 59 */     List listaDatosReporte = new ArrayList();
/*  37: 60 */     JRDataSource ds = null;
/*  38:    */     try
/*  39:    */     {
/*  40: 62 */       listaDatosReporte = this.servicioDespachoCliente.getReporteDespachoCliente(getDespachoCliente(), AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/*  41:    */       
/*  42: 64 */       String[] fields = { "numeroDespacho", "nombreComercial", "fechaDespacho", "nombreProducto", "cantidadProducto", "nombreBodega", "pesoProducto", "volumenProducto", "codigoUnidad", "codigoProducto", "codigoLote", "nombreCiudad", "nombreFiscal", "descripcionDespacho", "numeroFactura", "numeroPedido", "nombreProyecto", "estado", "descripcion", "sub_cliente", "sub_cliente_codigo", "f_codigoAlternoProducto", "f_documento", "f_responsable", "f_precioUltimaCompra", "f_categoriaCliente", "f_proveedor", "f_categoriaImpuesto", "f_precioListaPrecios", "idListaPrecios", "idProducto", "f_descripcionDocumento", "f_usuarioCreacion" };
/*  43: 70 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos() > 0)
/*  44:    */       {
/*  45: 72 */         fields = new String[72];
/*  46:    */         
/*  47: 74 */         fields[0] = "numeroDespacho";
/*  48: 75 */         fields[1] = "nombreComercial";
/*  49: 76 */         fields[2] = "fechaDespacho";
/*  50: 77 */         fields[3] = "nombreProducto";
/*  51: 78 */         fields[4] = "cantidadProducto";
/*  52: 79 */         fields[5] = "nombreBodega";
/*  53: 80 */         fields[6] = "pesoProducto";
/*  54: 81 */         fields[7] = "volumenProducto";
/*  55: 82 */         fields[8] = "codigoUnidad";
/*  56: 83 */         fields[9] = "codigoProducto";
/*  57: 84 */         fields[10] = "codigoLote";
/*  58: 85 */         fields[11] = "nombreCiudad";
/*  59: 86 */         fields[12] = "nombreFiscal";
/*  60: 87 */         fields[13] = "descripcionDespacho";
/*  61: 88 */         fields[14] = "numeroFactura";
/*  62: 89 */         fields[15] = "numeroPedido";
/*  63: 90 */         fields[16] = "nombreProyecto";
/*  64: 91 */         fields[17] = "estado";
/*  65: 92 */         fields[18] = "descripcion";
/*  66: 93 */         fields[19] = "sub_cliente";
/*  67: 94 */         fields[20] = "sub_cliente_codigo";
/*  68: 95 */         fields[21] = "f_codigoAlternoProducto";
/*  69:    */         
/*  70: 97 */         fields[22] = "f_documento";
/*  71: 98 */         fields[23] = "f_responsable";
/*  72: 99 */         fields[24] = "f_precioUltimaCompra";
/*  73:100 */         fields[25] = "f_categoriaCliente";
/*  74:101 */         fields[26] = "f_proveedor";
/*  75:102 */         fields[27] = "f_categoriaImpuesto";
/*  76:103 */         fields[28] = "f_precioListaPrecios";
/*  77:104 */         fields[29] = "idListaPrecios";
/*  78:105 */         fields[30] = "idProducto";
/*  79:106 */         fields[31] = "f_descripcionDocumento";
/*  80:107 */         fields[32] = "f_usuarioCreacion";
/*  81:    */         
/*  82:109 */         fields[33] = "codigoAtributoOFA";
/*  83:110 */         fields[34] = "nombreAtributoOFA";
/*  84:111 */         fields[35] = "codigoValorAtributoOFA";
/*  85:112 */         fields[36] = "nombreValorAtributoOFA";
/*  86:    */         
/*  87:114 */         int index = 37;
/*  88:115 */         for (int i = 1; i <= AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos() - 1; i++)
/*  89:    */         {
/*  90:116 */           fields[(index++)] = ("codigoAtributo" + i);
/*  91:117 */           fields[(index++)] = ("nombreAtributo" + i);
/*  92:118 */           fields[(index++)] = ("codigoValorAtributo" + i);
/*  93:119 */           fields[(index++)] = ("nombreValorAtributo" + i);
/*  94:    */         }
/*  95:    */       }
/*  96:123 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  97:    */     }
/*  98:    */     catch (ExcepcionAS2 e)
/*  99:    */     {
/* 100:125 */       LOG.info("Error " + e);
/* 101:126 */       e.printStackTrace();
/* 102:    */     }
/* 103:128 */     return ds;
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected String getCompileFileName()
/* 107:    */   {
/* 108:138 */     return "despachoCliente";
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected Map<String, Object> getReportParameters()
/* 112:    */   {
/* 113:149 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 114:150 */     reportParameters.put("ReportTitle", "Despacho Cliente");
/* 115:151 */     if (getDespachoCliente() != null) {
/* 116:152 */       reportParameters.put("nombreUsuario", getDespachoCliente().getUsuarioCreacion().trim());
/* 117:    */     }
/* 118:154 */     return reportParameters;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String execute()
/* 122:    */   {
/* 123:161 */     if (getDespachoCliente().getId() == 0) {
/* 124:162 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 125:    */     } else {
/* 126:    */       try
/* 127:    */       {
/* 128:165 */         super.prepareReport();
/* 129:    */       }
/* 130:    */       catch (JRException e)
/* 131:    */       {
/* 132:167 */         LOG.info("Error JRException");
/* 133:168 */         e.printStackTrace();
/* 134:169 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 135:    */       }
/* 136:    */       catch (IOException e)
/* 137:    */       {
/* 138:171 */         LOG.info("Error IOException");
/* 139:172 */         e.printStackTrace();
/* 140:173 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 141:    */       }
/* 142:    */     }
/* 143:176 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public DespachoCliente getDespachoCliente()
/* 147:    */   {
/* 148:180 */     if (this.despachoCliente == null) {
/* 149:181 */       this.despachoCliente = new DespachoCliente();
/* 150:    */     }
/* 151:183 */     return this.despachoCliente;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setDespachoCliente(DespachoCliente despachoCliente)
/* 155:    */   {
/* 156:187 */     this.despachoCliente = despachoCliente;
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteDespachoClienteBean
 * JD-Core Version:    0.7.0.1
 */