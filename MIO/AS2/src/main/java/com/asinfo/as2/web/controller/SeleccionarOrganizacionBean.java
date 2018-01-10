/*   1:    */ package com.asinfo.as2.web.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCaja;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.controller.PageController;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.Caja;
/*  10:    */ import com.asinfo.as2.entities.CodigoFormaPagoSRI;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.UsuarioBodega;
/*  15:    */ import com.asinfo.as2.enumeraciones.ProcesoAuditoriaEnum;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  17:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  18:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.DatosSRI;
/*  21:    */ import java.io.IOException;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.SessionScoped;
/*  30:    */ import javax.faces.context.ExternalContext;
/*  31:    */ import javax.faces.context.FacesContext;
/*  32:    */ import javax.servlet.ServletContext;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.jdom2.JDOMException;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @SessionScoped
/*  38:    */ public class SeleccionarOrganizacionBean
/*  39:    */   extends PageController
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 1945343457112304211L;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioSucursal servicioSucursal;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioCaja servicioCaja;
/*  50:    */   @EJB
/*  51:    */   private transient ServicioBodega servicioBodega;
/*  52:    */   @EJB
/*  53:    */   private transient ServicioGenerico<CodigoFormaPagoSRI> servicioCodigoFormaPagoSRI;
/*  54:    */   private List<Organizacion> listaOrganizacion;
/*  55: 77 */   private List<Sucursal> listaSucursal = new ArrayList();
/*  56: 78 */   private List<PuntoDeVenta> listaPuntoDeVenta = new ArrayList();
/*  57: 79 */   private List<Caja> listaCaja = new ArrayList();
/*  58: 80 */   private List<Bodega> listaBodega = new ArrayList();
/*  59:    */   private Organizacion organizacion;
/*  60:    */   private Sucursal sucursal;
/*  61:    */   private PuntoDeVenta puntoDeVenta;
/*  62:    */   private Caja caja;
/*  63:    */   private Bodega bodega;
/*  64:    */   
/*  65:    */   @PostConstruct
/*  66:    */   public void init()
/*  67:    */   {
/*  68: 90 */     if (AppUtil.getUsuarioEnSesion().getNombreUsuario().equals("usuario_anonimo")) {
/*  69:    */       try
/*  70:    */       {
/*  71: 92 */         ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
/*  72: 93 */         String ctxPath = ((ServletContext)ctx.getContext()).getContextPath();
/*  73: 94 */         ctx.redirect(ctxPath + "/login.jsf");
/*  74:    */       }
/*  75:    */       catch (IOException e)
/*  76:    */       {
/*  77: 97 */         e.printStackTrace();
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String ingresar()
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:105 */       AppUtil.setAtributo("com.asinfo.as2.organizacion", this.organizacion);
/*  87:106 */       AppUtil.setAtributo("com.asinfo.as2.sucursal", this.sucursal);
/*  88:107 */       AppUtil.setAtributo("com.asinfo.as2.punto_de_venta", this.puntoDeVenta);
/*  89:108 */       AppUtil.setAtributo("com.asinfo.as2.caja", this.caja);
/*  90:109 */       AppUtil.setAtributo("bodega", this.bodega);
/*  91:110 */       AppUtil.setAtributo("direccion_matriz", this.servicioOrganizacion.obtenerDireccionMatriz(this.organizacion.getIdOrganizacion()));
/*  92:    */       
/*  93:    */ 
/*  94:113 */       AppUtil.getUsuarioEnSesion().setListaBodega(null);
/*  95:114 */       crearLogAuditoria(ProcesoAuditoriaEnum.LOGIN, "Login Exitoso ");
/*  96:    */       try
/*  97:    */       {
/*  98:116 */         Map<String, String> filters = new HashMap();
/*  99:117 */         filters.put("idOrganizacion", String.valueOf(this.organizacion.getIdOrganizacion()));
/* 100:118 */         filters.put("activo", String.valueOf(true));
/* 101:119 */         List<CodigoFormaPagoSRI> listCodigoFormaPagoSRIDB = this.servicioCodigoFormaPagoSRI.obtenerListaCombo(CodigoFormaPagoSRI.class, "predeterminado", false, filters);
/* 102:    */         
/* 103:121 */         DatosSRI.cargarFormaPago(this.organizacion.getIdOrganizacion(), listCodigoFormaPagoSRIDB);
/* 104:123 */         if (DatosSRI.getListaDistritoAduanero().isEmpty()) {
/* 105:124 */           DatosSRI.cargarDistritoAduanero("codigosDistritoAduanero.xml", this.organizacion.getIdOrganizacion());
/* 106:    */         }
/* 107:127 */         if (DatosSRI.getListaRegimen().isEmpty()) {
/* 108:128 */           DatosSRI.cargarRegimen("codigosRegimen.xml", this.organizacion.getIdOrganizacion());
/* 109:    */         }
/* 110:131 */         if (DatosSRI.getListaPaises().isEmpty()) {
/* 111:132 */           DatosSRI.cargarPaises("codigosPaises.xml", this.organizacion.getIdOrganizacion());
/* 112:    */         }
/* 113:135 */         if (DatosSRI.getListaTipoIngresoExterior().isEmpty()) {
/* 114:136 */           DatosSRI.cargarTipoIngresoExterior("codigosTipoIngresoExterior.xml", this.organizacion.getIdOrganizacion());
/* 115:    */         }
/* 116:    */       }
/* 117:    */       catch (JDOMException e)
/* 118:    */       {
/* 119:140 */         LOG.error("Error al cargar datos SRI>>: " + e.getMessage());
/* 120:141 */         e.printStackTrace();
/* 121:    */       }
/* 122:    */       catch (IOException e)
/* 123:    */       {
/* 124:143 */         LOG.error("Error al cargar datos SRI>>: " + e.getMessage());
/* 125:144 */         e.printStackTrace();
/* 126:    */       }
/* 127:    */       catch (Exception e)
/* 128:    */       {
/* 129:146 */         LOG.error("Error al cargar datos SRI>>: " + e.getMessage());
/* 130:147 */         e.printStackTrace();
/* 131:    */       }
/* 132:    */     }
/* 133:    */     catch (Exception e)
/* 134:    */     {
/* 135:151 */       e.printStackTrace();
/* 136:152 */       return "organizacion_failure";
/* 137:    */     }
/* 138:154 */     return "organizacion_success";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String seleccionarOrganizacion()
/* 142:    */   {
/* 143:158 */     this.sucursal = null;
/* 144:159 */     this.puntoDeVenta = null;
/* 145:160 */     this.caja = null;
/* 146:161 */     this.bodega = null;
/* 147:162 */     setListaBodega(null);
/* 148:    */     
/* 149:164 */     Map<String, String> filters = new HashMap();
/* 150:165 */     filters.put("idOrganizacion", String.valueOf(this.organizacion.getId()));
/* 151:    */     
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:170 */     this.listaSucursal = this.servicioSucursal.obtenerListaComboPorUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario(), this.organizacion.getId());
/* 156:171 */     if ((this.sucursal == null) && (!this.listaSucursal.isEmpty()))
/* 157:    */     {
/* 158:172 */       this.sucursal = ((Sucursal)this.listaSucursal.get(0));
/* 159:173 */       seleccionarSucursal();
/* 160:    */     }
/* 161:    */     else
/* 162:    */     {
/* 163:175 */       this.listaPuntoDeVenta.clear();
/* 164:176 */       this.listaCaja.clear();
/* 165:    */     }
/* 166:180 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String seleccionarSucursal()
/* 170:    */   {
/* 171:184 */     this.puntoDeVenta = null;
/* 172:185 */     this.caja = null;
/* 173:186 */     String sortField = "predeterminado";
/* 174:187 */     Map<String, String> filters = new HashMap();
/* 175:    */     
/* 176:189 */     filters.put("idOrganizacion", String.valueOf(this.organizacion.getId()));
/* 177:190 */     filters.put("sucursal.idSucursal", String.valueOf(this.sucursal.getId()));
/* 178:    */     
/* 179:192 */     this.listaPuntoDeVenta = this.servicioPuntoDeVenta.obtenerListaCombo(sortField, false, filters);
/* 180:193 */     if ((this.puntoDeVenta == null) && (!this.listaPuntoDeVenta.isEmpty()))
/* 181:    */     {
/* 182:194 */       this.puntoDeVenta = ((PuntoDeVenta)this.listaPuntoDeVenta.get(0));
/* 183:195 */       seleccionarPuntoDeVenta();
/* 184:    */     }
/* 185:    */     else
/* 186:    */     {
/* 187:197 */       this.listaCaja.clear();
/* 188:    */     }
/* 189:200 */     return "";
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String seleccionarPuntoDeVenta()
/* 193:    */   {
/* 194:204 */     this.caja = null;
/* 195:205 */     String sortField = "predeterminado";
/* 196:206 */     Map<String, String> filters = new HashMap();
/* 197:    */     
/* 198:208 */     filters.put("idOrganizacion", String.valueOf(this.organizacion.getId()));
/* 199:209 */     filters.put("puntoDeVenta.idPuntoDeVenta", String.valueOf(this.puntoDeVenta.getId()));
/* 200:    */     
/* 201:211 */     this.listaCaja = this.servicioCaja.obtenerListaCombo(sortField, false, filters);
/* 202:212 */     if ((this.caja == null) && (!this.listaCaja.isEmpty())) {
/* 203:213 */       this.caja = ((Caja)this.listaCaja.get(0));
/* 204:    */     }
/* 205:216 */     return "";
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<Sucursal> getListaSucursal()
/* 209:    */   {
/* 210:225 */     return this.listaSucursal;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 214:    */   {
/* 215:235 */     this.listaSucursal = listaSucursal;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<Organizacion> getListaOrganizacion()
/* 219:    */   {
/* 220:244 */     if (this.listaOrganizacion == null)
/* 221:    */     {
/* 222:246 */       this.listaOrganizacion = this.servicioOrganizacion.obtenerOrganizacionPorUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 223:247 */       if ((this.organizacion == null) && (!this.listaOrganizacion.isEmpty()))
/* 224:    */       {
/* 225:248 */         this.organizacion = ((Organizacion)this.listaOrganizacion.get(0));
/* 226:249 */         seleccionarOrganizacion();
/* 227:    */       }
/* 228:    */     }
/* 229:252 */     return this.listaOrganizacion;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setListaOrganizacion(List<Organizacion> listaOrganizacion)
/* 233:    */   {
/* 234:262 */     this.listaOrganizacion = listaOrganizacion;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List<PuntoDeVenta> getListaPuntoDeVenta()
/* 238:    */   {
/* 239:271 */     return this.listaPuntoDeVenta;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setListaPuntoDeVenta(List<PuntoDeVenta> listaPuntoDeVenta)
/* 243:    */   {
/* 244:281 */     this.listaPuntoDeVenta = listaPuntoDeVenta;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<Caja> getListaCaja()
/* 248:    */   {
/* 249:290 */     return this.listaCaja;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setListaCaja(List<Caja> listaCaja)
/* 253:    */   {
/* 254:300 */     this.listaCaja = listaCaja;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Organizacion getOrganizacion()
/* 258:    */   {
/* 259:307 */     return this.organizacion;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setOrganizacion(Organizacion organizacion)
/* 263:    */   {
/* 264:315 */     this.organizacion = organizacion;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Sucursal getSucursal()
/* 268:    */   {
/* 269:322 */     return this.sucursal;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setSucursal(Sucursal sucursal)
/* 273:    */   {
/* 274:330 */     this.sucursal = sucursal;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public PuntoDeVenta getPuntoDeVenta()
/* 278:    */   {
/* 279:337 */     return this.puntoDeVenta;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 283:    */   {
/* 284:345 */     this.puntoDeVenta = puntoDeVenta;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Caja getCaja()
/* 288:    */   {
/* 289:352 */     return this.caja;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setCaja(Caja caja)
/* 293:    */   {
/* 294:360 */     this.caja = caja;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Bodega getBodega()
/* 298:    */   {
/* 299:367 */     return this.bodega;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setBodega(Bodega bodega)
/* 303:    */   {
/* 304:375 */     this.bodega = bodega;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public List<Bodega> getListaBodega()
/* 308:    */   {
/* 309:382 */     if (((this.listaBodega == null) || (this.listaBodega.isEmpty())) && 
/* 310:383 */       (this.organizacion != null))
/* 311:    */     {
/* 312:384 */       List<UsuarioBodega> listaUsuarioBodega = this.servicioBodega.obtenerListaComboPorUsuarioBodega(AppUtil.getUsuarioEnSesion().getIdUsuario(), this.organizacion
/* 313:385 */         .getId());
/* 314:386 */       if ((this.bodega == null) && (!listaUsuarioBodega.isEmpty()))
/* 315:    */       {
/* 316:387 */         this.listaBodega = new ArrayList();
/* 317:388 */         for (UsuarioBodega bodegaAux : listaUsuarioBodega)
/* 318:    */         {
/* 319:389 */           this.listaBodega.add(bodegaAux.getBodega());
/* 320:390 */           if (bodegaAux.isPredeterminado()) {
/* 321:391 */             this.bodega = bodegaAux.getBodega();
/* 322:    */           }
/* 323:    */         }
/* 324:394 */         if ((this.bodega == null) && (!this.listaBodega.isEmpty())) {
/* 325:395 */           this.bodega = ((Bodega)this.listaBodega.get(0));
/* 326:    */         }
/* 327:    */       }
/* 328:    */     }
/* 329:401 */     return this.listaBodega;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 333:    */   {
/* 334:409 */     this.listaBodega = listaBodega;
/* 335:    */   }
/* 336:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.web.controller.SeleccionarOrganizacionBean
 * JD-Core Version:    0.7.0.1
 */