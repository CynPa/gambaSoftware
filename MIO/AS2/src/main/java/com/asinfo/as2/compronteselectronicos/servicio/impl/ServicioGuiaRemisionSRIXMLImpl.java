/*   1:    */ package com.asinfo.as2.compronteselectronicos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.ServicioGuiaRemisionSRIXML;
/*   5:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*   6:    */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*   7:    */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*   8:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   9:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*  10:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*  11:    */ import com.asinfo.as2.dao.GuiaRemisionDao;
/*  12:    */ import com.asinfo.as2.dao.OrganizacionConfiguracionDao;
/*  13:    */ import com.asinfo.as2.dao.reportes.ventas.ReporteGuiaRemisionDao;
/*  14:    */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*  15:    */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*  16:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  17:    */ import com.asinfo.as2.entities.Bodega;
/*  18:    */ import com.asinfo.as2.entities.Ciudad;
/*  19:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  20:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  21:    */ import com.asinfo.as2.entities.DetalleGuiaRemision;
/*  22:    */ import com.asinfo.as2.entities.DetalleHojaRuta;
/*  23:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  24:    */ import com.asinfo.as2.entities.Documento;
/*  25:    */ import com.asinfo.as2.entities.Empresa;
/*  26:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  27:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  28:    */ import com.asinfo.as2.entities.HojaRuta;
/*  29:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  30:    */ import com.asinfo.as2.entities.Organizacion;
/*  31:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  32:    */ import com.asinfo.as2.entities.Producto;
/*  33:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  34:    */ import com.asinfo.as2.entities.Transportista;
/*  35:    */ import com.asinfo.as2.entities.Ubicacion;
/*  36:    */ import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
/*  37:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  38:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  39:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  40:    */ import com.asinfo.as2.excepciones.ExcepcionAS2DocumentoElectronico;
/*  41:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  42:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  43:    */ import com.asinfo.as2.util.AppUtil;
/*  44:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  45:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  46:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  47:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta;
/*  48:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteGuiaRemision;
/*  49:    */ import com.asinfo.as2.xml.jaxb.sri.CampoAdicionalJaxb;
/*  50:    */ import com.asinfo.as2.xml.jaxb.sri.DestinatarioJaxb;
/*  51:    */ import com.asinfo.as2.xml.jaxb.sri.DestinatariosJaxb;
/*  52:    */ import com.asinfo.as2.xml.jaxb.sri.DetalleJaxb;
/*  53:    */ import com.asinfo.as2.xml.jaxb.sri.DetallesJaxb;
/*  54:    */ import com.asinfo.as2.xml.jaxb.sri.GuiaRemisionJaxb;
/*  55:    */ import com.asinfo.as2.xml.jaxb.sri.InfoAdicionalJaxb;
/*  56:    */ import com.asinfo.as2.xml.jaxb.sri.InfoGuiaRemisionJaxb;
/*  57:    */ import com.asinfo.as2.xml.jaxb.sri.InfoTributariaJaxb;
/*  58:    */ import java.io.File;
/*  59:    */ import java.math.BigDecimal;
/*  60:    */ import java.math.RoundingMode;
/*  61:    */ import java.text.SimpleDateFormat;
/*  62:    */ import java.util.ArrayList;
/*  63:    */ import java.util.Date;
/*  64:    */ import java.util.HashMap;
/*  65:    */ import java.util.List;
/*  66:    */ import java.util.Map;
/*  67:    */ import javax.ejb.EJB;
/*  68:    */ import javax.ejb.Stateless;
/*  69:    */ import javax.xml.bind.JAXBContext;
/*  70:    */ import javax.xml.bind.Marshaller;
/*  71:    */ 
/*  72:    */ @Stateless
/*  73:    */ public class ServicioGuiaRemisionSRIXMLImpl
/*  74:    */   implements ServicioGuiaRemisionSRIXML
/*  75:    */ {
/*  76:    */   @EJB
/*  77:    */   private ServicioSecuenciaDocumentoElectronico servicioSecuenciaDocumentoElectronico;
/*  78:    */   @EJB
/*  79:    */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  80:    */   @EJB
/*  81:    */   private ServicioOrganizacion servicioOrganizacion;
/*  82:    */   @EJB
/*  83:    */   private FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  84:    */   @EJB
/*  85:    */   private ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  86:    */   @EJB
/*  87:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  88:    */   @EJB
/*  89:    */   private ServicioDocumento servicioDocumento;
/*  90:    */   @EJB
/*  91:    */   private GuiaRemisionDao guiaRemisionDao;
/*  92:    */   @EJB
/*  93:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  94:    */   @EJB
/*  95:    */   private FacturaClienteDao facturaClienteDao;
/*  96:    */   @EJB
/*  97:    */   private ServicioReporteGuiaRemision servicioReporteGuiaRemision;
/*  98:    */   @EJB
/*  99:    */   private ReporteGuiaRemisionDao reporteGuiaRemisionDao;
/* 100:    */   @EJB
/* 101:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/* 102:    */   @EJB
/* 103:    */   private ServicioGuiaRemision servicioGuiaRemision;
/* 104:    */   @EJB
/* 105:    */   private OrganizacionConfiguracionDao organizacionConfiguracionDao;
/* 106:    */   @EJB
/* 107:    */   private ServicioHojaRuta servicioHojaRuta;
/* 108:    */   @EJB
/* 109:    */   private transient ServicioSucursal servicioSucursal;
/* 110:114 */   private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
/* 111:    */   
/* 112:    */   public GuiaRemision generarClaveAcceso(DocumentoElectronico documento, GuiaRemision guiaRemision, boolean indicadorGenerarXML)
/* 113:    */     throws AS2Exception
/* 114:    */   {
/* 115:119 */     if (documento == null)
/* 116:    */     {
/* 117:120 */       String version = "1.1.0";
/* 118:    */       
/* 119:122 */       guiaRemision = this.guiaRemisionDao.cargarDetalle(guiaRemision.getId());
/* 120:    */       
/* 121:124 */       int ambiente = guiaRemision.getAmbiente();
/* 122:125 */       int tipoEmision = guiaRemision.getTipoEmision();
/* 123:126 */       TipoDocumentoElectronicoEnum tipoDocumento = TipoDocumentoElectronicoEnum.GUIA_REMISION;
/* 124:    */       
/* 125:128 */       guiaRemision.setIndicadorDocumentoElectronico(true);
/* 126:    */       
/* 127:130 */       Organizacion organizacion = AppUtil.getOrganizacion();
/* 128:131 */       if (organizacion == null)
/* 129:    */       {
/* 130:132 */         int idOrganizacion = guiaRemision.getIdOrganizacion();
/* 131:133 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/* 132:    */       }
/* 133:135 */       documento = new DocumentoElectronico(organizacion, ambiente, tipoEmision, tipoDocumento, "1.1.0");
/* 134:136 */       guiaRemision.setDocumentoElectronico(documento);
/* 135:137 */       documento.setDireccionMatriz(guiaRemision.getDireccionMatriz());
/* 136:138 */       documento.setDireccionSucursal(guiaRemision.getDireccionSucursal());
/* 137:139 */       documento.setEmail(guiaRemision.getEmail());
/* 138:    */     }
/* 139:141 */     if (guiaRemision.getCodigoUnico() == null)
/* 140:    */     {
/* 141:142 */       String codigoUnico = this.servicioSecuenciaDocumentoElectronico.generarSecuenciaDocumento(8);
/* 142:143 */       documento.setCodigoUnico(codigoUnico);
/* 143:144 */       guiaRemision.setCodigoUnico(codigoUnico);
/* 144:    */     }
/* 145:    */     else
/* 146:    */     {
/* 147:146 */       String codigoUnico = guiaRemision.getCodigoUnico();
/* 148:147 */       documento.setCodigoUnico(codigoUnico);
/* 149:    */     }
/* 150:150 */     documento.init(guiaRemision);
/* 151:151 */     if ((documento.getOrganizacion().getOrganizacionConfiguracion().getPkcs12Password() == null) || 
/* 152:152 */       (documento.getOrganizacion().getOrganizacionConfiguracion().getPkcs12Password().trim().isEmpty()))
/* 153:    */     {
/* 154:153 */       documento.getOrganizacion().getOrganizacionConfiguracion().setPkcs12Password(documento.getPkcs12_password());
/* 155:154 */       this.organizacionConfiguracionDao.guardar(documento.getOrganizacion().getOrganizacionConfiguracion());
/* 156:    */     }
/* 157:156 */     guiaRemision.setClaveAcceso(documento.getClaveAcceso());
/* 158:157 */     guiaRemision.setAutorizacion(documento.getClaveAcceso());
/* 159:158 */     guiaRemision.setFechaAutorizacion(null);
/* 160:    */     
/* 161:160 */     this.guiaRemisionDao.guardar(guiaRemision);
/* 162:161 */     this.guiaRemisionDao.flush();
/* 163:162 */     this.guiaRemisionDao.detach(guiaRemision);
/* 164:164 */     if (indicadorGenerarXML)
/* 165:    */     {
/* 166:    */       try
/* 167:    */       {
/* 168:166 */         generarXML(documento, guiaRemision);
/* 169:    */       }
/* 170:    */       catch (Exception e)
/* 171:    */       {
/* 172:168 */         e.printStackTrace();
/* 173:    */       }
/* 174:173 */       Map<String, String> filtros = new HashMap();
/* 175:174 */       filtros.put("idGuiaRemision", guiaRemision.getIdGuiaRemision() + "");
/* 176:175 */       List<ComprobanteElectronicoPendienteSRI> listaComprobante = this.comprobanteElectronicoPendienteSRIDao.obtenerListaCombo("idGuiaRemision", true, filtros);
/* 177:    */       
/* 178:177 */       ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI = null;
/* 179:178 */       if (listaComprobante.isEmpty())
/* 180:    */       {
/* 181:179 */         comprobanteElectronicoPendienteSRI = new ComprobanteElectronicoPendienteSRI();
/* 182:180 */         comprobanteElectronicoPendienteSRI.setIdOrganizacion(guiaRemision.getIdOrganizacion());
/* 183:181 */         comprobanteElectronicoPendienteSRI.setIdGuiaRemision(Integer.valueOf(guiaRemision.getIdGuiaRemision()));
/* 184:    */       }
/* 185:    */       else
/* 186:    */       {
/* 187:183 */         comprobanteElectronicoPendienteSRI = (ComprobanteElectronicoPendienteSRI)listaComprobante.get(0);
/* 188:    */       }
/* 189:185 */       comprobanteElectronicoPendienteSRI.setAmbiente(documento.getAmbiente());
/* 190:186 */       comprobanteElectronicoPendienteSRI.setClaveAcceso(documento.getClaveAcceso());
/* 191:187 */       comprobanteElectronicoPendienteSRI.setDocumentoBase(guiaRemision.getDocumento().getDocumentoBase());
/* 192:188 */       comprobanteElectronicoPendienteSRI.setEmails(guiaRemision.getEmail());
/* 193:189 */       comprobanteElectronicoPendienteSRI.setFechaEmision(guiaRemision.getFecha());
/* 194:190 */       comprobanteElectronicoPendienteSRI.setIdSucursal(guiaRemision.getIdSucursal());
/* 195:191 */       comprobanteElectronicoPendienteSRI.setNumero(guiaRemision.getNumero());
/* 196:192 */       comprobanteElectronicoPendienteSRI.setIndicadorNoEnviado(true);
/* 197:193 */       comprobanteElectronicoPendienteSRI.setIndicadorComprobarAutorizacion(false);
/* 198:194 */       comprobanteElectronicoPendienteSRI.setIndicadorRechazado(false);
/* 199:195 */       comprobanteElectronicoPendienteSRI.setTipoDocumento(documento.getTipoDocumento());
/* 200:196 */       comprobanteElectronicoPendienteSRI.setTipoEmision(documento.getTipoEmision());
/* 201:197 */       this.comprobanteElectronicoPendienteSRIDao.guardar(comprobanteElectronicoPendienteSRI);
/* 202:    */     }
/* 203:201 */     return guiaRemision;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public DocumentoElectronico autorizarGuiaRemision(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/* 207:    */     throws AS2Exception
/* 208:    */   {
/* 209:206 */     Organizacion organizacion = null;
/* 210:207 */     if (organizacion == null)
/* 211:    */     {
/* 212:208 */       int idOrganizacion = comprobanteElectronicoPendienteSRI.getIdOrganizacion();
/* 213:209 */       organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/* 214:    */     }
/* 215:211 */     DocumentoElectronico documento = new DocumentoElectronico(organizacion, comprobanteElectronicoPendienteSRI);
/* 216:    */     
/* 217:    */ 
/* 218:214 */     File ficheroFirmado = new File(documento.getPathArchivoFirmado());
/* 219:215 */     if (!ficheroFirmado.exists())
/* 220:    */     {
/* 221:216 */       documento = null;
/* 222:217 */       GuiaRemision guiaRemision = this.servicioGuiaRemision.cargarDetalle(comprobanteElectronicoPendienteSRI.getIdGuiaRemision().intValue());
/* 223:218 */       guiaRemision = generarClaveAcceso(documento, guiaRemision, false);
/* 224:219 */       documento = guiaRemision.getDocumentoElectronico();
/* 225:    */       
/* 226:    */ 
/* 227:222 */       generarXML(documento, guiaRemision);
/* 228:    */     }
/* 229:    */     try
/* 230:    */     {
/* 231:227 */       this.servicioDocumentoElectronico.autorizarDocumento(documento);
/* 232:    */     }
/* 233:    */     catch (AS2Exception e)
/* 234:    */     {
/* 235:229 */       documento.setMensajeSRI(e.getMensaje());
/* 236:    */     }
/* 237:231 */     return documento;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public DocumentoElectronico comprobarAutorizacion(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/* 241:    */     throws ExcepcionAS2DocumentoElectronico
/* 242:    */   {
/* 243:237 */     DocumentoElectronico documento = null;
/* 244:    */     try
/* 245:    */     {
/* 246:239 */       Organizacion organizacion = null;
/* 247:240 */       if (organizacion == null)
/* 248:    */       {
/* 249:241 */         int idOrganizacion = comprobanteElectronicoPendienteSRI.getIdOrganizacion();
/* 250:242 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/* 251:    */       }
/* 252:244 */       documento = new DocumentoElectronico(organizacion, comprobanteElectronicoPendienteSRI);
/* 253:    */       
/* 254:246 */       this.servicioDocumentoElectronico.comprobarAutorizacionDocumento(documento);
/* 255:    */     }
/* 256:    */     catch (AS2Exception e)
/* 257:    */     {
/* 258:248 */       documento.setMensajeSRI(e.getMensaje());
/* 259:    */     }
/* 260:    */     catch (Exception e)
/* 261:    */     {
/* 262:250 */       e.printStackTrace();
/* 263:251 */       throw new ExcepcionAS2DocumentoElectronico("msgs_error_autorizacion_documento_electronico", e.getMessage());
/* 264:    */     }
/* 265:253 */     return documento;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void generarXML(DocumentoElectronico documento, GuiaRemision guiaRemision)
/* 269:    */     throws AS2Exception
/* 270:    */   {
/* 271:258 */     String mensajeSRI = "";
/* 272:259 */     EstadoDocumentoElectronico estadoSRI = null;
/* 273:    */     try
/* 274:    */     {
/* 275:261 */       String numeroComprobante = FuncionesUtiles.completarALaIzquierda('0', 9, guiaRemision
/* 276:262 */         .getNumero().substring(8, guiaRemision.getNumero().length()));
/* 277:    */       
/* 278:264 */       GuiaRemisionJaxb guiaRemisionJaxb = new GuiaRemisionJaxb();
/* 279:265 */       InfoAdicionalJaxb infoAdicionalJaxb = null;
/* 280:    */       
/* 281:267 */       JAXBContext context = null;
/* 282:268 */       if (documento.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.GUIA_REMISION))
/* 283:    */       {
/* 284:269 */         context = JAXBContext.newInstance(new Class[] { GuiaRemisionJaxb.class });
/* 285:270 */         guiaRemisionJaxb = crearGuiaRemisionJaxb(documento, guiaRemision);
/* 286:271 */         crearDestinatariosJaxb(documento, guiaRemision, guiaRemisionJaxb);
/* 287:    */       }
/* 288:    */       else
/* 289:    */       {
/* 290:    */         SimpleDateFormat sdf;
/* 291:    */         String mensaje;
/* 292:273 */         return;
/* 293:    */       }
/* 294:276 */       guiaRemisionJaxb.setId("comprobante");
/* 295:277 */       guiaRemisionJaxb.setVersion(documento.getVersion());
/* 296:    */       
/* 297:    */ 
/* 298:280 */       InfoTributariaJaxb infoTributariaJaxb = new InfoTributariaJaxb();
/* 299:281 */       infoTributariaJaxb.setAmbiente(Integer.valueOf(documento.getAmbiente()));
/* 300:282 */       infoTributariaJaxb.setTipoEmision(Integer.valueOf(documento.getTipoEmision()));
/* 301:283 */       infoTributariaJaxb.setRazonSocial(documento.getAmbiente() == 2 ? documento.getOrganizacion().getRazonSocial() : "PRUEBAS SERVICIO DE RENTAS INTERNAS.");
/* 302:    */       
/* 303:285 */       infoTributariaJaxb.setNombreComercial(documento.getOrganizacion().getRazonSocial());
/* 304:286 */       infoTributariaJaxb.setRuc(documento.getOrganizacion().getIdentificacion());
/* 305:287 */       infoTributariaJaxb.setClaveAcceso(documento.getClaveAcceso());
/* 306:288 */       infoTributariaJaxb.setCodDoc(documento.getTipoDocumento().getCodigo());
/* 307:289 */       infoTributariaJaxb.setEstab(guiaRemision.getNumero().substring(0, 3));
/* 308:290 */       infoTributariaJaxb.setPtoEmi(guiaRemision.getNumero().substring(4, 7));
/* 309:291 */       infoTributariaJaxb.setSecuencial(numeroComprobante);
/* 310:292 */       infoTributariaJaxb.setDirMatriz(documento.getDireccionMatriz() == null ? "S/N" : documento.getDireccionMatriz());
/* 311:    */       
/* 312:294 */       guiaRemisionJaxb.setInfoTributaria(infoTributariaJaxb);
/* 313:297 */       if (infoAdicionalJaxb == null)
/* 314:    */       {
/* 315:298 */         infoAdicionalJaxb = new InfoAdicionalJaxb();
/* 316:299 */         infoAdicionalJaxb.setListaCampoAdicional(new ArrayList());
/* 317:    */       }
/* 318:301 */       SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
/* 319:302 */       infoAdicionalJaxb.getlListaCampoAdicional().add(
/* 320:303 */         agregarInformacionAdicional(infoAdicionalJaxb, "Hora Salida", formato.format(guiaRemision.getHoraSalida())));
/* 321:304 */       infoAdicionalJaxb.getlListaCampoAdicional().add(
/* 322:305 */         agregarInformacionAdicional(infoAdicionalJaxb, "Hora Llegada", formato.format(guiaRemision.getHoraLlegada())));
/* 323:307 */       if ((infoAdicionalJaxb != null) && (!infoAdicionalJaxb.getlListaCampoAdicional().isEmpty())) {
/* 324:308 */         guiaRemisionJaxb.setInfoAdicional(infoAdicionalJaxb);
/* 325:    */       }
/* 326:312 */       Marshaller m = context.createMarshaller();
/* 327:313 */       m.marshal(guiaRemisionJaxb, new File(documento.getPathArchivoEmitido()));
/* 328:    */       
/* 329:    */ 
/* 330:316 */       documento.firmar();
/* 331:    */       
/* 332:318 */       estadoSRI = EstadoDocumentoElectronico.EMITIDO;
/* 333:319 */       mensajeSRI = estadoSRI.toString();
/* 334:    */     }
/* 335:    */     catch (Exception e)
/* 336:    */     {
/* 337:    */       SimpleDateFormat sdf;
/* 338:    */       String mensaje;
/* 339:321 */       e.printStackTrace();
/* 340:322 */       mensajeSRI = e.getMessage();
/* 341:323 */       throw new AS2Exception(e.getMessage());
/* 342:    */     }
/* 343:    */     finally
/* 344:    */     {
/* 345:326 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
/* 346:327 */       mensajeSRI = sdf.format(new Date()) + " => " + mensajeSRI;
/* 347:328 */       String mensaje = mensajeSRI.length() > 5000 ? mensajeSRI.substring(0, 5000) : mensajeSRI;
/* 348:329 */       this.servicioGuiaRemision.actualizaGuiaRemisionSRI(guiaRemision.getIdGuiaRemision(), Estado.EN_ESPERA_CONTINGENCIA, estadoSRI, null, null, mensaje);
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   private GuiaRemisionJaxb crearGuiaRemisionJaxb(DocumentoElectronico documento, GuiaRemision guiaRemision)
/* 353:    */   {
/* 354:337 */     GuiaRemisionJaxb guiaRemisionJaxb = new GuiaRemisionJaxb();
/* 355:338 */     InfoGuiaRemisionJaxb infoGuiaRemisionJaxb = new InfoGuiaRemisionJaxb();
/* 356:    */     
/* 357:340 */     infoGuiaRemisionJaxb.setDirEstablecimiento(documento.getDireccionSucursal() == null ? "S/N" : documento.getDireccionSucursal());
/* 358:341 */     infoGuiaRemisionJaxb.setDirPartida(documento.getDireccionSucursal() == null ? "S/N" : documento.getDireccionSucursal());
/* 359:342 */     infoGuiaRemisionJaxb.setRazonSocialTransportista(guiaRemision.getConductor());
/* 360:    */     
/* 361:344 */     String tipIdenTransportista = "07";
/* 362:346 */     if (guiaRemision.getIdHojaRuta() != 0)
/* 363:    */     {
/* 364:347 */       if (guiaRemision.getLicencia().trim().length() == 13) {
/* 365:348 */         tipIdenTransportista = "04";
/* 366:349 */       } else if (guiaRemision.getLicencia().trim().length() == 10) {
/* 367:350 */         tipIdenTransportista = "05";
/* 368:    */       }
/* 369:    */     }
/* 370:    */     else {
/* 371:353 */       tipIdenTransportista = FuncionesUtiles.getTipoIdentificacionClienteSRI(guiaRemision.getTipoIdentificacionTransportista().getCodigo());
/* 372:    */     }
/* 373:356 */     infoGuiaRemisionJaxb.setTipoIdentificacionTransportista(tipIdenTransportista);
/* 374:357 */     infoGuiaRemisionJaxb.setRucTransportista(guiaRemision.getLicencia());
/* 375:358 */     infoGuiaRemisionJaxb
/* 376:359 */       .setObligadoContabilidad(documento.getOrganizacion().getOrganizacionConfiguracion().isIndicadorObligadoContabilidad() ? "SI" : "NO");
/* 377:361 */     if ((documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente() != null) && 
/* 378:362 */       (!documento.getOrganizacion().getOrganizacionConfiguracion().getNumeroResolucionContribuyente().equals(""))) {
/* 379:363 */       infoGuiaRemisionJaxb.setContribuyenteEspecial(documento.getOrganizacion().getOrganizacionConfiguracion()
/* 380:364 */         .getNumeroResolucionContribuyente());
/* 381:    */     }
/* 382:367 */     infoGuiaRemisionJaxb.setFechaIniTransporte(formatoFecha.format(guiaRemision.getFecha()));
/* 383:368 */     infoGuiaRemisionJaxb.setFechaFinTransporte(formatoFecha.format(guiaRemision.getFechaVigencia()));
/* 384:369 */     infoGuiaRemisionJaxb.setPlaca(guiaRemision.getPlaca());
/* 385:    */     
/* 386:371 */     guiaRemisionJaxb.setInfoGuiaRemision(infoGuiaRemisionJaxb);
/* 387:372 */     return guiaRemisionJaxb;
/* 388:    */   }
/* 389:    */   
/* 390:    */   private void crearDestinatariosJaxb(DocumentoElectronico documento, GuiaRemision guiaRemision, GuiaRemisionJaxb guiaRemisionJaxb)
/* 391:    */   {
/* 392:376 */     DestinatariosJaxb destinatariosJaxb = new DestinatariosJaxb();
/* 393:377 */     destinatariosJaxb.setListaDestinatario(new ArrayList());
/* 394:    */     
/* 395:379 */     MovimientoInventario movimientoInventario = null;
/* 396:380 */     if (guiaRemision.getTransferenciaBodega() != null) {
/* 397:381 */       movimientoInventario = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(guiaRemision.getTransferenciaBodega().getId()));
/* 398:    */     }
/* 399:383 */     HojaRuta hojaRutaTransportista = null;
/* 400:384 */     if (guiaRemision.getHojaRutaTransportista() != null) {
/* 401:385 */       hojaRutaTransportista = this.servicioHojaRuta.cargarDetalle(Integer.valueOf(guiaRemision.getHojaRutaTransportista().getIdHojaRuta()));
/* 402:    */     }
/* 403:388 */     if (hojaRutaTransportista != null)
/* 404:    */     {
/* 405:389 */       for (DetalleHojaRuta dhr : hojaRutaTransportista.getListaDetalleHojaRuta()) {
/* 406:390 */         if (!dhr.isEliminado())
/* 407:    */         {
/* 408:391 */           DestinatarioJaxb destinatarioJaxb = new DestinatarioJaxb();
/* 409:392 */           destinatarioJaxb.setIdentificacionDestinatario(dhr.getDespachoCliente().getEmpresa().getIdentificacion());
/* 410:393 */           destinatarioJaxb.setRazonSocialDestinatario(dhr.getDespachoCliente().getEmpresa().getNombreFiscal());
/* 411:394 */           destinatarioJaxb.setDirDestinatario(dhr.getDespachoCliente().getDireccionEmpresa().getDireccionCompleta());
/* 412:395 */           destinatarioJaxb.setMotivoTraslado("Hoja Ruta Transportista");
/* 413:396 */           destinatarioJaxb.setRuta(guiaRemision.getCiudadOrigen().getNombre() + " - " + guiaRemision.getCiudadDestino().getNombre());
/* 414:    */           
/* 415:    */ 
/* 416:399 */           DetallesJaxb detallesJaxb = new DetallesJaxb();
/* 417:400 */           detallesJaxb.setListaDetalle(new ArrayList());
/* 418:402 */           for (DetalleDespachoCliente ddc : dhr.getDespachoCliente().getListaDetalleDespachoCliente()) {
/* 419:403 */             if (!ddc.isEliminado())
/* 420:    */             {
/* 421:404 */               DetalleJaxb detalleJaxb = new DetalleJaxb();
/* 422:405 */               detalleJaxb.setCodigoInterno(ddc.getProducto().getCodigo());
/* 423:406 */               detalleJaxb.setDescripcion(ddc.getProducto().getNombre());
/* 424:407 */               detalleJaxb.setCantidad(ddc.getCantidad().setScale(6, RoundingMode.HALF_UP));
/* 425:    */               
/* 426:409 */               detallesJaxb.getListaDetalle().add(detalleJaxb);
/* 427:    */             }
/* 428:    */           }
/* 429:412 */           destinatarioJaxb.setDetalles(detallesJaxb);
/* 430:413 */           destinatariosJaxb.getListaDestinatario().add(destinatarioJaxb);
/* 431:    */         }
/* 432:    */       }
/* 433:    */     }
/* 434:    */     else
/* 435:    */     {
/* 436:417 */       DestinatarioJaxb destinatarioJaxb = new DestinatarioJaxb();
/* 437:418 */       if (guiaRemision.getEmpresa() != null)
/* 438:    */       {
/* 439:419 */         destinatarioJaxb.setIdentificacionDestinatario(guiaRemision.getEmpresa().getIdentificacion());
/* 440:420 */         destinatarioJaxb.setRazonSocialDestinatario(guiaRemision.getEmpresa().getNombreFiscal());
/* 441:421 */         destinatarioJaxb.setDirDestinatario(guiaRemision.getDireccionEmpresa().getDireccionCompleta());
/* 442:422 */         destinatarioJaxb.setMotivoTraslado("Despacho cliente");
/* 443:    */       }
/* 444:423 */       else if (movimientoInventario != null)
/* 445:    */       {
/* 446:424 */         destinatarioJaxb.setIdentificacionDestinatario(guiaRemision.getDocumentoElectronico().getOrganizacion().getIdentificacion());
/* 447:425 */         destinatarioJaxb.setRazonSocialDestinatario(guiaRemision.getDocumentoElectronico().getOrganizacion().getRazonSocial());
/* 448:426 */         destinatarioJaxb.setDirDestinatario(movimientoInventario.getBodegaDestino().getUbicacion().getDireccionCompleta());
/* 449:427 */         destinatarioJaxb.setMotivoTraslado("Transferencia entre bodegas");
/* 450:    */       }
/* 451:    */       else
/* 452:    */       {
/* 453:429 */         destinatarioJaxb.setIdentificacionDestinatario(guiaRemision.getTransportista().getIdentificacion());
/* 454:430 */         destinatarioJaxb.setRazonSocialDestinatario(guiaRemision.getTransportista().getNombre());
/* 455:431 */         destinatarioJaxb.setDirDestinatario(guiaRemision.getTransportista().getDireccion());
/* 456:432 */         destinatarioJaxb.setMotivoTraslado("Exportacion");
/* 457:    */       }
/* 458:434 */       destinatarioJaxb.setRuta(guiaRemision.getCiudadOrigen().getNombre() + " - " + guiaRemision.getCiudadDestino().getNombre());
/* 459:    */       String puntoVentaFactura;
/* 460:436 */       if (guiaRemision.getFacturaCliente() != null)
/* 461:    */       {
/* 462:437 */         destinatarioJaxb.setCodDocSustento("01");
/* 463:    */         
/* 464:439 */         String sucursalFactura = FuncionesUtiles.completarALaIzquierda('0', 3, guiaRemision.getFacturaCliente().getNumero().substring(0, 3));
/* 465:    */         
/* 466:441 */         puntoVentaFactura = FuncionesUtiles.completarALaIzquierda('0', 3, guiaRemision.getFacturaCliente().getNumero().substring(4, 7));
/* 467:442 */         String secuencialFactura = FuncionesUtiles.completarALaIzquierda('0', 9, guiaRemision.getFacturaCliente().getNumero().substring(8));
/* 468:    */         
/* 469:444 */         destinatarioJaxb.setNumDocSustento(sucursalFactura + "-" + puntoVentaFactura + "-" + secuencialFactura);
/* 470:445 */         destinatarioJaxb.setNumAutDocSustento(guiaRemision.getFacturaCliente().getFacturaClienteSRI().getAutorizacion());
/* 471:446 */         destinatarioJaxb.setFechaEmisionDocSustento(formatoFecha.format(guiaRemision.getFacturaCliente().getFecha()));
/* 472:    */       }
/* 473:450 */       DetallesJaxb detallesJaxb = new DetallesJaxb();
/* 474:451 */       detallesJaxb.setListaDetalle(new ArrayList());
/* 475:452 */       for (DetalleGuiaRemision detalleGuia : guiaRemision.getListaDetalleGuiaRemision()) {
/* 476:453 */         if ((!detalleGuia.isEliminado()) && (detalleGuia.getProducto() != null))
/* 477:    */         {
/* 478:454 */           DetalleJaxb detalleJaxb = new DetalleJaxb();
/* 479:455 */           detalleJaxb.setCodigoInterno(detalleGuia.getProducto().getCodigo());
/* 480:456 */           detalleJaxb.setDescripcion(detalleGuia.getProducto().getNombre());
/* 481:457 */           detalleJaxb.setCantidad(detalleGuia.getCantidad().setScale(6, RoundingMode.HALF_UP));
/* 482:    */           
/* 483:459 */           detallesJaxb.getListaDetalle().add(detalleJaxb);
/* 484:    */         }
/* 485:    */       }
/* 486:462 */       destinatarioJaxb.setDetalles(detallesJaxb);
/* 487:463 */       destinatariosJaxb.getListaDestinatario().add(destinatarioJaxb);
/* 488:    */     }
/* 489:465 */     guiaRemisionJaxb.setDestinatarios(destinatariosJaxb);
/* 490:    */   }
/* 491:    */   
/* 492:    */   private CampoAdicionalJaxb agregarInformacionAdicional(InfoAdicionalJaxb infoAdicionalJaxb, String nombreCampo, String valorCampo)
/* 493:    */   {
/* 494:469 */     CampoAdicionalJaxb campoAdicionalJaxb = new CampoAdicionalJaxb();
/* 495:470 */     campoAdicionalJaxb.setNombre(nombreCampo);
/* 496:471 */     campoAdicionalJaxb.setValor(valorCampo);
/* 497:    */     
/* 498:473 */     return campoAdicionalJaxb;
/* 499:    */   }
/* 500:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.servicio.impl.ServicioGuiaRemisionSRIXMLImpl
 * JD-Core Version:    0.7.0.1
 */