/*   1:    */ package com.asinfo.as2.compras.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioNotaCreditoProveedor;
/*   6:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   7:    */ import com.asinfo.as2.dao.CuentaPorPagarDao;
/*   8:    */ import com.asinfo.as2.dao.DetalleFacturaProveedorDao;
/*   9:    */ import com.asinfo.as2.dao.DetalleRecepcionProveedorDao;
/*  10:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*  11:    */ import com.asinfo.as2.dao.GastoProductoFacturaProveedorDao;
/*  12:    */ import com.asinfo.as2.dao.ImpuestoProductoFacturaProveedorDao;
/*  13:    */ import com.asinfo.as2.dao.InventarioProductoDao;
/*  14:    */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*  15:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  16:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  17:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*  18:    */ import com.asinfo.as2.entities.Asiento;
/*  19:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  20:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  21:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoProveedor;
/*  22:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*  23:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*  24:    */ import com.asinfo.as2.entities.Documento;
/*  25:    */ import com.asinfo.as2.entities.Empresa;
/*  26:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  27:    */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*  28:    */ import com.asinfo.as2.entities.Impuesto;
/*  29:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*  30:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  31:    */ import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
/*  32:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  33:    */ import com.asinfo.as2.entities.Organizacion;
/*  34:    */ import com.asinfo.as2.entities.Producto;
/*  35:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  36:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  37:    */ import com.asinfo.as2.entities.Sucursal;
/*  38:    */ import com.asinfo.as2.entities.Unidad;
/*  39:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*  40:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  41:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  42:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  43:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  44:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  45:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  46:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  47:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  48:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  49:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*  50:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioLiquidacionAnticipoProveedor;
/*  51:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  52:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  53:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  54:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  55:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*  56:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  57:    */ import com.asinfo.as2.util.AppUtil;
/*  58:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  59:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  60:    */ import java.math.BigDecimal;
/*  61:    */ import java.math.RoundingMode;
/*  62:    */ import java.util.ArrayList;
/*  63:    */ import java.util.HashMap;
/*  64:    */ import java.util.Iterator;
/*  65:    */ import java.util.List;
/*  66:    */ import java.util.Map;
/*  67:    */ import javax.ejb.EJB;
/*  68:    */ import javax.ejb.SessionContext;
/*  69:    */ import javax.ejb.Stateless;
/*  70:    */ import javax.ejb.TransactionAttribute;
/*  71:    */ import javax.ejb.TransactionAttributeType;
/*  72:    */ import org.apache.log4j.Logger;
/*  73:    */ 
/*  74:    */ @Stateless
/*  75:    */ public class ServicioNotaCreditoProveedorImpl
/*  76:    */   extends AbstractServicioAS2
/*  77:    */   implements ServicioNotaCreditoProveedor
/*  78:    */ {
/*  79:    */   private static final long serialVersionUID = -8103622431880354902L;
/*  80:    */   @EJB
/*  81:    */   private transient ServicioPeriodo servicioPeriodo;
/*  82:    */   @EJB
/*  83:    */   private transient ServicioAsiento servicioAsiento;
/*  84:    */   @EJB
/*  85:    */   private transient ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  86:    */   @EJB
/*  87:    */   private transient ServicioDocumento servicioDocumento;
/*  88:    */   @EJB
/*  89:    */   private transient ServicioSecuencia servicioSecuencia;
/*  90:    */   @EJB
/*  91:    */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  92:    */   @EJB
/*  93:    */   private transient ServicioLiquidacionAnticipoProveedor servicioLiquidacionAnticipoProveedor;
/*  94:    */   @EJB
/*  95:    */   private transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  96:    */   @EJB
/*  97:    */   private transient FacturaProveedorSRIDao facturaProveedorSRIDao;
/*  98:    */   @EJB
/*  99:    */   private transient FacturaProveedorDao facturaProveedorDao;
/* 100:    */   @EJB
/* 101:    */   private transient CuentaPorPagarDao cuentaPorPagarDao;
/* 102:    */   @EJB
/* 103:    */   private transient InventarioProductoDao inventarioProductoDao;
/* 104:    */   @EJB
/* 105:    */   private transient ImpuestoProductoFacturaProveedorDao impuestoProductoFacturaProveedorDao;
/* 106:    */   @EJB
/* 107:    */   private transient GastoProductoFacturaProveedorDao gastoProductoFacturaProveedorDao;
/* 108:    */   @EJB
/* 109:    */   private transient DetalleFacturaProveedorDao detalleFacturaProveedorDao;
/* 110:    */   @EJB
/* 111:    */   private transient ServicioProducto servicioProducto;
/* 112:    */   @EJB
/* 113:    */   private transient ServicioInventarioProducto servicioInventarioProducto;
/* 114:    */   @EJB
/* 115:    */   private transient DetalleRecepcionProveedorDao detalleRecepcionProveedorDao;
/* 116:    */   @EJB
/* 117:    */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/* 118:    */   @EJB
/* 119:    */   private transient ServicioMovimientoInventario servicioMovimientoInventario;
/* 120:    */   @EJB
/* 121:    */   private transient ServicioRecepcionProveedor servicioRecepcionProveedor;
/* 122:    */   
/* 123:    */   public void cargarSecuencia(FacturaProveedor notaCreditoProveedor)
/* 124:    */     throws ExcepcionAS2
/* 125:    */   {
/* 126:144 */     String numero = this.servicioSecuencia.obtenerSecuencia(notaCreditoProveedor.getDocumento().getSecuencia(), notaCreditoProveedor.getFecha());
/* 127:145 */     notaCreditoProveedor.setNumero(numero);
/* 128:    */   }
/* 129:    */   
/* 130:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 131:    */   public void guardar(FacturaProveedor notaCreditoProveedor)
/* 132:    */     throws ExcepcionAS2
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:160 */       this.servicioVerificadorInventario.cantidadDetalle(notaCreditoProveedor.getListaDetalleFacturaProveedor());
/* 137:    */       
/* 138:162 */       this.servicioVerificadorInventario.verificarTotalDetalle(notaCreditoProveedor.getListaDetalleFacturaProveedor());
/* 139:    */       
/* 140:164 */       validar(notaCreditoProveedor);
/* 141:165 */       this.servicioFacturaProveedorSRI.actualizarFacturaProveedorSRI(notaCreditoProveedor);
/* 142:168 */       if ((notaCreditoProveedor.getNumero() == null) || (notaCreditoProveedor.getNumero().isEmpty())) {
/* 143:169 */         cargarSecuencia(notaCreditoProveedor);
/* 144:    */       }
/* 145:175 */       actualizarInventarioProducto(notaCreditoProveedor, notaCreditoProveedor
/* 146:176 */         .getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_PROVEEDOR);
/* 147:180 */       if ((DocumentoBase.DEVOLUCION_PROVEEDOR.equals(notaCreditoProveedor.getDocumento().getDocumentoBase())) && (!notaCreditoProveedor.getDocumento().isIndicadorDocumentoTributario())) {
/* 148:182 */         this.facturaProveedorDao.guardar(notaCreditoProveedor);
/* 149:    */       }
/* 150:184 */       for (DetalleFacturaProveedor dfp : notaCreditoProveedor.getListaDetalleFacturaProveedor())
/* 151:    */       {
/* 152:186 */         if ((dfp.getPrecioLinea().compareTo(BigDecimal.ZERO) != 0) || (!notaCreditoProveedor.getDocumento().isIndicadorDocumentoTributario()))
/* 153:    */         {
/* 154:189 */           for (ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor : dfp.getListaImpuestoProductoFacturaProveedor()) {
/* 155:190 */             this.impuestoProductoFacturaProveedorDao.guardar(impuestoProductoFacturaProveedor);
/* 156:    */           }
/* 157:193 */           for (GastoProductoFacturaProveedor gasto : dfp.getListaGastoProductoFacturaProveedor())
/* 158:    */           {
/* 159:194 */             if (!dfp.getProducto().isTraIndicadorServicio()) {
/* 160:195 */               gasto.setEliminado(true);
/* 161:    */             }
/* 162:197 */             this.gastoProductoFacturaProveedorDao.guardar(gasto);
/* 163:    */           }
/* 164:200 */           this.detalleFacturaProveedorDao.guardar(dfp);
/* 165:202 */           if (dfp.getInventarioProducto() != null) {
/* 166:203 */             this.inventarioProductoDao.guardar(dfp.getInventarioProducto());
/* 167:    */           }
/* 168:    */         }
/* 169:209 */         if ((!dfp.isEliminado()) && (dfp.getProducto().isIndicadorManejaUnidadInformativa()) && 
/* 170:210 */           (notaCreditoProveedor.getRecepcionProveedor() != null)) {
/* 171:212 */           this.servicioMovimientoInventario.generarTransformacionUnidadInformativa(null, null, dfp);
/* 172:    */         }
/* 173:    */       }
/* 174:217 */       FacturaProveedorSRI facturaProveedorSRI = notaCreditoProveedor.getFacturaProveedorSRI();
/* 175:218 */       if (facturaProveedorSRI != null) {
/* 176:219 */         this.facturaProveedorSRIDao.guardar(facturaProveedorSRI);
/* 177:    */       }
/* 178:223 */       this.facturaProveedorDao.guardar(notaCreditoProveedor);
/* 179:    */       
/* 180:225 */       this.servicioSecuencia.actualizarSecuencia(notaCreditoProveedor.getDocumento().getSecuencia(), notaCreditoProveedor.getNumero());
/* 181:    */       String numeroLiquidacionCompra;
/* 182:226 */       if ((facturaProveedorSRI != null) && 
/* 183:227 */         (facturaProveedorSRI.getTipoComprobanteSRI() != null) && (facturaProveedorSRI.getSecuenciaLiquidacionCompra() != null) && 
/* 184:228 */         (facturaProveedorSRI.isIndicadorLiquidacionCompra()))
/* 185:    */       {
/* 186:229 */         numeroLiquidacionCompra = facturaProveedorSRI.getNumero();
/* 187:    */         
/* 188:231 */         this.servicioSecuencia.actualizarSecuencia(facturaProveedorSRI.getSecuenciaLiquidacionCompra(), numeroLiquidacionCompra);
/* 189:    */       }
/* 190:235 */       actualizarCantidadPorDevolver(notaCreditoProveedor);
/* 191:    */       
/* 192:    */ 
/* 193:238 */       this.servicioVerificadorInventario.actualizarSaldoProducto(notaCreditoProveedor, false);
/* 194:240 */       if ((notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_CREDITO_PROVEEDOR) || 
/* 195:241 */         (notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_PROVEEDOR))
/* 196:    */       {
/* 197:242 */         if (notaCreditoProveedor.getRecepcionProveedor() == null)
/* 198:    */         {
/* 199:243 */           generarAnticipo(notaCreditoProveedor);
/* 200:244 */           liquidaAnticipo(notaCreditoProveedor);
/* 201:    */         }
/* 202:    */         else
/* 203:    */         {
/* 204:246 */           this.servicioRecepcionProveedor.contabilizar(null, notaCreditoProveedor);
/* 205:    */         }
/* 206:    */       }
/* 207:    */       else
/* 208:    */       {
/* 209:249 */         notaCreditoProveedor.setNumeroCuotas(1);
/* 210:250 */         this.servicioFacturaProveedor.generarCuentaPorPagar(notaCreditoProveedor);
/* 211:252 */         for (CuentaPorPagar cuentaPorPagar : notaCreditoProveedor.getListaCuentaPorPagar())
/* 212:    */         {
/* 213:253 */           cuentaPorPagar.setSaldo(cuentaPorPagar.getValor());
/* 214:254 */           this.cuentaPorPagarDao.guardar(cuentaPorPagar);
/* 215:    */         }
/* 216:    */       }
/* 217:257 */       if (notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_DEBITO_PROVEEDOR) {
/* 218:258 */         this.servicioFacturaProveedor.contabilizar(notaCreditoProveedor);
/* 219:    */       }
/* 220:    */     }
/* 221:    */     catch (ExcepcionAS2Compras e)
/* 222:    */     {
/* 223:262 */       this.context.setRollbackOnly();
/* 224:263 */       e.printStackTrace();
/* 225:264 */       throw e;
/* 226:    */     }
/* 227:    */     catch (ExcepcionAS2Financiero e)
/* 228:    */     {
/* 229:266 */       this.context.setRollbackOnly();
/* 230:267 */       e.printStackTrace();
/* 231:268 */       throw e;
/* 232:    */     }
/* 233:    */     catch (ExcepcionAS2 e)
/* 234:    */     {
/* 235:270 */       this.context.setRollbackOnly();
/* 236:271 */       e.printStackTrace();
/* 237:272 */       throw e;
/* 238:    */     }
/* 239:    */     catch (Exception e)
/* 240:    */     {
/* 241:274 */       this.context.setRollbackOnly();
/* 242:275 */       e.printStackTrace();
/* 243:276 */       LOG.error(e);
/* 244:277 */       throw new ExcepcionAS2(e);
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   private void liquidaAnticipo(FacturaProveedor notaCreditoProveedor)
/* 249:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 250:    */   {
/* 251:291 */     LiquidacionAnticipoProveedor liquidacionAnticipoProveedor = new LiquidacionAnticipoProveedor();
/* 252:292 */     liquidacionAnticipoProveedor.setIdOrganizacion(notaCreditoProveedor.getIdOrganizacion());
/* 253:293 */     liquidacionAnticipoProveedor.setFecha(notaCreditoProveedor.getFecha());
/* 254:294 */     liquidacionAnticipoProveedor.setEstado(Estado.ELABORADO);
/* 255:295 */     liquidacionAnticipoProveedor.setAnticipoProveedor(notaCreditoProveedor.getAnticipoProveedor());
/* 256:    */     
/* 257:297 */     liquidacionAnticipoProveedor.setDocumento(notaCreditoProveedor.getDocumento());
/* 258:298 */     liquidacionAnticipoProveedor.setNumero(notaCreditoProveedor.getNumero());
/* 259:    */     
/* 260:300 */     liquidacionAnticipoProveedor = this.servicioLiquidacionAnticipoProveedor.cargarFacturasPendientes(liquidacionAnticipoProveedor, notaCreditoProveedor
/* 261:301 */       .getFacturaProveedorPadre().getIdFacturaProveedor());
/* 262:302 */     BigDecimal valorLiquidacion = BigDecimal.ZERO;
/* 263:303 */     for (DetalleLiquidacionAnticipoProveedor detalleLiquidacionAnticipoProveedor : liquidacionAnticipoProveedor
/* 264:304 */       .getListaDetalleLiquidacionAnticipoProveedor()) {
/* 265:305 */       valorLiquidacion = valorLiquidacion.add(detalleLiquidacionAnticipoProveedor.getValor());
/* 266:    */     }
/* 267:307 */     if (valorLiquidacion.compareTo(BigDecimal.ZERO) != 0) {
/* 268:308 */       this.servicioLiquidacionAnticipoProveedor.guardar(liquidacionAnticipoProveedor);
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   public FacturaProveedor buscarPorId(Integer id)
/* 273:    */     throws ExcepcionAS2
/* 274:    */   {
/* 275:315 */     return this.servicioFacturaProveedor.buscarPorId(id);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void totalizar(FacturaProveedor notaCreditoProveedor)
/* 279:    */     throws ExcepcionAS2Compras
/* 280:    */   {
/* 281:320 */     this.servicioFacturaProveedor.totalizar(notaCreditoProveedor);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void totalizarImpuesto(FacturaProveedor notaCreditoProveedor)
/* 285:    */     throws ExcepcionAS2Compras
/* 286:    */   {
/* 287:325 */     this.servicioFacturaProveedor.totalizarImpuesto(notaCreditoProveedor);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public List<FacturaProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 291:    */   {
/* 292:331 */     return this.servicioFacturaProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void actualizarEstado(Integer idNotaCreditoProveedor, Estado estado)
/* 296:    */   {
/* 297:343 */     this.servicioFacturaProveedor.actualizarEstado(idNotaCreditoProveedor, estado);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void anular(FacturaProveedor notaCreditoProveedor)
/* 301:    */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero, ExcepcionAS2
/* 302:    */   {
/* 303:    */     try
/* 304:    */     {
/* 305:350 */       notaCreditoProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(notaCreditoProveedor.getId()));
/* 306:    */       
/* 307:352 */       esEditable(notaCreditoProveedor);
/* 308:    */       
/* 309:354 */       actualizarEstado(Integer.valueOf(notaCreditoProveedor.getId()), Estado.ANULADO);
/* 310:356 */       if (notaCreditoProveedor.getFacturaProveedorSRI() != null) {
/* 311:359 */         this.facturaProveedorSRIDao.eliminarFacturaProveedorSRI(Integer.valueOf(notaCreditoProveedor.getFacturaProveedorSRI().getId()));
/* 312:    */       }
/* 313:    */       List<LiquidacionAnticipoProveedor> listaLiquidacionAnticipoProveedor;
/* 314:362 */       if ((notaCreditoProveedor.getAnticipoProveedor() != null) && (
/* 315:363 */         (notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_CREDITO_PROVEEDOR) || 
/* 316:364 */         (notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_PROVEEDOR)))
/* 317:    */       {
/* 318:366 */         notaCreditoProveedor.setAnticipoProveedor(this.servicioAnticipoProveedor.cargarDetalle(notaCreditoProveedor.getAnticipoProveedor()
/* 319:367 */           .getIdAnticipoProveedor()));
/* 320:    */         
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:373 */         listaLiquidacionAnticipoProveedor = this.servicioLiquidacionAnticipoProveedor.getLiquidacionAnticipoProveedorPorAnticipoProveedor(notaCreditoProveedor.getAnticipoProveedor().getId());
/* 326:375 */         for (LiquidacionAnticipoProveedor lap : listaLiquidacionAnticipoProveedor) {
/* 327:376 */           if ((lap != null) && (lap.getEstado() != Estado.ANULADO))
/* 328:    */           {
/* 329:377 */             lap = this.servicioLiquidacionAnticipoProveedor.cargarDetalle(lap.getId());
/* 330:378 */             this.servicioLiquidacionAnticipoProveedor.anular(lap);
/* 331:    */           }
/* 332:    */         }
/* 333:382 */         notaCreditoProveedor.getAnticipoProveedor().setNotaCreditoProveedor(null);
/* 334:    */         
/* 335:384 */         this.servicioAnticipoProveedor.detach(notaCreditoProveedor.getAnticipoProveedor());
/* 336:389 */         if (notaCreditoProveedor.getAnticipoProveedor().getEstado() != Estado.ANULADO) {
/* 337:390 */           this.servicioAnticipoProveedor.anularAnticipoProveedor(notaCreditoProveedor.getAnticipoProveedor());
/* 338:    */         }
/* 339:393 */         notaCreditoProveedor.getAnticipoProveedor().setNotaCreditoProveedor(notaCreditoProveedor);
/* 340:    */       }
/* 341:397 */       if (notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_PROVEEDOR)
/* 342:    */       {
/* 343:399 */         for (DetalleFacturaProveedor detalleFacturaProveedor : this.facturaProveedorDao.cargarDetalleFactura(notaCreditoProveedor
/* 344:400 */           .getIdFacturaProveedor())) {
/* 345:402 */           if (detalleFacturaProveedor.getDetalleRecepcionProveedorDevolucion() != null)
/* 346:    */           {
/* 347:405 */             BigDecimal cantidadDevuelta = detalleFacturaProveedor.getDetalleRecepcionProveedorDevolucion().getCantidadDevuelta().subtract(detalleFacturaProveedor.getCantidad());
/* 348:    */             
/* 349:407 */             this.detalleRecepcionProveedorDao.actualizarCantidadDevuelta(detalleFacturaProveedor.getDetalleRecepcionProveedorDevolucion(), cantidadDevuelta);
/* 350:    */           }
/* 351:    */         }
/* 352:413 */         this.servicioVerificadorInventario.actualizarSaldoProducto(notaCreditoProveedor, true);
/* 353:    */       }
/* 354:418 */       anularTransformacionDevolucionProveedor(notaCreditoProveedor, 
/* 355:419 */         ParametrosSistema.isRegistroReversoAnulacionInventario(notaCreditoProveedor.getIdOrganizacion()).booleanValue());
/* 356:421 */       if (ParametrosSistema.isRegistroReversoAnulacionInventario(notaCreditoProveedor.getIdOrganizacion()).booleanValue()) {
/* 357:424 */         this.servicioInventarioProducto.generarMovimientoInventarioInverso(notaCreditoProveedor, notaCreditoProveedor.getFecha());
/* 358:    */       } else {
/* 359:427 */         this.servicioInventarioProducto.eliminaInventarioProductoPorIdDevolucionProveedor(Integer.valueOf(notaCreditoProveedor.getId()));
/* 360:    */       }
/* 361:431 */       if ((notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_DEBITO_PROVEEDOR) || (
/* 362:432 */         (DocumentoBase.DEVOLUCION_PROVEEDOR.equals(notaCreditoProveedor.getDocumento().getDocumentoBase())) && 
/* 363:433 */         (!notaCreditoProveedor.getDocumento().isIndicadorDocumentoTributario())))
/* 364:    */       {
/* 365:434 */         notaCreditoProveedor.getAsiento().setIndicadorAutomatico(false);
/* 366:435 */         this.servicioAsiento.anular(notaCreditoProveedor.getAsiento());
/* 367:    */       }
/* 368:    */     }
/* 369:    */     catch (ExcepcionAS2Compras e)
/* 370:    */     {
/* 371:439 */       this.context.setRollbackOnly();
/* 372:440 */       throw e;
/* 373:    */     }
/* 374:    */     catch (ExcepcionAS2Financiero e)
/* 375:    */     {
/* 376:442 */       this.context.setRollbackOnly();
/* 377:443 */       throw e;
/* 378:    */     }
/* 379:    */     catch (Exception e)
/* 380:    */     {
/* 381:445 */       this.context.setRollbackOnly();
/* 382:446 */       LOG.error(e);
/* 383:447 */       throw new ExcepcionAS2(e);
/* 384:    */     }
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void esEditable(FacturaProveedor notaCreditoProveedor)
/* 388:    */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero
/* 389:    */   {
/* 390:455 */     this.servicioPeriodo.buscarPorFecha(notaCreditoProveedor.getFecha(), notaCreditoProveedor.getIdOrganizacion(), notaCreditoProveedor.getDocumento().getDocumentoBase());
/* 391:457 */     if (notaCreditoProveedor.getEstado() == Estado.ANULADO) {
/* 392:459 */       throw new ExcepcionAS2Compras("msg_error_editar");
/* 393:    */     }
/* 394:461 */     if ((notaCreditoProveedor.getAsiento() != null) && (notaCreditoProveedor.getAsiento().getEstado() == Estado.REVISADO)) {
/* 395:463 */       throw new ExcepcionAS2Compras("msg_error_editar");
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   public int contarPorCriterio(Map<String, String> filters)
/* 400:    */   {
/* 401:470 */     return this.servicioFacturaProveedor.contarPorCriterio(filters);
/* 402:    */   }
/* 403:    */   
/* 404:    */   public AnticipoProveedor generarAnticipo(FacturaProveedor notaCreditoProveedor)
/* 405:    */     throws ExcepcionAS2
/* 406:    */   {
/* 407:    */     AnticipoProveedor anticipoProveedor;
/* 408:477 */     if (notaCreditoProveedor.getAnticipoProveedor() == null)
/* 409:    */     {
/* 410:478 */       AnticipoProveedor anticipoProveedor = new AnticipoProveedor();
/* 411:479 */       anticipoProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 412:480 */       anticipoProveedor.setSucursal(AppUtil.getSucursal());
/* 413:481 */       anticipoProveedor.setIndicadorContabilizar(Boolean.valueOf(notaCreditoProveedor.getDocumento().isIndicadorContabilizar()));
/* 414:482 */       anticipoProveedor.setBeneficiario("N/A");
/* 415:483 */       anticipoProveedor.setNumero("");
/* 416:    */       
/* 417:485 */       Documento documento = (Documento)this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.ANTICIPO_PROVEEDOR).get(0);
/* 418:486 */       anticipoProveedor.setDocumento(documento);
/* 419:    */       
/* 420:488 */       anticipoProveedor.setNotaCreditoProveedor(notaCreditoProveedor);
/* 421:    */     }
/* 422:    */     else
/* 423:    */     {
/* 424:490 */       anticipoProveedor = notaCreditoProveedor.getAnticipoProveedor();
/* 425:    */     }
/* 426:492 */     anticipoProveedor.setEstado(Estado.CONTABILIZADO);
/* 427:493 */     anticipoProveedor.setFecha(notaCreditoProveedor.getFecha());
/* 428:494 */     anticipoProveedor.setFechaContabilizacion(notaCreditoProveedor.getFecha());
/* 429:495 */     anticipoProveedor.setValor(notaCreditoProveedor.getTotalFactura());
/* 430:496 */     anticipoProveedor.setSaldo(anticipoProveedor.getValor());
/* 431:497 */     anticipoProveedor.setEmpresa(notaCreditoProveedor.getEmpresa());
/* 432:498 */     anticipoProveedor.setAsiento(notaCreditoProveedor.getAsiento());
/* 433:499 */     this.servicioAnticipoProveedor.cargarSecuencia(anticipoProveedor);
/* 434:500 */     this.servicioAnticipoProveedor.guardar(anticipoProveedor);
/* 435:    */     
/* 436:502 */     notaCreditoProveedor.setAnticipoProveedor(anticipoProveedor);
/* 437:503 */     notaCreditoProveedor.setAsiento(anticipoProveedor.getAsiento());
/* 438:504 */     return anticipoProveedor;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void actualizarAtributoEntidad(FacturaProveedor notaCreditoProveedor, HashMap<String, Object> campos)
/* 442:    */   {
/* 443:509 */     this.servicioFacturaProveedor.actualizarAtributoEntidad(notaCreditoProveedor, campos);
/* 444:    */   }
/* 445:    */   
/* 446:    */   private void validar(FacturaProveedor notaCreditoProveedor)
/* 447:    */     throws ExcepcionAS2Financiero, ExcepcionAS2Compras
/* 448:    */   {
/* 449:515 */     this.servicioPeriodo.buscarPorFecha(notaCreditoProveedor.getFecha(), notaCreditoProveedor.getIdOrganizacion(), notaCreditoProveedor
/* 450:516 */       .getDocumento().getDocumentoBase());
/* 451:518 */     if ((notaCreditoProveedor.getDocumento().isIndicadorDocumentoTributario()) && 
/* 452:519 */       (!FuncionesUtiles.compararFechaAnteriorOIgual(notaCreditoProveedor.getFacturaProveedorPadre().getFecha(), notaCreditoProveedor.getFecha()))) {
/* 453:520 */       throw new ExcepcionAS2Financiero("msg_error_fecha_nota_credito_factura");
/* 454:    */     }
/* 455:522 */     this.servicioFacturaProveedor.verificaExistenciaNumero(notaCreditoProveedor);
/* 456:524 */     if (notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_CREDITO_PROVEEDOR)
/* 457:    */     {
/* 458:526 */       if (ParametrosSistema.getNotaCreditoReversaGasto(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 459:528 */         for (DetalleFacturaProveedor detalleFacturaProveedor : notaCreditoProveedor.getListaDetalleFacturaProveedor())
/* 460:    */         {
/* 461:    */           String mensaje;
/* 462:531 */           if ((!detalleFacturaProveedor.isEliminado()) && (detalleFacturaProveedor.getProducto().isTraIndicadorServicio()))
/* 463:    */           {
/* 464:533 */             BigDecimal valorLinea = detalleFacturaProveedor.getBaseImponible().add(detalleFacturaProveedor.getValorImpuestosLinea());
/* 465:535 */             if (valorLinea.compareTo(detalleFacturaProveedor.getValorGastoContable()) != 0)
/* 466:    */             {
/* 467:537 */               mensaje = "";
/* 468:538 */               if (notaCreditoProveedor.getFacturaProveedorSRI() != null) {
/* 469:541 */                 mensaje = " F:/" + notaCreditoProveedor.getFacturaProveedorSRI().getEstablecimiento() + "-" + notaCreditoProveedor.getFacturaProveedorSRI().getPuntoEmision() + "-" + notaCreditoProveedor.getFacturaProveedorSRI().getNumero() + " ";
/* 470:    */               }
/* 471:545 */               mensaje = mensaje + detalleFacturaProveedor.getProducto().getNombre().trim() + " " + detalleFacturaProveedor.getProducto().getNombre() + " " + valorLinea + "<>" + detalleFacturaProveedor.getValorGastoContable();
/* 472:    */               
/* 473:547 */               throw new ExcepcionAS2Compras("msg_error_diferencia_gasto_servicio", mensaje);
/* 474:    */             }
/* 475:550 */             for (GastoProductoFacturaProveedor gasto : detalleFacturaProveedor.getListaGastoProductoFactura()) {
/* 476:552 */               if ((gasto.getValor().compareTo(BigDecimal.ZERO) == 0) && (detalleFacturaProveedor.getPrecioLinea().compareTo(BigDecimal.ZERO) != 0)) {
/* 477:553 */                 throw new ExcepcionAS2Financiero("msg_error_movimiento_total_cero");
/* 478:    */               }
/* 479:    */             }
/* 480:    */           }
/* 481:558 */           if (!detalleFacturaProveedor.isEliminado())
/* 482:    */           {
/* 483:559 */             BigDecimal valorDetalleNotaCredito = detalleFacturaProveedor.getPrecioLinea();
/* 484:560 */             if (detalleFacturaProveedor.getDetalleFacturaProveedorPadre() != null)
/* 485:    */             {
/* 486:562 */               BigDecimal valorDetalleFacturaPadre = detalleFacturaProveedor.getDetalleFacturaProveedorPadre().getPrecioLinea().subtract(detalleFacturaProveedor.getDetalleFacturaProveedorPadre().getDescuentoLinea());
/* 487:563 */               if (valorDetalleNotaCredito.compareTo(valorDetalleFacturaPadre) == 1) {
/* 488:564 */                 throw new ExcepcionAS2Financiero("msg_error_nota_credito_001");
/* 489:    */               }
/* 490:    */             }
/* 491:    */           }
/* 492:    */         }
/* 493:    */       }
/* 494:573 */       BigDecimal totalFactura = notaCreditoProveedor.getFacturaProveedorPadre().getTotalFactura();
/* 495:574 */       BigDecimal totalFacturaADevolver = notaCreditoProveedor.getTotalFactura();
/* 496:575 */       if (totalFacturaADevolver.compareTo(totalFactura) == 1) {
/* 497:576 */         throw new ExcepcionAS2Financiero("msg_error_nota_credito_001");
/* 498:    */       }
/* 499:    */     }
/* 500:    */     Object facturaProveedorExistente;
/* 501:580 */     if (notaCreditoProveedor.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_PROVEEDOR)
/* 502:    */     {
/* 503:581 */       if (!notaCreditoProveedor.getDocumento().isIndicadorDocumentoTributario())
/* 504:    */       {
/* 505:583 */         facturaProveedorExistente = this.servicioFacturaProveedor.buscarPorRecepcionProveedor(notaCreditoProveedor.getRecepcionProveedor().getId());
/* 506:584 */         if (facturaProveedorExistente != null) {
/* 507:585 */           throw new ExcepcionAS2Financiero("msg_info_ya_existe_factura_recepcion", " " + ((FacturaProveedor)facturaProveedorExistente).getNumero());
/* 508:    */         }
/* 509:    */       }
/* 510:588 */       for (facturaProveedorExistente = notaCreditoProveedor.getListaDetalleFacturaProveedor().iterator(); ((Iterator)facturaProveedorExistente).hasNext();)
/* 511:    */       {
/* 512:588 */         DetalleFacturaProveedor detalleFacturaProveedor = (DetalleFacturaProveedor)((Iterator)facturaProveedorExistente).next();
/* 513:589 */         if (!detalleFacturaProveedor.isEliminado()) {
/* 514:591 */           if (detalleFacturaProveedor.getDetalleRecepcionProveedorDevolucion().getCantidadPorDevolver().compareTo(detalleFacturaProveedor.getCantidad()) == -1)
/* 515:    */           {
/* 516:592 */             if (!notaCreditoProveedor.getDocumento().isIndicadorDocumentoTributario()) {
/* 517:593 */               throw new ExcepcionAS2Financiero("msg_error_devolucion_cantidad_recepcion");
/* 518:    */             }
/* 519:595 */             throw new ExcepcionAS2Financiero("msg_error_devolucion_cantidad");
/* 520:    */           }
/* 521:    */         }
/* 522:    */       }
/* 523:    */     }
/* 524:602 */     if (notaCreditoProveedor.getDireccionEmpresa() == null) {
/* 525:603 */       throw new ExcepcionAS2Compras("msg_error_direccion_principal_no_lista_direcciones");
/* 526:    */     }
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void actulizarDetalleDevolucion(int idDevolucionProveedor, FacturaProveedor devolucionProveedor, boolean factura)
/* 530:    */   {
/* 531:611 */     devolucionProveedor.setListaDetalleFacturaProveedor(new ArrayList());
/* 532:    */     
/* 533:    */ 
/* 534:614 */     List<DetalleRecepcionProveedor> listadfpPorDevolver = this.facturaProveedorDao.obtenerDetalleDevolucionProveedor(idDevolucionProveedor, factura);
/* 535:615 */     if (factura) {
/* 536:616 */       for (DetalleRecepcionProveedor detalleRecepcionProveedor : listadfpPorDevolver)
/* 537:    */       {
/* 538:617 */         detalleRecepcionProveedor.getDetalleFacturaProveedor().getListaImpuestoProductoFacturaProveedor().size();
/* 539:618 */         for (localIterator2 = detalleRecepcionProveedor.getDetalleFacturaProveedor()
/* 540:619 */               .getListaImpuestoProductoFacturaProveedor().iterator(); localIterator2.hasNext();)
/* 541:    */         {
/* 542:618 */           ipfp = (ImpuestoProductoFacturaProveedor)localIterator2.next();
/* 543:    */           
/* 544:620 */           ipfp.getImpuesto().getId();
/* 545:    */         }
/* 546:    */       }
/* 547:    */     }
/* 548:    */     Iterator localIterator2;
/* 549:    */     ImpuestoProductoFacturaProveedor ipfp;
/* 550:626 */     for (DetalleRecepcionProveedor detalleRecepcionProveedor : listadfpPorDevolver)
/* 551:    */     {
/* 552:628 */       DetalleFacturaProveedor dfp = new DetalleFacturaProveedor();
/* 553:629 */       dfp.setIdOrganizacion(devolucionProveedor.getIdOrganizacion());
/* 554:630 */       dfp.setIdSucursal(devolucionProveedor.getSucursal().getId());
/* 555:631 */       dfp.setFacturaProveedor(devolucionProveedor);
/* 556:632 */       dfp.setProducto(detalleRecepcionProveedor.getProducto());
/* 557:633 */       if (factura)
/* 558:    */       {
/* 559:634 */         dfp.setPrecio(detalleRecepcionProveedor.getDetalleFacturaProveedor().getPrecio()
/* 560:635 */           .subtract(detalleRecepcionProveedor.getDetalleFacturaProveedor().getDescuento()));
/* 561:636 */         dfp.setDescuento(detalleRecepcionProveedor.getDetalleFacturaProveedor().getDescuento());
/* 562:637 */         dfp.setDetalleFacturaProveedorPadre(detalleRecepcionProveedor.getDetalleFacturaProveedor());
/* 563:638 */         dfp.setUnidadCompra(detalleRecepcionProveedor.getDetalleFacturaProveedor().getUnidadCompra());
/* 564:639 */         dfp.setIndicadorImpuestos(detalleRecepcionProveedor.getDetalleFacturaProveedor().isIndicadorImpuestos());
/* 565:    */       }
/* 566:    */       else
/* 567:    */       {
/* 568:641 */         if (detalleRecepcionProveedor.getDetallePedidoProveedor() != null) {
/* 569:642 */           dfp.setPrecio(detalleRecepcionProveedor.getDetallePedidoProveedor().getPrecio());
/* 570:    */         } else {
/* 571:644 */           dfp.setPrecio(detalleRecepcionProveedor.getInventarioProducto().getCosto()
/* 572:645 */             .divide(detalleRecepcionProveedor.getInventarioProducto().getCantidad(), 4, RoundingMode.HALF_UP));
/* 573:    */         }
/* 574:647 */         dfp.setUnidadCompra(dfp.getProducto().getUnidadCompra());
/* 575:    */       }
/* 576:650 */       dfp.setBodega(detalleRecepcionProveedor.getBodega());
/* 577:651 */       dfp.setDetalleRecepcionProveedorDevolucion(detalleRecepcionProveedor);
/* 578:652 */       dfp.setLote(detalleRecepcionProveedor.getLote());
/* 579:653 */       detalleRecepcionProveedor.setCantidadPorDevolver(detalleRecepcionProveedor.getCantidad().subtract(detalleRecepcionProveedor
/* 580:654 */         .getCantidadDevuelta()));
/* 581:656 */       if ((dfp.isIndicadorImpuestos()) && (factura))
/* 582:    */       {
/* 583:657 */         dfp.setIndicadorImpuestos(dfp.getProducto().isIndicadorImpuestos());
/* 584:658 */         for (ImpuestoProductoFacturaProveedor ipfp : detalleRecepcionProveedor.getDetalleFacturaProveedor()
/* 585:659 */           .getListaImpuestoProductoFacturaProveedor())
/* 586:    */         {
/* 587:660 */           ImpuestoProductoFacturaProveedor ifpNEW = new ImpuestoProductoFacturaProveedor();
/* 588:661 */           ifpNEW.setImpuesto(ipfp.getImpuesto());
/* 589:662 */           ifpNEW.setPorcentajeImpuesto(ipfp.getPorcentajeImpuesto());
/* 590:663 */           ifpNEW.setDetalleFacturaProveedor(dfp);
/* 591:664 */           dfp.getListaImpuestoProductoFacturaProveedor().add(ifpNEW);
/* 592:    */         }
/* 593:    */       }
/* 594:667 */       devolucionProveedor.getListaDetalleFacturaProveedor().add(dfp);
/* 595:    */     }
/* 596:    */   }
/* 597:    */   
/* 598:    */   public FacturaProveedor cargarDetalleFactura(FacturaProveedor facturaProveedor, FacturaProveedor notaCreditoProveedor)
/* 599:    */     throws ExcepcionAS2Compras, ExcepcionAS2
/* 600:    */   {
/* 601:674 */     FacturaProveedor fp = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(facturaProveedor.getId()));
/* 602:675 */     notaCreditoProveedor = this.servicioFacturaProveedor.copiarFacturaProveedor(fp, notaCreditoProveedor);
/* 603:676 */     return notaCreditoProveedor;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public FacturaProveedor cargarSecuencia(FacturaProveedor facturaProveedor, PuntoDeVenta puntoDeVenta)
/* 607:    */     throws ExcepcionAS2
/* 608:    */   {
/* 609:683 */     if (puntoDeVenta != null) {
/* 610:684 */       this.servicioDocumento.cargarSecuencia(facturaProveedor.getDocumento(), puntoDeVenta, facturaProveedor.getFecha());
/* 611:    */     }
/* 612:687 */     String numero = this.servicioSecuencia.obtenerSecuencia(facturaProveedor.getDocumento().getSecuencia(), facturaProveedor.getFecha());
/* 613:    */     
/* 614:689 */     facturaProveedor.setNumero(numero);
/* 615:    */     
/* 616:691 */     return facturaProveedor;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public FacturaProveedor actualizarAutorizacionSRI(FacturaProveedor facturaProveedor, PuntoDeVenta puntoDeVenta)
/* 620:    */     throws ExcepcionAS2
/* 621:    */   {
/* 622:696 */     if (facturaProveedor.getFacturaProveedorSRI() != null)
/* 623:    */     {
/* 624:697 */       AutorizacionDocumentoSRI autorizacionDocumentoSRI = this.servicioDocumento.obtenerAutorizacionConSecuencia(facturaProveedor.getFecha(), facturaProveedor
/* 625:698 */         .getDocumento(), puntoDeVenta);
/* 626:699 */       facturaProveedor.getFacturaProveedorSRI().setAutorizacion(autorizacionDocumentoSRI.getAutorizacion());
/* 627:    */     }
/* 628:701 */     return facturaProveedor;
/* 629:    */   }
/* 630:    */   
/* 631:    */   private void actualizarInventarioProducto(FacturaProveedor notaCreditoProveedor, boolean devolucion)
/* 632:    */     throws ExcepcionAS2Inventario, ExcepcionAS2
/* 633:    */   {
/* 634:713 */     InventarioProducto inventarioProducto = null;
/* 635:714 */     for (DetalleFacturaProveedor dfp : notaCreditoProveedor.getListaDetalleFacturaProveedor()) {
/* 636:716 */       if (dfp.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO))
/* 637:    */       {
/* 638:718 */         dfp.setEliminado(dfp.getCantidad().compareTo(BigDecimal.ZERO) == 0);
/* 639:720 */         if (!dfp.isEliminado())
/* 640:    */         {
/* 641:722 */           inventarioProducto = dfp.getInventarioProducto();
/* 642:724 */           if (inventarioProducto == null) {
/* 643:725 */             inventarioProducto = new InventarioProducto();
/* 644:    */           }
/* 645:727 */           dfp.setInventarioProducto(inventarioProducto);
/* 646:728 */           inventarioProducto.setDetalleDevolucionProveedor(dfp);
/* 647:    */           
/* 648:730 */           BigDecimal cantidad = BigDecimal.ZERO;
/* 649:731 */           inventarioProducto.setOperacion(-1);
/* 650:732 */           if (devolucion)
/* 651:    */           {
/* 652:733 */             cantidad = this.servicioProducto.convierteUnidad(dfp.getUnidadCompra(), dfp.getProducto().getUnidad(), dfp.getProducto()
/* 653:734 */               .getIdProducto(), dfp.getCantidad());
/* 654:735 */             inventarioProducto.setCantidadDocumento(dfp.getCantidad());
/* 655:736 */             inventarioProducto.setBodega(dfp.getBodega());
/* 656:737 */             if (notaCreditoProveedor.getDocumento().isIndicadorDocumentoTributario()) {
/* 657:738 */               inventarioProducto.setCosto(dfp.getPrecio().subtract(dfp.getDescuento())
/* 658:739 */                 .multiply(dfp.getDetalleRecepcionProveedorDevolucion().getCantidadDevuelta()));
/* 659:    */             } else {
/* 660:741 */               inventarioProducto.setCosto(dfp.getPrecio().subtract(dfp.getDescuento())
/* 661:742 */                 .multiply(dfp.getCantidadADevolver()));
/* 662:    */             }
/* 663:744 */             inventarioProducto.setIndicadorGeneraCosto(notaCreditoProveedor.getDocumento().isIndicadorGeneraCosto());
/* 664:    */           }
/* 665:    */           else
/* 666:    */           {
/* 667:747 */             if ((dfp.getDetalleFacturaProveedorPadre() != null) && (dfp.getDetalleFacturaProveedorPadre().getListaDetalleRecepcionProveedor().size() > 0)) {
/* 668:749 */               inventarioProducto.setBodega(((DetalleRecepcionProveedor)dfp.getDetalleFacturaProveedorPadre().getListaDetalleRecepcionProveedor().get(0)).getBodega());
/* 669:    */             } else {
/* 670:751 */               inventarioProducto.setBodega(AppUtil.getBodega());
/* 671:    */             }
/* 672:754 */             inventarioProducto.setCosto(dfp.getPrecio());
/* 673:755 */             inventarioProducto.setIndicadorGeneraCosto(true);
/* 674:    */           }
/* 675:758 */           if ((devolucion) && (!notaCreditoProveedor.getDocumento().isIndicadorDocumentoTributario())) {
/* 676:759 */             inventarioProducto.setIndicadorGeneraCosto(true);
/* 677:    */           }
/* 678:761 */           inventarioProducto.setCantidad(cantidad);
/* 679:762 */           inventarioProducto.setUnidadDocumento(dfp.getUnidadCompra().getNombre());
/* 680:    */           
/* 681:764 */           inventarioProducto.setFecha(notaCreditoProveedor.getFecha());
/* 682:765 */           inventarioProducto.setDocumento(notaCreditoProveedor.getDocumento());
/* 683:766 */           inventarioProducto.setIdOrganizacion(notaCreditoProveedor.getIdOrganizacion());
/* 684:    */           
/* 685:768 */           inventarioProducto.setIdSucursal(notaCreditoProveedor.getSucursal().getId());
/* 686:769 */           inventarioProducto.setProducto(dfp.getProducto());
/* 687:770 */           inventarioProducto.setNumeroDocumento(notaCreditoProveedor.getNumero());
/* 688:771 */           inventarioProducto.setNombreFiscalEmpresa(notaCreditoProveedor.getEmpresa().getNombreFiscal());
/* 689:773 */           if ((dfp.getDetalleRecepcionProveedorDevolucion() != null) && 
/* 690:774 */             (dfp.getDetalleRecepcionProveedorDevolucion().getInventarioProducto() != null) && 
/* 691:775 */             (dfp.getDetalleRecepcionProveedorDevolucion().getInventarioProducto().getLote() != null)) {
/* 692:776 */             inventarioProducto.setLote(dfp.getDetalleRecepcionProveedorDevolucion().getInventarioProducto().getLote());
/* 693:    */           }
/* 694:    */         }
/* 695:    */       }
/* 696:    */     }
/* 697:    */   }
/* 698:    */   
/* 699:    */   private void actualizarCantidadPorDevolver(FacturaProveedor devolucionProveedor)
/* 700:    */   {
/* 701:787 */     if (devolucionProveedor.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_PROVEEDOR) {
/* 702:789 */       for (DetalleFacturaProveedor detalleDevolucion : devolucionProveedor.getListaDetalleFacturaProveedor()) {
/* 703:791 */         if ((!detalleDevolucion.isEliminado()) && (detalleDevolucion.getDetalleRecepcionProveedorDevolucion() != null))
/* 704:    */         {
/* 705:795 */           BigDecimal cantidadDevuelta = detalleDevolucion.getDetalleRecepcionProveedorDevolucion().getCantidadDevuelta().add(detalleDevolucion.getCantidad());
/* 706:    */           
/* 707:797 */           HashMap<String, Object> campos = new HashMap();
/* 708:798 */           campos.put("cantidadDevuelta", cantidadDevuelta);
/* 709:    */           
/* 710:800 */           this.detalleRecepcionProveedorDao.actualizarAtributoEntidad(detalleDevolucion.getDetalleRecepcionProveedorDevolucion(), campos);
/* 711:    */         }
/* 712:    */       }
/* 713:    */     }
/* 714:    */   }
/* 715:    */   
/* 716:    */   public List getReporteNotaCreditoProveedor(int idFacturaProveedor)
/* 717:    */   {
/* 718:812 */     return this.facturaProveedorDao.getReporteNotaCreditoProveedor(idFacturaProveedor);
/* 719:    */   }
/* 720:    */   
/* 721:    */   public List<DetalleFacturaProveedor> getDetallesDevolucionesProveedor(int idDetalleRecepcionProveedor)
/* 722:    */   {
/* 723:817 */     return this.facturaProveedorDao.getDetallesDevolucionesProveedor(idDetalleRecepcionProveedor);
/* 724:    */   }
/* 725:    */   
/* 726:    */   private void anularTransformacionDevolucionProveedor(FacturaProveedor devolucionProveedor, boolean generaInversoInventarioProducto)
/* 727:    */     throws ExcepcionAS2Financiero
/* 728:    */   {
/* 729:829 */     Asiento asiento = null;
/* 730:830 */     for (DetalleFacturaProveedor dfp : devolucionProveedor.getListaDetalleFacturaProveedor())
/* 731:    */     {
/* 732:831 */       DetalleFacturaProveedor detalleFacturaProveedor = (DetalleFacturaProveedor)this.detalleFacturaProveedorDao.buscarPorId(Integer.valueOf(dfp.getId()));
/* 733:832 */       if (detalleFacturaProveedor.getTransformacionAutomatica() != null)
/* 734:    */       {
/* 735:833 */         detalleFacturaProveedor.getTransformacionAutomatica().getId();
/* 736:834 */         this.servicioMovimientoInventario.actualizarEstado(Integer.valueOf(detalleFacturaProveedor.getTransformacionAutomatica().getId()), Estado.ANULADO);
/* 737:835 */         asiento = detalleFacturaProveedor.getTransformacionAutomatica().getAsiento();
/* 738:    */       }
/* 739:    */     }
/* 740:838 */     if (asiento != null)
/* 741:    */     {
/* 742:839 */       asiento.setIndicadorAutomatico(false);
/* 743:840 */       this.servicioAsiento.anular(asiento);
/* 744:    */     }
/* 745:843 */     if (generaInversoInventarioProducto) {
/* 746:844 */       this.servicioInventarioProducto.generarMovimientoInventarioTransformacionInverso(devolucionProveedor, devolucionProveedor.getFecha());
/* 747:    */     } else {
/* 748:846 */       this.servicioInventarioProducto.eliminaInventarioProductoTransformacionPorIdDevolucionProveedor(Integer.valueOf(devolucionProveedor.getId()));
/* 749:    */     }
/* 750:    */   }
/* 751:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.impl.ServicioNotaCreditoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */