/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaUnidadManejo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
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
/*  26:    */ public class CategoriaUnidadManejoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<CategoriaUnidadManejo> servicioCategoriaUnidadManejo;
/*  32:    */   private CategoriaUnidadManejo categoriaUnidadManejo;
/*  33:    */   private LazyDataModel<CategoriaUnidadManejo> listaCategoriaUnidadManejo;
/*  34:    */   private DataTable dataTableCategoriaUnidadManejo;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 69 */     this.listaCategoriaUnidadManejo = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  42:    */       
/*  43:    */       public List<CategoriaUnidadManejo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 75 */         List<CategoriaUnidadManejo> lista = new ArrayList();
/*  46: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         try
/*  48:    */         {
/*  49: 78 */           lista = CategoriaUnidadManejoBean.this.servicioCategoriaUnidadManejo.obtenerListaPorPagina(CategoriaUnidadManejo.class, startIndex, pageSize, sortField, ordenar, filters);
/*  50:    */         }
/*  51:    */         catch (Exception e)
/*  52:    */         {
/*  53: 82 */           e.printStackTrace();
/*  54:    */         }
/*  55: 84 */         CategoriaUnidadManejoBean.this.listaCategoriaUnidadManejo.setRowCount(CategoriaUnidadManejoBean.this.servicioCategoriaUnidadManejo.contarPorCriterio(CategoriaUnidadManejo.class, filters));
/*  56: 85 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63: 98 */     if (getCategoriaUnidadManejo().getIdCategoriaUnidadManejo() > 0) {
/*  64: 99 */       setEditado(true);
/*  65:    */     } else {
/*  66:101 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:104 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:115 */       this.servicioCategoriaUnidadManejo.guardarValidar(this.categoriaUnidadManejo);
/*  76:116 */       cargarDatos();
/*  77:    */       
/*  78:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:    */     }
/*  80:    */     catch (AS2Exception e)
/*  81:    */     {
/*  82:120 */       JsfUtil.addErrorMessage(e, "");
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87:123 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  88:    */     }
/*  89:125 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String eliminar()
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:136 */       this.servicioCategoriaUnidadManejo.eliminar(this.categoriaUnidadManejo);
/*  97:137 */       cargarDatos();
/*  98:    */       
/*  99:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 104:142 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 105:    */     }
/* 106:144 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String cargarDatos()
/* 110:    */   {
/* 111:155 */     setEditado(false);
/* 112:    */     try
/* 113:    */     {
/* 114:158 */       limpiar();
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:161 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 119:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:164 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String limpiar()
/* 125:    */   {
/* 126:174 */     this.categoriaUnidadManejo = new CategoriaUnidadManejo();
/* 127:175 */     this.categoriaUnidadManejo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 128:176 */     this.categoriaUnidadManejo.setIdSucursal(AppUtil.getSucursal().getId());
/* 129:177 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public CategoriaUnidadManejo getCategoriaUnidadManejo()
/* 133:    */   {
/* 134:186 */     return this.categoriaUnidadManejo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setCategoriaUnidadManejo(CategoriaUnidadManejo categoriaUnidadManejo)
/* 138:    */   {
/* 139:196 */     this.categoriaUnidadManejo = categoriaUnidadManejo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public DataTable getDataTableCategoriaUnidadManejo()
/* 143:    */   {
/* 144:205 */     return this.dataTableCategoriaUnidadManejo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDataTableCategoriaUnidadManejo(DataTable dataTableCategoriaUnidadManejo)
/* 148:    */   {
/* 149:209 */     this.dataTableCategoriaUnidadManejo = dataTableCategoriaUnidadManejo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public LazyDataModel<CategoriaUnidadManejo> getListaCategoriaUnidadManejo()
/* 153:    */   {
/* 154:213 */     if (this.listaCategoriaUnidadManejo == null) {
/* 155:214 */       cargarDatos();
/* 156:    */     }
/* 157:216 */     return this.listaCategoriaUnidadManejo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaCategoriaUnidadManejo(LazyDataModel<CategoriaUnidadManejo> listaCategoriaUnidadManejo)
/* 161:    */   {
/* 162:220 */     this.listaCategoriaUnidadManejo = listaCategoriaUnidadManejo;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.CategoriaUnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */