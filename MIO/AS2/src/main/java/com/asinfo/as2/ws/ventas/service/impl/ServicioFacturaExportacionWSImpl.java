/*   1:    */ package com.asinfo.as2.ws.ventas.service.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteCobros;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   7:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   8:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   9:    */ import com.asinfo.as2.dao.sri.FacturaClienteSRIDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  12:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  14:    */ import com.asinfo.as2.entities.Ciudad;
/*  15:    */ import com.asinfo.as2.entities.CondicionPago;
/*  16:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  17:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  18:    */ import com.asinfo.as2.entities.Documento;
/*  19:    */ import com.asinfo.as2.entities.Empresa;
/*  20:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  21:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*  22:    */ import com.asinfo.as2.entities.Pais;
/*  23:    */ import com.asinfo.as2.entities.Producto;
/*  24:    */ import com.asinfo.as2.entities.Provincia;
/*  25:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  26:    */ import com.asinfo.as2.entities.Secuencia;
/*  27:    */ import com.asinfo.as2.entities.Subempresa;
/*  28:    */ import com.asinfo.as2.entities.Sucursal;
/*  29:    */ import com.asinfo.as2.entities.Zona;
/*  30:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  31:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  32:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  33:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  34:    */ import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
/*  35:    */ import com.asinfo.as2.enumeraciones.RefrendoEnum;
/*  36:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  37:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  38:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  39:    */ import com.asinfo.as2.finaciero.cobros.reportes.ReporteEstadoCuentaBean;
/*  40:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  41:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  42:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*  43:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  44:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioGarantiaCliente;
/*  45:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  46:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  47:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  48:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  49:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  50:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  51:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  52:    */ import com.asinfo.as2.ventas.reportes.ReporteFacturaClienteBean;
/*  53:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  54:    */ import com.asinfo.as2.ws.ventas.model.DetalleFacturaClienteWSEntity;
/*  55:    */ import com.asinfo.as2.ws.ventas.model.EstadoCuentaWSEntity;
/*  56:    */ import com.asinfo.as2.ws.ventas.model.FacturaClienteFlorWSEntity;
/*  57:    */ import com.asinfo.as2.ws.ventas.service.ServicioFacturaExportacionWS;
/*  58:    */ import java.io.PrintStream;
/*  59:    */ import java.math.BigDecimal;
/*  60:    */ import java.util.ArrayList;
/*  61:    */ import java.util.Calendar;
/*  62:    */ import java.util.Date;
/*  63:    */ import java.util.HashMap;
/*  64:    */ import java.util.List;
/*  65:    */ import java.util.Map;
/*  66:    */ import javax.ejb.EJB;
/*  67:    */ import javax.jws.WebService;
/*  68:    */ 
/*  69:    */ @WebService(endpointInterface="com.asinfo.as2.ws.ventas.service.ServicioFacturaExportacionWS")
/*  70:    */ public class ServicioFacturaExportacionWSImpl
/*  71:    */   implements ServicioFacturaExportacionWS
/*  72:    */ {
/*  73:    */   @EJB
/*  74:    */   private ServicioCondicionPago servicioCondicionPago;
/*  75:    */   @EJB
/*  76:    */   private ServicioEmpresa servicioEmpresa;
/*  77:    */   @EJB
/*  78:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  79:    */   @EJB
/*  80:    */   private ServicioDocumento servicioDocumento;
/*  81:    */   @EJB
/*  82:    */   private ServicioSecuencia servicioSecuencia;
/*  83:    */   @EJB
/*  84:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  85:    */   @EJB
/*  86:    */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  87:    */   @EJB
/*  88:    */   private ServicioProducto servicioProducto;
/*  89:    */   @EJB
/*  90:    */   private ServicioUsuario servicioUsuario;
/*  91:    */   @EJB
/*  92:    */   private FacturaClienteDao facturaClienteDao;
/*  93:    */   @EJB
/*  94:    */   private FacturaClienteSRIDao FacturaClienteSRIDao;
/*  95:    */   @EJB
/*  96:    */   private ServicioImpuesto servicioImpuesto;
/*  97:    */   @EJB
/*  98:    */   private ServicioZona servicioZona;
/*  99:    */   @EJB
/* 100:    */   private ServicioPais servicioPais;
/* 101:    */   @EJB
/* 102:    */   private ServicioCiudad servicioCiudad;
/* 103:    */   @EJB
/* 104:    */   private ServicioSucursal servicioSucursal;
/* 105:    */   @EJB
/* 106:    */   private ServicioReporteVenta servicioReporteVenta;
/* 107:    */   @EJB
/* 108:    */   private ServicioAnticipoCliente servicioAnticipoCliente;
/* 109:    */   @EJB
/* 110:    */   private ServicioGarantiaCliente servicioGarantiaCliente;
/* 111:    */   @EJB
/* 112:    */   private ServicioReporteCobroCliente servicioReporteCobroCliente;
/* 113:    */   
/* 114:    */   public boolean anularFactura(Long idFacturaCliente)
/* 115:    */     throws AS2Exception
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:130 */       FacturaCliente facturaCliente = this.servicioFacturaCliente.buscarPorId(Integer.valueOf(idFacturaCliente.intValue()));
/* 120:131 */       this.servicioFacturaCliente.anulaFacturaCliente(facturaCliente);
/* 121:    */       
/* 122:133 */       return true;
/* 123:    */     }
/* 124:    */     catch (ExcepcionAS2 e)
/* 125:    */     {
/* 126:136 */       throw new AS2Exception("msg_error_anular", new String[] { "" });
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean liberarFacturaAutomatica(Long idFacturaCliente)
/* 131:    */     throws AS2Exception
/* 132:    */   {
/* 133:142 */     this.facturaClienteDao.liberarFacturaAutomatica(Integer.valueOf(idFacturaCliente.intValue()));
/* 134:143 */     return true;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void esEditable(Long idFacturaCliente)
/* 138:    */     throws AS2Exception, ExcepcionAS2, ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:155 */       FacturaCliente facturaCliente = this.servicioFacturaCliente.buscarPorId(Integer.valueOf(idFacturaCliente.intValue()));
/* 143:156 */       this.servicioFacturaCliente.esEditable(facturaCliente);
/* 144:    */     }
/* 145:    */     catch (ExcepcionAS2Ventas e)
/* 146:    */     {
/* 147:158 */       e.printStackTrace();
/* 148:159 */       throw new AS2Exception(e.getCodigoExcepcion(), new String[] { e.getMessage() });
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String cambiarNumeroFactura(Long idFacturaCliente, String numeroFactura)
/* 153:    */   {
/* 154:167 */     FacturaCliente facturaCliente = new FacturaCliente();
/* 155:168 */     facturaCliente.setIdFacturaCliente(idFacturaCliente.intValue());
/* 156:    */     try
/* 157:    */     {
/* 158:170 */       FacturaClienteSRI facturaClienteSRI = this.servicioFacturaClienteSRI.buscarFacturaClienteSRIPorFacturaCliente(facturaCliente);
/* 159:    */       
/* 160:172 */       String[] factura = numeroFactura.split("-");
/* 161:    */       
/* 162:174 */       facturaClienteSRI.setEstablecimiento(factura[0]);
/* 163:175 */       facturaClienteSRI.setPuntoEmision(factura[1]);
/* 164:    */       
/* 165:177 */       facturaClienteSRI.setTraNumeroNuevo(factura[2]);
/* 166:178 */       this.servicioFacturaCliente.actualizarNumeroFactura(facturaClienteSRI);
/* 167:    */     }
/* 168:    */     catch (ExcepcionAS2Ventas e)
/* 169:    */     {
/* 170:180 */       e.printStackTrace();
/* 171:    */     }
/* 172:    */     catch (ExcepcionAS2Financiero e)
/* 173:    */     {
/* 174:182 */       e.printStackTrace();
/* 175:    */     }
/* 176:    */     catch (ExcepcionAS2 e1)
/* 177:    */     {
/* 178:184 */       e1.printStackTrace();
/* 179:    */     }
/* 180:186 */     return "";
/* 181:    */   }
/* 182:    */   
/* 183:    */   public FacturaClienteFlorWSEntity guardarFactura(FacturaClienteFlorWSEntity fcWS)
/* 184:    */     throws AS2Exception
/* 185:    */   {
/* 186:199 */     Map<String, DetalleFacturaCliente> hmDetalleFactura = new HashMap();
/* 187:200 */     FacturaCliente facturaCliente = null;
/* 188:201 */     if (fcWS.getIdFacturaCliente().longValue() != 0L) {
/* 189:    */       try
/* 190:    */       {
/* 191:203 */         facturaCliente = this.servicioFacturaCliente.cargarDetalle(fcWS.getIdFacturaCliente().intValue());
/* 192:204 */         this.servicioFacturaCliente.esEditable(facturaCliente);
/* 193:206 */         for (DetalleFacturaCliente detalle : facturaCliente.getListaDetalleFacturaCliente())
/* 194:    */         {
/* 195:207 */           detalle.setEliminado(true);
/* 196:208 */           hmDetalleFactura.put(detalle.getProducto().getCodigo() + "&" + detalle.getProducto() + "&" + detalle.getDescripcion(), detalle);
/* 197:210 */           for (ImpuestoProductoFacturaCliente ipfc : detalle.getListaImpuestoProductoFacturaCliente()) {
/* 198:211 */             ipfc.setEliminado(true);
/* 199:    */           }
/* 200:    */         }
/* 201:    */       }
/* 202:    */       catch (ExcepcionAS2 e)
/* 203:    */       {
/* 204:216 */         e.printStackTrace();
/* 205:217 */         throw new AS2Exception("msg_error_editar", new String[] { "" });
/* 206:    */       }
/* 207:    */     } else {
/* 208:220 */       facturaCliente = new FacturaCliente();
/* 209:    */     }
/* 210:223 */     Empresa empresa = this.servicioEmpresa.buscarPorId(Integer.valueOf(fcWS.getIdEmpresa().intValue()));
/* 211:224 */     facturaCliente.setEmpresa(empresa);
/* 212:    */     
/* 213:226 */     DireccionEmpresa direccionEmpresa = this.servicioEmpresa.buscarDireccionEmpresaPorId(fcWS.getIdDireccionEmpresa().intValue());
/* 214:227 */     facturaCliente.setDireccionEmpresa(direccionEmpresa);
/* 215:    */     
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:232 */     CondicionPago condicionPago = this.servicioCondicionPago.buscarPorId(Integer.valueOf(fcWS.getIdCondicionPago().intValue()));
/* 220:233 */     String serieFactura = fcWS.getEstablecimiento() + "-" + fcWS.getPuntoDeVenta();
/* 221:    */     
/* 222:235 */     facturaCliente.setSucursal(new Sucursal(fcWS.getIdSucursal().intValue(), "", ""));
/* 223:236 */     facturaCliente.setIdOrganizacion(fcWS.getIdOrganizacion().intValue());
/* 224:237 */     facturaCliente.setNumero(fcWS.getNumero());
/* 225:238 */     facturaCliente.setEmpresa(empresa);
/* 226:239 */     facturaCliente.setFecha(fcWS.getFechaFactura().getTime());
/* 227:240 */     facturaCliente.setDescuento(BigDecimal.ZERO);
/* 228:241 */     facturaCliente.setEstado(fcWS.getGenerarDocumentoElectronico().booleanValue() ? Estado.PROCESADO : Estado.ELABORADO);
/* 229:242 */     facturaCliente.setNumeroCuotas(fcWS.getNumeroCuotas());
/* 230:243 */     facturaCliente.setCondicionPago(condicionPago);
/* 231:244 */     facturaCliente.setDescripcion(fcWS.getNumeroPacking());
/* 232:245 */     facturaCliente.setIndicadorSaldoInicial(false);
/* 233:246 */     facturaCliente.setFacturaClienteFloricola(true);
/* 234:248 */     if (fcWS.getIdSubempresa() != null)
/* 235:    */     {
/* 236:249 */       Subempresa subempresa = this.servicioEmpresa.buscarPorIdSubempresa(Integer.valueOf(fcWS.getIdSubempresa().intValue()));
/* 237:250 */       facturaCliente.setSubempresa(subempresa);
/* 238:    */     }
/* 239:252 */     facturaCliente.setTotal(fcWS.getTotalImporte());
/* 240:253 */     facturaCliente.setFleteInternacional(fcWS.getFleteInternacional() == null ? BigDecimal.ZERO : fcWS.getFleteInternacional());
/* 241:254 */     facturaCliente.setImpuesto(fcWS.getTotalImpuesto());
/* 242:255 */     facturaCliente.setReferencia1(fcWS.getGuiaMadre() + "/" + fcWS.getGuiaHija());
/* 243:256 */     facturaCliente.setReferencia2((fcWS.getSubempresa() != null) && (fcWS.getInvoice() != null) ? fcWS.getSubempresa() + "/" + fcWS.getInvoice() : fcWS
/* 244:257 */       .getNumeroPacking());
/* 245:258 */     facturaCliente.setReferencia3(fcWS.getGuiaMadre());
/* 246:259 */     facturaCliente.setReferencia7(fcWS.getSubempresa());
/* 247:260 */     facturaCliente.setValorReferencia1(fcWS.getTotalCajas());
/* 248:261 */     facturaCliente.setValorReferencia2(fcWS.getTotalPiezas());
/* 249:262 */     facturaCliente.setValorReferencia3(fcWS.getTotalTallos());
/* 250:263 */     facturaCliente.setEmail(fcWS.getEmail());
/* 251:    */     
/* 252:    */ 
/* 253:266 */     facturaCliente.setReferencia4(fcWS.getDae());
/* 254:267 */     facturaCliente.setReferencia5(fcWS.getGuiaHija());
/* 255:268 */     facturaCliente.setReferencia6(fcWS.getAgenciaCarga());
/* 256:269 */     facturaCliente.setReferencia10(fcWS.getReferencia1());
/* 257:272 */     if (fcWS.getIdZona() != null)
/* 258:    */     {
/* 259:273 */       Zona zona = this.servicioZona.buscarPorId(fcWS.getIdZona().intValue());
/* 260:274 */       facturaCliente.setZona(zona);
/* 261:    */     }
/* 262:278 */     facturaCliente.setIndicadorAutomatico(true);
/* 263:280 */     if (fcWS.getIdAgenteComercial() != null)
/* 264:    */     {
/* 265:281 */       EntidadUsuario agenteComercial = this.servicioUsuario.buscarPorId(Integer.valueOf(fcWS.getIdAgenteComercial().intValue()));
/* 266:282 */       facturaCliente.setAgenteComercial(agenteComercial);
/* 267:    */     }
/* 268:286 */     FacturaClienteSRI fcSRI = facturaCliente.getFacturaClienteSRI();
/* 269:287 */     if (fcSRI == null)
/* 270:    */     {
/* 271:288 */       fcSRI = new FacturaClienteSRI();
/* 272:289 */       fcSRI.setFacturaCliente(facturaCliente);
/* 273:290 */       fcSRI.setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 274:    */       
/* 275:292 */       facturaCliente.setFacturaClienteSRI(fcSRI);
/* 276:    */     }
/* 277:295 */     fcSRI.setCodigoFormaPagoSRI("20");
/* 278:    */     
/* 279:297 */     fcSRI.setGenerarDocumentoElectronico(fcWS.getGenerarDocumentoElectronico());
/* 280:    */     
/* 281:299 */     fcSRI.setEstado(facturaCliente.getEstado());
/* 282:300 */     if (fcWS.isIndicadorDocumentoExterior())
/* 283:    */     {
/* 284:301 */       RefrendoEnum refrendo = RefrendoEnum.buscarPorCodigo(fcWS.getRefrendo());
/* 285:302 */       fcSRI.setRefrendo(refrendo);
/* 286:303 */       fcSRI.setDistritoRefrendo(fcWS.getDistritoAduanero());
/* 287:304 */       fcSRI.setRegimenRefrendo(fcWS.getRegimen());
/* 288:305 */       fcSRI.setCorrelativoRefrendo(fcWS.getCorrelativo());
/* 289:306 */       fcSRI.setDocumentoTransporteRefrendo(fcWS.getNumeroDocumentoTransporte());
/* 290:307 */       fcSRI.setFechaTransaccion(fcWS.getFechaFactura().getTime());
/* 291:    */       
/* 292:309 */       String anio = fcWS.getDae().substring(4, 8);
/* 293:310 */       fcSRI.setAnioRefrendo(Integer.valueOf(anio));
/* 294:312 */       if (fcWS.getIdPaisDestino() != null)
/* 295:    */       {
/* 296:313 */         Pais paisDestino = this.servicioPais.buscarPorId(Integer.valueOf(fcWS.getIdPaisDestino().intValue()));
/* 297:314 */         facturaCliente.setPaisDestino(paisDestino);
/* 298:    */       }
/* 299:316 */       if (fcWS.getIdPuertoDestino() != null)
/* 300:    */       {
/* 301:317 */         Ciudad puertoDestino = this.servicioCiudad.buscarPorId(fcWS.getIdPuertoDestino().intValue());
/* 302:318 */         facturaCliente.setPuertoDestino(puertoDestino);
/* 303:    */       }
/* 304:320 */       Sucursal sucursal = this.servicioSucursal.cargarDetalle(fcWS.getIdSucursal().intValue());
/* 305:321 */       facturaCliente.setPaisOrigen(sucursal.getCiudad().getProvincia().getPais());
/* 306:322 */       facturaCliente.setLugarIncoterm(sucursal.getCiudad());
/* 307:323 */       facturaCliente.setIncoterm("CIF");
/* 308:324 */       facturaCliente.setPuertoEmbarque(sucursal.getCiudad());
/* 309:    */     }
/* 310:328 */     Map<String, String> filters = new HashMap();
/* 311:329 */     filters.put("idOrganizacion", String.valueOf(fcWS.getIdOrganizacion()));
/* 312:330 */     filters.put("documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/* 313:331 */     filters.put("indicadorDocumentoTributario", String.valueOf(fcWS.isIndicadorDocumentoTributario()));
/* 314:332 */     filters.put("indicadorDocumentoExterior", String.valueOf(fcWS.isIndicadorDocumentoExterior()));
/* 315:333 */     filters.put("activo", "true");
/* 316:    */     
/* 317:335 */     List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("nombre", true, filters);
/* 318:338 */     if (fcWS.getNoGenerarDocumentoElectronico().booleanValue()) {
/* 319:339 */       ((Documento)listaDocumento.get(0)).setIndicadorDocumentoElectronico(false);
/* 320:    */     }
/* 321:342 */     if (listaDocumento.isEmpty()) {
/* 322:343 */       throw new AS2Exception("msg_configuracion_documento_exportacion", new String[] { DocumentoBase.FACTURA_CLIENTE.toString() });
/* 323:    */     }
/* 324:345 */     facturaCliente.setDocumento((Documento)listaDocumento.get(0));
/* 325:    */     
/* 326:    */ 
/* 327:    */ 
/* 328:349 */     filters = new HashMap();
/* 329:350 */     filters.put("idOrganizacion", String.valueOf(fcWS.getIdOrganizacion()));
/* 330:351 */     filters.put("sucursal.codigo", fcWS.getEstablecimiento());
/* 331:352 */     filters.put("codigo", fcWS.getPuntoDeVenta());
/* 332:353 */     List<PuntoDeVenta> listaPuntoVenta = this.servicioPuntoDeVenta.obtenerListaCombo("", false, filters);
/* 333:    */     PuntoDeVenta puntoVenta;
/* 334:354 */     if (listaPuntoVenta.size() > 0) {
/* 335:355 */       puntoVenta = (PuntoDeVenta)listaPuntoVenta.get(0);
/* 336:    */     } else {
/* 337:357 */       throw new AS2Exception("msg_error_punto_factura", new String[] { serieFactura });
/* 338:    */     }
/* 339:    */     PuntoDeVenta puntoVenta;
/* 340:    */     try
/* 341:    */     {
/* 342:362 */       if ((facturaCliente.getNumero() == null) || (facturaCliente.getNumero().isEmpty()))
/* 343:    */       {
/* 344:363 */         this.servicioFacturaCliente.cargarSecuencia(facturaCliente, puntoVenta);
/* 345:    */       }
/* 346:    */       else
/* 347:    */       {
/* 348:365 */         this.servicioDocumento.cargarDocumentoConAutorizacion(facturaCliente.getDocumento(), puntoVenta, facturaCliente.getFecha());
/* 349:366 */         facturaCliente.getDocumento().getSecuencia().setPrefijo(serieFactura + "-");
/* 350:    */       }
/* 351:    */     }
/* 352:    */     catch (ExcepcionAS2 e)
/* 353:    */     {
/* 354:369 */       throw new AS2Exception("msg_secuencia_no_encontrada", new String[] { facturaCliente.getDocumento().getNombre() });
/* 355:    */     }
/* 356:372 */     this.servicioSecuencia.detach(facturaCliente.getDocumento().getSecuencia());
/* 357:375 */     for (DetalleFacturaClienteWSEntity detalle : fcWS.getListaDetalleFacturaCliente()) {
/* 358:    */       try
/* 359:    */       {
/* 360:377 */         Producto producto = this.servicioProducto.buscarPorCodigo(detalle.getCodigoProducto(), facturaCliente.getIdOrganizacion(), null);
/* 361:    */         
/* 362:379 */         DetalleFacturaCliente dfc = (DetalleFacturaCliente)hmDetalleFactura.get(producto.getCodigo() + "&" + producto + "&" + detalle.getDescripcion());
/* 363:380 */         if (dfc == null)
/* 364:    */         {
/* 365:381 */           dfc = new DetalleFacturaCliente();
/* 366:382 */           dfc.setFacturaCliente(facturaCliente);
/* 367:    */           
/* 368:384 */           facturaCliente.getListaDetalleFacturaCliente().add(dfc);
/* 369:385 */           hmDetalleFactura.put(producto.getCodigo() + "&" + producto + "&" + detalle.getDescripcion(), dfc);
/* 370:    */         }
/* 371:    */         else
/* 372:    */         {
/* 373:387 */           dfc.setEliminado(false);
/* 374:    */         }
/* 375:390 */         dfc.setProducto(producto);
/* 376:391 */         if (fcWS.isIndicadorDocumentoExterior()) {
/* 377:392 */           dfc.setIndicadorImpuesto(false);
/* 378:    */         } else {
/* 379:394 */           dfc.setIndicadorImpuesto(producto.isIndicadorImpuestos());
/* 380:    */         }
/* 381:397 */         dfc.setUnidadVenta(producto.getUnidadVenta() != null ? producto.getUnidadVenta() : producto.getUnidad());
/* 382:    */         
/* 383:399 */         dfc.setCantidad(detalle.getCantidad());
/* 384:400 */         dfc.setPrecio(detalle.getPrecio());
/* 385:401 */         dfc.setPorcentajeDescuento(detalle.getPorcentajeDescuento() != null ? detalle.getPorcentajeDescuento() : BigDecimal.ZERO);
/* 386:402 */         dfc.setDescripcion(detalle.getDescripcion());
/* 387:403 */         dfc.setNandina(detalle.getNandina());
/* 388:406 */         if (dfc.isIndicadorImpuesto()) {
/* 389:    */           try
/* 390:    */           {
/* 391:408 */             dfc.setIndicadorImpuesto(true);
/* 392:409 */             this.servicioFacturaCliente.obtenerImpuestosProductos(dfc.getProducto(), dfc);
/* 393:    */           }
/* 394:    */           catch (ExcepcionAS2Inventario e)
/* 395:    */           {
/* 396:412 */             e.printStackTrace();
/* 397:    */           }
/* 398:    */         }
/* 399:    */       }
/* 400:    */       catch (ExcepcionAS2 e1)
/* 401:    */       {
/* 402:417 */         throw new AS2Exception("msg_producto_no_encontrado", new String[] { detalle.getCodigoProducto() });
/* 403:    */       }
/* 404:    */     }
/* 405:    */     try
/* 406:    */     {
/* 407:422 */       this.servicioFacturaCliente.totalizar(facturaCliente);
/* 408:423 */       this.servicioFacturaCliente.generarCuentaPorCobrar(facturaCliente);
/* 409:424 */       this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 410:425 */       this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(facturaCliente, puntoVenta);
/* 411:    */       
/* 412:427 */       this.servicioFacturaCliente.guardar(facturaCliente);
/* 413:    */       
/* 414:429 */       fcWS.setIdFacturaCliente(Long.valueOf(facturaCliente.getId()));
/* 415:430 */       fcWS.setNumero(facturaCliente.getNumero());
/* 416:431 */       Calendar fechaVencimiento = Calendar.getInstance();
/* 417:432 */       fechaVencimiento.setTime(facturaCliente.getFechaVencimiento());
/* 418:433 */       fcWS.setFechaVencimiento(fechaVencimiento);
/* 419:    */       
/* 420:435 */       return fcWS;
/* 421:    */     }
/* 422:    */     catch (ExcepcionAS2 e)
/* 423:    */     {
/* 424:438 */       if ("msg_error_numero_factura_fuera_rango".equals(e.getCodigoExcepcion())) {
/* 425:439 */         throw new AS2Exception("msg_error_numero_factura_fuera_rango", new String[] { serieFactura + "-" + fcWS.getNumero() });
/* 426:    */       }
/* 427:441 */       if ("msg_error_movimiento_total_cero".equals(e.getCodigoExcepcion())) {
/* 428:442 */         throw new AS2Exception("msg_error_factura_valor_cero", new String[0]);
/* 429:    */       }
/* 430:444 */       if ("msg_periodo_no_encontrado".equals(e.getCodigoExcepcion())) {
/* 431:445 */         throw new AS2Exception("msg_periodo_no_encontrado", new String[0]);
/* 432:    */       }
/* 433:447 */       if ("msg_error_numero_duplicado".equals(e.getCodigoExcepcion())) {
/* 434:448 */         throw new AS2Exception("msg_error_numero_factura_duplicado", new String[] { serieFactura + "-" + fcWS.getNumero() });
/* 435:    */       }
/* 436:450 */       if ("msg_secuencia_no_encontrada".equals(e.getCodigoExcepcion())) {
/* 437:451 */         throw new AS2Exception("msg_secuencia_no_encontrada", new String[] { facturaCliente.getDocumento().getNombre() });
/* 438:    */       }
/* 439:453 */       if ("msg_info_credito_maximo_cliente".equals(e.getCodigoExcepcion())) {
/* 440:454 */         throw new AS2Exception("msg_info_credito_maximo_cliente", new String[] { facturaCliente.getEmpresa().getNombreFiscal() });
/* 441:    */       }
/* 442:456 */       System.out.println("------------------------------------------------------1.1 " + e.getCodigoExcepcion());
/* 443:457 */       e.printStackTrace();
/* 444:458 */       throw new AS2Exception(e.getCodigoExcepcion());
/* 445:    */     }
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void actualizarInformacionFactura(FacturaClienteFlorWSEntity fcWS)
/* 449:    */     throws AS2Exception
/* 450:    */   {
/* 451:    */     try
/* 452:    */     {
/* 453:468 */       FacturaCliente facturaCliente = this.servicioFacturaCliente.cargarDetalle(fcWS.getIdFacturaCliente().intValue());
/* 454:469 */       facturaCliente.setReferencia3(fcWS.getGuiaMadre());
/* 455:470 */       facturaCliente.setReferencia4(fcWS.getDae());
/* 456:471 */       facturaCliente.setReferencia5(fcWS.getGuiaHija());
/* 457:    */       
/* 458:473 */       this.facturaClienteDao.guardar(facturaCliente);
/* 459:    */       
/* 460:475 */       FacturaClienteSRI fcSRI = facturaCliente.getFacturaClienteSRI();
/* 461:476 */       if (fcWS.isIndicadorDocumentoExterior())
/* 462:    */       {
/* 463:477 */         RefrendoEnum refrendo = RefrendoEnum.buscarPorCodigo(fcWS.getRefrendo());
/* 464:478 */         fcSRI.setRefrendo(refrendo);
/* 465:479 */         fcSRI.setDistritoRefrendo(fcWS.getDistritoAduanero());
/* 466:480 */         fcSRI.setRegimenRefrendo(fcWS.getRegimen());
/* 467:481 */         fcSRI.setCorrelativoRefrendo(fcWS.getCorrelativo());
/* 468:482 */         fcSRI.setDocumentoTransporteRefrendo(fcWS.getNumeroDocumentoTransporte());
/* 469:    */       }
/* 470:485 */       this.FacturaClienteSRIDao.guardar(fcSRI);
/* 471:    */     }
/* 472:    */     catch (ExcepcionAS2Ventas e)
/* 473:    */     {
/* 474:489 */       e.printStackTrace();
/* 475:    */     }
/* 476:    */   }
/* 477:    */   
/* 478:    */   public BigDecimal getPorcentajeIVA(int idOrganizacion, Date fecha)
/* 479:    */   {
/* 480:495 */     return this.servicioImpuesto.getPorcentajeIVA(idOrganizacion, fecha);
/* 481:    */   }
/* 482:    */   
/* 483:    */   public ArrayList<Object[]> getListaReporteEstadoCuenta(EstadoCuentaWSEntity estadoCuentaWSEntity)
/* 484:    */   {
/* 485:502 */     ArrayList<Object[]> lista = new ArrayList();
/* 486:    */     try
/* 487:    */     {
/* 488:505 */       Empresa cliente = new Empresa();
/* 489:506 */       cliente.setIdEmpresa(estadoCuentaWSEntity.getIdEmpresa() == null ? 0 : estadoCuentaWSEntity.getIdEmpresa().intValue());
/* 490:    */       
/* 491:508 */       Subempresa subempresa = new Subempresa();
/* 492:509 */       subempresa.setIdSubempresa(estadoCuentaWSEntity.getIdSubempresa() == null ? 0 : estadoCuentaWSEntity.getIdSubempresa().intValue());
/* 493:    */       
/* 494:511 */       lista = (ArrayList)this.servicioReporteVenta.getListaReporteEstadoCuenta(estadoCuentaWSEntity.getFechaDesde(), estadoCuentaWSEntity
/* 495:512 */         .getFechaHasta(), cliente, null, subempresa, estadoCuentaWSEntity.getIdOrganizacion().intValue(), OrdenamientoEnum.SALDO_FACTURA, estadoCuentaWSEntity
/* 496:513 */         .isSaldoDiferenteCero().booleanValue(), null, null);
/* 497:    */     }
/* 498:    */     catch (ExcepcionAS2Ventas e)
/* 499:    */     {
/* 500:516 */       e.printStackTrace();
/* 501:    */     }
/* 502:518 */     return lista;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public String[] getFieldsReporteEstadoCuenta()
/* 506:    */   {
/* 507:523 */     return ReporteEstadoCuentaBean.fields;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public BigDecimal obtenerSaldoAnticipo(int idCliente, Date fechaHasta)
/* 511:    */   {
/* 512:528 */     return this.servicioAnticipoCliente.obtenerSaldoAnticipo(idCliente, fechaHasta);
/* 513:    */   }
/* 514:    */   
/* 515:    */   public BigDecimal obtenerSaldoChequePosfechado(int idCliente, Date fechaHasta)
/* 516:    */   {
/* 517:533 */     return this.servicioGarantiaCliente.obtenerSaldoChequePosfechado(idCliente, fechaHasta);
/* 518:    */   }
/* 519:    */   
/* 520:    */   public BigDecimal obtenerSaldoEstadoCuenta(int idCliente, Date fechaHasta)
/* 521:    */   {
/* 522:538 */     return this.servicioReporteVenta.obtenerSaldoEstadoCuenta(idCliente, fechaHasta, true);
/* 523:    */   }
/* 524:    */   
/* 525:    */   public String[] getFieldsReporteFacturaCliente()
/* 526:    */   {
/* 527:543 */     return ReporteFacturaClienteBean.fields;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public ArrayList<Object[]> getDatosReporteFactura(Long idFacturaCliente, boolean incicadorDocumentoElectronico)
/* 531:    */     throws ExcepcionAS2
/* 532:    */   {
/* 533:550 */     FacturaCliente facturaCliente = null;
/* 534:551 */     facturaCliente = this.servicioFacturaCliente.buscarPorId(Integer.valueOf(idFacturaCliente.intValue()));
/* 535:552 */     if (facturaCliente != null)
/* 536:    */     {
/* 537:553 */       List<Object[]> lista = this.servicioFacturaCliente.getReporteFacturaCliente(idFacturaCliente.intValue(), TipoOrganizacion.TIPO_ORGANIZACION_GENERAL, 1, incicadorDocumentoElectronico, true, facturaCliente
/* 538:554 */         .getIdOrganizacion());
/* 539:555 */       return (ArrayList)lista;
/* 540:    */     }
/* 541:557 */     return null;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public ArrayList<ReporteCobros> getDatosReporteCobros(Long idCliente, Date fechaDesde, Date fechaHasta, Long idOrganizacion)
/* 545:    */   {
/* 546:564 */     return this.servicioReporteCobroCliente.getListaCobros(Integer.valueOf(idCliente.intValue()), fechaDesde, fechaHasta, idOrganizacion
/* 547:565 */       .intValue());
/* 548:    */   }
/* 549:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.service.impl.ServicioFacturaExportacionWSImpl
 * JD-Core Version:    0.7.0.1
 */