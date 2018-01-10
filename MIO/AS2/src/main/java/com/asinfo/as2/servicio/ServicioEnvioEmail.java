/*   1:    */ package com.asinfo.as2.servicio;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.entities.MensajeEmail;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.Ubicacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.util.RutaArchivo;
/*  13:    */ import com.asinfo.as2.utils.EjbUtil;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  16:    */ import com.asinfo.as2.utils.reportes.ReportConfigUtil;
/*  17:    */ import java.io.File;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.io.PrintStream;
/*  20:    */ import java.io.Serializable;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import java.util.concurrent.ConcurrentHashMap;
/*  26:    */ import java.util.concurrent.ConcurrentMap;
/*  27:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  28:    */ import javax.annotation.Resource;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.ejb.LocalBean;
/*  31:    */ import javax.ejb.Stateless;
/*  32:    */ import javax.inject.Inject;
/*  33:    */ import javax.jms.Destination;
/*  34:    */ import javax.jms.JMSConnectionFactory;
/*  35:    */ import javax.jms.JMSContext;
/*  36:    */ import javax.jms.JMSException;
/*  37:    */ import javax.jms.JMSProducer;
/*  38:    */ import javax.jms.MapMessage;
/*  39:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  40:    */ import net.sf.jasperreports.engine.JRException;
/*  41:    */ import net.sf.jasperreports.engine.JasperExportManager;
/*  42:    */ import net.sf.jasperreports.engine.JasperPrint;
/*  43:    */ 
/*  44:    */ @LocalBean
/*  45:    */ @Stateless
/*  46:    */ public class ServicioEnvioEmail
/*  47:    */ {
/*  48:    */   @Inject
/*  49:    */   @JMSConnectionFactory("java:/ConnectionFactory")
/*  50:    */   private JMSContext context;
/*  51:    */   @Resource(name="java:/jms/queue/AS2MailQueue")
/*  52:    */   private Destination destination;
/*  53:    */   @EJB
/*  54:    */   private ServicioSucursal servicioSucursal;
/*  55: 73 */   public static Map<Integer, DatosSMTP> hmDatosSMTP = new HashMap();
/*  56: 74 */   public static ConcurrentMap<Integer, MensajeEmail> cmMensajeEmail = new ConcurrentHashMap();
/*  57: 75 */   public static AtomicInteger aiMensajeEmail = new AtomicInteger(0);
/*  58:    */   
/*  59:    */   public void cargarDatosSMTP(Organizacion organizacion)
/*  60:    */   {
/*  61: 79 */     if (organizacion.getOrganizacionConfiguracion() != null)
/*  62:    */     {
/*  63: 81 */       DatosSMTP datosSMTP = new DatosSMTP();
/*  64: 82 */       datosSMTP.setAutenticacion(Boolean.valueOf(true));
/*  65: 83 */       datosSMTP.setSmtpFrom(organizacion.getOrganizacionConfiguracion().getSmtpEnviarDe());
/*  66: 84 */       datosSMTP.setSmtpHost(organizacion.getOrganizacionConfiguracion().getSmtpServidor());
/*  67: 85 */       datosSMTP.setSmtpPort(organizacion.getOrganizacionConfiguracion().getSmtpPuerto());
/*  68: 86 */       datosSMTP.setAutenticacion(organizacion.getOrganizacionConfiguracion().getSmtpRequiereAutenticacion());
/*  69: 87 */       datosSMTP.setNombreUsuario(organizacion.getOrganizacionConfiguracion().getSmtpUsuario());
/*  70: 88 */       datosSMTP.setClave(organizacion.getOrganizacionConfiguracion().getSmtpClave());
/*  71: 89 */       datosSMTP.setAutenticacionSSL(organizacion.getOrganizacionConfiguracion().getAutenticacionSSL());
/*  72: 90 */       datosSMTP.setSmtpCC(organizacion.getOrganizacionConfiguracion().getSmtpEnviarCopiaA());
/*  73: 91 */       hmDatosSMTP.put(Integer.valueOf(organizacion.getIdOrganizacion()), datosSMTP);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Boolean enviaMailDeComprobantesNoElectronicos(int idOrganizacion)
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81: 97 */       String ruta = ParametrosSistema.getAS2_HOME(idOrganizacion) + File.separator + "config";
/*  82: 98 */       String parametroFichero = EjbUtil.obtenerValorArchivoProperties("enviaMailDeComprobantesNoElectronicos", ruta, "produccion.properties");
/*  83: 99 */       return Boolean.valueOf(parametroFichero.equals("SI"));
/*  84:    */     }
/*  85:    */     catch (IOException e)
/*  86:    */     {
/*  87:101 */       e.printStackTrace();
/*  88:    */     }
/*  89:102 */     return Boolean.valueOf(false);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void enviarEmailComprobanteNoElectronicos(Organizacion organizacion, int idSucursal, String para, String tituloMensaje, String mensajeCuerpo, DocumentoBase documentoBase, String numeroComprobante, JRDataSource ds, String nombreJasper, String usuarioCreacion)
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:109 */       if (enviaMailDeComprobantesNoElectronicos(organizacion.getId()).booleanValue())
/*  97:    */       {
/*  98:110 */         List<String> archivos = new ArrayList();
/*  99:111 */         Map<String, String> mapaCid = new HashMap();
/* 100:    */         
/* 101:113 */         String rutaLogoOrganizacion = RutaArchivo.getUploadDir(organizacion.getId(), "logo") + organizacion.getImagen();
/* 102:114 */         String rutaComprobantesNoElectronicos = RutaArchivo.getDirectorioUpload(organizacion.getId(), 
/* 103:115 */           FuncionesUtiles.completarALaIzquierda('0', 10, organizacion.getId() + ""), "documentos_no_electronicos");
/* 104:116 */         rutaComprobantesNoElectronicos = rutaComprobantesNoElectronicos + File.separator + documentoBase.toString() + File.separator;
/* 105:    */         
/* 106:    */ 
/* 107:119 */         File file = new File(rutaComprobantesNoElectronicos);
/* 108:120 */         if (!file.exists()) {
/* 109:121 */           file.mkdirs();
/* 110:    */         }
/* 111:124 */         String nombreReporte = numeroComprobante;
/* 112:125 */         String rutaReporte = rutaComprobantesNoElectronicos + nombreReporte;
/* 113:    */         
/* 114:127 */         Map<String, Object> parameters = new HashMap();
/* 115:    */         
/* 116:129 */         parameters.put("logoEmpresa", rutaLogoOrganizacion);
/* 117:130 */         Sucursal sucursal = this.servicioSucursal.cargarDetalle(idSucursal);
/* 118:131 */         parameters.put("nombreOrganizacion", organizacion.getRazonSocial());
/* 119:132 */         parameters.put("p_direccionMatriz", AppUtil.getDireccionMatriz() == null ? "" : AppUtil.getDireccionMatriz());
/* 120:133 */         parameters.put("direccionOrganizacion", sucursal.getUbicacion().getDireccionCompleta());
/* 121:134 */         parameters.put("telefonoOrganizacion", sucursal
/* 122:135 */           .getTelefono1().concat(sucursal.getTelefono2() != null ? "   " + sucursal.getTelefono2() : ""));
/* 123:136 */         parameters.put("usuarioImpresion", AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 124:137 */         parameters.put("identificacionOrganizacion", organizacion.getIdentificacion());
/* 125:138 */         parameters.put("p_formatoFecha", ParametrosSistema.getFormatoFecha(organizacion.getIdOrganizacion()));
/* 126:140 */         if (documentoBase.equals(DocumentoBase.PEDIDO_CLIENTE))
/* 127:    */         {
/* 128:141 */           parameters.put("ReportTitle", "Pedido de Cliente");
/* 129:142 */           parameters.put("FechaPedido", "Fecha pedido:");
/* 130:143 */           parameters.put("CondicionPago", "Condicion de Pago:");
/* 131:144 */           parameters.put("NumeroCuotas", "Numero de Cuotas:");
/* 132:145 */           parameters.put("Subtotal", "Subtotal");
/* 133:146 */           parameters.put("Descuento", "Descuento");
/* 134:147 */           parameters.put("SubtotalDescuento", "Subtotal - Descuento");
/* 135:148 */           parameters.put("Impuesto", "Impuesto");
/* 136:149 */           parameters.put("Total", "Total");
/* 137:150 */           parameters.put("ClienteProveedor", "Cliente:");
/* 138:151 */           parameters.put("directorioImagenesProducto", RutaArchivo.getUploadDir(organizacion.getId(), "producto"));
/* 139:    */         }
/* 140:152 */         else if (documentoBase.equals(DocumentoBase.PEDIDO_PROVEEDOR))
/* 141:    */         {
/* 142:153 */           parameters.put("ReportTitle", "Pedido de Proveedor");
/* 143:154 */           parameters.put("FechaPedido", "Fecha pedido:");
/* 144:155 */           parameters.put("CondicionPago", "Condicion de Pago:");
/* 145:156 */           parameters.put("NumeroCuotas", "Numero de Cuotas:");
/* 146:157 */           parameters.put("Subtotal", "Subtotal");
/* 147:158 */           parameters.put("Descuento", "Descuento");
/* 148:159 */           parameters.put("SubtotalDescuento", "Subtotal - Descuento");
/* 149:160 */           parameters.put("Impuesto", "Impuesto");
/* 150:161 */           parameters.put("Total", "Total");
/* 151:162 */           parameters.put("ClienteProveedor", "Proveedor:");
/* 152:163 */           parameters.put("nombreUsuario", usuarioCreacion.trim());
/* 153:164 */           parameters.put("p_sucursal", Integer.valueOf(sucursal.getId()));
/* 154:    */         }
/* 155:165 */         else if (documentoBase.equals(DocumentoBase.PAGO_PROVEEDOR))
/* 156:    */         {
/* 157:166 */           parameters.put("ReportTitle", "Pago");
/* 158:    */         }
/* 159:167 */         else if (documentoBase.equals(DocumentoBase.PAGO_ROL))
/* 160:    */         {
/* 161:168 */           parameters.put("ReportTitle", "Pago Rol");
/* 162:    */         }
/* 163:169 */         else if (documentoBase.equals(DocumentoBase.SOLICITUD_COMPRA))
/* 164:    */         {
/* 165:170 */           parameters.put("ReportTitle", "Solicitud Compra");
/* 166:    */         }
/* 167:    */         else
/* 168:    */         {
/* 169:173 */           return;
/* 170:    */         }
/* 171:176 */         File reportFile = new File(ReportConfigUtil.getJasperFilePath(ParametrosSistema.getAS2_HOME(organizacion.getId()) + File.separator, "reportes" + File.separator, nombreJasper + ".jasper"));
/* 172:    */         
/* 173:178 */         JasperPrint jasperPrint = null;
/* 174:    */         try
/* 175:    */         {
/* 176:180 */           jasperPrint = ReportConfigUtil.fillReport(reportFile, parameters, ds);
/* 177:181 */           JasperExportManager.exportReportToPdfFile(jasperPrint, rutaReporte + ".pdf");
/* 178:182 */           archivos.add(rutaReporte + ".pdf");
/* 179:    */         }
/* 180:    */         catch (JRException e)
/* 181:    */         {
/* 182:184 */           e.printStackTrace();
/* 183:185 */           System.out.println("No se pudo generar el reporte PDF");
/* 184:    */         }
/* 185:188 */         if ((mensajeCuerpo == null) || (mensajeCuerpo.trim().isEmpty()) || (mensajeCuerpo.trim().equals("<br>")) || 
/* 186:189 */           (mensajeCuerpo.trim().equals("<div><br></div>")))
/* 187:    */         {
/* 188:190 */           mapaCid.put("cidLogoEmpresa", rutaLogoOrganizacion);
/* 189:    */           try
/* 190:    */           {
/* 191:193 */             JasperExportManager.exportReportToHtmlFile(jasperPrint, rutaReporte + ".html");
/* 192:    */           }
/* 193:    */           catch (JRException e)
/* 194:    */           {
/* 195:195 */             e.printStackTrace();
/* 196:196 */             System.out.println("No se pudo generar el reporte HTML");
/* 197:    */           }
/* 198:199 */           mensajeCuerpo = FuncionesUtiles.LeeFicheroHTMLToEmail(rutaReporte + ".html");
/* 199:    */         }
/* 200:203 */         enviarEmail(organizacion.getId(), para, tituloMensaje, mensajeCuerpo, archivos, mapaCid, null, null, null);
/* 201:    */       }
/* 202:    */       else
/* 203:    */       {
/* 204:205 */         System.out.println("No está configurado el envió de emails para los documentos no electrónicos");
/* 205:    */       }
/* 206:    */     }
/* 207:    */     catch (Exception e)
/* 208:    */     {
/* 209:209 */       e.printStackTrace();
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void enviarEmail(int idOrganizaion, String para, String tituloMensaje, String mensajeCuerpo, List<String> archivos, Map<String, String> mapaCid, byte[] adjuntoByte, String nombreAdjuntoByte, String tipoAdjuntoByte)
/* 214:    */   {
/* 215:    */     try
/* 216:    */     {
/* 217:216 */       DatosSMTP datoSMTP = (DatosSMTP)hmDatosSMTP.get(Integer.valueOf(idOrganizaion));
/* 218:217 */       if ((para != null) && (!para.trim().isEmpty()) && (datoSMTP != null) && (datoSMTP.getSmtpPort() != null) && 
/* 219:218 */         (Integer.parseInt(datoSMTP.getSmtpPort()) > 0) && (datoSMTP.getSmtpHost() != null) && (!datoSMTP.getSmtpHost().isEmpty()) && 
/* 220:219 */         (datoSMTP.getSmtpFrom() != null) && (!datoSMTP.getSmtpFrom().isEmpty()))
/* 221:    */       {
/* 222:221 */         List<String> listaCid = new ArrayList();
/* 223:222 */         for (String clave : mapaCid.keySet())
/* 224:    */         {
/* 225:223 */           String cid = clave + ";" + (String)mapaCid.get(clave);
/* 226:224 */           listaCid.add(cid);
/* 227:    */         }
/* 228:226 */         String strMapaCid = FuncionesUtiles.arrayToString((String[])listaCid.toArray(new String[listaCid.size()]));
/* 229:227 */         String strArchivos = FuncionesUtiles.arrayToString((String[])archivos.toArray(new String[archivos.size()]));
/* 230:    */         
/* 231:229 */         MensajeEmail me = new MensajeEmail(idOrganizaion, para, tituloMensaje, mensajeCuerpo, strArchivos, strMapaCid, adjuntoByte, nombreAdjuntoByte, tipoAdjuntoByte);
/* 232:    */         
/* 233:231 */         int idMensajeEmail = aiMensajeEmail.incrementAndGet();
/* 234:232 */         cmMensajeEmail.put(Integer.valueOf(idMensajeEmail), me);
/* 235:    */         
/* 236:    */ 
/* 237:    */ 
/* 238:236 */         MapMessage mapMessage = this.context.createMapMessage();
/* 239:237 */         mapMessage.setInt("idMapMessage", idMensajeEmail);
/* 240:    */         try
/* 241:    */         {
/* 242:241 */           JMSProducer producer = this.context.createProducer();
/* 243:    */           
/* 244:243 */           producer.send(this.destination, mapMessage);
/* 245:    */         }
/* 246:    */         catch (Exception e)
/* 247:    */         {
/* 248:248 */           e.printStackTrace();
/* 249:    */         }
/* 250:    */       }
/* 251:    */     }
/* 252:    */     catch (JMSException e)
/* 253:    */     {
/* 254:252 */       e.printStackTrace();
/* 255:    */     }
/* 256:    */     catch (Exception e)
/* 257:    */     {
/* 258:254 */       e.printStackTrace();
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   public class DatosSMTP
/* 263:    */     implements Serializable
/* 264:    */   {
/* 265:    */     private static final long serialVersionUID = 1L;
/* 266:    */     private int idOrganizaion;
/* 267:263 */     private Boolean autenticacion = Boolean.valueOf(true);
/* 268:    */     private String smtpFrom;
/* 269:    */     private String smtpHost;
/* 270:    */     private String smtpPort;
/* 271:    */     private String nombreUsuario;
/* 272:    */     private String clave;
/* 273:269 */     private Boolean autenticacionSSL = Boolean.valueOf(false);
/* 274:    */     private String smtpCC;
/* 275:    */     
/* 276:    */     public DatosSMTP() {}
/* 277:    */     
/* 278:    */     public int getIdOrganizaion()
/* 279:    */     {
/* 280:273 */       return this.idOrganizaion;
/* 281:    */     }
/* 282:    */     
/* 283:    */     public void setIdOrganizaion(int idOrganizaion)
/* 284:    */     {
/* 285:277 */       this.idOrganizaion = idOrganizaion;
/* 286:    */     }
/* 287:    */     
/* 288:    */     public Boolean isAutenticacion()
/* 289:    */     {
/* 290:281 */       return this.autenticacion;
/* 291:    */     }
/* 292:    */     
/* 293:    */     public void setAutenticacion(Boolean autenticacion)
/* 294:    */     {
/* 295:285 */       this.autenticacion = autenticacion;
/* 296:    */     }
/* 297:    */     
/* 298:    */     public String getSmtpFrom()
/* 299:    */     {
/* 300:289 */       return this.smtpFrom;
/* 301:    */     }
/* 302:    */     
/* 303:    */     public void setSmtpFrom(String smtpFrom)
/* 304:    */     {
/* 305:293 */       this.smtpFrom = smtpFrom;
/* 306:    */     }
/* 307:    */     
/* 308:    */     public String getSmtpHost()
/* 309:    */     {
/* 310:297 */       return this.smtpHost;
/* 311:    */     }
/* 312:    */     
/* 313:    */     public void setSmtpHost(String smtpHost)
/* 314:    */     {
/* 315:301 */       this.smtpHost = smtpHost;
/* 316:    */     }
/* 317:    */     
/* 318:    */     public String getSmtpPort()
/* 319:    */     {
/* 320:305 */       return this.smtpPort;
/* 321:    */     }
/* 322:    */     
/* 323:    */     public void setSmtpPort(String smtpPort)
/* 324:    */     {
/* 325:309 */       this.smtpPort = smtpPort;
/* 326:    */     }
/* 327:    */     
/* 328:    */     public String getNombreUsuario()
/* 329:    */     {
/* 330:313 */       return this.nombreUsuario;
/* 331:    */     }
/* 332:    */     
/* 333:    */     public void setNombreUsuario(String nombreUsuario)
/* 334:    */     {
/* 335:317 */       this.nombreUsuario = nombreUsuario;
/* 336:    */     }
/* 337:    */     
/* 338:    */     public String getClave()
/* 339:    */     {
/* 340:321 */       return this.clave;
/* 341:    */     }
/* 342:    */     
/* 343:    */     public void setClave(String clave)
/* 344:    */     {
/* 345:325 */       this.clave = clave;
/* 346:    */     }
/* 347:    */     
/* 348:    */     public Boolean getAutenticacionSSL()
/* 349:    */     {
/* 350:332 */       return this.autenticacionSSL;
/* 351:    */     }
/* 352:    */     
/* 353:    */     public void setAutenticacionSSL(Boolean autenticacionSSL)
/* 354:    */     {
/* 355:340 */       this.autenticacionSSL = autenticacionSSL;
/* 356:    */     }
/* 357:    */     
/* 358:    */     public String getSmtpCC()
/* 359:    */     {
/* 360:344 */       return this.smtpCC;
/* 361:    */     }
/* 362:    */     
/* 363:    */     public void setSmtpCC(String smtpCC)
/* 364:    */     {
/* 365:348 */       this.smtpCC = smtpCC;
/* 366:    */     }
/* 367:    */   }
/* 368:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.servicio.ServicioEnvioEmail
 * JD-Core Version:    0.7.0.1
 */