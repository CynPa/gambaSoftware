/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  18:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.ventas.procesos.ErrorCarga;
/*  21:    */ import java.io.BufferedInputStream;
/*  22:    */ import java.io.InputStream;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.Iterator;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.annotation.PostConstruct;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.primefaces.component.datatable.DataTable;
/*  33:    */ import org.primefaces.event.FileUploadEvent;
/*  34:    */ import org.primefaces.event.SelectEvent;
/*  35:    */ import org.primefaces.model.LazyDataModel;
/*  36:    */ import org.primefaces.model.SortOrder;
/*  37:    */ import org.primefaces.model.UploadedFile;
/*  38:    */ 
/*  39:    */ @ManagedBean
/*  40:    */ @ViewScoped
/*  41:    */ public class FacturaProveedorSRIElectronicaBean
/*  42:    */   extends FacturaProveedorSRIBean
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = 2079139990048172552L;
/*  45:    */   protected LazyDataModel<FacturaProveedorSRI> listaFacturaProveedorSRI;
/*  46:    */   protected DataTable dtFacturaProveedorSRI;
/*  47:    */   Empresa empresa;
/*  48:    */   private String mails;
/*  49: 65 */   private List<ErrorCarga> errores = new ArrayList();
/*  50:    */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  51:    */   
/*  52:    */   @PostConstruct
/*  53:    */   public void init()
/*  54:    */   {
/*  55:    */     try
/*  56:    */     {
/*  57: 72 */       limpiar();
/*  58:    */       
/*  59: 74 */       this.listaFacturaProveedorSRI = new LazyDataModel()
/*  60:    */       {
/*  61:    */         private static final long serialVersionUID = 1L;
/*  62:    */         
/*  63:    */         public List<FacturaProveedorSRI> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  64:    */         {
/*  65: 86 */           List<FacturaProveedorSRI> lista = new ArrayList();
/*  66:    */           
/*  67: 88 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  68:    */           
/*  69: 90 */           FacturaProveedorSRIElectronicaBean.this.obtenerFiltros(filters);
/*  70: 91 */           lista = FacturaProveedorSRIElectronicaBean.this.servicioFacturaProveedorSRI.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  71:    */           
/*  72: 93 */           FacturaProveedorSRIElectronicaBean.this.listaFacturaProveedorSRI.setRowCount(FacturaProveedorSRIElectronicaBean.this.servicioFacturaProveedorSRI.contarPorCriterio(filters));
/*  73: 94 */           return lista;
/*  74:    */         }
/*  75:    */       };
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79:100 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  80:101 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void cargarInformacion() {}
/*  85:    */   
/*  86:    */   public String actualizarDocumentoFacturaProveedor()
/*  87:    */   {
/*  88:111 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.facturaProveedorSRI.getFacturaProveedor().getDocumento().getId()));
/*  89:112 */     this.facturaProveedorSRI.getFacturaProveedor().setDocumento(documento);
/*  90:    */     
/*  91:114 */     this.facturaProveedorSRI.setTipoComprobanteSRI(documento.getTipoComprobanteSRI());
/*  92:    */     
/*  93:116 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/*  97:    */   {
/*  98:120 */     return this.listaDireccionEmpresa;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 102:    */   {
/* 103:124 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void cargarDirecciones()
/* 107:    */   {
/* 108:128 */     if (this.empresa != null)
/* 109:    */     {
/* 110:129 */       this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(this.empresa.getId());
/* 111:130 */       for (DireccionEmpresa dir : this.listaDireccionEmpresa) {
/* 112:131 */         if (dir.isIndicadorDireccionPrincipal()) {
/* 113:132 */           this.facturaProveedorSRI.setDireccionProveedor(dir.getDireccionCompleta());
/* 114:    */         }
/* 115:    */       }
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:136 */       this.listaDireccionEmpresa = new ArrayList();
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void actualizarProveedor(SelectEvent event)
/* 124:    */   {
/* 125:141 */     this.empresa = this.servicioEmpresa.buscarPorId(Integer.valueOf(((Empresa)event.getObject()).getIdEmpresa()));
/* 126:    */     
/* 127:143 */     this.facturaProveedorSRI.setNombreProveedor(this.empresa.getNombreFiscal());
/* 128:144 */     getFacturaProveedorSRI().setTipoIdentificacion(this.empresa.getTipoIdentificacion());
/* 129:145 */     cargarDirecciones();
/* 130:146 */     this.facturaProveedorSRI.setIdentificacionProveedor(this.empresa.getIdentificacion());
/* 131:    */   }
/* 132:    */   
/* 133:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 134:    */   {
/* 135:150 */     List<Empresa> lista = this.servicioEmpresa.autocompletarProveedores(consulta);
/* 136:    */     
/* 137:152 */     return lista;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public LazyDataModel<FacturaProveedorSRI> getListaFacturaProveedorSRI()
/* 141:    */   {
/* 142:156 */     return this.listaFacturaProveedorSRI;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setListaFacturaProveedorSRI(LazyDataModel<FacturaProveedorSRI> listaFacturaProveedorSRI)
/* 146:    */   {
/* 147:160 */     this.listaFacturaProveedorSRI = listaFacturaProveedorSRI;
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected void obtenerFiltros(Map<String, String> filters)
/* 151:    */   {
/* 152:164 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String cancelar()
/* 156:    */   {
/* 157:169 */     setEditado(false);
/* 158:170 */     limpiar();
/* 159:171 */     return "";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String limpiar()
/* 163:    */   {
/* 164:    */     try
/* 165:    */     {
/* 166:177 */       this.facturaProveedorSRI = new FacturaProveedorSRI();
/* 167:178 */       this.facturaProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 168:    */       
/* 169:180 */       this.facturaProveedorSRI.setIdSucursal(AppUtil.getSucursal().getId());
/* 170:181 */       this.facturaProveedorSRI.setEstado(Estado.PROCESADO);
/* 171:182 */       this.facturaProveedorSRI.setFechaEmision(new Date());
/* 172:183 */       this.facturaProveedorSRI.setFechaEmisionRetencion(new Date());
/* 173:184 */       this.facturaProveedorSRI.setNumeroRetencion("000000000");
/* 174:185 */       this.facturaProveedorSRI.setFechaRegistro(new Date());
/* 175:186 */       this.facturaProveedorSRI.setPuntoEmisionRetencion(AppUtil.getPuntoDeVenta().getCodigo());
/* 176:187 */       this.facturaProveedorSRI.setEstablecimientoRetencion(AppUtil.getSucursal().getCodigo());
/* 177:188 */       this.facturaProveedorSRI.setPuntoEmision("001");
/* 178:189 */       this.facturaProveedorSRI.setEstablecimiento("001");
/* 179:190 */       this.facturaProveedorSRI.setNumero("000000000");
/* 180:191 */       this.facturaProveedorSRI.setTipoComprobanteSRI(this.servicioFacturaProveedorSRI.cargarTipoComprobanteSRI("01", AppUtil.getOrganizacion().getId()));
/* 181:194 */       for (Documento documento : getListaDocumento()) {
/* 182:195 */         if (documento.isPredeterminado())
/* 183:    */         {
/* 184:196 */           this.facturaProveedorSRI.setDocumento(documento);
/* 185:197 */           break;
/* 186:    */         }
/* 187:    */       }
/* 188:200 */       actualizarDocumento();
/* 189:    */     }
/* 190:    */     catch (Exception e)
/* 191:    */     {
/* 192:204 */       LOG.error("ERROR Exception AL CARGAR DATOS EN FACTURAPROVEEDORSRIBEAN ", e);
/* 193:205 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 194:    */     }
/* 195:207 */     return "";
/* 196:    */   }
/* 197:    */   
/* 198:    */   public DataTable getDtFacturaProveedorSRI()
/* 199:    */   {
/* 200:211 */     return this.dtFacturaProveedorSRI;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setDtFacturaProveedorSRI(DataTable dtFacturaProveedorSRI)
/* 204:    */   {
/* 205:215 */     this.dtFacturaProveedorSRI = dtFacturaProveedorSRI;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String getMails()
/* 209:    */   {
/* 210:219 */     return this.mails;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setMails(String mails)
/* 214:    */   {
/* 215:223 */     this.mails = mails;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void establecerMail()
/* 219:    */   {
/* 220:228 */     if ((getFacturaProveedorSRI() != null) && (getFacturaProveedorSRI().getId() > 0))
/* 221:    */     {
/* 222:229 */       this.facturaProveedorSRI = this.servicioFacturaProveedorSRI.cargarDetalle(this.facturaProveedorSRI.getId());
/* 223:230 */       String recogeEmail = this.facturaProveedorSRI.getEmail();
/* 224:231 */       if ((recogeEmail == null) || (recogeEmail.isEmpty()))
/* 225:    */       {
/* 226:232 */         this.mails = "";
/* 227:    */       }
/* 228:    */       else
/* 229:    */       {
/* 230:236 */         String[] divideEmail = recogeEmail.split(";");
/* 231:237 */         if (divideEmail.length > 0) {
/* 232:238 */           this.mails = divideEmail[0].trim();
/* 233:    */         } else {
/* 234:241 */           this.mails = recogeEmail.trim();
/* 235:    */         }
/* 236:    */       }
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void enviarMail()
/* 241:    */   {
/* 242:    */     try
/* 243:    */     {
/* 244:251 */       if (this.facturaProveedorSRI != null)
/* 245:    */       {
/* 246:252 */         this.facturaProveedorSRI = this.servicioFacturaProveedorSRI.cargarDetalle(this.facturaProveedorSRI.getId());
/* 247:253 */         if (((this.facturaProveedorSRI.getDocumento() != null) && (!this.facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico())) || (this.facturaProveedorSRI.getEstado().equals(Estado.ANULADO)) || (this.facturaProveedorSRI.getEstado().equals(Estado.EN_ESPERA)) || (this.facturaProveedorSRI.getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/* 248:254 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 249:    */         } else {
/* 250:256 */           this.servicioFacturaProveedorSRI.enviarMail(this.facturaProveedorSRI, this.mails);
/* 251:    */         }
/* 252:    */       }
/* 253:    */       else
/* 254:    */       {
/* 255:259 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 256:    */       }
/* 257:    */     }
/* 258:    */     catch (ExcepcionAS2 e)
/* 259:    */     {
/* 260:262 */       addErrorMessage(this.facturaProveedorSRI.getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/* 261:    */     }
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String guardar()
/* 265:    */   {
/* 266:268 */     this.facturaProveedorSRI.setTipoEmpresa(this.empresa.getTipoEmpresa());
/* 267:    */     
/* 268:270 */     String url = "";
/* 269:    */     try
/* 270:    */     {
/* 271:272 */       this.servicioFacturaProveedorSRI.guardar(this.facturaProveedorSRI);
/* 272:273 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 273:    */       
/* 274:275 */       limpiar();
/* 275:276 */       setEditado(false);
/* 276:    */     }
/* 277:    */     catch (ExcepcionAS2Financiero e)
/* 278:    */     {
/* 279:279 */       e.printStackTrace();
/* 280:280 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 281:281 */       LOG.info("ERROR ExcepcionAS2Financiero AL GUARDAR DATOS FACTURA PROVEEDOR SRI", e);
/* 282:    */     }
/* 283:    */     catch (ExcepcionAS2 e)
/* 284:    */     {
/* 285:284 */       e.printStackTrace();
/* 286:285 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 287:286 */       LOG.info("ERROR ExcepcionAS2 AL GUARDAR DATOS FACTURA PROVEEDOR SRI", e);
/* 288:    */     }
/* 289:    */     catch (Exception e)
/* 290:    */     {
/* 291:289 */       e.printStackTrace();
/* 292:290 */       addInfoMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 293:291 */       LOG.error("ERROR AL GUARDAR DATOS FACTURA PROVEEDOR SRI", e);
/* 294:    */     }
/* 295:294 */     return url;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String editar()
/* 299:    */   {
/* 300:299 */     if ((getFacturaProveedorSRI() != null) && (getFacturaProveedorSRI().getId() > 0)) {
/* 301:    */       try
/* 302:    */       {
/* 303:302 */         this.facturaProveedorSRI = this.servicioFacturaProveedorSRI.cargarDetalle(this.facturaProveedorSRI.getId());
/* 304:    */         
/* 305:304 */         cargarDirecciones();
/* 306:    */         
/* 307:306 */         setEditado(true);
/* 308:    */       }
/* 309:    */       catch (Exception e)
/* 310:    */       {
/* 311:309 */         addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 312:310 */         LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 313:    */       }
/* 314:    */     } else {
/* 315:313 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 316:    */     }
/* 317:316 */     return "";
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String cargarRetencionProveedorElectronica(FileUploadEvent event)
/* 321:    */   {
/* 322:325 */     List<FacturaProveedorSRI> listaFacturaProveedorSRI = new ArrayList();
/* 323:    */     try
/* 324:    */     {
/* 325:328 */       String fileName = "migracion_retencion_proveedor_electronica" + event.getFile().getFileName();
/* 326:329 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 327:330 */       listaFacturaProveedorSRI = this.servicioFacturaProveedorSRI.migracionFacturaProveedorSRI(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/* 328:331 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 329:333 */       for (localIterator = listaFacturaProveedorSRI.iterator(); localIterator.hasNext();)
/* 330:    */       {
/* 331:333 */         fpSRI = (FacturaProveedorSRI)localIterator.next();
/* 332:334 */         this.servicioFacturaProveedorSRI.guardar(fpSRI);
/* 333:    */       }
/* 334:    */     }
/* 335:    */     catch (AS2Exception e)
/* 336:    */     {
/* 337:    */       Iterator localIterator;
/* 338:338 */       e.printStackTrace();
/* 339:339 */       List<String> listaMensajes = e.getCodigoMensajes();
/* 340:340 */       int i = 0;
/* 341:341 */       for (String a : listaMensajes)
/* 342:    */       {
/* 343:342 */         i = a.indexOf("*");
/* 344:343 */         a.substring(0, i + 1);
/* 345:344 */         ErrorCarga ec = new ErrorCarga();
/* 346:345 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/* 347:346 */         this.errores.add(ec);
/* 348:    */       }
/* 349:348 */       for (String a : e.getMensajes())
/* 350:    */       {
/* 351:349 */         ErrorCarga ec = new ErrorCarga();
/* 352:350 */         ec.setError(a);
/* 353:351 */         this.errores.add(ec);
/* 354:    */       }
/* 355:    */     }
/* 356:    */     catch (ExcepcionAS2 e)
/* 357:    */     {
/* 358:    */       FacturaProveedorSRI fpSRI;
/* 359:355 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 360:356 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 361:    */     }
/* 362:    */     catch (Exception e)
/* 363:    */     {
/* 364:359 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 365:360 */       e.printStackTrace();
/* 366:    */     }
/* 367:362 */     return null;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public Empresa getEmpresa()
/* 371:    */   {
/* 372:367 */     return this.empresa;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setEmpresa(Empresa empresa)
/* 376:    */   {
/* 377:371 */     this.empresa = empresa;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public List<ErrorCarga> getErrores()
/* 381:    */   {
/* 382:378 */     return this.errores;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setErrores(List<ErrorCarga> errores)
/* 386:    */   {
/* 387:382 */     this.errores = errores;
/* 388:    */   }
/* 389:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.FacturaProveedorSRIElectronicaBean
 * JD-Core Version:    0.7.0.1
 */