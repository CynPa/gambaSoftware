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
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*  17:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Calendar;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.primefaces.component.datatable.DataTable;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class InclusionAutorizacionAutoimpresorSRIBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 8782410859351300688L;
/*  38:    */   @EJB
/*  39:    */   private ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  40:    */   @EJB
/*  41:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  42:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  43:    */   private DocumentoBase documentoBase;
/*  44:    */   private PuntoDeVenta puntoDeVenta;
/*  45:    */   private Date fecha;
/*  46:    */   private List<DocumentoBase> listaDocumentoBase;
/*  47:    */   private List<PuntoDeVenta> listaPuntoDeVenta;
/*  48:    */   private Map<DocumentoBase, DocumentoBase> hmDocumentoBase;
/*  49:    */   private Map<Integer, PuntoDeVenta> hmPuntoDeVenta;
/*  50: 82 */   private Map<String, AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> hmDocumentoPuntoDeVenta = new HashMap();
/*  51:    */   private DataTable dtDocumentoBaseNoAsignados;
/*  52:    */   private DataTable dtAutorizacionDocumentoAutoimpresorSRI;
/*  53:    */   private DataTable dtPuntoDeVentaNoAsignados;
/*  54:    */   private DataTable dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/*  55:    */   private DataTable dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  56:    */   
/*  57:    */   @PostConstruct
/*  58:    */   public void init()
/*  59:    */   {
/*  60:    */     try
/*  61:    */     {
/*  62:101 */       setFecha(Calendar.getInstance().getTime());
/*  63:    */       
/*  64:103 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.obtenerAutorizacionSRIVigente(AppUtil.getOrganizacion()
/*  65:104 */         .getIdOrganizacion());
/*  66:    */       
/*  67:106 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.cargarDetalle(this.autorizacionAutoimpresorSRI.getId());
/*  68:107 */       cargarAutorizacionDocumentoPuntoDeVentaSRI();
/*  69:108 */       setEditado(true);
/*  70:    */     }
/*  71:    */     catch (ExcepcionAS2Financiero e)
/*  72:    */     {
/*  73:110 */       addErrorMessage(getLanguageController().getMensaje(e
/*  74:111 */         .getCodigoExcepcion()));
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void crearEntidad() {}
/*  79:    */   
/*  80:    */   public void agregarDocumentoBase()
/*  81:    */   {
/*  82:127 */     AutorizacionDocumentoAutoimpresorSRI a = new AutorizacionDocumentoAutoimpresorSRI();
/*  83:128 */     a.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  84:129 */     a.setIdSucursal(AppUtil.getSucursal().getId());
/*  85:130 */     a.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/*  86:131 */     a.setActivo(true);
/*  87:132 */     a.setDocumentoBase(this.documentoBase);
/*  88:133 */     this.autorizacionAutoimpresorSRI
/*  89:134 */       .getListaAutorizacionDocumentoAutoimpresorSRI().add(a);
/*  90:135 */     this.hmDocumentoBase.remove(a.getDocumentoBase());
/*  91:136 */     generarAutorizacionDocumentoPuntoDeVentaSRI(this.documentoBase, null);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<DocumentoBase> getListaDocumentoBaseNoAsignados()
/*  95:    */   {
/*  96:140 */     if (this.listaDocumentoBase == null)
/*  97:    */     {
/*  98:141 */       this.listaDocumentoBase = new ArrayList();
/*  99:142 */       this.listaDocumentoBase.add(DocumentoBase.FACTURA_CLIENTE);
/* 100:143 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 101:144 */       this.hmDocumentoBase = new HashMap();
/* 102:145 */       for (DocumentoBase db : this.listaDocumentoBase) {
/* 103:146 */         this.hmDocumentoBase.put(db, db);
/* 104:    */       }
/* 105:148 */       for (AutorizacionDocumentoAutoimpresorSRI a : this.autorizacionAutoimpresorSRI
/* 106:149 */         .getListaAutorizacionDocumentoAutoimpresorSRI()) {
/* 107:150 */         this.hmDocumentoBase.remove(a.getDocumentoBase());
/* 108:    */       }
/* 109:    */     }
/* 110:155 */     return new ArrayList(this.hmDocumentoBase.values());
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void agregarPuntoDeVenta()
/* 114:    */   {
/* 115:159 */     AutorizacionPuntoDeVentaAutoimpresorSRI a = new AutorizacionPuntoDeVentaAutoimpresorSRI();
/* 116:160 */     a.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 117:161 */     a.setIdSucursal(AppUtil.getSucursal().getId());
/* 118:162 */     a.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/* 119:163 */     a.setActivo(true);
/* 120:164 */     a.setPuntoDeVenta(this.puntoDeVenta);
/* 121:165 */     this.autorizacionAutoimpresorSRI
/* 122:166 */       .getListaAutorizacionPuntoDeVentaAutoimpresorSRI().add(a);
/* 123:167 */     this.hmPuntoDeVenta.remove(Integer.valueOf(a.getPuntoDeVenta().getId()));
/* 124:168 */     generarAutorizacionDocumentoPuntoDeVentaSRI(null, this.puntoDeVenta);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<PuntoDeVenta> getListaPuntoDeVentaNoAsignados()
/* 128:    */   {
/* 129:172 */     if (this.listaPuntoDeVenta == null)
/* 130:    */     {
/* 131:173 */       this.listaPuntoDeVenta = this.servicioPuntoDeVenta.obtenerListaCombo("sucursal.codigo", true, null);
/* 132:    */       
/* 133:175 */       this.hmPuntoDeVenta = new HashMap();
/* 134:176 */       for (PuntoDeVenta p : this.listaPuntoDeVenta) {
/* 135:177 */         this.hmPuntoDeVenta.put(Integer.valueOf(p.getId()), p);
/* 136:    */       }
/* 137:179 */       for (AutorizacionPuntoDeVentaAutoimpresorSRI a : this.autorizacionAutoimpresorSRI
/* 138:180 */         .getListaAutorizacionPuntoDeVentaAutoimpresorSRI()) {
/* 139:181 */         this.hmPuntoDeVenta.remove(Integer.valueOf(a.getPuntoDeVenta().getId()));
/* 140:    */       }
/* 141:    */     }
/* 142:186 */     return new ArrayList(this.hmPuntoDeVenta.values());
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void cargarAutorizacionDocumentoPuntoDeVentaSRI()
/* 146:    */   {
/* 147:190 */     String clave = "";
/* 148:191 */     for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv : this.autorizacionAutoimpresorSRI
/* 149:192 */       .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI())
/* 150:    */     {
/* 151:194 */       clave = adpv.getDocumentoBase().ordinal() + "¤" + adpv.getPuntoDeVenta().getId();
/* 152:195 */       this.hmDocumentoPuntoDeVenta.put(clave, adpv);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void generarAutorizacionDocumentoPuntoDeVentaSRI(DocumentoBase documentoBase, PuntoDeVenta puntoDeVenta)
/* 157:    */   {
/* 158:201 */     String clave = "";
/* 159:202 */     if (documentoBase != null) {
/* 160:203 */       for (AutorizacionPuntoDeVentaAutoimpresorSRI apv : this.autorizacionAutoimpresorSRI
/* 161:204 */         .getListaAutorizacionPuntoDeVentaAutoimpresorSRI())
/* 162:    */       {
/* 163:206 */         clave = documentoBase.ordinal() + "¤" + apv.getPuntoDeVenta().getId();
/* 164:207 */         if ((!this.hmDocumentoPuntoDeVenta.containsKey(clave)) && 
/* 165:208 */           (apv.isActivo()))
/* 166:    */         {
/* 167:209 */           AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI a = new AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI();
/* 168:210 */           a.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/* 169:211 */           a.setPuntoDeVenta(apv.getPuntoDeVenta());
/* 170:212 */           a.setDocumentoBase(documentoBase);
/* 171:213 */           a.setIndicadorNuevo(false);
/* 172:214 */           a.setActivo(true);
/* 173:215 */           a.setFechaInclusion(this.fecha);
/* 174:216 */           a.setNumero(1);
/* 175:217 */           this.autorizacionAutoimpresorSRI
/* 176:218 */             .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/* 177:219 */             .add(a);
/* 178:220 */           this.hmDocumentoPuntoDeVenta.put(clave, a);
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:225 */     if (puntoDeVenta != null) {
/* 183:227 */       for (AutorizacionDocumentoAutoimpresorSRI ad : this.autorizacionAutoimpresorSRI
/* 184:228 */         .getListaAutorizacionDocumentoAutoimpresorSRI())
/* 185:    */       {
/* 186:230 */         clave = ad.getDocumentoBase().ordinal() + "¤" + puntoDeVenta.getId();
/* 187:231 */         if ((!this.hmDocumentoPuntoDeVenta.containsKey(clave)) && 
/* 188:232 */           (ad.isActivo()))
/* 189:    */         {
/* 190:233 */           AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI a = new AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI();
/* 191:234 */           a.setAutorizacionAutoimpresorSRI(this.autorizacionAutoimpresorSRI);
/* 192:235 */           a.setPuntoDeVenta(puntoDeVenta);
/* 193:236 */           a.setDocumentoBase(ad.getDocumentoBase());
/* 194:237 */           a.setIndicadorNuevo(false);
/* 195:238 */           a.setActivo(true);
/* 196:239 */           a.setFechaInclusion(this.fecha);
/* 197:240 */           a.setNumero(1);
/* 198:241 */           this.autorizacionAutoimpresorSRI
/* 199:242 */             .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/* 200:243 */             .add(a);
/* 201:244 */           this.hmDocumentoPuntoDeVenta.put(clave, a);
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String editar()
/* 208:    */   {
/* 209:257 */     addInfoMessage("msg_accion_no_permitida");
/* 210:258 */     return "";
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String guardar()
/* 214:    */   {
/* 215:    */     try
/* 216:    */     {
/* 217:268 */       this.servicioAutorizacionAutoimpresorSRI.guardar(this.autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum.INCLUSION, this.fecha);
/* 218:    */       
/* 219:    */ 
/* 220:271 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 221:    */       
/* 222:273 */       setEditado(false);
/* 223:274 */       limpiar();
/* 224:    */     }
/* 225:    */     catch (ExcepcionAS2 e)
/* 226:    */     {
/* 227:276 */       addErrorMessage(getLanguageController().getMensaje(e
/* 228:277 */         .getCodigoExcepcion()) + " " + e
/* 229:278 */         .getMessage());
/* 230:279 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 231:    */     }
/* 232:    */     catch (Exception e)
/* 233:    */     {
/* 234:281 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 235:    */       
/* 236:283 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 237:    */     }
/* 238:285 */     return "";
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String eliminar()
/* 242:    */   {
/* 243:294 */     addInfoMessage("msg_accion_no_permitida");
/* 244:295 */     return "";
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String cargarDatos()
/* 248:    */   {
/* 249:304 */     return "";
/* 250:    */   }
/* 251:    */   
/* 252:    */   public String limpiar()
/* 253:    */   {
/* 254:313 */     crearEntidad();
/* 255:314 */     return "";
/* 256:    */   }
/* 257:    */   
/* 258:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/* 259:    */   {
/* 260:331 */     if (this.autorizacionAutoimpresorSRI == null) {
/* 261:332 */       crearEntidad();
/* 262:    */     }
/* 263:334 */     return this.autorizacionAutoimpresorSRI;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 267:    */   {
/* 268:345 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public DataTable getDtDocumentoBaseNoAsignados()
/* 272:    */   {
/* 273:349 */     return this.dtDocumentoBaseNoAsignados;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setDtDocumentoBaseNoAsignados(DataTable dtDocumentoBaseNoAsignados)
/* 277:    */   {
/* 278:354 */     this.dtDocumentoBaseNoAsignados = dtDocumentoBaseNoAsignados;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public DataTable getDtAutorizacionDocumentoAutoimpresorSRI()
/* 282:    */   {
/* 283:358 */     return this.dtAutorizacionDocumentoAutoimpresorSRI;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setDtAutorizacionDocumentoAutoimpresorSRI(DataTable dtAutorizacionDocumentoAutoimpresorSRI)
/* 287:    */   {
/* 288:363 */     this.dtAutorizacionDocumentoAutoimpresorSRI = dtAutorizacionDocumentoAutoimpresorSRI;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public DocumentoBase getDocumentoBase()
/* 292:    */   {
/* 293:367 */     return this.documentoBase;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 297:    */   {
/* 298:371 */     this.documentoBase = documentoBase;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public PuntoDeVenta getPuntoDeVenta()
/* 302:    */   {
/* 303:375 */     return this.puntoDeVenta;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 307:    */   {
/* 308:379 */     this.puntoDeVenta = puntoDeVenta;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public DataTable getDtPuntoDeVentaNoAsignados()
/* 312:    */   {
/* 313:383 */     return this.dtPuntoDeVentaNoAsignados;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setDtPuntoDeVentaNoAsignados(DataTable dtPuntoDeVentaNoAsignados)
/* 317:    */   {
/* 318:387 */     this.dtPuntoDeVentaNoAsignados = dtPuntoDeVentaNoAsignados;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public DataTable getDtAutorizacionPuntoDeVentaAutoimpresorSRI()
/* 322:    */   {
/* 323:391 */     return this.dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setDtAutorizacionPuntoDeVentaAutoimpresorSRI(DataTable dtAutorizacionPuntoDeVentaAutoimpresorSRI)
/* 327:    */   {
/* 328:396 */     this.dtAutorizacionPuntoDeVentaAutoimpresorSRI = dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public DataTable getDtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/* 332:    */   {
/* 333:400 */     return this.dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setDtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(DataTable dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)
/* 337:    */   {
/* 338:405 */     this.dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI = dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public Date getFecha()
/* 342:    */   {
/* 343:409 */     return this.fecha;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setFecha(Date fecha)
/* 347:    */   {
/* 348:413 */     this.fecha = fecha;
/* 349:    */   }
/* 350:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.autoimpresor.InclusionAutorizacionAutoimpresorSRIBean
 * JD-Core Version:    0.7.0.1
 */