/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.TipoServicioMantenimiento;
/*   8:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoServicioMantenimiento;
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
/*  24:    */ public class TipoServicioMantenimientoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoServicioMantenimiento servicioTipoServicioMantenimiento;
/*  30:    */   private TipoServicioMantenimiento tipoServicioMantenimiento;
/*  31:    */   private LazyDataModel<TipoServicioMantenimiento> listaTipoServicioMantenimiento;
/*  32:    */   private DataTable dtTipoServicioMantenimiento;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 76 */     this.listaTipoServicioMantenimiento = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<TipoServicioMantenimiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 84 */         List<TipoServicioMantenimiento> lista = new ArrayList();
/*  44: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 87 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 88 */         lista = TipoServicioMantenimientoBean.this.servicioTipoServicioMantenimiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 90 */         TipoServicioMantenimientoBean.this.listaTipoServicioMantenimiento.setRowCount(TipoServicioMantenimientoBean.this.servicioTipoServicioMantenimiento.contarPorCriterio(filters));
/*  50:    */         
/*  51: 92 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58:110 */     this.tipoServicioMantenimiento = new TipoServicioMantenimiento();
/*  59:111 */     this.tipoServicioMantenimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:112 */     this.tipoServicioMantenimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:121 */     if (getTipoServicioMantenimiento().getIdTipoServicioMantenimiento() > 0) {
/*  66:122 */       setEditado(true);
/*  67:    */     } else {
/*  68:124 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:126 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:136 */       this.servicioTipoServicioMantenimiento.guardar(this.tipoServicioMantenimiento);
/*  78:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:138 */       limpiar();
/*  80:139 */       setEditado(false);
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:142 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:144 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:154 */       this.servicioTipoServicioMantenimiento.eliminar(this.tipoServicioMantenimiento);
/*  95:155 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:158 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:160 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:169 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:178 */     crearEntidad();
/* 113:179 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public TipoServicioMantenimiento getTipoServicioMantenimiento()
/* 117:    */   {
/* 118:192 */     return this.tipoServicioMantenimiento;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTipoServicioMantenimiento(TipoServicioMantenimiento tipoServicioMantenimiento)
/* 122:    */   {
/* 123:202 */     this.tipoServicioMantenimiento = tipoServicioMantenimiento;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<TipoServicioMantenimiento> getListaTipoServicioMantenimiento()
/* 127:    */   {
/* 128:211 */     return this.listaTipoServicioMantenimiento;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaTipoServicioMantenimiento(LazyDataModel<TipoServicioMantenimiento> listaTipoServicioMantenimiento)
/* 132:    */   {
/* 133:221 */     this.listaTipoServicioMantenimiento = listaTipoServicioMantenimiento;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtTipoServicioMantenimiento()
/* 137:    */   {
/* 138:230 */     return this.dtTipoServicioMantenimiento;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtTipoServicioMantenimiento(DataTable dtTipoServicioMantenimiento)
/* 142:    */   {
/* 143:240 */     this.dtTipoServicioMantenimiento = dtTipoServicioMantenimiento;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.TipoServicioMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */