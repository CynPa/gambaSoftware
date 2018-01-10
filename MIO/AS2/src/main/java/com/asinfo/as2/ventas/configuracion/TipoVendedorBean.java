/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.TipoVendedor;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioTipoVendedor;
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
/*  24:    */ public class TipoVendedorBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoVendedor servicioTipoVendedor;
/*  30:    */   private TipoVendedor tipoVendedor;
/*  31:    */   private LazyDataModel<TipoVendedor> listaTipoVendedor;
/*  32:    */   private DataTable dtTipoVendedor;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 58 */     this.listaTipoVendedor = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  40:    */       
/*  41:    */       public List<TipoVendedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 65 */         List<TipoVendedor> lista = new ArrayList();
/*  44: 66 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 68 */         lista = TipoVendedorBean.this.servicioTipoVendedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48: 70 */         TipoVendedorBean.this.listaTipoVendedor.setRowCount(TipoVendedorBean.this.servicioTipoVendedor.contarPorCriterio(filters));
/*  49: 71 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String editar()
/*  55:    */   {
/*  56: 84 */     if (getTipoVendedor().getId() > 0) {
/*  57: 85 */       setEditado(true);
/*  58:    */     } else {
/*  59: 87 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  60:    */     }
/*  61: 90 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String guardar()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:103 */       this.servicioTipoVendedor.guardar(this.tipoVendedor);
/*  69:    */       
/*  70:105 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  71:106 */       limpiar();
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75:108 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  76:109 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  77:    */     }
/*  78:111 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String eliminar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:122 */       this.servicioTipoVendedor.eliminar(this.tipoVendedor);
/*  86:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:125 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  91:126 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  92:    */     }
/*  93:128 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String limpiar()
/*  97:    */   {
/*  98:139 */     setEditado(false);
/*  99:    */     
/* 100:141 */     this.tipoVendedor = new TipoVendedor();
/* 101:142 */     this.tipoVendedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 102:143 */     this.tipoVendedor.setIdSucursal(AppUtil.getSucursal().getId());
/* 103:    */     
/* 104:145 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String cargarDatos()
/* 108:    */   {
/* 109:155 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public TipoVendedor getTipoVendedor()
/* 113:    */   {
/* 114:164 */     return this.tipoVendedor;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setTipoVendedor(TipoVendedor tipoVendedor)
/* 118:    */   {
/* 119:174 */     this.tipoVendedor = tipoVendedor;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public LazyDataModel<TipoVendedor> getListaTipoVendedor()
/* 123:    */   {
/* 124:183 */     return this.listaTipoVendedor;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setListaTipoVendedor(LazyDataModel<TipoVendedor> listaTipoVendedor)
/* 128:    */   {
/* 129:193 */     this.listaTipoVendedor = listaTipoVendedor;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public DataTable getDtTipoVendedor()
/* 133:    */   {
/* 134:202 */     return this.dtTipoVendedor;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDtTipoVendedor(DataTable dtTipoVendedor)
/* 138:    */   {
/* 139:212 */     this.dtTipoVendedor = dtTipoVendedor;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.TipoVendedorBean
 * JD-Core Version:    0.7.0.1
 */