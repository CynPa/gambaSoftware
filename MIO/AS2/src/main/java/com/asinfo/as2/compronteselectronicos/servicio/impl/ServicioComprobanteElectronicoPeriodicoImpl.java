/*   1:    */ package com.asinfo.as2.compronteselectronicos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.ServicioComprobanteElectronicoPeriodico;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*   5:    */ import com.asinfo.as2.compronteselectronicos.ServicioFacturaClienteSRIXML;
/*   6:    */ import com.asinfo.as2.compronteselectronicos.ServicioFacturaProveedorSRIXML;
/*   7:    */ import com.asinfo.as2.compronteselectronicos.ServicioGuiaRemisionSRIXML;
/*   8:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*   9:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronicoAutorizacion;
/*  10:    */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*  11:    */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*  12:    */ import com.asinfo.as2.dao.CompraCajaChicaDao;
/*  13:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*  14:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*  15:    */ import com.asinfo.as2.dao.GuiaRemisionDao;
/*  16:    */ import com.asinfo.as2.dao.reportes.ventas.ReporteGuiaRemisionDao;
/*  17:    */ import com.asinfo.as2.dao.sri.ClaveAccesoPendientePublicarDao;
/*  18:    */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*  19:    */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*  20:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  21:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  22:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  23:    */ import com.asinfo.as2.entities.sri.ClaveAccesoPendientePublicar;
/*  24:    */ import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
/*  25:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  26:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  27:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  28:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  29:    */ import com.asinfo.as2.excepciones.ExcepcionAS2DocumentoElectronico;
/*  30:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  31:    */ import com.asinfo.as2.utils.ArchivoUtil;
/*  32:    */ import com.asinfo.as2.utils.EjbUtil;
/*  33:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  34:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  35:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  36:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  37:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  38:    */ import java.io.File;
/*  39:    */ import java.io.IOException;
/*  40:    */ import java.io.PrintStream;
/*  41:    */ import java.text.SimpleDateFormat;
/*  42:    */ import java.util.Base64;
/*  43:    */ import java.util.Base64.Encoder;
/*  44:    */ import java.util.Calendar;
/*  45:    */ import java.util.Date;
/*  46:    */ import java.util.Iterator;
/*  47:    */ import java.util.List;
/*  48:    */ import java.util.concurrent.TimeUnit;
/*  49:    */ import javax.annotation.Resource;
/*  50:    */ import javax.ejb.AccessTimeout;
/*  51:    */ import javax.ejb.EJB;
/*  52:    */ import javax.ejb.SessionContext;
/*  53:    */ import javax.ejb.Stateless;
/*  54:    */ import javax.ejb.TransactionAttribute;
/*  55:    */ import javax.ejb.TransactionAttributeType;
/*  56:    */ import javax.ejb.TransactionManagement;
/*  57:    */ import javax.ejb.TransactionManagementType;
/*  58:    */ 
/*  59:    */ @Stateless
/*  60:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  61:    */ public class ServicioComprobanteElectronicoPeriodicoImpl
/*  62:    */   implements ServicioComprobanteElectronicoPeriodico
/*  63:    */ {
/*  64:    */   @EJB
/*  65:    */   private ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  66:    */   @EJB
/*  67:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  68:    */   @EJB
/*  69:    */   private ServicioFacturaClienteSRIXML servicioFacturaClienteSRIXML;
/*  70:    */   @EJB
/*  71:    */   private ServicioFacturaProveedorSRIXML servicioFacturaProveedorSRIXML;
/*  72:    */   @EJB
/*  73:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  74:    */   @EJB
/*  75:    */   private FacturaProveedorDao facturaProveedorDao;
/*  76:    */   @EJB
/*  77:    */   private FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  78:    */   @EJB
/*  79:    */   private CompraCajaChicaDao compraCajaChicaDao;
/*  80:    */   @EJB
/*  81:    */   private ServicioDocumento servicioDocumento;
/*  82:    */   @EJB
/*  83:    */   private ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  84:    */   @EJB
/*  85:    */   private ServicioGuiaRemision servicioGuiaRemision;
/*  86:    */   @EJB
/*  87:    */   private ServicioGuiaRemisionSRIXML servicioGuiaRemisionSRIXML;
/*  88:    */   @EJB
/*  89:    */   private GuiaRemisionDao guiaRemisionDao;
/*  90:    */   @EJB
/*  91:    */   private FacturaClienteDao facturaClienteDao;
/*  92:    */   @EJB
/*  93:    */   private ClaveAccesoPendientePublicarDao claveAccesoPendientePublicarDao;
/*  94:    */   @EJB
/*  95:    */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  96:    */   @EJB
/*  97:    */   private ReporteGuiaRemisionDao reporteGuiaRemisionDao;
/*  98:    */   @Resource
/*  99:    */   protected SessionContext context;
/* 100:    */   
/* 101:    */   @AccessTimeout(unit=TimeUnit.MINUTES, value=3L)
/* 102:    */   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
/* 103:    */   public void sincronizarComprobantesSRI(int idOrganizacion, List<DocumentoBase> listaDocumentoBase)
/* 104:    */   {
/* 105:118 */     int cantidadDocumentosProcesar = ParametrosSistema.getCantidadComprobantesElectronicosSincronizacion(idOrganizacion).intValue();
/* 106:119 */     int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(idOrganizacion).booleanValue() ? 2 : 1;
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:123 */     List<ComprobanteElectronicoPendienteSRI> listaComprobantesPendientesEnviar = this.comprobanteElectronicoPendienteSRIDao.obtenerComprobantesPendientes(idOrganizacion, Integer.valueOf(ambiente), listaDocumentoBase, true, false, cantidadDocumentosProcesar);
/* 111:125 */     for (Iterator localIterator = listaComprobantesPendientesEnviar.iterator(); localIterator.hasNext();)
/* 112:    */     {
/* 113:125 */       comprobanteElectronicoPendienteSRI = (ComprobanteElectronicoPendienteSRI)localIterator.next();
/* 114:    */       try
/* 115:    */       {
/* 116:127 */         enviarComprobanteSRI(comprobanteElectronicoPendienteSRI);
/* 117:    */       }
/* 118:    */       catch (Exception e)
/* 119:    */       {
/* 120:129 */         e.printStackTrace();
/* 121:    */       }
/* 122:    */     }
/* 123:    */     ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI;
/* 124:136 */     Object listaComprobantesPendientesConsultar = this.comprobanteElectronicoPendienteSRIDao.obtenerComprobantesPendientes(idOrganizacion, Integer.valueOf(ambiente), listaDocumentoBase, false, true, cantidadDocumentosProcesar);
/* 125:138 */     for (ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI : (List)listaComprobantesPendientesConsultar) {
/* 126:    */       try
/* 127:    */       {
/* 128:140 */         comprobarAutorizacionComprobanteSRI(comprobanteElectronicoPendienteSRI, false);
/* 129:    */       }
/* 130:    */       catch (Exception e)
/* 131:    */       {
/* 132:142 */         e.printStackTrace();
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
/* 138:    */   public void enviarComprobanteSRI(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI)
/* 139:    */   {
/* 140:153 */     boolean aprobado = comprobarAutorizacionComprobanteSRI(comprobanteElectronicoPendienteSRI, true);
/* 141:154 */     if (!aprobado)
/* 142:    */     {
/* 143:155 */       String mensajeSRI = "";
/* 144:156 */       DocumentoElectronico documento = null;
/* 145:    */       try
/* 146:    */       {
/* 147:158 */         if ((comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA)) || 
/* 148:159 */           (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO)) || 
/* 149:160 */           (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_DEBITO))) {
/* 150:161 */           documento = this.servicioFacturaClienteSRIXML.autorizarFactura(comprobanteElectronicoPendienteSRI);
/* 151:162 */         } else if (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.GUIA_REMISION)) {
/* 152:163 */           documento = this.servicioGuiaRemisionSRIXML.autorizarGuiaRemision(comprobanteElectronicoPendienteSRI);
/* 153:164 */         } else if (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.RETENCION)) {
/* 154:165 */           documento = this.servicioFacturaProveedorSRIXML.autorizarFactura(comprobanteElectronicoPendienteSRI);
/* 155:    */         }
/* 156:168 */         mensajeSRI = documento.getMensajeSRI() != null ? documento.getMensajeSRI() : documento.getEstado().toString();
/* 157:    */       }
/* 158:    */       catch (AS2Exception e)
/* 159:    */       {
/* 160:    */         SimpleDateFormat sdf;
/* 161:    */         String mensaje;
/* 162:170 */         mensajeSRI = e.getMensaje();
/* 163:    */       }
/* 164:    */       catch (ExcepcionAS2Ventas e)
/* 165:    */       {
/* 166:    */         SimpleDateFormat sdf;
/* 167:    */         String mensaje;
/* 168:172 */         mensajeSRI = e.getMessage();
/* 169:    */       }
/* 170:    */       catch (Exception e)
/* 171:    */       {
/* 172:    */         SimpleDateFormat sdf;
/* 173:    */         String mensaje;
/* 174:174 */         mensajeSRI = e.getMessage();
/* 175:    */       }
/* 176:    */       finally
/* 177:    */       {
/* 178:    */         SimpleDateFormat sdf;
/* 179:    */         String mensaje;
/* 180:177 */         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
/* 181:178 */         mensajeSRI = sdf.format(new Date()) + " => " + mensajeSRI;
/* 182:179 */         String mensaje = mensajeSRI.length() > 5000 ? mensajeSRI.substring(0, 5000) : mensajeSRI;
/* 183:180 */         comprobanteElectronicoPendienteSRI.setMensajeSRI(mensaje);
/* 184:    */         
/* 185:182 */         actualizarComprobantePendiente(comprobanteElectronicoPendienteSRI, documento);
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
/* 191:    */   public boolean comprobarAutorizacionComprobanteSRI(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI, boolean indicadorSoloComprobar)
/* 192:    */   {
/* 193:191 */     String mensajeSRI = "";
/* 194:192 */     DocumentoElectronico documento = null;
/* 195:    */     try
/* 196:    */     {
/* 197:194 */       if ((comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA)) || 
/* 198:195 */         (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO)) || 
/* 199:196 */         (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_DEBITO))) {
/* 200:197 */         documento = this.servicioFacturaClienteSRIXML.comprobarAutorizacion(comprobanteElectronicoPendienteSRI);
/* 201:198 */       } else if (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.GUIA_REMISION)) {
/* 202:199 */         documento = this.servicioGuiaRemisionSRIXML.comprobarAutorizacion(comprobanteElectronicoPendienteSRI);
/* 203:200 */       } else if (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.RETENCION)) {
/* 204:201 */         documento = this.servicioFacturaProveedorSRIXML.comprobarAutorizacion(comprobanteElectronicoPendienteSRI);
/* 205:    */       }
/* 206:204 */       mensajeSRI = documento.getMensajeSRI() != null ? documento.getMensajeSRI() : documento.getEstado().toString();
/* 207:    */     }
/* 208:    */     catch (ExcepcionAS2DocumentoElectronico e)
/* 209:    */     {
/* 210:    */       SimpleDateFormat sdf;
/* 211:    */       String mensaje;
/* 212:206 */       mensajeSRI = e.getMessage();
/* 213:    */     }
/* 214:    */     catch (Exception e)
/* 215:    */     {
/* 216:    */       SimpleDateFormat sdf;
/* 217:    */       String mensaje;
/* 218:208 */       mensajeSRI = e.getMessage();
/* 219:    */     }
/* 220:    */     finally
/* 221:    */     {
/* 222:    */       SimpleDateFormat sdf;
/* 223:    */       String mensaje;
/* 224:211 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
/* 225:212 */       mensajeSRI = sdf.format(new Date()) + " => " + mensajeSRI;
/* 226:213 */       String mensaje = mensajeSRI.length() > 5000 ? mensajeSRI.substring(0, 5000) : mensajeSRI;
/* 227:214 */       comprobanteElectronicoPendienteSRI.setMensajeSRI(mensaje);
/* 228:216 */       if ((!indicadorSoloComprobar) || ((documento != null) && (EstadoDocumentoElectronico.AUTORIZADO.equals(documento.getEstado()))))
/* 229:    */       {
/* 230:217 */         actualizarComprobantePendiente(comprobanteElectronicoPendienteSRI, documento);
/* 231:218 */         return true;
/* 232:    */       }
/* 233:    */     }
/* 234:221 */     return false;
/* 235:    */   }
/* 236:    */   
/* 237:    */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/* 238:    */   private void actualizarComprobantePendiente(ComprobanteElectronicoPendienteSRI comprobanteElectronicoPendienteSRI, DocumentoElectronico documento)
/* 239:    */   {
/* 240:    */     try
/* 241:    */     {
/* 242:228 */       EstadoDocumentoElectronico estadoDocumentoElectronico = documento != null ? documento.getEstado() : null;
/* 243:229 */       int cantidadIntentos = comprobanteElectronicoPendienteSRI.getCantidadIntentos();
/* 244:230 */       cantidadIntentos++;
/* 245:231 */       Date fechaAutorizacion = null;
/* 246:232 */       String numeroAutorizacion = null;
/* 247:233 */       Estado estado = null;
/* 248:234 */       String mensajeSRI = comprobanteElectronicoPendienteSRI.getMensajeSRI();
/* 249:235 */       boolean indicadorAutorizao = false;
/* 250:236 */       if (EstadoDocumentoElectronico.AUTORIZADO.equals(estadoDocumentoElectronico))
/* 251:    */       {
/* 252:237 */         indicadorAutorizao = true;
/* 253:238 */         estado = Estado.PROCESADO;
/* 254:239 */         fechaAutorizacion = documento.getFechaAutorizacion();
/* 255:240 */         numeroAutorizacion = documento.getNumeroAutorizacion();
/* 256:    */       }
/* 257:241 */       else if (EstadoDocumentoElectronico.NO_AUTORIZADO.equals(estadoDocumentoElectronico))
/* 258:    */       {
/* 259:242 */         estado = Estado.RECHAZADO_SRI;
/* 260:243 */         cantidadIntentos = 0;
/* 261:244 */         comprobanteElectronicoPendienteSRI.setIndicadorRechazado(true);
/* 262:245 */         comprobanteElectronicoPendienteSRI.setIndicadorComprobarAutorizacion(false);
/* 263:246 */         comprobanteElectronicoPendienteSRI.setIndicadorNoEnviado(false);
/* 264:    */       }
/* 265:247 */       else if ((EstadoDocumentoElectronico.EMITIDO.equals(estadoDocumentoElectronico)) || 
/* 266:248 */         (EstadoDocumentoElectronico.ENVIADO.equals(estadoDocumentoElectronico)))
/* 267:    */       {
/* 268:249 */         estado = Estado.EN_ESPERA_CONTINGENCIA;
/* 269:250 */         comprobanteElectronicoPendienteSRI.setIndicadorRechazado(false);
/* 270:251 */         comprobanteElectronicoPendienteSRI.setIndicadorComprobarAutorizacion(false);
/* 271:252 */         comprobanteElectronicoPendienteSRI.setIndicadorNoEnviado(true);
/* 272:    */       }
/* 273:253 */       else if (EstadoDocumentoElectronico.RECIBIDA.equals(estadoDocumentoElectronico))
/* 274:    */       {
/* 275:254 */         estado = Estado.EN_ESPERA;
/* 276:255 */         comprobanteElectronicoPendienteSRI.setIndicadorRechazado(false);
/* 277:256 */         comprobanteElectronicoPendienteSRI.setIndicadorComprobarAutorizacion(true);
/* 278:257 */         comprobanteElectronicoPendienteSRI.setIndicadorNoEnviado(false);
/* 279:    */       }
/* 280:259 */       comprobanteElectronicoPendienteSRI.setCantidadIntentos(cantidadIntentos);
/* 281:260 */       comprobanteElectronicoPendienteSRI.setFechaUltimoIntento(new Date());
/* 282:262 */       if ((comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA)) || 
/* 283:263 */         (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO)) || 
/* 284:264 */         (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_DEBITO))) {
/* 285:266 */         this.servicioFacturaCliente.actualizaFacturaClienteSRI(comprobanteElectronicoPendienteSRI.getIdFacturaCliente().intValue(), estado, estadoDocumentoElectronico, fechaAutorizacion, numeroAutorizacion, mensajeSRI);
/* 286:268 */       } else if (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.GUIA_REMISION)) {
/* 287:269 */         this.servicioGuiaRemision.actualizaGuiaRemisionSRI(comprobanteElectronicoPendienteSRI.getIdGuiaRemision().intValue(), estado, estadoDocumentoElectronico, fechaAutorizacion, numeroAutorizacion, mensajeSRI);
/* 288:271 */       } else if (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.RETENCION)) {
/* 289:272 */         this.servicioFacturaProveedorSRI.actualizaFacturaProveedorSRI(comprobanteElectronicoPendienteSRI.getIdFacturaProveedorSRI().intValue(), estado, estadoDocumentoElectronico, fechaAutorizacion, numeroAutorizacion, mensajeSRI);
/* 290:    */       }
/* 291:276 */       if (indicadorAutorizao)
/* 292:    */       {
/* 293:277 */         boolean indicadorEnviarEmail = !ParametrosSistema.isComprobantesElectronicosEnviarEmailGuardar(comprobanteElectronicoPendienteSRI
/* 294:278 */           .getIdOrganizacion()).booleanValue();
/* 295:279 */         if (indicadorEnviarEmail) {
/* 296:281 */           if (documento.getAmbiente() == 2) {
/* 297:    */             try
/* 298:    */             {
/* 299:283 */               if ((comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.FACTURA)) || 
/* 300:284 */                 (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_CREDITO)) || 
/* 301:285 */                 (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.NOTA_DEBITO)))
/* 302:    */               {
/* 303:286 */                 FacturaCliente facturaCliente = this.facturaClienteDao.buscarPorId(comprobanteElectronicoPendienteSRI
/* 304:287 */                   .getIdFacturaCliente());
/* 305:288 */                 this.servicioFacturaCliente.enviarMail(facturaCliente, documento, null);
/* 306:    */               }
/* 307:289 */               else if (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.GUIA_REMISION))
/* 308:    */               {
/* 309:290 */                 GuiaRemision guiaRemision = this.servicioGuiaRemision.buscarPorId(comprobanteElectronicoPendienteSRI.getIdGuiaRemision());
/* 310:291 */                 this.servicioGuiaRemision.enviarMail(guiaRemision, documento, null);
/* 311:    */               }
/* 312:292 */               else if (comprobanteElectronicoPendienteSRI.getTipoDocumento().equals(TipoDocumentoElectronicoEnum.RETENCION))
/* 313:    */               {
/* 314:293 */                 FacturaProveedorSRI facturaProveedorSRI = this.servicioFacturaProveedorSRI.buscarPorId(comprobanteElectronicoPendienteSRI
/* 315:294 */                   .getIdFacturaProveedorSRI());
/* 316:295 */                 if (!facturaProveedorSRI.isIndicadorRetencionAsumida()) {
/* 317:296 */                   this.servicioFacturaProveedorSRI.enviarMail(facturaProveedorSRI, documento, null);
/* 318:    */                 }
/* 319:    */               }
/* 320:    */             }
/* 321:    */             catch (Exception e)
/* 322:    */             {
/* 323:300 */               e.printStackTrace();
/* 324:    */               
/* 325:    */ 
/* 326:303 */               System.out.println("Error en la generacion del reporte y enviar por mail al cliente (Facturacion electronica)" + e
/* 327:304 */                 .getMessage());
/* 328:    */             }
/* 329:    */           }
/* 330:    */         }
/* 331:309 */         ClaveAccesoPendientePublicar claveAccesoPendientePublicar = new ClaveAccesoPendientePublicar();
/* 332:310 */         claveAccesoPendientePublicar.setIdOrganizacion(comprobanteElectronicoPendienteSRI.getIdOrganizacion());
/* 333:311 */         claveAccesoPendientePublicar.setCantidadIntentos(0);
/* 334:312 */         claveAccesoPendientePublicar.setClaveAcceso(comprobanteElectronicoPendienteSRI.getClaveAcceso());
/* 335:313 */         claveAccesoPendientePublicar.setEmails(comprobanteElectronicoPendienteSRI.getEmails());
/* 336:314 */         claveAccesoPendientePublicar.setPathXML(documento.getPathArchivoAutorizado());
/* 337:315 */         claveAccesoPendientePublicar.setIndicadorPublicado(false);
/* 338:316 */         this.claveAccesoPendientePublicarDao.guardar(claveAccesoPendientePublicar);
/* 339:    */         
/* 340:318 */         this.comprobanteElectronicoPendienteSRIDao.eliminar(comprobanteElectronicoPendienteSRI);
/* 341:    */       }
/* 342:    */       else
/* 343:    */       {
/* 344:320 */         this.comprobanteElectronicoPendienteSRIDao.guardar(comprobanteElectronicoPendienteSRI);
/* 345:    */       }
/* 346:    */     }
/* 347:    */     catch (Exception e)
/* 348:    */     {
/* 349:323 */       e.printStackTrace();
/* 350:324 */       this.context.setRollbackOnly();
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
/* 355:    */   public void publicarClaveAccesoPendiente(int idOrganizacion)
/* 356:    */   {
/* 357:332 */     boolean indicadorPublicarFacturae = ParametrosSistema.getPublicarComprobantesFacturae(idOrganizacion).booleanValue();
/* 358:333 */     boolean indicadorProduccion = ParametrosSistema.getAmbienteFacturacionElectronica(idOrganizacion).booleanValue();
/* 359:334 */     if ((indicadorPublicarFacturae) && (indicadorProduccion))
/* 360:    */     {
/* 361:335 */       String url = null;
/* 362:    */       try
/* 363:    */       {
/* 364:337 */         url = EjbUtil.obtenerValorWebService("WSFacturaE");
/* 365:    */       }
/* 366:    */       catch (Exception e)
/* 367:    */       {
/* 368:339 */         url = null;
/* 369:    */       }
/* 370:341 */       if ((url != null) && (!url.isEmpty()))
/* 371:    */       {
/* 372:342 */         int cantidadDocumentosProcesar = ParametrosSistema.getCantidadComprobantesElectronicosSincronizacion(idOrganizacion).intValue();
/* 373:    */         
/* 374:344 */         List<ClaveAccesoPendientePublicar> listaclaveAccesoPendientePublicar = this.claveAccesoPendientePublicarDao.obtenerClaveAccesoPendientePublicar(idOrganizacion, cantidadDocumentosProcesar * 2);
/* 375:345 */         for (ClaveAccesoPendientePublicar claveAccesoPendientePublicar : listaclaveAccesoPendientePublicar)
/* 376:    */         {
/* 377:346 */           String emails = claveAccesoPendientePublicar.getEmails();
/* 378:347 */           if (emails == null) {
/* 379:348 */             emails = "";
/* 380:    */           }
/* 381:350 */           String xml = null;
/* 382:    */           try
/* 383:    */           {
/* 384:353 */             File xmlFile = new File(claveAccesoPendientePublicar.getPathXML());
/* 385:354 */             if (xmlFile.exists())
/* 386:    */             {
/* 387:355 */               byte[] xmlByte = ArchivoUtil.archivoToByte(xmlFile);
/* 388:356 */               byte[] bytesEncoded = Base64.getEncoder().encode(xmlByte);
/* 389:357 */               xml = new String(bytesEncoded);
/* 390:    */             }
/* 391:    */           }
/* 392:    */           catch (IOException e)
/* 393:    */           {
/* 394:360 */             e.printStackTrace();
/* 395:    */           }
/* 396:364 */           boolean publicado = DocumentoElectronicoAutorizacion.ingresarComprobanteFacturaE(claveAccesoPendientePublicar.getClaveAcceso(), xml, emails);
/* 397:366 */           if (publicado) {
/* 398:367 */             System.out.println("Publicada clave de acceso: " + claveAccesoPendientePublicar.getClaveAcceso());
/* 399:    */           }
/* 400:369 */           claveAccesoPendientePublicar.setIndicadorPublicado(publicado);
/* 401:370 */           claveAccesoPendientePublicar.setCantidadIntentos(claveAccesoPendientePublicar.getCantidadIntentos() + 1);
/* 402:371 */           claveAccesoPendientePublicar.setFechaUltimoIntento(Calendar.getInstance().getTime());
/* 403:372 */           this.claveAccesoPendientePublicarDao.guardar(claveAccesoPendientePublicar);
/* 404:    */         }
/* 405:    */       }
/* 406:    */       else
/* 407:    */       {
/* 408:375 */         System.out.println("No hay URL de facturae configurada");
/* 409:    */       }
/* 410:    */     }
/* 411:    */   }
/* 412:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.servicio.impl.ServicioComprobanteElectronicoPeriodicoImpl
 * JD-Core Version:    0.7.0.1
 */