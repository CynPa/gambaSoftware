/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.DetalleVisualizacion;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.Visualizacion;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioVisualizacion;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.event.SelectEvent;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class VisualizacionBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 361732406456184593L;
/*  35:    */   @EJB
/*  36:    */   private ServicioVisualizacion servicioVisualizacion;
/*  37:    */   @EJB
/*  38:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  39:    */   @EJB
/*  40:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  41:    */   private Visualizacion visualizacion;
/*  42:    */   private DataTable dataTableVisualizacion;
/*  43:    */   private DataTable dtDetalleVisualizacion;
/*  44:    */   private LazyDataModel<Visualizacion> listaVisualizacion;
/*  45:    */   private CategoriaProducto categoriaProductoSeleccionada;
/*  46:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  47:    */   private SubcategoriaProducto subcategoriaProducto;
/*  48: 75 */   HashMap<String, DetalleVisualizacion> hmCategoria = new HashMap();
/*  49: 76 */   HashMap<Integer, Boolean> hmExiste = new HashMap();
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 85 */     this.listaVisualizacion = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = -7197895094631249580L;
/*  57:    */       
/*  58:    */       public List<Visualizacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 97 */         List<Visualizacion> lista = new ArrayList();
/*  61:    */         
/*  62: 99 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  63:    */         
/*  64:101 */         lista = VisualizacionBean.this.servicioVisualizacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  65:    */         
/*  66:103 */         VisualizacionBean.this.listaVisualizacion.setRowCount(VisualizacionBean.this.servicioVisualizacion.contarPorCriterio(filters));
/*  67:104 */         return lista;
/*  68:    */       }
/*  69:    */     };
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String editar()
/*  73:    */   {
/*  74:118 */     this.hmCategoria.clear();
/*  75:119 */     this.hmExiste.clear();
/*  76:120 */     if ((this.visualizacion != null) && (this.visualizacion.getIdVisualizacion() != 0))
/*  77:    */     {
/*  78:121 */       setEditado(true);
/*  79:122 */       this.visualizacion = this.servicioVisualizacion.cargarDetalle(this.visualizacion.getIdVisualizacion());
/*  80:123 */       for (DetalleVisualizacion detalle : this.visualizacion.getListaDetalleVisualizacion())
/*  81:    */       {
/*  82:124 */         this.hmCategoria.put(detalle.getCategoriaProducto().getId() + "-" + (detalle.getSubcategoriaProducto() == null ? 0 : detalle.getSubcategoriaProducto().getId()), detalle);
/*  83:125 */         if (detalle.getSubcategoriaProducto() != null) {
/*  84:126 */           this.hmExiste.put(Integer.valueOf(detalle.getSubcategoriaProducto().getId()), Boolean.valueOf(true));
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  91:    */     }
/*  92:132 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String guardar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:143 */       this.servicioVisualizacion.guardar(this.visualizacion);
/* 100:144 */       limpiar();
/* 101:145 */       setEditado(false);
/* 102:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 103:    */     }
/* 104:    */     catch (Exception e)
/* 105:    */     {
/* 106:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 107:149 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 108:    */     }
/* 109:152 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String eliminar()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:163 */       this.servicioVisualizacion.eliminar(this.visualizacion);
/* 117:164 */       limpiar();
/* 118:165 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 123:168 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 124:    */     }
/* 125:171 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String cargarDatos()
/* 129:    */   {
/* 130:181 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void crearVisualizacion()
/* 134:    */   {
/* 135:185 */     setVisualizacion(new Visualizacion());
/* 136:186 */     getVisualizacion().setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 137:187 */     getVisualizacion().setIdSucursal(AppUtil.getSucursal().getId());
/* 138:188 */     getVisualizacion().setListaDetalleVisualizacion(new ArrayList());
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void cargarDetalleVisualizacion()
/* 142:    */   {
/* 143:198 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 144:199 */     DetalleVisualizacion detalleVisualizacion = new DetalleVisualizacion();
/* 145:200 */     detalleVisualizacion.setIdOrganizacion(idOrganizacion);
/* 146:201 */     detalleVisualizacion.setVisualizacion(getVisualizacion());
/* 147:202 */     detalleVisualizacion.setCategoriaProducto(getCategoriaProductoSeleccionada());
/* 148:203 */     detalleVisualizacion.setSubcategoriaProducto(getSubcategoriaProducto());
/* 149:204 */     String clave = getCategoriaProductoSeleccionada().getId() + "-" + (getSubcategoriaProducto() == null ? 0 : getSubcategoriaProducto().getId());
/* 150:206 */     if (this.hmCategoria.containsKey(clave)) {
/* 151:207 */       detalleVisualizacion.setEliminado(true);
/* 152:208 */     } else if (this.hmCategoria.containsKey(getCategoriaProductoSeleccionada().getId() + "-" + 0)) {
/* 153:209 */       ((DetalleVisualizacion)this.hmCategoria.get(getCategoriaProductoSeleccionada().getId() + "-" + 0)).setEliminado(true);
/* 154:210 */     } else if (this.hmExiste.containsKey(Integer.valueOf(getCategoriaProductoSeleccionada().getId()))) {
/* 155:211 */       detalleVisualizacion.setEliminado(true);
/* 156:    */     }
/* 157:213 */     this.hmCategoria.put(clave, detalleVisualizacion);
/* 158:214 */     getVisualizacion().getListaDetalleVisualizacion().add(detalleVisualizacion);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void limpiarCombos()
/* 162:    */   {
/* 163:219 */     setCategoriaProductoSeleccionada(null);
/* 164:220 */     setSubcategoriaProducto(null);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String eliminarDetalle(DetalleVisualizacion visualizacionSubcategoriaProducto)
/* 168:    */   {
/* 169:224 */     DetalleVisualizacion vscp = (DetalleVisualizacion)this.dtDetalleVisualizacion.getRowData();
/* 170:225 */     vscp.setEliminado(true);
/* 171:226 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String limpiar()
/* 175:    */   {
/* 176:234 */     crearVisualizacion();
/* 177:235 */     this.hmCategoria.clear();
/* 178:236 */     this.hmExiste.clear();
/* 179:237 */     return "";
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<CategoriaProducto> autocompleatarCategoriaProducto(String consulta)
/* 183:    */   {
/* 184:246 */     HashMap<String, String> filters = new HashMap();
/* 185:247 */     filters.put("nombre", "%" + consulta.trim());
/* 186:248 */     filters.put("activo", "true");
/* 187:249 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 188:250 */     return this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 192:    */   {
/* 193:254 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 194:255 */     lista = this.servicioCategoriaProducto.obtenerListaSubcategoriaProducto(AppUtil.getOrganizacion().getIdOrganizacion(), 
/* 195:256 */       getCategoriaProductoSeleccionada(), consulta);
/* 196:257 */     return lista;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void onRowSelect(SelectEvent event)
/* 200:    */   {
/* 201:264 */     Visualizacion visualizacion = (Visualizacion)event.getObject();
/* 202:265 */     setVisualizacion(visualizacion);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public ServicioVisualizacion getServicioVisualizacion()
/* 206:    */   {
/* 207:269 */     return this.servicioVisualizacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setServicioVisualizacion(ServicioVisualizacion servicioVisualizacion)
/* 211:    */   {
/* 212:273 */     this.servicioVisualizacion = servicioVisualizacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Visualizacion getVisualizacion()
/* 216:    */   {
/* 217:277 */     return this.visualizacion;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setVisualizacion(Visualizacion visualizacion)
/* 221:    */   {
/* 222:281 */     this.visualizacion = visualizacion;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public DataTable getDataTableVisualizacion()
/* 226:    */   {
/* 227:285 */     return this.dataTableVisualizacion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setDataTableVisualizacion(DataTable dataTableVisualizacion)
/* 231:    */   {
/* 232:289 */     this.dataTableVisualizacion = dataTableVisualizacion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public LazyDataModel<Visualizacion> getListaVisualizacion()
/* 236:    */   {
/* 237:293 */     return this.listaVisualizacion;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setListaVisualizacion(LazyDataModel<Visualizacion> listaVisualizacion)
/* 241:    */   {
/* 242:297 */     this.listaVisualizacion = listaVisualizacion;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public DataTable getDtDetalleVisualizacion()
/* 246:    */   {
/* 247:304 */     return this.dtDetalleVisualizacion;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setDtDetalleVisualizacion(DataTable dtDetalleVisualizacion)
/* 251:    */   {
/* 252:311 */     this.dtDetalleVisualizacion = dtDetalleVisualizacion;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public CategoriaProducto getCategoriaProductoSeleccionada()
/* 256:    */   {
/* 257:318 */     return this.categoriaProductoSeleccionada;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setCategoriaProductoSeleccionada(CategoriaProducto categoriaProductoSeleccionada)
/* 261:    */   {
/* 262:325 */     this.categoriaProductoSeleccionada = categoriaProductoSeleccionada;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 266:    */   {
/* 267:329 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 271:    */   {
/* 272:333 */     return this.subcategoriaProducto;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 276:    */   {
/* 277:337 */     this.subcategoriaProducto = subcategoriaProducto;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public List<DetalleVisualizacion> getListaDetalleVisualizacion()
/* 281:    */   {
/* 282:341 */     List<DetalleVisualizacion> lista = new ArrayList();
/* 283:342 */     for (DetalleVisualizacion vscp : getVisualizacion().getListaDetalleVisualizacion()) {
/* 284:343 */       if (!vscp.isEliminado()) {
/* 285:344 */         lista.add(vscp);
/* 286:    */       }
/* 287:    */     }
/* 288:347 */     return lista;
/* 289:    */   }
/* 290:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.VisualizacionBean
 * JD-Core Version:    0.7.0.1
 */