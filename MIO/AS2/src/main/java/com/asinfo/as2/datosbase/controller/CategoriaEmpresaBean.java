/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*   9:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoCategoriaEmpresa;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDocumentoDigitalizado;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.component.datatable.DataTable;
/*  24:    */ import org.primefaces.model.LazyDataModel;
/*  25:    */ import org.primefaces.model.SortOrder;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class CategoriaEmpresaBean
/*  30:    */   extends PageControllerAS2
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -1812294963753017704L;
/*  33:    */   @EJB
/*  34:    */   protected ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  35:    */   @EJB
/*  36:    */   private ServicioDocumentoDigitalizado servicioDocumentoDigitalizado;
/*  37:    */   private CategoriaEmpresa categoriaEmpresa;
/*  38:    */   private LazyDataModel<CategoriaEmpresa> listaCategoriaEmpresa;
/*  39:    */   private CuentaContable cuentaContable;
/*  40:    */   private DataTable dtCuentaContable;
/*  41:    */   private List<DocumentoDigitalizado> listaDocumentosDigitalizadosNoAsignados;
/*  42:    */   private DocumentoDigitalizado[] listaDocumentosDigitalizadosSeleccionados;
/*  43:    */   private List<DocumentoDigitalizadoCategoriaEmpresa> listaDocumentoDigitalizadoCategoriaEmpresa;
/*  44:    */   private DocumentoDigitalizadoCategoriaEmpresa documentoDigitalizadoCategoriaEmpresa;
/*  45:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  46:    */   private DataTable dtCategoriaEmpresa;
/*  47:    */   
/*  48:    */   private static enum enumCuentaContableEditada
/*  49:    */   {
/*  50: 71 */     CUENTA_CONTABLE_CLIENTE,  CUENTA_CONTABLE_PROVEEDOR,  CUENTA_CONTABLE_ANTICIPO_CLIENTE,  CUENTA_CONTABLE_ANTICIPO_PROVEEDOR,  CUENTA_CONTABLE_DESCUENTO_COMPRAS,  CUENTA_CONTABLE_DESCUENTO_VENTAS,  CUENTA_CONTABLE_SUELDO_POR_PAGAR,  CUENTA_CONTABLE_ANTICIPO_CLIENTE_NOTA_CREDITO,  CUENTA_CONTABLE_ANTICIPO_PROVEEDOR_NOTA_CREDITO,  CUENTA_CONTABLE_IVA_PRESUNTIVO,  CUENTA_CONTABLE_3X1000,  CUENTA_CONTABLE_2X1000;
/*  51:    */     
/*  52:    */     private enumCuentaContableEditada() {}
/*  53:    */   }
/*  54:    */   
/*  55:    */   @PostConstruct
/*  56:    */   public void init()
/*  57:    */   {
/*  58: 81 */     this.listaCategoriaEmpresa = new LazyDataModel()
/*  59:    */     {
/*  60:    */       private static final long serialVersionUID = 1L;
/*  61:    */       
/*  62:    */       public List<CategoriaEmpresa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  63:    */       {
/*  64: 88 */         List<CategoriaEmpresa> lista = new ArrayList();
/*  65: 89 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  66:    */         
/*  67: 91 */         lista = CategoriaEmpresaBean.this.servicioCategoriaEmpresa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  68: 92 */         CategoriaEmpresaBean.this.listaCategoriaEmpresa.setRowCount(CategoriaEmpresaBean.this.servicioCategoriaEmpresa.contarPorCriterio(filters));
/*  69:    */         
/*  70: 94 */         return lista;
/*  71:    */       }
/*  72:    */     };
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String editar()
/*  76:    */   {
/*  77:107 */     if (getCategoriaEmpresa().getId() > 0)
/*  78:    */     {
/*  79:108 */       this.categoriaEmpresa = this.servicioCategoriaEmpresa.cargarDetalle(getCategoriaEmpresa().getId());
/*  80:109 */       setEditado(true);
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:111 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  85:    */     }
/*  86:113 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String guardar()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:124 */       this.servicioCategoriaEmpresa.guardar(getCategoriaEmpresa());
/*  94:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  95:126 */       limpiar();
/*  96:127 */       setEditado(false);
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 101:130 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 102:    */     }
/* 103:132 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String eliminar()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:143 */       this.servicioCategoriaEmpresa.eliminar(getCategoriaEmpresa());
/* 111:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 112:    */     }
/* 113:    */     catch (Exception e)
/* 114:    */     {
/* 115:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 116:147 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 117:    */     }
/* 118:149 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String limpiar()
/* 122:    */   {
/* 123:159 */     crearCategoriaEmpresa();
/* 124:160 */     this.listaDocumentoDigitalizadoCategoriaEmpresa = null;
/* 125:161 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String cargarDatos()
/* 129:    */   {
/* 130:171 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void crearCategoriaEmpresa()
/* 134:    */   {
/* 135:180 */     this.categoriaEmpresa = new CategoriaEmpresa();
/* 136:181 */     this.categoriaEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 137:182 */     this.categoriaEmpresa.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void actualizarCuentaContableCliente()
/* 141:    */   {
/* 142:187 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_CLIENTE;
/* 143:188 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContableCliente();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void actualizarCuentaContableProveedor()
/* 147:    */   {
/* 148:192 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_PROVEEDOR;
/* 149:193 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContableProveedor();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void actualizarCuentaContableAnticipoCliente()
/* 153:    */   {
/* 154:197 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_ANTICIPO_CLIENTE;
/* 155:198 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContableAnticipoCliente();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void actualizarCuentaContableAnticipoProveedor()
/* 159:    */   {
/* 160:202 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_ANTICIPO_PROVEEDOR;
/* 161:203 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContableAnticipoProveedor();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void actualizarCuentaContableSueldoPorPagar()
/* 165:    */   {
/* 166:207 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_SUELDO_POR_PAGAR;
/* 167:208 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContableSueldoPorPagar();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void actualizarCuentaContableAnticipoClienteNotaCredito()
/* 171:    */   {
/* 172:212 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_ANTICIPO_CLIENTE_NOTA_CREDITO;
/* 173:213 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContableAnticipoClienteNotaCredito();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void actualizarCuentaContableAnticipoProveedorNotaCredito()
/* 177:    */   {
/* 178:217 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_ANTICIPO_PROVEEDOR_NOTA_CREDITO;
/* 179:218 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContableAnticipoProveedorNotaCredito();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void actualizarCuentaContableIvaPresuntivo()
/* 183:    */   {
/* 184:222 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_IVA_PRESUNTIVO;
/* 185:223 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContableIvaPresuntivo();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void actualizarCuentaContable3X1000()
/* 189:    */   {
/* 190:227 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_3X1000;
/* 191:228 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContable3X1000();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void actualizarCuentaContable2X1000()
/* 195:    */   {
/* 196:232 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_2X1000;
/* 197:233 */     this.cuentaContable = this.categoriaEmpresa.getCuentaContable2X1000();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void cargarCuentaContable()
/* 201:    */   {
/* 202:238 */     this.cuentaContable = ((CuentaContable)this.dtCuentaContable.getRowData());
/* 203:240 */     switch (2.$SwitchMap$com$asinfo$as2$datosbase$controller$CategoriaEmpresaBean$enumCuentaContableEditada[this.cuentaContableEditada.ordinal()])
/* 204:    */     {
/* 205:    */     case 1: 
/* 206:243 */       this.categoriaEmpresa.setCuentaContableCliente(this.cuentaContable);
/* 207:244 */       break;
/* 208:    */     case 2: 
/* 209:247 */       this.categoriaEmpresa.setCuentaContableProveedor(this.cuentaContable);
/* 210:248 */       break;
/* 211:    */     case 3: 
/* 212:251 */       this.categoriaEmpresa.setCuentaContableAnticipoCliente(this.cuentaContable);
/* 213:252 */       break;
/* 214:    */     case 4: 
/* 215:255 */       this.categoriaEmpresa.setCuentaContableAnticipoProveedor(this.cuentaContable);
/* 216:256 */       break;
/* 217:    */     case 5: 
/* 218:259 */       this.categoriaEmpresa.setCuentaContableSueldoPorPagar(this.cuentaContable);
/* 219:260 */       break;
/* 220:    */     case 6: 
/* 221:263 */       this.categoriaEmpresa.setCuentaContableAnticipoClienteNotaCredito(this.cuentaContable);
/* 222:264 */       break;
/* 223:    */     case 7: 
/* 224:267 */       this.categoriaEmpresa.setCuentaContableAnticipoProveedorNotaCredito(this.cuentaContable);
/* 225:268 */       break;
/* 226:    */     case 8: 
/* 227:271 */       this.categoriaEmpresa.setCuentaContableIvaPresuntivo(this.cuentaContable);
/* 228:272 */       break;
/* 229:    */     case 9: 
/* 230:275 */       this.categoriaEmpresa.setCuentaContable3X1000(this.cuentaContable);
/* 231:276 */       break;
/* 232:    */     case 10: 
/* 233:279 */       this.categoriaEmpresa.setCuentaContable2X1000(this.cuentaContable);
/* 234:280 */       break;
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 239:    */   {
/* 240:293 */     if (this.categoriaEmpresa == null) {
/* 241:294 */       crearCategoriaEmpresa();
/* 242:    */     }
/* 243:296 */     return this.categoriaEmpresa;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 247:    */   {
/* 248:306 */     this.categoriaEmpresa = categoriaEmpresa;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public LazyDataModel<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 252:    */   {
/* 253:315 */     return this.listaCategoriaEmpresa;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setListaCategoriaEmpresa(LazyDataModel<CategoriaEmpresa> listaCategoriaEmpresa)
/* 257:    */   {
/* 258:325 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public DataTable getDtCategoriaEmpresa()
/* 262:    */   {
/* 263:334 */     return this.dtCategoriaEmpresa;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setDtCategoriaEmpresa(DataTable dtCategoriaEmpresa)
/* 267:    */   {
/* 268:344 */     this.dtCategoriaEmpresa = dtCategoriaEmpresa;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public enumCuentaContableEditada getCuentaContableEditada()
/* 272:    */   {
/* 273:353 */     return this.cuentaContableEditada;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setCuentaContableEditada(enumCuentaContableEditada cuentaContableEditada)
/* 277:    */   {
/* 278:363 */     this.cuentaContableEditada = cuentaContableEditada;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public CuentaContable getCuentaContable()
/* 282:    */   {
/* 283:372 */     return this.cuentaContable;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 287:    */   {
/* 288:383 */     this.cuentaContable = cuentaContable;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public DataTable getDtCuentaContable()
/* 292:    */   {
/* 293:387 */     return this.dtCuentaContable;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 297:    */   {
/* 298:391 */     this.dtCuentaContable = dtCuentaContable;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<DocumentoDigitalizado> getListaDocumentosDigitalizadosNoAsignados()
/* 302:    */   {
/* 303:395 */     return this.listaDocumentosDigitalizadosNoAsignados;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setListaDocumentosDigitalizadosNoAsignados(List<DocumentoDigitalizado> listaDocumentosDigitalizadosNoAsignados)
/* 307:    */   {
/* 308:399 */     this.listaDocumentosDigitalizadosNoAsignados = listaDocumentosDigitalizadosNoAsignados;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public DocumentoDigitalizado[] getListaDocumentosDigitalizadosSeleccionados()
/* 312:    */   {
/* 313:403 */     return this.listaDocumentosDigitalizadosSeleccionados;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setListaDocumentosDigitalizadosSeleccionados(DocumentoDigitalizado[] listaDocumentosDigitalizadosSeleccionados)
/* 317:    */   {
/* 318:407 */     this.listaDocumentosDigitalizadosSeleccionados = listaDocumentosDigitalizadosSeleccionados;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void cargarDocumentosDigitalizadosNoAsignados()
/* 322:    */   {
/* 323:411 */     this.listaDocumentosDigitalizadosSeleccionados = null;
/* 324:412 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 325:413 */     this.listaDocumentosDigitalizadosNoAsignados = this.servicioDocumentoDigitalizado.obtenerListaCombo("nombre", true, filtros);
/* 326:    */     
/* 327:415 */     HashMap<Integer, DocumentoDigitalizado> documentosMap = new HashMap();
/* 328:416 */     for (DocumentoDigitalizado docDigitalizado : this.listaDocumentosDigitalizadosNoAsignados) {
/* 329:417 */       documentosMap.put(Integer.valueOf(docDigitalizado.getIdDocumentoDigitalizado()), docDigitalizado);
/* 330:    */     }
/* 331:420 */     for (DocumentoDigitalizadoCategoriaEmpresa docDigitalizadoCategoriaEmpresa : this.categoriaEmpresa.getListaDocumentoDigitalizadoCategoriaEmpresa()) {
/* 332:421 */       if (!docDigitalizadoCategoriaEmpresa.isEliminado()) {
/* 333:422 */         documentosMap.remove(Integer.valueOf(docDigitalizadoCategoriaEmpresa.getDocumentoDigitalizado().getIdDocumentoDigitalizado()));
/* 334:    */       }
/* 335:    */     }
/* 336:426 */     this.listaDocumentosDigitalizadosNoAsignados = new ArrayList(documentosMap.values());
/* 337:    */   }
/* 338:    */   
/* 339:    */   public String agregarDocumentosDigitalizados()
/* 340:    */   {
/* 341:430 */     if (this.listaDocumentosDigitalizadosSeleccionados != null) {
/* 342:431 */       for (DocumentoDigitalizado documento : this.listaDocumentosDigitalizadosSeleccionados)
/* 343:    */       {
/* 344:432 */         boolean encontre = false;
/* 345:433 */         for (DocumentoDigitalizadoCategoriaEmpresa docDigitalizadoCategoriaEmpresa : this.categoriaEmpresa
/* 346:434 */           .getListaDocumentoDigitalizadoCategoriaEmpresa()) {
/* 347:435 */           if (docDigitalizadoCategoriaEmpresa.getDocumentoDigitalizado().getId() == documento.getId())
/* 348:    */           {
/* 349:436 */             docDigitalizadoCategoriaEmpresa.setEliminado(false);
/* 350:437 */             encontre = true;
/* 351:438 */             break;
/* 352:    */           }
/* 353:    */         }
/* 354:441 */         if (!encontre)
/* 355:    */         {
/* 356:442 */           DocumentoDigitalizadoCategoriaEmpresa documentoCategoriaEmpresa = new DocumentoDigitalizadoCategoriaEmpresa();
/* 357:443 */           documentoCategoriaEmpresa.setDocumentoDigitalizado(documento);
/* 358:444 */           documentoCategoriaEmpresa.setCategoriaEmpresa(this.categoriaEmpresa);
/* 359:445 */           this.categoriaEmpresa.getListaDocumentoDigitalizadoCategoriaEmpresa().add(documentoCategoriaEmpresa);
/* 360:446 */           this.listaDocumentosDigitalizadosNoAsignados.remove(documento);
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:451 */     this.listaDocumentosDigitalizadosSeleccionados = null;
/* 365:452 */     this.listaDocumentoDigitalizadoCategoriaEmpresa = null;
/* 366:    */     
/* 367:454 */     return "";
/* 368:    */   }
/* 369:    */   
/* 370:    */   public String eliminarDocumentoDigitalizadoCategoriaEmpresa()
/* 371:    */   {
/* 372:458 */     this.documentoDigitalizadoCategoriaEmpresa.setEliminado(true);
/* 373:459 */     this.listaDocumentoDigitalizadoCategoriaEmpresa = null;
/* 374:460 */     return "";
/* 375:    */   }
/* 376:    */   
/* 377:    */   public DocumentoDigitalizadoCategoriaEmpresa getDocumentoDigitalizadoCategoriaEmpresa()
/* 378:    */   {
/* 379:464 */     return this.documentoDigitalizadoCategoriaEmpresa;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setDocumentoDigitalizadoCategoriaEmpresa(DocumentoDigitalizadoCategoriaEmpresa documentoDigitalizadoCategoriaEmpresa)
/* 383:    */   {
/* 384:468 */     this.documentoDigitalizadoCategoriaEmpresa = documentoDigitalizadoCategoriaEmpresa;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public List<DocumentoDigitalizadoCategoriaEmpresa> getListaDocumentoDigitalizadoCategoriaEmpresa()
/* 388:    */   {
/* 389:472 */     if (this.listaDocumentoDigitalizadoCategoriaEmpresa == null)
/* 390:    */     {
/* 391:473 */       this.listaDocumentoDigitalizadoCategoriaEmpresa = new ArrayList();
/* 392:474 */       for (DocumentoDigitalizadoCategoriaEmpresa documento : this.categoriaEmpresa.getListaDocumentoDigitalizadoCategoriaEmpresa()) {
/* 393:475 */         if (!documento.isEliminado()) {
/* 394:476 */           this.listaDocumentoDigitalizadoCategoriaEmpresa.add(documento);
/* 395:    */         }
/* 396:    */       }
/* 397:    */     }
/* 398:480 */     return this.listaDocumentoDigitalizadoCategoriaEmpresa;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void setListaDocumentoDigitalizadoCategoriaEmpresa(List<DocumentoDigitalizadoCategoriaEmpresa> listaDocumentoDigitalizadoCategoriaEmpresa)
/* 402:    */   {
/* 403:484 */     this.listaDocumentoDigitalizadoCategoriaEmpresa = listaDocumentoDigitalizadoCategoriaEmpresa;
/* 404:    */   }
/* 405:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.CategoriaEmpresaBean
 * JD-Core Version:    0.7.0.1
 */