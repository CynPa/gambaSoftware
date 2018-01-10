/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import javax.faces.model.SelectItem;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTable;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class CategoriaProductoBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -6796877103729387961L;
/*  29:    */   @EJB
/*  30:    */   ServicioCategoriaProducto servicioCategoriaProducto;
/*  31:    */   private CategoriaProducto categoriaProducto;
/*  32:    */   private LazyDataModel<CategoriaProducto> listaCategoriaProducto;
/*  33:    */   private DataTable dataTableCategoriaProducto;
/*  34:    */   private List<SelectItem> categoriaProductoItems;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 62 */     this.listaCategoriaProducto = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<CategoriaProducto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 69 */         List<CategoriaProducto> lista = new ArrayList();
/*  46: 70 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 72 */         lista = CategoriaProductoBean.this.servicioCategoriaProducto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  49: 73 */         CategoriaProductoBean.this.listaCategoriaProducto.setRowCount(CategoriaProductoBean.this.servicioCategoriaProducto.contarPorCriterio(filters));
/*  50:    */         
/*  51: 75 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String editar()
/*  57:    */   {
/*  58: 84 */     if (this.categoriaProducto.getIdCategoriaProducto() > 0) {
/*  59: 85 */       setEditado(true);
/*  60:    */     } else {
/*  61: 87 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  62:    */     }
/*  63: 90 */     return "";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String guardar()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:100 */       this.servicioCategoriaProducto.guardar(this.categoriaProducto);
/*  71:101 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  72:102 */       limpiar();
/*  73:103 */       setEditado(false);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77:105 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  78:106 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  79:    */     }
/*  80:109 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String eliminar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:118 */       this.servicioCategoriaProducto.eliminar(this.categoriaProducto);
/*  88:119 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  93:122 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  94:    */     }
/*  95:125 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String cargarDatos()
/*  99:    */   {
/* 100:133 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void crearCategoriaProducto()
/* 104:    */   {
/* 105:142 */     this.categoriaProducto = new CategoriaProducto();
/* 106:143 */     this.categoriaProducto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 107:144 */     this.categoriaProducto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String cargarItems()
/* 111:    */   {
/* 112:152 */     if (this.categoriaProductoItems == null)
/* 113:    */     {
/* 114:153 */       this.categoriaProductoItems = new ArrayList();
/* 115:154 */       for (CategoriaProducto categoriaProducto : this.listaCategoriaProducto)
/* 116:    */       {
/* 117:155 */         SelectItem opcion = new SelectItem(Integer.valueOf(categoriaProducto.getIdCategoriaProducto()), categoriaProducto.getNombre());
/* 118:156 */         this.categoriaProductoItems.add(opcion);
/* 119:    */       }
/* 120:    */     }
/* 121:160 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String limpiar()
/* 125:    */   {
/* 126:168 */     crearCategoriaProducto();
/* 127:169 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ServicioCategoriaProducto getServicioCategoriaProductoBean()
/* 131:    */   {
/* 132:174 */     return this.servicioCategoriaProducto;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setServicioCategoriaProductoBean(ServicioCategoriaProducto servicioCategoriaProducto)
/* 136:    */   {
/* 137:179 */     this.servicioCategoriaProducto = servicioCategoriaProducto;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public CategoriaProducto getCategoriaProducto()
/* 141:    */   {
/* 142:183 */     return this.categoriaProducto;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 146:    */   {
/* 147:187 */     this.categoriaProducto = categoriaProducto;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public LazyDataModel<CategoriaProducto> getListaCategoriaProductos()
/* 151:    */   {
/* 152:191 */     return this.listaCategoriaProducto;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setListaCategoriaProductos(LazyDataModel<CategoriaProducto> listaCategoriaProducto)
/* 156:    */   {
/* 157:195 */     this.listaCategoriaProducto = listaCategoriaProducto;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public DataTable getDataTableCategoriaProducto()
/* 161:    */   {
/* 162:199 */     return this.dataTableCategoriaProducto;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDataTableCategoriaProducto(DataTable dataTableCategoriaProducto)
/* 166:    */   {
/* 167:203 */     this.dataTableCategoriaProducto = dataTableCategoriaProducto;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<SelectItem> getCategoriaProductoItems()
/* 171:    */   {
/* 172:207 */     return this.categoriaProductoItems;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setCategoriaProductoItems(List<SelectItem> categoriaProductoItems)
/* 176:    */   {
/* 177:211 */     this.categoriaProductoItems = categoriaProductoItems;
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.CategoriaProductoBean
 * JD-Core Version:    0.7.0.1
 */