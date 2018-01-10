/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
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
/*  24:    */ public class MotivoAjusteInventarioBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -3241877447465945808L;
/*  28:    */   @EJB
/*  29:    */   private transient ServicioMotivoAjusteInventario servicioMotivoAjusteInventario;
/*  30:    */   private MotivoAjusteInventario motivoAjusteInventario;
/*  31:    */   private CuentaContable cuentaContable;
/*  32:    */   private LazyDataModel<MotivoAjusteInventario> listaMotivoAjusteInventario;
/*  33:    */   private DataTable dtMotivoAjusteInventario;
/*  34:    */   private DataTable dtCuentaContable;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 72 */     this.listaMotivoAjusteInventario = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 6945336735259433394L;
/*  42:    */       
/*  43:    */       public List<MotivoAjusteInventario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  46:    */         
/*  47: 86 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  48: 87 */         List<MotivoAjusteInventario> lista = MotivoAjusteInventarioBean.this.servicioMotivoAjusteInventario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  49:    */         
/*  50:    */ 
/*  51: 90 */         MotivoAjusteInventarioBean.this.listaMotivoAjusteInventario.setRowCount(MotivoAjusteInventarioBean.this.servicioMotivoAjusteInventario.contarPorCriterio(filters));
/*  52: 91 */         return lista;
/*  53:    */       }
/*  54:    */     };
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void crearEntidad()
/*  58:    */   {
/*  59:105 */     this.motivoAjusteInventario = new MotivoAjusteInventario();
/*  60:106 */     this.motivoAjusteInventario.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  61:107 */     this.motivoAjusteInventario.setIdSucursal(AppUtil.getSucursal().getId());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String editar()
/*  65:    */   {
/*  66:116 */     if (getMotivoAjusteInventario().getIdMotivoAjusteInventario() > 0)
/*  67:    */     {
/*  68:117 */       setMotivoAjusteInventario(this.servicioMotivoAjusteInventario.cargarDetalle(this.motivoAjusteInventario.getIdMotivoAjusteInventario()));
/*  69:118 */       setEditado(true);
/*  70:    */     }
/*  71:    */     else
/*  72:    */     {
/*  73:120 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  74:    */     }
/*  75:122 */     return "";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String guardar()
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:132 */       this.servicioMotivoAjusteInventario.guardar(this.motivoAjusteInventario);
/*  83:133 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  84:134 */       setEditado(false);
/*  85:135 */       limpiar();
/*  86:    */     }
/*  87:    */     catch (Exception e)
/*  88:    */     {
/*  89:137 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  90:138 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  91:    */     }
/*  92:140 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String eliminar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:150 */       this.servicioMotivoAjusteInventario.eliminar(this.motivoAjusteInventario);
/* 100:151 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 105:154 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 106:    */     }
/* 107:156 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String cargarDatos()
/* 111:    */   {
/* 112:165 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String limpiar()
/* 116:    */   {
/* 117:174 */     crearEntidad();
/* 118:175 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public MotivoAjusteInventario getMotivoAjusteInventario()
/* 122:    */   {
/* 123:193 */     return this.motivoAjusteInventario;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setMotivoAjusteInventario(MotivoAjusteInventario motivoAjusteInventario)
/* 127:    */   {
/* 128:203 */     this.motivoAjusteInventario = motivoAjusteInventario;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public LazyDataModel<MotivoAjusteInventario> getListaMotivoAjusteInventario()
/* 132:    */   {
/* 133:212 */     return this.listaMotivoAjusteInventario;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setListaMotivoAjusteInventario(LazyDataModel<MotivoAjusteInventario> listaMotivoAjusteInventario)
/* 137:    */   {
/* 138:222 */     this.listaMotivoAjusteInventario = listaMotivoAjusteInventario;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public DataTable getDtMotivoAjusteInventario()
/* 142:    */   {
/* 143:231 */     return this.dtMotivoAjusteInventario;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setDtMotivoAjusteInventario(DataTable dtMotivoAjusteInventario)
/* 147:    */   {
/* 148:241 */     this.dtMotivoAjusteInventario = dtMotivoAjusteInventario;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public DataTable getDtCuentaContable()
/* 152:    */   {
/* 153:250 */     return this.dtCuentaContable;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 157:    */   {
/* 158:260 */     this.dtCuentaContable = dtCuentaContable;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public CuentaContable getCuentaContable()
/* 162:    */   {
/* 163:269 */     return this.cuentaContable;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 167:    */   {
/* 168:279 */     this.cuentaContable = cuentaContable;
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.MotivoAjusteInventarioBean
 * JD-Core Version:    0.7.0.1
 */