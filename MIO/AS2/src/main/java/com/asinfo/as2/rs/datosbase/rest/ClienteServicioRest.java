/*   1:    */ package com.asinfo.as2.rs.datosbase.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioProvincia;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   8:    */ import com.asinfo.as2.dao.ClienteDao;
/*   9:    */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*  12:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*  14:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*  15:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*  16:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  17:    */ import com.asinfo.as2.entities.Ciudad;
/*  18:    */ import com.asinfo.as2.entities.Cliente;
/*  19:    */ import com.asinfo.as2.entities.CondicionPago;
/*  20:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  21:    */ import com.asinfo.as2.entities.Empresa;
/*  22:    */ import com.asinfo.as2.entities.FormaPago;
/*  23:    */ import com.asinfo.as2.entities.ListaDescuentos;
/*  24:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  25:    */ import com.asinfo.as2.entities.Pais;
/*  26:    */ import com.asinfo.as2.entities.Provincia;
/*  27:    */ import com.asinfo.as2.entities.Subempresa;
/*  28:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  29:    */ import com.asinfo.as2.entities.Ubicacion;
/*  30:    */ import com.asinfo.as2.enumeraciones.MetodoFacturacionEnum;
/*  31:    */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*  32:    */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*  33:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  34:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  35:    */ import com.asinfo.as2.rs.datosbase.dto.CatalogoGenericoResponseDto;
/*  36:    */ import com.asinfo.as2.rs.datosbase.dto.CiudadResponseDto;
/*  37:    */ import com.asinfo.as2.rs.datosbase.dto.ClienteRequestDto;
/*  38:    */ import com.asinfo.as2.rs.datosbase.dto.ClienteResponseDto;
/*  39:    */ import com.asinfo.as2.rs.datosbase.dto.DireccionEmpresaResponseDto;
/*  40:    */ import com.asinfo.as2.rs.datosbase.dto.EmpresaResponseDto;
/*  41:    */ import com.asinfo.as2.rs.datosbase.dto.ListaClienteRequestDto;
/*  42:    */ import com.asinfo.as2.rs.datosbase.dto.PaisResponseDto;
/*  43:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  44:    */ import com.asinfo.as2.rs.datosbase.dto.ProvinciaResponseDto;
/*  45:    */ import com.asinfo.as2.rs.datosbase.dto.TipoIdentificacionResponseDto;
/*  46:    */ import com.asinfo.as2.rs.datosbase.dto.UbicacionResponseDto;
/*  47:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  48:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  49:    */ import java.util.ArrayList;
/*  50:    */ import java.util.HashMap;
/*  51:    */ import java.util.Iterator;
/*  52:    */ import java.util.List;
/*  53:    */ import java.util.Map;
/*  54:    */ import javax.ejb.EJB;
/*  55:    */ import javax.ws.rs.Consumes;
/*  56:    */ import javax.ws.rs.POST;
/*  57:    */ import javax.ws.rs.Path;
/*  58:    */ import javax.ws.rs.Produces;
/*  59:    */ 
/*  60:    */ @Path("/datosBase")
/*  61:    */ public class ClienteServicioRest
/*  62:    */ {
/*  63:    */   @EJB
/*  64:    */   private ClienteDao clienteDao;
/*  65:    */   @EJB
/*  66:    */   private DireccionEmpresaDao direccionEmpresaDao;
/*  67:    */   @EJB
/*  68:    */   private ServicioEmpresa servicioEmpresa;
/*  69:    */   @EJB
/*  70:    */   private ServicioSucursal servicioSucursal;
/*  71:    */   @EJB
/*  72:    */   private ServicioGenerico<Ubicacion> servicioUbicacion;
/*  73:    */   @EJB
/*  74:    */   private ServicioCiudad servicioCiudad;
/*  75:    */   @EJB
/*  76:    */   private ServicioProvincia servicioProvincia;
/*  77:    */   @EJB
/*  78:    */   private ServicioPais servicioPais;
/*  79:    */   @EJB
/*  80:    */   private ServicioGenerico<DireccionEmpresa> servicioDireccionEmpresa;
/*  81:    */   @EJB
/*  82:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  83:    */   @EJB
/*  84:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  85:    */   @EJB
/*  86:    */   private ServicioFormaPago servicioFormaPago;
/*  87:    */   @EJB
/*  88:    */   private ServicioCondicionPago servicioCondicionPago;
/*  89:    */   @EJB
/*  90:    */   private ServicioListaPrecios servicioListaPrecios;
/*  91:    */   @EJB
/*  92:    */   private ServicioListaDescuentos servicioListaDescuentos;
/*  93:    */   
/*  94:    */   @POST
/*  95:    */   @Path("/consultarClientesPorUsuario")
/*  96:    */   @Consumes({"application/json"})
/*  97:    */   @Produces({"application/json"})
/*  98:    */   public List<ClienteResponseDto> consultarClientesPorusuario(ClienteRequestDto request)
/*  99:    */     throws AS2Exception
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:102 */       List<Cliente> listaCliente = this.clienteDao.buscarClientesPorIdUsuarioTransportistaOAgenteComercial(request.getIdUsuario().intValue());
/* 104:103 */       List<ClienteResponseDto> response = new ArrayList();
/* 105:105 */       for (Cliente cliente : listaCliente)
/* 106:    */       {
/* 107:106 */         ClienteResponseDto clienteResponse = new ClienteResponseDto();
/* 108:107 */         clienteResponse.setIdCliente(cliente.getId());
/* 109:108 */         clienteResponse.setIdentificacion(cliente.getEmpresa().getIdentificacion());
/* 110:109 */         clienteResponse.setTipoIdentificacion(cliente.getEmpresa().getTipoIdentificacion().getNombre());
/* 111:110 */         clienteResponse.setHorarioDespacho(cliente.getHorariosDespachos());
/* 112:111 */         clienteResponse.setHorarioVisita(cliente.getHorariosVisitas());
/* 113:112 */         clienteResponse.setIdOrganizacion(cliente.getEmpresa().getIdOrganizacion());
/* 114:113 */         clienteResponse.setNombreFiscal(cliente.getEmpresa().getNombreFiscal());
/* 115:114 */         clienteResponse.setEmail(cliente.getEmpresa().getEmail1());
/* 116:116 */         if (cliente.getListaPrecios() != null) {
/* 117:117 */           clienteResponse.setIdListaPrecios(Integer.valueOf(cliente.getListaPrecios().getId()));
/* 118:    */         }
/* 119:119 */         List<Subempresa> listaSubempresa = this.clienteDao.obtenerListaSubempresaBySubcliente(cliente.getEmpresa().getId());
/* 120:120 */         if (listaSubempresa.size() > 0)
/* 121:    */         {
/* 122:121 */           clienteResponse.setIdClientePadre(Integer.valueOf(((Subempresa)listaSubempresa.get(0)).getEmpresaPadre().getCliente().getId()));
/* 123:122 */           clienteResponse.setCodigoSubcliente(((Subempresa)listaSubempresa.get(0)).getCodigo());
/* 124:123 */           clienteResponse.setNombreSubcliente(((Subempresa)listaSubempresa.get(0)).getEmpresaFinal());
/* 125:    */         }
/* 126:125 */         List<DireccionEmpresa> listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(cliente.getEmpresa().getId());
/* 127:126 */         if (listaDireccionEmpresa.size() > 0) {
/* 128:127 */           clienteResponse.setDireccion(((DireccionEmpresa)listaDireccionEmpresa.get(0)).getDireccionCompleta());
/* 129:    */         }
/* 130:129 */         response.add(clienteResponse);
/* 131:    */       }
/* 132:132 */       return response;
/* 133:    */     }
/* 134:    */     catch (Exception e)
/* 135:    */     {
/* 136:134 */       e.printStackTrace();
/* 137:135 */       throw new AS2Exception(e.getMessage());
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   @POST
/* 142:    */   @Path("/buscarClientePorId")
/* 143:    */   @Consumes({"application/json"})
/* 144:    */   @Produces({"application/json"})
/* 145:    */   public List<ClienteResponseDto> buscarClientePorId(ClienteRequestDto request)
/* 146:    */     throws AS2Exception
/* 147:    */   {
/* 148:    */     try
/* 149:    */     {
/* 150:145 */       Cliente cliente = this.clienteDao.cargarDetalle(request.getIdCliente().intValue());
/* 151:146 */       if (cliente == null) {
/* 152:147 */         throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { request.getIdCliente().toString() });
/* 153:    */       }
/* 154:149 */       List<ClienteResponseDto> response = new ArrayList();
/* 155:150 */       ClienteResponseDto clienteResponse = new ClienteResponseDto();
/* 156:151 */       clienteResponse.setIdCliente(cliente.getId());
/* 157:152 */       clienteResponse.setIdEmpresa(Integer.valueOf(cliente.getEmpresa().getId()));
/* 158:153 */       clienteResponse.setIdentificacion(cliente.getEmpresa().getIdentificacion());
/* 159:154 */       clienteResponse.setTipoIdentificacion(cliente.getEmpresa().getTipoIdentificacion().getNombre());
/* 160:155 */       clienteResponse.setHorarioDespacho(cliente.getHorariosDespachos());
/* 161:156 */       clienteResponse.setHorarioVisita(cliente.getHorariosVisitas());
/* 162:157 */       clienteResponse.setIdOrganizacion(cliente.getEmpresa().getIdOrganizacion());
/* 163:158 */       clienteResponse.setNombreFiscal(cliente.getEmpresa().getNombreFiscal());
/* 164:159 */       if (cliente.getListaPrecios() != null) {
/* 165:160 */         clienteResponse.setIdListaPrecios(Integer.valueOf(cliente.getListaPrecios().getId()));
/* 166:    */       }
/* 167:162 */       List<Subempresa> listaSubempresa = this.clienteDao.obtenerListaSubempresaBySubcliente(cliente.getEmpresa().getId());
/* 168:163 */       if (listaSubempresa.size() > 0)
/* 169:    */       {
/* 170:164 */         clienteResponse.setIdClientePadre(Integer.valueOf(((Subempresa)listaSubempresa.get(0)).getEmpresaPadre().getCliente().getId()));
/* 171:165 */         clienteResponse.setCodigoSubcliente(((Subempresa)listaSubempresa.get(0)).getCodigo());
/* 172:166 */         clienteResponse.setNombreSubcliente(((Subempresa)listaSubempresa.get(0)).getEmpresaFinal());
/* 173:    */       }
/* 174:168 */       List<DireccionEmpresa> listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(cliente.getEmpresa().getId());
/* 175:169 */       if (listaDireccionEmpresa.size() > 0) {
/* 176:170 */         clienteResponse.setDireccion(((DireccionEmpresa)listaDireccionEmpresa.get(0)).getDireccionCompleta());
/* 177:    */       }
/* 178:173 */       response.add(clienteResponse);
/* 179:    */       
/* 180:175 */       return response;
/* 181:    */     }
/* 182:    */     catch (Exception e)
/* 183:    */     {
/* 184:177 */       e.printStackTrace();
/* 185:178 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { request.getIdCliente().toString() });
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   @POST
/* 190:    */   @Path("/consultarClientesPorOrganizacion")
/* 191:    */   @Consumes({"application/json"})
/* 192:    */   @Produces({"application/json"})
/* 193:    */   public List<ClienteResponseDto> consultarClientesPorOrganizacion(ListaClienteRequestDto request)
/* 194:    */     throws AS2Exception
/* 195:    */   {
/* 196:187 */     if (request.getListaClienteRequest() == null) {
/* 197:188 */       request.setListaClienteRequest(new ArrayList());
/* 198:    */     }
/* 199:    */     try
/* 200:    */     {
/* 201:191 */       List<Cliente> listaCliente = this.clienteDao.buscarClientesActivos(request.getIdOrganizacion());
/* 202:192 */       List<ClienteResponseDto> response = new ArrayList();
/* 203:194 */       for (Cliente cliente : listaCliente)
/* 204:    */       {
/* 205:195 */         ClienteResponseDto clienteResponse = new ClienteResponseDto();
/* 206:196 */         clienteResponse.setIdCliente(cliente.getId());
/* 207:197 */         clienteResponse.setIdEmpresa(Integer.valueOf(cliente.getEmpresa().getId()));
/* 208:198 */         clienteResponse.setIdentificacion(cliente.getEmpresa().getIdentificacion());
/* 209:199 */         clienteResponse.setTipoIdentificacion(cliente.getEmpresa().getTipoIdentificacion().getNombre());
/* 210:200 */         clienteResponse.setHorarioDespacho(cliente.getHorariosDespachos());
/* 211:201 */         clienteResponse.setHorarioVisita(cliente.getHorariosVisitas());
/* 212:202 */         clienteResponse.setIdOrganizacion(cliente.getEmpresa().getIdOrganizacion());
/* 213:203 */         clienteResponse.setNombreFiscal(cliente.getEmpresa().getNombreFiscal());
/* 214:204 */         clienteResponse.setNombreComercial(cliente.getEmpresa().getNombreComercial());
/* 215:205 */         clienteResponse.setIdSucursal(cliente.getEmpresa().getIdSucursal());
/* 216:206 */         clienteResponse.setEmail(cliente.getEmpresa().getEmail1());
/* 217:207 */         if (cliente.getListaPrecios() != null) {
/* 218:208 */           clienteResponse.setIdListaPrecios(Integer.valueOf(cliente.getListaPrecios().getId()));
/* 219:    */         }
/* 220:211 */         if (cliente.getListaDescuentos() != null) {
/* 221:212 */           clienteResponse.setIdListaDescuentos(Integer.valueOf(cliente.getListaDescuentos().getId()));
/* 222:    */         }
/* 223:215 */         List<Subempresa> listaSubempresa = this.clienteDao.obtenerListaSubempresaBySubcliente(cliente.getEmpresa().getId());
/* 224:216 */         if (listaSubempresa.size() > 0)
/* 225:    */         {
/* 226:217 */           clienteResponse.setIdClientePadre(Integer.valueOf(((Subempresa)listaSubempresa.get(0)).getEmpresaPadre().getCliente().getId()));
/* 227:218 */           clienteResponse.setCodigoSubcliente(((Subempresa)listaSubempresa.get(0)).getCodigo());
/* 228:219 */           clienteResponse.setNombreSubcliente(((Subempresa)listaSubempresa.get(0)).getEmpresaFinal());
/* 229:    */         }
/* 230:221 */         List<DireccionEmpresa> listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(cliente.getEmpresa().getId());
/* 231:222 */         if (listaDireccionEmpresa.size() > 0) {
/* 232:223 */           clienteResponse.setDireccion(((DireccionEmpresa)listaDireccionEmpresa.get(0)).getDireccionCompleta());
/* 233:    */         }
/* 234:226 */         for (DireccionEmpresa direccionEmpresa : listaDireccionEmpresa)
/* 235:    */         {
/* 236:227 */           DireccionEmpresaResponseDto responseDireccion = new DireccionEmpresaResponseDto();
/* 237:228 */           responseDireccion.setIdDireccionCliente(Integer.valueOf(direccionEmpresa.getIdDireccionEmpresa()));
/* 238:229 */           responseDireccion.setIdOrganizacion(direccionEmpresa.getIdOrganizacion());
/* 239:230 */           responseDireccion.setIdSucursal(direccionEmpresa.getIdSucursal());
/* 240:231 */           responseDireccion.setIndicadorDireccionPrincipal(direccionEmpresa.isIndicadorDireccionPrincipal());
/* 241:    */           
/* 242:    */ 
/* 243:234 */           ubicacion = direccionEmpresa.getUbicacion();
/* 244:    */           
/* 245:236 */           UbicacionResponseDto ubicacionResponse = new UbicacionResponseDto();
/* 246:237 */           ubicacionResponse.setIdOrganizacion(ubicacion.getIdOrganizacion());
/* 247:238 */           ubicacionResponse.setIdSucursal(ubicacion.getIdSucursal());
/* 248:239 */           ubicacionResponse.setIdUbicacion(Integer.valueOf(ubicacion.getIdUbicacion()));
/* 249:240 */           ubicacionResponse.setDireccion1(ubicacion.getDireccion1());
/* 250:241 */           ubicacionResponse.setDireccion2(ubicacion.getDireccion2());
/* 251:242 */           ubicacionResponse.setDireccion3(ubicacion.getDireccion3());
/* 252:243 */           ubicacionResponse.setDireccion4(ubicacion.getDireccion4());
/* 253:244 */           ubicacionResponse.setDireccion5(ubicacion.getDireccion5());
/* 254:    */           
/* 255:246 */           responseDireccion.setUbicacion(ubicacionResponse);
/* 256:    */           
/* 257:    */ 
/* 258:    */ 
/* 259:250 */           Ciudad ciudad = direccionEmpresa.getCiudad();
/* 260:    */           
/* 261:252 */           CiudadResponseDto ciudadResponse = new CiudadResponseDto();
/* 262:253 */           ciudadResponse.setIdOrganizacion(ciudad.getIdOrganizacion());
/* 263:254 */           ciudadResponse.setIdSucursal(ciudad.getIdCiudad());
/* 264:255 */           ciudadResponse.setIdCiudad(Integer.valueOf(ciudad.getIdCiudad()));
/* 265:256 */           ciudadResponse.setCodigo(ciudad.getCodigo());
/* 266:257 */           ciudadResponse.setNombre(ciudad.getNombre());
/* 267:258 */           ciudadResponse.setActivo(Boolean.valueOf(ciudad.isActivo()));
/* 268:259 */           ciudadResponse.setPredeterminado(Boolean.valueOf(ciudad.isPredeterminado()));
/* 269:    */           
/* 270:    */ 
/* 271:    */ 
/* 272:263 */           Provincia provincia = ciudad.getProvincia();
/* 273:264 */           ProvinciaResponseDto provinciaResponse = new ProvinciaResponseDto();
/* 274:    */           
/* 275:266 */           provinciaResponse.setIdOrganizacion(provincia.getIdOrganizacion());
/* 276:267 */           provinciaResponse.setIdSucursal(provincia.getIdSucursal());
/* 277:268 */           provinciaResponse.setIdProvincia(Integer.valueOf(provincia.getIdProvincia()));
/* 278:269 */           provinciaResponse.setCodigo(provincia.getCodigo());
/* 279:270 */           provinciaResponse.setNombre(provincia.getNombre());
/* 280:271 */           provinciaResponse.setActivo(Boolean.valueOf(provincia.isActivo()));
/* 281:272 */           provinciaResponse.setPredeterminado(Boolean.valueOf(provincia.isPredeterminado()));
/* 282:    */           
/* 283:    */ 
/* 284:275 */           Pais pais = provincia.getPais();
/* 285:276 */           PaisResponseDto paisResponse = new PaisResponseDto();
/* 286:277 */           paisResponse.setIdOrganizacion(pais.getIdOrganizacion());
/* 287:278 */           paisResponse.setIdSucursal(pais.getIdSucursal());
/* 288:279 */           paisResponse.setIdPais(Integer.valueOf(pais.getIdPais()));
/* 289:280 */           paisResponse.setCodigoIso(pais.getCodigoIso());
/* 290:281 */           paisResponse.setNombre(pais.getNombre());
/* 291:282 */           paisResponse.setActivo(Boolean.valueOf(pais.isActivo()));
/* 292:283 */           paisResponse.setPredeterminado(Boolean.valueOf(pais.isPredeterminado()));
/* 293:    */           
/* 294:285 */           provinciaResponse.setPaisResponse(paisResponse);
/* 295:286 */           ciudadResponse.setProvincia(provinciaResponse);
/* 296:287 */           responseDireccion.setCiudad(ciudadResponse);
/* 297:    */           
/* 298:289 */           clienteResponse.getListaDireccionEmpresa().add(responseDireccion);
/* 299:    */         }
/* 300:    */         Ubicacion ubicacion;
/* 301:293 */         TipoIdentificacion tipoIdentificacion = cliente.getEmpresa().getTipoIdentificacion();
/* 302:    */         
/* 303:295 */         TipoIdentificacionResponseDto tipoIdentificacionResponse = new TipoIdentificacionResponseDto();
/* 304:    */         
/* 305:297 */         tipoIdentificacionResponse.setIdOrganizacion(tipoIdentificacion.getIdOrganizacion());
/* 306:298 */         tipoIdentificacionResponse.setIdSucursal(tipoIdentificacion.getIdSucursal());
/* 307:299 */         tipoIdentificacionResponse.setIdTipoIdentificacion(tipoIdentificacion.getIdTipoIdentificacion());
/* 308:300 */         tipoIdentificacionResponse.setCodigo(tipoIdentificacion.getCodigo());
/* 309:301 */         tipoIdentificacionResponse.setNombre(tipoIdentificacion.getNombre());
/* 310:302 */         tipoIdentificacionResponse.setActivo(Boolean.valueOf(tipoIdentificacion.isActivo()));
/* 311:303 */         tipoIdentificacionResponse.setPredeterminado(Boolean.valueOf(tipoIdentificacion.isPredeterminado()));
/* 312:304 */         tipoIdentificacionResponse.setDescripcion(tipoIdentificacion.getDescripcion());
/* 313:305 */         tipoIdentificacionResponse.setIndicadorValidarIdentificacion(Boolean.valueOf(tipoIdentificacion.isIndicadorValidarIdentificacion()));
/* 314:    */         
/* 315:307 */         clienteResponse.setTipoIdentificacionResponse(tipoIdentificacionResponse);
/* 316:    */         
/* 317:309 */         boolean encontre = false;
/* 318:310 */         for (ClienteRequestDto clienteRequest : request.getListaClienteRequest()) {
/* 319:311 */           if (clienteResponse.getIdCliente() == clienteRequest.getIdCliente().intValue())
/* 320:    */           {
/* 321:312 */             encontre = true;
/* 322:313 */             clienteRequest.setRevisado(Boolean.valueOf(true));
/* 323:314 */             if (clienteResponse.getHashCode() == clienteRequest.getHashCode().intValue()) {
/* 324:    */               break;
/* 325:    */             }
/* 326:315 */             response.add(clienteResponse); break;
/* 327:    */           }
/* 328:    */         }
/* 329:320 */         if (!encontre) {
/* 330:321 */           response.add(clienteResponse);
/* 331:    */         }
/* 332:    */       }
/* 333:325 */       for (ClienteRequestDto clienteRequest : request.getListaClienteRequest()) {
/* 334:326 */         if (!clienteRequest.getRevisado().booleanValue())
/* 335:    */         {
/* 336:327 */           ClienteResponseDto clienteResponse = new ClienteResponseDto();
/* 337:328 */           clienteResponse.setIdCliente(clienteRequest.getIdCliente().intValue());
/* 338:329 */           clienteResponse.setActivo(false);
/* 339:330 */           response.add(clienteResponse);
/* 340:    */         }
/* 341:    */       }
/* 342:334 */       return response;
/* 343:    */     }
/* 344:    */     catch (Exception e)
/* 345:    */     {
/* 346:336 */       e.printStackTrace();
/* 347:337 */       throw new AS2Exception(e.getMessage());
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   @POST
/* 352:    */   @Path("/crearActualizarClientes")
/* 353:    */   @Consumes({"application/json"})
/* 354:    */   @Produces({"application/json"})
/* 355:    */   public ProcesosResponseDto crearActualizarClientes(EmpresaResponseDto clienteResponse)
/* 356:    */     throws AS2Exception
/* 357:    */   {
/* 358:347 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 359:348 */     String error = "";
/* 360:    */     try
/* 361:    */     {
/* 362:350 */       Integer idEmpresa = clienteResponse.getIdEmpresa();
/* 363:351 */       Empresa empresa = null;
/* 364:352 */       boolean nueva = (idEmpresa == null) || (idEmpresa.equals(Integer.valueOf(0)));
/* 365:354 */       if ((idEmpresa == null) || (idEmpresa.equals(Integer.valueOf(0))))
/* 366:    */       {
/* 367:356 */         Map<String, String> filtroEmpresa = new HashMap();
/* 368:357 */         filtroEmpresa.put("idOrganizacion", "" + clienteResponse.getIdOrganizacion());
/* 369:358 */         filtroEmpresa.put("idDispositivoSincronizacion", "" + clienteResponse.getIdDispositivoSincronizacion());
/* 370:359 */         filtroEmpresa.put("identificacion", "=" + clienteResponse.getIdentificacion());
/* 371:360 */         List<Empresa> listaEmpresa = this.servicioEmpresa.obtenerListaCombo("idDispositivoSincronizacion", true, filtroEmpresa);
/* 372:361 */         if (listaEmpresa.isEmpty())
/* 373:    */         {
/* 374:362 */           empresa = new Empresa();
/* 375:    */         }
/* 376:    */         else
/* 377:    */         {
/* 378:364 */           empresa = this.servicioEmpresa.cargarDetalle((Empresa)listaEmpresa.get(0));
/* 379:365 */           nueva = false;
/* 380:    */         }
/* 381:    */       }
/* 382:    */       else
/* 383:    */       {
/* 384:368 */         empresa = this.servicioEmpresa.buscarPorId(clienteResponse.getIdEmpresa());
/* 385:369 */         empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 386:    */       }
/* 387:372 */       empresa.setIdOrganizacion(clienteResponse.getIdOrganizacion());
/* 388:373 */       empresa.setIdSucursal(clienteResponse.getIdSucursal());
/* 389:374 */       empresa.setCodigo(clienteResponse.getIdentificacion());
/* 390:375 */       empresa.setNombreComercial(clienteResponse.getNombreComercial());
/* 391:376 */       empresa.setNombreFiscal(clienteResponse.getNombreFiscal());
/* 392:377 */       empresa.setActivo(clienteResponse.isActivo());
/* 393:378 */       empresa.setIndicadorCliente(true);
/* 394:379 */       empresa.setIdentificacion(clienteResponse.getIdentificacion());
/* 395:380 */       empresa.setIdDispositivoSincronizacion(clienteResponse.getIdDispositivoSincronizacion());
/* 396:381 */       empresa.setEmail1(clienteResponse.getEmail());
/* 397:382 */       empresa.setLatitud(clienteResponse.getLatitud());
/* 398:383 */       empresa.setLongitud(clienteResponse.getLongitud());
/* 399:385 */       if ((clienteResponse.getTipoEmpresa() == null) && (nueva)) {
/* 400:386 */         empresa.setTipoEmpresa(TipoEmpresa.NATURAL);
/* 401:387 */       } else if (clienteResponse.getTipoEmpresa() != null) {
/* 402:388 */         empresa.setTipoEmpresa(clienteResponse.getTipoEmpresa());
/* 403:    */       }
/* 404:391 */       TipoIdentificacionResponseDto tipoIdentificacionResponse = clienteResponse.getTipoIdentificacionResponse();
/* 405:    */       
/* 406:393 */       TipoIdentificacion tipoIdentificacion = null;
/* 407:394 */       Map<String, String> filtrosTipoIdentificacion = new HashMap();
/* 408:395 */       filtrosTipoIdentificacion.put("idOrganizacion", tipoIdentificacionResponse.getIdOrganizacion() + "");
/* 409:396 */       filtrosTipoIdentificacion.put("idTipoIdentificacion", tipoIdentificacionResponse.getIdTipoIdentificacion() + "");
/* 410:    */       
/* 411:398 */       List<TipoIdentificacion> listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("codigo", false, filtrosTipoIdentificacion);
/* 412:400 */       if (listaTipoIdentificacion.size() > 0) {
/* 413:401 */         tipoIdentificacion = (TipoIdentificacion)listaTipoIdentificacion.get(0);
/* 414:    */       }
/* 415:403 */       empresa.setTipoIdentificacion(tipoIdentificacion);
/* 416:405 */       if (empresa.getCliente() == null)
/* 417:    */       {
/* 418:407 */         Cliente cliente = new Cliente();
/* 419:408 */         cliente.setIdOrganizacion(clienteResponse.getIdOrganizacion());
/* 420:409 */         cliente.setIdSucursal(clienteResponse.getIdSucursal());
/* 421:410 */         cliente.setEmpresa(empresa);
/* 422:411 */         cliente.setNumeroCuotas(1);
/* 423:412 */         cliente.setMetodoFacturacion(MetodoFacturacionEnum.PEDIDO_FACTURA_DESPACHO);
/* 424:413 */         cliente.setTipoCliente(TipoEmpresaEnum.AMBOS);
/* 425:414 */         empresa.setCliente(cliente);
/* 426:    */       }
/* 427:418 */       Map<String, String> filters = new HashMap();
/* 428:419 */       filters.put("predeterminado", "true");
/* 429:420 */       filters.put("idOrganizacion", "" + clienteResponse.getIdOrganizacion());
/* 430:    */       
/* 431:    */ 
/* 432:423 */       CategoriaEmpresa categoriaEmpresa = null;
/* 433:424 */       if (clienteResponse.getCategoriaEmpresa() == null)
/* 434:    */       {
/* 435:425 */         List<CategoriaEmpresa> listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", false, filters);
/* 436:426 */         if (!listaCategoriaEmpresa.isEmpty()) {
/* 437:427 */           categoriaEmpresa = (CategoriaEmpresa)listaCategoriaEmpresa.get(0);
/* 438:    */         }
/* 439:    */       }
/* 440:    */       else
/* 441:    */       {
/* 442:430 */         categoriaEmpresa = this.servicioCategoriaEmpresa.buscarPorId(clienteResponse.getCategoriaEmpresa().getIdCatalogo());
/* 443:    */       }
/* 444:433 */       if (categoriaEmpresa == null) {
/* 445:434 */         throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { CategoriaEmpresa.class.getSimpleName() });
/* 446:    */       }
/* 447:436 */       empresa.setCategoriaEmpresa(categoriaEmpresa);
/* 448:    */       
/* 449:    */ 
/* 450:    */ 
/* 451:440 */       List<CondicionPago> listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("nombre", false, filters);
/* 452:441 */       if (listaCondicionPago.isEmpty()) {
/* 453:442 */         throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { CondicionPago.class.getSimpleName() });
/* 454:    */       }
/* 455:444 */       empresa.getCliente().setCondicionPago((CondicionPago)listaCondicionPago.get(0));
/* 456:    */       
/* 457:    */ 
/* 458:447 */       List<FormaPago> listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", false, filters);
/* 459:448 */       if (listaFormaPago.isEmpty()) {
/* 460:449 */         throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { FormaPago.class.getSimpleName() });
/* 461:    */       }
/* 462:451 */       empresa.getCliente().setFormaPago((FormaPago)listaFormaPago.get(0));
/* 463:454 */       if (empresa.getCliente().getListaPrecios() == null)
/* 464:    */       {
/* 465:455 */         Map<String, String> filtersListaPrecio = new HashMap();
/* 466:456 */         filtersListaPrecio.put("idOrganizacion", "" + clienteResponse.getIdOrganizacion());
/* 467:457 */         if ((clienteResponse.getIdListaPrecios() != null) && (!clienteResponse.getIdListaPrecios().equals(Integer.valueOf(0)))) {
/* 468:458 */           filtersListaPrecio.put("idListaPrecios", clienteResponse.getIdListaPrecios() + "");
/* 469:    */         } else {
/* 470:460 */           filtersListaPrecio.put("predeterminado", "true");
/* 471:    */         }
/* 472:462 */         filtersListaPrecio.put("indicadorVenta", "true");
/* 473:463 */         List<ListaPrecios> listaListaPrecios = this.servicioListaPrecios.obtenerListaCombo("nombre", true, filtersListaPrecio);
/* 474:464 */         if (listaListaPrecios.isEmpty()) {
/* 475:465 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { ListaPrecios.class.getSimpleName() });
/* 476:    */         }
/* 477:467 */         empresa.getCliente().setListaPrecios((ListaPrecios)listaListaPrecios.get(0));
/* 478:    */         
/* 479:469 */         filtersListaPrecio.remove("indicadorVenta");
/* 480:    */       }
/* 481:471 */       if (empresa.getCliente().getListaDescuentos() == null)
/* 482:    */       {
/* 483:472 */         Map<String, String> filtersListaDescuento = new HashMap();
/* 484:473 */         filtersListaDescuento.put("idOrganizacion", "" + clienteResponse.getIdOrganizacion());
/* 485:474 */         if ((clienteResponse.getIdListaDescuentos() != null) && (!clienteResponse.getIdListaDescuentos().equals(Integer.valueOf(0)))) {
/* 486:475 */           filtersListaDescuento.put("idListaDescuentos", clienteResponse.getIdListaDescuentos() + "");
/* 487:    */         } else {
/* 488:477 */           filtersListaDescuento.put("predeterminado", "true");
/* 489:    */         }
/* 490:479 */         listaListaDescuentos = this.servicioListaDescuentos.obtenerListaCombo("nombre", true, filtersListaDescuento);
/* 491:480 */         if (listaListaDescuentos.isEmpty()) {
/* 492:481 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { ListaDescuentos.class.getSimpleName() });
/* 493:    */         }
/* 494:483 */         empresa.getCliente().setListaDescuentos((ListaDescuentos)listaListaDescuentos.get(0));
/* 495:    */       }
/* 496:488 */       List<DireccionEmpresaResponseDto> listaDireccionEmpresaResponse = clienteResponse.getListaDireccionEmpresa();
/* 497:490 */       for (DireccionEmpresaResponseDto direccionEmpresaResponse : listaDireccionEmpresaResponse)
/* 498:    */       {
/* 499:491 */         direccionEmpresa = null;
/* 500:492 */         Integer idDireccionEmpresa = direccionEmpresaResponse.getIdDireccionCliente();
/* 501:494 */         if ((idDireccionEmpresa == null) || (idDireccionEmpresa.equals(Integer.valueOf(0))))
/* 502:    */         {
/* 503:495 */           direccionEmpresa = new DireccionEmpresa();
/* 504:    */         }
/* 505:    */         else
/* 506:    */         {
/* 507:497 */           Map<String, String> filtros = new HashMap();
/* 508:498 */           filtros.put("idOrganizacion", direccionEmpresaResponse.getIdOrganizacion() + "");
/* 509:499 */           filtros.put("idDireccionEmpresa", direccionEmpresaResponse.getIdDireccionCliente() + "");
/* 510:    */           
/* 511:501 */           List<DireccionEmpresa> listaDireccionEmpresa = this.servicioDireccionEmpresa.obtenerListaCombo(DireccionEmpresa.class, "activo", false, filtros);
/* 512:503 */           if (listaDireccionEmpresa.size() > 0) {
/* 513:504 */             direccionEmpresa = (DireccionEmpresa)listaDireccionEmpresa.get(0);
/* 514:    */           }
/* 515:    */         }
/* 516:507 */         direccionEmpresa.setIdOrganizacion(direccionEmpresaResponse.getIdOrganizacion());
/* 517:508 */         direccionEmpresa.setIdSucursal(direccionEmpresaResponse.getIdSucursal());
/* 518:509 */         direccionEmpresa.setActivo(direccionEmpresaResponse.isActivo());
/* 519:510 */         direccionEmpresa.setIndicadorDireccionPrincipal(true);
/* 520:511 */         direccionEmpresa.setIdDispositivoSincronizacion(direccionEmpresaResponse.getIdDispositivoSincronizacion());
/* 521:    */         
/* 522:513 */         UbicacionResponseDto ubicacionResponse = direccionEmpresaResponse.getUbicacion();
/* 523:    */         
/* 524:515 */         Ubicacion ubicacion = null;
/* 525:516 */         Integer idUbicacion = ubicacionResponse.getIdUbicacion();
/* 526:518 */         if ((idUbicacion == null) || (idUbicacion.equals(Integer.valueOf(0))))
/* 527:    */         {
/* 528:519 */           ubicacion = new Ubicacion();
/* 529:    */         }
/* 530:    */         else
/* 531:    */         {
/* 532:521 */           Map<String, String> filtros = new HashMap();
/* 533:522 */           filtros.put("idOrganizacion", ubicacionResponse.getIdOrganizacion() + "");
/* 534:523 */           filtros.put("idUbicacion", ubicacionResponse.getIdUbicacion() + "");
/* 535:524 */           List<Ubicacion> listaUbicacion = this.servicioUbicacion.obtenerListaCombo(Ubicacion.class, "direccion1", false, filtros);
/* 536:525 */           if (listaUbicacion.size() > 0) {
/* 537:526 */             ubicacion = (Ubicacion)listaUbicacion.get(0);
/* 538:    */           }
/* 539:    */         }
/* 540:530 */         ubicacion.setIdOrganizacion(ubicacionResponse.getIdOrganizacion());
/* 541:531 */         ubicacion.setIdSucursal(ubicacionResponse.getIdSucursal());
/* 542:532 */         ubicacion.setDireccion1(ubicacionResponse.getDireccion1());
/* 543:533 */         ubicacion.setDireccion2(ubicacionResponse.getDireccion2());
/* 544:534 */         ubicacion.setDireccion3(ubicacionResponse.getDireccion3());
/* 545:535 */         ubicacion.setDireccion4(ubicacionResponse.getDireccion4());
/* 546:536 */         ubicacion.setDireccion5(ubicacionResponse.getDireccion5());
/* 547:    */         
/* 548:538 */         this.servicioUbicacion.guardar(ubicacion);
/* 549:539 */         ubicacionResponse.setIdUbicacion(Integer.valueOf(ubicacion.getId()));
/* 550:    */         
/* 551:541 */         direccionEmpresa.setUbicacion(ubicacion);
/* 552:    */         
/* 553:543 */         CiudadResponseDto ciudadResponse = direccionEmpresaResponse.getCiudad();
/* 554:544 */         Integer idCiudad = ciudadResponse.getIdCiudad();
/* 555:545 */         Ciudad ciudad = null;
/* 556:547 */         if ((idCiudad == null) || (idCiudad.equals(Integer.valueOf(0))))
/* 557:    */         {
/* 558:548 */           ciudad = new Ciudad();
/* 559:549 */           ciudad.setIdOrganizacion(ciudadResponse.getIdOrganizacion());
/* 560:550 */           ciudad.setIdSucursal(ciudadResponse.getIdSucursal());
/* 561:551 */           ciudad.setCodigo(ciudadResponse.getCodigo());
/* 562:552 */           ciudad.setCodigoPostal(ciudadResponse.getCodigoPostal());
/* 563:553 */           ciudad.setNombre(ciudadResponse.getNombre());
/* 564:554 */           ciudad.setActivo(ciudadResponse.getActivo().booleanValue());
/* 565:555 */           ciudad.setPredeterminado(ciudadResponse.getPredeterminado().booleanValue());
/* 566:    */           
/* 567:557 */           ProvinciaResponseDto provinciaResponse = ciudadResponse.getProvincia();
/* 568:558 */           Integer idProvincia = provinciaResponse.getIdProvincia();
/* 569:    */           
/* 570:560 */           Provincia provincia = null;
/* 571:561 */           if ((idProvincia == null) || (idProvincia.equals(Integer.valueOf(0))))
/* 572:    */           {
/* 573:562 */             provincia = new Provincia();
/* 574:    */             
/* 575:564 */             provincia.setIdOrganizacion(provinciaResponse.getIdOrganizacion());
/* 576:565 */             provincia.setIdSucursal(provinciaResponse.getIdSucursal());
/* 577:566 */             provincia.setCodigo(provinciaResponse.getCodigo());
/* 578:567 */             provincia.setNombre(provinciaResponse.getNombre());
/* 579:568 */             provincia.setActivo(provinciaResponse.getActivo().booleanValue());
/* 580:569 */             provincia.setPredeterminado(provinciaResponse.getPredeterminado().booleanValue());
/* 581:    */             
/* 582:571 */             PaisResponseDto paisResponse = provinciaResponse.getPaisResponse();
/* 583:572 */             Integer idPais = paisResponse.getIdPais();
/* 584:573 */             Pais pais = null;
/* 585:574 */             if ((idPais == null) || (idPais.equals(Integer.valueOf(0))))
/* 586:    */             {
/* 587:575 */               pais = new Pais();
/* 588:576 */               pais.setIdOrganizacion(paisResponse.getIdOrganizacion());
/* 589:577 */               pais.setIdSucursal(paisResponse.getIdSucursal());
/* 590:578 */               pais.setCodigo(paisResponse.getCodigo());
/* 591:579 */               pais.setCodigoIso(paisResponse.getCodigoIso());
/* 592:580 */               pais.setNombre(paisResponse.getNombre());
/* 593:581 */               pais.setActivo(paisResponse.getActivo().booleanValue());
/* 594:582 */               pais.setPredeterminado(paisResponse.getPredeterminado().booleanValue());
/* 595:583 */               this.servicioPais.guardar(pais);
/* 596:584 */               paisResponse.setIdPais(Integer.valueOf(pais.getIdPais()));
/* 597:    */             }
/* 598:    */             else
/* 599:    */             {
/* 600:586 */               pais = this.servicioPais.buscarPorId(paisResponse.getIdPais());
/* 601:    */             }
/* 602:588 */             provincia.setPais(pais);
/* 603:589 */             this.servicioProvincia.guardar(provincia);
/* 604:590 */             provinciaResponse.setIdProvincia(Integer.valueOf(provincia.getIdProvincia()));
/* 605:    */           }
/* 606:    */           else
/* 607:    */           {
/* 608:592 */             provincia = this.servicioProvincia.buscarPorId(provinciaResponse.getIdProvincia());
/* 609:    */           }
/* 610:594 */           ciudad.setProvincia(provincia);
/* 611:595 */           this.servicioCiudad.guardar(ciudad);
/* 612:596 */           ciudadResponse.setIdCiudad(Integer.valueOf(ciudad.getId()));
/* 613:    */         }
/* 614:    */         else
/* 615:    */         {
/* 616:598 */           ciudad = this.servicioCiudad.buscarPorId(ciudadResponse.getIdCiudad().intValue());
/* 617:    */         }
/* 618:601 */         direccionEmpresa.setCiudad(ciudad);
/* 619:602 */         direccionEmpresa.setEmpresa(empresa);
/* 620:    */         
/* 621:604 */         empresa.setDirecciones(new ArrayList());
/* 622:605 */         empresa.getDirecciones().add(direccionEmpresa);
/* 623:    */       }
/* 624:    */       DireccionEmpresa direccionEmpresa;
/* 625:608 */       this.servicioEmpresa.guardar(empresa);
/* 626:609 */       clienteResponse.setIdEmpresa(Integer.valueOf(empresa.getId()));
/* 627:610 */       clienteResponse.setIdCliente(empresa.getCliente().getId());
/* 628:611 */       for (List<ListaDescuentos> listaListaDescuentos = empresa.getDirecciones().iterator(); listaListaDescuentos.hasNext();)
/* 629:    */       {
/* 630:611 */         direccionEmpresa = (DireccionEmpresa)listaListaDescuentos.next();
/* 631:612 */         for (DireccionEmpresaResponseDto direccionEmpresaResponseDto : listaDireccionEmpresaResponse) {
/* 632:613 */           if (direccionEmpresa.getIdDispositivoSincronizacion().equals(direccionEmpresaResponseDto.getIdDispositivoSincronizacion()))
/* 633:    */           {
/* 634:614 */             direccionEmpresaResponseDto.setIdDireccionCliente(Integer.valueOf(direccionEmpresa.getId()));
/* 635:615 */             break;
/* 636:    */           }
/* 637:    */         }
/* 638:    */       }
/* 639:    */       DireccionEmpresa direccionEmpresa;
/* 640:619 */       response.setSuccsess(true);
/* 641:620 */       response.setResponse(clienteResponse);
/* 642:621 */       return response;
/* 643:    */     }
/* 644:    */     catch (ExcepcionAS2Identification e)
/* 645:    */     {
/* 646:623 */       e.printStackTrace();
/* 647:624 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 648:    */     }
/* 649:    */     catch (ExcepcionAS2 e)
/* 650:    */     {
/* 651:626 */       e.printStackTrace();
/* 652:627 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 653:    */     }
/* 654:    */     catch (Exception e)
/* 655:    */     {
/* 656:629 */       e.printStackTrace();
/* 657:630 */       error = e.getMessage();
/* 658:    */     }
/* 659:632 */     response.setError(error);
/* 660:633 */     response.setSuccsess(false);
/* 661:634 */     return response;
/* 662:    */   }
/* 663:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.ClienteServicioRest
 * JD-Core Version:    0.7.0.1
 */