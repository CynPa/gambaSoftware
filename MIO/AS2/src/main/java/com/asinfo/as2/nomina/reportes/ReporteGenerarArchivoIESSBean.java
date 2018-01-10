/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.dao.UtilidadDao;
/*   7:    */ import com.asinfo.as2.entities.DetalleUtilidad;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PagoRol;
/*  11:    */ import com.asinfo.as2.entities.Quincena;
/*  12:    */ import com.asinfo.as2.entities.Rubro;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.Utilidad;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*  16:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  17:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  18:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  19:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.util.RutaArchivo;
/*  22:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  23:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  24:    */ import java.io.PrintStream;
/*  25:    */ import java.math.BigDecimal;
/*  26:    */ import java.text.SimpleDateFormat;
/*  27:    */ import java.util.ArrayList;
/*  28:    */ import java.util.Calendar;
/*  29:    */ import java.util.Date;
/*  30:    */ import java.util.HashMap;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import javax.ejb.EJB;
/*  34:    */ import javax.faces.bean.ManagedBean;
/*  35:    */ import javax.faces.bean.ViewScoped;
/*  36:    */ import javax.faces.model.SelectItem;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ import org.primefaces.model.StreamedContent;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class ReporteGenerarArchivoIESSBean
/*  43:    */   extends PageControllerAS2
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 7833317272726429414L;
/*  46:    */   @EJB
/*  47:    */   private ServicioPagoRol servicioPagoRol;
/*  48:    */   @EJB
/*  49:    */   private ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  50:    */   @EJB
/*  51:    */   private ServicioEmpleado servicioEmpleado;
/*  52:    */   @EJB
/*  53:    */   private ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  54:    */   @EJB
/*  55:    */   private UtilidadDao utilidadDao;
/*  56:    */   @EJB
/*  57:    */   private ServicioSucursal servicioSucursal;
/*  58:    */   private List<SelectItem> listaPagoRol;
/*  59: 60 */   protected static final Logger LOG = Logger.getLogger("com.asinfo.as2");
/*  60:    */   private static final String TIPO_CONTENIDO = "application/txt";
/*  61:    */   private List<SelectItem> listaTipoArchivo;
/*  62:    */   private int tipoArchivo;
/*  63:    */   private StreamedContent file;
/*  64:    */   private PagoRol pagoRol;
/*  65:    */   private int idPagoRol;
/*  66: 70 */   Rubro rubroDecimoTercero = null;
/*  67: 71 */   Rubro rubroDecimoCuarto = null;
/*  68:    */   private boolean tipoVariacion;
/*  69: 74 */   private Date desde = new Date();
/*  70: 75 */   private Date hasta = new Date();
/*  71:    */   private Sucursal sucursal;
/*  72:    */   private List<Sucursal> listaSucursal;
/*  73:    */   
/*  74:    */   public PagoRol getPagoRol()
/*  75:    */   {
/*  76: 81 */     if (this.pagoRol == null) {
/*  77: 82 */       this.pagoRol = new PagoRol();
/*  78:    */     }
/*  79: 84 */     return this.pagoRol;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setPagoRol(PagoRol pagoRol)
/*  83:    */   {
/*  84: 88 */     this.pagoRol = pagoRol;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<SelectItem> getListaPagoRol()
/*  88:    */   {
/*  89: 97 */     List<PagoRol> lista = new ArrayList();
/*  90: 98 */     Map<String, String> filters = new HashMap();
/*  91: 99 */     filters.put("indicadorFiniquito", "false");
/*  92:100 */     List<Rubro> rubroDC = this.servicioPagoRol.getRubroTipo(TipoRubroEnum.DECIMO_CUARTO, AppUtil.getOrganizacion().getId());
/*  93:101 */     List<Rubro> rubroDT = this.servicioPagoRol.getRubroDecimoTercero(AppUtil.getOrganizacion().getId());
/*  94:    */     
/*  95:103 */     lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/*  96:    */     
/*  97:105 */     this.listaPagoRol = new ArrayList();
/*  98:106 */     Map<Integer, SelectItem> mapaPagoRol = new HashMap();
/*  99:107 */     for (PagoRol pagoRol : lista) {
/* 100:108 */       if (this.tipoArchivo == 5)
/* 101:    */       {
/* 102:109 */         if (!rubroDC.isEmpty()) {
/* 103:110 */           for (int i = 0; i < rubroDC.size(); i++) {
/* 104:111 */             if ((((Rubro)rubroDC.get(i)).isIndicadorDecimoCuartoAcumulado()) && (((Rubro)rubroDC.get(i)).getFormula().equals("e")) && 
/* 105:112 */               (pagoRol.getQuincena().equals(((Rubro)rubroDC.get(i)).getQuincena())))
/* 106:    */             {
/* 107:113 */               this.rubroDecimoCuarto = ((Rubro)rubroDC.get(i));
/* 108:115 */               if (pagoRol.getQuincena().getIdQuincena() == ((Rubro)rubroDC.get(i)).getQuincena().getIdQuincena())
/* 109:    */               {
/* 110:120 */                 String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 111:121 */                 SelectItem item = new SelectItem(Integer.valueOf(pagoRol.getIdPagoRol()), label);
/* 112:122 */                 mapaPagoRol.put(Integer.valueOf(pagoRol.getIdPagoRol()), item);
/* 113:    */               }
/* 114:    */             }
/* 115:    */           }
/* 116:    */         }
/* 117:    */       }
/* 118:128 */       else if (this.tipoArchivo == 4)
/* 119:    */       {
/* 120:129 */         if (!rubroDT.isEmpty())
/* 121:    */         {
/* 122:130 */           this.rubroDecimoTercero = ((Rubro)rubroDT.get(0));
/* 123:131 */           for (int i = 0; i < rubroDT.size(); i++) {
/* 124:132 */             if (pagoRol.getQuincena().getIdQuincena() == ((Rubro)rubroDT.get(i)).getQuincena().getIdQuincena())
/* 125:    */             {
/* 126:138 */               String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 127:139 */               SelectItem item = new SelectItem(Integer.valueOf(pagoRol.getIdPagoRol()), label);
/* 128:140 */               mapaPagoRol.put(Integer.valueOf(pagoRol.getIdPagoRol()), item);
/* 129:    */             }
/* 130:    */           }
/* 131:    */         }
/* 132:    */       }
/* 133:    */       else
/* 134:    */       {
/* 135:150 */         String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 136:151 */         SelectItem item = new SelectItem(Integer.valueOf(pagoRol.getIdPagoRol()), label);
/* 137:152 */         mapaPagoRol.put(Integer.valueOf(pagoRol.getIdPagoRol()), item);
/* 138:    */       }
/* 139:    */     }
/* 140:155 */     this.listaPagoRol = new ArrayList(mapaPagoRol.values());
/* 141:    */     
/* 142:157 */     return this.listaPagoRol;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void actualizarListaPagoRol()
/* 146:    */   {
/* 147:161 */     getListaPagoRol();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void generarArchivo()
/* 151:    */   {
/* 152:170 */     switch (this.tipoArchivo)
/* 153:    */     {
/* 154:    */     case 1: 
/* 155:172 */       generarArchivoVariacionSueldo();
/* 156:173 */       break;
/* 157:    */     case 2: 
/* 158:175 */       generarArchivoEntrada();
/* 159:176 */       break;
/* 160:    */     case 3: 
/* 161:178 */       generarArchivoSalida();
/* 162:179 */       break;
/* 163:    */     case 4: 
/* 164:181 */       generarArchivoDecimotercero();
/* 165:182 */       break;
/* 166:    */     case 5: 
/* 167:184 */       generarArchivoDecimocuarto();
/* 168:185 */       break;
/* 169:    */     case 6: 
/* 170:187 */       generarArchivoUtilidades();
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void generarArchivoVariacionSueldo()
/* 175:    */   {
/* 176:195 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/* 177:    */     
/* 178:197 */     int numeroColumnas = 0;
/* 179:    */     try
/* 180:    */     {
/* 181:200 */       String rutaArchivoTxt = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "VariacionesIESS");
/* 182:201 */       int mesPago = this.pagoRol.getMes();
/* 183:202 */       int anno = this.pagoRol.getAnio();
/* 184:203 */       String nombreArchivo = FuncionesUtiles.nombreMes(mesPago - 1) + " " + anno + ".txt";
/* 185:    */       
/* 186:205 */       List<Object[]> listaDatos = this.servicioPagoRol.getDatosArchivoVariacionesIESS(this.pagoRol, getSucursal());
/* 187:206 */       List<Object[]> lista = new ArrayList();
/* 188:    */       
/* 189:208 */       String anio = "" + this.pagoRol.getAnio();
/* 190:209 */       String mes = (this.pagoRol.getMes() < 10 ? "0" : "") + this.pagoRol.getMes();
/* 191:210 */       for (Object[] objects : listaDatos)
/* 192:    */       {
/* 193:211 */         String valorAportable = objects[1].toString();
/* 194:212 */         valorAportable = valorAportable.replace(',', '.');
/* 195:213 */         Object[] dato = new Object[1];
/* 196:214 */         dato[0] = (AppUtil.getOrganizacion().getIdentificacion() + ";0" + getSucursal().getCodigo() + ";" + anio + ";" + mes + ";INS;" + objects[0] + ";" + valorAportable + ";O");
/* 197:    */         
/* 198:    */ 
/* 199:217 */         lista.add(dato);
/* 200:    */       }
/* 201:220 */       numeroColumnas = 1;
/* 202:    */       
/* 203:222 */       FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, lista, numeroColumnas, "");
/* 204:    */       
/* 205:224 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "application/txt", nombreArchivo);
/* 206:    */       
/* 207:    */ 
/* 208:227 */       LOG.info("Archivo variación de sueldo generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivo);
/* 209:    */     }
/* 210:    */     catch (Exception e)
/* 211:    */     {
/* 212:230 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 213:231 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void generarArchivoEntrada()
/* 218:    */   {
/* 219:238 */     if ((this.desde == null) || (this.hasta == null)) {
/* 220:239 */       return;
/* 221:    */     }
/* 222:    */     try
/* 223:    */     {
/* 224:243 */       int idRubro = ParametrosSistema.getRubroSalarioUnificado(AppUtil.getOrganizacion().getId()).intValue();
/* 225:    */       
/* 226:245 */       List<Object[]> listaDatos = this.servicioEmpleado.generarDatosArchivoEntradaIESS(idRubro, this.desde, this.hasta);
/* 227:    */       
/* 228:247 */       int numeroColumnas = 0;
/* 229:    */       
/* 230:249 */       String rutaArchivoTxt = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "VariacionesIESS");
/* 231:    */       
/* 232:251 */       String fechaDesde = agregarCero(this.desde.getDate()) + "-" + agregarCero(this.desde.getMonth() + 1) + "-" + Integer.toString(this.desde.getYear() + 1900);
/* 233:    */       
/* 234:253 */       String fechaHasta = agregarCero(this.hasta.getDate()) + "-" + agregarCero(this.hasta.getMonth() + 1) + "-" + Integer.toString(this.hasta.getYear() + 1900);
/* 235:254 */       String nombreArchivo = "Entrada " + fechaDesde + " hasta " + fechaHasta + ".txt";
/* 236:    */       
/* 237:256 */       List<Object[]> lista = new ArrayList();
/* 238:258 */       for (Object[] objects : listaDatos)
/* 239:    */       {
/* 240:259 */         String ruc = AppUtil.getOrganizacion().getIdentificacion();
/* 241:260 */         String codigoSuc = (String)objects[0];
/* 242:261 */         if (codigoSuc.length() < 4) {
/* 243:262 */           for (int i = 0; i < 4 - codigoSuc.length(); i++) {
/* 244:263 */             codigoSuc = "0" + codigoSuc;
/* 245:    */           }
/* 246:    */         }
/* 247:266 */         Date fechaActual = new Date();
/* 248:267 */         String annoActual = Integer.toString(fechaActual.getYear() + 1900);
/* 249:268 */         String mesActual = agregarCero(fechaActual.getMonth() + 1);
/* 250:269 */         String cedula = (String)objects[1];
/* 251:270 */         Date fechaIngresoDato = (Date)objects[2];
/* 252:271 */         Date fechaIngresoSistemaDato = new Date();
/* 253:    */         
/* 254:273 */         String fechaIngreso = Integer.toString(fechaIngresoDato.getYear() + 1900) + agregarCero(fechaIngresoDato.getMonth() + 1) + agregarCero(fechaIngresoDato.getDate());
/* 255:    */         
/* 256:275 */         String fechaIngresoSistema = Integer.toString(fechaIngresoSistemaDato.getYear() + 1900) + agregarCero(fechaIngresoSistemaDato.getMonth() + 1) + agregarCero(fechaIngresoSistemaDato.getDate());
/* 257:276 */         String jornada = "1";
/* 258:277 */         String codSegSocial = "R";
/* 259:278 */         String codTipoEmpleador = "2";
/* 260:279 */         String relTrabajo = "06";
/* 261:280 */         String denomCargo = (String)objects[3];
/* 262:281 */         String codActSectorial = (String)objects[5];
/* 263:282 */         if (objects[5] == null) {
/* 264:283 */           codActSectorial = "";
/* 265:    */         }
/* 266:285 */         String sueldo = ((BigDecimal)objects[4]).toString();
/* 267:286 */         String origenPago = "P";
/* 268:    */         
/* 269:288 */         Object[] dato = new Object[1];
/* 270:289 */         dato[0] = (ruc + ";" + codigoSuc + ";" + annoActual + ";" + mesActual + ";" + "ENT" + ";" + cedula + ";" + fechaIngreso + ";" + fechaIngresoSistema + ";" + jornada + ";" + codSegSocial + ";" + codTipoEmpleador + ";" + relTrabajo + ";" + denomCargo + ";" + codActSectorial + ";" + sueldo + ";" + origenPago);
/* 271:    */         
/* 272:    */ 
/* 273:    */ 
/* 274:293 */         lista.add(dato);
/* 275:    */       }
/* 276:296 */       numeroColumnas = 1;
/* 277:    */       
/* 278:298 */       FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, lista, numeroColumnas, "\t");
/* 279:    */       
/* 280:300 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "application/txt", nombreArchivo);
/* 281:    */       
/* 282:    */ 
/* 283:    */ 
/* 284:304 */       LOG.info("Archivo de entrada generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivo);
/* 285:    */     }
/* 286:    */     catch (Exception e)
/* 287:    */     {
/* 288:308 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 289:309 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 290:    */     }
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void generarArchivoSalida()
/* 294:    */   {
/* 295:319 */     if ((this.desde == null) || (this.hasta == null)) {
/* 296:320 */       return;
/* 297:    */     }
/* 298:    */     try
/* 299:    */     {
/* 300:325 */       List<Object[]> listaDatos = this.servicioEmpleado.generarDatosArchivoSalidaIESS(this.desde, this.hasta);
/* 301:    */       
/* 302:327 */       int numeroColumnas = 0;
/* 303:    */       
/* 304:329 */       String rutaArchivoTxt = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "VariacionesIESS");
/* 305:    */       
/* 306:331 */       String fechaDesde = agregarCero(this.desde.getDate()) + "-" + agregarCero(this.desde.getMonth() + 1) + "-" + Integer.toString(this.desde.getYear() + 1900);
/* 307:    */       
/* 308:333 */       String fechaHasta = agregarCero(this.hasta.getDate()) + "-" + agregarCero(this.hasta.getMonth() + 1) + "-" + Integer.toString(this.hasta.getYear() + 1900);
/* 309:334 */       String nombreArchivo = "Salida " + fechaDesde + " hasta " + fechaHasta + ".txt";
/* 310:    */       
/* 311:336 */       List<Object[]> lista = new ArrayList();
/* 312:338 */       for (Object[] objects : listaDatos)
/* 313:    */       {
/* 314:339 */         String ruc = AppUtil.getOrganizacion().getIdentificacion();
/* 315:340 */         String codigoSuc = (String)objects[0];
/* 316:341 */         if (codigoSuc.length() < 4) {
/* 317:342 */           for (int i = 0; i < 4 - codigoSuc.length(); i++) {
/* 318:343 */             codigoSuc = "0" + codigoSuc;
/* 319:    */           }
/* 320:    */         }
/* 321:346 */         Date fechaActual = new Date();
/* 322:347 */         String annoActual = Integer.toString(fechaActual.getYear() + 1900);
/* 323:348 */         String mesActual = agregarCero(fechaActual.getMonth() + 1);
/* 324:349 */         String cedula = (String)objects[1];
/* 325:350 */         Date fechaSalidaDato = (Date)objects[2];
/* 326:    */         
/* 327:352 */         String fechaSalida = Integer.toString(fechaSalidaDato.getYear() + 1900) + agregarCero(fechaSalidaDato.getMonth() + 1) + agregarCero(fechaSalidaDato.getDate());
/* 328:353 */         String causa = (String)objects[3];
/* 329:354 */         String fechaFallecimiento = "00000000";
/* 330:355 */         Object[] dato = new Object[1];
/* 331:356 */         dato[0] = (ruc + ";" + codigoSuc + ";" + annoActual + ";" + mesActual + ";" + "SAL" + ";" + cedula + ";" + fechaSalida + ";" + causa + ";" + fechaFallecimiento);
/* 332:    */         
/* 333:    */ 
/* 334:359 */         lista.add(dato);
/* 335:    */       }
/* 336:362 */       numeroColumnas = 1;
/* 337:    */       
/* 338:364 */       FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, lista, numeroColumnas, "\t");
/* 339:    */       
/* 340:366 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "application/txt", nombreArchivo);
/* 341:    */       
/* 342:    */ 
/* 343:    */ 
/* 344:370 */       LOG.info("Archivo de salida generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivo);
/* 345:    */     }
/* 346:    */     catch (Exception e)
/* 347:    */     {
/* 348:374 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 349:375 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void generarArchivoDecimotercero()
/* 354:    */   {
/* 355:384 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/* 356:    */     try
/* 357:    */     {
/* 358:387 */       this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/* 359:    */       
/* 360:389 */       String rutaArchivoTxt = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "VariacionesIESS");
/* 361:390 */       int mesPago = this.pagoRol.getMes();
/* 362:391 */       int anio = this.pagoRol.getAnio();
/* 363:392 */       String nombreArchivo = "Pago Decimotercero " + FuncionesUtiles.nombreMes(mesPago - 1) + " " + anio + ".csv";
/* 364:    */       
/* 365:394 */       this.pagoRol = this.servicioPagoRol.cargarDetalle(this.pagoRol.getIdPagoRol());
/* 366:    */       
/* 367:396 */       Date fechaDesde = FuncionesUtiles.getFecha(27, this.rubroDecimoTercero.getMesCalculoDesde(), anio - 1);
/* 368:397 */       Date fechaHasta = FuncionesUtiles.getFecha(27, this.rubroDecimoTercero.getMesCalculoHasta() + 1, anio);
/* 369:    */       
/* 370:399 */       List<Object[]> listaDecimoTercero = this.servicioPagoRol.getArchivoDecimotercero(AppUtil.getOrganizacion().getId(), fechaDesde, fechaHasta, this.pagoRol);
/* 371:    */       
/* 372:    */ 
/* 373:402 */       Calendar fecha = Calendar.getInstance();
/* 374:403 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 375:404 */       for (Object[] objects : listaDecimoTercero)
/* 376:    */       {
/* 377:405 */         objects[5] = ((BigDecimal)objects[5]).multiply(new BigDecimal(12));
/* 378:406 */         objects[6] = Integer.valueOf(((BigDecimal)objects[6]).intValue());
/* 379:407 */         fecha.setTime((Date)objects[13]);
/* 380:408 */         objects[13] = (fecha.get(1) == 1900 ? "" : sdf.format(fecha.getTime()));
/* 381:409 */         fecha.setTime((Date)objects[14]);
/* 382:410 */         objects[14] = (fecha.get(1) == 1900 ? "" : sdf.format(fecha.getTime()));
/* 383:    */       }
/* 384:412 */       String[] encabezado = { "Cédula", "Nombres", "Apellidos", "Género", "Ocupación", "Total Ganado", "Días Laborados", "Tipo de Depósito", "Jornada Parcial Permanente", "Horas de la Jornada Parcial Permanente Semanal Estipulado en el Contrato", "Discapacidad", "Valor Retenido", "Pago DT mensualizado", "Fecha solicitud mensualización DT", "Fecha solicitud para eliminar pago mensual DT" };
/* 385:    */       
/* 386:    */ 
/* 387:415 */       List<Object[]> listaFinal = new ArrayList();
/* 388:416 */       listaFinal.add(encabezado);
/* 389:417 */       listaFinal.addAll(listaDecimoTercero);
/* 390:    */       
/* 391:419 */       FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaFinal, 15, ";");
/* 392:    */       
/* 393:421 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "application/txt", nombreArchivo);
/* 394:    */       
/* 395:    */ 
/* 396:424 */       LOG.info("Archivo décimo tercero generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivo);
/* 397:    */     }
/* 398:    */     catch (Exception e)
/* 399:    */     {
/* 400:428 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 401:429 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void generarArchivoDecimocuarto()
/* 406:    */   {
/* 407:435 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/* 408:    */     try
/* 409:    */     {
/* 410:439 */       String rutaArchivoTxt = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "VariacionesIESS");
/* 411:    */       
/* 412:441 */       int mesPago = this.pagoRol.getMes();
/* 413:442 */       int anio = this.pagoRol.getAnio();
/* 414:443 */       String nombreArchivo = "Pago Decimocuarto " + FuncionesUtiles.nombreMes(mesPago - 1) + " " + anio + ".csv";
/* 415:444 */       List<Object[]> lista = this.servicioPagoRol.getArchivoDecimocuarto(this.pagoRol, this.rubroDecimoCuarto, AppUtil.getOrganizacion().getId());
/* 416:    */       
/* 417:    */ 
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:452 */       String[] encabezado = { "Cédula", "Nombres", "Apellidos", "Género", "Ocupación", "Días Laborados", "Tipo de Depósito", "Jornada Parcial Permanente", "Horas de la Jornada Parcial Permanente Semanal Estipulado en el Contrato", "Discapacidad", "Fecha Jubilación Patronal", "Valor Retenido", "SOLO SI SU TRABAJADOR MENSUALIZA EL PAGO DE LA DECIMOCUARTA REMUNERACIóN PONGA UNA X" };
/* 424:    */       
/* 425:    */ 
/* 426:    */ 
/* 427:    */ 
/* 428:457 */       List<Object[]> listaFinal = new ArrayList();
/* 429:458 */       listaFinal.add(encabezado);
/* 430:459 */       listaFinal.addAll(lista);
/* 431:460 */       FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaFinal, 13, ";");
/* 432:    */       
/* 433:462 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "application/txt", nombreArchivo);
/* 434:    */       
/* 435:    */ 
/* 436:    */ 
/* 437:466 */       LOG.info("Archivo décimo cuarto generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivo);
/* 438:    */     }
/* 439:    */     catch (Exception e)
/* 440:    */     {
/* 441:471 */       e.printStackTrace();
/* 442:472 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 443:473 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 444:    */     }
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void generarArchivoUtilidades()
/* 448:    */   {
/* 449:478 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/* 450:    */     try
/* 451:    */     {
/* 452:482 */       String rutaArchivoTxt = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "VariacionesIESS");
/* 453:    */       
/* 454:484 */       int mesPago = this.pagoRol.getMes();
/* 455:485 */       int anio = this.pagoRol.getAnio();
/* 456:486 */       String nombreArchivo = "Utilidades " + FuncionesUtiles.nombreMes(mesPago - 1) + " " + anio + ".csv";
/* 457:    */       
/* 458:488 */       Map<Integer, Object[]> mapaArchivo = new HashMap();
/* 459:    */       
/* 460:490 */       List<Object[]> listUtilidades = this.servicioPagoRol.getArchivoUtilidades(this.pagoRol.getIdPagoRol(), AppUtil.getOrganizacion().getId());
/* 461:    */       
/* 462:492 */       System.out.println("bean  " + listUtilidades.size());
/* 463:493 */       List<Object[]> listaEmpleadoSinRDEP = new ArrayList();
/* 464:494 */       for (Object[] objects : listUtilidades) {
/* 465:495 */         if (objects != null)
/* 466:    */         {
/* 467:496 */           Integer idEmpleado = (Integer)objects[24];
/* 468:497 */           BigDecimal diasTrabajados = (BigDecimal)objects[6];
/* 469:498 */           objects[6] = Integer.valueOf(diasTrabajados.intValue());
/* 470:499 */           String rucEmpresaComplementaria = (String)objects[11];
/* 471:500 */           if ((rucEmpresaComplementaria != null) && (!rucEmpresaComplementaria.isEmpty())) {
/* 472:501 */             objects[11] = ("#" + rucEmpresaComplementaria);
/* 473:    */           }
/* 474:505 */           if (idEmpleado.intValue() != 0) {
/* 475:506 */             mapaArchivo.put(idEmpleado, objects);
/* 476:    */           } else {
/* 477:509 */             listaEmpleadoSinRDEP.add(objects);
/* 478:    */           }
/* 479:    */         }
/* 480:    */       }
/* 481:515 */       int idTercero = this.servicioPagoRol.getRubroID("g");
/* 482:516 */       int idCuarto = this.servicioPagoRol.getRubroID("f");
/* 483:    */       
/* 484:518 */       int fReserva = -1;
/* 485:519 */       if (!this.servicioPagoRol.getRubroTipo(TipoRubroEnum.FONDOS_RESERVA, AppUtil.getOrganizacion().getId()).isEmpty()) {
/* 486:520 */         fReserva = ((Rubro)this.servicioPagoRol.getRubroTipo(TipoRubroEnum.FONDOS_RESERVA, AppUtil.getOrganizacion().getId()).get(0)).getIdRubro();
/* 487:    */       }
/* 488:527 */       List<Object[]> lista3 = this.servicioPagoRol.getValorPagoRubros(AppUtil.getOrganizacion().getIdOrganizacion(), anio - 1);
/* 489:    */       
/* 490:    */ 
/* 491:530 */       Utilidad utilidad = this.utilidadDao.obtenerUtilidadPorAnio(this.pagoRol.getIdOrganizacion(), anio - 2);
/* 492:531 */       if (utilidad != null)
/* 493:    */       {
/* 494:532 */         utilidad = this.utilidadDao.cargarDetalle(utilidad.getIdUtilidad());
/* 495:533 */         for (DetalleUtilidad detalle : utilidad.getListaDetalleUtilidad()) {
/* 496:534 */           if (detalle.getEmpleado() != null)
/* 497:    */           {
/* 498:535 */             Object[] fila = (Object[])mapaArchivo.get(Integer.valueOf(detalle.getEmpleado().getIdEmpleado()));
/* 499:536 */             if (fila != null) {
/* 500:537 */               fila[14] = detalle.getValor10().add(detalle.getValor5());
/* 501:    */             }
/* 502:    */           }
/* 503:    */         }
/* 504:    */       }
/* 505:543 */       for (Object[] objects : lista3)
/* 506:    */       {
/* 507:544 */         int idEMp = ((Integer)objects[0]).intValue();
/* 508:545 */         Object[] dato = (Object[])mapaArchivo.get(Integer.valueOf(idEMp));
/* 509:546 */         if (dato != null)
/* 510:    */         {
/* 511:548 */           dato[12] = objects[1];
/* 512:549 */           dato[13] = objects[2];
/* 513:550 */           dato[15] = objects[4];
/* 514:551 */           dato[16] = objects[3];
/* 515:    */         }
/* 516:    */       }
/* 517:560 */       String[] encabezado = { "Cédula", "Nombres", "Apellidos", "Género", "Ocupación", "Cargas Familiares", "Días Laborados", "Tipo de Pago Utilidad", "Jornada Parcial Permanente", "Horas de la Jornada Parcial Permanente Semanal Estipulado en el Contrato", "Discapacidad", "RUC de la Empresa Complementaria o de Unificación", "Décimo tercero valor proporcional al tiempo laborado en el año " + String.valueOf(anio - 1), "Décimo cuarto valor proporcional al tiempo laborado en el año " + String.valueOf(anio - 1), "Participación de Utilidades", "Salarios Percibidos", "Fondos de Reserva", "Comisiones", "Beneficios Adicionales en Efectivo", "Anticipo de Utilidad", "Retención Judicial", "impuesto de Retención", "Información MRL", "Tipo de Pago Salario Digno" };
/* 518:    */       
/* 519:    */ 
/* 520:    */ 
/* 521:564 */       List<Object[]> listaFinal = new ArrayList();
/* 522:565 */       listaFinal.add(encabezado);
/* 523:566 */       listaFinal.addAll(listaEmpleadoSinRDEP);
/* 524:567 */       listaFinal.addAll(mapaArchivo.values());
/* 525:    */       
/* 526:569 */       FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, listaFinal, 24, ";");
/* 527:    */       
/* 528:571 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "application/txt", nombreArchivo);
/* 529:    */       
/* 530:    */ 
/* 531:    */ 
/* 532:575 */       LOG.info("Archivo utilidades generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivo);
/* 533:    */     }
/* 534:    */     catch (Exception e)
/* 535:    */     {
/* 536:580 */       e = 
/* 537:    */       
/* 538:    */ 
/* 539:    */ 
/* 540:584 */         e;addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));LOG.error("ERROR AL CARGAR DATOS", e);
/* 541:    */     }
/* 542:    */     finally {}
/* 543:    */   }
/* 544:    */   
/* 545:    */   public StreamedContent getFile()
/* 546:    */   {
/* 547:588 */     return this.file;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public String editar()
/* 551:    */   {
/* 552:594 */     return null;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public String guardar()
/* 556:    */   {
/* 557:600 */     return null;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public String eliminar()
/* 561:    */   {
/* 562:606 */     return null;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public String limpiar()
/* 566:    */   {
/* 567:612 */     return null;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public String cargarDatos()
/* 571:    */   {
/* 572:618 */     return null;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public int getIdPagoRol()
/* 576:    */   {
/* 577:622 */     return this.idPagoRol;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public void setIdPagoRol(int idPagoRol)
/* 581:    */   {
/* 582:626 */     this.idPagoRol = idPagoRol;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public List<SelectItem> getListaTipoArchivo()
/* 586:    */   {
/* 587:630 */     if (this.listaTipoArchivo == null)
/* 588:    */     {
/* 589:631 */       this.listaTipoArchivo = new ArrayList();
/* 590:632 */       this.listaTipoArchivo.add(new SelectItem(Integer.valueOf(1), getLanguageController().getMensaje("msg_combo_iess_variacion")));
/* 591:633 */       this.listaTipoArchivo.add(new SelectItem(Integer.valueOf(2), getLanguageController().getMensaje("msg_combo_iess_entrada")));
/* 592:634 */       this.listaTipoArchivo.add(new SelectItem(Integer.valueOf(3), getLanguageController().getMensaje("msg_combo_iess_salida")));
/* 593:635 */       this.listaTipoArchivo.add(new SelectItem(Integer.valueOf(4), getLanguageController().getMensaje("msg_combo_iess_decimotercero")));
/* 594:636 */       this.listaTipoArchivo.add(new SelectItem(Integer.valueOf(5), getLanguageController().getMensaje("msg_combo_iess_decimocuarto")));
/* 595:637 */       this.listaTipoArchivo.add(new SelectItem(Integer.valueOf(6), getLanguageController().getMensaje("msg_combo_iess_utilidades")));
/* 596:    */     }
/* 597:639 */     return this.listaTipoArchivo;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setListaTipoArchivo(List<SelectItem> listaTipoArchivo)
/* 601:    */   {
/* 602:644 */     this.listaTipoArchivo = listaTipoArchivo;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public int getTipoArchivo()
/* 606:    */   {
/* 607:649 */     return this.tipoArchivo;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public void setTipoArchivo(int tipoArchivo)
/* 611:    */   {
/* 612:653 */     this.tipoArchivo = tipoArchivo;
/* 613:654 */     getListaPagoRol();
/* 614:    */   }
/* 615:    */   
/* 616:    */   public boolean isTipoVariacion()
/* 617:    */   {
/* 618:658 */     return this.tipoVariacion;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public void setTipoVariacion(boolean tipoVariacion)
/* 622:    */   {
/* 623:662 */     this.tipoVariacion = tipoVariacion;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public Date getDesde()
/* 627:    */   {
/* 628:666 */     return this.desde;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public void setDesde(Date desde)
/* 632:    */   {
/* 633:670 */     this.desde = desde;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public Date getHasta()
/* 637:    */   {
/* 638:674 */     return this.hasta;
/* 639:    */   }
/* 640:    */   
/* 641:    */   public void setHasta(Date hasta)
/* 642:    */   {
/* 643:678 */     this.hasta = hasta;
/* 644:    */   }
/* 645:    */   
/* 646:    */   private String agregarCero(int numero)
/* 647:    */   {
/* 648:    */     String result;
/* 649:    */     String result;
/* 650:683 */     if (numero < 10) {
/* 651:684 */       result = "0" + numero;
/* 652:    */     } else {
/* 653:686 */       result = Integer.toString(numero);
/* 654:    */     }
/* 655:689 */     return result;
/* 656:    */   }
/* 657:    */   
/* 658:    */   public Sucursal getSucursal()
/* 659:    */   {
/* 660:696 */     return this.sucursal;
/* 661:    */   }
/* 662:    */   
/* 663:    */   public void setSucursal(Sucursal sucursal)
/* 664:    */   {
/* 665:704 */     this.sucursal = sucursal;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public List<Sucursal> getListaSucursal()
/* 669:    */   {
/* 670:713 */     if (this.listaSucursal == null) {
/* 671:714 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 672:    */     }
/* 673:716 */     return this.listaSucursal;
/* 674:    */   }
/* 675:    */   
/* 676:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 677:    */   {
/* 678:724 */     this.listaSucursal = listaSucursal;
/* 679:    */   }
/* 680:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteGenerarArchivoIESSBean
 * JD-Core Version:    0.7.0.1
 */