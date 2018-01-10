/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.Unidad;
/*  11:    */ import com.asinfo.as2.entities.UnidadConversion;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.faces.model.SelectItem;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ import org.primefaces.component.datatable.DataTable;
/*  29:    */ import org.primefaces.model.LazyDataModel;
/*  30:    */ import org.primefaces.model.SortOrder;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class SubcategoriaProductoBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -1627591955222444207L;
/*  38:    */   @EJB
/*  39:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  40:    */   @EJB
/*  41:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  42:    */   @EJB
/*  43:    */   private ServicioCuentaContable servicioCuentaContable;
/*  44:    */   @EJB
/*  45:    */   private ServicioUnidad servicioUnidad;
/*  46:    */   private SubcategoriaProducto subcategoriaProducto;
/*  47:    */   private LazyDataModel<SubcategoriaProducto> subcategoriasProducto;
/*  48:    */   private List<CategoriaProducto> listaCategoriaProductoCombo;
/*  49:    */   private CuentaContable cuentaContable;
/*  50:    */   private DataTable dtCuentaContable;
/*  51:    */   private DataTable dtUnidadConversion;
/*  52:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  53:    */   private DataTable dataTableSubcategoriaProducto;
/*  54:    */   private List<CuentaContable> listaCuentaContable;
/*  55:    */   private List<SelectItem> subcategoriaProductoItems;
/*  56:    */   
/*  57:    */   private static enum enumCuentaContableEditada
/*  58:    */   {
/*  59: 73 */     CUENTA_CONTABLE_AJUSTE_INGRESO,  CUENTA_CONTABLE_AJUSTE_EGRESO,  CUENTA_CONTABLE_INVENTARIO,  CUENTA_CONTABLE_COSTO,  CUENTA_CONTABLE_GASTO,  CUENTA_CONTABLE_INGRESO,  CUENTA_CONTABLE_MERCADERIA_POR_RECIBIR,  CUENTA_CONTABLE_MERCADERIA_POR_DESPACHAR,  CUENTA_CONTABLE_DESCUENTO_VENTA,  CUENTA_CONTABLE_DEVOLUCION_VENTA,  CUENTA_CONTABLE_DESCUENTO_COMPRA,  CUENTA_CONTABLE_DEVOLUCION_COMPRA;
/*  60:    */     
/*  61:    */     private enumCuentaContableEditada() {}
/*  62:    */   }
/*  63:    */   
/*  64:    */   @PostConstruct
/*  65:    */   public void init()
/*  66:    */   {
/*  67: 92 */     this.subcategoriasProducto = new LazyDataModel()
/*  68:    */     {
/*  69:    */       private static final long serialVersionUID = 1L;
/*  70:    */       
/*  71:    */       public List<SubcategoriaProducto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  72:    */       {
/*  73: 99 */         List<SubcategoriaProducto> lista = new ArrayList();
/*  74:100 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  75:    */         
/*  76:102 */         lista = SubcategoriaProductoBean.this.servicioSubcategoriaProducto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  77:103 */         SubcategoriaProductoBean.this.subcategoriasProducto.setRowCount(SubcategoriaProductoBean.this.servicioSubcategoriaProducto.contarPorCriterio(filters));
/*  78:    */         
/*  79:105 */         return lista;
/*  80:    */       }
/*  81:    */     };
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void crearSubcategoriaProducto()
/*  85:    */   {
/*  86:112 */     this.subcategoriaProducto = new SubcategoriaProducto();
/*  87:113 */     this.subcategoriaProducto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  88:114 */     this.subcategoriaProducto.setIdSucursal(AppUtil.getSucursal().getId());
/*  89:115 */     this.subcategoriaProducto.setListaUnidadConversion(new ArrayList());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String editar()
/*  93:    */   {
/*  94:121 */     if (this.subcategoriaProducto.getIdSubcategoriaProducto() != 0)
/*  95:    */     {
/*  96:122 */       this.subcategoriaProducto = this.servicioSubcategoriaProducto.cargarDetalle(this.subcategoriaProducto.getId());
/*  97:123 */       setEditado(true);
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 102:    */     }
/* 103:127 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String guardar()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:136 */       this.servicioSubcategoriaProducto.guardar(this.subcategoriaProducto);
/* 111:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 112:138 */       setEditado(false);
/* 113:139 */       limpiar();
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 118:142 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 119:    */     }
/* 120:144 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String eliminar()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:153 */       this.servicioSubcategoriaProducto.eliminar(this.subcategoriaProducto);
/* 128:154 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 129:155 */       limpiar();
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 134:158 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 135:    */     }
/* 136:161 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String cargarDatos()
/* 140:    */   {
/* 141:169 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String cargarItems()
/* 145:    */   {
/* 146:176 */     if (this.subcategoriaProductoItems == null)
/* 147:    */     {
/* 148:177 */       this.subcategoriaProductoItems = new ArrayList();
/* 149:178 */       for (SubcategoriaProducto subcategoriaProducto : this.subcategoriasProducto)
/* 150:    */       {
/* 151:179 */         SelectItem opcion = new SelectItem(Integer.valueOf(subcategoriaProducto.getIdSubcategoriaProducto()), subcategoriaProducto.getNombre());
/* 152:180 */         this.subcategoriaProductoItems.add(opcion);
/* 153:    */       }
/* 154:    */     }
/* 155:184 */     return "";
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String limpiar()
/* 159:    */   {
/* 160:192 */     crearSubcategoriaProducto();
/* 161:193 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void cargarCuentaContable()
/* 165:    */   {
/* 166:197 */     this.cuentaContable = ((CuentaContable)this.dtCuentaContable.getRowData());
/* 167:199 */     switch (2.$SwitchMap$com$asinfo$as2$inventario$configuracion$controller$SubcategoriaProductoBean$enumCuentaContableEditada[this.cuentaContableEditada.ordinal()])
/* 168:    */     {
/* 169:    */     case 1: 
/* 170:202 */       this.subcategoriaProducto.setCuentaContableInventario(this.cuentaContable);
/* 171:203 */       break;
/* 172:    */     case 2: 
/* 173:206 */       this.subcategoriaProducto.setCuentaContableCosto(this.cuentaContable);
/* 174:207 */       break;
/* 175:    */     case 3: 
/* 176:210 */       this.subcategoriaProducto.setCuentaContableGasto(this.cuentaContable);
/* 177:211 */       break;
/* 178:    */     case 4: 
/* 179:214 */       this.subcategoriaProducto.setCuentaContableIngreso(this.cuentaContable);
/* 180:215 */       break;
/* 181:    */     case 5: 
/* 182:218 */       this.subcategoriaProducto.setCuentaContableMercaderiaPorRecibir(this.cuentaContable);
/* 183:219 */       break;
/* 184:    */     case 6: 
/* 185:222 */       this.subcategoriaProducto.setCuentaContableMercaderiaPorDespachar(this.cuentaContable);
/* 186:223 */       break;
/* 187:    */     case 7: 
/* 188:226 */       this.subcategoriaProducto.setCuentaContableDescuentoVenta(this.cuentaContable);
/* 189:227 */       break;
/* 190:    */     case 8: 
/* 191:230 */       this.subcategoriaProducto.setCuentaContableDevolucionVenta(this.cuentaContable);
/* 192:231 */       break;
/* 193:    */     case 9: 
/* 194:234 */       this.subcategoriaProducto.setCuentaContableDescuentoCompra(this.cuentaContable);
/* 195:235 */       break;
/* 196:    */     case 10: 
/* 197:238 */       this.subcategoriaProducto.setCuentaContableDevolucionCompra(this.cuentaContable);
/* 198:239 */       break;
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<Unidad> autocompletarUnidades(String consulta)
/* 203:    */   {
/* 204:254 */     List<Unidad> lista = new ArrayList();
/* 205:255 */     HashMap<String, String> filters = new HashMap();
/* 206:256 */     filters.put("nombre", consulta.trim());
/* 207:257 */     lista = this.servicioUnidad.obtenerListaCombo("nombre", true, filters);
/* 208:    */     
/* 209:259 */     return lista;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String agregarDetalleConversion()
/* 213:    */   {
/* 214:268 */     UnidadConversion unidadConversion = new UnidadConversion();
/* 215:269 */     unidadConversion.setSubcategoriaProducto(getSubcategoriaProducto());
/* 216:270 */     getSubcategoriaProducto().getListaUnidadConversion().add(unidadConversion);
/* 217:    */     
/* 218:272 */     return "";
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String eliminarDetalleConversion()
/* 222:    */   {
/* 223:276 */     UnidadConversion i = (UnidadConversion)this.dtUnidadConversion.getRowData();
/* 224:277 */     i.setEliminado(true);
/* 225:278 */     return "";
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void actualizarCuentaContableInventario()
/* 229:    */   {
/* 230:282 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_INVENTARIO;
/* 231:283 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableInventario();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void actualizarCuentaContableCosto()
/* 235:    */   {
/* 236:287 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_COSTO;
/* 237:288 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableCosto();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void actualizarCuentaContableGasto()
/* 241:    */   {
/* 242:292 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_GASTO;
/* 243:293 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableGasto();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void actualizarCuentaContableIngreso()
/* 247:    */   {
/* 248:297 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_INGRESO;
/* 249:298 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableIngreso();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void actualizarCuentaContableMercaderiaPorRecibir()
/* 253:    */   {
/* 254:302 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_MERCADERIA_POR_RECIBIR;
/* 255:303 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableMercaderiaPorRecibir();
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void actualizarCuentaContableMercaderiaPorDespachar()
/* 259:    */   {
/* 260:307 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_MERCADERIA_POR_DESPACHAR;
/* 261:308 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableMercaderiaPorDespachar();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void actualizarCuentaContableDescuentoVenta()
/* 265:    */   {
/* 266:312 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DESCUENTO_VENTA;
/* 267:313 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableDescuentoVenta();
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void actualizarContableDevolucionVenta()
/* 271:    */   {
/* 272:317 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DEVOLUCION_VENTA;
/* 273:318 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableDevolucionVenta();
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void actualizarCuentaContableDescuentoCompra()
/* 277:    */   {
/* 278:322 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DESCUENTO_COMPRA;
/* 279:323 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableDescuentoCompra();
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void actualizarContableDevolucionCompra()
/* 283:    */   {
/* 284:327 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DEVOLUCION_COMPRA;
/* 285:328 */     this.cuentaContable = this.subcategoriaProducto.getCuentaContableDevolucionCompra();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public ServicioSubcategoriaProducto getServicioSubcategoriaProductoBean()
/* 289:    */   {
/* 290:332 */     return this.servicioSubcategoriaProducto;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setServicioSubcategoriaProductoBean(ServicioSubcategoriaProducto servicioSubcategoriaProducto)
/* 294:    */   {
/* 295:336 */     this.servicioSubcategoriaProducto = servicioSubcategoriaProducto;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 299:    */   {
/* 300:340 */     if (this.subcategoriaProducto == null) {
/* 301:341 */       crearSubcategoriaProducto();
/* 302:    */     }
/* 303:344 */     return this.subcategoriaProducto;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 307:    */   {
/* 308:348 */     this.subcategoriaProducto = subcategoriaProducto;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public List<SelectItem> getSubcategoriaProductoItems()
/* 312:    */   {
/* 313:352 */     cargarItems();
/* 314:    */     
/* 315:354 */     return this.subcategoriaProductoItems;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setSubcategoriaProductoItems(List<SelectItem> subcategoriaProductoItems)
/* 319:    */   {
/* 320:358 */     this.subcategoriaProductoItems = subcategoriaProductoItems;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<CuentaContable> getListaCuentaContable()
/* 324:    */   {
/* 325:362 */     if (this.listaCuentaContable == null) {
/* 326:363 */       this.listaCuentaContable = this.servicioCuentaContable.buscarPorTipo(TipoCuentaContable.OTROS, AppUtil.getOrganizacion().getIdOrganizacion());
/* 327:    */     }
/* 328:365 */     return this.listaCuentaContable;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setListaCuentaContable(List<CuentaContable> listaCuentaContable)
/* 332:    */   {
/* 333:369 */     this.listaCuentaContable = listaCuentaContable;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public CuentaContable getCuentaContable()
/* 337:    */   {
/* 338:373 */     return this.cuentaContable;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 342:    */   {
/* 343:377 */     this.cuentaContable = cuentaContable;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public List<CategoriaProducto> getListaCategoriaProductoCombo()
/* 347:    */   {
/* 348:386 */     if (this.listaCategoriaProductoCombo == null) {
/* 349:387 */       this.listaCategoriaProductoCombo = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 350:    */     }
/* 351:389 */     return this.listaCategoriaProductoCombo;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public LazyDataModel<SubcategoriaProducto> getSubcategoriasProducto()
/* 355:    */   {
/* 356:398 */     return this.subcategoriasProducto;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setSubcategoriasProducto(LazyDataModel<SubcategoriaProducto> subcategoriasProducto)
/* 360:    */   {
/* 361:408 */     this.subcategoriasProducto = subcategoriasProducto;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public DataTable getDtCuentaContable()
/* 365:    */   {
/* 366:412 */     return this.dtCuentaContable;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 370:    */   {
/* 371:416 */     this.dtCuentaContable = dtCuentaContable;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public DataTable getDataTableSubcategoriaProducto()
/* 375:    */   {
/* 376:420 */     return this.dataTableSubcategoriaProducto;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setDataTableSubcategoriaProducto(DataTable dataTableSubcategoriaProducto)
/* 380:    */   {
/* 381:424 */     this.dataTableSubcategoriaProducto = dataTableSubcategoriaProducto;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public List<UnidadConversion> getListaUnidadConversion()
/* 385:    */   {
/* 386:428 */     List<UnidadConversion> lista = new ArrayList();
/* 387:429 */     for (UnidadConversion unidadConversion : getSubcategoriaProducto().getListaUnidadConversion()) {
/* 388:430 */       if (!unidadConversion.isEliminado()) {
/* 389:431 */         lista.add(unidadConversion);
/* 390:    */       }
/* 391:    */     }
/* 392:434 */     return lista;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public DataTable getDtUnidadConversion()
/* 396:    */   {
/* 397:443 */     return this.dtUnidadConversion;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setDtUnidadConversion(DataTable dtUnidadConversion)
/* 401:    */   {
/* 402:453 */     this.dtUnidadConversion = dtUnidadConversion;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public boolean getIndicadorSeleccionarTodo()
/* 406:    */   {
/* 407:457 */     return this.cuentaContableEditada == enumCuentaContableEditada.CUENTA_CONTABLE_GASTO;
/* 408:    */   }
/* 409:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.SubcategoriaProductoBean
 * JD-Core Version:    0.7.0.1
 */