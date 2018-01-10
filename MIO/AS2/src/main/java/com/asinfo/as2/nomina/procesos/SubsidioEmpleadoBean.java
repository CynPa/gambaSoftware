/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.SubsidioEmpleado;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.TipoSubsidio;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoSubsidio;
/*  11:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioSubsidioEmpleado;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class SubsidioEmpleadoBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioSubsidioEmpleado servicioSubsidioEmpleado;
/*  33:    */   @EJB
/*  34:    */   private ServicioTipoSubsidio servicioTipoSubsidio;
/*  35:    */   private Empleado empleado;
/*  36:    */   private SubsidioEmpleado subsidioEmpleado;
/*  37:    */   private LazyDataModel<SubsidioEmpleado> listaSubsidioEmpleado;
/*  38: 69 */   private List<TipoSubsidio> listaTipoSubsidio = new ArrayList();
/*  39:    */   private DataTable dtSubsidioEmpleado;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 81 */     this.listaSubsidioEmpleado = new LazyDataModel()
/*  45:    */     {
/*  46:    */       public List<SubsidioEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 87 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  49:    */         
/*  50: 89 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  51:    */         
/*  52: 91 */         List<SubsidioEmpleado> lista = SubsidioEmpleadoBean.this.servicioSubsidioEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  53:    */         
/*  54: 93 */         SubsidioEmpleadoBean.this.listaSubsidioEmpleado.setRowCount(SubsidioEmpleadoBean.this.servicioSubsidioEmpleado.contarPorCriterio(filters));
/*  55:    */         
/*  56: 95 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void crearSubsidioEmpleado()
/*  62:    */   {
/*  63:109 */     this.subsidioEmpleado = new SubsidioEmpleado();
/*  64:110 */     this.subsidioEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  65:111 */     this.subsidioEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/*  66:112 */     this.subsidioEmpleado.setEmpleado(new Empleado());
/*  67:113 */     this.subsidioEmpleado.setTipoSubsidio(new TipoSubsidio());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String editar()
/*  71:    */   {
/*  72:122 */     if (getSubsidioEmpleado().getIdSubsidioEmpleado() > 0) {
/*  73:123 */       setEditado(true);
/*  74:    */     } else {
/*  75:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  76:    */     }
/*  77:127 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String guardar()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:137 */       this.servicioSubsidioEmpleado.guardar(this.subsidioEmpleado);
/*  85:138 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  86:139 */       setEditado(false);
/*  87:140 */       limpiar();
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  92:143 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  93:    */     }
/*  94:145 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String eliminar()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:155 */       this.servicioSubsidioEmpleado.eliminar(this.subsidioEmpleado);
/* 102:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 103:    */     }
/* 104:    */     catch (Exception e)
/* 105:    */     {
/* 106:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 107:159 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 108:    */     }
/* 109:161 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String cargarDatos()
/* 113:    */   {
/* 114:170 */     SubsidioEmpleado subsidioEmpleado = this.servicioSubsidioEmpleado.cargarDetalle(getSubsidioEmpleado().getIdSubsidioEmpleado());
/* 115:171 */     setSubsidioEmpleado(subsidioEmpleado);
/* 116:172 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String limpiar()
/* 120:    */   {
/* 121:181 */     crearSubsidioEmpleado();
/* 122:182 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String cargarEmpleado()
/* 126:    */   {
/* 127:194 */     this.subsidioEmpleado.setEmpleado(this.empleado);
/* 128:195 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Empleado getEmpleado()
/* 132:    */   {
/* 133:208 */     if (this.empleado == null) {
/* 134:209 */       this.empleado = new Empleado();
/* 135:    */     }
/* 136:211 */     return this.empleado;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setEmpleado(Empleado empleado)
/* 140:    */   {
/* 141:221 */     this.empleado = empleado;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public SubsidioEmpleado getSubsidioEmpleado()
/* 145:    */   {
/* 146:230 */     if (this.subsidioEmpleado == null) {
/* 147:231 */       this.subsidioEmpleado = new SubsidioEmpleado();
/* 148:    */     }
/* 149:233 */     return this.subsidioEmpleado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setSubsidioEmpleado(SubsidioEmpleado subsidioEmpleado)
/* 153:    */   {
/* 154:243 */     this.subsidioEmpleado = subsidioEmpleado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public LazyDataModel<SubsidioEmpleado> getListaSubsidioEmpleado()
/* 158:    */   {
/* 159:252 */     return this.listaSubsidioEmpleado;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaSubsidioEmpleado(LazyDataModel<SubsidioEmpleado> listaSubsidioEmpleado)
/* 163:    */   {
/* 164:262 */     this.listaSubsidioEmpleado = listaSubsidioEmpleado;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public DataTable getDtSubsidioEmpleado()
/* 168:    */   {
/* 169:271 */     return this.dtSubsidioEmpleado;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setDtSubsidioEmpleado(DataTable dtSubsidioEmpleado)
/* 173:    */   {
/* 174:281 */     this.dtSubsidioEmpleado = dtSubsidioEmpleado;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public List<TipoSubsidio> getListaTipoSubsidio()
/* 178:    */   {
/* 179:290 */     this.listaTipoSubsidio = this.servicioTipoSubsidio.obtenerListaCombo("nombre", true, null);
/* 180:291 */     return this.listaTipoSubsidio;
/* 181:    */   }
/* 182:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.SubsidioEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */