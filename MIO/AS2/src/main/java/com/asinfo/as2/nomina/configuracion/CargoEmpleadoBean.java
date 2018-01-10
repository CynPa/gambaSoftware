/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CargoEmpleado;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargoEmpleado;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class CargoEmpleadoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   @EJB
/*  28:    */   private ServicioCargoEmpleado servicioCargoEmpleado;
/*  29:    */   private CargoEmpleado cargoEmpleado;
/*  30:    */   private LazyDataModel<CargoEmpleado> listaCargoEmpleado;
/*  31:    */   private DataTable dtCargoEmpleado;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 67 */     this.listaCargoEmpleado = new LazyDataModel()
/*  37:    */     {
/*  38:    */       private static final long serialVersionUID = 1L;
/*  39:    */       
/*  40:    */       public List<CargoEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  41:    */       {
/*  42: 74 */         List<CargoEmpleado> lista = new ArrayList();
/*  43: 75 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  44:    */         
/*  45: 77 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  46: 78 */         lista = CargoEmpleadoBean.this.servicioCargoEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48: 80 */         CargoEmpleadoBean.this.listaCargoEmpleado.setRowCount(CargoEmpleadoBean.this.servicioCargoEmpleado.contarPorCriterio(filters));
/*  49:    */         
/*  50: 82 */         return lista;
/*  51:    */       }
/*  52:    */     };
/*  53:    */   }
/*  54:    */   
/*  55:    */   private void crearCargoEmpleado()
/*  56:    */   {
/*  57: 96 */     this.cargoEmpleado = new CargoEmpleado();
/*  58: 97 */     this.cargoEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  59: 98 */     this.cargoEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String editar()
/*  63:    */   {
/*  64:108 */     if ((getCargoEmpleado() != null) && (getCargoEmpleado().getIdCargoEmpleado() != 0)) {
/*  65:109 */       setEditado(true);
/*  66:    */     } else {
/*  67:111 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  68:    */     }
/*  69:113 */     return "";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String guardar()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:123 */       this.servicioCargoEmpleado.guardar(this.cargoEmpleado);
/*  77:124 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  78:125 */       setEditado(false);
/*  79:126 */       limpiar();
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  84:129 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  85:    */     }
/*  86:131 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String eliminar()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:141 */       this.servicioCargoEmpleado.eliminar(this.cargoEmpleado);
/*  94:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  99:145 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 100:    */     }
/* 101:147 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String cargarDatos()
/* 105:    */   {
/* 106:156 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String limpiar()
/* 110:    */   {
/* 111:165 */     crearCargoEmpleado();
/* 112:166 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public CargoEmpleado getCargoEmpleado()
/* 116:    */   {
/* 117:182 */     if (this.cargoEmpleado == null) {
/* 118:183 */       this.cargoEmpleado = new CargoEmpleado();
/* 119:    */     }
/* 120:185 */     return this.cargoEmpleado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setCargoEmpleado(CargoEmpleado cargoEmpleado)
/* 124:    */   {
/* 125:195 */     this.cargoEmpleado = cargoEmpleado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public LazyDataModel<CargoEmpleado> getListaCargoEmpleado()
/* 129:    */   {
/* 130:204 */     return this.listaCargoEmpleado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setListaCargoEmpleado(LazyDataModel<CargoEmpleado> listaCargoEmpleado)
/* 134:    */   {
/* 135:214 */     this.listaCargoEmpleado = listaCargoEmpleado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public DataTable getDtCargoEmpleado()
/* 139:    */   {
/* 140:223 */     return this.dtCargoEmpleado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setDtCargoEmpleado(DataTable dtCargoEmpleado)
/* 144:    */   {
/* 145:233 */     this.dtCargoEmpleado = dtCargoEmpleado;
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.CargoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */