/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*  10:    */ import com.asinfo.as2.entities.Banco;
/*  11:    */ import com.asinfo.as2.entities.Caja;
/*  12:    */ import com.asinfo.as2.entities.Cobro;
/*  13:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  14:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  15:    */ import com.asinfo.as2.entities.DetalleCobro;
/*  16:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  17:    */ import com.asinfo.as2.entities.Documento;
/*  18:    */ import com.asinfo.as2.entities.Empresa;
/*  19:    */ import com.asinfo.as2.entities.FormaPago;
/*  20:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*  21:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  22:    */ import com.asinfo.as2.entities.RegistroMovil;
/*  23:    */ import com.asinfo.as2.entities.Sucursal;
/*  24:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*  25:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  26:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  27:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  28:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  29:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  30:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  31:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  33:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  34:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  35:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  36:    */ import com.asinfo.as2.rs.financiero.cobros.dto.ConsultarCobroRequestDto;
/*  37:    */ import com.asinfo.as2.rs.financiero.cobros.dto.CrearCobroRequestDto;
/*  38:    */ import com.asinfo.as2.rs.financiero.cobros.dto.DetalleCobroRequestDto;
/*  39:    */ import com.asinfo.as2.rs.financiero.cobros.dto.DetalleFormaCobroRequestDto;
/*  40:    */ import com.asinfo.as2.rs.financiero.cobros.dto.EstadoCobroResponseDto;
/*  41:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  42:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  43:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  44:    */ import java.text.ParseException;
/*  45:    */ import java.text.SimpleDateFormat;
/*  46:    */ import java.util.ArrayList;
/*  47:    */ import java.util.Date;
/*  48:    */ import java.util.HashMap;
/*  49:    */ import java.util.Iterator;
/*  50:    */ import java.util.List;
/*  51:    */ import java.util.Map;
/*  52:    */ import javax.ejb.EJB;
/*  53:    */ import javax.ws.rs.Consumes;
/*  54:    */ import javax.ws.rs.POST;
/*  55:    */ import javax.ws.rs.Path;
/*  56:    */ import javax.ws.rs.Produces;
/*  57:    */ 
/*  58:    */ @Path("/cobro")
/*  59:    */ public class CobroServicioRest
/*  60:    */ {
/*  61:    */   @EJB
/*  62:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  63:    */   @EJB
/*  64:    */   private transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  65:    */   @EJB
/*  66:    */   private transient ServicioCondicionPago servicioCondicionPago;
/*  67:    */   @EJB
/*  68:    */   private transient ServicioImpuesto servicioImpuesto;
/*  69:    */   @EJB
/*  70:    */   private transient ServicioCobro servicioCobro;
/*  71:    */   @EJB
/*  72:    */   private transient ServicioFormaPago servicioFormaPago;
/*  73:    */   @EJB
/*  74:    */   private transient ServicioEmpresa servicioEmpresa;
/*  75:    */   @EJB
/*  76:    */   private transient ServicioDocumento servicioDocumento;
/*  77:    */   @EJB
/*  78:    */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  79:    */   @EJB
/*  80:    */   private transient ServicioGenerico<Banco> servicioBanco;
/*  81:    */   @EJB
/*  82:    */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  83:    */   @EJB
/*  84:    */   private transient ServicioSucursal servicioSucursal;
/*  85:    */   @EJB
/*  86:    */   private transient ServicioUsuario servicioUsuario;
/*  87:    */   @EJB
/*  88:    */   private transient ServicioGenerico<RegistroMovil> servicioRegistroMovil;
/*  89:    */   @EJB
/*  90:    */   private transient ServicioGenerico<TarjetaCredito> servicioTarjetaCredito;
/*  91:    */   @EJB
/*  92:    */   private transient ServicioGenerico<PlanTarjetaCredito> servicioPlanTarjetaCredito;
/*  93: 94 */   private LanguageController languageController = new LanguageController();
/*  94:    */   
/*  95:    */   public LanguageController getLanguageController()
/*  96:    */   {
/*  97: 97 */     return this.languageController;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setLanguageController(LanguageController languageController)
/* 101:    */   {
/* 102:101 */     this.languageController = languageController;
/* 103:    */   }
/* 104:    */   
/* 105:    */   @POST
/* 106:    */   @Path("/crearCobro")
/* 107:    */   @Consumes({"application/json"})
/* 108:    */   @Produces({"application/json"})
/* 109:    */   public ProcesosResponseDto crearCobro(CrearCobroRequestDto request)
/* 110:    */     throws AS2Exception
/* 111:    */   {
/* 112:109 */     String codigoMovil = request.getCodigoMovil();
/* 113:110 */     if ((request.getIdDispositivoSincronizacion() != null) && (request.getCodigoMovil() == null)) {
/* 114:111 */       codigoMovil = "POS: " + request.getIdSucursal() + ": " + request.getIdDispositivoSincronizacion();
/* 115:    */     }
/* 116:113 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 117:114 */     String error = null;
/* 118:115 */     this.languageController.setAccesoWeb(false);
/* 119:116 */     this.languageController.setUrlHost(request.getUrlApp());
/* 120:    */     
/* 121:118 */     RegistroMovil registroMovil = new RegistroMovil();
/* 122:119 */     registroMovil.setFecha(new Date());
/* 123:120 */     registroMovil.setCodigoMovil(codigoMovil);
/* 124:121 */     registroMovil.setDocumentoBase(DocumentoBase.COBRO_CLIENTE);
/* 125:122 */     registroMovil.setIdOrganizacion(request.getIdOrganizacion().intValue());
/* 126:123 */     registroMovil.setIdSucursal(request.getIdSucursal().intValue());
/* 127:    */     try
/* 128:    */     {
/* 129:126 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 130:    */       
/* 131:128 */       int idOrganizacion = request.getIdOrganizacion().intValue();
/* 132:129 */       String nombreUsuario = request.getNombreUsuario();
/* 133:130 */       if (request.getIdUsuario() != null)
/* 134:    */       {
/* 135:131 */         EntidadUsuario usuario = this.servicioUsuario.buscarPorId(request.getIdUsuario());
/* 136:132 */         nombreUsuario = usuario.getNombreUsuario();
/* 137:    */       }
/* 138:134 */       registroMovil.setUsuario(nombreUsuario);
/* 139:135 */       Sucursal sucursal = this.servicioSucursal.buscarPorId(request.getIdSucursal());
/* 140:    */       
/* 141:137 */       Cobro cobro = new Cobro();
/* 142:138 */       cobro.setUsuarioCreacion(nombreUsuario);
/* 143:139 */       cobro.setIdOrganizacion(idOrganizacion);
/* 144:140 */       cobro.setValor(request.getValorTotal());
/* 145:141 */       cobro.setDescripcion(request.getNota());
/* 146:142 */       cobro.setSucursal(sucursal);
/* 147:143 */       cobro.setFecha(sdf.parse(request.getFecha()));
/* 148:144 */       cobro.setEstado(Estado.ELABORADO);
/* 149:145 */       cobro.setCodigoMovil(codigoMovil);
/* 150:146 */       cobro.setIdDispositivoSincronizacion(request.getIdDispositivoSincronizacion());
/* 151:    */       
/* 152:148 */       Empresa empresa = this.servicioEmpresa.buscarPorId(request.getIdCliente());
/* 153:149 */       empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 154:150 */       cobro.setEmpresa(empresa);
/* 155:    */       
/* 156:152 */       List<Documento> listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.COBRO_CLIENTE, cobro
/* 157:153 */         .getIdOrganizacion());
/* 158:154 */       if ((listaDocumento != null) && (!listaDocumento.isEmpty())) {
/* 159:155 */         cobro.setDocumento((Documento)listaDocumento.get(0));
/* 160:    */       }
/* 161:157 */       cobro.setListaDetalleCobro(new ArrayList());
/* 162:158 */       cobro.setListaDetalleFormaCobro(new ArrayList());
/* 163:159 */       PuntoDeVenta puntoVenta = this.servicioPuntoDeVenta.cargarDetalle(request.getIdPuntoVenta().intValue());
/* 164:160 */       for (DetalleCobroRequestDto detalle : request.getListaDetalleCobro())
/* 165:    */       {
/* 166:161 */         CuentaPorCobrar cuentaPorCobrar = this.servicioCobro.buscarCuentaPorCobrarPorId(detalle.getIdCuentaPorCobrar().intValue());
/* 167:    */         
/* 168:163 */         DetalleCobro detalleCobro = new DetalleCobro();
/* 169:164 */         detalleCobro.setUsuarioCreacion(nombreUsuario);
/* 170:165 */         detalleCobro.setCobro(cobro);
/* 171:166 */         detalleCobro.setValor(detalle.getValorCobro());
/* 172:167 */         detalleCobro.setCuentaPorCobrar(cuentaPorCobrar);
/* 173:168 */         detalleCobro.setIdOrganizacion(idOrganizacion);
/* 174:169 */         cobro.getListaDetalleCobro().add(detalleCobro);
/* 175:    */       }
/* 176:172 */       for (DetalleFormaCobroRequestDto detalle : request.getListaDetalleFormaCobro())
/* 177:    */       {
/* 178:173 */         detalleFormaCobro = new DetalleFormaCobro();
/* 179:174 */         detalleFormaCobro.setUsuarioCreacion(nombreUsuario);
/* 180:175 */         detalleFormaCobro.setIdOrganizacion(idOrganizacion);
/* 181:176 */         detalleFormaCobro.setCobro(cobro);
/* 182:177 */         detalleFormaCobro.setFormaPago(this.servicioFormaPago.buscarPorId(detalle.getIdFormaPago()));
/* 183:178 */         detalleFormaCobro.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(detalle.getIdCuentaOrganizacion().intValue()));
/* 184:179 */         detalleFormaCobro.setCuentaContableFormaCobro(detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/* 185:180 */         detalleFormaCobro.setValor(detalle.getValor());
/* 186:181 */         detalleFormaCobro.setIdDispositivoSincronizacion(detalle.getIdDispositivoSincronizacion());
/* 187:    */         
/* 188:183 */         detalleFormaCobro.setDocumentoReferencia(detalle.getDocumentoReferencia());
/* 189:185 */         if (!puntoVenta.getListaCaja().isEmpty()) {
/* 190:186 */           detalleFormaCobro.setCaja((Caja)puntoVenta.getListaCaja().get(0));
/* 191:    */         }
/* 192:188 */         if (detalle.getIdBancoOrigen() != null) {
/* 193:189 */           detalleFormaCobro.setBanco((Banco)this.servicioBanco.buscarPorId(Banco.class, detalle.getIdBancoOrigen()));
/* 194:    */         }
/* 195:193 */         if (detalleFormaCobro.getFormaPago().isIndicadorTarjetaCredito())
/* 196:    */         {
/* 197:194 */           detalleFormaCobro.setLote(detalle.getLote());
/* 198:195 */           detalleFormaCobro.setNumeroTarjeta(detalle.getNumeroTarjeta());
/* 199:196 */           detalleFormaCobro.setInteres(detalle.getInteres());
/* 200:197 */           detalleFormaCobro.setTarjetaCredito((TarjetaCredito)this.servicioTarjetaCredito.buscarPorId(TarjetaCredito.class, detalle.getIdTarjetaCredito()));
/* 201:198 */           detalleFormaCobro.setPlanTarjetaCredito((PlanTarjetaCredito)this.servicioPlanTarjetaCredito.buscarPorId(PlanTarjetaCredito.class, detalle
/* 202:199 */             .getIdPlanTarjetaCredito()));
/* 203:    */         }
/* 204:202 */         cobro.getListaDetalleFormaCobro().add(detalleFormaCobro);
/* 205:    */       }
/* 206:    */       DetalleFormaCobro detalleFormaCobro;
/* 207:205 */       this.servicioCobro.guardar(cobro);
/* 208:208 */       if (request.getIdDispositivoSincronizacion() != null)
/* 209:    */       {
/* 210:209 */         request.setIdCobro(Integer.valueOf(cobro.getId()));
/* 211:210 */         request.setNumeroCobro(cobro.getNumero());
/* 212:211 */         for (??? = cobro.getListaDetalleCobro().iterator(); ???.hasNext();)
/* 213:    */         {
/* 214:211 */           detalle = (DetalleCobro)???.next();
/* 215:212 */           for (DetalleCobroRequestDto detalleCobroRequestDto : request.getListaDetalleCobro()) {
/* 216:213 */             if (detalle.getCuentaPorCobrar().getId() == detalleCobroRequestDto.getIdCuentaPorCobrar().intValue())
/* 217:    */             {
/* 218:214 */               detalleCobroRequestDto.setIdDetalleCobro(Integer.valueOf(detalle.getId()));
/* 219:215 */               break;
/* 220:    */             }
/* 221:    */           }
/* 222:    */         }
/* 223:    */         DetalleCobro detalle;
/* 224:219 */         for (??? = cobro.getListaDetalleFormaCobro().iterator(); ???.hasNext();)
/* 225:    */         {
/* 226:219 */           detalle = (DetalleFormaCobro)???.next();
/* 227:220 */           for (DetalleFormaCobroRequestDto detalleFormaCobroRequestDto : request.getListaDetalleFormaCobro()) {
/* 228:221 */             if (detalleFormaCobroRequestDto.getIdDispositivoSincronizacion().equals(detalle.getIdDispositivoSincronizacion()))
/* 229:    */             {
/* 230:222 */               detalleFormaCobroRequestDto.setIdDetalleFormaCobro(Integer.valueOf(detalle.getId()));
/* 231:223 */               break;
/* 232:    */             }
/* 233:    */           }
/* 234:    */         }
/* 235:    */         DetalleFormaCobro detalle;
/* 236:228 */         response.setResponse(request);
/* 237:    */       }
/* 238:231 */       response.setSuccsess(true);
/* 239:232 */       registroMovil.setNumeroAs2(cobro.getNumero());
/* 240:233 */       registroMovil.setNota("REGISTRADO");
/* 241:234 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 242:235 */       return response;
/* 243:    */     }
/* 244:    */     catch (ExcepcionAS2Financiero e)
/* 245:    */     {
/* 246:237 */       e.printStackTrace();
/* 247:238 */       if (request.getUrlApp() != null) {
/* 248:239 */         error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 249:    */       } else {
/* 250:241 */         error = e.getCodigoExcepcion();
/* 251:    */       }
/* 252:    */     }
/* 253:    */     catch (ExcepcionAS2 e)
/* 254:    */     {
/* 255:244 */       e.printStackTrace();
/* 256:245 */       if (request.getUrlApp() != null) {
/* 257:246 */         error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 258:    */       } else {
/* 259:248 */         error = e.getCodigoExcepcion();
/* 260:    */       }
/* 261:    */     }
/* 262:    */     catch (ParseException e)
/* 263:    */     {
/* 264:251 */       e.printStackTrace();
/* 265:252 */       error = "Mal formado el formato de la fecha";
/* 266:    */     }
/* 267:255 */     registroMovil.setNota(error);
/* 268:256 */     this.servicioRegistroMovil.guardar(registroMovil);
/* 269:259 */     if (request.getUrlApp() != null)
/* 270:    */     {
/* 271:260 */       response.setSuccsess(false);
/* 272:261 */       response.setError(error);
/* 273:262 */       return response;
/* 274:    */     }
/* 275:264 */     throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { error });
/* 276:    */   }
/* 277:    */   
/* 278:    */   @POST
/* 279:    */   @Path("/consultarCobro")
/* 280:    */   @Consumes({"application/json"})
/* 281:    */   @Produces({"application/json"})
/* 282:    */   public EstadoCobroResponseDto consultarCobro(ConsultarCobroRequestDto request)
/* 283:    */     throws AS2Exception
/* 284:    */   {
/* 285:    */     try
/* 286:    */     {
/* 287:274 */       Cobro cobro = null;
/* 288:275 */       EstadoCobroResponseDto response = new EstadoCobroResponseDto();
/* 289:276 */       response.setEstado("SIN_ENVIAR");
/* 290:277 */       response.setNumero("");
/* 291:278 */       Map<String, String> filters = new HashMap();
/* 292:279 */       filters.put("codigoMovil", request.getCodigoMovil());
/* 293:    */       
/* 294:281 */       List<Cobro> listaCobro = this.servicioCobro.obtenerListaCombo(null, true, filters);
/* 295:282 */       if (listaCobro.size() > 0)
/* 296:    */       {
/* 297:283 */         cobro = (Cobro)listaCobro.get(0);
/* 298:284 */         response.setEstado(cobro.getEstado().toString());
/* 299:285 */         response.setNumero(cobro.getNumero());
/* 300:286 */         response.setIdCobro(Integer.valueOf(cobro.getIdCobro()));
/* 301:    */       }
/* 302:288 */       return response;
/* 303:    */     }
/* 304:    */     catch (Exception e)
/* 305:    */     {
/* 306:291 */       e.printStackTrace();
/* 307:292 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { "" });
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   @POST
/* 312:    */   @Path("/anularCobro")
/* 313:    */   @Consumes({"application/json"})
/* 314:    */   @Produces({"application/json"})
/* 315:    */   public Boolean anularCobro(ConsultarCobroRequestDto request)
/* 316:    */     throws AS2Exception
/* 317:    */   {
/* 318:301 */     RegistroMovil registroMovil = new RegistroMovil();
/* 319:302 */     registroMovil.setFecha(new Date());
/* 320:303 */     registroMovil.setCodigoMovil(request.getCodigoMovil());
/* 321:304 */     registroMovil.setDocumentoBase(DocumentoBase.COBRO_CLIENTE);
/* 322:    */     
/* 323:306 */     Cobro cobroAnulado = new Cobro();
/* 324:    */     
/* 325:308 */     cobroAnulado = this.servicioCobro.cargarDetalle(request.getIdCobro().intValue());
/* 326:309 */     if (cobroAnulado == null) {
/* 327:310 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { request.getIdCobro() + "" });
/* 328:    */     }
/* 329:312 */     registroMovil.setIdOrganizacion(cobroAnulado.getIdOrganizacion());
/* 330:313 */     registroMovil.setNumeroAs2(cobroAnulado.getNumero());
/* 331:314 */     registroMovil.setUsuario(cobroAnulado.getUsuarioCreacion());
/* 332:316 */     if (!cobroAnulado.getCodigoMovil().equals(request.getCodigoMovil()))
/* 333:    */     {
/* 334:317 */       registroMovil.setNota("ANULAR: NO COINCIDEN LOS CODIGOS");
/* 335:318 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 336:319 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { request.getIdCobro() + "" });
/* 337:    */     }
/* 338:    */     try
/* 339:    */     {
/* 340:324 */       if (cobroAnulado.getEstado().equals(Estado.ANULADO))
/* 341:    */       {
/* 342:325 */         registroMovil.setNota(Estado.ANULADO.toString());
/* 343:326 */         this.servicioRegistroMovil.guardar(registroMovil);
/* 344:327 */         return Boolean.valueOf(true);
/* 345:    */       }
/* 346:330 */       this.servicioCobro.anularCobro(cobroAnulado);
/* 347:331 */       registroMovil.setNota(Estado.ANULADO.toString());
/* 348:332 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 349:333 */       return Boolean.valueOf(true);
/* 350:    */     }
/* 351:    */     catch (Exception e)
/* 352:    */     {
/* 353:335 */       e.printStackTrace();
/* 354:336 */       registroMovil.setNota("ANULAR: ERROR:" + e.getMessage());
/* 355:337 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 356:338 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { e.getMessage() });
/* 357:    */     }
/* 358:    */   }
/* 359:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.rest.CobroServicioRest
 * JD-Core Version:    0.7.0.1
 */