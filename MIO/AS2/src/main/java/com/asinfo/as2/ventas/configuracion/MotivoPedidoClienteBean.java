/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.MotivoPedidoCliente;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioMotivoPedidoCliente;
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
/*  24:    */ public class MotivoPedidoClienteBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioMotivoPedidoCliente servicioMotivoPedidoCliente;
/*  30:    */   private MotivoPedidoCliente motivoPedidoCliente;
/*  31:    */   private LazyDataModel<MotivoPedidoCliente> listaMotivoPedidoCliente;
/*  32:    */   private DataTable dtMotivoPedidoCliente;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 71 */     this.listaMotivoPedidoCliente = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<MotivoPedidoCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 78 */         List<MotivoPedidoCliente> lista = new ArrayList();
/*  44: 79 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 81 */         lista = MotivoPedidoClienteBean.this.servicioMotivoPedidoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48: 83 */         MotivoPedidoClienteBean.this.listaMotivoPedidoCliente.setRowCount(MotivoPedidoClienteBean.this.servicioMotivoPedidoCliente.contarPorCriterio(filters));
/*  49:    */         
/*  50: 85 */         return lista;
/*  51:    */       }
/*  52:    */     };
/*  53:    */   }
/*  54:    */   
/*  55:    */   private void crearMotivoPedidoCliente()
/*  56:    */   {
/*  57: 99 */     this.motivoPedidoCliente = new MotivoPedidoCliente();
/*  58:100 */     this.motivoPedidoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  59:101 */     this.motivoPedidoCliente.setIdSucursal(AppUtil.getSucursal().getId());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String editar()
/*  63:    */   {
/*  64:111 */     if (getMotivoPedidoCliente().getIdMotivoPedidoCliente() > 0) {
/*  65:112 */       setEditado(true);
/*  66:    */     } else {
/*  67:114 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  68:    */     }
/*  69:116 */     return "";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String guardar()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:126 */       this.servicioMotivoPedidoCliente.guardar(getMotivoPedidoCliente());
/*  77:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  78:128 */       setEditado(false);
/*  79:129 */       limpiar();
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  84:132 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  85:    */     }
/*  86:134 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String eliminar()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:145 */       this.servicioMotivoPedidoCliente.eliminar(getMotivoPedidoCliente());
/*  94:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  99:149 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 100:    */     }
/* 101:151 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String cargarDatos()
/* 105:    */   {
/* 106:160 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String limpiar()
/* 110:    */   {
/* 111:169 */     crearMotivoPedidoCliente();
/* 112:170 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public MotivoPedidoCliente getMotivoPedidoCliente()
/* 116:    */   {
/* 117:187 */     return this.motivoPedidoCliente;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setMotivoPedidoCliente(MotivoPedidoCliente motivoPedidoCliente)
/* 121:    */   {
/* 122:197 */     this.motivoPedidoCliente = motivoPedidoCliente;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public LazyDataModel<MotivoPedidoCliente> getListaMotivoPedidoCliente()
/* 126:    */   {
/* 127:206 */     return this.listaMotivoPedidoCliente;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setListaMotivoPedidoCliente(LazyDataModel<MotivoPedidoCliente> listaMotivoPedidoCliente)
/* 131:    */   {
/* 132:216 */     this.listaMotivoPedidoCliente = listaMotivoPedidoCliente;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public DataTable getDtMotivoPedidoCliente()
/* 136:    */   {
/* 137:225 */     return this.dtMotivoPedidoCliente;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setDtMotivoPedidoCliente(DataTable dtMotivoPedidoCliente)
/* 141:    */   {
/* 142:235 */     this.dtMotivoPedidoCliente = dtMotivoPedidoCliente;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.MotivoPedidoClienteBean
 * JD-Core Version:    0.7.0.1
 */