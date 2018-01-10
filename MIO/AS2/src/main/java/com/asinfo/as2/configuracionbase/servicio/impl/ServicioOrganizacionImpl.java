/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.CatalogosACopiar;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacionRemoto;
/*   6:    */ import com.asinfo.as2.dao.OrganizacionConfiguracionDao;
/*   7:    */ import com.asinfo.as2.dao.OrganizacionDao;
/*   8:    */ import com.asinfo.as2.dao.ProcesoOrganizacionDao;
/*   9:    */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*  10:    */ import com.asinfo.as2.entities.Atributo;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  13:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*  14:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  16:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  20:    */ import com.asinfo.as2.utils.JsfUtil;
/*  21:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  22:    */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*  23:    */ import java.util.HashSet;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import java.util.Set;
/*  27:    */ import javax.annotation.Resource;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.ejb.SessionContext;
/*  30:    */ import javax.ejb.Stateless;
/*  31:    */ import javax.ejb.TransactionAttribute;
/*  32:    */ import javax.ejb.TransactionAttributeType;
/*  33:    */ 
/*  34:    */ @Stateless
/*  35:    */ public class ServicioOrganizacionImpl
/*  36:    */   extends ServicioOrganizacionBase
/*  37:    */   implements ServicioOrganizacion, ServicioOrganizacionRemoto
/*  38:    */ {
/*  39:    */   @EJB
/*  40:    */   private ServicioEnvioEmail servicioEnvioEmail;
/*  41:    */   @EJB
/*  42:    */   private ProcesoOrganizacionDao procesoOrganizacionDao;
/*  43:    */   @EJB
/*  44:    */   private UsuarioDao usuarioDao;
/*  45:    */   @Resource
/*  46:    */   protected SessionContext context;
/*  47:    */   
/*  48:    */   public void guardar(Organizacion organizacion)
/*  49:    */     throws ExcepcionAS2, ExcepcionAS2Identification
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 68 */       if ((!organizacion.getOrganizacionConfiguracion().getNumeroResolucionContribuyente().isEmpty()) && 
/*  54: 69 */         (!organizacion.getOrganizacionConfiguracion().getNumeroResolucionContribuyente().equals(null))) {
/*  55: 70 */         Integer.parseInt(organizacion.getOrganizacionConfiguracion().getNumeroResolucionContribuyente());
/*  56:    */       }
/*  57:    */     }
/*  58:    */     catch (NumberFormatException e)
/*  59:    */     {
/*  60: 73 */       throw new ExcepcionAS2("msg_error_campo_numerico");
/*  61:    */     }
/*  62: 75 */     validar(organizacion);
/*  63: 76 */     validarAtributos(organizacion);
/*  64:    */     
/*  65: 78 */     this.organizacionConfiguracionDao.guardar(organizacion.getOrganizacionConfiguracion());
/*  66: 79 */     this.organizacionDao.guardar(organizacion);
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void validar(Organizacion organizacion)
/*  70:    */     throws ExcepcionAS2Identification
/*  71:    */   {
/*  72: 83 */     ValidarIdentificacion.validarIdentificacion(organizacion.getTipoIdentificacion().isIndicadorValidarIdentificacion(), organizacion
/*  73: 84 */       .getIdentificacion());
/*  74: 85 */     ValidarIdentificacion.validarIdentificacion(organizacion.getTipoIdentificacionContador().isIndicadorValidarIdentificacion(), organizacion
/*  75: 86 */       .getIdentificacionContador());
/*  76: 87 */     ValidarIdentificacion.validarIdentificacion(organizacion.getTipoIdentificacionRepresentante().isIndicadorValidarIdentificacion(), organizacion
/*  77: 88 */       .getIdentificacionRepresentante());
/*  78:    */   }
/*  79:    */   
/*  80:    */   private void validarAtributos(Organizacion organizacion)
/*  81:    */     throws ExcepcionAS2
/*  82:    */   {
/*  83: 93 */     OrganizacionConfiguracion organizacionConfiguracion = organizacion.getOrganizacionConfiguracion();
/*  84: 94 */     Set<Integer> idsAtributo = new HashSet();
/*  85:    */     
/*  86: 96 */     int numeroAtributos = 0;
/*  87:    */     
/*  88: 98 */     Atributo atributo = organizacionConfiguracion.getAtributo1();
/*  89: 99 */     if (atributo != null)
/*  90:    */     {
/*  91:100 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/*  92:101 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/*  93:    */       }
/*  94:103 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/*  95:    */       
/*  96:105 */       numeroAtributos += 1;
/*  97:    */     }
/*  98:108 */     atributo = organizacionConfiguracion.getAtributo2();
/*  99:109 */     if (atributo != null)
/* 100:    */     {
/* 101:110 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 102:111 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 103:    */       }
/* 104:113 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 105:    */       
/* 106:115 */       numeroAtributos += 1;
/* 107:    */     }
/* 108:118 */     atributo = organizacionConfiguracion.getAtributo3();
/* 109:119 */     if (atributo != null)
/* 110:    */     {
/* 111:120 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 112:121 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 113:    */       }
/* 114:123 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 115:    */       
/* 116:125 */       numeroAtributos += 1;
/* 117:    */     }
/* 118:128 */     atributo = organizacionConfiguracion.getAtributo4();
/* 119:129 */     if (atributo != null)
/* 120:    */     {
/* 121:130 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 122:131 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 123:    */       }
/* 124:133 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 125:    */       
/* 126:135 */       numeroAtributos += 1;
/* 127:    */     }
/* 128:138 */     atributo = organizacionConfiguracion.getAtributo5();
/* 129:139 */     if (atributo != null)
/* 130:    */     {
/* 131:140 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 132:141 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 133:    */       }
/* 134:143 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 135:    */       
/* 136:145 */       numeroAtributos += 1;
/* 137:    */     }
/* 138:148 */     atributo = organizacionConfiguracion.getAtributo6();
/* 139:149 */     if (atributo != null)
/* 140:    */     {
/* 141:150 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 142:151 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 143:    */       }
/* 144:153 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 145:    */       
/* 146:155 */       numeroAtributos += 1;
/* 147:    */     }
/* 148:158 */     atributo = organizacionConfiguracion.getAtributo7();
/* 149:159 */     if (atributo != null)
/* 150:    */     {
/* 151:160 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 152:161 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 153:    */       }
/* 154:163 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 155:    */       
/* 156:165 */       numeroAtributos += 1;
/* 157:    */     }
/* 158:168 */     atributo = organizacionConfiguracion.getAtributo8();
/* 159:169 */     if (atributo != null)
/* 160:    */     {
/* 161:170 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 162:171 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 163:    */       }
/* 164:173 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 165:    */       
/* 166:175 */       numeroAtributos += 1;
/* 167:    */     }
/* 168:178 */     atributo = organizacionConfiguracion.getAtributo9();
/* 169:179 */     if (atributo != null)
/* 170:    */     {
/* 171:180 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 172:181 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 173:    */       }
/* 174:183 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 175:    */       
/* 176:185 */       numeroAtributos += 1;
/* 177:    */     }
/* 178:188 */     atributo = organizacionConfiguracion.getAtributo10();
/* 179:189 */     if (atributo != null)
/* 180:    */     {
/* 181:190 */       if (idsAtributo.contains(Integer.valueOf(atributo.getIdAtributo()))) {
/* 182:191 */         throw new ExcepcionAS2("msg_error_atributos_repetidos");
/* 183:    */       }
/* 184:193 */       idsAtributo.add(Integer.valueOf(atributo.getIdAtributo()));
/* 185:    */       
/* 186:195 */       numeroAtributos += 1;
/* 187:    */     }
/* 188:197 */     organizacionConfiguracion.setNumeroAtributos(numeroAtributos);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void eliminar(Organizacion entidad)
/* 192:    */   {
/* 193:208 */     this.organizacionDao.eliminar(entidad);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Organizacion buscarPorId(Integer id)
/* 197:    */   {
/* 198:218 */     return (Organizacion)this.organizacionDao.buscarPorId(id);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<Organizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 202:    */   {
/* 203:228 */     return this.organizacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<Organizacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 207:    */   {
/* 208:238 */     return this.organizacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public int contarPorCriterio(Map<String, String> filters)
/* 212:    */   {
/* 213:248 */     return this.organizacionDao.contarPorCriterio(filters);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<Organizacion> obtenerOrganizacionPorUsuario(int idUsuario)
/* 217:    */   {
/* 218:258 */     return obtenerOrganizacionPorUsuario(idUsuario, null);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<Organizacion> obtenerOrganizacionPorUsuario(int idUsuario, EntidadSistema entidadSistema)
/* 222:    */   {
/* 223:263 */     return obtenerOrganizacionPorUsuario(idUsuario, 0, 0, null, false, entidadSistema);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<Organizacion> obtenerOrganizacionPorUsuario(int idUsuario, int startIndex, int pageSize, String sortField, boolean ordenar)
/* 227:    */   {
/* 228:268 */     return obtenerOrganizacionPorUsuario(idUsuario, startIndex, pageSize, sortField, ordenar, null);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public List<Organizacion> obtenerOrganizacionPorUsuario(int idUsuario, int startIndex, int pageSize, String sortField, boolean ordenar, EntidadSistema entidadSistema)
/* 232:    */   {
/* 233:274 */     EntidadUsuario usuario = (EntidadUsuario)this.usuarioDao.buscarPorId(Integer.valueOf(idUsuario));
/* 234:276 */     if (usuario.isIndicadorSuperAdministrador()) {
/* 235:277 */       return obtenerListaCombo("razonSocial", true, null);
/* 236:    */     }
/* 237:280 */     return this.organizacionDao.obtenerOrganizacionPorUsuario(idUsuario, startIndex, pageSize, sortField, ordenar, entidadSistema);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void guardar(Organizacion organizacion, List<CatalogosACopiar> listaCatalogosACopiar, Organizacion organizacionACopiar)
/* 241:    */     throws ExcepcionAS2, ExcepcionAS2Identification
/* 242:    */   {
/* 243:293 */     if ((organizacion.getIdOrganizacion() == 0) && (organizacionACopiar == null)) {
/* 244:294 */       throw new ExcepcionAS2("msg_error_seleccionar_organizacion_copiar");
/* 245:    */     }
/* 246:295 */     if (organizacionACopiar != null)
/* 247:    */     {
/* 248:297 */       guardar(organizacion);
/* 249:298 */       organizacion.getOrganizacionConfiguracion().setOrganizacion(organizacion);
/* 250:    */       
/* 251:300 */       copiarTipoIdentificacion(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 252:301 */       copiarSucursal(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 253:    */       try
/* 254:    */       {
/* 255:303 */         copiarFormaPago(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 256:    */       }
/* 257:    */       catch (AS2Exception e)
/* 258:    */       {
/* 259:305 */         JsfUtil.addErrorMessage(e, "");
/* 260:306 */         this.context.setRollbackOnly();
/* 261:307 */         e.printStackTrace();
/* 262:    */       }
/* 263:309 */       copiarSecuencia(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 264:310 */       copiarTipoAsiento(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 265:311 */       copiarTipoComprobanteSRI(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 266:312 */       copiarConfiguracion(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 267:313 */       copiarDocumento(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 268:314 */       copiarConceptoRetencionSRI(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 269:315 */       copiarCondicionPago(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 270:316 */       copiarUnidad(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 271:317 */       copiarTipoCuentaBancaria(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 272:318 */       copiarBodega(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 273:319 */       copiarZona(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 274:320 */       copiarCategoriaEmpresa(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 275:321 */       copiarPais(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 276:322 */       copiarMoneda(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 277:323 */       copiarImpuestoRangoImpuesto(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 278:324 */       copiarCategoriaImpuesto(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 279:325 */       copiarModulo(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 280:    */       
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:330 */       copiarUsuario(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 285:331 */       copiarDocumentoVariableProceso(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 286:332 */       copiarDocumentoContabilizacion(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 287:333 */       copiarFiltroProducto(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 288:334 */       copiarSubcategoriaProducto(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 289:335 */       copiarPermiso(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 290:336 */       copiarBancos(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 291:    */       try
/* 292:    */       {
/* 293:338 */         copiarParroquias(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 294:    */       }
/* 295:    */       catch (AS2Exception e)
/* 296:    */       {
/* 297:340 */         JsfUtil.addErrorMessage(e, "");
/* 298:341 */         this.context.setRollbackOnly();
/* 299:342 */         e.printStackTrace();
/* 300:    */       }
/* 301:344 */       copiarHorarioHorasExtras(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 302:345 */       copiarEstadoCivil(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 303:346 */       copiarReportes(organizacion, organizacionACopiar, listaCatalogosACopiar);
/* 304:    */     }
/* 305:    */     else
/* 306:    */     {
/* 307:348 */       guardar(organizacion);
/* 308:    */     }
/* 309:352 */     this.servicioEnvioEmail.cargarDatosSMTP(organizacion);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public Organizacion cargarDetalle(int idOrganizacion)
/* 313:    */   {
/* 314:363 */     Organizacion organizacion = this.organizacionDao.cargarDetalle(idOrganizacion);
/* 315:    */     
/* 316:365 */     return organizacion;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public Long contarNumeroOrganizaciones()
/* 320:    */   {
/* 321:375 */     return this.organizacionDao.contarNumeroOrganizaciones();
/* 322:    */   }
/* 323:    */   
/* 324:    */   public Integer actualizarSecuenciaInicioSerie(Organizacion organizacion)
/* 325:    */   {
/* 326:385 */     return this.organizacionConfiguracionDao.actualizarSecuenciaInicioSerie(organizacion);
/* 327:    */   }
/* 328:    */   
/* 329:    */   public String obtenerDireccionMatriz(int idOrganizacion)
/* 330:    */   {
/* 331:395 */     return this.organizacionConfiguracionDao.obtenerDireccionMatriz(idOrganizacion);
/* 332:    */   }
/* 333:    */   
/* 334:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 335:    */   public void guardarListaProcesoOrganizacion(List<ProcesoOrganizacion> listaProcesoOrganizacion)
/* 336:    */     throws AS2Exception
/* 337:    */   {
/* 338:    */     try
/* 339:    */     {
/* 340:402 */       for (ProcesoOrganizacion procesoOrganizacion : listaProcesoOrganizacion) {
/* 341:403 */         this.procesoOrganizacionDao.guardar(procesoOrganizacion);
/* 342:    */       }
/* 343:    */     }
/* 344:    */     catch (Exception e)
/* 345:    */     {
/* 346:406 */       this.context.setRollbackOnly();
/* 347:407 */       throw new AS2Exception(e.getMessage());
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   public Organizacion cargarOrganizacionConfiguracion(int idOrganizacion)
/* 352:    */   {
/* 353:413 */     return this.organizacionDao.cargarOrganizacionConfiguracion(idOrganizacion);
/* 354:    */   }
/* 355:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioOrganizacionImpl
 * JD-Core Version:    0.7.0.1
 */