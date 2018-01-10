/*   1:    */ package com.asinfo.as2.webservices.procesos.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   9:    */ import com.asinfo.as2.entities.Banco;
/*  10:    */ import com.asinfo.as2.entities.Caja;
/*  11:    */ import com.asinfo.as2.entities.Cliente;
/*  12:    */ import com.asinfo.as2.entities.Cobro;
/*  13:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  14:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  15:    */ import com.asinfo.as2.entities.DetalleCobro;
/*  16:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  17:    */ import com.asinfo.as2.entities.Documento;
/*  18:    */ import com.asinfo.as2.entities.Empresa;
/*  19:    */ import com.asinfo.as2.entities.FormaPago;
/*  20:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  21:    */ import com.asinfo.as2.entities.Sucursal;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.enumeraciones.MensajeErrorEnum;
/*  25:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  28:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  30:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  31:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  32:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  33:    */ import com.asinfo.as2.webservices.procesos.ServicioWebCobro;
/*  34:    */ import com.asinfo.as2.ws.respuesta.MensajeWSEntity;
/*  35:    */ import com.asinfo.as2.ws.respuesta.RespuestaWSEntity;
/*  36:    */ import com.asinfo.as2.ws.ventas.model.CobroWSEntity;
/*  37:    */ import com.asinfo.as2.ws.ventas.model.DetalleCobroWSEntity;
/*  38:    */ import com.asinfo.as2.ws.ventas.model.DetalleFormaCobroWSEntity;
/*  39:    */ import java.io.PrintStream;
/*  40:    */ import java.math.BigDecimal;
/*  41:    */ import java.util.ArrayList;
/*  42:    */ import java.util.Calendar;
/*  43:    */ import java.util.HashMap;
/*  44:    */ import java.util.List;
/*  45:    */ import java.util.Map;
/*  46:    */ import javax.ejb.ApplicationException;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.ejb.TransactionAttribute;
/*  49:    */ import javax.ejb.TransactionAttributeType;
/*  50:    */ import javax.jws.WebService;
/*  51:    */ 
/*  52:    */ @WebService(endpointInterface="com.asinfo.as2.webservices.procesos.ServicioWebCobro", serviceName="CobroService", portName="CobroPort")
/*  53:    */ @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  54:    */ @ApplicationException(rollback=true)
/*  55:    */ public class ServicioWebCobroImpl
/*  56:    */   extends Exception
/*  57:    */   implements ServicioWebCobro
/*  58:    */ {
/*  59:    */   private static final long serialVersionUID = 1549892092907607317L;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioCondicionPago servicioCondicionPago;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioImpuesto servicioImpuesto;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioCobro servicioCobro;
/*  70:    */   @EJB
/*  71:    */   private transient ServicioFormaPago servicioFormaPago;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioEmpresa servicioEmpresa;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioDocumento servicioDocumento;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioGenerico<Banco> servicioBanco;
/*  80:    */   @EJB
/*  81:    */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  82:    */   @EJB
/*  83:    */   private transient ServicioSucursal servicioSucursal;
/*  84:    */   
/*  85:    */   public RespuestaWSEntity[] crearCobro(int organizacion, CobroWSEntity[] listaCobro)
/*  86:    */   {
/*  87: 86 */     System.out.println("ENTRO AL METODO CREAR COBRO CRUZ BLANCA");
/*  88:    */     
/*  89: 88 */     List<RespuestaWSEntity> listaRespuesta = new ArrayList();
/*  90: 89 */     for (CobroWSEntity cobroWS : listaCobro)
/*  91:    */     {
/*  92: 91 */       RespuestaWSEntity respuesta = new RespuestaWSEntity();
/*  93: 92 */       respuesta.setReferenciaDocumento(cobroWS.getNumero());
/*  94: 93 */       List<MensajeWSEntity> listaMensaje = new ArrayList();
/*  95:    */       
/*  96:    */ 
/*  97: 96 */       Empresa empresa = comprobarDatos(organizacion, cobroWS, listaMensaje);
/*  98: 97 */       if (listaMensaje.size() > 0)
/*  99:    */       {
/* 100: 98 */         respuesta.setEstado("ERROR");
/* 101: 99 */         respuesta.setListaMensaje((MensajeWSEntity[])listaMensaje.toArray(new MensajeWSEntity[0]));
/* 102:100 */         listaRespuesta.add(respuesta);
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106:105 */         Cobro cobro = new Cobro();
/* 107:106 */         cobro.setUsuarioCreacion(cobroWS.getUsuario());
/* 108:107 */         cobro.setIdOrganizacion(organizacion);
/* 109:108 */         cobro.setValor(cobroWS.getValorTotal());
/* 110:109 */         cobro.setDescripcion(cobroWS.getDescripcion());
/* 111:110 */         cobro.setNumero(cobroWS.getNumero());
/* 112:111 */         cobro.setFecha(cobroWS.getFecha().getTime());
/* 113:112 */         cobro.setEstado(Estado.ELABORADO);
/* 114:113 */         cobro.setEmpresa(empresa);
/* 115:    */         try
/* 116:    */         {
/* 117:126 */           List<Documento> listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.COBRO_CLIENTE, organizacion);
/* 118:127 */           if ((listaDocumento != null) && (!listaDocumento.isEmpty())) {
/* 119:128 */             cobro.setDocumento((Documento)listaDocumento.get(0));
/* 120:    */           }
/* 121:    */         }
/* 122:    */         catch (ExcepcionAS2 e)
/* 123:    */         {
/* 124:131 */           MensajeWSEntity mensajeError = new MensajeWSEntity(MensajeErrorEnum.ERROR_501, e.getCodigoExcepcion());
/* 125:132 */           listaMensaje.add(mensajeError);
/* 126:    */         }
/* 127:134 */         cobro.setListaDetalleCobro(new ArrayList());
/* 128:135 */         cobro.setListaDetalleFormaCobro(new ArrayList());
/* 129:136 */         String establecimiento = "001";
/* 130:137 */         String puntoDeVenta = "001";
/* 131:138 */         Sucursal sucursal = null;
/* 132:139 */         BigDecimal valor = BigDecimal.ZERO;
/* 133:140 */         for (DetalleCobroWSEntity detalle : cobroWS.getListaDetalleCobro())
/* 134:    */         {
/* 135:141 */           valor = detalle.getValorCobro();
/* 136:142 */           List<CuentaPorCobrar> listaCuentaPorCobrar = new ArrayList();
/* 137:143 */           if (cobro.getEmpresa() != null) {
/* 138:144 */             listaCuentaPorCobrar = this.servicioCobro.buscarCuentaPorCobrarPorNumeroFacturaCliente(organizacion, cobro.getEmpresa().getId(), detalle
/* 139:145 */               .getEstablecimientoFactura() + "-" + detalle.getPuntoVentaFactura() + "-" + detalle.getNumeroFactura());
/* 140:    */           }
/* 141:147 */           if ((listaCuentaPorCobrar != null) && (listaCuentaPorCobrar.size() > 0))
/* 142:    */           {
/* 143:148 */             for (CuentaPorCobrar cuentaPorCobrar : listaCuentaPorCobrar)
/* 144:    */             {
/* 145:149 */               BigDecimal valorDetalle = BigDecimal.ZERO;
/* 146:150 */               if (valor.compareTo(BigDecimal.ZERO) != 0)
/* 147:    */               {
/* 148:151 */                 if (valor.compareTo(cuentaPorCobrar.getSaldo()) == 1) {
/* 149:152 */                   valorDetalle = cuentaPorCobrar.getSaldo();
/* 150:    */                 } else {
/* 151:154 */                   valorDetalle = valor;
/* 152:    */                 }
/* 153:156 */                 valor = valor.subtract(valorDetalle);
/* 154:    */                 
/* 155:158 */                 DetalleCobro detalleCobro = new DetalleCobro();
/* 156:159 */                 detalleCobro.setUsuarioCreacion(cobroWS.getUsuario());
/* 157:    */                 
/* 158:161 */                 detalleCobro.setCobro(cobro);
/* 159:    */                 
/* 160:163 */                 detalleCobro.setValor(valorDetalle);
/* 161:    */                 
/* 162:165 */                 detalleCobro.setCuentaPorCobrar(cuentaPorCobrar);
/* 163:    */                 
/* 164:167 */                 cobro.getListaDetalleCobro().add(detalleCobro);
/* 165:168 */                 establecimiento = detalle.getEstablecimientoFactura();
/* 166:169 */                 puntoDeVenta = detalle.getPuntoVentaFactura();
/* 167:170 */                 sucursal = this.servicioSucursal.buscarPorCodigo(organizacion, establecimiento);
/* 168:171 */                 detalleCobro.setIdOrganizacion(organizacion);
/* 169:172 */                 if (sucursal != null)
/* 170:    */                 {
/* 171:173 */                   detalleCobro.setIdSucursal(sucursal.getId());
/* 172:    */                 }
/* 173:    */                 else
/* 174:    */                 {
/* 175:175 */                   MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe establecimiento con el codigo dado");
/* 176:    */                   
/* 177:177 */                   listaMensaje.add(mensaje);
/* 178:    */                 }
/* 179:    */               }
/* 180:    */             }
/* 181:181 */             if (valor.compareTo(BigDecimal.ZERO) != 0)
/* 182:    */             {
/* 183:184 */               MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "El valor es mayor a la suma de las cuentas por cobrar de la factura:" + detalle.getEstablecimientoFactura() + "-" + detalle.getPuntoVentaFactura() + "-" + detalle.getNumeroFactura());
/* 184:185 */               listaMensaje.add(mensaje);
/* 185:    */             }
/* 186:    */           }
/* 187:    */           else
/* 188:    */           {
/* 189:188 */             MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe cuenta por cobrar para la factura dada del cliente configurado");
/* 190:    */             
/* 191:190 */             listaMensaje.add(mensaje);
/* 192:    */           }
/* 193:    */         }
/* 194:194 */         for (DetalleFormaCobroWSEntity detalle : cobroWS.getListaDetalleFormaCobro())
/* 195:    */         {
/* 196:196 */           DetalleFormaCobro detalleFormaCobro = new DetalleFormaCobro();
/* 197:197 */           detalleFormaCobro.setUsuarioCreacion(cobroWS.getUsuario());
/* 198:198 */           detalleFormaCobro.setIdOrganizacion(organizacion);
/* 199:199 */           detalleFormaCobro.setCobro(cobro);
/* 200:200 */           detalleFormaCobro.setFormaPago(cobro.getEmpresa().getCliente().getFormaPago());
/* 201:201 */           detalleFormaCobro.setValor(detalle.getValor());
/* 202:    */           
/* 203:203 */           detalleFormaCobro.setDocumentoReferencia(detalle.getDocumentoReferencia());
/* 204:    */           try
/* 205:    */           {
/* 206:206 */             Object filters = new HashMap();
/* 207:207 */             ((Map)filters).put("codigo", "=" + detalle.getFormaPago());
/* 208:208 */             ((Map)filters).put("idOrganizacion", organizacion + "");
/* 209:209 */             FormaPago formaPago = this.servicioFormaPago.buscarFormaPago((Map)filters);
/* 210:210 */             detalleFormaCobro.setFormaPago(formaPago);
/* 211:    */           }
/* 212:    */           catch (ExcepcionAS2 e1)
/* 213:    */           {
/* 214:212 */             MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe la formaPago con el código dado");
/* 215:213 */             listaMensaje.add(mensaje);
/* 216:    */           }
/* 217:215 */           detalleFormaCobro.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.buscarPorNumeroCuentaBancaria(organizacion, detalle
/* 218:216 */             .getNumeroCuentaBancariaPago()));
/* 219:217 */           detalleFormaCobro.setCuentaContableFormaCobro(detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/* 220:    */           try
/* 221:    */           {
/* 222:219 */             detalleFormaCobro.setBanco((Banco)this.servicioBanco.buscarPorCodigo(Banco.class, organizacion, detalle.getBancoOrigen()));
/* 223:    */           }
/* 224:    */           catch (AS2Exception localAS2Exception1) {}
/* 225:223 */           Object filters = new HashMap();
/* 226:224 */           ((Map)filters).put("idOrganizacion", "" + organizacion);
/* 227:225 */           ((Map)filters).put("sucursal.codigo", establecimiento);
/* 228:226 */           ((Map)filters).put("codigo", puntoDeVenta);
/* 229:    */           try
/* 230:    */           {
/* 231:228 */             detalleFormaCobro.setCaja((Caja)this.servicioPuntoDeVenta.buscarPuntoDeVenta((Map)filters).getListaCaja().get(0));
/* 232:    */           }
/* 233:    */           catch (ExcepcionAS2 e1)
/* 234:    */           {
/* 235:230 */             MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el punto de venta");
/* 236:231 */             listaMensaje.add(mensaje);
/* 237:    */           }
/* 238:233 */           sucursal = this.servicioSucursal.buscarPorCodigo(organizacion, establecimiento);
/* 239:234 */           if (sucursal != null)
/* 240:    */           {
/* 241:235 */             cobro.setSucursal(sucursal);
/* 242:236 */             detalleFormaCobro.setIdSucursal(sucursal.getId());
/* 243:    */           }
/* 244:    */           else
/* 245:    */           {
/* 246:238 */             MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe establecimiento con el codigo dado");
/* 247:239 */             listaMensaje.add(mensaje);
/* 248:    */           }
/* 249:242 */           cobro.getListaDetalleFormaCobro().add(detalleFormaCobro);
/* 250:    */         }
/* 251:    */         try
/* 252:    */         {
/* 253:246 */           if (listaMensaje.size() == 0)
/* 254:    */           {
/* 255:247 */             this.servicioCobro.guardar(cobro);
/* 256:248 */             respuesta.setEstado("REGISTRADO");
/* 257:    */           }
/* 258:    */           else
/* 259:    */           {
/* 260:250 */             respuesta.setEstado("ERROR");
/* 261:    */           }
/* 262:    */         }
/* 263:    */         catch (Exception e)
/* 264:    */         {
/* 265:254 */           respuesta.setEstado("ERROR");
/* 266:255 */           MensajeWSEntity mensajeError = new MensajeWSEntity(MensajeErrorEnum.ERROR_501, e.getMessage());
/* 267:256 */           listaMensaje.add(mensajeError);
/* 268:    */         }
/* 269:258 */         respuesta.setListaMensaje((MensajeWSEntity[])listaMensaje.toArray(new MensajeWSEntity[0]));
/* 270:259 */         listaRespuesta.add(respuesta);
/* 271:    */       }
/* 272:    */     }
/* 273:261 */     return (RespuestaWSEntity[])listaRespuesta.toArray(new RespuestaWSEntity[0]);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public CobroWSEntity consultarCobro(int organizacion, String numero)
/* 277:    */     throws AS2Exception
/* 278:    */   {
/* 279:266 */     CobroWSEntity cobroWSEntity = null;
/* 280:267 */     Cobro cobro = null;
/* 281:    */     try
/* 282:    */     {
/* 283:269 */       cobro = this.servicioCobro.buscarPorNumero(organizacion, numero);
/* 284:270 */       if (cobro != null) {
/* 285:271 */         return new CobroWSEntity(cobro);
/* 286:    */       }
/* 287:274 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { numero });
/* 288:    */     }
/* 289:    */     catch (Exception e)
/* 290:    */     {
/* 291:277 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { numero });
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   private Empresa comprobarDatos(int organizacion, CobroWSEntity cobro, List<MensajeWSEntity> listaMensaje)
/* 296:    */   {
/* 297:282 */     Empresa empresa = null;
/* 298:283 */     if ((cobro.getNumero() == null) || (cobro.getNumero().length() < 1) || (cobro.getNumero().length() > 10))
/* 299:    */     {
/* 300:284 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo numero");
/* 301:285 */       listaMensaje.add(mensaje);
/* 302:    */     }
/* 303:287 */     if (cobro.getFecha() == null)
/* 304:    */     {
/* 305:288 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_302, "Falta el campo fecha");
/* 306:289 */       listaMensaje.add(mensaje);
/* 307:    */     }
/* 308:291 */     if ((cobro.getIdentificacionCliente().length() < 4) || (cobro.getIdentificacionCliente().length() > 20))
/* 309:    */     {
/* 310:292 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo identificacionCliente");
/* 311:293 */       listaMensaje.add(mensaje);
/* 312:    */     }
/* 313:295 */     if ((cobro.getDescripcion() != null) && (cobro.getDescripcion().length() > 200))
/* 314:    */     {
/* 315:296 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo descripcion");
/* 316:297 */       listaMensaje.add(mensaje);
/* 317:    */     }
/* 318:    */     MensajeWSEntity mensaje;
/* 319:    */     try
/* 320:    */     {
/* 321:300 */       empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(organizacion, cobro.getIdentificacionCliente());
/* 322:    */     }
/* 323:    */     catch (ExcepcionAS2 e)
/* 324:    */     {
/* 325:302 */       mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el cliente con la identificacion dada");
/* 326:303 */       listaMensaje.add(mensaje);
/* 327:    */     }
/* 328:305 */     BigDecimal suma = BigDecimal.ZERO;
/* 329:306 */     for (DetalleCobroWSEntity detalle : cobro.getListaDetalleCobro())
/* 330:    */     {
/* 331:307 */       if ((detalle.getEstablecimientoFactura() == null) || (detalle.getEstablecimientoFactura().length() != 3))
/* 332:    */       {
/* 333:308 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo establecimientoFactura");
/* 334:309 */         listaMensaje.add(mensaje);
/* 335:    */       }
/* 336:311 */       if ((detalle.getPuntoVentaFactura() == null) || (detalle.getPuntoVentaFactura().length() != 3))
/* 337:    */       {
/* 338:312 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo puntoVentaFactura");
/* 339:313 */         listaMensaje.add(mensaje);
/* 340:    */       }
/* 341:315 */       if ((detalle.getNumeroFactura() == null) || (detalle.getNumeroFactura().length() > 9))
/* 342:    */       {
/* 343:316 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo numeroFactura");
/* 344:317 */         listaMensaje.add(mensaje);
/* 345:    */       }
/* 346:319 */       if (detalle.getValorCobro() == null)
/* 347:    */       {
/* 348:320 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_302, "Falta el campo valorCobro");
/* 349:321 */         listaMensaje.add(mensaje);
/* 350:    */       }
/* 351:    */       else
/* 352:    */       {
/* 353:323 */         suma = suma.add(detalle.getValorCobro());
/* 354:    */       }
/* 355:    */     }
/* 356:    */     MensajeWSEntity mensaje;
/* 357:326 */     if (!suma.equals(cobro.getValorTotal()))
/* 358:    */     {
/* 359:327 */       System.out.println("valorTotal:" + cobro.getValorTotal());
/* 360:328 */       System.out.println("sumaValorCobro" + suma);
/* 361:329 */       mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "No coincide el valorTotal con la suma de los valorCobro");
/* 362:330 */       listaMensaje.add(mensaje);
/* 363:    */     }
/* 364:333 */     for (DetalleFormaCobroWSEntity detalle : cobro.getListaDetalleFormaCobro())
/* 365:    */     {
/* 366:    */       try
/* 367:    */       {
/* 368:335 */         Map<String, String> filters = new HashMap();
/* 369:336 */         filters.put("codigo", "=" + detalle.getFormaPago());
/* 370:337 */         filters.put("idOrganizacion", organizacion + "");
/* 371:338 */         this.servicioFormaPago.buscarFormaPago(filters);
/* 372:    */       }
/* 373:    */       catch (ExcepcionAS2 e1)
/* 374:    */       {
/* 375:340 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe la formaPago con el código dado");
/* 376:341 */         listaMensaje.add(mensaje);
/* 377:    */       }
/* 378:343 */       if (this.servicioCuentaBancariaOrganizacion.buscarPorNumeroCuentaBancaria(organizacion, detalle.getNumeroCuentaBancariaPago()) == null)
/* 379:    */       {
/* 380:344 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe la cuentaBancaria con el numero dado");
/* 381:345 */         listaMensaje.add(mensaje);
/* 382:    */       }
/* 383:    */       try
/* 384:    */       {
/* 385:348 */         if (this.servicioBanco.buscarPorCodigo(Banco.class, organizacion, detalle.getBancoOrigen()) == null)
/* 386:    */         {
/* 387:349 */           MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el banco con el codigo dado");
/* 388:350 */           listaMensaje.add(mensaje);
/* 389:    */         }
/* 390:    */       }
/* 391:    */       catch (AS2Exception localAS2Exception) {}
/* 392:    */     }
/* 393:357 */     Cobro cobroprov = this.servicioCobro.buscarPorNumero(organizacion, cobro.getNumero());
/* 394:358 */     if (cobroprov != null)
/* 395:    */     {
/* 396:359 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_101, "Ya existe un cobro con el numero dado");
/* 397:360 */       listaMensaje.add(mensaje);
/* 398:    */     }
/* 399:363 */     return empresa;
/* 400:    */   }
/* 401:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.procesos.impl.ServicioWebCobroImpl
 * JD-Core Version:    0.7.0.1
 */