/*   1:    */ package com.asinfo.as2.compronteselectronicos.base;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   6:    */ import com.asinfo.as2.entities.GuiaRemision;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
/*  11:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  12:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.utils.EjbUtil;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import ec.gob.sri.comprobantes.ws.autorizacion.Autorizacion;
/*  17:    */ import ec.gob.sri.comprobantes.ws.autorizacion.AutorizacionMensajes;
/*  18:    */ import ec.gob.sri.comprobantes.ws.recepcion.Comprobante;
/*  19:    */ import ec.gob.sri.comprobantes.ws.recepcion.ComprobanteMensajes;
/*  20:    */ import ec.gob.sri.comprobantes.ws.recepcion.RespuestaSolicitud;
/*  21:    */ import ec.gob.sri.comprobantes.ws.recepcion.RespuestaSolicitudComprobantes;
/*  22:    */ import java.io.File;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.io.Serializable;
/*  25:    */ import java.text.SimpleDateFormat;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.Map;
/*  29:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  30:    */ 
/*  31:    */ public class DocumentoElectronico
/*  32:    */   implements Serializable
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35: 52 */   public static String DOC_EMITIDO = "emitido";
/*  36: 53 */   public static String DOC_FIRMADO = "firmado";
/*  37: 54 */   public static String DOC_AUTORIZADO = "autorizado";
/*  38: 55 */   public static String DOC_NO_AUTORIZADO = "no_autorizado";
/*  39: 56 */   public static String DOC_PENDIENTE_CONTINGENCIA = "pendiente_contingencia";
/*  40:    */   private String pkcs12_resource;
/*  41: 60 */   private String pkcs12_password = "";
/*  42: 62 */   private String pathDocumento = null;
/*  43: 63 */   private String pathSRI = null;
/*  44:    */   private String pathArchivoEmitido;
/*  45:    */   private String pathArchivoFirmado;
/*  46:    */   private String pathArchivoAutorizado;
/*  47:    */   private String pathArchivoNoAutorizado;
/*  48:    */   private String pathArchivoPendienteContingencia;
/*  49:    */   private Organizacion organizacion;
/*  50:    */   private String version;
/*  51:    */   private int ambiente;
/*  52:    */   private int tipoEmision;
/*  53:    */   private String codigoUnico;
/*  54: 75 */   private String numeroAutorizacion = null;
/*  55:    */   private Date fechaAutorizacion;
/*  56: 77 */   private String claveAcceso = null;
/*  57: 78 */   private String numeroDocumento = null;
/*  58:    */   private String email;
/*  59:    */   private String direccionMatriz;
/*  60:    */   private String direccionSucursal;
/*  61: 82 */   private EstadoDocumentoElectronico estado = EstadoDocumentoElectronico.EMITIDO;
/*  62:    */   private TipoDocumentoElectronicoEnum tipoDocumento;
/*  63:    */   private Date fechaEmision;
/*  64:    */   private String telefonoSucursal;
/*  65:    */   private String mensajeSRI;
/*  66: 89 */   Sucursal sucursal = null;
/*  67:    */   private String nombreReporte;
/*  68:    */   private transient JRDataSource dataSource;
/*  69: 96 */   private static Map<String, String> hmDirectorios = new HashMap();
/*  70: 97 */   private static Map<String, String> hmClaveFirma = new HashMap();
/*  71:    */   
/*  72:    */   public DocumentoElectronico() {}
/*  73:    */   
/*  74:    */   public DocumentoElectronico(Organizacion organizacion, int ambiente, int tipoEmision, TipoDocumentoElectronicoEnum tipoDocumento, String version)
/*  75:    */     throws AS2Exception
/*  76:    */   {
/*  77:105 */     this.organizacion = organizacion;
/*  78:106 */     this.ambiente = ambiente;
/*  79:107 */     this.tipoEmision = tipoEmision;
/*  80:108 */     this.tipoDocumento = tipoDocumento;
/*  81:109 */     this.version = version;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public DocumentoElectronico(Organizacion organizacion, ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/*  85:    */     throws AS2Exception
/*  86:    */   {
/*  87:113 */     this.organizacion = organizacion;
/*  88:114 */     this.ambiente = comprobanteElectronicoPendienteSRI.getAmbiente();
/*  89:115 */     this.tipoEmision = comprobanteElectronicoPendienteSRI.getTipoEmision();
/*  90:116 */     this.tipoDocumento = comprobanteElectronicoPendienteSRI.getTipoDocumento();
/*  91:117 */     this.fechaEmision = comprobanteElectronicoPendienteSRI.getFechaEmision();
/*  92:118 */     this.claveAcceso = comprobanteElectronicoPendienteSRI.getClaveAcceso();
/*  93:119 */     this.email = comprobanteElectronicoPendienteSRI.getEmails();
/*  94:    */     
/*  95:121 */     String nombreDocumento = FuncionesUtiles.completarALaIzquierda('0', 9, comprobanteElectronicoPendienteSRI.getNumero()) + "-" + this.claveAcceso + ".xml";
/*  96:    */     
/*  97:123 */     actualizarPaths(nombreDocumento);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void init(EntidadBase entidad)
/* 101:    */     throws AS2Exception
/* 102:    */   {
/* 103:133 */     String identificacion = null;
/* 104:134 */     String serie = null;
/* 105:135 */     String nombreDocumento = null;
/* 106:137 */     if ((this.tipoDocumento == TipoDocumentoElectronicoEnum.FACTURA) || (this.tipoDocumento == TipoDocumentoElectronicoEnum.NOTA_CREDITO) || (this.tipoDocumento == TipoDocumentoElectronicoEnum.NOTA_DEBITO))
/* 107:    */     {
/* 108:139 */       FacturaCliente facturaCliente = (FacturaCliente)entidad;
/* 109:140 */       identificacion = this.organizacion.getIdentificacion();
/* 110:141 */       this.fechaEmision = facturaCliente.getFecha();
/* 111:142 */       serie = facturaCliente.getFacturaClienteSRI().getEstablecimiento() + facturaCliente.getFacturaClienteSRI().getPuntoEmision();
/* 112:143 */       this.numeroDocumento = FuncionesUtiles.completarALaIzquierda('0', 9, facturaCliente.getFacturaClienteSRI().getNumero());
/* 113:    */       
/* 114:    */ 
/* 115:146 */       nombreDocumento = facturaCliente.getFacturaClienteSRI().getEstablecimiento() + "-" + facturaCliente.getFacturaClienteSRI().getPuntoEmision() + "-" + this.numeroDocumento;
/* 116:    */       
/* 117:148 */       this.sucursal = facturaCliente.getSucursal();
/* 118:    */     }
/* 119:151 */     if (this.tipoDocumento == TipoDocumentoElectronicoEnum.RETENCION)
/* 120:    */     {
/* 121:152 */       FacturaProveedorSRI facturaProveedorSRI = (FacturaProveedorSRI)entidad;
/* 122:153 */       identificacion = this.organizacion.getIdentificacion();
/* 123:154 */       this.fechaEmision = facturaProveedorSRI.getFechaEmisionRetencion();
/* 124:155 */       serie = facturaProveedorSRI.getEstablecimientoRetencion() + facturaProveedorSRI.getPuntoEmisionRetencion();
/* 125:156 */       this.numeroDocumento = FuncionesUtiles.completarALaIzquierda('0', 9, facturaProveedorSRI.getNumeroRetencion());
/* 126:    */       
/* 127:158 */       nombreDocumento = facturaProveedorSRI.getEstablecimientoRetencion() + "-" + facturaProveedorSRI.getPuntoEmisionRetencion() + "-" + this.numeroDocumento;
/* 128:    */     }
/* 129:162 */     if (this.tipoDocumento == TipoDocumentoElectronicoEnum.GUIA_REMISION)
/* 130:    */     {
/* 131:163 */       GuiaRemision guiaRemision = (GuiaRemision)entidad;
/* 132:164 */       identificacion = this.organizacion.getIdentificacion();
/* 133:165 */       this.fechaEmision = guiaRemision.getFecha();
/* 134:166 */       serie = guiaRemision.getNumero().substring(0, 3) + guiaRemision.getNumero().substring(4, 7);
/* 135:167 */       this.numeroDocumento = FuncionesUtiles.completarALaIzquierda('0', 9, guiaRemision.getNumero().substring(8, guiaRemision.getNumero().length()));
/* 136:    */       
/* 137:169 */       nombreDocumento = guiaRemision.getNumero().substring(0, 3) + "-" + guiaRemision.getNumero().substring(4, 7) + "-" + this.numeroDocumento;
/* 138:    */     }
/* 139:173 */     if (this.sucursal != null) {
/* 140:176 */       this.telefonoSucursal = (this.sucursal.getTelefono2() != null ? "   " + this.sucursal.getTelefono2() : this.sucursal.getTelefono1() != null ? this.sucursal.getTelefono1().concat(this.sucursal.getTelefono2() != null ? "   " + this.sucursal.getTelefono2() : "") : "");
/* 141:    */     }
/* 142:179 */     this.claveAcceso = generarClaveAcceso(this.ambiente, this.fechaEmision, identificacion, this.tipoDocumento.getCodigo(), serie, this.numeroDocumento, this.codigoUnico, this.tipoEmision);
/* 143:    */     
/* 144:    */ 
/* 145:182 */     nombreDocumento = nombreDocumento + "-" + this.claveAcceso + ".xml";
/* 146:183 */     actualizarPaths(nombreDocumento);
/* 147:    */     
/* 148:185 */     this.pkcs12_password = ((String)hmClaveFirma.get(this.organizacion.getCodigoOrganizacion()));
/* 149:186 */     if (this.pkcs12_password == null) {
/* 150:    */       try
/* 151:    */       {
/* 152:189 */         this.pkcs12_password = this.organizacion.getOrganizacionConfiguracion().getPkcs12Password();
/* 153:190 */         if ((this.pkcs12_password == null) || (this.pkcs12_password.isEmpty())) {
/* 154:191 */           this.pkcs12_password = EjbUtil.obtenerValorArchivoProperties("clave", this.pathSRI + File.separator + "firmas", "clave_firma_electronica.properties");
/* 155:    */         }
/* 156:194 */         hmClaveFirma.put(this.organizacion.getCodigoOrganizacion(), this.pkcs12_password);
/* 157:    */       }
/* 158:    */       catch (IOException e)
/* 159:    */       {
/* 160:196 */         throw new AS2Exception(e.getMessage());
/* 161:    */       }
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void actualizarPaths(String nombreDocumento)
/* 166:    */     throws AS2Exception
/* 167:    */   {
/* 168:202 */     this.pathSRI = (ServicioConfiguracion.AS2_HOME + File.separator + this.organizacion.getCodigoOrganizacion() + File.separator + "sri");
/* 169:203 */     this.pathDocumento = (this.pathSRI + File.separator + "documentos_electronicos" + File.separator + this.tipoDocumento.toString());
/* 170:204 */     this.pkcs12_resource = (this.pathSRI + File.separator + "firmas" + File.separator + "firma_electronica.p12");
/* 171:    */     
/* 172:206 */     this.pathArchivoEmitido = (this.pathDocumento + File.separator + DOC_EMITIDO);
/* 173:207 */     crearDirectorio(this.pathArchivoEmitido);
/* 174:208 */     this.pathArchivoEmitido = (this.pathArchivoEmitido + File.separator + nombreDocumento);
/* 175:    */     
/* 176:210 */     this.pathArchivoFirmado = (this.pathDocumento + File.separator + DOC_FIRMADO);
/* 177:211 */     crearDirectorio(this.pathArchivoFirmado);
/* 178:212 */     this.pathArchivoFirmado = (this.pathArchivoFirmado + File.separator + nombreDocumento);
/* 179:    */     
/* 180:214 */     this.pathArchivoAutorizado = (this.pathDocumento + File.separator + DOC_AUTORIZADO);
/* 181:215 */     crearDirectorio(this.pathArchivoAutorizado);
/* 182:216 */     this.pathArchivoAutorizado = (this.pathArchivoAutorizado + File.separator + nombreDocumento);
/* 183:    */     
/* 184:218 */     this.pathArchivoNoAutorizado = (this.pathDocumento + File.separator + DOC_NO_AUTORIZADO);
/* 185:219 */     crearDirectorio(this.pathArchivoNoAutorizado);
/* 186:220 */     this.pathArchivoNoAutorizado = (this.pathArchivoNoAutorizado + File.separator + nombreDocumento);
/* 187:    */     
/* 188:222 */     this.pathArchivoPendienteContingencia = (this.pathDocumento + File.separator + DOC_PENDIENTE_CONTINGENCIA);
/* 189:223 */     crearDirectorio(this.pathArchivoPendienteContingencia);
/* 190:224 */     this.pathArchivoPendienteContingencia = (this.pathArchivoPendienteContingencia + File.separator + nombreDocumento);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void prepararParaEnvioEmail(EntidadBase entidad, String emails, String nombreReporte)
/* 194:    */     throws AS2Exception
/* 195:    */   {
/* 196:229 */     this.email = emails;
/* 197:230 */     this.nombreReporte = nombreReporte;
/* 198:231 */     this.estado = EstadoDocumentoElectronico.AUTORIZADO;
/* 199:    */     
/* 200:233 */     String nombreDocumento = null;
/* 201:235 */     if ((this.tipoDocumento == TipoDocumentoElectronicoEnum.FACTURA) || (this.tipoDocumento == TipoDocumentoElectronicoEnum.NOTA_CREDITO) || (this.tipoDocumento == TipoDocumentoElectronicoEnum.NOTA_DEBITO))
/* 202:    */     {
/* 203:237 */       FacturaCliente facturaCliente = (FacturaCliente)entidad;
/* 204:238 */       this.fechaEmision = facturaCliente.getFecha();
/* 205:239 */       this.numeroDocumento = FuncionesUtiles.completarALaIzquierda('0', 9, facturaCliente.getFacturaClienteSRI().getNumero());
/* 206:    */       
/* 207:    */ 
/* 208:242 */       nombreDocumento = facturaCliente.getFacturaClienteSRI().getEstablecimiento() + "-" + facturaCliente.getFacturaClienteSRI().getPuntoEmision() + "-" + this.numeroDocumento;
/* 209:    */       
/* 210:244 */       this.sucursal = facturaCliente.getSucursal();
/* 211:    */       
/* 212:246 */       this.claveAcceso = facturaCliente.getFacturaClienteSRI().getClaveAcceso();
/* 213:    */     }
/* 214:248 */     if (this.tipoDocumento == TipoDocumentoElectronicoEnum.RETENCION)
/* 215:    */     {
/* 216:249 */       FacturaProveedorSRI facturaProveedorSRI = (FacturaProveedorSRI)entidad;
/* 217:250 */       this.fechaEmision = facturaProveedorSRI.getFechaEmisionRetencion();
/* 218:251 */       this.numeroDocumento = FuncionesUtiles.completarALaIzquierda('0', 9, facturaProveedorSRI.getNumeroRetencion());
/* 219:    */       
/* 220:253 */       nombreDocumento = facturaProveedorSRI.getEstablecimientoRetencion() + "-" + facturaProveedorSRI.getPuntoEmisionRetencion() + "-" + this.numeroDocumento;
/* 221:    */       
/* 222:    */ 
/* 223:256 */       this.claveAcceso = facturaProveedorSRI.getClaveAcceso();
/* 224:    */     }
/* 225:258 */     if (this.tipoDocumento == TipoDocumentoElectronicoEnum.GUIA_REMISION)
/* 226:    */     {
/* 227:259 */       GuiaRemision guiaRemision = (GuiaRemision)entidad;
/* 228:260 */       this.fechaEmision = guiaRemision.getFecha();
/* 229:261 */       this.numeroDocumento = FuncionesUtiles.completarALaIzquierda('0', 9, guiaRemision.getNumero().substring(8, guiaRemision.getNumero().length()));
/* 230:    */       
/* 231:263 */       nombreDocumento = guiaRemision.getNumero().substring(0, 3) + "-" + guiaRemision.getNumero().substring(4, 7) + "-" + this.numeroDocumento;
/* 232:264 */       this.claveAcceso = guiaRemision.getClaveAcceso();
/* 233:    */     }
/* 234:268 */     if (this.sucursal != null) {
/* 235:271 */       this.telefonoSucursal = (this.sucursal.getTelefono2() != null ? "   " + this.sucursal.getTelefono2() : this.sucursal.getTelefono1() != null ? this.sucursal.getTelefono1().concat(this.sucursal.getTelefono2() != null ? "   " + this.sucursal.getTelefono2() : "") : "");
/* 236:    */     }
/* 237:274 */     nombreDocumento = nombreDocumento + "-" + this.claveAcceso + ".xml";
/* 238:    */     
/* 239:276 */     actualizarPaths(nombreDocumento);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void crearDirectorio(String directorio)
/* 243:    */   {
/* 244:280 */     if (!hmDirectorios.containsKey(directorio))
/* 245:    */     {
/* 246:281 */       hmDirectorios.put(directorio, directorio);
/* 247:    */       
/* 248:283 */       File file = new File(directorio);
/* 249:284 */       if (!file.exists()) {
/* 250:285 */         file.mkdirs();
/* 251:    */       }
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void firmar()
/* 256:    */   {
/* 257:295 */     FirmaXAdES_BES xades = new FirmaXAdES_BES(this);
/* 258:296 */     xades.execute();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public static String generarClaveAcceso(int tipoAmbiente, Date fecha, String identificacion, String tipoComprobante, String serie, String numeroComprobante, String codigoUnico, int tipoEmision)
/* 262:    */   {
/* 263:318 */     SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
/* 264:319 */     String fechaEmision = formato.format(fecha);
/* 265:320 */     String codigoAcceso = "";
/* 266:321 */     if (tipoEmision == 1) {
/* 267:322 */       codigoAcceso = fechaEmision + tipoComprobante + identificacion + String.valueOf(tipoAmbiente) + serie + numeroComprobante + codigoUnico + tipoEmision;
/* 268:    */     } else {
/* 269:325 */       codigoAcceso = fechaEmision + tipoComprobante + identificacion + String.valueOf(tipoAmbiente) + codigoUnico + tipoEmision;
/* 270:    */     }
/* 271:329 */     int digitoVerificador = generarDigitoVerificador(codigoAcceso);
/* 272:    */     
/* 273:331 */     return codigoAcceso + String.valueOf(digitoVerificador);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public static int generarDigitoVerificador(String cadena)
/* 277:    */   {
/* 278:343 */     int baseMultiplicador = 7;
/* 279:344 */     int[] aux = new int[cadena.length()];
/* 280:345 */     int multiplicador = 2;
/* 281:346 */     int total = 0;
/* 282:347 */     int verificador = 0;
/* 283:348 */     for (int i = aux.length - 1; i >= 0; i--)
/* 284:    */     {
/* 285:349 */       aux[i] = Integer.parseInt("" + cadena.charAt(i));
/* 286:350 */       aux[i] *= multiplicador;
/* 287:351 */       multiplicador++;
/* 288:352 */       if (multiplicador > baseMultiplicador) {
/* 289:353 */         multiplicador = 2;
/* 290:    */       }
/* 291:355 */       total += aux[i];
/* 292:    */     }
/* 293:358 */     if ((total == 0) || (total == 1)) {
/* 294:359 */       verificador = 0;
/* 295:    */     } else {
/* 296:361 */       verificador = 11 - total % 11 == 11 ? 0 : 11 - total % 11;
/* 297:    */     }
/* 298:364 */     if (verificador == 10) {
/* 299:365 */       verificador = 1;
/* 300:    */     }
/* 301:368 */     return verificador;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public static String getEndPoint(int ambiente, String nombreServicio)
/* 305:    */     throws AS2Exception
/* 306:    */   {
/* 307:382 */     StringBuilder url = new StringBuilder();
/* 308:    */     try
/* 309:    */     {
/* 310:385 */       if (ambiente == 2) {
/* 311:386 */         url.append(EjbUtil.obtenerValorWebService("WSComprobantesElectronicos"));
/* 312:387 */       } else if (ambiente == 1) {
/* 313:388 */         url.append(EjbUtil.obtenerValorWebService("WSComprobantesElectronicosTest"));
/* 314:    */       }
/* 315:391 */       url.append("/comprobantes-electronicos-ws/");
/* 316:392 */       url.append(nombreServicio);
/* 317:393 */       url.append("?wsdl");
/* 318:    */       
/* 319:395 */       return url.toString();
/* 320:    */     }
/* 321:    */     catch (IOException e)
/* 322:    */     {
/* 323:398 */       throw new AS2Exception(e.getMessage());
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   public static String getEndPointFacturaE()
/* 328:    */     throws AS2Exception
/* 329:    */   {
/* 330:404 */     StringBuilder url = new StringBuilder();
/* 331:    */     try
/* 332:    */     {
/* 333:407 */       url.append(EjbUtil.obtenerValorWebService("WSFacturaE"));
/* 334:    */       
/* 335:409 */       return url.toString();
/* 336:    */     }
/* 337:    */     catch (IOException e)
/* 338:    */     {
/* 339:412 */       throw new AS2Exception(e.getMessage());
/* 340:    */     }
/* 341:    */   }
/* 342:    */   
/* 343:    */   public static String obtieneMensajesAutorizacion(Autorizacion autorizacion)
/* 344:    */   {
/* 345:417 */     StringBuilder mensaje = new StringBuilder();
/* 346:419 */     for (ec.gob.sri.comprobantes.ws.autorizacion.Mensaje m : autorizacion.getMensajes().getMensaje()) {
/* 347:420 */       if (m.getInformacionAdicional() != null) {
/* 348:421 */         mensaje.append("\n" + m.getMensaje() + ": " + m.getInformacionAdicional());
/* 349:    */       } else {
/* 350:423 */         mensaje.append("\n" + m.getMensaje());
/* 351:    */       }
/* 352:    */     }
/* 353:427 */     return mensaje.toString();
/* 354:    */   }
/* 355:    */   
/* 356:    */   public static String obtieneMensajesRecepcion(RespuestaSolicitud respuestaSolicitud)
/* 357:    */   {
/* 358:431 */     StringBuilder mensaje = new StringBuilder();
/* 359:433 */     for (Comprobante comprobante : respuestaSolicitud.getComprobantes().getComprobante()) {
/* 360:434 */       for (ec.gob.sri.comprobantes.ws.recepcion.Mensaje m : comprobante.getMensajes().getMensaje()) {
/* 361:435 */         if (m.getInformacionAdicional() != null) {
/* 362:436 */           mensaje.append("\n" + m.getMensaje() + ": " + m.getInformacionAdicional());
/* 363:    */         } else {
/* 364:438 */           mensaje.append("\n" + m.getMensaje());
/* 365:    */         }
/* 366:    */       }
/* 367:    */     }
/* 368:443 */     return mensaje.toString();
/* 369:    */   }
/* 370:    */   
/* 371:    */   public String getPkcs12_resource()
/* 372:    */   {
/* 373:447 */     return this.pkcs12_resource;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setPkcs12_resource(String pkcs12_resource)
/* 377:    */   {
/* 378:451 */     this.pkcs12_resource = pkcs12_resource;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public String getPkcs12_password()
/* 382:    */   {
/* 383:455 */     return this.pkcs12_password;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setPkcs12_password(String pkcs12_password)
/* 387:    */   {
/* 388:459 */     this.pkcs12_password = pkcs12_password;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public String getPathArchivoEmitido()
/* 392:    */   {
/* 393:463 */     return this.pathArchivoEmitido;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public String getPathArchivoFirmado()
/* 397:    */   {
/* 398:467 */     return this.pathArchivoFirmado;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public String getPathDocumento()
/* 402:    */   {
/* 403:471 */     return this.pathDocumento;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setPathDocumento(String pathDocumento)
/* 407:    */   {
/* 408:475 */     this.pathDocumento = pathDocumento;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public String getNumeroAutorizacion()
/* 412:    */   {
/* 413:479 */     return this.numeroAutorizacion;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setNumeroAutorizacion(String numeroAutorizacion)
/* 417:    */   {
/* 418:483 */     this.numeroAutorizacion = numeroAutorizacion;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public String getClaveAcceso()
/* 422:    */   {
/* 423:487 */     return this.claveAcceso;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setClaveAcceso(String claveAcceso)
/* 427:    */   {
/* 428:491 */     this.claveAcceso = claveAcceso;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public String getNumeroDocumento()
/* 432:    */   {
/* 433:495 */     return this.numeroDocumento;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setNumeroDocumento(String numeroDocumento)
/* 437:    */   {
/* 438:499 */     this.numeroDocumento = numeroDocumento;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public String getPathArchivoAutorizado()
/* 442:    */   {
/* 443:503 */     return this.pathArchivoAutorizado;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setPathArchivoAutorizado(String pathArchivoAutorizado)
/* 447:    */   {
/* 448:507 */     this.pathArchivoAutorizado = pathArchivoAutorizado;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void setPathArchivoEmitido(String pathArchivoEmitido)
/* 452:    */   {
/* 453:511 */     this.pathArchivoEmitido = pathArchivoEmitido;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setPathArchivoFirmado(String pathArchivoFirmado)
/* 457:    */   {
/* 458:515 */     this.pathArchivoFirmado = pathArchivoFirmado;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public int getAmbiente()
/* 462:    */   {
/* 463:519 */     return this.ambiente;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setAmbiente(int ambiente)
/* 467:    */   {
/* 468:523 */     this.ambiente = ambiente;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public int getTipoEmision()
/* 472:    */   {
/* 473:527 */     return this.tipoEmision;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setTipoEmision(int tipoEmision)
/* 477:    */   {
/* 478:531 */     this.tipoEmision = tipoEmision;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public String getPathArchivoNoAutorizado()
/* 482:    */   {
/* 483:535 */     return this.pathArchivoNoAutorizado;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setPathArchivoNoAutorizado(String pathArchivoNoAutorizado)
/* 487:    */   {
/* 488:539 */     this.pathArchivoNoAutorizado = pathArchivoNoAutorizado;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public TipoDocumentoElectronicoEnum getTipoDocumento()
/* 492:    */   {
/* 493:543 */     return this.tipoDocumento;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void setTipoDocumento(TipoDocumentoElectronicoEnum tipoDocumento)
/* 497:    */   {
/* 498:547 */     this.tipoDocumento = tipoDocumento;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public JRDataSource getDataSource()
/* 502:    */   {
/* 503:551 */     return this.dataSource;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public void setDataSource(JRDataSource dataSource)
/* 507:    */   {
/* 508:555 */     this.dataSource = dataSource;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public String getNombreReporte()
/* 512:    */   {
/* 513:559 */     return this.nombreReporte;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public void setNombreReporte(String nombreReporte)
/* 517:    */   {
/* 518:563 */     this.nombreReporte = nombreReporte;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public String getEmail()
/* 522:    */   {
/* 523:567 */     return this.email;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public void setEmail(String email)
/* 527:    */   {
/* 528:571 */     this.email = email;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public EstadoDocumentoElectronico getEstado()
/* 532:    */   {
/* 533:575 */     return this.estado;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public void setEstado(EstadoDocumentoElectronico estado)
/* 537:    */   {
/* 538:579 */     this.estado = estado;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public Organizacion getOrganizacion()
/* 542:    */   {
/* 543:583 */     return this.organizacion;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public void setOrganizacion(Organizacion organizacion)
/* 547:    */   {
/* 548:587 */     this.organizacion = organizacion;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public String getCodigoUnico()
/* 552:    */   {
/* 553:591 */     return this.codigoUnico;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void setCodigoUnico(String codigoUnico)
/* 557:    */   {
/* 558:595 */     this.codigoUnico = codigoUnico;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public Date getFechaAutorizacion()
/* 562:    */   {
/* 563:599 */     return this.fechaAutorizacion;
/* 564:    */   }
/* 565:    */   
/* 566:    */   public void setFechaAutorizacion(Date fechaAutorizacion)
/* 567:    */   {
/* 568:603 */     this.fechaAutorizacion = fechaAutorizacion;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public String getDireccionMatriz()
/* 572:    */   {
/* 573:607 */     return this.direccionMatriz;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public void setDireccionMatriz(String direccionMatriz)
/* 577:    */   {
/* 578:611 */     this.direccionMatriz = direccionMatriz;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public String getDireccionSucursal()
/* 582:    */   {
/* 583:615 */     return this.direccionSucursal;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public void setDireccionSucursal(String direccionSucursal)
/* 587:    */   {
/* 588:619 */     this.direccionSucursal = direccionSucursal;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public String getVersion()
/* 592:    */   {
/* 593:623 */     return this.version;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public void setVersion(String version)
/* 597:    */   {
/* 598:627 */     this.version = version;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public String getPathArchivoPendienteContingencia()
/* 602:    */   {
/* 603:631 */     return this.pathArchivoPendienteContingencia;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void setPathArchivoPendienteContingencia(String pathArchivoPendienteContingencia)
/* 607:    */   {
/* 608:635 */     this.pathArchivoPendienteContingencia = pathArchivoPendienteContingencia;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public Date getFechaEmision()
/* 612:    */   {
/* 613:639 */     return this.fechaEmision;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public void setFechaEmision(Date fechaEmision)
/* 617:    */   {
/* 618:643 */     this.fechaEmision = fechaEmision;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public String getTelefonoSucursal()
/* 622:    */   {
/* 623:647 */     return this.telefonoSucursal;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public void setTelefonoSucursal(String telefonoSucursal)
/* 627:    */   {
/* 628:651 */     this.telefonoSucursal = telefonoSucursal;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public String getMensajeSRI()
/* 632:    */   {
/* 633:655 */     return this.mensajeSRI;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public void setMensajeSRI(String mensajeSRI)
/* 637:    */   {
/* 638:659 */     this.mensajeSRI = mensajeSRI;
/* 639:    */   }
/* 640:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico
 * JD-Core Version:    0.7.0.1
 */