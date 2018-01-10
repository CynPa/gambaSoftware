/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioEspecialidad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   9:    */ import com.asinfo.as2.entities.Empleado;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.Especialidad;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  16:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.component.datatable.DataTable;
/*  27:    */ import org.primefaces.model.LazyDataModel;
/*  28:    */ import org.primefaces.model.SortOrder;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class PersonaResponsableBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 7542944693847338952L;
/*  36:    */   @EJB
/*  37:    */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  38:    */   @EJB
/*  39:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  40:    */   @EJB
/*  41:    */   private ServicioEspecialidad servicioEspecialidad;
/*  42:    */   @EJB
/*  43:    */   private ServicioEmpresa servicioEmpresa;
/*  44:    */   @EJB
/*  45:    */   private ServicioEmpleado servicioEmpleado;
/*  46:    */   private PersonaResponsable entidad;
/*  47:    */   private LazyDataModel<PersonaResponsable> ListaPersonaResponsable;
/*  48:    */   private DataTable dtListadoPersonaResponsable;
/*  49:    */   private List<TipoIdentificacion> listaTipoIdentificacionCombo;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 80 */     this.ListaPersonaResponsable = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  57:    */       
/*  58:    */       public List<PersonaResponsable> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 86 */         List<PersonaResponsable> lista = new ArrayList();
/*  61:    */         
/*  62:    */ 
/*  63:    */ 
/*  64: 90 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  65:    */         try
/*  66:    */         {
/*  67: 92 */           lista = PersonaResponsableBean.this.servicioPersonaResponsable.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  68:    */         }
/*  69:    */         catch (Exception e)
/*  70:    */         {
/*  71: 95 */           e.printStackTrace();
/*  72:    */         }
/*  73: 97 */         PersonaResponsableBean.this.ListaPersonaResponsable.setRowCount(PersonaResponsableBean.this.servicioPersonaResponsable.contarPorCriterio(filters));
/*  74: 98 */         return lista;
/*  75:    */       }
/*  76:    */     };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String guardar()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:106 */       this.servicioPersonaResponsable.guardar(this.entidad);
/*  84:    */       
/*  85:108 */       limpiar();
/*  86:    */       
/*  87:110 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91:112 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  92:113 */       LOG.error("ERROR AL GUARDAR DATOS RESPONSABLESALIDAMERADERIA ", e);
/*  93:    */     }
/*  94:115 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String editar()
/*  98:    */   {
/*  99:122 */     if ((this.entidad != null) && (this.entidad.getId() != 0))
/* 100:    */     {
/* 101:123 */       this.entidad = this.servicioPersonaResponsable.cargarDetalle(this.entidad.getIdPersonaResponsable());
/* 102:    */       
/* 103:125 */       setEditado(true);
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 108:    */     }
/* 109:130 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String eliminar()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:136 */       this.servicioPersonaResponsable.eliminar(this.entidad);
/* 117:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 118:    */     }
/* 119:    */     catch (Exception e)
/* 120:    */     {
/* 121:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 122:140 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 123:    */     }
/* 124:143 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String limpiar()
/* 128:    */   {
/* 129:148 */     setEditado(false);
/* 130:149 */     this.entidad = new PersonaResponsable();
/* 131:150 */     this.entidad.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 132:151 */     this.entidad.setIdSucursal(AppUtil.getSucursal().getId());
/* 133:152 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String cargarDatos()
/* 137:    */   {
/* 138:157 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<Especialidad> autocompletarEspecialidad(String consulta)
/* 142:    */   {
/* 143:161 */     return this.servicioEspecialidad.autocompletarEspecialidad(consulta, AppUtil.getOrganizacion().getIdOrganizacion());
/* 144:    */   }
/* 145:    */   
/* 146:    */   public ServicioPersonaResponsable getServicioPersonaResponsable()
/* 147:    */   {
/* 148:168 */     return this.servicioPersonaResponsable;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setServicioPersonaResponsable(ServicioPersonaResponsable servicioPersonaResponsable)
/* 152:    */   {
/* 153:176 */     this.servicioPersonaResponsable = servicioPersonaResponsable;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public PersonaResponsable getEntidad()
/* 157:    */   {
/* 158:183 */     return this.entidad;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setEntidad(PersonaResponsable entidad)
/* 162:    */   {
/* 163:191 */     this.entidad = entidad;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public LazyDataModel<PersonaResponsable> getListaPersonaResponsable()
/* 167:    */   {
/* 168:198 */     return this.ListaPersonaResponsable;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setListaPersonaResponsable(LazyDataModel<PersonaResponsable> listaPersonaResponsable)
/* 172:    */   {
/* 173:206 */     this.ListaPersonaResponsable = listaPersonaResponsable;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public DataTable getDtListadoPersonaResponsable()
/* 177:    */   {
/* 178:213 */     return this.dtListadoPersonaResponsable;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setDtListadoPersonaResponsable(DataTable dtListadoPersonaResponsable)
/* 182:    */   {
/* 183:221 */     this.dtListadoPersonaResponsable = dtListadoPersonaResponsable;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<TipoIdentificacion> getListaTipoIdentificacionCombo()
/* 187:    */   {
/* 188:228 */     if (this.listaTipoIdentificacionCombo == null) {
/* 189:229 */       this.listaTipoIdentificacionCombo = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 190:    */     }
/* 191:231 */     return this.listaTipoIdentificacionCombo;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaTipoIdentificacionCombo(List<TipoIdentificacion> listaTipoIdentificacionCombo)
/* 195:    */   {
/* 196:239 */     this.listaTipoIdentificacionCombo = listaTipoIdentificacionCombo;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 200:    */   {
/* 201:243 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public List<Empleado> autocompletarEmpleados(String consulta)
/* 205:    */   {
/* 206:247 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 207:248 */     filters.put("activo", "true");
/* 208:249 */     filters.put("OR~nombres", "%" + consulta + "%");
/* 209:250 */     filters.put("OR~apellidos", "%" + consulta + "%");
/* 210:251 */     filters.put("OR~empresa.identificacion", "%" + consulta + "%");
/* 211:252 */     return this.servicioEmpleado.obtenerListaPorPagina(0, 50, "nombres", true, filters);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void actualizarEmpleado()
/* 215:    */   {
/* 216:256 */     if (this.entidad.getEmpleado() != null)
/* 217:    */     {
/* 218:257 */       this.entidad.setApellidos(this.entidad.getEmpleado().getApellidos());
/* 219:258 */       this.entidad.setNombres(this.entidad.getEmpleado().getNombres());
/* 220:259 */       this.entidad.setTipoIdentificacion(this.entidad.getEmpleado().getEmpresa().getTipoIdentificacion());
/* 221:260 */       this.entidad.setIdentificacion(this.entidad.getEmpleado().getEmpresa().getIdentificacion());
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void actualizarIndicadorExterno()
/* 226:    */   {
/* 227:265 */     this.entidad.setEmpleado(null);
/* 228:266 */     this.entidad.setProveedor(null);
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.PersonaResponsableBean
 * JD-Core Version:    0.7.0.1
 */