/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.Unidad;
/*   8:    */ import com.asinfo.as2.entities.UnidadConversion;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.event.SelectEvent;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class UnidadBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 361732406456184593L;
/*  35:    */   @EJB
/*  36:    */   private ServicioUnidad servicioUnidad;
/*  37:    */   private Unidad unidad;
/*  38:    */   private DataTable dataTableUnidad;
/*  39:    */   private DataTable dtUnidadConversion;
/*  40:    */   private LazyDataModel<Unidad> listaUnidad;
/*  41:    */   private List<Unidad> listaUnidadDestino;
/*  42:    */   private List<SelectItem> listaTipoUnidadMedida;
/*  43:    */   private BigDecimal valorIncremento;
/*  44:    */   
/*  45:    */   @PostConstruct
/*  46:    */   public void init()
/*  47:    */   {
/*  48: 76 */     this.listaUnidad = new LazyDataModel()
/*  49:    */     {
/*  50:    */       private static final long serialVersionUID = -7197895094631249580L;
/*  51:    */       
/*  52:    */       public List<Unidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  53:    */       {
/*  54: 88 */         List<Unidad> lista = new ArrayList();
/*  55:    */         
/*  56: 90 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  57:    */         
/*  58: 92 */         lista = UnidadBean.this.servicioUnidad.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  59:    */         
/*  60: 94 */         UnidadBean.this.listaUnidad.setRowCount(UnidadBean.this.servicioUnidad.contarPorCriterio(filters));
/*  61: 95 */         return lista;
/*  62:    */       }
/*  63:    */     };
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String editar()
/*  67:    */   {
/*  68:109 */     if (this.unidad.getIdUnidad() > 0)
/*  69:    */     {
/*  70:110 */       setEditado(true);
/*  71:111 */       this.unidad = this.servicioUnidad.cargarDetalle(this.unidad.getIdUnidad());
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75:113 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  76:    */     }
/*  77:116 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String guardar()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:127 */       this.servicioUnidad.guardar(this.unidad);
/*  85:128 */       limpiar();
/*  86:129 */       setEditado(false);
/*  87:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  92:133 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  93:    */     }
/*  94:136 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String eliminar()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:147 */       this.servicioUnidad.eliminar(this.unidad);
/* 102:148 */       limpiar();
/* 103:149 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 108:152 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 109:    */     }
/* 110:155 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String cargarDatos()
/* 114:    */   {
/* 115:165 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void crearUnidad()
/* 119:    */   {
/* 120:169 */     this.unidad = new Unidad();
/* 121:170 */     this.unidad.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 122:171 */     this.unidad.setIdSucursal(AppUtil.getSucursal().getId());
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String agregarDetalle()
/* 126:    */   {
/* 127:180 */     UnidadConversion unidadConversion = new UnidadConversion();
/* 128:181 */     unidadConversion.setUnidadOrigen(getUnidad());
/* 129:182 */     getUnidad().getListaUnidadConversion().add(unidadConversion);
/* 130:    */     
/* 131:184 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String eliminarDetalleConversion()
/* 135:    */   {
/* 136:188 */     UnidadConversion i = (UnidadConversion)this.dtUnidadConversion.getRowData();
/* 137:189 */     i.setEliminado(true);
/* 138:190 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<Unidad> autocompletarUnidades(String consulta)
/* 142:    */   {
/* 143:201 */     List<Unidad> lista = new ArrayList();
/* 144:202 */     HashMap<String, String> filters = new HashMap();
/* 145:203 */     filters.put("nombre", consulta.trim());
/* 146:204 */     lista = this.servicioUnidad.obtenerListaCombo("nombre", true, filters);
/* 147:    */     
/* 148:206 */     return lista;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String limpiar()
/* 152:    */   {
/* 153:214 */     crearUnidad();
/* 154:215 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void onRowSelect(SelectEvent event)
/* 158:    */   {
/* 159:222 */     Unidad unidad = (Unidad)event.getObject();
/* 160:223 */     setUnidad(unidad);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public ServicioUnidad getServicioUnidadBean()
/* 164:    */   {
/* 165:227 */     return this.servicioUnidad;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setServicioUnidadBean(ServicioUnidad servicioUnidad)
/* 169:    */   {
/* 170:231 */     this.servicioUnidad = servicioUnidad;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Unidad getUnidad()
/* 174:    */   {
/* 175:235 */     if (this.unidad == null) {
/* 176:236 */       crearUnidad();
/* 177:    */     }
/* 178:239 */     return this.unidad;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setUnidad(Unidad unidad)
/* 182:    */   {
/* 183:243 */     this.unidad = unidad;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public DataTable getDataTableUnidad()
/* 187:    */   {
/* 188:247 */     return this.dataTableUnidad;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDataTableUnidad(DataTable dataTableUnidad)
/* 192:    */   {
/* 193:251 */     this.dataTableUnidad = dataTableUnidad;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<UnidadConversion> getListaUnidadConversion()
/* 197:    */   {
/* 198:260 */     List<UnidadConversion> lista = new ArrayList();
/* 199:262 */     for (UnidadConversion unidadConversion : getUnidad().getListaUnidadConversion()) {
/* 200:263 */       if (!unidadConversion.isEliminado()) {
/* 201:264 */         lista.add(unidadConversion);
/* 202:    */       }
/* 203:    */     }
/* 204:267 */     return lista;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public BigDecimal getValorIncremento()
/* 208:    */   {
/* 209:276 */     this.valorIncremento = ParametrosSistema.getIntervaloIncrementoValorConversionUnidad();
/* 210:277 */     return this.valorIncremento;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<Unidad> getListaUnidadDestino()
/* 214:    */   {
/* 215:286 */     if (this.listaUnidadDestino == null) {
/* 216:288 */       this.listaUnidadDestino = new ArrayList();
/* 217:    */     }
/* 218:291 */     return this.listaUnidadDestino;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setListaUnidadDestino(List<Unidad> listaUnidadDestino)
/* 222:    */   {
/* 223:301 */     this.listaUnidadDestino = listaUnidadDestino;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public LazyDataModel<Unidad> getListaUnidad()
/* 227:    */   {
/* 228:310 */     return this.listaUnidad;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setListaUnidad(LazyDataModel<Unidad> listaUnidad)
/* 232:    */   {
/* 233:320 */     this.listaUnidad = listaUnidad;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<SelectItem> getListaTipoUnidadMedida()
/* 237:    */   {
/* 238:329 */     if (this.listaTipoUnidadMedida == null)
/* 239:    */     {
/* 240:330 */       this.listaTipoUnidadMedida = new ArrayList();
/* 241:332 */       for (TipoUnidadMedida t : TipoUnidadMedida.values())
/* 242:    */       {
/* 243:333 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 244:334 */         this.listaTipoUnidadMedida.add(item);
/* 245:    */       }
/* 246:    */     }
/* 247:337 */     return this.listaTipoUnidadMedida;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setListaTipoUnidadMedida(List<SelectItem> listaTipoUnidadMedida)
/* 251:    */   {
/* 252:347 */     this.listaTipoUnidadMedida = listaTipoUnidadMedida;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public DataTable getDtUnidadConversion()
/* 256:    */   {
/* 257:356 */     return this.dtUnidadConversion;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setDtUnidadConversion(DataTable dtUnidadConversion)
/* 261:    */   {
/* 262:366 */     this.dtUnidadConversion = dtUnidadConversion;
/* 263:    */   }
/* 264:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.UnidadBean
 * JD-Core Version:    0.7.0.1
 */