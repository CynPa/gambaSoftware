/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  23:    */ import net.sf.jasperreports.engine.JRException;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ReporteFacturaClienteBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1599721133141713459L;
/*  32:    */   @EJB
/*  33:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  34:    */   private Empresa empresa;
/*  35:    */   protected FacturaCliente facturaCliente;
/*  36: 54 */   private String COMPILE_FILE_NAME = "reporteFacturaCliente";
/*  37: 55 */   private boolean indicadorDetallado = false;
/*  38: 56 */   private boolean indicadorImpreso = true;
/*  39: 57 */   private int numeroImpresiones = 1;
/*  40: 59 */   public static final String[] fields = { "nombreFiscal", "direccionEmpresa", "identificacion", "fechaFactura", "cantidadProducto", "codigoProducto", "nombreProducto", "precioProducto", "subTotalFactura", "descuentoFactura", "impuestoTotal", "telefonoEmpresa", "descripcionFactura", "anioFecha", "mesFecha", "diaFecha", "descripcionProducto", "codigoCliente", "ciudadCliente", "condicionPago", "vendedor", "unidad", "peso", "numeroGuia", "referencia1", "referencia2", "codigoAlterno", "codigoComercial", "codigoBarras", "numeroFactura", "zona", "canal", "fechaVencimiento", "referencia", "nombreComercial", "baseImponibleTarifaCero", "baseImponibleDiferenteCero", "descuentoUnitario", "f_autorizacion", "f_fechaAutorizacion", "f_fechaCaducidad", "f_numeroCopia", "f_numeroFactura", "f_idFactura", "f_usuarioCreacion", "f_fechaCreacion", "f_lote", "f_provincia", "f_referencia", "f_despacho", "f_items", "f_telefonoEmpresa2", "f_notaProducto", "f_pais", "f_codigoVenta", "f_razonSocial", "f_identificacionOrganizacion", "f_fechaAutorizacion", "f_claveAcceso", "f_numeroResolucionContribuyente", "f_indicadorObligadoContabilidad", "f_direccionMatriz", "f_direccionSucursal", "f_ambiente", "f_tipoEmision", "f_numeroPadre", "f_fechaPadre", "f_motivoNCredito", "f_email", "f_pesoLinea", "f_precioLinea", "f_descuentoLinea", "f_listaDescuentos", "f_nandina", "f_guiaMadre", "f_dae", "f_guiaHija", "f_agenciaCarga", "f_pedido", "f_lote", "f_despacho", "f_porcientoDescuento", "f_cantidad2", "f_notaProducto", "f_descripcion2", "f_montoIRBPNR", "f_montoIVA", "nombreProductoNoComercial", "f_valorReferencia1", "f_valorReferencia2", "f_empresaFinal", "f_referencia7", "f_referencia8", "f_referencia9", "f_codigoAlternoCliente", "f_nombreCampoXML", "f_nombreCampoXML2", "f_referencia10", "f_autorizacionGR", "f_claveAccesoGR", "f_conductor", "f_identificacionConductor", "f_placa", "f_nombreCiudadOrigen", "f_fechaOrigen", "f_nombreCiudadDestino", "f_fechaVigencia", "f_fechaAutorizacion", "f_codigoSubempresa", "subsidio", "totalSubsidio", "f_formaPagoSRI", "diasPlazo", "f_identificacion_transportista", "f_nombre_transportista", "monto_ice", "mesesPlazo", "f_descuentoImpuesto", "f_contratos" };
/*  41:    */   
/*  42:    */   protected JRDataSource getJRDataSource()
/*  43:    */   {
/*  44: 86 */     List listaDatosReporte = new ArrayList();
/*  45: 87 */     JRDataSource ds = null;
/*  46:    */     try
/*  47:    */     {
/*  48: 90 */       procesarDetalle();
/*  49: 92 */       if (getFacturaCliente().getDocumento().isIndicadorDocumentoElectronico()) {
/*  50: 93 */         this.indicadorDetallado = true;
/*  51:    */       }
/*  52: 96 */       listaDatosReporte = this.servicioFacturaCliente.getReporteFacturaCliente(getFacturaCliente().getIdFacturaCliente(), 
/*  53: 97 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion(), this.numeroImpresiones, this.indicadorDetallado, this.indicadorImpreso, 
/*  54: 98 */         AppUtil.getOrganizacion().getId());
/*  55:    */       
/*  56:100 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  57:101 */       setIndicadorImpreso(true);
/*  58:102 */       setNumeroImpresiones(1);
/*  59:    */     }
/*  60:    */     catch (ExcepcionAS2 e)
/*  61:    */     {
/*  62:104 */       LOG.info("Error " + e);
/*  63:105 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  64:    */     }
/*  65:107 */     return ds;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void procesarDetalle()
/*  69:    */   {
/*  70:111 */     setIndicadorDetallado(false);
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected String getCompileFileName()
/*  74:    */   {
/*  75:121 */     if (!this.facturaCliente.getDocumento().getReporte().isEmpty()) {
/*  76:122 */       this.COMPILE_FILE_NAME = this.facturaCliente.getDocumento().getReporte();
/*  77:    */     }
/*  78:124 */     return this.COMPILE_FILE_NAME;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Map<String, Object> getReportParameters()
/*  82:    */   {
/*  83:136 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  84:137 */     reportParameters.put("ReportTitle", "Factura Cliente");
/*  85:138 */     reportParameters.put("p_numeroResolucionContribuyente", AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/*  86:139 */       .getNumeroResolucionContribuyente());
/*  87:140 */     reportParameters.put("p_indicadorAnulado", Boolean.valueOf(getFacturaCliente().getEstado().equals(Estado.ANULADO)));
/*  88:    */     
/*  89:142 */     return reportParameters;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String execute()
/*  93:    */   {
/*  94:149 */     if (getFacturaCliente().getId() == 0)
/*  95:    */     {
/*  96:150 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  97:    */     }
/*  98:    */     else
/*  99:    */     {
/* 100:153 */       setDocumentoElectronico(this.facturaCliente.getDocumento().isIndicadorDocumentoElectronico());
/* 101:155 */       if ((getFacturaCliente().getEstado().equals(Estado.ANULADO)) && (!isIndicadorAutoimpresor())) {
/* 102:156 */         addErrorMessage(getLanguageController().getMensaje("msg_error_imprimir_comprobante_anulado"));
/* 103:    */       } else {
/* 104:    */         try
/* 105:    */         {
/* 106:161 */           super.prepareReport();
/* 107:    */         }
/* 108:    */         catch (JRException e)
/* 109:    */         {
/* 110:164 */           LOG.info("Error JRException");
/* 111:165 */           e.printStackTrace();
/* 112:166 */           addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 113:    */         }
/* 114:    */         catch (IOException e)
/* 115:    */         {
/* 116:168 */           LOG.info("Error IOException");
/* 117:169 */           addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:173 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Empresa getEmpresa()
/* 125:    */   {
/* 126:177 */     return this.empresa;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setEmpresa(Empresa empresa)
/* 130:    */   {
/* 131:181 */     this.empresa = empresa;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public FacturaCliente getFacturaCliente()
/* 135:    */   {
/* 136:185 */     if (this.facturaCliente == null) {
/* 137:186 */       this.facturaCliente = new FacturaCliente();
/* 138:    */     }
/* 139:188 */     return this.facturaCliente;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 143:    */   {
/* 144:192 */     this.facturaCliente = facturaCliente;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isIndicadorDetallado()
/* 148:    */   {
/* 149:196 */     return this.indicadorDetallado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIndicadorDetallado(boolean indicadorDetallado)
/* 153:    */   {
/* 154:200 */     this.indicadorDetallado = indicadorDetallado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isIndicadorImpreso()
/* 158:    */   {
/* 159:204 */     return this.indicadorImpreso;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIndicadorImpreso(boolean indicadorImpreso)
/* 163:    */   {
/* 164:208 */     this.indicadorImpreso = indicadorImpreso;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getNumeroImpresiones()
/* 168:    */   {
/* 169:212 */     return this.numeroImpresiones;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setNumeroImpresiones(int numeroImpresiones)
/* 173:    */   {
/* 174:216 */     this.numeroImpresiones = numeroImpresiones;
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteFacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */