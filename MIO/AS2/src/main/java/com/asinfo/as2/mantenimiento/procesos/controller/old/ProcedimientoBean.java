/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.Actividad;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.old.Procedimiento;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.old.ProcedimientoActividad;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.old.TipoServicioMantenimiento;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoMantenimiento;
/*  12:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioActividad;
/*  13:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioProcedimiento;
/*  14:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoServicioMantenimiento;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import javax.faces.model.SelectItem;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ProcedimientoBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  35:    */   @EJB
/*  36:    */   private ServicioProcedimiento servicioProcedimiento;
/*  37:    */   @EJB
/*  38:    */   private ServicioTipoServicioMantenimiento servicioTipoServicioMantenimiento;
/*  39:    */   @EJB
/*  40:    */   private ServicioActividad servicioActividad;
/*  41:    */   private Procedimiento procedimiento;
/*  42:    */   private List<TipoServicioMantenimiento> listaTipoServicioMantenimiento;
/*  43:    */   private List<Actividad> listaActividad;
/*  44:    */   private List<SelectItem> listaTipoMantenimiento;
/*  45:    */   private ProcedimientoActividad procedimientoActividad;
/*  46:    */   private LazyDataModel<Procedimiento> listaMantenimiento;
/*  47:    */   private DataTable dtMantenimiento;
/*  48:    */   private DataTable dtProcedimientoActividad;
/*  49:    */   private DataTable dtObjeto;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 92 */     this.listaMantenimiento = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = 1L;
/*  57:    */       
/*  58:    */       public List<Procedimiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 99 */         List<Procedimiento> lista = new ArrayList();
/*  61:100 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  62:    */         
/*  63:102 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  64:103 */         lista = ProcedimientoBean.this.servicioProcedimiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  65:    */         
/*  66:105 */         ProcedimientoBean.this.listaMantenimiento.setRowCount(ProcedimientoBean.this.servicioProcedimiento.contarPorCriterio(filters));
/*  67:    */         
/*  68:107 */         return lista;
/*  69:    */       }
/*  70:    */     };
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void cargarObjeto()
/*  74:    */   {
/*  75:121 */     this.procedimiento.setTipoServicioMantenimiento((TipoServicioMantenimiento)this.dtObjeto.getRowData());
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void cargarDetalleActividad()
/*  79:    */   {
/*  80:126 */     Actividad actividad = this.procedimientoActividad.getActividad();
/*  81:127 */     actividad = this.servicioActividad.cargarDetalle(actividad.getIdActividad());
/*  82:128 */     this.procedimientoActividad.setActividad(actividad);
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void crearMantenimiento()
/*  86:    */   {
/*  87:135 */     this.procedimiento = new Procedimiento();
/*  88:136 */     this.procedimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  89:137 */     this.procedimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String editar()
/*  93:    */   {
/*  94:146 */     if (getProcedimiento().getIdProcedimiento() > 0)
/*  95:    */     {
/*  96:147 */       this.procedimiento = this.servicioProcedimiento.cargarDetalle(this.procedimiento.getIdProcedimiento());
/*  97:148 */       setEditado(true);
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:150 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 102:    */     }
/* 103:152 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String guardar()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:162 */       this.servicioProcedimiento.guardar(this.procedimiento);
/* 111:163 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 112:164 */       limpiar();
/* 113:165 */       setEditado(false);
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 118:168 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 119:    */     }
/* 120:170 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String eliminar()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:180 */       this.servicioProcedimiento.eliminar(this.procedimiento);
/* 128:181 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 129:    */     }
/* 130:    */     catch (Exception e)
/* 131:    */     {
/* 132:183 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 133:184 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 134:    */     }
/* 135:186 */     return "";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String cargarDatos()
/* 139:    */   {
/* 140:195 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String limpiar()
/* 144:    */   {
/* 145:204 */     crearMantenimiento();
/* 146:205 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String agregarProcedimientoActividad()
/* 150:    */   {
/* 151:214 */     ProcedimientoActividad procedimientoActividad = new ProcedimientoActividad();
/* 152:215 */     procedimientoActividad.setProcedimiento(this.procedimiento);
/* 153:216 */     this.procedimiento.getListaProcedimientoActividad().add(procedimientoActividad);
/* 154:217 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String eliminarActividad()
/* 158:    */   {
/* 159:226 */     ProcedimientoActividad procedimientoActividad = (ProcedimientoActividad)this.dtProcedimientoActividad.getRowData();
/* 160:227 */     procedimientoActividad.setEliminado(true);
/* 161:228 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public LazyDataModel<Procedimiento> getListaMantenimiento()
/* 165:    */   {
/* 166:241 */     return this.listaMantenimiento;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Procedimiento getProcedimiento()
/* 170:    */   {
/* 171:250 */     return this.procedimiento;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setProcedimiento(Procedimiento procedimiento)
/* 175:    */   {
/* 176:260 */     this.procedimiento = procedimiento;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setListaMantenimiento(LazyDataModel<Procedimiento> listaMantenimiento)
/* 180:    */   {
/* 181:270 */     this.listaMantenimiento = listaMantenimiento;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public DataTable getDtMantenimiento()
/* 185:    */   {
/* 186:279 */     return this.dtMantenimiento;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setDtMantenimiento(DataTable dtMantenimiento)
/* 190:    */   {
/* 191:289 */     this.dtMantenimiento = dtMantenimiento;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<TipoServicioMantenimiento> getListaTipoServicioMantenimiento()
/* 195:    */   {
/* 196:298 */     if (this.listaTipoServicioMantenimiento == null) {
/* 197:299 */       this.listaTipoServicioMantenimiento = this.servicioTipoServicioMantenimiento.obtenerListaCombo("nombre", true, null);
/* 198:    */     }
/* 199:301 */     return this.listaTipoServicioMantenimiento;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setListaTipoServicioMantenimiento(List<TipoServicioMantenimiento> listaTipoServicioMantenimiento)
/* 203:    */   {
/* 204:311 */     this.listaTipoServicioMantenimiento = listaTipoServicioMantenimiento;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public DataTable getDtProcedimientoActividad()
/* 208:    */   {
/* 209:320 */     return this.dtProcedimientoActividad;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setDtProcedimientoActividad(DataTable dtProcedimientoActividad)
/* 213:    */   {
/* 214:330 */     this.dtProcedimientoActividad = dtProcedimientoActividad;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public DataTable getDtObjeto()
/* 218:    */   {
/* 219:339 */     return this.dtObjeto;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setDtObjeto(DataTable dtObjeto)
/* 223:    */   {
/* 224:349 */     this.dtObjeto = dtObjeto;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public List<SelectItem> getListaTipoMantenimiento()
/* 228:    */   {
/* 229:358 */     if (this.listaTipoMantenimiento == null)
/* 230:    */     {
/* 231:359 */       this.listaTipoMantenimiento = new ArrayList();
/* 232:361 */       for (TipoMantenimiento t : TipoMantenimiento.values())
/* 233:    */       {
/* 234:362 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 235:363 */         this.listaTipoMantenimiento.add(item);
/* 236:    */       }
/* 237:    */     }
/* 238:366 */     return this.listaTipoMantenimiento;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public ProcedimientoActividad getProcedimientoActividad()
/* 242:    */   {
/* 243:375 */     if (this.procedimientoActividad == null) {
/* 244:376 */       this.procedimientoActividad = new ProcedimientoActividad();
/* 245:    */     }
/* 246:378 */     return this.procedimientoActividad;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setProcedimientoActividad(ProcedimientoActividad procedimientoActividad)
/* 250:    */   {
/* 251:388 */     this.procedimientoActividad = procedimientoActividad;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public List<ProcedimientoActividad> getListaProcedimientoActividad()
/* 255:    */   {
/* 256:392 */     List<ProcedimientoActividad> lista = new ArrayList();
/* 257:393 */     for (ProcedimientoActividad procedimientoActividad : this.procedimiento.getListaProcedimientoActividad()) {
/* 258:394 */       if (!procedimientoActividad.isEliminado()) {
/* 259:395 */         lista.add(procedimientoActividad);
/* 260:    */       }
/* 261:    */     }
/* 262:398 */     return lista;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public List<Actividad> getListaActividad()
/* 266:    */   {
/* 267:407 */     if (this.listaActividad == null) {
/* 268:408 */       this.listaActividad = this.servicioActividad.obtenerListaCombo("nombre", true, null);
/* 269:    */     }
/* 270:410 */     return this.listaActividad;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<Actividad> autocompletarActividad(String consulta)
/* 274:    */   {
/* 275:414 */     return this.servicioActividad.autocompletarActividad(consulta);
/* 276:    */   }
/* 277:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.controller.old.ProcedimientoBean
 * JD-Core Version:    0.7.0.1
 */