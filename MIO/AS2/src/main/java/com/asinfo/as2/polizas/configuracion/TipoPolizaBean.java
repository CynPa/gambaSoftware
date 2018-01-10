/*   1:    */ package com.asinfo.as2.polizas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.polizas.TipoPoliza;
/*   8:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioTipoPoliza;
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
/*  24:    */ public class TipoPolizaBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoPoliza servicioTipoPoliza;
/*  30:    */   private TipoPoliza tipoPoliza;
/*  31:    */   private LazyDataModel<TipoPoliza> listaTipoPoliza;
/*  32:    */   private DataTable dtTipoPoliza;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 72 */     this.listaTipoPoliza = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<TipoPoliza> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 79 */         List<TipoPoliza> lista = new ArrayList();
/*  44: 80 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 82 */         lista = TipoPolizaBean.this.servicioTipoPoliza.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48: 84 */         TipoPolizaBean.this.listaTipoPoliza.setRowCount(TipoPolizaBean.this.servicioTipoPoliza.contarPorCriterio(filters));
/*  49: 85 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void crearTipoPoliza()
/*  55:    */   {
/*  56: 99 */     this.tipoPoliza = new TipoPoliza();
/*  57:100 */     this.tipoPoliza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  58:101 */     this.tipoPoliza.setIdSucursal(AppUtil.getSucursal().getId());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63:111 */     if (getTipoPoliza().getIdTipoPoliza() > 0) {
/*  64:112 */       setEditado(true);
/*  65:    */     } else {
/*  66:114 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:116 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:126 */       this.servicioTipoPoliza.guardar(getTipoPoliza());
/*  76:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:128 */       setEditado(false);
/*  78:129 */       limpiar();
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  83:132 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  84:    */     }
/*  85:134 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String eliminar()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:145 */       this.servicioTipoPoliza.eliminar(getTipoPoliza());
/*  93:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  98:149 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  99:    */     }
/* 100:151 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String cargarDatos()
/* 104:    */   {
/* 105:160 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String limpiar()
/* 109:    */   {
/* 110:169 */     crearTipoPoliza();
/* 111:170 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public TipoPoliza getTipoPoliza()
/* 115:    */   {
/* 116:187 */     if (this.tipoPoliza == null) {
/* 117:188 */       crearTipoPoliza();
/* 118:    */     }
/* 119:190 */     return this.tipoPoliza;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTipoPoliza(TipoPoliza tipoPoliza)
/* 123:    */   {
/* 124:200 */     this.tipoPoliza = tipoPoliza;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public LazyDataModel<TipoPoliza> getListaTipoPoliza()
/* 128:    */   {
/* 129:209 */     return this.listaTipoPoliza;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setListaTipoPoliza(LazyDataModel<TipoPoliza> listaTipoPoliza)
/* 133:    */   {
/* 134:219 */     this.listaTipoPoliza = listaTipoPoliza;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public DataTable getDtTipoPoliza()
/* 138:    */   {
/* 139:228 */     return this.dtTipoPoliza;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDtTipoPoliza(DataTable dtTipoPoliza)
/* 143:    */   {
/* 144:238 */     this.dtTipoPoliza = dtTipoPoliza;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.configuracion.TipoPolizaBean
 * JD-Core Version:    0.7.0.1
 */