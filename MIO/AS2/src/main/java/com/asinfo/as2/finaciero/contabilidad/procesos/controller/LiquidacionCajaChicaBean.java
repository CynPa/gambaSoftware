/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.CajaChica;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.CuentaContable;
/*  11:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  12:    */ import com.asinfo.as2.entities.FormaPago;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Secuencia;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCajaChica;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  18:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.List;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.primefaces.component.selectonemenu.SelectOneMenu;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class LiquidacionCajaChicaBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 1L;
/*  38:    */   @EJB
/*  39:    */   private ServicioCajaChica servicioCajaChica;
/*  40:    */   @EJB
/*  41:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  42:    */   @EJB
/*  43:    */   private ServicioSecuencia servicioSecuencia;
/*  44:    */   private CajaChica cajaChica;
/*  45:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  46:    */   private FormaPago formaPago;
/*  47: 66 */   private Date fechaContabilizacion = new Date();
/*  48:    */   private String documentoReferencia;
/*  49:    */   private String beneficiario;
/*  50:    */   private Secuencia secuencia;
/*  51:    */   private Asiento asiento;
/*  52: 74 */   private BigDecimal debe = BigDecimal.ZERO;
/*  53: 75 */   private BigDecimal haber = BigDecimal.ZERO;
/*  54: 76 */   private BigDecimal diferencia = BigDecimal.ZERO;
/*  55:    */   private CuentaContable cuentaContable;
/*  56:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  57:    */   
/*  58:    */   @PostConstruct
/*  59:    */   public void init()
/*  60:    */   {
/*  61: 95 */     this.cajaChica = ((CajaChica)AppUtil.getAtributo("caja_chica"));
/*  62: 96 */     if (this.cajaChica != null)
/*  63:    */     {
/*  64: 97 */       this.cajaChica = this.servicioCajaChica.cargarDetalle(this.cajaChica.getId());
/*  65: 98 */       setEditado(true);
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69:100 */       cancelar();
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void crearEntidad() {}
/*  74:    */   
/*  75:    */   public String editar()
/*  76:    */   {
/*  77:122 */     if (getCajaChica().getId() > 0) {
/*  78:123 */       setEditado(true);
/*  79:    */     } else {
/*  80:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  81:    */     }
/*  82:127 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String guardar()
/*  86:    */   {
/*  87:136 */     String url = "";
/*  88:    */     try
/*  89:    */     {
/*  90:140 */       this.cajaChica.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/*  91:141 */       this.cajaChica.setCuentaContableLiquidacion(this.cuentaContable);
/*  92:142 */       this.cajaChica.setFormaPago(this.formaPago);
/*  93:143 */       this.cajaChica.setFechaContabilizacion(this.fechaContabilizacion);
/*  94:144 */       this.cajaChica.setDocumentoReferencia(this.documentoReferencia);
/*  95:145 */       this.cajaChica.setBeneficiario(this.beneficiario);
/*  96:    */       
/*  97:147 */       this.servicioCajaChica.esEditable(this.cajaChica);
/*  98:148 */       this.servicioCajaChica.contabilizar(this.cajaChica, getAsiento());
/*  99:149 */       this.servicioSecuencia.actualizarSecuencia(getSecuencia(), this.documentoReferencia);
/* 100:    */       
/* 101:151 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 102:152 */       url = "cajaChica?faces-redirect=true";
/* 103:    */     }
/* 104:    */     catch (ExcepcionAS2Financiero e)
/* 105:    */     {
/* 106:155 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 107:156 */       LOG.error("ERROR AL LIQUIDAR CAJA CHICA", e);
/* 108:    */     }
/* 109:    */     catch (ExcepcionAS2Compras e)
/* 110:    */     {
/* 111:159 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 112:160 */       LOG.error("ERROR AL LIQUIDAR CAJA CHICA", e);
/* 113:    */     }
/* 114:    */     catch (ExcepcionAS2 e)
/* 115:    */     {
/* 116:163 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 117:164 */       LOG.error("ERROR AL LIQUIDAR CAJA CHICA", e);
/* 118:    */     }
/* 119:    */     catch (Exception e)
/* 120:    */     {
/* 121:166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 122:167 */       LOG.error("ERROR AL LIQUIDAR CAJA CHICA", e);
/* 123:    */     }
/* 124:169 */     return url;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String eliminar()
/* 128:    */   {
/* 129:    */     try
/* 130:    */     {
/* 131:182 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 132:    */     }
/* 133:    */     catch (Exception e)
/* 134:    */     {
/* 135:184 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 136:185 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 137:    */     }
/* 138:187 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String cargarDatos()
/* 142:    */   {
/* 143:196 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String limpiar()
/* 147:    */   {
/* 148:205 */     crearEntidad();
/* 149:206 */     getCajaChica().setNotaContabilizacion(null);
/* 150:207 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String cancelar()
/* 154:    */   {
/* 155:216 */     return "cajaChica?faces-redirect=true";
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/* 159:    */   {
/* 160:230 */     SelectOneMenu selectOneMenu = (SelectOneMenu)event.getComponent();
/* 161:231 */     Integer idCuentaBancaria = Integer.valueOf(Integer.parseInt(selectOneMenu.getValue().toString()));
/* 162:232 */     this.cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(idCuentaBancaria.intValue());
/* 163:233 */     this.cuentaContable = this.cuentaBancariaOrganizacion.getCuentaContableBanco();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void actualizarFormaPago()
/* 167:    */   {
/* 168:240 */     int idFormaPago = getFormaPago().getId();
/* 169:241 */     int idCuentaBancariaOrganizacion = getCuentaBancariaOrganizacion().getId();
/* 170:242 */     this.secuencia = this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(idCuentaBancariaOrganizacion, idFormaPago);
/* 171:243 */     this.documentoReferencia = "";
/* 172:244 */     if (this.secuencia != null) {
/* 173:    */       try
/* 174:    */       {
/* 175:247 */         this.documentoReferencia = this.servicioSecuencia.obtenerSecuencia(this.secuencia, this.fechaContabilizacion);
/* 176:    */       }
/* 177:    */       catch (ExcepcionAS2 e)
/* 178:    */       {
/* 179:249 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 180:250 */         e.printStackTrace();
/* 181:    */       }
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void generarVistaPreviaAsiento()
/* 186:    */   {
/* 187:    */     try
/* 188:    */     {
/* 189:260 */       this.cajaChica.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/* 190:261 */       this.cajaChica.setFormaPago(this.formaPago);
/* 191:262 */       this.cajaChica.setFechaContabilizacion(this.fechaContabilizacion);
/* 192:263 */       this.cajaChica.setDocumentoReferencia(this.documentoReferencia);
/* 193:264 */       this.cajaChica.setBeneficiario(this.beneficiario);
/* 194:265 */       this.cajaChica.setCuentaContableLiquidacion(this.cuentaContable);
/* 195:266 */       this.cajaChica.setAsiento(null);
/* 196:    */       
/* 197:268 */       this.asiento = this.servicioCajaChica.vistaPreviaAsiento(this.cajaChica, AppUtil.getOrganizacion().getIdOrganizacion());
/* 198:    */     }
/* 199:    */     catch (ExcepcionAS2Financiero e)
/* 200:    */     {
/* 201:271 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 202:272 */       LOG.error("ERROR AL LIQUIDAR CAJA CHICA", e);
/* 203:    */     }
/* 204:    */     catch (ExcepcionAS2Compras e)
/* 205:    */     {
/* 206:275 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 207:276 */       LOG.error("ERROR AL LIQUIDAR CAJA CHICA", e);
/* 208:    */     }
/* 209:    */     catch (ExcepcionAS2 e)
/* 210:    */     {
/* 211:279 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 212:280 */       LOG.error("ERROR AL LIQUIDAR CAJA CHICA", e);
/* 213:    */     }
/* 214:    */     catch (Exception e)
/* 215:    */     {
/* 216:282 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 217:283 */       LOG.error("ERROR AL LIQUIDAR CAJA CHICA", e);
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public CajaChica getCajaChica()
/* 222:    */   {
/* 223:321 */     return this.cajaChica;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setCajaChica(CajaChica cajaChica)
/* 227:    */   {
/* 228:331 */     this.cajaChica = cajaChica;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 232:    */   {
/* 233:340 */     if (this.cuentaBancariaOrganizacion == null) {
/* 234:341 */       this.cuentaBancariaOrganizacion = new CuentaBancariaOrganizacion();
/* 235:    */     }
/* 236:343 */     return this.cuentaBancariaOrganizacion;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 240:    */   {
/* 241:353 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public FormaPago getFormaPago()
/* 245:    */   {
/* 246:362 */     if (this.formaPago == null) {
/* 247:363 */       this.formaPago = new FormaPago();
/* 248:    */     }
/* 249:365 */     return this.formaPago;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setFormaPago(FormaPago formaPago)
/* 253:    */   {
/* 254:375 */     this.formaPago = formaPago;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Date getFechaContabilizacion()
/* 258:    */   {
/* 259:384 */     return this.fechaContabilizacion;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 263:    */   {
/* 264:394 */     this.fechaContabilizacion = fechaContabilizacion;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getDocumentoReferencia()
/* 268:    */   {
/* 269:403 */     return this.documentoReferencia;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 273:    */   {
/* 274:413 */     this.documentoReferencia = documentoReferencia;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String getBeneficiario()
/* 278:    */   {
/* 279:422 */     return this.beneficiario;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setBeneficiario(String beneficiario)
/* 283:    */   {
/* 284:432 */     this.beneficiario = beneficiario;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Secuencia getSecuencia()
/* 288:    */   {
/* 289:441 */     return this.secuencia;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setSecuencia(Secuencia secuencia)
/* 293:    */   {
/* 294:451 */     this.secuencia = secuencia;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Asiento getAsiento()
/* 298:    */   {
/* 299:460 */     if (this.asiento == null) {
/* 300:461 */       this.asiento = new Asiento();
/* 301:    */     }
/* 302:463 */     return this.asiento;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setAsiento(Asiento asiento)
/* 306:    */   {
/* 307:473 */     this.asiento = asiento;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public BigDecimal getDebe()
/* 311:    */   {
/* 312:482 */     return this.debe;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setDebe(BigDecimal debe)
/* 316:    */   {
/* 317:492 */     this.debe = debe;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public BigDecimal getHaber()
/* 321:    */   {
/* 322:501 */     return this.haber;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setHaber(BigDecimal haber)
/* 326:    */   {
/* 327:511 */     this.haber = haber;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public BigDecimal getDiferencia()
/* 331:    */   {
/* 332:520 */     return this.diferencia;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setDiferencia(BigDecimal diferencia)
/* 336:    */   {
/* 337:530 */     this.diferencia = diferencia;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 341:    */   {
/* 342:534 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 343:535 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 344:    */     }
/* 345:537 */     return this.listaCuentaBancariaOrganizacion;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 349:    */   {
/* 350:547 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 354:    */   {
/* 355:556 */     List<DetalleAsiento> lista = new ArrayList();
/* 356:    */     
/* 357:558 */     this.debe = BigDecimal.ZERO;
/* 358:559 */     this.haber = BigDecimal.ZERO;
/* 359:560 */     this.diferencia = BigDecimal.ZERO;
/* 360:562 */     for (DetalleAsiento detalleAsiento : getAsiento().getListaDetalleAsiento()) {
/* 361:563 */       if (!detalleAsiento.isEliminado())
/* 362:    */       {
/* 363:565 */         this.haber = this.haber.add(detalleAsiento.getHaber());
/* 364:566 */         this.debe = this.debe.add(detalleAsiento.getDebe());
/* 365:567 */         lista.add(detalleAsiento);
/* 366:    */       }
/* 367:    */     }
/* 368:572 */     this.diferencia = this.diferencia.add(this.debe);
/* 369:573 */     this.diferencia = this.diferencia.subtract(this.haber);
/* 370:    */     
/* 371:575 */     return lista;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public CuentaContable getCuentaContable()
/* 375:    */   {
/* 376:582 */     return this.cuentaContable;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 380:    */   {
/* 381:589 */     this.cuentaContable = cuentaContable;
/* 382:    */   }
/* 383:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.LiquidacionCajaChicaBean
 * JD-Core Version:    0.7.0.1
 */