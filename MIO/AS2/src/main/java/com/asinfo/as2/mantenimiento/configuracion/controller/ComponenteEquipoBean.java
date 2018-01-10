/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ComponenteEquipoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  32:    */   private ComponenteEquipo componenteEquipo;
/*  33:    */   private LazyDataModel<ComponenteEquipo> listaComponenteEquipo;
/*  34:    */   private DataTable dtComponenteEquipo;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 55 */     this.listaComponenteEquipo = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<ComponenteEquipo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 62 */         List<ComponenteEquipo> lista = new ArrayList();
/*  46: 63 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 65 */         lista = ComponenteEquipoBean.this.servicioComponenteEquipo.obtenerListaPorPagina(ComponenteEquipo.class, startIndex, pageSize, sortField, ordenar, filters);
/*  49: 66 */         ComponenteEquipoBean.this.listaComponenteEquipo.setRowCount(ComponenteEquipoBean.this.servicioComponenteEquipo.contarPorCriterio(ComponenteEquipo.class, filters));
/*  50:    */         
/*  51: 68 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String editar()
/*  57:    */   {
/*  58: 81 */     if (getComponenteEquipo().getId() > 0) {
/*  59: 82 */       setEditado(true);
/*  60:    */     } else {
/*  61: 84 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  62:    */     }
/*  63: 86 */     return "";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String guardar()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70: 97 */       this.servicioComponenteEquipo.guardarValidar(this.componenteEquipo);
/*  71: 98 */       limpiar();
/*  72: 99 */       setEditado(false);
/*  73:100 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  74:    */     }
/*  75:    */     catch (AS2Exception e)
/*  76:    */     {
/*  77:102 */       JsfUtil.addErrorMessage(e, "");
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  82:105 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  83:    */     }
/*  84:107 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String limpiar()
/*  88:    */   {
/*  89:117 */     crearComponenteEquipo();
/*  90:118 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String eliminar()
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:129 */       this.servicioComponenteEquipo.eliminar(this.componenteEquipo);
/*  98:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 103:133 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 104:    */     }
/* 105:135 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String cargarDatos()
/* 109:    */   {
/* 110:145 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void crearComponenteEquipo()
/* 114:    */   {
/* 115:152 */     this.componenteEquipo = new ComponenteEquipo();
/* 116:153 */     this.componenteEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 117:154 */     this.componenteEquipo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 118:155 */     this.componenteEquipo.setActivo(true);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public ComponenteEquipo getComponenteEquipo()
/* 122:    */   {
/* 123:164 */     if (this.componenteEquipo == null) {
/* 124:165 */       crearComponenteEquipo();
/* 125:    */     }
/* 126:167 */     return this.componenteEquipo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setComponenteEquipo(ComponenteEquipo ComponenteEquipo)
/* 130:    */   {
/* 131:177 */     this.componenteEquipo = ComponenteEquipo;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public LazyDataModel<ComponenteEquipo> getListaComponenteEquipo()
/* 135:    */   {
/* 136:186 */     return this.listaComponenteEquipo;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setListaComponenteEquipo(LazyDataModel<ComponenteEquipo> listaComponenteEquipo)
/* 140:    */   {
/* 141:196 */     this.listaComponenteEquipo = listaComponenteEquipo;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public DataTable getDtComponenteEquipo()
/* 145:    */   {
/* 146:205 */     return this.dtComponenteEquipo;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setDtComponenteEquipo(DataTable dtComponenteEquipo)
/* 150:    */   {
/* 151:215 */     this.dtComponenteEquipo = dtComponenteEquipo;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.ComponenteEquipoBean
 * JD-Core Version:    0.7.0.1
 */