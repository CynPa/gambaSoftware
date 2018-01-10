/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.NivelCentroCosto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCentroCosto;
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
/*  24:    */ public class NivelCentroCostoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -1326899782514644732L;
/*  28:    */   @EJB
/*  29:    */   private ServicioNivelCentroCosto servicioNivelCentroCosto;
/*  30:    */   private NivelCentroCosto nivelCentroCosto;
/*  31:    */   private LazyDataModel<NivelCentroCosto> listaNivelCentroCosto;
/*  32:    */   private DataTable dtNivelCentroCosto;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 68 */     this.listaNivelCentroCosto = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = -8253799477679395886L;
/*  40:    */       
/*  41:    */       public List<NivelCentroCosto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 74 */         List<NivelCentroCosto> lista = new ArrayList();
/*  44:    */         
/*  45: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  46: 77 */         lista = NivelCentroCostoBean.this.servicioNivelCentroCosto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47: 78 */         NivelCentroCostoBean.this.listaNivelCentroCosto.setRowCount(NivelCentroCostoBean.this.servicioNivelCentroCosto.contarPorCriterio(filters));
/*  48: 79 */         return lista;
/*  49:    */       }
/*  50:    */     };
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String editar()
/*  54:    */   {
/*  55: 93 */     if (getNivelCentroCosto().getId() > 0) {
/*  56: 94 */       setEditado(true);
/*  57:    */     } else {
/*  58: 96 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  59:    */     }
/*  60: 98 */     return "";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String guardar()
/*  64:    */   {
/*  65:    */     try
/*  66:    */     {
/*  67:109 */       this.servicioNivelCentroCosto.guardar(this.nivelCentroCosto);
/*  68:110 */       limpiar();
/*  69:111 */       setEditado(false);
/*  70:112 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  71:    */     }
/*  72:    */     catch (Exception e)
/*  73:    */     {
/*  74:114 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  75:115 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  76:    */     }
/*  77:118 */     return "";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String eliminar()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:129 */       this.servicioNivelCentroCosto.eliminar(this.nivelCentroCosto);
/*  85:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  86:    */     }
/*  87:    */     catch (Exception e)
/*  88:    */     {
/*  89:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  90:133 */       LOG.error("ERROR AL ELMINAR DATOS", e);
/*  91:    */     }
/*  92:136 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String limpiar()
/*  96:    */   {
/*  97:146 */     crearNivelCentroCosto();
/*  98:147 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String cargarDatos()
/* 102:    */   {
/* 103:157 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void crearNivelCentroCosto()
/* 107:    */   {
/* 108:161 */     this.nivelCentroCosto = new NivelCentroCosto();
/* 109:162 */     this.nivelCentroCosto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 110:163 */     this.nivelCentroCosto.setIdSucursal(AppUtil.getSucursal().getId());
/* 111:    */   }
/* 112:    */   
/* 113:    */   public NivelCentroCosto getNivelCentroCosto()
/* 114:    */   {
/* 115:172 */     if (this.nivelCentroCosto == null) {
/* 116:173 */       crearNivelCentroCosto();
/* 117:    */     }
/* 118:175 */     return this.nivelCentroCosto;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setNivelCentroCosto(NivelCentroCosto nivelCentroCosto)
/* 122:    */   {
/* 123:185 */     this.nivelCentroCosto = nivelCentroCosto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<NivelCentroCosto> getListaNivelCentroCosto()
/* 127:    */   {
/* 128:194 */     return this.listaNivelCentroCosto;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaNivelCentroCosto(LazyDataModel<NivelCentroCosto> listaNivelCentroCosto)
/* 132:    */   {
/* 133:204 */     this.listaNivelCentroCosto = listaNivelCentroCosto;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtNivelCentroCosto()
/* 137:    */   {
/* 138:213 */     return this.dtNivelCentroCosto;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtNivelCentroCosto(DataTable dtNivelCentroCosto)
/* 142:    */   {
/* 143:223 */     this.dtNivelCentroCosto = dtNivelCentroCosto;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.NivelCentroCostoBean
 * JD-Core Version:    0.7.0.1
 */