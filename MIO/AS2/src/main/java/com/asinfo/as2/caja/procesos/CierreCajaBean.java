/*   1:    */ package com.asinfo.as2.caja.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.caja.procesos.servicio.ServicioCierreCaja;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   7:    */ import com.asinfo.as2.entities.Caja;
/*   8:    */ import com.asinfo.as2.entities.CierreCaja;
/*   9:    */ import com.asinfo.as2.entities.DenominacionFormaCobro;
/*  10:    */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*  11:    */ import com.asinfo.as2.entities.DetalleDenominacionFormaCobro;
/*  12:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  18:    */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*  19:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  22:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  25:    */ import java.math.BigDecimal;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.Iterator;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import javax.annotation.PostConstruct;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.faces.bean.ManagedBean;
/*  34:    */ import javax.faces.bean.ViewScoped;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.component.datatable.DataTable;
/*  37:    */ import org.primefaces.context.RequestContext;
/*  38:    */ import org.primefaces.model.LazyDataModel;
/*  39:    */ import org.primefaces.model.SortOrder;
/*  40:    */ 
/*  41:    */ @ManagedBean
/*  42:    */ @ViewScoped
/*  43:    */ public class CierreCajaBean
/*  44:    */   extends PageControllerAS2
/*  45:    */ {
/*  46:    */   private static final long serialVersionUID = 1L;
/*  47:    */   @EJB
/*  48:    */   private ServicioCierreCaja servicioCierreCaja;
/*  49:    */   @EJB
/*  50:    */   private transient ServicioGenerico<DenominacionFormaCobro> servicioDenominacionFormaCobro;
/*  51:    */   private CierreCaja cierreCaja;
/*  52:    */   private LazyDataModel<CierreCaja> listaCierreCaja;
/*  53:    */   private List<DenominacionFormaCobro> listDenominaciones;
/*  54:    */   private boolean renderRegistroDenominacionFormaCobro;
/*  55:    */   private DataTable dtCierreCaja;
/*  56:    */   
/*  57:    */   public String guardar()
/*  58:    */   {
/*  59:    */     try
/*  60:    */     {
/*  61: 84 */       if ((!this.renderRegistroDenominacionFormaCobro) && (isCierreCajaPorDenominacionFormaCobro()))
/*  62:    */       {
/*  63: 85 */         this.renderRegistroDenominacionFormaCobro = true;
/*  64: 86 */         RequestContext.getCurrentInstance().update(":form:panelRegistroDenominacionFormaCobro");
/*  65: 87 */         RequestContext.getCurrentInstance().execute("dialogRegistroDenominacionFormaCobro.show()");
/*  66:    */       }
/*  67:    */       else
/*  68:    */       {
/*  69: 89 */         this.servicioCierreCaja.guardar(this.cierreCaja);
/*  70: 90 */         setEditado(false);
/*  71: 91 */         if (this.renderRegistroDenominacionFormaCobro)
/*  72:    */         {
/*  73: 92 */           RequestContext.getCurrentInstance().update("panelContenedor");
/*  74: 93 */           this.renderRegistroDenominacionFormaCobro = false;
/*  75:    */         }
/*  76: 95 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:    */         
/*  78: 97 */         limpiar();
/*  79:    */       }
/*  80:    */     }
/*  81:    */     catch (ExcepcionAS2 e)
/*  82:    */     {
/*  83:100 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  84:    */     }
/*  85:    */     catch (Exception e)
/*  86:    */     {
/*  87:102 */       e.printStackTrace();
/*  88:103 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  89:104 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  90:    */     }
/*  91:106 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String eliminar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:117 */       this.servicioCierreCaja.anular(this.cierreCaja);
/*  99:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 100:    */     }
/* 101:    */     catch (ExcepcionAS2Financiero e)
/* 102:    */     {
/* 103:120 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 108:123 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 109:    */     }
/* 110:125 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String cargarDatos()
/* 114:    */   {
/* 115:135 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String editar()
/* 119:    */   {
/* 120:145 */     if ((getCierreCaja() != null) && (getCierreCaja().getId() > 0)) {
/* 121:    */       try
/* 122:    */       {
/* 123:147 */         this.servicioCierreCaja.esEditable(this.cierreCaja);
/* 124:    */         
/* 125:149 */         this.cierreCaja = this.servicioCierreCaja.cargarDetalle(getCierreCaja().getId());
/* 126:150 */         setEditado(true);
/* 127:    */       }
/* 128:    */       catch (ExcepcionAS2Financiero e)
/* 129:    */       {
/* 130:152 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 131:153 */         e.printStackTrace();
/* 132:    */       }
/* 133:    */     } else {
/* 134:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 135:    */     }
/* 136:159 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String limpiar()
/* 140:    */   {
/* 141:169 */     crearCierreCaja();
/* 142:170 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   @PostConstruct
/* 146:    */   public void init()
/* 147:    */   {
/* 148:179 */     this.listaCierreCaja = new LazyDataModel()
/* 149:    */     {
/* 150:    */       private static final long serialVersionUID = 1L;
/* 151:    */       
/* 152:    */       public List<CierreCaja> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 153:    */       {
/* 154:186 */         List<CierreCaja> lista = new ArrayList();
/* 155:    */         
/* 156:188 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 157:    */         
/* 158:    */ 
/* 159:191 */         Usuario usuarioSesion = AppUtil.getUsuarioEnSesion();
/* 160:192 */         if (!TipoVisualizacionEnum.TODA_LA_ORGANIZACION.equals(usuarioSesion.getTipoVisualizacion())) {
/* 161:193 */           filters = CierreCajaBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, usuarioSesion, false);
/* 162:    */         }
/* 163:196 */         lista = CierreCajaBean.this.servicioCierreCaja.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 164:197 */         CierreCajaBean.this.listaCierreCaja.setRowCount(CierreCajaBean.this.servicioCierreCaja.contarPorCriterio(filters));
/* 165:    */         
/* 166:199 */         return lista;
/* 167:    */       }
/* 168:    */     };
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String crearCierreCaja()
/* 172:    */   {
/* 173:206 */     this.cierreCaja = new CierreCaja();
/* 174:207 */     this.cierreCaja.setEstado(Estado.ELABORADO);
/* 175:208 */     this.cierreCaja.setCaja(AppUtil.getCaja());
/* 176:209 */     this.cierreCaja.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 177:210 */     this.cierreCaja.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 178:211 */     EntidadUsuario eu = new EntidadUsuario();
/* 179:212 */     eu.setIdUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 180:213 */     this.cierreCaja.setUsuario(eu);
/* 181:214 */     this.cierreCaja.setFechaHasta(new Date());
/* 182:215 */     cargarDatosCaja();
/* 183:216 */     return "";
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void cargarDatosCaja()
/* 187:    */   {
/* 188:220 */     int idOrganizacion = AppUtil.getOrganizacion().getIdOrganizacion();
/* 189:221 */     Integer idCaja = null;
/* 190:222 */     if (getTipoOrganizacion() != TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA) {
/* 191:223 */       idCaja = Integer.valueOf(AppUtil.getCaja().getIdCaja());
/* 192:    */     }
/* 193:226 */     List<DetalleCierreCaja> listaDetalleCierreCaja = new ArrayList();
/* 194:227 */     BigDecimal valorTotal = BigDecimal.ZERO;
/* 195:230 */     for (Iterator localIterator = this.servicioCierreCaja.obtenerListaDetalleCierreCaja(idOrganizacion, AppUtil.getUsuarioEnSesion().getNombreUsuario(), idCaja).iterator(); localIterator.hasNext();)
/* 196:    */     {
/* 197:230 */       detalleFormaCobro = (DetalleFormaCobro)localIterator.next();
/* 198:231 */       DetalleCierreCaja d = new DetalleCierreCaja();
/* 199:232 */       d.setCierreCaja(this.cierreCaja);
/* 200:233 */       d.setDetalleFormaCobro(detalleFormaCobro);
/* 201:234 */       d.setIdOrganizacion(this.cierreCaja.getIdOrganizacion());
/* 202:235 */       d.setIdSucursal(this.cierreCaja.getIdSucursal());
/* 203:236 */       d.setValor(detalleFormaCobro.getValor());
/* 204:237 */       valorTotal = valorTotal.add(detalleFormaCobro.getValor());
/* 205:238 */       listaDetalleCierreCaja.add(d);
/* 206:    */     }
/* 207:    */     DetalleFormaCobro detalleFormaCobro;
/* 208:242 */     Object listaAnticipoCliente = this.servicioCierreCaja.obtenerListaDetalleCierreCajaAC(idOrganizacion, AppUtil.getUsuarioEnSesion()
/* 209:243 */       .getNombreUsuario(), idCaja);
/* 210:244 */     for (AnticipoCliente anticipoCliente : (List)listaAnticipoCliente)
/* 211:    */     {
/* 212:245 */       DetalleCierreCaja d = new DetalleCierreCaja();
/* 213:246 */       d.setCierreCaja(this.cierreCaja);
/* 214:247 */       d.setAnticipoCliente(anticipoCliente);
/* 215:248 */       d.setIdOrganizacion(this.cierreCaja.getIdOrganizacion());
/* 216:249 */       d.setIdSucursal(this.cierreCaja.getIdSucursal());
/* 217:250 */       d.setValor(anticipoCliente.getValor());
/* 218:251 */       valorTotal = valorTotal.add(anticipoCliente.getValor());
/* 219:252 */       listaDetalleCierreCaja.add(d);
/* 220:    */     }
/* 221:255 */     this.cierreCaja.setListaDetalleCierreCaja(listaDetalleCierreCaja);
/* 222:256 */     this.cierreCaja.setValor(valorTotal);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void agregarDenominacion()
/* 226:    */   {
/* 227:260 */     DetalleDenominacionFormaCobro ddfc = new DetalleDenominacionFormaCobro();
/* 228:261 */     ddfc.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 229:262 */     ddfc.setIdSucursal(AppUtil.getSucursal().getId());
/* 230:263 */     ddfc.setCierreCaja(this.cierreCaja);
/* 231:264 */     this.cierreCaja.getListDetalleDenominacionFormaCobro().add(ddfc);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void agregarDenominacion(DetalleDenominacionFormaCobro ddfc)
/* 235:    */   {
/* 236:268 */     ddfc.setEliminado(true);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void totalizarDenominaciones()
/* 240:    */   {
/* 241:272 */     BigDecimal totalDenominaciones = BigDecimal.ZERO;
/* 242:273 */     for (DetalleDenominacionFormaCobro ddfc : this.cierreCaja.getListDetalleDenominacionFormaCobro()) {
/* 243:274 */       if (!ddfc.isEliminado()) {
/* 244:275 */         if (BigDecimal.ZERO.compareTo(ddfc.getDenominacionFormaCobro().getValor()) != 0)
/* 245:    */         {
/* 246:276 */           ddfc.setTotal(ddfc.getDenominacionFormaCobro().getValor().multiply(new BigDecimal(ddfc.getCantidad())));
/* 247:277 */           totalDenominaciones = totalDenominaciones.add(ddfc.getTotal());
/* 248:    */         }
/* 249:    */         else
/* 250:    */         {
/* 251:279 */           totalDenominaciones = totalDenominaciones.add(ddfc.getTotal());
/* 252:    */         }
/* 253:    */       }
/* 254:    */     }
/* 255:284 */     this.cierreCaja.setTotalUsuario(totalDenominaciones);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<DetalleDenominacionFormaCobro> getListDetalleDenominacionFormaCobro()
/* 259:    */   {
/* 260:288 */     List<DetalleDenominacionFormaCobro> listDetallesDenominacion = new ArrayList();
/* 261:289 */     for (DetalleDenominacionFormaCobro detalleDenominacionFormaCobro : this.cierreCaja.getListDetalleDenominacionFormaCobro()) {
/* 262:290 */       if (!detalleDenominacionFormaCobro.isEliminado()) {
/* 263:291 */         listDetallesDenominacion.add(detalleDenominacionFormaCobro);
/* 264:    */       }
/* 265:    */     }
/* 266:294 */     return listDetallesDenominacion;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public List<DenominacionFormaCobro> getListDenominaciones()
/* 270:    */   {
/* 271:298 */     if (this.listDenominaciones == null) {
/* 272:299 */       this.listDenominaciones = this.servicioDenominacionFormaCobro.obtenerListaCombo(DenominacionFormaCobro.class, "nombre", true, 
/* 273:300 */         agregarFiltroOrganizacion(null));
/* 274:    */     }
/* 275:302 */     return this.listDenominaciones;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public CierreCaja getCierreCaja()
/* 279:    */   {
/* 280:311 */     return this.cierreCaja;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setCierreCaja(CierreCaja cierreCaja)
/* 284:    */   {
/* 285:321 */     this.cierreCaja = cierreCaja;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public LazyDataModel<CierreCaja> getListaCierreCaja()
/* 289:    */   {
/* 290:330 */     return this.listaCierreCaja;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setListaCierreCaja(LazyDataModel<CierreCaja> listaCierreCaja)
/* 294:    */   {
/* 295:340 */     this.listaCierreCaja = listaCierreCaja;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public DataTable getDtCierreCaja()
/* 299:    */   {
/* 300:349 */     return this.dtCierreCaja;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setDtCierreCaja(DataTable dtCierreCaja)
/* 304:    */   {
/* 305:359 */     this.dtCierreCaja = dtCierreCaja;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public boolean isCierreCajaPorDenominacionFormaCobro()
/* 309:    */   {
/* 310:363 */     return ParametrosSistema.getCierreCajaPorDenominacionFormaCobro(AppUtil.getOrganizacion().getId()).booleanValue();
/* 311:    */   }
/* 312:    */   
/* 313:    */   public boolean isRenderRegistroDenominacionFormaCobro()
/* 314:    */   {
/* 315:367 */     return this.renderRegistroDenominacionFormaCobro;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setRenderRegistroDenominacionFormaCobro(boolean renderRegistroDenominacionFormaCobro)
/* 319:    */   {
/* 320:371 */     this.renderRegistroDenominacionFormaCobro = renderRegistroDenominacionFormaCobro;
/* 321:    */   }
/* 322:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.procesos.CierreCajaBean
 * JD-Core Version:    0.7.0.1
 */