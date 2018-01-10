/*    1:     */ package com.asinfo.as2.combustibles.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.FacturaComercializadora;
/*    4:     */ import com.asinfo.as2.combustibles.ServicioCargaProcesoCombustibles;
/*    5:     */ import com.asinfo.as2.compras.configuracion.servicio.impl.ServicioTipoOperacion;
/*    6:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    7:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*    9:     */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*   10:     */ import com.asinfo.as2.dao.sri.TipoComprobanteSRIDao;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   12:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   13:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   14:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   15:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   16:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   17:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   18:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   19:     */ import com.asinfo.as2.entities.Cliente;
/*   20:     */ import com.asinfo.as2.entities.CondicionPago;
/*   21:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   22:     */ import com.asinfo.as2.entities.DetalleFacturaClienteComercializadora;
/*   23:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   24:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   25:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   26:     */ import com.asinfo.as2.entities.Documento;
/*   27:     */ import com.asinfo.as2.entities.Empresa;
/*   28:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   29:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   30:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   31:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*   32:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   33:     */ import com.asinfo.as2.entities.Organizacion;
/*   34:     */ import com.asinfo.as2.entities.Producto;
/*   35:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   36:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   37:     */ import com.asinfo.as2.entities.RecargoListaPreciosCliente;
/*   38:     */ import com.asinfo.as2.entities.Secuencia;
/*   39:     */ import com.asinfo.as2.entities.Sucursal;
/*   40:     */ import com.asinfo.as2.entities.TipoOperacion;
/*   41:     */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*   42:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   43:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   44:     */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*   45:     */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*   46:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   47:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   48:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   49:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   50:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   51:     */ import com.asinfo.as2.enumeraciones.TipoImpuestoCombustibleEnum;
/*   52:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   53:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   54:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*   55:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*   56:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI;
/*   57:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   58:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*   59:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/*   60:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*   61:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   62:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   63:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   64:     */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*   65:     */ import com.asinfo.as2.util.AppUtil;
/*   66:     */ import com.asinfo.as2.utils.DatosSRI;
/*   67:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   68:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   69:     */ import com.asinfo.as2.utils.encriptacion.AESEncriptacion;
/*   70:     */ import com.asinfo.as2.utils.encriptacion.AESKey;
/*   71:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   72:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   73:     */ import java.io.File;
/*   74:     */ import java.io.FileInputStream;
/*   75:     */ import java.io.IOException;
/*   76:     */ import java.io.InputStream;
/*   77:     */ import java.math.BigDecimal;
/*   78:     */ import java.math.RoundingMode;
/*   79:     */ import java.util.ArrayList;
/*   80:     */ import java.util.Collections;
/*   81:     */ import java.util.Comparator;
/*   82:     */ import java.util.Date;
/*   83:     */ import java.util.HashMap;
/*   84:     */ import java.util.List;
/*   85:     */ import java.util.Map;
/*   86:     */ import java.util.Properties;
/*   87:     */ import javax.ejb.EJB;
/*   88:     */ import javax.ejb.SessionContext;
/*   89:     */ import javax.ejb.Stateless;
/*   90:     */ import javax.ejb.TransactionAttribute;
/*   91:     */ import javax.ejb.TransactionAttributeType;
/*   92:     */ import javax.faces.model.SelectItem;
/*   93:     */ import org.apache.log4j.Logger;
/*   94:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   95:     */ 
/*   96:     */ @Stateless
/*   97:     */ public class ServicioCargaProcesoCombustiblesImpl
/*   98:     */   extends AbstractServicioAS2Financiero
/*   99:     */   implements ServicioCargaProcesoCombustibles
/*  100:     */ {
/*  101:     */   private static final long serialVersionUID = 1L;
/*  102:     */   @EJB
/*  103:     */   private transient ServicioEmpresa servicioEmpresa;
/*  104:     */   @EJB
/*  105:     */   private transient DireccionEmpresaDao direccionEmpresaDao;
/*  106:     */   @EJB
/*  107:     */   private transient ServicioProducto servicioProducto;
/*  108:     */   @EJB
/*  109:     */   private transient ServicioCondicionPago servicioCondicionPago;
/*  110:     */   @EJB
/*  111:     */   private transient ServicioImpuesto servicioImpuesto;
/*  112:     */   @EJB
/*  113:     */   private transient ServicioDocumento servicioDocumento;
/*  114:     */   @EJB
/*  115:     */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  116:     */   @EJB
/*  117:     */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  118:     */   @EJB
/*  119:     */   private transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  120:     */   @EJB
/*  121:     */   private transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  122:     */   @EJB
/*  123:     */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  124:     */   @EJB
/*  125:     */   private transient ServicioListaPrecios servicioListaPrecios;
/*  126:     */   @EJB
/*  127:     */   private transient ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*  128:     */   @EJB
/*  129:     */   private transient ServicioCreditoTributario servicioCreditoTributario;
/*  130:     */   @EJB
/*  131:     */   private transient ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  132:     */   @EJB
/*  133:     */   private transient ServicioSRI servicioSRI;
/*  134:     */   @EJB
/*  135:     */   private transient TipoComprobanteSRIDao tipoComprobanteSRIDao;
/*  136:     */   @EJB
/*  137:     */   private transient ServicioAnuladoSRI servicioAnuladoSRI;
/*  138:     */   @EJB
/*  139:     */   private transient ServicioTipoOperacion servicioTipoOperacion;
/*  140:     */   @EJB
/*  141:     */   private transient ServicioSecuencia servicioSecuencia;
/*  142:     */   @EJB
/*  143:     */   protected ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  144:     */   
/*  145:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  146:     */   public void cargarFacturasBancoInternacionalMasgas(String fileName, InputStream imInputStream, Estado estado, boolean encriptado)
/*  147:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  148:     */   {
/*  149: 161 */     if (encriptado) {
/*  150: 162 */       cargarFacturasBancoInternacionalMasgasEncriptado(fileName, imInputStream, estado);
/*  151:     */     } else {
/*  152: 164 */       cargarFacturasBancoInternacionalMasgasExcel(fileName, imInputStream, estado);
/*  153:     */     }
/*  154:     */   }
/*  155:     */   
/*  156:     */   private void cargarFacturasBancoInternacionalMasgasEncriptado(String fileName, InputStream imInputStream, Estado estado)
/*  157:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  158:     */   {
/*  159: 181 */     List<String[]> datos = AESEncriptacion.desencriptaBancoInternacional(fileName, imInputStream);
/*  160:     */     
/*  161: 183 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/*  162: 184 */     HashMap<String, Producto> hmProducto = new HashMap();
/*  163:     */     
/*  164:     */ 
/*  165: 187 */     List<FacturaComercializadora> listaFacturaComercializadora = new ArrayList();
/*  166: 190 */     for (int i = 1; i < datos.size(); i++)
/*  167:     */     {
/*  168: 192 */       String[] linea = (String[])datos.get(i);
/*  169:     */       
/*  170: 194 */       String codigoCliente = linea[2];
/*  171: 195 */       for (int j = codigoCliente.length() + 1; j <= 8; j++) {
/*  172: 196 */         codigoCliente = "0" + codigoCliente;
/*  173:     */       }
/*  174: 199 */       String[] factura = linea[22].split("-");
/*  175: 200 */       String autorizacion = (linea.length >= 29) && (linea[29] != null) ? linea[29] : linea[25];
/*  176: 201 */       Date fechaFactura = FuncionesUtiles.stringAFecha(linea[5]);
/*  177: 202 */       Date fechaVencimiento = FuncionesUtiles.stringAFecha(linea[7]);
/*  178: 203 */       BigDecimal valorTotal = BigDecimal.valueOf(Double.parseDouble(linea[16]));
/*  179: 204 */       BigDecimal cantidad = BigDecimal.valueOf(Double.parseDouble(linea[10]));
/*  180:     */       
/*  181: 206 */       String codigoProducto = linea[8];
/*  182:     */       
/*  183: 208 */       Integer codigo = Integer.valueOf(Integer.parseInt(codigoProducto.substring(codigoProducto.length() - 2, codigoProducto.length())));
/*  184: 209 */       codigoProducto = codigo.toString();
/*  185:     */       
/*  186: 211 */       String establecimiento = factura[0];
/*  187: 212 */       String puntoVenta = factura[1];
/*  188: 213 */       String numeroFactura = factura[2];
/*  189: 215 */       if (linea[18].equalsIgnoreCase("A")) {
/*  190: 216 */         estado = Estado.ANULADO;
/*  191:     */       }
/*  192: 222 */       Empresa empresa = (Empresa)hmEmpresa.get(codigoCliente);
/*  193: 223 */       if (empresa == null)
/*  194:     */       {
/*  195: 224 */         Map<String, String> filters = new HashMap();
/*  196: 225 */         filters.put("codigoAlterno", String.valueOf(codigoCliente));
/*  197: 226 */         filters.put("indicadorCliente", "true");
/*  198: 227 */         List<Empresa> listaEmpresa = this.servicioEmpresa.obtenerListaCombo("nombreComercial", true, filters);
/*  199: 228 */         if (listaEmpresa.size() > 0)
/*  200:     */         {
/*  201: 229 */           empresa = (Empresa)listaEmpresa.get(0);
/*  202: 230 */           hmEmpresa.put(codigoCliente, empresa);
/*  203:     */         }
/*  204:     */         else
/*  205:     */         {
/*  206: 232 */           throw new ExcepcionAS2("msg_info_empresa_no_encontrada", String.valueOf(codigoCliente));
/*  207:     */         }
/*  208:     */       }
/*  209: 238 */       Producto producto = (Producto)hmProducto.get(codigoProducto);
/*  210: 239 */       if (producto == null)
/*  211:     */       {
/*  212: 240 */         Map<String, String> filters = new HashMap();
/*  213: 241 */         filters.put("codigo", codigoProducto);
/*  214: 242 */         List<Producto> listaProducto = this.servicioProducto.obtenerListaCombo("codigo", true, filters);
/*  215: 243 */         if (listaProducto.size() > 0)
/*  216:     */         {
/*  217: 244 */           producto = (Producto)listaProducto.get(0);
/*  218: 245 */           hmProducto.put(codigoProducto, producto);
/*  219:     */         }
/*  220:     */         else
/*  221:     */         {
/*  222: 247 */           throw new ExcepcionAS2("msg_producto_no_encontrado", codigoProducto);
/*  223:     */         }
/*  224:     */       }
/*  225: 251 */       FacturaComercializadora facturaComercializadora = new FacturaComercializadora(establecimiento, puntoVenta, autorizacion, numeroFactura, empresa, fechaFactura, fechaVencimiento, producto, cantidad, estado, valorTotal);
/*  226:     */       
/*  227: 253 */       listaFacturaComercializadora.add(facturaComercializadora);
/*  228:     */     }
/*  229: 256 */     generarFacturasComercializadoras(listaFacturaComercializadora);
/*  230:     */   }
/*  231:     */   
/*  232:     */   private void cargarFacturasBancoInternacionalMasgasExcel(String fileName, InputStream imInputStream, Estado estado)
/*  233:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  234:     */   {
/*  235: 264 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/*  236: 265 */     HashMap<String, Producto> hmProducto = new HashMap();
/*  237:     */     
/*  238: 267 */     int filaActual = 0;
/*  239: 268 */     int columnaActual = 0;
/*  240:     */     try
/*  241:     */     {
/*  242: 272 */       datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, imInputStream, 0, 0);
/*  243:     */     }
/*  244:     */     catch (IOException e1)
/*  245:     */     {
/*  246:     */       HSSFCell[][] datos;
/*  247: 274 */       throw new ExcepcionAS2("msg_error_archivo_generado");
/*  248:     */     }
/*  249:     */     HSSFCell[][] datos;
/*  250: 276 */     List<FacturaComercializadora> listaFacturaComercializadora = new ArrayList();
/*  251: 277 */     for (HSSFCell[] fila : datos)
/*  252:     */     {
/*  253:     */       try
/*  254:     */       {
/*  255: 289 */         int fechaFacturaString = (int)fila[(columnaActual = 0)].getNumericCellValue();
/*  256: 290 */         BigDecimal cantidad = BigDecimal.valueOf(fila[(columnaActual = 1)].getNumericCellValue());
/*  257: 291 */         String nombreProducto = fila[(columnaActual = 2)].getStringCellValue();
/*  258: 292 */         String codigoCliente = fila[(columnaActual = 3)].getStringCellValue();
/*  259: 293 */         String factura = fila[(columnaActual = 4)].getStringCellValue();
/*  260: 294 */         BigDecimal valorTotal = BigDecimal.valueOf(fila[(columnaActual = 6)].getNumericCellValue());
/*  261: 295 */         fechaVencimientoString = (int)fila[(columnaActual = 8)].getNumericCellValue();
/*  262:     */       }
/*  263:     */       catch (IllegalArgumentException e)
/*  264:     */       {
/*  265:     */         int fechaVencimientoString;
/*  266: 297 */         this.context.setRollbackOnly();
/*  267: 298 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1));
/*  268:     */       }
/*  269:     */       catch (Exception e)
/*  270:     */       {
/*  271: 300 */         this.context.setRollbackOnly();
/*  272: 301 */         e.printStackTrace();
/*  273: 302 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1));
/*  274:     */       }
/*  275:     */       int fechaVencimientoString;
/*  276:     */       BigDecimal valorTotal;
/*  277:     */       String factura;
/*  278:     */       String codigoCliente;
/*  279:     */       String nombreProducto;
/*  280:     */       BigDecimal cantidad;
/*  281:     */       int fechaFacturaString;
/*  282: 308 */       Empresa empresa = (Empresa)hmEmpresa.get(codigoCliente);
/*  283: 309 */       if (empresa == null)
/*  284:     */       {
/*  285: 310 */         Map<String, String> filters = new HashMap();
/*  286: 311 */         filters.put("codigoAlterno", codigoCliente);
/*  287: 312 */         filters.put("indicadorCliente", "true");
/*  288: 313 */         List<Empresa> listaEmpresa = this.servicioEmpresa.obtenerListaCombo("nombreComercial", true, filters);
/*  289: 314 */         if (listaEmpresa.size() > 0)
/*  290:     */         {
/*  291: 315 */           empresa = (Empresa)listaEmpresa.get(0);
/*  292: 316 */           hmEmpresa.put(codigoCliente, empresa);
/*  293:     */         }
/*  294:     */         else
/*  295:     */         {
/*  296: 318 */           throw new ExcepcionAS2("msg_info_empresa_no_encontrada", codigoCliente);
/*  297:     */         }
/*  298:     */       }
/*  299: 324 */       Producto producto = (Producto)hmProducto.get(nombreProducto);
/*  300: 325 */       if (producto == null)
/*  301:     */       {
/*  302: 326 */         Map<String, String> filters = new HashMap();
/*  303: 327 */         filters.put("nombre", nombreProducto);
/*  304: 328 */         List<Producto> listaProducto = this.servicioProducto.obtenerListaCombo("nombre", true, filters);
/*  305: 329 */         if (listaProducto.size() > 0)
/*  306:     */         {
/*  307: 330 */           producto = (Producto)listaProducto.get(0);
/*  308: 331 */           hmProducto.put(nombreProducto, producto);
/*  309:     */         }
/*  310:     */         else
/*  311:     */         {
/*  312: 333 */           throw new ExcepcionAS2("msg_producto_no_encontrado", nombreProducto);
/*  313:     */         }
/*  314:     */       }
/*  315: 336 */       String establecimiento = factura.substring(0, 3);
/*  316: 337 */       String puntoVenta = factura.substring(3, 6);
/*  317: 338 */       String numeroFactura = factura.substring(6);
/*  318: 339 */       String autorizacion = "";
/*  319: 340 */       Date fechaFactura = FuncionesUtiles.stringAFecha(String.valueOf(fechaFacturaString));
/*  320: 341 */       Date fechaVencimiento = FuncionesUtiles.stringAFecha(String.valueOf(fechaVencimientoString));
/*  321: 342 */       FacturaComercializadora facturaComercializadora = new FacturaComercializadora(establecimiento, puntoVenta, autorizacion, numeroFactura, empresa, fechaFactura, fechaVencimiento, producto, cantidad, estado, valorTotal);
/*  322:     */       
/*  323: 344 */       listaFacturaComercializadora.add(facturaComercializadora);
/*  324: 345 */       filaActual++;
/*  325:     */     }
/*  326:     */     try
/*  327:     */     {
/*  328: 348 */       generarFacturasComercializadoras(listaFacturaComercializadora);
/*  329:     */     }
/*  330:     */     catch (ExcepcionAS2 e)
/*  331:     */     {
/*  332: 350 */       this.context.setRollbackOnly();
/*  333: 351 */       throw new ExcepcionAS2(e.getCodigoExcepcion(), e.getMessage());
/*  334:     */     }
/*  335:     */     catch (Exception e)
/*  336:     */     {
/*  337: 353 */       e.printStackTrace();
/*  338: 354 */       throw new ExcepcionAS2(e);
/*  339:     */     }
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void cargarFacturasBancoGuayaquilMasgas(String fileName, InputStream imInputStream, boolean encriptado)
/*  343:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  344:     */   {
/*  345: 364 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/*  346: 365 */     HashMap<String, Producto> hmProducto = new HashMap();
/*  347:     */     try
/*  348:     */     {
/*  349: 369 */       lista = FuncionesUtiles.leerArchivoTexto(imInputStream);
/*  350:     */     }
/*  351:     */     catch (IOException e1)
/*  352:     */     {
/*  353:     */       List<String> lista;
/*  354: 371 */       throw new ExcepcionAS2("msg_error_archivo_generado");
/*  355:     */     }
/*  356:     */     List<String> lista;
/*  357: 374 */     List<FacturaComercializadora> listaFacturaComercializadora = new ArrayList();
/*  358: 375 */     String lineaDesencriptada = "";
/*  359: 376 */     String estatus = "";
/*  360: 377 */     String factura = "";
/*  361: 378 */     String codigoCliente = "";
/*  362: 379 */     Date fechaFactura = null;
/*  363: 380 */     Date fechaVencimiento = null;
/*  364: 381 */     BigDecimal cantidad = BigDecimal.ZERO;
/*  365:     */     
/*  366: 383 */     BigDecimal valorTotal = BigDecimal.ZERO;
/*  367: 384 */     AESKey aesKey = new AESKey();
/*  368:     */     
/*  369: 386 */     Properties prop = new Properties();
/*  370: 387 */     InputStream is = null;
/*  371:     */     try
/*  372:     */     {
/*  373: 389 */       String as2Home = System.getenv("AS2_HOME");
/*  374: 390 */       File config = new File(as2Home + File.separator + "config" + File.separator + "configuracion.properties");
/*  375: 391 */       is = new FileInputStream(config);
/*  376: 392 */       prop.load(is);
/*  377:     */     }
/*  378:     */     catch (IOException ioe)
/*  379:     */     {
/*  380: 394 */       throw new ExcepcionAS2("msg_error_archivo_configuracion_no_encontrado");
/*  381:     */     }
/*  382: 397 */     aesKey.setEncoded(prop.getProperty("claveBancoGuayaquil"));
/*  383: 399 */     for (String string : lista)
/*  384:     */     {
/*  385: 400 */       lineaDesencriptada = FuncionesUtiles.desEncriptar(string, aesKey);
/*  386: 402 */       if (lineaDesencriptada != null) {
/*  387: 406 */         if (lineaDesencriptada.substring(8, 9).compareTo("C") == 0)
/*  388:     */         {
/*  389: 407 */           factura = lineaDesencriptada.substring(95, 108);
/*  390:     */           
/*  391: 409 */           codigoCliente = lineaDesencriptada.substring(17, 25);
/*  392: 410 */           for (int j = codigoCliente.length() + 1; j <= 8; j++) {
/*  393: 411 */             codigoCliente = "0" + codigoCliente;
/*  394:     */           }
/*  395: 414 */           fechaFactura = FuncionesUtiles.stringAFecha(lineaDesencriptada.substring(69, 77));
/*  396: 415 */           fechaVencimiento = FuncionesUtiles.stringAFecha(lineaDesencriptada.substring(77, 85));
/*  397: 416 */           valorTotal = BigDecimal.valueOf(Double.parseDouble(lineaDesencriptada.substring(52, 69)));
/*  398: 417 */           estatus = lineaDesencriptada.substring(108, 118);
/*  399:     */         }
/*  400: 419 */         else if (lineaDesencriptada.substring(75, 76).compareTo(" ") == 0)
/*  401:     */         {
/*  402: 420 */           cantidad = BigDecimal.valueOf(Double.parseDouble(lineaDesencriptada.substring(32, 45)));
/*  403:     */           
/*  404: 422 */           String codigoProducto = lineaDesencriptada.substring(28, 32);
/*  405:     */           
/*  406:     */ 
/*  407: 425 */           Integer codigo = Integer.valueOf(Integer.parseInt(codigoProducto.substring(codigoProducto.length() - 2, codigoProducto.length())));
/*  408: 426 */           codigoProducto = codigo.toString();
/*  409:     */           
/*  410:     */ 
/*  411:     */ 
/*  412:     */ 
/*  413: 431 */           Empresa empresa = (Empresa)hmEmpresa.get(codigoCliente);
/*  414: 432 */           if (empresa == null)
/*  415:     */           {
/*  416: 433 */             Map<String, String> filters = new HashMap();
/*  417: 434 */             filters.put("codigoAlterno", String.valueOf(codigoCliente));
/*  418: 435 */             filters.put("indicadorCliente", "true");
/*  419: 436 */             List<Empresa> listaEmpresa = this.servicioEmpresa.obtenerListaCombo("nombreComercial", true, filters);
/*  420: 437 */             if (listaEmpresa.size() > 0)
/*  421:     */             {
/*  422: 438 */               empresa = (Empresa)listaEmpresa.get(0);
/*  423: 439 */               hmEmpresa.put(codigoCliente, empresa);
/*  424:     */             }
/*  425:     */             else
/*  426:     */             {
/*  427: 441 */               throw new ExcepcionAS2("msg_info_empresa_no_encontrada", String.valueOf(codigoCliente));
/*  428:     */             }
/*  429:     */           }
/*  430: 447 */           Producto producto = (Producto)hmProducto.get(codigoProducto);
/*  431: 448 */           if (producto == null)
/*  432:     */           {
/*  433: 449 */             Map<String, String> filters = new HashMap();
/*  434: 450 */             filters.put("codigo", codigoProducto);
/*  435: 451 */             List<Producto> listaProducto = this.servicioProducto.obtenerListaCombo("codigo", true, filters);
/*  436: 452 */             if (listaProducto.size() > 0)
/*  437:     */             {
/*  438: 453 */               producto = (Producto)listaProducto.get(0);
/*  439: 454 */               hmProducto.put(codigoProducto, producto);
/*  440:     */             }
/*  441:     */             else
/*  442:     */             {
/*  443: 456 */               throw new ExcepcionAS2("msg_producto_no_encontrado", codigoProducto);
/*  444:     */             }
/*  445:     */           }
/*  446: 459 */           String establecimiento = factura.substring(0, 3);
/*  447: 460 */           String puntoVenta = factura.substring(3, 6);
/*  448: 461 */           String numeroFactura = factura.substring(6);
/*  449: 462 */           String autorizacion = "";
/*  450: 463 */           Estado estado = estatus.trim().equals("ELIMINADA") ? Estado.ANULADO : Estado.PROCESADO;
/*  451: 464 */           FacturaComercializadora facturaComercializadora = new FacturaComercializadora(establecimiento, puntoVenta, autorizacion, numeroFactura, empresa, fechaFactura, fechaVencimiento, producto, cantidad, estado, valorTotal);
/*  452:     */           
/*  453: 466 */           listaFacturaComercializadora.add(facturaComercializadora);
/*  454:     */         }
/*  455:     */       }
/*  456:     */     }
/*  457: 472 */     generarFacturasComercializadoras(listaFacturaComercializadora);
/*  458:     */   }
/*  459:     */   
/*  460:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  461:     */   public void cargarFacturasBancoGeneralRuminahuiPichinchaMasgas(String fileName, InputStream imInputStream, boolean encriptado)
/*  462:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  463:     */   {
/*  464: 482 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/*  465: 483 */     HashMap<String, Producto> hmProducto = new HashMap();
/*  466:     */     
/*  467: 485 */     int filaActual = 1;
/*  468: 486 */     int columnaActual = 0;
/*  469:     */     try
/*  470:     */     {
/*  471: 490 */       datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, imInputStream, 3, 0);
/*  472:     */     }
/*  473:     */     catch (IOException e1)
/*  474:     */     {
/*  475:     */       HSSFCell[][] datos;
/*  476: 492 */       throw new ExcepcionAS2("msg_error_archivo_generado");
/*  477:     */     }
/*  478:     */     HSSFCell[][] datos;
/*  479: 494 */     List<FacturaComercializadora> listaFacturaComercializadora = new ArrayList();
/*  480: 495 */     for (HSSFCell[] fila : datos)
/*  481:     */     {
/*  482:     */       try
/*  483:     */       {
/*  484: 512 */         String factura = fila[(columnaActual = 2)].getStringCellValue().replace("'", "");
/*  485: 513 */         BigDecimal valorTotal = BigDecimal.valueOf(fila[(columnaActual = 3)].getNumericCellValue());
/*  486: 514 */         String codigoCliente = fila[(columnaActual = 5)].getStringCellValue().replace("'", "");
/*  487: 515 */         Date fechaFactura = FuncionesUtiles.stringToDate(fila[(columnaActual = 9)].getStringCellValue().replace("'", ""), "dd/MM/yyyy");
/*  488: 516 */         Date fechaVencimiento = FuncionesUtiles.stringToDate(fila[(columnaActual = 11)].getStringCellValue().replace("'", ""), "dd/MM/yyyy");
/*  489: 517 */         String estatus = fila[(columnaActual = 19)].getStringCellValue().replace("'", "");
/*  490: 518 */         String codigoProducto = fila[(columnaActual = 20)].getStringCellValue();
/*  491: 519 */         BigDecimal cantidad = BigDecimal.valueOf(fila[(columnaActual = 21)].getNumericCellValue());
/*  492: 520 */         String autorizacion = fila[(columnaActual = 28)] == null ? "" : fila[(columnaActual = 28)].getStringCellValue();
/*  493: 522 */         for (int i = codigoCliente.length() + 1; i <= 8; i++) {
/*  494: 523 */           codigoCliente = "0" + codigoCliente;
/*  495:     */         }
/*  496:     */       }
/*  497:     */       catch (IllegalArgumentException e)
/*  498:     */       {
/*  499: 527 */         this.context.setRollbackOnly();
/*  500: 528 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1));
/*  501:     */       }
/*  502:     */       catch (Exception e)
/*  503:     */       {
/*  504: 530 */         this.context.setRollbackOnly();
/*  505: 531 */         e.printStackTrace();
/*  506: 532 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1));
/*  507:     */       }
/*  508:     */       String autorizacion;
/*  509:     */       BigDecimal cantidad;
/*  510:     */       String codigoProducto;
/*  511:     */       String estatus;
/*  512:     */       Date fechaVencimiento;
/*  513:     */       Date fechaFactura;
/*  514:     */       String codigoCliente;
/*  515:     */       BigDecimal valorTotal;
/*  516:     */       String factura;
/*  517: 538 */       Empresa empresa = (Empresa)hmEmpresa.get(codigoCliente);
/*  518: 539 */       if (empresa == null)
/*  519:     */       {
/*  520: 540 */         Map<String, String> filters = new HashMap();
/*  521: 541 */         filters.put("codigoAlterno", codigoCliente);
/*  522: 542 */         filters.put("indicadorCliente", "true");
/*  523: 543 */         List<Empresa> listaEmpresa = this.servicioEmpresa.obtenerListaCombo("nombreComercial", true, filters);
/*  524: 544 */         if (listaEmpresa.size() > 0)
/*  525:     */         {
/*  526: 545 */           empresa = (Empresa)listaEmpresa.get(0);
/*  527: 546 */           hmEmpresa.put(codigoCliente, empresa);
/*  528:     */         }
/*  529:     */         else
/*  530:     */         {
/*  531: 548 */           throw new ExcepcionAS2("msg_info_empresa_no_encontrada", codigoCliente);
/*  532:     */         }
/*  533:     */       }
/*  534: 554 */       Producto producto = (Producto)hmProducto.get(codigoProducto);
/*  535: 555 */       if (producto == null)
/*  536:     */       {
/*  537: 556 */         Map<String, String> filters = new HashMap();
/*  538: 557 */         filters.put("codigo", codigoProducto);
/*  539: 558 */         List<Producto> listaProducto = this.servicioProducto.obtenerListaCombo("codigo", true, filters);
/*  540: 559 */         if (listaProducto.size() > 0)
/*  541:     */         {
/*  542: 560 */           producto = (Producto)listaProducto.get(0);
/*  543: 561 */           hmProducto.put(codigoProducto, producto);
/*  544:     */         }
/*  545:     */         else
/*  546:     */         {
/*  547: 563 */           throw new ExcepcionAS2("msg_producto_no_encontrado", " " + codigoProducto);
/*  548:     */         }
/*  549:     */       }
/*  550: 566 */       String establecimiento = factura.substring(0, 3);
/*  551: 567 */       String puntoVenta = factura.substring(4, 7);
/*  552: 568 */       String numeroFactura = factura.substring(8);
/*  553: 569 */       Estado estado = estatus.compareTo("0001") == 0 ? Estado.PROCESADO : Estado.ANULADO;
/*  554: 570 */       FacturaComercializadora facturaComercializadora = new FacturaComercializadora(establecimiento, puntoVenta, autorizacion, numeroFactura, empresa, fechaFactura, fechaVencimiento, producto, cantidad, estado, valorTotal);
/*  555:     */       
/*  556: 572 */       listaFacturaComercializadora.add(facturaComercializadora);
/*  557: 573 */       filaActual++;
/*  558:     */     }
/*  559: 575 */     generarFacturasComercializadoras(listaFacturaComercializadora);
/*  560:     */   }
/*  561:     */   
/*  562:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  563:     */   public void cargarFacturaUnica(String fileName, InputStream imInputStream, int filaInicial)
/*  564:     */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  565:     */   {
/*  566: 583 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/*  567: 584 */     HashMap<String, DireccionEmpresa> hmDireccionEmpresa = new HashMap();
/*  568: 585 */     HashMap<String, Producto> hmProducto = new HashMap();
/*  569: 586 */     Map<Integer, CondicionPago> hmCondicionPago = new HashMap();
/*  570: 587 */     HashMap<Date, RangoImpuesto> hmRangoImpuesto = new HashMap();
/*  571: 588 */     HashMap<String, TipoOperacion> hmTipoOperacion = new HashMap();
/*  572: 589 */     HashMap<String, FacturaProveedor> hmFacturaProveedo = new HashMap();
/*  573: 590 */     BigDecimal tolerancia = ParametrosSistema.getValorToleranciaCompraVenta(AppUtil.getOrganizacion().getId());
/*  574: 591 */     CreditoTributarioSRI creditoTributarioSRI = this.servicioCreditoTributario.buscarPorCodigo("06");
/*  575:     */     try
/*  576:     */     {
/*  577: 596 */       datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, imInputStream, filaInicial, 0);
/*  578:     */     }
/*  579:     */     catch (IOException e1)
/*  580:     */     {
/*  581:     */       HSSFCell[][] datos;
/*  582: 598 */       this.context.setRollbackOnly();
/*  583: 599 */       throw new ExcepcionAS2("msg_error_archivo_generado");
/*  584:     */     }
/*  585:     */     HSSFCell[][] datos;
/*  586: 614 */     Documento documento = null;
/*  587:     */     
/*  588:     */ 
/*  589: 617 */     int filaActual = filaInicial + 1;
/*  590: 618 */     int columnaActual = 0;
/*  591:     */     
/*  592:     */ 
/*  593: 621 */     HSSFCell[] filaErronea = new HSSFCell[0];
/*  594: 622 */     for (HSSFCell[] fila : datos)
/*  595:     */     {
/*  596: 625 */       filaErronea = fila;
/*  597:     */       try
/*  598:     */       {
/*  599: 629 */         columnaActual = 0;
/*  600: 630 */         String factura = fila[0].getStringCellValue().trim();
/*  601:     */         
/*  602: 632 */         columnaActual = 1;
/*  603: 633 */         String identificacionProveedor = fila[1].getStringCellValue().trim();
/*  604:     */         
/*  605: 635 */         columnaActual = 2;
/*  606: 636 */         Date fechaFactura = fila[2].getDateCellValue();
/*  607:     */         
/*  608: 638 */         columnaActual = 3;
/*  609: 639 */         Date fechaVencimiento = fila[3].getDateCellValue();
/*  610:     */         
/*  611: 641 */         columnaActual = 4;
/*  612: 642 */         String autorizacion = fila[4] == null ? "" : fila[4].getStringCellValue().trim();
/*  613:     */         
/*  614: 644 */         columnaActual = 5;
/*  615: 645 */         String nota = fila[5].getStringCellValue().trim();
/*  616:     */         
/*  617: 647 */         columnaActual = 6;
/*  618: 648 */         int numeroReferencia = (int)fila[6].getNumericCellValue();
/*  619:     */         
/*  620: 650 */         columnaActual = 7;
/*  621: 651 */         String codigoProducto = fila[7].getStringCellValue().trim();
/*  622:     */         
/*  623: 653 */         columnaActual = 8;
/*  624: 654 */         BigDecimal cantidad = BigDecimal.valueOf(fila[8].getNumericCellValue());
/*  625:     */         
/*  626: 656 */         columnaActual = 9;
/*  627: 657 */         BigDecimal valorTotal = FuncionesUtiles.redondearBigDecimal(BigDecimal.valueOf(fila[9].getNumericCellValue()), 2);
/*  628:     */         
/*  629: 659 */         columnaActual = 10;
/*  630: 660 */         codigoTipoOperacion = fila[10] == null ? "" : fila[10].getStringCellValue();
/*  631:     */       }
/*  632:     */       catch (IllegalArgumentException e)
/*  633:     */       {
/*  634:     */         String codigoTipoOperacion;
/*  635: 663 */         this.context.setRollbackOnly();
/*  636:     */         
/*  637: 665 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/*  638:     */       }
/*  639:     */       catch (Exception e)
/*  640:     */       {
/*  641: 667 */         this.context.setRollbackOnly();
/*  642: 668 */         e.printStackTrace();
/*  643:     */         
/*  644: 670 */         throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaActual + 1) + " Dato: " + filaErronea[columnaActual].toString());
/*  645:     */       }
/*  646:     */       try
/*  647:     */       {
/*  648:     */         String codigoTipoOperacion;
/*  649:     */         BigDecimal valorTotal;
/*  650:     */         BigDecimal cantidad;
/*  651:     */         String codigoProducto;
/*  652:     */         int numeroReferencia;
/*  653:     */         String nota;
/*  654:     */         String autorizacion;
/*  655:     */         Date fechaVencimiento;
/*  656:     */         Date fechaFactura;
/*  657:     */         String identificacionProveedor;
/*  658:     */         String factura;
/*  659: 679 */         Empresa empresa = (Empresa)hmEmpresa.get(identificacionProveedor);
/*  660: 680 */         if (empresa == null)
/*  661:     */         {
/*  662: 681 */           Map<String, String> filters = new HashMap();
/*  663: 682 */           filters.put("identificacion", identificacionProveedor);
/*  664: 683 */           filters.put("indicadorProveedor", "true");
/*  665: 684 */           empresa = this.servicioEmpresa.bucarEmpresaPorIdentificacion(filters);
/*  666: 685 */           empresa = this.servicioEmpresa.cargarDetalle(empresa);
/*  667: 686 */           hmEmpresa.put(identificacionProveedor, empresa);
/*  668:     */         }
/*  669: 692 */         DireccionEmpresa direccionEmpresa = (DireccionEmpresa)hmDireccionEmpresa.get(identificacionProveedor);
/*  670: 693 */         if (direccionEmpresa == null)
/*  671:     */         {
/*  672: 694 */           direccionEmpresa = this.direccionEmpresaDao.buscarPorEmpresa(empresa);
/*  673: 695 */           hmDireccionEmpresa.put(identificacionProveedor, direccionEmpresa);
/*  674:     */         }
/*  675: 701 */         if (documento == null)
/*  676:     */         {
/*  677: 702 */           Map<String, String> filters = new HashMap();
/*  678: 703 */           filters.put("documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
/*  679: 704 */           filters.put("indicadorDocumentoTributario", "true");
/*  680: 705 */           List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("nombre", true, filters);
/*  681: 706 */           if (listaDocumento.isEmpty()) {
/*  682: 707 */             throw new ExcepcionAS2("msg_configuracion_documento", " " + DocumentoBase.FACTURA_PROVEEDOR.getNombre());
/*  683:     */           }
/*  684: 709 */           documento = (Documento)listaDocumento.get(0);
/*  685:     */         }
/*  686: 716 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/*  687: 717 */         if (producto == null)
/*  688:     */         {
/*  689: 718 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  690: 719 */           hmProducto.put(codigoProducto, producto);
/*  691:     */         }
/*  692: 725 */         Integer plazo = Integer.valueOf(FuncionesUtiles.diferenciasDeFechas(fechaFactura, fechaVencimiento));
/*  693: 726 */         CondicionPago condicionPago = (CondicionPago)hmCondicionPago.get(plazo);
/*  694: 727 */         if (condicionPago == null)
/*  695:     */         {
/*  696: 728 */           condicionPago = this.servicioCondicionPago.buscarCondicionPagoPorDiasPlazo(plazo.intValue(), AppUtil.getOrganizacion().getId());
/*  697: 729 */           hmCondicionPago.put(plazo, condicionPago);
/*  698:     */         }
/*  699: 735 */         RangoImpuesto rangoImpuesto = (RangoImpuesto)hmRangoImpuesto.get(fechaFactura);
/*  700: 736 */         if (rangoImpuesto == null)
/*  701:     */         {
/*  702: 737 */           rangoImpuesto = this.servicioImpuesto.getRangoRangoImpuestoTributario(fechaFactura, AppUtil.getOrganizacion().getId());
/*  703: 738 */           hmRangoImpuesto.put(fechaFactura, rangoImpuesto);
/*  704:     */         }
/*  705: 742 */         TipoOperacion tipoOperacion = null;
/*  706: 743 */         if (!codigoTipoOperacion.isEmpty())
/*  707:     */         {
/*  708: 744 */           tipoOperacion = (TipoOperacion)hmTipoOperacion.get(codigoTipoOperacion);
/*  709: 745 */           if (tipoOperacion == null)
/*  710:     */           {
/*  711: 746 */             HashMap<String, String> filters = new HashMap();
/*  712: 747 */             filters.put("codigo", codigoTipoOperacion);
/*  713: 748 */             List<TipoOperacion> lista = this.servicioTipoOperacion.obtenerListaCombo("nombre", true, filters);
/*  714: 749 */             if (!lista.isEmpty())
/*  715:     */             {
/*  716: 750 */               tipoOperacion = (TipoOperacion)lista.get(0);
/*  717: 751 */               hmTipoOperacion.put(codigoTipoOperacion, tipoOperacion);
/*  718:     */             }
/*  719:     */           }
/*  720:     */         }
/*  721: 761 */         String establecimiento = factura.substring(0, 3);
/*  722: 762 */         String puntoEmision = factura.substring(4, 7);
/*  723: 763 */         String numeroFactura = factura.substring(8);
/*  724:     */         
/*  725: 765 */         FacturaProveedor facturaProveedor = (FacturaProveedor)hmFacturaProveedo.get(factura);
/*  726: 766 */         if (facturaProveedor == null)
/*  727:     */         {
/*  728: 771 */           facturaProveedor = new FacturaProveedor();
/*  729: 772 */           facturaProveedor.setBono(BigDecimal.ZERO);
/*  730: 773 */           facturaProveedor.setSucursal(AppUtil.getSucursal());
/*  731: 774 */           facturaProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  732: 775 */           facturaProveedor.setEmpresa(empresa);
/*  733: 776 */           facturaProveedor.setDocumento(documento);
/*  734: 777 */           facturaProveedor.setFecha(fechaFactura);
/*  735: 778 */           facturaProveedor.setDescuento(BigDecimal.ZERO);
/*  736: 779 */           facturaProveedor.setEstado(Estado.APROBADO);
/*  737: 780 */           facturaProveedor.setNumero("");
/*  738: 781 */           facturaProveedor.setNumeroCuotas(1);
/*  739: 782 */           facturaProveedor.setCondicionPago(condicionPago);
/*  740: 783 */           facturaProveedor.setDireccionEmpresa(direccionEmpresa);
/*  741: 784 */           facturaProveedor.setDescripcion(nota);
/*  742: 785 */           facturaProveedor.setTipoOperacion(tipoOperacion);
/*  743: 786 */           facturaProveedor.setIndicadorSaldoInicial(false);
/*  744: 787 */           facturaProveedor.setReferencia1(String.valueOf(numeroReferencia));
/*  745: 788 */           facturaProveedor.setTraValorTotalSubida(valorTotal);
/*  746:     */           
/*  747:     */ 
/*  748:     */ 
/*  749:     */ 
/*  750: 793 */           FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/*  751: 794 */           facturaProveedorSRI.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/*  752: 795 */           facturaProveedorSRI.setIdSucursal(facturaProveedor.getSucursal().getId());
/*  753: 796 */           facturaProveedorSRI.setEstablecimientoRetencion("000");
/*  754: 797 */           facturaProveedorSRI.setPuntoEmisionRetencion("000");
/*  755: 798 */           facturaProveedorSRI.setNumeroRetencion("0");
/*  756: 799 */           facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/*  757: 800 */           facturaProveedorSRI.setTipoComprobanteSRI(null);
/*  758: 801 */           facturaProveedorSRI.setTipoIdentificacion(facturaProveedor.getEmpresa().getTipoIdentificacion());
/*  759: 802 */           facturaProveedorSRI.setNumero(numeroFactura);
/*  760: 803 */           facturaProveedorSRI.setEstablecimiento(establecimiento);
/*  761: 804 */           facturaProveedorSRI.setPuntoEmision(puntoEmision);
/*  762: 805 */           facturaProveedorSRI.setFacturaProveedor(facturaProveedor);
/*  763: 806 */           facturaProveedor.setFacturaProveedorSRI(facturaProveedorSRI);
/*  764: 808 */           if (!autorizacion.isEmpty())
/*  765:     */           {
/*  766: 809 */             facturaProveedorSRI.setAutorizacion(autorizacion);
/*  767:     */           }
/*  768:     */           else
/*  769:     */           {
/*  770: 811 */             AutorizacionProveedorSRI autorizacionProveedorSRI = this.servicioEmpresa.buscarAutorizacion(empresa.getId(), factura, fechaFactura, 
/*  771: 812 */               AppUtil.getOrganizacion().getId());
/*  772: 813 */             facturaProveedorSRI.setAutorizacion(autorizacionProveedorSRI.getAutorizacion());
/*  773:     */           }
/*  774: 816 */           hmFacturaProveedo.put(factura, facturaProveedor);
/*  775:     */         }
/*  776: 822 */         DetalleFacturaProveedor detalleFacturaProveedor = new DetalleFacturaProveedor();
/*  777: 823 */         detalleFacturaProveedor.setIdSucursal(AppUtil.getSucursal().getId());
/*  778: 824 */         detalleFacturaProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  779: 825 */         detalleFacturaProveedor.setCantidad(cantidad);
/*  780: 826 */         detalleFacturaProveedor.setDescuento(BigDecimal.ZERO);
/*  781: 827 */         detalleFacturaProveedor.setPrecio(producto.getPrecioReferencialCompra());
/*  782: 828 */         detalleFacturaProveedor.setProducto(producto);
/*  783: 829 */         detalleFacturaProveedor.setUnidadCompra(producto.getUnidadCompra());
/*  784: 830 */         detalleFacturaProveedor.setFacturaProveedor(facturaProveedor);
/*  785: 839 */         if (producto.isIndicadorImpuestos())
/*  786:     */         {
/*  787: 840 */           detalleFacturaProveedor.setIndicadorImpuestos(true);
/*  788: 841 */           ImpuestoProductoFacturaProveedor ipfp = new ImpuestoProductoFacturaProveedor();
/*  789: 842 */           ipfp.setDetalleFacturaProveedor(detalleFacturaProveedor);
/*  790: 843 */           ipfp.setImpuesto(rangoImpuesto.getImpuesto());
/*  791: 844 */           ipfp.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/*  792: 845 */           detalleFacturaProveedor.getListaImpuestoProductoFacturaProveedor().add(ipfp);
/*  793:     */         }
/*  794: 847 */         facturaProveedor.getListaDetalleFacturaProveedor().add(detalleFacturaProveedor);
/*  795:     */         
/*  796: 849 */         filaActual++;
/*  797:     */       }
/*  798:     */       catch (ExcepcionAS2Financiero e)
/*  799:     */       {
/*  800: 852 */         LOG.error("Error al migrar factura proveedor", e);
/*  801: 853 */         this.context.setRollbackOnly();
/*  802: 854 */         throw e;
/*  803:     */       }
/*  804:     */       catch (ExcepcionAS2Compras e)
/*  805:     */       {
/*  806: 856 */         LOG.error("Error al migrar factura proveedor", e);
/*  807: 857 */         this.context.setRollbackOnly();
/*  808: 858 */         throw e;
/*  809:     */       }
/*  810:     */       catch (ExcepcionAS2 e)
/*  811:     */       {
/*  812: 860 */         LOG.error("Error al migrar factura proveedor", e);
/*  813: 861 */         this.context.setRollbackOnly();
/*  814: 862 */         throw e;
/*  815:     */       }
/*  816:     */       catch (Exception e)
/*  817:     */       {
/*  818: 864 */         LOG.error("Error al migrar factura proveedor", e);
/*  819: 865 */         this.context.setRollbackOnly();
/*  820: 866 */         throw new ExcepcionAS2(e);
/*  821:     */       }
/*  822:     */     }
/*  823:     */     try
/*  824:     */     {
/*  825: 874 */       Object listaFacturaProveedor = new ArrayList(hmFacturaProveedo.values());
/*  826:     */       
/*  827:     */ 
/*  828:     */ 
/*  829:     */ 
/*  830:     */ 
/*  831:     */ 
/*  832:     */ 
/*  833:     */ 
/*  834: 883 */       Collections.sort((List)listaFacturaProveedor, new Comparator()
/*  835:     */       {
/*  836:     */         public int compare(FacturaProveedor arg0, FacturaProveedor arg1)
/*  837:     */         {
/*  838: 879 */           return arg0.getFecha().compareTo(arg1.getFecha());
/*  839:     */         }
/*  840:     */       });
/*  841: 885 */       for (FacturaProveedor fp : (List)listaFacturaProveedor)
/*  842:     */       {
/*  843: 887 */         this.servicioFacturaProveedor.totalizar(fp);
/*  844:     */         
/*  845: 889 */         BigDecimal totalFactura = fp.getTotal().add(fp.getRetencionComercializadora()).add(fp.getImpuesto());
/*  846:     */         
/*  847:     */ 
/*  848: 892 */         String factura = fp.getFacturaProveedorSRI().getEstablecimiento() + "-" + fp.getFacturaProveedorSRI().getPuntoEmision() + "-" + fp.getFacturaProveedorSRI().getNumero();
/*  849: 894 */         if (totalFactura.subtract(fp.getTraValorTotalSubida()).abs().compareTo(tolerancia) > 0) {
/*  850: 896 */           throw new ExcepcionAS2("msg_error_valor_factura", " " + fp.getTraValorTotalSubida() + " <> " + totalFactura + " Factura: " + factura);
/*  851:     */         }
/*  852: 898 */         this.servicioFacturaProveedor.generarCuentaPorPagar(fp);
/*  853: 899 */         this.servicioFacturaProveedorSRI.actualizarFacturaProveedorSRI(fp);
/*  854: 900 */         fp.setIndicadorCreditoTributarioSRI(false);
/*  855: 901 */         this.servicioFacturaProveedorSRI.cargarConceptosRetencion(fp.getFacturaProveedorSRI(), fp.getEmpresa());
/*  856: 902 */         fp.getFacturaProveedorSRI().setCreditoTributarioSRI(creditoTributarioSRI);
/*  857:     */         
/*  858: 904 */         this.servicioFacturaProveedor.guardar(fp);
/*  859: 905 */         generarFacturaVentaSri(fp);
/*  860:     */       }
/*  861:     */     }
/*  862:     */     catch (ExcepcionAS2Financiero e)
/*  863:     */     {
/*  864: 910 */       LOG.error("Error al migrar factura proveedor", e);
/*  865: 911 */       this.context.setRollbackOnly();
/*  866: 912 */       throw e;
/*  867:     */     }
/*  868:     */     catch (ExcepcionAS2Compras e)
/*  869:     */     {
/*  870: 914 */       LOG.error("Error al migrar factura proveedor", e);
/*  871: 915 */       this.context.setRollbackOnly();
/*  872: 916 */       throw e;
/*  873:     */     }
/*  874:     */     catch (ExcepcionAS2 e)
/*  875:     */     {
/*  876: 918 */       LOG.error("Error al migrar factura proveedor", e);
/*  877: 919 */       this.context.setRollbackOnly();
/*  878: 920 */       throw e;
/*  879:     */     }
/*  880:     */     catch (Exception e)
/*  881:     */     {
/*  882: 922 */       LOG.error("Error al migrar factura proveedor", e);
/*  883: 923 */       this.context.setRollbackOnly();
/*  884: 924 */       throw new ExcepcionAS2(e);
/*  885:     */     }
/*  886:     */   }
/*  887:     */   
/*  888:     */   public void generarFacturasComercializadoras(List<FacturaComercializadora> listaFacturaComercializadora)
/*  889:     */     throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  890:     */   {
/*  891: 931 */     CreditoTributarioSRI creditoTributarioSRI = this.servicioCreditoTributario.buscarPorCodigo("12");
/*  892:     */     
/*  893: 933 */     Map<String, FacturaCliente> hmFacturaCliente = new HashMap();
/*  894: 934 */     Map<Integer, DireccionEmpresa> hmDireccionEmpresa = new HashMap();
/*  895: 935 */     Map<String, PuntoDeVenta> hmPuntoDeVenta = new HashMap();
/*  896: 936 */     Map<Integer, CondicionPago> hmCondicionPago = new HashMap();
/*  897: 937 */     Map<Date, RangoImpuesto> hmRangoImpuesto = new HashMap();
/*  898:     */     
/*  899:     */ 
/*  900: 940 */     Map<String, DetalleVersionListaPrecios> hmPreciosRecargos = new HashMap();
/*  901:     */     
/*  902: 942 */     Map<Integer, List<RecargoListaPreciosCliente>> hmRecargosCliente = new HashMap();
/*  903:     */     
/*  904:     */ 
/*  905: 945 */     Map<Integer, CategoriaEmpresa> hmCategoriaEmpresa = new HashMap();
/*  906:     */     
/*  907:     */ 
/*  908: 948 */     BigDecimal tolerancia = ParametrosSistema.getValorToleranciaCompraVenta(AppUtil.getOrganizacion().getId());
/*  909:     */     
/*  910: 950 */     Integer idConceptoRetencionSRI = ParametrosSistema.getConceptoRetencionComercializadora3X1000(AppUtil.getOrganizacion().getIdOrganizacion());
/*  911: 951 */     if (idConceptoRetencionSRI == null) {
/*  912: 952 */       throw new ExcepcionAS2("msgs_error_info_configuracion_documento_sri", "Concepto Retencion Comercializadora 3X1000");
/*  913:     */     }
/*  914: 955 */     StringBuffer sbMensajeValores = new StringBuffer();
/*  915: 956 */     RangoImpuesto rangoImpuesto = null;
/*  916: 957 */     for (FacturaComercializadora facturaComercializadora : listaFacturaComercializadora)
/*  917:     */     {
/*  918: 960 */       Map<String, DetalleFacturaClienteComercializadora> hmRecargosFacturaCliente = new HashMap();
/*  919:     */       
/*  920: 962 */       String establecimiento = facturaComercializadora.getEstablecimiento();
/*  921: 963 */       String puntoEmision = facturaComercializadora.getPuntoEmision();
/*  922: 964 */       numeroFactura = facturaComercializadora.getNumeroFactura();
/*  923: 965 */       String serieFactura = establecimiento + puntoEmision;
/*  924:     */       
/*  925:     */ 
/*  926:     */ 
/*  927: 969 */       Empresa empresa = facturaComercializadora.getEmpresa();
/*  928:     */       
/*  929:     */ 
/*  930:     */ 
/*  931:     */ 
/*  932: 974 */       DireccionEmpresa direccionEmpresa = (DireccionEmpresa)hmDireccionEmpresa.get(Integer.valueOf(empresa.getIdEmpresa()));
/*  933: 975 */       if (direccionEmpresa == null)
/*  934:     */       {
/*  935: 976 */         direccionEmpresa = this.direccionEmpresaDao.buscarPorEmpresa(empresa);
/*  936: 977 */         hmDireccionEmpresa.put(Integer.valueOf(empresa.getIdEmpresa()), direccionEmpresa);
/*  937:     */       }
/*  938: 983 */       Producto producto = facturaComercializadora.getProducto();
/*  939:     */       
/*  940:     */ 
/*  941:     */ 
/*  942:     */ 
/*  943: 988 */       Integer plazo = Integer.valueOf(FuncionesUtiles.diferenciasDeFechas(facturaComercializadora.getFechaFactura(), facturaComercializadora
/*  944: 989 */         .getFechaVencimiento()) - 1);
/*  945: 990 */       CondicionPago condicionPago = (CondicionPago)hmCondicionPago.get(plazo);
/*  946: 991 */       if (condicionPago == null)
/*  947:     */       {
/*  948: 992 */         condicionPago = this.servicioCondicionPago.buscarCondicionPagoPorDiasPlazo(plazo.intValue(), AppUtil.getOrganizacion().getId());
/*  949: 993 */         hmCondicionPago.put(plazo, condicionPago);
/*  950:     */       }
/*  951: 999 */       rangoImpuesto = (RangoImpuesto)hmRangoImpuesto.get(facturaComercializadora.getFechaFactura());
/*  952:1000 */       if (rangoImpuesto == null)
/*  953:     */       {
/*  954:1001 */         rangoImpuesto = this.servicioImpuesto.getRangoRangoImpuestoTributario(facturaComercializadora.getFechaFactura(), 
/*  955:1002 */           AppUtil.getOrganizacion().getId());
/*  956:1003 */         hmRangoImpuesto.put(facturaComercializadora.getFechaFactura(), rangoImpuesto);
/*  957:     */       }
/*  958:1008 */       PuntoDeVenta puntoVenta = (PuntoDeVenta)hmPuntoDeVenta.get(serieFactura);
/*  959:1009 */       if (puntoVenta == null)
/*  960:     */       {
/*  961:1010 */         HashMap<String, String> filters = new HashMap();
/*  962:1011 */         filters.put("sucursal.codigo", establecimiento);
/*  963:1012 */         filters.put("codigo", puntoEmision);
/*  964:1013 */         List<PuntoDeVenta> listaPuntoVenta = this.servicioPuntoDeVenta.obtenerListaCombo("", false, filters);
/*  965:1014 */         if (listaPuntoVenta.size() > 0) {
/*  966:1015 */           puntoVenta = (PuntoDeVenta)listaPuntoVenta.get(0);
/*  967:     */         } else {
/*  968:1017 */           throw new ExcepcionAS2("msg_error_punto_factura", serieFactura);
/*  969:     */         }
/*  970:1020 */         hmPuntoDeVenta.put(serieFactura, puntoVenta);
/*  971:     */       }
/*  972:1022 */       Integer numeroCuotas = Integer.valueOf(1);
/*  973:     */       
/*  974:1024 */       String nota = "";
/*  975:     */       
/*  976:1026 */       FacturaCliente facturaCliente = (FacturaCliente)hmFacturaCliente.get(numeroFactura);
/*  977:1027 */       if (facturaCliente == null)
/*  978:     */       {
/*  979:1032 */         facturaCliente = new FacturaCliente();
/*  980:1033 */         facturaCliente.setSucursal(AppUtil.getSucursal());
/*  981:1034 */         facturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  982:1035 */         facturaCliente.setEmpresa(empresa);
/*  983:1036 */         facturaCliente.setZona(empresa.getCliente().getZona());
/*  984:1037 */         facturaCliente.setFecha(facturaComercializadora.getFechaFactura());
/*  985:1038 */         facturaCliente.setDescuento(BigDecimal.ZERO);
/*  986:1039 */         facturaCliente.setEstado(facturaComercializadora.getEstado());
/*  987:1040 */         facturaCliente.setNumeroCuotas(numeroCuotas.intValue());
/*  988:1041 */         facturaCliente.setDireccionEmpresa(direccionEmpresa);
/*  989:1042 */         facturaCliente.setDescripcion(nota);
/*  990:1043 */         facturaCliente.setCondicionPago(condicionPago);
/*  991:     */         
/*  992:     */ 
/*  993:     */ 
/*  994:     */ 
/*  995:     */ 
/*  996:1049 */         Map<String, String> filters = new HashMap();
/*  997:1050 */         filters.put("documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/*  998:1051 */         filters.put("indicadorDocumentoTributario", "true");
/*  999:1052 */         filters.put("predeterminado", "true");
/* 1000:1053 */         List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("nombre", true, filters);
/* 1001:1055 */         if (listaDocumento.isEmpty()) {
/* 1002:1056 */           throw new ExcepcionAS2("msg_configuracion_documento", " " + DocumentoBase.FACTURA_CLIENTE.getNombre());
/* 1003:     */         }
/* 1004:1058 */         Documento documento = (Documento)listaDocumento.get(0);
/* 1005:1059 */         facturaCliente.setDocumento(documento);
/* 1006:     */         
/* 1007:     */ 
/* 1008:1062 */         AutorizacionDocumentoSRI autorizacionSRI = this.servicioDocumento.cargarDocumentoConAutorizacion(documento, puntoVenta, facturaCliente
/* 1009:1063 */           .getFecha());
/* 1010:1064 */         facturaCliente.setDocumento(documento);
/* 1011:1065 */         this.servicioSecuencia.detach(facturaCliente.getDocumento().getSecuencia());
/* 1012:1066 */         documento.getSecuencia().setPrefijo(establecimiento + "-" + puntoEmision + "-");
/* 1013:1068 */         for (int i = numeroFactura.length(); i < documento.getSecuencia().getLongitud(); i++) {
/* 1014:1069 */           numeroFactura = "0" + numeroFactura;
/* 1015:     */         }
/* 1016:1072 */         String numero = establecimiento + "-" + puntoEmision + "-" + numeroFactura;
/* 1017:1073 */         facturaCliente.setNumero(numero);
/* 1018:     */         
/* 1019:1075 */         FacturaClienteSRI facturaClienteSRI = new FacturaClienteSRI();
/* 1020:1076 */         facturaClienteSRI.setIdOrganizacion(facturaCliente.getIdOrganizacion());
/* 1021:1077 */         facturaClienteSRI.setIdSucursal(facturaCliente.getSucursal().getIdSucursal());
/* 1022:1078 */         facturaClienteSRI.setTipoIdentificacion(facturaCliente.getEmpresa().getTipoIdentificacion());
/* 1023:1079 */         facturaClienteSRI.setEstablecimiento(establecimiento);
/* 1024:1080 */         facturaClienteSRI.setPuntoEmision(puntoEmision);
/* 1025:1081 */         facturaClienteSRI.setEstado(facturaCliente.getEstado());
/* 1026:1082 */         facturaClienteSRI.setFacturaCliente(facturaCliente);
/* 1027:1083 */         facturaCliente.setFacturaClienteSRI(facturaClienteSRI);
/* 1028:1085 */         if (facturaComercializadora.getAutorizacion().equals("")) {
/* 1029:1086 */           facturaClienteSRI.setAutorizacion(autorizacionSRI.getAutorizacion());
/* 1030:     */         } else {
/* 1031:1088 */           facturaClienteSRI.setAutorizacion(facturaComercializadora.getAutorizacion());
/* 1032:     */         }
/* 1033:1092 */         if (!this.servicioFormaPagoSRI.getListaFormaPagoSRI(facturaCliente.getEmpresa()).isEmpty()) {
/* 1034:1094 */           facturaClienteSRI.setCodigoFormaPagoSRI(((FormaPagoSRI)this.servicioFormaPagoSRI.getListaFormaPagoSRI(facturaCliente.getEmpresa()).get(0)).getCodigo());
/* 1035:     */         } else {
/* 1036:1097 */           facturaClienteSRI.setCodigoFormaPagoSRI(((SelectItem)DatosSRI.getListaFormaPago(facturaCliente.getIdOrganizacion()).get(0)).getValue().toString());
/* 1037:     */         }
/* 1038:1101 */         if (facturaClienteSRI.getAutorizacion().length() > 10) {
/* 1039:1102 */           facturaClienteSRI.setIndicadorDocumentoElectronico(true);
/* 1040:     */         }
/* 1041:1105 */         hmFacturaCliente.put(facturaCliente.getNumero(), facturaCliente);
/* 1042:     */       }
/* 1043:1108 */       BigDecimal cantidad = FuncionesUtiles.redondearBigDecimal(facturaComercializadora.getCantidad(), 2);
/* 1044:1109 */       BigDecimal descuentoUnitario = BigDecimal.ZERO;
/* 1045:1114 */       if (facturaCliente.getEmpresa().getCliente().getListaPrecios() == null)
/* 1046:     */       {
/* 1047:1115 */         String strMensaje = " " + facturaCliente.getEmpresa().getCodigoAlterno() + " " + facturaCliente.getEmpresa().getNombreFiscal();
/* 1048:1116 */         throw new ExcepcionAS2Ventas("msg_error_cliente_sin_lista_de_precios", strMensaje);
/* 1049:     */       }
/* 1050:1119 */       int idListaPrecios = facturaCliente.getEmpresa().getCliente().getListaPrecios().getId();
/* 1051:1120 */       int idProducto = producto.getId();
/* 1052:1121 */       String clave = idListaPrecios + "~" + idProducto;
/* 1053:1122 */       DetalleVersionListaPrecios vl = (DetalleVersionListaPrecios)hmPreciosRecargos.get(clave);
/* 1054:1124 */       if (vl == null)
/* 1055:     */       {
/* 1056:1125 */         vl = this.servicioListaPrecios.getDatosVersionListaPrecios(idListaPrecios, producto.getId(), facturaCliente.getFecha(), null, facturaCliente
/* 1057:1126 */           .getNumero(), false);
/* 1058:1127 */         hmPreciosRecargos.put(clave, vl);
/* 1059:     */       }
/* 1060:1130 */       DetalleFacturaCliente detalleFacturaCliente = new DetalleFacturaCliente();
/* 1061:1131 */       detalleFacturaCliente.setIdSucursal(AppUtil.getSucursal().getId());
/* 1062:1132 */       detalleFacturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1063:1133 */       detalleFacturaCliente.setFacturaCliente(facturaCliente);
/* 1064:1134 */       detalleFacturaCliente.setCantidad(cantidad);
/* 1065:1135 */       detalleFacturaCliente.setDescuento(descuentoUnitario);
/* 1066:1136 */       detalleFacturaCliente.setPrecio(vl.getPrecioUnitario());
/* 1067:1137 */       detalleFacturaCliente.setProducto(producto);
/* 1068:1138 */       detalleFacturaCliente.setUnidadVenta(producto.getUnidadVenta());
/* 1069:     */       
/* 1070:1140 */       CategoriaEmpresa categoriaEmpresa = (CategoriaEmpresa)hmCategoriaEmpresa.get(facturaComercializadora.getEmpresa().getCategoriaEmpresa());
/* 1071:1141 */       if (categoriaEmpresa == null)
/* 1072:     */       {
/* 1073:1142 */         categoriaEmpresa = this.servicioCategoriaEmpresa.cargarDetalle(facturaComercializadora.getEmpresa().getCategoriaEmpresa().getId());
/* 1074:1143 */         hmCategoriaEmpresa.put(Integer.valueOf(categoriaEmpresa.getId()), categoriaEmpresa);
/* 1075:     */       }
/* 1076:1148 */       if (facturaCliente.getEmpresa().getCliente().isIndicadorIvaPresuntivo())
/* 1077:     */       {
/* 1078:1150 */         DetalleFacturaClienteComercializadora IVAPresuntivo = new DetalleFacturaClienteComercializadora();
/* 1079:1151 */         IVAPresuntivo.setRecargo(TipoImpuestoCombustibleEnum.IVA_PRESUNTIVO.toString());
/* 1080:1152 */         IVAPresuntivo.setFacturaCliente(facturaCliente);
/* 1081:1154 */         if (categoriaEmpresa.getCuentaContableIvaPresuntivo() == null) {
/* 1082:1155 */           throw new ExcepcionAS2Financiero("msg_error_cuenta_iva_presuntivo", facturaCliente.getEmpresa().getNombreFiscal());
/* 1083:     */         }
/* 1084:1158 */         IVAPresuntivo.setCuentaContable(categoriaEmpresa.getCuentaContableIvaPresuntivo());
/* 1085:1159 */         facturaCliente.getListaDetalleFacturaClienteComercializadora().add(IVAPresuntivo);
/* 1086:     */         
/* 1087:     */ 
/* 1088:1162 */         BigDecimal valorIVAPresuntivo = FuncionesUtiles.porcentaje(vl.getPrecioUnitarioClienteFinal().subtract(vl.getPrecioUnitario()), rangoImpuesto
/* 1089:1163 */           .getPorcentajeImpuesto(), 6);
/* 1090:1164 */         IVAPresuntivo.setValor(FuncionesUtiles.redondearBigDecimal(detalleFacturaCliente.getCantidad().multiply(valorIVAPresuntivo), 2));
/* 1091:     */       }
/* 1092:     */       BigDecimal valorImpuesto3X1000;
/* 1093:1169 */       if (facturaCliente.getEmpresa().getCliente().isIndicador3X1000())
/* 1094:     */       {
/* 1095:1171 */         DetalleFacturaClienteComercializadora impuesto3X1000 = new DetalleFacturaClienteComercializadora();
/* 1096:1172 */         impuesto3X1000.setRecargo(TipoImpuestoCombustibleEnum.IMP_3X1000.toString());
/* 1097:1173 */         impuesto3X1000.setFacturaCliente(facturaCliente);
/* 1098:1175 */         if (categoriaEmpresa.getCuentaContableIvaPresuntivo() == null) {
/* 1099:1176 */           throw new ExcepcionAS2Financiero("msg_error_cuenta_iva_presuntivo", facturaCliente.getEmpresa().getNombreFiscal());
/* 1100:     */         }
/* 1101:1179 */         impuesto3X1000.setCuentaContable(categoriaEmpresa.getCuentaContable3X1000());
/* 1102:1180 */         facturaCliente.getListaDetalleFacturaClienteComercializadora().add(impuesto3X1000);
/* 1103:     */         
/* 1104:1182 */         valorImpuesto3X1000 = detalleFacturaCliente.getCantidad().multiply(vl.getPrecioUnitario());
/* 1105:1183 */         impuesto3X1000.setValor(FuncionesUtiles.porcentaje(valorImpuesto3X1000, 0.3D, 2));
/* 1106:     */       }
/* 1107:1188 */       List<RecargoListaPreciosCliente> listaRecargosCliente = (List)hmRecargosCliente.get(Integer.valueOf(facturaCliente.getEmpresa().getId()));
/* 1108:1189 */       if (listaRecargosCliente == null)
/* 1109:     */       {
/* 1110:1190 */         listaRecargosCliente = this.servicioEmpresa.obtenerRecargos(facturaCliente.getEmpresa());
/* 1111:1191 */         hmRecargosCliente.put(Integer.valueOf(facturaCliente.getEmpresa().getId()), listaRecargosCliente);
/* 1112:     */       }
/* 1113:1194 */       for (RecargoListaPreciosCliente recargo : listaRecargosCliente)
/* 1114:     */       {
/* 1115:1195 */         idListaPrecios = recargo.getListaPrecios().getId();
/* 1116:1196 */         clave = idListaPrecios + "~" + idProducto;
/* 1117:1197 */         vl = (DetalleVersionListaPrecios)hmPreciosRecargos.get(clave);
/* 1118:1199 */         if (vl == null)
/* 1119:     */         {
/* 1120:1200 */           vl = this.servicioListaPrecios.getDatosVersionListaPrecios(idListaPrecios, producto.getId(), facturaCliente.getFecha(), null, facturaCliente
/* 1121:1201 */             .getNumero(), false);
/* 1122:1202 */           hmPreciosRecargos.put(clave, vl);
/* 1123:     */         }
/* 1124:1205 */         String TipoRecargoCombustible = recargo.getListaPrecios().getCodigo();
/* 1125:1206 */         DetalleFacturaClienteComercializadora dfcRecargo = (DetalleFacturaClienteComercializadora)hmRecargosFacturaCliente.get(TipoRecargoCombustible);
/* 1126:1207 */         if (dfcRecargo == null)
/* 1127:     */         {
/* 1128:1208 */           dfcRecargo = new DetalleFacturaClienteComercializadora();
/* 1129:1209 */           dfcRecargo.setCuentaContable(dfcRecargo.getCuentaContable());
/* 1130:1210 */           dfcRecargo.setRecargo(TipoRecargoCombustible);
/* 1131:1211 */           dfcRecargo.setFacturaCliente(facturaCliente);
/* 1132:1213 */           if (recargo.getCuentaContable() == null) {
/* 1133:1215 */             throw new ExcepcionAS2Financiero("msg_error_cuenta_recargo", " " + recargo.getListaPrecios().getNombre() + " " + facturaCliente.getEmpresa().getNombreFiscal());
/* 1134:     */           }
/* 1135:1218 */           dfcRecargo.setCuentaContable(recargo.getCuentaContable());
/* 1136:     */           
/* 1137:1220 */           facturaCliente.getListaDetalleFacturaClienteComercializadora().add(dfcRecargo);
/* 1138:     */         }
/* 1139:1223 */         BigDecimal valoreRecargo = detalleFacturaCliente.getCantidad().multiply(vl.getPrecioUnitario());
/* 1140:1224 */         dfcRecargo.setValor(dfcRecargo.getValor().add(FuncionesUtiles.redondearBigDecimal(valoreRecargo, 2)));
/* 1141:     */       }
/* 1142:1230 */       detalleFacturaCliente.setIndicadorImpuesto(true);
/* 1143:1231 */       this.servicioFacturaCliente.obtenerImpuestosProductos(producto, detalleFacturaCliente);
/* 1144:1232 */       facturaCliente.getListaDetalleFacturaCliente().add(detalleFacturaCliente);
/* 1145:1233 */       this.servicioFacturaCliente.totalizar(facturaCliente);
/* 1146:     */       
/* 1147:     */ 
/* 1148:1236 */       BigDecimal totalFactura = facturaCliente.getTotal().add(facturaCliente.getValorOtros()).add(facturaCliente.getImpuesto());
/* 1149:1238 */       if (totalFactura.subtract(facturaComercializadora.getValorTotal()).abs().compareTo(tolerancia) > 0)
/* 1150:     */       {
/* 1151:1240 */         String mensaje = "\n" + facturaComercializadora.getValorTotal() + " <> " + totalFactura + " F:/" + facturaCliente.getNumero() + " - Cliente: " + facturaCliente.getEmpresa().getNombreFiscal();
/* 1152:1241 */         sbMensajeValores.append(mensaje);
/* 1153:     */       }
/* 1154:     */     }
/* 1155:     */     String numeroFactura;
/* 1156:1246 */     if (!sbMensajeValores.toString().isEmpty()) {
/* 1157:1247 */       throw new ExcepcionAS2("msg_error_valor_factura", sbMensajeValores.toString());
/* 1158:     */     }
/* 1159:1250 */     for (FacturaCliente fc : hmFacturaCliente.values()) {
/* 1160:     */       try
/* 1161:     */       {
/* 1162:1252 */         verificaNumeroFacturaRangoSecuencia(fc);
/* 1163:     */         
/* 1164:1254 */         this.servicioFacturaCliente.totalizar(fc);
/* 1165:1255 */         this.servicioFacturaCliente.generarCuentaPorCobrar(fc);
/* 1166:1256 */         this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(fc);
/* 1167:     */         
/* 1168:1258 */         this.servicioFacturaCliente.guardar(fc);
/* 1169:1260 */         if (fc.getEstado() == Estado.ANULADO) {
/* 1170:1262 */           this.servicioAnuladoSRI.anularFacturaCliente(fc.getFacturaClienteSRI());
/* 1171:     */         }
/* 1172:1268 */         BigDecimal valorIVAPresuntivo = BigDecimal.ZERO;
/* 1173:1269 */         BigDecimal baseImponible = BigDecimal.ZERO;
/* 1174:1270 */         BigDecimal valorImpuestoComercial = BigDecimal.ZERO;
/* 1175:1272 */         for (DetalleFacturaClienteComercializadora detalleFacturaClienteComercializadora : fc
/* 1176:1273 */           .getListaDetalleFacturaClienteComercializadora())
/* 1177:     */         {
/* 1178:1274 */           if (detalleFacturaClienteComercializadora.getRecargo().equals(TipoImpuestoCombustibleEnum.IVA_PRESUNTIVO.toString())) {
/* 1179:1275 */             valorIVAPresuntivo = detalleFacturaClienteComercializadora.getValor();
/* 1180:     */           }
/* 1181:1277 */           if (detalleFacturaClienteComercializadora.getRecargo().equals(TipoImpuestoCombustibleEnum.IMP_3X1000.toString())) {
/* 1182:1278 */             valorImpuestoComercial = detalleFacturaClienteComercializadora.getValor();
/* 1183:     */           }
/* 1184:     */         }
/* 1185:1281 */         baseImponible = valorIVAPresuntivo.multiply(new BigDecimal(100.0D)).divide(rangoImpuesto.getPorcentajeImpuesto(), 2, RoundingMode.HALF_UP);
/* 1186:     */         
/* 1187:     */ 
/* 1188:1284 */         FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/* 1189:1285 */         facturaProveedorSRI.setFacturaClienteSRI(fc.getFacturaClienteSRI());
/* 1190:1286 */         facturaProveedorSRI.setIdOrganizacion(fc.getIdOrganizacion());
/* 1191:1287 */         facturaProveedorSRI.setIdSucursal(fc.getSucursal().getIdSucursal());
/* 1192:1288 */         facturaProveedorSRI.setEstado(fc.getEstado());
/* 1193:1289 */         facturaProveedorSRI.setTipoIdentificacion(fc.getEmpresa().getTipoIdentificacion());
/* 1194:1290 */         facturaProveedorSRI.setIdentificacionProveedor(fc.getEmpresa().getIdentificacion());
/* 1195:1291 */         facturaProveedorSRI.setNombreProveedor(fc.getEmpresa().getNombreFiscal());
/* 1196:1292 */         facturaProveedorSRI.setFechaRegistro(fc.getFecha());
/* 1197:1293 */         facturaProveedorSRI.setEstablecimiento(fc.getFacturaClienteSRI().getEstablecimiento());
/* 1198:1294 */         facturaProveedorSRI.setPuntoEmision(fc.getFacturaClienteSRI().getPuntoEmision());
/* 1199:1295 */         facturaProveedorSRI.setNumero(fc.getFacturaClienteSRI().getNumero());
/* 1200:1296 */         facturaProveedorSRI.setFechaEmision(fc.getFacturaClienteSRI().getFechaEmision());
/* 1201:1297 */         facturaProveedorSRI.setAutorizacion(fc.getFacturaClienteSRI().getAutorizacion());
/* 1202:1298 */         facturaProveedorSRI.setMontoIva(valorIVAPresuntivo);
/* 1203:     */         
/* 1204:1300 */         facturaProveedorSRI.setBaseImponibleTarifaCero(BigDecimal.ZERO);
/* 1205:1301 */         facturaProveedorSRI.setBaseImponibleDiferenteCero(baseImponible);
/* 1206:1302 */         facturaProveedorSRI.setBaseImponibleNoObjetoIva(BigDecimal.ZERO);
/* 1207:     */         
/* 1208:1304 */         facturaProveedorSRI.setTipoEmpresa(fc.getEmpresa().getTipoEmpresa());
/* 1209:     */         
/* 1210:1306 */         facturaProveedorSRI.setIndicadorSaldoInicial(false);
/* 1211:1307 */         facturaProveedorSRI.setIndicadorRetencionEmitida(false);
/* 1212:     */         
/* 1213:1309 */         facturaProveedorSRI.setEstablecimientoRetencion(fc.getFacturaClienteSRI().getEstablecimiento());
/* 1214:1310 */         facturaProveedorSRI.setPuntoEmisionRetencion(fc.getFacturaClienteSRI().getPuntoEmision());
/* 1215:1311 */         facturaProveedorSRI.setNumeroRetencion(fc.getFacturaClienteSRI().getNumero());
/* 1216:1312 */         facturaProveedorSRI.setAutorizacionRetencion(fc.getFacturaClienteSRI().getAutorizacion());
/* 1217:1313 */         facturaProveedorSRI.setFechaEmisionRetencion(fc.getFacturaClienteSRI().getFechaEmision());
/* 1218:     */         
/* 1219:1315 */         facturaProveedorSRI.setCreditoTributarioSRI(creditoTributarioSRI);
/* 1220:1316 */         facturaProveedorSRI.setDireccionProveedor(fc.getDireccionFactura());
/* 1221:1317 */         facturaProveedorSRI.setIndicadorValidar(false);
/* 1222:     */         
/* 1223:     */ 
/* 1224:1320 */         TipoComprobanteSRI tipoComprobanteSRI = this.servicioSRI.buscarTipoComprobanteSRIPorCodigo("42");
/* 1225:1321 */         facturaProveedorSRI.setTipoComprobanteSRI(tipoComprobanteSRI);
/* 1226:     */         
/* 1227:1323 */         DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = new DetalleFacturaProveedorSRI();
/* 1228:1324 */         detalleFacturaProveedorSRI.setFacturaProveedorSRI(facturaProveedorSRI);
/* 1229:1325 */         detalleFacturaProveedorSRI.setBaseImponibleRetencion(fc.getBaseImponible());
/* 1230:     */         
/* 1231:1327 */         ConceptoRetencionSRI concepto = this.servicioConceptoRetencionSRI.getConceptoRetencionPorIdYFecha(idConceptoRetencionSRI.intValue(), fc.getFecha());
/* 1232:1328 */         detalleFacturaProveedorSRI.setConceptoRetencionSRI(concepto);
/* 1233:1329 */         detalleFacturaProveedorSRI.setPorcentajeRetencion(concepto.getPorcentaje());
/* 1234:1330 */         detalleFacturaProveedorSRI.setValorRetencion(valorImpuestoComercial);
/* 1235:1331 */         facturaProveedorSRI.getListaDetalleFacturaProveedorSRI().add(detalleFacturaProveedorSRI);
/* 1236:     */         
/* 1237:1333 */         detalleFacturaProveedorSRI = new DetalleFacturaProveedorSRI();
/* 1238:1334 */         detalleFacturaProveedorSRI.setFacturaProveedorSRI(facturaProveedorSRI);
/* 1239:1335 */         detalleFacturaProveedorSRI.setBaseImponibleRetencion(valorIVAPresuntivo);
/* 1240:     */         
/* 1241:1337 */         Map<String, String> filters = new HashMap();
/* 1242:1338 */         filters.put("idOrganizacion", "" + facturaProveedorSRI.getIdOrganizacion());
/* 1243:1339 */         filters.put("porcentaje", "100");
/* 1244:1340 */         List<ConceptoRetencionSRI> listaConcepto = this.servicioConceptoRetencionSRI.obtenerListaCombo("codigo", true, filters);
/* 1245:1341 */         if (listaConcepto.size() == 1) {
/* 1246:1342 */           concepto = (ConceptoRetencionSRI)listaConcepto.get(0);
/* 1247:     */         } else {
/* 1248:1344 */           concepto = null;
/* 1249:     */         }
/* 1250:1346 */         if (concepto == null) {
/* 1251:1347 */           throw new ExcepcionAS2("msgs_error_info_configuracion_documento_sri", "Concepto Retencion Comercializadora Iva Presuntivo");
/* 1252:     */         }
/* 1253:1349 */         detalleFacturaProveedorSRI.setConceptoRetencionSRI(concepto);
/* 1254:1350 */         detalleFacturaProveedorSRI.setPorcentajeRetencion(concepto.getPorcentaje());
/* 1255:1351 */         detalleFacturaProveedorSRI.setValorRetencion(valorIVAPresuntivo);
/* 1256:1352 */         facturaProveedorSRI.getListaDetalleFacturaProveedorSRI().add(detalleFacturaProveedorSRI);
/* 1257:     */         
/* 1258:1354 */         this.servicioFacturaProveedorSRI.guardar(facturaProveedorSRI);
/* 1259:     */         
/* 1260:1356 */         fc.getFacturaClienteSRI().setFacturaProveedorSRI(facturaProveedorSRI);
/* 1261:     */       }
/* 1262:     */       catch (ExcepcionAS2Financiero e)
/* 1263:     */       {
/* 1264:1359 */         this.context.setRollbackOnly();
/* 1265:1360 */         throw e;
/* 1266:     */       }
/* 1267:     */       catch (ExcepcionAS2 e)
/* 1268:     */       {
/* 1269:1362 */         this.context.setRollbackOnly();
/* 1270:1363 */         throw e;
/* 1271:     */       }
/* 1272:     */       catch (AS2Exception e)
/* 1273:     */       {
/* 1274:1365 */         this.context.setRollbackOnly();
/* 1275:1366 */         throw e;
/* 1276:     */       }
/* 1277:     */     }
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   private void generarFacturaVentaSri(FacturaProveedor facturaProveedor)
/* 1281:     */     throws ExcepcionAS2
/* 1282:     */   {
/* 1283:1374 */     FacturaClienteSRI facturaClienteSRI = new FacturaClienteSRI();
/* 1284:1375 */     facturaClienteSRI.setFacturaProveedorSRI(facturaProveedor.getFacturaProveedorSRI());
/* 1285:1376 */     facturaClienteSRI.setEstado(Estado.PROCESADO);
/* 1286:1377 */     facturaClienteSRI.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/* 1287:1378 */     facturaClienteSRI.setIdSucursal(facturaProveedor.getSucursal().getId());
/* 1288:1379 */     facturaClienteSRI.setTipoIdentificacion(facturaProveedor.getEmpresa().getTipoIdentificacion());
/* 1289:1380 */     facturaClienteSRI.setIdentificacionCliente(facturaProveedor.getEmpresa().getIdentificacion());
/* 1290:1381 */     facturaClienteSRI.setFechaEmision(facturaProveedor.getFecha());
/* 1291:1382 */     facturaClienteSRI.setTipoComprobanteSRI(this.tipoComprobanteSRIDao.buscarPorCodigo("18"));
/* 1292:1383 */     facturaClienteSRI.setAutorizacion(facturaProveedor.getFacturaProveedorSRI().getAutorizacion());
/* 1293:1384 */     facturaClienteSRI.setFechaEmision(facturaProveedor.getFecha());
/* 1294:1385 */     facturaClienteSRI.setPuntoEmision(facturaProveedor.getFacturaProveedorSRI().getPuntoEmision());
/* 1295:1386 */     facturaClienteSRI.setEstablecimiento(facturaProveedor.getFacturaProveedorSRI().getPuntoEmision());
/* 1296:1387 */     facturaClienteSRI.setNumero(facturaProveedor.getFacturaProveedorSRI().getNumero());
/* 1297:1388 */     facturaClienteSRI.setValorRetenidoFuente(facturaProveedor.getRetencionComercializadora());
/* 1298:1389 */     facturaClienteSRI.setMontoIva(BigDecimal.ZERO);
/* 1299:1390 */     facturaClienteSRI.setBaseImponibleTarifaCero(BigDecimal.ZERO);
/* 1300:1391 */     facturaClienteSRI.setBaseImponibleDiferenteCero(BigDecimal.ZERO);
/* 1301:1392 */     facturaClienteSRI.setBaseImponibleNoObjetoIva(BigDecimal.ZERO);
/* 1302:1393 */     facturaClienteSRI.setIndicadorSaldoInicial(false);
/* 1303:1394 */     facturaClienteSRI.setEliminado(false);
/* 1304:     */     
/* 1305:1396 */     this.servicioFacturaClienteSRI.guardar(facturaClienteSRI);
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public void verificaNumeroFacturaRangoSecuencia(FacturaCliente facturaCliente)
/* 1309:     */     throws ExcepcionAS2Financiero
/* 1310:     */   {
/* 1311:1407 */     int desde = facturaCliente.getDocumento().getSecuencia().getDesde();
/* 1312:1408 */     int hasta = facturaCliente.getDocumento().getSecuencia().getHasta();
/* 1313:     */     
/* 1314:1410 */     String[] numeroFactura = facturaCliente.getNumero().split("-");
/* 1315:1411 */     int numero = Integer.valueOf(numeroFactura[2]).intValue();
/* 1316:1413 */     if ((numero < desde) || (numero > hasta)) {
/* 1317:1415 */       throw new ExcepcionAS2Financiero("msg_error_numero_factura_fuera_rango", " " + desde + " - " + hasta + " ( " + facturaCliente.getNumero() + " ) ");
/* 1318:     */     }
/* 1319:     */   }
/* 1320:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.combustibles.impl.ServicioCargaProcesoCombustiblesImpl
 * JD-Core Version:    0.7.0.1
 */