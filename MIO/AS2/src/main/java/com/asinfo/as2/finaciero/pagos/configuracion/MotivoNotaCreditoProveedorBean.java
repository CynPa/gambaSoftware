/*   1:    */ package com.asinfo.as2.finaciero.pagos.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioMotivoNotaCreditoProveedor;
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
/*  23:    */ public class MotivoNotaCreditoProveedorBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioMotivoNotaCreditoProveedor servicioMotivoNotaCreditoProveedor;
/*  29:    */   private MotivoNotaCreditoProveedor motivoNotaCreditoProveedor;
/*  30:    */   private LazyDataModel<MotivoNotaCreditoProveedor> listaMotivoNotaCreditoProveedor;
/*  31:    */   private DataTable dtMotivoNotaCreditoProveedor;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 70 */     limpiar();
/*  37:    */     
/*  38: 72 */     this.listaMotivoNotaCreditoProveedor = new LazyDataModel()
/*  39:    */     {
/*  40:    */       private static final long serialVersionUID = 6945336735259433394L;
/*  41:    */       
/*  42:    */       public List<MotivoNotaCreditoProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  43:    */       {
/*  44: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 86 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  47: 87 */         List<MotivoNotaCreditoProveedor> lista = MotivoNotaCreditoProveedorBean.this.servicioMotivoNotaCreditoProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49:    */ 
/*  50: 90 */         MotivoNotaCreditoProveedorBean.this.listaMotivoNotaCreditoProveedor.setRowCount(MotivoNotaCreditoProveedorBean.this.servicioMotivoNotaCreditoProveedor.contarPorCriterio(filters));
/*  51: 91 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String editar()
/*  57:    */   {
/*  58:103 */     if (getMotivoNotaCreditoProveedor().getIdMotivoNotaCreditoProveedor() > 0) {
/*  59:104 */       setEditado(true);
/*  60:    */     } else {
/*  61:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  62:    */     }
/*  63:108 */     return "";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String guardar()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:118 */       this.servicioMotivoNotaCreditoProveedor.guardar(this.motivoNotaCreditoProveedor);
/*  71:119 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  72:120 */       setEditado(false);
/*  73:121 */       limpiar();
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  78:124 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  79:    */     }
/*  80:126 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String eliminar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:136 */       this.servicioMotivoNotaCreditoProveedor.eliminar(this.motivoNotaCreditoProveedor);
/*  88:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  93:140 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  94:    */     }
/*  95:142 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String cargarDatos()
/*  99:    */   {
/* 100:151 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String limpiar()
/* 104:    */   {
/* 105:160 */     setEditado(false);
/* 106:161 */     this.motivoNotaCreditoProveedor = new MotivoNotaCreditoProveedor();
/* 107:162 */     this.motivoNotaCreditoProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 108:163 */     this.motivoNotaCreditoProveedor.setIdSucursal(AppUtil.getSucursal().getId());
/* 109:164 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public MotivoNotaCreditoProveedor getMotivoNotaCreditoProveedor()
/* 113:    */   {
/* 114:180 */     return this.motivoNotaCreditoProveedor;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setMotivoNotaCreditoProveedor(MotivoNotaCreditoProveedor motivoNotaCreditoProveedor)
/* 118:    */   {
/* 119:190 */     this.motivoNotaCreditoProveedor = motivoNotaCreditoProveedor;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public LazyDataModel<MotivoNotaCreditoProveedor> getListaMotivoNotaCreditoProveedor()
/* 123:    */   {
/* 124:199 */     return this.listaMotivoNotaCreditoProveedor;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setListaMotivoNotaCreditoProveedor(LazyDataModel<MotivoNotaCreditoProveedor> listaMotivoNotaCreditoProveedor)
/* 128:    */   {
/* 129:209 */     this.listaMotivoNotaCreditoProveedor = listaMotivoNotaCreditoProveedor;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public DataTable getDtMotivoNotaCreditoProveedor()
/* 133:    */   {
/* 134:218 */     return this.dtMotivoNotaCreditoProveedor;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDtMotivoNotaCreditoProveedor(DataTable dtMotivoNotaCreditoProveedor)
/* 138:    */   {
/* 139:228 */     this.dtMotivoNotaCreditoProveedor = dtMotivoNotaCreditoProveedor;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.configuracion.MotivoNotaCreditoProveedorBean
 * JD-Core Version:    0.7.0.1
 */