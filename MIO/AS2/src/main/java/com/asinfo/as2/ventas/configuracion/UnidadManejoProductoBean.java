/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  10:    */ import com.asinfo.as2.entities.UnidadManejoProducto;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.JsfUtil;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.annotation.PostConstruct;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ManagedProperty;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import javax.faces.component.html.HtmlInputText;
/*  29:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ import org.primefaces.component.datatable.DataTable;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class UnidadManejoProductoBean
/*  36:    */   extends PageControllerAS2
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @EJB
/*  40:    */   private ServicioGenerico<UnidadManejoProducto> servicioUnidadManejoProducto;
/*  41:    */   @EJB
/*  42:    */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*  43:    */   @EJB
/*  44:    */   private ServicioProducto servicioProducto;
/*  45:    */   @ManagedProperty("#{listaProductoBean}")
/*  46:    */   private ListaProductoBean listaProductoBean;
/*  47:    */   private UnidadManejoProducto unidadManejoProducto;
/*  48:    */   private List<UnidadManejoProducto> listaUnidadManejoProducto;
/*  49:    */   private List<UnidadManejoProducto> listaUnidadManejoProductoFiltrados;
/*  50:    */   private DataTable dataTableUnidadManejoProducto;
/*  51:    */   private List<UnidadManejo> listaUnidadManejo;
/*  52:    */   
/*  53:    */   @PostConstruct
/*  54:    */   public void init()
/*  55:    */   {
/*  56: 87 */     getListaProductoBean().setActivo(true);
/*  57: 88 */     getListaProductoBean().setIndicadorPesoBalanza(true);
/*  58: 89 */     Map<String, String> filters = new HashMap();
/*  59: 90 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  60: 91 */     filters.put("unidadManejo.activo", "true");
/*  61: 92 */     List<String> listaCampos = new ArrayList();
/*  62: 93 */     listaCampos.add("unidadManejo.categoriaUnidadManejo");
/*  63: 94 */     listaCampos.add("unidadManejo.unidad");
/*  64: 95 */     listaCampos.add("producto");
/*  65: 96 */     this.listaUnidadManejoProducto = this.servicioUnidadManejoProducto.obtenerListaPorPagina(UnidadManejoProducto.class, 0, 100000, "unidadManejo.nombre", true, filters, listaCampos);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String editar()
/*  69:    */   {
/*  70:107 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:117 */     return "";
/*  76:    */   }
/*  77:    */   
/*  78:    */   private boolean validar()
/*  79:    */   {
/*  80:121 */     boolean correcto = true;
/*  81:122 */     if (this.unidadManejoProducto.getPesoMinimo().compareTo(this.unidadManejoProducto.getUnidadManejo().getPeso()) < 0)
/*  82:    */     {
/*  83:123 */       correcto = false;
/*  84:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_peso_minimo_menor_unidad_manejo") + ". " + this.unidadManejoProducto
/*  85:125 */         .getProducto().getNombre());
/*  86:    */     }
/*  87:127 */     if ((this.unidadManejoProducto.getPesoMinimo().compareTo(this.unidadManejoProducto.getPesoPromedio()) > 0) || 
/*  88:128 */       (this.unidadManejoProducto.getPesoPromedio().compareTo(this.unidadManejoProducto.getPesoMaximo()) > 0))
/*  89:    */     {
/*  90:129 */       correcto = false;
/*  91:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_pesos_minimo_maximo_promedio") + ". " + this.unidadManejoProducto
/*  92:131 */         .getProducto().getNombre());
/*  93:    */     }
/*  94:133 */     return correcto;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String eliminar()
/*  98:    */   {
/*  99:143 */     eliminarUnidadManejoProducto(this.unidadManejoProducto);
/* 100:144 */     this.listaUnidadManejoProductoFiltrados = null;
/* 101:145 */     this.dataTableUnidadManejoProducto.reset();
/* 102:146 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:156 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:166 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public ListaProductoBean getListaProductoBean()
/* 116:    */   {
/* 117:170 */     return this.listaProductoBean;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 121:    */   {
/* 122:174 */     this.listaProductoBean = listaProductoBean;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public DataTable getDataTableUnidadManejoProducto()
/* 126:    */   {
/* 127:178 */     return this.dataTableUnidadManejoProducto;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setDataTableUnidadManejoProducto(DataTable dataTableUnidadManejoProducto)
/* 131:    */   {
/* 132:182 */     this.dataTableUnidadManejoProducto = dataTableUnidadManejoProducto;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 136:    */   {
/* 137:192 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 138:193 */     UnidadManejoProducto detalle = (UnidadManejoProducto)this.dataTableUnidadManejoProducto.getRowData();
/* 139:    */     try
/* 140:    */     {
/* 141:196 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 142:197 */       detalle.setProducto(producto);
/* 143:    */     }
/* 144:    */     catch (ExcepcionAS2 e)
/* 145:    */     {
/* 146:199 */       e.printStackTrace();
/* 147:200 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/* 152:    */   {
/* 153:205 */     if (saldoLote.getProducto().equals(getListaProductoBean().getProducto()))
/* 154:    */     {
/* 155:206 */       getListaProductoBean().setSaldoProductoLote(saldoLote);
/* 156:207 */       cargarProducto();
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void cargarProducto(Producto producto)
/* 161:    */   {
/* 162:212 */     getListaProductoBean().setProducto(producto);
/* 163:213 */     getListaProductoBean().setSaldoProductoLote(null);
/* 164:214 */     cargarProducto();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void cargarProducto()
/* 168:    */   {
/* 169:221 */     Producto producto = getListaProductoBean().getProducto();
/* 170:222 */     if (producto != null)
/* 171:    */     {
/* 172:223 */       UnidadManejoProducto detalle = new UnidadManejoProducto();
/* 173:224 */       detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 174:225 */       detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 175:226 */       detalle.setProducto(producto);
/* 176:227 */       this.listaUnidadManejoProducto.add(detalle);
/* 177:    */     }
/* 178:229 */     getListaProductoBean().setProducto(null);
/* 179:230 */     this.listaUnidadManejoProductoFiltrados = null;
/* 180:231 */     this.dataTableUnidadManejoProducto.reset();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String agregarDetalle()
/* 184:    */   {
/* 185:235 */     UnidadManejoProducto detalle = new UnidadManejoProducto();
/* 186:236 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 187:237 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 188:238 */     detalle.setProducto(new Producto());
/* 189:239 */     this.listaUnidadManejoProducto.add(detalle);
/* 190:240 */     this.listaUnidadManejoProductoFiltrados = null;
/* 191:241 */     this.dataTableUnidadManejoProducto.reset();
/* 192:242 */     return "";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public UnidadManejoProducto getUnidadManejoProducto()
/* 196:    */   {
/* 197:246 */     return this.unidadManejoProducto;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setUnidadManejoProducto(UnidadManejoProducto unidadManejoProducto)
/* 201:    */   {
/* 202:250 */     this.unidadManejoProducto = unidadManejoProducto;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public List<UnidadManejoProducto> getListaUnidadManejoProducto()
/* 206:    */   {
/* 207:254 */     return this.listaUnidadManejoProducto;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setListaUnidadManejoProducto(List<UnidadManejoProducto> listaUnidadManejoProducto)
/* 211:    */   {
/* 212:258 */     this.listaUnidadManejoProducto = listaUnidadManejoProducto;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<UnidadManejo> getListaUnidadManejo()
/* 216:    */   {
/* 217:262 */     if (this.listaUnidadManejo == null)
/* 218:    */     {
/* 219:263 */       Map<String, String> filtros = new HashMap();
/* 220:264 */       filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 221:265 */       filtros.put("activo", "true");
/* 222:266 */       filtros.put("categoriaUnidadManejo.indicadorPallet", "!=true");
/* 223:267 */       List<String> listaCampos = new ArrayList();
/* 224:268 */       listaCampos.add("categoriaUnidadManejo");
/* 225:269 */       listaCampos.add("unidad");
/* 226:270 */       this.listaUnidadManejo = this.servicioUnidadManejo.obtenerListaPorPagina(UnidadManejo.class, 0, 100000, "nombre", true, filtros, listaCampos);
/* 227:    */     }
/* 228:272 */     return this.listaUnidadManejo;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/* 232:    */   {
/* 233:276 */     this.listaUnidadManejo = listaUnidadManejo;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void eliminarUnidadManejoProducto(UnidadManejoProducto detalle)
/* 237:    */   {
/* 238:280 */     this.unidadManejoProducto = detalle;
/* 239:281 */     detalle.setEliminado(true);
/* 240:282 */     if (detalle.getId() != 0) {
/* 241:    */       try
/* 242:    */       {
/* 243:284 */         this.servicioUnidadManejoProducto.eliminar(detalle);
/* 244:    */       }
/* 245:    */       catch (Exception e)
/* 246:    */       {
/* 247:286 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 248:287 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 249:288 */         return;
/* 250:    */       }
/* 251:    */     }
/* 252:291 */     for (int i = 0; i < this.listaUnidadManejoProducto.size(); i++) {
/* 253:292 */       if (((UnidadManejoProducto)this.listaUnidadManejoProducto.get(i)).isEliminado())
/* 254:    */       {
/* 255:293 */         this.listaUnidadManejoProducto.remove(i);
/* 256:294 */         break;
/* 257:    */       }
/* 258:    */     }
/* 259:298 */     addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void guardarUnidadManejoProducto(UnidadManejoProducto detalle)
/* 263:    */   {
/* 264:302 */     this.unidadManejoProducto = detalle;
/* 265:    */     try
/* 266:    */     {
/* 267:304 */       if (validar())
/* 268:    */       {
/* 269:305 */         this.servicioUnidadManejoProducto.guardar(detalle);
/* 270:306 */         detalle.setEditado(false);
/* 271:307 */         cargarDatos();
/* 272:308 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 273:    */       }
/* 274:    */     }
/* 275:    */     catch (AS2Exception e)
/* 276:    */     {
/* 277:311 */       JsfUtil.addErrorMessage(e, "");
/* 278:    */     }
/* 279:    */     catch (Exception e)
/* 280:    */     {
/* 281:313 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 282:314 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public List<UnidadManejoProducto> getListaUnidadManejoProductoFiltrados()
/* 287:    */   {
/* 288:319 */     return this.listaUnidadManejoProductoFiltrados;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setListaUnidadManejoProductoFiltrados(List<UnidadManejoProducto> listaUnidadManejoProductoFiltrados)
/* 292:    */   {
/* 293:323 */     this.listaUnidadManejoProductoFiltrados = listaUnidadManejoProductoFiltrados;
/* 294:    */   }
/* 295:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.UnidadManejoProductoBean
 * JD-Core Version:    0.7.0.1
 */