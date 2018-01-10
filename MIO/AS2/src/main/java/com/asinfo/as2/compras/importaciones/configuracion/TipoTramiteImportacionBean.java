/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioTipoTramiteImportacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoTramiteImportacion;
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
/*  23:    */ public class TipoTramiteImportacionBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -9093956664723935436L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioTipoTramiteImportacion servicioTipoTramiteImportacion;
/*  29:    */   private TipoTramiteImportacion tipoTramiteImportacion;
/*  30:    */   private LazyDataModel<TipoTramiteImportacion> listaTipoTramiteImportacion;
/*  31:    */   private DataTable dtTipoTramiteImportacion;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 72 */     this.listaTipoTramiteImportacion = new LazyDataModel()
/*  37:    */     {
/*  38:    */       private static final long serialVersionUID = 6332796485708443987L;
/*  39:    */       
/*  40:    */       public List<TipoTramiteImportacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  41:    */       {
/*  42: 79 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  43: 80 */         List<TipoTramiteImportacion> lista = TipoTramiteImportacionBean.this.servicioTipoTramiteImportacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  44:    */         
/*  45: 82 */         TipoTramiteImportacionBean.this.listaTipoTramiteImportacion.setRowCount(TipoTramiteImportacionBean.this.servicioTipoTramiteImportacion.contarPorCriterio(filters));
/*  46: 83 */         return lista;
/*  47:    */       }
/*  48:    */     };
/*  49:    */   }
/*  50:    */   
/*  51:    */   private void crearEntidad()
/*  52:    */   {
/*  53: 96 */     this.tipoTramiteImportacion = new TipoTramiteImportacion();
/*  54: 97 */     this.tipoTramiteImportacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  55: 98 */     this.tipoTramiteImportacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String editar()
/*  59:    */   {
/*  60:107 */     if (getTipoTramiteImportacion().getIdTipoTramiteImportacion() > 0) {
/*  61:108 */       setEditado(true);
/*  62:    */     } else {
/*  63:110 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  64:    */     }
/*  65:112 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String guardar()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:122 */       this.servicioTipoTramiteImportacion.guardar(this.tipoTramiteImportacion);
/*  73:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  74:124 */       setEditado(false);
/*  75:125 */       limpiar();
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  80:128 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  81:    */     }
/*  82:130 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String eliminar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:140 */       this.servicioTipoTramiteImportacion.eliminar(this.tipoTramiteImportacion);
/*  90:141 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  95:144 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  96:    */     }
/*  97:146 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String cargarDatos()
/* 101:    */   {
/* 102:155 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String limpiar()
/* 106:    */   {
/* 107:164 */     crearEntidad();
/* 108:165 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public TipoTramiteImportacion getTipoTramiteImportacion()
/* 112:    */   {
/* 113:177 */     if (this.tipoTramiteImportacion == null) {
/* 114:178 */       crearEntidad();
/* 115:    */     }
/* 116:180 */     return this.tipoTramiteImportacion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setTipoTramiteImportacion(TipoTramiteImportacion tipoTramiteImportacion)
/* 120:    */   {
/* 121:188 */     this.tipoTramiteImportacion = tipoTramiteImportacion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public LazyDataModel<TipoTramiteImportacion> getListaTipoTramiteImportacion()
/* 125:    */   {
/* 126:196 */     return this.listaTipoTramiteImportacion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setListaTipoTramiteImportacion(LazyDataModel<TipoTramiteImportacion> listaTipoTramiteImportacion)
/* 130:    */   {
/* 131:204 */     this.listaTipoTramiteImportacion = listaTipoTramiteImportacion;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public DataTable getDtTipoTramiteImportacion()
/* 135:    */   {
/* 136:212 */     return this.dtTipoTramiteImportacion;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setDtTipoTramiteImportacion(DataTable dtTipoTramiteImportacion)
/* 140:    */   {
/* 141:220 */     this.dtTipoTramiteImportacion = dtTipoTramiteImportacion;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.TipoTramiteImportacionBean
 * JD-Core Version:    0.7.0.1
 */