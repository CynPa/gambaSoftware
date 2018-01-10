/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.NivelCuenta;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCuenta;
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
/*  24:    */ public class NivelCuentaBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 3000371545598336919L;
/*  28:    */   @EJB
/*  29:    */   private ServicioNivelCuenta servicioNivelCuenta;
/*  30:    */   private NivelCuenta nivelCuenta;
/*  31:    */   private LazyDataModel<NivelCuenta> listaNivelCuenta;
/*  32:    */   private DataTable dtNivelCuenta;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 56 */     this.listaNivelCuenta = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<NivelCuenta> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 63 */         List<NivelCuenta> lista = new ArrayList();
/*  44: 64 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 66 */         lista = NivelCuentaBean.this.servicioNivelCuenta.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47: 67 */         NivelCuentaBean.this.listaNivelCuenta.setRowCount(NivelCuentaBean.this.servicioNivelCuenta.ContarPorCriterio(filters));
/*  48:    */         
/*  49: 69 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String editar()
/*  55:    */   {
/*  56: 82 */     if (getNivelCuenta().getId() > 0) {
/*  57: 83 */       setEditado(true);
/*  58:    */     } else {
/*  59: 85 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  60:    */     }
/*  61: 88 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String guardar()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68: 96 */       setEditado(false);
/*  69: 97 */       this.servicioNivelCuenta.guardar(this.nivelCuenta);
/*  70: 98 */       limpiar();
/*  71: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75:102 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  76:103 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  77:    */     }
/*  78:105 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String eliminar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:114 */       this.servicioNivelCuenta.eliminar(this.nivelCuenta);
/*  86:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:117 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  91:118 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  92:    */     }
/*  93:120 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String limpiar()
/*  97:    */   {
/*  98:130 */     crearNivelCuenta();
/*  99:131 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String cargarDatos()
/* 103:    */   {
/* 104:138 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void crearNivelCuenta()
/* 108:    */   {
/* 109:147 */     this.nivelCuenta = new NivelCuenta();
/* 110:148 */     this.nivelCuenta.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 111:149 */     this.nivelCuenta.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 112:    */   }
/* 113:    */   
/* 114:    */   public LazyDataModel<NivelCuenta> getListaNivelCuenta()
/* 115:    */   {
/* 116:160 */     return this.listaNivelCuenta;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setListaNivelCuenta(LazyDataModel<NivelCuenta> listaNivelCuenta)
/* 120:    */   {
/* 121:170 */     this.listaNivelCuenta = listaNivelCuenta;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public DataTable getDtNivelCuenta()
/* 125:    */   {
/* 126:180 */     return this.dtNivelCuenta;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setDtNivelCuenta(DataTable dtNivelCuenta)
/* 130:    */   {
/* 131:190 */     this.dtNivelCuenta = dtNivelCuenta;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public NivelCuenta getNivelCuenta()
/* 135:    */   {
/* 136:199 */     if (this.nivelCuenta == null) {
/* 137:200 */       crearNivelCuenta();
/* 138:    */     }
/* 139:203 */     return this.nivelCuenta;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setNivelCuenta(NivelCuenta nivelCuenta)
/* 143:    */   {
/* 144:213 */     this.nivelCuenta = nivelCuenta;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.NivelCuentaBean
 * JD-Core Version:    0.7.0.1
 */