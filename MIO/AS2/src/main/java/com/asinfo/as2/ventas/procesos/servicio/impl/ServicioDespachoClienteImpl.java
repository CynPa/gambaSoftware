/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DespachoClienteDao;
/*   4:    */ import com.asinfo.as2.dao.DetalleDespachoClienteDao;
/*   5:    */ import com.asinfo.as2.dao.DetalleFacturaClienteDao;
/*   6:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   7:    */ import com.asinfo.as2.dao.InventarioProductoDao;
/*   8:    */ import com.asinfo.as2.dao.reportes.ventas.ReporteDespachoClienteDao;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  11:    */ import com.asinfo.as2.entities.Asiento;
/*  12:    */ import com.asinfo.as2.entities.Bodega;
/*  13:    */ import com.asinfo.as2.entities.Cliente;
/*  14:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  15:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  16:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  17:    */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*  18:    */ import com.asinfo.as2.entities.Documento;
/*  19:    */ import com.asinfo.as2.entities.Empresa;
/*  20:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  21:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  22:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  23:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  24:    */ import com.asinfo.as2.entities.Lote;
/*  25:    */ import com.asinfo.as2.entities.PedidoCliente;
/*  26:    */ import com.asinfo.as2.entities.Producto;
/*  27:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  28:    */ import com.asinfo.as2.entities.SerieInventarioProducto;
/*  29:    */ import com.asinfo.as2.entities.Subempresa;
/*  30:    */ import com.asinfo.as2.entities.Sucursal;
/*  31:    */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*  32:    */ import com.asinfo.as2.entities.Unidad;
/*  33:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  34:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  35:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  36:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  37:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  38:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  39:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  40:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  41:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  42:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  43:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  44:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  45:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  46:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  47:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*  48:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  49:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioSerieInventarioProducto;
/*  50:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*  51:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  52:    */ import com.asinfo.as2.util.AppUtil;
/*  53:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  54:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  55:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  56:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  57:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  58:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  59:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*  60:    */ import java.io.IOException;
/*  61:    */ import java.io.InputStream;
/*  62:    */ import java.io.PrintStream;
/*  63:    */ import java.math.BigDecimal;
/*  64:    */ import java.math.RoundingMode;
/*  65:    */ import java.util.ArrayList;
/*  66:    */ import java.util.Date;
/*  67:    */ import java.util.HashMap;
/*  68:    */ import java.util.Iterator;
/*  69:    */ import java.util.List;
/*  70:    */ import java.util.Map;
/*  71:    */ import javax.ejb.EJB;
/*  72:    */ import javax.ejb.SessionContext;
/*  73:    */ import javax.ejb.Stateless;
/*  74:    */ import javax.ejb.TransactionAttribute;
/*  75:    */ import javax.ejb.TransactionAttributeType;
/*  76:    */ import javax.ejb.TransactionManagement;
/*  77:    */ import javax.ejb.TransactionManagementType;
/*  78:    */ 
/*  79:    */ @Stateless
/*  80:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  81:    */ public class ServicioDespachoClienteImpl
/*  82:    */   extends AbstractServicioAS2Financiero
/*  83:    */   implements ServicioDespachoCliente
/*  84:    */ {
/*  85:    */   private static final long serialVersionUID = -8181844210742442787L;
/*  86:    */   @EJB
/*  87:    */   private transient DespachoClienteDao despachoClienteDao;
/*  88:    */   @EJB
/*  89:    */   private transient DetalleDespachoClienteDao detalleDespachoClienteDao;
/*  90:    */   @EJB
/*  91:    */   private transient ServicioSecuencia servicioSecuencia;
/*  92:    */   @EJB
/*  93:    */   private transient ReporteDespachoClienteDao reporteDespachoClienteDao;
/*  94:    */   @EJB
/*  95:    */   private transient ServicioProducto servicioProducto;
/*  96:    */   @EJB
/*  97:    */   private transient ServicioPeriodo servicioPeriodo;
/*  98:    */   @EJB
/*  99:    */   private transient ServicioPedidoCliente servicioPedidoCliente;
/* 100:    */   @EJB
/* 101:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/* 102:    */   @EJB
/* 103:    */   private transient FacturaClienteDao facturaClienteDao;
/* 104:    */   @EJB
/* 105:    */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/* 106:    */   @EJB
/* 107:    */   private transient DetalleFacturaClienteDao detalleFacturaClienteDao;
/* 108:    */   @EJB
/* 109:    */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/* 110:    */   @EJB
/* 111:    */   private transient ServicioInventarioProducto servicioInventarioProducto;
/* 112:    */   @EJB
/* 113:    */   private transient ServicioCosteo servicioCosteo;
/* 114:    */   @EJB
/* 115:    */   private transient ServicioLote servicioLote;
/* 116:    */   @EJB
/* 117:    */   private transient ServicioSerieInventarioProducto servicioSerieInventarioProducto;
/* 118:    */   @EJB
/* 119:    */   private transient InventarioProductoDao inventarioProductoDao;
/* 120:    */   @EJB
/* 121:    */   protected transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/* 122:    */   @EJB
/* 123:    */   protected transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/* 124:    */   @EJB
/* 125:    */   protected transient ServicioListaPrecios servicioListaPrecios;
/* 126:    */   
/* 127:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 128:    */   public void guardar(DespachoCliente despachoCliente)
/* 129:    */     throws ExcepcionAS2, ExcepcionAS2Inventario, ExcepcionAS2Financiero, AS2Exception
/* 130:    */   {
/* 131:147 */     boolean indicadorFacturarAlDespachar = ParametrosSistema.getIndicadorFacturarAlDespachar(despachoCliente.getIdOrganizacion()).booleanValue();
/* 132:    */     try
/* 133:    */     {
/* 134:150 */       asignarTipoOrdenDespacho(despachoCliente);
/* 135:    */       Iterator localIterator1;
/* 136:152 */       if (ParametrosSistema.getDespacharServicio(despachoCliente.getIdOrganizacion()).booleanValue()) {
/* 137:153 */         for (localIterator1 = despachoCliente.getListaDetalleDespachoCliente().iterator(); localIterator1.hasNext();)
/* 138:    */         {
/* 139:153 */           ddc = (DetalleDespachoCliente)localIterator1.next();
/* 140:154 */           if ((ddc.getProducto() != null) && (ddc.getProducto().isTraIndicadorServicio())) {
/* 141:155 */             ddc.setInventarioProducto(null);
/* 142:    */           }
/* 143:    */         }
/* 144:    */       }
/* 145:    */       DetalleDespachoCliente ddc;
/* 146:160 */       actualizarInventarioProducto(despachoCliente);
/* 147:    */       
/* 148:    */ 
/* 149:163 */       validar(despachoCliente);
/* 150:    */       
/* 151:    */ 
/* 152:166 */       Cliente cliente = despachoCliente.getEmpresa().getCliente();
/* 153:167 */       if ((cliente.getTipoOrdenDespacho() == null) || (!cliente.getTipoOrdenDespacho().isIndicadorGuardarSinDetalle())) {
/* 154:169 */         this.servicioVerificadorInventario.cantidadDetalle(despachoCliente.getListaDetalleDespachoCliente());
/* 155:    */       }
/* 156:174 */       this.servicioVerificadorVentas.actualizarCantidadPorDespachar(despachoCliente, true);
/* 157:    */       
/* 158:176 */       this.servicioVerificadorInventario.actualizarSaldoProducto(despachoCliente, true, despachoCliente.getFecha());
/* 159:    */       
/* 160:178 */       this.servicioVerificadorInventario.actualizarSaldoProducto(despachoCliente, false, despachoCliente.getFecha());
/* 161:    */       
/* 162:    */ 
/* 163:181 */       cargarSecuencia(despachoCliente);
/* 164:184 */       for (DetalleDespachoCliente ddc : despachoCliente.getListaDetalleDespachoCliente()) {
/* 165:186 */         if (!ddc.isEliminado())
/* 166:    */         {
/* 167:188 */           if ((ddc.getProducto().getIndicadorSerie().booleanValue()) && (!ddc.getProducto().isTraIndicadorServicio())) {
/* 168:189 */             for (SerieInventarioProducto serie : ddc.getInventarioProducto().getListaSerieProducto()) {
/* 169:190 */               this.servicioSerieInventarioProducto.guardar(serie);
/* 170:    */             }
/* 171:    */           }
/* 172:194 */           if (!ddc.getProducto().isTraIndicadorServicio())
/* 173:    */           {
/* 174:195 */             ddc.getInventarioProducto().setNumeroDocumento(despachoCliente.getNumero());
/* 175:196 */             this.servicioInventarioProducto.guardar(ddc.getInventarioProducto());
/* 176:    */           }
/* 177:199 */           if ((ddc.getInventarioProducto() != null) && (ddc.getInventarioProducto().getLote() != null) && (ddc.getInventarioProducto().getLote().getCodigo() != null)) {
/* 178:200 */             ddc.setCodigoLote(ddc.getInventarioProducto().getLote().getCodigo());
/* 179:    */           }
/* 180:203 */           this.detalleDespachoClienteDao.guardar(ddc);
/* 181:206 */           if (ddc.getDetalleFacturaCliente() != null)
/* 182:    */           {
/* 183:207 */             ddc.getDetalleFacturaCliente().setDetalleDespachoCliente(ddc);
/* 184:208 */             this.detalleFacturaClienteDao.guardar(ddc.getDetalleFacturaCliente());
/* 185:    */           }
/* 186:    */         }
/* 187:    */       }
/* 188:215 */       this.despachoClienteDao.guardar(despachoCliente);
/* 189:217 */       if (despachoCliente.getFacturaCliente() != null)
/* 190:    */       {
/* 191:218 */         FacturaCliente facBase = this.facturaClienteDao.buscarPorId(Integer.valueOf(despachoCliente.getFacturaCliente().getIdFacturaCliente()));
/* 192:219 */         facBase.setDespachoCliente(despachoCliente);
/* 193:220 */         this.facturaClienteDao.guardar(facBase);
/* 194:    */       }
/* 195:223 */       if (ParametrosSistema.getGeneracionDeCostosAutomatica(despachoCliente.getIdOrganizacion()).booleanValue()) {
/* 196:225 */         this.servicioCosteo.generarCostos(despachoCliente, ParametrosSistema.isCosteoPorBodega(despachoCliente.getIdOrganizacion()).booleanValue());
/* 197:    */       }
/* 198:229 */       this.servicioVerificadorVentas.actualizarCantidadPorDespachar(despachoCliente, false);
/* 199:232 */       if ((despachoCliente.getFacturaCliente() == null) && (indicadorFacturarAlDespachar) && (!despachoCliente.isIndicadorGeneradoFactura()))
/* 200:    */       {
/* 201:233 */         FacturaCliente facturaCliente = null;
/* 202:    */         
/* 203:235 */         facturaCliente = this.servicioFacturaCliente.crearFacturaDesdeDespacho(null, despachoCliente.getId(), AppUtil.getPuntoDeVenta(), null, ParametrosSistema.isBloqueoProductoListaPrecios(despachoCliente.getIdOrganizacion()).booleanValue());
/* 204:236 */         if (facturaCliente != null)
/* 205:    */         {
/* 206:237 */           this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 207:    */           
/* 208:239 */           PuntoDeVenta puntoDeVenta = this.servicioFacturaCliente.cargarPuntoVenta(facturaCliente);
/* 209:    */           
/* 210:241 */           this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(facturaCliente, puntoDeVenta);
/* 211:242 */           this.servicioFacturaCliente.guardar(facturaCliente);
/* 212:    */         }
/* 213:    */       }
/* 214:246 */       if ((despachoCliente.getDocumento().isIndicadorContabilizar()) && 
/* 215:247 */         (ParametrosSistema.getContabilizacionDespachos(despachoCliente.getIdOrganizacion()).booleanValue()))
/* 216:    */       {
/* 217:248 */         List<DespachoCliente> listaDespachoCliente = new ArrayList();
/* 218:249 */         listaDespachoCliente.add(despachoCliente);
/* 219:250 */         contabilizar(listaDespachoCliente);
/* 220:    */       }
/* 221:    */     }
/* 222:    */     catch (ExcepcionAS2Inventario e)
/* 223:    */     {
/* 224:254 */       this.context.setRollbackOnly();
/* 225:255 */       throw e;
/* 226:    */     }
/* 227:    */     catch (ExcepcionAS2Financiero e)
/* 228:    */     {
/* 229:257 */       this.context.setRollbackOnly();
/* 230:258 */       throw e;
/* 231:    */     }
/* 232:    */     catch (ExcepcionAS2 e)
/* 233:    */     {
/* 234:261 */       if ((!indicadorFacturarAlDespachar) || ((!"msg_error_producto_no_lista_precios".equals(e.getCodigoExcepcion())) && 
/* 235:262 */         (!"msg_secuencia_no_encontrada".equals(e.getCodigoExcepcion())))) {
/* 236:263 */         this.context.setRollbackOnly();
/* 237:    */       }
/* 238:265 */       throw e;
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   private void asignarTipoOrdenDespacho(DespachoCliente despachoCliente)
/* 243:    */   {
/* 244:270 */     TipoOrdenDespacho tipoOrdenDespacho = null;
/* 245:271 */     if ((despachoCliente.getSubempresa() != null) && (despachoCliente.getSubempresa().getEmpresa() != null) && 
/* 246:272 */       (despachoCliente.getSubempresa().getEmpresa().getCliente() != null)) {
/* 247:273 */       tipoOrdenDespacho = despachoCliente.getSubempresa().getEmpresa().getCliente().getTipoOrdenDespacho();
/* 248:    */     } else {
/* 249:275 */       tipoOrdenDespacho = despachoCliente.getEmpresa().getCliente().getTipoOrdenDespacho();
/* 250:    */     }
/* 251:277 */     despachoCliente.setTipoOrdenDespacho(tipoOrdenDespacho);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void actualizarInventarioProducto(DespachoCliente despachoCliente)
/* 255:    */     throws ExcepcionAS2Inventario, ExcepcionAS2
/* 256:    */   {
/* 257:289 */     InventarioProducto inventarioProducto = null;
/* 258:290 */     for (DetalleDespachoCliente ddc : despachoCliente.getListaDetalleDespachoCliente()) {
/* 259:291 */       if ((!ddc.isEliminado()) && (!ddc.getProducto().isTraIndicadorServicio()))
/* 260:    */       {
/* 261:293 */         if (ddc.getInventarioProducto() != null)
/* 262:    */         {
/* 263:294 */           inventarioProducto = ddc.getInventarioProducto();
/* 264:    */         }
/* 265:    */         else
/* 266:    */         {
/* 267:296 */           inventarioProducto = new InventarioProducto();
/* 268:297 */           ddc.setInventarioProducto(inventarioProducto);
/* 269:    */         }
/* 270:299 */         inventarioProducto.setDetalleDespachoCliente(ddc);
/* 271:    */         
/* 272:301 */         BigDecimal cantidad = this.servicioProducto.convierteUnidad(ddc.getUnidadVenta(), ddc.getProducto().getUnidad(), ddc
/* 273:302 */           .getProducto().getIdProducto(), ddc.getCantidad());
/* 274:    */         
/* 275:304 */         inventarioProducto.setCantidad(cantidad);
/* 276:305 */         inventarioProducto.setCantidadDocumento(ddc.getCantidad());
/* 277:306 */         inventarioProducto.setUnidadDocumento(ddc.getUnidadVenta().getNombre());
/* 278:    */         
/* 279:308 */         inventarioProducto.setFecha(despachoCliente.getFecha());
/* 280:309 */         inventarioProducto.setDocumento(despachoCliente.getDocumento());
/* 281:310 */         inventarioProducto.setIdOrganizacion(despachoCliente.getIdOrganizacion());
/* 282:311 */         inventarioProducto.setOperacion(despachoCliente.getDocumento().getOperacion());
/* 283:312 */         inventarioProducto.setIndicadorGeneraCosto(despachoCliente.getDocumento().isIndicadorGeneraCosto());
/* 284:    */         
/* 285:314 */         inventarioProducto.setBodega(ddc.getBodega());
/* 286:315 */         inventarioProducto.setProducto(ddc.getProducto());
/* 287:316 */         inventarioProducto.setNumeroDocumento(despachoCliente.getNumero());
/* 288:317 */         inventarioProducto.setPeso(ddc.getPeso());
/* 289:318 */         inventarioProducto.setProyecto(despachoCliente.getProyecto());
/* 290:319 */         inventarioProducto.setNombreFiscalEmpresa(despachoCliente.getEmpresa().getNombreFiscal());
/* 291:    */       }
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   private void validar(DespachoCliente despachoCliente)
/* 296:    */     throws ExcepcionAS2Inventario, ExcepcionAS2Financiero, AS2Exception
/* 297:    */   {
/* 298:336 */     this.servicioPeriodo.buscarPorFecha(despachoCliente.getFecha(), despachoCliente.getIdOrganizacion(), despachoCliente
/* 299:337 */       .getDocumento().getDocumentoBase());
/* 300:338 */     int cantidadItems = 0;
/* 301:339 */     for (DetalleDespachoCliente ddc : despachoCliente.getListaDetalleDespachoCliente()) {
/* 302:340 */       if (!ddc.isEliminado())
/* 303:    */       {
/* 304:341 */         cantidadItems++;
/* 305:343 */         if ((ddc.getProducto().getTipoProducto() != TipoProducto.ARTICULO) && 
/* 306:344 */           (!ParametrosSistema.getDespacharServicio(despachoCliente.getIdOrganizacion()).booleanValue())) {
/* 307:345 */           throw new ExcepcionAS2Inventario("msg_info_despacho_producto_tipo_articulo", ddc.getProducto().getCodigo());
/* 308:    */         }
/* 309:348 */         if (ddc.getCantidad().compareTo(BigDecimal.ZERO) == 0)
/* 310:    */         {
/* 311:349 */           System.out.println("Entra en validacion");
/* 312:350 */           throw new ExcepcionAS2Inventario("msg_error_cantidad_cero_despacho", " " + ddc.getProducto().getCodigo());
/* 313:    */         }
/* 314:    */       }
/* 315:    */     }
/* 316:355 */     Cliente cliente = despachoCliente.getEmpresa().getCliente();
/* 317:356 */     if ((cantidadItems == 0) && ((cliente.getTipoOrdenDespacho() == null) || (!cliente.getTipoOrdenDespacho().isIndicadorGuardarSinDetalle()))) {
/* 318:357 */       throw new ExcepcionAS2Inventario("msg_error_cantidad_cero_despacho", " ");
/* 319:    */     }
/* 320:360 */     if ((despachoCliente.getPedidoCliente() != null) && 
/* 321:361 */       (despachoCliente.getFecha().compareTo(despachoCliente.getPedidoCliente().getFecha()) < 0)) {
/* 322:363 */       throw new ExcepcionAS2Financiero("msgs_error_fecha_menor_a_registro_generado", "Pedido: " + despachoCliente.getPedidoCliente().getNumero());
/* 323:    */     }
/* 324:367 */     if (!ParametrosSistema.getDespacharServicio(despachoCliente.getIdOrganizacion()).booleanValue()) {
/* 325:368 */       this.servicioSerieInventarioProducto.validar(despachoCliente);
/* 326:    */     }
/* 327:    */   }
/* 328:    */   
/* 329:    */   private void cargarSecuencia(DespachoCliente despachoCliente)
/* 330:    */     throws ExcepcionAS2
/* 331:    */   {
/* 332:382 */     if (despachoCliente.getNumero().equals(""))
/* 333:    */     {
/* 334:383 */       String numero = "";
/* 335:384 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(despachoCliente.getDocumento().getId(), despachoCliente.getFecha());
/* 336:385 */       despachoCliente.setNumero(numero);
/* 337:    */     }
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void eliminar(DespachoCliente despachoCliente)
/* 341:    */     throws ExcepcionAS2
/* 342:    */   {
/* 343:397 */     for (DetalleDespachoCliente detalleDespachoCliente : despachoCliente.getListaDetalleDespachoCliente())
/* 344:    */     {
/* 345:398 */       detalleDespachoCliente.setDetalleFacturaCliente(null);
/* 346:399 */       detalleDespachoCliente.setDetallePedidoCliente(null);
/* 347:    */       
/* 348:401 */       this.detalleDespachoClienteDao.eliminar(detalleDespachoCliente);
/* 349:    */     }
/* 350:403 */     this.despachoClienteDao.eliminar(despachoCliente);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public DespachoCliente buscarPorId(Integer idDespachoCliente)
/* 354:    */   {
/* 355:413 */     return this.despachoClienteDao.buscarPorId(idDespachoCliente);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public DespachoCliente cargarDetalle(Integer idDespachoCliente)
/* 359:    */   {
/* 360:423 */     return this.despachoClienteDao.cargarDetalle(idDespachoCliente.intValue());
/* 361:    */   }
/* 362:    */   
/* 363:    */   public List<DespachoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 364:    */   {
/* 365:434 */     return this.despachoClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 366:    */   }
/* 367:    */   
/* 368:    */   public List<DespachoCliente> listaDespachosPorFacturar(Estado estado, int idEmpresa)
/* 369:    */     throws ExcepcionAS2
/* 370:    */   {
/* 371:444 */     return this.despachoClienteDao.listaDespachosPorFacturar(estado, idEmpresa);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public List getReporteDespachoCliente(DespachoCliente despachoCliente, int numeroAtributos)
/* 375:    */     throws ExcepcionAS2
/* 376:    */   {
/* 377:455 */     List<Object[]> lista = this.reporteDespachoClienteDao.getReporteDespachoCliente(despachoCliente, numeroAtributos);
/* 378:456 */     Map<Integer, BigDecimal> mapaProducto = new HashMap();
/* 379:    */     Object[] object;
/* 380:457 */     if (lista.size() > 0)
/* 381:    */     {
/* 382:459 */       Integer idListaPrecios = (Integer)((Object[])lista.get(0))[29];
/* 383:    */       List<ListaPrecios> listaPreciosCompra;
/* 384:461 */       if (idListaPrecios == null)
/* 385:    */       {
/* 386:463 */         Map<String, String> filltros = new HashMap();
/* 387:464 */         filltros.put("idOrganizacion", "" + despachoCliente.getIdOrganizacion());
/* 388:465 */         filltros.put("predeterminado", "true");
/* 389:466 */         filltros.put("indicadorCompra", "true");
/* 390:467 */         listaPreciosCompra = this.servicioListaPrecios.obtenerListaCombo("nombre", false, filltros);
/* 391:468 */         if (listaPreciosCompra.size() > 0) {
/* 392:469 */           idListaPrecios = Integer.valueOf(((ListaPrecios)listaPreciosCompra.get(0)).getIdListaPrecios());
/* 393:    */         }
/* 394:    */       }
/* 395:474 */       if (idListaPrecios != null)
/* 396:    */       {
/* 397:476 */         for (listaPreciosCompra = lista.iterator(); listaPreciosCompra.hasNext();)
/* 398:    */         {
/* 399:476 */           object = (Object[])listaPreciosCompra.next();
/* 400:477 */           mapaProducto.put((Integer)object[30], BigDecimal.ZERO);
/* 401:    */         }
/* 402:479 */         List<Integer> listaProductos = new ArrayList(mapaProducto.keySet());
/* 403:481 */         for (Integer idProducto : listaProductos) {
/* 404:    */           try
/* 405:    */           {
/* 406:484 */             BigDecimal precio = this.servicioListaPrecios.getDatosVersionListaPrecios(idListaPrecios.intValue(), idProducto.intValue(), despachoCliente.getFecha(), null, "").getPrecioUnitario();
/* 407:486 */             if (precio.compareTo(BigDecimal.ZERO) != 0) {
/* 408:487 */               mapaProducto.put(idProducto, precio);
/* 409:    */             } else {
/* 410:489 */               mapaProducto.remove(idProducto);
/* 411:    */             }
/* 412:    */           }
/* 413:    */           catch (Exception e)
/* 414:    */           {
/* 415:493 */             e.printStackTrace();
/* 416:    */           }
/* 417:    */         }
/* 418:496 */         for (Object[] object : lista)
/* 419:    */         {
/* 420:497 */           BigDecimal precio = (BigDecimal)mapaProducto.get(object[30]);
/* 421:498 */           if (precio != null) {
/* 422:499 */             object[28] = precio;
/* 423:    */           }
/* 424:    */         }
/* 425:    */       }
/* 426:    */     }
/* 427:504 */     return lista;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void esEditable(DespachoCliente despachoCliente)
/* 431:    */     throws ExcepcionAS2Financiero, ExcepcionAS2Ventas
/* 432:    */   {
/* 433:510 */     if (despachoCliente.getEstado() == Estado.ANULADO) {
/* 434:511 */       throw new ExcepcionAS2Ventas("msg_error_editar");
/* 435:    */     }
/* 436:514 */     this.servicioPeriodo.buscarPorFecha(despachoCliente.getFecha(), despachoCliente.getIdOrganizacion(), despachoCliente
/* 437:515 */       .getDocumento().getDocumentoBase());
/* 438:    */     
/* 439:    */ 
/* 440:518 */     FacturaCliente facturaClienteExistente = this.servicioFacturaCliente.buscarPorDespachoCliente(Integer.valueOf(despachoCliente.getId()));
/* 441:519 */     if (facturaClienteExistente != null) {
/* 442:520 */       throw new ExcepcionAS2Ventas("msg_info_ya_existe_factura_despacho", facturaClienteExistente.getNumero());
/* 443:    */     }
/* 444:    */   }
/* 445:    */   
/* 446:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 447:    */   public void anular(DespachoCliente despachoCliente, Date fechaAnulacion)
/* 448:    */     throws ExcepcionAS2Financiero, ExcepcionAS2Ventas, ExcepcionAS2Inventario, AS2Exception
/* 449:    */   {
/* 450:    */     try
/* 451:    */     {
/* 452:537 */       DespachoCliente despachoTmp = buscarPorId(Integer.valueOf(despachoCliente.getIdDespachoCliente()));
/* 453:539 */       if (despachoTmp.getEstado() == Estado.ANULADO) {
/* 454:540 */         throw new ExcepcionAS2Ventas("msg_error_anular");
/* 455:    */       }
/* 456:542 */       if (despachoTmp.getEstado() == Estado.APROBADO) {
/* 457:543 */         throw new ExcepcionAS2Ventas("msg_error_anular");
/* 458:    */       }
/* 459:545 */       if ((despachoTmp.getEstado() == Estado.CONTABILIZADO) && (despachoCliente.getAsiento() == null)) {
/* 460:546 */         throw new ExcepcionAS2Ventas("msg_error_anular");
/* 461:    */       }
/* 462:549 */       if (despachoTmp.isIndicadorGeneradoPrefactura()) {
/* 463:550 */         throw new ExcepcionAS2Ventas("msg_error_anular");
/* 464:    */       }
/* 465:554 */       if (this.despachoClienteDao.existeDevolucion(despachoCliente)) {
/* 466:555 */         throw new ExcepcionAS2Ventas("msg_error_devolucion_existe");
/* 467:    */       }
/* 468:558 */       this.servicioPeriodo.buscarPorFecha(despachoCliente.getFecha(), despachoCliente.getIdOrganizacion(), despachoTmp
/* 469:559 */         .getDocumento().getDocumentoBase());
/* 470:    */       
/* 471:561 */       despachoCliente.setEstado(Estado.ANULADO);
/* 472:562 */       this.despachoClienteDao.guardar(despachoCliente);
/* 473:    */       
/* 474:564 */       this.servicioVerificadorVentas.actualizarCantidadPorDespachar(despachoCliente, true);
/* 475:567 */       if (despachoCliente.getPedidoCliente() != null)
/* 476:    */       {
/* 477:568 */         this.servicioPedidoCliente.cierreAutomatico(despachoCliente.getPedidoCliente());
/* 478:    */         
/* 479:570 */         this.despachoClienteDao.eliminarPedido(Integer.valueOf(despachoCliente.getId()));
/* 480:    */       }
/* 481:574 */       FacturaCliente facturaClienteExistente = this.servicioFacturaCliente.buscarPorDespachoCliente(Integer.valueOf(despachoCliente.getId()));
/* 482:575 */       if (facturaClienteExistente != null)
/* 483:    */       {
/* 484:576 */         FacturaCliente fc = this.servicioFacturaCliente.cargarDetalle(facturaClienteExistente.getId());
/* 485:577 */         fc.setDespachoCliente(null);
/* 486:578 */         for (DetalleFacturaCliente dfc : fc.getListaDetalleFacturaCliente())
/* 487:    */         {
/* 488:579 */           if ((dfc.getDetallePedidoCliente() == null) && (dfc.getDetalleDespachoCliente() != null)) {
/* 489:580 */             dfc.setDetallePedidoCliente(dfc.getDetalleDespachoCliente().getDetallePedidoCliente());
/* 490:    */           }
/* 491:582 */           dfc.setDetalleDespachoCliente(null);
/* 492:583 */           this.detalleFacturaClienteDao.guardar(dfc);
/* 493:    */         }
/* 494:585 */         this.facturaClienteDao.guardar(fc);
/* 495:    */       }
/* 496:589 */       this.servicioVerificadorInventario.actualizarSaldoProducto(despachoCliente, true, fechaAnulacion);
/* 497:591 */       if (ParametrosSistema.isRegistroReversoAnulacionInventario(despachoCliente.getIdOrganizacion()).booleanValue()) {
/* 498:593 */         this.servicioInventarioProducto.generarMovimientoInventarioInverso(despachoCliente, fechaAnulacion);
/* 499:    */       } else {
/* 500:596 */         this.servicioInventarioProducto.eliminaInventarioProductoPorIdDespachoCliente(Integer.valueOf(despachoCliente.getId()));
/* 501:    */       }
/* 502:600 */       if (despachoCliente.getAsiento() != null)
/* 503:    */       {
/* 504:601 */         despachoCliente.getAsiento().setIndicadorAutomatico(false);
/* 505:602 */         this.servicioAsiento.anular(despachoCliente.getAsiento());
/* 506:    */       }
/* 507:    */     }
/* 508:    */     catch (ExcepcionAS2Financiero e)
/* 509:    */     {
/* 510:606 */       this.context.setRollbackOnly();
/* 511:607 */       throw e;
/* 512:    */     }
/* 513:    */     catch (ExcepcionAS2Ventas e)
/* 514:    */     {
/* 515:609 */       this.context.setRollbackOnly();
/* 516:610 */       throw e;
/* 517:    */     }
/* 518:    */     catch (ExcepcionAS2Inventario e)
/* 519:    */     {
/* 520:612 */       this.context.setRollbackOnly();
/* 521:613 */       throw e;
/* 522:    */     }
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void actualizarEstado(Integer idDespachoCliente, Estado estado)
/* 526:    */   {
/* 527:619 */     this.despachoClienteDao.actualizarEstado(idDespachoCliente, estado);
/* 528:    */   }
/* 529:    */   
/* 530:    */   public int contarPorCriterio(Map<String, String> filters)
/* 531:    */   {
/* 532:629 */     return this.despachoClienteDao.contarPorCriterio(filters);
/* 533:    */   }
/* 534:    */   
/* 535:    */   public DespachoCliente cargarDetalleAFacturar(Integer idDespachoCliente)
/* 536:    */   {
/* 537:639 */     return this.despachoClienteDao.cargarDetalleAFacturar(idDespachoCliente);
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void actualizarCantidadPorFacturar(List<Integer> listaIdDetalleDespachoCliente)
/* 541:    */   {
/* 542:649 */     this.despachoClienteDao.actualizarCantidadPorFacturar(listaIdDetalleDespachoCliente);
/* 543:    */   }
/* 544:    */   
/* 545:    */   public DespachoCliente buscarPorFacturaCliente(Integer idFacturaCliente)
/* 546:    */   {
/* 547:660 */     return this.despachoClienteDao.buscarPorFacturaCliente(idFacturaCliente);
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void cargarDetalleDespachoArchivoTexto(TipoOrganizacion tipoOrganizacion, DespachoCliente despachoCliente, InputStream inputStream, Bodega bodega)
/* 551:    */     throws ExcepcionAS2
/* 552:    */   {
/* 553:    */     try
/* 554:    */     {
/* 555:674 */       cargarDetalleTransferenciaArchivoTextoPadilla(despachoCliente, inputStream, bodega);
/* 556:    */     }
/* 557:    */     catch (IllegalArgumentException e)
/* 558:    */     {
/* 559:676 */       this.context.setRollbackOnly();
/* 560:677 */       e.printStackTrace();
/* 561:678 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", e.getMessage());
/* 562:    */     }
/* 563:    */     catch (ExcepcionAS2 e)
/* 564:    */     {
/* 565:680 */       this.context.setRollbackOnly();
/* 566:681 */       e.printStackTrace();
/* 567:682 */       despachoCliente.getListaDetalleDespachoCliente().clear();
/* 568:683 */       throw e;
/* 569:    */     }
/* 570:    */     catch (IOException e)
/* 571:    */     {
/* 572:685 */       this.context.setRollbackOnly();
/* 573:686 */       e.printStackTrace();
/* 574:687 */       throw new ExcepcionAS2(e);
/* 575:    */     }
/* 576:    */     catch (Exception e)
/* 577:    */     {
/* 578:689 */       this.context.setRollbackOnly();
/* 579:690 */       e.printStackTrace();
/* 580:691 */       throw new ExcepcionAS2(e);
/* 581:    */     }
/* 582:    */   }
/* 583:    */   
/* 584:    */   private void cargarDetalleTransferenciaArchivoTextoPadilla(DespachoCliente despachoCliente, InputStream inputStream, Bodega bodega)
/* 585:    */     throws IllegalArgumentException, ExcepcionAS2, IOException
/* 586:    */   {
/* 587:705 */     DetalleDespachoCliente detalleDespachoCliente = null;
/* 588:706 */     List<String> datosArchivo = FuncionesUtiles.leerArchivoTexto(inputStream);
/* 589:707 */     int cont = 0;
/* 590:708 */     Lote lote = null;
/* 591:709 */     Map<String, Lote> mapaLote = new HashMap();
/* 592:710 */     Map<String, String> mapaDatosArchivo = new HashMap();
/* 593:711 */     Map<String, String> mapaLoteAuxiliar = new HashMap();
/* 594:713 */     for (DetalleDespachoCliente detalleDespachoClienteTodos : despachoCliente.getListaDetalleDespachoCliente()) {
/* 595:714 */       if (!detalleDespachoClienteTodos.isEliminado()) {
/* 596:715 */         mapaLoteAuxiliar.put(detalleDespachoClienteTodos.getInventarioProducto().getLote().getCodigo(), detalleDespachoClienteTodos
/* 597:716 */           .getInventarioProducto().getLote().getCodigo());
/* 598:    */       }
/* 599:    */     }
/* 600:720 */     for (String dato : datosArchivo)
/* 601:    */     {
/* 602:721 */       if (cont > 0)
/* 603:    */       {
/* 604:723 */         String datoTruncado = dato.substring(1, dato.length());
/* 605:724 */         mapaDatosArchivo.put(String.valueOf(datoTruncado), String.valueOf(datoTruncado));
/* 606:    */       }
/* 607:726 */       cont++;
/* 608:    */     }
/* 609:729 */     for (String datoNumero : mapaDatosArchivo.values())
/* 610:    */     {
/* 611:731 */       if (mapaLote.containsKey(datoNumero))
/* 612:    */       {
/* 613:732 */         lote = (Lote)mapaLote.get(datoNumero);
/* 614:    */       }
/* 615:    */       else
/* 616:    */       {
/* 617:734 */         lote = this.servicioLote.buscarPorCodigo(datoNumero);
/* 618:735 */         mapaLote.put(datoNumero, lote);
/* 619:    */       }
/* 620:737 */       if (lote != null)
/* 621:    */       {
/* 622:738 */         Producto producto = this.servicioProducto.buscarPorCodigo(lote.getProducto().getCodigo(), despachoCliente.getIdOrganizacion(), null);
/* 623:740 */         if (producto != null)
/* 624:    */         {
/* 625:741 */           Bodega bodegaDato = bodega != null ? bodega : producto.getBodegaVenta();
/* 626:742 */           detalleDespachoCliente = new DetalleDespachoCliente();
/* 627:743 */           detalleDespachoCliente.setIdSucursal(despachoCliente.getSucursal().getIdSucursal());
/* 628:744 */           detalleDespachoCliente.setIdOrganizacion(despachoCliente.getIdOrganizacion());
/* 629:745 */           BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), bodegaDato.getIdBodega(), lote.getIdLote(), despachoCliente
/* 630:746 */             .getFecha());
/* 631:747 */           detalleDespachoCliente.setSaldo(saldo);
/* 632:748 */           detalleDespachoCliente.setCantidad(saldo.setScale(4, RoundingMode.HALF_UP));
/* 633:749 */           detalleDespachoCliente.setDescripcion("");
/* 634:750 */           detalleDespachoCliente.setProducto(producto);
/* 635:751 */           detalleDespachoCliente.setInventarioProducto(new InventarioProducto());
/* 636:752 */           detalleDespachoCliente.getInventarioProducto().setLote(lote);
/* 637:753 */           detalleDespachoCliente.setDespachoCliente(despachoCliente);
/* 638:754 */           detalleDespachoCliente.setUnidadVenta(producto.getUnidadVenta());
/* 639:755 */           detalleDespachoCliente.setBodega(bodegaDato);
/* 640:756 */           if (!mapaLoteAuxiliar.containsKey(lote.getCodigo())) {
/* 641:757 */             despachoCliente.getListaDetalleDespachoCliente().add(detalleDespachoCliente);
/* 642:    */           }
/* 643:    */         }
/* 644:    */       }
/* 645:    */     }
/* 646:    */   }
/* 647:    */   
/* 648:    */   public List<DespachoCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 649:    */   {
/* 650:772 */     return this.despachoClienteDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 651:    */   }
/* 652:    */   
/* 653:    */   public void actualizarAtributoEntidad(DespachoCliente despachoCliente, HashMap<String, Object> campos)
/* 654:    */   {
/* 655:777 */     this.despachoClienteDao.actualizarAtributoEntidad(despachoCliente, campos);
/* 656:    */   }
/* 657:    */   
/* 658:    */   public List<DespachoCliente> buscarDespachosNoFacturadosPorCliente(Integer idEmpresa)
/* 659:    */   {
/* 660:782 */     return this.despachoClienteDao.buscarDespachosNoFacturadosPorCliente(idEmpresa);
/* 661:    */   }
/* 662:    */   
/* 663:    */   public List<DespachoCliente> autocompletarDespachosNoFacturadosPorCliente(Integer idEmpresa, String consulta, int idSubempresa)
/* 664:    */   {
/* 665:787 */     return this.despachoClienteDao.buscarDespachosNoFacturadosPorCliente(idEmpresa, consulta, idSubempresa);
/* 666:    */   }
/* 667:    */   
/* 668:    */   public List<DespachoCliente> obtenerDespachosPorFecha(Date fechaDespacho, int idOrganizacion, Empresa empresa)
/* 669:    */   {
/* 670:793 */     return this.despachoClienteDao.obtenerDespachosPorFecha(fechaDespacho, idOrganizacion, empresa);
/* 671:    */   }
/* 672:    */   
/* 673:    */   public List<DetalleDespachoCliente> cargarDetalleDespachoClientePorDespacho(DespachoCliente despachoCliente)
/* 674:    */   {
/* 675:799 */     return this.despachoClienteDao.cargarDetalleDespachoClientePorDespacho(despachoCliente);
/* 676:    */   }
/* 677:    */   
/* 678:    */   public void actualizarDespachoCliente(List<DetalleDespachoCliente> listaDetalleDespachoCliente, DespachoCliente despacho)
/* 679:    */   {
/* 680:804 */     for (DetalleDespachoCliente detalleDespachoCliente : listaDetalleDespachoCliente)
/* 681:    */     {
/* 682:805 */       InventarioProducto ip = detalleDespachoCliente.getInventarioProducto();
/* 683:806 */       ip.setNumeroDocumento(despacho.getNumero());
/* 684:807 */       this.inventarioProductoDao.guardar(ip);
/* 685:    */     }
/* 686:809 */     this.detalleDespachoClienteDao.actualizarDespachoCliente(listaDetalleDespachoCliente, despacho);
/* 687:    */   }
/* 688:    */   
/* 689:    */   public void contabilizar(List<DespachoCliente> despachoCliente)
/* 690:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 691:    */   {
/* 692:815 */     InterfazContableProceso interfazContableProceso = new InterfazContableProceso();
/* 693:816 */     interfazContableProceso.setListaDespachoCliente(despachoCliente);
/* 694:817 */     interfazContableProceso.setDocumentoBase(DocumentoBase.INTERFAZ_DESPACHOS);
/* 695:818 */     interfazContableProceso.setIdOrganizacion(((DespachoCliente)despachoCliente.get(0)).getIdOrganizacion());
/* 696:819 */     interfazContableProceso.setFechaHasta(((DespachoCliente)despachoCliente.get(0)).getFecha());
/* 697:820 */     interfazContableProceso.setContabilizacionAutomatica(true);
/* 698:821 */     interfazContableProceso.setIndicadorAgrupadoPorCuenta(false);
/* 699:822 */     interfazContableProceso.setSucursal(((DespachoCliente)despachoCliente.get(0)).getSucursal());
/* 700:824 */     if (((DespachoCliente)despachoCliente.get(0)).getAsiento() != null) {
/* 701:825 */       interfazContableProceso.setAsiento(((DespachoCliente)despachoCliente.get(0)).getAsiento());
/* 702:    */     }
/* 703:827 */     Asiento asiento = this.servicioInterfazContableProceso.generarAsiento(interfazContableProceso);
/* 704:    */     
/* 705:829 */     asiento.setFecha(((DespachoCliente)despachoCliente.get(0)).getFecha());
/* 706:830 */     asiento.setDocumentoOrigen(((DespachoCliente)despachoCliente.get(0)).getDocumento());
/* 707:    */     
/* 708:832 */     this.servicioAsiento.guardar(asiento);
/* 709:    */     
/* 710:834 */     ((DespachoCliente)despachoCliente.get(0)).setEstado(Estado.CONTABILIZADO);
/* 711:835 */     ((DespachoCliente)despachoCliente.get(0)).setFechaContabilizacion(((DespachoCliente)despachoCliente.get(0)).getFecha());
/* 712:836 */     ((DespachoCliente)despachoCliente.get(0)).setAsiento(asiento);
/* 713:    */   }
/* 714:    */   
/* 715:    */   public List<DespachoCliente> obtenerDespachos(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 716:    */   {
/* 717:840 */     return this.despachoClienteDao.obtenerDespachos(idOrganizacion, fechaDesde, fechaHasta);
/* 718:    */   }
/* 719:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioDespachoClienteImpl
 * JD-Core Version:    0.7.0.1
 */