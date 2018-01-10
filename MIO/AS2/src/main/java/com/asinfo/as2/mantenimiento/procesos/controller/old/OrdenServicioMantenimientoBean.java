/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.old.OrdenServicioMantenimiento;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.old.Procedimiento;
/*  10:    */ import com.asinfo.as2.enumeraciones.EstadoOrdenServicioMantenimiento;
/*  11:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioOrdenServicioMantenimiento;
/*  12:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioProcedimiento;
/*  13:    */ import com.asinfo.as2.mantenimiento.procesos.old.ServicioArticuloServicio;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.util.ArrayList;
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
/*  29:    */ public class OrdenServicioMantenimientoBean
/*  30:    */   extends PageControllerAS2
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -5395397108655501620L;
/*  33:    */   @EJB
/*  34:    */   private ServicioOrdenServicioMantenimiento servicioOrdenServicioMantenimiento;
/*  35:    */   @EJB
/*  36:    */   private ServicioProcedimiento servicioProcedimiento;
/*  37:    */   @EJB
/*  38:    */   private ServicioArticuloServicio servicioArticuloServicio;
/*  39:    */   private OrdenServicioMantenimiento ordenServicioMantenimiento;
/*  40:    */   private LazyDataModel<OrdenServicioMantenimiento> listaOrdenServicioMantenimiento;
/*  41:    */   private List<Procedimiento> listaProcedimiento;
/*  42:    */   private DataTable dtOrdenServicioMantenimiento;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 85 */     this.listaOrdenServicioMantenimiento = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = 1L;
/*  50:    */       
/*  51:    */       public List<OrdenServicioMantenimiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 93 */         List<OrdenServicioMantenimiento> lista = new ArrayList();
/*  54: 94 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  55:    */         
/*  56: 96 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  57: 97 */         lista = OrdenServicioMantenimientoBean.this.servicioOrdenServicioMantenimiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  58:    */         
/*  59: 99 */         OrdenServicioMantenimientoBean.this.listaOrdenServicioMantenimiento.setRowCount(OrdenServicioMantenimientoBean.this.servicioOrdenServicioMantenimiento.contarPorCriterio(filters));
/*  60:    */         
/*  61:101 */         return lista;
/*  62:    */       }
/*  63:    */     };
/*  64:    */   }
/*  65:    */   
/*  66:    */   private void crearEntidad()
/*  67:    */   {
/*  68:119 */     this.ordenServicioMantenimiento = new OrdenServicioMantenimiento();
/*  69:120 */     this.ordenServicioMantenimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  70:121 */     this.ordenServicioMantenimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  71:122 */     this.ordenServicioMantenimiento.setEstadoOrdenServicioMantenimiento(EstadoOrdenServicioMantenimiento.ELABORADA);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String editar()
/*  75:    */   {
/*  76:131 */     if (getOrdenServicioMantenimiento().getIdOrdenServicioMantenimiento() > 0)
/*  77:    */     {
/*  78:132 */       this.ordenServicioMantenimiento = this.servicioOrdenServicioMantenimiento.cargarDetalle(this.ordenServicioMantenimiento
/*  79:133 */         .getIdOrdenServicioMantenimiento());
/*  80:134 */       setEditado(true);
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:136 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  85:    */     }
/*  86:138 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String guardar()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:148 */       this.servicioOrdenServicioMantenimiento.guardar(this.ordenServicioMantenimiento);
/*  94:149 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  95:150 */       limpiar();
/*  96:151 */       setEditado(false);
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 101:154 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 102:    */     }
/* 103:156 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String eliminar()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:166 */       this.servicioOrdenServicioMantenimiento.eliminar(this.ordenServicioMantenimiento);
/* 111:167 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 112:    */     }
/* 113:    */     catch (Exception e)
/* 114:    */     {
/* 115:169 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 116:170 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 117:    */     }
/* 118:172 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<ArticuloServicio> autocompletarArticuloServicio(String consulta)
/* 122:    */   {
/* 123:176 */     return this.servicioArticuloServicio.autocompletarArticuloServicio(consulta);
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
/* 137:    */   public OrdenServicioMantenimiento getOrdenServicioMantenimiento()
/* 138:    */   {
/* 139:208 */     return this.ordenServicioMantenimiento;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setOrdenServicioMantenimiento(OrdenServicioMantenimiento ordenServicioMantenimiento)
/* 143:    */   {
/* 144:218 */     this.ordenServicioMantenimiento = ordenServicioMantenimiento;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public LazyDataModel<OrdenServicioMantenimiento> getListaOrdenServicioMantenimiento()
/* 148:    */   {
/* 149:227 */     return this.listaOrdenServicioMantenimiento;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setListaOrdenServicioMantenimiento(LazyDataModel<OrdenServicioMantenimiento> listaOrdenServicioMantenimiento)
/* 153:    */   {
/* 154:237 */     this.listaOrdenServicioMantenimiento = listaOrdenServicioMantenimiento;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public DataTable getDtOrdenServicioMantenimiento()
/* 158:    */   {
/* 159:246 */     return this.dtOrdenServicioMantenimiento;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setDtOrdenServicioMantenimiento(DataTable dtOrdenServicioMantenimiento)
/* 163:    */   {
/* 164:256 */     this.dtOrdenServicioMantenimiento = dtOrdenServicioMantenimiento;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Procedimiento> getListaProcedimiento()
/* 168:    */   {
/* 169:265 */     if (this.listaProcedimiento == null) {
/* 170:266 */       this.listaProcedimiento = this.servicioProcedimiento.obtenerListaCombo("nombre", true, null);
/* 171:    */     }
/* 172:268 */     return this.listaProcedimiento;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setListaProcedimiento(List<Procedimiento> listaProcedimiento)
/* 176:    */   {
/* 177:278 */     this.listaProcedimiento = listaProcedimiento;
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.controller.old.OrdenServicioMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */