/*   1:    */ package com.asinfo.as2.produccion.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCentroTrabajo;
/*   6:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*  11:    */ import com.asinfo.as2.entities.produccion.OperacionProduccion;
/*  12:    */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacion;
/*  13:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  14:    */ import com.asinfo.as2.entities.produccion.TareaProduccion;
/*  15:    */ import com.asinfo.as2.entities.produccion.TarifaOperacion;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  19:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMaquina;
/*  20:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioTareaProduccion;
/*  21:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioTarifaOperacion;
/*  22:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.annotation.PostConstruct;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.faces.bean.ManagedBean;
/*  31:    */ import javax.faces.bean.ManagedProperty;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.component.datatable.DataTable;
/*  35:    */ import org.primefaces.event.SelectEvent;
/*  36:    */ import org.primefaces.model.LazyDataModel;
/*  37:    */ import org.primefaces.model.SortOrder;
/*  38:    */ 
/*  39:    */ @ManagedBean
/*  40:    */ @ViewScoped
/*  41:    */ public class RutaFabricacionBean
/*  42:    */   extends PageControllerAS2
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  45:    */   @EJB
/*  46:    */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  47:    */   @EJB
/*  48:    */   private ServicioTareaProduccion servicioTareaProduccion;
/*  49:    */   @EJB
/*  50:    */   private ServicioProducto servicioProducto;
/*  51:    */   @EJB
/*  52:    */   private ServicioTarifaOperacion servicioTarifaOperacion;
/*  53:    */   @EJB
/*  54:    */   private ServicioCentroTrabajo servicioCentroTrabajo;
/*  55:    */   @EJB
/*  56:    */   private ServicioMaquina servicioMaquina;
/*  57:    */   private RutaFabricacion rutaFabricacion;
/*  58: 88 */   private OperacionProduccion operacionProduccion = new OperacionProduccion();
/*  59:    */   @ManagedProperty("#{listaProductoBean}")
/*  60:    */   private ListaProductoBean listaProductoBean;
/*  61:    */   private LazyDataModel<RutaFabricacion> listaRutaFabricacion;
/*  62:    */   private List<TareaProduccion> listaTareaProduccion;
/*  63:    */   private List<TarifaOperacion> listaTarifaOperacion;
/*  64:    */   private List<CentroTrabajo> listaCentroTrabajo;
/*  65:    */   private List<Maquina> listaMaquina;
/*  66:    */   private List<TipoCicloProduccionEnum> listaTipoCicloEnum;
/*  67:    */   private DataTable dtRutaFabricacion;
/*  68:    */   private DataTable dtOperacionRutaFabricacion;
/*  69:    */   
/*  70:    */   @PostConstruct
/*  71:    */   public void init()
/*  72:    */   {
/*  73:115 */     this.listaRutaFabricacion = new LazyDataModel()
/*  74:    */     {
/*  75:    */       private static final long serialVersionUID = 1L;
/*  76:    */       
/*  77:    */       public List<RutaFabricacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  78:    */       {
/*  79:122 */         List<RutaFabricacion> lista = new ArrayList();
/*  80:123 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  81:    */         
/*  82:125 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  83:126 */         lista = RutaFabricacionBean.this.servicioRutaFabricacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  84:    */         
/*  85:128 */         RutaFabricacionBean.this.listaRutaFabricacion.setRowCount(RutaFabricacionBean.this.servicioRutaFabricacion.contarPorCriterio(filters));
/*  86:    */         
/*  87:130 */         return lista;
/*  88:    */       }
/*  89:    */     };
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void agregarOperacion()
/*  93:    */   {
/*  94:145 */     this.operacionProduccion = new OperacionProduccion();
/*  95:146 */     this.operacionProduccion.setRutaFabricacion(this.rutaFabricacion);
/*  96:147 */     this.operacionProduccion.setFechaDesde(new Date());
/*  97:148 */     this.rutaFabricacion.getListaOperacionProduccion().add(this.operacionProduccion);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void eliminarOperacion(OperacionProduccion operacion)
/* 101:    */   {
/* 102:152 */     operacion.setEliminado(true);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void eliminarProducto(ProductoRutaFabricacion productoRutaFabricacion)
/* 106:    */   {
/* 107:156 */     productoRutaFabricacion.setEliminado(true);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<TareaProduccion> autocompletarTareaProduccion(String consulta)
/* 111:    */   {
/* 112:160 */     return this.servicioTareaProduccion.autocompletarTareaProduccion(consulta);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public List<Producto> autocompletarProducto(String consulta)
/* 116:    */   {
/* 117:165 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public List<OperacionProduccion> getListaOperacionProduccion()
/* 121:    */   {
/* 122:170 */     List<OperacionProduccion> lista = new ArrayList();
/* 123:171 */     for (OperacionProduccion operacionRutaFabricacion : this.rutaFabricacion.getListaOperacionProduccion()) {
/* 124:172 */       if (!operacionRutaFabricacion.isEliminado()) {
/* 125:173 */         lista.add(operacionRutaFabricacion);
/* 126:    */       }
/* 127:    */     }
/* 128:176 */     return lista;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<ProductoRutaFabricacion> getListaProductoRutaFabricacion()
/* 132:    */   {
/* 133:180 */     List<ProductoRutaFabricacion> lista = new ArrayList();
/* 134:181 */     for (ProductoRutaFabricacion productoRutaFabricacion : this.rutaFabricacion.getListaProductoRutaFabricacion()) {
/* 135:182 */       if (!productoRutaFabricacion.isEliminado()) {
/* 136:183 */         lista.add(productoRutaFabricacion);
/* 137:    */       }
/* 138:    */     }
/* 139:186 */     return lista;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void crearEntidad()
/* 143:    */   {
/* 144:193 */     this.rutaFabricacion = new RutaFabricacion();
/* 145:194 */     this.rutaFabricacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 146:195 */     this.rutaFabricacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String editar()
/* 150:    */   {
/* 151:204 */     if (getRutaFabricacion().getIdRutaFabricacion() != 0)
/* 152:    */     {
/* 153:205 */       this.rutaFabricacion = this.servicioRutaFabricacion.cargarDetalle(this.rutaFabricacion.getIdRutaFabricacion());
/* 154:206 */       setEditado(true);
/* 155:    */     }
/* 156:    */     else
/* 157:    */     {
/* 158:208 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 159:    */     }
/* 160:210 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String guardar()
/* 164:    */   {
/* 165:    */     try
/* 166:    */     {
/* 167:220 */       this.servicioRutaFabricacion.guardar(this.rutaFabricacion);
/* 168:221 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 169:222 */       limpiar();
/* 170:223 */       setEditado(false);
/* 171:    */     }
/* 172:    */     catch (Exception e)
/* 173:    */     {
/* 174:225 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 175:226 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 176:    */     }
/* 177:228 */     return "";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String eliminar()
/* 181:    */   {
/* 182:    */     try
/* 183:    */     {
/* 184:238 */       this.servicioRutaFabricacion.eliminar(this.rutaFabricacion);
/* 185:239 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 186:    */     }
/* 187:    */     catch (Exception e)
/* 188:    */     {
/* 189:241 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 190:242 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 191:    */     }
/* 192:244 */     return "";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String copiar()
/* 196:    */   {
/* 197:248 */     RutaFabricacion rutaFabricacionOrigen = this.servicioRutaFabricacion.cargarDetalle(this.rutaFabricacion.getId());
/* 198:249 */     this.rutaFabricacion = this.servicioRutaFabricacion.copiarRutaFabricacion(rutaFabricacionOrigen);
/* 199:250 */     setEditado(true);
/* 200:    */     
/* 201:252 */     return "";
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String cargarDatos()
/* 205:    */   {
/* 206:261 */     return "";
/* 207:    */   }
/* 208:    */   
/* 209:    */   public String limpiar()
/* 210:    */   {
/* 211:270 */     crearEntidad();
/* 212:271 */     return "";
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void cargarProducto(Producto producto)
/* 216:    */   {
/* 217:279 */     boolean agregarProducto = true;
/* 218:281 */     for (ProductoRutaFabricacion productoRuta : this.rutaFabricacion.getListaProductoRutaFabricacion()) {
/* 219:282 */       if (productoRuta.getProducto().equals(producto))
/* 220:    */       {
/* 221:283 */         productoRuta.setEliminado(false);
/* 222:284 */         agregarProducto = false;
/* 223:285 */         break;
/* 224:    */       }
/* 225:    */     }
/* 226:289 */     if (agregarProducto)
/* 227:    */     {
/* 228:290 */       ProductoRutaFabricacion productoRutaFabricacion = new ProductoRutaFabricacion();
/* 229:291 */       productoRutaFabricacion.setProducto(producto);
/* 230:292 */       productoRutaFabricacion.setRutaFabricacion(this.rutaFabricacion);
/* 231:    */       
/* 232:294 */       this.rutaFabricacion.getListaProductoRutaFabricacion().add(productoRutaFabricacion);
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void cargarTareaProduccion(SelectEvent event)
/* 237:    */   {
/* 238:299 */     this.operacionProduccion = ((OperacionProduccion)this.dtOperacionRutaFabricacion.getRowData());
/* 239:300 */     TareaProduccion tareaProduccion = (TareaProduccion)event.getObject();
/* 240:    */     
/* 241:302 */     this.operacionProduccion.setNumeroMaquinas(tareaProduccion.getNumeroMaquinas());
/* 242:303 */     this.operacionProduccion.setNumeroPersonas(tareaProduccion.getNumeroPersonas());
/* 243:304 */     this.operacionProduccion.setCentroTrabajo(tareaProduccion.getCentroTrabajo());
/* 244:305 */     this.operacionProduccion.setMaquina(tareaProduccion.getMaquina());
/* 245:306 */     this.operacionProduccion.setIndicadorFijo(tareaProduccion.isIndicadorFijo());
/* 246:307 */     this.operacionProduccion.setIndicadorAutomatico(tareaProduccion.isIndicadorAutomatico());
/* 247:    */   }
/* 248:    */   
/* 249:    */   public RutaFabricacion getRutaFabricacion()
/* 250:    */   {
/* 251:320 */     return this.rutaFabricacion;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/* 255:    */   {
/* 256:330 */     this.rutaFabricacion = rutaFabricacion;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public ServicioRutaFabricacion getServicioRutaFabricacion()
/* 260:    */   {
/* 261:339 */     return this.servicioRutaFabricacion;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setServicioRutaFabricacion(ServicioRutaFabricacion servicioRutaFabricacion)
/* 265:    */   {
/* 266:349 */     this.servicioRutaFabricacion = servicioRutaFabricacion;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public ServicioTareaProduccion getServicioTareaProduccion()
/* 270:    */   {
/* 271:358 */     return this.servicioTareaProduccion;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setServicioTareaProduccion(ServicioTareaProduccion servicioTareaProduccion)
/* 275:    */   {
/* 276:368 */     this.servicioTareaProduccion = servicioTareaProduccion;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public LazyDataModel<RutaFabricacion> getListaRutaFabricacion()
/* 280:    */   {
/* 281:377 */     return this.listaRutaFabricacion;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setListaRutaFabricacion(LazyDataModel<RutaFabricacion> listaRutaFabricacion)
/* 285:    */   {
/* 286:387 */     this.listaRutaFabricacion = listaRutaFabricacion;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public List<TareaProduccion> getListaTareaProduccion()
/* 290:    */   {
/* 291:396 */     if (this.listaTareaProduccion == null) {
/* 292:397 */       this.listaTareaProduccion = this.servicioTareaProduccion.obtenerListaCombo("nombre", true, null);
/* 293:    */     }
/* 294:399 */     return this.listaTareaProduccion;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setListaTareaProduccion(List<TareaProduccion> listaTareaProduccion)
/* 298:    */   {
/* 299:409 */     this.listaTareaProduccion = listaTareaProduccion;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public DataTable getDtRutaFabricacion()
/* 303:    */   {
/* 304:418 */     return this.dtRutaFabricacion;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setDtRutaFabricacion(DataTable dtRutaFabricacion)
/* 308:    */   {
/* 309:428 */     this.dtRutaFabricacion = dtRutaFabricacion;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public List<TarifaOperacion> getListaTarifaOperacion()
/* 313:    */   {
/* 314:437 */     if (this.listaTarifaOperacion == null) {
/* 315:438 */       this.listaTarifaOperacion = this.servicioTarifaOperacion.obtenerListaCombo("nombre", true, null);
/* 316:    */     }
/* 317:440 */     return this.listaTarifaOperacion;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public OperacionProduccion getOperacionProduccion()
/* 321:    */   {
/* 322:449 */     return this.operacionProduccion;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setOperacionProduccion(OperacionProduccion operacionProduccion)
/* 326:    */   {
/* 327:459 */     this.operacionProduccion = operacionProduccion;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public List<CentroTrabajo> getListaCentroTrabajo()
/* 331:    */   {
/* 332:468 */     if (this.listaCentroTrabajo == null) {
/* 333:469 */       this.listaCentroTrabajo = this.servicioCentroTrabajo.obtenerListaCombo("nombre", true, null);
/* 334:    */     }
/* 335:471 */     return this.listaCentroTrabajo;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public List<Maquina> getListaMaquina()
/* 339:    */   {
/* 340:480 */     if (this.listaMaquina == null) {
/* 341:481 */       this.listaMaquina = this.servicioMaquina.obtenerListaCombo("nombre", true, null);
/* 342:    */     }
/* 343:483 */     return this.listaMaquina;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public DataTable getDtOperacionRutaFabricacion()
/* 347:    */   {
/* 348:492 */     return this.dtOperacionRutaFabricacion;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setDtOperacionRutaFabricacion(DataTable dtOperacionRutaFabricacion)
/* 352:    */   {
/* 353:502 */     this.dtOperacionRutaFabricacion = dtOperacionRutaFabricacion;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public ListaProductoBean getListaProductoBean()
/* 357:    */   {
/* 358:506 */     return this.listaProductoBean;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 362:    */   {
/* 363:510 */     this.listaProductoBean = listaProductoBean;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public List<TipoCicloProduccionEnum> getListaTipoCicloEnum()
/* 367:    */   {
/* 368:514 */     if (this.listaTipoCicloEnum == null)
/* 369:    */     {
/* 370:515 */       this.listaTipoCicloEnum = new ArrayList();
/* 371:516 */       for (TipoCicloProduccionEnum tipoCiclo : TipoCicloProduccionEnum.values()) {
/* 372:517 */         this.listaTipoCicloEnum.add(tipoCiclo);
/* 373:    */       }
/* 374:    */     }
/* 375:520 */     return this.listaTipoCicloEnum;
/* 376:    */   }
/* 377:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.controller.RutaFabricacionBean
 * JD-Core Version:    0.7.0.1
 */