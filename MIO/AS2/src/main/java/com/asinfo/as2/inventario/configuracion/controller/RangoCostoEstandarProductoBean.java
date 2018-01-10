/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CostoEstandarProducto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.RangoCostoEstandarProducto;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioRangoCostoEstandarProducto;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.util.RutaArchivo;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import java.util.Vector;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import javax.faces.model.SelectItem;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.primefaces.component.datatable.DataTable;
/*  31:    */ import org.primefaces.event.FileUploadEvent;
/*  32:    */ import org.primefaces.model.LazyDataModel;
/*  33:    */ import org.primefaces.model.SortOrder;
/*  34:    */ import org.primefaces.model.StreamedContent;
/*  35:    */ import org.primefaces.model.UploadedFile;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ @ViewScoped
/*  39:    */ public class RangoCostoEstandarProductoBean
/*  40:    */   extends PageControllerAS2
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 1L;
/*  43:    */   @EJB
/*  44:    */   private ServicioRangoCostoEstandarProducto servicioRangoCostoEstandarProducto;
/*  45:    */   @EJB
/*  46:    */   private ServicioProducto servicioProducto;
/*  47:    */   private RangoCostoEstandarProducto rangoCostoEstandarProducto;
/*  48:    */   private HashMap<Integer, CostoEstandarProducto> hmCostoEstandarProducto;
/*  49:    */   private List<SelectItem> listaMes;
/*  50:    */   private StreamedContent file;
/*  51:    */   private LazyDataModel<RangoCostoEstandarProducto> listaRangoCostoEstandarProducto;
/*  52:    */   private DataTable dtRangoCostoEstandarProducto;
/*  53:    */   private DataTable dtCostoEstandarProducto;
/*  54:    */   
/*  55:    */   @PostConstruct
/*  56:    */   public void init()
/*  57:    */   {
/*  58: 91 */     this.listaRangoCostoEstandarProducto = new LazyDataModel()
/*  59:    */     {
/*  60:    */       private static final long serialVersionUID = 1L;
/*  61:    */       
/*  62:    */       public List<RangoCostoEstandarProducto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  63:    */       {
/*  64: 99 */         List<RangoCostoEstandarProducto> lista = new ArrayList();
/*  65:    */         
/*  66:101 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  67:    */         
/*  68:103 */         lista = RangoCostoEstandarProductoBean.this.servicioRangoCostoEstandarProducto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  69:104 */         RangoCostoEstandarProductoBean.this.listaRangoCostoEstandarProducto.setRowCount(RangoCostoEstandarProductoBean.this.servicioRangoCostoEstandarProducto.contarPorCriterio(filters));
/*  70:    */         
/*  71:106 */         return lista;
/*  72:    */       }
/*  73:    */     };
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String crearRangoCostoEstandarProducto()
/*  77:    */   {
/*  78:118 */     this.rangoCostoEstandarProducto = new RangoCostoEstandarProducto();
/*  79:119 */     this.rangoCostoEstandarProducto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  80:120 */     this.rangoCostoEstandarProducto.setIdSucursal(AppUtil.getSucursal().getId());
/*  81:121 */     this.rangoCostoEstandarProducto.setAnio(FuncionesUtiles.getAnio(new Date()));
/*  82:122 */     this.rangoCostoEstandarProducto.setMes(FuncionesUtiles.getMes(new Date()));
/*  83:123 */     this.rangoCostoEstandarProducto.setActivo(true);
/*  84:124 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String editar()
/*  88:    */   {
/*  89:134 */     if (getRangoCostoEstandarProducto().getId() > 0) {
/*  90:    */       try
/*  91:    */       {
/*  92:137 */         this.rangoCostoEstandarProducto = this.servicioRangoCostoEstandarProducto.cargarDetalle(getRangoCostoEstandarProducto().getId());
/*  93:138 */         cargarHmProductos();
/*  94:139 */         setEditado(true);
/*  95:    */       }
/*  96:    */       catch (Exception e)
/*  97:    */       {
/*  98:142 */         addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/*  99:143 */         LOG.error("ERROR AL EDITAR DATOS", e);
/* 100:    */       }
/* 101:    */     } else {
/* 102:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 103:    */     }
/* 104:149 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String guardar()
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:161 */       this.servicioRangoCostoEstandarProducto.guardar(this.rangoCostoEstandarProducto);
/* 112:162 */       setEditado(false);
/* 113:163 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 114:164 */       limpiar();
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 119:168 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 120:    */     }
/* 121:170 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String eliminar()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:182 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 129:183 */       cargarDatos();
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:186 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 134:187 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 135:    */     }
/* 136:189 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String limpiar()
/* 140:    */   {
/* 141:199 */     crearRangoCostoEstandarProducto();
/* 142:200 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String cargarDatos()
/* 146:    */   {
/* 147:210 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void cargarProducto(Producto producto)
/* 151:    */   {
/* 152:219 */     if ((!getHmCostoEstandarProducto().containsKey(Integer.valueOf(producto.getId()))) && (producto.getTipoProducto() == TipoProducto.ARTICULO))
/* 153:    */     {
/* 154:221 */       CostoEstandarProducto costoEstandarProducto = new CostoEstandarProducto();
/* 155:222 */       costoEstandarProducto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 156:223 */       costoEstandarProducto.setIdSucursal(AppUtil.getSucursal().getId());
/* 157:224 */       costoEstandarProducto.setRangoCostoEstandarProducto(getRangoCostoEstandarProducto());
/* 158:225 */       costoEstandarProducto.setProducto(producto);
/* 159:226 */       getRangoCostoEstandarProducto().getListaCostoEstandarProducto().add(costoEstandarProducto);
/* 160:227 */       getHmCostoEstandarProducto().put(Integer.valueOf(producto.getId()), costoEstandarProducto);
/* 161:    */     }
/* 162:    */     else
/* 163:    */     {
/* 164:230 */       addErrorMessage(getLanguageController().getMensaje("msg_info_ya_existe_producto"));
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void eliminarCostoEstandarProducto(CostoEstandarProducto costoEstandarProducto)
/* 169:    */   {
/* 170:240 */     costoEstandarProducto.setEliminado(true);
/* 171:241 */     getHmCostoEstandarProducto().remove(Integer.valueOf(costoEstandarProducto.getProducto().getId()));
/* 172:    */   }
/* 173:    */   
/* 174:    */   private void cargarHmProductos()
/* 175:    */   {
/* 176:248 */     this.hmCostoEstandarProducto = new HashMap();
/* 177:249 */     for (CostoEstandarProducto costoEstandarProducto : getRangoCostoEstandarProducto().getListaCostoEstandarProducto()) {
/* 178:250 */       this.hmCostoEstandarProducto.put(Integer.valueOf(costoEstandarProducto.getProducto().getId()), costoEstandarProducto);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String copiar()
/* 183:    */   {
/* 184:260 */     if (getRangoCostoEstandarProducto().getId() > 0)
/* 185:    */     {
/* 186:262 */       this.rangoCostoEstandarProducto = this.servicioRangoCostoEstandarProducto.cargarDetalle(getRangoCostoEstandarProducto().getId());
/* 187:263 */       this.rangoCostoEstandarProducto.setIdRangoCostoEstandarProducto(0);
/* 188:264 */       this.rangoCostoEstandarProducto.setAnio(FuncionesUtiles.getAnio(new Date()));
/* 189:265 */       this.rangoCostoEstandarProducto.setMes(FuncionesUtiles.getMes(new Date()));
/* 190:266 */       for (CostoEstandarProducto costoEstandarProducto : getListaCostoEstandarProducto()) {
/* 191:267 */         costoEstandarProducto.setIdCostoEstandarProducto(0);
/* 192:    */       }
/* 193:269 */       setEditado(true);
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:272 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 198:    */     }
/* 199:275 */     return "";
/* 200:    */   }
/* 201:    */   
/* 202:    */   public StreamedContent getFile()
/* 203:    */   {
/* 204:    */     try
/* 205:    */     {
/* 206:284 */       Vector v = new Vector();
/* 207:    */       
/* 208:    */ 
/* 209:287 */       v.addElement("Codigo,Producto,Costo");
/* 210:    */       
/* 211:    */ 
/* 212:290 */       HashMap<String, String> filters = new HashMap();
/* 213:291 */       filters.put("tipoProducto", TipoProducto.ARTICULO.toString());
/* 214:292 */       filters.put("activo", "true");
/* 215:293 */       List<Producto> listaProductos = this.servicioProducto.obtenerListaCombo("codigo", true, filters);
/* 216:295 */       for (Producto producto : listaProductos)
/* 217:    */       {
/* 218:296 */         CostoEstandarProducto costoEstandarProducto = (CostoEstandarProducto)getHmCostoEstandarProducto().get(Integer.valueOf(producto.getId()));
/* 219:297 */         if (costoEstandarProducto == null) {
/* 220:298 */           v.addElement(producto.getCodigo() + " ," + producto.getNombre().replace(",", ";") + " ," + 0);
/* 221:    */         } else {
/* 222:300 */           v.addElement(producto.getCodigo() + " ," + producto.getNombre().replace(",", ";") + " ," + costoEstandarProducto.getCosto());
/* 223:    */         }
/* 224:    */       }
/* 225:305 */       String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "costoProductoEstandar");
/* 226:306 */       String nombreArchivo = "CostoProductoEstandar.xls";
/* 227:    */       
/* 228:308 */       FuncionesUtiles.crearExcel(v, "CostoProductoEstandar", rutaArchivo, nombreArchivo);
/* 229:309 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 230:    */     }
/* 231:    */     catch (Exception e)
/* 232:    */     {
/* 233:312 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 234:313 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 235:    */     }
/* 236:316 */     return this.file;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String cargaCostoEstadarProducto(FileUploadEvent event)
/* 240:    */   {
/* 241:    */     try
/* 242:    */     {
/* 243:327 */       this.servicioRangoCostoEstandarProducto.cargaCostoEstandarProducto(this.rangoCostoEstandarProducto, event.getFile().getInputstream(), 1);
/* 244:328 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 245:    */     }
/* 246:    */     catch (ExcepcionAS2 e)
/* 247:    */     {
/* 248:331 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 249:332 */       LOG.error(e);
/* 250:    */     }
/* 251:    */     catch (Exception e)
/* 252:    */     {
/* 253:334 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 254:335 */       LOG.error(e);
/* 255:    */     }
/* 256:337 */     return null;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<SelectItem> getListaMes()
/* 260:    */   {
/* 261:347 */     if (this.listaMes == null)
/* 262:    */     {
/* 263:348 */       this.listaMes = new ArrayList();
/* 264:349 */       for (Mes t : Mes.values())
/* 265:    */       {
/* 266:350 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 267:351 */         this.listaMes.add(item);
/* 268:    */       }
/* 269:    */     }
/* 270:354 */     return this.listaMes;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<CostoEstandarProducto> getListaCostoEstandarProducto()
/* 274:    */   {
/* 275:363 */     List<CostoEstandarProducto> lista = new ArrayList();
/* 276:364 */     for (CostoEstandarProducto costoEstandarProducto : getRangoCostoEstandarProducto().getListaCostoEstandarProducto()) {
/* 277:365 */       if (!costoEstandarProducto.isEliminado()) {
/* 278:366 */         lista.add(costoEstandarProducto);
/* 279:    */       }
/* 280:    */     }
/* 281:369 */     return lista;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public RangoCostoEstandarProducto getRangoCostoEstandarProducto()
/* 285:    */   {
/* 286:378 */     if (this.rangoCostoEstandarProducto == null) {
/* 287:379 */       crearRangoCostoEstandarProducto();
/* 288:    */     }
/* 289:381 */     return this.rangoCostoEstandarProducto;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setRangoCostoEstandarProducto(RangoCostoEstandarProducto rangoCostoEstandarProducto)
/* 293:    */   {
/* 294:391 */     this.rangoCostoEstandarProducto = rangoCostoEstandarProducto;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public LazyDataModel<RangoCostoEstandarProducto> getListaRangoCostoEstandarProducto()
/* 298:    */   {
/* 299:400 */     return this.listaRangoCostoEstandarProducto;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setListaRangoCostoEstandarProducto(LazyDataModel<RangoCostoEstandarProducto> listaRangoCostoEstandarProducto)
/* 303:    */   {
/* 304:410 */     this.listaRangoCostoEstandarProducto = listaRangoCostoEstandarProducto;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public DataTable getDtRangoCostoEstandarProducto()
/* 308:    */   {
/* 309:419 */     return this.dtRangoCostoEstandarProducto;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setDtRangoCostoEstandarProducto(DataTable dtRangoCostoEstandarProducto)
/* 313:    */   {
/* 314:429 */     this.dtRangoCostoEstandarProducto = dtRangoCostoEstandarProducto;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public HashMap<Integer, CostoEstandarProducto> getHmCostoEstandarProducto()
/* 318:    */   {
/* 319:438 */     if (this.hmCostoEstandarProducto == null) {
/* 320:439 */       this.hmCostoEstandarProducto = new HashMap();
/* 321:    */     }
/* 322:441 */     return this.hmCostoEstandarProducto;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setHmCostoEstandarProducto(HashMap<Integer, CostoEstandarProducto> hmCostoEstandarProducto)
/* 326:    */   {
/* 327:451 */     this.hmCostoEstandarProducto = hmCostoEstandarProducto;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public DataTable getDtCostoEstandarProducto()
/* 331:    */   {
/* 332:455 */     return this.dtCostoEstandarProducto;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setDtCostoEstandarProducto(DataTable dtCostoEstandarProducto)
/* 336:    */   {
/* 337:459 */     this.dtCostoEstandarProducto = dtCostoEstandarProducto;
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.RangoCostoEstandarProductoBean
 * JD-Core Version:    0.7.0.1
 */