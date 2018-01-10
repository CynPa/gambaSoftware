/*   1:    */ package com.asinfo.as2.rs.inventario.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.dao.SubempresaDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Bodega;
/*  10:    */ import com.asinfo.as2.entities.Cliente;
/*  11:    */ import com.asinfo.as2.entities.CondicionPago;
/*  12:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*  13:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.Empresa;
/*  16:    */ import com.asinfo.as2.entities.PedidoCliente;
/*  17:    */ import com.asinfo.as2.entities.Producto;
/*  18:    */ import com.asinfo.as2.entities.RegistroMovil;
/*  19:    */ import com.asinfo.as2.entities.Subempresa;
/*  20:    */ import com.asinfo.as2.entities.Unidad;
/*  21:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  29:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesoGenericoResponseDto;
/*  30:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  31:    */ import com.asinfo.as2.rs.inventario.dto.ConsultarPedidoRequestDto;
/*  32:    */ import com.asinfo.as2.rs.inventario.dto.CrearPedidoRequestDto;
/*  33:    */ import com.asinfo.as2.rs.inventario.dto.DetallePedidoRequestDto;
/*  34:    */ import com.asinfo.as2.rs.inventario.dto.EstadoPedidoResponseDto;
/*  35:    */ import com.asinfo.as2.rs.inventario.dto.PedidoSugeridoRequestDto;
/*  36:    */ import com.asinfo.as2.rs.inventario.dto.PedidoSugeridoResponseDto;
/*  37:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  38:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  39:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  40:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  41:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  42:    */ import java.io.PrintStream;
/*  43:    */ import java.math.BigDecimal;
/*  44:    */ import java.math.RoundingMode;
/*  45:    */ import java.text.ParseException;
/*  46:    */ import java.text.SimpleDateFormat;
/*  47:    */ import java.util.ArrayList;
/*  48:    */ import java.util.Date;
/*  49:    */ import java.util.HashMap;
/*  50:    */ import java.util.Iterator;
/*  51:    */ import java.util.List;
/*  52:    */ import java.util.Map;
/*  53:    */ import javax.ejb.EJB;
/*  54:    */ import javax.ws.rs.Consumes;
/*  55:    */ import javax.ws.rs.POST;
/*  56:    */ import javax.ws.rs.Path;
/*  57:    */ import javax.ws.rs.Produces;
/*  58:    */ 
/*  59:    */ @Path("/pedido")
/*  60:    */ public class PedidoServicioRest
/*  61:    */ {
/*  62:    */   @EJB
/*  63:    */   private transient ServicioPedidoCliente servicioPedidoCliente;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioSucursal servicioSucursal;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioBodega servicioBodega;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioDocumento servicioDocumento;
/*  70:    */   @EJB
/*  71:    */   private transient ServicioProducto servicioProducto;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioEmpresa servicioEmpresa;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioUsuario servicioUsuario;
/*  76:    */   @EJB
/*  77:    */   private transient SubempresaDao subempresaDao;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioGenerico<RegistroMovil> servicioRegistroMovil;
/*  80:    */   @EJB
/*  81:    */   private transient ServicioCondicionPago servicioCondicionPago;
/*  82: 85 */   private LanguageController languageController = new LanguageController();
/*  83:    */   
/*  84:    */   public LanguageController getLanguageController()
/*  85:    */   {
/*  86: 88 */     return this.languageController;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setLanguageController(LanguageController languageController)
/*  90:    */   {
/*  91: 92 */     this.languageController = languageController;
/*  92:    */   }
/*  93:    */   
/*  94:    */   @POST
/*  95:    */   @Path("/crearPedido")
/*  96:    */   @Consumes({"application/json"})
/*  97:    */   @Produces({"application/json"})
/*  98:    */   public ProcesosResponseDto crearPedido(CrearPedidoRequestDto request)
/*  99:    */     throws AS2Exception
/* 100:    */   {
/* 101:101 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 102:102 */     String error = null;
/* 103:103 */     this.languageController.setAccesoWeb(false);
/* 104:104 */     this.languageController.setUrlHost(request.getUrlApp());
/* 105:105 */     RegistroMovil registroMovil = new RegistroMovil();
/* 106:106 */     registroMovil.setFecha(new Date());
/* 107:107 */     registroMovil.setCodigoMovil(request.getCodigoMovil());
/* 108:108 */     registroMovil.setDocumentoBase(DocumentoBase.PEDIDO_CLIENTE);
/* 109:109 */     registroMovil.setIdOrganizacion(request.getOrganizacion().intValue());
/* 110:110 */     registroMovil.setIdSucursal(request.getSucursal().intValue());
/* 111:    */     try
/* 112:    */     {
/* 113:112 */       EntidadUsuario usuario = this.servicioUsuario.buscarPorId(request.getUsuario());
/* 114:113 */       registroMovil.setUsuario(usuario.getNombreUsuario());
/* 115:    */       
/* 116:115 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 117:116 */       Map<String, String> filtros = new HashMap();
/* 118:117 */       filtros.put("codigoMovil", "=" + request.getCodigoMovil());
/* 119:118 */       List<PedidoCliente> listaPedidoBase = this.servicioPedidoCliente.obtenerListaCombo(null, true, filtros);
/* 120:120 */       if (listaPedidoBase.size() > 0)
/* 121:    */       {
/* 122:121 */         PedidoCliente pedido = (PedidoCliente)listaPedidoBase.get(0);
/* 123:122 */         response.setSuccsess(true);
/* 124:123 */         registroMovil.setNumeroAs2(pedido.getNumero());
/* 125:124 */         registroMovil.setNota("REGISTRADO");
/* 126:125 */         this.servicioRegistroMovil.guardar(registroMovil);
/* 127:    */         
/* 128:127 */         ProcesoGenericoResponseDto pedidoResponse = new ProcesoGenericoResponseDto();
/* 129:128 */         pedidoResponse.setIdAs2(Integer.valueOf(pedido.getId()));
/* 130:129 */         pedidoResponse.setEstado(pedido.getEstado().toString());
/* 131:130 */         pedidoResponse.setIdentificacionDispositivo(request.getCodigoMovil());
/* 132:131 */         pedidoResponse.setNumeroAs2(pedido.getNumero());
/* 133:132 */         response.setResponse(pedidoResponse);
/* 134:133 */         return response;
/* 135:    */       }
/* 136:136 */       PedidoCliente pedido = new PedidoCliente();
/* 137:137 */       pedido.setIdOrganizacion(request.getOrganizacion().intValue());
/* 138:138 */       pedido.setSucursal(this.servicioSucursal.buscarPorId(request.getSucursal()));
/* 139:139 */       if (request.getReferencia8() != null) {
/* 140:140 */         pedido.setReferencia8(request.getReferencia8());
/* 141:    */       }
/* 142:142 */       pedido.setDescripcion(request.getNota());
/* 143:143 */       if (request.getBodega() != null) {
/* 144:144 */         pedido.setBodega(this.servicioBodega.buscarPorId(request.getBodega()));
/* 145:    */       }
/* 146:146 */       pedido.setCodigoMovil(request.getCodigoMovil());
/* 147:148 */       if (pedido.getBodega() == null)
/* 148:    */       {
/* 149:149 */         List<Bodega> listaBodega = this.servicioBodega.obtenerListaComboPorUsuario(request.getUsuario().intValue(), request.getOrganizacion().intValue());
/* 150:150 */         if (listaBodega.size() > 0) {
/* 151:151 */           pedido.setBodega((Bodega)listaBodega.get(0));
/* 152:    */         }
/* 153:    */       }
/* 154:155 */       pedido.setNumero("");
/* 155:157 */       if ((request.getIndicadorCualquierFecha() == null) || (!request.getIndicadorCualquierFecha().booleanValue()))
/* 156:    */       {
/* 157:158 */         Date hoy = new Date();
/* 158:159 */         hoy = FuncionesUtiles.setAtributoFecha(hoy);
/* 159:160 */         String fechaHoy = sdf.format(hoy);
/* 160:161 */         if (!fechaHoy.equals(request.getFecha()))
/* 161:    */         {
/* 162:162 */           if (request.getUrlApp() != null)
/* 163:    */           {
/* 164:163 */             response.setSuccsess(false);
/* 165:164 */             error = getLanguageController().getMensaje("msg_error_diferencia_fechas");
/* 166:165 */             response.setError(error);
/* 167:    */             
/* 168:167 */             registroMovil.setNota(error);
/* 169:168 */             this.servicioRegistroMovil.guardar(registroMovil);
/* 170:    */             
/* 171:170 */             return response;
/* 172:    */           }
/* 173:172 */           registroMovil.setNota("DIFERENTES FECHAS");
/* 174:173 */           this.servicioRegistroMovil.guardar(registroMovil);
/* 175:174 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { "" });
/* 176:    */         }
/* 177:    */       }
/* 178:179 */       pedido.setFecha(sdf.parse(request.getFecha()));
/* 179:180 */       if (request.getFechaDespacho() != null) {
/* 180:181 */         pedido.setFechaDespacho(sdf.parse(request.getFechaDespacho()));
/* 181:    */       }
/* 182:184 */       pedido.setEstado(Estado.ELABORADO);
/* 183:185 */       System.out.println("///////////////// idEmpresa: " + request.getCliente());
/* 184:186 */       Empresa empresa = this.servicioEmpresa.buscarPorId(request.getCliente());
/* 185:187 */       empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 186:188 */       pedido.setEmpresa(empresa);
/* 187:189 */       Empresa empresaDatos = pedido.getEmpresa();
/* 188:190 */       if (null != request.getSubcliente())
/* 189:    */       {
/* 190:191 */         Subempresa subempresa = this.subempresaDao.buscarSubEmpresaPorIdEmpresaAndIdSubCliente(request.getCliente().intValue(), request.getSubcliente().intValue());
/* 191:    */         
/* 192:193 */         pedido.setSubempresa(subempresa);
/* 193:194 */         empresaDatos = pedido.getSubempresa().getEmpresa();
/* 194:    */       }
/* 195:196 */       pedido.setAgenteComercial(usuario);
/* 196:197 */       pedido.setUsuarioCreacion(usuario.getNombreUsuario());
/* 197:198 */       pedido.setZona(empresaDatos.getCliente().getZona());
/* 198:199 */       CondicionPago condicionPago = null;
/* 199:200 */       if ((request.getIdCondicionPago() != null) && (request.getIdCondicionPago().intValue() != 0)) {
/* 200:201 */         condicionPago = this.servicioCondicionPago.buscarPorId(request.getIdCondicionPago());
/* 201:    */       } else {
/* 202:203 */         condicionPago = empresaDatos.getCliente().getCondicionPago();
/* 203:    */       }
/* 204:205 */       pedido.setCondicionPago(condicionPago);
/* 205:206 */       Integer numeroCuotas = Integer.valueOf(1);
/* 206:207 */       if ((request.getNumeroCuotas() != null) && (request.getNumeroCuotas().intValue() != 0)) {
/* 207:208 */         numeroCuotas = request.getNumeroCuotas();
/* 208:    */       } else {
/* 209:210 */         numeroCuotas = Integer.valueOf(empresaDatos.getCliente().getNumeroCuotas());
/* 210:    */       }
/* 211:212 */       pedido.setNumeroCuotas(numeroCuotas.intValue());
/* 212:213 */       pedido.setTransportista(empresaDatos.getCliente().getTransportista());
/* 213:215 */       for (Iterator localIterator = this.servicioEmpresa.obtenerListaComboDirecciones(empresaDatos.getId()).iterator(); localIterator.hasNext();)
/* 214:    */       {
/* 215:215 */         de = (DireccionEmpresa)localIterator.next();
/* 216:216 */         if (de.isIndicadorDireccionPrincipal())
/* 217:    */         {
/* 218:217 */           pedido.setDireccionEmpresa(de);
/* 219:218 */           break;
/* 220:    */         }
/* 221:    */       }
/* 222:    */       DireccionEmpresa de;
/* 223:222 */       Object listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PEDIDO_CLIENTE, pedido
/* 224:223 */         .getIdOrganizacion());
/* 225:224 */       if ((listaDocumento != null) && (!((List)listaDocumento).isEmpty())) {
/* 226:225 */         pedido.setDocumento((Documento)((List)listaDocumento).get(0));
/* 227:    */       }
/* 228:228 */       for (DetallePedidoRequestDto detreq : request.getListaDetallePedido())
/* 229:    */       {
/* 230:229 */         DetallePedidoCliente detalle = new DetallePedidoCliente();
/* 231:230 */         detalle.setPedidoCliente(pedido);
/* 232:231 */         detalle.setCantidad(detreq.getCantidad());
/* 233:232 */         detalle.setPrecio(detreq.getPrecio());
/* 234:    */         
/* 235:    */ 
/* 236:235 */         List<String> lcampos = new ArrayList();
/* 237:236 */         lcampos.add("categoriaImpuesto");
/* 238:237 */         detalle.setProducto(this.servicioProducto.cargaDetalle(detreq.getProducto().intValue()));
/* 239:238 */         detalle.setIndicadorPorcentajeIce(detalle.getProducto().isIndicadorPorcentajeIce());
/* 240:239 */         detalle.setCodigoIce(detalle.getProducto().getCodigoIce());
/* 241:240 */         detalle.setIce(detalle.getProducto().getIce());
/* 242:    */         
/* 243:242 */         Unidad unidadVenta = detalle.getProducto().getUnidadVenta() == null ? detalle.getProducto().getUnidad() : detalle.getProducto().getUnidadVenta();
/* 244:243 */         detalle.setUnidadVenta(unidadVenta);
/* 245:244 */         if (null != detreq.getDescuento())
/* 246:    */         {
/* 247:245 */           detalle.setDescuento(detreq.getDescuento());
/* 248:246 */           if (detalle.getDescuento().compareTo(BigDecimal.ZERO) > 0)
/* 249:    */           {
/* 250:250 */             BigDecimal c = detalle.getCantidad().multiply(detalle.getDescuento()).multiply(new BigDecimal(100)).divide(detalle.getPrecioLinea(), 4, RoundingMode.HALF_UP);
/* 251:251 */             detalle.setPorcentajeDescuento(c);
/* 252:    */           }
/* 253:    */         }
/* 254:254 */         if (pedido.getEmpresa().getCliente().isExcentoImpuestos()) {
/* 255:255 */           detalle.setIndicadorImpuesto(false);
/* 256:    */         } else {
/* 257:257 */           detalle.setIndicadorImpuesto(detalle.getProducto().isIndicadorImpuestos());
/* 258:    */         }
/* 259:260 */         if (detalle.isIndicadorImpuesto()) {
/* 260:261 */           this.servicioPedidoCliente.obtenerImpuestosProductos(detalle.getProducto(), detalle);
/* 261:    */         }
/* 262:263 */         pedido.getListaDetallePedidoCliente().add(detalle);
/* 263:    */       }
/* 264:265 */       this.servicioPedidoCliente.totalizar(pedido);
/* 265:266 */       this.servicioPedidoCliente.guardar(pedido);
/* 266:267 */       response.setSuccsess(true);
/* 267:268 */       ProcesoGenericoResponseDto pedidoResponse = new ProcesoGenericoResponseDto();
/* 268:269 */       pedidoResponse.setIdAs2(Integer.valueOf(pedido.getId()));
/* 269:270 */       pedidoResponse.setEstado(pedido.getEstado().toString());
/* 270:271 */       pedidoResponse.setIdentificacionDispositivo(request.getCodigoMovil());
/* 271:272 */       pedidoResponse.setNumeroAs2(pedido.getNumero());
/* 272:273 */       response.setResponse(pedidoResponse);
/* 273:    */       
/* 274:275 */       registroMovil.setNumeroAs2(pedido.getNumero());
/* 275:276 */       registroMovil.setNota("REGISTRADO");
/* 276:277 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 277:278 */       return response;
/* 278:    */     }
/* 279:    */     catch (ExcepcionAS2Ventas e)
/* 280:    */     {
/* 281:280 */       e.printStackTrace();
/* 282:281 */       if (request.getUrlApp() != null) {
/* 283:282 */         error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 284:    */       } else {
/* 285:284 */         error = e.getCodigoExcepcion();
/* 286:    */       }
/* 287:    */     }
/* 288:    */     catch (ExcepcionAS2Financiero e)
/* 289:    */     {
/* 290:287 */       e.printStackTrace();
/* 291:288 */       if (request.getUrlApp() != null) {
/* 292:289 */         error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 293:    */       } else {
/* 294:291 */         error = e.getCodigoExcepcion();
/* 295:    */       }
/* 296:    */     }
/* 297:    */     catch (ExcepcionAS2 e)
/* 298:    */     {
/* 299:294 */       e.printStackTrace();
/* 300:295 */       if (request.getUrlApp() != null) {
/* 301:296 */         error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 302:    */       } else {
/* 303:298 */         error = e.getCodigoExcepcion();
/* 304:    */       }
/* 305:    */     }
/* 306:    */     catch (ParseException e)
/* 307:    */     {
/* 308:301 */       e.printStackTrace();
/* 309:302 */       error = "Mal formado el formato de la fecha";
/* 310:    */     }
/* 311:305 */     registroMovil.setNota(error);
/* 312:306 */     this.servicioRegistroMovil.guardar(registroMovil);
/* 313:309 */     if (request.getUrlApp() != null)
/* 314:    */     {
/* 315:310 */       response.setSuccsess(false);
/* 316:311 */       response.setError(error);
/* 317:    */       
/* 318:313 */       return response;
/* 319:    */     }
/* 320:315 */     throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { error });
/* 321:    */   }
/* 322:    */   
/* 323:    */   @POST
/* 324:    */   @Path("/consultarPedido")
/* 325:    */   @Consumes({"application/json"})
/* 326:    */   @Produces({"application/json"})
/* 327:    */   public EstadoPedidoResponseDto consultarPedido(ConsultarPedidoRequestDto request)
/* 328:    */     throws AS2Exception
/* 329:    */   {
/* 330:    */     try
/* 331:    */     {
/* 332:325 */       PedidoCliente pedido = null;
/* 333:326 */       EstadoPedidoResponseDto response = new EstadoPedidoResponseDto();
/* 334:327 */       response.setEstado("SIN_ENVIAR");
/* 335:328 */       response.setNumero("");
/* 336:329 */       Map<String, String> filters = new HashMap();
/* 337:330 */       filters.put("codigoMovil", request.getCodigoMovil());
/* 338:    */       
/* 339:332 */       List<PedidoCliente> listaPedido = this.servicioPedidoCliente.obtenerListaCombo(null, true, filters);
/* 340:333 */       if (listaPedido.size() > 0)
/* 341:    */       {
/* 342:334 */         pedido = (PedidoCliente)listaPedido.get(0);
/* 343:335 */         response.setEstado(pedido.getEstado().toString());
/* 344:336 */         response.setNumero(pedido.getNumero());
/* 345:337 */         response.setIdPedido(Integer.valueOf(pedido.getIdPedidoCliente()));
/* 346:    */       }
/* 347:339 */       return response;
/* 348:    */     }
/* 349:    */     catch (Exception e)
/* 350:    */     {
/* 351:342 */       e.printStackTrace();
/* 352:343 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { "" });
/* 353:    */     }
/* 354:    */   }
/* 355:    */   
/* 356:    */   @POST
/* 357:    */   @Path("/anularPedido")
/* 358:    */   @Consumes({"application/json"})
/* 359:    */   @Produces({"application/json"})
/* 360:    */   public Boolean anularPedido(ConsultarPedidoRequestDto request)
/* 361:    */     throws AS2Exception
/* 362:    */   {
/* 363:352 */     PedidoCliente pedidoAnulado = new PedidoCliente();
/* 364:    */     
/* 365:354 */     RegistroMovil registroMovil = new RegistroMovil();
/* 366:355 */     registroMovil.setFecha(new Date());
/* 367:356 */     registroMovil.setCodigoMovil(request.getCodigoMovil());
/* 368:357 */     registroMovil.setDocumentoBase(DocumentoBase.PEDIDO_CLIENTE);
/* 369:    */     try
/* 370:    */     {
/* 371:361 */       pedidoAnulado = this.servicioPedidoCliente.buscarPorId(request.getIdPedidoCliente());
/* 372:    */       
/* 373:363 */       registroMovil.setIdOrganizacion(pedidoAnulado.getIdOrganizacion());
/* 374:364 */       registroMovil.setNumeroAs2(pedidoAnulado.getNumero());
/* 375:365 */       registroMovil.setUsuario(pedidoAnulado.getUsuarioCreacion());
/* 376:    */     }
/* 377:    */     catch (ExcepcionAS2 e)
/* 378:    */     {
/* 379:367 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { request.getIdPedidoCliente() + "" });
/* 380:    */     }
/* 381:370 */     if (!pedidoAnulado.getCodigoMovil().equals(request.getCodigoMovil()))
/* 382:    */     {
/* 383:371 */       registroMovil.setNota("ANULAR: NO COINCIDEN LOS CODIGOS");
/* 384:372 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 385:373 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { request.getIdPedidoCliente() + "" });
/* 386:    */     }
/* 387:    */     try
/* 388:    */     {
/* 389:378 */       if (pedidoAnulado.getEstado().equals(Estado.ANULADO))
/* 390:    */       {
/* 391:379 */         registroMovil.setNota(Estado.ANULADO.toString());
/* 392:380 */         this.servicioRegistroMovil.guardar(registroMovil);
/* 393:381 */         return Boolean.valueOf(true);
/* 394:    */       }
/* 395:384 */       this.servicioPedidoCliente.anular(pedidoAnulado);
/* 396:385 */       registroMovil.setNota(Estado.ANULADO.toString());
/* 397:386 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 398:387 */       return Boolean.valueOf(true);
/* 399:    */     }
/* 400:    */     catch (Exception e)
/* 401:    */     {
/* 402:389 */       e.printStackTrace();
/* 403:390 */       registroMovil.setNota("ANULAR: ERROR:" + e.getMessage());
/* 404:391 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 405:392 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { e.getMessage() });
/* 406:    */     }
/* 407:    */   }
/* 408:    */   
/* 409:    */   @POST
/* 410:    */   @Path("/obtenerPedidosSugeridosPorCliente")
/* 411:    */   @Consumes({"application/json"})
/* 412:    */   @Produces({"application/json"})
/* 413:    */   public List<PedidoSugeridoResponseDto> obtenerPedidosSugeridosPorCliente(PedidoSugeridoRequestDto request)
/* 414:    */     throws AS2Exception
/* 415:    */   {
/* 416:    */     try
/* 417:    */     {
/* 418:403 */       Empresa empresa = this.servicioEmpresa.buscarPorId(request.getIdEmpresa());
/* 419:404 */       empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 420:405 */       Subempresa subempresa = null;
/* 421:406 */       if (null != request.getIdEmpresaSubCliente()) {
/* 422:407 */         subempresa = this.subempresaDao.buscarSubEmpresaPorIdEmpresaAndIdSubCliente(request.getIdEmpresa().intValue(), request.getIdEmpresaSubCliente().intValue());
/* 423:    */       }
/* 424:410 */       List<PedidoSugeridoResponseDto> response = new ArrayList();
/* 425:411 */       List<Producto> listaProducto = this.servicioPedidoCliente.cargarPedidoSugerido(empresa.getIdOrganizacion(), empresa, subempresa);
/* 426:412 */       for (Producto producto : listaProducto)
/* 427:    */       {
/* 428:413 */         PedidoSugeridoResponseDto detalle = new PedidoSugeridoResponseDto();
/* 429:414 */         detalle.setIdProducto(Integer.valueOf(producto.getIdProducto()));
/* 430:415 */         detalle.setCantidad(producto.getTraCantidad());
/* 431:416 */         response.add(detalle);
/* 432:    */       }
/* 433:419 */       return response;
/* 434:    */     }
/* 435:    */     catch (Exception e)
/* 436:    */     {
/* 437:422 */       e.printStackTrace();
/* 438:423 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { "" });
/* 439:    */     }
/* 440:    */   }
/* 441:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.rest.PedidoServicioRest
 * JD-Core Version:    0.7.0.1
 */