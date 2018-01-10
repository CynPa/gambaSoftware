/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Ciudad;
/*  10:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  11:    */ import com.asinfo.as2.entities.DetalleHojaRuta;
/*  12:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  13:    */ import com.asinfo.as2.entities.Documento;
/*  14:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  15:    */ import com.asinfo.as2.entities.HojaRuta;
/*  16:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  17:    */ import com.asinfo.as2.entities.Organizacion;
/*  18:    */ import com.asinfo.as2.entities.Secuencia;
/*  19:    */ import com.asinfo.as2.entities.Sucursal;
/*  20:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  21:    */ import com.asinfo.as2.entities.Transportista;
/*  22:    */ import com.asinfo.as2.entities.Ubicacion;
/*  23:    */ import com.asinfo.as2.entities.Vehiculo;
/*  24:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  25:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioVehiculo;
/*  28:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  31:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  32:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  33:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta;
/*  34:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import javax.annotation.PostConstruct;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.faces.bean.ManagedBean;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.context.ExternalContext;
/*  43:    */ import javax.faces.context.FacesContext;
/*  44:    */ import javax.servlet.http.HttpSession;
/*  45:    */ import org.apache.log4j.Logger;
/*  46:    */ 
/*  47:    */ @ManagedBean
/*  48:    */ @ViewScoped
/*  49:    */ public class GuiaRemisionBean
/*  50:    */   extends PageControllerAS2
/*  51:    */ {
/*  52:    */   private static final long serialVersionUID = 5743838206711541349L;
/*  53:    */   protected GuiaRemision guiaRemision;
/*  54:    */   protected DespachoCliente despachoCliente;
/*  55:    */   protected MovimientoInventario transferenciaBodega;
/*  56:    */   protected HojaRuta hojaRutaTransportista;
/*  57:    */   @EJB
/*  58:    */   protected transient ServicioVehiculo servicioVehiculo;
/*  59:    */   @EJB
/*  60:    */   protected transient ServicioGuiaRemision servicioGuiaRemision;
/*  61:    */   @EJB
/*  62:    */   protected transient ServicioCiudad servicioCiudad;
/*  63:    */   @EJB
/*  64:    */   protected transient ServicioDespachoCliente servicioDespachoCliente;
/*  65:    */   @EJB
/*  66:    */   protected transient ServicioMovimientoInventario servicioMovimientoInventario;
/*  67:    */   @EJB
/*  68:    */   protected transient ServicioDocumento servicioDocumento;
/*  69:    */   @EJB
/*  70:    */   protected transient ServicioEmpresa servicioEmpresa;
/*  71:    */   @EJB
/*  72:    */   protected transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  73:    */   @EJB
/*  74:    */   protected transient ServicioHojaRuta servicioHojaRuta;
/*  75:    */   protected List<Vehiculo> listaVehiculoCombo;
/*  76:    */   protected List<Ciudad> listaCiudadCombo;
/*  77:    */   protected List<Documento> listaDocumento;
/*  78:    */   protected List<TipoIdentificacion> listaTipoIdentificacionCombo;
/*  79:    */   private String mensaje;
/*  80:    */   private Vehiculo vehiculo;
/*  81:    */   
/*  82:    */   @PostConstruct
/*  83:    */   public void init()
/*  84:    */   {
/*  85: 83 */     this.mensaje = null;
/*  86:    */     
/*  87: 85 */     ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/*  88: 86 */     HttpSession session = (HttpSession)context.getSession(true);
/*  89: 87 */     if (session.getAttribute("despachoCliente") != null)
/*  90:    */     {
/*  91: 88 */       this.despachoCliente = ((DespachoCliente)session.getAttribute("despachoCliente"));
/*  92: 89 */       this.despachoCliente = this.servicioDespachoCliente.cargarDetalle(Integer.valueOf(this.despachoCliente.getIdDespachoCliente()));
/*  93:    */     }
/*  94: 91 */     if (session.getAttribute("transferenciaBodega") != null)
/*  95:    */     {
/*  96: 92 */       this.transferenciaBodega = ((MovimientoInventario)session.getAttribute("transferenciaBodega"));
/*  97: 93 */       this.transferenciaBodega = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(this.transferenciaBodega.getId()));
/*  98:    */     }
/*  99: 95 */     if (session.getAttribute("hojaRuta") != null)
/* 100:    */     {
/* 101: 96 */       this.hojaRutaTransportista = ((HojaRuta)session.getAttribute("hojaRuta"));
/* 102: 97 */       this.hojaRutaTransportista = this.servicioHojaRuta.cargarDetalle(Integer.valueOf(this.hojaRutaTransportista.getId()));
/* 103:    */     }
/* 104: 99 */     setEditado(true);
/* 105:101 */     if (this.despachoCliente != null)
/* 106:    */     {
/* 107:102 */       this.guiaRemision = this.servicioGuiaRemision.buscarPorDespacho(this.despachoCliente.getId());
/* 108:103 */       if (this.guiaRemision != null) {
/* 109:104 */         this.guiaRemision = this.servicioGuiaRemision.cargarDetalle(this.guiaRemision.getId());
/* 110:    */       }
/* 111:    */     }
/* 112:107 */     if (this.transferenciaBodega != null)
/* 113:    */     {
/* 114:108 */       this.guiaRemision = this.servicioGuiaRemision.buscarPorTransferenciaBodega(this.transferenciaBodega.getId());
/* 115:109 */       if (this.guiaRemision != null) {
/* 116:110 */         this.guiaRemision = this.servicioGuiaRemision.cargarDetalle(this.guiaRemision.getId());
/* 117:    */       }
/* 118:    */     }
/* 119:113 */     if (this.hojaRutaTransportista != null)
/* 120:    */     {
/* 121:114 */       this.guiaRemision = this.servicioGuiaRemision.buscarPorHojaRutaTransportista(this.hojaRutaTransportista.getId());
/* 122:115 */       if (this.guiaRemision != null) {
/* 123:116 */         this.guiaRemision = this.servicioGuiaRemision.cargarDetalle(this.guiaRemision.getId());
/* 124:    */       }
/* 125:    */     }
/* 126:120 */     if (this.guiaRemision == null)
/* 127:    */     {
/* 128:122 */       crearGuiaRemision();
/* 129:    */       
/* 130:124 */       Documento documento = null;
/* 131:125 */       if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 132:    */       {
/* 133:126 */         documento = (Documento)getListaDocumento().get(0);
/* 134:127 */         this.guiaRemision.setDocumento(documento);
/* 135:128 */         actualizarDocumento();
/* 136:    */       }
/* 137:    */       else
/* 138:    */       {
/* 139:130 */         documento = new Documento();
/* 140:131 */         documento.setSecuencia(new Secuencia());
/* 141:132 */         this.guiaRemision.setDocumento(documento);
/* 142:    */       }
/* 143:135 */       if (this.despachoCliente != null)
/* 144:    */       {
/* 145:136 */         Ciudad ciudadOrigen = this.servicioCiudad.getCiudadDeSucursal(this.despachoCliente.getSucursal().getId());
/* 146:137 */         if (ciudadOrigen != null) {
/* 147:138 */           this.guiaRemision.setCiudadOrigen(ciudadOrigen);
/* 148:    */         }
/* 149:140 */         this.guiaRemision.setCiudadDestino(this.despachoCliente.getDireccionEmpresa().getCiudad());
/* 150:141 */         this.guiaRemision.setEmailCliente(this.servicioEmpresa.cargarMails(this.despachoCliente.getEmpresa(), DocumentoBase.GUIA_REMISION));
/* 151:    */       }
/* 152:143 */       if ((this.transferenciaBodega == null) || 
/* 153:    */       
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:148 */         (this.hojaRutaTransportista != null))
/* 158:    */       {
/* 159:150 */         List<Vehiculo> listaVehiculo = this.servicioVehiculo.obtenerListaVehiculoPorTransportista(this.hojaRutaTransportista.getTransportista()
/* 160:151 */           .getIdTransportista());
/* 161:153 */         if (!listaVehiculo.isEmpty())
/* 162:    */         {
/* 163:154 */           Vehiculo vehiculo = (Vehiculo)listaVehiculo.get(0);
/* 164:155 */           vehiculo.setTransportista(this.hojaRutaTransportista.getTransportista());
/* 165:156 */           getGuiaRemision().setVehiculo(vehiculo);
/* 166:    */         }
/* 167:159 */         actualizarConductor();
/* 168:    */         
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:168 */         String email = "";
/* 177:170 */         for (DetalleHojaRuta dhr : this.hojaRutaTransportista.getListaDetalleHojaRuta()) {
/* 178:171 */           email = email + this.servicioEmpresa.cargarMails(dhr.getDespachoCliente().getEmpresa(), DocumentoBase.GUIA_REMISION) + ";";
/* 179:    */         }
/* 180:173 */         getGuiaRemision().setEmailCliente(email.trim().substring(0, email.length()));
/* 181:    */       }
/* 182:175 */       if (this.guiaRemision.getCiudadOrigen() == null)
/* 183:    */       {
/* 184:176 */         Map<String, String> filters = new HashMap();
/* 185:177 */         filters.put("predeterminado", "true");
/* 186:178 */         List<Ciudad> listaCiudad = this.servicioCiudad.obtenerListaCombo("nombre", true, filters);
/* 187:179 */         if (listaCiudad.size() > 0) {
/* 188:180 */           this.guiaRemision.setCiudadOrigen((Ciudad)listaCiudad.get(0));
/* 189:    */         }
/* 190:    */       }
/* 191:185 */       int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(AppUtil.getOrganizacion().getId()).booleanValue() ? 2 : 1;
/* 192:186 */       this.guiaRemision.setAmbiente(ambiente);
/* 193:    */       
/* 194:188 */       this.guiaRemision.setDireccionMatriz(AppUtil.getDireccionMatriz());
/* 195:189 */       this.guiaRemision.setDireccionSucursal(AppUtil.getSucursal().getUbicacion().getDireccionCompleta());
/* 196:    */     }
/* 197:    */     else
/* 198:    */     {
/* 199:193 */       if ((this.guiaRemision.getDocumento() != null) && 
/* 200:194 */         (this.guiaRemision.getDocumento().isIndicadorDocumentoElectronico()) && (
/* 201:195 */         (this.guiaRemision.getEstado().equals(Estado.PROCESADO)) || (this.guiaRemision.getEstado().equals(Estado.APROBADO)) || 
/* 202:196 */         (this.guiaRemision.getEstado().equals(Estado.CONTABILIZADO)))) {
/* 203:197 */         this.mensaje = getLanguageController().getMensaje("msg_accion_implica_anulacion_previa");
/* 204:    */       }
/* 205:199 */       actualizarDocumento();
/* 206:    */     }
/* 207:201 */     this.guiaRemision.setDespachoCliente(this.despachoCliente);
/* 208:202 */     this.guiaRemision.setTransferenciaBodega(this.transferenciaBodega);
/* 209:203 */     this.guiaRemision.setHojaRutaTransportista(this.hojaRutaTransportista);
/* 210:204 */     if (session.getAttribute("despachoCliente") != null) {
/* 211:205 */       session.removeAttribute("despachoCliente");
/* 212:    */     }
/* 213:207 */     if (session.getAttribute("transferenciaBodega") != null) {
/* 214:208 */       session.removeAttribute("transferenciaBodega");
/* 215:    */     }
/* 216:210 */     if (session.getAttribute("hojaRuta") != null) {
/* 217:211 */       session.removeAttribute("hojaRuta");
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String actualizarDocumento()
/* 222:    */   {
/* 223:    */     try
/* 224:    */     {
/* 225:224 */       if (this.guiaRemision.getDocumento().isIndicadorDocumentoTributario()) {
/* 226:225 */         this.servicioGuiaRemision.cargarSecuencia(this.guiaRemision, AppUtil.getPuntoDeVenta());
/* 227:    */       } else {
/* 228:227 */         this.servicioGuiaRemision.cargarSecuencia(this.guiaRemision, null);
/* 229:    */       }
/* 230:    */     }
/* 231:    */     catch (ExcepcionAS2 e)
/* 232:    */     {
/* 233:230 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 234:    */     }
/* 235:233 */     return "";
/* 236:    */   }
/* 237:    */   
/* 238:    */   public List<Documento> getListaDocumento()
/* 239:    */   {
/* 240:243 */     if (this.listaDocumento == null) {
/* 241:    */       try
/* 242:    */       {
/* 243:245 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.GUIA_REMISION);
/* 244:    */       }
/* 245:    */       catch (ExcepcionAS2 e)
/* 246:    */       {
/* 247:247 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 248:    */       }
/* 249:    */     }
/* 250:250 */     return this.listaDocumento;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 254:    */   {
/* 255:254 */     this.listaDocumento = listaDocumento;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String editar()
/* 259:    */   {
/* 260:259 */     return "";
/* 261:    */   }
/* 262:    */   
/* 263:    */   public String guardar()
/* 264:    */   {
/* 265:    */     try
/* 266:    */     {
/* 267:265 */       this.servicioGuiaRemision.guardar(this.guiaRemision);
/* 268:266 */       this.guiaRemision = null;
/* 269:267 */       setEditado(false);
/* 270:268 */       if (this.despachoCliente != null) {
/* 271:269 */         return "/paginas/ventas/procesos/despachoCliente.xhtml?faces-redirect=true";
/* 272:    */       }
/* 273:271 */       if (this.transferenciaBodega != null) {
/* 274:272 */         return "/paginas/inventario/procesos/transferenciaBodegas.xhtml?faces-redirect=true";
/* 275:    */       }
/* 276:274 */       if (this.hojaRutaTransportista != null) {
/* 277:275 */         return "/paginas/ventas/procesos/hojaRutaTransportista.xhtml?faces-redirect=true";
/* 278:    */       }
/* 279:    */     }
/* 280:    */     catch (ExcepcionAS2Identification e)
/* 281:    */     {
/* 282:278 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 283:279 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 284:    */     }
/* 285:    */     catch (Exception e)
/* 286:    */     {
/* 287:281 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 288:282 */       LOG.error("ERROR AL GUARDAR DATOS GUIA REMISION", e);
/* 289:    */     }
/* 290:284 */     return "";
/* 291:    */   }
/* 292:    */   
/* 293:    */   public String eliminar()
/* 294:    */   {
/* 295:289 */     return "";
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String limpiar()
/* 299:    */   {
/* 300:294 */     crearGuiaRemision();
/* 301:295 */     return "";
/* 302:    */   }
/* 303:    */   
/* 304:    */   public String cargarDatos()
/* 305:    */   {
/* 306:300 */     return "";
/* 307:    */   }
/* 308:    */   
/* 309:    */   public String cancelar()
/* 310:    */   {
/* 311:305 */     this.guiaRemision = null;
/* 312:    */     
/* 313:307 */     setEditado(false);
/* 314:308 */     if (this.despachoCliente != null) {
/* 315:309 */       return "/paginas/ventas/procesos/despachoCliente.xhtml?faces-redirect=true";
/* 316:    */     }
/* 317:311 */     if (this.transferenciaBodega != null) {
/* 318:312 */       return "/paginas/inventario/procesos/transferenciaBodegas.xhtml?faces-redirect=true";
/* 319:    */     }
/* 320:314 */     if (this.hojaRutaTransportista != null) {
/* 321:315 */       return "/paginas/ventas/procesos/hojaRutaTransportista.xhtml?faces-redirect=true";
/* 322:    */     }
/* 323:317 */     return "";
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void crearGuiaRemision()
/* 327:    */   {
/* 328:321 */     this.guiaRemision = new GuiaRemision();
/* 329:322 */     this.guiaRemision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 330:323 */     this.guiaRemision.setIdSucursal(AppUtil.getSucursal().getId());
/* 331:324 */     if (this.despachoCliente != null) {
/* 332:325 */       this.guiaRemision.setFecha(this.despachoCliente.getFecha());
/* 333:    */     }
/* 334:327 */     if (this.transferenciaBodega != null) {
/* 335:328 */       this.guiaRemision.setFecha(this.transferenciaBodega.getFecha());
/* 336:    */     }
/* 337:330 */     if (this.hojaRutaTransportista != null) {
/* 338:331 */       this.guiaRemision.setFecha(this.hojaRutaTransportista.getFecha());
/* 339:    */     }
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void actualizarConductor()
/* 343:    */   {
/* 344:336 */     if ((getGuiaRemision().getVehiculo() != null) && (getGuiaRemision().getVehiculo().getTransportista() != null))
/* 345:    */     {
/* 346:337 */       getGuiaRemision().setPlaca(getGuiaRemision().getVehiculo().getPlaca());
/* 347:338 */       getGuiaRemision().setConductor(getGuiaRemision().getVehiculo().getTransportista().getNombre());
/* 348:339 */       getGuiaRemision().setLicencia(getGuiaRemision().getVehiculo().getTransportista().getIdentificacion());
/* 349:340 */       getGuiaRemision().setEmailTransportista(getGuiaRemision().getVehiculo().getTransportista().getEmail());
/* 350:341 */       getGuiaRemision().setTipoIdentificacionTransportista(getGuiaRemision().getVehiculo().getTransportista().getTipoIdentificacion());
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   public List<TipoIdentificacion> getListaTipoIdentificacionCombo()
/* 355:    */   {
/* 356:346 */     if (this.listaTipoIdentificacionCombo == null) {
/* 357:347 */       this.listaTipoIdentificacionCombo = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 358:    */     }
/* 359:349 */     return this.listaTipoIdentificacionCombo;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setListaTipoIdentificacionCombo(List<TipoIdentificacion> listaTipoIdentificacionCombo)
/* 363:    */   {
/* 364:353 */     this.listaTipoIdentificacionCombo = listaTipoIdentificacionCombo;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public GuiaRemision getGuiaRemision()
/* 368:    */   {
/* 369:357 */     return this.guiaRemision;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setGuiaRemision(GuiaRemision guiaRemision)
/* 373:    */   {
/* 374:361 */     this.guiaRemision = guiaRemision;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public DespachoCliente getDespachoCliente()
/* 378:    */   {
/* 379:365 */     return this.despachoCliente;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setDespachoCliente(DespachoCliente despachoCliente)
/* 383:    */   {
/* 384:369 */     this.despachoCliente = despachoCliente;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public List<Vehiculo> getListaVehiculoCombo()
/* 388:    */   {
/* 389:373 */     if (this.listaVehiculoCombo == null) {
/* 390:374 */       this.listaVehiculoCombo = this.servicioVehiculo.obtenerListaCombo("placa", true, null);
/* 391:    */     }
/* 392:376 */     return this.listaVehiculoCombo;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setListaVehiculoCombo(List<Vehiculo> listaVehiculoCombo)
/* 396:    */   {
/* 397:380 */     this.listaVehiculoCombo = listaVehiculoCombo;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public List<Ciudad> getListaCiudadCombo()
/* 401:    */   {
/* 402:387 */     if (this.listaCiudadCombo == null) {
/* 403:388 */       this.listaCiudadCombo = this.servicioCiudad.obtenerListaCombo("nombre", true, null);
/* 404:    */     }
/* 405:390 */     return this.listaCiudadCombo;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void setListaCiudadCombo(List<Ciudad> listaCiudadCombo)
/* 409:    */   {
/* 410:398 */     this.listaCiudadCombo = listaCiudadCombo;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public MovimientoInventario getTransferenciaBodega()
/* 414:    */   {
/* 415:402 */     return this.transferenciaBodega;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void setTransferenciaBodega(MovimientoInventario transferenciaBodega)
/* 419:    */   {
/* 420:406 */     this.transferenciaBodega = transferenciaBodega;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public String getMensaje()
/* 424:    */   {
/* 425:410 */     return this.mensaje;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void setMensaje(String mensaje)
/* 429:    */   {
/* 430:414 */     this.mensaje = mensaje;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public Vehiculo getVehiculo()
/* 434:    */   {
/* 435:418 */     if (this.vehiculo == null) {
/* 436:419 */       this.vehiculo = new Vehiculo();
/* 437:    */     }
/* 438:420 */     return this.vehiculo;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setVehiculo(Vehiculo vehiculo)
/* 442:    */   {
/* 443:424 */     this.vehiculo = vehiculo;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void crearVehiculo()
/* 447:    */   {
/* 448:428 */     this.vehiculo = new Vehiculo();
/* 449:429 */     this.vehiculo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 450:430 */     this.vehiculo.setIdSucursal(AppUtil.getSucursal().getId());
/* 451:431 */     this.vehiculo.setActivo(true);
/* 452:432 */     this.vehiculo.setDescripcion("");
/* 453:433 */     this.vehiculo.setRendered(true);
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void guardarVehiculo()
/* 457:    */   {
/* 458:    */     try
/* 459:    */     {
/* 460:438 */       this.servicioVehiculo.guardar(getVehiculo());
/* 461:439 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 462:440 */       getGuiaRemision().setVehiculo(getVehiculo());
/* 463:441 */       actualizarConductor();
/* 464:442 */       this.listaVehiculoCombo = null;
/* 465:    */     }
/* 466:    */     catch (Exception e)
/* 467:    */     {
/* 468:444 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 469:445 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 470:    */     }
/* 471:    */   }
/* 472:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.GuiaRemisionBean
 * JD-Core Version:    0.7.0.1
 */