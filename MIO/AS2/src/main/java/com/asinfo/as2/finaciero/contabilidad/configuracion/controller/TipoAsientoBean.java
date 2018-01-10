/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Secuencia;
/*   8:    */ import com.asinfo.as2.entities.TipoAsiento;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoReporteAsiento;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.event.SelectEvent;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class TipoAsientoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 347729583113342964L;
/*  32:    */   @EJB
/*  33:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  34:    */   @EJB
/*  35:    */   private ServicioSecuencia servicioSecuencia;
/*  36:    */   private TipoAsiento tipoAsiento;
/*  37:    */   private LazyDataModel<TipoAsiento> listaTipoAsiento;
/*  38:    */   private List<SelectItem> listaTipoReporteAsiento;
/*  39:    */   private List<TipoAsiento> listaTipoAsientoCombo;
/*  40:    */   private List<Secuencia> listaSecuencia;
/*  41:    */   private DataTable dataTableTipoAsiento;
/*  42:    */   private Integer idTipoAsiento;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 70 */     this.listaTipoAsiento = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = 1L;
/*  50:    */       
/*  51:    */       public List<TipoAsiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 77 */         if (TipoAsientoBean.this.idTipoAsiento != null)
/*  54:    */         {
/*  55: 78 */           filters.put("idTipoAsiento", TipoAsientoBean.this.idTipoAsiento.toString());
/*  56: 79 */           TipoAsientoBean.this.idTipoAsiento = null;
/*  57:    */         }
/*  58: 82 */         List<TipoAsiento> lista = new ArrayList();
/*  59:    */         
/*  60: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  61: 85 */         lista = TipoAsientoBean.this.servicioTipoAsiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  62: 86 */         TipoAsientoBean.this.listaTipoAsiento.setRowCount(TipoAsientoBean.this.servicioTipoAsiento.contarPorCriterio(filters));
/*  63:    */         
/*  64: 88 */         return lista;
/*  65:    */       }
/*  66:    */     };
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String editar()
/*  70:    */   {
/*  71:100 */     if (getTipoAsiento().getId() > 0) {
/*  72:101 */       setEditado(true);
/*  73:    */     } else {
/*  74:103 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  75:    */     }
/*  76:106 */     return "";
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String guardar()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:115 */       this.servicioTipoAsiento.guardar(getTipoAsiento());
/*  84:    */       
/*  85:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  86:    */       
/*  87:119 */       cargarDatos();
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  92:122 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  93:    */     }
/*  94:124 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String eliminar()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:133 */       this.servicioTipoAsiento.eliminar(getTipoAsiento());
/* 102:    */       
/* 103:135 */       cargarDatos();
/* 104:136 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 109:140 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 110:    */     }
/* 111:142 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String limpiar()
/* 115:    */   {
/* 116:152 */     crearTipoAsiento();
/* 117:153 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void crearTipoAsiento()
/* 121:    */   {
/* 122:157 */     this.tipoAsiento = new TipoAsiento();
/* 123:158 */     this.tipoAsiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 124:159 */     this.tipoAsiento.setSecuencia(new Secuencia());
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String cargarDatos()
/* 128:    */   {
/* 129:    */     try
/* 130:    */     {
/* 131:167 */       setEditado(false);
/* 132:    */       
/* 133:169 */       limpiar();
/* 134:    */     }
/* 135:    */     catch (Exception e)
/* 136:    */     {
/* 137:171 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 138:172 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 139:    */     }
/* 140:174 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<TipoAsiento> obtenerListaCombo()
/* 144:    */   {
/* 145:183 */     List<TipoAsiento> lista = new ArrayList();
/* 146:184 */     String sortField = "nombre";
/* 147:185 */     lista = this.servicioTipoAsiento.obtenerListaCombo(sortField, true, null);
/* 148:186 */     return lista;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void onRowSelect(SelectEvent event)
/* 152:    */   {
/* 153:193 */     TipoAsiento tipoAsiento1 = (TipoAsiento)event.getObject();
/* 154:194 */     setTipoAsiento(tipoAsiento1);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public ServicioTipoAsiento getServicioTipoAsiento()
/* 158:    */   {
/* 159:203 */     return this.servicioTipoAsiento;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setServicioTipoAsiento(ServicioTipoAsiento servicioTipoAsientoBean)
/* 163:    */   {
/* 164:213 */     this.servicioTipoAsiento = servicioTipoAsientoBean;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public TipoAsiento getTipoAsiento()
/* 168:    */   {
/* 169:222 */     if (this.tipoAsiento == null) {
/* 170:223 */       crearTipoAsiento();
/* 171:    */     }
/* 172:225 */     return this.tipoAsiento;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setTipoAsiento(TipoAsiento tipoAsiento)
/* 176:    */   {
/* 177:235 */     this.tipoAsiento = tipoAsiento;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public DataTable getDataTableTipoAsiento()
/* 181:    */   {
/* 182:244 */     return this.dataTableTipoAsiento;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setDataTableTipoAsiento(DataTable dataTableTipoAsiento)
/* 186:    */   {
/* 187:254 */     this.dataTableTipoAsiento = dataTableTipoAsiento;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List<SelectItem> getListaTipoReporteAsiento()
/* 191:    */   {
/* 192:258 */     if (this.listaTipoReporteAsiento == null)
/* 193:    */     {
/* 194:259 */       this.listaTipoReporteAsiento = new ArrayList();
/* 195:261 */       for (TipoReporteAsiento t : TipoReporteAsiento.values())
/* 196:    */       {
/* 197:262 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 198:263 */         this.listaTipoReporteAsiento.add(item);
/* 199:    */       }
/* 200:    */     }
/* 201:266 */     return this.listaTipoReporteAsiento;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setListaTipoReporteAsiento(List<SelectItem> listaTipoReporteAsiento)
/* 205:    */   {
/* 206:270 */     this.listaTipoReporteAsiento = listaTipoReporteAsiento;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public List<TipoAsiento> getListaTipoAsientoCombo()
/* 210:    */   {
/* 211:274 */     this.listaTipoAsientoCombo = obtenerListaCombo();
/* 212:275 */     return this.listaTipoAsientoCombo;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setListaTipoAsientoCombo(List<TipoAsiento> listaTipoAsientoCombo)
/* 216:    */   {
/* 217:279 */     this.listaTipoAsientoCombo = listaTipoAsientoCombo;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public LazyDataModel<TipoAsiento> getListaTipoAsiento()
/* 221:    */   {
/* 222:288 */     return this.listaTipoAsiento;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setListaTipoAsiento(LazyDataModel<TipoAsiento> listaTipoAsiento)
/* 226:    */   {
/* 227:298 */     this.listaTipoAsiento = listaTipoAsiento;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<Secuencia> getListaSecuencia()
/* 231:    */   {
/* 232:307 */     if (this.listaSecuencia == null) {
/* 233:308 */       this.listaSecuencia = this.servicioSecuencia.obtenerListaCombo();
/* 234:    */     }
/* 235:310 */     return this.listaSecuencia;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setListaSecuencia(List<Secuencia> listaSecuencia)
/* 239:    */   {
/* 240:320 */     this.listaSecuencia = listaSecuencia;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Integer getIdTipoAsiento()
/* 244:    */   {
/* 245:329 */     return this.idTipoAsiento;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setIdTipoAsiento(Integer idTipoAsiento)
/* 249:    */   {
/* 250:339 */     this.idTipoAsiento = idTipoAsiento;
/* 251:    */   }
/* 252:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.TipoAsientoBean
 * JD-Core Version:    0.7.0.1
 */