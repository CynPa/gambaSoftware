/*   1:    */ package com.asinfo.as2.rs.seguridad.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  14:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  19:    */ import com.asinfo.as2.rs.datosbase.dto.BodegaResponseDto;
/*  20:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  21:    */ import com.asinfo.as2.rs.seguridad.dto.ConsultarJerarquiaUsuarioRequestDto;
/*  22:    */ import com.asinfo.as2.rs.seguridad.dto.ConsultarJerarquiaUsuarioResponseDto;
/*  23:    */ import com.asinfo.as2.rs.seguridad.dto.ConsultarSistemaRequestDto;
/*  24:    */ import com.asinfo.as2.rs.seguridad.dto.ConsultarUsuarioRequestDto;
/*  25:    */ import com.asinfo.as2.rs.seguridad.dto.ConsultarUsuarioResponseDto;
/*  26:    */ import com.asinfo.as2.rs.seguridad.dto.OrganizacionResponseDto;
/*  27:    */ import com.asinfo.as2.rs.seguridad.dto.PuntoVentaResponseDto;
/*  28:    */ import com.asinfo.as2.rs.seguridad.dto.SucursalResponseDto;
/*  29:    */ import com.asinfo.as2.seguridad.ServicioSistema;
/*  30:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  31:    */ import com.asinfo.as2.seguridad.modelo.Rol;
/*  32:    */ import com.asinfo.as2.utils.NodoArbol;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.HashMap;
/*  35:    */ import java.util.Iterator;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.ws.rs.Consumes;
/*  40:    */ import javax.ws.rs.POST;
/*  41:    */ import javax.ws.rs.Path;
/*  42:    */ import javax.ws.rs.Produces;
/*  43:    */ 
/*  44:    */ @Path("/seguridad")
/*  45:    */ public class SeguridadServicioRest
/*  46:    */ {
/*  47:    */   @EJB
/*  48:    */   private transient UsuarioDao usuarioDao;
/*  49:    */   @EJB
/*  50:    */   private transient ServicioUsuario servicioUsuario;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioSistema servicioSistema;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  55:    */   @EJB
/*  56:    */   private transient ServicioSucursal servicioSucursal;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioBodega servicioBodega;
/*  61: 65 */   private LanguageController languageController = new LanguageController();
/*  62:    */   
/*  63:    */   public LanguageController getLanguageController()
/*  64:    */   {
/*  65: 68 */     return this.languageController;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setLanguageController(LanguageController languageController)
/*  69:    */   {
/*  70: 72 */     this.languageController = languageController;
/*  71:    */   }
/*  72:    */   
/*  73:    */   @POST
/*  74:    */   @Path("/consultarUsuario")
/*  75:    */   @Consumes({"application/json"})
/*  76:    */   @Produces({"application/json"})
/*  77:    */   public ConsultarUsuarioResponseDto consultarUsuario(ConsultarUsuarioRequestDto request)
/*  78:    */     throws AS2Exception
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82: 81 */       EntidadSistema sistema = this.servicioSistema.buscarPorNombre(request.getSistema());
/*  83: 82 */       EntidadUsuario entidadUsuario = this.usuarioDao.buscarPorNombreUsuario(request.getUsuario(), sistema);
/*  84: 83 */       if (entidadUsuario == null) {
/*  85: 84 */         throw new AS2Exception("El usuario con el nombre '" + request.getUsuario() + "' no encontrado.");
/*  86:    */       }
/*  87: 85 */       if ((!entidadUsuario.isActivo()) || (!entidadUsuario.getClave().equals(request.getClave()))) {
/*  88: 86 */         throw new AS2Exception("La clave es incorrecta o el usuario no es activo");
/*  89:    */       }
/*  90: 88 */       entidadUsuario.setEnlinea(true);
/*  91:    */       try
/*  92:    */       {
/*  93: 90 */         this.usuarioDao.actualizar(entidadUsuario);
/*  94:    */       }
/*  95:    */       catch (Exception e)
/*  96:    */       {
/*  97: 92 */         throw new AS2Exception(e.getMessage());
/*  98:    */       }
/*  99: 95 */       ConsultarUsuarioResponseDto response = new ConsultarUsuarioResponseDto();
/* 100: 96 */       response.setIdUsuario(entidadUsuario.getId());
/* 101: 97 */       response.setNombreUsuario(entidadUsuario.getNombreUsuario());
/* 102: 98 */       response.setNombre1(entidadUsuario.getNombre1());
/* 103: 99 */       response.setNombre2(entidadUsuario.getNombre2());
/* 104:100 */       response.setTipoVisualizacion(entidadUsuario.getTipoVisualizacion());
/* 105:101 */       response.setIndicadorAdministrador(Boolean.valueOf(entidadUsuario.isIndicadorAdministrador()));
/* 106:102 */       response.setEmail(entidadUsuario.getEmail());
/* 107:103 */       cargarListaRoles(entidadUsuario, response);
/* 108:104 */       cargarListaOrganizacion(entidadUsuario, response, sistema);
/* 109:    */       
/* 110:106 */       return response;
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:108 */       e.printStackTrace();
/* 115:109 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { request.getUsuario() });
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private void cargarListaRoles(EntidadUsuario entidadUsuario, ConsultarUsuarioResponseDto response)
/* 120:    */   {
/* 121:119 */     List<Rol> listaRol = new ArrayList();
/* 122:120 */     for (EntidadRol er : entidadUsuario.getListaRol()) {
/* 123:121 */       listaRol.add(new Rol(er));
/* 124:    */     }
/* 125:123 */     response.setListaRol(listaRol);
/* 126:    */   }
/* 127:    */   
/* 128:    */   private void cargarListaOrganizacion(EntidadUsuario entidadUsuario, ConsultarUsuarioResponseDto response, EntidadSistema entidadSistema)
/* 129:    */   {
/* 130:132 */     response.setListaOrganizacion(new ArrayList());
/* 131:133 */     for (Organizacion orgz : this.servicioOrganizacion.obtenerOrganizacionPorUsuario(entidadUsuario.getId(), entidadSistema))
/* 132:    */     {
/* 133:134 */       OrganizacionResponseDto orgdto = new OrganizacionResponseDto();
/* 134:135 */       orgdto.setIdentificacion(orgz.getIdentificacion());
/* 135:136 */       orgdto.setIdOrganizacion(Integer.valueOf(orgz.getId()));
/* 136:137 */       orgdto.setRazonSocial(orgz.getRazonSocial());
/* 137:138 */       orgdto.setListaSucursal(new ArrayList());
/* 138:140 */       for (Sucursal suc : this.servicioSucursal.obtenerListaComboPorUsuario(entidadUsuario.getId(), orgz.getId()))
/* 139:    */       {
/* 140:141 */         SucursalResponseDto sucdto = new SucursalResponseDto();
/* 141:142 */         sucdto.setCodigo(suc.getCodigo());
/* 142:143 */         sucdto.setIdSucursal(Integer.valueOf(suc.getId()));
/* 143:144 */         sucdto.setNombre(suc.getNombre());
/* 144:145 */         sucdto.setListaPuntoVenta(new ArrayList());
/* 145:    */         
/* 146:147 */         Map<String, String> filters = new HashMap();
/* 147:148 */         filters.put("idOrganizacion", String.valueOf(suc.getIdOrganizacion()));
/* 148:149 */         filters.put("sucursal.idSucursal", String.valueOf(suc.getId()));
/* 149:150 */         for (Iterator localIterator3 = this.servicioPuntoDeVenta.obtenerListaCombo("predeterminado", false, filters).iterator(); localIterator3.hasNext();)
/* 150:    */         {
/* 151:150 */           pto = (PuntoDeVenta)localIterator3.next();
/* 152:151 */           PuntoVentaResponseDto ptodto = new PuntoVentaResponseDto();
/* 153:152 */           ptodto.setCodigo(pto.getCodigo());
/* 154:153 */           ptodto.setIdPuntoVenta(Integer.valueOf(pto.getId()));
/* 155:154 */           ptodto.setNombre(pto.getNombre());
/* 156:155 */           sucdto.getListaPuntoVenta().add(ptodto);
/* 157:    */         }
/* 158:    */         PuntoDeVenta pto;
/* 159:158 */         Object listaBodega = this.servicioBodega.obtenerListaComboPorUsuario(entidadUsuario.getId(), orgz.getId(), suc.getId());
/* 160:159 */         for (Bodega bodega : (List)listaBodega)
/* 161:    */         {
/* 162:160 */           BodegaResponseDto bodegaResponse = new BodegaResponseDto();
/* 163:161 */           bodegaResponse.setActivo(Boolean.valueOf(bodega.isActivo()));
/* 164:162 */           bodegaResponse.setCodigo(bodega.getCodigo());
/* 165:163 */           bodegaResponse.setIdBodega(Integer.valueOf(bodega.getId()));
/* 166:164 */           bodegaResponse.setIdOrganizacion(Integer.valueOf(orgz.getId()));
/* 167:165 */           bodegaResponse.setIdSucursal(Integer.valueOf(suc.getId()));
/* 168:166 */           bodegaResponse.setNombre(bodega.getNombre());
/* 169:167 */           bodegaResponse.setPredeterminado(Boolean.valueOf(bodega.isPredeterminado()));
/* 170:168 */           sucdto.getListaBodega().add(bodegaResponse);
/* 171:    */         }
/* 172:171 */         orgdto.getListaSucursal().add(sucdto);
/* 173:    */       }
/* 174:173 */       response.getListaOrganizacion().add(orgdto);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   @POST
/* 179:    */   @Path("/consultarUsuariosPorSistema")
/* 180:    */   @Consumes({"application/json"})
/* 181:    */   @Produces({"application/json"})
/* 182:    */   public List<ConsultarUsuarioResponseDto> cargarListaUsuariosPorSistema(ConsultarSistemaRequestDto consultarSistemaRequestDto)
/* 183:    */     throws AS2Exception
/* 184:    */   {
/* 185:    */     try
/* 186:    */     {
/* 187:183 */       List<ConsultarUsuarioResponseDto> listaResponse = new ArrayList();
/* 188:184 */       List<EntidadUsuario> listaEntidadUsuario = this.servicioUsuario.buscarUsuarioPorSistemaYOrganizacion(consultarSistemaRequestDto.getSistema(), consultarSistemaRequestDto
/* 189:185 */         .getIdOrganizacion());
/* 190:186 */       for (EntidadUsuario entidadUsuario : listaEntidadUsuario)
/* 191:    */       {
/* 192:187 */         ConsultarUsuarioResponseDto response = new ConsultarUsuarioResponseDto();
/* 193:188 */         response.setIdUsuario(entidadUsuario.getId());
/* 194:189 */         response.setNombreUsuario(entidadUsuario.getNombreUsuario());
/* 195:190 */         response.setNombre1(entidadUsuario.getNombre1());
/* 196:191 */         response.setNombre2(entidadUsuario.getNombre2());
/* 197:192 */         response.setTipoVisualizacion(entidadUsuario.getTipoVisualizacion());
/* 198:193 */         response.setEmail(entidadUsuario.getEmail());
/* 199:194 */         listaResponse.add(response);
/* 200:    */       }
/* 201:197 */       return listaResponse;
/* 202:    */     }
/* 203:    */     catch (Exception e)
/* 204:    */     {
/* 205:199 */       e.printStackTrace();
/* 206:200 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_202", new String[] { e.getMessage() });
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   @POST
/* 211:    */   @Path("/consultarPermisosUsuario")
/* 212:    */   @Consumes({"application/json"})
/* 213:    */   @Produces({"application/json"})
/* 214:    */   public List<ConsultarUsuarioResponseDto> consultarPermisosUsuario(ConsultarUsuarioRequestDto request)
/* 215:    */     throws AS2Exception
/* 216:    */   {
/* 217:    */     try
/* 218:    */     {
/* 219:210 */       EntidadSistema sistema = null;
/* 220:211 */       if ((request.getSistema() != null) && (!request.getSistema().trim().isEmpty())) {
/* 221:212 */         sistema = this.servicioSistema.buscarPorNombre(request.getSistema());
/* 222:    */       }
/* 223:214 */       List<ConsultarUsuarioResponseDto> listaResponse = new ArrayList();
/* 224:215 */       List<EntidadUsuario> listaEntidadUsuario = this.servicioUsuario.buscarUsuarioPorSistemaYOrganizacion(request.getSistema(), request
/* 225:216 */         .getIdOrganizacion());
/* 226:218 */       for (EntidadUsuario eu : listaEntidadUsuario)
/* 227:    */       {
/* 228:219 */         ConsultarUsuarioResponseDto response = new ConsultarUsuarioResponseDto();
/* 229:220 */         response.setIdUsuario(eu.getId());
/* 230:221 */         response.setNombreUsuario(eu.getNombreUsuario());
/* 231:222 */         response.setNombre1(eu.getNombre1());
/* 232:223 */         response.setNombre2(eu.getNombre2());
/* 233:224 */         response.setTipoVisualizacion(eu.getTipoVisualizacion());
/* 234:225 */         response.setClave(eu.getClave());
/* 235:226 */         response.setIndicadorAdministrador(Boolean.valueOf(eu.isIndicadorAdministrador()));
/* 236:227 */         response.setEmail(eu.getEmail());
/* 237:228 */         cargarListaRoles(eu, response);
/* 238:229 */         cargarListaOrganizacion(eu, response, sistema);
/* 239:230 */         listaResponse.add(response);
/* 240:    */       }
/* 241:233 */       return listaResponse;
/* 242:    */     }
/* 243:    */     catch (Exception e)
/* 244:    */     {
/* 245:235 */       e.printStackTrace();
/* 246:236 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_202", new String[] { e.getMessage() });
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   @POST
/* 251:    */   @Path("/obtenerUsuariosJerarquia")
/* 252:    */   @Consumes({"application/json"})
/* 253:    */   @Produces({"application/json"})
/* 254:    */   public ProcesosResponseDto obtenerUsuariosJerarquia(ConsultarJerarquiaUsuarioRequestDto request)
/* 255:    */   {
/* 256:246 */     String error = null;
/* 257:247 */     this.languageController.setAccesoWeb(false);
/* 258:248 */     this.languageController.setUrlHost(request.getUrlApp());
/* 259:249 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 260:    */     try
/* 261:    */     {
/* 262:251 */       boolean indicadorAscendenteJerarquia = request.isIndicadorAscendente();
/* 263:252 */       EntidadUsuario entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(request.getIdUsuario()));
/* 264:253 */       DocumentoBase proceso = null;
/* 265:254 */       if ((request.getNombreDocumentoBase() != null) && (!request.getNombreDocumentoBase().trim().isEmpty()))
/* 266:    */       {
/* 267:255 */         proceso = DocumentoBase.obtenerDocumento(request.getNombreDocumentoBase());
/* 268:256 */         if (proceso == null) {
/* 269:257 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_202", new String[] { "DocumentoBase: " + request.getNombreDocumentoBase() });
/* 270:    */         }
/* 271:    */       }
/* 272:260 */       NodoArbol<EntidadUsuario> nodoArbol = this.servicioUsuario.obtenerJerarquiaUsuario(entidadUsuario, null, entidadUsuario, indicadorAscendenteJerarquia, proceso);
/* 273:    */       
/* 274:    */ 
/* 275:263 */       ConsultarJerarquiaUsuarioResponseDto jerarquia = obtenerJerarquiaUsuarioRecursivo(nodoArbol);
/* 276:    */       
/* 277:265 */       response.setSuccsess(true);
/* 278:266 */       response.setResponse(jerarquia);
/* 279:267 */       return response;
/* 280:    */     }
/* 281:    */     catch (ExcepcionAS2 e)
/* 282:    */     {
/* 283:269 */       e.printStackTrace();
/* 284:270 */       error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 285:    */     }
/* 286:    */     catch (AS2Exception e)
/* 287:    */     {
/* 288:272 */       e.printStackTrace();
/* 289:273 */       error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMensaje();
/* 290:    */     }
/* 291:    */     catch (Exception e)
/* 292:    */     {
/* 293:275 */       e.printStackTrace();
/* 294:276 */       error = e.getMessage();
/* 295:    */     }
/* 296:278 */     response.setSuccsess(false);
/* 297:279 */     response.setError(error);
/* 298:280 */     return response;
/* 299:    */   }
/* 300:    */   
/* 301:    */   private ConsultarJerarquiaUsuarioResponseDto obtenerJerarquiaUsuarioRecursivo(NodoArbol<EntidadUsuario> nodoArbol)
/* 302:    */   {
/* 303:284 */     ConsultarJerarquiaUsuarioResponseDto jerarquia = new ConsultarJerarquiaUsuarioResponseDto();
/* 304:285 */     EntidadUsuario usuario = (EntidadUsuario)nodoArbol.getValor();
/* 305:286 */     jerarquia.setIdUsuario(usuario.getId());
/* 306:287 */     jerarquia.setIndicadorAdministrador(Boolean.valueOf(usuario.isIndicadorAdministrador()));
/* 307:288 */     jerarquia.setNombre1(usuario.getNombre1());
/* 308:289 */     jerarquia.setNombre2(usuario.getNombre2());
/* 309:290 */     jerarquia.setNombreUsuario(usuario.getNombreUsuario());
/* 310:291 */     jerarquia.setTipoVisualizacion(usuario.getTipoVisualizacion());
/* 311:292 */     jerarquia.setEmail(usuario.getEmail());
/* 312:293 */     if (usuario.getProceso() != null) {
/* 313:294 */       jerarquia.setNombreDocumentoBase(usuario.getProceso().getNombre());
/* 314:    */     }
/* 315:297 */     for (NodoArbol<EntidadUsuario> hijo : nodoArbol.getHijos()) {
/* 316:298 */       jerarquia.getListaUsuarios().add(obtenerJerarquiaUsuarioRecursivo(hijo));
/* 317:    */     }
/* 318:301 */     return jerarquia;
/* 319:    */   }
/* 320:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.rest.SeguridadServicioRest
 * JD-Core Version:    0.7.0.1
 */