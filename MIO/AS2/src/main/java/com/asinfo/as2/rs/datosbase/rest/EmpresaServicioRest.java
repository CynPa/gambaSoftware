/*   1:    */ package com.asinfo.as2.rs.datosbase.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioProvincia;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   8:    */ import com.asinfo.as2.controller.LanguageController;
/*   9:    */ import com.asinfo.as2.dao.ClienteDao;
/*  10:    */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*  12:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  14:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*  15:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*  16:    */ import com.asinfo.as2.datosbase.servicio.ServicioTipoContacto;
/*  17:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  18:    */ import com.asinfo.as2.entities.Ciudad;
/*  19:    */ import com.asinfo.as2.entities.Cliente;
/*  20:    */ import com.asinfo.as2.entities.CondicionPago;
/*  21:    */ import com.asinfo.as2.entities.Contacto;
/*  22:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  23:    */ import com.asinfo.as2.entities.Empresa;
/*  24:    */ import com.asinfo.as2.entities.ListaDescuentos;
/*  25:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  26:    */ import com.asinfo.as2.entities.Pais;
/*  27:    */ import com.asinfo.as2.entities.Provincia;
/*  28:    */ import com.asinfo.as2.entities.TipoContacto;
/*  29:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  30:    */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*  31:    */ import com.asinfo.as2.entities.Ubicacion;
/*  32:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  33:    */ import com.asinfo.as2.rs.datosbase.dto.CargarDetalleRequestDto;
/*  34:    */ import com.asinfo.as2.rs.datosbase.dto.CatalogoGenericoResponseDto;
/*  35:    */ import com.asinfo.as2.rs.datosbase.dto.CiudadResponseDto;
/*  36:    */ import com.asinfo.as2.rs.datosbase.dto.ContactoResponseDto;
/*  37:    */ import com.asinfo.as2.rs.datosbase.dto.DireccionEmpresaResponseDto;
/*  38:    */ import com.asinfo.as2.rs.datosbase.dto.EmpresaResponseDto;
/*  39:    */ import com.asinfo.as2.rs.datosbase.dto.FiltroRequestDto;
/*  40:    */ import com.asinfo.as2.rs.datosbase.dto.FiltrosRequestDto;
/*  41:    */ import com.asinfo.as2.rs.datosbase.dto.ListaPaginadaResponseDto;
/*  42:    */ import com.asinfo.as2.rs.datosbase.dto.PaisResponseDto;
/*  43:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  44:    */ import com.asinfo.as2.rs.datosbase.dto.ProvinciaResponseDto;
/*  45:    */ import com.asinfo.as2.rs.datosbase.dto.TipoIdentificacionResponseDto;
/*  46:    */ import com.asinfo.as2.rs.datosbase.dto.UbicacionResponseDto;
/*  47:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  48:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  49:    */ import java.util.ArrayList;
/*  50:    */ import java.util.HashMap;
/*  51:    */ import java.util.List;
/*  52:    */ import java.util.Map;
/*  53:    */ import javax.ejb.EJB;
/*  54:    */ import javax.ws.rs.Consumes;
/*  55:    */ import javax.ws.rs.POST;
/*  56:    */ import javax.ws.rs.Path;
/*  57:    */ import javax.ws.rs.Produces;
/*  58:    */ 
/*  59:    */ @Path("/datosBase")
/*  60:    */ public class EmpresaServicioRest
/*  61:    */ {
/*  62:    */   @EJB
/*  63:    */   private ClienteDao clienteDao;
/*  64:    */   @EJB
/*  65:    */   private DireccionEmpresaDao direccionEmpresaDao;
/*  66:    */   @EJB
/*  67:    */   private ServicioEmpresa servicioEmpresa;
/*  68:    */   @EJB
/*  69:    */   private ServicioSucursal servicioSucursal;
/*  70:    */   @EJB
/*  71:    */   private ServicioGenerico<Ubicacion> servicioUbicacion;
/*  72:    */   @EJB
/*  73:    */   private ServicioCiudad servicioCiudad;
/*  74:    */   @EJB
/*  75:    */   private ServicioProvincia servicioProvincia;
/*  76:    */   @EJB
/*  77:    */   private ServicioPais servicioPais;
/*  78:    */   @EJB
/*  79:    */   private ServicioGenerico<DireccionEmpresa> servicioDireccionEmpresa;
/*  80:    */   @EJB
/*  81:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  82:    */   @EJB
/*  83:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  84:    */   @EJB
/*  85:    */   private ServicioFormaPago servicioFormaPago;
/*  86:    */   @EJB
/*  87:    */   private ServicioCondicionPago servicioCondicionPago;
/*  88:    */   @EJB
/*  89:    */   private ServicioListaPrecios servicioListaPrecios;
/*  90:    */   @EJB
/*  91:    */   private ServicioGenerico<Contacto> servicioContacto;
/*  92:    */   @EJB
/*  93:    */   private ServicioTipoContacto servicioTipoContacto;
/*  94: 94 */   private LanguageController languageController = new LanguageController();
/*  95:    */   
/*  96:    */   public LanguageController getLanguageController()
/*  97:    */   {
/*  98: 97 */     return this.languageController;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setLanguageController(LanguageController languageController)
/* 102:    */   {
/* 103:101 */     this.languageController = languageController;
/* 104:    */   }
/* 105:    */   
/* 106:    */   @POST
/* 107:    */   @Path("/obtenerListaEmpresaPorPagina")
/* 108:    */   @Consumes({"application/json"})
/* 109:    */   @Produces({"application/json"})
/* 110:    */   public ListaPaginadaResponseDto obtenerListaEmpresaPorPagina(FiltrosRequestDto request)
/* 111:    */     throws AS2Exception
/* 112:    */   {
/* 113:109 */     String error = null;
/* 114:110 */     this.languageController.setAccesoWeb(false);
/* 115:111 */     this.languageController.setUrlHost(request.getUrlApp());
/* 116:112 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 117:    */     try
/* 118:    */     {
/* 119:115 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 120:116 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 121:117 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombreFiscal";
/* 122:118 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 123:    */       
/* 124:120 */       Map<String, String> filtros = new HashMap();
/* 125:121 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 126:122 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 127:    */       }
/* 128:124 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 129:125 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 130:    */       }
/* 131:127 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 132:128 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 133:    */       }
/* 134:131 */       Object listaResponse = new ArrayList();
/* 135:132 */       List<Empresa> listaEmpresa = this.servicioEmpresa.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros);
/* 136:133 */       for (Empresa empresa : listaEmpresa)
/* 137:    */       {
/* 138:134 */         EmpresaResponseDto empresaResponse = new EmpresaResponseDto();
/* 139:135 */         cargarDatosBasicosEmpresa(empresaResponse, empresa);
/* 140:136 */         ((List)listaResponse).add(empresaResponse);
/* 141:    */       }
/* 142:139 */       response.setSuccsess(true);
/* 143:140 */       response.setRowCount(Integer.valueOf(this.servicioEmpresa.contarPorCriterio(filtros)));
/* 144:141 */       response.setLista((List)listaResponse);
/* 145:    */       
/* 146:143 */       return response;
/* 147:    */     }
/* 148:    */     catch (Exception e)
/* 149:    */     {
/* 150:145 */       e.printStackTrace();
/* 151:146 */       error = e.getMessage();
/* 152:    */       
/* 153:148 */       response.setSuccsess(false);
/* 154:149 */       response.setError(error);
/* 155:    */     }
/* 156:150 */     return response;
/* 157:    */   }
/* 158:    */   
/* 159:    */   @POST
/* 160:    */   @Path("/buscarEmpresaPorId")
/* 161:    */   @Consumes({"application/json"})
/* 162:    */   @Produces({"application/json"})
/* 163:    */   public ProcesosResponseDto buscarEmpresaPorId(Integer idEmpresa)
/* 164:    */     throws AS2Exception
/* 165:    */   {
/* 166:159 */     String error = null;
/* 167:160 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 168:    */     try
/* 169:    */     {
/* 170:163 */       Map<String, String> filtros = new HashMap();
/* 171:164 */       filtros.put("idEmpresa", "" + idEmpresa);
/* 172:    */       
/* 173:166 */       List<Empresa> listaEmpresa = this.servicioEmpresa.obtenerListaPorPagina(0, 1, "idEmpresa", true, filtros);
/* 174:167 */       EmpresaResponseDto empresaResponse = null;
/* 175:168 */       if (!listaEmpresa.isEmpty())
/* 176:    */       {
/* 177:169 */         empresaResponse = new EmpresaResponseDto();
/* 178:170 */         cargarDatosBasicosEmpresa(empresaResponse, (Empresa)listaEmpresa.get(0));
/* 179:    */       }
/* 180:173 */       response.setSuccsess(true);
/* 181:174 */       response.setResponse(empresaResponse);
/* 182:    */       
/* 183:176 */       return response;
/* 184:    */     }
/* 185:    */     catch (Exception e)
/* 186:    */     {
/* 187:178 */       e.printStackTrace();
/* 188:179 */       error = e.getMessage();
/* 189:    */       
/* 190:181 */       response.setSuccsess(false);
/* 191:182 */       response.setError(error);
/* 192:    */     }
/* 193:183 */     return response;
/* 194:    */   }
/* 195:    */   
/* 196:    */   @POST
/* 197:    */   @Path("/cargarDetalleEmpresa")
/* 198:    */   @Consumes({"application/json"})
/* 199:    */   @Produces({"application/json"})
/* 200:    */   public ProcesosResponseDto cargarDetalleEmpresa(CargarDetalleRequestDto request)
/* 201:    */     throws AS2Exception
/* 202:    */   {
/* 203:192 */     String error = null;
/* 204:193 */     this.languageController.setAccesoWeb(false);
/* 205:194 */     this.languageController.setUrlHost(request.getUrlApp());
/* 206:195 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 207:    */     try
/* 208:    */     {
/* 209:198 */       Empresa empresa = this.servicioEmpresa.buscarPorId(request.getId());
/* 210:199 */       empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 211:200 */       EmpresaResponseDto empresaResponse = new EmpresaResponseDto();
/* 212:    */       
/* 213:202 */       cargarDatosBasicosEmpresa(empresaResponse, empresa);
/* 214:    */       
/* 215:204 */       cargarDetallesEmpresa(empresaResponse, empresa);
/* 216:    */       
/* 217:206 */       response.setSuccsess(true);
/* 218:207 */       response.setResponse(empresaResponse);
/* 219:    */       
/* 220:209 */       return response;
/* 221:    */     }
/* 222:    */     catch (Exception e)
/* 223:    */     {
/* 224:211 */       e.printStackTrace();
/* 225:212 */       error = e.getMessage();
/* 226:    */       
/* 227:214 */       response.setSuccsess(false);
/* 228:215 */       response.setError(error);
/* 229:    */     }
/* 230:216 */     return response;
/* 231:    */   }
/* 232:    */   
/* 233:    */   @POST
/* 234:    */   @Path("/obtenerListaContactoPorPagina")
/* 235:    */   @Consumes({"application/json"})
/* 236:    */   @Produces({"application/json"})
/* 237:    */   public ListaPaginadaResponseDto obtenerListaContactoPorPagina(FiltrosRequestDto request)
/* 238:    */     throws AS2Exception
/* 239:    */   {
/* 240:225 */     String error = null;
/* 241:226 */     this.languageController.setAccesoWeb(false);
/* 242:227 */     this.languageController.setUrlHost(request.getUrlApp());
/* 243:228 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 244:    */     try
/* 245:    */     {
/* 246:231 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 247:232 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 248:233 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 249:234 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 250:    */       
/* 251:236 */       Map<String, String> filtros = new HashMap();
/* 252:237 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 253:238 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 254:    */       }
/* 255:240 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 256:241 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 257:    */       }
/* 258:243 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 259:244 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 260:    */       }
/* 261:247 */       Object listaResponse = new ArrayList();
/* 262:248 */       List<String> listaCampos = new ArrayList();
/* 263:249 */       listaCampos.add("tipoContacto");
/* 264:250 */       listaCampos.add("empresa");
/* 265:251 */       List<Contacto> listaContacto = this.servicioContacto.obtenerListaPorPagina(Contacto.class, startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros, listaCampos);
/* 266:253 */       for (Contacto contacto : listaContacto)
/* 267:    */       {
/* 268:254 */         ContactoResponseDto contactoResponse = new ContactoResponseDto();
/* 269:255 */         cargarContacto(contactoResponse, contacto);
/* 270:256 */         ((List)listaResponse).add(contactoResponse);
/* 271:    */       }
/* 272:259 */       response.setRowCount(Integer.valueOf(this.servicioContacto.contarPorCriterio(Contacto.class, filtros)));
/* 273:260 */       response.setSuccsess(true);
/* 274:261 */       response.setLista((List)listaResponse);
/* 275:    */       
/* 276:263 */       return response;
/* 277:    */     }
/* 278:    */     catch (Exception e)
/* 279:    */     {
/* 280:265 */       e.printStackTrace();
/* 281:266 */       error = e.getMessage();
/* 282:    */       
/* 283:268 */       response.setSuccsess(false);
/* 284:269 */       response.setError(error);
/* 285:    */     }
/* 286:270 */     return response;
/* 287:    */   }
/* 288:    */   
/* 289:    */   @POST
/* 290:    */   @Path("/guardarContacto")
/* 291:    */   @Consumes({"application/json"})
/* 292:    */   @Produces({"application/json"})
/* 293:    */   public ProcesosResponseDto guardarContacto(ContactoResponseDto request)
/* 294:    */     throws AS2Exception
/* 295:    */   {
/* 296:279 */     String error = null;
/* 297:280 */     this.languageController.setAccesoWeb(false);
/* 298:281 */     this.languageController.setUrlHost(request.getUrlApp());
/* 299:282 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 300:    */     try
/* 301:    */     {
/* 302:284 */       Contacto contacto = new Contacto();
/* 303:285 */       if ((request.getIdContacto() != null) && (!request.getIdContacto().equals(Integer.valueOf(0))))
/* 304:    */       {
/* 305:286 */         List<String> listaCampos = new ArrayList();
/* 306:287 */         listaCampos.add("empresa");
/* 307:288 */         listaCampos.add("tipoContacto");
/* 308:289 */         contacto = (Contacto)this.servicioContacto.cargarDetalle(Contacto.class, request.getIdContacto().intValue(), listaCampos);
/* 309:    */       }
/* 310:    */       else
/* 311:    */       {
/* 312:291 */         contacto.setIdOrganizacion(request.getIdOrganizacion().intValue());
/* 313:292 */         contacto.setUsuarioCreacion(request.getUsuarioCreacion());
/* 314:    */       }
/* 315:294 */       if ((contacto.getEmpresa() == null) && (request.getIdEmpresa() != null) && (!request.getIdEmpresa().equals(Integer.valueOf(0))))
/* 316:    */       {
/* 317:295 */         Empresa empresa = new Empresa();
/* 318:296 */         empresa.setIdEmpresa(request.getIdEmpresa().intValue());
/* 319:297 */         contacto.setEmpresa(empresa);
/* 320:    */       }
/* 321:299 */       contacto.setActivo(request.isActivo());
/* 322:300 */       contacto.setCargo(request.getCargo());
/* 323:301 */       contacto.setEmail1(request.getEmail1());
/* 324:302 */       contacto.setEmail2(request.getEmail2());
/* 325:303 */       contacto.setExtension1(request.getExtension1());
/* 326:304 */       contacto.setExtension2(request.getExtension2());
/* 327:305 */       contacto.setNombre(request.getNombre());
/* 328:306 */       contacto.setTelefono1(request.getTelefono1());
/* 329:307 */       contacto.setTelefono2(request.getTelefono2());
/* 330:308 */       contacto.setUsuarioModificacion(request.getUsuarioModificacion());
/* 331:309 */       if ((request.getTipoContacto() != null) && (request.getTipoContacto().getIdCatalogo() != null) && 
/* 332:310 */         (!request.getTipoContacto().getIdCatalogo().equals(Integer.valueOf(0))))
/* 333:    */       {
/* 334:311 */         TipoContacto tipoContacto = new TipoContacto();
/* 335:312 */         tipoContacto.setIdTipoContacto(request.getTipoContacto().getIdCatalogo().intValue());
/* 336:313 */         contacto.setTipoContacto(tipoContacto);
/* 337:    */       }
/* 338:316 */       this.servicioContacto.guardar(contacto);
/* 339:    */       
/* 340:318 */       request.setIdContacto(Integer.valueOf(contacto.getIdContacto()));
/* 341:    */       
/* 342:320 */       response.setSuccsess(true);
/* 343:321 */       response.setResponse(request);
/* 344:    */       
/* 345:323 */       return response;
/* 346:    */     }
/* 347:    */     catch (AS2Exception e)
/* 348:    */     {
/* 349:325 */       e.printStackTrace();
/* 350:326 */       error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + " | " + e.getMessage();
/* 351:    */     }
/* 352:    */     catch (Exception e)
/* 353:    */     {
/* 354:328 */       e.printStackTrace();
/* 355:329 */       error = e.getMessage();
/* 356:    */     }
/* 357:332 */     response.setSuccsess(false);
/* 358:333 */     response.setError(error);
/* 359:334 */     return response;
/* 360:    */   }
/* 361:    */   
/* 362:    */   @POST
/* 363:    */   @Path("/eliminarContacto")
/* 364:    */   @Consumes({"application/json"})
/* 365:    */   @Produces({"application/json"})
/* 366:    */   public ProcesosResponseDto eliminarContacto(ContactoResponseDto request)
/* 367:    */     throws AS2Exception
/* 368:    */   {
/* 369:342 */     String error = null;
/* 370:343 */     this.languageController.setAccesoWeb(false);
/* 371:344 */     this.languageController.setUrlHost(request.getUrlApp());
/* 372:345 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 373:    */     try
/* 374:    */     {
/* 375:347 */       Contacto contacto = new Contacto();
/* 376:348 */       contacto.setIdContacto(request.getIdContacto().intValue());
/* 377:349 */       this.servicioContacto.eliminar(contacto);
/* 378:    */       
/* 379:351 */       response.setSuccsess(true);
/* 380:    */       
/* 381:353 */       return response;
/* 382:    */     }
/* 383:    */     catch (Exception e)
/* 384:    */     {
/* 385:355 */       e.printStackTrace();
/* 386:356 */       error = e.getMessage();
/* 387:    */       
/* 388:    */ 
/* 389:359 */       response.setSuccsess(false);
/* 390:360 */       response.setError(error);
/* 391:    */     }
/* 392:361 */     return response;
/* 393:    */   }
/* 394:    */   
/* 395:    */   @POST
/* 396:    */   @Path("/obtenerListaTipoContactoPorPagina")
/* 397:    */   @Consumes({"application/json"})
/* 398:    */   @Produces({"application/json"})
/* 399:    */   public ListaPaginadaResponseDto obtenerListaTipoContactoPorPagina(FiltrosRequestDto request)
/* 400:    */     throws AS2Exception
/* 401:    */   {
/* 402:369 */     String error = null;
/* 403:370 */     this.languageController.setAccesoWeb(false);
/* 404:371 */     this.languageController.setUrlHost(request.getUrlApp());
/* 405:372 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 406:    */     try
/* 407:    */     {
/* 408:375 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 409:376 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 410:377 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 411:378 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 412:    */       
/* 413:380 */       Map<String, String> filtros = new HashMap();
/* 414:381 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 415:382 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 416:    */       }
/* 417:384 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 418:385 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 419:    */       }
/* 420:387 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 421:388 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 422:    */       }
/* 423:391 */       Object listaResponse = new ArrayList();
/* 424:392 */       List<TipoContacto> listaTipoContacto = this.servicioTipoContacto.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros);
/* 425:393 */       for (TipoContacto tipoContacto : listaTipoContacto)
/* 426:    */       {
/* 427:394 */         CatalogoGenericoResponseDto tipoContactoResponse = new CatalogoGenericoResponseDto();
/* 428:395 */         cargarTipoContacto(tipoContactoResponse, tipoContacto);
/* 429:396 */         ((List)listaResponse).add(tipoContactoResponse);
/* 430:    */       }
/* 431:399 */       response.setSuccsess(true);
/* 432:400 */       response.setRowCount(Integer.valueOf(this.servicioTipoContacto.contarPorCriterio(filtros)));
/* 433:401 */       response.setLista((List)listaResponse);
/* 434:    */       
/* 435:403 */       return response;
/* 436:    */     }
/* 437:    */     catch (Exception e)
/* 438:    */     {
/* 439:405 */       e.printStackTrace();
/* 440:406 */       error = e.getMessage();
/* 441:    */       
/* 442:408 */       response.setSuccsess(false);
/* 443:409 */       response.setError(error);
/* 444:    */     }
/* 445:410 */     return response;
/* 446:    */   }
/* 447:    */   
/* 448:    */   @POST
/* 449:    */   @Path("/obtenerListaCategoriaEmpresaPorPagina")
/* 450:    */   @Consumes({"application/json"})
/* 451:    */   @Produces({"application/json"})
/* 452:    */   public ListaPaginadaResponseDto obtenerListaCategoriaEmpresaPorPagina(FiltrosRequestDto request)
/* 453:    */     throws AS2Exception
/* 454:    */   {
/* 455:419 */     String error = null;
/* 456:420 */     this.languageController.setAccesoWeb(false);
/* 457:421 */     this.languageController.setUrlHost(request.getUrlApp());
/* 458:422 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 459:    */     try
/* 460:    */     {
/* 461:425 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 462:426 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 463:427 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 464:428 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 465:    */       
/* 466:430 */       Map<String, String> filtros = new HashMap();
/* 467:431 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 468:432 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 469:    */       }
/* 470:434 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 471:435 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 472:    */       }
/* 473:437 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 474:438 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 475:    */       }
/* 476:441 */       Object listaResponse = new ArrayList();
/* 477:442 */       List<CategoriaEmpresa> listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros);
/* 478:444 */       for (CategoriaEmpresa categoriaEmpresa : listaCategoriaEmpresa)
/* 479:    */       {
/* 480:446 */         CatalogoGenericoResponseDto categoriaEmpresaResponse = new CatalogoGenericoResponseDto();
/* 481:447 */         cargarCategoriaEmpresa(categoriaEmpresaResponse, categoriaEmpresa);
/* 482:    */         
/* 483:449 */         ((List)listaResponse).add(categoriaEmpresaResponse);
/* 484:    */       }
/* 485:452 */       response.setSuccsess(true);
/* 486:453 */       response.setRowCount(Integer.valueOf(this.servicioCategoriaEmpresa.contarPorCriterio(filtros)));
/* 487:454 */       response.setLista((List)listaResponse);
/* 488:    */       
/* 489:456 */       return response;
/* 490:    */     }
/* 491:    */     catch (Exception e)
/* 492:    */     {
/* 493:458 */       e.printStackTrace();
/* 494:459 */       error = e.getMessage();
/* 495:    */       
/* 496:461 */       response.setSuccsess(false);
/* 497:462 */       response.setError(error);
/* 498:    */     }
/* 499:463 */     return response;
/* 500:    */   }
/* 501:    */   
/* 502:    */   @POST
/* 503:    */   @Path("/obtenerListaTipoIdentificacionPorPagina")
/* 504:    */   @Consumes({"application/json"})
/* 505:    */   @Produces({"application/json"})
/* 506:    */   public ListaPaginadaResponseDto obtenerListaTipoIdentificacionPorPagina(FiltrosRequestDto request)
/* 507:    */     throws AS2Exception
/* 508:    */   {
/* 509:472 */     String error = null;
/* 510:473 */     this.languageController.setAccesoWeb(false);
/* 511:474 */     this.languageController.setUrlHost(request.getUrlApp());
/* 512:475 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 513:    */     try
/* 514:    */     {
/* 515:478 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 516:479 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 517:480 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 518:481 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 519:    */       
/* 520:483 */       Map<String, String> filtros = new HashMap();
/* 521:484 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 522:485 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 523:    */       }
/* 524:487 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 525:488 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 526:    */       }
/* 527:490 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 528:491 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 529:    */       }
/* 530:494 */       Object listaResponse = new ArrayList();
/* 531:495 */       List<TipoIdentificacion> listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc
/* 532:496 */         .booleanValue(), filtros);
/* 533:497 */       for (TipoIdentificacion tipoIdentificacion : listaTipoIdentificacion)
/* 534:    */       {
/* 535:498 */         TipoIdentificacionResponseDto tipoIdentificacionResponse = new TipoIdentificacionResponseDto();
/* 536:499 */         cargarTipoIdentificacion(tipoIdentificacionResponse, tipoIdentificacion);
/* 537:500 */         ((List)listaResponse).add(tipoIdentificacionResponse);
/* 538:    */       }
/* 539:503 */       response.setSuccsess(true);
/* 540:504 */       response.setRowCount(Integer.valueOf(this.servicioTipoIdentificacion.contarPorCriterio(filtros)));
/* 541:505 */       response.setLista((List)listaResponse);
/* 542:    */       
/* 543:507 */       return response;
/* 544:    */     }
/* 545:    */     catch (Exception e)
/* 546:    */     {
/* 547:509 */       e.printStackTrace();
/* 548:510 */       error = e.getMessage();
/* 549:    */       
/* 550:512 */       response.setSuccsess(false);
/* 551:513 */       response.setError(error);
/* 552:    */     }
/* 553:514 */     return response;
/* 554:    */   }
/* 555:    */   
/* 556:    */   @POST
/* 557:    */   @Path("/obtenerListaCiudadPorPagina")
/* 558:    */   @Consumes({"application/json"})
/* 559:    */   @Produces({"application/json"})
/* 560:    */   public ListaPaginadaResponseDto obtenerListaCiudadPorPagina(FiltrosRequestDto request)
/* 561:    */     throws AS2Exception
/* 562:    */   {
/* 563:523 */     String error = null;
/* 564:524 */     this.languageController.setAccesoWeb(false);
/* 565:525 */     this.languageController.setUrlHost(request.getUrlApp());
/* 566:526 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 567:    */     try
/* 568:    */     {
/* 569:529 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 570:530 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 571:531 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 572:532 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 573:    */       
/* 574:534 */       Map<String, String> filtros = new HashMap();
/* 575:535 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 576:536 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 577:    */       }
/* 578:538 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 579:539 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 580:    */       }
/* 581:541 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 582:542 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 583:    */       }
/* 584:545 */       Object listaResponse = new ArrayList();
/* 585:546 */       List<Ciudad> listaCiudad = this.servicioCiudad.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros);
/* 586:547 */       for (Ciudad ciudad : listaCiudad)
/* 587:    */       {
/* 588:548 */         CiudadResponseDto ciudadResponse = new CiudadResponseDto();
/* 589:549 */         cargarCiudad(ciudadResponse, ciudad);
/* 590:550 */         ((List)listaResponse).add(ciudadResponse);
/* 591:    */         
/* 592:    */ 
/* 593:553 */         Provincia provincia = ciudad.getProvincia();
/* 594:554 */         ProvinciaResponseDto provinciaResponse = new ProvinciaResponseDto();
/* 595:555 */         cargarProvincia(provinciaResponse, provincia);
/* 596:556 */         ciudadResponse.setProvincia(provinciaResponse);
/* 597:    */       }
/* 598:559 */       response.setSuccsess(true);
/* 599:560 */       response.setRowCount(Integer.valueOf(this.servicioCiudad.contarPorCriterio(filtros)));
/* 600:561 */       response.setLista((List)listaResponse);
/* 601:    */       
/* 602:563 */       return response;
/* 603:    */     }
/* 604:    */     catch (Exception e)
/* 605:    */     {
/* 606:565 */       e.printStackTrace();
/* 607:566 */       error = e.getMessage();
/* 608:    */       
/* 609:568 */       response.setSuccsess(false);
/* 610:569 */       response.setError(error);
/* 611:    */     }
/* 612:570 */     return response;
/* 613:    */   }
/* 614:    */   
/* 615:    */   @POST
/* 616:    */   @Path("/validarIdentificacion")
/* 617:    */   @Consumes({"application/json"})
/* 618:    */   @Produces({"application/json"})
/* 619:    */   public boolean validarIdentificacion(String identificacion)
/* 620:    */     throws AS2Exception
/* 621:    */   {
/* 622:    */     try
/* 623:    */     {
/* 624:580 */       this.servicioEmpresa.validarIdentificacion(identificacion);
/* 625:581 */       return true;
/* 626:    */     }
/* 627:    */     catch (ExcepcionAS2Identification e)
/* 628:    */     {
/* 629:583 */       e.printStackTrace();
/* 630:    */     }
/* 631:584 */     return false;
/* 632:    */   }
/* 633:    */   
/* 634:    */   @POST
/* 635:    */   @Path("/obtenerListaCondicionPagoPorPagina")
/* 636:    */   @Consumes({"application/json"})
/* 637:    */   @Produces({"application/json"})
/* 638:    */   public ListaPaginadaResponseDto obtenerListaCondicionPagoPorPagina(FiltrosRequestDto request)
/* 639:    */     throws AS2Exception
/* 640:    */   {
/* 641:593 */     String error = null;
/* 642:594 */     this.languageController.setAccesoWeb(false);
/* 643:595 */     this.languageController.setUrlHost(request.getUrlApp());
/* 644:596 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 645:    */     try
/* 646:    */     {
/* 647:599 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 648:600 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 649:601 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 650:602 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 651:    */       
/* 652:604 */       Map<String, String> filtros = new HashMap();
/* 653:605 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 654:606 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 655:    */       }
/* 656:608 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 657:609 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 658:    */       }
/* 659:611 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 660:612 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 661:    */       }
/* 662:615 */       Object listaCondicionPago = this.servicioCondicionPago.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros);
/* 663:    */       
/* 664:617 */       response.setSuccsess(true);
/* 665:618 */       response.setRowCount(Integer.valueOf(this.servicioTipoIdentificacion.contarPorCriterio(filtros)));
/* 666:619 */       response.setLista((List)listaCondicionPago);
/* 667:    */       
/* 668:621 */       return response;
/* 669:    */     }
/* 670:    */     catch (Exception e)
/* 671:    */     {
/* 672:623 */       e.printStackTrace();
/* 673:624 */       error = e.getMessage();
/* 674:    */       
/* 675:626 */       response.setSuccsess(false);
/* 676:627 */       response.setError(error);
/* 677:    */     }
/* 678:628 */     return response;
/* 679:    */   }
/* 680:    */   
/* 681:    */   @POST
/* 682:    */   @Path("/buscarCondicionPagoPorId")
/* 683:    */   @Consumes({"application/json"})
/* 684:    */   @Produces({"application/json"})
/* 685:    */   public ProcesosResponseDto buscarCondicionPagoPorId(Integer idCondicionPago)
/* 686:    */     throws AS2Exception
/* 687:    */   {
/* 688:637 */     String error = null;
/* 689:638 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 690:    */     try
/* 691:    */     {
/* 692:641 */       CondicionPago condicionPago = this.servicioCondicionPago.buscarPorId(idCondicionPago);
/* 693:    */       
/* 694:643 */       response.setSuccsess(true);
/* 695:644 */       response.setResponse(condicionPago);
/* 696:    */       
/* 697:646 */       return response;
/* 698:    */     }
/* 699:    */     catch (Exception e)
/* 700:    */     {
/* 701:648 */       e.printStackTrace();
/* 702:649 */       error = e.getMessage();
/* 703:    */       
/* 704:651 */       response.setSuccsess(false);
/* 705:652 */       response.setError(error);
/* 706:    */     }
/* 707:653 */     return response;
/* 708:    */   }
/* 709:    */   
/* 710:    */   @POST
/* 711:    */   @Path("/buscarContactoPorId")
/* 712:    */   @Consumes({"application/json"})
/* 713:    */   @Produces({"application/json"})
/* 714:    */   public ProcesosResponseDto buscarContactoPorId(Integer idContacto)
/* 715:    */     throws AS2Exception
/* 716:    */   {
/* 717:662 */     String error = null;
/* 718:663 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 719:    */     try
/* 720:    */     {
/* 721:666 */       List<String> listaCampos = new ArrayList();
/* 722:667 */       listaCampos.add("tipoContacto");
/* 723:668 */       listaCampos.add("empresa");
/* 724:669 */       Contacto contacto = (Contacto)this.servicioContacto.cargarDetalle(Contacto.class, idContacto.intValue(), listaCampos);
/* 725:670 */       ContactoResponseDto contactoResponse = new ContactoResponseDto();
/* 726:671 */       cargarContacto(contactoResponse, contacto);
/* 727:    */       
/* 728:673 */       response.setSuccsess(true);
/* 729:674 */       response.setResponse(contactoResponse);
/* 730:    */       
/* 731:676 */       return response;
/* 732:    */     }
/* 733:    */     catch (Exception e)
/* 734:    */     {
/* 735:678 */       e.printStackTrace();
/* 736:679 */       error = e.getMessage();
/* 737:    */       
/* 738:681 */       response.setSuccsess(false);
/* 739:682 */       response.setError(error);
/* 740:    */     }
/* 741:683 */     return response;
/* 742:    */   }
/* 743:    */   
/* 744:    */   private void cargarDatosBasicosEmpresa(EmpresaResponseDto empresaResponse, Empresa empresa)
/* 745:    */   {
/* 746:689 */     empresaResponse.setIdEmpresa(Integer.valueOf(empresa.getIdEmpresa()));
/* 747:690 */     empresaResponse.setIdOrganizacion(empresa.getIdOrganizacion());
/* 748:691 */     empresaResponse.setIdSucursal(empresa.getIdSucursal());
/* 749:692 */     empresaResponse.setIdentificacion(empresa.getIdentificacion());
/* 750:693 */     empresaResponse.setTipoIdentificacion(empresa.getTipoIdentificacion().getNombre());
/* 751:694 */     empresaResponse.setNombreFiscal(empresa.getNombreFiscal());
/* 752:695 */     empresaResponse.setNombreComercial(empresa.getNombreComercial());
/* 753:696 */     empresaResponse.setEmail(empresa.getEmail1());
/* 754:697 */     empresaResponse.setActivo(empresa.isActivo());
/* 755:698 */     empresaResponse.setIndicadorCliente(Boolean.valueOf(empresa.isIndicadorCliente()));
/* 756:699 */     empresaResponse.setIndicadorProveedor(Boolean.valueOf(empresa.isIndicadorProveedor()));
/* 757:700 */     empresaResponse.setTipoIdentificacion(empresa.getTipoIdentificacion().getNombre());
/* 758:701 */     empresaResponse.setDescripcion(empresa.getDescripcion());
/* 759:702 */     empresaResponse.setTipoEmpresa(empresa.getTipoEmpresa());
/* 760:703 */     empresaResponse.setLongitud(empresa.getLongitud());
/* 761:704 */     empresaResponse.setLatitud(empresa.getLatitud());
/* 762:706 */     if (empresa.getCliente() != null)
/* 763:    */     {
/* 764:707 */       empresaResponse.setExcentoImpuestos(Boolean.valueOf(empresa.getCliente().isExcentoImpuestos()));
/* 765:708 */       if (empresa.getCliente().getTipoOrdenDespacho() != null) {
/* 766:709 */         empresaResponse.setDiasDespacho(Integer.valueOf(empresa.getCliente().getTipoOrdenDespacho().getDiasDespacho()));
/* 767:    */       }
/* 768:    */     }
/* 769:713 */     CatalogoGenericoResponseDto categoriaEmpresaResponse = new CatalogoGenericoResponseDto();
/* 770:714 */     cargarCategoriaEmpresa(categoriaEmpresaResponse, empresa.getCategoriaEmpresa());
/* 771:715 */     empresaResponse.setCategoriaEmpresa(categoriaEmpresaResponse);
/* 772:    */   }
/* 773:    */   
/* 774:    */   private void cargarDetallesEmpresa(EmpresaResponseDto empresaResponse, Empresa empresa)
/* 775:    */   {
/* 776:720 */     TipoIdentificacion tipoIdentificacion = empresa.getTipoIdentificacion();
/* 777:721 */     TipoIdentificacionResponseDto tipoIdentificacionResponse = new TipoIdentificacionResponseDto();
/* 778:722 */     cargarTipoIdentificacion(tipoIdentificacionResponse, tipoIdentificacion);
/* 779:723 */     empresaResponse.setTipoIdentificacionResponse(tipoIdentificacionResponse);
/* 780:726 */     if ((empresa.getCliente() != null) && (empresa.getCliente().getListaPrecios() != null)) {
/* 781:727 */       empresaResponse.setIdListaPrecios(Integer.valueOf(empresa.getCliente().getListaPrecios().getIdListaPrecios()));
/* 782:    */     }
/* 783:729 */     if ((empresa.getCliente() != null) && (empresa.getCliente().getListaDescuentos() != null)) {
/* 784:730 */       empresaResponse.setIdListaDescuentos(Integer.valueOf(empresa.getCliente().getListaDescuentos().getIdListaDescuentos()));
/* 785:    */     }
/* 786:734 */     if (empresa.getDirecciones().size() > 0) {
/* 787:735 */       empresaResponse.setDireccion(((DireccionEmpresa)empresa.getDirecciones().get(0)).getDireccionCompleta());
/* 788:    */     }
/* 789:737 */     for (DireccionEmpresa direccionEmpresa : empresa.getDirecciones())
/* 790:    */     {
/* 791:738 */       DireccionEmpresaResponseDto responseDireccion = new DireccionEmpresaResponseDto();
/* 792:739 */       responseDireccion.setIdDireccionCliente(Integer.valueOf(direccionEmpresa.getIdDireccionEmpresa()));
/* 793:740 */       responseDireccion.setIdOrganizacion(direccionEmpresa.getIdOrganizacion());
/* 794:741 */       responseDireccion.setIdSucursal(direccionEmpresa.getIdSucursal());
/* 795:742 */       responseDireccion.setIndicadorDireccionPrincipal(direccionEmpresa.isIndicadorDireccionPrincipal());
/* 796:    */       
/* 797:    */ 
/* 798:745 */       Ubicacion ubicacion = direccionEmpresa.getUbicacion();
/* 799:    */       
/* 800:747 */       UbicacionResponseDto ubicacionResponse = new UbicacionResponseDto();
/* 801:748 */       ubicacionResponse.setIdOrganizacion(ubicacion.getIdOrganizacion());
/* 802:749 */       ubicacionResponse.setIdSucursal(ubicacion.getIdSucursal());
/* 803:750 */       ubicacionResponse.setIdUbicacion(Integer.valueOf(ubicacion.getIdUbicacion()));
/* 804:751 */       ubicacionResponse.setDireccion1(ubicacion.getDireccion1());
/* 805:752 */       ubicacionResponse.setDireccion2(ubicacion.getDireccion2());
/* 806:753 */       ubicacionResponse.setDireccion3(ubicacion.getDireccion3());
/* 807:754 */       ubicacionResponse.setDireccion4(ubicacion.getDireccion4());
/* 808:755 */       ubicacionResponse.setDireccion5(ubicacion.getDireccion5());
/* 809:    */       
/* 810:757 */       responseDireccion.setUbicacion(ubicacionResponse);
/* 811:    */       
/* 812:    */ 
/* 813:    */ 
/* 814:761 */       Ciudad ciudad = direccionEmpresa.getCiudad();
/* 815:    */       
/* 816:763 */       CiudadResponseDto ciudadResponse = new CiudadResponseDto();
/* 817:764 */       cargarCiudad(ciudadResponse, ciudad);
/* 818:    */       
/* 819:    */ 
/* 820:    */ 
/* 821:768 */       Provincia provincia = ciudad.getProvincia();
/* 822:769 */       ProvinciaResponseDto provinciaResponse = new ProvinciaResponseDto();
/* 823:    */       
/* 824:771 */       cargarProvincia(provinciaResponse, provincia);
/* 825:    */       
/* 826:    */ 
/* 827:774 */       Pais pais = provincia.getPais();
/* 828:775 */       PaisResponseDto paisResponse = new PaisResponseDto();
/* 829:776 */       paisResponse.setIdOrganizacion(pais.getIdOrganizacion());
/* 830:777 */       paisResponse.setIdSucursal(pais.getIdSucursal());
/* 831:778 */       paisResponse.setIdPais(Integer.valueOf(pais.getIdPais()));
/* 832:779 */       paisResponse.setCodigoIso(pais.getCodigoIso());
/* 833:780 */       paisResponse.setNombre(pais.getNombre());
/* 834:781 */       paisResponse.setActivo(Boolean.valueOf(pais.isActivo()));
/* 835:782 */       paisResponse.setPredeterminado(Boolean.valueOf(pais.isPredeterminado()));
/* 836:    */       
/* 837:784 */       provinciaResponse.setPaisResponse(paisResponse);
/* 838:785 */       ciudadResponse.setProvincia(provinciaResponse);
/* 839:786 */       responseDireccion.setCiudad(ciudadResponse);
/* 840:    */       
/* 841:788 */       empresaResponse.getListaDireccionEmpresa().add(responseDireccion);
/* 842:    */     }
/* 843:792 */     for (Contacto contacto : empresa.getListaContacto())
/* 844:    */     {
/* 845:793 */       ContactoResponseDto contactoResponse = new ContactoResponseDto();
/* 846:794 */       cargarContacto(contactoResponse, contacto);
/* 847:795 */       empresaResponse.getListaContactoEmpresa().add(contactoResponse);
/* 848:    */     }
/* 849:    */   }
/* 850:    */   
/* 851:    */   private void cargarContacto(ContactoResponseDto contactoResponse, Contacto contacto)
/* 852:    */   {
/* 853:800 */     contactoResponse.setActivo(contacto.isActivo());
/* 854:801 */     contactoResponse.setCargo(contacto.getCargo());
/* 855:802 */     contactoResponse.setEmail1(contacto.getEmail1());
/* 856:803 */     contactoResponse.setEmail2(contacto.getEmail2());
/* 857:804 */     contactoResponse.setExtension1(contacto.getExtension1());
/* 858:805 */     contactoResponse.setExtension2(contacto.getExtension2());
/* 859:806 */     contactoResponse.setIdContacto(Integer.valueOf(contacto.getIdContacto()));
/* 860:807 */     contactoResponse.setIdOrganizacion(Integer.valueOf(contacto.getIdOrganizacion()));
/* 861:808 */     contactoResponse.setNombre(contacto.getNombre());
/* 862:809 */     contactoResponse.setTelefono1(contacto.getTelefono1());
/* 863:810 */     contactoResponse.setTelefono2(contacto.getTelefono2());
/* 864:    */     
/* 865:812 */     TipoContacto tipoContacto = contacto.getTipoContacto();
/* 866:813 */     CatalogoGenericoResponseDto tipoContactoResponse = new CatalogoGenericoResponseDto();
/* 867:814 */     cargarTipoContacto(tipoContactoResponse, tipoContacto);
/* 868:815 */     contactoResponse.setTipoContacto(tipoContactoResponse);
/* 869:817 */     if (contacto.getEmpresa() != null)
/* 870:    */     {
/* 871:818 */       contactoResponse.setIdEmpresa(Integer.valueOf(contacto.getEmpresa().getIdEmpresa()));
/* 872:819 */       contactoResponse.setIdentificacionEmpresa(contacto.getEmpresa().getIdentificacion());
/* 873:820 */       contactoResponse.setNombreComercialEmpresa(contacto.getEmpresa().getNombreComercial());
/* 874:821 */       contactoResponse.setNombreFiscalEmpresa(contacto.getEmpresa().getNombreFiscal());
/* 875:    */     }
/* 876:    */   }
/* 877:    */   
/* 878:    */   private void cargarTipoContacto(CatalogoGenericoResponseDto tipoContactoResponse, TipoContacto tipoContacto)
/* 879:    */   {
/* 880:826 */     tipoContactoResponse.setActivo(Boolean.valueOf(tipoContacto.isActivo()));
/* 881:827 */     tipoContactoResponse.setCodigo(tipoContacto.getCodigo());
/* 882:828 */     tipoContactoResponse.setDescripcion(tipoContacto.getDescripcion());
/* 883:829 */     tipoContactoResponse.setIdCatalogo(Integer.valueOf(tipoContacto.getIdTipoContacto()));
/* 884:830 */     tipoContactoResponse.setIdOrganizacion(Integer.valueOf(tipoContacto.getIdOrganizacion()));
/* 885:831 */     tipoContactoResponse.setIdSucursal(Integer.valueOf(tipoContacto.getIdSucursal()));
/* 886:832 */     tipoContactoResponse.setNombre(tipoContacto.getNombre());
/* 887:833 */     tipoContactoResponse.setPredeterminado(Boolean.valueOf(tipoContacto.isPredeterminado()));
/* 888:    */   }
/* 889:    */   
/* 890:    */   private void cargarCategoriaEmpresa(CatalogoGenericoResponseDto categoriaEmpresaResponse, CategoriaEmpresa categoriaEmpresa)
/* 891:    */   {
/* 892:837 */     categoriaEmpresaResponse.setActivo(Boolean.valueOf(categoriaEmpresa.isActivo()));
/* 893:838 */     categoriaEmpresaResponse.setCodigo(categoriaEmpresa.getCodigo());
/* 894:839 */     categoriaEmpresaResponse.setDescripcion(categoriaEmpresa.getDescripcion());
/* 895:840 */     categoriaEmpresaResponse.setIdCatalogo(Integer.valueOf(categoriaEmpresa.getIdCategoriaEmpresa()));
/* 896:841 */     categoriaEmpresaResponse.setIdOrganizacion(Integer.valueOf(categoriaEmpresa.getIdOrganizacion()));
/* 897:842 */     categoriaEmpresaResponse.setIdSucursal(Integer.valueOf(categoriaEmpresa.getIdSucursal()));
/* 898:843 */     categoriaEmpresaResponse.setNombre(categoriaEmpresa.getNombre());
/* 899:844 */     categoriaEmpresaResponse.setPredeterminado(Boolean.valueOf(categoriaEmpresa.isPredeterminado()));
/* 900:    */   }
/* 901:    */   
/* 902:    */   private void cargarTipoIdentificacion(TipoIdentificacionResponseDto tipoIdentificacionResponse, TipoIdentificacion tipoIdentificacion)
/* 903:    */   {
/* 904:848 */     tipoIdentificacionResponse.setIdOrganizacion(tipoIdentificacion.getIdOrganizacion());
/* 905:849 */     tipoIdentificacionResponse.setIdSucursal(tipoIdentificacion.getIdSucursal());
/* 906:850 */     tipoIdentificacionResponse.setIdTipoIdentificacion(tipoIdentificacion.getIdTipoIdentificacion());
/* 907:851 */     tipoIdentificacionResponse.setCodigo(tipoIdentificacion.getCodigo());
/* 908:852 */     tipoIdentificacionResponse.setNombre(tipoIdentificacion.getNombre());
/* 909:853 */     tipoIdentificacionResponse.setActivo(Boolean.valueOf(tipoIdentificacion.isActivo()));
/* 910:854 */     tipoIdentificacionResponse.setPredeterminado(Boolean.valueOf(tipoIdentificacion.isPredeterminado()));
/* 911:855 */     tipoIdentificacionResponse.setDescripcion(tipoIdentificacion.getDescripcion());
/* 912:856 */     tipoIdentificacionResponse.setIndicadorValidarIdentificacion(Boolean.valueOf(tipoIdentificacion.isIndicadorValidarIdentificacion()));
/* 913:    */   }
/* 914:    */   
/* 915:    */   private void cargarCiudad(CiudadResponseDto ciudadResponse, Ciudad ciudad)
/* 916:    */   {
/* 917:860 */     ciudadResponse.setIdOrganizacion(ciudad.getIdOrganizacion());
/* 918:861 */     ciudadResponse.setIdSucursal(ciudad.getIdCiudad());
/* 919:862 */     ciudadResponse.setIdCiudad(Integer.valueOf(ciudad.getIdCiudad()));
/* 920:863 */     ciudadResponse.setCodigo(ciudad.getCodigo());
/* 921:864 */     ciudadResponse.setNombre(ciudad.getNombre());
/* 922:865 */     ciudadResponse.setActivo(Boolean.valueOf(ciudad.isActivo()));
/* 923:866 */     ciudadResponse.setPredeterminado(Boolean.valueOf(ciudad.isPredeterminado()));
/* 924:    */   }
/* 925:    */   
/* 926:    */   private void cargarProvincia(ProvinciaResponseDto provinciaResponse, Provincia provincia)
/* 927:    */   {
/* 928:870 */     provinciaResponse.setIdOrganizacion(provincia.getIdOrganizacion());
/* 929:871 */     provinciaResponse.setIdSucursal(provincia.getIdSucursal());
/* 930:872 */     provinciaResponse.setIdProvincia(Integer.valueOf(provincia.getIdProvincia()));
/* 931:873 */     provinciaResponse.setCodigo(provincia.getCodigo());
/* 932:874 */     provinciaResponse.setNombre(provincia.getNombre());
/* 933:875 */     provinciaResponse.setActivo(Boolean.valueOf(provincia.isActivo()));
/* 934:876 */     provinciaResponse.setPredeterminado(Boolean.valueOf(provincia.isPredeterminado()));
/* 935:    */   }
/* 936:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.EmpresaServicioRest
 * JD-Core Version:    0.7.0.1
 */