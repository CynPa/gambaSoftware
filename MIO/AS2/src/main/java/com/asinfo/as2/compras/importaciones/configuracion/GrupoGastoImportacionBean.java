/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGrupoGastoImportacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.GrupoGastoImportacion;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.annotation.PostConstruct;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ import org.primefaces.component.datatable.DataTable;
/*  18:    */ import org.primefaces.model.LazyDataModel;
/*  19:    */ import org.primefaces.model.SortOrder;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class GrupoGastoImportacionBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -7938231031096180731L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioGrupoGastoImportacion servicioGrupoGastoImportacion;
/*  29:    */   private GrupoGastoImportacion grupoGastoImportacion;
/*  30:    */   private LazyDataModel<GrupoGastoImportacion> listaGrupoGastoImportacion;
/*  31:    */   private DataTable dtGrupoGastoImportacion;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 69 */     this.listaGrupoGastoImportacion = new LazyDataModel()
/*  37:    */     {
/*  38:    */       private static final long serialVersionUID = 6332796485708443987L;
/*  39:    */       
/*  40:    */       public List<GrupoGastoImportacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  41:    */       {
/*  42: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  43: 77 */         List<GrupoGastoImportacion> lista = GrupoGastoImportacionBean.this.servicioGrupoGastoImportacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  44:    */         
/*  45: 79 */         GrupoGastoImportacionBean.this.listaGrupoGastoImportacion.setRowCount(GrupoGastoImportacionBean.this.servicioGrupoGastoImportacion.contarPorCriterio(filters));
/*  46: 80 */         return lista;
/*  47:    */       }
/*  48:    */     };
/*  49:    */   }
/*  50:    */   
/*  51:    */   private void crearEntidad()
/*  52:    */   {
/*  53: 94 */     this.grupoGastoImportacion = new GrupoGastoImportacion();
/*  54: 95 */     this.grupoGastoImportacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  55: 96 */     this.grupoGastoImportacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String editar()
/*  59:    */   {
/*  60:105 */     if (getGrupoGastoImportacion().getIdGrupoGastoImportacion() > 0) {
/*  61:106 */       setEditado(true);
/*  62:    */     } else {
/*  63:108 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  64:    */     }
/*  65:110 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String guardar()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:120 */       this.servicioGrupoGastoImportacion.guardar(this.grupoGastoImportacion);
/*  73:121 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  74:122 */       setEditado(false);
/*  75:123 */       limpiar();
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79:125 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  80:126 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  81:    */     }
/*  82:128 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String eliminar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:138 */       this.servicioGrupoGastoImportacion.eliminar(this.grupoGastoImportacion);
/*  90:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  95:142 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  96:    */     }
/*  97:144 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String cargarDatos()
/* 101:    */   {
/* 102:153 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String limpiar()
/* 106:    */   {
/* 107:162 */     crearEntidad();
/* 108:163 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public GrupoGastoImportacion getGrupoGastoImportacion()
/* 112:    */   {
/* 113:180 */     return this.grupoGastoImportacion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setGrupoGastoImportacion(GrupoGastoImportacion grupoGastoImportacion)
/* 117:    */   {
/* 118:190 */     this.grupoGastoImportacion = grupoGastoImportacion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public LazyDataModel<GrupoGastoImportacion> getListaGrupoGastoImportacion()
/* 122:    */   {
/* 123:200 */     return this.listaGrupoGastoImportacion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setListaGrupoGastoImportacion(LazyDataModel<GrupoGastoImportacion> listaGrupoGastoImportacion)
/* 127:    */   {
/* 128:208 */     this.listaGrupoGastoImportacion = listaGrupoGastoImportacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public DataTable getDtGrupoGastoImportacion()
/* 132:    */   {
/* 133:217 */     return this.dtGrupoGastoImportacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setDtGrupoGastoImportacion(DataTable dtGrupoGastoImportacion)
/* 137:    */   {
/* 138:227 */     this.dtGrupoGastoImportacion = dtGrupoGastoImportacion;
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.GrupoGastoImportacionBean
 * JD-Core Version:    0.7.0.1
 */