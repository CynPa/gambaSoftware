/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos.autoimpresor;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
/*  10:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoAutoimpresorSRI;
/*  11:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  12:    */ import com.asinfo.as2.entities.sri.AutorizacionPuntoDeVentaAutoimpresorSRI;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum;
/*  15:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.component.datatable.DataTable;
/*  28:    */ import org.primefaces.model.LazyDataModel;
/*  29:    */ import org.primefaces.model.SortOrder;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class AutorizacionAutoimpresorSRIBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 8782410859351300688L;
/*  37:    */   @EJB
/*  38:    */   private ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  39:    */   @EJB
/*  40:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  41:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  42:    */   private DocumentoBase documentoBase;
/*  43:    */   private PuntoDeVenta puntoDeVenta;
/*  44:    */   private LazyDataModel<AutorizacionAutoimpresorSRI> listaAutorizacionAutoimpresorSRI;
/*  45:    */   private List<DocumentoBase> listaDocumentoBase;
/*  46:    */   private List<PuntoDeVenta> listaPuntoDeVenta;
/*  47:    */   private Map<DocumentoBase, DocumentoBase> hmDocumentoBase;
/*  48:    */   private Map<Integer, PuntoDeVenta> hmPuntoDeVenta;
/*  49: 81 */   private Map<String, AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> hmDocumentoPuntoDeVenta = new HashMap();
/*  50:    */   private DataTable dtAutorizacionAutoimpresorSRI;
/*  51:    */   private DataTable dtDocumentoBaseNoAsignados;
/*  52:    */   private DataTable dtAutorizacionDocumentoAutoimpresorSRI;
/*  53:    */   private DataTable dtPuntoDeVentaNoAsignados;
/*  54:    */   private DataTable dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/*  55:    */   private DataTable dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  56:    */   
/*  57:    */   @PostConstruct
/*  58:    */   public void init()
/*  59:    */   {
/*  60: 98 */     this.listaAutorizacionAutoimpresorSRI = new LazyDataModel()
/*  61:    */     {
/*  62:    */       private static final long serialVersionUID = 1L;
/*  63:    */       
/*  64:    */       public List<AutorizacionAutoimpresorSRI> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  65:    */       {
/*  66:106 */         List<AutorizacionAutoimpresorSRI> lista = new ArrayList();
/*  67:107 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  68:    */         
/*  69:109 */         lista = AutorizacionAutoimpresorSRIBean.this.servicioAutorizacionAutoimpresorSRI.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  70:    */         
/*  71:111 */         AutorizacionAutoimpresorSRIBean.this.listaAutorizacionAutoimpresorSRI.setRowCount(AutorizacionAutoimpresorSRIBean.this.servicioAutorizacionAutoimpresorSRI.contarPorCriterio(filters));
/*  72:112 */         return lista;
/*  73:    */       }
/*  74:    */     };
/*  75:    */   }
/*  76:    */   
/*  77:    */   private void crearEntidad()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:127 */       this.servicioAutorizacionAutoimpresorSRI.obtenerAutorizacionSRIVigente(AppUtil.getOrganizacion().getIdOrganizacion());
/*  82:128 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  83:129 */       setEditado(false);
/*  84:    */     }
/*  85:    */     catch (ExcepcionAS2Financiero e)
/*  86:    */     {
/*  87:131 */       this.autorizacionAutoimpresorSRI = new AutorizacionAutoimpresorSRI();
/*  88:132 */       this.autorizacionAutoimpresorSRI.setActivo(true);
/*  89:133 */       this.autorizacionAutoimpresorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  90:134 */       this.autorizacionAutoimpresorSRI.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  91:135 */       setEditado(true);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void agregarDocumentoBase()
/*  96:    */   {
/*  97:141 */     AutorizacionDocumentoAutoimpresorSRI a = new AutorizacionDocumentoAutoimpresorSRI();
/*  98:142 */     a.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  99:143 */     a.setIdSucursal(AppUtil.getSucursal().getId());
/* 100:144 */     a.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/* 101:145 */     a.setActivo(true);
/* 102:146 */     a.setDocumentoBase(this.documentoBase);
/* 103:147 */     this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoAutoimpresorSRI().add(a);
/* 104:148 */     this.hmDocumentoBase.remove(a.getDocumentoBase());
/* 105:149 */     generarAutorizacionDocumentoPuntoDeVentaSRI(this.documentoBase, null);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public List<DocumentoBase> getListaDocumentoBaseNoAsignados()
/* 109:    */   {
/* 110:153 */     if (this.listaDocumentoBase == null)
/* 111:    */     {
/* 112:154 */       this.listaDocumentoBase = new ArrayList();
/* 113:155 */       this.listaDocumentoBase.add(DocumentoBase.FACTURA_CLIENTE);
/* 114:156 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 115:157 */       this.hmDocumentoBase = new HashMap();
/* 116:158 */       for (DocumentoBase db : this.listaDocumentoBase) {
/* 117:159 */         this.hmDocumentoBase.put(db, db);
/* 118:    */       }
/* 119:161 */       for (AutorizacionDocumentoAutoimpresorSRI a : this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoAutoimpresorSRI()) {
/* 120:162 */         this.hmDocumentoBase.remove(a.getDocumentoBase());
/* 121:    */       }
/* 122:    */     }
/* 123:167 */     return new ArrayList(this.hmDocumentoBase.values());
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void agregarPuntoDeVenta()
/* 127:    */   {
/* 128:171 */     AutorizacionPuntoDeVentaAutoimpresorSRI a = new AutorizacionPuntoDeVentaAutoimpresorSRI();
/* 129:172 */     a.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 130:173 */     a.setIdSucursal(AppUtil.getSucursal().getId());
/* 131:174 */     a.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/* 132:175 */     a.setActivo(true);
/* 133:176 */     a.setPuntoDeVenta(this.puntoDeVenta);
/* 134:177 */     this.autorizacionAutoimpresorSRI.getListaAutorizacionPuntoDeVentaAutoimpresorSRI().add(a);
/* 135:178 */     this.hmPuntoDeVenta.remove(Integer.valueOf(a.getPuntoDeVenta().getId()));
/* 136:179 */     generarAutorizacionDocumentoPuntoDeVentaSRI(null, this.puntoDeVenta);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<PuntoDeVenta> getListaPuntoDeVentaNoAsignados()
/* 140:    */   {
/* 141:183 */     if (this.listaPuntoDeVenta == null)
/* 142:    */     {
/* 143:184 */       this.listaPuntoDeVenta = this.servicioPuntoDeVenta.obtenerListaCombo("sucursal.codigo", true, null);
/* 144:185 */       this.hmPuntoDeVenta = new HashMap();
/* 145:186 */       for (PuntoDeVenta p : this.listaPuntoDeVenta) {
/* 146:187 */         this.hmPuntoDeVenta.put(Integer.valueOf(p.getId()), p);
/* 147:    */       }
/* 148:189 */       for (AutorizacionPuntoDeVentaAutoimpresorSRI a : this.autorizacionAutoimpresorSRI.getListaAutorizacionPuntoDeVentaAutoimpresorSRI()) {
/* 149:190 */         this.hmPuntoDeVenta.remove(Integer.valueOf(a.getPuntoDeVenta().getId()));
/* 150:    */       }
/* 151:    */     }
/* 152:195 */     return new ArrayList(this.hmPuntoDeVenta.values());
/* 153:    */   }
/* 154:    */   
/* 155:    */   private void cargarAutorizacionDocumentoPuntoDeVentaSRI()
/* 156:    */   {
/* 157:199 */     String clave = "";
/* 158:200 */     for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv : this.autorizacionAutoimpresorSRI
/* 159:201 */       .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI())
/* 160:    */     {
/* 161:202 */       clave = adpv.getDocumentoBase().ordinal() + "¤" + adpv.getPuntoDeVenta().getId();
/* 162:203 */       this.hmDocumentoPuntoDeVenta.put(clave, adpv);
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   private void generarAutorizacionDocumentoPuntoDeVentaSRI(DocumentoBase documentoBase, PuntoDeVenta puntoDeVenta)
/* 167:    */   {
/* 168:208 */     String clave = "";
/* 169:209 */     if (documentoBase != null) {
/* 170:210 */       for (AutorizacionPuntoDeVentaAutoimpresorSRI apv : this.autorizacionAutoimpresorSRI.getListaAutorizacionPuntoDeVentaAutoimpresorSRI())
/* 171:    */       {
/* 172:211 */         clave = documentoBase.ordinal() + "¤" + apv.getPuntoDeVenta().getId();
/* 173:212 */         if (!this.hmDocumentoPuntoDeVenta.containsKey(clave))
/* 174:    */         {
/* 175:213 */           AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI a = new AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI();
/* 176:214 */           a.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/* 177:215 */           a.setPuntoDeVenta(apv.getPuntoDeVenta());
/* 178:216 */           a.setDocumentoBase(documentoBase);
/* 179:217 */           a.setIndicadorNuevo(true);
/* 180:218 */           a.setActivo(true);
/* 181:219 */           a.setNumero(1);
/* 182:220 */           this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI().add(a);
/* 183:221 */           this.hmDocumentoPuntoDeVenta.put(clave, a);
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:225 */     if (puntoDeVenta != null) {
/* 188:227 */       for (AutorizacionDocumentoAutoimpresorSRI ad : this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoAutoimpresorSRI())
/* 189:    */       {
/* 190:228 */         clave = ad.getDocumentoBase().ordinal() + "¤" + puntoDeVenta.getId();
/* 191:229 */         if (!this.hmDocumentoPuntoDeVenta.containsKey(clave))
/* 192:    */         {
/* 193:230 */           AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI a = new AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI();
/* 194:231 */           a.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/* 195:232 */           a.setPuntoDeVenta(puntoDeVenta);
/* 196:233 */           a.setDocumentoBase(ad.getDocumentoBase());
/* 197:234 */           a.setIndicadorNuevo(true);
/* 198:235 */           a.setActivo(true);
/* 199:236 */           a.setNumero(1);
/* 200:237 */           this.autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI().add(a);
/* 201:238 */           this.hmDocumentoPuntoDeVenta.put(clave, a);
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String editar()
/* 208:    */   {
/* 209:250 */     if (getAutorizacionAutoimpresorSRI().getIdAutorizacionAutoimpresorSRI() > 0)
/* 210:    */     {
/* 211:252 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.cargarDetalle(this.autorizacionAutoimpresorSRI.getId());
/* 212:253 */       cargarAutorizacionDocumentoPuntoDeVentaSRI();
/* 213:254 */       setEditado(true);
/* 214:    */     }
/* 215:    */     else
/* 216:    */     {
/* 217:256 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 218:    */     }
/* 219:258 */     return "";
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String guardar()
/* 223:    */   {
/* 224:    */     try
/* 225:    */     {
/* 226:268 */       this.servicioAutorizacionAutoimpresorSRI.guardar(this.autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum.AUTORIZACION, this.autorizacionAutoimpresorSRI
/* 227:269 */         .getFechaDesde());
/* 228:270 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 229:271 */       setEditado(false);
/* 230:272 */       limpiar();
/* 231:    */     }
/* 232:    */     catch (ExcepcionAS2Financiero e)
/* 233:    */     {
/* 234:274 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 235:275 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 236:    */     }
/* 237:    */     catch (Exception e)
/* 238:    */     {
/* 239:277 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 240:278 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 241:    */     }
/* 242:280 */     return "";
/* 243:    */   }
/* 244:    */   
/* 245:    */   public String eliminar()
/* 246:    */   {
/* 247:    */     try
/* 248:    */     {
/* 249:290 */       this.servicioAutorizacionAutoimpresorSRI.eliminar(this.autorizacionAutoimpresorSRI);
/* 250:291 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 251:    */     }
/* 252:    */     catch (Exception e)
/* 253:    */     {
/* 254:293 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 255:294 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 256:    */     }
/* 257:296 */     return "";
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String cargarDatos()
/* 261:    */   {
/* 262:305 */     return "";
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String crear()
/* 266:    */   {
/* 267:310 */     crearEntidad();
/* 268:    */     
/* 269:312 */     return "";
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String limpiar()
/* 273:    */   {
/* 274:321 */     this.autorizacionAutoimpresorSRI = new AutorizacionAutoimpresorSRI();
/* 275:322 */     return "";
/* 276:    */   }
/* 277:    */   
/* 278:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/* 279:    */   {
/* 280:339 */     if (this.autorizacionAutoimpresorSRI == null) {}
/* 281:341 */     return this.autorizacionAutoimpresorSRI;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 285:    */   {
/* 286:351 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public LazyDataModel<AutorizacionAutoimpresorSRI> getListaAutorizacionAutoimpresorSRI()
/* 290:    */   {
/* 291:360 */     return this.listaAutorizacionAutoimpresorSRI;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setListaAutorizacionAutoimpresorSRI(LazyDataModel<AutorizacionAutoimpresorSRI> listaAutorizacionAutoimpresorSRI)
/* 295:    */   {
/* 296:371 */     this.listaAutorizacionAutoimpresorSRI = listaAutorizacionAutoimpresorSRI;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public DataTable getDtAutorizacionAutoimpresorSRI()
/* 300:    */   {
/* 301:380 */     return this.dtAutorizacionAutoimpresorSRI;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setDtAutorizacionAutoimpresorSRI(DataTable dtAutorizacionAutoimpresorSRI)
/* 305:    */   {
/* 306:390 */     this.dtAutorizacionAutoimpresorSRI = dtAutorizacionAutoimpresorSRI;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public DataTable getDtDocumentoBaseNoAsignados()
/* 310:    */   {
/* 311:394 */     return this.dtDocumentoBaseNoAsignados;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setDtDocumentoBaseNoAsignados(DataTable dtDocumentoBaseNoAsignados)
/* 315:    */   {
/* 316:398 */     this.dtDocumentoBaseNoAsignados = dtDocumentoBaseNoAsignados;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public DataTable getDtAutorizacionDocumentoAutoimpresorSRI()
/* 320:    */   {
/* 321:402 */     return this.dtAutorizacionDocumentoAutoimpresorSRI;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setDtAutorizacionDocumentoAutoimpresorSRI(DataTable dtAutorizacionDocumentoAutoimpresorSRI)
/* 325:    */   {
/* 326:406 */     this.dtAutorizacionDocumentoAutoimpresorSRI = dtAutorizacionDocumentoAutoimpresorSRI;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public DocumentoBase getDocumentoBase()
/* 330:    */   {
/* 331:410 */     return this.documentoBase;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 335:    */   {
/* 336:414 */     this.documentoBase = documentoBase;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public PuntoDeVenta getPuntoDeVenta()
/* 340:    */   {
/* 341:418 */     return this.puntoDeVenta;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 345:    */   {
/* 346:422 */     this.puntoDeVenta = puntoDeVenta;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public DataTable getDtPuntoDeVentaNoAsignados()
/* 350:    */   {
/* 351:426 */     return this.dtPuntoDeVentaNoAsignados;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setDtPuntoDeVentaNoAsignados(DataTable dtPuntoDeVentaNoAsignados)
/* 355:    */   {
/* 356:430 */     this.dtPuntoDeVentaNoAsignados = dtPuntoDeVentaNoAsignados;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public DataTable getDtAutorizacionPuntoDeVentaAutoimpresorSRI()
/* 360:    */   {
/* 361:434 */     return this.dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setDtAutorizacionPuntoDeVentaAutoimpresorSRI(DataTable dtAutorizacionPuntoDeVentaAutoimpresorSRI)
/* 365:    */   {
/* 366:438 */     this.dtAutorizacionPuntoDeVentaAutoimpresorSRI = dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public DataTable getDtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/* 370:    */   {
/* 371:442 */     return this.dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setDtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(DataTable dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)
/* 375:    */   {
/* 376:446 */     this.dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI = dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 377:    */   }
/* 378:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.autoimpresor.AutorizacionAutoimpresorSRIBean
 * JD-Core Version:    0.7.0.1
 */