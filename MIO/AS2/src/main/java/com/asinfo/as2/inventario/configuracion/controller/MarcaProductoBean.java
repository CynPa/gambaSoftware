/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.MarcaProducto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcaProducto;
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
/*  24:    */ public class MarcaProductoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  28:    */   @EJB
/*  29:    */   private ServicioMarcaProducto servicioMarcaProducto;
/*  30:    */   private MarcaProducto marcaProducto;
/*  31:    */   private LazyDataModel<MarcaProducto> listaMarcaProducto;
/*  32:    */   private DataTable dtMarcaProducto;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 75 */     this.listaMarcaProducto = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<MarcaProducto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 82 */         List<MarcaProducto> lista = new ArrayList();
/*  44: 83 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 85 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 86 */         lista = MarcaProductoBean.this.servicioMarcaProducto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 88 */         MarcaProductoBean.this.listaMarcaProducto.setRowCount(MarcaProductoBean.this.servicioMarcaProducto.contarPorCriterio(filters));
/*  50:    */         
/*  51: 90 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58: 97 */     this.marcaProducto = new MarcaProducto();
/*  59: 98 */     this.marcaProducto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60: 99 */     this.marcaProducto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:100 */     this.marcaProducto.setActivo(true);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String editar()
/*  65:    */   {
/*  66:105 */     if (this.marcaProducto.getId() != 0) {
/*  67:106 */       setEditado(true);
/*  68:    */     } else {
/*  69:108 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  70:    */     }
/*  71:110 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String guardar()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:116 */       this.servicioMarcaProducto.guardar(this.marcaProducto);
/*  79:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  80:118 */       limpiar();
/*  81:119 */       setEditado(false);
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86:122 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:124 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:130 */       this.servicioMarcaProducto.eliminar(this.marcaProducto);
/*  96:131 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:133 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 101:134 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 102:    */     }
/* 103:136 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cargarDatos()
/* 107:    */   {
/* 108:141 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String limpiar()
/* 112:    */   {
/* 113:146 */     crearEntidad();
/* 114:147 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public MarcaProducto getMarcaProducto()
/* 118:    */   {
/* 119:154 */     return this.marcaProducto;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setMarcaProducto(MarcaProducto marcaProducto)
/* 123:    */   {
/* 124:158 */     this.marcaProducto = marcaProducto;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public LazyDataModel<MarcaProducto> getListaMarcaProducto()
/* 128:    */   {
/* 129:162 */     return this.listaMarcaProducto;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setListaMarcaProducto(LazyDataModel<MarcaProducto> listaMarcaProducto)
/* 133:    */   {
/* 134:166 */     this.listaMarcaProducto = listaMarcaProducto;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public DataTable getDtMarcaProducto()
/* 138:    */   {
/* 139:170 */     return this.dtMarcaProducto;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDtMarcaProducto(DataTable dtMarcaProducto)
/* 143:    */   {
/* 144:174 */     this.dtMarcaProducto = dtMarcaProducto;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.MarcaProductoBean
 * JD-Core Version:    0.7.0.1
 */