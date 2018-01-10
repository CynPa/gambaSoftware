/*   1:    */ package com.asinfo.as2.compras.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.configuracion.servicio.impl.ServicioTipoOperacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoOperacion;
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
/*  24:    */ public class TipoOperacionBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoOperacion servicioTipoOperacion;
/*  30:    */   private TipoOperacion tipoOperacion;
/*  31:    */   private LazyDataModel<TipoOperacion> listaTipoOperacion;
/*  32:    */   private DataTable dtTipoOperacion;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 71 */     this.listaTipoOperacion = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<TipoOperacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 78 */         List<TipoOperacion> lista = new ArrayList();
/*  44: 79 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 81 */         lista = TipoOperacionBean.this.servicioTipoOperacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47: 82 */         TipoOperacionBean.this.listaTipoOperacion.setRowCount(TipoOperacionBean.this.servicioTipoOperacion.contarPorCriterio(filters));
/*  48:    */         
/*  49: 84 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void crearTipoOperacion()
/*  55:    */   {
/*  56: 98 */     this.tipoOperacion = new TipoOperacion();
/*  57: 99 */     this.tipoOperacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  58:100 */     this.tipoOperacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63:109 */     if (getTipoOperacion().getIdTipoOperacion() > 0) {
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
/*  75:124 */       this.servicioTipoOperacion.guardar(getTipoOperacion());
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
/*  92:142 */       this.servicioTipoOperacion.eliminar(getTipoOperacion());
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
/* 110:166 */     crearTipoOperacion();
/* 111:167 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public TipoOperacion getTipoOperacion()
/* 115:    */   {
/* 116:183 */     return this.tipoOperacion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setTipoOperacion(TipoOperacion tipoOperacion)
/* 120:    */   {
/* 121:193 */     this.tipoOperacion = tipoOperacion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public LazyDataModel<TipoOperacion> getListaTipoOperacion()
/* 125:    */   {
/* 126:202 */     return this.listaTipoOperacion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setListaTipoOperacion(LazyDataModel<TipoOperacion> listaTipoOperacion)
/* 130:    */   {
/* 131:212 */     this.listaTipoOperacion = listaTipoOperacion;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public DataTable getDtTipoOperacion()
/* 135:    */   {
/* 136:221 */     return this.dtTipoOperacion;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setDtTipoOperacion(DataTable dtTipoOperacion)
/* 140:    */   {
/* 141:231 */     this.dtTipoOperacion = dtTipoOperacion;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.configuracion.TipoOperacionBean
 * JD-Core Version:    0.7.0.1
 */