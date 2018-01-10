/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Ciudad;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Pais;
/*   9:    */ import com.asinfo.as2.entities.Provincia;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import javax.faces.model.SelectItem;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.component.datatable.DataTable;
/*  24:    */ import org.primefaces.event.map.PointSelectEvent;
/*  25:    */ import org.primefaces.model.DefaultTreeNode;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ import org.primefaces.model.TreeNode;
/*  29:    */ import org.primefaces.model.map.DefaultMapModel;
/*  30:    */ import org.primefaces.model.map.LatLng;
/*  31:    */ import org.primefaces.model.map.MapModel;
/*  32:    */ import org.primefaces.model.map.Marker;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class PaisBean
/*  37:    */   extends PageControllerAS2
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -894352093078369552L;
/*  40:    */   @EJB
/*  41:    */   private ServicioPais servicioPais;
/*  42:    */   private Pais pais;
/*  43:    */   private LazyDataModel<Pais> listaPais;
/*  44:    */   private List<SelectItem> paisItems;
/*  45:    */   private DataTable dtPais;
/*  46:    */   private DataTable dtProvincia;
/*  47:    */   private DataTable dtCiudad;
/*  48:    */   private MapModel geoModel;
/*  49:    */   private Provincia lineaProvincia;
/*  50: 71 */   private int zoomMap = 13;
/*  51:    */   private String centerGeoMap;
/*  52:    */   private TreeNode root;
/*  53:    */   
/*  54:    */   @PostConstruct
/*  55:    */   public void init()
/*  56:    */   {
/*  57: 78 */     this.listaPais = new LazyDataModel()
/*  58:    */     {
/*  59:    */       private static final long serialVersionUID = 1L;
/*  60:    */       
/*  61:    */       public List<Pais> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  62:    */       {
/*  63: 85 */         List<Pais> lista = new ArrayList();
/*  64: 86 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  65: 87 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  66: 88 */         lista = PaisBean.this.servicioPais.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  67: 89 */         PaisBean.this.listaPais.setRowCount(PaisBean.this.servicioPais.contarPorCriterio(filters));
/*  68:    */         
/*  69: 91 */         return lista;
/*  70:    */       }
/*  71:    */     };
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String guardar()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:106 */       this.servicioPais.guardar(this.pais);
/*  79:107 */       limpiar();
/*  80:108 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  81:109 */       setEditado(false);
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:112 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86:113 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:115 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:127 */       this.servicioPais.eliminar(this.pais);
/*  96:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 101:133 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 102:    */     }
/* 103:136 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cargarDatos()
/* 107:    */   {
/* 108:146 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void cargarDatosPais()
/* 112:    */   {
/* 113:153 */     List<Pais> paises = new ArrayList();
/* 114:154 */     paises = this.servicioPais.obtenerListaCombo("nombre", true, null);
/* 115:155 */     this.paisItems = new ArrayList();
/* 116:157 */     for (Pais paisX : paises)
/* 117:    */     {
/* 118:158 */       int value = paisX.getIdPais();
/* 119:159 */       String label = paisX.getNombre();
/* 120:160 */       SelectItem opcion = new SelectItem(Integer.valueOf(value), label);
/* 121:161 */       this.paisItems.add(opcion);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void crearPais()
/* 126:    */   {
/* 127:171 */     this.pais = new Pais();
/* 128:172 */     this.pais.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 129:173 */     this.pais.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String editar()
/* 133:    */   {
/* 134:184 */     if ((getPais() != null) && (getPais().getId() != 0))
/* 135:    */     {
/* 136:185 */       this.pais = this.servicioPais.cargarDetalle(this.pais.getId());
/* 137:186 */       setEditado(true);
/* 138:    */     }
/* 139:    */     else
/* 140:    */     {
/* 141:188 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 142:    */     }
/* 143:190 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String limpiar()
/* 147:    */   {
/* 148:200 */     crearPais();
/* 149:201 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String agregarDetalle()
/* 153:    */   {
/* 154:210 */     Provincia provincia = new Provincia();
/* 155:211 */     provincia.setPais(getPais());
/* 156:212 */     provincia.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 157:213 */     provincia.setIdSucursal(AppUtil.getSucursal().getId());
/* 158:214 */     getPais().getListaProvincia().add(provincia);
/* 159:    */     
/* 160:216 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String eliminarDetalle()
/* 164:    */   {
/* 165:225 */     Provincia provincia = (Provincia)this.dtProvincia.getRowData();
/* 166:226 */     provincia.setEliminado(true);
/* 167:227 */     return "";
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String atarProvinciaCiudad()
/* 171:    */   {
/* 172:236 */     this.lineaProvincia = ((Provincia)this.dtProvincia.getRowData());
/* 173:237 */     return "";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String agregarCiudad()
/* 177:    */   {
/* 178:246 */     Ciudad ciudad = new Ciudad();
/* 179:247 */     ciudad.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 180:248 */     ciudad.setIdSucursal(AppUtil.getSucursal().getId());
/* 181:249 */     ciudad.setProvincia(this.lineaProvincia);
/* 182:250 */     this.lineaProvincia.getListaCiudad().add(ciudad);
/* 183:251 */     return "";
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String eliminarCiudad()
/* 187:    */   {
/* 188:260 */     Ciudad ciudad = (Ciudad)this.dtCiudad.getRowData();
/* 189:261 */     ciudad.setEliminado(true);
/* 190:262 */     return "";
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void actualizarMapa()
/* 194:    */   {
/* 195:266 */     atarProvinciaCiudad();
/* 196:267 */     if (this.lineaProvincia.getLongitud() != null)
/* 197:    */     {
/* 198:268 */       LatLng latlng = new LatLng(this.lineaProvincia.getLatitud().doubleValue(), this.lineaProvincia.getLongitud().doubleValue());
/* 199:269 */       Marker marker = new Marker(latlng, "");
/* 200:270 */       getGeoModel().addOverlay(marker);
/* 201:271 */       actualizarGeoModel(latlng);
/* 202:272 */       setZoomMap(16);
/* 203:    */     }
/* 204:    */     else
/* 205:    */     {
/* 206:274 */       setZoomMap(13);
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   private void actualizarGeoModel(LatLng latLng)
/* 211:    */   {
/* 212:279 */     for (Marker m : getGeoModel().getMarkers()) {
/* 213:280 */       if ((m.getLatlng().getLat() != latLng.getLat()) && (m.getLatlng().getLng() != latLng.getLng())) {
/* 214:281 */         m.setVisible(false);
/* 215:    */       }
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void agregarMarker(PointSelectEvent event)
/* 220:    */   {
/* 221:286 */     LatLng latlng = event.getLatLng();
/* 222:287 */     Marker marker = new Marker(latlng, "");
/* 223:288 */     getGeoModel().addOverlay(marker);
/* 224:289 */     actualizarGeoModel(latlng);
/* 225:290 */     this.lineaProvincia.setLatitud(FuncionesUtiles.redondearBigDecimal(new BigDecimal(latlng.getLat()), 6));
/* 226:291 */     this.lineaProvincia.setLongitud(FuncionesUtiles.redondearBigDecimal(new BigDecimal(latlng.getLng()), 6));
/* 227:292 */     setZoomMap(18);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Pais getPais()
/* 231:    */   {
/* 232:301 */     if (this.pais == null) {
/* 233:302 */       crearPais();
/* 234:    */     }
/* 235:304 */     return this.pais;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setPais(Pais pais)
/* 239:    */   {
/* 240:314 */     this.pais = pais;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public List<SelectItem> getPaisItems()
/* 244:    */   {
/* 245:323 */     return this.paisItems;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setPaisItems(List<SelectItem> paisItems)
/* 249:    */   {
/* 250:333 */     this.paisItems = paisItems;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public LazyDataModel<Pais> getListaPais()
/* 254:    */   {
/* 255:337 */     return this.listaPais;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setListaPais(LazyDataModel<Pais> listaPais)
/* 259:    */   {
/* 260:341 */     this.listaPais = listaPais;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public DataTable getDtPais()
/* 264:    */   {
/* 265:350 */     return this.dtPais;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setDtPais(DataTable dtPais)
/* 269:    */   {
/* 270:360 */     this.dtPais = dtPais;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<Provincia> getListaProvincia()
/* 274:    */   {
/* 275:369 */     List<Provincia> lista = new ArrayList();
/* 276:371 */     for (Provincia provincia : this.pais.getListaProvincia()) {
/* 277:372 */       if (!provincia.isEliminado()) {
/* 278:373 */         lista.add(provincia);
/* 279:    */       }
/* 280:    */     }
/* 281:377 */     return lista;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public List<Ciudad> getListaCiudad()
/* 285:    */   {
/* 286:386 */     List<Ciudad> lista = new ArrayList();
/* 287:387 */     if (this.lineaProvincia != null) {
/* 288:388 */       for (Ciudad ciudad : this.lineaProvincia.getListaCiudad()) {
/* 289:389 */         if (!ciudad.isEliminado()) {
/* 290:390 */           lista.add(ciudad);
/* 291:    */         }
/* 292:    */       }
/* 293:    */     }
/* 294:394 */     return lista;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public DataTable getDtProvincia()
/* 298:    */   {
/* 299:403 */     return this.dtProvincia;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setDtProvincia(DataTable dtProvincia)
/* 303:    */   {
/* 304:413 */     this.dtProvincia = dtProvincia;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public Provincia getLineaProvincia()
/* 308:    */   {
/* 309:422 */     if (this.lineaProvincia == null)
/* 310:    */     {
/* 311:423 */       this.lineaProvincia = new Provincia();
/* 312:424 */       this.lineaProvincia.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 313:425 */       this.lineaProvincia.setIdSucursal(AppUtil.getSucursal().getId());
/* 314:426 */       this.lineaProvincia.setListaCiudad(new ArrayList());
/* 315:    */     }
/* 316:428 */     return this.lineaProvincia;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setLineaProvincia(Provincia lineaProvincia)
/* 320:    */   {
/* 321:438 */     this.lineaProvincia = lineaProvincia;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public DataTable getDtCiudad()
/* 325:    */   {
/* 326:447 */     return this.dtCiudad;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setDtCiudad(DataTable dtCiudad)
/* 330:    */   {
/* 331:457 */     this.dtCiudad = dtCiudad;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public TreeNode getRoot()
/* 335:    */   {
/* 336:463 */     if (this.root == null)
/* 337:    */     {
/* 338:464 */       this.root = new DefaultTreeNode("root", null);
/* 339:465 */       List<Pais> paises = this.servicioPais.obtenerPaises();
/* 340:466 */       for (Pais pais : paises)
/* 341:    */       {
/* 342:467 */         treePais = new DefaultTreeNode(pais, this.root);
/* 343:468 */         for (Provincia provincia : pais.getListaProvincia())
/* 344:    */         {
/* 345:469 */           treeProvincia = new DefaultTreeNode(provincia, treePais);
/* 346:470 */           for (Ciudad ciudad : provincia.getListaCiudad()) {
/* 347:471 */             DefaultTreeNode localDefaultTreeNode = new DefaultTreeNode(ciudad, treeProvincia);
/* 348:    */           }
/* 349:    */         }
/* 350:    */       }
/* 351:    */     }
/* 352:    */     TreeNode treePais;
/* 353:    */     TreeNode treeProvincia;
/* 354:476 */     return this.root;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setRoot(TreeNode root)
/* 358:    */   {
/* 359:481 */     this.root = root;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public MapModel getGeoModel()
/* 363:    */   {
/* 364:485 */     if (this.geoModel == null) {
/* 365:486 */       this.geoModel = new DefaultMapModel();
/* 366:    */     }
/* 367:488 */     return this.geoModel;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setGeoModel(MapModel geoModel)
/* 371:    */   {
/* 372:492 */     this.geoModel = geoModel;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public int getZoomMap()
/* 376:    */   {
/* 377:496 */     return this.zoomMap;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setZoomMap(int zoomMap)
/* 381:    */   {
/* 382:500 */     this.zoomMap = zoomMap;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public String getCenterGeoMap()
/* 386:    */   {
/* 387:504 */     this.centerGeoMap = "-0.180300, -78.492223";
/* 388:505 */     if ((this.lineaProvincia != null) && (this.lineaProvincia.getLatitud() != null) && (this.lineaProvincia.getLongitud() != null))
/* 389:    */     {
/* 390:506 */       this.centerGeoMap = (this.lineaProvincia.getLatitud().toString() + ", " + this.lineaProvincia.getLongitud().toString());
/* 391:507 */       setZoomMap(16);
/* 392:    */     }
/* 393:509 */     return this.centerGeoMap;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setCenterGeoMap(String centerGeoMap)
/* 397:    */   {
/* 398:513 */     this.centerGeoMap = centerGeoMap;
/* 399:    */   }
/* 400:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.PaisBean
 * JD-Core Version:    0.7.0.1
 */