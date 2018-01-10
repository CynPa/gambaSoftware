/*    1:     */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    4:     */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*    5:     */ import com.asinfo.as2.compronteselectronicos.ServicioFacturaClienteSRIXML;
/*    6:     */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*    7:     */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*    8:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*    9:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   10:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   11:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   12:     */ import com.asinfo.as2.dao.CuentaPorCobrarDao;
/*   13:     */ import com.asinfo.as2.dao.DespachoClienteDao;
/*   14:     */ import com.asinfo.as2.dao.DetalleCobroDao;
/*   15:     */ import com.asinfo.as2.dao.DetalleCobroFormaCobroDao;
/*   16:     */ import com.asinfo.as2.dao.DetalleFacturaClienteComercializadoraDao;
/*   17:     */ import com.asinfo.as2.dao.DetalleFacturaClienteDao;
/*   18:     */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*   19:     */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   20:     */ import com.asinfo.as2.dao.GenericoDao;
/*   21:     */ import com.asinfo.as2.dao.HojaRutaDao;
/*   22:     */ import com.asinfo.as2.dao.ImpuestoProductoFacturaClienteDao;
/*   23:     */ import com.asinfo.as2.dao.PedidoClienteDao;
/*   24:     */ import com.asinfo.as2.dao.PrefacturaClienteDao;
/*   25:     */ import com.asinfo.as2.dao.RangoImpuestoDao;
/*   26:     */ import com.asinfo.as2.dao.sri.AnuladoSRIDao;
/*   27:     */ import com.asinfo.as2.dao.sri.ClaveAccesoPendientePublicarDao;
/*   28:     */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*   29:     */ import com.asinfo.as2.dao.sri.FacturaClienteSRIDao;
/*   30:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   31:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   32:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   33:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   34:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   35:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   36:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   37:     */ import com.asinfo.as2.entities.Asiento;
/*   38:     */ import com.asinfo.as2.entities.Canal;
/*   39:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   40:     */ import com.asinfo.as2.entities.Cliente;
/*   41:     */ import com.asinfo.as2.entities.Cobro;
/*   42:     */ import com.asinfo.as2.entities.CondicionPago;
/*   43:     */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   44:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   45:     */ import com.asinfo.as2.entities.DetalleCobro;
/*   46:     */ import com.asinfo.as2.entities.DetalleCobroFormaCobro;
/*   47:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   48:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   49:     */ import com.asinfo.as2.entities.DetalleFacturaClienteComercializadora;
/*   50:     */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   51:     */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   52:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   53:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   54:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   55:     */ import com.asinfo.as2.entities.Documento;
/*   56:     */ import com.asinfo.as2.entities.Empresa;
/*   57:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   58:     */ import com.asinfo.as2.entities.GuiaRemision;
/*   59:     */ import com.asinfo.as2.entities.HojaRuta;
/*   60:     */ import com.asinfo.as2.entities.Impuesto;
/*   61:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   62:     */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   63:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   64:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   65:     */ import com.asinfo.as2.entities.MotivoAnulacion;
/*   66:     */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*   67:     */ import com.asinfo.as2.entities.NotaFacturaCliente;
/*   68:     */ import com.asinfo.as2.entities.Organizacion;
/*   69:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   70:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   71:     */ import com.asinfo.as2.entities.PrefacturaCliente;
/*   72:     */ import com.asinfo.as2.entities.Producto;
/*   73:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   74:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   75:     */ import com.asinfo.as2.entities.Secuencia;
/*   76:     */ import com.asinfo.as2.entities.Subempresa;
/*   77:     */ import com.asinfo.as2.entities.Sucursal;
/*   78:     */ import com.asinfo.as2.entities.Transportista;
/*   79:     */ import com.asinfo.as2.entities.Ubicacion;
/*   80:     */ import com.asinfo.as2.entities.Unidad;
/*   81:     */ import com.asinfo.as2.entities.Zona;
/*   82:     */ import com.asinfo.as2.entities.sri.AnuladoSRI;
/*   83:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   84:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   85:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   86:     */ import com.asinfo.as2.enumeraciones.FormatoCelda;
/*   87:     */ import com.asinfo.as2.enumeraciones.OrigenEnum;
/*   88:     */ import com.asinfo.as2.enumeraciones.Parametro;
/*   89:     */ import com.asinfo.as2.enumeraciones.TipoImpuestoEnum;
/*   90:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   91:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   92:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   93:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   94:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI;
/*   95:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*   96:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   97:     */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*   98:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*   99:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  100:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  101:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  102:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  103:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  104:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  105:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  106:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  107:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  108:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  109:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  110:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  111:     */ import com.asinfo.as2.util.AppUtil;
/*  112:     */ import com.asinfo.as2.utils.DatosSRI;
/*  113:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  114:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*  115:     */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  116:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  117:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta;
/*  118:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  119:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioEmisionBono;
/*  120:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  121:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaClienteRemote;
/*  122:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  123:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  124:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  125:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*  126:     */ import com.asinfo.as2.ventas.reportes.ReporteFacturaClienteBean;
/*  127:     */ import java.io.IOException;
/*  128:     */ import java.io.InputStream;
/*  129:     */ import java.io.PrintStream;
/*  130:     */ import java.math.BigDecimal;
/*  131:     */ import java.math.RoundingMode;
/*  132:     */ import java.util.ArrayList;
/*  133:     */ import java.util.Collection;
/*  134:     */ import java.util.Date;
/*  135:     */ import java.util.HashMap;
/*  136:     */ import java.util.Iterator;
/*  137:     */ import java.util.List;
/*  138:     */ import java.util.Map;
/*  139:     */ import java.util.TreeMap;
/*  140:     */ import javax.ejb.EJB;
/*  141:     */ import javax.ejb.SessionContext;
/*  142:     */ import javax.ejb.Stateless;
/*  143:     */ import javax.ejb.TransactionAttribute;
/*  144:     */ import javax.ejb.TransactionAttributeType;
/*  145:     */ import javax.ejb.TransactionManagement;
/*  146:     */ import javax.ejb.TransactionManagementType;
/*  147:     */ import javax.faces.model.SelectItem;
/*  148:     */ import net.sf.jasperreports.engine.JRDataSource;
/*  149:     */ import org.apache.log4j.Logger;
/*  150:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  151:     */ 
/*  152:     */ @Stateless
/*  153:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  154:     */ public class ServicioFacturaClienteImpl
/*  155:     */   extends AbstractServicioAS2Financiero
/*  156:     */   implements ServicioFacturaCliente, ServicioFacturaClienteRemote
/*  157:     */ {
/*  158:     */   private static final long serialVersionUID = -9214403611253077141L;
/*  159:     */   @EJB
/*  160:     */   private FacturaClienteDao facturaClienteDao;
/*  161:     */   @EJB
/*  162:     */   private PrefacturaClienteDao prefacturaClienteDao;
/*  163:     */   @EJB
/*  164:     */   private DetalleFacturaClienteDao detalleFacturaClienteDao;
/*  165:     */   @EJB
/*  166:     */   private DetalleFacturaClienteComercializadoraDao detalleFacturaClienteComercializadoraDao;
/*  167:     */   @EJB
/*  168:     */   private ServicioSecuencia servicioSecuencia;
/*  169:     */   @EJB
/*  170:     */   private CuentaPorCobrarDao cuentaPorCobrarDao;
/*  171:     */   @EJB
/*  172:     */   private ServicioCondicionPago servicioCondicionPago;
/*  173:     */   @EJB
/*  174:     */   private ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  175:     */   @EJB
/*  176:     */   private ImpuestoProductoFacturaClienteDao impuestoProductoFacturaClienteDao;
/*  177:     */   @EJB
/*  178:     */   private ServicioPeriodo servicioPeriodo;
/*  179:     */   @EJB
/*  180:     */   ServicioAsiento servicioAsiento;
/*  181:     */   @EJB
/*  182:     */   private ServicioDocumento servicioDocumento;
/*  183:     */   @EJB
/*  184:     */   private FacturaClienteSRIDao facturaClienteSRIDao;
/*  185:     */   @EJB
/*  186:     */   private ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  187:     */   @EJB
/*  188:     */   private ServicioProducto servicioProducto;
/*  189:     */   @EJB
/*  190:     */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  191:     */   @EJB
/*  192:     */   private ServicioVerificadorVentas servicioVerificadorVentas;
/*  193:     */   @EJB
/*  194:     */   private ServicioAnuladoSRI servicioAnuladoSRI;
/*  195:     */   @EJB
/*  196:     */   private ServicioDespachoCliente servicioDespachoCliente;
/*  197:     */   @EJB
/*  198:     */   private ServicioEmpresa servicioEmpresa;
/*  199:     */   @EJB
/*  200:     */   private DireccionEmpresaDao direccionEmpresaDao;
/*  201:     */   @EJB
/*  202:     */   private ServicioImpuesto servicioImpuesto;
/*  203:     */   @EJB
/*  204:     */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  205:     */   @EJB
/*  206:     */   private ServicioCobro servicioCobro;
/*  207:     */   @EJB
/*  208:     */   private ServicioFacturaClienteSRIXML servicioFacturaClienteSRIXML;
/*  209:     */   @EJB
/*  210:     */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  211:     */   @EJB
/*  212:     */   private ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  213:     */   @EJB
/*  214:     */   private DespachoClienteDao despachoClienteDao;
/*  215:     */   @EJB
/*  216:     */   private ServicioSucursal servicioSucursal;
/*  217:     */   @EJB
/*  218:     */   private ServicioOrganizacion servicioOrganizacion;
/*  219:     */   @EJB
/*  220:     */   private ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  221:     */   @EJB
/*  222:     */   private ServicioContratoVenta servicioContratoVenta;
/*  223:     */   @EJB
/*  224:     */   private ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  225:     */   @EJB
/*  226:     */   private PedidoClienteDao pedidoClienteDao;
/*  227:     */   @EJB
/*  228:     */   private ServicioPedidoCliente servicioPedidoCliente;
/*  229:     */   @EJB
/*  230:     */   private ServicioListaDescuentos servicioListaDescuentos;
/*  231:     */   @EJB
/*  232:     */   private HojaRutaDao hojaRutaDao;
/*  233:     */   @EJB
/*  234:     */   private ServicioGuiaRemision servicioGuiaRemision;
/*  235:     */   @EJB
/*  236:     */   private ServicioGenerico<DetalleFormaCobro> servicioDetalleFormaCobro;
/*  237:     */   @EJB
/*  238:     */   private ServicioGenerico<CuentaPorCobrar> servicioCuentaPorCobrar;
/*  239:     */   @EJB
/*  240:     */   private ServicioGenerico<DetalleFacturaCliente> servicioDetalleFacturaCliente;
/*  241:     */   @EJB
/*  242:     */   private ServicioGenerico<ImpuestoProductoFacturaCliente> servicioImpuestoProductoFacturaCliente;
/*  243:     */   @EJB
/*  244:     */   private DetalleCobroDao detalleCobroDao;
/*  245:     */   @EJB
/*  246:     */   private DetalleCobroFormaCobroDao detalleCobroFormaCobroDao;
/*  247:     */   @EJB
/*  248:     */   private ServicioEmisionBono servicioBono;
/*  249:     */   @EJB
/*  250:     */   private RangoImpuestoDao rangoImpuestoDao;
/*  251:     */   @EJB
/*  252:     */   private ServicioUnidadConversion servicioUnidadConversion;
/*  253:     */   @EJB
/*  254:     */   private ServicioListaPrecios servicioListaPrecios;
/*  255:     */   @EJB
/*  256:     */   private GenericoDao<NotaFacturaCliente> notaFacturaClienteDao;
/*  257:     */   @EJB
/*  258:     */   protected ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  259:     */   @EJB
/*  260:     */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  261:     */   @EJB
/*  262:     */   private ClaveAccesoPendientePublicarDao claveAccesoPendientePublicarDao;
/*  263:     */   @EJB
/*  264:     */   private AnuladoSRIDao anuladoSRIDao;
/*  265:     */   
/*  266:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  267:     */   public com.asinfo.as2.entities.FacturaCliente guardar(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/*  268:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  269:     */   {
/*  270:     */     try
/*  271:     */     {
/*  272:     */       int tipoEmision;
/*  273: 280 */       if ((facturaCliente.getDocumento() != null) && (facturaCliente.getDocumento().isIndicadorDocumentoElectronico()))
/*  274:     */       {
/*  275: 283 */         int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(facturaCliente.getIdOrganizacion()).booleanValue() ? 2 : 1;
/*  276: 284 */         facturaCliente.getFacturaClienteSRI().setAmbiente(ambiente);
/*  277: 285 */         tipoEmision = 1;
/*  278: 286 */         facturaCliente.getFacturaClienteSRI().setEmail(facturaCliente.getEmail());
/*  279:     */         
/*  280:     */ 
/*  281: 289 */         facturaCliente.getFacturaClienteSRI().setTipoEmision(tipoEmision);
/*  282: 291 */         if (facturaCliente.getSucursal() != null)
/*  283:     */         {
/*  284: 293 */           Sucursal sucursal = this.servicioSucursal.cargarDetalle(facturaCliente.getSucursal().getId());
/*  285: 294 */           facturaCliente.getFacturaClienteSRI().setDireccionSucursal(sucursal.getUbicacion().getDireccionCompleta());
/*  286:     */         }
/*  287: 296 */         if (facturaCliente.getIdOrganizacion() != 0)
/*  288:     */         {
/*  289: 297 */           String dirMatriz = "";
/*  290:     */           try
/*  291:     */           {
/*  292: 299 */             dirMatriz = this.servicioOrganizacion.obtenerDireccionMatriz(facturaCliente.getIdOrganizacion());
/*  293:     */           }
/*  294:     */           catch (Exception e)
/*  295:     */           {
/*  296: 301 */             dirMatriz = "N/A";
/*  297:     */           }
/*  298: 303 */           facturaCliente.getFacturaClienteSRI().setDireccionMatriz(dirMatriz);
/*  299:     */         }
/*  300:     */       }
/*  301: 309 */       if ((facturaCliente.getDespachoCliente() != null) && (facturaCliente.getDespachoCliente().getTransportista() != null)) {
/*  302: 310 */         facturaCliente.setReferencia10(facturaCliente.getDespachoCliente().getTransportista().getNombre());
/*  303:     */       }
/*  304: 313 */       if ((facturaCliente.getNumero() == null) || (facturaCliente.getNumero().isEmpty()))
/*  305:     */       {
/*  306: 314 */         String numero = this.servicioSecuencia.obtenerSecuencia(facturaCliente.getDocumento().getSecuencia(), facturaCliente.getFecha());
/*  307: 315 */         facturaCliente.setNumero(numero);
/*  308:     */       }
/*  309: 318 */       if (facturaCliente.getIdFacturaCliente() != 0)
/*  310:     */       {
/*  311: 319 */         BigDecimal creditoUtiulizado = this.facturaClienteDao.totalFacturaCliente(facturaCliente.getIdFacturaCliente());
/*  312: 320 */         this.servicioVerificadorVentas.actualizarCreditoUtilizado(facturaCliente.getEmpresa().getCliente(), creditoUtiulizado, true);
/*  313:     */       }
/*  314: 322 */       validar(facturaCliente);
/*  315: 324 */       if ((facturaCliente.getDespachoCliente() != null) && (facturaCliente.getDespachoCliente().getId() == 0))
/*  316:     */       {
/*  317: 325 */         int contador = 0;
/*  318: 326 */         for (tipoEmision = facturaCliente.getListaDetalleFacturaCliente().iterator(); tipoEmision.hasNext();)
/*  319:     */         {
/*  320: 326 */           dfc = (DetalleFacturaCliente)tipoEmision.next();
/*  321: 327 */           if ((!dfc.isEliminado()) && (dfc.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO))) {
/*  322: 328 */             contador++;
/*  323:     */           }
/*  324:     */         }
/*  325: 331 */         if (contador == 0) {
/*  326: 332 */           facturaCliente.setDespachoCliente(null);
/*  327:     */         }
/*  328: 336 */         if ((facturaCliente.getDespachoCliente() != null) && (facturaCliente.getDespachoCliente().getListaDetalleDespachoCliente().size() > 0))
/*  329:     */         {
/*  330: 337 */           facturaCliente.getDespachoCliente().setIndicadorGeneradoFactura(true);
/*  331: 338 */           this.servicioDespachoCliente.guardar(facturaCliente.getDespachoCliente());
/*  332:     */         }
/*  333:     */       }
/*  334: 343 */       this.servicioVerificadorVentas.actualizarCreditoUtilizado(facturaCliente.getEmpresa().getCliente(), facturaCliente.getTotalFactura(), false);
/*  335:     */       
/*  336: 345 */       this.servicioVerificadorVentas.actualizarPrecioFechaUltimaVenta(facturaCliente);
/*  337: 346 */       this.servicioVerificadorVentas.actualizarCantidadPorFacturar(facturaCliente, true);
/*  338: 347 */       HashMap<String, BigDecimal> mapaResumen = new HashMap();
/*  339:     */       
/*  340: 349 */       Map<Integer, DespachoCliente> mapaDespachoCliente = new HashMap();
/*  341: 351 */       for (DetalleFacturaCliente dfc : facturaCliente.getListaDetalleFacturaCliente())
/*  342:     */       {
/*  343: 353 */         if (AppUtil.getUsuarioEnSesion().getNombreUsuario().equals("usuario_anonimo")) {
/*  344: 354 */           dfc.setCantidad(FuncionesUtiles.redondearBigDecimal(dfc.getCantidad(), 4));
/*  345:     */         }
/*  346: 357 */         for (ImpuestoProductoFacturaCliente impuestoProductoFacturaCliente : dfc.getListaImpuestoProductoFacturaCliente()) {
/*  347: 358 */           this.impuestoProductoFacturaClienteDao.guardar(impuestoProductoFacturaCliente);
/*  348:     */         }
/*  349: 361 */         if (!dfc.isEliminado())
/*  350:     */         {
/*  351: 362 */           if (dfc.getUnidadVenta() == null) {
/*  352: 363 */             throw new ExcepcionAS2("msg_info_seleccionar_unidad", " UnidadVenta, Producto: " + dfc.getProducto().getNombre());
/*  353:     */           }
/*  354: 365 */           String unidadVenta = dfc.getUnidadVenta().getNombre();
/*  355: 366 */           if (mapaResumen.containsKey(unidadVenta))
/*  356:     */           {
/*  357: 367 */             BigDecimal valor = ((BigDecimal)mapaResumen.get(unidadVenta)).add(dfc.getCantidad());
/*  358: 368 */             mapaResumen.put(unidadVenta, valor);
/*  359:     */           }
/*  360:     */           else
/*  361:     */           {
/*  362: 370 */             mapaResumen.put(unidadVenta, dfc.getCantidad());
/*  363:     */           }
/*  364:     */         }
/*  365: 374 */         this.detalleFacturaClienteDao.guardar(dfc);
/*  366: 376 */         if (dfc.getDetalleDespachoCliente() != null) {
/*  367: 377 */           mapaDespachoCliente.put(Integer.valueOf(dfc.getDetalleDespachoCliente().getDespachoCliente().getId()), dfc
/*  368: 378 */             .getDetalleDespachoCliente().getDespachoCliente());
/*  369:     */         }
/*  370:     */       }
/*  371: 384 */       for (DetalleFacturaCliente dfc = mapaDespachoCliente.values().iterator(); dfc.hasNext();)
/*  372:     */       {
/*  373: 384 */         despacho = (DespachoCliente)dfc.next();
/*  374: 385 */         if (despacho.getId() != 0)
/*  375:     */         {
/*  376: 386 */           DespachoCliente dc = this.servicioDespachoCliente.buscarPorId(Integer.valueOf(despacho.getId()));
/*  377: 387 */           dc.setIndicadorGeneradoFactura(true);
/*  378: 388 */           dc.setFacturaCliente(facturaCliente);
/*  379: 389 */           this.despachoClienteDao.guardar(dc);
/*  380:     */         }
/*  381:     */       }
/*  382:     */       DespachoCliente despacho;
/*  383:     */       StringBuffer resumenUnidad;
/*  384: 393 */       if (facturaCliente.getReferencia2().isEmpty())
/*  385:     */       {
/*  386: 394 */         resumenUnidad = new StringBuffer();
/*  387: 395 */         for (String unidadVenta : mapaResumen.keySet()) {
/*  388: 396 */           resumenUnidad.append(unidadVenta).append(": ").append(String.valueOf(mapaResumen.get(unidadVenta))).append(", ");
/*  389:     */         }
/*  390: 399 */         if (resumenUnidad.length() > 1) {
/*  391: 400 */           facturaCliente.setReferencia1(resumenUnidad.substring(0, resumenUnidad.length() - 2));
/*  392:     */         }
/*  393:     */       }
/*  394: 405 */       if (facturaCliente.isIndicadorGeneraCxC()) {
/*  395: 406 */         for (CuentaPorCobrar cuentaPorCobrar : facturaCliente.getListaCuentaPorCobrar())
/*  396:     */         {
/*  397: 407 */           cuentaPorCobrar.setSaldo(cuentaPorCobrar.getValor()
/*  398: 408 */             .subtract(cuentaPorCobrar.getTraValorCobrado() != null ? cuentaPorCobrar.getTraValorCobrado() : BigDecimal.ZERO));
/*  399: 409 */           this.cuentaPorCobrarDao.guardar(cuentaPorCobrar);
/*  400:     */         }
/*  401:     */       }
/*  402: 412 */       for (DetalleFacturaClienteComercializadora detalleFacturaClienteComercializadora : facturaCliente
/*  403: 413 */         .getListaDetalleFacturaClienteComercializadora()) {
/*  404: 414 */         this.detalleFacturaClienteComercializadoraDao.guardar(detalleFacturaClienteComercializadora);
/*  405:     */       }
/*  406: 418 */       if (facturaCliente.getFacturaClienteSRI() != null) {
/*  407: 419 */         this.facturaClienteSRIDao.guardar(facturaCliente.getFacturaClienteSRI());
/*  408:     */       }
/*  409: 421 */       facturaCliente.setIndicadorGeneradoPrefactura(facturaCliente.getListaPrefacturaCliente().size() > 0);
/*  410: 422 */       this.facturaClienteDao.guardar(facturaCliente);
/*  411:     */       List<com.asinfo.as2.entities.FacturaCliente> listaFacturaCliente;
/*  412: 425 */       if ((!facturaCliente.isIndicadorSaldoInicial()) && (facturaCliente.getDocumento().isIndicadorContabilizar()) && 
/*  413: 426 */         (ParametrosSistema.getContabilizacionVentas(facturaCliente.getIdOrganizacion()).booleanValue()))
/*  414:     */       {
/*  415: 428 */         listaFacturaCliente = new ArrayList();
/*  416: 429 */         listaFacturaCliente.add(facturaCliente);
/*  417: 430 */         contabilizar(listaFacturaCliente);
/*  418:     */       }
/*  419: 434 */       if (facturaCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() == null) {
/*  420: 435 */         this.servicioSecuencia.actualizarSecuencia(facturaCliente.getDocumento().getSecuencia(), facturaCliente.getNumero());
/*  421:     */       } else {
/*  422: 437 */         this.servicioAutorizacionAutoimpresorSRI.actualizaSecuencia(facturaCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI());
/*  423:     */       }
/*  424: 439 */       this.servicioVerificadorVentas.actualizarCantidadPorFacturar(facturaCliente, false);
/*  425: 440 */       for (PrefacturaCliente prefactura : facturaCliente.getListaPrefacturaCliente())
/*  426:     */       {
/*  427: 441 */         if (prefactura.isEliminado())
/*  428:     */         {
/*  429: 442 */           prefactura.setFacturaCliente(null);
/*  430:     */         }
/*  431:     */         else
/*  432:     */         {
/*  433: 444 */           prefactura.setEstado(Estado.FACTURADO);
/*  434: 445 */           prefactura.setFacturaCliente(facturaCliente);
/*  435:     */         }
/*  436: 447 */         this.prefacturaClienteDao.guardar(prefactura);
/*  437:     */       }
/*  438: 449 */       if (facturaCliente.getCobro() != null)
/*  439:     */       {
/*  440: 450 */         this.servicioCobro.cargarFacturasPendientes(facturaCliente.getCobro(), facturaCliente.getIdFacturaCliente());
/*  441: 451 */         this.servicioCobro.guardar(facturaCliente.getCobro());
/*  442:     */       }
/*  443: 454 */       this.facturaClienteDao.flush();
/*  444: 455 */       if ((facturaCliente.getEmail() != null) && (!facturaCliente.getEmail().isEmpty())) {
/*  445:     */         try
/*  446:     */         {
/*  447: 459 */           Empresa empresa = null;
/*  448: 460 */           if ((facturaCliente.getSubempresa() != null) && (!facturaCliente.isFacturaClienteFloricola())) {
/*  449: 461 */             empresa = facturaCliente.getSubempresa().getEmpresa();
/*  450:     */           } else {
/*  451: 463 */             empresa = facturaCliente.getEmpresa();
/*  452:     */           }
/*  453: 465 */           this.servicioEmpresa.actualizarMails(empresa, facturaCliente.getEmail(), DocumentoBase.FACTURA_CLIENTE);
/*  454:     */         }
/*  455:     */         catch (Exception e)
/*  456:     */         {
/*  457: 468 */           e.printStackTrace();
/*  458:     */         }
/*  459:     */       }
/*  460: 473 */       if (((facturaCliente.isIndicadorSaldoInicial()) && (!facturaCliente.isIndicadorGeneraCxC())) || ((!facturaCliente.isIndicadorSaldoInicial()) && 
/*  461: 474 */         (facturaCliente.getDocumento().isIndicadorDocumentoTributario()) && 
/*  462: 475 */         (facturaCliente.getDocumento().isIndicadorDocumentoElectronico()))) {
/*  463: 476 */         if (facturaCliente.getFacturaClienteSRI().getGenerarDocumentoElectronico().booleanValue())
/*  464:     */         {
/*  465: 478 */           facturaCliente = this.servicioFacturaClienteSRIXML.generarClaveAcceso(null, facturaCliente, true);
/*  466: 479 */           boolean indicadorEnviarEmail = ParametrosSistema.isComprobantesElectronicosEnviarEmailGuardar(facturaCliente.getIdOrganizacion()).booleanValue();
/*  467: 480 */           if (indicadorEnviarEmail) {
/*  468: 482 */             if (facturaCliente.getFacturaClienteSRI().getAmbiente() == 2) {
/*  469:     */               try
/*  470:     */               {
/*  471: 484 */                 enviarMail(facturaCliente, facturaCliente.getDocumentoElectronico(), null);
/*  472:     */               }
/*  473:     */               catch (Exception e)
/*  474:     */               {
/*  475: 486 */                 e.printStackTrace();
/*  476:     */                 
/*  477: 488 */                 System.out.println("Error en la generacion del reporte y enviar por mail al cliente (Facturacion electronica)" + e
/*  478: 489 */                   .getMessage());
/*  479:     */               }
/*  480:     */             }
/*  481:     */           }
/*  482:     */         }
/*  483:     */         else
/*  484:     */         {
/*  485: 494 */           facturaCliente.setEstado(Estado.ELABORADO);
/*  486:     */         }
/*  487:     */       }
/*  488: 498 */       this.servicioBono.asignarNumeroBono(facturaCliente);
/*  489:     */       
/*  490: 500 */       return facturaCliente;
/*  491:     */     }
/*  492:     */     catch (ExcepcionAS2Ventas e)
/*  493:     */     {
/*  494: 503 */       e.printStackTrace();
/*  495: 504 */       this.context.setRollbackOnly();
/*  496: 505 */       throw e;
/*  497:     */     }
/*  498:     */     catch (ExcepcionAS2Inventario e)
/*  499:     */     {
/*  500: 507 */       e.printStackTrace();
/*  501: 508 */       this.context.setRollbackOnly();
/*  502: 509 */       throw e;
/*  503:     */     }
/*  504:     */     catch (ExcepcionAS2Financiero e)
/*  505:     */     {
/*  506: 511 */       e.printStackTrace();
/*  507: 512 */       this.context.setRollbackOnly();
/*  508: 513 */       throw e;
/*  509:     */     }
/*  510:     */     catch (ExcepcionAS2 e)
/*  511:     */     {
/*  512: 515 */       e.printStackTrace();
/*  513: 516 */       this.context.setRollbackOnly();
/*  514: 517 */       throw e;
/*  515:     */     }
/*  516:     */     catch (AS2Exception e)
/*  517:     */     {
/*  518: 519 */       this.context.setRollbackOnly();
/*  519: 520 */       e.printStackTrace();
/*  520: 521 */       throw e;
/*  521:     */     }
/*  522:     */     catch (Exception e)
/*  523:     */     {
/*  524: 523 */       e.printStackTrace();
/*  525: 524 */       this.context.setRollbackOnly();
/*  526: 525 */       throw new ExcepcionAS2(e);
/*  527:     */     }
/*  528:     */   }
/*  529:     */   
/*  530:     */   public void enviarMail(com.asinfo.as2.entities.FacturaCliente facturaCliente, String emails)
/*  531:     */     throws ExcepcionAS2
/*  532:     */   {
/*  533: 531 */     enviarMail(facturaCliente, null, emails);
/*  534:     */   }
/*  535:     */   
/*  536:     */   public void enviarMail(com.asinfo.as2.entities.FacturaCliente facturaCliente, DocumentoElectronico documento, String emails)
/*  537:     */     throws ExcepcionAS2
/*  538:     */   {
/*  539:     */     try
/*  540:     */     {
/*  541: 537 */       facturaCliente = cargarDetalle(facturaCliente.getId(), false);
/*  542: 538 */       if (documento == null)
/*  543:     */       {
/*  544: 539 */         String version = "1.0.0";
/*  545:     */         
/*  546: 541 */         int ambiente = facturaCliente.getFacturaClienteSRI().getAmbiente();
/*  547: 542 */         int tipoEmision = facturaCliente.getFacturaClienteSRI().getTipoEmision();
/*  548:     */         
/*  549:     */ 
/*  550: 545 */         TipoDocumentoElectronicoEnum tipoDocumento = facturaCliente.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_DEBITO_CLIENTE ? TipoDocumentoElectronicoEnum.NOTA_DEBITO : facturaCliente.getDocumento().getDocumentoBase() == DocumentoBase.FACTURA_CLIENTE ? TipoDocumentoElectronicoEnum.FACTURA : TipoDocumentoElectronicoEnum.NOTA_CREDITO;
/*  551:     */         
/*  552:     */ 
/*  553: 548 */         Organizacion organizacion = null;
/*  554: 549 */         int idOrganizacion = facturaCliente.getIdOrganizacion();
/*  555: 550 */         organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/*  556: 551 */         documento = new DocumentoElectronico(organizacion, ambiente, tipoEmision, tipoDocumento, "1.0.0");
/*  557: 552 */         documento.prepararParaEnvioEmail(facturaCliente, emails, facturaCliente.getDocumento().getReporte());
/*  558:     */       }
/*  559: 555 */       documento.setNombreReporte(facturaCliente.getDocumento().getReporte());
/*  560: 556 */       if (emails != null) {
/*  561: 557 */         documento.setEmail(emails);
/*  562:     */       }
/*  563: 561 */       List<Object[]> listaDatosReporte = getReporteFacturaCliente(facturaCliente.getIdFacturaCliente(), TipoOrganizacion.TIPO_ORGANIZACION_GENERAL, 1, false, true, facturaCliente
/*  564: 562 */         .getIdOrganizacion());
/*  565: 563 */       JRDataSource ds = new QueryResultDataSource(listaDatosReporte, ReporteFacturaClienteBean.fields);
/*  566: 564 */       documento.setDataSource(ds);
/*  567:     */       
/*  568: 566 */       this.servicioDocumentoElectronico.enviarDocumentoPorEmail(documento, facturaCliente.getEmpresa());
/*  569:     */     }
/*  570:     */     catch (Exception e)
/*  571:     */     {
/*  572: 568 */       e.printStackTrace();
/*  573: 569 */       throw new ExcepcionAS2(e.getMessage());
/*  574:     */     }
/*  575:     */   }
/*  576:     */   
/*  577:     */   public void cargarAutorizacionAutoImpresor(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/*  578:     */     throws ExcepcionAS2Financiero
/*  579:     */   {
/*  580: 575 */     facturaCliente.setNumero(this.servicioAutorizacionAutoimpresorSRI
/*  581: 576 */       .obtenerSecuencia(facturaCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()));
/*  582:     */   }
/*  583:     */   
/*  584:     */   public com.asinfo.as2.entities.FacturaCliente cargarSecuencia(com.asinfo.as2.entities.FacturaCliente facturaCliente, PuntoDeVenta puntoDeVenta)
/*  585:     */     throws ExcepcionAS2
/*  586:     */   {
/*  587: 589 */     if (puntoDeVenta != null) {
/*  588: 590 */       this.servicioDocumento.cargarSecuencia(facturaCliente.getDocumento(), puntoDeVenta, facturaCliente.getFecha());
/*  589:     */     }
/*  590: 592 */     if (facturaCliente.getId() == 0)
/*  591:     */     {
/*  592: 593 */       String numero = this.servicioSecuencia.obtenerSecuencia(facturaCliente.getDocumento().getSecuencia(), facturaCliente.getFecha());
/*  593:     */       
/*  594: 595 */       facturaCliente.setNumero(numero);
/*  595:     */     }
/*  596: 598 */     return facturaCliente;
/*  597:     */   }
/*  598:     */   
/*  599:     */   public com.asinfo.as2.entities.FacturaCliente generarCuentaPorCobrar(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/*  600:     */     throws ExcepcionAS2
/*  601:     */   {
/*  602: 611 */     if ((facturaCliente.getCondicionPago() == null) || (facturaCliente.getCondicionPago().getId() == 0)) {
/*  603: 612 */       throw new ExcepcionAS2("msg_info_seleccionar_condicion_pago");
/*  604:     */     }
/*  605: 615 */     int numeroCuota = 0;
/*  606: 616 */     int numeroCuotas = facturaCliente.getNumeroCuotas();
/*  607: 617 */     if (numeroCuotas > 0)
/*  608:     */     {
/*  609: 618 */       BigDecimal totalFactura = facturaCliente.getTotalFactura().add(facturaCliente.getValorOtros());
/*  610:     */       
/*  611: 620 */       BigDecimal valorCuota = totalFactura.divide(new BigDecimal(numeroCuotas), 2, RoundingMode.HALF_UP);
/*  612:     */       
/*  613: 622 */       int hasta = Math.max(facturaCliente.getNumeroCuotas(), facturaCliente.getListaCuentaPorCobrar().size());
/*  614: 624 */       for (int i = 0; i < hasta; i++)
/*  615:     */       {
/*  616: 625 */         numeroCuota = i + 1;
/*  617: 627 */         if (numeroCuota > facturaCliente.getNumeroCuotas())
/*  618:     */         {
/*  619: 628 */           ((CuentaPorCobrar)facturaCliente.getListaCuentaPorCobrar().get(i)).setEliminado(true);
/*  620:     */         }
/*  621:     */         else
/*  622:     */         {
/*  623:     */           CuentaPorCobrar cuentaPorCobrar;
/*  624:     */           CuentaPorCobrar cuentaPorCobrar;
/*  625: 633 */           if (numeroCuota > facturaCliente.getListaCuentaPorCobrar().size()) {
/*  626: 634 */             cuentaPorCobrar = new CuentaPorCobrar();
/*  627:     */           } else {
/*  628: 636 */             cuentaPorCobrar = (CuentaPorCobrar)facturaCliente.getListaCuentaPorCobrar().get(i);
/*  629:     */           }
/*  630: 638 */           cuentaPorCobrar.setEliminado(false);
/*  631: 639 */           Date fechaVencimiento = null;
/*  632:     */           
/*  633: 641 */           CondicionPago condicionPago = this.servicioCondicionPago.buscarPorId(Integer.valueOf(facturaCliente.getCondicionPago().getIdCondicionPago()));
/*  634:     */           
/*  635: 643 */           fechaVencimiento = FuncionesUtiles.sumarFechaDiasMeses(facturaCliente.getFecha(), condicionPago.getMesesPlazo() * (i + 1), condicionPago
/*  636: 644 */             .getDiasPlazo() * (i + 1));
/*  637:     */           
/*  638: 646 */           cuentaPorCobrar.setFechaVencimiento(fechaVencimiento);
/*  639: 647 */           cuentaPorCobrar.setIdOrganizacion(facturaCliente.getIdOrganizacion());
/*  640: 648 */           cuentaPorCobrar.setIdSucursal(facturaCliente.getSucursal().getIdSucursal());
/*  641: 649 */           cuentaPorCobrar.setNumeroCuota(numeroCuota);
/*  642: 652 */           if (numeroCuota == numeroCuotas)
/*  643:     */           {
/*  644: 653 */             valorCuota = totalFactura;
/*  645: 654 */             facturaCliente.setFechaVencimiento(fechaVencimiento);
/*  646:     */           }
/*  647: 656 */           cuentaPorCobrar.setValor(valorCuota);
/*  648:     */           
/*  649: 658 */           cuentaPorCobrar.setFacturaCliente(facturaCliente);
/*  650: 660 */           if (numeroCuota > facturaCliente.getListaCuentaPorCobrar().size()) {
/*  651: 661 */             facturaCliente.getListaCuentaPorCobrar().add(cuentaPorCobrar);
/*  652:     */           }
/*  653: 664 */           totalFactura = totalFactura.subtract(valorCuota);
/*  654:     */         }
/*  655:     */       }
/*  656:     */     }
/*  657: 669 */     return facturaCliente;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public void eliminar(com.asinfo.as2.entities.FacturaCliente entidad)
/*  661:     */   {
/*  662: 681 */     GuiaRemision guiaRemision = this.servicioGuiaRemision.buscarPorFactura(entidad.getId());
/*  663: 682 */     if (guiaRemision != null) {
/*  664: 683 */       guiaRemision.setFacturaCliente(null);
/*  665:     */     }
/*  666: 690 */     HashMap<Integer, Cobro> cobros = new HashMap();
/*  667: 691 */     HashMap<Integer, CuentaPorCobrar> cuentasPorCobrar = new HashMap();
/*  668: 692 */     for (CuentaPorCobrar cpc : entidad.getListaCuentaPorCobrar()) {
/*  669: 693 */       if (cpc != null)
/*  670:     */       {
/*  671: 694 */         cpc.setEliminado(true);
/*  672: 695 */         for (DetalleCobro dc : this.detalleCobroDao.obtenerDetalleCobro(cpc.getId()))
/*  673:     */         {
/*  674: 696 */           dc.setEliminado(true);
/*  675: 697 */           for (DetalleCobroFormaCobro dcfc : dc.getListaDetalleCobroFormaCobro())
/*  676:     */           {
/*  677: 698 */             dcfc.setEliminado(true);
/*  678: 699 */             this.detalleCobroFormaCobroDao.eliminar(dcfc);
/*  679:     */           }
/*  680: 701 */           this.detalleCobroDao.eliminar(dc);
/*  681:     */         }
/*  682: 703 */         if ((cpc.getDetalleFormaCobroProtesto() != null) && (!cpc.getDetalleFormaCobroProtesto().isEliminado()))
/*  683:     */         {
/*  684: 704 */           cpc.getDetalleFormaCobroProtesto().setEliminado(true);
/*  685: 705 */           this.servicioDetalleFormaCobro.eliminar(cpc.getDetalleFormaCobroProtesto());
/*  686: 706 */           cpc.getDetalleFormaCobroProtesto().getCobro().setEliminado(true);
/*  687: 707 */           if (cobros.get(Integer.valueOf(cpc.getDetalleFormaCobroProtesto().getCobro().getId())) == null) {
/*  688: 708 */             cobros.put(Integer.valueOf(cpc.getDetalleFormaCobroProtesto().getCobro().getId()), cpc.getDetalleFormaCobroProtesto().getCobro());
/*  689:     */           }
/*  690:     */         }
/*  691: 711 */         if (cuentasPorCobrar.get(Integer.valueOf(cpc.getId())) == null) {
/*  692: 712 */           cuentasPorCobrar.put(Integer.valueOf(cpc.getId()), cpc);
/*  693:     */         }
/*  694:     */       }
/*  695:     */     }
/*  696: 716 */     for (Cobro c : cobros.values())
/*  697:     */     {
/*  698: 717 */       c.setEliminado(true);
/*  699: 718 */       for (DetalleCobro dc : c.getListaDetalleCobro())
/*  700:     */       {
/*  701: 719 */         dc.setEliminado(true);
/*  702: 720 */         this.detalleCobroDao.eliminar(dc);
/*  703:     */       }
/*  704: 722 */       for (DetalleFormaCobro dc : c.getListaDetalleFormaCobro())
/*  705:     */       {
/*  706: 723 */         dc.setEliminado(true);
/*  707: 724 */         this.servicioDetalleFormaCobro.eliminar(dc);
/*  708:     */       }
/*  709: 726 */       this.servicioCobro.eliminar(c);
/*  710:     */     }
/*  711: 729 */     for (CuentaPorCobrar c : cuentasPorCobrar.values())
/*  712:     */     {
/*  713: 730 */       c.setEliminado(true);
/*  714: 731 */       this.servicioCuentaPorCobrar.eliminar(c);
/*  715:     */     }
/*  716: 733 */     for (DetalleFacturaCliente dfc : entidad.getListaDetalleFacturaCliente())
/*  717:     */     {
/*  718: 735 */       dfc.setEliminado(true);
/*  719: 736 */       for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente())
/*  720:     */       {
/*  721: 737 */         ipfc.setEliminado(true);
/*  722: 738 */         this.servicioImpuestoProductoFacturaCliente.eliminar(ipfc);
/*  723:     */       }
/*  724: 740 */       this.servicioDetalleFacturaCliente.eliminar(dfc);
/*  725:     */     }
/*  726: 742 */     if ((entidad.getFacturaClienteSRI() != null) && (!entidad.getFacturaClienteSRI().isEliminado()))
/*  727:     */     {
/*  728: 743 */       entidad.getFacturaClienteSRI().setEliminado(true);
/*  729: 744 */       this.servicioFacturaClienteSRI.eliminar(entidad.getFacturaClienteSRI());
/*  730:     */     }
/*  731: 746 */     if ((entidad != null) && (!entidad.isEliminado()))
/*  732:     */     {
/*  733: 747 */       entidad.setEliminado(true);
/*  734: 748 */       this.facturaClienteDao.eliminar(entidad);
/*  735:     */     }
/*  736: 751 */     if ((entidad != null) && (!entidad.isEliminado()))
/*  737:     */     {
/*  738: 752 */       entidad.setEliminado(true);
/*  739: 753 */       this.facturaClienteDao.eliminar(entidad);
/*  740:     */     }
/*  741: 756 */     Object filters = new HashMap();
/*  742: 757 */     ((HashMap)filters).put("puntoEmision", entidad.getFacturaClienteSRI().getPuntoEmision());
/*  743: 758 */     ((HashMap)filters).put("establecimiento", entidad.getFacturaClienteSRI().getEstablecimiento());
/*  744: 759 */     ((HashMap)filters).put("numeroDesde", entidad.getFacturaClienteSRI().getNumero());
/*  745:     */     
/*  746: 761 */     List<AnuladoSRI> listaAnuladosSRI = this.anuladoSRIDao.obtenerListaCombo("numeroDesde", true, (Map)filters);
/*  747: 762 */     if (listaAnuladosSRI.size() > 0)
/*  748:     */     {
/*  749: 763 */       AnuladoSRI anuladoSRI = (AnuladoSRI)listaAnuladosSRI.get(0);
/*  750: 764 */       anuladoSRI.setEliminado(true);
/*  751: 765 */       this.anuladoSRIDao.eliminar(anuladoSRI);
/*  752:     */     }
/*  753:     */   }
/*  754:     */   
/*  755:     */   public com.asinfo.as2.entities.FacturaCliente buscarPorId(Integer id)
/*  756:     */   {
/*  757: 777 */     return this.facturaClienteDao.buscarPorId(id);
/*  758:     */   }
/*  759:     */   
/*  760:     */   public com.asinfo.as2.entities.FacturaCliente cargarDetalle(int idFacturaCliente)
/*  761:     */     throws ExcepcionAS2Ventas
/*  762:     */   {
/*  763: 787 */     return this.facturaClienteDao.cargarDetalle(idFacturaCliente);
/*  764:     */   }
/*  765:     */   
/*  766:     */   public com.asinfo.as2.entities.FacturaCliente cargarDetalle(int idFacturaCliente, boolean cargarDetalles)
/*  767:     */   {
/*  768: 792 */     return this.facturaClienteDao.cargarDetalle(idFacturaCliente, cargarDetalles);
/*  769:     */   }
/*  770:     */   
/*  771:     */   public com.asinfo.as2.entities.FacturaCliente totalizar(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/*  772:     */     throws ExcepcionAS2Ventas
/*  773:     */   {
/*  774: 803 */     BigDecimal total = BigDecimal.ZERO;
/*  775: 804 */     BigDecimal descuento = BigDecimal.ZERO;
/*  776: 805 */     BigDecimal montoICE = BigDecimal.ZERO;
/*  777: 807 */     for (Iterator localIterator = facturaCliente.getListaDetalleFacturaCliente().iterator(); localIterator.hasNext();)
/*  778:     */     {
/*  779: 807 */       dfc = (DetalleFacturaCliente)localIterator.next();
/*  780:     */       
/*  781: 809 */       BigDecimal porcentajeDescuentoNC = BigDecimal.ZERO;
/*  782: 810 */       if ((facturaCliente.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE) && (dfc.getDetalleFacturaClientePadre() != null) && 
/*  783: 811 */         (dfc.getDetalleFacturaClientePadre().getDescuentoLineaNC().compareTo(BigDecimal.ZERO) > 0)) {
/*  784: 813 */         porcentajeDescuentoNC = dfc.getDetalleFacturaClientePadre().getDescuentoLineaNC().multiply(new BigDecimal(100)).divide(dfc.getDetalleFacturaClientePadre().getPrecioLinea(), 6, RoundingMode.HALF_UP);
/*  785:     */       }
/*  786: 816 */       dfc.setDescuento(dfc.getPrecio().multiply(dfc.getPorcentajeDescuento().add(porcentajeDescuentoNC)).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
/*  787: 819 */       if (dfc.isIndicadorManejoPeso())
/*  788:     */       {
/*  789: 820 */         dfc.setPrecioLinea(dfc.getPrecio().multiply(dfc.getPeso()).setScale(8, RoundingMode.HALF_UP));
/*  790:     */         
/*  791:     */ 
/*  792: 823 */         dfc.setDescuentoLinea(dfc.getDescuento().multiply(dfc.getPeso()).setScale(2, RoundingMode.HALF_UP));
/*  793:     */       }
/*  794:     */       else
/*  795:     */       {
/*  796: 825 */         dfc.setPrecioLinea(dfc.getPrecio().multiply(dfc.getCantidad()).setScale(8, RoundingMode.HALF_UP));
/*  797:     */         
/*  798:     */ 
/*  799: 828 */         dfc.setDescuentoLinea(dfc.getDescuento().multiply(dfc.getCantidad()).setScale(2, RoundingMode.HALF_UP));
/*  800:     */       }
/*  801: 832 */       if ((dfc.getFacturaCliente().getDocumento() != null) && (dfc.getFacturaCliente().getDocumento().isIndicadorDocumentoTributario())) {
/*  802: 833 */         if (dfc.isIndicadorPorcentajeIce()) {
/*  803: 834 */           dfc.setIceLinea(FuncionesUtiles.porcentaje(dfc.getPrecioLinea().subtract(dfc.getDescuentoLinea()), dfc.getIce()));
/*  804:     */         } else {
/*  805: 836 */           dfc.setIceLinea(FuncionesUtiles.redondearBigDecimal(dfc.getCantidad().multiply(dfc.getIce()), 2));
/*  806:     */         }
/*  807:     */       }
/*  808: 840 */       if (!dfc.isEliminado())
/*  809:     */       {
/*  810: 841 */         total = total.add(dfc.getPrecioLinea());
/*  811: 842 */         descuento = descuento.add(dfc.getDescuentoLinea());
/*  812: 843 */         if ((dfc.getFacturaCliente().getDocumento() != null) && 
/*  813: 844 */           (dfc.getFacturaCliente().getDocumento().isIndicadorDocumentoTributario())) {
/*  814: 845 */           montoICE = montoICE.add(dfc.getIceLinea());
/*  815:     */         }
/*  816:     */       }
/*  817:     */     }
/*  818:     */     DetalleFacturaCliente dfc;
/*  819: 850 */     if (!facturaCliente.getListaDetalleFacturaClienteComercializadora().isEmpty())
/*  820:     */     {
/*  821: 852 */       facturaCliente.setValorOtros(BigDecimal.ZERO);
/*  822: 853 */       BigDecimal totalValorOtros = BigDecimal.ZERO;
/*  823: 854 */       for (DetalleFacturaClienteComercializadora dfcc : facturaCliente.getListaDetalleFacturaClienteComercializadora()) {
/*  824: 855 */         if (!dfcc.isEliminado()) {
/*  825: 856 */           totalValorOtros = totalValorOtros.add(dfcc.getValor());
/*  826:     */         }
/*  827:     */       }
/*  828: 859 */       facturaCliente.setValorOtros(FuncionesUtiles.redondearBigDecimal(totalValorOtros, 2));
/*  829:     */     }
/*  830: 862 */     facturaCliente = totalizarImpuesto(facturaCliente);
/*  831: 863 */     if (facturaCliente.getDocumento().isIndicadorDocumentoExterior()) {
/*  832: 864 */       total = total.add(facturaCliente.getFleteInternacional().add(facturaCliente
/*  833: 865 */         .getGastosAduaneros().add(facturaCliente.getOtrosGastosTransporte().add(facturaCliente.getSeguroInternacional()))));
/*  834:     */     }
/*  835: 867 */     facturaCliente.setTotal(FuncionesUtiles.redondearBigDecimal(total));
/*  836: 868 */     facturaCliente.setDescuento(FuncionesUtiles.redondearBigDecimal(descuento));
/*  837: 869 */     if (facturaCliente.getFacturaClienteSRI() != null) {
/*  838: 870 */       facturaCliente.getFacturaClienteSRI().setMontoIce(FuncionesUtiles.redondearBigDecimal(montoICE));
/*  839:     */     }
/*  840: 873 */     return facturaCliente;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public List<CuentaPorCobrar> obtenerFacturasPendientes(int idEmpresa, int idFacturaCliente)
/*  844:     */   {
/*  845: 879 */     return obtenerFacturasPendientes(idEmpresa, idFacturaCliente, null);
/*  846:     */   }
/*  847:     */   
/*  848:     */   public List<CuentaPorCobrar> obtenerFacturasPendientes(int idEmpresa, int idFacturaCliente, String numeroFactura)
/*  849:     */   {
/*  850: 889 */     return this.facturaClienteDao.obtenerFacturasPendientes(idEmpresa, idFacturaCliente, numeroFactura);
/*  851:     */   }
/*  852:     */   
/*  853:     */   public List<CuentaPorCobrar> obtenerFacturasPendientes(int idEmpresa)
/*  854:     */   {
/*  855: 899 */     return obtenerFacturasPendientes(idEmpresa, 0);
/*  856:     */   }
/*  857:     */   
/*  858:     */   public com.asinfo.as2.entities.FacturaCliente totalizarImpuesto(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/*  859:     */     throws ExcepcionAS2Ventas
/*  860:     */   {
/*  861: 909 */     BigDecimal totalBaseImponible = BigDecimal.ZERO;
/*  862: 910 */     BigDecimal totalImpuestoProducto = BigDecimal.ZERO;
/*  863: 911 */     BigDecimal totalDescuentoImpuestoProductoTotal = BigDecimal.ZERO;
/*  864:     */     
/*  865: 913 */     BigDecimal totalSubsidio = BigDecimal.ZERO;
/*  866: 914 */     boolean setTotalSubsidio = false;
/*  867:     */     
/*  868:     */ 
/*  869:     */ 
/*  870: 918 */     BigDecimal porcentajeDescuento = facturaCliente.getSucursal().getCompensacionSolidaria();
/*  871:     */     com.asinfo.as2.entities.FacturaCliente fcAux;
/*  872: 919 */     if ((DocumentoBase.NOTA_CREDITO_CLIENTE.equals(facturaCliente.getDocumento().getDocumentoBase())) || 
/*  873: 920 */       (DocumentoBase.NOTA_DEBITO_CLIENTE.equals(facturaCliente.getDocumento().getDocumentoBase())) || 
/*  874: 921 */       (DocumentoBase.DEVOLUCION_CLIENTE.equals(facturaCliente.getDocumento().getDocumentoBase()))) {
/*  875: 923 */       if (facturaCliente.getFacturaClientePadre() != null)
/*  876:     */       {
/*  877: 924 */         fcAux = this.facturaClienteDao.buscarPorId(Integer.valueOf(facturaCliente.getFacturaClientePadre().getId()));
/*  878: 925 */         porcentajeDescuento = fcAux.getSucursal().getCompensacionSolidaria();
/*  879:     */       }
/*  880:     */     }
/*  881: 930 */     for (DetalleFacturaCliente dfc : facturaCliente.getListaDetalleFacturaCliente()) {
/*  882: 931 */       if (!dfc.isEliminado())
/*  883:     */       {
/*  884: 932 */         totalBaseImponible = totalBaseImponible.add(dfc.getBaseImponible());
/*  885:     */         
/*  886: 934 */         BigDecimal impuestoProductoLinea = dfc.getValorImpuestosLinea();
/*  887: 935 */         BigDecimal impuestoTributarioProductoLinea = dfc.getValorImpuestosTributariosLinea();
/*  888: 936 */         totalImpuestoProducto = totalImpuestoProducto.add(impuestoProductoLinea);
/*  889: 941 */         if (FuncionesUtiles.redondearBigDecimal(impuestoTributarioProductoLinea, 8).compareTo(BigDecimal.ZERO) > 0)
/*  890:     */         {
/*  891: 942 */           BigDecimal valorDescuentoImpuestoLinea = FuncionesUtiles.porcentaje(dfc.getBaseImponible(), porcentajeDescuento, 10);
/*  892: 943 */           totalDescuentoImpuestoProductoTotal = totalDescuentoImpuestoProductoTotal.add(valorDescuentoImpuestoLinea);
/*  893:     */         }
/*  894: 947 */         if ((dfc.getProducto().getSubsidio().compareTo(BigDecimal.ZERO) != 0) && (dfc.getFacturaCliente().getFacturaClienteSRI() != null))
/*  895:     */         {
/*  896: 948 */           dfc.setSubsidio(dfc.getProducto().getSubsidio());
/*  897: 949 */           BigDecimal ahorroPorSubsidioSinImpuesto = dfc.getCantidad().multiply(dfc.getSubsidio());
/*  898: 950 */           BigDecimal impuesto = new BigDecimal(0);
/*  899: 953 */           if (dfc.getListaImpuestoProductoFacturaCliente().size() > 0) {
/*  900: 954 */             for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente())
/*  901:     */             {
/*  902: 955 */               BigDecimal porcentajeImpuesto = ipfc.getPorcentajeImpuesto().divide(new BigDecimal(100));
/*  903: 956 */               if (ipfc.getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_PRECIO)) {
/*  904: 958 */                 impuesto = impuesto.add(ahorroPorSubsidioSinImpuesto.multiply(porcentajeImpuesto));
/*  905:     */               }
/*  906:     */             }
/*  907:     */           }
/*  908: 962 */           totalSubsidio = totalSubsidio.add(ahorroPorSubsidioSinImpuesto.add(impuesto));
/*  909: 963 */           setTotalSubsidio = true;
/*  910:     */         }
/*  911:     */       }
/*  912:     */     }
/*  913: 969 */     facturaCliente.setBaseImponible(FuncionesUtiles.redondearBigDecimal(totalBaseImponible));
/*  914: 970 */     facturaCliente.setImpuesto(FuncionesUtiles.redondearBigDecimal(totalImpuestoProducto));
/*  915:     */     
/*  916: 972 */     facturaCliente.setDescuentoImpuesto(FuncionesUtiles.redondearBigDecimal(totalDescuentoImpuestoProductoTotal));
/*  917: 974 */     if (setTotalSubsidio) {
/*  918: 975 */       facturaCliente.getFacturaClienteSRI().setTotalSubsidio(FuncionesUtiles.redondearBigDecimal(totalSubsidio, 2));
/*  919:     */     }
/*  920: 978 */     return facturaCliente;
/*  921:     */   }
/*  922:     */   
/*  923:     */   public DetalleFacturaCliente getDetalleFacturaPedidoCliente(DetallePedidoCliente detallePedidoCliente)
/*  924:     */     throws ExcepcionAS2Ventas
/*  925:     */   {
/*  926: 990 */     DetalleFacturaCliente detalleFacturaCliente = new DetalleFacturaCliente();
/*  927:     */     
/*  928: 992 */     detalleFacturaCliente.setDescripcion(detallePedidoCliente.getDescripcion());
/*  929: 993 */     detalleFacturaCliente.setDescuento(detallePedidoCliente.getDescuento());
/*  930: 994 */     detalleFacturaCliente.setDetallePedidoCliente(detallePedidoCliente);
/*  931: 995 */     detalleFacturaCliente.setIdOrganizacion(detallePedidoCliente.getIdOrganizacion());
/*  932: 996 */     detalleFacturaCliente.setIdSucursal(detallePedidoCliente.getIdSucursal());
/*  933: 997 */     detalleFacturaCliente.setPrecio(detallePedidoCliente.getPrecio());
/*  934: 998 */     detalleFacturaCliente.setProducto(detallePedidoCliente.getProducto());
/*  935:     */     
/*  936:1000 */     return detalleFacturaCliente;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public List<com.asinfo.as2.entities.FacturaCliente> listaFacturasPorDespachar(Estado estado, int idEmpresa)
/*  940:     */   {
/*  941:1011 */     return this.facturaClienteDao.listaFacturasPorDespachar(estado, idEmpresa);
/*  942:     */   }
/*  943:     */   
/*  944:     */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/*  945:     */   public List<com.asinfo.as2.entities.FacturaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  946:     */   {
/*  947:1023 */     return this.facturaClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  948:     */   }
/*  949:     */   
/*  950:     */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/*  951:     */   public List<com.asinfo.as2.entities.FacturaCliente> obtenerListaComboConEqual(String sortField, boolean sortOrder, Map<String, String> filters)
/*  952:     */   {
/*  953:1034 */     if (filters == null) {
/*  954:1035 */       filters = new HashMap();
/*  955:     */     }
/*  956:1037 */     return this.facturaClienteDao.obtenerListaComboConEqual(sortField, sortOrder, filters);
/*  957:     */   }
/*  958:     */   
/*  959:     */   public BigDecimal getSumaImpuestoPorIdFacturaCliente(int idDetalleFacturaCliente)
/*  960:     */   {
/*  961:1047 */     return this.facturaClienteDao.getSumaImpuestoPorIdFacturaCliente(idDetalleFacturaCliente);
/*  962:     */   }
/*  963:     */   
/*  964:     */   public List<Object[]> getReporteFacturaCliente(int idFacturaCliente, TipoOrganizacion tipoOrganizacion, int numeroCopias, boolean indicadorDetallado, boolean indicadorImpreso, int idOrganizacion)
/*  965:     */     throws ExcepcionAS2
/*  966:     */   {
/*  967:1059 */     List<Object[]> lista = this.facturaClienteDao.getReporteFacturaCliente(idFacturaCliente, tipoOrganizacion
/*  968:1060 */       .equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA));
/*  969:     */     
/*  970:     */ 
/*  971:1063 */     List<SelectItem> selItmFormaPagoSRI = DatosSRI.getListaFormaPago(idOrganizacion);
/*  972:1064 */     asignacionFormaPagoSRI(lista, selItmFormaPagoSRI);
/*  973:     */     Object[] o;
/*  974:1066 */     if ((!indicadorDetallado) && (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)))
/*  975:     */     {
/*  976:1068 */       TreeMap<String, Object[]> tmFactura = new TreeMap();
/*  977:1070 */       for (Object[] object : lista)
/*  978:     */       {
/*  979:1071 */         o = (Object[])tmFactura.get((String)object[5] + "~" + object[7]);
/*  980:1072 */         if (o == null)
/*  981:     */         {
/*  982:1073 */           tmFactura.put((String)object[5] + "~" + object[7], object);
/*  983:     */         }
/*  984:     */         else
/*  985:     */         {
/*  986:1075 */           BigDecimal sumaCantidad = ((BigDecimal)o[4]).add((BigDecimal)object[4]);
/*  987:1076 */           o[4] = sumaCantidad;
/*  988:1077 */           o[50] = Integer.valueOf(Integer.parseInt(o[50].toString()) + 1);
/*  989:     */           
/*  990:1079 */           BigDecimal sumaPeso = ((BigDecimal)o[69]).add((BigDecimal)object[69]);
/*  991:1080 */           o[69] = sumaPeso;
/*  992:     */           
/*  993:1082 */           BigDecimal sumaPrecioLinea = ((BigDecimal)o[70]).add((BigDecimal)object[70]);
/*  994:1083 */           o[70] = sumaPrecioLinea;
/*  995:     */         }
/*  996:     */       }
/*  997:1086 */       lista = new ArrayList(tmFactura.values());
/*  998:     */     }
/*  999:     */     StringBuilder sbguia;
/* 1000:1089 */     if (numeroCopias >= 1)
/* 1001:     */     {
/* 1002:1090 */       List<Object[]> listaAux = new ArrayList();
/* 1003:1091 */       Object hmguia = new HashMap();
/* 1004:1092 */       for (int i = 1; i <= numeroCopias; i++) {
/* 1005:1093 */         for (Object[] object : lista)
/* 1006:     */         {
/* 1007:1095 */           String tmpnumguia = (String)object[23];
/* 1008:1097 */           if ((tmpnumguia != null) && (!tmpnumguia.isEmpty()) && (((HashMap)hmguia).get(tmpnumguia) == null)) {
/* 1009:1098 */             ((HashMap)hmguia).put(tmpnumguia, tmpnumguia);
/* 1010:     */           }
/* 1011:1101 */           int tamanio = object.length;
/* 1012:1102 */           Object[] a = new Object[tamanio];
/* 1013:1103 */           for (int k = 0; k < tamanio; k++) {
/* 1014:1104 */             if (k == 41) {
/* 1015:1105 */               a[k] = Integer.valueOf(i + (indicadorImpreso ? 2 : 0));
/* 1016:     */             } else {
/* 1017:1107 */               a[k] = object[k];
/* 1018:     */             }
/* 1019:     */           }
/* 1020:1110 */           listaAux.add(a);
/* 1021:     */         }
/* 1022:     */       }
/* 1023:1114 */       sbguia = new StringBuilder();
/* 1024:1115 */       for (String tmpstr : ((HashMap)hmguia).values())
/* 1025:     */       {
/* 1026:1116 */         if (sbguia.length() > 0) {
/* 1027:1117 */           sbguia.append(", ");
/* 1028:     */         }
/* 1029:1119 */         sbguia.append(tmpstr);
/* 1030:     */       }
/* 1031:1122 */       o = listaAux.iterator();
/* 1032:1122 */       if (o.hasNext())
/* 1033:     */       {
/* 1034:1122 */         Object[] object = (Object[])o.next();
/* 1035:1123 */         object[23] = sbguia.toString();
/* 1036:     */       }
/* 1037:1126 */       lista = listaAux;
/* 1038:     */     }
/* 1039:1130 */     HashMap<String, String> hmContratos = this.servicioContratoVenta.getContratosEnFactura(idOrganizacion, idFacturaCliente);
/* 1040:1131 */     String contratosAux = " ";
/* 1041:1132 */     for (String string : hmContratos.values()) {
/* 1042:1133 */       contratosAux = contratosAux.trim() + " " + string + "-";
/* 1043:     */     }
/* 1044:1135 */     for (Object[] object : lista) {
/* 1045:1136 */       object[118] = contratosAux;
/* 1046:     */     }
/* 1047:1138 */     return lista;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public void asignacionFormaPagoSRI(List<Object[]> lista, List<SelectItem> selItmFormaPagoSRI)
/* 1051:     */   {
/* 1052:1147 */     for (Iterator localIterator1 = lista.iterator(); localIterator1.hasNext();)
/* 1053:     */     {
/* 1054:1147 */       objeto = (Object[])localIterator1.next();
/* 1055:1148 */       if (objeto[111] != null)
/* 1056:     */       {
/* 1057:1149 */         formaPagoSRI = objeto[111].toString();
/* 1058:1150 */         for (SelectItem formaPago : selItmFormaPagoSRI) {
/* 1059:1151 */           if (formaPagoSRI.equals(formaPago.getValue().toString()))
/* 1060:     */           {
/* 1061:1152 */             objeto[111] = formaPago.getLabel();
/* 1062:1153 */             break;
/* 1063:     */           }
/* 1064:     */         }
/* 1065:     */       }
/* 1066:     */     }
/* 1067:     */     Object[] objeto;
/* 1068:     */     String formaPagoSRI;
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public void anulaFacturaCliente(com.asinfo.as2.entities.FacturaCliente fc)
/* 1072:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2
/* 1073:     */   {
/* 1074:1162 */     anulaFacturaCliente(fc, false);
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1078:     */   public void anulaFacturaCliente(com.asinfo.as2.entities.FacturaCliente fc, boolean indicadorPos)
/* 1079:     */     throws ExcepcionAS2, ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 1080:     */   {
/* 1081:     */     try
/* 1082:     */     {
/* 1083:1174 */       boolean facturaClienteAgil = fc.isFacturaClienteAgil();
/* 1084:1175 */       com.asinfo.as2.entities.FacturaCliente facturaCliente = cargarDetalle(fc.getId());
/* 1085:     */       
/* 1086:1177 */       esEditable(facturaCliente);
/* 1087:     */       
/* 1088:     */ 
/* 1089:1180 */       facturaCliente.setEstado(Estado.ANULADO);
/* 1090:     */       
/* 1091:     */ 
/* 1092:1183 */       this.facturaClienteDao.guardar(facturaCliente);
/* 1093:1188 */       if ((facturaCliente.getDocumento().isIndicadorDocumentoTributario()) && (facturaCliente.getFacturaClienteSRI() != null))
/* 1094:     */       {
/* 1095:1189 */         FacturaClienteSRI facturaClienteSRI = this.servicioFacturaClienteSRI.buscarFacturaClienteSRIPorFacturaCliente(facturaCliente);
/* 1096:1190 */         if ((!facturaCliente.getDocumento().isIndicadorDocumentoElectronico()) || (!facturaClienteSRI.getAutorizacion().equals("0000000000"))) {
/* 1097:1191 */           this.servicioAnuladoSRI.anularFacturaCliente(facturaClienteSRI);
/* 1098:     */         }
/* 1099:     */       }
/* 1100:1196 */       if (facturaCliente.getFacturaClienteSRI() != null) {
/* 1101:1197 */         this.servicioFacturaClienteSRI.eliminarFacturaClienteSRI(facturaCliente.getFacturaClienteSRI().getId());
/* 1102:     */       }
/* 1103:1200 */       this.servicioVerificadorVentas.actualizarCantidadPorFacturar(facturaCliente, true);
/* 1104:     */       
/* 1105:     */ 
/* 1106:1203 */       this.servicioVerificadorVentas.actualizarCreditoUtilizado(facturaCliente.getEmpresa().getCliente(), facturaCliente.getTotalFactura(), true);
/* 1107:1205 */       if ((facturaCliente.getDespachoCliente() != null) && (
/* 1108:1206 */         ((ParametrosSistema.isAnulaDespachoAlAnularFacturaClienteAgil(facturaCliente.getIdOrganizacion()).booleanValue()) && (facturaClienteAgil)) || (indicadorPos)))
/* 1109:     */       {
/* 1110:1208 */         DespachoCliente despachoCliente = this.servicioDespachoCliente.cargarDetalle(Integer.valueOf(facturaCliente.getDespachoCliente().getId()));
/* 1111:1209 */         this.servicioDespachoCliente.anular(despachoCliente, new Date());
/* 1112:     */       }
/* 1113:1212 */       liberarDespachos(facturaCliente);
/* 1114:1213 */       if (facturaCliente.getDespachoCliente() != null) {
/* 1115:1214 */         facturaCliente.setDespachoCliente(null);
/* 1116:     */       }
/* 1117:1218 */       if (!facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.NOTA_DEBITO_CLIENTE)) {
/* 1118:1219 */         this.servicioContratoVenta.liberarValoresDevengados(facturaCliente);
/* 1119:     */       }
/* 1120:1223 */       if (facturaCliente.getPedidoCliente() != null)
/* 1121:     */       {
/* 1122:1224 */         facturaCliente.setPedidoCliente(null);
/* 1123:1225 */         this.facturaClienteDao.guardar(facturaCliente);
/* 1124:     */       }
/* 1125:1229 */       this.comprobanteElectronicoPendienteSRIDao.eliminarComprobanteElectronicoPendienteSRI(facturaCliente, null, null);
/* 1126:1231 */       if (facturaCliente.getAsiento() != null)
/* 1127:     */       {
/* 1128:1232 */         facturaCliente.getAsiento().setIndicadorAutomatico(false);
/* 1129:1233 */         this.servicioAsiento.anular(facturaCliente.getAsiento());
/* 1130:     */       }
/* 1131:     */     }
/* 1132:     */     catch (ExcepcionAS2Ventas e)
/* 1133:     */     {
/* 1134:1237 */       this.context.setRollbackOnly();
/* 1135:1238 */       throw e;
/* 1136:     */     }
/* 1137:     */     catch (ExcepcionAS2Financiero e)
/* 1138:     */     {
/* 1139:1240 */       this.context.setRollbackOnly();
/* 1140:1241 */       throw e;
/* 1141:     */     }
/* 1142:     */     catch (Exception e)
/* 1143:     */     {
/* 1144:1243 */       e.printStackTrace();
/* 1145:1244 */       this.context.setRollbackOnly();
/* 1146:1245 */       LOG.error(e);
/* 1147:1246 */       throw new ExcepcionAS2(e);
/* 1148:     */     }
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public void actualizaFacturaClienteSRI(int idFacturaCliente, Estado estadoFactura, EstadoDocumentoElectronico estadoSRI, Date fechaAutorizacion, String numeroAutorizacion, String mensajeSRI)
/* 1152:     */   {
/* 1153:1253 */     this.facturaClienteDao.actualizaFacturaClienteSRI(idFacturaCliente, estadoFactura, estadoSRI, fechaAutorizacion, numeroAutorizacion, mensajeSRI);
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public void validarBodega(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1157:     */     throws ExcepcionAS2
/* 1158:     */   {
/* 1159:1263 */     for (DetalleFacturaCliente dfc : facturaCliente.getListaDetalleFacturaCliente()) {
/* 1160:1264 */       if ((!dfc.isEliminado()) && (dfc.getDetalleDespachoCliente() != null) && (dfc.getDetalleDespachoCliente().getBodega() == null))
/* 1161:     */       {
/* 1162:1266 */         String strMensaje = dfc.getProducto().getCodigo() + " " + dfc.getProducto().getNombre();
/* 1163:1267 */         throw new ExcepcionAS2("msg_producto_bodega_venta", strMensaje);
/* 1164:     */       }
/* 1165:     */     }
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   private void validar(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1169:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 1170:     */   {
/* 1171:1274 */     this.servicioPeriodo.buscarPorFecha(facturaCliente.getFecha(), facturaCliente.getIdOrganizacion(), facturaCliente
/* 1172:1275 */       .getDocumento().getDocumentoBase());
/* 1173:1277 */     if ((facturaCliente.getIdFacturaCliente() == 0) && (facturaCliente.getDespachoCliente() != null))
/* 1174:     */     {
/* 1175:1278 */       com.asinfo.as2.entities.FacturaCliente facturaClienteExistente = buscarPorDespachoCliente(Integer.valueOf(facturaCliente.getDespachoCliente().getIdDespachoCliente()));
/* 1176:1279 */       if (facturaClienteExistente != null) {
/* 1177:1280 */         throw new ExcepcionAS2Ventas("msg_info_ya_existe_factura_despacho", facturaClienteExistente.getNumero());
/* 1178:     */       }
/* 1179:     */     }
/* 1180:1284 */     if (verificaExistenciaNumero(facturaCliente) > 0L) {
/* 1181:1285 */       throw new ExcepcionAS2Ventas("msg_error_numero_duplicado", " " + facturaCliente.getNumero());
/* 1182:     */     }
/* 1183:1288 */     boolean facturaDesdePrefactura = false;
/* 1184:1289 */     BigDecimal totalPrefactura = BigDecimal.ZERO;
/* 1185:1290 */     for (PrefacturaCliente prefactura : facturaCliente.getListaPrefacturaCliente()) {
/* 1186:1291 */       if (!prefactura.isEliminado())
/* 1187:     */       {
/* 1188:1292 */         facturaDesdePrefactura = true;
/* 1189:1293 */         totalPrefactura = totalPrefactura.add(prefactura.getTotal().subtract(prefactura.getDescuento()));
/* 1190:     */       }
/* 1191:     */     }
/* 1192:1302 */     if (facturaDesdePrefactura)
/* 1193:     */     {
/* 1194:1303 */       BigDecimal diferencia = facturaCliente.getTotal().subtract(facturaCliente.getDescuento()).subtract(totalPrefactura);
/* 1195:1309 */       if (diferencia.abs().compareTo(new BigDecimal(0.05D)) > 0) {
/* 1196:1310 */         throw new ExcepcionAS2Ventas("msg_error_valor_factura_prefactura", " " + facturaCliente.getNumero());
/* 1197:     */       }
/* 1198:     */     }
/* 1199:1314 */     if ((facturaCliente.getDocumento().isIndicadorDocumentoTributario()) && 
/* 1200:1315 */       (facturaCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() == null)) {
/* 1201:1317 */       if (facturaCliente.getDocumento().isIndicadorDocumentoElectronico())
/* 1202:     */       {
/* 1203:1318 */         if ((facturaCliente.getEmail() == null) || (facturaCliente.getEmail().isEmpty())) {
/* 1204:1320 */           throw new ExcepcionAS2Financiero("msgs_error_email_documento_electronico", " " + facturaCliente.getEmpresa().getNombreFiscal());
/* 1205:     */         }
/* 1206:     */       }
/* 1207:     */       else
/* 1208:     */       {
/* 1209:1326 */         int desde = facturaCliente.getDocumento().getSecuencia().getDesde();
/* 1210:1327 */         int hasta = facturaCliente.getDocumento().getSecuencia().getHasta();
/* 1211:     */         
/* 1212:1329 */         String[] numeroFactura = facturaCliente.getNumero().split("-");
/* 1213:1330 */         int numero = Integer.valueOf(numeroFactura[2]).intValue();
/* 1214:1332 */         if ((numero < desde) || (numero > hasta)) {
/* 1215:1334 */           throw new ExcepcionAS2Financiero("msg_error_numero_factura_fuera_rango", " " + desde + " - " + hasta + " ( " + facturaCliente.getNumero() + " ) ");
/* 1216:     */         }
/* 1217:     */       }
/* 1218:     */     }
/* 1219:1341 */     if (facturaCliente.getTotal().compareTo(BigDecimal.ZERO) == 0) {
/* 1220:1342 */       throw new ExcepcionAS2Ventas("msg_error_movimiento_total_cero");
/* 1221:     */     }
/* 1222:1346 */     if (!facturaCliente.isIndicadorSaldoInicial())
/* 1223:     */     {
/* 1224:1349 */       documentoBase = facturaCliente.getDocumento().getDocumentoBase();
/* 1225:1350 */       PedidoCliente pedidoCiente = facturaCliente.getDespachoCliente() == null ? facturaCliente.getPedidoCliente() : facturaCliente.getDespachoCliente().getPedidoCliente();
/* 1226:1352 */       if (((((DocumentoBase)documentoBase).equals(DocumentoBase.FACTURA_CLIENTE)) || (((DocumentoBase)documentoBase).equals(DocumentoBase.NOTA_DEBITO_CLIENTE))) && (pedidoCiente == null))
/* 1227:     */       {
/* 1228:1353 */         this.servicioVerificadorVentas.verificarBloqueoCliente(facturaCliente.getEmpresa().getCliente(), facturaCliente.getFecha());
/* 1229:     */         try
/* 1230:     */         {
/* 1231:1360 */           this.servicioVerificadorVentas.verificarCupoCredito(facturaCliente.getEmpresa().getCliente(), facturaCliente.getTotalFactura(), facturaCliente.isIndicadorAutorizaVenta());
/* 1232:     */         }
/* 1233:     */         catch (ExcepcionAS2Ventas e)
/* 1234:     */         {
/* 1235:1363 */           if (facturaCliente.getIdFacturaCliente() != 0)
/* 1236:     */           {
/* 1237:1364 */             BigDecimal creditoUtiulizado = this.facturaClienteDao.totalFacturaCliente(facturaCliente.getIdFacturaCliente());
/* 1238:1365 */             this.servicioVerificadorVentas.actualizarCreditoUtilizado(facturaCliente.getEmpresa().getCliente(), creditoUtiulizado, false);
/* 1239:     */           }
/* 1240:1367 */           throw e;
/* 1241:     */         }
/* 1242:     */       }
/* 1243:     */     }
/* 1244:1371 */     for (Object documentoBase = facturaCliente.getListaDetalleFacturaCliente().iterator(); ((Iterator)documentoBase).hasNext();)
/* 1245:     */     {
/* 1246:1371 */       DetalleFacturaCliente detalleFacturaCliente = (DetalleFacturaCliente)((Iterator)documentoBase).next();
/* 1247:1372 */       if ((!detalleFacturaCliente.isEliminado()) && (AppUtil.getOrganizacion() != null)) {
/* 1248:1374 */         if ((AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) && 
/* 1249:1375 */           (detalleFacturaCliente.getPrecioLinea().compareTo(BigDecimal.ZERO) == 0)) {
/* 1250:1377 */           throw new ExcepcionAS2Ventas("msg_error_precio_linea_factura", " Producto: " + detalleFacturaCliente.getProducto().getCodigo());
/* 1251:     */         }
/* 1252:     */       }
/* 1253:     */     }
/* 1254:1381 */     BigDecimal totalFactura = facturaCliente.getTotalFactura().add(facturaCliente.getValorOtros());
/* 1255:1382 */     if (facturaCliente.getTotalCuentaPorCobrar().compareTo(totalFactura) != 0) {
/* 1256:1383 */       throw new ExcepcionAS2Ventas("msg_error_diferencia_forma_pago");
/* 1257:     */     }
/* 1258:1386 */     if ((facturaCliente.getDocumento() != null) && (facturaCliente.getDocumento().isIndicadorDocumentoExterior()) && (
/* 1259:1387 */       (facturaCliente.getReferencia2() == null) || (facturaCliente.getReferencia2().isEmpty())))
/* 1260:     */     {
/* 1261:1388 */       String mensajeErrorDatosExportacion = "";
/* 1262:1389 */       if ((facturaCliente.getIncoterm() == null) || (facturaCliente.getIncoterm().isEmpty())) {
/* 1263:1390 */         mensajeErrorDatosExportacion = mensajeErrorDatosExportacion + "Incoterm,";
/* 1264:     */       }
/* 1265:1391 */       if (facturaCliente.getLugarIncoterm() == null) {
/* 1266:1392 */         mensajeErrorDatosExportacion = mensajeErrorDatosExportacion + "Lugar Incoterm,";
/* 1267:     */       }
/* 1268:1393 */       if (facturaCliente.getPaisOrigen() == null) {
/* 1269:1394 */         mensajeErrorDatosExportacion = mensajeErrorDatosExportacion + "Pais Origen,";
/* 1270:     */       }
/* 1271:1395 */       if (facturaCliente.getPuertoEmbarque() == null) {
/* 1272:1396 */         mensajeErrorDatosExportacion = mensajeErrorDatosExportacion + "Puerto Embarque,";
/* 1273:     */       }
/* 1274:1397 */       if (facturaCliente.getPaisDestino() == null) {
/* 1275:1398 */         mensajeErrorDatosExportacion = mensajeErrorDatosExportacion + "Pais Destino,";
/* 1276:     */       }
/* 1277:1399 */       if (facturaCliente.getPuertoDestino() == null) {
/* 1278:1400 */         mensajeErrorDatosExportacion = mensajeErrorDatosExportacion + "Puerto Destino";
/* 1279:     */       }
/* 1280:1401 */       if (mensajeErrorDatosExportacion.length() > 0)
/* 1281:     */       {
/* 1282:1402 */         if (mensajeErrorDatosExportacion.charAt(mensajeErrorDatosExportacion.length() - 1) == ',') {
/* 1283:1403 */           mensajeErrorDatosExportacion = mensajeErrorDatosExportacion.substring(0, mensajeErrorDatosExportacion.length() - 1);
/* 1284:     */         }
/* 1285:1405 */         throw new ExcepcionAS2Financiero("msg_error_campo_requerido_factura_exportacion", " : " + mensajeErrorDatosExportacion);
/* 1286:     */       }
/* 1287:     */     }
/* 1288:1411 */     if ((!OrigenEnum.POS_WEB.equals(facturaCliente.getOrigen())) && (facturaCliente.getDespachoCliente() != null) && (facturaCliente.getDespachoCliente().getId() == 0) && 
/* 1289:1412 */       (facturaCliente.getCondicionPago().getDiasPlazo() + facturaCliente.getCondicionPago().getMesesPlazo() == 0))
/* 1290:     */     {
/* 1291:1415 */       BigDecimal valorCobro = facturaCliente.getCobro() != null ? facturaCliente.getCobro().getValor() : BigDecimal.ZERO;
/* 1292:1416 */       if (facturaCliente.getTotalFactura().compareTo(valorCobro) != 0)
/* 1293:     */       {
/* 1294:1417 */         String mensajeError = facturaCliente.getTotalFactura() + " != " + valorCobro;
/* 1295:1418 */         throw new ExcepcionAS2Ventas("msg_error_valor_cobro_vs_factura", " : " + mensajeError);
/* 1296:     */       }
/* 1297:     */     }
/* 1298:1422 */     if ((!OrigenEnum.POS_WEB.equals(facturaCliente.getOrigen())) && (facturaCliente.getDespachoCliente() != null) && (facturaCliente.getDespachoCliente().getId() == 0) && 
/* 1299:1423 */       (facturaCliente.getCondicionPago().getDiasPlazo() + facturaCliente.getCondicionPago().getMesesPlazo() == 0))
/* 1300:     */     {
/* 1301:1426 */       BigDecimal valorCobro = facturaCliente.getCobro() != null ? facturaCliente.getCobro().getValor() : BigDecimal.ZERO;
/* 1302:1427 */       if (facturaCliente.getTotalFactura().compareTo(valorCobro) != 0)
/* 1303:     */       {
/* 1304:1428 */         String mensajeError = facturaCliente.getTotalFactura() + " != " + valorCobro;
/* 1305:1429 */         throw new ExcepcionAS2Ventas("msg_error_valor_cobro_vs_factura", " : " + mensajeError);
/* 1306:     */       }
/* 1307:     */     }
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public void actualizarEstado(int idFacturaCliente, Estado estado, MotivoAnulacion motivoAnulacion)
/* 1311:     */   {
/* 1312:1441 */     this.facturaClienteDao.actualizarEstado(idFacturaCliente, estado, motivoAnulacion);
/* 1313:     */   }
/* 1314:     */   
/* 1315:     */   public void actualizarValorDevuelto(int idFacturaCliente, BigDecimal valorDevuelto)
/* 1316:     */   {
/* 1317:1446 */     this.facturaClienteDao.actualizarValorDevuelto(idFacturaCliente, valorDevuelto);
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public void esEditable(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1321:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 1322:     */   {
/* 1323:1457 */     this.servicioPeriodo.buscarPorFecha(facturaCliente.getFecha(), facturaCliente.getIdOrganizacion(), facturaCliente
/* 1324:1458 */       .getDocumento().getDocumentoBase());
/* 1325:1460 */     if ((facturaCliente.getFacturaClienteSRI() != null) && (facturaCliente.getFacturaClienteSRI().isIndicadorDocumentoElectronico()) && 
/* 1326:1461 */       (facturaCliente.getEstado().equals(Estado.EN_ESPERA))) {
/* 1327:1463 */       throw new ExcepcionAS2Ventas("msg_error_anular", " Documento Electrónico | " + facturaCliente.getEstado());
/* 1328:     */     }
/* 1329:1466 */     if (facturaCliente.getEstado() == Estado.ANULADO) {
/* 1330:1468 */       throw new ExcepcionAS2Ventas("msg_error_anular", " " + Estado.ANULADO.getNombre());
/* 1331:     */     }
/* 1332:1471 */     if ((facturaCliente.getEstado() == Estado.CONTABILIZADO) && (facturaCliente.getAsiento() == null)) {
/* 1333:1474 */       throw new ExcepcionAS2Ventas("msg_error_anular", " " + Estado.CONTABILIZADO.getNombre());
/* 1334:     */     }
/* 1335:1479 */     int diasEdicion = ParametrosSistema.getDiasMaximosEdicionFactura(facturaCliente.getIdOrganizacion()).intValue();
/* 1336:1480 */     Date fecha = FuncionesUtiles.sumarFechaDiasMeses(facturaCliente.getFecha(), diasEdicion);
/* 1337:1481 */     if (fecha.before(new Date())) {
/* 1338:1482 */       throw new ExcepcionAS2Ventas("msg_error_anular", " " + Parametro.DIAS_MAXIMOS_EDICION_FACTURA.getNombre());
/* 1339:     */     }
/* 1340:1486 */     List<CuentaPorCobrar> lista = this.facturaClienteDao.obtenerCuotasCobradas(facturaCliente.getId());
/* 1341:1487 */     if ((lista.size() > 0) && (
/* 1342:1488 */       (facturaCliente.getFacturaClienteSRI() == null) || (facturaCliente.getFacturaClienteSRI().getGenerarDocumentoElectronico().booleanValue()))) {
/* 1343:1492 */       throw new ExcepcionAS2Ventas("msg_error_existen_cobros_para_factura");
/* 1344:     */     }
/* 1345:1496 */     lista = this.facturaClienteDao.obtenerProtestos(facturaCliente.getId());
/* 1346:1497 */     if (lista.size() > 0) {
/* 1347:1499 */       throw new ExcepcionAS2Ventas("msg_error_existe_cheque_protesto_factura", " : " + facturaCliente.getNumero());
/* 1348:     */     }
/* 1349:     */   }
/* 1350:     */   
/* 1351:     */   public void obtenerImpuestosProductos(Producto producto, DetalleFacturaCliente d)
/* 1352:     */     throws ExcepcionAS2Inventario
/* 1353:     */   {
/* 1354:1512 */     producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/* 1355:     */     
/* 1356:1514 */     List<RangoImpuesto> listaRangoImpuesto = new ArrayList();
/* 1357:1518 */     if (d.getFacturaCliente().getFacturaClientePadre() != null) {
/* 1358:1519 */       listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, d.getFacturaCliente().getFacturaClientePadre().getFecha());
/* 1359:     */     } else {
/* 1360:1522 */       listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, d.getFacturaCliente().getFecha());
/* 1361:     */     }
/* 1362:1525 */     for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 1363:     */     {
/* 1364:1527 */       ImpuestoProductoFacturaCliente impuestoProductoFacturaCliente = new ImpuestoProductoFacturaCliente();
/* 1365:1528 */       impuestoProductoFacturaCliente.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 1366:1529 */       impuestoProductoFacturaCliente.setImpuesto(rangoImpuesto.getImpuesto());
/* 1367:1530 */       impuestoProductoFacturaCliente.setDetalleFacturaCliente(d);
/* 1368:1531 */       d.getListaImpuestoProductoFacturaCliente().add(impuestoProductoFacturaCliente);
/* 1369:     */     }
/* 1370:     */   }
/* 1371:     */   
/* 1372:     */   public int contarPorCriterio(Map<String, String> filters)
/* 1373:     */   {
/* 1374:1542 */     return this.facturaClienteDao.contarPorCriterio(filters);
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   public long verificaExistenciaNumero(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1378:     */   {
/* 1379:1552 */     return this.facturaClienteDao.verificaExistenciaNumero(facturaCliente);
/* 1380:     */   }
/* 1381:     */   
/* 1382:     */   public com.asinfo.as2.entities.FacturaCliente buscarPorDespachoCliente(Integer idDespachoCliente)
/* 1383:     */   {
/* 1384:1562 */     return this.facturaClienteDao.buscarPorDespachoCliente(idDespachoCliente);
/* 1385:     */   }
/* 1386:     */   
/* 1387:     */   public com.asinfo.as2.entities.FacturaCliente cargarCabecera(Integer idFacturaCliente)
/* 1388:     */   {
/* 1389:1572 */     return this.facturaClienteDao.cargarDetalleADespachar(idFacturaCliente);
/* 1390:     */   }
/* 1391:     */   
/* 1392:     */   public com.asinfo.as2.entities.FacturaCliente buscarFacturaCliente(Map<String, String> filters)
/* 1393:     */     throws ExcepcionAS2
/* 1394:     */   {
/* 1395:1582 */     return this.facturaClienteDao.buscarFacturaCliente(filters);
/* 1396:     */   }
/* 1397:     */   
/* 1398:     */   public void actualizarNumeroFactura(FacturaClienteSRI facturaClienteSRI)
/* 1399:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 1400:     */   {
/* 1401:1587 */     validarCambioNumeroFactura(facturaClienteSRI.getFacturaCliente());
/* 1402:1588 */     com.asinfo.as2.entities.FacturaCliente f = facturaClienteSRI.getFacturaCliente();
/* 1403:1589 */     facturaClienteSRI.getFacturaCliente().setNumero(facturaClienteSRI
/* 1404:1590 */       .getEstablecimiento() + "-" + facturaClienteSRI.getPuntoEmision() + "-" + facturaClienteSRI.getTraNumeroNuevo());
/* 1405:1591 */     facturaClienteSRI.setNumero(facturaClienteSRI.getTraNumeroNuevo());
/* 1406:1592 */     this.facturaClienteSRIDao.guardar(facturaClienteSRI);
/* 1407:1593 */     this.facturaClienteDao.guardar(f);
/* 1408:     */   }
/* 1409:     */   
/* 1410:     */   public void validarCambioNumeroFactura(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1411:     */     throws ExcepcionAS2Ventas
/* 1412:     */   {
/* 1413:1598 */     this.facturaClienteDao.validarCambioNumeroFactura(facturaCliente.getFacturaClienteSRI().getEstablecimiento() + "-" + facturaCliente
/* 1414:1599 */       .getFacturaClienteSRI().getPuntoEmision() + "-" + facturaCliente.getFacturaClienteSRI().getTraNumeroNuevo());
/* 1415:     */   }
/* 1416:     */   
/* 1417:     */   public void liberarDespachos(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1418:     */   {
/* 1419:1610 */     if (facturaCliente.getDespachoCliente() != null) {
/* 1420:1611 */       this.facturaClienteDao.liberarDespachoCliente(facturaCliente);
/* 1421:     */     }
/* 1422:     */   }
/* 1423:     */   
/* 1424:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1425:     */   public List<com.asinfo.as2.entities.FacturaCliente> migracionFacturaCliente(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial)
/* 1426:     */     throws ExcepcionAS2, AS2Exception
/* 1427:     */   {
/* 1428:1625 */     com.asinfo.as2.entities.FacturaCliente facturaCliente = new com.asinfo.as2.entities.FacturaCliente();
/* 1429:     */     
/* 1430:1627 */     HashMap<String, com.asinfo.as2.entities.FacturaCliente> hmFacturaCliente = new HashMap();
/* 1431:1628 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/* 1432:1629 */     HashMap<String, Producto> hmProducto = new HashMap();
/* 1433:1630 */     HashMap<Date, RangoImpuesto> hmRangoImpuesto = new HashMap();
/* 1434:1631 */     HashMap<Integer, CondicionPago> hmCondicionPago = new HashMap();
/* 1435:1632 */     HashMap<String, DireccionEmpresa> hmDireccionEmpresa = new HashMap();
/* 1436:1633 */     HashMap<String, PuntoDeVenta> hmPuntoVenta = new HashMap();
/* 1437:1634 */     HashMap<String, Documento> hmDocumento = new HashMap();
/* 1438:1635 */     HashMap<String, MotivoNotaCreditoCliente> hmMotivoNotaCreditoCliente = new HashMap();
/* 1439:1636 */     List<com.asinfo.as2.entities.FacturaCliente> listaFacturaClientes = new ArrayList();
/* 1440:1637 */     AS2Exception excepcionFacturas = null;
/* 1441:     */     
/* 1442:1639 */     int filaActual = filaInicial;
/* 1443:1640 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 1444:1641 */     int columnaErronea = 0;
/* 1445:     */     try
/* 1446:     */     {
/* 1447:1645 */       datos = FuncionesUtiles.leerExcelFinal(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/* 1448:     */     }
/* 1449:     */     catch (IOException ioe)
/* 1450:     */     {
/* 1451:     */       HSSFCell[][] datos;
/* 1452:1647 */       throw new ExcepcionAS2(ioe);
/* 1453:     */     }
/* 1454:     */     HSSFCell[][] datos;
/* 1455:1650 */     for (HSSFCell[] fila : datos)
/* 1456:     */     {
/* 1457:     */       try
/* 1458:     */       {
/* 1459:1652 */         filaErronea = fila;
/* 1460:1653 */         filaActual++;
/* 1461:     */         
/* 1462:     */ 
/* 1463:1656 */         Date fecha = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaErronea = 0, true, Integer.valueOf(0), Integer.valueOf(0));
/* 1464:1657 */         String nombreDocumento = fila[(columnaErronea = 1)].getStringCellValue().trim();
/* 1465:1658 */         fila[(columnaErronea = 2)].setCellType(1);
/* 1466:1659 */         String identificacion = fila[(columnaErronea = 2)].getStringCellValue().trim();
/* 1467:1660 */         Integer numeroCuotas = Integer.valueOf((int)fila[(columnaErronea = 3)].getNumericCellValue());
/* 1468:1661 */         Integer plazo = Integer.valueOf((int)fila[(columnaErronea = 4)].getNumericCellValue());
/* 1469:1662 */         Date fechaVencimiento = fila[(columnaErronea = 5)] == null ? null : fila[5].getDateCellValue();
/* 1470:1663 */         String establecimiento = fila[(columnaErronea = 6)].getStringCellValue().trim();
/* 1471:1664 */         if ((establecimiento.length() < 3) || (establecimiento.length() >= 4)) {
/* 1472:1666 */           throw new ExcepcionAS2("msg_error_longitud_sucursal", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 1473:     */         }
/* 1474:1668 */         String puntoEmision = fila[(columnaErronea = 7)].getStringCellValue().trim();
/* 1475:1669 */         if ((puntoEmision.length() < 3) || (puntoEmision.length() >= 4)) {
/* 1476:1671 */           throw new ExcepcionAS2("msg_error_longitud_punto_venta", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 1477:     */         }
/* 1478:1673 */         String numeroFactura = fila[(columnaErronea = 8)].getStringCellValue().trim();
/* 1479:1674 */         String nota = fila[(columnaErronea = 9)] != null ? fila[(columnaErronea = 9)].getStringCellValue() : "";
/* 1480:1675 */         String codigoProducto = fila[(columnaErronea = 10)].getStringCellValue().trim();
/* 1481:1676 */         BigDecimal cantidad = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 11)].getNumericCellValue()), 2);
/* 1482:1677 */         BigDecimal precio = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 12)].getNumericCellValue()), 4);
/* 1483:1678 */         BigDecimal descuentoUnitario = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 13)].getNumericCellValue()), 2);
/* 1484:     */         
/* 1485:1680 */         String impuestos = fila[(columnaErronea = 14)].getStringCellValue().trim();
/* 1486:1681 */         boolean indicadorSaldoInicial = fila[(columnaErronea = 15)].getStringCellValue().equalsIgnoreCase("SI");
/* 1487:1682 */         String correo = fila[(columnaErronea = 16)] != null ? fila[(columnaErronea = 16)].getStringCellValue().trim() : "";
/* 1488:     */         
/* 1489:1684 */         columnaErronea = -1;
/* 1490:     */         
/* 1491:     */ 
/* 1492:     */ 
/* 1493:     */ 
/* 1494:1689 */         StringBuilder clave = new StringBuilder();
/* 1495:1690 */         clave.append(identificacion);
/* 1496:1691 */         clave.append(establecimiento);
/* 1497:1692 */         clave.append(puntoEmision);
/* 1498:1693 */         clave.append(numeroFactura);
/* 1499:     */         
/* 1500:     */ 
/* 1501:     */ 
/* 1502:     */ 
/* 1503:1698 */         Empresa empresa = (Empresa)hmEmpresa.get(identificacion);
/* 1504:1699 */         if (empresa == null)
/* 1505:     */         {
/* 1506:1700 */           Map<String, String> filters = new HashMap();
/* 1507:1701 */           filters.put("identificacion", identificacion);
/* 1508:1702 */           filters.put("indicadorCliente", "true");
/* 1509:1703 */           empresa = this.servicioEmpresa.bucarEmpresaPorIdentificacion(filters);
/* 1510:1704 */           empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 1511:1705 */           hmEmpresa.put(identificacion, empresa);
/* 1512:     */         }
/* 1513:1710 */         DireccionEmpresa direccionEmpresa = (DireccionEmpresa)hmDireccionEmpresa.get(identificacion);
/* 1514:1711 */         if (direccionEmpresa == null)
/* 1515:     */         {
/* 1516:1712 */           direccionEmpresa = this.direccionEmpresaDao.buscarPorEmpresa(empresa);
/* 1517:1715 */           if (direccionEmpresa == null) {
/* 1518:1716 */             throw new ExcepcionAS2("msg_error_direccion_empresa", " " + identificacion);
/* 1519:     */           }
/* 1520:1719 */           hmDireccionEmpresa.put(identificacion, direccionEmpresa);
/* 1521:     */         }
/* 1522:1724 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 1523:1725 */         if (producto == null)
/* 1524:     */         {
/* 1525:1726 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 1526:1727 */           hmProducto.put(codigoProducto, producto);
/* 1527:     */         }
/* 1528:1733 */         RangoImpuesto rangoImpuesto = (RangoImpuesto)hmRangoImpuesto.get(fecha);
/* 1529:1734 */         if (rangoImpuesto == null)
/* 1530:     */         {
/* 1531:1735 */           rangoImpuesto = this.servicioImpuesto.getRangoRangoImpuestoTributario(fecha, AppUtil.getOrganizacion().getId());
/* 1532:1736 */           hmRangoImpuesto.put(fecha, rangoImpuesto);
/* 1533:     */         }
/* 1534:1742 */         if (fechaVencimiento != null) {
/* 1535:1743 */           plazo = Integer.valueOf(FuncionesUtiles.diferenciasDeFechas(fecha, fechaVencimiento) + 1);
/* 1536:     */         }
/* 1537:1746 */         CondicionPago condicionPago = (CondicionPago)hmCondicionPago.get(plazo);
/* 1538:1747 */         if (condicionPago == null)
/* 1539:     */         {
/* 1540:1748 */           condicionPago = this.servicioCondicionPago.buscarCondicionPagoPorDiasPlazo(plazo.intValue(), AppUtil.getOrganizacion().getId());
/* 1541:1749 */           hmCondicionPago.put(plazo, condicionPago);
/* 1542:     */         }
/* 1543:1754 */         PuntoDeVenta puntoVenta = (PuntoDeVenta)hmPuntoVenta.get(establecimiento + puntoEmision);
/* 1544:1755 */         if (puntoVenta == null)
/* 1545:     */         {
/* 1546:1756 */           HashMap<String, String> filters = new HashMap();
/* 1547:1757 */           filters.put("sucursal.codigo", establecimiento);
/* 1548:1758 */           filters.put("codigo", puntoEmision);
/* 1549:1759 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 1550:1760 */           List<PuntoDeVenta> listPuntoDeVenta = this.servicioPuntoDeVenta.obtenerListaCombo("", false, filters);
/* 1551:1761 */           if (listPuntoDeVenta.size() > 0)
/* 1552:     */           {
/* 1553:1762 */             puntoVenta = (PuntoDeVenta)listPuntoDeVenta.get(0);
/* 1554:1763 */             hmPuntoVenta.put(establecimiento + puntoEmision, puntoVenta);
/* 1555:     */           }
/* 1556:     */           else
/* 1557:     */           {
/* 1558:1765 */             columnaErronea = 7;
/* 1559:1766 */             throw new AS2Exception("msg_error_punto_factura", new String[] { establecimiento + " - " + puntoEmision });
/* 1560:     */           }
/* 1561:     */         }
/* 1562:1772 */         Documento documento = (Documento)hmDocumento.get(nombreDocumento);
/* 1563:1773 */         if (documento == null)
/* 1564:     */         {
/* 1565:1774 */           HashMap<String, String> filters = new HashMap();
/* 1566:1775 */           filters.put("nombre", nombreDocumento);
/* 1567:1776 */           filters.put("idOrganizacion", "" + idOrganizacion);
/* 1568:1777 */           List<Documento> listDocumentos = this.servicioDocumento.obtenerListaCombo("", false, filters);
/* 1569:1778 */           if (listDocumentos.size() > 0)
/* 1570:     */           {
/* 1571:1779 */             documento = (Documento)listDocumentos.get(0);
/* 1572:1780 */             hmDocumento.put(nombreDocumento, documento);
/* 1573:     */           }
/* 1574:     */           else
/* 1575:     */           {
/* 1576:1782 */             throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioFacturaClienteImpl.NO_EXISTE_DOCUMENTO", new String[] { nombreDocumento, "" + filaActual, "1" });
/* 1577:     */           }
/* 1578:     */         }
/* 1579:1789 */         facturaCliente = (com.asinfo.as2.entities.FacturaCliente)hmFacturaCliente.get(clave.toString());
/* 1580:1790 */         StringBuilder numero = new StringBuilder();
/* 1581:1792 */         if (facturaCliente == null)
/* 1582:     */         {
/* 1583:1794 */           facturaCliente = new com.asinfo.as2.entities.FacturaCliente();
/* 1584:1795 */           facturaCliente.setSucursal(AppUtil.getSucursal());
/* 1585:1796 */           facturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1586:1797 */           facturaCliente.setDocumento(documento);
/* 1587:1798 */           facturaCliente.setEmpresa(empresa);
/* 1588:1799 */           facturaCliente.setFecha(fecha);
/* 1589:1800 */           facturaCliente.setDescuento(BigDecimal.ZERO);
/* 1590:1801 */           facturaCliente.setEstado(Estado.PROCESADO);
/* 1591:1802 */           facturaCliente.setNumeroCuotas(numeroCuotas.intValue());
/* 1592:1803 */           facturaCliente.setCondicionPago(new CondicionPago());
/* 1593:1804 */           facturaCliente.setDireccionEmpresa(direccionEmpresa);
/* 1594:1805 */           facturaCliente.setDescripcion(nota);
/* 1595:1806 */           facturaCliente.setCondicionPago(condicionPago);
/* 1596:1807 */           facturaCliente.setFechaVencimiento(fechaVencimiento);
/* 1597:1808 */           facturaCliente.setIndicadorSaldoInicial(indicadorSaldoInicial);
/* 1598:1809 */           facturaCliente.setEmail(correo);
/* 1599:1813 */           if (puntoVenta != null) {
/* 1600:1814 */             this.servicioDocumento.cargarSecuencia(facturaCliente.getDocumento(), puntoVenta, facturaCliente.getFecha());
/* 1601:     */           }
/* 1602:1819 */           int longitud = documento.getSecuencia().getLongitud() - documento.getSecuencia().getPrefijo().length();
/* 1603:1820 */           numeroFactura = FuncionesUtiles.completarALaIzquierda('0', longitud, numeroFactura);
/* 1604:1821 */           numero.append(establecimiento).append("-").append(puntoEmision).append("-").append(numeroFactura);
/* 1605:1822 */           facturaCliente.setNumero(numero.toString());
/* 1606:1823 */           hmFacturaCliente.put(clave.toString(), facturaCliente);
/* 1607:     */         }
/* 1608:1830 */         if (documento.getDocumentoBase().equals(DocumentoBase.NOTA_CREDITO_CLIENTE))
/* 1609:     */         {
/* 1610:1831 */           String numeroFacturaPadre = fila[(columnaErronea = 17)] != null ? fila[(columnaErronea = 17)].getStringCellValue().trim() : "";
/* 1611:1832 */           String nombreMotivoNotaCredito = fila[(columnaErronea = 18)] != null ? fila[(columnaErronea = 18)].getStringCellValue().trim() : "";
/* 1612:     */           
/* 1613:1834 */           facturaCliente
/* 1614:1835 */             .setFacturaClientePadre(buscarFacturaClientePorNumero(idOrganizacion, numeroFacturaPadre, DocumentoBase.FACTURA_CLIENTE));
/* 1615:     */           
/* 1616:1837 */           MotivoNotaCreditoCliente motivoNotaCreditoCliente = (MotivoNotaCreditoCliente)hmMotivoNotaCreditoCliente.get(nombreMotivoNotaCredito);
/* 1617:1838 */           if (motivoNotaCreditoCliente == null)
/* 1618:     */           {
/* 1619:1839 */             HashMap<String, String> filters = new HashMap();
/* 1620:1840 */             filters.put("nombre", nombreMotivoNotaCredito);
/* 1621:1841 */             filters.put("idOrganizacion", "" + idOrganizacion);
/* 1622:1842 */             motivoNotaCreditoCliente = (MotivoNotaCreditoCliente)this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("", false, filters).get(0);
/* 1623:1843 */             hmMotivoNotaCreditoCliente.put(nombreMotivoNotaCredito, motivoNotaCreditoCliente);
/* 1624:     */           }
/* 1625:1846 */           facturaCliente.setMotivoNotaCreditoCliente(motivoNotaCreditoCliente);
/* 1626:     */         }
/* 1627:1850 */         else if (documento.getDocumentoBase().equals(DocumentoBase.NOTA_DEBITO_CLIENTE))
/* 1628:     */         {
/* 1629:1851 */           String numeroFacturaPadre = fila[(columnaErronea = 17)] != null ? fila[(columnaErronea = 17)].getStringCellValue().trim() : "";
/* 1630:1852 */           facturaCliente
/* 1631:1853 */             .setFacturaClientePadre(buscarFacturaClientePorNumero(idOrganizacion, numeroFacturaPadre, DocumentoBase.FACTURA_CLIENTE));
/* 1632:     */         }
/* 1633:1859 */         DetalleFacturaCliente detalleFacturaCliente = new DetalleFacturaCliente();
/* 1634:1860 */         detalleFacturaCliente.setIdSucursal(AppUtil.getSucursal().getId());
/* 1635:1861 */         detalleFacturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1636:1862 */         detalleFacturaCliente.setFacturaCliente(facturaCliente);
/* 1637:1863 */         detalleFacturaCliente.setCantidad(cantidad);
/* 1638:1864 */         detalleFacturaCliente.setDescuento(descuentoUnitario);
/* 1639:1865 */         detalleFacturaCliente.setPrecio(precio);
/* 1640:1866 */         detalleFacturaCliente.setProducto(producto);
/* 1641:1867 */         detalleFacturaCliente.setUnidadVenta(producto.getUnidadVenta());
/* 1642:1869 */         if (impuestos.equals("SI"))
/* 1643:     */         {
/* 1644:1873 */           detalleFacturaCliente.setIndicadorImpuesto(true);
/* 1645:1874 */           obtenerImpuestosProductos(producto, detalleFacturaCliente);
/* 1646:     */         }
/* 1647:     */         else
/* 1648:     */         {
/* 1649:1876 */           detalleFacturaCliente.setIndicadorImpuesto(false);
/* 1650:     */         }
/* 1651:1878 */         facturaCliente.getListaDetalleFacturaCliente().add(detalleFacturaCliente);
/* 1652:     */         
/* 1653:1880 */         totalizar(facturaCliente);
/* 1654:1881 */         generarCuentaPorCobrar(facturaCliente);
/* 1655:1882 */         this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 1656:1883 */         this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(facturaCliente, puntoVenta);
/* 1657:     */       }
/* 1658:     */       catch (IllegalStateException e)
/* 1659:     */       {
/* 1660:1887 */         LOG.error("Error al migrar FacturaCliente", e);
/* 1661:1888 */         if (excepcionFacturas == null) {
/* 1662:1889 */           excepcionFacturas = new AS2Exception();
/* 1663:     */         }
/* 1664:1891 */         excepcionFacturas.getMensajes().add(e.toString());
/* 1665:     */       }
/* 1666:     */       catch (IllegalArgumentException e)
/* 1667:     */       {
/* 1668:1893 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1669:1894 */         if (excepcionFacturas == null) {
/* 1670:1895 */           excepcionFacturas = new AS2Exception();
/* 1671:     */         }
/* 1672:1897 */         excepcionFacturas.getMensajes().add(e.toString());
/* 1673:     */       }
/* 1674:     */       catch (ExcepcionAS2Financiero e)
/* 1675:     */       {
/* 1676:1899 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1677:1900 */         LOG.error("Error al migrar FacturaCliente", e);
/* 1678:1901 */         if (excepcionFacturas == null) {
/* 1679:1902 */           excepcionFacturas = new AS2Exception();
/* 1680:     */         }
/* 1681:1904 */         excepcionFacturas.getCodigoMensajes().add(e.getCodigoExcepcion() + "*" + e.getMessage());
/* 1682:     */       }
/* 1683:     */       catch (ExcepcionAS2Compras e)
/* 1684:     */       {
/* 1685:1906 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1686:1907 */         LOG.error("Error al migrar FacturaCliente", e);
/* 1687:1908 */         if (excepcionFacturas == null) {
/* 1688:1909 */           excepcionFacturas = new AS2Exception();
/* 1689:     */         }
/* 1690:1911 */         excepcionFacturas.getCodigoMensajes().add(e.getCodigoExcepcion() + "*" + e.getMessage());
/* 1691:     */       }
/* 1692:     */       catch (ExcepcionAS2 e)
/* 1693:     */       {
/* 1694:1913 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1695:1914 */         LOG.error("Error al migrar FacturaCliente", e);
/* 1696:1915 */         if (excepcionFacturas == null) {
/* 1697:1916 */           excepcionFacturas = new AS2Exception();
/* 1698:     */         }
/* 1699:1918 */         excepcionFacturas.getCodigoMensajes().add(e.getCodigoExcepcion() + "*" + e.getMessage());
/* 1700:     */       }
/* 1701:     */       catch (Exception e)
/* 1702:     */       {
/* 1703:1920 */         LOG.info("Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1704:1921 */         LOG.error("Error al migrar FacturaCliente", e);
/* 1705:1922 */         e.printStackTrace();
/* 1706:1923 */         if (excepcionFacturas == null) {
/* 1707:1924 */           excepcionFacturas = new AS2Exception();
/* 1708:     */         }
/* 1709:1926 */         excepcionFacturas.getMensajes().add(e.toString() + " Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1710:     */       }
/* 1711:1929 */       if ((excepcionFacturas != null) && ((!excepcionFacturas.getMensajes().isEmpty()) || (!excepcionFacturas.getCodigoMensajes().isEmpty()))) {
/* 1712:1930 */         throw excepcionFacturas;
/* 1713:     */       }
/* 1714:     */     }
/* 1715:1935 */     for (com.asinfo.as2.entities.FacturaCliente fc : hmFacturaCliente.values()) {
/* 1716:1936 */       listaFacturaClientes.add(fc);
/* 1717:     */     }
/* 1718:1939 */     return listaFacturaClientes;
/* 1719:     */   }
/* 1720:     */   
/* 1721:     */   public void cargarExcepcion(AS2Exception excepcion, String mensaje)
/* 1722:     */   {
/* 1723:1943 */     if (excepcion == null) {
/* 1724:1944 */       excepcion = new AS2Exception(mensaje);
/* 1725:     */     }
/* 1726:1946 */     excepcion.getMensajes().add(mensaje);
/* 1727:     */   }
/* 1728:     */   
/* 1729:     */   public com.asinfo.as2.entities.FacturaCliente copiarFacturaCliente(com.asinfo.as2.entities.FacturaCliente fc, com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1730:     */     throws ExcepcionAS2
/* 1731:     */   {
/* 1732:1951 */     boolean indicadorRequiereAprobacion = ParametrosSistema.getNotaCreditoFinancieraRequiereAprobacion(fc.getIdOrganizacion()).booleanValue();
/* 1733:1952 */     if (facturaCliente == null)
/* 1734:     */     {
/* 1735:1953 */       facturaCliente = new com.asinfo.as2.entities.FacturaCliente();
/* 1736:1954 */       facturaCliente.setFecha(fc.getFecha());
/* 1737:1955 */       facturaCliente.setDocumento(fc.getDocumento());
/* 1738:1956 */       facturaCliente.setIndicadorSaldoInicial(fc.isIndicadorSaldoInicial());
/* 1739:1957 */       facturaCliente.setDescuento(fc.getDescuento());
/* 1740:1958 */       facturaCliente.setDescripcion(fc.getDescripcion());
/* 1741:1959 */       facturaCliente.setNumeroCuotas(fc.getNumeroCuotas());
/* 1742:1960 */       facturaCliente.setCondicionPago(fc.getCondicionPago());
/* 1743:1961 */       facturaCliente.setIdOrganizacion(fc.getIdOrganizacion());
/* 1744:1962 */       facturaCliente.setSucursal(fc.getSucursal());
/* 1745:1963 */       facturaCliente.setEmpresa(fc.getEmpresa());
/* 1746:1964 */       if (indicadorRequiereAprobacion) {
/* 1747:1965 */         facturaCliente.setEstado(Estado.ELABORADO);
/* 1748:     */       } else {
/* 1749:1967 */         facturaCliente.setEstado(Estado.PROCESADO);
/* 1750:     */       }
/* 1751:     */     }
/* 1752:1972 */     facturaCliente.setDireccionEmpresa(fc.getDireccionEmpresa());
/* 1753:1973 */     facturaCliente.setImpuesto(fc.getImpuesto());
/* 1754:1974 */     facturaCliente.setTotal(fc.getTotal());
/* 1755:1976 */     if (!fc.getListaDetalleFacturaCliente().isEmpty()) {
/* 1756:1978 */       for (DetalleFacturaCliente dfc : fc.getListaDetalleFacturaCliente())
/* 1757:     */       {
/* 1758:1980 */         detalleFacturaCliente = new DetalleFacturaCliente();
/* 1759:1981 */         if (facturaCliente.getFacturaClientePadre() != null) {
/* 1760:1982 */           detalleFacturaCliente.setDetalleFacturaClientePadre(dfc);
/* 1761:     */         }
/* 1762:1984 */         detalleFacturaCliente.setIdOrganizacion(dfc.getIdOrganizacion());
/* 1763:1985 */         detalleFacturaCliente.setIdSucursal(dfc.getIdSucursal());
/* 1764:1986 */         detalleFacturaCliente.setFacturaCliente(facturaCliente);
/* 1765:1987 */         detalleFacturaCliente.setCantidad(dfc.getCantidad());
/* 1766:1988 */         detalleFacturaCliente.setDescuento(dfc.getDescuento());
/* 1767:1989 */         detalleFacturaCliente.setDescripcion(dfc.getDescripcion());
/* 1768:1990 */         detalleFacturaCliente.setPrecio(dfc.getPrecio());
/* 1769:1991 */         detalleFacturaCliente.setUnidadVenta(dfc.getUnidadVenta());
/* 1770:1992 */         detalleFacturaCliente.setProducto(dfc.getProducto());
/* 1771:1993 */         detalleFacturaCliente.setIndicadorPorcentajeIce(dfc.isIndicadorPorcentajeIce());
/* 1772:1994 */         detalleFacturaCliente.setIce(dfc.getIce());
/* 1773:1995 */         detalleFacturaCliente.setCodigoIce(dfc.getCodigoIce());
/* 1774:     */         
/* 1775:1997 */         facturaCliente.getListaDetalleFacturaCliente().add(detalleFacturaCliente);
/* 1776:1999 */         if (!dfc.getListaImpuestoProductoFacturaCliente().isEmpty()) {
/* 1777:2001 */           for (ImpuestoProductoFacturaCliente ifp : dfc.getListaImpuestoProductoFacturaCliente())
/* 1778:     */           {
/* 1779:2003 */             ImpuestoProductoFacturaCliente impuestoProductoFacturaCliente = new ImpuestoProductoFacturaCliente();
/* 1780:2004 */             impuestoProductoFacturaCliente.setPorcentajeImpuesto(ifp.getPorcentajeImpuesto());
/* 1781:2005 */             impuestoProductoFacturaCliente.setImpuesto(ifp.getImpuesto());
/* 1782:2006 */             impuestoProductoFacturaCliente.setDetalleFacturaCliente(detalleFacturaCliente);
/* 1783:2007 */             detalleFacturaCliente.getListaImpuestoProductoFacturaCliente().add(impuestoProductoFacturaCliente);
/* 1784:     */           }
/* 1785:     */         }
/* 1786:     */       }
/* 1787:     */     }
/* 1788:     */     DetalleFacturaCliente detalleFacturaCliente;
/* 1789:2015 */     if (!fc.getListaCuentaPorCobrar().isEmpty()) {
/* 1790:2016 */       for (CuentaPorCobrar cpp : fc.getListaCuentaPorCobrar())
/* 1791:     */       {
/* 1792:2017 */         CuentaPorCobrar cuentaPorCobrar = new CuentaPorCobrar();
/* 1793:2018 */         cuentaPorCobrar.setIdOrganizacion(cpp.getIdOrganizacion());
/* 1794:2019 */         cuentaPorCobrar.setIdSucursal(cpp.getIdSucursal());
/* 1795:2020 */         cuentaPorCobrar.setFacturaCliente(facturaCliente);
/* 1796:2021 */         cuentaPorCobrar.setFechaVencimiento(cpp.getFechaVencimiento());
/* 1797:2022 */         cuentaPorCobrar.setNumeroCuota(cpp.getNumeroCuota());
/* 1798:2023 */         cuentaPorCobrar.setSaldo(cpp.getSaldo());
/* 1799:2024 */         cuentaPorCobrar.setValor(cpp.getValor());
/* 1800:2025 */         facturaCliente.getListaCuentaPorCobrar().add(cuentaPorCobrar);
/* 1801:     */       }
/* 1802:     */     }
/* 1803:2028 */     if (fc.getFacturaClienteSRI() != null)
/* 1804:     */     {
/* 1805:2030 */       FacturaClienteSRI facturaClienteSRI = new FacturaClienteSRI();
/* 1806:2031 */       facturaClienteSRI.setIdOrganizacion(fc.getFacturaClienteSRI().getIdOrganizacion());
/* 1807:2032 */       facturaClienteSRI.setIdSucursal(fc.getFacturaClienteSRI().getIdSucursal());
/* 1808:2033 */       facturaClienteSRI.setTipoComprobanteSRI(fc.getFacturaClienteSRI().getTipoComprobanteSRI());
/* 1809:2034 */       facturaClienteSRI.setTipoIdentificacion(fc.getFacturaClienteSRI().getTipoIdentificacion());
/* 1810:2035 */       facturaClienteSRI.setAutorizacion(fc.getFacturaClienteSRI().getAutorizacion());
/* 1811:2036 */       facturaClienteSRI.setEstablecimiento(fc.getFacturaClienteSRI().getEstablecimiento());
/* 1812:2037 */       facturaClienteSRI.setPuntoEmision(fc.getFacturaClienteSRI().getPuntoEmision());
/* 1813:2038 */       facturaClienteSRI.setEstado(Estado.ELABORADO);
/* 1814:2039 */       facturaClienteSRI.setFacturaCliente(facturaCliente);
/* 1815:2040 */       facturaCliente.setFacturaClienteSRI(facturaClienteSRI);
/* 1816:     */     }
/* 1817:2042 */     return facturaCliente;
/* 1818:     */   }
/* 1819:     */   
/* 1820:     */   public void actualizarCabeceraFacturaCliente(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1821:     */     throws ExcepcionAS2Financiero
/* 1822:     */   {
/* 1823:     */     try
/* 1824:     */     {
/* 1825:2055 */       this.servicioPeriodo.buscarPorFecha(facturaCliente.getFecha(), facturaCliente.getIdOrganizacion(), facturaCliente
/* 1826:2056 */         .getDocumento().getDocumentoBase());
/* 1827:     */       
/* 1828:2058 */       HashMap<String, Object> campos = new HashMap();
/* 1829:2059 */       campos.put("canal", facturaCliente.getCanal());
/* 1830:2060 */       campos.put("zona", facturaCliente.getZona());
/* 1831:2061 */       campos.put("agenteComercial", facturaCliente.getAgenteComercial());
/* 1832:2062 */       this.facturaClienteDao.actualizarAtributoEntidad(facturaCliente, campos);
/* 1833:     */     }
/* 1834:     */     catch (ExcepcionAS2Financiero e)
/* 1835:     */     {
/* 1836:2065 */       this.context.setRollbackOnly();
/* 1837:2066 */       throw e;
/* 1838:     */     }
/* 1839:     */   }
/* 1840:     */   
/* 1841:     */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/* 1842:     */   public List<com.asinfo.as2.entities.FacturaCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 1843:     */   {
/* 1844:2079 */     return this.facturaClienteDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 1845:     */   }
/* 1846:     */   
/* 1847:     */   public void verificaNumeroFacturaRangoSecuencia(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1848:     */     throws ExcepcionAS2Financiero
/* 1849:     */   {
/* 1850:2090 */     this.facturaClienteDao.verificaNumeroFacturaRangoSecuencia(facturaCliente);
/* 1851:     */   }
/* 1852:     */   
/* 1853:     */   public List<CuentaPorCobrar> obtenerFacturasPendientesLiquidacionCuentaPorCobrar(int idEmpresa, int idFacturaCliente, Date fechaCobroLiquidacion, BigDecimal tolerancia)
/* 1854:     */   {
/* 1855:2096 */     return this.facturaClienteDao.obtenerFacturasPendientesLiquidacionCuentaPorCobrar(idEmpresa, idFacturaCliente, fechaCobroLiquidacion, tolerancia);
/* 1856:     */   }
/* 1857:     */   
/* 1858:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1859:     */   public void anularFacturaClientePos(com.asinfo.pos.model.FacturaCliente facturaCliente)
/* 1860:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1861:     */   {
/* 1862:2109 */     if (facturaCliente.getIdCobroAs2() != 0)
/* 1863:     */     {
/* 1864:2110 */       Cobro c = this.servicioCobro.cargarDetalle(facturaCliente.getIdCobroAs2());
/* 1865:2111 */       this.servicioCobro.anularCobro(c);
/* 1866:     */     }
/* 1867:2114 */     if (facturaCliente.getIdDespachoClienteAs2() != 0)
/* 1868:     */     {
/* 1869:2115 */       DespachoCliente dc = this.servicioDespachoCliente.cargarDetalle(Integer.valueOf(facturaCliente.getIdDespachoClienteAs2()));
/* 1870:2116 */       this.servicioDespachoCliente.anular(dc, new Date());
/* 1871:     */     }
/* 1872:2119 */     if (facturaCliente.getIdFacturaClienteAs2() != 0)
/* 1873:     */     {
/* 1874:2120 */       com.asinfo.as2.entities.FacturaCliente fc = cargarDetalle(facturaCliente.getIdFacturaClienteAs2());
/* 1875:2121 */       anulaFacturaCliente(fc);
/* 1876:     */     }
/* 1877:     */   }
/* 1878:     */   
/* 1879:     */   public List<Object[]> getListaDevolucionCliente(int idFacturaCliente, int idOrganizacion)
/* 1880:     */     throws ExcepcionAS2
/* 1881:     */   {
/* 1882:2127 */     return this.facturaClienteDao.getListaDevolucionCliente(idFacturaCliente, idOrganizacion);
/* 1883:     */   }
/* 1884:     */   
/* 1885:     */   public List<com.asinfo.as2.entities.FacturaCliente> obtenerFacturasMes(Date fechaDesde, Date fechaHasta, Sucursal sucursal, PuntoDeVenta puntoDeVenta, int idOrganizacion, DocumentoBase documentoBase, boolean indicadorIncluirAnulados)
/* 1886:     */   {
/* 1887:2140 */     return this.facturaClienteDao.obtenerFacturasMes(fechaDesde, fechaHasta, sucursal, puntoDeVenta, idOrganizacion, documentoBase, indicadorIncluirAnulados);
/* 1888:     */   }
/* 1889:     */   
/* 1890:     */   public List<com.asinfo.as2.entities.FacturaCliente> obtenerFacturasMes(Date fechaDesde, Date fechaHasta, Sucursal sucursal, PuntoDeVenta puntoDeVenta, int idOrganizacion, DocumentoBase documentoBase)
/* 1891:     */   {
/* 1892:2153 */     return obtenerFacturasMes(fechaDesde, fechaHasta, sucursal, puntoDeVenta, idOrganizacion, documentoBase, false);
/* 1893:     */   }
/* 1894:     */   
/* 1895:     */   public void actualizarEstado(int idFacturaCliente, Estado estado)
/* 1896:     */   {
/* 1897:2158 */     actualizarEstado(idFacturaCliente, estado, null);
/* 1898:     */   }
/* 1899:     */   
/* 1900:     */   public List<com.asinfo.as2.entities.FacturaCliente> getListaNotaCreditoCliente(com.asinfo.as2.entities.FacturaCliente facturaCliente, com.asinfo.as2.entities.FacturaCliente notaCreditoFinancieraCliente)
/* 1901:     */   {
/* 1902:2169 */     return this.facturaClienteDao.getListaNotaCreditoCliente(facturaCliente, notaCreditoFinancieraCliente);
/* 1903:     */   }
/* 1904:     */   
/* 1905:     */   public void anulaFacturaCliente(com.asinfo.as2.entities.FacturaCliente fc, Sucursal sucursal)
/* 1906:     */     throws ExcepcionAS2, ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 1907:     */   {
/* 1908:2185 */     if (!sucursal.equals(fc.getSucursal())) {
/* 1909:2186 */       throw new ExcepcionAS2Ventas("msg_error_anular", " " + fc.getNumero());
/* 1910:     */     }
/* 1911:2189 */     anulaFacturaCliente(fc);
/* 1912:     */   }
/* 1913:     */   
/* 1914:     */   public com.asinfo.as2.entities.FacturaCliente buscarFacturaClientePorNumero(int idOrganizacion, String numero, DocumentoBase documentoBase)
/* 1915:     */   {
/* 1916:2194 */     return this.facturaClienteDao.buscarFacturaClientePorNumero(idOrganizacion, numero, documentoBase);
/* 1917:     */   }
/* 1918:     */   
/* 1919:     */   public void guardarFacturaClienteRevisadas(List<com.asinfo.as2.entities.FacturaCliente> lisFacturaCliente)
/* 1920:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1921:     */   {
/* 1922:2200 */     for (com.asinfo.as2.entities.FacturaCliente fc : lisFacturaCliente) {
/* 1923:2201 */       guardar(fc);
/* 1924:     */     }
/* 1925:     */   }
/* 1926:     */   
/* 1927:     */   public void adicionarMensajes(AS2Exception exception, String error)
/* 1928:     */   {
/* 1929:2207 */     if (exception == null) {
/* 1930:2208 */       exception = new AS2Exception(error);
/* 1931:     */     }
/* 1932:     */   }
/* 1933:     */   
/* 1934:     */   public PuntoDeVenta cargarPuntoVenta(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 1935:     */     throws ExcepcionAS2
/* 1936:     */   {
/* 1937:2214 */     PuntoDeVenta puntoDeVenta = AppUtil.getPuntoDeVenta();
/* 1938:2217 */     if ((facturaCliente.getId() != 0) || (puntoDeVenta == null))
/* 1939:     */     {
/* 1940:2218 */       String[] numero = facturaCliente.getNumero().split("-");
/* 1941:2219 */       String codigoSucursal = numero[0];
/* 1942:2220 */       String codigoPuntoVenta = numero[1];
/* 1943:     */       
/* 1944:2222 */       Sucursal sucursal = this.servicioSucursal.buscarPorCodigo(facturaCliente.getIdOrganizacion(), codigoSucursal);
/* 1945:     */       
/* 1946:2224 */       Map<String, String> filters = new HashMap();
/* 1947:2225 */       filters.put("idOrganizacion", "" + facturaCliente.getIdOrganizacion());
/* 1948:2226 */       filters.put("codigo", codigoPuntoVenta);
/* 1949:2227 */       filters.put("sucursal.idSucursal", "" + sucursal.getId());
/* 1950:2228 */       puntoDeVenta = this.servicioPuntoDeVenta.buscarPuntoDeVenta(filters);
/* 1951:     */     }
/* 1952:2230 */     return puntoDeVenta;
/* 1953:     */   }
/* 1954:     */   
/* 1955:     */   public List<DetalleFacturaCliente> buscarDetallesNoDespachados(Integer idFacturaCliente)
/* 1956:     */   {
/* 1957:2235 */     return this.facturaClienteDao.buscarDetallesNoDespachados(idFacturaCliente);
/* 1958:     */   }
/* 1959:     */   
/* 1960:     */   public List<com.asinfo.as2.entities.FacturaCliente> cargaNotaCreditoCliente(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial, PuntoDeVenta puntoDeVenta)
/* 1961:     */     throws ExcepcionAS2, AS2Exception
/* 1962:     */   {
/* 1963:2242 */     List<com.asinfo.as2.entities.FacturaCliente> listaNotaCreditoCliente = new ArrayList();
/* 1964:2243 */     com.asinfo.as2.entities.FacturaCliente notaCredito = null;
/* 1965:     */     
/* 1966:2245 */     HashMap<String, String> filtros = new HashMap();
/* 1967:2246 */     filtros.put("idOrganizacion", Integer.toString(idOrganizacion));
/* 1968:     */     
/* 1969:     */ 
/* 1970:2249 */     HashMap<String, Documento> hmDocumento = new HashMap();
/* 1971:2250 */     for (Iterator localIterator1 = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros).iterator(); localIterator1.hasNext();)
/* 1972:     */     {
/* 1973:2250 */       documento = (Documento)localIterator1.next();
/* 1974:2251 */       hmDocumento.put(documento.getNombre(), documento);
/* 1975:     */     }
/* 1976:     */     Documento documento;
/* 1977:2254 */     Object hmMotivoNotaCreditoCliente = new HashMap();
/* 1978:2255 */     for (MotivoNotaCreditoCliente motivoNotaCreditoCliente : this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("nombre", true, filtros)) {
/* 1979:2256 */       ((HashMap)hmMotivoNotaCreditoCliente).put(motivoNotaCreditoCliente.getNombre(), motivoNotaCreditoCliente);
/* 1980:     */     }
/* 1981:2260 */     HashMap<String, com.asinfo.as2.entities.FacturaCliente> hmNotaCreditoCliente = new HashMap();
/* 1982:     */     
/* 1983:2262 */     int filaActual = filaInicial;
/* 1984:2263 */     int columnaActual = 0;
/* 1985:2264 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 1986:     */     try
/* 1987:     */     {
/* 1988:2268 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 1989:2269 */       for (HSSFCell[] fila : datos)
/* 1990:     */       {
/* 1991:2271 */         filaErronea = fila;
/* 1992:2272 */         filaActual++;
/* 1993:     */         
/* 1994:2274 */         Date fechaNotaCredito = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 0, true, Integer.valueOf(0), Integer.valueOf(0));
/* 1995:2275 */         String nombreDocumento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 1, true, Integer.valueOf(1), Integer.valueOf(80));
/* 1996:2276 */         Documento documento = (Documento)hmDocumento.get(nombreDocumento);
/* 1997:2277 */         if (documento == null) {
/* 1998:2278 */           throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioFacturaClienteImpl.NO_EXISTE_DOCUMENTO", new String[] { nombreDocumento, "" + filaActual, "" + columnaActual });
/* 1999:     */         }
/* 2000:2280 */         String identificacionCliente = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 2, true, Integer.valueOf(2), 
/* 2001:2281 */           Integer.valueOf(20));
/* 2002:2282 */         String establecimiento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 3, true, Integer.valueOf(2), Integer.valueOf(20));
/* 2003:2283 */         String puntoEmision = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 4, true, Integer.valueOf(2), Integer.valueOf(20));
/* 2004:2284 */         String numeroFactura = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 5, true, Integer.valueOf(2), Integer.valueOf(20));
/* 2005:2285 */         String autorizacion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 6, false, Integer.valueOf(2), Integer.valueOf(20));
/* 2006:2286 */         String nota = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 7, true, Integer.valueOf(2), Integer.valueOf(20));
/* 2007:2287 */         String codigoProductoAS2 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 8, true, Integer.valueOf(2), 
/* 2008:2288 */           Integer.valueOf(20));
/* 2009:2289 */         BigDecimal cantidad = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 9, false, Integer.valueOf(0), 
/* 2010:2290 */           Integer.valueOf(0));
/* 2011:2291 */         BigDecimal precio = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 10, false, Integer.valueOf(0), Integer.valueOf(0));
/* 2012:2292 */         String impuesto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 11, true, Integer.valueOf(0), Integer.valueOf(2));
/* 2013:2293 */         boolean indicadorImpuesto = impuesto.equalsIgnoreCase("SI");
/* 2014:2294 */         String correoElectronico = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 12, true, Integer.valueOf(1), 
/* 2015:2295 */           Integer.valueOf(80));
/* 2016:2296 */         String motivoNotaCredito = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 13, true, Integer.valueOf(1), 
/* 2017:2297 */           Integer.valueOf(80));
/* 2018:     */         
/* 2019:2299 */         String claveNotaCredito = numeroFactura + "-" + autorizacion;
/* 2020:     */         
/* 2021:2301 */         documento = this.servicioDocumento.cargarDetalle(documento.getIdDocumento());
/* 2022:2302 */         MotivoNotaCreditoCliente motivoNotaCreditoCliente = (MotivoNotaCreditoCliente)((HashMap)hmMotivoNotaCreditoCliente).get(motivoNotaCredito);
/* 2023:2303 */         Empresa empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(identificacionCliente);
/* 2024:2304 */         DireccionEmpresa direccionEmpresa = (DireccionEmpresa)this.servicioEmpresa.obtenerListaComboDirecciones(empresa.getId()).get(0);
/* 2025:2305 */         com.asinfo.as2.entities.FacturaCliente factura = buscarFacturaClientePorNumero(idOrganizacion, establecimiento + "-" + puntoEmision + "-" + numeroFactura, DocumentoBase.FACTURA_CLIENTE);
/* 2026:2307 */         if (factura == null) {
/* 2027:2308 */           throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioFacturaClienteImpl.NO_EXISTE_FACTURA", new String[] { establecimiento + "-" + puntoEmision + "-" + numeroFactura });
/* 2028:     */         }
/* 2029:2310 */         Sucursal sucursal = this.servicioSucursal.buscarPorCodigo(idOrganizacion, establecimiento);
/* 2030:2312 */         if (!hmNotaCreditoCliente.containsKey(claveNotaCredito))
/* 2031:     */         {
/* 2032:2313 */           notaCredito = new com.asinfo.as2.entities.FacturaCliente();
/* 2033:2314 */           notaCredito.setFecha(fechaNotaCredito);
/* 2034:2315 */           notaCredito.setIdOrganizacion(idOrganizacion);
/* 2035:2316 */           notaCredito.setSucursal(sucursal);
/* 2036:2317 */           notaCredito.setDocumento(documento);
/* 2037:2318 */           notaCredito.setMotivoNotaCreditoCliente(motivoNotaCreditoCliente);
/* 2038:2319 */           notaCredito.setEmpresa(empresa);
/* 2039:2320 */           notaCredito.setDireccionEmpresa(direccionEmpresa);
/* 2040:2321 */           notaCredito.setFacturaClientePadre(factura);
/* 2041:2322 */           notaCredito.setEmail(correoElectronico);
/* 2042:2323 */           notaCredito.setDescripcion(nota);
/* 2043:2324 */           notaCredito.setNumero(" ");
/* 2044:2325 */           notaCredito.setNumeroCuotas(1);
/* 2045:2326 */           notaCredito.setEstado(Estado.ELABORADO);
/* 2046:2327 */           notaCredito.setCondicionPago(empresa.getCliente().getCondicionPago());
/* 2047:2328 */           hmNotaCreditoCliente.put(numeroFactura + "-" + autorizacion, notaCredito);
/* 2048:     */         }
/* 2049:     */         else
/* 2050:     */         {
/* 2051:2331 */           notaCredito = (com.asinfo.as2.entities.FacturaCliente)hmNotaCreditoCliente.get(claveNotaCredito);
/* 2052:     */         }
/* 2053:2334 */         Producto producto = this.servicioProducto.buscarPorCodigo(codigoProductoAS2, idOrganizacion, null);
/* 2054:     */         
/* 2055:2336 */         DetalleFacturaCliente detalleNotaCredito = new DetalleFacturaCliente();
/* 2056:2337 */         detalleNotaCredito.setIdOrganizacion(idOrganizacion);
/* 2057:2338 */         detalleNotaCredito.setIdSucursal(sucursal.getIdSucursal());
/* 2058:2339 */         detalleNotaCredito.setFacturaCliente(notaCredito);
/* 2059:2340 */         detalleNotaCredito.setProducto(producto);
/* 2060:2341 */         detalleNotaCredito.setCantidad(cantidad);
/* 2061:2342 */         detalleNotaCredito.setPrecio(precio);
/* 2062:2343 */         detalleNotaCredito.setIndicadorImpuesto(indicadorImpuesto);
/* 2063:2344 */         detalleNotaCredito.setUnidadVenta(producto.getUnidadVenta());
/* 2064:2345 */         obtenerImpuestosProductos(producto, detalleNotaCredito);
/* 2065:     */         
/* 2066:2347 */         notaCredito.getListaDetalleFacturaCliente().add(detalleNotaCredito);
/* 2067:     */         
/* 2068:2349 */         totalizar(notaCredito);
/* 2069:2351 */         for (DetalleFacturaCliente dnc : notaCredito.getListaDetalleFacturaCliente()) {
/* 2070:2352 */           System.out.println("---------->>>>>>   " + dnc.getPrecioLinea());
/* 2071:     */         }
/* 2072:     */       }
/* 2073:2356 */       for (??? = hmNotaCreditoCliente.values().iterator(); ((Iterator)???).hasNext();)
/* 2074:     */       {
/* 2075:2356 */         com.asinfo.as2.entities.FacturaCliente notaCreditoAux = (com.asinfo.as2.entities.FacturaCliente)((Iterator)???).next();
/* 2076:2357 */         listaNotaCreditoCliente.add(notaCreditoAux);
/* 2077:     */       }
/* 2078:     */     }
/* 2079:     */     catch (AS2Exception e)
/* 2080:     */     {
/* 2081:2361 */       e.printStackTrace();
/* 2082:2362 */       LOG.error("Error al migrar factura proveedor", e);
/* 2083:2363 */       this.context.setRollbackOnly();
/* 2084:2364 */       throw e;
/* 2085:     */     }
/* 2086:     */     catch (IllegalStateException e)
/* 2087:     */     {
/* 2088:2367 */       LOG.info("Error al migrar productos", e);
/* 2089:2368 */       this.context.setRollbackOnly();
/* 2090:     */       
/* 2091:2370 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 2092:     */     }
/* 2093:     */     catch (ExcepcionAS2 e)
/* 2094:     */     {
/* 2095:2372 */       LOG.info("Error al migrar productos", e);
/* 2096:2373 */       this.context.setRollbackOnly();
/* 2097:2374 */       throw e;
/* 2098:     */     }
/* 2099:     */     catch (Exception e)
/* 2100:     */     {
/* 2101:2376 */       LOG.error("Error al migrar productos", e);
/* 2102:2377 */       e.printStackTrace();
/* 2103:2378 */       this.context.setRollbackOnly();
/* 2104:     */       
/* 2105:2380 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString(), e);
/* 2106:     */     }
/* 2107:     */     HSSFCell[][] datos;
/* 2108:2383 */     return listaNotaCreditoCliente;
/* 2109:     */   }
/* 2110:     */   
/* 2111:     */   public void guardarCuotas(List<CuentaPorCobrar> listaCuentaPorCobrar, BigDecimal totalFactura)
/* 2112:     */     throws AS2Exception
/* 2113:     */   {
/* 2114:2389 */     BigDecimal totalCuotas = BigDecimal.ZERO;
/* 2115:2390 */     for (CuentaPorCobrar cpc : listaCuentaPorCobrar) {
/* 2116:2391 */       if (!cpc.isEliminado()) {
/* 2117:2392 */         totalCuotas = totalCuotas.add(cpc.getValor());
/* 2118:     */       }
/* 2119:     */     }
/* 2120:2396 */     if (totalFactura.compareTo(totalCuotas) != 0) {
/* 2121:2397 */       throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioFacturaClienteImpl.ERROR_TOTAL_CUOTA_TOTAL_FACTURA", new String[] { totalFactura.toString(), totalCuotas.toString() });
/* 2122:     */     }
/* 2123:2399 */     for (CuentaPorCobrar cpc : listaCuentaPorCobrar)
/* 2124:     */     {
/* 2125:2401 */       if (!cpc.isActivoEdicionCuota()) {
/* 2126:2402 */         cpc.setSaldo(cpc.getValor());
/* 2127:     */       }
/* 2128:2404 */       this.cuentaPorCobrarDao.guardar(cpc);
/* 2129:     */     }
/* 2130:     */   }
/* 2131:     */   
/* 2132:     */   public com.asinfo.as2.entities.FacturaCliente obtenerUltimaFacturaAutorizadaPorCliente(int idOrganizacion, Empresa empresa, boolean devolucion, BigDecimal total)
/* 2133:     */   {
/* 2134:2413 */     return this.facturaClienteDao.obtenerUltimaFacturaAutorizadaPorCliente(idOrganizacion, empresa, devolucion, total);
/* 2135:     */   }
/* 2136:     */   
/* 2137:     */   public List<Object[]> getReporteFacturaCliente(int idFacturaCliente, int idOrganizacion)
/* 2138:     */     throws ExcepcionAS2
/* 2139:     */   {
/* 2140:2419 */     List<Object[]> lista = this.facturaClienteDao.getReporteFacturaCliente(idFacturaCliente);
/* 2141:2420 */     List<SelectItem> selItmFormaPagoSRI = DatosSRI.getListaFormaPago(idOrganizacion);
/* 2142:2421 */     asignacionFormaPagoSRI(lista, selItmFormaPagoSRI);
/* 2143:2422 */     return lista;
/* 2144:     */   }
/* 2145:     */   
/* 2146:     */   public List<com.asinfo.as2.entities.FacturaCliente> listaFacturas(int idOrganizacion, Date fechaDesde, Date fechaHasta, Transportista transportista, Zona zona, Canal canal, HojaRuta hojaRuta)
/* 2147:     */   {
/* 2148:2428 */     return this.facturaClienteDao.listaFacturas(idOrganizacion, fechaDesde, fechaHasta, transportista, zona, canal, hojaRuta);
/* 2149:     */   }
/* 2150:     */   
/* 2151:     */   public void facturaEnLote(int idOrganizacion, Sucursal sucursal, HojaRuta hojaRuta, Transportista transportista, Date fechaDesde, Date fechaHasta, PuntoDeVenta puntoDeVenta)
/* 2152:     */     throws ExcepcionAS2, AS2Exception
/* 2153:     */   {
/* 2154:2435 */     List<DespachoCliente> listaDespachoCliente = this.facturaClienteDao.detalleDespachoCliente(idOrganizacion, sucursal, hojaRuta, transportista, fechaDesde, fechaHasta);
/* 2155:2437 */     if (listaDespachoCliente.size() > 0)
/* 2156:     */     {
/* 2157:2438 */       List<com.asinfo.as2.entities.FacturaCliente> listaFacturas = new ArrayList();
/* 2158:     */       
/* 2159:     */ 
/* 2160:2441 */       List<Documento> listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FACTURA_CLIENTE, idOrganizacion);
/* 2161:     */       
/* 2162:2443 */       Documento documento = null;
/* 2163:2444 */       for (Documento doc : listaDocumentoCliente) {
/* 2164:2445 */         if (doc.isPredeterminado()) {
/* 2165:2446 */           documento = doc;
/* 2166:     */         }
/* 2167:     */       }
/* 2168:2449 */       if ((documento == null) && (!listaDocumentoCliente.isEmpty()))
/* 2169:     */       {
/* 2170:2450 */         documento = (Documento)listaDocumentoCliente.get(0);
/* 2171:2451 */         this.servicioDocumento.detach(documento);
/* 2172:     */       }
/* 2173:2454 */       for (DespachoCliente despachoCliente : listaDespachoCliente) {
/* 2174:2456 */         if (despachoCliente.getEmpresa().getCliente().isIndicadorFacturarAlDespachar())
/* 2175:     */         {
/* 2176:2457 */           com.asinfo.as2.entities.FacturaCliente facturaEnDespacho = buscarPorDespachoCliente(Integer.valueOf(despachoCliente.getIdDespachoCliente()));
/* 2177:2458 */           if (facturaEnDespacho == null)
/* 2178:     */           {
/* 2179:2459 */             facturaCliente = new com.asinfo.as2.entities.FacturaCliente();
/* 2180:2460 */             listaFacturas.add(facturaCliente);
/* 2181:     */             
/* 2182:2462 */             facturaCliente.setNumero("");
/* 2183:2463 */             facturaCliente.setFecha(new Date());
/* 2184:2464 */             facturaCliente.setEstado(Estado.PROCESADO);
/* 2185:2465 */             facturaCliente.setDocumento(documento);
/* 2186:     */             
/* 2187:2467 */             facturaCliente.setIdOrganizacion(idOrganizacion);
/* 2188:2468 */             facturaCliente.setSucursal(sucursal);
/* 2189:     */             
/* 2190:2470 */             facturaCliente.setFacturaClienteSRI(new FacturaClienteSRI());
/* 2191:2471 */             facturaCliente.getFacturaClienteSRI().setEstado(facturaCliente.getEstado());
/* 2192:2472 */             facturaCliente.getFacturaClienteSRI().setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 2193:2473 */             facturaCliente.getFacturaClienteSRI().setIdSucursal(facturaCliente.getSucursal().getId());
/* 2194:     */             
/* 2195:2475 */             facturaCliente.getFacturaClienteSRI().setFacturaCliente(facturaCliente);
/* 2196:2476 */             facturaCliente.setFacturaClienteSRI(facturaCliente.getFacturaClienteSRI());
/* 2197:     */             
/* 2198:2478 */             Empresa empresa = this.servicioEmpresa.obtenerDatosCliente(despachoCliente.getEmpresa().getIdEmpresa());
/* 2199:     */             
/* 2200:     */ 
/* 2201:2481 */             List<FormaPagoSRI> listaFormaPagoSRI = this.servicioFormaPagoSRI.getListaFormaPagoSRI(empresa);
/* 2202:2482 */             String codigoFormaPagoSRI = "";
/* 2203:2483 */             if ((listaFormaPagoSRI != null) && (!listaFormaPagoSRI.isEmpty())) {
/* 2204:2484 */               codigoFormaPagoSRI = ((FormaPagoSRI)this.servicioFormaPagoSRI.getListaFormaPagoSRI(empresa).get(0)).getCodigo();
/* 2205:     */             } else {
/* 2206:2488 */               codigoFormaPagoSRI = ((SelectItem)DatosSRI.getListaFormaPago(idOrganizacion).get(0)).getValue().toString();
/* 2207:     */             }
/* 2208:2491 */             facturaCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(codigoFormaPagoSRI);
/* 2209:     */             
/* 2210:     */ 
/* 2211:2494 */             facturaCliente.setEmpresa(empresa);
/* 2212:2495 */             facturaCliente.setSubempresa(despachoCliente.getSubempresa());
/* 2213:2496 */             facturaCliente.setCondicionPago(empresa.getCliente().getCondicionPago());
/* 2214:2497 */             facturaCliente.setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/* 2215:     */             
/* 2216:2499 */             facturaCliente.setDespachoCliente(despachoCliente);
/* 2217:2500 */             facturaCliente.setPedidoCliente(despachoCliente.getPedidoCliente());
/* 2218:2502 */             if (facturaCliente.getPedidoCliente() != null)
/* 2219:     */             {
/* 2220:2503 */               facturaCliente.setReferencia8(facturaCliente.getPedidoCliente().getReferencia8());
/* 2221:2504 */               facturaCliente.setReferencia9(facturaCliente.getPedidoCliente().getReferencia9());
/* 2222:2505 */               facturaCliente.setZona(facturaCliente.getPedidoCliente().getZona());
/* 2223:     */             }
/* 2224:     */             else
/* 2225:     */             {
/* 2226:2507 */               facturaCliente.setZona(empresa.getCliente().getZona());
/* 2227:     */             }
/* 2228:2510 */             facturaCliente.setDireccionEmpresa(despachoCliente.getDireccionEmpresa());
/* 2229:2511 */             if (facturaCliente.getSubempresa() != null) {
/* 2230:2512 */               facturaCliente.setEmail(this.servicioEmpresa
/* 2231:2513 */                 .cargarMails(facturaCliente.getSubempresa().getEmpresa(), DocumentoBase.FACTURA_CLIENTE));
/* 2232:     */             } else {
/* 2233:2515 */               facturaCliente.setEmail(this.servicioEmpresa.cargarMails(facturaCliente.getEmpresa(), DocumentoBase.FACTURA_CLIENTE));
/* 2234:     */             }
/* 2235:2518 */             for (DetalleDespachoCliente ddc : despachoCliente.getListaDetalleDespachoCliente())
/* 2236:     */             {
/* 2237:2519 */               DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 2238:2520 */               dfc.setDetalleDespachoCliente(ddc);
/* 2239:2521 */               facturaCliente.getListaDetalleFacturaCliente().add(dfc);
/* 2240:2522 */               dfc.setCantidad(ddc.getCantidad());
/* 2241:2523 */               dfc.setUnidadVenta(ddc.getUnidadVenta());
/* 2242:2524 */               dfc.setPeso(ddc.getPeso());
/* 2243:2525 */               dfc.setDescripcion(ddc.getDescripcion());
/* 2244:2526 */               dfc.setIndicadorManejoPeso(ddc.isIndicadorManejoPeso());
/* 2245:2527 */               dfc.setProducto(ddc.getProducto());
/* 2246:2528 */               dfc.setEliminado(false);
/* 2247:2529 */               dfc.setFacturaCliente(facturaCliente);
/* 2248:2530 */               dfc.setIndicadorPorcentajeIce(ddc.getProducto().isIndicadorPorcentajeIce());
/* 2249:2531 */               dfc.setCodigoIce(ddc.getProducto().getCodigoIce());
/* 2250:2532 */               dfc.setIce(ddc.getProducto().getIce());
/* 2251:2534 */               if (ddc.getDetallePedidoCliente() != null)
/* 2252:     */               {
/* 2253:2535 */                 dfc.setPrecio(ddc.getDetallePedidoCliente().getPrecio());
/* 2254:2536 */                 dfc.setDescuento(ddc.getDetallePedidoCliente().getDescuento());
/* 2255:2537 */                 dfc.setPorcentajeDescuento(ddc.getDetallePedidoCliente().getPorcentajeDescuento());
/* 2256:     */                 
/* 2257:     */ 
/* 2258:     */ 
/* 2259:2541 */                 dfc.setIceLinea(ddc.getDetallePedidoCliente().getIceLinea());
/* 2260:     */               }
/* 2261:     */               else
/* 2262:     */               {
/* 2263:2543 */                 dfc.setPrecio(new BigDecimal(1));
/* 2264:     */               }
/* 2265:2546 */               if ((facturaCliente.getEmpresa().getCliente().isExcentoImpuestos()) || 
/* 2266:2547 */                 (facturaCliente.getDocumento().isIndicadorDocumentoExterior())) {
/* 2267:2548 */                 dfc.setIndicadorImpuesto(false);
/* 2268:     */               } else {
/* 2269:2550 */                 dfc.setIndicadorImpuesto(ddc.getProducto().isIndicadorImpuestos());
/* 2270:     */               }
/* 2271:2552 */               if (dfc.isIndicadorImpuesto()) {
/* 2272:2553 */                 obtenerImpuestosProductos(dfc.getProducto(), dfc);
/* 2273:     */               }
/* 2274:     */             }
/* 2275:     */           }
/* 2276:     */         }
/* 2277:     */       }
/* 2278:     */       com.asinfo.as2.entities.FacturaCliente facturaCliente;
/* 2279:2559 */       for (com.asinfo.as2.entities.FacturaCliente facturaCliente : listaFacturas)
/* 2280:     */       {
/* 2281:2561 */         cargarSecuencia(facturaCliente, puntoDeVenta);
/* 2282:2562 */         this.servicioSecuencia.detach(facturaCliente.getDocumento().getSecuencia());
/* 2283:2563 */         this.servicioSecuencia.actualizarSecuencia(facturaCliente.getDocumento().getSecuencia(), facturaCliente.getNumero());
/* 2284:2565 */         if (facturaCliente.getEmpresa().getCliente().isIndicadorFacturarAlDespachar())
/* 2285:     */         {
/* 2286:2567 */           totalizar(facturaCliente);
/* 2287:2568 */           generarCuentaPorCobrar(facturaCliente);
/* 2288:2569 */           this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 2289:2570 */           this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(facturaCliente, puntoDeVenta);
/* 2290:2571 */           guardar(facturaCliente);
/* 2291:     */         }
/* 2292:     */       }
/* 2293:2576 */       if (hojaRuta != null)
/* 2294:     */       {
/* 2295:2577 */         hojaRuta = this.hojaRutaDao.cargarDetalle(hojaRuta.getIdHojaRuta());
/* 2296:2578 */         hojaRuta.setEstado(Estado.FACTURADO);
/* 2297:     */       }
/* 2298:     */     }
/* 2299:     */     else
/* 2300:     */     {
/* 2301:2581 */       throw new ExcepcionAS2("msg_no_hay_datos", "");
/* 2302:     */     }
/* 2303:     */   }
/* 2304:     */   
/* 2305:     */   public com.asinfo.as2.entities.FacturaCliente crearFacturaDesdeDespacho(com.asinfo.as2.entities.FacturaCliente facturaCliente, int idDespachoCliente, PuntoDeVenta puntoVenta)
/* 2306:     */     throws AS2Exception, ExcepcionAS2
/* 2307:     */   {
/* 2308:2588 */     return crearFacturaDesdeDespacho(facturaCliente, idDespachoCliente, puntoVenta, null);
/* 2309:     */   }
/* 2310:     */   
/* 2311:     */   public com.asinfo.as2.entities.FacturaCliente crearFacturaDesdeDespacho(com.asinfo.as2.entities.FacturaCliente facturaCliente, int idDespachoCliente, PuntoDeVenta puntoVenta, Sucursal sucursal)
/* 2312:     */     throws AS2Exception, ExcepcionAS2
/* 2313:     */   {
/* 2314:2593 */     return crearFacturaDesdeDespacho(facturaCliente, idDespachoCliente, puntoVenta, sucursal, true);
/* 2315:     */   }
/* 2316:     */   
/* 2317:     */   public com.asinfo.as2.entities.FacturaCliente crearFacturaDesdeDespacho(com.asinfo.as2.entities.FacturaCliente facturaCliente, int idDespachoCliente, PuntoDeVenta puntoVenta, Sucursal sucursal, boolean validarListaPrecios)
/* 2318:     */     throws AS2Exception, ExcepcionAS2
/* 2319:     */   {
/* 2320:2598 */     com.asinfo.as2.entities.FacturaCliente facturaClienteExistente = buscarPorDespachoCliente(Integer.valueOf(idDespachoCliente));
/* 2321:2599 */     if (facturaClienteExistente != null) {
/* 2322:2600 */       throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioFacturaClienteImpl.MENSAJE_INFO_YA_EXISTE_FACTURA_DESPACHO", new String[] { facturaClienteExistente.getNumero() });
/* 2323:     */     }
/* 2324:2602 */     DespachoCliente despachoCliente = this.servicioDespachoCliente.cargarDetalleAFacturar(Integer.valueOf(idDespachoCliente));
/* 2325:2603 */     ExcepcionAS2 excepcionPrecio = null;
/* 2326:2604 */     ExcepcionAS2 excepcionSecuencia = null;
/* 2327:2606 */     if (facturaCliente == null)
/* 2328:     */     {
/* 2329:2607 */       facturaCliente = new com.asinfo.as2.entities.FacturaCliente();
/* 2330:2608 */       facturaCliente.setNumero("");
/* 2331:2609 */       facturaCliente.setFecha(new Date());
/* 2332:2610 */       facturaCliente.setEstado(Estado.PROCESADO);
/* 2333:     */     }
/* 2334:2613 */     facturaCliente.setIdOrganizacion(despachoCliente.getIdOrganizacion());
/* 2335:2614 */     if (sucursal == null) {
/* 2336:2615 */       facturaCliente.setSucursal(despachoCliente.getSucursal());
/* 2337:     */     } else {
/* 2338:2617 */       facturaCliente.setSucursal(sucursal);
/* 2339:     */     }
/* 2340:2619 */     facturaCliente.setFacturaClienteSRI(new FacturaClienteSRI());
/* 2341:2620 */     facturaCliente.getFacturaClienteSRI().setEstado(facturaCliente.getEstado());
/* 2342:2621 */     facturaCliente.getFacturaClienteSRI().setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 2343:2622 */     facturaCliente.getFacturaClienteSRI().setIdSucursal(facturaCliente.getSucursal().getId());
/* 2344:     */     
/* 2345:2624 */     facturaCliente.getFacturaClienteSRI().setFacturaCliente(facturaCliente);
/* 2346:2625 */     facturaCliente.setFacturaClienteSRI(facturaCliente.getFacturaClienteSRI());
/* 2347:     */     
/* 2348:2627 */     List<Documento> listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FACTURA_CLIENTE, facturaCliente
/* 2349:2628 */       .getIdOrganizacion());
/* 2350:2629 */     if (listaDocumentoCliente.size() > 0)
/* 2351:     */     {
/* 2352:2630 */       facturaCliente.setDocumento((Documento)listaDocumentoCliente.get(0));
/* 2353:     */       try
/* 2354:     */       {
/* 2355:2632 */         actualizarDocumento(facturaCliente, false, puntoVenta);
/* 2356:2633 */         this.servicioSecuencia.detach(facturaCliente.getDocumento().getSecuencia());
/* 2357:     */       }
/* 2358:     */       catch (ExcepcionAS2 e)
/* 2359:     */       {
/* 2360:2635 */         excepcionSecuencia = e;
/* 2361:     */       }
/* 2362:     */     }
/* 2363:     */     else
/* 2364:     */     {
/* 2365:2638 */       throw new AS2Exception("ERROR_FALTA_PARAMETRIZAR_DOCUMENTO", new String[] { DocumentoBase.FACTURA_CLIENTE.toString() });
/* 2366:     */     }
/* 2367:2641 */     facturaCliente.setEmpresa(despachoCliente.getEmpresa());
/* 2368:2642 */     facturaCliente.setSubempresa(despachoCliente.getSubempresa());
/* 2369:     */     
/* 2370:2644 */     excepcionPrecio = agregarDetalleDespachoAFactura(despachoCliente, facturaCliente, validarListaPrecios);
/* 2371:     */     
/* 2372:2646 */     facturaCliente.setDireccionEmpresa(despachoCliente.getDireccionEmpresa());
/* 2373:2647 */     if (despachoCliente.getPedidoCliente() != null) {
/* 2374:2649 */       facturaCliente.getEmpresa().setSoloLectura(true);
/* 2375:     */     }
/* 2376:2651 */     if (facturaCliente.getSubempresa() != null)
/* 2377:     */     {
/* 2378:2652 */       Empresa subempresa = facturaCliente.getSubempresa().getEmpresa();
/* 2379:2653 */       facturaCliente.setEmail(this.servicioEmpresa.cargarMails(subempresa, DocumentoBase.FACTURA_CLIENTE));
/* 2380:     */     }
/* 2381:     */     else
/* 2382:     */     {
/* 2383:2655 */       facturaCliente.setEmail(this.servicioEmpresa.cargarMails(facturaCliente.getEmpresa(), DocumentoBase.FACTURA_CLIENTE));
/* 2384:     */     }
/* 2385:2658 */     if (excepcionSecuencia != null) {
/* 2386:2659 */       throw excepcionSecuencia;
/* 2387:     */     }
/* 2388:2661 */     if (excepcionPrecio != null) {
/* 2389:2662 */       throw excepcionPrecio;
/* 2390:     */     }
/* 2391:2666 */     return facturaCliente;
/* 2392:     */   }
/* 2393:     */   
/* 2394:     */   public void actualizarDocumento(com.asinfo.as2.entities.FacturaCliente facturaCliente, boolean indicadorAutoimpresor, PuntoDeVenta puntoVenta)
/* 2395:     */     throws ExcepcionAS2
/* 2396:     */   {
/* 2397:2671 */     if (indicadorAutoimpresor) {
/* 2398:2672 */       facturaCliente.setAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(this.servicioAutorizacionAutoimpresorSRI
/* 2399:2673 */         .obtenerAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(facturaCliente.getDocumento().getDocumentoBase(), puntoVenta));
/* 2400:     */     }
/* 2401:2676 */     if ((facturaCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() == null) && 
/* 2402:2677 */       (facturaCliente.getDocumento().isIndicadorDocumentoTributario()))
/* 2403:     */     {
/* 2404:2678 */       PuntoDeVenta puntoDeVenta = cargarPuntoVenta(facturaCliente);
/* 2405:2679 */       cargarSecuencia(facturaCliente, puntoDeVenta);
/* 2406:     */     }
/* 2407:2683 */     if ((!facturaCliente.getDocumento().isIndicadorDocumentoExterior()) && (facturaCliente.getFacturaClienteSRI() != null))
/* 2408:     */     {
/* 2409:2684 */       facturaCliente.getFacturaClienteSRI().setDistritoRefrendo(null);
/* 2410:2685 */       facturaCliente.getFacturaClienteSRI().setRegimenRefrendo(null);
/* 2411:2686 */       facturaCliente.getFacturaClienteSRI().setAnioRefrendo(null);
/* 2412:2687 */       facturaCliente.getFacturaClienteSRI().setCorrelativoRefrendo(null);
/* 2413:2688 */       facturaCliente.getFacturaClienteSRI().setDocumentoTransporteRefrendo(null);
/* 2414:     */     }
/* 2415:     */   }
/* 2416:     */   
/* 2417:     */   public ExcepcionAS2 agregarDetalleDespachoAFactura(DespachoCliente despachoCliente, com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 2418:     */     throws ExcepcionAS2
/* 2419:     */   {
/* 2420:2694 */     return agregarDetalleDespachoAFactura(despachoCliente, facturaCliente, true);
/* 2421:     */   }
/* 2422:     */   
/* 2423:     */   public ExcepcionAS2 agregarDetalleDespachoAFactura(DespachoCliente despachoCliente, com.asinfo.as2.entities.FacturaCliente facturaCliente, boolean validarListaPrecios)
/* 2424:     */     throws ExcepcionAS2
/* 2425:     */   {
/* 2426:2699 */     ExcepcionAS2 excepcionPrecios = null;
/* 2427:     */     
/* 2428:2701 */     facturaCliente.setDespachoCliente(despachoCliente);
/* 2429:2702 */     facturaCliente.setProyecto(despachoCliente.getProyecto());
/* 2430:2703 */     facturaCliente.setDescripcion(despachoCliente.getDescripcion());
/* 2431:2705 */     if (despachoCliente.getPedidoCliente() != null)
/* 2432:     */     {
/* 2433:2706 */       PedidoCliente pedidoCliente = this.servicioPedidoCliente.cargarDetalle(despachoCliente.getPedidoCliente().getId(), false);
/* 2434:2707 */       facturaCliente.setPedidoCliente(pedidoCliente);
/* 2435:2708 */       facturaCliente.setZona(pedidoCliente.getZona());
/* 2436:2709 */       facturaCliente.setCanal(pedidoCliente.getCanal());
/* 2437:2710 */       facturaCliente.setCondicionPago(pedidoCliente.getCondicionPago());
/* 2438:2711 */       facturaCliente.setNumeroCuotas(pedidoCliente.getNumeroCuotas());
/* 2439:2712 */       facturaCliente.setDescripcion(pedidoCliente.getDescripcion());
/* 2440:2713 */       facturaCliente.setDireccionEmpresa(pedidoCliente.getDireccionEmpresa());
/* 2441:2714 */       facturaCliente.setAgenteComercial(pedidoCliente.getAgenteComercial());
/* 2442:2715 */       facturaCliente.setReferencia8(pedidoCliente.getReferencia8());
/* 2443:2716 */       facturaCliente.setReferencia9(pedidoCliente.getReferencia9());
/* 2444:     */     }
/* 2445:     */     else
/* 2446:     */     {
/* 2447:2718 */       facturaCliente.setCondicionPago(despachoCliente.getEmpresa().getCliente().getCondicionPago());
/* 2448:2719 */       facturaCliente.setNumeroCuotas(despachoCliente.getEmpresa().getCliente().getNumeroCuotas());
/* 2449:2720 */       if (despachoCliente.getEmpresa().getCliente().getAgenteComercial() != null) {
/* 2450:2721 */         facturaCliente.setAgenteComercial(despachoCliente.getEmpresa().getCliente().getAgenteComercial());
/* 2451:     */       }
/* 2452:     */     }
/* 2453:2724 */     Organizacion organizacion = this.servicioOrganizacion.cargarOrganizacionConfiguracion(facturaCliente.getIdOrganizacion());
/* 2454:2725 */     for (DetalleDespachoCliente ddc : despachoCliente.getListaDetalleDespachoCliente()) {
/* 2455:2726 */       if ((ddc.getDetalleFacturaCliente() == null) || (ddc.getDetalleFacturaCliente().getFacturaCliente().getEstado().equals(Estado.ANULADO)) || 
/* 2456:2727 */         (!DocumentoBase.FACTURA_CLIENTE.equals(ddc.getDetalleFacturaCliente().getFacturaCliente().getDocumento().getDocumentoBase())))
/* 2457:     */       {
/* 2458:2728 */         DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 2459:2729 */         dfc.setDetalleDespachoCliente(ddc);
/* 2460:2730 */         dfc.setCantidad(ddc.getCantidad().subtract(ddc.getCantidadDevuelta()));
/* 2461:2731 */         dfc.setUnidadVenta(ddc.getUnidadVenta());
/* 2462:2732 */         dfc.setPeso(ddc.getPeso());
/* 2463:2733 */         dfc.setDescripcion(ddc.getDescripcion());
/* 2464:2734 */         dfc.setIndicadorManejoPeso(ddc.isIndicadorManejoPeso());
/* 2465:2737 */         if (ddc.getDetallePedidoCliente() != null)
/* 2466:     */         {
/* 2467:2738 */           dfc.setPrecio(ddc.getDetallePedidoCliente().getPrecio());
/* 2468:2739 */           dfc.setDescuento(ddc.getDetallePedidoCliente().getDescuento());
/* 2469:2740 */           dfc.setIce(ddc.getDetallePedidoCliente().getIce());
/* 2470:2741 */           dfc.setIndicadorPorcentajeIce(ddc.getDetallePedidoCliente().isIndicadorPorcentajeIce());
/* 2471:2742 */           dfc.setCodigoIce(ddc.getDetallePedidoCliente().getCodigoIce());
/* 2472:2743 */           dfc.setIceLinea(ddc.getDetallePedidoCliente().getIceLinea());
/* 2473:     */         }
/* 2474:2746 */         facturaCliente.getListaDetalleFacturaCliente().add(dfc);
/* 2475:     */         try
/* 2476:     */         {
/* 2477:2749 */           if (!validarListaPrecios) {
/* 2478:2750 */             actualizarProducto(facturaCliente, dfc, ddc.getProducto(), false, organizacion
/* 2479:2751 */               .getOrganizacionConfiguracion().getTipoOrganizacion(), validarListaPrecios);
/* 2480:     */           } else {
/* 2481:2753 */             actualizarProducto(facturaCliente, dfc, ddc.getProducto(), false, organizacion
/* 2482:2754 */               .getOrganizacionConfiguracion().getTipoOrganizacion());
/* 2483:     */           }
/* 2484:     */         }
/* 2485:     */         catch (ExcepcionAS2 e)
/* 2486:     */         {
/* 2487:2757 */           if (ParametrosSistema.isBloqueoProductoListaPrecios(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue())
/* 2488:     */           {
/* 2489:2758 */             facturaCliente.getListaDetalleFacturaCliente().clear();
/* 2490:2759 */             facturaCliente.getListaCuentaPorCobrar().clear();
/* 2491:2760 */             totalizar(facturaCliente);
/* 2492:2761 */             excepcionPrecios = e;
/* 2493:2762 */             break;
/* 2494:     */           }
/* 2495:2764 */           if ("msg_error_producto_no_lista_precios".equals(e.getCodigoExcepcion())) {
/* 2496:2765 */             excepcionPrecios = e;
/* 2497:     */           } else {
/* 2498:2767 */             throw e;
/* 2499:     */           }
/* 2500:     */         }
/* 2501:     */       }
/* 2502:     */     }
/* 2503:2773 */     return excepcionPrecios;
/* 2504:     */   }
/* 2505:     */   
/* 2506:     */   public void actualizarProducto(com.asinfo.as2.entities.FacturaCliente facturaCliente, DetalleFacturaCliente dfc, Producto producto, boolean editar, TipoOrganizacion tipoOrganizacion)
/* 2507:     */     throws ExcepcionAS2Inventario, ExcepcionAS2
/* 2508:     */   {
/* 2509:2778 */     actualizarProducto(facturaCliente, dfc, producto, editar, tipoOrganizacion, true);
/* 2510:     */   }
/* 2511:     */   
/* 2512:     */   public void actualizarProducto(com.asinfo.as2.entities.FacturaCliente facturaCliente, DetalleFacturaCliente dfc, Producto producto, boolean editar, TipoOrganizacion tipoOrganizacion, boolean validarListaPrecios)
/* 2513:     */     throws ExcepcionAS2Inventario, ExcepcionAS2
/* 2514:     */   {
/* 2515:2784 */     producto = this.servicioProducto.cargaDetalle(producto.getId(), false);
/* 2516:2785 */     for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente()) {
/* 2517:2786 */       ipfc.setEliminado(true);
/* 2518:     */     }
/* 2519:2789 */     this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 2520:2790 */     dfc.setProducto(producto);
/* 2521:2791 */     dfc.setUnidadVenta(producto.getUnidadVenta());
/* 2522:2792 */     dfc.setFacturaCliente(facturaCliente);
/* 2523:2793 */     dfc.setIndicadorManejoPeso(producto.isIndicadorManejoPeso());
/* 2524:2794 */     dfc.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 2525:2795 */     dfc.setIce(producto.getIce());
/* 2526:2796 */     dfc.setCodigoIce(producto.getCodigoIce());
/* 2527:2797 */     if ((facturaCliente.getEmpresa().getCliente().isExcentoImpuestos()) || (facturaCliente.getDocumento().isIndicadorDocumentoExterior())) {
/* 2528:2798 */       dfc.setIndicadorImpuesto(false);
/* 2529:     */     } else {
/* 2530:2800 */       dfc.setIndicadorImpuesto(producto.isIndicadorImpuestos());
/* 2531:     */     }
/* 2532:2803 */     if (dfc.isIndicadorImpuesto()) {
/* 2533:2804 */       obtenerImpuestosProductos(dfc.getProducto(), dfc);
/* 2534:     */     }
/* 2535:2807 */     if (!editar)
/* 2536:     */     {
/* 2537:2808 */       boolean indicadorListaPrecioPorZona = ParametrosSistema.isIndicadorListaPrecioPorZona(facturaCliente.getIdOrganizacion()).booleanValue();
/* 2538:     */       
/* 2539:     */ 
/* 2540:2811 */       Integer idZona = null;
/* 2541:2812 */       if ((indicadorListaPrecioPorZona) && 
/* 2542:2813 */         (facturaCliente.getZona() != null)) {
/* 2543:2814 */         idZona = Integer.valueOf(facturaCliente.getZona().getId());
/* 2544:     */       }
/* 2545:2818 */       if (facturaCliente.getEmpresa().getCliente().getListaPrecios() != null)
/* 2546:     */       {
/* 2547:2820 */         if ((validarListaPrecios) || (facturaCliente.getPedidoCliente() == null))
/* 2548:     */         {
/* 2549:2821 */           DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(facturaCliente
/* 2550:2822 */             .getEmpresa().getCliente().getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), facturaCliente
/* 2551:2823 */             .getFecha(), idZona, facturaCliente.getNumero());
/* 2552:2824 */           dfc.setPrecio(dvlp.getPrecioUnitario());
/* 2553:     */         }
/* 2554:     */       }
/* 2555:     */       else {
/* 2556:2828 */         throw new ExcepcionAS2("msg_error_empresa_lista_precios");
/* 2557:     */       }
/* 2558:2833 */       if (facturaCliente.getEmpresa().getCliente().getListaDescuentos() != null)
/* 2559:     */       {
/* 2560:2835 */         ListaDescuentos ld = facturaCliente.getEmpresa().getCliente().getListaDescuentos();
/* 2561:2837 */         if (!ld.isIndicadorDescuentoPorProducto())
/* 2562:     */         {
/* 2563:2838 */           BigDecimal porcentajeDescuentoMaximo = this.servicioListaDescuentos.getPorcentajeDescuentoMaximoVigente(ld, facturaCliente.getFecha());
/* 2564:2839 */           dfc.getProducto().setTraDescuentoPorcentajeMaximo(porcentajeDescuentoMaximo);
/* 2565:2840 */           if ((dfc.getDescuento().compareTo(BigDecimal.ZERO) == 0) && 
/* 2566:2841 */             (ld.isIndicadorCargaAutomatica())) {
/* 2567:2842 */             dfc.setPorcentajeDescuento(porcentajeDescuentoMaximo);
/* 2568:     */           }
/* 2569:     */         }
/* 2570:     */         else
/* 2571:     */         {
/* 2572:2846 */           DetalleListaDescuentos descuentoMaximo = this.servicioListaDescuentos.getDatosListaDescuentosPorProducto(ld, dfc.getProducto());
/* 2573:2847 */           if (descuentoMaximo != null)
/* 2574:     */           {
/* 2575:2848 */             dfc.getProducto().setTraDescuentoPorcentajeMaximo(descuentoMaximo.getPorcentajeDescuentoMaximo());
/* 2576:2849 */             if ((dfc.getDescuento().compareTo(BigDecimal.ZERO) == 0) && 
/* 2577:2850 */               (ld.isIndicadorCargaAutomatica())) {
/* 2578:2851 */               dfc.setPorcentajeDescuento(descuentoMaximo.getPorcentajeDescuentoMaximo());
/* 2579:     */             }
/* 2580:     */           }
/* 2581:     */         }
/* 2582:     */       }
/* 2583:     */     }
/* 2584:2858 */     totalizar(facturaCliente);
/* 2585:2859 */     generarCuentaPorCobrar(facturaCliente);
/* 2586:     */   }
/* 2587:     */   
/* 2588:     */   public void guardarNotaFacturaCliente(NotaFacturaCliente notaFacturaCliente)
/* 2589:     */   {
/* 2590:2864 */     this.notaFacturaClienteDao.guardar(notaFacturaCliente);
/* 2591:     */   }
/* 2592:     */   
/* 2593:     */   public List<NotaFacturaCliente> cargarListaNotaFacturaCliente(com.asinfo.as2.entities.FacturaCliente facturaCliente)
/* 2594:     */   {
/* 2595:2869 */     Map<String, String> filtros = new HashMap();
/* 2596:2870 */     filtros.put("facturaCliente.idFacturaCliente", facturaCliente.getId() + "");
/* 2597:2871 */     return this.notaFacturaClienteDao.obtenerListaCombo(NotaFacturaCliente.class, "fechaCreacion", false, filtros);
/* 2598:     */   }
/* 2599:     */   
/* 2600:     */   public void contabilizar(List<com.asinfo.as2.entities.FacturaCliente> facturaCliente)
/* 2601:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 2602:     */   {
/* 2603:2876 */     InterfazContableProceso interfazContableProceso = new InterfazContableProceso();
/* 2604:2877 */     interfazContableProceso.setListaFacturaCliente(facturaCliente);
/* 2605:2878 */     interfazContableProceso.setDocumentoBase(DocumentoBase.INTERFAZ_VENTAS);
/* 2606:2879 */     interfazContableProceso.setFiltroDocumentoBase(DocumentoBase.FACTURA_CLIENTE);
/* 2607:2880 */     interfazContableProceso.setIdOrganizacion(((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).getIdOrganizacion());
/* 2608:2881 */     interfazContableProceso.setContabilizacionAutomatica(true);
/* 2609:2882 */     interfazContableProceso.setDescuentoImpuesto(((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).getDescuentoImpuesto());
/* 2610:2883 */     interfazContableProceso.setSucursal(((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).getSucursal());
/* 2611:2885 */     if (interfazContableProceso.getFiltroDocumento() == null) {
/* 2612:2886 */       interfazContableProceso.setFiltroDocumento(new Documento());
/* 2613:     */     }
/* 2614:2890 */     if (((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).getAsiento() != null) {
/* 2615:2891 */       interfazContableProceso.setAsiento(((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).getAsiento());
/* 2616:     */     }
/* 2617:2894 */     Asiento asiento = this.servicioInterfazContableProceso.generarAsiento(interfazContableProceso);
/* 2618:     */     
/* 2619:2896 */     asiento.setFecha(((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).getFecha());
/* 2620:2897 */     asiento.setDocumentoOrigen(((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).getDocumento());
/* 2621:     */     
/* 2622:2899 */     this.servicioAsiento.guardar(asiento);
/* 2623:     */     
/* 2624:2901 */     ((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).setEstado(Estado.CONTABILIZADO);
/* 2625:2902 */     ((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).setFechaContabilizacion(((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).getFecha());
/* 2626:2903 */     ((com.asinfo.as2.entities.FacturaCliente)facturaCliente.get(0)).setAsiento(asiento);
/* 2627:     */   }
/* 2628:     */   
/* 2629:     */   public List getReporteFacturaCliente(int idFacturaCliente)
/* 2630:     */     throws ExcepcionAS2
/* 2631:     */   {
/* 2632:2909 */     return getReporteFacturaCliente(idFacturaCliente, 1);
/* 2633:     */   }
/* 2634:     */   
/* 2635:     */   public List<com.asinfo.as2.entities.FacturaCliente> obtenerFacturasNotasCredito(int idOrganizacion, Date fechaDesde, Date fechaHasta, DocumentoBase documentoBase, int idEmpresa)
/* 2636:     */   {
/* 2637:2914 */     return this.facturaClienteDao.obtenerFacturasNotasCredito(idOrganizacion, fechaDesde, fechaHasta, documentoBase, idEmpresa);
/* 2638:     */   }
/* 2639:     */   
/* 2640:     */   public void actualizarAtributoEntidad(com.asinfo.as2.entities.FacturaCliente facturaCliente, HashMap<String, Object> campos)
/* 2641:     */   {
/* 2642:2919 */     this.facturaClienteDao.actualizarAtributoEntidad(facturaCliente, campos);
/* 2643:     */   }
/* 2644:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioFacturaClienteImpl
 * JD-Core Version:    0.7.0.1
 */