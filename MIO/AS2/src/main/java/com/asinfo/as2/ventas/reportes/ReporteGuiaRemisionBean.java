/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.GuiaRemision;
/*   6:    */ import com.asinfo.as2.entities.HojaRuta;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  13:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteGuiaRemision;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.RequestScoped;
/*  21:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  22:    */ import net.sf.jasperreports.engine.JRException;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @RequestScoped
/*  27:    */ public class ReporteGuiaRemisionBean
/*  28:    */   extends AbstractBaseReportBean
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -3029723362152385033L;
/*  31:    */   @EJB
/*  32:    */   ServicioReporteGuiaRemision servicioReporteGuiaRemision;
/*  33:    */   @EJB
/*  34:    */   ServicioGuiaRemision servicioGuiaRemision;
/*  35:    */   private Integer idDespachoCliente;
/*  36:    */   private Integer idTransferenciaBodega;
/*  37:    */   private Integer idGuiaRemision;
/*  38: 37 */   private String COMPILE_FILE_NAME = "reporteGuiaRemision";
/*  39:    */   private int hojaRuta;
/*  40:    */   private HojaRuta hojaRutaTransportista;
/*  41: 42 */   public static final String[] fields = { "f_nota", "f_documentoOrigen", "f_identificacionCliente", "f_nombreFiscal", "f_ciudadOrigen", "f_ciudadDestino", "f_numero", "f_fecha", "f_tarifa", "f_conductor", "f_licencia", "f_concepto", "f_placaVehiculo", "f_transporista", "f_identificacionTransportista", "f_codigoProducto", "f_nombreProducto", "f_peso", "f_volumen", "f_unidad", "f_cantidad", "f_descripcion", "f_numeroFactura", "f_codigoBarras", "f_direccion", "f_sucursal", "f_zona", "f_nombreComercial", "f_fechaVigencia", "f_fechaFactura", "f_numeroReferencia", "f_razonSocial", "f_identificacionOrganizacion", "f_fechaAutorizacion", "f_claveAcceso", "f_numeroResolucionContribuyente", "f_indicadorObligadoContabilidad", "f_direccionMatriz", "f_direccionSucursal", "f_ambiente", "f_tipoEmision", "f_email", "f_autorizacion", "f_autorizacionComprobanteVenta", "f_lote", "f_clienteFloricola", "f_consignatarioFloricola", "f_agenciaFloricola", "f_ruta", "f_identificacionEmpresaTB", "f_nombreEmpresaTB", "f_direccionEmpresaTB", "f_horaSalida", "f_horaLlegada", "f_telefono1", "f_motivoTraslado", "f_descripcionPedidoCliente" };
/*  42:    */   
/*  43:    */   protected JRDataSource getJRDataSource()
/*  44:    */   {
/*  45: 56 */     List listaDatosReporte = new ArrayList();
/*  46: 57 */     JRDataSource ds = null;
/*  47:    */     try
/*  48:    */     {
/*  49: 59 */       this.idDespachoCliente = Integer.valueOf(this.idDespachoCliente == null ? 0 : this.idDespachoCliente.intValue());
/*  50: 60 */       this.idTransferenciaBodega = Integer.valueOf(this.idTransferenciaBodega == null ? 0 : this.idTransferenciaBodega.intValue());
/*  51: 61 */       this.idGuiaRemision = Integer.valueOf(this.idGuiaRemision == null ? 0 : this.idGuiaRemision.intValue());
/*  52:    */       
/*  53: 63 */       listaDatosReporte = this.servicioReporteGuiaRemision.getReporteGuiaRemision(this.idDespachoCliente.intValue(), this.idTransferenciaBodega.intValue(), this.idGuiaRemision.intValue(), 
/*  54: 64 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion(), getHojaRutaTransportista());
/*  55:    */       
/*  56: 66 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  57:    */     }
/*  58:    */     catch (Exception e)
/*  59:    */     {
/*  60: 68 */       LOG.info("Error " + e);
/*  61: 69 */       e.printStackTrace();
/*  62:    */     }
/*  63: 71 */     return ds;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String execute()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70: 81 */       super.prepareReport();
/*  71:    */     }
/*  72:    */     catch (JRException e)
/*  73:    */     {
/*  74: 83 */       LOG.info("Error JRException");
/*  75: 84 */       e.printStackTrace();
/*  76: 85 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  77:    */     }
/*  78:    */     catch (IOException e)
/*  79:    */     {
/*  80: 87 */       LOG.info("Error IOException");
/*  81: 88 */       e.printStackTrace();
/*  82: 89 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86: 91 */       System.out.println("entroooooooooooooooooooooo");
/*  87: 92 */       e.printStackTrace();
/*  88:    */     }
/*  89: 95 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected String getCompileFileName()
/*  93:    */   {
/*  94:100 */     GuiaRemision guiaRemision = null;
/*  95:101 */     if (this.idDespachoCliente != null) {
/*  96:102 */       guiaRemision = this.servicioGuiaRemision.buscarPorDespacho(this.idDespachoCliente.intValue());
/*  97:    */     }
/*  98:104 */     if (this.idTransferenciaBodega != null) {
/*  99:105 */       guiaRemision = this.servicioGuiaRemision.buscarPorTransferenciaBodega(this.idTransferenciaBodega.intValue());
/* 100:    */     }
/* 101:107 */     if (this.idGuiaRemision != null) {
/* 102:108 */       guiaRemision = this.servicioGuiaRemision.cargarDetalle(this.idGuiaRemision.intValue());
/* 103:    */     }
/* 104:110 */     if (getHojaRutaTransportista() != null) {
/* 105:111 */       guiaRemision = this.servicioGuiaRemision.cargarDetalle(this.hojaRutaTransportista.getGuiaRemision().getIdGuiaRemision());
/* 106:    */     }
/* 107:114 */     if ((guiaRemision != null) && (guiaRemision.getIdHojaRuta() != 0)) {
/* 108:115 */       this.COMPILE_FILE_NAME = "guiaRemisionElectronicaFloricola";
/* 109:    */     }
/* 110:118 */     if (((guiaRemision != null) && (!guiaRemision.getDocumento().getReporte().isEmpty()) && (guiaRemision.getIdHojaRuta() == 0)) || (getHojaRutaTransportista() != null)) {
/* 111:119 */       this.COMPILE_FILE_NAME = guiaRemision.getDocumento().getReporte();
/* 112:    */     }
/* 113:122 */     return this.COMPILE_FILE_NAME;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Integer getIdDespachoCliente()
/* 117:    */   {
/* 118:129 */     return this.idDespachoCliente;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setIdDespachoCliente(Integer idDespachoCliente)
/* 122:    */   {
/* 123:137 */     this.idDespachoCliente = idDespachoCliente;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Integer getIdTransferenciaBodega()
/* 127:    */   {
/* 128:141 */     return this.idTransferenciaBodega;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setIdTransferenciaBodega(Integer idTransferenciaBodega)
/* 132:    */   {
/* 133:145 */     this.idTransferenciaBodega = idTransferenciaBodega;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Integer getIdGuiaRemision()
/* 137:    */   {
/* 138:149 */     return this.idGuiaRemision;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIdGuiaRemision(Integer idGuiaRemision)
/* 142:    */   {
/* 143:153 */     this.idGuiaRemision = idGuiaRemision;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getHojaRuta()
/* 147:    */   {
/* 148:160 */     return this.hojaRuta;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setHojaRuta(int hojaRuta)
/* 152:    */   {
/* 153:168 */     this.hojaRuta = hojaRuta;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public HojaRuta getHojaRutaTransportista()
/* 157:    */   {
/* 158:172 */     return this.hojaRutaTransportista;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setHojaRutaTransportista(HojaRuta hojaRutaTransportista)
/* 162:    */   {
/* 163:176 */     this.hojaRutaTransportista = hojaRutaTransportista;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteGuiaRemisionBean
 * JD-Core Version:    0.7.0.1
 */