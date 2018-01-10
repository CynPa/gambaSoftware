/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Banco;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioGarantiaCliente;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  24:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import javax.faces.component.html.HtmlSelectOneMenu;
/*  34:    */ import javax.faces.component.html.HtmlSelectOneRadio;
/*  35:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  36:    */ import javax.faces.model.SelectItem;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ import org.primefaces.component.datatable.DataTable;
/*  39:    */ import org.primefaces.model.LazyDataModel;
/*  40:    */ import org.primefaces.model.SortOrder;
/*  41:    */ 
/*  42:    */ @ManagedBean
/*  43:    */ @ViewScoped
/*  44:    */ public class GarantiaClienteBean
/*  45:    */   extends PageControllerAS2
/*  46:    */ {
/*  47:    */   private static final long serialVersionUID = -2107801723127815798L;
/*  48:    */   @EJB
/*  49:    */   private ServicioGarantiaCliente servicioGarantiaCliente;
/*  50:    */   @EJB
/*  51:    */   private ServicioGenerico<Banco> servicioBanco;
/*  52:    */   @EJB
/*  53:    */   private ServicioEmpresa servicioEmpresa;
/*  54:    */   @EJB
/*  55:    */   private ServicioDocumento servicioDocumento;
/*  56:    */   @EJB
/*  57:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  58:    */   private GarantiaCliente garantiaCliente;
/*  59:    */   private LazyDataModel<GarantiaCliente> listaGarantiaCliente;
/*  60:    */   private boolean indicadorEstadoGarantiaCliente;
/*  61:    */   private DataTable dtGarantiaCliente;
/*  62:    */   private List<SelectItem> listaCargaCombo;
/*  63:    */   private List<Banco> listaBanco;
/*  64:    */   private SelectItem[] listaEstadoGarantiaClienteItem;
/*  65:    */   private List<TipoGarantiaCliente> listaTipoGarantiaCliente;
/*  66:    */   
/*  67:    */   @PostConstruct
/*  68:    */   public void init()
/*  69:    */   {
/*  70:103 */     this.listaGarantiaCliente = new LazyDataModel()
/*  71:    */     {
/*  72:    */       private static final long serialVersionUID = 1L;
/*  73:    */       
/*  74:    */       public List<GarantiaCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  75:    */       {
/*  76:111 */         List<GarantiaCliente> lista = new ArrayList();
/*  77:112 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  78:    */         
/*  79:114 */         lista = GarantiaClienteBean.this.servicioGarantiaCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  80:115 */         GarantiaClienteBean.this.listaGarantiaCliente.setRowCount(GarantiaClienteBean.this.servicioGarantiaCliente.contarPorCriterio(filters));
/*  81:    */         
/*  82:117 */         return lista;
/*  83:    */       }
/*  84:    */     };
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String editar()
/*  88:    */   {
/*  89:130 */     if (getGarantiaCliente().getIdGarantiaCliente() > 0)
/*  90:    */     {
/*  91:131 */       if ((this.garantiaCliente.getEstadoGarantiaCliente() != EstadoGarantiaCliente.PROTESTADO) && 
/*  92:132 */         (this.garantiaCliente.getEstadoGarantiaCliente() != EstadoGarantiaCliente.CONTABILIZADO)) {
/*  93:133 */         setEditado(true);
/*  94:    */       } else {
/*  95:135 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  96:    */       }
/*  97:    */     }
/*  98:    */     else {
/*  99:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 100:    */     }
/* 101:141 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String guardar()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:151 */       this.servicioGarantiaCliente.guardar(this.garantiaCliente);
/* 109:152 */       limpiar();
/* 110:153 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 111:154 */       setEditado(false);
/* 112:    */     }
/* 113:    */     catch (ExcepcionAS2Ventas e)
/* 114:    */     {
/* 115:156 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 116:157 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 117:    */     }
/* 118:    */     catch (ExcepcionAS2Financiero e)
/* 119:    */     {
/* 120:159 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 121:160 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 122:    */     }
/* 123:    */     catch (ExcepcionAS2 e)
/* 124:    */     {
/* 125:162 */       addErrorMessage(e.getMessage());
/* 126:163 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 127:    */     }
/* 128:    */     catch (Exception e)
/* 129:    */     {
/* 130:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 131:166 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 132:    */     }
/* 133:168 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String eliminar()
/* 137:    */   {
/* 138:    */     try
/* 139:    */     {
/* 140:177 */       this.servicioGarantiaCliente.eliminar(this.garantiaCliente);
/* 141:178 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 142:    */     }
/* 143:    */     catch (Exception e)
/* 144:    */     {
/* 145:180 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 146:181 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 147:    */     }
/* 148:183 */     return "";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String cargarDatos()
/* 152:    */   {
/* 153:190 */     return "";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String limpiar()
/* 157:    */   {
/* 158:199 */     crearGarantiaCliente();
/* 159:200 */     this.indicadorEstadoGarantiaCliente = false;
/* 160:201 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void crearGarantiaCliente()
/* 164:    */   {
/* 165:208 */     this.garantiaCliente = new GarantiaCliente();
/* 166:209 */     this.garantiaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 167:210 */     this.garantiaCliente.setIdSucursal(AppUtil.getSucursal().getId());
/* 168:211 */     this.garantiaCliente.setEstadoGarantiaCliente(EstadoGarantiaCliente.REGISTRADO);
/* 169:212 */     this.garantiaCliente.setFechaIngreso(new Date());
/* 170:213 */     this.garantiaCliente.setFechaCobro(new Date());
/* 171:    */   }
/* 172:    */   
/* 173:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 174:    */   {
/* 175:228 */     List<Empresa> lista = this.servicioEmpresa.autocompletarClientes(consulta);
/* 176:229 */     return lista;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<SelectItem> getListaEstadoGarantiaCliente()
/* 180:    */   {
/* 181:233 */     List<SelectItem> lista = new ArrayList();
/* 182:234 */     for (EstadoGarantiaCliente e : EstadoGarantiaCliente.values()) {
/* 183:235 */       if (e != EstadoGarantiaCliente.CONTABILIZADO)
/* 184:    */       {
/* 185:236 */         SelectItem item = new SelectItem(e, e.getNombre());
/* 186:237 */         lista.add(item);
/* 187:    */       }
/* 188:    */     }
/* 189:240 */     return lista;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void cambioEstadoGarantiaCliente(AjaxBehaviorEvent event)
/* 193:    */   {
/* 194:244 */     this.indicadorEstadoGarantiaCliente = false;
/* 195:245 */     String estado = ((HtmlSelectOneMenu)event.getSource()).getValue().toString();
/* 196:246 */     if (estado == EstadoGarantiaCliente.PROTESTADO.name()) {
/* 197:247 */       this.indicadorEstadoGarantiaCliente = true;
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void cambioMotivoResponsable(AjaxBehaviorEvent event)
/* 202:    */   {
/* 203:253 */     this.listaCargaCombo = new ArrayList();
/* 204:254 */     int opcion = Integer.parseInt(((HtmlSelectOneRadio)event.getSource()).getValue().toString());
/* 205:255 */     if (opcion == 1) {
/* 206:    */       try
/* 207:    */       {
/* 208:257 */         for (Documento documento : this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.NOTA_DEBITO_CLIENTE))
/* 209:    */         {
/* 210:258 */           SelectItem item = new SelectItem(Integer.valueOf(documento.getId()), documento.getNombre());
/* 211:259 */           this.listaCargaCombo.add(item);
/* 212:    */         }
/* 213:    */       }
/* 214:    */       catch (ExcepcionAS2 e)
/* 215:    */       {
/* 216:262 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 217:    */       }
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 222:    */   {
/* 223:268 */     List<CuentaBancariaOrganizacion> lista = new ArrayList();
/* 224:269 */     lista = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", true, null);
/* 225:270 */     return lista;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public SelectItem[] getListaEstadoGarantiaClienteFiltro()
/* 229:    */   {
/* 230:279 */     if (this.listaEstadoGarantiaClienteItem == null)
/* 231:    */     {
/* 232:281 */       List<SelectItem> lista = new ArrayList();
/* 233:282 */       lista.add(new SelectItem("", ""));
/* 234:284 */       for (EstadoGarantiaCliente t : EstadoGarantiaCliente.values())
/* 235:    */       {
/* 236:285 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 237:286 */         lista.add(item);
/* 238:    */       }
/* 239:288 */       this.listaEstadoGarantiaClienteItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 240:    */     }
/* 241:291 */     return this.listaEstadoGarantiaClienteItem;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String actualizarFechaCobro()
/* 245:    */   {
/* 246:295 */     Date fechaIngreso = getGarantiaCliente().getFechaIngreso();
/* 247:296 */     int dias = getGarantiaCliente().getDiasCreditoOtorgado();
/* 248:297 */     fechaIngreso = FuncionesUtiles.sumarFechaDiasMeses(fechaIngreso, dias);
/* 249:298 */     getGarantiaCliente().setFechaCobro(fechaIngreso);
/* 250:    */     
/* 251:300 */     return "";
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void actualizarDiasCreditoDateSelect() {}
/* 255:    */   
/* 256:    */   public void actualizarDiasCreditoListener()
/* 257:    */   {
/* 258:316 */     actualizarDiasCredito(getGarantiaCliente().getFechaCobro());
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void actualizarDiasCredito(Date fechaCobro)
/* 262:    */   {
/* 263:320 */     getGarantiaCliente().setFechaCobro(fechaCobro);
/* 264:321 */     Date fechaIngreso = getGarantiaCliente().getFechaIngreso();
/* 265:322 */     int diasCredito = FuncionesUtiles.diferenciasDeFechas(fechaIngreso, fechaCobro);
/* 266:323 */     getGarantiaCliente().setDiasCreditoOtorgado(diasCredito);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public GarantiaCliente getGarantiaCliente()
/* 270:    */   {
/* 271:328 */     if (this.garantiaCliente == null) {
/* 272:329 */       limpiar();
/* 273:    */     }
/* 274:331 */     return this.garantiaCliente;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setGarantiaCliente(GarantiaCliente garantiaCliente)
/* 278:    */   {
/* 279:335 */     this.garantiaCliente = garantiaCliente;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public boolean isIndicadorEstadoGarantiaCliente()
/* 283:    */   {
/* 284:339 */     return this.indicadorEstadoGarantiaCliente;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setIndicadorEstadoGarantiaCliente(boolean indicadorEstadoGarantiaCliente)
/* 288:    */   {
/* 289:343 */     this.indicadorEstadoGarantiaCliente = indicadorEstadoGarantiaCliente;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public LazyDataModel<GarantiaCliente> getListaGarantiaCliente()
/* 293:    */   {
/* 294:347 */     return this.listaGarantiaCliente;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setListaGarantiaCliente(LazyDataModel<GarantiaCliente> listaGarantiaCliente)
/* 298:    */   {
/* 299:351 */     this.listaGarantiaCliente = listaGarantiaCliente;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public DataTable getDtGarantiaCliente()
/* 303:    */   {
/* 304:355 */     return this.dtGarantiaCliente;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setDtGarantiaCliente(DataTable dtGarantiaCliente)
/* 308:    */   {
/* 309:359 */     this.dtGarantiaCliente = dtGarantiaCliente;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public List<SelectItem> getListaCargaCombo()
/* 313:    */   {
/* 314:363 */     return this.listaCargaCombo;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setListaCargaCombo(List<SelectItem> listaCargaCombo)
/* 318:    */   {
/* 319:367 */     this.listaCargaCombo = listaCargaCombo;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public SelectItem[] getListaEstadoGarantiaClienteItem()
/* 323:    */   {
/* 324:371 */     return this.listaEstadoGarantiaClienteItem;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setListaEstadoGarantiaClienteItem(SelectItem[] listaEstadoGarantiaClienteItem)
/* 328:    */   {
/* 329:375 */     this.listaEstadoGarantiaClienteItem = listaEstadoGarantiaClienteItem;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public List<TipoGarantiaCliente> getListaTipoGarantiaCliente()
/* 333:    */   {
/* 334:384 */     if (this.listaTipoGarantiaCliente == null)
/* 335:    */     {
/* 336:385 */       this.listaTipoGarantiaCliente = new ArrayList();
/* 337:386 */       for (TipoGarantiaCliente tipoGarantiaCliente : TipoGarantiaCliente.values()) {
/* 338:387 */         this.listaTipoGarantiaCliente.add(tipoGarantiaCliente);
/* 339:    */       }
/* 340:    */     }
/* 341:390 */     return this.listaTipoGarantiaCliente;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setListaTipoGarantiaCliente(List<TipoGarantiaCliente> listaTipoGarantiaCliente)
/* 345:    */   {
/* 346:400 */     this.listaTipoGarantiaCliente = listaTipoGarantiaCliente;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public List<Banco> getListaBanco()
/* 350:    */   {
/* 351:409 */     if (this.listaBanco == null) {
/* 352:410 */       this.listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/* 353:    */     }
/* 354:412 */     return this.listaBanco;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setListaBanco(List<Banco> listaBanco)
/* 358:    */   {
/* 359:422 */     this.listaBanco = listaBanco;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public boolean getIndicadorCheque()
/* 363:    */   {
/* 364:426 */     return this.garantiaCliente.getTipoGarantiaCliente() == TipoGarantiaCliente.CHEQUE_POSFECHADO;
/* 365:    */   }
/* 366:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.GarantiaClienteBean
 * JD-Core Version:    0.7.0.1
 */