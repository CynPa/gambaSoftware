/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioEspecialidad;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpleadoGenerico;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.EmpleadoGenerico;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.Especialidad;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.Genero;
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
/*  31:    */ public class EmpleadoGenericoBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  35:    */   @EJB
/*  36:    */   private ServicioEmpleadoGenerico servicioEmpleadoGenerico;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   @EJB
/*  40:    */   private ServicioEspecialidad servicioEspecialidad;
/*  41:    */   private EmpleadoGenerico empleadoGenerico;
/*  42:    */   private LazyDataModel<EmpleadoGenerico> listaEmpleadoGenerico;
/*  43:    */   private List<Especialidad> listaEspecialidad;
/*  44:    */   private List<Genero> listaGenero;
/*  45:    */   private DataTable dtEmpleadoGenerico;
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 88 */     this.listaEmpleadoGenerico = new LazyDataModel()
/*  51:    */     {
/*  52:    */       private static final long serialVersionUID = 1L;
/*  53:    */       
/*  54:    */       public List<EmpleadoGenerico> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  55:    */       {
/*  56: 95 */         List<EmpleadoGenerico> lista = new ArrayList();
/*  57: 96 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  58:    */         
/*  59: 98 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  60: 99 */         lista = EmpleadoGenericoBean.this.servicioEmpleadoGenerico.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  61:    */         
/*  62:101 */         EmpleadoGenericoBean.this.listaEmpleadoGenerico.setRowCount(EmpleadoGenericoBean.this.servicioEmpleadoGenerico.contarPorCriterio(filters));
/*  63:    */         
/*  64:103 */         return lista;
/*  65:    */       }
/*  66:    */     };
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void crearEntidad()
/*  70:    */   {
/*  71:121 */     this.empleadoGenerico = new EmpleadoGenerico();
/*  72:122 */     this.empleadoGenerico.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  73:123 */     this.empleadoGenerico.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  74:124 */     this.empleadoGenerico.setActivo(true);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String editar()
/*  78:    */   {
/*  79:133 */     if (getEmpleadoGenerico().getIdEmpleadoGenerico() > 0)
/*  80:    */     {
/*  81:134 */       this.empleadoGenerico = this.servicioEmpleadoGenerico.cargarDetalle(this.empleadoGenerico.getIdEmpleadoGenerico());
/*  82:135 */       setEditado(true);
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  87:    */     }
/*  88:139 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String guardar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:149 */       this.servicioEmpleadoGenerico.guardar(this.empleadoGenerico);
/*  96:150 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  97:151 */       limpiar();
/*  98:152 */       setEditado(false);
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:154 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 103:155 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 104:    */     }
/* 105:157 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String eliminar()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:167 */       this.servicioEmpleadoGenerico.eliminar(this.empleadoGenerico);
/* 113:168 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:170 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 118:171 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 119:    */     }
/* 120:173 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String cargarDatos()
/* 124:    */   {
/* 125:182 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String limpiar()
/* 129:    */   {
/* 130:191 */     crearEntidad();
/* 131:192 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public List<Empresa> autocompletarEmpleados(String consulta)
/* 135:    */   {
/* 136:196 */     return this.servicioEmpresa.autocompletarEmpleados(consulta);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void actualizarEmpleado(SelectEvent event)
/* 140:    */   {
/* 141:207 */     Empresa empresa = (Empresa)event.getObject();
/* 142:208 */     getEmpleadoGenerico().setEmpresa(empresa);
/* 143:209 */     getEmpleadoGenerico().setNombre(empresa.getNombreFiscal().concat(" - ").concat(empresa.getNombreComercial()));
/* 144:210 */     getEmpleadoGenerico().setIdentificacion(empresa.getIdentificacion());
/* 145:211 */     getEmpleadoGenerico().setGenero(empresa.getEmpleado().getGenero());
/* 146:    */   }
/* 147:    */   
/* 148:    */   public EmpleadoGenerico getEmpleadoGenerico()
/* 149:    */   {
/* 150:224 */     return this.empleadoGenerico;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setEmpleadoGenerico(EmpleadoGenerico empleadoGenerico)
/* 154:    */   {
/* 155:234 */     this.empleadoGenerico = empleadoGenerico;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public LazyDataModel<EmpleadoGenerico> getListaEmpleadoGenerico()
/* 159:    */   {
/* 160:243 */     return this.listaEmpleadoGenerico;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setListaEmpleadoGenerico(LazyDataModel<EmpleadoGenerico> listaEmpleadoGenerico)
/* 164:    */   {
/* 165:253 */     this.listaEmpleadoGenerico = listaEmpleadoGenerico;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public DataTable getDtEmpleadoGenerico()
/* 169:    */   {
/* 170:262 */     return this.dtEmpleadoGenerico;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setDtEmpleadoGenerico(DataTable dtEmpleadoGenerico)
/* 174:    */   {
/* 175:272 */     this.dtEmpleadoGenerico = dtEmpleadoGenerico;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<Especialidad> getListaEspecialidad()
/* 179:    */   {
/* 180:281 */     if (this.listaEspecialidad == null) {
/* 181:282 */       this.listaEspecialidad = this.servicioEspecialidad.obtenerListaCombo("nombre", true, null);
/* 182:    */     }
/* 183:284 */     return this.listaEspecialidad;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setListaEspecialidad(List<Especialidad> listaEspecialidad)
/* 187:    */   {
/* 188:294 */     this.listaEspecialidad = listaEspecialidad;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<Genero> getListaGenero()
/* 192:    */   {
/* 193:303 */     if (this.listaGenero == null)
/* 194:    */     {
/* 195:304 */       this.listaGenero = new ArrayList();
/* 196:305 */       for (Genero genero : Genero.values()) {
/* 197:306 */         this.listaGenero.add(genero);
/* 198:    */       }
/* 199:    */     }
/* 200:309 */     return this.listaGenero;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setListaGenero(List<Genero> listaGenero)
/* 204:    */   {
/* 205:319 */     this.listaGenero = listaGenero;
/* 206:    */   }
/* 207:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.EmpleadoGenericoBean
 * JD-Core Version:    0.7.0.1
 */