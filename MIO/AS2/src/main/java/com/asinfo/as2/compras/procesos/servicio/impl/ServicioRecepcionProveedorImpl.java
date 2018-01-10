/*    1:     */ package com.asinfo.as2.compras.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    5:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    6:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    7:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*    8:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*    9:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedorRemote;
/*   10:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioVerificadorCompras;
/*   11:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   12:     */ import com.asinfo.as2.dao.AsientoDao;
/*   13:     */ import com.asinfo.as2.dao.DetalleAsientoDao;
/*   14:     */ import com.asinfo.as2.dao.DetallePedidoProveedorDao;
/*   15:     */ import com.asinfo.as2.dao.DetalleRecepcionProveedorDao;
/*   16:     */ import com.asinfo.as2.dao.DocumentoContabilizacionDao;
/*   17:     */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*   18:     */ import com.asinfo.as2.dao.FacturaProveedorImportacionDao;
/*   19:     */ import com.asinfo.as2.dao.GenericoDao;
/*   20:     */ import com.asinfo.as2.dao.RecepcionProveedorDao;
/*   21:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   22:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   23:     */ import com.asinfo.as2.entities.Asiento;
/*   24:     */ import com.asinfo.as2.entities.Bodega;
/*   25:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   26:     */ import com.asinfo.as2.entities.CriterioContabilizacion;
/*   27:     */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   28:     */ import com.asinfo.as2.entities.CuentaContable;
/*   29:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   30:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   31:     */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   32:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   33:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   34:     */ import com.asinfo.as2.entities.DimensionContable;
/*   35:     */ import com.asinfo.as2.entities.Documento;
/*   36:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   37:     */ import com.asinfo.as2.entities.Empresa;
/*   38:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   39:     */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   40:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   41:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*   42:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   43:     */ import com.asinfo.as2.entities.Lote;
/*   44:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   45:     */ import com.asinfo.as2.entities.Organizacion;
/*   46:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   47:     */ import com.asinfo.as2.entities.PedidoProveedor;
/*   48:     */ import com.asinfo.as2.entities.Producto;
/*   49:     */ import com.asinfo.as2.entities.Proveedor;
/*   50:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   51:     */ import com.asinfo.as2.entities.SaldoProducto;
/*   52:     */ import com.asinfo.as2.entities.SerieInventarioProducto;
/*   53:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   54:     */ import com.asinfo.as2.entities.Sucursal;
/*   55:     */ import com.asinfo.as2.entities.TipoAsiento;
/*   56:     */ import com.asinfo.as2.entities.Unidad;
/*   57:     */ import com.asinfo.as2.entities.VersionListaPrecios;
/*   58:     */ import com.asinfo.as2.entities.presupuesto.DetallePresupuesto;
/*   59:     */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*   60:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   61:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   62:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   63:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   64:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   65:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   66:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   67:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   68:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*   69:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   70:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   71:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*   72:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   73:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   74:     */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioPresupuesto;
/*   75:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   76:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   77:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   78:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   79:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*   80:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*   81:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*   82:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioSerieInventarioProducto;
/*   83:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*   84:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   85:     */ import com.asinfo.as2.util.AppUtil;
/*   86:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   87:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   88:     */ import java.io.IOException;
/*   89:     */ import java.io.InputStream;
/*   90:     */ import java.io.PrintStream;
/*   91:     */ import java.math.BigDecimal;
/*   92:     */ import java.math.RoundingMode;
/*   93:     */ import java.util.ArrayList;
/*   94:     */ import java.util.Date;
/*   95:     */ import java.util.HashMap;
/*   96:     */ import java.util.HashSet;
/*   97:     */ import java.util.Iterator;
/*   98:     */ import java.util.List;
/*   99:     */ import java.util.Map;
/*  100:     */ import java.util.Set;
/*  101:     */ import javax.ejb.EJB;
/*  102:     */ import javax.ejb.SessionContext;
/*  103:     */ import javax.ejb.Stateless;
/*  104:     */ import javax.ejb.TransactionAttribute;
/*  105:     */ import javax.ejb.TransactionAttributeType;
/*  106:     */ import javax.ejb.TransactionManagement;
/*  107:     */ import javax.ejb.TransactionManagementType;
/*  108:     */ import org.apache.log4j.Logger;
/*  109:     */ 
/*  110:     */ @Stateless
/*  111:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  112:     */ public class ServicioRecepcionProveedorImpl
/*  113:     */   extends AbstractServicioAS2Financiero
/*  114:     */   implements ServicioRecepcionProveedor, ServicioRecepcionProveedorRemote
/*  115:     */ {
/*  116:     */   private static final long serialVersionUID = -2793691958199809668L;
/*  117:     */   @EJB
/*  118:     */   private ServicioSecuencia servicioSecuencia;
/*  119:     */   @EJB
/*  120:     */   private RecepcionProveedorDao recepcionProveedorDao;
/*  121:     */   @EJB
/*  122:     */   private DetalleRecepcionProveedorDao detalleRecepcionProveedorDao;
/*  123:     */   @EJB
/*  124:     */   private ServicioProducto servicioProducto;
/*  125:     */   @EJB
/*  126:     */   private ServicioPeriodo servicioPeriodo;
/*  127:     */   @EJB
/*  128:     */   private ServicioCosteo servicioCosteo;
/*  129:     */   @EJB
/*  130:     */   private ServicioInventarioProducto servicioInventarioProducto;
/*  131:     */   @EJB
/*  132:     */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*  133:     */   @EJB
/*  134:     */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  135:     */   @EJB
/*  136:     */   private FacturaProveedorDao facturaProveedorDao;
/*  137:     */   @EJB
/*  138:     */   private transient ServicioVerificadorCompras servicioVerificadorCompras;
/*  139:     */   @EJB
/*  140:     */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/*  141:     */   @EJB
/*  142:     */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  143:     */   @EJB
/*  144:     */   private transient ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  145:     */   @EJB
/*  146:     */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  147:     */   @EJB
/*  148:     */   private transient ServicioLote servicioLote;
/*  149:     */   @EJB
/*  150:     */   private transient ServicioOrganizacion servicioOrganizacion;
/*  151:     */   @EJB
/*  152:     */   private ServicioSerieInventarioProducto servicioSerieInventarioProducto;
/*  153:     */   @EJB
/*  154:     */   private transient ServicioPresupuesto servicioPresupuesto;
/*  155:     */   @EJB
/*  156:     */   private DetallePedidoProveedorDao detallePedidoProveedorDao;
/*  157:     */   @EJB
/*  158:     */   private AsientoDao asientoDao;
/*  159:     */   @EJB
/*  160:     */   private DetalleAsientoDao detalleAsientoDao;
/*  161:     */   @EJB
/*  162:     */   private GenericoDao<LecturaBalanza> lecturaBalanzaDao;
/*  163:     */   @EJB
/*  164:     */   private transient DocumentoContabilizacionDao documentoContabilizacionDao;
/*  165:     */   @EJB
/*  166:     */   private transient ServicioMovimientoInventario servicioMovimientoInventario;
/*  167:     */   @EJB
/*  168:     */   private transient FacturaProveedorImportacionDao facturaProveedorImportacionDao;
/*  169:     */   @EJB
/*  170:     */   private transient ServicioListaPrecios servicioListaPrecios;
/*  171:     */   @EJB
/*  172:     */   private transient ServicioBodega servicioBodega;
/*  173:     */   
/*  174:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  175:     */   public void guardar(RecepcionProveedor recepcionProveedor)
/*  176:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/*  177:     */   {
/*  178: 185 */     guardar(recepcionProveedor, false, false);
/*  179:     */   }
/*  180:     */   
/*  181:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  182:     */   public void guardar(RecepcionProveedor recepcionProveedor, boolean indicadorCostoCompra, boolean indicadorModificaPrecio)
/*  183:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/*  184:     */   {
/*  185: 197 */     boolean nuevo = recepcionProveedor.getId() == 0;
/*  186: 198 */     if (recepcionProveedor.getEstado().equals(Estado.ELABORADO))
/*  187:     */     {
/*  188: 199 */       eliminarInventariosProducto(recepcionProveedor);
/*  189:     */     }
/*  190:     */     else
/*  191:     */     {
/*  192: 201 */       eliminarDetallesCero(recepcionProveedor);
/*  193: 202 */       agregarInventariosProducto(recepcionProveedor);
/*  194:     */     }
/*  195:     */     try
/*  196:     */     {
/*  197: 206 */       if (recepcionProveedor.getFacturaProveedor() != null)
/*  198:     */       {
/*  199: 207 */         recepcionProveedor.setEstado(Estado.FACTURADO);
/*  200: 208 */         if (recepcionProveedor.getFacturaProveedor().getFacturaProveedorSRI() != null)
/*  201:     */         {
/*  202: 211 */           String numeroFactura = recepcionProveedor.getFacturaProveedor().getFacturaProveedorSRI().getEstablecimiento() + "-" + recepcionProveedor.getFacturaProveedor().getFacturaProveedorSRI().getPuntoEmision() + "-" + recepcionProveedor.getFacturaProveedor().getFacturaProveedorSRI().getNumero();
/*  203: 212 */           recepcionProveedor.setNumeroFactura(numeroFactura);
/*  204:     */         }
/*  205:     */         else
/*  206:     */         {
/*  207: 214 */           recepcionProveedor.setNumeroFactura(recepcionProveedor.getFacturaProveedor().getReferencia3());
/*  208:     */         }
/*  209:     */       }
/*  210: 219 */       if ((getDimensionPresupuesto() != null) && (recepcionProveedor.getPedidoProveedor() != null)) {
/*  211: 220 */         actualizarSaldoRealPresupuesto(recepcionProveedor);
/*  212:     */       }
/*  213: 221 */       if (!recepcionProveedor.getEstado().equals(Estado.ELABORADO))
/*  214:     */       {
/*  215: 223 */         actualizarInventarioProducto(recepcionProveedor);
/*  216:     */         
/*  217: 225 */         this.servicioVerificadorInventario.cantidadDetalle(recepcionProveedor.getListaDetalleRecepcionProveedor());
/*  218:     */         
/*  219: 227 */         validar(recepcionProveedor, indicadorModificaPrecio);
/*  220: 229 */         if (!indicadorCostoCompra)
/*  221:     */         {
/*  222: 231 */           this.servicioVerificadorInventario.actualizarSaldoProducto(recepcionProveedor, true);
/*  223:     */           
/*  224: 233 */           this.servicioVerificadorInventario.actualizarSaldoProducto(recepcionProveedor, false);
/*  225:     */           
/*  226: 235 */           cargarSecuencia(recepcionProveedor);
/*  227:     */         }
/*  228:     */       }
/*  229:     */       else
/*  230:     */       {
/*  231: 241 */         cargarSecuencia(recepcionProveedor);
/*  232:     */       }
/*  233: 247 */       ArrayList<DetalleRecepcionProveedor> auxListaDetalleRecepcionProveedor = new ArrayList();
/*  234: 249 */       if (nuevo) {
/*  235: 251 */         this.recepcionProveedorDao.guardar(recepcionProveedor);
/*  236:     */       }
/*  237: 255 */       for (DetalleRecepcionProveedor drp : recepcionProveedor.getListaDetalleRecepcionProveedor())
/*  238:     */       {
/*  239: 256 */         if (!drp.isEliminado()) {
/*  240: 257 */           auxListaDetalleRecepcionProveedor.add(drp);
/*  241:     */         }
/*  242: 259 */         drp.setIdOrganizacion(recepcionProveedor.getIdOrganizacion());
/*  243: 260 */         drp.setIdSucursal(recepcionProveedor.getSucursal().getId());
/*  244: 261 */         if (drp.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO))
/*  245:     */         {
/*  246:     */           Lote lote;
/*  247:     */           String codigoLote;
/*  248: 262 */           if (!drp.isEliminado())
/*  249:     */           {
/*  250: 264 */             lote = drp.getLote();
/*  251: 265 */             if ((lote == null) && (drp.getInventarioProducto() != null)) {
/*  252: 266 */               lote = drp.getInventarioProducto().getLote();
/*  253:     */             }
/*  254: 268 */             if (lote != null)
/*  255:     */             {
/*  256: 270 */               codigoLote = lote.getCodigo();
/*  257:     */               try
/*  258:     */               {
/*  259: 272 */                 if (lote.getIdLote() == 0) {
/*  260: 273 */                   if (lote.isIndicadorMovimientoInterno()) {
/*  261: 274 */                     lote = this.servicioLote.buscarPorCodigoProductoProveedor(codigoLote, drp.getProducto(), null);
/*  262:     */                   } else {
/*  263: 276 */                     lote = this.servicioLote.buscarPorCodigoProductoProveedor(codigoLote, drp.getProducto(), drp
/*  264: 277 */                       .getRecepcionProveedor().getEmpresa());
/*  265:     */                   }
/*  266:     */                 }
/*  267:     */               }
/*  268:     */               catch (ExcepcionAS2 e)
/*  269:     */               {
/*  270: 282 */                 lote = this.servicioLote.crearLote(drp.getRecepcionProveedor().getIdOrganizacion(), drp.getProducto(), codigoLote, false, drp
/*  271: 283 */                   .getRecepcionProveedor().getEmpresa(), drp.getRecepcionProveedor().getFecha(), drp
/*  272: 284 */                   .getRecepcionProveedor().getFecha(), true);
/*  273:     */               }
/*  274:     */             }
/*  275: 287 */             drp.setLote(lote);
/*  276: 288 */             if (drp.getInventarioProducto() != null) {
/*  277: 289 */               drp.getInventarioProducto().setLote(lote);
/*  278:     */             }
/*  279: 293 */             if (!recepcionProveedor.getEstado().equals(Estado.ELABORADO))
/*  280:     */             {
/*  281: 295 */               if (drp.getProducto().getIndicadorSerie().booleanValue()) {
/*  282: 296 */                 for (SerieInventarioProducto serie : drp.getInventarioProducto().getListaSerieProducto()) {
/*  283: 297 */                   this.servicioSerieInventarioProducto.guardar(serie);
/*  284:     */                 }
/*  285:     */               }
/*  286: 303 */               drp.getInventarioProducto().setNumeroDocumento(recepcionProveedor.getNumero() + (recepcionProveedor.getFacturaProveedor() != null ? "/" + recepcionProveedor
/*  287: 304 */                 .getFacturaProveedor().getNumero() : ""));
/*  288: 305 */               drp.getInventarioProducto().setNombreFiscalEmpresa(recepcionProveedor.getEmpresa().getNombreFiscal());
/*  289: 306 */               this.servicioInventarioProducto.guardar(drp.getInventarioProducto());
/*  290:     */             }
/*  291: 310 */             this.detalleRecepcionProveedorDao.guardar(drp);
/*  292: 311 */             for (LecturaBalanza lb : drp.getListaLecturaBalanza()) {
/*  293: 312 */               this.lecturaBalanzaDao.guardar(lb);
/*  294:     */             }
/*  295:     */           }
/*  296: 314 */           else if (drp.getId() != 0)
/*  297:     */           {
/*  298: 315 */             for (LecturaBalanza lb : drp.getListaLecturaBalanza()) {
/*  299: 316 */               this.lecturaBalanzaDao.guardar(lb);
/*  300:     */             }
/*  301: 318 */             this.detalleRecepcionProveedorDao.guardar(drp);
/*  302:     */           }
/*  303:     */         }
/*  304:     */         else
/*  305:     */         {
/*  306: 321 */           this.detalleRecepcionProveedorDao.guardar(drp);
/*  307:     */         }
/*  308: 325 */         if ((!recepcionProveedor.getEstado().equals(Estado.ELABORADO)) && (drp.getProducto().isIndicadorManejaUnidadInformativa()) && 
/*  309: 326 */           (drp.getTransformacionAutomatica() == null)) {
/*  310: 327 */           this.servicioMovimientoInventario.generarTransformacionUnidadInformativa(null, drp);
/*  311:     */         }
/*  312:     */       }
/*  313: 331 */       if (!recepcionProveedor.getEstado().equals(Estado.ELABORADO))
/*  314:     */       {
/*  315: 333 */         this.servicioVerificadorCompras.actualizarCantidadPorRecibir(recepcionProveedor, false);
/*  316: 335 */         if (recepcionProveedor.getPedidoProveedor() != null) {
/*  317: 336 */           this.servicioPedidoProveedor.cierreAutomatico(recepcionProveedor.getPedidoProveedor().getId());
/*  318:     */         }
/*  319: 339 */         contabilizar(recepcionProveedor);
/*  320: 340 */         if (ParametrosSistema.getGeneracionDeCostosAutomatica(recepcionProveedor.getIdOrganizacion()).booleanValue()) {
/*  321: 342 */           this.servicioCosteo.generarCostos(recepcionProveedor, ParametrosSistema.isCosteoPorBodega(recepcionProveedor.getIdOrganizacion()).booleanValue());
/*  322:     */         }
/*  323: 345 */         if (recepcionProveedor.getFacturaProveedor() != null)
/*  324:     */         {
/*  325: 346 */           recepcionProveedor.getFacturaProveedor().setRecepcionProveedor(recepcionProveedor);
/*  326:     */           
/*  327: 348 */           this.facturaProveedorDao.guardar(recepcionProveedor.getFacturaProveedor());
/*  328:     */         }
/*  329:     */       }
/*  330: 354 */       recepcionProveedor.setListaDetalleRecepcionProveedor(auxListaDetalleRecepcionProveedor);
/*  331: 355 */       if (recepcionProveedor.getId() != 0) {
/*  332: 356 */         this.recepcionProveedorDao.guardar(recepcionProveedor);
/*  333:     */       }
/*  334:     */     }
/*  335:     */     catch (ExcepcionAS2Financiero e)
/*  336:     */     {
/*  337: 360 */       this.context.setRollbackOnly();
/*  338: 361 */       e.printStackTrace();
/*  339: 362 */       throw e;
/*  340:     */     }
/*  341:     */     catch (ExcepcionAS2 e)
/*  342:     */     {
/*  343: 364 */       this.context.setRollbackOnly();
/*  344: 365 */       e.printStackTrace();
/*  345: 366 */       throw e;
/*  346:     */     }
/*  347:     */     catch (AS2Exception e)
/*  348:     */     {
/*  349: 368 */       this.context.setRollbackOnly();
/*  350: 369 */       e.printStackTrace();
/*  351: 370 */       throw e;
/*  352:     */     }
/*  353:     */     catch (Exception e)
/*  354:     */     {
/*  355: 372 */       this.context.setRollbackOnly();
/*  356: 373 */       LOG.error(e);
/*  357: 374 */       e.printStackTrace();
/*  358: 375 */       throw new ExcepcionAS2(e);
/*  359:     */     }
/*  360:     */   }
/*  361:     */   
/*  362:     */   private String getDimensionPresupuesto()
/*  363:     */   {
/*  364: 381 */     OrganizacionConfiguracion organizacionConfiguracion = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/*  365: 382 */     if ((!organizacionConfiguracion.getNombreDimension1().equals("")) && (organizacionConfiguracion.isIndicadorUsoPresupuestoDimension1())) {
/*  366: 383 */       return "1";
/*  367:     */     }
/*  368: 384 */     if ((!organizacionConfiguracion.getNombreDimension2().equals("")) && (organizacionConfiguracion.isIndicadorUsoPresupuestoDimension2())) {
/*  369: 385 */       return "2";
/*  370:     */     }
/*  371: 386 */     if ((!organizacionConfiguracion.getNombreDimension3().equals("")) && (organizacionConfiguracion.isIndicadorUsoPresupuestoDimension3())) {
/*  372: 387 */       return "3";
/*  373:     */     }
/*  374: 388 */     if ((!organizacionConfiguracion.getNombreDimension4().equals("")) && (organizacionConfiguracion.isIndicadorUsoPresupuestoDimension4())) {
/*  375: 389 */       return "4";
/*  376:     */     }
/*  377: 390 */     if ((!organizacionConfiguracion.getNombreDimension5().equals("")) && (organizacionConfiguracion.isIndicadorUsoPresupuestoDimension5())) {
/*  378: 391 */       return "5";
/*  379:     */     }
/*  380: 393 */     return null;
/*  381:     */   }
/*  382:     */   
/*  383:     */   private void actualizarSaldoRealPresupuesto(RecepcionProveedor recepcionProveedor)
/*  384:     */     throws ExcepcionAS2
/*  385:     */   {
/*  386: 397 */     Presupuesto presupuesto = this.servicioPresupuesto.buscarPorFecha(recepcionProveedor.getFecha(), recepcionProveedor.getIdOrganizacion());
/*  387: 398 */     presupuesto = this.servicioPresupuesto.cargarDetalle(presupuesto.getId());
/*  388: 399 */     Map<String, CriterioContabilizacion> hashCriterioContabilizacionProducto = new HashMap();
/*  389: 400 */     Map<String, CriterioContabilizacion> hashCriterioContabilizacionCategoriaProducto = new HashMap();
/*  390: 401 */     Map<String, CriterioContabilizacion> hashCriterioContabilizacionSubCategoriaProducto = new HashMap();
/*  391: 402 */     Map<String, CriterioContabilizacion> hashCriterioContabilizacionNull = new HashMap();
/*  392: 403 */     for (Iterator localIterator1 = this.servicioDocumentoContabilizacion
/*  393: 404 */           .obtenerListaPorDocumentoBase(AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.PEDIDO_PROVEEDOR).iterator(); localIterator1.hasNext();)
/*  394:     */     {
/*  395: 403 */       dc = (DocumentoContabilizacion)localIterator1.next();
/*  396:     */       
/*  397: 405 */       List<CriterioContabilizacion> listCC = this.servicioDocumentoContabilizacion.cargarDetalle(dc.getId()).getListaCriterioContabilizacion();
/*  398: 406 */       FuncionesUtiles.ordenaLista(listCC, "orden");
/*  399: 407 */       for (CriterioContabilizacion cc : listCC) {
/*  400: 408 */         if (cc.getProducto() != null) {
/*  401: 409 */           hashCriterioContabilizacionProducto.put(cc.getProducto().getId() + "~" + cc.getProducto().getCodigo(), cc);
/*  402: 410 */         } else if (cc.getCategoriaProducto() != null) {
/*  403: 411 */           hashCriterioContabilizacionCategoriaProducto.put(cc.getCategoriaProducto().getId() + "~" + cc.getCategoriaProducto().getCodigo(), cc);
/*  404: 413 */         } else if (cc.getSubcategoriaProducto() != null) {
/*  405: 415 */           hashCriterioContabilizacionSubCategoriaProducto.put(cc.getSubcategoriaProducto().getId() + "~" + cc.getSubcategoriaProducto().getCodigo(), cc);
/*  406:     */         } else {
/*  407: 417 */           hashCriterioContabilizacionNull.put(cc.getCuentaContable().getId() + "~" + cc.getCuentaContable().getCodigo(), cc);
/*  408:     */         }
/*  409:     */       }
/*  410:     */     }
/*  411:     */     DocumentoContabilizacion dc;
/*  412: 421 */     Object hashDetallesPresupuesto = new HashMap();
/*  413: 422 */     for (DetallePresupuesto dpr : presupuesto.getListaDetallePresupuesto()) {
/*  414: 423 */       ((Map)hashDetallesPresupuesto).put(dpr.getCuentaContable().getId() + "~" + dpr.getDimensionContable().getId(), dpr);
/*  415:     */     }
/*  416: 425 */     CriterioContabilizacion criterioContabilizacion = null;
/*  417: 426 */     int mes = FuncionesUtiles.getMes(recepcionProveedor.getFecha());
/*  418: 427 */     for (DetallePedidoProveedor detallePedidoProveedor : this.servicioPedidoProveedor
/*  419: 428 */       .obtenerListaDetallePedidoPorRecibir(recepcionProveedor.getPedidoProveedor().getId()))
/*  420:     */     {
/*  421: 431 */       if (hashCriterioContabilizacionProducto.containsKey(detallePedidoProveedor.getProducto().getId() + "~" + detallePedidoProveedor.getProducto().getCodigo()))
/*  422:     */       {
/*  423: 433 */         criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionProducto.get(detallePedidoProveedor.getProducto().getId() + "~" + detallePedidoProveedor.getProducto().getCodigo());
/*  424:     */       }
/*  425: 435 */       else if (hashCriterioContabilizacionCategoriaProducto.containsKey(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getCategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  426: 436 */         .getProducto().getSubcategoriaProducto().getCategoriaProducto().getCodigo()))
/*  427:     */       {
/*  428: 438 */         criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionCategoriaProducto.get(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getCategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  429: 439 */           .getProducto().getSubcategoriaProducto().getCategoriaProducto().getCodigo());
/*  430:     */       }
/*  431: 441 */       else if (hashCriterioContabilizacionSubCategoriaProducto.containsKey(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  432: 442 */         .getProducto().getSubcategoriaProducto().getCodigo()))
/*  433:     */       {
/*  434: 444 */         criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionSubCategoriaProducto.get(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  435: 445 */           .getProducto().getSubcategoriaProducto().getCodigo());
/*  436:     */       }
/*  437:     */       else
/*  438:     */       {
/*  439: 447 */         List<CriterioContabilizacion> listCCNulls = new ArrayList();
/*  440: 448 */         for (CriterioContabilizacion cd : hashCriterioContabilizacionNull.values()) {
/*  441: 449 */           listCCNulls.add(cd);
/*  442:     */         }
/*  443: 451 */         FuncionesUtiles.ordenaLista(listCCNulls, "orden");
/*  444: 452 */         if (listCCNulls.size() > 0) {
/*  445: 453 */           criterioContabilizacion = (CriterioContabilizacion)listCCNulls.get(0);
/*  446:     */         }
/*  447:     */       }
/*  448: 455 */       if ((criterioContabilizacion != null) && (detallePedidoProveedor.getDimensionContable5() != null))
/*  449:     */       {
/*  450: 457 */         DetallePresupuesto detallePresupuesto = (DetallePresupuesto)((Map)hashDetallesPresupuesto).get(criterioContabilizacion.getCuentaContable().getId() + "~" + detallePedidoProveedor.getDimensionContable5().getId());
/*  451: 458 */         if (detallePresupuesto != null) {
/*  452: 459 */           if (mes == 1)
/*  453:     */           {
/*  454: 461 */             validarDetallePresupuesto(detallePresupuesto.getValorEnero(), detallePresupuesto.getTransferenciasIngresoEnero(), detallePresupuesto
/*  455: 462 */               .getTransferenciasEgresoEnero(), detallePresupuesto.getIncrementosEnero(), detallePresupuesto
/*  456: 463 */               .getDecrementosEnero(), detallePresupuesto.getSaldoRealEnero(), detallePedidoProveedor
/*  457: 464 */               .getPrecioLinea());
/*  458:     */             
/*  459: 466 */             detallePresupuesto.setSaldoRealEnero(detallePresupuesto.getSaldoRealEnero().add(detallePedidoProveedor.getPrecioLinea()));
/*  460:     */           }
/*  461: 467 */           else if (mes == 2)
/*  462:     */           {
/*  463: 468 */             validarDetallePresupuesto(detallePresupuesto.getValorFebrero(), detallePresupuesto.getTransferenciasIngresoFebrero(), detallePresupuesto
/*  464: 469 */               .getTransferenciasEgresoFebrero(), detallePresupuesto.getIncrementosFebrero(), detallePresupuesto
/*  465: 470 */               .getDecrementosFebrero(), detallePresupuesto.getSaldoRealFebrero(), detallePedidoProveedor
/*  466: 471 */               .getPrecioLinea());
/*  467:     */             
/*  468: 473 */             detallePresupuesto.setSaldoRealFebrero(detallePresupuesto.getSaldoRealFebrero().add(detallePedidoProveedor.getPrecioLinea()));
/*  469:     */           }
/*  470: 474 */           else if (mes == 3)
/*  471:     */           {
/*  472: 475 */             validarDetallePresupuesto(detallePresupuesto.getValorMarzo(), detallePresupuesto.getTransferenciasIngresoMarzo(), detallePresupuesto
/*  473: 476 */               .getTransferenciasEgresoMarzo(), detallePresupuesto.getIncrementosMarzo(), detallePresupuesto
/*  474: 477 */               .getDecrementosMarzo(), detallePresupuesto.getSaldoRealMarzo(), detallePedidoProveedor
/*  475: 478 */               .getPrecioLinea());
/*  476:     */             
/*  477: 480 */             detallePresupuesto.setSaldoRealMarzo(detallePresupuesto.getSaldoRealMarzo().add(detallePedidoProveedor.getPrecioLinea()));
/*  478:     */           }
/*  479: 481 */           else if (mes == 4)
/*  480:     */           {
/*  481: 482 */             validarDetallePresupuesto(detallePresupuesto.getValorAbril(), detallePresupuesto.getTransferenciasIngresoAbril(), detallePresupuesto
/*  482: 483 */               .getTransferenciasEgresoAbril(), detallePresupuesto.getIncrementosAbril(), detallePresupuesto
/*  483: 484 */               .getDecrementosAbril(), detallePresupuesto.getSaldoRealAbril(), detallePedidoProveedor
/*  484: 485 */               .getPrecioLinea());
/*  485:     */             
/*  486: 487 */             detallePresupuesto.setSaldoRealAbril(detallePresupuesto.getSaldoRealAbril().add(detallePedidoProveedor.getPrecioLinea()));
/*  487:     */           }
/*  488: 488 */           else if (mes == 5)
/*  489:     */           {
/*  490: 489 */             validarDetallePresupuesto(detallePresupuesto.getValorMayo(), detallePresupuesto.getTransferenciasIngresoMayo(), detallePresupuesto
/*  491: 490 */               .getTransferenciasEgresoMayo(), detallePresupuesto.getIncrementosMayo(), detallePresupuesto
/*  492: 491 */               .getDecrementosMayo(), detallePresupuesto.getSaldoRealMayo(), detallePedidoProveedor
/*  493: 492 */               .getPrecioLinea());
/*  494:     */             
/*  495: 494 */             detallePresupuesto.setSaldoRealMayo(detallePresupuesto.getSaldoRealMayo().add(detallePedidoProveedor.getPrecioLinea()));
/*  496:     */           }
/*  497: 495 */           else if (mes == 6)
/*  498:     */           {
/*  499: 496 */             validarDetallePresupuesto(detallePresupuesto.getValorJunio(), detallePresupuesto.getTransferenciasIngresoJunio(), detallePresupuesto
/*  500: 497 */               .getTransferenciasEgresoJunio(), detallePresupuesto.getIncrementosJunio(), detallePresupuesto
/*  501: 498 */               .getDecrementosJunio(), detallePresupuesto.getSaldoRealJunio(), detallePedidoProveedor
/*  502: 499 */               .getPrecioLinea());
/*  503:     */             
/*  504: 501 */             detallePresupuesto.setSaldoRealJunio(detallePresupuesto.getSaldoRealJunio().add(detallePedidoProveedor.getPrecioLinea()));
/*  505:     */           }
/*  506: 502 */           else if (mes == 7)
/*  507:     */           {
/*  508: 503 */             validarDetallePresupuesto(detallePresupuesto.getValorJulio(), detallePresupuesto.getTransferenciasIngresoJulio(), detallePresupuesto
/*  509: 504 */               .getTransferenciasEgresoJulio(), detallePresupuesto.getIncrementosJulio(), detallePresupuesto
/*  510: 505 */               .getDecrementosJulio(), detallePresupuesto.getSaldoRealJulio(), detallePedidoProveedor
/*  511: 506 */               .getPrecioLinea());
/*  512:     */             
/*  513: 508 */             detallePresupuesto.setSaldoRealJulio(detallePresupuesto.getSaldoRealJulio().add(detallePedidoProveedor.getPrecioLinea()));
/*  514:     */           }
/*  515: 509 */           else if (mes == 8)
/*  516:     */           {
/*  517: 510 */             validarDetallePresupuesto(detallePresupuesto.getValorAgosto(), detallePresupuesto.getTransferenciasIngresoAgosto(), detallePresupuesto
/*  518: 511 */               .getTransferenciasEgresoAgosto(), detallePresupuesto.getIncrementosAgosto(), detallePresupuesto
/*  519: 512 */               .getDecrementosAgosto(), detallePresupuesto.getSaldoRealAgosto(), detallePedidoProveedor
/*  520: 513 */               .getPrecioLinea());
/*  521:     */             
/*  522: 515 */             detallePresupuesto.setSaldoRealAgosto(detallePresupuesto.getSaldoRealAgosto().add(detallePedidoProveedor.getPrecioLinea()));
/*  523:     */           }
/*  524: 516 */           else if (mes == 9)
/*  525:     */           {
/*  526: 517 */             validarDetallePresupuesto(detallePresupuesto.getValorSeptiembre(), detallePresupuesto.getTransferenciasIngresoSeptiembre(), detallePresupuesto
/*  527: 518 */               .getTransferenciasEgresoSeptiembre(), detallePresupuesto.getIncrementosSeptiembre(), detallePresupuesto
/*  528: 519 */               .getDecrementosSeptiembre(), detallePresupuesto.getSaldoRealSeptiembre(), detallePedidoProveedor
/*  529: 520 */               .getPrecioLinea());
/*  530:     */             
/*  531: 522 */             detallePresupuesto
/*  532: 523 */               .setSaldoRealSeptiembre(detallePresupuesto.getSaldoRealSeptiembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  533:     */           }
/*  534: 524 */           else if (mes == 10)
/*  535:     */           {
/*  536: 525 */             validarDetallePresupuesto(detallePresupuesto.getValorOctubre(), detallePresupuesto.getTransferenciasIngresoOctubre(), detallePresupuesto
/*  537: 526 */               .getTransferenciasEgresoOctubre(), detallePresupuesto.getIncrementosOctubre(), detallePresupuesto
/*  538: 527 */               .getDecrementosOctubre(), detallePresupuesto.getSaldoRealOctubre(), detallePedidoProveedor
/*  539: 528 */               .getPrecioLinea());
/*  540:     */             
/*  541: 530 */             detallePresupuesto.setSaldoRealOctubre(detallePresupuesto.getSaldoRealOctubre().add(detallePedidoProveedor.getPrecioLinea()));
/*  542:     */           }
/*  543: 531 */           else if (mes == 11)
/*  544:     */           {
/*  545: 532 */             validarDetallePresupuesto(detallePresupuesto.getValorNoviembre(), detallePresupuesto.getTransferenciasIngresoNoviembre(), detallePresupuesto
/*  546: 533 */               .getTransferenciasEgresoNoviembre(), detallePresupuesto.getIncrementosNoviembre(), detallePresupuesto
/*  547: 534 */               .getDecrementosNoviembre(), detallePresupuesto.getSaldoRealNoviembre(), detallePedidoProveedor
/*  548: 535 */               .getPrecioLinea());
/*  549:     */             
/*  550: 537 */             detallePresupuesto
/*  551: 538 */               .setSaldoRealNoviembre(detallePresupuesto.getSaldoRealNoviembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  552:     */           }
/*  553: 539 */           else if (mes == 12)
/*  554:     */           {
/*  555: 540 */             validarDetallePresupuesto(detallePresupuesto.getValorDiciembre(), detallePresupuesto.getTransferenciasIngresoDiciembre(), detallePresupuesto
/*  556: 541 */               .getTransferenciasEgresoDiciembre(), detallePresupuesto.getIncrementosDiciembre(), detallePresupuesto
/*  557: 542 */               .getDecrementosDiciembre(), detallePresupuesto.getSaldoRealDiciembre(), detallePedidoProveedor
/*  558: 543 */               .getPrecioLinea());
/*  559:     */             
/*  560: 545 */             detallePresupuesto
/*  561: 546 */               .setSaldoRealDiciembre(detallePresupuesto.getSaldoRealDiciembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  562:     */           }
/*  563:     */         }
/*  564:     */       }
/*  565:     */     }
/*  566: 551 */     this.servicioPresupuesto.guardar(presupuesto);
/*  567:     */   }
/*  568:     */   
/*  569:     */   private void validarDetallePresupuesto(BigDecimal valorInicial, BigDecimal transferenciasI, BigDecimal transferenciasE, BigDecimal incrementos, BigDecimal decrementos, BigDecimal saldoComprometidoActual, BigDecimal valorAComparar)
/*  570:     */     throws ExcepcionAS2
/*  571:     */   {
/*  572: 557 */     BigDecimal result = valorInicial.add(transferenciasI).add(incrementos).subtract(decrementos).subtract(transferenciasI).subtract(saldoComprometidoActual);
/*  573: 558 */     if (result.compareTo(valorAComparar) < 0) {
/*  574: 559 */       throw new ExcepcionAS2("msg_error_excedio_monto_presupuestado", "     " + valorAComparar.toString() + ">" + result.toString());
/*  575:     */     }
/*  576:     */   }
/*  577:     */   
/*  578:     */   private void eliminarInventariosProducto(RecepcionProveedor recepcionProveedor)
/*  579:     */   {
/*  580: 564 */     for (DetalleRecepcionProveedor detalle : recepcionProveedor.getListaDetalleRecepcionProveedor())
/*  581:     */     {
/*  582: 565 */       if ((detalle.getInventarioProducto() != null) && (detalle.getInventarioProducto().getLote() != null)) {
/*  583: 566 */         detalle.setLote(detalle.getInventarioProducto().getLote());
/*  584:     */       }
/*  585: 568 */       detalle.setInventarioProducto(null);
/*  586:     */     }
/*  587:     */   }
/*  588:     */   
/*  589:     */   public void agregarInventariosProducto(RecepcionProveedor recepcionProveedor)
/*  590:     */   {
/*  591: 574 */     for (DetalleRecepcionProveedor detalle : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/*  592: 575 */       if ((!detalle.isEliminado()) && (detalle.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO))) {
/*  593: 576 */         if (detalle.getInventarioProducto() == null)
/*  594:     */         {
/*  595: 577 */           InventarioProducto inventarioProducto = new InventarioProducto();
/*  596: 578 */           inventarioProducto.setOperacion(recepcionProveedor.getDocumento().getOperacion());
/*  597: 579 */           inventarioProducto.setProducto(detalle.getProducto());
/*  598: 580 */           detalle.setInventarioProducto(inventarioProducto);
/*  599: 581 */           if (detalle.getLote() != null) {
/*  600: 582 */             inventarioProducto.setLote(detalle.getLote());
/*  601:     */           }
/*  602:     */         }
/*  603:     */         else
/*  604:     */         {
/*  605: 585 */           detalle.setLote(detalle.getInventarioProducto().getLote());
/*  606:     */         }
/*  607:     */       }
/*  608:     */     }
/*  609:     */   }
/*  610:     */   
/*  611:     */   private void eliminarDetallesCero(RecepcionProveedor recepcionProveedor)
/*  612:     */   {
/*  613: 592 */     for (DetalleRecepcionProveedor detalle : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/*  614: 593 */       if (detalle.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
/*  615: 594 */         detalle.setEliminado(true);
/*  616:     */       }
/*  617:     */     }
/*  618:     */   }
/*  619:     */   
/*  620:     */   private void actualizarInventarioProducto(RecepcionProveedor recepcionProveedor)
/*  621:     */     throws ExcepcionAS2
/*  622:     */   {
/*  623: 601 */     InventarioProducto inventarioProducto = null;
/*  624: 603 */     for (Iterator localIterator = recepcionProveedor.getListaDetalleRecepcionProveedor().iterator(); localIterator.hasNext();)
/*  625:     */     {
/*  626: 603 */       drp = (DetalleRecepcionProveedor)localIterator.next();
/*  627: 604 */       if ((drp.getDetalleFacturaProveedor() != null) && (drp.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO))) {
/*  628: 606 */         drp.getDetalleFacturaProveedor().setTraCantidadRecibida(drp.getDetalleFacturaProveedor().getTraCantidadRecibida().add(drp.getCantidad()));
/*  629:     */       }
/*  630:     */     }
/*  631:     */     DetalleRecepcionProveedor drp;
/*  632: 609 */     boolean contabilizaConPresupueto = ParametrosSistema.isContabilizaImportacionBasadaPresupuesto(recepcionProveedor.getIdOrganizacion()).booleanValue();
/*  633: 611 */     for (DetalleRecepcionProveedor drp : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/*  634: 612 */       if ((!drp.isEliminado()) && (drp.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO)))
/*  635:     */       {
/*  636: 613 */         if (drp.getInventarioProducto() != null)
/*  637:     */         {
/*  638: 614 */           inventarioProducto = drp.getInventarioProducto();
/*  639:     */         }
/*  640:     */         else
/*  641:     */         {
/*  642: 616 */           inventarioProducto = new InventarioProducto();
/*  643: 617 */           drp.setInventarioProducto(inventarioProducto);
/*  644:     */         }
/*  645: 619 */         inventarioProducto.setDetalleRecepcionProveedor(drp);
/*  646:     */         
/*  647:     */ 
/*  648: 622 */         BigDecimal cantidad = drp.getCantidad();
/*  649: 623 */         cantidad = this.servicioProducto.convierteUnidad(drp.getUnidadCompra(), drp.getProducto().getUnidad(), drp.getProducto().getIdProducto(), drp
/*  650: 624 */           .getCantidad());
/*  651:     */         
/*  652: 626 */         inventarioProducto.setCantidad(cantidad);
/*  653: 627 */         inventarioProducto.setCantidadDocumento(drp.getCantidad());
/*  654: 628 */         inventarioProducto.setUnidadDocumento(drp.getUnidadCompra().getNombre());
/*  655:     */         
/*  656: 630 */         inventarioProducto.setFecha(recepcionProveedor.getFecha());
/*  657: 631 */         inventarioProducto.setDocumento(recepcionProveedor.getDocumento());
/*  658: 632 */         inventarioProducto.setIdOrganizacion(recepcionProveedor.getIdOrganizacion());
/*  659: 633 */         inventarioProducto.setOperacion(recepcionProveedor.getDocumento().getOperacion());
/*  660: 634 */         inventarioProducto.setIndicadorGeneraCosto(recepcionProveedor.getDocumento().isIndicadorGeneraCosto());
/*  661: 635 */         inventarioProducto.setIdSucursal(recepcionProveedor.getSucursal().getId());
/*  662: 636 */         inventarioProducto.setBodega(drp.getBodega());
/*  663: 637 */         inventarioProducto.setProducto(drp.getProducto());
/*  664: 638 */         inventarioProducto.setNumeroDocumento(recepcionProveedor.getNumero() + (recepcionProveedor
/*  665: 639 */           .getFacturaProveedor() != null ? "/" + recepcionProveedor.getFacturaProveedor().getNumero() : ""));
/*  666: 640 */         inventarioProducto.setProyecto(recepcionProveedor.getProyecto());
/*  667: 642 */         if (drp.getDetalleFacturaProveedor() != null)
/*  668:     */         {
/*  669:     */           BigDecimal costoLinea;
/*  670: 644 */           if (drp.getDetalleFacturaProveedor().getFacturaProveedor().getDocumento().isIndicadorDocumentoExterior())
/*  671:     */           {
/*  672:     */             BigDecimal costoLinea;
/*  673: 645 */             if ((contabilizaConPresupueto) && (recepcionProveedor.getFechaContabilizacionImportacion() == null)) {
/*  674: 646 */               costoLinea = drp.getDetalleFacturaProveedor().getGastoPresupuesto();
/*  675:     */             } else {
/*  676: 648 */               costoLinea = drp.getDetalleFacturaProveedor().getGastoReal();
/*  677:     */             }
/*  678:     */           }
/*  679:     */           else
/*  680:     */           {
/*  681: 651 */             costoLinea = drp.getDetalleFacturaProveedor().getBaseImponible();
/*  682: 652 */             if (!drp.getDetalleFacturaProveedor().getFacturaProveedor().isIndicadorCreditoTributario()) {
/*  683: 653 */               costoLinea = costoLinea.add(drp.getDetalleFacturaProveedor().getValorImpuestosLinea());
/*  684:     */             }
/*  685:     */           }
/*  686: 656 */           BigDecimal costoLinea = costoLinea.divide(drp.getDetalleFacturaProveedor().getTraCantidadRecibida(), 18, RoundingMode.HALF_UP);
/*  687:     */           
/*  688: 658 */           inventarioProducto.setCosto(costoLinea.multiply(drp.getCantidad()).setScale(4, RoundingMode.HALF_UP));
/*  689:     */         }
/*  690: 659 */         else if (drp.getDetallePedidoProveedor() != null)
/*  691:     */         {
/*  692: 660 */           inventarioProducto.setCosto(drp.getDetallePedidoProveedor().getBaseImponible().multiply(drp.getCantidad())
/*  693: 661 */             .divide(drp.getDetallePedidoProveedor().getCantidad(), 4, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP));
/*  694:     */         }
/*  695:     */       }
/*  696:     */     }
/*  697:     */   }
/*  698:     */   
/*  699:     */   private void cargarSecuencia(RecepcionProveedor recepcionProveedor)
/*  700:     */     throws ExcepcionAS2
/*  701:     */   {
/*  702: 674 */     if ((recepcionProveedor.getNumero() == null) || (recepcionProveedor.getNumero().equals("")))
/*  703:     */     {
/*  704: 675 */       String numero = "";
/*  705: 676 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(recepcionProveedor.getDocumento().getId(), recepcionProveedor.getFecha());
/*  706: 677 */       recepcionProveedor.setNumero(numero);
/*  707:     */     }
/*  708:     */   }
/*  709:     */   
/*  710:     */   public void eliminar(RecepcionProveedor recepcionProveedor)
/*  711:     */     throws ExcepcionAS2
/*  712:     */   {
/*  713: 688 */     for (DetalleRecepcionProveedor detalleRecepcionProveedor : recepcionProveedor.getListaDetalleRecepcionProveedor())
/*  714:     */     {
/*  715: 690 */       detalleRecepcionProveedor.setDetallePedidoProveedor(null);
/*  716:     */       
/*  717: 692 */       this.detalleRecepcionProveedorDao.eliminar(detalleRecepcionProveedor);
/*  718:     */     }
/*  719: 694 */     this.recepcionProveedorDao.eliminar(recepcionProveedor);
/*  720:     */   }
/*  721:     */   
/*  722:     */   public RecepcionProveedor buscarPorId(Integer idRecepcionProveedor)
/*  723:     */     throws ExcepcionAS2
/*  724:     */   {
/*  725: 704 */     return (RecepcionProveedor)this.recepcionProveedorDao.buscarPorId(idRecepcionProveedor);
/*  726:     */   }
/*  727:     */   
/*  728:     */   public RecepcionProveedor cargarDetalle(RecepcionProveedor recepcionProveedor)
/*  729:     */   {
/*  730: 714 */     return this.recepcionProveedorDao.cargarDetalle(recepcionProveedor);
/*  731:     */   }
/*  732:     */   
/*  733:     */   public List<RecepcionProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  734:     */   {
/*  735: 726 */     return this.recepcionProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  736:     */   }
/*  737:     */   
/*  738:     */   private void validar(RecepcionProveedor recepcionProveedor, boolean indicadorModificaPrecio)
/*  739:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Inventario, AS2Exception
/*  740:     */   {
/*  741: 742 */     Date fechaAComparar = recepcionProveedor.getFechaContabilizacionImportacion() != null ? recepcionProveedor.getFechaContabilizacionImportacion() : recepcionProveedor.getFecha();
/*  742: 743 */     this.servicioPeriodo.buscarPorFecha(fechaAComparar, recepcionProveedor.getIdOrganizacion(), recepcionProveedor.getDocumento().getDocumentoBase());
/*  743: 745 */     if ((recepcionProveedor.getPedidoProveedor() != null) && 
/*  744: 746 */       (recepcionProveedor.getFecha().compareTo(recepcionProveedor.getPedidoProveedor().getFecha()) < 0)) {
/*  745: 748 */       throw new ExcepcionAS2Financiero("msgs_error_fecha_menor_a_registro_generado", "Pedido: " + recepcionProveedor.getPedidoProveedor().getNumero());
/*  746:     */     }
/*  747: 751 */     for (DetalleRecepcionProveedor ddc : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/*  748: 752 */       if (!ddc.isEliminado())
/*  749:     */       {
/*  750: 754 */         if ((ddc.getProducto().getTipoProducto() != TipoProducto.ARTICULO) && 
/*  751: 755 */           (ddc.getInventarioProducto() != null))
/*  752:     */         {
/*  753: 756 */           ddc.getInventarioProducto().setEliminado(true);
/*  754: 757 */           ddc.setInventarioProducto(null);
/*  755:     */         }
/*  756: 760 */         validacionCantidadRecepcion(ddc, recepcionProveedor, indicadorModificaPrecio);
/*  757:     */       }
/*  758:     */     }
/*  759: 764 */     if ((recepcionProveedor.getFacturaProveedor() != null) && 
/*  760: 765 */       (DocumentoBase.PEDIDO_IMPORTACION.equals(recepcionProveedor.getFacturaProveedor().getDocumento())) && 
/*  761: 766 */       (ParametrosSistema.isContabilizaImportacionBasadaPresupuesto(recepcionProveedor.getIdOrganizacion()).booleanValue()) && 
/*  762: 767 */       (!this.facturaProveedorImportacionDao.tieneGastoFacturaExterior(recepcionProveedor.getFacturaProveedor().getId()))) {
/*  763: 768 */       throw new AS2Exception("msg_error_ingresar_gasto_factura_exterior", new String[] { "" });
/*  764:     */     }
/*  765: 779 */     this.servicioSerieInventarioProducto.validar(recepcionProveedor);
/*  766:     */   }
/*  767:     */   
/*  768:     */   public void contabilizar(RecepcionProveedor recepcionProveedor)
/*  769:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  770:     */   {
/*  771: 790 */     contabilizar(recepcionProveedor, null);
/*  772:     */   }
/*  773:     */   
/*  774:     */   public void contabilizar(RecepcionProveedor recepcionProveedor, FacturaProveedor notaCreditoProveedor)
/*  775:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  776:     */   {
/*  777: 796 */     boolean contabiliza = true;
/*  778: 797 */     int idOrganizacion = recepcionProveedor != null ? recepcionProveedor.getIdOrganizacion() : notaCreditoProveedor.getIdOrganizacion();
/*  779: 798 */     if (recepcionProveedor != null) {
/*  780: 799 */       contabiliza = recepcionProveedor.getDocumento().isIndicadorContabilizar();
/*  781: 800 */     } else if (notaCreditoProveedor != null) {
/*  782: 801 */       contabiliza = notaCreditoProveedor.getDocumento().isIndicadorContabilizar();
/*  783:     */     }
/*  784: 803 */     if (contabiliza)
/*  785:     */     {
/*  786: 804 */       Date fechaContabilizacion = recepcionProveedor != null ? recepcionProveedor.getFecha() : notaCreditoProveedor.getFecha();
/*  787:     */       
/*  788: 806 */       boolean contabilizaConPresupueto = ParametrosSistema.isContabilizaImportacionBasadaPresupuesto(idOrganizacion).booleanValue();
/*  789: 807 */       boolean creaNuevoAsiento = false;
/*  790:     */       String fechaRecepcion;
/*  791: 808 */       if ((recepcionProveedor != null) && (recepcionProveedor.getFacturaProveedor() != null) && 
/*  792: 809 */         (recepcionProveedor.getFechaContabilizacionImportacion() != null))
/*  793:     */       {
/*  794: 810 */         fechaRecepcion = FuncionesUtiles.getMes(fechaContabilizacion) + "~" + FuncionesUtiles.getAnio(fechaContabilizacion);
/*  795:     */         
/*  796: 812 */         String fechaContabilizacionImportacion = FuncionesUtiles.getMes(recepcionProveedor.getFechaContabilizacionImportacion()) + "~" + FuncionesUtiles.getAnio(recepcionProveedor.getFechaContabilizacionImportacion());
/*  797: 813 */         creaNuevoAsiento = (!fechaRecepcion.equals(fechaContabilizacionImportacion)) && (contabilizaConPresupueto);
/*  798:     */       }
/*  799: 817 */       if (creaNuevoAsiento)
/*  800:     */       {
/*  801: 818 */         verificarDifereciasAsientoImportacion(recepcionProveedor);
/*  802: 819 */         return;
/*  803:     */       }
/*  804: 820 */       if ((recepcionProveedor != null) && (recepcionProveedor.getFacturaProveedor() != null) && 
/*  805: 821 */         (recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion() != null) && 
/*  806: 822 */         (recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion().getAsiento() != null))
/*  807:     */       {
/*  808: 823 */         this.servicioAsiento.anular(recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion().getAsiento());
/*  809: 824 */         recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion().setAsiento(null);
/*  810: 825 */         this.facturaProveedorImportacionDao.guardar(recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion());
/*  811:     */       }
/*  812:     */       Asiento asiento;
/*  813: 827 */       if ((recepcionProveedor != null) && (recepcionProveedor.getAsiento() != null))
/*  814:     */       {
/*  815: 828 */         Asiento asiento = recepcionProveedor.getAsiento();
/*  816: 829 */         asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/*  817: 830 */         for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  818: 831 */           detalleAsiento.setEliminado(true);
/*  819:     */         }
/*  820:     */       }
/*  821: 833 */       else if ((notaCreditoProveedor != null) && (notaCreditoProveedor.getAsiento() != null))
/*  822:     */       {
/*  823: 834 */         Asiento asiento = notaCreditoProveedor.getAsiento();
/*  824: 835 */         asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/*  825: 836 */         for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  826: 837 */           detalleAsiento.setEliminado(true);
/*  827:     */         }
/*  828:     */       }
/*  829:     */       else
/*  830:     */       {
/*  831: 840 */         asiento = new Asiento();
/*  832: 841 */         asiento.setEstado(Estado.ELABORADO);
/*  833: 842 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  834: 843 */         asiento.setSucursal(AppUtil.getSucursal());
/*  835:     */         
/*  836:     */ 
/*  837: 846 */         TipoAsiento tipoAsiento = recepcionProveedor != null ? recepcionProveedor.getDocumento().getTipoAsiento() : notaCreditoProveedor.getDocumento().getTipoAsiento();
/*  838: 847 */         asiento.setTipoAsiento(tipoAsiento);
/*  839:     */       }
/*  840: 851 */       String numeroImportacion = (recepcionProveedor != null) && (recepcionProveedor.getNumeroImportacion() != null) ? recepcionProveedor.getNumeroImportacion() : "";
/*  841: 852 */       String concepto = "";
/*  842: 853 */       if (recepcionProveedor != null) {
/*  843: 855 */         concepto = recepcionProveedor.getEmpresa().getNombreFiscal() + " - " + recepcionProveedor.getDocumento().getNombre().trim() + " # " + recepcionProveedor.getNumero() + "  " + numeroImportacion;
/*  844:     */       } else {
/*  845: 858 */         concepto = notaCreditoProveedor.getEmpresa().getNombreFiscal() + " - " + notaCreditoProveedor.getDocumento().getNombre().trim() + " # " + notaCreditoProveedor.getNumero();
/*  846:     */       }
/*  847: 860 */       asiento.setConcepto(concepto);
/*  848: 861 */       asiento.setFecha(fechaContabilizacion);
/*  849:     */       
/*  850: 863 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/*  851: 864 */       List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/*  852: 865 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(idOrganizacion, DocumentoBase.RECEPCION_BODEGA);
/*  853:     */       
/*  854: 867 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/*  855:     */       DetalleInterfazContable dicAux;
/*  856: 869 */       if ((recepcionProveedor != null) && (recepcionProveedor.getFacturaProveedor() != null) && 
/*  857: 870 */         (recepcionProveedor.getFacturaProveedor().getDocumento().isIndicadorDocumentoExterior()))
/*  858:     */       {
/*  859: 872 */         listaDA = this.recepcionProveedorDao.getRedepcionProveedorCCFPIIC(recepcionProveedor
/*  860: 873 */           .getFacturaProveedor().getFacturaProveedorImportacion().getCuentaContableImportacion().getId(), recepcionProveedor);
/*  861: 876 */         if (recepcionProveedor.getTotalCosto() != null)
/*  862:     */         {
/*  863: 877 */           BigDecimal valorActualCosto = BigDecimal.ZERO;
/*  864: 878 */           dicAux = null;
/*  865: 880 */           for (DetalleInterfazContable dic : listaDA)
/*  866:     */           {
/*  867: 881 */             valorActualCosto = dic.getValor().negate();
/*  868: 882 */             dicAux = dic;
/*  869:     */           }
/*  870: 885 */           CuentaContable cuentaContableMasOMenos = null;
/*  871: 887 */           if (valorActualCosto.compareTo(recepcionProveedor.getTotalCosto()) != 0)
/*  872:     */           {
/*  873: 890 */             cuentaContableMasOMenos = recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion().getCuentaContableImportacionDiferenciaEnMasOEnMenos();
/*  874:     */             
/*  875:     */ 
/*  876: 893 */             DetalleInterfazContable detalleInterfazContable = new DetalleInterfazContable(Integer.valueOf(cuentaContableMasOMenos.getIdCuentaContable()), dicAux.getReferencia1(), dicAux.getReferencia2(), dicAux.getReferencia3(), valorActualCosto.subtract(recepcionProveedor.getTotalCosto()).setScale(2, RoundingMode.HALF_UP).negate());
/*  877: 894 */             listaDA.add(detalleInterfazContable);
/*  878: 895 */             dicAux.setValor(FuncionesUtiles.redondearBigDecimal(recepcionProveedor.getTotalCosto().negate(), 2));
/*  879:     */           }
/*  880:     */         }
/*  881:     */       }
/*  882: 903 */       super.generarAsiento(asiento, listaDA, recepcionProveedor != null ? recepcionProveedor
/*  883: 904 */         .getDocumento() : notaCreditoProveedor.getDocumento());
/*  884:     */       
/*  885: 906 */       List<DocumentoContabilizacion> listDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(idOrganizacion, DocumentoBase.RECEPCION_BODEGA);
/*  886: 907 */       listDocumentoContabilizacion.addAll(this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(idOrganizacion, DocumentoBase.FACTURA_PROVEEDOR, ProcesoContabilizacionEnum.SERVICIOS_GASTOS));
/*  887: 910 */       for (DocumentoContabilizacion documentoContabilizacion : listDocumentoContabilizacion) {
/*  888: 911 */         if ((recepcionProveedor == null) || (recepcionProveedor.getFacturaProveedor() == null) || 
/*  889: 912 */           (!recepcionProveedor.getFacturaProveedor().getDocumento().isIndicadorDocumentoExterior()) || 
/*  890: 913 */           (!documentoContabilizacion.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.MERCADERIA_POR_RECIBIR))) {
/*  891: 914 */           if (ProcesoContabilizacionEnum.PROVISION_GASTOS.equals(documentoContabilizacion.getProcesoContabilizacion()))
/*  892:     */           {
/*  893: 915 */             lista = this.recepcionProveedorDao.getInterfazRecepcionProveedorDimensiones(recepcionProveedor, TipoProducto.SERVICIO, notaCreditoProveedor);
/*  894:     */             
/*  895: 917 */             listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/*  896:     */           }
/*  897: 919 */           else if (ProcesoContabilizacionEnum.SERVICIOS_GASTOS.equals(documentoContabilizacion.getProcesoContabilizacion()))
/*  898:     */           {
/*  899: 920 */             lista = this.recepcionProveedorDao.getInterfazRecepcionProveedorDimensiones(recepcionProveedor, TipoProducto.SERVICIO, notaCreditoProveedor);
/*  900:     */             
/*  901: 922 */             listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/*  902:     */           }
/*  903:     */           else
/*  904:     */           {
/*  905: 925 */             lista = this.recepcionProveedorDao.getInterfazRecepcionProveedorDimensiones(recepcionProveedor, TipoProducto.ARTICULO, notaCreditoProveedor);
/*  906:     */             
/*  907: 927 */             listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/*  908:     */           }
/*  909:     */         }
/*  910:     */       }
/*  911: 932 */       asiento.getListaDetalleAsiento().addAll(listaDetalleAsiento);
/*  912: 933 */       for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  913: 934 */         System.out.println("CuentaContable: " + detalleAsiento.getCuentaContable().getId() + "Debe: " + detalleAsiento.getDebe() + "Haber: " + detalleAsiento
/*  914: 935 */           .getHaber());
/*  915:     */       }
/*  916: 941 */       for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento())
/*  917:     */       {
/*  918: 942 */         String descripcion = "";
/*  919: 943 */         if (recepcionProveedor != null) {
/*  920: 945 */           descripcion = recepcionProveedor.getEmpresa().getNombreFiscal() + " - " + recepcionProveedor.getDocumento().getNombre().trim() + " # " + recepcionProveedor.getNumero() + "  " + numeroImportacion;
/*  921:     */         } else {
/*  922: 948 */           descripcion = notaCreditoProveedor.getEmpresa().getNombreFiscal() + " - " + notaCreditoProveedor.getDocumento().getNombre().trim() + " # " + notaCreditoProveedor.getNumero() + "  " + numeroImportacion;
/*  923:     */         }
/*  924: 950 */         detalleAsiento.setDescripcion(descripcion.length() > 200 ? descripcion.substring(0, 200) : descripcion);
/*  925:     */       }
/*  926: 954 */       this.servicioAsiento.guardar(asiento);
/*  927: 956 */       if (recepcionProveedor != null)
/*  928:     */       {
/*  929: 958 */         recepcionProveedor.setFechaContabilizacion(fechaContabilizacion);
/*  930: 959 */         recepcionProveedor.setAsiento(asiento);
/*  931:     */       }
/*  932:     */       else
/*  933:     */       {
/*  934: 961 */         notaCreditoProveedor.setFechaContabilizacion(fechaContabilizacion);
/*  935: 962 */         notaCreditoProveedor.setAsiento(asiento);
/*  936:     */       }
/*  937:     */     }
/*  938:     */   }
/*  939:     */   
/*  940:     */   private void verificarDifereciasAsientoImportacion(RecepcionProveedor recepcionProveedor)
/*  941:     */     throws AS2Exception, ExcepcionAS2
/*  942:     */   {
/*  943: 969 */     Date fechaContabilizacion = recepcionProveedor.getFechaContabilizacionImportacion();
/*  944:     */     
/*  945:     */ 
/*  946: 972 */     Asiento asientoRecepcion = recepcionProveedor.getAsiento();
/*  947: 973 */     asientoRecepcion = this.servicioAsiento.cargarDetalle(asientoRecepcion.getId());
/*  948:     */     Asiento asiento;
/*  949: 975 */     if (recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion().getAsiento() != null)
/*  950:     */     {
/*  951: 976 */       Asiento asiento = recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion().getAsiento();
/*  952: 977 */       asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/*  953: 978 */       for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/*  954: 979 */         detalleAsiento.setEliminado(true);
/*  955:     */       }
/*  956:     */     }
/*  957:     */     else
/*  958:     */     {
/*  959: 982 */       asiento = new Asiento();
/*  960: 983 */       asiento.setEstado(Estado.ELABORADO);
/*  961: 984 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  962: 985 */       asiento.setSucursal(AppUtil.getSucursal());
/*  963:     */       
/*  964: 987 */       TipoAsiento tipoAsiento = recepcionProveedor.getDocumento().getTipoAsiento();
/*  965: 988 */       asiento.setTipoAsiento(tipoAsiento);
/*  966:     */     }
/*  967: 992 */     String numeroImportacion = recepcionProveedor.getNumeroImportacion() != null ? recepcionProveedor.getNumeroImportacion() : "";
/*  968: 993 */     String concepto = "";
/*  969: 994 */     concepto = recepcionProveedor.getEmpresa().getNombreFiscal() + " - Ajuste de importacion";
/*  970: 995 */     asiento.setConcepto(concepto);
/*  971: 996 */     asiento.setFecha(fechaContabilizacion);
/*  972:     */     
/*  973: 998 */     List<DetalleInterfazContableProceso> lista = new ArrayList();
/*  974: 999 */     List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/*  975:     */     
/*  976:1001 */     List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(recepcionProveedor.getIdOrganizacion(), DocumentoBase.RECEPCION_BODEGA);
/*  977:1002 */     List<DetalleInterfazContable> listaDA = new ArrayList();
/*  978:     */     DetalleInterfazContable dicAux;
/*  979:1004 */     if ((recepcionProveedor.getFacturaProveedor() != null) && 
/*  980:1005 */       (recepcionProveedor.getFacturaProveedor().getDocumento().isIndicadorDocumentoExterior()))
/*  981:     */     {
/*  982:1007 */       listaDA = this.recepcionProveedorDao.getRedepcionProveedorCCFPIIC(recepcionProveedor
/*  983:1008 */         .getFacturaProveedor().getFacturaProveedorImportacion().getCuentaContableImportacion().getId(), recepcionProveedor);
/*  984:1011 */       if (recepcionProveedor.getTotalCosto() != null)
/*  985:     */       {
/*  986:1012 */         BigDecimal valorActualCosto = BigDecimal.ZERO;
/*  987:1013 */         dicAux = null;
/*  988:1015 */         for (DetalleInterfazContable dic : listaDA)
/*  989:     */         {
/*  990:1016 */           valorActualCosto = dic.getValor().negate();
/*  991:1017 */           dicAux = dic;
/*  992:     */         }
/*  993:1020 */         CuentaContable cuentaContableMasOMenos = null;
/*  994:1022 */         if (valorActualCosto.compareTo(recepcionProveedor.getTotalCosto()) != 0)
/*  995:     */         {
/*  996:1025 */           cuentaContableMasOMenos = recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion().getCuentaContableImportacionDiferenciaEnMasOEnMenos();
/*  997:     */           
/*  998:     */ 
/*  999:1028 */           DetalleInterfazContable detalleInterfazContable = new DetalleInterfazContable(Integer.valueOf(cuentaContableMasOMenos.getIdCuentaContable()), dicAux.getReferencia1(), dicAux.getReferencia2(), dicAux.getReferencia3(), valorActualCosto.subtract(recepcionProveedor.getTotalCosto()).setScale(2, RoundingMode.HALF_UP).negate());
/* 1000:1029 */           listaDA.add(detalleInterfazContable);
/* 1001:1030 */           dicAux.setValor(FuncionesUtiles.redondearBigDecimal(recepcionProveedor.getTotalCosto().negate(), 2));
/* 1002:     */         }
/* 1003:     */       }
/* 1004:     */     }
/* 1005:1038 */     super.generarAsiento(asiento, listaDA, recepcionProveedor.getDocumento());
/* 1006:1039 */     List<DocumentoContabilizacion> listDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(recepcionProveedor
/* 1007:1040 */       .getIdOrganizacion(), DocumentoBase.PEDIDO_IMPORTACION, ProcesoContabilizacionEnum.DIFERENCIA_ASIENTO_IMPORTACION);
/* 1008:1042 */     for (DocumentoContabilizacion documentoContabilizacion : listDocumentoContabilizacion) {
/* 1009:1043 */       if ((recepcionProveedor.getFacturaProveedor() == null) || 
/* 1010:1044 */         (!recepcionProveedor.getFacturaProveedor().getDocumento().isIndicadorDocumentoExterior()) || 
/* 1011:1045 */         (!documentoContabilizacion.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.MERCADERIA_POR_RECIBIR))) {
/* 1012:1046 */         if (ProcesoContabilizacionEnum.PROVISION_GASTOS.equals(documentoContabilizacion.getProcesoContabilizacion()))
/* 1013:     */         {
/* 1014:1047 */           lista = this.recepcionProveedorDao.getInterfazRecepcionProveedorDimensiones(recepcionProveedor, TipoProducto.SERVICIO);
/* 1015:1048 */           listaDetalleAsiento.addAll(this.servicioInterfazContableProceso
/* 1016:1049 */             .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 1017:     */         }
/* 1018:1050 */         else if (ProcesoContabilizacionEnum.SERVICIOS_GASTOS.equals(documentoContabilizacion.getProcesoContabilizacion()))
/* 1019:     */         {
/* 1020:1051 */           lista = this.recepcionProveedorDao.getInterfazRecepcionProveedorDimensiones(recepcionProveedor, TipoProducto.SERVICIO);
/* 1021:1052 */           listaDetalleAsiento.addAll(this.servicioInterfazContableProceso
/* 1022:1053 */             .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 1023:     */         }
/* 1024:     */         else
/* 1025:     */         {
/* 1026:1055 */           lista = this.recepcionProveedorDao.getInterfazRecepcionProveedorDimensiones(recepcionProveedor, TipoProducto.ARTICULO);
/* 1027:1056 */           listaDetalleAsiento.addAll(this.servicioInterfazContableProceso
/* 1028:1057 */             .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false));
/* 1029:     */         }
/* 1030:     */       }
/* 1031:     */     }
/* 1032:1061 */     asiento.getListaDetalleAsiento().addAll(listaDetalleAsiento);
/* 1033:1064 */     if (!numeroImportacion.isEmpty()) {
/* 1034:1065 */       for (dicAux = asiento.getListaDetalleAsiento().iterator(); dicAux.hasNext();)
/* 1035:     */       {
/* 1036:1065 */         detalleAsiento = (DetalleAsiento)dicAux.next();
/* 1037:1066 */         String descripcion = recepcionProveedor.getEmpresa().getNombreFiscal() + " - " + ((DetalleAsiento)detalleAsiento).getDescripcion() + "-" + numeroImportacion;
/* 1038:     */         
/* 1039:1068 */         ((DetalleAsiento)detalleAsiento).setDescripcion(descripcion.length() > 200 ? descripcion.substring(0, 200) : descripcion);
/* 1040:     */       }
/* 1041:     */     }
/* 1042:     */     Object detalleAsiento;
/* 1043:1072 */     BigDecimal diferencia = verificarDiferenciasEntreAsientos(asientoRecepcion, asiento);
/* 1044:1073 */     if (diferencia.compareTo(BigDecimal.ZERO) != 0)
/* 1045:     */     {
/* 1046:1075 */       for (detalleAsiento = asiento.getListaDetalleAsiento().iterator(); ((Iterator)detalleAsiento).hasNext();)
/* 1047:     */       {
/* 1048:1075 */         DetalleAsiento det = (DetalleAsiento)((Iterator)detalleAsiento).next();
/* 1049:1076 */         if (diferencia.compareTo(BigDecimal.ZERO) < 0)
/* 1050:     */         {
/* 1051:1077 */           if (det.getDebe().compareTo(BigDecimal.ZERO) != 0)
/* 1052:     */           {
/* 1053:1078 */             det.setHaber(diferencia.negate());
/* 1054:1079 */             det.setDebe(BigDecimal.ZERO);
/* 1055:     */           }
/* 1056:     */           else
/* 1057:     */           {
/* 1058:1081 */             det.setDebe(diferencia.negate());
/* 1059:1082 */             det.setHaber(BigDecimal.ZERO);
/* 1060:     */           }
/* 1061:     */         }
/* 1062:1086 */         else if (det.getDebe().compareTo(BigDecimal.ZERO) != 0)
/* 1063:     */         {
/* 1064:1087 */           det.setHaber(BigDecimal.ZERO);
/* 1065:1088 */           det.setDebe(diferencia);
/* 1066:     */         }
/* 1067:     */         else
/* 1068:     */         {
/* 1069:1090 */           det.setDebe(BigDecimal.ZERO);
/* 1070:1091 */           det.setHaber(diferencia);
/* 1071:     */         }
/* 1072:     */       }
/* 1073:1095 */       this.servicioAsiento.guardar(asiento);
/* 1074:1096 */       recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion().setAsiento(asiento);
/* 1075:1097 */       this.facturaProveedorImportacionDao.guardar(recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion());
/* 1076:     */     }
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   private BigDecimal verificarDiferenciasEntreAsientos(Asiento asientoRecepcion, Asiento asiento)
/* 1080:     */   {
/* 1081:1103 */     BigDecimal debeAsientoRecepcion = BigDecimal.ZERO;
/* 1082:1104 */     BigDecimal debeAsientoNuevo = BigDecimal.ZERO;
/* 1083:1106 */     for (DetalleAsiento det : asientoRecepcion.getListaDetalleAsiento()) {
/* 1084:1107 */       if (!det.isEliminado()) {
/* 1085:1108 */         debeAsientoRecepcion = debeAsientoRecepcion.add(det.getDebe());
/* 1086:     */       }
/* 1087:     */     }
/* 1088:1112 */     for (DetalleAsiento det : asiento.getListaDetalleAsiento()) {
/* 1089:1113 */       if (!det.isEliminado()) {
/* 1090:1114 */         debeAsientoNuevo = debeAsientoNuevo.add(det.getDebe());
/* 1091:     */       }
/* 1092:     */     }
/* 1093:1118 */     BigDecimal diferencia = debeAsientoNuevo.subtract(debeAsientoRecepcion);
/* 1094:     */     
/* 1095:1120 */     return diferencia;
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   public void esEditable(RecepcionProveedor recepcionProveedor)
/* 1099:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Compras
/* 1100:     */   {
/* 1101:1131 */     this.servicioPeriodo.buscarPorFecha(recepcionProveedor.getFecha(), recepcionProveedor.getIdOrganizacion(), recepcionProveedor
/* 1102:1132 */       .getDocumento().getDocumentoBase());
/* 1103:1134 */     if (recepcionProveedor.getEstado() == Estado.ANULADO) {
/* 1104:1135 */       throw new ExcepcionAS2Compras("msg_error_editar");
/* 1105:     */     }
/* 1106:1137 */     if ((recepcionProveedor.getAsiento() != null) && (recepcionProveedor.getAsiento().getEstado() == Estado.REVISADO)) {
/* 1107:1138 */       throw new ExcepcionAS2Compras("msg_info_anulacion_proceso_estado_asiento");
/* 1108:     */     }
/* 1109:1142 */     FacturaProveedor facturaProveedorExistente = this.servicioFacturaProveedor.buscarPorRecepcionProveedor(recepcionProveedor.getId());
/* 1110:1143 */     if (facturaProveedorExistente != null) {
/* 1111:1144 */       throw new ExcepcionAS2Compras("msg_info_ya_existe_factura_recepcion", facturaProveedorExistente.getNumero());
/* 1112:     */     }
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1116:     */   public void anular(RecepcionProveedor recepcionProveedor, Date fechaAnulacion)
/* 1117:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Compras, ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 1118:     */   {
/* 1119:     */     try
/* 1120:     */     {
/* 1121:1154 */       recepcionProveedor = cargarDetalle(recepcionProveedor);
/* 1122:     */       
/* 1123:1156 */       this.servicioPeriodo.buscarPorFecha(recepcionProveedor.getFecha(), recepcionProveedor.getIdOrganizacion(), recepcionProveedor
/* 1124:1157 */         .getDocumento().getDocumentoBase());
/* 1125:1159 */       if (recepcionProveedor.getEstado() == Estado.ANULADO) {
/* 1126:1160 */         throw new ExcepcionAS2Compras("msg_error_editar");
/* 1127:     */       }
/* 1128:1162 */       if ((recepcionProveedor.getAsiento() != null) && (recepcionProveedor.getAsiento().getEstado() == Estado.REVISADO)) {
/* 1129:1163 */         throw new ExcepcionAS2Compras("msg_info_anulacion_proceso_estado_asiento");
/* 1130:     */       }
/* 1131:1164 */       if ((recepcionProveedor.getFacturaProveedor() != null) && 
/* 1132:1165 */         (recepcionProveedor.getFacturaProveedor().getFacturaProveedorImportacion() != null) && 
/* 1133:1166 */         (Estado.CERRADO.equals(recepcionProveedor.getFacturaProveedor().getEstado()))) {
/* 1134:1167 */         throw new AS2Exception("msg_info_importacion_estado_cerrado", new String[] { "" });
/* 1135:     */       }
/* 1136:1170 */       if (recepcionProveedor.getAsiento() != null)
/* 1137:     */       {
/* 1138:1171 */         recepcionProveedor.getAsiento().setIndicadorAutomatico(false);
/* 1139:1172 */         this.servicioAsiento.anular(recepcionProveedor.getAsiento());
/* 1140:     */       }
/* 1141:1175 */       actualizarEstado(Integer.valueOf(recepcionProveedor.getIdRecepcionProveedor()), Estado.ANULADO);
/* 1142:1180 */       for (DetalleRecepcionProveedor detalle : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 1143:1181 */         if (detalle.getTransformacionAutomatica() != null)
/* 1144:     */         {
/* 1145:1182 */           MovimientoInventario transformacion = detalle.getTransformacionAutomatica();
/* 1146:1183 */           detalle.setTransformacionAutomatica(null);
/* 1147:1184 */           this.detalleRecepcionProveedorDao.guardar(detalle);
/* 1148:1185 */           this.servicioMovimientoInventario.eliminarTransformacionProductoMateriales(transformacion);
/* 1149:     */         }
/* 1150:     */       }
/* 1151:1190 */       FacturaProveedor facturaProveedorExistente = this.servicioFacturaProveedor.buscarPorRecepcionProveedor(recepcionProveedor.getId());
/* 1152:1191 */       if (facturaProveedorExistente != null)
/* 1153:     */       {
/* 1154:1192 */         FacturaProveedor fp = this.servicioFacturaProveedor.buscarPorId(Integer.valueOf(facturaProveedorExistente.getId()));
/* 1155:1193 */         fp.setRecepcionProveedor(null);
/* 1156:     */       }
/* 1157:1197 */       this.servicioVerificadorCompras.actualizarCantidadPorRecibir(recepcionProveedor, true);
/* 1158:1199 */       if (recepcionProveedor.getPedidoProveedor() != null) {
/* 1159:1200 */         this.servicioPedidoProveedor.cierreAutomatico(recepcionProveedor.getPedidoProveedor().getId());
/* 1160:     */       }
/* 1161:1206 */       this.servicioVerificadorInventario.actualizarSaldoProducto(recepcionProveedor, true, fechaAnulacion);
/* 1162:1208 */       if (ParametrosSistema.isRegistroReversoAnulacionInventario(recepcionProveedor.getIdOrganizacion()).booleanValue()) {
/* 1163:1211 */         this.servicioInventarioProducto.generarMovimientoInventarioInverso(recepcionProveedor, fechaAnulacion);
/* 1164:     */       } else {
/* 1165:1214 */         this.servicioInventarioProducto.eliminaInventarioProductoPorIdRecepcionProveedor(Integer.valueOf(recepcionProveedor.getIdRecepcionProveedor()));
/* 1166:     */       }
/* 1167:1217 */       if (ParametrosSistema.getGeneracionDeCostosAutomatica(recepcionProveedor.getIdOrganizacion()).booleanValue())
/* 1168:     */       {
/* 1169:1218 */         recepcionProveedor = cargarDetalle(recepcionProveedor);
/* 1170:     */         
/* 1171:1220 */         this.servicioCosteo.generarCostos(recepcionProveedor, ParametrosSistema.isCosteoPorBodega(recepcionProveedor.getIdOrganizacion()).booleanValue());
/* 1172:     */       }
/* 1173:1224 */       this.servicioFacturaProveedor.liberarRecepcionFacturaProveedor(recepcionProveedor);
/* 1174:     */     }
/* 1175:     */     catch (ExcepcionAS2Financiero e)
/* 1176:     */     {
/* 1177:1226 */       this.context.setRollbackOnly();
/* 1178:1227 */       throw e;
/* 1179:     */     }
/* 1180:     */     catch (ExcepcionAS2Inventario e)
/* 1181:     */     {
/* 1182:1229 */       this.context.setRollbackOnly();
/* 1183:1230 */       throw e;
/* 1184:     */     }
/* 1185:     */     catch (ExcepcionAS2Compras e)
/* 1186:     */     {
/* 1187:1232 */       this.context.setRollbackOnly();
/* 1188:1233 */       throw e;
/* 1189:     */     }
/* 1190:     */     catch (ExcepcionAS2 e)
/* 1191:     */     {
/* 1192:1235 */       this.context.setRollbackOnly();
/* 1193:1236 */       throw e;
/* 1194:     */     }
/* 1195:     */     catch (AS2Exception e)
/* 1196:     */     {
/* 1197:1238 */       this.context.setRollbackOnly();
/* 1198:1239 */       throw e;
/* 1199:     */     }
/* 1200:     */     catch (Exception e)
/* 1201:     */     {
/* 1202:1241 */       this.context.setRollbackOnly();
/* 1203:1242 */       throw new ExcepcionAS2Compras(e);
/* 1204:     */     }
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public void actualizarEstado(Integer idRecepcionProveedor, Estado estado)
/* 1208:     */   {
/* 1209:1255 */     this.recepcionProveedorDao.actualizarEstado(idRecepcionProveedor, estado);
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public int contarPorCriterio(Map<String, String> filters)
/* 1213:     */   {
/* 1214:1266 */     return this.recepcionProveedorDao.contarPorCriterio(filters);
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public RecepcionProveedor cargarDetalleAFacturar(int idRecepcionProveedor)
/* 1218:     */   {
/* 1219:1276 */     return this.recepcionProveedorDao.cargarDetalleAFacturar(idRecepcionProveedor);
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   public RecepcionProveedor buscarPorFacturaProveedor(int idFacturaProveedor)
/* 1223:     */   {
/* 1224:1286 */     return this.recepcionProveedorDao.buscarPorFacturaProveedor(idFacturaProveedor);
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   public void cargarDetalleRecepcionArchivoTexto(TipoOrganizacion tipoOrganizacion, RecepcionProveedor recepcionProveedor, InputStream inputStream, Bodega bodega, Producto producto, Empresa empresa, DetalleRecepcionProveedor detalleRecepcionProveedor)
/* 1228:     */     throws ExcepcionAS2
/* 1229:     */   {
/* 1230:     */     try
/* 1231:     */     {
/* 1232:1300 */       cargarDetalleRecepcionArchivoTextoPadilla(recepcionProveedor, inputStream, bodega, producto, empresa, detalleRecepcionProveedor);
/* 1233:     */     }
/* 1234:     */     catch (IllegalArgumentException e)
/* 1235:     */     {
/* 1236:1302 */       this.context.setRollbackOnly();
/* 1237:1303 */       e.printStackTrace();
/* 1238:1304 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", e.getMessage());
/* 1239:     */     }
/* 1240:     */     catch (ExcepcionAS2 e)
/* 1241:     */     {
/* 1242:1306 */       this.context.setRollbackOnly();
/* 1243:1307 */       e.printStackTrace();
/* 1244:1308 */       recepcionProveedor.getListaDetalleRecepcionProveedor().clear();
/* 1245:1309 */       throw e;
/* 1246:     */     }
/* 1247:     */     catch (IOException e)
/* 1248:     */     {
/* 1249:1311 */       this.context.setRollbackOnly();
/* 1250:1312 */       e.printStackTrace();
/* 1251:1313 */       throw new ExcepcionAS2(e);
/* 1252:     */     }
/* 1253:     */     catch (Exception e)
/* 1254:     */     {
/* 1255:1315 */       this.context.setRollbackOnly();
/* 1256:1316 */       e.printStackTrace();
/* 1257:1317 */       throw new ExcepcionAS2(e);
/* 1258:     */     }
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   private void cargarDetalleRecepcionArchivoTextoPadilla(RecepcionProveedor recepcionProveedor, InputStream inputStream, Bodega bodega, Producto producto, Empresa empresa, DetalleRecepcionProveedor detalleRecepcionProveedor)
/* 1262:     */     throws IllegalArgumentException, ExcepcionAS2, IOException, AS2Exception
/* 1263:     */   {
/* 1264:1337 */     DetalleRecepcionProveedor drp = null;
/* 1265:1338 */     List<String> datosArchivo = FuncionesUtiles.leerArchivoTexto(inputStream);
/* 1266:1339 */     int cont = 0;
/* 1267:1340 */     Map<String, String> mapaDatosArchivo = new HashMap();
/* 1268:1341 */     Map<String, String> mapaLoteAuxiliar = new HashMap();
/* 1269:1343 */     if (empresa == null) {
/* 1270:1344 */       throw new ExcepcionAS2("msg_error_selecciona_proveedor_recepcion");
/* 1271:     */     }
/* 1272:1347 */     for (DetalleRecepcionProveedor detalleRecepcionProveedorTodos : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 1273:1348 */       if ((!detalleRecepcionProveedorTodos.isEliminado()) && (detalleRecepcionProveedorTodos.getInventarioProducto().getLote() != null)) {
/* 1274:1349 */         mapaLoteAuxiliar.put(detalleRecepcionProveedorTodos.getInventarioProducto().getLote().getCodigo(), detalleRecepcionProveedorTodos
/* 1275:1350 */           .getInventarioProducto().getLote().getCodigo());
/* 1276:     */       }
/* 1277:     */     }
/* 1278:1354 */     for (String dato : datosArchivo)
/* 1279:     */     {
/* 1280:1355 */       if (cont > 0)
/* 1281:     */       {
/* 1282:1357 */         String codigoLote = dato.substring(1, dato.indexOf(","));
/* 1283:1358 */         String cantidad = dato.substring(dato.indexOf(",") + 1, dato.length());
/* 1284:1359 */         mapaDatosArchivo.put(String.valueOf(codigoLote), codigoLote + "~" + cantidad);
/* 1285:     */       }
/* 1286:1361 */       cont++;
/* 1287:     */     }
/* 1288:1364 */     for (String datoNumero : mapaDatosArchivo.values())
/* 1289:     */     {
/* 1290:1366 */       String codigoLote = datoNumero.substring(0, datoNumero.indexOf("~"));
/* 1291:     */       
/* 1292:1368 */       Lote loteCrear = null;
/* 1293:     */       try
/* 1294:     */       {
/* 1295:1370 */         loteCrear = this.servicioLote.buscarPorCodigo(codigoLote);
/* 1296:     */       }
/* 1297:     */       catch (ExcepcionAS2 e)
/* 1298:     */       {
/* 1299:1372 */         loteCrear = new Lote();
/* 1300:1373 */         loteCrear.setActivo(true);
/* 1301:1374 */         loteCrear.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1302:1375 */         loteCrear.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 1303:1376 */         loteCrear.setProducto(producto);
/* 1304:1377 */         loteCrear.setCodigo(codigoLote);
/* 1305:1378 */         loteCrear.setIndicadorMovimientoInterno(false);
/* 1306:1379 */         loteCrear.setEmpresa(empresa);
/* 1307:1380 */         loteCrear.setFechaCaducidad(new Date());
/* 1308:1381 */         loteCrear.setFechaFabricacion(new Date());
/* 1309:1382 */         this.servicioLote.guardar(loteCrear);
/* 1310:     */       }
/* 1311:1385 */       if ((producto != null) && (producto.isIndicadorLote()))
/* 1312:     */       {
/* 1313:1386 */         Bodega bodegaDato = bodega != null ? bodega : producto.getBodegaVenta();
/* 1314:1387 */         drp = new DetalleRecepcionProveedor();
/* 1315:1388 */         drp.setIdSucursal(recepcionProveedor.getSucursal().getIdSucursal());
/* 1316:1389 */         drp.setIdOrganizacion(recepcionProveedor.getIdOrganizacion());
/* 1317:1390 */         drp.setCantidad(new BigDecimal(datoNumero.substring(datoNumero.indexOf("~") + 1, datoNumero.length())));
/* 1318:1391 */         drp.setDescripcion("");
/* 1319:1392 */         drp.setProducto(producto);
/* 1320:1393 */         drp.setInventarioProducto(new InventarioProducto());
/* 1321:1394 */         drp.getInventarioProducto().setLote(loteCrear);
/* 1322:1395 */         drp.setRecepcionProveedor(recepcionProveedor);
/* 1323:1396 */         drp.setUnidadCompra(producto.getUnidadCompra());
/* 1324:1397 */         drp.setBodega(bodegaDato);
/* 1325:1398 */         if (detalleRecepcionProveedor != null) {
/* 1326:1399 */           drp.setDetalleFacturaProveedor(detalleRecepcionProveedor.getDetalleFacturaProveedor());
/* 1327:     */         }
/* 1328:1401 */         if (!mapaLoteAuxiliar.containsKey(loteCrear.getCodigo())) {
/* 1329:1402 */           recepcionProveedor.getListaDetalleRecepcionProveedor().add(drp);
/* 1330:     */         }
/* 1331:     */       }
/* 1332:     */       else
/* 1333:     */       {
/* 1334:1405 */         throw new ExcepcionAS2("msg_error_selecciona_producto_recepcion");
/* 1335:     */       }
/* 1336:     */     }
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   public void cargarRecepcionDesdeNumeroLotes(TipoOrganizacion tipoOrganizacion, RecepcionProveedor recepcionProveedor, Bodega bodega, Producto producto, BigDecimal cantidadPeso, Integer numeroLotes, DetalleRecepcionProveedor detalleRecepcionProveedor, boolean generarLotesAutomaticamente)
/* 1340:     */     throws ExcepcionAS2, AS2Exception
/* 1341:     */   {
/* 1342:     */     try
/* 1343:     */     {
/* 1344:1423 */       cargarDetalleRecepcionNumeroLotesPadilla(recepcionProveedor, bodega, producto, cantidadPeso, numeroLotes, detalleRecepcionProveedor, generarLotesAutomaticamente);
/* 1345:     */     }
/* 1346:     */     catch (ExcepcionAS2 e)
/* 1347:     */     {
/* 1348:1426 */       this.context.setRollbackOnly();
/* 1349:1427 */       e.printStackTrace();
/* 1350:1428 */       recepcionProveedor.getListaDetalleRecepcionProveedor().clear();
/* 1351:1429 */       throw e;
/* 1352:     */     }
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   private void cargarDetalleRecepcionNumeroLotesPadilla(RecepcionProveedor recepcionProveedor, Bodega bodega, Producto producto, BigDecimal cantidad, Integer numeroLotes, DetalleRecepcionProveedor detalleRecepcionProveedor, boolean generarLotesAutomaticamente)
/* 1356:     */     throws ExcepcionAS2, AS2Exception
/* 1357:     */   {
/* 1358:1447 */     DetalleRecepcionProveedor drp = null;
/* 1359:1448 */     BigDecimal cantidadDetalle = null;
/* 1360:1450 */     if (detalleRecepcionProveedor != null)
/* 1361:     */     {
/* 1362:1451 */       numeroLotes = Integer.valueOf(numeroLotes.intValue() - 1);
/* 1363:     */       
/* 1364:1453 */       cantidadDetalle = detalleRecepcionProveedor.getCantidad();
/* 1365:1454 */       bodega = detalleRecepcionProveedor.getBodega();
/* 1366:     */       
/* 1367:1456 */       detalleRecepcionProveedor.setCantidad(cantidad);
/* 1368:     */     }
/* 1369:1458 */     if (numeroLotes == null) {
/* 1370:1459 */       throw new ExcepcionAS2("msg_error_numero_lote_valido");
/* 1371:     */     }
/* 1372:1462 */     if ((cantidad == null) || (cantidad.compareTo(BigDecimal.ZERO) == 0)) {
/* 1373:1463 */       throw new ExcepcionAS2("msg_error_cantidad_peso_valida");
/* 1374:     */     }
/* 1375:1466 */     if (producto == null) {
/* 1376:1467 */       throw new ExcepcionAS2("msg_error_producto_valido");
/* 1377:     */     }
/* 1378:1470 */     Lote loteCrear = null;
/* 1379:1472 */     if (detalleRecepcionProveedor != null)
/* 1380:     */     {
/* 1381:1473 */       InventarioProducto ip = detalleRecepcionProveedor.getInventarioProducto();
/* 1382:1474 */       if ((ip.getLote() == null) || (ip.getLote().getCodigo() == null))
/* 1383:     */       {
/* 1384:1475 */         if (generarLotesAutomaticamente) {
/* 1385:1476 */           loteCrear = crearLote(producto);
/* 1386:     */         }
/* 1387:1478 */         detalleRecepcionProveedor.getInventarioProducto().setLote(loteCrear);
/* 1388:     */       }
/* 1389:     */     }
/* 1390:1481 */     for (int i = 1; i <= numeroLotes.intValue(); i++)
/* 1391:     */     {
/* 1392:1482 */       if (generarLotesAutomaticamente) {
/* 1393:1483 */         loteCrear = crearLote(producto);
/* 1394:     */       }
/* 1395:1486 */       if (producto.isIndicadorLote())
/* 1396:     */       {
/* 1397:1487 */         Bodega bodegaDato = bodega != null ? bodega : producto.getBodegaVenta();
/* 1398:1488 */         drp = new DetalleRecepcionProveedor();
/* 1399:1489 */         drp.setIdSucursal(recepcionProveedor.getSucursal().getIdSucursal());
/* 1400:1490 */         drp.setIdOrganizacion(recepcionProveedor.getIdOrganizacion());
/* 1401:1491 */         drp.setCantidad(cantidad);
/* 1402:1492 */         drp.setDescripcion("");
/* 1403:1493 */         drp.setProducto(producto);
/* 1404:1494 */         drp.setInventarioProducto(new InventarioProducto());
/* 1405:1495 */         drp.getInventarioProducto().setLote(loteCrear);
/* 1406:1496 */         drp.setRecepcionProveedor(recepcionProveedor);
/* 1407:1497 */         drp.setUnidadCompra(producto.getUnidadCompra());
/* 1408:1498 */         drp.setBodega(bodegaDato);
/* 1409:1499 */         drp.setDetalleFacturaProveedor(detalleRecepcionProveedor != null ? detalleRecepcionProveedor.getDetalleFacturaProveedor() : null);
/* 1410:1500 */         recepcionProveedor.getListaDetalleRecepcionProveedor().add(drp);
/* 1411:1502 */         if ((i == numeroLotes.intValue()) && (cantidadDetalle != null))
/* 1412:     */         {
/* 1413:1504 */           BigDecimal saldo = cantidadDetalle.subtract(cantidad.multiply(new BigDecimal(i)));
/* 1414:1505 */           drp.setCantidad(saldo);
/* 1415:1506 */           if (drp.getInventarioProducto() != null) {
/* 1416:1507 */             drp.getInventarioProducto().setCantidad(drp.getCantidad());
/* 1417:     */           }
/* 1418:     */         }
/* 1419:     */       }
/* 1420:     */     }
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   private Lote crearLote(Producto producto)
/* 1424:     */     throws ExcepcionAS2, AS2Exception
/* 1425:     */   {
/* 1426:1516 */     Lote loteCrear = new Lote();
/* 1427:1517 */     loteCrear.setActivo(true);
/* 1428:1518 */     loteCrear.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1429:1519 */     loteCrear.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 1430:1520 */     loteCrear.setProducto(producto);
/* 1431:1521 */     Integer numeroSerie = this.servicioOrganizacion.actualizarSecuenciaInicioSerie(AppUtil.getOrganizacion());
/* 1432:1522 */     loteCrear.setCodigo(producto.getPrefijoLote() + String.valueOf(FuncionesUtiles.completarALaIzquierda('0', 10, numeroSerie.toString())));
/* 1433:1523 */     loteCrear.setIndicadorMovimientoInterno(true);
/* 1434:1524 */     loteCrear.setFechaCaducidad(new Date());
/* 1435:1525 */     loteCrear.setFechaFabricacion(new Date());
/* 1436:     */     
/* 1437:     */ 
/* 1438:1528 */     loteCrear.setAtributo1(producto.getTraAtributo1());
/* 1439:1529 */     loteCrear.setValorAtributo1(producto.getValorAtributo1());
/* 1440:     */     
/* 1441:1531 */     loteCrear.setAtributo2(producto.getTraAtributo2());
/* 1442:1532 */     loteCrear.setValorAtributo2(producto.getValorAtributo2());
/* 1443:     */     
/* 1444:1534 */     loteCrear.setAtributo3(producto.getTraAtributo3());
/* 1445:1535 */     loteCrear.setValorAtributo3(producto.getValorAtributo3());
/* 1446:     */     
/* 1447:1537 */     loteCrear.setAtributo4(producto.getTraAtributo4());
/* 1448:1538 */     loteCrear.setValorAtributo4(producto.getValorAtributo4());
/* 1449:     */     
/* 1450:1540 */     loteCrear.setAtributo5(producto.getTraAtributo5());
/* 1451:1541 */     loteCrear.setValorAtributo5(producto.getValorAtributo5());
/* 1452:     */     
/* 1453:1543 */     loteCrear.setAtributo6(producto.getTraAtributo6());
/* 1454:1544 */     loteCrear.setValorAtributo6(producto.getValorAtributo6());
/* 1455:     */     
/* 1456:1546 */     loteCrear.setAtributo7(producto.getTraAtributo7());
/* 1457:1547 */     loteCrear.setValorAtributo7(producto.getValorAtributo7());
/* 1458:     */     
/* 1459:1549 */     loteCrear.setAtributo8(producto.getTraAtributo8());
/* 1460:1550 */     loteCrear.setValorAtributo8(producto.getValorAtributo8());
/* 1461:     */     
/* 1462:1552 */     loteCrear.setAtributo9(producto.getTraAtributo9());
/* 1463:1553 */     loteCrear.setValorAtributo9(producto.getValorAtributo9());
/* 1464:     */     
/* 1465:1555 */     loteCrear.setAtributo10(producto.getTraAtributo10());
/* 1466:1556 */     loteCrear.setValorAtributo10(producto.getValorAtributo10());
/* 1467:     */     
/* 1468:     */ 
/* 1469:1559 */     return loteCrear;
/* 1470:     */   }
/* 1471:     */   
/* 1472:     */   public RecepcionProveedor buscarRecepcionPorNumero(String numero)
/* 1473:     */   {
/* 1474:1569 */     return this.recepcionProveedorDao.buscarRecepcionPorNumero(numero);
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   public List<Object[]> getDatosImpresionEtiquetaLote(int idOrganizacion, Documento documento, String numero, String loteDesde, String loteHasta, boolean indicadoriImprimirPorUnidad, int numeroAtributos)
/* 1478:     */   {
/* 1479:1576 */     List<Object[]> listaNueva = new ArrayList();
/* 1480:     */     HashMap<String, DetalleVersionListaPrecios> hmProducto;
/* 1481:     */     VersionListaPrecios versionListaPrecios;
/* 1482:1578 */     if (indicadoriImprimirPorUnidad)
/* 1483:     */     {
/* 1484:1581 */       HashMap<String, String> filters = new HashMap();
/* 1485:1582 */       filters.put("idOrganizacion", "=" + idOrganizacion);
/* 1486:1583 */       filters.put("indicadorImpresionEtiqueta", "true");
/* 1487:1584 */       List<ListaPrecios> listaPreciosList = this.servicioListaPrecios.obtenerListaCombo("nombre", true, filters);
/* 1488:     */       
/* 1489:1586 */       ListaPrecios listaPrecios = null;
/* 1490:1587 */       hmProducto = new HashMap();
/* 1491:     */       Iterator localIterator1;
/* 1492:1589 */       if (!listaPreciosList.isEmpty())
/* 1493:     */       {
/* 1494:1590 */         listaPrecios = this.servicioListaPrecios.obtenerListaPreciosVigente(((ListaPrecios)listaPreciosList.get(0)).getId());
/* 1495:1591 */         for (localIterator1 = listaPrecios.getVersionesListaPrecios().iterator(); localIterator1.hasNext();)
/* 1496:     */         {
/* 1497:1591 */           versionListaPrecios = (VersionListaPrecios)localIterator1.next();
/* 1498:1592 */           for (DetalleVersionListaPrecios detalleVersionListaPrecios : versionListaPrecios.getDetalleVersionesListaPrecios()) {
/* 1499:1593 */             hmProducto.put(detalleVersionListaPrecios.getProducto().getCodigo(), detalleVersionListaPrecios);
/* 1500:     */           }
/* 1501:     */         }
/* 1502:     */       }
/* 1503:1598 */       Object lista = this.recepcionProveedorDao.getDatosImpresionEtiquetaLote(idOrganizacion, documento, numero, loteDesde, loteHasta, indicadoriImprimirPorUnidad, numeroAtributos);
/* 1504:1601 */       for (Object[] objects : (List)lista)
/* 1505:     */       {
/* 1506:1603 */         Integer contador = Integer.valueOf(1);
/* 1507:1604 */         BigDecimal cantidad = (BigDecimal)objects[4];
/* 1508:1605 */         Integer numeroDecimales = (Integer)objects[11];
/* 1509:1607 */         if (numeroDecimales.intValue() == 0) {
/* 1510:1608 */           while (contador.intValue() <= cantidad.intValue())
/* 1511:     */           {
/* 1512:1611 */             DetalleVersionListaPrecios detalleVersionListaPrecios = hmProducto.get(objects[10]) == null ? null : (DetalleVersionListaPrecios)hmProducto.get(objects[10]);
/* 1513:     */             
/* 1514:1613 */             Object[] object = new Object[13];
/* 1515:1614 */             object[0] = objects[0];
/* 1516:1615 */             object[1] = objects[1];
/* 1517:1616 */             object[2] = objects[2];
/* 1518:1617 */             object[3] = objects[3];
/* 1519:1618 */             object[4] = BigDecimal.ONE;
/* 1520:1619 */             object[5] = objects[5];
/* 1521:1620 */             object[6] = objects[6];
/* 1522:1621 */             object[7] = objects[7];
/* 1523:1622 */             object[8] = objects[8];
/* 1524:1623 */             object[9] = objects[9];
/* 1525:1624 */             object[10] = objects[10];
/* 1526:1625 */             object[11] = objects[11];
/* 1527:1626 */             object[12] = (detalleVersionListaPrecios == null ? BigDecimal.ZERO : detalleVersionListaPrecios
/* 1528:1627 */               .getPrecioUnitarioClienteFinal());
/* 1529:     */             
/* 1530:1629 */             Integer localInteger1 = contador;Integer localInteger2 = contador = Integer.valueOf(contador.intValue() + 1);
/* 1531:1630 */             listaNueva.add(object);
/* 1532:     */           }
/* 1533:     */         }
/* 1534:1634 */         listaNueva.add(objects);
/* 1535:     */       }
/* 1536:     */     }
/* 1537:     */     else
/* 1538:     */     {
/* 1539:1639 */       listaNueva = this.recepcionProveedorDao.getDatosImpresionEtiquetaLote(idOrganizacion, documento, numero, loteDesde, loteHasta, indicadoriImprimirPorUnidad, numeroAtributos);
/* 1540:     */     }
/* 1541:1642 */     return listaNueva;
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1545:     */   public void guardarDetalleRecepcionProveedor(RecepcionProveedor recepcionProveedor, BigDecimal totalCostoAnterior, CuentaContable cuentaDiferencia)
/* 1546:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1547:     */   {
/* 1548:1648 */     BigDecimal valorCostoActual = BigDecimal.ZERO;
/* 1549:1649 */     FacturaProveedor fp = this.servicioFacturaProveedor.buscarPorRecepcionProveedor(recepcionProveedor.getIdRecepcionProveedor());
/* 1550:     */     DetalleRecepcionProveedor detalleRecepcionProveedor;
/* 1551:     */     boolean hayDiferencias;
/* 1552:1650 */     if (fp != null)
/* 1553:     */     {
/* 1554:1651 */       if (fp.getDocumento().isIndicadorDocumentoExterior())
/* 1555:     */       {
/* 1556:1652 */         this.servicioPeriodo.buscarPorFecha(recepcionProveedor.getFecha(), recepcionProveedor.getIdOrganizacion(), recepcionProveedor
/* 1557:1653 */           .getDocumento().getDocumentoBase());
/* 1558:1654 */         for (DetalleRecepcionProveedor detalleRecepcionProveedor : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 1559:1655 */           valorCostoActual = valorCostoActual.add(detalleRecepcionProveedor.getInventarioProducto().getCosto());
/* 1560:     */         }
/* 1561:1658 */         validacionCostos(totalCostoAnterior, valorCostoActual, "", cuentaDiferencia);
/* 1562:1660 */         for (??? = recepcionProveedor.getListaDetalleRecepcionProveedor().iterator(); ???.hasNext();)
/* 1563:     */         {
/* 1564:1660 */           detalleRecepcionProveedor = (DetalleRecepcionProveedor)???.next();
/* 1565:     */           
/* 1566:1662 */           InventarioProducto ip = this.servicioInventarioProducto.cargarDetalle(detalleRecepcionProveedor.getInventarioProducto().getIdInventarioProducto());
/* 1567:1663 */           valorCostoActual = valorCostoActual.add(detalleRecepcionProveedor.getInventarioProducto().getCosto());
/* 1568:1664 */           ip.setCosto(detalleRecepcionProveedor.getInventarioProducto().getCosto());
/* 1569:1665 */           this.servicioInventarioProducto.guardar(ip);
/* 1570:1666 */           this.detalleRecepcionProveedorDao.guardar(detalleRecepcionProveedor);
/* 1571:     */         }
/* 1572:1669 */         fp.getFacturaProveedorImportacion().setCuentaContableImportacionDiferenciaEnMasOEnMenos(cuentaDiferencia);
/* 1573:1670 */         recepcionProveedor.setFacturaProveedor(fp);
/* 1574:1671 */         recepcionProveedor.setTotalCosto(totalCostoAnterior);
/* 1575:     */       }
/* 1576:     */     }
/* 1577:     */     else
/* 1578:     */     {
/* 1579:1675 */       esEditable(recepcionProveedor);
/* 1580:     */       
/* 1581:1677 */       hayDiferencias = false;
/* 1582:1678 */       for (DetalleRecepcionProveedor detalleRecepcionProveedor : recepcionProveedor.getListaDetalleRecepcionProveedor())
/* 1583:     */       {
/* 1584:1680 */         DetalleRecepcionProveedor detalleAnterior = (DetalleRecepcionProveedor)this.detalleRecepcionProveedorDao.buscarPorId(Integer.valueOf(detalleRecepcionProveedor.getId()));
/* 1585:1681 */         BigDecimal cantidadAnterior = detalleAnterior.getCantidad();
/* 1586:1682 */         if (cantidadAnterior.compareTo(detalleRecepcionProveedor.getCantidad()) != 0) {
/* 1587:1683 */           hayDiferencias = true;
/* 1588:     */         }
/* 1589:     */       }
/* 1590:1687 */       if (hayDiferencias)
/* 1591:     */       {
/* 1592:1689 */         this.servicioVerificadorInventario.actualizarSaldoProducto(recepcionProveedor, true);
/* 1593:     */         
/* 1594:1691 */         this.servicioVerificadorInventario.actualizarSaldoProducto(recepcionProveedor, false);
/* 1595:     */       }
/* 1596:1694 */       for (DetalleRecepcionProveedor detalleRecepcionProveedor : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 1597:1695 */         if (detalleRecepcionProveedor.getDetallePedidoProveedor() != null)
/* 1598:     */         {
/* 1599:1697 */           InventarioProducto ip = this.servicioInventarioProducto.cargarDetalle(detalleRecepcionProveedor.getInventarioProducto().getIdInventarioProducto());
/* 1600:1698 */           ip.setCosto(detalleRecepcionProveedor.getInventarioProducto().getCosto());
/* 1601:     */           
/* 1602:1700 */           BigDecimal precioUnitario = detalleRecepcionProveedor.getInventarioProducto().getCosto().divide(detalleRecepcionProveedor.getCantidad(), 6, RoundingMode.HALF_UP);
/* 1603:     */           
/* 1604:1702 */           DetallePedidoProveedor dpp = this.detallePedidoProveedorDao.cargarDetalle(detalleRecepcionProveedor.getDetallePedidoProveedor().getIdDetallePedidoProveedor());
/* 1605:1703 */           dpp.setPrecio(precioUnitario);
/* 1606:1704 */           this.detallePedidoProveedorDao.guardar(dpp);
/* 1607:1705 */           detalleRecepcionProveedor.setInventarioProducto(ip);
/* 1608:1706 */           if (hayDiferencias) {
/* 1609:1707 */             actualizarInventarioProducto(recepcionProveedor);
/* 1610:     */           }
/* 1611:1709 */           this.servicioInventarioProducto.guardar(ip);
/* 1612:1710 */           this.detalleRecepcionProveedorDao.guardar(detalleRecepcionProveedor);
/* 1613:     */         }
/* 1614:     */       }
/* 1615:     */     }
/* 1616:1716 */     this.recepcionProveedorDao.guardar(recepcionProveedor);
/* 1617:1717 */     contabilizar(recepcionProveedor);
/* 1618:     */   }
/* 1619:     */   
/* 1620:     */   public List<RecepcionProveedor> buscarRecepcionesNoFacturadasPorProveedor(Integer idEmpresa)
/* 1621:     */   {
/* 1622:1723 */     return this.recepcionProveedorDao.buscarRecepcionesNoFacturadasPorProveedor(idEmpresa);
/* 1623:     */   }
/* 1624:     */   
/* 1625:     */   public List<RecepcionProveedor> autocompletarRecepcionesNoFacturadasPorProveedor(Integer idEmpresa, String numero)
/* 1626:     */   {
/* 1627:1728 */     return this.recepcionProveedorDao.autocompletarRecepcionesNoFacturadasPorProveedor(idEmpresa, numero);
/* 1628:     */   }
/* 1629:     */   
/* 1630:     */   public void validacionCostos(BigDecimal costoTotalAnterior, BigDecimal costoTotalActual, String mensaje, CuentaContable cuentaDiferencia)
/* 1631:     */     throws AS2Exception
/* 1632:     */   {
/* 1633:1733 */     if ((costoTotalAnterior.compareTo(costoTotalActual) != 0) && (cuentaDiferencia == null)) {
/* 1634:1734 */       throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioRecepcionProveedorImpl.COSTOS_DESIGUALES", new String[] { mensaje });
/* 1635:     */     }
/* 1636:     */   }
/* 1637:     */   
/* 1638:     */   public void validacionCantidadRecepcion(DetalleRecepcionProveedor detalleRecepcionProveedor, RecepcionProveedor recepcionProveedor, boolean indicadorModificaPrecio)
/* 1639:     */     throws AS2Exception
/* 1640:     */   {
/* 1641:1742 */     if (recepcionProveedor.getPedidoProveedor() != null) {
/* 1642:1744 */       if ((detalleRecepcionProveedor.getDetallePedidoProveedor() != null) && (!indicadorModificaPrecio))
/* 1643:     */       {
/* 1644:1746 */         BigDecimal porcentaje = new BigDecimal(recepcionProveedor.getEmpresa().getProveedor().getPorcentajeAdicionalRecepcion());
/* 1645:1747 */         porcentaje = porcentaje.divide(new BigDecimal(100));
/* 1646:1749 */         if (porcentaje.compareTo(BigDecimal.ZERO) > 0)
/* 1647:     */         {
/* 1648:1751 */           BigDecimal cantidadPermitida = detalleRecepcionProveedor.getDetallePedidoProveedor().getCantidad().add(detalleRecepcionProveedor.getDetallePedidoProveedor().getCantidad().multiply(porcentaje));
/* 1649:1752 */           if (cantidadPermitida.compareTo(detalleRecepcionProveedor.getCantidad()
/* 1650:1753 */             .add(detalleRecepcionProveedor.getDetallePedidoProveedor().getCantidadRecibida())) < 0)
/* 1651:     */           {
/* 1652:1754 */             detalleRecepcionProveedor.setCantidad(BigDecimal.ZERO);
/* 1653:     */             
/* 1654:1756 */             throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioRecepcionProveedorImpl.MENSAJE_ERROR_CANTIDAD_EXCEDIDA", new String[] { detalleRecepcionProveedor.getProducto().getCodigo(), "" + recepcionProveedor.getEmpresa().getProveedor().getPorcentajeAdicionalRecepcion() });
/* 1655:     */           }
/* 1656:     */         }
/* 1657:     */       }
/* 1658:     */     }
/* 1659:     */   }
/* 1660:     */   
/* 1661:     */   public void actualizarAtributoEntidad(RecepcionProveedor recepcionProveedor, HashMap<String, Object> campos)
/* 1662:     */   {
/* 1663:1767 */     this.recepcionProveedorDao.actualizarAtributoEntidad(recepcionProveedor, campos);
/* 1664:     */   }
/* 1665:     */   
/* 1666:     */   public List<RecepcionProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 1667:     */   {
/* 1668:1773 */     return this.recepcionProveedorDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 1669:     */   }
/* 1670:     */   
/* 1671:     */   public List<Object[]> getDatosImpresionEtiqueta(int idOrganizacion, Producto producto, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Bodega bodega, boolean saldoMayorCero)
/* 1672:     */   {
/* 1673:1781 */     List<Object[]> listaNueva = new ArrayList();
/* 1674:     */     
/* 1675:     */ 
/* 1676:1784 */     HashMap<String, String> filters = new HashMap();
/* 1677:1785 */     filters.put("idOrganizacion", "=" + idOrganizacion);
/* 1678:1786 */     filters.put("indicadorImpresionEtiqueta", "true");
/* 1679:1787 */     List<ListaPrecios> listaPreciosList = this.servicioListaPrecios.obtenerListaCombo("nombre", true, filters);
/* 1680:     */     
/* 1681:1789 */     ListaPrecios listaPrecios = null;
/* 1682:1790 */     HashMap<Integer, DetalleVersionListaPrecios> hmProducto = new HashMap();
/* 1683:1792 */     if (!listaPreciosList.isEmpty())
/* 1684:     */     {
/* 1685:1793 */       listaPrecios = this.servicioListaPrecios.obtenerListaPreciosVigente(((ListaPrecios)listaPreciosList.get(0)).getId());
/* 1686:1794 */       for (VersionListaPrecios versionListaPrecios : listaPrecios.getVersionesListaPrecios()) {
/* 1687:1795 */         for (DetalleVersionListaPrecios detalleVersionListaPrecios : versionListaPrecios.getDetalleVersionesListaPrecios()) {
/* 1688:1796 */           hmProducto.put(Integer.valueOf(detalleVersionListaPrecios.getProducto().getId()), detalleVersionListaPrecios);
/* 1689:     */         }
/* 1690:     */       }
/* 1691:     */     }
/* 1692:     */     Set<Integer> hmProductoSaldoMayorCero;
/* 1693:     */     SaldoProducto sp;
/* 1694:1802 */     if (producto == null)
/* 1695:     */     {
/* 1696:1805 */       Object listaBodega = null;
/* 1697:1806 */       hmProductoSaldoMayorCero = null;
/* 1698:1808 */       if (saldoMayorCero)
/* 1699:     */       {
/* 1700:1809 */         hmProductoSaldoMayorCero = new HashSet();
/* 1701:1811 */         if (bodega == null)
/* 1702:     */         {
/* 1703:1813 */           filters = new HashMap();
/* 1704:1814 */           filters.put("idOrganizacion", "=" + idOrganizacion);
/* 1705:1815 */           listaBodega = this.servicioBodega.obtenerListaCombo("codigo", true, filters);
/* 1706:1817 */           for (Bodega bo : (List)listaBodega) {
/* 1707:1818 */             for (SaldoProducto sp : this.servicioProducto.obtenerProductosConSaldoBodega(bo, new Date(), false)) {
/* 1708:1819 */               if (sp.getSaldo().compareTo(BigDecimal.ZERO) == 1) {
/* 1709:1820 */                 hmProductoSaldoMayorCero.add(Integer.valueOf(sp.getProducto().getIdProducto()));
/* 1710:     */               }
/* 1711:     */             }
/* 1712:     */           }
/* 1713:     */         }
/* 1714:     */         else
/* 1715:     */         {
/* 1716:1826 */           for (??? = this.servicioProducto.obtenerProductosConSaldoBodega(bodega, new Date(), false).iterator(); ???.hasNext();)
/* 1717:     */           {
/* 1718:1826 */             sp = (SaldoProducto)???.next();
/* 1719:1827 */             if (sp.getSaldo().compareTo(BigDecimal.ZERO) == 1) {
/* 1720:1828 */               hmProductoSaldoMayorCero.add(Integer.valueOf(sp.getProducto().getIdProducto()));
/* 1721:     */             }
/* 1722:     */           }
/* 1723:     */         }
/* 1724:     */       }
/* 1725:1835 */       filters = new HashMap();
/* 1726:1836 */       filters.put("idOrganizacion", "=" + idOrganizacion);
/* 1727:1837 */       filters.put("indicadorVenta", "true");
/* 1728:1838 */       filters.put("activo", "true");
/* 1729:1839 */       if (subcategoriaProducto != null) {
/* 1730:1840 */         filters.put("subcategoriaProducto.idSubcategoriaProducto", "=" + subcategoriaProducto.getId());
/* 1731:     */       }
/* 1732:1842 */       if (categoriaProducto != null) {
/* 1733:1843 */         filters.put("subcategoriaProducto.categoriaProducto.idCategoriaProducto", "=" + categoriaProducto.getId());
/* 1734:     */       }
/* 1735:1845 */       Object listaProducto = this.servicioProducto.obtenerListaCombo("nombre", false, filters);
/* 1736:1847 */       for (Producto pr : (List)listaProducto)
/* 1737:     */       {
/* 1738:1850 */         DetalleVersionListaPrecios detalleVersionListaPrecios = hmProducto.get(Integer.valueOf(pr.getId())) == null ? null : (DetalleVersionListaPrecios)hmProducto.get(Integer.valueOf(pr.getId()));
/* 1739:1852 */         if (detalleVersionListaPrecios != null)
/* 1740:     */         {
/* 1741:1854 */           boolean insertar = true;
/* 1742:1856 */           if (saldoMayorCero) {
/* 1743:1857 */             insertar = hmProductoSaldoMayorCero.contains(Integer.valueOf(pr.getId()));
/* 1744:     */           }
/* 1745:1860 */           if (insertar)
/* 1746:     */           {
/* 1747:1862 */             Object[] object = new Object[14];
/* 1748:1863 */             object[0] = "";
/* 1749:1864 */             object[1] = new Date();
/* 1750:1865 */             object[2] = "";
/* 1751:1866 */             object[3] = pr.getNombre();
/* 1752:1867 */             object[4] = BigDecimal.ONE;
/* 1753:1868 */             object[5] = pr.getUnidad().getNombre();
/* 1754:1869 */             object[6] = pr.getDescripcion();
/* 1755:1870 */             object[7] = "";
/* 1756:1871 */             object[8] = pr.getNombre();
/* 1757:1872 */             object[9] = pr.getUnidad().getCodigo();
/* 1758:1873 */             object[10] = pr.getCodigo();
/* 1759:1874 */             object[11] = Integer.valueOf(0);
/* 1760:1875 */             object[12] = detalleVersionListaPrecios.getPrecioUnitarioClienteFinal();
/* 1761:1876 */             object[13] = new Date();
/* 1762:     */             
/* 1763:1878 */             listaNueva.add(object);
/* 1764:     */           }
/* 1765:     */         }
/* 1766:     */       }
/* 1767:     */     }
/* 1768:     */     else
/* 1769:     */     {
/* 1770:1887 */       DetalleVersionListaPrecios detalleVersionListaPrecios = hmProducto.get(Integer.valueOf(producto.getId())) == null ? null : (DetalleVersionListaPrecios)hmProducto.get(Integer.valueOf(producto.getId()));
/* 1771:     */       
/* 1772:1889 */       Object[] object = new Object[14];
/* 1773:1890 */       object[0] = "";
/* 1774:1891 */       object[1] = new Date();
/* 1775:1892 */       object[2] = "";
/* 1776:1893 */       object[3] = producto.getNombre();
/* 1777:1894 */       object[4] = BigDecimal.ONE;
/* 1778:1895 */       object[5] = producto.getUnidad().getNombre();
/* 1779:1896 */       object[6] = producto.getDescripcion();
/* 1780:1897 */       object[7] = "";
/* 1781:1898 */       object[8] = producto.getNombre();
/* 1782:1899 */       object[9] = producto.getUnidad().getCodigo();
/* 1783:1900 */       object[10] = producto.getCodigo();
/* 1784:1901 */       object[11] = Integer.valueOf(0);
/* 1785:1902 */       object[12] = (detalleVersionListaPrecios == null ? BigDecimal.ZERO : detalleVersionListaPrecios.getPrecioUnitarioClienteFinal());
/* 1786:1903 */       object[13] = new Date();
/* 1787:     */       
/* 1788:1905 */       listaNueva.add(object);
/* 1789:     */     }
/* 1790:1908 */     return listaNueva;
/* 1791:     */   }
/* 1792:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.impl.ServicioRecepcionProveedorImpl
 * JD-Core Version:    0.7.0.1
 */