/*   1:    */ package com.asinfo.as2.finaciero.cobros.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
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
/*  23:    */ public class MotivoNotaCreditoClienteBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -3241877447465945808L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  29:    */   private MotivoNotaCreditoCliente motivoNotaCreditoCliente;
/*  30:    */   private LazyDataModel<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente;
/*  31:    */   private DataTable dtMotivoNotaCreditoCliente;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 70 */     limpiar();
/*  37:    */     
/*  38: 72 */     this.listaMotivoNotaCreditoCliente = new LazyDataModel()
/*  39:    */     {
/*  40:    */       private static final long serialVersionUID = 6945336735259433394L;
/*  41:    */       
/*  42:    */       public List<MotivoNotaCreditoCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  43:    */       {
/*  44: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 86 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  47: 87 */         List<MotivoNotaCreditoCliente> lista = MotivoNotaCreditoClienteBean.this.servicioMotivoNotaCreditoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49:    */ 
/*  50: 90 */         MotivoNotaCreditoClienteBean.this.listaMotivoNotaCreditoCliente.setRowCount(MotivoNotaCreditoClienteBean.this.servicioMotivoNotaCreditoCliente.contarPorCriterio(filters));
/*  51: 91 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String editar()
/*  57:    */   {
/*  58:103 */     if (getMotivoNotaCreditoCliente().getIdMotivoNotaCreditoCliente() > 0) {
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
/*  70:118 */       this.servicioMotivoNotaCreditoCliente.guardar(this.motivoNotaCreditoCliente);
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
/*  87:136 */       this.servicioMotivoNotaCreditoCliente.eliminar(this.motivoNotaCreditoCliente);
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
/* 105:161 */     setEditado(false);
/* 106:    */     
/* 107:163 */     this.motivoNotaCreditoCliente = new MotivoNotaCreditoCliente();
/* 108:164 */     this.motivoNotaCreditoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 109:165 */     this.motivoNotaCreditoCliente.setIdSucursal(AppUtil.getSucursal().getId());
/* 110:    */     
/* 111:167 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public MotivoNotaCreditoCliente getMotivoNotaCreditoCliente()
/* 115:    */   {
/* 116:183 */     return this.motivoNotaCreditoCliente;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setMotivoNotaCreditoCliente(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/* 120:    */   {
/* 121:193 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public LazyDataModel<MotivoNotaCreditoCliente> getListaMotivoNotaCreditoCliente()
/* 125:    */   {
/* 126:202 */     return this.listaMotivoNotaCreditoCliente;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setListaMotivoNotaCreditoCliente(LazyDataModel<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente)
/* 130:    */   {
/* 131:212 */     this.listaMotivoNotaCreditoCliente = listaMotivoNotaCreditoCliente;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public DataTable getDtMotivoNotaCreditoCliente()
/* 135:    */   {
/* 136:221 */     return this.dtMotivoNotaCreditoCliente;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setDtMotivoNotaCreditoCliente(DataTable dtMotivoNotaCreditoCliente)
/* 140:    */   {
/* 141:231 */     this.dtMotivoNotaCreditoCliente = dtMotivoNotaCreditoCliente;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.configuracion.MotivoNotaCreditoClienteBean
 * JD-Core Version:    0.7.0.1
 */