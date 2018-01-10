/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetallePlantillaAsiento;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.PlantillaAsiento;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioPlantillaAsiento;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.JsfUtil;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ManagedProperty;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.component.datatable.DataTable;
/*  28:    */ import org.primefaces.event.SelectEvent;
/*  29:    */ import org.primefaces.model.LazyDataModel;
/*  30:    */ import org.primefaces.model.SortOrder;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class PlantillaAsientoBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -5788627579433275126L;
/*  38:    */   @EJB
/*  39:    */   private ServicioPlantillaAsiento servicioPlantillaAsiento;
/*  40:    */   @EJB
/*  41:    */   private ServicioCuentaContable servicioCuentaContable;
/*  42:    */   private PlantillaAsiento plantillaAsiento;
/*  43:    */   private LazyDataModel<PlantillaAsiento> listaPlantillaAsiento;
/*  44:    */   private CuentaContable cuentaContable;
/*  45:    */   private DetallePlantillaAsiento detallePlantillaAsientoSeleccionada;
/*  46:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  47:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  48:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  49:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  50:    */   private DataTable dtCuentaContable;
/*  51:    */   
/*  52:    */   @PostConstruct
/*  53:    */   public void init()
/*  54:    */   {
/*  55: 82 */     this.listaPlantillaAsiento = new LazyDataModel()
/*  56:    */     {
/*  57:    */       private static final long serialVersionUID = 1L;
/*  58:    */       
/*  59:    */       public List<PlantillaAsiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  60:    */       {
/*  61: 89 */         List<PlantillaAsiento> lista = new ArrayList();
/*  62:    */         
/*  63: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  64:    */         
/*  65: 93 */         lista = PlantillaAsientoBean.this.servicioPlantillaAsiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  66: 94 */         PlantillaAsientoBean.this.listaPlantillaAsiento.setRowCount(PlantillaAsientoBean.this.servicioPlantillaAsiento.contarPorCriterio(filters));
/*  67:    */         
/*  68: 96 */         return lista;
/*  69:    */       }
/*  70:    */     };
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void crearAsiento()
/*  74:    */   {
/*  75:106 */     this.plantillaAsiento = new PlantillaAsiento();
/*  76:107 */     this.plantillaAsiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  77:108 */     this.plantillaAsiento.setSucursal(AppUtil.getSucursal());
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String guardar()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:118 */       this.servicioPlantillaAsiento.guardar(this.plantillaAsiento);
/*  85:119 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  86:    */       
/*  87:121 */       setEditado(false);
/*  88:122 */       limpiar();
/*  89:123 */       return "";
/*  90:    */     }
/*  91:    */     catch (AS2Exception e)
/*  92:    */     {
/*  93:126 */       JsfUtil.addErrorMessage(e, "");
/*  94:127 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  99:130 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 100:    */     }
/* 101:132 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String eliminar()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:141 */       this.plantillaAsiento = this.servicioPlantillaAsiento.cargarDetalle(Integer.valueOf(this.plantillaAsiento.getId()));
/* 109:142 */       this.plantillaAsiento.setEliminado(true);
/* 110:143 */       for (DetallePlantillaAsiento detalle : this.plantillaAsiento.getListaDetallePlantillaAsiento()) {
/* 111:144 */         detalle.setEliminado(true);
/* 112:    */       }
/* 113:146 */       this.servicioPlantillaAsiento.guardar(this.plantillaAsiento);
/* 114:147 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 115:148 */       limpiar();
/* 116:    */     }
/* 117:    */     catch (Exception e)
/* 118:    */     {
/* 119:150 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 120:151 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 121:    */     }
/* 122:153 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String cargarDatos()
/* 126:    */   {
/* 127:160 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String editar()
/* 131:    */   {
/* 132:170 */     if (this.plantillaAsiento.getId() > 0) {
/* 133:    */       try
/* 134:    */       {
/* 135:172 */         this.plantillaAsiento = this.servicioPlantillaAsiento.cargarDetalle(Integer.valueOf(this.plantillaAsiento.getId()));
/* 136:173 */         this.servicioPlantillaAsiento.calcularTotales(this.plantillaAsiento);
/* 137:174 */         setEditado(true);
/* 138:    */       }
/* 139:    */       catch (Exception e)
/* 140:    */       {
/* 141:176 */         addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 142:177 */         LOG.error("ERROR AL CARGAR LOS DETALLES DATOS");
/* 143:    */       }
/* 144:    */     } else {
/* 145:181 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 146:    */     }
/* 147:184 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String limpiar()
/* 151:    */   {
/* 152:193 */     crearAsiento();
/* 153:194 */     this.detallePlantillaAsientoSeleccionada = null;
/* 154:195 */     this.cuentaContable = null;
/* 155:196 */     return "";
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<DetallePlantillaAsiento> getDetallePlantillaAsiento()
/* 159:    */   {
/* 160:205 */     List<DetallePlantillaAsiento> lista = new ArrayList();
/* 161:206 */     for (DetallePlantillaAsiento da : this.plantillaAsiento.getListaDetallePlantillaAsiento()) {
/* 162:207 */       if (!da.isEliminado()) {
/* 163:208 */         lista.add(da);
/* 164:    */       }
/* 165:    */     }
/* 166:211 */     return lista;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String agregarDetalle()
/* 170:    */   {
/* 171:221 */     DetallePlantillaAsiento detalleAsiento = new DetallePlantillaAsiento();
/* 172:222 */     detalleAsiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 173:223 */     detalleAsiento.setIdOrganizacion(AppUtil.getSucursal().getId());
/* 174:224 */     detalleAsiento.setCuentaContable(new CuentaContable());
/* 175:225 */     detalleAsiento.setDebe(BigDecimal.ZERO);
/* 176:226 */     detalleAsiento.setHaber(BigDecimal.ZERO);
/* 177:227 */     detalleAsiento.setPlantillaAsiento(this.plantillaAsiento);
/* 178:228 */     this.plantillaAsiento.getListaDetallePlantillaAsiento().add(detalleAsiento);
/* 179:229 */     return "";
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void buscarCuentaContable(DetallePlantillaAsiento detalle)
/* 183:    */   {
/* 184:    */     try
/* 185:    */     {
/* 186:237 */       String codigoCuentaContable = detalle.getCuentaContable().getCodigo();
/* 187:238 */       if (codigoCuentaContable != "")
/* 188:    */       {
/* 189:239 */         this.cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion().getIdOrganizacion());
/* 190:240 */         detalle.setCuentaContable(this.cuentaContable);
/* 191:    */       }
/* 192:    */     }
/* 193:    */     catch (ExcepcionAS2Financiero e)
/* 194:    */     {
/* 195:243 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + detalle.getCuentaContable().getCodigo();
/* 196:244 */       addInfoMessage(strMensaje);
/* 197:    */     }
/* 198:    */     catch (Exception e)
/* 199:    */     {
/* 200:247 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void seleccionarCuentaContable(SelectEvent event)
/* 205:    */   {
/* 206:    */     try
/* 207:    */     {
/* 208:258 */       this.cuentaContable = ((CuentaContable)event.getObject());
/* 209:259 */       this.detallePlantillaAsientoSeleccionada.setCuentaContable(this.cuentaContable);
/* 210:    */     }
/* 211:    */     catch (Exception e)
/* 212:    */     {
/* 213:262 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 214:263 */       LOG.error("ERROR AL CARGAR CUENTA CONTABLE", e);
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void seleccionarDimensionContableListener(SelectEvent event)
/* 219:    */   {
/* 220:268 */     DimensionContable dimension = (DimensionContable)event.getObject();
/* 221:    */     try
/* 222:    */     {
/* 223:270 */       String numeroDimension = getListaDimensionContableBean().getNumeroDimension();
/* 224:271 */       if (numeroDimension.equals("1")) {
/* 225:272 */         this.detallePlantillaAsientoSeleccionada.setDimensionContable1(dimension);
/* 226:273 */       } else if (numeroDimension.equals("2")) {
/* 227:274 */         this.detallePlantillaAsientoSeleccionada.setDimensionContable2(dimension);
/* 228:275 */       } else if (numeroDimension.equals("3")) {
/* 229:276 */         this.detallePlantillaAsientoSeleccionada.setDimensionContable3(dimension);
/* 230:277 */       } else if (numeroDimension.equals("4")) {
/* 231:278 */         this.detallePlantillaAsientoSeleccionada.setDimensionContable4(dimension);
/* 232:279 */       } else if (numeroDimension.equals("5")) {
/* 233:280 */         this.detallePlantillaAsientoSeleccionada.setDimensionContable5(dimension);
/* 234:    */       }
/* 235:    */     }
/* 236:    */     catch (Exception e)
/* 237:    */     {
/* 238:284 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 239:285 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void seleccionarDetalle(DetallePlantillaAsiento detalle)
/* 244:    */   {
/* 245:290 */     this.detallePlantillaAsientoSeleccionada = detalle;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String eliminarDetalle(DetallePlantillaAsiento detalle)
/* 249:    */   {
/* 250:299 */     detalle.setEliminado(true);
/* 251:300 */     this.servicioPlantillaAsiento.calcularTotales(this.plantillaAsiento);
/* 252:301 */     return "";
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void calcularTotalesListener()
/* 256:    */   {
/* 257:308 */     this.servicioPlantillaAsiento.calcularTotales(this.plantillaAsiento);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String copiar()
/* 261:    */   {
/* 262:318 */     this.plantillaAsiento = this.servicioPlantillaAsiento.cargarDetalle(Integer.valueOf(this.plantillaAsiento.getId()));
/* 263:319 */     this.servicioPlantillaAsiento.calcularTotales(this.plantillaAsiento);
/* 264:320 */     this.plantillaAsiento.setIdPlantillaAsiento(0);
/* 265:321 */     for (DetallePlantillaAsiento detalle : this.plantillaAsiento.getListaDetallePlantillaAsiento())
/* 266:    */     {
/* 267:322 */       detalle.setIdDetallePlantillaAsiento(0);
/* 268:323 */       detalle.setPlantillaAsiento(this.plantillaAsiento);
/* 269:    */     }
/* 270:325 */     setEditado(true);
/* 271:    */     
/* 272:327 */     return "";
/* 273:    */   }
/* 274:    */   
/* 275:    */   public ServicioPlantillaAsiento getServicioPlantillaAsiento()
/* 276:    */   {
/* 277:331 */     return this.servicioPlantillaAsiento;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setServicioPlantillaAsiento(ServicioPlantillaAsiento servicioPlantillaAsiento)
/* 281:    */   {
/* 282:335 */     this.servicioPlantillaAsiento = servicioPlantillaAsiento;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public ServicioCuentaContable getServicioCuentaContable()
/* 286:    */   {
/* 287:339 */     return this.servicioCuentaContable;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setServicioCuentaContable(ServicioCuentaContable servicioCuentaContable)
/* 291:    */   {
/* 292:343 */     this.servicioCuentaContable = servicioCuentaContable;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public PlantillaAsiento getPlantillaAsiento()
/* 296:    */   {
/* 297:347 */     return this.plantillaAsiento;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setPlantillaAsiento(PlantillaAsiento plantillaAsiento)
/* 301:    */   {
/* 302:351 */     this.plantillaAsiento = plantillaAsiento;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public LazyDataModel<PlantillaAsiento> getListaPlantillaAsiento()
/* 306:    */   {
/* 307:355 */     return this.listaPlantillaAsiento;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setListaPlantillaAsiento(LazyDataModel<PlantillaAsiento> listaPlantillaAsiento)
/* 311:    */   {
/* 312:359 */     this.listaPlantillaAsiento = listaPlantillaAsiento;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public CuentaContable getCuentaContable()
/* 316:    */   {
/* 317:363 */     return this.cuentaContable;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 321:    */   {
/* 322:367 */     this.cuentaContable = cuentaContable;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public DataTable getDtCuentaContable()
/* 326:    */   {
/* 327:371 */     return this.dtCuentaContable;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 331:    */   {
/* 332:375 */     this.dtCuentaContable = dtCuentaContable;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public DetallePlantillaAsiento getDetallePlantillaAsientoSeleccionada()
/* 336:    */   {
/* 337:379 */     return this.detallePlantillaAsientoSeleccionada;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setDetallePlantillaAsientoSeleccionada(DetallePlantillaAsiento detallePlantillaAsientoSeleccionada)
/* 341:    */   {
/* 342:383 */     this.detallePlantillaAsientoSeleccionada = detallePlantillaAsientoSeleccionada;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 346:    */   {
/* 347:390 */     return this.listaDimensionContableBean;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 351:    */   {
/* 352:398 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 356:    */   {
/* 357:402 */     return this.listaCuentaContableBean;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 361:    */   {
/* 362:407 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 363:    */   }
/* 364:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.PlantillaAsientoBean
 * JD-Core Version:    0.7.0.1
 */