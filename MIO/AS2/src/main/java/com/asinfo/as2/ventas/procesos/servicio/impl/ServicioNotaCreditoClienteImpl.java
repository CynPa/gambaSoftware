/*    1:     */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*    4:     */ import com.asinfo.as2.compronteselectronicos.ServicioFacturaClienteSRIXML;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*    9:     */ import com.asinfo.as2.dao.CuentaPorCobrarDao;
/*   10:     */ import com.asinfo.as2.dao.DetalleDespachoClienteDao;
/*   11:     */ import com.asinfo.as2.dao.DetalleFacturaClienteDao;
/*   12:     */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   13:     */ import com.asinfo.as2.dao.ImpuestoProductoFacturaClienteDao;
/*   14:     */ import com.asinfo.as2.dao.sri.ComprobanteElectronicoPendienteSRIDao;
/*   15:     */ import com.asinfo.as2.dao.sri.FacturaClienteSRIDao;
/*   16:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   17:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   19:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   20:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   21:     */ import com.asinfo.as2.entities.AnticipoCliente;
/*   22:     */ import com.asinfo.as2.entities.Asiento;
/*   23:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   24:     */ import com.asinfo.as2.entities.Cliente;
/*   25:     */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   26:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   27:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   28:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   29:     */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoCliente;
/*   30:     */ import com.asinfo.as2.entities.DetallePreDevolucionCliente;
/*   31:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   32:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   33:     */ import com.asinfo.as2.entities.Documento;
/*   34:     */ import com.asinfo.as2.entities.Empresa;
/*   35:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   36:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   37:     */ import com.asinfo.as2.entities.Impuesto;
/*   38:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   39:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   40:     */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*   41:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   42:     */ import com.asinfo.as2.entities.MotivoAnulacion;
/*   43:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   44:     */ import com.asinfo.as2.entities.NotaFacturaCliente;
/*   45:     */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*   46:     */ import com.asinfo.as2.entities.Producto;
/*   47:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   48:     */ import com.asinfo.as2.entities.RecepcionDevolucionTransportista;
/*   49:     */ import com.asinfo.as2.entities.Subempresa;
/*   50:     */ import com.asinfo.as2.entities.Sucursal;
/*   51:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   52:     */ import com.asinfo.as2.entities.Transportista;
/*   53:     */ import com.asinfo.as2.entities.Ubicacion;
/*   54:     */ import com.asinfo.as2.entities.Unidad;
/*   55:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   56:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   57:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   58:     */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*   59:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   60:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   61:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   62:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   63:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI;
/*   64:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*   65:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   66:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*   67:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente;
/*   68:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   69:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   70:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   71:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   72:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   73:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*   74:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*   75:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*   76:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRecepcionDevolucionTransportista;
/*   77:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*   78:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   79:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   80:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   81:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   82:     */ import com.asinfo.as2.utils.DatosSRI;
/*   83:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   84:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   85:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   86:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta;
/*   87:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*   88:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   89:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*   90:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioOrdenDespachoCliente;
/*   91:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*   92:     */ import java.io.PrintStream;
/*   93:     */ import java.math.BigDecimal;
/*   94:     */ import java.math.RoundingMode;
/*   95:     */ import java.util.ArrayList;
/*   96:     */ import java.util.Date;
/*   97:     */ import java.util.HashMap;
/*   98:     */ import java.util.Iterator;
/*   99:     */ import java.util.List;
/*  100:     */ import java.util.Map;
/*  101:     */ import java.util.Set;
/*  102:     */ import java.util.TreeMap;
/*  103:     */ import javax.ejb.EJB;
/*  104:     */ import javax.ejb.SessionContext;
/*  105:     */ import javax.ejb.Stateless;
/*  106:     */ import javax.ejb.TransactionAttribute;
/*  107:     */ import javax.ejb.TransactionAttributeType;
/*  108:     */ import javax.ejb.TransactionManagement;
/*  109:     */ import javax.ejb.TransactionManagementType;
/*  110:     */ import javax.faces.model.SelectItem;
/*  111:     */ import org.apache.log4j.Logger;
/*  112:     */ 
/*  113:     */ @Stateless
/*  114:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  115:     */ public class ServicioNotaCreditoClienteImpl
/*  116:     */   extends AbstractServicioAS2Financiero
/*  117:     */   implements ServicioNotaCreditoCliente
/*  118:     */ {
/*  119:     */   private static final long serialVersionUID = -7058356014670051473L;
/*  120:     */   @EJB
/*  121:     */   private transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  122:     */   @EJB
/*  123:     */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  124:     */   @EJB
/*  125:     */   private transient ServicioAnticipoCliente servicioAnticipoCliente;
/*  126:     */   @EJB
/*  127:     */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/*  128:     */   @EJB
/*  129:     */   private transient ServicioLiquidacionAnticipoCliente servicioLiquidacionAnticipoCliente;
/*  130:     */   @EJB
/*  131:     */   private transient ServicioPeriodo servicioPeriodo;
/*  132:     */   @EJB
/*  133:     */   private transient ServicioDocumento servicioDocumento;
/*  134:     */   @EJB
/*  135:     */   private transient ServicioSecuencia servicioSecuencia;
/*  136:     */   @EJB
/*  137:     */   private transient ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  138:     */   @EJB
/*  139:     */   private transient FacturaClienteDao facturaClienteDao;
/*  140:     */   @EJB
/*  141:     */   private transient CuentaPorCobrarDao cuentaPorCobrarDao;
/*  142:     */   @EJB
/*  143:     */   private transient ImpuestoProductoFacturaClienteDao impuestoProductoFacturaClienteDao;
/*  144:     */   @EJB
/*  145:     */   private transient DetalleFacturaClienteDao detalleFacturaClienteDao;
/*  146:     */   @EJB
/*  147:     */   private transient FacturaClienteSRIDao facturaClienteSRIDao;
/*  148:     */   @EJB
/*  149:     */   private transient ServicioProducto servicioProducto;
/*  150:     */   @EJB
/*  151:     */   private transient ServicioInventarioProducto servicioInventarioProducto;
/*  152:     */   @EJB
/*  153:     */   private transient DetalleDespachoClienteDao detalleDespachoClienteDao;
/*  154:     */   @EJB
/*  155:     */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/*  156:     */   @EJB
/*  157:     */   private transient ServicioCosteo servicioCosteo;
/*  158:     */   @EJB
/*  159:     */   private transient ServicioFacturaClienteSRIXML servicioFacturaClienteSRIXML;
/*  160:     */   @EJB
/*  161:     */   private transient ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  162:     */   @EJB
/*  163:     */   private transient ServicioEmpresa servicioEmpresa;
/*  164:     */   @EJB
/*  165:     */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  166:     */   @EJB
/*  167:     */   private transient ServicioSucursal servicioSucursal;
/*  168:     */   @EJB
/*  169:     */   private transient ServicioOrganizacion servicioOrganizacion;
/*  170:     */   @EJB
/*  171:     */   private transient ComprobanteElectronicoPendienteSRIDao comprobanteElectronicoPendienteSRIDao;
/*  172:     */   @EJB
/*  173:     */   private transient ServicioContratoVenta servicioContratoVenta;
/*  174:     */   @EJB
/*  175:     */   private transient ServicioOrdenDespachoCliente servicioOrdenDespachoCliente;
/*  176:     */   @EJB
/*  177:     */   private transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  178:     */   @EJB
/*  179:     */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  180:     */   @EJB
/*  181:     */   private transient ServicioGenerico<PreDevolucionCliente> servicioPreDevolucionCliente;
/*  182:     */   @EJB
/*  183:     */   private transient ServicioMovimientoInventario servicioMovimientoInventario;
/*  184:     */   @EJB
/*  185:     */   private transient ServicioRecepcionDevolucionTransportista servicioRecepcionDevolucionTransportista;
/*  186:     */   @EJB
/*  187:     */   private transient ServicioUsuario servicioUsuario;
/*  188:     */   @EJB
/*  189:     */   private transient ServicioDespachoCliente servicioDespachoCliente;
/*  190:     */   @EJB
/*  191:     */   private transient ServicioListaPrecios servicioListaPrecios;
/*  192:     */   @EJB
/*  193:     */   private transient ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  194:     */   @EJB
/*  195:     */   private transient ServicioAnuladoSRI servicioAnuladoSRI;
/*  196:     */   
/*  197:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  198:     */   public void guardar(FacturaCliente notaCreditoCliente)
/*  199:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  200:     */   {
/*  201: 209 */     guardar(notaCreditoCliente, false);
/*  202:     */   }
/*  203:     */   
/*  204:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  205:     */   public void guardar(FacturaCliente notaCreditoCliente, boolean indicadorAprobar)
/*  206:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  207:     */   {
/*  208:     */     try
/*  209:     */     {
/*  210: 217 */       int idNotaCrediro = notaCreditoCliente.getId();
/*  211:     */       
/*  212: 219 */       boolean indicadorRequiereAprobacion = ParametrosSistema.getNotaCreditoFinancieraRequiereAprobacion(notaCreditoCliente.getIdOrganizacion()).booleanValue();
/*  213: 221 */       if ((indicadorRequiereAprobacion) && (!indicadorAprobar) && (!Estado.APROBADO_PARCIAL.equals(notaCreditoCliente.getEstado()))) {
/*  214: 222 */         notaCreditoCliente.setEstado(Estado.ELABORADO);
/*  215: 223 */       } else if ((indicadorRequiereAprobacion) && (indicadorAprobar)) {
/*  216: 224 */         notaCreditoCliente.setEstado(Estado.PROCESADO);
/*  217:     */       }
/*  218: 227 */       FacturaCliente facturaCliente = null;
/*  219:     */       int tipoEmision;
/*  220: 230 */       if (((!indicadorRequiereAprobacion) || (indicadorAprobar)) && (notaCreditoCliente.getDocumento() != null) && 
/*  221: 231 */         (notaCreditoCliente.getDocumento().isIndicadorDocumentoElectronico()))
/*  222:     */       {
/*  223: 233 */         if (notaCreditoCliente.getFacturaClienteSRI() == null)
/*  224:     */         {
/*  225: 234 */           notaCreditoCliente.setFacturaClienteSRI(new FacturaClienteSRI());
/*  226: 235 */           notaCreditoCliente.getFacturaClienteSRI().setFacturaCliente(notaCreditoCliente);
/*  227: 237 */           if ((facturaCliente != null) && (facturaCliente.getFacturaClienteSRI().getCodigoFormaPagoSRI() != null)) {
/*  228: 239 */             notaCreditoCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(facturaCliente.getFacturaClienteSRI().getCodigoFormaPagoSRI());
/*  229:     */           }
/*  230: 241 */           notaCreditoCliente.getFacturaClienteSRI().setEstado(notaCreditoCliente.getEstado());
/*  231: 242 */           notaCreditoCliente.getFacturaClienteSRI().setIdOrganizacion(notaCreditoCliente.getIdOrganizacion());
/*  232: 243 */           notaCreditoCliente.getFacturaClienteSRI().setIdSucursal(notaCreditoCliente.getSucursal().getId());
/*  233: 244 */           asignarCodigoFormaPagoSRI(notaCreditoCliente.getIdOrganizacion(), notaCreditoCliente.getFacturaClienteSRI(), notaCreditoCliente
/*  234: 245 */             .getEmpresa());
/*  235:     */         }
/*  236: 247 */         int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(notaCreditoCliente.getIdOrganizacion()).booleanValue() ? 2 : 1;
/*  237: 248 */         notaCreditoCliente.getFacturaClienteSRI().setAmbiente(ambiente);
/*  238: 249 */         tipoEmision = 1;
/*  239: 250 */         notaCreditoCliente.getFacturaClienteSRI().setIndicadorDocumentoElectronico(true);
/*  240: 251 */         notaCreditoCliente.getFacturaClienteSRI().setEmail(notaCreditoCliente.getEmail());
/*  241:     */         
/*  242:     */ 
/*  243: 254 */         notaCreditoCliente.getFacturaClienteSRI().setTipoEmision(tipoEmision);
/*  244: 256 */         if (notaCreditoCliente.getSucursal() != null)
/*  245:     */         {
/*  246: 257 */           Sucursal sucursal = this.servicioSucursal.cargarDetalle(notaCreditoCliente.getSucursal().getId());
/*  247: 258 */           notaCreditoCliente.getFacturaClienteSRI().setDireccionSucursal(sucursal.getUbicacion().getDireccionCompleta());
/*  248:     */         }
/*  249: 260 */         if (notaCreditoCliente.getIdOrganizacion() != 0)
/*  250:     */         {
/*  251: 261 */           String dirMatriz = "";
/*  252:     */           try
/*  253:     */           {
/*  254: 263 */             dirMatriz = this.servicioOrganizacion.obtenerDireccionMatriz(notaCreditoCliente.getIdOrganizacion());
/*  255:     */           }
/*  256:     */           catch (Exception e)
/*  257:     */           {
/*  258: 265 */             dirMatriz = "N/A";
/*  259:     */           }
/*  260: 267 */           notaCreditoCliente.getFacturaClienteSRI().setDireccionMatriz(dirMatriz);
/*  261:     */         }
/*  262:     */       }
/*  263: 272 */       if (notaCreditoCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() != null) {
/*  264: 273 */         this.servicioFacturaCliente.cargarAutorizacionAutoImpresor(notaCreditoCliente);
/*  265:     */       }
/*  266: 275 */       this.servicioVerificadorInventario.verificarTotalDetalle(notaCreditoCliente.getListaDetalleFacturaCliente());
/*  267: 276 */       validar(notaCreditoCliente);
/*  268: 277 */       if (((!indicadorRequiereAprobacion) || (indicadorAprobar)) && (notaCreditoCliente.getDocumento().isIndicadorDocumentoTributario())) {
/*  269: 279 */         this.facturaClienteDao.actualizar(notaCreditoCliente.getFacturaClientePadre());
/*  270:     */       }
/*  271: 282 */       this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(notaCreditoCliente);
/*  272: 285 */       if ((notaCreditoCliente.getFacturaClientePadre() != null) && (notaCreditoCliente.getFacturaClienteSRI() != null))
/*  273:     */       {
/*  274: 286 */         Map<String, String> filtros = new HashMap();
/*  275: 287 */         filtros.put("idOrganizacion", "" + notaCreditoCliente.getIdOrganizacion());
/*  276: 288 */         filtros.put("idFacturaCliente", "" + notaCreditoCliente.getFacturaClientePadre().getIdFacturaCliente());
/*  277: 289 */         facturaCliente = this.servicioFacturaCliente.buscarFacturaCliente(filtros);
/*  278: 290 */         notaCreditoCliente.setAgenteComercial(facturaCliente.getAgenteComercial());
/*  279: 291 */         notaCreditoCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(facturaCliente.getFacturaClienteSRI().getCodigoFormaPagoSRI());
/*  280:     */       }
/*  281: 294 */       if (notaCreditoCliente.getDocumento().isIndicadorDocumentoTributario())
/*  282:     */       {
/*  283: 295 */         PuntoDeVenta puntoDeVenta = this.servicioFacturaCliente.cargarPuntoVenta(notaCreditoCliente);
/*  284: 296 */         this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(notaCreditoCliente, puntoDeVenta);
/*  285:     */       }
/*  286: 298 */       this.servicioFacturaCliente.totalizar(notaCreditoCliente);
/*  287: 299 */       if (idNotaCrediro == 0) {
/*  288: 301 */         if (notaCreditoCliente.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE) {
/*  289: 302 */           actualizarInventarioProducto(notaCreditoCliente);
/*  290:     */         }
/*  291:     */       }
/*  292: 306 */       Map<String, AuxDescuentoLineaNC> hmAuxDescuentoLineaNC = new HashMap();
/*  293: 307 */       for (DetalleFacturaCliente dfc : notaCreditoCliente.getListaDetalleFacturaCliente()) {
/*  294: 308 */         if ((!dfc.isEliminado()) && (
/*  295: 309 */           (dfc.getPrecio().compareTo(BigDecimal.ZERO) != 0) || (dfc.getCantidad().compareTo(BigDecimal.ZERO) != 0)))
/*  296:     */         {
/*  297: 311 */           for (ImpuestoProductoFacturaCliente impuestoProductoFacturaCliente : dfc.getListaImpuestoProductoFacturaCliente()) {
/*  298: 312 */             this.impuestoProductoFacturaClienteDao.guardar(impuestoProductoFacturaCliente);
/*  299:     */           }
/*  300: 314 */           if (dfc.getInventarioProducto() != null) {
/*  301: 315 */             this.servicioInventarioProducto.guardar(dfc.getInventarioProducto());
/*  302:     */           }
/*  303: 317 */           this.detalleFacturaClienteDao.guardar(dfc);
/*  304: 320 */           if (((!indicadorRequiereAprobacion) || (indicadorAprobar)) && 
/*  305: 321 */             (notaCreditoCliente.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_CREDITO_CLIENTE) && 
/*  306: 322 */             (dfc.getDetalleFacturaClientePadre() != null)) {
/*  307: 323 */             if ((idNotaCrediro == 0) || (indicadorAprobar))
/*  308:     */             {
/*  309: 325 */               int idDFCPadre = dfc.getDetalleFacturaClientePadre().getId();
/*  310: 326 */               BigDecimal descuentoLineaNC = dfc.getDetalleFacturaClientePadre().getDescuentoLineaNC().add(dfc.getPrecio());
/*  311: 327 */               hmAuxDescuentoLineaNC.put(String.valueOf(dfc.getId()), new AuxDescuentoLineaNC(idDFCPadre, descuentoLineaNC));
/*  312:     */               
/*  313: 329 */               dfc.getDetalleFacturaClientePadre()
/*  314: 330 */                 .setDescuentoLineaNC(dfc.getDetalleFacturaClientePadre().getDescuentoLineaNC().add(dfc.getPrecio()));
/*  315:     */             }
/*  316:     */             else
/*  317:     */             {
/*  318: 334 */               int idDFCPadre = dfc.getDetalleFacturaClientePadre().getId();
/*  319:     */               
/*  320: 336 */               BigDecimal descuentoLineaNC = dfc.getDetalleFacturaClientePadre().getDescuentoLineaNC().subtract(dfc.getPrecio()).add(dfc.getPrecio());
/*  321: 337 */               hmAuxDescuentoLineaNC.put(String.valueOf(dfc.getId()), new AuxDescuentoLineaNC(idDFCPadre, descuentoLineaNC));
/*  322:     */               
/*  323: 339 */               dfc.getDetalleFacturaClientePadre().setDescuentoLineaNC(descuentoLineaNC);
/*  324:     */             }
/*  325:     */           }
/*  326:     */         }
/*  327:     */       }
/*  328: 348 */       if (notaCreditoCliente.getFacturaClienteSRI() != null)
/*  329:     */       {
/*  330: 349 */         if ((!indicadorRequiereAprobacion) || (indicadorAprobar)) {
/*  331: 350 */           notaCreditoCliente.getFacturaClienteSRI().setEstado(Estado.PROCESADO);
/*  332:     */         } else {
/*  333: 352 */           notaCreditoCliente.getFacturaClienteSRI().setEstado(Estado.ELABORADO);
/*  334:     */         }
/*  335: 354 */         this.facturaClienteSRIDao.guardar(notaCreditoCliente.getFacturaClienteSRI());
/*  336:     */       }
/*  337: 357 */       this.facturaClienteDao.guardar(notaCreditoCliente);
/*  338: 358 */       if (!indicadorAprobar) {
/*  339: 359 */         if (notaCreditoCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() == null) {
/*  340: 360 */           this.servicioSecuencia.actualizarSecuencia(notaCreditoCliente.getDocumento().getSecuencia(), notaCreditoCliente.getNumero());
/*  341:     */         } else {
/*  342: 362 */           this.servicioAutorizacionAutoimpresorSRI.actualizaSecuencia(notaCreditoCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI());
/*  343:     */         }
/*  344:     */       }
/*  345: 366 */       if (idNotaCrediro == 0)
/*  346:     */       {
/*  347: 368 */         actualizarCantidadPorDevolver(notaCreditoCliente);
/*  348:     */         
/*  349: 370 */         this.servicioVerificadorInventario.actualizarSaldoProducto(notaCreditoCliente, false);
/*  350: 372 */         if ((notaCreditoCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.DEVOLUCION_CLIENTE)) && 
/*  351: 373 */           (ParametrosSistema.getGeneracionDeCostosAutomatica(notaCreditoCliente.getIdOrganizacion()).booleanValue())) {
/*  352: 375 */           this.servicioCosteo.generarCostos(notaCreditoCliente, ParametrosSistema.isCosteoPorBodega(notaCreditoCliente.getIdOrganizacion()).booleanValue());
/*  353:     */         }
/*  354:     */       }
/*  355: 381 */       Iterator it = hmAuxDescuentoLineaNC.keySet().iterator();
/*  356: 382 */       while (it.hasNext())
/*  357:     */       {
/*  358: 383 */         String key = (String)it.next();
/*  359: 384 */         this.detalleFacturaClienteDao.actualizarDescuentoLineaNC(((AuxDescuentoLineaNC)hmAuxDescuentoLineaNC.get(key)).getIdDFCPadre(), 
/*  360: 385 */           ((AuxDescuentoLineaNC)hmAuxDescuentoLineaNC.get(key)).getDescuentoLineaNC());
/*  361:     */       }
/*  362: 388 */       if (((!indicadorRequiereAprobacion) || (indicadorAprobar)) && (notaCreditoCliente.getAnticipoCliente() == null) && 
/*  363: 389 */         (notaCreditoCliente.getFacturaClientePadre() != null))
/*  364:     */       {
/*  365: 390 */         generarAnticipo(notaCreditoCliente);
/*  366: 391 */         liquidaAnticipo(notaCreditoCliente);
/*  367:     */       }
/*  368: 394 */       if ((notaCreditoCliente.getEmail() != null) && (!notaCreditoCliente.getEmail().isEmpty()))
/*  369:     */       {
/*  370: 397 */         Empresa empresa = null;
/*  371: 398 */         if (notaCreditoCliente.getSubempresa() != null) {
/*  372: 399 */           empresa = notaCreditoCliente.getSubempresa().getEmpresa();
/*  373:     */         } else {
/*  374: 401 */           empresa = notaCreditoCliente.getEmpresa();
/*  375:     */         }
/*  376: 403 */         this.servicioEmpresa.actualizarMails(empresa, notaCreditoCliente.getEmail(), DocumentoBase.NOTA_CREDITO_CLIENTE);
/*  377:     */       }
/*  378: 406 */       if ((!indicadorRequiereAprobacion) || (indicadorAprobar)) {
/*  379: 409 */         this.servicioContratoVenta.generarDevengadosNotaCredito(notaCreditoCliente);
/*  380:     */       }
/*  381: 412 */       if (((!indicadorRequiereAprobacion) || (indicadorAprobar)) && (!notaCreditoCliente.isIndicadorSaldoInicial()) && 
/*  382: 413 */         (notaCreditoCliente.getDocumento().isIndicadorDocumentoTributario()) && 
/*  383: 414 */         (notaCreditoCliente.getDocumento().isIndicadorDocumentoElectronico()))
/*  384:     */       {
/*  385: 415 */         notaCreditoCliente = this.servicioFacturaClienteSRIXML.generarClaveAcceso(null, notaCreditoCliente, true);
/*  386: 416 */         boolean indicadorEnviarEmail = ParametrosSistema.isComprobantesElectronicosEnviarEmailGuardar(notaCreditoCliente.getIdOrganizacion()).booleanValue();
/*  387: 417 */         if (indicadorEnviarEmail) {
/*  388: 419 */           if (notaCreditoCliente.getFacturaClienteSRI().getAmbiente() == 2) {
/*  389:     */             try
/*  390:     */             {
/*  391: 421 */               this.servicioFacturaCliente.enviarMail(notaCreditoCliente, notaCreditoCliente.getDocumentoElectronico(), null);
/*  392:     */             }
/*  393:     */             catch (Exception e)
/*  394:     */             {
/*  395: 423 */               e.printStackTrace();
/*  396:     */               
/*  397: 425 */               System.out.println("Error en la generacion del reporte y enviar por mail al cliente (Facturacion electronica)" + e
/*  398: 426 */                 .getMessage());
/*  399:     */             }
/*  400:     */           }
/*  401:     */         }
/*  402:     */       }
/*  403: 432 */       if (notaCreditoCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.DEVOLUCION_CLIENTE)) {
/*  404: 434 */         this.servicioOrdenDespachoCliente.generarExplosionRapida(notaCreditoCliente);
/*  405:     */       }
/*  406:     */     }
/*  407:     */     catch (ExcepcionAS2Ventas e)
/*  408:     */     {
/*  409: 439 */       this.context.setRollbackOnly();
/*  410: 440 */       throw e;
/*  411:     */     }
/*  412:     */     catch (ExcepcionAS2Inventario e)
/*  413:     */     {
/*  414: 442 */       this.context.setRollbackOnly();
/*  415: 443 */       throw e;
/*  416:     */     }
/*  417:     */     catch (ExcepcionAS2Financiero e)
/*  418:     */     {
/*  419: 445 */       this.context.setRollbackOnly();
/*  420: 446 */       throw e;
/*  421:     */     }
/*  422:     */     catch (AS2Exception e)
/*  423:     */     {
/*  424: 448 */       this.context.setRollbackOnly();
/*  425: 449 */       throw e;
/*  426:     */     }
/*  427:     */     catch (ExcepcionAS2 e)
/*  428:     */     {
/*  429: 451 */       this.context.setRollbackOnly();
/*  430: 452 */       throw e;
/*  431:     */     }
/*  432:     */     catch (Exception e)
/*  433:     */     {
/*  434: 454 */       e.printStackTrace();
/*  435: 455 */       this.context.setRollbackOnly();
/*  436: 456 */       e.printStackTrace();
/*  437: 457 */       throw new ExcepcionAS2(e);
/*  438:     */     }
/*  439:     */   }
/*  440:     */   
/*  441:     */   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
/*  442:     */   public List<MovimientoInventario> costearTransformacionDevolucionCliente(FacturaCliente devolucionCliente)
/*  443:     */   {
/*  444: 465 */     this.facturaClienteDao.flush();
/*  445: 466 */     this.servicioInventarioProducto.flush();
/*  446:     */     
/*  447: 468 */     Map<String, String> filtros = new HashMap();
/*  448: 469 */     filtros.put("devolucionClienteTransformacion.idFacturaCliente", "" + devolucionCliente.getIdFacturaCliente());
/*  449: 470 */     filtros.put("estado", "!=" + Estado.ANULADO.toString());
/*  450: 471 */     List<MovimientoInventario> listaTransformaciones = this.servicioMovimientoInventario.obtenerListaCombo("documento.operacion", true, filtros);
/*  451: 472 */     for (MovimientoInventario transformacion : listaTransformaciones) {
/*  452: 473 */       this.servicioCosteo.generarCostos(transformacion, ParametrosSistema.isCosteoPorBodega(devolucionCliente.getIdOrganizacion()).booleanValue(), null);
/*  453:     */     }
/*  454: 475 */     return listaTransformaciones;
/*  455:     */   }
/*  456:     */   
/*  457:     */   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
/*  458:     */   public List<MovimientoInventario> contabilizarTransformacionDevolucionCliente(FacturaCliente devolucionCliente)
/*  459:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  460:     */   {
/*  461: 482 */     this.facturaClienteDao.flush();
/*  462: 483 */     this.servicioInventarioProducto.flush();
/*  463:     */     try
/*  464:     */     {
/*  465: 486 */       Map<String, String> filtros = new HashMap();
/*  466: 487 */       filtros.put("devolucionClienteTransformacion.idFacturaCliente", "" + devolucionCliente.getIdFacturaCliente());
/*  467: 488 */       filtros.put("estado", "!=" + Estado.ANULADO.toString());
/*  468: 489 */       filtros.put("movimientoInventarioPadre", OperacionEnum.IS_NOT_NULL.toString());
/*  469: 490 */       List<MovimientoInventario> listaTransformaciones = this.servicioMovimientoInventario.obtenerListaCombo("documento.operacion", true, filtros);
/*  470: 491 */       for (MovimientoInventario transformacion : listaTransformaciones) {
/*  471: 492 */         this.servicioMovimientoInventario.contabilizar(transformacion);
/*  472:     */       }
/*  473: 494 */       return listaTransformaciones;
/*  474:     */     }
/*  475:     */     catch (ExcepcionAS2Financiero e)
/*  476:     */     {
/*  477: 496 */       this.context.setRollbackOnly();
/*  478: 497 */       throw e;
/*  479:     */     }
/*  480:     */     catch (ExcepcionAS2 e)
/*  481:     */     {
/*  482: 499 */       this.context.setRollbackOnly();
/*  483: 500 */       throw e;
/*  484:     */     }
/*  485:     */     catch (AS2Exception e)
/*  486:     */     {
/*  487: 502 */       this.context.setRollbackOnly();
/*  488: 503 */       throw e;
/*  489:     */     }
/*  490:     */     catch (Exception e)
/*  491:     */     {
/*  492: 505 */       this.context.setRollbackOnly();
/*  493: 506 */       throw new ExcepcionAS2(e);
/*  494:     */     }
/*  495:     */   }
/*  496:     */   
/*  497:     */   public List<Object[]> getReporteFacturaCliente(int idFacturaCliente, TipoOrganizacion tipoOrganizacion, int numeroCopias, boolean indicadorDetallado, boolean indicadorImpreso)
/*  498:     */     throws ExcepcionAS2
/*  499:     */   {
/*  500: 513 */     List<Object[]> lista = this.facturaClienteDao.getReporteFacturaCliente(idFacturaCliente);
/*  501:     */     Object[] object;
/*  502: 515 */     if ((!indicadorDetallado) && (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)))
/*  503:     */     {
/*  504: 517 */       TreeMap<String, Object[]> tmFactura = new TreeMap();
/*  505: 519 */       for (Iterator localIterator = lista.iterator(); localIterator.hasNext();)
/*  506:     */       {
/*  507: 519 */         object = (Object[])localIterator.next();
/*  508: 520 */         Object[] o = (Object[])tmFactura.get((String)object[5] + "~" + object[7]);
/*  509: 521 */         if (o == null)
/*  510:     */         {
/*  511: 522 */           tmFactura.put((String)object[5] + "~" + object[7], object);
/*  512:     */         }
/*  513:     */         else
/*  514:     */         {
/*  515: 524 */           BigDecimal sumaCantidad = ((BigDecimal)o[4]).add((BigDecimal)object[4]);
/*  516: 525 */           o[4] = sumaCantidad;
/*  517: 526 */           o[50] = Integer.valueOf(Integer.parseInt(o[50].toString()) + 1);
/*  518:     */         }
/*  519:     */       }
/*  520: 529 */       lista = new ArrayList(tmFactura.values());
/*  521:     */     }
/*  522: 532 */     if (numeroCopias >= 1)
/*  523:     */     {
/*  524: 533 */       List<Object[]> listaAux = new ArrayList();
/*  525: 535 */       for (int i = 1; i <= numeroCopias; i++) {
/*  526: 536 */         for (Object[] objects : lista)
/*  527:     */         {
/*  528: 538 */           int tamanio = objects.length;
/*  529:     */           
/*  530: 540 */           Object[] a = new Object[tamanio];
/*  531: 542 */           for (int k = 0; k < tamanio; k++) {
/*  532: 543 */             if (k == 41) {
/*  533: 544 */               a[k] = Integer.valueOf(i + (indicadorImpreso ? 2 : 0));
/*  534:     */             } else {
/*  535: 546 */               a[k] = objects[k];
/*  536:     */             }
/*  537:     */           }
/*  538: 550 */           listaAux.add(a);
/*  539:     */         }
/*  540:     */       }
/*  541: 553 */       lista = listaAux;
/*  542:     */     }
/*  543: 555 */     return lista;
/*  544:     */   }
/*  545:     */   
/*  546:     */   private void liquidaAnticipo(FacturaCliente notaCreditoCliente)
/*  547:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  548:     */   {
/*  549: 560 */     LiquidacionAnticipoCliente liquidacionAnticipoCliente = new LiquidacionAnticipoCliente();
/*  550: 561 */     liquidacionAnticipoCliente.setIdOrganizacion(notaCreditoCliente.getIdOrganizacion());
/*  551: 562 */     liquidacionAnticipoCliente.setIdSucursal(notaCreditoCliente.getSucursal().getId());
/*  552: 563 */     liquidacionAnticipoCliente.setFecha(notaCreditoCliente.getFecha());
/*  553: 564 */     liquidacionAnticipoCliente.setEstado(Estado.ELABORADO);
/*  554: 565 */     liquidacionAnticipoCliente.setAnticipoCliente(notaCreditoCliente.getAnticipoCliente());
/*  555:     */     
/*  556: 567 */     liquidacionAnticipoCliente.setDocumento(notaCreditoCliente.getDocumento());
/*  557: 568 */     liquidacionAnticipoCliente.setNumero(notaCreditoCliente.getNumero());
/*  558:     */     
/*  559: 570 */     liquidacionAnticipoCliente = this.servicioLiquidacionAnticipoCliente.cargarFacturasPendientes(liquidacionAnticipoCliente, notaCreditoCliente
/*  560: 571 */       .getFacturaClientePadre().getIdFacturaCliente());
/*  561: 572 */     BigDecimal valorLiquidacion = BigDecimal.ZERO;
/*  562: 573 */     for (DetalleLiquidacionAnticipoCliente detalleLiquidacionAnticipoCliente : liquidacionAnticipoCliente
/*  563: 574 */       .getListaDetalleLiquidacionAnticipoCliente()) {
/*  564: 575 */       valorLiquidacion = valorLiquidacion.add(detalleLiquidacionAnticipoCliente.getValor());
/*  565:     */     }
/*  566: 577 */     if (valorLiquidacion.compareTo(BigDecimal.ZERO) != 0) {
/*  567: 578 */       this.servicioLiquidacionAnticipoCliente.guardar(liquidacionAnticipoCliente);
/*  568:     */     }
/*  569:     */   }
/*  570:     */   
/*  571:     */   private void validar(FacturaCliente facturaCliente)
/*  572:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/*  573:     */   {
/*  574:     */     BigDecimal valorNotaCreditoAcumulado;
/*  575: 585 */     if (facturaCliente.getDocumento().isIndicadorDocumentoTributario())
/*  576:     */     {
/*  577: 586 */       if (facturaCliente.getFecha().before(facturaCliente.getFacturaClientePadre().getFecha())) {
/*  578: 588 */         throw new ExcepcionAS2Ventas("msg_error_fecha_antes_del_proceso", " " + facturaCliente.getFacturaClientePadre().getNumero());
/*  579:     */       }
/*  580: 591 */       this.servicioPeriodo.buscarPorFecha(facturaCliente.getFecha(), facturaCliente.getIdOrganizacion(), facturaCliente
/*  581: 592 */         .getDocumento().getDocumentoBase());
/*  582:     */       
/*  583: 594 */       valorNotaCreditoAcumulado = this.facturaClienteDao.valorNotaCreditoFactura(facturaCliente);
/*  584: 595 */       valorNotaCreditoAcumulado = valorNotaCreditoAcumulado == null ? BigDecimal.ZERO : valorNotaCreditoAcumulado;
/*  585:     */       
/*  586:     */ 
/*  587:     */ 
/*  588: 599 */       BigDecimal valorNetoOriginal = facturaCliente.getFacturaClientePadre().getTotal().subtract(facturaCliente.getFacturaClientePadre().getDescuento()).subtract(valorNotaCreditoAcumulado);
/*  589: 600 */       BigDecimal valorNetoDevolucion = facturaCliente.getTotal().subtract(facturaCliente.getDescuento());
/*  590:     */       String facturaPadre;
/*  591: 602 */       if (valorNetoDevolucion.subtract(valorNetoOriginal).compareTo(new BigDecimal(0.01D)) > 0)
/*  592:     */       {
/*  593: 603 */         facturaPadre = "";
/*  594: 604 */         if (facturaCliente.getFacturaClientePadre() != null) {
/*  595: 605 */           facturaPadre = "\n De la factura No.:" + facturaCliente.getFacturaClientePadre().getNumero();
/*  596:     */         }
/*  597: 606 */         throw new ExcepcionAS2Ventas("msg_error_nota_credito_001", " " + facturaCliente.getNumero() + facturaPadre);
/*  598:     */       }
/*  599: 609 */       facturaCliente.getFacturaClientePadre().setValorDevuelto(FuncionesUtiles.redondearBigDecimal(valorNotaCreditoAcumulado));
/*  600: 611 */       if (this.servicioFacturaCliente.verificaExistenciaNumero(facturaCliente) > 0L) {
/*  601: 612 */         throw new ExcepcionAS2Ventas("msg_error_numero_duplicado", " " + facturaCliente.getNumero());
/*  602:     */       }
/*  603: 615 */       if ((facturaCliente.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE) && (facturaCliente.getId() == 0)) {
/*  604: 617 */         for (DetalleFacturaCliente detalleFacturaCliente : facturaCliente.getListaDetalleFacturaCliente()) {
/*  605: 618 */           if (!detalleFacturaCliente.isEliminado())
/*  606:     */           {
/*  607: 619 */             DetalleDespachoCliente ddc = null;
/*  608: 620 */             if ((detalleFacturaCliente.getDetalleFacturaClientePadre() != null) && 
/*  609: 621 */               (detalleFacturaCliente.getDetalleFacturaClientePadre().getDetalleDespachoCliente() != null)) {
/*  610: 622 */               ddc = detalleFacturaCliente.getDetalleFacturaClientePadre().getDetalleDespachoCliente();
/*  611:     */             } else {
/*  612: 624 */               ddc = detalleFacturaCliente.getDetalleDespachoClienteNoFacturado();
/*  613:     */             }
/*  614: 625 */             if ((ddc != null) && (ddc.getCantidadPorDevolver().compareTo(detalleFacturaCliente.getCantidad()) == -1) && 
/*  615: 626 */               (facturaCliente.getCodigoMovil().isEmpty())) {
/*  616: 627 */               throw new ExcepcionAS2Financiero("msg_error_devolucion_cantidad");
/*  617:     */             }
/*  618:     */           }
/*  619:     */         }
/*  620:     */       }
/*  621: 633 */       if (facturaCliente.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_CREDITO_CLIENTE)
/*  622:     */       {
/*  623: 634 */         BigDecimal totalFactura = facturaCliente.getFacturaClientePadre().getTotalFactura();
/*  624: 635 */         BigDecimal totalFacturaADevolver = facturaCliente.getTotalFactura();
/*  625: 636 */         if (totalFacturaADevolver.compareTo(totalFactura) == 1) {
/*  626: 637 */           throw new ExcepcionAS2Financiero("msg_error_nota_credito_001");
/*  627:     */         }
/*  628:     */       }
/*  629: 641 */       validarPrecio(facturaCliente);
/*  630:     */     }
/*  631:     */     else
/*  632:     */     {
/*  633: 643 */       if ((facturaCliente.getDespachoCliente() != null) && 
/*  634: 644 */         (!FuncionesUtiles.compararFechaAnteriorOIgual(facturaCliente.getDespachoCliente().getFecha(), facturaCliente.getFecha()))) {
/*  635: 645 */         throw new ExcepcionAS2Ventas("msg_error_fecha_devolucion");
/*  636:     */       }
/*  637: 649 */       if (facturaCliente.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE) {
/*  638: 650 */         for (DetalleFacturaCliente detalleFacturaCliente : facturaCliente.getListaDetalleFacturaCliente()) {
/*  639: 651 */           if (!detalleFacturaCliente.isEliminado())
/*  640:     */           {
/*  641: 652 */             DetalleDespachoCliente ddc = null;
/*  642: 653 */             if ((detalleFacturaCliente.getDetalleFacturaClientePadre() != null) && 
/*  643: 654 */               (detalleFacturaCliente.getDetalleFacturaClientePadre().getDetalleDespachoCliente() != null)) {
/*  644: 655 */               ddc = detalleFacturaCliente.getDetalleFacturaClientePadre().getDetalleDespachoCliente();
/*  645: 656 */             } else if (detalleFacturaCliente.getDetalleDespachoCliente() != null) {
/*  646: 657 */               ddc = detalleFacturaCliente.getDetalleDespachoCliente();
/*  647:     */             } else {
/*  648: 659 */               ddc = detalleFacturaCliente.getDetalleDespachoClienteNoFacturado();
/*  649:     */             }
/*  650: 660 */             if ((ddc != null) && (ddc.getCantidadPorDevolver().compareTo(detalleFacturaCliente.getCantidad()) == -1)) {
/*  651: 661 */               throw new ExcepcionAS2Financiero("msg_error_devolucion_cantidad");
/*  652:     */             }
/*  653:     */           }
/*  654:     */         }
/*  655:     */       }
/*  656:     */     }
/*  657:     */   }
/*  658:     */   
/*  659:     */   private void validarPrecio(FacturaCliente notaCreditoCliente)
/*  660:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/*  661:     */   {
/*  662: 670 */     BigDecimal valorNotaCreditoCliente = BigDecimal.ZERO;
/*  663: 671 */     BigDecimal valorPrecioNotaCreditoCliente = BigDecimal.ZERO;
/*  664: 672 */     BigDecimal valorTotalFacturaPadre = BigDecimal.ZERO;
/*  665:     */     
/*  666: 674 */     List<FacturaCliente> listaNotaCreditoCliente = this.servicioFacturaCliente.getListaNotaCreditoCliente(notaCreditoCliente.getFacturaClientePadre(), notaCreditoCliente);
/*  667: 677 */     if ((listaNotaCreditoCliente != null) && (!listaNotaCreditoCliente.isEmpty())) {
/*  668: 678 */       for (FacturaCliente verificarNotaCreditoCliente : listaNotaCreditoCliente) {
/*  669: 679 */         if (verificarNotaCreditoCliente.getEstado() != Estado.ANULADO) {
/*  670: 680 */           valorNotaCreditoCliente = valorNotaCreditoCliente.add(verificarNotaCreditoCliente.getTotalFactura());
/*  671:     */         }
/*  672:     */       }
/*  673:     */     }
/*  674: 684 */     valorPrecioNotaCreditoCliente = valorPrecioNotaCreditoCliente.add(notaCreditoCliente.getTotalFactura());
/*  675: 685 */     valorPrecioNotaCreditoCliente = valorPrecioNotaCreditoCliente.add(valorNotaCreditoCliente);
/*  676: 686 */     valorTotalFacturaPadre = notaCreditoCliente.getFacturaClientePadre().getTotalFactura();
/*  677: 688 */     if (valorPrecioNotaCreditoCliente.subtract(valorTotalFacturaPadre).compareTo(new BigDecimal(0.01D)) > 0) {
/*  678: 690 */       throw new ExcepcionAS2Ventas("msg_error_nota_credito_002", "Notas de Crédito: " + valorPrecioNotaCreditoCliente.abs().toString() + " > " + "Factura: " + valorTotalFacturaPadre.abs().toString());
/*  679:     */     }
/*  680:     */   }
/*  681:     */   
/*  682:     */   public List<FacturaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  683:     */   {
/*  684: 697 */     return this.servicioFacturaCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  685:     */   }
/*  686:     */   
/*  687:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  688:     */   public void anular(FacturaCliente notaCreditoCliente)
/*  689:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2
/*  690:     */   {
/*  691:     */     try
/*  692:     */     {
/*  693: 704 */       if (notaCreditoCliente.isIndicadorGeneroTransformacion()) {
/*  694: 710 */         throw new ExcepcionAS2("msg_error_devolucion_genero_transformacion", "");
/*  695:     */       }
/*  696: 712 */       MotivoAnulacion motivoAnulacion = notaCreditoCliente.getMotivoAnulacion();
/*  697: 714 */       if (motivoAnulacion == null) {}
/*  698: 719 */       notaCreditoCliente = this.servicioFacturaCliente.cargarDetalle(notaCreditoCliente.getId());
/*  699:     */       
/*  700: 721 */       esEditable(notaCreditoCliente);
/*  701: 724 */       if ((notaCreditoCliente.getDocumento().isIndicadorDocumentoTributario()) && (notaCreditoCliente.getFacturaClienteSRI() != null))
/*  702:     */       {
/*  703: 725 */         FacturaClienteSRI facturaClienteSRI = this.servicioFacturaClienteSRI.buscarFacturaClienteSRIPorFacturaCliente(notaCreditoCliente);
/*  704: 726 */         if ((!notaCreditoCliente.getDocumento().isIndicadorDocumentoElectronico()) || 
/*  705: 727 */           (!facturaClienteSRI.getAutorizacion().equals("0000000000"))) {
/*  706: 728 */           this.servicioAnuladoSRI.anularFacturaCliente(facturaClienteSRI);
/*  707:     */         }
/*  708:     */       }
/*  709: 732 */       actualizarEstado(notaCreditoCliente.getId(), Estado.ANULADO, null);
/*  710: 734 */       if (notaCreditoCliente.getFacturaClienteSRI() != null) {
/*  711: 735 */         this.facturaClienteSRIDao.eliminarFacturaClienteSRI(Integer.valueOf(notaCreditoCliente.getFacturaClienteSRI().getId()));
/*  712:     */       }
/*  713: 739 */       if (notaCreditoCliente.getFacturaClientePadre() != null)
/*  714:     */       {
/*  715: 740 */         BigDecimal valorNotaCreditoAcumulado = this.facturaClienteDao.valorNotaCreditoFactura(notaCreditoCliente);
/*  716: 741 */         valorNotaCreditoAcumulado = valorNotaCreditoAcumulado == null ? BigDecimal.ZERO : valorNotaCreditoAcumulado;
/*  717: 742 */         this.facturaClienteDao.actualizarValorDevuelto(notaCreditoCliente.getFacturaClientePadre().getId(), 
/*  718: 743 */           FuncionesUtiles.redondearBigDecimal(valorNotaCreditoAcumulado));
/*  719:     */         
/*  720: 745 */         notaCreditoCliente.setAnticipoCliente(this.servicioAnticipoCliente
/*  721: 746 */           .cargarDetalle(notaCreditoCliente.getAnticipoCliente().getIdAnticipoCliente()));
/*  722:     */       }
/*  723: 751 */       if (notaCreditoCliente.getAnticipoCliente() != null)
/*  724:     */       {
/*  725: 753 */         listaLiquidacionAnticipoCliente = this.servicioLiquidacionAnticipoCliente.getLiquidacionAnticipoClientePorAnticipoCliente(Integer.valueOf(notaCreditoCliente.getAnticipoCliente().getId()));
/*  726: 755 */         for (LiquidacionAnticipoCliente lac : listaLiquidacionAnticipoCliente) {
/*  727: 756 */           if ((lac != null) && (lac.getEstado() != Estado.ANULADO))
/*  728:     */           {
/*  729: 757 */             lac = this.servicioLiquidacionAnticipoCliente.cargarDetalle(lac.getId());
/*  730: 758 */             this.servicioLiquidacionAnticipoCliente.anular(lac);
/*  731:     */           }
/*  732:     */         }
/*  733: 765 */         if (notaCreditoCliente.getAnticipoCliente().getEstado() != Estado.ANULADO) {
/*  734: 766 */           this.servicioAnticipoCliente.anularAnticipoCliente(notaCreditoCliente.getAnticipoCliente(), false, false);
/*  735:     */         }
/*  736:     */       }
/*  737: 774 */       if (notaCreditoCliente.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE)
/*  738:     */       {
/*  739: 776 */         for (DetalleFacturaCliente detalleFacturaCliente : this.facturaClienteDao
/*  740: 777 */           .cargarDetalleNotaCreditoDevolucion(notaCreditoCliente.getIdFacturaCliente()))
/*  741:     */         {
/*  742: 778 */           DetalleDespachoCliente ddc = null;
/*  743: 779 */           if ((detalleFacturaCliente.getDetalleFacturaClientePadre() != null) && 
/*  744: 780 */             (detalleFacturaCliente.getDetalleFacturaClientePadre().getDetalleDespachoCliente() != null))
/*  745:     */           {
/*  746: 781 */             ddc = detalleFacturaCliente.getDetalleFacturaClientePadre().getDetalleDespachoCliente();
/*  747:     */             
/*  748: 783 */             detalleFacturaCliente.setDetalleFacturaClientePadre(null);
/*  749: 784 */             this.detalleFacturaClienteDao.guardar(detalleFacturaCliente);
/*  750:     */           }
/*  751:     */           else
/*  752:     */           {
/*  753: 786 */             ddc = detalleFacturaCliente.getDetalleDespachoClienteNoFacturado();
/*  754: 787 */             detalleFacturaCliente.setDetalleDespachoClienteNoFacturado(null);
/*  755: 788 */             this.detalleFacturaClienteDao.guardar(detalleFacturaCliente);
/*  756:     */           }
/*  757: 790 */           if (ddc != null)
/*  758:     */           {
/*  759: 792 */             BigDecimal cantidadDevuelta = ddc.getCantidadDevuelta().subtract(detalleFacturaCliente.getCantidad());
/*  760: 793 */             HashMap<String, Object> campos = new HashMap();
/*  761: 794 */             campos.put("cantidadDevuelta", cantidadDevuelta);
/*  762: 795 */             this.detalleDespachoClienteDao.actualizarAtributoEntidad(ddc, campos);
/*  763:     */           }
/*  764:     */         }
/*  765: 800 */         this.servicioVerificadorInventario.actualizarSaldoProducto(notaCreditoCliente, true);
/*  766: 802 */         if (ParametrosSistema.isRegistroReversoAnulacionInventario(notaCreditoCliente.getIdOrganizacion()).booleanValue()) {
/*  767: 805 */           this.servicioInventarioProducto.generarMovimientoInventarioInverso(notaCreditoCliente, notaCreditoCliente.getFecha());
/*  768:     */         } else {
/*  769: 808 */           this.servicioInventarioProducto.eliminaInventarioProductoPorIdDevolucionCliente(Integer.valueOf(notaCreditoCliente.getId()));
/*  770:     */         }
/*  771:     */       }
/*  772: 814 */       this.servicioContratoVenta.liberarValoresDevengados(notaCreditoCliente);
/*  773:     */       
/*  774:     */ 
/*  775: 817 */       this.comprobanteElectronicoPendienteSRIDao.eliminarComprobanteElectronicoPendienteSRI(notaCreditoCliente, null, null);
/*  776: 819 */       if (notaCreditoCliente.getDocumento().getDocumentoBase() == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/*  777: 821 */         for (DetalleFacturaCliente detalleFacturaCliente : this.facturaClienteDao
/*  778: 822 */           .cargarDetalleNotaCreditoDevolucion(notaCreditoCliente.getIdFacturaCliente())) {
/*  779: 824 */           if (detalleFacturaCliente.getDetalleFacturaClientePadre() != null)
/*  780:     */           {
/*  781: 827 */             BigDecimal descuentoLineaNC = detalleFacturaCliente.getDetalleFacturaClientePadre().getDescuentoLineaNC().subtract(detalleFacturaCliente.getPrecio());
/*  782: 828 */             HashMap<String, Object> desc = new HashMap();
/*  783: 829 */             desc.put("descuentoLineaNC", descuentoLineaNC);
/*  784: 830 */             this.detalleFacturaClienteDao.actualizarAtributoEntidad(detalleFacturaCliente.getDetalleFacturaClientePadre(), desc);
/*  785:     */           }
/*  786:     */         }
/*  787:     */       }
/*  788:     */     }
/*  789:     */     catch (ExcepcionAS2Ventas e)
/*  790:     */     {
/*  791:     */       List<LiquidacionAnticipoCliente> listaLiquidacionAnticipoCliente;
/*  792: 837 */       this.context.setRollbackOnly();
/*  793: 838 */       throw e;
/*  794:     */     }
/*  795:     */     catch (ExcepcionAS2Financiero e)
/*  796:     */     {
/*  797: 840 */       this.context.setRollbackOnly();
/*  798: 841 */       throw e;
/*  799:     */     }
/*  800:     */     catch (ExcepcionAS2Inventario e)
/*  801:     */     {
/*  802: 843 */       this.context.setRollbackOnly();
/*  803: 844 */       throw e;
/*  804:     */     }
/*  805:     */     catch (ExcepcionAS2 e)
/*  806:     */     {
/*  807: 846 */       this.context.setRollbackOnly();
/*  808: 847 */       throw e;
/*  809:     */     }
/*  810:     */     catch (Exception e)
/*  811:     */     {
/*  812: 849 */       this.context.setRollbackOnly();
/*  813: 850 */       LOG.error(e);
/*  814: 851 */       throw new ExcepcionAS2(e);
/*  815:     */     }
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void actualizarEstado(int idNotaCreditoCliente, Estado estado, MotivoAnulacion motivoAnulacion)
/*  819:     */   {
/*  820: 858 */     this.servicioFacturaCliente.actualizarEstado(idNotaCreditoCliente, estado, motivoAnulacion);
/*  821:     */   }
/*  822:     */   
/*  823:     */   public void esEditable(FacturaCliente notaCreditoCliente)
/*  824:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/*  825:     */   {
/*  826: 864 */     this.servicioPeriodo.buscarPorFecha(notaCreditoCliente.getFecha(), notaCreditoCliente.getIdOrganizacion(), notaCreditoCliente
/*  827: 865 */       .getDocumento().getDocumentoBase());
/*  828: 867 */     if ((notaCreditoCliente.getFacturaClienteSRI() != null) && (!notaCreditoCliente.getFacturaClienteSRI().isIndicadorDocumentoElectronico()) && 
/*  829: 868 */       (notaCreditoCliente.getFacturaClienteSRI().getAmbiente() == 2) && ((notaCreditoCliente.getEstado().equals(Estado.EN_ESPERA)) || 
/*  830: 869 */       (notaCreditoCliente.getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA)))) {
/*  831: 871 */       throw new ExcepcionAS2Ventas("msg_error_anular", " Documento Electrónico |" + notaCreditoCliente.getEstado());
/*  832:     */     }
/*  833: 875 */     FacturaCliente auxNotaCreditoCliente = this.servicioFacturaCliente.cargarDetalle(notaCreditoCliente.getId());
/*  834: 876 */     if (auxNotaCreditoCliente.getEstado() == Estado.ANULADO) {
/*  835: 878 */       throw new ExcepcionAS2Ventas("msg_error_anular");
/*  836:     */     }
/*  837: 882 */     if ((notaCreditoCliente.getAsiento() != null) && (notaCreditoCliente.getAsiento().getEstado() == Estado.REVISADO)) {
/*  838: 883 */       throw new ExcepcionAS2Ventas("msg_error_anular");
/*  839:     */     }
/*  840:     */   }
/*  841:     */   
/*  842:     */   public AnticipoCliente generarAnticipo(FacturaCliente notaCreditoCliente)
/*  843:     */     throws ExcepcionAS2, AS2Exception
/*  844:     */   {
/*  845: 892 */     AnticipoCliente anticipoCliente = new AnticipoCliente();
/*  846: 893 */     int idOrganizacion = notaCreditoCliente.getIdOrganizacion();
/*  847: 894 */     anticipoCliente.setIdOrganizacion(idOrganizacion);
/*  848: 895 */     anticipoCliente.setSucursal(notaCreditoCliente.getSucursal());
/*  849: 896 */     anticipoCliente.setNumero("");
/*  850: 897 */     anticipoCliente.setDescripcion("");
/*  851: 898 */     anticipoCliente.setIndicadorContabilizar(Boolean.valueOf(notaCreditoCliente.getDocumento().isIndicadorContabilizar()));
/*  852: 899 */     anticipoCliente.setEstado(Estado.ELABORADO);
/*  853: 900 */     Documento documento = (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ANTICIPO_CLIENTE, idOrganizacion).get(0);
/*  854: 901 */     anticipoCliente.setDocumento(documento);
/*  855:     */     
/*  856: 903 */     anticipoCliente.setNotaCreditoCliente(notaCreditoCliente);
/*  857: 904 */     anticipoCliente.setFecha(notaCreditoCliente.getFecha());
/*  858: 905 */     anticipoCliente.setValor(notaCreditoCliente.getTotalFactura());
/*  859: 906 */     anticipoCliente.setSaldo(anticipoCliente.getValor());
/*  860: 907 */     anticipoCliente.setEmpresa(notaCreditoCliente.getEmpresa());
/*  861: 908 */     this.servicioAnticipoCliente.cargarSecuencia(anticipoCliente);
/*  862: 909 */     this.servicioAnticipoCliente.guardar(anticipoCliente);
/*  863:     */     
/*  864: 911 */     notaCreditoCliente.setAnticipoCliente(anticipoCliente);
/*  865:     */     
/*  866: 913 */     notaCreditoCliente.setEstado(Estado.CONTABILIZADO);
/*  867:     */     
/*  868: 915 */     notaCreditoCliente.setFechaContabilizacion(notaCreditoCliente.getFecha());
/*  869: 916 */     notaCreditoCliente.setAsiento(anticipoCliente.getAsiento());
/*  870: 917 */     return anticipoCliente;
/*  871:     */   }
/*  872:     */   
/*  873:     */   public int contarPorCriterio(Map<String, String> filters)
/*  874:     */   {
/*  875: 922 */     return this.servicioFacturaCliente.contarPorCriterio(filters);
/*  876:     */   }
/*  877:     */   
/*  878:     */   public FacturaCliente cargarSecuencia(FacturaCliente notaCreditoCliente, PuntoDeVenta puntoDeVenta)
/*  879:     */     throws ExcepcionAS2
/*  880:     */   {
/*  881: 927 */     return this.servicioFacturaCliente.cargarSecuencia(notaCreditoCliente, puntoDeVenta);
/*  882:     */   }
/*  883:     */   
/*  884:     */   public FacturaCliente totalizar(FacturaCliente notaCreditoCliente)
/*  885:     */     throws ExcepcionAS2Ventas
/*  886:     */   {
/*  887: 932 */     return this.servicioFacturaCliente.totalizar(notaCreditoCliente);
/*  888:     */   }
/*  889:     */   
/*  890:     */   public List<DetalleFacturaCliente> obtenerDetalleFacturaClientePorDevolver(int idFacturaCliente)
/*  891:     */   {
/*  892: 937 */     return this.facturaClienteDao.obtenerDetalleDevolucionCliente(idFacturaCliente);
/*  893:     */   }
/*  894:     */   
/*  895:     */   public void actualizarDetalleDevolucion(int idNotaCreditoCliente, FacturaCliente devolucionCliente)
/*  896:     */   {
/*  897: 944 */     devolucionCliente.setListaDetalleFacturaCliente(new ArrayList());
/*  898:     */     
/*  899:     */ 
/*  900: 947 */     List<DetalleFacturaCliente> listadfcPorDevolver = this.facturaClienteDao.obtenerDetalleDevolucionCliente(idNotaCreditoCliente);
/*  901: 948 */     for (DetalleFacturaCliente detalleFacturaCliente : listadfcPorDevolver)
/*  902:     */     {
/*  903: 949 */       detalleFacturaCliente.getListaImpuestoProductoFacturaCliente().size();
/*  904: 950 */       for (ImpuestoProductoFacturaCliente ipfc : detalleFacturaCliente.getListaImpuestoProductoFacturaCliente()) {
/*  905: 951 */         ipfc.getImpuesto().getId();
/*  906:     */       }
/*  907:     */     }
/*  908: 957 */     devolucionCliente.setDireccionEmpresa(((DetalleFacturaCliente)listadfcPorDevolver.get(0)).getFacturaCliente().getDireccionEmpresa());
/*  909: 960 */     for (DetalleFacturaCliente dfcPorDevolver : listadfcPorDevolver)
/*  910:     */     {
/*  911: 962 */       DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/*  912: 963 */       dfc.setFacturaCliente(devolucionCliente);
/*  913: 964 */       dfc.setProducto(dfcPorDevolver.getProducto());
/*  914: 965 */       dfc.setPrecio(dfcPorDevolver.getPrecio());
/*  915: 966 */       dfc.setDetalleFacturaClientePadre(dfcPorDevolver);
/*  916: 967 */       dfc.setUnidadVenta(dfcPorDevolver.getUnidadVenta());
/*  917: 968 */       dfc.setDescuento(dfcPorDevolver.getDescuento());
/*  918: 969 */       dfc.setPorcentajeDescuento(dfcPorDevolver.getPorcentajeDescuento());
/*  919: 970 */       dfc.setIndicadorManejoPeso(dfcPorDevolver.isIndicadorManejoPeso());
/*  920: 971 */       dfc.setIndicadorPorcentajeIce(dfcPorDevolver.isIndicadorPorcentajeIce());
/*  921: 972 */       dfc.setIce(dfcPorDevolver.getIce());
/*  922: 973 */       dfc.setCodigoIce(dfcPorDevolver.getCodigoIce());
/*  923:     */       
/*  924: 975 */       InventarioProducto inventarioProducto = new InventarioProducto();
/*  925: 976 */       inventarioProducto.setDetalleDevolucionCliente(dfc);
/*  926: 977 */       dfc.setInventarioProducto(inventarioProducto);
/*  927: 979 */       if (dfcPorDevolver.getDetalleDespachoCliente() != null)
/*  928:     */       {
/*  929: 980 */         dfc.setBodega(dfcPorDevolver.getDetalleDespachoCliente().getBodega());
/*  930: 982 */         if (dfcPorDevolver.getDetalleDespachoCliente().getInventarioProducto().getLote() != null) {
/*  931: 983 */           dfc.getInventarioProducto().setLote(dfcPorDevolver.getDetalleDespachoCliente().getInventarioProducto().getLote());
/*  932:     */         }
/*  933:     */       }
/*  934: 986 */       if (devolucionCliente.getEmpresa().getCliente().isExcentoImpuestos())
/*  935:     */       {
/*  936: 987 */         dfc.setIndicadorImpuesto(false);
/*  937:     */       }
/*  938:     */       else
/*  939:     */       {
/*  940: 989 */         dfc.setIndicadorImpuesto(dfc.getProducto().isIndicadorImpuestos());
/*  941: 990 */         for (ImpuestoProductoFacturaCliente ipfc : dfcPorDevolver.getListaImpuestoProductoFacturaCliente())
/*  942:     */         {
/*  943: 991 */           ImpuestoProductoFacturaCliente ifcNEW = new ImpuestoProductoFacturaCliente();
/*  944: 992 */           ifcNEW.setImpuesto(ipfc.getImpuesto());
/*  945: 993 */           ifcNEW.setPorcentajeImpuesto(ipfc.getPorcentajeImpuesto());
/*  946: 994 */           ifcNEW.setDetalleFacturaCliente(dfc);
/*  947: 995 */           dfc.getListaImpuestoProductoFacturaCliente().add(ifcNEW);
/*  948:     */         }
/*  949:     */       }
/*  950: 998 */       devolucionCliente.getListaDetalleFacturaCliente().add(dfc);
/*  951:     */     }
/*  952:     */     try
/*  953:     */     {
/*  954:1002 */       totalizar(devolucionCliente);
/*  955:     */     }
/*  956:     */     catch (ExcepcionAS2Ventas e)
/*  957:     */     {
/*  958:1004 */       LOG.error(e.getErrorMessage(e));
/*  959:     */     }
/*  960:     */     catch (Exception e)
/*  961:     */     {
/*  962:1006 */       LOG.error(e);
/*  963:1007 */       e.printStackTrace();
/*  964:     */     }
/*  965:     */   }
/*  966:     */   
/*  967:     */   public FacturaCliente cargarDetalleFactura(FacturaCliente facturaCliente, FacturaCliente notaCreditoCliente)
/*  968:     */     throws ExcepcionAS2Ventas, ExcepcionAS2
/*  969:     */   {
/*  970:1021 */     FacturaCliente fp = this.servicioFacturaCliente.cargarDetalle(facturaCliente.getId());
/*  971:1022 */     facturaCliente = this.servicioFacturaCliente.copiarFacturaCliente(fp, notaCreditoCliente);
/*  972:1023 */     return notaCreditoCliente;
/*  973:     */   }
/*  974:     */   
/*  975:     */   private void actualizarInventarioProducto(FacturaCliente devolucionCliente)
/*  976:     */     throws ExcepcionAS2Inventario, ExcepcionAS2
/*  977:     */   {
/*  978:1034 */     InventarioProducto inventarioProducto = null;
/*  979:1035 */     for (DetalleFacturaCliente dfc : devolucionCliente.getListaDetalleFacturaCliente())
/*  980:     */     {
/*  981:1036 */       dfc.setEliminado(dfc.getCantidad().compareTo(BigDecimal.ZERO) == 0 ? true : dfc.isEliminado());
/*  982:1037 */       if (!dfc.isEliminado())
/*  983:     */       {
/*  984:1039 */         if (dfc.getInventarioProducto() != null)
/*  985:     */         {
/*  986:1040 */           inventarioProducto = dfc.getInventarioProducto();
/*  987:     */         }
/*  988:     */         else
/*  989:     */         {
/*  990:1042 */           inventarioProducto = new InventarioProducto();
/*  991:1043 */           dfc.setInventarioProducto(inventarioProducto);
/*  992:     */         }
/*  993:1045 */         inventarioProducto.setDetalleDevolucionCliente(dfc);
/*  994:     */         
/*  995:1047 */         BigDecimal cantidad = this.servicioProducto.convierteUnidad(dfc.getUnidadVenta(), dfc.getProducto().getUnidad(), dfc
/*  996:1048 */           .getProducto().getIdProducto(), dfc.getCantidad());
/*  997:     */         
/*  998:1050 */         inventarioProducto.setCantidad(cantidad);
/*  999:1051 */         inventarioProducto.setCantidadDocumento(dfc.getCantidad());
/* 1000:1052 */         inventarioProducto.setUnidadDocumento(dfc.getUnidadVenta().getNombre());
/* 1001:     */         
/* 1002:1054 */         inventarioProducto.setFecha(devolucionCliente.getFecha());
/* 1003:1055 */         inventarioProducto.setDocumento(devolucionCliente.getDocumento());
/* 1004:1056 */         inventarioProducto.setIdOrganizacion(devolucionCliente.getIdOrganizacion());
/* 1005:1057 */         inventarioProducto.setOperacion(1);
/* 1006:1058 */         inventarioProducto.setIndicadorGeneraCosto(devolucionCliente.getDocumento().isIndicadorGeneraCosto());
/* 1007:1059 */         inventarioProducto.setIdSucursal(devolucionCliente.getSucursal().getId());
/* 1008:1060 */         inventarioProducto.setBodega(dfc.getBodega());
/* 1009:1061 */         inventarioProducto.setProducto(dfc.getProducto());
/* 1010:1062 */         inventarioProducto.setNumeroDocumento(devolucionCliente.getNumero());
/* 1011:1063 */         inventarioProducto.setDescripcion(devolucionCliente.getDescripcion());
/* 1012:1064 */         inventarioProducto.setNombreFiscalEmpresa(devolucionCliente.getEmpresa().getNombreFiscal());
/* 1013:     */       }
/* 1014:     */     }
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   private void actualizarCantidadPorDevolver(FacturaCliente devolucionCliente)
/* 1018:     */   {
/* 1019:1074 */     if (devolucionCliente.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE) {
/* 1020:1076 */       for (DetalleFacturaCliente detalleDevolucion : devolucionCliente.getListaDetalleFacturaCliente())
/* 1021:     */       {
/* 1022:1078 */         DetalleDespachoCliente ddc = null;
/* 1023:1079 */         if ((detalleDevolucion.getDetalleFacturaClientePadre() != null) && 
/* 1024:1080 */           (detalleDevolucion.getDetalleFacturaClientePadre().getDetalleDespachoCliente() != null)) {
/* 1025:1081 */           ddc = detalleDevolucion.getDetalleFacturaClientePadre().getDetalleDespachoCliente();
/* 1026:     */         } else {
/* 1027:1083 */           ddc = detalleDevolucion.getDetalleDespachoClienteNoFacturado();
/* 1028:     */         }
/* 1029:1084 */         if ((!detalleDevolucion.isEliminado()) && (ddc != null))
/* 1030:     */         {
/* 1031:1087 */           BigDecimal cantidadDevuelta = ddc.getCantidadDevuelta().add(detalleDevolucion.getCantidad());
/* 1032:1088 */           HashMap<String, Object> campos = new HashMap();
/* 1033:1089 */           campos.put("cantidadDevuelta", cantidadDevuelta);
/* 1034:     */           
/* 1035:1091 */           this.detalleDespachoClienteDao.actualizarAtributoEntidad(ddc, campos);
/* 1036:     */         }
/* 1037:     */       }
/* 1038:     */     }
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public List getReporteNotaCreditoCliente(int idFacturaCliente, TipoOrganizacion tipoOrganizacion, DocumentoBase documentoBase, boolean indicadorDetallado)
/* 1042:     */   {
/* 1043:1108 */     return getReporteNotaCreditoCliente(idFacturaCliente, tipoOrganizacion, documentoBase, indicadorDetallado, 1, false);
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public List<Object[]> getReporteNotaCreditoCliente(int idFacturaCliente, TipoOrganizacion tipoOrganizacion, DocumentoBase documentoBase, boolean indicadorDetallado, int numeroCopias, boolean indicadorImpreso)
/* 1047:     */   {
/* 1048:1119 */     List<Object[]> lista = this.facturaClienteDao.getReporteNotaCreditoCliente(idFacturaCliente);
/* 1049:     */     Object[] object;
/* 1050:1120 */     if ((!indicadorDetallado) && (documentoBase.equals(DocumentoBase.DEVOLUCION_CLIENTE)) && 
/* 1051:1121 */       (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)))
/* 1052:     */     {
/* 1053:1123 */       TreeMap<String, Object[]> tmFactura = new TreeMap();
/* 1054:1125 */       for (Iterator localIterator = lista.iterator(); localIterator.hasNext();)
/* 1055:     */       {
/* 1056:1125 */         object = (Object[])localIterator.next();
/* 1057:     */         
/* 1058:1127 */         Object[] o = (Object[])tmFactura.get((String)object[5] + "~" + object[7]);
/* 1059:1128 */         if (o == null)
/* 1060:     */         {
/* 1061:1129 */           tmFactura.put((String)object[5] + "~" + object[7], object);
/* 1062:     */         }
/* 1063:     */         else
/* 1064:     */         {
/* 1065:1131 */           BigDecimal sumaCantidad = ((BigDecimal)o[4]).add((BigDecimal)object[4]);
/* 1066:1132 */           o[4] = sumaCantidad;
/* 1067:1133 */           o[37] = Integer.valueOf(Integer.parseInt(o[37].toString()) + 1);
/* 1068:     */         }
/* 1069:     */       }
/* 1070:1136 */       lista = new ArrayList(tmFactura.values());
/* 1071:     */     }
/* 1072:1139 */     if (numeroCopias >= 1)
/* 1073:     */     {
/* 1074:1140 */       listaAux = new ArrayList();
/* 1075:1141 */       for (int i = 1; i <= numeroCopias; i++) {
/* 1076:1142 */         for (Object[] objects : lista)
/* 1077:     */         {
/* 1078:1144 */           int tamanio = objects.length;
/* 1079:     */           
/* 1080:1146 */           Object[] a = new Object[tamanio];
/* 1081:1148 */           for (int k = 0; k < tamanio; k++) {
/* 1082:1149 */             if (k == 33) {
/* 1083:1150 */               a[33] = Integer.valueOf(i + (indicadorImpreso ? 2 : 0));
/* 1084:     */             } else {
/* 1085:1152 */               a[k] = objects[k];
/* 1086:     */             }
/* 1087:     */           }
/* 1088:1156 */           listaAux.add(a);
/* 1089:     */         }
/* 1090:     */       }
/* 1091:1159 */       lista = listaAux;
/* 1092:     */     }
/* 1093:1162 */     for (List<Object[]> listaAux = lista.iterator(); listaAux.hasNext();)
/* 1094:     */     {
/* 1095:1162 */       objects = (Object[])listaAux.next();
/* 1096:1163 */       if ((String)objects[67] != null) {
/* 1097:1164 */         for (SelectItem seleItem : DatosSRI.getListaFormaPago(((Integer)objects[68]).intValue())) {
/* 1098:1165 */           if (((String)objects[67]).equals(seleItem.getValue().toString())) {
/* 1099:1166 */             objects[67] = (seleItem.getValue().toString() + " " + seleItem.getLabel().toString());
/* 1100:     */           }
/* 1101:     */         }
/* 1102:     */       }
/* 1103:     */     }
/* 1104:     */     Object[] objects;
/* 1105:1172 */     return lista;
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1109:     */   public void procesarPreDevolucion(PreDevolucionCliente predev, boolean indicadorAutoImpresor, PuntoDeVenta puntoVenta)
/* 1110:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Ventas, AS2Exception, ExcepcionAS2
/* 1111:     */   {
/* 1112:     */     try
/* 1113:     */     {
/* 1114:1181 */       FacturaCliente devolucion = new FacturaCliente();
/* 1115:1182 */       devolucion.setIdOrganizacion(predev.getIdOrganizacion());
/* 1116:1183 */       devolucion.setSucursal(predev.getSucursal());
/* 1117:1184 */       devolucion.setNumero("");
/* 1118:1185 */       if (predev.getReferencia8() != null) {
/* 1119:1186 */         devolucion.setReferencia8(predev.getReferencia8());
/* 1120:     */       }
/* 1121:1188 */       devolucion.setFecha(new Date());
/* 1122:1189 */       devolucion.setEstado(Estado.ELABORADO);
/* 1123:1190 */       devolucion.setNumeroCuotas(1);
/* 1124:1191 */       devolucion.setMotivoNotaCreditoCliente(predev.getMotivoNotaCreditoCliente());
/* 1125:1192 */       devolucion.setCodigoMovil(predev.getCodigoMovil());
/* 1126:1193 */       devolucion.setEmpresa(predev.getEmpresa());
/* 1127:1194 */       devolucion.getEmpresa().setTipoIdentificacion(this.servicioTipoIdentificacion
/* 1128:1195 */         .buscarPorId(Integer.valueOf(predev.getEmpresa().getTipoIdentificacion().getIdTipoIdentificacion())));
/* 1129:1196 */       devolucion.setSubempresa(predev.getSubempresa() != null ? predev.getSubempresa() : null);
/* 1130:     */       
/* 1131:1198 */       List<DireccionEmpresa> listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(devolucion.getEmpresaFinal().getId());
/* 1132:1199 */       for (DireccionEmpresa direccion : listaDireccionEmpresa) {
/* 1133:1200 */         if (direccion.isIndicadorDireccionPrincipal()) {
/* 1134:1201 */           devolucion.setDireccionEmpresa(direccion);
/* 1135:     */         } else {
/* 1136:1203 */           devolucion.setDireccionEmpresa((DireccionEmpresa)listaDireccionEmpresa.get(0));
/* 1137:     */         }
/* 1138:     */       }
/* 1139:1207 */       devolucion.setEmail(this.servicioEmpresa.cargarMails(devolucion.getEmpresaFinal(), DocumentoBase.DEVOLUCION_CLIENTE));
/* 1140:     */       
/* 1141:1209 */       Object listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DEVOLUCION_CLIENTE, devolucion
/* 1142:1210 */         .getIdOrganizacion());
/* 1143:1211 */       devolucion.setDocumento((Documento)((List)listaDocumento).get(0));
/* 1144:1212 */       if (devolucion.getDocumento().isIndicadorDocumentoTributario()) {
/* 1145:1213 */         cargarSecuencia(devolucion, puntoVenta);
/* 1146:     */       } else {
/* 1147:1215 */         cargarSecuencia(devolucion, null);
/* 1148:     */       }
/* 1149:1217 */       this.servicioSecuencia.detach(devolucion.getDocumento().getSecuencia());
/* 1150:1218 */       this.servicioSecuencia.actualizarSecuencia(devolucion.getDocumento().getSecuencia(), devolucion.getNumero());
/* 1151:     */       
/* 1152:     */ 
/* 1153:     */ 
/* 1154:1222 */       CuentaPorCobrar cxc = this.cuentaPorCobrarDao.buscarPorId(predev.getIdFacturaClientePadre());
/* 1155:1223 */       FacturaCliente facturaClientePadre = cxc.getFacturaCliente();
/* 1156:1225 */       if (facturaClientePadre != null) {
/* 1157:1226 */         if ((facturaClientePadre.getDespachoCliente() != null) || (facturaClientePadre.isIndicadorSaldoInicial()))
/* 1158:     */         {
/* 1159:1227 */           devolucion.setFacturaClientePadre(facturaClientePadre);
/* 1160:1228 */           actualizarDetalleDevolucion(predev, devolucion);
/* 1161:     */         }
/* 1162:     */         else
/* 1163:     */         {
/* 1164:1230 */           if (facturaClientePadre.getDespachoCliente() == null) {
/* 1165:1231 */             throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioNotaCreditoClienteImpl.ERROR_FACTURA_SIN_DESPACHAR", new String[] { facturaClientePadre.getNumero() });
/* 1166:     */           }
/* 1167:1232 */           if (!facturaClientePadre.isIndicadorSaldoInicial()) {
/* 1168:1233 */             throw new AS2Exception("Error al procesar pre-devolucion");
/* 1169:     */           }
/* 1170:     */         }
/* 1171:     */       }
/* 1172:1239 */       guardar(devolucion);
/* 1173:     */       
/* 1174:1241 */       predev.setEstado(Estado.PROCESADO);
/* 1175:1242 */       this.servicioPreDevolucionCliente.guardarValidar(predev);
/* 1176:     */     }
/* 1177:     */     catch (ExcepcionAS2Financiero e)
/* 1178:     */     {
/* 1179:1244 */       e.printStackTrace();
/* 1180:1245 */       this.context.setRollbackOnly();
/* 1181:1246 */       throw e;
/* 1182:     */     }
/* 1183:     */     catch (ExcepcionAS2Ventas e)
/* 1184:     */     {
/* 1185:1248 */       e.printStackTrace();
/* 1186:1249 */       this.context.setRollbackOnly();
/* 1187:1250 */       throw e;
/* 1188:     */     }
/* 1189:     */     catch (AS2Exception e)
/* 1190:     */     {
/* 1191:1252 */       e.printStackTrace();
/* 1192:1253 */       this.context.setRollbackOnly();
/* 1193:1254 */       throw e;
/* 1194:     */     }
/* 1195:     */     catch (ExcepcionAS2 e)
/* 1196:     */     {
/* 1197:1256 */       e.printStackTrace();
/* 1198:1257 */       this.context.setRollbackOnly();
/* 1199:1258 */       throw e;
/* 1200:     */     }
/* 1201:     */     catch (Exception e)
/* 1202:     */     {
/* 1203:1260 */       e.printStackTrace();
/* 1204:1261 */       this.context.setRollbackOnly();
/* 1205:1262 */       throw new ExcepcionAS2(e);
/* 1206:     */     }
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   private void actualizarDetalleDevolucion(PreDevolucionCliente predev, FacturaCliente devolucion)
/* 1210:     */     throws AS2Exception
/* 1211:     */   {
/* 1212:1268 */     Map<Integer, DetalleFacturaCliente> mapDetalles = new HashMap();
/* 1213:1269 */     for (DetalleFacturaCliente dfc : this.facturaClienteDao.cargarDetalleFactura(devolucion.getFacturaClientePadre().getId())) {
/* 1214:1270 */       mapDetalles.put(Integer.valueOf(dfc.getProducto().getId()), dfc);
/* 1215:     */     }
/* 1216:1273 */     devolucion.setListaDetalleFacturaCliente(new ArrayList());
/* 1217:     */     
/* 1218:     */ 
/* 1219:1276 */     devolucion.setDireccionEmpresa(devolucion.getFacturaClientePadre().getDireccionEmpresa());
/* 1220:1280 */     for (DetallePreDevolucionCliente detpredev : predev.getListaDetallePreDevolucionCliente())
/* 1221:     */     {
/* 1222:1281 */       Producto producto = this.servicioProducto.cargaDetalle(detpredev.getProducto().getIdProducto());
/* 1223:1282 */       DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 1224:1283 */       dfc.setFacturaCliente(devolucion);
/* 1225:1284 */       dfc.setProducto(producto);
/* 1226:1285 */       dfc.setIce(producto.getIce());
/* 1227:1286 */       dfc.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 1228:1287 */       dfc.setCodigoIce(producto.getCodigoIce());
/* 1229:1288 */       dfc.setCantidad(detpredev.getCantidad());
/* 1230:1289 */       dfc.setPrecio(detpredev.getPrecio());
/* 1231:1290 */       dfc.setUnidadVenta(detpredev.getUnidad());
/* 1232:1291 */       dfc.setBodega(detpredev.getBodega());
/* 1233:1292 */       dfc.setDescuento(BigDecimal.ZERO);
/* 1234:1293 */       dfc.setPorcentajeDescuento(BigDecimal.ZERO);
/* 1235:     */       
/* 1236:1295 */       dfc.setDetalleFacturaClientePadre((DetalleFacturaCliente)mapDetalles.get(Integer.valueOf(producto.getId())));
/* 1237:     */       
/* 1238:1297 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 1239:1298 */       inventarioProducto.setDetalleDevolucionCliente(dfc);
/* 1240:1299 */       dfc.setInventarioProducto(inventarioProducto);
/* 1241:     */       
/* 1242:1301 */       dfc.getInventarioProducto().setLote(detpredev.getLote());
/* 1243:1303 */       if (devolucion.getEmpresa().getCliente().isExcentoImpuestos()) {
/* 1244:1304 */         dfc.setIndicadorImpuesto(false);
/* 1245:     */       } else {
/* 1246:1306 */         dfc.setIndicadorImpuesto(dfc.getProducto().isIndicadorImpuestos());
/* 1247:     */       }
/* 1248:1308 */       if (dfc.isIndicadorImpuesto()) {
/* 1249:     */         try
/* 1250:     */         {
/* 1251:1310 */           dfc.getProducto().setCategoriaImpuesto(this.servicioCategoriaImpuesto
/* 1252:1311 */             .cargarDetalle(dfc.getProducto().getCategoriaImpuesto().getIdCategoriaImpuesto()));
/* 1253:1312 */           this.servicioFacturaCliente.obtenerImpuestosProductos(dfc.getProducto(), dfc);
/* 1254:     */         }
/* 1255:     */         catch (ExcepcionAS2Inventario e)
/* 1256:     */         {
/* 1257:1314 */           throw new AS2Exception("Error al calcular impuestos en pre-devolucion");
/* 1258:     */         }
/* 1259:     */       }
/* 1260:1317 */       devolucion.getListaDetalleFacturaCliente().add(dfc);
/* 1261:     */     }
/* 1262:     */     try
/* 1263:     */     {
/* 1264:1320 */       totalizar(devolucion);
/* 1265:     */     }
/* 1266:     */     catch (ExcepcionAS2Ventas e)
/* 1267:     */     {
/* 1268:1322 */       LOG.error(e.getErrorMessage(e));
/* 1269:     */     }
/* 1270:     */     catch (Exception e)
/* 1271:     */     {
/* 1272:1324 */       LOG.error(e);
/* 1273:1325 */       e.printStackTrace();
/* 1274:     */     }
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   public void actualizarAtributoEntidad(FacturaCliente notaCreditoCliente, HashMap<String, Object> campos)
/* 1278:     */   {
/* 1279:1331 */     this.facturaClienteDao.actualizarAtributoEntidad(notaCreditoCliente, campos);
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1283:     */   public void rechazar(FacturaCliente notaCreditoCliente, Usuario usuarioSesion, NotaFacturaCliente notaFacturaCliente)
/* 1284:     */     throws AS2Exception, ExcepcionAS2
/* 1285:     */   {
/* 1286:1338 */     FacturaCliente notaBase = this.servicioFacturaCliente.buscarPorId(Integer.valueOf(notaCreditoCliente.getId()));
/* 1287:1340 */     if ((!Estado.ELABORADO.equals(notaBase.getEstado())) && (!Estado.APROBADO_PARCIAL.equals(notaBase))) {
/* 1288:1341 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] { notaBase.getEstado().toString() });
/* 1289:     */     }
/* 1290:1343 */     this.servicioFacturaCliente.guardarNotaFacturaCliente(notaFacturaCliente);
/* 1291:1344 */     actualizarEstado(notaCreditoCliente.getId(), Estado.ANULADO, null);
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1295:     */   public void aprobar(FacturaCliente notaCreditoCliente, Usuario usuarioSesion, NotaFacturaCliente notaFacturaCliente)
/* 1296:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 1297:     */   {
/* 1298:1351 */     FacturaCliente notaBase = this.servicioFacturaCliente.buscarPorId(Integer.valueOf(notaCreditoCliente.getId()));
/* 1299:1353 */     if ((!Estado.ELABORADO.equals(notaBase.getEstado())) && (!Estado.APROBADO_PARCIAL.equals(notaBase))) {
/* 1300:1354 */       throw new AS2Exception("msg_error_accion_no_permitida", new String[] { notaBase.getEstado().toString() });
/* 1301:     */     }
/* 1302:1356 */     this.servicioFacturaCliente.guardarNotaFacturaCliente(notaFacturaCliente);
/* 1303:1357 */     guardar(notaCreditoCliente, true);
/* 1304:     */   }
/* 1305:     */   
/* 1306:     */   public class AuxDescuentoLineaNC
/* 1307:     */   {
/* 1308:     */     int idDFCPadre;
/* 1309:     */     BigDecimal descuentoLineaNC;
/* 1310:     */     
/* 1311:     */     public AuxDescuentoLineaNC(int idDFCPadre, BigDecimal descuentoLineaNC)
/* 1312:     */     {
/* 1313:1366 */       this.idDFCPadre = idDFCPadre;
/* 1314:1367 */       this.descuentoLineaNC = descuentoLineaNC;
/* 1315:     */     }
/* 1316:     */     
/* 1317:     */     public int getIdDFCPadre()
/* 1318:     */     {
/* 1319:1371 */       return this.idDFCPadre;
/* 1320:     */     }
/* 1321:     */     
/* 1322:     */     public void setIdDFCPadre(int idDFCPadre)
/* 1323:     */     {
/* 1324:1375 */       this.idDFCPadre = idDFCPadre;
/* 1325:     */     }
/* 1326:     */     
/* 1327:     */     public BigDecimal getDescuentoLineaNC()
/* 1328:     */     {
/* 1329:1379 */       return this.descuentoLineaNC;
/* 1330:     */     }
/* 1331:     */     
/* 1332:     */     public void setDescuentoLineaNC(BigDecimal descuentoLineaNC)
/* 1333:     */     {
/* 1334:1383 */       this.descuentoLineaNC = descuentoLineaNC;
/* 1335:     */     }
/* 1336:     */   }
/* 1337:     */   
/* 1338:     */   public void procesarPreDevoluciones(RecepcionDevolucionTransportista recepcionDevolucionTransportista, boolean indicadorAutoimpresor, PuntoDeVenta puntoDeVenta)
/* 1339:     */     throws ExcepcionAS2Financiero, ExcepcionAS2Ventas, ExcepcionAS2, AS2Exception
/* 1340:     */   {
/* 1341:     */     try
/* 1342:     */     {
/* 1343:1393 */       validarPreDevoluciones(recepcionDevolucionTransportista);
/* 1344:1394 */       recepcionDevolucionTransportista.setEstado(Estado.PROCESADO);
/* 1345:1395 */       recepcionDevolucionTransportista.setFechaProcesamiento(new Date());
/* 1346:1396 */       this.servicioRecepcionDevolucionTransportista.guardar(recepcionDevolucionTransportista);
/* 1347:     */       
/* 1348:1398 */       List<DetallePreDevolucionCliente> listDetallesFacturarEcopacific = new ArrayList();
/* 1349:1399 */       List<DetallePreDevolucionCliente> listDetallesFacturarTransportista = new ArrayList();
/* 1350:1400 */       for (PreDevolucionCliente pdc : recepcionDevolucionTransportista.getListaPreDevolucionCliente()) {
/* 1351:1401 */         if ((pdc.getRecepcionDevolucionTransportista() != null) && (!Estado.PROCESADO.equals(pdc.getEstado())))
/* 1352:     */         {
/* 1353:1402 */           for (DetallePreDevolucionCliente dpdc : pdc.getListaDetallePreDevolucionCliente()) {
/* 1354:1403 */             if ((!recepcionDevolucionTransportista.isProductoBueno()) && 
/* 1355:1404 */               (dpdc.isIndicadorProcesar())) {
/* 1356:1405 */               if (dpdc.getCantidadProcesar().compareTo(dpdc.getCantidadRecibida()) != 0)
/* 1357:     */               {
/* 1358:1406 */                 listDetallesFacturarTransportista.add(dpdc);
/* 1359:1407 */                 if (dpdc.getCantidadRecibida().compareTo(BigDecimal.ZERO) != 0) {
/* 1360:1408 */                   listDetallesFacturarEcopacific.add(dpdc);
/* 1361:     */                 }
/* 1362:     */               }
/* 1363:     */               else
/* 1364:     */               {
/* 1365:1410 */                 listDetallesFacturarEcopacific.add(dpdc);
/* 1366:     */               }
/* 1367:     */             }
/* 1368:     */           }
/* 1369:1415 */           procesarPreDevolucion(pdc, indicadorAutoimpresor, puntoDeVenta);
/* 1370:     */         }
/* 1371:     */       }
/* 1372:1418 */       if (listDetallesFacturarTransportista.size() > 0)
/* 1373:     */       {
/* 1374:1419 */         FacturaCliente fc = crearFacturaCliente(true, recepcionDevolucionTransportista, indicadorAutoimpresor, puntoDeVenta, listDetallesFacturarTransportista);
/* 1375:     */         
/* 1376:1421 */         crearDespacho(fc);
/* 1377:     */       }
/* 1378:1423 */       if (listDetallesFacturarEcopacific.size() > 0)
/* 1379:     */       {
/* 1380:1424 */         FacturaCliente fc = crearFacturaCliente(false, recepcionDevolucionTransportista, indicadorAutoimpresor, puntoDeVenta, listDetallesFacturarEcopacific);
/* 1381:     */         
/* 1382:     */ 
/* 1383:1427 */         crearDespacho(fc);
/* 1384:     */       }
/* 1385:     */     }
/* 1386:     */     catch (ExcepcionAS2Financiero e)
/* 1387:     */     {
/* 1388:1430 */       e.printStackTrace();
/* 1389:1431 */       this.context.setRollbackOnly();
/* 1390:1432 */       throw e;
/* 1391:     */     }
/* 1392:     */     catch (ExcepcionAS2Ventas e)
/* 1393:     */     {
/* 1394:1434 */       e.printStackTrace();
/* 1395:1435 */       this.context.setRollbackOnly();
/* 1396:1436 */       throw e;
/* 1397:     */     }
/* 1398:     */     catch (ExcepcionAS2 e)
/* 1399:     */     {
/* 1400:1438 */       e.printStackTrace();
/* 1401:1439 */       this.context.setRollbackOnly();
/* 1402:1440 */       throw e;
/* 1403:     */     }
/* 1404:     */     catch (AS2Exception e)
/* 1405:     */     {
/* 1406:1442 */       e.printStackTrace();
/* 1407:1443 */       this.context.setRollbackOnly();
/* 1408:1444 */       throw e;
/* 1409:     */     }
/* 1410:     */   }
/* 1411:     */   
/* 1412:     */   private void validarPreDevoluciones(RecepcionDevolucionTransportista recepcionDevolucionTransportista)
/* 1413:     */     throws AS2Exception
/* 1414:     */   {
/* 1415:1449 */     for (PreDevolucionCliente pdc : recepcionDevolucionTransportista.getListaPreDevolucionCliente()) {
/* 1416:1450 */       if (pdc.getRecepcionDevolucionTransportista() != null) {
/* 1417:1452 */         for (DetallePreDevolucionCliente dpdc : pdc.getListaDetallePreDevolucionCliente())
/* 1418:     */         {
/* 1419:1453 */           if ((dpdc.isIndicadorProcesar()) && 
/* 1420:1454 */             (dpdc.getCantidadRecibida().compareTo(dpdc.getCantidadProcesar()) > 0)) {
/* 1421:1456 */             throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioNotaCreditoClienteImpl.MENSAJE_ERROR_CANTIDAD_DEVUELTA_EXCEDIDA", new String[] { "" + dpdc.getCantidadProcesar(), "" + dpdc.getCantidadRecibida(), dpdc.getProducto().getNombre() });
/* 1422:     */           }
/* 1423:1459 */           if (null == dpdc.getBodega()) {
/* 1424:1460 */             throw new AS2Exception("msg_info_seleccionar_bodega", new String[] { "" });
/* 1425:     */           }
/* 1426:1462 */           if ((dpdc.getProducto().isIndicadorLote()) && (null == dpdc.getLote())) {
/* 1427:1463 */             throw new AS2Exception("msg_error_lote_requerido", new String[] { "" });
/* 1428:     */           }
/* 1429:     */         }
/* 1430:     */       }
/* 1431:     */     }
/* 1432:     */   }
/* 1433:     */   
/* 1434:     */   private FacturaCliente crearFacturaCliente(boolean facturaTransportista, RecepcionDevolucionTransportista recepcionDevolucionTransportista, boolean indicadorAutoimpresor, PuntoDeVenta puntoDeVenta, List<DetallePreDevolucionCliente> listDetallesAFacturar)
/* 1435:     */     throws ExcepcionAS2, AS2Exception
/* 1436:     */   {
/* 1437:1475 */     FacturaCliente facturaCliente = new FacturaCliente();
/* 1438:1476 */     facturaCliente.setNumero("");
/* 1439:1477 */     facturaCliente.setFecha(new Date());
/* 1440:1478 */     facturaCliente.setEstado(Estado.PROCESADO);
/* 1441:1479 */     facturaCliente.setNumeroCuotas(1);
/* 1442:     */     
/* 1443:1481 */     facturaCliente.setIdOrganizacion(recepcionDevolucionTransportista.getIdOrganizacion());
/* 1444:1482 */     facturaCliente.setSucursal(recepcionDevolucionTransportista.getSucursal());
/* 1445:1483 */     FacturaClienteSRI facturaClienteSRI = new FacturaClienteSRI();
/* 1446:1484 */     facturaClienteSRI.setEstado(facturaCliente.getEstado());
/* 1447:1485 */     facturaClienteSRI.setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 1448:1486 */     facturaClienteSRI.setIdSucursal(facturaCliente.getSucursal().getId());
/* 1449:     */     
/* 1450:1488 */     facturaClienteSRI.setFacturaCliente(facturaCliente);
/* 1451:1489 */     facturaCliente.setFacturaClienteSRI(facturaClienteSRI);
/* 1452:     */     
/* 1453:1491 */     Documento documento = null;
/* 1454:1492 */     if ((getListaDocumentoCliente(facturaCliente.getIdOrganizacion()) != null) && 
/* 1455:1493 */       (!getListaDocumentoCliente(facturaCliente.getIdOrganizacion()).isEmpty()))
/* 1456:     */     {
/* 1457:1494 */       documento = (Documento)getListaDocumentoCliente(facturaCliente.getIdOrganizacion()).get(0);
/* 1458:1495 */       facturaCliente.setDocumento(documento);
/* 1459:1496 */       this.servicioFacturaCliente.actualizarDocumento(facturaCliente, indicadorAutoimpresor, puntoDeVenta);
/* 1460:     */     }
/* 1461:1500 */     facturaCliente.setIndicadorAutorizaVenta(false);
/* 1462:     */     
/* 1463:1502 */     Subempresa subEmpresa = null;
/* 1464:     */     Empresa empresa;
/* 1465:1503 */     if (facturaTransportista)
/* 1466:     */     {
/* 1467:1504 */       Empresa empresa = recepcionDevolucionTransportista.getTransportista().getCliente();
/* 1468:1505 */       if (empresa == null) {
/* 1469:1507 */         throw new AS2Exception("msg_error_configurar_cliente_transportista", new String[] {recepcionDevolucionTransportista.getTransportista().getNombre() });
/* 1470:     */       }
/* 1471:     */     }
/* 1472:     */     else
/* 1473:     */     {
/* 1474:1510 */       empresa = recepcionDevolucionTransportista.getEmpresa();
/* 1475:1511 */       subEmpresa = recepcionDevolucionTransportista.getSubempresa();
/* 1476:     */     }
/* 1477:1513 */     actualizarCliente(empresa, facturaCliente, subEmpresa);
/* 1478:1514 */     asignarCodigoFormaPagoSRI(recepcionDevolucionTransportista.getIdOrganizacion(), facturaClienteSRI, empresa);
/* 1479:1515 */     actualizarDetalleFacturaDevolucion(listDetallesAFacturar, facturaCliente, facturaTransportista);
/* 1480:     */     
/* 1481:1517 */     this.servicioFacturaCliente.generarCuentaPorCobrar(facturaCliente);
/* 1482:1518 */     this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(facturaCliente);
/* 1483:1519 */     totalizar(facturaCliente);
/* 1484:1520 */     this.servicioFacturaCliente.guardar(facturaCliente);
/* 1485:1521 */     return facturaCliente;
/* 1486:     */   }
/* 1487:     */   
/* 1488:     */   private DespachoCliente crearDespacho(FacturaCliente facturaCliente)
/* 1489:     */     throws ExcepcionAS2, AS2Exception
/* 1490:     */   {
/* 1491:1526 */     DespachoCliente despachoCliente = new DespachoCliente();
/* 1492:1527 */     despachoCliente.setNumero("");
/* 1493:1528 */     despachoCliente.setEstado(Estado.PROCESADO);
/* 1494:1529 */     despachoCliente.setFecha(facturaCliente.getFecha());
/* 1495:1530 */     despachoCliente.setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 1496:1531 */     despachoCliente.setSucursal(facturaCliente.getSucursal());
/* 1497:1532 */     despachoCliente.setEmpresa(facturaCliente.getEmpresa());
/* 1498:1533 */     despachoCliente.setSubempresa(facturaCliente.getSubempresa());
/* 1499:1534 */     despachoCliente.setDireccionEmpresa(facturaCliente.getDireccionEmpresa());
/* 1500:1535 */     despachoCliente.setDescripcion(facturaCliente.getDescripcion());
/* 1501:1536 */     facturaCliente.setDespachoCliente(despachoCliente);
/* 1502:1537 */     despachoCliente.setFacturaCliente(facturaCliente);
/* 1503:1539 */     if (!getListaDocumentoDespacho(facturaCliente.getIdOrganizacion()).isEmpty()) {
/* 1504:1540 */       facturaCliente.getDespachoCliente().setDocumento((Documento)getListaDocumentoDespacho(facturaCliente.getIdOrganizacion()).get(0));
/* 1505:     */     }
/* 1506:1543 */     for (DetalleFacturaCliente dfc : facturaCliente.getListaDetalleFacturaCliente()) {
/* 1507:1544 */       if ((!dfc.isEliminado()) && (TipoProducto.ARTICULO.equals(dfc.getProducto().getTipoProducto())))
/* 1508:     */       {
/* 1509:1545 */         DetalleDespachoCliente ddc = new DetalleDespachoCliente();
/* 1510:1546 */         ddc.setBodega(dfc.getBodega());
/* 1511:1547 */         ddc.setCantidad(dfc.getCantidad());
/* 1512:1548 */         ddc.setDespachoCliente(facturaCliente.getDespachoCliente());
/* 1513:1549 */         ddc.setDetalleFacturaCliente(dfc);
/* 1514:1550 */         InventarioProducto inventarioProducto = new InventarioProducto();
/* 1515:1551 */         inventarioProducto.setOperacion(facturaCliente.getDespachoCliente().getDocumento().getOperacion());
/* 1516:1552 */         ddc.setInventarioProducto(inventarioProducto);
/* 1517:1553 */         ddc.setProducto(dfc.getProducto());
/* 1518:1554 */         ddc.setUnidadVenta(dfc.getProducto().getUnidad());
/* 1519:1555 */         facturaCliente.getDespachoCliente().getListaDetalleDespachoCliente().add(ddc);
/* 1520:1556 */         dfc.setDetalleDespachoCliente(ddc);
/* 1521:1557 */         dfc.getDetalleDespachoCliente().setProducto(dfc.getProducto());
/* 1522:1558 */         dfc.getDetalleDespachoCliente().setCantidad(dfc.getCantidad().setScale(2, RoundingMode.HALF_UP));
/* 1523:1559 */         dfc.getDetalleDespachoCliente().setPeso(dfc.getPeso().setScale(2, RoundingMode.HALF_UP));
/* 1524:1560 */         dfc.getDetalleDespachoCliente().setUnidadVenta(dfc.getUnidadVenta());
/* 1525:     */       }
/* 1526:     */     }
/* 1527:1565 */     this.servicioDespachoCliente.guardar(despachoCliente);
/* 1528:1566 */     return despachoCliente;
/* 1529:     */   }
/* 1530:     */   
/* 1531:     */   public List<Documento> getListaDocumentoDespacho(int idOrganizacion)
/* 1532:     */   {
/* 1533:1570 */     List<Documento> listaDocumentoDespacho = null;
/* 1534:     */     try
/* 1535:     */     {
/* 1536:1572 */       listaDocumentoDespacho = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DESPACHO_BODEGA, idOrganizacion);
/* 1537:     */     }
/* 1538:     */     catch (ExcepcionAS2 localExcepcionAS2) {}
/* 1539:1575 */     return listaDocumentoDespacho;
/* 1540:     */   }
/* 1541:     */   
/* 1542:     */   public List<Documento> getListaDocumentoCliente(int idOrganizacion)
/* 1543:     */   {
/* 1544:1579 */     List<Documento> listaDocumentoCliente = null;
/* 1545:     */     try
/* 1546:     */     {
/* 1547:1581 */       listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FACTURA_CLIENTE, idOrganizacion);
/* 1548:     */     }
/* 1549:     */     catch (ExcepcionAS2 localExcepcionAS2) {}
/* 1550:1584 */     return listaDocumentoCliente;
/* 1551:     */   }
/* 1552:     */   
/* 1553:     */   private void actualizarCliente(Empresa empresa, FacturaCliente facturaCliente, Subempresa subempresa)
/* 1554:     */   {
/* 1555:1589 */     if (empresa != null) {
/* 1556:1590 */       facturaCliente.setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/* 1557:     */     }
/* 1558:1592 */     empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 1559:1593 */     facturaCliente.setEmpresa(empresa);
/* 1560:1595 */     if (empresa.getListaFormaPagoSRI().size() > 0) {
/* 1561:1596 */       facturaCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(((FormaPagoSRI)empresa.getListaFormaPagoSRI().get(0)).getCodigo());
/* 1562:     */     }
/* 1563:1599 */     facturaCliente.setSubempresa(subempresa);
/* 1564:1600 */     facturaCliente.setDireccionEmpresa(null);
/* 1565:1601 */     facturaCliente.setEmail(this.servicioEmpresa.cargarMails(empresa, facturaCliente.getDocumento().getDocumentoBase()));
/* 1566:1604 */     if (facturaCliente.getZona() == null) {
/* 1567:1605 */       facturaCliente.setZona(empresa.getCliente().getZona());
/* 1568:     */     }
/* 1569:1608 */     if (facturaCliente.getCondicionPago() == null) {
/* 1570:1610 */       facturaCliente.setCondicionPago(empresa.getCliente().getCondicionPago());
/* 1571:     */     }
/* 1572:1613 */     if (facturaCliente.getNumeroCuotas() == 0) {
/* 1573:1614 */       facturaCliente.setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/* 1574:     */     }
/* 1575:1617 */     if ((facturaCliente.getEmail() != null) && (facturaCliente.getEmail().isEmpty())) {
/* 1576:1618 */       facturaCliente.setEmail(empresa.getEmail1());
/* 1577:     */     }
/* 1578:1621 */     List<DireccionEmpresa> listaDireccionEmpresa = null;
/* 1579:1622 */     if (facturaCliente.getSubempresa() != null) {
/* 1580:1623 */       listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(facturaCliente.getSubempresa().getEmpresa().getId());
/* 1581:     */     }
/* 1582:1626 */     if ((listaDireccionEmpresa == null) || (listaDireccionEmpresa.isEmpty())) {
/* 1583:1627 */       listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(empresa.getId());
/* 1584:     */     }
/* 1585:1630 */     for (DireccionEmpresa de : listaDireccionEmpresa) {
/* 1586:1631 */       if (de.isIndicadorDireccionPrincipal())
/* 1587:     */       {
/* 1588:1632 */         facturaCliente.setDireccionEmpresa(de);
/* 1589:1633 */         break;
/* 1590:     */       }
/* 1591:     */     }
/* 1592:     */   }
/* 1593:     */   
/* 1594:     */   private void actualizarDetalleFacturaDevolucion(List<DetallePreDevolucionCliente> listDetalles, FacturaCliente devolucion, boolean facturaTransportista)
/* 1595:     */     throws AS2Exception, ExcepcionAS2
/* 1596:     */   {
/* 1597:1644 */     devolucion.setListaDetalleFacturaCliente(new ArrayList());
/* 1598:1648 */     for (DetallePreDevolucionCliente detpredev : listDetalles)
/* 1599:     */     {
/* 1600:1649 */       Producto producto = this.servicioProducto.cargaDetalle(detpredev.getProducto().getIdProducto());
/* 1601:1650 */       DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 1602:1651 */       dfc.setFacturaCliente(devolucion);
/* 1603:1652 */       dfc.setProducto(producto);
/* 1604:1653 */       if (facturaTransportista) {
/* 1605:1654 */         dfc.setCantidad(detpredev.getCantidadProcesar().subtract(detpredev.getCantidadRecibida()).abs());
/* 1606:     */       } else {
/* 1607:1656 */         dfc.setCantidad(detpredev.getCantidadRecibida());
/* 1608:     */       }
/* 1609:1657 */       dfc.setIce(producto.getIce());
/* 1610:1658 */       dfc.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 1611:1659 */       dfc.setCodigoIce(producto.getCodigoIce());
/* 1612:1660 */       dfc.setPrecio(BigDecimal.ZERO);
/* 1613:1661 */       if (devolucion.getEmpresa().getCliente().getListaPrecios() != null)
/* 1614:     */       {
/* 1615:1662 */         if (dfc.getPrecio().compareTo(BigDecimal.ZERO) == 0) {
/* 1616:     */           try
/* 1617:     */           {
/* 1618:1665 */             DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(devolucion
/* 1619:1666 */               .getEmpresa().getCliente().getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), devolucion
/* 1620:1667 */               .getFecha(), null, devolucion.getNumero());
/* 1621:1668 */             dfc.setPrecio(dvlp.getPrecioUnitario());
/* 1622:     */           }
/* 1623:     */           catch (ExcepcionAS2 e)
/* 1624:     */           {
/* 1625:1670 */             e.printStackTrace();
/* 1626:     */           }
/* 1627:     */         }
/* 1628:     */       }
/* 1629:     */       else {
/* 1630:1675 */         throw new ExcepcionAS2("msg_error_empresa_lista_precios");
/* 1631:     */       }
/* 1632:1677 */       dfc.setUnidadVenta(detpredev.getUnidad());
/* 1633:1678 */       dfc.setBodega(detpredev.getBodega());
/* 1634:1679 */       dfc.setDescuento(BigDecimal.ZERO);
/* 1635:1680 */       dfc.setPorcentajeDescuento(BigDecimal.ZERO);
/* 1636:1682 */       if (devolucion.getEmpresa().getCliente().isExcentoImpuestos()) {
/* 1637:1683 */         dfc.setIndicadorImpuesto(false);
/* 1638:     */       } else {
/* 1639:1685 */         dfc.setIndicadorImpuesto(dfc.getProducto().isIndicadorImpuestos());
/* 1640:     */       }
/* 1641:1687 */       if (dfc.isIndicadorImpuesto()) {
/* 1642:     */         try
/* 1643:     */         {
/* 1644:1689 */           dfc.getProducto().setCategoriaImpuesto(this.servicioCategoriaImpuesto
/* 1645:1690 */             .cargarDetalle(dfc.getProducto().getCategoriaImpuesto().getIdCategoriaImpuesto()));
/* 1646:1691 */           this.servicioFacturaCliente.obtenerImpuestosProductos(dfc.getProducto(), dfc);
/* 1647:     */         }
/* 1648:     */         catch (ExcepcionAS2Inventario e)
/* 1649:     */         {
/* 1650:1693 */           throw new AS2Exception("Error al calcular impuestos en pre-devolucion");
/* 1651:     */         }
/* 1652:     */       }
/* 1653:1696 */       devolucion.getListaDetalleFacturaCliente().add(dfc);
/* 1654:     */     }
/* 1655:     */     try
/* 1656:     */     {
/* 1657:1699 */       totalizar(devolucion);
/* 1658:     */     }
/* 1659:     */     catch (ExcepcionAS2Ventas e)
/* 1660:     */     {
/* 1661:1701 */       LOG.error(e.getErrorMessage(e));
/* 1662:     */     }
/* 1663:     */     catch (Exception e)
/* 1664:     */     {
/* 1665:1703 */       LOG.error(e);
/* 1666:1704 */       e.printStackTrace();
/* 1667:     */     }
/* 1668:     */   }
/* 1669:     */   
/* 1670:     */   public void actualizarDetalleDevolucionDesdeDespacho(int idDespachoCliente, FacturaCliente devolucionCliente)
/* 1671:     */   {
/* 1672:1712 */     devolucionCliente.setListaDetalleFacturaCliente(new ArrayList());
/* 1673:     */     
/* 1674:     */ 
/* 1675:1715 */     List<DetalleDespachoCliente> listadfcPorDevolver = this.facturaClienteDao.obtenerDetalleDespachoCliente(idDespachoCliente);
/* 1676:1718 */     for (DetalleDespachoCliente dfcPorDevolver : listadfcPorDevolver)
/* 1677:     */     {
/* 1678:1720 */       DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/* 1679:1721 */       dfc.setDetalleDespachoClienteNoFacturado(dfcPorDevolver);
/* 1680:1722 */       dfc.setFacturaCliente(devolucionCliente);
/* 1681:1723 */       dfc.setProducto(dfcPorDevolver.getProducto());
/* 1682:1724 */       dfc.setUnidadVenta(dfcPorDevolver.getUnidadVenta());
/* 1683:1725 */       dfc.setIndicadorManejoPeso(dfcPorDevolver.isIndicadorManejoPeso());
/* 1684:     */       
/* 1685:1727 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 1686:1728 */       inventarioProducto.setDetalleDevolucionCliente(dfc);
/* 1687:1729 */       dfc.setInventarioProducto(inventarioProducto);
/* 1688:     */       
/* 1689:1731 */       dfc.setBodega(dfcPorDevolver.getBodega());
/* 1690:1733 */       if (dfcPorDevolver.getInventarioProducto() != null) {
/* 1691:1734 */         dfc.getInventarioProducto().setLote(dfcPorDevolver.getInventarioProducto().getLote());
/* 1692:     */       }
/* 1693:1736 */       if (devolucionCliente.getEmpresa().getCliente().isExcentoImpuestos()) {
/* 1694:1737 */         dfc.setIndicadorImpuesto(false);
/* 1695:     */       } else {
/* 1696:1739 */         dfc.setIndicadorImpuesto(dfc.getProducto().isIndicadorImpuestos());
/* 1697:     */       }
/* 1698:1741 */       devolucionCliente.getListaDetalleFacturaCliente().add(dfc);
/* 1699:     */     }
/* 1700:     */     try
/* 1701:     */     {
/* 1702:1745 */       totalizar(devolucionCliente);
/* 1703:     */     }
/* 1704:     */     catch (ExcepcionAS2Ventas e)
/* 1705:     */     {
/* 1706:1747 */       LOG.error(e.getErrorMessage(e));
/* 1707:     */     }
/* 1708:     */     catch (Exception e)
/* 1709:     */     {
/* 1710:1749 */       LOG.error(e);
/* 1711:1750 */       e.printStackTrace();
/* 1712:     */     }
/* 1713:     */   }
/* 1714:     */   
/* 1715:     */   private void asignarCodigoFormaPagoSRI(int idOrganizacion, FacturaClienteSRI facturaClienteSRI, Empresa empresa)
/* 1716:     */   {
/* 1717:1756 */     List<SelectItem> listaCodigoFormaPagoSRI = DatosSRI.getListaFormaPago(idOrganizacion);
/* 1718:1757 */     for (Iterator localIterator1 = this.servicioFormaPagoSRI.getListaFormaPagoSRI(empresa).iterator(); localIterator1.hasNext();)
/* 1719:     */     {
/* 1720:1757 */       formaPagoSRI = (FormaPagoSRI)localIterator1.next();
/* 1721:1758 */       for (SelectItem seleItem : listaCodigoFormaPagoSRI) {
/* 1722:1759 */         if (formaPagoSRI.getCodigo().equals(seleItem.getValue().toString()))
/* 1723:     */         {
/* 1724:1760 */           facturaClienteSRI.setCodigoFormaPagoSRI(formaPagoSRI.getCodigo());
/* 1725:1761 */           break;
/* 1726:     */         }
/* 1727:     */       }
/* 1728:     */     }
/* 1729:     */     FormaPagoSRI formaPagoSRI;
/* 1730:1765 */     if (facturaClienteSRI.getCodigoFormaPagoSRI() == null)
/* 1731:     */     {
/* 1732:1766 */       localIterator1 = listaCodigoFormaPagoSRI.iterator();
/* 1733:1766 */       if (localIterator1.hasNext())
/* 1734:     */       {
/* 1735:1766 */         SelectItem seleItem = (SelectItem)localIterator1.next();
/* 1736:1767 */         facturaClienteSRI.setCodigoFormaPagoSRI(seleItem.getValue().toString());
/* 1737:     */       }
/* 1738:     */     }
/* 1739:     */   }
/* 1740:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioNotaCreditoClienteImpl
 * JD-Core Version:    0.7.0.1
 */