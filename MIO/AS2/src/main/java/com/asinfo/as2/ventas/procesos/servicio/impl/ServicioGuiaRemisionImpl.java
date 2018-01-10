/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.ServicioGuiaRemisionSRIXML;
/*   5:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*   6:    */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*   7:    */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*   8:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   9:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*  10:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*  11:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*  12:    */ import com.asinfo.as2.dao.DespachoClienteDao;
/*  13:    */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*  14:    */ import com.asinfo.as2.dao.GuiaRemisionDao;
/*  15:    */ import com.asinfo.as2.dao.reportes.ventas.ReporteGuiaRemisionDao;
/*  16:    */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*  17:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  18:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  19:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  20:    */ import com.asinfo.as2.entities.Ciudad;
/*  21:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  22:    */ import com.asinfo.as2.entities.DetalleGuiaRemision;
/*  23:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  24:    */ import com.asinfo.as2.entities.Documento;
/*  25:    */ import com.asinfo.as2.entities.Empresa;
/*  26:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  27:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  28:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  29:    */ import com.asinfo.as2.entities.Organizacion;
/*  30:    */ import com.asinfo.as2.entities.Producto;
/*  31:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  32:    */ import com.asinfo.as2.entities.Sucursal;
/*  33:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  34:    */ import com.asinfo.as2.entities.Transportista;
/*  35:    */ import com.asinfo.as2.entities.Ubicacion;
/*  36:    */ import com.asinfo.as2.entities.Vehiculo;
/*  37:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  38:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  39:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  40:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  41:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  42:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioVehiculo;
/*  43:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  44:    */ import com.asinfo.as2.util.AppUtil;
/*  45:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  46:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  47:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  48:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  49:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  50:    */ import com.asinfo.as2.ventas.reportes.ReporteGuiaRemisionBean;
/*  51:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  52:    */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*  53:    */ import java.io.IOException;
/*  54:    */ import java.io.InputStream;
/*  55:    */ import java.io.PrintStream;
/*  56:    */ import java.math.BigDecimal;
/*  57:    */ import java.util.ArrayList;
/*  58:    */ import java.util.Date;
/*  59:    */ import java.util.HashMap;
/*  60:    */ import java.util.List;
/*  61:    */ import java.util.Map;
/*  62:    */ import javax.ejb.EJB;
/*  63:    */ import javax.ejb.Stateless;
/*  64:    */ import javax.ejb.TransactionAttribute;
/*  65:    */ import javax.ejb.TransactionAttributeType;
/*  66:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  67:    */ import org.apache.log4j.Logger;
/*  68:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  69:    */ 
/*  70:    */ @Stateless
/*  71:    */ public class ServicioGuiaRemisionImpl
/*  72:    */   extends AbstractServicioAS2
/*  73:    */   implements ServicioGuiaRemision
/*  74:    */ {
/*  75:    */   @EJB
/*  76:    */   private transient GuiaRemisionDao guiaRemisionDao;
/*  77:    */   @EJB
/*  78:    */   private transient ServicioSecuencia servicioSecuencia;
/*  79:    */   @EJB
/*  80:    */   private transient ServicioDocumento servicioDocumento;
/*  81:    */   @EJB
/*  82:    */   private transient ServicioEmpresa servicioEmpresa;
/*  83:    */   @EJB
/*  84:    */   private transient ServicioGuiaRemisionSRIXML servicioGuiaRemisionSRIXML;
/*  85:    */   @EJB
/*  86:    */   private transient ServicioTransportista servicioTransportista;
/*  87:    */   @EJB
/*  88:    */   private transient ServicioVehiculo servicioVehiculo;
/*  89:    */   @EJB
/*  90:    */   private ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  91:    */   @EJB
/*  92:    */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  93:    */   @EJB
/*  94:    */   private transient ServicioCiudad servicioCiudad;
/*  95:    */   @EJB
/*  96:    */   private transient DireccionEmpresaDao direccionEmpresaDao;
/*  97:    */   @EJB
/*  98:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  99:    */   @EJB
/* 100:    */   private transient ServicioSucursal servicioSucursal;
/* 101:    */   @EJB
/* 102:    */   private transient ServicioOrganizacion servicioOrganizacion;
/* 103:    */   @EJB
/* 104:    */   private transient ServicioProducto servicioProducto;
/* 105:    */   @EJB
/* 106:    */   private transient ServicioGuiaRemision servicioGuiaRemision;
/* 107:    */   @EJB
/* 108:    */   private transient DespachoClienteDao despachoClienteDao;
/* 109:    */   @EJB
/* 110:    */   private ReporteGuiaRemisionDao reporteGuiaRemisionDao;
/* 111:    */   @EJB
/* 112:    */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/* 113:    */   
/* 114:    */   public void guardar(GuiaRemision guiaRemision)
/* 115:    */     throws ExcepcionAS2Identification
/* 116:    */   {
/* 117:131 */     if ((guiaRemision.getEmpresa() == null) && (guiaRemision.getHojaRutaTransportista() == null) && 
/* 118:132 */       (guiaRemision.getDespachoCliente() != null) && (guiaRemision.getDespachoCliente().getEmpresa() != null)) {
/* 119:133 */       guiaRemision.setEmpresa(guiaRemision.getDespachoCliente().getEmpresa());
/* 120:    */     }
/* 121:136 */     if ((guiaRemision.getDireccionEmpresa() == null) && (guiaRemision.getHojaRutaTransportista() == null) && 
/* 122:137 */       (guiaRemision.getDespachoCliente() != null) && (guiaRemision.getDespachoCliente().getDireccionEmpresa() != null)) {
/* 123:138 */       guiaRemision.setDireccionEmpresa(guiaRemision.getDespachoCliente().getDireccionEmpresa());
/* 124:    */     }
/* 125:142 */     if ((guiaRemision.getFacturaCliente() == null) && (guiaRemision.getHojaRutaTransportista() == null)) {
/* 126:144 */       if ((guiaRemision.getDespachoCliente() != null) && (guiaRemision.getDespachoCliente().getFacturaCliente() != null)) {
/* 127:145 */         guiaRemision.setFacturaCliente(guiaRemision.getDespachoCliente().getFacturaCliente());
/* 128:    */       }
/* 129:    */     }
/* 130:149 */     this.servicioSecuencia.actualizarSecuencia(guiaRemision.getDocumento().getSecuencia(), guiaRemision.getNumero());
/* 131:151 */     if ((guiaRemision.getDocumento() != null) && (guiaRemision.getDocumento().isIndicadorDocumentoElectronico()))
/* 132:    */     {
/* 133:153 */       int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(guiaRemision.getIdOrganizacion()).booleanValue() ? 2 : 1;
/* 134:154 */       guiaRemision.setAmbiente(ambiente);
/* 135:155 */       int tipoEmision = 1;
/* 136:156 */       guiaRemision.setIndicadorDocumentoElectronico(true);
/* 137:    */       
/* 138:    */ 
/* 139:159 */       guiaRemision.setTipoEmision(tipoEmision);
/* 140:161 */       if (guiaRemision.getIdSucursal() != 0)
/* 141:    */       {
/* 142:162 */         Sucursal sucursal = this.servicioSucursal.cargarDetalle(guiaRemision.getIdSucursal());
/* 143:163 */         guiaRemision.setDireccionSucursal(sucursal.getUbicacion().getDireccionCompleta());
/* 144:    */       }
/* 145:165 */       if (guiaRemision.getIdOrganizacion() != 0)
/* 146:    */       {
/* 147:166 */         String dirMatriz = "";
/* 148:    */         try
/* 149:    */         {
/* 150:168 */           dirMatriz = this.servicioOrganizacion.obtenerDireccionMatriz(guiaRemision.getIdOrganizacion());
/* 151:    */         }
/* 152:    */         catch (Exception e)
/* 153:    */         {
/* 154:170 */           dirMatriz = "N/A";
/* 155:    */         }
/* 156:172 */         guiaRemision.setDireccionMatriz(dirMatriz);
/* 157:    */       }
/* 158:    */     }
/* 159:179 */     TipoIdentificacion tipoIdentificacion = this.servicioTipoIdentificacion.buscarPorId(Integer.valueOf(guiaRemision.getTipoIdentificacionTransportista().getId()));
/* 160:180 */     ValidarIdentificacion.validarIdentificacion(tipoIdentificacion.isIndicadorValidarIdentificacion(), guiaRemision.getLicencia());
/* 161:182 */     if ((guiaRemision.getVehiculo() != null) && (guiaRemision.getVehiculo().getTransportista() != null) && (guiaRemision.getEmail() != null) && 
/* 162:183 */       (!guiaRemision.getEmail().isEmpty()))
/* 163:    */     {
/* 164:186 */       guiaRemision.getVehiculo().getTransportista().setEmail(guiaRemision.getEmailTransportista());
/* 165:187 */       this.servicioTransportista.guardar(guiaRemision.getVehiculo().getTransportista());
/* 166:    */     }
/* 167:189 */     if ((guiaRemision.getConductor() != null) && (!guiaRemision.getConductor().isEmpty()) && (guiaRemision.getLicencia() != null) && 
/* 168:190 */       (!guiaRemision.getLicencia().isEmpty()) && (guiaRemision.getTipoIdentificacionTransportista() != null))
/* 169:    */     {
/* 170:191 */       ValidarIdentificacion.validarIdentificacion(guiaRemision.getTipoIdentificacionTransportista().isIndicadorValidarIdentificacion(), guiaRemision
/* 171:192 */         .getLicencia());
/* 172:193 */       if ((guiaRemision.getVehiculo() != null) && (guiaRemision.getVehiculo().getTransportista() == null))
/* 173:    */       {
/* 174:194 */         Transportista transportista = new Transportista();
/* 175:195 */         transportista.setIdOrganizacion(guiaRemision.getIdOrganizacion());
/* 176:196 */         transportista.setIdSucursal(guiaRemision.getIdSucursal());
/* 177:197 */         transportista.setActivo(true);
/* 178:198 */         transportista.setTipoIdentificacion(guiaRemision.getTipoIdentificacionTransportista());
/* 179:199 */         transportista.setIdentificacion(guiaRemision.getLicencia());
/* 180:200 */         transportista.setCodigo(guiaRemision.getLicencia());
/* 181:201 */         transportista.setNombre(guiaRemision.getConductor());
/* 182:202 */         this.servicioTransportista.guardar(transportista);
/* 183:203 */         guiaRemision.getVehiculo().setTransportista(transportista);
/* 184:204 */         this.servicioVehiculo.guardar(guiaRemision.getVehiculo());
/* 185:    */       }
/* 186:    */     }
/* 187:208 */     String email = "";
/* 188:210 */     if ((guiaRemision.getEmailTransportista() != null) && (!guiaRemision.getEmailTransportista().isEmpty())) {
/* 189:211 */       email = guiaRemision.getEmailTransportista();
/* 190:    */     }
/* 191:213 */     if ((guiaRemision.getEmailCliente() != null) && (!guiaRemision.getEmailCliente().isEmpty()))
/* 192:    */     {
/* 193:214 */       if (!email.equals("")) {
/* 194:215 */         email = email + ";";
/* 195:    */       }
/* 196:217 */       email = email + guiaRemision.getEmailCliente();
/* 197:    */     }
/* 198:220 */     guiaRemision.setEmail(email);
/* 199:    */     
/* 200:222 */     this.guiaRemisionDao.guardar(guiaRemision);
/* 201:223 */     this.guiaRemisionDao.flush();
/* 202:233 */     if (((guiaRemision.getEmpresa() != null) || (guiaRemision.getDespachoCliente() != null) || (guiaRemision.getTransferenciaBodega() != null) || 
/* 203:234 */       (guiaRemision.getIdHojaRuta() != 0) || (guiaRemision.getHojaRutaTransportista() != null)) && 
/* 204:235 */       (guiaRemision.getDocumento().isIndicadorDocumentoTributario()) && (guiaRemision.getDocumento().isIndicadorDocumentoElectronico()))
/* 205:    */     {
/* 206:    */       try
/* 207:    */       {
/* 208:237 */         guiaRemision = this.servicioGuiaRemisionSRIXML.generarClaveAcceso(null, guiaRemision, true);
/* 209:    */       }
/* 210:    */       catch (AS2Exception e)
/* 211:    */       {
/* 212:239 */         e.printStackTrace();
/* 213:    */       }
/* 214:242 */       boolean indicadorEnviarEmail = ParametrosSistema.isComprobantesElectronicosEnviarEmailGuardar(guiaRemision.getIdOrganizacion()).booleanValue();
/* 215:243 */       if (indicadorEnviarEmail) {
/* 216:245 */         if (guiaRemision.getAmbiente() == 2) {
/* 217:    */           try
/* 218:    */           {
/* 219:247 */             enviarMail(guiaRemision, guiaRemision.getDocumentoElectronico(), null);
/* 220:    */           }
/* 221:    */           catch (Exception e)
/* 222:    */           {
/* 223:249 */             e.printStackTrace();
/* 224:    */             
/* 225:251 */             System.out.println("Error en la generacion del reporte y enviar por mail al cliente (Facturacion electronica)" + e
/* 226:252 */               .getMessage());
/* 227:    */           }
/* 228:    */         }
/* 229:    */       }
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void actualizaGuiaRemisionSRI(int idGuiaRemision, Estado estadoGuiaRemision, EstadoDocumentoElectronico estadoSRI, Date fechaAutorizacion, String numeroAutorizacion, String mensajeSRI)
/* 234:    */   {
/* 235:263 */     this.guiaRemisionDao.actualizaGuiaRemision(idGuiaRemision, estadoGuiaRemision, estadoSRI, fechaAutorizacion, numeroAutorizacion, mensajeSRI);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void eliminar(GuiaRemision guiaRemision)
/* 239:    */   {
/* 240:273 */     this.guiaRemisionDao.eliminar(guiaRemision);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public GuiaRemision buscarPorId(Integer id)
/* 244:    */   {
/* 245:284 */     return (GuiaRemision)this.guiaRemisionDao.buscarPorId(id);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public GuiaRemision buscarPorDespacho(int idDespachoCliente)
/* 249:    */   {
/* 250:294 */     return this.guiaRemisionDao.buscarPorDespacho(idDespachoCliente);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public GuiaRemision buscarPorTransferenciaBodega(int idTransferenciaBodega)
/* 254:    */   {
/* 255:299 */     return this.guiaRemisionDao.buscarPorTransferenciaBodega(idTransferenciaBodega);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public GuiaRemision buscarPorHojaRutaTransportista(int idHojaRutaTransportista)
/* 259:    */   {
/* 260:303 */     return this.guiaRemisionDao.buscarPorHojaRutaTransportista(idHojaRutaTransportista);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public GuiaRemision cargarSecuencia(GuiaRemision guiaRemision, PuntoDeVenta puntoVenta)
/* 264:    */     throws ExcepcionAS2
/* 265:    */   {
/* 266:308 */     String numero = "";
/* 267:310 */     if (puntoVenta != null) {
/* 268:311 */       this.servicioDocumento.cargarSecuencia(guiaRemision.getDocumento(), puntoVenta, guiaRemision.getFecha());
/* 269:    */     }
/* 270:313 */     if ((guiaRemision.getId() == 0) || (guiaRemision.getEstado().equals(Estado.PROCESADO)) || (guiaRemision.getEstado().equals(Estado.APROBADO)) || 
/* 271:314 */       (guiaRemision.getEstado().equals(Estado.CONTABILIZADO)))
/* 272:    */     {
/* 273:315 */       numero = this.servicioSecuencia.obtenerSecuencia(guiaRemision.getDocumento().getSecuencia(), guiaRemision.getFecha());
/* 274:316 */       guiaRemision.setNumero(numero);
/* 275:    */     }
/* 276:319 */     return guiaRemision;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public GuiaRemision cargarDetalle(int idGuiaRemision)
/* 280:    */   {
/* 281:324 */     return this.guiaRemisionDao.cargarDetalle(idGuiaRemision);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public List<GuiaRemision> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean ordenar, Map<String, String> filters)
/* 285:    */   {
/* 286:329 */     return this.guiaRemisionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public int contarPorCriterio(Map<String, String> filters)
/* 290:    */   {
/* 291:334 */     return this.guiaRemisionDao.contarPorCriterio(filters);
/* 292:    */   }
/* 293:    */   
/* 294:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 295:    */   public List<GuiaRemision> migracionGuiaRemision(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial)
/* 296:    */     throws ExcepcionAS2, AS2Exception
/* 297:    */   {
/* 298:345 */     HashMap<String, Documento> hmDocumento = new HashMap();
/* 299:346 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/* 300:347 */     HashMap<String, GuiaRemision> hmGuiaRemision = new HashMap();
/* 301:348 */     HashMap<String, Ciudad> hmCiudad = new HashMap();
/* 302:349 */     HashMap<String, Ciudad> hmCiudadDestino = new HashMap();
/* 303:350 */     HashMap<String, Vehiculo> hmVehiculo = new HashMap();
/* 304:351 */     HashMap<String, Transportista> hmTransportista = new HashMap();
/* 305:352 */     HashMap<String, DireccionEmpresa> hmDireccionEmpresa = new HashMap();
/* 306:353 */     HashMap<String, FacturaCliente> hmFacturaCliente = new HashMap();
/* 307:354 */     HashMap<String, Producto> hmProducto = new HashMap();
/* 308:    */     
/* 309:356 */     GuiaRemision guiaRemision = new GuiaRemision();
/* 310:    */     
/* 311:358 */     List<GuiaRemision> listaGuiaRemision = new ArrayList();
/* 312:359 */     AS2Exception excepcionGuiaRemision = null;
/* 313:    */     
/* 314:361 */     int filaActual = filaInicial;
/* 315:362 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 316:363 */     int columnaErronea = 0;
/* 317:    */     try
/* 318:    */     {
/* 319:367 */       datos = FuncionesUtiles.leerExcelFinal(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/* 320:    */     }
/* 321:    */     catch (IOException ioe)
/* 322:    */     {
/* 323:    */       HSSFCell[][] datos;
/* 324:369 */       throw new ExcepcionAS2(ioe);
/* 325:    */     }
/* 326:    */     HSSFCell[][] datos;
/* 327:372 */     for (HSSFCell[] fila : datos)
/* 328:    */     {
/* 329:    */       try
/* 330:    */       {
/* 331:374 */         filaErronea = fila;
/* 332:375 */         filaActual++;
/* 333:    */         
/* 334:    */ 
/* 335:378 */         String nombreDocumento = fila[(columnaErronea = 0)].getStringCellValue().trim();
/* 336:    */         
/* 337:380 */         String numero = fila[(columnaErronea = 1)].getStringCellValue().trim();
/* 338:381 */         String establecimiento = numero.split("-")[0];
/* 339:382 */         String puntoEmision = numero.split("-")[1];
/* 340:383 */         String secuencia = numero.split("-")[2];
/* 341:    */         
/* 342:385 */         Date fecha = fila[(columnaErronea = 2)].getDateCellValue();
/* 343:386 */         Date fechaVigencia = fila[(columnaErronea = 3)].getDateCellValue();
/* 344:387 */         String nombreCiudadOrigen = fila[(columnaErronea = 4)].getStringCellValue().trim();
/* 345:388 */         String nombreCiudadDestino = fila[(columnaErronea = 5)].getStringCellValue().trim();
/* 346:389 */         String placaVehiculo = fila[(columnaErronea = 6)].getStringCellValue().trim();
/* 347:390 */         String conductor = fila[(columnaErronea = 7)].getStringCellValue().trim();
/* 348:391 */         String identificacionConductor = fila[(columnaErronea = 8)].getStringCellValue().trim();
/* 349:392 */         BigDecimal tarifa = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 9)].getNumericCellValue()), 2);
/* 350:393 */         String correoTransportista = fila[(columnaErronea = 10)].getStringCellValue().trim();
/* 351:394 */         String identificacion = fila[(columnaErronea = 11)].getStringCellValue().trim();
/* 352:395 */         String emailCliente = fila[(columnaErronea = 12)].getStringCellValue().trim();
/* 353:396 */         String numeroFactura = fila[(columnaErronea = 13)] != null ? fila[(columnaErronea = 13)].getStringCellValue().trim() : null;
/* 354:397 */         String descripcion = fila[(columnaErronea = 14)] != null ? fila[(columnaErronea = 14)].getStringCellValue().trim() : "";
/* 355:398 */         String codigoProducto = fila[(columnaErronea = 15)].getStringCellValue().trim();
/* 356:399 */         BigDecimal cantidad = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 16)].getNumericCellValue()), 2);
/* 357:    */         
/* 358:401 */         columnaErronea = -1;
/* 359:    */         
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:406 */         StringBuilder clave = new StringBuilder();
/* 364:407 */         clave.append(numero);
/* 365:    */         
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:412 */         Empresa empresa = (Empresa)hmEmpresa.get(identificacion);
/* 370:413 */         if (empresa == null)
/* 371:    */         {
/* 372:414 */           Map<String, String> filters = new HashMap();
/* 373:415 */           filters.put("identificacion", identificacion);
/* 374:416 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 375:417 */           empresa = this.servicioEmpresa.bucarEmpresaPorIdentificacion(filters);
/* 376:418 */           empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 377:419 */           hmEmpresa.put(identificacion, empresa);
/* 378:    */         }
/* 379:425 */         DireccionEmpresa direccionEmpresa = (DireccionEmpresa)hmDireccionEmpresa.get(identificacion);
/* 380:426 */         if (direccionEmpresa == null)
/* 381:    */         {
/* 382:427 */           direccionEmpresa = this.direccionEmpresaDao.buscarPorEmpresa(empresa);
/* 383:428 */           hmDireccionEmpresa.put(identificacion, direccionEmpresa);
/* 384:    */         }
/* 385:434 */         Documento documento = (Documento)hmDocumento.get(nombreDocumento);
/* 386:435 */         if (documento == null)
/* 387:    */         {
/* 388:436 */           HashMap<String, String> filters = new HashMap();
/* 389:437 */           filters.put("nombre", nombreDocumento);
/* 390:438 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 391:439 */           documento = (Documento)this.servicioDocumento.obtenerListaCombo("", false, filters).get(0);
/* 392:    */           
/* 393:441 */           hmDocumento.put(nombreDocumento, documento);
/* 394:    */         }
/* 395:447 */         Ciudad ciudadOrigen = (Ciudad)hmCiudad.get(nombreCiudadOrigen);
/* 396:448 */         if (ciudadOrigen == null)
/* 397:    */         {
/* 398:449 */           HashMap<String, String> filters = new HashMap();
/* 399:450 */           filters.put("nombre", nombreCiudadOrigen);
/* 400:451 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 401:452 */           ciudadOrigen = (Ciudad)this.servicioCiudad.obtenerListaCombo("", false, filters).get(0);
/* 402:453 */           hmCiudad.put(nombreCiudadOrigen, ciudadOrigen);
/* 403:    */         }
/* 404:459 */         Ciudad ciudadDestino = (Ciudad)hmCiudadDestino.get(nombreCiudadDestino);
/* 405:460 */         if (ciudadDestino == null)
/* 406:    */         {
/* 407:461 */           HashMap<String, String> filters = new HashMap();
/* 408:462 */           filters.put("nombre", nombreCiudadDestino);
/* 409:463 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 410:464 */           ciudadDestino = (Ciudad)this.servicioCiudad.obtenerListaCombo("", false, filters).get(0);
/* 411:465 */           hmCiudad.put(nombreCiudadDestino, ciudadDestino);
/* 412:    */         }
/* 413:471 */         Vehiculo vehiculo = (Vehiculo)hmVehiculo.get(placaVehiculo);
/* 414:472 */         if (vehiculo == null)
/* 415:    */         {
/* 416:473 */           HashMap<String, String> filters = new HashMap();
/* 417:474 */           filters.put("placa", placaVehiculo);
/* 418:475 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 419:476 */           vehiculo = (Vehiculo)this.servicioVehiculo.obtenerListaCombo("", false, filters).get(0);
/* 420:477 */           hmVehiculo.put(placaVehiculo, vehiculo);
/* 421:    */         }
/* 422:483 */         Transportista transportista = (Transportista)hmTransportista.get(identificacionConductor);
/* 423:484 */         if (transportista == null)
/* 424:    */         {
/* 425:485 */           HashMap<String, String> filters = new HashMap();
/* 426:486 */           filters.put("identificacion", identificacionConductor);
/* 427:487 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 428:488 */           transportista = (Transportista)this.servicioTransportista.obtenerListaCombo("", false, filters).get(0);
/* 429:489 */           hmTransportista.put(identificacionConductor, transportista);
/* 430:    */         }
/* 431:495 */         FacturaCliente facturaCliente = (FacturaCliente)hmFacturaCliente.get(numeroFactura);
/* 432:496 */         if ((facturaCliente == null) && (numeroFactura != null) && (!numeroFactura.trim().isEmpty()))
/* 433:    */         {
/* 434:497 */           HashMap<String, String> filters = new HashMap();
/* 435:498 */           filters.put("numero", numeroFactura);
/* 436:499 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 437:500 */           facturaCliente = (FacturaCliente)this.servicioFacturaCliente.obtenerListaCombo("", false, filters).get(0);
/* 438:501 */           facturaCliente = this.servicioFacturaCliente.cargarDetalle(facturaCliente.getIdFacturaCliente());
/* 439:502 */           hmFacturaCliente.put(numeroFactura, facturaCliente);
/* 440:    */         }
/* 441:508 */         guiaRemision = (GuiaRemision)hmGuiaRemision.get(clave.toString());
/* 442:509 */         if (guiaRemision == null)
/* 443:    */         {
/* 444:510 */           guiaRemision = new GuiaRemision();
/* 445:511 */           guiaRemision.setIdOrganizacion(idOrganizacion);
/* 446:512 */           guiaRemision.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 447:513 */           guiaRemision.setDocumento(documento);
/* 448:514 */           guiaRemision.setNumero(numero);
/* 449:515 */           guiaRemision.setFecha(fecha);
/* 450:516 */           guiaRemision.setFechaVigencia(fechaVigencia);
/* 451:517 */           guiaRemision.setCiudadOrigen(ciudadOrigen);
/* 452:518 */           guiaRemision.setCiudadDestino(ciudadDestino);
/* 453:519 */           guiaRemision.setVehiculo(vehiculo);
/* 454:520 */           guiaRemision.setPlaca(placaVehiculo);
/* 455:521 */           guiaRemision.setConductor(conductor);
/* 456:522 */           guiaRemision.setTipoIdentificacionTransportista(transportista.getTipoIdentificacion());
/* 457:523 */           guiaRemision.setTarifa(tarifa);
/* 458:524 */           guiaRemision.setEmailTransportista(transportista.getEmail());
/* 459:525 */           guiaRemision.setEmpresa(empresa);
/* 460:526 */           guiaRemision.setDireccionEmpresa(direccionEmpresa);
/* 461:527 */           guiaRemision.setEmailCliente(emailCliente);
/* 462:528 */           if (facturaCliente != null) {
/* 463:529 */             guiaRemision.setFacturaCliente(facturaCliente);
/* 464:    */           } else {
/* 465:531 */             guiaRemision.setFacturaCliente(null);
/* 466:    */           }
/* 467:532 */           guiaRemision.setLicencia(identificacionConductor);
/* 468:533 */           guiaRemision.setDescripcion(descripcion);
/* 469:534 */           if (guiaRemision.getDocumento().isIndicadorDocumentoTributario()) {
/* 470:535 */             this.servicioDocumento.cargarSecuencia(guiaRemision.getDocumento(), AppUtil.getPuntoDeVenta(), guiaRemision.getFecha());
/* 471:    */           }
/* 472:    */         }
/* 473:542 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 474:543 */         if (producto == null)
/* 475:    */         {
/* 476:544 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 477:545 */           hmProducto.put(codigoProducto, producto);
/* 478:    */         }
/* 479:551 */         DetalleGuiaRemision detalleGuiaRemision = new DetalleGuiaRemision();
/* 480:552 */         detalleGuiaRemision.setGuiaRemision(guiaRemision);
/* 481:553 */         detalleGuiaRemision.setCantidad(cantidad);
/* 482:554 */         detalleGuiaRemision.setProducto(producto);
/* 483:    */         
/* 484:556 */         guiaRemision.getListaDetalleGuiaRemision().add(detalleGuiaRemision);
/* 485:    */         
/* 486:558 */         hmGuiaRemision.put(clave.toString(), guiaRemision);
/* 487:    */       }
/* 488:    */       catch (IllegalStateException e)
/* 489:    */       {
/* 490:561 */         LOG.error("Error al migrar Guia Remision", e);
/* 491:562 */         if (excepcionGuiaRemision == null) {
/* 492:563 */           excepcionGuiaRemision = new AS2Exception();
/* 493:    */         }
/* 494:565 */         excepcionGuiaRemision.getMensajes().add(e.toString());
/* 495:    */       }
/* 496:    */       catch (IllegalArgumentException e)
/* 497:    */       {
/* 498:567 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 499:568 */         if (excepcionGuiaRemision == null) {
/* 500:569 */           excepcionGuiaRemision = new AS2Exception();
/* 501:    */         }
/* 502:571 */         excepcionGuiaRemision.getMensajes().add(e.toString());
/* 503:    */       }
/* 504:    */       catch (ExcepcionAS2 e)
/* 505:    */       {
/* 506:573 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 507:574 */         LOG.error("Error al migrar Guia Remision", e);
/* 508:575 */         if (excepcionGuiaRemision == null) {
/* 509:576 */           excepcionGuiaRemision = new AS2Exception();
/* 510:    */         }
/* 511:578 */         excepcionGuiaRemision.getCodigoMensajes().add(e.getCodigoExcepcion() + "*" + e.getMessage());
/* 512:    */       }
/* 513:    */       catch (Exception e)
/* 514:    */       {
/* 515:580 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 516:581 */         LOG.error("Error al migrar Guia Remision", e);
/* 517:582 */         if (excepcionGuiaRemision == null) {
/* 518:583 */           excepcionGuiaRemision = new AS2Exception();
/* 519:    */         }
/* 520:585 */         excepcionGuiaRemision.getMensajes().add(e.toString() + " Fila: " + filaActual + " Columna: " + columnaErronea);
/* 521:    */       }
/* 522:588 */       if ((excepcionGuiaRemision != null) && (
/* 523:589 */         (!excepcionGuiaRemision.getMensajes().isEmpty()) || (!excepcionGuiaRemision.getCodigoMensajes().isEmpty()))) {
/* 524:590 */         throw excepcionGuiaRemision;
/* 525:    */       }
/* 526:    */     }
/* 527:594 */     for (GuiaRemision gr : hmGuiaRemision.values()) {
/* 528:595 */       listaGuiaRemision.add(gr);
/* 529:    */     }
/* 530:598 */     return listaGuiaRemision;
/* 531:    */   }
/* 532:    */   
/* 533:    */   public GuiaRemision buscarPorFactura(int idFacturaCliente)
/* 534:    */   {
/* 535:604 */     return this.guiaRemisionDao.buscarPorFactura(idFacturaCliente);
/* 536:    */   }
/* 537:    */   
/* 538:    */   public void anular(GuiaRemision guiaRemision)
/* 539:    */   {
/* 540:609 */     if (!guiaRemision.getEstado().equals(Estado.ANULADO)) {
/* 541:611 */       this.comprobanteElectronicoPendienteSRIDao.eliminarComprobanteElectronicoPendienteSRI(null, null, guiaRemision);
/* 542:    */     }
/* 543:613 */     this.guiaRemisionDao.anular(guiaRemision);
/* 544:    */   }
/* 545:    */   
/* 546:    */   public void enviarMail(GuiaRemision guiaRemision, String emails)
/* 547:    */     throws ExcepcionAS2
/* 548:    */   {
/* 549:618 */     enviarMail(guiaRemision, null, emails);
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void enviarMail(GuiaRemision guiaRemision, DocumentoElectronico documento, String emails)
/* 553:    */     throws ExcepcionAS2
/* 554:    */   {
/* 555:    */     try
/* 556:    */     {
/* 557:625 */       guiaRemision = cargarDetalle(guiaRemision.getId());
/* 558:626 */       if (documento == null)
/* 559:    */       {
/* 560:627 */         String version = "1.0.0";
/* 561:    */         
/* 562:629 */         int ambiente = guiaRemision.getAmbiente();
/* 563:630 */         int tipoEmision = guiaRemision.getTipoEmision();
/* 564:631 */         TipoDocumentoElectronicoEnum tipoDocumento = TipoDocumentoElectronicoEnum.GUIA_REMISION;
/* 565:    */         
/* 566:633 */         Organizacion organizacion = null;
/* 567:634 */         int idOrganizacion = guiaRemision.getIdOrganizacion();
/* 568:635 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/* 569:636 */         documento = new DocumentoElectronico(organizacion, ambiente, tipoEmision, tipoDocumento, "1.0.0");
/* 570:637 */         documento.prepararParaEnvioEmail(guiaRemision, emails, guiaRemision.getDocumento().getReporte());
/* 571:    */       }
/* 572:639 */       documento.setNombreReporte(guiaRemision.getDocumento().getReporte());
/* 573:640 */       if (emails != null) {
/* 574:641 */         documento.setEmail(emails);
/* 575:    */       }
/* 576:645 */       int idDespachoCliente = 0;
/* 577:646 */       int idTransferenciaBodega = 0;
/* 578:647 */       if (guiaRemision.getDespachoCliente() != null) {
/* 579:648 */         idDespachoCliente = guiaRemision.getDespachoCliente().getId();
/* 580:    */       }
/* 581:650 */       if (guiaRemision.getTransferenciaBodega() != null) {
/* 582:651 */         idTransferenciaBodega = guiaRemision.getTransferenciaBodega().getId();
/* 583:    */       }
/* 584:653 */       List<Object[]> listaDatosReporte = this.reporteGuiaRemisionDao.getReporteGuiaRemision(idDespachoCliente, idTransferenciaBodega, guiaRemision
/* 585:654 */         .getId(), guiaRemision.getHojaRutaTransportista());
/* 586:    */       
/* 587:656 */       JRDataSource ds = new QueryResultDataSource(listaDatosReporte, ReporteGuiaRemisionBean.fields);
/* 588:657 */       documento.setDataSource(ds);
/* 589:    */       
/* 590:    */ 
/* 591:660 */       Sucursal sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(guiaRemision.getIdSucursal()));
/* 592:661 */       documento.setTelefonoSucursal(sucursal.getTelefono1().concat(sucursal.getTelefono2() != null ? "   " + sucursal.getTelefono2() : ""));
/* 593:    */       
/* 594:663 */       this.servicioDocumentoElectronico.enviarDocumentoPorEmail(documento, guiaRemision.getEmpresa());
/* 595:    */     }
/* 596:    */     catch (Exception e)
/* 597:    */     {
/* 598:665 */       e.printStackTrace();
/* 599:666 */       throw new ExcepcionAS2(e.getMessage());
/* 600:    */     }
/* 601:    */   }
/* 602:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioGuiaRemisionImpl
 * JD-Core Version:    0.7.0.1
 */