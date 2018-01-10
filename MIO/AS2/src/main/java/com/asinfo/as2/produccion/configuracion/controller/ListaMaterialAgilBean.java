/*   1:    */ package com.asinfo.as2.produccion.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.MarcaProducto;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.ProductoMaterial;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcaProducto;
/*  17:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.JsfUtil;
/*  20:    */ import java.io.Serializable;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ManagedProperty;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.validation.constraints.Min;
/*  31:    */ import javax.validation.constraints.NotNull;
/*  32:    */ import org.primefaces.component.datatable.DataTable;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ListaMaterialAgilBean
/*  37:    */   extends PageControllerAS2
/*  38:    */   implements Serializable
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 2670004787702683037L;
/*  41:    */   @EJB
/*  42:    */   private ServicioProducto servicioProducto;
/*  43:    */   @EJB
/*  44:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  45:    */   @EJB
/*  46:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  47:    */   @EJB
/*  48:    */   private ServicioMarcaProducto servicioMarcaProducto;
/*  49:    */   @EJB
/*  50:    */   private ServicioGenerico<ProductoMaterial> servicioProductoMaterial;
/*  51:    */   private List<ProductoMaterial> listaProductoMaterial;
/*  52:    */   private List<ProductoMaterial> listaProductoMaterialSeleccionados;
/*  53:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  54:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  55:    */   private List<MarcaProducto> listaMarcaProducto;
/*  56:    */   private DataTable dtProductoMaterial;
/*  57:    */   @ManagedProperty("#{listaProductoBean}")
/*  58:    */   private ListaProductoBean listaProductoBean;
/*  59:    */   private CategoriaProducto categoriaProducto;
/*  60:    */   private SubcategoriaProducto subcategoriaProducto;
/*  61:    */   private MarcaProducto marcaProducto;
/*  62:    */   @NotNull
/*  63:    */   private Producto material;
/*  64:    */   @Min(0L)
/*  65: 90 */   private BigDecimal proporcionAsignar = new BigDecimal(100);
/*  66:    */   
/*  67:    */   @PostConstruct
/*  68:    */   public void init()
/*  69:    */   {
/*  70: 95 */     this.proporcionAsignar = new BigDecimal(100);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String editar()
/*  74:    */   {
/*  75:105 */     return "";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String guardar()
/*  79:    */   {
/*  80:115 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String eliminar()
/*  84:    */   {
/*  85:125 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String limpiar()
/*  89:    */   {
/*  90:135 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String crear()
/*  94:    */   {
/*  95:140 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String cargarDatos()
/*  99:    */   {
/* 100:150 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<ProductoMaterial> getListaProductoMaterial()
/* 104:    */   {
/* 105:154 */     return this.listaProductoMaterial;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setListaProductoMaterial(List<ProductoMaterial> listaProductoMaterial)
/* 109:    */   {
/* 110:158 */     this.listaProductoMaterial = listaProductoMaterial;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public ListaProductoBean getListaProductoBean()
/* 114:    */   {
/* 115:162 */     return this.listaProductoBean;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 119:    */   {
/* 120:166 */     this.listaProductoBean = listaProductoBean;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public CategoriaProducto getCategoriaProducto()
/* 124:    */   {
/* 125:170 */     return this.categoriaProducto;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 129:    */   {
/* 130:174 */     this.categoriaProducto = categoriaProducto;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 134:    */   {
/* 135:178 */     return this.subcategoriaProducto;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 139:    */   {
/* 140:182 */     this.subcategoriaProducto = subcategoriaProducto;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public MarcaProducto getMarcaProducto()
/* 144:    */   {
/* 145:186 */     return this.marcaProducto;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setMarcaProducto(MarcaProducto marcaProducto)
/* 149:    */   {
/* 150:190 */     this.marcaProducto = marcaProducto;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Producto getMaterial()
/* 154:    */   {
/* 155:194 */     return this.material;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setMaterial(Producto material)
/* 159:    */   {
/* 160:198 */     this.material = material;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public BigDecimal getProporcionAsignar()
/* 164:    */   {
/* 165:202 */     return this.proporcionAsignar;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setProporcionAsignar(BigDecimal proporcionAsignar)
/* 169:    */   {
/* 170:206 */     this.proporcionAsignar = proporcionAsignar;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 174:    */   {
/* 175:213 */     HashMap<String, String> filters = new HashMap();
/* 176:214 */     agregarFiltroOrganizacion(filters);
/* 177:215 */     if (this.listaCategoriaProductos == null) {
/* 178:216 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 179:    */     }
/* 180:218 */     return this.listaCategoriaProductos;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 184:    */   {
/* 185:226 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 189:    */   {
/* 190:234 */     return this.listaSubcategoriaProductos;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 194:    */   {
/* 195:242 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<ProductoMaterial> getListaProductoMaterialSeleccionados()
/* 199:    */   {
/* 200:246 */     return this.listaProductoMaterialSeleccionados;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setListaProductoMaterialSeleccionados(List<ProductoMaterial> listaProductoMaterialSeleccionados)
/* 204:    */   {
/* 205:250 */     this.listaProductoMaterialSeleccionados = listaProductoMaterialSeleccionados;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public DataTable getDtProductoMaterial()
/* 209:    */   {
/* 210:254 */     return this.dtProductoMaterial;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setDtProductoMaterial(DataTable dtProductoMaterial)
/* 214:    */   {
/* 215:258 */     this.dtProductoMaterial = dtProductoMaterial;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void cargarListaSubcategoriaProducto()
/* 219:    */   {
/* 220:262 */     HashMap<String, String> filters = new HashMap();
/* 221:263 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 222:264 */     agregarFiltroOrganizacion(filters);
/* 223:265 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<MarcaProducto> getListaMarcaProducto()
/* 227:    */   {
/* 228:269 */     if (this.listaMarcaProducto == null)
/* 229:    */     {
/* 230:270 */       Map<String, String> filters = new HashMap();
/* 231:271 */       agregarFiltroOrganizacion(filters);
/* 232:272 */       filters.put("activo", "true");
/* 233:273 */       this.listaMarcaProducto = this.servicioMarcaProducto.obtenerListaCombo("nombre", true, filters);
/* 234:    */     }
/* 235:275 */     return this.listaMarcaProducto;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setListaMarcaProducto(List<MarcaProducto> listaMarcaProducto)
/* 239:    */   {
/* 240:279 */     this.listaMarcaProducto = listaMarcaProducto;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void asignarProporcion()
/* 244:    */   {
/* 245:    */     try
/* 246:    */     {
/* 247:284 */       if ((this.listaProductoMaterialSeleccionados == null) || (this.listaProductoMaterialSeleccionados.isEmpty()))
/* 248:    */       {
/* 249:285 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 250:286 */         return;
/* 251:    */       }
/* 252:288 */       for (ProductoMaterial productoMaterial : this.listaProductoMaterialSeleccionados)
/* 253:    */       {
/* 254:289 */         productoMaterial.setProporcionMaterialPrincipalHijo(this.proporcionAsignar);
/* 255:290 */         this.servicioProductoMaterial.guardar(productoMaterial);
/* 256:    */       }
/* 257:292 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 258:    */     }
/* 259:    */     catch (AS2Exception e)
/* 260:    */     {
/* 261:294 */       JsfUtil.addErrorMessage(e, "");
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void buscar()
/* 266:    */   {
/* 267:299 */     this.listaProductoMaterial = this.servicioProducto.getProductosCambioAngilProporcionProduccion(AppUtil.getOrganizacion().getId(), this.subcategoriaProducto, this.categoriaProducto, this.marcaProducto, this.material);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void cargarProducto(Producto material)
/* 271:    */   {
/* 272:304 */     this.material = material;
/* 273:305 */     buscar();
/* 274:    */   }
/* 275:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.controller.ListaMaterialAgilBean
 * JD-Core Version:    0.7.0.1
 */