/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
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
/*  28:    */ public class ReporteNotaCreditoFinancieraBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1599721133141713459L;
/*  32:    */   @EJB
/*  33:    */   private ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  34:    */   private Empresa empresa;
/*  35:    */   private FacturaCliente facturaCliente;
/*  36: 54 */   private String COMPILE_FILE_NAME = "reporteNotaCreditoCliente";
/*  37: 55 */   private boolean indicadorImpreso = true;
/*  38: 56 */   private int numeroImpresiones = 1;
/*  39: 58 */   public static final String[] fields = { "nombreFiscal", "direccionEmpresa", "identificacion", "fechaFactura", "cantidadProducto", "codigoProducto", "nombreProducto", "precioProducto", "subTotalFactura", "descuentoFactura", "impuestoTotal", "telefonoEmpresa", "descripcionFactura", "anioFecha", "mesFecha", "diaFecha", "descripcionProducto", "codigoCliente", "ciudadCliente", "unidad", "peso", "codigoAlterno", "codigoComercial", "codigoBarras", "numeroFactura", "nombreComercial", "numeroFacturaPadre", "motivoNotaCredito", "f_lote", "f_numeroDespacho", "f_autorizacion", "f_fechaAutorizacion", "f_fechaCaducidad", "f_numeroCopia", "f_idFactura", "f_usuarioCreacion", "f_fechaCreacion", "f_items", "f_razonSocial", "f_identificacionOrganizacion", "f_fechaAutorizacion", "f_claveAcceso", "f_numeroResolucionContribuyente", "f_indicadorObligadoContabilidad", "f_direccionMatriz", "f_direccionSucursal", "f_ambiente", "f_tipoEmision", "f_numeroPadre", "f_fechaPadre", "f_motivoNCredito", "f_email", "baseImponibleTarifaCero", "baseImponibleDiferenteCero", "descuentoUnitario", "f_pesoLinea", "f_precioLinea", "f_descuentoLinea", "f_montoIRBPNR", "f_montoIVA", "f_nombreCampoXML1", "f_referencia8", "f_nombreCampoXML2", "f_referencia9", "monto_ice", "f_invoice", "f_descuentoImpuesto", "f_formaPago", " ", "f_agenteComercial", "f_referencia8Devolucion" };
/*  40:    */   
/*  41:    */   protected JRDataSource getJRDataSource()
/*  42:    */   {
/*  43: 78 */     List listaDatosReporte = new ArrayList();
/*  44: 79 */     JRDataSource ds = null;
/*  45:    */     try
/*  46:    */     {
/*  47: 82 */       listaDatosReporte = this.servicioNotaCreditoCliente.getReporteNotaCreditoCliente(getFacturaCliente().getIdFacturaCliente(), 
/*  48: 83 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion(), DocumentoBase.NOTA_CREDITO_CLIENTE, false, this.numeroImpresiones, this.indicadorImpreso);
/*  49:    */       
/*  50:    */ 
/*  51: 86 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  52: 87 */       setIndicadorImpreso(true);
/*  53: 88 */       setNumeroImpresiones(1);
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57: 90 */       LOG.info("Error ", e);
/*  58: 91 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  59:    */     }
/*  60: 93 */     return ds;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected String getCompileFileName()
/*  64:    */   {
/*  65:104 */     if (!this.facturaCliente.getDocumento().getReporte().isEmpty()) {
/*  66:105 */       this.COMPILE_FILE_NAME = this.facturaCliente.getDocumento().getReporte();
/*  67:    */     }
/*  68:108 */     return this.COMPILE_FILE_NAME;
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected Map<String, Object> getReportParameters()
/*  72:    */   {
/*  73:119 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  74:120 */     reportParameters.put("ReportTitle", "Factura Cliente");
/*  75:121 */     reportParameters.put("p_indicadorNotaCredito", Boolean.valueOf(true));
/*  76:122 */     reportParameters.put("p_indicadorAnulado", Boolean.valueOf(this.facturaCliente.getEstado().equals(Estado.ANULADO)));
/*  77:123 */     return reportParameters;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String execute()
/*  81:    */   {
/*  82:131 */     setDocumentoElectronico(this.facturaCliente.getDocumento().isIndicadorDocumentoElectronico());
/*  83:    */     try
/*  84:    */     {
/*  85:134 */       LOG.info("Nombre del archivo + " + this.COMPILE_FILE_NAME);
/*  86:135 */       super.prepareReport();
/*  87:    */     }
/*  88:    */     catch (JRException e)
/*  89:    */     {
/*  90:138 */       LOG.info("Error JRException");
/*  91:139 */       e.printStackTrace();
/*  92:140 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  93:    */     }
/*  94:    */     catch (IOException e)
/*  95:    */     {
/*  96:142 */       LOG.info("Error IOException");
/*  97:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  98:    */     }
/*  99:146 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Empresa getEmpresa()
/* 103:    */   {
/* 104:150 */     return this.empresa;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setEmpresa(Empresa empresa)
/* 108:    */   {
/* 109:154 */     this.empresa = empresa;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public FacturaCliente getFacturaCliente()
/* 113:    */   {
/* 114:158 */     if (this.facturaCliente == null) {
/* 115:159 */       this.facturaCliente = new FacturaCliente();
/* 116:    */     }
/* 117:161 */     return this.facturaCliente;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 121:    */   {
/* 122:165 */     this.facturaCliente = facturaCliente;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isIndicadorImpreso()
/* 126:    */   {
/* 127:169 */     return this.indicadorImpreso;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setIndicadorImpreso(boolean indicadorImpreso)
/* 131:    */   {
/* 132:173 */     this.indicadorImpreso = indicadorImpreso;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getNumeroImpresiones()
/* 136:    */   {
/* 137:177 */     return this.numeroImpresiones;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setNumeroImpresiones(int numeroImpresiones)
/* 141:    */   {
/* 142:181 */     this.numeroImpresiones = numeroImpresiones;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteNotaCreditoFinancieraBean
 * JD-Core Version:    0.7.0.1
 */