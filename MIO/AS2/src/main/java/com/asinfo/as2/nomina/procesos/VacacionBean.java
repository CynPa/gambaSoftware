/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Vacacion;
/*  12:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  13:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  14:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioVacacion;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.event.SelectEvent;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class VacacionBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @EJB
/*  36:    */   private ServicioVacacion servicioVacacion;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   private Vacacion vacacion;
/*  40:    */   private Empresa empresa;
/*  41: 63 */   private boolean indicadorAnticipoVacacion = true;
/*  42:    */   private LazyDataModel<Vacacion> listaVacacion;
/*  43:    */   private List<Vacacion> vacaciones;
/*  44:    */   private DataTable dtVacacion;
/*  45:    */   
/*  46:    */   @PostConstruct
/*  47:    */   public void init()
/*  48:    */   {
/*  49: 84 */     this.listaVacacion = new LazyDataModel()
/*  50:    */     {
/*  51:    */       private static final long serialVersionUID = 1L;
/*  52:    */       
/*  53:    */       public List<Vacacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  54:    */       {
/*  55: 91 */         if (!filters.containsKey("historicoEmpleado.fechaSalida"))
/*  56:    */         {
/*  57: 92 */           filters.put("historicoEmpleado.fechaSalida", OperacionEnum.IS_NULL.toString());
/*  58:    */         }
/*  59:    */         else
/*  60:    */         {
/*  61: 94 */           String activo = (String)filters.get("historicoEmpleado.fechaSalida");
/*  62: 95 */           filters.put("historicoEmpleado.fechaSalida", "true".equalsIgnoreCase(activo.trim()) ? OperacionEnum.IS_NULL.toString() : OperacionEnum.IS_NOT_NULL
/*  63: 96 */             .toString());
/*  64:    */         }
/*  65: 99 */         List<Vacacion> lista = new ArrayList();
/*  66:100 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  67:101 */         lista = VacacionBean.this.servicioVacacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  68:    */         
/*  69:103 */         VacacionBean.this.listaVacacion.setRowCount(VacacionBean.this.servicioVacacion.contarPorCriterio(filters));
/*  70:    */         
/*  71:105 */         return lista;
/*  72:    */       }
/*  73:    */     };
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void crearVacacion()
/*  77:    */   {
/*  78:120 */     this.vacacion = new Vacacion();
/*  79:121 */     this.vacacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  80:122 */     this.vacacion.setIdSucursal(AppUtil.getSucursal().getId());
/*  81:123 */     this.vacacion.setHistoricoEmpleado(new HistoricoEmpleado());
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String editar()
/*  85:    */   {
/*  86:132 */     if (getVacacion().getIdVacacion() > 0) {
/*  87:133 */       setEditado(true);
/*  88:    */     } else {
/*  89:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  90:    */     }
/*  91:137 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String guardar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:147 */       this.servicioVacacion.guardar(this.vacacion);
/*  99:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 100:149 */       setEditado(false);
/* 101:150 */       limpiar();
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 106:153 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 107:    */     }
/* 108:155 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String eliminar()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:165 */       this.servicioVacacion.eliminar(this.vacacion);
/* 116:166 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:168 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 121:169 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 122:    */     }
/* 123:171 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String cargarDatos()
/* 127:    */   {
/* 128:181 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String limpiar()
/* 132:    */   {
/* 133:190 */     crearVacacion();
/* 134:191 */     this.empresa = new Empresa();
/* 135:192 */     return "";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String generarVacaciones()
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:202 */       this.servicioVacacion.generarVacaciones(AppUtil.getOrganizacion().getIdOrganizacion(), null, true);
/* 143:203 */       setIndicadorAnticipoVacacion(false);
/* 144:    */     }
/* 145:    */     catch (Exception e)
/* 146:    */     {
/* 147:206 */       e.printStackTrace();
/* 148:    */     }
/* 149:209 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void generarAnticipo(SelectEvent event)
/* 153:    */   {
/* 154:    */     try
/* 155:    */     {
/* 156:216 */       Empresa empresa = (Empresa)event.getObject();
/* 157:217 */       setEmpresa(empresa);
/* 158:218 */       int idEmpleado = getEmpresa().getEmpleado().getId();
/* 159:219 */       setVacacion(this.servicioVacacion.generarAnticipoVacacion(AppUtil.getOrganizacion().getId(), idEmpleado));
/* 160:    */     }
/* 161:    */     catch (ExcepcionAS2Nomina e)
/* 162:    */     {
/* 163:222 */       limpiar();
/* 164:223 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 165:224 */       LOG.info("ERROR AL GUARDAR ANTCIPO VACACION", e);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public List<Empresa> autocompletarEmpleados(String consulta)
/* 170:    */   {
/* 171:230 */     return this.servicioEmpresa.autocompletarEmpleados(consulta);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Vacacion getVacacion()
/* 175:    */   {
/* 176:251 */     if (this.vacacion == null) {
/* 177:252 */       this.vacacion = new Vacacion();
/* 178:    */     }
/* 179:254 */     return this.vacacion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setVacacion(Vacacion vacacion)
/* 183:    */   {
/* 184:264 */     this.vacacion = vacacion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Empresa getEmpresa()
/* 188:    */   {
/* 189:273 */     if (this.empresa == null) {
/* 190:274 */       this.empresa = new Empresa();
/* 191:    */     }
/* 192:276 */     return this.empresa;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setEmpresa(Empresa empresa)
/* 196:    */   {
/* 197:286 */     this.empresa = empresa;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isIndicadorAnticipoVacacion()
/* 201:    */   {
/* 202:295 */     return this.indicadorAnticipoVacacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setIndicadorAnticipoVacacion(boolean indicadorAnticipoVacacion)
/* 206:    */   {
/* 207:305 */     this.indicadorAnticipoVacacion = indicadorAnticipoVacacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<Vacacion> getVacaciones()
/* 211:    */   {
/* 212:314 */     if (this.vacaciones == null) {
/* 213:315 */       this.vacaciones = new ArrayList();
/* 214:    */     }
/* 215:317 */     return this.vacaciones;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setVacaciones(List<Vacacion> vacaciones)
/* 219:    */   {
/* 220:327 */     this.vacaciones = vacaciones;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public LazyDataModel<Vacacion> getListaVacacion()
/* 224:    */   {
/* 225:336 */     return this.listaVacacion;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setListaVacacion(LazyDataModel<Vacacion> listaVacacion)
/* 229:    */   {
/* 230:346 */     this.listaVacacion = listaVacacion;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public DataTable getDtVacacion()
/* 234:    */   {
/* 235:355 */     return this.dtVacacion;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setDtVacacion(DataTable dtVacacion)
/* 239:    */   {
/* 240:365 */     this.dtVacacion = dtVacacion;
/* 241:    */   }
/* 242:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.VacacionBean
 * JD-Core Version:    0.7.0.1
 */