/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   8:    */ import com.asinfo.as2.entities.ListaDescuentos;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.JsfUtil;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.validation.constraints.Max;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class CambioDescuentoBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = -6458772552568395980L;
/*  36:    */   @EJB
/*  37:    */   private ServicioListaDescuentos servicioListaDescuentos;
/*  38:    */   @EJB
/*  39:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  40:    */   @EJB
/*  41:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  42:    */   @EJB
/*  43:    */   private ServicioProducto servicioProducto;
/*  44:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  45:    */   private SubcategoriaProducto subcategoriaProductoSeleccionado;
/*  46:    */   private Producto producto;
/*  47:    */   private List<ListaDescuentos> listadoListaDescuentoSeleccionados;
/*  48:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  49:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  50:    */   @Max(100L)
/*  51:    */   @Min(0L)
/*  52:    */   private BigDecimal porcentajeDescuento;
/*  53:    */   
/*  54:    */   public List<ListaDescuentos> autocompletarListaDescuento(String filtro)
/*  55:    */   {
/*  56: 77 */     Map<String, ListaDescuentos> hmListaDescuentos = new HashMap();
/*  57: 78 */     List<ListaDescuentos> ld = new ArrayList();
/*  58: 79 */     Map<String, String> filtros = new HashMap();
/*  59: 80 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  60: 81 */     filtros.put("indicadorDescuentoPorProducto", "true");
/*  61: 82 */     filtros.put("nombre", filtro);
/*  62: 83 */     ld = this.servicioListaDescuentos.obtenerListaCombo("nombre", false, filtros);
/*  63: 85 */     for (ListaDescuentos lisDes : ld) {
/*  64: 86 */       hmListaDescuentos.put(Integer.toString(lisDes.getIdListaDescuentos()), lisDes);
/*  65:    */     }
/*  66: 89 */     if (getListadoListaDescuentoSeleccionados() != null)
/*  67:    */     {
/*  68: 90 */       for (ListaDescuentos lisDes : getListadoListaDescuentoSeleccionados()) {
/*  69: 91 */         if (hmListaDescuentos.containsKey(Integer.toString(lisDes.getIdListaDescuentos()))) {
/*  70: 92 */           hmListaDescuentos.remove(Integer.toString(lisDes.getIdListaDescuentos()));
/*  71:    */         }
/*  72:    */       }
/*  73: 95 */       Object listaDes = new ArrayList();
/*  74: 96 */       ((List)listaDes).addAll(hmListaDescuentos.values());
/*  75:    */       
/*  76: 98 */       return listaDes;
/*  77:    */     }
/*  78:101 */     return ld;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void actualizarCategoriaYSubcategoria()
/*  82:    */   {
/*  83:107 */     this.categoriaProductoSeleccionado = null;
/*  84:108 */     this.subcategoriaProductoSeleccionado = null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void cargarListaSubcategoriaProducto()
/*  88:    */   {
/*  89:112 */     HashMap<String, String> filters = new HashMap();
/*  90:113 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/*  91:114 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<Producto> autocompletarProductos(String consulta)
/*  95:    */   {
/*  96:118 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void cambioMasivoDescuento()
/* 100:    */   {
/* 101:123 */     List<DetalleListaDescuentos> detalleListaDescuento = this.servicioListaDescuentos.listaCambioMasivoDescuento(this.listadoListaDescuentoSeleccionados, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, this.producto);
/* 102:    */     try
/* 103:    */     {
/* 104:127 */       this.servicioListaDescuentos.cambioMasivoDescuento(detalleListaDescuento, this.porcentajeDescuento);
/* 105:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 106:    */     }
/* 107:    */     catch (AS2Exception e)
/* 108:    */     {
/* 109:130 */       JsfUtil.addErrorMessage(e, "");
/* 110:131 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 111:132 */       e.printStackTrace();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void asignarProductoNoAsignadosListener()
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:138 */       this.servicioListaDescuentos.asignarProductoNoAsignados(this.listadoListaDescuentoSeleccionados, this.categoriaProductoSeleccionado, this.subcategoriaProductoSeleccionado, this.producto, this.porcentajeDescuento);
/* 120:    */       
/* 121:140 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 122:    */     }
/* 123:    */     catch (AS2Exception e)
/* 124:    */     {
/* 125:142 */       JsfUtil.addErrorMessage(e, "");
/* 126:143 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 127:144 */       e.printStackTrace();
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String editar()
/* 132:    */   {
/* 133:151 */     return null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String guardar()
/* 137:    */   {
/* 138:157 */     return null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String eliminar()
/* 142:    */   {
/* 143:163 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String limpiar()
/* 147:    */   {
/* 148:169 */     return null;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String cargarDatos()
/* 152:    */   {
/* 153:175 */     return null;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List<ListaDescuentos> getListadoListaDescuentoSeleccionados()
/* 157:    */   {
/* 158:182 */     return this.listadoListaDescuentoSeleccionados;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setListadoListaDescuentoSeleccionados(List<ListaDescuentos> listadoListaDescuentoSeleccionados)
/* 162:    */   {
/* 163:190 */     this.listadoListaDescuentoSeleccionados = listadoListaDescuentoSeleccionados;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 167:    */   {
/* 168:197 */     return this.categoriaProductoSeleccionado;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 172:    */   {
/* 173:205 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public SubcategoriaProducto getSubcategoriaProductoSeleccionado()
/* 177:    */   {
/* 178:212 */     return this.subcategoriaProductoSeleccionado;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setSubcategoriaProductoSeleccionado(SubcategoriaProducto subcategoriaProductoSeleccionado)
/* 182:    */   {
/* 183:220 */     this.subcategoriaProductoSeleccionado = subcategoriaProductoSeleccionado;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 187:    */   {
/* 188:224 */     HashMap<String, String> filters = new HashMap();
/* 189:225 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 190:226 */     if (this.listaCategoriaProductos == null) {
/* 191:227 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 192:    */     }
/* 193:229 */     return this.listaCategoriaProductos;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setListaCategoriaProductos(List<CategoriaProducto> listaCategoriaProductos)
/* 197:    */   {
/* 198:237 */     this.listaCategoriaProductos = listaCategoriaProductos;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 202:    */   {
/* 203:242 */     return this.listaSubcategoriaProductos;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setListaSubcategoriaProductos(List<SubcategoriaProducto> listaSubcategoriaProductos)
/* 207:    */   {
/* 208:250 */     this.listaSubcategoriaProductos = listaSubcategoriaProductos;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Producto getProducto()
/* 212:    */   {
/* 213:257 */     return this.producto;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setProducto(Producto producto)
/* 217:    */   {
/* 218:265 */     this.producto = producto;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public BigDecimal getPorcentajeDescuento()
/* 222:    */   {
/* 223:272 */     return this.porcentajeDescuento;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setPorcentajeDescuento(BigDecimal porcentajeDescuento)
/* 227:    */   {
/* 228:280 */     this.porcentajeDescuento = porcentajeDescuento;
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.CambioDescuentoBean
 * JD-Core Version:    0.7.0.1
 */