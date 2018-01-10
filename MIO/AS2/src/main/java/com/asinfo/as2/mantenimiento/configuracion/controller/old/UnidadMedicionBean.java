/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.Criticidad;
/*   8:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioCriticidad;
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
/*  24:    */ public class UnidadMedicionBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 3685912063362411983L;
/*  28:    */   @EJB
/*  29:    */   private ServicioCriticidad servicioCriticidad;
/*  30:    */   private Criticidad criticidad;
/*  31:    */   private LazyDataModel<Criticidad> listaCriticidad;
/*  32:    */   private DataTable dtCriticidad;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 67 */     this.listaCriticidad = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<Criticidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 74 */         List<Criticidad> lista = new ArrayList();
/*  44: 75 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 77 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 78 */         lista = UnidadMedicionBean.this.servicioCriticidad.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 80 */         UnidadMedicionBean.this.listaCriticidad.setRowCount(UnidadMedicionBean.this.servicioCriticidad.contarPorCriterio(filters));
/*  50:    */         
/*  51: 82 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58:100 */     this.criticidad = new Criticidad();
/*  59:101 */     this.criticidad.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:102 */     this.criticidad.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:111 */     if (getCriticidad().getIdCriticidad() > 0) {
/*  66:113 */       setEditado(true);
/*  67:    */     } else {
/*  68:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:117 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:127 */       this.servicioCriticidad.guardar(this.criticidad);
/*  78:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:129 */       limpiar();
/*  80:130 */       setEditado(false);
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:133 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:135 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:145 */       this.servicioCriticidad.eliminar(this.criticidad);
/*  95:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:149 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:151 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:160 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:169 */     crearEntidad();
/* 113:170 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Criticidad getCriticidad()
/* 117:    */   {
/* 118:183 */     return this.criticidad;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setCriticidad(Criticidad criticidad)
/* 122:    */   {
/* 123:193 */     this.criticidad = criticidad;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<Criticidad> getListaCriticidad()
/* 127:    */   {
/* 128:202 */     return this.listaCriticidad;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaCriticidad(LazyDataModel<Criticidad> listaCriticidad)
/* 132:    */   {
/* 133:212 */     this.listaCriticidad = listaCriticidad;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtCriticidad()
/* 137:    */   {
/* 138:221 */     return this.dtCriticidad;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtCriticidad(DataTable dtCriticidad)
/* 142:    */   {
/* 143:231 */     this.dtCriticidad = dtCriticidad;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.UnidadMedicionBean
 * JD-Core Version:    0.7.0.1
 */