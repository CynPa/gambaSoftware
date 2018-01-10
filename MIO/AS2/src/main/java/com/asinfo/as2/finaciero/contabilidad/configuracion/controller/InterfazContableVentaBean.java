/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Asiento;
/*   7:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Calendar;
/*  22:    */ import java.util.Collection;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.faces.context.FacesContext;
/*  30:    */ import javax.faces.context.PartialViewContext;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ import org.primefaces.component.datatable.DataTable;
/*  34:    */ import org.primefaces.context.RequestContext;
/*  35:    */ import org.primefaces.model.LazyDataModel;
/*  36:    */ import org.primefaces.model.SortOrder;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class InterfazContableVentaBean
/*  41:    */   extends PageControllerAS2
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = -8914881290932226380L;
/*  44:    */   @EJB
/*  45:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  46:    */   @EJB
/*  47:    */   private ServicioDocumento servicioDocumento;
/*  48:    */   private BigDecimal debe;
/*  49:    */   private BigDecimal haber;
/*  50:    */   private InterfazContableProceso interfazContableProceso;
/*  51:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  52:    */   private List<Documento> listaDocumento;
/*  53:    */   private List<SelectItem> listaDocumentoBase;
/*  54:    */   private DataTable dtDetalleAsiento;
/*  55:    */   private DataTable dtInterfazContableProceso;
/*  56: 86 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  57:    */   
/*  58:    */   @PostConstruct
/*  59:    */   public void init()
/*  60:    */   {
/*  61: 91 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  62:    */     {
/*  63:    */       private static final long serialVersionUID = 763093382591716471L;
/*  64:    */       
/*  65:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  66:    */       {
/*  67:103 */         List<InterfazContableProceso> lista = new ArrayList();
/*  68:    */         
/*  69:105 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  70:106 */         filters.put("documentoBase", String.valueOf(DocumentoBase.INTERFAZ_VENTAS));
/*  71:    */         
/*  72:108 */         lista = InterfazContableVentaBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  73:109 */         InterfazContableVentaBean.this.listaInterfazContableProceso.setRowCount(InterfazContableVentaBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  74:110 */         return lista;
/*  75:    */       }
/*  76:    */     };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String crear()
/*  80:    */   {
/*  81:122 */     limpiar();
/*  82:123 */     setEditado(true);
/*  83:    */     
/*  84:125 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String editar()
/*  88:    */   {
/*  89:135 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  90:136 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String guardar()
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:148 */       if (this.interfazContableProceso.getAsiento().getListaDetalleAsiento().isEmpty())
/*  98:    */       {
/*  99:149 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:153 */         this.servicioInterfazContableProceso.guardar(this.interfazContableProceso);
/* 104:    */         
/* 105:155 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 106:    */         
/* 107:157 */         cargarDatos();
/* 108:    */       }
/* 109:    */     }
/* 110:    */     catch (ExcepcionAS2Financiero e)
/* 111:    */     {
/* 112:161 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 113:    */     }
/* 114:    */     catch (ExcepcionAS2 e)
/* 115:    */     {
/* 116:164 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 117:    */     }
/* 118:167 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String eliminar()
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:180 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 126:181 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 127:182 */       cargarDatos();
/* 128:    */     }
/* 129:    */     catch (ExcepcionAS2Ventas e)
/* 130:    */     {
/* 131:185 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 132:    */     }
/* 133:    */     catch (ExcepcionAS2Financiero e)
/* 134:    */     {
/* 135:188 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 136:    */     }
/* 137:    */     catch (ExcepcionAS2 e)
/* 138:    */     {
/* 139:191 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 140:192 */       LOG.info(e);
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:194 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 145:195 */       LOG.error(e);
/* 146:    */     }
/* 147:198 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String limpiar()
/* 151:    */   {
/* 152:208 */     setEditado(false);
/* 153:209 */     this.interfazContableProceso = new InterfazContableProceso();
/* 154:210 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 155:211 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 156:212 */     this.interfazContableProceso.setFechaDesde(Calendar.getInstance().getTime());
/* 157:213 */     this.interfazContableProceso.setFechaHasta(Calendar.getInstance().getTime());
/* 158:214 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 159:215 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.INTERFAZ_VENTAS);
/* 160:216 */     this.interfazContableProceso.setAsiento(new Asiento());
/* 161:217 */     this.interfazContableProceso.setFiltroDocumentoBase(DocumentoBase.FACTURA_CLIENTE);
/* 162:218 */     cargarListaDocumento();
/* 163:219 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String preliminarInterfazProceso()
/* 167:    */   {
/* 168:    */     try
/* 169:    */     {
/* 170:229 */       if (isIndicadorAutoimpresor()) {
/* 171:230 */         this.interfazContableProceso.setFechaHasta(this.interfazContableProceso.getFechaDesde());
/* 172:    */       }
/* 173:232 */       if (getInterfazContableProceso().getFiltroDocumento() == null) {
/* 174:233 */         this.interfazContableProceso.setFiltroDocumento(new Documento());
/* 175:    */       }
/* 176:236 */       this.interfazContableProceso.setAsiento(this.servicioInterfazContableProceso.generarAsiento(this.interfazContableProceso));
/* 177:237 */       calcular();
/* 178:    */     }
/* 179:    */     catch (ExcepcionAS2Financiero e)
/* 180:    */     {
/* 181:240 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 182:241 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 183:242 */       e.printStackTrace();
/* 184:    */     }
/* 185:    */     catch (ExcepcionAS2 e)
/* 186:    */     {
/* 187:244 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 188:245 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 189:246 */       e.printStackTrace();
/* 190:    */     }
/* 191:    */     catch (AS2Exception e)
/* 192:    */     {
/* 193:248 */       e.printStackTrace();
/* 194:249 */       this.exContabilizacion = e;
/* 195:250 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 196:251 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 197:    */     }
/* 198:    */     catch (Exception e)
/* 199:    */     {
/* 200:253 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 201:254 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 202:    */     }
/* 203:256 */     return "";
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String cargarDatos()
/* 207:    */   {
/* 208:266 */     setEditado(false);
/* 209:267 */     limpiar();
/* 210:268 */     return null;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 214:    */   {
/* 215:277 */     List<DetalleAsiento> lista = new ArrayList();
/* 216:278 */     if (this.interfazContableProceso.getAsiento() != null) {
/* 217:279 */       for (DetalleAsiento da : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 218:281 */         if (!da.isEliminado()) {
/* 219:282 */           lista.add(da);
/* 220:    */         }
/* 221:    */       }
/* 222:    */     }
/* 223:286 */     return lista;
/* 224:    */   }
/* 225:    */   
/* 226:    */   private void calcular()
/* 227:    */   {
/* 228:293 */     this.debe = BigDecimal.ZERO;
/* 229:294 */     this.haber = BigDecimal.ZERO;
/* 230:296 */     for (DetalleAsiento d : this.interfazContableProceso.getAsiento().getListaDetalleAsiento()) {
/* 231:297 */       if (!d.isEliminado())
/* 232:    */       {
/* 233:298 */         this.haber = this.haber.add(d.getHaber());
/* 234:299 */         this.debe = this.debe.add(d.getDebe());
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void cargarListaDocumento()
/* 240:    */   {
/* 241:    */     try
/* 242:    */     {
/* 243:306 */       this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(getInterfazContableProceso().getFiltroDocumentoBase());
/* 244:    */     }
/* 245:    */     catch (ExcepcionAS2 e)
/* 246:    */     {
/* 247:308 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public BigDecimal getDebe()
/* 252:    */   {
/* 253:323 */     if (this.debe == null) {
/* 254:324 */       calcular();
/* 255:    */     }
/* 256:326 */     return this.debe;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setDebe(BigDecimal debe)
/* 260:    */   {
/* 261:330 */     this.debe = debe;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public BigDecimal getHaber()
/* 265:    */   {
/* 266:334 */     if (this.haber == null) {
/* 267:335 */       calcular();
/* 268:    */     }
/* 269:337 */     return this.haber;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setHaber(BigDecimal haber)
/* 273:    */   {
/* 274:341 */     this.haber = haber;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public List<Documento> getListaDocumento()
/* 278:    */   {
/* 279:350 */     if (this.listaDocumento == null) {
/* 280:351 */       this.listaDocumento = new ArrayList();
/* 281:    */     }
/* 282:353 */     return this.listaDocumento;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 286:    */   {
/* 287:363 */     this.listaDocumento = listaDocumento;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public List<SelectItem> getListaDocumentoBase()
/* 291:    */   {
/* 292:372 */     if (this.listaDocumentoBase == null)
/* 293:    */     {
/* 294:373 */       this.listaDocumentoBase = new ArrayList();
/* 295:374 */       SelectItem item1 = new SelectItem(DocumentoBase.FACTURA_CLIENTE, DocumentoBase.FACTURA_CLIENTE.getNombre());
/* 296:375 */       SelectItem item2 = new SelectItem(DocumentoBase.NOTA_DEBITO_CLIENTE, DocumentoBase.NOTA_DEBITO_CLIENTE.getNombre());
/* 297:376 */       this.listaDocumentoBase.add(item1);
/* 298:377 */       this.listaDocumentoBase.add(item2);
/* 299:    */     }
/* 300:379 */     return this.listaDocumentoBase;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setListaDocumentoBase(List<SelectItem> listaDocumentoBase)
/* 304:    */   {
/* 305:389 */     this.listaDocumentoBase = listaDocumentoBase;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public InterfazContableProceso getInterfazContableProceso()
/* 309:    */   {
/* 310:393 */     return this.interfazContableProceso;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 314:    */   {
/* 315:397 */     this.interfazContableProceso = interfazContableProceso;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 319:    */   {
/* 320:401 */     return this.listaInterfazContableProceso;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 324:    */   {
/* 325:405 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public DataTable getDtDetalleAsiento()
/* 329:    */   {
/* 330:409 */     return this.dtDetalleAsiento;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 334:    */   {
/* 335:413 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public DataTable getDtInterfazContableProceso()
/* 339:    */   {
/* 340:417 */     return this.dtInterfazContableProceso;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 344:    */   {
/* 345:421 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public AS2Exception getExContabilizacion()
/* 349:    */   {
/* 350:430 */     return this.exContabilizacion;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 354:    */   {
/* 355:440 */     this.exContabilizacion = exContabilizacion;
/* 356:    */   }
/* 357:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.InterfazContableVentaBean
 * JD-Core Version:    0.7.0.1
 */