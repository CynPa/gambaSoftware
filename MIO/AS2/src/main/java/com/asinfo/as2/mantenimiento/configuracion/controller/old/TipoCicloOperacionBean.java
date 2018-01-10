/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.TipoCicloOperacion;
/*   8:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoCicloOperacion;
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
/*  24:    */ public class TipoCicloOperacionBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoCicloOperacion servicioTipoCicloOperacion;
/*  30:    */   private TipoCicloOperacion tipoCicloOperacion;
/*  31:    */   private LazyDataModel<TipoCicloOperacion> listaTipoCicloOperacion;
/*  32:    */   private DataTable dtTipoCicloOperacion;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 77 */     this.listaTipoCicloOperacion = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<TipoCicloOperacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 84 */         List<TipoCicloOperacion> lista = new ArrayList();
/*  44: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 87 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 88 */         lista = TipoCicloOperacionBean.this.servicioTipoCicloOperacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 90 */         TipoCicloOperacionBean.this.listaTipoCicloOperacion.setRowCount(TipoCicloOperacionBean.this.servicioTipoCicloOperacion.contarPorCriterio(filters));
/*  50:    */         
/*  51: 92 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58:110 */     this.tipoCicloOperacion = new TipoCicloOperacion();
/*  59:111 */     this.tipoCicloOperacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:112 */     this.tipoCicloOperacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:121 */     if (getTipoCicloOperacion().getIdTipoCicloOperacion() > 0) {
/*  66:123 */       setEditado(true);
/*  67:    */     } else {
/*  68:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:127 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:137 */       this.servicioTipoCicloOperacion.guardar(this.tipoCicloOperacion);
/*  78:138 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:139 */       limpiar();
/*  80:140 */       setEditado(false);
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:143 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:145 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:155 */       this.servicioTipoCicloOperacion.eliminar(this.tipoCicloOperacion);
/*  95:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:159 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:161 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:170 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:179 */     crearEntidad();
/* 113:180 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public TipoCicloOperacion getTipoCicloOperacion()
/* 117:    */   {
/* 118:193 */     return this.tipoCicloOperacion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTipoCicloOperacion(TipoCicloOperacion tipoCicloOperacion)
/* 122:    */   {
/* 123:203 */     this.tipoCicloOperacion = tipoCicloOperacion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<TipoCicloOperacion> getListaTipoCicloOperacion()
/* 127:    */   {
/* 128:212 */     return this.listaTipoCicloOperacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaTipoCicloOperacion(LazyDataModel<TipoCicloOperacion> listaTipoCicloOperacion)
/* 132:    */   {
/* 133:222 */     this.listaTipoCicloOperacion = listaTipoCicloOperacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtTipoCicloOperacion()
/* 137:    */   {
/* 138:231 */     return this.dtTipoCicloOperacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtTipoCicloOperacion(DataTable dtTipoCicloOperacion)
/* 142:    */   {
/* 143:241 */     this.dtTipoCicloOperacion = dtTipoCicloOperacion;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.TipoCicloOperacionBean
 * JD-Core Version:    0.7.0.1
 */