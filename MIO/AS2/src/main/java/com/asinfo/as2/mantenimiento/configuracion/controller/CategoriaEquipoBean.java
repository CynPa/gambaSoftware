/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
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
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.event.SelectEvent;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class CategoriaEquipoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @EJB
/*  33:    */   private ServicioGenerico<CategoriaEquipo> servicioCategoriaEquipo;
/*  34:    */   private CategoriaEquipo categoriaEquipo;
/*  35:    */   private LazyDataModel<CategoriaEquipo> listaCategoriaEquipo;
/*  36:    */   private List<SelectItem> categoriaEquipoItems;
/*  37:    */   private DataTable dtCategoriaEquipo;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 54 */     this.listaCategoriaEquipo = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<CategoriaEquipo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 61 */         List<CategoriaEquipo> lista = new ArrayList();
/*  49: 62 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 64 */         lista = CategoriaEquipoBean.this.servicioCategoriaEquipo.obtenerListaPorPagina(CategoriaEquipo.class, startIndex, pageSize, sortField, ordenar, filters);
/*  52: 65 */         CategoriaEquipoBean.this.listaCategoriaEquipo.setRowCount(CategoriaEquipoBean.this.servicioCategoriaEquipo.contarPorCriterio(CategoriaEquipo.class, filters));
/*  53:    */         
/*  54: 67 */         return lista;
/*  55:    */       }
/*  56:    */     };
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String editar()
/*  60:    */   {
/*  61: 80 */     if (getCategoriaEquipo().getId() > 0) {
/*  62: 81 */       setEditado(true);
/*  63:    */     } else {
/*  64: 83 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  65:    */     }
/*  66: 85 */     return "";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String guardar()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73: 96 */       this.servicioCategoriaEquipo.guardarValidar(this.categoriaEquipo);
/*  74: 97 */       limpiar();
/*  75: 98 */       setEditado(false);
/*  76: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:    */     }
/*  78:    */     catch (AS2Exception e)
/*  79:    */     {
/*  80:101 */       JsfUtil.addErrorMessage(e, "");
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:103 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:104 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:106 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String limpiar()
/*  91:    */   {
/*  92:116 */     crearCategoriaEquipo();
/*  93:117 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String eliminar()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:128 */       this.servicioCategoriaEquipo.eliminar(this.categoriaEquipo);
/* 101:129 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 106:132 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 107:    */     }
/* 108:134 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String cargarDatos()
/* 112:    */   {
/* 113:144 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void cargarDatosCategoriaEquipo()
/* 117:    */   {
/* 118:151 */     List<CategoriaEquipo> categoriaEquipoes = new ArrayList();
/* 119:152 */     categoriaEquipoes = this.servicioCategoriaEquipo.obtenerListaCombo(CategoriaEquipo.class, "nombre", true, null);
/* 120:153 */     this.categoriaEquipoItems = new ArrayList();
/* 121:155 */     for (CategoriaEquipo CategoriaEquipoX : categoriaEquipoes)
/* 122:    */     {
/* 123:156 */       int value = CategoriaEquipoX.getIdCategoriaEquipo();
/* 124:157 */       String label = CategoriaEquipoX.getNombre();
/* 125:158 */       SelectItem opcion = new SelectItem(Integer.valueOf(value), label);
/* 126:159 */       this.categoriaEquipoItems.add(opcion);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void crearCategoriaEquipo()
/* 131:    */   {
/* 132:168 */     this.categoriaEquipo = new CategoriaEquipo();
/* 133:169 */     this.categoriaEquipo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 134:170 */     this.categoriaEquipo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 135:171 */     this.categoriaEquipo.setActivo(true);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void onRowSelect(SelectEvent event)
/* 139:    */   {
/* 140:178 */     CategoriaEquipo categoriaEquipo1 = (CategoriaEquipo)event.getObject();
/* 141:179 */     setCategoriaEquipo(categoriaEquipo1);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public CategoriaEquipo getCategoriaEquipo()
/* 145:    */   {
/* 146:188 */     if (this.categoriaEquipo == null) {
/* 147:189 */       crearCategoriaEquipo();
/* 148:    */     }
/* 149:191 */     return this.categoriaEquipo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setCategoriaEquipo(CategoriaEquipo CategoriaEquipo)
/* 153:    */   {
/* 154:201 */     this.categoriaEquipo = CategoriaEquipo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public LazyDataModel<CategoriaEquipo> getListaCategoriaEquipo()
/* 158:    */   {
/* 159:210 */     return this.listaCategoriaEquipo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaCategoriaEquipo(LazyDataModel<CategoriaEquipo> listaCategoriaEquipo)
/* 163:    */   {
/* 164:220 */     this.listaCategoriaEquipo = listaCategoriaEquipo;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<SelectItem> getCategoriaEquipoItems()
/* 168:    */   {
/* 169:229 */     return this.categoriaEquipoItems;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setCategoriaEquipoItems(List<SelectItem> CategoriaEquipoItems)
/* 173:    */   {
/* 174:239 */     this.categoriaEquipoItems = CategoriaEquipoItems;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public DataTable getDtCategoriaEquipo()
/* 178:    */   {
/* 179:248 */     return this.dtCategoriaEquipo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setDtCategoriaEquipo(DataTable dtCategoriaEquipo)
/* 183:    */   {
/* 184:258 */     this.dtCategoriaEquipo = dtCategoriaEquipo;
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.CategoriaEquipoBean
 * JD-Core Version:    0.7.0.1
 */