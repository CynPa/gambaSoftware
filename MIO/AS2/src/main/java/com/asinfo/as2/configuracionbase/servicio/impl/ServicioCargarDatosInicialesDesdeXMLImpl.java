/*    1:     */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCargarDatosInicialesDesdeXML;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    6:     */ import com.asinfo.as2.dao.BodegaDao;
/*    7:     */ import com.asinfo.as2.dao.CajaDao;
/*    8:     */ import com.asinfo.as2.dao.CategoriaEmpresaDao;
/*    9:     */ import com.asinfo.as2.dao.CiudadDao;
/*   10:     */ import com.asinfo.as2.dao.CondicionPagoDao;
/*   11:     */ import com.asinfo.as2.dao.ConfiguracionDao;
/*   12:     */ import com.asinfo.as2.dao.DocumentoContabilizacionDao;
/*   13:     */ import com.asinfo.as2.dao.DocumentoDao;
/*   14:     */ import com.asinfo.as2.dao.DocumentoVariableProcesoDao;
/*   15:     */ import com.asinfo.as2.dao.EstadoChequeDao;
/*   16:     */ import com.asinfo.as2.dao.EstadoCivilDao;
/*   17:     */ import com.asinfo.as2.dao.EstadoProcesoDao;
/*   18:     */ import com.asinfo.as2.dao.FiltroProductoDao;
/*   19:     */ import com.asinfo.as2.dao.FormaPagoDao;
/*   20:     */ import com.asinfo.as2.dao.GenericoDao;
/*   21:     */ import com.asinfo.as2.dao.IdiomaDao;
/*   22:     */ import com.asinfo.as2.dao.ImpuestoDao;
/*   23:     */ import com.asinfo.as2.dao.ListaPreciosDao;
/*   24:     */ import com.asinfo.as2.dao.ModuloDao;
/*   25:     */ import com.asinfo.as2.dao.MonedaDao;
/*   26:     */ import com.asinfo.as2.dao.MotivoAjusteInventarioDao;
/*   27:     */ import com.asinfo.as2.dao.OrganizacionConfiguracionDao;
/*   28:     */ import com.asinfo.as2.dao.OrganizacionDao;
/*   29:     */ import com.asinfo.as2.dao.PaisDao;
/*   30:     */ import com.asinfo.as2.dao.ParroquiaDao;
/*   31:     */ import com.asinfo.as2.dao.ProcesoOrganizacionDao;
/*   32:     */ import com.asinfo.as2.dao.ProvinciaDao;
/*   33:     */ import com.asinfo.as2.dao.PuntoDeVentaDao;
/*   34:     */ import com.asinfo.as2.dao.QuincenaDao;
/*   35:     */ import com.asinfo.as2.dao.RangoImpuestoDao;
/*   36:     */ import com.asinfo.as2.dao.ReporteadorDao;
/*   37:     */ import com.asinfo.as2.dao.RubroDao;
/*   38:     */ import com.asinfo.as2.dao.SecuenciaDao;
/*   39:     */ import com.asinfo.as2.dao.TemaDao;
/*   40:     */ import com.asinfo.as2.dao.TipoAsientoDao;
/*   41:     */ import com.asinfo.as2.dao.TipoBodegaDao;
/*   42:     */ import com.asinfo.as2.dao.TipoCuentaBancariaDao;
/*   43:     */ import com.asinfo.as2.dao.TipoIdentificacionDao;
/*   44:     */ import com.asinfo.as2.dao.UbicacionDao;
/*   45:     */ import com.asinfo.as2.dao.UnidadDao;
/*   46:     */ import com.asinfo.as2.dao.UsuarioBodegaDao;
/*   47:     */ import com.asinfo.as2.dao.UsuarioOrganizacionDao;
/*   48:     */ import com.asinfo.as2.dao.UsuarioSucursalDao;
/*   49:     */ import com.asinfo.as2.dao.ZonaDao;
/*   50:     */ import com.asinfo.as2.dao.nomina.asistencia.HoraExtraDao;
/*   51:     */ import com.asinfo.as2.dao.seguridad.AccionDao;
/*   52:     */ import com.asinfo.as2.dao.seguridad.PermisoDao;
/*   53:     */ import com.asinfo.as2.dao.seguridad.ProcesoDao;
/*   54:     */ import com.asinfo.as2.dao.seguridad.RolDao;
/*   55:     */ import com.asinfo.as2.dao.seguridad.SistemaDao;
/*   56:     */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*   57:     */ import com.asinfo.as2.dao.sri.ConceptoRetencionSRIDao;
/*   58:     */ import com.asinfo.as2.dao.sri.CreditoTributarioSRIDao;
/*   59:     */ import com.asinfo.as2.dao.sri.TipoComprobanteSRIDao;
/*   60:     */ import com.asinfo.as2.datosbase.servicio.ServicioMoneda;
/*   61:     */ import com.asinfo.as2.entities.Banco;
/*   62:     */ import com.asinfo.as2.entities.Bodega;
/*   63:     */ import com.asinfo.as2.entities.Caja;
/*   64:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   65:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   66:     */ import com.asinfo.as2.entities.Ciudad;
/*   67:     */ import com.asinfo.as2.entities.CodigoFormaPagoSRI;
/*   68:     */ import com.asinfo.as2.entities.ComprobanteSRICreditoTributarioSRI;
/*   69:     */ import com.asinfo.as2.entities.CondicionPago;
/*   70:     */ import com.asinfo.as2.entities.Configuracion;
/*   71:     */ import com.asinfo.as2.entities.ConfiguracionConciliacionBancaria;
/*   72:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   73:     */ import com.asinfo.as2.entities.DetalleReporteador;
/*   74:     */ import com.asinfo.as2.entities.DetalleReporteadorVariable;
/*   75:     */ import com.asinfo.as2.entities.Documento;
/*   76:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   77:     */ import com.asinfo.as2.entities.DocumentoVariableProceso;
/*   78:     */ import com.asinfo.as2.entities.EntidadBase;
/*   79:     */ import com.asinfo.as2.entities.EstadoCheque;
/*   80:     */ import com.asinfo.as2.entities.EstadoCivil;
/*   81:     */ import com.asinfo.as2.entities.EstadoProceso;
/*   82:     */ import com.asinfo.as2.entities.FiltroProducto;
/*   83:     */ import com.asinfo.as2.entities.FormaPago;
/*   84:     */ import com.asinfo.as2.entities.Idioma;
/*   85:     */ import com.asinfo.as2.entities.Impuesto;
/*   86:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   87:     */ import com.asinfo.as2.entities.Modulo;
/*   88:     */ import com.asinfo.as2.entities.Moneda;
/*   89:     */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   90:     */ import com.asinfo.as2.entities.Organizacion;
/*   91:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   92:     */ import com.asinfo.as2.entities.OrigenIngresos;
/*   93:     */ import com.asinfo.as2.entities.Pais;
/*   94:     */ import com.asinfo.as2.entities.Parroquia;
/*   95:     */ import com.asinfo.as2.entities.Plantilla;
/*   96:     */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*   97:     */ import com.asinfo.as2.entities.Provincia;
/*   98:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   99:     */ import com.asinfo.as2.entities.Quincena;
/*  100:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*  101:     */ import com.asinfo.as2.entities.Reporteador;
/*  102:     */ import com.asinfo.as2.entities.Reportes;
/*  103:     */ import com.asinfo.as2.entities.ReportesPersonalizados;
/*  104:     */ import com.asinfo.as2.entities.Rubro;
/*  105:     */ import com.asinfo.as2.entities.Secuencia;
/*  106:     */ import com.asinfo.as2.entities.Sucursal;
/*  107:     */ import com.asinfo.as2.entities.TareaProgramada;
/*  108:     */ import com.asinfo.as2.entities.Tema;
/*  109:     */ import com.asinfo.as2.entities.TipoAsiento;
/*  110:     */ import com.asinfo.as2.entities.TipoBodega;
/*  111:     */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*  112:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  113:     */ import com.asinfo.as2.entities.TipoIdentificacionComprobanteSRI;
/*  114:     */ import com.asinfo.as2.entities.Ubicacion;
/*  115:     */ import com.asinfo.as2.entities.Unidad;
/*  116:     */ import com.asinfo.as2.entities.UsuarioBodega;
/*  117:     */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*  118:     */ import com.asinfo.as2.entities.UsuarioSucursal;
/*  119:     */ import com.asinfo.as2.entities.VersionSistema;
/*  120:     */ import com.asinfo.as2.entities.Zona;
/*  121:     */ import com.asinfo.as2.entities.nomina.asistencia.HoraExtra;
/*  122:     */ import com.asinfo.as2.entities.seguridad.EntidadAccion;
/*  123:     */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  124:     */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  125:     */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  126:     */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  127:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  128:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  129:     */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  130:     */ import com.asinfo.as2.entities.sri.IBPCapacidad;
/*  131:     */ import com.asinfo.as2.entities.sri.IBPClasificacion;
/*  132:     */ import com.asinfo.as2.entities.sri.IBPMarca;
/*  133:     */ import com.asinfo.as2.entities.sri.IBPUnidad;
/*  134:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  135:     */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*  136:     */ import com.asinfo.as2.enumeraciones.DiaMes;
/*  137:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  138:     */ import com.asinfo.as2.enumeraciones.Estado;
/*  139:     */ import com.asinfo.as2.enumeraciones.Genero;
/*  140:     */ import com.asinfo.as2.enumeraciones.Parametro;
/*  141:     */ import com.asinfo.as2.enumeraciones.Periodicidad;
/*  142:     */ import com.asinfo.as2.enumeraciones.PorCientoHoraExtra;
/*  143:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  144:     */ import com.asinfo.as2.enumeraciones.TareaProgramadaEnum;
/*  145:     */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  146:     */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*  147:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  148:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  149:     */ import com.asinfo.as2.enumeraciones.TipoReporteAsiento;
/*  150:     */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*  151:     */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*  152:     */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*  153:     */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*  154:     */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  155:     */ import com.asinfo.as2.enumeraciones.VariableProcesoEnum;
/*  156:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  157:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  158:     */ import com.asinfo.as2.utils.ArchivoUtil;
/*  159:     */ import com.asinfo.as2.utils.EjbUtil;
/*  160:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  161:     */ import java.io.File;
/*  162:     */ import java.io.IOException;
/*  163:     */ import java.io.PrintStream;
/*  164:     */ import java.math.BigDecimal;
/*  165:     */ import java.nio.file.CopyOption;
/*  166:     */ import java.nio.file.Files;
/*  167:     */ import java.nio.file.LinkOption;
/*  168:     */ import java.nio.file.Path;
/*  169:     */ import java.nio.file.Paths;
/*  170:     */ import java.nio.file.StandardCopyOption;
/*  171:     */ import java.text.ParseException;
/*  172:     */ import java.text.SimpleDateFormat;
/*  173:     */ import java.util.ArrayList;
/*  174:     */ import java.util.Collection;
/*  175:     */ import java.util.Date;
/*  176:     */ import java.util.HashMap;
/*  177:     */ import java.util.Iterator;
/*  178:     */ import java.util.List;
/*  179:     */ import java.util.Locale;
/*  180:     */ import java.util.Map;
/*  181:     */ import java.util.Map.Entry;
/*  182:     */ import java.util.Set;
/*  183:     */ import javax.ejb.EJB;
/*  184:     */ import javax.ejb.Stateless;
/*  185:     */ import org.jdom2.Document;
/*  186:     */ import org.jdom2.Element;
/*  187:     */ 
/*  188:     */ @Stateless
/*  189:     */ public class ServicioCargarDatosInicialesDesdeXMLImpl
/*  190:     */   extends ServicioCargarDatosInicialesDesdeXMLBase
/*  191:     */   implements ServicioCargarDatosInicialesDesdeXML
/*  192:     */ {
/*  193:     */   @EJB
/*  194:     */   SistemaDao sistemaDao;
/*  195:     */   
/*  196:     */   public void cargarTipoIdentificacionDesdeXML()
/*  197:     */     throws ExcepcionAS2
/*  198:     */   {
/*  199: 168 */     TipoIdentificacion tipoIdentificacion = null;
/*  200:     */     
/*  201: 170 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "tipoIdentificacion.xml";
/*  202:     */     
/*  203: 172 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  204: 173 */     Element nodoPrincipal = document.getRootElement();
/*  205:     */     
/*  206: 175 */     List<Element> listaTipoIdentificacionXML = nodoPrincipal.getChildren("tipoIdentificacion");
/*  207: 176 */     for (Element tipoIdentificacionXML : listaTipoIdentificacionXML)
/*  208:     */     {
/*  209: 178 */       String codigo = tipoIdentificacionXML.getChildText("codigo");
/*  210: 179 */       String nombre = tipoIdentificacionXML.getChildText("nombre");
/*  211: 180 */       String descripcion = tipoIdentificacionXML.getChildText("descripcion");
/*  212: 181 */       boolean indicadorValidarIdentificacion = obtenerIndicador(tipoIdentificacionXML.getChildText("indicadorValidarIdentificacion"));
/*  213: 182 */       boolean activo = obtenerIndicador(tipoIdentificacionXML.getChildText("activo"));
/*  214: 183 */       boolean predeterminado = obtenerIndicador(tipoIdentificacionXML.getChildText("predeterminado"));
/*  215: 184 */       String idSucursal = tipoIdentificacionXML.getChildText("idSucursal");
/*  216: 185 */       String idOrganizacion = tipoIdentificacionXML.getChildText("idOrganizacion");
/*  217: 186 */       String longitudMaxima = tipoIdentificacionXML.getChildText("longitudMaxima");
/*  218:     */       
/*  219: 188 */       tipoIdentificacion = new TipoIdentificacion();
/*  220: 189 */       tipoIdentificacion.setCodigo(codigo);
/*  221: 190 */       tipoIdentificacion.setNombre(nombre);
/*  222: 191 */       tipoIdentificacion.setDescripcion(descripcion);
/*  223: 192 */       tipoIdentificacion.setIndicadorValidarIdentificacion(indicadorValidarIdentificacion);
/*  224: 193 */       tipoIdentificacion.setActivo(activo);
/*  225: 194 */       tipoIdentificacion.setPredeterminado(predeterminado);
/*  226: 195 */       tipoIdentificacion.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  227: 196 */       tipoIdentificacion.setIdSucursal(Integer.parseInt(idSucursal));
/*  228: 197 */       tipoIdentificacion.setLongitudMaxima(Integer.parseInt(longitudMaxima));
/*  229:     */       
/*  230: 199 */       this.tipoIdentificacionDao.guardar(tipoIdentificacion);
/*  231:     */     }
/*  232:     */   }
/*  233:     */   
/*  234:     */   public void cargarOrganizacionDesdeXML()
/*  235:     */     throws ExcepcionAS2
/*  236:     */   {
/*  237: 217 */     Organizacion organizacion = null;
/*  238: 218 */     OrganizacionConfiguracion organizacionConfiguracion = null;
/*  239: 219 */     Ubicacion ubicacion = null;
/*  240: 220 */     Sucursal sucursal = null;
/*  241: 221 */     PuntoDeVenta puntoDeVenta = null;
/*  242: 222 */     Caja caja = null;
/*  243:     */     
/*  244:     */ 
/*  245: 225 */     Map<String, TipoIdentificacion> hashMapTipoIdentificacion = new HashMap();
/*  246:     */     
/*  247:     */ 
/*  248: 228 */     List<TipoIdentificacion> lista = this.tipoIdentificacionDao.obtenerListaCombo(null, false, null);
/*  249: 229 */     for (TipoIdentificacion tipoIdentificacion : lista) {
/*  250: 230 */       hashMapTipoIdentificacion.put(tipoIdentificacion.getCodigo(), tipoIdentificacion);
/*  251:     */     }
/*  252: 233 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "organizacion.xml";
/*  253:     */     
/*  254: 235 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  255: 236 */     Element nodoPrincipal = document.getRootElement();
/*  256:     */     
/*  257: 238 */     List<Element> listaOrganizacionXML = nodoPrincipal.getChildren("organizacion");
/*  258: 240 */     for (Element organizacionXML : listaOrganizacionXML)
/*  259:     */     {
/*  260: 243 */       String identificacion = organizacionXML.getAttributeValue("identificacion");
/*  261: 244 */       String identificacionContador = organizacionXML.getAttributeValue("identificacionContador");
/*  262: 245 */       String identificacionRepresentante = organizacionXML.getAttributeValue("identificacionRepresentante");
/*  263: 246 */       String paginaWeb = organizacionXML.getAttributeValue("paginaWeb");
/*  264: 247 */       String razonSocial = organizacionXML.getAttributeValue("razonSocial");
/*  265: 248 */       String representanteLegal = organizacionXML.getAttributeValue("representanteLegal");
/*  266: 249 */       String imagen = organizacionXML.getAttributeValue("imagen");
/*  267: 250 */       String tipoIdentificacion = organizacionXML.getAttributeValue("tipoIdentificacion");
/*  268: 251 */       String tipoIdentificacionContador = organizacionXML.getAttributeValue("tipoIdentificacionContador");
/*  269: 252 */       String tipoIdentificacionRepresentante = organizacionXML.getAttributeValue("tipoIdentificacionRepresentante");
/*  270:     */       
/*  271:     */ 
/*  272: 255 */       organizacion = new Organizacion();
/*  273: 256 */       organizacion.setIdentificacion(identificacion);
/*  274: 257 */       organizacion.setIdentificacionContador(identificacionContador);
/*  275: 258 */       organizacion.setIdentificacionRepresentante(identificacionRepresentante);
/*  276: 259 */       organizacion.setPaginaWeb(paginaWeb);
/*  277: 260 */       organizacion.setRazonSocial(razonSocial);
/*  278: 261 */       organizacion.setRepresentanteLegal(representanteLegal);
/*  279: 262 */       organizacion.setImagen(imagen);
/*  280: 263 */       organizacion.setTipoIdentificacion((TipoIdentificacion)hashMapTipoIdentificacion.get(tipoIdentificacion));
/*  281: 264 */       organizacion.setTipoIdentificacionContador((TipoIdentificacion)hashMapTipoIdentificacion.get(tipoIdentificacionContador));
/*  282: 265 */       organizacion.setTipoIdentificacionRepresentante((TipoIdentificacion)hashMapTipoIdentificacion.get(tipoIdentificacionRepresentante));
/*  283:     */       
/*  284:     */ 
/*  285: 268 */       this.organizacionDao.guardar(organizacion);
/*  286:     */       
/*  287:     */ 
/*  288: 271 */       Element oganizacionConfiguracionXML = organizacionXML.getChild("organizacionConfiguracion");
/*  289:     */       
/*  290: 273 */       String creacionOrganizacion = oganizacionConfiguracionXML.getChildText("creacionOrganizacion");
/*  291: 274 */       String inicioSerie = oganizacionConfiguracionXML.getChildText("inicioSerie");
/*  292: 275 */       String numeroCopiasDocumentoTributario = oganizacionConfiguracionXML.getChildText("numeroCopiasDocumentoTributario");
/*  293:     */       
/*  294: 277 */       organizacionConfiguracion = new OrganizacionConfiguracion();
/*  295: 278 */       organizacionConfiguracion.setTipoOrganizacion(TipoOrganizacion.TIPO_ORGANIZACION_GENERAL);
/*  296: 279 */       organizacionConfiguracion.setCreacionOrganizacion(creacionOrganizacion);
/*  297: 280 */       organizacionConfiguracion.setInicioSerie(Integer.valueOf(Integer.parseInt(inicioSerie)));
/*  298: 281 */       organizacionConfiguracion.setNumeroCopiasDocumentoTributario(Integer.parseInt(numeroCopiasDocumentoTributario));
/*  299: 282 */       organizacionConfiguracion.setOrganizacion(organizacion);
/*  300: 283 */       organizacionConfiguracion.setNombreDimension1(oganizacionConfiguracionXML.getChildText("nombreDimension1"));
/*  301: 284 */       organizacionConfiguracion.setNombreDimension5(oganizacionConfiguracionXML.getChildText("nombreDimension5"));
/*  302:     */       
/*  303: 286 */       this.organizacionConfiguracionDao.guardar(organizacionConfiguracion);
/*  304:     */       
/*  305:     */ 
/*  306: 289 */       List<Element> listaSucursalXML = organizacionXML.getChildren("sucursal");
/*  307: 291 */       for (Element sucursalXML : listaSucursalXML)
/*  308:     */       {
/*  309: 296 */         Element ubicacionXML = sucursalXML.getChild("ubicacion");
/*  310: 297 */         String callePrincipal = ubicacionXML.getChildText("callePrincipal");
/*  311: 298 */         String calleSecundaria = ubicacionXML.getChildText("calleSecundaria");
/*  312: 299 */         String numero = ubicacionXML.getChildText("numero");
/*  313: 300 */         String barrio = ubicacionXML.getChildText("barrio");
/*  314: 301 */         String referencia = ubicacionXML.getChildText("referencia");
/*  315: 302 */         String idOrganizacion = ubicacionXML.getChildText("idOrganizacion");
/*  316: 303 */         String idSucursal = ubicacionXML.getChildText("idSucursal");
/*  317:     */         
/*  318:     */ 
/*  319: 306 */         ubicacion = new Ubicacion();
/*  320: 307 */         ubicacion.setDireccion1(callePrincipal);
/*  321: 308 */         ubicacion.setDireccion2(calleSecundaria);
/*  322: 309 */         ubicacion.setDireccion3(numero);
/*  323: 310 */         ubicacion.setDireccion4(barrio);
/*  324: 311 */         ubicacion.setDireccion5(referencia);
/*  325: 312 */         ubicacion.setIdSucursal(Integer.parseInt(idSucursal));
/*  326: 313 */         ubicacion.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  327:     */         
/*  328:     */ 
/*  329: 316 */         this.ubicacionDao.guardar(ubicacion);
/*  330:     */         
/*  331:     */ 
/*  332: 319 */         String codigoSucursal = sucursalXML.getChildText("codigo");
/*  333: 320 */         String nombreSucursal = sucursalXML.getChildText("nombre");
/*  334: 321 */         String email1Sucursal = sucursalXML.getChildText("email1");
/*  335: 322 */         String email2Sucursal = sucursalXML.getChildText("email2");
/*  336: 323 */         String telefono1Sucursal = sucursalXML.getChildText("telefono1");
/*  337: 324 */         String telefono2Sucursal = sucursalXML.getChildText("telefono2");
/*  338: 325 */         String activoSucursal = sucursalXML.getChildText("activo");
/*  339: 326 */         String predeterminadoSucursal = sucursalXML.getChildText("predeterminado");
/*  340: 327 */         boolean indicadorMatriz = obtenerIndicador(sucursalXML.getChildText("indicadorMatriz"));
/*  341:     */         
/*  342:     */ 
/*  343: 330 */         sucursal = new Sucursal();
/*  344: 331 */         sucursal.setCodigo(codigoSucursal);
/*  345: 332 */         sucursal.setNombre(nombreSucursal);
/*  346: 333 */         sucursal.setEmail1(email1Sucursal);
/*  347: 334 */         sucursal.setEmail2(email2Sucursal);
/*  348: 335 */         sucursal.setTelefono1(telefono1Sucursal);
/*  349: 336 */         sucursal.setTelefono2(telefono2Sucursal);
/*  350: 337 */         sucursal.setIndicadorMatriz(indicadorMatriz);
/*  351: 338 */         sucursal.setIdOrganizacion(organizacion.getId());
/*  352: 339 */         sucursal.setUbicacion(ubicacion);
/*  353: 340 */         sucursal.setActivo(obtenerIndicador(activoSucursal));
/*  354: 341 */         sucursal.setPredeterminado(obtenerIndicador(predeterminadoSucursal));
/*  355:     */         
/*  356:     */ 
/*  357: 344 */         sucAux = this.servicioSucursal.guardar(sucursal);
/*  358:     */         
/*  359:     */ 
/*  360: 347 */         Element puntosDeVentas = sucursalXML.getChild("puntosDeVenta");
/*  361: 348 */         List<Element> listaPuntoXML = puntosDeVentas.getChildren("punto");
/*  362: 350 */         for (Element puntoXML : listaPuntoXML)
/*  363:     */         {
/*  364: 352 */           String codigoPunto = puntoXML.getChildText("codigo");
/*  365: 353 */           String nombrePunto = puntoXML.getChildText("nombre");
/*  366: 354 */           String descripcionPunto = puntoXML.getChildText("descripcion");
/*  367: 355 */           String activoPunto = puntoXML.getChildText("activo");
/*  368: 356 */           String predeterminadoPunto = puntoXML.getChildText("predeterminado");
/*  369: 357 */           String idOrganizacionPunto = puntoXML.getChildText("idOrganizacion");
/*  370:     */           
/*  371:     */ 
/*  372:     */ 
/*  373: 361 */           puntoDeVenta = new PuntoDeVenta();
/*  374: 362 */           puntoDeVenta.setCodigo(codigoPunto);
/*  375: 363 */           puntoDeVenta.setNombre(nombrePunto);
/*  376: 364 */           puntoDeVenta.setDescripcion(descripcionPunto);
/*  377: 365 */           puntoDeVenta.setActivo(obtenerIndicador(activoPunto));
/*  378: 366 */           puntoDeVenta.setPredeterminado(obtenerIndicador(predeterminadoPunto));
/*  379: 367 */           puntoDeVenta.setSucursal(sucAux);
/*  380: 368 */           puntoDeVenta.setIdOrganizacion(Integer.parseInt(idOrganizacionPunto));
/*  381:     */           
/*  382: 370 */           this.puntoDeVentaDao.guardar(puntoDeVenta);
/*  383:     */           
/*  384:     */ 
/*  385: 373 */           Element cajasPunto = puntoXML.getChild("cajasPunto");
/*  386: 374 */           List<Element> listaCajaXML = cajasPunto.getChildren("caja");
/*  387: 376 */           for (Element cajaXML : listaCajaXML)
/*  388:     */           {
/*  389: 377 */             String codigoCaja = cajaXML.getChildText("codigo");
/*  390: 378 */             String nombreCaja = cajaXML.getChildText("nombre");
/*  391: 379 */             String descripcionCaja = cajaXML.getChildText("descripcion");
/*  392: 380 */             String activoCaja = cajaXML.getChildText("activo");
/*  393: 381 */             String predeterminadoCaja = cajaXML.getChildText("predeterminado");
/*  394: 382 */             String idOrganizacionCaja = cajaXML.getChildText("idOrganizacion");
/*  395: 383 */             String idSucursalCaja = cajaXML.getChildText("idSucursal");
/*  396:     */             
/*  397: 385 */             caja = new Caja();
/*  398: 386 */             caja.setCodigo(codigoCaja);
/*  399: 387 */             caja.setNombre(nombreCaja);
/*  400: 388 */             caja.setDescripcion(descripcionCaja);
/*  401: 389 */             caja.setActivo(obtenerIndicador(activoCaja));
/*  402: 390 */             caja.setPredeterminado(obtenerIndicador(predeterminadoCaja));
/*  403: 391 */             caja.setPuntoDeVenta(puntoDeVenta);
/*  404: 392 */             caja.setIdOrganizacion(Integer.parseInt(idOrganizacionCaja));
/*  405: 393 */             caja.setIdSucursal(Integer.parseInt(idSucursalCaja));
/*  406:     */             
/*  407: 395 */             this.cajaDao.guardar(caja);
/*  408:     */           }
/*  409:     */         }
/*  410:     */       }
/*  411:     */     }
/*  412:     */     Sucursal sucAux;
/*  413:     */   }
/*  414:     */   
/*  415:     */   public void cargarRolDesdeXML()
/*  416:     */     throws ExcepcionAS2
/*  417:     */   {
/*  418: 413 */     EntidadRol rol = null;
/*  419:     */     
/*  420: 415 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "rol.xml";
/*  421:     */     
/*  422: 417 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  423: 418 */     Element nodoPrincipal = document.getRootElement();
/*  424: 419 */     List<Element> listaRolXML = nodoPrincipal.getChildren("rol");
/*  425: 420 */     for (Element rolXML : listaRolXML)
/*  426:     */     {
/*  427: 422 */       String idSucursalRol = rolXML.getChildText("idSucursal");
/*  428: 423 */       String idOrganizacionRol = rolXML.getChildText("idOrganizacion");
/*  429: 424 */       String nombreRol = rolXML.getChildText("nombre");
/*  430: 425 */       String descripcionRol = rolXML.getChildText("descripcion");
/*  431: 426 */       String activoRol = rolXML.getChildText("activo");
/*  432:     */       
/*  433: 428 */       rol = new EntidadRol();
/*  434: 429 */       rol.setIdSucursal(Integer.parseInt(idSucursalRol));
/*  435: 430 */       rol.setIdOrganizacion(Integer.parseInt(idOrganizacionRol));
/*  436: 431 */       rol.setNombre(nombreRol);
/*  437: 432 */       rol.setDescripcion(descripcionRol);
/*  438: 433 */       rol.setActivo(obtenerIndicador(activoRol));
/*  439:     */       
/*  440: 435 */       this.rolDao.guardar(rol);
/*  441:     */     }
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void cargarTemaDesdeXML()
/*  445:     */     throws ExcepcionAS2
/*  446:     */   {
/*  447: 452 */     Tema tema = null;
/*  448:     */     
/*  449: 454 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "tema.xml";
/*  450: 455 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  451: 456 */     Element nodoPrincipal = document.getRootElement();
/*  452: 457 */     List<Element> listaTemaXML = nodoPrincipal.getChildren("tema");
/*  453: 459 */     for (Element temaXML : listaTemaXML)
/*  454:     */     {
/*  455: 461 */       String codigo = temaXML.getChildText("codigo");
/*  456: 462 */       String nombre = temaXML.getChildText("nombre");
/*  457: 463 */       String imagen = temaXML.getChildText("imagen");
/*  458:     */       
/*  459: 465 */       tema = new Tema();
/*  460:     */       
/*  461: 467 */       tema.setCodigo(codigo);
/*  462: 468 */       tema.setNombre(nombre);
/*  463: 469 */       tema.setImagen(imagen);
/*  464:     */       
/*  465: 471 */       this.temaDao.guardar(tema);
/*  466:     */     }
/*  467:     */   }
/*  468:     */   
/*  469:     */   public void cargarUsuarioDesdeXML()
/*  470:     */     throws ExcepcionAS2
/*  471:     */   {
/*  472: 488 */     EntidadUsuario usuario = null;
/*  473:     */     
/*  474: 490 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "usuario.xml";
/*  475:     */     
/*  476: 492 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  477: 493 */     Element nodoPrincipal = document.getRootElement();
/*  478: 494 */     List<Element> listaUsuarioXML = nodoPrincipal.getChildren("usuario");
/*  479: 496 */     for (Element usuarioXML : listaUsuarioXML)
/*  480:     */     {
/*  481: 499 */       String nombreUsuario = usuarioXML.getChildText("nombreUsuario");
/*  482: 500 */       String clave = usuarioXML.getChildText("clave");
/*  483: 501 */       String codigo = usuarioXML.getChildText("codigo");
/*  484: 502 */       String identificacion = usuarioXML.getChildText("identificacion");
/*  485: 503 */       String nombre1 = usuarioXML.getChildText("nombre1");
/*  486: 504 */       String nombre2 = usuarioXML.getChildText("nombre2");
/*  487: 505 */       String tema = usuarioXML.getChildText("tema");
/*  488: 506 */       String direccion = usuarioXML.getChildText("direccion");
/*  489: 507 */       String email = usuarioXML.getChildText("email");
/*  490: 508 */       String activo = usuarioXML.getChildText("activo");
/*  491: 509 */       String enLinea = usuarioXML.getChildText("enLinea");
/*  492: 510 */       String genero = usuarioXML.getChildText("genero");
/*  493: 511 */       String indicadorAgenteComercial = usuarioXML.getChildText("indicadorAgenteComercial");
/*  494: 512 */       String indicadorAdministrador = usuarioXML.getChildText("indicadorAdministrador");
/*  495: 513 */       String indicadorUsuarioPos = usuarioXML.getChildText("indicadorUsuarioPos");
/*  496: 514 */       String tipoVisualizacion = usuarioXML.getChildText("tipoVisualizacion");
/*  497: 515 */       String indicadorSuperAdministrador = usuarioXML.getChildText("indicadorSoloLectura");
/*  498:     */       
/*  499: 517 */       usuario = new EntidadUsuario();
/*  500:     */       
/*  501: 519 */       Element rolXML = usuarioXML.getChild("rolUsuario");
/*  502: 520 */       List<Element> listaRolXML = rolXML.getChildren("rol");
/*  503:     */       
/*  504: 522 */       Map<String, EntidadRol> hashMapRol = new HashMap();
/*  505: 523 */       List<EntidadRol> listaRol = this.rolDao.obtenerListaCombo(null, false, null);
/*  506: 524 */       for (EntidadRol rol : listaRol) {
/*  507: 525 */         hashMapRol.put(rol.getNombre(), rol);
/*  508:     */       }
/*  509: 528 */       for (Element nombreRolXML : listaRolXML)
/*  510:     */       {
/*  511: 529 */         String nombreRol = nombreRolXML.getChildText("nombre");
/*  512: 530 */         usuario.getListaRol().add(hashMapRol.get(nombreRol.trim()));
/*  513:     */       }
/*  514: 535 */       usuario.setNombreUsuario(nombreUsuario);
/*  515: 536 */       usuario.setClave(clave);
/*  516: 537 */       usuario.setCodigo(codigo);
/*  517: 538 */       usuario.setIdentificacion(identificacion);
/*  518: 539 */       usuario.setNombre1(nombre1);
/*  519: 540 */       usuario.setNombre2(nombre2);
/*  520: 541 */       usuario.setTema(tema);
/*  521: 542 */       usuario.setDireccion(direccion);
/*  522: 543 */       usuario.setEmail(email);
/*  523: 544 */       usuario.setActivo(obtenerIndicador(activo));
/*  524: 545 */       usuario.setEnlinea(obtenerIndicador(enLinea));
/*  525: 546 */       usuario.setGenero(Genero.obtenerGenero(genero));
/*  526: 547 */       usuario.setIndicadorAgenteComercial(obtenerIndicador(indicadorAgenteComercial));
/*  527: 548 */       usuario.setIndicadorAdministrador(obtenerIndicador(indicadorAdministrador));
/*  528: 549 */       usuario.setIndicadorUsuarioPos(obtenerIndicador(indicadorUsuarioPos));
/*  529: 550 */       usuario.setTipoVisualizacion(TipoVisualizacionEnum.valueOf(tipoVisualizacion));
/*  530: 551 */       usuario.setIndicadorSuperAdministrador(obtenerIndicador(indicadorSuperAdministrador));
/*  531: 552 */       if (usuario.isIndicadorSuperAdministrador()) {
/*  532: 553 */         usuario.setIndicadorNuevo(false);
/*  533:     */       }
/*  534: 556 */       this.usuarioDao.guardar(usuario);
/*  535:     */     }
/*  536:     */   }
/*  537:     */   
/*  538:     */   public void cargarModuloDesdeXML(boolean baseVacia)
/*  539:     */     throws ExcepcionAS2
/*  540:     */   {
/*  541: 569 */     List<Modulo> listaModulo = this.moduloDao.obtenerListaCombo(null, false, null);
/*  542: 570 */     Map<String, Modulo> mapaModulo = new HashMap();
/*  543: 571 */     Map<String, EntidadProceso> mapaProceso = new HashMap();
/*  544: 572 */     Map<String, EntidadSistema> mapaSistema = new HashMap();
/*  545:     */     Iterator localIterator1;
/*  546: 574 */     if (!listaModulo.isEmpty()) {
/*  547: 575 */       for (localIterator1 = listaModulo.iterator(); localIterator1.hasNext();)
/*  548:     */       {
/*  549: 575 */         modulo = (Modulo)localIterator1.next();
/*  550: 576 */         mapaModulo.put(modulo.getNombre() + " " + modulo.getIdOrganizacion(), modulo);
/*  551:     */       }
/*  552:     */     }
/*  553:     */     Modulo modulo;
/*  554: 580 */     Object listaProceso = this.procesoDao.obtenerListaCombo(null, false, null);
/*  555: 581 */     if (!listaModulo.isEmpty()) {
/*  556: 582 */       for (modulo = ((List)listaProceso).iterator(); modulo.hasNext();)
/*  557:     */       {
/*  558: 582 */         proceso = (EntidadProceso)modulo.next();
/*  559: 583 */         mapaProceso.put(proceso.getViewId() + " " + proceso.getSistema().getNombre(), proceso);
/*  560:     */       }
/*  561:     */     }
/*  562:     */     EntidadProceso proceso;
/*  563: 587 */     List<EntidadSistema> listaSistema = this.sistemaDao.obtenerListaCombo(null, false, null);
/*  564: 588 */     if (!listaSistema.isEmpty()) {
/*  565: 589 */       for (EntidadSistema sistema : listaSistema) {
/*  566: 590 */         mapaSistema.put(sistema.getNombre(), sistema);
/*  567:     */       }
/*  568:     */     }
/*  569: 594 */     Modulo modulo = null;
/*  570: 595 */     EntidadProceso proceso = null;
/*  571: 596 */     EntidadSistema entidadSistema = null;
/*  572: 597 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "modulo.xml";
/*  573:     */     
/*  574: 599 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  575: 600 */     Element nodoPrincipal = document.getRootElement();
/*  576: 601 */     List<Element> listaModuloXML = nodoPrincipal.getChildren("modulo");
/*  577: 603 */     for (Element moduloXML : listaModuloXML)
/*  578:     */     {
/*  579: 604 */       String nombre = moduloXML.getChildText("nombre");
/*  580: 605 */       String orden = moduloXML.getChildText("orden");
/*  581: 606 */       String idOrganizacion = moduloXML.getChildText("idOrganizacion");
/*  582: 607 */       String idSucursal = moduloXML.getChildText("idSucursal");
/*  583: 609 */       if (!mapaModulo.containsKey(nombre + " " + idOrganizacion))
/*  584:     */       {
/*  585: 610 */         modulo = new Modulo();
/*  586: 611 */         modulo.setNombre(nombre);
/*  587: 612 */         modulo.setOrden(Integer.parseInt(orden));
/*  588: 613 */         modulo.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  589: 614 */         modulo.setIdSucursal(Integer.parseInt(idSucursal));
/*  590:     */         
/*  591: 616 */         this.moduloDao.guardar(modulo);
/*  592:     */       }
/*  593:     */       else
/*  594:     */       {
/*  595: 618 */         modulo = (Modulo)mapaModulo.get(nombre + " " + idOrganizacion);
/*  596:     */       }
/*  597: 621 */       Element procesosModuloXML = moduloXML.getChild("procesosModulo");
/*  598: 622 */       List<Element> listaProcesoXML = procesosModuloXML.getChildren("proceso");
/*  599: 624 */       for (Element procesoXML : listaProcesoXML) {
/*  600: 625 */         if (procesoXML.getChildText("idSucursal") != null)
/*  601:     */         {
/*  602: 627 */           String idSucursalProceso = procesoXML.getChildText("idSucursal");
/*  603: 628 */           String idOrganizacionProceso = procesoXML.getChildText("idOrganizacion");
/*  604: 629 */           String ordenProceso = procesoXML.getChildText("orden");
/*  605: 630 */           String viewIdProceso = procesoXML.getChildTextTrim("viewId");
/*  606: 631 */           String oldViewIdProceso = procesoXML.getChildTextTrim("oldViewId");
/*  607: 632 */           String viewNameProceso = procesoXML.getChildText("viewName");
/*  608: 633 */           String indicadorMostrarMenu = procesoXML.getChildText("indicadorMostrarMenu");
/*  609: 634 */           String activo = procesoXML.getChildText("activo");
/*  610: 635 */           String sistemaProceso = procesoXML.getChildText("sistema");
/*  611: 638 */           if ((oldViewIdProceso != null) && (!mapaProceso.containsKey(viewIdProceso + " " + sistemaProceso)))
/*  612:     */           {
/*  613: 639 */             EntidadProceso procesoOld = (EntidadProceso)mapaProceso.get(oldViewIdProceso + " " + sistemaProceso);
/*  614: 640 */             if (procesoOld != null)
/*  615:     */             {
/*  616: 641 */               procesoOld.setViewId(viewIdProceso);
/*  617: 642 */               procesoOld.setViewName(viewNameProceso);
/*  618: 643 */               this.procesoDao.guardar(procesoOld);
/*  619: 644 */               continue;
/*  620:     */             }
/*  621:     */           }
/*  622: 648 */           if (!mapaProceso.containsKey(viewIdProceso + " " + sistemaProceso))
/*  623:     */           {
/*  624: 649 */             proceso = new EntidadProceso();
/*  625: 650 */             proceso.setIdSucursal(Integer.parseInt(idSucursalProceso));
/*  626: 651 */             proceso.setIdOrganizacion(Integer.parseInt(idOrganizacionProceso));
/*  627: 652 */             proceso.setOrden(Integer.parseInt(ordenProceso));
/*  628: 653 */             proceso.setViewId(viewIdProceso);
/*  629: 654 */             proceso.setViewName(viewNameProceso);
/*  630: 655 */             proceso.setModulo(modulo);
/*  631: 656 */             proceso.setIndicadorMostrarMenu(obtenerIndicador(indicadorMostrarMenu));
/*  632: 657 */             proceso.setActivo(obtenerIndicador(activo));
/*  633: 658 */             if (!mapaSistema.containsKey(sistemaProceso))
/*  634:     */             {
/*  635: 659 */               entidadSistema = new EntidadSistema(sistemaProceso);
/*  636: 660 */               this.sistemaDao.guardar(entidadSistema);
/*  637: 661 */               proceso.setSistema(entidadSistema);
/*  638: 662 */               mapaSistema.put(sistemaProceso, entidadSistema);
/*  639:     */             }
/*  640:     */             else
/*  641:     */             {
/*  642: 664 */               proceso.setSistema((EntidadSistema)mapaSistema.get(sistemaProceso));
/*  643:     */             }
/*  644: 666 */             this.procesoDao.guardar(proceso);
/*  645: 667 */             if ((proceso.getViewId().equals("/paginas/seguridad/asignarProcesosOrganizacion.xhtml")) || (baseVacia))
/*  646:     */             {
/*  647: 668 */               List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/*  648: 669 */               for (Organizacion organizacion : listaOrganizacion)
/*  649:     */               {
/*  650: 670 */                 ProcesoOrganizacion procesoOrganizacion = new ProcesoOrganizacion();
/*  651: 671 */                 procesoOrganizacion.setEntidadProceso(proceso);
/*  652: 672 */                 procesoOrganizacion.setOrganizacion(organizacion);
/*  653: 673 */                 this.procesoOrganizacionDao.guardar(procesoOrganizacion);
/*  654:     */               }
/*  655:     */             }
/*  656:     */           }
/*  657:     */         }
/*  658:     */       }
/*  659:     */     }
/*  660:     */   }
/*  661:     */   
/*  662:     */   public void cargarIdiomaDesdeXML()
/*  663:     */     throws ExcepcionAS2
/*  664:     */   {
/*  665: 693 */     Idioma idioma = null;
/*  666: 694 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "idioma.xml";
/*  667:     */     
/*  668: 696 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  669: 697 */     Element nodoPrincipal = document.getRootElement();
/*  670: 698 */     List<Element> listaIdiomaXML = nodoPrincipal.getChildren("idioma");
/*  671: 700 */     for (Element idiomaXML : listaIdiomaXML)
/*  672:     */     {
/*  673: 702 */       String localeXML = idiomaXML.getChildText("locale");
/*  674: 703 */       String nombreXML = idiomaXML.getChildText("nombre");
/*  675:     */       
/*  676: 705 */       idioma = new Idioma();
/*  677: 706 */       idioma.setLocale(new Locale(localeXML));
/*  678: 707 */       idioma.setNombre(nombreXML);
/*  679:     */       
/*  680: 709 */       this.idiomaDao.guardar(idioma);
/*  681:     */     }
/*  682:     */   }
/*  683:     */   
/*  684:     */   public void cargarUsuarioSucursalDesdeXML()
/*  685:     */     throws ExcepcionAS2
/*  686:     */   {
/*  687: 723 */     Map<String, EntidadUsuario> hashMapUsuario = new HashMap();
/*  688: 724 */     Map<String, Sucursal> hashMapSucursal = new HashMap();
/*  689:     */     
/*  690:     */ 
/*  691:     */ 
/*  692: 728 */     List<Sucursal> listaSucursal = this.servicioSucursal.obtenerListaCombo(null, false, null);
/*  693: 729 */     for (Iterator localIterator1 = listaSucursal.iterator(); localIterator1.hasNext();)
/*  694:     */     {
/*  695: 729 */       sucursal = (Sucursal)localIterator1.next();
/*  696: 730 */       hashMapSucursal.put(sucursal.getCodigo(), sucursal);
/*  697:     */     }
/*  698:     */     Sucursal sucursal;
/*  699: 733 */     Object listaUsuario = this.usuarioDao.obtenerListaCombo(null, false, null);
/*  700: 734 */     for (EntidadUsuario usuario : (List)listaUsuario) {
/*  701: 735 */       hashMapUsuario.put(usuario.getIdentificacion(), usuario);
/*  702:     */     }
/*  703: 738 */     UsuarioSucursal usuarioSucursal = null;
/*  704: 739 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "usuarioSucursal.xml";
/*  705:     */     
/*  706: 741 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  707: 742 */     Element nodoPrincipal = document.getRootElement();
/*  708: 743 */     List<Element> listaUsurioSucursalXML = nodoPrincipal.getChildren("usuarioSucursal");
/*  709:     */     
/*  710: 745 */     Map<String, UsuarioOrganizacion> mapaUsuarioOrganizacion = new HashMap();
/*  711: 747 */     for (Element usuarioSucursalXML : listaUsurioSucursalXML)
/*  712:     */     {
/*  713: 748 */       String identificacion = usuarioSucursalXML.getChildText("identificacion");
/*  714: 749 */       String codigoSucursal = usuarioSucursalXML.getChildText("codigoSucursal");
/*  715: 750 */       String idOrganizacion = usuarioSucursalXML.getChildText("idOrganizacion");
/*  716: 751 */       String predeterminadoUsuarioSucursal = usuarioSucursalXML.getChildText("predeterminado");
/*  717:     */       
/*  718: 753 */       usuarioSucursal = new UsuarioSucursal();
/*  719: 754 */       usuarioSucursal.setEntidadUsuario((EntidadUsuario)hashMapUsuario.get(identificacion.trim()));
/*  720: 755 */       usuarioSucursal.setSucursal((Sucursal)hashMapSucursal.get(codigoSucursal));
/*  721: 756 */       usuarioSucursal.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  722: 757 */       usuarioSucursal.setPredeterminado(obtenerIndicador(predeterminadoUsuarioSucursal));
/*  723:     */       
/*  724: 759 */       this.usuarioSucursalDao.guardar(usuarioSucursal);
/*  725: 760 */       UsuarioOrganizacion usuarioOrganizacion = new UsuarioOrganizacion();
/*  726: 761 */       usuarioOrganizacion.setEntidadUsuario(usuarioSucursal.getEntidadUsuario());
/*  727: 762 */       usuarioOrganizacion.setOrganizacion((Organizacion)this.organizacionDao.buscarPorId(Integer.valueOf(Integer.parseInt(idOrganizacion))));
/*  728: 763 */       mapaUsuarioOrganizacion.put(identificacion.trim() + "-" + idOrganizacion, usuarioOrganizacion);
/*  729:     */     }
/*  730: 766 */     for (UsuarioOrganizacion usuarioOrganizacion : mapaUsuarioOrganizacion.values()) {
/*  731: 767 */       this.usuarioOrganizacionDao.guardar(usuarioOrganizacion);
/*  732:     */     }
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void cargarAccionDesdeXML()
/*  736:     */     throws ExcepcionAS2
/*  737:     */   {
/*  738: 782 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "accion.xml";
/*  739:     */     
/*  740:     */ 
/*  741:     */ 
/*  742: 786 */     EntidadAccion accion = null;
/*  743: 787 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  744: 788 */     Element nodoPrincipal = document.getRootElement();
/*  745: 789 */     List<Element> listaAccionXML = nodoPrincipal.getChildren("accion");
/*  746: 791 */     for (Element accionXML : listaAccionXML)
/*  747:     */     {
/*  748: 793 */       String idOrganizacion = accionXML.getChildText("idOrganizacion");
/*  749: 794 */       String mascara = accionXML.getChildText("mascara");
/*  750: 795 */       String nombre = accionXML.getChildText("nombre");
/*  751:     */       
/*  752: 797 */       accion = new EntidadAccion();
/*  753: 798 */       accion.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  754: 799 */       accion.setNombre(nombre);
/*  755: 800 */       accion.setMascara(Integer.parseInt(mascara));
/*  756:     */       
/*  757: 802 */       this.accionDao.guardar(accion);
/*  758:     */     }
/*  759:     */   }
/*  760:     */   
/*  761:     */   public void cargarPermisoDesdeXML()
/*  762:     */     throws ExcepcionAS2
/*  763:     */   {
/*  764: 817 */     List<EntidadPermiso> listaPermisoAux = this.permisoDao.obtenerRolProceso();
/*  765: 818 */     EntidadAccion accion = this.accionDao.obtenerAccionPorNombre("ALL");
/*  766: 820 */     for (EntidadPermiso permiso : listaPermisoAux)
/*  767:     */     {
/*  768: 821 */       permiso.getListaAccion().add(accion);
/*  769: 822 */       this.permisoDao.guardar(permiso);
/*  770:     */     }
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void cargarConfiguracionDesdeXML()
/*  774:     */     throws ExcepcionAS2
/*  775:     */   {
/*  776: 836 */     Map<Integer, Modulo> hashMapModulo = new HashMap();
/*  777: 837 */     Map<String, Configuracion> mapaConfiguracion = new HashMap();
/*  778:     */     
/*  779:     */ 
/*  780:     */ 
/*  781: 841 */     List<Configuracion> listaConfiguracion = this.configuracionDao.obtenerListaCombo(null, false, null);
/*  782:     */     Iterator localIterator1;
/*  783: 842 */     if (!listaConfiguracion.isEmpty()) {
/*  784: 843 */       for (localIterator1 = listaConfiguracion.iterator(); localIterator1.hasNext();)
/*  785:     */       {
/*  786: 843 */         configuracionDatos = (Configuracion)localIterator1.next();
/*  787: 844 */         mapaConfiguracion.put(configuracionDatos.getParametro().toString() + " " + configuracionDatos.getIdOrganizacion(), configuracionDatos);
/*  788:     */       }
/*  789:     */     }
/*  790:     */     Configuracion configuracionDatos;
/*  791: 849 */     Object listaModulo = this.moduloDao.obtenerListaCombo(null, false, null);
/*  792: 850 */     for (Modulo modulo : (List)listaModulo) {
/*  793: 851 */       hashMapModulo.put(Integer.valueOf(modulo.getOrden()), modulo);
/*  794:     */     }
/*  795: 854 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, false, null);
/*  796:     */     
/*  797: 856 */     Configuracion configuracion = null;
/*  798:     */     Parametro configuracionEnum;
/*  799: 858 */     for (configuracionEnum : Parametro.values()) {
/*  800: 859 */       for (Organizacion organizacion : listaOrganizacion)
/*  801:     */       {
/*  802: 860 */         int idSucursal = 0;
/*  803: 861 */         String idOrganizacion = String.valueOf(organizacion.getIdOrganizacion());
/*  804: 862 */         String parametro = configuracionEnum.name();
/*  805: 863 */         String valor = configuracionEnum.getValorInicial();
/*  806: 864 */         String valorMostrar = configuracionEnum.getValorMostrar();
/*  807: 865 */         String modulo = configuracionEnum.getModulo();
/*  808: 867 */         if (!mapaConfiguracion.containsKey(parametro + " " + idOrganizacion))
/*  809:     */         {
/*  810: 868 */           configuracion = new Configuracion();
/*  811: 869 */           configuracion.setIdSucursal(idSucursal);
/*  812: 870 */           configuracion.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  813: 871 */           configuracion.setParametro(Parametro.obtenerParametro(parametro.trim()));
/*  814: 872 */           configuracion.setValor(valor);
/*  815: 873 */           configuracion.setValorMostrar(valorMostrar);
/*  816: 874 */           if ((listaConfiguracion.isEmpty()) && (Parametro.AS2_HOME.equals(configuracion.getParametro())))
/*  817:     */           {
/*  818: 875 */             configuracion.setValor(ServicioConfiguracion.AS2_HOME);
/*  819: 876 */             configuracion.setValorMostrar(ServicioConfiguracion.AS2_HOME);
/*  820:     */           }
/*  821: 878 */           configuracion.setModulo((Modulo)hashMapModulo.get(Integer.valueOf(Integer.parseInt(modulo))));
/*  822:     */           
/*  823: 880 */           this.configuracionDao.guardar(configuracion);
/*  824:     */         }
/*  825:     */       }
/*  826:     */     }
/*  827:     */   }
/*  828:     */   
/*  829:     */   public void cargarCodigoFormaPagoSRIDesdeXML()
/*  830:     */     throws ExcepcionAS2
/*  831:     */   {
/*  832: 896 */     CodigoFormaPagoSRI codigoFormaPagoSRI = null;
/*  833:     */     
/*  834: 898 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "codigoFormaPagoSRI.xml";
/*  835:     */     
/*  836: 900 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  837: 901 */     Element nodoPrincipal = document.getRootElement();
/*  838: 902 */     List<Element> listaCodigoFormaPagoSRIXML = nodoPrincipal.getChildren("codigoFormaPagoSRI");
/*  839: 904 */     for (Element codigoFormaPagoSRIXML : listaCodigoFormaPagoSRIXML)
/*  840:     */     {
/*  841: 905 */       String idOrganizacion = codigoFormaPagoSRIXML.getChildText("idOrganizacion");
/*  842: 906 */       String activo = codigoFormaPagoSRIXML.getChildText("activo");
/*  843: 907 */       String codigo = codigoFormaPagoSRIXML.getChildText("codigo");
/*  844: 908 */       String nombre = codigoFormaPagoSRIXML.getChildText("nombre");
/*  845: 909 */       String predeterminado = codigoFormaPagoSRIXML.getChildText("predeterminado");
/*  846:     */       
/*  847: 911 */       codigoFormaPagoSRI = new CodigoFormaPagoSRI();
/*  848: 912 */       codigoFormaPagoSRI.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  849: 913 */       codigoFormaPagoSRI.setActivo(obtenerIndicador(activo));
/*  850: 914 */       codigoFormaPagoSRI.setCodigo(codigo);
/*  851: 915 */       codigoFormaPagoSRI.setNombre(nombre);
/*  852: 916 */       codigoFormaPagoSRI.setPredeterminado(obtenerIndicador(predeterminado));
/*  853:     */       
/*  854: 918 */       this.codigoFormaPagoSRIDao.guardar(codigoFormaPagoSRI);
/*  855:     */     }
/*  856:     */   }
/*  857:     */   
/*  858:     */   public void cargarFormaPagoDesdeXML()
/*  859:     */     throws ExcepcionAS2
/*  860:     */   {
/*  861: 933 */     FormaPago formaPago = null;
/*  862:     */     
/*  863: 935 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "formaPago.xml";
/*  864:     */     
/*  865: 937 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  866: 938 */     Element nodoPrincipal = document.getRootElement();
/*  867: 939 */     List<Element> listaFormaPagoXML = nodoPrincipal.getChildren("formaPago");
/*  868: 941 */     for (Element formaPagoXML : listaFormaPagoXML)
/*  869:     */     {
/*  870: 942 */       String idOrganizacion = formaPagoXML.getChildText("idOrganizacion");
/*  871: 943 */       String idSucursal = formaPagoXML.getChildText("idSucursal");
/*  872: 944 */       String codigo = formaPagoXML.getChildText("codigo");
/*  873: 945 */       String nombre = formaPagoXML.getChildText("nombre");
/*  874: 946 */       String predeterminado = formaPagoXML.getChildText("predeterminado");
/*  875: 947 */       String activo = formaPagoXML.getChildText("activo");
/*  876: 948 */       String indicadorRetencionFuente = formaPagoXML.getChildText("indicadorRetencionFuente");
/*  877: 949 */       String indicadorRetencionIVA = formaPagoXML.getChildText("indicadorRetencionIVA");
/*  878: 950 */       String indicadorDepositoAutomatico = formaPagoXML.getChildText("indicadorDepositoAutomatico");
/*  879: 951 */       String indicadorCheque = formaPagoXML.getChildText("indicadorCheque");
/*  880:     */       
/*  881: 953 */       formaPago = new FormaPago();
/*  882: 954 */       formaPago.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  883: 955 */       formaPago.setIdSucursal(Integer.parseInt(idSucursal));
/*  884: 956 */       formaPago.setCodigo(codigo);
/*  885: 957 */       formaPago.setNombre(nombre);
/*  886: 958 */       formaPago.setPredeterminado(obtenerIndicador(predeterminado));
/*  887: 959 */       formaPago.setActivo(obtenerIndicador(activo));
/*  888: 960 */       formaPago.setIndicadorRetencionFuente(obtenerIndicador(indicadorRetencionFuente));
/*  889: 961 */       formaPago.setIndicadorRetencionIva(obtenerIndicador(indicadorRetencionIVA));
/*  890: 962 */       formaPago.setIndicadorDepositoAutomatico(obtenerIndicador(indicadorDepositoAutomatico));
/*  891: 963 */       formaPago.setIndicadorCheque(obtenerIndicador(indicadorCheque));
/*  892:     */       
/*  893: 965 */       this.formaPagoDao.guardar(formaPago);
/*  894:     */     }
/*  895:     */   }
/*  896:     */   
/*  897:     */   public void cargarSecuenciaDesdeXML()
/*  898:     */     throws ExcepcionAS2
/*  899:     */   {
/*  900: 980 */     Secuencia secuencia = null;
/*  901:     */     
/*  902: 982 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "secuencia.xml";
/*  903:     */     
/*  904: 984 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  905: 985 */     Element nodoPrincipal = document.getRootElement();
/*  906: 986 */     List<Element> listaSecuenciaXML = nodoPrincipal.getChildren("secuencia");
/*  907: 988 */     for (Element secuenciaXML : listaSecuenciaXML)
/*  908:     */     {
/*  909: 989 */       String idOrganizacion = secuenciaXML.getChildText("idOrganizacion");
/*  910: 990 */       String idSucursal = secuenciaXML.getChildText("idSucursal");
/*  911: 991 */       String desde = secuenciaXML.getChildText("desde");
/*  912: 992 */       String hasta = secuenciaXML.getChildText("hasta");
/*  913: 993 */       String longitud = secuenciaXML.getChildText("longitud");
/*  914: 994 */       String nombre = secuenciaXML.getChildText("nombre");
/*  915: 995 */       String numero = secuenciaXML.getChildText("numero");
/*  916: 996 */       String prefijo = secuenciaXML.getChildText("prefijo");
/*  917: 997 */       String sufijo = secuenciaXML.getChildText("sufijo");
/*  918: 998 */       String fechaDesde = secuenciaXML.getChildText("fechaDesde");
/*  919: 999 */       String fechaHasta = secuenciaXML.getChildText("fechaHasta");
/*  920:     */       
/*  921:1001 */       secuencia = new Secuencia();
/*  922:1002 */       secuencia.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  923:1003 */       secuencia.setIdSucursal(Integer.parseInt(idSucursal));
/*  924:1004 */       secuencia.setDesde(Integer.parseInt(desde));
/*  925:1005 */       secuencia.setHasta(Integer.parseInt(hasta));
/*  926:1006 */       secuencia.setLongitud(Integer.parseInt(longitud));
/*  927:1007 */       secuencia.setNombre(nombre);
/*  928:1008 */       secuencia.setNumero(Integer.parseInt(numero.trim()));
/*  929:1009 */       secuencia.setPrefijo(prefijo);
/*  930:1010 */       secuencia.setSufijo(sufijo);
/*  931:1011 */       secuencia.setFechaDesde(FuncionesUtiles.stringToDate(fechaDesde, "dd/MM/yyyy"));
/*  932:1012 */       secuencia.setFechaHasta(FuncionesUtiles.stringToDate(fechaHasta, "dd/MM/yyyy"));
/*  933:     */       
/*  934:1014 */       this.secuenciaDao.guardar(secuencia);
/*  935:     */     }
/*  936:     */   }
/*  937:     */   
/*  938:     */   public void cargarTipoAsientoDesdeXML()
/*  939:     */     throws ExcepcionAS2
/*  940:     */   {
/*  941:1030 */     TipoAsiento tipoAsiento = null;
/*  942:1031 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "tipoAsiento.xml";
/*  943:     */     
/*  944:1033 */     List<Secuencia> listaSecuencia = this.secuenciaDao.obtenerListaCombo(null, false, null);
/*  945:1034 */     Map<String, Secuencia> hashMapSecuencia = new HashMap();
/*  946:1035 */     for (Secuencia secuencia : listaSecuencia) {
/*  947:1036 */       hashMapSecuencia.put(secuencia.getPrefijo(), secuencia);
/*  948:     */     }
/*  949:1039 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  950:1040 */     Element nodoPrincipal = document.getRootElement();
/*  951:1041 */     List<Element> listaTipoAsientoXML = nodoPrincipal.getChildren("tipoAsiento");
/*  952:1043 */     for (Element tipoAsientoXML : listaTipoAsientoXML)
/*  953:     */     {
/*  954:1044 */       String idOrganizacion = tipoAsientoXML.getChildText("idOrganizacion");
/*  955:1045 */       String idSucursal = tipoAsientoXML.getChildText("idSucursal");
/*  956:1046 */       String nombre = tipoAsientoXML.getChildText("nombre");
/*  957:1047 */       String descripcion = tipoAsientoXML.getChildText("descripcion");
/*  958:1048 */       String tipoReporteAsiento = tipoAsientoXML.getChildText("tipoReporteAsiento");
/*  959:1049 */       String secuencia = tipoAsientoXML.getChildText("secuencia");
/*  960:1050 */       String indicadorNIIF = tipoAsientoXML.getChildText("indicadorNIIF");
/*  961:1051 */       String reporte = tipoAsientoXML.getChildText("reporte");
/*  962:1052 */       String activo = tipoAsientoXML.getChildText("activo");
/*  963:1053 */       String predeterminado = tipoAsientoXML.getChildText("predeterminado");
/*  964:     */       
/*  965:1055 */       tipoAsiento = new TipoAsiento();
/*  966:1056 */       tipoAsiento.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/*  967:1057 */       tipoAsiento.setIdSucursal(Integer.parseInt(idSucursal));
/*  968:1058 */       tipoAsiento.setNombre(nombre);
/*  969:1059 */       tipoAsiento.setDescripcion(descripcion);
/*  970:1060 */       tipoAsiento.setTipoReporteAsiento(TipoReporteAsiento.obtenerTipoReporteAsiento(tipoReporteAsiento));
/*  971:1061 */       tipoAsiento.setSecuencia((Secuencia)hashMapSecuencia.get(secuencia));
/*  972:1062 */       tipoAsiento.setIndicadorNIIF(obtenerIndicador(indicadorNIIF));
/*  973:1063 */       tipoAsiento.setReporte(reporte);
/*  974:1064 */       tipoAsiento.setActivo(obtenerIndicador(activo));
/*  975:1065 */       tipoAsiento.setPredeterminado(obtenerIndicador(predeterminado));
/*  976:     */       
/*  977:1067 */       this.tipoAsientoDao.guardar(tipoAsiento);
/*  978:     */     }
/*  979:     */   }
/*  980:     */   
/*  981:     */   public void cargarTipoComprobanteSRIDesdeXML()
/*  982:     */     throws ExcepcionAS2
/*  983:     */   {
/*  984:1083 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "tipoComprobante.xml";
/*  985:1084 */     TipoComprobanteSRI tipoComprobanteSRI = null;
/*  986:     */     
/*  987:1086 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/*  988:1087 */     Element nodoPrincipal = document.getRootElement();
/*  989:1088 */     List<Element> listaTipoComprobanteSRIXML = nodoPrincipal.getChildren("tipoComprobanteSRI");
/*  990:1090 */     for (Element creditoTributarioSRIXML : listaTipoComprobanteSRIXML)
/*  991:     */     {
/*  992:1091 */       String idOrganizacion = creditoTributarioSRIXML.getChildText("idOrganizacion");
/*  993:1092 */       String idSucursal = creditoTributarioSRIXML.getChildText("idSucursal");
/*  994:1093 */       String codigo = creditoTributarioSRIXML.getChildText("codigo");
/*  995:1094 */       String nombre = creditoTributarioSRIXML.getChildText("nombre");
/*  996:1095 */       String descripcion = creditoTributarioSRIXML.getChildText("descripcion");
/*  997:1096 */       String fechaDesde = creditoTributarioSRIXML.getChildText("fechaDesde");
/*  998:1097 */       String fechaHasta = creditoTributarioSRIXML.getChildText("fechaHasta");
/*  999:1098 */       String activo = creditoTributarioSRIXML.getChildText("activo");
/* 1000:1099 */       String predeterminado = creditoTributarioSRIXML.getChildText("predeterminado");
/* 1001:     */       
/* 1002:1101 */       tipoComprobanteSRI = new TipoComprobanteSRI();
/* 1003:1102 */       tipoComprobanteSRI.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1004:1103 */       tipoComprobanteSRI.setIdSucursal(Integer.parseInt(idSucursal));
/* 1005:1104 */       tipoComprobanteSRI.setCodigo(codigo);
/* 1006:1105 */       tipoComprobanteSRI.setNombre(nombre);
/* 1007:1106 */       tipoComprobanteSRI.setDescripcion(descripcion);
/* 1008:1107 */       tipoComprobanteSRI.setFechaDesde(FuncionesUtiles.stringToDate(fechaDesde, "dd/MM/yyyy"));
/* 1009:1108 */       tipoComprobanteSRI.setFechaHasta(FuncionesUtiles.stringToDate(fechaHasta, "dd/MM/yyyy"));
/* 1010:1109 */       tipoComprobanteSRI.setActivo(obtenerIndicador(activo));
/* 1011:1110 */       tipoComprobanteSRI.setPredeterminado(obtenerIndicador(predeterminado));
/* 1012:     */       
/* 1013:1112 */       this.tipoComprobanteSRIDao.guardar(tipoComprobanteSRI);
/* 1014:     */     }
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public void cargarConceptosRetencionSRIDesdeXML()
/* 1018:     */     throws ExcepcionAS2
/* 1019:     */   {
/* 1020:1129 */     Map<String, ConceptoRetencionSRI> mapaConceptoRetencionSRI = new HashMap();
/* 1021:     */     
/* 1022:1131 */     List<ConceptoRetencionSRI> listaConceptoRetencionSRI = this.conceptoRetencionSRIDao.obtenerListaCombo(null, true, null);
/* 1023:1132 */     for (ConceptoRetencionSRI conceptoRetencionSRI : listaConceptoRetencionSRI) {
/* 1024:1133 */       mapaConceptoRetencionSRI.put(conceptoRetencionSRI.getClaveUpdateXML(), conceptoRetencionSRI);
/* 1025:     */     }
/* 1026:1136 */     ConceptoRetencionSRI conceptoRetencionSRI = null;
/* 1027:1137 */     String porcentaje = "";
/* 1028:1138 */     String fechaHasta = null;
/* 1029:     */     
/* 1030:1140 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "conceptosRetencion.xml";
/* 1031:     */     
/* 1032:1142 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1033:1143 */     Element nodoPrincipal = document.getRootElement();
/* 1034:     */     
/* 1035:1145 */     List<Element> listaConceptosRetenciXML = nodoPrincipal.getChildren("conceptoRetencion");
/* 1036:     */     
/* 1037:1147 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 1038:1149 */     for (Iterator localIterator2 = listaConceptosRetenciXML.iterator(); localIterator2.hasNext();)
/* 1039:     */     {
/* 1040:1149 */       conceptosRetencionXML = (Element)localIterator2.next();
/* 1041:1151 */       for (Organizacion organizacion : listaOrganizacion)
/* 1042:     */       {
/* 1043:1153 */         String concepto = conceptosRetencionXML.getAttributeValue("concepto");
/* 1044:1155 */         if (!conceptosRetencionXML.getAttributeValue("porcentaje").equals("")) {
/* 1045:1156 */           porcentaje = conceptosRetencionXML.getAttributeValue("porcentaje");
/* 1046:     */         } else {
/* 1047:1158 */           porcentaje = "0.0";
/* 1048:     */         }
/* 1049:1161 */         String codigo = conceptosRetencionXML.getAttributeValue("codigo");
/* 1050:1162 */         String ingresaPorcentaje = conceptosRetencionXML.getAttributeValue("ingresaPorcentaje");
/* 1051:1163 */         String fechaDesde = conceptosRetencionXML.getAttributeValue("fechaInicio");
/* 1052:1165 */         if ((conceptosRetencionXML.getAttributeValue("fechaFin") != null) && (!conceptosRetencionXML.getAttributeValue("fechaFin").equals(""))) {
/* 1053:1166 */           fechaHasta = conceptosRetencionXML.getAttributeValue("fechaFin");
/* 1054:     */         } else {
/* 1055:1168 */           fechaHasta = null;
/* 1056:     */         }
/* 1057:1171 */         String descripcion = conceptosRetencionXML.getAttributeValue("concepto");
/* 1058:1172 */         String activo = conceptosRetencionXML.getAttributeValue("activo");
/* 1059:1173 */         String tipo = conceptosRetencionXML.getAttributeValue("tipo");
/* 1060:     */         
/* 1061:1175 */         conceptoRetencionSRI = new ConceptoRetencionSRI();
/* 1062:1176 */         conceptoRetencionSRI.setIdOrganizacion(organizacion.getId());
/* 1063:1177 */         conceptoRetencionSRI.setIdSucursal(1);
/* 1064:1178 */         conceptoRetencionSRI.setCodigo(codigo);
/* 1065:1179 */         conceptoRetencionSRI.setNombre(concepto);
/* 1066:1180 */         conceptoRetencionSRI.setPorcentaje(new BigDecimal(porcentaje));
/* 1067:1181 */         conceptoRetencionSRI.setIngresaPorcentaje(obtenerIndicador(ingresaPorcentaje));
/* 1068:1182 */         conceptoRetencionSRI.setFechaDesde(FuncionesUtiles.stringToDate(fechaDesde, "dd/MM/yyyy"));
/* 1069:     */         
/* 1070:1184 */         String tipoProducto = conceptosRetencionXML.getAttributeValue("tipoProducto");
/* 1071:1185 */         if ((tipoProducto != null) && (!"".equals(tipoProducto))) {
/* 1072:1186 */           conceptoRetencionSRI.setTipoProducto(TipoProducto.valueOf(tipoProducto));
/* 1073:     */         }
/* 1074:1189 */         if (fechaHasta != null) {
/* 1075:1190 */           conceptoRetencionSRI.setFechaHasta(FuncionesUtiles.stringToDate(fechaHasta, "dd/MM/yyyy"));
/* 1076:     */         }
/* 1077:1192 */         TipoConceptoRetencion tipoConceptoRetencion = TipoConceptoRetencion.obtenerPorNombre(tipo);
/* 1078:1193 */         conceptoRetencionSRI.setTipoConceptoRetencion(tipoConceptoRetencion);
/* 1079:1194 */         conceptoRetencionSRI.setDescripcion(descripcion);
/* 1080:1195 */         conceptoRetencionSRI.setActivo(obtenerIndicador(activo));
/* 1081:1196 */         conceptoRetencionSRI.setPredeterminado(false);
/* 1082:     */         
/* 1083:1198 */         ConceptoRetencionSRI conceptoRetencionSRIMap = (ConceptoRetencionSRI)mapaConceptoRetencionSRI.get(conceptoRetencionSRI.getClaveUpdateXML());
/* 1084:1199 */         if (conceptoRetencionSRIMap == null)
/* 1085:     */         {
/* 1086:1200 */           this.conceptoRetencionSRIDao.guardar(conceptoRetencionSRI);
/* 1087:     */         }
/* 1088:     */         else
/* 1089:     */         {
/* 1090:1202 */           conceptoRetencionSRIMap.setFechaHasta(conceptoRetencionSRI.getFechaHasta());
/* 1091:1203 */           if (conceptoRetencionSRIMap.getTipoProducto() == null) {}
/* 1092:1207 */           this.conceptoRetencionSRIDao.guardar(conceptoRetencionSRIMap);
/* 1093:     */         }
/* 1094:     */       }
/* 1095:     */     }
/* 1096:     */     Element conceptosRetencionXML;
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   public void cargarIBPClasificacionDesdeXML()
/* 1100:     */     throws ExcepcionAS2
/* 1101:     */   {
/* 1102:1218 */     Map<String, IBPClasificacion> mapaIBPClasificacion = new HashMap();
/* 1103:     */     
/* 1104:1220 */     List<IBPClasificacion> listaIBPClasificacion = this.ibpClasificacionDao.obtenerListaCombo(IBPClasificacion.class, null, true, null);
/* 1105:1221 */     for (IBPClasificacion ibpClasificacion : listaIBPClasificacion) {
/* 1106:1222 */       mapaIBPClasificacion.put(ibpClasificacion.getCodigo() + " - " + ibpClasificacion.getIdOrganizacion(), ibpClasificacion);
/* 1107:     */     }
/* 1108:1225 */     IBPClasificacion ibpClasificacion = null;
/* 1109:     */     
/* 1110:1227 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "ibpClasificacion.xml";
/* 1111:     */     
/* 1112:1229 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1113:1230 */     Element nodoPrincipal = document.getRootElement();
/* 1114:     */     
/* 1115:1232 */     List<Element> listaIBPClasificacionXML = nodoPrincipal.getChildren("ibpClasificacion");
/* 1116:     */     
/* 1117:1234 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 1118:1236 */     for (Iterator localIterator2 = listaIBPClasificacionXML.iterator(); localIterator2.hasNext();)
/* 1119:     */     {
/* 1120:1236 */       ibpClasificacionXML = (Element)localIterator2.next();
/* 1121:1238 */       for (Organizacion organizacion : listaOrganizacion)
/* 1122:     */       {
/* 1123:1240 */         String codigo = FuncionesUtiles.completarALaIzquierda('0', 2, ibpClasificacionXML.getAttributeValue("codigo"));
/* 1124:1241 */         String nombre = ibpClasificacionXML.getAttributeValue("nombre");
/* 1125:1242 */         String activo = ibpClasificacionXML.getAttributeValue("activo");
/* 1126:     */         
/* 1127:1244 */         ibpClasificacion = new IBPClasificacion();
/* 1128:1245 */         ibpClasificacion.setIdOrganizacion(organizacion.getId());
/* 1129:1246 */         ibpClasificacion.setIdSucursal(1);
/* 1130:1247 */         ibpClasificacion.setCodigo(codigo);
/* 1131:1248 */         ibpClasificacion.setNombre(nombre);
/* 1132:1249 */         ibpClasificacion.setActivo(obtenerIndicador(activo));
/* 1133:1251 */         if (!mapaIBPClasificacion.containsKey(ibpClasificacion.getCodigo() + " - " + ibpClasificacion.getIdOrganizacion()))
/* 1134:     */         {
/* 1135:1252 */           this.ibpClasificacionDao.guardar(ibpClasificacion);
/* 1136:     */         }
/* 1137:     */         else
/* 1138:     */         {
/* 1139:1256 */           IBPClasificacion ibpClasificacionMap = (IBPClasificacion)mapaIBPClasificacion.get(ibpClasificacion.getCodigo() + " - " + ibpClasificacion.getIdOrganizacion());
/* 1140:1257 */           ibpClasificacionMap.setActivo(ibpClasificacion.isActivo());
/* 1141:1258 */           this.ibpClasificacionDao.guardar(ibpClasificacionMap);
/* 1142:     */         }
/* 1143:     */       }
/* 1144:     */     }
/* 1145:     */     Element ibpClasificacionXML;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public void cargarIBPMarcaDesdeXML()
/* 1149:     */     throws ExcepcionAS2
/* 1150:     */   {
/* 1151:1266 */     Map<String, IBPMarca> mapaIBPMarca = new HashMap();
/* 1152:     */     
/* 1153:1268 */     List<IBPMarca> listaIBPMarca = this.ibpMarcaDao.obtenerListaCombo(IBPMarca.class, null, true, null);
/* 1154:1269 */     for (IBPMarca ibpMarca : listaIBPMarca) {
/* 1155:1270 */       mapaIBPMarca.put(ibpMarca.getCodigo() + " - " + ibpMarca.getIdOrganizacion(), ibpMarca);
/* 1156:     */     }
/* 1157:1273 */     IBPMarca ibpMarca = null;
/* 1158:     */     
/* 1159:1275 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "ibpMarca.xml";
/* 1160:     */     
/* 1161:1277 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1162:1278 */     Element nodoPrincipal = document.getRootElement();
/* 1163:     */     
/* 1164:1280 */     List<Element> listaIBPMarcaXML = nodoPrincipal.getChildren("ibpMarca");
/* 1165:     */     
/* 1166:1282 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 1167:     */     
/* 1168:1284 */     List<IBPClasificacion> listaIBPClasificacion = this.ibpClasificacionDao.obtenerListaCombo(IBPClasificacion.class, "nombre", true, null);
/* 1169:1285 */     Map<String, IBPClasificacion> mapaIBPClasificacion = new HashMap();
/* 1170:1286 */     for (IBPClasificacion ibpClasificacion : listaIBPClasificacion)
/* 1171:     */     {
/* 1172:1287 */       key = ibpClasificacion.getCodigo() + "~" + ibpClasificacion.getIdOrganizacion();
/* 1173:1288 */       mapaIBPClasificacion.put(key, ibpClasificacion);
/* 1174:     */     }
/* 1175:     */     String key;
/* 1176:1291 */     for (??? = listaIBPMarcaXML.iterator(); ???.hasNext();)
/* 1177:     */     {
/* 1178:1291 */       ibpMarcaXML = (Element)???.next();
/* 1179:1293 */       for (Organizacion organizacion : listaOrganizacion)
/* 1180:     */       {
/* 1181:1295 */         String codigo = FuncionesUtiles.completarALaIzquierda('0', 6, ibpMarcaXML.getAttributeValue("codigo"));
/* 1182:1296 */         String nombre = ibpMarcaXML.getAttributeValue("nombre");
/* 1183:1297 */         String activo = ibpMarcaXML.getAttributeValue("activo");
/* 1184:1298 */         String codigoClasificacion = ibpMarcaXML.getAttributeValue("clasificacion");
/* 1185:1299 */         String key = codigoClasificacion + "~" + organizacion.getId();
/* 1186:1300 */         IBPClasificacion ibpClasificacion = (IBPClasificacion)mapaIBPClasificacion.get(key);
/* 1187:1301 */         if (ibpClasificacion != null)
/* 1188:     */         {
/* 1189:1302 */           ibpMarca = new IBPMarca();
/* 1190:1303 */           ibpMarca.setIdOrganizacion(organizacion.getId());
/* 1191:1304 */           ibpMarca.setIdSucursal(1);
/* 1192:1305 */           ibpMarca.setCodigo(codigo);
/* 1193:1306 */           ibpMarca.setNombre(nombre);
/* 1194:1307 */           ibpMarca.setActivo(obtenerIndicador(activo));
/* 1195:1308 */           ibpMarca.setIbpClasificacion(ibpClasificacion);
/* 1196:1310 */           if (!mapaIBPMarca.containsKey(ibpMarca.getCodigo() + " - " + ibpMarca.getIdOrganizacion()))
/* 1197:     */           {
/* 1198:1311 */             this.ibpMarcaDao.guardar(ibpMarca);
/* 1199:     */           }
/* 1200:     */           else
/* 1201:     */           {
/* 1202:1314 */             IBPMarca ibpMarcaMap = (IBPMarca)mapaIBPMarca.get(ibpMarca.getCodigo() + " - " + ibpMarca.getIdOrganizacion());
/* 1203:1315 */             ibpMarcaMap.setActivo(ibpMarca.isActivo());
/* 1204:1316 */             this.ibpMarcaDao.guardar(ibpMarcaMap);
/* 1205:     */           }
/* 1206:     */         }
/* 1207:     */       }
/* 1208:     */     }
/* 1209:     */     Element ibpMarcaXML;
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public void cargarIBPCapacidadDesdeXML()
/* 1213:     */     throws ExcepcionAS2
/* 1214:     */   {
/* 1215:1325 */     Map<String, IBPCapacidad> mapaIBPCapacidad = new HashMap();
/* 1216:     */     
/* 1217:1327 */     List<IBPCapacidad> listaIBPCapacidad = this.ibpCapacidadDao.obtenerListaCombo(IBPCapacidad.class, null, true, null);
/* 1218:1328 */     for (IBPCapacidad ibpCapacidad : listaIBPCapacidad) {
/* 1219:1329 */       mapaIBPCapacidad.put(ibpCapacidad.getCodigo() + " - " + ibpCapacidad.getIdOrganizacion(), ibpCapacidad);
/* 1220:     */     }
/* 1221:1332 */     IBPCapacidad ibpCapacidad = null;
/* 1222:     */     
/* 1223:1334 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "ibpCapacidad.xml";
/* 1224:     */     
/* 1225:1336 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1226:1337 */     Element nodoPrincipal = document.getRootElement();
/* 1227:     */     
/* 1228:1339 */     List<Element> listaIBPCapacidadXML = nodoPrincipal.getChildren("ibpCapacidad");
/* 1229:     */     
/* 1230:1341 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 1231:1343 */     for (Iterator localIterator2 = listaIBPCapacidadXML.iterator(); localIterator2.hasNext();)
/* 1232:     */     {
/* 1233:1343 */       ibpCapacidadXML = (Element)localIterator2.next();
/* 1234:1345 */       for (Organizacion organizacion : listaOrganizacion)
/* 1235:     */       {
/* 1236:1347 */         String codigo = FuncionesUtiles.completarALaIzquierda('0', 6, ibpCapacidadXML.getAttributeValue("codigo"));
/* 1237:1348 */         String nombre = ibpCapacidadXML.getAttributeValue("nombre");
/* 1238:1349 */         String activo = ibpCapacidadXML.getAttributeValue("activo");
/* 1239:     */         
/* 1240:1351 */         ibpCapacidad = new IBPCapacidad();
/* 1241:1352 */         ibpCapacidad.setIdOrganizacion(organizacion.getId());
/* 1242:1353 */         ibpCapacidad.setIdSucursal(1);
/* 1243:1354 */         ibpCapacidad.setCodigo(codigo);
/* 1244:1355 */         ibpCapacidad.setNombre(nombre);
/* 1245:1356 */         ibpCapacidad.setActivo(obtenerIndicador(activo));
/* 1246:1358 */         if (!mapaIBPCapacidad.containsKey(ibpCapacidad.getCodigo() + " - " + ibpCapacidad.getIdOrganizacion()))
/* 1247:     */         {
/* 1248:1359 */           this.ibpCapacidadDao.guardar(ibpCapacidad);
/* 1249:     */         }
/* 1250:     */         else
/* 1251:     */         {
/* 1252:1362 */           IBPCapacidad ibpCapacidadMap = (IBPCapacidad)mapaIBPCapacidad.get(ibpCapacidad.getCodigo() + " - " + ibpCapacidad.getIdOrganizacion());
/* 1253:1363 */           ibpCapacidadMap.setActivo(ibpCapacidad.isActivo());
/* 1254:1364 */           this.ibpCapacidadDao.guardar(ibpCapacidadMap);
/* 1255:     */         }
/* 1256:     */       }
/* 1257:     */     }
/* 1258:     */     Element ibpCapacidadXML;
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   public void cargarIBPUnidadDesdeXML()
/* 1262:     */     throws ExcepcionAS2
/* 1263:     */   {
/* 1264:1372 */     Map<String, IBPUnidad> mapaIBPUnidad = new HashMap();
/* 1265:     */     
/* 1266:1374 */     List<IBPUnidad> listaIBPUnidad = this.ibpUnidadDao.obtenerListaCombo(IBPUnidad.class, null, true, null);
/* 1267:1375 */     for (IBPUnidad ibpUnidad : listaIBPUnidad) {
/* 1268:1376 */       mapaIBPUnidad.put(ibpUnidad.getCodigo() + " - " + ibpUnidad.getIdOrganizacion(), ibpUnidad);
/* 1269:     */     }
/* 1270:1379 */     IBPUnidad ibpUnidad = null;
/* 1271:     */     
/* 1272:1381 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "ibpUnidad.xml";
/* 1273:     */     
/* 1274:1383 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1275:1384 */     Element nodoPrincipal = document.getRootElement();
/* 1276:     */     
/* 1277:1386 */     List<Element> listaIBPUnidadXML = nodoPrincipal.getChildren("ibpUnidad");
/* 1278:     */     
/* 1279:1388 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 1280:1390 */     for (Iterator localIterator2 = listaIBPUnidadXML.iterator(); localIterator2.hasNext();)
/* 1281:     */     {
/* 1282:1390 */       ibpUnidadXML = (Element)localIterator2.next();
/* 1283:1392 */       for (Organizacion organizacion : listaOrganizacion)
/* 1284:     */       {
/* 1285:1394 */         String codigo = FuncionesUtiles.completarALaIzquierda('0', 2, ibpUnidadXML.getAttributeValue("codigo"));
/* 1286:1395 */         String nombre = ibpUnidadXML.getAttributeValue("nombre");
/* 1287:1396 */         String activo = ibpUnidadXML.getAttributeValue("activo");
/* 1288:     */         
/* 1289:1398 */         ibpUnidad = new IBPUnidad();
/* 1290:1399 */         ibpUnidad.setIdOrganizacion(organizacion.getId());
/* 1291:1400 */         ibpUnidad.setIdSucursal(1);
/* 1292:1401 */         ibpUnidad.setCodigo(codigo);
/* 1293:1402 */         ibpUnidad.setNombre(nombre);
/* 1294:1403 */         ibpUnidad.setActivo(obtenerIndicador(activo));
/* 1295:1405 */         if (!mapaIBPUnidad.containsKey(ibpUnidad.getCodigo() + " - " + ibpUnidad.getIdOrganizacion()))
/* 1296:     */         {
/* 1297:1406 */           this.ibpUnidadDao.guardar(ibpUnidad);
/* 1298:     */         }
/* 1299:     */         else
/* 1300:     */         {
/* 1301:1409 */           IBPUnidad ibpUnidadMap = (IBPUnidad)mapaIBPUnidad.get(ibpUnidad.getCodigo() + " - " + ibpUnidad.getIdOrganizacion());
/* 1302:1410 */           ibpUnidadMap.setActivo(ibpUnidad.isActivo());
/* 1303:1411 */           this.ibpUnidadDao.guardar(ibpUnidadMap);
/* 1304:     */         }
/* 1305:     */       }
/* 1306:     */     }
/* 1307:     */     Element ibpUnidadXML;
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public void cargarDocumentoDesdeXML()
/* 1311:     */     throws ExcepcionAS2
/* 1312:     */   {
/* 1313:1428 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "documento.xml";
/* 1314:1429 */     Documento documento = null;
/* 1315:     */     
/* 1316:1431 */     List<Secuencia> listaSecuencia = this.secuenciaDao.obtenerListaCombo(null, false, null);
/* 1317:1432 */     Map<String, Secuencia> hashMapSecuencia = new HashMap();
/* 1318:1433 */     for (Secuencia secuencia : listaSecuencia) {
/* 1319:1434 */       hashMapSecuencia.put(secuencia.getPrefijo(), secuencia);
/* 1320:     */     }
/* 1321:1437 */     Object listaTipoAsiento = this.tipoAsientoDao.obtenerListaCombo(null, false, null);
/* 1322:1438 */     Map<String, TipoAsiento> hashMapTipoAsiento = new HashMap();
/* 1323:1439 */     for (TipoAsiento tipoAsiento : (List)listaTipoAsiento) {
/* 1324:1440 */       hashMapTipoAsiento.put(tipoAsiento.getNombre(), tipoAsiento);
/* 1325:     */     }
/* 1326:1442 */     Object listaTipoComprobanteSRI = this.tipoComprobanteSRIDao.obtenerListaCombo(null, false, null);
/* 1327:1443 */     Map<String, TipoComprobanteSRI> hashMapTipoComprobanteSRI = new HashMap();
/* 1328:1444 */     for (TipoComprobanteSRI tipoComprobanteSRI : (List)listaTipoComprobanteSRI) {
/* 1329:1445 */       hashMapTipoComprobanteSRI.put(tipoComprobanteSRI.getCodigo(), tipoComprobanteSRI);
/* 1330:     */     }
/* 1331:1448 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1332:1449 */     Element nodoPrincipal = document.getRootElement();
/* 1333:1450 */     List<Element> listaDocumentoXML = nodoPrincipal.getChildren("documento");
/* 1334:1452 */     for (Element documentoXML : listaDocumentoXML)
/* 1335:     */     {
/* 1336:1453 */       String idOrganizacion = documentoXML.getChildText("idOrganizacion");
/* 1337:1454 */       String idSucursal = documentoXML.getChildText("idSucursal");
/* 1338:1455 */       String codigo = documentoXML.getChildText("codigo");
/* 1339:1456 */       String documentoBase = documentoXML.getChildText("documentoBase");
/* 1340:1457 */       String nombre = documentoXML.getChildText("nombre");
/* 1341:1458 */       String operacion = documentoXML.getChildText("operacion");
/* 1342:1459 */       String descripcion = documentoXML.getChildText("descripcion");
/* 1343:1460 */       String activo = documentoXML.getChildText("activo");
/* 1344:1461 */       String predeterminado = documentoXML.getChildText("predeterminado");
/* 1345:1462 */       String secuencia = documentoXML.getChildText("secuencia");
/* 1346:1463 */       String reporte = documentoXML.getChildText("reporte");
/* 1347:1464 */       String tipoAsiento = documentoXML.getChildText("tipoAsiento");
/* 1348:1465 */       String tipoComprobante = documentoXML.getChildText("tipoComprobante");
/* 1349:1466 */       String indicadorDocumentoTributario = documentoXML.getChildText("indicadorDocumentoTributario");
/* 1350:1467 */       String indicadorGeneraCosto = documentoXML.getChildText("indicadorGeneraCosto");
/* 1351:1468 */       String indicadorDocumentoElectronico = documentoXML.getChildText("indicadorDocumentoElectronico");
/* 1352:     */       
/* 1353:1470 */       documento = new Documento();
/* 1354:1471 */       documento.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1355:1472 */       documento.setIdSucursal(Integer.parseInt(idSucursal));
/* 1356:1473 */       documento.setCodigo(codigo);
/* 1357:1474 */       documento.setDocumentoBase(DocumentoBase.obtenerDocumento(documentoBase));
/* 1358:1475 */       documento.setNombre(nombre);
/* 1359:1476 */       documento.setOperacion(Short.parseShort(operacion));
/* 1360:1477 */       documento.setDescripcion(descripcion);
/* 1361:1478 */       documento.setActivo(obtenerIndicador(activo));
/* 1362:1479 */       documento.setPredeterminado(obtenerIndicador(predeterminado));
/* 1363:1480 */       documento.setSecuencia((Secuencia)hashMapSecuencia.get(secuencia));
/* 1364:1481 */       documento.setReporte(reporte);
/* 1365:1482 */       documento.setTipoAsiento((TipoAsiento)hashMapTipoAsiento.get(tipoAsiento));
/* 1366:1483 */       documento.setTipoComprobanteSRI((TipoComprobanteSRI)hashMapTipoComprobanteSRI.get(tipoComprobante));
/* 1367:1484 */       documento.setIndicadorDocumentoTributario(obtenerIndicador(indicadorDocumentoTributario));
/* 1368:1485 */       documento.setIndicadorGeneraCosto(obtenerIndicador(indicadorGeneraCosto));
/* 1369:1486 */       documento.setIndicadorDocumentoElectronico(obtenerIndicador(indicadorDocumentoElectronico));
/* 1370:     */       
/* 1371:1488 */       this.documentoDao.guardar(documento);
/* 1372:     */     }
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   public void cargarCreditosTributariosSRIDesdeXML()
/* 1376:     */     throws ExcepcionAS2
/* 1377:     */   {
/* 1378:1504 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "creditoTributario.xml";
/* 1379:1505 */     CreditoTributarioSRI creditoTributarioSRI = null;
/* 1380:     */     
/* 1381:1507 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1382:1508 */     Element nodoPrincipal = document.getRootElement();
/* 1383:1509 */     List<Element> listaCreditoTributarioSRIXML = nodoPrincipal.getChildren("creditoTributarioSRI");
/* 1384:1511 */     for (Element creditoTributarioSRIXML : listaCreditoTributarioSRIXML)
/* 1385:     */     {
/* 1386:1513 */       String codigo = creditoTributarioSRIXML.getChildText("codigo");
/* 1387:1514 */       String nombre = creditoTributarioSRIXML.getChildText("nombre");
/* 1388:1515 */       String descripcion = creditoTributarioSRIXML.getChildText("descripcion");
/* 1389:1516 */       String fechaDesde = creditoTributarioSRIXML.getChildText("fechaDesde");
/* 1390:1517 */       String fechaHasta = creditoTributarioSRIXML.getChildText("fechaHasta");
/* 1391:1518 */       String activo = creditoTributarioSRIXML.getChildText("activo");
/* 1392:1519 */       String predeterminado = creditoTributarioSRIXML.getChildText("predeterminado");
/* 1393:     */       
/* 1394:1521 */       creditoTributarioSRI = new CreditoTributarioSRI();
/* 1395:1522 */       creditoTributarioSRI.setCodigo(codigo);
/* 1396:1523 */       creditoTributarioSRI.setNombre(nombre);
/* 1397:1524 */       creditoTributarioSRI.setDescripcion(descripcion);
/* 1398:1525 */       creditoTributarioSRI.setFechaDesde(FuncionesUtiles.stringToDate(fechaDesde, "dd/MM/yyyy"));
/* 1399:1526 */       creditoTributarioSRI.setFechaHasta(FuncionesUtiles.stringToDate(fechaHasta, "dd/MM/yyyy"));
/* 1400:1527 */       creditoTributarioSRI.setActivo(obtenerIndicador(activo));
/* 1401:1528 */       creditoTributarioSRI.setPredeterminado(obtenerIndicador(predeterminado));
/* 1402:     */       
/* 1403:1530 */       this.creditoTributarioSRIDao.guardar(creditoTributarioSRI);
/* 1404:     */     }
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   public void cargarCondicionPagoDesdeXML()
/* 1408:     */     throws ExcepcionAS2
/* 1409:     */   {
/* 1410:1546 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "condicionPago.xml";
/* 1411:1547 */     CondicionPago condicionPago = null;
/* 1412:     */     
/* 1413:1549 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1414:1550 */     Element nodoPrincipal = document.getRootElement();
/* 1415:1551 */     List<Element> listaCondicionPagoXML = nodoPrincipal.getChildren("condicionPago");
/* 1416:1553 */     for (Element condicionPagoXML : listaCondicionPagoXML)
/* 1417:     */     {
/* 1418:1554 */       String idSucursal = condicionPagoXML.getChildText("idSucursal");
/* 1419:1555 */       String idOrganizacion = condicionPagoXML.getChildText("idOrganizacion");
/* 1420:1556 */       String codigo = condicionPagoXML.getChildText("codigo");
/* 1421:1557 */       String nombre = condicionPagoXML.getChildText("nombre");
/* 1422:1558 */       String descripcion = condicionPagoXML.getChildText("descripcion");
/* 1423:1559 */       String diasPlazo = condicionPagoXML.getChildText("diasPlazo");
/* 1424:1560 */       String mesesPlazo = condicionPagoXML.getChildText("mesesPlazo");
/* 1425:1561 */       String diaVencimiento = condicionPagoXML.getChildText("diaVencimiento");
/* 1426:1562 */       String indicadorFechaFija = condicionPagoXML.getChildText("indicadorFechaFija");
/* 1427:1563 */       String activo = condicionPagoXML.getChildText("activo");
/* 1428:1564 */       String predeterminado = condicionPagoXML.getChildText("predeterminado");
/* 1429:     */       
/* 1430:1566 */       condicionPago = new CondicionPago();
/* 1431:1567 */       condicionPago.setIdSucursal(Integer.parseInt(idSucursal));
/* 1432:1568 */       condicionPago.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1433:1569 */       condicionPago.setCodigo(codigo);
/* 1434:1570 */       condicionPago.setNombre(nombre);
/* 1435:1571 */       condicionPago.setDescripcion(descripcion);
/* 1436:1572 */       condicionPago.setDiasPlazo(Integer.parseInt(diasPlazo));
/* 1437:1573 */       condicionPago.setMesesPlazo(Integer.parseInt(mesesPlazo));
/* 1438:1574 */       condicionPago.setDiaVencimiento(Integer.parseInt(diaVencimiento));
/* 1439:1575 */       condicionPago.setIndicadorFechaFija(obtenerIndicador(indicadorFechaFija));
/* 1440:1576 */       condicionPago.setActivo(obtenerIndicador(activo));
/* 1441:1577 */       condicionPago.setPredeterminado(obtenerIndicador(predeterminado));
/* 1442:     */       
/* 1443:1579 */       this.condicionPagoDao.guardar(condicionPago);
/* 1444:     */     }
/* 1445:     */   }
/* 1446:     */   
/* 1447:     */   public void cargarUnidadDesdeXML()
/* 1448:     */     throws ExcepcionAS2
/* 1449:     */   {
/* 1450:1594 */     Unidad unidad = null;
/* 1451:     */     
/* 1452:1596 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "unidad.xml";
/* 1453:1597 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1454:1598 */     Element nodoPrincipal = document.getRootElement();
/* 1455:1599 */     List<Element> listaUnidadXML = nodoPrincipal.getChildren("unidad");
/* 1456:1601 */     for (Element unidadXML : listaUnidadXML)
/* 1457:     */     {
/* 1458:1602 */       String idOrganizacion = unidadXML.getChildText("idOrganizacion");
/* 1459:1603 */       String idSucursal = unidadXML.getChildText("idSucursal");
/* 1460:1604 */       String codigo = unidadXML.getChildText("codigo");
/* 1461:1605 */       String nombre = unidadXML.getChildText("nombre");
/* 1462:1606 */       String descripcion = unidadXML.getChildText("descripcion");
/* 1463:1607 */       String tipoUnidadMedida = unidadXML.getChildText("tipoUnidadMedidad");
/* 1464:1608 */       String numeroDecimales = unidadXML.getChildText("numeroDecimales");
/* 1465:1609 */       String activo = unidadXML.getChildText("activo");
/* 1466:1610 */       String predeterminado = unidadXML.getChildText("predeterminado");
/* 1467:     */       
/* 1468:1612 */       unidad = new Unidad();
/* 1469:1613 */       unidad.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1470:1614 */       unidad.setIdSucursal(Integer.parseInt(idSucursal));
/* 1471:1615 */       unidad.setCodigo(codigo);
/* 1472:1616 */       unidad.setNombre(nombre);
/* 1473:1617 */       unidad.setDescripcion(descripcion);
/* 1474:1618 */       unidad.setTipoUnidadMedida(TipoUnidadMedida.obtieneTipoUnidadMedida(tipoUnidadMedida));
/* 1475:1619 */       unidad.setNumeroDecimales(Integer.valueOf(Integer.parseInt(numeroDecimales)));
/* 1476:1620 */       unidad.setActivo(obtenerIndicador(activo));
/* 1477:1621 */       unidad.setPredeterminado(obtenerIndicador(predeterminado));
/* 1478:     */       
/* 1479:1623 */       this.unidadDao.guardar(unidad);
/* 1480:     */     }
/* 1481:     */   }
/* 1482:     */   
/* 1483:     */   public void cargarMonedaDesdeXML()
/* 1484:     */     throws ExcepcionAS2
/* 1485:     */   {
/* 1486:1638 */     Moneda moneda = null;
/* 1487:     */     
/* 1488:1640 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "moneda.xml";
/* 1489:1641 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1490:1642 */     Element nodoPrincipal = document.getRootElement();
/* 1491:1643 */     List<Element> listaMonedaXML = nodoPrincipal.getChildren("moneda");
/* 1492:1645 */     for (Element monedaXML : listaMonedaXML)
/* 1493:     */     {
/* 1494:1646 */       String idSucursal = monedaXML.getChildText("idSucursal");
/* 1495:1647 */       String idOrganizacion = monedaXML.getChildText("idOrganizacion");
/* 1496:1648 */       String codigo = monedaXML.getChildText("codigo");
/* 1497:1649 */       String nombre = monedaXML.getChildText("nombre");
/* 1498:1650 */       String descripcion = monedaXML.getChildText("descripcion");
/* 1499:1651 */       String activo = monedaXML.getChildText("activo");
/* 1500:1652 */       String predeterminado = monedaXML.getChildText("predeterminado");
/* 1501:     */       
/* 1502:1654 */       moneda = new Moneda();
/* 1503:1655 */       moneda.setIdSucursal(Integer.parseInt(idSucursal));
/* 1504:1656 */       moneda.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1505:1657 */       moneda.setCodigo(codigo);
/* 1506:1658 */       moneda.setNombre(nombre);
/* 1507:1659 */       moneda.setDescripcion(descripcion);
/* 1508:1660 */       moneda.setActivo(obtenerIndicador(activo));
/* 1509:1661 */       moneda.setPredeterminado(obtenerIndicador(predeterminado));
/* 1510:     */       
/* 1511:1663 */       this.monedaDao.guardar(moneda);
/* 1512:     */     }
/* 1513:     */   }
/* 1514:     */   
/* 1515:     */   public void cargarCategoriaImpuestoDesdeXML()
/* 1516:     */     throws ExcepcionAS2
/* 1517:     */   {
/* 1518:1678 */     CategoriaImpuesto categoriaImpuesto = null;
/* 1519:     */     
/* 1520:1680 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "categoriaImpuesto.xml";
/* 1521:1681 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1522:1682 */     Element nodoPrincipal = document.getRootElement();
/* 1523:1683 */     List<Element> listaCategoriaImpuestoXML = nodoPrincipal.getChildren("categoriaImpuesto");
/* 1524:1685 */     for (Element categoriaImpuestoXML : listaCategoriaImpuestoXML)
/* 1525:     */     {
/* 1526:1686 */       String idSucursal = categoriaImpuestoXML.getChildText("idSucursal");
/* 1527:1687 */       String idOrganizacion = categoriaImpuestoXML.getChildText("idOrganizacion");
/* 1528:1688 */       String codigo = categoriaImpuestoXML.getChildText("codigo");
/* 1529:1689 */       String nombre = categoriaImpuestoXML.getChildText("nombre");
/* 1530:1690 */       String descripcion = categoriaImpuestoXML.getChildText("descripcion");
/* 1531:1691 */       String estado = categoriaImpuestoXML.getChildText("estado");
/* 1532:1692 */       String predeterminado = categoriaImpuestoXML.getChildText("predeterminado");
/* 1533:     */       
/* 1534:1694 */       categoriaImpuesto = new CategoriaImpuesto();
/* 1535:1695 */       categoriaImpuesto.setIdSucursal(Integer.parseInt(idSucursal));
/* 1536:1696 */       categoriaImpuesto.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1537:1697 */       categoriaImpuesto.setCodigo(codigo);
/* 1538:1698 */       categoriaImpuesto.setNombre(nombre);
/* 1539:1699 */       categoriaImpuesto.setDescripcion(descripcion);
/* 1540:1700 */       categoriaImpuesto.setEstado(obtenerIndicador(estado));
/* 1541:1701 */       categoriaImpuesto.setPredeterminado(obtenerIndicador(predeterminado));
/* 1542:     */       
/* 1543:1703 */       Impuesto impuesto = null;
/* 1544:1704 */       if (codigo.equals("01")) {
/* 1545:1705 */         impuesto = this.impuestoDao.buscarPorCodigo("2", Integer.parseInt(idOrganizacion));
/* 1546:     */       } else {
/* 1547:1707 */         impuesto = this.impuestoDao.buscarPorCodigo("NOIVA", Integer.parseInt(idOrganizacion));
/* 1548:     */       }
/* 1549:1709 */       categoriaImpuesto.getListaImpuesto().add(impuesto);
/* 1550:     */       
/* 1551:1711 */       this.servicioCategoriaImpuesto.guardar(categoriaImpuesto);
/* 1552:     */     }
/* 1553:     */   }
/* 1554:     */   
/* 1555:     */   public void cargarImpuestoDesdeXML()
/* 1556:     */     throws ExcepcionAS2
/* 1557:     */   {
/* 1558:1726 */     Impuesto impuesto = null;
/* 1559:     */     
/* 1560:1728 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "impuesto.xml";
/* 1561:1729 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1562:1730 */     Element nodoPrincipal = document.getRootElement();
/* 1563:1731 */     List<Element> listaImpuestoXML = nodoPrincipal.getChildren("impuesto");
/* 1564:1733 */     for (Element impuestoXML : listaImpuestoXML)
/* 1565:     */     {
/* 1566:1735 */       idSucursal = impuestoXML.getChildText("idSucursal");
/* 1567:1736 */       idOrganizacion = impuestoXML.getChildText("idOrganizacion");
/* 1568:1737 */       String codigo = impuestoXML.getChildText("codigo");
/* 1569:1738 */       String nombre = impuestoXML.getChildText("nombre");
/* 1570:1739 */       String descripcion = impuestoXML.getChildText("descripcion");
/* 1571:1740 */       String estado = impuestoXML.getChildText("estado");
/* 1572:1741 */       String indicadorImpuestoTributario = impuestoXML.getChildText("indicadorImpuestoTributario");
/* 1573:     */       
/* 1574:1743 */       impuesto = new Impuesto();
/* 1575:1744 */       impuesto.setIdSucursal(Integer.parseInt(idSucursal));
/* 1576:1745 */       impuesto.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1577:1746 */       impuesto.setCodigo(codigo);
/* 1578:1747 */       impuesto.setNombre(nombre);
/* 1579:1748 */       impuesto.setDescripcion(descripcion);
/* 1580:1749 */       impuesto.setEstado(obtenerIndicador(estado));
/* 1581:1750 */       impuesto.setIndicadorImpuestoTributario(obtenerIndicador(indicadorImpuestoTributario));
/* 1582:     */       
/* 1583:1752 */       this.impuestoDao.guardar(impuesto);
/* 1584:     */       
/* 1585:     */ 
/* 1586:1755 */       List<Element> listaRangoImpuestoXML = impuestoXML.getChildren("rangoImpuesto");
/* 1587:1756 */       for (Element rangoImpuestoXML : listaRangoImpuestoXML)
/* 1588:     */       {
/* 1589:1757 */         String porcentajeImpuesto = rangoImpuestoXML.getChildText("porcentajeImpuesto");
/* 1590:1758 */         String indicadorCompra = rangoImpuestoXML.getChildText("indicadorCompra");
/* 1591:1759 */         String indicadorVenta = rangoImpuestoXML.getChildText("indicadorVenta");
/* 1592:1760 */         String fechaDesde = rangoImpuestoXML.getChildText("fechaDesde");
/* 1593:1761 */         String fechaHasta = rangoImpuestoXML.getChildText("fechaHasta");
/* 1594:     */         
/* 1595:     */ 
/* 1596:     */ 
/* 1597:     */ 
/* 1598:1766 */         RangoImpuesto rangoImpuesto = new RangoImpuesto(Integer.parseInt(idSucursal), Integer.parseInt(idOrganizacion), new BigDecimal(porcentajeImpuesto), FuncionesUtiles.stringAFecha(fechaDesde), fechaHasta != null ? FuncionesUtiles.stringAFecha(fechaHasta) : null, obtenerIndicador(indicadorCompra), obtenerIndicador(indicadorVenta), impuesto);
/* 1599:     */         
/* 1600:1768 */         this.rangoImpuestoDao.guardar(rangoImpuesto);
/* 1601:     */       }
/* 1602:     */     }
/* 1603:     */     String idSucursal;
/* 1604:     */     String idOrganizacion;
/* 1605:     */   }
/* 1606:     */   
/* 1607:     */   public void cargarTipoCuentaBancariaDesdeXML()
/* 1608:     */     throws ExcepcionAS2
/* 1609:     */   {
/* 1610:1784 */     TipoCuentaBancaria tipoCuentaBancaria = null;
/* 1611:     */     
/* 1612:1786 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "tipoCuentaBancaria.xml";
/* 1613:1787 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1614:1788 */     Element nodoPrincipal = document.getRootElement();
/* 1615:1789 */     List<Element> listaTipoCuentaBancariaXML = nodoPrincipal.getChildren("tipoCuentaBancaria");
/* 1616:1791 */     for (Element tipoCuentaBancariaXML : listaTipoCuentaBancariaXML)
/* 1617:     */     {
/* 1618:1792 */       String idSucursal = tipoCuentaBancariaXML.getChildText("idSucursal");
/* 1619:1793 */       String idOrganizacion = tipoCuentaBancariaXML.getChildText("idOrganizacion");
/* 1620:1794 */       String codigo = tipoCuentaBancariaXML.getChildText("codigo");
/* 1621:1795 */       String nombre = tipoCuentaBancariaXML.getChildText("nombre");
/* 1622:1796 */       String descripcion = tipoCuentaBancariaXML.getChildText("descripcion");
/* 1623:1797 */       String activo = tipoCuentaBancariaXML.getChildText("activo");
/* 1624:1798 */       String predeterminado = tipoCuentaBancariaXML.getChildText("predeterminado");
/* 1625:     */       
/* 1626:1800 */       tipoCuentaBancaria = new TipoCuentaBancaria();
/* 1627:1801 */       tipoCuentaBancaria.setIdSucursal(Integer.parseInt(idSucursal));
/* 1628:1802 */       tipoCuentaBancaria.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1629:1803 */       tipoCuentaBancaria.setCodigo(codigo);
/* 1630:1804 */       tipoCuentaBancaria.setNombre(nombre);
/* 1631:1805 */       tipoCuentaBancaria.setDescripcion(descripcion);
/* 1632:1806 */       tipoCuentaBancaria.setActivo(obtenerIndicador(activo));
/* 1633:1807 */       tipoCuentaBancaria.setPredeterminado(obtenerIndicador(predeterminado));
/* 1634:     */       
/* 1635:1809 */       this.tipoCuentaBancariaDao.guardar(tipoCuentaBancaria);
/* 1636:     */     }
/* 1637:     */   }
/* 1638:     */   
/* 1639:     */   public void cargarTipoBodegaDesdeXML()
/* 1640:     */     throws ExcepcionAS2
/* 1641:     */   {
/* 1642:1824 */     TipoBodega tipoBodega = null;
/* 1643:1825 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "tipoBodega.xml";
/* 1644:1826 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1645:1827 */     Element nodoPrincipal = document.getRootElement();
/* 1646:1828 */     List<Element> listaTipoBodegaXML = nodoPrincipal.getChildren("tipoBodega");
/* 1647:1830 */     for (Element tipoBodegaXML : listaTipoBodegaXML)
/* 1648:     */     {
/* 1649:1831 */       String idOrganizacion = tipoBodegaXML.getChildText("idOrganizacion");
/* 1650:1832 */       String idSucursal = tipoBodegaXML.getChildText("idSucursal");
/* 1651:1833 */       String codigo = tipoBodegaXML.getChildText("codigo");
/* 1652:1834 */       String nombre = tipoBodegaXML.getChildText("nombre");
/* 1653:1835 */       String descripcion = tipoBodegaXML.getChildText("descripcion");
/* 1654:1836 */       String activo = tipoBodegaXML.getChildText("activo");
/* 1655:1837 */       String predeterminado = tipoBodegaXML.getChildText("predeterminado");
/* 1656:     */       
/* 1657:1839 */       tipoBodega = new TipoBodega();
/* 1658:1840 */       tipoBodega.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1659:1841 */       tipoBodega.setIdSucursal(Integer.parseInt(idSucursal));
/* 1660:1842 */       tipoBodega.setCodigo(codigo);
/* 1661:1843 */       tipoBodega.setNombre(nombre);
/* 1662:1844 */       tipoBodega.setDescripcion(descripcion);
/* 1663:1845 */       tipoBodega.setActivo(obtenerIndicador(activo));
/* 1664:1846 */       tipoBodega.setPredeterminado(obtenerIndicador(predeterminado));
/* 1665:     */       
/* 1666:1848 */       this.tipoBodegaDao.guardar(tipoBodega);
/* 1667:     */     }
/* 1668:     */   }
/* 1669:     */   
/* 1670:     */   public void cargarBodegaDesdeXML()
/* 1671:     */     throws ExcepcionAS2
/* 1672:     */   {
/* 1673:1865 */     Bodega bodega = null;
/* 1674:1866 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "bodega.xml";
/* 1675:1867 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1676:1868 */     Element nodoPrincipal = document.getRootElement();
/* 1677:1869 */     List<Element> listaBodegaXML = nodoPrincipal.getChildren("bodega");
/* 1678:1871 */     for (Element bodegaXML : listaBodegaXML)
/* 1679:     */     {
/* 1680:1872 */       String idOrganizacion = bodegaXML.getChildText("idOrganizacion");
/* 1681:1873 */       String idSucursal = bodegaXML.getChildText("idSucursal");
/* 1682:1874 */       Sucursal sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(Integer.parseInt(idSucursal)));
/* 1683:1875 */       String codigo = bodegaXML.getChildText("codigo");
/* 1684:1876 */       String nombre = bodegaXML.getChildText("nombre");
/* 1685:1877 */       String descripcion = bodegaXML.getChildText("descripcion");
/* 1686:1878 */       String activo = bodegaXML.getChildText("activo");
/* 1687:1879 */       String predeterminado = bodegaXML.getChildText("predeterminado");
/* 1688:1880 */       String tipoBodega = bodegaXML.getChildText("tipoBodega");
/* 1689:1881 */       String ubicacion = bodegaXML.getChildText("ubicacion");
/* 1690:1882 */       String claseBodega = bodegaXML.getChildText("claseBodega");
/* 1691:1883 */       String usuarioBodega = bodegaXML.getChildText("usuarioBodega");
/* 1692:     */       
/* 1693:1885 */       TipoBodega tipoBodegaAux = this.tipoBodegaDao.obtenerBodepaPorNombre(tipoBodega);
/* 1694:     */       
/* 1695:1887 */       Ubicacion ubicacionAux = this.ubicacionDao.obtenerUbicacionPorDireccion1(ubicacion, Integer.parseInt(idOrganizacion));
/* 1696:     */       
/* 1697:1889 */       ClaseBodegaEnum claseBodegaEnum = ClaseBodegaEnum.obtenerClaseBodegaPorNombre(claseBodega);
/* 1698:     */       
/* 1699:1891 */       bodega = new Bodega();
/* 1700:1892 */       bodega.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1701:1893 */       bodega.setSucursal(sucursal);
/* 1702:1894 */       bodega.setCodigo(codigo);
/* 1703:1895 */       bodega.setNombre(nombre);
/* 1704:1896 */       bodega.setDescripcion(descripcion);
/* 1705:1897 */       bodega.setActivo(obtenerIndicador(activo));
/* 1706:1898 */       bodega.setPredeterminado(obtenerIndicador(predeterminado));
/* 1707:1899 */       bodega.setTipoBodega(tipoBodegaAux);
/* 1708:1900 */       bodega.setUbicacion(ubicacionAux);
/* 1709:1901 */       bodega.setClaseBodega(claseBodegaEnum);
/* 1710:1902 */       this.bodegaDao.guardar(bodega);
/* 1711:     */       
/* 1712:1904 */       EntidadUsuario usuario = this.usuarioDao.buscarPorNombreUsuario(usuarioBodega);
/* 1713:1905 */       UsuarioBodega usuarioBodegaAux = new UsuarioBodega();
/* 1714:1906 */       usuarioBodegaAux.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1715:1907 */       usuarioBodegaAux.setIdSucursal(Integer.parseInt(idSucursal));
/* 1716:1908 */       usuarioBodegaAux.setEntidadUsuario(usuario);
/* 1717:1909 */       usuarioBodegaAux.setBodega(bodega);
/* 1718:1910 */       usuarioBodegaAux.setPredeterminado(obtenerIndicador(predeterminado));
/* 1719:     */       
/* 1720:1912 */       this.usuarioBodegaDao.guardar(usuarioBodegaAux);
/* 1721:     */       
/* 1722:1914 */       EntidadUsuario superUsuario = this.usuarioDao.buscarPorNombreUsuario("asinfo");
/* 1723:1915 */       UsuarioBodega usuarioBodegaAuxRoot = new UsuarioBodega();
/* 1724:1916 */       usuarioBodegaAuxRoot.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1725:1917 */       usuarioBodegaAuxRoot.setIdSucursal(Integer.parseInt(idSucursal));
/* 1726:1918 */       usuarioBodegaAuxRoot.setEntidadUsuario(superUsuario);
/* 1727:1919 */       usuarioBodegaAuxRoot.setBodega(bodega);
/* 1728:1920 */       usuarioBodegaAuxRoot.setPredeterminado(obtenerIndicador(predeterminado));
/* 1729:     */       
/* 1730:1922 */       this.usuarioBodegaDao.guardar(usuarioBodegaAuxRoot);
/* 1731:     */     }
/* 1732:     */   }
/* 1733:     */   
/* 1734:     */   public void cargarListaPreciosDesdeXML()
/* 1735:     */     throws ExcepcionAS2
/* 1736:     */   {
/* 1737:1936 */     ListaPrecios listaPrecios = null;
/* 1738:1937 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "listaPrecios.xml";
/* 1739:     */     
/* 1740:1939 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1741:1940 */     Element nodoPrincipal = document.getRootElement();
/* 1742:1941 */     List<Element> listaPreciosXML = nodoPrincipal.getChildren("listaPrecios");
/* 1743:1943 */     for (Element lPreciosXML : listaPreciosXML)
/* 1744:     */     {
/* 1745:1944 */       String idOrganizacion = lPreciosXML.getChildText("idOrganizacion");
/* 1746:1945 */       String idSucursal = lPreciosXML.getChildText("idSucursal");
/* 1747:1946 */       String codigo = lPreciosXML.getChildText("codigo");
/* 1748:1947 */       String nombre = lPreciosXML.getChildText("nombre");
/* 1749:1948 */       String descripcion = lPreciosXML.getChildText("descripcion");
/* 1750:1949 */       String activo = lPreciosXML.getChildText("activo");
/* 1751:1950 */       String predeterminado = lPreciosXML.getChildText("predeterminado");
/* 1752:1951 */       String indicadorVenta = lPreciosXML.getChildText("indicadorVenta");
/* 1753:1952 */       String indicadorCompra = lPreciosXML.getChildText("indicadorCompra");
/* 1754:1953 */       String moneda = lPreciosXML.getChildText("moneda");
/* 1755:     */       
/* 1756:1955 */       Moneda monedaDato = this.servicioMoneda.obtenerPorCodigo(moneda);
/* 1757:     */       
/* 1758:1957 */       listaPrecios = new ListaPrecios();
/* 1759:1958 */       listaPrecios.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1760:1959 */       listaPrecios.setIdSucursal(Integer.parseInt(idSucursal));
/* 1761:1960 */       listaPrecios.setCodigo(codigo);
/* 1762:1961 */       listaPrecios.setNombre(nombre);
/* 1763:1962 */       listaPrecios.setDescripcion(descripcion);
/* 1764:1963 */       listaPrecios.setActivo(obtenerIndicador(activo));
/* 1765:1964 */       listaPrecios.setPredeterminado(obtenerIndicador(predeterminado));
/* 1766:1965 */       listaPrecios.setIndicadorCompra(obtenerIndicador(indicadorCompra));
/* 1767:1966 */       listaPrecios.setIndicadorVenta(obtenerIndicador(indicadorVenta));
/* 1768:1967 */       listaPrecios.setMoneda(monedaDato);
/* 1769:     */       
/* 1770:1969 */       this.listaPreciosDao.guardar(listaPrecios);
/* 1771:     */     }
/* 1772:     */   }
/* 1773:     */   
/* 1774:     */   public void cargarZonaDesdeXML()
/* 1775:     */     throws ExcepcionAS2
/* 1776:     */   {
/* 1777:1981 */     Zona zona = null;
/* 1778:1982 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "zona.xml";
/* 1779:     */     
/* 1780:1984 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1781:1985 */     Element nodoPrincipal = document.getRootElement();
/* 1782:1986 */     List<Element> listaZonaXML = nodoPrincipal.getChildren("zona");
/* 1783:1988 */     for (Element zonaXML : listaZonaXML)
/* 1784:     */     {
/* 1785:1989 */       String idOrganizacion = zonaXML.getChildText("idOrganizacion");
/* 1786:1990 */       String idSucursal = zonaXML.getChildText("idSucursal");
/* 1787:1991 */       String codigo = zonaXML.getChildText("codigo");
/* 1788:1992 */       String nombre = zonaXML.getChildText("nombre");
/* 1789:1993 */       String descripcion = zonaXML.getChildText("descripcion");
/* 1790:1994 */       String activo = zonaXML.getChildText("activo");
/* 1791:1995 */       String predeterminado = zonaXML.getChildText("predeterminado");
/* 1792:     */       
/* 1793:1997 */       zona = new Zona();
/* 1794:1998 */       zona.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1795:1999 */       zona.setIdSucursal(Integer.parseInt(idSucursal));
/* 1796:2000 */       zona.setCodigo(codigo);
/* 1797:2001 */       zona.setNombre(nombre);
/* 1798:2002 */       zona.setDescripcion(descripcion);
/* 1799:2003 */       zona.setActivo(obtenerIndicador(activo));
/* 1800:2004 */       zona.setPredeterminado(obtenerIndicador(predeterminado));
/* 1801:     */       
/* 1802:2006 */       this.zonaDao.guardar(zona);
/* 1803:     */     }
/* 1804:     */   }
/* 1805:     */   
/* 1806:     */   public void cargarCategoriaEmpresa()
/* 1807:     */     throws ExcepcionAS2
/* 1808:     */   {
/* 1809:2018 */     CategoriaEmpresa categoriaEmpresa = null;
/* 1810:2019 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "categoriaEmpresa.xml";
/* 1811:     */     
/* 1812:2021 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1813:2022 */     Element nodoPrincipal = document.getRootElement();
/* 1814:2023 */     List<Element> listaCategoriaEmpresaXML = nodoPrincipal.getChildren("categoriaEmpresa");
/* 1815:2025 */     for (Element categoriaEmpresaXML : listaCategoriaEmpresaXML)
/* 1816:     */     {
/* 1817:2026 */       String idOrganizacion = categoriaEmpresaXML.getChildText("idOrganizacion");
/* 1818:2027 */       String idSucursal = categoriaEmpresaXML.getChildText("idSucursal");
/* 1819:2028 */       String codigo = categoriaEmpresaXML.getChildText("codigo");
/* 1820:2029 */       String nombre = categoriaEmpresaXML.getChildText("nombre");
/* 1821:2030 */       String descripcion = categoriaEmpresaXML.getChildText("descripcion");
/* 1822:2031 */       String activo = categoriaEmpresaXML.getChildText("activo");
/* 1823:2032 */       String predeterminado = categoriaEmpresaXML.getChildText("predeterminado");
/* 1824:     */       
/* 1825:2034 */       categoriaEmpresa = new CategoriaEmpresa();
/* 1826:2035 */       categoriaEmpresa.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1827:2036 */       categoriaEmpresa.setIdSucursal(Integer.parseInt(idSucursal));
/* 1828:2037 */       categoriaEmpresa.setCodigo(codigo);
/* 1829:2038 */       categoriaEmpresa.setNombre(nombre);
/* 1830:2039 */       categoriaEmpresa.setDescripcion(descripcion);
/* 1831:2040 */       categoriaEmpresa.setActivo(obtenerIndicador(activo));
/* 1832:2041 */       categoriaEmpresa.setPredeterminado(obtenerIndicador(predeterminado));
/* 1833:     */       
/* 1834:2043 */       this.categoriaEmpresaDao.guardar(categoriaEmpresa);
/* 1835:     */     }
/* 1836:     */   }
/* 1837:     */   
/* 1838:     */   public void cargarDocumentoVariableProcesoDesdeXML()
/* 1839:     */     throws ExcepcionAS2
/* 1840:     */   {
/* 1841:2054 */     DocumentoVariableProceso documentoVariableProceso = null;
/* 1842:2055 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "documentoVariableProceso.xml";
/* 1843:2056 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1844:2057 */     Element nodoPrincipal = document.getRootElement();
/* 1845:2058 */     List<Element> listaDocumentoVariableProcesoXML = nodoPrincipal.getChildren("DocumentoVariableProceso");
/* 1846:2060 */     for (Element documentoVariableProcesoXML : listaDocumentoVariableProcesoXML)
/* 1847:     */     {
/* 1848:2061 */       String idOrganizacion = documentoVariableProcesoXML.getChildText("idOrganizacion");
/* 1849:2062 */       String idSucursal = documentoVariableProcesoXML.getChildText("idSucursal");
/* 1850:2063 */       String documentoBase = documentoVariableProcesoXML.getChildText("documentoBase");
/* 1851:2064 */       String variableProceso = documentoVariableProcesoXML.getChildText("variableProceso");
/* 1852:     */       
/* 1853:2066 */       documentoVariableProceso = new DocumentoVariableProceso();
/* 1854:2067 */       documentoVariableProceso.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1855:2068 */       documentoVariableProceso.setIdSucursal(Integer.parseInt(idSucursal));
/* 1856:2069 */       documentoVariableProceso.setDocumentoBase(DocumentoBase.valueOf(documentoBase));
/* 1857:2070 */       documentoVariableProceso.setVariableProceso(VariableProcesoEnum.valueOf(variableProceso));
/* 1858:     */       
/* 1859:2072 */       this.documentoVariableProcesoDao.guardar(documentoVariableProceso);
/* 1860:     */     }
/* 1861:     */   }
/* 1862:     */   
/* 1863:     */   public void cargarDocumentoContabilizacionDesdeXML()
/* 1864:     */     throws ExcepcionAS2
/* 1865:     */   {
/* 1866:2085 */     DocumentoContabilizacion documentoContabilizacion = null;
/* 1867:2086 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "documentoContabilizacion.xml";
/* 1868:2087 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1869:2088 */     Element nodoPrincipal = document.getRootElement();
/* 1870:2089 */     List<Element> listaDocumentoContabilizacionXML = nodoPrincipal.getChildren("DocumentoContabilizacion");
/* 1871:2091 */     for (Element documentoContabilizacionXML : listaDocumentoContabilizacionXML)
/* 1872:     */     {
/* 1873:2092 */       String idOrganizacion = documentoContabilizacionXML.getChildText("idOrganizacion");
/* 1874:2093 */       String idSucursal = documentoContabilizacionXML.getChildText("idSucursal");
/* 1875:2094 */       String documentoBase = documentoContabilizacionXML.getChildText("documentoBase");
/* 1876:     */       
/* 1877:2096 */       boolean activo = Integer.parseInt(documentoContabilizacionXML.getChildText("activo")) == 1;
/* 1878:2097 */       boolean debe = Integer.parseInt(documentoContabilizacionXML.getChildText("debe")) == 1;
/* 1879:2098 */       String procesoContabilizacion = documentoContabilizacionXML.getChildText("proceso_contabilizacion");
/* 1880:2099 */       boolean reversaProceso = Integer.parseInt(documentoContabilizacionXML.getChildText("reversa_proceso")) == 1;
/* 1881:     */       
/* 1882:2101 */       documentoContabilizacion = new DocumentoContabilizacion();
/* 1883:2102 */       documentoContabilizacion.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1884:2103 */       documentoContabilizacion.setIdSucursal(Integer.parseInt(idSucursal));
/* 1885:2104 */       documentoContabilizacion.setDocumentoBase(DocumentoBase.valueOf(documentoBase));
/* 1886:2105 */       documentoContabilizacion.setActivo(activo);
/* 1887:2106 */       documentoContabilizacion.setDebe(debe);
/* 1888:2107 */       documentoContabilizacion.setProcesoContabilizacion(ProcesoContabilizacionEnum.valueOf(procesoContabilizacion));
/* 1889:2108 */       documentoContabilizacion.setReversaProceso(reversaProceso);
/* 1890:     */       
/* 1891:2110 */       this.documentoContabilizacionDao.guardar(documentoContabilizacion);
/* 1892:     */     }
/* 1893:     */   }
/* 1894:     */   
/* 1895:     */   public void cargarPaisProvinciaCiudadDesdeXML()
/* 1896:     */     throws ExcepcionAS2
/* 1897:     */   {
/* 1898:2119 */     Pais pais = null;
/* 1899:2120 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "paisProvinciaCiudadParroquia.xml";
/* 1900:2121 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 1901:2122 */     Element nodoPrincipal = document.getRootElement();
/* 1902:2123 */     List<Element> listaPaisesXML = nodoPrincipal.getChildren("Pais");
/* 1903:2125 */     for (Element paisXML : listaPaisesXML)
/* 1904:     */     {
/* 1905:2126 */       idOrganizacion = paisXML.getChildText("idOrganizacion");
/* 1906:2127 */       idSucursal = paisXML.getChildText("idSucursal");
/* 1907:2128 */       boolean activo = Integer.parseInt(paisXML.getChildText("activo")) == 1;
/* 1908:2129 */       boolean predeterminado = Integer.parseInt(paisXML.getChildText("predeterminado")) == 1;
/* 1909:2130 */       String codigoIso = paisXML.getChildText("codigoIso");
/* 1910:2131 */       String codigo = paisXML.getChildText("codigo");
/* 1911:2132 */       String gentilicio = paisXML.getChildText("gentilicio");
/* 1912:2133 */       String nombre = paisXML.getChildText("nombre");
/* 1913:2134 */       Organizacion organizacion = new Organizacion();
/* 1914:2135 */       organizacion.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1915:     */       
/* 1916:2137 */       pais = new Pais();
/* 1917:2138 */       pais.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1918:2139 */       pais.setIdSucursal(Integer.parseInt(idSucursal));
/* 1919:2140 */       pais.setActivo(activo);
/* 1920:2141 */       pais.setPredeterminado(predeterminado);
/* 1921:2142 */       pais.setCodigoIso(codigoIso);
/* 1922:2143 */       pais.setCodigo(codigo);
/* 1923:2144 */       pais.setNombre(nombre);
/* 1924:2145 */       pais.setGentilicio(gentilicio);
/* 1925:2146 */       this.paisDao.guardar(pais);
/* 1926:     */       
/* 1927:2148 */       List<Element> listaProvinciasXML = paisXML.getChildren("Provincia");
/* 1928:2149 */       provincia = null;
/* 1929:2150 */       for (Element provinciaXML : listaProvinciasXML)
/* 1930:     */       {
/* 1931:2151 */         String idOrganizacionProvincia = provinciaXML.getChildText("idOrganizacion");
/* 1932:2152 */         String idSucursalProvincia = provinciaXML.getChildText("idSucursal");
/* 1933:2153 */         String codigoProvincia = provinciaXML.getChildText("codigo");
/* 1934:2154 */         String nombreProvincia = provinciaXML.getChildText("nombre");
/* 1935:2155 */         boolean activoProvincia = Integer.parseInt(provinciaXML.getChildText("activo")) == 1;
/* 1936:2156 */         boolean predeterminadoProvincia = Integer.parseInt(provinciaXML.getChildText("predeterminado")) == 1;
/* 1937:     */         
/* 1938:2158 */         provincia = new Provincia();
/* 1939:2159 */         provincia.setPais(pais);
/* 1940:2160 */         provincia.setIdOrganizacion(Integer.parseInt(idOrganizacionProvincia));
/* 1941:2161 */         provincia.setIdSucursal(Integer.parseInt(idSucursalProvincia));
/* 1942:2162 */         provincia.setActivo(activoProvincia);
/* 1943:2163 */         provincia.setPredeterminado(predeterminadoProvincia);
/* 1944:2164 */         provincia.setCodigo(codigoProvincia);
/* 1945:2165 */         provincia.setNombre(nombreProvincia);
/* 1946:2166 */         this.provinciaDao.guardar(provincia);
/* 1947:     */         
/* 1948:2168 */         List<Element> listaCiudadesXML = provinciaXML.getChildren("Ciudad");
/* 1949:2169 */         ciudad = null;
/* 1950:2170 */         for (Element ciudadXML : listaCiudadesXML)
/* 1951:     */         {
/* 1952:2171 */           String idOrganizacionCiudad = ciudadXML.getChildText("idOrganizacion");
/* 1953:2172 */           String idSucursalCiudad = ciudadXML.getChildText("idSucursal");
/* 1954:2173 */           boolean activoCiudad = Integer.parseInt(ciudadXML.getChildText("activo")) == 1;
/* 1955:2174 */           boolean predeterminadoCiudad = Integer.parseInt(ciudadXML.getChildText("predeterminado")) == 1;
/* 1956:2175 */           String codigoCiudad = ciudadXML.getChildText("codigo");
/* 1957:2176 */           String codigoPostalCiudad = ciudadXML.getChildText("codigoPostal");
/* 1958:2177 */           String nombreCiudad = ciudadXML.getChildText("nombre");
/* 1959:     */           
/* 1960:2179 */           ciudad = new Ciudad();
/* 1961:2180 */           ciudad.setProvincia(provincia);
/* 1962:2181 */           ciudad.setIdOrganizacion(Integer.parseInt(idOrganizacionCiudad));
/* 1963:2182 */           ciudad.setIdSucursal(Integer.parseInt(idSucursalCiudad));
/* 1964:2183 */           ciudad.setActivo(activoCiudad);
/* 1965:2184 */           ciudad.setPredeterminado(predeterminadoCiudad);
/* 1966:2185 */           ciudad.setCodigo(codigoCiudad);
/* 1967:2186 */           ciudad.setCodigoPostal(codigoPostalCiudad);
/* 1968:2187 */           ciudad.setNombre(nombreCiudad);
/* 1969:2188 */           this.ciudadDao.guardar(ciudad);
/* 1970:     */           
/* 1971:2190 */           List<Element> listaParroquiaXML = ciudadXML.getChildren("Parroquia");
/* 1972:2191 */           parroquia = null;
/* 1973:2192 */           for (Element parroquiaXML : listaParroquiaXML)
/* 1974:     */           {
/* 1975:2193 */             String codigoParroquia = parroquiaXML.getChildText("codigoParroquia");
/* 1976:2194 */             String nombreParroquia = parroquiaXML.getChildText("nombreParroquia");
/* 1977:2195 */             String descripcionParroquia = parroquiaXML.getChildText("descripcionParroquia");
/* 1978:2196 */             String activoParroquia = parroquiaXML.getChildText("activoParroquia");
/* 1979:2197 */             String predeterminadoParroquia = parroquiaXML.getChildText("predeterminadoParroquia");
/* 1980:     */             
/* 1981:2199 */             parroquia = new Parroquia();
/* 1982:2200 */             parroquia.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 1983:2201 */             parroquia.setIdSucursal(Integer.parseInt(idSucursal));
/* 1984:2202 */             parroquia.setCodigo(codigoParroquia);
/* 1985:2203 */             parroquia.setNombre(nombreParroquia);
/* 1986:2204 */             parroquia.setDescripcion(descripcionParroquia);
/* 1987:2205 */             parroquia.setActivo(obtenerIndicador(activoParroquia));
/* 1988:2206 */             parroquia.setPredeterminado(obtenerIndicador(predeterminadoParroquia));
/* 1989:2207 */             parroquia.setCiudad(ciudad);
/* 1990:2208 */             this.parroquiaDao.guardar(parroquia);
/* 1991:     */           }
/* 1992:     */         }
/* 1993:     */       }
/* 1994:     */     }
/* 1995:     */     String idOrganizacion;
/* 1996:     */     String idSucursal;
/* 1997:     */     Provincia provincia;
/* 1998:     */     Ciudad ciudad;
/* 1999:     */     Parroquia parroquia;
/* 2000:     */   }
/* 2001:     */   
/* 2002:     */   public void cargarBancosDesdeXML()
/* 2003:     */     throws ExcepcionAS2
/* 2004:     */   {
/* 2005:2221 */     Banco banco = null;
/* 2006:2222 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "bancos.xml";
/* 2007:2223 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2008:2224 */     Element nodoPrincipal = document.getRootElement();
/* 2009:2225 */     List<Element> listaBancoXML = nodoPrincipal.getChildren("Banco");
/* 2010:2227 */     for (Element bancoXML : listaBancoXML)
/* 2011:     */     {
/* 2012:2228 */       String idOrganizacion = bancoXML.getChildText("idOrganizacion");
/* 2013:2229 */       String idSucursal = bancoXML.getChildText("idSucursal");
/* 2014:     */       
/* 2015:2231 */       boolean activo = Integer.parseInt(bancoXML.getChildText("activo")) == 1;
/* 2016:2232 */       boolean predeterminado = Integer.parseInt(bancoXML.getChildText("predeterminado")) == 1;
/* 2017:2233 */       String codigo = bancoXML.getChildText("codigo");
/* 2018:2234 */       String nombre = bancoXML.getChildText("nombre");
/* 2019:     */       
/* 2020:2236 */       banco = new Banco();
/* 2021:2237 */       banco.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2022:2238 */       banco.setIdSucursal(Integer.parseInt(idSucursal));
/* 2023:2239 */       banco.setActivo(activo);
/* 2024:2240 */       banco.setPredeterminado(predeterminado);
/* 2025:2241 */       banco.setCodigo(codigo);
/* 2026:2242 */       banco.setNombre(nombre);
/* 2027:     */       
/* 2028:2244 */       this.bancoDao.guardar(banco);
/* 2029:     */     }
/* 2030:     */   }
/* 2031:     */   
/* 2032:     */   public void cargarQuincenasDesdeXML()
/* 2033:     */     throws ExcepcionAS2
/* 2034:     */   {
/* 2035:2250 */     Quincena quincena = null;
/* 2036:2251 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "quincenas.xml";
/* 2037:2252 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2038:2253 */     Element nodoPrincipal = document.getRootElement();
/* 2039:2254 */     List<Element> listaQuincenaXML = nodoPrincipal.getChildren("Quincena");
/* 2040:2256 */     for (Element quincenaXML : listaQuincenaXML)
/* 2041:     */     {
/* 2042:2257 */       String idOrganizacion = quincenaXML.getChildText("idOrganizacion");
/* 2043:2258 */       String idSucursal = quincenaXML.getChildText("idSucursal");
/* 2044:     */       
/* 2045:2260 */       boolean activo = Integer.parseInt(quincenaXML.getChildText("activo")) == 1;
/* 2046:2261 */       boolean predeterminado = Integer.parseInt(quincenaXML.getChildText("predeterminado")) == 1;
/* 2047:2262 */       String codigo = quincenaXML.getChildText("codigo");
/* 2048:2263 */       String nombre = quincenaXML.getChildText("nombre");
/* 2049:2264 */       String descripcion = quincenaXML.getChildText("descripcion");
/* 2050:2265 */       String diaPago = quincenaXML.getChildText("diaPago");
/* 2051:2266 */       int mesPago = Integer.parseInt(quincenaXML.getChildText("mesPago"));
/* 2052:2267 */       boolean indicadorFiniquito = Integer.parseInt(quincenaXML.getChildText("indicadorFiniquito")) == 1;
/* 2053:     */       
/* 2054:2269 */       quincena = new Quincena();
/* 2055:2270 */       quincena.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2056:2271 */       quincena.setIdSucursal(Integer.parseInt(idSucursal));
/* 2057:2272 */       quincena.setActivo(activo);
/* 2058:2273 */       quincena.setPredeterminado(predeterminado);
/* 2059:2274 */       quincena.setCodigo(codigo);
/* 2060:2275 */       quincena.setNombre(nombre);
/* 2061:2276 */       quincena.setDescripcion(descripcion);
/* 2062:2277 */       quincena.setDiaPago(DiaMes.valueOf(diaPago));
/* 2063:2278 */       quincena.setMesPago(mesPago);
/* 2064:2279 */       quincena.setIndicadorFiniquito(indicadorFiniquito);
/* 2065:     */       
/* 2066:2281 */       this.quincenaDao.guardar(quincena);
/* 2067:     */     }
/* 2068:     */   }
/* 2069:     */   
/* 2070:     */   public void cargarRubrosDesdeXML()
/* 2071:     */     throws ExcepcionAS2
/* 2072:     */   {
/* 2073:2287 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "rubros.xml";
/* 2074:2288 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2075:2289 */     Element nodoPrincipal = document.getRootElement();
/* 2076:2290 */     cargarRubrosDesdeXMLRecursivo(nodoPrincipal, null);
/* 2077:     */   }
/* 2078:     */   
/* 2079:     */   public void cargarHorasExtraDesdeXML()
/* 2080:     */     throws ExcepcionAS2
/* 2081:     */   {
/* 2082:2295 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "horaExtra.xml";
/* 2083:2296 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2084:2297 */     Element nodoPrincipal = document.getRootElement();
/* 2085:2298 */     List<Element> listaHorasExtraXML = nodoPrincipal.getChildren("HoraExtra");
/* 2086:     */     
/* 2087:     */ 
/* 2088:     */ 
/* 2089:2302 */     SimpleDateFormat formatoFecha = new SimpleDateFormat("HH:mm:ss");
/* 2090:2304 */     for (Element horaExtraXML : listaHorasExtraXML)
/* 2091:     */     {
/* 2092:2305 */       String idOrganizacion = horaExtraXML.getChildText("idOrganizacion");
/* 2093:2306 */       String idSucursal = horaExtraXML.getChildText("idSucursal");
/* 2094:     */       
/* 2095:2308 */       String codigo = horaExtraXML.getChildText("codigo");
/* 2096:2309 */       String nombre = horaExtraXML.getChildText("nombre");
/* 2097:2310 */       String descripcion = horaExtraXML.getChildText("descripcion");
/* 2098:2311 */       boolean indicadorDentroHorario = Integer.parseInt(horaExtraXML.getChildText("indicadorDentroHorario")) == 1;
/* 2099:2312 */       boolean indicadorDiaFestivo = Integer.parseInt(horaExtraXML.getChildText("indicadorDiaFestivo")) == 1;
/* 2100:2313 */       boolean indicadorDiaDescanso = Integer.parseInt(horaExtraXML.getChildText("indicadorDiaDescanso")) == 1;
/* 2101:2314 */       boolean indicadorDiaComplementario = false;
/* 2102:2315 */       if (horaExtraXML.getChildText("indicadorDiaComplementario") != null) {
/* 2103:2316 */         indicadorDiaComplementario = Integer.parseInt(horaExtraXML.getChildText("indicadorDiaComplementario")) == 1;
/* 2104:     */       }
/* 2105:2318 */       Date horaDesde = null;
/* 2106:2319 */       Date horaHasta = null;
/* 2107:     */       try
/* 2108:     */       {
/* 2109:2321 */         horaDesde = formatoFecha.parse(horaExtraXML.getChildText("horaDesde"));
/* 2110:2322 */         horaHasta = formatoFecha.parse(horaExtraXML.getChildText("horaHasta"));
/* 2111:     */       }
/* 2112:     */       catch (ParseException e)
/* 2113:     */       {
/* 2114:2324 */         System.out.println("Error al parsear horaDesde y/o horaHasta: " + horaExtraXML.getChildText("horaDesde") + " --- " + horaExtraXML
/* 2115:2325 */           .getChildText("horaHasta"));
/* 2116:     */       }
/* 2117:2326 */       continue;
/* 2118:     */       
/* 2119:     */ 
/* 2120:2329 */       PorCientoHoraExtra porCiento = PorCientoHoraExtra.obtenerPorCientoHoraExtra(horaExtraXML.getChildText("porCiento"));
/* 2121:2330 */       String codigoRubro = horaExtraXML.getChildText("rubro");
/* 2122:2331 */       Map<String, String> filtros = new HashMap();
/* 2123:2332 */       filtros.put("idOrganizacion", idOrganizacion);
/* 2124:2333 */       filtros.put("codigo", "=" + codigoRubro);
/* 2125:2334 */       List<Rubro> listaRubro = this.rubroDao.obtenerListaCombo("nombre", true, filtros);
/* 2126:2335 */       Rubro rubro = null;
/* 2127:2336 */       if (listaRubro.size() > 0)
/* 2128:     */       {
/* 2129:2337 */         rubro = (Rubro)listaRubro.get(0);
/* 2130:     */       }
/* 2131:     */       else
/* 2132:     */       {
/* 2133:2339 */         System.out.println("No se encontro el rubro con codigo: " + codigoRubro);
/* 2134:2340 */         continue;
/* 2135:     */       }
/* 2136:2343 */       HoraExtra horaExtra = new HoraExtra();
/* 2137:2344 */       horaExtra.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2138:2345 */       horaExtra.setIdSucursal(Integer.parseInt(idSucursal));
/* 2139:2346 */       horaExtra.setCodigo(codigo);
/* 2140:2347 */       horaExtra.setNombre(nombre);
/* 2141:2348 */       horaExtra.setDescripcion(descripcion);
/* 2142:2349 */       horaExtra.setIndicadorDentroHorario(indicadorDentroHorario);
/* 2143:2350 */       horaExtra.setIndicadorDiaDescanso(indicadorDiaDescanso);
/* 2144:2351 */       horaExtra.setIndicadorDiaFestivo(indicadorDiaFestivo);
/* 2145:2352 */       horaExtra.setIndicadorDiaComplementario(Boolean.valueOf(indicadorDiaComplementario));
/* 2146:2353 */       horaExtra.setHoraDesde(horaDesde);
/* 2147:2354 */       horaExtra.setHoraHasta(horaHasta);
/* 2148:2355 */       horaExtra.setPorCiento(porCiento);
/* 2149:2356 */       horaExtra.setRubro(rubro);
/* 2150:     */       
/* 2151:2358 */       this.horaExtraDao.guardar(horaExtra);
/* 2152:     */     }
/* 2153:     */   }
/* 2154:     */   
/* 2155:     */   private void cargarRubrosDesdeXMLRecursivo(Element nodo, Rubro rubroPadre)
/* 2156:     */     throws ExcepcionAS2
/* 2157:     */   {
/* 2158:2364 */     Rubro rubro = null;
/* 2159:2365 */     List<Element> listaRubroXML = nodo.getChildren("Rubro");
/* 2160:2366 */     if (listaRubroXML.size() == 0) {
/* 2161:2367 */       return;
/* 2162:     */     }
/* 2163:2370 */     for (Element rubroXML : listaRubroXML)
/* 2164:     */     {
/* 2165:2371 */       String idOrganizacion = rubroXML.getChildText("idOrganizacion");
/* 2166:2372 */       String idSucursal = rubroXML.getChildText("idSucursal");
/* 2167:2373 */       boolean activo = Integer.parseInt(rubroXML.getChildText("activo")) == 1;
/* 2168:2374 */       boolean predeterminado = Integer.parseInt(rubroXML.getChildText("predeterminado")) == 1;
/* 2169:2375 */       String codigo = rubroXML.getChildText("codigo");
/* 2170:2376 */       String nombre = rubroXML.getChildText("nombre");
/* 2171:2377 */       String descripcion = rubroXML.getChildText("descripcion");
/* 2172:2378 */       boolean indicadorImpresionSobre = Integer.parseInt(rubroXML.getChildText("indicadorImpresionSobre")) == 1;
/* 2173:2379 */       boolean indicadorProvision = Integer.parseInt(rubroXML.getChildText("indicadorProvision")) == 1;
/* 2174:2380 */       boolean indicadorCalculoIESS = Integer.parseInt(rubroXML.getChildText("indicadorCalculoIESS")) == 1;
/* 2175:2381 */       int ordenCalculo = Integer.parseInt(rubroXML.getChildText("ordenCalculo"));
/* 2176:2382 */       boolean indicadorTiempo = Integer.parseInt(rubroXML.getChildText("indicadorTiempo")) == 1;
/* 2177:2383 */       boolean indicadorProyectar = Integer.parseInt(rubroXML.getChildText("indicadorProyectar")) == 1;
/* 2178:2384 */       int operacion = Integer.parseInt(rubroXML.getChildText("operacion"));
/* 2179:2385 */       int ordenImpresion = Integer.parseInt(rubroXML.getChildText("ordenImpresion"));
/* 2180:2386 */       boolean indicadorDiasTrabajados = Integer.parseInt(rubroXML.getChildText("indicadorDiasTrabajados")) == 1;
/* 2181:2387 */       boolean indicadorCalculoImpuestoRenta = Integer.parseInt(rubroXML.getChildText("indicadorCalculoImpuestoRenta")) == 1;
/* 2182:2388 */       boolean indicadorPagoFiniquito = Integer.parseInt(rubroXML.getChildText("indicadorPagoFiniquito")) == 1;
/* 2183:2389 */       String tipoRubro = rubroXML.getChildText("tipoRubro");
/* 2184:2390 */       String tipo = rubroXML.getChildText("tipo");
/* 2185:2391 */       String formula = rubroXML.getChildText("formula");
/* 2186:2392 */       String codigoQuincena = rubroXML.getChildText("codigoQuincena");
/* 2187:2393 */       int mesCalculoDesde = Integer.parseInt(rubroXML.getChildText("mesCalculoDesde"));
/* 2188:2394 */       int mesCalculoHasta = Integer.parseInt(rubroXML.getChildText("mesCalculoHasta"));
/* 2189:2395 */       int mesPago = Integer.parseInt(rubroXML.getChildText("mesPago"));
/* 2190:     */       
/* 2191:2397 */       Quincena quincena = this.quincenaDao.obtenerQuincenaPorCodigo(codigoQuincena);
/* 2192:     */       
/* 2193:2399 */       rubro = new Rubro();
/* 2194:2400 */       rubro.setRubroPadre(rubroPadre);
/* 2195:2401 */       rubro.setQuincena(quincena);
/* 2196:2402 */       rubro.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2197:2403 */       rubro.setIdSucursal(Integer.parseInt(idSucursal));
/* 2198:2404 */       rubro.setActivo(activo);
/* 2199:2405 */       rubro.setPredeterminado(predeterminado);
/* 2200:2406 */       rubro.setCodigo(codigo);
/* 2201:2407 */       rubro.setNombre(nombre);
/* 2202:2408 */       rubro.setDescripcion(descripcion);
/* 2203:2409 */       rubro.setIndicadorImpresionSobre(indicadorImpresionSobre);
/* 2204:2410 */       rubro.setIndicadorProvision(indicadorProvision);
/* 2205:2411 */       rubro.setIndicadorCalculoIESS(indicadorCalculoIESS);
/* 2206:2412 */       rubro.setOrdenCalculo(ordenCalculo);
/* 2207:2413 */       rubro.setIndicadorTiempo(indicadorTiempo);
/* 2208:2414 */       rubro.setIndicadorProyectar(indicadorProyectar);
/* 2209:2415 */       rubro.setOperacion(operacion);
/* 2210:2416 */       rubro.setOrdenImpresion(ordenImpresion);
/* 2211:2417 */       rubro.setIndicadorDiasTrabajados(indicadorDiasTrabajados);
/* 2212:2418 */       rubro.setIndicadorCalculoImpuestoRenta(indicadorCalculoImpuestoRenta);
/* 2213:2419 */       rubro.setIndicadorPagoFiniquito(indicadorPagoFiniquito);
/* 2214:2420 */       rubro.setTipoRubro(TipoRubro.valueOf(tipoRubro));
/* 2215:2421 */       rubro.setTipo(TipoRubroEnum.valueOf(tipo));
/* 2216:2422 */       rubro.setValor(BigDecimal.ZERO);
/* 2217:2423 */       rubro.setFormula(formula);
/* 2218:2424 */       rubro.setMesCalculoDesde(mesCalculoDesde);
/* 2219:2425 */       rubro.setMesCalculoHasta(mesCalculoHasta);
/* 2220:2426 */       rubro.setMesPago(mesPago);
/* 2221:2427 */       this.rubroDao.guardar(rubro);
/* 2222:2428 */       cargarRubrosDesdeXMLRecursivo(rubroXML, rubro);
/* 2223:     */     }
/* 2224:     */   }
/* 2225:     */   
/* 2226:     */   public void cargarFiltroProductoDesdeXML()
/* 2227:     */     throws ExcepcionAS2
/* 2228:     */   {
/* 2229:2441 */     FiltroProducto filtroProducto = null;
/* 2230:2442 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "filtroProducto.xml";
/* 2231:2443 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2232:2444 */     Element nodoPrincipal = document.getRootElement();
/* 2233:2445 */     List<Element> listaFiltroProductoXML = nodoPrincipal.getChildren("FiltroProducto");
/* 2234:2447 */     for (Element filtroProductoXML : listaFiltroProductoXML)
/* 2235:     */     {
/* 2236:2449 */       String idOrganizacion = filtroProductoXML.getChildText("idOrganizacion");
/* 2237:2450 */       String idSucursal = filtroProductoXML.getChildText("idSucursal");
/* 2238:     */       
/* 2239:2452 */       filtroProducto = new FiltroProducto();
/* 2240:2453 */       filtroProducto.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2241:2454 */       filtroProducto.setIdSucursal(Integer.parseInt(idSucursal));
/* 2242:2455 */       filtroProducto.setIndicadorCategoriaProducto(true);
/* 2243:2456 */       filtroProducto.setIndicadorCodigoAlterno(true);
/* 2244:2457 */       filtroProducto.setIndicadorCodigo(true);
/* 2245:2458 */       filtroProducto.setIndicadorNombre(true);
/* 2246:2459 */       filtroProducto.setIndicadorNombreComercial(true);
/* 2247:2460 */       filtroProducto.setIndicadorSubcategoriaProducto(true);
/* 2248:2461 */       filtroProducto.setIndicadorUnidad(true);
/* 2249:     */       
/* 2250:2463 */       this.filtroProductoDao.guardar(filtroProducto);
/* 2251:     */     }
/* 2252:     */   }
/* 2253:     */   
/* 2254:     */   public void cargarEstadoProcesoDesdeXML()
/* 2255:     */     throws ExcepcionAS2
/* 2256:     */   {
/* 2257:2471 */     EstadoProceso estadoProceso = null;
/* 2258:2472 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "estadoProceso.xml";
/* 2259:2473 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2260:2474 */     Element nodoPrincipal = document.getRootElement();
/* 2261:2475 */     List<Element> listaEstadoProcesoXML = nodoPrincipal.getChildren("EstadoProceso");
/* 2262:2477 */     for (Element estadoProcesoXML : listaEstadoProcesoXML)
/* 2263:     */     {
/* 2264:2479 */       String idOrganizacion = estadoProcesoXML.getChildText("idOrganizacion");
/* 2265:2480 */       String idSucursal = estadoProcesoXML.getChildText("idSucursal");
/* 2266:2481 */       String documentoBase = estadoProcesoXML.getChildText("documentoBase");
/* 2267:2482 */       String estado = estadoProcesoXML.getChildText("estado");
/* 2268:     */       
/* 2269:2484 */       estadoProceso = new EstadoProceso();
/* 2270:2485 */       estadoProceso.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2271:2486 */       estadoProceso.setIdSucursal(Integer.parseInt(idSucursal));
/* 2272:2487 */       estadoProceso.setDocumentoBase(DocumentoBase.valueOf(documentoBase));
/* 2273:2488 */       estadoProceso.setEstado(Estado.valueOf(estado));
/* 2274:     */       
/* 2275:2490 */       this.estadoProcesoDao.guardar(estadoProceso);
/* 2276:     */     }
/* 2277:     */   }
/* 2278:     */   
/* 2279:     */   public void cargarsEstadoCivilDesdeXML()
/* 2280:     */     throws ExcepcionAS2
/* 2281:     */   {
/* 2282:2503 */     EstadoCivil estadoCivil = null;
/* 2283:     */     
/* 2284:2505 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "estadoCivil.xml";
/* 2285:     */     
/* 2286:2507 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2287:2508 */     Element nodoPrincipal = document.getRootElement();
/* 2288:2509 */     List<Element> listaEstadoCivilXML = nodoPrincipal.getChildren("estadoCivil");
/* 2289:2511 */     for (Element estadoCivilXML : listaEstadoCivilXML)
/* 2290:     */     {
/* 2291:2512 */       String idSucursal = estadoCivilXML.getChildText("idSucursal");
/* 2292:2513 */       String idOrganizacion = estadoCivilXML.getChildText("idOrganizacion");
/* 2293:2514 */       String codigo = estadoCivilXML.getChildText("codigo");
/* 2294:2515 */       String nombre = estadoCivilXML.getChildText("nombre");
/* 2295:2516 */       String descripcion = estadoCivilXML.getChildText("descripcion");
/* 2296:2517 */       String activo = estadoCivilXML.getChildText("activo");
/* 2297:2518 */       String predeterminado = estadoCivilXML.getChildText("predeterminado");
/* 2298:     */       
/* 2299:2520 */       estadoCivil = new EstadoCivil();
/* 2300:2521 */       estadoCivil.setIdSucursal(Integer.parseInt(idSucursal));
/* 2301:2522 */       estadoCivil.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2302:2523 */       estadoCivil.setCodigo(codigo);
/* 2303:2524 */       estadoCivil.setNombre(nombre);
/* 2304:2525 */       estadoCivil.setDescripcion(descripcion);
/* 2305:2526 */       estadoCivil.setActivo(obtenerIndicador(activo));
/* 2306:2527 */       estadoCivil.setPredeterminado(obtenerIndicador(predeterminado));
/* 2307:     */       
/* 2308:2529 */       this.estadoCivilDao.guardar(estadoCivil);
/* 2309:     */     }
/* 2310:     */   }
/* 2311:     */   
/* 2312:     */   public void actualizarTipoIdentificacionDesdeXML()
/* 2313:     */     throws ExcepcionAS2
/* 2314:     */   {
/* 2315:2537 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 2316:2538 */     for (Iterator localIterator1 = listaOrganizacion.iterator(); localIterator1.hasNext();)
/* 2317:     */     {
/* 2318:2538 */       organizacion = (Organizacion)localIterator1.next();
/* 2319:     */       
/* 2320:2540 */       Map<String, String> organizacionFilter = new HashMap();
/* 2321:2541 */       organizacionFilter.put("idOrganizacion", String.valueOf(organizacion.getId()));
/* 2322:     */       
/* 2323:     */ 
/* 2324:2544 */       List<TipoIdentificacion> lTipoIdentificacion = this.tipoIdentificacionDao.obtenerListaCombo(null, true, organizacionFilter);
/* 2325:2545 */       mapaTipoIdentificacion = new HashMap();
/* 2326:2546 */       for (TipoIdentificacion ti : lTipoIdentificacion) {
/* 2327:2547 */         mapaTipoIdentificacion.put(ti.getCodigo(), ti);
/* 2328:     */       }
/* 2329:2551 */       Object lTipoComprobanteSRI = this.tipoComprobanteSRIDao.obtenerListaCombo(null, true, organizacionFilter);
/* 2330:2552 */       mapaTipoComprobanteSRI = new HashMap();
/* 2331:2553 */       for (TipoComprobanteSRI tcsri : (List)lTipoComprobanteSRI) {
/* 2332:2554 */         mapaTipoComprobanteSRI.put(tcsri.getCodigo(), tcsri);
/* 2333:     */       }
/* 2334:2558 */       Object lCreditoTributarioSRI = this.creditoTributarioSRIDao.obtenerListaCombo(null, true, null);
/* 2335:2559 */       mapaCreditoTributarioSRI = new HashMap();
/* 2336:2560 */       for (CreditoTributarioSRI ctsri : (List)lCreditoTributarioSRI) {
/* 2337:2561 */         mapaCreditoTributarioSRI.put(ctsri.getCodigo(), ctsri);
/* 2338:     */       }
/* 2339:2564 */       String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "tipoIdentificacion.xml";
/* 2340:2565 */       Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2341:2566 */       Element nodoPrincipal = document.getRootElement();
/* 2342:2567 */       List<Element> listaTipoIdentificacionXML = nodoPrincipal.getChildren("tipoIdentificacion");
/* 2343:2568 */       for (Element tiXML : listaTipoIdentificacionXML)
/* 2344:     */       {
/* 2345:2570 */         String codigo = tiXML.getChildText("codigo");
/* 2346:2571 */         tipoIdentificacion = (TipoIdentificacion)mapaTipoIdentificacion.get(codigo);
/* 2347:2572 */         if (tipoIdentificacion != null)
/* 2348:     */         {
/* 2349:2576 */           Map<String, String> tcsriFilter = new HashMap();
/* 2350:2577 */           tcsriFilter.put("idOrganizacion", String.valueOf(organizacion.getId()));
/* 2351:2578 */           tcsriFilter.put("tipoIdentificacion.idTipoIdentificacion", String.valueOf(tipoIdentificacion.getId()));
/* 2352:     */           
/* 2353:2580 */           List<TipoIdentificacionComprobanteSRI> lTipoIdentificacionComprobanteSRI = this.tipoIdentificacionComprobanteSRIDao.obtenerListaCombo(TipoIdentificacionComprobanteSRI.class, null, false, tcsriFilter);
/* 2354:2581 */           mapaTipoComprobanteSRIAsignado = new HashMap();
/* 2355:2582 */           for (TipoIdentificacionComprobanteSRI ticsri : lTipoIdentificacionComprobanteSRI) {
/* 2356:2583 */             mapaTipoComprobanteSRIAsignado.put(ticsri.getTipoComprobanteSRI().getCodigo(), ticsri.getTipoComprobanteSRI());
/* 2357:     */           }
/* 2358:2587 */           Element listaTipoComprobanteSRI = tiXML.getChild("listaTipoComprobanteSRI");
/* 2359:2588 */           if (listaTipoComprobanteSRI != null)
/* 2360:     */           {
/* 2361:2591 */             List<Element> listaTipoComprobanteXML = listaTipoComprobanteSRI.getChildren("tipoComprobanteSRI");
/* 2362:2592 */             for (Element tcsriXML : listaTipoComprobanteXML)
/* 2363:     */             {
/* 2364:2593 */               String codigoTC = tcsriXML.getAttributeValue("codigo");
/* 2365:2594 */               tcsri = (TipoComprobanteSRI)mapaTipoComprobanteSRI.get(codigoTC);
/* 2366:2595 */               if (tcsri != null)
/* 2367:     */               {
/* 2368:2598 */                 if (!mapaTipoComprobanteSRIAsignado.containsKey(tcsri.getCodigo()))
/* 2369:     */                 {
/* 2370:2599 */                   TipoIdentificacionComprobanteSRI ticsri = new TipoIdentificacionComprobanteSRI();
/* 2371:2600 */                   ticsri.setIdOrganizacion(organizacion.getId());
/* 2372:2601 */                   ticsri.setIdSucursal(1);
/* 2373:2602 */                   ticsri.setTipoIdentificacion(tipoIdentificacion);
/* 2374:2603 */                   ticsri.setTipoComprobanteSRI(tcsri);
/* 2375:2604 */                   this.tipoIdentificacionComprobanteSRIDao.guardar(ticsri);
/* 2376:2605 */                   mapaTipoComprobanteSRIAsignado.put(tcsri.getCodigo(), tcsri);
/* 2377:     */                 }
/* 2378:2609 */                 Map<String, String> ctsriFilter = new HashMap();
/* 2379:2610 */                 ctsriFilter.put("idOrganizacion", String.valueOf(organizacion.getId()));
/* 2380:2611 */                 ctsriFilter.put("tipoIdentificacion.idTipoIdentificacion", String.valueOf(tipoIdentificacion.getId()));
/* 2381:2612 */                 ctsriFilter.put("tipoComprobanteSRI.idTipoComprobanteSRI", String.valueOf(tcsri.getId()));
/* 2382:     */                 
/* 2383:2614 */                 List<ComprobanteSRICreditoTributarioSRI> lComprobanteSRICreditoTributarioSRI = this.comprobanteSRICreditoTributarioSRIDao.obtenerListaCombo(ComprobanteSRICreditoTributarioSRI.class, null, false, ctsriFilter);
/* 2384:2615 */                 mapaCreditoTributarioSRIAsignado = new HashMap();
/* 2385:2616 */                 for (ComprobanteSRICreditoTributarioSRI cctsri : lComprobanteSRICreditoTributarioSRI) {
/* 2386:2617 */                   mapaCreditoTributarioSRIAsignado.put(cctsri.getCreditoTributarioSRI().getCodigo(), cctsri.getCreditoTributarioSRI());
/* 2387:     */                 }
/* 2388:2621 */                 Element listaCreditoTributarioSRI = tcsriXML.getChild("listaCreditoTributarioSRI");
/* 2389:2622 */                 if (listaCreditoTributarioSRI != null)
/* 2390:     */                 {
/* 2391:2625 */                   List<Element> listaCreditoTributarioXML = listaCreditoTributarioSRI.getChildren("creditoTributarioSRI");
/* 2392:2626 */                   for (Element ctXML : listaCreditoTributarioXML)
/* 2393:     */                   {
/* 2394:2627 */                     String codigoCT = ctXML.getAttributeValue("codigo");
/* 2395:2628 */                     CreditoTributarioSRI ctsri = (CreditoTributarioSRI)mapaCreditoTributarioSRI.get(codigoCT);
/* 2396:2629 */                     if (ctsri != null) {
/* 2397:2632 */                       if (!mapaCreditoTributarioSRIAsignado.containsKey(ctsri.getCodigo()))
/* 2398:     */                       {
/* 2399:2633 */                         ComprobanteSRICreditoTributarioSRI cctsri = new ComprobanteSRICreditoTributarioSRI();
/* 2400:2634 */                         cctsri.setIdOrganizacion(organizacion.getId());
/* 2401:2635 */                         cctsri.setIdSucursal(1);
/* 2402:2636 */                         cctsri.setTipoIdentificacion(tipoIdentificacion);
/* 2403:2637 */                         cctsri.setTipoComprobanteSRI(tcsri);
/* 2404:2638 */                         cctsri.setCreditoTributarioSRI(ctsri);
/* 2405:2639 */                         this.comprobanteSRICreditoTributarioSRIDao.guardar(cctsri);
/* 2406:     */                       }
/* 2407:     */                     }
/* 2408:     */                   }
/* 2409:     */                 }
/* 2410:     */               }
/* 2411:     */             }
/* 2412:     */           }
/* 2413:     */         }
/* 2414:     */       }
/* 2415:     */     }
/* 2416:     */     Organizacion organizacion;
/* 2417:     */     Map<String, TipoIdentificacion> mapaTipoIdentificacion;
/* 2418:     */     Map<String, TipoComprobanteSRI> mapaTipoComprobanteSRI;
/* 2419:     */     Map<String, CreditoTributarioSRI> mapaCreditoTributarioSRI;
/* 2420:     */     TipoIdentificacion tipoIdentificacion;
/* 2421:     */     Map<String, TipoComprobanteSRI> mapaTipoComprobanteSRIAsignado;
/* 2422:     */     TipoComprobanteSRI tcsri;
/* 2423:     */     Map<String, CreditoTributarioSRI> mapaCreditoTributarioSRIAsignado;
/* 2424:     */   }
/* 2425:     */   
/* 2426:     */   public void cargarEstadoChequeDesdeXML()
/* 2427:     */     throws ExcepcionAS2
/* 2428:     */   {
/* 2429:2649 */     Map<String, EstadoCheque> mapaEstadoCheque = new HashMap();
/* 2430:2650 */     List<EstadoCheque> listaEstadoCheque = this.estadoChequeDao.obtenerListaCombo(null, true, null);
/* 2431:2651 */     for (EstadoCheque ec : listaEstadoCheque) {
/* 2432:2652 */       mapaEstadoCheque.put(ec.getCodigo() + " - " + ec.getIdOrganizacion(), ec);
/* 2433:     */     }
/* 2434:2654 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "estadoCheque.xml";
/* 2435:2655 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2436:2656 */     Element nodoPrincipal = document.getRootElement();
/* 2437:2657 */     List<Element> listaECXML = nodoPrincipal.getChildren("estadoCheque");
/* 2438:2658 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 2439:2659 */     for (Iterator localIterator2 = listaECXML.iterator(); localIterator2.hasNext();)
/* 2440:     */     {
/* 2441:2659 */       ecXML = (Element)localIterator2.next();
/* 2442:2660 */       for (Organizacion organizacion : listaOrganizacion)
/* 2443:     */       {
/* 2444:2661 */         String codigo = ecXML.getAttributeValue("codigo");
/* 2445:2662 */         String nombre = ecXML.getAttributeValue("nombre");
/* 2446:2663 */         String estadoInicial = ecXML.getAttributeValue("estadoInicial");
/* 2447:2664 */         String estadoFinal = ecXML.getAttributeValue("estadoFinal");
/* 2448:2665 */         if (!mapaEstadoCheque.containsKey(codigo + " - " + organizacion.getIdOrganizacion()))
/* 2449:     */         {
/* 2450:2666 */           EstadoCheque ec = new EstadoCheque();
/* 2451:2667 */           ec.setIdOrganizacion(organizacion.getId());
/* 2452:2668 */           ec.setIdSucursal(1);
/* 2453:2669 */           ec.setCodigo(codigo);
/* 2454:2670 */           ec.setNombre(nombre);
/* 2455:2671 */           ec.setEstadoInicial(obtenerIndicador(estadoInicial));
/* 2456:2672 */           ec.setEstadoFinal(obtenerIndicador(estadoFinal));
/* 2457:2673 */           this.estadoChequeDao.guardar(ec);
/* 2458:     */         }
/* 2459:     */       }
/* 2460:     */     }
/* 2461:     */     Element ecXML;
/* 2462:     */   }
/* 2463:     */   
/* 2464:     */   public void actualizarConfiguracionConciliacionDesdeXML()
/* 2465:     */     throws ExcepcionAS2
/* 2466:     */   {
/* 2467:2681 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 2468:2682 */     for (Iterator localIterator1 = listaOrganizacion.iterator(); localIterator1.hasNext();)
/* 2469:     */     {
/* 2470:2682 */       organizacion = (Organizacion)localIterator1.next();
/* 2471:     */       
/* 2472:2684 */       Map<String, String> organizacionFilter = new HashMap();
/* 2473:2685 */       organizacionFilter.put("idOrganizacion", String.valueOf(organizacion.getId()));
/* 2474:     */       
/* 2475:     */ 
/* 2476:2688 */       List<Banco> lBanco = this.bancoDao.obtenerListaCombo(Banco.class, "codigo", true, organizacionFilter);
/* 2477:2689 */       mapaBanco = new HashMap();
/* 2478:2690 */       for (Banco b : lBanco) {
/* 2479:2691 */         mapaBanco.put(b.getCodigo(), b);
/* 2480:     */       }
/* 2481:2694 */       String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "bancos.xml";
/* 2482:2695 */       Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2483:2696 */       Element nodoPrincipal = document.getRootElement();
/* 2484:2697 */       List<Element> listaBancoXML = nodoPrincipal.getChildren("Banco");
/* 2485:2698 */       for (Element bancoXML : listaBancoXML)
/* 2486:     */       {
/* 2487:2699 */         Banco banco = (Banco)mapaBanco.get(bancoXML.getChildText("codigo"));
/* 2488:2700 */         if (banco != null)
/* 2489:     */         {
/* 2490:2701 */           Element confConciliacion = bancoXML.getChild("configuracionConciliacion");
/* 2491:2702 */           if (confConciliacion != null)
/* 2492:     */           {
/* 2493:2705 */             columnaMonto = confConciliacion.getAttributeValue("columnaMonto");
/* 2494:2706 */             columnaDocumento = confConciliacion.getAttributeValue("columnaDocumento");
/* 2495:2707 */             filaInicial = confConciliacion.getAttributeValue("filaInicial");
/* 2496:     */             
/* 2497:2709 */             Map<String, String> cboFilter = new HashMap();
/* 2498:2710 */             cboFilter.put("idOrganizacion", String.valueOf(organizacion.getId()));
/* 2499:2711 */             cboFilter.put("cuentaBancaria.banco.idBanco", String.valueOf(banco.getId()));
/* 2500:     */             
/* 2501:2713 */             List<String> lcampos = new ArrayList();
/* 2502:2714 */             lcampos.add("cuentaBancaria");
/* 2503:2715 */             List<CuentaBancariaOrganizacion> lcbo = this.cuentaBancariaOrganizacionDao.obtenerListaPorPagina(CuentaBancariaOrganizacion.class, 0, 1000, "idOrganizacion", true, cboFilter, lcampos);
/* 2504:2718 */             for (CuentaBancariaOrganizacion cbo : lcbo)
/* 2505:     */             {
/* 2506:2719 */               Map<String, String> ccbFilter = new HashMap();
/* 2507:2720 */               ccbFilter.put("idOrganizacion", String.valueOf(organizacion.getId()));
/* 2508:2721 */               ccbFilter.put("cuentaBancariaOrganizacion.idCuentaBancariaOrganizacion", String.valueOf(cbo.getId()));
/* 2509:     */               
/* 2510:2723 */               List<ConfiguracionConciliacionBancaria> lccb = this.configuracionConciliacionBancariaDao.obtenerListaCombo(ConfiguracionConciliacionBancaria.class, "idOrganizacion", true, ccbFilter);
/* 2511:     */               ConfiguracionConciliacionBancaria ccb;
/* 2512:2725 */               if ((lccb != null) && (!lccb.isEmpty()))
/* 2513:     */               {
/* 2514:2726 */                 ConfiguracionConciliacionBancaria ccb = (ConfiguracionConciliacionBancaria)lccb.get(0);
/* 2515:2727 */                 ccb.setColumnaDocumento(Integer.valueOf(Integer.valueOf(columnaDocumento).intValue()));
/* 2516:2728 */                 ccb.setColumnaMonto(Integer.valueOf(Integer.valueOf(columnaMonto).intValue()));
/* 2517:2729 */                 ccb.setFilaInicial(Integer.valueOf(Integer.valueOf(filaInicial).intValue()));
/* 2518:     */               }
/* 2519:     */               else
/* 2520:     */               {
/* 2521:2731 */                 ccb = new ConfiguracionConciliacionBancaria();
/* 2522:2732 */                 ccb.setIdOrganizacion(organizacion.getId());
/* 2523:2733 */                 ccb.setIdSucursal(1);
/* 2524:2734 */                 ccb.setActivo(false);
/* 2525:2735 */                 ccb.setColumnaDocumento(Integer.valueOf(Integer.valueOf(columnaDocumento).intValue()));
/* 2526:2736 */                 ccb.setColumnaMonto(Integer.valueOf(Integer.valueOf(columnaMonto).intValue()));
/* 2527:2737 */                 ccb.setFilaInicial(Integer.valueOf(Integer.valueOf(filaInicial).intValue()));
/* 2528:2738 */                 ccb.setCuentaBancariaOrganizacion(cbo);
/* 2529:     */               }
/* 2530:2740 */               this.configuracionConciliacionBancariaDao.guardar(ccb);
/* 2531:     */             }
/* 2532:     */           }
/* 2533:     */         }
/* 2534:     */       }
/* 2535:     */     }
/* 2536:     */     Organizacion organizacion;
/* 2537:     */     Map<String, Banco> mapaBanco;
/* 2538:     */     String columnaMonto;
/* 2539:     */     String columnaDocumento;
/* 2540:     */     String filaInicial;
/* 2541:     */   }
/* 2542:     */   
/* 2543:     */   public void cargarReporteador()
/* 2544:     */     throws ExcepcionAS2
/* 2545:     */   {
/* 2546:2749 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "reporteador.xml";
/* 2547:2750 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2548:2751 */     Element nodoPrincipal = document.getRootElement();
/* 2549:2752 */     List<Element> listaReporteXML = nodoPrincipal.getChildren("reporte");
/* 2550:2753 */     cargarCabeceraReporteador(listaReporteXML);
/* 2551:     */   }
/* 2552:     */   
/* 2553:     */   public void cargarCabeceraReporteador(List<Element> listaReporteXML)
/* 2554:     */     throws ExcepcionAS2
/* 2555:     */   {
/* 2556:2758 */     List<Reporteador> listaReporteador = this.reporteadorDao.obtenerListaCombo(null, true, null);
/* 2557:     */     
/* 2558:2760 */     Map<String, Reporteador> mapaReporte = new HashMap();
/* 2559:2762 */     if (!listaReporteador.isEmpty()) {
/* 2560:2763 */       for (Reporteador reporteador : listaReporteador) {
/* 2561:2764 */         mapaReporte.put(reporteador.getNombre() + "~" + reporteador.getIdOrganizacion(), reporteador);
/* 2562:     */       }
/* 2563:     */     }
/* 2564:2768 */     Reporteador reporteador = null;
/* 2565:2769 */     int nodo = 1;
/* 2566:2770 */     for (Element reporteXML : listaReporteXML)
/* 2567:     */     {
/* 2568:2771 */       List<Element> listaReportesXML = reporteXML.getChildren("nivel" + nodo);
/* 2569:2772 */       String idSucursal = reporteXML.getChildText("idSucursal");
/* 2570:2773 */       String idOrganizacion = reporteXML.getChildText("idOrganizacion");
/* 2571:2774 */       String nombre = reporteXML.getChildText("nombre");
/* 2572:2775 */       String periocidad = reporteXML.getChildText("periocidad");
/* 2573:2776 */       String ficheroReporte = reporteXML.getChildText("ficheroReporte");
/* 2574:2777 */       if (!mapaReporte.containsKey(nombre + "~" + idOrganizacion))
/* 2575:     */       {
/* 2576:2778 */         reporteador = new Reporteador();
/* 2577:2779 */         reporteador.setIdSucursal(Integer.parseInt(idSucursal));
/* 2578:2780 */         reporteador.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2579:2781 */         reporteador.setNombre(nombre);
/* 2580:2782 */         reporteador.setPeriodicidad(Periodicidad.valueOf(periocidad));
/* 2581:2783 */         reporteador.setFicheroReporte(ficheroReporte);
/* 2582:     */         
/* 2583:2785 */         this.reporteadorDao.guardar(reporteador);
/* 2584:     */         
/* 2585:2787 */         cargarVariablesReporteador(reporteXML, reporteador, idSucursal, idOrganizacion);
/* 2586:2788 */         cargaDetalleReporteadorRecursivo(listaReportesXML, nodo, reporteador, null, idSucursal, idOrganizacion);
/* 2587:     */       }
/* 2588:     */     }
/* 2589:     */   }
/* 2590:     */   
/* 2591:     */   public void cargarVariablesReporteador(Element reporteXML, Reporteador reporteador, String idSucursal, String idOrganizacion)
/* 2592:     */     throws ExcepcionAS2
/* 2593:     */   {
/* 2594:2798 */     List<Element> listaVariablesXML = reporteXML.getChildren("variables");
/* 2595:2800 */     for (Iterator localIterator = listaVariablesXML.iterator(); localIterator.hasNext();)
/* 2596:     */     {
/* 2597:2800 */       variableXML = (Element)localIterator.next();
/* 2598:2801 */       String codigo = variableXML.getChildText("codigo");
/* 2599:2802 */       String tipoCalculo = variableXML.getChildText("tipoCalculo");
/* 2600:2803 */       String valorCalculo = variableXML.getChildText("valorCalculo");
/* 2601:2804 */       String nota = variableXML.getChildText("nota");
/* 2602:     */       
/* 2603:2806 */       DetalleReporteadorVariable drv = new DetalleReporteadorVariable();
/* 2604:2807 */       drv.setIdSucursal(Integer.parseInt(idSucursal));
/* 2605:2808 */       drv.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2606:2809 */       drv.setCodigo(codigo);
/* 2607:2810 */       drv.setTipoCalculo(TipoCalculo.valueOf(tipoCalculo));
/* 2608:2811 */       drv.setValoresCalculo(ValoresCalculo.valueOf(valorCalculo));
/* 2609:2812 */       drv.setReporteador(reporteador);
/* 2610:2813 */       drv.setDescripcion(nota);
/* 2611:     */       
/* 2612:2815 */       this.detalleReporteadorVariableDao.guardar(drv);
/* 2613:     */     }
/* 2614:     */     Element variableXML;
/* 2615:2821 */     Object listaFormulaXML = reporteXML.getChildren("formula");
/* 2616:2822 */     for (Element formulaXML : (List)listaFormulaXML)
/* 2617:     */     {
/* 2618:2823 */       String codigo = formulaXML.getChildText("codigo");
/* 2619:2824 */       String expresion = formulaXML.getChildText("expresion");
/* 2620:2825 */       String nota = formulaXML.getChildText("nota");
/* 2621:2826 */       DetalleReporteadorVariable drvf = new DetalleReporteadorVariable();
/* 2622:2827 */       drvf.setIdSucursal(Integer.parseInt(idSucursal));
/* 2623:2828 */       drvf.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2624:2829 */       drvf.setCodigo(codigo);
/* 2625:2830 */       drvf.setExpresion(expresion);
/* 2626:2831 */       drvf.setDescripcion(nota);
/* 2627:2832 */       drvf.setIndicadorFormula(true);
/* 2628:2833 */       drvf.setReporteador(reporteador);
/* 2629:2834 */       this.detalleReporteadorVariableDao.guardar(drvf);
/* 2630:     */     }
/* 2631:     */   }
/* 2632:     */   
/* 2633:     */   public void cargaDetalleReporteadorRecursivo(List<Element> listaReporteXML, int nodo, Reporteador reporteador, DetalleReporteador detalleReporteadorPadre, String idSucursal, String idOrganizacion)
/* 2634:     */     throws ExcepcionAS2
/* 2635:     */   {
/* 2636:2843 */     if (listaReporteXML != null)
/* 2637:     */     {
/* 2638:2844 */       nodo++;
/* 2639:2845 */       for (Element nivelXML : listaReporteXML)
/* 2640:     */       {
/* 2641:2847 */         String nivel = nivelXML.getChildText("nivel");
/* 2642:2848 */         String orden = nivelXML.getChildText("orden");
/* 2643:2849 */         String nombreCampo = nivelXML.getChildText("nombre");
/* 2644:2850 */         String formula = nivelXML.getChildText("formula");
/* 2645:2851 */         String nota = nivelXML.getChildText("nota");
/* 2646:     */         
/* 2647:2853 */         DetalleReporteador detalleReporteador = new DetalleReporteador();
/* 2648:2854 */         detalleReporteador.setIdSucursal(Integer.parseInt(idSucursal));
/* 2649:2855 */         detalleReporteador.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2650:2856 */         detalleReporteador.setNivel(Integer.valueOf(nivel).intValue());
/* 2651:2857 */         detalleReporteador.setOrden(Integer.valueOf(orden).intValue());
/* 2652:2858 */         detalleReporteador.setNombre(nombreCampo);
/* 2653:2859 */         detalleReporteador.setActivo(true);
/* 2654:2860 */         detalleReporteador.setDescripcion(nota);
/* 2655:2861 */         detalleReporteador.setReporteador(reporteador);
/* 2656:2862 */         detalleReporteador.setDetalleReporteadorPadre(detalleReporteadorPadre);
/* 2657:2863 */         if (!formula.isEmpty())
/* 2658:     */         {
/* 2659:2864 */           DetalleReporteadorVariable drv = this.reporteadorDao.buscarVariablePorCodigo(formula, Integer.parseInt(idOrganizacion));
/* 2660:2865 */           detalleReporteador.setDetalleReporteadorVariable(drv);
/* 2661:     */         }
/* 2662:2868 */         this.detalleReporteadorDAO.guardar(detalleReporteador);
/* 2663:2869 */         List<Element> listaNivel2XML = nivelXML.getChildren("nivel" + nodo);
/* 2664:     */         
/* 2665:2871 */         cargaDetalleReporteadorRecursivo(listaNivel2XML, nodo, reporteador, detalleReporteador, idSucursal, idOrganizacion);
/* 2666:     */       }
/* 2667:     */     }
/* 2668:     */   }
/* 2669:     */   
/* 2670:     */   public void cargarPlantillaXML()
/* 2671:     */     throws ExcepcionAS2
/* 2672:     */   {
/* 2673:2884 */     Plantilla plantilla = null;
/* 2674:2885 */     Map<String, Plantilla> mapPlantilla = new HashMap();
/* 2675:     */     
/* 2676:2887 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 2677:     */     
/* 2678:2889 */     List<Plantilla> listaPlantillas = this.plantillaDao.obtenerListaCombo(Plantilla.class, "ruta", true, null);
/* 2679:2890 */     for (Plantilla p : listaPlantillas) {
/* 2680:2891 */       mapPlantilla.put(p.getNombreProceso() + "~" + p.getIdOrganizacion(), p);
/* 2681:     */     }
/* 2682:2894 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "plantilla.xml";
/* 2683:2895 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2684:2896 */     Element nodoPrincipal = document.getRootElement();
/* 2685:2897 */     List<Element> listaPlantillaXML = nodoPrincipal.getChildren("plantilla");
/* 2686:2899 */     for (Iterator localIterator2 = listaPlantillaXML.iterator(); localIterator2.hasNext();)
/* 2687:     */     {
/* 2688:2899 */       plantillaXML = (Element)localIterator2.next();
/* 2689:2900 */       for (Organizacion organizacion : listaOrganizacion)
/* 2690:     */       {
/* 2691:2901 */         String codigo = plantillaXML.getChildText("codigo");
/* 2692:2902 */         String proceso = plantillaXML.getChildText("proceso");
/* 2693:2903 */         String ruta = plantillaXML.getChildText("ruta");
/* 2694:2904 */         if (!mapPlantilla.containsKey(proceso + "~" + organizacion.getId()))
/* 2695:     */         {
/* 2696:2905 */           plantilla = new Plantilla();
/* 2697:2906 */           plantilla.setIdOrganizacion(organizacion.getId());
/* 2698:2907 */           plantilla.setCodigo(codigo);
/* 2699:2908 */           plantilla.setNombreProceso(proceso);
/* 2700:2909 */           plantilla.setRuta(ruta);
/* 2701:2910 */           this.plantillaDao.guardar(plantilla);
/* 2702:     */         }
/* 2703:     */       }
/* 2704:     */     }
/* 2705:     */     Element plantillaXML;
/* 2706:     */   }
/* 2707:     */   
/* 2708:     */   public void actualizarTareaProgramada()
/* 2709:     */     throws ExcepcionAS2
/* 2710:     */   {
/* 2711:2918 */     List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, true, null);
/* 2712:2920 */     for (Organizacion organizacion : listaOrganizacion)
/* 2713:     */     {
/* 2714:2921 */       Map<String, TareaProgramada> hmTareaProgramada = new HashMap();
/* 2715:2922 */       Map<String, String> organizacionFilter = new HashMap();
/* 2716:2923 */       organizacionFilter.put("idOrganizacion", String.valueOf(organizacion.getId()));
/* 2717:     */       
/* 2718:2925 */       List<Sucursal> listaSucursal = this.servicioSucursal.obtenerListaCombo("predeterminado", false, organizacionFilter);
/* 2719:2926 */       Sucursal sucursal = (Sucursal)listaSucursal.get(0);
/* 2720:     */       
/* 2721:2928 */       List<TareaProgramada> listaTareaProgramada = this.tareaProgramadaDao.obtenerListaCombo(TareaProgramada.class, "tareaProgramadaEnum", true, organizacionFilter);
/* 2722:2932 */       for (TareaProgramada tp : listaTareaProgramada)
/* 2723:     */       {
/* 2724:2933 */         String clave = "~" + tp.getTareaProgramadaEnum();
/* 2725:2934 */         if (hmTareaProgramada.get(clave) == null) {
/* 2726:2935 */           hmTareaProgramada.put(clave, tp);
/* 2727:     */         }
/* 2728:     */       }
/* 2729:2940 */       if (!hmTareaProgramada.containsKey("~" + TareaProgramadaEnum.SINCRONIZARCOMPROBANTESELECTRONICOS_GUIAREMISION)) {
/* 2730:2941 */         crearTareaProgramada(TareaProgramadaEnum.SINCRONIZARCOMPROBANTESELECTRONICOS_GUIAREMISION, organizacion, sucursal);
/* 2731:     */       }
/* 2732:2943 */       if (!hmTareaProgramada.containsKey("~" + TareaProgramadaEnum.SINCRONIZARCOMPROBANTESELECTRONICOS_HACIAFACTURAE)) {
/* 2733:2944 */         crearTareaProgramada(TareaProgramadaEnum.SINCRONIZARCOMPROBANTESELECTRONICOS_HACIAFACTURAE, organizacion, sucursal);
/* 2734:     */       }
/* 2735:2946 */       if (!hmTareaProgramada.containsKey("~" + TareaProgramadaEnum.SINCRONIZARCOMPROBANTESELECTRONICOS_RETENCIONPROVEEDOR)) {
/* 2736:2947 */         crearTareaProgramada(TareaProgramadaEnum.SINCRONIZARCOMPROBANTESELECTRONICOS_RETENCIONPROVEEDOR, organizacion, sucursal);
/* 2737:     */       }
/* 2738:2949 */       if (!hmTareaProgramada.containsKey("~" + TareaProgramadaEnum.SINCRONIZARCOMPROBANTESELECTRONICOS_VENTAS)) {
/* 2739:2950 */         crearTareaProgramada(TareaProgramadaEnum.SINCRONIZARCOMPROBANTESELECTRONICOS_VENTAS, organizacion, sucursal);
/* 2740:     */       }
/* 2741:     */     }
/* 2742:     */   }
/* 2743:     */   
/* 2744:     */   public void crearTareaProgramada(TareaProgramadaEnum tareaProgramadaEnum, Organizacion organizacion, Sucursal sucursal)
/* 2745:     */   {
/* 2746:2956 */     TareaProgramada nuevaTareaProgramada = new TareaProgramada();
/* 2747:2957 */     nuevaTareaProgramada.setIdOrganizacion(organizacion.getId());
/* 2748:2958 */     nuevaTareaProgramada.setTareaProgramadaEnum(tareaProgramadaEnum);
/* 2749:2959 */     nuevaTareaProgramada.setActivo(true);
/* 2750:2960 */     nuevaTareaProgramada.setSucursal(sucursal);
/* 2751:     */     
/* 2752:2962 */     String horas = null;
/* 2753:2963 */     Map map = System.getenv();
/* 2754:2964 */     String ruta = (String)map.get("AS2_HOME");
/* 2755:2965 */     ruta = ruta + File.separator + "config";
/* 2756:     */     try
/* 2757:     */     {
/* 2758:2967 */       String parametroFichero = EjbUtil.obtenerValorArchivoProperties("reenviarComprobantesElectronicos", ruta, "horarios.properties");
/* 2759:2968 */       String[] horarios = parametroFichero.split(";");
/* 2760:2969 */       for (String horario : horarios)
/* 2761:     */       {
/* 2762:2970 */         horario = horario.trim();
/* 2763:2971 */         String horaInicio = horario.split("-")[0];
/* 2764:2972 */         String horaFin = horario.split("-")[1];
/* 2765:2974 */         if (horas == null) {
/* 2766:2975 */           horas = "";
/* 2767:     */         } else {
/* 2768:2977 */           horas = horas + ",";
/* 2769:     */         }
/* 2770:2980 */         int horaHoraInicio = Integer.parseInt(horaInicio.split(":")[0]);
/* 2771:2981 */         int horaHoraFin = Integer.parseInt(horaFin.split(":")[0]);
/* 2772:2983 */         if (horaHoraInicio == horaHoraFin) {
/* 2773:2984 */           horas = horas + String.valueOf(horaHoraInicio);
/* 2774:     */         } else {
/* 2775:2986 */           horas = horas + String.valueOf(horaHoraInicio) + "-" + String.valueOf(horaHoraFin);
/* 2776:     */         }
/* 2777:     */       }
/* 2778:     */     }
/* 2779:     */     catch (Exception e)
/* 2780:     */     {
/* 2781:2990 */       horas = null;
/* 2782:2991 */       System.out.println("No pudo cargar el fichero '" + ruta + "/horarios.properties', en su lugar se crearan las tareas con el horario predeterminado");
/* 2783:     */     }
/* 2784:2994 */     if (horas == null) {
/* 2785:2995 */       horas = "*";
/* 2786:     */     }
/* 2787:2997 */     String expresionTiempo = "0 0/10 " + horas + " * * ? *";
/* 2788:     */     
/* 2789:2999 */     nuevaTareaProgramada.setExpresionTiempo(expresionTiempo);
/* 2790:3000 */     this.tareaProgramadaDao.guardar(nuevaTareaProgramada);
/* 2791:     */   }
/* 2792:     */   
/* 2793:     */   public void actualizarVersionSistema()
/* 2794:     */   {
/* 2795:3005 */     VersionSistema versionSistema = new VersionSistema();
/* 2796:3006 */     versionSistema.setFecha(new Date());
/* 2797:3007 */     versionSistema.setNumero("2.2.11.012");
/* 2798:3008 */     versionSistema.setSistema(this.sistemaDao.buscarPorNombre("AS2-ERP"));
/* 2799:3009 */     this.versionSistemaDao.guardar(versionSistema);
/* 2800:     */   }
/* 2801:     */   
/* 2802:     */   public void updateReportes(String version)
/* 2803:     */   {
/* 2804:     */     try
/* 2805:     */     {
/* 2806:3016 */       String nombreCarpeta = "reportes";
/* 2807:3017 */       ServicioCargarDatosInicialesDesdeXMLBase scdid = new ServicioCargarDatosInicialesDesdeXMLBase();
/* 2808:3018 */       path = scdid.getPathResoucesAS2(nombreCarpeta);
/* 2809:3019 */       File folder = new File(path);
/* 2810:3020 */       File[] listOfFiles = folder.listFiles();
/* 2811:     */       
/* 2812:3022 */       List<Reportes> listaReportes = this.reportesDao.obtenerListaCombo(Reportes.class, "nombre", true, null);
/* 2813:     */       
/* 2814:3024 */       List<ReportesPersonalizados> listaReportesPersonalizados = this.reportesPersonalizadosDao.obtenerListaCombo(ReportesPersonalizados.class, "nombre", true, null);
/* 2815:     */       
/* 2816:     */ 
/* 2817:3027 */       HashMap<String, String> hmReportes = new HashMap();
/* 2818:3028 */       hmReportesActualizar = new HashMap();
/* 2819:3029 */       for (ReportesPersonalizados reportesPersonalizados : listaReportesPersonalizados) {
/* 2820:3030 */         hmReportes.put(reportesPersonalizados.getNombre() + "~" + reportesPersonalizados.getOrganizacion(), reportesPersonalizados
/* 2821:3031 */           .getNombre());
/* 2822:     */       }
/* 2823:3034 */       for (Reportes reportes : listaReportes) {
/* 2824:3035 */         if (!hmReportes.containsKey(reportes.getNombre() + "~" + reportes.getOrganizacion())) {
/* 2825:3036 */           hmReportesActualizar.put(reportes.getNombre() + "~" + reportes.getOrganizacion(), reportes);
/* 2826:     */         }
/* 2827:     */       }
/* 2828:3041 */       Object listaOrganizacion = this.organizacionDao.obtenerListaCombo("razonSocial", true, null);
/* 2829:3042 */       mapReportes = new HashMap();
/* 2830:3043 */       Map<String, String> mapFiles = new HashMap();
/* 2831:     */       
/* 2832:3045 */       HashMap<Integer, String> mapAS2Home = obtenerAS2Home();
/* 2833:3047 */       for (Organizacion organizacion : (List)listaOrganizacion) {
/* 2834:3048 */         for (int i = 0; i < listOfFiles.length; i++) {
/* 2835:3049 */           if (listOfFiles[i].isFile())
/* 2836:     */           {
/* 2837:3050 */             String ruta = (String)mapAS2Home.get(Integer.valueOf(organizacion.getIdOrganizacion()));
/* 2838:3051 */             mapFiles.put(organizacion.getRazonSocial() + "~" + listOfFiles[i].getName(), ruta + File.separator + listOfFiles[i]
/* 2839:3052 */               .getName());
/* 2840:     */           }
/* 2841:     */         }
/* 2842:     */       }
/* 2843:3057 */       for (??? = mapFiles.entrySet().iterator(); ???.hasNext();)
/* 2844:     */       {
/* 2845:3057 */         file = (Map.Entry)???.next();
/* 2846:3058 */         nombreReporte = ((String)file.getKey()).substring(((String)file.getKey()).lastIndexOf("~") + 1, ((String)file.getKey()).length());
/* 2847:3059 */         carpeta = ((String)file.getValue()).replace(File.separator + nombreReporte, "");
/* 2848:3060 */         rutaOrigen = path + nombreReporte;
/* 2849:3061 */         rutaDestino = (String)file.getValue();
/* 2850:     */         
/* 2851:3063 */         boolean existeRutaOrigen = verificarExistenciaReporte(rutaOrigen);
/* 2852:3064 */         boolean existeRutaDestino = verificarExistenciaReporte(rutaDestino);
/* 2853:3067 */         if (!existeRutaDestino) {
/* 2854:3068 */           copiarReporte(rutaOrigen, rutaDestino);
/* 2855:     */         }
/* 2856:3071 */         if ((existeRutaOrigen) && (existeRutaDestino)) {
/* 2857:3074 */           for (Map.Entry<String, Reportes> reportes : hmReportesActualizar.entrySet()) {
/* 2858:3076 */             if ((nombreReporte.equals(((Reportes)reportes.getValue()).getNombre() + ".jrxml")) && (version.equals(((Reportes)reportes.getValue()).getVersion())))
/* 2859:     */             {
/* 2860:3078 */               if (!mapReportes.containsKey(file.getValue()))
/* 2861:     */               {
/* 2862:3080 */                 respaldoReporte(carpeta, ((Reportes)reportes.getValue()).getNombre(), ((Reportes)reportes.getValue()).getVersion());
/* 2863:3081 */                 mapReportes.put(file.getValue(), file.getValue());
/* 2864:     */               }
/* 2865:3083 */               copiarReporte(rutaOrigen, rutaDestino);
/* 2866:     */               
/* 2867:3085 */               ((Reportes)reportes.getValue()).setActualizado(true);
/* 2868:3086 */               this.reportesDao.guardar((EntidadBase)reportes.getValue());
/* 2869:     */             }
/* 2870:     */           }
/* 2871:     */         }
/* 2872:     */       }
/* 2873:     */     }
/* 2874:     */     catch (IOException e)
/* 2875:     */     {
/* 2876:     */       String path;
/* 2877:     */       HashMap<String, Reportes> hmReportesActualizar;
/* 2878:     */       Map<String, String> mapReportes;
/* 2879:     */       Map.Entry<String, String> file;
/* 2880:     */       String nombreReporte;
/* 2881:     */       String carpeta;
/* 2882:     */       String rutaOrigen;
/* 2883:     */       String rutaDestino;
/* 2884:3094 */       e.printStackTrace();
/* 2885:     */     }
/* 2886:     */   }
/* 2887:     */   
/* 2888:     */   public static void respaldoReporte(String path, String nombreReporte, String version)
/* 2889:     */   {
/* 2890:     */     try
/* 2891:     */     {
/* 2892:3101 */       String rutaFolder = path + "/reportes" + "_" + version;
/* 2893:3102 */       File folder = new File(rutaFolder);
/* 2894:3103 */       folder.mkdirs();
/* 2895:     */       
/* 2896:     */ 
/* 2897:3106 */       String rutaOrigen = path + File.separator + nombreReporte + ".jrxml";
/* 2898:3107 */       String rutaDestino = rutaFolder + File.separator + nombreReporte + "_" + version + ".jrxml";
/* 2899:     */       
/* 2900:3109 */       copiarReporte(rutaOrigen, rutaDestino);
/* 2901:     */     }
/* 2902:     */     catch (IOException e)
/* 2903:     */     {
/* 2904:3114 */       e.printStackTrace();
/* 2905:     */     }
/* 2906:     */   }
/* 2907:     */   
/* 2908:     */   public static void copiarReporte(String origen, String destino)
/* 2909:     */     throws IOException
/* 2910:     */   {
/* 2911:3122 */     Path FROM = Paths.get(origen, new String[0]);
/* 2912:3123 */     Path TO = Paths.get(destino, new String[0]);
/* 2913:3124 */     Path DELETE = Paths.get(origen.replace("jrxml", "jasper"), new String[0]);
/* 2914:     */     
/* 2915:     */ 
/* 2916:3127 */     CopyOption[] options = { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES };
/* 2917:3130 */     if (Files.exists(FROM, new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
/* 2918:3131 */       Files.copy(FROM, TO, options);
/* 2919:     */     }
/* 2920:3134 */     Files.deleteIfExists(DELETE);
/* 2921:     */   }
/* 2922:     */   
/* 2923:     */   public void cargarTodosLosReportes()
/* 2924:     */   {
/* 2925:     */     try
/* 2926:     */     {
/* 2927:3141 */       String nombreCarpeta = "reportes";
/* 2928:3142 */       ServicioCargarDatosInicialesDesdeXMLBase scdid = new ServicioCargarDatosInicialesDesdeXMLBase();
/* 2929:3143 */       String path = scdid.getPathResoucesAS2(nombreCarpeta);
/* 2930:3144 */       File folder = new File(path);
/* 2931:3145 */       File[] listOfFiles = folder.listFiles();
/* 2932:3146 */       String ruta = ServicioConfiguracion.AS2_HOME + File.separator + "reportes";
/* 2933:3147 */       File carpetaReportes = new File(ruta);
/* 2934:3148 */       if (!carpetaReportes.exists()) {
/* 2935:3149 */         carpetaReportes.mkdirs();
/* 2936:     */       }
/* 2937:3151 */       for (int i = 0; i < listOfFiles.length; i++) {
/* 2938:3152 */         if (listOfFiles[i].isFile())
/* 2939:     */         {
/* 2940:3154 */           String rutaOrigen = path + listOfFiles[i].getName();
/* 2941:3155 */           String rutaDestino = ruta + File.separator + listOfFiles[i].getName();
/* 2942:3156 */           copiarReporte(rutaOrigen, rutaDestino);
/* 2943:     */         }
/* 2944:     */       }
/* 2945:     */     }
/* 2946:     */     catch (IOException e)
/* 2947:     */     {
/* 2948:3160 */       e.printStackTrace();
/* 2949:     */     }
/* 2950:     */   }
/* 2951:     */   
/* 2952:     */   public boolean verificarExistenciaReporte(String path)
/* 2953:     */   {
/* 2954:3165 */     boolean existe = false;
/* 2955:3166 */     File f = new File(path);
/* 2956:3167 */     if ((f.exists()) && (f.isFile())) {
/* 2957:3168 */       existe = true;
/* 2958:     */     }
/* 2959:3170 */     return existe;
/* 2960:     */   }
/* 2961:     */   
/* 2962:     */   public HashMap<Integer, String> obtenerAS2Home()
/* 2963:     */   {
/* 2964:3175 */     HashMap<Integer, String> mapAS2Home = new HashMap();
/* 2965:3176 */     Map<String, String> filtersConf = new HashMap();
/* 2966:3177 */     filtersConf.put("parametro", Parametro.AS2_HOME.name());
/* 2967:3178 */     List<Configuracion> AS2_HOME = this.configuracionDao.obtenerListaCombo("parametro", true, filtersConf);
/* 2968:3179 */     for (Configuracion configuracion : AS2_HOME) {
/* 2969:3180 */       mapAS2Home.put(Integer.valueOf(configuracion.getIdOrganizacion()), configuracion.getValor() + File.separator + "reportes");
/* 2970:     */     }
/* 2971:3182 */     return mapAS2Home;
/* 2972:     */   }
/* 2973:     */   
/* 2974:     */   public void cargarMotivoAjusteInventarioDesdeXML()
/* 2975:     */     throws ExcepcionAS2
/* 2976:     */   {
/* 2977:3189 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "motivoAjusteInventario.xml";
/* 2978:3190 */     MotivoAjusteInventario motivoAjusteInventario = null;
/* 2979:     */     
/* 2980:3192 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 2981:3193 */     Element nodoPrincipal = document.getRootElement();
/* 2982:3194 */     List<Element> listaDocumentoXML = nodoPrincipal.getChildren("motivoAjusteInventario");
/* 2983:3196 */     for (Element documentoXML : listaDocumentoXML)
/* 2984:     */     {
/* 2985:3197 */       String idOrganizacion = documentoXML.getChildText("idOrganizacion");
/* 2986:3198 */       String idSucursal = documentoXML.getChildText("idSucursal");
/* 2987:3199 */       String codigo = documentoXML.getChildText("codigo");
/* 2988:3200 */       String nombre = documentoXML.getChildText("nombre");
/* 2989:3201 */       String descripcion = documentoXML.getChildText("descripcion");
/* 2990:3202 */       String activo = documentoXML.getChildText("activo");
/* 2991:3203 */       String predeterminado = documentoXML.getChildText("predeterminado");
/* 2992:     */       
/* 2993:3205 */       motivoAjusteInventario = new MotivoAjusteInventario();
/* 2994:     */       
/* 2995:3207 */       motivoAjusteInventario.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 2996:3208 */       motivoAjusteInventario.setIdSucursal(Integer.parseInt(idSucursal));
/* 2997:3209 */       motivoAjusteInventario.setCodigo(codigo);
/* 2998:3210 */       motivoAjusteInventario.setNombre(nombre);
/* 2999:3211 */       motivoAjusteInventario.setDescripcion(descripcion);
/* 3000:3212 */       motivoAjusteInventario.setActivo(obtenerIndicador(activo));
/* 3001:3213 */       motivoAjusteInventario.setPredeterminado(obtenerIndicador(predeterminado));
/* 3002:     */       
/* 3003:3215 */       this.motivoAjusteInventarioDao.guardar(motivoAjusteInventario);
/* 3004:     */     }
/* 3005:     */   }
/* 3006:     */   
/* 3007:     */   public void cargarOrigenIngresos()
/* 3008:     */     throws ExcepcionAS2
/* 3009:     */   {
/* 3010:3223 */     String rutaArchivo = getPathResoucesAS2(RESOURCE_DATOS_INICIALES) + "origenIngresos.xml";
/* 3011:3224 */     OrigenIngresos origenIngresos = null;
/* 3012:3225 */     Document document = ArchivoUtil.obtenerDocumentoXML(rutaArchivo);
/* 3013:3226 */     Element nodoPrincipal = document.getRootElement();
/* 3014:3227 */     List<Element> listaDocumentoXML = nodoPrincipal.getChildren("origenIngresos");
/* 3015:3228 */     for (Element documentoXML : listaDocumentoXML)
/* 3016:     */     {
/* 3017:3229 */       String idOrganizacion = documentoXML.getChildText("idOrganizacion");
/* 3018:3230 */       String idSucursal = documentoXML.getChildText("idSucursal");
/* 3019:3231 */       String codigo = documentoXML.getChildText("codigo");
/* 3020:3232 */       String nombre = documentoXML.getChildText("nombre");
/* 3021:3233 */       String descripcion = documentoXML.getChildText("descripcion");
/* 3022:3234 */       String activo = documentoXML.getChildText("activo");
/* 3023:3235 */       String predeterminado = documentoXML.getChildText("predeterminado");
/* 3024:3236 */       origenIngresos = new OrigenIngresos();
/* 3025:3237 */       origenIngresos.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 3026:3238 */       origenIngresos.setIdSucursal(Integer.parseInt(idSucursal));
/* 3027:3239 */       origenIngresos.setCodigo(codigo);
/* 3028:3240 */       origenIngresos.setNombre(nombre);
/* 3029:3241 */       origenIngresos.setDescripcion(descripcion);
/* 3030:3242 */       origenIngresos.setActivo(obtenerIndicador(activo));
/* 3031:3243 */       origenIngresos.setPredeterminado(obtenerIndicador(predeterminado));
/* 3032:3244 */       this.origenIngreososDao.guardar(origenIngresos);
/* 3033:     */     }
/* 3034:     */   }
/* 3035:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioCargarDatosInicialesDesdeXMLImpl
 * JD-Core Version:    0.7.0.1
 */