/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.JsfUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.event.SelectEvent;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class SubcategoriaEquipoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @EJB
/*  33:    */   private ServicioGenerico<SubcategoriaEquipo> servicioSubcategoriaEquipo;
/*  34:    */   @EJB
/*  35:    */   private ServicioGenerico<CategoriaEquipo> servicioCategoriaEquipo;
/*  36:    */   private SubcategoriaEquipo subcategoriaEquipo;
/*  37:    */   private LazyDataModel<SubcategoriaEquipo> listaSubcategoriaEquipo;
/*  38:    */   private List<CategoriaEquipo> listaCategoriaEquipo;
/*  39:    */   private DataTable dtSubcategoriaEquipo;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 56 */     this.listaSubcategoriaEquipo = new LazyDataModel()
/*  45:    */     {
/*  46:    */       private static final long serialVersionUID = 1L;
/*  47:    */       
/*  48:    */       public List<SubcategoriaEquipo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 63 */         List<SubcategoriaEquipo> lista = new ArrayList();
/*  51: 64 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  52:    */         
/*  53: 66 */         List<String> lcampos = new ArrayList();
/*  54: 67 */         lcampos.add("categoriaEquipo");
/*  55:    */         
/*  56: 69 */         lista = SubcategoriaEquipoBean.this.servicioSubcategoriaEquipo.obtenerListaPorPagina(SubcategoriaEquipo.class, startIndex, pageSize, sortField, ordenar, filters, lcampos);
/*  57:    */         
/*  58: 71 */         SubcategoriaEquipoBean.this.listaSubcategoriaEquipo.setRowCount(SubcategoriaEquipoBean.this.servicioSubcategoriaEquipo.contarPorCriterio(SubcategoriaEquipo.class, filters));
/*  59:    */         
/*  60: 73 */         return lista;
/*  61:    */       }
/*  62:    */     };
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String editar()
/*  66:    */   {
/*  67: 86 */     if (getSubcategoriaEquipo().getId() > 0) {
/*  68: 87 */       setEditado(true);
/*  69:    */     } else {
/*  70: 89 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  71:    */     }
/*  72: 91 */     return "";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String guardar()
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79:102 */       this.servicioSubcategoriaEquipo.guardarValidar(this.subcategoriaEquipo);
/*  80:103 */       limpiar();
/*  81:104 */       setEditado(false);
/*  82:105 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  83:    */     }
/*  84:    */     catch (AS2Exception e)
/*  85:    */     {
/*  86:107 */       JsfUtil.addErrorMessage(e, "");
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  91:110 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  92:    */     }
/*  93:112 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String limpiar()
/*  97:    */   {
/*  98:122 */     crearSubcategoriaEquipo();
/*  99:123 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String eliminar()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:134 */       this.servicioSubcategoriaEquipo.eliminar(this.subcategoriaEquipo);
/* 107:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:137 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 112:138 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 113:    */     }
/* 114:140 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String cargarDatos()
/* 118:    */   {
/* 119:150 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void crearSubcategoriaEquipo()
/* 123:    */   {
/* 124:157 */     this.subcategoriaEquipo = new SubcategoriaEquipo();
/* 125:158 */     this.subcategoriaEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 126:159 */     this.subcategoriaEquipo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 127:160 */     this.subcategoriaEquipo.setActivo(true);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void onRowSelect(SelectEvent event)
/* 131:    */   {
/* 132:167 */     SubcategoriaEquipo categoriaEquipo1 = (SubcategoriaEquipo)event.getObject();
/* 133:168 */     setSubcategoriaEquipo(categoriaEquipo1);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public SubcategoriaEquipo getSubcategoriaEquipo()
/* 137:    */   {
/* 138:177 */     if (this.subcategoriaEquipo == null) {
/* 139:178 */       crearSubcategoriaEquipo();
/* 140:    */     }
/* 141:180 */     return this.subcategoriaEquipo;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setSubcategoriaEquipo(SubcategoriaEquipo SubcategoriaEquipo)
/* 145:    */   {
/* 146:190 */     this.subcategoriaEquipo = SubcategoriaEquipo;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public LazyDataModel<SubcategoriaEquipo> getListaSubcategoriaEquipo()
/* 150:    */   {
/* 151:199 */     return this.listaSubcategoriaEquipo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setListaSubcategoriaEquipo(LazyDataModel<SubcategoriaEquipo> listaSubcategoriaEquipo)
/* 155:    */   {
/* 156:209 */     this.listaSubcategoriaEquipo = listaSubcategoriaEquipo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public DataTable getDtSubcategoriaEquipo()
/* 160:    */   {
/* 161:218 */     return this.dtSubcategoriaEquipo;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setDtSubcategoriaEquipo(DataTable dtSubcategoriaEquipo)
/* 165:    */   {
/* 166:228 */     this.dtSubcategoriaEquipo = dtSubcategoriaEquipo;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public List<CategoriaEquipo> getListaCategoriaEquipo()
/* 170:    */   {
/* 171:235 */     if (this.listaCategoriaEquipo == null)
/* 172:    */     {
/* 173:236 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 174:237 */       this.listaCategoriaEquipo = this.servicioCategoriaEquipo.obtenerListaCombo(CategoriaEquipo.class, "nombre", true, filters);
/* 175:    */     }
/* 176:240 */     return this.listaCategoriaEquipo;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setListaCategoriaEquipo(List<CategoriaEquipo> listaCategoriaEquipo)
/* 180:    */   {
/* 181:248 */     this.listaCategoriaEquipo = listaCategoriaEquipo;
/* 182:    */   }
/* 183:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.SubcategoriaEquipoBean
 * JD-Core Version:    0.7.0.1
 */