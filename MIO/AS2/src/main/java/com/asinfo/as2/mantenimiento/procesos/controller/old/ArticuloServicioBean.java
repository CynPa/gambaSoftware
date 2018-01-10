/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.old.CategoriaArticuloServicio;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.old.GrupoArticuloServicio;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.old.HistoricoArticuloServicio;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.old.TipoCicloOperacion;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoArticuloServicioEnum;
/*  13:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioCategoriaArticuloServicio;
/*  14:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioGrupoArticuloServicio;
/*  15:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoCicloOperacion;
/*  16:    */ import com.asinfo.as2.mantenimiento.procesos.old.ServicioArticuloServicio;
/*  17:    */ import com.asinfo.as2.mantenimiento.procesos.old.ServicioHistoricoArticuloServicio;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.faces.model.SelectItem;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ import org.primefaces.component.datatable.DataTable;
/*  29:    */ import org.primefaces.model.DefaultTreeNode;
/*  30:    */ import org.primefaces.model.LazyDataModel;
/*  31:    */ import org.primefaces.model.SortOrder;
/*  32:    */ import org.primefaces.model.TreeNode;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ArticuloServicioBean
/*  37:    */   extends PageControllerAS2
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  40:    */   @EJB
/*  41:    */   private ServicioArticuloServicio servicioArticuloServicio;
/*  42:    */   @EJB
/*  43:    */   private ServicioGrupoArticuloServicio servicioGrupoArticuloServicio;
/*  44:    */   @EJB
/*  45:    */   private ServicioTipoCicloOperacion servicioTipoCicloOperacion;
/*  46:    */   @EJB
/*  47:    */   private ServicioHistoricoArticuloServicio servicioHistoricoArticuloServicio;
/*  48:    */   @EJB
/*  49:    */   private ServicioCategoriaArticuloServicio servicioCategoriaArticuloServicio;
/*  50:    */   private ArticuloServicio articuloServicio;
/*  51:    */   private HistoricoArticuloServicio historicoArticuloServicio;
/*  52:    */   private TreeNode nodoPrincipal;
/*  53:    */   private TreeNode nodoSeleccionado;
/*  54:    */   private List<GrupoArticuloServicio> listaGrupoArticuloServicio;
/*  55:    */   private List<TipoCicloOperacion> listaTipoCicloOperacion;
/*  56:    */   private List<ArticuloServicio> listaArticuloServicioDisponible;
/*  57:    */   private List<SelectItem> listaTipoArticuloServicio;
/*  58:    */   private List<CategoriaArticuloServicio> listaCategoriaArticuloServicio;
/*  59:    */   private LazyDataModel<ArticuloServicio> listaArticuloServicio;
/*  60:    */   private DataTable dtArticuloServicio;
/*  61:    */   private DataTable dtHistoricoArticulo;
/*  62:    */   private DataTable dtArticuloDisponible;
/*  63:    */   
/*  64:    */   @PostConstruct
/*  65:    */   public void init()
/*  66:    */   {
/*  67:106 */     this.historicoArticuloServicio = new HistoricoArticuloServicio();
/*  68:107 */     this.historicoArticuloServicio.setArticuloServicioHijo(new ArticuloServicio());
/*  69:108 */     this.listaArticuloServicio = new LazyDataModel()
/*  70:    */     {
/*  71:    */       private static final long serialVersionUID = 1L;
/*  72:    */       
/*  73:    */       public List<ArticuloServicio> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  74:    */       {
/*  75:114 */         List<ArticuloServicio> lista = new ArrayList();
/*  76:115 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  77:    */         
/*  78:117 */         filters.put("tipoArticuloServicio", String.valueOf(TipoArticuloServicioEnum.ESTRUCTURA));
/*  79:    */         
/*  80:119 */         lista = ArticuloServicioBean.this.servicioArticuloServicio.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  81:    */         
/*  82:121 */         ArticuloServicioBean.this.listaArticuloServicio.setRowCount(ArticuloServicioBean.this.servicioArticuloServicio.contarPorCriterio(filters));
/*  83:    */         
/*  84:123 */         return lista;
/*  85:    */       }
/*  86:    */     };
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void crearArticuloServicio()
/*  90:    */   {
/*  91:141 */     this.articuloServicio = new ArticuloServicio();
/*  92:142 */     this.articuloServicio.setTipoArticuloServicio(TipoArticuloServicioEnum.ESTRUCTURA);
/*  93:143 */     this.articuloServicio.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  94:144 */     this.articuloServicio.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String editar()
/*  98:    */   {
/*  99:153 */     if (getArticuloServicio().getIdArticuloServicio() > 0)
/* 100:    */     {
/* 101:154 */       this.articuloServicio = this.servicioArticuloServicio.cargarDetalle(this.articuloServicio.getIdArticuloServicio());
/* 102:155 */       setEditado(true);
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 107:    */     }
/* 108:159 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String guardar()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:169 */       this.servicioArticuloServicio.guardar(this.articuloServicio);
/* 116:170 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 117:171 */       limpiar();
/* 118:172 */       setEditado(false);
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:174 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 123:175 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 124:    */     }
/* 125:177 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String eliminar()
/* 129:    */   {
/* 130:    */     try
/* 131:    */     {
/* 132:187 */       this.servicioArticuloServicio.eliminar(this.articuloServicio);
/* 133:188 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 134:    */     }
/* 135:    */     catch (Exception e)
/* 136:    */     {
/* 137:190 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 138:191 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 139:    */     }
/* 140:193 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String limpiar()
/* 144:    */   {
/* 145:202 */     crearArticuloServicio();
/* 146:203 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void seleccionarHistoricoArticulo()
/* 150:    */   {
/* 151:    */     try
/* 152:    */     {
/* 153:208 */       this.historicoArticuloServicio = ((HistoricoArticuloServicio)this.nodoSeleccionado.getData());
/* 154:209 */       this.historicoArticuloServicio = this.servicioHistoricoArticuloServicio.cargarDetalle(this.historicoArticuloServicio.getIdHistoricoServicio());
/* 155:    */     }
/* 156:    */     catch (Exception e)
/* 157:    */     {
/* 158:211 */       this.historicoArticuloServicio = null;
/* 159:212 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String guardarHistoricoArticulo()
/* 164:    */   {
/* 165:    */     try
/* 166:    */     {
/* 167:218 */       this.servicioHistoricoArticuloServicio.guardar(this.historicoArticuloServicio);
/* 168:219 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 169:220 */       this.historicoArticuloServicio = new HistoricoArticuloServicio();
/* 170:221 */       cargarArbol();
/* 171:    */     }
/* 172:    */     catch (Exception e)
/* 173:    */     {
/* 174:224 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 175:225 */       e.printStackTrace();
/* 176:226 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 177:    */     }
/* 178:228 */     return "";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void crearArticulo()
/* 182:    */   {
/* 183:232 */     this.articuloServicio = new ArticuloServicio();
/* 184:233 */     this.articuloServicio.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 185:234 */     this.articuloServicio.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String agregarArticulo()
/* 189:    */   {
/* 190:243 */     seleccionarHistoricoArticulo();
/* 191:244 */     HistoricoArticuloServicio h = this.historicoArticuloServicio;
/* 192:245 */     this.historicoArticuloServicio = new HistoricoArticuloServicio();
/* 193:246 */     ArticuloServicio articuloServicio = new ArticuloServicio();
/* 194:247 */     this.articuloServicio.getListaHistoricoArticuloServicioHijo().add(this.historicoArticuloServicio);
/* 195:248 */     this.historicoArticuloServicio.setArticuloServicioHijo(articuloServicio);
/* 196:249 */     if (h == null) {
/* 197:250 */       this.historicoArticuloServicio.setArticuloServicioPadre(this.articuloServicio);
/* 198:    */     } else {
/* 199:252 */       this.historicoArticuloServicio.setArticuloServicioPadre(h.getArticuloServicioHijo());
/* 200:    */     }
/* 201:254 */     return "";
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void cargarArbol()
/* 205:    */   {
/* 206:258 */     HistoricoArticuloServicio historicoArticuloServicio = new HistoricoArticuloServicio();
/* 207:259 */     historicoArticuloServicio.setArticuloServicioHijo(this.articuloServicio);
/* 208:260 */     this.nodoPrincipal = new DefaultTreeNode(historicoArticuloServicio, null);
/* 209:261 */     obtenerHijos(this.nodoPrincipal);
/* 210:    */   }
/* 211:    */   
/* 212:    */   private void obtenerHijos(TreeNode nodo)
/* 213:    */   {
/* 214:266 */     HistoricoArticuloServicio historicoArticuloServicio = (HistoricoArticuloServicio)nodo.getData();
/* 215:267 */     if (historicoArticuloServicio.getArticuloServicioHijo() != null)
/* 216:    */     {
/* 217:268 */       ArticuloServicio articuloServicio = this.servicioArticuloServicio.cargarDetalle(historicoArticuloServicio.getArticuloServicioHijo()
/* 218:269 */         .getIdArticuloServicio());
/* 219:270 */       for (HistoricoArticuloServicio historicoArticuloServicioAux : articuloServicio.getListaHistoricoArticuloServicioHijo())
/* 220:    */       {
/* 221:271 */         TreeNode nodoNuevo = new DefaultTreeNode(historicoArticuloServicioAux, nodo);
/* 222:272 */         obtenerHijos(nodoNuevo);
/* 223:    */       }
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public LazyDataModel<ArticuloServicio> getListaArticuloServicio()
/* 228:    */   {
/* 229:287 */     return this.listaArticuloServicio;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setListaArticuloServicio(LazyDataModel<ArticuloServicio> listaArticuloServicio)
/* 233:    */   {
/* 234:297 */     this.listaArticuloServicio = listaArticuloServicio;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public DataTable getDtArticuloServicio()
/* 238:    */   {
/* 239:306 */     return this.dtArticuloServicio;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setDtArticuloServicio(DataTable dtArticuloServicio)
/* 243:    */   {
/* 244:316 */     this.dtArticuloServicio = dtArticuloServicio;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<GrupoArticuloServicio> getListaGrupoArticuloServicio()
/* 248:    */   {
/* 249:325 */     if (this.listaGrupoArticuloServicio == null) {
/* 250:326 */       this.listaGrupoArticuloServicio = this.servicioGrupoArticuloServicio.obtenerListaCombo("nombre", true, null);
/* 251:    */     }
/* 252:328 */     return this.listaGrupoArticuloServicio;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setListaGrupoArticuloServicio(List<GrupoArticuloServicio> listaGrupoArticuloServicio)
/* 256:    */   {
/* 257:338 */     this.listaGrupoArticuloServicio = listaGrupoArticuloServicio;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public List<TipoCicloOperacion> getListaTipoCicloOperacion()
/* 261:    */   {
/* 262:347 */     if (this.listaTipoCicloOperacion == null) {
/* 263:348 */       this.listaTipoCicloOperacion = this.servicioTipoCicloOperacion.obtenerListaCombo("nombre", true, null);
/* 264:    */     }
/* 265:350 */     return this.listaTipoCicloOperacion;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setListaTipoCicloOperacion(List<TipoCicloOperacion> listaTipoCicloOperacion)
/* 269:    */   {
/* 270:360 */     this.listaTipoCicloOperacion = listaTipoCicloOperacion;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<SelectItem> getListaTipoArticuloServicio()
/* 274:    */   {
/* 275:369 */     if (this.listaTipoArticuloServicio == null)
/* 276:    */     {
/* 277:370 */       this.listaTipoArticuloServicio = new ArrayList();
/* 278:372 */       for (TipoArticuloServicioEnum t : TipoArticuloServicioEnum.values()) {
/* 279:373 */         if (t.compareTo(TipoArticuloServicioEnum.ESTRUCTURA) != 0)
/* 280:    */         {
/* 281:374 */           SelectItem item = new SelectItem(t, t.getNombre());
/* 282:375 */           this.listaTipoArticuloServicio.add(item);
/* 283:    */         }
/* 284:    */       }
/* 285:    */     }
/* 286:379 */     return this.listaTipoArticuloServicio;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setListaTipoArticuloServicio(List<SelectItem> listaTipoArticuloServicio)
/* 290:    */   {
/* 291:389 */     this.listaTipoArticuloServicio = listaTipoArticuloServicio;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public String cargarDatos()
/* 295:    */   {
/* 296:399 */     return null;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<ArticuloServicio> getListaArticuloServicioDisponible()
/* 300:    */   {
/* 301:408 */     this.listaArticuloServicioDisponible = this.servicioArticuloServicio.obtenerArticulosDisponibles();
/* 302:409 */     return this.listaArticuloServicioDisponible;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setListaArticuloServicioDisponible(List<ArticuloServicio> listaArticuloServicioDisponible)
/* 306:    */   {
/* 307:419 */     this.listaArticuloServicioDisponible = listaArticuloServicioDisponible;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public DataTable getDtHistoricoArticulo()
/* 311:    */   {
/* 312:428 */     return this.dtHistoricoArticulo;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setDtHistoricoArticulo(DataTable dtHistoricoArticulo)
/* 316:    */   {
/* 317:438 */     this.dtHistoricoArticulo = dtHistoricoArticulo;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public DataTable getDtArticuloDisponible()
/* 321:    */   {
/* 322:447 */     return this.dtArticuloDisponible;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setDtArticuloDisponible(DataTable dtArticuloDisponible)
/* 326:    */   {
/* 327:457 */     this.dtArticuloDisponible = dtArticuloDisponible;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public TreeNode getNodoPrincipal()
/* 331:    */   {
/* 332:466 */     if (this.articuloServicio == null) {
/* 333:467 */       this.nodoPrincipal = new DefaultTreeNode();
/* 334:469 */     } else if ((this.articuloServicio.getIdArticuloServicio() != 0) && (this.nodoPrincipal.getChildCount() == 0)) {
/* 335:470 */       cargarArbol();
/* 336:    */     }
/* 337:474 */     return this.nodoPrincipal;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setNodoPrincipal(TreeNode nodoPrincipal)
/* 341:    */   {
/* 342:484 */     this.nodoPrincipal = nodoPrincipal;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public TreeNode getNodoSeleccionado()
/* 346:    */   {
/* 347:493 */     return this.nodoSeleccionado;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setNodoSeleccionado(TreeNode nodoSeleccionado)
/* 351:    */   {
/* 352:503 */     this.nodoSeleccionado = nodoSeleccionado;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public ArticuloServicio getArticuloServicio()
/* 356:    */   {
/* 357:512 */     return this.articuloServicio;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setArticuloServicio(ArticuloServicio articuloServicio)
/* 361:    */   {
/* 362:522 */     this.articuloServicio = articuloServicio;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public HistoricoArticuloServicio getHistoricoArticuloServicio()
/* 366:    */   {
/* 367:531 */     return this.historicoArticuloServicio;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setHistoricoArticuloServicio(HistoricoArticuloServicio historicoArticuloServicio)
/* 371:    */   {
/* 372:541 */     this.historicoArticuloServicio = historicoArticuloServicio;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public List<CategoriaArticuloServicio> getListaCategoriaArticuloServicio()
/* 376:    */   {
/* 377:550 */     if (this.listaCategoriaArticuloServicio == null) {
/* 378:551 */       this.listaCategoriaArticuloServicio = this.servicioCategoriaArticuloServicio.obtenerListaCombo("nombre", true, null);
/* 379:    */     }
/* 380:553 */     return this.listaCategoriaArticuloServicio;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setListaCategoriaArticuloServicio(List<CategoriaArticuloServicio> listaCategoriaArticuloServicio)
/* 384:    */   {
/* 385:563 */     this.listaCategoriaArticuloServicio = listaCategoriaArticuloServicio;
/* 386:    */   }
/* 387:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.controller.old.ArticuloServicioBean
 * JD-Core Version:    0.7.0.1
 */