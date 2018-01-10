/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.CentroCosto;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.TipoBodega;
/*  11:    */ import com.asinfo.as2.entities.Ubicacion;
/*  12:    */ import com.asinfo.as2.entities.UbicacionBodega;
/*  13:    */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoBodega;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.faces.model.SelectItem;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ import org.primefaces.component.datatable.DataTable;
/*  29:    */ import org.primefaces.event.SelectEvent;
/*  30:    */ import org.primefaces.model.LazyDataModel;
/*  31:    */ import org.primefaces.model.SortOrder;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class BodegaBean
/*  36:    */   extends PageControllerAS2
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @EJB
/*  40:    */   private ServicioBodega servicioBodega;
/*  41:    */   @EJB
/*  42:    */   private ServicioSucursal servicioSucursal;
/*  43:    */   @EJB
/*  44:    */   private ServicioTipoBodega servicioTipoBodega;
/*  45:    */   @EJB
/*  46:    */   private ServicioCentroCosto servicioCentroCosto;
/*  47:    */   private Bodega bodega;
/*  48:    */   private LazyDataModel<Bodega> listaBodega;
/*  49:    */   private List<TipoBodega> listaTipoBodega;
/*  50:    */   private List<Sucursal> listaSucursal;
/*  51:    */   private List<ClaseBodegaEnum> listaClaseBodega;
/*  52:    */   private List<CentroCosto> listaCentrosCostoNoAsignados;
/*  53:    */   private List<CentroCosto> listaCentroCosto;
/*  54:    */   private CentroCosto[] centrosCostoSeleccionados;
/*  55:    */   private DataTable dataTableBodega;
/*  56:    */   private List<SelectItem> listaBodegaItems;
/*  57:    */   
/*  58:    */   @PostConstruct
/*  59:    */   public void init()
/*  60:    */   {
/*  61: 94 */     this.listaBodega = new LazyDataModel()
/*  62:    */     {
/*  63:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  64:    */       
/*  65:    */       public List<Bodega> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  66:    */       {
/*  67:100 */         List<Bodega> lista = new ArrayList();
/*  68:101 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  69:    */         try
/*  70:    */         {
/*  71:103 */           lista = BodegaBean.this.servicioBodega.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  72:    */         }
/*  73:    */         catch (Exception e)
/*  74:    */         {
/*  75:106 */           e.printStackTrace();
/*  76:    */         }
/*  77:108 */         BodegaBean.this.listaBodega.setRowCount(BodegaBean.this.servicioBodega.contarPorCriterio(filters));
/*  78:109 */         return lista;
/*  79:    */       }
/*  80:    */     };
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String editar()
/*  84:    */   {
/*  85:122 */     if ((getBodega() != null) && (getBodega().getIdBodega() != 0))
/*  86:    */     {
/*  87:123 */       setEditado(true);
/*  88:124 */       this.bodega = this.servicioBodega.cargarDetalle(this.bodega);
/*  89:    */     }
/*  90:    */     else
/*  91:    */     {
/*  92:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  93:    */     }
/*  94:129 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String guardar()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:140 */       this.servicioBodega.guardar(this.bodega);
/* 102:    */       
/* 103:142 */       cargarDatos();
/* 104:    */       
/* 105:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 106:    */     }
/* 107:    */     catch (Exception e)
/* 108:    */     {
/* 109:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 110:147 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 111:    */     }
/* 112:149 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String eliminar()
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:160 */       this.servicioBodega.eliminar(this.bodega);
/* 120:161 */       cargarDatos();
/* 121:    */       
/* 122:163 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 123:    */     }
/* 124:    */     catch (Exception e)
/* 125:    */     {
/* 126:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 127:166 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 128:    */     }
/* 129:168 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String cargarDatos()
/* 133:    */   {
/* 134:179 */     setEditado(false);
/* 135:    */     try
/* 136:    */     {
/* 137:182 */       limpiar();
/* 138:    */     }
/* 139:    */     catch (Exception e)
/* 140:    */     {
/* 141:185 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 142:186 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 143:    */     }
/* 144:188 */     return "";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String limpiar()
/* 148:    */   {
/* 149:198 */     this.bodega = new Bodega();
/* 150:199 */     this.bodega.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 151:200 */     this.bodega.setTipoBodega(new TipoBodega());
/* 152:201 */     this.bodega.setUbicacion(new Ubicacion());
/* 153:202 */     return "";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String cargarItems()
/* 157:    */   {
/* 158:206 */     if (this.listaBodegaItems == null)
/* 159:    */     {
/* 160:207 */       this.listaBodegaItems = new ArrayList();
/* 161:    */       
/* 162:209 */       cargarDatos();
/* 163:211 */       for (Bodega bodega : getListaBodega())
/* 164:    */       {
/* 165:212 */         SelectItem opcion = new SelectItem(Integer.valueOf(bodega.getIdBodega()), bodega.getNombre());
/* 166:213 */         this.listaBodegaItems.add(opcion);
/* 167:    */       }
/* 168:    */     }
/* 169:216 */     return "";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<Bodega> autocompletarBodega(String consulta)
/* 173:    */   {
/* 174:226 */     List<Bodega> lista = new ArrayList();
/* 175:227 */     String sortField = "nombre";
/* 176:228 */     HashMap<String, String> filters = new HashMap();
/* 177:229 */     filters.put("nombreOCodigo", consulta.trim() + "%");
/* 178:230 */     lista = this.servicioBodega.obtenerListaCombo(sortField, true, filters);
/* 179:    */     
/* 180:232 */     return lista;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<Bodega> getListaCombo()
/* 184:    */   {
/* 185:242 */     String sortField = "nombre";
/* 186:    */     
/* 187:244 */     HashMap<String, String> filters = new HashMap();
/* 188:245 */     filters.put("activo", "true");
/* 189:246 */     return this.servicioBodega.obtenerListaCombo(sortField, true, null);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void onRowSelect(SelectEvent event)
/* 193:    */   {
/* 194:255 */     Bodega b = (Bodega)event.getObject();
/* 195:256 */     setBodega(b);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String agregarUbicacion()
/* 199:    */   {
/* 200:266 */     UbicacionBodega ub = new UbicacionBodega();
/* 201:267 */     ub.setBodega(this.bodega);
/* 202:268 */     getUbicacionesBodega().add(ub);
/* 203:269 */     return "";
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String cargarCentrosCostoNoAsignados()
/* 207:    */   {
/* 208:278 */     this.centrosCostoSeleccionados = null;
/* 209:279 */     HashMap<Integer, CentroCosto> mapaCentroCosto = new HashMap();
/* 210:280 */     for (CentroCosto centroCosto : getListaCentroCosto()) {
/* 211:281 */       mapaCentroCosto.put(Integer.valueOf(centroCosto.getId()), centroCosto);
/* 212:    */     }
/* 213:290 */     this.listaCentrosCostoNoAsignados = new ArrayList(mapaCentroCosto.values());
/* 214:    */     
/* 215:292 */     return "";
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<CentroCosto> getListaCentroCosto()
/* 219:    */   {
/* 220:301 */     if (this.listaCentroCosto == null) {
/* 221:302 */       this.listaCentroCosto = this.servicioCentroCosto.obtenerListaCombo("nombre", true, null);
/* 222:    */     }
/* 223:304 */     return this.listaCentroCosto;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Bodega getBodega()
/* 227:    */   {
/* 228:313 */     return this.bodega;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setBodega(Bodega bodega)
/* 232:    */   {
/* 233:323 */     this.bodega = bodega;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public DataTable getDataTableBodega()
/* 237:    */   {
/* 238:332 */     return this.dataTableBodega;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setDataTableBodega(DataTable dataTableBodega)
/* 242:    */   {
/* 243:342 */     this.dataTableBodega = dataTableBodega;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<UbicacionBodega> getUbicacionesBodega()
/* 247:    */   {
/* 248:351 */     return this.bodega.getUbicacionesBodega();
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setUbicacionesBodega(List<UbicacionBodega> ubicacionesBodega)
/* 252:    */   {
/* 253:361 */     this.bodega.setUbicacionesBodega(ubicacionesBodega);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public LazyDataModel<Bodega> getListaBodega()
/* 257:    */   {
/* 258:370 */     if (this.listaBodega == null) {
/* 259:371 */       cargarDatos();
/* 260:    */     }
/* 261:373 */     return this.listaBodega;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setListaBodega(LazyDataModel<Bodega> listaBodega)
/* 265:    */   {
/* 266:383 */     this.listaBodega = listaBodega;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public List<SelectItem> getListaBodegaItems()
/* 270:    */   {
/* 271:392 */     cargarItems();
/* 272:    */     
/* 273:394 */     return this.listaBodegaItems;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setListaBodegaItems(List<SelectItem> listaBodegaItems)
/* 277:    */   {
/* 278:404 */     this.listaBodegaItems = listaBodegaItems;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public List<TipoBodega> getListaTipoBodega()
/* 282:    */   {
/* 283:413 */     if (this.listaTipoBodega == null) {
/* 284:414 */       this.listaTipoBodega = this.servicioTipoBodega.obtenerListaCombo("nombre", true, null);
/* 285:    */     }
/* 286:416 */     return this.listaTipoBodega;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setListaTipoBodega(List<TipoBodega> listaTipoBodega)
/* 290:    */   {
/* 291:426 */     this.listaTipoBodega = listaTipoBodega;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public List<ClaseBodegaEnum> getListaClaseBodega()
/* 295:    */   {
/* 296:435 */     if (this.listaClaseBodega == null)
/* 297:    */     {
/* 298:436 */       this.listaClaseBodega = new ArrayList();
/* 299:437 */       for (ClaseBodegaEnum claseBodegaEnum : ClaseBodegaEnum.values()) {
/* 300:438 */         this.listaClaseBodega.add(claseBodegaEnum);
/* 301:    */       }
/* 302:    */     }
/* 303:441 */     return this.listaClaseBodega;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setListaTipoBodegaEnum(List<ClaseBodegaEnum> listaClaseBodega)
/* 307:    */   {
/* 308:451 */     this.listaClaseBodega = listaClaseBodega;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public CentroCosto[] getCentrosCostoSeleccionados()
/* 312:    */   {
/* 313:460 */     return this.centrosCostoSeleccionados;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setCentrosCostoSeleccionados(CentroCosto[] centrosCostoSeleccionados)
/* 317:    */   {
/* 318:470 */     this.centrosCostoSeleccionados = centrosCostoSeleccionados;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public List<CentroCosto> getListaCentrosCostoNoAsignados()
/* 322:    */   {
/* 323:479 */     return this.listaCentrosCostoNoAsignados;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public List<Sucursal> getListaSucursal()
/* 327:    */   {
/* 328:488 */     if (this.listaSucursal == null) {
/* 329:489 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 330:    */     }
/* 331:491 */     return this.listaSucursal;
/* 332:    */   }
/* 333:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.BodegaBean
 * JD-Core Version:    0.7.0.1
 */