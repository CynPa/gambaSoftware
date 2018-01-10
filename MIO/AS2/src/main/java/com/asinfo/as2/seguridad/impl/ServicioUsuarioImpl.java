/*   1:    */ package com.asinfo.as2.seguridad.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.dao.ProcesoOrganizacionDao;
/*   6:    */ import com.asinfo.as2.dao.RutaVendeorDao;
/*   7:    */ import com.asinfo.as2.dao.UsuarioBodegaDao;
/*   8:    */ import com.asinfo.as2.dao.UsuarioDimensionContableDao;
/*   9:    */ import com.asinfo.as2.dao.UsuarioOrganizacionDao;
/*  10:    */ import com.asinfo.as2.dao.UsuarioSucursalDao;
/*  11:    */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*  12:    */ import com.asinfo.as2.entities.Bodega;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*  15:    */ import com.asinfo.as2.entities.RutaVendedor;
/*  16:    */ import com.asinfo.as2.entities.Sector;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.entities.UsuarioBodega;
/*  19:    */ import com.asinfo.as2.entities.UsuarioDimensionContable;
/*  20:    */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*  21:    */ import com.asinfo.as2.entities.UsuarioSucursal;
/*  22:    */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  23:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  24:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  25:    */ import com.asinfo.as2.entities.seguridad.UsuarioSuperior;
/*  26:    */ import com.asinfo.as2.enumeraciones.DiaSemanaEnum;
/*  27:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  28:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  29:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  30:    */ import com.asinfo.as2.seguridad.ServicioAccion;
/*  31:    */ import com.asinfo.as2.seguridad.ServicioProceso;
/*  32:    */ import com.asinfo.as2.seguridad.ServicioRol;
/*  33:    */ import com.asinfo.as2.seguridad.ServicioSistema;
/*  34:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  35:    */ import com.asinfo.as2.seguridad.modelo.Rol;
/*  36:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  37:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  38:    */ import com.asinfo.as2.util.exception.ServiceException;
/*  39:    */ import com.asinfo.as2.util.seguridad.AuthorizationPermission;
/*  40:    */ import com.asinfo.as2.utils.NodoArbol;
/*  41:    */ import com.asinfo.as2.ws.seguridad.model.UsuarioWSEntity;
/*  42:    */ import java.util.ArrayList;
/*  43:    */ import java.util.Collection;
/*  44:    */ import java.util.HashMap;
/*  45:    */ import java.util.HashSet;
/*  46:    */ import java.util.Iterator;
/*  47:    */ import java.util.List;
/*  48:    */ import java.util.Map;
/*  49:    */ import java.util.Set;
/*  50:    */ import javax.ejb.EJB;
/*  51:    */ import javax.ejb.SessionContext;
/*  52:    */ import javax.ejb.Stateless;
/*  53:    */ import javax.ejb.TransactionAttribute;
/*  54:    */ import javax.ejb.TransactionAttributeType;
/*  55:    */ import javax.security.auth.login.CredentialNotFoundException;
/*  56:    */ import javax.security.auth.login.LoginException;
/*  57:    */ 
/*  58:    */ @Stateless
/*  59:    */ public class ServicioUsuarioImpl
/*  60:    */   extends AbstractServicioAS2
/*  61:    */   implements ServicioUsuario
/*  62:    */ {
/*  63:    */   private static final long serialVersionUID = 1L;
/*  64:    */   @EJB
/*  65:    */   private transient UsuarioDao usuarioDao;
/*  66:    */   @EJB
/*  67:    */   private transient UsuarioBodegaDao usuarioBodegaDao;
/*  68:    */   @EJB
/*  69:    */   private transient RutaVendeorDao vendedorSectorDao;
/*  70:    */   @EJB
/*  71:    */   private transient UsuarioSucursalDao usuarioSucursalDao;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioProceso servicioProceso;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioSistema servicioSistema;
/*  76:    */   @EJB
/*  77:    */   private transient UsuarioOrganizacionDao usuarioOrganizacionDao;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioRol servicioRol;
/*  80:    */   @EJB
/*  81:    */   private transient ProcesoOrganizacionDao procesoOrganizacionDao;
/*  82:    */   @EJB
/*  83:    */   private transient ServicioAccion servicioAccion;
/*  84:    */   @EJB
/*  85:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  86:    */   @EJB
/*  87:    */   private transient GenericoDao<UsuarioSuperior> usuarioSuperiorDao;
/*  88:    */   @EJB
/*  89:    */   private transient UsuarioDimensionContableDao usuarioDimensionContableDao;
/*  90:    */   
/*  91:    */   public void guardar(EntidadUsuario usuario)
/*  92:    */     throws ExcepcionAS2, AS2Exception
/*  93:    */   {
/*  94:116 */     guardar(usuario, true);
/*  95:    */   }
/*  96:    */   
/*  97:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  98:    */   public void guardar(EntidadUsuario usuario, boolean valida)
/*  99:    */     throws ExcepcionAS2, AS2Exception
/* 100:    */   {
/* 101:122 */     if (valida) {
/* 102:123 */       validar(usuario);
/* 103:125 */     } else if (usuario.getListaRutaVendedor().size() > 0) {
/* 104:126 */       validaRutaVendededor(usuario.getListaRutaVendedor());
/* 105:    */     }
/* 106:    */     try
/* 107:    */     {
/* 108:130 */       for (UsuarioBodega usuarioBodega : usuario.getListaUsuarioBodega()) {
/* 109:131 */         this.usuarioBodegaDao.guardar(usuarioBodega);
/* 110:    */       }
/* 111:134 */       for (UsuarioSucursal usuarioSucursal : usuario.getListaUsuarioSucursal()) {
/* 112:135 */         this.usuarioSucursalDao.guardar(usuarioSucursal);
/* 113:    */       }
/* 114:138 */       for (RutaVendedor vendedorSector : usuario.getListaRutaVendedor()) {
/* 115:139 */         this.vendedorSectorDao.guardar(vendedorSector);
/* 116:    */       }
/* 117:141 */       for (UsuarioSuperior usuarioSuperior : usuario.getListaUsuarioSuperior())
/* 118:    */       {
/* 119:142 */         if (!usuario.getIndicadorAprobador().booleanValue()) {
/* 120:143 */           usuarioSuperior.setEliminado(true);
/* 121:    */         }
/* 122:145 */         this.usuarioSuperiorDao.guardar(usuarioSuperior);
/* 123:    */       }
/* 124:148 */       for (UsuarioDimensionContable udc : usuario.getListaUsuarioDimensionContable()) {
/* 125:149 */         this.usuarioDimensionContableDao.guardar(udc);
/* 126:    */       }
/* 127:152 */       if (usuario.getId() == 0)
/* 128:    */       {
/* 129:153 */         lista = (List)usuario.getListaRol();
/* 130:154 */         usuario.setListaRol(null);
/* 131:    */         
/* 132:156 */         List<UsuarioOrganizacion> listaUsuarioOrganizacion = usuario.getListaUsuarioOrganizacion();
/* 133:157 */         usuario.setListaUsuarioOrganizacion(null);
/* 134:    */         
/* 135:159 */         this.usuarioDao.guardar(usuario);
/* 136:    */         
/* 137:161 */         usuario.setListaRol((Collection)lista);
/* 138:162 */         this.usuarioDao.guardar(usuario);
/* 139:    */         
/* 140:164 */         usuario.setListaUsuarioOrganizacion(listaUsuarioOrganizacion);
/* 141:    */       }
/* 142:    */       else
/* 143:    */       {
/* 144:167 */         this.usuarioDao.guardar(usuario);
/* 145:    */       }
/* 146:169 */       for (Object lista = usuario.getListaUsuarioOrganizacion().iterator(); ((Iterator)lista).hasNext();)
/* 147:    */       {
/* 148:169 */         UsuarioOrganizacion usuarioOrganizacion = (UsuarioOrganizacion)((Iterator)lista).next();
/* 149:170 */         if (usuarioOrganizacion.isEliminado())
/* 150:    */         {
/* 151:171 */           this.usuarioOrganizacionDao.guardar(usuarioOrganizacion);
/* 152:172 */           this.usuarioOrganizacionDao.flush();
/* 153:    */         }
/* 154:    */       }
/* 155:175 */       for (lista = usuario.getListaUsuarioOrganizacion().iterator(); ((Iterator)lista).hasNext();)
/* 156:    */       {
/* 157:175 */         UsuarioOrganizacion usuarioOrganizacion = (UsuarioOrganizacion)((Iterator)lista).next();
/* 158:176 */         if (!usuarioOrganizacion.isEliminado()) {
/* 159:177 */           this.usuarioOrganizacionDao.guardar(usuarioOrganizacion);
/* 160:    */         }
/* 161:    */       }
/* 162:182 */       obtenerJerarquiaUsuario(usuario, null, usuario, true);
/* 163:183 */       obtenerJerarquiaUsuario(usuario, null, usuario, false);
/* 164:    */     }
/* 165:    */     catch (ExcepcionAS2 e)
/* 166:    */     {
/* 167:185 */       this.context.setRollbackOnly();
/* 168:186 */       throw e;
/* 169:    */     }
/* 170:    */     catch (Exception e)
/* 171:    */     {
/* 172:188 */       e.printStackTrace();
/* 173:189 */       this.context.setRollbackOnly();
/* 174:190 */       throw new ExcepcionAS2(e);
/* 175:    */     }
/* 176:195 */     if (!usuario.isActivo())
/* 177:    */     {
/* 178:196 */       EntidadUsuario usuarioPredeterminado = null;
/* 179:    */       try
/* 180:    */       {
/* 181:198 */         usuarioPredeterminado = this.usuarioDao.usuarioPredeterminado();
/* 182:    */       }
/* 183:    */       catch (ExcepcionAS2 e)
/* 184:    */       {
/* 185:201 */         e.printStackTrace();
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   private void validar(EntidadUsuario usuario)
/* 191:    */     throws ExcepcionAS2
/* 192:    */   {
/* 193:209 */     if (usuario.getId() == 0)
/* 194:    */     {
/* 195:210 */       Map<String, String> filtros = new HashMap();
/* 196:211 */       filtros.put("nombreUsuario", "=" + usuario.getNombreUsuario());
/* 197:212 */       listaUsuarioViejo = obtenerListaCombo("nombreUsuario", true, filtros);
/* 198:213 */       EntidadUsuario usuarioViejo = null;
/* 199:214 */       if (listaUsuarioViejo.size() > 0)
/* 200:    */       {
/* 201:215 */         usuarioViejo = cargarDetalle(((EntidadUsuario)listaUsuarioViejo.get(0)).getId(), 0);
/* 202:216 */         String organizacionesAsignadas = "";
/* 203:217 */         for (UsuarioOrganizacion uo : usuarioViejo.getListaUsuarioOrganizacion()) {
/* 204:218 */           organizacionesAsignadas = organizacionesAsignadas + " - " + uo.getOrganizacion().getRazonSocial();
/* 205:    */         }
/* 206:220 */         throw new ExcepcionAS2("msg_error_existe_usuario", " # Organizaciones Asignadas: " + organizacionesAsignadas);
/* 207:    */       }
/* 208:    */     }
/* 209:223 */     boolean asignadaOrganizacion = false;
/* 210:224 */     for (List<EntidadUsuario> listaUsuarioViejo = usuario.getListaUsuarioOrganizacion().iterator(); listaUsuarioViejo.hasNext();)
/* 211:    */     {
/* 212:224 */       usuarioOrganizacion = (UsuarioOrganizacion)listaUsuarioViejo.next();
/* 213:225 */       if (!usuarioOrganizacion.isEliminado()) {
/* 214:226 */         asignadaOrganizacion = true;
/* 215:    */       }
/* 216:    */     }
/* 217:    */     UsuarioOrganizacion usuarioOrganizacion;
/* 218:230 */     if (!asignadaOrganizacion) {
/* 219:231 */       throw new ExcepcionAS2("msg_error_organizaciones");
/* 220:    */     }
/* 221:234 */     if ((!usuario.isActivo()) && (usuario.isIndicadorAdministrador()) && 
/* 222:235 */       (this.usuarioDao.buscarAdministradores(usuario.getNombreUsuario()))) {
/* 223:236 */       throw new ExcepcionAS2("msg_error_usuario_administrador_activo");
/* 224:    */     }
/* 225:    */     Set<String> usuarioSuperiorAsignados;
/* 226:239 */     if (usuario.getIndicadorAprobador().booleanValue())
/* 227:    */     {
/* 228:240 */       usuarioSuperiorAsignados = new HashSet();
/* 229:241 */       for (UsuarioSuperior usuarioSuperior : usuario.getListaUsuarioSuperior()) {
/* 230:242 */         if (!usuarioSuperior.isEliminado())
/* 231:    */         {
/* 232:243 */           String key = usuarioSuperior.getDocumentoBase() + "~" + usuarioSuperior.getSuperior().getId();
/* 233:244 */           if (usuarioSuperiorAsignados.contains(key)) {
/* 234:245 */             throw new ExcepcionAS2("msg_error_duplicado_superior_proceso");
/* 235:    */           }
/* 236:247 */           usuarioSuperiorAsignados.add(key);
/* 237:    */         }
/* 238:    */       }
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void guardarNuevo(EntidadUsuario usuario)
/* 243:    */   {
/* 244:261 */     this.usuarioDao.guardar(usuario);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void eliminar(EntidadUsuario entidad)
/* 248:    */   {
/* 249:271 */     this.usuarioDao.eliminar(entidad);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public EntidadUsuario buscarPorId(Integer id)
/* 253:    */   {
/* 254:281 */     return (EntidadUsuario)this.usuarioDao.buscarPorId(id);
/* 255:    */   }
/* 256:    */   
/* 257:    */   @Deprecated
/* 258:    */   public Usuario login(String nombreUsuario, String clave)
/* 259:    */     throws ServiceException, LoginException
/* 260:    */   {
/* 261:293 */     EntidadUsuario entidadUsuario = this.usuarioDao.buscarPorNombreUsuario(nombreUsuario);
/* 262:295 */     if (entidadUsuario == null) {
/* 263:296 */       throw new CredentialNotFoundException("El usuario con el nombre '" + nombreUsuario + "' no encontrado.");
/* 264:    */     }
/* 265:297 */     if ((!entidadUsuario.isActivo()) || (!entidadUsuario.getClave().equals(clave)))
/* 266:    */     {
/* 267:298 */       if (entidadUsuario.isIndicadorNuevo()) {
/* 268:299 */         return new Usuario(entidadUsuario);
/* 269:    */       }
/* 270:301 */       throw new CredentialNotFoundException("La contrasenia es incorrecta o el usuario esta inactivo ");
/* 271:    */     }
/* 272:303 */     entidadUsuario.setEnlinea(true);
/* 273:    */     try
/* 274:    */     {
/* 275:305 */       this.usuarioDao.actualizar(entidadUsuario);
/* 276:    */     }
/* 277:    */     catch (Exception e)
/* 278:    */     {
/* 279:308 */       throw new ServiceException(e);
/* 280:    */     }
/* 281:311 */     return new Usuario(entidadUsuario);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void logout(Usuario usuario)
/* 285:    */     throws ServiceException
/* 286:    */   {
/* 287:323 */     EntidadUsuario entidadUsuario = (EntidadUsuario)this.usuarioDao.buscarPorId(Integer.valueOf(usuario.getIdUsuario()));
/* 288:324 */     entidadUsuario.setEnlinea(false);
/* 289:325 */     this.usuarioDao.actualizar(entidadUsuario);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void validaRutaVendededor(List<RutaVendedor> listaRutaVendedor)
/* 293:    */     throws AS2Exception
/* 294:    */   {
/* 295:334 */     HashMap<String, RutaVendedor> hmRutaVendedor = new HashMap();
/* 296:335 */     for (RutaVendedor rv : listaRutaVendedor)
/* 297:    */     {
/* 298:336 */       RutaVendedor auxRutaVendedor = (RutaVendedor)hmRutaVendedor.get(rv.getSector().getIdSector() + "~" + rv.getDiaSemana().toString());
/* 299:337 */       if (auxRutaVendedor != null) {
/* 300:338 */         throw new AS2Exception("com.asinfo.as2.seguridad.impl.ServicioUsuarioImpl.YA_EXISTE_EN_LA_LISTA_SECTOR_Y_DIA", new String[] { rv.getSector().getNombre(), rv.getDiaSemana().toString() });
/* 301:    */       }
/* 302:340 */       hmRutaVendedor.put(rv.getSector().getIdSector() + "~" + rv.getDiaSemana().toString(), rv);
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   public List<EntidadUsuario> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters, List<Integer> listaIdOrganizacion)
/* 307:    */   {
/* 308:354 */     return this.usuarioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters, listaIdOrganizacion);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public List<EntidadUsuario> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 312:    */   {
/* 313:364 */     return this.usuarioDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List<Object[]> getListaPermisosUsuario(int idUsuario)
/* 317:    */   {
/* 318:372 */     return this.usuarioDao.getListaPermisosUsuario(idUsuario);
/* 319:    */   }
/* 320:    */   
/* 321:    */   public int contarPorCriterio(Map<String, String> filters, List<Integer> listaIdOrganizacion)
/* 322:    */   {
/* 323:382 */     return this.usuarioDao.contarPorCriterio(filters, listaIdOrganizacion);
/* 324:    */   }
/* 325:    */   
/* 326:    */   public EntidadUsuario cargarDetalle(int idEntidadUsuario, int idOrganizacion)
/* 327:    */   {
/* 328:392 */     EntidadUsuario entidadUsuario = this.usuarioDao.cargarDetalle(idEntidadUsuario, idOrganizacion);
/* 329:393 */     HashMap<Integer, Organizacion> hmOrganizacion = new HashMap();
/* 330:395 */     for (UsuarioOrganizacion uo : entidadUsuario.getListaUsuarioOrganizacion()) {
/* 331:396 */       if (!hmOrganizacion.containsKey(Integer.valueOf(uo.getOrganizacion().getId()))) {
/* 332:397 */         hmOrganizacion.put(Integer.valueOf(uo.getOrganizacion().getId()), uo.getOrganizacion());
/* 333:    */       }
/* 334:    */     }
/* 335:401 */     for (UsuarioBodega ub : entidadUsuario.getListaUsuarioBodega()) {
/* 336:402 */       ub.getBodega().setOrganizacion((Organizacion)hmOrganizacion.get(Integer.valueOf(ub.getBodega().getIdOrganizacion())));
/* 337:    */     }
/* 338:404 */     return entidadUsuario;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public EntidadUsuario cargarDetalleRutaVendedor(int idEntidadUsuario)
/* 342:    */   {
/* 343:408 */     return this.usuarioDao.cargarDetalleRutaVendedor(idEntidadUsuario);
/* 344:    */   }
/* 345:    */   
/* 346:    */   public List<EntidadUsuario> obtenerListaAgenteComercial(int idOrganizacion)
/* 347:    */   {
/* 348:421 */     return getEntidadUsuario(idOrganizacion, true, null);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<EntidadUsuario> obtenerListaAgenteComercial(int idOrganizacion, Boolean activo)
/* 352:    */   {
/* 353:426 */     return getEntidadUsuario(idOrganizacion, true, null, activo);
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<EntidadUsuario> obtenerListaAgenteComercial(int idOrganizacion, Boolean activo, Boolean indicadorConPlanComision)
/* 357:    */   {
/* 358:431 */     return this.usuarioDao.getEntidadUsuario(idOrganizacion, true, null, activo, indicadorConPlanComision);
/* 359:    */   }
/* 360:    */   
/* 361:    */   public EntidadUsuario buscaAgenteComercialPorIdEmpresa(int idEmpresa)
/* 362:    */   {
/* 363:441 */     return this.usuarioDao.buscaAgenteComercialPorIdEmpresa(idEmpresa);
/* 364:    */   }
/* 365:    */   
/* 366:    */   public EntidadUsuario obtieneUsuarioAutorizaVentas(String nombreUsuario, String clave)
/* 367:    */     throws ExcepcionAS2
/* 368:    */   {
/* 369:451 */     return this.usuarioDao.obtieneUsuarioAutorizaVentas(nombreUsuario, clave);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void actualizarClave(EntidadUsuario usuario)
/* 373:    */   {
/* 374:461 */     this.usuarioDao.guardar(usuario);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public List<EntidadUsuario> getEntidadUsuario(int idOrganizacion, boolean indicadorAgenteComercial, Sucursal sucursal)
/* 378:    */   {
/* 379:472 */     return this.usuarioDao.getEntidadUsuario(idOrganizacion, indicadorAgenteComercial, sucursal);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public List<EntidadUsuario> getEntidadUsuario(int idOrganizacion, boolean indicadorAgenteComercial, Sucursal sucursal, Boolean activo)
/* 383:    */   {
/* 384:477 */     return this.usuarioDao.getEntidadUsuario(idOrganizacion, indicadorAgenteComercial, sucursal, activo, null);
/* 385:    */   }
/* 386:    */   
/* 387:    */   public UsuarioWSEntity login(String nombreUsuario, String clave, String nombreSistema)
/* 388:    */     throws AS2Exception
/* 389:    */   {
/* 390:483 */     EntidadSistema sistema = this.servicioSistema.buscarPorNombre(nombreSistema);
/* 391:484 */     EntidadUsuario entidadUsuario = this.usuarioDao.buscarPorNombreUsuario(nombreUsuario, sistema);
/* 392:486 */     if (entidadUsuario == null) {
/* 393:487 */       throw new AS2Exception("El usuario con el nombre '" + nombreUsuario + "' no encontrado.");
/* 394:    */     }
/* 395:488 */     if ((!entidadUsuario.isActivo()) || (!entidadUsuario.getClave().equals(clave))) {
/* 396:489 */       throw new AS2Exception("La contrasenia es incorrecta o el usuario no es activo");
/* 397:    */     }
/* 398:491 */     entidadUsuario.setEnlinea(true);
/* 399:    */     try
/* 400:    */     {
/* 401:493 */       this.usuarioDao.actualizar(entidadUsuario);
/* 402:    */     }
/* 403:    */     catch (Exception e)
/* 404:    */     {
/* 405:495 */       throw new AS2Exception(e.getMessage());
/* 406:    */     }
/* 407:497 */     return new UsuarioWSEntity(entidadUsuario);
/* 408:    */   }
/* 409:    */   
/* 410:    */   public List<EntidadProceso> getProcesos(UsuarioWSEntity usuario, EntidadSistema sistema)
/* 411:    */   {
/* 412:509 */     List<Integer> listaIdProceso = new ArrayList();
/* 413:510 */     Map<Integer, Integer> mapaProceso = new HashMap();
/* 414:511 */     for (Rol rol : usuario.getListaRol()) {
/* 415:512 */       for (AuthorizationPermission permiso : rol.getListaPermisos())
/* 416:    */       {
/* 417:513 */         AuthorizationPermission ap = permiso;
/* 418:    */         
/* 419:515 */         String acciones = ap.getAcciones().toUpperCase();
/* 420:517 */         if ((acciones.contains("ALL")) || (acciones.contains("READ"))) {
/* 421:518 */           mapaProceso.put(Integer.valueOf(ap.getIdProceso()), Integer.valueOf(ap.getIdProceso()));
/* 422:    */         }
/* 423:    */       }
/* 424:    */     }
/* 425:523 */     listaIdProceso.addAll(mapaProceso.values());
/* 426:    */     
/* 427:525 */     return this.servicioProceso.obtenerLista(listaIdProceso, sistema);
/* 428:    */   }
/* 429:    */   
/* 430:    */   public List<EntidadProceso> getProcesos(Usuario usuario, EntidadSistema sistema, Organizacion organizacion)
/* 431:    */   {
/* 432:537 */     if (usuario.isIndicadorSuperAdministrador())
/* 433:    */     {
/* 434:538 */       List<EntidadProceso> listaProceso = new ArrayList();
/* 435:539 */       List<ProcesoOrganizacion> listaProcesoOrganizacion = this.procesoOrganizacionDao.buscarPorSistemaOrganizacion(sistema, organizacion);
/* 436:540 */       for (ProcesoOrganizacion procesoOrganizacion : listaProcesoOrganizacion) {
/* 437:541 */         listaProceso.add(procesoOrganizacion.getEntidadProceso());
/* 438:    */       }
/* 439:543 */       return listaProceso;
/* 440:    */     }
/* 441:546 */     List<Integer> listaIdProceso = new ArrayList();
/* 442:547 */     Map<Integer, Integer> mapaProceso = new HashMap();
/* 443:549 */     for (Rol rol : usuario.getListaRol()) {
/* 444:550 */       for (AuthorizationPermission permiso : rol.getListaPermisos()) {
/* 445:551 */         if (permiso.getIdOrganizacion() == organizacion.getId())
/* 446:    */         {
/* 447:553 */           AuthorizationPermission ap = permiso;
/* 448:    */           
/* 449:555 */           String acciones = ap.getAcciones().toUpperCase();
/* 450:557 */           if ((acciones.contains("ALL")) || (acciones.contains("READ"))) {
/* 451:558 */             mapaProceso.put(Integer.valueOf(ap.getIdProceso()), Integer.valueOf(ap.getIdProceso()));
/* 452:    */           }
/* 453:    */         }
/* 454:    */       }
/* 455:    */     }
/* 456:564 */     listaIdProceso.addAll(mapaProceso.values());
/* 457:565 */     return this.servicioProceso.obtenerLista(listaIdProceso, sistema);
/* 458:    */   }
/* 459:    */   
/* 460:    */   public List<EntidadUsuario> autocompletarUsuarios(String consulta)
/* 461:    */   {
/* 462:569 */     List<EntidadUsuario> lista = new ArrayList();
/* 463:    */     
/* 464:571 */     String sortField = "codigo";
/* 465:572 */     HashMap<String, String> filters = new HashMap();
/* 466:573 */     filters.put("nombreUsuario", consulta.trim());
/* 467:574 */     lista = obtenerListaCombo(sortField, true, filters);
/* 468:    */     
/* 469:576 */     return lista;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public List<Object[]> listaRepoteRutaVendedor(EntidadUsuario entidadUsuario)
/* 473:    */   {
/* 474:581 */     return this.usuarioDao.listaReporteRutaVenededor(entidadUsuario);
/* 475:    */   }
/* 476:    */   
/* 477:    */   public List<EntidadUsuario> autocompletarSuperiores(String consulta, EntidadUsuario entidadUsuario)
/* 478:    */   {
/* 479:586 */     return this.usuarioDao.autocompletarSuperiores(consulta, entidadUsuario);
/* 480:    */   }
/* 481:    */   
/* 482:    */   public List<DocumentoBase> getListaProcesosJerarquia()
/* 483:    */   {
/* 484:592 */     List<DocumentoBase> listaProcesos = new ArrayList();
/* 485:593 */     listaProcesos.add(DocumentoBase.PEDIDO_CLIENTE);
/* 486:594 */     listaProcesos.add(DocumentoBase.PEDIDO_PROVEEDOR);
/* 487:595 */     listaProcesos.add(DocumentoBase.ORDEN_SALIDA_MATERIAL);
/* 488:596 */     listaProcesos.add(DocumentoBase.SUPERVISOR);
/* 489:597 */     return listaProcesos;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public NodoArbol<EntidadUsuario> obtenerJerarquiaUsuario(EntidadUsuario entidadUsuarioPrincipal, NodoArbol<EntidadUsuario> padre, EntidadUsuario entidadUsuario, boolean indicadorAscendente)
/* 493:    */     throws ExcepcionAS2
/* 494:    */   {
/* 495:603 */     NodoArbol<EntidadUsuario> nodo = new NodoArbol(entidadUsuario, padre);
/* 496:604 */     List<DocumentoBase> listaProcesos = getListaProcesosJerarquia();
/* 497:605 */     for (Iterator localIterator1 = getListaProcesosJerarquia().iterator(); localIterator1.hasNext();)
/* 498:    */     {
/* 499:605 */       proceso = (DocumentoBase)localIterator1.next();
/* 500:    */       
/* 501:607 */       List<EntidadUsuario> listaJerarquia = this.usuarioDao.buscarJerarquiaInmediata(entidadUsuario, indicadorAscendente, proceso);
/* 502:608 */       for (EntidadUsuario entidadUsuarioJerarquia : listaJerarquia)
/* 503:    */       {
/* 504:609 */         this.usuarioDao.detach(entidadUsuarioJerarquia);
/* 505:610 */         if (entidadUsuarioJerarquia.getId() == entidadUsuarioPrincipal.getId()) {
/* 506:611 */           throw new ExcepcionAS2("msg_error_recursividad_infinita");
/* 507:    */         }
/* 508:613 */         entidadUsuarioJerarquia.setProceso(proceso);
/* 509:    */         
/* 510:615 */         NodoArbol<EntidadUsuario> hijo = obtenerJerarquiaUsuario(entidadUsuarioPrincipal, nodo, entidadUsuarioJerarquia, indicadorAscendente, proceso);
/* 511:    */         
/* 512:617 */         nodo.addHijo(hijo);
/* 513:    */       }
/* 514:    */     }
/* 515:    */     DocumentoBase proceso;
/* 516:620 */     return nodo;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public NodoArbol<EntidadUsuario> obtenerJerarquiaUsuario(EntidadUsuario entidadUsuarioPrincipal, NodoArbol<EntidadUsuario> padre, EntidadUsuario entidadUsuario, boolean indicadorAscendente, DocumentoBase proceso)
/* 520:    */     throws ExcepcionAS2
/* 521:    */   {
/* 522:626 */     NodoArbol<EntidadUsuario> nodo = new NodoArbol(entidadUsuario, padre);
/* 523:    */     
/* 524:628 */     List<EntidadUsuario> listaJerarquia = this.usuarioDao.buscarJerarquiaInmediata(entidadUsuario, indicadorAscendente, proceso);
/* 525:629 */     for (EntidadUsuario entidadUsuarioJerarquia : listaJerarquia)
/* 526:    */     {
/* 527:630 */       if (entidadUsuarioJerarquia.getId() == entidadUsuarioPrincipal.getId()) {
/* 528:631 */         throw new ExcepcionAS2("msg_error_recursividad_infinita");
/* 529:    */       }
/* 530:633 */       entidadUsuarioJerarquia.setProceso(proceso);
/* 531:634 */       NodoArbol<EntidadUsuario> hijo = obtenerJerarquiaUsuario(entidadUsuarioPrincipal, nodo, entidadUsuarioJerarquia, indicadorAscendente, proceso);
/* 532:    */       
/* 533:636 */       nodo.addHijo(hijo);
/* 534:    */     }
/* 535:639 */     return nodo;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public List<EntidadUsuario> buscarJerarquiaInmediata(int idUsuario, boolean indicadorAscendente, DocumentoBase proceso)
/* 539:    */   {
/* 540:644 */     EntidadUsuario entidadUsuario = new EntidadUsuario();
/* 541:645 */     entidadUsuario.setIdUsuario(idUsuario);
/* 542:646 */     return this.usuarioDao.buscarJerarquiaInmediata(entidadUsuario, indicadorAscendente, proceso);
/* 543:    */   }
/* 544:    */   
/* 545:    */   public List<EntidadUsuario> buscarUsuarioPorSistemaYOrganizacion(String sistema, Integer idOrganizacion)
/* 546:    */   {
/* 547:651 */     return this.usuarioDao.buscarUsuarioPorSistemaYOrganizacion(sistema, idOrganizacion);
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void verificarJerarquiaUsuario(Usuario usuarioEnSesion, DocumentoBase documentoBase)
/* 551:    */     throws AS2Exception
/* 552:    */   {
/* 553:656 */     if (usuarioEnSesion.isIndicadorAprobador())
/* 554:    */     {
/* 555:657 */       List<EntidadUsuario> listaSuperiores = buscarJerarquiaInmediata(usuarioEnSesion.getIdUsuario(), true, documentoBase);
/* 556:658 */       List<EntidadUsuario> listaSubordinados = buscarJerarquiaInmediata(usuarioEnSesion.getIdUsuario(), false, documentoBase);
/* 557:659 */       if ((listaSuperiores.isEmpty()) && (listaSubordinados.isEmpty())) {
/* 558:660 */         throw new AS2Exception("ERROR_NO_DEFINIDA_JERARQUIA_USUARIO_APROBADOR", new String[] { usuarioEnSesion.getNombreUsuario() });
/* 559:    */       }
/* 560:    */     }
/* 561:    */   }
/* 562:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.impl.ServicioUsuarioImpl
 * JD-Core Version:    0.7.0.1
 */