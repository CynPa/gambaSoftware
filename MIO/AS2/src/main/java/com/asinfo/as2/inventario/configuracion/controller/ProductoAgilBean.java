/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioConjuntoAtributo;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  18:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  19:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import java.io.Serializable;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.primefaces.component.datatable.DataTable;
/*  33:    */ import org.primefaces.model.LazyDataModel;
/*  34:    */ import org.primefaces.model.SortOrder;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class ProductoAgilBean
/*  39:    */   extends PageControllerAS2
/*  40:    */   implements Serializable
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = -4585439120860530199L;
/*  43:    */   @EJB
/*  44:    */   private ServicioProducto servicioProducto;
/*  45:    */   @EJB
/*  46:    */   private ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  47:    */   @EJB
/*  48:    */   private ServicioConjuntoAtributo servicioConjuntoAtributo;
/*  49:    */   @EJB
/*  50:    */   private ServicioUnidad servicioUnidad;
/*  51:    */   @EJB
/*  52:    */   private ServicioBodega servicioBodega;
/*  53:    */   @EJB
/*  54:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  55:    */   @EJB
/*  56:    */   private ServicioEmpresa servicioEmpresa;
/*  57:    */   @EJB
/*  58:    */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  59:    */   private Producto producto;
/*  60:    */   private LazyDataModel<Producto> listaProducto;
/*  61:    */   private List<SelectItem> listaTipoProducto;
/*  62:    */   private List<CategoriaImpuesto> listaCategoriaImpuesto;
/*  63:    */   private DataTable dtProducto;
/*  64:    */   private List<SelectItem> productoItems;
/*  65:    */   private SelectItem[] listaTipoProductoItem;
/*  66:    */   
/*  67:    */   @PostConstruct
/*  68:    */   public void init()
/*  69:    */   {
/*  70: 93 */     this.listaProducto = new LazyDataModel()
/*  71:    */     {
/*  72:    */       private static final long serialVersionUID = 1L;
/*  73:    */       
/*  74:    */       public List<Producto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  75:    */       {
/*  76:104 */         List<Producto> lista = new ArrayList();
/*  77:    */         
/*  78:106 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  79:    */         try
/*  80:    */         {
/*  81:109 */           lista = ProductoAgilBean.this.servicioProducto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  82:110 */           ProductoAgilBean.this.listaProducto.setRowCount(ProductoAgilBean.this.servicioProducto.contarPorCriterio(filters));
/*  83:    */         }
/*  84:    */         catch (ExcepcionAS2Inventario e)
/*  85:    */         {
/*  86:112 */           ProductoAgilBean.LOG.error("ERROR AL CARGAR LOS DATOS PRODUCTO", e);
/*  87:    */         }
/*  88:114 */         return lista;
/*  89:    */       }
/*  90:    */     };
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void crearProducto()
/*  94:    */   {
/*  95:125 */     this.producto = new Producto();
/*  96:126 */     this.producto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  97:127 */     this.producto.setIdSucursal(AppUtil.getSucursal().getId());
/*  98:128 */     this.producto.setListaUnidadConversion(new ArrayList());
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String editar()
/* 102:    */   {
/* 103:138 */     if ((this.producto != null) && (this.producto.getIdProducto() != 0))
/* 104:    */     {
/* 105:139 */       this.producto = this.servicioProducto.cargaDetalle(this.producto.getId());
/* 106:140 */       setEditado(true);
/* 107:    */     }
/* 108:    */     else
/* 109:    */     {
/* 110:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 111:    */     }
/* 112:145 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String guardar()
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:156 */       this.servicioProducto.guardarProductoAgil(this.producto);
/* 120:157 */       limpiar();
/* 121:158 */       setEditado(false);
/* 122:159 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 123:    */     }
/* 124:    */     catch (ExcepcionAS2Inventario e)
/* 125:    */     {
/* 126:161 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 127:    */     }
/* 128:    */     catch (Exception e)
/* 129:    */     {
/* 130:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 131:164 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 132:    */     }
/* 133:167 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String eliminar()
/* 137:    */   {
/* 138:    */     try
/* 139:    */     {
/* 140:178 */       this.servicioProducto.eliminar(this.producto);
/* 141:179 */       limpiar();
/* 142:180 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 143:    */     }
/* 144:    */     catch (ExcepcionAS2Inventario e)
/* 145:    */     {
/* 146:182 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 147:183 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 148:    */     }
/* 149:    */     catch (ExcepcionAS2 e)
/* 150:    */     {
/* 151:185 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 152:186 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 153:    */     }
/* 154:    */     catch (Exception e)
/* 155:    */     {
/* 156:188 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 157:189 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 158:    */     }
/* 159:192 */     return "";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String cargarDatos()
/* 163:    */   {
/* 164:205 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Producto> autocompletarProductos(String consulta)
/* 168:    */   {
/* 169:210 */     List<Producto> lista = new ArrayList();
/* 170:211 */     String sortField = "nombreComercial";
/* 171:212 */     HashMap<String, String> filters = new HashMap();
/* 172:213 */     filters.put("codigo", consulta.trim() + "%");
/* 173:    */     try
/* 174:    */     {
/* 175:215 */       lista = this.servicioProducto.obtenerProductos(sortField, true, filters);
/* 176:    */     }
/* 177:    */     catch (ExcepcionAS2Inventario e)
/* 178:    */     {
/* 179:218 */       e.printStackTrace();
/* 180:    */     }
/* 181:220 */     return lista;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String cargarItems()
/* 185:    */   {
/* 186:227 */     if (this.productoItems == null)
/* 187:    */     {
/* 188:228 */       this.productoItems = new ArrayList();
/* 189:230 */       for (Producto producto : this.listaProducto)
/* 190:    */       {
/* 191:231 */         SelectItem opcion = new SelectItem(Integer.valueOf(producto.getIdProducto()), producto.getNombre());
/* 192:232 */         this.productoItems.add(opcion);
/* 193:    */       }
/* 194:    */     }
/* 195:236 */     return "";
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String limpiar()
/* 199:    */   {
/* 200:246 */     crearProducto();
/* 201:247 */     return "";
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String actualizarIndicadorImpuestos()
/* 205:    */   {
/* 206:257 */     if (!getProducto().isIndicadorImpuestos()) {
/* 207:258 */       getProducto().setCategoriaImpuesto(null);
/* 208:    */     }
/* 209:261 */     return "";
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Producto getProducto()
/* 213:    */   {
/* 214:265 */     if (this.producto == null) {
/* 215:266 */       crearProducto();
/* 216:    */     }
/* 217:269 */     return this.producto;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setProducto(Producto producto)
/* 221:    */   {
/* 222:273 */     this.producto = producto;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public LazyDataModel<Producto> getListaProducto()
/* 226:    */   {
/* 227:277 */     return this.listaProducto;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setListaProducto(LazyDataModel<Producto> productos)
/* 231:    */   {
/* 232:281 */     this.listaProducto = productos;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List<SelectItem> getProductoItems()
/* 236:    */   {
/* 237:285 */     return this.productoItems;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setProductoItems(List<SelectItem> productoItems)
/* 241:    */   {
/* 242:289 */     this.productoItems = productoItems;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public List<SelectItem> getListaTipoProducto()
/* 246:    */   {
/* 247:298 */     if (this.listaTipoProducto == null)
/* 248:    */     {
/* 249:299 */       this.listaTipoProducto = new ArrayList();
/* 250:300 */       this.listaTipoProducto.add(new SelectItem(TipoProducto.SERVICIO, TipoProducto.SERVICIO.getNombre()));
/* 251:301 */       this.listaTipoProducto.add(new SelectItem(TipoProducto.ARTICULO, TipoProducto.ARTICULO.getNombre()));
/* 252:    */     }
/* 253:303 */     return this.listaTipoProducto;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setListaTipoProducto(List<SelectItem> listaTipoProducto)
/* 257:    */   {
/* 258:314 */     this.listaTipoProducto = listaTipoProducto;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public List<CategoriaImpuesto> getListaCategoriaImpuesto()
/* 262:    */   {
/* 263:323 */     if (this.listaCategoriaImpuesto == null) {
/* 264:324 */       this.listaCategoriaImpuesto = this.servicioCategoriaImpuesto.obtenerListaCombo("codigo", true, null);
/* 265:    */     }
/* 266:326 */     return this.listaCategoriaImpuesto;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setListaCategoriaImpuesto(List<CategoriaImpuesto> listaCategoriaImpuesto)
/* 270:    */   {
/* 271:336 */     this.listaCategoriaImpuesto = listaCategoriaImpuesto;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public DataTable getDtProducto()
/* 275:    */   {
/* 276:340 */     return this.dtProducto;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setDtProducto(DataTable dtProducto)
/* 280:    */   {
/* 281:344 */     this.dtProducto = dtProducto;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public SelectItem[] getListaTipoProductoItem()
/* 285:    */   {
/* 286:354 */     if (this.listaTipoProductoItem == null)
/* 287:    */     {
/* 288:355 */       List<SelectItem> lista = new ArrayList();
/* 289:356 */       lista.add(new SelectItem("", ""));
/* 290:358 */       for (TipoProducto t : TipoProducto.values())
/* 291:    */       {
/* 292:359 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 293:360 */         lista.add(item);
/* 294:    */       }
/* 295:362 */       this.listaTipoProductoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 296:    */     }
/* 297:365 */     return this.listaTipoProductoItem;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setListaTipoProductoItem(SelectItem[] listaTipoProductoItem)
/* 301:    */   {
/* 302:375 */     this.listaTipoProductoItem = listaTipoProductoItem;
/* 303:    */   }
/* 304:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.ProductoAgilBean
 * JD-Core Version:    0.7.0.1
 */