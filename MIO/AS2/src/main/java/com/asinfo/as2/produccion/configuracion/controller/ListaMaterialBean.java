/*   1:    */ package com.asinfo.as2.produccion.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.entities.ProductoMaterial;
/*   7:    */ import com.asinfo.as2.entities.SubProducto;
/*   8:    */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacion;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  12:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  13:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.JsfUtil;
/*  16:    */ import com.asinfo.as2.utils.NodoArbol;
/*  17:    */ import java.io.Serializable;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.Iterator;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.annotation.PostConstruct;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ManagedProperty;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.model.DefaultTreeNode;
/*  30:    */ import org.primefaces.model.LazyDataModel;
/*  31:    */ import org.primefaces.model.SortOrder;
/*  32:    */ import org.primefaces.model.TreeNode;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ListaMaterialBean
/*  37:    */   extends PageControllerAS2
/*  38:    */   implements Serializable
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 2670004787702683037L;
/*  41:    */   @EJB
/*  42:    */   private ServicioProducto servicioProducto;
/*  43:    */   @EJB
/*  44:    */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  45:    */   private Producto producto;
/*  46:    */   private LazyDataModel<Producto> listaProducto;
/*  47:    */   @ManagedProperty("#{listaProductoBean}")
/*  48:    */   private ListaProductoBean listaProductoBean;
/*  49:    */   private ProductoMaterial material;
/*  50:    */   
/*  51:    */   private static enum TipoSeleccionEnum
/*  52:    */   {
/*  53: 73 */     MATERIAL,  SUBPRODUCTO,  SUSTITUTO;
/*  54:    */     
/*  55:    */     private TipoSeleccionEnum() {}
/*  56:    */   }
/*  57:    */   
/*  58: 78 */   private TipoSeleccionEnum tipoSeleccion = TipoSeleccionEnum.MATERIAL;
/*  59: 79 */   private int activeIndex = 0;
/*  60:    */   private TreeNode root;
/*  61:    */   
/*  62:    */   @PostConstruct
/*  63:    */   public void init()
/*  64:    */   {
/*  65: 87 */     this.listaProducto = new LazyDataModel()
/*  66:    */     {
/*  67:    */       private static final long serialVersionUID = 1L;
/*  68:    */       
/*  69:    */       public List<Producto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  70:    */       {
/*  71: 98 */         List<Producto> lista = new ArrayList();
/*  72:    */         
/*  73:100 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  74:101 */         filters.put("indicadorProduccion", "true");
/*  75:    */         try
/*  76:    */         {
/*  77:104 */           lista = ListaMaterialBean.this.servicioProducto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  78:105 */           ListaMaterialBean.this.listaProducto.setRowCount(ListaMaterialBean.this.servicioProducto.contarPorCriterio(filters));
/*  79:    */         }
/*  80:    */         catch (ExcepcionAS2Inventario e)
/*  81:    */         {
/*  82:107 */           e.printStackTrace();
/*  83:    */         }
/*  84:109 */         return lista;
/*  85:    */       }
/*  86:    */     };
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String editar()
/*  90:    */   {
/*  91:121 */     if ((this.producto != null) && (this.producto.getIdProducto() != 0))
/*  92:    */     {
/*  93:122 */       this.producto = this.servicioProducto.cargarDetalleListaMaterial(this.producto);
/*  94:123 */       setEditado(true);
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  99:    */     }
/* 100:128 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String guardar()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:139 */       this.servicioProducto.guardarListaMaterial(this.producto);
/* 108:140 */       limpiar();
/* 109:141 */       setEditado(false);
/* 110:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 111:    */     }
/* 112:    */     catch (AS2Exception e)
/* 113:    */     {
/* 114:145 */       JsfUtil.addErrorMessage(e, "");
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:147 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 119:148 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 120:    */     }
/* 121:151 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String eliminar()
/* 125:    */   {
/* 126:161 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 127:162 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String limpiar()
/* 131:    */   {
/* 132:172 */     this.producto = null;
/* 133:173 */     this.material = null;
/* 134:174 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String crear()
/* 138:    */   {
/* 139:179 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 140:180 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String cargarDatos()
/* 144:    */   {
/* 145:191 */     return null;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void cargarProducto(Producto materialSubProducto)
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:201 */       this.servicioProducto.validarMaterialSubproducto(this.producto, materialSubProducto);
/* 153:    */       
/* 154:203 */       boolean agregarMaterial = true;
/* 155:    */       Object fecha;
/* 156:205 */       if (this.tipoSeleccion == TipoSeleccionEnum.MATERIAL)
/* 157:    */       {
/* 158:207 */         if (this.producto != null)
/* 159:    */         {
/* 160:209 */           for (ProductoMaterial materialProducto : this.producto.getListaProductoMaterial()) {
/* 161:210 */             if (materialProducto.getMaterial().equals(materialSubProducto))
/* 162:    */             {
/* 163:211 */               materialProducto.setEliminado(false);
/* 164:212 */               agregarMaterial = false;
/* 165:213 */               break;
/* 166:    */             }
/* 167:    */           }
/* 168:217 */           if (agregarMaterial)
/* 169:    */           {
/* 170:218 */             fecha = new Date();
/* 171:219 */             fecha = FuncionesUtiles.getFechaInicioMes((Date)fecha);
/* 172:220 */             ProductoMaterial productoMaterial = new ProductoMaterial();
/* 173:221 */             productoMaterial.setIdOrganizacion(this.producto.getIdOrganizacion());
/* 174:222 */             productoMaterial.setMaterial(materialSubProducto);
/* 175:223 */             productoMaterial.setFechaDesde((Date)fecha);
/* 176:224 */             productoMaterial.setProducto(this.producto);
/* 177:225 */             this.producto.getListaProductoMaterial().add(productoMaterial);
/* 178:    */           }
/* 179:    */         }
/* 180:    */       }
/* 181:229 */       else if (this.tipoSeleccion == TipoSeleccionEnum.SUBPRODUCTO)
/* 182:    */       {
/* 183:231 */         for (fecha = this.producto.getListaSubProducto().iterator(); ((Iterator)fecha).hasNext();)
/* 184:    */         {
/* 185:231 */           SubProducto subProducto = (SubProducto)((Iterator)fecha).next();
/* 186:232 */           if (subProducto.getProducto().equals(materialSubProducto))
/* 187:    */           {
/* 188:233 */             subProducto.setEliminado(false);
/* 189:234 */             agregarMaterial = false;
/* 190:235 */             break;
/* 191:    */           }
/* 192:    */         }
/* 193:239 */         if (agregarMaterial)
/* 194:    */         {
/* 195:240 */           SubProducto subProducto = new SubProducto();
/* 196:241 */           subProducto.setIdOrganizacion(this.producto.getIdOrganizacion());
/* 197:242 */           subProducto.setIdSucursal(this.producto.getIdSucursal());
/* 198:243 */           subProducto.setProductoPadre(this.producto);
/* 199:244 */           subProducto.setProducto(materialSubProducto);
/* 200:245 */           subProducto.setFechaDesde(new Date());
/* 201:246 */           this.producto.getListaSubProducto().add(subProducto);
/* 202:    */         }
/* 203:    */       }
/* 204:248 */       else if ((this.tipoSeleccion == TipoSeleccionEnum.SUSTITUTO) && (this.material != null))
/* 205:    */       {
/* 206:249 */         this.material.setSustituto(materialSubProducto);
/* 207:    */       }
/* 208:    */     }
/* 209:    */     catch (AS2Exception e)
/* 210:    */     {
/* 211:253 */       JsfUtil.addErrorMessage(e, "");
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void eliminarMaterial(ProductoMaterial materialProducto)
/* 216:    */   {
/* 217:262 */     materialProducto.setEliminado(true);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void eliminarSubProducto(SubProducto subProducto)
/* 221:    */   {
/* 222:266 */     subProducto.setEliminado(true);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void agregarRutaFabricacion()
/* 226:    */   {
/* 227:274 */     ProductoRutaFabricacion productoRutaFabricacion = new ProductoRutaFabricacion();
/* 228:275 */     productoRutaFabricacion.setProducto(this.producto);
/* 229:276 */     this.producto.getListaProductoRutaFabricacion().add(productoRutaFabricacion);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void eliminarRutaFabricacion(ProductoRutaFabricacion productoRutaFabricacion)
/* 233:    */   {
/* 234:283 */     productoRutaFabricacion.setEliminado(true);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void crearArbol(Producto productoSeleccionado)
/* 238:    */   {
/* 239:287 */     productoSeleccionado.setArbolComponentes(this.servicioProducto.obtenerArbolComponentes(productoSeleccionado, true));
/* 240:288 */     NodoArbol<Producto> arbol = productoSeleccionado.getArbolComponentes();
/* 241:289 */     this.root = new DefaultTreeNode(arbol.getValor(), null);
/* 242:290 */     this.root.setExpanded(true);
/* 243:291 */     for (NodoArbol<Producto> nodo : arbol.getHijos()) {
/* 244:292 */       crearArbolRecusivo(nodo, this.root);
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void crearArbolRecusivo(NodoArbol<Producto> nodo, TreeNode padre)
/* 249:    */   {
/* 250:297 */     TreeNode root1 = new DefaultTreeNode(nodo.getValor(), this.root);
/* 251:298 */     root1.setExpanded(true);
/* 252:299 */     padre.getChildren().add(root1);
/* 253:300 */     for (NodoArbol<Producto> nodo1 : nodo.getHijos()) {
/* 254:301 */       crearArbolRecusivo(nodo1, root1);
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void changeGeneraSubOrden(ProductoMaterial productoMaterial)
/* 259:    */   {
/* 260:306 */     productoMaterial.setIndicadorValidaStockSuborden(
/* 261:307 */       !productoMaterial.isIndicadorGeneraSuborden() ? false : productoMaterial.isIndicadorValidaStockSuborden());
/* 262:    */   }
/* 263:    */   
/* 264:    */   public Producto getProducto()
/* 265:    */   {
/* 266:311 */     return this.producto;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setProducto(Producto producto)
/* 270:    */   {
/* 271:315 */     this.producto = producto;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public LazyDataModel<Producto> getListaProducto()
/* 275:    */   {
/* 276:319 */     return this.listaProducto;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setListaProducto(LazyDataModel<Producto> productos)
/* 280:    */   {
/* 281:323 */     this.listaProducto = productos;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public List<ProductoMaterial> getListaProductoMaterial()
/* 285:    */   {
/* 286:332 */     List<ProductoMaterial> lista = new ArrayList();
/* 287:334 */     if (this.producto != null) {
/* 288:335 */       for (ProductoMaterial productoMaterial : this.producto.getListaProductoMaterial()) {
/* 289:336 */         if (!productoMaterial.isEliminado()) {
/* 290:337 */           lista.add(productoMaterial);
/* 291:    */         }
/* 292:    */       }
/* 293:    */     }
/* 294:342 */     return lista;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<ProductoRutaFabricacion> getListaProductoRutaFabricacion()
/* 298:    */   {
/* 299:346 */     List<ProductoRutaFabricacion> lista = new ArrayList();
/* 300:347 */     for (ProductoRutaFabricacion productoRutaFabricacion : this.producto.getListaProductoRutaFabricacion()) {
/* 301:348 */       if (!productoRutaFabricacion.isEliminado()) {
/* 302:349 */         lista.add(productoRutaFabricacion);
/* 303:    */       }
/* 304:    */     }
/* 305:352 */     return lista;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public List<SubProducto> getListaSubProducto()
/* 309:    */   {
/* 310:360 */     List<SubProducto> lista = new ArrayList();
/* 311:362 */     for (SubProducto subProducto : this.producto.getListaSubProducto()) {
/* 312:363 */       if (!subProducto.isEliminado()) {
/* 313:364 */         lista.add(subProducto);
/* 314:    */       }
/* 315:    */     }
/* 316:368 */     return lista;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public ListaProductoBean getListaProductoBean()
/* 320:    */   {
/* 321:372 */     return this.listaProductoBean;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 325:    */   {
/* 326:376 */     this.listaProductoBean = listaProductoBean;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public TipoSeleccionEnum getTipoSeleccion()
/* 330:    */   {
/* 331:380 */     return this.tipoSeleccion;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setTipoSeleccion(TipoSeleccionEnum tipoSeleccion)
/* 335:    */   {
/* 336:384 */     this.tipoSeleccion = tipoSeleccion;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public int getActiveIndex()
/* 340:    */   {
/* 341:388 */     return (this.tipoSeleccion == TipoSeleccionEnum.MATERIAL) || (this.tipoSeleccion == TipoSeleccionEnum.SUSTITUTO) ? 0 : 1;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setActiveIndex(int activeIndex)
/* 345:    */   {
/* 346:392 */     this.activeIndex = activeIndex;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public ProductoMaterial getMaterial()
/* 350:    */   {
/* 351:399 */     return this.material;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setMaterial(ProductoMaterial material)
/* 355:    */   {
/* 356:407 */     this.material = material;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public TreeNode getRoot()
/* 360:    */   {
/* 361:411 */     return this.root;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setRoot(TreeNode root)
/* 365:    */   {
/* 366:415 */     this.root = root;
/* 367:    */   }
/* 368:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.controller.ListaMaterialBean
 * JD-Core Version:    0.7.0.1
 */