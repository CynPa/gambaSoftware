/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.Actividad;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.old.Criticidad;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.old.ListaVerificacion;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.old.Tarea;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.old.TarifaSalarial;
/*  12:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioActividad;
/*  13:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioCriticidad;
/*  14:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioListaVerificacion;
/*  15:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTarifaSalarial;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ActividadBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  35:    */   @EJB
/*  36:    */   private ServicioActividad servicioActividad;
/*  37:    */   @EJB
/*  38:    */   private ServicioTarifaSalarial servicioTarifaSalarial;
/*  39:    */   @EJB
/*  40:    */   private ServicioCriticidad servicioCriticidad;
/*  41:    */   @EJB
/*  42:    */   private ServicioListaVerificacion servicioListaVerificacion;
/*  43:    */   private Actividad actividad;
/*  44:    */   private LazyDataModel<Actividad> listaActividad;
/*  45:    */   private List<TarifaSalarial> listaTarifaSalarial;
/*  46:    */   private List<Criticidad> listaCriticidad;
/*  47:    */   private List<ListaVerificacion> listaListaVerificacion;
/*  48:    */   private DataTable dtActividad;
/*  49:    */   private DataTable dtTarea;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 92 */     this.listaActividad = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = 1L;
/*  57:    */       
/*  58:    */       public List<Actividad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 99 */         List<Actividad> lista = new ArrayList();
/*  61:100 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  62:    */         
/*  63:102 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  64:103 */         lista = ActividadBean.this.servicioActividad.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  65:    */         
/*  66:105 */         ActividadBean.this.listaActividad.setRowCount(ActividadBean.this.servicioActividad.contarPorCriterio(filters));
/*  67:    */         
/*  68:107 */         return lista;
/*  69:    */       }
/*  70:    */     };
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void crearEntidad()
/*  74:    */   {
/*  75:125 */     this.actividad = new Actividad();
/*  76:126 */     this.actividad.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  77:127 */     this.actividad.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String editar()
/*  81:    */   {
/*  82:136 */     if (getActividad().getIdActividad() > 0)
/*  83:    */     {
/*  84:137 */       this.actividad = this.servicioActividad.cargarDetalle(this.actividad.getIdActividad());
/*  85:138 */       setEditado(true);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:140 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  90:    */     }
/*  91:142 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String guardar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:152 */       this.servicioActividad.guardar(this.actividad);
/*  99:153 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 100:154 */       limpiar();
/* 101:155 */       setEditado(false);
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 106:158 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 107:    */     }
/* 108:160 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String eliminar()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:170 */       this.servicioActividad.eliminar(this.actividad);
/* 116:171 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:173 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 121:174 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 122:    */     }
/* 123:176 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String cargarDatos()
/* 127:    */   {
/* 128:185 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String limpiar()
/* 132:    */   {
/* 133:194 */     crearEntidad();
/* 134:195 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void agregarTarea()
/* 138:    */   {
/* 139:202 */     Tarea tarea = new Tarea();
/* 140:203 */     tarea.setActividad(this.actividad);
/* 141:204 */     this.actividad.getListaTarea().add(tarea);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void eliminarTarea()
/* 145:    */   {
/* 146:211 */     Tarea tarea = (Tarea)this.dtTarea.getRowData();
/* 147:212 */     tarea.setEliminado(true);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<Tarea> getListaTarea()
/* 151:    */   {
/* 152:216 */     List<Tarea> lista = new ArrayList();
/* 153:218 */     for (Tarea tarea : this.actividad.getListaTarea()) {
/* 154:219 */       if (!tarea.isEliminado()) {
/* 155:220 */         lista.add(tarea);
/* 156:    */       }
/* 157:    */     }
/* 158:224 */     return lista;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Actividad getActividad()
/* 162:    */   {
/* 163:237 */     return this.actividad;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setActividad(Actividad actividad)
/* 167:    */   {
/* 168:247 */     this.actividad = actividad;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public LazyDataModel<Actividad> getListaActividad()
/* 172:    */   {
/* 173:256 */     return this.listaActividad;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setListaActividad(LazyDataModel<Actividad> listaActividad)
/* 177:    */   {
/* 178:266 */     this.listaActividad = listaActividad;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public DataTable getDtActividad()
/* 182:    */   {
/* 183:275 */     return this.dtActividad;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setDtActividad(DataTable dtActividad)
/* 187:    */   {
/* 188:285 */     this.dtActividad = dtActividad;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public DataTable getDtTarea()
/* 192:    */   {
/* 193:294 */     return this.dtTarea;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setDtTarea(DataTable dtTarea)
/* 197:    */   {
/* 198:304 */     this.dtTarea = dtTarea;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<TarifaSalarial> getListaTarifaSalarial()
/* 202:    */   {
/* 203:313 */     if (this.listaTarifaSalarial == null) {
/* 204:314 */       this.listaTarifaSalarial = this.servicioTarifaSalarial.obtenerListaCombo("nombre", true, null);
/* 205:    */     }
/* 206:316 */     return this.listaTarifaSalarial;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setListaTarifaSalarial(List<TarifaSalarial> listaTarifaSalarial)
/* 210:    */   {
/* 211:326 */     this.listaTarifaSalarial = listaTarifaSalarial;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<Criticidad> getListaCriticidad()
/* 215:    */   {
/* 216:335 */     if (this.listaCriticidad == null) {
/* 217:336 */       this.listaCriticidad = this.servicioCriticidad.obtenerListaCombo("nombre", true, null);
/* 218:    */     }
/* 219:338 */     return this.listaCriticidad;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setListaCriticidad(List<Criticidad> listaCriticidad)
/* 223:    */   {
/* 224:348 */     this.listaCriticidad = listaCriticidad;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public List<ListaVerificacion> getListaListaVerificacion()
/* 228:    */   {
/* 229:356 */     if (this.listaListaVerificacion == null) {
/* 230:357 */       this.listaListaVerificacion = this.servicioListaVerificacion.obtenerListaCombo("nombre", true, null);
/* 231:    */     }
/* 232:359 */     return this.listaListaVerificacion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setListaListaVerificacion(List<ListaVerificacion> listaListaVerificacion)
/* 236:    */   {
/* 237:367 */     this.listaListaVerificacion = listaListaVerificacion;
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.ActividadBean
 * JD-Core Version:    0.7.0.1
 */