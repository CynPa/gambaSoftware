/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.Criticidad;
/*   8:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioCriticidad;
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
/*  24:    */ public class CriticidadBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  28:    */   @EJB
/*  29:    */   private ServicioCriticidad servicioCriticidad;
/*  30:    */   private Criticidad criticidad;
/*  31:    */   private LazyDataModel<Criticidad> listaCriticidad;
/*  32:    */   private DataTable dtCriticidad;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 77 */     this.listaCriticidad = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<Criticidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 84 */         List<Criticidad> lista = new ArrayList();
/*  44: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 87 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 88 */         lista = CriticidadBean.this.servicioCriticidad.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 90 */         CriticidadBean.this.listaCriticidad.setRowCount(CriticidadBean.this.servicioCriticidad.contarPorCriterio(filters));
/*  50:    */         
/*  51: 92 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58:110 */     this.criticidad = new Criticidad();
/*  59:111 */     this.criticidad.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:112 */     this.criticidad.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:121 */     if (getCriticidad().getIdCriticidad() > 0) {
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
/*  77:137 */       this.servicioCriticidad.guardar(this.criticidad);
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
/*  94:155 */       this.servicioCriticidad.eliminar(this.criticidad);
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
/* 116:    */   public Criticidad getCriticidad()
/* 117:    */   {
/* 118:193 */     return this.criticidad;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setCriticidad(Criticidad criticidad)
/* 122:    */   {
/* 123:203 */     this.criticidad = criticidad;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<Criticidad> getListaCriticidad()
/* 127:    */   {
/* 128:212 */     return this.listaCriticidad;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaCriticidad(LazyDataModel<Criticidad> listaCriticidad)
/* 132:    */   {
/* 133:222 */     this.listaCriticidad = listaCriticidad;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtCriticidad()
/* 137:    */   {
/* 138:231 */     return this.dtCriticidad;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtCriticidad(DataTable dtCriticidad)
/* 142:    */   {
/* 143:241 */     this.dtCriticidad = dtCriticidad;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.CriticidadBean
 * JD-Core Version:    0.7.0.1
 */