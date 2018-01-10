/*   1:    */ package com.asinfo.as2.nomina.asistencia.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Departamento;
/*   7:    */ import com.asinfo.as2.entities.Empleado;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*  11:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  12:    */ import com.asinfo.as2.nomina.configuracion.EmpleadoBean;
/*  13:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  14:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  15:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class AsignarHorarioEmpleadoBean
/*  32:    */   extends EmpleadoBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 2800723784125017154L;
/*  35:    */   @EJB
/*  36:    */   private ServicioUsuario servicioUsuario;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpleado servicioEmpleado;
/*  39:    */   private List<Empresa> listaEmpresasSeleccionadas;
/*  40:    */   private List<Empresa> listaEmpresasFiltradas;
/*  41:    */   private Departamento departamento;
/*  42:    */   private HorarioEmpleado horarioEmpleado;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 72 */     if (((this.departamento == null) || (this.departamento.getId() == 0)) && (getListaDepartamento().size() > 0)) {
/*  48: 73 */       this.departamento = ((Departamento)getListaDepartamento().get(0));
/*  49:    */     }
/*  50: 76 */     this.listaEmpresa = new LazyDataModel()
/*  51:    */     {
/*  52:    */       private static final long serialVersionUID = 1L;
/*  53:    */       
/*  54:    */       public List<Empresa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  55:    */       {
/*  56: 83 */         List<Empresa> lista = new ArrayList();
/*  57:    */         
/*  58: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  59: 86 */         filters.put("indicadorEmpleado", "true");
/*  60: 87 */         filters.put("empleado.cargoEmpleado.indicadorRegistraAsistencia", "true");
/*  61: 88 */         filters.put("empleado.departamento.idDepartamento", "" + AsignarHorarioEmpleadoBean.this.getDepartamento().getId());
/*  62: 89 */         filters.put("empleado.activo", "true");
/*  63: 90 */         lista = AsignarHorarioEmpleadoBean.this.servicioEmpresa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  64: 91 */         AsignarHorarioEmpleadoBean.this.listaEmpresa.setRowCount(AsignarHorarioEmpleadoBean.this.servicioEmpresa.contarPorCriterio(filters));
/*  65:    */         
/*  66: 93 */         return lista;
/*  67:    */       }
/*  68:    */     };
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<Empresa> getListaEmpresasSeleccionadas()
/*  72:    */   {
/*  73: 99 */     return this.listaEmpresasSeleccionadas;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setListaEmpresasSeleccionadas(List<Empresa> listaEmpresasSeleccionadas)
/*  77:    */   {
/*  78:103 */     this.listaEmpresasSeleccionadas = listaEmpresasSeleccionadas;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List<Departamento> getListaDepartamento()
/*  82:    */   {
/*  83:107 */     EntidadUsuario usuario = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  84:108 */     Map<String, String> filtros = new HashMap();
/*  85:109 */     Map<String, String> filtros2 = new HashMap();
/*  86:    */     
/*  87:111 */     List<Departamento> lista = new ArrayList();
/*  88:    */     
/*  89:113 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  90:114 */     filtros2.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  91:116 */     if ((usuario.getEmpleado() != null) && (!usuario.isIndicadorAdministrador()))
/*  92:    */     {
/*  93:118 */       filtros.put("supervisor.idEmpleado", "" + usuario.getEmpleado().getId());
/*  94:119 */       filtros2.put("supervisor2.idEmpleado", "" + usuario.getEmpleado().getId());
/*  95:    */       
/*  96:121 */       lista.addAll(this.servicioDepartamento.obtenerListaCombo("nombre", true, filtros2));
/*  97:    */     }
/*  98:124 */     else if (!usuario.isIndicadorAdministrador())
/*  99:    */     {
/* 100:125 */       return lista;
/* 101:    */     }
/* 102:128 */     lista.addAll(this.servicioDepartamento.obtenerListaCombo("nombre", true, filtros));
/* 103:    */     
/* 104:130 */     return lista;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Departamento getDepartamento()
/* 108:    */   {
/* 109:134 */     if (this.departamento == null)
/* 110:    */     {
/* 111:135 */       this.departamento = new Departamento();
/* 112:136 */       this.departamento.setId(0);
/* 113:    */     }
/* 114:138 */     return this.departamento;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDepartamento(Departamento departamento)
/* 118:    */   {
/* 119:142 */     this.departamento = departamento;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public HorarioEmpleado getHorarioEmpleado()
/* 123:    */   {
/* 124:146 */     return this.horarioEmpleado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setHorarioEmpleado(HorarioEmpleado horarioEmpleado)
/* 128:    */   {
/* 129:150 */     this.horarioEmpleado = horarioEmpleado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<Empresa> getListaEmpresasFiltradas()
/* 133:    */   {
/* 134:154 */     return this.listaEmpresasFiltradas;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setListaEmpresasFiltradas(List<Empresa> listaEmpresasFiltradas)
/* 138:    */   {
/* 139:158 */     this.listaEmpresasFiltradas = listaEmpresasFiltradas;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void asignar()
/* 143:    */   {
/* 144:162 */     boolean errores = false;
/* 145:163 */     for (Empresa empresa : this.listaEmpresasSeleccionadas) {
/* 146:    */       try
/* 147:    */       {
/* 148:165 */         empresa.setListaEmpresaAtributo(new ArrayList());
/* 149:166 */         empresa.getEmpleado().setHorarioEmpleado(this.horarioEmpleado);
/* 150:167 */         this.servicioEmpleado.guardarSoloEntidad(empresa.getEmpleado());
/* 151:    */       }
/* 152:    */       catch (Exception e)
/* 153:    */       {
/* 154:170 */         errores = true;
/* 155:171 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 156:    */       }
/* 157:    */     }
/* 158:174 */     if (!errores) {
/* 159:175 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 160:    */     }
/* 161:177 */     this.listaEmpresasFiltradas = null;
/* 162:178 */     this.dtEmpresa.reset();
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.procesos.AsignarHorarioEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */