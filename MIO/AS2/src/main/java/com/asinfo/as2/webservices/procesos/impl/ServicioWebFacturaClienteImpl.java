/*   1:    */ package com.asinfo.as2.webservices.procesos.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   9:    */ import com.asinfo.as2.entities.CondicionPago;
/*  10:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  11:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  15:    */ import com.asinfo.as2.entities.Producto;
/*  16:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.entities.Ubicacion;
/*  19:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  20:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  21:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  22:    */ import com.asinfo.as2.enumeraciones.MensajeErrorEnum;
/*  23:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  24:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  25:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  26:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  27:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  31:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  32:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  33:    */ import com.asinfo.as2.webservices.procesos.ServicioWebFacturaCliente;
/*  34:    */ import com.asinfo.as2.ws.respuesta.MensajeWSEntity;
/*  35:    */ import com.asinfo.as2.ws.respuesta.RespuestaWSEntity;
/*  36:    */ import com.asinfo.as2.ws.ventas.model.DetalleFacturaClienteWSEntity;
/*  37:    */ import com.asinfo.as2.ws.ventas.model.FacturaClienteWSEntity;
/*  38:    */ import java.io.PrintStream;
/*  39:    */ import java.util.ArrayList;
/*  40:    */ import java.util.Calendar;
/*  41:    */ import java.util.HashMap;
/*  42:    */ import java.util.List;
/*  43:    */ import java.util.Map;
/*  44:    */ import javax.ejb.ApplicationException;
/*  45:    */ import javax.ejb.EJB;
/*  46:    */ import javax.ejb.TransactionAttribute;
/*  47:    */ import javax.ejb.TransactionAttributeType;
/*  48:    */ import javax.jws.WebService;
/*  49:    */ 
/*  50:    */ @WebService(endpointInterface="com.asinfo.as2.webservices.procesos.ServicioWebFacturaCliente", serviceName="FacturaClienteService", portName="FacturaClientePort")
/*  51:    */ @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  52:    */ @ApplicationException(rollback=true)
/*  53:    */ public class ServicioWebFacturaClienteImpl
/*  54:    */   extends Exception
/*  55:    */   implements ServicioWebFacturaCliente
/*  56:    */ {
/*  57:    */   private static final long serialVersionUID = 1549892092907607317L;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioDocumento servicioDocumento;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioCondicionPago servicioCondicionPago;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioEmpresa servicioEmpresa;
/*  70:    */   @EJB
/*  71:    */   private transient ServicioProducto servicioProducto;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioImpuesto servicioImpuesto;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioSucursal servicioSucursal;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioCobro servicioCobro;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioFormaPago servicioFormaPago;
/*  80:    */   @EJB
/*  81:    */   private ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  82:    */   
/*  83:    */   public RespuestaWSEntity[] crearFacturaCliente(int organizacion, FacturaClienteWSEntity[] listaFacturaCliente)
/*  84:    */   {
/*  85: 91 */     List<RespuestaWSEntity> listaRespuesta = new ArrayList();
/*  86: 92 */     for (FacturaClienteWSEntity facturaClienteWSEntity : listaFacturaCliente)
/*  87:    */     {
/*  88: 94 */       RespuestaWSEntity respuesta = new RespuestaWSEntity();
/*  89: 95 */       respuesta.setReferenciaDocumento(facturaClienteWSEntity.getEstablecimiento() + "-" + facturaClienteWSEntity.getPuntoDeVenta() + "-" + facturaClienteWSEntity.getNumero());
/*  90: 96 */       List<MensajeWSEntity> listaMensaje = new ArrayList();
/*  91:    */       
/*  92:    */ 
/*  93: 99 */       listaMensaje = comprobarDatos(organizacion, facturaClienteWSEntity);
/*  94:100 */       if (listaMensaje.size() > 0)
/*  95:    */       {
/*  96:101 */         respuesta.setEstado("ERROR");
/*  97:102 */         respuesta.setListaMensaje((MensajeWSEntity[])listaMensaje.toArray(new MensajeWSEntity[0]));
/*  98:103 */         listaRespuesta.add(respuesta);
/*  99:    */       }
/* 100:    */       else
/* 101:    */       {
/* 102:106 */         FacturaCliente facturaCliente = new FacturaCliente();
/* 103:107 */         PuntoDeVenta puntoDeVenta = null;
/* 104:    */         try
/* 105:    */         {
/* 106:113 */           Documento documento = null;
/* 107:114 */           if ((facturaClienteWSEntity.getDocumento() != null) && (!facturaClienteWSEntity.getDocumento().isEmpty()))
/* 108:    */           {
/* 109:115 */             HashMap<String, String> filters = new HashMap();
/* 110:116 */             filters.put("nombre", facturaClienteWSEntity.getDocumento());
/* 111:117 */             filters.put("idOrganizacion", "" + organizacion);
/* 112:118 */             documento = (Documento)this.servicioDocumento.obtenerListaCombo("", false, filters).get(0);
/* 113:    */           }
/* 114:    */           else
/* 115:    */           {
/* 116:    */             try
/* 117:    */             {
/* 118:121 */               List<Documento> listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion((facturaClienteWSEntity.getTipoDocumento().equals("04")) || (facturaClienteWSEntity.getTipoDocumento().equals("4")) ? DocumentoBase.NOTA_CREDITO_CLIENTE : (facturaClienteWSEntity.getTipoDocumento().equals("01")) || (facturaClienteWSEntity.getTipoDocumento().equals("1")) ? DocumentoBase.FACTURA_CLIENTE : DocumentoBase.NOTA_DEBITO_CLIENTE, organizacion);
/* 119:122 */               if ((listaDocumento != null) && (!listaDocumento.isEmpty())) {
/* 120:123 */                 documento = (Documento)listaDocumento.get(0);
/* 121:    */               }
/* 122:    */             }
/* 123:    */             catch (ExcepcionAS2 e)
/* 124:    */             {
/* 125:126 */               MensajeWSEntity mensajeError = new MensajeWSEntity(MensajeErrorEnum.ERROR_501, e.getCodigoExcepcion());
/* 126:127 */               listaMensaje.add(mensajeError);
/* 127:    */             }
/* 128:    */           }
/* 129:131 */           Sucursal sucursal = this.servicioSucursal.buscarPorCodigo(organizacion, facturaClienteWSEntity.getEstablecimiento());
/* 130:    */           
/* 131:133 */           Map<String, String> filters = new HashMap();
/* 132:134 */           filters.put("idOrganizacion", "" + organizacion);
/* 133:135 */           filters.put("codigo", facturaClienteWSEntity.getPuntoDeVenta());
/* 134:136 */           filters.put("sucursal.idSucursal", "" + sucursal.getId());
/* 135:137 */           puntoDeVenta = this.servicioPuntoDeVenta.buscarPuntoDeVenta(filters);
/* 136:138 */           facturaCliente.setEstado(Estado.PROCESADO);
/* 137:    */           
/* 138:    */ 
/* 139:141 */           facturaCliente.setDocumento(documento);
/* 140:142 */           FacturaClienteSRI facturaClienteSRI = new FacturaClienteSRI();
/* 141:143 */           facturaCliente.setFacturaClienteSRI(facturaClienteSRI);
/* 142:144 */           facturaClienteSRI.setFacturaCliente(facturaCliente);
/* 143:    */           
/* 144:146 */           int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(organizacion).booleanValue() ? 2 : 1;
/* 145:147 */           facturaClienteSRI.setAmbiente(ambiente);
/* 146:    */           
/* 147:149 */           facturaClienteSRI.setDireccionMatriz(AppUtil.getDireccionMatriz());
/* 148:150 */           facturaClienteSRI.setDireccionSucursal(sucursal.getUbicacion().getDireccionCompleta());
/* 149:151 */           facturaClienteSRI.setEmail(facturaClienteWSEntity.getEmail());
/* 150:152 */           facturaClienteSRI.setEstado(Estado.PROCESADO);
/* 151:153 */           facturaClienteSRI.setCodigoFormaPagoSRI("20");
/* 152:154 */           facturaCliente.setEmail(facturaClienteWSEntity.getEmail());
/* 153:    */           
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:160 */           facturaCliente.setSucursal(sucursal);
/* 159:161 */           facturaCliente.setIdOrganizacion(organizacion);
/* 160:162 */           facturaClienteSRI.setIdSucursal(sucursal.getId());
/* 161:163 */           facturaClienteSRI.setIdOrganizacion(organizacion);
/* 162:    */           try
/* 163:    */           {
/* 164:167 */             Empresa empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(organizacion, facturaClienteWSEntity.getIdentificacionCliente());
/* 165:168 */             facturaCliente.setEmpresa(empresa);
/* 166:169 */             facturaCliente.setDireccionEmpresa((DireccionEmpresa)empresa.getDirecciones().get(0));
/* 167:    */           }
/* 168:    */           catch (ExcepcionAS2 e)
/* 169:    */           {
/* 170:171 */             MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el cliente con la identificacion dada");
/* 171:172 */             listaMensaje.add(mensaje);
/* 172:    */           }
/* 173:185 */           CondicionPago condicionPago = null;
/* 174:    */           try
/* 175:    */           {
/* 176:187 */             condicionPago = this.servicioCondicionPago.buscarCondicionPagoPorDiasPlazo(facturaClienteWSEntity.getCondicionPago().intValue(), organizacion);
/* 177:    */           }
/* 178:    */           catch (ExcepcionAS2 e1)
/* 179:    */           {
/* 180:189 */             condicionPago = new CondicionPago();
/* 181:190 */             condicionPago.setActivo(true);
/* 182:191 */             condicionPago.setIdOrganizacion(organizacion);
/* 183:192 */             condicionPago.setDiasPlazo(facturaClienteWSEntity.getCondicionPago().intValue());
/* 184:193 */             condicionPago.setNombre(facturaClienteWSEntity.getCondicionPago() + " dias");
/* 185:194 */             condicionPago.setCodigo(facturaClienteWSEntity.getCondicionPago() + "DIAS");
/* 186:195 */             this.servicioCondicionPago.guardar(condicionPago);
/* 187:    */           }
/* 188:198 */           facturaCliente.setCondicionPago(condicionPago);
/* 189:199 */           facturaCliente.setDescripcion(facturaClienteWSEntity.getDescripcion());
/* 190:    */           
/* 191:201 */           facturaCliente.setNumero(facturaClienteWSEntity.getEstablecimiento() + "-" + facturaClienteWSEntity.getPuntoDeVenta() + "-" + facturaClienteWSEntity.getNumero());
/* 192:202 */           facturaCliente.setFecha(facturaClienteWSEntity.getFecha().getTime());
/* 193:203 */           this.servicioDocumento.cargarSecuencia(facturaCliente.getDocumento(), puntoDeVenta, facturaCliente.getFecha());
/* 194:206 */           if (facturaClienteWSEntity.getFechaVencimiento() != null) {
/* 195:207 */             facturaCliente.setFechaVencimiento(facturaClienteWSEntity.getFechaVencimiento().getTime());
/* 196:    */           } else {
/* 197:209 */             facturaCliente.setFechaVencimiento(facturaClienteWSEntity.getFecha().getTime());
/* 198:    */           }
/* 199:213 */           facturaCliente.setDireccionFactura(facturaClienteWSEntity.getDireccionFactura());
/* 200:214 */           facturaCliente.setNumeroCuotas(facturaClienteWSEntity.getNumeroCuotas().intValue());
/* 201:215 */           facturaCliente.setImpuesto(facturaClienteWSEntity.getTotalImpuesto());
/* 202:216 */           facturaCliente.setTotal(facturaClienteWSEntity.getTotalImporte());
/* 203:217 */           facturaCliente.setListaDetalleFacturaCliente(new ArrayList());
/* 204:220 */           for (DetalleFacturaClienteWSEntity detalleFacturaWS : facturaClienteWSEntity.getListaDetalleFacturaCliente())
/* 205:    */           {
/* 206:221 */             DetalleFacturaCliente detalleFactura = new DetalleFacturaCliente();
/* 207:222 */             detalleFactura.setIdOrganizacion(organizacion);
/* 208:223 */             detalleFactura.setIdSucursal(sucursal.getId());
/* 209:224 */             detalleFactura.setCantidad(detalleFacturaWS.getCantidad());
/* 210:225 */             detalleFactura.setFacturaCliente(facturaCliente);
/* 211:226 */             detalleFactura.setPorcentajeDescuento(detalleFacturaWS.getPorcentajeDescuento());
/* 212:227 */             detalleFactura.setDescuento(detalleFacturaWS.getDescuento());
/* 213:228 */             detalleFactura.setPrecio(detalleFacturaWS.getPrecio());
/* 214:    */             try
/* 215:    */             {
/* 216:231 */               detalleFactura.setProducto(this.servicioProducto.buscarPorCodigo(detalleFacturaWS.getCodigoProducto(), organizacion, null));
/* 217:232 */               detalleFactura.setUnidadVenta(detalleFactura.getProducto().getUnidadVenta());
/* 218:    */             }
/* 219:    */             catch (ExcepcionAS2 e)
/* 220:    */             {
/* 221:234 */               MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el producto con el codigo dado");
/* 222:235 */               listaMensaje.add(mensaje);
/* 223:    */             }
/* 224:237 */             facturaCliente.getListaDetalleFacturaCliente().add(detalleFactura);
/* 225:    */             
/* 226:239 */             detalleFactura.setIndicadorImpuesto(detalleFactura.getProducto().isIndicadorImpuestos());
/* 227:241 */             if (detalleFactura.isIndicadorImpuesto()) {
/* 228:242 */               this.servicioFacturaCliente.obtenerImpuestosProductos(detalleFactura.getProducto(), detalleFactura);
/* 229:    */             }
/* 230:    */           }
/* 231:245 */           this.servicioFacturaCliente.totalizar(facturaCliente);
/* 232:247 */           if ((!facturaClienteWSEntity.getTipoDocumento().equals("04")) && (!facturaClienteWSEntity.getTipoDocumento().equals("4")))
/* 233:    */           {
/* 234:248 */             facturaCliente.setNumeroCuotas(facturaClienteWSEntity.getNumeroCuotas().intValue());
/* 235:249 */             this.servicioFacturaCliente.generarCuentaPorCobrar(facturaCliente);
/* 236:    */           }
/* 237:252 */           if ((facturaClienteWSEntity.getTipoDocumento().equals("04")) || (facturaClienteWSEntity.getTipoDocumento().equals("4")) || (facturaClienteWSEntity.getTipoDocumento().equals("05")) || (facturaClienteWSEntity.getTipoDocumento().equals("5")))
/* 238:    */           {
/* 239:253 */             FacturaCliente facturaClientePadre = this.servicioFacturaCliente.buscarFacturaClientePorNumero(organizacion, facturaClienteWSEntity.getEstablecimientoModificado() + "-" + facturaClienteWSEntity.getPuntoDeVentaModificado() + "-" + facturaClienteWSEntity.getNumeroModificado(), DocumentoBase.FACTURA_CLIENTE);
/* 240:254 */             if (facturaClientePadre != null)
/* 241:    */             {
/* 242:255 */               facturaCliente.setFacturaClientePadre(facturaClientePadre);
/* 243:    */             }
/* 244:    */             else
/* 245:    */             {
/* 246:257 */               MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe la factura a modificar con los datos ingresados");
/* 247:258 */               listaMensaje.add(mensaje);
/* 248:    */             }
/* 249:    */           }
/* 250:    */         }
/* 251:    */         catch (Exception e)
/* 252:    */         {
/* 253:262 */           System.out.println(e.getMessage());
/* 254:263 */           System.out.println(e.getCause());
/* 255:264 */           e.printStackTrace();
/* 256:265 */           respuesta.setEstado("ERROR");
/* 257:266 */           MensajeWSEntity mensajeError = new MensajeWSEntity(MensajeErrorEnum.ERROR_501, e.getMessage());
/* 258:267 */           listaMensaje.add(mensajeError);
/* 259:    */         }
/* 260:    */         try
/* 261:    */         {
/* 262:270 */           if (listaMensaje.size() == 0)
/* 263:    */           {
/* 264:271 */             if ((facturaClienteWSEntity.getTipoDocumento().equals("04")) || (facturaClienteWSEntity.getTipoDocumento().equals("4")))
/* 265:    */             {
/* 266:272 */               this.servicioNotaCreditoCliente.guardar(facturaCliente);
/* 267:    */             }
/* 268:    */             else
/* 269:    */             {
/* 270:274 */               this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 271:275 */               this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(facturaCliente, puntoDeVenta);
/* 272:276 */               facturaCliente = this.servicioFacturaCliente.guardar(facturaCliente);
/* 273:    */             }
/* 274:279 */             respuesta.setEstado("REGISTRADO");
/* 275:280 */             respuesta.setDetalle(facturaCliente.getFacturaClienteSRI().getAutorizacion());
/* 276:    */           }
/* 277:    */           else
/* 278:    */           {
/* 279:282 */             respuesta.setEstado("ERROR");
/* 280:    */           }
/* 281:    */         }
/* 282:    */         catch (Exception e)
/* 283:    */         {
/* 284:286 */           System.out.println(e.getMessage());
/* 285:287 */           System.out.println(e.getCause());
/* 286:288 */           e.printStackTrace();
/* 287:289 */           respuesta.setEstado("ERROR");
/* 288:290 */           MensajeWSEntity mensajeError = new MensajeWSEntity(MensajeErrorEnum.ERROR_501, e.getMessage());
/* 289:291 */           listaMensaje.add(mensajeError);
/* 290:    */         }
/* 291:293 */         respuesta.setListaMensaje((MensajeWSEntity[])listaMensaje.toArray(new MensajeWSEntity[0]));
/* 292:294 */         listaRespuesta.add(respuesta);
/* 293:    */       }
/* 294:    */     }
/* 295:296 */     return (RespuestaWSEntity[])listaRespuesta.toArray(new RespuestaWSEntity[0]);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public FacturaClienteWSEntity consultarFacturaCliente(int organizacion, String tipoDocumento, String establecimiento, String puntoDeVenta, String numero)
/* 299:    */     throws AS2Exception
/* 300:    */   {
/* 301:301 */     FacturaClienteWSEntity facturaClienteWSEntity = null;
/* 302:    */     
/* 303:303 */     FacturaCliente facturaCliente = null;
/* 304:    */     try
/* 305:    */     {
/* 306:305 */       DocumentoBase documentoBase = tipoDocumento.equals("05") ? DocumentoBase.NOTA_DEBITO_CLIENTE : tipoDocumento.equals("04") ? DocumentoBase.NOTA_CREDITO_CLIENTE : tipoDocumento.equals("01") ? DocumentoBase.FACTURA_CLIENTE : null;
/* 307:306 */       facturaCliente = this.servicioFacturaCliente.buscarFacturaClientePorNumero(organizacion, establecimiento + "-" + puntoDeVenta + "-" + numero, documentoBase);
/* 308:307 */       if (facturaCliente != null) {
/* 309:308 */         return new FacturaClienteWSEntity(facturaCliente);
/* 310:    */       }
/* 311:311 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { establecimiento + "-" + puntoDeVenta + "-" + numero });
/* 312:    */     }
/* 313:    */     catch (Exception e)
/* 314:    */     {
/* 315:314 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { establecimiento + "-" + puntoDeVenta + "-" + numero });
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   private List<MensajeWSEntity> comprobarDatos(int organizacion, FacturaClienteWSEntity facturaClienteWSEntity)
/* 320:    */   {
/* 321:320 */     List<MensajeWSEntity> listaMensaje = new ArrayList();
/* 322:321 */     if ((facturaClienteWSEntity.getDocumento() != null) && (!facturaClienteWSEntity.getDocumento().isEmpty()))
/* 323:    */     {
/* 324:322 */       HashMap<String, String> filters = new HashMap();
/* 325:323 */       filters.put("nombre", facturaClienteWSEntity.getDocumento());
/* 326:324 */       filters.put("idOrganizacion", "" + organizacion);
/* 327:325 */       if (this.servicioDocumento.obtenerListaCombo("", false, filters).size() == 0)
/* 328:    */       {
/* 329:326 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el documento con el nombre dado");
/* 330:327 */         listaMensaje.add(mensaje);
/* 331:    */       }
/* 332:    */     }
/* 333:    */     try
/* 334:    */     {
/* 335:331 */       this.servicioEmpresa.buscarEmpresaPorIdentificacion(organizacion, facturaClienteWSEntity.getIdentificacionCliente());
/* 336:    */     }
/* 337:    */     catch (ExcepcionAS2 e)
/* 338:    */     {
/* 339:333 */       mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el cliente con la identificacion dada");
/* 340:334 */       listaMensaje.add(mensaje);
/* 341:    */     }
/* 342:337 */     if ((!facturaClienteWSEntity.getTipoDocumento().equals("04")) && (!facturaClienteWSEntity.getTipoDocumento().equals("4")) && (!facturaClienteWSEntity.getTipoDocumento().equals("05")) && (!facturaClienteWSEntity.getTipoDocumento().equals("5")) && (!facturaClienteWSEntity.getTipoDocumento().equals("01")) && (!facturaClienteWSEntity.getTipoDocumento().equals("1")))
/* 343:    */     {
/* 344:338 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Valor del campo tipoDocumento no válido");
/* 345:339 */       listaMensaje.add(mensaje);
/* 346:    */     }
/* 347:342 */     if ((facturaClienteWSEntity.getNumero() == null) || (facturaClienteWSEntity.getNumero().length() != 9))
/* 348:    */     {
/* 349:343 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo numero");
/* 350:344 */       listaMensaje.add(mensaje);
/* 351:    */     }
/* 352:352 */     if ((facturaClienteWSEntity.getPuntoDeVenta() == null) || (facturaClienteWSEntity.getPuntoDeVenta().length() != 3))
/* 353:    */     {
/* 354:353 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo puntoVenta");
/* 355:354 */       listaMensaje.add(mensaje);
/* 356:    */     }
/* 357:362 */     if ((facturaClienteWSEntity.getEstablecimiento() == null) || (facturaClienteWSEntity.getEstablecimiento().length() != 3))
/* 358:    */     {
/* 359:363 */       mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_301, "Longitud inválida para el campo establecimiento");
/* 360:364 */       listaMensaje.add(mensaje);
/* 361:    */     }
/* 362:372 */     MensajeWSEntity mensaje = facturaClienteWSEntity.getListaDetalleFacturaCliente();MensajeWSEntity mensaje = mensaje.length;
/* 363:372 */     for (MensajeWSEntity localMensajeWSEntity1 = 0; localMensajeWSEntity1 < mensaje; localMensajeWSEntity1++)
/* 364:    */     {
/* 365:372 */       DetalleFacturaClienteWSEntity detalleFacturaWS = mensaje[localMensajeWSEntity1];
/* 366:    */       try
/* 367:    */       {
/* 368:374 */         this.servicioProducto.buscarPorCodigo(detalleFacturaWS.getCodigoProducto(), organizacion, null);
/* 369:    */       }
/* 370:    */       catch (ExcepcionAS2 e)
/* 371:    */       {
/* 372:376 */         MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_201, "No existe el producto con el codigo dado");
/* 373:377 */         listaMensaje.add(mensaje);
/* 374:    */       }
/* 375:    */     }
/* 376:380 */     DocumentoBase documentoBase = facturaClienteWSEntity.getTipoDocumento().equals("05") ? DocumentoBase.NOTA_DEBITO_CLIENTE : facturaClienteWSEntity.getTipoDocumento().equals("04") ? DocumentoBase.NOTA_CREDITO_CLIENTE : facturaClienteWSEntity.getTipoDocumento().equals("01") ? DocumentoBase.FACTURA_CLIENTE : null;
/* 377:381 */     if (this.servicioFacturaCliente.buscarFacturaClientePorNumero(organizacion, facturaClienteWSEntity.getEstablecimiento() + "-" + facturaClienteWSEntity.getPuntoDeVenta() + "-" + facturaClienteWSEntity.getNumero(), documentoBase) != null)
/* 378:    */     {
/* 379:382 */       MensajeWSEntity mensaje = new MensajeWSEntity(MensajeErrorEnum.ERROR_101, "Ya existe el comprobante con ese número");
/* 380:383 */       listaMensaje.add(mensaje);
/* 381:    */     }
/* 382:387 */     return listaMensaje;
/* 383:    */   }
/* 384:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.procesos.impl.ServicioWebFacturaClienteImpl
 * JD-Core Version:    0.7.0.1
 */