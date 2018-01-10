/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.TipoTarifaSalarialHoraria;
/*   8:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoTarifaSalarialHoraria;
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
/*  24:    */ public class TipoTarifaSalarialHorariaBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -5236165158683459016L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoTarifaSalarialHoraria servicioTipoTarifaSalarialHoraria;
/*  30:    */   private TipoTarifaSalarialHoraria tipoTarifaSalarialHoraria;
/*  31:    */   private LazyDataModel<TipoTarifaSalarialHoraria> listaTipoTarifaSalarialHoraria;
/*  32:    */   private DataTable dtTipoTarifaSalarialHoraria;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 76 */     this.listaTipoTarifaSalarialHoraria = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<TipoTarifaSalarialHoraria> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 83 */         List<TipoTarifaSalarialHoraria> lista = new ArrayList();
/*  44: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 86 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 87 */         lista = TipoTarifaSalarialHorariaBean.this.servicioTipoTarifaSalarialHoraria.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 89 */         TipoTarifaSalarialHorariaBean.this.listaTipoTarifaSalarialHoraria.setRowCount(TipoTarifaSalarialHorariaBean.this.servicioTipoTarifaSalarialHoraria.contarPorCriterio(filters));
/*  50:    */         
/*  51: 91 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58:109 */     this.tipoTarifaSalarialHoraria = new TipoTarifaSalarialHoraria();
/*  59:110 */     this.tipoTarifaSalarialHoraria.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:111 */     this.tipoTarifaSalarialHoraria.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:120 */     if (getTipoTarifaSalarialHoraria().getIdTipoTarifaSalarialHoraria() > 0) {
/*  66:122 */       setEditado(true);
/*  67:    */     } else {
/*  68:124 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:126 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:136 */       this.servicioTipoTarifaSalarialHoraria.guardar(this.tipoTarifaSalarialHoraria);
/*  78:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:138 */       limpiar();
/*  80:139 */       setEditado(false);
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:142 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:144 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:154 */       this.servicioTipoTarifaSalarialHoraria.eliminar(this.tipoTarifaSalarialHoraria);
/*  95:155 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:158 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:160 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:169 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:178 */     crearEntidad();
/* 113:179 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public TipoTarifaSalarialHoraria getTipoTarifaSalarialHoraria()
/* 117:    */   {
/* 118:192 */     return this.tipoTarifaSalarialHoraria;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTipoTarifaSalarialHoraria(TipoTarifaSalarialHoraria tipoTarifaSalarialHoraria)
/* 122:    */   {
/* 123:202 */     this.tipoTarifaSalarialHoraria = tipoTarifaSalarialHoraria;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<TipoTarifaSalarialHoraria> getListaTipoTarifaSalarialHoraria()
/* 127:    */   {
/* 128:211 */     return this.listaTipoTarifaSalarialHoraria;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaTipoTarifaSalarialHoraria(LazyDataModel<TipoTarifaSalarialHoraria> listaTipoTarifaSalarialHoraria)
/* 132:    */   {
/* 133:221 */     this.listaTipoTarifaSalarialHoraria = listaTipoTarifaSalarialHoraria;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtTipoTarifaSalarialHoraria()
/* 137:    */   {
/* 138:230 */     return this.dtTipoTarifaSalarialHoraria;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtTipoTarifaSalarialHoraria(DataTable dtTipoTarifaSalarialHoraria)
/* 142:    */   {
/* 143:240 */     this.dtTipoTarifaSalarialHoraria = dtTipoTarifaSalarialHoraria;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.TipoTarifaSalarialHorariaBean
 * JD-Core Version:    0.7.0.1
 */