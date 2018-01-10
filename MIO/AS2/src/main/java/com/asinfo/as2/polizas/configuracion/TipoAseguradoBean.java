/*   1:    */ package com.asinfo.as2.polizas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.polizas.TipoAsegurado;
/*   8:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioTipoAsegurado;
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
/*  24:    */ public class TipoAseguradoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoAsegurado servicioTipoAsegurado;
/*  30:    */   private TipoAsegurado tipoAsegurado;
/*  31:    */   private LazyDataModel<TipoAsegurado> listaTipoAsegurado;
/*  32:    */   private DataTable dtTipoAsegurado;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 71 */     this.listaTipoAsegurado = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<TipoAsegurado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 78 */         List<TipoAsegurado> lista = new ArrayList();
/*  44: 79 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 81 */         lista = TipoAseguradoBean.this.servicioTipoAsegurado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48: 83 */         TipoAseguradoBean.this.listaTipoAsegurado.setRowCount(TipoAseguradoBean.this.servicioTipoAsegurado.contarPorCriterio(filters));
/*  49: 84 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void crearTipoAsegurado()
/*  55:    */   {
/*  56: 98 */     this.tipoAsegurado = new TipoAsegurado();
/*  57: 99 */     this.tipoAsegurado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  58:100 */     this.tipoAsegurado.setIdSucursal(AppUtil.getSucursal().getId());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63:109 */     if (getTipoAsegurado().getIdTipoAsegurado() > 0) {
/*  64:110 */       setEditado(true);
/*  65:    */     } else {
/*  66:112 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:114 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:124 */       this.servicioTipoAsegurado.guardar(getTipoAsegurado());
/*  76:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:126 */       setEditado(false);
/*  78:127 */       limpiar();
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  83:130 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  84:    */     }
/*  85:132 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String eliminar()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:142 */       this.servicioTipoAsegurado.eliminar(getTipoAsegurado());
/*  93:143 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:145 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  98:146 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  99:    */     }
/* 100:148 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String cargarDatos()
/* 104:    */   {
/* 105:157 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String limpiar()
/* 109:    */   {
/* 110:166 */     crearTipoAsegurado();
/* 111:167 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public TipoAsegurado getTipoAsegurado()
/* 115:    */   {
/* 116:184 */     if (this.tipoAsegurado == null) {
/* 117:185 */       crearTipoAsegurado();
/* 118:    */     }
/* 119:187 */     return this.tipoAsegurado;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTipoAsegurado(TipoAsegurado tipoAsegurado)
/* 123:    */   {
/* 124:197 */     this.tipoAsegurado = tipoAsegurado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public LazyDataModel<TipoAsegurado> getListaTipoAsegurado()
/* 128:    */   {
/* 129:206 */     return this.listaTipoAsegurado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setListaTipoAsegurado(LazyDataModel<TipoAsegurado> listaTipoAsegurado)
/* 133:    */   {
/* 134:216 */     this.listaTipoAsegurado = listaTipoAsegurado;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public DataTable getDtTipoAsegurado()
/* 138:    */   {
/* 139:225 */     return this.dtTipoAsegurado;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDtTipoAsegurado(DataTable dtTipoAsegurado)
/* 143:    */   {
/* 144:235 */     this.dtTipoAsegurado = dtTipoAsegurado;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.configuracion.TipoAseguradoBean
 * JD-Core Version:    0.7.0.1
 */