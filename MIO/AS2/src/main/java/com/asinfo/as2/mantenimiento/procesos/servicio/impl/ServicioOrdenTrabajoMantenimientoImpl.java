/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.CalendarioMantenimientoDao;
/*   5:    */ import com.asinfo.as2.dao.mantenimiento.LecturaMantenimientoDao;
/*   6:    */ import com.asinfo.as2.dao.mantenimiento.OrdenTrabajoMantenimientoDao;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  12:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  15:    */ import com.asinfo.as2.entities.Producto;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.mantenimiento.ActividadImagenOrdenTrabajoMantenimiento;
/*  18:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*  19:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoHerramienta;
/*  20:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoMaterial;
/*  21:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMaterialOrdenTrabajoMantenimiento;
/*  22:    */ import com.asinfo.as2.entities.mantenimiento.ActividadResponsableOrdenTrabajoMantenimiento;
/*  23:    */ import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimientoEntidad;
/*  24:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*  25:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  26:    */ import com.asinfo.as2.entities.mantenimiento.ConsumoAgilMantenimiento;
/*  27:    */ import com.asinfo.as2.entities.mantenimiento.DetalleConsumoAgilMantenimiento;
/*  28:    */ import com.asinfo.as2.entities.mantenimiento.DetalleOrdenTrabajoMantenimiento;
/*  29:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  30:    */ import com.asinfo.as2.entities.mantenimiento.Herramienta;
/*  31:    */ import com.asinfo.as2.entities.mantenimiento.HerramientaOrdenTrabajoMantenimiento;
/*  32:    */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*  33:    */ import com.asinfo.as2.entities.mantenimiento.MaterialOrdenTrabajoMantenimiento;
/*  34:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*  35:    */ import com.asinfo.as2.entities.mantenimiento.ResponsableOrdenTrabajoMantenimiento;
/*  36:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  37:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  38:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  39:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  40:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  41:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  42:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  43:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  44:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  45:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioActividadMantenimiento;
/*  46:    */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*  47:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  48:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  49:    */ import com.asinfo.as2.util.AppUtil;
/*  50:    */ import java.io.PrintStream;
/*  51:    */ import java.math.BigDecimal;
/*  52:    */ import java.util.ArrayList;
/*  53:    */ import java.util.Date;
/*  54:    */ import java.util.HashMap;
/*  55:    */ import java.util.HashSet;
/*  56:    */ import java.util.Iterator;
/*  57:    */ import java.util.List;
/*  58:    */ import java.util.Map;
/*  59:    */ import java.util.Set;
/*  60:    */ import javax.ejb.EJB;
/*  61:    */ import javax.ejb.SessionContext;
/*  62:    */ import javax.ejb.Stateless;
/*  63:    */ import javax.ejb.TransactionAttribute;
/*  64:    */ import javax.ejb.TransactionAttributeType;
/*  65:    */ import javax.ejb.TransactionManagement;
/*  66:    */ import javax.ejb.TransactionManagementType;
/*  67:    */ 
/*  68:    */ @Stateless
/*  69:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  70:    */ public class ServicioOrdenTrabajoMantenimientoImpl
/*  71:    */   extends AbstractServicioAS2
/*  72:    */   implements ServicioOrdenTrabajoMantenimiento
/*  73:    */ {
/*  74:    */   private static final long serialVersionUID = 1L;
/*  75:    */   @EJB
/*  76:    */   private transient OrdenTrabajoMantenimientoDao ordenTrabajoMantenimientoDao;
/*  77:    */   @EJB
/*  78:    */   private transient GenericoDao<DetalleOrdenTrabajoMantenimiento> detalleOrdenTrabajoMantenimientoDao;
/*  79:    */   @EJB
/*  80:    */   private transient GenericoDao<ResponsableOrdenTrabajoMantenimiento> responsableOrdenTrabajoMantenimientoDao;
/*  81:    */   @EJB
/*  82:    */   private transient ServicioSecuencia servicioSecuencia;
/*  83:    */   @EJB
/*  84:    */   private transient ServicioActividadMantenimiento servicioActividadMantenimiento;
/*  85:    */   @EJB
/*  86:    */   private transient ServicioGenerico<HerramientaOrdenTrabajoMantenimiento> herramientaOrdenTrabajoMantenimientoDao;
/*  87:    */   @EJB
/*  88:    */   private transient ServicioGenerico<MaterialOrdenTrabajoMantenimiento> materialOrdenTrabajoMantenimientoDao;
/*  89:    */   @EJB
/*  90:    */   private transient ServicioUnidadConversion servicioUnidadConversion;
/*  91:    */   @EJB
/*  92:    */   private transient ServicioProducto servicioProducto;
/*  93:    */   @EJB
/*  94:    */   private transient CalendarioMantenimientoDao calendarioMantenimientoDao;
/*  95:    */   @EJB
/*  96:    */   private transient LecturaMantenimientoDao lecturaMantenimientoDao;
/*  97:    */   @EJB
/*  98:    */   private transient GenericoDao<ActividadResponsableOrdenTrabajoMantenimiento> actividadResponsableOrdenTrabajoMantenimientoDao;
/*  99:    */   @EJB
/* 100:    */   private transient GenericoDao<ActividadMaterialOrdenTrabajoMantenimiento> actividadMaterialOrdenTrabajoMantenimientoDao;
/* 101:    */   @EJB
/* 102:    */   private transient GenericoDao<ActividadImagenOrdenTrabajoMantenimiento> actividadImagenOrdenTrabajoMantenimientoDao;
/* 103:    */   @EJB
/* 104:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/* 105:    */   
/* 106:    */   public List<OrdenTrabajoMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 107:    */   {
/* 108:115 */     return this.ordenTrabajoMantenimientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 109:    */   }
/* 110:    */   
/* 111:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 112:    */   public void guardar(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 113:    */     throws AS2ExceptionMantenimiento
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:122 */       validar(ordenTrabajoMantenimiento);
/* 118:    */       
/* 119:124 */       cargarSecuencia(ordenTrabajoMantenimiento);
/* 120:    */       
/* 121:126 */       this.ordenTrabajoMantenimientoDao.guardar(ordenTrabajoMantenimiento);
/* 122:128 */       for (DetalleOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento())
/* 123:    */       {
/* 124:129 */         this.detalleOrdenTrabajoMantenimientoDao.guardar(detalle);
/* 125:130 */         if (detalle.getCalendarioMantenimientoEntidad() != null)
/* 126:    */         {
/* 127:131 */           detalle.getCalendarioMantenimientoEntidad().setDetalleOrdenTrabajoMantenimiento(detalle);
/* 128:132 */           detalle.getCalendarioMantenimientoEntidad().setFechaModificada(ordenTrabajoMantenimiento.getFechaMantenimiento());
/* 129:133 */           this.calendarioMantenimientoDao.guardar(detalle.getCalendarioMantenimientoEntidad());
/* 130:    */         }
/* 131:    */       }
/* 132:136 */       for (ResponsableOrdenTrabajoMantenimiento responsable : ordenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento()) {
/* 133:137 */         this.responsableOrdenTrabajoMantenimientoDao.guardar(responsable);
/* 134:    */       }
/* 135:139 */       for (HerramientaOrdenTrabajoMantenimiento herramienta : ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento()) {
/* 136:140 */         this.herramientaOrdenTrabajoMantenimientoDao.guardar(herramienta);
/* 137:    */       }
/* 138:142 */       for (MaterialOrdenTrabajoMantenimiento material : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento())
/* 139:    */       {
/* 140:143 */         if ((!material.isEliminado()) && (material.getId() != 0))
/* 141:    */         {
/* 142:146 */           MaterialOrdenTrabajoMantenimiento materialBase = (MaterialOrdenTrabajoMantenimiento)this.materialOrdenTrabajoMantenimientoDao.buscarPorId(MaterialOrdenTrabajoMantenimiento.class, Integer.valueOf(material.getId()));
/* 143:147 */           material.setCantidadDespachada(materialBase.getCantidadDespachada());
/* 144:148 */           material.setCantidadDevuelta(materialBase.getCantidadDevuelta());
/* 145:    */         }
/* 146:150 */         this.materialOrdenTrabajoMantenimientoDao.guardar(material);
/* 147:    */       }
/* 148:    */     }
/* 149:    */     catch (Exception e)
/* 150:    */     {
/* 151:153 */       this.context.setRollbackOnly();
/* 152:154 */       e.printStackTrace();
/* 153:155 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   private void cargarSecuencia(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 158:    */     throws ExcepcionAS2
/* 159:    */   {
/* 160:162 */     if ((ordenTrabajoMantenimiento.getNumero() == null) || (ordenTrabajoMantenimiento.getNumero().equals("")))
/* 161:    */     {
/* 162:163 */       String numero = "";
/* 163:164 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(ordenTrabajoMantenimiento.getDocumento().getId(), ordenTrabajoMantenimiento
/* 164:165 */         .getFechaMantenimiento());
/* 165:166 */       ordenTrabajoMantenimiento.setNumero(numero);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   private void validar(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 170:    */     throws AS2ExceptionMantenimiento
/* 171:    */   {
/* 172:173 */     Set<String> responsables = new HashSet();
/* 173:174 */     for (Iterator localIterator = ordenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento().iterator(); localIterator.hasNext();)
/* 174:    */     {
/* 175:174 */       responsable = (ResponsableOrdenTrabajoMantenimiento)localIterator.next();
/* 176:175 */       if (!responsable.isEliminado())
/* 177:    */       {
/* 178:176 */         String key = responsable.getPersonaResponsable().getId() + "";
/* 179:177 */         if (responsables.contains(key)) {
/* 180:179 */           throw new AS2ExceptionMantenimiento("msg_error_detalle_repetido", new String[] { "RESPONSABLE", responsable.getPersonaResponsable().getNombres() });
/* 181:    */         }
/* 182:181 */         responsables.add(key);
/* 183:    */       }
/* 184:    */     }
/* 185:186 */     Object materiales = new HashSet();
/* 186:187 */     for (ResponsableOrdenTrabajoMantenimiento responsable = ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento().iterator(); responsable.hasNext();)
/* 187:    */     {
/* 188:187 */       material = (MaterialOrdenTrabajoMantenimiento)responsable.next();
/* 189:188 */       if (!material.isEliminado())
/* 190:    */       {
/* 191:189 */         String key = material.getMaterial().getId() + "";
/* 192:190 */         if (((Set)materiales).contains(key)) {
/* 193:191 */           throw new AS2ExceptionMantenimiento("msg_error_detalle_repetido", new String[] { "MATERIAL", material.getMaterial().getNombre() });
/* 194:    */         }
/* 195:193 */         if (material.getCantidadRequerida().compareTo(BigDecimal.ZERO) == 0) {
/* 196:194 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioPlanMantenimientoImpl.ERROR_CANTIDAD_MAYOR_CERO", new String[] { "MATERIAL: " + material.getMaterial().getNombre(), "0" });
/* 197:    */         }
/* 198:197 */         ((Set)materiales).add(key);
/* 199:    */       }
/* 200:    */     }
/* 201:    */     MaterialOrdenTrabajoMantenimiento material;
/* 202:202 */     Set<String> herramientas = new HashSet();
/* 203:203 */     for (HerramientaOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento()) {
/* 204:204 */       if (!detalle.isEliminado())
/* 205:    */       {
/* 206:205 */         String key = detalle.getHerramienta().getId() + "";
/* 207:206 */         if (herramientas.contains(key)) {
/* 208:207 */           throw new AS2ExceptionMantenimiento("msg_error_detalle_repetido", new String[] { "HERRAMIENTA", detalle.getHerramienta().getNombre() });
/* 209:    */         }
/* 210:209 */         if (detalle.getCantidadRequerida().compareTo(BigDecimal.ZERO) == 0) {
/* 211:210 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioPlanMantenimientoImpl.ERROR_CANTIDAD_MAYOR_CERO", new String[] { "HERRAMIENTA: " + detalle.getHerramienta().getNombre(), "0" });
/* 212:    */         }
/* 213:213 */         herramientas.add(key);
/* 214:    */       }
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void eliminar(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 219:    */     throws AS2ExceptionMantenimiento
/* 220:    */   {
/* 221:    */     try
/* 222:    */     {
/* 223:221 */       ordenTrabajoMantenimiento = cargarDetalle(ordenTrabajoMantenimiento);
/* 224:223 */       for (DetalleOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento()) {
/* 225:224 */         this.detalleOrdenTrabajoMantenimientoDao.eliminar(detalle);
/* 226:    */       }
/* 227:226 */       for (ResponsableOrdenTrabajoMantenimiento responsable : ordenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento()) {
/* 228:227 */         this.responsableOrdenTrabajoMantenimientoDao.eliminar(responsable);
/* 229:    */       }
/* 230:229 */       for (HerramientaOrdenTrabajoMantenimiento herramienta : ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento()) {
/* 231:230 */         this.herramientaOrdenTrabajoMantenimientoDao.eliminar(herramienta);
/* 232:    */       }
/* 233:232 */       for (MaterialOrdenTrabajoMantenimiento material : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 234:233 */         this.materialOrdenTrabajoMantenimientoDao.eliminar(material);
/* 235:    */       }
/* 236:235 */       this.ordenTrabajoMantenimientoDao.eliminar(ordenTrabajoMantenimiento);
/* 237:    */     }
/* 238:    */     catch (Exception e)
/* 239:    */     {
/* 240:237 */       this.context.setRollbackOnly();
/* 241:238 */       e.printStackTrace();
/* 242:239 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void anular(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 247:    */     throws AS2ExceptionMantenimiento
/* 248:    */   {
/* 249:    */     try
/* 250:    */     {
/* 251:246 */       ordenTrabajoMantenimiento = cargarDetalle(ordenTrabajoMantenimiento);
/* 252:    */       
/* 253:248 */       validarAnular(ordenTrabajoMantenimiento);
/* 254:250 */       for (DetalleOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento()) {
/* 255:251 */         this.calendarioMantenimientoDao.desvincularDetalleOrdenTrabajoMantenimiento(detalle);
/* 256:    */       }
/* 257:253 */       ordenTrabajoMantenimiento.setEstado(Estado.ANULADO);
/* 258:254 */       this.ordenTrabajoMantenimientoDao.guardar(ordenTrabajoMantenimiento);
/* 259:    */     }
/* 260:    */     catch (Exception e)
/* 261:    */     {
/* 262:256 */       this.context.setRollbackOnly();
/* 263:257 */       e.printStackTrace();
/* 264:258 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   private void validarAnular(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 269:    */     throws AS2ExceptionMantenimiento
/* 270:    */   {
/* 271:263 */     if ((ordenTrabajoMantenimiento.getEstado().equals(Estado.ANULADO)) || (ordenTrabajoMantenimiento.getEstado().equals(Estado.CERRADO))) {
/* 272:264 */       throw new AS2ExceptionMantenimiento("msg_error_accion_no_permitida", new String[] { ordenTrabajoMantenimiento.getEstado().toString() });
/* 273:    */     }
/* 274:266 */     for (MaterialOrdenTrabajoMantenimiento material : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 275:267 */       if (material.getCantidadConsumida().compareTo(BigDecimal.ZERO) > 0) {
/* 276:269 */         throw new AS2ExceptionMantenimiento("msg_error_accion_no_permitida", new String[] {material.getMaterial().getNombre() + " Despachado: " + material.getCantidadConsumida() });
/* 277:    */       }
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   public OrdenTrabajoMantenimiento buscarPorId(Integer id)
/* 282:    */   {
/* 283:276 */     return (OrdenTrabajoMantenimiento)this.ordenTrabajoMantenimientoDao.buscarPorId(id);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public OrdenTrabajoMantenimiento cargarDetalle(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 287:    */   {
/* 288:281 */     OrdenTrabajoMantenimiento ordenBase = this.ordenTrabajoMantenimientoDao.cargarDetalle(ordenTrabajoMantenimiento);
/* 289:282 */     for (DetalleOrdenTrabajoMantenimiento detalle : ordenBase.getListaDetalleOrdenTrabajoMantenimiento())
/* 290:    */     {
/* 291:283 */       Map<String, String> filtros = new HashMap();
/* 292:284 */       filtros.put("detalleOrdenTrabajoMantenimiento.idDetalleOrdenTrabajoMantenimiento", detalle.getId() + "");
/* 293:285 */       List<CalendarioMantenimientoEntidad> listaCalendario = this.calendarioMantenimientoDao.obtenerListaPorPagina(0, 1, "fecha", false, filtros);
/* 294:286 */       if (listaCalendario.size() > 0) {
/* 295:287 */         detalle.setCalendarioMantenimientoEntidad((CalendarioMantenimientoEntidad)listaCalendario.get(0));
/* 296:    */       }
/* 297:    */     }
/* 298:290 */     return ordenBase;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public int contarPorCriterio(Map<String, String> filters)
/* 302:    */   {
/* 303:295 */     return this.ordenTrabajoMantenimientoDao.contarPorCriterio(filters);
/* 304:    */   }
/* 305:    */   
/* 306:    */   public List<OrdenTrabajoMantenimiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 307:    */   {
/* 308:300 */     return this.ordenTrabajoMantenimientoDao.obtenerListaCombo(sortField, sortOrder, filtros);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void actualizarMateriales(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 312:    */   {
/* 313:306 */     Map<String, MaterialOrdenTrabajoMantenimiento> mapaMateriales = new HashMap();
/* 314:307 */     for (MaterialOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 315:308 */       if (detalle.getMaterial() != null)
/* 316:    */       {
/* 317:309 */         mapaMateriales.put(detalle.getMaterial().getId() + "", detalle);
/* 318:310 */         detalle.setCantidadRequerida(BigDecimal.ZERO);
/* 319:312 */         if (detalle.getCantidadDespachada().compareTo(BigDecimal.ZERO) == 0) {
/* 320:313 */           detalle.setEliminado(true);
/* 321:    */         }
/* 322:    */       }
/* 323:    */     }
/* 324:318 */     for (DetalleOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento()) {
/* 325:319 */       if ((!detalle.isEliminado()) && (detalle.getActividadMantenimiento() != null))
/* 326:    */       {
/* 327:320 */         ActividadMantenimiento actividad = this.servicioActividadMantenimiento.cargarDetalle(detalle.getActividadMantenimiento());
/* 328:321 */         for (ActividadMantenimientoMaterial detalleMaterial : actividad.getListaMaterial())
/* 329:    */         {
/* 330:322 */           String key = detalleMaterial.getProducto().getId() + "";
/* 331:323 */           MaterialOrdenTrabajoMantenimiento material = (MaterialOrdenTrabajoMantenimiento)mapaMateriales.get(key);
/* 332:324 */           if (material == null)
/* 333:    */           {
/* 334:325 */             material = new MaterialOrdenTrabajoMantenimiento();
/* 335:326 */             material.setIdOrganizacion(ordenTrabajoMantenimiento.getIdOrganizacion());
/* 336:327 */             material.setIdSucursal(ordenTrabajoMantenimiento.getIdSucursal());
/* 337:328 */             material.setMaterial(detalleMaterial.getProducto());
/* 338:329 */             material.setOrdenTrabajoMantenimiento(ordenTrabajoMantenimiento);
/* 339:    */             
/* 340:331 */             ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento().add(material);
/* 341:332 */             mapaMateriales.put(key, material);
/* 342:    */           }
/* 343:335 */           BigDecimal cantidadAcumulada = material.getCantidadRequerida();
/* 344:336 */           cantidadAcumulada = cantidadAcumulada.add(detalleMaterial.getCantidad());
/* 345:337 */           material.setCantidadRequerida(cantidadAcumulada);
/* 346:338 */           material.setEliminado(false);
/* 347:    */         }
/* 348:    */       }
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void actualizarHerramientas(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento, boolean indicadorMaximoValor)
/* 353:    */   {
/* 354:347 */     Map<String, HerramientaOrdenTrabajoMantenimiento> mapaHerramientas = new HashMap();
/* 355:348 */     for (HerramientaOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento()) {
/* 356:349 */       if (detalle.getHerramienta() != null)
/* 357:    */       {
/* 358:350 */         mapaHerramientas.put(detalle.getHerramienta().getId() + "", detalle);
/* 359:351 */         detalle.setCantidadRequerida(BigDecimal.ZERO);
/* 360:    */         
/* 361:353 */         detalle.setEliminado(true);
/* 362:    */       }
/* 363:    */     }
/* 364:357 */     for (DetalleOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento()) {
/* 365:358 */       if ((!detalle.isEliminado()) && (detalle.getActividadMantenimiento() != null))
/* 366:    */       {
/* 367:359 */         ActividadMantenimiento actividad = this.servicioActividadMantenimiento.cargarDetalle(detalle.getActividadMantenimiento());
/* 368:360 */         for (ActividadMantenimientoHerramienta detalleHerramienta : actividad.getListaHerramienta())
/* 369:    */         {
/* 370:361 */           String key = detalleHerramienta.getHerramienta().getId() + "";
/* 371:362 */           HerramientaOrdenTrabajoMantenimiento herramienta = (HerramientaOrdenTrabajoMantenimiento)mapaHerramientas.get(key);
/* 372:363 */           if (herramienta == null)
/* 373:    */           {
/* 374:364 */             herramienta = new HerramientaOrdenTrabajoMantenimiento();
/* 375:365 */             herramienta.setIdOrganizacion(ordenTrabajoMantenimiento.getIdOrganizacion());
/* 376:366 */             herramienta.setIdSucursal(ordenTrabajoMantenimiento.getIdSucursal());
/* 377:367 */             herramienta.setHerramienta(detalleHerramienta.getHerramienta());
/* 378:368 */             herramienta.setOrdenTrabajoMantenimiento(ordenTrabajoMantenimiento);
/* 379:    */             
/* 380:370 */             ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento().add(herramienta);
/* 381:371 */             mapaHerramientas.put(key, herramienta);
/* 382:    */           }
/* 383:374 */           BigDecimal cantidadAcumulada = herramienta.getCantidadRequerida();
/* 384:375 */           if (!indicadorMaximoValor) {
/* 385:376 */             cantidadAcumulada = cantidadAcumulada.add(detalleHerramienta.getCantidad());
/* 386:378 */           } else if (herramienta.getCantidadRequerida().compareTo(detalleHerramienta.getCantidad()) > 0) {
/* 387:379 */             cantidadAcumulada = herramienta.getCantidadRequerida();
/* 388:    */           } else {
/* 389:381 */             cantidadAcumulada = detalleHerramienta.getCantidad();
/* 390:    */           }
/* 391:384 */           herramienta.setCantidadRequerida(cantidadAcumulada);
/* 392:385 */           herramienta.setEliminado(false);
/* 393:    */         }
/* 394:    */       }
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void procesar(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 399:    */     throws AS2ExceptionMantenimiento
/* 400:    */   {
/* 401:393 */     OrdenTrabajoMantenimiento ordenBase = buscarPorId(Integer.valueOf(ordenTrabajoMantenimiento.getId()));
/* 402:394 */     if (ordenBase.getEstado().equals(Estado.ELABORADO)) {
/* 403:395 */       this.ordenTrabajoMantenimientoDao.actualizarEstado(ordenTrabajoMantenimiento, Estado.PROCESADO);
/* 404:    */     } else {
/* 405:397 */       throw new AS2ExceptionMantenimiento("msg_error_accion_no_permitida", new String[] { ordenBase.getEstado().toString() });
/* 406:    */     }
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void cargarOrdenTrabajoEnConsumoBodega(MovimientoInventario consumoBodega, OrdenTrabajoMantenimiento ordenTrabajoMantenimiento, Bodega bodega)
/* 410:    */     throws ExcepcionAS2
/* 411:    */   {
/* 412:405 */     ordenTrabajoMantenimiento = cargarDetalle(ordenTrabajoMantenimiento);
/* 413:407 */     for (DetalleMovimientoInventario detalle : consumoBodega.getDetalleMovimientosInventario()) {
/* 414:408 */       detalle.setEliminado(true);
/* 415:    */     }
/* 416:412 */     for (MaterialOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 417:413 */       crearDetalleConsumoBodega(detalle, consumoBodega, bodega);
/* 418:    */     }
/* 419:    */   }
/* 420:    */   
/* 421:    */   private void crearDetalleConsumoBodega(MaterialOrdenTrabajoMantenimiento materialOrdenTrabajo, MovimientoInventario consumoBodega, Bodega bodega)
/* 422:    */     throws ExcepcionAS2
/* 423:    */   {
/* 424:420 */     DetalleMovimientoInventario detalleConsumo = new DetalleMovimientoInventario();
/* 425:421 */     detalleConsumo.setIdOrganizacion(consumoBodega.getIdOrganizacion());
/* 426:422 */     detalleConsumo.setIdSucursal(consumoBodega.getSucursal().getId());
/* 427:423 */     detalleConsumo.setMovimientoInventario(consumoBodega);
/* 428:    */     
/* 429:425 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* 430:426 */     inventarioProducto.setOperacion(consumoBodega.getDocumento().getOperacion());
/* 431:427 */     inventarioProducto.setProducto(detalleConsumo.getProducto());
/* 432:428 */     inventarioProducto.setDetalleMovimientoInventario(detalleConsumo);
/* 433:429 */     inventarioProducto.setLote(detalleConsumo.getLote());
/* 434:430 */     detalleConsumo.setInventarioProducto(inventarioProducto);
/* 435:431 */     consumoBodega.getDetalleMovimientosInventario().add(detalleConsumo);
/* 436:    */     
/* 437:433 */     detalleConsumo.setProducto(materialOrdenTrabajo.getMaterial());
/* 438:434 */     detalleConsumo.setCantidad(materialOrdenTrabajo.getCantidadPorDespachar());
/* 439:435 */     detalleConsumo.setCantidadOrigen(materialOrdenTrabajo.getCantidadPorDespachar());
/* 440:436 */     detalleConsumo.setTraCantidadCoversion(materialOrdenTrabajo.getCantidadPorDespachar());
/* 441:437 */     detalleConsumo.setMaterialOrdenTrabajoMantenimiento(materialOrdenTrabajo);
/* 442:438 */     detalleConsumo.setUnidadConversion(materialOrdenTrabajo.getMaterial().getUnidad());
/* 443:439 */     this.servicioUnidadConversion.cargarListaUnidadConversion(this.servicioProducto.cargaDetalle(materialOrdenTrabajo.getMaterial().getId()));
/* 444:440 */     detalleConsumo.setBodegaOrigen(bodega);
/* 445:441 */     BigDecimal saldoBodega = this.servicioProducto.getSaldo(materialOrdenTrabajo.getMaterial().getId(), bodega == null ? 0 : bodega.getId(), consumoBodega
/* 446:442 */       .getFecha());
/* 447:443 */     detalleConsumo.setSaldo(saldoBodega);
/* 448:444 */     detalleConsumo.setDestinoCosto(materialOrdenTrabajo.getDestinoCosto());
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void actualizarOrdenTrabajo(MovimientoInventario consumoBodega)
/* 452:    */     throws AS2Exception
/* 453:    */   {
/* 454:    */     OrdenTrabajoMantenimiento ordenTrabajo;
/* 455:449 */     if ((consumoBodega.getOrdenTrabajoMantenimiento() != null) && 
/* 456:450 */       (consumoBodega.getDocumento().getDocumentoBase().equals(DocumentoBase.CONSUMO_BODEGA)))
/* 457:    */     {
/* 458:451 */       ordenTrabajo = null;
/* 459:452 */       if (consumoBodega.getOrdenTrabajoMantenimiento().getId() == 0)
/* 460:    */       {
/* 461:453 */         this.ordenTrabajoMantenimientoDao.guardar(consumoBodega.getOrdenTrabajoMantenimiento());
/* 462:454 */         ordenTrabajo = consumoBodega.getOrdenTrabajoMantenimiento();
/* 463:    */       }
/* 464:    */       else
/* 465:    */       {
/* 466:456 */         ordenTrabajo = cargarDetalle(consumoBodega.getOrdenTrabajoMantenimiento());
/* 467:    */       }
/* 468:459 */       for (DetalleMovimientoInventario detalle : consumoBodega.getDetalleMovimientosInventario()) {
/* 469:460 */         if ((!detalle.isEliminado()) && (detalle.getCantidad().compareTo(BigDecimal.ZERO) > 0))
/* 470:    */         {
/* 471:462 */           if (detalle.getMaterialOrdenTrabajoMantenimiento() == null)
/* 472:    */           {
/* 473:463 */             MaterialOrdenTrabajoMantenimiento material = buscarMaterialOrdenTrabajo(detalle.getProducto(), ordenTrabajo);
/* 474:464 */             if (material == null)
/* 475:    */             {
/* 476:465 */               material = new MaterialOrdenTrabajoMantenimiento();
/* 477:466 */               material.setIdOrganizacion(ordenTrabajo.getIdOrganizacion());
/* 478:467 */               material.setIdSucursal(ordenTrabajo.getIdSucursal());
/* 479:468 */               material.setMaterial(detalle.getProducto());
/* 480:469 */               material.setOrdenTrabajoMantenimiento(ordenTrabajo);
/* 481:470 */               ordenTrabajo.getListaMaterialOrdenTrabajoMantenimiento().add(material);
/* 482:471 */               this.materialOrdenTrabajoMantenimientoDao.guardar(material);
/* 483:    */             }
/* 484:473 */             detalle.setMaterialOrdenTrabajoMantenimiento(material);
/* 485:    */           }
/* 486:475 */           BigDecimal cantidad = detalle.getCantidad();
/* 487:476 */           if (consumoBodega.getEstado().equals(Estado.ANULADO)) {
/* 488:477 */             cantidad = cantidad.multiply(new BigDecimal(-1));
/* 489:    */           }
/* 490:480 */           this.ordenTrabajoMantenimientoDao.actualizarCantidadConsumidaMaterial(detalle.getMaterialOrdenTrabajoMantenimiento(), cantidad, BigDecimal.ZERO);
/* 491:    */         }
/* 492:    */       }
/* 493:    */     }
/* 494:484 */     else if ((consumoBodega.getOrdenTrabajoMantenimiento() != null) && 
/* 495:485 */       (consumoBodega.getDocumento().getDocumentoBase().equals(DocumentoBase.DEVOLUCION_CONSUMO_BODEGA)))
/* 496:    */     {
/* 497:486 */       for (DetalleMovimientoInventario detalle : consumoBodega.getDetalleMovimientosInventario()) {
/* 498:487 */         if ((!detalle.isEliminado()) && (detalle.getCantidad().compareTo(BigDecimal.ZERO) > 0))
/* 499:    */         {
/* 500:489 */           BigDecimal cantidad = detalle.getCantidad();
/* 501:490 */           if (consumoBodega.getEstado().equals(Estado.ANULADO)) {
/* 502:491 */             cantidad = cantidad.multiply(new BigDecimal(-1));
/* 503:    */           }
/* 504:493 */           this.ordenTrabajoMantenimientoDao.actualizarCantidadConsumidaMaterial(detalle.getMaterialOrdenTrabajoMantenimiento(), BigDecimal.ZERO, cantidad);
/* 505:    */         }
/* 506:    */       }
/* 507:    */     }
/* 508:    */     else
/* 509:    */     {
/* 510:499 */       for (DetalleMovimientoInventario detalle : consumoBodega.getDetalleMovimientosInventario()) {
/* 511:500 */         detalle.setMaterialOrdenTrabajoMantenimiento(null);
/* 512:    */       }
/* 513:    */     }
/* 514:    */   }
/* 515:    */   
/* 516:    */   private MaterialOrdenTrabajoMantenimiento buscarMaterialOrdenTrabajo(Producto material, OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 517:    */   {
/* 518:506 */     for (MaterialOrdenTrabajoMantenimiento materialOrdenTrabajo : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 519:507 */       if (materialOrdenTrabajo.getId() == material.getId()) {
/* 520:508 */         return materialOrdenTrabajo;
/* 521:    */       }
/* 522:    */     }
/* 523:511 */     return null;
/* 524:    */   }
/* 525:    */   
/* 526:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 527:    */   public void cerrarDetalleOrdenTrabajo(DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/* 528:    */     throws AS2ExceptionMantenimiento
/* 529:    */   {
/* 530:    */     try
/* 531:    */     {
/* 532:519 */       validaCierreDetalleOrdenTrabajo(detalleOrdenTrabajoMantenimiento);
/* 533:    */       
/* 534:    */ 
/* 535:    */ 
/* 536:523 */       detalleOrdenTrabajoMantenimiento.setIndicadorCerrada(true);
/* 537:524 */       this.detalleOrdenTrabajoMantenimientoDao.guardar(detalleOrdenTrabajoMantenimiento);
/* 538:    */       
/* 539:526 */       Map<String, String> filtros = new HashMap();
/* 540:527 */       filtros.put("detalleOrdenTrabajoMantenimiento.idDetalleOrdenTrabajoMantenimiento", detalleOrdenTrabajoMantenimiento.getId() + "");
/* 541:528 */       List<CalendarioMantenimientoEntidad> listaCalendario = this.calendarioMantenimientoDao.obtenerListaCombo("fecha", true, filtros);
/* 542:529 */       for (CalendarioMantenimientoEntidad calendario : listaCalendario) {
/* 543:530 */         this.calendarioMantenimientoDao.eliminar(calendario);
/* 544:    */       }
/* 545:553 */       for (ActividadResponsableOrdenTrabajoMantenimiento actividadResponsable : detalleOrdenTrabajoMantenimiento
/* 546:554 */         .getListaResponsableOrdenTrabajoMantenimiento())
/* 547:    */       {
/* 548:555 */         if (actividadResponsable.getHorasTrabajo().compareTo(BigDecimal.ZERO) == 0) {
/* 549:556 */           actividadResponsable.setEliminado(true);
/* 550:    */         }
/* 551:558 */         this.actividadResponsableOrdenTrabajoMantenimientoDao.guardar(actividadResponsable);
/* 552:    */       }
/* 553:561 */       for (ActividadMaterialOrdenTrabajoMantenimiento actividadMaterial : detalleOrdenTrabajoMantenimiento
/* 554:562 */         .getListaMaterialOrdenTrabajoMantenimiento())
/* 555:    */       {
/* 556:563 */         if (actividadMaterial.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
/* 557:564 */           actividadMaterial.setEliminado(true);
/* 558:    */         }
/* 559:566 */         this.actividadMaterialOrdenTrabajoMantenimientoDao.guardar(actividadMaterial);
/* 560:    */       }
/* 561:569 */       for (ActividadImagenOrdenTrabajoMantenimiento actividadImagen : detalleOrdenTrabajoMantenimiento
/* 562:570 */         .getListaImagenOrdenTrabajoMantenimiento())
/* 563:    */       {
/* 564:571 */         if (actividadImagen.getArchivo() == null) {
/* 565:572 */           actividadImagen.setEliminado(true);
/* 566:    */         }
/* 567:574 */         this.actividadImagenOrdenTrabajoMantenimientoDao.guardar(actividadImagen);
/* 568:    */       }
/* 569:577 */       for (LecturaMantenimiento lecturaMantenimiento : detalleOrdenTrabajoMantenimiento.getListaLecturaMantenimiento())
/* 570:    */       {
/* 571:578 */         lecturaMantenimiento.setIndicadorAutomatico(true);
/* 572:579 */         this.lecturaMantenimientoDao.guardar(lecturaMantenimiento);
/* 573:    */       }
/* 574:    */     }
/* 575:    */     catch (Exception e)
/* 576:    */     {
/* 577:582 */       this.context.setRollbackOnly();
/* 578:583 */       e.printStackTrace();
/* 579:584 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 580:    */     }
/* 581:    */   }
/* 582:    */   
/* 583:    */   public void cargarResponsablesporActividad(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento, DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/* 584:    */   {
/* 585:591 */     Set<Integer> idResponsablesAsignados = new HashSet();
/* 586:592 */     for (ActividadResponsableOrdenTrabajoMantenimiento actividadResponsable : detalleOrdenTrabajoMantenimiento
/* 587:593 */       .getListaResponsableOrdenTrabajoMantenimiento()) {
/* 588:594 */       idResponsablesAsignados.add(Integer.valueOf(actividadResponsable.getResponsableOrdenTrabajoMantenimiento().getId()));
/* 589:    */     }
/* 590:597 */     for (ResponsableOrdenTrabajoMantenimiento responsable : ordenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento()) {
/* 591:598 */       if (!idResponsablesAsignados.contains(Integer.valueOf(responsable.getId())))
/* 592:    */       {
/* 593:599 */         ActividadResponsableOrdenTrabajoMantenimiento actividadResponsable = new ActividadResponsableOrdenTrabajoMantenimiento();
/* 594:600 */         actividadResponsable.setIdOrganizacion(ordenTrabajoMantenimiento.getIdOrganizacion());
/* 595:601 */         actividadResponsable.setIdSucursal(ordenTrabajoMantenimiento.getIdSucursal());
/* 596:602 */         actividadResponsable.setDetalleOrdenTrabajoMantenimiento(detalleOrdenTrabajoMantenimiento);
/* 597:603 */         actividadResponsable.setResponsableOrdenTrabajoMantenimiento(responsable);
/* 598:604 */         idResponsablesAsignados.add(Integer.valueOf(responsable.getId()));
/* 599:605 */         detalleOrdenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento().add(actividadResponsable);
/* 600:    */       }
/* 601:    */     }
/* 602:    */   }
/* 603:    */   
/* 604:    */   public void cargarMaterialesporActividad(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento, DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/* 605:    */   {
/* 606:614 */     Map<Integer, BigDecimal> mapaCantidadesRequeridas = new HashMap();
/* 607:615 */     ActividadMantenimiento actividad = this.servicioActividadMantenimiento.cargarDetalle(detalleOrdenTrabajoMantenimiento.getActividadMantenimiento());
/* 608:616 */     for (Iterator localIterator = actividad.getListaMaterial().iterator(); localIterator.hasNext();)
/* 609:    */     {
/* 610:616 */       actividadMaterial = (ActividadMantenimientoMaterial)localIterator.next();
/* 611:617 */       mapaCantidadesRequeridas.put(Integer.valueOf(actividadMaterial.getProducto().getId()), actividadMaterial.getCantidad());
/* 612:    */     }
/* 613:    */     ActividadMantenimientoMaterial actividadMaterial;
/* 614:620 */     Object idMaterialesAsignados = new HashSet();
/* 615:621 */     for (ActividadMaterialOrdenTrabajoMantenimiento actividadMaterial : detalleOrdenTrabajoMantenimiento
/* 616:622 */       .getListaMaterialOrdenTrabajoMantenimiento()) {
/* 617:623 */       ((Set)idMaterialesAsignados).add(Integer.valueOf(actividadMaterial.getMaterialOrdenTrabajoMantenimiento().getId()));
/* 618:    */     }
/* 619:626 */     for (MaterialOrdenTrabajoMantenimiento material : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 620:627 */       if (!((Set)idMaterialesAsignados).contains(Integer.valueOf(material.getId())))
/* 621:    */       {
/* 622:628 */         ActividadMaterialOrdenTrabajoMantenimiento actividadMaterial = new ActividadMaterialOrdenTrabajoMantenimiento();
/* 623:629 */         actividadMaterial.setIdOrganizacion(ordenTrabajoMantenimiento.getIdOrganizacion());
/* 624:630 */         actividadMaterial.setIdSucursal(ordenTrabajoMantenimiento.getIdSucursal());
/* 625:631 */         actividadMaterial.setDetalleOrdenTrabajoMantenimiento(detalleOrdenTrabajoMantenimiento);
/* 626:632 */         actividadMaterial.setMaterialOrdenTrabajoMantenimiento(material);
/* 627:633 */         ((Set)idMaterialesAsignados).add(Integer.valueOf(material.getId()));
/* 628:634 */         detalleOrdenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento().add(actividadMaterial);
/* 629:    */         
/* 630:    */ 
/* 631:637 */         BigDecimal valor = (BigDecimal)mapaCantidadesRequeridas.get(Integer.valueOf(material.getMaterial().getId()));
/* 632:638 */         if (valor == null) {
/* 633:639 */           valor = BigDecimal.ZERO;
/* 634:    */         }
/* 635:641 */         actividadMaterial.setCantidad(valor);
/* 636:    */       }
/* 637:    */     }
/* 638:    */   }
/* 639:    */   
/* 640:    */   public void cerrarOrdenTrabajo(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 641:    */     throws AS2ExceptionMantenimiento
/* 642:    */   {
/* 643:648 */     ordenTrabajoMantenimiento = cargarDetalle(ordenTrabajoMantenimiento);
/* 644:649 */     validarCierreOrdenTrabajo(ordenTrabajoMantenimiento);
/* 645:    */     
/* 646:651 */     ordenTrabajoMantenimiento.setEstado(Estado.CERRADO);
/* 647:652 */     this.ordenTrabajoMantenimientoDao.guardar(ordenTrabajoMantenimiento);
/* 648:    */   }
/* 649:    */   
/* 650:    */   private void validarCierreOrdenTrabajo(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 651:    */     throws AS2ExceptionMantenimiento
/* 652:    */   {
/* 653:656 */     if (!ordenTrabajoMantenimiento.getEstado().equals(Estado.PROCESADO)) {
/* 654:657 */       throw new AS2ExceptionMantenimiento("msg_error_accion_no_permitida", new String[] { ordenTrabajoMantenimiento.getEstado().toString() });
/* 655:    */     }
/* 656:660 */     Map<Integer, BigDecimal> mapaValorMaterialesAsignados = new HashMap();
/* 657:661 */     for (DetalleOrdenTrabajoMantenimiento detalle : ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento()) {
/* 658:662 */       for (ActividadMaterialOrdenTrabajoMantenimiento actividadMaterial : detalle.getListaMaterialOrdenTrabajoMantenimiento())
/* 659:    */       {
/* 660:663 */         Integer idMaterial = Integer.valueOf(actividadMaterial.getMaterialOrdenTrabajoMantenimiento().getMaterial().getId());
/* 661:664 */         BigDecimal valorAcumulado = (BigDecimal)mapaValorMaterialesAsignados.get(idMaterial);
/* 662:665 */         if (valorAcumulado == null) {
/* 663:666 */           valorAcumulado = BigDecimal.ZERO;
/* 664:    */         }
/* 665:668 */         valorAcumulado = valorAcumulado.add(actividadMaterial.getCantidad());
/* 666:669 */         mapaValorMaterialesAsignados.put(idMaterial, valorAcumulado);
/* 667:    */       }
/* 668:    */     }
/* 669:674 */     for (MaterialOrdenTrabajoMantenimiento detaleMaterial : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento())
/* 670:    */     {
/* 671:675 */       Integer idMaterial = Integer.valueOf(detaleMaterial.getMaterial().getId());
/* 672:676 */       BigDecimal valorAcumulado = (BigDecimal)mapaValorMaterialesAsignados.get(idMaterial);
/* 673:677 */       if (valorAcumulado == null) {
/* 674:678 */         valorAcumulado = BigDecimal.ZERO;
/* 675:    */       }
/* 676:680 */       if (valorAcumulado.compareTo(detaleMaterial.getCantidadConsumida()) != 0) {
/* 677:682 */         throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_ASIGNACION_DETALLES_MATERIAL", new String[] { detaleMaterial.getMaterial().getNombre(), valorAcumulado.toString() + " != " + detaleMaterial.getCantidadConsumida().toString() });
/* 678:    */       }
/* 679:    */     }
/* 680:    */   }
/* 681:    */   
/* 682:    */   public void guardarOrdentTrabajo(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento, ConsumoAgilMantenimiento consumoAgilMantenimiento)
/* 683:    */     throws AS2ExceptionMantenimiento
/* 684:    */   {
/* 685:    */     try
/* 686:    */     {
/* 687:693 */       List<ResponsableOrdenTrabajoMantenimiento> listaResponsableOrdenTrabajoMantenimiento = new ArrayList();
/* 688:694 */       ResponsableOrdenTrabajoMantenimiento responsable = new ResponsableOrdenTrabajoMantenimiento();
/* 689:695 */       responsable.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 690:696 */       responsable.setIdSucursal(AppUtil.getSucursal().getId());
/* 691:697 */       responsable.setOrdenTrabajoMantenimiento(ordenTrabajoMantenimiento);
/* 692:698 */       responsable.setPersonaResponsable(consumoAgilMantenimiento.getResponsableSalidaMercaderia());
/* 693:699 */       listaResponsableOrdenTrabajoMantenimiento.add(responsable);
/* 694:    */       
/* 695:701 */       List<DetalleOrdenTrabajoMantenimiento> listaDetalleOrdenTrabajoMantenimiento = new ArrayList();
/* 696:702 */       HashMap<String, DetalleOrdenTrabajoMantenimiento> mapEquipo = new HashMap();
/* 697:703 */       Set<String> detalleKey = new HashSet();
/* 698:705 */       if (!consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento().isEmpty())
/* 699:    */       {
/* 700:706 */         for (DetalleConsumoAgilMantenimiento detalleDCAM : consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento()) {
/* 701:709 */           if (!detalleDCAM.isEliminado())
/* 702:    */           {
/* 703:711 */             String key = detalleDCAM.getEquipo().getId() + "~" + detalleDCAM.getComponenteEquipo().getId() + "~" + detalleDCAM.getActividadMantenimiento().getId();
/* 704:712 */             if (!detalleKey.contains(key))
/* 705:    */             {
/* 706:713 */               detalle = new DetalleOrdenTrabajoMantenimiento();
/* 707:714 */               detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 708:715 */               detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 709:716 */               detalle.setOrdenTrabajoMantenimiento(ordenTrabajoMantenimiento);
/* 710:717 */               detalle.setEquipo(detalleDCAM.getEquipo());
/* 711:718 */               detalle.setComponenteEquipo(detalleDCAM.getComponenteEquipo());
/* 712:719 */               detalle.setActividadMantenimiento(detalleDCAM.getActividadMantenimiento());
/* 713:720 */               detalleKey.add(key);
/* 714:721 */               listaDetalleOrdenTrabajoMantenimiento.add(detalle);
/* 715:    */             }
/* 716:    */           }
/* 717:    */         }
/* 718:    */         DetalleOrdenTrabajoMantenimiento detalle;
/* 719:762 */         Object listaMaterialOrdenTrabajoMantenimiento = new ArrayList();
/* 720:763 */         HashMap<Integer, MaterialOrdenTrabajoMantenimiento> mapMOTM = new HashMap();
/* 721:764 */         for (DetalleConsumoAgilMantenimiento detalleDCAM : consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento())
/* 722:    */         {
/* 723:765 */           Producto p = this.servicioProducto.cargaDetalle(detalleDCAM.getMaterial().getId());
/* 724:767 */           if (mapMOTM.get(Integer.valueOf(p.getId())) != null)
/* 725:    */           {
/* 726:768 */             MaterialOrdenTrabajoMantenimiento material = (MaterialOrdenTrabajoMantenimiento)mapMOTM.get(Integer.valueOf(p.getId()));
/* 727:769 */             material.setCantidadRequerida(material.getCantidadRequerida().add(detalleDCAM.getCantidad()));
/* 728:    */           }
/* 729:    */           else
/* 730:    */           {
/* 731:771 */             MaterialOrdenTrabajoMantenimiento material = new MaterialOrdenTrabajoMantenimiento();
/* 732:772 */             material.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 733:773 */             material.setIdSucursal(AppUtil.getSucursal().getId());
/* 734:774 */             material.setOrdenTrabajoMantenimiento(ordenTrabajoMantenimiento);
/* 735:775 */             material.setMaterial(p);
/* 736:776 */             System.out.println("---------------->" + detalleDCAM.getCantidad());
/* 737:777 */             material.setCantidadRequerida(detalleDCAM.getCantidad());
/* 738:778 */             material.setDestinoCosto(detalleDCAM.getDestinoCosto());
/* 739:779 */             material.setBodegaOrigen(detalleDCAM.getBodegaOrigen());
/* 740:780 */             material.setLote(detalleDCAM.getLote());
/* 741:781 */             mapMOTM.put(Integer.valueOf(p.getId()), material);
/* 742:    */           }
/* 743:    */         }
/* 744:784 */         listaDetalleOrdenTrabajoMantenimiento.addAll(mapEquipo.values());
/* 745:785 */         ((List)listaMaterialOrdenTrabajoMantenimiento).addAll(mapMOTM.values());
/* 746:786 */         ordenTrabajoMantenimiento.setListaResponsableOrdenTrabajoMantenimiento(listaResponsableOrdenTrabajoMantenimiento);
/* 747:787 */         ordenTrabajoMantenimiento.setListaDetalleOrdenTrabajoMantenimiento(listaDetalleOrdenTrabajoMantenimiento);
/* 748:788 */         ordenTrabajoMantenimiento.setListaMaterialOrdenTrabajoMantenimiento((List)listaMaterialOrdenTrabajoMantenimiento);
/* 749:    */       }
/* 750:    */       else
/* 751:    */       {
/* 752:790 */         throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_NO_DETALLE", new String[] { "DETALLES" });
/* 753:    */       }
/* 754:    */     }
/* 755:    */     catch (Exception e)
/* 756:    */     {
/* 757:795 */       this.context.setRollbackOnly();
/* 758:796 */       e.printStackTrace();
/* 759:797 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 760:    */     }
/* 761:    */   }
/* 762:    */   
/* 763:    */   public void cargarOrdenTrabajoEnConsumoBodega(MovimientoInventario consumoBodega, OrdenTrabajoMantenimiento ordenTrabajoMantenimiento, ConsumoAgilMantenimiento consumoAgilMantenimiento)
/* 764:    */     throws ExcepcionAS2, AS2ExceptionMantenimiento
/* 765:    */   {
/* 766:805 */     ordenTrabajoMantenimiento = cargarDetalle(ordenTrabajoMantenimiento);
/* 767:807 */     for (DetalleMovimientoInventario detalle : consumoBodega.getDetalleMovimientosInventario()) {
/* 768:808 */       detalle.setEliminado(true);
/* 769:    */     }
/* 770:812 */     for (MaterialOrdenTrabajoMantenimiento material : ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 771:813 */       crearDetalleConsumoBodega(material, consumoBodega);
/* 772:    */     }
/* 773:    */   }
/* 774:    */   
/* 775:    */   public void procesarConsumoAgilMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento, ConsumoAgilMantenimiento consumoAgilMantenimiento, MovimientoInventario movimientoInventario)
/* 776:    */     throws AS2ExceptionMantenimiento, ExcepcionAS2
/* 777:    */   {
/* 778:822 */     validarDetalleConsumoAgilMantenimiento(consumoAgilMantenimiento);
/* 779:823 */     guardarOrdentTrabajo(ordenTrabajoMantenimiento, consumoAgilMantenimiento);
/* 780:    */     try
/* 781:    */     {
/* 782:825 */       ordenTrabajoMantenimiento.setIndicadorAgil(true);
/* 783:826 */       guardar(ordenTrabajoMantenimiento);
/* 784:827 */       if (movimientoInventario.getOrdenTrabajoMantenimiento() != null)
/* 785:    */       {
/* 786:828 */         cargarOrdenTrabajoEnConsumoBodega(movimientoInventario, ordenTrabajoMantenimiento, consumoAgilMantenimiento);
/* 787:829 */         this.servicioMovimientoInventario.guardar(movimientoInventario);
/* 788:    */       }
/* 789:    */     }
/* 790:    */     catch (Exception e)
/* 791:    */     {
/* 792:832 */       this.context.setRollbackOnly();
/* 793:833 */       e.printStackTrace();
/* 794:834 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 795:    */     }
/* 796:    */   }
/* 797:    */   
/* 798:    */   private void crearDetalleConsumoBodega(MaterialOrdenTrabajoMantenimiento materialOrdenTrabajo, MovimientoInventario consumoBodega)
/* 799:    */     throws ExcepcionAS2
/* 800:    */   {
/* 801:840 */     DetalleMovimientoInventario detalleConsumo = new DetalleMovimientoInventario();
/* 802:841 */     detalleConsumo.setIdOrganizacion(consumoBodega.getIdOrganizacion());
/* 803:842 */     detalleConsumo.setIdSucursal(consumoBodega.getSucursal().getId());
/* 804:843 */     detalleConsumo.setMovimientoInventario(consumoBodega);
/* 805:    */     
/* 806:845 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* 807:846 */     inventarioProducto.setOperacion(consumoBodega.getDocumento().getOperacion());
/* 808:847 */     inventarioProducto.setProducto(detalleConsumo.getProducto());
/* 809:848 */     inventarioProducto.setDetalleMovimientoInventario(detalleConsumo);
/* 810:849 */     inventarioProducto.setLote(detalleConsumo.getLote());
/* 811:850 */     detalleConsumo.setInventarioProducto(inventarioProducto);
/* 812:851 */     consumoBodega.getDetalleMovimientosInventario().add(detalleConsumo);
/* 813:    */     
/* 814:853 */     detalleConsumo.setProducto(materialOrdenTrabajo.getMaterial());
/* 815:854 */     detalleConsumo.setCantidad(materialOrdenTrabajo.getCantidadPorDespachar());
/* 816:855 */     detalleConsumo.setCantidadOrigen(materialOrdenTrabajo.getCantidadPorDespachar());
/* 817:856 */     detalleConsumo.setTraCantidadCoversion(materialOrdenTrabajo.getCantidadPorDespachar());
/* 818:857 */     detalleConsumo.setMaterialOrdenTrabajoMantenimiento(materialOrdenTrabajo);
/* 819:858 */     detalleConsumo.setUnidadConversion(materialOrdenTrabajo.getMaterial().getUnidad());
/* 820:859 */     detalleConsumo.setDestinoCosto(materialOrdenTrabajo.getDestinoCosto());
/* 821:860 */     detalleConsumo.setLote(materialOrdenTrabajo.getLote());
/* 822:861 */     this.servicioUnidadConversion.cargarListaUnidadConversion(this.servicioProducto.cargaDetalle(materialOrdenTrabajo.getMaterial().getId()));
/* 823:862 */     detalleConsumo.setBodegaOrigen(materialOrdenTrabajo.getBodegaOrigen());
/* 824:863 */     BigDecimal saldoBodega = this.servicioProducto.getSaldo(materialOrdenTrabajo.getMaterial().getId(), materialOrdenTrabajo
/* 825:864 */       .getBodegaOrigen() == null ? 0 : materialOrdenTrabajo.getBodegaOrigen().getId(), consumoBodega.getFecha());
/* 826:865 */     detalleConsumo.setSaldo(saldoBodega);
/* 827:    */   }
/* 828:    */   
/* 829:    */   public List<Object[]> getReporteCostoHistoricoMantenimiento(Date fechaDesde, Date fechaHasta, CategoriaEquipo categoriaEquipo, SubcategoriaEquipo subcategoriaEquipo, Equipo equipo, ComponenteEquipo componenteEquipo, ActividadMantenimiento actividadMantenimiento, OrdenTrabajoMantenimiento otm, String tipoReporte, int agrupar)
/* 830:    */   {
/* 831:872 */     return this.ordenTrabajoMantenimientoDao.getReporteCostoHistoricoMantenimiento(fechaDesde, fechaHasta, categoriaEquipo, subcategoriaEquipo, equipo, componenteEquipo, actividadMantenimiento, otm, tipoReporte, agrupar);
/* 832:    */   }
/* 833:    */   
/* 834:    */   public void validarDetalleConsumoAgilMantenimiento(ConsumoAgilMantenimiento consumoAgilMantenimiento)
/* 835:    */     throws AS2ExceptionMantenimiento
/* 836:    */   {
/* 837:877 */     if (consumoAgilMantenimiento.getResponsableSalidaMercaderia() == null) {
/* 838:878 */       throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_SELECCIONE_RESPONSABLE", new String[] { "RESPONSABLE" });
/* 839:    */     }
/* 840:879 */     for (DetalleConsumoAgilMantenimiento detalle : consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento()) {
/* 841:880 */       if (!detalle.isEliminado())
/* 842:    */       {
/* 843:881 */         if (detalle.getDestinoCosto() == null) {
/* 844:883 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_SELECCIONE_DESTINO_COSTO", new String[] { "DESTINO DE COSTO", detalle.getMaterial().getNombre() });
/* 845:    */         }
/* 846:884 */         if (detalle.getBodegaOrigen() == null) {
/* 847:885 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_SELECCIONE_BODEGA", new String[] { "BODEGA", detalle.getMaterial().getNombre() });
/* 848:    */         }
/* 849:886 */         if (detalle.getEquipo() == null) {
/* 850:887 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_SELECCIONE_EQUIPO", new String[] { "EQUIPO" });
/* 851:    */         }
/* 852:888 */         if (detalle.getComponenteEquipo() == null) {
/* 853:889 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_SELECCIONE_COMPONENTE", new String[] { "COMPONENTE", detalle.getEquipo().getNombre() });
/* 854:    */         }
/* 855:890 */         if (detalle.getActividadMantenimiento() == null) {
/* 856:891 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_SELECCIONE_ACTIVIDAD_MANTENIMIENTO", new String[] { "ACTIVIDAD" });
/* 857:    */         }
/* 858:892 */         if ((detalle.getMaterial().isIndicadorLote()) && 
/* 859:893 */           (detalle.getLote() == null)) {
/* 860:894 */           throw new AS2ExceptionMantenimiento("msg_error_lote_requerido", new String[] { detalle.getMaterial().getNombre() });
/* 861:    */         }
/* 862:    */       }
/* 863:    */     }
/* 864:    */   }
/* 865:    */   
/* 866:    */   private void validaCierreDetalleOrdenTrabajo(DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/* 867:    */     throws AS2ExceptionMantenimiento
/* 868:    */   {
/* 869:901 */     Date fechalectura = null;
/* 870:902 */     if ((detalleOrdenTrabajoMantenimiento.isIndicadorDesplazarFecha()) || (detalleOrdenTrabajoMantenimiento.getFechaPlanificadaOriginal() == null)) {
/* 871:903 */       fechalectura = detalleOrdenTrabajoMantenimiento.getFechaCierre();
/* 872:    */     } else {
/* 873:905 */       fechalectura = detalleOrdenTrabajoMantenimiento.getFechaPlanificadaOriginal();
/* 874:    */     }
/* 875:908 */     LecturaMantenimiento lecturaMantenimiento = this.lecturaMantenimientoDao.obtenerUltimaLectura(detalleOrdenTrabajoMantenimiento.getEquipo(), detalleOrdenTrabajoMantenimiento
/* 876:909 */       .getComponenteEquipo(), null, null, true);
/* 877:910 */     if ((lecturaMantenimiento != null) && (lecturaMantenimiento.getFechaLectura().compareTo(fechalectura) >= 0)) {
/* 878:911 */       throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_FECHA_CIERRE_MAYOR_FECHA_MAX_MANTENIMIENTO", new String[] { "" });
/* 879:    */     }
/* 880:    */   }
/* 881:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl
 * JD-Core Version:    0.7.0.1
 */