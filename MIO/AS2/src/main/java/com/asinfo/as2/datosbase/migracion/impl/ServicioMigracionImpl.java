/*    1:     */ package com.asinfo.as2.datosbase.migracion.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.configuracion.servicio.impl.ServicioTipoOperacion;
/*    4:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    5:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioProvincia;
/*    9:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   10:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   11:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoCuentaBancaria;
/*   12:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   13:     */ import com.asinfo.as2.dao.DetalleHistoricoEmpleadoDao;
/*   14:     */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*   15:     */ import com.asinfo.as2.dao.GenericoDao;
/*   16:     */ import com.asinfo.as2.dao.HistoricoEmpleadoDao;
/*   17:     */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   19:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   20:     */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   21:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   22:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   23:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   24:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   25:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   26:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   27:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   28:     */ import com.asinfo.as2.entities.ActivoFijo;
/*   29:     */ import com.asinfo.as2.entities.Atributo;
/*   30:     */ import com.asinfo.as2.entities.Banco;
/*   31:     */ import com.asinfo.as2.entities.Bodega;
/*   32:     */ import com.asinfo.as2.entities.CargaEmpleado;
/*   33:     */ import com.asinfo.as2.entities.CargoEmpleado;
/*   34:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   35:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   36:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   37:     */ import com.asinfo.as2.entities.CategoriaRetencion;
/*   38:     */ import com.asinfo.as2.entities.Ciudad;
/*   39:     */ import com.asinfo.as2.entities.Cliente;
/*   40:     */ import com.asinfo.as2.entities.CodigoFormaPagoSRI;
/*   41:     */ import com.asinfo.as2.entities.CondicionPago;
/*   42:     */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*   43:     */ import com.asinfo.as2.entities.CuentaBancaria;
/*   44:     */ import com.asinfo.as2.entities.CuentaBancariaEmpresa;
/*   45:     */ import com.asinfo.as2.entities.CuentaContable;
/*   46:     */ import com.asinfo.as2.entities.CustodioActivoFijo;
/*   47:     */ import com.asinfo.as2.entities.Departamento;
/*   48:     */ import com.asinfo.as2.entities.DestinoCosto;
/*   49:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   50:     */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*   51:     */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   52:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   53:     */ import com.asinfo.as2.entities.DetalleTomaFisica;
/*   54:     */ import com.asinfo.as2.entities.DetalleVacacion;
/*   55:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   56:     */ import com.asinfo.as2.entities.DetalleVersionPlanComision;
/*   57:     */ import com.asinfo.as2.entities.DetalleVersionPlanComisionRangoDias;
/*   58:     */ import com.asinfo.as2.entities.DimensionContable;
/*   59:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   60:     */ import com.asinfo.as2.entities.Documento;
/*   61:     */ import com.asinfo.as2.entities.Empleado;
/*   62:     */ import com.asinfo.as2.entities.Empresa;
/*   63:     */ import com.asinfo.as2.entities.EntidadBase;
/*   64:     */ import com.asinfo.as2.entities.EstadoCivil;
/*   65:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   66:     */ import com.asinfo.as2.entities.FormaPago;
/*   67:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   68:     */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   69:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*   70:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   71:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   72:     */ import com.asinfo.as2.entities.Lote;
/*   73:     */ import com.asinfo.as2.entities.MarcaProducto;
/*   74:     */ import com.asinfo.as2.entities.NivelCuenta;
/*   75:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   76:     */ import com.asinfo.as2.entities.Organizacion;
/*   77:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   78:     */ import com.asinfo.as2.entities.PagoRol;
/*   79:     */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   80:     */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*   81:     */ import com.asinfo.as2.entities.Pais;
/*   82:     */ import com.asinfo.as2.entities.PlanComision;
/*   83:     */ import com.asinfo.as2.entities.PresentacionProducto;
/*   84:     */ import com.asinfo.as2.entities.Producto;
/*   85:     */ import com.asinfo.as2.entities.ProductoAtributo;
/*   86:     */ import com.asinfo.as2.entities.ProductoMaterial;
/*   87:     */ import com.asinfo.as2.entities.Proveedor;
/*   88:     */ import com.asinfo.as2.entities.Provincia;
/*   89:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   90:     */ import com.asinfo.as2.entities.RangoDiasComision;
/*   91:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   92:     */ import com.asinfo.as2.entities.Rubro;
/*   93:     */ import com.asinfo.as2.entities.RubroEmpleado;
/*   94:     */ import com.asinfo.as2.entities.Secuencia;
/*   95:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   96:     */ import com.asinfo.as2.entities.Sucursal;
/*   97:     */ import com.asinfo.as2.entities.TipoContrato;
/*   98:     */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   99:     */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*  100:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  101:     */ import com.asinfo.as2.entities.TipoOperacion;
/*  102:     */ import com.asinfo.as2.entities.TipoPresentacionProducto;
/*  103:     */ import com.asinfo.as2.entities.Titulo;
/*  104:     */ import com.asinfo.as2.entities.Ubicacion;
/*  105:     */ import com.asinfo.as2.entities.UbicacionActivo;
/*  106:     */ import com.asinfo.as2.entities.Unidad;
/*  107:     */ import com.asinfo.as2.entities.Vacacion;
/*  108:     */ import com.asinfo.as2.entities.ValorAtributo;
/*  109:     */ import com.asinfo.as2.entities.VersionListaDescuentos;
/*  110:     */ import com.asinfo.as2.entities.VersionListaPrecios;
/*  111:     */ import com.asinfo.as2.entities.VersionPlanComision;
/*  112:     */ import com.asinfo.as2.entities.Zona;
/*  113:     */ import com.asinfo.as2.entities.aerolineas.ConfiguracionCargaTicket;
/*  114:     */ import com.asinfo.as2.entities.aerolineas.DetalleTicket;
/*  115:     */ import com.asinfo.as2.entities.aerolineas.Ticket;
/*  116:     */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*  117:     */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*  118:     */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  119:     */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*  120:     */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento;
/*  121:     */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  122:     */ import com.asinfo.as2.entities.mantenimiento.Frecuencia;
/*  123:     */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*  124:     */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimiento;
/*  125:     */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo;
/*  126:     */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  127:     */ import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
/*  128:     */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*  129:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*  130:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*  131:     */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  132:     */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*  133:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  134:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  135:     */ import com.asinfo.as2.enumeraciones.Estado;
/*  136:     */ import com.asinfo.as2.enumeraciones.FormaPagoComisionEnum;
/*  137:     */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*  138:     */ import com.asinfo.as2.enumeraciones.FormatoCelda;
/*  139:     */ import com.asinfo.as2.enumeraciones.FrecuenciaFechaEnum;
/*  140:     */ import com.asinfo.as2.enumeraciones.Genero;
/*  141:     */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*  142:     */ import com.asinfo.as2.enumeraciones.MetodoFacturacionEnum;
/*  143:     */ import com.asinfo.as2.enumeraciones.Parentezco;
/*  144:     */ import com.asinfo.as2.enumeraciones.PrioridadEnum;
/*  145:     */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*  146:     */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*  147:     */ import com.asinfo.as2.enumeraciones.TipoCosto;
/*  148:     */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  149:     */ import com.asinfo.as2.enumeraciones.TipoDepartamento;
/*  150:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*  151:     */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*  152:     */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*  153:     */ import com.asinfo.as2.enumeraciones.TipoMaterialEnum;
/*  154:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  155:     */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*  156:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*  157:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  158:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion;
/*  159:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*  160:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*  161:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  162:     */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*  163:     */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCustodioActivoFijo;
/*  164:     */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioUbicacionActivo;
/*  165:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  166:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  167:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  168:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  169:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  170:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCuenta;
/*  171:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  172:     */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioNivelPartidaPresupuestaria;
/*  173:     */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioPartidaPresupuestaria;
/*  174:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  175:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  176:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  177:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioConjuntoAtributo;
/*  178:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  179:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  180:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProductoAtributo;
/*  181:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  182:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  183:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  184:     */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*  185:     */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo;
/*  186:     */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioLecturaMantenimiento;
/*  187:     */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioPlanMantenimiento;
/*  188:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargaEmpleado;
/*  189:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargoEmpleado;
/*  190:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  191:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEstadoCivil;
/*  192:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  193:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoContrato;
/*  194:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoDiscapacidad;
/*  195:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTitulo;
/*  196:     */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  197:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioDetalleVacacion;
/*  198:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  199:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  200:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleadoRubro;
/*  201:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  202:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioVacacion;
/*  203:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcaProducto;
/*  204:     */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  205:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  206:     */ import com.asinfo.as2.util.AppUtil;
/*  207:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  208:     */ import com.asinfo.as2.utils.JsfUtil;
/*  209:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*  210:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioPlanComision;
/*  211:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  212:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  213:     */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*  214:     */ import java.io.IOException;
/*  215:     */ import java.io.InputStream;
/*  216:     */ import java.io.PrintStream;
/*  217:     */ import java.math.BigDecimal;
/*  218:     */ import java.math.RoundingMode;
/*  219:     */ import java.util.ArrayList;
/*  220:     */ import java.util.Collection;
/*  221:     */ import java.util.Date;
/*  222:     */ import java.util.HashMap;
/*  223:     */ import java.util.Iterator;
/*  224:     */ import java.util.LinkedHashMap;
/*  225:     */ import java.util.List;
/*  226:     */ import java.util.Map;
/*  227:     */ import java.util.Set;
/*  228:     */ import javax.annotation.Resource;
/*  229:     */ import javax.ejb.EJB;
/*  230:     */ import javax.ejb.SessionContext;
/*  231:     */ import javax.ejb.Stateless;
/*  232:     */ import javax.ejb.TransactionAttribute;
/*  233:     */ import javax.ejb.TransactionAttributeType;
/*  234:     */ import javax.ejb.TransactionManagement;
/*  235:     */ import javax.ejb.TransactionManagementType;
/*  236:     */ import javax.faces.model.SelectItem;
/*  237:     */ import org.apache.log4j.Logger;
/*  238:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  239:     */ 
/*  240:     */ @Stateless
/*  241:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  242:     */ public class ServicioMigracionImpl
/*  243:     */   extends AbstractServicioAS2
/*  244:     */   implements ServicioMigracion
/*  245:     */ {
/*  246:     */   private static final long serialVersionUID = -8852811015519316572L;
/*  247:     */   @EJB
/*  248:     */   private ServicioNivelCuenta servicioNivelCuenta;
/*  249:     */   @EJB
/*  250:     */   private ServicioCuentaContable servicioCuentaContable;
/*  251:     */   @EJB
/*  252:     */   private ServicioSucursal servicioSucursal;
/*  253:     */   @EJB
/*  254:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  255:     */   @EJB
/*  256:     */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  257:     */   @EJB
/*  258:     */   private ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  259:     */   @EJB
/*  260:     */   private ServicioEmpresa servicioEmpresa;
/*  261:     */   @EJB
/*  262:     */   private ServicioFormaPago servicioFormaPago;
/*  263:     */   @EJB
/*  264:     */   private ServicioCondicionPago servicioCondicionPago;
/*  265:     */   @EJB
/*  266:     */   private ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  267:     */   @EJB
/*  268:     */   private ServicioPagoRolEmpleadoRubro servicioPagoRolEmpleadoRubro;
/*  269:     */   @EJB
/*  270:     */   private ServicioCategoriaRetencion servicioCategoriaRetencion;
/*  271:     */   @EJB
/*  272:     */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  273:     */   @EJB
/*  274:     */   private ServicioUnidad servicioUnidad;
/*  275:     */   @EJB
/*  276:     */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  277:     */   @EJB
/*  278:     */   private ServicioProducto servicioProducto;
/*  279:     */   @EJB
/*  280:     */   private ServicioAtributo servicioAtributo;
/*  281:     */   @EJB
/*  282:     */   private ServicioConjuntoAtributo servicioConjuntoAtributo;
/*  283:     */   @EJB
/*  284:     */   private ServicioProductoAtributo servicioProductoAtributo;
/*  285:     */   @EJB
/*  286:     */   private ServicioDimensionContable servicioDimensionContable;
/*  287:     */   @EJB
/*  288:     */   private ServicioMarcaProducto servicioMarcaProducto;
/*  289:     */   @EJB
/*  290:     */   private ServicioGenerico<PresentacionProducto> servicioPresentacionProducto;
/*  291:     */   @EJB
/*  292:     */   private ServicioGenerico<TipoPresentacionProducto> servicioTipoPresentacionProducto;
/*  293:     */   @EJB
/*  294:     */   private ServicioCargoEmpleado servicioCargoEmpleado;
/*  295:     */   @EJB
/*  296:     */   private ServicioDepartamento servicioDepartamento;
/*  297:     */   @EJB
/*  298:     */   private ServicioEstadoCivil servicioEstadoCivil;
/*  299:     */   @EJB
/*  300:     */   private ServicioTipoDiscapacidad servicioTipoDiscapacidad;
/*  301:     */   @EJB
/*  302:     */   private ServicioTitulo servicioTitulo;
/*  303:     */   @EJB
/*  304:     */   private ServicioTipoContrato servicioTipoContrato;
/*  305:     */   @EJB
/*  306:     */   private ServicioPais servicioPais;
/*  307:     */   @EJB
/*  308:     */   private ServicioEmpleado servicioEmpleado;
/*  309:     */   @EJB
/*  310:     */   private ServicioCargaEmpleado servicioCargaEmpleado;
/*  311:     */   @EJB
/*  312:     */   private ServicioProvincia servicioProvincia;
/*  313:     */   @EJB
/*  314:     */   private ServicioCiudad servicioCiudad;
/*  315:     */   @EJB
/*  316:     */   private ServicioSecuencia servicioSecuencia;
/*  317:     */   @EJB
/*  318:     */   private ServicioVacacion servicioVacacion;
/*  319:     */   @EJB
/*  320:     */   private ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  321:     */   @EJB
/*  322:     */   private ServicioCentroCosto servicioCentroCosto;
/*  323:     */   @EJB
/*  324:     */   private HistoricoEmpleadoDao historicoEmpleadoDao;
/*  325:     */   @EJB
/*  326:     */   private ServicioDetalleVacacion servicioDetalleVacacion;
/*  327:     */   @EJB
/*  328:     */   private DetalleHistoricoEmpleadoDao detalleHistoricoEmpleadoDao;
/*  329:     */   @EJB
/*  330:     */   private ServicioDocumento servicioDocumento;
/*  331:     */   @EJB
/*  332:     */   private DireccionEmpresaDao direccionEmpresaDao;
/*  333:     */   @EJB
/*  334:     */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  335:     */   @EJB
/*  336:     */   private ServicioImpuesto servicioImpuesto;
/*  337:     */   @EJB
/*  338:     */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  339:     */   @EJB
/*  340:     */   private ServicioGenerico<Banco> servicioBanco;
/*  341:     */   @EJB
/*  342:     */   private ServicioTipoCuentaBancaria servicioTipoCuentaBancaria;
/*  343:     */   @EJB
/*  344:     */   private ServicioTipoOperacion servicioTipoOperacion;
/*  345:     */   @EJB
/*  346:     */   private ServicioLote servicioLote;
/*  347:     */   @EJB
/*  348:     */   private ServicioRubro servicioRubro;
/*  349:     */   @EJB
/*  350:     */   private ServicioRubroEmpleado servicioRubroEmpleado;
/*  351:     */   @Resource
/*  352:     */   protected SessionContext context;
/*  353:     */   @EJB
/*  354:     */   private ServicioListaPrecios servicioListaPrecios;
/*  355:     */   @EJB
/*  356:     */   private ServicioZona servicioZona;
/*  357:     */   @EJB
/*  358:     */   private GenericoDao<ProductoMaterial> productoMaterialDao;
/*  359:     */   @EJB
/*  360:     */   private ServicioCreditoTributario servicioCreditoTributario;
/*  361:     */   @EJB
/*  362:     */   private ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*  363:     */   @EJB
/*  364:     */   private ServicioGenerico<ConfiguracionCargaTicket> servicioCatalogoConfiguracionTicket;
/*  365:     */   @EJB
/*  366:     */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  367:     */   @EJB
/*  368:     */   private ServicioListaDescuentos servicioListaDescuentos;
/*  369:     */   @EJB
/*  370:     */   private ServicioNivelPartidaPresupuestaria servicioNivelPartidaPresupuestaria;
/*  371:     */   @EJB
/*  372:     */   private ServicioPartidaPresupuestaria servicioPartidaPresupuestaria;
/*  373:     */   @EJB
/*  374:     */   private ServicioBodega servicioBodega;
/*  375:     */   @EJB
/*  376:     */   private ServicioDestinoCosto servicioDestinoCosto;
/*  377:     */   @EJB
/*  378:     */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  379:     */   @EJB
/*  380:     */   private ServicioUbicacionActivo servicioUbicacionActivo;
/*  381:     */   @EJB
/*  382:     */   private ServicioGenerico<CategoriaEquipo> servicioCategoriaEquipo;
/*  383:     */   @EJB
/*  384:     */   private ServicioGenerico<SubcategoriaEquipo> servicioSubCategoriaEquipo;
/*  385:     */   @EJB
/*  386:     */   private ServicioEquipo servicioEquipo;
/*  387:     */   @EJB
/*  388:     */   private ServicioGenerico<DetalleComponenteEquipo> servicioDetalleComponenteEquipo;
/*  389:     */   @EJB
/*  390:     */   private ServicioLecturaMantenimiento servicioLecturaMantenimiento;
/*  391:     */   @EJB
/*  392:     */   private ServicioGenerico<Frecuencia> servicioFrecuencia;
/*  393:     */   @EJB
/*  394:     */   private ServicioGenerico<ActividadMantenimiento> servicioActividadMantenimiento;
/*  395:     */   @EJB
/*  396:     */   private ServicioActivoFijo servicioActivoFijo;
/*  397:     */   @EJB
/*  398:     */   private ServicioPlanMantenimiento servicioPlanMantenimiento;
/*  399:     */   @EJB
/*  400:     */   private ServicioGenerico<Cliente> servicioCliente;
/*  401:     */   @EJB
/*  402:     */   private ServicioCustodioActivoFijo servicioCustodioActivoFijo;
/*  403:     */   @EJB
/*  404:     */   private ServicioGenerico<RangoDiasComision> servicioRangoDiasComision;
/*  405:     */   @EJB
/*  406:     */   private ServicioPlanComision servicioPlanComision;
/*  407:     */   @EJB
/*  408:     */   private ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  409:     */   @EJB
/*  410:     */   private GenericoDao<CodigoFormaPagoSRI> codigoFormaPagoSRIDao;
/*  411:     */   @EJB
/*  412:     */   private GenericoDao<Empresa> empresaDao;
/*  413:     */   @EJB
/*  414:     */   private transient ServicioGenerico<CodigoFormaPagoSRI> servicioCodigoFormaPagoSRI;
/*  415:     */   
/*  416:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  417:     */   public void migracionPlanDeCuentas(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial)
/*  418:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  419:     */   {
/*  420: 469 */     int filaActual = filaInicial;
/*  421: 470 */     int columnaErronea = -1;
/*  422: 471 */     HSSFCell[] filaErronea = new HSSFCell[0];
/*  423:     */     try
/*  424:     */     {
/*  425: 476 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/*  426:     */       
/*  427:     */ 
/*  428:     */ 
/*  429:     */ 
/*  430: 481 */       String codigoCuentaContableMayor = "";
/*  431:     */       
/*  432:     */ 
/*  433:     */ 
/*  434: 485 */       HashMap<String, CuentaContable> hashMapCuentaContable = new HashMap();
/*  435: 486 */       HashMap<String, String> filtros = new HashMap();
/*  436: 487 */       filtros.put("idOrganizacion", "" + idOrganizacion);
/*  437: 488 */       List<CuentaContable> listaCuentaContables = this.servicioCuentaContable.obtenerListaCombo("", false, filtros);
/*  438: 489 */       for (CuentaContable cc : listaCuentaContables) {
/*  439: 490 */         hashMapCuentaContable.put(cc.getCodigo(), cc);
/*  440:     */       }
/*  441: 494 */       Object hashMapNivelCuenta = new HashMap();
/*  442: 495 */       List<NivelCuenta> listaNivelCuenta = this.servicioNivelCuenta.obtenerListaCombo("", false, filtros);
/*  443: 496 */       for (Object localObject1 = listaNivelCuenta.iterator(); ((Iterator)localObject1).hasNext();)
/*  444:     */       {
/*  445: 496 */         nc = (NivelCuenta)((Iterator)localObject1).next();
/*  446: 497 */         ((HashMap)hashMapNivelCuenta).put(Integer.valueOf(nc.getCodigo()), nc);
/*  447:     */       }
/*  448: 502 */       localObject1 = datos;NivelCuenta nc = localObject1.length;
/*  449: 502 */       for (Object localObject2 = 0; localObject2 < nc; localObject2++)
/*  450:     */       {
/*  451: 502 */         fila = localObject1[localObject2];
/*  452: 503 */         codigoCuenta = fila[0].getStringCellValue().trim();
/*  453: 504 */         if (codigoCuentaContableMayor.length() < codigoCuenta.length()) {
/*  454: 505 */           codigoCuentaContableMayor = codigoCuenta;
/*  455:     */         }
/*  456:     */       }
/*  457: 509 */       String[] cadenaNivel = codigoCuentaContableMayor.split("\\.");
/*  458: 510 */       int codigoNivelCuenta = 1;
/*  459: 511 */       localObject2 = cadenaNivel;HSSFCell[] fila = localObject2.length;
/*  460: 511 */       for (String codigoCuenta = 0; codigoCuenta < fila; codigoCuenta++)
/*  461:     */       {
/*  462: 511 */         String cadena = localObject2[codigoCuenta];
/*  463: 513 */         if (!((HashMap)hashMapNivelCuenta).containsKey(Integer.valueOf(codigoNivelCuenta)))
/*  464:     */         {
/*  465: 515 */           NivelCuenta nivelCuenta = new NivelCuenta();
/*  466: 516 */           nivelCuenta.setIdOrganizacion(idOrganizacion);
/*  467: 517 */           nivelCuenta.setIdSucursal(AppUtil.getSucursal().getId());
/*  468: 518 */           nivelCuenta.setCodigo(codigoNivelCuenta);
/*  469: 519 */           nivelCuenta.setNombre("Nivel Cuenta " + codigoNivelCuenta);
/*  470: 520 */           nivelCuenta.setLongitud(cadena.length());
/*  471: 521 */           nivelCuenta.setActivo(true);
/*  472: 522 */           this.servicioNivelCuenta.guardar(nivelCuenta);
/*  473: 523 */           ((HashMap)hashMapNivelCuenta).put(Integer.valueOf(codigoNivelCuenta), nivelCuenta);
/*  474:     */         }
/*  475: 527 */         codigoNivelCuenta++;
/*  476:     */       }
/*  477: 529 */       localObject2 = datos;fila = localObject2.length;
/*  478: 529 */       for (codigoCuenta = 0; codigoCuenta < fila; codigoCuenta++)
/*  479:     */       {
/*  480: 529 */         HSSFCell[] fila = localObject2[codigoCuenta];
/*  481:     */         
/*  482: 531 */         filaErronea = fila;
/*  483: 532 */         filaActual++;
/*  484:     */         
/*  485:     */ 
/*  486: 535 */         String codigoCuenta = fila[(columnaErronea = 0)].getStringCellValue().trim();
/*  487: 536 */         GrupoCuenta grupoCuenta = GrupoCuenta.valueOf(fila[(columnaErronea = 1)].getStringCellValue().trim());
/*  488: 537 */         String nombreCuenta = fila[(columnaErronea = 2)].getStringCellValue().trim();
/*  489: 538 */         String descripcionCuenta = fila[(columnaErronea = 3)] != null ? fila[(columnaErronea = 3)].getStringCellValue() : null;
/*  490: 539 */         String tipoCuenta = fila[(columnaErronea = 4)].getStringCellValue().trim();
/*  491: 540 */         if (!tipoCuenta.equals(TipoCuentaContable.BANCO.toString())) {
/*  492: 541 */           tipoCuenta = "" + TipoCuentaContable.OTROS;
/*  493:     */         }
/*  494: 543 */         boolean movimiento = fila[(columnaErronea = 5)].getStringCellValue().equalsIgnoreCase("SI");
/*  495: 544 */         boolean indicadorValidarDimension1 = fila[(columnaErronea = 6)].getStringCellValue().equalsIgnoreCase("SI");
/*  496: 545 */         boolean indicadorValidarDimension2 = fila[(columnaErronea = 7)].getStringCellValue().equalsIgnoreCase("SI");
/*  497: 546 */         boolean indicadorValidarDimension3 = fila[(columnaErronea = 8)].getStringCellValue().equalsIgnoreCase("SI");
/*  498: 547 */         boolean indicadorValidarDimension4 = fila[(columnaErronea = 9)].getStringCellValue().equalsIgnoreCase("SI");
/*  499: 548 */         boolean indicadorValidarDimension5 = fila[(columnaErronea = 10)].getStringCellValue().equalsIgnoreCase("SI");
/*  500: 551 */         if (!hashMapCuentaContable.containsKey(codigoCuenta))
/*  501:     */         {
/*  502: 553 */           CuentaContable cuentaContable = new CuentaContable();
/*  503: 554 */           cuentaContable.setIdOrganizacion(idOrganizacion);
/*  504: 555 */           cuentaContable.setIdSucursal(AppUtil.getSucursal().getId());
/*  505: 556 */           cuentaContable.setCodigo(codigoCuenta);
/*  506: 557 */           cuentaContable.setGrupoCuenta(grupoCuenta);
/*  507: 558 */           cuentaContable.setNombre(nombreCuenta);
/*  508: 559 */           cuentaContable.setDescripcion(descripcionCuenta);
/*  509: 560 */           cuentaContable.setTipoCuentaContable(TipoCuentaContable.valueOf(tipoCuenta));
/*  510: 561 */           cuentaContable.setIndicadorMovimiento(movimiento);
/*  511: 562 */           cuentaContable.setIndicadorValidarDimension1(indicadorValidarDimension1);
/*  512: 563 */           cuentaContable.setIndicadorValidarDimension2(indicadorValidarDimension2);
/*  513: 564 */           cuentaContable.setIndicadorValidarDimension3(indicadorValidarDimension3);
/*  514: 565 */           cuentaContable.setIndicadorValidarDimension4(indicadorValidarDimension4);
/*  515: 566 */           cuentaContable.setIndicadorValidarDimension5(indicadorValidarDimension5);
/*  516: 567 */           cuentaContable.setActivo(true);
/*  517: 568 */           cuentaContable.setTipoAccesoCuentaContable(TipoAccesoContable.LIBRE);
/*  518: 569 */           String[] nivel = codigoCuenta.split("\\.");
/*  519: 570 */           cuentaContable.setNivelCuenta((NivelCuenta)((HashMap)hashMapNivelCuenta).get(Integer.valueOf(nivel.length)));
/*  520: 571 */           if (nivel.length == 1)
/*  521:     */           {
/*  522: 572 */             cuentaContable.setCuentaPadre(null);
/*  523:     */           }
/*  524:     */           else
/*  525:     */           {
/*  526: 574 */             String codigoCuentaPadre = codigoCuenta.substring(0, codigoCuenta
/*  527: 575 */               .length() - cuentaContable.getNivelCuenta().getLongitud() - 1);
/*  528: 576 */             cuentaContable.setCuentaPadre((CuentaContable)hashMapCuentaContable.get(codigoCuentaPadre));
/*  529:     */           }
/*  530: 578 */           this.servicioCuentaContable.guardar(cuentaContable);
/*  531: 579 */           hashMapCuentaContable.put(codigoCuenta, cuentaContable);
/*  532:     */         }
/*  533:     */       }
/*  534:     */     }
/*  535:     */     catch (IllegalArgumentException e)
/*  536:     */     {
/*  537: 585 */       LOG.info("Error al migrar plan de cuentas", e);
/*  538: 586 */       this.context.setRollbackOnly();
/*  539:     */       
/*  540: 588 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/*  541:     */     }
/*  542:     */     catch (ExcepcionAS2Financiero e)
/*  543:     */     {
/*  544: 591 */       LOG.info("Error al migrar plan de cuentas", e);
/*  545: 592 */       this.context.setRollbackOnly();
/*  546: 593 */       throw e;
/*  547:     */     }
/*  548:     */     catch (IllegalStateException e)
/*  549:     */     {
/*  550: 596 */       LOG.info("Error al migrar plan de cuentas", e);
/*  551: 597 */       this.context.setRollbackOnly();
/*  552:     */       
/*  553: 599 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/*  554:     */     }
/*  555:     */     catch (Exception e)
/*  556:     */     {
/*  557: 602 */       LOG.error("Error al migrar plan de cuentas", e);
/*  558: 603 */       this.context.setRollbackOnly();
/*  559: 604 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/*  560:     */     }
/*  561:     */   }
/*  562:     */   
/*  563:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  564:     */   public void migracionClientesProveedores(int idOrganizacion, int idSucursal, String fileName, InputStream imInputStream, int filaInicial, List<Empresa> listaClientesProveedorNoMigrados)
/*  565:     */     throws ExcepcionAS2, ExcepcionAS2Identification, AS2Exception
/*  566:     */   {
/*  567: 620 */     HashMap<String, CategoriaEmpresa> hashMapCategoriaEmpresa = new HashMap();
/*  568: 621 */     List<CategoriaEmpresa> listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("", false, null);
/*  569: 622 */     for (CategoriaEmpresa ce : listaCategoriaEmpresa) {
/*  570: 623 */       hashMapCategoriaEmpresa.put(ce.getCodigo().trim(), ce);
/*  571:     */     }
/*  572: 626 */     Object hashMapFormaPago = new HashMap();
/*  573: 627 */     List<FormaPago> listaFormaPago = this.servicioFormaPago.obtenerListaCombo("", false, null);
/*  574: 628 */     for (FormaPago fp : listaFormaPago) {
/*  575: 629 */       ((HashMap)hashMapFormaPago).put(fp.getCodigo().trim(), fp);
/*  576:     */     }
/*  577: 632 */     Object hashMapCondicionPago = new HashMap();
/*  578: 633 */     List<CondicionPago> listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("", false, null);
/*  579: 634 */     for (CondicionPago cp : listaCondicionPago) {
/*  580: 635 */       ((HashMap)hashMapCondicionPago).put(cp.getCodigo().trim(), cp);
/*  581:     */     }
/*  582: 638 */     Object hashTipoIdentificacion = new HashMap();
/*  583: 639 */     List<TipoIdentificacion> listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("", false, null);
/*  584: 640 */     for (TipoIdentificacion ti : listaTipoIdentificacion) {
/*  585: 641 */       ((HashMap)hashTipoIdentificacion).put(ti.getCodigo().trim(), ti);
/*  586:     */     }
/*  587: 644 */     Object hashProvincia = new HashMap();
/*  588: 645 */     List<Provincia> listaProvincia = this.servicioProvincia.obtenerListaCombo("", false, null);
/*  589: 646 */     for (Provincia ci : listaProvincia) {
/*  590: 647 */       ((HashMap)hashProvincia).put(ci.getCodigo().trim(), ci);
/*  591:     */     }
/*  592: 650 */     Object hashCiudad = new HashMap();
/*  593: 651 */     List<Ciudad> listaCiudad = this.servicioCiudad.obtenerListaCombo("", false, null);
/*  594: 652 */     for (Ciudad ci : listaCiudad) {
/*  595: 653 */       ((HashMap)hashCiudad).put(ci.getProvincia().getCodigo().trim() + "~" + ci.getCodigo().trim(), ci);
/*  596:     */     }
/*  597: 656 */     Object hashEstadoCivil = new HashMap();
/*  598: 657 */     List<EstadoCivil> listaEstadoCivil = this.servicioEstadoCivil.obtenerListaCombo("", false, null);
/*  599: 658 */     for (EstadoCivil ec : listaEstadoCivil) {
/*  600: 659 */       ((HashMap)hashEstadoCivil).put(ec.getCodigo().trim(), ec);
/*  601:     */     }
/*  602: 662 */     Object hashPais = new HashMap();
/*  603: 663 */     List<Pais> listaPais = this.servicioPais.obtenerListaCombo("", false, null);
/*  604: 664 */     for (Pais p : listaPais) {
/*  605: 665 */       ((HashMap)hashPais).put(p.getCodigoIso().trim(), p);
/*  606:     */     }
/*  607: 668 */     Object hashBanco = new HashMap();
/*  608: 669 */     List<Banco> listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "", false, null);
/*  609: 670 */     for (Banco b : listaBanco) {
/*  610: 671 */       ((HashMap)hashBanco).put(b.getCodigo().trim(), b);
/*  611:     */     }
/*  612: 674 */     Object hashTipoCtaBco = new HashMap();
/*  613: 675 */     List<TipoCuentaBancaria> listaTipoCtaBco = this.servicioTipoCuentaBancaria.obtenerListaCombo("", false, null);
/*  614: 676 */     for (TipoCuentaBancaria tcb : listaTipoCtaBco) {
/*  615: 677 */       ((HashMap)hashTipoCtaBco).put(tcb.getCodigo().trim(), tcb);
/*  616:     */     }
/*  617: 680 */     Object hashCatRetencion = new HashMap();
/*  618: 681 */     List<CategoriaRetencion> listaCatRetencion = this.servicioCategoriaRetencion.obtenerListaCombo("", false, null);
/*  619: 682 */     for (CategoriaRetencion cr : listaCatRetencion) {
/*  620: 683 */       ((HashMap)hashCatRetencion).put(cr.getCodigo().trim(), cr);
/*  621:     */     }
/*  622: 686 */     Object hashMapEmpresa = new HashMap();
/*  623: 687 */     List<Empresa> listaEmpresa = this.servicioEmpresa.obtenerListaCombo("", false, null);
/*  624: 688 */     for (Empresa e : listaEmpresa) {
/*  625: 689 */       ((HashMap)hashMapEmpresa).put(e.getCodigo(), e);
/*  626:     */     }
/*  627: 692 */     CodigoFormaPagoSRI codigoFormaPagoSRI = null;
/*  628: 693 */     Map<String, String> filters = new HashMap();
/*  629: 694 */     filters.put("predeterminado", "true");
/*  630: 695 */     List<CodigoFormaPagoSRI> listaCodigoFormaPagoSRI = this.servicioCodigoFormaPagoSRI.obtenerListaCombo(CodigoFormaPagoSRI.class, null, true, filters);
/*  631: 697 */     if (!listaCodigoFormaPagoSRI.isEmpty()) {
/*  632: 698 */       codigoFormaPagoSRI = (CodigoFormaPagoSRI)listaCodigoFormaPagoSRI.get(0);
/*  633:     */     }
/*  634: 701 */     int filaActual = filaInicial;
/*  635: 702 */     int columnaActual = 0;
/*  636: 703 */     HSSFCell[] filaErronea = new HSSFCell[0];
/*  637:     */     try
/*  638:     */     {
/*  639: 706 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/*  640: 708 */       for (HSSFCell[] fila : datos)
/*  641:     */       {
/*  642: 710 */         filaErronea = fila;
/*  643: 711 */         filaActual++;
/*  644: 713 */         if (fila[0] != null) {
/*  645: 714 */           fila[0].setCellType(1);
/*  646:     */         }
/*  647: 715 */         String codigoEmpresa = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 0, true, Integer.valueOf(0), Integer.valueOf(20));
/*  648:     */         
/*  649:     */ 
/*  650:     */ 
/*  651:     */ 
/*  652:     */ 
/*  653:     */ 
/*  654: 722 */         String nombreFiscal = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 1, true, Integer.valueOf(1), Integer.valueOf(100));
/*  655:     */         
/*  656: 724 */         String nombreComercial = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 2, true, Integer.valueOf(0), Integer.valueOf(100));
/*  657:     */         
/*  658: 726 */         String codigoTipoIdentificacion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 3, true, 
/*  659: 727 */           Integer.valueOf(0), Integer.valueOf(10));
/*  660: 728 */         TipoIdentificacion tipoIdentificacion = (TipoIdentificacion)((HashMap)hashTipoIdentificacion).get(codigoTipoIdentificacion);
/*  661: 729 */         if (tipoIdentificacion == null) {
/*  662: 731 */           throw new ExcepcionAS2("msg_error_no_existe_tipo_identificacion", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  663:     */         }
/*  664: 734 */         if (fila[4] != null) {
/*  665: 735 */           fila[4].setCellType(1);
/*  666:     */         }
/*  667: 736 */         String identificacion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 4, true, Integer.valueOf(0), Integer.valueOf(20));
/*  668:     */         try
/*  669:     */         {
/*  670: 744 */           ValidarIdentificacion.validarIdentificacion(tipoIdentificacion.isIndicadorValidarIdentificacion(), identificacion);
/*  671:     */         }
/*  672:     */         catch (ExcepcionAS2Identification e)
/*  673:     */         {
/*  674: 747 */           throw new ExcepcionAS2(e.getCodigoExcepcion(), "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  675:     */         }
/*  676: 750 */         if ((tipoIdentificacion.getCodigo().equals(codigoTipoIdentificacion)) && (
/*  677: 751 */           (identificacion.length() > tipoIdentificacion.getLongitudMaxima()) || 
/*  678: 752 */           (identificacion.toString().length() < tipoIdentificacion.getLongitudMaxima()))) {
/*  679: 753 */           throw new ExcepcionAS2("msg_error_numero_identificacion", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  680:     */         }
/*  681: 757 */         String codigoCategoriaEmpresa = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, columnaActual = filaActual, 5, true, 
/*  682: 758 */           Integer.valueOf(0), Integer.valueOf(10));
/*  683:     */         
/*  684: 760 */         String nombreCategoriaEmpresa = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, columnaActual = filaActual, 6, true, 
/*  685: 761 */           Integer.valueOf(0), Integer.valueOf(50));
/*  686:     */         
/*  687: 763 */         String nombreTipoEmpresa = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 7, true, Integer.valueOf(0), 
/*  688: 764 */           Integer.valueOf(10));
/*  689: 765 */         TipoEmpresa tipoEmpresa = nombreTipoEmpresa.equalsIgnoreCase(TipoEmpresa.JURIDICA.toString()) ? TipoEmpresa.JURIDICA : TipoEmpresa.NATURAL;
/*  690:     */         
/*  691:     */ 
/*  692: 768 */         String telefono1 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 8, false, Integer.valueOf(0), Integer.valueOf(13));
/*  693:     */         
/*  694: 770 */         String telefono2 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 9, false, Integer.valueOf(0), Integer.valueOf(13));
/*  695:     */         
/*  696: 772 */         String direccion1 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 10, true, Integer.valueOf(2), Integer.valueOf(50));
/*  697:     */         
/*  698: 774 */         String direccion2 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 11, false, Integer.valueOf(2), Integer.valueOf(50));
/*  699:     */         
/*  700: 776 */         String direccion3 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 12, false, Integer.valueOf(2), Integer.valueOf(50));
/*  701:     */         
/*  702: 778 */         String direccion4 = "";
/*  703:     */         
/*  704: 780 */         String email1 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 13, false, Integer.valueOf(2), Integer.valueOf(50));
/*  705:     */         
/*  706: 782 */         String email2 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 14, false, Integer.valueOf(2), Integer.valueOf(50));
/*  707:     */         
/*  708: 784 */         String cadenaIndicadorCliente = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 15, true, 
/*  709: 785 */           Integer.valueOf(0), Integer.valueOf(2));
/*  710: 786 */         boolean indicadorCliente = cadenaIndicadorCliente.equalsIgnoreCase("SI");
/*  711:     */         
/*  712: 788 */         String cadenaIndicadorProveedor = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 16, true, 
/*  713: 789 */           Integer.valueOf(0), Integer.valueOf(2));
/*  714: 790 */         boolean indicadorProveedor = cadenaIndicadorProveedor.equalsIgnoreCase("SI");
/*  715: 792 */         if (fila[17] != null) {
/*  716: 793 */           fila[17].setCellType(1);
/*  717:     */         }
/*  718: 794 */         String codigoProvincia = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 17, true, Integer.valueOf(0), Integer.valueOf(10));
/*  719: 795 */         Provincia provincia = (Provincia)((HashMap)hashProvincia).get(codigoProvincia);
/*  720: 796 */         if (provincia == null) {
/*  721: 798 */           throw new ExcepcionAS2("msg_error_provincia_no_registrada", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  722:     */         }
/*  723: 802 */         if (fila[18] != null) {
/*  724: 803 */           fila[18].setCellType(1);
/*  725:     */         }
/*  726: 804 */         String codigoCiudad = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 18, true, Integer.valueOf(0), Integer.valueOf(10));
/*  727: 805 */         Ciudad ciudad = (Ciudad)((HashMap)hashCiudad).get(codigoProvincia + "~" + codigoCiudad);
/*  728: 806 */         if (ciudad == null) {
/*  729: 808 */           throw new ExcepcionAS2("msg_error_ciudad_no_registrada", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString() + "\n Con la provincia de codigo: " + codigoProvincia);
/*  730:     */         }
/*  731: 811 */         String codigoFormaPago = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 19, true, Integer.valueOf(0), Integer.valueOf(10));
/*  732: 812 */         FormaPago formaPago = (FormaPago)((HashMap)hashMapFormaPago).get(codigoFormaPago);
/*  733: 813 */         if (formaPago == null) {
/*  734: 815 */           throw new ExcepcionAS2("msg_error_forma_pago_no_registrado", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  735:     */         }
/*  736: 818 */         String codigoCondicionPago = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 20, true, Integer.valueOf(0), 
/*  737: 819 */           Integer.valueOf(10));
/*  738: 820 */         CondicionPago condicionPago = (CondicionPago)((HashMap)hashMapCondicionPago).get(codigoCondicionPago);
/*  739: 821 */         if (condicionPago == null) {
/*  740: 823 */           throw new ExcepcionAS2("msg_error_condicion_pago_no_registrado", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  741:     */         }
/*  742: 826 */         BigDecimal creditoMaximo = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 21, true, 
/*  743: 827 */           Integer.valueOf(0), Integer.valueOf(0));
/*  744:     */         
/*  745: 829 */         String cadenaIndicadorClienteLocalExterior = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 22, true, 
/*  746: 830 */           Integer.valueOf(0), Integer.valueOf(2));
/*  747:     */         
/*  748: 832 */         boolean indicadorClienteLocalExterior = cadenaIndicadorClienteLocalExterior.equalsIgnoreCase("SI");
/*  749:     */         
/*  750: 834 */         Genero genero = null;
/*  751: 835 */         EstadoCivil estadoCivil = null;
/*  752: 836 */         if (tipoEmpresa.equals(TipoEmpresa.NATURAL))
/*  753:     */         {
/*  754: 837 */           String nombreGenero = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 23, true, Integer.valueOf(0), 
/*  755: 838 */             Integer.valueOf(10));
/*  756: 839 */           genero = nombreGenero.equalsIgnoreCase(Genero.FEMENINO.toString()) ? Genero.FEMENINO : Genero.MASCULINO;
/*  757: 840 */           String codigoEstadoCivil = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 24, true, 
/*  758: 841 */             Integer.valueOf(0), Integer.valueOf(10));
/*  759: 842 */           estadoCivil = (EstadoCivil)((HashMap)hashEstadoCivil).get(codigoEstadoCivil);
/*  760: 843 */           if (estadoCivil == null) {
/*  761: 845 */             throw new ExcepcionAS2("msg_error_estado_civil_no_registrado", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  762:     */           }
/*  763:     */         }
/*  764: 849 */         Banco banco = null;
/*  765: 850 */         Pais pais = null;
/*  766: 851 */         TipoCuentaBancaria tipoCtaBco = null;
/*  767: 852 */         String numeroCuenta = null;
/*  768: 853 */         if (fila[25] != null) {
/*  769: 854 */           fila[25].setCellType(1);
/*  770:     */         }
/*  771: 855 */         String codigoBanco = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 25, false, Integer.valueOf(0), Integer.valueOf(10));
/*  772: 856 */         if (codigoBanco != null)
/*  773:     */         {
/*  774: 857 */           banco = (Banco)((HashMap)hashBanco).get(codigoBanco);
/*  775: 858 */           if (banco == null) {
/*  776: 860 */             throw new ExcepcionAS2("msg_error_banco_no_registrado", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  777:     */           }
/*  778: 863 */           String codigoPaisBanco = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 26, true, 
/*  779: 864 */             Integer.valueOf(0), Integer.valueOf(10));
/*  780: 865 */           pais = (Pais)((HashMap)hashPais).get(codigoPaisBanco);
/*  781: 866 */           if (pais == null) {
/*  782: 868 */             throw new ExcepcionAS2("msg_error_pais_no_registrado", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  783:     */           }
/*  784: 871 */           String codigoTipoCuenta = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 27, true, 
/*  785: 872 */             Integer.valueOf(0), Integer.valueOf(10));
/*  786: 873 */           tipoCtaBco = (TipoCuentaBancaria)((HashMap)hashTipoCtaBco).get(codigoTipoCuenta);
/*  787: 874 */           if (tipoCtaBco == null) {
/*  788: 876 */             throw new ExcepcionAS2("msg_error_tipo_cuenta_bancaria_no_registrada", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  789:     */           }
/*  790: 879 */           numeroCuenta = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 28, true, Integer.valueOf(6), Integer.valueOf(20));
/*  791:     */         }
/*  792: 883 */         CategoriaRetencion catRetencion = null;
/*  793: 884 */         if (indicadorProveedor)
/*  794:     */         {
/*  795: 885 */           String codigoCatRetencion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 29, false, 
/*  796: 886 */             Integer.valueOf(0), Integer.valueOf(10));
/*  797: 887 */           if (codigoCatRetencion != null)
/*  798:     */           {
/*  799: 888 */             catRetencion = (CategoriaRetencion)((HashMap)hashCatRetencion).get(codigoCatRetencion);
/*  800: 889 */             if (catRetencion == null) {
/*  801: 891 */               throw new ExcepcionAS2("msg_error_categoria_retencion_no_registrada", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/*  802:     */             }
/*  803:     */           }
/*  804:     */         }
/*  805: 895 */         boolean facturaElectronica = false;
/*  806: 896 */         if (fila[30] != null)
/*  807:     */         {
/*  808: 897 */           String facturaElectronicaSt = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 30, false, 
/*  809: 898 */             Integer.valueOf(0), Integer.valueOf(10));
/*  810: 899 */           facturaElectronica = facturaElectronicaSt.equalsIgnoreCase("SI");
/*  811:     */         }
/*  812: 902 */         String establecimiento = null;
/*  813: 903 */         if (fila[31] != null)
/*  814:     */         {
/*  815: 904 */           fila[31].setCellType(1);
/*  816: 905 */           establecimiento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 31, false, Integer.valueOf(0), Integer.valueOf(10));
/*  817:     */         }
/*  818: 908 */         String punto = null;
/*  819: 909 */         if (fila[32] != null)
/*  820:     */         {
/*  821: 910 */           fila[32].setCellType(1);
/*  822: 911 */           punto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 32, false, Integer.valueOf(0), Integer.valueOf(10));
/*  823:     */         }
/*  824: 914 */         String autorizacion = null;
/*  825: 915 */         if (fila[33] != null)
/*  826:     */         {
/*  827: 916 */           fila[33].setCellType(1);
/*  828: 917 */           autorizacion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 33, false, Integer.valueOf(0), Integer.valueOf(50));
/*  829:     */         }
/*  830: 920 */         Date fechaEmision = null;
/*  831: 921 */         if (fila[34] != null) {
/*  832: 923 */           fechaEmision = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 34, false, Integer.valueOf(0), Integer.valueOf(50));
/*  833:     */         }
/*  834: 926 */         Date fechaCaducidad = null;
/*  835: 927 */         if (fila[35] != null) {
/*  836: 929 */           fechaCaducidad = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 35, false, Integer.valueOf(0), Integer.valueOf(50));
/*  837:     */         }
/*  838: 932 */         CategoriaEmpresa categoriaEmpresa = (CategoriaEmpresa)hashMapCategoriaEmpresa.get(codigoCategoriaEmpresa);
/*  839: 933 */         if (categoriaEmpresa == null)
/*  840:     */         {
/*  841: 934 */           categoriaEmpresa = new CategoriaEmpresa();
/*  842: 935 */           categoriaEmpresa.setIdOrganizacion(idOrganizacion);
/*  843: 936 */           categoriaEmpresa.setIdSucursal(idSucursal);
/*  844: 937 */           categoriaEmpresa.setActivo(true);
/*  845: 938 */           categoriaEmpresa.setNombre(nombreCategoriaEmpresa);
/*  846: 939 */           categoriaEmpresa.setDescripcion("");
/*  847: 940 */           categoriaEmpresa.setCodigo(codigoCategoriaEmpresa);
/*  848: 941 */           categoriaEmpresa.setPredeterminado(false);
/*  849: 942 */           this.servicioCategoriaEmpresa.guardar(categoriaEmpresa);
/*  850: 943 */           hashMapCategoriaEmpresa.put(codigoCategoriaEmpresa, categoriaEmpresa);
/*  851:     */         }
/*  852: 946 */         Empresa empresa = (Empresa)((HashMap)hashMapEmpresa).get(codigoEmpresa.trim());
/*  853: 947 */         if (empresa == null)
/*  854:     */         {
/*  855: 948 */           Map<String, String> filtrosEmpresa = new HashMap();
/*  856: 949 */           filtrosEmpresa.put("identificacion", "=" + identificacion);
/*  857: 950 */           List<Empresa> emp = this.servicioEmpresa.obtenerListaCombo("", false, filtrosEmpresa);
/*  858: 951 */           if (emp.isEmpty())
/*  859:     */           {
/*  860: 952 */             empresa = new Empresa();
/*  861: 953 */             empresa.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  862: 954 */             empresa.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  863:     */             
/*  864: 956 */             Ubicacion ubicacion = new Ubicacion();
/*  865: 957 */             ubicacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  866: 958 */             ubicacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  867: 959 */             ubicacion.setDireccion1(direccion1);
/*  868: 960 */             ubicacion.setDireccion2(direccion2 == null ? "" : direccion2);
/*  869: 961 */             ubicacion.setDireccion3(direccion3);
/*  870: 962 */             ubicacion.setDireccion4(direccion4);
/*  871:     */             
/*  872: 964 */             DireccionEmpresa direccionEmpresa = new DireccionEmpresa();
/*  873: 965 */             direccionEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  874: 966 */             direccionEmpresa.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  875: 967 */             direccionEmpresa.setUbicacion(ubicacion);
/*  876: 968 */             direccionEmpresa.setTelefono1(telefono1);
/*  877: 969 */             direccionEmpresa.setTelefono1(telefono2);
/*  878: 970 */             direccionEmpresa.setEmpresa(empresa);
/*  879: 971 */             direccionEmpresa.setCiudad(ciudad);
/*  880: 972 */             List<DireccionEmpresa> direcciones = new ArrayList();
/*  881: 973 */             direcciones.add(direccionEmpresa);
/*  882: 976 */             if (banco != null)
/*  883:     */             {
/*  884: 977 */               CuentaBancaria ctaBancaria = new CuentaBancaria();
/*  885: 978 */               ctaBancaria.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  886: 979 */               ctaBancaria.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  887: 980 */               ctaBancaria.setPais(pais);
/*  888: 981 */               ctaBancaria.setBanco(banco);
/*  889: 982 */               ctaBancaria.setTipoCuentaBancaria(tipoCtaBco);
/*  890: 983 */               ctaBancaria.setNumero(numeroCuenta);
/*  891: 984 */               ctaBancaria.setPredeterminado(false);
/*  892:     */               
/*  893: 986 */               CuentaBancariaEmpresa ctaBancariaEmpresa = new CuentaBancariaEmpresa();
/*  894: 987 */               ctaBancariaEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  895: 988 */               ctaBancariaEmpresa.setIdSucursal(Integer.valueOf(AppUtil.getSucursal().getIdSucursal()));
/*  896: 989 */               ctaBancariaEmpresa.setEmpresa(empresa);
/*  897: 990 */               ctaBancariaEmpresa.setCuentaBancaria(ctaBancaria);
/*  898:     */               
/*  899: 992 */               List<CuentaBancariaEmpresa> listaCuentaBancariaEmpresa = new ArrayList();
/*  900: 993 */               listaCuentaBancariaEmpresa.add(ctaBancariaEmpresa);
/*  901: 994 */               empresa.setListaCuentaBancariaEmpresa(listaCuentaBancariaEmpresa);
/*  902:     */             }
/*  903: 997 */             empresa.setCodigo(codigoEmpresa);
/*  904: 998 */             empresa.setTipoIdentificacion(tipoIdentificacion);
/*  905: 999 */             empresa.setIdentificacion(identificacion);
/*  906:1000 */             empresa.setNombreComercial(nombreComercial);
/*  907:1001 */             empresa.setNombreFiscal(nombreFiscal);
/*  908:1002 */             empresa.setEmail1(email1);
/*  909:1003 */             empresa.setEmail2(email2);
/*  910:1004 */             empresa.setTipoEmpresa(tipoEmpresa);
/*  911:1005 */             empresa.setDirecciones(direcciones);
/*  912:1006 */             empresa.setCategoriaEmpresa(categoriaEmpresa);
/*  913:1007 */             empresa.setActivo(true);
/*  914:1008 */             if (tipoEmpresa.equals(TipoEmpresa.NATURAL))
/*  915:     */             {
/*  916:1009 */               empresa.setGenero(genero);
/*  917:1010 */               empresa.setEstadoCivil(estadoCivil);
/*  918:     */             }
/*  919:1012 */             if (indicadorCliente == true)
/*  920:     */             {
/*  921:1013 */               Cliente cliente = new Cliente();
/*  922:1014 */               cliente.setIdOrganizacion(idOrganizacion);
/*  923:1015 */               cliente.setIdSucursal(idSucursal);
/*  924:1016 */               cliente.setFormaPago(formaPago);
/*  925:1017 */               cliente.setCondicionPago(condicionPago);
/*  926:1018 */               cliente.setEmpresa(empresa);
/*  927:     */               
/*  928:1020 */               cliente.setTipoCliente(TipoEmpresaEnum.PRINCIPAL);
/*  929:1021 */               cliente.setNumeroCuotas(1);
/*  930:1022 */               cliente.setMetodoFacturacion(MetodoFacturacionEnum.PEDIDO_DESPACHO_FACTURA);
/*  931:1023 */               cliente.setCreditoMaximo(creditoMaximo);
/*  932:1024 */               cliente.setCreditoDisponible(creditoMaximo);
/*  933:1025 */               cliente.setIndicadorExterior(Boolean.valueOf(indicadorClienteLocalExterior));
/*  934:1026 */               empresa.setIndicadorCliente(true);
/*  935:1027 */               empresa.setCliente(cliente);
/*  936:     */             }
/*  937:1030 */             if (indicadorProveedor == true)
/*  938:     */             {
/*  939:1031 */               Proveedor proveedor = new Proveedor();
/*  940:1032 */               proveedor.setIdOrganizacion(idOrganizacion);
/*  941:1033 */               proveedor.setIdSucursal(idSucursal);
/*  942:1034 */               proveedor.setFormaPago(formaPago);
/*  943:1035 */               proveedor.setCondicionPago(condicionPago);
/*  944:1036 */               proveedor.setEmpresa(empresa);
/*  945:1037 */               proveedor.setNumeroCuotas(1);
/*  946:1038 */               proveedor.setIndicadorParteRelacionada(false);
/*  947:1039 */               proveedor.setTipoProveedor(TipoEmpresaEnum.PRINCIPAL);
/*  948:1041 */               if (catRetencion != null) {
/*  949:1042 */                 proveedor.setCategoriaRetencion(catRetencion);
/*  950:     */               }
/*  951:1044 */               empresa.setIndicadorProveedor(true);
/*  952:1045 */               empresa.setProveedor(proveedor);
/*  953:     */               
/*  954:1047 */               FormaPagoSRI formaPagoSRI = new FormaPagoSRI();
/*  955:1048 */               formaPagoSRI.setIdOrganizacion(idOrganizacion);
/*  956:1049 */               formaPagoSRI.setIdSucursal(idSucursal);
/*  957:1050 */               formaPagoSRI.setEmpresa(empresa);
/*  958:1051 */               formaPagoSRI.setCodigo("02");
/*  959:1052 */               empresa.getListaFormaPagoSRI().add(formaPagoSRI);
/*  960:1054 */               if (establecimiento != null)
/*  961:     */               {
/*  962:1055 */                 AutorizacionProveedorSRI autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/*  963:1056 */                 autorizacionProveedorSRI.setIdOrganizacion(idOrganizacion);
/*  964:1057 */                 autorizacionProveedorSRI.setIdSucursal(idSucursal);
/*  965:1058 */                 autorizacionProveedorSRI.setEmpresa(empresa);
/*  966:1059 */                 autorizacionProveedorSRI.setActivo(true);
/*  967:1060 */                 autorizacionProveedorSRI.setIndicadorFacturaElectronica(facturaElectronica);
/*  968:1061 */                 autorizacionProveedorSRI.setEstablecimiento(establecimiento);
/*  969:1062 */                 autorizacionProveedorSRI.setPuntoEmision(punto);
/*  970:1063 */                 autorizacionProveedorSRI.setFechaDesde(fechaEmision);
/*  971:1064 */                 autorizacionProveedorSRI.setFechaHasta(fechaCaducidad);
/*  972:1065 */                 autorizacionProveedorSRI.setAutorizacion(autorizacion);
/*  973:1067 */                 if (facturaElectronica)
/*  974:     */                 {
/*  975:1068 */                   autorizacionProveedorSRI.setAutorizacion("0000000000000000000000000000000000000");
/*  976:1069 */                   autorizacionProveedorSRI.setFechaDesde(FuncionesUtiles.getFecha(1, 1, 1999));
/*  977:1070 */                   autorizacionProveedorSRI.setFechaHasta(FuncionesUtiles.getFecha(31, 12, 2999));
/*  978:     */                 }
/*  979:1073 */                 empresa.getListaAutorizacionProveedorSRI().add(autorizacionProveedorSRI);
/*  980:     */               }
/*  981:     */             }
/*  982:1076 */             this.servicioEmpresa.guardar(empresa);
/*  983:1077 */             if (codigoFormaPagoSRI != null)
/*  984:     */             {
/*  985:1078 */               FormaPagoSRI fpSRI = new FormaPagoSRI();
/*  986:1079 */               fpSRI.setIdOrganizacion(idOrganizacion);
/*  987:1080 */               fpSRI.setEmpresa(empresa);
/*  988:1081 */               fpSRI.setCodigo(codigoFormaPagoSRI.getCodigo());
/*  989:1082 */               this.servicioFormaPagoSRI.guardar(fpSRI);
/*  990:     */             }
/*  991:     */             else
/*  992:     */             {
/*  993:1084 */               throw new ExcepcionAS2("msg_configure_forma_pago_sri_predeterminada", "");
/*  994:     */             }
/*  995:1086 */             ((HashMap)hashMapEmpresa).put(empresa.getCodigo(), empresa);
/*  996:     */           }
/*  997:     */           else
/*  998:     */           {
/*  999:1088 */             ((Empresa)emp.get(0)).setMigracionExisteIdentificacion(Boolean.valueOf(true));
/* 1000:1089 */             listaClientesProveedorNoMigrados.add(emp.get(0));
/* 1001:     */           }
/* 1002:     */         }
/* 1003:     */         else
/* 1004:     */         {
/* 1005:1092 */           empresa.setMigracionExisteIdentificacion(Boolean.valueOf(false));
/* 1006:1093 */           listaClientesProveedorNoMigrados.add(empresa);
/* 1007:     */         }
/* 1008:     */       }
/* 1009:     */     }
/* 1010:     */     catch (IllegalArgumentException e)
/* 1011:     */     {
/* 1012:1098 */       LOG.info("Error al migrar cliente/proveedor", e);
/* 1013:1099 */       this.context.setRollbackOnly();
/* 1014:     */       
/* 1015:1101 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 1016:     */     }
/* 1017:     */     catch (IllegalStateException e)
/* 1018:     */     {
/* 1019:1104 */       LOG.info("Error al migrar cliente/proveedor", e);
/* 1020:1105 */       this.context.setRollbackOnly();
/* 1021:     */       
/* 1022:1107 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 1023:     */     }
/* 1024:     */     catch (AS2Exception e)
/* 1025:     */     {
/* 1026:1109 */       LOG.info("Error al migrar cliente/proveedor", e);
/* 1027:1110 */       this.context.setRollbackOnly();
/* 1028:1111 */       throw e;
/* 1029:     */     }
/* 1030:     */     catch (ExcepcionAS2 e)
/* 1031:     */     {
/* 1032:1113 */       LOG.info("Error al migrar cliente/proveedor", e);
/* 1033:1114 */       this.context.setRollbackOnly();
/* 1034:1115 */       throw e;
/* 1035:     */     }
/* 1036:     */     catch (Exception e)
/* 1037:     */     {
/* 1038:1117 */       LOG.error("Error al migrar dimensiones contables", e);
/* 1039:1118 */       this.context.setRollbackOnly();
/* 1040:1119 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 1041:     */     }
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public void migracionProductos(int idOrganizacion, int idSucursal, String fileName, InputStream imInputStream, int filaInicial, List<Producto> listaProducto)
/* 1045:     */     throws ExcepcionAS2, AS2Exception
/* 1046:     */   {
/* 1047:1132 */     HashMap<String, CategoriaProducto> hashMapCategoriaProducto = new HashMap();
/* 1048:1133 */     List<CategoriaProducto> listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("", false, null);
/* 1049:1134 */     for (CategoriaProducto cp : listaCategoriaProducto) {
/* 1050:1135 */       hashMapCategoriaProducto.put(cp.getCodigo(), cp);
/* 1051:     */     }
/* 1052:1138 */     Object hashMapCategoriaImpuesto = new HashMap();
/* 1053:1139 */     List<CategoriaImpuesto> listaCategoriaImpuesto = this.servicioCategoriaImpuesto.obtenerListaCombo("", false, null);
/* 1054:1140 */     for (CategoriaImpuesto ci : listaCategoriaImpuesto) {
/* 1055:1141 */       ((HashMap)hashMapCategoriaImpuesto).put(ci.getCodigo(), ci);
/* 1056:     */     }
/* 1057:1144 */     Object hashMapUnidad = new HashMap();
/* 1058:1145 */     List<Unidad> listaUnidadProducto = this.servicioUnidad.obtenerListaCombo("", false, null);
/* 1059:1146 */     for (Unidad u : listaUnidadProducto) {
/* 1060:1147 */       ((HashMap)hashMapUnidad).put(u.getCodigo(), u);
/* 1061:     */     }
/* 1062:1150 */     Object hashMapSubcategoriaProducto = new HashMap();
/* 1063:1151 */     List<SubcategoriaProducto> listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("", false, null);
/* 1064:1152 */     for (SubcategoriaProducto sp : listaSubcategoriaProducto) {
/* 1065:1153 */       ((HashMap)hashMapSubcategoriaProducto).put(sp.getCodigo(), sp);
/* 1066:     */     }
/* 1067:1156 */     Object hashMapMarcaProducto = new HashMap();
/* 1068:1157 */     List<MarcaProducto> listaMarcaProducto = this.servicioMarcaProducto.obtenerListaCombo("", false, null);
/* 1069:1158 */     for (MarcaProducto ma : listaMarcaProducto) {
/* 1070:1159 */       ((HashMap)hashMapMarcaProducto).put(ma.getCodigo(), ma);
/* 1071:     */     }
/* 1072:1162 */     Object hashMapPresentacionProducto = new HashMap();
/* 1073:1163 */     List<PresentacionProducto> listaPresentacionProducto = this.servicioPresentacionProducto.obtenerListaCombo(PresentacionProducto.class, "", false, null);
/* 1074:1165 */     for (PresentacionProducto pp : listaPresentacionProducto) {
/* 1075:1166 */       ((HashMap)hashMapPresentacionProducto).put(pp.getNombre(), pp);
/* 1076:     */     }
/* 1077:1169 */     Object hashMapTipoPresentacionProducto = new HashMap();
/* 1078:1170 */     Map<String, String> filtrosTipoPresentacion = new HashMap();
/* 1079:1171 */     filtrosTipoPresentacion.put("idOrganizacion", "" + idOrganizacion);
/* 1080:     */     
/* 1081:1173 */     List<TipoPresentacionProducto> listaTipoPresentacionProducto = this.servicioTipoPresentacionProducto.obtenerListaCombo(TipoPresentacionProducto.class, "predeterminado", false, filtrosTipoPresentacion);
/* 1082:1174 */     for (TipoPresentacionProducto tpp : listaTipoPresentacionProducto) {
/* 1083:1175 */       ((HashMap)hashMapTipoPresentacionProducto).put(tpp.getCodigo(), tpp);
/* 1084:     */     }
/* 1085:1178 */     Object hashMapProducto = new HashMap();
/* 1086:1179 */     List<Producto> listaProductos = this.servicioProducto.obtenerListaCombo("", false, null);
/* 1087:1180 */     for (Producto p : listaProductos) {
/* 1088:1181 */       ((HashMap)hashMapProducto).put(p.getCodigo(), p);
/* 1089:     */     }
/* 1090:1184 */     int filaActual = filaInicial;
/* 1091:1185 */     int columnaErronea = 0;
/* 1092:1186 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 1093:     */     try
/* 1094:     */     {
/* 1095:1190 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getId(), fileName, imInputStream, filaInicial, 0);
/* 1096:1192 */       for (HSSFCell[] fila : datos)
/* 1097:     */       {
/* 1098:1194 */         filaErronea = fila;
/* 1099:1195 */         filaActual++;
/* 1100:     */         
/* 1101:1197 */         String codigoProducto = fila[(columnaErronea = 0)].getStringCellValue().trim();
/* 1102:1198 */         String codigoAlternoProducto = fila[(columnaErronea = 1)] == null ? null : fila[(columnaErronea = 1)].getStringCellValue().trim();
/* 1103:1199 */         String codigoBarraProducto = fila[(columnaErronea = 2)] == null ? null : fila[(columnaErronea = 2)].getStringCellValue().trim();
/* 1104:1200 */         String nombreProducto = fila[(columnaErronea = 3)].getStringCellValue().trim();
/* 1105:1201 */         String nombreComercialProducto = fila[(columnaErronea = 4)].getStringCellValue().trim();
/* 1106:1202 */         BigDecimal costoEstadarProducto = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 5)].getNumericCellValue()), 2);
/* 1107:     */         
/* 1108:     */ 
/* 1109:1205 */         BigDecimal precioReferencialProducto = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 6)].getNumericCellValue()), 2);
/* 1110:1206 */         String tipoCosteo = fila[(columnaErronea = 7)].getStringCellValue().trim();
/* 1111:1207 */         if (!tipoCosteo.equals(TipoCosto.ESTANDAR.toString())) {
/* 1112:1208 */           tipoCosteo = "" + TipoCosto.PROMEDIO_PONDERADO;
/* 1113:     */         }
/* 1114:1210 */         String tipoProducto = fila[(columnaErronea = 8)].getStringCellValue();
/* 1115:1211 */         if (!tipoProducto.equals(TipoProducto.ARTICULO.toString())) {
/* 1116:1212 */           tipoProducto = "" + TipoProducto.SERVICIO;
/* 1117:     */         }
/* 1118:1214 */         String codigoCategoriaProducto = fila[(columnaErronea = 9)].getStringCellValue().trim();
/* 1119:1215 */         String nombreCategoriaProducto = fila[(columnaErronea = 10)].getStringCellValue().trim();
/* 1120:1216 */         String codigoSubcategoriaProducto = fila[(columnaErronea = 11)].getStringCellValue().trim();
/* 1121:1217 */         String nombreSubCategoriaProducto = fila[(columnaErronea = 12)].getStringCellValue().trim();
/* 1122:1218 */         boolean indicadorVenta = fila[(columnaErronea = 13)].getStringCellValue().equals("SI");
/* 1123:1219 */         boolean indicadorCompra = fila[(columnaErronea = 14)].getStringCellValue().equals("SI");
/* 1124:1220 */         boolean indicadorProduccion = fila[(columnaErronea = 15)].getStringCellValue().equals("SI");
/* 1125:1221 */         boolean indicadorConsumoDirecto = fila[(columnaErronea = 16)].getStringCellValue().equals("SI");
/* 1126:1222 */         boolean indicadorImpuesto = fila[(columnaErronea = 17)].getStringCellValue().equals("SI");
/* 1127:1223 */         String codigoCategoriaImpuesto = fila[(columnaErronea = 18)].getStringCellValue() == null ? null : fila[18].getStringCellValue().trim();
/* 1128:1224 */         String codigoUnidad = fila[(columnaErronea = 19)].getStringCellValue().trim();
/* 1129:1225 */         String nombreUnidad = fila[(columnaErronea = 20)].getStringCellValue().trim();
/* 1130:1226 */         String tipoUnidad = fila[(columnaErronea = 21)].getStringCellValue().trim();
/* 1131:1227 */         if (tipoUnidad.equals(TipoUnidadMedida.MASA.toString())) {
/* 1132:1228 */           tipoUnidad = "" + TipoUnidadMedida.MASA;
/* 1133:1229 */         } else if (tipoUnidad.equals(TipoUnidadMedida.VOLUMEN.toString())) {
/* 1134:1230 */           tipoUnidad = "" + TipoUnidadMedida.VOLUMEN;
/* 1135:1231 */         } else if (tipoUnidad.equals(TipoUnidadMedida.SUPERFICIE.toString())) {
/* 1136:1232 */           tipoUnidad = "" + TipoUnidadMedida.SUPERFICIE;
/* 1137:1233 */         } else if (tipoUnidad.equals(TipoUnidadMedida.LONGITUD.toString())) {
/* 1138:1234 */           tipoUnidad = "" + TipoUnidadMedida.LONGITUD;
/* 1139:     */         } else {
/* 1140:1236 */           tipoUnidad = "" + TipoUnidadMedida.OTROS;
/* 1141:     */         }
/* 1142:1239 */         BigDecimal cantidadMinimaProducto = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 22)].getNumericCellValue()), 2);
/* 1143:     */         
/* 1144:1241 */         BigDecimal cantidadMaximaProducto = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 23)].getNumericCellValue()), 2);
/* 1145:1242 */         BigDecimal pesoProducto = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 24)].getNumericCellValue()), 2);
/* 1146:1243 */         BigDecimal volumenProducto = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 25)].getNumericCellValue()), 2);
/* 1147:1244 */         String descripcion = fila[(columnaErronea = 26)] == null ? null : fila[(columnaErronea = 26)].getStringCellValue();
/* 1148:     */         
/* 1149:1246 */         String codigoMarcaProducto = fila[(columnaErronea = 27)] == null ? null : fila[(columnaErronea = 27)].getStringCellValue().trim();
/* 1150:1247 */         String nombreMarcaProducto = fila[(columnaErronea = 28)] == null ? null : fila[(columnaErronea = 28)].getStringCellValue().trim();
/* 1151:1248 */         String descripcionMarcaProducto = fila[(columnaErronea = 29)] == null ? null : fila[(columnaErronea = 29)].getStringCellValue();
/* 1152:1249 */         String nombrePresentacionProducto = fila[(columnaErronea = 30)] == null ? null : fila[(columnaErronea = 30)].getStringCellValue().trim();
/* 1153:1250 */         String descripcionPresentacionProducto = fila[(columnaErronea = 31)] == null ? null : fila[(columnaErronea = 31)].getStringCellValue();
/* 1154:     */         
/* 1155:1252 */         BigDecimal cantidadUnidadesPresentacionProducto = fila[(columnaErronea = 32)] == null ? BigDecimal.ONE : FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 32)].getNumericCellValue()), 2);
/* 1156:1253 */         if (cantidadUnidadesPresentacionProducto.compareTo(BigDecimal.ZERO) <= 0) {
/* 1157:1254 */           throw new ExcepcionAS2("msg_inf_cantidad_Unidades_Presentacion");
/* 1158:     */         }
/* 1159:1257 */         String codigoTipoPresentacionProducto = fila[(columnaErronea = 33)] == null ? null : fila[(columnaErronea = 33)].getStringCellValue().trim();
/* 1160:     */         
/* 1161:1259 */         String nombreTipoPresentacionProducto = fila[(columnaErronea = 34)] == null ? null : fila[(columnaErronea = 34)].getStringCellValue().trim();
/* 1162:     */         
/* 1163:     */ 
/* 1164:1262 */         TipoMaterialEnum tipoProductoFormulacion = (fila[(columnaErronea = 35)] == null) || (fila[(columnaErronea = 35)].getStringCellValue().trim().isEmpty()) ? null : TipoMaterialEnum.valueOf(fila[(columnaErronea = 35)].getStringCellValue());
/* 1165:     */         
/* 1166:1264 */         boolean indicadorManejaLote = fila[(columnaErronea = 36)].getStringCellValue().trim().equals("SI");
/* 1167:     */         
/* 1168:1266 */         CategoriaImpuesto categoriaImpuesto = null;
/* 1169:1267 */         if (indicadorImpuesto)
/* 1170:     */         {
/* 1171:1268 */           categoriaImpuesto = (CategoriaImpuesto)((HashMap)hashMapCategoriaImpuesto).get(codigoCategoriaImpuesto);
/* 1172:1269 */           if (categoriaImpuesto == null) {
/* 1173:1270 */             throw new ExcepcionAS2("msg_error_categoria_impuesto", " " + codigoCategoriaImpuesto + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 1174:     */           }
/* 1175:     */         }
/* 1176:1276 */         CategoriaProducto categoriaProducto = (CategoriaProducto)hashMapCategoriaProducto.get(codigoCategoriaProducto);
/* 1177:1277 */         if (categoriaProducto == null)
/* 1178:     */         {
/* 1179:1279 */           categoriaProducto = new CategoriaProducto();
/* 1180:1280 */           categoriaProducto.setIdOrganizacion(idOrganizacion);
/* 1181:1281 */           categoriaProducto.setIdSucursal(idSucursal);
/* 1182:1282 */           categoriaProducto.setCodigo(codigoCategoriaProducto);
/* 1183:1283 */           categoriaProducto.setNombre(nombreCategoriaProducto);
/* 1184:1284 */           categoriaProducto.setActivo(true);
/* 1185:     */           
/* 1186:1286 */           this.servicioCategoriaProducto.guardar(categoriaProducto);
/* 1187:1287 */           hashMapCategoriaProducto.put(codigoCategoriaProducto, categoriaProducto);
/* 1188:     */         }
/* 1189:1290 */         Unidad unidad = (Unidad)((HashMap)hashMapUnidad).get(codigoUnidad);
/* 1190:1291 */         if (unidad == null)
/* 1191:     */         {
/* 1192:1293 */           unidad = new Unidad();
/* 1193:1294 */           unidad.setIdOrganizacion(idOrganizacion);
/* 1194:1295 */           unidad.setIdSucursal(idSucursal);
/* 1195:1296 */           unidad.setNumeroDecimales(Integer.valueOf(2));
/* 1196:1297 */           unidad.setNombre(nombreUnidad);
/* 1197:1298 */           unidad.setCodigo(codigoUnidad);
/* 1198:1299 */           unidad.setTipoUnidadMedida(TipoUnidadMedida.valueOf(tipoUnidad));
/* 1199:1300 */           unidad.setActivo(true);
/* 1200:1301 */           this.servicioUnidad.guardar(unidad);
/* 1201:1302 */           ((HashMap)hashMapUnidad).put(codigoUnidad, unidad);
/* 1202:     */         }
/* 1203:1305 */         SubcategoriaProducto subcategoriaProducto = (SubcategoriaProducto)((HashMap)hashMapSubcategoriaProducto).get(codigoSubcategoriaProducto);
/* 1204:1306 */         if (subcategoriaProducto == null)
/* 1205:     */         {
/* 1206:1308 */           subcategoriaProducto = new SubcategoriaProducto();
/* 1207:1309 */           subcategoriaProducto.setIdOrganizacion(idOrganizacion);
/* 1208:1310 */           subcategoriaProducto.setIdSucursal(idSucursal);
/* 1209:1311 */           subcategoriaProducto.setCodigo(codigoSubcategoriaProducto);
/* 1210:1312 */           subcategoriaProducto.setNombre(nombreSubCategoriaProducto);
/* 1211:1313 */           subcategoriaProducto.setCategoriaProducto(categoriaProducto);
/* 1212:1314 */           subcategoriaProducto.setActivo(true);
/* 1213:     */           
/* 1214:1316 */           this.servicioSubcategoriaProducto.guardar(subcategoriaProducto);
/* 1215:1317 */           ((HashMap)hashMapSubcategoriaProducto).put(codigoSubcategoriaProducto, subcategoriaProducto);
/* 1216:     */         }
/* 1217:1320 */         MarcaProducto marcaProducto = null;
/* 1218:1321 */         if ((codigoMarcaProducto != null) && (!codigoMarcaProducto.isEmpty()))
/* 1219:     */         {
/* 1220:1322 */           marcaProducto = (MarcaProducto)((HashMap)hashMapMarcaProducto).get(codigoMarcaProducto);
/* 1221:1323 */           if (marcaProducto == null)
/* 1222:     */           {
/* 1223:1324 */             marcaProducto = new MarcaProducto();
/* 1224:1325 */             marcaProducto.setIdOrganizacion(idOrganizacion);
/* 1225:1326 */             marcaProducto.setIdSucursal(idSucursal);
/* 1226:1327 */             marcaProducto.setCodigo(codigoMarcaProducto);
/* 1227:1328 */             marcaProducto.setNombre(nombreMarcaProducto);
/* 1228:1329 */             marcaProducto.setDescripcion(descripcionMarcaProducto);
/* 1229:1330 */             marcaProducto.setActivo(true);
/* 1230:     */             
/* 1231:1332 */             this.servicioMarcaProducto.guardar(marcaProducto);
/* 1232:1333 */             ((HashMap)hashMapMarcaProducto).put(codigoMarcaProducto, marcaProducto);
/* 1233:     */           }
/* 1234:     */         }
/* 1235:1337 */         PresentacionProducto presentacionProducto = null;
/* 1236:1338 */         if ((nombrePresentacionProducto != null) && (!nombrePresentacionProducto.isEmpty()))
/* 1237:     */         {
/* 1238:1339 */           presentacionProducto = (PresentacionProducto)((HashMap)hashMapPresentacionProducto).get(nombrePresentacionProducto);
/* 1239:1341 */           if (presentacionProducto == null)
/* 1240:     */           {
/* 1241:1343 */             TipoPresentacionProducto tipoPresentacionProducto = null;
/* 1242:1344 */             if ((codigoTipoPresentacionProducto != null) && (!codigoTipoPresentacionProducto.trim().isEmpty()))
/* 1243:     */             {
/* 1244:1345 */               tipoPresentacionProducto = (TipoPresentacionProducto)((HashMap)hashMapTipoPresentacionProducto).get(codigoTipoPresentacionProducto);
/* 1245:1346 */               if (tipoPresentacionProducto == null)
/* 1246:     */               {
/* 1247:1347 */                 TipoPresentacionProducto tipo = new TipoPresentacionProducto();
/* 1248:1348 */                 tipo.setIdOrganizacion(idOrganizacion);
/* 1249:1349 */                 tipo.setIdSucursal(idSucursal);
/* 1250:1350 */                 tipo.setActivo(true);
/* 1251:1351 */                 tipo.setCodigo(codigoTipoPresentacionProducto);
/* 1252:1352 */                 tipo.setNombre(nombreTipoPresentacionProducto);
/* 1253:1353 */                 this.servicioTipoPresentacionProducto.guardar(tipo);
/* 1254:1354 */                 tipoPresentacionProducto = tipo;
/* 1255:1355 */                 ((HashMap)hashMapTipoPresentacionProducto).put(tipoPresentacionProducto.getCodigo(), tipoPresentacionProducto);
/* 1256:     */               }
/* 1257:     */             }
/* 1258:1358 */             else if (listaTipoPresentacionProducto.size() > 0)
/* 1259:     */             {
/* 1260:1359 */               tipoPresentacionProducto = (TipoPresentacionProducto)listaTipoPresentacionProducto.get(0);
/* 1261:     */             }
/* 1262:1363 */             presentacionProducto = new PresentacionProducto();
/* 1263:1364 */             presentacionProducto.setIdOrganizacion(idSucursal);
/* 1264:1365 */             presentacionProducto.setIdSucursal(idOrganizacion);
/* 1265:1366 */             presentacionProducto.setNombre(nombrePresentacionProducto);
/* 1266:1367 */             presentacionProducto.setDescripcion(descripcionPresentacionProducto);
/* 1267:1368 */             presentacionProducto.setCantidadUnidades(cantidadUnidadesPresentacionProducto);
/* 1268:1369 */             presentacionProducto.setActivo(true);
/* 1269:1370 */             presentacionProducto.setTipoPresentacionProducto(tipoPresentacionProducto);
/* 1270:1371 */             this.servicioPresentacionProducto.guardar(presentacionProducto);
/* 1271:1372 */             ((HashMap)hashMapPresentacionProducto).put(nombrePresentacionProducto, presentacionProducto);
/* 1272:     */           }
/* 1273:     */         }
/* 1274:1376 */         Producto producto = (Producto)((HashMap)hashMapProducto).get(codigoProducto.trim());
/* 1275:1377 */         if (producto == null)
/* 1276:     */         {
/* 1277:1378 */           Map<String, String> filtros = new HashMap();
/* 1278:1379 */           filtros.put("nombre", "=" + nombreProducto);
/* 1279:1380 */           List<Producto> p = this.servicioProducto.obtenerListaCombo("", false, filtros);
/* 1280:1381 */           if (p.isEmpty())
/* 1281:     */           {
/* 1282:1382 */             producto = new Producto();
/* 1283:1383 */             producto.setIdOrganizacion(idOrganizacion);
/* 1284:1384 */             producto.setIdSucursal(idSucursal);
/* 1285:1385 */             producto.setCodigo(codigoProducto);
/* 1286:1386 */             producto.setCodigoAlterno(codigoAlternoProducto);
/* 1287:1387 */             producto.setCodigoBarras(codigoBarraProducto);
/* 1288:1388 */             producto.setNombre(nombreProducto);
/* 1289:1389 */             producto.setNombreComercial(nombreComercialProducto);
/* 1290:1390 */             producto.setCostoEstandar(costoEstadarProducto);
/* 1291:1391 */             producto.setPrecioReferencialVenta(precioReferencialProducto);
/* 1292:1392 */             producto.setTipoCosto(TipoCosto.valueOf(tipoCosteo));
/* 1293:1393 */             producto.setTipoProducto(TipoProducto.valueOf(tipoProducto));
/* 1294:1394 */             producto.setIndicadorCompra(indicadorCompra);
/* 1295:1395 */             producto.setIndicadorImpuestos(indicadorImpuesto);
/* 1296:1396 */             producto.setIndicadorProduccion(indicadorProduccion);
/* 1297:1397 */             producto.setIndicadorConsumoDirecto(indicadorConsumoDirecto);
/* 1298:1398 */             producto.setIndicadorVenta(indicadorVenta);
/* 1299:1399 */             producto.setCantidadMinima(cantidadMinimaProducto);
/* 1300:1400 */             producto.setCantidadMaxima(cantidadMaximaProducto);
/* 1301:1401 */             producto.setPeso(pesoProducto);
/* 1302:1402 */             producto.setVolumen(volumenProducto);
/* 1303:1403 */             producto.setUnidad(unidad);
/* 1304:1404 */             producto.setUnidadAlmacenamiento(unidad);
/* 1305:1405 */             producto.setActivo(true);
/* 1306:1406 */             producto.setDescripcion(descripcion);
/* 1307:1407 */             producto.setSubcategoriaProducto(subcategoriaProducto);
/* 1308:1408 */             producto.setMarcaProducto(marcaProducto);
/* 1309:1409 */             producto.setPresentacionProducto(presentacionProducto);
/* 1310:1410 */             producto.setTipoMaterialEnum(tipoProductoFormulacion);
/* 1311:1411 */             producto.setIceGramosAzucar(new BigDecimal(0));
/* 1312:1412 */             producto.setIndicadorLote(indicadorManejaLote);
/* 1313:1415 */             if (indicadorVenta) {
/* 1314:1416 */               producto.setUnidadVenta(unidad);
/* 1315:     */             }
/* 1316:1418 */             if (indicadorCompra) {
/* 1317:1419 */               producto.setUnidadCompra(unidad);
/* 1318:     */             }
/* 1319:1421 */             if (indicadorImpuesto) {
/* 1320:1422 */               producto.setCategoriaImpuesto(categoriaImpuesto);
/* 1321:     */             } else {
/* 1322:1424 */               producto.setCategoriaImpuesto(null);
/* 1323:     */             }
/* 1324:1426 */             this.servicioProducto.guardar(producto);
/* 1325:1427 */             ((HashMap)hashMapProducto).put(producto.getCodigo(), producto);
/* 1326:     */           }
/* 1327:     */           else
/* 1328:     */           {
/* 1329:1430 */             ((Producto)p.get(0)).setMigracionExisteNombreProducto(Boolean.valueOf(true));
/* 1330:1431 */             listaProducto.add(p.get(0));
/* 1331:     */           }
/* 1332:     */         }
/* 1333:     */         else
/* 1334:     */         {
/* 1335:1436 */           producto.setMigracionExisteNombreProducto(Boolean.valueOf(false));
/* 1336:1437 */           listaProducto.add(producto);
/* 1337:     */         }
/* 1338:     */       }
/* 1339:     */     }
/* 1340:     */     catch (IllegalArgumentException e)
/* 1341:     */     {
/* 1342:1442 */       LOG.info("Error al migrar productos", e);
/* 1343:1443 */       this.context.setRollbackOnly();
/* 1344:     */       
/* 1345:1445 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 1346:     */     }
/* 1347:     */     catch (IllegalStateException e)
/* 1348:     */     {
/* 1349:1448 */       LOG.info("Error al migrar productos", e);
/* 1350:1449 */       this.context.setRollbackOnly();
/* 1351:     */       
/* 1352:1451 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 1353:     */     }
/* 1354:     */     catch (AS2Exception e)
/* 1355:     */     {
/* 1356:1453 */       LOG.info("Error al migrar productos", e);
/* 1357:1454 */       this.context.setRollbackOnly();
/* 1358:1455 */       throw e;
/* 1359:     */     }
/* 1360:     */     catch (ExcepcionAS2 e)
/* 1361:     */     {
/* 1362:1457 */       LOG.info("Error al migrar productos", e);
/* 1363:1458 */       this.context.setRollbackOnly();
/* 1364:1459 */       throw e;
/* 1365:     */     }
/* 1366:     */     catch (Exception e)
/* 1367:     */     {
/* 1368:1461 */       LOG.error("Error al migrar dimensiones contables", e);
/* 1369:1462 */       this.context.setRollbackOnly();
/* 1370:1463 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 1371:     */     }
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public void migracionDimensionesContables(int idOrganizacion, String dimension, String fileName, InputStream imInputStream, int filaInicial, List<SelectItem> listaNivel)
/* 1375:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 1376:     */   {
/* 1377:1471 */     int filaActual = filaInicial;
/* 1378:1472 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 1379:1473 */     int columnaErronea = -1;
/* 1380:     */     try
/* 1381:     */     {
/* 1382:1476 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/* 1383:     */       
/* 1384:     */ 
/* 1385:     */ 
/* 1386:1480 */       Map<String, DimensionContable> hmDimensionContable = new HashMap();
/* 1387:1481 */       Map<Integer, String> hmNivelDimensionContable = new HashMap();
/* 1388:1484 */       for (SelectItem si : listaNivel) {
/* 1389:1485 */         if (!hmNivelDimensionContable.containsKey(si.getLabel())) {
/* 1390:1486 */           hmNivelDimensionContable.put(Integer.valueOf(si.getLabel()), si.getValue().toString());
/* 1391:     */         }
/* 1392:     */       }
/* 1393:1489 */       Object filtros = new HashMap();
/* 1394:1490 */       ((Map)filtros).put("numero", dimension);
/* 1395:1491 */       List<DimensionContable> listaDimensionContable = this.servicioDimensionContable.obtenerListaCombo("", false, (Map)filtros);
/* 1396:1492 */       for (Object localObject1 = listaDimensionContable.iterator(); ((Iterator)localObject1).hasNext();)
/* 1397:     */       {
/* 1398:1492 */         dc = (DimensionContable)((Iterator)localObject1).next();
/* 1399:1493 */         hmDimensionContable.put(dc.getCodigo(), dc);
/* 1400:     */       }
/* 1401:1496 */       localObject1 = datos;DimensionContable dc = localObject1.length;
/* 1402:1496 */       for (DimensionContable localDimensionContable1 = 0; localDimensionContable1 < dc; localDimensionContable1++)
/* 1403:     */       {
/* 1404:1496 */         HSSFCell[] fila = localObject1[localDimensionContable1];
/* 1405:     */         
/* 1406:1498 */         filaErronea = fila;
/* 1407:     */         
/* 1408:1500 */         String codigoDimensionContable = fila[(columnaErronea = 0)].getStringCellValue();
/* 1409:1501 */         String nombreDimensionContable = fila[(columnaErronea = 1)].getStringCellValue();
/* 1410:1502 */         String descripcionDimensionContable = fila[(columnaErronea = 2)] != null ? fila[(columnaErronea = 2)].getStringCellValue() : null;
/* 1411:1503 */         boolean movimiento = fila[(columnaErronea = 3)].getStringCellValue().equals("SI");
/* 1412:1504 */         if (!hmDimensionContable.containsKey(codigoDimensionContable))
/* 1413:     */         {
/* 1414:1505 */           DimensionContable dimensionContable = new DimensionContable();
/* 1415:1506 */           dimensionContable.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1416:1507 */           dimensionContable.setIdSucursal(AppUtil.getSucursal().getId());
/* 1417:1508 */           dimensionContable.setCodigo(codigoDimensionContable);
/* 1418:1509 */           dimensionContable.setNombre(nombreDimensionContable);
/* 1419:1510 */           dimensionContable.setDescripcion(descripcionDimensionContable);
/* 1420:1511 */           dimensionContable.setActivo(true);
/* 1421:1512 */           dimensionContable.setIndicadorMovimiento(movimiento);
/* 1422:1513 */           dimensionContable.setNumero(dimension);
/* 1423:1514 */           String[] nivel = codigoDimensionContable.split("\\.");
/* 1424:1515 */           dimensionContable.setMascara((String)hmNivelDimensionContable.get(Integer.valueOf(nivel.length)));
/* 1425:1516 */           dimensionContable.setTipoAccesoContable(TipoAccesoContable.LIBRE);
/* 1426:1518 */           if (nivel.length == 1)
/* 1427:     */           {
/* 1428:1519 */             dimensionContable.setDimensionPadre(null);
/* 1429:     */           }
/* 1430:     */           else
/* 1431:     */           {
/* 1432:1521 */             String codigoDimensionPadre = codigoDimensionContable.substring(0, codigoDimensionContable.length() - (nivel.length - 1));
/* 1433:1522 */             dimensionContable.setDimensionPadre((DimensionContable)hmDimensionContable.get(codigoDimensionPadre));
/* 1434:     */           }
/* 1435:1524 */           this.servicioDimensionContable.guardar(dimensionContable);
/* 1436:1525 */           hmDimensionContable.put(codigoDimensionContable, dimensionContable);
/* 1437:     */         }
/* 1438:1528 */         filaActual++;
/* 1439:     */       }
/* 1440:     */     }
/* 1441:     */     catch (IllegalArgumentException e)
/* 1442:     */     {
/* 1443:1532 */       LOG.info("Error al migrar dimensiones contables", e);
/* 1444:1533 */       this.context.setRollbackOnly();
/* 1445:     */       
/* 1446:1535 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 1447:     */     }
/* 1448:     */     catch (ExcepcionAS2Financiero e)
/* 1449:     */     {
/* 1450:1538 */       LOG.info("Error al migrar dimensiones contables", e);
/* 1451:1539 */       this.context.setRollbackOnly();
/* 1452:     */       
/* 1453:1541 */       throw new ExcepcionAS2Financiero(e.getCodigoExcepcion(), "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 1454:     */     }
/* 1455:     */     catch (IllegalStateException e)
/* 1456:     */     {
/* 1457:1544 */       LOG.info("Error al migrar dimensiones contables", e);
/* 1458:1545 */       this.context.setRollbackOnly();
/* 1459:     */       
/* 1460:1547 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 1461:     */     }
/* 1462:     */     catch (Exception e)
/* 1463:     */     {
/* 1464:1549 */       LOG.error("Error al migrar dimensiones contables", e);
/* 1465:1550 */       this.context.setRollbackOnly();
/* 1466:1551 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 1467:     */     }
/* 1468:     */   }
/* 1469:     */   
/* 1470:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 1471:     */   public void migracionEmpleado(InputStream imInputStream, int filaInicial)
/* 1472:     */     throws ExcepcionAS2
/* 1473:     */   {
/* 1474:1568 */     HashMap<String, Empresa> hashMapEmpresa = new HashMap();
/* 1475:1569 */     HashMap<String, Empresa> hashMapEmpresa2 = new HashMap();
/* 1476:1570 */     HashMap<String, String> filtros = new HashMap();
/* 1477:1571 */     filtros.put("notSetMaxResults", "true");
/* 1478:1572 */     for (Iterator localIterator1 = this.servicioEmpresa.obtenerListaCombo("", false, filtros).iterator(); localIterator1.hasNext();)
/* 1479:     */     {
/* 1480:1572 */       e = (Empresa)localIterator1.next();
/* 1481:1573 */       if (e.getEmpleado() != null) {
/* 1482:1574 */         e.getEmpleado().getId();
/* 1483:     */       }
/* 1484:1576 */       hashMapEmpresa.put(e.getCodigo().trim(), e);
/* 1485:1577 */       hashMapEmpresa2.put(e.getIdentificacion().trim(), e);
/* 1486:     */     }
/* 1487:1582 */     Object hashSucursal = new HashMap();
/* 1488:1583 */     for (Empresa e = this.servicioSucursal.obtenerListaCombo("", false, null).iterator(); e.hasNext();)
/* 1489:     */     {
/* 1490:1583 */       s = (Sucursal)e.next();
/* 1491:1584 */       ((HashMap)hashSucursal).put(s.getCodigo().trim(), s);
/* 1492:     */     }
/* 1493:1588 */     HashMap<String, TipoIdentificacion> hashTipoIdentificacion = new HashMap();
/* 1494:1589 */     for (Sucursal s = this.servicioTipoIdentificacion.obtenerListaCombo("", false, null).iterator(); s.hasNext();)
/* 1495:     */     {
/* 1496:1589 */       ti = (TipoIdentificacion)s.next();
/* 1497:1590 */       hashTipoIdentificacion.put(ti.getCodigo().trim(), ti);
/* 1498:     */     }
/* 1499:1594 */     HashMap<String, Banco> hashMapBanco = new HashMap();
/* 1500:1595 */     for (TipoIdentificacion ti = this.servicioBanco.obtenerListaCombo(Banco.class, "", false, null).iterator(); ti.hasNext();)
/* 1501:     */     {
/* 1502:1595 */       b = (Banco)ti.next();
/* 1503:1596 */       hashMapBanco.put(b.getCodigo().trim(), b);
/* 1504:     */     }
/* 1505:1600 */     HashMap<String, EstadoCivil> hashMapEstadoCivil = new HashMap();
/* 1506:1601 */     for (Banco b = this.servicioEstadoCivil.obtenerListaCombo("", true, null).iterator(); b.hasNext();)
/* 1507:     */     {
/* 1508:1601 */       ec = (EstadoCivil)b.next();
/* 1509:1602 */       hashMapEstadoCivil.put(ec.getCodigo().trim(), ec);
/* 1510:     */     }
/* 1511:1606 */     HashMap<String, TipoCuentaBancaria> hashMapTipoCuentaBancaria = new HashMap();
/* 1512:1607 */     for (EstadoCivil ec = this.servicioTipoCuentaBancaria.obtenerListaCombo("", false, null).iterator(); ec.hasNext();)
/* 1513:     */     {
/* 1514:1607 */       tcb = (TipoCuentaBancaria)ec.next();
/* 1515:1608 */       hashMapTipoCuentaBancaria.put(tcb.getCodigo().trim(), tcb);
/* 1516:     */     }
/* 1517:1612 */     HashMap<String, Departamento> hashMapDepartamento = new HashMap();
/* 1518:1613 */     for (TipoCuentaBancaria tcb = this.servicioDepartamento.obtenerListaCombo("", false, null).iterator(); tcb.hasNext();)
/* 1519:     */     {
/* 1520:1613 */       d = (Departamento)tcb.next();
/* 1521:1614 */       hashMapDepartamento.put(d.getCodigo().trim(), d);
/* 1522:     */     }
/* 1523:1618 */     HashMap<String, CargoEmpleado> hashMapCargoEmpleado = new HashMap();
/* 1524:1619 */     for (Departamento d = this.servicioCargoEmpleado.obtenerListaCombo("", false, null).iterator(); d.hasNext();)
/* 1525:     */     {
/* 1526:1619 */       d = (CargoEmpleado)d.next();
/* 1527:1620 */       hashMapCargoEmpleado.put(d.getCodigo().trim(), d);
/* 1528:     */     }
/* 1529:1624 */     HashMap<String, TipoDiscapacidad> hashMapTipoDiscapacidad = new HashMap();
/* 1530:1625 */     for (CargoEmpleado d = this.servicioTipoDiscapacidad.obtenerListaCombo("", false, null).iterator(); d.hasNext();)
/* 1531:     */     {
/* 1532:1625 */       td = (TipoDiscapacidad)d.next();
/* 1533:1626 */       hashMapTipoDiscapacidad.put(td.getCodigo().trim(), td);
/* 1534:     */     }
/* 1535:1630 */     HashMap<String, Titulo> hashMapTitulo = new HashMap();
/* 1536:1631 */     for (TipoDiscapacidad td = this.servicioTitulo.obtenerListaCombo("", false, null).iterator(); td.hasNext();)
/* 1537:     */     {
/* 1538:1631 */       t = (Titulo)td.next();
/* 1539:1632 */       hashMapTitulo.put(t.getCodigo().trim(), t);
/* 1540:     */     }
/* 1541:1636 */     HashMap<String, TipoContrato> hashMapTipoContrato = new HashMap();
/* 1542:1637 */     for (Titulo t = this.servicioTipoContrato.obtenerListaCombo("", false, null).iterator(); t.hasNext();)
/* 1543:     */     {
/* 1544:1637 */       t = (TipoContrato)t.next();
/* 1545:1638 */       hashMapTipoContrato.put(t.getCodigo().trim(), t);
/* 1546:     */     }
/* 1547:     */     TipoContrato t;
/* 1548:1642 */     HashMap<String, Pais> hashMapPais = new HashMap();
/* 1549:1643 */     for (Pais p : this.servicioPais.obtenerListaCombo("", false, null)) {
/* 1550:1644 */       hashMapPais.put(p.getCodigoIso().trim(), p);
/* 1551:     */     }
/* 1552:1648 */     HashMap<String, Provincia> hashMapProvincia = new HashMap();
/* 1553:1649 */     List<Provincia> listaProvincia = this.servicioProvincia.obtenerListaCombo("", false, null);
/* 1554:1650 */     for (Provincia ci : listaProvincia) {
/* 1555:1651 */       hashMapProvincia.put(ci.getCodigo().trim(), ci);
/* 1556:     */     }
/* 1557:1655 */     Object hashMapCiudad = new HashMap();
/* 1558:1656 */     List<Ciudad> listaCiudad = this.servicioCiudad.obtenerListaCombo("", false, null);
/* 1559:1657 */     for (Ciudad ci : listaCiudad) {
/* 1560:1658 */       ((HashMap)hashMapCiudad).put(ci.getProvincia().getCodigo().trim() + "~" + ci.getCodigo().trim(), ci);
/* 1561:     */     }
/* 1562:1662 */     Object hashMapCentroCosto = new HashMap();
/* 1563:1663 */     HashMap<String, String> filtroDimension = new HashMap();
/* 1564:1664 */     filtroDimension.put("numero", "1");
/* 1565:1665 */     filtroDimension.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 1566:1666 */     for (DimensionContable dc : this.servicioDimensionContable.obtenerListaCombo("", false, filtroDimension)) {
/* 1567:1667 */       ((HashMap)hashMapCentroCosto).put(dc.getCodigo().trim(), dc);
/* 1568:     */     }
/* 1569:1671 */     CategoriaEmpresa categoriaEmpresa = null;
/* 1570:     */     
/* 1571:1673 */     HashMap<String, String> filters = new HashMap();
/* 1572:1674 */     filters.put("nombre", "Categoria Empleado Asistencial");
/* 1573:1675 */     List<CategoriaEmpresa> listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo(null, true, filters);
/* 1574:1677 */     if (!listaCategoriaEmpresa.isEmpty()) {
/* 1575:1678 */       categoriaEmpresa = (CategoriaEmpresa)this.servicioCategoriaEmpresa.obtenerListaCombo(null, true, filters).get(0);
/* 1576:     */     }
/* 1577:1681 */     if (categoriaEmpresa == null)
/* 1578:     */     {
/* 1579:1682 */       categoriaEmpresa = new CategoriaEmpresa();
/* 1580:1683 */       categoriaEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1581:1684 */       categoriaEmpresa.setIdSucursal(AppUtil.getSucursal().getId());
/* 1582:1685 */       categoriaEmpresa.setActivo(true);
/* 1583:1686 */       categoriaEmpresa.setNombre("Categoria Empleado Asistencial");
/* 1584:1687 */       categoriaEmpresa.setDescripcion("");
/* 1585:1688 */       categoriaEmpresa.setCodigo("CE-001");
/* 1586:1689 */       categoriaEmpresa.setPredeterminado(false);
/* 1587:1690 */       this.servicioCategoriaEmpresa.guardar(categoriaEmpresa);
/* 1588:     */     }
/* 1589:1693 */     int filaActual = filaInicial;
/* 1590:1694 */     int columnaActual = 0;
/* 1591:1695 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 1592:     */     try
/* 1593:     */     {
/* 1594:1698 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 1595:1700 */       for (HSSFCell[] fila : datos)
/* 1596:     */       {
/* 1597:1702 */         filaErronea = fila;
/* 1598:1703 */         filaActual++;
/* 1599:1706 */         if (fila[0] != null) {
/* 1600:1707 */           fila[0].setCellType(1);
/* 1601:     */         }
/* 1602:1708 */         String codigoEmpresa = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 0, true, Integer.valueOf(1), Integer.valueOf(20));
/* 1603:1710 */         if (fila[37] != null) {
/* 1604:1711 */           fila[37].setCellType(1);
/* 1605:     */         }
/* 1606:1712 */         String codigoSucursal = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 37, true, Integer.valueOf(1), Integer.valueOf(50));
/* 1607:1713 */         Sucursal sucursal = (Sucursal)((HashMap)hashSucursal).get(codigoSucursal);
/* 1608:1714 */         if (sucursal == null) {
/* 1609:1716 */           throw new ExcepcionAS2("msg_error_sucursal_no_registrada", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 1610:     */         }
/* 1611:1720 */         String apellidos = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 1, true, Integer.valueOf(1), Integer.valueOf(100));
/* 1612:     */         
/* 1613:1722 */         String nombres = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 2, true, Integer.valueOf(1), Integer.valueOf(100));
/* 1614:1724 */         if (fila[3] != null) {
/* 1615:1725 */           fila[3].setCellType(1);
/* 1616:     */         }
/* 1617:1726 */         String codigoTipoIdentificacion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 3, true, 
/* 1618:1727 */           Integer.valueOf(1), Integer.valueOf(10));
/* 1619:1728 */         TipoIdentificacion tipoIdentificacion = (TipoIdentificacion)hashTipoIdentificacion.get(codigoTipoIdentificacion);
/* 1620:1729 */         if (tipoIdentificacion == null) {
/* 1621:1731 */           throw new ExcepcionAS2("msg_error_no_existe_tipo_identificacion", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 1622:     */         }
/* 1623:1734 */         if (fila[4] != null) {
/* 1624:1735 */           fila[4].setCellType(1);
/* 1625:     */         }
/* 1626:1736 */         String identificacion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 4, true, Integer.valueOf(2), Integer.valueOf(20));
/* 1627:1738 */         if (fila[36] != null) {
/* 1628:1739 */           fila[36].setCellType(1);
/* 1629:     */         }
/* 1630:1740 */         String codigoSectorial = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 36, true, Integer.valueOf(2), Integer.valueOf(20));
/* 1631:     */         
/* 1632:1742 */         Date fechaNacimiento = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 5, true, Integer.valueOf(0), Integer.valueOf(0));
/* 1633:     */         
/* 1634:1744 */         String lugarNacimiento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 38, true, Integer.valueOf(5), Integer.valueOf(70));
/* 1635:     */         
/* 1636:1746 */         String nombreGenero = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 6, true, Integer.valueOf(0), Integer.valueOf(10));
/* 1637:1747 */         Genero genero = nombreGenero.equalsIgnoreCase(Genero.MASCULINO.toString()) ? Genero.MASCULINO : Genero.FEMENINO;
/* 1638:1749 */         if (fila[7] != null) {
/* 1639:1750 */           fila[7].setCellType(1);
/* 1640:     */         }
/* 1641:1751 */         String codigoEstadoCivil = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 7, true, Integer.valueOf(2), 
/* 1642:1752 */           Integer.valueOf(10));
/* 1643:1753 */         EstadoCivil estadoCivil = (EstadoCivil)hashMapEstadoCivil.get(codigoEstadoCivil);
/* 1644:1754 */         if (estadoCivil == null) {
/* 1645:1756 */           throw new ExcepcionAS2("msg_error_estado_civil_no_registrado", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 1646:     */         }
/* 1647:1759 */         if (fila[8] != null) {
/* 1648:1760 */           fila[8].setCellType(1);
/* 1649:     */         }
/* 1650:1761 */         String codigoTitulo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 8, true, Integer.valueOf(2), Integer.valueOf(10));
/* 1651:     */         
/* 1652:1763 */         String nombreTitulo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 9, true, Integer.valueOf(2), Integer.valueOf(50));
/* 1653:1764 */         Titulo titulo = (Titulo)hashMapTitulo.get(codigoTitulo);
/* 1654:1765 */         if (titulo == null)
/* 1655:     */         {
/* 1656:1766 */           titulo = new Titulo();
/* 1657:1767 */           titulo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1658:1768 */           titulo.setIdSucursal(AppUtil.getSucursal().getId());
/* 1659:1769 */           titulo.setCodigo(codigoTitulo);
/* 1660:1770 */           titulo.setNombre(nombreTitulo);
/* 1661:1771 */           titulo.setActivo(true);
/* 1662:1772 */           this.servicioTitulo.guardar(titulo);
/* 1663:1773 */           hashMapTitulo.put(codigoTitulo, titulo);
/* 1664:     */         }
/* 1665:1776 */         if (fila[10] != null) {
/* 1666:1777 */           fila[10].setCellType(1);
/* 1667:     */         }
/* 1668:1778 */         String codigoCargo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 10, true, Integer.valueOf(2), Integer.valueOf(10));
/* 1669:     */         
/* 1670:1780 */         String nombreCargo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 11, true, Integer.valueOf(2), Integer.valueOf(50));
/* 1671:1781 */         CargoEmpleado cargoEmpleado = (CargoEmpleado)hashMapCargoEmpleado.get(codigoCargo);
/* 1672:1782 */         if (cargoEmpleado == null)
/* 1673:     */         {
/* 1674:1783 */           cargoEmpleado = new CargoEmpleado();
/* 1675:1784 */           cargoEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1676:1785 */           cargoEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/* 1677:1786 */           cargoEmpleado.setCodigo(codigoCargo);
/* 1678:1787 */           cargoEmpleado.setNombre(nombreCargo);
/* 1679:1788 */           cargoEmpleado.setActivo(true);
/* 1680:1789 */           this.servicioCargoEmpleado.guardar(cargoEmpleado);
/* 1681:1790 */           hashMapCargoEmpleado.put(codigoCargo, cargoEmpleado);
/* 1682:     */         }
/* 1683:1793 */         if (fila[12] != null) {
/* 1684:1794 */           fila[12].setCellType(1);
/* 1685:     */         }
/* 1686:1795 */         String codigoDepartamento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 12, true, Integer.valueOf(2), 
/* 1687:1796 */           Integer.valueOf(10));
/* 1688:     */         
/* 1689:1798 */         String nombreDepartamento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 13, true, Integer.valueOf(2), 
/* 1690:1799 */           Integer.valueOf(50));
/* 1691:1800 */         Departamento departamento = (Departamento)hashMapDepartamento.get(codigoDepartamento);
/* 1692:1801 */         if (departamento == null)
/* 1693:     */         {
/* 1694:1802 */           departamento = new Departamento();
/* 1695:1803 */           departamento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1696:1804 */           departamento.setIdSucursal(AppUtil.getSucursal().getId());
/* 1697:1805 */           departamento.setCodigo(codigoDepartamento);
/* 1698:1806 */           departamento.setNombre(nombreDepartamento);
/* 1699:1807 */           departamento.setActivo(true);
/* 1700:1808 */           departamento.setTipoDepartamento(TipoDepartamento.CONTABILIDAD);
/* 1701:1809 */           this.servicioDepartamento.guardar(departamento);
/* 1702:1810 */           hashMapDepartamento.put(codigoDepartamento, departamento);
/* 1703:     */         }
/* 1704:1812 */         if (fila[14] != null) {
/* 1705:1813 */           fila[14].setCellType(1);
/* 1706:     */         }
/* 1707:1814 */         String codigoTipoDiscapacidad = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 14, false, 
/* 1708:1815 */           Integer.valueOf(2), Integer.valueOf(10));
/* 1709:     */         
/* 1710:1817 */         String nombreTipoDiscapacidad = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 15, false, 
/* 1711:1818 */           Integer.valueOf(2), Integer.valueOf(50));
/* 1712:     */         
/* 1713:1820 */         BigDecimal porcentajeTipoDiscapacidad = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 16, false, 
/* 1714:1821 */           Integer.valueOf(0), Integer.valueOf(0));
/* 1715:     */         
/* 1716:1823 */         TipoDiscapacidad tipoDiscapacidad = null;
/* 1717:1824 */         if ((codigoTipoDiscapacidad != null) && (!codigoTipoDiscapacidad.isEmpty()))
/* 1718:     */         {
/* 1719:1825 */           tipoDiscapacidad = (TipoDiscapacidad)hashMapTipoDiscapacidad.get(codigoTipoDiscapacidad);
/* 1720:1826 */           if (tipoDiscapacidad == null)
/* 1721:     */           {
/* 1722:1827 */             tipoDiscapacidad = new TipoDiscapacidad();
/* 1723:1828 */             tipoDiscapacidad.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1724:1829 */             tipoDiscapacidad.setIdSucursal(AppUtil.getSucursal().getId());
/* 1725:1830 */             tipoDiscapacidad.setCodigo(codigoTipoDiscapacidad);
/* 1726:1831 */             tipoDiscapacidad.setNombre(nombreTipoDiscapacidad);
/* 1727:1832 */             tipoDiscapacidad.setDescripcion("Enfermedad Catastrofica");
/* 1728:1833 */             tipoDiscapacidad.setActivo(true);
/* 1729:1834 */             this.servicioTipoDiscapacidad.guardar(tipoDiscapacidad);
/* 1730:1835 */             hashMapTipoDiscapacidad.put(codigoTipoDiscapacidad, tipoDiscapacidad);
/* 1731:     */           }
/* 1732:     */         }
/* 1733:1840 */         if (fila[17] != null) {
/* 1734:1841 */           fila[17].setCellType(1);
/* 1735:     */         }
/* 1736:1842 */         String codigoTipoContrato = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 17, false, Integer.valueOf(2), 
/* 1737:1843 */           Integer.valueOf(10));
/* 1738:     */         
/* 1739:1845 */         String nombreTipoContrato = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 18, false, Integer.valueOf(2), 
/* 1740:1846 */           Integer.valueOf(50));
/* 1741:     */         
/* 1742:     */ 
/* 1743:1849 */         Secuencia secuencia = null;
/* 1744:1850 */         HashMap<String, String> filters1 = new HashMap();
/* 1745:1851 */         filters1.put("nombre", "Tipo Contrato Empleado");
/* 1746:1852 */         filters1.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 1747:     */         
/* 1748:1854 */         List<Secuencia> listaSecuencia = this.servicioSecuencia.obtenerListaCombo(null, true, filters1);
/* 1749:1856 */         if (!listaSecuencia.isEmpty())
/* 1750:     */         {
/* 1751:1857 */           secuencia = (Secuencia)listaSecuencia.get(0);
/* 1752:1858 */           if (secuencia.getPrefijo().equals(""))
/* 1753:     */           {
/* 1754:1859 */             secuencia.setPrefijo(codigoTipoContrato);
/* 1755:1860 */             secuencia.setSufijo("");
/* 1756:     */           }
/* 1757:     */         }
/* 1758:1864 */         if (secuencia == null)
/* 1759:     */         {
/* 1760:1865 */           secuencia = new Secuencia();
/* 1761:1866 */           secuencia.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1762:1867 */           secuencia.setIdSucursal(AppUtil.getSucursal().getId());
/* 1763:1868 */           secuencia.setNombre("Tipo Contrato Empleado");
/* 1764:1869 */           secuencia.setPrefijo(codigoTipoContrato);
/* 1765:1870 */           secuencia.setSufijo("");
/* 1766:1871 */           secuencia.setLongitud(6);
/* 1767:1872 */           secuencia.setDesde(1);
/* 1768:1873 */           secuencia.setHasta(9999);
/* 1769:1874 */           secuencia.setFechaDesde(FuncionesUtiles.getFecha(1, 1, 1900));
/* 1770:1875 */           secuencia.setFechaHasta(FuncionesUtiles.getFecha(31, 12, 9999));
/* 1771:1876 */           secuencia.setNumero(1);
/* 1772:1877 */           this.servicioSecuencia.guardar(secuencia);
/* 1773:     */         }
/* 1774:1880 */         TipoContrato tipoContrato = (TipoContrato)hashMapTipoContrato.get(codigoTipoContrato);
/* 1775:1881 */         if (tipoContrato == null)
/* 1776:     */         {
/* 1777:1882 */           tipoContrato = new TipoContrato();
/* 1778:1883 */           tipoContrato.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1779:1884 */           tipoContrato.setIdSucursal(AppUtil.getSucursal().getId());
/* 1780:1885 */           tipoContrato.setCodigo(codigoTipoContrato);
/* 1781:1886 */           tipoContrato.setNombre(nombreTipoContrato);
/* 1782:1887 */           tipoContrato.setActivo(true);
/* 1783:1888 */           tipoContrato.setSecuencia(secuencia);
/* 1784:1889 */           this.servicioTipoContrato.guardar(tipoContrato);
/* 1785:1890 */           hashMapTipoContrato.put(codigoTipoContrato, tipoContrato);
/* 1786:     */         }
/* 1787:1894 */         Date fechaIngreso = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 19, true, Integer.valueOf(0), Integer.valueOf(0));
/* 1788:     */         
/* 1789:1896 */         Date fechaSalida = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 20, false, Integer.valueOf(0), Integer.valueOf(0));
/* 1790:     */         
/* 1791:1898 */         String nombreFormaPago = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 21, true, Integer.valueOf(0), Integer.valueOf(10));
/* 1792:     */         
/* 1793:1900 */         Boolean pagoCash = Boolean.valueOf(fila[(columnaActual = 41)].getStringCellValue().equals("SI"));
/* 1794:     */         
/* 1795:1902 */         FormaPagoEmpleado formaPagoEmpleado = nombreFormaPago.equalsIgnoreCase(FormaPagoEmpleado.ROL.toString()) ? FormaPagoEmpleado.ROL : FormaPagoEmpleado.FACTURA;
/* 1796:1905 */         if (fila[22] != null) {
/* 1797:1906 */           fila[22].setCellType(1);
/* 1798:     */         }
/* 1799:1907 */         String codigoBanco = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 22, true, Integer.valueOf(2), Integer.valueOf(10));
/* 1800:1908 */         Banco banco = (Banco)hashMapBanco.get(codigoBanco);
/* 1801:1909 */         if (banco == null) {
/* 1802:1911 */           throw new ExcepcionAS2("msg_error_banco_no_registrado", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 1803:     */         }
/* 1804:1914 */         if (fila[23] != null) {
/* 1805:1915 */           fila[23].setCellType(1);
/* 1806:     */         }
/* 1807:1916 */         String codigoTipoCuenta = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 23, true, Integer.valueOf(2), 
/* 1808:1917 */           Integer.valueOf(10));
/* 1809:1918 */         TipoCuentaBancaria tipoCuentaBancaria = (TipoCuentaBancaria)hashMapTipoCuentaBancaria.get(codigoTipoCuenta);
/* 1810:1919 */         if (tipoCuentaBancaria == null) {
/* 1811:1921 */           throw new ExcepcionAS2("msg_error_tipo_cuenta_bancaria_no_registrada", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 1812:     */         }
/* 1813:1924 */         if (fila[24] != null) {
/* 1814:1925 */           fila[24].setCellType(1);
/* 1815:     */         }
/* 1816:1926 */         String numeroCuentaBanco = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 24, true, Integer.valueOf(6), 
/* 1817:1927 */           Integer.valueOf(20));
/* 1818:     */         
/* 1819:1929 */         BigDecimal sueldo = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 25, true, Integer.valueOf(0), Integer.valueOf(0));
/* 1820:1932 */         if (fila[26] != null) {
/* 1821:1933 */           fila[26].setCellType(1);
/* 1822:     */         }
/* 1823:1934 */         String centroCosto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 26, true, Integer.valueOf(1), Integer.valueOf(20));
/* 1824:1935 */         DimensionContable dimensionContable = (DimensionContable)((HashMap)hashMapCentroCosto).get(centroCosto);
/* 1825:1936 */         if (dimensionContable == null) {
/* 1826:1938 */           throw new ExcepcionAS2("msg_error_dimension_contable_no_registrada", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 1827:     */         }
/* 1828:1941 */         if (fila[27] != null) {
/* 1829:1942 */           fila[27].setCellType(1);
/* 1830:     */         }
/* 1831:1943 */         String codigoPais = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 27, true, Integer.valueOf(2), Integer.valueOf(10));
/* 1832:1944 */         Pais pais = (Pais)hashMapPais.get(codigoPais);
/* 1833:1945 */         if (pais == null) {
/* 1834:1947 */           throw new ExcepcionAS2("msg_error_pais_no_registrado", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 1835:     */         }
/* 1836:1950 */         if (fila[28] != null) {
/* 1837:1951 */           fila[28].setCellType(1);
/* 1838:     */         }
/* 1839:1952 */         String codigoProvincia = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 28, true, Integer.valueOf(2), Integer.valueOf(10));
/* 1840:1953 */         Provincia provincia = (Provincia)hashMapProvincia.get(codigoProvincia);
/* 1841:1954 */         if (provincia == null) {
/* 1842:1956 */           throw new ExcepcionAS2("msg_error_provincia_no_registrada", "\nFila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 1843:     */         }
/* 1844:1959 */         if (fila[29] != null) {
/* 1845:1960 */           fila[29].setCellType(1);
/* 1846:     */         }
/* 1847:1961 */         String codigoCiudad = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 29, true, Integer.valueOf(2), Integer.valueOf(10));
/* 1848:1962 */         Ciudad ciudad = (Ciudad)((HashMap)hashMapCiudad).get(codigoProvincia + "~" + codigoCiudad);
/* 1849:1963 */         if (ciudad == null) {
/* 1850:1965 */           throw new ExcepcionAS2("msg_error_ciudad_no_registrada", "Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString() + "\nPara la provincia de codigo: " + codigoProvincia);
/* 1851:     */         }
/* 1852:1968 */         String direccion1 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 30, true, Integer.valueOf(2), Integer.valueOf(50));
/* 1853:     */         
/* 1854:1970 */         String direccion2 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 31, false, Integer.valueOf(2), Integer.valueOf(50));
/* 1855:1971 */         if (direccion2 == null) {
/* 1856:1972 */           direccion2 = "";
/* 1857:     */         }
/* 1858:1975 */         String direccion3 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 32, false, Integer.valueOf(2), Integer.valueOf(50));
/* 1859:     */         
/* 1860:1977 */         String direccion4 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 42, false, Integer.valueOf(0), Integer.valueOf(50));
/* 1861:     */         
/* 1862:1979 */         String direccion5 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 43, false, Integer.valueOf(0), Integer.valueOf(50));
/* 1863:1981 */         if (fila[33] != null) {
/* 1864:1982 */           fila[33].setCellType(1);
/* 1865:     */         }
/* 1866:1983 */         String telefono1 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 33, false, Integer.valueOf(0), Integer.valueOf(13));
/* 1867:1985 */         if (fila[34] != null) {
/* 1868:1986 */           fila[34].setCellType(1);
/* 1869:     */         }
/* 1870:1987 */         String telefono2 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 34, false, Integer.valueOf(0), Integer.valueOf(13));
/* 1871:     */         
/* 1872:1989 */         String email1 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 35, false, Integer.valueOf(0), Integer.valueOf(50));
/* 1873:     */         
/* 1874:1991 */         String email2 = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 39, false, Integer.valueOf(0), Integer.valueOf(50));
/* 1875:     */         
/* 1876:1993 */         String paginaWeb = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 40, false, Integer.valueOf(0), Integer.valueOf(50));
/* 1877:     */         
/* 1878:1995 */         Empresa empresa = null;
/* 1879:1996 */         if ((!hashMapEmpresa.containsKey(codigoEmpresa)) && (!hashMapEmpresa2.containsKey(identificacion)))
/* 1880:     */         {
/* 1881:1998 */           empresa = new Empresa();
/* 1882:1999 */           empresa.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1883:2000 */           empresa.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 1884:     */           
/* 1885:2002 */           Ubicacion ubicacion = new Ubicacion();
/* 1886:2003 */           ubicacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1887:2004 */           ubicacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 1888:2005 */           ubicacion.setDireccion1(direccion1);
/* 1889:2006 */           ubicacion.setDireccion2(direccion2);
/* 1890:2007 */           ubicacion.setDireccion3(direccion3);
/* 1891:2008 */           ubicacion.setDireccion4(direccion4);
/* 1892:2009 */           ubicacion.setDireccion5(direccion5);
/* 1893:     */           
/* 1894:2011 */           DireccionEmpresa direccionEmpresa = new DireccionEmpresa();
/* 1895:2012 */           direccionEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1896:2013 */           direccionEmpresa.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 1897:2014 */           direccionEmpresa.setUbicacion(ubicacion);
/* 1898:2015 */           direccionEmpresa.setTelefono1(telefono1);
/* 1899:2016 */           direccionEmpresa.setTelefono2(telefono2);
/* 1900:2017 */           direccionEmpresa.setEmpresa(empresa);
/* 1901:2018 */           direccionEmpresa.setCiudad(ciudad);
/* 1902:2019 */           direccionEmpresa.setIndicadorDireccionPrincipal(true);
/* 1903:2020 */           List<DireccionEmpresa> direcciones = new ArrayList();
/* 1904:2021 */           direcciones.add(direccionEmpresa);
/* 1905:     */           
/* 1906:2023 */           CuentaBancaria cuentaBancaria = new CuentaBancaria();
/* 1907:2024 */           cuentaBancaria.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1908:2025 */           cuentaBancaria.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 1909:2026 */           cuentaBancaria.setBanco(banco);
/* 1910:2027 */           cuentaBancaria.setTipoCuentaBancaria(tipoCuentaBancaria);
/* 1911:2028 */           cuentaBancaria.setNumero(numeroCuentaBanco);
/* 1912:2029 */           cuentaBancaria.setContacto("N/A");
/* 1913:2030 */           cuentaBancaria.setTelefonoContacto("999999999");
/* 1914:2031 */           cuentaBancaria.setActivo(true);
/* 1915:2032 */           cuentaBancaria.setPais(pais);
/* 1916:     */           
/* 1917:2034 */           CuentaBancariaEmpresa cuentaBancariaEmpresa = new CuentaBancariaEmpresa();
/* 1918:2035 */           cuentaBancariaEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1919:2036 */           cuentaBancariaEmpresa.setIdSucursal(Integer.valueOf(AppUtil.getSucursal().getIdSucursal()));
/* 1920:2037 */           cuentaBancariaEmpresa.setDescripcion("N/A");
/* 1921:2038 */           cuentaBancariaEmpresa.setActivo(true);
/* 1922:2039 */           cuentaBancariaEmpresa.setCuentaBancaria(cuentaBancaria);
/* 1923:2040 */           cuentaBancariaEmpresa.setEmpresa(empresa);
/* 1924:2041 */           List<CuentaBancariaEmpresa> listaCuentaBancariaEmpresa = new ArrayList();
/* 1925:2042 */           listaCuentaBancariaEmpresa.add(cuentaBancariaEmpresa);
/* 1926:     */           
/* 1927:2044 */           empresa.setCodigo(codigoEmpresa);
/* 1928:2045 */           empresa.setTipoIdentificacion(tipoIdentificacion);
/* 1929:2046 */           empresa.setIdentificacion(identificacion);
/* 1930:2047 */           empresa.setNombreComercial(nombres);
/* 1931:2048 */           empresa.setNombreFiscal(apellidos);
/* 1932:2049 */           empresa.setEmail1(email1);
/* 1933:2050 */           empresa.setEmail2(email2);
/* 1934:2051 */           empresa.setPaginaWeb(paginaWeb);
/* 1935:2052 */           empresa.setTipoEmpresa(TipoEmpresa.NATURAL);
/* 1936:2053 */           empresa.setDirecciones(direcciones);
/* 1937:2054 */           empresa.setListaCuentaBancariaEmpresa(listaCuentaBancariaEmpresa);
/* 1938:2055 */           empresa.setCategoriaEmpresa(categoriaEmpresa);
/* 1939:2056 */           empresa.setActivo(true);
/* 1940:2057 */           empresa.setIndicadorEmpleado(true);
/* 1941:     */         }
/* 1942:     */         else
/* 1943:     */         {
/* 1944:2060 */           empresa = (Empresa)hashMapEmpresa.get(identificacion);
/* 1945:2061 */           empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 1946:2062 */           if (empresa.getEmpleado() != null) {
/* 1947:2063 */             throw new ExcepcionAS2Nomina("msg_info_existe_empleado", empresa.getIdentificacion());
/* 1948:     */           }
/* 1949:     */         }
/* 1950:2067 */         Empleado empleado = new Empleado();
/* 1951:2068 */         empleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1952:2069 */         empleado.setSucursal(AppUtil.getSucursal());
/* 1953:2070 */         empleado.setNombres(nombres);
/* 1954:2071 */         empleado.setApellidos(apellidos);
/* 1955:2072 */         empleado.setCodigoSectorial(codigoSectorial);
/* 1956:2073 */         empleado.setEstadoCivil(estadoCivil);
/* 1957:2074 */         empleado.setDepartamento(departamento);
/* 1958:2075 */         empleado.setCargoEmpleado(cargoEmpleado);
/* 1959:2076 */         empleado.setTitulo(titulo);
/* 1960:2077 */         empleado.setGenero(genero);
/* 1961:2078 */         empleado.setFormaPagoEmpleado(formaPagoEmpleado);
/* 1962:2079 */         empleado.setTipoContrato(tipoContrato);
/* 1963:2080 */         empleado.setFechaNacimiento(fechaNacimiento);
/* 1964:2081 */         empleado.setLugarNacimiento(lugarNacimiento);
/* 1965:2082 */         empleado.setPais(pais);
/* 1966:2083 */         empleado.setActivo(true);
/* 1967:2084 */         empleado.setIndicadorPagoCash(pagoCash.booleanValue());
/* 1968:2085 */         empleado.setResidenciaTrabajador("01");
/* 1969:2087 */         if (tipoDiscapacidad != null)
/* 1970:     */         {
/* 1971:2088 */           empleado.setCondicionRespectoDiscapacidad("02");
/* 1972:2089 */           empleado.setTipoIdentificacionSustitutoPariente(" ");
/* 1973:     */         }
/* 1974:     */         else
/* 1975:     */         {
/* 1976:2091 */           empleado.setCondicionRespectoDiscapacidad("01");
/* 1977:     */         }
/* 1978:2093 */         empleado.setTipoDiscapacidad(tipoDiscapacidad);
/* 1979:2094 */         empleado.setPorcentajeDiscapacidad(porcentajeTipoDiscapacidad);
/* 1980:     */         
/* 1981:     */ 
/* 1982:2097 */         RubroEmpleado rubroEmpleado = new RubroEmpleado();
/* 1983:2098 */         Integer idRubro = ParametrosSistema.getRubroSalarioUnificado(AppUtil.getOrganizacion().getId());
/* 1984:2099 */         if (idRubro != null)
/* 1985:     */         {
/* 1986:2101 */           Rubro rubro = this.servicioRubro.buscarPorId(idRubro.intValue());
/* 1987:2103 */           if (rubro != null)
/* 1988:     */           {
/* 1989:2105 */             rubroEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 1990:2106 */             rubroEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/* 1991:2107 */             rubroEmpleado.setRubro(rubro);
/* 1992:2108 */             rubroEmpleado.setValor(sueldo);
/* 1993:2109 */             rubroEmpleado.setEmpleado(empleado);
/* 1994:     */           }
/* 1995:     */           else
/* 1996:     */           {
/* 1997:2113 */             throw new ExcepcionAS2Nomina("msg_error_no_existe_salario_unificado");
/* 1998:     */           }
/* 1999:     */         }
/* 2000:     */         else
/* 2001:     */         {
/* 2002:2118 */           throw new ExcepcionAS2Nomina("msg_error_no_existe_salario_unificado");
/* 2003:     */         }
/* 2004:2121 */         empleado.setCentroCosto(dimensionContable);
/* 2005:     */         
/* 2006:2123 */         HistoricoEmpleado historicoEmpleado = new HistoricoEmpleado();
/* 2007:2124 */         historicoEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 2008:2125 */         historicoEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/* 2009:2126 */         historicoEmpleado.setNumeroFiniquito("");
/* 2010:2127 */         historicoEmpleado.setFechaIngreso(fechaIngreso);
/* 2011:2128 */         historicoEmpleado.setDiasFondoReserva(0);
/* 2012:2129 */         historicoEmpleado.setEmpleado(empleado);
/* 2013:     */         
/* 2014:     */ 
/* 2015:2132 */         DetalleHistoricoEmpleado dhe = new DetalleHistoricoEmpleado();
/* 2016:2133 */         dhe.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 2017:2134 */         dhe.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 2018:2135 */         dhe.setHistoricoEmpleado(historicoEmpleado);
/* 2019:2136 */         dhe.setTipoContrato(tipoContrato);
/* 2020:2137 */         String numero = this.servicioSecuencia.obtenerSecuencia(tipoContrato.getSecuencia(), fechaIngreso);
/* 2021:2138 */         dhe.setNumeroContrato(numero);
/* 2022:2139 */         dhe.setHorasSemana(ParametrosSistema.getHorasSemanaLaboral(AppUtil.getOrganizacion().getId()).intValue());
/* 2023:2140 */         Date fechaInicio = this.servicioHistoricoEmpleado.getMaximaFechaDetalleHistoricoEmpleado(historicoEmpleado.getId());
/* 2024:2141 */         if ((historicoEmpleado.getListaDetalleHistoricoEmpleado().isEmpty()) && (fechaInicio == null)) {
/* 2025:2142 */           dhe.setFechaInicio(historicoEmpleado.getFechaIngreso());
/* 2026:     */         }
/* 2027:2144 */         int horasSemanaLaboral = ParametrosSistema.getHorasSemanaLaboral(AppUtil.getOrganizacion().getId()).intValue();
/* 2028:     */         
/* 2029:2146 */         BigDecimal porcentaje = new BigDecimal(dhe.getHorasSemana()).multiply(new BigDecimal(100.0D)).divide(FuncionesUtiles.redondearBigDecimal(new BigDecimal(horasSemanaLaboral), 2));
/* 2030:2147 */         dhe.setPorcentajeCapacidadSemanal(FuncionesUtiles.redondearBigDecimal(porcentaje, 2));
/* 2031:2148 */         historicoEmpleado.getListaDetalleHistoricoEmpleado().add(dhe);
/* 2032:     */         
/* 2033:2150 */         empresa.setIndicadorEmpleado(true);
/* 2034:2151 */         empleado.setEmpresa(empresa);
/* 2035:2152 */         empresa.setEmpleado(empleado);
/* 2036:2153 */         this.servicioEmpresa.guardar(empresa);
/* 2037:2154 */         this.historicoEmpleadoDao.guardar(historicoEmpleado);
/* 2038:2155 */         this.detalleHistoricoEmpleadoDao.guardar((EntidadBase)historicoEmpleado.getListaDetalleHistoricoEmpleado().get(0));
/* 2039:2156 */         this.servicioSecuencia.actualizarSecuencia(secuencia, numero);
/* 2040:2157 */         this.servicioRubroEmpleado.guardar(rubroEmpleado);
/* 2041:     */       }
/* 2042:     */     }
/* 2043:     */     catch (IllegalStateException e)
/* 2044:     */     {
/* 2045:2161 */       LOG.info("Error al migrar productos", e);
/* 2046:2162 */       this.context.setRollbackOnly();
/* 2047:     */       
/* 2048:2164 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 2049:     */     }
/* 2050:     */     catch (ExcepcionAS2 e)
/* 2051:     */     {
/* 2052:2166 */       LOG.info("Error al migrar productos", e);
/* 2053:2167 */       this.context.setRollbackOnly();
/* 2054:2168 */       throw e;
/* 2055:     */     }
/* 2056:     */     catch (Exception e)
/* 2057:     */     {
/* 2058:2170 */       LOG.error("Error al migrar productos", e);
/* 2059:2171 */       e.printStackTrace();
/* 2060:2172 */       this.context.setRollbackOnly();
/* 2061:     */       
/* 2062:2174 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString(), e);
/* 2063:     */     }
/* 2064:     */   }
/* 2065:     */   
/* 2066:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2067:     */   public void migracionCargaEmpleado(InputStream imInputStream, int filaInicial)
/* 2068:     */     throws ExcepcionAS2
/* 2069:     */   {
/* 2070:2190 */     Map<String, String> filtros = new HashMap();
/* 2071:2191 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 2072:     */     
/* 2073:2193 */     Map<String, Empleado> hmEmpleados = new HashMap();
/* 2074:2194 */     for (Iterator localIterator = this.servicioEmpleado.obtenerListaCombo("", false, filtros).iterator(); localIterator.hasNext();)
/* 2075:     */     {
/* 2076:2194 */       empleado = (Empleado)localIterator.next();
/* 2077:2195 */       hmEmpleados.put(empleado.getEmpresa().getIdentificacion(), empleado);
/* 2078:     */     }
/* 2079:     */     Empleado empleado;
/* 2080:2198 */     Object hmTipoDiscapacidad = new HashMap();
/* 2081:2199 */     for (TipoDiscapacidad tipoDiscapacidad : this.servicioTipoDiscapacidad.obtenerListaCombo("", false, filtros)) {
/* 2082:2200 */       ((Map)hmTipoDiscapacidad).put(tipoDiscapacidad.getCodigo(), tipoDiscapacidad);
/* 2083:     */     }
/* 2084:2203 */     int filaActual = filaInicial;
/* 2085:2204 */     int columnaActual = 0;
/* 2086:2205 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 2087:     */     try
/* 2088:     */     {
/* 2089:2208 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 2090:2209 */       for (HSSFCell[] fila : datos)
/* 2091:     */       {
/* 2092:2211 */         filaErronea = fila;
/* 2093:2212 */         filaActual++;
/* 2094:     */         
/* 2095:2214 */         String identificacionEmpleado = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 0, true, 
/* 2096:2215 */           Integer.valueOf(1), Integer.valueOf(20));
/* 2097:     */         
/* 2098:2217 */         Empleado empleado = (Empleado)hmEmpleados.get(identificacionEmpleado);
/* 2099:2218 */         if (empleado == null) {
/* 2100:2220 */           throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", "Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 2101:     */         }
/* 2102:2223 */         String identificacionCarga = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 1, false, Integer.valueOf(1), 
/* 2103:2224 */           Integer.valueOf(20));
/* 2104:2225 */         Date fechaNacimiento = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 2, true, Integer.valueOf(0), Integer.valueOf(0));
/* 2105:     */         
/* 2106:2227 */         String nombresCarga = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 3, true, Integer.valueOf(0), Integer.valueOf(20));
/* 2107:2228 */         String apellidosCarga = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 4, true, Integer.valueOf(0), Integer.valueOf(20));
/* 2108:     */         
/* 2109:2230 */         String parentezcoCarga = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 5, true, Integer.valueOf(0), Integer.valueOf(10));
/* 2110:2231 */         Parentezco parentezcoEmpleado = Parentezco.valueOf(parentezcoCarga);
/* 2111:2232 */         if (parentezcoEmpleado == null) {
/* 2112:2233 */           parentezcoEmpleado = Parentezco.OTRO;
/* 2113:     */         }
/* 2114:2236 */         String genero = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 6, true, Integer.valueOf(0), Integer.valueOf(10));
/* 2115:2237 */         Genero generoCarga = genero.equalsIgnoreCase(Genero.MASCULINO.toString()) ? Genero.MASCULINO : Genero.FEMENINO;
/* 2116:     */         
/* 2117:2239 */         String codigoTipoDiscapacidad = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 7, false, 
/* 2118:2240 */           Integer.valueOf(2), Integer.valueOf(10));
/* 2119:2241 */         TipoDiscapacidad tipoDiscapacidad = (TipoDiscapacidad)((Map)hmTipoDiscapacidad).get(codigoTipoDiscapacidad);
/* 2120:     */         
/* 2121:2243 */         BigDecimal porcentajeTipoDiscapacidad = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 8, false, 
/* 2122:2244 */           Integer.valueOf(0), Integer.valueOf(0));
/* 2123:     */         
/* 2124:2246 */         CargaEmpleado cargaEmpleado = new CargaEmpleado();
/* 2125:2247 */         cargaEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 2126:2248 */         cargaEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/* 2127:2249 */         cargaEmpleado.setFechaNacimiento(fechaNacimiento);
/* 2128:2250 */         cargaEmpleado.setIdentificacion(identificacionCarga);
/* 2129:2251 */         cargaEmpleado.setGenero(generoCarga);
/* 2130:2252 */         cargaEmpleado.setParentezco(parentezcoEmpleado);
/* 2131:2253 */         cargaEmpleado.setNombre(nombresCarga + "\t" + apellidosCarga);
/* 2132:2254 */         cargaEmpleado.setPorcentajeDiscapacidad(porcentajeTipoDiscapacidad);
/* 2133:2255 */         cargaEmpleado.setActivo(true);
/* 2134:2256 */         empleado.setNumeroCargasActivas(empleado.getNumeroCargasActivas() + 1);
/* 2135:2257 */         cargaEmpleado.setEmpleado(empleado);
/* 2136:2258 */         cargaEmpleado.setTipoDiscapacidad(tipoDiscapacidad);
/* 2137:2259 */         this.servicioCargaEmpleado.guardar(cargaEmpleado);
/* 2138:     */       }
/* 2139:     */     }
/* 2140:     */     catch (IllegalStateException e)
/* 2141:     */     {
/* 2142:2264 */       LOG.info("Error al migrar ", e);
/* 2143:2265 */       this.context.setRollbackOnly();
/* 2144:     */       
/* 2145:2267 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 2146:     */     }
/* 2147:     */     catch (IllegalArgumentException e)
/* 2148:     */     {
/* 2149:2269 */       LOG.info("Error al migrar ", e);
/* 2150:2270 */       this.context.setRollbackOnly();
/* 2151:     */       
/* 2152:2272 */       throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 2153:     */     }
/* 2154:     */     catch (ExcepcionAS2Financiero e)
/* 2155:     */     {
/* 2156:2274 */       this.context.setRollbackOnly();
/* 2157:2275 */       throw e;
/* 2158:     */     }
/* 2159:     */     catch (ExcepcionAS2 e)
/* 2160:     */     {
/* 2161:2277 */       this.context.setRollbackOnly();
/* 2162:2278 */       throw e;
/* 2163:     */     }
/* 2164:     */     catch (Exception e)
/* 2165:     */     {
/* 2166:2280 */       this.context.setRollbackOnly();
/* 2167:2281 */       throw new ExcepcionAS2(e);
/* 2168:     */     }
/* 2169:     */   }
/* 2170:     */   
/* 2171:     */   public void listaRetencion(BigDecimal baseImponibleRetencionIVA, ConceptoRetencionSRI conceptoRetencionIVA, ConceptoRetencionSRI conceptoRetencionIVADiferencia, BigDecimal porcentajeRetencionIVA, BigDecimal baseImponibleRetencionFuente, ConceptoRetencionSRI conceptoRetencionFuente, ConceptoRetencionSRI conceptoRetencionFuenteDiferencia, BigDecimal porcentajeRetencionFuente, FacturaProveedor facturaProveedor)
/* 2172:     */     throws ExcepcionAS2Financiero, AS2Exception
/* 2173:     */   {
/* 2174:2291 */     BigDecimal total = facturaProveedor.getTotal();
/* 2175:2292 */     BigDecimal impuesto = facturaProveedor.getImpuesto();
/* 2176:     */     
/* 2177:2294 */     verificaValorMayor(baseImponibleRetencionFuente, total, baseImponibleRetencionIVA, impuesto);
/* 2178:2296 */     if (impuesto.subtract(baseImponibleRetencionIVA).compareTo(BigDecimal.ZERO) != 0) {
/* 2179:2297 */       creacionDetalleFacturaProveedorSRI(impuesto.subtract(baseImponibleRetencionIVA), conceptoRetencionIVADiferencia, facturaProveedor);
/* 2180:     */     }
/* 2181:2300 */     if (total.subtract(baseImponibleRetencionFuente).compareTo(BigDecimal.ZERO) != 0) {
/* 2182:2301 */       creacionDetalleFacturaProveedorSRI(total.subtract(baseImponibleRetencionFuente), conceptoRetencionFuenteDiferencia, facturaProveedor);
/* 2183:     */     }
/* 2184:2304 */     creacionDetalleFacturaProveedorSRI(baseImponibleRetencionIVA, conceptoRetencionIVA, facturaProveedor);
/* 2185:     */     
/* 2186:2306 */     creacionDetalleFacturaProveedorSRI(baseImponibleRetencionFuente, conceptoRetencionFuente, facturaProveedor);
/* 2187:     */   }
/* 2188:     */   
/* 2189:     */   public void creacionDetalleFacturaProveedorSRI(BigDecimal baseImponible, ConceptoRetencionSRI conceptoRetencion, FacturaProveedor facturaProveedor)
/* 2190:     */   {
/* 2191:2312 */     DetalleFacturaProveedorSRI detalleFacturaProveedorSRI = new DetalleFacturaProveedorSRI();
/* 2192:2313 */     detalleFacturaProveedorSRI.setBaseImponibleTarifaCero(BigDecimal.ZERO);
/* 2193:2314 */     detalleFacturaProveedorSRI.setBaseImponibleDiferenteCero(BigDecimal.ZERO);
/* 2194:2315 */     detalleFacturaProveedorSRI.setBaseImponibleNoObjetoIva(BigDecimal.ZERO);
/* 2195:2316 */     detalleFacturaProveedorSRI.setBaseImponibleRetencion(baseImponible);
/* 2196:2317 */     detalleFacturaProveedorSRI.setConceptoRetencionSRI(conceptoRetencion);
/* 2197:2318 */     detalleFacturaProveedorSRI.setPorcentajeRetencion(conceptoRetencion.getPorcentaje());
/* 2198:2319 */     detalleFacturaProveedorSRI.getConceptoRetencionSRI().setIngresaPorcentaje(false);
/* 2199:2320 */     this.servicioFacturaProveedorSRI.actualizarValorRetencion(detalleFacturaProveedorSRI);
/* 2200:2321 */     detalleFacturaProveedorSRI.setFacturaProveedorSRI(facturaProveedor.getFacturaProveedorSRI());
/* 2201:2322 */     facturaProveedor.getFacturaProveedorSRI().getListaDetalleFacturaProveedorSRI().add(detalleFacturaProveedorSRI);
/* 2202:     */   }
/* 2203:     */   
/* 2204:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2205:     */   public List<FacturaProveedor> migracionFacturaProveedor(String fileName, InputStream imInputStream, int filaInicial, int idOrganizacion)
/* 2206:     */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 2207:     */   {
/* 2208:2335 */     List<FacturaProveedor> listaFacturaProveedor = new ArrayList();
/* 2209:     */     
/* 2210:2337 */     HashMap<String, String> filtersFPS = new HashMap();
/* 2211:2338 */     filtersFPS.put("idOrganizacion", Integer.valueOf(idOrganizacion).toString());
/* 2212:2339 */     filtersFPS.put("predeterminado", "true");
/* 2213:2340 */     List<CodigoFormaPagoSRI> listaFPS = this.codigoFormaPagoSRIDao.obtenerListaCombo(CodigoFormaPagoSRI.class, "predeterminado", true, filtersFPS);
/* 2214:     */     
/* 2215:2342 */     HashMap<String, String> hmProveedorFPS = new HashMap();
/* 2216:     */     
/* 2217:     */ 
/* 2218:     */ 
/* 2219:     */ 
/* 2220:2347 */     Date fechaVencimiento = null;
/* 2221:2348 */     HashMap<String, FacturaProveedor> hmFacturaProveedo = new HashMap();
/* 2222:2349 */     HashMap<String, Empresa> hmEmpresa = new HashMap();
/* 2223:2350 */     HashMap<String, TipoOperacion> hmTipoOperacion = new HashMap();
/* 2224:2351 */     HashMap<String, Producto> hmProducto = new HashMap();
/* 2225:2352 */     HashMap<Date, RangoImpuesto> hmRangoImpuesto = new HashMap();
/* 2226:2353 */     HashMap<String, DimensionContable> hmDimensionContable1 = new HashMap();
/* 2227:2354 */     HashMap<String, DimensionContable> hmDimensionContable2 = new HashMap();
/* 2228:2355 */     HashMap<String, DimensionContable> hmDimensionContable3 = new HashMap();
/* 2229:2356 */     HashMap<String, DimensionContable> hmDimensionContable4 = new HashMap();
/* 2230:2357 */     HashMap<String, DimensionContable> hmDimensionContable5 = new HashMap();
/* 2231:2358 */     HashMap<Integer, CondicionPago> hmCondicionPago = new HashMap();
/* 2232:2359 */     HashMap<String, Documento> hmDocumento = new HashMap();
/* 2233:2360 */     HashMap<String, DireccionEmpresa> hmDireccionEmpresa = new HashMap();
/* 2234:     */     
/* 2235:     */ 
/* 2236:2363 */     HashMap<String, CreditoTributarioSRI> hmCreditoTributario = new HashMap();
/* 2237:2364 */     HashMap<String, ConceptoRetencionSRI> hmConceptoRetencionSRIFuente = new HashMap();
/* 2238:2365 */     HashMap<BigDecimal, ConceptoRetencionSRI> hmConceptoRetencionSRIIVA = new HashMap();
/* 2239:     */     
/* 2240:     */ 
/* 2241:2368 */     int filaActual = filaInicial;
/* 2242:2369 */     int columnaErronea = -1;
/* 2243:2370 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 2244:2371 */     int numeroColumna = 0;
/* 2245:2372 */     String retornarNumeroFactura = "";
/* 2246:     */     try
/* 2247:     */     {
/* 2248:2375 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/* 2249:2376 */       numeroColumna = datos[0].length;
/* 2250:2378 */       for (HSSFCell[] fila : datos)
/* 2251:     */       {
/* 2252:2380 */         filaErronea = fila;
/* 2253:     */         
/* 2254:     */ 
/* 2255:     */ 
/* 2256:     */ 
/* 2257:2385 */         Date fecha = fila[(columnaErronea = 0)].getDateCellValue();
/* 2258:2386 */         String identificacion = fila[(columnaErronea = 1)].getStringCellValue();
/* 2259:2387 */         Integer numeroCuotas = Integer.valueOf((int)fila[(columnaErronea = 2)].getNumericCellValue());
/* 2260:2388 */         Integer plazo = Integer.valueOf((int)fila[(columnaErronea = 3)].getNumericCellValue());
/* 2261:2389 */         String establecimiento = fila[(columnaErronea = 4)].getStringCellValue();
/* 2262:2390 */         String puntoEmision = fila[(columnaErronea = 5)].getStringCellValue();
/* 2263:2391 */         String numeroFactura = fila[(columnaErronea = 6)].getStringCellValue();
/* 2264:2392 */         String autorizacion = fila[(columnaErronea = 7)].getStringCellValue();
/* 2265:2393 */         BigDecimal valorBono = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 8)] == null ? 0.0D : fila[(columnaErronea = 8)]
/* 2266:2394 */           .getNumericCellValue()), 2);
/* 2267:2395 */         String nota = fila[(columnaErronea = 9)] != null ? fila[(columnaErronea = 9)].getStringCellValue() : "";
/* 2268:2396 */         String codigoProducto = fila[(columnaErronea = 10)].getStringCellValue();
/* 2269:2397 */         BigDecimal cantidad = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 11)].getNumericCellValue()), 2);
/* 2270:2398 */         BigDecimal precio = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 12)].getNumericCellValue()), 2);
/* 2271:2399 */         BigDecimal descuentoUnitario = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 13)].getNumericCellValue()), 2);
/* 2272:     */         
/* 2273:2401 */         String IVA = fila[(columnaErronea = 14)].getStringCellValue();
/* 2274:2402 */         String codigoCuentaGasto = fila[(columnaErronea = 15)] == null ? "" : fila[15].getStringCellValue().trim();
/* 2275:2403 */         String codigoCuentaGastoImpuesto = fila[(columnaErronea = 16)] == null ? "" : fila[16].getStringCellValue().trim();
/* 2276:     */         
/* 2277:2405 */         String codigoDimensionContable1 = (fila[(columnaErronea = 17)] == null) || (fila[17].getStringCellValue().trim().isEmpty()) ? null : fila[17].getStringCellValue().trim();
/* 2278:     */         
/* 2279:2407 */         String codigoDimensionContable2 = (fila[(columnaErronea = 18)] == null) || (fila[18].getStringCellValue().trim().isEmpty()) ? null : fila[18].getStringCellValue().trim();
/* 2280:     */         
/* 2281:2409 */         String codigoDimensionContable3 = (fila[(columnaErronea = 19)] == null) || (fila[19].getStringCellValue().trim().isEmpty()) ? null : fila[19].getStringCellValue().trim();
/* 2282:     */         
/* 2283:2411 */         String codigoDimensionContable4 = (fila[(columnaErronea = 20)] == null) || (fila[20].getStringCellValue().trim().isEmpty()) ? null : fila[20].getStringCellValue().trim();
/* 2284:     */         
/* 2285:2413 */         String codigoDimensionContable5 = (fila[(columnaErronea = 21)] == null) || (fila[21].getStringCellValue().trim().isEmpty()) ? null : fila[21].getStringCellValue().trim();
/* 2286:2414 */         boolean indicadorSaldoInicial = fila[(columnaErronea = 22)].getStringCellValue().equalsIgnoreCase("SI");
/* 2287:2415 */         fechaVencimiento = fila[(columnaErronea = 23)] == null ? null : fila[23].getDateCellValue();
/* 2288:2416 */         String codigoTipoOperacion = fila[24] == null ? "" : fila[(columnaErronea = 24)].getStringCellValue();
/* 2289:2417 */         String nombreDocumento = fila[25] == null ? "" : fila[(columnaErronea = 25)].getStringCellValue().trim();
/* 2290:     */         
/* 2291:     */ 
/* 2292:2420 */         String nombreDocumentoRetencion = fila[(columnaErronea = 26)] == null ? null : fila[(columnaErronea = 26)].getStringCellValue();
/* 2293:2421 */         System.out.println("nombre documento referencia .....     " + nombreDocumentoRetencion);
/* 2294:2422 */         Date fechaEmisionRetencion = fila[(columnaErronea = 27)] == null ? null : fila[(columnaErronea = 27)].getDateCellValue();
/* 2295:2423 */         String codigoSustentoTributario = fila[(columnaErronea = 28)] == null ? null : fila[(columnaErronea = 28)].getStringCellValue();
/* 2296:2424 */         String email = fila[(columnaErronea = 29)] == null ? null : fila[(columnaErronea = 29)].getStringCellValue();
/* 2297:     */         
/* 2298:     */ 
/* 2299:2427 */         BigDecimal baseImponibleRetencionIVA = fila[(columnaErronea = 30)] == null ? BigDecimal.ZERO : FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 30)].getNumericCellValue()), 2);
/* 2300:     */         
/* 2301:2429 */         BigDecimal porcentajeRetencionIVA = fila[(columnaErronea = 31)] == null ? BigDecimal.ZERO : FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 31)].getNumericCellValue()), 2);
/* 2302:     */         
/* 2303:2431 */         BigDecimal baseImponibleRetencionFuente = fila[(columnaErronea = 32)] == null ? BigDecimal.ZERO : FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 32)].getNumericCellValue()), 2);
/* 2304:     */         
/* 2305:2433 */         String codigoConceptoRetencionFuente = fila[(columnaErronea = 33)] == null ? null : fila[(columnaErronea = 33)].toString().replace(".0", "");
/* 2306:     */         
/* 2307:     */ 
/* 2308:2436 */         BigDecimal porcentajeRetencionFuente = (fila[(columnaErronea = 34)] == null) || (fila[(columnaErronea = 34)].getStringCellValue().equals("")) || (fila[(columnaErronea = 34)].getStringCellValue().equals("0.0")) ? BigDecimal.ZERO : FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[(columnaErronea = 34)].getNumericCellValue()), 2);
/* 2309:     */         
/* 2310:     */ 
/* 2311:2439 */         List<ConceptoRetencionSRI> listaConceptoRetencionSRIFuente = this.servicioConceptoRetencionSRI.getConceptoListaRetencionPorFecha(fechaEmisionRetencion);
/* 2312:2440 */         for (Iterator localIterator = listaConceptoRetencionSRIFuente.iterator(); localIterator.hasNext();)
/* 2313:     */         {
/* 2314:2440 */           conceptoRetencionSRI = (ConceptoRetencionSRI)localIterator.next();
/* 2315:2441 */           hmConceptoRetencionSRIFuente.put(conceptoRetencionSRI.getCodigo(), conceptoRetencionSRI);
/* 2316:     */         }
/* 2317:     */         ConceptoRetencionSRI conceptoRetencionSRI;
/* 2318:2444 */         Object listaConceptoRetencionSRIIVA = this.servicioConceptoRetencionSRI.conceptoRetencionIVAPorcentaje(idOrganizacion);
/* 2319:2445 */         for (ConceptoRetencionSRI conceptoRetencionSRI : (List)listaConceptoRetencionSRIIVA) {
/* 2320:2446 */           hmConceptoRetencionSRIIVA.put(conceptoRetencionSRI.getPorcentaje(), conceptoRetencionSRI);
/* 2321:     */         }
/* 2322:2451 */         StringBuilder clave = new StringBuilder();
/* 2323:2452 */         clave.append(identificacion);
/* 2324:2453 */         clave.append(establecimiento);
/* 2325:2454 */         clave.append(puntoEmision);
/* 2326:2455 */         clave.append(numeroFactura);
/* 2327:2456 */         clave.append(autorizacion);
/* 2328:     */         
/* 2329:     */ 
/* 2330:     */ 
/* 2331:2460 */         Empresa empresa = (Empresa)hmEmpresa.get(identificacion);
/* 2332:2461 */         if (empresa == null)
/* 2333:     */         {
/* 2334:2462 */           Map<String, String> filters = new HashMap();
/* 2335:2463 */           filters.put("identificacion", identificacion);
/* 2336:2464 */           filters.put("indicadorProveedor", "true");
/* 2337:2465 */           empresa = this.servicioEmpresa.bucarEmpresaPorIdentificacion(filters);
/* 2338:2466 */           empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 2339:2467 */           hmEmpresa.put(identificacion, empresa);
/* 2340:     */         }
/* 2341:2473 */         DireccionEmpresa direccionEmpresa = (DireccionEmpresa)hmDireccionEmpresa.get(identificacion);
/* 2342:2474 */         if (direccionEmpresa == null)
/* 2343:     */         {
/* 2344:2475 */           direccionEmpresa = this.direccionEmpresaDao.buscarPorEmpresa(empresa);
/* 2345:2476 */           hmDireccionEmpresa.put(identificacion, direccionEmpresa);
/* 2346:     */         }
/* 2347:2482 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 2348:2483 */         if (producto == null)
/* 2349:     */         {
/* 2350:2484 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, idOrganizacion, null);
/* 2351:2485 */           hmProducto.put(codigoProducto, producto);
/* 2352:     */         }
/* 2353:2492 */         RangoImpuesto rangoImpuesto = (RangoImpuesto)hmRangoImpuesto.get(fecha);
/* 2354:2493 */         if (rangoImpuesto == null)
/* 2355:     */         {
/* 2356:2494 */           rangoImpuesto = this.servicioImpuesto.getRangoRangoImpuestoTributario(fecha, idOrganizacion);
/* 2357:2495 */           hmRangoImpuesto.put(fecha, rangoImpuesto);
/* 2358:     */         }
/* 2359:2501 */         CondicionPago condicionPago = (CondicionPago)hmCondicionPago.get(plazo);
/* 2360:2502 */         if (condicionPago == null)
/* 2361:     */         {
/* 2362:2503 */           condicionPago = this.servicioCondicionPago.buscarCondicionPagoPorDiasPlazo(plazo.intValue(), idOrganizacion);
/* 2363:2504 */           hmCondicionPago.put(plazo, condicionPago);
/* 2364:     */         }
/* 2365:2508 */         if (fechaVencimiento != null) {
/* 2366:2509 */           plazo = Integer.valueOf(FuncionesUtiles.diferenciasDeFechas(fecha, fechaVencimiento));
/* 2367:     */         }
/* 2368:2512 */         TipoOperacion tipoOperacion = null;
/* 2369:2513 */         if (!codigoTipoOperacion.isEmpty())
/* 2370:     */         {
/* 2371:2514 */           tipoOperacion = (TipoOperacion)hmTipoOperacion.get(codigoTipoOperacion);
/* 2372:2515 */           if (tipoOperacion == null)
/* 2373:     */           {
/* 2374:2516 */             HashMap<String, String> filters = new HashMap();
/* 2375:2517 */             filters.put("codigo", codigoTipoOperacion);
/* 2376:2518 */             List<TipoOperacion> lista = this.servicioTipoOperacion.obtenerListaCombo("nombre", true, filters);
/* 2377:2519 */             if (!lista.isEmpty())
/* 2378:     */             {
/* 2379:2520 */               tipoOperacion = (TipoOperacion)lista.get(0);
/* 2380:2521 */               hmTipoOperacion.put(codigoTipoOperacion, tipoOperacion);
/* 2381:     */             }
/* 2382:     */           }
/* 2383:     */         }
/* 2384:2528 */         FacturaProveedor facturaProveedor = (FacturaProveedor)hmFacturaProveedo.get(clave.toString());
/* 2385:2529 */         if (facturaProveedor == null)
/* 2386:     */         {
/* 2387:2533 */           facturaProveedor = new FacturaProveedor();
/* 2388:2534 */           facturaProveedor.setBono(valorBono);
/* 2389:2535 */           facturaProveedor.setSucursal(AppUtil.getSucursal());
/* 2390:2536 */           facturaProveedor.setIdOrganizacion(idOrganizacion);
/* 2391:2537 */           facturaProveedor.setEmpresa(empresa);
/* 2392:2538 */           facturaProveedor.setFecha(fecha);
/* 2393:2539 */           facturaProveedor.setDescuento(BigDecimal.ZERO);
/* 2394:2540 */           facturaProveedor.setEstado(Estado.APROBADO);
/* 2395:2541 */           facturaProveedor.setNumero("");
/* 2396:2542 */           facturaProveedor.setNumeroCuotas(numeroCuotas.intValue());
/* 2397:2543 */           facturaProveedor.setCondicionPago(new CondicionPago());
/* 2398:2544 */           facturaProveedor.setDireccionEmpresa(direccionEmpresa);
/* 2399:2545 */           facturaProveedor.setDescripcion(nota);
/* 2400:2546 */           facturaProveedor.setCondicionPago(condicionPago);
/* 2401:2547 */           facturaProveedor.setIndicadorSaldoInicial(indicadorSaldoInicial);
/* 2402:2548 */           facturaProveedor.setTipoOperacion(tipoOperacion);
/* 2403:     */           
/* 2404:     */ 
/* 2405:     */ 
/* 2406:     */ 
/* 2407:2553 */           Documento documento = (Documento)hmDocumento.get(nombreDocumento);
/* 2408:2554 */           if (documento == null)
/* 2409:     */           {
/* 2410:2555 */             Map<String, String> filtroDoc = new HashMap();
/* 2411:2556 */             filtroDoc.put("documentoBase", "" + DocumentoBase.FACTURA_PROVEEDOR);
/* 2412:2557 */             filtroDoc.put("nombre", nombreDocumento);
/* 2413:2558 */             List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("", false, filtroDoc);
/* 2414:2559 */             if (listaDocumento.isEmpty()) {
/* 2415:2560 */               throw new AS2Exception("msg_configuracion_documento", new String[] { "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + nombreDocumento });
/* 2416:     */             }
/* 2417:2563 */             documento = (Documento)listaDocumento.get(0);
/* 2418:2564 */             hmDocumento.put("nombre", documento);
/* 2419:     */           }
/* 2420:2567 */           facturaProveedor.setDocumento(documento);
/* 2421:     */           
/* 2422:     */ 
/* 2423:     */ 
/* 2424:     */ 
/* 2425:     */ 
/* 2426:2573 */           FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/* 2427:2574 */           facturaProveedorSRI.setIdOrganizacion(idOrganizacion);
/* 2428:2575 */           facturaProveedorSRI.setIdSucursal(facturaProveedor.getSucursal().getId());
/* 2429:2576 */           facturaProveedorSRI.setEstablecimientoRetencion(establecimiento);
/* 2430:2577 */           facturaProveedorSRI.setPuntoEmisionRetencion(puntoEmision);
/* 2431:2578 */           facturaProveedorSRI.setNumeroRetencion("0");
/* 2432:2579 */           facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/* 2433:2580 */           facturaProveedorSRI.setTipoComprobanteSRI(null);
/* 2434:2581 */           facturaProveedorSRI.setTipoIdentificacion(facturaProveedor.getEmpresa().getTipoIdentificacion());
/* 2435:2582 */           facturaProveedorSRI.setNumero(numeroFactura);
/* 2436:2583 */           facturaProveedorSRI.setEstablecimiento(establecimiento);
/* 2437:2584 */           facturaProveedorSRI.setPuntoEmision(puntoEmision);
/* 2438:2585 */           facturaProveedorSRI.setAutorizacion(autorizacion);
/* 2439:2586 */           if (autorizacion.length() >= 37) {
/* 2440:2587 */             facturaProveedorSRI.setIndicadorFacturaElectronica(true);
/* 2441:     */           }
/* 2442:2590 */           facturaProveedorSRI.setFacturaProveedor(facturaProveedor);
/* 2443:2591 */           facturaProveedorSRI.setFechaEmision(fecha);
/* 2444:2592 */           facturaProveedor.setFacturaProveedorSRI(facturaProveedorSRI);
/* 2445:     */           
/* 2446:     */ 
/* 2447:2595 */           codigoFormaPagoSRI(facturaProveedor, listaFPS, identificacion, hmProveedorFPS);
/* 2448:     */           
/* 2449:2597 */           hmFacturaProveedo.put(clave.toString(), facturaProveedor);
/* 2450:     */         }
/* 2451:2603 */         DetalleFacturaProveedor detalleFacturaProveedor = new DetalleFacturaProveedor();
/* 2452:2604 */         detalleFacturaProveedor.setIdSucursal(AppUtil.getSucursal().getId());
/* 2453:2605 */         detalleFacturaProveedor.setIdOrganizacion(idOrganizacion);
/* 2454:2606 */         detalleFacturaProveedor.setFacturaProveedor(facturaProveedor);
/* 2455:2607 */         detalleFacturaProveedor.setCantidad(cantidad);
/* 2456:2608 */         detalleFacturaProveedor.setDescuento(descuentoUnitario);
/* 2457:2609 */         detalleFacturaProveedor.setPrecio(precio);
/* 2458:2610 */         detalleFacturaProveedor.setProducto(producto);
/* 2459:2611 */         if (producto.getUnidadCompra() == null) {
/* 2460:2612 */           throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.CONFIGURAR_UNIDAD_COMPRA", new String[] { codigoProducto });
/* 2461:     */         }
/* 2462:2614 */         detalleFacturaProveedor.setUnidadCompra(producto.getUnidadCompra());
/* 2463:2616 */         if (IVA.equals("SI"))
/* 2464:     */         {
/* 2465:2620 */           detalleFacturaProveedor.setIndicadorImpuestos(true);
/* 2466:2621 */           ImpuestoProductoFacturaProveedor ipfp = new ImpuestoProductoFacturaProveedor();
/* 2467:2622 */           ipfp.setDetalleFacturaProveedor(detalleFacturaProveedor);
/* 2468:2623 */           ipfp.setImpuesto(rangoImpuesto.getImpuesto());
/* 2469:2624 */           ipfp.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 2470:2625 */           detalleFacturaProveedor.getListaImpuestoProductoFacturaProveedor().add(ipfp);
/* 2471:     */         }
/* 2472:     */         else
/* 2473:     */         {
/* 2474:2628 */           detalleFacturaProveedor.setIndicadorImpuestos(false);
/* 2475:     */         }
/* 2476:2630 */         facturaProveedor.getListaDetalleFacturaProveedor().add(detalleFacturaProveedor);
/* 2477:2635 */         if (producto.isTraIndicadorServicio())
/* 2478:     */         {
/* 2479:2637 */           DimensionContable dimensionContable1 = null;
/* 2480:2638 */           if (codigoDimensionContable1 != null)
/* 2481:     */           {
/* 2482:2640 */             dimensionContable1 = (DimensionContable)hmDimensionContable1.get(codigoDimensionContable1);
/* 2483:2642 */             if (dimensionContable1 == null)
/* 2484:     */             {
/* 2485:2643 */               dimensionContable1 = this.servicioDimensionContable.buscarPorCodigo("1", codigoDimensionContable1);
/* 2486:2644 */               hmDimensionContable1.put(codigoDimensionContable1, dimensionContable1);
/* 2487:     */             }
/* 2488:     */           }
/* 2489:2648 */           DimensionContable dimensionContable2 = null;
/* 2490:2649 */           if (codigoDimensionContable2 != null)
/* 2491:     */           {
/* 2492:2651 */             dimensionContable2 = (DimensionContable)hmDimensionContable2.get(codigoDimensionContable2);
/* 2493:2653 */             if (dimensionContable2 == null)
/* 2494:     */             {
/* 2495:2654 */               dimensionContable2 = this.servicioDimensionContable.buscarPorCodigo("2", codigoDimensionContable2);
/* 2496:2655 */               hmDimensionContable2.put(codigoDimensionContable2, dimensionContable2);
/* 2497:     */             }
/* 2498:     */           }
/* 2499:2659 */           DimensionContable dimensionContable3 = null;
/* 2500:2660 */           if (codigoDimensionContable3 != null)
/* 2501:     */           {
/* 2502:2662 */             dimensionContable3 = (DimensionContable)hmDimensionContable3.get(codigoDimensionContable3);
/* 2503:2664 */             if (dimensionContable3 == null)
/* 2504:     */             {
/* 2505:2665 */               dimensionContable3 = this.servicioDimensionContable.buscarPorCodigo("3", codigoDimensionContable3);
/* 2506:2666 */               hmDimensionContable3.put(codigoDimensionContable3, dimensionContable3);
/* 2507:     */             }
/* 2508:     */           }
/* 2509:2670 */           DimensionContable dimensionContable4 = null;
/* 2510:2671 */           if (codigoDimensionContable4 != null)
/* 2511:     */           {
/* 2512:2673 */             dimensionContable4 = (DimensionContable)hmDimensionContable4.get(codigoDimensionContable4);
/* 2513:2675 */             if (dimensionContable4 == null)
/* 2514:     */             {
/* 2515:2676 */               dimensionContable4 = this.servicioDimensionContable.buscarPorCodigo("4", codigoDimensionContable4);
/* 2516:2677 */               hmDimensionContable4.put(codigoDimensionContable4, dimensionContable4);
/* 2517:     */             }
/* 2518:     */           }
/* 2519:2681 */           DimensionContable dimensionContable5 = null;
/* 2520:2682 */           if (codigoDimensionContable5 != null)
/* 2521:     */           {
/* 2522:2684 */             dimensionContable5 = (DimensionContable)hmDimensionContable5.get(codigoDimensionContable5);
/* 2523:2686 */             if (dimensionContable5 == null)
/* 2524:     */             {
/* 2525:2687 */               dimensionContable5 = this.servicioDimensionContable.buscarPorCodigo("5", codigoDimensionContable5);
/* 2526:2688 */               hmDimensionContable5.put(codigoDimensionContable5, dimensionContable5);
/* 2527:     */             }
/* 2528:     */           }
/* 2529:2696 */           CuentaContable CCGasto = null;
/* 2530:2697 */           if (!"".equals(codigoCuentaGasto)) {
/* 2531:     */             try
/* 2532:     */             {
/* 2533:2699 */               CCGasto = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaGasto, AppUtil.getOrganizacion().getIdOrganizacion());
/* 2534:     */             }
/* 2535:     */             catch (Exception e)
/* 2536:     */             {
/* 2537:2701 */               throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl.CUENTA_CONTABLE_ERRONEA", new String[] { codigoCuentaGasto });
/* 2538:     */             }
/* 2539:     */           }
/* 2540:2705 */           CuentaContable CCImpuesto = null;
/* 2541:2707 */           if (!"".equals(codigoCuentaGastoImpuesto)) {
/* 2542:     */             try
/* 2543:     */             {
/* 2544:2709 */               CCImpuesto = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaGastoImpuesto, 
/* 2545:2710 */                 AppUtil.getOrganizacion().getIdOrganizacion());
/* 2546:     */             }
/* 2547:     */             catch (Exception e)
/* 2548:     */             {
/* 2549:2712 */               throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioAsientoImpl.CUENTA_CONTABLE_ERRONEA", new String[] { codigoCuentaGastoImpuesto });
/* 2550:     */             }
/* 2551:     */           }
/* 2552:2716 */           this.servicioFacturaProveedor.agregarGastoProductoFacturaProveedor(detalleFacturaProveedor, dimensionContable1, dimensionContable2, dimensionContable3, dimensionContable4, dimensionContable5, CCGasto, CCImpuesto);
/* 2553:     */         }
/* 2554:2726 */         if ((nombreDocumentoRetencion != null) && (!nombreDocumentoRetencion.trim().isEmpty()))
/* 2555:     */         {
/* 2556:2728 */           facturaProveedor.getFacturaProveedorSRI().setFechaEmisionRetencion(fechaEmisionRetencion);
/* 2557:2729 */           facturaProveedor.getFacturaProveedorSRI().setEmail(email);
/* 2558:     */           
/* 2559:2731 */           Documento documentoRetencion = (Documento)hmDocumento.get(nombreDocumentoRetencion);
/* 2560:2732 */           if (documentoRetencion == null)
/* 2561:     */           {
/* 2562:2733 */             HashMap<String, String> filters = new HashMap();
/* 2563:2734 */             filters.put("nombre", nombreDocumentoRetencion);
/* 2564:2735 */             filters.put("idOrganizacion", Integer.toString(idOrganizacion));
/* 2565:2736 */             documentoRetencion = (Documento)this.servicioDocumento.obtenerListaCombo("", false, filters).get(0);
/* 2566:2737 */             hmDocumento.put(nombreDocumentoRetencion, documentoRetencion);
/* 2567:     */           }
/* 2568:2739 */           CreditoTributarioSRI creditoTributarioSRI = (CreditoTributarioSRI)hmCreditoTributario.get(codigoSustentoTributario);
/* 2569:2740 */           if (creditoTributarioSRI == null)
/* 2570:     */           {
/* 2571:2741 */             HashMap<String, String> filters = new HashMap();
/* 2572:2742 */             filters.put("codigo", codigoSustentoTributario);
/* 2573:2743 */             if ((codigoSustentoTributario != null) && (!codigoSustentoTributario.equals("")))
/* 2574:     */             {
/* 2575:2744 */               if (this.servicioCreditoTributario.obtenerListaCombo("codigo", false, filters).size() == 0) {
/* 2576:2745 */                 throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.CODIGO_SUSTENTO_TRIBUTARIO", new String[] { codigoSustentoTributario, "", "" });
/* 2577:     */               }
/* 2578:     */             }
/* 2579:     */             else
/* 2580:     */             {
/* 2581:2748 */               creditoTributarioSRI = (CreditoTributarioSRI)this.servicioCreditoTributario.obtenerListaCombo("codigo", false, filters).get(0);
/* 2582:2749 */               hmCreditoTributario.put(codigoSustentoTributario, creditoTributarioSRI);
/* 2583:     */             }
/* 2584:     */           }
/* 2585:2752 */           facturaProveedor.getFacturaProveedorSRI().setDocumento(documentoRetencion);
/* 2586:2753 */           facturaProveedor.getFacturaProveedorSRI().setCreditoTributarioSRI(creditoTributarioSRI);
/* 2587:     */           
/* 2588:2755 */           this.servicioFacturaProveedor.totalizar(facturaProveedor);
/* 2589:2756 */           ConceptoRetencionSRI conceptoRetencionSRIFuente = (ConceptoRetencionSRI)hmConceptoRetencionSRIFuente.get(codigoConceptoRetencionFuente);
/* 2590:2757 */           analizaConcepto(conceptoRetencionSRIFuente, codigoConceptoRetencionFuente);
/* 2591:2758 */           ConceptoRetencionSRI conceptoRetencionSRIIVA = (ConceptoRetencionSRI)hmConceptoRetencionSRIIVA.get(porcentajeRetencionIVA);
/* 2592:2759 */           analizaConcepto(conceptoRetencionSRIIVA, porcentajeRetencionIVA.toString());
/* 2593:2760 */           ConceptoRetencionSRI conceptoRetencionSRIFuenteDiferencia = (ConceptoRetencionSRI)hmConceptoRetencionSRIFuente.get("332");
/* 2594:2761 */           analizaConcepto(conceptoRetencionSRIFuenteDiferencia, "332");
/* 2595:2762 */           ConceptoRetencionSRI conceptoRetencionSRIIVADiferencia = (ConceptoRetencionSRI)hmConceptoRetencionSRIFuente.get("7");
/* 2596:2763 */           analizaConcepto(conceptoRetencionSRIIVADiferencia, "7");
/* 2597:2764 */           listaRetencion(baseImponibleRetencionIVA, conceptoRetencionSRIIVA, conceptoRetencionSRIIVADiferencia, porcentajeRetencionIVA, baseImponibleRetencionFuente, conceptoRetencionSRIFuente, conceptoRetencionSRIFuenteDiferencia, porcentajeRetencionFuente, facturaProveedor);
/* 2598:     */           
/* 2599:     */ 
/* 2600:     */ 
/* 2601:2768 */           facturaProveedor.setIndicadorConRetencion(true);
/* 2602:     */         }
/* 2603:2773 */         filaActual++;
/* 2604:     */       }
/* 2605:2776 */       for (??? = hmFacturaProveedo.values().iterator(); ((Iterator)???).hasNext();)
/* 2606:     */       {
/* 2607:2776 */         FacturaProveedor fp = (FacturaProveedor)((Iterator)???).next();
/* 2608:2777 */         this.servicioFacturaProveedor.totalizar(fp);
/* 2609:2778 */         this.servicioFacturaProveedor.generarCuentaPorPagar(fp);
/* 2610:2779 */         this.servicioFacturaProveedorSRI.actualizarFacturaProveedorSRI(fp);
/* 2611:2780 */         if (fp.getFacturaProveedorSRI() != null) {
/* 2612:2782 */           retornarNumeroFactura = fp.getFacturaProveedorSRI().getEstablecimiento() + "-" + fp.getFacturaProveedorSRI().getPuntoEmision() + "-" + fp.getFacturaProveedorSRI().getNumero();
/* 2613:     */         }
/* 2614:2784 */         this.servicioFacturaProveedor.guardar(fp);
/* 2615:     */         
/* 2616:2786 */         listaFacturaProveedor.add(fp);
/* 2617:     */       }
/* 2618:2789 */       return listaFacturaProveedor;
/* 2619:     */     }
/* 2620:     */     catch (AS2Exception e)
/* 2621:     */     {
/* 2622:2792 */       e.printStackTrace();
/* 2623:2793 */       LOG.error("Error al migrar factura proveedor", e);
/* 2624:2794 */       this.context.setRollbackOnly();
/* 2625:2795 */       throw e;
/* 2626:     */     }
/* 2627:     */     catch (IllegalArgumentException e)
/* 2628:     */     {
/* 2629:2797 */       LOG.info("Error al migrar factura proveedor", e);
/* 2630:2798 */       this.context.setRollbackOnly();
/* 2631:     */       
/* 2632:2800 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 2633:     */     }
/* 2634:     */     catch (IllegalStateException e)
/* 2635:     */     {
/* 2636:2802 */       LOG.info("Error al migrar asiento", e);
/* 2637:2803 */       this.context.setRollbackOnly();
/* 2638:     */       
/* 2639:2805 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 2640:     */     }
/* 2641:     */     catch (ExcepcionAS2Financiero e)
/* 2642:     */     {
/* 2643:2807 */       LOG.error("Error al migrar factura proveedor", e);
/* 2644:2808 */       this.context.setRollbackOnly();
/* 2645:2809 */       throw new ExcepcionAS2Financiero("msg_periodo_no_encontrado", " : " + retornarNumeroFactura);
/* 2646:     */     }
/* 2647:     */     catch (ExcepcionAS2Compras e)
/* 2648:     */     {
/* 2649:2811 */       LOG.error("Error al migrar factura proveedor", e);
/* 2650:2812 */       LOG.info("Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: ");
/* 2651:2813 */       this.context.setRollbackOnly();
/* 2652:2814 */       throw e;
/* 2653:     */     }
/* 2654:     */     catch (ExcepcionAS2 e)
/* 2655:     */     {
/* 2656:2816 */       LOG.error("Error al migrar factura proveedor", e);
/* 2657:2817 */       LOG.info("Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + (filaErronea[columnaErronea] != null ? filaErronea[columnaErronea]
/* 2658:2818 */         .toString() : ""));
/* 2659:2819 */       this.context.setRollbackOnly();
/* 2660:2820 */       throw e;
/* 2661:     */     }
/* 2662:     */     catch (Exception e)
/* 2663:     */     {
/* 2664:2822 */       e.printStackTrace();
/* 2665:2823 */       LOG.error("Error al migrar factura proveedor", e);
/* 2666:2824 */       LOG.info("Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + (filaErronea[columnaErronea] != null ? filaErronea[columnaErronea]
/* 2667:2825 */         .toString() : ""));
/* 2668:2826 */       this.context.setRollbackOnly();
/* 2669:2827 */       throw new ExcepcionAS2(e);
/* 2670:     */     }
/* 2671:     */   }
/* 2672:     */   
/* 2673:     */   public void codigoFormaPagoSRI(FacturaProveedor facturaProveedor, List<CodigoFormaPagoSRI> listaFPS, String identificacion, HashMap<String, String> hmProveedorFPS)
/* 2674:     */     throws AS2Exception
/* 2675:     */   {
/* 2676:2835 */     String codigoProveedorFPS = (String)hmProveedorFPS.get(identificacion);
/* 2677:2837 */     if (codigoProveedorFPS == null)
/* 2678:     */     {
/* 2679:2838 */       List<FormaPagoSRI> listaFormaPagoSRI = this.servicioFormaPagoSRI.getListaFormaPagoSRI(facturaProveedor.getEmpresa());
/* 2680:2840 */       if ((listaFormaPagoSRI.isEmpty()) && ((listaFPS == null) || (listaFPS.isEmpty()))) {
/* 2681:2841 */         throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.CONFIGURAR_FORMA_PAGO_SRI", new String[] { identificacion });
/* 2682:     */       }
/* 2683:2843 */       if (!listaFormaPagoSRI.isEmpty())
/* 2684:     */       {
/* 2685:2844 */         facturaProveedor.getFacturaProveedorSRI().setCodigoFormaPagoSRI(((FormaPagoSRI)listaFormaPagoSRI.get(0)).getCodigo());
/* 2686:2845 */         hmProveedorFPS.put(identificacion, ((FormaPagoSRI)listaFormaPagoSRI.get(0)).getCodigo());
/* 2687:     */       }
/* 2688:     */       else
/* 2689:     */       {
/* 2690:2847 */         facturaProveedor.getFacturaProveedorSRI().setCodigoFormaPagoSRI(((CodigoFormaPagoSRI)listaFPS.get(0)).getCodigo());
/* 2691:     */       }
/* 2692:     */     }
/* 2693:     */     else
/* 2694:     */     {
/* 2695:2851 */       facturaProveedor.getFacturaProveedorSRI().setCodigoFormaPagoSRI(codigoProveedorFPS);
/* 2696:     */     }
/* 2697:     */   }
/* 2698:     */   
/* 2699:     */   public void analizaConcepto(ConceptoRetencionSRI conceptoRetencionSRI, String codigoConcepto)
/* 2700:     */     throws AS2Exception
/* 2701:     */   {
/* 2702:2857 */     if (conceptoRetencionSRI == null) {
/* 2703:2858 */       throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.CODIGO_CONCEPTO_RETENCION", new String[] { codigoConcepto, "", "" });
/* 2704:     */     }
/* 2705:     */   }
/* 2706:     */   
/* 2707:     */   public void verificaValorMayor(BigDecimal valorMigracionFuente, BigDecimal valorTotalizacionFuente, BigDecimal valorMigracionIVA, BigDecimal valorTotalizacionIVA)
/* 2708:     */     throws AS2Exception
/* 2709:     */   {
/* 2710:2864 */     if ((valorMigracionFuente.compareTo(valorTotalizacionFuente) == 1) || (valorMigracionIVA.compareTo(valorTotalizacionIVA) == 1))
/* 2711:     */     {
/* 2712:2865 */       BigDecimal valorMayor = BigDecimal.ZERO;
/* 2713:2866 */       BigDecimal valorComparado = BigDecimal.ZERO;
/* 2714:2867 */       if (valorMigracionFuente.compareTo(valorTotalizacionFuente) == 1)
/* 2715:     */       {
/* 2716:2868 */         valorMayor = valorMigracionFuente;
/* 2717:2869 */         valorComparado = valorTotalizacionFuente;
/* 2718:     */       }
/* 2719:     */       else
/* 2720:     */       {
/* 2721:2871 */         valorMayor = valorMigracionIVA;
/* 2722:2872 */         valorComparado = valorTotalizacionIVA;
/* 2723:     */       }
/* 2724:2875 */       throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.ERROR_VALOR_MAYOR_MIGRACION", new String[] { valorMayor.toString(), valorComparado.toString() });
/* 2725:     */     }
/* 2726:     */   }
/* 2727:     */   
/* 2728:     */   public void migracionVersionListaPrecios(Date fechaDesde, int idListaPrecios, String fileName, InputStream imInputStream, int filaInicial, int idOrganizacion, boolean indicadorListaPrecioPorZona)
/* 2729:     */     throws ExcepcionAS2
/* 2730:     */   {
/* 2731:     */     try
/* 2732:     */     {
/* 2733:2885 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/* 2734:     */       
/* 2735:2887 */       int secuencial = 1;
/* 2736:     */       
/* 2737:2889 */       ListaPrecios listaPrecios = this.servicioListaPrecios.cargarDetalle(idListaPrecios);
/* 2738:2891 */       for (VersionListaPrecios versionListaPrecios : listaPrecios.getVersionesListaPrecios()) {
/* 2739:2892 */         if (versionListaPrecios.getValidoHasta() == null)
/* 2740:     */         {
/* 2741:2893 */           versionListaPrecios.setValidoHasta(fechaDesde);
/* 2742:2894 */           versionListaPrecios.setActivo(false);
/* 2743:     */         }
/* 2744:     */       }
/* 2745:2898 */       VersionListaPrecios versionListaPrecios = null;
/* 2746:     */       
/* 2747:2900 */       HashMap<String, Producto> hashMapProducto = new HashMap();
/* 2748:2901 */       for (Producto p : this.servicioProducto.obtenerListaCombo("", false, null)) {
/* 2749:2902 */         hashMapProducto.put(p.getCodigo(), p);
/* 2750:     */       }
/* 2751:2905 */       Object listaZonas = new ArrayList();
/* 2752:     */       
/* 2753:2907 */       Zona z = new Zona();
/* 2754:2908 */       z.setId(0);
/* 2755:2909 */       z.setCodigo("0");
/* 2756:2910 */       z.setNombre("Sin Zona");
/* 2757:2911 */       z.setActivo(true);
/* 2758:2912 */       ((List)listaZonas).add(z);
/* 2759:2913 */       if (indicadorListaPrecioPorZona) {
/* 2760:2914 */         ((List)listaZonas).addAll(this.servicioZona.obtenerListaCombo("", false, null));
/* 2761:     */       }
/* 2762:2916 */       HashMap<Integer, Zona> hashMapZona = new HashMap();
/* 2763:2917 */       for (Zona z1 : (List)listaZonas) {
/* 2764:2918 */         hashMapZona.put(Integer.valueOf(z1.getIdZona()), z1);
/* 2765:     */       }
/* 2766:2922 */       for (int j = 5; j < datos[0].length; j++)
/* 2767:     */       {
/* 2768:2924 */         int celdaIdZona = Integer.parseInt(datos[0][j].getStringCellValue().trim());
/* 2769:2925 */         Zona zona = null;
/* 2770:2927 */         if (!listaPrecios.isIndicadorCompra()) {
/* 2771:2928 */           zona = (Zona)hashMapZona.get(Integer.valueOf(celdaIdZona));
/* 2772:     */         }
/* 2773:2931 */         versionListaPrecios = new VersionListaPrecios();
/* 2774:2932 */         versionListaPrecios.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 2775:2933 */         versionListaPrecios.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 2776:2934 */         versionListaPrecios.setCodigo(secuencial + FuncionesUtiles.dateToString(fechaDesde).replace("/", ""));
/* 2777:2935 */         versionListaPrecios.setNombre("ListaPrecios_" + secuencial + "_" + FuncionesUtiles.dateToString(fechaDesde));
/* 2778:2936 */         versionListaPrecios.setZona((zona != null) && (zona.getIdZona() == 0) ? null : zona);
/* 2779:2937 */         versionListaPrecios.setValidoDesde(fechaDesde);
/* 2780:2938 */         versionListaPrecios.setDetalleVersionesListaPrecios(new ArrayList());
/* 2781:2939 */         versionListaPrecios.setListaPrecios(listaPrecios);
/* 2782:2940 */         listaPrecios.getVersionesListaPrecios().add(versionListaPrecios);
/* 2783:2941 */         versionListaPrecios.setActivo(true);
/* 2784:2942 */         secuencial++;
/* 2785:2944 */         for (int i = 2; i < datos.length; i++)
/* 2786:     */         {
/* 2787:2946 */           String celdaCodigoProducto = datos[i][0] == null ? "" : datos[i][0].getStringCellValue().trim();
/* 2788:     */           
/* 2789:2948 */           BigDecimal celdaPrecioProducto = datos[i][j] == null ? BigDecimal.ZERO : FuncionesUtiles.redondearBigDecimal(new BigDecimal(datos[i][j].getNumericCellValue()), 4);
/* 2790:2950 */           if ((hashMapProducto.containsKey(celdaCodigoProducto)) && (celdaPrecioProducto.compareTo(BigDecimal.ZERO) != 0))
/* 2791:     */           {
/* 2792:2952 */             Producto producto = (Producto)hashMapProducto.get(celdaCodigoProducto);
/* 2793:2953 */             DetalleVersionListaPrecios detalleVersionListaPrecios = new DetalleVersionListaPrecios();
/* 2794:2954 */             detalleVersionListaPrecios.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 2795:2955 */             detalleVersionListaPrecios.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 2796:2956 */             detalleVersionListaPrecios.setProducto(producto);
/* 2797:2957 */             detalleVersionListaPrecios.setPrecioUnitario(celdaPrecioProducto);
/* 2798:2958 */             detalleVersionListaPrecios.setVersionListaPrecios(versionListaPrecios);
/* 2799:2959 */             versionListaPrecios.getDetalleVersionesListaPrecios().add(detalleVersionListaPrecios);
/* 2800:     */           }
/* 2801:     */         }
/* 2802:     */       }
/* 2803:2965 */       this.servicioListaPrecios.guardar(listaPrecios);
/* 2804:     */     }
/* 2805:     */     catch (IllegalArgumentException e)
/* 2806:     */     {
/* 2807:2968 */       LOG.info("Error al migrar version de lista de precios", e);
/* 2808:2969 */       this.context.setRollbackOnly();
/* 2809:2970 */       throw new ExcepcionAS2("msg_error_formato_incorrecto");
/* 2810:     */     }
/* 2811:     */     catch (IllegalStateException e)
/* 2812:     */     {
/* 2813:2972 */       LOG.info("Error al migrar version de lista de precios", e);
/* 2814:2973 */       this.context.setRollbackOnly();
/* 2815:2974 */       throw new ExcepcionAS2("msg_error_formato_incorrecto");
/* 2816:     */     }
/* 2817:     */     catch (Exception e)
/* 2818:     */     {
/* 2819:2976 */       LOG.info("Error al migrar version de lista de precios", e);
/* 2820:2977 */       this.context.setRollbackOnly();
/* 2821:2978 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 2822:     */     }
/* 2823:     */   }
/* 2824:     */   
/* 2825:     */   public void subidaRubrosVariablesEmpleado(PagoRol pagoRol, int idRubro, String fileName, InputStream imInputStream, int filaInicial)
/* 2826:     */     throws IOException
/* 2827:     */   {
/* 2828:2991 */     HSSFCell[][] datos = FuncionesUtiles.leerExcel2(fileName, imInputStream, filaInicial, 0);
/* 2829:     */     
/* 2830:2993 */     List<PagoRolEmpleadoRubro> listaPagoRolEmpeladoRubro = this.servicioPagoRolEmpleado.obtenerListaPorPagoRolRubro(pagoRol.getIdPagoRol(), idRubro);
/* 2831:     */     
/* 2832:2995 */     HashMap<Integer, PagoRolEmpleadoRubro> hm = new HashMap();
/* 2833:2997 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : listaPagoRolEmpeladoRubro) {
/* 2834:2999 */       if (!pagoRolEmpleadoRubro.isIndicadorAutomatico()) {
/* 2835:3000 */         hm.put(Integer.valueOf(pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getIdEmpleado()), pagoRolEmpleadoRubro);
/* 2836:     */       }
/* 2837:     */     }
/* 2838:3003 */     for (int i = 2; i < datos.length; i++)
/* 2839:     */     {
/* 2840:3005 */       int celdaIdEmpleado = Integer.parseInt(datos[i][0] == null ? "0" : datos[i][0].getStringCellValue().trim());
/* 2841:3006 */       BigDecimal celdaValor = BigDecimal.valueOf(datos[i][4] == null ? 0.0D : datos[i][4].getNumericCellValue());
/* 2842:3008 */       if (hm.containsKey(Integer.valueOf(celdaIdEmpleado)))
/* 2843:     */       {
/* 2844:3009 */         PagoRolEmpleadoRubro pagoRolEmpleadoRubro = (PagoRolEmpleadoRubro)hm.get(Integer.valueOf(celdaIdEmpleado));
/* 2845:3010 */         if (pagoRolEmpleadoRubro.getRubro().isIndicadorTiempo()) {
/* 2846:3011 */           pagoRolEmpleadoRubro.setTiempo(celdaValor);
/* 2847:     */         } else {
/* 2848:3013 */           pagoRolEmpleadoRubro.setValor(celdaValor);
/* 2849:     */         }
/* 2850:     */       }
/* 2851:     */     }
/* 2852:3018 */     this.servicioPagoRolEmpleadoRubro.guardar(listaPagoRolEmpeladoRubro, pagoRol);
/* 2853:     */   }
/* 2854:     */   
/* 2855:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 2856:     */   public void migracionVacacionEmpleado(InputStream imInputStream, int filaInicial)
/* 2857:     */     throws ExcepcionAS2Nomina, ExcepcionAS2
/* 2858:     */   {
/* 2859:3032 */     HashMap<Integer, HashMap<Date, Vacacion>> hmlistaVacaciones = new HashMap();
/* 2860:3033 */     for (Vacacion vacacion : this.servicioVacacion.obtenerListaCombo("", false, null))
/* 2861:     */     {
/* 2862:3034 */       int idHistoricoEmpleado = vacacion.getHistoricoEmpleado().getIdHistoricoEmpleado();
/* 2863:3035 */       HashMap<Date, Vacacion> hmVacaciones = (HashMap)hmlistaVacaciones.get(Integer.valueOf(idHistoricoEmpleado));
/* 2864:3036 */       if (hmVacaciones == null)
/* 2865:     */       {
/* 2866:3037 */         hmVacaciones = new HashMap();
/* 2867:3038 */         hmlistaVacaciones.put(Integer.valueOf(idHistoricoEmpleado), hmVacaciones);
/* 2868:     */       }
/* 2869:3040 */       hmVacaciones.put(vacacion.getFechaInicioPeriodo(), vacacion);
/* 2870:     */     }
/* 2871:3044 */     Vacacion vacacion = null;
/* 2872:3045 */     HashMap<String, HistoricoEmpleado> hmHistoricoEmpleado = new HashMap();
/* 2873:3046 */     int filaActual = filaInicial;
/* 2874:3047 */     int columnaActual = 0;
/* 2875:3048 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 2876:     */     try
/* 2877:     */     {
/* 2878:3051 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 2879:3053 */       for (HSSFCell[] fila : datos)
/* 2880:     */       {
/* 2881:3055 */         filaErronea = fila;
/* 2882:3056 */         filaActual++;
/* 2883:     */         
/* 2884:     */ 
/* 2885:3059 */         String identificacion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 0, true, Integer.valueOf(1), Integer.valueOf(20));
/* 2886:3060 */         Empleado empleado = this.servicioEmpleado.bucarEmpleadoPorIdentificacion(identificacion, AppUtil.getOrganizacion().getId());
/* 2887:3061 */         HistoricoEmpleado historicoEmpleado = (HistoricoEmpleado)hmHistoricoEmpleado.get(identificacion);
/* 2888:3062 */         if (empleado == null) {
/* 2889:3063 */           throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + fila[0].getStringCellValue().toString());
/* 2890:     */         }
/* 2891:3066 */         if (historicoEmpleado == null)
/* 2892:     */         {
/* 2893:     */           try
/* 2894:     */           {
/* 2895:3069 */             historicoEmpleado = this.servicioHistoricoEmpleado.obtenerPeriodoActivo(empleado.getId(), new Date());
/* 2896:     */           }
/* 2897:     */           catch (ExcepcionAS2Nomina e)
/* 2898:     */           {
/* 2899:3071 */             throw new ExcepcionAS2Nomina("msg_error_error_periodo_activo", " " + identificacion);
/* 2900:     */           }
/* 2901:3074 */           if (historicoEmpleado == null) {
/* 2902:3075 */             throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + String.valueOf(fila[0].getStringCellValue()));
/* 2903:     */           }
/* 2904:3077 */           hmHistoricoEmpleado.put(identificacion, historicoEmpleado);
/* 2905:     */         }
/* 2906:3082 */         Date fechaInicioPeriodo = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 1, true, Integer.valueOf(0), Integer.valueOf(0));
/* 2907:     */         
/* 2908:3084 */         Date fechaFinPeriodo = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaActual = 2, true, Integer.valueOf(0), Integer.valueOf(0));
/* 2909:     */         
/* 2910:     */ 
/* 2911:3087 */         int dias = ((BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 3, true, Integer.valueOf(0), Integer.valueOf(0))).intValue();
/* 2912:     */         
/* 2913:     */ 
/* 2914:3090 */         int diasAdicionales = ((BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 4, true, Integer.valueOf(0), Integer.valueOf(0))).intValue();
/* 2915:     */         
/* 2916:     */ 
/* 2917:3093 */         int diasTomados = ((BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 5, true, Integer.valueOf(0), Integer.valueOf(0))).intValue();
/* 2918:3095 */         if (dias + diasAdicionales >= diasTomados)
/* 2919:     */         {
/* 2920:3097 */           HashMap<Date, Vacacion> hmVacaciones = (HashMap)hmlistaVacaciones.get(Integer.valueOf(historicoEmpleado.getId()));
/* 2921:3098 */           if (hmVacaciones == null) {
/* 2922:3099 */             hmVacaciones = new HashMap();
/* 2923:     */           }
/* 2924:3102 */           vacacion = (Vacacion)hmVacaciones.get(fechaInicioPeriodo);
/* 2925:3103 */           if (vacacion == null)
/* 2926:     */           {
/* 2927:3105 */             vacacion = new Vacacion();
/* 2928:3106 */             vacacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 2929:3107 */             vacacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 2930:3108 */             vacacion.setDias(dias);
/* 2931:3109 */             vacacion.setDiasAdicionales(diasAdicionales);
/* 2932:3110 */             vacacion.setDiasTomados(0);
/* 2933:3111 */             vacacion.setFechaInicioPeriodo(fechaInicioPeriodo);
/* 2934:3112 */             vacacion.setFechaFinPeriodo(fechaFinPeriodo);
/* 2935:3113 */             vacacion.setHistoricoEmpleado(historicoEmpleado);
/* 2936:3114 */             this.servicioVacacion.guardar(vacacion);
/* 2937:3115 */             hmVacaciones.put(fechaInicioPeriodo, vacacion);
/* 2938:3116 */             hmlistaVacaciones.put(Integer.valueOf(historicoEmpleado.getId()), hmVacaciones);
/* 2939:3118 */             if (diasTomados != 0)
/* 2940:     */             {
/* 2941:3119 */               DetalleVacacion detalleVacacion = new DetalleVacacion();
/* 2942:3120 */               detalleVacacion.setIdOrganizacion(vacacion.getIdOrganizacion());
/* 2943:3121 */               detalleVacacion.setIdSucursal(vacacion.getIdSucursal());
/* 2944:3122 */               detalleVacacion.setDiasTomados(diasTomados);
/* 2945:3123 */               detalleVacacion.setFechaInicio(vacacion.getFechaInicioPeriodo());
/* 2946:3124 */               detalleVacacion.setFechaFin(
/* 2947:3125 */                 FuncionesUtiles.sumarFechaDiasMeses(detalleVacacion.getFechaInicio(), detalleVacacion.getDiasTomados() - 1));
/* 2948:3126 */               detalleVacacion.setEstado(Estado.APROBADO);
/* 2949:3127 */               detalleVacacion.setVacacion(vacacion);
/* 2950:3128 */               detalleVacacion.setDocumento((Documento)this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.SOLICITUD_VACACION).get(0));
/* 2951:3129 */               this.servicioDetalleVacacion.guardar(detalleVacacion);
/* 2952:3130 */               this.servicioDetalleVacacion.cerrarVacacion(detalleVacacion);
/* 2953:     */             }
/* 2954:     */           }
/* 2955:     */         }
/* 2956:     */         else
/* 2957:     */         {
/* 2958:3136 */           System.out.println("Los dias tomado no puede ser mayor a la suma de los dias  + dias adcionales de la vacacion");
/* 2959:     */         }
/* 2960:     */       }
/* 2961:     */     }
/* 2962:     */     catch (IllegalStateException e)
/* 2963:     */     {
/* 2964:3142 */       LOG.info("Error al migrar productos", e);
/* 2965:3143 */       this.context.setRollbackOnly();
/* 2966:     */       
/* 2967:3145 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 2968:     */     }
/* 2969:     */     catch (ExcepcionAS2 e)
/* 2970:     */     {
/* 2971:3147 */       LOG.info("Error al migrar productos", e);
/* 2972:3148 */       this.context.setRollbackOnly();
/* 2973:3149 */       throw e;
/* 2974:     */     }
/* 2975:     */     catch (Exception e)
/* 2976:     */     {
/* 2977:3151 */       LOG.error("Error al migrar productos", e);
/* 2978:3152 */       e.printStackTrace();
/* 2979:3153 */       this.context.setRollbackOnly();
/* 2980:     */       
/* 2981:3155 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString(), e);
/* 2982:     */     }
/* 2983:     */   }
/* 2984:     */   
/* 2985:     */   public void migracionLote(String fileName, InputStream imInputStream, int filaInicial, int idOrganizacion, int idSucursal, OrganizacionConfiguracion organizacionConfiguracion)
/* 2986:     */     throws ExcepcionAS2, AS2Exception
/* 2987:     */   {
/* 2988:3163 */     int filaActual = filaInicial;
/* 2989:3164 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 2990:3165 */     int columnaErronea = -1;
/* 2991:     */     try
/* 2992:     */     {
/* 2993:3169 */       HashMap<String, Producto> hmProducto = new HashMap();
/* 2994:3170 */       HashMap<String, Lote> hmLote = new HashMap();
/* 2995:3171 */       HashMap<String, HashMap<String, ValorAtributo>> hmAtributo = new HashMap();
/* 2996:     */       
/* 2997:     */ 
/* 2998:3174 */       HashMap<String, String> filters = new HashMap();
/* 2999:3175 */       filters.put("numeroAtributos", "" + organizacionConfiguracion.getNumeroAtributos());
/* 3000:3176 */       for (Producto producto : this.servicioProducto.obtenerListaCombo("nombre", true, filters)) {
/* 3001:3177 */         hmProducto.put(producto.getCodigo(), producto);
/* 3002:     */       }
/* 3003:3180 */       for (Lote lote : this.servicioLote.obtenerListaCombo("codigo", true, null)) {
/* 3004:3181 */         hmLote.put(lote.getCodigo(), lote);
/* 3005:     */       }
/* 3006:3185 */       if (organizacionConfiguracion.getNumeroAtributos() > 0)
/* 3007:     */       {
/* 3008:3186 */         if (organizacionConfiguracion.getAtributo1() != null)
/* 3009:     */         {
/* 3010:3187 */           Object hmValorAtributo = new HashMap();
/* 3011:     */           
/* 3012:3189 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo1().getId()).getListaValorAtributo();
/* 3013:3190 */           for (ValorAtributo valorAtributo : lista) {
/* 3014:3191 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3015:     */           }
/* 3016:3193 */           System.out.println("1_hmValorAtributo----------------->" + ((HashMap)hmValorAtributo).size());
/* 3017:3194 */           hmAtributo.put(organizacionConfiguracion.getAtributo1().getCodigo(), hmValorAtributo);
/* 3018:     */         }
/* 3019:3196 */         if (organizacionConfiguracion.getAtributo2() != null)
/* 3020:     */         {
/* 3021:3197 */           Object hmValorAtributo = new HashMap();
/* 3022:     */           
/* 3023:3199 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo2().getId()).getListaValorAtributo();
/* 3024:3200 */           for (ValorAtributo valorAtributo : lista) {
/* 3025:3201 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3026:     */           }
/* 3027:3203 */           System.out.println("2_hmValorAtributo----------------->" + ((HashMap)hmValorAtributo).size());
/* 3028:3204 */           hmAtributo.put(organizacionConfiguracion.getAtributo2().getCodigo(), hmValorAtributo);
/* 3029:     */         }
/* 3030:3206 */         if (organizacionConfiguracion.getAtributo3() != null)
/* 3031:     */         {
/* 3032:3207 */           Object hmValorAtributo = new HashMap();
/* 3033:     */           
/* 3034:3209 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo3().getId()).getListaValorAtributo();
/* 3035:3210 */           for (ValorAtributo valorAtributo : lista) {
/* 3036:3211 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3037:     */           }
/* 3038:3213 */           System.out.println("3_hmValorAtributo----------------->" + ((HashMap)hmValorAtributo).size());
/* 3039:3214 */           hmAtributo.put(organizacionConfiguracion.getAtributo3().getCodigo(), hmValorAtributo);
/* 3040:     */         }
/* 3041:3216 */         if (organizacionConfiguracion.getAtributo4() != null)
/* 3042:     */         {
/* 3043:3217 */           Object hmValorAtributo = new HashMap();
/* 3044:     */           
/* 3045:3219 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo4().getId()).getListaValorAtributo();
/* 3046:3220 */           for (ValorAtributo valorAtributo : lista) {
/* 3047:3221 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3048:     */           }
/* 3049:3223 */           System.out.println("4_hmValorAtributo----------------->" + ((HashMap)hmValorAtributo).size());
/* 3050:3224 */           hmAtributo.put(organizacionConfiguracion.getAtributo4().getCodigo(), hmValorAtributo);
/* 3051:     */         }
/* 3052:3226 */         if (organizacionConfiguracion.getAtributo5() != null)
/* 3053:     */         {
/* 3054:3227 */           Object hmValorAtributo = new HashMap();
/* 3055:     */           
/* 3056:3229 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo5().getId()).getListaValorAtributo();
/* 3057:3230 */           for (ValorAtributo valorAtributo : lista) {
/* 3058:3231 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3059:     */           }
/* 3060:3233 */           hmAtributo.put(organizacionConfiguracion.getAtributo5().getCodigo(), hmValorAtributo);
/* 3061:     */         }
/* 3062:3235 */         if (organizacionConfiguracion.getAtributo6() != null)
/* 3063:     */         {
/* 3064:3236 */           Object hmValorAtributo = new HashMap();
/* 3065:     */           
/* 3066:3238 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo6().getId()).getListaValorAtributo();
/* 3067:3239 */           for (ValorAtributo valorAtributo : lista) {
/* 3068:3240 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3069:     */           }
/* 3070:3242 */           hmAtributo.put(organizacionConfiguracion.getAtributo6().getCodigo(), hmValorAtributo);
/* 3071:     */         }
/* 3072:3244 */         if (organizacionConfiguracion.getAtributo7() != null)
/* 3073:     */         {
/* 3074:3245 */           Object hmValorAtributo = new HashMap();
/* 3075:     */           
/* 3076:3247 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo7().getId()).getListaValorAtributo();
/* 3077:3248 */           for (ValorAtributo valorAtributo : lista) {
/* 3078:3249 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3079:     */           }
/* 3080:3251 */           hmAtributo.put(organizacionConfiguracion.getAtributo7().getCodigo(), hmValorAtributo);
/* 3081:     */         }
/* 3082:3253 */         if (organizacionConfiguracion.getAtributo8() != null)
/* 3083:     */         {
/* 3084:3254 */           Object hmValorAtributo = new HashMap();
/* 3085:     */           
/* 3086:3256 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo8().getId()).getListaValorAtributo();
/* 3087:3257 */           for (ValorAtributo valorAtributo : lista) {
/* 3088:3258 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3089:     */           }
/* 3090:3260 */           hmAtributo.put(organizacionConfiguracion.getAtributo8().getCodigo(), hmValorAtributo);
/* 3091:     */         }
/* 3092:3262 */         if (organizacionConfiguracion.getAtributo9() != null)
/* 3093:     */         {
/* 3094:3263 */           Object hmValorAtributo = new HashMap();
/* 3095:     */           
/* 3096:3265 */           List<ValorAtributo> lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo9().getId()).getListaValorAtributo();
/* 3097:3266 */           for (ValorAtributo valorAtributo : lista) {
/* 3098:3267 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3099:     */           }
/* 3100:3269 */           hmAtributo.put(organizacionConfiguracion.getAtributo9().getCodigo(), hmValorAtributo);
/* 3101:     */         }
/* 3102:3271 */         if (organizacionConfiguracion.getAtributo10() != null)
/* 3103:     */         {
/* 3104:3272 */           Object hmValorAtributo = new HashMap();
/* 3105:     */           
/* 3106:3274 */           lista = this.servicioAtributo.cargarDetalle(organizacionConfiguracion.getAtributo10().getId()).getListaValorAtributo();
/* 3107:3275 */           for (??? = lista.iterator(); ???.hasNext();)
/* 3108:     */           {
/* 3109:3275 */             valorAtributo = (ValorAtributo)???.next();
/* 3110:3276 */             ((HashMap)hmValorAtributo).put(valorAtributo.getCodigo(), valorAtributo);
/* 3111:     */           }
/* 3112:3278 */           hmAtributo.put(organizacionConfiguracion.getAtributo10().getCodigo(), hmValorAtributo);
/* 3113:     */         }
/* 3114:     */       }
/* 3115:3283 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(AppUtil.getOrganizacion().getId(), fileName, imInputStream, filaInicial, 0);
/* 3116:     */       
/* 3117:3285 */       List<ValorAtributo> lista = datos;ValorAtributo localValorAtributo1 = lista.length;
/* 3118:3285 */       for (ValorAtributo valorAtributo = 0; valorAtributo < localValorAtributo1; valorAtributo++)
/* 3119:     */       {
/* 3120:3285 */         HSSFCell[] fila = lista[valorAtributo];
/* 3121:     */         
/* 3122:3287 */         filaErronea = fila;
/* 3123:     */         
/* 3124:3289 */         columnaErronea = 0;
/* 3125:3290 */         String codigoLote = fila[0] != null ? fila[0].getStringCellValue().trim() : "";
/* 3126:3291 */         columnaErronea = 1;
/* 3127:3292 */         String codigoProducto = fila[1] != null ? fila[1].getStringCellValue().trim() : "";
/* 3128:3293 */         columnaErronea = 2;
/* 3129:3294 */         boolean movimientoInterno = fila[2].getStringCellValue().equals("SI");
/* 3130:3295 */         columnaErronea = 3;
/* 3131:3296 */         Date fechaFabricacion = fila[3].getDateCellValue();
/* 3132:3297 */         columnaErronea = 4;
/* 3133:3298 */         Date fechaCaducidad = fila[4].getDateCellValue();
/* 3134:     */         
/* 3135:3300 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 3136:3301 */         if (producto == null)
/* 3137:     */         {
/* 3138:3302 */           ExcepcionAS2 e = new ExcepcionAS2("msg_info_no_existe_producto", " " + codigoProducto);
/* 3139:3303 */           throw e;
/* 3140:     */         }
/* 3141:3307 */         ValorAtributo valorAtributo1 = null;
/* 3142:3308 */         ValorAtributo valorAtributo2 = null;
/* 3143:3309 */         ValorAtributo valorAtributo3 = null;
/* 3144:3310 */         ValorAtributo valorAtributo4 = null;
/* 3145:3311 */         ValorAtributo valorAtributo5 = null;
/* 3146:3312 */         ValorAtributo valorAtributo6 = null;
/* 3147:3313 */         ValorAtributo valorAtributo7 = null;
/* 3148:3314 */         ValorAtributo valorAtributo8 = null;
/* 3149:3315 */         ValorAtributo valorAtributo9 = null;
/* 3150:3316 */         ValorAtributo valorAtributo10 = null;
/* 3151:3318 */         if (organizacionConfiguracion.getNumeroAtributos() > 0)
/* 3152:     */         {
/* 3153:3320 */           if (organizacionConfiguracion.getAtributo1() != null)
/* 3154:     */           {
/* 3155:3321 */             columnaErronea = 5;
/* 3156:3322 */             String codigoValorAtributo = fila[5] == null ? "" : fila[5].getStringCellValue();
/* 3157:3323 */             valorAtributo1 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo1().getCodigo())).get(codigoValorAtributo);
/* 3158:3324 */             valorAtributo1 = producto.getAtributoProduccion1() == null ? null : valorAtributo1;
/* 3159:     */           }
/* 3160:3326 */           if (organizacionConfiguracion.getAtributo2() != null)
/* 3161:     */           {
/* 3162:3327 */             columnaErronea = 6;
/* 3163:3328 */             String codigoValorAtributo = fila[6] == null ? "" : fila[6].getStringCellValue();
/* 3164:3329 */             valorAtributo2 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo2().getCodigo())).get(codigoValorAtributo);
/* 3165:3330 */             valorAtributo2 = producto.getAtributoProduccion2() == null ? null : valorAtributo2;
/* 3166:     */           }
/* 3167:3332 */           if (organizacionConfiguracion.getAtributo3() != null)
/* 3168:     */           {
/* 3169:3333 */             columnaErronea = 7;
/* 3170:3334 */             String codigoValorAtributo = fila[7] == null ? "" : fila[7].getStringCellValue();
/* 3171:3335 */             valorAtributo3 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo3().getCodigo())).get(codigoValorAtributo);
/* 3172:3336 */             valorAtributo3 = producto.getAtributoProduccion3() == null ? null : valorAtributo3;
/* 3173:     */           }
/* 3174:3338 */           if (organizacionConfiguracion.getAtributo4() != null)
/* 3175:     */           {
/* 3176:3339 */             columnaErronea = 8;
/* 3177:3340 */             String codigoValorAtributo = fila[8] == null ? "" : fila[8].getStringCellValue();
/* 3178:3341 */             valorAtributo4 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo4().getCodigo())).get(codigoValorAtributo);
/* 3179:3342 */             valorAtributo4 = producto.getAtributoProduccion4() == null ? null : valorAtributo4;
/* 3180:     */           }
/* 3181:3344 */           if (organizacionConfiguracion.getAtributo5() != null)
/* 3182:     */           {
/* 3183:3345 */             columnaErronea = 9;
/* 3184:3346 */             String codigoValorAtributo = fila[9] == null ? "" : fila[9].getStringCellValue();
/* 3185:3347 */             valorAtributo5 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo5().getCodigo())).get(codigoValorAtributo);
/* 3186:3348 */             valorAtributo5 = producto.getAtributoProduccion5() == null ? null : valorAtributo5;
/* 3187:     */           }
/* 3188:3350 */           if (organizacionConfiguracion.getAtributo6() != null)
/* 3189:     */           {
/* 3190:3351 */             columnaErronea = 10;
/* 3191:3352 */             String codigoValorAtributo = fila[10] == null ? "" : fila[10].getStringCellValue();
/* 3192:3353 */             valorAtributo6 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo6().getCodigo())).get(codigoValorAtributo);
/* 3193:3354 */             valorAtributo6 = producto.getAtributoProduccion6() == null ? null : valorAtributo6;
/* 3194:     */           }
/* 3195:3356 */           if (organizacionConfiguracion.getAtributo7() != null)
/* 3196:     */           {
/* 3197:3357 */             columnaErronea = 11;
/* 3198:3358 */             String codigoValorAtributo = fila[11] == null ? "" : fila[11].getStringCellValue();
/* 3199:3359 */             valorAtributo7 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo7().getCodigo())).get(codigoValorAtributo);
/* 3200:3360 */             valorAtributo7 = producto.getAtributoProduccion7() == null ? null : valorAtributo7;
/* 3201:     */           }
/* 3202:3362 */           if (organizacionConfiguracion.getAtributo8() != null)
/* 3203:     */           {
/* 3204:3363 */             columnaErronea = 12;
/* 3205:3364 */             String codigoValorAtributo = fila[12] == null ? "" : fila[12].getStringCellValue();
/* 3206:3365 */             valorAtributo8 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo8().getCodigo())).get(codigoValorAtributo);
/* 3207:3366 */             valorAtributo8 = producto.getAtributoProduccion8() == null ? null : valorAtributo8;
/* 3208:     */           }
/* 3209:3368 */           if (organizacionConfiguracion.getAtributo9() != null)
/* 3210:     */           {
/* 3211:3369 */             columnaErronea = 13;
/* 3212:3370 */             String codigoValorAtributo = fila[13] == null ? "" : fila[13].getStringCellValue();
/* 3213:3371 */             valorAtributo9 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo9().getCodigo())).get(codigoValorAtributo);
/* 3214:3372 */             valorAtributo9 = producto.getAtributoProduccion9() == null ? null : valorAtributo9;
/* 3215:     */           }
/* 3216:3374 */           if (organizacionConfiguracion.getAtributo10() != null)
/* 3217:     */           {
/* 3218:3375 */             columnaErronea = 14;
/* 3219:3376 */             String codigoValorAtributo = fila[14] == null ? "" : fila[14].getStringCellValue();
/* 3220:3377 */             valorAtributo10 = (ValorAtributo)((HashMap)hmAtributo.get(organizacionConfiguracion.getAtributo10().getCodigo())).get(codigoValorAtributo);
/* 3221:3378 */             valorAtributo10 = producto.getAtributoProduccion10() == null ? null : valorAtributo10;
/* 3222:     */           }
/* 3223:     */         }
/* 3224:3382 */         Lote lote = (Lote)hmLote.get(codigoLote);
/* 3225:3383 */         if (lote == null)
/* 3226:     */         {
/* 3227:3385 */           lote = new Lote();
/* 3228:3386 */           lote.setActivo(true);
/* 3229:3387 */           lote.setCodigo(codigoLote);
/* 3230:3388 */           lote.setIndicadorMovimientoInterno(movimientoInterno);
/* 3231:3389 */           lote.setFechaFabricacion(fechaFabricacion);
/* 3232:3390 */           lote.setFechaCaducidad(fechaCaducidad);
/* 3233:3391 */           lote.setProducto(producto);
/* 3234:3392 */           lote.setIdOrganizacion(idOrganizacion);
/* 3235:3393 */           lote.setIdSucursal(idSucursal);
/* 3236:3396 */           if (producto.getAtributoProduccion1() != null)
/* 3237:     */           {
/* 3238:3397 */             if (valorAtributo1 == null) {
/* 3239:3399 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion1().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3240:     */             }
/* 3241:3401 */             lote.setAtributo1(producto.getAtributoProduccion1());
/* 3242:3402 */             lote.setValorAtributo1(valorAtributo1);
/* 3243:     */           }
/* 3244:3406 */           if (producto.getAtributoProduccion2() != null)
/* 3245:     */           {
/* 3246:3407 */             if (valorAtributo2 == null) {
/* 3247:3409 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion2().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3248:     */             }
/* 3249:3411 */             lote.setAtributo2(producto.getAtributoProduccion2());
/* 3250:3412 */             lote.setValorAtributo2(valorAtributo2);
/* 3251:     */           }
/* 3252:3416 */           if (producto.getAtributoProduccion3() != null)
/* 3253:     */           {
/* 3254:3417 */             if (valorAtributo3 == null) {
/* 3255:3419 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion3().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3256:     */             }
/* 3257:3421 */             lote.setAtributo3(producto.getAtributoProduccion3());
/* 3258:3422 */             lote.setValorAtributo3(valorAtributo3);
/* 3259:     */           }
/* 3260:3426 */           if (producto.getAtributoProduccion4() != null)
/* 3261:     */           {
/* 3262:3427 */             if (valorAtributo4 == null) {
/* 3263:3429 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion4().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3264:     */             }
/* 3265:3431 */             lote.setAtributo4(producto.getAtributoProduccion4());
/* 3266:3432 */             lote.setValorAtributo4(valorAtributo4);
/* 3267:     */           }
/* 3268:3436 */           if (producto.getAtributoProduccion5() != null)
/* 3269:     */           {
/* 3270:3437 */             if (valorAtributo5 == null) {
/* 3271:3439 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion5().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3272:     */             }
/* 3273:3441 */             lote.setAtributo5(producto.getAtributoProduccion5());
/* 3274:3442 */             lote.setValorAtributo5(valorAtributo5);
/* 3275:     */           }
/* 3276:3446 */           if (producto.getAtributoProduccion6() != null)
/* 3277:     */           {
/* 3278:3447 */             if (valorAtributo6 == null) {
/* 3279:3449 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion6().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3280:     */             }
/* 3281:3451 */             lote.setAtributo6(producto.getAtributoProduccion6());
/* 3282:3452 */             lote.setValorAtributo6(valorAtributo6);
/* 3283:     */           }
/* 3284:3456 */           if (producto.getAtributoProduccion7() != null)
/* 3285:     */           {
/* 3286:3457 */             if (valorAtributo7 == null) {
/* 3287:3459 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion7().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3288:     */             }
/* 3289:3461 */             lote.setAtributo7(producto.getAtributoProduccion7());
/* 3290:3462 */             lote.setValorAtributo7(valorAtributo7);
/* 3291:     */           }
/* 3292:3466 */           if (producto.getAtributoProduccion8() != null)
/* 3293:     */           {
/* 3294:3467 */             if (valorAtributo8 == null) {
/* 3295:3469 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion8().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3296:     */             }
/* 3297:3471 */             lote.setAtributo8(producto.getAtributoProduccion8());
/* 3298:3472 */             lote.setValorAtributo8(valorAtributo8);
/* 3299:     */           }
/* 3300:3476 */           if (producto.getAtributoProduccion9() != null)
/* 3301:     */           {
/* 3302:3477 */             if (valorAtributo9 == null) {
/* 3303:3479 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion9().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3304:     */             }
/* 3305:3481 */             lote.setAtributo9(producto.getAtributoProduccion9());
/* 3306:3482 */             lote.setValorAtributo9(valorAtributo9);
/* 3307:     */           }
/* 3308:3486 */           if (producto.getAtributoProduccion10() != null)
/* 3309:     */           {
/* 3310:3487 */             if (valorAtributo10 == null) {
/* 3311:3489 */               throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] {producto.getAtributoProduccion10().getNombre() + " para el producto: " + producto.getCodigo() + " - " + producto.getNombre() });
/* 3312:     */             }
/* 3313:3491 */             lote.setAtributo10(producto.getAtributoProduccion10());
/* 3314:3492 */             lote.setValorAtributo10(valorAtributo10);
/* 3315:     */           }
/* 3316:3496 */           this.servicioLote.guardar(lote);
/* 3317:     */           
/* 3318:3498 */           hmLote.put(codigoLote, lote);
/* 3319:     */         }
/* 3320:3502 */         filaActual++;
/* 3321:     */       }
/* 3322:     */     }
/* 3323:     */     catch (IllegalArgumentException e)
/* 3324:     */     {
/* 3325:3507 */       this.context.setRollbackOnly();
/* 3326:3508 */       throw new ExcepcionAS2("msg_error_formato_incorrecto");
/* 3327:     */     }
/* 3328:     */     catch (ExcepcionAS2 e)
/* 3329:     */     {
/* 3330:3510 */       this.context.setRollbackOnly();
/* 3331:3511 */       throw e;
/* 3332:     */     }
/* 3333:     */     catch (AS2Exception e)
/* 3334:     */     {
/* 3335:3513 */       this.context.setRollbackOnly();
/* 3336:3514 */       throw e;
/* 3337:     */     }
/* 3338:     */     catch (Exception e)
/* 3339:     */     {
/* 3340:3516 */       this.context.setRollbackOnly();
/* 3341:3517 */       throw new ExcepcionAS2(e);
/* 3342:     */     }
/* 3343:     */   }
/* 3344:     */   
/* 3345:     */   public void migracionProductoAtributo(String fileName, InputStream imInputStream, int filaInicial)
/* 3346:     */     throws ExcepcionAS2
/* 3347:     */   {
/* 3348:3528 */     Map<String, Producto> hashMapProducto = new HashMap();
/* 3349:3529 */     Map<String, Map<String, Atributo>> hashMapProductoAtributo = new HashMap();
/* 3350:3530 */     Map<String, ConjuntoAtributo> hashMapConjuntoAtributo = new HashMap();
/* 3351:3531 */     Map<String, Atributo> hashMapAtributo = new HashMap();
/* 3352:3532 */     Map<String, Map<String, ValorAtributo>> hashMapValorAtributo = new HashMap();
/* 3353:     */     
/* 3354:     */ 
/* 3355:     */ 
/* 3356:     */ 
/* 3357:3537 */     List<Atributo> listaAtributo = this.servicioAtributo.obtenerListaCombo("", false, null);
/* 3358:3538 */     for (Iterator localIterator1 = listaAtributo.iterator(); localIterator1.hasNext();)
/* 3359:     */     {
/* 3360:3538 */       a = (Atributo)localIterator1.next();
/* 3361:     */       Iterator localIterator2;
/* 3362:3539 */       if (a.getTipoAtributo().equals(TipoAtributo.LISTA))
/* 3363:     */       {
/* 3364:3540 */         Atributo atri = this.servicioAtributo.cargarDetalle(a.getIdAtributo());
/* 3365:3541 */         for (localIterator2 = atri.getListaValorAtributo().iterator(); localIterator2.hasNext();)
/* 3366:     */         {
/* 3367:3541 */           valorAtributo = (ValorAtributo)localIterator2.next();
/* 3368:3542 */           if (!hashMapValorAtributo.containsKey(a.getCodigo())) {
/* 3369:3543 */             hashMapValorAtributo.put(a.getCodigo(), new HashMap());
/* 3370:     */           }
/* 3371:3545 */           ((Map)hashMapValorAtributo.get(a.getCodigo())).put(valorAtributo.getNombre(), valorAtributo);
/* 3372:     */         }
/* 3373:     */       }
/* 3374:3548 */       hashMapAtributo.put(a.getCodigo(), a);
/* 3375:     */     }
/* 3376:     */     Atributo a;
/* 3377:     */     ValorAtributo valorAtributo;
/* 3378:3551 */     Object listaProducto = this.servicioProducto.obtenerListaCombo("", false, null);
/* 3379:3552 */     for (Producto p : (List)listaProducto)
/* 3380:     */     {
/* 3381:3553 */       if (p.getConjuntoAtributo() != null)
/* 3382:     */       {
/* 3383:3554 */         ConjuntoAtributo ca = (ConjuntoAtributo)hashMapConjuntoAtributo.get(p.getConjuntoAtributo().getCodigo());
/* 3384:3555 */         if (ca == null)
/* 3385:     */         {
/* 3386:3556 */           ca = this.servicioConjuntoAtributo.cargarDetalle(p.getConjuntoAtributo().getIdConjuntoAtributo());
/* 3387:3557 */           hashMapProductoAtributo.put(ca.getCodigo(), new HashMap());
/* 3388:3558 */           for (valorAtributo = ca.getListaAtributo().iterator(); valorAtributo.hasNext();)
/* 3389:     */           {
/* 3390:3558 */             a = (Atributo)valorAtributo.next();
/* 3391:3559 */             ((Map)hashMapProductoAtributo.get(ca.getCodigo())).put(a.getCodigo(), a);
/* 3392:     */           }
/* 3393:3561 */           hashMapConjuntoAtributo.put(ca.getCodigo(), ca);
/* 3394:     */         }
/* 3395:     */       }
/* 3396:3565 */       hashMapProducto.put(p.getCodigo(), p);
/* 3397:     */     }
/* 3398:     */     Atributo a;
/* 3399:3568 */     int filaActual = filaInicial;
/* 3400:3569 */     int columnaErronea = 0;
/* 3401:3570 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 3402:     */     try
/* 3403:     */     {
/* 3404:3574 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getId(), fileName, imInputStream, filaInicial, 0);
/* 3405:3576 */       for (HSSFCell[] fila : datos)
/* 3406:     */       {
/* 3407:3578 */         filaErronea = fila;
/* 3408:3579 */         filaActual++;
/* 3409:     */         
/* 3410:3581 */         String codigoProducto = fila[(columnaErronea = 0)].getStringCellValue().trim();
/* 3411:3582 */         String codigoAtributo = fila[(columnaErronea = 1)] == null ? null : fila[(columnaErronea = 1)].getStringCellValue().trim();
/* 3412:3583 */         String valor = fila[(columnaErronea = 2)] == null ? null : fila[(columnaErronea = 2)].getStringCellValue().trim();
/* 3413:     */         
/* 3414:3585 */         Producto producto = (Producto)hashMapProducto.get(codigoProducto);
/* 3415:3586 */         if (producto == null) {
/* 3416:3587 */           throw new ExcepcionAS2Inventario("msg_error_producto_valido", " Codigo: " + codigoProducto);
/* 3417:     */         }
/* 3418:3590 */         Atributo atributo = (Atributo)hashMapAtributo.get(codigoAtributo);
/* 3419:3592 */         if (atributo == null) {
/* 3420:3593 */           throw new ExcepcionAS2Inventario("msg_error_producto_valor_atributo", " Atributo: " + codigoAtributo);
/* 3421:     */         }
/* 3422:3596 */         ValorAtributo valorAtributo = (ValorAtributo)((Map)hashMapValorAtributo.get(atributo.getCodigo())).get(valor);
/* 3423:     */         
/* 3424:3598 */         ProductoAtributo productoAtributo = this.servicioProductoAtributo.buscarPorAtributoProducto(producto.getIdProducto(), atributo
/* 3425:3599 */           .getIdAtributo());
/* 3426:3600 */         if (productoAtributo == null) {
/* 3427:3601 */           productoAtributo = new ProductoAtributo();
/* 3428:     */         }
/* 3429:3603 */         productoAtributo.setProducto(producto);
/* 3430:3604 */         productoAtributo.setAtributo(atributo);
/* 3431:3605 */         productoAtributo.setValor(valor);
/* 3432:3606 */         productoAtributo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 3433:3607 */         productoAtributo.setIdSucursal(AppUtil.getSucursal().getId());
/* 3434:3609 */         if (atributo.getTipoAtributo().equals(TipoAtributo.LISTA))
/* 3435:     */         {
/* 3436:3610 */           if (valorAtributo == null) {
/* 3437:3612 */             throw new ExcepcionAS2Inventario("msg_error_producto_valor_atributo", " para Producto: " + producto.getNombre() + ", Atributo: " + atributo.getNombre() + ", Valor: " + valor);
/* 3438:     */           }
/* 3439:3614 */           productoAtributo.setValorAtributo(valorAtributo);
/* 3440:     */         }
/* 3441:3617 */         if (producto.getConjuntoAtributo() == null) {
/* 3442:3619 */           throw new ExcepcionAS2Inventario("msg_error_producto_valor_atributo", "Producto: " + producto.getNombre() + ", No tiene un Conjunto Atributo asociado");
/* 3443:     */         }
/* 3444:3621 */         if (!((Map)hashMapProductoAtributo.get(producto.getConjuntoAtributo().getCodigo())).containsKey(atributo.getCodigo())) {
/* 3445:3624 */           throw new ExcepcionAS2Inventario("msg_error_producto_valor_atributo", " para el Conjunto Atributo :" + producto.getConjuntoAtributo().getNombre() + " del Producto: " + producto.getNombre() + ", Atributo: " + atributo.getNombre() + ", Valor: " + valor);
/* 3446:     */         }
/* 3447:3626 */         this.servicioProductoAtributo.guardar(productoAtributo);
/* 3448:     */       }
/* 3449:     */     }
/* 3450:     */     catch (IllegalArgumentException e)
/* 3451:     */     {
/* 3452:3630 */       LOG.info("Error al migrar productos", e);
/* 3453:3631 */       this.context.setRollbackOnly();
/* 3454:     */       
/* 3455:3633 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 3456:     */     }
/* 3457:     */     catch (IllegalStateException e)
/* 3458:     */     {
/* 3459:3636 */       LOG.info("Error al migrar productos", e);
/* 3460:3637 */       this.context.setRollbackOnly();
/* 3461:     */       
/* 3462:3639 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 3463:     */     }
/* 3464:     */     catch (ExcepcionAS2 e)
/* 3465:     */     {
/* 3466:3641 */       LOG.info("Error al migrar productos", e);
/* 3467:3642 */       this.context.setRollbackOnly();
/* 3468:3643 */       throw e;
/* 3469:     */     }
/* 3470:     */     catch (Exception e)
/* 3471:     */     {
/* 3472:3645 */       LOG.error("Error al migrar productos", e);
/* 3473:3646 */       this.context.setRollbackOnly();
/* 3474:     */       
/* 3475:3648 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString(), e);
/* 3476:     */     }
/* 3477:     */   }
/* 3478:     */   
/* 3479:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 3480:     */   public void cargaListaDeMateriales(int idOrganizacion, InputStream imInputStream, int filaInicial)
/* 3481:     */     throws ExcepcionAS2
/* 3482:     */   {
/* 3483:3661 */     HashMap<String, String> filters = new HashMap();
/* 3484:3662 */     filters.put("idOrganizacion", "=" + idOrganizacion);
/* 3485:3663 */     List<Producto> listaMateriales = this.servicioProducto.obtenerListaCombo("codigo", true, filters);
/* 3486:3664 */     HashMap<String, Producto> hmMaterial = new HashMap();
/* 3487:3665 */     for (Producto material : listaMateriales) {
/* 3488:3666 */       hmMaterial.put(material.getCodigo(), material);
/* 3489:     */     }
/* 3490:3670 */     Object hmProductoMaterial = new HashMap();
/* 3491:3671 */     List<ProductoMaterial> listaProductoMateriales = this.productoMaterialDao.obtenerListaCombo(ProductoMaterial.class, "cantidad", true, filters);
/* 3492:3672 */     for (ProductoMaterial productoMaterial : listaProductoMateriales) {
/* 3493:3673 */       ((HashMap)hmProductoMaterial).put(productoMaterial.getProducto().getCodigo() + "~" + productoMaterial.getMaterial().getCodigo(), productoMaterial);
/* 3494:     */     }
/* 3495:3676 */     Object hmProducto = new LinkedHashMap();
/* 3496:     */     
/* 3497:3678 */     int filaActual = filaInicial;
/* 3498:3679 */     int columnaActual = 0;
/* 3499:3680 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 3500:     */     try
/* 3501:     */     {
/* 3502:3683 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 3503:3684 */       for (HSSFCell[] fila : datos)
/* 3504:     */       {
/* 3505:3686 */         filaErronea = fila;
/* 3506:3687 */         filaActual++;
/* 3507:     */         
/* 3508:3689 */         String codigoProducto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 0, true, Integer.valueOf(1), Integer.valueOf(20));
/* 3509:3690 */         Producto producto = (Producto)((Map)hmProducto).get(codigoProducto);
/* 3510:3692 */         if (producto == null)
/* 3511:     */         {
/* 3512:3693 */           producto = this.servicioProducto.buscarPorCodigo(codigoProducto, idOrganizacion, null);
/* 3513:3694 */           producto.getListaProductoMaterial().size();
/* 3514:3695 */           if (producto != null) {
/* 3515:3696 */             ((Map)hmProducto).put(producto.getCodigo(), producto);
/* 3516:     */           } else {
/* 3517:3699 */             throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", "Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 3518:     */           }
/* 3519:     */         }
/* 3520:3710 */         BigDecimal cantidadProduccion = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 1, true, 
/* 3521:3711 */           Integer.valueOf(0), Integer.valueOf(0));
/* 3522:     */         
/* 3523:3713 */         String codigoMaterial = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 2, true, Integer.valueOf(1), Integer.valueOf(20));
/* 3524:3714 */         Producto material = (Producto)hmMaterial.get(codigoMaterial);
/* 3525:3715 */         if (material == null) {
/* 3526:3717 */           throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", "Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 3527:     */         }
/* 3528:3721 */         int orden = ((BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 3, false, Integer.valueOf(0), Integer.valueOf(0))).intValue();
/* 3529:     */         
/* 3530:3723 */         BigDecimal cantidad = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 4, true, Integer.valueOf(0), Integer.valueOf(0));
/* 3531:3724 */         BigDecimal proporcion = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 5, true, Integer.valueOf(0), 
/* 3532:3725 */           Integer.valueOf(0));
/* 3533:     */         
/* 3534:     */ 
/* 3535:3728 */         boolean activo = ((String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 6, true, Integer.valueOf(2), Integer.valueOf(2))).equals("SI");
/* 3536:     */         
/* 3537:     */ 
/* 3538:3731 */         boolean principal = ((String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 7, true, Integer.valueOf(2), Integer.valueOf(2))).equals("SI");
/* 3539:     */         
/* 3540:     */ 
/* 3541:3734 */         boolean explotaLDM = ((String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 8, true, Integer.valueOf(2), Integer.valueOf(2))).equals("SI");
/* 3542:     */         
/* 3543:     */ 
/* 3544:     */ 
/* 3545:     */ 
/* 3546:3739 */         String codigoProductoSustituto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 9, false, 
/* 3547:3740 */           Integer.valueOf(1), Integer.valueOf(20));
/* 3548:     */         
/* 3549:3742 */         Producto productoSustituto = null;
/* 3550:3743 */         BigDecimal cantidadProductoSustituto = BigDecimal.ZERO;
/* 3551:3745 */         if ((codigoProductoSustituto != null) && (!codigoProductoSustituto.trim().isEmpty()))
/* 3552:     */         {
/* 3553:3747 */           productoSustituto = (Producto)hmMaterial.get(codigoProductoSustituto);
/* 3554:3749 */           if (productoSustituto == null)
/* 3555:     */           {
/* 3556:3750 */             productoSustituto = this.servicioProducto.buscarPorCodigo(codigoProductoSustituto, idOrganizacion, null);
/* 3557:3751 */             if (productoSustituto != null) {
/* 3558:3752 */               hmMaterial.put(productoSustituto.getCodigo(), productoSustituto);
/* 3559:     */             } else {
/* 3560:3755 */               throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", "Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 3561:     */             }
/* 3562:     */           }
/* 3563:3758 */           cantidadProductoSustituto = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaActual = 10, true, 
/* 3564:3759 */             Integer.valueOf(0), Integer.valueOf(0));
/* 3565:     */         }
/* 3566:3763 */         ProductoMaterial productoMaterial = (ProductoMaterial)((HashMap)hmProductoMaterial).get(codigoProducto + "~" + codigoMaterial);
/* 3567:3764 */         if (productoMaterial == null)
/* 3568:     */         {
/* 3569:3765 */           Date fechaDesde = new Date();
/* 3570:3766 */           fechaDesde = FuncionesUtiles.getFechaInicioMes(fechaDesde);
/* 3571:3767 */           productoMaterial = new ProductoMaterial();
/* 3572:3768 */           productoMaterial.setIdOrganizacion(producto.getIdOrganizacion());
/* 3573:3769 */           productoMaterial.setIdSucursal(producto.getIdSucursal());
/* 3574:3770 */           productoMaterial.setActivo(activo);
/* 3575:3771 */           productoMaterial.setCantidad(cantidad);
/* 3576:3772 */           productoMaterial.setOrden(orden);
/* 3577:3773 */           productoMaterial.setProporcion(proporcion);
/* 3578:3774 */           if ((codigoProductoSustituto != null) && (!codigoProductoSustituto.isEmpty()))
/* 3579:     */           {
/* 3580:3775 */             productoMaterial.setSustituto(productoSustituto);
/* 3581:3776 */             productoMaterial.setCantidadSustituto(cantidadProductoSustituto);
/* 3582:     */           }
/* 3583:3778 */           productoMaterial.setIndicadorPrincipal(Boolean.valueOf(principal));
/* 3584:3779 */           productoMaterial.setIndicadorExplota(explotaLDM);
/* 3585:3780 */           productoMaterial.setProducto(producto);
/* 3586:     */           
/* 3587:3782 */           productoMaterial.setMaterial(material);
/* 3588:3783 */           producto.getListaProductoMaterial().add(productoMaterial);
/* 3589:3784 */           producto.setCantidadProduccion(cantidadProduccion);
/* 3590:3785 */           productoMaterial.setFechaDesde(fechaDesde);
/* 3591:     */           
/* 3592:3787 */           ((HashMap)hmProductoMaterial).put(codigoProducto + "~" + codigoMaterial, productoMaterial);
/* 3593:     */         }
/* 3594:     */       }
/* 3595:3792 */       for (??? = ((Map)hmProducto).values().iterator(); ((Iterator)???).hasNext();)
/* 3596:     */       {
/* 3597:3792 */         Producto pro = (Producto)((Iterator)???).next();
/* 3598:3793 */         this.servicioProducto.guardarListaMaterial(pro);
/* 3599:     */       }
/* 3600:     */     }
/* 3601:     */     catch (IllegalStateException e)
/* 3602:     */     {
/* 3603:3797 */       LOG.info("Error al migrar ", e);
/* 3604:3798 */       this.context.setRollbackOnly();
/* 3605:     */       
/* 3606:3800 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 3607:     */     }
/* 3608:     */     catch (IllegalArgumentException e)
/* 3609:     */     {
/* 3610:3802 */       LOG.info("Error al migrar ", e);
/* 3611:3803 */       this.context.setRollbackOnly();
/* 3612:     */       
/* 3613:3805 */       throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 3614:     */     }
/* 3615:     */     catch (ExcepcionAS2Financiero e)
/* 3616:     */     {
/* 3617:3807 */       this.context.setRollbackOnly();
/* 3618:3808 */       throw e;
/* 3619:     */     }
/* 3620:     */     catch (ExcepcionAS2 e)
/* 3621:     */     {
/* 3622:3810 */       this.context.setRollbackOnly();
/* 3623:3811 */       throw e;
/* 3624:     */     }
/* 3625:     */     catch (Exception e)
/* 3626:     */     {
/* 3627:3813 */       this.context.setRollbackOnly();
/* 3628:3814 */       throw new ExcepcionAS2(e);
/* 3629:     */     }
/* 3630:     */   }
/* 3631:     */   
/* 3632:     */   public void cargarAsignarRubros(int idOrganizacion, InputStream imInputStream, int filaInicial)
/* 3633:     */     throws ExcepcionAS2
/* 3634:     */   {
/* 3635:     */     try
/* 3636:     */     {
/* 3637:3822 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 3638:     */       
/* 3639:3824 */       List<RubroEmpleado> listaRubroEmpleadoSI = new ArrayList();
/* 3640:3825 */       List<RubroEmpleado> listaRubroEmpleadoNO = new ArrayList();
/* 3641:     */       
/* 3642:3827 */       Map<Integer, Rubro> hmRubro = new HashMap();
/* 3643:3829 */       for (filaEmpleado : datos)
/* 3644:     */       {
/* 3645:3831 */         String identificacion = filaEmpleado[1].toString();
/* 3646:3832 */         Empleado empleado = this.servicioEmpleado.bucarEmpleadoPorIdentificacion(identificacion, idOrganizacion);
/* 3647:3834 */         for (int j = 4; j < filaEmpleado.length; j++)
/* 3648:     */         {
/* 3649:3836 */           String valor = filaEmpleado[j].getStringCellValue();
/* 3650:3837 */           int idRubro = Integer.parseInt(datos[0][j].getStringCellValue());
/* 3651:     */           
/* 3652:3839 */           Rubro rubro = (Rubro)hmRubro.get(Integer.valueOf(idRubro));
/* 3653:3840 */           if (rubro == null)
/* 3654:     */           {
/* 3655:3841 */             rubro = this.servicioRubro.buscarPorId(idRubro);
/* 3656:3842 */             hmRubro.put(Integer.valueOf(rubro.getId()), rubro);
/* 3657:     */           }
/* 3658:3845 */           if (valor.equalsIgnoreCase("SI")) {
/* 3659:3846 */             listaRubroEmpleadoSI.add(new RubroEmpleado(idOrganizacion, rubro, empleado));
/* 3660:3847 */           } else if (valor.equalsIgnoreCase("NO")) {
/* 3661:3848 */             listaRubroEmpleadoNO.add(new RubroEmpleado(idOrganizacion, rubro, empleado));
/* 3662:     */           }
/* 3663:     */         }
/* 3664:     */       }
/* 3665:3853 */       hm = new HashMap();
/* 3666:3854 */       Object filters = new HashMap();
/* 3667:3855 */       ((HashMap)filters).put("idOrganizacion", "=" + idOrganizacion);
/* 3668:     */       
/* 3669:     */ 
/* 3670:3858 */       Object listaRubroEmpleado = this.servicioRubroEmpleado.getGenerarRubroEmpleado(null, idOrganizacion);
/* 3671:3860 */       for (RubroEmpleado remp : (List)listaRubroEmpleado) {
/* 3672:3861 */         ((HashMap)hm).put(remp.getEmpleado().getIdEmpleado() + "~" + remp.getRubro().getIdRubro(), remp);
/* 3673:     */       }
/* 3674:3864 */       for (RubroEmpleado re : listaRubroEmpleadoSI) {
/* 3675:3865 */         if (!((HashMap)hm).containsKey(re.getEmpleado().getIdEmpleado() + "~" + re.getRubro().getIdRubro()))
/* 3676:     */         {
/* 3677:3866 */           re.setValor(new BigDecimal("0"));
/* 3678:3867 */           this.servicioRubroEmpleado.guardar(re);
/* 3679:     */         }
/* 3680:     */       }
/* 3681:3870 */       for (RubroEmpleado re : listaRubroEmpleadoNO)
/* 3682:     */       {
/* 3683:3871 */         RubroEmpleado rel = (RubroEmpleado)((HashMap)hm).get(re.getEmpleado().getIdEmpleado() + "~" + re.getRubro().getIdRubro());
/* 3684:3872 */         if (rel != null) {
/* 3685:3873 */           this.servicioRubroEmpleado.eliminar(rel);
/* 3686:     */         }
/* 3687:     */       }
/* 3688:     */     }
/* 3689:     */     catch (ExcepcionAS2 e)
/* 3690:     */     {
/* 3691:     */       HSSFCell[] filaEmpleado;
/* 3692:     */       Object hm;
/* 3693:3878 */       this.context.setRollbackOnly();
/* 3694:3879 */       throw new ExcepcionAS2Financiero("msg_info_cuenta_contable_0001", " " + e);
/* 3695:     */     }
/* 3696:     */     catch (Exception e)
/* 3697:     */     {
/* 3698:3881 */       this.context.setRollbackOnly();
/* 3699:3882 */       throw new ExcepcionAS2(e);
/* 3700:     */     }
/* 3701:     */   }
/* 3702:     */   
/* 3703:     */   public List<Ticket> migracionCargaTicket(InputStream input, int i, int idOrganizacion, int idSucursal, Integer columnasEnBlanco)
/* 3704:     */     throws ExcepcionAS2, AS2Exception
/* 3705:     */   {
/* 3706:3890 */     List<Ticket> listaTickets = new ArrayList();
/* 3707:     */     try
/* 3708:     */     {
/* 3709:3892 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(input, i, 0, columnasEnBlanco, Integer.valueOf(4));
/* 3710:     */       
/* 3711:     */ 
/* 3712:     */ 
/* 3713:3896 */       HashMap<String, String> filters = new HashMap();
/* 3714:3897 */       filters.put("idOrganizacion", "=" + idOrganizacion);
/* 3715:3898 */       List<ConfiguracionCargaTicket> listConfiguraciones = this.servicioCatalogoConfiguracionTicket.obtenerListaCombo(ConfiguracionCargaTicket.class, "orden", true, filters);
/* 3716:     */       
/* 3717:     */ 
/* 3718:3901 */       int cont = 0;
/* 3719:3902 */       for (HSSFCell[] filaTicket : datos)
/* 3720:     */       {
/* 3721:     */         String impuestoNoregistrado;
/* 3722:3904 */         if (cont < 6)
/* 3723:     */         {
/* 3724:3905 */           if (cont == 0)
/* 3725:     */           {
/* 3726:3907 */             impuestoNoregistrado = null;
/* 3727:3908 */             for (int j = 0; j < filaTicket.length; j++) {
/* 3728:3909 */               if (filaTicket[j] != null)
/* 3729:     */               {
/* 3730:3911 */                 if (filaTicket[j].toString().trim().length() == 2) {
/* 3731:3912 */                   impuestoNoregistrado = filaTicket[j].toString().trim();
/* 3732:     */                 }
/* 3733:3914 */                 for (ConfiguracionCargaTicket cctk : listConfiguraciones) {
/* 3734:3915 */                   if (filaTicket[j].toString().trim().equals(cctk.getNombreEtiqueta().trim()))
/* 3735:     */                   {
/* 3736:3916 */                     cctk.setIndice(j);
/* 3737:3917 */                     impuestoNoregistrado = null;
/* 3738:     */                   }
/* 3739:     */                 }
/* 3740:3920 */                 if (impuestoNoregistrado != null)
/* 3741:     */                 {
/* 3742:3921 */                   cctn = new ConfiguracionCargaTicket();
/* 3743:3922 */                   ((ConfiguracionCargaTicket)cctn).setNombreEtiqueta(impuestoNoregistrado);
/* 3744:3923 */                   ((ConfiguracionCargaTicket)cctn).setIndice(j);
/* 3745:3924 */                   listConfiguraciones.add(cctn);
/* 3746:     */                 }
/* 3747:     */               }
/* 3748:     */             }
/* 3749:3928 */             int verificadorIndices = 0;
/* 3750:3929 */             for (Object cctn = listConfiguraciones.iterator(); ((Iterator)cctn).hasNext();)
/* 3751:     */             {
/* 3752:3929 */               ConfiguracionCargaTicket cctk = (ConfiguracionCargaTicket)((Iterator)cctn).next();
/* 3753:3930 */               System.out.println(cctk.getIndice() + "\t" + cctk.getNombreEtiqueta());
/* 3754:3931 */               if (cctk.getIndice() > 0) {
/* 3755:3932 */                 verificadorIndices++;
/* 3756:     */               }
/* 3757:     */             }
/* 3758:3935 */             if (verificadorIndices == 0) {
/* 3759:3936 */               throw new ExcepcionAS2("msg_mensaje_error_cabecera_incorrecta");
/* 3760:     */             }
/* 3761:     */           }
/* 3762:3939 */           cont++;
/* 3763:     */         }
/* 3764:     */         else
/* 3765:     */         {
/* 3766:3944 */           FuncionesUtiles.ordenaLista(listConfiguraciones, "orden");
/* 3767:3945 */           Ticket ticket = new Ticket();
/* 3768:3946 */           ticket.setIdOrganizacion(idOrganizacion);
/* 3769:3947 */           ticket.setIdSucursal(idSucursal);
/* 3770:3948 */           for (ConfiguracionCargaTicket cctk : listConfiguraciones)
/* 3771:     */           {
/* 3772:3949 */             if ((cctk.getNombreEtiqueta().equals("CODIGO ESTACION")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3773:     */             {
/* 3774:3950 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals("")))
/* 3775:     */               {
/* 3776:3951 */                 Object filtersPV = new HashMap();
/* 3777:3952 */                 ((HashMap)filtersPV).put("idOrganizacion", "=" + idOrganizacion);
/* 3778:3953 */                 ((HashMap)filtersPV).put("codigoAlterno", "=" + filaTicket[cctk.getIndice()].toString());
/* 3779:3954 */                 PuntoDeVenta puntoVenta = this.servicioPuntoDeVenta.buscarPuntoDeVenta((Map)filtersPV);
/* 3780:3955 */                 ticket.setPuntoDeVenta(puntoVenta);
/* 3781:     */               }
/* 3782:     */             }
/* 3783:3957 */             else if ((cctk.getNombreEtiqueta().equals("TIPO DE EMISION")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3784:     */             {
/* 3785:3958 */               ticket.setOperacion(filaTicket[cctk.getIndice()].toString().trim());
/* 3786:     */             }
/* 3787:3959 */             else if ((cctk.getNombreEtiqueta().equals("NUMERO DE DOCUMENTO")) && (cctk.getIndice() >= 0) && 
/* 3788:3960 */               (filaTicket[cctk.getIndice()] != null))
/* 3789:     */             {
/* 3790:3961 */               filaTicket[cctk.getIndice()].setCellType(1);
/* 3791:3962 */               ticket.setNumero(filaTicket[cctk.getIndice()].toString().trim());
/* 3792:     */             }
/* 3793:3964 */             if ((cctk.getNombreEtiqueta().equals("TIPO DE DOCUMENTO")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3794:     */             {
/* 3795:3965 */               ticket.setTipoDeDocumento(filaTicket[cctk.getIndice()].toString().trim());
/* 3796:     */             }
/* 3797:3966 */             else if ((cctk.getNombreEtiqueta().equals("IDENTIFICACION TRIBUTARIA")) && (cctk.getIndice() >= 0) && 
/* 3798:3967 */               (filaTicket[cctk.getIndice()] != null))
/* 3799:     */             {
/* 3800:3968 */               filaTicket[cctk.getIndice()].setCellType(1);
/* 3801:3969 */               ticket.setIdentificacionTributaria(filaTicket[cctk.getIndice()].toString().trim());
/* 3802:     */             }
/* 3803:3971 */             else if ((cctk.getNombreEtiqueta().equals("RUTA")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3804:     */             {
/* 3805:3972 */               ticket.setRuta(filaTicket[cctk.getIndice()].toString().trim());
/* 3806:     */             }
/* 3807:3974 */             else if ((cctk.getNombreEtiqueta().equals("FECHA DE VIAJE")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3808:     */             {
/* 3809:3975 */               if (!filaTicket[cctk.getIndice()].toString().trim().equals("")) {
/* 3810:     */                 try
/* 3811:     */                 {
/* 3812:3977 */                   ticket.setFechaViaje(filaTicket[cctk.getIndice()].getDateCellValue());
/* 3813:     */                 }
/* 3814:     */                 catch (Exception e)
/* 3815:     */                 {
/* 3816:3979 */                   ticket.setFechaViaje(FuncionesUtiles.stringToDate(filaTicket[cctk.getIndice()].toString()));
/* 3817:     */                 }
/* 3818:     */               }
/* 3819:     */             }
/* 3820:3984 */             else if ((cctk.getNombreEtiqueta().equals("CODIGO DE SERVICIO")) && (cctk.getIndice() >= 0) && 
/* 3821:3985 */               (filaTicket[cctk.getIndice()] != null))
/* 3822:     */             {
/* 3823:3986 */               ticket.setCodigoDeServicio(filaTicket[cctk.getIndice()].toString().trim());
/* 3824:     */             }
/* 3825:3987 */             else if ((cctk.getNombreEtiqueta().equals("PENALIDADES Y SERVICIOS")) && (cctk.getIndice() >= 0) && 
/* 3826:3988 */               (filaTicket[cctk.getIndice()] != null))
/* 3827:     */             {
/* 3828:3989 */               if ((!filaTicket[cctk.getIndice()].toString().trim().equals("")) && (!filaTicket[cctk.getIndice()].toString().equals("0"))) {
/* 3829:3990 */                 ticket.setPenalty(
/* 3830:3991 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].toString().replaceAll(",", ""))));
/* 3831:     */               }
/* 3832:     */             }
/* 3833:3992 */             else if ((cctk.getNombreEtiqueta().equals("FORMA DE PAGO")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3834:     */             {
/* 3835:3995 */               ticket.setFormaPago(filaTicket[cctk.getIndice()].toString().trim());
/* 3836:     */             }
/* 3837:3996 */             else if ((cctk.getNombreEtiqueta().equals("CUENTAS CORPORATIVAS")) && (cctk.getIndice() >= 0) && 
/* 3838:3997 */               (filaTicket[cctk.getIndice()] != null))
/* 3839:     */             {
/* 3840:3998 */               if ((!filaTicket[cctk.getIndice()].toString().trim().equals("")) && (!filaTicket[cctk.getIndice()].toString().trim().equals("0"))) {
/* 3841:3999 */                 ticket.setValorFormaPago(
/* 3842:4000 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3843:     */               }
/* 3844:     */             }
/* 3845:4001 */             else if ((cctk.getNombreEtiqueta().equals("# DOCUMENTO RELACIONADO")) && (cctk.getIndice() >= 0) && 
/* 3846:4002 */               (filaTicket[cctk.getIndice()] != null))
/* 3847:     */             {
/* 3848:4003 */               filaTicket[cctk.getIndice()].setCellType(1);
/* 3849:4004 */               ticket.setNumeroDocumentoRelacionado(filaTicket[cctk.getIndice()].toString().trim());
/* 3850:     */             }
/* 3851:4005 */             else if ((cctk.getNombreEtiqueta().equals("ANTICIPO")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3852:     */             {
/* 3853:4006 */               if (!filaTicket[cctk.getIndice()].toString().trim().equals("")) {
/* 3854:4007 */                 ticket.setAnticipo(
/* 3855:4008 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3856:     */               } else {
/* 3857:4010 */                 ticket.setAnticipo(BigDecimal.ZERO);
/* 3858:     */               }
/* 3859:     */             }
/* 3860:4011 */             else if ((cctk.getNombreEtiqueta().equals("DESCUENTO")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3861:     */             {
/* 3862:4012 */               if (!filaTicket[cctk.getIndice()].toString().trim().equals("")) {
/* 3863:4013 */                 ticket.setDescuento(
/* 3864:4014 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3865:     */               } else {
/* 3866:4016 */                 ticket.setDescuento(BigDecimal.ZERO);
/* 3867:     */               }
/* 3868:     */             }
/* 3869:4017 */             else if ((cctk.getNombreEtiqueta().equals("OBSERVACIONES")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3870:     */             {
/* 3871:4018 */               ticket.setObservaciones(filaTicket[cctk.getIndice()].toString().trim());
/* 3872:     */             }
/* 3873:4019 */             else if ((cctk.getNombreEtiqueta().equals("TIPO DE TARJETA DE CREDITO")) && (cctk.getIndice() >= 0) && 
/* 3874:4020 */               (filaTicket[cctk.getIndice()] != null))
/* 3875:     */             {
/* 3876:4021 */               ticket.setTipoTarjetaCredito(filaTicket[cctk.getIndice()].toString().trim());
/* 3877:     */             }
/* 3878:4022 */             else if ((cctk.getNombreEtiqueta().equals("MONEDA")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3879:     */             {
/* 3880:4023 */               ticket.setMoneda(filaTicket[cctk.getIndice()].toString().trim());
/* 3881:     */             }
/* 3882:4024 */             else if ((cctk.getNombreEtiqueta().equals("CODIGO AGENTE")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3883:     */             {
/* 3884:4025 */               filaTicket[cctk.getIndice()].setCellType(1);
/* 3885:4026 */               ticket.setCodigoAgente(filaTicket[cctk.getIndice()].toString().trim());
/* 3886:     */             }
/* 3887:4029 */             else if ((cctk.getNombreEtiqueta().equals("RECORD")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3888:     */             {
/* 3889:4030 */               ticket.setRecord(filaTicket[cctk.getIndice()].toString().trim());
/* 3890:     */             }
/* 3891:4031 */             else if ((cctk.getNombreEtiqueta().equals("FECHA DE REPORTE")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3892:     */             {
/* 3893:4032 */               if (!filaTicket[cctk.getIndice()].toString().trim().equals(""))
/* 3894:     */               {
/* 3895:     */                 try
/* 3896:     */                 {
/* 3897:4034 */                   ticket.setPeriodo(filaTicket[cctk.getIndice()].getDateCellValue());
/* 3898:     */                 }
/* 3899:     */                 catch (Exception e)
/* 3900:     */                 {
/* 3901:4036 */                   ticket.setPeriodo(FuncionesUtiles.stringToDate(filaTicket[cctk.getIndice()].toString()));
/* 3902:     */                 }
/* 3903:4038 */                 ticket.setFechaReporte(ticket.getPeriodo());
/* 3904:     */               }
/* 3905:     */             }
/* 3906:4040 */             else if ((cctk.getNombreEtiqueta().equals("FECHA DE EMISION")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3907:     */             {
/* 3908:4041 */               if (!filaTicket[cctk.getIndice()].toString().trim().equals("")) {
/* 3909:     */                 try
/* 3910:     */                 {
/* 3911:4043 */                   ticket.setFecha(filaTicket[cctk.getIndice()].getDateCellValue());
/* 3912:     */                 }
/* 3913:     */                 catch (Exception e)
/* 3914:     */                 {
/* 3915:4045 */                   ticket.setFecha(FuncionesUtiles.stringToDate(filaTicket[cctk.getIndice()].toString()));
/* 3916:     */                 }
/* 3917:     */               }
/* 3918:     */             }
/* 3919:4049 */             else if ((cctk.getNombreEtiqueta().equals("TIPO DE TRANSACCION")) && (cctk.getIndice() >= 0) && 
/* 3920:4050 */               (filaTicket[cctk.getIndice()] != null))
/* 3921:     */             {
/* 3922:4051 */               if (!filaTicket[cctk.getIndice()].toString().trim().equals("")) {
/* 3923:4052 */                 ticket.setTipoTransaccion(filaTicket[cctk.getIndice()].toString().trim());
/* 3924:     */               }
/* 3925:     */             }
/* 3926:4053 */             else if ((cctk.getNombreEtiqueta().equals("NOMBRE PASAJERO")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3927:     */             {
/* 3928:4054 */               if (!filaTicket[cctk.getIndice()].toString().trim().equals("")) {
/* 3929:4055 */                 ticket.setPasajero(filaTicket[cctk.getIndice()].toString().trim());
/* 3930:     */               }
/* 3931:     */             }
/* 3932:4056 */             else if ((cctk.getNombreEtiqueta().equals("TARIFA PRELIMINAR")) && (cctk.getIndice() >= 0) && 
/* 3933:4057 */               (filaTicket[cctk.getIndice()] != null))
/* 3934:     */             {
/* 3935:4058 */               if ((filaTicket[cctk.getIndice()].toString().trim().equals("")) || (filaTicket[cctk.getIndice()].toString().trim().equals("0")) || 
/* 3936:4059 */                 (filaTicket[cctk.getIndice()].toString().trim().equals("0,00"))) {
/* 3937:4060 */                 ticket.setTarifaPreliminar(BigDecimal.ZERO);
/* 3938:4062 */               } else if (!filaTicket[cctk.getIndice()].toString().trim().equals("")) {
/* 3939:4063 */                 ticket.setTarifaPreliminar(
/* 3940:4064 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3941:     */               }
/* 3942:     */             }
/* 3943:4066 */             else if ((cctk.getNombreEtiqueta().equals("VALOR TOTAL PRELIMINAR")) && (cctk.getIndice() >= 0) && 
/* 3944:4067 */               (filaTicket[cctk.getIndice()] != null))
/* 3945:     */             {
/* 3946:4068 */               if ((filaTicket[cctk.getIndice()].toString().trim().equals("")) || (filaTicket[cctk.getIndice()].toString().trim().equals("0")) || 
/* 3947:4069 */                 (filaTicket[cctk.getIndice()].toString().trim().equals("0,00"))) {
/* 3948:4070 */                 ticket.setTarifaPreliminar(BigDecimal.ZERO);
/* 3949:4072 */               } else if (!filaTicket[cctk.getIndice()].toString().trim().equals("")) {
/* 3950:4073 */                 ticket.setValorTotalPreliminar(
/* 3951:4074 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3952:     */               }
/* 3953:     */             }
/* 3954:4076 */             else if ((cctk.getNombreEtiqueta().equals("COMISION")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3955:     */             {
/* 3956:4077 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals(""))) {
/* 3957:4078 */                 ticket.setComision(
/* 3958:4079 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3959:     */               }
/* 3960:     */             }
/* 3961:4081 */             else if ((cctk.getNombreEtiqueta().equals("IVA COMISION")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3962:     */             {
/* 3963:4082 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals(""))) {
/* 3964:4083 */                 ticket.setIvaComision(
/* 3965:4084 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3966:     */               }
/* 3967:     */             }
/* 3968:4086 */             else if ((cctk.getNombreEtiqueta().equals("Retencion Fte")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3969:     */             {
/* 3970:4087 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals(""))) {
/* 3971:4088 */                 ticket.setRetencionFte(
/* 3972:4089 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3973:     */               }
/* 3974:     */             }
/* 3975:4091 */             else if ((cctk.getNombreEtiqueta().equals("Porcentaje Comision")) && (cctk.getIndice() >= 0) && 
/* 3976:4092 */               (filaTicket[cctk.getIndice()] != null))
/* 3977:     */             {
/* 3978:4093 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals(""))) {
/* 3979:4094 */                 ticket.setPorComision(
/* 3980:4095 */                   FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3981:     */               }
/* 3982:     */             }
/* 3983:4097 */             else if ((cctk.getNombreEtiqueta().equals("Neto a Pagar")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3984:     */             {
/* 3985:4098 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals(""))) {
/* 3986:4099 */                 ticket.setNeto(FuncionesUtiles.redondearBigDecimal(new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue())));
/* 3987:     */               }
/* 3988:     */             }
/* 3989:4101 */             else if ((cctk.getNombreEtiqueta().equals("# Periodo")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3990:     */             {
/* 3991:4102 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals("")))
/* 3992:     */               {
/* 3993:4103 */                 filaTicket[cctk.getIndice()].setCellType(1);
/* 3994:4104 */                 ticket.setNumeroPeriodo(filaTicket[cctk.getIndice()].toString());
/* 3995:     */               }
/* 3996:     */             }
/* 3997:4106 */             else if ((cctk.getNombreEtiqueta().equals("Periodo BSP")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 3998:     */             {
/* 3999:4107 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals(""))) {
/* 4000:4108 */                 ticket.setPeriodoBSP(filaTicket[cctk.getIndice()].toString());
/* 4001:     */               }
/* 4002:     */             }
/* 4003:4110 */             else if ((cctk.getNombreEtiqueta().equals("ORIGINAL (1) / CONJUNCION (2)")) && (cctk.getIndice() >= 0) && 
/* 4004:4111 */               (filaTicket[cctk.getIndice()] != null))
/* 4005:     */             {
/* 4006:4112 */               if ((filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals(""))) {
/* 4007:4113 */                 ticket.setOriginalConjuncion(filaTicket[cctk.getIndice()].toString().replace(".0", ""));
/* 4008:     */               }
/* 4009:     */             }
/* 4010:4115 */             else if ((cctk.getNombreEtiqueta().equals("CREDITO")) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null))
/* 4011:     */             {
/* 4012:4116 */               if (filaTicket[cctk.getIndice()].toString().trim().equals("YES")) {
/* 4013:4117 */                 ticket.setIsCredito(Boolean.valueOf(true));
/* 4014:4119 */               } else if (filaTicket[cctk.getIndice()].toString().trim().equals("NO")) {
/* 4015:4120 */                 ticket.setIsCredito(Boolean.valueOf(false));
/* 4016:     */               }
/* 4017:     */             }
/* 4018:4123 */             else if ((cctk.getNombreEtiqueta().length() == 2) && (cctk.getIndice() >= 0) && (filaTicket[cctk.getIndice()] != null) && 
/* 4019:4124 */               (filaTicket[cctk.getIndice()] != null) && (!filaTicket[cctk.getIndice()].toString().trim().equals("")) && 
/* 4020:4125 */               (!filaTicket[cctk.getIndice()].toString().equals("0.00")))
/* 4021:     */             {
/* 4022:4127 */               BigDecimal valor = new BigDecimal(filaTicket[cctk.getIndice()].getNumericCellValue());
/* 4023:4128 */               if (valor.compareTo(BigDecimal.ZERO) != 0)
/* 4024:     */               {
/* 4025:4129 */                 DetalleTicket detalleTicket = new DetalleTicket();
/* 4026:4130 */                 if ((cctk.getNombreEtiqueta().equals("EC")) || (cctk.getNombreEtiqueta().equals("ED")) || 
/* 4027:4131 */                   (cctk.getNombreEtiqueta().equals("E2")) || (cctk.getNombreEtiqueta().equals("QB")) || 
/* 4028:4132 */                   (cctk.getNombreEtiqueta().equals("QI")) || (cctk.getNombreEtiqueta().equals("WT")) || 
/* 4029:4133 */                   (cctk.getNombreEtiqueta().equals("YQ"))) {
/* 4030:4134 */                   detalleTicket.setIndicadorNacional(Boolean.valueOf(true));
/* 4031:     */                 } else {
/* 4032:4136 */                   detalleTicket.setIndicadorNacional(Boolean.valueOf(false));
/* 4033:     */                 }
/* 4034:4138 */                 detalleTicket.setTaxMiscFeeAmt1(FuncionesUtiles.redondearBigDecimal(valor));
/* 4035:4139 */                 detalleTicket.setTaxMiscFeeType1(cctk.getNombreEtiqueta());
/* 4036:4140 */                 detalleTicket.setStdMsgId("BKS");
/* 4037:4141 */                 detalleTicket.setStdNumQual("30");
/* 4038:4142 */                 detalleTicket.setTicket(ticket);
/* 4039:4143 */                 ticket.getListaDetalleTicket().add(detalleTicket);
/* 4040:     */               }
/* 4041:     */             }
/* 4042:     */           }
/* 4043:4150 */           listaTickets.add(ticket);
/* 4044:     */         }
/* 4045:     */       }
/* 4046:     */     }
/* 4047:     */     catch (ExcepcionAS2 e)
/* 4048:     */     {
/* 4049:4154 */       throw e;
/* 4050:     */     }
/* 4051:     */     catch (Exception e)
/* 4052:     */     {
/* 4053:4157 */       e.printStackTrace();
/* 4054:4158 */       throw new ExcepcionAS2(e);
/* 4055:     */     }
/* 4056:4160 */     return listaTickets;
/* 4057:     */   }
/* 4058:     */   
/* 4059:     */   public void cargarTomaFisica(int idOrganizacion, InputStream imInputStream, int filaInicial, List<DetalleTomaFisica> listaDetalleTomaFisica, List<String> codigosProducto)
/* 4060:     */     throws ExcepcionAS2, IOException
/* 4061:     */   {
/* 4062:4166 */     int filaActual = filaInicial;
/* 4063:4167 */     int columnaActual = 0;
/* 4064:4168 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 4065:4169 */     HashMap<String, DetalleTomaFisica> hmDetalleTomaFisica = new HashMap();
/* 4066:4170 */     for (Iterator localIterator = listaDetalleTomaFisica.iterator(); localIterator.hasNext();)
/* 4067:     */     {
/* 4068:4170 */       dtf = (DetalleTomaFisica)localIterator.next();
/* 4069:4171 */       claveProducto = dtf.getProducto().getCodigo();
/* 4070:4172 */       claveLote = dtf.getLote() != null ? dtf.getLote().getCodigo() : "n/a";
/* 4071:4173 */       hmDetalleTomaFisica.put(claveProducto + "~" + claveLote.trim(), dtf);
/* 4072:     */     }
/* 4073:4175 */     HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 4074:4176 */     DetalleTomaFisica dtf = datos;String claveProducto = dtf.length;
/* 4075:4176 */     for (String claveLote = 0; claveLote < claveProducto; claveLote++)
/* 4076:     */     {
/* 4077:4176 */       HSSFCell[] filaEmpleado = dtf[claveLote];
/* 4078:     */       
/* 4079:4178 */       filaErronea = filaEmpleado;
/* 4080:4179 */       filaActual++;
/* 4081:4180 */       String codigoProducto = (String)FuncionesUtiles.validarCelda(filaEmpleado, FormatoCelda.TEXTO, filaActual, columnaActual = 0, true, Integer.valueOf(1), 
/* 4082:4181 */         Integer.valueOf(20));
/* 4083:4182 */       String codigoLote = (String)FuncionesUtiles.validarCelda(filaEmpleado, FormatoCelda.TEXTO, filaActual, columnaActual = 6, true, Integer.valueOf(1), Integer.valueOf(20));
/* 4084:4183 */       BigDecimal cantidadTomaFisica = (BigDecimal)FuncionesUtiles.validarCelda(filaEmpleado, FormatoCelda.NUMERO, filaActual, columnaActual = 8, true, 
/* 4085:4184 */         Integer.valueOf(0), Integer.valueOf(0));
/* 4086:4185 */       DetalleTomaFisica detalleTomaFisica = (DetalleTomaFisica)hmDetalleTomaFisica.get(codigoProducto + "~" + codigoLote.trim());
/* 4087:4186 */       if (detalleTomaFisica != null) {
/* 4088:4187 */         detalleTomaFisica.setCantidadTomaFisica(cantidadTomaFisica);
/* 4089:     */       } else {
/* 4090:4189 */         codigosProducto.add(codigoProducto.trim() + "~" + codigoLote.trim());
/* 4091:     */       }
/* 4092:     */     }
/* 4093:     */   }
/* 4094:     */   
/* 4095:     */   public void migracionListaDescuentos(Date fechaDesde, int idListaDescuentos, String fileName, InputStream imInputStream, int filaInicial, int idOrganizacion, int idSucursal)
/* 4096:     */     throws ExcepcionAS2
/* 4097:     */   {
/* 4098:4200 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 4099:4201 */     int filaActual = filaInicial;
/* 4100:4202 */     int columnaActual = 0;
/* 4101:     */     try
/* 4102:     */     {
/* 4103:4205 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/* 4104:     */       
/* 4105:4207 */       ListaDescuentos listaDescuentos = this.servicioListaDescuentos.cargarDetalle(idListaDescuentos);
/* 4106:4208 */       for (VersionListaDescuentos versionListaDescuentos : listaDescuentos.getListaVersionesListaDescuentos()) {
/* 4107:4209 */         if (versionListaDescuentos.getValidoHasta() == null)
/* 4108:     */         {
/* 4109:4210 */           versionListaDescuentos.setValidoHasta(fechaDesde);
/* 4110:4211 */           versionListaDescuentos.setActivo(false);
/* 4111:     */         }
/* 4112:     */       }
/* 4113:4215 */       VersionListaDescuentos versionListaDescuentos = new VersionListaDescuentos();
/* 4114:4216 */       versionListaDescuentos.setIdOrganizacion(idOrganizacion);
/* 4115:4217 */       versionListaDescuentos.setIdSucursal(idSucursal);
/* 4116:4218 */       versionListaDescuentos.setDescripcion("ListaDescuentos_" + FuncionesUtiles.dateToString(fechaDesde));
/* 4117:4219 */       versionListaDescuentos.setValidoDesde(fechaDesde);
/* 4118:4220 */       versionListaDescuentos.setListaDetalleListaDescuentos(new ArrayList());
/* 4119:4221 */       versionListaDescuentos.setListaDescuentos(listaDescuentos);
/* 4120:4222 */       versionListaDescuentos.setActivo(true);
/* 4121:4223 */       listaDescuentos.getListaVersionesListaDescuentos().add(versionListaDescuentos);
/* 4122:     */       
/* 4123:4225 */       HashMap<String, Producto> hashMapProducto = new HashMap();
/* 4124:4226 */       HashMap<String, String> filters = new HashMap();
/* 4125:4227 */       filters.put("indicadorVenta", "true");
/* 4126:4228 */       filters.put("activo", "true");
/* 4127:4229 */       filters.put("idOrganizacion", "" + idOrganizacion);
/* 4128:4230 */       for (Object localObject = this.servicioProducto.obtenerListaCombo("", false, filters).iterator(); ((Iterator)localObject).hasNext();)
/* 4129:     */       {
/* 4130:4230 */         p = (Producto)((Iterator)localObject).next();
/* 4131:4231 */         hashMapProducto.put(p.getCodigo(), p);
/* 4132:     */       }
/* 4133:4235 */       localObject = datos;Producto p = localObject.length;
/* 4134:4235 */       for (Producto localProducto1 = 0; localProducto1 < p; localProducto1++)
/* 4135:     */       {
/* 4136:4235 */         HSSFCell[] fila = localObject[localProducto1];
/* 4137:     */         
/* 4138:4237 */         filaErronea = fila;
/* 4139:4238 */         columnaActual = 0;
/* 4140:4239 */         fila[columnaActual].setCellType(1);
/* 4141:4240 */         String codigoProducto = fila[0].toString();
/* 4142:     */         
/* 4143:4242 */         BigDecimal descuento = null;
/* 4144:4243 */         columnaActual = 5;
/* 4145:4244 */         fila[columnaActual].setCellType(1);
/* 4146:4246 */         if ((fila[columnaActual] != null) && (!fila[columnaActual].toString().trim().equals("")) && 
/* 4147:4247 */           (!fila[columnaActual].toString().trim().equals("0"))) {
/* 4148:4248 */           descuento = FuncionesUtiles.redondearBigDecimal(new BigDecimal(fila[columnaActual].toString()));
/* 4149:     */         } else {
/* 4150:4250 */           descuento = BigDecimal.ZERO;
/* 4151:     */         }
/* 4152:4252 */         if ((hashMapProducto.containsKey(codigoProducto)) && (descuento.compareTo(BigDecimal.ZERO) != 0))
/* 4153:     */         {
/* 4154:4253 */           Producto producto = (Producto)hashMapProducto.get(codigoProducto);
/* 4155:4254 */           if (hashMapProducto.containsKey(codigoProducto))
/* 4156:     */           {
/* 4157:4255 */             DetalleListaDescuentos detalleListaDescuentos = new DetalleListaDescuentos();
/* 4158:4256 */             detalleListaDescuentos.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 4159:4257 */             detalleListaDescuentos.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 4160:4258 */             detalleListaDescuentos.setProducto(producto);
/* 4161:4259 */             detalleListaDescuentos.setPorcentajeDescuentoMaximo(descuento);
/* 4162:4260 */             detalleListaDescuentos.setVersionListaDescuentos(versionListaDescuentos);
/* 4163:4261 */             versionListaDescuentos.getListaDetalleListaDescuentos().add(detalleListaDescuentos);
/* 4164:     */           }
/* 4165:     */         }
/* 4166:     */       }
/* 4167:4265 */       this.servicioListaDescuentos.guardar(listaDescuentos);
/* 4168:     */     }
/* 4169:     */     catch (IllegalArgumentException e)
/* 4170:     */     {
/* 4171:4267 */       LOG.info("Error al migrar de lista de descuentos", e);
/* 4172:4268 */       this.context.setRollbackOnly();
/* 4173:     */       
/* 4174:4270 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 4175:     */     }
/* 4176:     */     catch (IllegalStateException e)
/* 4177:     */     {
/* 4178:4272 */       LOG.info("Error al migrar de lista de descuentos", e);
/* 4179:4273 */       this.context.setRollbackOnly();
/* 4180:     */       
/* 4181:4275 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 4182:     */     }
/* 4183:     */     catch (Exception e)
/* 4184:     */     {
/* 4185:4277 */       LOG.info("Error al migrar de lista de descuentos", e);
/* 4186:4278 */       this.context.setRollbackOnly();
/* 4187:4279 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 4188:     */     }
/* 4189:     */   }
/* 4190:     */   
/* 4191:     */   public void migracionPartidasPresupuestarias(int idOrganizacion, InputStream imInputStream, int filaInicial)
/* 4192:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 4193:     */   {
/* 4194:4287 */     int filaActual = filaInicial;
/* 4195:4288 */     int columnaErronea = -1;
/* 4196:4289 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 4197:     */     try
/* 4198:     */     {
/* 4199:4294 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 4200:     */       
/* 4201:     */ 
/* 4202:     */ 
/* 4203:     */ 
/* 4204:4299 */       String codigoCuentaContableMayor = "";
/* 4205:     */       
/* 4206:     */ 
/* 4207:     */ 
/* 4208:4303 */       HashMap<String, PartidaPresupuestaria> hashMapPartidaPresupuestaria = new HashMap();
/* 4209:4304 */       HashMap<String, String> filtros = new HashMap();
/* 4210:4305 */       filtros.put("idOrganizacion", "" + idOrganizacion);
/* 4211:4306 */       List<PartidaPresupuestaria> listaPartidaPresupuestaria = this.servicioPartidaPresupuestaria.obtenerListaCombo("", false, filtros);
/* 4212:4307 */       for (PartidaPresupuestaria cc : listaPartidaPresupuestaria) {
/* 4213:4308 */         hashMapPartidaPresupuestaria.put(cc.getCodigo(), cc);
/* 4214:     */       }
/* 4215:4312 */       Object hashMapNivelPartidaPresupuestaria = new HashMap();
/* 4216:4313 */       List<NivelPartidaPresupuestaria> listaNivelPartidaPresupuestaria = this.servicioNivelPartidaPresupuestaria.obtenerListaCombo("codigo", true, null);
/* 4217:4315 */       for (Object localObject1 = listaNivelPartidaPresupuestaria.iterator(); ((Iterator)localObject1).hasNext();)
/* 4218:     */       {
/* 4219:4315 */         nc = (NivelPartidaPresupuestaria)((Iterator)localObject1).next();
/* 4220:4316 */         ((HashMap)hashMapNivelPartidaPresupuestaria).put(Integer.valueOf(nc.getCodigo()), nc);
/* 4221:     */       }
/* 4222:4321 */       localObject1 = datos;NivelPartidaPresupuestaria nc = localObject1.length;
/* 4223:4321 */       for (Object localObject2 = 0; localObject2 < nc; localObject2++)
/* 4224:     */       {
/* 4225:4321 */         fila = localObject1[localObject2];
/* 4226:4322 */         codigoCuenta = fila[0].getStringCellValue().trim();
/* 4227:4323 */         if (codigoCuentaContableMayor.length() < codigoCuenta.length()) {
/* 4228:4324 */           codigoCuentaContableMayor = codigoCuenta;
/* 4229:     */         }
/* 4230:     */       }
/* 4231:4328 */       String[] cadenaNivel = codigoCuentaContableMayor.split("\\.");
/* 4232:4329 */       int codigoNivelCuenta = 1;
/* 4233:4330 */       localObject2 = cadenaNivel;HSSFCell[] fila = localObject2.length;
/* 4234:4330 */       for (String codigoCuenta = 0; codigoCuenta < fila; codigoCuenta++)
/* 4235:     */       {
/* 4236:4330 */         String cadena = localObject2[codigoCuenta];
/* 4237:4332 */         if (!((HashMap)hashMapNivelPartidaPresupuestaria).containsKey(Integer.valueOf(codigoNivelCuenta)))
/* 4238:     */         {
/* 4239:4334 */           NivelPartidaPresupuestaria nivelPartidaPresupuestaria = new NivelPartidaPresupuestaria();
/* 4240:4335 */           nivelPartidaPresupuestaria.setIdOrganizacion(idOrganizacion);
/* 4241:4336 */           nivelPartidaPresupuestaria.setIdSucursal(AppUtil.getSucursal().getId());
/* 4242:4337 */           nivelPartidaPresupuestaria.setCodigo(codigoNivelCuenta);
/* 4243:4338 */           nivelPartidaPresupuestaria.setNombre("Nivel " + codigoNivelCuenta);
/* 4244:4339 */           nivelPartidaPresupuestaria.setLongitud(cadena.length());
/* 4245:4340 */           nivelPartidaPresupuestaria.setActivo(true);
/* 4246:4341 */           this.servicioNivelPartidaPresupuestaria.guardar(nivelPartidaPresupuestaria);
/* 4247:4342 */           ((HashMap)hashMapNivelPartidaPresupuestaria).put(Integer.valueOf(codigoNivelCuenta), nivelPartidaPresupuestaria);
/* 4248:     */         }
/* 4249:4346 */         codigoNivelCuenta++;
/* 4250:     */       }
/* 4251:4348 */       localObject2 = datos;fila = localObject2.length;
/* 4252:4348 */       for (codigoCuenta = 0; codigoCuenta < fila; codigoCuenta++)
/* 4253:     */       {
/* 4254:4348 */         HSSFCell[] fila = localObject2[codigoCuenta];
/* 4255:     */         
/* 4256:4350 */         filaErronea = fila;
/* 4257:4351 */         filaActual++;
/* 4258:     */         
/* 4259:     */ 
/* 4260:4354 */         fila[(columnaErronea = 0)].setCellType(1);
/* 4261:4355 */         String codigoPartidaPresupuestaria = fila[(columnaErronea = 0)].getStringCellValue().trim();
/* 4262:4356 */         fila[(columnaErronea = 1)].setCellType(1);
/* 4263:4357 */         String nombreGrupoPartidaPresupuestaria = fila[(columnaErronea = 1)].getStringCellValue().trim();
/* 4264:4358 */         fila[(columnaErronea = 2)].setCellType(1);
/* 4265:4359 */         String nombrePartidaPresupuestaria = fila[(columnaErronea = 2)].getStringCellValue().trim();
/* 4266:4360 */         fila[(columnaErronea = 3)].setCellType(1);
/* 4267:4361 */         String descripcionPartidaPresupuestaria = fila[(columnaErronea = 3)] != null ? fila[(columnaErronea = 3)].getStringCellValue() : null;
/* 4268:4362 */         fila[(columnaErronea = 4)].setCellType(1);
/* 4269:4363 */         boolean movimiento = fila[(columnaErronea = 4)].getStringCellValue().equalsIgnoreCase("SI");
/* 4270:4366 */         if (!hashMapPartidaPresupuestaria.containsKey(codigoPartidaPresupuestaria))
/* 4271:     */         {
/* 4272:4368 */           PartidaPresupuestaria partidaPresupuestaria = new PartidaPresupuestaria();
/* 4273:4369 */           partidaPresupuestaria.setIdOrganizacion(idOrganizacion);
/* 4274:4370 */           partidaPresupuestaria.setIdSucursal(AppUtil.getSucursal().getId());
/* 4275:4371 */           partidaPresupuestaria.setCodigo(codigoPartidaPresupuestaria);
/* 4276:4372 */           partidaPresupuestaria.setGrupoPartidaPresupuestaria(GrupoCuenta.valueOf(nombreGrupoPartidaPresupuestaria));
/* 4277:4373 */           partidaPresupuestaria.setNombre(nombrePartidaPresupuestaria);
/* 4278:4374 */           partidaPresupuestaria.setDescripcion(descripcionPartidaPresupuestaria);
/* 4279:4375 */           partidaPresupuestaria.setIndicadorMovimiento(movimiento);
/* 4280:4376 */           partidaPresupuestaria.setActivo(true);
/* 4281:4377 */           String[] nivel = codigoPartidaPresupuestaria.split("\\.");
/* 4282:4378 */           partidaPresupuestaria.setNivelPartidaPresupuestaria((NivelPartidaPresupuestaria)((HashMap)hashMapNivelPartidaPresupuestaria).get(Integer.valueOf(nivel.length)));
/* 4283:4379 */           if (nivel.length == 1)
/* 4284:     */           {
/* 4285:4380 */             partidaPresupuestaria.setPartidaPresupuestariaPadre(null);
/* 4286:     */           }
/* 4287:     */           else
/* 4288:     */           {
/* 4289:4382 */             String codigoCuentaPadre = codigoPartidaPresupuestaria.substring(0, codigoPartidaPresupuestaria
/* 4290:4383 */               .length() - partidaPresupuestaria.getNivelPartidaPresupuestaria().getLongitud() - 1);
/* 4291:4384 */             partidaPresupuestaria.setPartidaPresupuestariaPadre((PartidaPresupuestaria)hashMapPartidaPresupuestaria.get(codigoCuentaPadre));
/* 4292:     */           }
/* 4293:4386 */           this.servicioPartidaPresupuestaria.guardar(partidaPresupuestaria);
/* 4294:4387 */           hashMapPartidaPresupuestaria.put(codigoPartidaPresupuestaria, partidaPresupuestaria);
/* 4295:     */         }
/* 4296:     */       }
/* 4297:     */     }
/* 4298:     */     catch (IllegalArgumentException e)
/* 4299:     */     {
/* 4300:4393 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 4301:4394 */       this.context.setRollbackOnly();
/* 4302:     */       
/* 4303:4396 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 4304:     */     }
/* 4305:     */     catch (ExcepcionAS2Financiero e)
/* 4306:     */     {
/* 4307:4399 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 4308:4400 */       this.context.setRollbackOnly();
/* 4309:4401 */       throw e;
/* 4310:     */     }
/* 4311:     */     catch (IllegalStateException e)
/* 4312:     */     {
/* 4313:4404 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 4314:4405 */       this.context.setRollbackOnly();
/* 4315:     */       
/* 4316:4407 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 4317:     */     }
/* 4318:     */     catch (Exception e)
/* 4319:     */     {
/* 4320:4410 */       LOG.error("Error al migrar partidas presupuestarias", e);
/* 4321:4411 */       this.context.setRollbackOnly();
/* 4322:4412 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 4323:     */     }
/* 4324:     */   }
/* 4325:     */   
/* 4326:     */   public void asignacionPartidasPresupuestarias(int idOrganizacion, InputStream imInputStream, int filaInicial)
/* 4327:     */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/* 4328:     */   {
/* 4329:4421 */     int filaActual = filaInicial;
/* 4330:4422 */     int columnaErronea = -1;
/* 4331:4423 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 4332:     */     try
/* 4333:     */     {
/* 4334:4428 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 4335:     */       
/* 4336:     */ 
/* 4337:     */ 
/* 4338:     */ 
/* 4339:     */ 
/* 4340:     */ 
/* 4341:     */ 
/* 4342:4436 */       HashMap<String, PartidaPresupuestaria> hashMapPartidaPresupuestaria = new HashMap();
/* 4343:4437 */       HashMap<String, String> filtros = new HashMap();
/* 4344:4438 */       filtros.put("idOrganizacion", "" + idOrganizacion);
/* 4345:4439 */       List<PartidaPresupuestaria> listaPartidaPresupuestaria = this.servicioPartidaPresupuestaria.obtenerListaCombo("", false, filtros);
/* 4346:4440 */       for (PartidaPresupuestaria cc : listaPartidaPresupuestaria) {
/* 4347:4441 */         hashMapPartidaPresupuestaria.put(cc.getCodigo(), cc);
/* 4348:     */       }
/* 4349:4444 */       Object hashMapCuentaContable = new HashMap();
/* 4350:4445 */       List<CuentaContable> listaCuentaContable = this.servicioCuentaContable.obtenerListaCombo("", false, filtros);
/* 4351:4446 */       for (Object localObject1 = listaCuentaContable.iterator(); ((Iterator)localObject1).hasNext();)
/* 4352:     */       {
/* 4353:4446 */         cc = (CuentaContable)((Iterator)localObject1).next();
/* 4354:4447 */         ((HashMap)hashMapCuentaContable).put(cc.getCodigo(), cc);
/* 4355:     */       }
/* 4356:4450 */       localObject1 = datos;CuentaContable cc = localObject1.length;
/* 4357:4450 */       for (CuentaContable localCuentaContable1 = 0; localCuentaContable1 < cc; localCuentaContable1++)
/* 4358:     */       {
/* 4359:4450 */         HSSFCell[] fila = localObject1[localCuentaContable1];
/* 4360:     */         
/* 4361:4452 */         filaErronea = fila;
/* 4362:4453 */         filaActual++;
/* 4363:     */         
/* 4364:     */ 
/* 4365:4456 */         fila[(columnaErronea = 0)].setCellType(1);
/* 4366:4457 */         String codigoPartidaPresupuestaria = fila[(columnaErronea = 0)].getStringCellValue().trim();
/* 4367:4458 */         fila[(columnaErronea = 1)].setCellType(1);
/* 4368:4459 */         String codigoCuentaContable = fila[(columnaErronea = 1)].getStringCellValue().trim();
/* 4369:     */         
/* 4370:4461 */         PartidaPresupuestaria partidaPresupuestaria = (PartidaPresupuestaria)hashMapPartidaPresupuestaria.get(codigoPartidaPresupuestaria);
/* 4371:4462 */         CuentaContable cuentaContable = (CuentaContable)((HashMap)hashMapCuentaContable).get(codigoCuentaContable);
/* 4372:4464 */         if ((partidaPresupuestaria != null) && (cuentaContable != null))
/* 4373:     */         {
/* 4374:4465 */           if (!partidaPresupuestaria.isIndicadorMovimiento()) {
/* 4375:4466 */             throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.MENSAJE_ERROR_PARTIDA_PRESUPUESTARIA_NO_MIVIMIENTO", new String[] { partidaPresupuestaria.getCodigo(), "\nFila: " + filaActual + " Columna: " + columnaErronea });
/* 4376:     */           }
/* 4377:4468 */           if (!cuentaContable.isIndicadorMovimiento()) {
/* 4378:4469 */             throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.MENSAJE_ERROR_CUENTA_CONTABLE_NO_MIVIMIENTO", new String[] { cuentaContable.getCodigo(), "\nFila: " + filaActual + " Columna: " + columnaErronea });
/* 4379:     */           }
/* 4380:4471 */           if (cuentaContable.getPartidaPresupuestaria() != null) {
/* 4381:4473 */             throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.MENSAJE_ERROR_CUENTA_CONTABLE_TIENE_PARTIDA_PARTIDA_PRESUPUESTARIA", new String[] {cuentaContable.getCodigo(), "\nFila: " + filaActual + " Columna: " + columnaErronea });
/* 4382:     */           }
/* 4383:4475 */           partidaPresupuestaria = this.servicioPartidaPresupuestaria.cargarDetalle(partidaPresupuestaria.getId());
/* 4384:     */           
/* 4385:4477 */           cuentaContable.setPartidaPresupuestaria(partidaPresupuestaria);
/* 4386:4478 */           partidaPresupuestaria.getListaCuentaContable().add(cuentaContable);
/* 4387:4479 */           this.servicioPartidaPresupuestaria.guardar(partidaPresupuestaria);
/* 4388:4480 */           this.servicioCuentaContable.guardar(cuentaContable);
/* 4389:     */         }
/* 4390:     */         else
/* 4391:     */         {
/* 4392:4483 */           if (partidaPresupuestaria == null) {
/* 4393:4484 */             throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.MENSAJE_ERROR_PARTIDA_PRESUPUESTARIA_NO_EXISTE", new String[] { codigoPartidaPresupuestaria, "\nFila: " + filaActual + " Columna: " + columnaErronea });
/* 4394:     */           }
/* 4395:4487 */           throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.MENSAJE_ERROR_CUENTA_CONTABLE_NO_EXISTE", new String[] { codigoCuentaContable, "\nFila: " + filaActual + " Columna: " + columnaErronea });
/* 4396:     */         }
/* 4397:     */       }
/* 4398:     */     }
/* 4399:     */     catch (IllegalArgumentException e)
/* 4400:     */     {
/* 4401:4493 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 4402:4494 */       this.context.setRollbackOnly();
/* 4403:     */       
/* 4404:4496 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 4405:     */     }
/* 4406:     */     catch (AS2Exception e)
/* 4407:     */     {
/* 4408:4499 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 4409:4500 */       this.context.setRollbackOnly();
/* 4410:4501 */       throw e;
/* 4411:     */     }
/* 4412:     */     catch (IllegalStateException e)
/* 4413:     */     {
/* 4414:4504 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 4415:4505 */       this.context.setRollbackOnly();
/* 4416:     */       
/* 4417:4507 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 4418:     */     }
/* 4419:     */     catch (Exception e)
/* 4420:     */     {
/* 4421:4510 */       LOG.error("Error al migrar partidas presupuestarias", e);
/* 4422:4511 */       this.context.setRollbackOnly();
/* 4423:4512 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 4424:     */     }
/* 4425:     */   }
/* 4426:     */   
/* 4427:     */   public void migracionEquipos(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial)
/* 4428:     */     throws ExcepcionAS2
/* 4429:     */   {
/* 4430:4521 */     String sucursal = null;
/* 4431:4522 */     String puntoVenta = null;
/* 4432:4523 */     String secuencial = null;
/* 4433:     */     
/* 4434:     */ 
/* 4435:     */ 
/* 4436:     */ 
/* 4437:     */ 
/* 4438:4529 */     HashMap<String, ComponenteEquipo> hashMapComponenteEquipo = new HashMap();
/* 4439:4530 */     List<ComponenteEquipo> listaComponenteEquipo = this.servicioComponenteEquipo.obtenerListaCombo(ComponenteEquipo.class, "", false, null);
/* 4440:4531 */     for (ComponenteEquipo cp : listaComponenteEquipo) {
/* 4441:4532 */       hashMapComponenteEquipo.put(cp.getCodigo(), cp);
/* 4442:     */     }
/* 4443:4535 */     Object hashMapUbicacionActivo = new HashMap();
/* 4444:4536 */     List<UbicacionActivo> listaUbicacionActivo = this.servicioUbicacionActivo.obtenerListaCombo("", false, null);
/* 4445:4537 */     for (UbicacionActivo ua : listaUbicacionActivo) {
/* 4446:4538 */       ((HashMap)hashMapUbicacionActivo).put(ua.getCodigo(), ua);
/* 4447:     */     }
/* 4448:4541 */     Object hashMapCategoriaEquipo = new HashMap();
/* 4449:4542 */     List<CategoriaEquipo> listaCategoriaEquipo = this.servicioCategoriaEquipo.obtenerListaCombo(CategoriaEquipo.class, "", false, null);
/* 4450:4543 */     for (CategoriaEquipo ce : listaCategoriaEquipo) {
/* 4451:4544 */       ((HashMap)hashMapCategoriaEquipo).put(ce.getCodigo(), ce);
/* 4452:     */     }
/* 4453:4548 */     Object hashMapSubcategoriaEquipo = new HashMap();
/* 4454:4549 */     List<SubcategoriaEquipo> listaSubcategoriaEquipo = this.servicioSubCategoriaEquipo.obtenerListaCombo(SubcategoriaEquipo.class, "", false, null);
/* 4455:4550 */     for (SubcategoriaEquipo se : listaSubcategoriaEquipo) {
/* 4456:4551 */       ((HashMap)hashMapSubcategoriaEquipo).put(se.getCodigo(), se);
/* 4457:     */     }
/* 4458:4554 */     Object hashMapDimensionContable = new HashMap();
/* 4459:4555 */     List<DimensionContable> listaDimensionContable = this.servicioDimensionContable.obtenerListaCombo("", false, null);
/* 4460:4556 */     for (DimensionContable dc : listaDimensionContable) {
/* 4461:4557 */       ((HashMap)hashMapDimensionContable).put(dc.getCodigo(), dc);
/* 4462:     */     }
/* 4463:4560 */     Object hashMapEquipo = new HashMap();
/* 4464:4561 */     List<Equipo> listaEquipo = this.servicioEquipo.obtenerListaCombo("", false, null);
/* 4465:4562 */     for (Iterator localIterator6 = listaEquipo.iterator(); localIterator6.hasNext();)
/* 4466:     */     {
/* 4467:4562 */       e = (Equipo)localIterator6.next();
/* 4468:4563 */       ((HashMap)hashMapEquipo).put(e.getCodigo(), e);
/* 4469:     */     }
/* 4470:     */     Equipo e;
/* 4471:4566 */     Object it = ((HashMap)hashMapEquipo).keySet().iterator();
/* 4472:4567 */     while (((Iterator)it).hasNext()) {
/* 4473:4568 */       e = (String)((Iterator)it).next();
/* 4474:     */     }
/* 4475:4571 */     HashMap<String, Departamento> hashMapDepartamento = new HashMap();
/* 4476:4572 */     List<Departamento> listaDepartamento = this.servicioDepartamento.obtenerListaCombo("", false, null);
/* 4477:4573 */     for (Departamento e : listaDepartamento) {
/* 4478:4574 */       hashMapDepartamento.put(e.getCodigo(), e);
/* 4479:     */     }
/* 4480:4576 */     Object hashMapDetalleComponente = new HashMap();
/* 4481:4577 */     List<DetalleComponenteEquipo> listaDetalleComponenteEquipo = this.servicioDetalleComponenteEquipo.obtenerListaCombo(DetalleComponenteEquipo.class, "", false, null);
/* 4482:4579 */     for (DetalleComponenteEquipo dce : listaDetalleComponenteEquipo) {
/* 4483:4580 */       ((HashMap)hashMapDetalleComponente).put(dce.getEquipo().getCodigo(), dce);
/* 4484:     */     }
/* 4485:4583 */     int filaActual = filaInicial;
/* 4486:4584 */     int columnaErronea = 0;
/* 4487:4585 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 4488:     */     try
/* 4489:     */     {
/* 4490:4590 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getId(), fileName, imInputStream, filaInicial, 0);
/* 4491:4592 */       for (HSSFCell[] fila : datos)
/* 4492:     */       {
/* 4493:4594 */         filaErronea = fila;
/* 4494:4595 */         filaActual++;
/* 4495:4596 */         Equipo equipo = null;
/* 4496:4597 */         if (fila[(columnaErronea = 2)] != null) {
/* 4497:4598 */           fila[(columnaErronea = 2)].setCellType(1);
/* 4498:     */         }
/* 4499:4599 */         String indicadorActivoFijo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 2, false, Integer.valueOf(0), 
/* 4500:4600 */           Integer.valueOf(3));
/* 4501:4602 */         if (fila[(columnaErronea = 0)] != null)
/* 4502:     */         {
/* 4503:4603 */           fila[(columnaErronea = 0)].setCellType(1);
/* 4504:4604 */           String codigoEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 0, true, Integer.valueOf(20), 
/* 4505:4605 */             Integer.valueOf(20));
/* 4506:4606 */           if (indicadorActivoFijo == null) {
/* 4507:4607 */             indicadorActivoFijo = "NO";
/* 4508:     */           }
/* 4509:4609 */           if (indicadorActivoFijo.equalsIgnoreCase("SI"))
/* 4510:     */           {
/* 4511:4610 */             equipo = (Equipo)((HashMap)hashMapEquipo).get(codigoEquipo.trim());
/* 4512:     */             String modeloEquipo;
/* 4513:4611 */             if (equipo == null)
/* 4514:     */             {
/* 4515:4612 */               HashMap<String, String> filters = new HashMap();
/* 4516:4613 */               filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 4517:4614 */               filters.put("codigo", "=" + codigoEquipo);
/* 4518:4615 */               List<ActivoFijo> listaActivoFijo = this.servicioActivoFijo.obtenerListaComboParaMantenimiento("codigo", true, filters);
/* 4519:4616 */               if (listaActivoFijo.size() > 0)
/* 4520:     */               {
/* 4521:4617 */                 equipo = new Equipo();
/* 4522:4618 */                 ActivoFijo activoFijo = (ActivoFijo)listaActivoFijo.get(0);
/* 4523:4619 */                 equipo.setIdOrganizacion(activoFijo.getIdOrganizacion());
/* 4524:4620 */                 equipo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 4525:4621 */                 equipo.setActivoFijo(activoFijo);
/* 4526:4622 */                 equipo.setCodigo(equipo.getActivoFijo().getCodigo().trim());
/* 4527:4623 */                 equipo.setNombre(activoFijo.getNombre());
/* 4528:4624 */                 equipo.setNumeroSerie(activoFijo.getNumeroSerie());
/* 4529:4625 */                 equipo.setNumeroParte(activoFijo.getNumeroParte());
/* 4530:4626 */                 equipo.setCodigoBarras(activoFijo.getCodigoBarras());
/* 4531:4627 */                 equipo.setCentroCosto(activoFijo.getCentroCosto());
/* 4532:4628 */                 equipo.setFechaCompra(equipo.getActivoFijo().getFechaFacturaProveedor());
/* 4533:4629 */                 String numeroFacturaAF = activoFijo.getNumeroFacturaProveedor();
/* 4534:4630 */                 List<FacturaProveedor> listaFacturaProvedorAF = new ArrayList();
/* 4535:4631 */                 if (numeroFacturaAF != null)
/* 4536:     */                 {
/* 4537:4632 */                   String delimeter = "-";
/* 4538:     */                   
/* 4539:4634 */                   String[] temp = numeroFacturaAF.split(delimeter);
/* 4540:4635 */                   if (temp.length == 3)
/* 4541:     */                   {
/* 4542:4636 */                     String sucursalAF = temp[0];
/* 4543:4637 */                     String puntoVentaAF = temp[1];
/* 4544:4638 */                     String secuencialAF = temp[2];
/* 4545:4639 */                     HashMap<String, String> filtersAF = new HashMap();
/* 4546:4640 */                     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 4547:4641 */                     filters.put("facturaProveedorSRI.establecimiento", "=" + sucursalAF);
/* 4548:4642 */                     filters.put("facturaProveedorSRI.puntoEmision", "=" + puntoVentaAF);
/* 4549:4643 */                     filters.put("facturaProveedorSRI.numero", "=" + secuencialAF);
/* 4550:4644 */                     listaFacturaProvedorAF = this.servicioFacturaProveedor.obtenerListaCombo("fecha", true, filtersAF);
/* 4551:     */                   }
/* 4552:     */                 }
/* 4553:4647 */                 if (listaFacturaProvedorAF.size() > 0)
/* 4554:     */                 {
/* 4555:4648 */                   equipo.setFacturaProveedor((FacturaProveedor)listaFacturaProvedorAF.get(0));
/* 4556:4649 */                   equipo.setNumeroFactura(numeroFacturaAF);
/* 4557:     */                 }
/* 4558:     */                 else
/* 4559:     */                 {
/* 4560:4651 */                   equipo.setNumeroFactura(numeroFacturaAF);
/* 4561:     */                 }
/* 4562:4654 */                 if (fila[(columnaErronea = 3)] != null) {
/* 4563:4655 */                   fila[(columnaErronea = 3)].setCellType(1);
/* 4564:     */                 }
/* 4565:4657 */                 String descripcionEquipo = fila[(columnaErronea = 3)] == null ? null : fila[(columnaErronea = 3)].getStringCellValue().trim();
/* 4566:4658 */                 equipo.setDescripcion(descripcionEquipo);
/* 4567:     */                 
/* 4568:4660 */                 modeloEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 7, false, 
/* 4569:4661 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 4570:4662 */                 equipo.setModelo(modeloEquipo);
/* 4571:4664 */                 if (fila[(columnaErronea = 8)] != null) {
/* 4572:4665 */                   fila[(columnaErronea = 8)].setCellType(1);
/* 4573:     */                 }
/* 4574:4666 */                 String prioridadEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 8, true, 
/* 4575:4667 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 4576:4668 */                 PrioridadEnum auxPrioridad = null;
/* 4577:4669 */                 if (PrioridadEnum.ALTA.getNombre().equalsIgnoreCase(prioridadEquipo)) {
/* 4578:4670 */                   auxPrioridad = PrioridadEnum.ALTA;
/* 4579:4672 */                 } else if (PrioridadEnum.MEDIA.getNombre().equalsIgnoreCase(prioridadEquipo)) {
/* 4580:4673 */                   auxPrioridad = PrioridadEnum.MEDIA;
/* 4581:4675 */                 } else if (PrioridadEnum.BAJA.getNombre().equalsIgnoreCase(prioridadEquipo)) {
/* 4582:4676 */                   auxPrioridad = PrioridadEnum.BAJA;
/* 4583:     */                 }
/* 4584:4681 */                 equipo.setPrioridad(auxPrioridad);
/* 4585:     */                 
/* 4586:4683 */                 String indicadorPredeterminadoEq = "";
/* 4587:4684 */                 if (fila[(columnaErronea = 9)] != null) {
/* 4588:4685 */                   fila[(columnaErronea = 9)].setCellType(1);
/* 4589:     */                 }
/* 4590:4686 */                 indicadorPredeterminadoEq = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 9, true, 
/* 4591:4687 */                   Integer.valueOf(0), Integer.valueOf(3));
/* 4592:     */                 
/* 4593:4689 */                 boolean indicadorPredeterminadoEquipo = indicadorPredeterminadoEq.equalsIgnoreCase("SI");
/* 4594:4690 */                 equipo.setPredeterminado(indicadorPredeterminadoEquipo);
/* 4595:4692 */                 if (fila[(columnaErronea = 10)] != null) {
/* 4596:4693 */                   fila[(columnaErronea = 10)].setCellType(1);
/* 4597:     */                 }
/* 4598:4694 */                 String indicadorActivoEq = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 10, false, 
/* 4599:4695 */                   Integer.valueOf(0), Integer.valueOf(2));
/* 4600:     */                 
/* 4601:4697 */                 boolean indicadorActivoEquipo = true;
/* 4602:4698 */                 if (indicadorActivoEq == "NO") {
/* 4603:4699 */                   indicadorActivoEquipo = false;
/* 4604:     */                 }
/* 4605:4701 */                 equipo.setActivo(indicadorActivoEquipo);
/* 4606:4703 */                 if (fila[(columnaErronea = 13)] != null) {
/* 4607:4704 */                   fila[(columnaErronea = 13)].setCellType(1);
/* 4608:     */                 }
/* 4609:4705 */                 String codigoComponenteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 13, true, 
/* 4610:4706 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 4611:     */                 
/* 4612:4708 */                 ComponenteEquipo componenteEquipo = (ComponenteEquipo)hashMapComponenteEquipo.get(codigoComponenteEquipo);
/* 4613:4710 */                 if (componenteEquipo == null)
/* 4614:     */                 {
/* 4615:4711 */                   if (fila[(columnaErronea = 14)] != null) {
/* 4616:4712 */                     fila[(columnaErronea = 14)].setCellType(1);
/* 4617:     */                   }
/* 4618:4713 */                   String nombreComponenteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 14, true, 
/* 4619:4714 */                     Integer.valueOf(0), Integer.valueOf(100));
/* 4620:4715 */                   componenteEquipo = new ComponenteEquipo();
/* 4621:4716 */                   componenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4622:4717 */                   componenteEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 4623:4718 */                   componenteEquipo.setCodigo(codigoComponenteEquipo.trim());
/* 4624:4719 */                   componenteEquipo.setNombre(nombreComponenteEquipo.trim());
/* 4625:4720 */                   componenteEquipo.setActivo(true);
/* 4626:4721 */                   this.servicioComponenteEquipo.guardar(componenteEquipo);
/* 4627:4722 */                   hashMapComponenteEquipo.put(componenteEquipo.getCodigo(), componenteEquipo);
/* 4628:     */                 }
/* 4629:4725 */                 if (fila[(columnaErronea = 15)] != null) {
/* 4630:4726 */                   fila[(columnaErronea = 15)].setCellType(0);
/* 4631:     */                 }
/* 4632:4727 */                 BigDecimal vidaUtil = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 15, false, 
/* 4633:4728 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 4634:4729 */                 FrecuenciaFechaEnum periodo = null;
/* 4635:4730 */                 if (vidaUtil.compareTo(BigDecimal.ZERO) > 0)
/* 4636:     */                 {
/* 4637:4731 */                   if (fila[(columnaErronea = 16)] != null) {
/* 4638:4732 */                     fila[(columnaErronea = 16)].setCellType(1);
/* 4639:     */                   }
/* 4640:4733 */                   String periodoVidaUtil = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 16, true, 
/* 4641:4734 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 4642:4736 */                   if (FrecuenciaFechaEnum.DIAS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4643:4737 */                     periodo = FrecuenciaFechaEnum.DIAS;
/* 4644:4738 */                   } else if (FrecuenciaFechaEnum.SEMANAS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4645:4739 */                     periodo = FrecuenciaFechaEnum.SEMANAS;
/* 4646:4740 */                   } else if (FrecuenciaFechaEnum.MESES.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4647:4741 */                     periodo = FrecuenciaFechaEnum.MESES;
/* 4648:4742 */                   } else if (FrecuenciaFechaEnum.ANIOS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4649:4743 */                     periodo = FrecuenciaFechaEnum.ANIOS;
/* 4650:     */                   }
/* 4651:     */                 }
/* 4652:4747 */                 if (fila[(columnaErronea = 17)] != null) {
/* 4653:4748 */                   fila[(columnaErronea = 17)].setCellType(0);
/* 4654:     */                 }
/* 4655:4749 */                 BigDecimal porcentaje = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 17, false, 
/* 4656:4750 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 4657:4752 */                 if (fila[(columnaErronea = 19)] != null) {
/* 4658:4753 */                   fila[(columnaErronea = 19)].setCellType(1);
/* 4659:     */                 }
/* 4660:4754 */                 String codigoUbicacionEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 19, true, 
/* 4661:4755 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 4662:     */                 
/* 4663:4757 */                 UbicacionActivo ubicacionActivo = (UbicacionActivo)((HashMap)hashMapUbicacionActivo).get(codigoUbicacionEquipo);
/* 4664:4758 */                 if (ubicacionActivo == null)
/* 4665:     */                 {
/* 4666:4760 */                   if (fila[(columnaErronea = 20)] != null) {
/* 4667:4761 */                     fila[(columnaErronea = 20)].setCellType(1);
/* 4668:     */                   }
/* 4669:4762 */                   String nombreUbicacionEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 20, true, 
/* 4670:4763 */                     Integer.valueOf(0), Integer.valueOf(50));
/* 4671:4764 */                   ubicacionActivo = new UbicacionActivo();
/* 4672:4765 */                   ubicacionActivo.setCodigo(codigoUbicacionEquipo.trim());
/* 4673:4766 */                   ubicacionActivo.setNombre(nombreUbicacionEquipo.trim());
/* 4674:4767 */                   ubicacionActivo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4675:4768 */                   ubicacionActivo.setSucursal(AppUtil.getSucursal());
/* 4676:4770 */                   if (fila[(columnaErronea = 21)] != null) {
/* 4677:4771 */                     fila[(columnaErronea = 21)].setCellType(1);
/* 4678:     */                   }
/* 4679:4772 */                   String codigoDepartamento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 21, true, 
/* 4680:4773 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 4681:4774 */                   Departamento departamento = (Departamento)hashMapDepartamento.get(codigoDepartamento);
/* 4682:4775 */                   if (departamento == null) {
/* 4683:4776 */                     throw new ExcepcionAS2("msg_departamento_no_encontrado", " " + codigoDepartamento + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 4684:     */                   }
/* 4685:4779 */                   ubicacionActivo.setDepartamento(departamento);
/* 4686:     */                   
/* 4687:4781 */                   this.servicioUbicacionActivo.guardar(ubicacionActivo);
/* 4688:4782 */                   ((HashMap)hashMapUbicacionActivo).put(ubicacionActivo.getCodigo(), ubicacionActivo);
/* 4689:     */                 }
/* 4690:4784 */                 equipo.setUbicacion(ubicacionActivo);
/* 4691:4786 */                 if (fila[(columnaErronea = 24)] != null) {
/* 4692:4787 */                   fila[(columnaErronea = 24)].setCellType(1);
/* 4693:     */                 }
/* 4694:4788 */                 String codigoSubCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 24, true, 
/* 4695:4789 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 4696:4790 */                 SubcategoriaEquipo subCategoriaEquipo = (SubcategoriaEquipo)((HashMap)hashMapSubcategoriaEquipo).get(codigoSubCategoriaEquipo);
/* 4697:4791 */                 if (subCategoriaEquipo == null)
/* 4698:     */                 {
/* 4699:4792 */                   if (fila[(columnaErronea = 22)] != null) {
/* 4700:4793 */                     fila[(columnaErronea = 22)].setCellType(1);
/* 4701:     */                   }
/* 4702:4794 */                   String codigoCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 22, true, 
/* 4703:4795 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 4704:4796 */                   CategoriaEquipo categoriaEquipo = (CategoriaEquipo)((HashMap)hashMapCategoriaEquipo).get(codigoCategoriaEquipo);
/* 4705:4797 */                   if (categoriaEquipo != null)
/* 4706:     */                   {
/* 4707:4798 */                     if (fila[(columnaErronea = 25)] != null) {
/* 4708:4799 */                       fila[(columnaErronea = 25)].setCellType(1);
/* 4709:     */                     }
/* 4710:4800 */                     String nombreSubCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 25, true, 
/* 4711:4801 */                       Integer.valueOf(0), Integer.valueOf(100));
/* 4712:4802 */                     subCategoriaEquipo = new SubcategoriaEquipo();
/* 4713:4803 */                     subCategoriaEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4714:4804 */                     subCategoriaEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 4715:4805 */                     subCategoriaEquipo.setCodigo(codigoSubCategoriaEquipo.trim());
/* 4716:4806 */                     subCategoriaEquipo.setNombre(nombreSubCategoriaEquipo.trim());
/* 4717:4807 */                     subCategoriaEquipo.setCategoriaEquipo(categoriaEquipo);
/* 4718:4808 */                     subCategoriaEquipo.setActivo(true);
/* 4719:4809 */                     this.servicioSubCategoriaEquipo.guardar(subCategoriaEquipo);
/* 4720:4810 */                     ((HashMap)hashMapSubcategoriaEquipo).put(subCategoriaEquipo.getCodigo(), subCategoriaEquipo);
/* 4721:     */                   }
/* 4722:     */                   else
/* 4723:     */                   {
/* 4724:4812 */                     if (fila[(columnaErronea = 23)] != null) {
/* 4725:4813 */                       fila[(columnaErronea = 23)].setCellType(1);
/* 4726:     */                     }
/* 4727:4814 */                     String nombreCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 23, true, 
/* 4728:4815 */                       Integer.valueOf(0), Integer.valueOf(100));
/* 4729:4816 */                     if (fila[(columnaErronea = 25)] != null) {
/* 4730:4817 */                       fila[(columnaErronea = 25)].setCellType(1);
/* 4731:     */                     }
/* 4732:4818 */                     String nombreSubCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 25, true, 
/* 4733:4819 */                       Integer.valueOf(0), Integer.valueOf(100));
/* 4734:4820 */                     categoriaEquipo = new CategoriaEquipo();
/* 4735:4821 */                     categoriaEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4736:4822 */                     categoriaEquipo.setIdSucursal(AppUtil.getOrganizacion().getId());
/* 4737:4823 */                     categoriaEquipo.setCodigo(codigoCategoriaEquipo.trim());
/* 4738:4824 */                     categoriaEquipo.setNombre(nombreCategoriaEquipo.trim());
/* 4739:4825 */                     categoriaEquipo.setActivo(true);
/* 4740:4826 */                     this.servicioCategoriaEquipo.guardar(categoriaEquipo);
/* 4741:4827 */                     ((HashMap)hashMapCategoriaEquipo).put(categoriaEquipo.getCodigo(), categoriaEquipo);
/* 4742:4828 */                     subCategoriaEquipo = new SubcategoriaEquipo();
/* 4743:4829 */                     subCategoriaEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4744:4830 */                     subCategoriaEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 4745:4831 */                     subCategoriaEquipo.setCodigo(codigoSubCategoriaEquipo.trim());
/* 4746:4832 */                     subCategoriaEquipo.setNombre(nombreSubCategoriaEquipo.trim());
/* 4747:4833 */                     subCategoriaEquipo.setCategoriaEquipo(categoriaEquipo);
/* 4748:4834 */                     subCategoriaEquipo.setActivo(true);
/* 4749:4835 */                     this.servicioSubCategoriaEquipo.guardar(subCategoriaEquipo);
/* 4750:4836 */                     ((HashMap)hashMapSubcategoriaEquipo).put(subCategoriaEquipo.getCodigo(), subCategoriaEquipo);
/* 4751:     */                   }
/* 4752:     */                 }
/* 4753:4839 */                 equipo.setSubcategoriaEquipo(subCategoriaEquipo);
/* 4754:     */                 
/* 4755:4841 */                 this.servicioEquipo.guardar(equipo);
/* 4756:4842 */                 ((HashMap)hashMapEquipo).put(equipo.getCodigo(), equipo);
/* 4757:     */                 
/* 4758:4844 */                 DetalleComponenteEquipo detalleComponenteEquipo = new DetalleComponenteEquipo();
/* 4759:4845 */                 detalleComponenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4760:4846 */                 detalleComponenteEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 4761:4847 */                 detalleComponenteEquipo.setComponenteEquipo(componenteEquipo);
/* 4762:4848 */                 detalleComponenteEquipo.setEquipo(equipo);
/* 4763:4849 */                 detalleComponenteEquipo.setTiempoVida(vidaUtil);
/* 4764:4850 */                 detalleComponenteEquipo.setPeriodoVidaUtil(periodo);
/* 4765:4851 */                 detalleComponenteEquipo.setPorcentajeAlarma(porcentaje);
/* 4766:4852 */                 this.servicioDetalleComponenteEquipo.guardar(detalleComponenteEquipo);
/* 4767:4853 */                 ((HashMap)hashMapDetalleComponente).put(equipo.getCodigo(), detalleComponenteEquipo);
/* 4768:     */               }
/* 4769:     */               else
/* 4770:     */               {
/* 4771:4855 */                 throw new ExcepcionAS2("msg_activo_fijo_no_encontrado", " " + codigoEquipo + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 4772:     */               }
/* 4773:     */             }
/* 4774:     */             else
/* 4775:     */             {
/* 4776:4860 */               if (fila[(columnaErronea = 13)] != null) {
/* 4777:4861 */                 fila[(columnaErronea = 13)].setCellType(1);
/* 4778:     */               }
/* 4779:4862 */               String codigoComponenteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 13, true, 
/* 4780:4863 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 4781:     */               
/* 4782:4865 */               ComponenteEquipo componenteEquipo = (ComponenteEquipo)hashMapComponenteEquipo.get(codigoComponenteEquipo);
/* 4783:4866 */               if ((componenteEquipo == null) && (!codigoComponenteEquipo.isEmpty()))
/* 4784:     */               {
/* 4785:4867 */                 if (fila[(columnaErronea = 14)] != null) {
/* 4786:4868 */                   fila[(columnaErronea = 14)].setCellType(1);
/* 4787:     */                 }
/* 4788:4869 */                 String nombreComponenteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 14, true, 
/* 4789:4870 */                   Integer.valueOf(0), Integer.valueOf(100));
/* 4790:4871 */                 componenteEquipo = new ComponenteEquipo();
/* 4791:4872 */                 componenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4792:4873 */                 componenteEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 4793:4874 */                 componenteEquipo.setCodigo(codigoComponenteEquipo.trim());
/* 4794:4875 */                 componenteEquipo.setNombre(nombreComponenteEquipo.trim());
/* 4795:4876 */                 componenteEquipo.setActivo(true);
/* 4796:4877 */                 this.servicioComponenteEquipo.guardar(componenteEquipo);
/* 4797:4878 */                 hashMapComponenteEquipo.put(componenteEquipo.getCodigo(), componenteEquipo);
/* 4798:     */               }
/* 4799:     */               else
/* 4800:     */               {
/* 4801:4880 */                 boolean contiene = false;
/* 4802:     */                 
/* 4803:4882 */                 Equipo eq = this.servicioEquipo.cargarDetalle(equipo);
/* 4804:4883 */                 List<DetalleComponenteEquipo> lDCE = eq.getListaComponenteEquipo();
/* 4805:4884 */                 for (DetalleComponenteEquipo detalleCE : lDCE) {
/* 4806:4885 */                   if (detalleCE.getComponenteEquipo().getCodigo().equals(codigoComponenteEquipo)) {
/* 4807:4886 */                     contiene = true;
/* 4808:     */                   }
/* 4809:     */                 }
/* 4810:4889 */                 if (!contiene)
/* 4811:     */                 {
/* 4812:4890 */                   if (fila[(columnaErronea = 15)] != null) {
/* 4813:4891 */                     fila[(columnaErronea = 15)].setCellType(0);
/* 4814:     */                   }
/* 4815:4892 */                   BigDecimal vidaUtil = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 15, false, 
/* 4816:4893 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 4817:4894 */                   FrecuenciaFechaEnum periodo = null;
/* 4818:4895 */                   if (vidaUtil.compareTo(BigDecimal.ZERO) > 0)
/* 4819:     */                   {
/* 4820:4896 */                     if (fila[(columnaErronea = 16)] != null) {
/* 4821:4897 */                       fila[(columnaErronea = 16)].setCellType(1);
/* 4822:     */                     }
/* 4823:4898 */                     String periodoVidaUtil = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 16, true, 
/* 4824:4899 */                       Integer.valueOf(0), Integer.valueOf(20));
/* 4825:4901 */                     if (FrecuenciaFechaEnum.DIAS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4826:4902 */                       periodo = FrecuenciaFechaEnum.DIAS;
/* 4827:4903 */                     } else if (FrecuenciaFechaEnum.SEMANAS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4828:4904 */                       periodo = FrecuenciaFechaEnum.SEMANAS;
/* 4829:4905 */                     } else if (FrecuenciaFechaEnum.MESES.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4830:4906 */                       periodo = FrecuenciaFechaEnum.MESES;
/* 4831:4907 */                     } else if (FrecuenciaFechaEnum.ANIOS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4832:4908 */                       periodo = FrecuenciaFechaEnum.ANIOS;
/* 4833:     */                     }
/* 4834:     */                   }
/* 4835:4912 */                   if (fila[(columnaErronea = 17)] != null) {
/* 4836:4913 */                     fila[(columnaErronea = 17)].setCellType(0);
/* 4837:     */                   }
/* 4838:4914 */                   BigDecimal porcentaje = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 17, false, 
/* 4839:4915 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 4840:4916 */                   DetalleComponenteEquipo detalleComponenteEquipo = new DetalleComponenteEquipo();
/* 4841:4917 */                   detalleComponenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4842:4918 */                   detalleComponenteEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 4843:4919 */                   detalleComponenteEquipo.setComponenteEquipo(componenteEquipo);
/* 4844:4920 */                   detalleComponenteEquipo.setEquipo(equipo);
/* 4845:4921 */                   detalleComponenteEquipo.setTiempoVida(vidaUtil);
/* 4846:4922 */                   detalleComponenteEquipo.setPeriodoVidaUtil(periodo);
/* 4847:4923 */                   detalleComponenteEquipo.setPorcentajeAlarma(porcentaje);
/* 4848:4924 */                   this.servicioDetalleComponenteEquipo.guardar(detalleComponenteEquipo);
/* 4849:4925 */                   ((HashMap)hashMapDetalleComponente).put(equipo.getCodigo(), detalleComponenteEquipo);
/* 4850:     */                 }
/* 4851:     */               }
/* 4852:     */             }
/* 4853:     */           }
/* 4854:     */           else
/* 4855:     */           {
/* 4856:4932 */             equipo = (Equipo)((HashMap)hashMapEquipo).get(codigoEquipo.trim());
/* 4857:     */             String descripcionEquipo;
/* 4858:4933 */             if (equipo == null)
/* 4859:     */             {
/* 4860:4934 */               equipo = new Equipo();
/* 4861:4935 */               equipo.setCodigo(codigoEquipo.trim());
/* 4862:4936 */               String codigoBarrasEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 1, false, 
/* 4863:4937 */                 Integer.valueOf(20), Integer.valueOf(20));
/* 4864:4938 */               equipo.setCodigoBarras(codigoBarrasEquipo);
/* 4865:4939 */               if (fila[(columnaErronea = 3)] != null) {
/* 4866:4940 */                 fila[(columnaErronea = 3)].setCellType(1);
/* 4867:     */               }
/* 4868:4941 */               String nombreEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 3, true, 
/* 4869:4942 */                 Integer.valueOf(0), Integer.valueOf(100));
/* 4870:4943 */               equipo.setNombre(nombreEquipo);
/* 4871:4944 */               if (fila[(columnaErronea = 5)] != null) {
/* 4872:4945 */                 fila[(columnaErronea = 5)].setCellType(1);
/* 4873:     */               }
/* 4874:4946 */               String numeroParteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 5, false, 
/* 4875:4947 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 4876:4948 */               equipo.setNumeroParte(numeroParteEquipo);
/* 4877:4950 */               if (fila[(columnaErronea = 11)] != null) {
/* 4878:4951 */                 fila[(columnaErronea = 11)].setCellType(1);
/* 4879:     */               }
/* 4880:4952 */               String numeroFacturaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 11, false, 
/* 4881:4953 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 4882:4954 */               if (numeroFacturaEquipo != null)
/* 4883:     */               {
/* 4884:4955 */                 String delimeter = "-";
/* 4885:     */                 
/* 4886:4957 */                 String[] temp = numeroFacturaEquipo.split(delimeter);
/* 4887:4958 */                 if (temp.length == 3)
/* 4888:     */                 {
/* 4889:4959 */                   sucursal = temp[0];
/* 4890:4960 */                   puntoVenta = temp[1];
/* 4891:4961 */                   secuencial = temp[2];
/* 4892:     */                 }
/* 4893:     */               }
/* 4894:4965 */               Map<String, String> filtrosTipoPresentacion = new HashMap();
/* 4895:4966 */               filtrosTipoPresentacion.put("facturaProveedorSRI.establecimiento", "=" + sucursal);
/* 4896:4967 */               filtrosTipoPresentacion.put("facturaProveedorSRI.puntoEmision", "=" + puntoVenta);
/* 4897:4968 */               filtrosTipoPresentacion.put("facturaProveedorSRI.numero", "=" + secuencial);
/* 4898:4969 */               filtrosTipoPresentacion.put("idOrganizacion", "" + idOrganizacion);
/* 4899:4970 */               List<FacturaProveedor> listaFacturaProveedor = this.servicioFacturaProveedor.obtenerListaCombo("fecha", true, filtrosTipoPresentacion);
/* 4900:4972 */               if (listaFacturaProveedor.size() > 0)
/* 4901:     */               {
/* 4902:4973 */                 equipo.setFacturaProveedor((FacturaProveedor)listaFacturaProveedor.get(0));
/* 4903:4974 */                 equipo.setNumeroFactura(numeroFacturaEquipo);
/* 4904:4975 */                 equipo.setFechaCompra(((FacturaProveedor)listaFacturaProveedor.get(0)).getFecha());
/* 4905:     */               }
/* 4906:     */               else
/* 4907:     */               {
/* 4908:4977 */                 equipo.setNumeroFactura(numeroFacturaEquipo);
/* 4909:4978 */                 Date fechaCompra = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaErronea = 12, true, 
/* 4910:4979 */                   Integer.valueOf(0), Integer.valueOf(0));
/* 4911:4980 */                 equipo.setFechaCompra(fechaCompra);
/* 4912:     */               }
/* 4913:4983 */               if (fila[(columnaErronea = 4)] != null) {
/* 4914:4984 */                 fila[(columnaErronea = 4)].setCellType(1);
/* 4915:     */               }
/* 4916:4985 */               descripcionEquipo = fila[(columnaErronea = 4)] == null ? null : fila[(columnaErronea = 4)].getStringCellValue().trim();
/* 4917:4986 */               equipo.setDescripcion(descripcionEquipo);
/* 4918:     */               
/* 4919:4988 */               String modeloEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 7, false, 
/* 4920:4989 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 4921:4990 */               equipo.setModelo(modeloEquipo);
/* 4922:4991 */               if (fila[(columnaErronea = 8)] != null) {
/* 4923:4992 */                 fila[(columnaErronea = 8)].setCellType(1);
/* 4924:     */               }
/* 4925:4993 */               String prioridadEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 8, true, 
/* 4926:4994 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 4927:     */               
/* 4928:4996 */               PrioridadEnum auxPrioridad = null;
/* 4929:4997 */               if (PrioridadEnum.ALTA.getNombre().equalsIgnoreCase(prioridadEquipo)) {
/* 4930:4998 */                 auxPrioridad = PrioridadEnum.ALTA;
/* 4931:5000 */               } else if (PrioridadEnum.MEDIA.getNombre().equalsIgnoreCase(prioridadEquipo)) {
/* 4932:5001 */                 auxPrioridad = PrioridadEnum.MEDIA;
/* 4933:5003 */               } else if (PrioridadEnum.BAJA.getNombre().equalsIgnoreCase(prioridadEquipo)) {
/* 4934:5004 */                 auxPrioridad = PrioridadEnum.BAJA;
/* 4935:     */               }
/* 4936:5008 */               equipo.setPrioridad(auxPrioridad);
/* 4937:5010 */               if (fila[(columnaErronea = 9)] != null) {
/* 4938:5011 */                 fila[(columnaErronea = 9)].setCellType(1);
/* 4939:     */               }
/* 4940:5012 */               String indicadorPredeterminadoEq = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 9, true, 
/* 4941:5013 */                 Integer.valueOf(0), Integer.valueOf(3));
/* 4942:5014 */               boolean indicadorPredeterminadoEquipo = indicadorPredeterminadoEq.equalsIgnoreCase("SI");
/* 4943:5015 */               equipo.setPredeterminado(indicadorPredeterminadoEquipo);
/* 4944:5017 */               if (fila[(columnaErronea = 10)] != null) {
/* 4945:5018 */                 fila[(columnaErronea = 10)].setCellType(1);
/* 4946:     */               }
/* 4947:5019 */               String indicadorActivoEq = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 10, false, 
/* 4948:5020 */                 Integer.valueOf(0), Integer.valueOf(2));
/* 4949:5021 */               boolean indicadorActivoEquipo = true;
/* 4950:5022 */               if (indicadorActivoEq == "NO") {
/* 4951:5023 */                 indicadorActivoEquipo = false;
/* 4952:     */               }
/* 4953:5025 */               equipo.setActivo(indicadorActivoEquipo);
/* 4954:5027 */               if (fila[(columnaErronea = 13)] != null) {
/* 4955:5028 */                 fila[(columnaErronea = 13)].setCellType(1);
/* 4956:     */               }
/* 4957:5029 */               String codigoComponenteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 13, true, 
/* 4958:5030 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 4959:     */               
/* 4960:5032 */               ComponenteEquipo componenteEquipo = (ComponenteEquipo)hashMapComponenteEquipo.get(codigoComponenteEquipo);
/* 4961:5033 */               if ((componenteEquipo == null) && (!codigoComponenteEquipo.isEmpty()))
/* 4962:     */               {
/* 4963:5034 */                 String nombreComponenteEquipo = "";
/* 4964:5035 */                 if (fila[(columnaErronea = 14)] != null)
/* 4965:     */                 {
/* 4966:5036 */                   fila[(columnaErronea = 14)].setCellType(1);
/* 4967:5037 */                   nombreComponenteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 14, true, 
/* 4968:5038 */                     Integer.valueOf(0), Integer.valueOf(100));
/* 4969:     */                 }
/* 4970:5040 */                 componenteEquipo = new ComponenteEquipo();
/* 4971:5041 */                 componenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 4972:5042 */                 componenteEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 4973:5043 */                 componenteEquipo.setCodigo(codigoComponenteEquipo.trim());
/* 4974:5044 */                 componenteEquipo.setNombre(nombreComponenteEquipo);
/* 4975:5045 */                 componenteEquipo.setActivo(indicadorActivoEquipo);
/* 4976:5046 */                 this.servicioComponenteEquipo.guardar(componenteEquipo);
/* 4977:5047 */                 hashMapComponenteEquipo.put(componenteEquipo.getCodigo(), componenteEquipo);
/* 4978:     */               }
/* 4979:5050 */               if (fila[(columnaErronea = 15)] != null) {
/* 4980:5051 */                 fila[(columnaErronea = 15)].setCellType(0);
/* 4981:     */               }
/* 4982:5052 */               BigDecimal vidaUtil = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 15, false, 
/* 4983:5053 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 4984:5054 */               FrecuenciaFechaEnum periodo = null;
/* 4985:5055 */               if (vidaUtil.compareTo(BigDecimal.ZERO) > 0)
/* 4986:     */               {
/* 4987:5056 */                 if (fila[(columnaErronea = 16)] != null) {
/* 4988:5057 */                   fila[(columnaErronea = 16)].setCellType(1);
/* 4989:     */                 }
/* 4990:5058 */                 String periodoVidaUtil = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 16, true, 
/* 4991:5059 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 4992:5061 */                 if (FrecuenciaFechaEnum.DIAS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4993:5062 */                   periodo = FrecuenciaFechaEnum.DIAS;
/* 4994:5063 */                 } else if (FrecuenciaFechaEnum.SEMANAS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4995:5064 */                   periodo = FrecuenciaFechaEnum.SEMANAS;
/* 4996:5065 */                 } else if (FrecuenciaFechaEnum.MESES.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4997:5066 */                   periodo = FrecuenciaFechaEnum.MESES;
/* 4998:5067 */                 } else if (FrecuenciaFechaEnum.ANIOS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 4999:5068 */                   periodo = FrecuenciaFechaEnum.ANIOS;
/* 5000:     */                 }
/* 5001:     */               }
/* 5002:5072 */               if (fila[(columnaErronea = 17)] != null) {
/* 5003:5073 */                 fila[(columnaErronea = 17)].setCellType(0);
/* 5004:     */               }
/* 5005:5074 */               BigDecimal porcentaje = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 17, false, 
/* 5006:5075 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 5007:     */               
/* 5008:     */ 
/* 5009:5078 */               String codigoCentroCostoEquipo = "";
/* 5010:5079 */               if (fila[(columnaErronea = 18)] != null)
/* 5011:     */               {
/* 5012:5080 */                 fila[(columnaErronea = 18)].setCellType(1);
/* 5013:5081 */                 codigoCentroCostoEquipo = fila[(columnaErronea = 18)].getStringCellValue().trim();
/* 5014:     */               }
/* 5015:5083 */               DimensionContable centroCosto = (DimensionContable)((HashMap)hashMapDimensionContable).get(codigoCentroCostoEquipo);
/* 5016:5084 */               if ((centroCosto == null) && (!codigoCentroCostoEquipo.isEmpty())) {
/* 5017:5085 */                 throw new ExcepcionAS2("msg_info_centro_costo_no_encontrado", " " + codigoCentroCostoEquipo + "  Fila: " + filaActual + " Columna " + columnaErronea);
/* 5018:     */               }
/* 5019:5088 */               equipo.setCentroCosto(centroCosto);
/* 5020:5092 */               if (fila[(columnaErronea = 19)] != null) {
/* 5021:5093 */                 fila[(columnaErronea = 19)].setCellType(1);
/* 5022:     */               }
/* 5023:5094 */               String codigoUbicacionEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 19, true, 
/* 5024:5095 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 5025:5096 */               UbicacionActivo ubicacionActivo = (UbicacionActivo)((HashMap)hashMapUbicacionActivo).get(codigoUbicacionEquipo);
/* 5026:5097 */               if (ubicacionActivo == null)
/* 5027:     */               {
/* 5028:5099 */                 if (fila[(columnaErronea = 20)] != null) {
/* 5029:5100 */                   fila[(columnaErronea = 20)].setCellType(1);
/* 5030:     */                 }
/* 5031:5101 */                 String nombreUbicacionEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 20, true, 
/* 5032:5102 */                   Integer.valueOf(0), Integer.valueOf(50));
/* 5033:5103 */                 ubicacionActivo = new UbicacionActivo();
/* 5034:5104 */                 ubicacionActivo.setNombre(nombreUbicacionEquipo.trim());
/* 5035:5105 */                 ubicacionActivo.setCodigo(codigoUbicacionEquipo.trim());
/* 5036:5106 */                 ubicacionActivo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 5037:5107 */                 ubicacionActivo.setSucursal(AppUtil.getSucursal());
/* 5038:5109 */                 if (fila[(columnaErronea = 21)] != null) {
/* 5039:5110 */                   fila[(columnaErronea = 21)].setCellType(1);
/* 5040:     */                 }
/* 5041:5111 */                 String codigoDepartamento = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 21, true, 
/* 5042:5112 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 5043:5113 */                 Departamento departamento = (Departamento)hashMapDepartamento.get(codigoDepartamento);
/* 5044:5114 */                 if (departamento == null) {
/* 5045:5115 */                   throw new ExcepcionAS2("msg_departamento_no_encontrado", " " + codigoDepartamento + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5046:     */                 }
/* 5047:5118 */                 ubicacionActivo.setDepartamento(departamento);
/* 5048:     */                 
/* 5049:5120 */                 this.servicioUbicacionActivo.guardar(ubicacionActivo);
/* 5050:5121 */                 ((HashMap)hashMapUbicacionActivo).put(ubicacionActivo.getCodigo(), ubicacionActivo);
/* 5051:     */               }
/* 5052:5123 */               equipo.setUbicacion(ubicacionActivo);
/* 5053:5125 */               if (fila[(columnaErronea = 24)] != null) {
/* 5054:5126 */                 fila[(columnaErronea = 24)].setCellType(1);
/* 5055:     */               }
/* 5056:5127 */               String codigoSubCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 24, true, 
/* 5057:5128 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 5058:5129 */               SubcategoriaEquipo subCategoriaEquipo = (SubcategoriaEquipo)((HashMap)hashMapSubcategoriaEquipo).get(codigoSubCategoriaEquipo);
/* 5059:5130 */               if (subCategoriaEquipo == null)
/* 5060:     */               {
/* 5061:5131 */                 if (fila[(columnaErronea = 22)] != null) {
/* 5062:5132 */                   fila[(columnaErronea = 22)].setCellType(1);
/* 5063:     */                 }
/* 5064:5133 */                 String codigoCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 22, true, 
/* 5065:5134 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 5066:5135 */                 CategoriaEquipo categoriaEquipo = (CategoriaEquipo)((HashMap)hashMapCategoriaEquipo).get(codigoCategoriaEquipo);
/* 5067:5136 */                 if (categoriaEquipo != null)
/* 5068:     */                 {
/* 5069:5137 */                   if (fila[(columnaErronea = 25)] != null) {
/* 5070:5138 */                     fila[(columnaErronea = 25)].setCellType(1);
/* 5071:     */                   }
/* 5072:5139 */                   String nombreSubCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 25, true, 
/* 5073:5140 */                     Integer.valueOf(0), Integer.valueOf(100));
/* 5074:5141 */                   subCategoriaEquipo = new SubcategoriaEquipo();
/* 5075:5142 */                   subCategoriaEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 5076:5143 */                   subCategoriaEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 5077:5144 */                   subCategoriaEquipo.setCodigo(codigoSubCategoriaEquipo.trim());
/* 5078:5145 */                   subCategoriaEquipo.setNombre(nombreSubCategoriaEquipo.trim());
/* 5079:5146 */                   subCategoriaEquipo.setCategoriaEquipo(categoriaEquipo);
/* 5080:5147 */                   subCategoriaEquipo.setActivo(true);
/* 5081:5148 */                   this.servicioSubCategoriaEquipo.guardar(subCategoriaEquipo);
/* 5082:5149 */                   ((HashMap)hashMapSubcategoriaEquipo).put(subCategoriaEquipo.getCodigo(), subCategoriaEquipo);
/* 5083:5150 */                   equipo.setSubcategoriaEquipo(subCategoriaEquipo);
/* 5084:     */                 }
/* 5085:     */                 else
/* 5086:     */                 {
/* 5087:5152 */                   fila[(columnaErronea = 23)].setCellType(1);
/* 5088:5153 */                   String nombreCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 23, true, 
/* 5089:5154 */                     Integer.valueOf(0), Integer.valueOf(100));
/* 5090:5155 */                   String nombreSubCategoriaEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 25, true, 
/* 5091:5156 */                     Integer.valueOf(0), Integer.valueOf(100));
/* 5092:5157 */                   categoriaEquipo = new CategoriaEquipo();
/* 5093:5158 */                   categoriaEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 5094:5159 */                   categoriaEquipo.setIdSucursal(AppUtil.getOrganizacion().getId());
/* 5095:5160 */                   categoriaEquipo.setCodigo(codigoCategoriaEquipo.trim());
/* 5096:5161 */                   categoriaEquipo.setNombre(nombreCategoriaEquipo.trim());
/* 5097:5162 */                   categoriaEquipo.setActivo(true);
/* 5098:5163 */                   this.servicioCategoriaEquipo.guardar(categoriaEquipo);
/* 5099:5164 */                   ((HashMap)hashMapCategoriaEquipo).put(categoriaEquipo.getCodigo(), categoriaEquipo);
/* 5100:     */                   
/* 5101:5166 */                   subCategoriaEquipo = new SubcategoriaEquipo();
/* 5102:5167 */                   subCategoriaEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 5103:5168 */                   subCategoriaEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 5104:5169 */                   subCategoriaEquipo.setCodigo(codigoSubCategoriaEquipo.trim());
/* 5105:5170 */                   subCategoriaEquipo.setNombre(nombreSubCategoriaEquipo.trim());
/* 5106:5171 */                   subCategoriaEquipo.setCategoriaEquipo(categoriaEquipo);
/* 5107:5172 */                   subCategoriaEquipo.setActivo(true);
/* 5108:5173 */                   this.servicioSubCategoriaEquipo.guardar(subCategoriaEquipo);
/* 5109:5174 */                   ((HashMap)hashMapSubcategoriaEquipo).put(subCategoriaEquipo.getCodigo(), subCategoriaEquipo);
/* 5110:5175 */                   equipo.setSubcategoriaEquipo(subCategoriaEquipo);
/* 5111:     */                 }
/* 5112:     */               }
/* 5113:     */               else
/* 5114:     */               {
/* 5115:5178 */                 equipo.setSubcategoriaEquipo(subCategoriaEquipo);
/* 5116:     */               }
/* 5117:5182 */               equipo.setIdOrganizacion(idOrganizacion);
/* 5118:5183 */               equipo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 5119:5184 */               this.servicioEquipo.guardar(equipo);
/* 5120:5185 */               ((HashMap)hashMapEquipo).put(equipo.getCodigo(), equipo);
/* 5121:     */               
/* 5122:5187 */               DetalleComponenteEquipo detalleComponenteEquipo = new DetalleComponenteEquipo();
/* 5123:5188 */               detalleComponenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 5124:5189 */               detalleComponenteEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 5125:5190 */               detalleComponenteEquipo.setComponenteEquipo(componenteEquipo);
/* 5126:5191 */               detalleComponenteEquipo.setEquipo(equipo);
/* 5127:5192 */               detalleComponenteEquipo.setTiempoVida(vidaUtil);
/* 5128:5193 */               detalleComponenteEquipo.setPeriodoVidaUtil(periodo);
/* 5129:5194 */               detalleComponenteEquipo.setPorcentajeAlarma(porcentaje);
/* 5130:5195 */               this.servicioDetalleComponenteEquipo.guardar(detalleComponenteEquipo);
/* 5131:5196 */               ((HashMap)hashMapDetalleComponente).put(equipo.getCodigo(), detalleComponenteEquipo);
/* 5132:     */             }
/* 5133:     */             else
/* 5134:     */             {
/* 5135:5200 */               if (fila[(columnaErronea = 13)] != null) {
/* 5136:5201 */                 fila[(columnaErronea = 13)].setCellType(1);
/* 5137:     */               }
/* 5138:5202 */               String codigoComponenteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 13, true, 
/* 5139:5203 */                 Integer.valueOf(0), Integer.valueOf(20));
/* 5140:     */               
/* 5141:5205 */               ComponenteEquipo componenteEquipo = (ComponenteEquipo)hashMapComponenteEquipo.get(codigoComponenteEquipo);
/* 5142:5206 */               if (componenteEquipo == null)
/* 5143:     */               {
/* 5144:5207 */                 if (fila[(columnaErronea = 14)] != null) {
/* 5145:5208 */                   fila[(columnaErronea = 14)].setCellType(1);
/* 5146:     */                 }
/* 5147:5209 */                 String nombreComponenteEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 14, true, 
/* 5148:5210 */                   Integer.valueOf(0), Integer.valueOf(100));
/* 5149:5211 */                 componenteEquipo = new ComponenteEquipo();
/* 5150:5212 */                 componenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 5151:5213 */                 componenteEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 5152:5214 */                 componenteEquipo.setCodigo(codigoComponenteEquipo.trim());
/* 5153:5215 */                 componenteEquipo.setNombre(nombreComponenteEquipo.trim());
/* 5154:5216 */                 componenteEquipo.setActivo(true);
/* 5155:5217 */                 this.servicioComponenteEquipo.guardar(componenteEquipo);
/* 5156:5218 */                 hashMapComponenteEquipo.put(componenteEquipo.getCodigo(), componenteEquipo);
/* 5157:     */               }
/* 5158:     */               else
/* 5159:     */               {
/* 5160:5220 */                 boolean contiene = false;
/* 5161:     */                 
/* 5162:5222 */                 Equipo eq = this.servicioEquipo.cargarDetalle(equipo);
/* 5163:5223 */                 List<DetalleComponenteEquipo> lDCE = eq.getListaComponenteEquipo();
/* 5164:5224 */                 for (DetalleComponenteEquipo detalleCE : lDCE) {
/* 5165:5225 */                   if (detalleCE.getComponenteEquipo().getCodigo().equals(codigoComponenteEquipo)) {
/* 5166:5226 */                     contiene = true;
/* 5167:     */                   }
/* 5168:     */                 }
/* 5169:5229 */                 if (!contiene)
/* 5170:     */                 {
/* 5171:5230 */                   if (fila[(columnaErronea = 15)] != null) {
/* 5172:5231 */                     fila[(columnaErronea = 15)].setCellType(0);
/* 5173:     */                   }
/* 5174:5232 */                   BigDecimal vidaUtil = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 15, false, 
/* 5175:5233 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 5176:5234 */                   FrecuenciaFechaEnum periodo = null;
/* 5177:5235 */                   if (vidaUtil.compareTo(BigDecimal.ZERO) > 0)
/* 5178:     */                   {
/* 5179:5236 */                     if (fila[(columnaErronea = 16)] != null) {
/* 5180:5237 */                       fila[(columnaErronea = 16)].setCellType(1);
/* 5181:     */                     }
/* 5182:5238 */                     String periodoVidaUtil = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 16, true, 
/* 5183:5239 */                       Integer.valueOf(0), Integer.valueOf(20));
/* 5184:5241 */                     if (FrecuenciaFechaEnum.DIAS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 5185:5242 */                       periodo = FrecuenciaFechaEnum.DIAS;
/* 5186:5243 */                     } else if (FrecuenciaFechaEnum.SEMANAS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 5187:5244 */                       periodo = FrecuenciaFechaEnum.SEMANAS;
/* 5188:5245 */                     } else if (FrecuenciaFechaEnum.MESES.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 5189:5246 */                       periodo = FrecuenciaFechaEnum.MESES;
/* 5190:5247 */                     } else if (FrecuenciaFechaEnum.ANIOS.getNombre().equalsIgnoreCase(periodoVidaUtil)) {
/* 5191:5248 */                       periodo = FrecuenciaFechaEnum.ANIOS;
/* 5192:     */                     }
/* 5193:     */                   }
/* 5194:5252 */                   if (fila[(columnaErronea = 17)] != null) {
/* 5195:5253 */                     fila[(columnaErronea = 17)].setCellType(0);
/* 5196:     */                   }
/* 5197:5254 */                   BigDecimal porcentaje = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 17, false, 
/* 5198:5255 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 5199:5256 */                   DetalleComponenteEquipo detalleComponenteEquipo = new DetalleComponenteEquipo();
/* 5200:5257 */                   detalleComponenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 5201:5258 */                   detalleComponenteEquipo.setIdSucursal(AppUtil.getSucursal().getId());
/* 5202:5259 */                   detalleComponenteEquipo.setComponenteEquipo(componenteEquipo);
/* 5203:5260 */                   detalleComponenteEquipo.setEquipo(equipo);
/* 5204:5261 */                   detalleComponenteEquipo.setTiempoVida(vidaUtil);
/* 5205:5262 */                   detalleComponenteEquipo.setPeriodoVidaUtil(periodo);
/* 5206:5263 */                   detalleComponenteEquipo.setPorcentajeAlarma(porcentaje);
/* 5207:5264 */                   this.servicioDetalleComponenteEquipo.guardar(detalleComponenteEquipo);
/* 5208:5265 */                   ((HashMap)hashMapDetalleComponente).put(equipo.getCodigo(), detalleComponenteEquipo);
/* 5209:     */                 }
/* 5210:     */               }
/* 5211:     */             }
/* 5212:     */           }
/* 5213:     */         }
/* 5214:     */       }
/* 5215:     */     }
/* 5216:     */     catch (IllegalArgumentException e)
/* 5217:     */     {
/* 5218:5275 */       LOG.info("Error al migrar equipo", e);
/* 5219:5276 */       e.printStackTrace();
/* 5220:5277 */       this.context.setRollbackOnly();
/* 5221:     */       
/* 5222:5279 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5223:     */     }
/* 5224:     */     catch (IllegalStateException e)
/* 5225:     */     {
/* 5226:5282 */       LOG.info("Error al migrar equipos", e);
/* 5227:5283 */       e.printStackTrace();
/* 5228:5284 */       this.context.setRollbackOnly();
/* 5229:     */       
/* 5230:5286 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5231:     */     }
/* 5232:     */     catch (ExcepcionAS2 e)
/* 5233:     */     {
/* 5234:5288 */       LOG.info("Error al migrar equipos", e);
/* 5235:5289 */       e.printStackTrace();
/* 5236:5290 */       this.context.setRollbackOnly();
/* 5237:5291 */       throw e;
/* 5238:     */     }
/* 5239:     */     catch (Exception e)
/* 5240:     */     {
/* 5241:5293 */       LOG.error("Error al migrar equipos", e);
/* 5242:5294 */       e.printStackTrace();
/* 5243:5295 */       this.context.setRollbackOnly();
/* 5244:     */       
/* 5245:     */ 
/* 5246:5298 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea] != null ? filaErronea[columnaErronea].toString() : "", e);
/* 5247:     */     }
/* 5248:     */   }
/* 5249:     */   
/* 5250:     */   public void migracionLecturaMantenimiento(int idOrganizacion, int idSucursal, String fileName, InputStream imInputStream, int filaInicial)
/* 5251:     */     throws ExcepcionAS2, AS2Exception
/* 5252:     */   {
/* 5253:5311 */     HashMap<String, Equipo> hashMapEquipo = new HashMap();
/* 5254:5312 */     List<Equipo> listaEquipo = this.servicioEquipo.obtenerListaCombo("", false, null);
/* 5255:5313 */     for (Equipo e : listaEquipo) {
/* 5256:5314 */       hashMapEquipo.put(e.getCodigo(), e);
/* 5257:     */     }
/* 5258:5317 */     Object hashMapFrecuencia = new HashMap();
/* 5259:5318 */     List<Frecuencia> listaFrecuencia = this.servicioFrecuencia.obtenerListaCombo(Frecuencia.class, "", false, null);
/* 5260:5319 */     for (Frecuencia fr : listaFrecuencia) {
/* 5261:5320 */       ((HashMap)hashMapFrecuencia).put(fr.getCodigo(), fr);
/* 5262:     */     }
/* 5263:5323 */     int filaActual = filaInicial;
/* 5264:5324 */     int columnaErronea = 0;
/* 5265:5325 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 5266:     */     try
/* 5267:     */     {
/* 5268:5330 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(AppUtil.getOrganizacion().getId(), fileName, imInputStream, filaInicial, 0);
/* 5269:5332 */       for (HSSFCell[] fila : datos)
/* 5270:     */       {
/* 5271:5333 */         filaErronea = fila;
/* 5272:5334 */         filaActual++;
/* 5273:5335 */         LecturaMantenimiento lecturaMantenimiento = new LecturaMantenimiento();
/* 5274:5336 */         String codigoEquipo = "";
/* 5275:5337 */         if (fila[(columnaErronea = 0)] != null)
/* 5276:     */         {
/* 5277:5338 */           fila[(columnaErronea = 0)].setCellType(1);
/* 5278:5339 */           codigoEquipo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 0, true, Integer.valueOf(0), Integer.valueOf(20));
/* 5279:     */         }
/* 5280:5341 */         Equipo eq = (Equipo)hashMapEquipo.get(codigoEquipo);
/* 5281:5342 */         HashMap<String, ComponenteEquipo> hashMapComponenteEquipo = new HashMap();
/* 5282:5343 */         HashMap<String, ActividadMantenimiento> hashMapActividadMantenimiento = new HashMap();
/* 5283:5345 */         if (eq != null)
/* 5284:     */         {
/* 5285:5347 */           Equipo equipo = this.servicioEquipo.cargarDetalle(eq);
/* 5286:5349 */           if (!equipo.getListaPlanMantenimientoEquipo().isEmpty())
/* 5287:     */           {
/* 5288:5350 */             lecturaMantenimiento.setEquipo(equipo);
/* 5289:5351 */             for (PlanMantenimientoEquipo pM : equipo.getListaPlanMantenimientoEquipo())
/* 5290:     */             {
/* 5291:5352 */               PlanMantenimiento plan = this.servicioPlanMantenimiento.cargarDetalle(pM.getPlanMantenimiento());
/* 5292:5353 */               for (DetallePlanMantenimiento detalle : plan.getListaDetallePlanMantenimiento()) {
/* 5293:5354 */                 hashMapActividadMantenimiento.put(detalle.getActividad().getCodigo(), detalle.getActividad());
/* 5294:     */               }
/* 5295:     */             }
/* 5296:5358 */             for (DetalleComponenteEquipo detalleComponenteEquipo : equipo.getListaComponenteEquipo()) {
/* 5297:5359 */               hashMapComponenteEquipo.put(detalleComponenteEquipo.getComponenteEquipo().getCodigo(), detalleComponenteEquipo
/* 5298:5360 */                 .getComponenteEquipo());
/* 5299:     */             }
/* 5300:5364 */             String tipoFrecuencia = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 5, true, 
/* 5301:5365 */               Integer.valueOf(0), Integer.valueOf(10));
/* 5302:5366 */             if ((tipoFrecuencia.equalsIgnoreCase("Tiempo")) || (tipoFrecuencia.equalsIgnoreCase("Lectura")))
/* 5303:     */             {
/* 5304:5367 */               if (tipoFrecuencia.equalsIgnoreCase("Tiempo"))
/* 5305:     */               {
/* 5306:5368 */                 lecturaMantenimiento.setIndicadorTiempo(true);
/* 5307:5369 */                 String codigoComponente = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 1, true, 
/* 5308:5370 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 5309:     */                 
/* 5310:5372 */                 ComponenteEquipo componenteEquipo = new ComponenteEquipo();
/* 5311:5373 */                 componenteEquipo = (ComponenteEquipo)hashMapComponenteEquipo.get(codigoComponente);
/* 5312:5374 */                 if (componenteEquipo != null) {
/* 5313:5375 */                   lecturaMantenimiento.setComponenteEquipo(componenteEquipo);
/* 5314:     */                 } else {
/* 5315:5377 */                   throw new ExcepcionAS2("msg_info_componente_no_asignado", " " + codigoComponente + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5316:     */                 }
/* 5317:5380 */                 Date fechaLectura = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaErronea = 2, true, 
/* 5318:5381 */                   Integer.valueOf(0), Integer.valueOf(0));
/* 5319:5382 */                 lecturaMantenimiento.setFechaLectura(fechaLectura);
/* 5320:5383 */                 String codigoActividad = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 7, true, 
/* 5321:5384 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 5322:5385 */                 ActividadMantenimiento actividadMantenimiento = new ActividadMantenimiento();
/* 5323:     */                 
/* 5324:5387 */                 actividadMantenimiento = (ActividadMantenimiento)hashMapActividadMantenimiento.get(codigoActividad);
/* 5325:5388 */                 if (actividadMantenimiento != null) {
/* 5326:5389 */                   lecturaMantenimiento.setActividadMantenimiento(actividadMantenimiento);
/* 5327:     */                 } else {
/* 5328:5391 */                   throw new ExcepcionAS2("msg_actividad_no_encontrada", " " + codigoActividad + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5329:     */                 }
/* 5330:     */               }
/* 5331:5395 */               else if (tipoFrecuencia.equalsIgnoreCase("Lectura"))
/* 5332:     */               {
/* 5333:5397 */                 String codigoFrecuencia = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 3, true, 
/* 5334:5398 */                   Integer.valueOf(0), Integer.valueOf(20));
/* 5335:5399 */                 Frecuencia frecuencia = (Frecuencia)((HashMap)hashMapFrecuencia).get(codigoFrecuencia);
/* 5336:5400 */                 if ((frecuencia != null) && (frecuencia.getTipoFrecuenciaEnum().getNombre().equalsIgnoreCase("Lectura")))
/* 5337:     */                 {
/* 5338:5401 */                   String codigoComponente = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 1, false, 
/* 5339:5402 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 5340:5403 */                   ComponenteEquipo componenteEquipo = new ComponenteEquipo();
/* 5341:5404 */                   componenteEquipo = (ComponenteEquipo)hashMapComponenteEquipo.get(codigoComponente);
/* 5342:5405 */                   if (componenteEquipo != null) {
/* 5343:5406 */                     lecturaMantenimiento.setComponenteEquipo(componenteEquipo);
/* 5344:     */                   } else {
/* 5345:5408 */                     System.out.println(">>>>>>>>> No se encontro el componente, la lectura es de todo el equipo");
/* 5346:     */                   }
/* 5347:5410 */                   Date fechaLectura = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaErronea = 2, true, 
/* 5348:5411 */                     Integer.valueOf(0), Integer.valueOf(0));
/* 5349:5412 */                   lecturaMantenimiento.setFechaLectura(fechaLectura);
/* 5350:5413 */                   String codigoActividad = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 7, false, 
/* 5351:5414 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 5352:5415 */                   ActividadMantenimiento actividadMantenimiento = new ActividadMantenimiento();
/* 5353:5416 */                   actividadMantenimiento = (ActividadMantenimiento)hashMapActividadMantenimiento.get(codigoActividad);
/* 5354:5417 */                   if (actividadMantenimiento != null) {
/* 5355:5418 */                     lecturaMantenimiento.setActividadMantenimiento(actividadMantenimiento);
/* 5356:     */                   }
/* 5357:5420 */                   BigDecimal valorLectura = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = 6, true, 
/* 5358:5421 */                     Integer.valueOf(0), Integer.valueOf(20));
/* 5359:5422 */                   lecturaMantenimiento.setValor(valorLectura);
/* 5360:5423 */                   lecturaMantenimiento.setFrecuencia(frecuencia);
/* 5361:5424 */                   if (fila[(columnaErronea = 8)] != null) {
/* 5362:5425 */                     fila[(columnaErronea = 8)].setCellType(1);
/* 5363:     */                   }
/* 5364:5426 */                   String indicadorAutomatico = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 8, false, 
/* 5365:5427 */                     Integer.valueOf(0), Integer.valueOf(3));
/* 5366:5428 */                   if (indicadorAutomatico == null) {
/* 5367:5429 */                     indicadorAutomatico = "NO";
/* 5368:     */                   }
/* 5369:5431 */                   if (indicadorAutomatico.equalsIgnoreCase("SI")) {
/* 5370:5432 */                     lecturaMantenimiento.setIndicadorAutomatico(true);
/* 5371:     */                   }
/* 5372:     */                 }
/* 5373:     */                 else
/* 5374:     */                 {
/* 5375:5436 */                   throw new ExcepcionAS2("msg_error_frecuencia_no_registrada", " " + tipoFrecuencia + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5376:     */                 }
/* 5377:     */               }
/* 5378:     */             }
/* 5379:     */             else {
/* 5380:5442 */               throw new ExcepcionAS2("msg_error_tipo_frecuencia", " " + tipoFrecuencia + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5381:     */             }
/* 5382:     */           }
/* 5383:     */           else
/* 5384:     */           {
/* 5385:5446 */             throw new ExcepcionAS2("msg_info_equipo_no_plan_mantenimiento", " " + codigoEquipo + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5386:     */           }
/* 5387:     */         }
/* 5388:     */         else
/* 5389:     */         {
/* 5390:5450 */           throw new ExcepcionAS2("msg_info_equipo_no_encontrado", " " + codigoEquipo + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5391:     */         }
/* 5392:5453 */         lecturaMantenimiento.setIdOrganizacion(idOrganizacion);
/* 5393:5454 */         lecturaMantenimiento.setIdSucursal(idSucursal);
/* 5394:5455 */         if (lecturaMantenimiento.getFrecuencia() != null) {
/* 5395:5456 */           this.servicioLecturaMantenimiento.actualizarValoresLecturaMantenimiento(lecturaMantenimiento, true);
/* 5396:     */         }
/* 5397:5458 */         this.servicioLecturaMantenimiento.guardar(lecturaMantenimiento);
/* 5398:     */       }
/* 5399:     */     }
/* 5400:     */     catch (IllegalArgumentException e)
/* 5401:     */     {
/* 5402:5464 */       LOG.info("Error al migrar lecturas", e);
/* 5403:5465 */       e.printStackTrace();
/* 5404:5466 */       this.context.setRollbackOnly();
/* 5405:     */       
/* 5406:5468 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5407:     */     }
/* 5408:     */     catch (IllegalStateException e)
/* 5409:     */     {
/* 5410:5471 */       LOG.info("Error al migrar lecturas", e);
/* 5411:5472 */       e.printStackTrace();
/* 5412:5473 */       this.context.setRollbackOnly();
/* 5413:     */       
/* 5414:5475 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5415:     */     }
/* 5416:     */     catch (AS2Exception e)
/* 5417:     */     {
/* 5418:5477 */       LOG.info("Error al migrar lecturas", e);
/* 5419:5478 */       JsfUtil.addErrorMessage(e, " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5420:     */       
/* 5421:5480 */       throw new AS2Exception(e.getCodigoExcepcion(), new String[] {" Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString() });
/* 5422:     */     }
/* 5423:     */     catch (ExcepcionAS2 e)
/* 5424:     */     {
/* 5425:5482 */       LOG.info("Error al migrar lecturas", e);
/* 5426:5483 */       e.printStackTrace();
/* 5427:5484 */       this.context.setRollbackOnly();
/* 5428:5485 */       throw e;
/* 5429:     */     }
/* 5430:     */     catch (Exception e)
/* 5431:     */     {
/* 5432:5487 */       LOG.error("Error al migrar lecturas", e);
/* 5433:5488 */       e.printStackTrace();
/* 5434:5489 */       this.context.setRollbackOnly();
/* 5435:     */       
/* 5436:     */ 
/* 5437:5492 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea] != null ? filaErronea[columnaErronea].toString() : "", e);
/* 5438:     */     }
/* 5439:     */   }
/* 5440:     */   
/* 5441:     */   public void migracionCustodioActivoFijo(int idOrganizacion, int idSucursal, String fileName, InputStream imInputStream, int filaInicial)
/* 5442:     */     throws ExcepcionAS2, AS2Exception
/* 5443:     */   {
/* 5444:5504 */     HashMap<String, ActivoFijo> hashActivoFijo = new HashMap();
/* 5445:5505 */     HashMap<String, String> filtrosAF = new HashMap();
/* 5446:5506 */     filtrosAF.put("notSetMaxResults", "true");
/* 5447:5507 */     List<ActivoFijo> listActivoFijo = this.servicioActivoFijo.obtenerListaCombo("", false, filtrosAF);
/* 5448:5508 */     for (ActivoFijo activoFijo : listActivoFijo) {
/* 5449:5509 */       hashActivoFijo.put(activoFijo.getCodigo().trim(), activoFijo);
/* 5450:     */     }
/* 5451:5512 */     Object hashEmpleado = new HashMap();
/* 5452:5513 */     List<Empleado> listEmpleado = this.servicioEmpleado.obtenerListaCombo("", false, null);
/* 5453:5514 */     for (Empleado empleado : listEmpleado) {
/* 5454:5515 */       ((HashMap)hashEmpleado).put(empleado.getEmpresa().getIdentificacion().trim(), empleado);
/* 5455:     */     }
/* 5456:5518 */     Object hashUbicacion = new HashMap();
/* 5457:5519 */     List<UbicacionActivo> listUbicacion = this.servicioUbicacionActivo.obtenerListaCombo("", false, null);
/* 5458:5520 */     for (UbicacionActivo ubicacionActivo : listUbicacion) {
/* 5459:5521 */       ((HashMap)hashUbicacion).put(ubicacionActivo.getCodigo().trim(), ubicacionActivo);
/* 5460:     */     }
/* 5461:5524 */     Object hashCliente = new HashMap();
/* 5462:5525 */     HashMap<String, String> filtros = new HashMap();
/* 5463:5526 */     filtros.put("notSetMaxResults", "true");
/* 5464:5527 */     List<Empresa> listCliente = this.servicioEmpresa.obtenerListaCombo("", false, filtros);
/* 5465:5528 */     for (Empresa cliente : listCliente) {
/* 5466:5529 */       ((HashMap)hashCliente).put(cliente.getIdentificacion().trim(), cliente);
/* 5467:     */     }
/* 5468:5531 */     int filaActual = filaInicial;
/* 5469:5532 */     int columnaErronea = 0;
/* 5470:5533 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 5471:     */     try
/* 5472:     */     {
/* 5473:5538 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(idOrganizacion, fileName, imInputStream, filaInicial, 0);
/* 5474:5540 */       for (HSSFCell[] fila : datos)
/* 5475:     */       {
/* 5476:5541 */         filaErronea = fila;
/* 5477:5542 */         filaActual++;
/* 5478:5543 */         String codigoActivoFijo = "";
/* 5479:5544 */         if (fila[(columnaErronea = 0)] != null) {
/* 5480:5545 */           fila[(columnaErronea = 0)].setCellType(1);
/* 5481:     */         }
/* 5482:5546 */         codigoActivoFijo = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 0, true, Integer.valueOf(0), Integer.valueOf(10));
/* 5483:5547 */         ActivoFijo activoFijo = (ActivoFijo)hashActivoFijo.get(codigoActivoFijo.trim());
/* 5484:5548 */         if (activoFijo != null)
/* 5485:     */         {
/* 5486:5549 */           String identificacionEmpleado = "";
/* 5487:5550 */           if (fila[(columnaErronea = 2)] != null) {
/* 5488:5551 */             fila[(columnaErronea = 2)].setCellType(1);
/* 5489:     */           }
/* 5490:5552 */           identificacionEmpleado = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 2, true, Integer.valueOf(0), 
/* 5491:5553 */             Integer.valueOf(20));
/* 5492:5554 */           Empleado empleado = (Empleado)((HashMap)hashEmpleado).get(identificacionEmpleado.trim());
/* 5493:5555 */           if (empleado == null) {
/* 5494:5556 */             throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " " + identificacionEmpleado + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5495:     */           }
/* 5496:5560 */           String identificacionCliente = "";
/* 5497:5561 */           if (fila[(columnaErronea = 3)] != null) {
/* 5498:5562 */             fila[(columnaErronea = 3)].setCellType(1);
/* 5499:     */           }
/* 5500:5563 */           identificacionCliente = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 3, false, Integer.valueOf(0), 
/* 5501:5564 */             Integer.valueOf(20));
/* 5502:5565 */           Empresa cliente = null;
/* 5503:5566 */           if (identificacionCliente != null)
/* 5504:     */           {
/* 5505:5567 */             cliente = (Empresa)((HashMap)hashCliente).get(identificacionCliente.trim());
/* 5506:5568 */             if (cliente == null) {
/* 5507:5569 */               throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " " + identificacionCliente + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5508:     */             }
/* 5509:     */           }
/* 5510:5574 */           String codigoUbicacion = "";
/* 5511:5575 */           if (fila[(columnaErronea = 4)] != null) {
/* 5512:5576 */             fila[(columnaErronea = 4)].setCellType(1);
/* 5513:     */           }
/* 5514:5578 */           codigoUbicacion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 4, true, Integer.valueOf(0), Integer.valueOf(10));
/* 5515:     */           
/* 5516:5580 */           UbicacionActivo ubicacionActivo = (UbicacionActivo)((HashMap)hashUbicacion).get(codigoUbicacion.trim());
/* 5517:5582 */           if (ubicacionActivo == null) {
/* 5518:5583 */             throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " " + ubicacionActivo + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5519:     */           }
/* 5520:5587 */           Date fechaInicio = (Date)FuncionesUtiles.validarCelda(fila, FormatoCelda.FECHA, filaActual, columnaErronea = 5, true, Integer.valueOf(0), Integer.valueOf(0));
/* 5521:5588 */           if (fechaInicio == null) {
/* 5522:5589 */             throw new ExcepcionAS2("msg_error_dato_obligatorio", " Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5523:     */           }
/* 5524:5592 */           String descripcion = "";
/* 5525:5593 */           if (fila[(columnaErronea = 6)] != null) {
/* 5526:5594 */             fila[(columnaErronea = 6)].setCellType(1);
/* 5527:     */           }
/* 5528:5596 */           descripcion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 6, false, Integer.valueOf(0), Integer.valueOf(200));
/* 5529:5597 */           ActivoFijo af = new ActivoFijo();
/* 5530:5598 */           af = this.servicioActivoFijo.buscarPorId(activoFijo.getId());
/* 5531:5599 */           CustodioActivoFijo custodioActivoFijo = new CustodioActivoFijo();
/* 5532:5600 */           custodioActivoFijo.setIdOrganizacion(idOrganizacion);
/* 5533:5601 */           custodioActivoFijo.setIdSucursal(idSucursal);
/* 5534:5602 */           custodioActivoFijo.setActivoFijo(af);
/* 5535:5603 */           custodioActivoFijo.setEmpleado(empleado);
/* 5536:5604 */           custodioActivoFijo.setEmpresa(cliente);
/* 5537:5605 */           custodioActivoFijo.setUbicacionActivo(ubicacionActivo);
/* 5538:5606 */           custodioActivoFijo.setActivo(true);
/* 5539:5607 */           custodioActivoFijo.setFechaInicio(fechaInicio);
/* 5540:5608 */           custodioActivoFijo.setDescripcion(descripcion);
/* 5541:5609 */           this.servicioCustodioActivoFijo.guardar(custodioActivoFijo);
/* 5542:     */         }
/* 5543:     */         else
/* 5544:     */         {
/* 5545:5611 */           throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " " + codigoActivoFijo + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5546:     */         }
/* 5547:     */       }
/* 5548:     */     }
/* 5549:     */     catch (IllegalArgumentException e)
/* 5550:     */     {
/* 5551:5619 */       LOG.info("Error al migrar custodio Activo Fijo", e);
/* 5552:5620 */       e.printStackTrace();
/* 5553:5621 */       this.context.setRollbackOnly();
/* 5554:     */       
/* 5555:5623 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5556:     */     }
/* 5557:     */     catch (IllegalStateException e)
/* 5558:     */     {
/* 5559:5625 */       LOG.info("Error al migrar custodio Activo Fijo", e);
/* 5560:5626 */       e.printStackTrace();
/* 5561:5627 */       this.context.setRollbackOnly();
/* 5562:     */       
/* 5563:5629 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5564:     */     }
/* 5565:     */     catch (ExcepcionAS2 e)
/* 5566:     */     {
/* 5567:5631 */       LOG.info("Error al migrar custodio Activo Fijo", e);
/* 5568:5632 */       e.printStackTrace();
/* 5569:5633 */       this.context.setRollbackOnly();
/* 5570:5634 */       throw e;
/* 5571:     */     }
/* 5572:     */     catch (Exception e)
/* 5573:     */     {
/* 5574:5636 */       LOG.error("Error al migrar custodio Activo Fijo", e);
/* 5575:5637 */       e.printStackTrace();
/* 5576:5638 */       this.context.setRollbackOnly();
/* 5577:     */       
/* 5578:     */ 
/* 5579:5641 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea] != null ? filaErronea[columnaErronea].toString() : "", e);
/* 5580:     */     }
/* 5581:     */   }
/* 5582:     */   
/* 5583:     */   public void migrarListaDetalleVersionPlanComision(PlanComision planComision, VersionPlanComision versionPlanComision, String fileName, InputStream imInputStream, int filaInicial, int clasificadorProducto)
/* 5584:     */     throws AS2Exception, ExcepcionAS2
/* 5585:     */   {
/* 5586:5649 */     Map<String, DetalleVersionPlanComision> mapaDetalleVersionPlanComision = new HashMap();
/* 5587:5651 */     for (DetalleVersionPlanComision detalle : versionPlanComision.getListaDetalleVersionPlanComision())
/* 5588:     */     {
/* 5589:5652 */       detalle.setEliminado(true);
/* 5590:5653 */       String key = "";
/* 5591:5654 */       if (detalle.getCategoriaProducto() != null) {
/* 5592:5655 */         key = key + "~" + detalle.getCategoriaProducto().getId();
/* 5593:     */       }
/* 5594:5657 */       if (detalle.getSubcategoriaProducto() != null) {
/* 5595:5658 */         key = key + "~" + detalle.getSubcategoriaProducto().getId();
/* 5596:     */       }
/* 5597:5660 */       if (detalle.getProducto() != null) {
/* 5598:5661 */         key = key + "~" + detalle.getProducto().getId();
/* 5599:     */       }
/* 5600:5663 */       mapaDetalleVersionPlanComision.put(key, detalle);
/* 5601:     */     }
/* 5602:5666 */     Object mapaCategoriaProducto = new HashMap();
/* 5603:5667 */     Map<String, SubcategoriaProducto> mapaSubcategoriaProducto = new HashMap();
/* 5604:5668 */     Map<String, Producto> mapaProducto = new HashMap();
/* 5605:5669 */     Map<String, RangoDiasComision> mapaRangoDiasComision = new HashMap();
/* 5606:     */     
/* 5607:5671 */     Map<String, String> filtros = new HashMap();
/* 5608:5672 */     filtros.put("idOrganizacion", planComision.getIdOrganizacion() + "");
/* 5609:5673 */     filtros.put("activo", "true");
/* 5610:     */     Iterator localIterator2;
/* 5611:     */     CategoriaProducto categoriaProducto;
/* 5612:     */     SubcategoriaProducto subcategoriaProducto;
/* 5613:5674 */     switch (clasificadorProducto)
/* 5614:     */     {
/* 5615:     */     case 1: 
/* 5616:5676 */       List<CategoriaProducto> listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filtros);
/* 5617:5677 */       for (localIterator2 = listaCategoriaProductos.iterator(); localIterator2.hasNext();)
/* 5618:     */       {
/* 5619:5677 */         categoriaProducto = (CategoriaProducto)localIterator2.next();
/* 5620:5678 */         ((Map)mapaCategoriaProducto).put(categoriaProducto.getCodigo(), categoriaProducto);
/* 5621:     */       }
/* 5622:5680 */       break;
/* 5623:     */     case 2: 
/* 5624:5682 */       listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaPorPagina(0, 1000, "nombre", true, filtros);
/* 5625:5684 */       for (categoriaProducto = ((List)listaSubcategoriaProducto).iterator(); categoriaProducto.hasNext();)
/* 5626:     */       {
/* 5627:5684 */         subcategoriaProducto = (SubcategoriaProducto)categoriaProducto.next();
/* 5628:5685 */         mapaSubcategoriaProducto.put(subcategoriaProducto.getCodigo(), subcategoriaProducto);
/* 5629:     */       }
/* 5630:5687 */       break;
/* 5631:     */     case 3: 
/* 5632:5689 */       List<Producto> listaProducto = this.servicioProducto.obtenerListaCombo("nombre", true, filtros);
/* 5633:5690 */       for (Producto producto : listaProducto) {
/* 5634:5691 */         mapaProducto.put(producto.getCodigo(), producto);
/* 5635:     */       }
/* 5636:5693 */       break;
/* 5637:     */     }
/* 5638:5697 */     List<RangoDiasComision> listaRangoDiasComision = this.servicioRangoDiasComision.obtenerListaCombo(RangoDiasComision.class, "nombre", true, filtros);
/* 5639:5699 */     for (Object listaSubcategoriaProducto = listaRangoDiasComision.iterator(); ((Iterator)listaSubcategoriaProducto).hasNext();)
/* 5640:     */     {
/* 5641:5699 */       RangoDiasComision rangoDiasComision = (RangoDiasComision)((Iterator)listaSubcategoriaProducto).next();
/* 5642:5700 */       mapaRangoDiasComision.put(rangoDiasComision.getNombre(), rangoDiasComision);
/* 5643:     */     }
/* 5644:5703 */     int filaActual = filaInicial;
/* 5645:5704 */     int columnaErronea = 0;
/* 5646:5705 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 5647:     */     try
/* 5648:     */     {
/* 5649:5709 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(planComision.getIdOrganizacion(), fileName, imInputStream, filaInicial, 0);
/* 5650:5710 */       int i = 0;
/* 5651:5711 */       Map<Integer, RangoDiasComision> mapaRangosDiasExcel = new HashMap();
/* 5652:5712 */       for (HSSFCell[] fila : datos)
/* 5653:     */       {
/* 5654:5713 */         filaErronea = fila;
/* 5655:5714 */         filaActual++;
/* 5656:5716 */         if (i == 0)
/* 5657:     */         {
/* 5658:5717 */           for (int j = 4; j < fila.length; j++)
/* 5659:     */           {
/* 5660:5718 */             String nombreRangoDias = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = j, true, 
/* 5661:5719 */               Integer.valueOf(0), Integer.valueOf(50));
/* 5662:5720 */             RangoDiasComision rangoDias = (RangoDiasComision)mapaRangoDiasComision.get(nombreRangoDias);
/* 5663:5721 */             if (rangoDias == null) {
/* 5664:5722 */               throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " " + nombreRangoDias + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5665:     */             }
/* 5666:5725 */             mapaRangosDiasExcel.put(Integer.valueOf(j), rangoDias);
/* 5667:     */           }
/* 5668:     */         }
/* 5669:     */         else
/* 5670:     */         {
/* 5671:5729 */           String codigoClasificadorProducto = "";
/* 5672:5730 */           if (fila[(columnaErronea = 0)] != null) {
/* 5673:5731 */             fila[(columnaErronea = 0)].setCellType(1);
/* 5674:     */           }
/* 5675:5733 */           codigoClasificadorProducto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 0, true, 
/* 5676:5734 */             Integer.valueOf(0), Integer.valueOf(20));
/* 5677:5735 */           CategoriaProducto categoriaProducto = null;
/* 5678:5736 */           SubcategoriaProducto subcategoriaProducto = null;
/* 5679:5737 */           Producto producto = null;
/* 5680:5738 */           switch (clasificadorProducto)
/* 5681:     */           {
/* 5682:     */           case 1: 
/* 5683:5740 */             categoriaProducto = (CategoriaProducto)((Map)mapaCategoriaProducto).get(codigoClasificadorProducto);
/* 5684:5741 */             break;
/* 5685:     */           case 2: 
/* 5686:5743 */             subcategoriaProducto = (SubcategoriaProducto)mapaSubcategoriaProducto.get(codigoClasificadorProducto);
/* 5687:5744 */             break;
/* 5688:     */           case 3: 
/* 5689:5746 */             producto = (Producto)mapaProducto.get(codigoClasificadorProducto);
/* 5690:5747 */             break;
/* 5691:     */           }
/* 5692:5751 */           if ((categoriaProducto == null) && (subcategoriaProducto == null) && (producto == null)) {
/* 5693:5752 */             throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " " + codigoClasificadorProducto + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5694:     */           }
/* 5695:5755 */           if (subcategoriaProducto != null) {
/* 5696:5756 */             categoriaProducto = subcategoriaProducto.getCategoriaProducto();
/* 5697:     */           }
/* 5698:5759 */           String key = "";
/* 5699:5760 */           if (categoriaProducto != null) {
/* 5700:5761 */             key = key + "~" + categoriaProducto.getId();
/* 5701:     */           }
/* 5702:5763 */           if (subcategoriaProducto != null) {
/* 5703:5764 */             key = key + "~" + subcategoriaProducto.getId();
/* 5704:     */           }
/* 5705:5766 */           if (producto != null) {
/* 5706:5767 */             key = key + "~" + producto.getId();
/* 5707:     */           }
/* 5708:5769 */           DetalleVersionPlanComision detalle = (DetalleVersionPlanComision)mapaDetalleVersionPlanComision.get(key);
/* 5709:5770 */           if (detalle == null)
/* 5710:     */           {
/* 5711:5771 */             detalle = new DetalleVersionPlanComision();
/* 5712:5772 */             detalle.setIdOrganizacion(planComision.getIdOrganizacion());
/* 5713:5773 */             detalle.setIdSucursal(planComision.getIdSucursal());
/* 5714:5774 */             detalle.setVersionPlanComision(versionPlanComision);
/* 5715:5775 */             detalle.setCategoriaProducto(categoriaProducto);
/* 5716:5776 */             detalle.setSubcategoriaProducto(subcategoriaProducto);
/* 5717:5777 */             detalle.setProducto(producto);
/* 5718:5778 */             versionPlanComision.getListaDetalleVersionPlanComision().add(detalle);
/* 5719:     */           }
/* 5720:5780 */           detalle.setEliminado(false);
/* 5721:5781 */           this.servicioPlanComision.actualizarRangoDiasPorDetalleVersion(detalle, listaRangoDiasComision);
/* 5722:5784 */           if (fila[(columnaErronea = 2)] != null) {
/* 5723:5785 */             fila[(columnaErronea = 2)].setCellType(1);
/* 5724:     */           }
/* 5725:5787 */           String nombreFormaPago = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 2, true, Integer.valueOf(0), 
/* 5726:5788 */             Integer.valueOf(20));
/* 5727:5789 */           if (FormaPagoComisionEnum.CANTIDAD.toString().equals(nombreFormaPago)) {
/* 5728:5790 */             detalle.setFormaPagoComisionEnum(FormaPagoComisionEnum.CANTIDAD);
/* 5729:5791 */           } else if (FormaPagoComisionEnum.PORCENTAJE_COBRO.toString().equals(nombreFormaPago)) {
/* 5730:5792 */             detalle.setFormaPagoComisionEnum(FormaPagoComisionEnum.PORCENTAJE_COBRO);
/* 5731:     */           } else {
/* 5732:5794 */             throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", " " + nombreFormaPago + "  Fila: " + filaActual + " Columna: " + columnaErronea);
/* 5733:     */           }
/* 5734:5799 */           if (fila[(columnaErronea = 3)] != null) {
/* 5735:5800 */             fila[(columnaErronea = 3)].setCellType(1);
/* 5736:     */           }
/* 5737:5802 */           String descripcion = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaErronea = 3, false, Integer.valueOf(0), 
/* 5738:5803 */             Integer.valueOf(200));
/* 5739:5804 */           detalle.setDescripcion(descripcion);
/* 5740:5807 */           for (int j = 4; j < fila.length; j++)
/* 5741:     */           {
/* 5742:5808 */             if (fila[(columnaErronea = j)] != null) {
/* 5743:5809 */               fila[(columnaErronea = j)].setCellType(0);
/* 5744:     */             }
/* 5745:5811 */             BigDecimal valorRangoDias = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.NUMERO, filaActual, columnaErronea = j, false, 
/* 5746:5812 */               Integer.valueOf(0), Integer.valueOf(50));
/* 5747:5813 */             RangoDiasComision rangoDias = (RangoDiasComision)mapaRangosDiasExcel.get(Integer.valueOf(j));
/* 5748:5814 */             DetalleVersionPlanComisionRangoDias detalleRangoDias = this.servicioPlanComision.obtenerValorDetalleVersionPlanComision(detalle, rangoDias);
/* 5749:     */             
/* 5750:5816 */             detalleRangoDias.setValor(valorRangoDias);
/* 5751:     */           }
/* 5752:     */         }
/* 5753:5820 */         i++;
/* 5754:     */       }
/* 5755:     */     }
/* 5756:     */     catch (IllegalArgumentException e)
/* 5757:     */     {
/* 5758:5823 */       LOG.info("Error al migrar custodio Activo Fijo", e);
/* 5759:5824 */       e.printStackTrace();
/* 5760:5825 */       this.context.setRollbackOnly();
/* 5761:     */       
/* 5762:5827 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5763:     */     }
/* 5764:     */     catch (IllegalStateException e)
/* 5765:     */     {
/* 5766:5829 */       LOG.info("Error al migrar", e);
/* 5767:5830 */       e.printStackTrace();
/* 5768:5831 */       this.context.setRollbackOnly();
/* 5769:     */       
/* 5770:5833 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea].toString());
/* 5771:     */     }
/* 5772:     */     catch (ExcepcionAS2 e)
/* 5773:     */     {
/* 5774:5835 */       LOG.info("Error al migrar", e);
/* 5775:5836 */       e.printStackTrace();
/* 5776:5837 */       this.context.setRollbackOnly();
/* 5777:5838 */       throw e;
/* 5778:     */     }
/* 5779:     */     catch (Exception e)
/* 5780:     */     {
/* 5781:5840 */       LOG.error("Error al migrar", e);
/* 5782:5841 */       e.printStackTrace();
/* 5783:5842 */       this.context.setRollbackOnly();
/* 5784:     */       
/* 5785:     */ 
/* 5786:5845 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaErronea + " Dato: " + filaErronea[columnaErronea] != null ? filaErronea[columnaErronea].toString() : "", e);
/* 5787:     */     }
/* 5788:     */   }
/* 5789:     */   
/* 5790:     */   public void cargarOrdenSalidaMaterial(int idOrganizacion, InputStream imInputStream, int filaInicial, List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMateriales, OrdenSalidaMaterial ordenSalidaMaterial)
/* 5791:     */     throws ExcepcionAS2, IOException, AS2Exception
/* 5792:     */   {
/* 5793:5855 */     HashMap<String, String> filters = new HashMap();
/* 5794:5856 */     filters.put("idOrganizacion", Integer.toString(idOrganizacion));
/* 5795:     */     
/* 5796:5858 */     HashMap<String, Producto> hmProducto = new HashMap();
/* 5797:5859 */     for (Iterator localIterator = this.servicioProducto.obtenerListaCombo("codigo", true, filters).iterator(); localIterator.hasNext();)
/* 5798:     */     {
/* 5799:5859 */       producto = (Producto)localIterator.next();
/* 5800:5860 */       hmProducto.put(producto.getCodigo(), producto);
/* 5801:     */     }
/* 5802:5863 */     Object hmBodega = new HashMap();
/* 5803:5864 */     for (Producto producto = this.servicioBodega.obtenerListaCombo("codigo", true, filters).iterator(); producto.hasNext();)
/* 5804:     */     {
/* 5805:5864 */       bodega = (Bodega)producto.next();
/* 5806:5865 */       ((HashMap)hmBodega).put(bodega.getCodigo(), bodega);
/* 5807:     */     }
/* 5808:5868 */     HashMap<String, DestinoCosto> hmDestinoCosto = new HashMap();
/* 5809:5869 */     for (Bodega bodega = this.servicioDestinoCosto.obtenerListaCombo("codigo", true, filters).iterator(); bodega.hasNext();)
/* 5810:     */     {
/* 5811:5869 */       destinoCosto = (DestinoCosto)bodega.next();
/* 5812:5870 */       hmDestinoCosto.put(destinoCosto.getCodigo(), destinoCosto);
/* 5813:     */     }
/* 5814:     */     DestinoCosto destinoCosto;
/* 5815:5873 */     HashMap<String, Lote> hmLotePorProducto = new HashMap();
/* 5816:5874 */     for (Lote lote : this.servicioLote.obtenerListaCombo("codigo", true, filters)) {
/* 5817:5875 */       hmLotePorProducto.put(lote.getCodigo() + "~" + lote.getProducto().getCodigo(), lote);
/* 5818:     */     }
/* 5819:5878 */     int filaActual = filaInicial;
/* 5820:5879 */     int columnaErronea = -1;
/* 5821:5880 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 5822:     */     try
/* 5823:     */     {
/* 5824:5885 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 5825:5887 */       for (HSSFCell[] fila : datos)
/* 5826:     */       {
/* 5827:5889 */         filaErronea = fila;
/* 5828:5890 */         filaActual++;
/* 5829:     */         
/* 5830:     */ 
/* 5831:5893 */         String codigoProducto = fila[(columnaErronea = 1)].getStringCellValue().trim();
/* 5832:5894 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 5833:5895 */         if (producto == null) {
/* 5834:5896 */           throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.CARGA_NO_EXISTE_PRODUCTO", new String[] { Integer.toString(filaActual), Integer.toString(columnaErronea), codigoProducto });
/* 5835:     */         }
/* 5836:5900 */         Lote lote = null;
/* 5837:5901 */         if (producto.isIndicadorLote())
/* 5838:     */         {
/* 5839:5902 */           String codigoLote = fila[(columnaErronea = 4)].getStringCellValue().trim();
/* 5840:5903 */           lote = (Lote)hmLotePorProducto.get(codigoLote + "~" + producto.getCodigo());
/* 5841:5904 */           if (lote == null) {
/* 5842:5906 */             throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.CARGA_NO_EXISTE_LOTE", new String[] { Integer.toString(filaActual), Integer.toString(columnaErronea), codigoLote, producto.getCodigo() });
/* 5843:     */           }
/* 5844:     */         }
/* 5845:5909 */         String codigoBodega = fila[(columnaErronea = 2)].getStringCellValue().trim();
/* 5846:5910 */         Bodega bodega = (Bodega)((HashMap)hmBodega).get(codigoBodega);
/* 5847:5911 */         if (bodega == null) {
/* 5848:5912 */           throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.CARGA_NO_EXISTE_BODEGA", new String[] { Integer.toString(filaActual), Integer.toString(columnaErronea), codigoProducto });
/* 5849:     */         }
/* 5850:5915 */         BigDecimal cantidadRequerida = new BigDecimal(fila[(columnaErronea = 3)].getNumericCellValue());
/* 5851:5916 */         String nota = fila[(columnaErronea = 5)] != null ? fila[(columnaErronea = 5)].getStringCellValue().trim() : "";
/* 5852:     */         
/* 5853:5918 */         String codigoDestinoCosto = fila[(columnaErronea = 6)].getStringCellValue().trim();
/* 5854:5919 */         DestinoCosto destinoCosto = null;
/* 5855:5920 */         if ((codigoDestinoCosto != null) && (!codigoDestinoCosto.isEmpty()))
/* 5856:     */         {
/* 5857:5921 */           destinoCosto = (DestinoCosto)hmDestinoCosto.get(codigoDestinoCosto);
/* 5858:5922 */           if (destinoCosto == null) {
/* 5859:5924 */             throw new AS2Exception("com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl.CARGA_NO_EXISTE_DESTINO_COSTO", new String[] { Integer.toString(filaActual), Integer.toString(columnaErronea), codigoProducto });
/* 5860:     */           }
/* 5861:     */         }
/* 5862:5928 */         DetalleOrdenSalidaMaterial detalleOrden = new DetalleOrdenSalidaMaterial();
/* 5863:5929 */         detalleOrden.setIdOrganizacion(ordenSalidaMaterial.getIdOrganizacion());
/* 5864:5930 */         detalleOrden.setIdSucursal(ordenSalidaMaterial.getSucursal().getIdSucursal());
/* 5865:5931 */         detalleOrden.setProducto(producto);
/* 5866:5932 */         detalleOrden.setIndicadorConsumoDirecto(producto.isIndicadorConsumoDirecto());
/* 5867:5933 */         detalleOrden.setBodega(bodega);
/* 5868:5934 */         detalleOrden.setDestinoCosto(destinoCosto);
/* 5869:5935 */         detalleOrden.setOrdenSalidaMaterial(ordenSalidaMaterial);
/* 5870:5936 */         detalleOrden.setCantidad(cantidadRequerida.setScale(4, RoundingMode.HALF_UP));
/* 5871:5937 */         detalleOrden.setUnidad(producto.getUnidad());
/* 5872:5938 */         detalleOrden.setLote(producto.isIndicadorLote() ? lote : null);
/* 5873:5939 */         detalleOrden.setDescripcion(nota);
/* 5874:5940 */         ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().add(detalleOrden);
/* 5875:5941 */         listaDetalleOrdenSalidaMateriales.add(detalleOrden);
/* 5876:     */       }
/* 5877:     */     }
/* 5878:     */     catch (AS2Exception e)
/* 5879:     */     {
/* 5880:5945 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 5881:5946 */       this.context.setRollbackOnly();
/* 5882:5947 */       throw e;
/* 5883:     */     }
/* 5884:     */     catch (IllegalArgumentException e)
/* 5885:     */     {
/* 5886:5949 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 5887:5950 */       this.context.setRollbackOnly();
/* 5888:     */       
/* 5889:5952 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 5890:     */     }
/* 5891:     */     catch (IllegalStateException e)
/* 5892:     */     {
/* 5893:5955 */       LOG.info("Error al migrar partidas presupuestarias", e);
/* 5894:5956 */       this.context.setRollbackOnly();
/* 5895:     */       
/* 5896:5958 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 5897:     */     }
/* 5898:     */     catch (Exception e)
/* 5899:     */     {
/* 5900:5961 */       LOG.error("Error al migrar partidas presupuestarias", e);
/* 5901:5962 */       this.context.setRollbackOnly();
/* 5902:5963 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 5903:     */     }
/* 5904:     */   }
/* 5905:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.migracion.impl.ServicioMigracionImpl
 * JD-Core Version:    0.7.0.1
 */