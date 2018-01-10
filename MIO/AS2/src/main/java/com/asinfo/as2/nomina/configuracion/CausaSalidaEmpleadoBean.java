/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CausaSalidaEmpleado;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCausaSalidaEmpleado;
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
/*  24:    */ public class CausaSalidaEmpleadoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioCausaSalidaEmpleado servicioCausaSalidaEmpleado;
/*  30:    */   private CausaSalidaEmpleado causaSalidaEmpleado;
/*  31:    */   private LazyDataModel<CausaSalidaEmpleado> listaCausaSalidaEmpleado;
/*  32:    */   private DataTable dtCausaSalidaEmpleado;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 72 */     this.listaCausaSalidaEmpleado = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<CausaSalidaEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 79 */         List<CausaSalidaEmpleado> lista = new ArrayList();
/*  44: 80 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 82 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 83 */         lista = CausaSalidaEmpleadoBean.this.servicioCausaSalidaEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 85 */         CausaSalidaEmpleadoBean.this.listaCausaSalidaEmpleado.setRowCount(CausaSalidaEmpleadoBean.this.servicioCausaSalidaEmpleado.contarPorCriterio(filters));
/*  50:    */         
/*  51: 87 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearCausaSalidaEmpleado()
/*  57:    */   {
/*  58:102 */     this.causaSalidaEmpleado = new CausaSalidaEmpleado();
/*  59:103 */     this.causaSalidaEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  60:104 */     this.causaSalidaEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:114 */     if ((getCausaSalidaEmpleado() != null) && (getCausaSalidaEmpleado().getIdCausaSalidaEmpleado() != 0)) {
/*  66:115 */       setEditado(true);
/*  67:    */     } else {
/*  68:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:119 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:129 */       this.servicioCausaSalidaEmpleado.guardar(this.causaSalidaEmpleado);
/*  78:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:131 */       setEditado(false);
/*  80:132 */       limpiar();
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:134 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:135 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:137 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:147 */       this.servicioCausaSalidaEmpleado.eliminar(this.causaSalidaEmpleado);
/*  95:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:150 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:151 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:153 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:162 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:171 */     crearCausaSalidaEmpleado();
/* 113:172 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public CausaSalidaEmpleado getCausaSalidaEmpleado()
/* 117:    */   {
/* 118:189 */     return this.causaSalidaEmpleado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setCausaSalidaEmpleado(CausaSalidaEmpleado causaSalidaEmpleado)
/* 122:    */   {
/* 123:199 */     this.causaSalidaEmpleado = causaSalidaEmpleado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<CausaSalidaEmpleado> getListaCausaSalidaEmpleado()
/* 127:    */   {
/* 128:208 */     return this.listaCausaSalidaEmpleado;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaCausaSalidaEmpleado(LazyDataModel<CausaSalidaEmpleado> listaCausaSalidaEmpleado)
/* 132:    */   {
/* 133:218 */     this.listaCausaSalidaEmpleado = listaCausaSalidaEmpleado;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtCausaSalidaEmpleado()
/* 137:    */   {
/* 138:227 */     return this.dtCausaSalidaEmpleado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtCausaSalidaEmpleado(DataTable dtCausaSalidaEmpleado)
/* 142:    */   {
/* 143:237 */     this.dtCausaSalidaEmpleado = dtCausaSalidaEmpleado;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.CausaSalidaEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */