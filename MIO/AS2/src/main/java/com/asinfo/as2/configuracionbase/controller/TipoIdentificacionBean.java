/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.dao.sri.TipoComprobanteSRIDao;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  10:    */ import com.asinfo.as2.entities.TipoIdentificacionComprobanteSRI;
/*  11:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  12:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  13:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
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
/*  31:    */ public class TipoIdentificacionBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = -3344766710473334395L;
/*  35:    */   @EJB
/*  36:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  37:    */   @EJB
/*  38:    */   private ServicioCreditoTributario servicioCreditoTributario;
/*  39:    */   @EJB
/*  40:    */   private TipoComprobanteSRIDao tipoComprobanteSRIDao;
/*  41:    */   private TipoIdentificacion tipoIdentificacion;
/*  42:    */   private LazyDataModel<TipoIdentificacion> listaTipoIdentificacion;
/*  43:    */   private List<TipoIdentificacion> listaTipoIdentificacionCombo;
/*  44:    */   private List<TipoIdentificacionComprobanteSRI> listaTipoIdentificacionComprobanteSRI;
/*  45:    */   private TipoComprobanteSRI[] listaTipoComprobanteSeleccionados;
/*  46:    */   private List<TipoComprobanteSRI> listaTipoComprobanteNoAsignados;
/*  47:    */   private DataTable dtTipoIdentificacion;
/*  48:    */   
/*  49:    */   @PostConstruct
/*  50:    */   public void init()
/*  51:    */   {
/*  52: 74 */     this.listaTipoIdentificacion = new LazyDataModel()
/*  53:    */     {
/*  54:    */       private static final long serialVersionUID = 1L;
/*  55:    */       
/*  56:    */       public List<TipoIdentificacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  57:    */       {
/*  58: 81 */         List<TipoIdentificacion> lista = new ArrayList();
/*  59: 82 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  60:    */         
/*  61: 84 */         lista = TipoIdentificacionBean.this.servicioTipoIdentificacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  62: 85 */         TipoIdentificacionBean.this.listaTipoIdentificacion.setRowCount(TipoIdentificacionBean.this.servicioTipoIdentificacion.contarPorCriterio(filters));
/*  63:    */         
/*  64: 87 */         return lista;
/*  65:    */       }
/*  66:    */     };
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String editar()
/*  70:    */   {
/*  71:100 */     if (getTipoIdentificacion().getId() > 0)
/*  72:    */     {
/*  73:101 */       this.tipoIdentificacion = this.servicioTipoIdentificacion.cargarDetalle(this.tipoIdentificacion);
/*  74:102 */       setEditado(true);
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78:104 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  79:    */     }
/*  80:107 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String guardar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:119 */       this.servicioTipoIdentificacion.guardar(getTipoIdentificacion());
/*  88:120 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  89:121 */       limpiar();
/*  90:122 */       setEditado(false);
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:125 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  95:126 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  96:    */     }
/*  97:129 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String eliminar()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:140 */       this.servicioTipoIdentificacion.eliminar(getTipoIdentificacion());
/* 105:141 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 106:    */     }
/* 107:    */     catch (Exception e)
/* 108:    */     {
/* 109:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 110:145 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 111:    */     }
/* 112:147 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String limpiar()
/* 116:    */   {
/* 117:157 */     crearTipoIdentificacion();
/* 118:158 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String cargarDatos()
/* 122:    */   {
/* 123:168 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void crearTipoIdentificacion()
/* 127:    */   {
/* 128:177 */     this.tipoIdentificacion = new TipoIdentificacion();
/* 129:178 */     this.tipoIdentificacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 130:179 */     this.tipoIdentificacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 131:180 */     this.listaTipoIdentificacionComprobanteSRI = null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public List<TipoIdentificacion> obtenerListaCombo()
/* 135:    */   {
/* 136:184 */     List<TipoIdentificacion> lista = new ArrayList();
/* 137:185 */     String sortField = "nombre";
/* 138:186 */     lista = this.servicioTipoIdentificacion.obtenerListaCombo(sortField, true, null);
/* 139:187 */     return lista;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void onRowSelect(SelectEvent event)
/* 143:    */   {
/* 144:196 */     this.tipoIdentificacion = ((TipoIdentificacion)event.getObject());
/* 145:    */   }
/* 146:    */   
/* 147:    */   public TipoIdentificacion getTipoIdentificacion()
/* 148:    */   {
/* 149:205 */     if (this.tipoIdentificacion == null) {
/* 150:206 */       crearTipoIdentificacion();
/* 151:    */     }
/* 152:208 */     return this.tipoIdentificacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/* 156:    */   {
/* 157:218 */     this.tipoIdentificacion = tipoIdentificacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public LazyDataModel<TipoIdentificacion> getListaTipoIdentificacion()
/* 161:    */   {
/* 162:227 */     return this.listaTipoIdentificacion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setListaTipoIdentificacion(LazyDataModel<TipoIdentificacion> listaTipoIdentificacion)
/* 166:    */   {
/* 167:237 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public DataTable getDtTipoIdentificacion()
/* 171:    */   {
/* 172:246 */     return this.dtTipoIdentificacion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setDtTipoIdentificacion(DataTable dtTipoIdentificacion)
/* 176:    */   {
/* 177:256 */     this.dtTipoIdentificacion = dtTipoIdentificacion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<TipoIdentificacion> getListaTipoIdentificacionCombo()
/* 181:    */   {
/* 182:260 */     if (this.listaTipoIdentificacionCombo == null) {
/* 183:261 */       this.listaTipoIdentificacionCombo = obtenerListaCombo();
/* 184:    */     }
/* 185:263 */     return this.listaTipoIdentificacionCombo;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setListaTipoIdentificacionCombo(List<TipoIdentificacion> listaTipoIdentificacionCombo)
/* 189:    */   {
/* 190:267 */     this.listaTipoIdentificacionCombo = listaTipoIdentificacionCombo;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<TipoIdentificacionComprobanteSRI> getListaTipoIdentificacionComprobanteSRI()
/* 194:    */   {
/* 195:274 */     if ((this.tipoIdentificacion != null) && (this.listaTipoIdentificacionComprobanteSRI == null))
/* 196:    */     {
/* 197:275 */       this.listaTipoIdentificacionComprobanteSRI = new ArrayList();
/* 198:276 */       for (TipoIdentificacionComprobanteSRI ticsri : this.tipoIdentificacion.getListaTipoIdentificacionComprobanteSRI()) {
/* 199:277 */         if (!ticsri.isEliminado()) {
/* 200:278 */           this.listaTipoIdentificacionComprobanteSRI.add(ticsri);
/* 201:    */         }
/* 202:    */       }
/* 203:    */     }
/* 204:283 */     return this.listaTipoIdentificacionComprobanteSRI;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaTipoIdentificacionComprobanteSRI(List<TipoIdentificacionComprobanteSRI> listaTipoIdentificacionComprobanteSRI)
/* 208:    */   {
/* 209:291 */     this.listaTipoIdentificacionComprobanteSRI = listaTipoIdentificacionComprobanteSRI;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<CreditoTributarioSRI> autocompletarCreditoTributario(String query)
/* 213:    */   {
/* 214:295 */     return this.servicioCreditoTributario.autocompletarCreditoTributario(query, true);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public TipoComprobanteSRI[] getListaTipoComprobanteSeleccionados()
/* 218:    */   {
/* 219:302 */     return this.listaTipoComprobanteSeleccionados;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setListaTipoComprobanteSeleccionados(TipoComprobanteSRI[] listaTipoComprobanteSeleccionados)
/* 223:    */   {
/* 224:310 */     this.listaTipoComprobanteSeleccionados = listaTipoComprobanteSeleccionados;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public List<TipoComprobanteSRI> getListaTipoComprobanteNoAsignados()
/* 228:    */   {
/* 229:317 */     return this.listaTipoComprobanteNoAsignados;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setListaTipoComprobanteNoAsignados(List<TipoComprobanteSRI> listaTipoComprobanteNoAsignados)
/* 233:    */   {
/* 234:325 */     this.listaTipoComprobanteNoAsignados = listaTipoComprobanteNoAsignados;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void eliminarTipoComprobante(TipoIdentificacionComprobanteSRI ticsri)
/* 238:    */   {
/* 239:329 */     ticsri.setEliminado(true);
/* 240:330 */     this.listaTipoIdentificacionComprobanteSRI = null;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public String agregarTipoComprobante()
/* 244:    */   {
/* 245:334 */     if (this.listaTipoComprobanteSeleccionados != null) {
/* 246:    */       label198:
/* 247:335 */       for (TipoComprobanteSRI tc : this.listaTipoComprobanteSeleccionados)
/* 248:    */       {
/* 249:337 */         for (TipoIdentificacionComprobanteSRI ticsri : this.tipoIdentificacion.getListaTipoIdentificacionComprobanteSRI()) {
/* 250:338 */           if (ticsri.getTipoComprobanteSRI().equals(tc))
/* 251:    */           {
/* 252:339 */             ticsri.setEliminado(false);
/* 253:340 */             getListaTipoIdentificacionComprobanteSRI().add(ticsri);
/* 254:    */             break label198;
/* 255:    */           }
/* 256:    */         }
/* 257:344 */         TipoIdentificacionComprobanteSRI ticsri = new TipoIdentificacionComprobanteSRI();
/* 258:345 */         ticsri.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 259:346 */         ticsri.setIdSucursal(AppUtil.getSucursal().getId());
/* 260:347 */         ticsri.setTipoIdentificacion(this.tipoIdentificacion);
/* 261:348 */         tc.setListaCreditoTributarioSRI(new ArrayList());
/* 262:349 */         tc.setListaComprobanteSRICreditoTributarioSRI(new ArrayList());
/* 263:350 */         ticsri.setTipoComprobanteSRI(tc);
/* 264:351 */         this.tipoIdentificacion.getListaTipoIdentificacionComprobanteSRI().add(ticsri);
/* 265:352 */         getListaTipoIdentificacionComprobanteSRI().add(ticsri);
/* 266:    */       }
/* 267:    */     }
/* 268:355 */     this.listaTipoComprobanteSeleccionados = null;
/* 269:    */     
/* 270:357 */     return "";
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void cargarTipoComprobanteNoAsignados()
/* 274:    */   {
/* 275:361 */     this.listaTipoComprobanteSeleccionados = null;
/* 276:362 */     Map<String, String> filters = new HashMap();
/* 277:363 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 278:364 */     this.listaTipoComprobanteNoAsignados = this.tipoComprobanteSRIDao.obtenerListaCombo("codigo", true, filters);
/* 279:    */     
/* 280:366 */     HashMap<Integer, TipoComprobanteSRI> mtcsri = new HashMap();
/* 281:367 */     for (TipoComprobanteSRI tcsri : this.listaTipoComprobanteNoAsignados) {
/* 282:368 */       mtcsri.put(Integer.valueOf(tcsri.getId()), tcsri);
/* 283:    */     }
/* 284:371 */     for (TipoIdentificacionComprobanteSRI ticsri : this.tipoIdentificacion.getListaTipoIdentificacionComprobanteSRI()) {
/* 285:372 */       if (!ticsri.isEliminado()) {
/* 286:373 */         mtcsri.remove(Integer.valueOf(ticsri.getTipoComprobanteSRI().getId()));
/* 287:    */       }
/* 288:    */     }
/* 289:376 */     this.listaTipoComprobanteNoAsignados = new ArrayList(mtcsri.values());
/* 290:    */   }
/* 291:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.TipoIdentificacionBean
 * JD-Core Version:    0.7.0.1
 */