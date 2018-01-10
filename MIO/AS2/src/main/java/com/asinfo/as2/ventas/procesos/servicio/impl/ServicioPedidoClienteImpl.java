/*    1:     */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    4:     */ import com.asinfo.as2.dao.DetallePedidoClienteDao;
/*    5:     */ import com.asinfo.as2.dao.FacturaClienteDao;
/*    6:     */ import com.asinfo.as2.dao.GenericoDao;
/*    7:     */ import com.asinfo.as2.dao.PedidoClienteDao;
/*    8:     */ import com.asinfo.as2.dao.reportes.ventas.ReportePedidosVSInventarioDao;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   12:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   13:     */ import com.asinfo.as2.entities.Cliente;
/*   14:     */ import com.asinfo.as2.entities.Contacto;
/*   15:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   16:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   17:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   18:     */ import com.asinfo.as2.entities.Documento;
/*   19:     */ import com.asinfo.as2.entities.Empresa;
/*   20:     */ import com.asinfo.as2.entities.ImpuestoProductoPedidoCliente;
/*   21:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   22:     */ import com.asinfo.as2.entities.Organizacion;
/*   23:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   24:     */ import com.asinfo.as2.entities.Producto;
/*   25:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   26:     */ import com.asinfo.as2.entities.RegistroPeso;
/*   27:     */ import com.asinfo.as2.entities.Ruta;
/*   28:     */ import com.asinfo.as2.entities.Subempresa;
/*   29:     */ import com.asinfo.as2.entities.Sucursal;
/*   30:     */ import com.asinfo.as2.entities.TipoContacto;
/*   31:     */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*   32:     */ import com.asinfo.as2.entities.Transportista;
/*   33:     */ import com.asinfo.as2.entities.Zona;
/*   34:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   35:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   36:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   37:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   38:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   39:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   40:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   41:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   42:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   43:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   44:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*   45:     */ import com.asinfo.as2.rs.ventas.dto.DetalleCalculoImpuestoResponseDto;
/*   46:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   47:     */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*   48:     */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*   49:     */ import com.asinfo.as2.util.AppUtil;
/*   50:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   51:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   52:     */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   53:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   54:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   55:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*   56:     */ import com.asinfo.as2.ventas.reportes.ReportePedidoClienteBean;
/*   57:     */ import java.io.IOException;
/*   58:     */ import java.io.InputStream;
/*   59:     */ import java.io.PrintStream;
/*   60:     */ import java.math.BigDecimal;
/*   61:     */ import java.math.RoundingMode;
/*   62:     */ import java.text.ParseException;
/*   63:     */ import java.text.SimpleDateFormat;
/*   64:     */ import java.util.ArrayList;
/*   65:     */ import java.util.Calendar;
/*   66:     */ import java.util.Date;
/*   67:     */ import java.util.HashMap;
/*   68:     */ import java.util.Iterator;
/*   69:     */ import java.util.List;
/*   70:     */ import java.util.Map;
/*   71:     */ import javax.ejb.EJB;
/*   72:     */ import javax.ejb.SessionContext;
/*   73:     */ import javax.ejb.Stateless;
/*   74:     */ import javax.ejb.TransactionAttribute;
/*   75:     */ import javax.ejb.TransactionAttributeType;
/*   76:     */ import javax.ejb.TransactionManagement;
/*   77:     */ import javax.ejb.TransactionManagementType;
/*   78:     */ import net.sf.jasperreports.engine.JRDataSource;
/*   79:     */ import org.apache.log4j.Logger;
/*   80:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   81:     */ 
/*   82:     */ @Stateless
/*   83:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*   84:     */ public class ServicioPedidoClienteImpl
/*   85:     */   extends AbstractServicioAS2
/*   86:     */   implements ServicioPedidoCliente
/*   87:     */ {
/*   88:     */   private static final long serialVersionUID = -188812375117191835L;
/*   89:     */   @EJB
/*   90:     */   private transient PedidoClienteDao pedidoClienteDao;
/*   91:     */   @EJB
/*   92:     */   private transient DetallePedidoClienteDao detallePedidoClienteDao;
/*   93:     */   @EJB
/*   94:     */   private transient GenericoDao<ImpuestoProductoPedidoCliente> impuestoProductoPedidoClienteDao;
/*   95:     */   @EJB
/*   96:     */   private transient ServicioSecuencia servicioSecuencia;
/*   97:     */   @EJB
/*   98:     */   private transient ServicioPeriodo servicioPeriodo;
/*   99:     */   @EJB
/*  100:     */   private transient ReportePedidosVSInventarioDao reportePedidosVSInventarioDao;
/*  101:     */   @EJB
/*  102:     */   private transient ServicioProducto servicioProducto;
/*  103:     */   @EJB
/*  104:     */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/*  105:     */   @EJB
/*  106:     */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/*  107:     */   @EJB
/*  108:     */   private ServicioEmpresa servicioEmpresa;
/*  109:     */   @EJB
/*  110:     */   private ServicioOrganizacion servicioOrganizacion;
/*  111:     */   @EJB
/*  112:     */   private ServicioEnvioEmail servicioEnvioEmail;
/*  113:     */   @EJB
/*  114:     */   private ServicioUsuario servicioUsuario;
/*  115:     */   @EJB
/*  116:     */   protected transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  117:     */   @EJB
/*  118:     */   protected transient FacturaClienteDao facturaClienteDao;
/*  119:     */   @EJB
/*  120:     */   private ServicioListaPrecios servicioListaPrecios;
/*  121:     */   
/*  122:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  123:     */   public void guardar(PedidoCliente pedidoCliente)
/*  124:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  125:     */   {
/*  126:     */     try
/*  127:     */     {
/*  128: 146 */       actualizarZonaFechaDespacho(pedidoCliente);
/*  129: 147 */       validar(pedidoCliente);
/*  130: 148 */       this.servicioVerificadorInventario.cantidadDetalle(pedidoCliente.getListaDetallePedidoCliente());
/*  131: 149 */       this.servicioPeriodo.buscarPorFecha(pedidoCliente.getFecha(), pedidoCliente.getIdOrganizacion(), pedidoCliente
/*  132: 150 */         .getDocumento().getDocumentoBase());
/*  133:     */       
/*  134: 152 */       cargarSecuencia(pedidoCliente);
/*  135: 154 */       for (DetallePedidoCliente dpc : pedidoCliente.getListaDetallePedidoCliente())
/*  136:     */       {
/*  137: 156 */         if ((dpc.getProducto().getTipoProducto() == TipoProducto.ARTICULO) || 
/*  138: 157 */           (ParametrosSistema.getDespacharServicio(pedidoCliente.getIdOrganizacion()).booleanValue())) {
/*  139: 158 */           dpc.setCantidadPorDespachar(dpc.getCantidad());
/*  140:     */         } else {
/*  141: 162 */           dpc.setCantidadPorDespachar(BigDecimal.ZERO);
/*  142:     */         }
/*  143: 165 */         dpc.setCantidadPorFacturar(dpc.getCantidad());
/*  144: 167 */         for (ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente : dpc.getListaImpuestoProductoPedidoCliente())
/*  145:     */         {
/*  146: 168 */           if (dpc.isEliminado()) {
/*  147: 169 */             impuestoProductoPedidoCliente.setEliminado(true);
/*  148:     */           }
/*  149: 171 */           this.impuestoProductoPedidoClienteDao.guardar(impuestoProductoPedidoCliente);
/*  150:     */         }
/*  151: 174 */         this.detallePedidoClienteDao.guardar(dpc);
/*  152:     */       }
/*  153: 177 */       pedidoCliente.setIndicadorPedidoPreautorizado(pedidoCliente.getEmpresa().getCliente().getIndicadorPedidoPreautorizado());
/*  154:     */       
/*  155: 179 */       this.pedidoClienteDao.guardar(pedidoCliente);
/*  156: 181 */       if (pedidoCliente.getEstado().equals(Estado.ELABORADO)) {
/*  157: 184 */         if (pedidoCliente.getEmpresa().getCliente().getIndicadorPedidoPreautorizado().booleanValue())
/*  158:     */         {
/*  159: 185 */           procesarPedidoCliente(pedidoCliente, Boolean.valueOf(false), true, pedidoCliente.getUsuarioCreacion(), new Date());
/*  160:     */         }
/*  161: 186 */         else if (pedidoCliente.isIndicadorAutorizacionPedidoPorCriterio())
/*  162:     */         {
/*  163: 189 */           String mensajes = this.servicioVerificadorVentas.verificarBloqueoClientePedido(pedidoCliente.getEmpresa().getCliente(), pedidoCliente
/*  164: 190 */             .getFecha());
/*  165:     */           
/*  166:     */ 
/*  167: 193 */           String mensajes2 = "";
/*  168:     */           try
/*  169:     */           {
/*  170: 195 */             this.servicioVerificadorVentas.verificarCupoCredito(pedidoCliente.getEmpresa().getCliente(), pedidoCliente.getTotalPedido());
/*  171:     */           }
/*  172:     */           catch (Exception e)
/*  173:     */           {
/*  174: 197 */             mensajes2 = "Verificar Cupo Credito " + e.getMessage() + " ";
/*  175:     */           }
/*  176: 199 */           mensajes = mensajes + mensajes2;
/*  177: 202 */           if ((mensajes != null) && (!mensajes.isEmpty()))
/*  178:     */           {
/*  179: 203 */             mensajes = mensajes + "-" + pedidoCliente.getDescripcion();
/*  180: 204 */             pedidoCliente.setDescripcion(mensajes);
/*  181:     */           }
/*  182:     */           else
/*  183:     */           {
/*  184: 206 */             procesarPedidoCliente(pedidoCliente, Boolean.valueOf(false), true, pedidoCliente.getUsuarioCreacion(), new Date());
/*  185:     */           }
/*  186:     */         }
/*  187:     */       }
/*  188: 211 */       if (pedidoCliente.getEstado().equals(Estado.PROCESADO)) {
/*  189:     */         try
/*  190:     */         {
/*  191: 213 */           enviarEmail(pedidoCliente);
/*  192:     */         }
/*  193:     */         catch (Exception e)
/*  194:     */         {
/*  195: 215 */           System.out.println("Ocurrio un error al enviar mail");
/*  196:     */         }
/*  197:     */       }
/*  198:     */     }
/*  199:     */     catch (ExcepcionAS2Inventario e)
/*  200:     */     {
/*  201: 222 */       this.context.setRollbackOnly();
/*  202: 223 */       throw e;
/*  203:     */     }
/*  204:     */     catch (ExcepcionAS2Financiero e)
/*  205:     */     {
/*  206: 225 */       this.context.setRollbackOnly();
/*  207: 226 */       throw e;
/*  208:     */     }
/*  209:     */     catch (ExcepcionAS2 e)
/*  210:     */     {
/*  211: 228 */       this.context.setRollbackOnly();
/*  212: 229 */       throw e;
/*  213:     */     }
/*  214:     */     catch (Exception e)
/*  215:     */     {
/*  216: 231 */       this.context.setRollbackOnly();
/*  217: 232 */       throw new ExcepcionAS2Ventas(e);
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  222:     */   public PedidoCliente guardarLogistica(PedidoCliente pedidoCliente, PedidoCliente pedidoClienteOrigen)
/*  223:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  224:     */   {
/*  225:     */     try
/*  226:     */     {
/*  227: 243 */       PedidoCliente pedidoAuxiliar = null;
/*  228: 244 */       for (DetallePedidoCliente detalle : pedidoCliente.getListaDetallePedidoCliente()) {
/*  229: 245 */         if ((detalle.isEliminado()) || (detalle.getCantidad().compareTo(detalle.getCantidadOriginal()) < 0)) {
/*  230: 246 */           pedidoAuxiliar = crearDetalleAuxiliar(detalle, pedidoAuxiliar, pedidoClienteOrigen);
/*  231: 247 */         } else if (detalle.getCantidad().compareTo(detalle.getCantidadOriginal()) > 0) {
/*  232: 249 */           throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioPedidoClienteImpl.MENSAJE_ERROR_CANTIDAD_EXCEDIDA_PEDIDO", new String[] { detalle.getProducto().getNombre(), detalle.getCantidad() + " > " + detalle.getCantidadOriginal() });
/*  233:     */         }
/*  234:     */       }
/*  235: 253 */       pedidoCliente.setEstado(Estado.REALIZADO_LOGISTICA);
/*  236: 254 */       guardar(pedidoCliente);
/*  237: 255 */       if (pedidoAuxiliar != null)
/*  238:     */       {
/*  239: 256 */         totalizar(pedidoAuxiliar);
/*  240: 257 */         guardar(pedidoAuxiliar);
/*  241:     */       }
/*  242: 259 */       return pedidoAuxiliar;
/*  243:     */     }
/*  244:     */     catch (ExcepcionAS2Inventario e)
/*  245:     */     {
/*  246: 261 */       this.context.setRollbackOnly();
/*  247: 262 */       throw e;
/*  248:     */     }
/*  249:     */     catch (ExcepcionAS2Financiero e)
/*  250:     */     {
/*  251: 264 */       this.context.setRollbackOnly();
/*  252: 265 */       throw e;
/*  253:     */     }
/*  254:     */     catch (ExcepcionAS2 e)
/*  255:     */     {
/*  256: 267 */       this.context.setRollbackOnly();
/*  257: 268 */       throw e;
/*  258:     */     }
/*  259:     */     catch (AS2Exception e)
/*  260:     */     {
/*  261: 270 */       this.context.setRollbackOnly();
/*  262: 271 */       throw e;
/*  263:     */     }
/*  264:     */     catch (Exception e)
/*  265:     */     {
/*  266: 273 */       this.context.setRollbackOnly();
/*  267: 274 */       throw new ExcepcionAS2Ventas(e);
/*  268:     */     }
/*  269:     */   }
/*  270:     */   
/*  271:     */   private PedidoCliente crearDetalleAuxiliar(DetallePedidoCliente detalle, PedidoCliente pedidoAuxiliar, PedidoCliente pedidoClienteOrigen)
/*  272:     */     throws ExcepcionAS2
/*  273:     */   {
/*  274: 280 */     if (pedidoAuxiliar == null)
/*  275:     */     {
/*  276: 282 */       String numeroOriginal = detalle.getPedidoCliente().getNumero();
/*  277:     */       
/*  278: 284 */       String regex = "^.*_\\d{2}$";
/*  279: 285 */       if ((numeroOriginal.length() > 4) && (numeroOriginal.matches(regex))) {
/*  280: 286 */         numeroOriginal = numeroOriginal.substring(0, numeroOriginal.length() - 3);
/*  281:     */       }
/*  282: 289 */       String sufijoNumeral = null;
/*  283: 290 */       String numeroMayor = this.pedidoClienteDao.obtenerMayorNumeroPedidoDividido(numeroOriginal + "_");
/*  284: 291 */       if (numeroMayor != null) {
/*  285: 292 */         sufijoNumeral = numeroMayor.substring(numeroMayor.length() - 3);
/*  286:     */       }
/*  287: 295 */       int incremental = 0;
/*  288: 296 */       if ((sufijoNumeral != null) && (sufijoNumeral.startsWith("_"))) {
/*  289:     */         try
/*  290:     */         {
/*  291: 298 */           incremental = Integer.valueOf(sufijoNumeral.substring(1)).intValue();
/*  292:     */         }
/*  293:     */         catch (Exception e)
/*  294:     */         {
/*  295: 300 */           incremental = 0;
/*  296:     */         }
/*  297:     */       }
/*  298: 303 */       incremental++;
/*  299:     */       
/*  300: 305 */       pedidoAuxiliar = pedidoClienteOrigen;
/*  301: 306 */       this.pedidoClienteDao.detach(pedidoAuxiliar);
/*  302: 307 */       pedidoAuxiliar.setIdPedidoCliente(0);
/*  303: 308 */       pedidoAuxiliar.setNumero(numeroOriginal + "_" + FuncionesUtiles.completarALaIzquierda('0', 2, new StringBuilder().append(incremental).append("").toString()));
/*  304: 309 */       pedidoAuxiliar.setUsuarioCreacion(null);
/*  305: 310 */       pedidoAuxiliar.setFechaCreacion(null);
/*  306: 311 */       pedidoAuxiliar.setEstado(Estado.PROCESADO);
/*  307: 312 */       pedidoAuxiliar.setListaDetallePedidoCliente(new ArrayList());
/*  308: 313 */       pedidoAuxiliar.setPedidoClientePadre(detalle.getPedidoCliente());
/*  309:     */     }
/*  310: 316 */     DetallePedidoCliente detalleAuxiliar = new DetallePedidoCliente();
/*  311: 317 */     detalleAuxiliar.setIdOrganizacion(pedidoAuxiliar.getIdOrganizacion());
/*  312: 318 */     detalleAuxiliar.setIdSucursal(pedidoAuxiliar.getSucursal().getId());
/*  313: 319 */     detalleAuxiliar.setPedidoCliente(pedidoAuxiliar);
/*  314: 320 */     detalleAuxiliar.setCantidad(detalle.getCantidadOriginal().subtract(detalle.getCantidad()));
/*  315: 321 */     detalleAuxiliar.setPorcentajeDescuento(detalle.getPorcentajeDescuento());
/*  316: 322 */     detalleAuxiliar.setPrecio(detalle.getPrecio());
/*  317: 323 */     detalleAuxiliar.setProducto(this.servicioProducto.cargaDetalle(detalle.getProducto().getId()));
/*  318: 324 */     detalleAuxiliar.setUnidadVenta(detalle.getUnidadVenta());
/*  319: 326 */     if (pedidoAuxiliar.getEmpresa().getCliente().isExcentoImpuestos()) {
/*  320: 327 */       detalleAuxiliar.setIndicadorImpuesto(false);
/*  321:     */     } else {
/*  322: 329 */       detalleAuxiliar.setIndicadorImpuesto(detalleAuxiliar.getProducto().isIndicadorImpuestos());
/*  323:     */     }
/*  324: 332 */     if (detalleAuxiliar.isIndicadorImpuesto()) {
/*  325: 333 */       obtenerImpuestosProductos(detalleAuxiliar.getProducto(), detalleAuxiliar);
/*  326:     */     }
/*  327: 336 */     pedidoAuxiliar.getListaDetallePedidoCliente().add(detalleAuxiliar);
/*  328: 337 */     return pedidoAuxiliar;
/*  329:     */   }
/*  330:     */   
/*  331:     */   private void actualizarZonaFechaDespacho(PedidoCliente pedidoCliente)
/*  332:     */   {
/*  333: 341 */     if (pedidoCliente.getFechaDespacho() == null)
/*  334:     */     {
/*  335: 342 */       Date fechaDespacho = pedidoCliente.getFecha();
/*  336: 343 */       int diasDespacho = 0;
/*  337: 344 */       if ((pedidoCliente.getEmpresaFinal() != null) && (pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho() != null) && 
/*  338: 345 */         (pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho().getDiasDespacho() > 0)) {
/*  339: 346 */         diasDespacho = pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho().getDiasDespacho();
/*  340:     */       }
/*  341: 349 */       if (pedidoCliente.getZona() == null)
/*  342:     */       {
/*  343: 350 */         Zona zona = pedidoCliente.getEmpresaFinal().getCliente().getZona();
/*  344: 351 */         pedidoCliente.setZona(zona);
/*  345:     */       }
/*  346: 354 */       fechaDespacho = FuncionesUtiles.sumarFechaDiasMeses(pedidoCliente.getFecha(), diasDespacho);
/*  347: 355 */       pedidoCliente.setFechaDespacho(fechaDespacho);
/*  348:     */     }
/*  349:     */   }
/*  350:     */   
/*  351:     */   public void validar(PedidoCliente pedidoCliente)
/*  352:     */     throws ExcepcionAS2Ventas
/*  353:     */   {
/*  354: 367 */     Empresa empresaFinal = null;
/*  355: 368 */     if (pedidoCliente.getSubempresa() != null) {
/*  356: 369 */       empresaFinal = pedidoCliente.getSubempresa().getEmpresa();
/*  357:     */     } else {
/*  358: 371 */       empresaFinal = pedidoCliente.getEmpresa();
/*  359:     */     }
/*  360: 373 */     if ((empresaFinal.getCliente().getTipoOrdenDespacho() != null) && 
/*  361: 374 */       (empresaFinal.getCliente().getTipoOrdenDespacho().getHoraMaximaRegistroPedido() != null))
/*  362:     */     {
/*  363: 375 */       Calendar ahora = Calendar.getInstance();
/*  364: 376 */       ahora.setTime(new Date());
/*  365: 377 */       Calendar horaMaxima = Calendar.getInstance();
/*  366: 378 */       horaMaxima.setTime(empresaFinal.getCliente().getTipoOrdenDespacho().getHoraMaximaRegistroPedido());
/*  367: 379 */       horaMaxima.set(5, ahora.get(5));
/*  368: 380 */       horaMaxima.set(2, ahora.get(2));
/*  369: 381 */       horaMaxima.set(1, ahora.get(1));
/*  370: 382 */       if (ahora.after(horaMaxima))
/*  371:     */       {
/*  372: 383 */         String mensaje = "(" + ahora.get(11) + ":" + ahora.get(12) + ":" + ahora.get(13) + ") > ";
/*  373: 384 */         mensaje = mensaje + "(" + horaMaxima.get(11) + ":" + horaMaxima.get(12) + ":" + horaMaxima.get(13) + ") ";
/*  374:     */         
/*  375: 386 */         throw new ExcepcionAS2Ventas("msg_error_pedido_fuera_horario", mensaje);
/*  376:     */       }
/*  377:     */     }
/*  378:     */     String mensaje;
/*  379: 390 */     if ((pedidoCliente.getEmpresaFinal() != null) && (pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho() != null) && 
/*  380: 391 */       (pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho().getMontoMinimoPedido().compareTo(BigDecimal.ZERO) > 0)) {
/*  381: 393 */       if (pedidoCliente.getTotal().subtract(pedidoCliente.getDescuento()).compareTo(pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho().getMontoMinimoPedido()) < 0)
/*  382:     */       {
/*  383: 395 */         mensaje = " ( " + pedidoCliente.getTotal().subtract(pedidoCliente.getDescuento()) + " < " + pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho().getMontoMinimoPedido() + " ).";
/*  384: 396 */         throw new ExcepcionAS2Ventas("msg_error_cantidad_minima_ventas", mensaje);
/*  385:     */       }
/*  386:     */     }
/*  387: 400 */     for (DetallePedidoCliente detalle : pedidoCliente.getListaDetallePedidoCliente()) {
/*  388: 401 */       if (!detalle.isEliminado())
/*  389:     */       {
/*  390: 402 */         Producto producto = this.servicioProducto.buscarPorId(detalle.getProducto().getId());
/*  391: 408 */         if (producto.getMultiploPedido().intValue() > 0)
/*  392:     */         {
/*  393: 409 */           double restoDivision = detalle.getCantidad().doubleValue() % producto.getMultiploPedido().doubleValue();
/*  394: 410 */           if (restoDivision != 0.0D)
/*  395:     */           {
/*  396: 411 */             String mensaje = " ( " + producto.getNombre() + " - " + producto.getMultiploPedido() + " ).";
/*  397: 412 */             throw new ExcepcionAS2Ventas("msg_error_cantidad_no_cumple_multiplo", mensaje);
/*  398:     */           }
/*  399:     */         }
/*  400:     */       }
/*  401:     */     }
/*  402:     */   }
/*  403:     */   
/*  404:     */   private void cargarSecuencia(PedidoCliente pedidoCliente)
/*  405:     */     throws ExcepcionAS2
/*  406:     */   {
/*  407: 426 */     if (pedidoCliente.getNumero().isEmpty())
/*  408:     */     {
/*  409: 427 */       String numero = "";
/*  410: 428 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(pedidoCliente.getDocumento().getId(), pedidoCliente.getFecha());
/*  411:     */       
/*  412: 430 */       pedidoCliente.setNumero(numero);
/*  413:     */     }
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void eliminar(PedidoCliente entidad)
/*  417:     */     throws ExcepcionAS2
/*  418:     */   {
/*  419: 441 */     this.pedidoClienteDao.eliminar(entidad);
/*  420:     */   }
/*  421:     */   
/*  422:     */   public PedidoCliente buscarPorId(Integer id)
/*  423:     */     throws ExcepcionAS2
/*  424:     */   {
/*  425: 451 */     return this.pedidoClienteDao.buscarPorId(id);
/*  426:     */   }
/*  427:     */   
/*  428:     */   public List<PedidoCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  429:     */   {
/*  430: 461 */     return this.pedidoClienteDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  431:     */   }
/*  432:     */   
/*  433:     */   public PedidoCliente copiarPedidoCliente(PedidoCliente pedidoCliente)
/*  434:     */     throws ExcepcionAS2Financiero
/*  435:     */   {
/*  436: 466 */     pedidoCliente.setEstado(Estado.ELABORADO);
/*  437: 467 */     pedidoCliente.setIdPedidoCliente(0);
/*  438: 468 */     pedidoCliente.setNumero("");
/*  439: 469 */     pedidoCliente.setFecha(new Date());
/*  440:     */     
/*  441:     */ 
/*  442: 472 */     TipoOrdenDespacho tipoOrdenDespacho = pedidoCliente.getEmpresa().getCliente().getTipoOrdenDespacho();
/*  443: 473 */     Date fechaDespacho = new Date();
/*  444: 474 */     if ((tipoOrdenDespacho != null) && (tipoOrdenDespacho.getDiasDespacho() > 0)) {
/*  445: 475 */       fechaDespacho = FuncionesUtiles.sumarFechaDiasMeses(new Date(), tipoOrdenDespacho.getDiasDespacho());
/*  446:     */     }
/*  447: 478 */     pedidoCliente.setFechaDespacho(fechaDespacho);
/*  448: 479 */     pedidoCliente.setUsuarioCreacion(null);
/*  449: 480 */     pedidoCliente.setFechaCreacion(null);
/*  450: 481 */     pedidoCliente.setIndicadorTieneDespacho(Boolean.valueOf(false));
/*  451: 482 */     for (DetallePedidoCliente detallePedidoCliente : pedidoCliente.getListaDetallePedidoCliente())
/*  452:     */     {
/*  453: 483 */       detallePedidoCliente.setIdDetallePedidoCliente(0);
/*  454: 484 */       for (ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente : detallePedidoCliente.getListaImpuestoProductoPedidoCliente()) {
/*  455: 485 */         impuestoProductoPedidoCliente.setIdImpuestoPrductoPedidoCliente(0);
/*  456:     */       }
/*  457:     */     }
/*  458: 489 */     return pedidoCliente;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public PedidoCliente cargarDetalle(int idPedidoCliente)
/*  462:     */   {
/*  463: 501 */     return this.pedidoClienteDao.cargarDetalle(idPedidoCliente);
/*  464:     */   }
/*  465:     */   
/*  466:     */   public PedidoCliente cargarDetalle(int idPedidoCliente, boolean cargarDetalle)
/*  467:     */   {
/*  468: 506 */     return this.pedidoClienteDao.cargarDetalle(idPedidoCliente, cargarDetalle);
/*  469:     */   }
/*  470:     */   
/*  471:     */   public void totalizar(PedidoCliente pedidoCliente)
/*  472:     */     throws ExcepcionAS2Ventas
/*  473:     */   {
/*  474: 517 */     BigDecimal total = BigDecimal.ZERO;
/*  475: 518 */     BigDecimal descuento = BigDecimal.ZERO;
/*  476: 519 */     BigDecimal montoICE = BigDecimal.ZERO;
/*  477: 521 */     for (DetallePedidoCliente dpc : pedidoCliente.getListaDetallePedidoCliente()) {
/*  478: 523 */       if (!dpc.isEliminado())
/*  479:     */       {
/*  480: 525 */         dpc.setDescuento(dpc.getPrecio().multiply(dpc.getPorcentajeDescuento()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/*  481:     */         
/*  482: 527 */         total = total.add(dpc.getPrecioLinea());
/*  483: 528 */         descuento = descuento.add(dpc.getDescuentoLinea());
/*  484: 531 */         if (dpc.isIndicadorPorcentajeIce()) {
/*  485: 532 */           dpc.setIceLinea(FuncionesUtiles.porcentaje(dpc.getPrecioLinea().subtract(dpc.getDescuentoLinea()), dpc.getIce()));
/*  486:     */         } else {
/*  487: 534 */           dpc.setIceLinea(FuncionesUtiles.redondearBigDecimal(dpc.getCantidad().multiply(dpc.getIce()), 2));
/*  488:     */         }
/*  489: 537 */         montoICE = montoICE.add(dpc.getIceLinea());
/*  490:     */       }
/*  491:     */     }
/*  492: 540 */     pedidoCliente.setMontoIce(FuncionesUtiles.redondearBigDecimal(montoICE));
/*  493: 541 */     totalizarImpuesto(pedidoCliente);
/*  494: 542 */     pedidoCliente.setTotal(FuncionesUtiles.redondearBigDecimal(total));
/*  495: 543 */     pedidoCliente.setDescuento(FuncionesUtiles.redondearBigDecimal(descuento));
/*  496:     */   }
/*  497:     */   
/*  498:     */   public void totalizarImpuesto(PedidoCliente pedidoCliente)
/*  499:     */     throws ExcepcionAS2Ventas
/*  500:     */   {
/*  501: 555 */     BigDecimal totalBaseImponible = BigDecimal.ZERO;
/*  502: 556 */     BigDecimal totalImpuestoProducto = BigDecimal.ZERO;
/*  503:     */     
/*  504: 558 */     BigDecimal totalDescuentoImpuestoProductoTotal = BigDecimal.ZERO;
/*  505: 559 */     BigDecimal porcentajeDescuento = pedidoCliente.getSucursal().getCompensacionSolidaria();
/*  506: 562 */     for (DetallePedidoCliente dpc : pedidoCliente.getListaDetallePedidoCliente()) {
/*  507: 564 */       if (!dpc.isEliminado())
/*  508:     */       {
/*  509: 565 */         totalBaseImponible = totalBaseImponible.add(dpc.getBaseImponible());
/*  510: 566 */         totalImpuestoProducto = totalImpuestoProducto.add(dpc.getValorImpuestosLinea());
/*  511: 570 */         if (FuncionesUtiles.redondearBigDecimal(dpc.getValorImpuestosLinea(), 8).compareTo(BigDecimal.ZERO) > 0)
/*  512:     */         {
/*  513: 571 */           BigDecimal valorDescuentoImpuestoLinea = FuncionesUtiles.porcentaje(dpc.getBaseImponible(), porcentajeDescuento, 10);
/*  514: 572 */           totalDescuentoImpuestoProductoTotal = totalDescuentoImpuestoProductoTotal.add(valorDescuentoImpuestoLinea);
/*  515:     */         }
/*  516:     */       }
/*  517:     */     }
/*  518: 578 */     pedidoCliente.setBaseImponible(FuncionesUtiles.redondearBigDecimal(totalBaseImponible));
/*  519: 579 */     pedidoCliente.setImpuesto(FuncionesUtiles.redondearBigDecimal(totalImpuestoProducto));
/*  520: 580 */     pedidoCliente.setDescuentoImpuesto(FuncionesUtiles.redondearBigDecimal(totalDescuentoImpuestoProductoTotal));
/*  521:     */   }
/*  522:     */   
/*  523:     */   public List<DetallePedidoCliente> obtenerListaDetallePedidoPorDespachar(int idPedidoCliente)
/*  524:     */   {
/*  525: 589 */     return this.pedidoClienteDao.obtenerListaDetallePedidoPorDespachar(idPedidoCliente);
/*  526:     */   }
/*  527:     */   
/*  528:     */   public List<PedidoCliente> listaPedidosPorDespachar(int idEmpresa)
/*  529:     */   {
/*  530: 599 */     return this.pedidoClienteDao.listaPedidosPorDespachar(idEmpresa);
/*  531:     */   }
/*  532:     */   
/*  533:     */   public List<PedidoCliente> listaPedidosPorDespachar(Organizacion organizacion)
/*  534:     */   {
/*  535: 604 */     return this.pedidoClienteDao.listaPedidosPorDespachar(organizacion);
/*  536:     */   }
/*  537:     */   
/*  538:     */   public List<PedidoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  539:     */     throws ExcepcionAS2
/*  540:     */   {
/*  541: 615 */     return this.pedidoClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  542:     */   }
/*  543:     */   
/*  544:     */   public List<PedidoCliente> listaPedidosPorFacturar(Estado estado, int idEmpresa)
/*  545:     */     throws ExcepcionAS2
/*  546:     */   {
/*  547: 625 */     return this.pedidoClienteDao.listaPedidosPorFacturar(estado, idEmpresa);
/*  548:     */   }
/*  549:     */   
/*  550:     */   public List getReportePedidoCliente(int idPedidoCliente)
/*  551:     */     throws ExcepcionAS2
/*  552:     */   {
/*  553: 636 */     return this.pedidoClienteDao.getReportePedidoCliente(idPedidoCliente);
/*  554:     */   }
/*  555:     */   
/*  556:     */   public List<DetallePedidoCliente> getPedidosVSInventario(Date fecha)
/*  557:     */   {
/*  558: 647 */     return this.reportePedidosVSInventarioDao.getPedidosVSInventario(fecha);
/*  559:     */   }
/*  560:     */   
/*  561:     */   public int contarPorCriterio(Map<String, String> filters)
/*  562:     */   {
/*  563: 657 */     return this.pedidoClienteDao.contarPorCriterio(filters);
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void actualizarEstado(PedidoCliente pedidoCliente, Estado estado, String descripcion)
/*  567:     */   {
/*  568: 667 */     this.pedidoClienteDao.actualizarEstado(pedidoCliente, estado, descripcion, null, null, null);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public void esEditable(PedidoCliente pedidoCliente)
/*  572:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/*  573:     */   {
/*  574: 679 */     if ((pedidoCliente.getEstado() != Estado.ELABORADO) && (
/*  575: 680 */       (pedidoCliente.getEstado() != Estado.PROCESADO) || (!pedidoCliente.getIndicadorPedidoPreautorizado().booleanValue()))) {
/*  576: 682 */       throw new ExcepcionAS2Ventas("msg_error_editar");
/*  577:     */     }
/*  578: 686 */     this.servicioPeriodo.buscarPorFecha(pedidoCliente.getFecha(), pedidoCliente.getIdOrganizacion(), pedidoCliente.getDocumento().getDocumentoBase());
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void anular(PedidoCliente pedidoCliente)
/*  582:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/*  583:     */   {
/*  584: 697 */     pedidoCliente = this.pedidoClienteDao.buscarPorId(Integer.valueOf(pedidoCliente.getId()));
/*  585: 698 */     esEditable(pedidoCliente);
/*  586: 699 */     pedidoCliente.setEstado(Estado.ANULADO);
/*  587:     */     
/*  588: 701 */     this.pedidoClienteDao.guardar(pedidoCliente);
/*  589: 702 */     if (pedidoCliente.getIndicadorPedidoPreautorizado().booleanValue()) {
/*  590: 703 */       liberarGenerarSaldoComprometido(pedidoCliente, true);
/*  591:     */     }
/*  592:     */   }
/*  593:     */   
/*  594:     */   private void liberarGenerarSaldoComprometido(PedidoCliente pedidoCliente, boolean liberar) {}
/*  595:     */   
/*  596:     */   public void actualizarCantidadPorDespachar(List<Integer> listaIdDetalleDespachoCliente)
/*  597:     */   {
/*  598: 733 */     this.pedidoClienteDao.actualizarCantidadPorDespachar(listaIdDetalleDespachoCliente);
/*  599:     */   }
/*  600:     */   
/*  601:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  602:     */   public void cerrarPedidoCliente(PedidoCliente pedidoCliente)
/*  603:     */   {
/*  604: 745 */     this.pedidoClienteDao.actualizarEstado(pedidoCliente, Estado.CERRADO, pedidoCliente.getDescripcion(), null, null, null);
/*  605: 746 */     liberarGenerarSaldoComprometido(pedidoCliente, true);
/*  606:     */   }
/*  607:     */   
/*  608:     */   public void abrirPedidoCliente(PedidoCliente pedidoCliente)
/*  609:     */     throws ExcepcionAS2Inventario
/*  610:     */   {
/*  611: 757 */     this.pedidoClienteDao.actualizarEstado(pedidoCliente, Estado.PROCESADO, null, null, null, null);
/*  612:     */   }
/*  613:     */   
/*  614:     */   public boolean esPedidoPorDespachar(int idPedidoCliente)
/*  615:     */   {
/*  616: 768 */     return this.pedidoClienteDao.esPedidoPorDespachar(idPedidoCliente);
/*  617:     */   }
/*  618:     */   
/*  619:     */   public void cierreAutomatico(PedidoCliente pedidoCliente)
/*  620:     */     throws ExcepcionAS2Inventario
/*  621:     */   {
/*  622: 778 */     if (esPedidoPorDespachar(pedidoCliente.getIdPedidoCliente())) {
/*  623: 779 */       abrirPedidoCliente(pedidoCliente);
/*  624:     */     } else {
/*  625: 781 */       cerrarPedidoCliente(pedidoCliente);
/*  626:     */     }
/*  627:     */   }
/*  628:     */   
/*  629:     */   public List<DetallePedidoCliente> obtenerListaDetallePedidoPorFacturar(int idPedidoCliente)
/*  630:     */   {
/*  631: 792 */     return this.pedidoClienteDao.obtenerListaDetallePedidoPorFacturar(idPedidoCliente);
/*  632:     */   }
/*  633:     */   
/*  634:     */   public Object[] cargarPedidosClienteCSVSantaMaria(PedidoCliente pedidoClienteBase, InputStream imInputStream)
/*  635:     */     throws AS2Exception
/*  636:     */   {
/*  637: 797 */     List<AS2Exception> listaErrores = new ArrayList();
/*  638: 798 */     List<PedidoCliente> lista = new ArrayList();
/*  639:     */     try
/*  640:     */     {
/*  641: 801 */       List<String> lineas = FuncionesUtiles.leerArchivoTexto(imInputStream);
/*  642: 802 */       PedidoCliente pedidoCliente = null;
/*  643: 803 */       for (int i = 0; i < lineas.size(); i++) {
/*  644: 805 */         if ((((String)lineas.get(i)).trim().equals(",")) && (((String)lineas.get(i + 1)).trim().equals(",")))
/*  645:     */         {
/*  646: 806 */           if (pedidoCliente != null)
/*  647:     */           {
/*  648: 808 */             totalizar(pedidoCliente);
/*  649: 809 */             lista.add(pedidoCliente);
/*  650:     */           }
/*  651: 812 */           pedidoCliente = null;
/*  652: 813 */           pedidoCliente = crearPedidoCliente(pedidoClienteBase);
/*  653: 814 */           pedidoCliente.setIndicadorFijo(true);
/*  654: 815 */           String lineaSubCliente = (String)lineas.get(i + 2);
/*  655: 816 */           String textoSubCliente = lineaSubCliente.split(":")[1].trim();
/*  656: 817 */           String codigoSubCliente = textoSubCliente.split(" ")[0].trim();
/*  657: 818 */           List<Subempresa> listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(pedidoCliente.getEmpresa().getId());
/*  658: 819 */           Subempresa subCliente = null;
/*  659: 820 */           for (Iterator localIterator1 = listaSubempresa.iterator(); localIterator1.hasNext();)
/*  660:     */           {
/*  661: 820 */             subempresa = (Subempresa)localIterator1.next();
/*  662: 821 */             if ((subempresa.getCodigo().equals(codigoSubCliente)) || (subempresa.getEmpresa().getCodigo().equals(codigoSubCliente)))
/*  663:     */             {
/*  664: 822 */               subCliente = subempresa;
/*  665: 823 */               break;
/*  666:     */             }
/*  667:     */           }
/*  668:     */           Subempresa subempresa;
/*  669: 826 */           if (subCliente != null)
/*  670:     */           {
/*  671: 827 */             pedidoCliente.setSubempresa(subCliente);
/*  672: 828 */             pedidoCliente.setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(subCliente.getEmpresa().getId()));
/*  673: 829 */             pedidoCliente.setZona(subCliente.getEmpresa().getCliente().getZona());
/*  674: 830 */             pedidoCliente.setCondicionPago(subCliente.getEmpresa().getCliente().getCondicionPago());
/*  675: 831 */             pedidoCliente.setNumeroCuotas(subCliente.getEmpresa().getCliente().getNumeroCuotas());
/*  676: 832 */             pedidoCliente.setTransportista(subCliente.getEmpresa().getCliente().getTransportista());
/*  677:     */             
/*  678: 834 */             Object listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(subCliente.getEmpresa().getId());
/*  679: 835 */             for (DireccionEmpresa de : (List)listaDireccionEmpresa) {
/*  680: 836 */               if (de.isIndicadorDireccionPrincipal())
/*  681:     */               {
/*  682: 837 */                 pedidoCliente.setDireccionEmpresa(de);
/*  683: 838 */                 break;
/*  684:     */               }
/*  685:     */             }
/*  686:     */           }
/*  687:     */           else
/*  688:     */           {
/*  689: 842 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { i + 2 + "", "2", codigoSubCliente, "no existe codigo subcliente" });
/*  690:     */             
/*  691: 844 */             listaErrores.add(error);
/*  692:     */           }
/*  693: 847 */           String lineaNroOrden = (String)lineas.get(i + 4);
/*  694: 848 */           String nroOrden = lineaNroOrden.split(":")[1].trim();
/*  695: 849 */           pedidoCliente.setDescripcion("Numero Pedido Santa Maria " + nroOrden);
/*  696:     */           
/*  697: 851 */           Map<String, String> filtros = new HashMap();
/*  698: 852 */           filtros.put("idOrganizacion", pedidoCliente.getIdOrganizacion() + "");
/*  699: 853 */           filtros.put("referencia1", "=" + nroOrden);
/*  700: 854 */           filtros.put("estado", "!=" + Estado.ANULADO);
/*  701: 855 */           List<PedidoCliente> listaPedidoExiste = obtenerListaCombo("referencia1", true, filtros);
/*  702: 856 */           if (listaPedidoExiste.size() > 0) {
/*  703: 857 */             throw new AS2Exception("msg_error_migracion_excel", new String[] { i + 4 + "", "2", nroOrden, "ya existe codigo pedido" });
/*  704:     */           }
/*  705: 859 */           pedidoCliente.setReferencia1(nroOrden);
/*  706:     */           
/*  707:     */ 
/*  708: 862 */           pedidoCliente.setFechaDespacho(null);
/*  709:     */           
/*  710: 864 */           actualizarZonaFechaDespacho(pedidoCliente);
/*  711:     */           
/*  712:     */ 
/*  713:     */ 
/*  714:     */ 
/*  715:     */ 
/*  716:     */ 
/*  717:     */ 
/*  718:     */ 
/*  719: 873 */           i += 10;
/*  720:     */         }
/*  721: 877 */         else if ((((String)lineas.get(i)).trim().equals("OBSERVACIONES:")) && (((String)lineas.get(i + 1)).trim().equals(".")))
/*  722:     */         {
/*  723: 878 */           i++;
/*  724:     */         }
/*  725:     */         else
/*  726:     */         {
/*  727: 880 */           String[] campos = ((String)lineas.get(i)).split(",");
/*  728: 881 */           String codigoProducto = campos[1].trim();
/*  729: 882 */           BigDecimal cantidad = new BigDecimal(campos[6]).setScale(2, RoundingMode.HALF_UP);
/*  730: 883 */           BigDecimal precio = new BigDecimal(campos[7]).setScale(6, RoundingMode.HALF_UP);
/*  731:     */           
/*  732: 885 */           BigDecimal unidadMedida = new BigDecimal(campos[5]).setScale(2, RoundingMode.HALF_UP);
/*  733: 886 */           BigDecimal cantidadDespacho = new BigDecimal(campos[4]).setScale(2, RoundingMode.HALF_UP);
/*  734:     */           
/*  735:     */ 
/*  736:     */ 
/*  737:     */ 
/*  738: 891 */           Producto producto = null;
/*  739:     */           try
/*  740:     */           {
/*  741: 893 */             producto = this.servicioProducto.buscarPorCualquierCodigo(codigoProducto, pedidoCliente.getIdOrganizacion());
/*  742:     */           }
/*  743:     */           catch (ExcepcionAS2 e)
/*  744:     */           {
/*  745: 895 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { i + 1 + "", "2", codigoProducto, "msg_info_no_existe_producto" });
/*  746:     */             
/*  747: 897 */             listaErrores.add(error);
/*  748:     */           }
/*  749: 899 */           if (producto != null)
/*  750:     */           {
/*  751:     */             DetallePedidoCliente detallePedidoCliente;
/*  752: 900 */             if ((producto.getUnidadVenta() != null) && (producto.isActivo()) && (producto.isIndicadorVenta()))
/*  753:     */             {
/*  754: 901 */               detallePedidoCliente = new DetallePedidoCliente();
/*  755: 902 */               detallePedidoCliente.setIdSucursal(pedidoCliente.getSucursal().getIdSucursal());
/*  756: 903 */               detallePedidoCliente.setIdOrganizacion(pedidoCliente.getIdOrganizacion());
/*  757: 904 */               detallePedidoCliente.setCantidad(cantidad);
/*  758: 905 */               detallePedidoCliente.setPrecio(precio);
/*  759: 906 */               detallePedidoCliente.setProducto(producto);
/*  760: 907 */               detallePedidoCliente.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/*  761: 908 */               detallePedidoCliente.setCodigoIce(producto.getCodigoIce());
/*  762: 909 */               detallePedidoCliente.setIce(producto.getIce());
/*  763: 910 */               detallePedidoCliente.setPedidoCliente(pedidoCliente);
/*  764: 911 */               detallePedidoCliente.setUnidadVenta(producto.getUnidadVenta());
/*  765: 912 */               detallePedidoCliente.setCantidadUnidadDespacho(cantidadDespacho);
/*  766: 913 */               detallePedidoCliente.setCantidadEmbalajeDespacho(unidadMedida);
/*  767:     */               
/*  768: 915 */               pedidoCliente.getListaDetallePedidoCliente().add(detallePedidoCliente);
/*  769: 917 */               if (producto.isIndicadorImpuestos())
/*  770:     */               {
/*  771: 919 */                 List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, pedidoCliente.getFecha());
/*  772: 921 */                 for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/*  773:     */                 {
/*  774: 923 */                   ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente = new ImpuestoProductoPedidoCliente();
/*  775: 924 */                   impuestoProductoPedidoCliente.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/*  776: 925 */                   impuestoProductoPedidoCliente.setImpuesto(rangoImpuesto.getImpuesto());
/*  777: 926 */                   impuestoProductoPedidoCliente.setDetallePedidoCliente(detallePedidoCliente);
/*  778: 927 */                   detallePedidoCliente.getListaImpuestoProductoPedidoCliente().add(impuestoProductoPedidoCliente);
/*  779:     */                 }
/*  780:     */               }
/*  781:     */             }
/*  782:     */             else
/*  783:     */             {
/*  784: 931 */               String mensaje = "";
/*  785: 932 */               if (producto.getUnidadVenta() == null) {
/*  786: 933 */                 mensaje = "Falta definir Unidad Venta en el producto";
/*  787:     */               }
/*  788: 935 */               if (!producto.isActivo()) {
/*  789: 936 */                 mensaje = "Producto no activo";
/*  790:     */               }
/*  791: 938 */               if (!producto.isIndicadorVenta()) {
/*  792: 939 */                 mensaje = "Producto no es de venta";
/*  793:     */               }
/*  794: 942 */               AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { i + 1 + "", "2", codigoProducto, mensaje });
/*  795:     */               
/*  796: 944 */               listaErrores.add(error);
/*  797:     */             }
/*  798:     */           }
/*  799:     */         }
/*  800:     */       }
/*  801: 951 */       if (pedidoCliente != null)
/*  802:     */       {
/*  803: 952 */         totalizar(pedidoCliente);
/*  804: 953 */         lista.add(pedidoCliente);
/*  805:     */       }
/*  806:     */     }
/*  807:     */     catch (IOException e)
/*  808:     */     {
/*  809: 957 */       this.context.setRollbackOnly();
/*  810: 958 */       e.printStackTrace();
/*  811: 959 */       throw new AS2Exception("msg_error_lectura_fichero");
/*  812:     */     }
/*  813:     */     catch (ExcepcionAS2Ventas e)
/*  814:     */     {
/*  815: 961 */       this.context.setRollbackOnly();
/*  816: 962 */       e.printStackTrace();
/*  817: 963 */       throw new AS2Exception(e.getCodigoExcepcion());
/*  818:     */     }
/*  819:     */     catch (ExcepcionAS2Inventario e)
/*  820:     */     {
/*  821: 965 */       this.context.setRollbackOnly();
/*  822: 966 */       e.printStackTrace();
/*  823: 967 */       throw new AS2Exception(e.getCodigoExcepcion());
/*  824:     */     }
/*  825: 970 */     Object[] resultado = new Object[2];
/*  826: 971 */     resultado[0] = lista;
/*  827: 972 */     resultado[1] = listaErrores;
/*  828:     */     
/*  829: 974 */     return resultado;
/*  830:     */   }
/*  831:     */   
/*  832:     */   private PedidoCliente crearPedidoCliente(PedidoCliente pedidoClienteBase)
/*  833:     */   {
/*  834: 979 */     PedidoCliente pedidoCliente = new PedidoCliente();
/*  835: 980 */     pedidoCliente.setIdOrganizacion(pedidoClienteBase.getIdOrganizacion());
/*  836: 981 */     pedidoCliente.setSucursal(pedidoClienteBase.getSucursal());
/*  837: 982 */     pedidoCliente.setBodega(pedidoClienteBase.getBodega());
/*  838: 983 */     pedidoCliente.setNumero("");
/*  839: 984 */     pedidoCliente.setFecha(pedidoClienteBase.getFecha());
/*  840: 985 */     pedidoCliente.setFechaDespacho(pedidoClienteBase.getFechaDespacho());
/*  841: 986 */     pedidoCliente.setEstado(pedidoClienteBase.getEstado());
/*  842: 987 */     pedidoCliente.setAgenteComercial(pedidoClienteBase.getAgenteComercial());
/*  843: 988 */     pedidoCliente.setDocumento(pedidoClienteBase.getDocumento());
/*  844: 989 */     pedidoCliente.setEmpresa(pedidoClienteBase.getEmpresa());
/*  845: 990 */     pedidoCliente.setSubempresa(pedidoClienteBase.getSubempresa());
/*  846: 991 */     pedidoCliente.setDireccionEmpresa(pedidoClienteBase.getDireccionEmpresa());
/*  847: 992 */     pedidoCliente.setCondicionPago(pedidoClienteBase.getCondicionPago());
/*  848: 993 */     pedidoCliente.setNumeroCuotas(pedidoClienteBase.getNumeroCuotas());
/*  849: 994 */     pedidoCliente.setZona(pedidoClienteBase.getZona());
/*  850: 995 */     pedidoCliente.setCanal(pedidoClienteBase.getCanal());
/*  851: 996 */     pedidoCliente.setMotivoPedidoCliente(pedidoClienteBase.getMotivoPedidoCliente());
/*  852: 997 */     pedidoCliente.setTransportista(pedidoClienteBase.getTransportista());
/*  853:     */     
/*  854: 999 */     return pedidoCliente;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public Object[] cargarPedidosClienteExcelSupermaxi(PedidoCliente pedidoClienteBase, String fileName, InputStream imInputStream, int filaInicial)
/*  858:     */     throws AS2Exception
/*  859:     */   {
/*  860:1005 */     List<AS2Exception> listaErrores = new ArrayList();
/*  861:     */     
/*  862:1007 */     PedidoCliente pedidoCliente = crearPedidoCliente(pedidoClienteBase);
/*  863:     */     
/*  864:     */ 
/*  865:1010 */     List<PedidoCliente> lista = new ArrayList();
/*  866:     */     try
/*  867:     */     {
/*  868:1013 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2ColumnasIrregulares(pedidoClienteBase.getIdOrganizacion(), fileName, imInputStream, filaInicial, 0, 22);
/*  869:     */       
/*  870:1015 */       int numeroFila = filaInicial;
/*  871:1016 */       for (HSSFCell[] fila : datos)
/*  872:     */       {
/*  873:1017 */         String tipoDato = fila[0] != null ? fila[0].getStringCellValue().trim() : "";
/*  874:1018 */         if (tipoDato.equals("1"))
/*  875:     */         {
/*  876:1019 */           String numPed = "";
/*  877:     */           try
/*  878:     */           {
/*  879:1021 */             numPed = fila[6] != null ? fila[6].getStringCellValue().trim() : "";
/*  880:     */           }
/*  881:     */           catch (Exception e)
/*  882:     */           {
/*  883:1023 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "7", "", "" });
/*  884:1024 */             listaErrores.add(error);
/*  885:     */           }
/*  886:1026 */           String tipoPed = "";
/*  887:     */           try
/*  888:     */           {
/*  889:1028 */             tipoPed = fila[7] != null ? fila[7].getStringCellValue().trim() : "";
/*  890:     */           }
/*  891:     */           catch (Exception e)
/*  892:     */           {
/*  893:1030 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "8", "", "" });
/*  894:1031 */             listaErrores.add(error);
/*  895:     */           }
/*  896:1033 */           String fechaDespacho = "";
/*  897:     */           try
/*  898:     */           {
/*  899:1035 */             fechaDespacho = fila[4] != null ? fila[3].getStringCellValue().trim() : "";
/*  900:     */           }
/*  901:     */           catch (Exception e)
/*  902:     */           {
/*  903:1037 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "4", "", "" });
/*  904:1038 */             listaErrores.add(error);
/*  905:     */           }
/*  906:1040 */           SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/*  907:     */           try
/*  908:     */           {
/*  909:1042 */             pedidoCliente.setFechaDespacho(sdf.parse(fechaDespacho.trim()));
/*  910:     */           }
/*  911:     */           catch (ParseException e)
/*  912:     */           {
/*  913:1044 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "5", fechaDespacho.trim(), "yyyyMMdd" });
/*  914:     */             
/*  915:1046 */             listaErrores.add(error);
/*  916:     */           }
/*  917:1048 */           pedidoCliente.setDescripcion("Numero Pedido Supermaxi " + numPed);
/*  918:1049 */           Map<String, String> filtros = new HashMap();
/*  919:1050 */           filtros.put("idOrganizacion", pedidoCliente.getIdOrganizacion() + "");
/*  920:1051 */           filtros.put("referencia1", "=" + numPed);
/*  921:1052 */           filtros.put("estado", "!=" + Estado.ANULADO);
/*  922:1053 */           List<PedidoCliente> listaPedidoExiste = obtenerListaCombo("referencia1", true, filtros);
/*  923:1054 */           if (listaPedidoExiste.size() > 0) {
/*  924:1055 */             throw new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "7", numPed, "ya existe codigo pedido" });
/*  925:     */           }
/*  926:1057 */           pedidoCliente.setReferencia1(numPed);
/*  927:1060 */           if ((tipoPed != null) && (!tipoPed.trim().isEmpty()) && (tipoPed.startsWith("F"))) {
/*  928:1061 */             pedidoCliente.setIndicadorFijo(true);
/*  929:     */           }
/*  930:     */         }
/*  931:1064 */         if (tipoDato.equals("2"))
/*  932:     */         {
/*  933:1065 */           String codigoProducto = "";
/*  934:     */           try
/*  935:     */           {
/*  936:1067 */             codigoProducto = fila[1] != null ? fila[1].getStringCellValue().trim() : "";
/*  937:     */           }
/*  938:     */           catch (Exception e)
/*  939:     */           {
/*  940:1069 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "2", "", "" });
/*  941:1070 */             listaErrores.add(error);
/*  942:     */           }
/*  943:1073 */           BigDecimal unidadMedida = BigDecimal.ZERO;
/*  944:     */           try
/*  945:     */           {
/*  946:1075 */             unidadMedida = fila[8] != null ? new BigDecimal(fila[8].getStringCellValue()) : BigDecimal.ZERO;
/*  947:     */           }
/*  948:     */           catch (Exception e)
/*  949:     */           {
/*  950:1077 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "9", "", "" });
/*  951:1078 */             listaErrores.add(error);
/*  952:     */           }
/*  953:1081 */           BigDecimal precio = BigDecimal.ZERO;
/*  954:     */           try
/*  955:     */           {
/*  956:1084 */             precio = fila[9] != null ? FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[9].getStringCellValue().replace(',', '.')), 6) : BigDecimal.ZERO;
/*  957:     */           }
/*  958:     */           catch (Exception e)
/*  959:     */           {
/*  960:1087 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "10", "", "" });
/*  961:1088 */             listaErrores.add(error);
/*  962:     */           }
/*  963:1091 */           BigDecimal cantidad = BigDecimal.ZERO;
/*  964:     */           try
/*  965:     */           {
/*  966:1093 */             cantidad = fila[18] != null ? new BigDecimal(fila[18].getStringCellValue()) : BigDecimal.ZERO;
/*  967:     */           }
/*  968:     */           catch (Exception e)
/*  969:     */           {
/*  970:1095 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "19", "", "" });
/*  971:1096 */             listaErrores.add(error);
/*  972:     */           }
/*  973:1104 */           String descripcionDetalle = "";
/*  974:     */           try
/*  975:     */           {
/*  976:1106 */             descripcionDetalle = fila[21] != null ? fila[21].getStringCellValue().trim() : "";
/*  977:     */           }
/*  978:     */           catch (Exception e)
/*  979:     */           {
/*  980:1108 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "22", "", "" });
/*  981:1109 */             listaErrores.add(error);
/*  982:     */           }
/*  983:1115 */           Producto producto = null;
/*  984:     */           try
/*  985:     */           {
/*  986:1117 */             producto = this.servicioProducto.buscarPorCualquierCodigo(codigoProducto, pedidoCliente.getIdOrganizacion());
/*  987:     */           }
/*  988:     */           catch (ExcepcionAS2 e)
/*  989:     */           {
/*  990:1119 */             AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "2", codigoProducto, "msg_info_no_existe_producto" });
/*  991:     */             
/*  992:1121 */             listaErrores.add(error);
/*  993:     */           }
/*  994:1123 */           if (producto != null)
/*  995:     */           {
/*  996:     */             DetallePedidoCliente detallePedidoCliente;
/*  997:1124 */             if ((producto.getUnidadVenta() != null) && (producto.isActivo()) && (producto.isIndicadorVenta()))
/*  998:     */             {
/*  999:1125 */               detallePedidoCliente = new DetallePedidoCliente();
/* 1000:1126 */               detallePedidoCliente.setIdSucursal(pedidoCliente.getSucursal().getIdSucursal());
/* 1001:1127 */               detallePedidoCliente.setIdOrganizacion(pedidoCliente.getIdOrganizacion());
/* 1002:1128 */               detallePedidoCliente.setCantidad(cantidad.multiply(unidadMedida));
/* 1003:1129 */               detallePedidoCliente.setReferencia1(descripcionDetalle);
/* 1004:1130 */               detallePedidoCliente.setPrecio(precio);
/* 1005:1131 */               detallePedidoCliente.setProducto(producto);
/* 1006:1132 */               detallePedidoCliente.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 1007:1133 */               detallePedidoCliente.setCodigoIce(producto.getCodigoIce());
/* 1008:1134 */               detallePedidoCliente.setIce(producto.getIce());
/* 1009:1135 */               detallePedidoCliente.setPedidoCliente(pedidoCliente);
/* 1010:1136 */               detallePedidoCliente.setUnidadVenta(producto.getUnidadVenta());
/* 1011:1137 */               detallePedidoCliente.setCantidadUnidadDespacho(cantidad);
/* 1012:1138 */               detallePedidoCliente.setCantidadEmbalajeDespacho(unidadMedida);
/* 1013:     */               
/* 1014:1140 */               pedidoCliente.getListaDetallePedidoCliente().add(detallePedidoCliente);
/* 1015:1142 */               if (producto.isIndicadorImpuestos())
/* 1016:     */               {
/* 1017:1144 */                 List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, pedidoCliente.getFecha());
/* 1018:1146 */                 for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 1019:     */                 {
/* 1020:1148 */                   ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente = new ImpuestoProductoPedidoCliente();
/* 1021:1149 */                   impuestoProductoPedidoCliente.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 1022:1150 */                   impuestoProductoPedidoCliente.setImpuesto(rangoImpuesto.getImpuesto());
/* 1023:1151 */                   impuestoProductoPedidoCliente.setDetallePedidoCliente(detallePedidoCliente);
/* 1024:1152 */                   detallePedidoCliente.getListaImpuestoProductoPedidoCliente().add(impuestoProductoPedidoCliente);
/* 1025:     */                 }
/* 1026:     */               }
/* 1027:     */             }
/* 1028:     */             else
/* 1029:     */             {
/* 1030:1156 */               String mensaje = "";
/* 1031:1157 */               if (producto.getUnidadVenta() == null) {
/* 1032:1158 */                 mensaje = "Falta definir Unidad Venta en el producto";
/* 1033:     */               }
/* 1034:1160 */               if (!producto.isActivo()) {
/* 1035:1161 */                 mensaje = "Producto no activo";
/* 1036:     */               }
/* 1037:1163 */               if (!producto.isIndicadorVenta()) {
/* 1038:1164 */                 mensaje = "Producto no es de venta";
/* 1039:     */               }
/* 1040:1167 */               AS2Exception error = new AS2Exception("msg_error_migracion_excel", new String[] { numeroFila + "", "2", codigoProducto, mensaje });
/* 1041:     */               
/* 1042:1169 */               listaErrores.add(error);
/* 1043:     */             }
/* 1044:     */           }
/* 1045:     */         }
/* 1046:1173 */         numeroFila++;
/* 1047:     */       }
/* 1048:1175 */       totalizar(pedidoCliente);
/* 1049:1176 */       lista.add(pedidoCliente);
/* 1050:     */     }
/* 1051:     */     catch (IOException e)
/* 1052:     */     {
/* 1053:1178 */       this.context.setRollbackOnly();
/* 1054:1179 */       e.printStackTrace();
/* 1055:1180 */       throw new AS2Exception("msg_error_lectura_fichero");
/* 1056:     */     }
/* 1057:     */     catch (ExcepcionAS2Ventas e)
/* 1058:     */     {
/* 1059:1182 */       this.context.setRollbackOnly();
/* 1060:1183 */       e.printStackTrace();
/* 1061:1184 */       throw new AS2Exception(e.getCodigoExcepcion());
/* 1062:     */     }
/* 1063:     */     catch (ExcepcionAS2Inventario e)
/* 1064:     */     {
/* 1065:1186 */       this.context.setRollbackOnly();
/* 1066:1187 */       e.printStackTrace();
/* 1067:1188 */       throw new AS2Exception(e.getCodigoExcepcion());
/* 1068:     */     }
/* 1069:     */     HSSFCell[][] datos;
/* 1070:1191 */     Object[] resultado = new Object[2];
/* 1071:1192 */     resultado[0] = lista;
/* 1072:1193 */     resultado[1] = listaErrores;
/* 1073:     */     
/* 1074:1195 */     return resultado;
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   public void cargarDetallePedidoClienteExcel(PedidoCliente pedidoCliente, String fileName, InputStream imInputStream, int filaInicial)
/* 1078:     */     throws ExcepcionAS2
/* 1079:     */   {
/* 1080:1208 */     List<DetallePedidoCliente> listAux = new ArrayList();
/* 1081:     */     try
/* 1082:     */     {
/* 1083:1211 */       DetallePedidoCliente detallePedidoCliente = null;
/* 1084:     */       
/* 1085:1213 */       HashMap<String, Producto> hmProducto = new HashMap();
/* 1086:     */       
/* 1087:1215 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(pedidoCliente.getIdOrganizacion(), fileName, imInputStream, filaInicial, 0);
/* 1088:1217 */       for (HSSFCell[] fila : datos)
/* 1089:     */       {
/* 1090:1219 */         String codigoProducto = fila[0] != null ? fila[0].getStringCellValue().trim() : "";
/* 1091:1220 */         BigDecimal cantidad = fila[1] != null ? new BigDecimal(fila[1].getNumericCellValue()) : BigDecimal.ZERO;
/* 1092:1221 */         String notaDetalle = fila[2] != null ? fila[2].getStringCellValue().trim() : "";
/* 1093:1222 */         BigDecimal precio = fila[3] != null ? FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[3].getNumericCellValue()), 6) : BigDecimal.ZERO;
/* 1094:     */         
/* 1095:1224 */         BigDecimal descuento = fila[4] != null ? new BigDecimal(fila[4].getNumericCellValue()) : BigDecimal.ZERO;
/* 1096:     */         
/* 1097:     */ 
/* 1098:     */ 
/* 1099:     */ 
/* 1100:1229 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 1101:1230 */         if (producto == null)
/* 1102:     */         {
/* 1103:1231 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, pedidoCliente.getIdOrganizacion(), null);
/* 1104:1232 */           hmProducto.put(codigoProducto, producto);
/* 1105:     */         }
/* 1106:1235 */         if (producto != null)
/* 1107:     */         {
/* 1108:1236 */           boolean indicadorListaPrecioPorZona = ParametrosSistema.isIndicadorListaPrecioPorZona(pedidoCliente.getIdOrganizacion()).booleanValue();
/* 1109:     */           
/* 1110:     */ 
/* 1111:1239 */           Integer idZona = null;
/* 1112:1240 */           if ((indicadorListaPrecioPorZona) && 
/* 1113:1241 */             (pedidoCliente.getZona() != null)) {
/* 1114:1242 */             idZona = Integer.valueOf(pedidoCliente.getZona().getId());
/* 1115:     */           }
/* 1116:1246 */           if (pedidoCliente.getEmpresa().getCliente().getListaPrecios() != null)
/* 1117:     */           {
/* 1118:1248 */             detallePedidoCliente = new DetallePedidoCliente();
/* 1119:1249 */             detallePedidoCliente.setIdSucursal(pedidoCliente.getSucursal().getIdSucursal());
/* 1120:1250 */             detallePedidoCliente.setIdOrganizacion(pedidoCliente.getIdOrganizacion());
/* 1121:1251 */             detallePedidoCliente.setCantidad(cantidad);
/* 1122:1252 */             detallePedidoCliente.setDescripcion(notaDetalle);
/* 1123:1253 */             detallePedidoCliente.setProducto(producto);
/* 1124:1254 */             detallePedidoCliente.setPrecio(precio);
/* 1125:1255 */             detallePedidoCliente.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 1126:1256 */             detallePedidoCliente.setCodigoIce(producto.getCodigoIce());
/* 1127:1257 */             detallePedidoCliente.setIce(producto.getIce());
/* 1128:1258 */             detallePedidoCliente.setDescuento(descuento);
/* 1129:1259 */             detallePedidoCliente.setPedidoCliente(pedidoCliente);
/* 1130:1260 */             detallePedidoCliente.setUnidadVenta(producto.getUnidadVenta());
/* 1131:1262 */             if (ParametrosSistema.isBloqueoProductoListaPrecios(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue())
/* 1132:     */             {
/* 1133:1263 */               DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(pedidoCliente
/* 1134:1264 */                 .getEmpresa().getCliente().getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), pedidoCliente
/* 1135:1265 */                 .getFecha(), idZona, pedidoCliente.getNumero());
/* 1136:1266 */               detallePedidoCliente.setPrecio(dvlp.getPrecioUnitario());
/* 1137:     */             }
/* 1138:1270 */             listAux.add(detallePedidoCliente);
/* 1139:1272 */             if (producto.isIndicadorImpuestos())
/* 1140:     */             {
/* 1141:1274 */               List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto((Producto)hmProducto.get(codigoProducto), pedidoCliente
/* 1142:1275 */                 .getFecha());
/* 1143:1277 */               for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 1144:     */               {
/* 1145:1279 */                 ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente = new ImpuestoProductoPedidoCliente();
/* 1146:1280 */                 impuestoProductoPedidoCliente.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 1147:1281 */                 impuestoProductoPedidoCliente.setImpuesto(rangoImpuesto.getImpuesto());
/* 1148:1282 */                 impuestoProductoPedidoCliente.setDetallePedidoCliente(detallePedidoCliente);
/* 1149:1283 */                 detallePedidoCliente.getListaImpuestoProductoPedidoCliente().add(impuestoProductoPedidoCliente);
/* 1150:     */               }
/* 1151:     */             }
/* 1152:     */           }
/* 1153:1288 */           else if (ParametrosSistema.isBloqueoProductoListaPrecios(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue())
/* 1154:     */           {
/* 1155:1289 */             throw new ExcepcionAS2("msg_error_empresa_lista_precios");
/* 1156:     */           }
/* 1157:     */         }
/* 1158:     */       }
/* 1159:     */     }
/* 1160:     */     catch (IllegalArgumentException e)
/* 1161:     */     {
/* 1162:1297 */       this.context.setRollbackOnly();
/* 1163:1298 */       e.printStackTrace();
/* 1164:1299 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", e.getMessage());
/* 1165:     */     }
/* 1166:     */     catch (ExcepcionAS2 e)
/* 1167:     */     {
/* 1168:1301 */       this.context.setRollbackOnly();
/* 1169:1302 */       e.printStackTrace();
/* 1170:1303 */       listAux.clear();
/* 1171:1304 */       throw e;
/* 1172:     */     }
/* 1173:     */     catch (Exception e)
/* 1174:     */     {
/* 1175:1306 */       this.context.setRollbackOnly();
/* 1176:1307 */       e.printStackTrace();
/* 1177:1308 */       throw new ExcepcionAS2(e);
/* 1178:     */     }
/* 1179:     */     finally
/* 1180:     */     {
/* 1181:1310 */       pedidoCliente.getListaDetallePedidoCliente().addAll(listAux);
/* 1182:1311 */       totalizar(pedidoCliente);
/* 1183:     */     }
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   public void procesarPedidoCliente(PedidoCliente pedidoCliente, Boolean indicadorAutorizacionManual, boolean indicadorAutorizarCompleto, String usuarioAutoriza, Date fechaAutorizacion)
/* 1187:     */     throws ExcepcionAS2Inventario
/* 1188:     */   {
/* 1189:1324 */     if (indicadorAutorizarCompleto)
/* 1190:     */     {
/* 1191:1325 */       this.pedidoClienteDao.actualizarEstado(pedidoCliente, Estado.PROCESADO, pedidoCliente.getDescripcion(), indicadorAutorizacionManual, usuarioAutoriza, fechaAutorizacion);
/* 1192:     */       
/* 1193:1327 */       liberarGenerarSaldoComprometido(pedidoCliente, false);
/* 1194:     */       try
/* 1195:     */       {
/* 1196:1329 */         enviarEmail(pedidoCliente);
/* 1197:     */       }
/* 1198:     */       catch (Exception e)
/* 1199:     */       {
/* 1200:1331 */         System.out.println("Ocurrio un error al enviar mail");
/* 1201:     */       }
/* 1202:     */     }
/* 1203:     */     else
/* 1204:     */     {
/* 1205:1334 */       this.pedidoClienteDao.actualizarEstado(pedidoCliente, Estado.APROBADO_PARCIAL, pedidoCliente.getDescripcion(), indicadorAutorizacionManual, usuarioAutoriza, fechaAutorizacion);
/* 1206:     */     }
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public void enviarEmail(PedidoCliente pedidoCliente)
/* 1210:     */   {
/* 1211:1341 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 1212:1342 */     List<Contacto> listaContactos = this.pedidoClienteDao.getContactosPedidoCliente(pedidoCliente);
/* 1213:1344 */     if (listaContactos.size() > 0)
/* 1214:     */     {
/* 1215:1345 */       Contacto contacto = (Contacto)listaContactos.get(0);
/* 1216:1346 */       String bodyText = contacto.getTipoContacto().getTextoCuerpoCorreoPedidoCliente();
/* 1217:1347 */       bodyText = bodyText.replaceAll(":numeroComprobante:", pedidoCliente.getNumero());
/* 1218:1348 */       bodyText = bodyText.replaceAll(":fechaComprobante:", sdf.format(pedidoCliente.getFecha()));
/* 1219:1349 */       bodyText = bodyText.replaceAll(":nombreCliente:", pedidoCliente.getEmpresa().getNombreFiscal());
/* 1220:1350 */       bodyText = bodyText.replaceAll(":identificacionCliente:", pedidoCliente.getEmpresa().getIdentificacion());
/* 1221:     */       
/* 1222:1352 */       Organizacion organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(pedidoCliente.getIdOrganizacion()));
/* 1223:     */       
/* 1224:1354 */       String asunto = "Pedido Cliente No. " + pedidoCliente.getNumero() + " - " + pedidoCliente.getEmpresa().getNombreFiscal() + " => " + organizacion.getRazonSocial();
/* 1225:1355 */       String emails = "";
/* 1226:1356 */       if (pedidoCliente.getSubempresa() != null) {
/* 1227:1357 */         emails = this.servicioEmpresa.cargarMails(pedidoCliente.getSubempresa().getEmpresa(), DocumentoBase.PEDIDO_CLIENTE);
/* 1228:     */       } else {
/* 1229:1359 */         emails = this.servicioEmpresa.cargarMails(pedidoCliente.getEmpresa(), DocumentoBase.PEDIDO_CLIENTE);
/* 1230:     */       }
/* 1231:1362 */       List<Object[]> listaDatosReporte = new ArrayList();
/* 1232:     */       try
/* 1233:     */       {
/* 1234:1364 */         listaDatosReporte = getReportePedidoCliente(pedidoCliente.getIdPedidoCliente());
/* 1235:     */       }
/* 1236:     */       catch (ExcepcionAS2 e)
/* 1237:     */       {
/* 1238:1366 */         System.out.println("Error generando el reporte");
/* 1239:     */       }
/* 1240:1368 */       JRDataSource ds = new QueryResultDataSource(listaDatosReporte, ReportePedidoClienteBean.fields);
/* 1241:     */       
/* 1242:1370 */       this.servicioEnvioEmail.enviarEmailComprobanteNoElectronicos(organizacion, pedidoCliente.getSucursal().getId(), emails, asunto, bodyText, pedidoCliente
/* 1243:1371 */         .getDocumento().getDocumentoBase(), pedidoCliente.getNumero(), ds, ReportePedidoClienteBean.COMPILE_FILE_NAME, pedidoCliente
/* 1244:1372 */         .getUsuarioCreacion());
/* 1245:     */     }
/* 1246:     */   }
/* 1247:     */   
/* 1248:     */   public void obtenerImpuestosProductos(Producto producto, DetallePedidoCliente d)
/* 1249:     */     throws ExcepcionAS2
/* 1250:     */   {
/* 1251:     */     try
/* 1252:     */     {
/* 1253:1379 */       producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/* 1254:1380 */       List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, d.getPedidoCliente().getFecha());
/* 1255:1381 */       for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 1256:     */       {
/* 1257:1382 */         ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente = new ImpuestoProductoPedidoCliente();
/* 1258:1383 */         impuestoProductoPedidoCliente.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 1259:1384 */         impuestoProductoPedidoCliente.setImpuesto(rangoImpuesto.getImpuesto());
/* 1260:1385 */         impuestoProductoPedidoCliente.setDetallePedidoCliente(d);
/* 1261:1386 */         d.getListaImpuestoProductoPedidoCliente().add(impuestoProductoPedidoCliente);
/* 1262:     */       }
/* 1263:     */     }
/* 1264:     */     catch (ExcepcionAS2 ex)
/* 1265:     */     {
/* 1266:1389 */       LOG.info("Error es: " + ex.getErrorMessage(ex));
/* 1267:1390 */       throw new ExcepcionAS2("msg_producto_no_encontrado", ex.getMessage());
/* 1268:     */     }
/* 1269:     */     catch (Exception e)
/* 1270:     */     {
/* 1271:1392 */       LOG.info(e);
/* 1272:     */     }
/* 1273:     */   }
/* 1274:     */   
/* 1275:     */   public List<Object[]> getReporteLogisticaTransportista(Date fechaDesde, Date fechaHasta, int idOrganizacion, Transportista transportista, Ruta ruta, Empresa empresa)
/* 1276:     */   {
/* 1277:1399 */     return this.pedidoClienteDao.getReporteLogisticaTransportista(fechaDesde, fechaHasta, idOrganizacion, transportista, ruta, empresa);
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   public List<Object[]> getReporteProductoPedidoVsFactura(Date fechaDesde, Date fechaHasta, int idCategoriaEmpresa, int idEmpresa, int idZona, int idCanal, int idAgenteComercial, int idSucursal, int idOrganizacion, DocumentoBase documentoBase)
/* 1281:     */   {
/* 1282:1407 */     HashMap<Integer, Object[]> hmDetalle = new HashMap();
/* 1283:1408 */     List<Object[]> listaDetalleFactura = new ArrayList();
/* 1284:1409 */     List<Object[]> listaDetallePedido = new ArrayList();
/* 1285:     */     
/* 1286:     */ 
/* 1287:1412 */     HashMap<Integer, List<Integer>> hmIdDetallePedido = new HashMap();
/* 1288:1413 */     int clave = 1;
/* 1289:     */     Iterator localIterator;
/* 1290:1415 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[documentoBase.ordinal()])
/* 1291:     */     {
/* 1292:     */     case 1: 
/* 1293:1419 */       listaDetallePedido = this.pedidoClienteDao.getReporteProductoPedidoVsFactura(fechaDesde, fechaHasta, idCategoriaEmpresa, idEmpresa, idZona, idCanal, idAgenteComercial, idSucursal, idOrganizacion, null);
/* 1294:1422 */       for (localIterator = listaDetallePedido.iterator(); localIterator.hasNext(); clave = listaIdDetallePedido.size() == 2000 ? clave : clave)
/* 1295:     */       {
/* 1296:1422 */         Object[] detallePedido = (Object[])localIterator.next();
/* 1297:1423 */         List<Integer> listaIdDetallePedido = (List)hmIdDetallePedido.get(Integer.valueOf(clave));
/* 1298:1424 */         if (listaIdDetallePedido == null) {
/* 1299:1425 */           listaIdDetallePedido = new ArrayList();
/* 1300:     */         }
/* 1301:1427 */         listaIdDetallePedido.add((Integer)detallePedido[0]);
/* 1302:1428 */         hmIdDetallePedido.put(Integer.valueOf(clave), listaIdDetallePedido);
/* 1303:1429 */         clave++;
/* 1304:     */       }
/* 1305:1433 */       for (Integer key : hmIdDetallePedido.keySet())
/* 1306:     */       {
/* 1307:1434 */         List<Integer> listaIdDetallePedido = (List)hmIdDetallePedido.get(key);
/* 1308:1435 */         if ((listaIdDetallePedido != null) && (!listaIdDetallePedido.isEmpty()))
/* 1309:     */         {
/* 1310:1436 */           List<Object[]> lista = this.facturaClienteDao.getReporteProductoPedidoVsFactura(null, null, idCategoriaEmpresa, idEmpresa, idZona, idCanal, idAgenteComercial, idSucursal, idOrganizacion, listaIdDetallePedido);
/* 1311:     */           
/* 1312:1438 */           listaDetalleFactura.addAll(lista);
/* 1313:     */         }
/* 1314:     */       }
/* 1315:1443 */       for (Object[] detalleFactura : listaDetalleFactura) {
/* 1316:1444 */         hmDetalle.put((Integer)detalleFactura[0], detalleFactura);
/* 1317:     */       }
/* 1318:1448 */       for (Object[] detallePedido : listaDetallePedido)
/* 1319:     */       {
/* 1320:1449 */         detallePedido[21] = null;
/* 1321:1450 */         Object[] detalleFactura = (Object[])hmDetalle.get((Integer)detallePedido[0]);
/* 1322:1451 */         if (detalleFactura != null)
/* 1323:     */         {
/* 1324:1452 */           detallePedido[17] = detalleFactura[13];
/* 1325:1453 */           detallePedido[18] = detalleFactura[14];
/* 1326:1454 */           detallePedido[19] = detalleFactura[15];
/* 1327:1455 */           detallePedido[20] = detalleFactura[16];
/* 1328:1456 */           detallePedido[21] = detalleFactura[1];
/* 1329:1457 */           detallePedido[22] = detalleFactura[2];
/* 1330:     */         }
/* 1331:     */       }
/* 1332:1460 */       return listaDetallePedido;
/* 1333:     */     case 2: 
/* 1334:1465 */       listaDetalleFactura = this.facturaClienteDao.getReporteProductoPedidoVsFactura(fechaDesde, fechaHasta, idCategoriaEmpresa, idEmpresa, idZona, idCanal, idAgenteComercial, idSucursal, idOrganizacion, null);
/* 1335:1468 */       for (localIterator = listaDetalleFactura.iterator(); localIterator.hasNext(); clave = listaIdDetallePedido.size() == 2000 ? clave : clave)
/* 1336:     */       {
/* 1337:1468 */         Object[] detalleFactura = (Object[])localIterator.next();
/* 1338:1469 */         List<Integer> listaIdDetallePedido = (List)hmIdDetallePedido.get(Integer.valueOf(clave));
/* 1339:1470 */         if (listaIdDetallePedido == null) {
/* 1340:1471 */           listaIdDetallePedido = new ArrayList();
/* 1341:     */         }
/* 1342:1473 */         if (detalleFactura[0] != null)
/* 1343:     */         {
/* 1344:1474 */           listaIdDetallePedido.add((Integer)detalleFactura[0]);
/* 1345:1475 */           hmIdDetallePedido.put(Integer.valueOf(clave), listaIdDetallePedido);
/* 1346:     */         }
/* 1347:1477 */         clave++;
/* 1348:     */       }
/* 1349:1481 */       for (Integer key : hmIdDetallePedido.keySet())
/* 1350:     */       {
/* 1351:1482 */         List<Integer> listaIdDetallePedido = (List)hmIdDetallePedido.get(key);
/* 1352:1483 */         if ((listaIdDetallePedido != null) && (!listaIdDetallePedido.isEmpty()))
/* 1353:     */         {
/* 1354:1484 */           List<Object[]> lista = this.pedidoClienteDao.getReporteProductoPedidoVsFactura(null, null, idCategoriaEmpresa, idEmpresa, idZona, idCanal, idAgenteComercial, idSucursal, idOrganizacion, listaIdDetallePedido);
/* 1355:     */           
/* 1356:1486 */           listaDetallePedido.addAll(lista);
/* 1357:     */         }
/* 1358:     */       }
/* 1359:1491 */       for (Object[] detallePedido : listaDetallePedido) {
/* 1360:1492 */         hmDetalle.put((Integer)detallePedido[0], detallePedido);
/* 1361:     */       }
/* 1362:1496 */       for (Object[] detalleFactura : listaDetalleFactura)
/* 1363:     */       {
/* 1364:1497 */         detalleFactura[21] = null;
/* 1365:1498 */         Object[] detallePedido = (Object[])hmDetalle.get((Integer)detalleFactura[0]);
/* 1366:1499 */         if (detallePedido != null)
/* 1367:     */         {
/* 1368:1500 */           detalleFactura[17] = detallePedido[13];
/* 1369:1501 */           detalleFactura[18] = detallePedido[14];
/* 1370:1502 */           detalleFactura[19] = detallePedido[15];
/* 1371:1503 */           detalleFactura[20] = detallePedido[16];
/* 1372:1504 */           detalleFactura[21] = detallePedido[1];
/* 1373:1505 */           detalleFactura[22] = detallePedido[2];
/* 1374:     */         }
/* 1375:     */       }
/* 1376:1509 */       return listaDetalleFactura;
/* 1377:     */     }
/* 1378:1512 */     return new ArrayList();
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   public DetalleCalculoImpuestoResponseDto calcularImpuestosDetalleProducto(Producto producto, boolean empresaExcentoImpuesto, Date fecha, Sucursal sucursal, BigDecimal cantidad, BigDecimal baseImponibleInicial)
/* 1382:     */     throws ExcepcionAS2
/* 1383:     */   {
/* 1384:     */     try
/* 1385:     */     {
/* 1386:1524 */       BigDecimal iceLinea = BigDecimal.ZERO;
/* 1387:1525 */       if (producto.isIndicadorPorcentajeIce()) {
/* 1388:1526 */         iceLinea = FuncionesUtiles.porcentaje(baseImponibleInicial, producto.getIce());
/* 1389:     */       } else {
/* 1390:1528 */         iceLinea = FuncionesUtiles.redondearBigDecimal(cantidad.multiply(producto.getIce()), 2);
/* 1391:     */       }
/* 1392:1531 */       BigDecimal baseImponibleTotal = baseImponibleInicial.add(iceLinea);
/* 1393:     */       
/* 1394:     */ 
/* 1395:1534 */       BigDecimal valorImpuestosTributarios = BigDecimal.ZERO;
/* 1396:1535 */       if ((producto.isIndicadorImpuestos()) && (!empresaExcentoImpuesto))
/* 1397:     */       {
/* 1398:1536 */         producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/* 1399:     */         
/* 1400:1538 */         List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, fecha);
/* 1401:1539 */         for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 1402:     */         {
/* 1403:1540 */           ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente = new ImpuestoProductoPedidoCliente();
/* 1404:1541 */           impuestoProductoPedidoCliente.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 1405:1542 */           impuestoProductoPedidoCliente.setImpuesto(rangoImpuesto.getImpuesto());
/* 1406:1543 */           impuestoProductoPedidoCliente.setProducto(producto);
/* 1407:1544 */           impuestoProductoPedidoCliente.setCantidad(cantidad);
/* 1408:1545 */           impuestoProductoPedidoCliente.setBaseImponible(baseImponibleTotal);
/* 1409:1546 */           valorImpuestosTributarios = valorImpuestosTributarios.add(impuestoProductoPedidoCliente.getImpuestoProducto());
/* 1410:     */         }
/* 1411:     */       }
/* 1412:1551 */       BigDecimal porcentajeDescuento = sucursal.getCompensacionSolidaria();
/* 1413:1552 */       BigDecimal valorDescuentoImpuestoLinea = BigDecimal.ZERO;
/* 1414:1553 */       if (FuncionesUtiles.redondearBigDecimal(valorImpuestosTributarios, 8).compareTo(BigDecimal.ZERO) > 0) {
/* 1415:1554 */         valorDescuentoImpuestoLinea = FuncionesUtiles.porcentaje(baseImponibleTotal, porcentajeDescuento, 10);
/* 1416:     */       }
/* 1417:1558 */       DetalleCalculoImpuestoResponseDto respuesta = new DetalleCalculoImpuestoResponseDto();
/* 1418:1559 */       respuesta.setIceLinea(iceLinea);
/* 1419:1560 */       respuesta.setValorImpuestosTributarios(valorImpuestosTributarios);
/* 1420:1561 */       respuesta.setValorDescuentoImpuestoLinea(valorDescuentoImpuestoLinea);
/* 1421:     */       
/* 1422:1563 */       return respuesta;
/* 1423:     */     }
/* 1424:     */     catch (ExcepcionAS2 ex)
/* 1425:     */     {
/* 1426:1565 */       LOG.info("Error es: " + ex.getErrorMessage(ex));
/* 1427:1566 */       throw new ExcepcionAS2("msg_producto_no_encontrado", ex.getMessage());
/* 1428:     */     }
/* 1429:     */     catch (Exception e)
/* 1430:     */     {
/* 1431:1568 */       LOG.info(e);
/* 1432:1569 */       throw new ExcepcionAS2(e);
/* 1433:     */     }
/* 1434:     */   }
/* 1435:     */   
/* 1436:     */   public void actualizarAtributoEntidad(PedidoCliente pedidoCliente, HashMap<String, Object> campos)
/* 1437:     */   {
/* 1438:1575 */     this.pedidoClienteDao.actualizarAtributoEntidad(pedidoCliente, campos);
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public List<Producto> cargarPedidoSugerido(int idOrganizacion, Empresa empresa, Subempresa subempresa)
/* 1442:     */     throws AS2Exception
/* 1443:     */   {
/* 1444:     */     try
/* 1445:     */     {
/* 1446:1581 */       Map<Integer, Producto> mapaProductos = new HashMap();
/* 1447:     */       List<Producto> listaProductosNuevos;
/* 1448:     */       Producto producto;
/* 1449:1582 */       if ((empresa != null) && (empresa.getCliente() != null) && (empresa.getCliente().getIndicadorCargarPedidoSugerido() != null) && 
/* 1450:1583 */         (empresa.getCliente().getIndicadorCargarPedidoSugerido().booleanValue()))
/* 1451:     */       {
/* 1452:1585 */         listaProductosNuevos = buscarProductosNuevosSugerirPedido(idOrganizacion);
/* 1453:1586 */         for (Iterator localIterator = listaProductosNuevos.iterator(); localIterator.hasNext();)
/* 1454:     */         {
/* 1455:1586 */           producto = (Producto)localIterator.next();
/* 1456:1587 */           mapaProductos.put(Integer.valueOf(producto.getIdProducto()), producto);
/* 1457:     */         }
/* 1458:1590 */         Object listaUltimosProductosVendidos = this.facturaClienteDao.obtenerUltimosProductosVendidos(idOrganizacion, empresa, subempresa);
/* 1459:1591 */         for (Producto producto : (List)listaUltimosProductosVendidos)
/* 1460:     */         {
/* 1461:1594 */           BigDecimal cantidadAdicional = producto.getTraCantidad().multiply(empresa.getCliente().getPorcentajeCrecimientoVentas()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
/* 1462:1595 */           BigDecimal cantidadSugerida = producto.getTraCantidad().add(cantidadAdicional);
/* 1463:1596 */           if ((producto.getMultiploPedido() != null) && (!producto.getMultiploPedido().equals(Integer.valueOf(0))))
/* 1464:     */           {
/* 1465:1597 */             BigDecimal[] division = cantidadSugerida.divideAndRemainder(new BigDecimal(producto.getMultiploPedido().intValue()));
/* 1466:1598 */             BigDecimal resultadoEntero = division[0];
/* 1467:1599 */             BigDecimal restoDivision = division[1];
/* 1468:1600 */             cantidadSugerida = cantidadSugerida.subtract(restoDivision);
/* 1469:     */           }
/* 1470:1603 */           producto.setTraCantidad(cantidadSugerida);
/* 1471:1604 */           mapaProductos.put(Integer.valueOf(producto.getIdProducto()), producto);
/* 1472:     */         }
/* 1473:     */       }
/* 1474:1607 */       for (Producto producto : mapaProductos.values())
/* 1475:     */       {
/* 1476:1609 */         BigDecimal cantidadSugerida = producto.getTraCantidad();
/* 1477:1610 */         int numeroDecimales = 0;
/* 1478:1611 */         cantidadSugerida = cantidadSugerida.setScale(numeroDecimales, RoundingMode.HALF_UP);
/* 1479:1612 */         producto.setTraCantidad(cantidadSugerida);
/* 1480:     */       }
/* 1481:1614 */       List<Producto> listaProducto = new ArrayList();
/* 1482:1615 */       listaProducto.addAll(mapaProductos.values());
/* 1483:     */       
/* 1484:1617 */       return listaProducto;
/* 1485:     */     }
/* 1486:     */     catch (AS2Exception e)
/* 1487:     */     {
/* 1488:1619 */       throw e;
/* 1489:     */     }
/* 1490:     */     catch (Exception e)
/* 1491:     */     {
/* 1492:1621 */       throw new AS2Exception(e.getMessage());
/* 1493:     */     }
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   private List<Producto> buscarProductosNuevosSugerirPedido(int idOrganizacion)
/* 1497:     */     throws AS2Exception
/* 1498:     */   {
/* 1499:1626 */     Map<String, String> filtros = new HashMap();
/* 1500:1627 */     filtros.put("idOrganizacion", "=" + idOrganizacion);
/* 1501:1628 */     filtros.put("activo", "true");
/* 1502:1629 */     filtros.put("indicadorIncluirPedidoSugerido", "true");
/* 1503:1630 */     filtros.put("indicadorVenta", "true");
/* 1504:     */     try
/* 1505:     */     {
/* 1506:1634 */       lista = this.servicioProducto.obtenerListaPorPagina(0, 100000, "nombre", true, filtros);
/* 1507:     */     }
/* 1508:     */     catch (ExcepcionAS2Inventario e)
/* 1509:     */     {
/* 1510:     */       List<Producto> lista;
/* 1511:1636 */       e.printStackTrace();
/* 1512:1637 */       throw new AS2Exception(e.getMessage());
/* 1513:     */     }
/* 1514:     */     List<Producto> lista;
/* 1515:1639 */     return lista;
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   public List<PedidoCliente> getPedidosClienteNoExistentesRegistroPeso(Estado estado, Empresa empresa, RegistroPeso registroPeso)
/* 1519:     */   {
/* 1520:1644 */     return this.pedidoClienteDao.getPedidosClienteNoExistentesRegistroPeso(estado, empresa, registroPeso);
/* 1521:     */   }
/* 1522:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioPedidoClienteImpl
 * JD-Core Version:    0.7.0.1
 */