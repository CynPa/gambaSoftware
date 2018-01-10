/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCategoriaDocumentoDigitalizado;
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
/*  24:    */ public class CategoriaDocumentoDigitalizadoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 5562328087712294452L;
/*  28:    */   @EJB
/*  29:    */   private ServicioCategoriaDocumentoDigitalizado servicioCategoriaDocumentoDigitalizado;
/*  30:    */   private CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado;
/*  31:    */   private LazyDataModel<CategoriaDocumentoDigitalizado> listaCategoriaDocumentoDigitalizado;
/*  32:    */   private DataTable dtCategoriaDocumentoDigitalizado;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 46 */     this.listaCategoriaDocumentoDigitalizado = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<CategoriaDocumentoDigitalizado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 56 */         List<CategoriaDocumentoDigitalizado> lista = new ArrayList();
/*  44: 57 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 59 */         filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/*  47: 60 */         lista = CategoriaDocumentoDigitalizadoBean.this.servicioCategoriaDocumentoDigitalizado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 62 */         CategoriaDocumentoDigitalizadoBean.this.listaCategoriaDocumentoDigitalizado.setRowCount(CategoriaDocumentoDigitalizadoBean.this.servicioCategoriaDocumentoDigitalizado.contarPorCriterio(filters));
/*  50:    */         
/*  51: 64 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String editar()
/*  57:    */   {
/*  58: 71 */     if ((getCategoriaDocumentoDigitalizado() != null) && (getCategoriaDocumentoDigitalizado().getIdCategoriaDocumentoDigitalizado() != 0)) {
/*  59: 72 */       setEditado(true);
/*  60:    */     } else {
/*  61: 74 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  62:    */     }
/*  63: 76 */     return "";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String guardar()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70: 82 */       this.categoriaDocumentoDigitalizado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  71: 83 */       this.categoriaDocumentoDigitalizado.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  72: 84 */       this.servicioCategoriaDocumentoDigitalizado.guardar(this.categoriaDocumentoDigitalizado);
/*  73: 85 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  74: 86 */       setEditado(false);
/*  75: 87 */       limpiar();
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79: 89 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  80: 90 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  81:    */     }
/*  82: 92 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String eliminar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89: 98 */       this.servicioCategoriaDocumentoDigitalizado.eliminar(this.categoriaDocumentoDigitalizado);
/*  90: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:101 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  95:102 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  96:    */     }
/*  97:104 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String limpiar()
/* 101:    */   {
/* 102:109 */     this.categoriaDocumentoDigitalizado = new CategoriaDocumentoDigitalizado();
/* 103:110 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cargarDatos()
/* 107:    */   {
/* 108:116 */     return null;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public CategoriaDocumentoDigitalizado getCategoriaDocumentoDigitalizado()
/* 112:    */   {
/* 113:120 */     if (this.categoriaDocumentoDigitalizado == null) {
/* 114:121 */       this.categoriaDocumentoDigitalizado = new CategoriaDocumentoDigitalizado();
/* 115:    */     }
/* 116:123 */     return this.categoriaDocumentoDigitalizado;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setCategoriaDocumentoDigitalizado(CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado)
/* 120:    */   {
/* 121:128 */     this.categoriaDocumentoDigitalizado = categoriaDocumentoDigitalizado;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public LazyDataModel<CategoriaDocumentoDigitalizado> getListaCategoriaDocumentoDigitalizado()
/* 125:    */   {
/* 126:132 */     return this.listaCategoriaDocumentoDigitalizado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setListaCategoriaDocumentoDigitalizado(LazyDataModel<CategoriaDocumentoDigitalizado> listaCategoriaDocumentoDigitalizado)
/* 130:    */   {
/* 131:137 */     this.listaCategoriaDocumentoDigitalizado = listaCategoriaDocumentoDigitalizado;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public DataTable getDtCategoriaDocumentoDigitalizado()
/* 135:    */   {
/* 136:141 */     return this.dtCategoriaDocumentoDigitalizado;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setDtCategoriaDocumentoDigitalizado(DataTable dtCategoriaDocumentoDigitalizado)
/* 140:    */   {
/* 141:146 */     this.dtCategoriaDocumentoDigitalizado = dtCategoriaDocumentoDigitalizado;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.CategoriaDocumentoDigitalizadoBean
 * JD-Core Version:    0.7.0.1
 */