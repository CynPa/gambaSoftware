/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   7:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.IOException;
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
/*  25:    */ public class ReporteFacturacionSRIBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 9190611298827300034L;
/*  29:    */   private int idFacturaProveedorSRI;
/*  30:    */   private String COMPILE_FILE_NAME;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioDocumento servicioDocumento;
/*  35: 58 */   public static final String[] fields = { "nombreProveedor", "direccionProveedor", "identificacionProveedor", "fechaEmision", "numeroComprobante", "baseImponibleRetencion", "nombreConceptoRetencion", "codigoConceptoRetencion", "tipoConceptoRetencion", "porcentajeRetencion", "valorRetencion", "tipoComprobante", "ejercicioFiscal", "telefonoProveedor", "mes", "dia", "ciudad", "provincia", "f_autorizacionRetencion", "f_razonSocial", "f_identificacionOrganizacion", "f_fechaAutorizacion", "f_claveAcceso", "f_numeroResolucionContribuyente", "f_indicadorObligadoContabilidad", "f_direccionMatriz", "f_direccionSucursal", "f_ambiente", "f_tipoEmision", "f_numeroComprobanteRemision", "f_fechaEmisionComprobanteModificado", "f_email", "f_fechaEmision", "f_autorizacionCompra" };
/*  36:    */   
/*  37:    */   public String execute()
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 69 */       cargarNombreReporte();
/*  42:    */       
/*  43: 71 */       super.prepareReport();
/*  44:    */     }
/*  45:    */     catch (JRException e)
/*  46:    */     {
/*  47: 75 */       e.printStackTrace();
/*  48:    */     }
/*  49:    */     catch (IOException e)
/*  50:    */     {
/*  51: 78 */       e.printStackTrace();
/*  52:    */     }
/*  53: 81 */     return "";
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected Map<String, Object> getReportParameters()
/*  57:    */   {
/*  58: 92 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  59: 93 */     reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_reporte_asiento_contable_titulo"));
/*  60: 94 */     reportParameters.put("p_formatoFecha", getFormatoFecha());
/*  61: 95 */     return reportParameters;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected JRDataSource getJRDataSource()
/*  65:    */   {
/*  66:106 */     JRDataSource ds = null;
/*  67:    */     try
/*  68:    */     {
/*  69:109 */       List<Object[]> listaDatosReporte = this.servicioFacturaProveedorSRI.getReporteFacturaProveedorSRI(getIdFacturaProveedorSRI());
/*  70:110 */       for (Object[] a : listaDatosReporte)
/*  71:    */       {
/*  72:111 */         String delimeter = "-";
/*  73:    */         
/*  74:113 */         String[] temp = a[4].toString().split(delimeter);
/*  75:114 */         if (temp.length == 3)
/*  76:    */         {
/*  77:115 */           String numeroComprobante = "";
/*  78:116 */           numeroComprobante = temp[0] + "-" + temp[1] + "-" + FuncionesUtiles.completarALaIzquierda('0', 9, temp[2].toString());
/*  79:117 */           a[4] = numeroComprobante;
/*  80:    */         }
/*  81:    */       }
/*  82:121 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  83:    */     }
/*  84:    */     catch (ExcepcionAS2 e)
/*  85:    */     {
/*  86:124 */       LOG.info("Error " + e);
/*  87:125 */       e.printStackTrace();
/*  88:    */     }
/*  89:127 */     return ds;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected String getCompileFileName()
/*  93:    */   {
/*  94:137 */     return this.COMPILE_FILE_NAME;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void cargarNombreReporte()
/*  98:    */   {
/*  99:141 */     List<Documento> listaDocumento = null;
/* 100:142 */     FacturaProveedorSRI facturaPrveedorSRI = this.servicioFacturaProveedorSRI.cargarDetalle(this.idFacturaProveedorSRI);
/* 101:143 */     Documento documento = null;
/* 102:144 */     if (facturaPrveedorSRI.getDocumento() == null)
/* 103:    */     {
/* 104:    */       try
/* 105:    */       {
/* 106:146 */         listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.RETENCION_PROVEEDOR);
/* 107:    */       }
/* 108:    */       catch (ExcepcionAS2 e)
/* 109:    */       {
/* 110:148 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 111:    */       }
/* 112:152 */       if (listaDocumento.size() > 0) {
/* 113:153 */         documento = (Documento)listaDocumento.get(0);
/* 114:    */       }
/* 115:    */     }
/* 116:    */     else
/* 117:    */     {
/* 118:156 */       documento = facturaPrveedorSRI.getDocumento();
/* 119:    */     }
/* 120:158 */     if (!documento.getReporte().isEmpty())
/* 121:    */     {
/* 122:159 */       this.COMPILE_FILE_NAME = documento.getReporte();
/* 123:160 */       setDocumentoElectronico(documento.isIndicadorDocumentoElectronico());
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getIdFacturaProveedorSRI()
/* 128:    */   {
/* 129:165 */     return this.idFacturaProveedorSRI;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setIdFacturaProveedorSRI(int idFacturaProveedorSRI)
/* 133:    */   {
/* 134:169 */     this.idFacturaProveedorSRI = idFacturaProveedorSRI;
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.ReporteFacturacionSRIBean
 * JD-Core Version:    0.7.0.1
 */