/*   1:    */ package com.asinfo.as2.produccion.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.produccion.TarifaOperacion;
/*   8:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioTarifaOperacion;
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
/*  24:    */ public class TarifaOperacionBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 7575889074704162047L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTarifaOperacion servicioTarifaOperacion;
/*  30:    */   private TarifaOperacion tarifaOperacion;
/*  31:    */   private LazyDataModel<TarifaOperacion> listaTarifaOperacion;
/*  32:    */   private DataTable dtTarifaOperacion;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 76 */     this.listaTarifaOperacion = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<TarifaOperacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 83 */         List<TarifaOperacion> lista = new ArrayList();
/*  44: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 86 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 87 */         lista = TarifaOperacionBean.this.servicioTarifaOperacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 89 */         TarifaOperacionBean.this.listaTarifaOperacion.setRowCount(TarifaOperacionBean.this.servicioTarifaOperacion.contarPorCriterio(filters));
/*  50:    */         
/*  51: 91 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58:109 */     this.tarifaOperacion = new TarifaOperacion();
/*  59:110 */     this.tarifaOperacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:111 */     this.tarifaOperacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:112 */     this.tarifaOperacion.setActivo(true);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String editar()
/*  65:    */   {
/*  66:121 */     if (getTarifaOperacion().getIdTarifaOperacion() > 0) {
/*  67:123 */       setEditado(true);
/*  68:    */     } else {
/*  69:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  70:    */     }
/*  71:127 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String guardar()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:137 */       this.servicioTarifaOperacion.guardar(this.tarifaOperacion);
/*  79:138 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  80:139 */       limpiar();
/*  81:140 */       setEditado(false);
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86:143 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:145 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:155 */       this.servicioTarifaOperacion.eliminar(this.tarifaOperacion);
/*  96:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 101:159 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 102:    */     }
/* 103:161 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cargarDatos()
/* 107:    */   {
/* 108:170 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String limpiar()
/* 112:    */   {
/* 113:179 */     crearEntidad();
/* 114:180 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public TarifaOperacion getTarifaOperacion()
/* 118:    */   {
/* 119:193 */     return this.tarifaOperacion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTarifaOperacion(TarifaOperacion tarifaOperacion)
/* 123:    */   {
/* 124:203 */     this.tarifaOperacion = tarifaOperacion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public LazyDataModel<TarifaOperacion> getListaTarifaOperacion()
/* 128:    */   {
/* 129:212 */     return this.listaTarifaOperacion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setListaTarifaOperacion(LazyDataModel<TarifaOperacion> listaTarifaOperacion)
/* 133:    */   {
/* 134:222 */     this.listaTarifaOperacion = listaTarifaOperacion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public DataTable getDtTarifaOperacion()
/* 138:    */   {
/* 139:231 */     return this.dtTarifaOperacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDtTarifaOperacion(DataTable dtTarifaOperacion)
/* 143:    */   {
/* 144:241 */     this.dtTarifaOperacion = dtTarifaOperacion;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.controller.TarifaOperacionBean
 * JD-Core Version:    0.7.0.1
 */