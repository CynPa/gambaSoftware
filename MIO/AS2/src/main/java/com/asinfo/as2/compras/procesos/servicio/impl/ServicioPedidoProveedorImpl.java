/*    1:     */ package com.asinfo.as2.compras.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    4:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*    5:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*    6:     */ import com.asinfo.as2.compras.reportes.ReportePedidoProveedorBean;
/*    7:     */ import com.asinfo.as2.compras.reportes.servicio.ServicioReportePedidoProveedor;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    9:     */ import com.asinfo.as2.dao.DetallePedidoProveedorDao;
/*   10:     */ import com.asinfo.as2.dao.GenericoDao;
/*   11:     */ import com.asinfo.as2.dao.PedidoProveedorDao;
/*   12:     */ import com.asinfo.as2.dao.reportes.compras.ReportePedidoSaldoPorRecibirDao;
/*   13:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   14:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   15:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   16:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   17:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   18:     */ import com.asinfo.as2.entities.Contacto;
/*   19:     */ import com.asinfo.as2.entities.CriterioContabilizacion;
/*   20:     */ import com.asinfo.as2.entities.CuentaContable;
/*   21:     */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   22:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   23:     */ import com.asinfo.as2.entities.DetalleSolicitudCompra;
/*   24:     */ import com.asinfo.as2.entities.DimensionContable;
/*   25:     */ import com.asinfo.as2.entities.Documento;
/*   26:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   27:     */ import com.asinfo.as2.entities.Empresa;
/*   28:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   29:     */ import com.asinfo.as2.entities.ImpuestoProductoPedidoProveedor;
/*   30:     */ import com.asinfo.as2.entities.Organizacion;
/*   31:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   32:     */ import com.asinfo.as2.entities.PedidoProveedor;
/*   33:     */ import com.asinfo.as2.entities.Producto;
/*   34:     */ import com.asinfo.as2.entities.Proveedor;
/*   35:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   36:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   37:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   38:     */ import com.asinfo.as2.entities.Sucursal;
/*   39:     */ import com.asinfo.as2.entities.TipoContacto;
/*   40:     */ import com.asinfo.as2.entities.presupuesto.DetallePresupuesto;
/*   41:     */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*   42:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   43:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   44:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   45:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   46:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   47:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   48:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   49:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   50:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   51:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   52:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   53:     */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioPresupuesto;
/*   54:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   55:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   56:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*   57:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   58:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   59:     */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*   60:     */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*   61:     */ import com.asinfo.as2.util.AppUtil;
/*   62:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   63:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   64:     */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   65:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   66:     */ import java.io.InputStream;
/*   67:     */ import java.io.PrintStream;
/*   68:     */ import java.math.BigDecimal;
/*   69:     */ import java.math.RoundingMode;
/*   70:     */ import java.text.SimpleDateFormat;
/*   71:     */ import java.util.ArrayList;
/*   72:     */ import java.util.Date;
/*   73:     */ import java.util.HashMap;
/*   74:     */ import java.util.Iterator;
/*   75:     */ import java.util.List;
/*   76:     */ import java.util.Map;
/*   77:     */ import javax.ejb.EJB;
/*   78:     */ import javax.ejb.SessionContext;
/*   79:     */ import javax.ejb.Stateless;
/*   80:     */ import javax.ejb.TransactionAttribute;
/*   81:     */ import javax.ejb.TransactionAttributeType;
/*   82:     */ import javax.ejb.TransactionManagement;
/*   83:     */ import javax.ejb.TransactionManagementType;
/*   84:     */ import net.sf.jasperreports.engine.JRDataSource;
/*   85:     */ import org.apache.log4j.Logger;
/*   86:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   87:     */ 
/*   88:     */ @Stateless
/*   89:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*   90:     */ public class ServicioPedidoProveedorImpl
/*   91:     */   extends AbstractServicioAS2
/*   92:     */   implements ServicioPedidoProveedor
/*   93:     */ {
/*   94:     */   private static final long serialVersionUID = -8193490757169357529L;
/*   95:     */   @EJB
/*   96:     */   private PedidoProveedorDao pedidoProveedorDao;
/*   97:     */   @EJB
/*   98:     */   private DetallePedidoProveedorDao detallePedidoProveedorDao;
/*   99:     */   @EJB
/*  100:     */   private ServicioSecuencia servicioSecuencia;
/*  101:     */   @EJB
/*  102:     */   private ServicioPeriodo servicioPeriodo;
/*  103:     */   @EJB
/*  104:     */   private GenericoDao<ImpuestoProductoPedidoProveedor> impuestoProductoPedidoProveedorDao;
/*  105:     */   @EJB
/*  106:     */   private ReportePedidoSaldoPorRecibirDao reportePedidoSaldoPorRecibirDao;
/*  107:     */   @EJB
/*  108:     */   private ServicioProducto servicioProducto;
/*  109:     */   @EJB
/*  110:     */   private transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  111:     */   @EJB
/*  112:     */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*  113:     */   @EJB
/*  114:     */   private ServicioOrganizacion servicioOrganizacion;
/*  115:     */   @EJB
/*  116:     */   private ServicioEmpresa servicioEmpresa;
/*  117:     */   @EJB
/*  118:     */   private ServicioReportePedidoProveedor servicioReportePedidoProveedor;
/*  119:     */   @EJB
/*  120:     */   private ServicioEnvioEmail servicioEnvioEmail;
/*  121:     */   @EJB
/*  122:     */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  123:     */   @EJB
/*  124:     */   private transient ServicioPresupuesto servicioPresupuesto;
/*  125:     */   @EJB
/*  126:     */   private ServicioUsuario servicioUsuario;
/*  127:     */   @EJB
/*  128:     */   private ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  129:     */   @EJB
/*  130:     */   private ServicioInventarioProducto servicioInventarioProducto;
/*  131:     */   private Proveedor proveedor;
/*  132:     */   @EJB
/*  133:     */   private GenericoDao<DetalleSolicitudCompra> detalleSolicitudCompraDao;
/*  134:     */   
/*  135:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  136:     */   public void guardar(PedidoProveedor pedidoProveedor)
/*  137:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  138:     */   {
/*  139:     */     try
/*  140:     */     {
/*  141: 145 */       OrganizacionConfiguracion organizacionConfiguracion = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/*  142:     */       
/*  143: 147 */       validar(pedidoProveedor, organizacionConfiguracion);
/*  144: 148 */       if (getDimensionPresupuesto(organizacionConfiguracion) != null) {
/*  145: 149 */         actualizarSaldosComprometidosPresupuesto(pedidoProveedor);
/*  146:     */       }
/*  147: 151 */       cargarSecuencia(pedidoProveedor);
/*  148:     */       
/*  149: 153 */       this.pedidoProveedorDao.guardar(pedidoProveedor);
/*  150: 155 */       for (DetallePedidoProveedor dpp : pedidoProveedor.getListaDetallePedidoProveedor())
/*  151:     */       {
/*  152: 157 */         List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = new ArrayList();
/*  153: 158 */         if (dpp.getId() != 0) {
/*  154: 159 */           listaDetalleRecepcionProveedor = this.detallePedidoProveedorDao.obtenerDetalleRecepcionProveedor(dpp);
/*  155:     */         }
/*  156: 162 */         if ((!pedidoProveedor.getIndicadorSolicitudCambioPrecio().booleanValue()) && (listaDetalleRecepcionProveedor.isEmpty())) {
/*  157: 163 */           if ((dpp.getProducto().getTipoProducto() == TipoProducto.ARTICULO) || ((dpp.getProducto().getTipoProducto() == TipoProducto.SERVICIO) && 
/*  158: 164 */             (ParametrosSistema.getRecibirServicio(pedidoProveedor.getIdOrganizacion()).booleanValue()))) {
/*  159: 165 */             dpp.setCantidadPorRecibir(dpp.getCantidad());
/*  160:     */           } else {
/*  161: 167 */             dpp.setCantidadPorRecibir(BigDecimal.ZERO);
/*  162:     */           }
/*  163:     */         }
/*  164: 170 */         if (listaDetalleRecepcionProveedor.isEmpty()) {
/*  165: 171 */           dpp.setCantidadPorFacturar(dpp.getCantidad());
/*  166:     */         }
/*  167: 174 */         for (ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor : dpp.getListaImpuestoProductoPedidoProveedor()) {
/*  168: 176 */           this.impuestoProductoPedidoProveedorDao.guardar(impuestoProductoPedidoProveedor);
/*  169:     */         }
/*  170: 179 */         dpp.setIdOrganizacion(pedidoProveedor.getIdOrganizacion());
/*  171: 180 */         this.detallePedidoProveedorDao.guardar(dpp);
/*  172: 183 */         if (dpp.getDetalleSolicitudCompra() != null)
/*  173:     */         {
/*  174: 184 */           DetalleSolicitudCompra dsc = dpp.getDetalleSolicitudCompra();
/*  175: 185 */           dsc.setIndicadorEnPedido(true);
/*  176: 186 */           this.detalleSolicitudCompraDao.guardar(dsc);
/*  177:     */         }
/*  178:     */       }
/*  179: 191 */       if ((pedidoProveedor.getEstado().equals(Estado.APROBADO)) && (!pedidoProveedor.getIndicadorSolicitudCambioPrecio().booleanValue())) {
/*  180:     */         try
/*  181:     */         {
/*  182: 193 */           enviarEmail(pedidoProveedor);
/*  183:     */         }
/*  184:     */         catch (Exception e)
/*  185:     */         {
/*  186: 195 */           System.out.println("Error al enviar el mail");
/*  187:     */         }
/*  188:     */       }
/*  189:     */     }
/*  190:     */     catch (ExcepcionAS2Financiero e)
/*  191:     */     {
/*  192: 202 */       this.context.setRollbackOnly();
/*  193: 203 */       throw e;
/*  194:     */     }
/*  195:     */     catch (ExcepcionAS2 e)
/*  196:     */     {
/*  197: 205 */       this.context.setRollbackOnly();
/*  198: 206 */       throw e;
/*  199:     */     }
/*  200:     */     catch (AS2Exception e)
/*  201:     */     {
/*  202: 208 */       this.context.setRollbackOnly();
/*  203: 209 */       throw e;
/*  204:     */     }
/*  205:     */     catch (Exception e)
/*  206:     */     {
/*  207: 211 */       this.context.setRollbackOnly();
/*  208: 212 */       throw new ExcepcionAS2Ventas(e);
/*  209:     */     }
/*  210:     */   }
/*  211:     */   
/*  212:     */   private void validar(PedidoProveedor pedidoProveedor, OrganizacionConfiguracion organizacionconfiguracion)
/*  213:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  214:     */   {
/*  215: 219 */     this.servicioPeriodo.buscarPorFecha(pedidoProveedor.getFecha(), pedidoProveedor.getIdOrganizacion(), pedidoProveedor
/*  216: 220 */       .getDocumento().getDocumentoBase());
/*  217: 221 */     if (pedidoProveedor.getTotalPedido().compareTo(BigDecimal.ZERO) == 0) {
/*  218: 222 */       throw new ExcepcionAS2("msg_error_total_pedido", "");
/*  219:     */     }
/*  220: 225 */     if (organizacionconfiguracion.getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_ADRIALPETRO)) {
/*  221: 226 */       for (DetallePedidoProveedor detallePedidoProveedor : pedidoProveedor.getListaDetallePedidoProveedor()) {
/*  222: 227 */         if ((!detallePedidoProveedor.isEliminado()) && 
/*  223: 228 */           (detallePedidoProveedor.getDimensionContable1() == null) && (detallePedidoProveedor.getDimensionContable2() == null) && 
/*  224: 229 */           (detallePedidoProveedor.getDimensionContable3() == null) && (detallePedidoProveedor.getDimensionContable4() == null) && 
/*  225: 230 */           (detallePedidoProveedor.getDimensionContable5() == null)) {
/*  226: 232 */           throw new ExcepcionAS2Financiero("msg_error_dimension_contable_pedido_proveedor", detallePedidoProveedor.getProducto().getNombre());
/*  227:     */         }
/*  228:     */       }
/*  229:     */     }
/*  230: 239 */     if (ParametrosSistema.isManejaDimesionPedidoProveedor(pedidoProveedor.getIdOrganizacion()).booleanValue()) {
/*  231: 240 */       for (DetallePedidoProveedor detallePedidoProveedor : pedidoProveedor.getListaDetallePedidoProveedor()) {
/*  232: 241 */         if (!detallePedidoProveedor.isEliminado())
/*  233:     */         {
/*  234: 242 */           if ((detallePedidoProveedor.getProducto().getTipoProducto().equals(TipoProducto.SERVICIO)) && 
/*  235: 243 */             (!organizacionconfiguracion.getNombreDimension1().equals("")) && 
/*  236: 244 */             (detallePedidoProveedor.getDimensionContable1() == null)) {
/*  237: 246 */             throw new ExcepcionAS2Financiero("msg_error_dimension_contable_pedido_proveedor", detallePedidoProveedor.getProducto().getNombre());
/*  238:     */           }
/*  239: 247 */           if ((detallePedidoProveedor.getProducto().getTipoProducto().equals(TipoProducto.SERVICIO)) && 
/*  240: 248 */             (!organizacionconfiguracion.getNombreDimension2().equals("")) && 
/*  241: 249 */             (detallePedidoProveedor.getDimensionContable2() == null)) {
/*  242: 251 */             throw new ExcepcionAS2Financiero("msg_error_dimension_contable_pedido_proveedor", detallePedidoProveedor.getProducto().getNombre());
/*  243:     */           }
/*  244: 252 */           if ((detallePedidoProveedor.getProducto().getTipoProducto().equals(TipoProducto.SERVICIO)) && 
/*  245: 253 */             (!organizacionconfiguracion.getNombreDimension3().equals("")) && 
/*  246: 254 */             (detallePedidoProveedor.getDimensionContable3() == null)) {
/*  247: 256 */             throw new ExcepcionAS2Financiero("msg_error_dimension_contable_pedido_proveedor", detallePedidoProveedor.getProducto().getNombre());
/*  248:     */           }
/*  249: 257 */           if ((detallePedidoProveedor.getProducto().getTipoProducto().equals(TipoProducto.SERVICIO)) && 
/*  250: 258 */             (!organizacionconfiguracion.getNombreDimension4().equals("")) && 
/*  251: 259 */             (detallePedidoProveedor.getDimensionContable4() == null)) {
/*  252: 261 */             throw new ExcepcionAS2Financiero("msg_error_dimension_contable_pedido_proveedor", detallePedidoProveedor.getProducto().getNombre());
/*  253:     */           }
/*  254: 262 */           if ((detallePedidoProveedor.getProducto().getTipoProducto().equals(TipoProducto.SERVICIO)) && 
/*  255: 263 */             (!organizacionconfiguracion.getNombreDimension5().equals("")) && 
/*  256: 264 */             (detallePedidoProveedor.getDimensionContable5() == null)) {
/*  257: 266 */             throw new ExcepcionAS2Financiero("msg_error_dimension_contable_pedido_proveedor", detallePedidoProveedor.getProducto().getNombre());
/*  258:     */           }
/*  259:     */         }
/*  260:     */       }
/*  261:     */     }
/*  262: 273 */     if ((pedidoProveedor.getEmpresa().getProveedor().getValorMaximoDocumento().compareTo(BigDecimal.ZERO) > 0) && 
/*  263: 274 */       (pedidoProveedor.getTotalPedido().compareTo(pedidoProveedor.getEmpresa().getProveedor().getValorMaximoDocumento()) > 0)) {
/*  264: 276 */       throw new ExcepcionAS2("msg_error_excedio_monto_maximo_compra", "     " + pedidoProveedor.getTotalPedido().toString() + ">" + pedidoProveedor.getEmpresa().getProveedor().getValorMaximoDocumento().toString());
/*  265:     */     }
/*  266:     */   }
/*  267:     */   
/*  268:     */   private String getDimensionPresupuesto(OrganizacionConfiguracion organizacionconfiguracion)
/*  269:     */   {
/*  270: 283 */     if ((!organizacionconfiguracion.getNombreDimension1().equals("")) && (organizacionconfiguracion.isIndicadorUsoPresupuestoDimension1())) {
/*  271: 284 */       return "1";
/*  272:     */     }
/*  273: 285 */     if ((!organizacionconfiguracion.getNombreDimension2().equals("")) && (organizacionconfiguracion.isIndicadorUsoPresupuestoDimension2())) {
/*  274: 286 */       return "2";
/*  275:     */     }
/*  276: 287 */     if ((!organizacionconfiguracion.getNombreDimension3().equals("")) && (organizacionconfiguracion.isIndicadorUsoPresupuestoDimension3())) {
/*  277: 288 */       return "3";
/*  278:     */     }
/*  279: 289 */     if ((!organizacionconfiguracion.getNombreDimension4().equals("")) && (organizacionconfiguracion.isIndicadorUsoPresupuestoDimension4())) {
/*  280: 290 */       return "4";
/*  281:     */     }
/*  282: 291 */     if ((!organizacionconfiguracion.getNombreDimension5().equals("")) && (organizacionconfiguracion.isIndicadorUsoPresupuestoDimension5())) {
/*  283: 292 */       return "5";
/*  284:     */     }
/*  285: 294 */     return null;
/*  286:     */   }
/*  287:     */   
/*  288:     */   private void actualizarSaldosComprometidosPresupuesto(PedidoProveedor pedidoProveedor)
/*  289:     */     throws ExcepcionAS2, AS2Exception
/*  290:     */   {
/*  291: 298 */     Presupuesto presupuesto = this.servicioPresupuesto.buscarPorFecha(pedidoProveedor.getFecha(), pedidoProveedor.getIdOrganizacion());
/*  292: 299 */     presupuesto = this.servicioPresupuesto.cargarDetalle(presupuesto.getId());
/*  293: 300 */     Map<String, CriterioContabilizacion> hashCriterioContabilizacionProducto = new HashMap();
/*  294: 301 */     Map<String, CriterioContabilizacion> hashCriterioContabilizacionCategoriaProducto = new HashMap();
/*  295: 302 */     Map<String, CriterioContabilizacion> hashCriterioContabilizacionSubCategoriaProducto = new HashMap();
/*  296: 303 */     Map<String, CriterioContabilizacion> hashCriterioContabilizacionNull = new HashMap();
/*  297: 304 */     for (Iterator localIterator1 = this.servicioDocumentoContabilizacion
/*  298: 305 */           .obtenerListaPorDocumentoBase(AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.PEDIDO_PROVEEDOR).iterator(); localIterator1.hasNext();)
/*  299:     */     {
/*  300: 304 */       dc = (DocumentoContabilizacion)localIterator1.next();
/*  301:     */       
/*  302: 306 */       List<CriterioContabilizacion> listCC = this.servicioDocumentoContabilizacion.cargarDetalle(dc.getId()).getListaCriterioContabilizacion();
/*  303: 307 */       FuncionesUtiles.ordenaLista(listCC, "orden");
/*  304: 308 */       for (CriterioContabilizacion cc : listCC) {
/*  305: 309 */         if (cc.getProducto() != null) {
/*  306: 310 */           hashCriterioContabilizacionProducto.put(cc.getProducto().getId() + "~" + cc.getProducto().getCodigo(), cc);
/*  307: 311 */         } else if (cc.getCategoriaProducto() != null) {
/*  308: 312 */           hashCriterioContabilizacionCategoriaProducto.put(cc.getCategoriaProducto().getId() + "~" + cc.getCategoriaProducto().getCodigo(), cc);
/*  309: 314 */         } else if (cc.getSubcategoriaProducto() != null) {
/*  310: 316 */           hashCriterioContabilizacionSubCategoriaProducto.put(cc.getSubcategoriaProducto().getId() + "~" + cc.getSubcategoriaProducto().getCodigo(), cc);
/*  311:     */         } else {
/*  312: 318 */           hashCriterioContabilizacionNull.put(cc.getCuentaContable().getId() + "~" + cc.getCuentaContable().getCodigo(), cc);
/*  313:     */         }
/*  314:     */       }
/*  315:     */     }
/*  316:     */     DocumentoContabilizacion dc;
/*  317: 322 */     Object hashDetallesPresupuesto = new HashMap();
/*  318: 323 */     for (DetallePresupuesto dpr : presupuesto.getListaDetallePresupuesto()) {
/*  319: 324 */       ((Map)hashDetallesPresupuesto).put(dpr.getCuentaContable().getId() + "~" + dpr.getDimensionContable().getId(), dpr);
/*  320:     */     }
/*  321: 326 */     CriterioContabilizacion criterioContabilizacion = null;
/*  322: 327 */     int mes = FuncionesUtiles.getMes(pedidoProveedor.getFechaEntrega());
/*  323: 328 */     for (DetallePedidoProveedor detallePedidoProveedor : pedidoProveedor.getListaDetallePedidoProveedor()) {
/*  324: 330 */       if (!detallePedidoProveedor.isEliminado())
/*  325:     */       {
/*  326: 332 */         if (hashCriterioContabilizacionProducto.containsKey(detallePedidoProveedor.getProducto().getId() + "~" + detallePedidoProveedor.getProducto().getCodigo()))
/*  327:     */         {
/*  328: 334 */           criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionProducto.get(detallePedidoProveedor.getProducto().getId() + "~" + detallePedidoProveedor.getProducto().getCodigo());
/*  329:     */         }
/*  330: 336 */         else if (hashCriterioContabilizacionCategoriaProducto.containsKey(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getCategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  331: 337 */           .getProducto().getSubcategoriaProducto().getCategoriaProducto().getCodigo()))
/*  332:     */         {
/*  333: 339 */           criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionCategoriaProducto.get(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getCategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  334: 340 */             .getProducto().getSubcategoriaProducto().getCategoriaProducto().getCodigo());
/*  335:     */         }
/*  336: 342 */         else if (hashCriterioContabilizacionSubCategoriaProducto.containsKey(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  337: 343 */           .getProducto().getSubcategoriaProducto().getCodigo()))
/*  338:     */         {
/*  339: 345 */           criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionSubCategoriaProducto.get(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  340: 346 */             .getProducto().getSubcategoriaProducto().getCodigo());
/*  341:     */         }
/*  342:     */         else
/*  343:     */         {
/*  344: 348 */           List<CriterioContabilizacion> listCCNulls = new ArrayList();
/*  345: 349 */           for (CriterioContabilizacion cd : hashCriterioContabilizacionNull.values()) {
/*  346: 350 */             listCCNulls.add(cd);
/*  347:     */           }
/*  348: 352 */           FuncionesUtiles.ordenaLista(listCCNulls, "orden");
/*  349: 353 */           if (listCCNulls.size() > 0) {
/*  350: 354 */             criterioContabilizacion = (CriterioContabilizacion)listCCNulls.get(0);
/*  351:     */           }
/*  352:     */         }
/*  353: 356 */         if ((criterioContabilizacion != null) && (detallePedidoProveedor.getDimensionContable5() != null))
/*  354:     */         {
/*  355: 358 */           DetallePresupuesto detallePresupuesto = (DetallePresupuesto)((Map)hashDetallesPresupuesto).get(criterioContabilizacion.getCuentaContable().getId() + "~" + detallePedidoProveedor.getDimensionContable5().getId());
/*  356: 359 */           if (detallePresupuesto != null)
/*  357:     */           {
/*  358: 360 */             if (pedidoProveedor.getId() == 0) {
/*  359: 361 */               actualizarDetalle(detallePresupuesto, detallePedidoProveedor, mes);
/*  360:     */             } else {
/*  361: 363 */               actualizarDetalleEditado(detallePresupuesto, detallePedidoProveedor, mes);
/*  362:     */             }
/*  363:     */           }
/*  364:     */           else {
/*  365: 367 */             throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioPedidoProveedorImpl.ERROR_CONFIGURACION_CRITERIO_CONTABILIZACION_PRESUPUESTO", new String[] {"" + detallePedidoProveedor.getProducto().getNombre() });
/*  366:     */           }
/*  367:     */         }
/*  368: 370 */         else if ((criterioContabilizacion == null) && (detallePedidoProveedor.getDimensionContable5() != null))
/*  369:     */         {
/*  370: 372 */           throw new AS2Exception("com.asinfo.as2.compras.procesos.servicio.impl.ServicioPedidoProveedorImpl.ERROR_CONFIGURACION_CRITERIO_CONTABILIZACION_PRESUPUESTO", new String[] {"" + detallePedidoProveedor.getProducto().getNombre() });
/*  371:     */         }
/*  372:     */       }
/*  373:     */     }
/*  374: 376 */     this.servicioPresupuesto.guardar(presupuesto);
/*  375:     */   }
/*  376:     */   
/*  377:     */   private void actualizarDetalle(DetallePresupuesto detallePresupuesto, DetallePedidoProveedor detallePedidoProveedor, int mes)
/*  378:     */     throws ExcepcionAS2
/*  379:     */   {
/*  380: 381 */     if (mes == 1)
/*  381:     */     {
/*  382: 383 */       validarDetallePresupuesto(detallePresupuesto.getValorEnero(), detallePresupuesto.getTransferenciasIngresoEnero(), detallePresupuesto
/*  383: 384 */         .getTransferenciasEgresoEnero(), detallePresupuesto.getIncrementosEnero(), detallePresupuesto
/*  384: 385 */         .getDecrementosEnero(), detallePresupuesto.getSaldoComprometidoEnero(), detallePedidoProveedor
/*  385: 386 */         .getPrecioLinea());
/*  386:     */       
/*  387: 388 */       detallePresupuesto.setSaldoComprometidoEnero(detallePresupuesto.getSaldoComprometidoEnero().add(detallePedidoProveedor.getPrecioLinea()));
/*  388:     */     }
/*  389: 389 */     else if (mes == 2)
/*  390:     */     {
/*  391: 390 */       validarDetallePresupuesto(detallePresupuesto.getValorFebrero(), detallePresupuesto.getTransferenciasIngresoFebrero(), detallePresupuesto
/*  392: 391 */         .getTransferenciasEgresoFebrero(), detallePresupuesto.getIncrementosFebrero(), detallePresupuesto
/*  393: 392 */         .getDecrementosFebrero(), detallePresupuesto.getSaldoComprometidoFebrero(), detallePedidoProveedor
/*  394: 393 */         .getPrecioLinea());
/*  395:     */       
/*  396: 395 */       detallePresupuesto
/*  397: 396 */         .setSaldoComprometidoFebrero(detallePresupuesto.getSaldoComprometidoFebrero().add(detallePedidoProveedor.getPrecioLinea()));
/*  398:     */     }
/*  399: 397 */     else if (mes == 3)
/*  400:     */     {
/*  401: 398 */       validarDetallePresupuesto(detallePresupuesto.getValorMarzo(), detallePresupuesto.getTransferenciasIngresoMarzo(), detallePresupuesto
/*  402: 399 */         .getTransferenciasEgresoMarzo(), detallePresupuesto.getIncrementosMarzo(), detallePresupuesto
/*  403: 400 */         .getDecrementosMarzo(), detallePresupuesto.getSaldoComprometidoMarzo(), detallePedidoProveedor
/*  404: 401 */         .getPrecioLinea());
/*  405:     */       
/*  406: 403 */       detallePresupuesto.setSaldoComprometidoMarzo(detallePresupuesto.getSaldoComprometidoMarzo().add(detallePedidoProveedor.getPrecioLinea()));
/*  407:     */     }
/*  408: 404 */     else if (mes == 4)
/*  409:     */     {
/*  410: 405 */       validarDetallePresupuesto(detallePresupuesto.getValorAbril(), detallePresupuesto.getTransferenciasIngresoAbril(), detallePresupuesto
/*  411: 406 */         .getTransferenciasEgresoAbril(), detallePresupuesto.getIncrementosAbril(), detallePresupuesto
/*  412: 407 */         .getDecrementosAbril(), detallePresupuesto.getSaldoComprometidoAbril(), detallePedidoProveedor
/*  413: 408 */         .getPrecioLinea());
/*  414:     */       
/*  415: 410 */       detallePresupuesto.setSaldoComprometidoAbril(detallePresupuesto.getSaldoComprometidoAbril().add(detallePedidoProveedor.getPrecioLinea()));
/*  416:     */     }
/*  417: 411 */     else if (mes == 5)
/*  418:     */     {
/*  419: 412 */       validarDetallePresupuesto(detallePresupuesto.getValorMayo(), detallePresupuesto.getTransferenciasIngresoMayo(), detallePresupuesto
/*  420: 413 */         .getTransferenciasEgresoMayo(), detallePresupuesto.getIncrementosMayo(), detallePresupuesto
/*  421: 414 */         .getDecrementosMayo(), detallePresupuesto.getSaldoComprometidoMayo(), detallePedidoProveedor.getPrecioLinea());
/*  422:     */       
/*  423: 416 */       detallePresupuesto.setSaldoComprometidoMayo(detallePresupuesto.getSaldoComprometidoMayo().add(detallePedidoProveedor.getPrecioLinea()));
/*  424:     */     }
/*  425: 417 */     else if (mes == 6)
/*  426:     */     {
/*  427: 418 */       validarDetallePresupuesto(detallePresupuesto.getValorJunio(), detallePresupuesto.getTransferenciasIngresoJunio(), detallePresupuesto
/*  428: 419 */         .getTransferenciasEgresoJunio(), detallePresupuesto.getIncrementosJunio(), detallePresupuesto
/*  429: 420 */         .getDecrementosJunio(), detallePresupuesto.getSaldoComprometidoJunio(), detallePedidoProveedor
/*  430: 421 */         .getPrecioLinea());
/*  431:     */       
/*  432: 423 */       detallePresupuesto.setSaldoComprometidoJunio(detallePresupuesto.getSaldoComprometidoJunio().add(detallePedidoProveedor.getPrecioLinea()));
/*  433:     */     }
/*  434: 424 */     else if (mes == 7)
/*  435:     */     {
/*  436: 425 */       validarDetallePresupuesto(detallePresupuesto.getValorJulio(), detallePresupuesto.getTransferenciasIngresoJulio(), detallePresupuesto
/*  437: 426 */         .getTransferenciasEgresoJulio(), detallePresupuesto.getIncrementosJulio(), detallePresupuesto
/*  438: 427 */         .getDecrementosJulio(), detallePresupuesto.getSaldoComprometidoJulio(), detallePedidoProveedor
/*  439: 428 */         .getPrecioLinea());
/*  440:     */       
/*  441: 430 */       detallePresupuesto.setSaldoComprometidoJulio(detallePresupuesto.getSaldoComprometidoJulio().add(detallePedidoProveedor.getPrecioLinea()));
/*  442:     */     }
/*  443: 431 */     else if (mes == 8)
/*  444:     */     {
/*  445: 432 */       validarDetallePresupuesto(detallePresupuesto.getValorAgosto(), detallePresupuesto.getTransferenciasIngresoAgosto(), detallePresupuesto
/*  446: 433 */         .getTransferenciasEgresoAgosto(), detallePresupuesto.getIncrementosAgosto(), detallePresupuesto
/*  447: 434 */         .getDecrementosAgosto(), detallePresupuesto.getSaldoComprometidoAgosto(), detallePedidoProveedor
/*  448: 435 */         .getPrecioLinea());
/*  449:     */       
/*  450: 437 */       detallePresupuesto
/*  451: 438 */         .setSaldoComprometidoAgosto(detallePresupuesto.getSaldoComprometidoAgosto().add(detallePedidoProveedor.getPrecioLinea()));
/*  452:     */     }
/*  453: 439 */     else if (mes == 9)
/*  454:     */     {
/*  455: 440 */       validarDetallePresupuesto(detallePresupuesto.getValorSeptiembre(), detallePresupuesto.getTransferenciasIngresoSeptiembre(), detallePresupuesto
/*  456: 441 */         .getTransferenciasEgresoSeptiembre(), detallePresupuesto.getIncrementosSeptiembre(), detallePresupuesto
/*  457: 442 */         .getDecrementosSeptiembre(), detallePresupuesto.getSaldoComprometidoSeptiembre(), detallePedidoProveedor
/*  458: 443 */         .getPrecioLinea());
/*  459:     */       
/*  460: 445 */       detallePresupuesto
/*  461: 446 */         .setSaldoComprometidoSeptiembre(detallePresupuesto.getSaldoComprometidoSeptiembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  462:     */     }
/*  463: 447 */     else if (mes == 10)
/*  464:     */     {
/*  465: 448 */       validarDetallePresupuesto(detallePresupuesto.getValorOctubre(), detallePresupuesto.getTransferenciasIngresoOctubre(), detallePresupuesto
/*  466: 449 */         .getTransferenciasEgresoOctubre(), detallePresupuesto.getIncrementosOctubre(), detallePresupuesto
/*  467: 450 */         .getDecrementosOctubre(), detallePresupuesto.getSaldoComprometidoOctubre(), detallePedidoProveedor
/*  468: 451 */         .getPrecioLinea());
/*  469:     */       
/*  470: 453 */       detallePresupuesto
/*  471: 454 */         .setSaldoComprometidoOctubre(detallePresupuesto.getSaldoComprometidoOctubre().add(detallePedidoProveedor.getPrecioLinea()));
/*  472:     */     }
/*  473: 455 */     else if (mes == 11)
/*  474:     */     {
/*  475: 456 */       validarDetallePresupuesto(detallePresupuesto.getValorNoviembre(), detallePresupuesto.getTransferenciasIngresoNoviembre(), detallePresupuesto
/*  476: 457 */         .getTransferenciasEgresoNoviembre(), detallePresupuesto.getIncrementosNoviembre(), detallePresupuesto
/*  477: 458 */         .getDecrementosNoviembre(), detallePresupuesto.getSaldoComprometidoNoviembre(), detallePedidoProveedor
/*  478: 459 */         .getPrecioLinea());
/*  479:     */       
/*  480: 461 */       detallePresupuesto
/*  481: 462 */         .setSaldoComprometidoNoviembre(detallePresupuesto.getSaldoComprometidoNoviembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  482:     */     }
/*  483: 463 */     else if (mes == 12)
/*  484:     */     {
/*  485: 464 */       validarDetallePresupuesto(detallePresupuesto.getValorDiciembre(), detallePresupuesto.getTransferenciasIngresoDiciembre(), detallePresupuesto
/*  486: 465 */         .getTransferenciasEgresoDiciembre(), detallePresupuesto.getIncrementosDiciembre(), detallePresupuesto
/*  487: 466 */         .getDecrementosDiciembre(), detallePresupuesto.getSaldoComprometidoDiciembre(), detallePedidoProveedor
/*  488: 467 */         .getPrecioLinea());
/*  489:     */       
/*  490: 469 */       detallePresupuesto
/*  491: 470 */         .setSaldoComprometidoDiciembre(detallePresupuesto.getSaldoComprometidoDiciembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  492:     */     }
/*  493:     */   }
/*  494:     */   
/*  495:     */   private void actualizarDetalleEditado(DetallePresupuesto detallePresupuesto, DetallePedidoProveedor detallePedidoProveedor, int mes)
/*  496:     */     throws ExcepcionAS2
/*  497:     */   {
/*  498: 476 */     DetallePedidoProveedor detallePedidoProveedorBD = (DetallePedidoProveedor)this.detallePedidoProveedorDao.buscarPorId(Integer.valueOf(detallePedidoProveedor.getId()));
/*  499: 477 */     if (detallePedidoProveedorBD.getPrecioLinea().compareTo(detallePedidoProveedor.getPrecioLinea()) != 0) {
/*  500: 478 */       if (mes == 1)
/*  501:     */       {
/*  502: 479 */         detallePresupuesto.setSaldoComprometidoEnero(detallePresupuesto
/*  503: 480 */           .getSaldoComprometidoEnero().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  504: 481 */         validarDetallePresupuesto(detallePresupuesto.getValorEnero(), detallePresupuesto.getTransferenciasIngresoEnero(), detallePresupuesto
/*  505: 482 */           .getTransferenciasEgresoEnero(), detallePresupuesto.getIncrementosEnero(), detallePresupuesto
/*  506: 483 */           .getDecrementosEnero(), detallePresupuesto.getSaldoComprometidoEnero(), detallePedidoProveedor
/*  507: 484 */           .getPrecioLinea());
/*  508:     */         
/*  509: 486 */         detallePresupuesto
/*  510: 487 */           .setSaldoComprometidoEnero(detallePresupuesto.getSaldoComprometidoEnero().add(detallePedidoProveedor.getPrecioLinea()));
/*  511:     */       }
/*  512: 488 */       else if (mes == 2)
/*  513:     */       {
/*  514: 489 */         detallePresupuesto.setSaldoComprometidoFebrero(detallePresupuesto
/*  515: 490 */           .getSaldoComprometidoFebrero().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  516: 491 */         validarDetallePresupuesto(detallePresupuesto.getValorFebrero(), detallePresupuesto.getTransferenciasIngresoFebrero(), detallePresupuesto
/*  517: 492 */           .getTransferenciasEgresoFebrero(), detallePresupuesto.getIncrementosFebrero(), detallePresupuesto
/*  518: 493 */           .getDecrementosFebrero(), detallePresupuesto.getSaldoComprometidoFebrero(), detallePedidoProveedor
/*  519: 494 */           .getPrecioLinea());
/*  520:     */         
/*  521: 496 */         detallePresupuesto
/*  522: 497 */           .setSaldoComprometidoFebrero(detallePresupuesto.getSaldoComprometidoFebrero().add(detallePedidoProveedor.getPrecioLinea()));
/*  523:     */       }
/*  524: 498 */       else if (mes == 3)
/*  525:     */       {
/*  526: 499 */         detallePresupuesto.setSaldoComprometidoMarzo(detallePresupuesto
/*  527: 500 */           .getSaldoComprometidoMarzo().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  528: 501 */         validarDetallePresupuesto(detallePresupuesto.getValorMarzo(), detallePresupuesto.getTransferenciasIngresoMarzo(), detallePresupuesto
/*  529: 502 */           .getTransferenciasEgresoMarzo(), detallePresupuesto.getIncrementosMarzo(), detallePresupuesto
/*  530: 503 */           .getDecrementosMarzo(), detallePresupuesto.getSaldoComprometidoMarzo(), detallePedidoProveedor
/*  531: 504 */           .getPrecioLinea());
/*  532:     */         
/*  533: 506 */         detallePresupuesto
/*  534: 507 */           .setSaldoComprometidoMarzo(detallePresupuesto.getSaldoComprometidoMarzo().add(detallePedidoProveedor.getPrecioLinea()));
/*  535:     */       }
/*  536: 508 */       else if (mes == 4)
/*  537:     */       {
/*  538: 509 */         detallePresupuesto.setSaldoComprometidoAbril(detallePresupuesto
/*  539: 510 */           .getSaldoComprometidoAbril().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  540: 511 */         validarDetallePresupuesto(detallePresupuesto.getValorAbril(), detallePresupuesto.getTransferenciasIngresoAbril(), detallePresupuesto
/*  541: 512 */           .getTransferenciasEgresoAbril(), detallePresupuesto.getIncrementosAbril(), detallePresupuesto
/*  542: 513 */           .getDecrementosAbril(), detallePresupuesto.getSaldoComprometidoAbril(), detallePedidoProveedor
/*  543: 514 */           .getPrecioLinea());
/*  544:     */         
/*  545: 516 */         detallePresupuesto
/*  546: 517 */           .setSaldoComprometidoAbril(detallePresupuesto.getSaldoComprometidoAbril().add(detallePedidoProveedor.getPrecioLinea()));
/*  547:     */       }
/*  548: 518 */       else if (mes == 5)
/*  549:     */       {
/*  550: 520 */         detallePresupuesto.setSaldoComprometidoMayo(detallePresupuesto.getSaldoComprometidoMayo().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  551: 521 */         validarDetallePresupuesto(detallePresupuesto.getValorMayo(), detallePresupuesto.getTransferenciasIngresoMayo(), detallePresupuesto
/*  552: 522 */           .getTransferenciasEgresoMayo(), detallePresupuesto.getIncrementosMayo(), detallePresupuesto
/*  553: 523 */           .getDecrementosMayo(), detallePresupuesto.getSaldoComprometidoMayo(), detallePedidoProveedor
/*  554: 524 */           .getPrecioLinea());
/*  555:     */         
/*  556: 526 */         detallePresupuesto
/*  557: 527 */           .setSaldoComprometidoMayo(detallePresupuesto.getSaldoComprometidoMayo().add(detallePedidoProveedor.getPrecioLinea()));
/*  558:     */       }
/*  559: 528 */       else if (mes == 6)
/*  560:     */       {
/*  561: 529 */         detallePresupuesto.setSaldoComprometidoJunio(detallePresupuesto
/*  562: 530 */           .getSaldoComprometidoJunio().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  563: 531 */         validarDetallePresupuesto(detallePresupuesto.getValorJunio(), detallePresupuesto.getTransferenciasIngresoJunio(), detallePresupuesto
/*  564: 532 */           .getTransferenciasEgresoJunio(), detallePresupuesto.getIncrementosJunio(), detallePresupuesto
/*  565: 533 */           .getDecrementosJunio(), detallePresupuesto.getSaldoComprometidoJunio(), detallePedidoProveedor
/*  566: 534 */           .getPrecioLinea());
/*  567:     */         
/*  568: 536 */         detallePresupuesto
/*  569: 537 */           .setSaldoComprometidoJunio(detallePresupuesto.getSaldoComprometidoJunio().add(detallePedidoProveedor.getPrecioLinea()));
/*  570:     */       }
/*  571: 538 */       else if (mes == 7)
/*  572:     */       {
/*  573: 539 */         detallePresupuesto.setSaldoComprometidoJulio(detallePresupuesto
/*  574: 540 */           .getSaldoComprometidoJulio().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  575: 541 */         validarDetallePresupuesto(detallePresupuesto.getValorJulio(), detallePresupuesto.getTransferenciasIngresoJulio(), detallePresupuesto
/*  576: 542 */           .getTransferenciasEgresoJulio(), detallePresupuesto.getIncrementosJulio(), detallePresupuesto
/*  577: 543 */           .getDecrementosJulio(), detallePresupuesto.getSaldoComprometidoJulio(), detallePedidoProveedor
/*  578: 544 */           .getPrecioLinea());
/*  579:     */         
/*  580: 546 */         detallePresupuesto
/*  581: 547 */           .setSaldoComprometidoJulio(detallePresupuesto.getSaldoComprometidoJulio().add(detallePedidoProveedor.getPrecioLinea()));
/*  582:     */       }
/*  583: 548 */       else if (mes == 8)
/*  584:     */       {
/*  585: 549 */         detallePresupuesto.setSaldoComprometidoAgosto(detallePresupuesto
/*  586: 550 */           .getSaldoComprometidoAgosto().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  587: 551 */         validarDetallePresupuesto(detallePresupuesto.getValorAgosto(), detallePresupuesto.getTransferenciasIngresoAgosto(), detallePresupuesto
/*  588: 552 */           .getTransferenciasEgresoAgosto(), detallePresupuesto.getIncrementosAgosto(), detallePresupuesto
/*  589: 553 */           .getDecrementosAgosto(), detallePresupuesto.getSaldoComprometidoAgosto(), detallePedidoProveedor
/*  590: 554 */           .getPrecioLinea());
/*  591:     */         
/*  592: 556 */         detallePresupuesto
/*  593: 557 */           .setSaldoComprometidoAgosto(detallePresupuesto.getSaldoComprometidoAgosto().add(detallePedidoProveedor.getPrecioLinea()));
/*  594:     */       }
/*  595: 558 */       else if (mes == 9)
/*  596:     */       {
/*  597: 559 */         detallePresupuesto.setSaldoComprometidoSeptiembre(detallePresupuesto
/*  598: 560 */           .getSaldoComprometidoSeptiembre().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  599: 561 */         validarDetallePresupuesto(detallePresupuesto.getValorSeptiembre(), detallePresupuesto.getTransferenciasIngresoSeptiembre(), detallePresupuesto
/*  600: 562 */           .getTransferenciasEgresoSeptiembre(), detallePresupuesto.getIncrementosSeptiembre(), detallePresupuesto
/*  601: 563 */           .getDecrementosSeptiembre(), detallePresupuesto.getSaldoComprometidoSeptiembre(), detallePedidoProveedor
/*  602: 564 */           .getPrecioLinea());
/*  603:     */         
/*  604: 566 */         detallePresupuesto.setSaldoComprometidoSeptiembre(detallePresupuesto
/*  605: 567 */           .getSaldoComprometidoSeptiembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  606:     */       }
/*  607: 568 */       else if (mes == 10)
/*  608:     */       {
/*  609: 569 */         detallePresupuesto.setSaldoComprometidoOctubre(detallePresupuesto
/*  610: 570 */           .getSaldoComprometidoOctubre().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  611: 571 */         validarDetallePresupuesto(detallePresupuesto.getValorOctubre(), detallePresupuesto.getTransferenciasIngresoOctubre(), detallePresupuesto
/*  612: 572 */           .getTransferenciasEgresoOctubre(), detallePresupuesto.getIncrementosOctubre(), detallePresupuesto
/*  613: 573 */           .getDecrementosOctubre(), detallePresupuesto.getSaldoComprometidoOctubre(), detallePedidoProveedor
/*  614: 574 */           .getPrecioLinea());
/*  615:     */         
/*  616: 576 */         detallePresupuesto
/*  617: 577 */           .setSaldoComprometidoOctubre(detallePresupuesto.getSaldoComprometidoOctubre().add(detallePedidoProveedor.getPrecioLinea()));
/*  618:     */       }
/*  619: 578 */       else if (mes == 11)
/*  620:     */       {
/*  621: 579 */         detallePresupuesto.setSaldoComprometidoNoviembre(detallePresupuesto
/*  622: 580 */           .getSaldoComprometidoNoviembre().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  623: 581 */         validarDetallePresupuesto(detallePresupuesto.getValorNoviembre(), detallePresupuesto.getTransferenciasIngresoNoviembre(), detallePresupuesto
/*  624: 582 */           .getTransferenciasEgresoNoviembre(), detallePresupuesto.getIncrementosNoviembre(), detallePresupuesto
/*  625: 583 */           .getDecrementosNoviembre(), detallePresupuesto.getSaldoComprometidoNoviembre(), detallePedidoProveedor
/*  626: 584 */           .getPrecioLinea());
/*  627:     */         
/*  628: 586 */         detallePresupuesto.setSaldoComprometidoNoviembre(detallePresupuesto
/*  629: 587 */           .getSaldoComprometidoNoviembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  630:     */       }
/*  631: 588 */       else if (mes == 12)
/*  632:     */       {
/*  633: 589 */         detallePresupuesto.setSaldoComprometidoDiciembre(detallePresupuesto
/*  634: 590 */           .getSaldoComprometidoDiciembre().subtract(detallePedidoProveedorBD.getPrecioLinea()));
/*  635: 591 */         validarDetallePresupuesto(detallePresupuesto.getValorDiciembre(), detallePresupuesto.getTransferenciasIngresoDiciembre(), detallePresupuesto
/*  636: 592 */           .getTransferenciasEgresoDiciembre(), detallePresupuesto.getIncrementosDiciembre(), detallePresupuesto
/*  637: 593 */           .getDecrementosDiciembre(), detallePresupuesto.getSaldoComprometidoDiciembre(), detallePedidoProveedor
/*  638: 594 */           .getPrecioLinea());
/*  639:     */         
/*  640: 596 */         detallePresupuesto.setSaldoComprometidoDiciembre(detallePresupuesto
/*  641: 597 */           .getSaldoComprometidoDiciembre().add(detallePedidoProveedor.getPrecioLinea()));
/*  642:     */       }
/*  643:     */     }
/*  644:     */   }
/*  645:     */   
/*  646:     */   private void validarDetallePresupuesto(BigDecimal valorInicial, BigDecimal transferenciasI, BigDecimal transferenciasE, BigDecimal incrementos, BigDecimal decrementos, BigDecimal saldoComprometidoActual, BigDecimal valorAComparar)
/*  647:     */     throws ExcepcionAS2
/*  648:     */   {
/*  649: 605 */     BigDecimal result = valorInicial.add(transferenciasI).add(incrementos).subtract(decrementos).subtract(transferenciasE).subtract(saldoComprometidoActual);
/*  650: 606 */     if (result.compareTo(valorAComparar) < 0) {
/*  651: 607 */       throw new ExcepcionAS2("msg_error_excedio_monto_presupuestado", "     " + valorAComparar.toString() + ">" + result.toString());
/*  652:     */     }
/*  653:     */   }
/*  654:     */   
/*  655:     */   private void cargarSecuencia(PedidoProveedor pedidoProveedor)
/*  656:     */     throws ExcepcionAS2
/*  657:     */   {
/*  658: 618 */     if (pedidoProveedor.getNumero().isEmpty())
/*  659:     */     {
/*  660: 619 */       String numero = "";
/*  661: 620 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(pedidoProveedor.getDocumento().getIdDocumento(), pedidoProveedor.getFecha());
/*  662: 621 */       pedidoProveedor.setNumero(numero);
/*  663:     */     }
/*  664:     */   }
/*  665:     */   
/*  666:     */   public PedidoProveedor buscarPorId(Integer id)
/*  667:     */     throws ExcepcionAS2
/*  668:     */   {
/*  669: 632 */     return (PedidoProveedor)this.pedidoProveedorDao.buscarPorId(id);
/*  670:     */   }
/*  671:     */   
/*  672:     */   public PedidoProveedor cargarDetalle(Integer idPedidoProveedor)
/*  673:     */     throws ExcepcionAS2Compras
/*  674:     */   {
/*  675: 642 */     return this.pedidoProveedorDao.cargarDetalle(idPedidoProveedor.intValue());
/*  676:     */   }
/*  677:     */   
/*  678:     */   public void totalizar(PedidoProveedor pedidoProveedor)
/*  679:     */     throws ExcepcionAS2Compras
/*  680:     */   {
/*  681: 653 */     BigDecimal total = BigDecimal.ZERO;
/*  682: 654 */     BigDecimal descuento = BigDecimal.ZERO;
/*  683: 656 */     for (DetallePedidoProveedor dpp : pedidoProveedor.getListaDetallePedidoProveedor()) {
/*  684: 658 */       if (!dpp.isEliminado())
/*  685:     */       {
/*  686: 660 */         dpp.setDescuento(dpp.getPrecio().multiply(dpp.getPorcentajeDescuento()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/*  687:     */         
/*  688: 662 */         total = total.add(dpp.getPrecioLinea());
/*  689: 663 */         descuento = descuento.add(dpp.getDescuentoLinea());
/*  690:     */       }
/*  691:     */     }
/*  692: 667 */     totalizarImpuesto(pedidoProveedor);
/*  693: 668 */     pedidoProveedor.setTotal(FuncionesUtiles.redondearBigDecimal(total));
/*  694: 669 */     pedidoProveedor.setDescuento(FuncionesUtiles.redondearBigDecimal(descuento));
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void totalizarImpuesto(PedidoProveedor pedidoProveedor)
/*  698:     */     throws ExcepcionAS2Compras
/*  699:     */   {
/*  700: 680 */     BigDecimal totalBaseImponible = BigDecimal.ZERO;
/*  701: 681 */     BigDecimal totalImpuestoProducto = BigDecimal.ZERO;
/*  702: 683 */     for (DetallePedidoProveedor dpp : pedidoProveedor.getListaDetallePedidoProveedor()) {
/*  703: 685 */       if (!dpp.isEliminado())
/*  704:     */       {
/*  705: 687 */         totalBaseImponible = totalBaseImponible.add(dpp.getBaseImponible());
/*  706: 688 */         totalImpuestoProducto = totalImpuestoProducto.add(dpp.getValorImpuestosLinea());
/*  707:     */       }
/*  708:     */     }
/*  709: 692 */     pedidoProveedor.setBaseImponibleTotal(FuncionesUtiles.redondearBigDecimal(totalBaseImponible));
/*  710: 693 */     pedidoProveedor.setImpuesto(FuncionesUtiles.redondearBigDecimal(totalImpuestoProducto));
/*  711:     */   }
/*  712:     */   
/*  713:     */   public List<PedidoProveedor> listaPedidosPorRecibir(int idEmpresa)
/*  714:     */   {
/*  715: 704 */     return this.pedidoProveedorDao.listaPedidosPorRecibir(idEmpresa);
/*  716:     */   }
/*  717:     */   
/*  718:     */   public List<PedidoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  719:     */     throws ExcepcionAS2
/*  720:     */   {
/*  721: 717 */     return this.pedidoProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  722:     */   }
/*  723:     */   
/*  724:     */   public List<PedidoProveedor> listaPedidosPorFacturar(int idEmpresa)
/*  725:     */   {
/*  726: 727 */     return this.pedidoProveedorDao.listaPedidosPorFacturar(idEmpresa);
/*  727:     */   }
/*  728:     */   
/*  729:     */   public List getReportePedidoProveedor(int idPedidoProveedor)
/*  730:     */     throws ExcepcionAS2
/*  731:     */   {
/*  732: 738 */     return this.pedidoProveedorDao.getReportePedidoProveedor(idPedidoProveedor);
/*  733:     */   }
/*  734:     */   
/*  735:     */   public int contarPorCriterio(Map<String, String> filters)
/*  736:     */   {
/*  737: 760 */     return this.pedidoProveedorDao.contarPorCriterio(filters);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void actualizarEstado(Integer idPedidoProveedor, Estado estado)
/*  741:     */   {
/*  742:     */     try
/*  743:     */     {
/*  744: 767 */       PedidoProveedor pedidoProveedor = this.servicioPedidoProveedor.buscarPorId(idPedidoProveedor);
/*  745: 768 */       this.pedidoProveedorDao.actualizarEstado(idPedidoProveedor, estado, pedidoProveedor.getDescripcionCambioEstado(), pedidoProveedor
/*  746: 769 */         .getUsuarioCambioEstado(), pedidoProveedor.getFechaCambioEstado(), false);
/*  747:     */     }
/*  748:     */     catch (ExcepcionAS2 e)
/*  749:     */     {
/*  750: 771 */       e.printStackTrace();
/*  751:     */     }
/*  752:     */   }
/*  753:     */   
/*  754:     */   public void anular(PedidoProveedor pedidoProveedor)
/*  755:     */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero
/*  756:     */   {
/*  757: 783 */     esEditable(pedidoProveedor);
/*  758: 784 */     OrganizacionConfiguracion organizacionConfiguracion = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/*  759: 785 */     if (getDimensionPresupuesto(organizacionConfiguracion) != null) {
/*  760: 786 */       reversarSaldosComprometidosPresupuesto(cargarDetalle(Integer.valueOf(pedidoProveedor.getId())));
/*  761:     */     }
/*  762: 788 */     this.pedidoProveedorDao.actualizarEstado(Integer.valueOf(pedidoProveedor.getId()), Estado.ANULADO, "", "", null, false);
/*  763:     */   }
/*  764:     */   
/*  765:     */   private void reversarSaldosComprometidosPresupuesto(PedidoProveedor pedidoProveedor)
/*  766:     */   {
/*  767:     */     try
/*  768:     */     {
/*  769: 794 */       Presupuesto presupuesto = this.servicioPresupuesto.buscarPorFecha(pedidoProveedor.getFecha(), pedidoProveedor.getIdOrganizacion());
/*  770: 795 */       presupuesto = this.servicioPresupuesto.cargarDetalle(presupuesto.getId());
/*  771: 796 */       Map<String, CriterioContabilizacion> hashCriterioContabilizacionProducto = new HashMap();
/*  772: 797 */       Map<String, CriterioContabilizacion> hashCriterioContabilizacionCategoriaProducto = new HashMap();
/*  773: 798 */       Map<String, CriterioContabilizacion> hashCriterioContabilizacionSubCategoriaProducto = new HashMap();
/*  774: 799 */       Map<String, CriterioContabilizacion> hashCriterioContabilizacionNull = new HashMap();
/*  775: 800 */       for (Iterator localIterator1 = this.servicioDocumentoContabilizacion
/*  776: 801 */             .obtenerListaPorDocumentoBase(AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.PEDIDO_PROVEEDOR).iterator(); localIterator1.hasNext();)
/*  777:     */       {
/*  778: 800 */         dc = (DocumentoContabilizacion)localIterator1.next();
/*  779:     */         
/*  780: 802 */         List<CriterioContabilizacion> listCC = this.servicioDocumentoContabilizacion.cargarDetalle(dc.getId()).getListaCriterioContabilizacion();
/*  781: 803 */         FuncionesUtiles.ordenaLista(listCC, "orden");
/*  782: 804 */         for (CriterioContabilizacion cc : listCC) {
/*  783: 805 */           if (cc.getProducto() != null) {
/*  784: 806 */             hashCriterioContabilizacionProducto.put(cc.getProducto().getId() + "~" + cc.getProducto().getCodigo(), cc);
/*  785: 807 */           } else if (cc.getCategoriaProducto() != null) {
/*  786: 809 */             hashCriterioContabilizacionCategoriaProducto.put(cc.getCategoriaProducto().getId() + "~" + cc.getCategoriaProducto().getCodigo(), cc);
/*  787: 810 */           } else if (cc.getSubcategoriaProducto() != null) {
/*  788: 812 */             hashCriterioContabilizacionSubCategoriaProducto.put(cc.getSubcategoriaProducto().getId() + "~" + cc.getSubcategoriaProducto().getCodigo(), cc);
/*  789:     */           } else {
/*  790: 814 */             hashCriterioContabilizacionNull.put(cc.getCuentaContable().getId() + "~" + cc.getCuentaContable().getCodigo(), cc);
/*  791:     */           }
/*  792:     */         }
/*  793:     */       }
/*  794:     */       DocumentoContabilizacion dc;
/*  795: 818 */       Object hashDetallesPresupuesto = new HashMap();
/*  796: 819 */       for (DetallePresupuesto dpr : presupuesto.getListaDetallePresupuesto()) {
/*  797: 820 */         ((Map)hashDetallesPresupuesto).put(dpr.getCuentaContable().getId() + "~" + dpr.getDimensionContable().getId(), dpr);
/*  798:     */       }
/*  799: 822 */       CriterioContabilizacion criterioContabilizacion = null;
/*  800: 823 */       int mes = FuncionesUtiles.getMes(pedidoProveedor.getFechaEntrega());
/*  801: 824 */       for (DetallePedidoProveedor detallePedidoProveedor : pedidoProveedor.getListaDetallePedidoProveedor()) {
/*  802: 826 */         if (!detallePedidoProveedor.isEliminado())
/*  803:     */         {
/*  804: 828 */           if (hashCriterioContabilizacionProducto.containsKey(detallePedidoProveedor.getProducto().getId() + "~" + detallePedidoProveedor.getProducto().getCodigo()))
/*  805:     */           {
/*  806: 830 */             criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionProducto.get(detallePedidoProveedor.getProducto().getId() + "~" + detallePedidoProveedor.getProducto().getCodigo());
/*  807:     */           }
/*  808: 832 */           else if (hashCriterioContabilizacionCategoriaProducto.containsKey(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getCategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  809: 833 */             .getProducto().getSubcategoriaProducto().getCategoriaProducto().getCodigo()))
/*  810:     */           {
/*  811: 835 */             criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionCategoriaProducto.get(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getCategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  812: 836 */               .getProducto().getSubcategoriaProducto().getCategoriaProducto().getCodigo());
/*  813:     */           }
/*  814: 838 */           else if (hashCriterioContabilizacionSubCategoriaProducto.containsKey(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  815: 839 */             .getProducto().getSubcategoriaProducto().getCodigo()))
/*  816:     */           {
/*  817: 841 */             criterioContabilizacion = (CriterioContabilizacion)hashCriterioContabilizacionSubCategoriaProducto.get(detallePedidoProveedor.getProducto().getSubcategoriaProducto().getId() + "~" + detallePedidoProveedor
/*  818: 842 */               .getProducto().getSubcategoriaProducto().getCodigo());
/*  819:     */           }
/*  820:     */           else
/*  821:     */           {
/*  822: 844 */             List<CriterioContabilizacion> listCCNulls = new ArrayList();
/*  823: 845 */             for (CriterioContabilizacion cd : hashCriterioContabilizacionNull.values()) {
/*  824: 846 */               listCCNulls.add(cd);
/*  825:     */             }
/*  826: 848 */             FuncionesUtiles.ordenaLista(listCCNulls, "orden");
/*  827: 849 */             if (listCCNulls.size() > 0) {
/*  828: 850 */               criterioContabilizacion = (CriterioContabilizacion)listCCNulls.get(0);
/*  829:     */             }
/*  830:     */           }
/*  831: 852 */           if ((criterioContabilizacion != null) && (detallePedidoProveedor.getDimensionContable5() != null))
/*  832:     */           {
/*  833: 853 */             DetallePresupuesto detallePresupuesto = (DetallePresupuesto)((Map)hashDetallesPresupuesto).get(criterioContabilizacion
/*  834: 854 */               .getCuentaContable().getId() + "~" + detallePedidoProveedor.getDimensionContable5().getId());
/*  835: 855 */             if (detallePresupuesto != null) {
/*  836: 856 */               reversarDetalle(detallePresupuesto, detallePedidoProveedor, mes);
/*  837:     */             }
/*  838:     */           }
/*  839:     */         }
/*  840:     */       }
/*  841: 861 */       this.servicioPresupuesto.guardar(presupuesto);
/*  842:     */     }
/*  843:     */     catch (ExcepcionAS2Financiero e)
/*  844:     */     {
/*  845: 864 */       e.printStackTrace();
/*  846:     */     }
/*  847:     */     catch (ExcepcionAS2Inventario e)
/*  848:     */     {
/*  849: 867 */       e.printStackTrace();
/*  850:     */     }
/*  851:     */   }
/*  852:     */   
/*  853:     */   private void reversarDetalle(DetallePresupuesto detallePresupuesto, DetallePedidoProveedor detallePedidoProveedor, int mes)
/*  854:     */   {
/*  855: 872 */     if (mes == 1) {
/*  856: 874 */       detallePresupuesto.setSaldoComprometidoEnero(detallePresupuesto.getSaldoComprometidoEnero().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  857: 875 */     } else if (mes == 2) {
/*  858: 877 */       detallePresupuesto.setSaldoComprometidoFebrero(detallePresupuesto.getSaldoComprometidoFebrero().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  859: 878 */     } else if (mes == 3) {
/*  860: 880 */       detallePresupuesto.setSaldoComprometidoMarzo(detallePresupuesto.getSaldoComprometidoMarzo().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  861: 881 */     } else if (mes == 4) {
/*  862: 883 */       detallePresupuesto.setSaldoComprometidoAbril(detallePresupuesto.getSaldoComprometidoAbril().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  863: 884 */     } else if (mes == 5) {
/*  864: 886 */       detallePresupuesto.setSaldoComprometidoMayo(detallePresupuesto.getSaldoComprometidoMayo().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  865: 887 */     } else if (mes == 6) {
/*  866: 889 */       detallePresupuesto.setSaldoComprometidoJunio(detallePresupuesto.getSaldoComprometidoJunio().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  867: 890 */     } else if (mes == 7) {
/*  868: 892 */       detallePresupuesto.setSaldoComprometidoJulio(detallePresupuesto.getSaldoComprometidoJulio().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  869: 893 */     } else if (mes == 8) {
/*  870: 895 */       detallePresupuesto.setSaldoComprometidoAgosto(detallePresupuesto.getSaldoComprometidoAgosto().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  871: 896 */     } else if (mes == 9) {
/*  872: 897 */       detallePresupuesto.setSaldoComprometidoSeptiembre(detallePresupuesto
/*  873: 898 */         .getSaldoComprometidoSeptiembre().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  874: 899 */     } else if (mes == 10) {
/*  875: 901 */       detallePresupuesto.setSaldoComprometidoOctubre(detallePresupuesto.getSaldoComprometidoOctubre().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  876: 902 */     } else if (mes == 11) {
/*  877: 903 */       detallePresupuesto.setSaldoComprometidoNoviembre(detallePresupuesto
/*  878: 904 */         .getSaldoComprometidoNoviembre().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  879: 905 */     } else if (mes == 12) {
/*  880: 906 */       detallePresupuesto.setSaldoComprometidoDiciembre(detallePresupuesto
/*  881: 907 */         .getSaldoComprometidoDiciembre().subtract(detallePedidoProveedor.getPrecioLinea()));
/*  882:     */     }
/*  883:     */   }
/*  884:     */   
/*  885:     */   public void esEditable(PedidoProveedor pedidoProveedor)
/*  886:     */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero
/*  887:     */   {
/*  888: 919 */     this.servicioPedidoProveedor.pedidoEnRecepcionProveedor(pedidoProveedor.getIdPedidoProveedor());
/*  889: 920 */     this.servicioPedidoProveedor.pedidoEnFacturaProveedor(pedidoProveedor.getIdPedidoProveedor());
/*  890: 921 */     this.servicioPeriodo.buscarPorFecha(pedidoProveedor.getFecha(), pedidoProveedor.getIdOrganizacion(), pedidoProveedor
/*  891: 922 */       .getDocumento().getDocumentoBase());
/*  892:     */   }
/*  893:     */   
/*  894:     */   public void abrirPedidoProveedor(int idPedidoProveedor)
/*  895:     */   {
/*  896:     */     try
/*  897:     */     {
/*  898: 933 */       PedidoProveedor pedidoProveedor = this.servicioPedidoProveedor.buscarPorId(Integer.valueOf(idPedidoProveedor));
/*  899: 934 */       this.pedidoProveedorDao.actualizarEstado(Integer.valueOf(idPedidoProveedor), Estado.APROBADO, pedidoProveedor.getDescripcionCambioEstado(), pedidoProveedor
/*  900: 935 */         .getUsuarioCambioEstado(), pedidoProveedor.getFechaCambioEstado(), false);
/*  901:     */     }
/*  902:     */     catch (ExcepcionAS2 e)
/*  903:     */     {
/*  904: 937 */       e.printStackTrace();
/*  905:     */     }
/*  906:     */   }
/*  907:     */   
/*  908:     */   public List<DetallePedidoProveedor> obtenerListaDetallePedidoPorRecibir(int id)
/*  909:     */   {
/*  910: 943 */     return obtenerListaDetallePedidoPorRecibir(id, false);
/*  911:     */   }
/*  912:     */   
/*  913:     */   public List<DetallePedidoProveedor> obtenerListaDetallePedidoPorRecibir(int id, boolean aprobacionParcial)
/*  914:     */   {
/*  915: 948 */     return this.pedidoProveedorDao.obtenerListaDetallePedidoPorRecibir(id, aprobacionParcial);
/*  916:     */   }
/*  917:     */   
/*  918:     */   public List<PedidoProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  919:     */   {
/*  920: 958 */     return this.pedidoProveedorDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  921:     */   }
/*  922:     */   
/*  923:     */   public void cierreAutomatico(int idPedidoProveedor)
/*  924:     */   {
/*  925: 968 */     if (esPedidoPorRecibir(idPedidoProveedor)) {
/*  926: 969 */       abrirPedidoProveedor(idPedidoProveedor);
/*  927:     */     } else {
/*  928: 971 */       actualizarEstado(Integer.valueOf(idPedidoProveedor), Estado.CERRADO);
/*  929:     */     }
/*  930:     */   }
/*  931:     */   
/*  932:     */   public boolean esPedidoPorRecibir(int idPedidoProveedor)
/*  933:     */   {
/*  934: 982 */     return this.pedidoProveedorDao.esPedidoPorRecibir(idPedidoProveedor);
/*  935:     */   }
/*  936:     */   
/*  937:     */   public List<DetallePedidoProveedor> obtenerListaDetallePedidoPorFacturar(int idPedidoProveedor)
/*  938:     */   {
/*  939: 992 */     return this.pedidoProveedorDao.obtenerListaDetallePedidoPorFacturar(idPedidoProveedor);
/*  940:     */   }
/*  941:     */   
/*  942:     */   public void cargarDetallePedidoProveedorExcel(PedidoProveedor pedidoProveedor, String fileName, InputStream imInputStream, int filaInicial)
/*  943:     */     throws ExcepcionAS2
/*  944:     */   {
/*  945:     */     try
/*  946:     */     {
/*  947:1009 */       HashMap<String, Producto> hmProducto = new HashMap();
/*  948:     */       
/*  949:1011 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(fileName, imInputStream, filaInicial, 0);
/*  950:     */       DetallePedidoProveedor detallePedidoProveedor;
/*  951:1013 */       for (HSSFCell[] fila : datos)
/*  952:     */       {
/*  953:1015 */         String codigoProducto = fila[0] != null ? fila[0].getStringCellValue().trim() : "";
/*  954:1016 */         BigDecimal cantidad = fila[1] != null ? new BigDecimal(fila[1].getNumericCellValue()) : BigDecimal.ZERO;
/*  955:1017 */         String notaDetalle = fila[2] != null ? fila[2].getStringCellValue().trim() : "";
/*  956:1018 */         BigDecimal precio = fila[3] != null ? FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[3].getNumericCellValue()), 6) : BigDecimal.ZERO;
/*  957:     */         
/*  958:1020 */         BigDecimal descuento = fila[4] != null ? new BigDecimal(fila[4].getNumericCellValue()) : BigDecimal.ZERO;
/*  959:     */         
/*  960:     */ 
/*  961:     */ 
/*  962:     */ 
/*  963:1025 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/*  964:1026 */         if (producto == null)
/*  965:     */         {
/*  966:1027 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, pedidoProveedor.getIdOrganizacion(), null);
/*  967:1028 */           hmProducto.put(codigoProducto, producto);
/*  968:     */         }
/*  969:1031 */         if (producto != null)
/*  970:     */         {
/*  971:1033 */           detallePedidoProveedor = new DetallePedidoProveedor();
/*  972:1034 */           detallePedidoProveedor.setIdSucursal(AppUtil.getSucursal().getId());
/*  973:1035 */           detallePedidoProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  974:1036 */           detallePedidoProveedor.setCantidad(cantidad);
/*  975:1037 */           detallePedidoProveedor.setDescripcion(notaDetalle);
/*  976:1038 */           detallePedidoProveedor.setPrecio(precio);
/*  977:1039 */           detallePedidoProveedor.setProducto(producto);
/*  978:1040 */           detallePedidoProveedor.setDescuento(descuento);
/*  979:1041 */           detallePedidoProveedor.setPedidoProveedor(pedidoProveedor);
/*  980:1042 */           detallePedidoProveedor.setUnidadCompra(producto.getUnidadCompra());
/*  981:     */           
/*  982:1044 */           pedidoProveedor.getListaDetallePedidoProveedor().add(detallePedidoProveedor);
/*  983:1046 */           if (producto.isIndicadorImpuestos())
/*  984:     */           {
/*  985:1048 */             List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto((Producto)hmProducto.get(codigoProducto), pedidoProveedor
/*  986:1049 */               .getFecha());
/*  987:1051 */             for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/*  988:     */             {
/*  989:1052 */               ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor = new ImpuestoProductoPedidoProveedor();
/*  990:1053 */               impuestoProductoPedidoProveedor.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/*  991:1054 */               impuestoProductoPedidoProveedor.setImpuesto(rangoImpuesto.getImpuesto());
/*  992:1055 */               impuestoProductoPedidoProveedor.setDetallePedidoProveedor(detallePedidoProveedor);
/*  993:1056 */               detallePedidoProveedor.getListaImpuestoProductoPedidoProveedor().add(impuestoProductoPedidoProveedor);
/*  994:     */             }
/*  995:     */           }
/*  996:     */         }
/*  997:     */       }
/*  998:1063 */       totalizar(pedidoProveedor);
/*  999:     */     }
/* 1000:     */     catch (IllegalArgumentException e)
/* 1001:     */     {
/* 1002:1066 */       this.context.setRollbackOnly();
/* 1003:1067 */       e.printStackTrace();
/* 1004:1068 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", e.getMessage());
/* 1005:     */     }
/* 1006:     */     catch (ExcepcionAS2 e)
/* 1007:     */     {
/* 1008:1070 */       this.context.setRollbackOnly();
/* 1009:1071 */       e.printStackTrace();
/* 1010:1072 */       throw e;
/* 1011:     */     }
/* 1012:     */     catch (Exception e)
/* 1013:     */     {
/* 1014:1074 */       this.context.setRollbackOnly();
/* 1015:1075 */       e.printStackTrace();
/* 1016:1076 */       throw new ExcepcionAS2(e);
/* 1017:     */     }
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public void obtenerImpuestosProductos(Producto producto, DetallePedidoProveedor dpp)
/* 1021:     */     throws ExcepcionAS2Inventario
/* 1022:     */   {
/* 1023:1089 */     producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/* 1024:     */     
/* 1025:1091 */     List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, dpp.getPedidoProveedor().getFecha());
/* 1026:1093 */     for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 1027:     */     {
/* 1028:1095 */       ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor = new ImpuestoProductoPedidoProveedor();
/* 1029:1096 */       impuestoProductoPedidoProveedor.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 1030:1097 */       impuestoProductoPedidoProveedor.setImpuesto(rangoImpuesto.getImpuesto());
/* 1031:1098 */       impuestoProductoPedidoProveedor.setDetallePedidoProveedor(dpp);
/* 1032:1099 */       dpp.getListaImpuestoProductoPedidoProveedor().add(impuestoProductoPedidoProveedor);
/* 1033:     */     }
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public List<PedidoProveedor> cargarPedidosPorAprobar(int idOrganizacion, Sucursal sucursal, Documento documento, Empresa proveedor, Date fechaDesde, Date fechaHasta, boolean cargarPedidoProcesados, Usuario usuarioSesion, boolean indicadorMostrarTodoAprobacion, CategoriaEmpresa categoriaEmpresa)
/* 1037:     */     throws AS2Exception
/* 1038:     */   {
/* 1039:1117 */     List<EntidadUsuario> listaSuperiores = this.servicioUsuario.buscarJerarquiaInmediata(usuarioSesion.getIdUsuario(), true, DocumentoBase.PEDIDO_PROVEEDOR);
/* 1040:     */     
/* 1041:1119 */     List<EntidadUsuario> listaSubordinados = this.servicioUsuario.buscarJerarquiaInmediata(usuarioSesion.getIdUsuario(), false, DocumentoBase.PEDIDO_PROVEEDOR);
/* 1042:     */     
/* 1043:1121 */     return this.pedidoProveedorDao.cargarPedidosPorAprobar(idOrganizacion, sucursal, documento, proveedor, fechaDesde, fechaHasta, cargarPedidoProcesados, usuarioSesion, listaSuperiores, listaSubordinados, indicadorMostrarTodoAprobacion, categoriaEmpresa);
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public void cambiarEstadoPedidos(List<PedidoProveedor> listaPedidoProveedor, Estado estado, String usuario, Date fecha, List<PedidoProveedor> listaPedidosNoActualizarUsuariosAprobacion)
/* 1047:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/* 1048:     */   {
/* 1049:1134 */     boolean actualizarUsuario = true;
/* 1050:1135 */     for (PedidoProveedor pedidoProveedor : listaPedidoProveedor) {
/* 1051:1136 */       if (pedidoProveedor.isTraSeleccionado())
/* 1052:     */       {
/* 1053:1138 */         if ((listaPedidosNoActualizarUsuariosAprobacion != null) && (listaPedidosNoActualizarUsuariosAprobacion.contains(pedidoProveedor))) {
/* 1054:1139 */           actualizarUsuario = false;
/* 1055:     */         }
/* 1056:1141 */         this.pedidoProveedorDao.actualizarEstado(Integer.valueOf(pedidoProveedor.getIdPedidoProveedor()), estado, pedidoProveedor.getDescripcionCambioEstado(), usuario, fecha, pedidoProveedor
/* 1057:1142 */           .getIndicadorSolicitudCambioPrecio().booleanValue(), actualizarUsuario);
/* 1058:1143 */         if (estado.equals(Estado.APROBADO)) {
/* 1059:     */           try
/* 1060:     */           {
/* 1061:1145 */             enviarEmail(pedidoProveedor);
/* 1062:     */           }
/* 1063:     */           catch (Exception e)
/* 1064:     */           {
/* 1065:1147 */             System.out.println("Error al enviar el mail");
/* 1066:     */           }
/* 1067:     */         }
/* 1068:1150 */         if (pedidoProveedor.getIndicadorSolicitudCambioPrecio().booleanValue()) {
/* 1069:1151 */           cambioPrecioPedidoProveedor(pedidoProveedor);
/* 1070:     */         }
/* 1071:     */       }
/* 1072:     */     }
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public void cambioPrecioPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 1076:     */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/* 1077:     */   {
/* 1078:     */     try
/* 1079:     */     {
/* 1080:1162 */       List<DetallePedidoProveedor> listaDetallePedidoProveedor = this.detallePedidoProveedorDao.obtenerDetallePedidoProveedor(pedidoProveedor.getIdPedidoProveedor());
/* 1081:1163 */       for (DetallePedidoProveedor detallePedidoProveedor : listaDetallePedidoProveedor)
/* 1082:     */       {
/* 1083:1164 */         detallePedidoProveedor.getPedidoProveedor().setDescripcion(detallePedidoProveedor.getPedidoProveedor().getDescripcion() + "Aprobo Cambio Precio:(" + 
/* 1084:1165 */           AppUtil.getUsuarioEnSesion().getNombreUsuario() + ")");
/* 1085:1166 */         detallePedidoProveedor.setPrecio(detallePedidoProveedor.getPrecioNuevo());
/* 1086:1167 */         totalizar(detallePedidoProveedor.getPedidoProveedor());
/* 1087:1168 */         guardar(detallePedidoProveedor.getPedidoProveedor());
/* 1088:     */         
/* 1089:1170 */         List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = this.detallePedidoProveedorDao.obtenerDetalleRecepcionProveedor(detallePedidoProveedor);
/* 1090:1171 */         if (listaDetalleRecepcionProveedor.size() > 0) {
/* 1091:1172 */           this.servicioRecepcionProveedor.guardar(((DetalleRecepcionProveedor)listaDetalleRecepcionProveedor.get(0)).getRecepcionProveedor(), false, true);
/* 1092:     */         }
/* 1093:     */       }
/* 1094:     */     }
/* 1095:     */     catch (ExcepcionAS2Financiero e)
/* 1096:     */     {
/* 1097:1176 */       this.context.setRollbackOnly();
/* 1098:1177 */       e.printStackTrace();
/* 1099:1178 */       throw e;
/* 1100:     */     }
/* 1101:     */     catch (ExcepcionAS2 e)
/* 1102:     */     {
/* 1103:1180 */       this.context.setRollbackOnly();
/* 1104:1181 */       e.printStackTrace();
/* 1105:1182 */       throw e;
/* 1106:     */     }
/* 1107:     */     catch (AS2Exception e)
/* 1108:     */     {
/* 1109:1184 */       this.context.setRollbackOnly();
/* 1110:1185 */       e.printStackTrace();
/* 1111:1186 */       throw e;
/* 1112:     */     }
/* 1113:     */     catch (Exception e)
/* 1114:     */     {
/* 1115:1188 */       this.context.setRollbackOnly();
/* 1116:1189 */       LOG.error(e);
/* 1117:1190 */       e.printStackTrace();
/* 1118:1191 */       throw new ExcepcionAS2(e);
/* 1119:     */     }
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public List<Object[]> getResumenAnualAprobadasYPorAprobar(int idOrganizanizacion, int anio, int mes, Documento documento)
/* 1123:     */   {
/* 1124:1203 */     return this.pedidoProveedorDao.getResumenAnualAprobadasYPorAprobar(idOrganizanizacion, anio, mes, documento);
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public List<FacturaProveedor> pedidoEnFacturaProveedor(int idPedidoProveedor)
/* 1128:     */     throws ExcepcionAS2Compras
/* 1129:     */   {
/* 1130:1208 */     return this.pedidoProveedorDao.pedidoEnFacturaProveedor(idPedidoProveedor);
/* 1131:     */   }
/* 1132:     */   
/* 1133:     */   public List<RecepcionProveedor> pedidoEnRecepcionProveedor(int idPedidoProveedor)
/* 1134:     */     throws ExcepcionAS2Compras
/* 1135:     */   {
/* 1136:1213 */     return this.pedidoProveedorDao.pedidoEnRecepcionProveedor(idPedidoProveedor);
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public PedidoProveedor copiarPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 1140:     */     throws ExcepcionAS2Financiero
/* 1141:     */   {
/* 1142:1218 */     pedidoProveedor.setIdPedidoProveedor(0);
/* 1143:1219 */     pedidoProveedor.setUsuarioAprobacionCambioPrecio("");
/* 1144:1220 */     pedidoProveedor.setUsuarioCambioEstado("");
/* 1145:1221 */     pedidoProveedor.setUsuarioCreacion(null);
/* 1146:1222 */     pedidoProveedor.setUsuarioModificacion(null);
/* 1147:1223 */     pedidoProveedor.setFechaCambioEstado(null);
/* 1148:1224 */     pedidoProveedor.setFechaCreacion(null);
/* 1149:1225 */     pedidoProveedor.setFechaModificacion(null);
/* 1150:1226 */     pedidoProveedor.setNumero("");
/* 1151:1227 */     pedidoProveedor.setEstado(Estado.ELABORADO);
/* 1152:1228 */     pedidoProveedor.setFecha(new Date());
/* 1153:1229 */     pedidoProveedor.setFechaEntrega(null);
/* 1154:1230 */     pedidoProveedor.setDireccionEmpresa(pedidoProveedor.getDireccionEmpresa());
/* 1155:1231 */     pedidoProveedor.setUsuariosAutorizacion("");
/* 1156:1233 */     for (DetallePedidoProveedor detallePedidoProveedor : pedidoProveedor.getListaDetallePedidoProveedor())
/* 1157:     */     {
/* 1158:1234 */       detallePedidoProveedor.setIdDetallePedidoProveedor(0);
/* 1159:1235 */       detallePedidoProveedor.setIndicadorAprobado(false);
/* 1160:1236 */       for (ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor : detallePedidoProveedor.getListaImpuestoProductoPedidoProveedor()) {
/* 1161:1237 */         impuestoProductoPedidoProveedor.setIdImpuestoProductoPedidoProveedor(0);
/* 1162:     */       }
/* 1163:     */     }
/* 1164:1241 */     return pedidoProveedor;
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public void enviarEmail(PedidoProveedor pedidoProveedor)
/* 1168:     */   {
/* 1169:1246 */     enviarEmail(pedidoProveedor, null);
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public void enviarEmail(PedidoProveedor pedidoProveedor, String mails)
/* 1173:     */   {
/* 1174:1251 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 1175:1252 */     List<Contacto> listaContactos = this.pedidoProveedorDao.getContactosPedidoProveedor(pedidoProveedor);
/* 1176:1254 */     if (listaContactos.size() > 0)
/* 1177:     */     {
/* 1178:1255 */       Contacto contacto = (Contacto)listaContactos.get(0);
/* 1179:1256 */       String bodyText = contacto.getTipoContacto().getTextoCuerpoCorreoPedidoProveedor();
/* 1180:1257 */       bodyText = bodyText.replaceAll(":numeroComprobante:", pedidoProveedor.getNumero());
/* 1181:1258 */       bodyText = bodyText.replaceAll(":fechaComprobante:", sdf.format(pedidoProveedor.getFecha()));
/* 1182:1259 */       bodyText = bodyText.replaceAll(":nombreProveedor:", pedidoProveedor.getEmpresa().getNombreFiscal());
/* 1183:1260 */       bodyText = bodyText.replaceAll(":identificacionProveedor:", pedidoProveedor.getEmpresa().getIdentificacion());
/* 1184:1261 */       if (contacto.getEmailRespuesta() != null) {
/* 1185:1262 */         bodyText = bodyText.replaceAll(":emailRespuesta:", contacto.getEmailRespuesta());
/* 1186:     */       }
/* 1187:1263 */       if (contacto.getTelefonoRespuesta() != null) {
/* 1188:1264 */         bodyText = bodyText.replaceAll(":telefonoRespuesta:", contacto.getTelefonoRespuesta());
/* 1189:     */       }
/* 1190:1266 */       Organizacion organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(pedidoProveedor.getIdOrganizacion()));
/* 1191:     */       
/* 1192:1268 */       String asunto = "Pedido Proveedor No. " + pedidoProveedor.getNumero() + " - " + organizacion.getRazonSocial() + " => " + pedidoProveedor.getEmpresa().getNombreFiscal();
/* 1193:1269 */       String emails = mails;
/* 1194:1270 */       if (emails == null)
/* 1195:     */       {
/* 1196:1271 */         emails = this.servicioEmpresa.cargarMails(pedidoProveedor.getEmpresa(), DocumentoBase.PEDIDO_PROVEEDOR);
/* 1197:1272 */         String[] correos = emails.split("~");
/* 1198:1273 */         if (correos.length > 0) {
/* 1199:1274 */           emails = correos[0];
/* 1200:     */         }
/* 1201:     */       }
/* 1202:1277 */       List<Object[]> listaDatosReporte = new ArrayList();
/* 1203:1278 */       listaDatosReporte = this.servicioReportePedidoProveedor.getReportePedidoProveedor(pedidoProveedor.getIdPedidoProveedor());
/* 1204:1279 */       JRDataSource ds = new QueryResultDataSource(listaDatosReporte, ReportePedidoProveedorBean.fields);
/* 1205:     */       
/* 1206:1281 */       this.servicioEnvioEmail.enviarEmailComprobanteNoElectronicos(organizacion, pedidoProveedor.getSucursal().getId(), emails, asunto, bodyText, pedidoProveedor
/* 1207:1282 */         .getDocumento().getDocumentoBase(), pedidoProveedor.getNumero(), ds, pedidoProveedor.getDocumento().getReporte(), pedidoProveedor
/* 1208:1283 */         .getUsuarioCreacion());
/* 1209:     */     }
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public DetallePedidoProveedor cargarDetallePedidoProveedor(DetallePedidoProveedor detallePedidoProveedor)
/* 1213:     */   {
/* 1214:1290 */     return this.detallePedidoProveedorDao.cargarDetalle(detallePedidoProveedor.getId());
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public List<DetallePedidoProveedor> obtenerListaPorPaginaDetallePedidoProveedor(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 1218:     */   {
/* 1219:1296 */     return this.detallePedidoProveedorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   public int contarPorCriterioDetallePedidoProveedor(Map<String, String> filters)
/* 1223:     */   {
/* 1224:1301 */     return this.detallePedidoProveedorDao.contarPorCriterio(filters);
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   public void cargaDatosProveedor(PedidoProveedor pedidoProveedor)
/* 1228:     */   {
/* 1229:1306 */     String mails = this.servicioEmpresa.cargarMails(pedidoProveedor.getEmpresa(), pedidoProveedor.getDocumento().getDocumentoBase());
/* 1230:1307 */     String[] emails = mails.split("~");
/* 1231:1308 */     if (emails.length > 0)
/* 1232:     */     {
/* 1233:1309 */       pedidoProveedor.setEmail(emails[0]);
/* 1234:1310 */       pedidoProveedor.setEmailRespuesta(emails[1].replace("^", ""));
/* 1235:1311 */       pedidoProveedor.setTelefonoRespuesta(emails[2].replace("^", ""));
/* 1236:     */     }
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public void actualizarAtributoEntidad(PedidoProveedor pedidoProveedor, HashMap<String, Object> campos)
/* 1240:     */   {
/* 1241:1317 */     this.pedidoProveedorDao.actualizarAtributoEntidad(pedidoProveedor, campos);
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public PedidoProveedor buscarPorNumero(Integer idOrganizacion, String numero, String nombreDocumento)
/* 1245:     */     throws AS2Exception
/* 1246:     */   {
/* 1247:1323 */     return this.pedidoProveedorDao.buscarPorNumero(idOrganizacion, numero, nombreDocumento);
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public void aprobarPorProducto(PedidoProveedor pedidoProveedor, Producto producto)
/* 1251:     */   {
/* 1252:1328 */     this.pedidoProveedorDao.aprobarPorProducto(pedidoProveedor, producto);
/* 1253:     */   }
/* 1254:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.impl.ServicioPedidoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */