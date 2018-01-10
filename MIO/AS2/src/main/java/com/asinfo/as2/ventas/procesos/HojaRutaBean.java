/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.DetalleHojaRuta;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.HojaRuta;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  20:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Calendar;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ import org.primefaces.component.datatable.DataTable;
/*  32:    */ import org.primefaces.model.LazyDataModel;
/*  33:    */ import org.primefaces.model.SortOrder;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class HojaRutaBean
/*  38:    */   extends PageControllerAS2
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = -7577609751620904609L;
/*  41:    */   @EJB
/*  42:    */   protected ServicioHojaRuta servicioHojaRuta;
/*  43:    */   @EJB
/*  44:    */   private ServicioDocumento servicioDocumento;
/*  45:    */   @EJB
/*  46:    */   private transient ServicioSucursal servicioSucursal;
/*  47:    */   private HojaRuta hojaRuta;
/*  48:    */   private LazyDataModel<HojaRuta> listaHojaRuta;
/*  49:    */   private List<Documento> listaDocumentoHojaRuta;
/*  50:    */   private DataTable dtHojaRuta;
/*  51:    */   protected DataTable dtDetalleHojaRuta;
/*  52: 77 */   private Date fechaDesde = Calendar.getInstance().getTime();
/*  53: 78 */   private Date fechaHasta = Calendar.getInstance().getTime();
/*  54:    */   private Sucursal sucursal;
/*  55:    */   private List<Sucursal> listaSucursal;
/*  56:    */   private Integer idHojaRuta;
/*  57:    */   
/*  58:    */   @PostConstruct
/*  59:    */   public void init()
/*  60:    */   {
/*  61: 87 */     this.listaHojaRuta = new LazyDataModel()
/*  62:    */     {
/*  63:    */       private static final long serialVersionUID = 1L;
/*  64:    */       
/*  65:    */       public List<HojaRuta> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  66:    */       {
/*  67: 94 */         if (HojaRutaBean.this.idHojaRuta != null)
/*  68:    */         {
/*  69: 95 */           filters.put("idHojaRuta", HojaRutaBean.this.idHojaRuta.toString());
/*  70: 96 */           HojaRutaBean.this.idHojaRuta = null;
/*  71:    */         }
/*  72:100 */         if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  73:101 */           filters.put("sucursal.idSucursal", String.valueOf(AppUtil.getSucursal().getIdSucursal()));
/*  74:    */         }
/*  75:104 */         List<HojaRuta> lista = new ArrayList();
/*  76:    */         
/*  77:106 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  78:    */         
/*  79:108 */         HojaRutaBean.this.agregarFiltros(filters);
/*  80:    */         
/*  81:110 */         lista = HojaRutaBean.this.servicioHojaRuta.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  82:111 */         HojaRutaBean.this.listaHojaRuta.setRowCount(HojaRutaBean.this.servicioHojaRuta.contarPorCriterio(filters));
/*  83:    */         
/*  84:113 */         return lista;
/*  85:    */       }
/*  86:    */     };
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String editar()
/*  90:    */   {
/*  91:126 */     if ((this.hojaRuta != null) && (this.hojaRuta.getIdHojaRuta() != 0)) {
/*  92:    */       try
/*  93:    */       {
/*  94:129 */         this.hojaRuta = this.servicioHojaRuta.cargarDetalle(Integer.valueOf(this.hojaRuta.getId()));
/*  95:130 */         this.servicioHojaRuta.esEditable(this.hojaRuta);
/*  96:131 */         setEditado(true);
/*  97:    */       }
/*  98:    */       catch (ExcepcionAS2Financiero e)
/*  99:    */       {
/* 100:134 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 101:135 */         LOG.info("ERROR AL EDITAR DESPACHO DE CLIENTE:", e);
/* 102:    */       }
/* 103:    */       catch (ExcepcionAS2Ventas e)
/* 104:    */       {
/* 105:138 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 106:139 */         LOG.info("ERROR AL EDITAR DESPACHO DE CLIENTE:", e);
/* 107:    */       }
/* 108:    */       catch (Exception e)
/* 109:    */       {
/* 110:142 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 111:143 */         LOG.error("ERROR AL EDITAR DESPACHO DE CLIENTE:", e);
/* 112:    */       }
/* 113:    */     } else {
/* 114:147 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 115:    */     }
/* 116:150 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String guardar()
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:162 */       this.servicioHojaRuta.guardar(this.hojaRuta);
/* 124:163 */       this.idHojaRuta = Integer.valueOf(this.hojaRuta.getIdHojaRuta());
/* 125:    */       
/* 126:165 */       setEditado(false);
/* 127:166 */       limpiar();
/* 128:167 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 129:169 */       if (getHojaRuta().getIndicadorHojaRutaTransportista().booleanValue() == true) {
/* 130:170 */         return "hojaRutaTransportista.xhtml?faces-redirect=true&idHojaRuta=" + this.idHojaRuta;
/* 131:    */       }
/* 132:172 */       return "hojaRuta.xhtml?faces-redirect=true&idHojaRuta=" + this.idHojaRuta;
/* 133:    */     }
/* 134:    */     catch (ExcepcionAS2Inventario e)
/* 135:    */     {
/* 136:177 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 137:    */     }
/* 138:    */     catch (ExcepcionAS2Financiero e)
/* 139:    */     {
/* 140:179 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 141:    */     }
/* 142:    */     catch (ExcepcionAS2 e)
/* 143:    */     {
/* 144:181 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 145:    */     }
/* 146:    */     catch (Exception e)
/* 147:    */     {
/* 148:183 */       e.printStackTrace();
/* 149:184 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 150:185 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 151:    */     }
/* 152:188 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String limpiar()
/* 156:    */   {
/* 157:199 */     setEditado(false);
/* 158:    */     
/* 159:201 */     crearHojaRuta();
/* 160:    */     
/* 161:203 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void agregarFiltros(Map<String, String> filters)
/* 165:    */   {
/* 166:207 */     filters.put("indicadorHojaRutaTransportista", "false");
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void crearHojaRuta()
/* 170:    */   {
/* 171:214 */     this.hojaRuta = new HojaRuta();
/* 172:215 */     this.hojaRuta.setFecha(new Date());
/* 173:216 */     this.hojaRuta.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 174:217 */     this.hojaRuta.setSucursal(AppUtil.getSucursal());
/* 175:219 */     if (!getListaDocumentoHojaRuta().isEmpty())
/* 176:    */     {
/* 177:220 */       Documento documento = (Documento)getListaDocumentoHojaRuta().get(0);
/* 178:221 */       this.hojaRuta.setDocumento(documento);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String cargarDatos()
/* 183:    */   {
/* 184:233 */     return "";
/* 185:    */   }
/* 186:    */   
/* 187:    */   public HojaRuta getHojaRuta()
/* 188:    */   {
/* 189:244 */     return this.hojaRuta;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setHojaRuta(HojaRuta hojaRuta)
/* 193:    */   {
/* 194:254 */     this.hojaRuta = hojaRuta;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public LazyDataModel<HojaRuta> getListaHojaRuta()
/* 198:    */   {
/* 199:263 */     return this.listaHojaRuta;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setListaHojaRuta(LazyDataModel<HojaRuta> listaHojaRuta)
/* 203:    */   {
/* 204:273 */     this.listaHojaRuta = listaHojaRuta;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void cargarInformacion()
/* 208:    */   {
/* 209:277 */     List<DetalleHojaRuta> lista = new ArrayList();
/* 210:278 */     for (DetalleHojaRuta detalle : this.hojaRuta.getListaDetalleHojaRuta()) {
/* 211:279 */       if ((detalle.getIdDetalleHojaRuta() != 0) || (detalle.isSeleccionado())) {
/* 212:280 */         lista.add(detalle);
/* 213:    */       }
/* 214:    */     }
/* 215:283 */     this.hojaRuta.setListaDetalleHojaRuta(lista);
/* 216:284 */     this.hojaRuta.getListaDetalleHojaRuta().addAll(this.servicioHojaRuta
/* 217:285 */       .cargarDetalleHojaRuta(AppUtil.getOrganizacion().getId(), this.sucursal, this.hojaRuta, this.fechaDesde, this.fechaHasta));
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<DetalleHojaRuta> getListaDetalleHojaRuta()
/* 221:    */   {
/* 222:294 */     List<DetalleHojaRuta> detalle = new ArrayList();
/* 223:295 */     for (DetalleHojaRuta detalleHojaRuta : getHojaRuta().getListaDetalleHojaRuta()) {
/* 224:297 */       if (!detalleHojaRuta.isEliminado()) {
/* 225:298 */         detalle.add(detalleHojaRuta);
/* 226:    */       }
/* 227:    */     }
/* 228:302 */     return detalle;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean isModoEdicion()
/* 232:    */   {
/* 233:306 */     return getHojaRuta().getId() > 0;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public DataTable getDtHojaRuta()
/* 237:    */   {
/* 238:315 */     return this.dtHojaRuta;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setDtHojaRuta(DataTable dtHojaRuta)
/* 242:    */   {
/* 243:325 */     this.dtHojaRuta = dtHojaRuta;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public DataTable getDtDetalleHojaRuta()
/* 247:    */   {
/* 248:334 */     if (this.dtDetalleHojaRuta == null) {
/* 249:335 */       this.dtDetalleHojaRuta = new DataTable();
/* 250:    */     }
/* 251:337 */     return this.dtDetalleHojaRuta;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setDtDetalleHojaRuta(DataTable dtDetalleHojaRuta)
/* 255:    */   {
/* 256:347 */     this.dtDetalleHojaRuta = dtDetalleHojaRuta;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<Documento> getListaDocumentoHojaRuta()
/* 260:    */   {
/* 261:    */     try
/* 262:    */     {
/* 263:352 */       if (this.listaDocumentoHojaRuta == null) {
/* 264:353 */         this.listaDocumentoHojaRuta = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.HOJA_RUTA);
/* 265:    */       }
/* 266:    */     }
/* 267:    */     catch (ExcepcionAS2 e)
/* 268:    */     {
/* 269:356 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 270:    */     }
/* 271:359 */     return this.listaDocumentoHojaRuta;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public Integer getIdHojaRuta()
/* 275:    */   {
/* 276:366 */     return this.idHojaRuta;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setIdHojaRuta(Integer idHojaRuta)
/* 280:    */   {
/* 281:374 */     this.idHojaRuta = idHojaRuta;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public Date getFechaDesde()
/* 285:    */   {
/* 286:378 */     return this.fechaDesde;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setFechaDesde(Date fechaDesde)
/* 290:    */   {
/* 291:382 */     this.fechaDesde = fechaDesde;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public Date getFechaHasta()
/* 295:    */   {
/* 296:386 */     return this.fechaHasta;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setFechaHasta(Date fechaHasta)
/* 300:    */   {
/* 301:390 */     this.fechaHasta = fechaHasta;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public String eliminar()
/* 305:    */   {
/* 306:395 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 307:396 */     return "";
/* 308:    */   }
/* 309:    */   
/* 310:    */   public Sucursal getSucursal()
/* 311:    */   {
/* 312:400 */     if (this.sucursal == null) {
/* 313:401 */       this.sucursal = AppUtil.getSucursal();
/* 314:    */     }
/* 315:403 */     return this.sucursal;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setSucursal(Sucursal sucursal)
/* 319:    */   {
/* 320:407 */     this.sucursal = sucursal;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<Sucursal> getListaSucursal()
/* 324:    */   {
/* 325:411 */     if (this.listaSucursal == null) {
/* 326:412 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 327:    */     }
/* 328:415 */     return this.listaSucursal;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 332:    */   {
/* 333:419 */     this.listaSucursal = listaSucursal;
/* 334:    */   }
/* 335:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.HojaRutaBean
 * JD-Core Version:    0.7.0.1
 */