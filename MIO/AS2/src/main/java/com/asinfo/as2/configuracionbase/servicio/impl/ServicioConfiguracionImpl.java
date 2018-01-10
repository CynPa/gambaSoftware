/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCargarDatosInicialesDesdeXML;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioUpdate;
/*   6:    */ import com.asinfo.as2.dao.ConfiguracionDao;
/*   7:    */ import com.asinfo.as2.dao.OrganizacionDao;
/*   8:    */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*   9:    */ import com.asinfo.as2.entities.Configuracion;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  16:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.ArchivoUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  21:    */ import java.io.File;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.io.PrintStream;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.Iterator;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.annotation.Resource;
/*  31:    */ import javax.ejb.DependsOn;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.ejb.SessionContext;
/*  34:    */ import javax.ejb.Singleton;
/*  35:    */ import javax.ejb.Startup;
/*  36:    */ import javax.ejb.TransactionAttribute;
/*  37:    */ import javax.ejb.TransactionAttributeType;
/*  38:    */ import javax.ejb.TransactionManagement;
/*  39:    */ import javax.ejb.TransactionManagementType;
/*  40:    */ import javax.persistence.Query;
/*  41:    */ import org.apache.log4j.Logger;
/*  42:    */ 
/*  43:    */ @Startup
/*  44:    */ @Singleton
/*  45:    */ @DependsOn({"ServicioUpdateImpl"})
/*  46:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  47:    */ public class ServicioConfiguracionImpl
/*  48:    */   extends AbstractServicioAS2
/*  49:    */   implements ServicioConfiguracion
/*  50:    */ {
/*  51:    */   private static final long serialVersionUID = -7731772042193318837L;
/*  52:    */   @Resource
/*  53:    */   private SessionContext context;
/*  54:    */   @EJB
/*  55:    */   private ConfiguracionDao configuracionDao;
/*  56:    */   @EJB
/*  57:    */   private OrganizacionDao organizacionDao;
/*  58:    */   @EJB
/*  59:    */   private UsuarioDao usuarioDao;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioCargarDatosInicialesDesdeXML servicioCargarDatosInicialesDesdeXML;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioEnvioEmail servicioEnvioEmail;
/*  64:    */   @EJB
/*  65:    */   private ServicioUpdate servicioUpdate;
/*  66:    */   private static final String SQLServer2008Dialect = "org.hibernate.dialect.SQLServer2008Dialect";
/*  67:    */   private static final String SQLServerDialect = "org.hibernate.dialect.SQLServerDialect";
/*  68:    */   private static final String PostgreSQLDialect = "org.hibernate.dialect.PostgreSQLDialect";
/*  69: 89 */   private boolean instalacionNueva = false;
/*  70:    */   
/*  71:    */   @PostConstruct
/*  72:    */   public void cargarConfiguracion()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:102 */       ParametrosSistema.borrarParametros();
/*  77:    */       
/*  78:    */ 
/*  79:105 */       cargarDatosInicialesDesdeXML();
/*  80:    */       
/*  81:    */ 
/*  82:108 */       cargarVistasSQL();
/*  83:    */       
/*  84:    */ 
/*  85:111 */       actualizarArchivosCertificado();
/*  86:    */     }
/*  87:    */     catch (ExcepcionAS2 e)
/*  88:    */     {
/*  89:    */       List<Organizacion> listaOrganizacion;
/*  90:    */       Iterator localIterator1;
/*  91:    */       Organizacion organizacion;
/*  92:117 */       LOG.error("Error al cargar configuracion", e);
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:    */       int idOrganizacion;
/*  97:    */       List<Organizacion> listaOrganizacion;
/*  98:    */       Iterator localIterator2;
/*  99:    */       Organizacion organizacion;
/* 100:119 */       LOG.error("Error al cargar configuracion", e);
/* 101:    */     }
/* 102:    */     finally
/* 103:    */     {
/* 104:    */       int idOrganizacion;
/* 105:    */       List<Organizacion> listaOrganizacion;
/* 106:    */       Iterator localIterator3;
/* 107:    */       Organizacion organizacion;
/* 108:    */       int idOrganizacion;
/* 109:121 */       List<Organizacion> listaOrganizacion = this.organizacionDao.obtenerListaCombo("razonSocial", true, null);
/* 110:123 */       for (Organizacion organizacion : listaOrganizacion)
/* 111:    */       {
/* 112:124 */         cargarConfiguracion(Integer.valueOf(organizacion.getId()));
/* 113:    */         
/* 114:126 */         this.servicioEnvioEmail.cargarDatosSMTP(organizacion);
/* 115:    */       }
/* 116:128 */       int idOrganizacion = this.organizacionDao.getIdOrganizacionMinima();
/* 117:129 */       if (!((Boolean)ParametrosSistema.getParametro(idOrganizacion, Parametro.INDICADOR_ENCRIPTAR_PASSWORD)).booleanValue()) {
/* 118:130 */         encriptarClaves();
/* 119:    */       }
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 124:    */   private void cargarDatosInicialesDesdeXML()
/* 125:    */     throws ExcepcionAS2
/* 126:    */   {
/* 127:145 */     if (ServicioConfiguracion.AS2_HOME == null)
/* 128:    */     {
/* 129:146 */       System.out.println("La variable del entorno AS2_HOME no esta configurada");
/* 130:147 */       throw new ExcepcionAS2("La variable del entorno AS2_HOME no esta configurada");
/* 131:    */     }
/* 132:    */     try
/* 133:    */     {
/* 134:151 */       List<Organizacion> listaOrganizacion = new ArrayList();
/* 135:152 */       listaOrganizacion = this.organizacionDao.obtenerListaCombo(null, false, null);
/* 136:153 */       if (listaOrganizacion.isEmpty())
/* 137:    */       {
/* 138:154 */         this.servicioCargarDatosInicialesDesdeXML.cargarTipoIdentificacionDesdeXML();
/* 139:155 */         this.servicioCargarDatosInicialesDesdeXML.cargarOrganizacionDesdeXML();
/* 140:156 */         this.servicioCargarDatosInicialesDesdeXML.cargarRolDesdeXML();
/* 141:157 */         this.servicioCargarDatosInicialesDesdeXML.cargarTemaDesdeXML();
/* 142:158 */         this.servicioCargarDatosInicialesDesdeXML.cargarUsuarioDesdeXML();
/* 143:159 */         this.servicioCargarDatosInicialesDesdeXML.cargarTodosLosReportes();
/* 144:    */         
/* 145:161 */         this.servicioCargarDatosInicialesDesdeXML.cargarCodigoFormaPagoSRIDesdeXML();
/* 146:    */         
/* 147:    */ 
/* 148:164 */         crearCarpetasIniciales();
/* 149:165 */         this.instalacionNueva = true;
/* 150:    */       }
/* 151:168 */       this.servicioCargarDatosInicialesDesdeXML.cargarModuloDesdeXML(listaOrganizacion.isEmpty());
/* 152:169 */       this.servicioCargarDatosInicialesDesdeXML.cargarConfiguracionDesdeXML();
/* 153:170 */       this.servicioCargarDatosInicialesDesdeXML.cargarConceptosRetencionSRIDesdeXML();
/* 154:171 */       this.servicioCargarDatosInicialesDesdeXML.cargarIBPClasificacionDesdeXML();
/* 155:172 */       this.servicioCargarDatosInicialesDesdeXML.cargarIBPMarcaDesdeXML();
/* 156:173 */       this.servicioCargarDatosInicialesDesdeXML.cargarIBPCapacidadDesdeXML();
/* 157:174 */       this.servicioCargarDatosInicialesDesdeXML.cargarIBPUnidadDesdeXML();
/* 158:175 */       this.servicioCargarDatosInicialesDesdeXML.cargarEstadoChequeDesdeXML();
/* 159:176 */       this.servicioCargarDatosInicialesDesdeXML.cargarReporteador();
/* 160:178 */       if (listaOrganizacion.isEmpty())
/* 161:    */       {
/* 162:179 */         this.servicioCargarDatosInicialesDesdeXML.cargarIdiomaDesdeXML();
/* 163:180 */         this.servicioCargarDatosInicialesDesdeXML.cargarUsuarioSucursalDesdeXML();
/* 164:181 */         this.servicioCargarDatosInicialesDesdeXML.cargarAccionDesdeXML();
/* 165:182 */         this.servicioCargarDatosInicialesDesdeXML.cargarPermisoDesdeXML();
/* 166:183 */         this.servicioCargarDatosInicialesDesdeXML.cargarFormaPagoDesdeXML();
/* 167:184 */         this.servicioCargarDatosInicialesDesdeXML.cargarSecuenciaDesdeXML();
/* 168:185 */         this.servicioCargarDatosInicialesDesdeXML.cargarTipoAsientoDesdeXML();
/* 169:186 */         this.servicioCargarDatosInicialesDesdeXML.cargarTipoComprobanteSRIDesdeXML();
/* 170:187 */         this.servicioCargarDatosInicialesDesdeXML.cargarDocumentoDesdeXML();
/* 171:188 */         this.servicioCargarDatosInicialesDesdeXML.cargarMotivoAjusteInventarioDesdeXML();
/* 172:189 */         this.servicioCargarDatosInicialesDesdeXML.cargarCreditosTributariosSRIDesdeXML();
/* 173:    */         
/* 174:191 */         this.servicioCargarDatosInicialesDesdeXML.cargarCondicionPagoDesdeXML();
/* 175:192 */         this.servicioCargarDatosInicialesDesdeXML.cargarUnidadDesdeXML();
/* 176:193 */         this.servicioCargarDatosInicialesDesdeXML.cargarMonedaDesdeXML();
/* 177:194 */         this.servicioCargarDatosInicialesDesdeXML.cargarImpuestoDesdeXML();
/* 178:195 */         this.servicioCargarDatosInicialesDesdeXML.cargarCategoriaImpuestoDesdeXML();
/* 179:196 */         this.servicioCargarDatosInicialesDesdeXML.cargarTipoCuentaBancariaDesdeXML();
/* 180:197 */         this.servicioCargarDatosInicialesDesdeXML.cargarTipoBodegaDesdeXML();
/* 181:198 */         this.servicioCargarDatosInicialesDesdeXML.cargarBodegaDesdeXML();
/* 182:199 */         this.servicioCargarDatosInicialesDesdeXML.cargarListaPreciosDesdeXML();
/* 183:200 */         this.servicioCargarDatosInicialesDesdeXML.cargarZonaDesdeXML();
/* 184:201 */         this.servicioCargarDatosInicialesDesdeXML.cargarCategoriaEmpresa();
/* 185:202 */         this.servicioCargarDatosInicialesDesdeXML.cargarDocumentoVariableProcesoDesdeXML();
/* 186:203 */         this.servicioCargarDatosInicialesDesdeXML.cargarDocumentoContabilizacionDesdeXML();
/* 187:204 */         this.servicioCargarDatosInicialesDesdeXML.cargarBancosDesdeXML();
/* 188:205 */         this.servicioCargarDatosInicialesDesdeXML.cargarPaisProvinciaCiudadDesdeXML();
/* 189:206 */         this.servicioCargarDatosInicialesDesdeXML.cargarQuincenasDesdeXML();
/* 190:207 */         this.servicioCargarDatosInicialesDesdeXML.cargarRubrosDesdeXML();
/* 191:208 */         this.servicioCargarDatosInicialesDesdeXML.cargarFiltroProductoDesdeXML();
/* 192:209 */         this.servicioCargarDatosInicialesDesdeXML.cargarEstadoProcesoDesdeXML();
/* 193:210 */         this.servicioCargarDatosInicialesDesdeXML.cargarHorasExtraDesdeXML();
/* 194:211 */         this.servicioCargarDatosInicialesDesdeXML.cargarsEstadoCivilDesdeXML();
/* 195:212 */         this.servicioCargarDatosInicialesDesdeXML.actualizarVersionSistema();
/* 196:213 */         this.servicioCargarDatosInicialesDesdeXML.cargarOrigenIngresos();
/* 197:    */       }
/* 198:216 */       this.servicioCargarDatosInicialesDesdeXML.actualizarTipoIdentificacionDesdeXML();
/* 199:    */       
/* 200:218 */       this.servicioCargarDatosInicialesDesdeXML.actualizarConfiguracionConciliacionDesdeXML();
/* 201:    */       
/* 202:220 */       this.servicioCargarDatosInicialesDesdeXML.actualizarTareaProgramada();
/* 203:    */       
/* 204:222 */       this.servicioCargarDatosInicialesDesdeXML.cargarPlantillaXML();
/* 205:    */     }
/* 206:    */     catch (Exception e)
/* 207:    */     {
/* 208:224 */       System.out.println("Error al cargar los datos iniciales desde la carga de XML");
/* 209:225 */       LOG.info("Error al cargar datos iniciales en ServicioConfiguracionImpl en cargarDatosInicialesDesdeXML", e);
/* 210:226 */       this.context.setRollbackOnly();
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void cargarConfiguracion(Integer idOrganizacion)
/* 215:    */   {
/* 216:238 */     Map<Parametro, Object> parametrosOrganizacion = ParametrosSistema.getParametrosOrganizacion(idOrganizacion);
/* 217:239 */     parametrosOrganizacion.clear();
/* 218:    */     
/* 219:241 */     Map<String, String> filters = new HashMap();
/* 220:242 */     filters.put("idOrganizacion", idOrganizacion.toString());
/* 221:    */     
/* 222:244 */     List<Configuracion> listaParametros = this.configuracionDao.obtenerListaCombo("idOrganizacion", true, filters);
/* 223:247 */     for (Configuracion configuracion : listaParametros) {
/* 224:248 */       parametrosOrganizacion.put(configuracion.getParametro(), configuracion.getValor());
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void guardar(Configuracion entidad)
/* 229:    */   {
/* 230:259 */     this.configuracionDao.guardar(entidad);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void eliminar(Configuracion entidad)
/* 234:    */   {
/* 235:269 */     this.configuracionDao.eliminar(entidad);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Configuracion buscarPorId(Integer id)
/* 239:    */   {
/* 240:279 */     return (Configuracion)this.configuracionDao.buscarPorId(id);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public List<Configuracion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 244:    */   {
/* 245:290 */     return this.configuracionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public int contarPorCriterio(Map<String, String> filters)
/* 249:    */   {
/* 250:300 */     return this.configuracionDao.contarPorCriterio(filters);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void actualizarTipoAsiento(TipoAsiento tipoAsiento, int idOrganizacion)
/* 254:    */   {
/* 255:310 */     this.configuracionDao.actualizarTipoAsiento(tipoAsiento, idOrganizacion);
/* 256:    */   }
/* 257:    */   
/* 258:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 259:    */   public void encriptarClaves()
/* 260:    */   {
/* 261:316 */     List<Configuracion> listaConfiguracion = this.configuracionDao.obtenerListaConfiguracionPorParametro(Parametro.INDICADOR_ENCRIPTAR_PASSWORD);
/* 262:317 */     for (Iterator localIterator = listaConfiguracion.iterator(); localIterator.hasNext();)
/* 263:    */     {
/* 264:317 */       configuracion = (Configuracion)localIterator.next();
/* 265:318 */       configuracion.setValor("true");
/* 266:319 */       configuracion.setValorMostrar("true");
/* 267:320 */       this.configuracionDao.actualizar(configuracion);
/* 268:    */     }
/* 269:    */     Configuracion configuracion;
/* 270:322 */     Object listaUsuario = this.usuarioDao.obtenerListaUsuarios();
/* 271:323 */     for (EntidadUsuario usuario : (List)listaUsuario) {
/* 272:325 */       if ((usuario.getNombreUsuario().equals("asinfo")) && (usuario.getCodigo().equals("root")))
/* 273:    */       {
/* 274:326 */         usuario.setIndicadorSuperAdministrador(true);
/* 275:327 */         usuario.setIndicadorNuevo(false);
/* 276:328 */         this.usuarioDao.guardar(usuario);
/* 277:    */       }
/* 278:    */       else
/* 279:    */       {
/* 280:330 */         String claveActual = usuario.getClave();
/* 281:331 */         String claveEncriptada = AppUtil.encriptaEnMD5(claveActual);
/* 282:332 */         usuario.setClave(claveEncriptada);
/* 283:333 */         this.usuarioDao.guardar(usuario);
/* 284:    */       }
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<Configuracion> listaConfiguracionPorModulo(String nombre, int idOrganizacion)
/* 289:    */   {
/* 290:340 */     return this.configuracionDao.listaConfiguracionPorModulo(nombre, idOrganizacion);
/* 291:    */   }
/* 292:    */   
/* 293:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 294:    */   public void cargarVistasSQL()
/* 295:    */     throws ExcepcionAS2
/* 296:    */   {
/* 297:346 */     String nombreCarpeta = "vistas_sql";
/* 298:347 */     ServicioCargarDatosInicialesDesdeXMLBase scdid = new ServicioCargarDatosInicialesDesdeXMLBase();
/* 299:348 */     String path = scdid.getPathResoucesAS2(nombreCarpeta);
/* 300:    */     
/* 301:350 */     File folder = new File(path);
/* 302:351 */     File[] listOfFiles = folder.listFiles();
/* 303:    */     
/* 304:353 */     Boolean indicadorNombreComercialCXC = new Boolean(FuncionesUtiles.leerArchivoConfiguracion("NOMBRE_COMERCIAL_REPORTES_CXC"));
/* 305:    */     
/* 306:355 */     String hibernatedialectDB = this.configuracionDao.obtenerPropiedadesPersistence();
/* 307:357 */     for (int i = 0; i < listOfFiles.length; i++) {
/* 308:359 */       if (listOfFiles[i].isFile())
/* 309:    */       {
/* 310:361 */         String vistaSQL = ArchivoUtil.leerArchivoSQL(path + "/" + listOfFiles[i].getName());
/* 311:    */         
/* 312:363 */         String[] arraySQL = vistaSQL.split(" ");
/* 313:364 */         String nombreVista = arraySQL[4];
/* 314:366 */         if ((hibernatedialectDB.equals("org.hibernate.dialect.SQLServerDialect")) || (hibernatedialectDB.equals("org.hibernate.dialect.SQLServer2008Dialect")))
/* 315:    */         {
/* 316:368 */           String dropTable = "if exists(select * from sysobjects where name='" + nombreVista + "' and xtype='U') " + "drop table " + nombreVista;
/* 317:    */           
/* 318:370 */           String dropView = "if exists(select * from sysobjects where name='" + nombreVista + "' and xtype='V') " + "drop view " + nombreVista;
/* 319:    */           
/* 320:    */ 
/* 321:373 */           this.configuracionDao.ejecutarNativeQuery(dropTable).executeUpdate();
/* 322:374 */           this.configuracionDao.ejecutarNativeQuery(dropView).executeUpdate();
/* 323:    */           
/* 324:376 */           vistaSQL = vistaSQL.replace("or replace", " ");
/* 325:378 */           if (indicadorNombreComercialCXC.booleanValue()) {
/* 326:379 */             vistaSQL = vistaSQL.replace("em.nombre_fiscal", "em.nombre_comercial nombre_fiscal");
/* 327:    */           }
/* 328:381 */           vistaSQL = vistaSQL.replace("concat", " ");
/* 329:382 */           vistaSQL = vistaSQL.replace(",' ',", "+' '+");
/* 330:383 */           vistaSQL = vistaSQL.replace(",'/',", "+'/'+");
/* 331:384 */           vistaSQL = vistaSQL.replace(",'-',", "+'-'+");
/* 332:385 */           vistaSQL = vistaSQL.replace(",('", "+''+('");
/* 333:386 */           vistaSQL = vistaSQL.replace("trim", " ");
/* 334:387 */           vistaSQL = vistaSQL.replace("true", "1");
/* 335:388 */           vistaSQL = vistaSQL.replace("false", "0");
/* 336:    */         }
/* 337:389 */         else if (hibernatedialectDB.equals("org.hibernate.dialect.PostgreSQLDialect"))
/* 338:    */         {
/* 339:390 */           String dropViewP = "";
/* 340:391 */           String dropTableP = "";
/* 341:392 */           if (this.instalacionNueva)
/* 342:    */           {
/* 343:393 */             dropViewP = dropViewP + "drop view if exists " + nombreVista;
/* 344:394 */             dropTableP = dropTableP + "drop table if exists " + nombreVista;
/* 345:    */           }
/* 346:    */           else
/* 347:    */           {
/* 348:396 */             dropTableP = dropTableP + "drop view if exists " + nombreVista;
/* 349:397 */             dropViewP = dropViewP + "drop table if exists " + nombreVista;
/* 350:    */           }
/* 351:399 */           this.configuracionDao.ejecutarNativeQuery(dropTableP).executeUpdate();
/* 352:400 */           this.configuracionDao.ejecutarNativeQuery(dropViewP).executeUpdate();
/* 353:    */         }
/* 354:402 */         this.configuracionDao.ejecutarNativeQuery(vistaSQL).executeUpdate();
/* 355:    */       }
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 360:    */   public void cargarUpdate()
/* 361:    */     throws ExcepcionAS2
/* 362:    */   {
/* 363:410 */     String nombreCarpeta = "update_sql";
/* 364:411 */     ServicioCargarDatosInicialesDesdeXMLBase scdid = new ServicioCargarDatosInicialesDesdeXMLBase();
/* 365:412 */     String path = scdid.getPathResoucesAS2(nombreCarpeta);
/* 366:413 */     File folder = new File(path);
/* 367:414 */     File[] listOfFiles = folder.listFiles();
/* 368:    */     
/* 369:416 */     String hibernatedialectDB = this.configuracionDao.obtenerPropiedadesPersistence();
/* 370:418 */     for (int i = 0; i < listOfFiles.length; i++) {
/* 371:420 */       if (listOfFiles[i].isFile()) {
/* 372:422 */         if ((hibernatedialectDB.equals("org.hibernate.dialect.SQLServerDialect")) || (
/* 373:423 */           (hibernatedialectDB.equals("org.hibernate.dialect.SQLServer2008Dialect")) && (listOfFiles[i].getName().equals("posUpdateSQL.sql"))))
/* 374:    */         {
/* 375:424 */           String updateSQL = ArchivoUtil.leerArchivoSQL(path + "/" + "posUpdateSQL.sql");
/* 376:425 */           updateSQL = updateSQL.replace("\n", " ");
/* 377:426 */           this.configuracionDao.ejecutarNativeQuery(updateSQL != null ? updateSQL : " ").executeUpdate();
/* 378:    */         }
/* 379:428 */         else if ((hibernatedialectDB.equals("org.hibernate.dialect.PostgreSQLDialect")) && (listOfFiles[i].getName().equals("posUpdatePSQL.sql")))
/* 380:    */         {
/* 381:429 */           String updatePSQL = ArchivoUtil.leerArchivoSQL(path + "/" + "posUpdatePSQL.sql");
/* 382:430 */           String posUpdatePSQL = updatePSQL.replace("\n", " ");
/* 383:431 */           this.configuracionDao.ejecutarNativeQuery(posUpdatePSQL != null ? posUpdatePSQL : " ").executeUpdate();
/* 384:    */         }
/* 385:    */       }
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void crearCarpetasIniciales()
/* 390:    */     throws ExcepcionAS2, IOException
/* 391:    */   {
/* 392:441 */     ServicioCargarDatosInicialesDesdeXMLBase scdid = new ServicioCargarDatosInicialesDesdeXMLBase();
/* 393:442 */     String pathOrigen = scdid.getPathResoucesAS2("instalacion");
/* 394:443 */     File directorioOrigen = new File(pathOrigen);
/* 395:444 */     String pathDestino = AppUtil.obtenerDirectorioRaiz();
/* 396:445 */     File directorioDestino = new File(pathDestino);
/* 397:    */     
/* 398:447 */     System.out.println("===================== CREACION DE CARPETAS EN LA INSTALACION (Inicio) =====================");
/* 399:450 */     if (!directorioOrigen.exists()) {
/* 400:451 */       throw new ExcepcionAS2("No existe el directorio, " + pathOrigen);
/* 401:    */     }
/* 402:455 */     if (!directorioDestino.exists()) {
/* 403:456 */       ArchivoUtil.crearDirectorio(directorioDestino);
/* 404:    */     }
/* 405:460 */     ArchivoUtil.copiarDirectorio(directorioOrigen, directorioDestino);
/* 406:    */     
/* 407:462 */     System.out.println("===================== CREACION DE CARPETAS EN LA INSTALACION (Fin) =====================");
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void actualizarArchivosCertificado()
/* 411:    */     throws ExcepcionAS2, IOException
/* 412:    */   {
/* 413:471 */     String directorioCertificados = "sri/certificados";
/* 414:472 */     String nombreArchivo = "jssecacerts";
/* 415:473 */     ServicioCargarDatosInicialesDesdeXMLBase scdid = new ServicioCargarDatosInicialesDesdeXMLBase();
/* 416:474 */     String pathArchivoOrigen = scdid.getPathResoucesAS2("instalacion") + directorioCertificados + "/" + nombreArchivo;
/* 417:475 */     String pathArchivoDestino = AppUtil.obtenerDirectorioRaiz() + "/" + directorioCertificados + "/" + nombreArchivo;
/* 418:    */     
/* 419:477 */     System.out.println("===================== Copia del archivo jssecacerts (Inicio) =====================");
/* 420:479 */     if (ArchivoUtil.esArchivoNuevo(pathArchivoOrigen, pathArchivoDestino)) {
/* 421:480 */       ArchivoUtil.sobreescribirArchivo(pathArchivoOrigen, pathArchivoDestino);
/* 422:    */     }
/* 423:483 */     System.out.println("===================== Copia del archivo jssecacerts (Fin) =====================");
/* 424:    */   }
/* 425:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioConfiguracionImpl
 * JD-Core Version:    0.7.0.1
 */