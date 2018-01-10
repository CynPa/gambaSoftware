/*   1:    */ package com.asinfo.as2.nomina.asistencia.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.dao.EmpleadoDao;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo;
/*  11:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  12:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.component.datatable.DataTable;
/*  24:    */ import org.primefaces.model.LazyDataModel;
/*  25:    */ import org.primefaces.model.SortOrder;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class GrupoTrabajoEmpleadoBean
/*  30:    */   extends PageControllerAS2
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private ServicioEmpleado servicioEmpleado;
/*  35:    */   @EJB
/*  36:    */   private ServicioGenerico<GrupoTrabajo> servicioGrupoTrabajo;
/*  37:    */   @EJB
/*  38:    */   private EmpleadoDao empleadoDao;
/*  39:    */   private GrupoTrabajo grupoTrabajo;
/*  40:    */   private boolean seleccionarTodos;
/*  41:    */   private Empleado empleado;
/*  42:    */   private DataTable dtEmpleado;
/*  43:    */   private DataTable dtGrupoTrabajo;
/*  44:    */   private LazyDataModel<GrupoTrabajo> listaGrupoTrabajo;
/*  45:    */   private HashMap<String, Empleado> hmEmpleados;
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 82 */     this.listaGrupoTrabajo = new LazyDataModel()
/*  51:    */     {
/*  52:    */       private static final long serialVersionUID = -1752987002238164010L;
/*  53:    */       
/*  54:    */       public List<GrupoTrabajo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  55:    */       {
/*  56: 88 */         List<GrupoTrabajo> lista = new ArrayList();
/*  57: 89 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  58:    */         
/*  59: 91 */         lista = GrupoTrabajoEmpleadoBean.this.servicioGrupoTrabajo.obtenerListaPorPagina(GrupoTrabajo.class, startIndex, pageSize, sortField, ordenar, filters);
/*  60: 92 */         GrupoTrabajoEmpleadoBean.this.listaGrupoTrabajo.setRowCount(GrupoTrabajoEmpleadoBean.this.servicioGrupoTrabajo.contarPorCriterio(GrupoTrabajo.class, filters));
/*  61:    */         
/*  62: 94 */         return lista;
/*  63:    */       }
/*  64:    */     };
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void cargarEmpleado()
/*  68:    */   {
/*  69:105 */     Empleado auxEm = this.servicioEmpleado.VerificarGrupoTrabajoEmpleado(getEmpleado());
/*  70:107 */     if ((auxEm != null) && (auxEm.getGrupoTrabajo().getIdGrupoTrabajo() != getGrupoTrabajo().getIdGrupoTrabajo()))
/*  71:    */     {
/*  72:108 */       addInfoMessage(getLanguageController().getMensaje("msg_ya_se_encuentra_el_empleado") + "  " + auxEm.getGrupoTrabajo() != null ? auxEm
/*  73:109 */         .getGrupoTrabajo().getNombre() : "");
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:112 */       Empleado em = (Empleado)getHmEmpleados().get(getEmpleado().getEmpresa().getIdentificacion());
/*  78:114 */       if (em == null)
/*  79:    */       {
/*  80:115 */         getEmpleado().setGrupoTrabajo(getGrupoTrabajo());
/*  81:116 */         getHmEmpleados().put(getEmpleado().getEmpresa().getIdentificacion(), getEmpleado());
/*  82:117 */         getGrupoTrabajo().getListaEmpleados().add(getEmpleado());
/*  83:    */       }
/*  84:119 */       else if (em.getGrupoTrabajo() == null)
/*  85:    */       {
/*  86:120 */         em.setGrupoTrabajo(getGrupoTrabajo());
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90:122 */         addInfoMessage(getLanguageController().getMensaje("msg_ya_se_encuentra_el_empleado"));
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List<Empleado> getListaEmpleados()
/*  96:    */   {
/*  97:131 */     List<Empleado> listaEmpleadosNoEliminados = new ArrayList();
/*  98:132 */     for (Empleado e : getGrupoTrabajo().getListaEmpleados()) {
/*  99:133 */       if (e.getGrupoTrabajo() != null) {
/* 100:134 */         listaEmpleadosNoEliminados.add(e);
/* 101:    */       }
/* 102:    */     }
/* 103:137 */     return listaEmpleadosNoEliminados;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String eliminarDetalle()
/* 107:    */   {
/* 108:141 */     Empleado e = (Empleado)this.dtEmpleado.getRowData();
/* 109:142 */     e.setGrupoTrabajo(null);
/* 110:    */     
/* 111:144 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String crear()
/* 115:    */   {
/* 116:151 */     this.grupoTrabajo = new GrupoTrabajo();
/* 117:152 */     this.grupoTrabajo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 118:153 */     this.grupoTrabajo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 119:154 */     this.grupoTrabajo.setActivo(true);
/* 120:155 */     setEditado(true);
/* 121:    */     
/* 122:157 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String limpiar()
/* 126:    */   {
/* 127:166 */     getHmEmpleados().clear();
/* 128:167 */     setEmpleado(new Empleado());
/* 129:168 */     crear();
/* 130:169 */     setEditado(false);
/* 131:170 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String editar()
/* 135:    */   {
/* 136:179 */     if ((getGrupoTrabajo() != null) && (getGrupoTrabajo().getId() != 0))
/* 137:    */     {
/* 138:180 */       setGrupoTrabajo(this.servicioEmpleado.cargarDetalleGrupoTrabajo(this.grupoTrabajo.getIdGrupoTrabajo()));
/* 139:181 */       setEditado(true);
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:183 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 144:    */     }
/* 145:185 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String guardar()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:197 */       this.servicioGrupoTrabajo.guardarValidar(getGrupoTrabajo(), getGrupoTrabajo().getListaEmpleados());
/* 153:198 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 154:199 */       limpiar();
/* 155:200 */       setEditado(false);
/* 156:    */     }
/* 157:    */     catch (Exception e)
/* 158:    */     {
/* 159:202 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 160:203 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 161:    */     }
/* 162:205 */     return "";
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void seleccionar() {}
/* 166:    */   
/* 167:    */   public String eliminar()
/* 168:    */   {
/* 169:217 */     if ((getGrupoTrabajo() != null) && (getGrupoTrabajo().getId() != 0)) {
/* 170:218 */       if (this.servicioEmpleado.cargarDetalleGrupoTrabajo(this.grupoTrabajo.getIdGrupoTrabajo()).getListaEmpleados().size() > 0) {
/* 171:219 */         addInfoMessage(getLanguageController().getMensaje("msg_error_existe_empleados"));
/* 172:    */       } else {
/* 173:222 */         this.servicioGrupoTrabajo.eliminar(getGrupoTrabajo());
/* 174:    */       }
/* 175:    */     }
/* 176:226 */     return "";
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void copiar() {}
/* 180:    */   
/* 181:    */   public String cargarDatos()
/* 182:    */   {
/* 183:238 */     return "";
/* 184:    */   }
/* 185:    */   
/* 186:    */   public GrupoTrabajo getGrupoTrabajo()
/* 187:    */   {
/* 188:246 */     return this.grupoTrabajo;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setGrupoTrabajo(GrupoTrabajo grupoTrabajo)
/* 192:    */   {
/* 193:250 */     this.grupoTrabajo = grupoTrabajo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public DataTable getDtEmpleado()
/* 197:    */   {
/* 198:254 */     return this.dtEmpleado;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setDtEmpleado(DataTable dtEmpleado)
/* 202:    */   {
/* 203:258 */     this.dtEmpleado = dtEmpleado;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public DataTable getDtGrupoTrabajo()
/* 207:    */   {
/* 208:262 */     return this.dtGrupoTrabajo;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setDtGrupoTrabajo(DataTable dtGrupoTrabajo)
/* 212:    */   {
/* 213:266 */     this.dtGrupoTrabajo = dtGrupoTrabajo;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public LazyDataModel<GrupoTrabajo> getListaGrupoTrabajo()
/* 217:    */   {
/* 218:270 */     return this.listaGrupoTrabajo;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setListaGrupoTrabajo(LazyDataModel<GrupoTrabajo> listaGrupoTrabajo)
/* 222:    */   {
/* 223:274 */     this.listaGrupoTrabajo = listaGrupoTrabajo;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean isSeleccionarTodos()
/* 227:    */   {
/* 228:278 */     return this.seleccionarTodos;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setSeleccionarTodos(boolean seleccionarTodos)
/* 232:    */   {
/* 233:282 */     this.seleccionarTodos = seleccionarTodos;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Empleado getEmpleado()
/* 237:    */   {
/* 238:286 */     return this.empleado;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setEmpleado(Empleado empleado)
/* 242:    */   {
/* 243:290 */     this.empleado = empleado;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public HashMap<String, Empleado> getHmEmpleados()
/* 247:    */   {
/* 248:294 */     if (this.hmEmpleados == null) {
/* 249:295 */       this.hmEmpleados = new HashMap();
/* 250:    */     }
/* 251:297 */     return this.hmEmpleados;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setHmEmpleados(HashMap<String, Empleado> hmEmpleados)
/* 255:    */   {
/* 256:301 */     this.hmEmpleados = hmEmpleados;
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.GrupoTrabajoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */