/*   1:    */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.calidad.procesos.servicio.ServicioControlCalidad;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   8:    */ import com.asinfo.as2.dao.DetalleRecepcionProveedorDao;
/*   9:    */ import com.asinfo.as2.dao.GenericoDao;
/*  10:    */ import com.asinfo.as2.dao.PedidoProveedorDao;
/*  11:    */ import com.asinfo.as2.dao.RecepcionProveedorDao;
/*  12:    */ import com.asinfo.as2.dao.RegistroPesoDao;
/*  13:    */ import com.asinfo.as2.dao.produccion.OrdenSalidaMaterialDao;
/*  14:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  15:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  16:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  17:    */ import com.asinfo.as2.entities.Bodega;
/*  18:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*  19:    */ import com.asinfo.as2.entities.Chofer;
/*  20:    */ import com.asinfo.as2.entities.Cliente;
/*  21:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  22:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  23:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  24:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  25:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  26:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*  27:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*  28:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*  29:    */ import com.asinfo.as2.entities.DetalleRegistroPeso;
/*  30:    */ import com.asinfo.as2.entities.DetalleRegistroPesoLote;
/*  31:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  32:    */ import com.asinfo.as2.entities.Documento;
/*  33:    */ import com.asinfo.as2.entities.Empresa;
/*  34:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  35:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  36:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  37:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  38:    */ import com.asinfo.as2.entities.Lote;
/*  39:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  40:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  41:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*  42:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  43:    */ import com.asinfo.as2.entities.Producto;
/*  44:    */ import com.asinfo.as2.entities.RegistroPeso;
/*  45:    */ import com.asinfo.as2.entities.Sucursal;
/*  46:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  47:    */ import com.asinfo.as2.entities.Transportista;
/*  48:    */ import com.asinfo.as2.entities.Ubicacion;
/*  49:    */ import com.asinfo.as2.entities.Unidad;
/*  50:    */ import com.asinfo.as2.entities.Vehiculo;
/*  51:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  52:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  53:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  54:    */ import com.asinfo.as2.enumeraciones.EstadoRegistroPeso;
/*  55:    */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*  56:    */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*  57:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  58:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  59:    */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*  60:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  61:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  62:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  63:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  64:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  65:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  66:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  67:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*  68:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  69:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  70:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  71:    */ import com.asinfo.as2.util.AppUtil;
/*  72:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  73:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  74:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  75:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  76:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  77:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  78:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  79:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  80:    */ import java.math.BigDecimal;
/*  81:    */ import java.util.ArrayList;
/*  82:    */ import java.util.Date;
/*  83:    */ import java.util.HashMap;
/*  84:    */ import java.util.Iterator;
/*  85:    */ import java.util.List;
/*  86:    */ import java.util.Map;
/*  87:    */ import javax.ejb.EJB;
/*  88:    */ import javax.ejb.SessionContext;
/*  89:    */ import javax.ejb.Stateless;
/*  90:    */ import javax.ejb.TransactionAttribute;
/*  91:    */ import javax.ejb.TransactionAttributeType;
/*  92:    */ import javax.ejb.TransactionManagement;
/*  93:    */ import javax.ejb.TransactionManagementType;
/*  94:    */ import org.apache.log4j.Logger;
/*  95:    */ 
/*  96:    */ @Stateless
/*  97:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  98:    */ public class ServicioRegistroPesoImpl
/*  99:    */   extends AbstractServicioAS2
/* 100:    */   implements ServicioRegistroPeso
/* 101:    */ {
/* 102:    */   private static final long serialVersionUID = -8181844210742442787L;
/* 103:    */   @EJB
/* 104:    */   private transient RegistroPesoDao registroPesoDao;
/* 105:    */   @EJB
/* 106:    */   private transient ServicioSecuencia servicioSecuencia;
/* 107:    */   @EJB
/* 108:    */   private transient ServicioSucursal servicioSucursal;
/* 109:    */   @EJB
/* 110:    */   private transient ServicioDocumento servicioDocumento;
/* 111:    */   @EJB
/* 112:    */   private transient ServicioRecepcionProveedor servicioRecepcionProveedor;
/* 113:    */   @EJB
/* 114:    */   private transient RecepcionProveedorDao recepcionProveedorDao;
/* 115:    */   @EJB
/* 116:    */   private transient DetalleRecepcionProveedorDao detalleRecepcionProveedorDao;
/* 117:    */   @EJB
/* 118:    */   private transient PedidoProveedorDao pedidoProveedorDao;
/* 119:    */   @EJB
/* 120:    */   private transient GenericoDao<DetalleRegistroPesoLote> detalleRegistroPesoLoteDao;
/* 121:    */   @EJB
/* 122:    */   private transient ServicioDespachoCliente servicioDespachoCliente;
/* 123:    */   @EJB
/* 124:    */   private transient ServicioEmpresa servicioEmpresa;
/* 125:    */   @EJB
/* 126:    */   private transient ServicioOrganizacion servicioOrganizacion;
/* 127:    */   @EJB
/* 128:    */   private transient ServicioGuiaRemision servicioGuiaRemision;
/* 129:    */   @EJB
/* 130:    */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/* 131:    */   @EJB
/* 132:    */   private transient ServicioMovimientoInventario servicioMovimientoInventario;
/* 133:    */   @EJB
/* 134:    */   private transient ServicioGenerico<DetalleRegistroPeso> detalleRegistroPesoDao;
/* 135:    */   @EJB
/* 136:    */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/* 137:    */   @EJB
/* 138:    */   private transient ServicioControlCalidad servicioControlCalidad;
/* 139:    */   @EJB
/* 140:    */   private transient ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/* 141:    */   @EJB
/* 142:    */   private transient ServicioProducto servicioProducto;
/* 143:    */   @EJB
/* 144:    */   private transient ServicioUnidadConversion servicioUnidadConversion;
/* 145:    */   @EJB
/* 146:    */   private transient OrdenSalidaMaterialDao ordenSalidaMaterialDao;
/* 147:    */   @EJB
/* 148:    */   private transient ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/* 149:    */   @EJB
/* 150:    */   private transient ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/* 151:    */   @EJB
/* 152:    */   private transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/* 153:    */   @EJB
/* 154:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/* 155:    */   
/* 156:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 157:    */   public void guardar(RegistroPeso registroPeso)
/* 158:    */     throws ExcepcionAS2Identification, AS2Exception, ExcepcionAS2, ExcepcionAS2Financiero, ExcepcionAS2Inventario
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:166 */       validar(registroPeso);
/* 163:167 */       cargarSecuencia(registroPeso);
/* 164:168 */       actualizarEstado(registroPeso);
/* 165:    */       
/* 166:170 */       actualizarDatosControlCalidad(registroPeso);
/* 167:172 */       if (registroPeso.getVehiculo() != null) {
/* 168:173 */         registroPeso.setPlacaVehiculo(registroPeso.getVehiculo().getPlaca());
/* 169:    */       }
/* 170:175 */       if (registroPeso.getChofer() != null) {
/* 171:176 */         registroPeso.setNombreChofer(registroPeso.getChofer().getNombre());
/* 172:    */       }
/* 173:179 */       this.registroPesoDao.guardar(registroPeso);
/* 174:180 */       for (DetalleRegistroPesoLote detalle : registroPeso.getListaDetalleRegistroPesoLote())
/* 175:    */       {
/* 176:181 */         this.servicioVerificadorInventario.actualizarInventarioComprometidoDespacho(detalle);
/* 177:182 */         this.detalleRegistroPesoLoteDao.guardar(detalle);
/* 178:    */       }
/* 179:185 */       for (DetalleRegistroPeso rpdpc : registroPeso.getListaDetalleRegistroPeso()) {
/* 180:186 */         this.detalleRegistroPesoDao.guardar(rpdpc);
/* 181:    */       }
/* 182:189 */       if (registroPeso.getEstado().equals(EstadoRegistroPeso.SEGUNDO_PESO)) {
/* 183:190 */         if (registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) {
/* 184:191 */           generarRecepcionTransferenciaBodega(registroPeso);
/* 185:192 */         } else if (registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE)) {
/* 186:193 */           generarDespachoCliente(registroPeso);
/* 187:194 */         } else if ((registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) && 
/* 188:195 */           (!registroPeso.getProducto().getIndicadorControlCalidad().booleanValue())) {
/* 189:196 */           this.servicioControlCalidad.generarRecepcionProveedor(registroPeso, registroPeso.getBodega());
/* 190:197 */         } else if (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(registroPeso.getTipoRegistroPeso())) {
/* 191:198 */           generarTransferenciaBodega(registroPeso);
/* 192:199 */         } else if (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(registroPeso.getTipoRegistroPeso())) {
/* 193:200 */           generarDevolucionCliente(registroPeso);
/* 194:    */         }
/* 195:    */       }
/* 196:    */     }
/* 197:    */     catch (ExcepcionAS2Financiero e)
/* 198:    */     {
/* 199:204 */       this.context.setRollbackOnly();
/* 200:205 */       e.printStackTrace();
/* 201:206 */       throw e;
/* 202:    */     }
/* 203:    */     catch (ExcepcionAS2Inventario e)
/* 204:    */     {
/* 205:208 */       this.context.setRollbackOnly();
/* 206:209 */       e.printStackTrace();
/* 207:210 */       throw e;
/* 208:    */     }
/* 209:    */     catch (ExcepcionAS2Identification e)
/* 210:    */     {
/* 211:212 */       this.context.setRollbackOnly();
/* 212:213 */       e.printStackTrace();
/* 213:214 */       throw e;
/* 214:    */     }
/* 215:    */     catch (ExcepcionAS2 e)
/* 216:    */     {
/* 217:216 */       this.context.setRollbackOnly();
/* 218:217 */       e.printStackTrace();
/* 219:218 */       throw e;
/* 220:    */     }
/* 221:    */     catch (AS2Exception e)
/* 222:    */     {
/* 223:220 */       this.context.setRollbackOnly();
/* 224:221 */       e.printStackTrace();
/* 225:222 */       throw e;
/* 226:    */     }
/* 227:    */     catch (Exception e)
/* 228:    */     {
/* 229:224 */       this.context.setRollbackOnly();
/* 230:225 */       LOG.error(e);
/* 231:226 */       e.printStackTrace();
/* 232:227 */       throw new ExcepcionAS2(e);
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   private void generarDevolucionCliente(RegistroPeso registroPeso)
/* 237:    */     throws AS2Exception, ExcepcionAS2
/* 238:    */   {
/* 239:    */     try
/* 240:    */     {
/* 241:234 */       FacturaCliente facturaCliente = null;
/* 242:235 */       Iterator localIterator1 = registroPeso.getListaDetalleRegistroPeso().iterator();
/* 243:235 */       if (localIterator1.hasNext())
/* 244:    */       {
/* 245:235 */         DetalleRegistroPeso drp = (DetalleRegistroPeso)localIterator1.next();
/* 246:236 */         facturaCliente = drp.getDetalleFacturaCliente().getFacturaCliente();
/* 247:    */       }
/* 248:239 */       facturaCliente = this.servicioFacturaCliente.cargarDetalle(facturaCliente.getId(), false);
/* 249:240 */       FacturaCliente devolucion = new FacturaCliente();
/* 250:241 */       devolucion.setIdOrganizacion(registroPeso.getIdOrganizacion());
/* 251:242 */       devolucion.setSucursal(facturaCliente.getSucursal());
/* 252:243 */       devolucion.setNumero("");
/* 253:244 */       devolucion.setFecha(new Date());
/* 254:245 */       devolucion.setEstado(Estado.ELABORADO);
/* 255:246 */       devolucion.setNumeroCuotas(1);
/* 256:247 */       Map<String, String> filters = new HashMap();
/* 257:248 */       filters.put("idOrganizacion", String.valueOf(registroPeso.getIdOrganizacion()));
/* 258:249 */       List<MotivoNotaCreditoCliente> listaMotivos = this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("predeterminado", false, null);
/* 259:250 */       if ((listaMotivos != null) && (!listaMotivos.isEmpty())) {
/* 260:251 */         devolucion.setMotivoNotaCreditoCliente((MotivoNotaCreditoCliente)listaMotivos.get(0));
/* 261:    */       }
/* 262:253 */       devolucion.setEmpresa(facturaCliente.getEmpresa());
/* 263:254 */       devolucion.getEmpresa().setTipoIdentificacion(this.servicioTipoIdentificacion
/* 264:255 */         .buscarPorId(Integer.valueOf(facturaCliente.getEmpresa().getTipoIdentificacion().getIdTipoIdentificacion())));
/* 265:256 */       devolucion.setSubempresa(facturaCliente.getSubempresa());
/* 266:    */       
/* 267:258 */       List<DireccionEmpresa> listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(devolucion.getEmpresaFinal().getId());
/* 268:259 */       for (DireccionEmpresa direccion : listaDireccionEmpresa) {
/* 269:260 */         if (direccion.isIndicadorDireccionPrincipal()) {
/* 270:261 */           devolucion.setDireccionEmpresa(direccion);
/* 271:    */         } else {
/* 272:263 */           devolucion.setDireccionEmpresa((DireccionEmpresa)listaDireccionEmpresa.get(0));
/* 273:    */         }
/* 274:    */       }
/* 275:267 */       devolucion.setEmail(this.servicioEmpresa.cargarMails(devolucion.getEmpresaFinal(), DocumentoBase.DEVOLUCION_CLIENTE));
/* 276:    */       
/* 277:269 */       Object listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DEVOLUCION_CLIENTE, devolucion
/* 278:270 */         .getIdOrganizacion());
/* 279:271 */       devolucion.setDocumento((Documento)((List)listaDocumento).get(0));
/* 280:272 */       if (devolucion.getDocumento().isIndicadorDocumentoTributario()) {
/* 281:273 */         this.servicioNotaCreditoCliente.cargarSecuencia(devolucion, registroPeso.getPuntoDeVenta());
/* 282:    */       } else {
/* 283:275 */         this.servicioNotaCreditoCliente.cargarSecuencia(devolucion, null);
/* 284:    */       }
/* 285:277 */       this.servicioSecuencia.detach(devolucion.getDocumento().getSecuencia());
/* 286:278 */       this.servicioSecuencia.actualizarSecuencia(devolucion.getDocumento().getSecuencia(), devolucion.getNumero());
/* 287:279 */       devolucion.setFacturaClientePadre(facturaCliente);
/* 288:280 */       actualizarDetalleDevolucion(devolucion, registroPeso);
/* 289:281 */       this.servicioNotaCreditoCliente.totalizar(devolucion);
/* 290:    */       
/* 291:283 */       this.servicioNotaCreditoCliente.guardar(devolucion);
/* 292:284 */       registroPeso.setDevolucionCliente(devolucion);
/* 293:285 */       this.registroPesoDao.guardar(registroPeso);
/* 294:    */     }
/* 295:    */     catch (ExcepcionAS2Financiero e)
/* 296:    */     {
/* 297:287 */       e.printStackTrace();
/* 298:288 */       this.context.setRollbackOnly();
/* 299:289 */       throw e;
/* 300:    */     }
/* 301:    */     catch (ExcepcionAS2Ventas e)
/* 302:    */     {
/* 303:291 */       e.printStackTrace();
/* 304:292 */       this.context.setRollbackOnly();
/* 305:293 */       throw e;
/* 306:    */     }
/* 307:    */     catch (AS2Exception e)
/* 308:    */     {
/* 309:295 */       e.printStackTrace();
/* 310:296 */       this.context.setRollbackOnly();
/* 311:297 */       throw e;
/* 312:    */     }
/* 313:    */     catch (ExcepcionAS2 e)
/* 314:    */     {
/* 315:299 */       e.printStackTrace();
/* 316:300 */       this.context.setRollbackOnly();
/* 317:301 */       throw e;
/* 318:    */     }
/* 319:    */     catch (Exception e)
/* 320:    */     {
/* 321:303 */       e.printStackTrace();
/* 322:304 */       this.context.setRollbackOnly();
/* 323:305 */       throw new ExcepcionAS2(e);
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   private void actualizarDetalleDevolucion(FacturaCliente devolucion, RegistroPeso registroPeso)
/* 328:    */     throws AS2Exception, ExcepcionAS2Inventario
/* 329:    */   {
/* 330:312 */     devolucion.setListaDetalleFacturaCliente(new ArrayList());
/* 331:    */     
/* 332:    */ 
/* 333:315 */     devolucion.setDireccionEmpresa(devolucion.getFacturaClientePadre().getDireccionEmpresa());
/* 334:316 */     for (DetalleRegistroPeso rpdpc : registroPeso.getListaDetalleRegistroPeso()) {
/* 335:317 */       if (!rpdpc.isEliminado())
/* 336:    */       {
/* 337:318 */         DetalleFacturaCliente detalleFC = rpdpc.getDetalleFacturaCliente();
/* 338:319 */         if (detalleFC.getProducto().isIndicadorLote())
/* 339:    */         {
/* 340:320 */           boolean existeLote = false;
/* 341:321 */           for (DetalleRegistroPesoLote detalleRegistro : registroPeso.getListaDetalleRegistroPesoLote()) {
/* 342:322 */             if ((detalleRegistro.getProducto().getId() == detalleFC.getProducto().getId()) && (!detalleRegistro.isEliminado()) && 
/* 343:323 */               (detalleRegistro.getCantidad().compareTo(BigDecimal.ZERO) > 0))
/* 344:    */             {
/* 345:324 */               existeLote = true;
/* 346:325 */               crearDetalleDevolucion(devolucion, detalleFC, registroPeso, detalleRegistro, detalleRegistro.getCantidad());
/* 347:    */             }
/* 348:    */           }
/* 349:329 */           if (!existeLote) {
/* 350:330 */             throw new AS2Exception("msg_error_lote_requerido", new String[] { "" });
/* 351:    */           }
/* 352:    */         }
/* 353:333 */         else if (rpdpc.getCantidad().compareTo(BigDecimal.ZERO) > 0)
/* 354:    */         {
/* 355:334 */           crearDetalleDevolucion(devolucion, detalleFC, registroPeso, null, rpdpc.getCantidad());
/* 356:    */         }
/* 357:    */       }
/* 358:    */     }
/* 359:    */   }
/* 360:    */   
/* 361:    */   private void crearDetalleDevolucion(FacturaCliente devolucion, DetalleFacturaCliente detalleFC, RegistroPeso registroPeso, DetalleRegistroPesoLote detalleRegistroLote, BigDecimal cantidad)
/* 362:    */     throws ExcepcionAS2Inventario
/* 363:    */   {
/* 364:343 */     Producto producto = this.servicioProducto.cargaDetalle(detalleFC.getProducto().getIdProducto());
/* 365:344 */     DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 366:345 */     dfc.setFacturaCliente(devolucion);
/* 367:346 */     dfc.setProducto(producto);
/* 368:347 */     dfc.setIce(producto.getIce());
/* 369:348 */     dfc.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 370:349 */     dfc.setCodigoIce(producto.getCodigoIce());
/* 371:350 */     dfc.setCantidad(cantidad);
/* 372:351 */     dfc.setPrecio(detalleFC.getPrecio());
/* 373:352 */     dfc.setUnidadVenta(detalleFC.getUnidadVenta());
/* 374:353 */     dfc.setBodega(registroPeso.getBodega());
/* 375:354 */     dfc.setDescuento(detalleFC.getDescuento());
/* 376:355 */     dfc.setPorcentajeDescuento(detalleFC.getPorcentajeDescuento());
/* 377:    */     
/* 378:357 */     dfc.setDetalleFacturaClientePadre(detalleFC);
/* 379:    */     
/* 380:359 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* 381:360 */     inventarioProducto.setDetalleDevolucionCliente(dfc);
/* 382:361 */     dfc.setInventarioProducto(inventarioProducto);
/* 383:363 */     if (detalleRegistroLote != null) {
/* 384:364 */       dfc.getInventarioProducto().setLote(detalleRegistroLote.getLote());
/* 385:    */     }
/* 386:367 */     if (devolucion.getEmpresa().getCliente().isExcentoImpuestos()) {
/* 387:368 */       dfc.setIndicadorImpuesto(false);
/* 388:    */     } else {
/* 389:370 */       dfc.setIndicadorImpuesto(dfc.getProducto().isIndicadorImpuestos());
/* 390:    */     }
/* 391:372 */     if (dfc.isIndicadorImpuesto())
/* 392:    */     {
/* 393:374 */       dfc.getProducto().setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(dfc.getProducto().getCategoriaImpuesto().getIdCategoriaImpuesto()));
/* 394:375 */       this.servicioFacturaCliente.obtenerImpuestosProductos(dfc.getProducto(), dfc);
/* 395:    */     }
/* 396:377 */     devolucion.getListaDetalleFacturaCliente().add(dfc);
/* 397:    */   }
/* 398:    */   
/* 399:    */   private void generarRecepcionTransferenciaBodega(RegistroPeso registroPeso)
/* 400:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 401:    */   {
/* 402:381 */     if (registroPeso.getDetalleTransferenciaBodega() != null)
/* 403:    */     {
/* 404:382 */       DetalleMovimientoInventario dmi = registroPeso.getDetalleTransferenciaBodega();
/* 405:383 */       dmi.setCantidadPesada(dmi.getCantidad());
/* 406:384 */       if (Estado.ELABORADO.equals(dmi.getMovimientoInventario().getEstado())) {
/* 407:385 */         this.servicioMovimientoInventario.guardaTransferenciaBodegaIngreso(dmi.getMovimientoInventario(), false);
/* 408:    */       }
/* 409:386 */       registroPeso.setTransferenciaBodega(dmi.getMovimientoInventario());
/* 410:387 */       this.registroPesoDao.guardar(registroPeso);
/* 411:    */     }
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void totalizarPesoNeto(RegistroPeso registroPeso)
/* 415:    */   {
/* 416:393 */     BigDecimal pesoNeto = BigDecimal.ZERO;
/* 417:395 */     if ((registroPeso.getTipoRegistroPeso() != null) && (registroPeso.getPesoEntrada() != null) && (registroPeso.getPesoSalida() != null) && (registroPeso.getPesoDestareTotal() != null)) {
/* 418:396 */       if ((registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) || (registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) || (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(registroPeso.getTipoRegistroPeso())))
/* 419:    */       {
/* 420:397 */         pesoNeto = registroPeso.getPesoEntrada().subtract(registroPeso.getPesoSalida()).subtract(registroPeso.getPesoDestareTotal());
/* 421:    */       }
/* 422:398 */       else if (registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.OTROS))
/* 423:    */       {
/* 424:399 */         pesoNeto = registroPeso.getPesoEntrada().subtract(registroPeso.getPesoSalida()).subtract(registroPeso.getPesoDestareTotal()).abs();
/* 425:    */       }
/* 426:    */       else
/* 427:    */       {
/* 428:402 */         pesoNeto = registroPeso.getPesoSalida().subtract(registroPeso.getPesoEntrada()).subtract(registroPeso.getPesoDestareTotal());
/* 429:404 */         if ((pesoNeto.compareTo(BigDecimal.ZERO) < 0) && (registroPeso.getTipoRegistroPeso() != null) && ((TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(registroPeso.getTipoRegistroPeso())) || (registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE))) && (registroPeso.getFechaSalida() == null)) {
/* 430:405 */           pesoNeto = BigDecimal.ZERO;
/* 431:    */         }
/* 432:    */       }
/* 433:    */     }
/* 434:409 */     registroPeso.setPesoNeto(pesoNeto);
/* 435:    */   }
/* 436:    */   
/* 437:    */   public void validar(RegistroPeso registroPeso)
/* 438:    */     throws AS2Exception
/* 439:    */   {
/* 440:415 */     boolean segundoPeso = (registroPeso.getEstado().equals(EstadoRegistroPeso.PRIMER_PESO)) && (registroPeso.getFechaSalida() != null);
/* 441:416 */     if ((registroPeso.getPesoNeto() != null) && ((registroPeso.getPesoNeto().compareTo(BigDecimal.ZERO) < 0) || ((segundoPeso) && (registroPeso.getPesoNeto().compareTo(BigDecimal.ZERO) <= 0)))) {
/* 442:417 */       throw new AS2Exception("msg_error_campo_negativo", new String[] { "Peso neto: " + registroPeso.getPesoNeto() });
/* 443:    */     }
/* 444:    */     String mensaje;
/* 445:422 */     if ((registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) && (segundoPeso) && (registroPeso.getPesoNeto().compareTo(registroPeso.getPesoReferencia()) != 0))
/* 446:    */     {
/* 447:424 */       BigDecimal diferencia = registroPeso.getPesoNeto().subtract(registroPeso.getPesoReferencia());
/* 448:425 */       registroPeso.setPesoDestareTotal(diferencia);
/* 449:426 */       totalizarPesoNeto(registroPeso);
/* 450:427 */       BigDecimal pesoTransferencia = registroPeso.getDetalleTransferenciaBodega().getCantidad();
/* 451:428 */       if ((registroPeso.getDetalleTransferenciaBodega().getProducto().getPeso() != null) && (registroPeso.getDetalleTransferenciaBodega().getProducto().getPeso().compareTo(BigDecimal.ZERO) != 0)) {
/* 452:429 */         pesoTransferencia = pesoTransferencia.multiply(registroPeso.getDetalleTransferenciaBodega().getProducto().getPeso());
/* 453:    */       }
/* 454:431 */       mensaje = registroPeso.getPesoNeto() + " != " + pesoTransferencia;
/* 455:432 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_ERROR_DIFERENCIA_PESO_NETO_TRANSFERENCIA", new String[] { mensaje });
/* 456:    */     }
/* 457:437 */     if ((segundoPeso) && ((registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE)) || (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(registroPeso.getTipoRegistroPeso())) || (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(registroPeso.getTipoRegistroPeso()))))
/* 458:    */     {
/* 459:439 */       BigDecimal[] rango = calcularRangosPermitidos(registroPeso);
/* 460:440 */       BigDecimal pesoDespacho = BigDecimal.ZERO;
/* 461:441 */       for (DetalleRegistroPesoLote drpl : registroPeso.getListaDetalleRegistroPesoLote()) {
/* 462:442 */         if ((drpl.getProducto().getPeso() != null) && (drpl.getProducto().getPeso().compareTo(BigDecimal.ZERO) != 0)) {
/* 463:443 */           pesoDespacho = pesoDespacho.add(drpl.getCantidad().multiply(drpl.getProducto().getPeso()));
/* 464:    */         }
/* 465:    */       }
/* 466:447 */       pesoDespacho = FuncionesUtiles.redondearBigDecimal(pesoDespacho);
/* 467:448 */       if ((registroPeso.getPesoNeto().compareTo(pesoDespacho) != 0) && (rango == null))
/* 468:    */       {
/* 469:449 */         String mensaje = registroPeso.getPesoNeto() + " != " + pesoDespacho;
/* 470:450 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_ERROR_DIFERENCIA_PESO_CALCULADO_PESO_NETO", new String[] { "" + registroPeso.getPesoNeto(), mensaje });
/* 471:    */       }
/* 472:451 */       if (rango != null)
/* 473:    */       {
/* 474:452 */         BigDecimal cantidadCompareMin = rango[0];
/* 475:453 */         BigDecimal cantidadCompareMax = rango[1];
/* 476:454 */         if ((registroPeso.getPesoNeto().compareTo(cantidadCompareMin) < 0) || 
/* 477:455 */           (registroPeso.getPesoNeto().compareTo(cantidadCompareMax) > 0))
/* 478:    */         {
/* 479:456 */           String mensaje = registroPeso.getPesoNeto() + " debe estar entre " + cantidadCompareMin + " y " + cantidadCompareMax;
/* 480:457 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_ERROR_DIFERENCIA_PESO_CALCULADO_PESO_NETO", new String[] { "" + registroPeso.getPesoNeto(), mensaje });
/* 481:    */         }
/* 482:    */       }
/* 483:    */     }
/* 484:    */   }
/* 485:    */   
/* 486:    */   public BigDecimal[] calcularRangosPermitidos(RegistroPeso registroPeso)
/* 487:    */   {
/* 488:466 */     boolean tieneProductosSinLote = false;
/* 489:467 */     BigDecimal[] rango = null;
/* 490:468 */     BigDecimal cantidadCompareMax = BigDecimal.ZERO;
/* 491:469 */     BigDecimal cantidadCompareMin = BigDecimal.ZERO;
/* 492:471 */     for (DetalleRegistroPeso rpd : registroPeso.getListaDetalleRegistroPeso())
/* 493:    */     {
/* 494:472 */       Producto producto = null;
/* 495:473 */       BigDecimal cantidad = null;
/* 496:474 */       if (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(registroPeso.getTipoRegistroPeso()))
/* 497:    */       {
/* 498:475 */         if (!rpd.getDetalleOrdenSalidaMaterial().getProducto().isTraIndicadorServicio())
/* 499:    */         {
/* 500:476 */           producto = rpd.getDetalleOrdenSalidaMaterial().getProducto();
/* 501:477 */           cantidad = rpd.getDetalleOrdenSalidaMaterial().getCantidad();
/* 502:    */         }
/* 503:    */       }
/* 504:479 */       else if (TipoRegistroPeso.DESPACHO_CLIENTE.equals(registroPeso.getTipoRegistroPeso()))
/* 505:    */       {
/* 506:480 */         if (!rpd.getDetallePedidoCliente().getProducto().isTraIndicadorServicio())
/* 507:    */         {
/* 508:481 */           producto = rpd.getDetallePedidoCliente().getProducto();
/* 509:482 */           cantidad = rpd.getDetallePedidoCliente().getCantidad();
/* 510:    */         }
/* 511:    */       }
/* 512:485 */       else if (!rpd.getDetalleFacturaCliente().getProducto().isTraIndicadorServicio())
/* 513:    */       {
/* 514:486 */         producto = rpd.getDetallePedidoCliente().getProducto();
/* 515:487 */         cantidad = rpd.getDetallePedidoCliente().getCantidad();
/* 516:    */       }
/* 517:490 */       if ((producto != null) && (!producto.isIndicadorLote()) && (cantidad != null))
/* 518:    */       {
/* 519:491 */         tieneProductosSinLote = true;
/* 520:    */         
/* 521:    */ 
/* 522:494 */         cantidadCompareMax = cantidadCompareMax.add(cantidad.multiply(producto.getPesoMaximo()));
/* 523:495 */         cantidadCompareMin = cantidadCompareMin.add(cantidad.multiply(producto.getPesoMinimo()));
/* 524:    */       }
/* 525:    */     }
/* 526:499 */     if (tieneProductosSinLote)
/* 527:    */     {
/* 528:500 */       rango = new BigDecimal[2];
/* 529:501 */       rango[0] = FuncionesUtiles.redondearBigDecimal(cantidadCompareMin);
/* 530:502 */       rango[1] = FuncionesUtiles.redondearBigDecimal(cantidadCompareMax);
/* 531:    */     }
/* 532:505 */     return rango;
/* 533:    */   }
/* 534:    */   
/* 535:    */   private void actualizarEstado(RegistroPeso registroPeso)
/* 536:    */   {
/* 537:509 */     if (registroPeso.getFechaEntrada() == null) {
/* 538:510 */       registroPeso.setEstado(EstadoRegistroPeso.EN_ESPERA);
/* 539:511 */     } else if ((registroPeso.getFechaEntrada() != null) && (registroPeso.getFechaSalida() == null)) {
/* 540:512 */       registroPeso.setEstado(EstadoRegistroPeso.PRIMER_PESO);
/* 541:    */     } else {
/* 542:514 */       registroPeso.setEstado(EstadoRegistroPeso.SEGUNDO_PESO);
/* 543:    */     }
/* 544:    */   }
/* 545:    */   
/* 546:    */   private void cargarSecuencia(RegistroPeso registroPeso)
/* 547:    */     throws ExcepcionAS2
/* 548:    */   {
/* 549:520 */     if (registroPeso.getNumero().equals(""))
/* 550:    */     {
/* 551:521 */       String numero = "";
/* 552:522 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(registroPeso.getDocumento().getId(), registroPeso.getFecha());
/* 553:523 */       registroPeso.setNumero(numero);
/* 554:    */     }
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void eliminar(RegistroPeso registroPeso)
/* 558:    */     throws AS2Exception
/* 559:    */   {
/* 560:529 */     this.registroPesoDao.eliminar(registroPeso);
/* 561:    */   }
/* 562:    */   
/* 563:    */   public RegistroPeso cargarDetalle(int idRegistroPeso)
/* 564:    */   {
/* 565:534 */     return this.registroPesoDao.cargarDetalle(idRegistroPeso);
/* 566:    */   }
/* 567:    */   
/* 568:    */   public RegistroPeso buscarPorId(Integer idRegistroPeso)
/* 569:    */   {
/* 570:539 */     return (RegistroPeso)this.registroPesoDao.buscarPorId(idRegistroPeso);
/* 571:    */   }
/* 572:    */   
/* 573:    */   public List<RegistroPeso> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 574:    */   {
/* 575:544 */     return this.registroPesoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 576:    */   }
/* 577:    */   
/* 578:    */   public int contarPorCriterio(Map<String, String> filters)
/* 579:    */   {
/* 580:549 */     return this.registroPesoDao.contarPorCriterio(filters);
/* 581:    */   }
/* 582:    */   
/* 583:    */   public void flush()
/* 584:    */   {
/* 585:554 */     this.registroPesoDao.flush();
/* 586:    */   }
/* 587:    */   
/* 588:    */   public List<Object[]> getReporte(int idRegistroPeso)
/* 589:    */   {
/* 590:559 */     return this.registroPesoDao.getReporte(idRegistroPeso);
/* 591:    */   }
/* 592:    */   
/* 593:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 594:    */   public void guardarParcial(RegistroPeso registroPeso)
/* 595:    */     throws AS2Exception, ExcepcionAS2
/* 596:    */   {
/* 597:565 */     RegistroPeso registroPesoBase = cargarDetalle(registroPeso.getId());
/* 598:567 */     if ((registroPeso.getProducto() == null) || (registroPesoBase.getProducto().getId() != registroPeso.getProducto().getId())) {
/* 599:568 */       throw new AS2Exception("msg_error_no_puede_cambiar_producto", new String[] { registroPeso.getProducto().getNombre() });
/* 600:    */     }
/* 601:    */     try
/* 602:    */     {
/* 603:571 */       if (registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA))
/* 604:    */       {
/* 605:573 */         if (registroPesoBase.getListaRecepcionProveedor().size() > 0)
/* 606:    */         {
/* 607:574 */           BigDecimal cantidad = registroPeso.getPesoNeto();
/* 608:575 */           BigDecimal cantidadNegativa = cantidad.negate();
/* 609:    */           
/* 610:577 */           this.pedidoProveedorDao.actualizarCantidadPorRecibir(registroPesoBase.getDetallePedidoProveedor(), cantidadNegativa);
/* 611:    */           
/* 612:579 */           this.pedidoProveedorDao.actualizarCantidadPorRecibir(registroPeso.getDetallePedidoProveedor(), cantidad);
/* 613:    */         }
/* 614:582 */         this.registroPesoDao.guardar(registroPeso);
/* 615:    */       }
/* 616:    */     }
/* 617:    */     catch (Exception e)
/* 618:    */     {
/* 619:585 */       this.context.setRollbackOnly();
/* 620:586 */       LOG.error(e);
/* 621:587 */       e.printStackTrace();
/* 622:588 */       throw new ExcepcionAS2(e);
/* 623:    */     }
/* 624:    */   }
/* 625:    */   
/* 626:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 627:    */   private void generarDespachoCliente(RegistroPeso registroPeso)
/* 628:    */     throws ExcepcionAS2Identification, ExcepcionAS2, ExcepcionAS2Inventario, ExcepcionAS2Financiero, AS2Exception
/* 629:    */   {
/* 630:    */     try
/* 631:    */     {
/* 632:596 */       DespachoCliente despachoCliente = new DespachoCliente();
/* 633:597 */       despachoCliente.setNumero("");
/* 634:598 */       despachoCliente.setIdOrganizacion(registroPeso.getIdOrganizacion());
/* 635:599 */       despachoCliente.setSucursal(this.servicioSucursal.buscarPorId(Integer.valueOf(registroPeso.getIdSucursal())));
/* 636:600 */       despachoCliente.setFecha(new Date());
/* 637:601 */       despachoCliente.setEstado(Estado.PROCESADO);
/* 638:602 */       despachoCliente.setDescripcion(registroPeso.getDescripcion());
/* 639:603 */       despachoCliente.setEmpresa(registroPeso.getEmpresa());
/* 640:604 */       DireccionEmpresa direccion = null;
/* 641:606 */       for (DireccionEmpresa direccionEmpresa : despachoCliente.getEmpresa().getDirecciones())
/* 642:    */       {
/* 643:607 */         direccion = direccionEmpresa;
/* 644:608 */         if (direccionEmpresa.isIndicadorDireccionEnvio()) {
/* 645:    */           break;
/* 646:    */         }
/* 647:    */       }
/* 648:612 */       despachoCliente.setDireccionEmpresa(direccion);
/* 649:    */       
/* 650:614 */       Documento documento = null;
/* 651:615 */       List<Documento> listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DESPACHO_BODEGA, registroPeso
/* 652:616 */         .getIdOrganizacion());
/* 653:617 */       if ((listaDocumento != null) && (!listaDocumento.isEmpty()))
/* 654:    */       {
/* 655:618 */         documento = (Documento)listaDocumento.get(0);
/* 656:619 */         despachoCliente.setDocumento(documento);
/* 657:    */       }
/* 658:    */       else
/* 659:    */       {
/* 660:621 */         throw new AS2Exception("ERROR_FALTA_PARAMETRIZAR_DOCUMENTO", new String[] { DocumentoBase.DESPACHO_BODEGA.toString() });
/* 661:    */       }
/* 662:624 */       List<DetalleRegistroPeso> listaDetalleRegistroPesoNoEliminados = new ArrayList();
/* 663:625 */       List<DetalleRegistroPesoLote> listaDetalleRegistroPesoLoteNoEliminados = new ArrayList();
/* 664:626 */       for (DetalleRegistroPeso rpdpc : registroPeso.getListaDetalleRegistroPeso()) {
/* 665:627 */         if (!rpdpc.isEliminado())
/* 666:    */         {
/* 667:628 */           despachoCliente.setPedidoCliente(rpdpc.getDetallePedidoCliente().getPedidoCliente());
/* 668:    */           
/* 669:630 */           DetallePedidoCliente detallePedido = rpdpc.getDetallePedidoCliente();
/* 670:631 */           if (detallePedido.getProducto().isIndicadorLote())
/* 671:    */           {
/* 672:632 */             boolean existeLote = false;
/* 673:633 */             for (DetalleRegistroPesoLote detalleRegistro : registroPeso.getListaDetalleRegistroPesoLote()) {
/* 674:634 */               if (!detalleRegistro.isEliminado())
/* 675:    */               {
/* 676:635 */                 if ((detalleRegistro.getProducto().getId() == detallePedido.getProducto().getId()) && (!detalleRegistro.isEliminado()) && 
/* 677:636 */                   (detalleRegistro.getCantidad().compareTo(BigDecimal.ZERO) > 0))
/* 678:    */                 {
/* 679:637 */                   existeLote = true;
/* 680:    */                   
/* 681:639 */                   crearDetalleDespachoCliente(despachoCliente, registroPeso, detallePedido, detalleRegistro);
/* 682:    */                 }
/* 683:641 */                 listaDetalleRegistroPesoLoteNoEliminados.add(detalleRegistro);
/* 684:    */               }
/* 685:    */             }
/* 686:644 */             if (!existeLote) {
/* 687:645 */               throw new AS2Exception("msg_error_lote_requerido", new String[] { "" });
/* 688:    */             }
/* 689:    */           }
/* 690:    */           else
/* 691:    */           {
/* 692:649 */             crearDetalleDespachoCliente(despachoCliente, registroPeso, detallePedido, null);
/* 693:    */           }
/* 694:651 */           listaDetalleRegistroPesoNoEliminados.add(rpdpc);
/* 695:    */         }
/* 696:    */       }
/* 697:655 */       this.servicioDespachoCliente.guardar(despachoCliente);
/* 698:    */       
/* 699:657 */       registroPeso.setDespachoCliente(despachoCliente);
/* 700:    */       
/* 701:659 */       crearGuiaRemision(registroPeso);
/* 702:    */       
/* 703:661 */       registroPeso.setListaDetalleRegistroPeso(listaDetalleRegistroPesoNoEliminados);
/* 704:662 */       registroPeso.setListaDetalleRegistroPesoLote(listaDetalleRegistroPesoLoteNoEliminados);
/* 705:663 */       this.registroPesoDao.guardar(registroPeso);
/* 706:    */     }
/* 707:    */     catch (ExcepcionAS2Identification e)
/* 708:    */     {
/* 709:665 */       e.printStackTrace();
/* 710:666 */       this.context.setRollbackOnly();
/* 711:667 */       throw e;
/* 712:    */     }
/* 713:    */     catch (ExcepcionAS2Inventario e)
/* 714:    */     {
/* 715:669 */       e.printStackTrace();
/* 716:670 */       this.context.setRollbackOnly();
/* 717:671 */       throw e;
/* 718:    */     }
/* 719:    */     catch (ExcepcionAS2Financiero e)
/* 720:    */     {
/* 721:673 */       e.printStackTrace();
/* 722:674 */       this.context.setRollbackOnly();
/* 723:675 */       throw e;
/* 724:    */     }
/* 725:    */     catch (ExcepcionAS2 e)
/* 726:    */     {
/* 727:677 */       e.printStackTrace();
/* 728:678 */       this.context.setRollbackOnly();
/* 729:679 */       throw e;
/* 730:    */     }
/* 731:    */     catch (AS2Exception e)
/* 732:    */     {
/* 733:681 */       e.printStackTrace();
/* 734:682 */       this.context.setRollbackOnly();
/* 735:683 */       throw e;
/* 736:    */     }
/* 737:    */     catch (Exception e)
/* 738:    */     {
/* 739:685 */       e.printStackTrace();
/* 740:686 */       this.context.setRollbackOnly();
/* 741:687 */       throw new ExcepcionAS2(e);
/* 742:    */     }
/* 743:    */   }
/* 744:    */   
/* 745:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 746:    */   private void crearGuiaRemision(RegistroPeso registroPeso)
/* 747:    */     throws ExcepcionAS2Identification, ExcepcionAS2, AS2Exception
/* 748:    */   {
/* 749:    */     try
/* 750:    */     {
/* 751:694 */       GuiaRemision guiaRemision = new GuiaRemision();
/* 752:695 */       guiaRemision.setIdOrganizacion(registroPeso.getIdOrganizacion());
/* 753:696 */       guiaRemision.setIdSucursal(registroPeso.getIdSucursal());
/* 754:697 */       guiaRemision.setDespachoCliente(registroPeso.getDespachoCliente());
/* 755:698 */       guiaRemision.setCiudadDestino(registroPeso.getDespachoCliente().getDireccionEmpresa().getCiudad());
/* 756:699 */       guiaRemision.setCiudadOrigen(registroPeso.getDespachoCliente().getSucursal().getCiudad());
/* 757:    */       
/* 758:701 */       Map<String, String> filtros = new HashMap();
/* 759:702 */       filtros.put("idOrganizacion", registroPeso.getIdOrganizacion() + "");
/* 760:703 */       filtros.put("codigo", "=C");
/* 761:704 */       List<TipoIdentificacion> listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, filtros);
/* 762:705 */       if (listaTipoIdentificacion.size() > 0) {
/* 763:706 */         guiaRemision.setTipoIdentificacionTransportista((TipoIdentificacion)listaTipoIdentificacion.get(0));
/* 764:    */       } else {
/* 765:708 */         throw new AS2Exception("msg_error_no_existe_tipo_identificacion_codigo", new String[] { "C" });
/* 766:    */       }
/* 767:711 */       Documento documento = null;
/* 768:712 */       List<Documento> listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.GUIA_REMISION, registroPeso
/* 769:713 */         .getIdOrganizacion());
/* 770:714 */       if ((listaDocumento != null) && (!listaDocumento.isEmpty()))
/* 771:    */       {
/* 772:715 */         documento = (Documento)listaDocumento.get(0);
/* 773:716 */         guiaRemision.setDocumento(documento);
/* 774:    */       }
/* 775:    */       else
/* 776:    */       {
/* 777:718 */         throw new AS2Exception("ERROR_FALTA_PARAMETRIZAR_DOCUMENTO", new String[] { DocumentoBase.GUIA_REMISION.toString() });
/* 778:    */       }
/* 779:722 */       if (guiaRemision.getDocumento().isIndicadorDocumentoTributario()) {
/* 780:723 */         this.servicioGuiaRemision.cargarSecuencia(guiaRemision, AppUtil.getPuntoDeVenta());
/* 781:    */       } else {
/* 782:725 */         this.servicioGuiaRemision.cargarSecuencia(guiaRemision, null);
/* 783:    */       }
/* 784:728 */       guiaRemision.setConductor(registroPeso.getChofer().getNombre());
/* 785:729 */       guiaRemision.setDescripcion(registroPeso.getDescripcion());
/* 786:730 */       guiaRemision.setDireccionEmpresa(registroPeso.getDespachoCliente().getDireccionEmpresa());
/* 787:731 */       guiaRemision.setEmpresa(registroPeso.getEmpresa());
/* 788:732 */       guiaRemision.setEstado(Estado.PROCESADO);
/* 789:733 */       guiaRemision.setFecha(registroPeso.getFechaSalida());
/* 790:734 */       guiaRemision.setFechaVigencia(registroPeso.getFechaSalida());
/* 791:735 */       guiaRemision.setHoraSalida(registroPeso.getFechaSalida());
/* 792:736 */       guiaRemision.setHoraLlegada(registroPeso.getFechaSalida());
/* 793:737 */       guiaRemision.setLicencia(registroPeso.getChofer().getLicencia());
/* 794:738 */       guiaRemision.setPlaca(registroPeso.getVehiculo().getPlaca());
/* 795:739 */       guiaRemision.setTransportista(registroPeso.getTransportista());
/* 796:740 */       guiaRemision.setVehiculo(registroPeso.getVehiculo());
/* 797:741 */       guiaRemision.setEmailTransportista(registroPeso.getTransportista().getEmail());
/* 798:    */       
/* 799:743 */       guiaRemision.setEmailCliente(this.servicioEmpresa.cargarMails(registroPeso.getEmpresa(), DocumentoBase.GUIA_REMISION));
/* 800:744 */       int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(registroPeso.getIdOrganizacion()).booleanValue() ? 2 : 1;
/* 801:745 */       guiaRemision.setAmbiente(ambiente);
/* 802:746 */       guiaRemision.setDireccionSucursal(registroPeso.getDespachoCliente().getSucursal().getUbicacion().getDireccionCompleta());
/* 803:747 */       String dirMatriz = "";
/* 804:    */       try
/* 805:    */       {
/* 806:749 */         dirMatriz = this.servicioOrganizacion.obtenerDireccionMatriz(guiaRemision.getIdOrganizacion());
/* 807:    */       }
/* 808:    */       catch (Exception e)
/* 809:    */       {
/* 810:751 */         dirMatriz = "N/A";
/* 811:    */       }
/* 812:753 */       guiaRemision.setDireccionMatriz(dirMatriz);
/* 813:    */       
/* 814:    */ 
/* 815:756 */       this.servicioGuiaRemision.guardar(guiaRemision);
/* 816:    */     }
/* 817:    */     catch (ExcepcionAS2Identification e)
/* 818:    */     {
/* 819:759 */       this.context.setRollbackOnly();
/* 820:760 */       throw e;
/* 821:    */     }
/* 822:    */     catch (ExcepcionAS2 e)
/* 823:    */     {
/* 824:762 */       this.context.setRollbackOnly();
/* 825:763 */       throw e;
/* 826:    */     }
/* 827:    */     catch (AS2Exception e)
/* 828:    */     {
/* 829:765 */       this.context.setRollbackOnly();
/* 830:766 */       throw e;
/* 831:    */     }
/* 832:    */     catch (Exception e)
/* 833:    */     {
/* 834:768 */       this.context.setRollbackOnly();
/* 835:769 */       throw new ExcepcionAS2(e);
/* 836:    */     }
/* 837:    */   }
/* 838:    */   
/* 839:    */   private void crearDetalleDespachoCliente(DespachoCliente despachoCliente, RegistroPeso registroPeso, DetallePedidoCliente detallePedido, DetalleRegistroPesoLote detalleRegistro)
/* 840:    */   {
/* 841:775 */     BigDecimal cantidad = detallePedido.getCantidadPorDespachar();
/* 842:776 */     if (detalleRegistro != null) {
/* 843:777 */       cantidad = detalleRegistro.getCantidad();
/* 844:    */     }
/* 845:779 */     DetalleDespachoCliente detalle = new DetalleDespachoCliente();
/* 846:780 */     detalle.setIdOrganizacion(despachoCliente.getIdOrganizacion());
/* 847:781 */     detalle.setIdSucursal(despachoCliente.getSucursal().getId());
/* 848:782 */     detalle.setBodega(registroPeso.getBodega());
/* 849:783 */     detalle.setCantidad(cantidad);
/* 850:784 */     detalle.setDetallePedidoCliente(detallePedido);
/* 851:785 */     detalle.setUnidadVenta(detallePedido.getUnidadVenta());
/* 852:786 */     detalle.setProducto(detallePedido.getProducto());
/* 853:    */     
/* 854:788 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* 855:789 */     inventarioProducto.setOperacion(despachoCliente.getDocumento().getOperacion());
/* 856:790 */     inventarioProducto.setProducto(detallePedido.getProducto());
/* 857:791 */     if (detalleRegistro != null) {
/* 858:792 */       inventarioProducto.setLote(detalleRegistro.getLote());
/* 859:    */     }
/* 860:794 */     detalle.setInventarioProducto(inventarioProducto);
/* 861:795 */     detalle.setDespachoCliente(despachoCliente);
/* 862:    */     
/* 863:797 */     despachoCliente.getListaDetalleDespachoCliente().add(detalle);
/* 864:    */   }
/* 865:    */   
/* 866:    */   public List<RegistroPeso> obtenerListaRegistroPesoPendientesPorLiquidar(Empresa empresaTransportista)
/* 867:    */   {
/* 868:802 */     return this.registroPesoDao.obtenerListaRegistroPesoPendientesPorLiquidar(empresaTransportista);
/* 869:    */   }
/* 870:    */   
/* 871:    */   public void actualizarLiquidacion(FacturaProveedor facturaProveedor)
/* 872:    */   {
/* 873:807 */     boolean anular = facturaProveedor.getEstado().equals(Estado.ANULADO);
/* 874:808 */     for (Iterator localIterator1 = facturaProveedor.getListaDetalleFacturaProveedor().iterator(); localIterator1.hasNext();)
/* 875:    */     {
/* 876:808 */       detalle = (DetalleFacturaProveedor)localIterator1.next();
/* 877:809 */       if (!detalle.getListaRegistroPesoLiquidados().isEmpty()) {
/* 878:810 */         for (RegistroPeso registroPeso : detalle.getListaRegistroPesoLiquidados())
/* 879:    */         {
/* 880:811 */           if ((!detalle.isEliminado()) && (!anular))
/* 881:    */           {
/* 882:812 */             registroPeso.setDetalleFacturaProveedor(detalle);
/* 883:813 */             registroPeso.setIndicadorLiquidadoFlete(true);
/* 884:    */           }
/* 885:    */           else
/* 886:    */           {
/* 887:815 */             registroPeso.setDetalleFacturaProveedor(null);
/* 888:816 */             registroPeso.setIndicadorLiquidadoFlete(false);
/* 889:    */           }
/* 890:818 */           this.registroPesoDao.guardar(registroPeso);
/* 891:    */         }
/* 892:    */       }
/* 893:    */     }
/* 894:    */     DetalleFacturaProveedor detalle;
/* 895:    */   }
/* 896:    */   
/* 897:    */   public List<Object[]> getReporteFleteTransportistas(int idOrganizacion, Date fechaDesde, Date fechaHasta, Transportista transportista, boolean indicadorLiquidados, boolean indicadorPorLiquidar)
/* 898:    */   {
/* 899:827 */     return this.registroPesoDao.getReporteFleteTransportistas(idOrganizacion, fechaDesde, fechaHasta, transportista, indicadorLiquidados, indicadorPorLiquidar);
/* 900:    */   }
/* 901:    */   
/* 902:    */   private void actualizarDatosControlCalidad(RegistroPeso registroPeso)
/* 903:    */   {
/* 904:832 */     if (((registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) || (registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA))) && 
/* 905:833 */       (registroPeso.getEstado().equals(EstadoRegistroPeso.SEGUNDO_PESO)) && (registroPeso.getProducto().getIndicadorControlCalidad().booleanValue()))
/* 906:    */     {
/* 907:834 */       if (registroPeso.getEstadoControlCalidad() == null) {
/* 908:835 */         registroPeso.setEstadoControlCalidad(EstadoControlCalidad.PENDIENTE);
/* 909:    */       }
/* 910:837 */       if (registroPeso.getControlCalidadFinalizado() == null) {
/* 911:838 */         registroPeso.setControlCalidadFinalizado(new Boolean(false));
/* 912:    */       }
/* 913:    */     }
/* 914:    */   }
/* 915:    */   
/* 916:    */   public List<Object[]> getReporteCalidadMateriaPrima(Date fechaDesde, Date fechaHasta, EstadoControlCalidad estado, PedidoProveedor pedidoProveedor)
/* 917:    */   {
/* 918:846 */     return this.registroPesoDao.getReporteCalidadMateriaPrima(fechaDesde, fechaHasta, estado, pedidoProveedor);
/* 919:    */   }
/* 920:    */   
/* 921:    */   public List<Object[]> getReporteRegistroPesoRecepcionMateriaPrima(Date fechaDesde, Date fechaHasta, Empresa proveedor, Producto producto, Lote lote)
/* 922:    */   {
/* 923:852 */     return this.registroPesoDao.getReporteRegistroPesoRecepcionMateriaPrima(fechaDesde, fechaHasta, proveedor, producto, lote);
/* 924:    */   }
/* 925:    */   
/* 926:    */   public List<Object[]> getReporteRegistroPesoTransferencia(Date fechaDesde, Date fechaHasta, Producto producto, MovimientoInventario transferencia, Lote lote, TipoRegistroPeso tipoRegistroPeso)
/* 927:    */   {
/* 928:858 */     return this.registroPesoDao.getReporteRegistroPesoTransferencia(fechaDesde, fechaHasta, producto, transferencia, lote, tipoRegistroPeso);
/* 929:    */   }
/* 930:    */   
/* 931:    */   public BigDecimal getCantidadRegistroRecepcionProveedor(RegistroPeso registroPeso)
/* 932:    */   {
/* 933:863 */     BigDecimal cantidadRecepcion = BigDecimal.ZERO;
/* 934:864 */     if ((registroPeso.getEstado().equals(EstadoRegistroPeso.SEGUNDO_PESO)) && 
/* 935:865 */       (registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) && 
/* 936:866 */       (registroPeso.getDetallePedidoProveedor() != null)) {
/* 937:867 */       if (registroPeso.getDetallePedidoProveedor().getUnidadCompra().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA)) {
/* 938:868 */         cantidadRecepcion = registroPeso.getPesoNeto();
/* 939:    */       } else {
/* 940:870 */         cantidadRecepcion = registroPeso.getCantidad();
/* 941:    */       }
/* 942:    */     }
/* 943:873 */     return cantidadRecepcion;
/* 944:    */   }
/* 945:    */   
/* 946:    */   public List<Object[]> getReporteProductoCuarentena(Date fechaHasta, Empresa proveedor, Producto producto, PedidoProveedor pedidoProveedor)
/* 947:    */   {
/* 948:878 */     return this.registroPesoDao.getReporteProductoCuarentena(fechaHasta, proveedor, producto, pedidoProveedor);
/* 949:    */   }
/* 950:    */   
/* 951:    */   private void generarTransferenciaBodega(RegistroPeso registroPeso)
/* 952:    */     throws ExcepcionAS2, AS2Exception, ExcepcionAS2Inventario
/* 953:    */   {
/* 954:882 */     OrdenSalidaMaterial ordenSalidaMaterial = registroPeso.getOrdenSalidaMaterial();
/* 955:    */     
/* 956:884 */     MovimientoInventario transferenciaBodega = new MovimientoInventario();
/* 957:885 */     List<Documento> listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.TRANSFERENCIA_BODEGA, ordenSalidaMaterial
/* 958:886 */       .getIdOrganizacion());
/* 959:888 */     if (!listaDocumentoCombo.isEmpty()) {
/* 960:889 */       transferenciaBodega.setDocumento((Documento)listaDocumentoCombo.get(0));
/* 961:    */     }
/* 962:894 */     transferenciaBodega.setIdOrganizacion(ordenSalidaMaterial.getIdOrganizacion());
/* 963:895 */     transferenciaBodega.setFecha(new Date());
/* 964:896 */     transferenciaBodega.setNumero("");
/* 965:897 */     transferenciaBodega.setEstado(Estado.ELABORADO);
/* 966:898 */     transferenciaBodega.setSucursal(ordenSalidaMaterial.getSucursal());
/* 967:899 */     transferenciaBodega.setBodegaOrigen(ordenSalidaMaterial.getBodegaOrigen());
/* 968:    */     
/* 969:901 */     List<DetalleRegistroPeso> listaDetalleRegistroPesoNoEliminados = new ArrayList();
/* 970:902 */     List<DetalleRegistroPesoLote> listaDetalleRegistroPesoLoteNoEliminados = new ArrayList();
/* 971:903 */     for (DetalleRegistroPeso rpdpc : registroPeso.getListaDetalleRegistroPeso()) {
/* 972:904 */       if (!rpdpc.isEliminado())
/* 973:    */       {
/* 974:906 */         DetalleOrdenSalidaMaterial detalleOSM = rpdpc.getDetalleOrdenSalidaMaterial();
/* 975:907 */         if (detalleOSM.getProducto().isIndicadorLote())
/* 976:    */         {
/* 977:908 */           boolean existeLote = false;
/* 978:909 */           for (DetalleRegistroPesoLote detalleRegistro : registroPeso.getListaDetalleRegistroPesoLote()) {
/* 979:910 */             if ((detalleRegistro.getProducto().getId() == detalleOSM.getProducto().getId()) && (!detalleRegistro.isEliminado()) && 
/* 980:911 */               (detalleRegistro.getCantidad().compareTo(BigDecimal.ZERO) > 0))
/* 981:    */             {
/* 982:912 */               existeLote = true;
/* 983:913 */               crearDetalleMovimientoInventario(registroPeso, transferenciaBodega, detalleOSM, detalleRegistro);
/* 984:914 */               listaDetalleRegistroPesoLoteNoEliminados.add(detalleRegistro);
/* 985:    */             }
/* 986:    */           }
/* 987:917 */           if (!existeLote) {
/* 988:918 */             throw new AS2Exception("msg_error_lote_requerido", new String[] { "" });
/* 989:    */           }
/* 990:    */         }
/* 991:    */         else
/* 992:    */         {
/* 993:921 */           crearDetalleMovimientoInventario(registroPeso, transferenciaBodega, detalleOSM, null);
/* 994:    */         }
/* 995:923 */         listaDetalleRegistroPesoNoEliminados.add(rpdpc);
/* 996:    */       }
/* 997:    */     }
/* 998:926 */     this.servicioOrdenSalidaMaterial.asignarBodegaDestinoTransferenciaDeBodega(transferenciaBodega);
/* 999:927 */     this.servicioMovimientoInventario.guardar(transferenciaBodega);
/* :00:928 */     registroPeso.setTransferenciaBodega(transferenciaBodega);
/* :01:929 */     registroPeso.setListaDetalleRegistroPeso(listaDetalleRegistroPesoNoEliminados);
/* :02:930 */     registroPeso.setListaDetalleRegistroPesoLote(listaDetalleRegistroPesoLoteNoEliminados);
/* :03:931 */     this.registroPesoDao.guardar(registroPeso);
/* :04:932 */     ordenSalidaMaterial.setEstado(Estado.PROCESADO);
/* :05:933 */     this.ordenSalidaMaterialDao.guardar(ordenSalidaMaterial);
/* :06:    */   }
/* :07:    */   
/* :08:    */   public void crearDetalleMovimientoInventario(RegistroPeso registroPeso, MovimientoInventario transferenciaBodega, DetalleOrdenSalidaMaterial detalleOSM, DetalleRegistroPesoLote detalleRegistroPesoLote)
/* :09:    */     throws ExcepcionAS2
/* :10:    */   {
/* :11:938 */     BigDecimal cantidad = detalleOSM.getCantidad();
/* :12:939 */     if (detalleRegistroPesoLote != null) {
/* :13:940 */       cantidad = detalleRegistroPesoLote.getCantidad();
/* :14:    */     }
/* :15:942 */     DetalleMovimientoInventario detMov = new DetalleMovimientoInventario();
/* :16:943 */     detMov.setIdOrganizacion(registroPeso.getIdOrganizacion());
/* :17:944 */     detMov.setIdSucursal(registroPeso.getIdSucursal());
/* :18:945 */     detMov.setMovimientoInventario(transferenciaBodega);
/* :19:    */     
/* :20:947 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* :21:948 */     inventarioProducto.setOperacion(transferenciaBodega.getDocumento().getOperacion());
/* :22:949 */     inventarioProducto.setProducto(detalleOSM.getProducto());
/* :23:950 */     if (detalleRegistroPesoLote != null) {
/* :24:951 */       inventarioProducto.setLote(detalleRegistroPesoLote.getLote());
/* :25:    */     }
/* :26:953 */     inventarioProducto.setDetalleMovimientoInventario(detMov);
/* :27:954 */     detMov.setInventarioProducto(inventarioProducto);
/* :28:    */     
/* :29:956 */     detMov.setProducto(detalleOSM.getProducto());
/* :30:957 */     detMov.setCantidad(cantidad);
/* :31:958 */     detMov.setCantidadOrigen(cantidad);
/* :32:959 */     detMov.setTraCantidadCoversion(cantidad);
/* :33:    */     
/* :34:    */ 
/* :35:962 */     detMov.setBodegaOrigen(transferenciaBodega.getBodegaOrigen());
/* :36:963 */     detMov.setBodegaDestino(detalleOSM.getBodega());
/* :37:964 */     detMov.setUnidadConversion(detalleOSM.getProducto().getUnidad());
/* :38:965 */     detMov.setSaldo(this.servicioProducto
/* :39:966 */       .getSaldo(detalleOSM.getProducto().getId(), transferenciaBodega.getBodegaOrigen().getId(), registroPeso.getFecha()));
/* :40:967 */     this.servicioUnidadConversion.cargarListaUnidadConversion(detalleOSM.getProducto());
/* :41:968 */     transferenciaBodega.getDetalleMovimientosInventario().add(detMov);
/* :42:    */   }
/* :43:    */   
/* :44:    */   public List<RegistroPeso> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* :45:    */   {
/* :46:973 */     return this.registroPesoDao.obtenerListaCombo(sortField, sortOrder, filtros);
/* :47:    */   }
/* :48:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioRegistroPesoImpl
 * JD-Core Version:    0.7.0.1
 */