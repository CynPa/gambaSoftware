/*   1:    */ package com.asinfo.as2.webservices.procesos.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioProvincia;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*  11:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  12:    */ import com.asinfo.as2.entities.Ciudad;
/*  13:    */ import com.asinfo.as2.entities.Cliente;
/*  14:    */ import com.asinfo.as2.entities.CondicionPago;
/*  15:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  16:    */ import com.asinfo.as2.entities.Empresa;
/*  17:    */ import com.asinfo.as2.entities.FormaPago;
/*  18:    */ import com.asinfo.as2.entities.Pais;
/*  19:    */ import com.asinfo.as2.entities.Provincia;
/*  20:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  21:    */ import com.asinfo.as2.entities.Ubicacion;
/*  22:    */ import com.asinfo.as2.enumeraciones.MensajeErrorEnum;
/*  23:    */ import com.asinfo.as2.enumeraciones.MetodoFacturacionEnum;
/*  24:    */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*  25:    */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*  26:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  27:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  28:    */ import com.asinfo.as2.webservices.procesos.ServicioWebCliente;
/*  29:    */ import com.asinfo.as2.ws.datosbase.ClienteWSEntity;
/*  30:    */ import com.asinfo.as2.ws.respuesta.MensajeWSEntity;
/*  31:    */ import com.asinfo.as2.ws.respuesta.RespuestaWSEntity;
/*  32:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  33:    */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*  34:    */ import java.io.PrintStream;
/*  35:    */ import java.util.ArrayList;
/*  36:    */ import java.util.HashMap;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.ejb.ApplicationException;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.ejb.TransactionAttribute;
/*  42:    */ import javax.ejb.TransactionAttributeType;
/*  43:    */ import javax.jws.WebService;
/*  44:    */ 
/*  45:    */ @WebService(endpointInterface="com.asinfo.as2.webservices.procesos.ServicioWebCliente", serviceName="ClienteService", portName="ClientePort")
/*  46:    */ @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  47:    */ @ApplicationException(rollback=true)
/*  48:    */ public class ServicioWebClienteImpl
/*  49:    */   extends Exception
/*  50:    */   implements ServicioWebCliente
/*  51:    */ {
/*  52:    */   private static final long serialVersionUID = 1549892092907607317L;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioEmpresa servicioEmpresa;
/*  55:    */   @EJB
/*  56:    */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioCiudad servicioCiudad;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioProvincia servicioProvincia;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioPais servicioPais;
/*  63:    */   @EJB
/*  64:    */   private transient ServicioFormaPago servicioFormaPago;
/*  65:    */   @EJB
/*  66:    */   private transient ServicioCondicionPago servicioCondicionPago;
/*  67:    */   @EJB
/*  68:    */   private transient ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  69:    */   
/*  70:    */   public RespuestaWSEntity[] crearCliente(int organizacion, ClienteWSEntity[] listaCliente)
/*  71:    */   {
/*  72: 76 */     List<RespuestaWSEntity> listaRespuesta = new ArrayList();
/*  73: 77 */     for (ClienteWSEntity clienteWS : listaCliente)
/*  74:    */     {
/*  75: 78 */       RespuestaWSEntity respuesta = new RespuestaWSEntity();
/*  76: 79 */       respuesta.setReferenciaDocumento(clienteWS.getIdentificacion());
/*  77: 80 */       List<MensajeWSEntity> listaMensaje = new ArrayList();
/*  78:    */       
/*  79:    */ 
/*  80: 83 */       listaMensaje = comprobarDatos(organizacion, clienteWS);
/*  81: 84 */       if (listaMensaje.size() > 0)
/*  82:    */       {
/*  83: 85 */         respuesta.setEstado("ERROR");
/*  84: 86 */         respuesta.setListaMensaje((MensajeWSEntity[])listaMensaje.toArray(new MensajeWSEntity[0]));
/*  85: 87 */         listaRespuesta.add(respuesta);
/*  86:    */       }
/*  87:    */       else
/*  88:    */       {
/*  89: 91 */         Empresa empresa = new Empresa();
/*  90: 92 */         boolean actualizar = false;
/*  91:    */         try
/*  92:    */         {
/*  93:    */           try
/*  94:    */           {
/*  95: 95 */             empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(organizacion, clienteWS.getIdentificacion());
/*  96: 96 */             empresa = this.servicioEmpresa.cargarDetalle(empresa);
/*  97: 97 */             if ((clienteWS.getIdentificacionNueva() != null) && (!clienteWS.getIdentificacionNueva().isEmpty())) {
/*  98: 98 */               empresa.setIdentificacion(clienteWS.getIdentificacionNueva());
/*  99:    */             }
/* 100:100 */             actualizar = true;
/* 101:    */           }
/* 102:    */           catch (ExcepcionAS2 e)
/* 103:    */           {
/* 104:104 */             actualizar = false;
/* 105:    */           }
/* 106:107 */           empresa.setActivo(true);
/* 107:108 */           empresa.setIdOrganizacion(organizacion);
/* 108:109 */           if ((empresa.getCodigo() == null) || (empresa.getCodigo().isEmpty())) {
/* 109:110 */             empresa.setCodigo(clienteWS.getIdentificacion().toUpperCase());
/* 110:    */           }
/* 111:112 */           empresa.setDescripcion(clienteWS.getDescripcion());
/* 112:113 */           empresa.setEmail1(clienteWS.getEmail());
/* 113:114 */           empresa.setIndicadorCliente(true);
/* 114:115 */           empresa.setNombreComercial(clienteWS.getNombre());
/* 115:116 */           empresa.setNombreFiscal(clienteWS.getNombre());
/* 116:117 */           empresa.setTipoEmpresa(clienteWS.getTipoCliente().intValue() == 1 ? TipoEmpresa.NATURAL : TipoEmpresa.JURIDICA);
/* 117:118 */           if (!actualizar)
/* 118:    */           {
/* 119:119 */             CategoriaEmpresa categoriaEmpresa = this.servicioCategoriaEmpresa.buscarPorCodigo(organizacion, "CLI-PRO");
/* 120:120 */             empresa.setCategoriaEmpresa(categoriaEmpresa);
/* 121:    */           }
/* 122:122 */           if ((empresa.getIdentificacion() == null) || (empresa.getIdentificacion().isEmpty())) {
/* 123:123 */             empresa.setIdentificacion(clienteWS.getIdentificacion());
/* 124:    */           }
/* 125:126 */           TipoIdentificacion tipoIdentificacion = this.servicioTipoIdentificacion.buscarPorCodigo(organizacion, clienteWS.getTipoIdentificacion());
/* 126:127 */           if (tipoIdentificacion != null)
/* 127:    */           {
/* 128:128 */             empresa.setTipoIdentificacion(tipoIdentificacion);
/* 129:    */             try
/* 130:    */             {
/* 131:130 */               ValidarIdentificacion.validarIdentificacion(tipoIdentificacion.isIndicadorValidarIdentificacion(), clienteWS
/* 132:131 */                 .getIdentificacion());
/* 133:    */             }
/* 134:    */             catch (ExcepcionAS2Identification e)
/* 135:    */             {
/* 136:133 */               MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Identificación inválida");
/* 137:134 */               listaMensaje.add(mensaje);
/* 138:    */             }
/* 139:    */           }
/* 140:    */           else
/* 141:    */           {
/* 142:138 */             MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el tipoIdentificacion con el código dado");
/* 143:139 */             listaMensaje.add(mensaje);
/* 144:    */           }
/* 145:142 */           DireccionEmpresa direccion = cargarDireccionEmpresa(organizacion, clienteWS);
/* 146:143 */           direccion.setEmpresa(empresa);
/* 147:144 */           empresa.setDirecciones(new ArrayList());
/* 148:145 */           empresa.getDirecciones().add(direccion);
/* 149:147 */           if (empresa.getCliente() == null)
/* 150:    */           {
/* 151:149 */             Cliente cliente = new Cliente();
/* 152:150 */             cliente.setIdOrganizacion(organizacion);
/* 153:151 */             cliente.setEmpresa(empresa);
/* 154:152 */             empresa.setCliente(cliente);
/* 155:153 */             cliente.setNumeroCuotas(1);
/* 156:    */           }
/* 157:    */           try
/* 158:    */           {
/* 159:157 */             Map<String, String> filters = new HashMap();
/* 160:158 */             filters.put("codigo", "=" + clienteWS.getFormaPago());
/* 161:159 */             filters.put("idOrganizacion", organizacion + "");
/* 162:160 */             FormaPago formaPago = this.servicioFormaPago.buscarFormaPago(filters);
/* 163:161 */             empresa.getCliente().setFormaPago(formaPago);
/* 164:    */           }
/* 165:    */           catch (ExcepcionAS2 e1)
/* 166:    */           {
/* 167:163 */             MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe la formaPago con el código dado");
/* 168:164 */             listaMensaje.add(mensaje);
/* 169:    */           }
/* 170:167 */           CondicionPago condicionPago = null;
/* 171:    */           try
/* 172:    */           {
/* 173:169 */             condicionPago = this.servicioCondicionPago.buscarCondicionPagoPorDiasPlazo(clienteWS.getCondicionPago().intValue(), organizacion);
/* 174:    */           }
/* 175:    */           catch (ExcepcionAS2 e1)
/* 176:    */           {
/* 177:171 */             condicionPago = new CondicionPago();
/* 178:172 */             condicionPago.setActivo(true);
/* 179:173 */             condicionPago.setIdOrganizacion(organizacion);
/* 180:174 */             condicionPago.setDiasPlazo(clienteWS.getCondicionPago().intValue());
/* 181:175 */             condicionPago.setNombre(clienteWS.getCondicionPago() + " dias");
/* 182:176 */             condicionPago.setCodigo(clienteWS.getCondicionPago() + "DIAS");
/* 183:177 */             this.servicioCondicionPago.guardar(condicionPago);
/* 184:    */           }
/* 185:179 */           empresa.getCliente().setCondicionPago(condicionPago);
/* 186:180 */           if (!actualizar)
/* 187:    */           {
/* 188:181 */             empresa.getCliente().setMetodoFacturacion(MetodoFacturacionEnum.PEDIDO_DESPACHO_FACTURA);
/* 189:182 */             empresa.getCliente().setTipoCliente(TipoEmpresaEnum.PRINCIPAL);
/* 190:    */           }
/* 191:    */         }
/* 192:    */         catch (Exception localException1) {}
/* 193:    */         try
/* 194:    */         {
/* 195:190 */           if (listaMensaje.size() == 0)
/* 196:    */           {
/* 197:191 */             this.servicioEmpresa.guardar(empresa);
/* 198:192 */             respuesta.setEstado("REGISTRADO");
/* 199:    */           }
/* 200:    */           else
/* 201:    */           {
/* 202:194 */             respuesta.setEstado("ERROR");
/* 203:    */           }
/* 204:    */         }
/* 205:    */         catch (Exception e)
/* 206:    */         {
/* 207:198 */           System.out.println(e.getMessage() + " " + e.getStackTrace());
/* 208:199 */           respuesta.setEstado("ERROR");
/* 209:200 */           MensajeWSEntity mensajeError = new MensajeWSEntity(MensajeErrorEnum.ERROR_501, e.getMessage());
/* 210:201 */           listaMensaje.add(mensajeError);
/* 211:    */         }
/* 212:203 */         respuesta.setListaMensaje((MensajeWSEntity[])listaMensaje.toArray(new MensajeWSEntity[0]));
/* 213:204 */         listaRespuesta.add(respuesta);
/* 214:    */       }
/* 215:    */     }
/* 216:207 */     return (RespuestaWSEntity[])listaRespuesta.toArray(new RespuestaWSEntity[0]);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public ClienteWSEntity consultarCliente(int organizacion, String identificacion)
/* 220:    */     throws AS2Exception
/* 221:    */   {
/* 222:212 */     ClienteWSEntity clienteWSEntity = null;
/* 223:213 */     Empresa empresa = null;
/* 224:    */     try
/* 225:    */     {
/* 226:215 */       empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(organizacion, identificacion);
/* 227:216 */       if (empresa != null) {
/* 228:217 */         return new ClienteWSEntity(empresa);
/* 229:    */       }
/* 230:220 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { identificacion });
/* 231:    */     }
/* 232:    */     catch (Exception e)
/* 233:    */     {
/* 234:223 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { identificacion });
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   private List<MensajeWSEntity> comprobarDatos(int organizacion, ClienteWSEntity cliente)
/* 239:    */   {
/* 240:228 */     List<MensajeWSEntity> listaMensaje = new ArrayList();
/* 241:229 */     if ((!cliente.getTipoIdentificacion().equals("R")) && (!cliente.getTipoIdentificacion().equals("C")) && 
/* 242:230 */       (!cliente.getTipoIdentificacion().equals("P")) && (!cliente.getTipoIdentificacion().equals("O")))
/* 243:    */     {
/* 244:231 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Valor no válido del campo tipoIdentificación");
/* 245:232 */       listaMensaje.add(mensaje);
/* 246:    */     }
/* 247:234 */     if ((cliente.getIdentificacionNueva() == null) || (cliente.getIdentificacionNueva().isEmpty()))
/* 248:    */     {
/* 249:235 */       if (((cliente.getTipoIdentificacion().equals("R")) && (cliente.getIdentificacion().length() != 13)) || 
/* 250:236 */         ((cliente.getTipoIdentificacion().equals("C")) && (cliente.getIdentificacion().length() != 10)) || 
/* 251:237 */         ((cliente.getTipoIdentificacion().equals("P")) && ((cliente.getIdentificacion().length() <= 5) || 
/* 252:238 */         (cliente.getIdentificacion().length() >= 21))) || ((cliente.getTipoIdentificacion().equals("O")) && (!cliente.getIdentificacion().equals("9999999999999"))))
/* 253:    */       {
/* 254:239 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo identificacion");
/* 255:240 */         listaMensaje.add(mensaje);
/* 256:    */       }
/* 257:    */     }
/* 258:243 */     else if (((cliente.getTipoIdentificacion().equals("R")) && (cliente.getIdentificacionNueva().length() != 13)) || 
/* 259:244 */       ((cliente.getTipoIdentificacion().equals("C")) && (cliente.getIdentificacionNueva().length() != 10)) || 
/* 260:245 */       ((cliente.getTipoIdentificacion().equals("P")) && ((cliente.getIdentificacionNueva().length() <= 5) || 
/* 261:246 */       (cliente.getIdentificacionNueva().length() >= 21))) || (
/* 262:247 */       (cliente.getTipoIdentificacion().equals("O")) && (!cliente.getIdentificacionNueva().equals("9999999999999"))))
/* 263:    */     {
/* 264:248 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo identificacion");
/* 265:249 */       listaMensaje.add(mensaje);
/* 266:    */     }
/* 267:252 */     if ((cliente.getNombre().length() < 5) || (cliente.getNombre().length() > 100))
/* 268:    */     {
/* 269:253 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo nombre");
/* 270:254 */       listaMensaje.add(mensaje);
/* 271:    */     }
/* 272:256 */     if ((cliente.getTipoCliente().intValue() != 1) && (cliente.getTipoCliente().intValue() != 2))
/* 273:    */     {
/* 274:257 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Valor no válido del campo tipoCliente");
/* 275:258 */       listaMensaje.add(mensaje);
/* 276:    */     }
/* 277:260 */     if (cliente.getTelefono().length() > 13)
/* 278:    */     {
/* 279:261 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo telefono");
/* 280:262 */       listaMensaje.add(mensaje);
/* 281:    */     }
/* 282:264 */     if ((cliente.getEmail().length() < 5) || (cliente.getEmail().length() > 50))
/* 283:    */     {
/* 284:265 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo email");
/* 285:266 */       listaMensaje.add(mensaje);
/* 286:    */     }
/* 287:268 */     if ((!cliente.getCodigoISOPais().isEmpty()) && (cliente.getCodigoISOPais().length() > 10))
/* 288:    */     {
/* 289:269 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo codigoISOPais");
/* 290:270 */       listaMensaje.add(mensaje);
/* 291:    */     }
/* 292:272 */     if ((!cliente.getPais().isEmpty()) && (cliente.getPais().length() > 50))
/* 293:    */     {
/* 294:273 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo pais");
/* 295:274 */       listaMensaje.add(mensaje);
/* 296:    */     }
/* 297:276 */     if ((!cliente.getProvincia().isEmpty()) && (cliente.getProvincia().length() > 50))
/* 298:    */     {
/* 299:277 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo provincia");
/* 300:278 */       listaMensaje.add(mensaje);
/* 301:    */     }
/* 302:280 */     if ((!cliente.getCiudad().isEmpty()) && (cliente.getCiudad().length() > 50))
/* 303:    */     {
/* 304:281 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo ciudad");
/* 305:282 */       listaMensaje.add(mensaje);
/* 306:    */     }
/* 307:284 */     if (cliente.getDireccion().length() > 200)
/* 308:    */     {
/* 309:285 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo direccion");
/* 310:286 */       listaMensaje.add(mensaje);
/* 311:    */     }
/* 312:288 */     if (cliente.getCondicionPago().intValue() > 365)
/* 313:    */     {
/* 314:289 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "La condición de pago no debe superar los 365 días");
/* 315:290 */       listaMensaje.add(mensaje);
/* 316:    */     }
/* 317:292 */     if ((!cliente.getDescripcion().isEmpty()) && (cliente.getDescripcion().length() > 500))
/* 318:    */     {
/* 319:293 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo descripcion");
/* 320:294 */       listaMensaje.add(mensaje);
/* 321:    */     }
/* 322:    */     try
/* 323:    */     {
/* 324:297 */       Map<String, String> filters = new HashMap();
/* 325:298 */       filters.put("codigo", "=" + cliente.getFormaPago());
/* 326:299 */       filters.put("idOrganizacion", organizacion + "");
/* 327:300 */       this.servicioFormaPago.buscarFormaPago(filters);
/* 328:    */     }
/* 329:    */     catch (ExcepcionAS2 e1)
/* 330:    */     {
/* 331:302 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe la formaPago con el código dado");
/* 332:303 */       listaMensaje.add(mensaje);
/* 333:    */     }
/* 334:306 */     return listaMensaje;
/* 335:    */   }
/* 336:    */   
/* 337:    */   private DireccionEmpresa cargarDireccionEmpresa(int organizacion, ClienteWSEntity clienteWS)
/* 338:    */   {
/* 339:310 */     clienteWS.setCiudad(clienteWS.getCiudad().isEmpty() ? "Quito" : clienteWS.getCiudad());
/* 340:311 */     clienteWS.setProvincia(clienteWS.getProvincia().isEmpty() ? "Pichincha" : clienteWS.getProvincia());
/* 341:312 */     clienteWS.setPais(clienteWS.getPais().isEmpty() ? "ECUADOR" : clienteWS.getPais());
/* 342:313 */     clienteWS.setCodigoISOPais(clienteWS.getCodigoISOPais().isEmpty() ? "ECU" : clienteWS.getCodigoISOPais());
/* 343:314 */     DireccionEmpresa direccion = new DireccionEmpresa();
/* 344:315 */     direccion.setIdOrganizacion(organizacion);
/* 345:316 */     direccion.setActivo(true);
/* 346:317 */     direccion.setTelefono1(clienteWS.getTelefono());
/* 347:    */     
/* 348:    */ 
/* 349:320 */     Ubicacion ubicacion = new Ubicacion();
/* 350:321 */     ubicacion.setIdOrganizacion(organizacion);
/* 351:322 */     String direccion1 = clienteWS.getDireccion();
/* 352:323 */     String direccion2 = "";
/* 353:324 */     String direccion3 = "";
/* 354:325 */     String direccion4 = "";
/* 355:326 */     if (clienteWS.getDireccion().length() > 50)
/* 356:    */     {
/* 357:327 */       direccion1 = clienteWS.getDireccion().substring(0, 50);
/* 358:328 */       direccion2 = clienteWS.getDireccion().substring(50, clienteWS.getDireccion().length());
/* 359:    */     }
/* 360:330 */     if (clienteWS.getDireccion().length() > 100)
/* 361:    */     {
/* 362:331 */       direccion2 = clienteWS.getDireccion().substring(50, 100);
/* 363:332 */       direccion3 = clienteWS.getDireccion().substring(100, clienteWS.getDireccion().length());
/* 364:    */     }
/* 365:334 */     if (clienteWS.getDireccion().length() > 150)
/* 366:    */     {
/* 367:335 */       direccion3 = clienteWS.getDireccion().substring(100, 150);
/* 368:336 */       direccion4 = clienteWS.getDireccion().substring(150, clienteWS.getDireccion().length());
/* 369:    */     }
/* 370:338 */     if (clienteWS.getDireccion().length() > 200) {
/* 371:339 */       return null;
/* 372:    */     }
/* 373:341 */     ubicacion.setDireccion1(direccion1);
/* 374:342 */     ubicacion.setDireccion2(direccion2);
/* 375:343 */     ubicacion.setDireccion3(direccion3);
/* 376:344 */     ubicacion.setDireccion4(direccion4);
/* 377:    */     
/* 378:346 */     direccion.setUbicacion(ubicacion);
/* 379:    */     
/* 380:    */ 
/* 381:349 */     Ciudad ciudad = this.servicioCiudad.buscarPorNombre(clienteWS.getCiudad());
/* 382:350 */     if (ciudad == null)
/* 383:    */     {
/* 384:351 */       ciudad = new Ciudad();
/* 385:352 */       ciudad.setIdOrganizacion(organizacion);
/* 386:353 */       ciudad.setActivo(true);
/* 387:354 */       ciudad.setNombre(clienteWS.getCiudad());
/* 388:355 */       Provincia provincia = this.servicioProvincia.buscarPorNombre(clienteWS.getProvincia());
/* 389:356 */       if (provincia == null)
/* 390:    */       {
/* 391:357 */         provincia = new Provincia();
/* 392:358 */         provincia.setIdOrganizacion(organizacion);
/* 393:359 */         provincia.setActivo(true);
/* 394:360 */         provincia.setNombre(clienteWS.getProvincia());
/* 395:361 */         Pais pais = this.servicioPais.buscarPorNombre(clienteWS.getPais());
/* 396:362 */         if (pais == null)
/* 397:    */         {
/* 398:363 */           pais = new Pais();
/* 399:364 */           pais.setIdOrganizacion(organizacion);
/* 400:365 */           pais.setActivo(true);
/* 401:366 */           pais.setNombre(clienteWS.getPais());
/* 402:367 */           pais.setCodigoIso(clienteWS.getCodigoISOPais());
/* 403:368 */           this.servicioPais.guardar(pais);
/* 404:    */         }
/* 405:370 */         provincia.setPais(pais);
/* 406:371 */         this.servicioProvincia.guardar(provincia);
/* 407:    */       }
/* 408:373 */       ciudad.setProvincia(provincia);
/* 409:374 */       this.servicioCiudad.guardar(ciudad);
/* 410:    */     }
/* 411:376 */     direccion.setCiudad(ciudad);
/* 412:    */     
/* 413:378 */     return direccion;
/* 414:    */   }
/* 415:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.procesos.impl.ServicioWebClienteImpl
 * JD-Core Version:    0.7.0.1
 */