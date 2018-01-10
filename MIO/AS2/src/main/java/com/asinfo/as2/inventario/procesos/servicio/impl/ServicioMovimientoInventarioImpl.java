/*    1:     */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    5:     */ import com.asinfo.as2.clases.DetalleInterfazContableProcesoMovimientoInventario;
/*    6:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    8:     */ import com.asinfo.as2.dao.AsientoDao;
/*    9:     */ import com.asinfo.as2.dao.DetalleAsientoDao;
/*   10:     */ import com.asinfo.as2.dao.DetalleFacturaProveedorDao;
/*   11:     */ import com.asinfo.as2.dao.DetalleRecepcionProveedorDao;
/*   12:     */ import com.asinfo.as2.dao.GenericoDao;
/*   13:     */ import com.asinfo.as2.dao.InventarioProductoDao;
/*   14:     */ import com.asinfo.as2.dao.MovimientoInventarioDao;
/*   15:     */ import com.asinfo.as2.dao.ProductoDao;
/*   16:     */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*   17:     */ import com.asinfo.as2.dao.produccion.OrdenSalidaMaterialDao;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   19:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   20:     */ import com.asinfo.as2.entities.Asiento;
/*   21:     */ import com.asinfo.as2.entities.Atributo;
/*   22:     */ import com.asinfo.as2.entities.Bodega;
/*   23:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   24:     */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   25:     */ import com.asinfo.as2.entities.DestinoCosto;
/*   26:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   27:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   28:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   29:     */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   30:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   31:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   32:     */ import com.asinfo.as2.entities.Documento;
/*   33:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   34:     */ import com.asinfo.as2.entities.EntidadBase;
/*   35:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   36:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   37:     */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   38:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   39:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*   40:     */ import com.asinfo.as2.entities.Lote;
/*   41:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   42:     */ import com.asinfo.as2.entities.OrdenDespachoCliente;
/*   43:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   44:     */ import com.asinfo.as2.entities.Organizacion;
/*   45:     */ import com.asinfo.as2.entities.Producto;
/*   46:     */ import com.asinfo.as2.entities.ProductoMaterial;
/*   47:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   48:     */ import com.asinfo.as2.entities.RegistroPeso;
/*   49:     */ import com.asinfo.as2.entities.SaldoProducto;
/*   50:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   51:     */ import com.asinfo.as2.entities.SerieInventarioProducto;
/*   52:     */ import com.asinfo.as2.entities.Sucursal;
/*   53:     */ import com.asinfo.as2.entities.TipoAsiento;
/*   54:     */ import com.asinfo.as2.entities.Unidad;
/*   55:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
/*   56:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   57:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   58:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   59:     */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*   60:     */ import com.asinfo.as2.enumeraciones.Parametro;
/*   61:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   62:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   63:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   64:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   65:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*   66:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   67:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   68:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioDetalleAsiento;
/*   69:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*   70:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   71:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   72:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   73:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   74:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   75:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*   76:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*   77:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   78:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*   79:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*   80:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*   81:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*   82:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioSerieInventarioProducto;
/*   83:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*   84:     */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*   85:     */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*   86:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*   87:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*   88:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   89:     */ import com.asinfo.as2.util.AppUtil;
/*   90:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   91:     */ import com.asinfo.as2.utils.NodoArbol;
/*   92:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   93:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*   94:     */ import java.io.IOException;
/*   95:     */ import java.io.InputStream;
/*   96:     */ import java.math.BigDecimal;
/*   97:     */ import java.math.RoundingMode;
/*   98:     */ import java.text.SimpleDateFormat;
/*   99:     */ import java.util.ArrayList;
/*  100:     */ import java.util.Arrays;
/*  101:     */ import java.util.Calendar;
/*  102:     */ import java.util.Date;
/*  103:     */ import java.util.HashMap;
/*  104:     */ import java.util.Iterator;
/*  105:     */ import java.util.List;
/*  106:     */ import java.util.Map;
/*  107:     */ import java.util.Set;
/*  108:     */ import javax.ejb.EJB;
/*  109:     */ import javax.ejb.SessionContext;
/*  110:     */ import javax.ejb.Stateless;
/*  111:     */ import javax.ejb.TransactionAttribute;
/*  112:     */ import javax.ejb.TransactionAttributeType;
/*  113:     */ import javax.ejb.TransactionManagement;
/*  114:     */ import javax.ejb.TransactionManagementType;
/*  115:     */ import org.apache.log4j.Logger;
/*  116:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  117:     */ 
/*  118:     */ @Stateless
/*  119:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  120:     */ public class ServicioMovimientoInventarioImpl
/*  121:     */   extends AbstractServicioAS2Financiero
/*  122:     */   implements ServicioMovimientoInventario
/*  123:     */ {
/*  124:     */   private static final long serialVersionUID = 5549093623281619045L;
/*  125:     */   @EJB
/*  126:     */   private MovimientoInventarioDao movimientoInventarioDao;
/*  127:     */   @EJB
/*  128:     */   private OrdenSalidaMaterialDao ordenSalidaMaterialDao;
/*  129:     */   @EJB
/*  130:     */   private GenericoDao<DetalleMovimientoInventario> detalleMovimientoInventarioDao;
/*  131:     */   @EJB
/*  132:     */   private ServicioProducto servicioProducto;
/*  133:     */   @EJB
/*  134:     */   private ServicioPeriodo servicioPeriodo;
/*  135:     */   @EJB
/*  136:     */   private ServicioSecuencia servicioSecuencia;
/*  137:     */   @EJB
/*  138:     */   private ServicioCosteo servicioCosteo;
/*  139:     */   @EJB
/*  140:     */   private ServicioAsiento servicioAsiento;
/*  141:     */   @EJB
/*  142:     */   private ServicioInventarioProducto servicioInventarioProducto;
/*  143:     */   @EJB
/*  144:     */   private ServicioBodega servicioBodega;
/*  145:     */   @EJB
/*  146:     */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/*  147:     */   @EJB
/*  148:     */   private ServicioUnidad servicioUnidad;
/*  149:     */   @EJB
/*  150:     */   private ServicioUnidadConversion servicioUnidadConversion;
/*  151:     */   @EJB
/*  152:     */   private AsientoDao asientoDao;
/*  153:     */   @EJB
/*  154:     */   private ServicioDestinoCosto servicioDestinoCosto;
/*  155:     */   @EJB
/*  156:     */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  157:     */   @EJB
/*  158:     */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  159:     */   @EJB
/*  160:     */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  161:     */   @EJB
/*  162:     */   private ServicioLote servicioLote;
/*  163:     */   @EJB
/*  164:     */   private ServicioSerieInventarioProducto servicioSerieInventarioProducto;
/*  165:     */   @EJB
/*  166:     */   private ServicioDocumento servicioDocumento;
/*  167:     */   @EJB
/*  168:     */   private OrdenFabricacionDao ordenFabricacionDao;
/*  169:     */   @EJB
/*  170:     */   private GenericoDao<LecturaBalanza> lecturaBalanzaDao;
/*  171:     */   @EJB
/*  172:     */   private InventarioProductoDao inventarioProductoDao;
/*  173:     */   @EJB
/*  174:     */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  175:     */   @EJB
/*  176:     */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  177:     */   @EJB
/*  178:     */   private ServicioRegistroPeso servicioRegistroPeso;
/*  179:     */   @EJB
/*  180:     */   private ProductoDao productoDao;
/*  181:     */   @EJB
/*  182:     */   private DetalleAsientoDao detalleAsientoDao;
/*  183:     */   @EJB
/*  184:     */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  185:     */   @EJB
/*  186:     */   private DetalleRecepcionProveedorDao detalleRecepcionProveedorDao;
/*  187:     */   @EJB
/*  188:     */   private ServicioDetalleAsiento servicioDetalleAsiento;
/*  189:     */   @EJB
/*  190:     */   private ServicioSucursal servicioSucursal;
/*  191:     */   @EJB
/*  192:     */   private transient DetalleFacturaProveedorDao detalleFacturaProveedorDao;
/*  193:     */   
/*  194:     */   public void guardar(MovimientoInventario movimientoInventario)
/*  195:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/*  196:     */   {
/*  197: 211 */     guardar(movimientoInventario, false, false);
/*  198:     */   }
/*  199:     */   
/*  200:     */   public void guardar(MovimientoInventario movimientoInventario, boolean indicadorAprobar, boolean indicadorMovimientoInventario)
/*  201:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/*  202:     */   {
/*  203: 217 */     guardar(movimientoInventario, indicadorAprobar, indicadorMovimientoInventario, false);
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void guardar(MovimientoInventario movimientoInventario, Producto producto)
/*  207:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/*  208:     */   {
/*  209: 222 */     guardar(movimientoInventario, false, false, false, producto);
/*  210:     */   }
/*  211:     */   
/*  212:     */   public void guardar(MovimientoInventario movimientoInventario, boolean indicadorAprobar, boolean indicadorMovimientoInventario, boolean indicadorBorrador)
/*  213:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/*  214:     */   {
/*  215: 233 */     guardar(movimientoInventario, indicadorAprobar, indicadorMovimientoInventario, indicadorBorrador, null);
/*  216:     */   }
/*  217:     */   
/*  218:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  219:     */   public void guardar(MovimientoInventario movimientoInventario, boolean indicadorAprobar, boolean indicadorMovimientoInventario, boolean indicadorBorrador, Producto producto)
/*  220:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/*  221:     */   {
/*  222:     */     try
/*  223:     */     {
/*  224: 241 */       boolean indicadorAprobarMovimientoInventario = ((Boolean)ParametrosSistema.getParametro(movimientoInventario.getIdOrganizacion(), Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO)).booleanValue();
/*  225: 244 */       if (movimientoInventario.getEstado().equals(Estado.REGISTRADO_DATOS)) {
/*  226: 245 */         eliminarInventariosProducto(movimientoInventario);
/*  227:     */       }
/*  228: 247 */       if ((movimientoInventario.getEstado().equals(Estado.ELABORADO)) && (!indicadorAprobarMovimientoInventario)) {
/*  229: 248 */         agregarInventariosProducto(movimientoInventario);
/*  230:     */       }
/*  231:     */       LecturaBalanza lb;
/*  232: 251 */       if (((indicadorMovimientoInventario) && (indicadorAprobarMovimientoInventario) && (!indicadorAprobar)) || (indicadorBorrador))
/*  233:     */       {
/*  234: 252 */         if (movimientoInventario.getId() == 0) {
/*  235: 253 */           cargarSecuencia(movimientoInventario);
/*  236:     */         }
/*  237: 257 */         for (DetalleMovimientoInventario dmi : movimientoInventario.getDetalleMovimientosInventario())
/*  238:     */         {
/*  239: 259 */           if ((dmi.getCantidad().compareTo(BigDecimal.ZERO) == 0) && (!indicadorBorrador)) {
/*  240: 260 */             dmi.setEliminado(true);
/*  241:     */           }
/*  242: 263 */           if (!dmi.isEliminado()) {
/*  243: 264 */             this.detalleMovimientoInventarioDao.guardar(dmi);
/*  244:     */           }
/*  245: 266 */           for (Iterator localIterator2 = dmi.getListaLecturaBalanza().iterator(); localIterator2.hasNext();)
/*  246:     */           {
/*  247: 266 */             lb = (LecturaBalanza)localIterator2.next();
/*  248: 267 */             if (dmi.isEliminado()) {
/*  249: 268 */               lb.setEliminado(true);
/*  250:     */             }
/*  251: 270 */             this.lecturaBalanzaDao.guardar(lb);
/*  252:     */           }
/*  253: 272 */           if (dmi.isEliminado()) {
/*  254: 273 */             this.detalleMovimientoInventarioDao.guardar(dmi);
/*  255:     */           }
/*  256:     */         }
/*  257: 277 */         this.movimientoInventarioDao.guardar(movimientoInventario);
/*  258:     */       }
/*  259:     */       else
/*  260:     */       {
/*  261: 280 */         if (indicadorAprobar)
/*  262:     */         {
/*  263: 281 */           movimientoInventario = this.movimientoInventarioDao.cargarDetalle(movimientoInventario.getId(), producto);
/*  264: 282 */           for (DetalleMovimientoInventario detalleMovimientoInventario : movimientoInventario.getDetalleMovimientosInventario()) {
/*  265: 283 */             if (detalleMovimientoInventario.getInventarioProducto() == null)
/*  266:     */             {
/*  267: 284 */               InventarioProducto inventarioProducto = new InventarioProducto();
/*  268: 285 */               inventarioProducto.setIdOrganizacion(movimientoInventario.getIdOrganizacion());
/*  269: 286 */               inventarioProducto.setIdSucursal(movimientoInventario.getSucursal().getId());
/*  270: 287 */               inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/*  271: 288 */               inventarioProducto.setDocumento(movimientoInventario.getDocumento());
/*  272: 289 */               inventarioProducto.setNumeroDocumento(movimientoInventario.getNumero());
/*  273: 290 */               inventarioProducto.setProducto(detalleMovimientoInventario.getProducto());
/*  274: 291 */               inventarioProducto.setLote(detalleMovimientoInventario.getLote());
/*  275: 292 */               detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/*  276:     */             }
/*  277:     */           }
/*  278: 296 */           for (DetalleMovimientoInventario dmi : movimientoInventario.getDetalleMovimientosInventario()) {
/*  279: 297 */             this.servicioInventarioProducto.guardar(dmi.getInventarioProducto());
/*  280:     */           }
/*  281:     */         }
/*  282: 301 */         actualizarInventarioProducto(movimientoInventario);
/*  283:     */         
/*  284: 303 */         validar(movimientoInventario);
/*  285: 305 */         if (!indicadorAprobar) {
/*  286: 308 */           this.servicioVerificadorInventario.actualizarSaldoProducto(movimientoInventario, true, producto);
/*  287:     */         }
/*  288: 311 */         this.servicioVerificadorInventario.actualizarSaldoProducto(movimientoInventario, false, producto);
/*  289: 313 */         if (!indicadorAprobar) {
/*  290: 314 */           cargarSecuencia(movimientoInventario);
/*  291:     */         }
/*  292: 317 */         this.servicioOrdenTrabajoMantenimiento.actualizarOrdenTrabajo(movimientoInventario);
/*  293:     */         
/*  294:     */ 
/*  295: 320 */         setearDescripcion(movimientoInventario);
/*  296: 321 */         this.movimientoInventarioDao.guardar(movimientoInventario);
/*  297: 324 */         for (int i = 0; i < movimientoInventario.getDetalleMovimientosInventario().size(); i++)
/*  298:     */         {
/*  299: 325 */           DetalleMovimientoInventario dmi = (DetalleMovimientoInventario)movimientoInventario.getDetalleMovimientosInventario().get(i);
/*  300: 327 */           if (dmi.getInventarioProducto() != null) {
/*  301: 328 */             dmi.getInventarioProducto().setIdOrganizacion(dmi.getMovimientoInventario().getIdOrganizacion());
/*  302:     */           }
/*  303: 331 */           if (dmi.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
/*  304: 332 */             dmi.setEliminado(true);
/*  305:     */           }
/*  306: 335 */           if ((dmi.isEliminado()) && (dmi.getInventarioProducto() != null)) {
/*  307: 336 */             dmi.getInventarioProducto().setEliminado(true);
/*  308:     */           }
/*  309: 340 */           if (dmi.getProducto().getIndicadorSerie().booleanValue())
/*  310:     */           {
/*  311: 344 */             Object lista = dmi.getInventarioProducto().getInventarioProductoTransferencia() != null ? dmi.getInventarioProducto().getInventarioProductoTransferencia().getListaSerieProducto() : dmi.getInventarioProducto().getListaSerieProducto();
/*  312: 346 */             for (SerieInventarioProducto serie : (List)lista) {
/*  313: 347 */               this.servicioSerieInventarioProducto.guardar(serie);
/*  314:     */             }
/*  315:     */           }
/*  316: 351 */           if (dmi.getInventarioProducto() != null)
/*  317:     */           {
/*  318: 352 */             dmi.getInventarioProducto().setNumeroDocumento(movimientoInventario.getNumero());
/*  319: 354 */             if (dmi.getInventarioProducto().getInventarioProductoTransferencia() != null) {
/*  320: 355 */               this.servicioInventarioProducto.guardar(dmi.getInventarioProducto().getInventarioProductoTransferencia());
/*  321:     */             }
/*  322: 358 */             this.servicioInventarioProducto.guardar(dmi.getInventarioProducto());
/*  323:     */           }
/*  324: 361 */           if (!dmi.isEliminado())
/*  325:     */           {
/*  326: 362 */             this.detalleMovimientoInventarioDao.guardar(dmi);
/*  327: 364 */             if (dmi.getDetalleMovimientoInventarioPadre() != null)
/*  328:     */             {
/*  329: 365 */               detallePadre = (DetalleMovimientoInventario)this.detalleMovimientoInventarioDao.buscarPorId(DetalleMovimientoInventario.class, 
/*  330: 366 */                 Integer.valueOf(dmi.getDetalleMovimientoInventarioPadre().getId()));
/*  331: 367 */               this.detalleMovimientoInventarioDao.detach((EntidadBase)detallePadre);
/*  332: 368 */               this.movimientoInventarioDao.actualizarCantidadDevueltaDetalle((DetalleMovimientoInventario)detallePadre, dmi.getCantidad());
/*  333:     */             }
/*  334:     */           }
/*  335: 371 */           for (Object detallePadre = dmi.getListaLecturaBalanza().iterator(); ((Iterator)detallePadre).hasNext();)
/*  336:     */           {
/*  337: 371 */             LecturaBalanza lb = (LecturaBalanza)((Iterator)detallePadre).next();
/*  338: 372 */             if (dmi.isEliminado()) {
/*  339: 373 */               lb.setEliminado(true);
/*  340:     */             }
/*  341: 375 */             this.lecturaBalanzaDao.guardar(lb);
/*  342:     */           }
/*  343: 377 */           if (dmi.isEliminado())
/*  344:     */           {
/*  345: 378 */             this.detalleMovimientoInventarioDao.guardar(dmi);
/*  346:     */             
/*  347:     */ 
/*  348: 381 */             movimientoInventario.getDetalleMovimientosInventario().remove(i);
/*  349: 382 */             i--;
/*  350:     */           }
/*  351:     */         }
/*  352: 388 */         boolean usaBalanza = ParametrosSistema.getDespachoUsaBalanza(movimientoInventario.getIdOrganizacion()).booleanValue();
/*  353: 389 */         if (!usaBalanza) {
/*  354: 390 */           this.servicioVerificadorInventario.abrirCerrarOrdenSalidaMaterial(movimientoInventario);
/*  355:     */         }
/*  356: 394 */         if ((ParametrosSistema.getGeneracionDeCostosAutomatica(movimientoInventario.getIdOrganizacion()).booleanValue()) && 
/*  357: 395 */           (movimientoInventario.isIndicadorGenerarCostos())) {
/*  358: 396 */           this.servicioCosteo.generarCostos(movimientoInventario, ParametrosSistema.isCosteoPorBodega(movimientoInventario.getIdOrganizacion()).booleanValue());
/*  359:     */         }
/*  360: 399 */         DocumentoBase documentoBase = movimientoInventario.getDocumento().getDocumentoBase();
/*  361: 404 */         if ((!movimientoInventario.getIndicadorSaldoInicial()) && (movimientoInventario.getDocumento().isIndicadorContabilizar())) {
/*  362: 405 */           if ((documentoBase == DocumentoBase.AJUSTE_INVENTARIO) || (documentoBase == DocumentoBase.INGRESO_PRODUCCION) || (documentoBase == DocumentoBase.TRANSFERENCIA_BODEGA) || (documentoBase == DocumentoBase.SALIDA_MATERIAL))
/*  363:     */           {
/*  364: 408 */             contabilizar(movimientoInventario);
/*  365:     */           }
/*  366: 411 */           else if ((ParametrosSistema.getContabilizacionConsumosBodega(movimientoInventario.getIdOrganizacion()).booleanValue()) && (documentoBase == DocumentoBase.CONSUMO_BODEGA))
/*  367:     */           {
/*  368: 413 */             Object listamovimientoInventario = new ArrayList();
/*  369: 414 */             ((List)listamovimientoInventario).add(movimientoInventario);
/*  370: 415 */             contabilizarConsumoBodega((List)listamovimientoInventario);
/*  371:     */           }
/*  372:     */         }
/*  373:     */       }
/*  374:     */     }
/*  375:     */     catch (ExcepcionAS2Financiero e)
/*  376:     */     {
/*  377: 422 */       this.context.setRollbackOnly();
/*  378: 423 */       throw e;
/*  379:     */     }
/*  380:     */     catch (ExcepcionAS2 e)
/*  381:     */     {
/*  382: 425 */       this.context.setRollbackOnly();
/*  383: 426 */       throw e;
/*  384:     */     }
/*  385:     */     catch (AS2Exception e)
/*  386:     */     {
/*  387: 428 */       this.context.setRollbackOnly();
/*  388: 429 */       throw e;
/*  389:     */     }
/*  390:     */     catch (Exception e)
/*  391:     */     {
/*  392: 431 */       this.context.setRollbackOnly();
/*  393: 432 */       throw new ExcepcionAS2(e);
/*  394:     */     }
/*  395:     */   }
/*  396:     */   
/*  397:     */   private void eliminarInventariosProducto(MovimientoInventario movimientoInventario)
/*  398:     */   {
/*  399: 437 */     for (DetalleMovimientoInventario detalle : movimientoInventario.getDetalleMovimientosInventario())
/*  400:     */     {
/*  401: 438 */       if ((detalle.getInventarioProducto() != null) && (detalle.getInventarioProducto().getLote() != null)) {
/*  402: 439 */         detalle.setLote(detalle.getInventarioProducto().getLote());
/*  403:     */       }
/*  404: 441 */       detalle.setInventarioProducto(null);
/*  405:     */     }
/*  406:     */   }
/*  407:     */   
/*  408:     */   public void agregarInventariosProducto(MovimientoInventario movimientoInventario)
/*  409:     */   {
/*  410: 447 */     for (DetalleMovimientoInventario detalle : movimientoInventario.getDetalleMovimientosInventario()) {
/*  411: 448 */       if ((!detalle.isEliminado()) && 
/*  412: 449 */         (detalle.getInventarioProducto() == null))
/*  413:     */       {
/*  414: 450 */         InventarioProducto inventarioProducto = new InventarioProducto();
/*  415: 451 */         inventarioProducto.setOperacion(movimientoInventario.getDocumento().getOperacion());
/*  416: 452 */         inventarioProducto.setDetalleMovimientoInventario(detalle);
/*  417: 453 */         inventarioProducto.setProducto(detalle.getProducto());
/*  418: 454 */         detalle.setInventarioProducto(inventarioProducto);
/*  419: 455 */         inventarioProducto.setBodega(detalle.getBodegaOrigen());
/*  420: 456 */         inventarioProducto.setCantidad(detalle.getCantidad());
/*  421: 457 */         if (detalle.getLote() != null) {
/*  422: 458 */           inventarioProducto.setLote(detalle.getLote());
/*  423:     */         }
/*  424:     */       }
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   private void actualizarInventarioProducto(MovimientoInventario movimientoInventario)
/*  429:     */     throws ExcepcionAS2
/*  430:     */   {
/*  431: 473 */     for (DetalleMovimientoInventario dmi : movimientoInventario.getDetalleMovimientosInventario()) {
/*  432: 475 */       if (!dmi.isEliminado()) {
/*  433: 476 */         actualizarInventarioProductoDetalle(dmi);
/*  434:     */       }
/*  435:     */     }
/*  436:     */   }
/*  437:     */   
/*  438:     */   public void actualizarInventarioProductoDetalle(DetalleMovimientoInventario dmi)
/*  439:     */     throws ExcepcionAS2
/*  440:     */   {
/*  441: 486 */     MovimientoInventario movimientoInventario = dmi.getMovimientoInventario();
/*  442: 487 */     InventarioProducto inventarioProducto = null;
/*  443: 488 */     if (dmi.getInventarioProducto() != null)
/*  444:     */     {
/*  445: 489 */       inventarioProducto = dmi.getInventarioProducto();
/*  446: 490 */       this.inventarioProductoDao.detach(dmi.getInventarioProducto());
/*  447:     */     }
/*  448:     */     else
/*  449:     */     {
/*  450: 492 */       inventarioProducto = new InventarioProducto();
/*  451: 493 */       dmi.setInventarioProducto(inventarioProducto);
/*  452:     */     }
/*  453: 495 */     inventarioProducto.setDetalleMovimientoInventario(dmi);
/*  454: 496 */     BigDecimal cantidad = dmi.getCantidad();
/*  455:     */     
/*  456:     */ 
/*  457: 499 */     cantidad = this.servicioProducto.convierteUnidad(dmi.getUnidadConversion(), dmi.getProducto().getUnidad(), dmi.getProducto().getIdProducto(), dmi
/*  458: 500 */       .getCantidad());
/*  459: 501 */     inventarioProducto.setCantidad(cantidad);
/*  460: 502 */     inventarioProducto.setCantidadDocumento(dmi.getCantidad());
/*  461: 503 */     inventarioProducto.setUnidadDocumento(dmi.getUnidadConversion().getNombre());
/*  462:     */     
/*  463: 505 */     inventarioProducto.setFecha(movimientoInventario.getFecha());
/*  464: 506 */     inventarioProducto.setDocumento(movimientoInventario.getDocumento());
/*  465: 507 */     inventarioProducto.setIdOrganizacion(movimientoInventario.getIdOrganizacion());
/*  466: 508 */     if ((movimientoInventario.getMovimientoInventarioPadre() != null) && 
/*  467: 509 */       (!movimientoInventario.getDocumento().getDocumentoBase().equals(DocumentoBase.TRANSFORMACION_PRODUCTO))) {
/*  468: 510 */       inventarioProducto.setOperacion(movimientoInventario.getDocumento().getOperacion() * -1);
/*  469:     */     } else {
/*  470: 512 */       inventarioProducto.setOperacion(movimientoInventario.getDocumento().getOperacion());
/*  471:     */     }
/*  472: 514 */     inventarioProducto.setIndicadorGeneraCosto(inventarioProducto
/*  473: 515 */       .getOperacion() == -1 ? false : movimientoInventario.getDocumento().isIndicadorGeneraCosto());
/*  474: 516 */     inventarioProducto.setIdSucursal(dmi.getId());
/*  475:     */     
/*  476: 518 */     inventarioProducto.setProducto(dmi.getProducto());
/*  477: 519 */     inventarioProducto.setNumeroDocumento(movimientoInventario.getNumero());
/*  478: 521 */     if ((movimientoInventario.getDocumento().getDocumentoBase().equals(DocumentoBase.TRANSFORMACION_PRODUCTO)) && 
/*  479: 522 */       (movimientoInventario.getDocumento().getOperacion() == 1)) {
/*  480: 523 */       inventarioProducto.setBodega(dmi.getBodegaDestino());
/*  481:     */     } else {
/*  482: 525 */       inventarioProducto.setBodega(dmi.getBodegaOrigen());
/*  483:     */     }
/*  484: 527 */     if (inventarioProducto.getBodega() == null) {
/*  485: 528 */       inventarioProducto.setBodega(dmi.getBodegaOrigen());
/*  486:     */     }
/*  487: 529 */     inventarioProducto.setCantidadDocumento(dmi.getCantidad());
/*  488: 530 */     inventarioProducto.setProyecto(movimientoInventario.getProyecto());
/*  489: 532 */     if (movimientoInventario.getDocumento().isIndicadorGeneraCosto()) {
/*  490: 533 */       inventarioProducto.setCosto(dmi.getCosto().multiply(dmi.getCantidad()));
/*  491:     */     }
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void guardaTransferenciaBodegaIngreso(MovimientoInventario transferenciaEgreso, boolean borrador)
/*  495:     */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/*  496:     */   {
/*  497: 540 */     MovimientoInventario mi = buscarPorId(Integer.valueOf(transferenciaEgreso.getId()));
/*  498: 541 */     if (mi.getEstado().equals(Estado.ELABORADO)) {
/*  499: 542 */       guardaTransferenciaBodegaIngreso(transferenciaEgreso, false, borrador);
/*  500:     */     } else {
/*  501: 544 */       throw new ExcepcionAS2Inventario(mi.getNumero(), "msg_error_recepcion_transferencia");
/*  502:     */     }
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void guardaTransferenciaBodegaIngreso(MovimientoInventario transferenciaEgreso, boolean anulacion, boolean borrador)
/*  506:     */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/*  507:     */   {
/*  508: 558 */     for (DetalleMovimientoInventario dmi : transferenciaEgreso.getDetalleMovimientosInventario()) {
/*  509: 559 */       if (!borrador)
/*  510:     */       {
/*  511: 560 */         InventarioProducto inventarioProducto = dmi.getInventarioProducto();
/*  512:     */         
/*  513: 562 */         ipTran = new InventarioProducto();
/*  514:     */         
/*  515: 564 */         inventarioProducto.setIndicadorTransferencia(true);
/*  516: 565 */         inventarioProducto.setInventarioProductoTransferencia(ipTran);
/*  517: 566 */         inventarioProducto.setCosto(BigDecimal.ZERO);
/*  518:     */         
/*  519: 568 */         ipTran.setIndicadorTransferencia(true);
/*  520: 569 */         ipTran.setCantidad(inventarioProducto.getCantidad());
/*  521: 570 */         ipTran.setFecha(inventarioProducto.getFecha());
/*  522: 571 */         ipTran.setDocumento(inventarioProducto.getDocumento());
/*  523: 572 */         ipTran.setIdOrganizacion(inventarioProducto.getIdOrganizacion());
/*  524: 573 */         ipTran.setDescripcion(transferenciaEgreso.getDescripcion());
/*  525: 574 */         ipTran.setOperacion(inventarioProducto.getOperacion() * -1);
/*  526: 575 */         ipTran.setIdSucursal(inventarioProducto.getIdSucursal());
/*  527: 576 */         ipTran.setBodega(anulacion ? dmi.getBodegaOrigen() : dmi.getBodegaDestino());
/*  528: 577 */         ipTran.setProducto(inventarioProducto.getProducto());
/*  529: 578 */         ipTran.setNumeroDocumento((anulacion ? "Anulación " : "Recepción ") + inventarioProducto.getNumeroDocumento());
/*  530: 579 */         ipTran.setCantidadDocumento(dmi.getCantidad());
/*  531: 580 */         ipTran.setUnidadDocumento(dmi.getProducto().getUnidad().getNombre());
/*  532: 581 */         ipTran.setLote(inventarioProducto.getLote());
/*  533: 582 */         ipTran.setCosto(inventarioProducto.getCosto());
/*  534: 583 */         ipTran.setIndicadorGeneraCosto(ParametrosSistema.isCosteoPorBodega(transferenciaEgreso.getIdOrganizacion()).booleanValue());
/*  535: 584 */         ipTran.setProyecto(inventarioProducto.getProyecto());
/*  536: 585 */         ipTran.setHoraCreacion(Calendar.getInstance().getTime());
/*  537: 587 */         if (anulacion)
/*  538:     */         {
/*  539: 588 */           ipTran.setIndicadorAnulacion(true);
/*  540: 589 */           ipTran.setDetalleMovimientoInventarioAjuste(dmi);
/*  541: 592 */           if (dmi.getDetalleOrdenSalidaMaterial() != null) {
/*  542: 593 */             this.ordenSalidaMaterialDao.actualizarCantidadConsumida(dmi.getDetalleOrdenSalidaMaterial(), inventarioProducto.getCantidad());
/*  543:     */           }
/*  544:     */         }
/*  545: 600 */         if (ipTran.getProducto().getIndicadorSerie().booleanValue()) {
/*  546: 601 */           for (SerieInventarioProducto serieInventarioProducto : inventarioProducto.getListaSerieProducto())
/*  547:     */           {
/*  548: 602 */             SerieInventarioProducto spTra = new SerieInventarioProducto();
/*  549: 603 */             spTra.setInventarioProducto(ipTran);
/*  550: 604 */             spTra.setSerieProducto(serieInventarioProducto.getSerieProducto());
/*  551:     */             
/*  552: 606 */             ipTran.getListaSerieProducto().add(spTra);
/*  553:     */           }
/*  554:     */         }
/*  555:     */       }
/*  556:     */     }
/*  557:     */     InventarioProducto ipTran;
/*  558: 612 */     if (anulacion)
/*  559:     */     {
/*  560: 613 */       transferenciaEgreso.setEstado(Estado.ANULADO);
/*  561:     */       
/*  562: 615 */       this.servicioVerificadorInventario.abrirCerrarOrdenSalidaMaterial(transferenciaEgreso);
/*  563:     */     }
/*  564: 617 */     else if (!borrador)
/*  565:     */     {
/*  566: 618 */       transferenciaEgreso.setEstado(Estado.PROCESADO);
/*  567:     */     }
/*  568: 620 */     transferenciaEgreso.setIndicadorRecepcionTransferencia(true);
/*  569: 621 */     guardar(transferenciaEgreso, false, false, borrador);
/*  570:     */   }
/*  571:     */   
/*  572:     */   public void eliminar(MovimientoInventario movimientoInventario)
/*  573:     */   {
/*  574: 631 */     this.movimientoInventarioDao.eliminar(movimientoInventario);
/*  575:     */   }
/*  576:     */   
/*  577:     */   public MovimientoInventario buscarPorId(Integer idMovimientoInventario)
/*  578:     */   {
/*  579: 642 */     return (MovimientoInventario)this.movimientoInventarioDao.buscarPorId(idMovimientoInventario);
/*  580:     */   }
/*  581:     */   
/*  582:     */   public MovimientoInventario cargarDetalle(Integer idMovimientoInventario)
/*  583:     */   {
/*  584: 652 */     return this.movimientoInventarioDao.cargarDetalle(idMovimientoInventario.intValue());
/*  585:     */   }
/*  586:     */   
/*  587:     */   public MovimientoInventario cargarDetalle(int idMovimientoInventario, Producto producto)
/*  588:     */   {
/*  589: 657 */     return this.movimientoInventarioDao.cargarDetalle(idMovimientoInventario, producto);
/*  590:     */   }
/*  591:     */   
/*  592:     */   public List<MovimientoInventario> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  593:     */   {
/*  594: 669 */     return this.movimientoInventarioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void cargarSecuencia(MovimientoInventario movimientoInventario)
/*  598:     */     throws ExcepcionAS2, ExcepcionAS2Inventario
/*  599:     */   {
/*  600: 681 */     if (movimientoInventario.getNumero().equals(""))
/*  601:     */     {
/*  602: 682 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(movimientoInventario.getDocumento().getIdDocumento(), movimientoInventario
/*  603: 683 */         .getFecha());
/*  604: 684 */       movimientoInventario.setNumero(numero);
/*  605:     */     }
/*  606:     */   }
/*  607:     */   
/*  608:     */   private void validar(MovimientoInventario movimientoInventario)
/*  609:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Inventario, AS2Exception
/*  610:     */   {
/*  611: 699 */     this.servicioPeriodo.buscarPorFecha(movimientoInventario.getFecha(), AppUtil.getOrganizacion().getIdOrganizacion(), movimientoInventario
/*  612: 700 */       .getDocumento().getDocumentoBase());
/*  613: 701 */     for (DetalleMovimientoInventario dmi : movimientoInventario.getDetalleMovimientosInventario()) {
/*  614: 702 */       if ((!dmi.isEliminado()) && 
/*  615: 703 */         (movimientoInventario.getDocumento().getDocumentoBase() == DocumentoBase.TRANSFERENCIA_BODEGA) && 
/*  616: 704 */         (dmi.getBodegaOrigen().equals(dmi.getBodegaDestino()))) {
/*  617: 705 */         throw new ExcepcionAS2Inventario("msg_error_bodega_destino_no_valida", " Producto: " + dmi.getProducto().getNombre());
/*  618:     */       }
/*  619:     */     }
/*  620: 728 */     this.servicioSerieInventarioProducto.validar(movimientoInventario);
/*  621:     */   }
/*  622:     */   
/*  623:     */   public List<DetalleFacturaCliente> getDetalleFacturaDevolucionCliente(int idFacturaCliente, int idDevolucionCliente)
/*  624:     */   {
/*  625: 738 */     return this.movimientoInventarioDao.getDetalleFacturaDevolucionCliente(idFacturaCliente, idDevolucionCliente);
/*  626:     */   }
/*  627:     */   
/*  628:     */   public List<DetalleFacturaProveedor> getDetalleFacturaDevolucionProveedor(int idFacturaProveedor, int idDevolucionProveedor)
/*  629:     */   {
/*  630: 748 */     return this.movimientoInventarioDao.getDetalleFacturaDevolucionProveedor(idFacturaProveedor, idDevolucionProveedor);
/*  631:     */   }
/*  632:     */   
/*  633:     */   public boolean verificaAjusteTomaFisica(int idMovimientoInventario)
/*  634:     */   {
/*  635: 758 */     return this.movimientoInventarioDao.verificaAjusteTomaFisica(idMovimientoInventario);
/*  636:     */   }
/*  637:     */   
/*  638:     */   public void contabilizar(MovimientoInventario movimientoInventario)
/*  639:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  640:     */   {
/*  641: 763 */     if (movimientoInventario.getDocumento().isIndicadorContabilizar())
/*  642:     */     {
/*  643: 765 */       DocumentoBase documentoBase = movimientoInventario.getDocumento().getDocumentoBase();
/*  644:     */       
/*  645: 767 */       DocumentoBase documentoBaseContrapartida = documentoBase == DocumentoBase.SALIDA_MATERIAL ? DocumentoBase.INGRESO_PRODUCCION : documentoBase;
/*  646:     */       try
/*  647:     */       {
/*  648: 771 */         Date fechaContabilizacion = movimientoInventario.getFecha();
/*  649:     */         Asiento asiento;
/*  650: 773 */         if (movimientoInventario.getAsiento() != null)
/*  651:     */         {
/*  652: 774 */           Asiento asiento = movimientoInventario.getAsiento();
/*  653: 775 */           asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/*  654: 777 */           for (DetalleAsiento detalle : asiento.getListaDetalleAsiento()) {
/*  655: 778 */             detalle.setEliminado(true);
/*  656:     */           }
/*  657:     */         }
/*  658:     */         else
/*  659:     */         {
/*  660: 781 */           asiento = new Asiento();
/*  661: 782 */           asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  662: 783 */           asiento.setSucursal(AppUtil.getSucursal());
/*  663: 784 */           asiento.setEstado(Estado.ELABORADO);
/*  664: 785 */           TipoAsiento tipoAsiento = movimientoInventario.getDocumento().getTipoAsiento();
/*  665: 786 */           asiento.setTipoAsiento(tipoAsiento);
/*  666: 787 */           asiento.setIndicadorAutomatico(true);
/*  667:     */         }
/*  668: 791 */         String concepto = "";
/*  669: 792 */         concepto = movimientoInventario.getDocumento().getNombre().trim() + " # " + movimientoInventario.getNumero();
/*  670: 793 */         asiento.setConcepto(concepto);
/*  671: 794 */         asiento.setFecha(fechaContabilizacion);
/*  672: 795 */         List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/*  673:     */         Documento documento;
/*  674: 799 */         if (movimientoInventario.getId() > 0)
/*  675:     */         {
/*  676: 801 */           documento = movimientoInventario.getDocumento();
/*  677:     */           
/*  678: 803 */           boolean indicadorTransformacionDevolucion = (documentoBase == DocumentoBase.TRANSFORMACION_PRODUCTO) && (movimientoInventario.getDocumento().getOperacion() == 1);
/*  679:     */           
/*  680: 805 */           List<Integer> listaMovimientoInventario = Arrays.asList(new Integer[] { Integer.valueOf(movimientoInventario.getIdMovimientoInventario()) });
/*  681:     */           
/*  682:     */ 
/*  683: 808 */           List<DetalleInterfazContableProceso> lista = new ArrayList();
/*  684:     */           
/*  685:     */ 
/*  686:     */ 
/*  687:     */ 
/*  688:     */ 
/*  689: 814 */           DocumentoContabilizacion documentoContabilizacionInventarioProducto = (DocumentoContabilizacion)this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(movimientoInventario.getIdOrganizacion(), DocumentoBase.AJUSTE_INVENTARIO, ProcesoContabilizacionEnum.INVENTARIO_PRODUCTO).get(0);
/*  690:     */           
/*  691:     */ 
/*  692: 817 */           List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(movimientoInventario
/*  693: 818 */             .getIdOrganizacion(), documentoBase == DocumentoBase.TRANSFORMACION_PRODUCTO ? DocumentoBase.AJUSTE_INVENTARIO : documentoBase);
/*  694:     */           
/*  695:     */ 
/*  696:     */ 
/*  697: 822 */           boolean operacionTmp = false;
/*  698: 823 */           lista = this.movimientoInventarioDao.getInterfazMovimientoInventarioDimensiones(listaMovimientoInventario, DocumentoBase.AJUSTE_INVENTARIO, false);
/*  699:     */           
/*  700: 825 */           operacionTmp = movimientoInventario.getDocumento().getOperacion() != 1;
/*  701: 826 */           listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacionInventarioProducto, listaCriterioDistribucion, operacionTmp));
/*  702:     */           DocumentoContabilizacion documentoContabilizacionInventarioMotivoAjuste;
/*  703:     */           List<DocumentoContabilizacion> listaDocumentoContabilizacionContrapartida;
/*  704: 833 */           if ((documentoBase == DocumentoBase.TRANSFORMACION_PRODUCTO) || ((documentoBase == DocumentoBase.TRANSFERENCIA_BODEGA) && 
/*  705: 834 */             (movimientoInventario.getEstado() != Estado.ELABORADO)))
/*  706:     */           {
/*  707: 835 */             List<DocumentoContabilizacion> listaDocumentoContabilizacionContrapartida = new ArrayList();
/*  708: 836 */             listaDocumentoContabilizacionContrapartida.add(documentoContabilizacionInventarioProducto);
/*  709: 837 */             operacionTmp = !operacionTmp;
/*  710:     */           }
/*  711: 838 */           else if (documentoBase == DocumentoBase.AJUSTE_INVENTARIO)
/*  712:     */           {
/*  713: 843 */             documentoContabilizacionInventarioMotivoAjuste = (DocumentoContabilizacion)this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(movimientoInventario.getIdOrganizacion(), DocumentoBase.AJUSTE_INVENTARIO, ProcesoContabilizacionEnum.MOTIVO_AJUSTE_INVENTARIO).get(0);
/*  714: 844 */             List<DocumentoContabilizacion> listaDocumentoContabilizacionContrapartida = new ArrayList();
/*  715: 845 */             listaDocumentoContabilizacionContrapartida.add(documentoContabilizacionInventarioMotivoAjuste);
/*  716:     */           }
/*  717:     */           else
/*  718:     */           {
/*  719: 847 */             if ((documentoBase == DocumentoBase.SALIDA_MATERIAL) || (documentoBase == DocumentoBase.INGRESO_PRODUCCION) || ((documentoBase == DocumentoBase.TRANSFERENCIA_BODEGA) && 
/*  720: 848 */               (movimientoInventario.getEstado() == Estado.ELABORADO))) {
/*  721: 849 */               operacionTmp = !operacionTmp;
/*  722:     */             }
/*  723: 853 */             listaDocumentoContabilizacionContrapartida = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(movimientoInventario.getIdOrganizacion(), documentoBaseContrapartida);
/*  724:     */           }
/*  725: 855 */           for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacionContrapartida)
/*  726:     */           {
/*  727: 857 */             if (documentoBase == DocumentoBase.TRANSFORMACION_PRODUCTO)
/*  728:     */             {
/*  729: 858 */               listaMovimientoInventario = new ArrayList();
/*  730: 859 */               listaMovimientoInventario.add(Integer.valueOf(movimientoInventario.getMovimientoInventarioPadre().getId()));
/*  731:     */             }
/*  732: 862 */             lista = this.movimientoInventarioDao.getInterfazMovimientoInventarioDimensiones(listaMovimientoInventario, DocumentoBase.AJUSTE_INVENTARIO, true);
/*  733:     */             
/*  734:     */ 
/*  735: 865 */             listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, operacionTmp));
/*  736:     */           }
/*  737: 869 */           if (indicadorTransformacionDevolucion)
/*  738:     */           {
/*  739: 870 */             BigDecimal diferencia = calcularDiferencia(listaDetalleAsiento);
/*  740: 871 */             if (diferencia.compareTo(BigDecimal.ZERO) != 0)
/*  741:     */             {
/*  742: 876 */               DocumentoContabilizacion documentoContabilizacionDiferenciaMovimiento = (DocumentoContabilizacion)this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(movimientoInventario.getIdOrganizacion(), DocumentoBase.AJUSTE_INVENTARIO, ProcesoContabilizacionEnum.DIFERENCIA_MOVIMIENTO).get(0);
/*  743:     */               
/*  744: 878 */               Sucursal sucursal = asiento.getSucursal();
/*  745:     */               
/*  746: 880 */               String descripcion = documento.getNombre() + " #" + movimientoInventario.getNumero() + " " + movimientoInventario.getDescripcion();
/*  747:     */               
/*  748: 882 */               DetalleInterfazContableProceso detalleInterfazDiferencia = new DetalleInterfazContableProcesoMovimientoInventario(Integer.valueOf(documento.getIdDocumento()), documento.getNombre(), Integer.valueOf(sucursal.getId()), sucursal.getNombre(), null, null, null, null, null, null, null, null, null, null, descripcion, diferencia);
/*  749:     */               
/*  750: 884 */               lista = new ArrayList();
/*  751: 885 */               lista.add(detalleInterfazDiferencia);
/*  752: 886 */               listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacionDiferenciaMovimiento, listaCriterioDistribucion, false));
/*  753:     */             }
/*  754:     */           }
/*  755:     */         }
/*  756:     */         else
/*  757:     */         {
/*  758: 894 */           listaDetalleAsiento = this.movimientoInventarioDao.getInterfazTextilPadilla(movimientoInventario);
/*  759: 895 */           for (DetalleAsiento detalle : listaDetalleAsiento) {
/*  760: 896 */             detalle.setAsiento(asiento);
/*  761:     */           }
/*  762:     */         }
/*  763: 900 */         asiento.getListaDetalleAsiento().addAll(listaDetalleAsiento);
/*  764:     */         
/*  765: 902 */         this.servicioInterfazContableProceso.ajustarDiferencias(asiento);
/*  766:     */         
/*  767:     */ 
/*  768: 905 */         this.servicioAsiento.guardar(asiento);
/*  769: 907 */         if (((documentoBase != DocumentoBase.TRANSFERENCIA_BODEGA) || (movimientoInventario.isIndicadorRecepcionTransferencia())) && 
/*  770: 908 */           (movimientoInventario.getEstado() != Estado.ANULADO)) {
/*  771: 909 */           movimientoInventario.setEstado(Estado.CONTABILIZADO);
/*  772:     */         }
/*  773: 912 */         movimientoInventario.setFechaContabilizacion(fechaContabilizacion);
/*  774:     */         
/*  775: 914 */         movimientoInventario.setAsiento(asiento);
/*  776: 915 */         this.movimientoInventarioDao.guardar(movimientoInventario);
/*  777:     */       }
/*  778:     */       catch (ExcepcionAS2Financiero e)
/*  779:     */       {
/*  780: 918 */         this.context.setRollbackOnly();
/*  781: 919 */         throw e;
/*  782:     */       }
/*  783:     */       catch (ExcepcionAS2Inventario e)
/*  784:     */       {
/*  785: 921 */         this.context.setRollbackOnly();
/*  786: 922 */         throw e;
/*  787:     */       }
/*  788:     */       catch (ExcepcionAS2 e)
/*  789:     */       {
/*  790: 924 */         this.context.setRollbackOnly();
/*  791: 925 */         throw e;
/*  792:     */       }
/*  793:     */       catch (AS2Exception e)
/*  794:     */       {
/*  795: 927 */         this.context.setRollbackOnly();
/*  796: 928 */         throw e;
/*  797:     */       }
/*  798:     */       catch (Exception e)
/*  799:     */       {
/*  800: 930 */         this.context.setRollbackOnly();
/*  801: 931 */         throw new ExcepcionAS2(e);
/*  802:     */       }
/*  803:     */     }
/*  804:     */   }
/*  805:     */   
/*  806:     */   private BigDecimal calcularDiferencia(List<DetalleAsiento> lista)
/*  807:     */   {
/*  808: 937 */     BigDecimal totalDebe = BigDecimal.ZERO;
/*  809: 938 */     BigDecimal totalHaber = BigDecimal.ZERO;
/*  810: 940 */     for (DetalleAsiento detalle : lista) {
/*  811: 941 */       if (!detalle.isEliminado())
/*  812:     */       {
/*  813: 942 */         totalDebe = totalDebe.add(detalle.getDebe());
/*  814: 943 */         totalHaber = totalHaber.add(detalle.getHaber());
/*  815:     */       }
/*  816:     */     }
/*  817: 946 */     BigDecimal diferencia = totalDebe.subtract(totalHaber);
/*  818: 947 */     return diferencia;
/*  819:     */   }
/*  820:     */   
/*  821:     */   private BigDecimal sumaListaDetalleInterfazContable(List<DetalleInterfazContable> lista)
/*  822:     */   {
/*  823: 951 */     BigDecimal total = BigDecimal.ZERO;
/*  824: 952 */     for (DetalleInterfazContable detalleInterfazContable : lista) {
/*  825: 953 */       total = total.add(detalleInterfazContable.getValor());
/*  826:     */     }
/*  827: 955 */     return total;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public void esEditable(MovimientoInventario movimientoInventario)
/*  831:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Inventario
/*  832:     */   {
/*  833: 966 */     if (movimientoInventario.getEstado() == Estado.ANULADO) {
/*  834: 967 */       throw new ExcepcionAS2Inventario("msg_error_editar");
/*  835:     */     }
/*  836: 970 */     this.servicioPeriodo.buscarPorFecha(movimientoInventario.getFecha(), AppUtil.getOrganizacion().getIdOrganizacion(), movimientoInventario
/*  837: 971 */       .getDocumento().getDocumentoBase());
/*  838: 973 */     if (((movimientoInventario.getAsiento() != null) && (movimientoInventario.getAsiento().getEstado() == Estado.REVISADO)) || 
/*  839: 974 */       (movimientoInventario.getInterfazContableProceso() != null)) {
/*  840: 975 */       throw new ExcepcionAS2Inventario("msg_error_editar");
/*  841:     */     }
/*  842:     */   }
/*  843:     */   
/*  844:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  845:     */   public void anular(MovimientoInventario movimientoInventario)
/*  846:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Inventario
/*  847:     */   {
/*  848:     */     try
/*  849:     */     {
/*  850: 991 */       movimientoInventario = cargarDetalle(Integer.valueOf(movimientoInventario.getId()));
/*  851: 992 */       esEditable(movimientoInventario);
/*  852:     */       
/*  853:     */ 
/*  854: 995 */       Estado estadoAnterior = movimientoInventario.getEstado();
/*  855:     */       
/*  856:     */ 
/*  857: 998 */       movimientoInventario.setEstado(Estado.ANULADO);
/*  858: 999 */       this.movimientoInventarioDao.guardar(movimientoInventario);
/*  859:1002 */       if (((estadoAnterior != Estado.ELABORADO) && (!movimientoInventario.getIndicadorSaldoInicial())) || ((estadoAnterior == Estado.ELABORADO) && (
/*  860:     */       
/*  861:1004 */         (!movimientoInventario.getDocumento().isIndicadorContabilizar()) || (movimientoInventario.getIndicadorSaldoInicial()))) || 
/*  862:1005 */         (movimientoInventario.getDocumento().getDocumentoBase().equals(DocumentoBase.CONSUMO_BODEGA)))
/*  863:     */       {
/*  864:1006 */         this.servicioVerificadorInventario.actualizarSaldoProducto(movimientoInventario, true);
/*  865:1008 */         if (ParametrosSistema.isRegistroReversoAnulacionInventario(movimientoInventario.getIdOrganizacion()).booleanValue()) {
/*  866:1010 */           this.servicioInventarioProducto.generarMovimientoInventarioInverso(movimientoInventario, movimientoInventario.getFecha());
/*  867:     */         } else {
/*  868:1013 */           this.servicioInventarioProducto.eliminaInventarioProductoPorIdMovimientoInventario(Integer.valueOf(movimientoInventario.getIdMovimientoInventario()));
/*  869:     */         }
/*  870:     */       }
/*  871:1016 */       if (movimientoInventario.getAsiento() != null)
/*  872:     */       {
/*  873:1017 */         movimientoInventario.getAsiento().setIndicadorAutomatico(false);
/*  874:1018 */         this.servicioAsiento.anular(movimientoInventario.getAsiento());
/*  875:     */       }
/*  876:1023 */       MovimientoInventario mi = this.movimientoInventarioDao.cargarDetalle(movimientoInventario.getId());
/*  877:1024 */       this.servicioVerificadorInventario.abrirCerrarOrdenSalidaMaterial(mi);
/*  878:1027 */       if (movimientoInventario.getDocumento().getDocumentoBase() == DocumentoBase.CONSUMO_BODEGA) {
/*  879:1028 */         this.movimientoInventarioDao.eliminarDetalleOrdenSalidaMaterial(movimientoInventario);
/*  880:     */       }
/*  881:1032 */       this.servicioOrdenTrabajoMantenimiento.actualizarOrdenTrabajo(movimientoInventario);
/*  882:     */     }
/*  883:     */     catch (ExcepcionAS2Financiero e)
/*  884:     */     {
/*  885:1041 */       this.context.setRollbackOnly();
/*  886:1042 */       throw e;
/*  887:     */     }
/*  888:     */     catch (ExcepcionAS2Inventario e)
/*  889:     */     {
/*  890:1044 */       this.context.setRollbackOnly();
/*  891:1045 */       throw e;
/*  892:     */     }
/*  893:     */     catch (Exception e)
/*  894:     */     {
/*  895:1047 */       this.context.setRollbackOnly();
/*  896:1048 */       throw new ExcepcionAS2Inventario(e);
/*  897:     */     }
/*  898:     */   }
/*  899:     */   
/*  900:     */   public void actualizarEstado(Integer idMovimientoInventario, Estado estado)
/*  901:     */   {
/*  902:1060 */     this.movimientoInventarioDao.actualizarEstado(idMovimientoInventario, estado);
/*  903:     */   }
/*  904:     */   
/*  905:     */   public void cargarDetalleAjusteInventario(MovimientoInventario movimientoInventario, String fileName, InputStream imInputStream, int filaInicial)
/*  906:     */     throws ExcepcionAS2
/*  907:     */   {
/*  908:1073 */     int filaActual = filaInicial;
/*  909:1074 */     HSSFCell[] filaErronea = new HSSFCell[0];
/*  910:1075 */     int columnaErronea = -1;
/*  911:     */     try
/*  912:     */     {
/*  913:1081 */       HashMap<String, Producto> hmProducto = new HashMap();
/*  914:1082 */       HashMap<String, Bodega> hmBodega = new HashMap();
/*  915:1083 */       HashMap<String, Unidad> hmUnidad = new HashMap();
/*  916:1084 */       HashMap<String, Lote> hmLote = new HashMap();
/*  917:1086 */       for (Unidad unidad : this.servicioUnidad.obtenerListaCombo("nombre", true, null)) {
/*  918:1087 */         hmUnidad.put(unidad.getCodigo(), unidad);
/*  919:     */       }
/*  920:1090 */       for (??? = this.servicioLote.obtenerListaCombo("codigo", true, null).iterator(); ???.hasNext();)
/*  921:     */       {
/*  922:1090 */         lote = (Lote)???.next();
/*  923:1091 */         hmLote.put(lote.getCodigo() + "~" + lote.getProducto().getId(), lote);
/*  924:     */       }
/*  925:     */       Lote lote;
/*  926:1094 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(fileName, imInputStream, filaInicial, 0);
/*  927:1096 */       for (HSSFCell[] fila : datos)
/*  928:     */       {
/*  929:1098 */         filaErronea = fila;
/*  930:     */         
/*  931:1100 */         columnaErronea = 0;
/*  932:1101 */         String codigoProducto = fila[0] != null ? fila[0].getStringCellValue().trim() : "";
/*  933:1102 */         columnaErronea = 1;
/*  934:1103 */         String codigoBodega = fila[1] != null ? fila[1].getStringCellValue().trim() : "";
/*  935:1104 */         columnaErronea = 2;
/*  936:1105 */         String notaDetalle = fila[2] != null ? fila[2].getStringCellValue().trim() : "";
/*  937:1106 */         columnaErronea = 3;
/*  938:1107 */         String nombreUnidad = fila[3] != null ? fila[3].getStringCellValue().trim() : "";
/*  939:1108 */         columnaErronea = 4;
/*  940:1109 */         BigDecimal cantidad = fila[4] != null ? new BigDecimal(fila[4].getNumericCellValue()) : BigDecimal.ZERO;
/*  941:1110 */         columnaErronea = 5;
/*  942:1111 */         BigDecimal costo = fila[5] != null ? new BigDecimal(fila[5].getNumericCellValue()) : BigDecimal.ZERO;
/*  943:1112 */         columnaErronea = 6;
/*  944:1113 */         String codigoLote = fila[6] != null ? fila[6].getStringCellValue().trim() : null;
/*  945:     */         
/*  946:1115 */         cantidad = FuncionesUtiles.redondearBigDecimal(cantidad, 2);
/*  947:1116 */         costo = FuncionesUtiles.redondearBigDecimal(costo, 6);
/*  948:     */         
/*  949:     */ 
/*  950:     */ 
/*  951:     */ 
/*  952:1121 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/*  953:1122 */         if (producto == null)
/*  954:     */         {
/*  955:1123 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, movimientoInventario.getIdOrganizacion(), null);
/*  956:1124 */           hmProducto.put(codigoProducto, producto);
/*  957:     */         }
/*  958:1129 */         Bodega bodega = (Bodega)hmBodega.get(codigoBodega);
/*  959:1130 */         if (bodega == null)
/*  960:     */         {
/*  961:1131 */           bodega = this.servicioBodega.buscarPorCodigo(codigoBodega);
/*  962:1132 */           hmBodega.put(codigoBodega, bodega);
/*  963:     */         }
/*  964:1138 */         if ((hmProducto.get(codigoProducto) != null) && (hmBodega.get(codigoBodega) != null))
/*  965:     */         {
/*  966:1140 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/*  967:     */           
/*  968:1142 */           DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/*  969:1143 */           detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getId());
/*  970:1144 */           detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  971:1145 */           detalleMovimientoInventario.setCantidad(cantidad);
/*  972:1146 */           detalleMovimientoInventario.setCosto(costo);
/*  973:1147 */           detalleMovimientoInventario.setDescripcion(notaDetalle);
/*  974:1148 */           detalleMovimientoInventario.setBodegaDestino(null);
/*  975:1149 */           detalleMovimientoInventario.setBodegaOrigen(bodega);
/*  976:1150 */           detalleMovimientoInventario.setProducto(producto);
/*  977:1151 */           detalleMovimientoInventario.setUnidadConversion((Unidad)hmUnidad.get(nombreUnidad));
/*  978:1152 */           detalleMovimientoInventario.setMovimientoInventario(movimientoInventario);
/*  979:1153 */           movimientoInventario.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/*  980:1155 */           if (!ParametrosSistema.getIndicadorAprobarAjusteInventario(movimientoInventario.getIdOrganizacion()).booleanValue())
/*  981:     */           {
/*  982:1157 */             InventarioProducto inventarioProducto = new InventarioProducto();
/*  983:1158 */             inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/*  984:1159 */             inventarioProducto.setProducto(detalleMovimientoInventario.getProducto());
/*  985:1160 */             inventarioProducto.setCantidadDocumento(detalleMovimientoInventario.getCantidad());
/*  986:1162 */             if (producto.isIndicadorLote())
/*  987:     */             {
/*  988:1163 */               Lote lote = (Lote)hmLote.get(codigoLote + "~" + producto.getId());
/*  989:1164 */               if (lote != null)
/*  990:     */               {
/*  991:1165 */                 inventarioProducto.setLote(lote);
/*  992:1166 */                 detalleMovimientoInventario.setLote(lote);
/*  993:     */               }
/*  994:     */               else
/*  995:     */               {
/*  996:1168 */                 detalleMovimientoInventario.setInventarioProducto(new InventarioProducto());
/*  997:1169 */                 throw new ExcepcionAS2("msg_info_error_codigo_lote", " " + codigoLote);
/*  998:     */               }
/*  999:     */             }
/* 1000:1172 */             detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 1001:     */           }
/* 1002:     */         }
/* 1003:     */       }
/* 1004:     */     }
/* 1005:     */     catch (IllegalArgumentException e)
/* 1006:     */     {
/* 1007:1181 */       this.context.setRollbackOnly();
/* 1008:1182 */       throw new ExcepcionAS2("msg_error_formato_incorrecto");
/* 1009:     */     }
/* 1010:     */     catch (ExcepcionAS2Financiero e)
/* 1011:     */     {
/* 1012:1184 */       this.context.setRollbackOnly();
/* 1013:1185 */       e.printStackTrace();
/* 1014:1186 */       throw e;
/* 1015:     */     }
/* 1016:     */     catch (ExcepcionAS2Compras e)
/* 1017:     */     {
/* 1018:1188 */       this.context.setRollbackOnly();
/* 1019:1189 */       throw e;
/* 1020:     */     }
/* 1021:     */     catch (ExcepcionAS2 e)
/* 1022:     */     {
/* 1023:1191 */       this.context.setRollbackOnly();
/* 1024:1192 */       throw e;
/* 1025:     */     }
/* 1026:     */     catch (Exception e)
/* 1027:     */     {
/* 1028:1194 */       this.context.setRollbackOnly();
/* 1029:1195 */       throw new ExcepcionAS2(e);
/* 1030:     */     }
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public int contarPorCirterio(Map<String, String> filters)
/* 1034:     */   {
/* 1035:1207 */     return this.movimientoInventarioDao.contarPorCriterio(filters);
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   @Deprecated
/* 1039:     */   public void actualizarAsientoAjusteInventario(int idOrganizacion, Date fechaDesde, Date fechaHasta, TipoOrganizacion tipoOrganizacion)
/* 1040:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1041:     */   {
/* 1042:1216 */     List<MovimientoInventario> lista = this.movimientoInventarioDao.getListaAjusteInvetario(idOrganizacion, fechaDesde, fechaHasta, tipoOrganizacion, null);
/* 1043:1218 */     if (lista.size() != 0) {
/* 1044:1294 */       for (MovimientoInventario movimientoInventario : lista)
/* 1045:     */       {
/* 1046:1295 */         MovimientoInventario mi = cargarDetalle(Integer.valueOf(movimientoInventario.getId()));
/* 1047:1296 */         if ((mi != null) && (mi.getAsiento() != null))
/* 1048:     */         {
/* 1049:1297 */           Asiento asiento = mi.getAsiento();
/* 1050:1298 */           this.asientoDao.detach(asiento);
/* 1051:1299 */           for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/* 1052:1300 */             detalleAsiento.setEliminado(true);
/* 1053:     */           }
/* 1054:     */         }
/* 1055:1306 */         contabilizar(mi);
/* 1056:     */       }
/* 1057:     */     }
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public MovimientoInventario obtenerUltimaTransformacion(Producto producto, Lote lote)
/* 1061:     */   {
/* 1062:1334 */     MovimientoInventario transformacion = this.movimientoInventarioDao.obtenerUltimaTransformacion(producto, lote);
/* 1063:     */     
/* 1064:1336 */     return transformacion;
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public MovimientoInventario guardaTransformacionProducto(MovimientoInventario transformacionMateriales, boolean inverso, BigDecimal cantidadDevuelta, Bodega bodegaDevolucion, FacturaCliente devolucionCliente)
/* 1068:     */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 1069:     */   {
/* 1070:     */     try
/* 1071:     */     {
/* 1072:1346 */       validacionProduccionBom(transformacionMateriales);
/* 1073:     */       
/* 1074:1348 */       Integer versionCosteo = Integer.valueOf(-2);
/* 1075:1349 */       if (inverso)
/* 1076:     */       {
/* 1077:1350 */         Documento documentoProv = transformacionMateriales.getDocumento();
/* 1078:1351 */         transformacionMateriales.setDocumento(transformacionMateriales.getDocumentoDestino());
/* 1079:1352 */         transformacionMateriales.setDocumentoDestino(documentoProv);
/* 1080:     */       }
/* 1081:1355 */       MovimientoInventario transformacionProductoTerminado = new MovimientoInventario();
/* 1082:1356 */       transformacionProductoTerminado.setDocumento(transformacionMateriales.getDocumentoDestino());
/* 1083:1357 */       transformacionProductoTerminado.setFecha(transformacionMateriales.getFecha());
/* 1084:1358 */       transformacionProductoTerminado.setIdOrganizacion(transformacionMateriales.getIdOrganizacion());
/* 1085:1359 */       transformacionProductoTerminado.setSucursal(transformacionMateriales.getSucursal());
/* 1086:1360 */       transformacionProductoTerminado.setDevolucionClienteTransformacion(devolucionCliente);
/* 1087:1361 */       transformacionMateriales.setMovimientoInventarioPadre(transformacionProductoTerminado);
/* 1088:1362 */       transformacionMateriales.setDevolucionClienteTransformacion(devolucionCliente);
/* 1089:1365 */       if (transformacionMateriales.isIndicadorTransferenciaBoom()) {
/* 1090:1366 */         transformacionProductoTerminado.getDocumento().setIndicadorGeneraCosto(false);
/* 1091:     */       }
/* 1092:1369 */       Producto productoTerminado = transformacionMateriales.getProductoTerminadoTransformacion();
/* 1093:1370 */       versionCosteo = Integer.valueOf(productoTerminado.getVersionCosteo());
/* 1094:1371 */       if ((versionCosteo == null) || (versionCosteo.equals(Integer.valueOf(0)))) {
/* 1095:1372 */         versionCosteo = Integer.valueOf(-2);
/* 1096:     */       }
/* 1097:1374 */       BigDecimal cantidad = transformacionMateriales.getCantidadTransformacion();
/* 1098:1375 */       if (inverso) {
/* 1099:1376 */         cantidad = cantidadDevuelta;
/* 1100:     */       }
/* 1101:1378 */       Bodega bodegaDestino = transformacionMateriales.getBodegaDestino();
/* 1102:1379 */       if (inverso) {
/* 1103:1380 */         bodegaDestino = bodegaDevolucion;
/* 1104:     */       }
/* 1105:1382 */       DetalleMovimientoInventario dmiTerminado = new DetalleMovimientoInventario();
/* 1106:1383 */       dmiTerminado.setProducto(productoTerminado);
/* 1107:1384 */       dmiTerminado.setCantidad(cantidad);
/* 1108:1385 */       dmiTerminado.setBodegaOrigen(bodegaDestino);
/* 1109:1386 */       if (!inverso) {
/* 1110:1387 */         dmiTerminado.setBodegaDestino(bodegaDestino);
/* 1111:     */       }
/* 1112:1389 */       dmiTerminado.setUnidadConversion(productoTerminado.getUnidadAlmacenamiento());
/* 1113:1390 */       dmiTerminado.setMovimientoInventario(transformacionProductoTerminado);
/* 1114:1391 */       dmiTerminado.setIdOrganizacion(transformacionProductoTerminado.getIdOrganizacion());
/* 1115:1392 */       dmiTerminado.setIdSucursal(transformacionProductoTerminado.getSucursal().getId());
/* 1116:     */       
/* 1117:1394 */       InventarioProducto inventarioProductoTerminado = new InventarioProducto();
/* 1118:     */       
/* 1119:     */ 
/* 1120:1397 */       inventarioProductoTerminado.setDetalleMovimientoInventario(dmiTerminado);
/* 1121:     */       
/* 1122:1399 */       inventarioProductoTerminado.setCantidad(dmiTerminado.getCantidad());
/* 1123:1400 */       inventarioProductoTerminado.setFecha(transformacionProductoTerminado.getFecha());
/* 1124:1401 */       inventarioProductoTerminado.setDocumento(transformacionProductoTerminado.getDocumento());
/* 1125:1402 */       inventarioProductoTerminado.setIdOrganizacion(transformacionProductoTerminado.getIdOrganizacion());
/* 1126:1403 */       inventarioProductoTerminado.setOperacion(transformacionProductoTerminado.getDocumento().getOperacion());
/* 1127:1404 */       inventarioProductoTerminado.setIdSucursal(dmiTerminado.getIdSucursal());
/* 1128:1405 */       inventarioProductoTerminado.setBodega(bodegaDestino);
/* 1129:1406 */       inventarioProductoTerminado.setProducto(dmiTerminado.getProducto());
/* 1130:1407 */       inventarioProductoTerminado.setNumeroDocumento(transformacionProductoTerminado.getNumero());
/* 1131:1408 */       inventarioProductoTerminado.setCantidadDocumento(dmiTerminado.getCantidad());
/* 1132:1409 */       inventarioProductoTerminado.setUnidadDocumento(dmiTerminado.getProducto().getUnidad().getNombre());
/* 1133:1412 */       if (transformacionMateriales.isIndicadorTransferenciaBoom()) {
/* 1134:1413 */         inventarioProductoTerminado.setIndicadorIngresoDespuesEgreso(true);
/* 1135:     */       }
/* 1136:1416 */       if (productoTerminado.isIndicadorLote())
/* 1137:     */       {
/* 1138:1417 */         inventarioProductoTerminado.setLote(transformacionMateriales.getLoteTransformacion());
/* 1139:1418 */         if ((!inverso) && 
/* 1140:1419 */           (inventarioProductoTerminado.getLote().getIdLote() == 0))
/* 1141:     */         {
/* 1142:     */           Lote l;
/* 1143:     */           try
/* 1144:     */           {
/* 1145:1422 */             l = this.servicioLote.buscarPorCodigo(inventarioProductoTerminado.getLote().getCodigo(), inventarioProductoTerminado
/* 1146:1423 */               .getProducto());
/* 1147:     */           }
/* 1148:     */           catch (ExcepcionAS2 e)
/* 1149:     */           {
/* 1150:     */             Lote l;
/* 1151:1425 */             l = null;
/* 1152:     */           }
/* 1153:1427 */           if (l == null)
/* 1154:     */           {
/* 1155:1428 */             this.servicioLote.guardar(inventarioProductoTerminado.getLote());
/* 1156:     */           }
/* 1157:     */           else
/* 1158:     */           {
/* 1159:1430 */             inventarioProductoTerminado.setLote(l);
/* 1160:1431 */             transformacionMateriales.setLoteTransformacion(l);
/* 1161:     */           }
/* 1162:     */         }
/* 1163:     */       }
/* 1164:1437 */       dmiTerminado.setInventarioProducto(inventarioProductoTerminado);
/* 1165:1438 */       transformacionProductoTerminado.getDetalleMovimientosInventario().add(dmiTerminado);
/* 1166:     */       
/* 1167:1440 */       BigDecimal proporcionInverso = BigDecimal.ONE;
/* 1168:     */       BigDecimal cantidadOriginal;
/* 1169:1442 */       if (inverso)
/* 1170:     */       {
/* 1171:1443 */         cantidadOriginal = transformacionMateriales.getCantidadTransformacion();
/* 1172:1444 */         proporcionInverso = cantidadDevuelta.divide(cantidadOriginal, 6, RoundingMode.HALF_UP);
/* 1173:     */       }
/* 1174:1447 */       for (DetalleMovimientoInventario detalle : transformacionMateriales.getDetalleMovimientosInventario()) {
/* 1175:1448 */         if (!detalle.isEliminado())
/* 1176:     */         {
/* 1177:1449 */           if (!inverso) {
/* 1178:1450 */             detalle.setBodegaDestino(null);
/* 1179:     */           } else {
/* 1180:1452 */             detalle.setBodegaDestino(detalle.getBodegaOrigen());
/* 1181:     */           }
/* 1182:1455 */           if (inverso) {
/* 1183:1456 */             detalle.setCantidad(detalle.getCantidad().multiply(proporcionInverso).setScale(2, RoundingMode.HALF_UP));
/* 1184:     */           }
/* 1185:1459 */           InventarioProducto inventarioProducto = detalle.getInventarioProducto();
/* 1186:1460 */           if (inventarioProducto == null)
/* 1187:     */           {
/* 1188:1461 */             inventarioProducto = new InventarioProducto();
/* 1189:1462 */             detalle.setInventarioProducto(inventarioProducto);
/* 1190:     */           }
/* 1191:1464 */           if (inventarioProducto.getLote() == null) {
/* 1192:1465 */             inventarioProducto.setLote(detalle.getLote());
/* 1193:     */           }
/* 1194:1467 */           inventarioProducto.setIndicadorTransformacion(Boolean.valueOf(true));
/* 1195:1468 */           inventarioProducto.setInventarioProductoTransformacion(inventarioProductoTerminado);
/* 1196:1469 */           inventarioProducto.setDetalleMovimientoInventario(detalle);
/* 1197:1470 */           inventarioProducto.setCantidad(detalle.getCantidad());
/* 1198:1471 */           inventarioProducto.setFecha(transformacionMateriales.getFecha());
/* 1199:1472 */           inventarioProducto.setDocumento(transformacionMateriales.getDocumento());
/* 1200:1473 */           inventarioProducto.setIdOrganizacion(transformacionMateriales.getIdOrganizacion());
/* 1201:1474 */           inventarioProducto.setOperacion(transformacionMateriales.getDocumento().getOperacion());
/* 1202:1475 */           inventarioProducto.setIdSucursal(transformacionMateriales.getSucursal().getId());
/* 1203:1476 */           inventarioProducto.setBodega(detalle.getBodegaOrigen());
/* 1204:1477 */           inventarioProducto.setProducto(detalle.getProducto());
/* 1205:1478 */           inventarioProducto.setNumeroDocumento(transformacionMateriales.getNumero());
/* 1206:1479 */           inventarioProducto.setCantidadDocumento(detalle.getCantidad());
/* 1207:1480 */           inventarioProducto.setUnidadDocumento(detalle.getProducto().getUnidad().getNombre());
/* 1208:     */         }
/* 1209:     */       }
/* 1210:1483 */       if ((transformacionProductoTerminado.getNumero() == null) || (inverso)) {
/* 1211:1484 */         transformacionProductoTerminado.setNumero("");
/* 1212:     */       }
/* 1213:1486 */       transformacionProductoTerminado.setIndicadorGenerarCostos(false);
/* 1214:1487 */       transformacionProductoTerminado.setEstado(Estado.PROCESADO);
/* 1215:1488 */       guardar(transformacionProductoTerminado);
/* 1216:     */       
/* 1217:1490 */       transformacionMateriales.setEstado(Estado.PROCESADO);
/* 1218:1491 */       transformacionMateriales.setNumero(transformacionProductoTerminado.getNumero());
/* 1219:     */       
/* 1220:1493 */       guardar(transformacionMateriales);
/* 1221:1494 */       this.movimientoInventarioDao.flush();
/* 1222:     */       
/* 1223:     */ 
/* 1224:     */ 
/* 1225:1498 */       this.servicioCosteo.generarCostos(transformacionProductoTerminado, 
/* 1226:1499 */         ParametrosSistema.isCosteoPorBodega(transformacionProductoTerminado.getIdOrganizacion()).booleanValue(), Integer.valueOf(versionCosteo.intValue() + 1));
/* 1227:1500 */       if (inverso) {
/* 1228:1501 */         this.servicioCosteo.generarCostos(transformacionMateriales, 
/* 1229:1502 */           ParametrosSistema.isCosteoPorBodega(transformacionMateriales.getIdOrganizacion()).booleanValue(), Integer.valueOf(versionCosteo.intValue() + 1));
/* 1230:     */       }
/* 1231:1504 */       this.movimientoInventarioDao.flush();
/* 1232:1505 */       contabilizar(transformacionMateriales);
/* 1233:1512 */       if ((transformacionMateriales.getOrdenFabricacion() != null) && (!inverso) && (cantidadDevuelta == null) && (bodegaDevolucion == null) && (devolucionCliente == null)) {
/* 1234:1514 */         this.ordenFabricacionDao.actualizarCantidadControlCalidad(transformacionMateriales.getOrdenFabricacion(), transformacionMateriales
/* 1235:1515 */           .getCantidadTransformacion(), "cantidadFabricada");
/* 1236:     */       }
/* 1237:1518 */       return transformacionMateriales;
/* 1238:     */     }
/* 1239:     */     catch (ExcepcionAS2Financiero e)
/* 1240:     */     {
/* 1241:1521 */       this.context.setRollbackOnly();
/* 1242:1522 */       e.printStackTrace();
/* 1243:1523 */       throw e;
/* 1244:     */     }
/* 1245:     */     catch (ExcepcionAS2 e)
/* 1246:     */     {
/* 1247:1525 */       this.context.setRollbackOnly();
/* 1248:1526 */       e.printStackTrace();
/* 1249:1527 */       throw e;
/* 1250:     */     }
/* 1251:     */     catch (AS2Exception e)
/* 1252:     */     {
/* 1253:1529 */       this.context.setRollbackOnly();
/* 1254:1530 */       e.printStackTrace();
/* 1255:1531 */       throw e;
/* 1256:     */     }
/* 1257:     */     catch (Exception e)
/* 1258:     */     {
/* 1259:1533 */       this.context.setRollbackOnly();
/* 1260:1534 */       LOG.error(e);
/* 1261:1535 */       e.printStackTrace();
/* 1262:1536 */       throw new ExcepcionAS2(e);
/* 1263:     */     }
/* 1264:     */   }
/* 1265:     */   
/* 1266:     */   public void validacionProduccionBom(MovimientoInventario transformacionMateriales)
/* 1267:     */     throws AS2Exception
/* 1268:     */   {
/* 1269:1542 */     if (transformacionMateriales.getProductoTerminadoTransformacion() == null) {
/* 1270:1543 */       throw new AS2Exception("com.asinfo.as2.inventario.procesos.servicio.impl.ServicioMovimientoInventarioImpl.ERROR_SELECCIONAR_UN_PRODUCTO", new String[] { "" });
/* 1271:     */     }
/* 1272:1546 */     if ((transformacionMateriales.getCantidadTransformacion() == null) || 
/* 1273:1547 */       (transformacionMateriales.getCantidadTransformacion().compareTo(BigDecimal.ZERO) == 0)) {
/* 1274:1548 */       throw new AS2Exception("com.asinfo.as2.inventario.procesos.servicio.impl.ServicioMovimientoInventarioImpl.ERROR_CANTIDAD_TRANSFORMACION_MAYOR_CERO", new String[] { "" });
/* 1275:     */     }
/* 1276:     */   }
/* 1277:     */   
/* 1278:     */   public MovimientoInventario guardaTransformacionRapidaProducto(Map<String, Producto> mapaProductos, Date fecha, OrdenDespachoCliente ordenDespachoCliente, boolean inverso, int idSucursal, FacturaCliente devolucionCliente)
/* 1279:     */     throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2
/* 1280:     */   {
/* 1281:1559 */     Integer versionCosteo = Integer.valueOf(0);
/* 1282:     */     
/* 1283:     */ 
/* 1284:1562 */     Map<String, String> filtros = new HashMap();
/* 1285:1563 */     filtros.put("idOrganizacion", String.valueOf(ordenDespachoCliente.getIdOrganizacion()));
/* 1286:1564 */     filtros.put("operacion", inverso ? "=1" : "=-1");
/* 1287:1565 */     filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 1288:     */     
/* 1289:1567 */     List<Documento> listaDocumentoEgreso = this.servicioDocumento.obtenerListaCombo("predeterminado", false, filtros);
/* 1290:1568 */     Documento documentoEgreso = null;
/* 1291:1569 */     if (listaDocumentoEgreso.size() > 0)
/* 1292:     */     {
/* 1293:1570 */       documentoEgreso = (Documento)listaDocumentoEgreso.get(0);
/* 1294:     */     }
/* 1295:     */     else
/* 1296:     */     {
/* 1297:1572 */       if (inverso) {
/* 1298:1573 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_INGRESO", new String[] { "" });
/* 1299:     */       }
/* 1300:1575 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_EGRESO", new String[] { "" });
/* 1301:     */     }
/* 1302:1580 */     filtros = new HashMap();
/* 1303:1581 */     filtros.put("idOrganizacion", String.valueOf(ordenDespachoCliente.getIdOrganizacion()));
/* 1304:1582 */     filtros.put("operacion", inverso ? "=-1" : "=1");
/* 1305:1583 */     filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 1306:     */     
/* 1307:1585 */     List<Documento> listaDocumentoIngreso = this.servicioDocumento.obtenerListaCombo("predeterminado", false, filtros);
/* 1308:1586 */     Documento documentoIngreso = null;
/* 1309:1587 */     if (listaDocumentoIngreso.size() > 0)
/* 1310:     */     {
/* 1311:1588 */       documentoIngreso = (Documento)listaDocumentoIngreso.get(0);
/* 1312:     */     }
/* 1313:     */     else
/* 1314:     */     {
/* 1315:1590 */       if (inverso) {
/* 1316:1591 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_EGRESO", new String[] { "" });
/* 1317:     */       }
/* 1318:1593 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_INGRESO", new String[] { "" });
/* 1319:     */     }
/* 1320:1598 */     MovimientoInventario movimientoIngresoProducto = new MovimientoInventario();
/* 1321:1599 */     movimientoIngresoProducto.setIdOrganizacion(ordenDespachoCliente.getIdOrganizacion());
/* 1322:1600 */     movimientoIngresoProducto.setSucursal(ordenDespachoCliente.getSucursal());
/* 1323:1601 */     movimientoIngresoProducto.setDocumento(documentoIngreso);
/* 1324:1602 */     movimientoIngresoProducto.setFecha(fecha);
/* 1325:1603 */     movimientoIngresoProducto.setNumero("");
/* 1326:1604 */     movimientoIngresoProducto.setOrdenDespachoCliente(ordenDespachoCliente);
/* 1327:1605 */     movimientoIngresoProducto.setDevolucionClienteTransformacion(devolucionCliente);
/* 1328:     */     
/* 1329:     */ 
/* 1330:1608 */     MovimientoInventario movimientoEgresoMateriales = new MovimientoInventario();
/* 1331:1609 */     movimientoEgresoMateriales.setIdOrganizacion(ordenDespachoCliente.getIdOrganizacion());
/* 1332:1610 */     movimientoEgresoMateriales.setSucursal(ordenDespachoCliente.getSucursal());
/* 1333:1611 */     movimientoEgresoMateriales.setDocumento(documentoEgreso);
/* 1334:1612 */     movimientoEgresoMateriales.setFecha(fecha);
/* 1335:1613 */     movimientoEgresoMateriales.setMovimientoInventarioPadre(movimientoIngresoProducto);
/* 1336:1614 */     movimientoEgresoMateriales.setOrdenDespachoCliente(ordenDespachoCliente);
/* 1337:1615 */     movimientoEgresoMateriales.setDevolucionClienteTransformacion(devolucionCliente);
/* 1338:     */     
/* 1339:1617 */     Iterator it = mapaProductos.keySet().iterator();
/* 1340:     */     InventarioProducto inventarioProducto;
/* 1341:     */     BigDecimal pesoMaterialPrincipal;
/* 1342:     */     BigDecimal totalCantidadProducirPrincipales;
/* 1343:1618 */     while (it.hasNext())
/* 1344:     */     {
/* 1345:1619 */       String key = (String)it.next();
/* 1346:1620 */       Producto producto = (Producto)mapaProductos.get(key);
/* 1347:1621 */       if (producto.getVersionCosteo() > versionCosteo.intValue()) {
/* 1348:1622 */         versionCosteo = Integer.valueOf(producto.getVersionCosteo());
/* 1349:     */       }
/* 1350:1624 */       if (producto.getCantidadProduccion().compareTo(BigDecimal.ZERO) <= 0) {
/* 1351:1625 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_CANTIDAD_BOM", new String[] { producto.getCodigo(), producto.getNombre() });
/* 1352:     */       }
/* 1353:1628 */       Bodega bodegaDevolucion = producto.getBodegaDevolucion();
/* 1354:1629 */       if (producto.getCantidadProducir().compareTo(BigDecimal.ZERO) > 0)
/* 1355:     */       {
/* 1356:1631 */         DetalleMovimientoInventario dmiProducto = new DetalleMovimientoInventario();
/* 1357:1632 */         dmiProducto.setProducto(producto);
/* 1358:1633 */         dmiProducto.setCantidad(producto.getCantidadProducir());
/* 1359:1634 */         Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(producto, Integer.valueOf(idSucursal));
/* 1360:1635 */         if (!inverso) {
/* 1361:1636 */           dmiProducto.setBodegaDestino(bodegaTrabajo);
/* 1362:     */         }
/* 1363:1638 */         dmiProducto.setBodegaOrigen(!inverso ? bodegaTrabajo : bodegaDevolucion);
/* 1364:1639 */         dmiProducto.setUnidadConversion(dmiProducto.getProducto().getUnidadAlmacenamiento());
/* 1365:1640 */         dmiProducto.setMovimientoInventario(movimientoIngresoProducto);
/* 1366:1641 */         dmiProducto.setIdOrganizacion(movimientoIngresoProducto.getIdOrganizacion());
/* 1367:1642 */         dmiProducto.setIdSucursal(movimientoIngresoProducto.getSucursal().getId());
/* 1368:     */         
/* 1369:1644 */         inventarioProducto = new InventarioProducto();
/* 1370:1645 */         inventarioProducto.setDetalleMovimientoInventario(dmiProducto);
/* 1371:1646 */         inventarioProducto.setIndicadorTransformacion(Boolean.valueOf(true));
/* 1372:1647 */         inventarioProducto.setCantidad(dmiProducto.getCantidad());
/* 1373:1648 */         inventarioProducto.setFecha(movimientoIngresoProducto.getFecha());
/* 1374:1649 */         inventarioProducto.setDocumento(movimientoIngresoProducto.getDocumento());
/* 1375:1650 */         inventarioProducto.setIdOrganizacion(movimientoIngresoProducto.getIdOrganizacion());
/* 1376:1651 */         inventarioProducto.setOperacion(movimientoIngresoProducto.getDocumento().getOperacion());
/* 1377:1652 */         inventarioProducto.setIdSucursal(movimientoIngresoProducto.getSucursal().getId());
/* 1378:1653 */         inventarioProducto.setBodega(!inverso ? dmiProducto.getBodegaDestino() : dmiProducto.getBodegaOrigen());
/* 1379:1654 */         inventarioProducto.setProducto(dmiProducto.getProducto());
/* 1380:1655 */         inventarioProducto.setNumeroDocumento(movimientoIngresoProducto.getNumero());
/* 1381:1656 */         inventarioProducto.setCantidadDocumento(dmiProducto.getCantidad());
/* 1382:1657 */         inventarioProducto.setUnidadDocumento(dmiProducto.getProducto().getUnidad().getNombre());
/* 1383:     */         
/* 1384:1659 */         dmiProducto.setInventarioProducto(inventarioProducto);
/* 1385:1660 */         movimientoIngresoProducto.getDetalleMovimientosInventario().add(dmiProducto);
/* 1386:     */         
/* 1387:     */ 
/* 1388:1663 */         NodoArbol<Producto> nodoArbol = this.servicioProducto.obtenerArbolComponentes(producto);
/* 1389:1664 */         pesoMaterialPrincipal = producto.getPesoMaterialPrincipal();
/* 1390:1669 */         if ((pesoMaterialPrincipal != null) && (pesoMaterialPrincipal.setScale(2, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) == 0)) {
/* 1391:1670 */           pesoMaterialPrincipal = null;
/* 1392:     */         }
/* 1393:1672 */         totalCantidadProducirPrincipales = BigDecimal.ZERO;
/* 1394:1673 */         for (NodoArbol<Producto> nodo : nodoArbol.getHijos())
/* 1395:     */         {
/* 1396:1674 */           ProductoMaterial productoMaterial = (ProductoMaterial)nodo.getPropiedades().get("ProductoMaterial");
/* 1397:1675 */           Producto material = (Producto)nodo.getValor();
/* 1398:1676 */           if (productoMaterial.getIndicadorPrincipal().booleanValue()) {
/* 1399:1677 */             totalCantidadProducirPrincipales = totalCantidadProducirPrincipales.add(material.getCantidadProducir());
/* 1400:     */           }
/* 1401:     */         }
/* 1402:1682 */         for (NodoArbol<Producto> nodo : nodoArbol.getHijos())
/* 1403:     */         {
/* 1404:1683 */           ProductoMaterial productoMaterial = (ProductoMaterial)nodo.getPropiedades().get("ProductoMaterial");
/* 1405:1684 */           Producto material = (Producto)nodo.getValor();
/* 1406:     */           
/* 1407:     */ 
/* 1408:1687 */           BigDecimal proporcionMaterialPrincipal = BigDecimal.ZERO;
/* 1409:1688 */           BigDecimal cantidadProducirReceta = material.getCantidadProducir();
/* 1410:1689 */           if ((productoMaterial.getIndicadorPrincipal().booleanValue()) && (totalCantidadProducirPrincipales.compareTo(BigDecimal.ZERO) != 0)) {
/* 1411:1690 */             proporcionMaterialPrincipal = cantidadProducirReceta.divide(totalCantidadProducirPrincipales, 6, RoundingMode.HALF_UP);
/* 1412:     */           }
/* 1413:1695 */           BigDecimal cantidad = (pesoMaterialPrincipal != null) && (productoMaterial.getIndicadorPrincipal().booleanValue()) ? pesoMaterialPrincipal.multiply(proporcionMaterialPrincipal).setScale(material.getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP) : material.getCantidadProducir();
/* 1414:     */           
/* 1415:1697 */           DetalleMovimientoInventario dmiMateriales = new DetalleMovimientoInventario();
/* 1416:1698 */           dmiMateriales.setProducto(material);
/* 1417:1699 */           dmiMateriales.setCantidad(cantidad);
/* 1418:1700 */           Bodega bodegaTrabajoMaterial = this.servicioProducto.obtenerBodegaTrabajoProducto(material, Integer.valueOf(idSucursal));
/* 1419:     */           
/* 1420:1702 */           dmiMateriales.setBodegaOrigen(bodegaTrabajoMaterial);
/* 1421:1703 */           if (inverso) {
/* 1422:1704 */             dmiMateriales.setBodegaDestino(bodegaTrabajoMaterial);
/* 1423:     */           }
/* 1424:1706 */           dmiMateriales.setUnidadConversion(dmiMateriales.getProducto().getUnidadAlmacenamiento());
/* 1425:1707 */           dmiMateriales.setMovimientoInventario(movimientoEgresoMateriales);
/* 1426:1708 */           dmiMateriales.setIdOrganizacion(movimientoEgresoMateriales.getIdOrganizacion());
/* 1427:1709 */           dmiMateriales.setIdSucursal(movimientoEgresoMateriales.getSucursal().getId());
/* 1428:     */           
/* 1429:1711 */           InventarioProducto inventarioProductoMateriales = new InventarioProducto();
/* 1430:1712 */           inventarioProductoMateriales.setInventarioProductoTransformacion(inventarioProducto);
/* 1431:1713 */           inventarioProductoMateriales.setDetalleMovimientoInventario(dmiMateriales);
/* 1432:1714 */           inventarioProductoMateriales.setIndicadorTransformacion(Boolean.valueOf(true));
/* 1433:1715 */           inventarioProductoMateriales.setCantidad(dmiMateriales.getCantidad());
/* 1434:1716 */           inventarioProductoMateriales.setFecha(movimientoEgresoMateriales.getFecha());
/* 1435:1717 */           inventarioProductoMateriales.setDocumento(movimientoEgresoMateriales.getDocumento());
/* 1436:1718 */           inventarioProductoMateriales.setIdOrganizacion(movimientoEgresoMateriales.getIdOrganizacion());
/* 1437:1719 */           inventarioProductoMateriales.setOperacion(movimientoEgresoMateriales.getDocumento().getOperacion());
/* 1438:1720 */           inventarioProductoMateriales.setIdSucursal(inventarioProducto.getIdSucursal());
/* 1439:1721 */           inventarioProductoMateriales.setBodega(dmiMateriales.getBodegaOrigen());
/* 1440:1722 */           inventarioProductoMateriales.setProducto(dmiMateriales.getProducto());
/* 1441:1723 */           inventarioProductoMateriales.setNumeroDocumento(movimientoEgresoMateriales.getNumero());
/* 1442:1724 */           inventarioProductoMateriales.setCantidadDocumento(dmiMateriales.getCantidad());
/* 1443:1725 */           inventarioProductoMateriales.setUnidadDocumento(dmiMateriales.getProducto().getUnidad().getNombre());
/* 1444:     */           
/* 1445:1727 */           dmiMateriales.setInventarioProducto(inventarioProductoMateriales);
/* 1446:1728 */           movimientoEgresoMateriales.getDetalleMovimientosInventario().add(dmiMateriales);
/* 1447:     */         }
/* 1448:     */       }
/* 1449:     */     }
/* 1450:1732 */     if (movimientoIngresoProducto.getDetalleMovimientosInventario().size() > 0)
/* 1451:     */     {
/* 1452:1733 */       movimientoIngresoProducto.setIndicadorGenerarCostos(false);
/* 1453:1734 */       movimientoIngresoProducto.setEstado(Estado.PROCESADO);
/* 1454:1735 */       guardar(movimientoIngresoProducto);
/* 1455:     */       
/* 1456:1737 */       movimientoEgresoMateriales.setEstado(Estado.PROCESADO);
/* 1457:1738 */       movimientoEgresoMateriales.setNumero(movimientoIngresoProducto.getNumero());
/* 1458:     */       
/* 1459:1740 */       guardar(movimientoEgresoMateriales);
/* 1460:1741 */       this.movimientoInventarioDao.flush();
/* 1461:     */       
/* 1462:     */ 
/* 1463:     */ 
/* 1464:     */ 
/* 1465:1746 */       this.servicioCosteo.generarCostos(movimientoIngresoProducto, 
/* 1466:1747 */         ParametrosSistema.isCosteoPorBodega(movimientoIngresoProducto.getIdOrganizacion()).booleanValue(), Integer.valueOf(versionCosteo.intValue() + 1));
/* 1467:1748 */       if (inverso) {
/* 1468:1749 */         this.servicioCosteo.generarCostos(movimientoEgresoMateriales, 
/* 1469:1750 */           ParametrosSistema.isCosteoPorBodega(movimientoIngresoProducto.getIdOrganizacion()).booleanValue(), Integer.valueOf(versionCosteo.intValue() + 1));
/* 1470:     */       }
/* 1471:1753 */       this.movimientoInventarioDao.flush();
/* 1472:     */     }
/* 1473:1761 */     return movimientoEgresoMateriales;
/* 1474:     */   }
/* 1475:     */   
/* 1476:     */   public MovimientoInventario guardaTransformacionSubordenes(OrdenFabricacion ordenFabricacion, BigDecimal cantidad, Date fecha, Producto productoNuevo, Lote lote)
/* 1477:     */     throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2
/* 1478:     */   {
/* 1479:1769 */     ordenFabricacion = this.servicioOrdenFabricacion.cargarDetalle(ordenFabricacion.getId());
/* 1480:1770 */     Integer versionCosteo = Integer.valueOf(0);
/* 1481:     */     
/* 1482:     */ 
/* 1483:1773 */     Map<String, String> filtros = new HashMap();
/* 1484:1774 */     filtros.put("idOrganizacion", String.valueOf(ordenFabricacion.getIdOrganizacion()));
/* 1485:1775 */     filtros.put("operacion", "=-1");
/* 1486:1776 */     filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 1487:     */     
/* 1488:1778 */     List<Documento> listaDocumentoEgreso = this.servicioDocumento.obtenerListaCombo("predeterminado", false, filtros);
/* 1489:1779 */     Documento documentoEgreso = null;
/* 1490:1780 */     if (listaDocumentoEgreso.size() > 0) {
/* 1491:1781 */       documentoEgreso = (Documento)listaDocumentoEgreso.get(0);
/* 1492:     */     } else {
/* 1493:1783 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_EGRESO", new String[] { "" });
/* 1494:     */     }
/* 1495:1787 */     filtros = new HashMap();
/* 1496:1788 */     filtros.put("idOrganizacion", String.valueOf(ordenFabricacion.getIdOrganizacion()));
/* 1497:1789 */     filtros.put("operacion", "=1");
/* 1498:1790 */     filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 1499:     */     
/* 1500:1792 */     List<Documento> listaDocumentoIngreso = this.servicioDocumento.obtenerListaCombo("predeterminado", false, filtros);
/* 1501:1793 */     Documento documentoIngreso = null;
/* 1502:1794 */     if (listaDocumentoIngreso.size() > 0) {
/* 1503:1795 */       documentoIngreso = (Documento)listaDocumentoIngreso.get(0);
/* 1504:     */     } else {
/* 1505:1797 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_INGRESO", new String[] { "" });
/* 1506:     */     }
/* 1507:1801 */     MovimientoInventario movimientoIngresoProducto = new MovimientoInventario();
/* 1508:1802 */     movimientoIngresoProducto.setIdOrganizacion(ordenFabricacion.getIdOrganizacion());
/* 1509:1803 */     movimientoIngresoProducto.setSucursal(ordenFabricacion.getSucursal());
/* 1510:1804 */     movimientoIngresoProducto.setDocumento(documentoIngreso);
/* 1511:1805 */     movimientoIngresoProducto.setFecha(fecha);
/* 1512:1806 */     movimientoIngresoProducto.setNumero("");
/* 1513:1807 */     movimientoIngresoProducto.setOrdenFabricacion(ordenFabricacion);
/* 1514:     */     
/* 1515:     */ 
/* 1516:1810 */     MovimientoInventario movimientoEgresoMateriales = new MovimientoInventario();
/* 1517:1811 */     movimientoEgresoMateriales.setIdOrganizacion(ordenFabricacion.getIdOrganizacion());
/* 1518:1812 */     movimientoEgresoMateriales.setSucursal(ordenFabricacion.getSucursal());
/* 1519:1813 */     movimientoEgresoMateriales.setDocumento(documentoEgreso);
/* 1520:1814 */     movimientoEgresoMateriales.setFecha(fecha);
/* 1521:1815 */     movimientoEgresoMateriales.setMovimientoInventarioPadre(movimientoIngresoProducto);
/* 1522:1816 */     movimientoEgresoMateriales.setOrdenFabricacion(ordenFabricacion);
/* 1523:     */     
/* 1524:1818 */     Producto productoIngreso = productoNuevo != null ? productoNuevo : ordenFabricacion.getProducto();
/* 1525:1819 */     versionCosteo = Integer.valueOf(productoIngreso.getVersionCosteo());
/* 1526:1820 */     if (ordenFabricacion.getProducto().getCantidadProduccion().compareTo(BigDecimal.ZERO) <= 0) {
/* 1527:1822 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_CANTIDAD_BOM", new String[] { ordenFabricacion.getProducto().getCodigo(), ordenFabricacion.getProducto().getNombre() });
/* 1528:     */     }
/* 1529:1826 */     Bodega bodegaIngreso = ordenFabricacion.getBodega() != null ? ordenFabricacion.getBodega() : this.servicioProducto.obtenerBodegaTrabajoProducto(productoIngreso, Integer.valueOf(ordenFabricacion.getIdOrganizacion()));
/* 1530:1828 */     if (bodegaIngreso == null) {
/* 1531:1829 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PARAMETRIZAR_BODEGA_TRABAJO", new String[] { productoIngreso.getNombre() });
/* 1532:     */     }
/* 1533:     */     InventarioProducto inventarioProducto;
/* 1534:     */     DetalleMovimientoInventario localDetalleMovimientoInventario1;
/* 1535:1831 */     if (cantidad.compareTo(BigDecimal.ZERO) > 0)
/* 1536:     */     {
/* 1537:1833 */       DetalleMovimientoInventario dmiProducto = new DetalleMovimientoInventario();
/* 1538:1834 */       dmiProducto.setProducto(productoIngreso);
/* 1539:1835 */       dmiProducto.setCantidad(cantidad);
/* 1540:1836 */       dmiProducto.setBodegaDestino(bodegaIngreso);
/* 1541:1837 */       dmiProducto.setBodegaOrigen(bodegaIngreso);
/* 1542:1838 */       dmiProducto.setUnidadConversion(dmiProducto.getProducto().getUnidadAlmacenamiento());
/* 1543:1839 */       dmiProducto.setMovimientoInventario(movimientoIngresoProducto);
/* 1544:1840 */       dmiProducto.setIdOrganizacion(movimientoIngresoProducto.getIdOrganizacion());
/* 1545:1841 */       dmiProducto.setIdSucursal(movimientoIngresoProducto.getSucursal().getId());
/* 1546:     */       
/* 1547:1843 */       inventarioProducto = new InventarioProducto();
/* 1548:1844 */       inventarioProducto.setDetalleMovimientoInventario(dmiProducto);
/* 1549:1845 */       inventarioProducto.setIndicadorTransformacion(Boolean.valueOf(true));
/* 1550:1846 */       inventarioProducto.setCantidad(dmiProducto.getCantidad());
/* 1551:1847 */       inventarioProducto.setFecha(movimientoIngresoProducto.getFecha());
/* 1552:1848 */       inventarioProducto.setDocumento(movimientoIngresoProducto.getDocumento());
/* 1553:1849 */       inventarioProducto.setIdOrganizacion(movimientoIngresoProducto.getIdOrganizacion());
/* 1554:1850 */       inventarioProducto.setOperacion(movimientoIngresoProducto.getDocumento().getOperacion());
/* 1555:1851 */       inventarioProducto.setIdSucursal(movimientoIngresoProducto.getSucursal().getId());
/* 1556:1852 */       inventarioProducto.setBodega(dmiProducto.getBodegaDestino());
/* 1557:1853 */       inventarioProducto.setProducto(dmiProducto.getProducto());
/* 1558:1854 */       inventarioProducto.setNumeroDocumento(movimientoIngresoProducto.getNumero());
/* 1559:1855 */       inventarioProducto.setCantidadDocumento(dmiProducto.getCantidad());
/* 1560:1856 */       inventarioProducto.setUnidadDocumento(dmiProducto.getProducto().getUnidad().getNombre());
/* 1561:1857 */       inventarioProducto.setLote(lote);
/* 1562:     */       
/* 1563:1859 */       dmiProducto.setInventarioProducto(inventarioProducto);
/* 1564:1860 */       movimientoIngresoProducto.getDetalleMovimientosInventario().add(dmiProducto);
/* 1565:     */       
/* 1566:     */ 
/* 1567:     */ 
/* 1568:1864 */       List<DetalleOrdenFabricacionMaterial> listaMateriales = this.servicioProducto.obtenerMaterialesDetalleOrdenFabricacionMaterial(ordenFabricacion, Boolean.valueOf(true));
/* 1569:     */       
/* 1570:1866 */       List<OrdenFabricacion> listaSubordenes = this.servicioOrdenFabricacion.obtenerListaSubordenFabricacion(ordenFabricacion.getId(), true);
/* 1571:1868 */       for (DetalleOrdenFabricacionMaterial dofm : listaMateriales) {
/* 1572:1869 */         if (dofm.getDetalleOrdenFabricacionMaterialPadre() != null)
/* 1573:     */         {
/* 1574:1870 */           BigDecimal proporcion = BigDecimal.ZERO;
/* 1575:1871 */           if (ordenFabricacion.getCantidad().compareTo(BigDecimal.ZERO) != 0)
/* 1576:     */           {
/* 1577:1872 */             proporcion = dofm.getCantidad().divide(ordenFabricacion.getCantidad(), 10, RoundingMode.HALF_UP);
/* 1578:1873 */             BigDecimal cantidadMaterial = cantidad.multiply(proporcion).setScale(2, RoundingMode.HALF_UP);
/* 1579:1874 */             localDetalleMovimientoInventario1 = crearDetalleMateriales(dofm.getMaterial(), cantidadMaterial, movimientoEgresoMateriales, inventarioProducto);
/* 1580:     */           }
/* 1581:     */         }
/* 1582:     */       }
/* 1583:1879 */       for (OrdenFabricacion suborden : listaSubordenes)
/* 1584:     */       {
/* 1585:1880 */         BigDecimal proporcion = suborden.getProporcionOrdenPadre();
/* 1586:1881 */         BigDecimal cantidadMaterial = cantidad.multiply(proporcion).setScale(2, RoundingMode.HALF_UP);
/* 1587:1882 */         localDetalleMovimientoInventario1 = crearDetalleMateriales(suborden.getProducto(), cantidadMaterial, movimientoEgresoMateriales, inventarioProducto);
/* 1588:     */       }
/* 1589:     */     }
/* 1590:1887 */     if (movimientoIngresoProducto.getDetalleMovimientosInventario().size() > 0)
/* 1591:     */     {
/* 1592:1888 */       movimientoIngresoProducto.setIndicadorGenerarCostos(false);
/* 1593:1889 */       movimientoIngresoProducto.setEstado(Estado.PROCESADO);
/* 1594:1890 */       guardar(movimientoIngresoProducto);
/* 1595:     */       
/* 1596:1892 */       movimientoEgresoMateriales.setEstado(Estado.PROCESADO);
/* 1597:1893 */       movimientoEgresoMateriales.setNumero(movimientoIngresoProducto.getNumero());
/* 1598:     */       
/* 1599:1895 */       guardar(movimientoEgresoMateriales);
/* 1600:1896 */       this.movimientoInventarioDao.flush();
/* 1601:     */       
/* 1602:     */ 
/* 1603:     */ 
/* 1604:     */ 
/* 1605:1901 */       this.servicioCosteo.generarCostos(movimientoIngresoProducto, 
/* 1606:1902 */         ParametrosSistema.isCosteoPorBodega(movimientoIngresoProducto.getIdOrganizacion()).booleanValue(), Integer.valueOf(versionCosteo.intValue() + 1));
/* 1607:1903 */       this.movimientoInventarioDao.flush();
/* 1608:     */     }
/* 1609:1911 */     return movimientoEgresoMateriales;
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   private DetalleMovimientoInventario crearDetalleMateriales(Producto material, BigDecimal cantidadMaterial, MovimientoInventario movimientoEgresoMateriales, InventarioProducto inventarioProducto)
/* 1613:     */     throws AS2Exception
/* 1614:     */   {
/* 1615:1917 */     DetalleMovimientoInventario dmiMateriales = new DetalleMovimientoInventario();
/* 1616:1918 */     dmiMateriales.setProducto(material);
/* 1617:1919 */     dmiMateriales.setCantidad(cantidadMaterial);
/* 1618:1920 */     Bodega bodegaTrabajoMaterial = this.servicioProducto.obtenerBodegaTrabajoProducto(material, Integer.valueOf(movimientoEgresoMateriales.getSucursal().getId()));
/* 1619:1921 */     if (bodegaTrabajoMaterial == null) {
/* 1620:1922 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PARAMETRIZAR_BODEGA_TRABAJO", new String[] { material.getNombre() });
/* 1621:     */     }
/* 1622:1924 */     dmiMateriales.setBodegaOrigen(bodegaTrabajoMaterial);
/* 1623:1925 */     dmiMateriales.setUnidadConversion(dmiMateriales.getProducto().getUnidadAlmacenamiento());
/* 1624:1926 */     dmiMateriales.setMovimientoInventario(movimientoEgresoMateriales);
/* 1625:1927 */     dmiMateriales.setIdOrganizacion(movimientoEgresoMateriales.getIdOrganizacion());
/* 1626:1928 */     dmiMateriales.setIdSucursal(movimientoEgresoMateriales.getSucursal().getId());
/* 1627:     */     
/* 1628:1930 */     InventarioProducto inventarioProductoMateriales = new InventarioProducto();
/* 1629:1931 */     inventarioProductoMateriales.setInventarioProductoTransformacion(inventarioProducto);
/* 1630:1932 */     inventarioProductoMateriales.setDetalleMovimientoInventario(dmiMateriales);
/* 1631:1933 */     inventarioProductoMateriales.setIndicadorTransformacion(Boolean.valueOf(true));
/* 1632:1934 */     inventarioProductoMateriales.setCantidad(dmiMateriales.getCantidad());
/* 1633:1935 */     inventarioProductoMateriales.setFecha(movimientoEgresoMateriales.getFecha());
/* 1634:1936 */     inventarioProductoMateriales.setDocumento(movimientoEgresoMateriales.getDocumento());
/* 1635:1937 */     inventarioProductoMateriales.setIdOrganizacion(movimientoEgresoMateriales.getIdOrganizacion());
/* 1636:1938 */     inventarioProductoMateriales.setOperacion(movimientoEgresoMateriales.getDocumento().getOperacion());
/* 1637:1939 */     inventarioProductoMateriales.setIdSucursal(inventarioProducto.getIdSucursal());
/* 1638:1940 */     inventarioProductoMateriales.setBodega(dmiMateriales.getBodegaOrigen());
/* 1639:1941 */     inventarioProductoMateriales.setProducto(dmiMateriales.getProducto());
/* 1640:1942 */     inventarioProductoMateriales.setNumeroDocumento(movimientoEgresoMateriales.getNumero());
/* 1641:1943 */     inventarioProductoMateriales.setCantidadDocumento(dmiMateriales.getCantidad());
/* 1642:1944 */     inventarioProductoMateriales.setUnidadDocumento(dmiMateriales.getProducto().getUnidad().getNombre());
/* 1643:     */     
/* 1644:1946 */     dmiMateriales.setInventarioProducto(inventarioProductoMateriales);
/* 1645:1947 */     movimientoEgresoMateriales.getDetalleMovimientosInventario().add(dmiMateriales);
/* 1646:1948 */     return dmiMateriales;
/* 1647:     */   }
/* 1648:     */   
/* 1649:     */   public void cargarDetalleConsumoBodega(MovimientoInventario movimientoInventario, String fileName, InputStream imInputStream, int filaInicial)
/* 1650:     */     throws ExcepcionAS2
/* 1651:     */   {
/* 1652:1961 */     int filaActual = filaInicial - 1;
/* 1653:1962 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 1654:1963 */     int columnaErronea = -1;
/* 1655:     */     try
/* 1656:     */     {
/* 1657:1969 */       HashMap<String, Producto> hmProducto = new HashMap();
/* 1658:1970 */       HashMap<String, Bodega> hmBodega = new HashMap();
/* 1659:1971 */       HashMap<String, Unidad> hmUnidad = new HashMap();
/* 1660:1972 */       HashMap<String, DestinoCosto> hmDestinoCosto = new HashMap();
/* 1661:1973 */       HashMap<String, Lote> hmLote = new HashMap();
/* 1662:1975 */       for (Iterator localIterator = this.servicioUnidad.obtenerListaCombo("nombre", true, null).iterator(); localIterator.hasNext();)
/* 1663:     */       {
/* 1664:1975 */         unidad = (Unidad)localIterator.next();
/* 1665:1976 */         hmUnidad.put(unidad.getCodigo(), unidad);
/* 1666:     */       }
/* 1667:     */       Unidad unidad;
/* 1668:1979 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(fileName, imInputStream, filaInicial, 0);
/* 1669:1981 */       for (HSSFCell[] fila : datos)
/* 1670:     */       {
/* 1671:1983 */         filaActual++;
/* 1672:1984 */         filaErronea = fila;
/* 1673:1985 */         String codigoProducto = fila[(columnaErronea = 0)] != null ? fila[0].getStringCellValue().trim() : "";
/* 1674:1986 */         String codigoBodega = fila[(columnaErronea = 1)] != null ? fila[1].getStringCellValue().trim() : "";
/* 1675:1987 */         String notaDetalle = fila[(columnaErronea = 2)] != null ? fila[2].getStringCellValue().trim() : "";
/* 1676:1988 */         String nombreUnidad = fila[(columnaErronea = 3)] != null ? fila[3].getStringCellValue().trim() : "";
/* 1677:1989 */         BigDecimal cantidad = fila[(columnaErronea = 4)] != null ? new BigDecimal(fila[4].getNumericCellValue()) : BigDecimal.ZERO;
/* 1678:1990 */         String codigoDestinoCosto = fila[(columnaErronea = 5)] != null ? fila[5].getStringCellValue().trim() : "";
/* 1679:1991 */         String codigoLote = fila[(columnaErronea = 6)] != null ? fila[6].getStringCellValue().trim() : "";
/* 1680:     */         
/* 1681:1993 */         cantidad = FuncionesUtiles.redondearBigDecimal(cantidad, 2);
/* 1682:     */         
/* 1683:     */ 
/* 1684:     */ 
/* 1685:     */ 
/* 1686:1998 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 1687:1999 */         if (producto == null)
/* 1688:     */         {
/* 1689:2000 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, movimientoInventario.getIdOrganizacion(), null);
/* 1690:2001 */           hmProducto.put(codigoProducto, producto);
/* 1691:     */         }
/* 1692:2006 */         Bodega bodega = (Bodega)hmBodega.get(codigoBodega);
/* 1693:2007 */         if (bodega == null)
/* 1694:     */         {
/* 1695:2008 */           bodega = this.servicioBodega.buscarPorCodigo(codigoBodega);
/* 1696:2009 */           hmBodega.put(codigoBodega, bodega);
/* 1697:     */         }
/* 1698:2014 */         DestinoCosto destinoCosto = (DestinoCosto)hmDestinoCosto.get(codigoDestinoCosto);
/* 1699:2015 */         if (destinoCosto == null)
/* 1700:     */         {
/* 1701:2016 */           destinoCosto = this.servicioDestinoCosto.buscarPorCodigo(codigoDestinoCosto);
/* 1702:2017 */           hmDestinoCosto.put(codigoDestinoCosto, destinoCosto);
/* 1703:     */         }
/* 1704:2022 */         Lote lote = (Lote)hmLote.get(codigoLote);
/* 1705:2023 */         if (producto.isIndicadorLote())
/* 1706:     */         {
/* 1707:2024 */           if (codigoLote.trim().isEmpty()) {
/* 1708:2025 */             throw new ExcepcionAS2("msg_migracion_consumo_bodega_lote_requerido", " Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1709:     */           }
/* 1710:2028 */           if (lote == null)
/* 1711:     */           {
/* 1712:2029 */             lote = this.servicioLote.buscarPorCodigo(codigoLote, producto);
/* 1713:2030 */             hmLote.put(codigoLote, lote);
/* 1714:     */           }
/* 1715:     */         }
/* 1716:2036 */         if ((hmProducto.get(codigoProducto) != null) && (hmBodega.get(codigoBodega) != null))
/* 1717:     */         {
/* 1718:2038 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 1719:     */           
/* 1720:2040 */           DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 1721:2041 */           detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getId());
/* 1722:2042 */           detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1723:2043 */           detalleMovimientoInventario.setCantidadOrigen(cantidad);
/* 1724:2044 */           detalleMovimientoInventario.setCosto(BigDecimal.ZERO);
/* 1725:2045 */           detalleMovimientoInventario.setDescripcion(notaDetalle);
/* 1726:2046 */           detalleMovimientoInventario.setBodegaDestino(null);
/* 1727:2047 */           detalleMovimientoInventario.setBodegaOrigen(bodega);
/* 1728:2048 */           detalleMovimientoInventario.setProducto(producto);
/* 1729:2049 */           detalleMovimientoInventario.setUnidadConversion((Unidad)hmUnidad.get(nombreUnidad));
/* 1730:2050 */           detalleMovimientoInventario.setDestinoCosto(destinoCosto);
/* 1731:2051 */           detalleMovimientoInventario.setMovimientoInventario(movimientoInventario);
/* 1732:     */           
/* 1733:2053 */           InventarioProducto inventarioProducto = new InventarioProducto();
/* 1734:2054 */           inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 1735:2055 */           inventarioProducto.setProducto(detalleMovimientoInventario.getProducto());
/* 1736:2056 */           inventarioProducto.setCantidadDocumento(detalleMovimientoInventario.getCantidad());
/* 1737:2057 */           if (producto.isIndicadorLote()) {
/* 1738:2058 */             inventarioProducto.setLote(lote);
/* 1739:     */           }
/* 1740:2060 */           detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 1741:     */           
/* 1742:2062 */           movimientoInventario.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 1743:     */         }
/* 1744:     */       }
/* 1745:     */     }
/* 1746:     */     catch (IllegalArgumentException e)
/* 1747:     */     {
/* 1748:2068 */       this.context.setRollbackOnly();
/* 1749:2069 */       movimientoInventario.getDetalleMovimientosInventario().clear();
/* 1750:     */       
/* 1751:2071 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 1752:     */     }
/* 1753:     */     catch (ExcepcionAS2Financiero e)
/* 1754:     */     {
/* 1755:2073 */       this.context.setRollbackOnly();
/* 1756:2074 */       movimientoInventario.getDetalleMovimientosInventario().clear();
/* 1757:2075 */       e.printStackTrace();
/* 1758:2076 */       throw e;
/* 1759:     */     }
/* 1760:     */     catch (ExcepcionAS2Compras e)
/* 1761:     */     {
/* 1762:2078 */       this.context.setRollbackOnly();
/* 1763:2079 */       movimientoInventario.getDetalleMovimientosInventario().clear();
/* 1764:2080 */       throw e;
/* 1765:     */     }
/* 1766:     */     catch (ExcepcionAS2 e)
/* 1767:     */     {
/* 1768:2082 */       this.context.setRollbackOnly();
/* 1769:2083 */       movimientoInventario.getDetalleMovimientosInventario().clear();
/* 1770:2084 */       throw e;
/* 1771:     */     }
/* 1772:     */     catch (Exception e)
/* 1773:     */     {
/* 1774:2086 */       this.context.setRollbackOnly();
/* 1775:2087 */       movimientoInventario.getDetalleMovimientosInventario().clear();
/* 1776:2088 */       throw new ExcepcionAS2(e);
/* 1777:     */     }
/* 1778:     */   }
/* 1779:     */   
/* 1780:     */   public void cargarDetalleTransferenciaArchivoTexto(TipoOrganizacion tipoOrganizacion, MovimientoInventario transferencia, String filename, InputStream inputStream, Bodega bodega, Bodega bodegaDestino)
/* 1781:     */     throws ExcepcionAS2
/* 1782:     */   {
/* 1783:     */     try
/* 1784:     */     {
/* 1785:2104 */       if (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/* 1786:2105 */         cargarDetalleTransferenciaArchivoTextoPadilla(transferencia, inputStream, bodega, bodegaDestino);
/* 1787:     */       } else {
/* 1788:2107 */         cargarDetalleTransferenciaBodega(transferencia, filename, inputStream, 5);
/* 1789:     */       }
/* 1790:     */     }
/* 1791:     */     catch (IllegalArgumentException e)
/* 1792:     */     {
/* 1793:2110 */       this.context.setRollbackOnly();
/* 1794:2111 */       e.printStackTrace();
/* 1795:2112 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", e.getMessage());
/* 1796:     */     }
/* 1797:     */     catch (ExcepcionAS2 e)
/* 1798:     */     {
/* 1799:2114 */       this.context.setRollbackOnly();
/* 1800:2115 */       e.printStackTrace();
/* 1801:2116 */       transferencia.getDetalleMovimientosInventario().clear();
/* 1802:2117 */       throw e;
/* 1803:     */     }
/* 1804:     */     catch (IOException e)
/* 1805:     */     {
/* 1806:2119 */       this.context.setRollbackOnly();
/* 1807:2120 */       e.printStackTrace();
/* 1808:2121 */       throw new ExcepcionAS2(e);
/* 1809:     */     }
/* 1810:     */     catch (Exception e)
/* 1811:     */     {
/* 1812:2123 */       this.context.setRollbackOnly();
/* 1813:2124 */       e.printStackTrace();
/* 1814:2125 */       throw new ExcepcionAS2(e);
/* 1815:     */     }
/* 1816:     */   }
/* 1817:     */   
/* 1818:     */   private void cargarDetalleTransferenciaArchivoTextoPadilla(MovimientoInventario transferencia, InputStream inputStream, Bodega bodega, Bodega bodegaDestino)
/* 1819:     */     throws IllegalArgumentException, ExcepcionAS2, IOException
/* 1820:     */   {
/* 1821:2140 */     DetalleMovimientoInventario detalleMovimientoInventario = null;
/* 1822:2141 */     List<String> datosArchivo = FuncionesUtiles.leerArchivoTexto(inputStream);
/* 1823:2142 */     int cont = 0;
/* 1824:2143 */     Lote lote = null;
/* 1825:2144 */     Map<String, Lote> mapaLote = new HashMap();
/* 1826:2145 */     Map<String, String> mapaDatosArchivo = new HashMap();
/* 1827:2146 */     Map<String, String> mapaLoteAuxiliar = new HashMap();
/* 1828:2148 */     for (DetalleMovimientoInventario detalleMovimientoInventarioTodos : transferencia.getDetalleMovimientosInventario()) {
/* 1829:2149 */       if (!detalleMovimientoInventarioTodos.isEliminado()) {
/* 1830:2150 */         mapaLoteAuxiliar.put(detalleMovimientoInventarioTodos.getInventarioProducto().getLote().getCodigo(), detalleMovimientoInventarioTodos
/* 1831:2151 */           .getInventarioProducto().getLote().getCodigo());
/* 1832:     */       }
/* 1833:     */     }
/* 1834:2155 */     for (String dato : datosArchivo)
/* 1835:     */     {
/* 1836:2156 */       if (cont > 0)
/* 1837:     */       {
/* 1838:2158 */         String datoTruncado = dato.substring(1, dato.length());
/* 1839:2159 */         mapaDatosArchivo.put(String.valueOf(datoTruncado), String.valueOf(datoTruncado));
/* 1840:     */       }
/* 1841:2161 */       cont++;
/* 1842:     */     }
/* 1843:2164 */     for (String datoNumero : mapaDatosArchivo.values())
/* 1844:     */     {
/* 1845:2166 */       if (mapaLote.containsKey(datoNumero))
/* 1846:     */       {
/* 1847:2167 */         lote = (Lote)mapaLote.get(String.valueOf(datoNumero));
/* 1848:     */       }
/* 1849:     */       else
/* 1850:     */       {
/* 1851:2169 */         lote = this.servicioLote.buscarPorCodigo(String.valueOf(datoNumero));
/* 1852:2170 */         mapaLote.put(String.valueOf(datoNumero), lote);
/* 1853:     */       }
/* 1854:2172 */       if (lote != null)
/* 1855:     */       {
/* 1856:2173 */         Producto producto = this.servicioProducto.buscarPorCodigo(lote.getProducto().getCodigo(), transferencia.getIdOrganizacion(), null);
/* 1857:2175 */         if (producto != null)
/* 1858:     */         {
/* 1859:2177 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 1860:     */           
/* 1861:2179 */           Bodega bodegaDato = bodega != null ? bodega : producto.getBodegaVenta();
/* 1862:2180 */           detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 1863:2181 */           detalleMovimientoInventario.setIdSucursal(transferencia.getSucursal().getId());
/* 1864:2182 */           detalleMovimientoInventario.setIdOrganizacion(transferencia.getIdOrganizacion());
/* 1865:2183 */           BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), bodegaDato.getIdBodega(), lote.getIdLote(), transferencia
/* 1866:2184 */             .getFecha());
/* 1867:2185 */           detalleMovimientoInventario.setSaldo(saldo);
/* 1868:2186 */           detalleMovimientoInventario.setCantidad(saldo.setScale(4, RoundingMode.HALF_UP));
/* 1869:2187 */           detalleMovimientoInventario.setDescripcion("");
/* 1870:2188 */           detalleMovimientoInventario.setProducto(producto);
/* 1871:2189 */           detalleMovimientoInventario.setInventarioProducto(new InventarioProducto());
/* 1872:2190 */           detalleMovimientoInventario.getInventarioProducto().setLote(lote);
/* 1873:2191 */           detalleMovimientoInventario.setMovimientoInventario(transferencia);
/* 1874:2192 */           detalleMovimientoInventario.setBodegaOrigen(bodegaDato);
/* 1875:2193 */           detalleMovimientoInventario.setBodegaDestino(bodegaDestino);
/* 1876:2194 */           detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 1877:2195 */           if (!mapaLoteAuxiliar.containsKey(lote.getCodigo())) {
/* 1878:2196 */             transferencia.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 1879:     */           }
/* 1880:     */         }
/* 1881:     */       }
/* 1882:     */     }
/* 1883:     */   }
/* 1884:     */   
/* 1885:     */   public MovimientoInventario copiarMovimientoInventario(MovimientoInventario movimientoInventario)
/* 1886:     */     throws ExcepcionAS2Financiero
/* 1887:     */   {
/* 1888:2207 */     movimientoInventario.setIdMovimientoInventario(0);
/* 1889:2208 */     movimientoInventario.setNumero("");
/* 1890:2209 */     movimientoInventario.setEstado(Estado.ELABORADO);
/* 1891:2210 */     movimientoInventario.setFechaContabilizacion(null);
/* 1892:2211 */     movimientoInventario.setAsiento(null);
/* 1893:2212 */     movimientoInventario.setFecha(new Date());
/* 1894:2213 */     movimientoInventario.setInterfazContableProceso(null);
/* 1895:2214 */     movimientoInventario.setMovimientoInventarioPadre(null);
/* 1896:2215 */     movimientoInventario.setTomaFisica(null);
/* 1897:2217 */     for (DetalleMovimientoInventario detalleMovimientoInventario : movimientoInventario.getDetalleMovimientosInventario())
/* 1898:     */     {
/* 1899:2218 */       detalleMovimientoInventario.setIdDetalleMovimientoInventario(0);
/* 1900:2219 */       detalleMovimientoInventario.getInventarioProducto().setIdInventarioProducto(0);
/* 1901:     */     }
/* 1902:2222 */     return movimientoInventario;
/* 1903:     */   }
/* 1904:     */   
/* 1905:     */   public List getReporteAprobarAjusteInventario(int idMovimientoInventario)
/* 1906:     */   {
/* 1907:2229 */     return this.movimientoInventarioDao.getReporteAprobarAjusteInventario(idMovimientoInventario);
/* 1908:     */   }
/* 1909:     */   
/* 1910:     */   public List<MovimientoInventario> getListaAjusteInvetario(int idOrganizacion, Date fechaDesde, Date fechaHasta, TipoOrganizacion tipoOrganizacion)
/* 1911:     */   {
/* 1912:2235 */     return this.movimientoInventarioDao.getListaAjusteInvetario(idOrganizacion, fechaDesde, fechaHasta, tipoOrganizacion, null);
/* 1913:     */   }
/* 1914:     */   
/* 1915:     */   public List<MovimientoInventario> getListaAjusteInvetario(int idOrganizacion, Date fechaDesde, Date fechaHasta, TipoOrganizacion tipoOrganizacion, List<DocumentoBase> listaDocumento)
/* 1916:     */   {
/* 1917:2247 */     return this.movimientoInventarioDao.getListaAjusteInvetario(idOrganizacion, fechaDesde, fechaHasta, tipoOrganizacion, listaDocumento);
/* 1918:     */   }
/* 1919:     */   
/* 1920:     */   public void guardarDetalleMovimiento(MovimientoInventario movimientoInventario)
/* 1921:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1922:     */   {
/* 1923:2252 */     esEditable(movimientoInventario);
/* 1924:2253 */     for (DetalleMovimientoInventario detalleMovimientoInventario : movimientoInventario.getDetalleMovimientosInventario())
/* 1925:     */     {
/* 1926:2255 */       InventarioProducto ip = this.servicioInventarioProducto.cargarDetalle(detalleMovimientoInventario.getInventarioProducto().getIdInventarioProducto());
/* 1927:2256 */       ip.setCosto(detalleMovimientoInventario.getInventarioProducto().getCosto());
/* 1928:2257 */       this.servicioInventarioProducto.guardar(ip);
/* 1929:2258 */       this.detalleMovimientoInventarioDao.guardar(detalleMovimientoInventario);
/* 1930:     */     }
/* 1931:2260 */     this.movimientoInventarioDao.guardar(movimientoInventario);
/* 1932:2261 */     contabilizar(movimientoInventario);
/* 1933:     */   }
/* 1934:     */   
/* 1935:     */   public void cargarDetalleTransferenciaBodega(MovimientoInventario transferencia, String fileName, InputStream imInputStream, int filaInicial)
/* 1936:     */     throws ExcepcionAS2
/* 1937:     */   {
/* 1938:2269 */     int filaActual = filaInicial;
/* 1939:2270 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 1940:2271 */     int columnaErronea = -1;
/* 1941:     */     try
/* 1942:     */     {
/* 1943:2277 */       HashMap<String, Producto> hmProducto = new HashMap();
/* 1944:2278 */       HashMap<String, Unidad> hmUnidad = new HashMap();
/* 1945:2279 */       HashMap<String, Lote> hmLote = new HashMap();
/* 1946:2281 */       for (Unidad unidad : this.servicioUnidad.obtenerListaCombo("nombre", true, null)) {
/* 1947:2282 */         hmUnidad.put(unidad.getCodigo(), unidad);
/* 1948:     */       }
/* 1949:2285 */       for (??? = this.servicioLote.obtenerListaCombo("codigo", true, null).iterator(); ???.hasNext();)
/* 1950:     */       {
/* 1951:2285 */         lote = (Lote)???.next();
/* 1952:2286 */         hmLote.put(lote.getCodigo(), lote);
/* 1953:     */       }
/* 1954:     */       Lote lote;
/* 1955:2289 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(fileName, imInputStream, filaInicial, 0);
/* 1956:2291 */       for (HSSFCell[] fila : datos)
/* 1957:     */       {
/* 1958:2293 */         filaErronea = fila;
/* 1959:     */         
/* 1960:2295 */         columnaErronea = 0;
/* 1961:2296 */         String codigoProducto = fila[0] != null ? fila[0].getStringCellValue().trim() : "";
/* 1962:2297 */         columnaErronea = 1;
/* 1963:2298 */         String notaDetalle = fila[1] != null ? fila[1].getStringCellValue().trim() : "";
/* 1964:2299 */         columnaErronea = 2;
/* 1965:2300 */         String nombreUnidad = fila[2] != null ? fila[2].getStringCellValue().trim() : "";
/* 1966:2301 */         columnaErronea = 3;
/* 1967:2302 */         BigDecimal cantidad = fila[3] != null ? new BigDecimal(fila[3].getNumericCellValue()) : BigDecimal.ZERO;
/* 1968:2303 */         columnaErronea = 4;
/* 1969:2304 */         String codigoLote = fila[4] != null ? fila[4].getStringCellValue().trim() : null;
/* 1970:2305 */         columnaErronea = 5;
/* 1971:2306 */         BigDecimal cantidadUnidadInformativa = fila[5] != null ? new BigDecimal(fila[5].getNumericCellValue()) : BigDecimal.ZERO;
/* 1972:2307 */         cantidadUnidadInformativa = cantidadUnidadInformativa.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : cantidadUnidadInformativa;
/* 1973:     */         
/* 1974:2309 */         cantidad = FuncionesUtiles.redondearBigDecimal(cantidad, 2);
/* 1975:     */         
/* 1976:     */ 
/* 1977:     */ 
/* 1978:     */ 
/* 1979:2314 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 1980:2315 */         if (producto == null)
/* 1981:     */         {
/* 1982:2316 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, transferencia.getIdOrganizacion(), null);
/* 1983:2317 */           hmProducto.put(codigoProducto, producto);
/* 1984:     */         }
/* 1985:2322 */         if (hmProducto.get(codigoProducto) != null)
/* 1986:     */         {
/* 1987:2324 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 1988:     */           
/* 1989:2326 */           DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 1990:2327 */           detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getId());
/* 1991:2328 */           detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1992:2329 */           detalleMovimientoInventario.setCantidad(cantidad);
/* 1993:2330 */           detalleMovimientoInventario.setDescripcion(notaDetalle);
/* 1994:2331 */           detalleMovimientoInventario.setBodegaDestino(transferencia.getBodegaDestino());
/* 1995:2332 */           detalleMovimientoInventario.setBodegaOrigen(transferencia.getBodegaOrigen());
/* 1996:2333 */           detalleMovimientoInventario.setProducto(producto);
/* 1997:2334 */           detalleMovimientoInventario.setUnidadConversion((Unidad)hmUnidad.get(nombreUnidad));
/* 1998:2335 */           detalleMovimientoInventario.setMovimientoInventario(transferencia);
/* 1999:2336 */           detalleMovimientoInventario.setCantidadUnidadInformativa(cantidadUnidadInformativa);
/* 2000:2337 */           transferencia.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 2001:2339 */           if (!ParametrosSistema.getIndicadorAprobarAjusteInventario(transferencia.getIdOrganizacion()).booleanValue())
/* 2002:     */           {
/* 2003:2341 */             InventarioProducto inventarioProducto = new InventarioProducto();
/* 2004:2342 */             inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 2005:2343 */             inventarioProducto.setProducto(detalleMovimientoInventario.getProducto());
/* 2006:2344 */             inventarioProducto.setCantidadDocumento(detalleMovimientoInventario.getCantidad());
/* 2007:2346 */             if (producto.isIndicadorLote())
/* 2008:     */             {
/* 2009:2348 */               Lote lote = (Lote)hmLote.get(codigoLote);
/* 2010:2349 */               if (lote != null)
/* 2011:     */               {
/* 2012:2350 */                 inventarioProducto.setLote(lote);
/* 2013:     */               }
/* 2014:     */               else
/* 2015:     */               {
/* 2016:2352 */                 detalleMovimientoInventario.setInventarioProducto(new InventarioProducto());
/* 2017:2353 */                 throw new ExcepcionAS2("msg_info_error_codigo_lote", " " + codigoLote);
/* 2018:     */               }
/* 2019:     */             }
/* 2020:2357 */             detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 2021:     */           }
/* 2022:     */         }
/* 2023:     */       }
/* 2024:     */     }
/* 2025:     */     catch (IllegalArgumentException e)
/* 2026:     */     {
/* 2027:2366 */       this.context.setRollbackOnly();
/* 2028:2367 */       throw new ExcepcionAS2("msg_error_formato_incorrecto");
/* 2029:     */     }
/* 2030:     */     catch (ExcepcionAS2Financiero e)
/* 2031:     */     {
/* 2032:2369 */       this.context.setRollbackOnly();
/* 2033:2370 */       e.printStackTrace();
/* 2034:2371 */       throw e;
/* 2035:     */     }
/* 2036:     */     catch (ExcepcionAS2Compras e)
/* 2037:     */     {
/* 2038:2373 */       this.context.setRollbackOnly();
/* 2039:2374 */       throw e;
/* 2040:     */     }
/* 2041:     */     catch (ExcepcionAS2 e)
/* 2042:     */     {
/* 2043:2376 */       this.context.setRollbackOnly();
/* 2044:2377 */       throw e;
/* 2045:     */     }
/* 2046:     */     catch (Exception e)
/* 2047:     */     {
/* 2048:2379 */       this.context.setRollbackOnly();
/* 2049:2380 */       throw new ExcepcionAS2(e);
/* 2050:     */     }
/* 2051:     */   }
/* 2052:     */   
/* 2053:     */   public void setearDescripcion(MovimientoInventario mi)
/* 2054:     */   {
/* 2055:2392 */     Map<Integer, String> hmOrdenSalida = new HashMap();
/* 2056:2394 */     for (DetalleMovimientoInventario dmi : mi.getDetalleMovimientosInventario()) {
/* 2057:2395 */       if (!dmi.isEliminado())
/* 2058:     */       {
/* 2059:2396 */         DetalleOrdenSalidaMaterial dosm = dmi.getDetalleOrdenSalidaMaterial();
/* 2060:2397 */         if ((dosm != null) && (!dosm.isEliminado())) {
/* 2061:2398 */           hmOrdenSalida.put(Integer.valueOf(dosm.getOrdenSalidaMaterial().getId()), dosm.getOrdenSalidaMaterial().getNumero());
/* 2062:     */         }
/* 2063:     */       }
/* 2064:     */     }
/* 2065:2402 */     mi.setDescripcion(FuncionesUtiles.formarDescripcion(hmOrdenSalida.values(), mi.getDescripcion(), 200));
/* 2066:     */   }
/* 2067:     */   
/* 2068:     */   public MovimientoInventario obtenerIngresoFabricacionPorFecha(int idOrganizacion, Date fecha, OrdenFabricacion ordenFabricacion)
/* 2069:     */   {
/* 2070:2407 */     fecha = FuncionesUtiles.setAtributoFecha(fecha);
/* 2071:2408 */     return this.movimientoInventarioDao.obtenerIngresoFabricacionPorFecha(idOrganizacion, fecha, ordenFabricacion);
/* 2072:     */   }
/* 2073:     */   
/* 2074:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2075:     */   public MovimientoInventario guardarIngresoFabricacion(MovimientoInventario movimientoInventario, DetalleMovimientoInventario detalleMovimientoInventario, LecturaBalanza lecturaBalanza, TipoOrganizacion tipoOrganizacion)
/* 2076:     */     throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2, ExcepcionAS2Financiero
/* 2077:     */   {
/* 2078:2417 */     validarIngresoFabricacion(movimientoInventario, detalleMovimientoInventario, lecturaBalanza, tipoOrganizacion);
/* 2079:2420 */     if (lecturaBalanza != null)
/* 2080:     */     {
/* 2081:2421 */       Producto producto = lecturaBalanza.getProducto();
/* 2082:2422 */       detalleMovimientoInventario.setProducto(producto);
/* 2083:2424 */       if (detalleMovimientoInventario.getInventarioProducto() != null) {
/* 2084:2425 */         detalleMovimientoInventario.getInventarioProducto().setProducto(producto);
/* 2085:     */       }
/* 2086:2428 */       this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 2087:     */       
/* 2088:     */ 
/* 2089:2431 */       BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(lecturaBalanza);
/* 2090:2432 */       BigDecimal cantidad = cantidades[0];
/* 2091:2433 */       BigDecimal cantidadInformativa = cantidades[1];
/* 2092:     */       
/* 2093:2435 */       detalleMovimientoInventario.setCantidad(cantidad);
/* 2094:2436 */       detalleMovimientoInventario.setCantidadOrigen(cantidad);
/* 2095:2437 */       detalleMovimientoInventario.setCantidadUnidadInformativa(cantidadInformativa);
/* 2096:     */       
/* 2097:2439 */       detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 2098:2440 */       lecturaBalanza.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 2099:2441 */       detalleMovimientoInventario.getListaLecturaBalanza().add(lecturaBalanza);
/* 2100:     */     }
/* 2101:2444 */     if (detalleMovimientoInventario.getCantidad().compareTo(BigDecimal.ZERO) != 0)
/* 2102:     */     {
/* 2103:2445 */       OrdenFabricacion ordenFabricacion = movimientoInventario.getOrdenFabricacion();
/* 2104:     */       
/* 2105:     */ 
/* 2106:     */ 
/* 2107:2449 */       BigDecimal maximo_desvio = ordenFabricacion.getCantidad().multiply(detalleMovimientoInventario.getProducto().getMaximoDesvio()).divide(new BigDecimal(100));
/* 2108:2451 */       if (detalleMovimientoInventario.getCantidad().compareTo(ordenFabricacion.getCantidad().add(maximo_desvio).subtract(ordenFabricacion.getCantidadFabricada())) > 0) {
/* 2109:2452 */         throw new AS2Exception("msg_error_ingreso_fabricacion_mayor", new String[] { "" });
/* 2110:     */       }
/* 2111:2455 */       Date fecha = FuncionesUtiles.setAtributoFecha(movimientoInventario.getFecha());
/* 2112:2456 */       Date hoy = FuncionesUtiles.setAtributoFecha(new Date());
/* 2113:2458 */       if (detalleMovimientoInventario.getProducto().isIndicadorLote())
/* 2114:     */       {
/* 2115:2459 */         Lote lote = detalleMovimientoInventario.getLote();
/* 2116:2460 */         if (lote == null) {
/* 2117:     */           try
/* 2118:     */           {
/* 2119:2462 */             lote = this.servicioLote.buscarPorCodigo(ordenFabricacion.getNumero(), detalleMovimientoInventario.getProducto());
/* 2120:     */           }
/* 2121:     */           catch (ExcepcionAS2 e)
/* 2122:     */           {
/* 2123:2465 */             lote = this.servicioLote.crearLoteInterno(movimientoInventario.getIdOrganizacion(), detalleMovimientoInventario.getProducto(), ordenFabricacion
/* 2124:2466 */               .getNumero(), true);
/* 2125:     */           }
/* 2126:     */         } else {
/* 2127:2469 */           this.servicioLote.guardar(lote);
/* 2128:     */         }
/* 2129:2471 */         detalleMovimientoInventario.setLote(lote);
/* 2130:     */       }
/* 2131:2474 */       if ((detalleMovimientoInventario.getProducto().isIndicadorLote()) && (detalleMovimientoInventario.getLote() == null))
/* 2132:     */       {
/* 2133:2475 */         Lote lote = null;
/* 2134:     */         try
/* 2135:     */         {
/* 2136:2477 */           lote = this.servicioLote.buscarPorCodigo(ordenFabricacion.getNumero(), detalleMovimientoInventario.getProducto());
/* 2137:     */         }
/* 2138:     */         catch (ExcepcionAS2 e)
/* 2139:     */         {
/* 2140:2480 */           lote = this.servicioLote.crearLoteInterno(movimientoInventario.getIdOrganizacion(), detalleMovimientoInventario.getProducto(), ordenFabricacion
/* 2141:2481 */             .getNumero(), true);
/* 2142:     */         }
/* 2143:2483 */         detalleMovimientoInventario.setLote(lote);
/* 2144:     */       }
/* 2145:2485 */       if (movimientoInventario.getBodegaOrigen() != null) {
/* 2146:2488 */         detalleMovimientoInventario.setBodegaOrigen(movimientoInventario.getBodegaOrigen());
/* 2147:     */       }
/* 2148:2492 */       boolean cambioOrdenFabricacion = false;
/* 2149:2493 */       MovimientoInventario movOriginal = null;
/* 2150:2494 */       if (movimientoInventario.getId() != 0)
/* 2151:     */       {
/* 2152:2495 */         movOriginal = buscarPorId(Integer.valueOf(movimientoInventario.getId()));
/* 2153:2496 */         if (ordenFabricacion.getId() != movOriginal.getOrdenFabricacion().getId()) {
/* 2154:2497 */           cambioOrdenFabricacion = true;
/* 2155:     */         }
/* 2156:     */       }
/* 2157:2503 */       if ((fecha.before(hoy)) || (cambioOrdenFabricacion))
/* 2158:     */       {
/* 2159:2504 */         this.movimientoInventarioDao.detach(movimientoInventario);
/* 2160:2505 */         movimientoInventario.setIdMovimientoInventario(0);
/* 2161:2506 */         movimientoInventario.setFecha(hoy);
/* 2162:2507 */         movimientoInventario.setNumero("");
/* 2163:2508 */         movimientoInventario.setAsiento(null);
/* 2164:2509 */         movimientoInventario.setDetalleMovimientosInventario(new ArrayList());
/* 2165:2510 */         fecha = hoy;
/* 2166:     */       }
/* 2167:     */       else
/* 2168:     */       {
/* 2169:2511 */         if ((movOriginal != null) && (!movOriginal.getEstado().equals(Estado.PROCESADO))) {
/* 2170:2512 */           throw new AS2Exception("msg_error_accion_no_permitida", new String[] { movOriginal.getEstado().getNombre() });
/* 2171:     */         }
/* 2172:2514 */         OrdenFabricacion ordenFabricacionBase = (OrdenFabricacion)this.ordenFabricacionDao.buscarPorId(Integer.valueOf(ordenFabricacion.getId()));
/* 2173:2515 */         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 2174:2516 */         if ((ordenFabricacionBase.getEstado().equals(EstadoProduccionEnum.ANULADO)) || (ordenFabricacionBase.getFechaCierre() != null)) {
/* 2175:2519 */           throw new AS2Exception("msg_error_accion_no_permitida", new String[] {"OF: " + ordenFabricacionBase.getNumero() + " " + ordenFabricacionBase.getEstado().getNombre() + (ordenFabricacionBase.getFechaCierre() != null ? " : " + sdf.format(ordenFabricacionBase.getFechaCierre()) : "") });
/* 2176:     */         }
/* 2177:     */       }
/* 2178:2524 */       if (movimientoInventario.getId() == 0)
/* 2179:     */       {
/* 2180:2525 */         MovimientoInventario mi = obtenerIngresoFabricacionPorFecha(movimientoInventario.getIdOrganizacion(), fecha, ordenFabricacion);
/* 2181:2526 */         if (mi != null) {
/* 2182:2527 */           movimientoInventario = mi;
/* 2183:     */         } else {
/* 2184:2529 */           cargarSecuencia(movimientoInventario);
/* 2185:     */         }
/* 2186:     */       }
/* 2187:2532 */       movimientoInventario.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 2188:2533 */       detalleMovimientoInventario.setMovimientoInventario(movimientoInventario);
/* 2189:     */       try
/* 2190:     */       {
/* 2191:2539 */         if (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA))
/* 2192:     */         {
/* 2193:2542 */           InventarioProducto inventarioProducto = new InventarioProducto();
/* 2194:2543 */           inventarioProducto.setIdOrganizacion(movimientoInventario.getIdOrganizacion());
/* 2195:2544 */           inventarioProducto.setIdSucursal(detalleMovimientoInventario.getIdSucursal());
/* 2196:2545 */           inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 2197:2546 */           inventarioProducto.setDocumento(movimientoInventario.getDocumento());
/* 2198:2547 */           inventarioProducto.setNumeroDocumento(movimientoInventario.getNumero());
/* 2199:2548 */           inventarioProducto.setProducto(detalleMovimientoInventario.getProducto());
/* 2200:     */           
/* 2201:2550 */           inventarioProducto.setLote(detalleMovimientoInventario.getLote());
/* 2202:2551 */           detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 2203:2552 */           inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 2204:     */           
/* 2205:     */ 
/* 2206:2555 */           BigDecimal cantidad = detalleMovimientoInventario.getCantidad();
/* 2207:2556 */           detalleMovimientoInventario.setCantidad(detalleMovimientoInventario.getCantidadOrigen());
/* 2208:2557 */           actualizarInventarioProductoDetalle(detalleMovimientoInventario);
/* 2209:2558 */           detalleMovimientoInventario.setCantidad(cantidad);
/* 2210:2559 */           detalleMovimientoInventario.setIndicadorRecibido(true);
/* 2211:     */           
/* 2212:     */ 
/* 2213:2562 */           this.servicioInventarioProducto.guardar(detalleMovimientoInventario.getInventarioProducto());
/* 2214:     */         }
/* 2215:2567 */         this.detalleMovimientoInventarioDao.guardar(detalleMovimientoInventario);
/* 2216:2570 */         if (lecturaBalanza != null) {
/* 2217:2571 */           this.lecturaBalanzaDao.guardar(lecturaBalanza);
/* 2218:     */         }
/* 2219:2575 */         this.movimientoInventarioDao.guardar(movimientoInventario);
/* 2220:     */         
/* 2221:     */ 
/* 2222:2578 */         this.servicioVerificadorInventario.actualizarCantidadFabricadaOrdenFabricacion(ordenFabricacion, detalleMovimientoInventario
/* 2223:2579 */           .getCantidad());
/* 2224:2582 */         if (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA))
/* 2225:     */         {
/* 2226:2587 */           this.servicioVerificadorInventario.actualizarSaldoDetalle(detalleMovimientoInventario, false, movimientoInventario);
/* 2227:     */           
/* 2228:2589 */           detalleMovimientoInventario.getInventarioProducto().setNumeroDocumento(movimientoInventario.getNumero());
/* 2229:     */           
/* 2230:     */ 
/* 2231:2592 */           this.servicioInventarioProducto.guardar(detalleMovimientoInventario.getInventarioProducto());
/* 2232:2595 */           if (detalleMovimientoInventario.getProducto().isIndicadorManejaUnidadInformativa()) {
/* 2233:2596 */             generarTransformacionUnidadInformativa(detalleMovimientoInventario, null);
/* 2234:     */           }
/* 2235:     */         }
/* 2236:     */       }
/* 2237:     */       catch (Exception e)
/* 2238:     */       {
/* 2239:2608 */         this.context.setRollbackOnly();
/* 2240:2609 */         throw new ExcepcionAS2(e);
/* 2241:     */       }
/* 2242:     */     }
/* 2243:2612 */     return movimientoInventario;
/* 2244:     */   }
/* 2245:     */   
/* 2246:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2247:     */   public void eliminarDetalleIngresoFabricacion(MovimientoInventario movimientoInventario, DetalleMovimientoInventario detalleMovimientoInventario, boolean eliminarInvetarioIngresoFabricacion)
/* 2248:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 2249:     */   {
/* 2250:     */     try
/* 2251:     */     {
/* 2252:2622 */       detalleMovimientoInventario = (DetalleMovimientoInventario)this.detalleMovimientoInventarioDao.buscarPorId(DetalleMovimientoInventario.class, 
/* 2253:2623 */         Integer.valueOf(detalleMovimientoInventario.getId()));
/* 2254:2624 */       if ((!eliminarInvetarioIngresoFabricacion) && (detalleMovimientoInventario.isIndicadorRecibido())) {
/* 2255:2625 */         throw new AS2Exception("msg_error_accion_no_permitida", new String[] { "RECIBIDO" });
/* 2256:     */       }
/* 2257:     */       HashMap<String, String> filters;
/* 2258:2629 */       if (eliminarInvetarioIngresoFabricacion)
/* 2259:     */       {
/* 2260:2632 */         filters = new HashMap();
/* 2261:2633 */         filters.put("detalleMovimientoInventario.idDetalleMovimientoInventario", "=" + detalleMovimientoInventario.getId());
/* 2262:2634 */         List<InventarioProducto> lista = this.inventarioProductoDao.obtenerListaCombo(null, false, filters);
/* 2263:     */         
/* 2264:2636 */         InventarioProducto inventarioProducto = null;
/* 2265:2637 */         if (!lista.isEmpty()) {
/* 2266:2638 */           inventarioProducto = this.inventarioProductoDao.cargarDetalle(((InventarioProducto)lista.get(0)).getId());
/* 2267:     */         }
/* 2268:2642 */         if (inventarioProducto != null)
/* 2269:     */         {
/* 2270:2644 */           this.servicioVerificadorInventario.actualizarSaldoDetalle(inventarioProducto.getDetalleMovimientoInventario(), true, movimientoInventario);
/* 2271:     */           
/* 2272:     */ 
/* 2273:     */ 
/* 2274:2648 */           this.inventarioProductoDao.eliminarInventarioProductoDetalleMovimientoInventario(inventarioProducto.getDetalleMovimientoInventario());
/* 2275:     */         }
/* 2276:     */       }
/* 2277:2654 */       this.servicioVerificadorInventario.actualizarCantidadFabricadaOrdenFabricacion(detalleMovimientoInventario
/* 2278:2655 */         .getMovimientoInventario().getOrdenFabricacion(), detalleMovimientoInventario.getCantidad().negate());
/* 2279:     */       
/* 2280:     */ 
/* 2281:2658 */       movimientoInventario.getDetalleMovimientosInventario().remove(detalleMovimientoInventario);
/* 2282:2661 */       for (LecturaBalanza lectura : detalleMovimientoInventario.getListaLecturaBalanza()) {
/* 2283:2662 */         this.lecturaBalanzaDao.eliminar(lectura);
/* 2284:     */       }
/* 2285:2665 */       MovimientoInventario transformacionAutomatica = detalleMovimientoInventario.getTransformacionAutomatica();
/* 2286:     */       
/* 2287:     */ 
/* 2288:2668 */       this.detalleMovimientoInventarioDao.eliminar(detalleMovimientoInventario);
/* 2289:2670 */       if (transformacionAutomatica != null) {
/* 2290:2671 */         eliminarTransformacionProductoMateriales(transformacionAutomatica);
/* 2291:     */       }
/* 2292:     */     }
/* 2293:     */     catch (ExcepcionAS2 e)
/* 2294:     */     {
/* 2295:2674 */       this.context.setRollbackOnly();
/* 2296:2675 */       throw e;
/* 2297:     */     }
/* 2298:     */     catch (AS2Exception e)
/* 2299:     */     {
/* 2300:2677 */       this.context.setRollbackOnly();
/* 2301:2678 */       throw e;
/* 2302:     */     }
/* 2303:     */     catch (Exception e)
/* 2304:     */     {
/* 2305:2680 */       this.context.setRollbackOnly();
/* 2306:2681 */       throw new ExcepcionAS2(e);
/* 2307:     */     }
/* 2308:     */   }
/* 2309:     */   
/* 2310:     */   public List<DetalleMovimientoInventario> obtenerListaDetalleRecepcionFabricacion(int idOrganizacion, Bodega bodega)
/* 2311:     */   {
/* 2312:2687 */     return this.movimientoInventarioDao.obtenerListaDetalleRecepcionFabricacion(idOrganizacion, bodega);
/* 2313:     */   }
/* 2314:     */   
/* 2315:     */   public List<DetalleMovimientoInventario> obtenerListaDetalleRecepcionFabricacion(int idOrganizacion, Bodega bodega, String usuarioCreacion, List<Integer> idsSucursalesAsignadasUsuarioEnSesion)
/* 2316:     */   {
/* 2317:2693 */     return this.movimientoInventarioDao.obtenerListaDetalleRecepcionFabricacion(idOrganizacion, bodega, usuarioCreacion, idsSucursalesAsignadasUsuarioEnSesion);
/* 2318:     */   }
/* 2319:     */   
/* 2320:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2321:     */   public void recepcionarDetalleIngresoFabricacion(DetalleMovimientoInventario detalleMovimientoInventario)
/* 2322:     */     throws ExcepcionAS2, AS2Exception, ExcepcionAS2Financiero
/* 2323:     */   {
/* 2324:     */     try
/* 2325:     */     {
/* 2326:2702 */       MovimientoInventario movimientoInventario = detalleMovimientoInventario.getMovimientoInventario();
/* 2327:     */       
/* 2328:     */ 
/* 2329:2705 */       this.servicioPeriodo.buscarPorFecha(movimientoInventario.getFecha(), movimientoInventario.getIdOrganizacion(), DocumentoBase.AJUSTE_INVENTARIO);
/* 2330:     */       
/* 2331:     */ 
/* 2332:2708 */       detalleMovimientoInventario.setIndicadorRecibido(true);
/* 2333:2712 */       if (((DetalleMovimientoInventario)this.detalleMovimientoInventarioDao.buscarPorId(DetalleMovimientoInventario.class, Integer.valueOf(detalleMovimientoInventario.getId()))).getInventarioProducto() != null) {
/* 2334:2713 */         throw new ExcepcionAS2("msg_error_existe_ingreso_recepcion_fabricacion", " ");
/* 2335:     */       }
/* 2336:2717 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 2337:2718 */       inventarioProducto.setIdOrganizacion(movimientoInventario.getIdOrganizacion());
/* 2338:2719 */       inventarioProducto.setIdSucursal(detalleMovimientoInventario.getIdSucursal());
/* 2339:2720 */       inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 2340:2721 */       inventarioProducto.setDocumento(movimientoInventario.getDocumento());
/* 2341:2722 */       inventarioProducto.setNumeroDocumento(movimientoInventario.getNumero());
/* 2342:2723 */       inventarioProducto.setProducto(detalleMovimientoInventario.getProducto());
/* 2343:     */       
/* 2344:2725 */       inventarioProducto.setLote(detalleMovimientoInventario.getLote());
/* 2345:2726 */       detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 2346:2727 */       inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 2347:     */       
/* 2348:     */ 
/* 2349:2730 */       BigDecimal cantidad = detalleMovimientoInventario.getCantidad();
/* 2350:2731 */       detalleMovimientoInventario.setCantidad(detalleMovimientoInventario.getCantidadOrigen());
/* 2351:2732 */       actualizarInventarioProductoDetalle(detalleMovimientoInventario);
/* 2352:2733 */       detalleMovimientoInventario.setCantidad(cantidad);
/* 2353:     */       
/* 2354:     */ 
/* 2355:     */ 
/* 2356:     */ 
/* 2357:2738 */       this.servicioVerificadorInventario.actualizarSaldoDetalle(detalleMovimientoInventario, false, movimientoInventario);
/* 2358:     */       
/* 2359:2740 */       detalleMovimientoInventario.getInventarioProducto().setNumeroDocumento(movimientoInventario.getNumero());
/* 2360:     */       
/* 2361:     */ 
/* 2362:2743 */       this.servicioInventarioProducto.guardar(detalleMovimientoInventario.getInventarioProducto());
/* 2363:2744 */       this.detalleMovimientoInventarioDao.guardar(detalleMovimientoInventario);
/* 2364:2747 */       if (detalleMovimientoInventario.getProducto().isIndicadorManejaUnidadInformativa()) {
/* 2365:2748 */         generarTransformacionUnidadInformativa(detalleMovimientoInventario, null);
/* 2366:     */       }
/* 2367:     */     }
/* 2368:     */     catch (ExcepcionAS2Inventario e)
/* 2369:     */     {
/* 2370:2762 */       this.context.setRollbackOnly();
/* 2371:2763 */       throw e;
/* 2372:     */     }
/* 2373:     */     catch (ExcepcionAS2 e)
/* 2374:     */     {
/* 2375:2765 */       this.context.setRollbackOnly();
/* 2376:2766 */       throw e;
/* 2377:     */     }
/* 2378:     */     catch (AS2Exception e)
/* 2379:     */     {
/* 2380:2768 */       this.context.setRollbackOnly();
/* 2381:2769 */       throw e;
/* 2382:     */     }
/* 2383:     */     catch (Exception e)
/* 2384:     */     {
/* 2385:2771 */       this.context.setRollbackOnly();
/* 2386:2772 */       throw new ExcepcionAS2(e);
/* 2387:     */     }
/* 2388:     */   }
/* 2389:     */   
/* 2390:     */   public void contabilizarIngresoFabricacion(MovimientoInventario movimientoInventario)
/* 2391:     */     throws AS2Exception, ExcepcionAS2Financiero, ExcepcionAS2
/* 2392:     */   {
/* 2393:2794 */     contabilizar(movimientoInventario);
/* 2394:     */   }
/* 2395:     */   
/* 2396:     */   public boolean existenDiferenciasRecepcionTransferencia(MovimientoInventario transferencia, boolean indicadorAdministrador)
/* 2397:     */     throws AS2Exception
/* 2398:     */   {
/* 2399:2799 */     boolean existeDiferencia = false;
/* 2400:2800 */     AS2Exception e = null;
/* 2401:2801 */     for (DetalleMovimientoInventario dmi : transferencia.getDetalleMovimientosInventario())
/* 2402:     */     {
/* 2403:2802 */       int porcientoTolerancia = dmi.getProducto().getPorcientoToleranciaTransferencia();
/* 2404:2803 */       BigDecimal diferenciaTransferencia = dmi.getCantidadPesada().subtract(dmi.getCantidad()).abs();
/* 2405:2804 */       BigDecimal porcientoDiferencia = diferenciaTransferencia.multiply(new BigDecimal(100)).divide(dmi.getCantidad(), 2, RoundingMode.HALF_UP);
/* 2406:2805 */       if ((porcientoDiferencia.compareTo(new BigDecimal(porcientoTolerancia)) > 0) && (!indicadorAdministrador)) {
/* 2407:2806 */         e = AS2Exception.agregarMensaje(e, "com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_ERROR_DIFERENCIA_SUPERA_TOLERANCIA_TRANSFERENCIA", new String[] { porcientoTolerancia + "%", dmi
/* 2408:2807 */           .getProducto().getNombre() });
/* 2409:2810 */       } else if (diferenciaTransferencia.compareTo(BigDecimal.ZERO) != 0) {
/* 2410:2811 */         existeDiferencia = true;
/* 2411:     */       }
/* 2412:     */     }
/* 2413:2814 */     if (e != null) {
/* 2414:2815 */       throw e;
/* 2415:     */     }
/* 2416:2817 */     return existeDiferencia;
/* 2417:     */   }
/* 2418:     */   
/* 2419:     */   public Map<Integer, MovimientoInventario> crearAjustesInventarioDiferenciasRecepcionTransferencia(MovimientoInventario transferencia)
/* 2420:     */   {
/* 2421:2822 */     Map<Integer, MovimientoInventario> mapaAjustesInventario = new HashMap();
/* 2422:2823 */     for (DetalleMovimientoInventario dmi : transferencia.getDetalleMovimientosInventario())
/* 2423:     */     {
/* 2424:2824 */       BigDecimal diferenciaTransferencia = dmi.getCantidadPesada().subtract(dmi.getCantidad());
/* 2425:2825 */       if (diferenciaTransferencia.compareTo(BigDecimal.ZERO) != 0)
/* 2426:     */       {
/* 2427:2826 */         int operacion = 0;
/* 2428:2827 */         if (diferenciaTransferencia.compareTo(BigDecimal.ZERO) > 0) {
/* 2429:2828 */           operacion = 1;
/* 2430:2829 */         } else if (diferenciaTransferencia.compareTo(BigDecimal.ZERO) < 0) {
/* 2431:2830 */           operacion = -1;
/* 2432:     */         }
/* 2433:2832 */         MovimientoInventario ajusteInventario = (MovimientoInventario)mapaAjustesInventario.get(Integer.valueOf(operacion));
/* 2434:2833 */         if (ajusteInventario == null)
/* 2435:     */         {
/* 2436:2834 */           ajusteInventario = new MovimientoInventario();
/* 2437:2835 */           ajusteInventario.setIdOrganizacion(transferencia.getIdOrganizacion());
/* 2438:2836 */           ajusteInventario.setNumero("");
/* 2439:2837 */           ajusteInventario.setFecha(transferencia.getFecha());
/* 2440:2838 */           ajusteInventario.setEstado(Estado.PROCESADO);
/* 2441:2839 */           ajusteInventario.setSucursal(transferencia.getSucursal());
/* 2442:     */         }
/* 2443:2841 */         DetalleMovimientoInventario detalle = new DetalleMovimientoInventario();
/* 2444:2842 */         detalle.setIdOrganizacion(transferencia.getIdOrganizacion());
/* 2445:2843 */         detalle.setIdSucursal(transferencia.getSucursal().getId());
/* 2446:2844 */         detalle.setBodegaOrigen(dmi.getBodegaDestino());
/* 2447:2845 */         if (operacion == 1) {
/* 2448:2846 */           detalle.setBodegaDestino(dmi.getBodegaDestino());
/* 2449:     */         }
/* 2450:2848 */         detalle.setCantidad(diferenciaTransferencia.abs());
/* 2451:2849 */         detalle.setCantidadPesada(diferenciaTransferencia.abs());
/* 2452:2850 */         detalle.setCantidadOrigen(diferenciaTransferencia.abs());
/* 2453:2851 */         detalle.setMovimientoInventario(ajusteInventario);
/* 2454:2852 */         detalle.setProducto(dmi.getProducto());
/* 2455:2853 */         detalle.setUnidadConversion(dmi.getUnidadConversion());
/* 2456:2854 */         ajusteInventario.getDetalleMovimientosInventario().add(detalle);
/* 2457:     */         
/* 2458:2856 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 2459:2857 */         inventarioProducto.setDetalleMovimientoInventario(detalle);
/* 2460:2858 */         inventarioProducto.setProducto(detalle.getProducto());
/* 2461:2859 */         inventarioProducto.setOperacion(operacion);
/* 2462:2860 */         inventarioProducto.setBodega(dmi.getBodegaDestino());
/* 2463:2861 */         inventarioProducto.setLote(dmi.getInventarioProducto().getLote());
/* 2464:2862 */         detalle.setInventarioProducto(inventarioProducto);
/* 2465:     */         
/* 2466:2864 */         mapaAjustesInventario.put(Integer.valueOf(operacion), ajusteInventario);
/* 2467:     */       }
/* 2468:     */     }
/* 2469:2867 */     return mapaAjustesInventario;
/* 2470:     */   }
/* 2471:     */   
/* 2472:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2473:     */   public void guardarRecepcionTransferenciaConAjusteInventario(List<MovimientoInventario> listaAjustesInventario, MovimientoInventario transferencia, RegistroPeso registroPeso)
/* 2474:     */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception, ExcepcionAS2Identification
/* 2475:     */   {
/* 2476:     */     try
/* 2477:     */     {
/* 2478:2877 */       guardarRecepcionTransferenciaConAjusteInventario(listaAjustesInventario, transferencia);
/* 2479:2878 */       this.servicioRegistroPeso.guardar(registroPeso);
/* 2480:     */     }
/* 2481:     */     catch (ExcepcionAS2Inventario e)
/* 2482:     */     {
/* 2483:2880 */       this.context.setRollbackOnly();
/* 2484:2881 */       throw e;
/* 2485:     */     }
/* 2486:     */     catch (ExcepcionAS2 e)
/* 2487:     */     {
/* 2488:2883 */       this.context.setRollbackOnly();
/* 2489:2884 */       throw e;
/* 2490:     */     }
/* 2491:     */     catch (AS2Exception e)
/* 2492:     */     {
/* 2493:2886 */       this.context.setRollbackOnly();
/* 2494:2887 */       throw e;
/* 2495:     */     }
/* 2496:     */     catch (ExcepcionAS2Identification e)
/* 2497:     */     {
/* 2498:2889 */       this.context.setRollbackOnly();
/* 2499:2890 */       throw e;
/* 2500:     */     }
/* 2501:     */     catch (Exception e)
/* 2502:     */     {
/* 2503:2892 */       this.context.setRollbackOnly();
/* 2504:2893 */       throw new ExcepcionAS2(e);
/* 2505:     */     }
/* 2506:     */   }
/* 2507:     */   
/* 2508:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2509:     */   public void guardarRecepcionTransferenciaConAjusteInventario(List<MovimientoInventario> listaAjustesInventario, MovimientoInventario transferencia)
/* 2510:     */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 2511:     */   {
/* 2512:     */     try
/* 2513:     */     {
/* 2514:2902 */       guardaTransferenciaBodegaIngreso(transferencia, false);
/* 2515:2903 */       for (MovimientoInventario ajuste : listaAjustesInventario)
/* 2516:     */       {
/* 2517:2904 */         ajuste.setTransferenciaAjuste(transferencia);
/* 2518:2905 */         guardar(ajuste, false, true);
/* 2519:     */       }
/* 2520:     */     }
/* 2521:     */     catch (ExcepcionAS2Inventario e)
/* 2522:     */     {
/* 2523:2908 */       this.context.setRollbackOnly();
/* 2524:2909 */       throw e;
/* 2525:     */     }
/* 2526:     */     catch (ExcepcionAS2 e)
/* 2527:     */     {
/* 2528:2911 */       this.context.setRollbackOnly();
/* 2529:2912 */       throw e;
/* 2530:     */     }
/* 2531:     */     catch (AS2Exception e)
/* 2532:     */     {
/* 2533:2914 */       this.context.setRollbackOnly();
/* 2534:2915 */       throw e;
/* 2535:     */     }
/* 2536:     */     catch (Exception e)
/* 2537:     */     {
/* 2538:2917 */       this.context.setRollbackOnly();
/* 2539:2918 */       throw new ExcepcionAS2(e);
/* 2540:     */     }
/* 2541:     */   }
/* 2542:     */   
/* 2543:     */   public void cargarSaldosBodegaOrigenTransferencia(MovimientoInventario transferencia)
/* 2544:     */     throws AS2Exception, ExcepcionAS2
/* 2545:     */   {
/* 2546:2924 */     Bodega bodegaOrigen = transferencia.getBodegaOrigen();
/* 2547:2925 */     Date fecha = transferencia.getFecha();
/* 2548:     */     Map<String, DetalleMovimientoInventario> mapaProductoDetalle;
/* 2549:     */     String key;
/* 2550:2926 */     if ((bodegaOrigen != null) && (fecha != null))
/* 2551:     */     {
/* 2552:2927 */       mapaProductoDetalle = new HashMap();
/* 2553:2928 */       for (DetalleMovimientoInventario dmi : transferencia.getDetalleMovimientosInventario())
/* 2554:     */       {
/* 2555:2929 */         dmi.setEliminado(true);
/* 2556:2930 */         if (dmi.getProducto() != null)
/* 2557:     */         {
/* 2558:2931 */           String idLote = "-";
/* 2559:2932 */           if ((dmi.getProducto().isIndicadorLote()) && (dmi.getInventarioProducto() != null) && (dmi.getInventarioProducto().getLote() != null)) {
/* 2560:2933 */             idLote = idLote + dmi.getInventarioProducto().getLote().getId();
/* 2561:     */           }
/* 2562:2935 */           key = dmi.getProducto().getId() + "";
/* 2563:2936 */           mapaProductoDetalle.put(key, dmi);
/* 2564:     */         }
/* 2565:     */       }
/* 2566:2939 */       Object listaSaldoProducto = this.servicioProducto.obtenerProductosConSaldoBodega(bodegaOrigen, fecha, false);
/* 2567:2940 */       List<SaldoProductoLote> listaSaldoProductoLote = this.servicioProducto.obtenerProductosConSaldoBodegaLote(bodegaOrigen, fecha, null, false);
/* 2568:2941 */       List<Object[]> listaSaldos = new ArrayList();
/* 2569:2942 */       for (SaldoProducto saldoProducto : (List)listaSaldoProducto)
/* 2570:     */       {
/* 2571:2943 */         Object[] object = new Object[3];
/* 2572:2944 */         object[0] = saldoProducto.getProducto();
/* 2573:2945 */         object[1] = saldoProducto.getSaldo();
/* 2574:2946 */         listaSaldos.add(object);
/* 2575:     */       }
/* 2576:2948 */       for (SaldoProductoLote saldoProductoLote : listaSaldoProductoLote)
/* 2577:     */       {
/* 2578:2949 */         Object[] object = new Object[3];
/* 2579:2950 */         object[0] = saldoProductoLote.getProducto();
/* 2580:2951 */         object[1] = saldoProductoLote.getSaldo();
/* 2581:2952 */         object[2] = saldoProductoLote.getLote();
/* 2582:2953 */         listaSaldos.add(object);
/* 2583:     */       }
/* 2584:2955 */       for (Object[] objects : listaSaldos)
/* 2585:     */       {
/* 2586:2956 */         Producto producto = (Producto)objects[0];
/* 2587:2957 */         BigDecimal saldo = (BigDecimal)objects[1];
/* 2588:2958 */         if (saldo.setScale(2, RoundingMode.DOWN).compareTo(BigDecimal.ZERO) > 0)
/* 2589:     */         {
/* 2590:2959 */           Lote lote = null;
/* 2591:2960 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 2592:2961 */           String idLote = "-";
/* 2593:2962 */           if (producto.isIndicadorLote())
/* 2594:     */           {
/* 2595:2963 */             lote = (Lote)objects[2];
/* 2596:2964 */             idLote = idLote + lote.getId();
/* 2597:     */           }
/* 2598:2966 */           String key = producto.getId() + "";
/* 2599:2967 */           DetalleMovimientoInventario dmi = (DetalleMovimientoInventario)mapaProductoDetalle.get(key);
/* 2600:2968 */           if (dmi == null)
/* 2601:     */           {
/* 2602:2969 */             dmi = new DetalleMovimientoInventario();
/* 2603:2970 */             dmi.setIdOrganizacion(transferencia.getIdOrganizacion());
/* 2604:2971 */             dmi.setIdSucursal(transferencia.getSucursal().getId());
/* 2605:2972 */             dmi.setProducto(producto);
/* 2606:2973 */             dmi.setBodegaOrigen(transferencia.getBodegaOrigen());
/* 2607:2974 */             dmi.setBodegaDestino(transferencia.getBodegaDestino());
/* 2608:2975 */             dmi.setMovimientoInventario(transferencia);
/* 2609:     */             
/* 2610:2977 */             dmi.setUnidadConversion(producto.getUnidad());
/* 2611:2978 */             InventarioProducto inventarioProducto = new InventarioProducto();
/* 2612:2979 */             inventarioProducto.setOperacion(transferencia.getDocumento().getOperacion());
/* 2613:2980 */             inventarioProducto.setDetalleMovimientoInventario(dmi);
/* 2614:2981 */             inventarioProducto.setProducto(producto);
/* 2615:2982 */             inventarioProducto.setLote(lote);
/* 2616:2983 */             dmi.setInventarioProducto(inventarioProducto);
/* 2617:     */             
/* 2618:2985 */             transferencia.getDetalleMovimientosInventario().add(dmi);
/* 2619:     */           }
/* 2620:2987 */           dmi.setCantidad(saldo.setScale(4, RoundingMode.DOWN));
/* 2621:2988 */           dmi.setSaldo(saldo);
/* 2622:2989 */           dmi.setEliminado(false);
/* 2623:     */         }
/* 2624:     */       }
/* 2625:     */     }
/* 2626:     */   }
/* 2627:     */   
/* 2628:     */   public MovimientoInventario cargarDetallesDiariosIngresoFabricacion(MovimientoInventario ingresoFabricacion, CategoriaProducto categoriaProducto, int numeroAtributos)
/* 2629:     */   {
/* 2630:2998 */     return this.movimientoInventarioDao.cargarDetallesDiariosIngresoFabricacion(ingresoFabricacion, categoriaProducto, numeroAtributos);
/* 2631:     */   }
/* 2632:     */   
/* 2633:     */   public List<MovimientoInventario> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 2634:     */   {
/* 2635:3003 */     return this.movimientoInventarioDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 2636:     */   }
/* 2637:     */   
/* 2638:     */   public void cargarDetallesConsumoBodegaADevolver(MovimientoInventario devolucionConsumoBodega)
/* 2639:     */     throws ExcepcionAS2
/* 2640:     */   {
/* 2641:3008 */     for (Iterator localIterator = devolucionConsumoBodega.getDetalleMovimientosInventario().iterator(); localIterator.hasNext();)
/* 2642:     */     {
/* 2643:3008 */       detalle = (DetalleMovimientoInventario)localIterator.next();
/* 2644:3009 */       detalle.setEliminado(true);
/* 2645:     */     }
/* 2646:     */     DetalleMovimientoInventario detalle;
/* 2647:3011 */     MovimientoInventario consumoBodega = devolucionConsumoBodega.getMovimientoInventarioPadre();
/* 2648:3012 */     if (consumoBodega != null)
/* 2649:     */     {
/* 2650:3013 */       consumoBodega = cargarDetalle(Integer.valueOf(consumoBodega.getId()));
/* 2651:     */       
/* 2652:3015 */       devolucionConsumoBodega.setResponsableSalidaMercaderia(consumoBodega.getResponsableSalidaMercaderia());
/* 2653:3016 */       devolucionConsumoBodega.setOrdenTrabajoMantenimiento(consumoBodega.getOrdenTrabajoMantenimiento());
/* 2654:3017 */       for (DetalleMovimientoInventario detalle : consumoBodega.getDetalleMovimientosInventario()) {
/* 2655:3018 */         if (detalle.getCantidadPorDevolver().compareTo(BigDecimal.ZERO) > 0)
/* 2656:     */         {
/* 2657:3019 */           DetalleMovimientoInventario detalleDevolucion = new DetalleMovimientoInventario();
/* 2658:3020 */           detalleDevolucion.setIdOrganizacion(devolucionConsumoBodega.getIdOrganizacion());
/* 2659:3021 */           detalleDevolucion.setIdSucursal(devolucionConsumoBodega.getSucursal().getId());
/* 2660:3022 */           detalleDevolucion.setMovimientoInventario(devolucionConsumoBodega);
/* 2661:3023 */           detalleDevolucion.setMaterialOrdenTrabajoMantenimiento(detalle.getMaterialOrdenTrabajoMantenimiento());
/* 2662:     */           
/* 2663:3025 */           InventarioProducto inventarioProducto = new InventarioProducto();
/* 2664:3026 */           inventarioProducto.setOperacion(consumoBodega.getDocumento().getOperacion());
/* 2665:3027 */           inventarioProducto.setProducto(detalleDevolucion.getProducto());
/* 2666:3028 */           inventarioProducto.setDetalleMovimientoInventario(detalleDevolucion);
/* 2667:3029 */           inventarioProducto.setLote(detalleDevolucion.getLote());
/* 2668:3030 */           detalleDevolucion.setInventarioProducto(inventarioProducto);
/* 2669:3031 */           devolucionConsumoBodega.getDetalleMovimientosInventario().add(detalleDevolucion);
/* 2670:     */           
/* 2671:3033 */           detalleDevolucion.setProducto(detalle.getProducto());
/* 2672:3034 */           detalleDevolucion.setCantidad(detalle.getCantidadPorDevolver());
/* 2673:3035 */           detalleDevolucion.setCantidadOrigen(detalle.getCantidadPorDevolver());
/* 2674:3036 */           detalleDevolucion.setTraCantidadCoversion(detalle.getCantidadPorDevolver());
/* 2675:3037 */           detalleDevolucion.setUnidadConversion(detalle.getUnidadConversion());
/* 2676:3038 */           this.servicioUnidadConversion.cargarListaUnidadConversion(detalle.getProducto());
/* 2677:3039 */           detalleDevolucion.setBodegaOrigen(detalle.getBodegaOrigen());
/* 2678:3040 */           detalleDevolucion.setBodegaDestino(detalle.getBodegaOrigen());
/* 2679:3041 */           detalleDevolucion.setLote(detalle.getLote());
/* 2680:3042 */           detalleDevolucion.setDestinoCosto(detalle.getDestinoCosto());
/* 2681:3043 */           detalleDevolucion.setDetalleMovimientoInventarioPadre(detalle);
/* 2682:3044 */           BigDecimal saldoBodega = this.servicioProducto.getSaldo(detalle.getProducto().getId(), detalle
/* 2683:3045 */             .getBodegaOrigen() == null ? 0 : detalle.getBodegaOrigen().getId(), devolucionConsumoBodega.getFecha());
/* 2684:3046 */           detalleDevolucion.setSaldo(saldoBodega);
/* 2685:     */         }
/* 2686:     */       }
/* 2687:     */     }
/* 2688:     */   }
/* 2689:     */   
/* 2690:     */   public void generarTransformacionUnidadInformativa(DetalleMovimientoInventario detalleMovimientoInventario, DetalleRecepcionProveedor detalleRecepcionProveedor)
/* 2691:     */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 2692:     */   {
/* 2693:3055 */     generarTransformacionUnidadInformativa(detalleMovimientoInventario, detalleRecepcionProveedor, null);
/* 2694:     */   }
/* 2695:     */   
/* 2696:     */   public void generarTransformacionUnidadInformativa(DetalleMovimientoInventario detalleMovimientoInventario, DetalleRecepcionProveedor detalleRecepcionProveedor, DetalleFacturaProveedor detalleFacturaProveedor)
/* 2697:     */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/* 2698:     */   {
/* 2699:3062 */     Producto producto = null;
/* 2700:3063 */     Producto productoTerminado = null;
/* 2701:3064 */     Integer idOrganizacion = null;
/* 2702:3065 */     Integer idSucursal = null;
/* 2703:3066 */     Bodega bodegaOrigen = null;
/* 2704:3067 */     Bodega bodegaDestino = null;
/* 2705:3068 */     BigDecimal cantidad = null;
/* 2706:3069 */     BigDecimal cantidadOrigen = null;
/* 2707:3070 */     BigDecimal cantidadInformativa = null;
/* 2708:3071 */     Unidad unidadConversion = null;
/* 2709:3072 */     Date fecha = null;
/* 2710:3073 */     Lote lote = null;
/* 2711:3074 */     Lote lotePT = null;
/* 2712:3076 */     if (detalleMovimientoInventario != null)
/* 2713:     */     {
/* 2714:3077 */       producto = detalleMovimientoInventario.getProducto();
/* 2715:3078 */       productoTerminado = this.productoDao.getProductoTransformacionAutomatica(producto);
/* 2716:3080 */       if (productoTerminado == null) {
/* 2717:3081 */         return;
/* 2718:     */       }
/* 2719:3083 */       idOrganizacion = Integer.valueOf(detalleMovimientoInventario.getIdOrganizacion());
/* 2720:3084 */       idSucursal = Integer.valueOf(detalleMovimientoInventario.getIdSucursal());
/* 2721:3085 */       bodegaOrigen = detalleMovimientoInventario.getBodegaOrigen();
/* 2722:3086 */       bodegaDestino = detalleMovimientoInventario.getBodegaOrigen();
/* 2723:3087 */       cantidad = detalleMovimientoInventario.getCantidad();
/* 2724:3088 */       cantidadOrigen = detalleMovimientoInventario.getCantidadOrigen();
/* 2725:3089 */       unidadConversion = detalleMovimientoInventario.getUnidadConversion();
/* 2726:3090 */       fecha = detalleMovimientoInventario.getMovimientoInventario().getFecha();
/* 2727:3091 */       cantidadInformativa = detalleMovimientoInventario.getCantidadUnidadInformativa();
/* 2728:3094 */       if (productoTerminado.isIndicadorLote())
/* 2729:     */       {
/* 2730:3095 */         String codigoLote = "";
/* 2731:3096 */         if (detalleMovimientoInventario.getLote() != null) {
/* 2732:3097 */           codigoLote = detalleMovimientoInventario.getLote().getCodigo();
/* 2733:3098 */         } else if (detalleMovimientoInventario.getMovimientoInventario().getOrdenFabricacion() != null) {
/* 2734:3099 */           codigoLote = detalleMovimientoInventario.getMovimientoInventario().getOrdenFabricacion().getNumero();
/* 2735:     */         }
/* 2736:     */         try
/* 2737:     */         {
/* 2738:3102 */           lotePT = this.servicioLote.buscarPorCodigo(codigoLote, productoTerminado);
/* 2739:     */         }
/* 2740:     */         catch (ExcepcionAS2 e)
/* 2741:     */         {
/* 2742:3105 */           lotePT = this.servicioLote.crearLoteInterno(idOrganizacion.intValue(), productoTerminado, codigoLote, true);
/* 2743:     */         }
/* 2744:     */       }
/* 2745:3109 */       if (producto.isIndicadorLote())
/* 2746:     */       {
/* 2747:3110 */         lote = detalleMovimientoInventario.getLote();
/* 2748:3111 */         if (lote == null)
/* 2749:     */         {
/* 2750:3112 */           String codigoLote = detalleMovimientoInventario.getMovimientoInventario().getOrdenFabricacion().getNumero();
/* 2751:     */           try
/* 2752:     */           {
/* 2753:3114 */             lote = this.servicioLote.buscarPorCodigo(codigoLote, producto);
/* 2754:     */           }
/* 2755:     */           catch (ExcepcionAS2 e)
/* 2756:     */           {
/* 2757:3117 */             lote = this.servicioLote.crearLoteInterno(idOrganizacion.intValue(), producto, codigoLote, true);
/* 2758:     */           }
/* 2759:     */         }
/* 2760:     */       }
/* 2761:     */     }
/* 2762:3122 */     if (detalleRecepcionProveedor != null)
/* 2763:     */     {
/* 2764:3123 */       producto = detalleRecepcionProveedor.getProducto();
/* 2765:3124 */       productoTerminado = this.productoDao.getProductoTransformacionAutomatica(producto);
/* 2766:3126 */       if (productoTerminado == null) {
/* 2767:3127 */         return;
/* 2768:     */       }
/* 2769:3129 */       idOrganizacion = Integer.valueOf(detalleRecepcionProveedor.getIdOrganizacion());
/* 2770:3130 */       idSucursal = Integer.valueOf(detalleRecepcionProveedor.getIdSucursal());
/* 2771:3131 */       bodegaOrigen = detalleRecepcionProveedor.getBodega();
/* 2772:3132 */       bodegaDestino = detalleRecepcionProveedor.getBodega();
/* 2773:     */       
/* 2774:3134 */       cantidad = this.servicioProducto.convierteUnidad(detalleRecepcionProveedor.getUnidadCompra(), producto.getUnidad(), producto.getIdProducto(), detalleRecepcionProveedor.getCantidad()).setScale(producto.getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 2775:3135 */       cantidadOrigen = detalleRecepcionProveedor.getCantidad();
/* 2776:3136 */       unidadConversion = detalleRecepcionProveedor.getUnidadCompra();
/* 2777:3137 */       fecha = detalleRecepcionProveedor.getRecepcionProveedor().getFecha();
/* 2778:3138 */       cantidadInformativa = detalleRecepcionProveedor.getCantidadUnidadInformativa();
/* 2779:     */       
/* 2780:3140 */       lote = detalleRecepcionProveedor.getLote();
/* 2781:3141 */       if ((lote == null) && (detalleRecepcionProveedor.getInventarioProducto() != null)) {
/* 2782:3142 */         lote = detalleRecepcionProveedor.getInventarioProducto().getLote();
/* 2783:     */       }
/* 2784:3144 */       if (lote != null)
/* 2785:     */       {
/* 2786:3146 */         String codigoLote = lote.getCodigo();
/* 2787:     */         try
/* 2788:     */         {
/* 2789:3148 */           lote = this.servicioLote.buscarPorCodigoProductoProveedor(codigoLote, producto, detalleRecepcionProveedor
/* 2790:3149 */             .getRecepcionProveedor().getEmpresa());
/* 2791:     */         }
/* 2792:     */         catch (ExcepcionAS2 e)
/* 2793:     */         {
/* 2794:3152 */           lote = this.servicioLote.crearLote(idOrganizacion.intValue(), producto, codigoLote, false, detalleRecepcionProveedor
/* 2795:3153 */             .getRecepcionProveedor().getEmpresa(), fecha, fecha, true);
/* 2796:     */         }
/* 2797:     */       }
/* 2798:3157 */       if (productoTerminado.isIndicadorLote())
/* 2799:     */       {
/* 2800:3158 */         String codigoLote = "";
/* 2801:3159 */         if (lote != null) {
/* 2802:3160 */           codigoLote = lote.getCodigo();
/* 2803:     */         } else {
/* 2804:3162 */           codigoLote = detalleRecepcionProveedor.getRecepcionProveedor().getNumero();
/* 2805:     */         }
/* 2806:     */         try
/* 2807:     */         {
/* 2808:3165 */           lotePT = this.servicioLote.buscarPorCodigoProductoProveedor(codigoLote, productoTerminado, detalleRecepcionProveedor
/* 2809:3166 */             .getRecepcionProveedor().getEmpresa());
/* 2810:     */         }
/* 2811:     */         catch (ExcepcionAS2 e)
/* 2812:     */         {
/* 2813:3169 */           lotePT = this.servicioLote.crearLote(idOrganizacion.intValue(), productoTerminado, codigoLote, false, detalleRecepcionProveedor
/* 2814:3170 */             .getRecepcionProveedor().getEmpresa(), fecha, fecha, true);
/* 2815:     */         }
/* 2816:     */       }
/* 2817:     */     }
/* 2818:3175 */     if (detalleFacturaProveedor != null)
/* 2819:     */     {
/* 2820:3176 */       producto = detalleFacturaProveedor.getProducto();
/* 2821:3177 */       productoTerminado = this.productoDao.getProductoTransformacionAutomatica(producto);
/* 2822:3179 */       if (productoTerminado == null) {
/* 2823:3180 */         return;
/* 2824:     */       }
/* 2825:3182 */       idOrganizacion = Integer.valueOf(detalleFacturaProveedor.getIdOrganizacion());
/* 2826:3183 */       idSucursal = Integer.valueOf(detalleFacturaProveedor.getIdSucursal());
/* 2827:3184 */       bodegaOrigen = detalleFacturaProveedor.getBodega();
/* 2828:3185 */       bodegaDestino = detalleFacturaProveedor.getBodega();
/* 2829:     */       
/* 2830:3187 */       cantidad = this.servicioProducto.convierteUnidad(detalleFacturaProveedor.getUnidadCompra(), producto.getUnidad(), producto.getIdProducto(), detalleFacturaProveedor.getCantidad()).setScale(producto.getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 2831:3188 */       cantidadOrigen = detalleFacturaProveedor.getCantidad();
/* 2832:3189 */       unidadConversion = detalleFacturaProveedor.getUnidadCompra();
/* 2833:3190 */       fecha = detalleFacturaProveedor.getFacturaProveedor().getFecha();
/* 2834:3191 */       cantidadInformativa = detalleFacturaProveedor.getCantidadUnidadInformativa();
/* 2835:3194 */       if ((lote == null) && (detalleFacturaProveedor.getInventarioProducto() != null)) {
/* 2836:3195 */         lote = detalleFacturaProveedor.getInventarioProducto().getLote();
/* 2837:     */       }
/* 2838:3197 */       if (lote != null)
/* 2839:     */       {
/* 2840:3199 */         String codigoLote = lote.getCodigo();
/* 2841:     */         try
/* 2842:     */         {
/* 2843:3201 */           lote = this.servicioLote.buscarPorCodigoProductoProveedor(codigoLote, producto, detalleFacturaProveedor
/* 2844:3202 */             .getFacturaProveedor().getEmpresa());
/* 2845:     */         }
/* 2846:     */         catch (ExcepcionAS2 e)
/* 2847:     */         {
/* 2848:3205 */           lote = this.servicioLote.crearLote(idOrganizacion.intValue(), producto, codigoLote, false, detalleFacturaProveedor
/* 2849:3206 */             .getFacturaProveedor().getEmpresa(), fecha, fecha, true);
/* 2850:     */         }
/* 2851:     */       }
/* 2852:3210 */       if (productoTerminado.isIndicadorLote())
/* 2853:     */       {
/* 2854:3211 */         String codigoLote = detalleFacturaProveedor.getLote().getCodigo();
/* 2855:     */         try
/* 2856:     */         {
/* 2857:3218 */           lotePT = this.servicioLote.buscarPorCodigoProductoProveedor(codigoLote, productoTerminado, detalleFacturaProveedor
/* 2858:3219 */             .getFacturaProveedor().getEmpresa());
/* 2859:     */         }
/* 2860:     */         catch (ExcepcionAS2 e)
/* 2861:     */         {
/* 2862:3222 */           lotePT = this.servicioLote.crearLote(idOrganizacion.intValue(), productoTerminado, codigoLote, false, detalleFacturaProveedor
/* 2863:3223 */             .getFacturaProveedor().getEmpresa(), fecha, fecha, true);
/* 2864:     */         }
/* 2865:     */       }
/* 2866:     */     }
/* 2867:3228 */     if (producto.getUnidadInformativa() != null)
/* 2868:     */     {
/* 2869:3229 */       MovimientoInventario transformacionMateriales = new MovimientoInventario();
/* 2870:3230 */       transformacionMateriales.setIdOrganizacion(idOrganizacion.intValue());
/* 2871:3231 */       transformacionMateriales.setSucursal(this.servicioSucursal.buscarPorId(idSucursal));
/* 2872:3232 */       transformacionMateriales.setFecha(fecha);
/* 2873:3233 */       transformacionMateriales.setProductoTerminadoTransformacion(productoTerminado);
/* 2874:3234 */       transformacionMateriales.setBodegaOrigen(bodegaOrigen);
/* 2875:3235 */       transformacionMateriales.setBodegaDestino(bodegaDestino);
/* 2876:3237 */       if (transformacionMateriales.getProductoTerminadoTransformacion() == null) {
/* 2877:3239 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_PRODUCTO_TRANSFORMACION_AUTOMATICA", new String[] {producto.getCodigo() + " - " + producto.getNombre() });
/* 2878:     */       }
/* 2879:3242 */       transformacionMateriales.setLoteTransformacion(lotePT);
/* 2880:     */       
/* 2881:     */ 
/* 2882:3245 */       BigDecimal cantidadPT = cantidadInformativa;
/* 2883:3246 */       if (!transformacionMateriales.getProductoTerminadoTransformacion().getUnidad().equals(producto.getUnidadInformativa()))
/* 2884:     */       {
/* 2885:3249 */         BigDecimal valorConversion = this.servicioProducto.convierteUnidad(producto.getUnidadInformativa(), productoTerminado.getUnidad(), productoTerminado.getIdProducto(), cantidadInformativa).setScale(productoTerminado.getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 2886:3250 */         cantidadPT = valorConversion;
/* 2887:     */       }
/* 2888:3252 */       transformacionMateriales.setCantidadTransformacion(cantidadPT);
/* 2889:     */       
/* 2890:     */ 
/* 2891:3255 */       Map<String, String> filtros = new HashMap();
/* 2892:3256 */       filtros.put("idOrganizacion", transformacionMateriales.getIdOrganizacion() + "");
/* 2893:3257 */       if (detalleFacturaProveedor == null) {
/* 2894:3258 */         filtros.put("operacion", "=-1");
/* 2895:     */       } else {
/* 2896:3260 */         filtros.put("operacion", "1");
/* 2897:     */       }
/* 2898:3262 */       filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 2899:     */       
/* 2900:3264 */       List<Documento> listaDocumentoOrigen = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/* 2901:3265 */       if (listaDocumentoOrigen.size() > 0)
/* 2902:     */       {
/* 2903:3266 */         transformacionMateriales.setDocumento((Documento)listaDocumentoOrigen.get(0));
/* 2904:     */       }
/* 2905:     */       else
/* 2906:     */       {
/* 2907:3268 */         if (detalleFacturaProveedor == null) {
/* 2908:3269 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_EGRESO", new String[] { "" });
/* 2909:     */         }
/* 2910:3271 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_INGRESO", new String[] { "" });
/* 2911:     */       }
/* 2912:3276 */       filtros = new HashMap();
/* 2913:3277 */       filtros.put("idOrganizacion", transformacionMateriales.getIdOrganizacion() + "");
/* 2914:3278 */       if (detalleFacturaProveedor == null) {
/* 2915:3279 */         filtros.put("operacion", "=1");
/* 2916:     */       } else {
/* 2917:3281 */         filtros.put("operacion", "=-1");
/* 2918:     */       }
/* 2919:3283 */       filtros.put("documentoBase", DocumentoBase.TRANSFORMACION_PRODUCTO.toString());
/* 2920:     */       
/* 2921:3285 */       List<Documento> listaDocumentoDestino = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/* 2922:3286 */       if (listaDocumentoDestino.size() > 0)
/* 2923:     */       {
/* 2924:3287 */         transformacionMateriales.setDocumentoDestino((Documento)listaDocumentoDestino.get(0));
/* 2925:     */       }
/* 2926:     */       else
/* 2927:     */       {
/* 2928:3289 */         if (detalleFacturaProveedor == null) {
/* 2929:3290 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_INGRESO", new String[] { "" });
/* 2930:     */         }
/* 2931:3292 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_DOCUMENTO_TRANSFORMACION_EGRESO", new String[] { "" });
/* 2932:     */       }
/* 2933:3297 */       DetalleMovimientoInventario detalle = new DetalleMovimientoInventario();
/* 2934:3298 */       detalle.setIdOrganizacion(idOrganizacion.intValue());
/* 2935:3299 */       detalle.setIdSucursal(idSucursal.intValue());
/* 2936:3300 */       detalle.setMovimientoInventario(transformacionMateriales);
/* 2937:3301 */       detalle.setBodegaOrigen(bodegaOrigen);
/* 2938:3302 */       detalle.setCantidad(cantidad);
/* 2939:3303 */       detalle.setCantidadOrigen(cantidadOrigen);
/* 2940:3304 */       detalle.setLote(lote);
/* 2941:3305 */       detalle.setProducto(producto);
/* 2942:3306 */       detalle.setUnidadConversion(unidadConversion);
/* 2943:3307 */       transformacionMateriales.getDetalleMovimientosInventario().add(detalle);
/* 2944:     */       
/* 2945:3309 */       guardaTransformacionProducto(transformacionMateriales, false, null, null, null);
/* 2946:3311 */       if (detalleMovimientoInventario != null)
/* 2947:     */       {
/* 2948:3313 */         detalleMovimientoInventario.setTransformacionAutomatica(transformacionMateriales);
/* 2949:3314 */         this.detalleMovimientoInventarioDao.guardar(detalleMovimientoInventario);
/* 2950:     */       }
/* 2951:3315 */       else if (detalleRecepcionProveedor != null)
/* 2952:     */       {
/* 2953:3317 */         detalleRecepcionProveedor.setTransformacionAutomatica(transformacionMateriales);
/* 2954:3318 */         this.detalleRecepcionProveedorDao.guardar(detalleRecepcionProveedor);
/* 2955:     */       }
/* 2956:3319 */       else if (detalleFacturaProveedor != null)
/* 2957:     */       {
/* 2958:3321 */         detalleFacturaProveedor.setTransformacionAutomatica(transformacionMateriales);
/* 2959:3322 */         this.detalleFacturaProveedorDao.guardar(detalleFacturaProveedor);
/* 2960:     */       }
/* 2961:     */     }
/* 2962:     */   }
/* 2963:     */   
/* 2964:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2965:     */   public void eliminarTransformacionProductoMateriales(MovimientoInventario transformacionProducto)
/* 2966:     */     throws ExcepcionAS2
/* 2967:     */   {
/* 2968:     */     try
/* 2969:     */     {
/* 2970:3331 */       anular(transformacionProducto);
/* 2971:3332 */       transformacionProducto = cargarDetalle(Integer.valueOf(transformacionProducto.getId()));
/* 2972:3333 */       anular(transformacionProducto.getMovimientoInventarioPadre());
/* 2973:3334 */       MovimientoInventario transformacionPadre = cargarDetalle(Integer.valueOf(transformacionProducto.getMovimientoInventarioPadre().getId()));
/* 2974:     */       
/* 2975:3336 */       this.movimientoInventarioDao.eliminar(transformacionProducto);
/* 2976:3337 */       this.movimientoInventarioDao.eliminar(transformacionPadre);
/* 2977:     */     }
/* 2978:     */     catch (Exception e)
/* 2979:     */     {
/* 2980:3339 */       this.context.setRollbackOnly();
/* 2981:3340 */       throw new ExcepcionAS2(e);
/* 2982:     */     }
/* 2983:     */   }
/* 2984:     */   
/* 2985:     */   public List<MovimientoInventario> obtenerIngresoFabricacionDelMes(Date fecha, OrdenFabricacion ordenFabricacion)
/* 2986:     */   {
/* 2987:3346 */     return this.movimientoInventarioDao.obtenerIngresoFabricacionDelMes(fecha, ordenFabricacion);
/* 2988:     */   }
/* 2989:     */   
/* 2990:     */   public void contabilizarConsumoBodega(List<MovimientoInventario> listaMovimientoInventario)
/* 2991:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 2992:     */   {
/* 2993:3354 */     InterfazContableProceso interfazContableProceso = new InterfazContableProceso();
/* 2994:3355 */     interfazContableProceso.setListaMovimientoInventario(listaMovimientoInventario);
/* 2995:3356 */     interfazContableProceso.setDocumentoBase(DocumentoBase.INTERFAZ_CONSUMOS_BODEGA);
/* 2996:3357 */     interfazContableProceso.setIdOrganizacion(((MovimientoInventario)listaMovimientoInventario.get(0)).getIdOrganizacion());
/* 2997:3358 */     interfazContableProceso.setContabilizacionAutomatica(true);
/* 2998:3359 */     interfazContableProceso.setFechaHasta(((MovimientoInventario)listaMovimientoInventario.get(0)).getFecha());
/* 2999:3360 */     interfazContableProceso.setIndicadorAgrupadoPorCuenta(false);
/* 3000:3362 */     if (((MovimientoInventario)listaMovimientoInventario.get(0)).getAsiento() != null) {
/* 3001:3363 */       interfazContableProceso.setAsiento(((MovimientoInventario)listaMovimientoInventario.get(0)).getAsiento());
/* 3002:     */     }
/* 3003:3366 */     Asiento asiento = this.servicioInterfazContableProceso.generarAsiento(interfazContableProceso);
/* 3004:3367 */     asiento.setDocumentoOrigen(((MovimientoInventario)listaMovimientoInventario.get(0)).getDocumento());
/* 3005:     */     
/* 3006:     */ 
/* 3007:3370 */     this.servicioAsiento.guardar(asiento);
/* 3008:     */     
/* 3009:3372 */     ((MovimientoInventario)listaMovimientoInventario.get(0)).setEstado(Estado.CONTABILIZADO);
/* 3010:3373 */     ((MovimientoInventario)listaMovimientoInventario.get(0)).setFechaContabilizacion(((MovimientoInventario)listaMovimientoInventario.get(0)).getFecha());
/* 3011:3374 */     ((MovimientoInventario)listaMovimientoInventario.get(0)).setAsiento(asiento);
/* 3012:     */   }
/* 3013:     */   
/* 3014:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 3015:     */   public void anularTransformacionProducto(MovimientoInventario transferencia)
/* 3016:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Inventario
/* 3017:     */   {
/* 3018:3383 */     anular(transferencia);
/* 3019:3384 */     transferencia = cargarDetalle(Integer.valueOf(transferencia.getId()));
/* 3020:3385 */     anular(transferencia.getMovimientoInventarioPadre());
/* 3021:     */   }
/* 3022:     */   
/* 3023:     */   private void validarIngresoFabricacion(MovimientoInventario movimientoInventario, DetalleMovimientoInventario detalleMovimientoInventario, LecturaBalanza lecturaBalanza, TipoOrganizacion tipoOrganizacion)
/* 3024:     */     throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2, ExcepcionAS2Financiero
/* 3025:     */   {
/* 3026:3393 */     this.servicioPeriodo.buscarPorFecha(movimientoInventario.getFecha(), movimientoInventario.getIdOrganizacion(), movimientoInventario
/* 3027:3394 */       .getDocumento().getDocumentoBase());
/* 3028:3396 */     if (movimientoInventario.getOrdenFabricacion().getValorAtributoOrdenFabricacion() != null)
/* 3029:     */     {
/* 3030:3398 */       Lote lote = detalleMovimientoInventario.getLote();
/* 3031:     */       
/* 3032:3400 */       Producto producto = this.servicioProducto.cargarProductoConAtributoInstancia(detalleMovimientoInventario.getProducto().getId());
/* 3033:     */       
/* 3034:3402 */       Atributo atributo = producto.getAtributoProduccion1();
/* 3035:3403 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo1() == null))) {
/* 3036:3404 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3037:     */       }
/* 3038:3407 */       atributo = producto.getAtributoProduccion2();
/* 3039:3408 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo2() == null))) {
/* 3040:3409 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3041:     */       }
/* 3042:3412 */       atributo = producto.getAtributoProduccion3();
/* 3043:3413 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo3() == null))) {
/* 3044:3414 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3045:     */       }
/* 3046:3417 */       atributo = producto.getAtributoProduccion4();
/* 3047:3418 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo4() == null))) {
/* 3048:3419 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3049:     */       }
/* 3050:3422 */       atributo = producto.getAtributoProduccion5();
/* 3051:3423 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo5() == null))) {
/* 3052:3424 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3053:     */       }
/* 3054:3427 */       atributo = producto.getAtributoProduccion6();
/* 3055:3428 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo6() == null))) {
/* 3056:3429 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3057:     */       }
/* 3058:3432 */       atributo = producto.getAtributoProduccion7();
/* 3059:3433 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo7() == null))) {
/* 3060:3434 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3061:     */       }
/* 3062:3436 */       atributo = producto.getAtributoProduccion8();
/* 3063:3437 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo8() == null))) {
/* 3064:3438 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3065:     */       }
/* 3066:3441 */       atributo = producto.getAtributoProduccion9();
/* 3067:3442 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo9() == null))) {
/* 3068:3443 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3069:     */       }
/* 3070:3446 */       atributo = producto.getAtributoProduccion10();
/* 3071:3447 */       if ((atributo != null) && ((lote == null) || (lote.getAtributo10() == null))) {
/* 3072:3448 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_LOTE_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { "" });
/* 3073:     */       }
/* 3074:     */     }
/* 3075:3453 */     HashMap<String, String> filters = new HashMap();
/* 3076:3454 */     filters = new HashMap();
/* 3077:3455 */     filters.put("ordenFabricacionPadre.idOrdenFabricacion", "" + movimientoInventario.getOrdenFabricacion().getIdOrdenFabricacion());
/* 3078:3456 */     List<OrdenFabricacion> subordenes = this.servicioOrdenFabricacion.obtenerListaPorPagina(0, 1000, "numero", true, filters, true);
/* 3079:     */     BigDecimal cantidadIngresoFabricacion;
/* 3080:3458 */     if (!subordenes.isEmpty())
/* 3081:     */     {
/* 3082:3461 */       filters = new HashMap();
/* 3083:3462 */       filters.put("movimientoInventario.ordenFabricacion.idOrdenFabricacion", "" + movimientoInventario.getOrdenFabricacion().getId());
/* 3084:     */       
/* 3085:3464 */       List<DetalleMovimientoInventario> listaIngresoFabricacionPadre = this.detalleMovimientoInventarioDao.obtenerListaPorPagina(DetalleMovimientoInventario.class, 0, 1000, "idDetalleMovimientoInventario", true, filters);
/* 3086:     */       
/* 3087:     */ 
/* 3088:3467 */       cantidadIngresoFabricacion = detalleMovimientoInventario.getCantidad();
/* 3089:3468 */       for (DetalleMovimientoInventario dmi : listaIngresoFabricacionPadre) {
/* 3090:3469 */         cantidadIngresoFabricacion = cantidadIngresoFabricacion.add(dmi.getCantidad());
/* 3091:     */       }
/* 3092:3473 */       for (OrdenFabricacion ordenFabricacion : subordenes) {
/* 3093:3475 */         if (ordenFabricacion.isIndicadorValidaStockSuborden())
/* 3094:     */         {
/* 3095:3478 */           filters = new HashMap();
/* 3096:3479 */           filters.put("movimientoInventario.ordenFabricacion.idOrdenFabricacion", "" + ordenFabricacion.getId());
/* 3097:3480 */           filters.put("indicadorRecibido", "true");
/* 3098:3481 */           List<DetalleMovimientoInventario> lista = this.detalleMovimientoInventarioDao.obtenerListaCombo(DetalleMovimientoInventario.class, "idDetalleMovimientoInventario", true, filters);
/* 3099:     */           
/* 3100:3483 */           BigDecimal cantidadSuborden = BigDecimal.ZERO;
/* 3101:3484 */           for (DetalleMovimientoInventario dmi : lista) {
/* 3102:3485 */             cantidadSuborden = cantidadSuborden.add(dmi.getCantidad());
/* 3103:     */           }
/* 3104:3488 */           if (cantidadSuborden.compareTo(cantidadIngresoFabricacion) < 0) {
/* 3105:3491 */             throw new AS2Exception("com.asinfo.as2.inventario.procesos.servicio.impl.ServicioMovimientoInventarioImpl.MENSAJE_ERROR_CANTIDAD_MENOR_SUBORDEN", new String[] { ordenFabricacion.getNumero(), movimientoInventario.getOrdenFabricacion().getNumero(), cantidadSuborden.toString(), cantidadIngresoFabricacion.toString() });
/* 3106:     */           }
/* 3107:     */         }
/* 3108:     */       }
/* 3109:     */     }
/* 3110:     */   }
/* 3111:     */   
/* 3112:     */   public List<DetalleMovimientoInventario> autocompletarIngresosFabricacion(int idOrganizacion, String cadena, CategoriaProducto categoriaProducto)
/* 3113:     */   {
/* 3114:3501 */     return this.movimientoInventarioDao.autocompletarIngresosFabricacion(idOrganizacion, cadena, categoriaProducto);
/* 3115:     */   }
/* 3116:     */   
/* 3117:     */   public Map<Integer, MovimientoInventario> crearAjustesInventarioDiferenciasRecepcionTransferenciaRegistroPeso(MovimientoInventario transferencia)
/* 3118:     */   {
/* 3119:3506 */     Map<Integer, MovimientoInventario> mapaAjustesInventario = new HashMap();
/* 3120:3507 */     for (DetalleMovimientoInventario dmi : transferencia.getDetalleMovimientosInventario())
/* 3121:     */     {
/* 3122:3510 */       BigDecimal pesoProducto = dmi.getProducto().getPeso() == null ? BigDecimal.ONE : dmi.getProducto().getPeso();
/* 3123:     */       
/* 3124:     */ 
/* 3125:3513 */       BigDecimal diferenciaTransferencia = dmi.getCantidadPesada().subtract(dmi.getPesoProducto());
/* 3126:     */       
/* 3127:     */ 
/* 3128:3516 */       BigDecimal proporcionPeso = diferenciaTransferencia;
/* 3129:3517 */       if (pesoProducto.compareTo(BigDecimal.ZERO) > 0) {
/* 3130:3518 */         proporcionPeso = diferenciaTransferencia.divide(pesoProducto, dmi.getProducto().getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 3131:     */       }
/* 3132:3523 */       if (proporcionPeso.compareTo(BigDecimal.ZERO) != 0)
/* 3133:     */       {
/* 3134:3524 */         int operacion = 0;
/* 3135:3525 */         if (proporcionPeso.compareTo(BigDecimal.ZERO) > 0) {
/* 3136:3526 */           operacion = 1;
/* 3137:3527 */         } else if (proporcionPeso.compareTo(BigDecimal.ZERO) < 0) {
/* 3138:3528 */           operacion = -1;
/* 3139:     */         }
/* 3140:3530 */         MovimientoInventario ajusteInventario = (MovimientoInventario)mapaAjustesInventario.get(Integer.valueOf(operacion));
/* 3141:3531 */         if (ajusteInventario == null)
/* 3142:     */         {
/* 3143:3532 */           ajusteInventario = new MovimientoInventario();
/* 3144:3533 */           ajusteInventario.setIdOrganizacion(transferencia.getIdOrganizacion());
/* 3145:3534 */           ajusteInventario.setNumero("");
/* 3146:3535 */           ajusteInventario.setFecha(transferencia.getFecha());
/* 3147:3536 */           ajusteInventario.setEstado(Estado.PROCESADO);
/* 3148:3537 */           ajusteInventario.setSucursal(transferencia.getSucursal());
/* 3149:     */         }
/* 3150:3539 */         DetalleMovimientoInventario detalle = new DetalleMovimientoInventario();
/* 3151:3540 */         detalle.setIdOrganizacion(transferencia.getIdOrganizacion());
/* 3152:3541 */         detalle.setIdSucursal(transferencia.getSucursal().getId());
/* 3153:3542 */         detalle.setBodegaOrigen(dmi.getBodegaDestino());
/* 3154:3543 */         if (operacion == 1) {
/* 3155:3544 */           detalle.setBodegaDestino(dmi.getBodegaDestino());
/* 3156:     */         }
/* 3157:3546 */         detalle.setCantidad(proporcionPeso.abs());
/* 3158:3547 */         detalle.setCantidadPesada(proporcionPeso.abs());
/* 3159:3548 */         detalle.setCantidadOrigen(proporcionPeso.abs());
/* 3160:3549 */         detalle.setMovimientoInventario(ajusteInventario);
/* 3161:3550 */         detalle.setProducto(dmi.getProducto());
/* 3162:3551 */         detalle.setUnidadConversion(dmi.getUnidadConversion());
/* 3163:3552 */         ajusteInventario.getDetalleMovimientosInventario().add(detalle);
/* 3164:     */         
/* 3165:3554 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 3166:3555 */         inventarioProducto.setDetalleMovimientoInventario(detalle);
/* 3167:3556 */         inventarioProducto.setProducto(detalle.getProducto());
/* 3168:3557 */         inventarioProducto.setOperacion(operacion);
/* 3169:3558 */         inventarioProducto.setBodega(dmi.getBodegaDestino());
/* 3170:3559 */         inventarioProducto.setLote(dmi.getInventarioProducto().getLote());
/* 3171:3560 */         detalle.setInventarioProducto(inventarioProducto);
/* 3172:     */         
/* 3173:3562 */         mapaAjustesInventario.put(Integer.valueOf(operacion), ajusteInventario);
/* 3174:     */       }
/* 3175:     */     }
/* 3176:3565 */     return mapaAjustesInventario;
/* 3177:     */   }
/* 3178:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioMovimientoInventarioImpl
 * JD-Core Version:    0.7.0.1
 */