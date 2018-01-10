/*   1:    */ package com.asinfo.as2.compronteselectronicos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*   5:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronicoAutorizacion;
/*   6:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronicoRecepcion;
/*   7:    */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*   8:    */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*   9:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*  10:    */ import com.asinfo.as2.dao.EmpresaDao;
/*  11:    */ import com.asinfo.as2.dao.OrganizacionDao;
/*  12:    */ import com.asinfo.as2.dao.TipoContactoDao;
/*  13:    */ import com.asinfo.as2.dao.sri.ClaveAccesoPendientePublicarDao;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.Organizacion;
/*  16:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  17:    */ import com.asinfo.as2.entities.TipoContacto;
/*  18:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  19:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  20:    */ import com.asinfo.as2.util.RutaArchivo;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import com.asinfo.as2.utils.reportes.ReportConfigUtil;
/*  23:    */ import ec.gob.sri.comprobantes.ws.autorizacion.Autorizacion;
/*  24:    */ import ec.gob.sri.comprobantes.ws.autorizacion.RespuestaComprobante;
/*  25:    */ import ec.gob.sri.comprobantes.ws.autorizacion.RespuestaComprobanteAutorizaciones;
/*  26:    */ import ec.gob.sri.comprobantes.ws.recepcion.RespuestaSolicitud;
/*  27:    */ import java.io.File;
/*  28:    */ import java.io.PrintStream;
/*  29:    */ import java.text.SimpleDateFormat;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Calendar;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.List;
/*  34:    */ import java.util.Map;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.ejb.Stateless;
/*  37:    */ import net.sf.jasperreports.engine.JRException;
/*  38:    */ import net.sf.jasperreports.engine.JasperExportManager;
/*  39:    */ import net.sf.jasperreports.engine.JasperPrint;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ 
/*  42:    */ @Stateless
/*  43:    */ public class ServicioDocumentoElectronicoImpl
/*  44:    */   implements ServicioDocumentoElectronico
/*  45:    */ {
/*  46: 61 */   protected static final Logger LOG = Logger.getLogger("com.asinfo.as2");
/*  47: 62 */   private static String DIRECCTORIO_REPORTES = "/reportes/";
/*  48:    */   @EJB
/*  49:    */   private ServicioSecuenciaDocumentoElectronico servicioSecuenciaDocumentoElectronico;
/*  50:    */   @EJB
/*  51:    */   private transient ServicioEnvioEmail servicioEnvioEmail;
/*  52:    */   @EJB
/*  53:    */   private ClaveAccesoPendientePublicarDao claveAccesoPendientePublicarDao;
/*  54:    */   @EJB
/*  55:    */   private EmpresaDao empresaDao;
/*  56:    */   @EJB
/*  57:    */   private TipoContactoDao tipoContactoDao;
/*  58:    */   @EJB
/*  59:    */   private OrganizacionDao organizacionDao;
/*  60:    */   
/*  61:    */   public String autorizarDocumento(DocumentoElectronico documento)
/*  62:    */     throws AS2Exception
/*  63:    */   {
/*  64: 86 */     RespuestaSolicitud respuestaRecepcion = DocumentoElectronicoRecepcion.enviarComprobante(documento.getAmbiente(), new File(documento
/*  65: 87 */       .getPathArchivoFirmado()));
/*  66:    */     
/*  67: 89 */     documento.setEstado(EstadoDocumentoElectronico.ENVIADO);
/*  68: 91 */     if ((respuestaRecepcion != null) && (respuestaRecepcion.getEstado().equals(EstadoDocumentoElectronico.RECIBIDA.toString())))
/*  69:    */     {
/*  70: 93 */       documento.setEstado(EstadoDocumentoElectronico.RECIBIDA);
/*  71: 94 */       comprobarAutorizacionDocumento(documento);
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75: 97 */       documento.setEstado(EstadoDocumentoElectronico.NO_AUTORIZADO);
/*  76: 98 */       throw new AS2Exception(DocumentoElectronico.obtieneMensajesRecepcion(respuestaRecepcion));
/*  77:    */     }
/*  78:101 */     return documento.getNumeroAutorizacion();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String comprobarAutorizacionDocumento(DocumentoElectronico documento)
/*  82:    */     throws AS2Exception
/*  83:    */   {
/*  84:107 */     RespuestaComprobante respuesta = DocumentoElectronicoAutorizacion.autorizarComprobanteIndividual(documento);
/*  85:109 */     for (Autorizacion autorizacion : respuesta.getAutorizaciones().getAutorizacion()) {
/*  86:110 */       if (autorizacion.getEstado().equals(EstadoDocumentoElectronico.AUTORIZADO.toString()))
/*  87:    */       {
/*  88:111 */         documento.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
/*  89:112 */         documento.setFechaAutorizacion(autorizacion.getFechaAutorizacion().getTime());
/*  90:113 */         break;
/*  91:    */       }
/*  92:    */     }
/*  93:116 */     return documento.getNumeroAutorizacion();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void enviarDocumentoPorEmail(DocumentoElectronico documento, Empresa empresa)
/*  97:    */     throws AS2Exception
/*  98:    */   {
/*  99:121 */     String pathReporteDocumento = "";
/* 100:122 */     String pathReporteDocumentoPdf = "";
/* 101:123 */     pathReporteDocumento = documento.getPathArchivoAutorizado().replace(".xml", ".html");
/* 102:124 */     pathReporteDocumentoPdf = documento.getPathArchivoAutorizado().replace(".xml", ".pdf");
/* 103:125 */     if ((documento.getTipoEmision() == 2) && (
/* 104:126 */       (documento.getEstado().equals(EstadoDocumentoElectronico.EMITIDO)) || (documento.getEstado().equals(EstadoDocumentoElectronico.ENVIADO))))
/* 105:    */     {
/* 106:128 */       pathReporteDocumento = documento.getPathArchivoPendienteContingencia().replace(".xml", ".html");
/* 107:129 */       pathReporteDocumentoPdf = documento.getPathArchivoPendienteContingencia().replace(".xml", ".pdf");
/* 108:    */     }
/* 109:132 */     String tipoDocumento = documento.getTipoDocumento() == null ? "COMPROBANTE" : documento.getTipoDocumento().getNombre().toUpperCase();
/* 110:    */     
/* 111:134 */     String numeroCompleto = documento.getPathArchivoAutorizado().substring(documento.getPathArchivoAutorizado().length() - 71, documento
/* 112:135 */       .getPathArchivoAutorizado().length() - 54);
/* 113:    */     
/* 114:137 */     String subject = tipoDocumento + " No. " + numeroCompleto + " - " + documento.getOrganizacion().getRazonSocial();
/* 115:138 */     String bodyText = "Usted ha recibido un documento electrónico";
/* 116:139 */     List<String> archivos = new ArrayList();
/* 117:140 */     if (documento.getEstado().equals(EstadoDocumentoElectronico.AUTORIZADO)) {
/* 118:141 */       archivos.add(documento.getPathArchivoAutorizado());
/* 119:    */     }
/* 120:143 */     archivos.add(pathReporteDocumentoPdf);
/* 121:    */     try
/* 122:    */     {
/* 123:146 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 124:    */       
/* 125:148 */       String rutaLogoOrganizacion = RutaArchivo.getUploadDir(documento.getOrganizacion().getId(), "logo") + documento.getOrganizacion().getImagen();
/* 126:    */       
/* 127:150 */       File reportFile = new File(ReportConfigUtil.getJasperFilePath(ServicioConfiguracion.DIRECTORIO_SRI, DIRECCTORIO_REPORTES, documento
/* 128:151 */         .getNombreReporte() + ".jasper"));
/* 129:    */       
/* 130:    */ 
/* 131:154 */       Map<String, Object> parameters = new HashMap();
/* 132:155 */       parameters.put("logoEmpresa", rutaLogoOrganizacion);
/* 133:156 */       parameters.put("telefonoOrganizacion", documento.getTelefonoSucursal());
/* 134:    */       
/* 135:158 */       JasperPrint jasperPrint = ReportConfigUtil.fillReport(reportFile, parameters, documento.getDataSource());
/* 136:    */       
/* 137:160 */       JasperExportManager.exportReportToPdfFile(jasperPrint, pathReporteDocumentoPdf);
/* 138:    */       
/* 139:162 */       String tipoNotificacion = documento.getTipoDocumento().getNombre().replaceAll(" ", "");
/* 140:163 */       List<TipoContacto> listaTipoContactos = this.empresaDao.getTipoContactosPorTipoNotificacion(empresa != null ? empresa : new Empresa(), tipoNotificacion);
/* 141:    */       
/* 142:165 */       TipoContacto tipoContacto = null;
/* 143:166 */       if (listaTipoContactos.size() > 0) {
/* 144:167 */         tipoContacto = (TipoContacto)listaTipoContactos.get(0);
/* 145:    */       } else {
/* 146:170 */         tipoContacto = this.tipoContactoDao.getTipoContactoPredeterminadoPorTipoNotificacion(tipoNotificacion, documento.getOrganizacion().getId());
/* 147:    */       }
/* 148:173 */       List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, false, null);
/* 149:174 */       Organizacion organizacion = null;
/* 150:175 */       if (listaOrganizacion != null) {
/* 151:176 */         organizacion = (Organizacion)listaOrganizacion.get(0);
/* 152:    */       }
/* 153:179 */       Map<String, String> mapaCid = new HashMap();
/* 154:180 */       String cuerpoCorreo = null;
/* 155:181 */       if (tipoContacto != null) {
/* 156:190 */         cuerpoCorreo = documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.GUIA_REMISION ? tipoContacto.getTextoCuerpoCorreoGuiaRemision() : documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.RETENCION ? tipoContacto.getTextoCuerpoCorreoRetencionProveedor() : documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.NOTA_DEBITO ? tipoContacto.getTextoCuerpoCorreoNotaDebitoCliente() : documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.NOTA_CREDITO ? tipoContacto.getTextoCuerpoCorreoNotaCreditoCliente() : documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.FACTURA ? tipoContacto.getTextoCuerpoCorreoFactura() : "";
/* 157:    */       }
/* 158:192 */       if ((tipoContacto != null) && (cuerpoCorreo != null) && (!cuerpoCorreo.trim().isEmpty()) && (!cuerpoCorreo.trim().equals("<br>")) && 
/* 159:193 */         (!cuerpoCorreo.trim().equals("<div><br></div>")))
/* 160:    */       {
/* 161:194 */         bodyText = organizacion.getOrganizacionConfiguracion().getMensajeDocumentosElectronicos() + cuerpoCorreo;
/* 162:    */         
/* 163:196 */         bodyText = bodyText.replaceAll(":tipoComprobante:", 
/* 164:    */         
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:201 */           documento
/* 169:202 */           .getTipoDocumento() == TipoDocumentoElectronicoEnum.GUIA_REMISION ? "Guia de Remisión" : documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.RETENCION ? "Retención" : documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.NOTA_DEBITO ? "Nota de Débito" : documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.NOTA_CREDITO ? "Nota de Crédito" : documento.getTipoDocumento() == TipoDocumentoElectronicoEnum.FACTURA ? "Factura" : "");
/* 170:203 */         bodyText = bodyText.replaceAll(":numeroComprobante:", numeroCompleto);
/* 171:204 */         bodyText = bodyText.replaceAll(":fechaComprobante:", sdf.format(documento.getFechaEmision()));
/* 172:205 */         bodyText = bodyText.replaceAll(":nombreCliente:", empresa.getNombreFiscal());
/* 173:206 */         bodyText = bodyText.replaceAll(":identificacionCliente:", empresa.getIdentificacion());
/* 174:207 */         bodyText = bodyText.replaceAll(":nombreProveedor:", empresa.getNombreFiscal());
/* 175:208 */         bodyText = bodyText.replaceAll(":identificacionProveedor:", empresa.getIdentificacion());
/* 176:    */       }
/* 177:    */       else
/* 178:    */       {
/* 179:210 */         JasperExportManager.exportReportToHtmlFile(jasperPrint, pathReporteDocumento);
/* 180:    */         
/* 181:212 */         bodyText = organizacion.getOrganizacionConfiguracion().getMensajeDocumentosElectronicos() + FuncionesUtiles.LeeFicheroHTMLToEmail(pathReporteDocumento);
/* 182:    */         try
/* 183:    */         {
/* 184:215 */           File dir = new File(pathReporteDocumento + "_files");
/* 185:216 */           for (String fichero : dir.list()) {
/* 186:217 */             mapaCid.put(fichero, pathReporteDocumento + "_files/" + fichero);
/* 187:    */           }
/* 188:    */         }
/* 189:    */         catch (Exception e)
/* 190:    */         {
/* 191:221 */           System.out.println("No hay codigo de barras");
/* 192:    */         }
/* 193:    */       }
/* 194:225 */       this.servicioEnvioEmail.enviarEmail(documento.getOrganizacion().getIdOrganizacion(), documento.getEmail(), subject, bodyText, archivos, mapaCid, null, null, null);
/* 195:    */     }
/* 196:    */     catch (JRException e)
/* 197:    */     {
/* 198:229 */       e.printStackTrace();
/* 199:    */     }
/* 200:    */     catch (Exception e)
/* 201:    */     {
/* 202:232 */       e.printStackTrace();
/* 203:    */     }
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.servicio.impl.ServicioDocumentoElectronicoImpl
 * JD-Core Version:    0.7.0.1
 */