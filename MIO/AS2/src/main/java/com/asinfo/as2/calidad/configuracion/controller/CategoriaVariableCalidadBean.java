/*   1:    */ package com.asinfo.as2.calidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.calidad.CategoriaVariableCalidad;
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
/*  26:    */ public class CategoriaVariableCalidadBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<CategoriaVariableCalidad> servicioCategoriaVariableCalidad;
/*  32:    */   private CategoriaVariableCalidad categoriaVariableCalidad;
/*  33:    */   private LazyDataModel<CategoriaVariableCalidad> listaCategoriaVariableCalidad;
/*  34:    */   private DataTable dtCategoriaVariableCalidad;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 51 */     this.listaCategoriaVariableCalidad = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<CategoriaVariableCalidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 59 */         List<CategoriaVariableCalidad> lista = new ArrayList();
/*  46: 60 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 62 */         lista = CategoriaVariableCalidadBean.this.servicioCategoriaVariableCalidad.obtenerListaPorPagina(CategoriaVariableCalidad.class, startIndex, pageSize, sortField, ordenar, filters);
/*  49:    */         
/*  50: 64 */         CategoriaVariableCalidadBean.this.listaCategoriaVariableCalidad
/*  51: 65 */           .setRowCount(CategoriaVariableCalidadBean.this.servicioCategoriaVariableCalidad.contarPorCriterio(CategoriaVariableCalidad.class, filters));
/*  52:    */         
/*  53: 67 */         return lista;
/*  54:    */       }
/*  55:    */     };
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String editar()
/*  59:    */   {
/*  60: 80 */     if (getCategoriaVariableCalidad().getId() > 0) {
/*  61: 81 */       setEditado(true);
/*  62:    */     } else {
/*  63: 83 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  64:    */     }
/*  65: 85 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String guardar()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 96 */       this.servicioCategoriaVariableCalidad.guardarValidar(this.categoriaVariableCalidad);
/*  73: 97 */       limpiar();
/*  74: 98 */       setEditado(false);
/*  75: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  76:    */     }
/*  77:    */     catch (AS2Exception e)
/*  78:    */     {
/*  79:101 */       JsfUtil.addErrorMessage(e, "");
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:103 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  84:104 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  85:    */     }
/*  86:106 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String limpiar()
/*  90:    */   {
/*  91:116 */     crearCategoriaVariableCalidad();
/*  92:117 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String eliminar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:128 */       this.servicioCategoriaVariableCalidad.eliminar(this.categoriaVariableCalidad);
/* 100:129 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 105:132 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 106:    */     }
/* 107:134 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String cargarDatos()
/* 111:    */   {
/* 112:144 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void crearCategoriaVariableCalidad()
/* 116:    */   {
/* 117:151 */     this.categoriaVariableCalidad = new CategoriaVariableCalidad();
/* 118:152 */     this.categoriaVariableCalidad.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 119:153 */     this.categoriaVariableCalidad.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 120:154 */     this.categoriaVariableCalidad.setActivo(true);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public CategoriaVariableCalidad getCategoriaVariableCalidad()
/* 124:    */   {
/* 125:163 */     if (this.categoriaVariableCalidad == null) {
/* 126:164 */       crearCategoriaVariableCalidad();
/* 127:    */     }
/* 128:166 */     return this.categoriaVariableCalidad;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setCategoriaVariableCalidad(CategoriaVariableCalidad CategoriaVariableCalidad)
/* 132:    */   {
/* 133:176 */     this.categoriaVariableCalidad = CategoriaVariableCalidad;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public LazyDataModel<CategoriaVariableCalidad> getListaCategoriaVariableCalidad()
/* 137:    */   {
/* 138:185 */     return this.listaCategoriaVariableCalidad;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setListaCategoriaVariableCalidad(LazyDataModel<CategoriaVariableCalidad> listaCategoriaVariableCalidad)
/* 142:    */   {
/* 143:195 */     this.listaCategoriaVariableCalidad = listaCategoriaVariableCalidad;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public DataTable getDtCategoriaVariableCalidad()
/* 147:    */   {
/* 148:204 */     return this.dtCategoriaVariableCalidad;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setDtCategoriaVariableCalidad(DataTable dtCategoriaVariableCalidad)
/* 152:    */   {
/* 153:214 */     this.dtCategoriaVariableCalidad = dtCategoriaVariableCalidad;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.configuracion.controller.CategoriaVariableCalidadBean
 * JD-Core Version:    0.7.0.1
 */