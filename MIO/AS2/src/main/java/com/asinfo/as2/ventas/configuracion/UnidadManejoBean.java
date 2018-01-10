/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaUnidadManejo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.Unidad;
/*  11:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  12:    */ import com.asinfo.as2.entities.UnidadManejoProducto;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  18:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.JsfUtil;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ManagedProperty;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.component.html.HtmlInputText;
/*  32:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.component.datatable.DataTable;
/*  35:    */ import org.primefaces.model.LazyDataModel;
/*  36:    */ import org.primefaces.model.SortOrder;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class UnidadManejoBean
/*  41:    */   extends PageControllerAS2
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = 1L;
/*  44:    */   @EJB
/*  45:    */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*  46:    */   @EJB
/*  47:    */   private ServicioGenerico<CategoriaUnidadManejo> servicioCategoriaUnidadManejo;
/*  48:    */   @EJB
/*  49:    */   private ServicioProducto servicioProducto;
/*  50:    */   @EJB
/*  51:    */   private ServicioUnidad servicioUnidad;
/*  52:    */   @ManagedProperty("#{listaProductoBean}")
/*  53:    */   private ListaProductoBean listaProductoBean;
/*  54:    */   private UnidadManejo unidadManejo;
/*  55:    */   private LazyDataModel<UnidadManejo> listaUnidadManejo;
/*  56:    */   private DataTable dataTableUnidadManejo;
/*  57:    */   private DataTable dataTableUnidadManejoProducto;
/*  58:    */   private List<CategoriaUnidadManejo> listaCategoriaUnidadManejo;
/*  59:    */   private List<Unidad> listaUnidad;
/*  60:    */   protected boolean indicadorPallet;
/*  61: 84 */   private String order = null;
/*  62:    */   
/*  63:    */   @PostConstruct
/*  64:    */   public void init()
/*  65:    */   {
/*  66: 97 */     getListaProductoBean().setActivo(true);
/*  67: 98 */     getListaProductoBean().setIndicadorPesoBalanza(true);
/*  68: 99 */     this.listaUnidadManejo = new LazyDataModel()
/*  69:    */     {
/*  70:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  71:    */       
/*  72:    */       public List<UnidadManejo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  73:    */       {
/*  74:105 */         List<UnidadManejo> lista = new ArrayList();
/*  75:106 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  76:107 */         List<String> listaCampos = new ArrayList();
/*  77:108 */         listaCampos.add("categoriaUnidadManejo");
/*  78:109 */         listaCampos.add("unidad");
/*  79:110 */         if (UnidadManejoBean.this.indicadorPallet) {
/*  80:111 */           filters.put("categoriaUnidadManejo.indicadorPallet", "false");
/*  81:    */         }
/*  82:    */         try
/*  83:    */         {
/*  84:114 */           lista = UnidadManejoBean.this.servicioUnidadManejo.obtenerListaPorPagina(UnidadManejo.class, startIndex, pageSize, UnidadManejoBean.this.order == null ? sortField : UnidadManejoBean.this.order, ordenar, filters, listaCampos);
/*  85:    */         }
/*  86:    */         catch (Exception e)
/*  87:    */         {
/*  88:117 */           e.printStackTrace();
/*  89:    */         }
/*  90:119 */         UnidadManejoBean.this.listaUnidadManejo.setRowCount(UnidadManejoBean.this.servicioUnidadManejo.contarPorCriterio(UnidadManejo.class, filters));
/*  91:120 */         return lista;
/*  92:    */       }
/*  93:    */     };
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getOrder()
/*  97:    */   {
/*  98:126 */     return this.order;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setOrder(String order)
/* 102:    */   {
/* 103:130 */     this.order = order;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String editar()
/* 107:    */   {
/* 108:141 */     if (getUnidadManejo().getIdUnidadManejo() > 0)
/* 109:    */     {
/* 110:142 */       List<String> listaCampos = new ArrayList();
/* 111:143 */       listaCampos.add("categoriaUnidadManejo");
/* 112:144 */       listaCampos.add("unidad");
/* 113:145 */       listaCampos.add("listaUnidadManejoProducto.producto");
/* 114:146 */       this.unidadManejo = ((UnidadManejo)this.servicioUnidadManejo.cargarDetalle(this.unidadManejo.getClass(), this.unidadManejo.getId(), listaCampos));
/* 115:147 */       setEditado(true);
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:149 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 120:    */     }
/* 121:152 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String guardar()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:163 */       if (validar())
/* 129:    */       {
/* 130:164 */         this.servicioUnidadManejo.guardarValidar(this.unidadManejo, this.unidadManejo.getListaUnidadManejoProducto());
/* 131:165 */         cargarDatos();
/* 132:166 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 133:    */       }
/* 134:    */     }
/* 135:    */     catch (AS2Exception e)
/* 136:    */     {
/* 137:169 */       JsfUtil.addErrorMessage(e, "");
/* 138:    */     }
/* 139:    */     catch (Exception e)
/* 140:    */     {
/* 141:171 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 142:172 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 143:    */     }
/* 144:174 */     return "";
/* 145:    */   }
/* 146:    */   
/* 147:    */   private boolean validar()
/* 148:    */   {
/* 149:178 */     boolean correcto = true;
/* 150:179 */     for (UnidadManejoProducto detalle : this.unidadManejo.getListaUnidadManejoProducto()) {
/* 151:180 */       if (!detalle.isEliminado()) {
/* 152:181 */         if ((this.unidadManejo.getCategoriaUnidadManejo() != null) && (this.unidadManejo.getCategoriaUnidadManejo().isIndicadorPallet()))
/* 153:    */         {
/* 154:182 */           detalle.setEliminado(true);
/* 155:    */         }
/* 156:    */         else
/* 157:    */         {
/* 158:184 */           if (detalle.getPesoMinimo().compareTo(detalle.getUnidadManejo().getPeso()) < 0)
/* 159:    */           {
/* 160:185 */             correcto = false;
/* 161:186 */             addErrorMessage(getLanguageController().getMensaje("msg_error_peso_minimo_menor_unidad_manejo") + ". " + detalle
/* 162:187 */               .getProducto().getNombre());
/* 163:188 */             break;
/* 164:    */           }
/* 165:190 */           if ((detalle.getPesoMinimo().compareTo(detalle.getPesoPromedio()) > 0) || 
/* 166:191 */             (detalle.getPesoPromedio().compareTo(detalle.getPesoMaximo()) > 0))
/* 167:    */           {
/* 168:192 */             correcto = false;
/* 169:193 */             addErrorMessage(getLanguageController().getMensaje("msg_error_pesos_minimo_maximo_promedio") + ". " + detalle
/* 170:194 */               .getProducto().getNombre());
/* 171:195 */             break;
/* 172:    */           }
/* 173:    */         }
/* 174:    */       }
/* 175:    */     }
/* 176:201 */     return correcto;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String eliminar()
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:212 */       List<String> listaCampos = new ArrayList();
/* 184:213 */       listaCampos.add("categoriaUnidadManejo");
/* 185:214 */       listaCampos.add("listaUnidadManejoProducto.producto");
/* 186:215 */       this.unidadManejo = ((UnidadManejo)this.servicioUnidadManejo.cargarDetalle(this.unidadManejo.getClass(), this.unidadManejo.getId(), listaCampos));
/* 187:216 */       this.servicioUnidadManejo.eliminar(this.unidadManejo, this.unidadManejo.getListaUnidadManejoProducto());
/* 188:217 */       cargarDatos();
/* 189:    */       
/* 190:219 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 191:    */     }
/* 192:    */     catch (Exception e)
/* 193:    */     {
/* 194:221 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 195:222 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 196:    */     }
/* 197:224 */     return "";
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String cargarDatos()
/* 201:    */   {
/* 202:235 */     setEditado(false);
/* 203:    */     try
/* 204:    */     {
/* 205:238 */       limpiar();
/* 206:    */     }
/* 207:    */     catch (Exception e)
/* 208:    */     {
/* 209:241 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 210:242 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 211:    */     }
/* 212:244 */     return "";
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String limpiar()
/* 216:    */   {
/* 217:254 */     this.unidadManejo = new UnidadManejo();
/* 218:255 */     this.unidadManejo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 219:256 */     this.unidadManejo.setIdSucursal(AppUtil.getSucursal().getId());
/* 220:257 */     return "";
/* 221:    */   }
/* 222:    */   
/* 223:    */   public UnidadManejo getUnidadManejo()
/* 224:    */   {
/* 225:266 */     return this.unidadManejo;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setUnidadManejo(UnidadManejo unidadManejo)
/* 229:    */   {
/* 230:276 */     this.unidadManejo = unidadManejo;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public DataTable getDataTableUnidadManejo()
/* 234:    */   {
/* 235:285 */     return this.dataTableUnidadManejo;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setDataTableUnidadManejo(DataTable dataTableUnidadManejo)
/* 239:    */   {
/* 240:289 */     this.dataTableUnidadManejo = dataTableUnidadManejo;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public LazyDataModel<UnidadManejo> getListaUnidadManejo()
/* 244:    */   {
/* 245:293 */     if (this.listaUnidadManejo == null) {
/* 246:294 */       cargarDatos();
/* 247:    */     }
/* 248:296 */     return this.listaUnidadManejo;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaUnidadManejo(LazyDataModel<UnidadManejo> listaUnidadManejo)
/* 252:    */   {
/* 253:300 */     this.listaUnidadManejo = listaUnidadManejo;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public List<CategoriaUnidadManejo> getListaCategoriaUnidadManejo()
/* 257:    */   {
/* 258:304 */     if (this.listaCategoriaUnidadManejo == null)
/* 259:    */     {
/* 260:305 */       Map<String, String> filtros = new HashMap();
/* 261:306 */       filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 262:307 */       filtros.put("activo", "true");
/* 263:308 */       this.listaCategoriaUnidadManejo = this.servicioCategoriaUnidadManejo.obtenerListaCombo(CategoriaUnidadManejo.class, "nombre", true, filtros);
/* 264:    */     }
/* 265:310 */     return this.listaCategoriaUnidadManejo;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setListaCategoriaUnidadManejo(List<CategoriaUnidadManejo> listaCategoriaUnidadManejo)
/* 269:    */   {
/* 270:314 */     this.listaCategoriaUnidadManejo = listaCategoriaUnidadManejo;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public ListaProductoBean getListaProductoBean()
/* 274:    */   {
/* 275:318 */     return this.listaProductoBean;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 279:    */   {
/* 280:322 */     this.listaProductoBean = listaProductoBean;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public DataTable getDataTableUnidadManejoProducto()
/* 284:    */   {
/* 285:326 */     return this.dataTableUnidadManejoProducto;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setDataTableUnidadManejoProducto(DataTable dataTableUnidadManejoProducto)
/* 289:    */   {
/* 290:330 */     this.dataTableUnidadManejoProducto = dataTableUnidadManejoProducto;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public List<UnidadManejoProducto> getListaUnidadManejoProducto()
/* 294:    */   {
/* 295:334 */     List<UnidadManejoProducto> lista = new ArrayList();
/* 296:335 */     for (UnidadManejoProducto detalle : this.unidadManejo.getListaUnidadManejoProducto()) {
/* 297:336 */       if (!detalle.isEliminado()) {
/* 298:337 */         lista.add(detalle);
/* 299:    */       }
/* 300:    */     }
/* 301:340 */     return lista;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void eliminarUnidadManejoProducto(UnidadManejoProducto detalle)
/* 305:    */   {
/* 306:344 */     detalle.setEliminado(true);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 310:    */   {
/* 311:354 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 312:355 */     UnidadManejoProducto detalle = (UnidadManejoProducto)this.dataTableUnidadManejoProducto.getRowData();
/* 313:    */     try
/* 314:    */     {
/* 315:358 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 316:359 */       detalle.setProducto(producto);
/* 317:    */     }
/* 318:    */     catch (ExcepcionAS2 e)
/* 319:    */     {
/* 320:361 */       e.printStackTrace();
/* 321:362 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/* 326:    */   {
/* 327:367 */     if (saldoLote.getProducto().equals(getListaProductoBean().getProducto()))
/* 328:    */     {
/* 329:368 */       getListaProductoBean().setSaldoProductoLote(saldoLote);
/* 330:369 */       cargarProducto();
/* 331:    */     }
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void cargarProducto(Producto producto)
/* 335:    */   {
/* 336:374 */     getListaProductoBean().setProducto(producto);
/* 337:375 */     getListaProductoBean().setSaldoProductoLote(null);
/* 338:376 */     cargarProducto();
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void cargarProducto()
/* 342:    */   {
/* 343:383 */     Producto producto = getListaProductoBean().getProducto();
/* 344:384 */     if (producto != null)
/* 345:    */     {
/* 346:385 */       UnidadManejoProducto detalle = new UnidadManejoProducto();
/* 347:386 */       detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 348:387 */       detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 349:388 */       detalle.setProducto(producto);
/* 350:389 */       detalle.setUnidadManejo(this.unidadManejo);
/* 351:390 */       this.unidadManejo.getListaUnidadManejoProducto().add(detalle);
/* 352:    */     }
/* 353:392 */     getListaProductoBean().setProducto(null);
/* 354:    */   }
/* 355:    */   
/* 356:    */   public String agregarDetalle()
/* 357:    */   {
/* 358:396 */     UnidadManejoProducto detalle = new UnidadManejoProducto();
/* 359:397 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 360:398 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 361:399 */     detalle.setProducto(new Producto());
/* 362:400 */     detalle.setUnidadManejo(this.unidadManejo);
/* 363:401 */     this.unidadManejo.getListaUnidadManejoProducto().add(detalle);
/* 364:402 */     return "";
/* 365:    */   }
/* 366:    */   
/* 367:    */   public List<Unidad> getListaUnidad()
/* 368:    */   {
/* 369:407 */     if (this.listaUnidad == null) {
/* 370:408 */       this.listaUnidad = this.servicioUnidad.obtenerListaCombo("codigo", true, null);
/* 371:    */     }
/* 372:411 */     return this.listaUnidad;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public boolean isIndicadorPallet()
/* 376:    */   {
/* 377:418 */     return this.indicadorPallet;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setIndicadorPallet(boolean indicadorPallet)
/* 381:    */   {
/* 382:426 */     this.indicadorPallet = indicadorPallet;
/* 383:    */   }
/* 384:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.UnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */