/*   1:    */ package com.asinfo.as2.rs.inventario.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.dao.SubempresaDao;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Bodega;
/*  10:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  11:    */ import com.asinfo.as2.entities.DetallePreDevolucionCliente;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  15:    */ import com.asinfo.as2.entities.Impuesto;
/*  16:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*  17:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  18:    */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*  19:    */ import com.asinfo.as2.entities.Producto;
/*  20:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  21:    */ import com.asinfo.as2.entities.RegistroMovil;
/*  22:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  23:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  24:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  25:    */ import com.asinfo.as2.enumeraciones.TipoImpuestoEnum;
/*  26:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  27:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  28:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  29:    */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*  30:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  31:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  32:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  33:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  34:    */ import com.asinfo.as2.rs.ventas.dto.ConsultarFacturaClienteRequestDto;
/*  35:    */ import com.asinfo.as2.rs.ventas.dto.DetalleFacturaClienteResponseDto;
/*  36:    */ import com.asinfo.as2.rs.ventas.dto.EstadoFacturaClienteResponseDto;
/*  37:    */ import com.asinfo.as2.rs.ventas.dto.FacturaClienteResponseDto;
/*  38:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  39:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  40:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  41:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  42:    */ import java.math.BigDecimal;
/*  43:    */ import java.math.RoundingMode;
/*  44:    */ import java.text.ParseException;
/*  45:    */ import java.text.SimpleDateFormat;
/*  46:    */ import java.util.Date;
/*  47:    */ import java.util.HashMap;
/*  48:    */ import java.util.Iterator;
/*  49:    */ import java.util.List;
/*  50:    */ import java.util.Map;
/*  51:    */ import javax.ejb.EJB;
/*  52:    */ import javax.ws.rs.Consumes;
/*  53:    */ import javax.ws.rs.POST;
/*  54:    */ import javax.ws.rs.Path;
/*  55:    */ import javax.ws.rs.Produces;
/*  56:    */ 
/*  57:    */ @Path("/notaCreditoCliente")
/*  58:    */ public class NotaCreditoServicioRest
/*  59:    */ {
/*  60:    */   @EJB
/*  61:    */   private transient ServicioProducto servicioProducto;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioUnidad servicioUnidad;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioEmpresa servicioEmpresa;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioSucursal servicioSucursal;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioUsuario servicioUsuario;
/*  70:    */   @EJB
/*  71:    */   private transient ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioGenerico<PreDevolucionCliente> servicioPreDevolucionCliente;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioGenerico<DetallePreDevolucionCliente> servicioDetallePreDevolucionCliente;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  78:    */   @EJB
/*  79:    */   private transient SubempresaDao subempresaDao;
/*  80:    */   @EJB
/*  81:    */   private transient ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  82:    */   @EJB
/*  83:    */   private transient ServicioGenerico<RegistroMovil> servicioRegistroMovil;
/*  84:    */   @EJB
/*  85:    */   private transient ServicioDocumento servicioDocumento;
/*  86:    */   @EJB
/*  87:    */   private transient ServicioBodega servicioBodega;
/*  88:    */   @EJB
/*  89:    */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  90:    */   @EJB
/*  91:    */   private transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  92: 94 */   private LanguageController languageController = new LanguageController();
/*  93:    */   
/*  94:    */   public LanguageController getLanguageController()
/*  95:    */   {
/*  96: 97 */     return this.languageController;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setLanguageController(LanguageController languageController)
/* 100:    */   {
/* 101:101 */     this.languageController = languageController;
/* 102:    */   }
/* 103:    */   
/* 104:    */   @POST
/* 105:    */   @Path("/crearNotaCreditoCliente")
/* 106:    */   @Consumes({"application/json"})
/* 107:    */   @Produces({"application/json"})
/* 108:    */   public ProcesosResponseDto crearNotaCredito(FacturaClienteResponseDto request)
/* 109:    */     throws AS2Exception
/* 110:    */   {
/* 111:109 */     String codigoMovil = request.getCodigoMovil();
/* 112:110 */     if ((request.getIdDispositivoSincronizacion() != null) && (request.getCodigoMovil() == null)) {
/* 113:111 */       codigoMovil = "POS: " + request.getIdSucursal() + ": " + request.getIdDispositivoSincronizacion();
/* 114:    */     }
/* 115:113 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 116:114 */     String error = null;
/* 117:115 */     this.languageController.setAccesoWeb(false);
/* 118:116 */     this.languageController.setUrlHost(request.getUrlApp());
/* 119:117 */     FacturaCliente notaCreditoCliente = new FacturaCliente();
/* 120:118 */     RegistroMovil registroMovil = new RegistroMovil();
/* 121:119 */     registroMovil.setFecha(new Date());
/* 122:120 */     registroMovil.setCodigoMovil(codigoMovil);
/* 123:    */     try
/* 124:    */     {
/* 125:123 */       Map<String, String> filtrosFactura = new HashMap();
/* 126:124 */       filtrosFactura.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 127:125 */       filtrosFactura.put("idDispositivoSincronizacion", request.getIdDispositivoSincronizacion() + "");
/* 128:126 */       filtrosFactura.put("empresa.idEmpresa", request.getIdEmpresa() + "");
/* 129:127 */       filtrosFactura.put("facturaClientePadre.idFacturaCliente", request.getIdFacturaPadre() + "");
/* 130:128 */       List<FacturaCliente> listaFactura = this.servicioFacturaCliente.obtenerListaCombo("fecha", false, filtrosFactura);
/* 131:    */       DocumentoBase documentoBase;
/* 132:    */       List<Documento> listaDocumento;
/* 133:129 */       if (!listaFactura.isEmpty())
/* 134:    */       {
/* 135:130 */         notaCreditoCliente = this.servicioFacturaCliente.cargarDetalle(((FacturaCliente)listaFactura.get(0)).getId());
/* 136:    */       }
/* 137:    */       else
/* 138:    */       {
/* 139:133 */         documentoBase = null;
/* 140:134 */         Documento documento = null;
/* 141:137 */         if ((request.getIndicadorDevolucion() != null) && (request.getIndicadorDevolucion().booleanValue())) {
/* 142:138 */           documentoBase = DocumentoBase.DEVOLUCION_CLIENTE;
/* 143:139 */         } else if ((request.getIndicadorDevolucion() != null) && (!request.getIndicadorDevolucion().booleanValue())) {
/* 144:140 */           documentoBase = DocumentoBase.NOTA_CREDITO_CLIENTE;
/* 145:    */         }
/* 146:142 */         listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(documentoBase, request.getIdOrganizacion());
/* 147:143 */         if (listaDocumento.size() > 0)
/* 148:    */         {
/* 149:144 */           documento = (Documento)listaDocumento.get(0);
/* 150:    */         }
/* 151:    */         else
/* 152:    */         {
/* 153:146 */           registroMovil.setNota("ERROR: No existe documento");
/* 154:147 */           this.servicioRegistroMovil.guardar(registroMovil);
/* 155:148 */           throw new AS2Exception("msg_error_no_existe_documento_nota_credito", new String[] { "" });
/* 156:    */         }
/* 157:151 */         registroMovil.setDocumentoBase(documentoBase);
/* 158:152 */         registroMovil.setIdOrganizacion(request.getIdOrganizacion());
/* 159:153 */         registroMovil.setIdSucursal(request.getIdSucursal());
/* 160:    */         
/* 161:155 */         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 162:    */         
/* 163:157 */         registroMovil.setUsuario(request.getNombreUsuarioCreacion());
/* 164:    */         
/* 165:    */ 
/* 166:160 */         Map<String, String> filtro = new HashMap();
/* 167:161 */         filtro.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 168:162 */         filtro.put("activo", "true");
/* 169:163 */         MotivoNotaCreditoCliente motivoNotaCredito = null;
/* 170:164 */         if ((request.getIdMotivoNotaCreditoCliente() != null) && (request.getIdMotivoNotaCreditoCliente().intValue() != 0))
/* 171:    */         {
/* 172:165 */           motivoNotaCredito = this.servicioMotivoNotaCreditoCliente.buscarPorId(request.getIdMotivoNotaCreditoCliente().intValue());
/* 173:    */         }
/* 174:    */         else
/* 175:    */         {
/* 176:167 */           List<MotivoNotaCreditoCliente> listaMotivoNotaCredito = this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("predeterminado", false, filtro);
/* 177:169 */           if (listaMotivoNotaCredito.size() > 0) {
/* 178:170 */             motivoNotaCredito = (MotivoNotaCreditoCliente)listaMotivoNotaCredito.get(0);
/* 179:    */           }
/* 180:    */         }
/* 181:173 */         if (motivoNotaCredito == null) {
/* 182:174 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { MotivoNotaCreditoCliente.class.getSimpleName() });
/* 183:    */         }
/* 184:178 */         FacturaCliente facturaClientePadre = this.servicioFacturaCliente.cargarDetalle(request.getIdFacturaPadre().intValue());
/* 185:179 */         Empresa empresa = facturaClientePadre.getEmpresa();
/* 186:180 */         if (!request.getIdEmpresa().equals(Integer.valueOf(empresa.getId()))) {
/* 187:182 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] {Empresa.class.getSimpleName() + ": " + request.getIdEmpresa() + " !=" + empresa.getId() });
/* 188:    */         }
/* 189:185 */         Map<String, String> filters = new HashMap();
/* 190:186 */         filters.put("idOrganizacion", "" + request.getIdOrganizacion());
/* 191:187 */         filters.put("idPuntoDeVenta", request.getIdPuntoVenta() + "");
/* 192:188 */         filters.put("sucursal.idSucursal", "" + request.getIdSucursal());
/* 193:    */         
/* 194:190 */         PuntoDeVenta puntoDeVenta = this.servicioPuntoDeVenta.buscarPuntoDeVenta(filters);
/* 195:    */         
/* 196:192 */         notaCreditoCliente.setIdOrganizacion(request.getIdOrganizacion());
/* 197:193 */         notaCreditoCliente.setSucursal(this.servicioSucursal.buscarPorId(Integer.valueOf(request.getIdSucursal())));
/* 198:194 */         notaCreditoCliente.setFecha(sdf.parse(request.getFecha()));
/* 199:195 */         notaCreditoCliente.setMotivoNotaCreditoCliente(motivoNotaCredito);
/* 200:196 */         notaCreditoCliente.setEstado(Estado.ELABORADO);
/* 201:197 */         notaCreditoCliente.setEmpresa(empresa);
/* 202:198 */         notaCreditoCliente.setCodigoMovil(codigoMovil);
/* 203:199 */         notaCreditoCliente.setFacturaClientePadre(facturaClientePadre);
/* 204:200 */         notaCreditoCliente.setDireccionEmpresa(facturaClientePadre.getDireccionEmpresa());
/* 205:201 */         notaCreditoCliente.setNumero("");
/* 206:202 */         notaCreditoCliente.setDocumento(documento);
/* 207:203 */         notaCreditoCliente.setIdDispositivoSincronizacion(request.getIdDispositivoSincronizacion());
/* 208:204 */         notaCreditoCliente.setDescripcion(request.getDescripcion());
/* 209:205 */         notaCreditoCliente.setUsuarioCreacion(request.getNombreUsuarioCreacion());
/* 210:206 */         notaCreditoCliente.setSubempresa(facturaClientePadre.getSubempresa());
/* 211:207 */         notaCreditoCliente.setNumeroCuotas(1);
/* 212:208 */         FacturaClienteSRI facturaClienteSRI = new FacturaClienteSRI();
/* 213:209 */         facturaClienteSRI.setIdOrganizacion(request.getIdOrganizacion());
/* 214:210 */         facturaClienteSRI.setEstado(Estado.ELABORADO);
/* 215:211 */         facturaClienteSRI.setNumero("0");
/* 216:212 */         facturaClienteSRI.setFacturaCliente(notaCreditoCliente);
/* 217:213 */         facturaClienteSRI.setPuntoEmision(puntoDeVenta.getCodigo());
/* 218:214 */         notaCreditoCliente.setFacturaClienteSRI(facturaClienteSRI);
/* 219:    */         
/* 220:216 */         notaCreditoCliente.setEmail(this.servicioEmpresa.cargarMails(empresa, notaCreditoCliente.getDocumento().getDocumentoBase()));
/* 221:    */         
/* 222:218 */         this.servicioDocumento.cargarSecuencia(notaCreditoCliente.getDocumento(), puntoDeVenta, notaCreditoCliente.getFecha());
/* 223:    */         
/* 224:    */ 
/* 225:221 */         notaCreditoCliente.setNumero(request.getNumero());
/* 226:    */         
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:227 */         Map<Integer, DetalleFacturaCliente> mapaDetalleFacturaPadre = new HashMap();
/* 232:228 */         for (DetalleFacturaCliente detalle : facturaClientePadre.getListaDetalleFacturaCliente()) {
/* 233:229 */           mapaDetalleFacturaPadre.put(Integer.valueOf(detalle.getId()), detalle);
/* 234:    */         }
/* 235:232 */         for (DetalleFacturaClienteResponseDto detalleResponse : request.getDetalleFacturaClienteResponse())
/* 236:    */         {
/* 237:233 */           Producto producto = this.servicioProducto.cargaDetalle(detalleResponse.getIdProducto().intValue());
/* 238:234 */           DetalleFacturaCliente detalle = new DetalleFacturaCliente();
/* 239:235 */           detalle.setIdOrganizacion(request.getIdOrganizacion());
/* 240:236 */           detalle.setIdSucursal(request.getIdSucursal());
/* 241:237 */           detalle.setFacturaCliente(notaCreditoCliente);
/* 242:238 */           detalle.setCantidad(detalleResponse.getCantidad());
/* 243:239 */           detalle.setPrecio(detalleResponse.getPrecio());
/* 244:240 */           detalle.setProducto(producto);
/* 245:241 */           detalle.setPorcentajeDescuento(detalleResponse.getPorcentajeDescuento().setScale(4, RoundingMode.HALF_UP));
/* 246:242 */           detalle.setIdDispositivoSincronizacion(detalleResponse.getIdDispositivoSincronizacion());
/* 247:244 */           if ((request.getIndicadorDevolucion().booleanValue()) || (producto.isIndicadorPorcentajeIce()))
/* 248:    */           {
/* 249:245 */             detalle.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 250:246 */             detalle.setCodigoIce(producto.getCodigoIce());
/* 251:247 */             detalle.setIce(producto.getIce());
/* 252:    */           }
/* 253:249 */           DetalleFacturaCliente detallePadre = null;
/* 254:250 */           if (detalleResponse.getIdDetalleFacturaClientePadre() != null) {
/* 255:251 */             detallePadre = (DetalleFacturaCliente)mapaDetalleFacturaPadre.get(detalleResponse.getIdDetalleFacturaClientePadre());
/* 256:    */           }
/* 257:253 */           detalle.setDetalleFacturaClientePadre(detallePadre);
/* 258:254 */           Bodega bodega = null;
/* 259:255 */           if ((detalleResponse.getIdBodega() != null) && (detalleResponse.getIdBodega().intValue() != 0))
/* 260:    */           {
/* 261:256 */             bodega = new Bodega();
/* 262:257 */             bodega.setIdBodega(detalleResponse.getIdBodega().intValue());
/* 263:258 */             bodega = this.servicioBodega.cargarDetalle(bodega);
/* 264:    */           }
/* 265:259 */           else if (detallePadre != null)
/* 266:    */           {
/* 267:260 */             bodega = detallePadre.getBodega();
/* 268:    */           }
/* 269:    */           else
/* 270:    */           {
/* 271:262 */             bodega = this.servicioProducto.obtenerBodegaTrabajoProducto(producto, Integer.valueOf(request.getIdSucursal()));
/* 272:    */           }
/* 273:264 */           detalle.setBodega(bodega);
/* 274:265 */           detalle.setUnidadVenta(detalle.getProducto().getUnidadVenta());
/* 275:266 */           detalle.setPrecioLinea(detalle.getCantidad().multiply(detalle.getPrecio()).setScale(2, RoundingMode.HALF_UP));
/* 276:    */           
/* 277:268 */           detalle.setIndicadorImpuesto(detalle.getProducto().isIndicadorImpuestos());
/* 278:270 */           if (detalle.isIndicadorImpuesto()) {
/* 279:271 */             this.servicioFacturaCliente.obtenerImpuestosProductos(detalle.getProducto(), detalle);
/* 280:    */           }
/* 281:275 */           if (!request.getIndicadorDevolucion().booleanValue()) {
/* 282:276 */             for (int i = 0; i < detalle.getListaImpuestoProductoFacturaCliente().size(); i++) {
/* 283:278 */               if (((ImpuestoProductoFacturaCliente)detalle.getListaImpuestoProductoFacturaCliente().get(i)).getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_CANTIDAD_UNIDADES))
/* 284:    */               {
/* 285:279 */                 detalle.getListaImpuestoProductoFacturaCliente().remove(i);
/* 286:280 */                 i--;
/* 287:    */               }
/* 288:    */             }
/* 289:    */           }
/* 290:285 */           notaCreditoCliente.getListaDetalleFacturaCliente().add(detalle);
/* 291:    */         }
/* 292:288 */         this.servicioNotaCreditoCliente.totalizar(notaCreditoCliente);
/* 293:    */         
/* 294:290 */         this.servicioNotaCreditoCliente.guardar(notaCreditoCliente);
/* 295:    */       }
/* 296:293 */       if ((request.getIdDispositivoSincronizacion() != null) && (request.getCodigoMovil() == null))
/* 297:    */       {
/* 298:294 */         request.setIdFacturaCliente(Integer.valueOf(notaCreditoCliente.getId()));
/* 299:295 */         request.setNumero(notaCreditoCliente.getNumero());
/* 300:296 */         for (documentoBase = notaCreditoCliente.getListaDetalleFacturaCliente().iterator(); documentoBase.hasNext();)
/* 301:    */         {
/* 302:296 */           detalle = (DetalleFacturaCliente)documentoBase.next();
/* 303:297 */           for (DetalleFacturaClienteResponseDto detalleFacturaClienteResponseDto : request.getDetalleFacturaClienteResponse()) {
/* 304:298 */             if (detalle.getIdDispositivoSincronizacion().equals(detalleFacturaClienteResponseDto.getIdDispositivoSincronizacion())) {
/* 305:299 */               detalleFacturaClienteResponseDto.setIdDetalleFacturaCliente(detalle.getId());
/* 306:    */             }
/* 307:    */           }
/* 308:    */         }
/* 309:    */         DetalleFacturaCliente detalle;
/* 310:304 */         response.setResponse(request);
/* 311:    */       }
/* 312:307 */       response.setSuccsess(true);
/* 313:308 */       return response;
/* 314:    */     }
/* 315:    */     catch (AS2Exception e)
/* 316:    */     {
/* 317:310 */       e.printStackTrace();
/* 318:311 */       if (request.getUrlApp() != null) {
/* 319:312 */         error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 320:    */       } else {
/* 321:314 */         error = e.getCodigoExcepcion();
/* 322:    */       }
/* 323:    */     }
/* 324:    */     catch (ParseException e)
/* 325:    */     {
/* 326:317 */       e.printStackTrace();
/* 327:318 */       error = "Mal formado el formato de la fecha";
/* 328:    */     }
/* 329:    */     catch (ExcepcionAS2 e)
/* 330:    */     {
/* 331:320 */       e.printStackTrace();
/* 332:321 */       if (request.getUrlApp() != null) {
/* 333:322 */         error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 334:    */       } else {
/* 335:324 */         error = e.getCodigoExcepcion() + "|" + e.getMessage();
/* 336:    */       }
/* 337:    */     }
/* 338:327 */     registroMovil.setNota(error);
/* 339:328 */     this.servicioRegistroMovil.guardar(registroMovil);
/* 340:    */     
/* 341:330 */     response.setSuccsess(false);
/* 342:331 */     response.setError(error);
/* 343:332 */     return response;
/* 344:    */   }
/* 345:    */   
/* 346:    */   @POST
/* 347:    */   @Path("/anularNotaCreditoCliente")
/* 348:    */   @Consumes({"application/json"})
/* 349:    */   @Produces({"application/json"})
/* 350:    */   public ProcesosResponseDto anularNotaCreditoCliente(ConsultarFacturaClienteRequestDto request)
/* 351:    */     throws AS2Exception
/* 352:    */   {
/* 353:340 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 354:341 */     String error = "";
/* 355:    */     try
/* 356:    */     {
/* 357:343 */       Map<String, String> filtros = new HashMap();
/* 358:344 */       filtros.put("idOrganizacion", "" + request.getIdOrganizacion());
/* 359:345 */       filtros.put("idFacturaCliente", "" + request.getIdFacturaCliente());
/* 360:    */       
/* 361:347 */       FacturaCliente notaCreditoCliente = this.servicioFacturaCliente.buscarFacturaCliente(filtros);
/* 362:348 */       if (!notaCreditoCliente.getEstado().equals(Estado.ANULADO)) {
/* 363:349 */         this.servicioNotaCreditoCliente.anular(notaCreditoCliente);
/* 364:    */       }
/* 365:352 */       response.setSuccsess(true);
/* 366:353 */       return response;
/* 367:    */     }
/* 368:    */     catch (ExcepcionAS2 e)
/* 369:    */     {
/* 370:355 */       e.printStackTrace();
/* 371:356 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 372:    */     }
/* 373:    */     catch (Exception e)
/* 374:    */     {
/* 375:358 */       e.printStackTrace();
/* 376:359 */       error = e.getMessage();
/* 377:    */     }
/* 378:361 */     response.setError(error);
/* 379:362 */     response.setSuccsess(false);
/* 380:363 */     return response;
/* 381:    */   }
/* 382:    */   
/* 383:    */   @POST
/* 384:    */   @Path("/consultarNotaCreditoCliente")
/* 385:    */   @Consumes({"application/json"})
/* 386:    */   @Produces({"application/json"})
/* 387:    */   public EstadoFacturaClienteResponseDto consultarNotaCreditoCliente(ConsultarFacturaClienteRequestDto request)
/* 388:    */     throws AS2Exception
/* 389:    */   {
/* 390:371 */     EstadoFacturaClienteResponseDto estadoFacturaClienteResponse = new EstadoFacturaClienteResponseDto();
/* 391:    */     
/* 392:373 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy.mm:ss");
/* 393:    */     
/* 394:375 */     Map<String, String> filtros = new HashMap();
/* 395:376 */     filtros.put("idOrganizacion", "" + request.getIdOrganizacion());
/* 396:377 */     filtros.put("idFacturaCliente", "" + request.getIdFacturaCliente());
/* 397:    */     
/* 398:    */ 
/* 399:380 */     FacturaCliente facturaCliente = this.servicioFacturaCliente.cargarDetalle(request.getIdFacturaCliente().intValue(), false);
/* 400:382 */     if (facturaCliente != null)
/* 401:    */     {
/* 402:383 */       estadoFacturaClienteResponse.setNumero(facturaCliente.getNumero());
/* 403:384 */       estadoFacturaClienteResponse.setAutorizacion(facturaCliente.getFacturaClienteSRI().getAutorizacion());
/* 404:385 */       estadoFacturaClienteResponse.setEstado(facturaCliente.getEstado());
/* 405:386 */       if (facturaCliente.getFacturaClienteSRI().getFechaAutorizacion() != null) {
/* 406:387 */         estadoFacturaClienteResponse.setFechaAutorizacion(sdf.format(facturaCliente.getFacturaClienteSRI().getFechaAutorizacion()));
/* 407:    */       } else {
/* 408:389 */         estadoFacturaClienteResponse.setFechaAutorizacion(null);
/* 409:    */       }
/* 410:391 */       estadoFacturaClienteResponse.setClaveAcceso(facturaCliente.getFacturaClienteSRI().getClaveAcceso());
/* 411:392 */       estadoFacturaClienteResponse.setIdFacturaCliente(Integer.valueOf(facturaCliente.getIdFacturaCliente()));
/* 412:    */     }
/* 413:    */     else
/* 414:    */     {
/* 415:395 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201");
/* 416:    */     }
/* 417:397 */     return estadoFacturaClienteResponse;
/* 418:    */   }
/* 419:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.rest.NotaCreditoServicioRest
 * JD-Core Version:    0.7.0.1
 */