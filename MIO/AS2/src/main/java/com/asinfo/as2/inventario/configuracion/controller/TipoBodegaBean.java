/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.TipoBodega;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoBodega;
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
/*  24:    */ public class TipoBodegaBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1461837273230255977L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoBodega servicioTipoBodega;
/*  30:    */   private TipoBodega tipoBodega;
/*  31:    */   private LazyDataModel<TipoBodega> listaTipoBodega;
/*  32:    */   private DataTable dataTableTipoBodega;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 60 */     this.listaTipoBodega = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<TipoBodega> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 67 */         List<TipoBodega> lista = new ArrayList();
/*  44: 68 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 70 */         lista = TipoBodegaBean.this.servicioTipoBodega.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47: 71 */         TipoBodegaBean.this.listaTipoBodega.setRowCount(TipoBodegaBean.this.servicioTipoBodega.contarPorCriterio(filters));
/*  48:    */         
/*  49: 73 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String editar()
/*  55:    */   {
/*  56: 86 */     if (getTipoBodega().getIdTipoBodega() > 0) {
/*  57: 87 */       setEditado(true);
/*  58:    */     } else {
/*  59: 89 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  60:    */     }
/*  61: 92 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String limpiar()
/*  65:    */   {
/*  66:102 */     crearTipoBodega();
/*  67:    */     
/*  68:104 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:116 */       this.servicioTipoBodega.guardar(this.tipoBodega);
/*  76:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:118 */       limpiar();
/*  78:119 */       setEditado(false);
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  83:123 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  84:    */     }
/*  85:126 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String eliminar()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:137 */       this.servicioTipoBodega.eliminar(this.tipoBodega);
/*  93:138 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:140 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  98:141 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  99:    */     }
/* 100:144 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String cargarDatos()
/* 104:    */   {
/* 105:154 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void crearTipoBodega()
/* 109:    */   {
/* 110:163 */     this.tipoBodega = new TipoBodega();
/* 111:164 */     this.tipoBodega.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 112:165 */     this.tipoBodega.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 113:    */   }
/* 114:    */   
/* 115:    */   public TipoBodega getTipoBodega()
/* 116:    */   {
/* 117:175 */     if (this.tipoBodega == null) {
/* 118:176 */       crearTipoBodega();
/* 119:    */     }
/* 120:179 */     return this.tipoBodega;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setTipoBodega(TipoBodega tipoBodega)
/* 124:    */   {
/* 125:189 */     this.tipoBodega = tipoBodega;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public LazyDataModel<TipoBodega> getListaTipoBodega()
/* 129:    */   {
/* 130:199 */     return this.listaTipoBodega;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setListaTipoBodega(LazyDataModel<TipoBodega> listaTipoBodega)
/* 134:    */   {
/* 135:209 */     this.listaTipoBodega = listaTipoBodega;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public DataTable getDataTableTipoBodega()
/* 139:    */   {
/* 140:218 */     return this.dataTableTipoBodega;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setDataTableTipoBodega(DataTable dataTableTipoBodega)
/* 144:    */   {
/* 145:228 */     this.dataTableTipoBodega = dataTableTipoBodega;
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.TipoBodegaBean
 * JD-Core Version:    0.7.0.1
 */