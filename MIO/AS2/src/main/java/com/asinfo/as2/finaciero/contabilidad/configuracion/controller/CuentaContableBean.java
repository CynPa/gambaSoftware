/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.NivelCuenta;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCuenta;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.component.datatable.DataTable;
/*  27:    */ import org.primefaces.model.LazyDataModel;
/*  28:    */ import org.primefaces.model.SortOrder;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class CuentaContableBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = -1832175620477451430L;
/*  36:    */   @EJB
/*  37:    */   private ServicioCuentaContable servicioCuentaContable;
/*  38:    */   @EJB
/*  39:    */   private ServicioNivelCuenta servicioNivelContable;
/*  40:    */   private CuentaContable cuentaContable;
/*  41:    */   public LazyDataModel<CuentaContable> listaCuentaContable;
/*  42:    */   private List<SelectItem> listaTipoCuentaContable;
/*  43:    */   private List<SelectItem> listaCuentaContablePadre;
/*  44:    */   public List<CuentaContable> listaCuentaContableCombo;
/*  45:    */   public List<SelectItem> listaGrupoCuenta;
/*  46:    */   private List<NivelCuenta> listaNivelCuenta;
/*  47: 64 */   private TipoAccesoContable tipoAccesoCuentaContable = TipoAccesoContable.LIBRE;
/*  48:    */   private List<TipoAccesoContable> listaTipoAccesoCuentaContable;
/*  49:    */   private DataTable dtCuentaContable;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 77 */     this.listaCuentaContable = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = 1L;
/*  57:    */       
/*  58:    */       public List<CuentaContable> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 83 */         List<CuentaContable> lista = new ArrayList();
/*  61:    */         
/*  62:    */ 
/*  63: 86 */         String filtroCodigo = (String)filters.get("codigo");
/*  64: 87 */         if (filtroCodigo != null) {
/*  65: 88 */           filters.put("codigo", filtroCodigo + "%");
/*  66:    */         }
/*  67: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  68: 92 */         lista = CuentaContableBean.this.servicioCuentaContable.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  69:    */         
/*  70: 94 */         CuentaContableBean.this.listaCuentaContable.setRowCount(CuentaContableBean.this.servicioCuentaContable.contarPorCriterio(filters));
/*  71:    */         
/*  72: 96 */         return lista;
/*  73:    */       }
/*  74:    */     };
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String editar()
/*  78:    */   {
/*  79:103 */     if (getCuentaContable().getId() > 0)
/*  80:    */     {
/*  81:104 */       this.cuentaContable = this.servicioCuentaContable.cargarDetalle(this.cuentaContable.getId());
/*  82:106 */       if (this.cuentaContable.getCuentaPadre() == null) {
/*  83:107 */         this.cuentaContable.setCuentaPadre(new CuentaContable());
/*  84:    */       }
/*  85:109 */       cargarCuentaPadre();
/*  86:    */       
/*  87:111 */       setEditado(true);
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:113 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  92:    */     }
/*  93:116 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String guardar()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:126 */       this.cuentaContable.setNivelCuenta(this.servicioNivelContable.buscarPorId(Integer.valueOf(this.cuentaContable.getNivelCuenta().getId())));
/* 101:127 */       this.servicioCuentaContable.guardar(this.cuentaContable);
/* 102:128 */       setEditado(false);
/* 103:129 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 104:    */       
/* 105:131 */       cargarDatos();
/* 106:    */     }
/* 107:    */     catch (ExcepcionAS2Financiero e)
/* 108:    */     {
/* 109:134 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 110:135 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/* 111:136 */       e.printStackTrace();
/* 112:    */     }
/* 113:    */     catch (Exception e)
/* 114:    */     {
/* 115:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 116:140 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 117:    */     }
/* 118:142 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String eliminar()
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:151 */       this.servicioCuentaContable.eliminar(this.cuentaContable);
/* 126:152 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 127:153 */       cargarDatos();
/* 128:    */     }
/* 129:    */     catch (Exception e)
/* 130:    */     {
/* 131:155 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 132:156 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 133:    */     }
/* 134:158 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String cargarDatos()
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:167 */       limpiar();
/* 142:    */     }
/* 143:    */     catch (Exception e)
/* 144:    */     {
/* 145:169 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 146:170 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 147:    */     }
/* 148:172 */     return "";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String limpiar()
/* 152:    */   {
/* 153:179 */     crearCuentaContable();
/* 154:180 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<CuentaContable> autoCompletarCuentaContable(String consulta)
/* 158:    */   {
/* 159:184 */     List<CuentaContable> lista = new ArrayList();
/* 160:185 */     String sortField = "codigo";
/* 161:186 */     HashMap<String, String> filters = new HashMap();
/* 162:187 */     filters.put("nombre", consulta.trim());
/* 163:188 */     lista = this.servicioCuentaContable.obtenerListaCombo(sortField, true, filters);
/* 164:189 */     return lista;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<CuentaContable> obtenerListaCombo()
/* 168:    */   {
/* 169:193 */     List<CuentaContable> lista = new ArrayList();
/* 170:194 */     String sortField = "codigo";
/* 171:195 */     lista = this.servicioCuentaContable.obtenerListaCombo(sortField, true, null);
/* 172:196 */     return lista;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public ServicioCuentaContable getServicioCuentaContableBean()
/* 176:    */   {
/* 177:205 */     return this.servicioCuentaContable;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setServicioCuentaContableBean(ServicioCuentaContable servicioCuentaContableBean)
/* 181:    */   {
/* 182:215 */     this.servicioCuentaContable = servicioCuentaContableBean;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public CuentaContable getCuentaContable()
/* 186:    */   {
/* 187:224 */     if (this.cuentaContable == null) {
/* 188:225 */       crearCuentaContable();
/* 189:    */     }
/* 190:227 */     return this.cuentaContable;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private void crearCuentaContable()
/* 194:    */   {
/* 195:231 */     this.cuentaContable = new CuentaContable();
/* 196:232 */     this.cuentaContable.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 197:233 */     this.cuentaContable.setIdSucursal(AppUtil.getSucursal().getId());
/* 198:234 */     this.cuentaContable.setCuentaPadre(new CuentaContable());
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 202:    */   {
/* 203:246 */     this.cuentaContable = cuentaContable;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public DataTable getDtCuentaContable()
/* 207:    */   {
/* 208:255 */     return this.dtCuentaContable;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 212:    */   {
/* 213:265 */     this.dtCuentaContable = dtCuentaContable;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<SelectItem> getListaTipoCuentaContable()
/* 217:    */   {
/* 218:269 */     if (this.listaTipoCuentaContable == null)
/* 219:    */     {
/* 220:270 */       this.listaTipoCuentaContable = new ArrayList();
/* 221:272 */       for (TipoCuentaContable t : TipoCuentaContable.values())
/* 222:    */       {
/* 223:273 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 224:274 */         this.listaTipoCuentaContable.add(item);
/* 225:    */       }
/* 226:    */     }
/* 227:277 */     return this.listaTipoCuentaContable;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setListaTipoCuentaContable(List<SelectItem> listaTipoCuentaContable)
/* 231:    */   {
/* 232:281 */     this.listaTipoCuentaContable = listaTipoCuentaContable;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public ServicioNivelCuenta getServicioNivelContable()
/* 236:    */   {
/* 237:285 */     return this.servicioNivelContable;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setServicioNivelContable(ServicioNivelCuenta servicioNivelContable)
/* 241:    */   {
/* 242:289 */     this.servicioNivelContable = servicioNivelContable;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public List<SelectItem> getListaCuentaContablePadre()
/* 246:    */   {
/* 247:293 */     return this.listaCuentaContablePadre;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void cargarCuentaPadre()
/* 251:    */   {
/* 252:297 */     this.listaCuentaContablePadre = new ArrayList();
/* 253:298 */     for (CuentaContable cuentaContablePadre : this.servicioCuentaContable.buscarPorGrupoNivel(this.cuentaContable.getGrupoCuenta(), this.cuentaContable
/* 254:299 */       .getNivelCuenta().getIdNivelCuenta(), AppUtil.getOrganizacion().getIdOrganizacion()))
/* 255:    */     {
/* 256:300 */       SelectItem si = new SelectItem(Integer.valueOf(cuentaContablePadre.getIdCuentaContable()), cuentaContablePadre.getTraNombreParaMostrar());
/* 257:301 */       this.listaCuentaContablePadre.add(si);
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   public List<SelectItem> getListaGrupoCuenta()
/* 262:    */   {
/* 263:307 */     if (this.listaGrupoCuenta == null)
/* 264:    */     {
/* 265:308 */       this.listaGrupoCuenta = new ArrayList();
/* 266:310 */       for (GrupoCuenta g : GrupoCuenta.values())
/* 267:    */       {
/* 268:311 */         SelectItem item = new SelectItem(g, g.getNombre());
/* 269:312 */         this.listaGrupoCuenta.add(item);
/* 270:    */       }
/* 271:    */     }
/* 272:315 */     return this.listaGrupoCuenta;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setListaCuentaContablePadre(List<SelectItem> listaCuentaContablePadre)
/* 276:    */   {
/* 277:319 */     this.listaCuentaContablePadre = listaCuentaContablePadre;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public String obtenerMascara()
/* 281:    */   {
/* 282:329 */     String mascara = getServicioNivelContable().getMascara(this.cuentaContable.getCuentaPadre().getIdCuentaContable(), this.cuentaContable
/* 283:330 */       .getNivelCuenta().getIdNivelCuenta());
/* 284:331 */     this.cuentaContable.setMascara(mascara);
/* 285:332 */     return "";
/* 286:    */   }
/* 287:    */   
/* 288:    */   public LazyDataModel<CuentaContable> getListaCuentaContable()
/* 289:    */   {
/* 290:336 */     return this.listaCuentaContable;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setListaCuentaContable(LazyDataModel<CuentaContable> listaCuentaContable)
/* 294:    */   {
/* 295:340 */     this.listaCuentaContable = listaCuentaContable;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public List<CuentaContable> getListaCuentaContableCombo()
/* 299:    */   {
/* 300:344 */     this.listaCuentaContableCombo = obtenerListaCombo();
/* 301:345 */     return this.listaCuentaContableCombo;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setListaCuentaContableCombo(List<CuentaContable> listaCuentaContableCombo)
/* 305:    */   {
/* 306:349 */     this.listaCuentaContableCombo = listaCuentaContableCombo;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public List<NivelCuenta> getListaNivelCuenta()
/* 310:    */   {
/* 311:358 */     if (this.listaNivelCuenta == null) {
/* 312:359 */       this.listaNivelCuenta = this.servicioNivelContable.obtenerListaCombo("nombre", true, null);
/* 313:    */     }
/* 314:361 */     return this.listaNivelCuenta;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setListaNivelCuenta(List<NivelCuenta> listaNivelCuenta)
/* 318:    */   {
/* 319:371 */     this.listaNivelCuenta = listaNivelCuenta;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public TipoAccesoContable getTipoAccesoCuentaContable()
/* 323:    */   {
/* 324:380 */     return this.tipoAccesoCuentaContable;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setTipoAccesoCuentaContable(TipoAccesoContable tipoAccesoCuentaContable)
/* 328:    */   {
/* 329:390 */     this.tipoAccesoCuentaContable = tipoAccesoCuentaContable;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public List<TipoAccesoContable> getListaTipoAccesoCuentaContable()
/* 333:    */   {
/* 334:399 */     if (this.listaTipoAccesoCuentaContable == null)
/* 335:    */     {
/* 336:400 */       this.listaTipoAccesoCuentaContable = new ArrayList();
/* 337:401 */       for (TipoAccesoContable tipoAccesoCuentaContable : TipoAccesoContable.values()) {
/* 338:402 */         this.listaTipoAccesoCuentaContable.add(tipoAccesoCuentaContable);
/* 339:    */       }
/* 340:    */     }
/* 341:405 */     return this.listaTipoAccesoCuentaContable;
/* 342:    */   }
/* 343:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.CuentaContableBean
 * JD-Core Version:    0.7.0.1
 */