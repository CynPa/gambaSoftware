/*   1:    */ package com.asinfo.as2.rs.ventas.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.dao.GenericoDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*  10:    */ import com.asinfo.as2.entities.Bodega;
/*  11:    */ import com.asinfo.as2.entities.Cliente;
/*  12:    */ import com.asinfo.as2.entities.Cobro;
/*  13:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  14:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  15:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  16:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  17:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  18:    */ import com.asinfo.as2.entities.Documento;
/*  19:    */ import com.asinfo.as2.entities.Empresa;
/*  20:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  21:    */ import com.asinfo.as2.entities.FormaPagoSRI;
/*  22:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  23:    */ import com.asinfo.as2.entities.Producto;
/*  24:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  25:    */ import com.asinfo.as2.entities.Sucursal;
/*  26:    */ import com.asinfo.as2.entities.Ubicacion;
/*  27:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*  28:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  29:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  30:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  31:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  32:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  33:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  34:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  35:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  36:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  37:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  38:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  39:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  40:    */ import com.asinfo.as2.rs.datosbase.dto.EstadisticaVentaClienteRequestDto;
/*  41:    */ import com.asinfo.as2.rs.datosbase.dto.EstadisticaVentaClienteResponseDto;
/*  42:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  43:    */ import com.asinfo.as2.rs.financiero.cobros.dto.SaldosFacturaResponseDto;
/*  44:    */ import com.asinfo.as2.rs.inventario.dto.ProductoResponseDto;
/*  45:    */ import com.asinfo.as2.rs.ventas.dto.ConsultarFacturaClienteRequestDto;
/*  46:    */ import com.asinfo.as2.rs.ventas.dto.DetalleFacturaClienteResponseDto;
/*  47:    */ import com.asinfo.as2.rs.ventas.dto.EstadoFacturaClienteResponseDto;
/*  48:    */ import com.asinfo.as2.rs.ventas.dto.FacturaClienteResponseDto;
/*  49:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  50:    */ import com.asinfo.as2.util.AppUtil;
/*  51:    */ import com.asinfo.as2.utils.DatosSRI;
/*  52:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  53:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  54:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  55:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  56:    */ import java.math.BigDecimal;
/*  57:    */ import java.math.RoundingMode;
/*  58:    */ import java.text.ParseException;
/*  59:    */ import java.text.SimpleDateFormat;
/*  60:    */ import java.util.ArrayList;
/*  61:    */ import java.util.Date;
/*  62:    */ import java.util.HashMap;
/*  63:    */ import java.util.Iterator;
/*  64:    */ import java.util.List;
/*  65:    */ import java.util.Map;
/*  66:    */ import javax.ejb.EJB;
/*  67:    */ import javax.faces.model.SelectItem;
/*  68:    */ import javax.ws.rs.Consumes;
/*  69:    */ import javax.ws.rs.POST;
/*  70:    */ import javax.ws.rs.Path;
/*  71:    */ import javax.ws.rs.Produces;
/*  72:    */ 
/*  73:    */ @Path("/ventas")
/*  74:    */ public class FacturaServicioRest
/*  75:    */ {
/*  76:    */   @EJB
/*  77:    */   private ServicioDocumento servicioDocumento;
/*  78:    */   @EJB
/*  79:    */   private ServicioSucursal servicioSucursal;
/*  80:    */   @EJB
/*  81:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  82:    */   @EJB
/*  83:    */   private ServicioEmpresa servicioEmpresa;
/*  84:    */   @EJB
/*  85:    */   private ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  86:    */   @EJB
/*  87:    */   private GenericoDao<FormaPagoSRI> formaPagoSRIDao;
/*  88:    */   @EJB
/*  89:    */   private ServicioCondicionPago servicioCondicionPago;
/*  90:    */   @EJB
/*  91:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  92:    */   @EJB
/*  93:    */   private ServicioProducto servicioProducto;
/*  94:    */   @EJB
/*  95:    */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  96:    */   @EJB
/*  97:    */   private ServicioGenerico<DireccionEmpresa> servicioDireccionEmpresa;
/*  98:    */   @EJB
/*  99:    */   private ServicioBodega servicioBodega;
/* 100:    */   @EJB
/* 101:    */   private ServicioCobro servicioCobro;
/* 102:    */   @EJB
/* 103:    */   private ServicioReporteVenta servicioReporteVenta;
/* 104:    */   @EJB
/* 105:    */   private ServicioGenerico<DetalleFacturaCliente> servicioDetalleFacturaCliente;
/* 106:    */   
/* 107:    */   @POST
/* 108:    */   @Path("/crearFacturaCliente")
/* 109:    */   @Consumes({"application/json"})
/* 110:    */   @Produces({"application/json"})
/* 111:    */   public ProcesosResponseDto crearFacturaCliente(FacturaClienteResponseDto facturaResponse)
/* 112:    */     throws AS2Exception
/* 113:    */   {
/* 114:114 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 115:115 */     String error = "";
/* 116:116 */     FacturaCliente facturaCliente = new FacturaCliente();
/* 117:117 */     PuntoDeVenta puntoDeVenta = null;
/* 118:    */     
/* 119:119 */     Documento documento = null;
/* 120:    */     
/* 121:121 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 122:    */     
/* 123:123 */     List<DetalleFacturaClienteResponseDto> listaDetalleFacturaClienteResponseDto = facturaResponse.getDetalleFacturaClienteResponse();
/* 124:    */     try
/* 125:    */     {
/* 126:127 */       Map<String, String> filtrosFactura = new HashMap();
/* 127:128 */       filtrosFactura.put("idOrganizacion", facturaResponse.getIdOrganizacion() + "");
/* 128:129 */       filtrosFactura.put("idDispositivoSincronizacion", facturaResponse.getIdDispositivoSincronizacion() + "");
/* 129:130 */       filtrosFactura.put("empresa.idEmpresa", facturaResponse.getIdEmpresa() + "");
/* 130:131 */       filtrosFactura.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE + "");
/* 131:132 */       List<FacturaCliente> listaFactura = this.servicioFacturaCliente.obtenerListaCombo("fecha", false, filtrosFactura);
/* 132:    */       List<Documento> listaDocumento;
/* 133:133 */       if (!listaFactura.isEmpty())
/* 134:    */       {
/* 135:134 */         facturaCliente = this.servicioFacturaCliente.cargarDetalle(((FacturaCliente)listaFactura.get(0)).getId());
/* 136:    */       }
/* 137:    */       else
/* 138:    */       {
/* 139:137 */         filtrosDocumento = new HashMap();
/* 140:138 */         filtrosDocumento.put("idOrganizacion", facturaResponse.getIdOrganizacion() + "");
/* 141:139 */         filtrosDocumento.put("idDispositivoSincronizacion", facturaResponse.getIdDispositivoSincronizacion() + "");
/* 142:140 */         filtrosDocumento.put("empresa.idEmpresa", facturaResponse.getIdEmpresa() + "");
/* 143:141 */         filtrosDocumento.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE + "");
/* 144:    */         
/* 145:    */ 
/* 146:144 */         List<AutorizacionDocumentoSRI> listaAutorizacion = this.servicioDocumento.obtenerAutorizaciones(sdf.parse(facturaResponse.getFecha()), facturaResponse
/* 147:145 */           .getIdOrganizacion(), true);
/* 148:146 */         listaDocumento = new ArrayList();
/* 149:147 */         for (AutorizacionDocumentoSRI autorizacionDocumentoSRI : listaAutorizacion) {
/* 150:148 */           if ((autorizacionDocumentoSRI.getDocumento().getDocumentoBase().equals(DocumentoBase.FACTURA_CLIENTE)) && 
/* 151:149 */             (autorizacionDocumentoSRI.getPuntoDeVenta().getId() == facturaResponse.getIdPuntoVenta().intValue())) {
/* 152:150 */             listaDocumento.add(autorizacionDocumentoSRI.getDocumento());
/* 153:    */           }
/* 154:    */         }
/* 155:153 */         if ((listaDocumento != null) && (!listaDocumento.isEmpty())) {
/* 156:154 */           documento = (Documento)listaDocumento.get(0);
/* 157:    */         } else {
/* 158:156 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { Documento.class.getSimpleName() + ". " + DocumentoBase.FACTURA_CLIENTE });
/* 159:    */         }
/* 160:159 */         Sucursal sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(facturaResponse.getIdSucursal()));
/* 161:    */         
/* 162:161 */         Map<String, String> filters = new HashMap();
/* 163:162 */         filters.put("idOrganizacion", "" + facturaResponse.getIdOrganizacion());
/* 164:163 */         filters.put("idPuntoDeVenta", facturaResponse.getIdPuntoVenta() + "");
/* 165:164 */         filters.put("sucursal.idSucursal", "" + facturaResponse.getIdSucursal());
/* 166:    */         
/* 167:166 */         puntoDeVenta = this.servicioPuntoDeVenta.buscarPuntoDeVenta(filters);
/* 168:    */         
/* 169:168 */         facturaCliente.setEstado(Estado.PROCESADO);
/* 170:169 */         facturaCliente.setDocumento(documento);
/* 171:170 */         facturaCliente.setDescripcion(facturaResponse.getDescripcion());
/* 172:171 */         if (facturaResponse.getOrigen() != null) {
/* 173:172 */           facturaCliente.setOrigen(facturaResponse.getOrigen());
/* 174:    */         }
/* 175:175 */         FacturaClienteSRI facturaClienteSRI = new FacturaClienteSRI();
/* 176:176 */         facturaCliente.setFacturaClienteSRI(facturaClienteSRI);
/* 177:177 */         facturaClienteSRI.setFacturaCliente(facturaCliente);
/* 178:    */         
/* 179:179 */         int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(facturaResponse.getIdOrganizacion()).booleanValue() ? 2 : 1;
/* 180:180 */         facturaClienteSRI.setAmbiente(ambiente);
/* 181:    */         
/* 182:182 */         facturaClienteSRI.setDireccionMatriz(AppUtil.getDireccionMatriz());
/* 183:183 */         facturaClienteSRI.setDireccionSucursal(sucursal.getUbicacion().getDireccionCompleta());
/* 184:184 */         facturaClienteSRI.setEstado(Estado.PROCESADO);
/* 185:    */         
/* 186:186 */         facturaCliente.setSucursal(sucursal);
/* 187:187 */         facturaCliente.setIdOrganizacion(facturaResponse.getIdOrganizacion());
/* 188:188 */         facturaCliente.setIdDispositivoSincronizacion(facturaResponse.getIdDispositivoSincronizacion());
/* 189:189 */         facturaClienteSRI.setIdSucursal(sucursal.getId());
/* 190:190 */         facturaClienteSRI.setIdOrganizacion(facturaResponse.getIdOrganizacion());
/* 191:    */         
/* 192:192 */         Empresa empresa = obtenerCliente(facturaResponse);
/* 193:    */         
/* 194:194 */         facturaCliente.setEmpresa(empresa);
/* 195:    */         
/* 196:196 */         facturaClienteSRI.setEmail(empresa.getEmail1());
/* 197:197 */         facturaCliente.setEmail(empresa.getEmail1());
/* 198:198 */         facturaCliente.setDireccionFactura(facturaResponse.getDireccionFactura());
/* 199:    */         
/* 200:200 */         DireccionEmpresa direccionEmpresa = obtenerDireccionEmpresa(facturaResponse);
/* 201:    */         
/* 202:202 */         facturaCliente.setDireccionEmpresa(direccionEmpresa);
/* 203:    */         
/* 204:204 */         List<FormaPagoSRI> listaFormaPagoSRI = this.servicioFormaPagoSRI.getListaFormaPagoSRI(empresa);
/* 205:206 */         if (listaFormaPagoSRI.size() > 0) {
/* 206:207 */           facturaCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(((FormaPagoSRI)listaFormaPagoSRI.get(0)).getCodigo());
/* 207:    */         } else {
/* 208:209 */           facturaCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(((SelectItem)DatosSRI.getListaFormaPago(facturaResponse.getIdOrganizacion()).get(0)).getValue().toString());
/* 209:    */         }
/* 210:212 */         facturaCliente.setCondicionPago(empresa.getCliente().getCondicionPago());
/* 211:    */         
/* 212:214 */         facturaCliente.setFecha(sdf.parse(facturaResponse.getFecha()));
/* 213:    */         
/* 214:216 */         facturaCliente.setFechaVencimiento(sdf.parse(facturaResponse.getFecha()));
/* 215:    */         
/* 216:218 */         facturaCliente.setEmail(this.servicioEmpresa.cargarMails(empresa, facturaCliente.getDocumento().getDocumentoBase()));
/* 217:    */         
/* 218:220 */         this.servicioDocumento.cargarSecuencia(facturaCliente.getDocumento(), puntoDeVenta, facturaCliente.getFecha());
/* 219:    */         
/* 220:    */ 
/* 221:223 */         facturaCliente.setNumero(facturaResponse.getNumero());
/* 222:225 */         for (DetalleFacturaClienteResponseDto detalleFacturaClienteResponse : listaDetalleFacturaClienteResponseDto)
/* 223:    */         {
/* 224:227 */           DetalleFacturaCliente detalleFactura = new DetalleFacturaCliente();
/* 225:228 */           detalleFactura.setIdOrganizacion(detalleFacturaClienteResponse.getIdOrganizacion());
/* 226:229 */           detalleFactura.setIdSucursal(detalleFacturaClienteResponse.getIdSucursal());
/* 227:230 */           detalleFactura.setCantidad(detalleFacturaClienteResponse.getCantidad());
/* 228:231 */           detalleFactura.setFacturaCliente(facturaCliente);
/* 229:232 */           detalleFactura.setDescuento(detalleFacturaClienteResponse.getDescuento());
/* 230:233 */           detalleFactura.setDescuentoLinea(detalleFacturaClienteResponse.getDescuentoLinea());
/* 231:234 */           detalleFactura.setPrecio(detalleFacturaClienteResponse.getPrecio());
/* 232:235 */           detalleFactura.setIdDispositivoSincronizacion(detalleFacturaClienteResponse.getIdDispositivoSincronizacion());
/* 233:237 */           if (((detalleFactura.getPorcentajeDescuento() == null) || (detalleFactura.getPorcentajeDescuento().compareTo(BigDecimal.ZERO) == 0)) && 
/* 234:238 */             (detalleFactura.getDescuentoLinea().compareTo(BigDecimal.ZERO) > 0))
/* 235:    */           {
/* 236:239 */             BigDecimal precioLinea = detalleFactura.getCantidad().multiply(detalleFactura.getPrecio());
/* 237:    */             
/* 238:241 */             BigDecimal porcentajeDescuento = detalleFactura.getDescuentoLinea().divide(precioLinea, 8, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(4, RoundingMode.HALF_UP);
/* 239:242 */             detalleFactura.setPorcentajeDescuento(porcentajeDescuento);
/* 240:    */           }
/* 241:245 */           Producto producto = this.servicioProducto.cargaDetalle(detalleFacturaClienteResponse.getIdProducto().intValue());
/* 242:246 */           if (producto != null) {
/* 243:247 */             detalleFactura.setProducto(producto);
/* 244:    */           } else {
/* 245:249 */             throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { detalleFacturaClienteResponse.getIdProducto().toString() });
/* 246:    */           }
/* 247:251 */           if (producto.isIndicadorManejoPeso()) {
/* 248:252 */             detalleFactura.setPeso(detalleFacturaClienteResponse.getCantidad());
/* 249:    */           }
/* 250:254 */           detalleFactura.setCodigoIce(detalleFactura.getProducto().getCodigoIce());
/* 251:255 */           detalleFactura.setIndicadorPorcentajeIce(detalleFactura.getProducto().isIndicadorPorcentajeIce());
/* 252:256 */           detalleFactura.setIce(detalleFactura.getProducto().getIce());
/* 253:257 */           detalleFactura.setUnidadVenta(detalleFactura.getProducto().getUnidadVenta());
/* 254:258 */           facturaCliente.getListaDetalleFacturaCliente().add(detalleFactura);
/* 255:259 */           detalleFactura.setIndicadorImpuesto(detalleFactura.getProducto().isIndicadorImpuestos());
/* 256:261 */           if (detalleFactura.isIndicadorImpuesto()) {
/* 257:262 */             this.servicioFacturaCliente.obtenerImpuestosProductos(detalleFactura.getProducto(), detalleFactura);
/* 258:    */           }
/* 259:    */         }
/* 260:266 */         this.servicioFacturaCliente.totalizar(facturaCliente);
/* 261:    */         
/* 262:268 */         crearDespacho(facturaCliente, facturaResponse, listaDetalleFacturaClienteResponseDto);
/* 263:    */         
/* 264:270 */         facturaCliente.setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/* 265:271 */         this.servicioFacturaCliente.generarCuentaPorCobrar(facturaCliente);
/* 266:    */         
/* 267:273 */         this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 268:274 */         this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(facturaCliente, puntoDeVenta);
/* 269:275 */         facturaCliente = this.servicioFacturaCliente.guardar(facturaCliente);
/* 270:    */       }
/* 271:277 */       facturaResponse.setIdFacturaCliente(Integer.valueOf(facturaCliente.getId()));
/* 272:278 */       facturaResponse.setNumero(facturaCliente.getNumero());
/* 273:279 */       for (Map<String, String> filtrosDocumento = facturaCliente.getListaDetalleFacturaCliente().iterator(); filtrosDocumento.hasNext();)
/* 274:    */       {
/* 275:279 */         detalle = (DetalleFacturaCliente)filtrosDocumento.next();
/* 276:280 */         for (DetalleFacturaClienteResponseDto detalleFacturaClienteResponseDto : listaDetalleFacturaClienteResponseDto) {
/* 277:281 */           if (detalle.getIdDispositivoSincronizacion().equals(detalleFacturaClienteResponseDto.getIdDispositivoSincronizacion())) {
/* 278:282 */             detalleFacturaClienteResponseDto.setIdDetalleFacturaCliente(detalle.getId());
/* 279:    */           }
/* 280:    */         }
/* 281:    */       }
/* 282:    */       DetalleFacturaCliente detalle;
/* 283:287 */       for (CuentaPorCobrar cuentaPorCobrar : facturaCliente.getListaCuentaPorCobrar())
/* 284:    */       {
/* 285:288 */         SaldosFacturaResponseDto saldoFacturaResponseDto = new SaldosFacturaResponseDto();
/* 286:289 */         saldoFacturaResponseDto.setFecha(sdf.format(facturaCliente.getFecha()));
/* 287:290 */         saldoFacturaResponseDto.setNumero(facturaCliente.getNumero());
/* 288:291 */         saldoFacturaResponseDto.setSaldo(cuentaPorCobrar.getSaldo());
/* 289:292 */         saldoFacturaResponseDto.setSaldoBloqueado(cuentaPorCobrar.getValorBloqueado());
/* 290:293 */         saldoFacturaResponseDto.setIdFactura(Integer.valueOf(cuentaPorCobrar.getIdCuentaPorCobrar()));
/* 291:294 */         saldoFacturaResponseDto.setIndicadorEmitidaRetencion(Boolean.valueOf(cuentaPorCobrar.getFacturaCliente().isIndicadorEmisionRetencion()));
/* 292:295 */         saldoFacturaResponseDto.setIdFacturaCliente(Integer.valueOf(facturaCliente.getId()));
/* 293:296 */         facturaResponse.getListaSaldosFacturaResponseDto().add(saldoFacturaResponseDto);
/* 294:    */       }
/* 295:299 */       response.setSuccsess(true);
/* 296:300 */       response.setResponse(facturaResponse);
/* 297:301 */       return response;
/* 298:    */     }
/* 299:    */     catch (ExcepcionAS2Ventas e)
/* 300:    */     {
/* 301:303 */       e.printStackTrace();
/* 302:304 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 303:    */     }
/* 304:    */     catch (ExcepcionAS2Inventario e)
/* 305:    */     {
/* 306:306 */       e.printStackTrace();
/* 307:307 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 308:    */     }
/* 309:    */     catch (ExcepcionAS2Financiero e)
/* 310:    */     {
/* 311:309 */       e.printStackTrace();
/* 312:310 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 313:    */     }
/* 314:    */     catch (ExcepcionAS2 e)
/* 315:    */     {
/* 316:312 */       e.printStackTrace();
/* 317:313 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 318:    */     }
/* 319:    */     catch (AS2Exception e)
/* 320:    */     {
/* 321:315 */       e.printStackTrace();
/* 322:316 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 323:    */     }
/* 324:    */     catch (ParseException e)
/* 325:    */     {
/* 326:318 */       e.printStackTrace();
/* 327:319 */       error = "Mal formado el formato de la fecha";
/* 328:    */     }
/* 329:    */     catch (Exception e)
/* 330:    */     {
/* 331:321 */       e.printStackTrace();
/* 332:322 */       error = e.getMessage();
/* 333:    */     }
/* 334:324 */     response.setError(error);
/* 335:325 */     response.setSuccsess(false);
/* 336:326 */     return response;
/* 337:    */   }
/* 338:    */   
/* 339:    */   @POST
/* 340:    */   @Path("/anularFacturaCliente")
/* 341:    */   @Consumes({"application/json"})
/* 342:    */   @Produces({"application/json"})
/* 343:    */   public ProcesosResponseDto anularFacturaCliente(ConsultarFacturaClienteRequestDto request)
/* 344:    */     throws AS2Exception
/* 345:    */   {
/* 346:334 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 347:335 */     String error = "";
/* 348:    */     try
/* 349:    */     {
/* 350:337 */       Map<String, String> filtros = new HashMap();
/* 351:338 */       filtros.put("idOrganizacion", "" + request.getIdOrganizacion());
/* 352:339 */       filtros.put("idFacturaCliente", "" + request.getIdFacturaCliente());
/* 353:    */       
/* 354:341 */       FacturaCliente facturaCliente = this.servicioFacturaCliente.buscarFacturaCliente(filtros);
/* 355:    */       
/* 356:343 */       Cobro cobro = this.servicioCobro.buscarPorId(request.getIdCobro());
/* 357:345 */       if (!facturaCliente.getEstado().equals(Estado.ANULADO))
/* 358:    */       {
/* 359:346 */         if (!Estado.ANULADO.equals(cobro.getEstado())) {
/* 360:347 */           this.servicioCobro.anularCobro(cobro);
/* 361:    */         }
/* 362:349 */         DespachoCliente despachoCliente = new DespachoCliente();
/* 363:350 */         despachoCliente.setIdDespachoCliente(request.getIdDespacho().intValue());
/* 364:351 */         facturaCliente.setDespachoCliente(despachoCliente);
/* 365:352 */         facturaCliente.setFacturaClienteAgil(true);
/* 366:353 */         this.servicioFacturaCliente.anulaFacturaCliente(facturaCliente, true);
/* 367:    */       }
/* 368:356 */       response.setSuccsess(true);
/* 369:357 */       return response;
/* 370:    */     }
/* 371:    */     catch (ExcepcionAS2 e)
/* 372:    */     {
/* 373:359 */       e.printStackTrace();
/* 374:360 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 375:    */     }
/* 376:    */     catch (Exception e)
/* 377:    */     {
/* 378:362 */       e.printStackTrace();
/* 379:363 */       error = e.getMessage();
/* 380:    */     }
/* 381:365 */     response.setError(error);
/* 382:366 */     response.setSuccsess(false);
/* 383:367 */     return response;
/* 384:    */   }
/* 385:    */   
/* 386:    */   @POST
/* 387:    */   @Path("/consultarFacturaCliente")
/* 388:    */   @Consumes({"application/json"})
/* 389:    */   @Produces({"application/json"})
/* 390:    */   public EstadoFacturaClienteResponseDto consultarFacturaCliente(ConsultarFacturaClienteRequestDto request)
/* 391:    */     throws AS2Exception
/* 392:    */   {
/* 393:375 */     EstadoFacturaClienteResponseDto estadoFacturaClienteResponse = new EstadoFacturaClienteResponseDto();
/* 394:    */     
/* 395:377 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy.mm:ss");
/* 396:    */     
/* 397:379 */     Map<String, String> filtros = new HashMap();
/* 398:380 */     filtros.put("idOrganizacion", "" + request.getIdOrganizacion());
/* 399:381 */     filtros.put("idFacturaCliente", "" + request.getIdFacturaCliente());
/* 400:    */     
/* 401:    */ 
/* 402:384 */     FacturaCliente facturaCliente = this.servicioFacturaCliente.cargarDetalle(request.getIdFacturaCliente().intValue(), false);
/* 403:386 */     if (facturaCliente != null)
/* 404:    */     {
/* 405:387 */       estadoFacturaClienteResponse.setNumero(facturaCliente.getNumero());
/* 406:388 */       estadoFacturaClienteResponse.setAutorizacion(facturaCliente.getFacturaClienteSRI().getAutorizacion());
/* 407:389 */       estadoFacturaClienteResponse.setEstado(facturaCliente.getEstado());
/* 408:390 */       if (facturaCliente.getFacturaClienteSRI().getFechaAutorizacion() != null) {
/* 409:391 */         estadoFacturaClienteResponse.setFechaAutorizacion(sdf.format(facturaCliente.getFacturaClienteSRI().getFechaAutorizacion()));
/* 410:    */       } else {
/* 411:393 */         estadoFacturaClienteResponse.setFechaAutorizacion(null);
/* 412:    */       }
/* 413:395 */       estadoFacturaClienteResponse.setClaveAcceso(facturaCliente.getFacturaClienteSRI().getClaveAcceso());
/* 414:396 */       estadoFacturaClienteResponse.setIdFacturaCliente(Integer.valueOf(facturaCliente.getIdFacturaCliente()));
/* 415:397 */       if (facturaCliente.getDespachoCliente() != null)
/* 416:    */       {
/* 417:398 */         estadoFacturaClienteResponse.setDespacho(facturaCliente.getDespachoCliente().getNumero());
/* 418:399 */         estadoFacturaClienteResponse.setIdDespacho(facturaCliente.getDespachoCliente().getIdDespachoCliente());
/* 419:    */       }
/* 420:    */     }
/* 421:    */     else
/* 422:    */     {
/* 423:403 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201");
/* 424:    */     }
/* 425:405 */     return estadoFacturaClienteResponse;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void crearDespacho(FacturaCliente facturaCliente, FacturaClienteResponseDto facturaResponse, List<DetalleFacturaClienteResponseDto> listaDetalleFacturaClienteResponseDto)
/* 429:    */     throws ParseException, AS2Exception, ExcepcionAS2
/* 430:    */   {
/* 431:410 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 432:    */     
/* 433:    */ 
/* 434:413 */     DespachoCliente despachoCliente = new DespachoCliente();
/* 435:414 */     despachoCliente.setNumero("");
/* 436:415 */     despachoCliente.setEstado(Estado.PROCESADO);
/* 437:416 */     despachoCliente.setFecha(sdf.parse(facturaResponse.getFecha()));
/* 438:417 */     despachoCliente.setIdOrganizacion(facturaResponse.getIdOrganizacion());
/* 439:    */     
/* 440:419 */     Sucursal sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(facturaResponse.getIdSucursal()));
/* 441:420 */     despachoCliente.setSucursal(sucursal);
/* 442:    */     
/* 443:422 */     Empresa empresa = obtenerCliente(facturaResponse);
/* 444:423 */     despachoCliente.setEmpresa(empresa);
/* 445:    */     
/* 446:425 */     DireccionEmpresa direccionEmpresa = obtenerDireccionEmpresa(facturaResponse);
/* 447:    */     
/* 448:427 */     despachoCliente.setDireccionEmpresa(direccionEmpresa);
/* 449:428 */     despachoCliente.setDescripcion(facturaResponse.getDescripcion());
/* 450:429 */     despachoCliente.setPedidoCliente(null);
/* 451:    */     
/* 452:431 */     Documento documento = null;
/* 453:432 */     List<Documento> listaDocumentoDespacho = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DESPACHO_BODEGA, facturaResponse
/* 454:433 */       .getIdOrganizacion());
/* 455:434 */     if ((listaDocumentoDespacho != null) && (!listaDocumentoDespacho.isEmpty())) {
/* 456:435 */       documento = (Documento)listaDocumentoDespacho.get(0);
/* 457:    */     } else {
/* 458:437 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { Documento.class.getSimpleName() + ". " + DocumentoBase.DESPACHO_BODEGA });
/* 459:    */     }
/* 460:439 */     despachoCliente.setDocumento(documento);
/* 461:440 */     for (DetalleFacturaClienteResponseDto detalleFacturaClienteResponse : listaDetalleFacturaClienteResponseDto)
/* 462:    */     {
/* 463:442 */       DetalleDespachoCliente detalleDespachoCliente = new DetalleDespachoCliente();
/* 464:443 */       detalleDespachoCliente.setIdOrganizacion(facturaResponse.getIdOrganizacion());
/* 465:444 */       detalleDespachoCliente.setIdSucursal(facturaResponse.getIdSucursal());
/* 466:    */       
/* 467:446 */       Producto producto = this.servicioProducto.cargaDetalle(detalleFacturaClienteResponse.getIdProducto().intValue());
/* 468:447 */       if (producto != null) {
/* 469:448 */         detalleDespachoCliente.setProducto(producto);
/* 470:    */       } else {
/* 471:450 */         throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { detalleFacturaClienteResponse.getIdProducto().toString() });
/* 472:    */       }
/* 473:452 */       if (producto.isIndicadorManejoPeso()) {
/* 474:453 */         detalleDespachoCliente.setPeso(detalleFacturaClienteResponse.getCantidad());
/* 475:    */       }
/* 476:455 */       Bodega bodega = this.servicioBodega.buscarPorId(detalleFacturaClienteResponse.getIdBodega());
/* 477:456 */       if (bodega != null) {
/* 478:457 */         detalleDespachoCliente.setBodega(bodega);
/* 479:    */       } else {
/* 480:459 */         throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { detalleFacturaClienteResponse.getIdBodega().toString() });
/* 481:    */       }
/* 482:462 */       detalleDespachoCliente.setCantidad(detalleFacturaClienteResponse.getCantidad().setScale(4, RoundingMode.HALF_UP));
/* 483:463 */       detalleDespachoCliente.setPeso(detalleFacturaClienteResponse.getCantidad().setScale(2, RoundingMode.HALF_UP));
/* 484:464 */       detalleDespachoCliente.setUnidadVenta(detalleDespachoCliente.getProducto().getUnidadVenta());
/* 485:465 */       detalleDespachoCliente.setSaldo(detalleFacturaClienteResponse.getCantidad());
/* 486:466 */       detalleDespachoCliente.setDespachoCliente(facturaCliente.getDespachoCliente());
/* 487:467 */       despachoCliente.getListaDetalleDespachoCliente().add(detalleDespachoCliente);
/* 488:    */       
/* 489:469 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 490:470 */       inventarioProducto.setBodega(detalleDespachoCliente.getBodega());
/* 491:471 */       inventarioProducto.setCantidad(detalleFacturaClienteResponse.getCantidad());
/* 492:472 */       inventarioProducto.setOperacion(((Documento)listaDocumentoDespacho.get(0)).getOperacion());
/* 493:473 */       inventarioProducto.setDetalleDespachoCliente(detalleDespachoCliente);
/* 494:474 */       inventarioProducto.getDetalleDespachoCliente().setDespachoCliente(despachoCliente);
/* 495:475 */       inventarioProducto.getDetalleDespachoCliente().getDespachoCliente().setPedidoCliente(null);
/* 496:476 */       detalleDespachoCliente.setInventarioProducto(inventarioProducto);
/* 497:    */     }
/* 498:479 */     facturaCliente.setDespachoCliente(despachoCliente);
/* 499:    */   }
/* 500:    */   
/* 501:    */   public Empresa obtenerCliente(FacturaClienteResponseDto facturaResponse)
/* 502:    */     throws AS2Exception
/* 503:    */   {
/* 504:483 */     Empresa empresa = this.servicioEmpresa.buscarPorId(facturaResponse.getIdEmpresa());
/* 505:485 */     if (empresa != null) {
/* 506:486 */       empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 507:    */     } else {
/* 508:488 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { facturaResponse.getIdEmpresa().toString() });
/* 509:    */     }
/* 510:490 */     return empresa;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public DireccionEmpresa obtenerDireccionEmpresa(FacturaClienteResponseDto facturaResponse)
/* 514:    */     throws AS2Exception
/* 515:    */   {
/* 516:495 */     DireccionEmpresa direccionEmpresa = null;
/* 517:496 */     List<String> listaCampos = new ArrayList();
/* 518:497 */     listaCampos.add("ubicacion");
/* 519:498 */     direccionEmpresa = (DireccionEmpresa)this.servicioDireccionEmpresa.cargarDetalle(DireccionEmpresa.class, facturaResponse.getIdDireccionEmpresa().intValue(), listaCampos);
/* 520:    */     
/* 521:500 */     return direccionEmpresa;
/* 522:    */   }
/* 523:    */   
/* 524:    */   @POST
/* 525:    */   @Path("/obtenerEstadisticaVentas")
/* 526:    */   @Consumes({"application/json"})
/* 527:    */   @Produces({"application/json"})
/* 528:    */   public ProcesosResponseDto obtenerEstadisticaVentas(EstadisticaVentaClienteRequestDto request)
/* 529:    */     throws AS2Exception
/* 530:    */   {
/* 531:508 */     String error = null;
/* 532:509 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 533:    */     try
/* 534:    */     {
/* 535:511 */       SimpleDateFormat sdfFiltro = new SimpleDateFormat("dd/MM/yyyy");
/* 536:512 */       EstadisticaVentaClienteResponseDto estadistica = new EstadisticaVentaClienteResponseDto();
/* 537:513 */       List<EstadisticaVentaClienteResponseDto> listaEstadistica = new ArrayList();
/* 538:    */       
/* 539:515 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 540:516 */       Date fechaDesde = sdf.parse(request.getFechaDesde());
/* 541:517 */       Date fechaHasta = sdf.parse(request.getFechaHasta());
/* 542:518 */       Producto producto = null;
/* 543:519 */       if ((request.getIdProducto() != null) && (request.getIdProducto().intValue() != 0))
/* 544:    */       {
/* 545:520 */         producto = this.servicioProducto.buscarPorId(request.getIdProducto().intValue());
/* 546:521 */         if (producto == null) {
/* 547:522 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { Producto.class.getSimpleName() + ": " + request.getIdProducto() });
/* 548:    */         }
/* 549:    */       }
/* 550:525 */       boolean indicadorResumen = request.getResumen() == null ? false : request.getResumen().booleanValue();
/* 551:526 */       Empresa empresa = this.servicioEmpresa.buscarPorId(request.getIdEmpresa());
/* 552:527 */       if ((empresa == null) || (empresa.getCliente() == null)) {
/* 553:528 */         throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { Cliente.class.getSimpleName() + ": " + request.getIdEmpresa() });
/* 554:    */       }
/* 555:    */       BigDecimal valorVendido;
/* 556:531 */       if ((producto == null) && (indicadorResumen))
/* 557:    */       {
/* 558:532 */         BigDecimal creditoDisponible = empresa.getCliente().getCreditoDisponible();
/* 559:533 */         estadistica.setCreditoMaximo(empresa.getCliente().getCreditoMaximo());
/* 560:534 */         estadistica.setCreditoDisponible(creditoDisponible);
/* 561:535 */         valorVendido = null;
/* 562:536 */         Integer cantidadFacturas = null;
/* 563:    */         
/* 564:538 */         Object[] resumen = this.servicioReporteVenta.getResumenVentaCliente(fechaDesde, fechaHasta, request.getIdEmpresa().intValue());
/* 565:539 */         if (resumen != null)
/* 566:    */         {
/* 567:540 */           valorVendido = (BigDecimal)resumen[0];
/* 568:541 */           cantidadFacturas = Integer.valueOf(((Long)resumen[1]).intValue());
/* 569:    */         }
/* 570:543 */         estadistica.setCantidad(cantidadFacturas);
/* 571:544 */         estadistica.setValor(valorVendido);
/* 572:    */         
/* 573:    */ 
/* 574:547 */         Map<String, String> filtrosUltimaFactura = new HashMap();
/* 575:548 */         filtrosUltimaFactura.put("empresa.idEmpresa", request.getIdEmpresa() + "");
/* 576:549 */         filtrosUltimaFactura.put("estado", "!=" + Estado.ANULADO.name());
/* 577:550 */         filtrosUltimaFactura.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.name());
/* 578:551 */         filtrosUltimaFactura.put("fecha", OperacionEnum.BETWEEN.name() + sdfFiltro.format(fechaDesde) + "~" + sdfFiltro.format(fechaHasta));
/* 579:552 */         List<FacturaCliente> listaFactura = this.servicioFacturaCliente.obtenerListaPorPagina(0, 1, "fecha", false, filtrosUltimaFactura);
/* 580:553 */         if (!listaFactura.isEmpty())
/* 581:    */         {
/* 582:554 */           FacturaCliente ultimaFactura = (FacturaCliente)listaFactura.get(0);
/* 583:555 */           estadistica.setFechaUltimaFactura(sdf.format(ultimaFactura.getFecha()));
/* 584:556 */           estadistica.setValorUltimaFactura(ultimaFactura.getTotal().subtract(ultimaFactura.getDescuento()));
/* 585:    */         }
/* 586:559 */         response.setResponse(estadistica);
/* 587:    */       }
/* 588:560 */       else if ((producto == null) && (!indicadorResumen))
/* 589:    */       {
/* 590:562 */         List<Object[]> listaProductosMasVendidos = this.servicioReporteVenta.getResumenProductosMasVendidosCliente(fechaDesde, fechaHasta, request
/* 591:563 */           .getIdEmpresa().intValue());
/* 592:564 */         for (Object[] row : listaProductosMasVendidos)
/* 593:    */         {
/* 594:565 */           Integer idProducto = (Integer)row[0];
/* 595:566 */           EstadisticaVentaClienteResponseDto estadisticaProducto = new EstadisticaVentaClienteResponseDto();
/* 596:567 */           ProductoResponseDto productoResponse = new ProductoResponseDto();
/* 597:568 */           productoResponse.setIdProducto(idProducto);
/* 598:569 */           productoResponse.setCodigo((String)row[1]);
/* 599:570 */           productoResponse.setNombre((String)row[2]);
/* 600:571 */           estadisticaProducto.setProducto(productoResponse);
/* 601:572 */           estadisticaProducto.setCantidad(Integer.valueOf(((BigDecimal)row[3]).intValue()));
/* 602:573 */           estadisticaProducto.setValor((BigDecimal)row[4]);
/* 603:    */           
/* 604:    */ 
/* 605:576 */           Map<String, String> filtrosUltimaVentaProducto = new HashMap();
/* 606:577 */           filtrosUltimaVentaProducto.put("producto.idProducto", idProducto + "");
/* 607:578 */           filtrosUltimaVentaProducto.put("facturaCliente.empresa.idEmpresa", request.getIdEmpresa() + "");
/* 608:579 */           filtrosUltimaVentaProducto.put("facturaCliente.estado", "!=" + Estado.ANULADO.name());
/* 609:580 */           filtrosUltimaVentaProducto.put("facturaCliente.documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.name());
/* 610:581 */           filtrosUltimaVentaProducto.put("facturaCliente.fecha", OperacionEnum.BETWEEN.name() + sdfFiltro.format(fechaDesde) + "~" + sdfFiltro
/* 611:582 */             .format(fechaHasta));
/* 612:583 */           List<String> listaCampos = new ArrayList();
/* 613:584 */           listaCampos.add("facturaCliente");
/* 614:585 */           List<DetalleFacturaCliente> listaDetalleFacturaCliente = this.servicioDetalleFacturaCliente.obtenerListaPorPagina(DetalleFacturaCliente.class, 0, 1, "facturaCliente.fecha", false, filtrosUltimaVentaProducto, listaCampos);
/* 615:587 */           if (!listaDetalleFacturaCliente.isEmpty())
/* 616:    */           {
/* 617:588 */             DetalleFacturaCliente detalle = (DetalleFacturaCliente)listaDetalleFacturaCliente.get(0);
/* 618:589 */             estadisticaProducto.setFechaUltimaFactura(sdf.format(detalle.getFacturaCliente().getFecha()));
/* 619:590 */             BigDecimal valorProducto = detalle.getPrecioLinea().subtract(detalle.getDescuentoLinea());
/* 620:591 */             estadisticaProducto.setValorUltimaFactura(valorProducto);
/* 621:    */           }
/* 622:594 */           listaEstadistica.add(estadisticaProducto);
/* 623:    */         }
/* 624:596 */         response.setResponse(listaEstadistica);
/* 625:    */       }
/* 626:597 */       else if (producto != null)
/* 627:    */       {
/* 628:598 */         List<Object[]> listaReporte = this.servicioReporteVenta.getDetalleVentasProductoCliente(fechaDesde, fechaHasta, request.getIdEmpresa().intValue(), producto
/* 629:599 */           .getIdProducto());
/* 630:600 */         for (Object[] row : listaReporte)
/* 631:    */         {
/* 632:601 */           EstadisticaVentaClienteResponseDto estadisticaDetalle = new EstadisticaVentaClienteResponseDto();
/* 633:602 */           estadisticaDetalle.setFechaUltimaFactura(sdf.format((Date)row[0]));
/* 634:603 */           estadisticaDetalle.setCantidad(Integer.valueOf(((BigDecimal)row[1]).intValue()));
/* 635:604 */           estadisticaDetalle.setValor((BigDecimal)row[2]);
/* 636:605 */           listaEstadistica.add(estadisticaDetalle);
/* 637:    */         }
/* 638:607 */         response.setResponse(listaEstadistica);
/* 639:    */       }
/* 640:610 */       response.setSuccsess(true);
/* 641:    */       
/* 642:612 */       return response;
/* 643:    */     }
/* 644:    */     catch (Exception e)
/* 645:    */     {
/* 646:614 */       e.printStackTrace();
/* 647:615 */       error = e.getMessage();
/* 648:    */       
/* 649:617 */       response.setSuccsess(false);
/* 650:618 */       response.setError(error);
/* 651:    */     }
/* 652:619 */     return response;
/* 653:    */   }
/* 654:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.ventas.rest.FacturaServicioRest
 * JD-Core Version:    0.7.0.1
 */