/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.CuentaContableDimensionContable;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  14:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ManagedProperty;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import javax.faces.model.SelectItem;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.component.datatable.DataTable;
/*  28:    */ import org.primefaces.event.SelectEvent;
/*  29:    */ import org.primefaces.model.LazyDataModel;
/*  30:    */ import org.primefaces.model.SortOrder;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class DimensionContableBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -1832175620477451430L;
/*  38:    */   @EJB
/*  39:    */   private ServicioDimensionContable servicioDimensionContable;
/*  40:    */   @EJB
/*  41:    */   private ServicioCuentaContable servicioCuentaContable;
/*  42:    */   @EJB
/*  43:    */   private ServicioGenerico<CuentaContableDimensionContable> servicioCuentaContableDimensionContable;
/*  44:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  45:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  46:    */   private DimensionContable dimensionContable;
/*  47:    */   private CuentaContable cuentaContable;
/*  48:    */   private CuentaContableDimensionContable cuentaContableDimensionContable;
/*  49:    */   public LazyDataModel<DimensionContable> listaDimensionContable;
/*  50:    */   private List<DimensionContable> listaDimensionContablePadre;
/*  51:    */   private List<TipoAccesoContable> listaTipoAccesoContable;
/*  52: 72 */   private String dimension = "1";
/*  53: 73 */   private String mascara = "";
/*  54:    */   private boolean agregaCC;
/*  55:    */   private DataTable dtDimensionContable;
/*  56:    */   private DataTable dtCuentaContable;
/*  57:    */   
/*  58:    */   @PostConstruct
/*  59:    */   public void init()
/*  60:    */   {
/*  61: 87 */     this.listaDimensionContable = new LazyDataModel()
/*  62:    */     {
/*  63:    */       private static final long serialVersionUID = 1L;
/*  64:    */       
/*  65:    */       public List<DimensionContable> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  66:    */       {
/*  67: 93 */         List<DimensionContable> lista = new ArrayList();
/*  68:    */         
/*  69: 95 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  70: 96 */         DimensionContableBean.this.cargarFiltros(filters);
/*  71: 97 */         lista = DimensionContableBean.this.servicioDimensionContable.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  72:    */         
/*  73: 99 */         DimensionContableBean.this.listaDimensionContable.setRowCount(DimensionContableBean.this.servicioDimensionContable.contarPorCriterio(filters));
/*  74:    */         
/*  75:101 */         return lista;
/*  76:    */       }
/*  77:    */     };
/*  78:    */   }
/*  79:    */   
/*  80:    */   private void cargarFiltros(Map<String, String> filters)
/*  81:    */   {
/*  82:107 */     filters.put("numero", String.valueOf(getDimension()));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String editar()
/*  86:    */   {
/*  87:112 */     if (getDimensionContable().getId() != 0)
/*  88:    */     {
/*  89:113 */       this.dimensionContable = this.servicioDimensionContable.cargarDetalle(this.dimensionContable.getId());
/*  90:114 */       String[] filtro = { "indicadorValidarDimension" + getDimension(), "true" };
/*  91:115 */       this.listaCuentaContableBean.agregarFiltro(filtro);
/*  92:116 */       this.listaCuentaContableBean.setIndicadorSeleccionarTodo(true);
/*  93:117 */       verificaDimension();
/*  94:118 */       setEditado(true);
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:120 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  99:    */     }
/* 100:123 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String guardar()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:132 */       this.servicioDimensionContable.guardar(this.dimensionContable);
/* 108:133 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 109:134 */       setEditado(false);
/* 110:135 */       cargarDatos();
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:137 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 115:138 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 116:    */     }
/* 117:140 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String eliminar()
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:149 */       this.servicioDimensionContable.eliminar(this.dimensionContable);
/* 125:150 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 126:151 */       cargarDatos();
/* 127:    */     }
/* 128:    */     catch (Exception e)
/* 129:    */     {
/* 130:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 131:154 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 132:    */     }
/* 133:156 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String cargarDatos()
/* 137:    */   {
/* 138:163 */     limpiar();
/* 139:164 */     return "";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String limpiar()
/* 143:    */   {
/* 144:171 */     crearDimensionContable();
/* 145:172 */     String[] filtro = { "indicadorValidarDimension" + this.dimension, "true" };
/* 146:173 */     this.listaCuentaContableBean.agregarFiltro(filtro);
/* 147:174 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String seleccionarCuentaContable(SelectEvent event)
/* 151:    */   {
/* 152:178 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 153:180 */     if ((getDimension().equals("1")) && (!this.cuentaContable.isIndicadorValidarDimension1())) {
/* 154:181 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cuenta_contable_indicador_dimension"));
/* 155:182 */     } else if ((getDimension().equals("2")) && (!this.cuentaContable.isIndicadorValidarDimension2())) {
/* 156:183 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cuenta_contable_indicador_dimension"));
/* 157:184 */     } else if ((getDimension().equals("3")) && (!this.cuentaContable.isIndicadorValidarDimension3())) {
/* 158:185 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cuenta_contable_indicador_dimension"));
/* 159:186 */     } else if ((getDimension().equals("4")) && (!this.cuentaContable.isIndicadorValidarDimension4())) {
/* 160:187 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cuenta_contable_indicador_dimension"));
/* 161:188 */     } else if ((getDimension().equals("5")) && (!this.cuentaContable.isIndicadorValidarDimension5())) {
/* 162:189 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cuenta_contable_indicador_dimension"));
/* 163:    */     }
/* 164:192 */     Map<Integer, CuentaContable> hashCC = new HashMap();
/* 165:193 */     for (CuentaContableDimensionContable cccc : getDimensionContable().getListaCuentaContableDimensionContable()) {
/* 166:194 */       hashCC.put(Integer.valueOf(cccc.getCuentaContable().getId()), cccc.getCuentaContable());
/* 167:    */     }
/* 168:196 */     Object filters = agregarFiltroOrganizacion(null);
/* 169:197 */     ((Map)filters).put("codigo", this.cuentaContable.getCodigo() + "%");
/* 170:198 */     ((Map)filters).put("indicadorMovimiento", "true");
/* 171:199 */     ((Map)filters).put("indicadorValidarDimension" + this.dimension, "true");
/* 172:200 */     List<CuentaContable> listCuentasGrupo = this.servicioCuentaContable.obtenerListaCombo("codigo", true, (Map)filters);
/* 173:201 */     for (CuentaContable cc : listCuentasGrupo) {
/* 174:203 */       if (hashCC.get(Integer.valueOf(cc.getId())) == null)
/* 175:    */       {
/* 176:204 */         CuentaContableDimensionContable cccc = new CuentaContableDimensionContable();
/* 177:205 */         cccc.setCuentaContable(cc);
/* 178:206 */         cccc.setDimensionContable(getDimensionContable());
/* 179:207 */         getDimensionContable().getListaCuentaContableDimensionContable().add(cccc);
/* 180:    */       }
/* 181:    */     }
/* 182:215 */     return "";
/* 183:    */   }
/* 184:    */   
/* 185:    */   public ServicioDimensionContable getServicioDimensionContableBean()
/* 186:    */   {
/* 187:226 */     return this.servicioDimensionContable;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setServicioDimensionContableBean(ServicioDimensionContable servicioDimensionContableBean)
/* 191:    */   {
/* 192:236 */     this.servicioDimensionContable = servicioDimensionContableBean;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public DimensionContable getDimensionContable()
/* 196:    */   {
/* 197:245 */     return this.dimensionContable;
/* 198:    */   }
/* 199:    */   
/* 200:    */   private void crearDimensionContable()
/* 201:    */   {
/* 202:249 */     this.dimensionContable = new DimensionContable();
/* 203:250 */     this.dimensionContable.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 204:251 */     this.dimensionContable.setIdSucursal(AppUtil.getSucursal().getId());
/* 205:252 */     this.dimensionContable.setNumero(getDimension());
/* 206:253 */     verificaDimension();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setDimensionContable(DimensionContable dimensionContable)
/* 210:    */   {
/* 211:264 */     this.dimensionContable = dimensionContable;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public DataTable getDtDimensionContable()
/* 215:    */   {
/* 216:273 */     return this.dtDimensionContable;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDtDimensionContable(DataTable dtDimensionContable)
/* 220:    */   {
/* 221:283 */     this.dtDimensionContable = dtDimensionContable;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<DimensionContable> getListaDimensionContablePadre()
/* 225:    */   {
/* 226:292 */     return this.listaDimensionContablePadre;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void cargarDimensionPadre()
/* 230:    */   {
/* 231:296 */     if (getMascara(this.dimension).equals(this.dimensionContable.getMascara())) {
/* 232:297 */       this.dimensionContable.setIndicadorMovimiento(true);
/* 233:    */     } else {
/* 234:299 */       this.dimensionContable.setIndicadorMovimiento(false);
/* 235:    */     }
/* 236:302 */     this.listaDimensionContablePadre = this.servicioDimensionContable.obtenerListaDimensionPadre(this.dimensionContable);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<SelectItem> getListaNivelDimensionContable()
/* 240:    */   {
/* 241:306 */     return getNivelDimension(this.dimension);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public LazyDataModel<DimensionContable> getListaDimensionContable()
/* 245:    */   {
/* 246:310 */     return this.listaDimensionContable;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setListaDimensionContable(LazyDataModel<DimensionContable> listaDimensionContable)
/* 250:    */   {
/* 251:314 */     this.listaDimensionContable = listaDimensionContable;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public ServicioDimensionContable getServicioDimensionContable()
/* 255:    */   {
/* 256:318 */     return this.servicioDimensionContable;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setServicioDimensionContable(ServicioDimensionContable servicioDimensionContable)
/* 260:    */   {
/* 261:322 */     this.servicioDimensionContable = servicioDimensionContable;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<TipoAccesoContable> getListaTipoAccesoContable()
/* 265:    */   {
/* 266:326 */     if (this.listaTipoAccesoContable == null)
/* 267:    */     {
/* 268:327 */       this.listaTipoAccesoContable = new ArrayList();
/* 269:328 */       for (TipoAccesoContable tipoAccesoContable : TipoAccesoContable.values()) {
/* 270:329 */         this.listaTipoAccesoContable.add(tipoAccesoContable);
/* 271:    */       }
/* 272:    */     }
/* 273:332 */     return this.listaTipoAccesoContable;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String getDimension()
/* 277:    */   {
/* 278:341 */     return this.dimension;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setDimension(String dimension)
/* 282:    */   {
/* 283:351 */     this.dimension = dimension;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public String getMascara()
/* 287:    */   {
/* 288:355 */     if (this.dimensionContable.getMascara() != null) {
/* 289:356 */       if (this.dimensionContable.getDimensionPadre() != null) {
/* 290:358 */         this.mascara = (this.dimensionContable.getDimensionPadre().getCodigo() + this.dimensionContable.getMascara().substring(this.dimensionContable.getDimensionPadre().getCodigo().length()));
/* 291:    */       } else {
/* 292:360 */         this.mascara = this.dimensionContable.getMascara();
/* 293:    */       }
/* 294:    */     }
/* 295:363 */     return this.mascara;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public CuentaContable getCuentaContable()
/* 299:    */   {
/* 300:367 */     return this.cuentaContable;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 304:    */   {
/* 305:371 */     this.cuentaContable = cuentaContable;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public DataTable getDtCuentaContable()
/* 309:    */   {
/* 310:375 */     return this.dtCuentaContable;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 314:    */   {
/* 315:379 */     this.dtCuentaContable = dtCuentaContable;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 319:    */   {
/* 320:383 */     return this.listaCuentaContableBean;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 324:    */   {
/* 325:387 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List<CuentaContableDimensionContable> getListaCuentaContables()
/* 329:    */   {
/* 330:391 */     List<CuentaContableDimensionContable> lista = new ArrayList();
/* 331:392 */     for (CuentaContableDimensionContable cuentaContableDimensionContable : getDimensionContable().getListaCuentaContableDimensionContable()) {
/* 332:393 */       if (!cuentaContableDimensionContable.isEliminado()) {
/* 333:394 */         lista.add(cuentaContableDimensionContable);
/* 334:    */       }
/* 335:    */     }
/* 336:396 */     return lista;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public String eliminarDetalle()
/* 340:    */   {
/* 341:400 */     this.cuentaContableDimensionContable = ((CuentaContableDimensionContable)this.dtCuentaContable.getRowData());
/* 342:401 */     this.cuentaContableDimensionContable.setEliminado(true);
/* 343:402 */     return "";
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void verificaDimension()
/* 347:    */   {
/* 348:406 */     OrganizacionConfiguracion aux = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/* 349:407 */     this.agregaCC = false;
/* 350:408 */     if (("1".equals(getDimension())) && (!aux.getNombreDimension1().equals("")) && (aux.isIndicadorUsoPresupuestoDimension1())) {
/* 351:409 */       this.agregaCC = true;
/* 352:410 */     } else if (("2".equals(getDimension())) && (!aux.getNombreDimension2().equals("")) && (aux.isIndicadorUsoPresupuestoDimension2())) {
/* 353:411 */       this.agregaCC = true;
/* 354:412 */     } else if (("3".equals(getDimension())) && (!aux.getNombreDimension3().equals("")) && (aux.isIndicadorUsoPresupuestoDimension3())) {
/* 355:413 */       this.agregaCC = true;
/* 356:414 */     } else if (("4".equals(getDimension())) && (!aux.getNombreDimension4().equals("")) && (aux.isIndicadorUsoPresupuestoDimension4())) {
/* 357:415 */       this.agregaCC = true;
/* 358:416 */     } else if (("5".equals(getDimension())) && (!aux.getNombreDimension5().equals("")) && (aux.isIndicadorUsoPresupuestoDimension5())) {
/* 359:417 */       this.agregaCC = true;
/* 360:    */     }
/* 361:    */   }
/* 362:    */   
/* 363:    */   public boolean isAgregaCC()
/* 364:    */   {
/* 365:422 */     return this.agregaCC;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setAgregaCC(boolean agregaCC)
/* 369:    */   {
/* 370:426 */     this.agregaCC = agregaCC;
/* 371:    */   }
/* 372:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.DimensionContableBean
 * JD-Core Version:    0.7.0.1
 */