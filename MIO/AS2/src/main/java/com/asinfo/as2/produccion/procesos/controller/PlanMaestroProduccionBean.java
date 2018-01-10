/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.Unidad;
/*   8:    */ import com.asinfo.as2.entities.produccion.DetallePlanMaestroProduccion;
/*   9:    */ import com.asinfo.as2.entities.produccion.PlanMaestroProduccion;
/*  10:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  11:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  12:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  17:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanMaestroProduccion;
/*  18:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import com.asinfo.as2.utils.JsfUtil;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ import org.primefaces.component.datatable.DataTable;
/*  34:    */ import org.primefaces.model.LazyDataModel;
/*  35:    */ import org.primefaces.model.SortOrder;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ @ViewScoped
/*  39:    */ public class PlanMaestroProduccionBean
/*  40:    */   extends PageControllerAS2
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  43:    */   @EJB
/*  44:    */   private ServicioPlanMaestroProduccion servicioPlanMaestroProduccion;
/*  45:    */   @EJB
/*  46:    */   private ServicioProducto servicioProducto;
/*  47:    */   @EJB
/*  48:    */   private ServicioUnidad servicioUnidad;
/*  49:    */   @EJB
/*  50:    */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  51:    */   private DataTable dtDetallePlanMaestroProduccion;
/*  52:    */   private DataTable dtPlanMaestroProduccion;
/*  53:    */   private PlanMaestroProduccion planMaestroProduccion;
/*  54:    */   private LazyDataModel<PlanMaestroProduccion> listaPlanMaestroProduccion;
/*  55:    */   private DetallePlanMaestroProduccion detallePlanMaestroProduccionSeleccionado;
/*  56:    */   private List<DetallePlanMaestroProduccion> listaDetallePlanMaestroProduccionFiltrados;
/*  57:    */   private List<SelectItem> listaMes;
/*  58:    */   
/*  59:    */   @PostConstruct
/*  60:    */   public void init()
/*  61:    */   {
/*  62: 93 */     this.listaPlanMaestroProduccion = new LazyDataModel()
/*  63:    */     {
/*  64:    */       private static final long serialVersionUID = 1L;
/*  65:    */       
/*  66:    */       public List<PlanMaestroProduccion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  67:    */       {
/*  68:100 */         List<PlanMaestroProduccion> lista = new ArrayList();
/*  69:101 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  70:    */         
/*  71:103 */         lista = PlanMaestroProduccionBean.this.servicioPlanMaestroProduccion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  72:    */         
/*  73:105 */         PlanMaestroProduccionBean.this.listaPlanMaestroProduccion.setRowCount(PlanMaestroProduccionBean.this.servicioPlanMaestroProduccion.contarPorCriterio(filters));
/*  74:    */         
/*  75:107 */         return lista;
/*  76:    */       }
/*  77:    */     };
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String editar()
/*  81:    */   {
/*  82:115 */     if ((this.planMaestroProduccion != null) && (this.planMaestroProduccion.getId() != 0))
/*  83:    */     {
/*  84:116 */       this.planMaestroProduccion = this.servicioPlanMaestroProduccion.cargarDetalle(this.planMaestroProduccion.getId());
/*  85:117 */       setEditado(true);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:119 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  90:    */     }
/*  91:121 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String guardar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:127 */       this.servicioPlanMaestroProduccion.guardar(this.planMaestroProduccion);
/*  99:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 100:129 */       limpiar();
/* 101:130 */       setEditado(false);
/* 102:    */     }
/* 103:    */     catch (ExcepcionAS2Financiero e)
/* 104:    */     {
/* 105:133 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 106:134 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 107:    */     }
/* 108:    */     catch (AS2Exception e)
/* 109:    */     {
/* 110:136 */       JsfUtil.addErrorMessage(e, "");
/* 111:137 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 112:    */     }
/* 113:    */     catch (ExcepcionAS2 e)
/* 114:    */     {
/* 115:139 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 116:140 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 121:143 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 122:    */     }
/* 123:145 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String eliminar()
/* 127:    */   {
/* 128:    */     try
/* 129:    */     {
/* 130:151 */       this.servicioPlanMaestroProduccion.eliminar(this.planMaestroProduccion);
/* 131:152 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 132:    */     }
/* 133:    */     catch (Exception e)
/* 134:    */     {
/* 135:154 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 136:155 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 137:    */     }
/* 138:157 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String cargarDatos()
/* 142:    */   {
/* 143:162 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String limpiar()
/* 147:    */   {
/* 148:167 */     Date inicio = new Date();
/* 149:168 */     inicio.setMonth(0);
/* 150:169 */     inicio.setDate(1);
/* 151:170 */     Date fin = new Date();
/* 152:171 */     fin.setMonth(11);
/* 153:172 */     fin.setDate(31);
/* 154:173 */     this.planMaestroProduccion = new PlanMaestroProduccion();
/* 155:174 */     this.planMaestroProduccion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 156:175 */     this.planMaestroProduccion.setIdSucursal(AppUtil.getSucursal().getId());
/* 157:176 */     this.planMaestroProduccion.setFechaInicio(inicio);
/* 158:177 */     this.planMaestroProduccion.setFechaFin(fin);
/* 159:178 */     this.planMaestroProduccion.setActivo(true);
/* 160:    */     
/* 161:180 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public DataTable getDtDetallePlanMaestroProduccion()
/* 165:    */   {
/* 166:184 */     return this.dtDetallePlanMaestroProduccion;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setDtDetallePlanMaestroProduccion(DataTable dtDetallePlanMaestroProduccion)
/* 170:    */   {
/* 171:188 */     this.dtDetallePlanMaestroProduccion = dtDetallePlanMaestroProduccion;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public DataTable getDtPlanMaestroProduccion()
/* 175:    */   {
/* 176:192 */     return this.dtPlanMaestroProduccion;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setDtPlanMaestroProduccion(DataTable dtPlanMaestroProduccion)
/* 180:    */   {
/* 181:196 */     this.dtPlanMaestroProduccion = dtPlanMaestroProduccion;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public PlanMaestroProduccion getPlanMaestroProduccion()
/* 185:    */   {
/* 186:200 */     return this.planMaestroProduccion;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setPlanMaestroProduccion(PlanMaestroProduccion planMaestroProduccion)
/* 190:    */   {
/* 191:204 */     this.planMaestroProduccion = planMaestroProduccion;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public LazyDataModel<PlanMaestroProduccion> getListaPlanMaestroProduccion()
/* 195:    */   {
/* 196:208 */     return this.listaPlanMaestroProduccion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaPlanMaestroProduccion(LazyDataModel<PlanMaestroProduccion> listaPlanMaestroProduccion)
/* 200:    */   {
/* 201:212 */     this.listaPlanMaestroProduccion = listaPlanMaestroProduccion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public DetallePlanMaestroProduccion getDetallePlanMaestroProduccionSeleccionado()
/* 205:    */   {
/* 206:216 */     return this.detallePlanMaestroProduccionSeleccionado;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setDetallePlanMaestroProduccionSeleccionado(DetallePlanMaestroProduccion detallePlanMaestroProduccionSeleccionado)
/* 210:    */   {
/* 211:220 */     this.detallePlanMaestroProduccionSeleccionado = detallePlanMaestroProduccionSeleccionado;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String eliminarDetalle()
/* 215:    */   {
/* 216:224 */     this.detallePlanMaestroProduccionSeleccionado = ((DetallePlanMaestroProduccion)this.dtDetallePlanMaestroProduccion.getRowData());
/* 217:225 */     this.detallePlanMaestroProduccionSeleccionado.setEliminado(true);
/* 218:226 */     this.listaDetallePlanMaestroProduccionFiltrados = null;
/* 219:227 */     this.dtDetallePlanMaestroProduccion.reset();
/* 220:228 */     return "";
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<Unidad> getListaUnidad()
/* 224:    */   {
/* 225:232 */     Map<String, String> filtros = new HashMap();
/* 226:233 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 227:234 */     filtros.put("activo", "true");
/* 228:235 */     return this.servicioUnidad.obtenerListaCombo("nombre", true, filtros);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public List<RutaFabricacion> getListaRutaFabricacion()
/* 232:    */   {
/* 233:239 */     Map<String, String> filtros = new HashMap();
/* 234:240 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 235:241 */     filtros.put("activo", "true");
/* 236:242 */     return this.servicioRutaFabricacion.obtenerListaCombo("nombre", true, filtros);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<DetallePlanMaestroProduccion> getListaDetallePlanMaestroProduccion()
/* 240:    */   {
/* 241:246 */     List<DetallePlanMaestroProduccion> lista = new ArrayList();
/* 242:247 */     for (DetallePlanMaestroProduccion detallePlanMaestroProduccion : this.planMaestroProduccion.getListaDetallePlanMaestroProduccion()) {
/* 243:248 */       if (!detallePlanMaestroProduccion.isEliminado()) {
/* 244:249 */         lista.add(detallePlanMaestroProduccion);
/* 245:    */       }
/* 246:    */     }
/* 247:253 */     return lista;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void cargarProductos()
/* 251:    */   {
/* 252:257 */     this.servicioPlanMaestroProduccion.actualizarProductos(this.planMaestroProduccion);
/* 253:258 */     this.listaDetallePlanMaestroProduccionFiltrados = null;
/* 254:259 */     this.dtDetallePlanMaestroProduccion.reset();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public List<RutaFabricacion> autocompletarRutaFabricacion(String consulta)
/* 258:    */   {
/* 259:263 */     this.detallePlanMaestroProduccionSeleccionado = ((DetallePlanMaestroProduccion)getDtDetallePlanMaestroProduccion().getRowData());
/* 260:264 */     return this.servicioRutaFabricacion.autocompletarRutaFabricacion(this.detallePlanMaestroProduccionSeleccionado.getProducto(), consulta);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public List<DetallePlanMaestroProduccion> getListaDetallePlanMaestroProduccionFiltrados()
/* 264:    */   {
/* 265:268 */     return this.listaDetallePlanMaestroProduccionFiltrados;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setListaDetallePlanMaestroProduccionFiltrados(List<DetallePlanMaestroProduccion> listaDetallePlanMaestroProduccionFiltrados)
/* 269:    */   {
/* 270:272 */     this.listaDetallePlanMaestroProduccionFiltrados = listaDetallePlanMaestroProduccionFiltrados;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<SelectItem> getListaMes()
/* 274:    */   {
/* 275:276 */     if (this.listaMes == null)
/* 276:    */     {
/* 277:277 */       this.listaMes = new ArrayList();
/* 278:278 */       for (Mes item : Mes.values()) {
/* 279:279 */         this.listaMes.add(new SelectItem("" + item.ordinal(), item.getNombre()));
/* 280:    */       }
/* 281:    */     }
/* 282:282 */     return this.listaMes;
/* 283:    */   }
/* 284:    */   
/* 285:    */   private String[] mesesNoSeleccionados(String[] mesesSeleccionados)
/* 286:    */   {
/* 287:286 */     String noSeleccionados = "";
/* 288:287 */     for (Mes mes : Mes.values())
/* 289:    */     {
/* 290:288 */       boolean encontre = false;
/* 291:289 */       for (String seleccionado : mesesSeleccionados) {
/* 292:290 */         if (("" + mes.ordinal()).equals(seleccionado))
/* 293:    */         {
/* 294:291 */           encontre = true;
/* 295:292 */           break;
/* 296:    */         }
/* 297:    */       }
/* 298:295 */       if (!encontre) {
/* 299:296 */         noSeleccionados = noSeleccionados + mes.ordinal() + ",";
/* 300:    */       }
/* 301:    */     }
/* 302:299 */     if (noSeleccionados.length() > 0) {
/* 303:300 */       return noSeleccionados.split(",");
/* 304:    */     }
/* 305:302 */     return new String[0];
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void copiarDetalle()
/* 309:    */   {
/* 310:308 */     if (this.detallePlanMaestroProduccionSeleccionado != null)
/* 311:    */     {
/* 312:309 */       DetallePlanMaestroProduccion detalle = new DetallePlanMaestroProduccion();
/* 313:310 */       detalle.setIdOrganizacion(this.detallePlanMaestroProduccionSeleccionado.getIdOrganizacion());
/* 314:311 */       detalle.setIdSucursal(this.detallePlanMaestroProduccionSeleccionado.getIdSucursal());
/* 315:312 */       detalle.setCantidadDiasStock(this.detallePlanMaestroProduccionSeleccionado.getCantidadDiasStock());
/* 316:313 */       detalle.setMesesSeleccionados(mesesNoSeleccionados(this.detallePlanMaestroProduccionSeleccionado.getMesesSeleccionados()));
/* 317:314 */       detalle.setPlanMaestroProduccion(this.detallePlanMaestroProduccionSeleccionado.getPlanMaestroProduccion());
/* 318:315 */       detalle.setProducto(this.detallePlanMaestroProduccionSeleccionado.getProducto());
/* 319:316 */       detalle.setRutaFabricacion(this.detallePlanMaestroProduccionSeleccionado.getRutaFabricacion());
/* 320:317 */       detalle.setProporcionLunes(this.detallePlanMaestroProduccionSeleccionado.getProporcionLunes());
/* 321:318 */       detalle.setProporcionMartes(this.detallePlanMaestroProduccionSeleccionado.getProporcionMartes());
/* 322:319 */       detalle.setProporcionMiercoles(this.detallePlanMaestroProduccionSeleccionado.getProporcionMiercoles());
/* 323:320 */       detalle.setProporcionJueves(this.detallePlanMaestroProduccionSeleccionado.getProporcionJueves());
/* 324:321 */       detalle.setProporcionViernes(this.detallePlanMaestroProduccionSeleccionado.getProporcionViernes());
/* 325:322 */       detalle.setProporcionSabado(this.detallePlanMaestroProduccionSeleccionado.getProporcionSabado());
/* 326:323 */       detalle.setProporcionDomingo(this.detallePlanMaestroProduccionSeleccionado.getProporcionDomingo());
/* 327:324 */       this.planMaestroProduccion.getListaDetallePlanMaestroProduccion().add(detalle);
/* 328:325 */       FuncionesUtiles.ordenaLista(this.planMaestroProduccion.getListaDetallePlanMaestroProduccion(), "producto", true);
/* 329:326 */       this.listaDetallePlanMaestroProduccionFiltrados = null;
/* 330:327 */       this.dtDetallePlanMaestroProduccion.reset();
/* 331:328 */       this.detallePlanMaestroProduccionSeleccionado = detalle;
/* 332:    */     }
/* 333:    */     else
/* 334:    */     {
/* 335:330 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 336:    */     }
/* 337:    */   }
/* 338:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.PlanMaestroProduccionBean
 * JD-Core Version:    0.7.0.1
 */