/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.ProductoUltimaCompra;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.Iterator;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.primefaces.component.datatable.DataTable;
/*  29:    */ import org.primefaces.model.LazyDataModel;
/*  30:    */ import org.primefaces.model.SortOrder;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class AsignarProveedorBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -4585439120860530199L;
/*  38:    */   @EJB
/*  39:    */   private ServicioProducto servicioProducto;
/*  40:    */   @EJB
/*  41:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  42:    */   @EJB
/*  43:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  44:    */   @EJB
/*  45:    */   private ServicioEmpresa servicioEmpresa;
/*  46:    */   private ProductoUltimaCompra productoProveedor;
/*  47:    */   private Producto producto;
/*  48:    */   private CategoriaProducto categoriaProducto;
/*  49:    */   private SubcategoriaProducto subcategoriaProducto;
/*  50: 69 */   private BigDecimal precioPactado = BigDecimal.ZERO;
/*  51:    */   private LazyDataModel<Empresa> listaProveedor;
/*  52:    */   private List<ProductoUltimaCompra> listaProductoProveedor;
/*  53:    */   private Empresa proveedor;
/*  54:    */   private DataTable dtProducto;
/*  55:    */   private DataTable dtProveedor;
/*  56:    */   
/*  57:    */   @PostConstruct
/*  58:    */   public void init()
/*  59:    */   {
/*  60: 81 */     this.listaProveedor = new LazyDataModel()
/*  61:    */     {
/*  62:    */       private static final long serialVersionUID = 1L;
/*  63:    */       
/*  64:    */       public List<Empresa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  65:    */       {
/*  66: 92 */         List<Empresa> lista = new ArrayList();
/*  67:    */         
/*  68: 94 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  69: 95 */         filters = AsignarProveedorBean.this.agregarFiltroOrganizacion(filters);
/*  70: 96 */         filters.put("indicadorProveedor", "true");
/*  71: 97 */         lista = AsignarProveedorBean.this.servicioEmpresa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  72: 98 */         AsignarProveedorBean.this.listaProveedor.setRowCount(AsignarProveedorBean.this.servicioEmpresa.contarPorCriterio(filters));
/*  73: 99 */         return lista;
/*  74:    */       }
/*  75:    */     };
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String editar()
/*  79:    */   {
/*  80:111 */     if ((getProveedor() != null) && (getProveedor().getId() != 0))
/*  81:    */     {
/*  82:112 */       this.proveedor = this.servicioEmpresa.cargarDetalleProducto(getProveedor());
/*  83:113 */       this.listaProductoProveedor = this.proveedor.getListaProductoUltimaCompra();
/*  84:114 */       setEditado(true);
/*  85:    */     }
/*  86:    */     else
/*  87:    */     {
/*  88:116 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  89:    */     }
/*  90:118 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String guardar()
/*  94:    */   {
/*  95:128 */     this.servicioEmpresa.guardarProductoEmpresa(getProveedor());
/*  96:129 */     addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  97:130 */     setEditado(false);
/*  98:131 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String crear()
/* 102:    */   {
/* 103:136 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 104:137 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String eliminar()
/* 108:    */   {
/* 109:147 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 110:148 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String cargarDatos()
/* 114:    */   {
/* 115:161 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String limpiar()
/* 119:    */   {
/* 120:171 */     this.proveedor = new Empresa();
/* 121:172 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void asignar()
/* 125:    */   {
/* 126:176 */     if ((this.producto != null) || (this.categoriaProducto != null) || (this.subcategoriaProducto != null))
/* 127:    */     {
/* 128:    */       List<Producto> listProducto;
/* 129:179 */       if (this.producto != null)
/* 130:    */       {
/* 131:180 */         List<Producto> listProducto = new ArrayList();
/* 132:181 */         listProducto.add(this.producto);
/* 133:    */       }
/* 134:    */       else
/* 135:    */       {
/* 136:183 */         Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 137:184 */         if (this.categoriaProducto != null) {
/* 138:185 */           filters.put("subcategoriaProducto.categoriaProducto.idCategoriaProducto", String.valueOf(this.categoriaProducto.getId()));
/* 139:    */         }
/* 140:187 */         if (this.subcategoriaProducto != null) {
/* 141:188 */           filters.put("subcategoriaProducto.idSubcategoriaProducto", String.valueOf(this.subcategoriaProducto.getId()));
/* 142:    */         }
/* 143:190 */         listProducto = this.servicioProducto.obtenerListaCombo("nombre", true, filters);
/* 144:    */       }
/* 145:192 */       agregarProductos(listProducto);
/* 146:193 */       this.producto = null;
/* 147:194 */       this.categoriaProducto = null;
/* 148:195 */       this.subcategoriaProducto = null;
/* 149:196 */       this.precioPactado = BigDecimal.ZERO;
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void agregarProductos(List<Producto> listProducto)
/* 154:    */   {
/* 155:201 */     Map<Integer, ProductoUltimaCompra> mapProductoUltimaCompra = new HashMap();
/* 156:202 */     for (Iterator localIterator = getProveedor().getListaProductoUltimaCompra().iterator(); localIterator.hasNext();)
/* 157:    */     {
/* 158:202 */       puc = (ProductoUltimaCompra)localIterator.next();
/* 159:203 */       mapProductoUltimaCompra.put(Integer.valueOf(puc.getProducto().getId()), puc);
/* 160:    */     }
/* 161:    */     ProductoUltimaCompra puc;
/* 162:206 */     for (Producto p : listProducto)
/* 163:    */     {
/* 164:207 */       ProductoUltimaCompra productoUltimaCompra = (ProductoUltimaCompra)mapProductoUltimaCompra.get(Integer.valueOf(p.getId()));
/* 165:208 */       if (productoUltimaCompra == null)
/* 166:    */       {
/* 167:209 */         productoUltimaCompra = new ProductoUltimaCompra();
/* 168:210 */         productoUltimaCompra.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 169:211 */         productoUltimaCompra.setIdSucursal(AppUtil.getSucursal().getId());
/* 170:212 */         productoUltimaCompra.setProducto(p);
/* 171:213 */         productoUltimaCompra.setEmpresa(getProveedor());
/* 172:214 */         productoUltimaCompra.setPrecioPactado(getPrecioPactado());
/* 173:215 */         productoUltimaCompra.setFecha(new Date());
/* 174:216 */         getProveedor().getListaProductoUltimaCompra().add(productoUltimaCompra);
/* 175:    */       }
/* 176:    */       else
/* 177:    */       {
/* 178:218 */         productoUltimaCompra.setEliminado(false);
/* 179:219 */         productoUltimaCompra.setPrecioPactado(getPrecioPactado());
/* 180:    */       }
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 185:    */   {
/* 186:225 */     List<CategoriaProducto> lista = new ArrayList();
/* 187:    */     
/* 188:227 */     HashMap<String, String> filters = new HashMap();
/* 189:228 */     filters.put("nombre", "%" + consulta.trim());
/* 190:    */     
/* 191:230 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 192:    */     
/* 193:232 */     return lista;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 197:    */   {
/* 198:236 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 199:    */     
/* 200:238 */     HashMap<String, String> filters = new HashMap();
/* 201:239 */     filters.put("nombre", "%" + consulta.trim());
/* 202:240 */     if (this.categoriaProducto != null) {
/* 203:241 */       filters.put("categoriaProducto.idCategoriaProducto", String.valueOf(this.categoriaProducto.getId()));
/* 204:    */     }
/* 205:243 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 206:    */     
/* 207:245 */     return lista;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<Producto> autocompletarProducto(String consulta)
/* 211:    */   {
/* 212:249 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 213:250 */     if (this.categoriaProducto != null) {
/* 214:251 */       filtros.put("subcategoriaProducto.categoriaProducto.idCategoriaProducto", String.valueOf(this.categoriaProducto.getId()));
/* 215:    */     }
/* 216:253 */     if (this.subcategoriaProducto != null) {
/* 217:254 */       filtros.put("subcategoriaProducto.idSubcategoriaProducto", String.valueOf(this.subcategoriaProducto.getId()));
/* 218:    */     }
/* 219:256 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta, filtros);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 223:    */   {
/* 224:260 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public DataTable getDtProducto()
/* 228:    */   {
/* 229:264 */     return this.dtProducto;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setDtProducto(DataTable dtProducto)
/* 233:    */   {
/* 234:268 */     this.dtProducto = dtProducto;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Producto getProducto()
/* 238:    */   {
/* 239:272 */     return this.producto;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setProducto(Producto producto)
/* 243:    */   {
/* 244:276 */     this.producto = producto;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public CategoriaProducto getCategoriaProducto()
/* 248:    */   {
/* 249:280 */     return this.categoriaProducto;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 253:    */   {
/* 254:284 */     this.categoriaProducto = categoriaProducto;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 258:    */   {
/* 259:288 */     return this.subcategoriaProducto;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 263:    */   {
/* 264:292 */     this.subcategoriaProducto = subcategoriaProducto;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public List<ProductoUltimaCompra> getListaProductoProveedor()
/* 268:    */   {
/* 269:296 */     this.listaProductoProveedor = new ArrayList();
/* 270:297 */     for (ProductoUltimaCompra puc : getProveedor().getListaProductoUltimaCompra()) {
/* 271:298 */       if (!puc.isEliminado()) {
/* 272:299 */         this.listaProductoProveedor.add(puc);
/* 273:    */       }
/* 274:    */     }
/* 275:302 */     return this.listaProductoProveedor;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setListaProductoProveedor(List<ProductoUltimaCompra> listaProductoProveedor)
/* 279:    */   {
/* 280:306 */     this.listaProductoProveedor = listaProductoProveedor;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public BigDecimal getPrecioPactado()
/* 284:    */   {
/* 285:310 */     return this.precioPactado;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setPrecioPactado(BigDecimal precioPactado)
/* 289:    */   {
/* 290:314 */     this.precioPactado = precioPactado;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public LazyDataModel<Empresa> getListaProveedor()
/* 294:    */   {
/* 295:318 */     return this.listaProveedor;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setListaProveedor(LazyDataModel<Empresa> listaProveedor)
/* 299:    */   {
/* 300:322 */     this.listaProveedor = listaProveedor;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public DataTable getDtProveedor()
/* 304:    */   {
/* 305:326 */     return this.dtProveedor;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setDtProveedor(DataTable dtProveedor)
/* 309:    */   {
/* 310:330 */     this.dtProveedor = dtProveedor;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public Empresa getProveedor()
/* 314:    */   {
/* 315:334 */     return this.proveedor;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setProveedor(Empresa proveedor)
/* 319:    */   {
/* 320:338 */     this.proveedor = proveedor;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public ProductoUltimaCompra getProductoProveedor()
/* 324:    */   {
/* 325:342 */     return this.productoProveedor;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setProductoProveedor(ProductoUltimaCompra productoProveedor)
/* 329:    */   {
/* 330:346 */     this.productoProveedor = productoProveedor;
/* 331:    */   }
/* 332:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.AsignarProveedorBean
 * JD-Core Version:    0.7.0.1
 */