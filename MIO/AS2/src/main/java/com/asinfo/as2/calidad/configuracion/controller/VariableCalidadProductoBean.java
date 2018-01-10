/*   1:    */ package com.asinfo.as2.calidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.calidad.VariableCalidad;
/*   9:    */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  12:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  13:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.JsfUtil;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.event.SelectEvent;
/*  27:    */ import org.primefaces.model.LazyDataModel;
/*  28:    */ import org.primefaces.model.SortOrder;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class VariableCalidadProductoBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @EJB
/*  37:    */   private ServicioProducto servicioProducto;
/*  38:    */   @EJB
/*  39:    */   private ServicioGenerico<VariableCalidad> servicioVariableCalidad;
/*  40:    */   private Producto producto;
/*  41:    */   private LazyDataModel<Producto> listaProducto;
/*  42:    */   private List<VariableCalidadProducto> listaVariableCalidadProducto;
/*  43:    */   private VariableCalidad[] listaVariableCalidadSeleccionados;
/*  44:    */   private List<VariableCalidad> listaVariableCalidadNoAsignados;
/*  45:    */   private DataTable dtProducto;
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 61 */     this.listaProducto = new LazyDataModel()
/*  51:    */     {
/*  52:    */       private static final long serialVersionUID = 1L;
/*  53:    */       
/*  54:    */       public List<Producto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  55:    */       {
/*  56: 67 */         List<Producto> lista = new ArrayList();
/*  57: 68 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  58: 69 */         filters.put("indicadorControlCalidad", "true");
/*  59:    */         try
/*  60:    */         {
/*  61: 71 */           lista = VariableCalidadProductoBean.this.servicioProducto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  62: 72 */           VariableCalidadProductoBean.this.listaProducto.setRowCount(VariableCalidadProductoBean.this.servicioProducto.contarPorCriterio(filters));
/*  63:    */         }
/*  64:    */         catch (ExcepcionAS2Inventario e)
/*  65:    */         {
/*  66: 74 */           e.printStackTrace();
/*  67:    */         }
/*  68: 76 */         return lista;
/*  69:    */       }
/*  70:    */     };
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String editar()
/*  74:    */   {
/*  75: 88 */     if ((this.producto != null) && (this.producto.getIdProducto() != 0))
/*  76:    */     {
/*  77: 89 */       this.producto = this.servicioProducto.cargarDetalleListaVariableCalidadProducto(this.producto);
/*  78: 90 */       setEditado(true);
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82: 92 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  83:    */     }
/*  84: 95 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String crear()
/*  88:    */   {
/*  89:100 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  90:101 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String guardar()
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:112 */       this.servicioProducto.guardarListaVariableCalidadProducto(this.producto);
/*  98:113 */       limpiar();
/*  99:114 */       setEditado(false);
/* 100:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 101:    */     }
/* 102:    */     catch (AS2Exception e)
/* 103:    */     {
/* 104:118 */       JsfUtil.addErrorMessage(e, "");
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:120 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 109:121 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 110:    */     }
/* 111:124 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String eliminar()
/* 115:    */   {
/* 116:134 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 117:135 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String limpiar()
/* 121:    */   {
/* 122:145 */     this.listaVariableCalidadProducto = null;
/* 123:146 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String cargarDatos()
/* 127:    */   {
/* 128:156 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void crearProducto()
/* 132:    */   {
/* 133:165 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void onRowSelect(SelectEvent event)
/* 137:    */   {
/* 138:174 */     this.producto = ((Producto)event.getObject());
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Producto getProducto()
/* 142:    */   {
/* 143:178 */     return this.producto;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setProducto(Producto producto)
/* 147:    */   {
/* 148:182 */     this.producto = producto;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public LazyDataModel<Producto> getListaProducto()
/* 152:    */   {
/* 153:191 */     return this.listaProducto;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setListaProducto(LazyDataModel<Producto> listaProducto)
/* 157:    */   {
/* 158:201 */     this.listaProducto = listaProducto;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public DataTable getDtProducto()
/* 162:    */   {
/* 163:210 */     return this.dtProducto;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setDtProducto(DataTable dtProducto)
/* 167:    */   {
/* 168:220 */     this.dtProducto = dtProducto;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<VariableCalidadProducto> getListaVariableCalidadProducto()
/* 172:    */   {
/* 173:224 */     if ((this.producto != null) && (this.listaVariableCalidadProducto == null))
/* 174:    */     {
/* 175:225 */       this.listaVariableCalidadProducto = new ArrayList();
/* 176:226 */       for (VariableCalidadProducto vcp : this.producto.getListaVariableCalidadProducto()) {
/* 177:227 */         if (!vcp.isEliminado()) {
/* 178:228 */           this.listaVariableCalidadProducto.add(vcp);
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:233 */     return this.listaVariableCalidadProducto;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setListaVariableCalidadProducto(List<VariableCalidadProducto> listaVariableCalidadProducto)
/* 186:    */   {
/* 187:237 */     this.listaVariableCalidadProducto = listaVariableCalidadProducto;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void eliminarVariableCalidad(VariableCalidadProducto vcp)
/* 191:    */   {
/* 192:241 */     vcp.setEliminado(true);
/* 193:242 */     this.listaVariableCalidadProducto = null;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String agregarVariableCalidad()
/* 197:    */   {
/* 198:246 */     if (this.listaVariableCalidadSeleccionados != null) {
/* 199:    */       label174:
/* 200:247 */       for (VariableCalidad vc : this.listaVariableCalidadSeleccionados)
/* 201:    */       {
/* 202:249 */         for (VariableCalidadProducto vcp : this.producto.getListaVariableCalidadProducto()) {
/* 203:250 */           if (vcp.getVariableCalidad().equals(vc))
/* 204:    */           {
/* 205:251 */             vcp.setEliminado(false);
/* 206:252 */             getListaVariableCalidadProducto().add(vcp);
/* 207:    */             break label174;
/* 208:    */           }
/* 209:    */         }
/* 210:256 */         VariableCalidadProducto vcp = new VariableCalidadProducto();
/* 211:257 */         vcp.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 212:258 */         vcp.setIdSucursal(AppUtil.getSucursal().getId());
/* 213:259 */         vcp.setProducto(this.producto);
/* 214:260 */         vcp.setVariableCalidad(vc);
/* 215:261 */         this.producto.getListaVariableCalidadProducto().add(vcp);
/* 216:262 */         getListaVariableCalidadProducto().add(vcp);
/* 217:    */       }
/* 218:    */     }
/* 219:265 */     this.listaVariableCalidadSeleccionados = null;
/* 220:    */     
/* 221:267 */     return "";
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void cargarVariableCalidadNoAsignados()
/* 225:    */   {
/* 226:271 */     this.listaVariableCalidadSeleccionados = null;
/* 227:272 */     Map<String, String> filters = new HashMap();
/* 228:273 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 229:    */     
/* 230:275 */     List<String> lcampos = new ArrayList();
/* 231:276 */     lcampos.add("categoriaVariableCalidad");
/* 232:277 */     this.listaVariableCalidadNoAsignados = this.servicioVariableCalidad.obtenerListaPorPagina(VariableCalidad.class, 0, 1000, "codigo", true, filters, lcampos);
/* 233:    */     
/* 234:279 */     HashMap<Integer, VariableCalidad> mvc = new HashMap();
/* 235:280 */     for (VariableCalidad vc : this.listaVariableCalidadNoAsignados) {
/* 236:281 */       mvc.put(Integer.valueOf(vc.getId()), vc);
/* 237:    */     }
/* 238:284 */     for (VariableCalidadProducto vcp : this.producto.getListaVariableCalidadProducto()) {
/* 239:285 */       if (!vcp.isEliminado()) {
/* 240:286 */         mvc.remove(Integer.valueOf(vcp.getVariableCalidad().getId()));
/* 241:    */       }
/* 242:    */     }
/* 243:289 */     this.listaVariableCalidadNoAsignados = new ArrayList(mvc.values());
/* 244:    */   }
/* 245:    */   
/* 246:    */   public VariableCalidad[] getListaVariableCalidadSeleccionados()
/* 247:    */   {
/* 248:296 */     return this.listaVariableCalidadSeleccionados;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaVariableCalidadSeleccionados(VariableCalidad[] listaVariableCalidadSeleccionados)
/* 252:    */   {
/* 253:304 */     this.listaVariableCalidadSeleccionados = listaVariableCalidadSeleccionados;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public List<VariableCalidad> getListaVariableCalidadNoAsignados()
/* 257:    */   {
/* 258:311 */     return this.listaVariableCalidadNoAsignados;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setListaVariableCalidadNoAsignados(List<VariableCalidad> listaVariableCalidadNoAsignados)
/* 262:    */   {
/* 263:319 */     this.listaVariableCalidadNoAsignados = listaVariableCalidadNoAsignados;
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.configuracion.controller.VariableCalidadProductoBean
 * JD-Core Version:    0.7.0.1
 */