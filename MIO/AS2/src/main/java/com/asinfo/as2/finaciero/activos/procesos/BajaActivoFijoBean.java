/*   1:    */ package com.asinfo.as2.finaciero.activos.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   6:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   7:    */ import com.asinfo.as2.entities.Depreciacion;
/*   8:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*   9:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*  14:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioMotivoBajaActivo;
/*  15:    */ import com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacion;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Collection;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.faces.context.FacesContext;
/*  31:    */ import javax.faces.context.PartialViewContext;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ import org.primefaces.component.datatable.DataTable;
/*  34:    */ import org.primefaces.context.RequestContext;
/*  35:    */ import org.primefaces.model.LazyDataModel;
/*  36:    */ import org.primefaces.model.SortOrder;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class BajaActivoFijoBean
/*  41:    */   extends PageControllerAS2
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = -4191737313019472017L;
/*  44: 62 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  45:    */   @EJB
/*  46:    */   private ServicioActivoFijo servicioActivoFijo;
/*  47:    */   @EJB
/*  48:    */   private ServicioMotivoBajaActivo servicioMotivoBajaActivo;
/*  49:    */   @EJB
/*  50:    */   private ServicioDepreciacion servicioDepreciacion;
/*  51:    */   private ActivoFijo activoFijo;
/*  52:    */   private ActivoFijo activoFijoBaja;
/*  53:    */   private String motivoBajaActivoSeleccionado;
/*  54:    */   private LazyDataModel<ActivoFijo> listaActivoFijo;
/*  55:    */   private List<MotivoBajaActivo> listaMotivoBajaActivoCombo;
/*  56:    */   private DataTable dtActivoFijo;
/*  57:    */   
/*  58:    */   @PostConstruct
/*  59:    */   public void init()
/*  60:    */   {
/*  61: 97 */     this.listaActivoFijo = new LazyDataModel()
/*  62:    */     {
/*  63:    */       private static final long serialVersionUID = 1L;
/*  64:    */       
/*  65:    */       public List<ActivoFijo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  66:    */       {
/*  67:104 */         List<ActivoFijo> lista = new ArrayList();
/*  68:105 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  69:107 */         if (filters == null) {
/*  70:108 */           filters = new HashMap();
/*  71:    */         }
/*  72:110 */         filters.put("activo", "false");
/*  73:    */         
/*  74:112 */         lista = BajaActivoFijoBean.this.servicioActivoFijo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  75:    */         
/*  76:114 */         BajaActivoFijoBean.this.listaActivoFijo.setRowCount(BajaActivoFijoBean.this.servicioActivoFijo.contarPorCriterio(filters));
/*  77:    */         
/*  78:116 */         return lista;
/*  79:    */       }
/*  80:    */     };
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void crearActivoFijoBaja()
/*  84:    */   {
/*  85:130 */     this.activoFijoBaja = new ActivoFijo();
/*  86:131 */     this.activoFijoBaja.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  87:132 */     this.activoFijoBaja.setSucursal(AppUtil.getSucursal());
/*  88:133 */     this.activoFijoBaja.setCategoriaActivo(new CategoriaActivo());
/*  89:134 */     this.activoFijoBaja.setMotivoBajaActivo(new MotivoBajaActivo());
/*  90:135 */     this.activoFijoBaja.setValorActivo(BigDecimal.ZERO);
/*  91:136 */     this.activoFijoBaja.setValorDepreciado(BigDecimal.ZERO);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String editar()
/*  95:    */   {
/*  96:145 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  97:146 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String guardar()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:156 */       if (this.activoFijoBaja.getId() != 0)
/* 105:    */       {
/* 106:157 */         this.servicioActivoFijo.guardar(this.activoFijoBaja);
/* 107:158 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 108:159 */         setEditado(false);
/* 109:160 */         limpiar();
/* 110:    */       }
/* 111:    */       else
/* 112:    */       {
/* 113:162 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar") + " Activo Fijo");
/* 114:    */       }
/* 115:    */     }
/* 116:    */     catch (ExcepcionAS2Financiero e)
/* 117:    */     {
/* 118:166 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 119:167 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 120:    */     }
/* 121:    */     catch (ExcepcionAS2 e)
/* 122:    */     {
/* 123:170 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 124:171 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 125:    */     }
/* 126:    */     catch (AS2Exception e)
/* 127:    */     {
/* 128:174 */       e.printStackTrace();
/* 129:175 */       this.exContabilizacion = e;
/* 130:176 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 131:177 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 132:    */     }
/* 133:    */     catch (Exception e)
/* 134:    */     {
/* 135:179 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 136:180 */       e.printStackTrace();
/* 137:    */     }
/* 138:182 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String eliminar()
/* 142:    */   {
/* 143:    */     try
/* 144:    */     {
/* 145:192 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 146:    */     }
/* 147:    */     catch (Exception e)
/* 148:    */     {
/* 149:194 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 150:195 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 151:    */     }
/* 152:197 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String cargarDatos()
/* 156:    */   {
/* 157:206 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String limpiar()
/* 161:    */   {
/* 162:215 */     crearActivoFijoBaja();
/* 163:216 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String cargarActivoFijo()
/* 167:    */   {
/* 168:227 */     setActivoFijoBaja(this.servicioActivoFijo.cargarDetalle(this.activoFijo.getIdActivoFijo()));
/* 169:228 */     this.activoFijoBaja.setMotivoBajaActivo(new MotivoBajaActivo());
/* 170:229 */     this.activoFijoBaja.setActivo(false);
/* 171:    */     
/* 172:    */ 
/* 173:232 */     Depreciacion depreciacionFiscal = this.servicioDepreciacion.obtenerDepreciacionActivo(this.activoFijoBaja.getId(), true);
/* 174:233 */     Depreciacion depreciacionNIIF = this.servicioDepreciacion.obtenerDepreciacionActivo(this.activoFijoBaja.getId(), false);
/* 175:    */     
/* 176:    */ 
/* 177:236 */     DetalleDepreciacion detalleDepreciacionFiscal = null;
/* 178:237 */     DetalleDepreciacion detalleDepreciacionNIFF = null;
/* 179:238 */     Date fechaNIIF = null;
/* 180:239 */     Date fechaFiscal = null;
/* 181:242 */     if (depreciacionFiscal != null)
/* 182:    */     {
/* 183:243 */       detalleDepreciacionFiscal = this.servicioDepreciacion.obtenerUltimoDetalleDepreciacionDepreciado(depreciacionFiscal.getId());
/* 184:244 */       if (detalleDepreciacionFiscal != null) {
/* 185:245 */         fechaFiscal = detalleDepreciacionFiscal.getFecha();
/* 186:    */       }
/* 187:    */     }
/* 188:249 */     if (depreciacionNIIF != null)
/* 189:    */     {
/* 190:250 */       detalleDepreciacionNIFF = this.servicioDepreciacion.obtenerUltimoDetalleDepreciacionDepreciado(depreciacionNIIF.getId());
/* 191:251 */       if (detalleDepreciacionNIFF != null) {
/* 192:252 */         fechaFiscal = detalleDepreciacionFiscal.getFecha();
/* 193:    */       }
/* 194:    */     }
/* 195:256 */     if ((fechaFiscal != null) && (fechaNIIF != null))
/* 196:    */     {
/* 197:257 */       this.activoFijoBaja.setFechaBaja(FuncionesUtiles.obtenerFechaMaxima(fechaFiscal, fechaNIIF));
/* 198:258 */       return "";
/* 199:    */     }
/* 200:259 */     if ((fechaFiscal != null) && (fechaNIIF == null))
/* 201:    */     {
/* 202:260 */       this.activoFijoBaja.setFechaBaja(fechaFiscal);
/* 203:261 */       return "";
/* 204:    */     }
/* 205:262 */     if ((fechaFiscal == null) && (fechaNIIF != null))
/* 206:    */     {
/* 207:263 */       this.activoFijoBaja.setFechaBaja(fechaNIIF);
/* 208:264 */       return "";
/* 209:    */     }
/* 210:265 */     if (fechaFiscal == null)
/* 211:    */     {
/* 212:266 */       this.activoFijoBaja.setFechaBaja(new Date());
/* 213:267 */       return "";
/* 214:    */     }
/* 215:269 */     setActivoFijo(new ActivoFijo());
/* 216:270 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 217:    */     
/* 218:    */ 
/* 219:273 */     return "";
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void actualizaMotivoBaja()
/* 223:    */   {
/* 224:284 */     MotivoBajaActivo motivoBajaActivo = this.servicioMotivoBajaActivo.buscarPorId(this.activoFijoBaja.getMotivoBajaActivo().getIdMotivoBajaActivo());
/* 225:285 */     this.activoFijoBaja.setMotivoBajaActivo(motivoBajaActivo);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public ActivoFijo getActivoFijo()
/* 229:    */   {
/* 230:298 */     return this.activoFijo;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 234:    */   {
/* 235:308 */     this.activoFijo = activoFijo;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public LazyDataModel<ActivoFijo> getListaActivoFijo()
/* 239:    */   {
/* 240:317 */     return this.listaActivoFijo;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setListaActivoFijo(LazyDataModel<ActivoFijo> listaActivoFijo)
/* 244:    */   {
/* 245:327 */     this.listaActivoFijo = listaActivoFijo;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public List<MotivoBajaActivo> getListaMotivoBajaActivoCombo()
/* 249:    */   {
/* 250:336 */     if (this.listaMotivoBajaActivoCombo == null) {
/* 251:337 */       this.listaMotivoBajaActivoCombo = this.servicioMotivoBajaActivo.obtenerListaCombo("nombre", true, null);
/* 252:    */     }
/* 253:339 */     return this.listaMotivoBajaActivoCombo;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setListaMotivoBajaActivoCombo(List<MotivoBajaActivo> listaMotivoBajaActivoCombo)
/* 257:    */   {
/* 258:349 */     this.listaMotivoBajaActivoCombo = listaMotivoBajaActivoCombo;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public DataTable getDtActivoFijo()
/* 262:    */   {
/* 263:358 */     return this.dtActivoFijo;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setDtActivoFijo(DataTable dtActivoFijo)
/* 267:    */   {
/* 268:368 */     this.dtActivoFijo = dtActivoFijo;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getMotivoBajaActivoSeleccionado()
/* 272:    */   {
/* 273:377 */     return this.motivoBajaActivoSeleccionado;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setMotivoBajaActivoSeleccionado(String motivoBajaActivoSeleccionado)
/* 277:    */   {
/* 278:387 */     this.motivoBajaActivoSeleccionado = motivoBajaActivoSeleccionado;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public ActivoFijo getActivoFijoBaja()
/* 282:    */   {
/* 283:396 */     if (this.activoFijoBaja == null) {
/* 284:397 */       crearActivoFijoBaja();
/* 285:    */     }
/* 286:399 */     return this.activoFijoBaja;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setActivoFijoBaja(ActivoFijo activoFijoBaja)
/* 290:    */   {
/* 291:409 */     this.activoFijoBaja = activoFijoBaja;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public AS2Exception getExContabilizacion()
/* 295:    */   {
/* 296:413 */     return this.exContabilizacion;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 300:    */   {
/* 301:417 */     this.exContabilizacion = exContabilizacion;
/* 302:    */   }
/* 303:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.procesos.BajaActivoFijoBean
 * JD-Core Version:    0.7.0.1
 */