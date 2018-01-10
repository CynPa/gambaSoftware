/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioEspecialidad;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Especialidad;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoEspecialidad;
/*  12:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoHerramienta;
/*  13:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoMaterial;
/*  14:    */ import com.asinfo.as2.entities.mantenimiento.Herramienta;
/*  15:    */ import com.asinfo.as2.entities.mantenimiento.TipoActividad;
/*  16:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  20:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioActividadMantenimiento;
/*  21:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.JsfUtil;
/*  24:    */ import java.math.BigDecimal;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.annotation.PostConstruct;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.faces.bean.ManagedBean;
/*  31:    */ import javax.faces.bean.ManagedProperty;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import javax.faces.component.html.HtmlInputText;
/*  34:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.component.datatable.DataTable;
/*  37:    */ import org.primefaces.model.LazyDataModel;
/*  38:    */ import org.primefaces.model.SortOrder;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class ActividadMantenimientoBean
/*  43:    */   extends PageControllerAS2
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 1L;
/*  46:    */   @EJB
/*  47:    */   private ServicioActividadMantenimiento servicioActividadMantenimiento;
/*  48:    */   @EJB
/*  49:    */   private ServicioGenerico<TipoActividad> servicioTipoActividad;
/*  50:    */   @EJB
/*  51:    */   private ServicioGenerico<Herramienta> servicioHerramienta;
/*  52:    */   @EJB
/*  53:    */   private ServicioEspecialidad servicioEspecialidad;
/*  54:    */   @EJB
/*  55:    */   private ServicioProducto servicioProducto;
/*  56:    */   private ActividadMantenimiento actividadMantenimiento;
/*  57:    */   private LazyDataModel<ActividadMantenimiento> listaActividadMantenimiento;
/*  58:    */   private List<TipoActividad> listaTipoActividad;
/*  59:    */   private DataTable dtActividadMantenimiento;
/*  60:    */   private DataTable dtHerramienta;
/*  61:    */   private DataTable dtMaterial;
/*  62:    */   private DataTable dtEspecialidad;
/*  63:    */   @ManagedProperty("#{listaProductoBean}")
/*  64:    */   private ListaProductoBean listaProductoBean;
/*  65:    */   
/*  66:    */   @PostConstruct
/*  67:    */   public void init()
/*  68:    */   {
/*  69: 82 */     getListaProductoBean().setIndicadorMantenimiento(true);
/*  70: 83 */     this.listaActividadMantenimiento = new LazyDataModel()
/*  71:    */     {
/*  72:    */       private static final long serialVersionUID = 1L;
/*  73:    */       
/*  74:    */       public List<ActividadMantenimiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  75:    */       {
/*  76: 90 */         filters = ActividadMantenimientoBean.this.agregarFiltroOrganizacion(filters);
/*  77:    */         
/*  78: 92 */         List<ActividadMantenimiento> lista = new ArrayList();
/*  79: 93 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  80:    */         
/*  81: 95 */         lista = ActividadMantenimientoBean.this.servicioActividadMantenimiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  82: 96 */         ActividadMantenimientoBean.this.listaActividadMantenimiento.setRowCount(ActividadMantenimientoBean.this.servicioActividadMantenimiento.contarPorCriterio(filters));
/*  83:    */         
/*  84: 98 */         return lista;
/*  85:    */       }
/*  86:    */     };
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String editar()
/*  90:    */   {
/*  91:111 */     if ((getActividad() != null) && (getActividad().getId() != 0))
/*  92:    */     {
/*  93:112 */       this.actividadMantenimiento = this.servicioActividadMantenimiento.cargarDetalle(this.actividadMantenimiento);
/*  94:113 */       setEditado(true);
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  99:    */     }
/* 100:117 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String guardar()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:128 */       this.servicioActividadMantenimiento.guardar(this.actividadMantenimiento);
/* 108:129 */       limpiar();
/* 109:130 */       setEditado(false);
/* 110:131 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 111:    */     }
/* 112:    */     catch (AS2ExceptionMantenimiento e)
/* 113:    */     {
/* 114:133 */       JsfUtil.addErrorMessage(e, "");
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:135 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 119:136 */       e.printStackTrace();
/* 120:    */     }
/* 121:138 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String limpiar()
/* 125:    */   {
/* 126:148 */     crearActividad();
/* 127:149 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String eliminar()
/* 131:    */   {
/* 132:159 */     if ((getActividad() != null) && (getActividad().getId() != 0)) {
/* 133:    */       try
/* 134:    */       {
/* 135:161 */         this.servicioActividadMantenimiento.eliminar(this.actividadMantenimiento);
/* 136:162 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 137:    */       }
/* 138:    */       catch (AS2ExceptionMantenimiento e)
/* 139:    */       {
/* 140:164 */         JsfUtil.addErrorMessage(e, "");
/* 141:    */       }
/* 142:    */       catch (Exception e)
/* 143:    */       {
/* 144:166 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 145:167 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 146:    */       }
/* 147:    */     } else {
/* 148:170 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 149:    */     }
/* 150:172 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String cargarDatos()
/* 154:    */   {
/* 155:182 */     return "";
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void crearActividad()
/* 159:    */   {
/* 160:189 */     this.actividadMantenimiento = new ActividadMantenimiento();
/* 161:190 */     this.actividadMantenimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 162:191 */     this.actividadMantenimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 163:192 */     this.actividadMantenimiento.setActivo(true);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public ActividadMantenimiento getActividad()
/* 167:    */   {
/* 168:201 */     if (this.actividadMantenimiento == null) {
/* 169:202 */       crearActividad();
/* 170:    */     }
/* 171:204 */     return this.actividadMantenimiento;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public List<TipoActividad> getListaTipoActividad()
/* 175:    */   {
/* 176:211 */     if (this.listaTipoActividad == null)
/* 177:    */     {
/* 178:212 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 179:213 */       this.listaTipoActividad = this.servicioTipoActividad.obtenerListaCombo(TipoActividad.class, "nombre", true, filters);
/* 180:    */     }
/* 181:216 */     return this.listaTipoActividad;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setListaTipoActividad(List<TipoActividad> listaTipoActividad)
/* 185:    */   {
/* 186:224 */     this.listaTipoActividad = listaTipoActividad;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public ActividadMantenimiento getActividadMantenimiento()
/* 190:    */   {
/* 191:231 */     return this.actividadMantenimiento;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setActividadMantenimiento(ActividadMantenimiento actividadMantenimiento)
/* 195:    */   {
/* 196:239 */     this.actividadMantenimiento = actividadMantenimiento;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public LazyDataModel<ActividadMantenimiento> getListaActividadMantenimiento()
/* 200:    */   {
/* 201:246 */     return this.listaActividadMantenimiento;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setListaActividadMantenimiento(LazyDataModel<ActividadMantenimiento> listaActividadMantenimiento)
/* 205:    */   {
/* 206:254 */     this.listaActividadMantenimiento = listaActividadMantenimiento;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public DataTable getDtActividadMantenimiento()
/* 210:    */   {
/* 211:261 */     return this.dtActividadMantenimiento;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setDtActividadMantenimiento(DataTable dtActividadMantenimiento)
/* 215:    */   {
/* 216:269 */     this.dtActividadMantenimiento = dtActividadMantenimiento;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public DataTable getDtHerramienta()
/* 220:    */   {
/* 221:273 */     return this.dtHerramienta;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setDtHerramienta(DataTable dtHerramienta)
/* 225:    */   {
/* 226:277 */     this.dtHerramienta = dtHerramienta;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public DataTable getDtMaterial()
/* 230:    */   {
/* 231:281 */     return this.dtMaterial;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setDtMaterial(DataTable dtMaterial)
/* 235:    */   {
/* 236:285 */     this.dtMaterial = dtMaterial;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public DataTable getDtEspecialidad()
/* 240:    */   {
/* 241:289 */     return this.dtEspecialidad;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setDtEspecialidad(DataTable dtEspecialidad)
/* 245:    */   {
/* 246:293 */     this.dtEspecialidad = dtEspecialidad;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public List<ActividadMantenimientoHerramienta> getListaHerramienta()
/* 250:    */   {
/* 251:297 */     List<ActividadMantenimientoHerramienta> lista = new ArrayList();
/* 252:298 */     for (ActividadMantenimientoHerramienta detalle : this.actividadMantenimiento.getListaHerramienta()) {
/* 253:299 */       if (!detalle.isEliminado()) {
/* 254:300 */         lista.add(detalle);
/* 255:    */       }
/* 256:    */     }
/* 257:304 */     return lista;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public List<ActividadMantenimientoMaterial> getListaMaterial()
/* 261:    */   {
/* 262:308 */     List<ActividadMantenimientoMaterial> lista = new ArrayList();
/* 263:309 */     for (ActividadMantenimientoMaterial detalle : this.actividadMantenimiento.getListaMaterial()) {
/* 264:310 */       if (!detalle.isEliminado()) {
/* 265:311 */         lista.add(detalle);
/* 266:    */       }
/* 267:    */     }
/* 268:315 */     return lista;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public List<ActividadMantenimientoEspecialidad> getListaEspecialidad()
/* 272:    */   {
/* 273:319 */     List<ActividadMantenimientoEspecialidad> lista = new ArrayList();
/* 274:320 */     for (ActividadMantenimientoEspecialidad detalle : this.actividadMantenimiento.getListaEspecialidad()) {
/* 275:321 */       if (!detalle.isEliminado()) {
/* 276:322 */         lista.add(detalle);
/* 277:    */       }
/* 278:    */     }
/* 279:326 */     return lista;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void eliminarDetalleHerramienta()
/* 283:    */   {
/* 284:330 */     ActividadMantenimientoHerramienta detalle = (ActividadMantenimientoHerramienta)this.dtHerramienta.getRowData();
/* 285:331 */     detalle.setEliminado(true);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void eliminarDetalleMaterial()
/* 289:    */   {
/* 290:335 */     ActividadMantenimientoMaterial detalle = (ActividadMantenimientoMaterial)this.dtMaterial.getRowData();
/* 291:336 */     detalle.setEliminado(true);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void eliminarDetalleEspecialidad()
/* 295:    */   {
/* 296:340 */     ActividadMantenimientoEspecialidad detalle = (ActividadMantenimientoEspecialidad)this.dtEspecialidad.getRowData();
/* 297:341 */     detalle.setEliminado(true);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List<Herramienta> autocompletarHerramienta(String consulta)
/* 301:    */   {
/* 302:345 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 303:346 */     filtros.put("activo", "true");
/* 304:347 */     filtros.put("OR~codigo", "%" + consulta + "%");
/* 305:348 */     filtros.put("OR~nombre", "%" + consulta + "%");
/* 306:    */     
/* 307:350 */     return this.servicioHerramienta.obtenerListaCombo(Herramienta.class, "nombre", true, filtros);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public List<Especialidad> autocompletarEspecialidad(String consulta)
/* 311:    */   {
/* 312:354 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 313:355 */     filtros.put("activo", "true");
/* 314:356 */     filtros.put("OR~codigo", "%" + consulta + "%");
/* 315:357 */     filtros.put("OR~nombre", "%" + consulta + "%");
/* 316:    */     
/* 317:359 */     return this.servicioEspecialidad.obtenerListaCombo("nombre", true, filtros);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void agregarDetalleHerramienta()
/* 321:    */   {
/* 322:363 */     ActividadMantenimientoHerramienta detalle = new ActividadMantenimientoHerramienta();
/* 323:364 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 324:365 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 325:366 */     detalle.setActividadMantenimiento(this.actividadMantenimiento);
/* 326:367 */     detalle.setCantidad(BigDecimal.ZERO);
/* 327:368 */     this.actividadMantenimiento.getListaHerramienta().add(detalle);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void agregarDetalleEspecialidad()
/* 331:    */   {
/* 332:372 */     ActividadMantenimientoEspecialidad detalle = new ActividadMantenimientoEspecialidad();
/* 333:373 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 334:374 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 335:375 */     detalle.setActividadMantenimiento(this.actividadMantenimiento);
/* 336:376 */     this.actividadMantenimiento.getListaEspecialidad().add(detalle);
/* 337:    */   }
/* 338:    */   
/* 339:    */   public ActividadMantenimientoMaterial agregarDetalleMaterial()
/* 340:    */   {
/* 341:380 */     ActividadMantenimientoMaterial detalle = new ActividadMantenimientoMaterial();
/* 342:381 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 343:382 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 344:383 */     detalle.setActividadMantenimiento(this.actividadMantenimiento);
/* 345:384 */     detalle.setProducto(new Producto());
/* 346:385 */     this.actividadMantenimiento.getListaMaterial().add(detalle);
/* 347:386 */     return detalle;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void cargarProducto(Producto producto)
/* 351:    */   {
/* 352:390 */     ActividadMantenimientoMaterial detalle = agregarDetalleMaterial();
/* 353:391 */     detalle.setProducto(producto);
/* 354:392 */     detalle.setCantidad(producto.getTraCantidad());
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 358:    */   {
/* 359:396 */     ActividadMantenimientoMaterial detalle = null;
/* 360:    */     try
/* 361:    */     {
/* 362:398 */       String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 363:399 */       detalle = (ActividadMantenimientoMaterial)this.dtMaterial.getRowData();
/* 364:400 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 365:401 */       detalle.setProducto(producto);
/* 366:    */     }
/* 367:    */     catch (ExcepcionAS2 e)
/* 368:    */     {
/* 369:403 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 370:404 */       detalle.getProducto().setCodigo("");
/* 371:405 */       detalle.getProducto().setNombre("");
/* 372:    */     }
/* 373:    */   }
/* 374:    */   
/* 375:    */   public ListaProductoBean getListaProductoBean()
/* 376:    */   {
/* 377:410 */     return this.listaProductoBean;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 381:    */   {
/* 382:414 */     this.listaProductoBean = listaProductoBean;
/* 383:    */   }
/* 384:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.ActividadMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */