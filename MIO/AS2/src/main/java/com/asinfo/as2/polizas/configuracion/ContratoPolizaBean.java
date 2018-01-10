/*   1:    */ package com.asinfo.as2.polizas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.polizas.ContratoPoliza;
/*   8:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioContratoPoliza;
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
/*  24:    */ public class ContratoPolizaBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioContratoPoliza servicioContratoPoliza;
/*  30:    */   private ContratoPoliza contratoPoliza;
/*  31:    */   private LazyDataModel<ContratoPoliza> listaContratoPoliza;
/*  32:    */   private DataTable dtContratoPoliza;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 72 */     this.listaContratoPoliza = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<ContratoPoliza> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 79 */         List<ContratoPoliza> lista = new ArrayList();
/*  44: 80 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 82 */         lista = ContratoPolizaBean.this.servicioContratoPoliza.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48: 84 */         ContratoPolizaBean.this.listaContratoPoliza.setRowCount(ContratoPolizaBean.this.servicioContratoPoliza.contarPorCriterio(filters));
/*  49: 85 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void crearContratoPoliza()
/*  55:    */   {
/*  56: 99 */     this.contratoPoliza = new ContratoPoliza();
/*  57:100 */     this.contratoPoliza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  58:101 */     this.contratoPoliza.setIdSucursal(AppUtil.getSucursal().getId());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63:110 */     if (getContratoPoliza().getIdContratoPoliza() > 0) {
/*  64:111 */       setEditado(true);
/*  65:    */     } else {
/*  66:113 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:115 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:125 */       this.servicioContratoPoliza.guardar(getContratoPoliza());
/*  76:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:127 */       setEditado(false);
/*  78:128 */       limpiar();
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  83:131 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  84:    */     }
/*  85:133 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String eliminar()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:144 */       this.servicioContratoPoliza.eliminar(getContratoPoliza());
/*  93:145 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:147 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  98:148 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  99:    */     }
/* 100:150 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String cargarDatos()
/* 104:    */   {
/* 105:159 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String limpiar()
/* 109:    */   {
/* 110:168 */     crearContratoPoliza();
/* 111:169 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public ContratoPoliza getContratoPoliza()
/* 115:    */   {
/* 116:186 */     if (this.contratoPoliza == null) {
/* 117:187 */       crearContratoPoliza();
/* 118:    */     }
/* 119:189 */     return this.contratoPoliza;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setContratoPoliza(ContratoPoliza contratoPoliza)
/* 123:    */   {
/* 124:199 */     this.contratoPoliza = contratoPoliza;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public LazyDataModel<ContratoPoliza> getListaContratoPoliza()
/* 128:    */   {
/* 129:208 */     return this.listaContratoPoliza;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setListaContratoPoliza(LazyDataModel<ContratoPoliza> listaContratoPoliza)
/* 133:    */   {
/* 134:218 */     this.listaContratoPoliza = listaContratoPoliza;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public DataTable getDtContratoPoliza()
/* 138:    */   {
/* 139:227 */     return this.dtContratoPoliza;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDtContratoPoliza(DataTable dtContratoPoliza)
/* 143:    */   {
/* 144:237 */     this.dtContratoPoliza = dtContratoPoliza;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.configuracion.ContratoPolizaBean
 * JD-Core Version:    0.7.0.1
 */