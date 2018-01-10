/*   1:    */ package com.asinfo.as2.compronteselectronicos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.ServicioFacturaProveedorSRIXML;
/*   5:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*   6:    */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*   7:    */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*   8:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   9:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*  10:    */ import com.asinfo.as2.dao.OrganizacionConfiguracionDao;
/*  11:    */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*  12:    */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.Organizacion;
/*  16:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  19:    */ import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
/*  20:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  21:    */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*  22:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*  25:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2DocumentoElectronico;
/*  27:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  30:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  31:    */ import com.asinfo.as2.xml.jaxb.sri.CampoAdicionalJaxb;
/*  32:    */ import com.asinfo.as2.xml.jaxb.sri.ImpuestoJaxb;
/*  33:    */ import com.asinfo.as2.xml.jaxb.sri.ImpuestosJaxb;
/*  34:    */ import com.asinfo.as2.xml.jaxb.sri.InfoAdicionalJaxb;
/*  35:    */ import com.asinfo.as2.xml.jaxb.sri.InfoFacturaJaxb;
/*  36:    */ import com.asinfo.as2.xml.jaxb.sri.InfoTributariaJaxb;
/*  37:    */ import com.asinfo.as2.xml.jaxb.sri.RetencionProveedorJaxb;
/*  38:    */ import java.io.File;
/*  39:    */ import java.math.BigDecimal;
/*  40:    */ import java.text.SimpleDateFormat;
/*  41:    */ import java.util.ArrayList;
/*  42:    */ import java.util.Date;
/*  43:    */ import java.util.HashMap;
/*  44:    */ import java.util.List;
/*  45:    */ import java.util.Map;
/*  46:    */ import javax.ejb.EJB;
/*  47:    */ import javax.ejb.Stateless;
/*  48:    */ import javax.xml.bind.JAXBContext;
/*  49:    */ import javax.xml.bind.Marshaller;
/*  50:    */ 
/*  51:    */ @Stateless
/*  52:    */ public class ServicioFacturaProveedorSRIXMLImpl
/*  53:    */   implements ServicioFacturaProveedorSRIXML
/*  54:    */ {
/*  55:    */   @EJB
/*  56:    */   private ServicioSecuenciaDocumentoElectronico servicioSecuenciaDocumentoElectronico;
/*  57:    */   @EJB
/*  58:    */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  59:    */   @EJB
/*  60:    */   private ServicioOrganizacion servicioOrganizacion;
/*  61:    */   @EJB
/*  62:    */   private FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  63:    */   @EJB
/*  64:    */   private ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  65:    */   @EJB
/*  66:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  67:    */   @EJB
/*  68:    */   private ServicioDocumento servicioDocumento;
/*  69:    */   @EJB
/*  70:    */   private OrganizacionConfiguracionDao organizacionConfiguracionDao;
/*  71:    */   @EJB
/*  72:    */   private transient ServicioSucursal servicioSucursal;
/*  73: 87 */   private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
/*  74:    */   
/*  75:    */   public FacturaProveedorSRI generarClaveAcceso(DocumentoElectronico documento, FacturaProveedorSRI facturaProveedorSRI, boolean indicadorGenerarXML)
/*  76:    */     throws AS2Exception
/*  77:    */   {
/*  78: 92 */     if (documento == null)
/*  79:    */     {
/*  80: 93 */       String version = "1.0.0";
/*  81:    */       
/*  82:    */ 
/*  83: 96 */       int ambiente = facturaProveedorSRI.getAmbiente();
/*  84: 97 */       int tipoEmision = facturaProveedorSRI.getTipoEmision();
/*  85: 98 */       TipoDocumentoElectronicoEnum tipoDocumento = TipoDocumentoElectronicoEnum.RETENCION;
/*  86:    */       
/*  87:100 */       Organizacion organizacion = AppUtil.getOrganizacion();
/*  88:101 */       if (organizacion == null)
/*  89:    */       {
/*  90:102 */         int idOrganizacion = facturaProveedorSRI.getIdOrganizacion();
/*  91:103 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/*  92:    */       }
/*  93:105 */       documento = new DocumentoElectronico(organizacion, ambiente, tipoEmision, tipoDocumento, "1.0.0");
/*  94:106 */       documento.setDireccionMatriz(facturaProveedorSRI.getDireccionMatriz());
/*  95:107 */       documento.setDireccionSucursal(facturaProveedorSRI.getDireccionSucursal());
/*  96:108 */       documento.setEmail(facturaProveedorSRI.getEmail());
/*  97:    */     }
/*  98:110 */     facturaProveedorSRI.setDocumentoElectronico(documento);
/*  99:112 */     if (facturaProveedorSRI.getCodigoUnico() == null)
/* 100:    */     {
/* 101:113 */       String codigoUnico = this.servicioSecuenciaDocumentoElectronico.generarSecuenciaDocumento(8);
/* 102:114 */       documento.setCodigoUnico(codigoUnico);
/* 103:115 */       facturaProveedorSRI.setCodigoUnico(codigoUnico);
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:117 */       String codigoUnico = facturaProveedorSRI.getCodigoUnico();
/* 108:118 */       documento.setCodigoUnico(codigoUnico);
/* 109:    */     }
/* 110:121 */     documento.init(facturaProveedorSRI);
/* 111:    */     
/* 112:    */ 
/* 113:124 */     Sucursal sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(facturaProveedorSRI.getIdSucursal()));
/* 114:125 */     documento.setTelefonoSucursal(sucursal.getTelefono1().concat(sucursal.getTelefono2() != null ? "   " + sucursal.getTelefono2() : ""));
/* 115:127 */     if ((documento.getOrganizacion().getOrganizacionConfiguracion().getPkcs12Password() == null) || 
/* 116:128 */       (documento.getOrganizacion().getOrganizacionConfiguracion().getPkcs12Password().trim().isEmpty()))
/* 117:    */     {
/* 118:129 */       documento.getOrganizacion().getOrganizacionConfiguracion().setPkcs12Password(documento.getPkcs12_password());
/* 119:130 */       this.organizacionConfiguracionDao.guardar(documento.getOrganizacion().getOrganizacionConfiguracion());
/* 120:    */     }
/* 121:132 */     facturaProveedorSRI.setClaveAcceso(documento.getClaveAcceso());
/* 122:133 */     facturaProveedorSRI.setAutorizacionRetencion(documento.getClaveAcceso());
/* 123:134 */     facturaProveedorSRI.setFechaAutorizacion(null);
/* 124:135 */     this.facturaProveedorSRIDao.guardar(facturaProveedorSRI);
/* 125:136 */     this.facturaProveedorSRIDao.flush();
/* 126:137 */     this.facturaProveedorSRIDao.detach(facturaProveedorSRI);
/* 127:139 */     if (indicadorGenerarXML)
/* 128:    */     {
/* 129:    */       try
/* 130:    */       {
/* 131:141 */         generarXML(documento, facturaProveedorSRI);
/* 132:    */       }
/* 133:    */       catch (Exception e)
/* 134:    */       {
/* 135:143 */         e.printStackTrace();
/* 136:    */       }
/* 137:148 */       Map<String, String> filtros = new HashMap();
/* 138:149 */       filtros.put("idFacturaProveedorSRI", facturaProveedorSRI.getIdFacturaProveedorSRI() + "");
/* 139:150 */       List<ComprobanteElectronicoPendienteSRI> listaComprobante = this.comprobanteElectronicoPendienteSRIDao.obtenerListaCombo("idFacturaProveedorSRI", true, filtros);
/* 140:    */       
/* 141:152 */       ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI = null;
/* 142:153 */       if (listaComprobante.isEmpty())
/* 143:    */       {
/* 144:154 */         comprobanteElectronicoPendienteSRI = new ComprobanteElectronicoPendienteSRI();
/* 145:155 */         comprobanteElectronicoPendienteSRI.setIdOrganizacion(facturaProveedorSRI.getIdOrganizacion());
/* 146:156 */         comprobanteElectronicoPendienteSRI.setIdFacturaProveedorSRI(Integer.valueOf(facturaProveedorSRI.getId()));
/* 147:    */       }
/* 148:    */       else
/* 149:    */       {
/* 150:158 */         comprobanteElectronicoPendienteSRI = (ComprobanteElectronicoPendienteSRI)listaComprobante.get(0);
/* 151:    */       }
/* 152:160 */       comprobanteElectronicoPendienteSRI.setAmbiente(documento.getAmbiente());
/* 153:161 */       comprobanteElectronicoPendienteSRI.setClaveAcceso(documento.getClaveAcceso());
/* 154:162 */       comprobanteElectronicoPendienteSRI.setDocumentoBase(facturaProveedorSRI.getDocumento().getDocumentoBase());
/* 155:163 */       comprobanteElectronicoPendienteSRI.setEmails(facturaProveedorSRI.getEmail());
/* 156:164 */       comprobanteElectronicoPendienteSRI.setFechaEmision(facturaProveedorSRI.getFechaEmisionRetencion());
/* 157:165 */       comprobanteElectronicoPendienteSRI.setIdSucursal(facturaProveedorSRI.getIdSucursal());
/* 158:166 */       comprobanteElectronicoPendienteSRI.setNumero(facturaProveedorSRI.getEstablecimientoRetencion() + "-" + facturaProveedorSRI
/* 159:167 */         .getPuntoEmisionRetencion() + "-" + facturaProveedorSRI.getNumeroRetencion());
/* 160:168 */       comprobanteElectronicoPendienteSRI.setIndicadorNoEnviado(true);
/* 161:169 */       comprobanteElectronicoPendienteSRI.setIndicadorComprobarAutorizacion(false);
/* 162:170 */       comprobanteElectronicoPendienteSRI.setIndicadorRechazado(false);
/* 163:171 */       comprobanteElectronicoPendienteSRI.setTipoDocumento(documento.getTipoDocumento());
/* 164:172 */       comprobanteElectronicoPendienteSRI.setTipoEmision(documento.getTipoEmision());
/* 165:173 */       this.comprobanteElectronicoPendienteSRIDao.guardar(comprobanteElectronicoPendienteSRI);
/* 166:    */     }
/* 167:177 */     return facturaProveedorSRI;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public DocumentoElectronico autorizarFactura(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/* 171:    */     throws AS2Exception, ExcepcionAS2Ventas
/* 172:    */   {
/* 173:183 */     Organizacion organizacion = null;
/* 174:184 */     if (organizacion == null)
/* 175:    */     {
/* 176:185 */       int idOrganizacion = comprobanteElectronicoPendienteSRI.getIdOrganizacion();
/* 177:186 */       organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/* 178:    */     }
/* 179:188 */     DocumentoElectronico documento = new DocumentoElectronico(organizacion, comprobanteElectronicoPendienteSRI);
/* 180:    */     
/* 181:    */ 
/* 182:191 */     File ficheroFirmado = new File(documento.getPathArchivoFirmado());
/* 183:192 */     if (!ficheroFirmado.exists())
/* 184:    */     {
/* 185:193 */       documento = null;
/* 186:194 */       FacturaProveedorSRI facturaProveedorSRI = this.servicioFacturaProveedorSRI.cargarDetalle(comprobanteElectronicoPendienteSRI
/* 187:195 */         .getIdFacturaProveedorSRI().intValue());
/* 188:196 */       facturaProveedorSRI = generarClaveAcceso(documento, facturaProveedorSRI, false);
/* 189:197 */       documento = facturaProveedorSRI.getDocumentoElectronico();
/* 190:    */       
/* 191:    */ 
/* 192:200 */       generarXML(documento, facturaProveedorSRI);
/* 193:    */     }
/* 194:    */     try
/* 195:    */     {
/* 196:205 */       this.servicioDocumentoElectronico.autorizarDocumento(documento);
/* 197:    */     }
/* 198:    */     catch (AS2Exception e)
/* 199:    */     {
/* 200:207 */       documento.setMensajeSRI(e.getMensaje());
/* 201:    */     }
/* 202:209 */     return documento;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public DocumentoElectronico comprobarAutorizacion(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/* 206:    */     throws ExcepcionAS2DocumentoElectronico
/* 207:    */   {
/* 208:215 */     DocumentoElectronico documento = null;
/* 209:    */     try
/* 210:    */     {
/* 211:217 */       Organizacion organizacion = null;
/* 212:218 */       if (organizacion == null)
/* 213:    */       {
/* 214:219 */         int idOrganizacion = comprobanteElectronicoPendienteSRI.getIdOrganizacion();
/* 215:220 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/* 216:    */       }
/* 217:222 */       documento = new DocumentoElectronico(organizacion, comprobanteElectronicoPendienteSRI);
/* 218:    */       
/* 219:224 */       this.servicioDocumentoElectronico.comprobarAutorizacionDocumento(documento);
/* 220:    */     }
/* 221:    */     catch (AS2Exception e)
/* 222:    */     {
/* 223:226 */       documento.setMensajeSRI(e.getMensaje());
/* 224:    */     }
/* 225:    */     catch (Exception e)
/* 226:    */     {
/* 227:228 */       e.printStackTrace();
/* 228:229 */       throw new ExcepcionAS2DocumentoElectronico("msgs_error_autorizacion_documento_electronico", e.getMessage());
/* 229:    */     }
/* 230:231 */     return documento;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void generarXML(DocumentoElectronico documento, FacturaProveedorSRI facturaProveedorSRI)
/* 234:    */     throws AS2Exception
/* 235:    */   {
/* 236:236 */     String mensajeSRI = "";
/* 237:237 */     EstadoDocumentoElectronico estadoSRI = null;
/* 238:    */     try
/* 239:    */     {
/* 240:240 */       String numeroComprobante = FuncionesUtiles.completarALaIzquierda('0', 9, facturaProveedorSRI.getNumeroRetencion());
/* 241:    */       
/* 242:242 */       RetencionProveedorJaxb retencionProveedorJaxb = null;
/* 243:243 */       InfoAdicionalJaxb infoAdicionalJaxb = null;
/* 244:    */       
/* 245:245 */       JAXBContext context = null;
/* 246:246 */       if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.RETENCION))
/* 247:    */       {
/* 248:247 */         context = JAXBContext.newInstance(new Class[] { RetencionProveedorJaxb.class });
/* 249:248 */         retencionProveedorJaxb = crearRetencionProveedorJaxb(documento, facturaProveedorSRI);
/* 250:    */       }
/* 251:    */       else
/* 252:    */       {
/* 253:    */         SimpleDateFormat sdf;
/* 254:    */         String mensaje;
/* 255:250 */         return;
/* 256:    */       }
/* 257:254 */       String codigoRetencion = "";
/* 258:255 */       String baseImponible = String.valueOf(facturaProveedorSRI.getMontoIva());
/* 259:256 */       String porcentajeRetener = "";
/* 260:257 */       String valorRetenido = "";
/* 261:258 */       String tipoConceptoretencion = "";
/* 262:259 */       String codDocSustento = "01";
/* 263:260 */       String[] cadenaSplit = facturaProveedorSRI.getNumeroFactura().split("-");
/* 264:261 */       String numeroDocModificado1 = FuncionesUtiles.completarALaIzquierda('0', 3, cadenaSplit[0]);
/* 265:262 */       String numeroDocModificado2 = FuncionesUtiles.completarALaIzquierda('0', 3, cadenaSplit[1]);
/* 266:263 */       String numeroDocModificado3 = FuncionesUtiles.completarALaIzquierda('0', 9, cadenaSplit[2]);
/* 267:264 */       String numDocSustento = numeroDocModificado1 + numeroDocModificado2 + numeroDocModificado3;
/* 268:265 */       String fechaEmisionDocSustento = formatoFecha.format(facturaProveedorSRI.getFechaEmision());
/* 269:    */       
/* 270:    */ 
/* 271:268 */       ImpuestosJaxb impuestosJaxb = new ImpuestosJaxb();
/* 272:269 */       impuestosJaxb.setListaImpuesto(new ArrayList());
/* 273:270 */       for (DetalleFacturaProveedorSRI detalleFactura : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/* 274:271 */         if (!detalleFactura.isEliminado())
/* 275:    */         {
/* 276:272 */           ImpuestoJaxb impuestoJaxb = new ImpuestoJaxb();
/* 277:273 */           codigoRetencion = detalleFactura.getConceptoRetencionSRI().getCodigo();
/* 278:274 */           baseImponible = String.valueOf(detalleFactura.getBaseImponibleRetencion());
/* 279:275 */           porcentajeRetener = String.valueOf(detalleFactura.getPorcentajeRetencion());
/* 280:276 */           valorRetenido = String.valueOf(detalleFactura.getValorRetencion());
/* 281:    */           
/* 282:    */ 
/* 283:279 */           tipoConceptoretencion = detalleFactura.getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA) ? "2" : detalleFactura.getTipoConceptoRetencion().equals(TipoConceptoRetencion.FUENTE) ? "1" : "6";
/* 284:    */           
/* 285:281 */           agregarImpuesto(impuestoJaxb, tipoConceptoretencion, codigoRetencion, baseImponible, porcentajeRetener, valorRetenido, codDocSustento, numDocSustento, fechaEmisionDocSustento);
/* 286:    */           
/* 287:283 */           impuestosJaxb.getListaImpuesto().add(impuestoJaxb);
/* 288:    */         }
/* 289:    */       }
/* 290:286 */       retencionProveedorJaxb.setImpuestos(impuestosJaxb);
/* 291:    */       
/* 292:    */ 
/* 293:289 */       retencionProveedorJaxb.setId("comprobante");
/* 294:290 */       retencionProveedorJaxb.setVersion(documento.getVersion());
/* 295:    */       
/* 296:292 */       InfoTributariaJaxb infoTributariaJaxb = new InfoTributariaJaxb();
/* 297:293 */       infoTributariaJaxb.setAmbiente(Integer.valueOf(documento.getAmbiente()));
/* 298:294 */       infoTributariaJaxb.setTipoEmision(Integer.valueOf(documento.getTipoEmision()));
/* 299:295 */       infoTributariaJaxb.setRazonSocial(documento.getAmbiente() == 2 ? documento.getOrganizacion().getRazonSocial() : "PRUEBAS SERVICIO DE RENTAS INTERNAS.");
/* 300:    */       
/* 301:297 */       infoTributariaJaxb.setNombreComercial(documento.getOrganizacion().getRazonSocial());
/* 302:298 */       infoTributariaJaxb.setRuc(documento.getOrganizacion().getIdentificacion());
/* 303:299 */       infoTributariaJaxb.setClaveAcceso(documento.getClaveAcceso());
/* 304:300 */       infoTributariaJaxb.setCodDoc(documento.getTipoDocumento().getCodigo());
/* 305:301 */       infoTributariaJaxb.setEstab(facturaProveedorSRI.getEstablecimientoRetencion());
/* 306:302 */       infoTributariaJaxb.setPtoEmi(facturaProveedorSRI.getPuntoEmisionRetencion());
/* 307:303 */       infoTributariaJaxb.setSecuencial(numeroComprobante);
/* 308:304 */       infoTributariaJaxb.setDirMatriz(documento.getDireccionMatriz() == null ? "S/N" : documento.getDireccionMatriz());
/* 309:    */       
/* 310:306 */       retencionProveedorJaxb.setInfoTributaria(infoTributariaJaxb);
/* 311:309 */       if (infoAdicionalJaxb == null)
/* 312:    */       {
/* 313:310 */         infoAdicionalJaxb = new InfoAdicionalJaxb();
/* 314:311 */         infoAdicionalJaxb.setListaCampoAdicional(new ArrayList());
/* 315:    */       }
/* 316:314 */       if ((facturaProveedorSRI.getDireccionProveedor() != null) && (!facturaProveedorSRI.getDireccionProveedor().isEmpty())) {
/* 317:315 */         infoAdicionalJaxb.getlListaCampoAdicional().add(
/* 318:316 */           agregarInformacionAdicional(infoAdicionalJaxb, "Direccion", facturaProveedorSRI.getDireccionProveedor()));
/* 319:    */       }
/* 320:318 */       if ((facturaProveedorSRI.getEmail() != null) && (!facturaProveedorSRI.getEmail().isEmpty())) {
/* 321:319 */         infoAdicionalJaxb.getlListaCampoAdicional().add(
/* 322:320 */           agregarInformacionAdicional(infoAdicionalJaxb, "Email", facturaProveedorSRI.getEmail()));
/* 323:    */       }
/* 324:323 */       if ((infoAdicionalJaxb != null) && (!infoAdicionalJaxb.getlListaCampoAdicional().isEmpty())) {
/* 325:324 */         retencionProveedorJaxb.setInfoAdicional(infoAdicionalJaxb);
/* 326:    */       }
/* 327:328 */       Marshaller m = context.createMarshaller();
/* 328:329 */       m.marshal(retencionProveedorJaxb, new File(documento.getPathArchivoEmitido()));
/* 329:    */       
/* 330:    */ 
/* 331:332 */       documento.firmar();
/* 332:    */       
/* 333:334 */       estadoSRI = EstadoDocumentoElectronico.EMITIDO;
/* 334:335 */       mensajeSRI = estadoSRI.toString();
/* 335:    */     }
/* 336:    */     catch (Exception e)
/* 337:    */     {
/* 338:    */       SimpleDateFormat sdf;
/* 339:    */       String mensaje;
/* 340:337 */       e.printStackTrace();
/* 341:338 */       mensajeSRI = e.getMessage();
/* 342:339 */       throw new AS2Exception(e.getMessage());
/* 343:    */     }
/* 344:    */     finally
/* 345:    */     {
/* 346:342 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
/* 347:343 */       mensajeSRI = sdf.format(new Date()) + " => " + mensajeSRI;
/* 348:344 */       String mensaje = mensajeSRI.length() > 5000 ? mensajeSRI.substring(0, 5000) : mensajeSRI;
/* 349:345 */       this.servicioFacturaProveedorSRI.actualizaFacturaProveedorSRI(facturaProveedorSRI.getId(), Estado.EN_ESPERA_CONTINGENCIA, estadoSRI, null, null, mensaje);
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   private RetencionProveedorJaxb crearRetencionProveedorJaxb(DocumentoElectronico documento, FacturaProveedorSRI facturaProveedorSRI)
/* 354:    */   {
/* 355:354 */     RetencionProveedorJaxb retencionProveedorJaxb = new RetencionProveedorJaxb();
/* 356:355 */     InfoFacturaJaxb infoCompRetencion = new InfoFacturaJaxb();
/* 357:358 */     if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.RETENCION))
/* 358:    */     {
/* 359:359 */       infoCompRetencion.setFechaEmision(formatoFecha.format(facturaProveedorSRI.getFechaEmisionRetencion()));
/* 360:360 */       infoCompRetencion.setDirEstablecimiento(documento.getDireccionSucursal() == null ? "S/N" : documento.getDireccionSucursal());
/* 361:361 */       if ((documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente() != null) && 
/* 362:362 */         (!documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente().equals(""))) {
/* 363:363 */         infoCompRetencion.setContribuyenteEspecial(documento.getOrganizacion().getOrganizacionConfiguracion()
/* 364:364 */           .getNumeroResolucionContribuyente());
/* 365:    */       }
/* 366:367 */       infoCompRetencion.setObligadoContabilidad(documento.getOrganizacion().getOrganizacionConfiguracion().isIndicadorObligadoContabilidad() ? "SI" : "NO");
/* 367:    */       
/* 368:    */ 
/* 369:370 */       String tipIdenComprador = FuncionesUtiles.getTipoIdentificacionClienteSRI(facturaProveedorSRI.getTipoIdentificacion().getCodigo());
/* 370:371 */       infoCompRetencion.setTipoIdentificacionSujetoRetenido(tipIdenComprador);
/* 371:    */       
/* 372:373 */       infoCompRetencion.setRazonSocialSujetoRetenido(facturaProveedorSRI.getNombreProveedor());
/* 373:374 */       infoCompRetencion.setIdentificacionSujetoRetenido(facturaProveedorSRI.getIdentificacionProveedor());
/* 374:375 */       infoCompRetencion.setPeriodoFiscal(FuncionesUtiles.completarALaIzquierda('0', 2, 
/* 375:376 */         String.valueOf(facturaProveedorSRI.getFechaEmision().getMonth() + 1)) + "/" + 
/* 376:377 */         String.valueOf(facturaProveedorSRI.getFechaEmision().getYear() + 1900));
/* 377:    */     }
/* 378:380 */     retencionProveedorJaxb.setInfoCompRetencion(infoCompRetencion);
/* 379:381 */     return retencionProveedorJaxb;
/* 380:    */   }
/* 381:    */   
/* 382:    */   private CampoAdicionalJaxb agregarInformacionAdicional(InfoAdicionalJaxb infoAdicionalJaxb, String nombreCampo, String valorCampo)
/* 383:    */   {
/* 384:385 */     CampoAdicionalJaxb campoAdicionalJaxb = new CampoAdicionalJaxb();
/* 385:386 */     campoAdicionalJaxb.setNombre(nombreCampo);
/* 386:387 */     campoAdicionalJaxb.setValor(valorCampo);
/* 387:    */     
/* 388:389 */     return campoAdicionalJaxb;
/* 389:    */   }
/* 390:    */   
/* 391:    */   private void agregarImpuesto(ImpuestoJaxb impuestos, String pcodigo, String pcodigoRetencion, String pbaseImponible, String pporcentajeRetener, String pvalorRetenido, String pcodDocSustento, String pnumDocSustento, String pfechaEmisionDocSustento)
/* 392:    */   {
/* 393:394 */     impuestos.setCodigo(pcodigo);
/* 394:395 */     impuestos.setCodigoRetencion(pcodigoRetencion);
/* 395:396 */     impuestos.setBaseImponible(new BigDecimal(pbaseImponible));
/* 396:397 */     impuestos.setPorcentajeRetener(new BigDecimal(pporcentajeRetener));
/* 397:398 */     impuestos.setValorRetenido(new BigDecimal(pvalorRetenido));
/* 398:399 */     impuestos.setCodDocSustento(pcodDocSustento);
/* 399:400 */     impuestos.setNumDocSustento(pnumDocSustento);
/* 400:401 */     impuestos.setFechaEmisionDocSustento(pfechaEmisionDocSustento);
/* 401:    */   }
/* 402:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.servicio.impl.ServicioFacturaProveedorSRIXMLImpl
 * JD-Core Version:    0.7.0.1
 */