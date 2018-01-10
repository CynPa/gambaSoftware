/*   1:    */ package com.asinfo.as2.nomina.reportes.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.PrestamoDao;
/*   4:    */ import com.asinfo.as2.dao.ReporteNominaDao;
/*   5:    */ import com.asinfo.as2.entities.Banco;
/*   6:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.Departamento;
/*  10:    */ import com.asinfo.as2.entities.DetallePagoCash;
/*  11:    */ import com.asinfo.as2.entities.DimensionContable;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Empleado;
/*  14:    */ import com.asinfo.as2.entities.FormaPago;
/*  15:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.PagoCash;
/*  18:    */ import com.asinfo.as2.entities.PagoRol;
/*  19:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  20:    */ import com.asinfo.as2.entities.Rubro;
/*  21:    */ import com.asinfo.as2.entities.Sucursal;
/*  22:    */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*  23:    */ import com.asinfo.as2.entities.TipoPermisoEmpleado;
/*  24:    */ import com.asinfo.as2.entities.TipoPrestamo;
/*  25:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  26:    */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*  27:    */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*  28:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  29:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  30:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  31:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  32:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPrestamo;
/*  33:    */ import com.asinfo.as2.nomina.reportes.ReporteSobreEmpleadoBean;
/*  34:    */ import com.asinfo.as2.nomina.reportes.ServicioReporteNomina;
/*  35:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  36:    */ import com.asinfo.as2.util.AppUtil;
/*  37:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  38:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  39:    */ import java.math.BigDecimal;
/*  40:    */ import java.math.RoundingMode;
/*  41:    */ import java.util.ArrayList;
/*  42:    */ import java.util.Date;
/*  43:    */ import java.util.HashMap;
/*  44:    */ import java.util.Iterator;
/*  45:    */ import java.util.List;
/*  46:    */ import java.util.Map;
/*  47:    */ import java.util.Map.Entry;
/*  48:    */ import java.util.Set;
/*  49:    */ import javax.ejb.EJB;
/*  50:    */ import javax.ejb.Stateless;
/*  51:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  52:    */ import org.primefaces.model.SortOrder;
/*  53:    */ 
/*  54:    */ @Stateless
/*  55:    */ public class ServicioReporteNominaImpl
/*  56:    */   implements ServicioReporteNomina
/*  57:    */ {
/*  58:    */   @EJB
/*  59:    */   private transient ReporteNominaDao reporteNominaDao;
/*  60:    */   @EJB
/*  61:    */   private transient PrestamoDao prestamoDao;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioPagoRol servicioPagoRol;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioPrestamo servicioPrestamo;
/*  66:    */   @EJB
/*  67:    */   private ServicioEnvioEmail servicioEnvioEmail;
/*  68:    */   @EJB
/*  69:    */   private ServicioReporteNomina servicioReporteNomina;
/*  70:    */   private static final String RAWTYPES = "rawtypes";
/*  71:    */   
/*  72:    */   public List<PagoRolEmpleadoRubro> obtenerListaPorPaginaPagoRolEmpleadoRubro(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  73:    */   {
/*  74: 98 */     return this.reporteNominaDao.obtenerListaPorPaginaPagoRolEmpleadoRubro(startIndex, pageSize, sortField, sortOrder, filters);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int contarPorCriterioPagoRolEmpleadoRubro(Map<String, String> filters)
/*  78:    */   {
/*  79:108 */     return this.reporteNominaDao.contarPorCriterioPagoRolEmpleadoRubro(filters);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List getListaIngresosEgresos(PagoRol pagoRol, FormaPagoEmpleado formaPagoEmpleado, Departamento departamento, Sucursal sucursal, int idCategoriaEmpresa, int idOrganizacion, boolean indicadorProvision, int indicadorAgrupado, boolean indicadorCodigos, DimensionContable dimensionContable)
/*  83:    */   {
/*  84:121 */     return this.reporteNominaDao.getListaIngresosEgresos(pagoRol, formaPagoEmpleado, departamento, sucursal, idCategoriaEmpresa, idOrganizacion, indicadorProvision, indicadorAgrupado, indicadorCodigos, dimensionContable);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List getListaFirmas(PagoRol pagoRol, FormaPagoEmpleado formaPagoEmpleado, Sucursal sucursal, int idOrganizacion)
/*  88:    */   {
/*  89:133 */     return this.reporteNominaDao.getListaFirmas(pagoRol, formaPagoEmpleado, sucursal, idOrganizacion);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List getListaTransacionBancos(PagoRol pagoRol, FormaPagoEmpleado formaPagoEmpleado, Sucursal sucursal, int idOrganizacion, boolean aprobados, boolean indicadorPagoCash)
/*  93:    */   {
/*  94:145 */     return this.reporteNominaDao.getListaCuentasBancariasEmpleado(pagoRol, formaPagoEmpleado, sucursal, idOrganizacion, aprobados, indicadorPagoCash);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<Object[]> getSobreEmpleado(PagoRol pagoRol, Empleado empleado, FormaPagoEmpleado formaPagoEmpleado, Sucursal sucursal, int idOrganizacion, Departamento departamento, boolean aprobados)
/*  98:    */   {
/*  99:156 */     return this.reporteNominaDao.getSobreEmpleado(pagoRol, empleado, formaPagoEmpleado, sucursal, idOrganizacion, departamento, aprobados);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List getVacacion(Departamento departamento, Empleado empleado, Sucursal sucursal, String tipoSelecionado, FormaPagoEmpleado formaPagoEmpleado, int idOrganizacion, boolean activos, Date fechaDesde, Date fechaHasta, boolean porPeriodo)
/* 103:    */   {
/* 104:169 */     return this.reporteNominaDao.getVacacion(departamento, empleado, sucursal, tipoSelecionado, formaPagoEmpleado, idOrganizacion, activos, fechaDesde, fechaHasta, porPeriodo);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getReporteTipoContratoPorId(int idTipoContrato)
/* 108:    */     throws ExcepcionAS2
/* 109:    */   {
/* 110:180 */     return this.reporteNominaDao.getReporteTipoContratoPorId(idTipoContrato);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<DetallePagoCash> getCashManagementEmpleado(PagoCash pagoCash, CuentaBancariaOrganizacion cuentaBancariaOrganizacion, FormaPago formaPago, Empleado empleado, boolean requiereAprobacion, TipoServicioCuentaBancariaEnum tipoServicioCuentaBancariaEnum, Banco banco)
/* 114:    */     throws ExcepcionAS2Nomina
/* 115:    */   {
/* 116:195 */     PagoRol pagoRol = this.servicioPagoRol.cargarDetalle(pagoCash.getPagoRol().getIdPagoRol());
/* 117:    */     
/* 118:197 */     CuentaBancariaOrganizacion cuentaBancaria = cuentaBancariaOrganizacion != null ? cuentaBancariaOrganizacion : pagoCash.getCuentaBancariaOrganizacion();
/* 119:    */     try
/* 120:    */     {
/* 121:200 */       String cuentaBancariaEmpresa = cuentaBancaria.getCuentaBancaria().getNumero();
/* 122:    */       
/* 123:202 */       String referencia = pagoRol.getDocumento().getNombre() + " " + FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + " " + pagoRol.getAnio();
/* 124:    */       
/* 125:204 */       List<Object[]> listaEmpleado = this.reporteNominaDao.getEmpleadosPagoCash(pagoRol, empleado, requiereAprobacion, tipoServicioCuentaBancariaEnum, banco);
/* 126:    */       
/* 127:206 */       List<Object[]> listaCuentaBancariaEmpleado = this.reporteNominaDao.getCuentaBancariaEmpleado();
/* 128:207 */       List<Object[]> listaDireccionTelefonoEmpleado = this.reporteNominaDao.getDireccionTelefonoEmpleado(AppUtil.getOrganizacion().getId());
/* 129:    */       
/* 130:209 */       Map<Integer, Object[]> hashMapCuentaBancariaEmpleado = new HashMap();
/* 131:210 */       HashMap<Integer, Object> hashMapDireccionEmpleado = new HashMap();
/* 132:211 */       HashMap<Integer, Object> hashMapTelefonoEmpleado = new HashMap();
/* 133:212 */       HashMap<Integer, Object> hashMapCiudadEmpleado = new HashMap();
/* 134:    */       
/* 135:214 */       int idEmp = 0;
/* 136:215 */       for (Object[] object : listaCuentaBancariaEmpleado)
/* 137:    */       {
/* 138:216 */         idEmp = Integer.parseInt(object[0].toString());
/* 139:217 */         hashMapCuentaBancariaEmpleado.put(Integer.valueOf(idEmp), object);
/* 140:    */       }
/* 141:220 */       for (??? = listaDireccionTelefonoEmpleado.iterator(); ???.hasNext();)
/* 142:    */       {
/* 143:220 */         object = (Object[])???.next();
/* 144:221 */         idEmp = Integer.parseInt(object[0].toString());
/* 145:222 */         hashMapDireccionEmpleado.put(Integer.valueOf(idEmp), object[1].toString());
/* 146:223 */         hashMapTelefonoEmpleado.put(Integer.valueOf(idEmp), object[2].toString());
/* 147:224 */         hashMapCiudadEmpleado.put(Integer.valueOf(idEmp), object[3].toString());
/* 148:    */       }
/* 149:226 */       List<DetallePagoCash> listaDetallePagoCash = pagoCash.getListaDetallePagoCash();
/* 150:    */       
/* 151:228 */       Object mapaDetallePagoCash = new HashMap();
/* 152:229 */       for (Object[] object = listaDetallePagoCash.iterator(); object.hasNext();)
/* 153:    */       {
/* 154:229 */         detallePC = (DetallePagoCash)object.next();
/* 155:    */         
/* 156:    */ 
/* 157:    */ 
/* 158:233 */         ((Map)mapaDetallePagoCash).put(Integer.valueOf(detallePC.getEmpleado().getIdEmpleado()), detallePC);
/* 159:    */       }
/* 160:    */       DetallePagoCash detallePC;
/* 161:237 */       for (Object[] object : listaEmpleado)
/* 162:    */       {
/* 163:238 */         int idEmpresa = Integer.parseInt(object[0].toString());
/* 164:239 */         BigDecimal valorAPagar = new BigDecimal(((BigDecimal)object[2]).intValue()).setScale(2, RoundingMode.HALF_UP);
/* 165:240 */         int idEmpleado = ((Integer)object[7]).intValue();
/* 166:241 */         String tipoCuentaBancaria = (String)object[8];
/* 167:242 */         String identificacionEmpleado = (String)object[1];
/* 168:243 */         String nombreBanco = (String)object[11];
/* 169:    */         
/* 170:245 */         DetallePagoCash detalle = (DetallePagoCash)((Map)mapaDetallePagoCash).get(Integer.valueOf(idEmpleado));
/* 171:246 */         if (detalle == null)
/* 172:    */         {
/* 173:247 */           detalle = new DetallePagoCash();
/* 174:248 */           Empleado empleadoDato = new Empleado();
/* 175:249 */           empleadoDato.setIdEmpleado(idEmpleado);
/* 176:250 */           empleadoDato.setApellidos((String)object[9]);
/* 177:251 */           empleadoDato.setNombres((String)object[10]);
/* 178:252 */           empleadoDato.setIndicadorPagoCash(true);
/* 179:253 */           detalle.setEmpleado(empleadoDato);
/* 180:254 */           listaDetallePagoCash.add(detalle);
/* 181:255 */           ((Map)mapaDetallePagoCash).put(Integer.valueOf(idEmpleado), detalle);
/* 182:    */         }
/* 183:258 */         detalle.setEliminado(false);
/* 184:259 */         detalle.setPagoCash(pagoCash);
/* 185:    */         
/* 186:261 */         Object[] cuentaEmpleado = (Object[])hashMapCuentaBancariaEmpleado.get(Integer.valueOf(idEmpresa));
/* 187:262 */         String numeroCuentaEmpleado = cuentaEmpleado[1].toString();
/* 188:263 */         String tipoServicioCuentaBancaria = cuentaEmpleado[3].toString();
/* 189:    */         
/* 190:265 */         detalle.setCuentaBancariaEmpresa(cuentaBancariaEmpresa.length() > 20 ? cuentaBancariaEmpresa.substring(0, 20) : cuentaBancariaEmpresa);
/* 191:266 */         detalle.setCuentaBancariaEmpleado(numeroCuentaEmpleado.length() > 20 ? numeroCuentaEmpleado.substring(0, 20) : numeroCuentaEmpleado);
/* 192:267 */         detalle.setCodigoBancoCuentaBancariaEmpleado(cuentaEmpleado[2].toString());
/* 193:268 */         detalle.setTipoServicioCuentaBancaria(tipoServicioCuentaBancaria);
/* 194:    */         
/* 195:270 */         String direccion = (String)hashMapDireccionEmpleado.get(Integer.valueOf(idEmpresa));
/* 196:271 */         direccion = direccion == null ? "" : direccion;
/* 197:272 */         String ciudad = (String)hashMapCiudadEmpleado.get(Integer.valueOf(idEmpresa));
/* 198:273 */         ciudad = ciudad == null ? "" : ciudad;
/* 199:274 */         String telefono = (String)hashMapTelefonoEmpleado.get(Integer.valueOf(idEmpresa));
/* 200:275 */         telefono = telefono == null ? "" : telefono;
/* 201:    */         
/* 202:277 */         detalle.setDireccionEmpleado(direccion.length() > 50 ? direccion.substring(0, 50) : direccion);
/* 203:278 */         detalle.setCiudadEmpleado(ciudad.length() > 50 ? ciudad.substring(0, 50) : ciudad);
/* 204:279 */         detalle.setTelefonoEmpleado(telefono.length() > 13 ? telefono.substring(0, 13) : telefono);
/* 205:    */         
/* 206:281 */         detalle.setReferenciaPagoEmpleado(referencia.length() > 200 ? referencia.substring(0, 200) : referencia);
/* 207:282 */         detalle.setValor(valorAPagar);
/* 208:283 */         detalle.setTipoCuentaBancaria(tipoCuentaBancaria);
/* 209:284 */         detalle.setIdentificacionEmpleado(identificacionEmpleado);
/* 210:285 */         detalle.setNombreBanco(nombreBanco);
/* 211:    */       }
/* 212:288 */       return listaDetallePagoCash;
/* 213:    */     }
/* 214:    */     catch (Exception e)
/* 215:    */     {
/* 216:290 */       throw new ExcepcionAS2Nomina(e);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List getReporteDiscapacidad(Sucursal sucursal, TipoDiscapacidad tipoDiscapacidad, Departamento departamento, Organizacion organizacion)
/* 221:    */   {
/* 222:304 */     return this.reporteNominaDao.getReporteDiscapacidad(sucursal, tipoDiscapacidad, departamento, organizacion);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List getReporteDiscapacidadCargaEmpleado(Sucursal sucursal, TipoDiscapacidad tipoDiscapacidad, Departamento departamento, Organizacion organizacion)
/* 226:    */   {
/* 227:317 */     return this.reporteNominaDao.getReporteDiscapacidadCargaEmpleado(sucursal, tipoDiscapacidad, departamento, organizacion);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<Object[]> getReporteRubroEmpleado(int idSucursal, int idEmpleado, int idDepartamento, List<PagoRol> listaPagoRol, List<Rubro> listaRubro, int movimiento, boolean indicadorRubroEmpleado, boolean indicadorRubroProvisionEmpleado, int idOrganizacion, CategoriaEmpresa categoriaEmpresa)
/* 231:    */   {
/* 232:328 */     return this.reporteNominaDao.getReporteRubroEmpleado(idSucursal, idEmpleado, idDepartamento, listaPagoRol, listaRubro, movimiento, indicadorRubroEmpleado, indicadorRubroProvisionEmpleado, idOrganizacion, categoriaEmpresa);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List getReporteUtilidadEmpleado(int idSucursal, int idEmpleado, int idUtilidad, int idDepartamento)
/* 236:    */   {
/* 237:335 */     return this.reporteNominaDao.getReporteUtilidadEmpleado(idSucursal, idEmpleado, idUtilidad, idDepartamento);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List getReportePrestamoEmpleado(int idOrganizacion, TipoPrestamo tipoPrestamo, Departamento departamento, Empleado empleado, Sucursal sucursal, boolean indicadorActivos, Date fechaDesde, Date fechaHasta)
/* 241:    */   {
/* 242:346 */     return this.prestamoDao.getReportePrestamoEmpleado(idOrganizacion, tipoPrestamo, departamento, empleado, sucursal, indicadorActivos, fechaDesde, fechaHasta);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public List<Object[]> getReportePermisos(Empleado empleado, int idSucursal, Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion, TipoPermisoEmpleado tipoPermisoEmpleado)
/* 246:    */   {
/* 247:354 */     return this.reporteNominaDao.getReportePermisos(empleado, idSucursal, departamento, fechaDesde, fechaHasta, idOrganizacion, tipoPermisoEmpleado);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List<Object[]> getReporteRubroMensual(int idSucursal, int idEmpleado, int idDepartamento, List<PagoRol> listaPagoRol, List<Rubro> listaRubro, int movimiento, boolean indicadorRubroEmpleado, boolean indicadorRubroProvisionEmpleado, int idOrganizacion, CategoriaEmpresa categoriaEmpresa)
/* 251:    */   {
/* 252:361 */     List<Object[]> result = this.reporteNominaDao.getReporteRubroMensual(idSucursal, idEmpleado, idDepartamento, listaPagoRol, listaRubro, movimiento, indicadorRubroEmpleado, indicadorRubroProvisionEmpleado, idOrganizacion, categoriaEmpresa);
/* 253:    */     
/* 254:363 */     return result;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public List<Object[]> getReporteRubroMensualDetallado(int idSucursal, int idEmpleado, int idDepartamento, List<PagoRol> listaPagoRol, List<Rubro> listaRubro, int movimiento, boolean indicadorRubroEmpleado, boolean indicadorRubroProvisionEmpleado, int idOrganizacion, CategoriaEmpresa categoriaEmpresa)
/* 258:    */   {
/* 259:370 */     List<Object[]> result = this.reporteNominaDao.getReporteRubroMensualDetallado(idSucursal, idEmpleado, idDepartamento, listaPagoRol, listaRubro, movimiento, indicadorRubroEmpleado, indicadorRubroProvisionEmpleado, idOrganizacion, categoriaEmpresa);
/* 260:    */     
/* 261:372 */     return result;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<Object[]> getReporteRubroAsignado(int idOrganizacion, Empleado empleado, boolean activo)
/* 265:    */   {
/* 266:378 */     return this.reporteNominaDao.getReporteRubroAsignado(idOrganizacion, empleado, activo);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public List<Object[]> getPagoRolEmpleadoRubro(HistoricoEmpleado historicoEmpleado, Empleado empleado, int idOrganizacion, int idSucursal)
/* 270:    */   {
/* 271:383 */     return this.reporteNominaDao.getPagoRolEmpleadoRubro(historicoEmpleado, empleado, idOrganizacion, idSucursal);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<Object[]> getReporteDotacionEmpleado(Date fechaDesde, Date fechaHasta, int empleado, int producto, int departamento, int idOrganizacion, int sucursal, boolean dotacionEmpleadoDetallado, boolean dotacionEmpleadoResumido, boolean reposicionDotacion)
/* 275:    */   {
/* 276:388 */     return this.reporteNominaDao.getReporteDotacionEmpleado(fechaDesde, fechaHasta, empleado, producto, departamento, idOrganizacion, sucursal, dotacionEmpleadoDetallado, dotacionEmpleadoResumido, reposicionDotacion);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void enviarEmailPagoRolEmpleados(Organizacion organizacion, String nombreReporte, PagoRol pagoRol, Sucursal sucursal, Empleado empleado, FormaPagoEmpleado formaPagoEmpleado, Departamento departamento)
/* 280:    */     throws AS2Exception
/* 281:    */   {
/* 282:396 */     List<Object[]> listaDatosReporte = this.servicioReporteNomina.getSobreEmpleado(pagoRol, empleado, formaPagoEmpleado, sucursal, organizacion
/* 283:397 */       .getIdOrganizacion(), departamento, false);
/* 284:398 */     HashMap<String, List<Object[]>> hmEmpleado = new HashMap();
/* 285:400 */     for (Object[] datosEmpleado : listaDatosReporte)
/* 286:    */     {
/* 287:401 */       List<Object[]> listaNueva = (List)hmEmpleado.get(datosEmpleado[1].toString());
/* 288:402 */       if (listaNueva == null)
/* 289:    */       {
/* 290:403 */         listaNueva = new ArrayList();
/* 291:404 */         hmEmpleado.put(datosEmpleado[1].toString(), listaNueva);
/* 292:    */       }
/* 293:406 */       listaNueva.add(datosEmpleado);
/* 294:    */     }
/* 295:409 */     Iterator it = hmEmpleado.entrySet().iterator();
/* 296:410 */     while (it.hasNext())
/* 297:    */     {
/* 298:411 */       Map.Entry e = (Map.Entry)it.next();
/* 299:412 */       String bodyText = "Pago Rol";
/* 300:413 */       String asunto = "Sobre Rol" + organizacion.getRazonSocial();
/* 301:414 */       List<Object[]> datosEmpleado = (List)e.getValue();
/* 302:415 */       String email = "";
/* 303:416 */       String identificacion = "";
/* 304:417 */       Iterator localIterator2 = datosEmpleado.iterator();
/* 305:417 */       if (localIterator2.hasNext())
/* 306:    */       {
/* 307:417 */         Object[] empleadoRol = (Object[])localIterator2.next();
/* 308:418 */         if (!empleadoRol[16].toString().isEmpty()) {
/* 309:419 */           email = empleadoRol[16].toString();
/* 310:    */         } else {
/* 311:421 */           email = empleadoRol[17].toString();
/* 312:    */         }
/* 313:424 */         identificacion = empleadoRol[0].toString();
/* 314:    */       }
/* 315:427 */       email = email.replace(" ", "");
/* 316:428 */       if (!email.isEmpty())
/* 317:    */       {
/* 318:429 */         Object ds = new QueryResultDataSource(datosEmpleado, ReporteSobreEmpleadoBean.fields);
/* 319:430 */         this.servicioEnvioEmail.enviarEmailComprobanteNoElectronicos(organizacion, sucursal.getIdSucursal(), email, asunto, bodyText, DocumentoBase.PAGO_ROL, identificacion, (JRDataSource)ds, nombreReporte, pagoRol
/* 320:431 */           .getUsuarioCreacion());
/* 321:    */       }
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   public String getReporteMotivoLlamadoAtencionPorId(int idMotivoLlamadoAtencion)
/* 326:    */     throws ExcepcionAS2
/* 327:    */   {
/* 328:438 */     return this.reporteNominaDao.getReporteMotivoLlamadoAtencionPorId(idMotivoLlamadoAtencion);
/* 329:    */   }
/* 330:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.impl.ServicioReporteNominaImpl
 * JD-Core Version:    0.7.0.1
 */