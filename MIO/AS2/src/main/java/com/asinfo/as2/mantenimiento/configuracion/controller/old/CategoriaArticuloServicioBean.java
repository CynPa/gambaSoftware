/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.CategoriaArticuloServicio;
/*   8:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioCategoriaArticuloServicio;
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
/*  24:    */ public class CategoriaArticuloServicioBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  28:    */   @EJB
/*  29:    */   private ServicioCategoriaArticuloServicio servicioCategoriaArticuloServicio;
/*  30:    */   private CategoriaArticuloServicio categoriaArticuloServicio;
/*  31:    */   private LazyDataModel<CategoriaArticuloServicio> listaCategoriaArticuloServicio;
/*  32:    */   private DataTable dtCategoriaArticuloServicio;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 77 */     this.listaCategoriaArticuloServicio = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<CategoriaArticuloServicio> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 85 */         List<CategoriaArticuloServicio> lista = new ArrayList();
/*  44: 86 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 88 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 89 */         lista = CategoriaArticuloServicioBean.this.servicioCategoriaArticuloServicio.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 91 */         CategoriaArticuloServicioBean.this.listaCategoriaArticuloServicio.setRowCount(CategoriaArticuloServicioBean.this.servicioCategoriaArticuloServicio.contarPorCriterio(filters));
/*  50:    */         
/*  51: 93 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58:111 */     this.categoriaArticuloServicio = new CategoriaArticuloServicio();
/*  59:112 */     this.categoriaArticuloServicio.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:113 */     this.categoriaArticuloServicio.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:122 */     if (getCategoriaArticuloServicio().getIdCategoriaArticuloServicio() > 0) {
/*  66:125 */       setEditado(true);
/*  67:    */     } else {
/*  68:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:129 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:139 */       this.servicioCategoriaArticuloServicio.guardar(this.categoriaArticuloServicio);
/*  78:140 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:141 */       limpiar();
/*  80:142 */       setEditado(false);
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:145 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:147 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:157 */       this.servicioCategoriaArticuloServicio.eliminar(this.categoriaArticuloServicio);
/*  95:158 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:160 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:161 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:163 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:172 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:181 */     crearEntidad();
/* 113:182 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public CategoriaArticuloServicio getCategoriaArticuloServicio()
/* 117:    */   {
/* 118:195 */     return this.categoriaArticuloServicio;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setCategoriaArticuloServicio(CategoriaArticuloServicio categoriaArticuloServicio)
/* 122:    */   {
/* 123:205 */     this.categoriaArticuloServicio = categoriaArticuloServicio;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<CategoriaArticuloServicio> getListaCategoriaArticuloServicio()
/* 127:    */   {
/* 128:214 */     return this.listaCategoriaArticuloServicio;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaCategoriaArticuloServicio(LazyDataModel<CategoriaArticuloServicio> listaCategoriaArticuloServicio)
/* 132:    */   {
/* 133:224 */     this.listaCategoriaArticuloServicio = listaCategoriaArticuloServicio;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtCategoriaArticuloServicio()
/* 137:    */   {
/* 138:233 */     return this.dtCategoriaArticuloServicio;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtCategoriaArticuloServicio(DataTable dtCategoriaArticuloServicio)
/* 142:    */   {
/* 143:243 */     this.dtCategoriaArticuloServicio = dtCategoriaArticuloServicio;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.CategoriaArticuloServicioBean
 * JD-Core Version:    0.7.0.1
 */