/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.JsfUtil;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ManagedProperty;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class CambioMasivoListaDeMaterilesBean
/*  30:    */   extends PageControllerAS2
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  35:    */   @EJB
/*  36:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  37:    */   @EJB
/*  38:    */   private ServicioProducto servicioProducto;
/*  39:    */   private CategoriaProducto categoriaProducto;
/*  40:    */   private SubcategoriaProducto subcategoriaProducto;
/*  41:    */   private BigDecimal total;
/*  42:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  43:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  44:    */   private List<Producto> listaMateriales;
/*  45:    */   private List<Producto> listaMaterialesComunes;
/*  46:    */   private List<Producto> listaProductosSeleccionados;
/*  47:    */   private DataTable dtMaterial;
/*  48:    */   private DataTable dtMaterialComun;
/*  49:    */   @ManagedProperty("#{listaProductoBean}")
/*  50:    */   private ListaProductoBean listaProductoBean;
/*  51:    */   private List<SelectItem> listaTipoReporte;
/*  52:    */   
/*  53:    */   private static enum enumTipoReporte
/*  54:    */   {
/*  55: 84 */     ACTUALIZAR,  AGREGAR;
/*  56:    */     
/*  57:    */     private enumTipoReporte() {}
/*  58:    */   }
/*  59:    */   
/*  60: 94 */   private enumTipoReporte tipoReporte = enumTipoReporte.ACTUALIZAR;
/*  61:    */   
/*  62:    */   public void cargarListaSubcategoriaProducto()
/*  63:    */   {
/*  64:100 */     limpiarParcial();
/*  65:101 */     HashMap<String, String> filters = new HashMap();
/*  66:102 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/*  67:103 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void cargarListaProductos()
/*  71:    */   {
/*  72:108 */     this.listaMateriales = this.servicioProducto.getListaProductos(AppUtil.getOrganizacion().getIdOrganizacion(), this.categoriaProducto, this.subcategoriaProducto, this.tipoReporte
/*  73:109 */       .equals(enumTipoReporte.AGREGAR) ? new ArrayList() : getListaMaterialesComunesNoEliminados());
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void totalizar()
/*  77:    */   {
/*  78:114 */     setTotal(BigDecimal.ZERO);
/*  79:115 */     for (Producto producto : getListaMaterialesComunesNoEliminados()) {
/*  80:116 */       setTotal(getTotal().add(producto.getPorcentajeReceta()));
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void procesarNuevosPorcentajes()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:123 */       if (getListaProductosSeleccionados().size() == 0)
/*  89:    */       {
/*  90:124 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_producto"));
/*  91:    */       }
/*  92:    */       else
/*  93:    */       {
/*  94:126 */         if (this.tipoReporte.equals(enumTipoReporte.AGREGAR)) {
/*  95:127 */           this.servicioProducto.agregarNuevosMateriales(getListaProductosSeleccionados(), getListaMaterialesComunesNoEliminados());
/*  96:    */         } else {
/*  97:129 */           this.servicioProducto.procesarNuevosPorcentajes(getListaProductosSeleccionados(), getListaMaterialesComunesNoEliminados());
/*  98:    */         }
/*  99:131 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 100:    */       }
/* 101:    */     }
/* 102:    */     catch (AS2Exception e)
/* 103:    */     {
/* 104:135 */       JsfUtil.addErrorMessage(e, "");
/* 105:136 */       e.printStackTrace();
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void cargarProducto(Producto producto)
/* 110:    */   {
/* 111:141 */     getListaProductoBean().setProducto(producto);
/* 112:142 */     getListaMaterialesComunes().add(getListaProductoBean().getProducto());
/* 113:143 */     cargarListaProductosPorCriterio();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String eliminarMaterial()
/* 117:    */   {
/* 118:148 */     Producto p = (Producto)this.dtMaterialComun.getRowData();
/* 119:149 */     p.setEliminado(true);
/* 120:150 */     cargarListaProductosPorCriterio();
/* 121:151 */     totalizar();
/* 122:152 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void cargarListaProductosPorCriterio()
/* 126:    */   {
/* 127:156 */     if (this.tipoReporte.equals(enumTipoReporte.ACTUALIZAR)) {
/* 128:157 */       cargarListaProductos();
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String editar()
/* 133:    */   {
/* 134:164 */     return null;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String guardar()
/* 138:    */   {
/* 139:170 */     return null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String eliminar()
/* 143:    */   {
/* 144:176 */     return null;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String limpiar()
/* 148:    */   {
/* 149:181 */     this.categoriaProducto = null;
/* 150:182 */     limpiarParcial();
/* 151:183 */     return null;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String limpiarParcial()
/* 155:    */   {
/* 156:187 */     this.subcategoriaProducto = null;
/* 157:188 */     this.total = BigDecimal.ZERO;
/* 158:189 */     this.listaCategoriaProductos = null;
/* 159:190 */     this.listaSubcategoriaProductos = null;
/* 160:191 */     this.listaMateriales = null;
/* 161:192 */     this.listaMaterialesComunes = null;
/* 162:193 */     this.listaProductosSeleccionados = null;
/* 163:194 */     return null;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String cargarDatos()
/* 167:    */   {
/* 168:200 */     return null;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public CategoriaProducto getCategoriaProducto()
/* 172:    */   {
/* 173:204 */     return this.categoriaProducto;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 177:    */   {
/* 178:208 */     this.categoriaProducto = categoriaProducto;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 182:    */   {
/* 183:212 */     HashMap<String, String> filters = new HashMap();
/* 184:213 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 185:214 */     if (this.listaCategoriaProductos == null) {
/* 186:215 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 187:    */     }
/* 188:217 */     return this.listaCategoriaProductos;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 192:    */   {
/* 193:221 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 197:    */   {
/* 198:225 */     return this.subcategoriaProducto;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 202:    */   {
/* 203:229 */     this.subcategoriaProducto = subcategoriaProducto;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 207:    */   {
/* 208:233 */     return this.listaSubcategoriaProductos;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 212:    */   {
/* 213:237 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<Producto> getListaMateriales()
/* 217:    */   {
/* 218:241 */     return this.listaMateriales;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setListaMateriales(List<Producto> listaMateriales)
/* 222:    */   {
/* 223:245 */     this.listaMateriales = listaMateriales;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public DataTable getDtMaterial()
/* 227:    */   {
/* 228:249 */     return this.dtMaterial;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setDtMaterial(DataTable dtMaterial)
/* 232:    */   {
/* 233:253 */     this.dtMaterial = dtMaterial;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<Producto> getListaMaterialesComunesNoEliminados()
/* 237:    */   {
/* 238:257 */     List<Producto> listaMaterialesComunes = new ArrayList();
/* 239:259 */     for (Producto materialeComun : getListaMaterialesComunes()) {
/* 240:260 */       if (!materialeComun.isEliminado()) {
/* 241:261 */         listaMaterialesComunes.add(materialeComun);
/* 242:    */       }
/* 243:    */     }
/* 244:265 */     return listaMaterialesComunes;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<Producto> getListaMaterialesComunes()
/* 248:    */   {
/* 249:269 */     return this.listaMaterialesComunes == null ? (this.listaMaterialesComunes = new ArrayList()) : this.listaMaterialesComunes;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setListaMaterialesComunes(List<Producto> listaMaterialesComunes)
/* 253:    */   {
/* 254:273 */     this.listaMaterialesComunes = listaMaterialesComunes;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public DataTable getDtMaterialComun()
/* 258:    */   {
/* 259:277 */     return this.dtMaterialComun;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setDtMaterialComun(DataTable dtMaterialComun)
/* 263:    */   {
/* 264:281 */     this.dtMaterialComun = dtMaterialComun;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public BigDecimal getTotal()
/* 268:    */   {
/* 269:285 */     return this.total;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setTotal(BigDecimal total)
/* 273:    */   {
/* 274:289 */     this.total = total;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public List<Producto> getListaProductosSeleccionados()
/* 278:    */   {
/* 279:293 */     return this.listaProductosSeleccionados == null ? (this.listaProductosSeleccionados = new ArrayList()) : this.listaProductosSeleccionados;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setListaProductosSeleccionados(List<Producto> listaProductosSeleccionados)
/* 283:    */   {
/* 284:297 */     this.listaProductosSeleccionados = listaProductosSeleccionados;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public ListaProductoBean getListaProductoBean()
/* 288:    */   {
/* 289:301 */     return this.listaProductoBean;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 293:    */   {
/* 294:305 */     this.listaProductoBean = listaProductoBean;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<SelectItem> getListaTipoReporte()
/* 298:    */   {
/* 299:309 */     if (this.listaTipoReporte == null)
/* 300:    */     {
/* 301:310 */       this.listaTipoReporte = new ArrayList();
/* 302:311 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 303:312 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 304:    */       }
/* 305:    */     }
/* 306:315 */     return this.listaTipoReporte;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 310:    */   {
/* 311:319 */     this.listaTipoReporte = listaTipoReporte;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public enumTipoReporte getTipoReporte()
/* 315:    */   {
/* 316:323 */     return this.tipoReporte;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 320:    */   {
/* 321:327 */     this.tipoReporte = tipoReporte;
/* 322:    */   }
/* 323:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.CambioMasivoListaDeMaterilesBean
 * JD-Core Version:    0.7.0.1
 */